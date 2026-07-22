var importAcqUnitContentHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportAcqUnitWindow", {
    extend: 'Ext.window.Window',
    id:'ImportAcqUnitWindow_Id',
    alias: 'widget.ImportAcqUnitWindow',
    layout: 'fit',
    title: loginUserLanguageResource.importAcqUnit,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: false,          // 禁用内置按钮，完全自定义
    maximizable: false,
    minimizable: false,
    collapsible: false,
    width: 1500,
    minWidth: 1500,
    height: 700,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
        		xtype:'form',
        		id:'AcqUnitImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'AcqUnitImportFilefield_Id',
                    name: 'file',
                    fieldLabel: loginUserLanguageResource.uploadFile,
                    labelWidth: getLabelWidth(loginUserLanguageResource.uploadFile,loginUserLanguage),
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable:true,
                    buttonText: loginUserLanguageResource.selectUploadFile,
                    accept:'.json',
                    listeners:{
                        change:function(cmp){
                        	submitImportedAcqUnitFile();
                        }
                    }
        	    },{
                    id: 'ImportAcqUnitSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportAcqUnitSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},{
            	xtype: 'label',
            	id: 'ImportAcqUnitWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
				xtype : "hidden",
				id : 'ImportAcqUnitWinDeviceType_Id',
				value:'0'
			},'->',{
    	    	xtype: 'button',
                text: loginUserLanguageResource.saveAll,
                iconCls: 'save',
                handler: function (v, o) {
                	var treeStore = Ext.getCmp("ImportAcqUnitContentTreeGridPanel_Id").getStore();
                	var count=treeStore.getCount();
                	var overlayCount=0;
            		var collisionCount=0; 
                	for(var i=0;i<count;i++){
                		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign==1){
                			overlayCount++;
                		}else if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign==2){
                			collisionCount++;
                		}
                	}
                	if(overlayCount>0 || collisionCount>0){
                		var info="";
                		if(overlayCount>0){
                			info+=overlayCount+"个单元已存在";
                			if(collisionCount>0){
                				info+="，";
                			}
                		}
                		if(collisionCount>0){
                			info+=overlayCount+"个单元无权限修改";
                		}
                		info+="！是否执行全部保存？";
                		
                		Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
                            if (btn == "yes") {
                            	saveAllImportedAcqUnit();
                            }
                        });
                	}else{
                		saveAllImportedAcqUnit();
                	}
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'25%',
            	title:loginUserLanguageResource.uploadUnitList,
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importAcqUnitTreePanel_Id"
            },{
            	region: 'center',
            	id:"importedAcqUnitItemInfoTablePanel_Id",
            	title:loginUserLanguageResource.acqAndCtrlItemConfig,
            	layout: "fit",
            	html:'<div class="importedAcqUnitItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importedAcqUnitItemInfoTableDiv_Id"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(importAcqUnitContentHandsontableHelper!=null && importAcqUnitContentHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importAcqUnitContentHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
                }
            }],
            listeners: {
                beforeclose: function (panel) {
                	clearImportAcqUnitHandsontable();
                },
                // 最大化时，确保窗口处于展开状态，并刷新表格
                maximize: function (win) {
                    win._minimized = false;
                    win.setTitle(win.originalTitle);
                    if (win._panel) win._panel.show();
                    if (win._toolbar) win._toolbar.show();
                    var minBtn = win._minimizeBtn;
                    var expandBtn = win._expandBtn;
                    if (minBtn) minBtn.style.display = 'inline-block';
                    if (expandBtn) expandBtn.style.display = 'none';
                    var maxBtn = win._maximizeBtn;
                    var restoreBtn = win._restoreBtn;
                    if (maxBtn) maxBtn.style.display = 'none';
                    if (restoreBtn) restoreBtn.style.display = 'inline-block';
                    var panel = Ext.getCmp('ImportAcqUnitWindow_Id');
                    if (panel) {
                        Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                    }
                },
                restore: function (win) {
                    if (win._minimized) {
                        win._minimized = false;
                        win.setTitle(win.originalTitle);
                        if (win._panel) win._panel.show();
                        if (win._toolbar) win._toolbar.show();
                        var minBtn = win._minimizeBtn;
                        var expandBtn = win._expandBtn;
                        if (minBtn) minBtn.style.display = 'inline-block';
                        if (expandBtn) expandBtn.style.display = 'none';
                    }
                    var maxBtn = win._maximizeBtn;
                    var restoreBtn = win._restoreBtn;
                    if (maxBtn) maxBtn.style.display = 'inline-block';
                    if (restoreBtn) restoreBtn.style.display = 'none';
                    if (win._savedHeight && !win.maximized) {
                        win.setHeight(win._savedHeight);
                        win._savedHeight = null;
                    }
                    win._minimized = false;
                    win.setTitle(win.originalTitle);
                    var panel = Ext.getCmp('ImportAcqUnitWindow_Id');
                    if (panel) {
                        Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 50);
                    }
                },
                afterrender: function (panel) {
                    var win = panel;
                    win.originalTitle = win.title;
                    win._savedHeight = null;
                    win._minimized = false;
                    win._defaultHeight = win.getHeight();

                    win._panel = win.down('#ImportAcqUnitWindow_Id');
                    win._toolbar = win.down('toolbar');
                    if (win._toolbar) win._toolbar.show();

                    var header = win.getHeader();
                    if (!header) return;

                    var headerEl = header.el.dom;
                    var btnContainer = document.createElement('div');
                    btnContainer.style.cssText = 
                        'position:absolute; right:2px; top:0; height:100%;' +
                        'display:flex; align-items:center; gap:2px; z-index:10;' +
                        'padding:0 4px;';
                    headerEl.style.position = 'relative';
                    headerEl.appendChild(btnContainer);

                    function createButton(text, title, clickHandler, isClose) {
                        var btn = document.createElement('button');
                        btn.textContent = text;
                        btn.title = title;
                        var defaultColor = '#404040';
                        btn.style.cssText = 
                            'background:transparent; border:none; font-size:16px; font-weight:300;' +
                            'cursor:pointer; padding:0 8px;' +
                            'display:flex; align-items:center; justify-content:center; border-radius:2px;' +
                            'transition:background 0.15s, color 0.15s;' +
                            'color:' + defaultColor + '; touch-action:manipulation;' +
                            'font-family:sans-serif; line-height:1;' +
                            'min-width:30px; min-height:30px;';

                        // 使用 pointerdown 事件（统一鼠标和触摸）
                        btn.addEventListener('pointerdown', function(e) {
                            e.preventDefault();
                            e.stopPropagation();
                            // 视觉反馈
                            if (isClose) {
                                this.style.background = '#e81123';
                                this.style.color = '#ffffff';
                            } else {
                                this.style.background = 'rgba(0,0,0,0.15)';
                            }
                            // 执行业务逻辑
                            clickHandler(e);
                            // 释放指针捕获（如果有）
                            this.releasePointerCapture(e.pointerId);
                        }, { passive: false });

                        // 指针释放时恢复样式
                        btn.addEventListener('pointerup', function(e) {
                            this.style.background = 'transparent';
                            this.style.color = isClose ? defaultColor : defaultColor;
                        }, { passive: false });

                        btn.addEventListener('pointerleave', function(e) {
                            this.style.background = 'transparent';
                            this.style.color = isClose ? defaultColor : defaultColor;
                        }, { passive: false });

                        return btn;
                    }

                    // ---- 创建各按钮 ----
                    var minBtn = createButton('─', '', function () {
                        win._savedHeight = win.getHeight();
                        var headerH = win.getHeader() ? win.getHeader().getHeight() : 30;
                        if (win._panel) win._panel.hide();
                        if (win._toolbar) win._toolbar.hide();
                        win.setHeight(headerH);
                        win._minimized = true;
//                        win.setTitle(win.originalTitle + ' (已最小化)');
                        minBtn.style.display = 'none';
                        expandBtn.style.display = 'inline-block';
                    });
                    btnContainer.appendChild(minBtn);
                    win._minimizeBtn = minBtn;

                    var expandBtn = createButton('⤢', '', function () {
                        if (win._savedHeight) {
                            win.setHeight(win._savedHeight);
                            win._savedHeight = null;
                        } else {
                            win.setHeight(win._defaultHeight || '80%');
                        }
                        win._minimized = false;
                        win.setTitle(win.originalTitle);
                        if (win._panel) win._panel.show();
                        if (win._toolbar) win._toolbar.show();
                        win.updateLayout();
                        expandBtn.style.display = 'none';
                        minBtn.style.display = 'inline-block';
                        var panel = Ext.getCmp('ImportAcqUnitWindow_Id');
                        if (panel) {
                            Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 100);
                        }
                    });
                    expandBtn.style.display = 'none';
                    btnContainer.appendChild(expandBtn);
                    win._expandBtn = expandBtn;

                    var maxBtn = createButton('☐', '', function () {
                        if (!win.maximized) {
                            win.maximize();
                        }
                    });
                    btnContainer.appendChild(maxBtn);
                    win._maximizeBtn = maxBtn;

                    var restoreBtn = createButton('⧉', '', function () {
                        if (win.maximized) {
                            win.restore();
                        }
                    });
                    restoreBtn.style.display = 'none';
                    btnContainer.appendChild(restoreBtn);
                    win._restoreBtn = restoreBtn;

                    var closeBtn = createButton('✕', '', function () {
                        win.close();
                    }, true);
                    btnContainer.appendChild(closeBtn);

                    // 标题栏点击展开（忽略按钮区域）
                    header.el.on('click', function (e) {
                        if (btnContainer.contains(e.target)) {
                            return;
                        }
                        if (win._minimized && !win.maximized) {
                            e.stopEvent();
                            if (win._savedHeight) {
                                win.setHeight(win._savedHeight);
                                win._savedHeight = null;
                            } else {
                                win.setHeight(win._defaultHeight || '80%');
                            }
                            win._minimized = false;
                            win.setTitle(win.originalTitle);
                            win.updateLayout();
                            if (win._panel) win._panel.show();
                            if (win._toolbar) win._toolbar.show();
                            expandBtn.style.display = 'none';
                            minBtn.style.display = 'inline-block';
                            var panel = Ext.getCmp('ImportAcqUnitWindow_Id');
                            if (panel) {
                                Ext.defer(function () { panel.fireEvent('resize', panel, panel.getWidth(), panel.getHeight()); }, 100);
                            }
                        }
                    });

                    win.on('destroy', function () {
                        if (btnContainer.parentNode) {
                            btnContainer.parentNode.removeChild(btnContainer);
                        }
                    });
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportAcqUnitHandsontable(){
	if(importAcqUnitContentHandsontableHelper!=null){
		if(importAcqUnitContentHandsontableHelper.hot!=undefined){
			importAcqUnitContentHandsontableHelper.hot.destroy();
		}
		importAcqUnitContentHandsontableHelper=null;
	}
}

function importAcqUnitContentTreeSelectClear(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', false);
		if (childArrNode.childNodes != null) {
			importAcqUnitContentTreeSelectClear(childArrNode.childNodes);
        }
	});
}

function importAcqUnitContentTreeSelectAll(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', true);
		if (childArrNode.childNodes != null) {
			importAcqUnitContentTreeSelectAll(childArrNode.childNodes);
        }
	});
}

function submitImportedAcqUnitFile() {
	clearImportAcqUnitHandsontable();
	var form = Ext.getCmp("AcqUnitImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAcqUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
            	}else{
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
            	}
            	
            	
            	var importAcqUnitContentTreeGridPanel = Ext.getCmp("ImportAcqUnitContentTreeGridPanel_Id");
            	if (isNotVal(importAcqUnitContentTreeGridPanel)) {
            		importAcqUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportAcqUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
			}
        });
    }
    return false;
};

function CreateUploadedAcqUnitContentInfoTable(protocolName,protocolDeviceType,classes,unitName,groupName,groupType){
	clearImportAcqUnitHandsontable();
	if(Ext.getCmp("importedAcqUnitItemInfoTablePanel_Id")!=undefined){
		Ext.getCmp("importedAcqUnitItemInfoTablePanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
    }
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getUploadedAcqUnitItemsConfigData',
		success:function(response) {
			if(Ext.getCmp("importedAcqUnitItemInfoTablePanel_Id")!=undefined){
				Ext.getCmp("importedAcqUnitItemInfoTablePanel_Id").getEl().unmask();
		    }
			
			Ext.getCmp("importedAcqUnitItemInfoTablePanel_Id").setTitle(unitName);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAcqUnitContentHandsontableHelper==null || importAcqUnitContentHandsontableHelper.hot==undefined){
				importAcqUnitContentHandsontableHelper = ImportAcqUnitContentHandsontableHelper.createNew("importedAcqUnitItemInfoTableDiv_Id");
				var colHeaders=[loginUserLanguageResource.idx,loginUserLanguageResource.name,loginUserLanguageResource.startAddress,loginUserLanguageResource.RWType,loginUserLanguageResource.unit,loginUserLanguageResource.resolutionMode,'',loginUserLanguageResource.dailyCalculate,loginUserLanguageResource.dailyCalculateColumn];
				var columns="[" 
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,importAcqUnitContentHandsontableHelper);}},"
						+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.readOnly+"', '"+loginUserLanguageResource.readWrite+"']}," 
						+"{data:'unit'},"
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.switchingValue+"', '"+loginUserLanguageResource.enumValue+"','"+loginUserLanguageResource.numericValue+"']}," 
						+"{data:'bitIndex'}," 
						+"{data:'dailyTotalCalculate',type:'checkbox'},"
						+"{data:'dailyTotalCalculateName'}"
						+"]";
				importAcqUnitContentHandsontableHelper.colHeaders=colHeaders;
				importAcqUnitContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				
				if(classes==2 && groupType==0){
					importAcqUnitContentHandsontableHelper.hiddenColumns=[2,3,4,5,6];
				}else{
					importAcqUnitContentHandsontableHelper.hiddenColumns=[2,3,4,5,6,7,8];
				}
				
				if(result.totalRoot.length==0){
					importAcqUnitContentHandsontableHelper.Data=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					importAcqUnitContentHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAcqUnitContentHandsontableHelper.Data=result.totalRoot;
					importAcqUnitContentHandsontableHelper.createTable(result.totalRoot);
				}
			}
		},
		failure:function(){
			if(Ext.getCmp("importedAcqUnitItemInfoTablePanel_Id")!=undefined){
				Ext.getCmp("importedAcqUnitItemInfoTablePanel_Id").getEl().unmask();
		    }
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			protocolName:protocolName,
			protocolDeviceType:protocolDeviceType,
			classes:classes,
			unitName:unitName,
			groupName:groupName,
			groupType:groupType
        }
	});
};

var ImportAcqUnitContentHandsontableHelper = {
		createNew: function (divid) {
	        var importAcqUnitContentHandsontableHelper = {};
	        importAcqUnitContentHandsontableHelper.hot1 = '';
	        importAcqUnitContentHandsontableHelper.divid = divid;
	        importAcqUnitContentHandsontableHelper.validresult=true;//数据校验
	        importAcqUnitContentHandsontableHelper.colHeaders=[];
	        importAcqUnitContentHandsontableHelper.columns=[];
	        importAcqUnitContentHandsontableHelper.AllData=[];
	        importAcqUnitContentHandsontableHelper.Data=[];
	        
	        importAcqUnitContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importAcqUnitContentHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importAcqUnitContentHandsontableHelper.createTable = function (data) {
	        	$('#'+importAcqUnitContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAcqUnitContentHandsontableHelper.divid);
	        	importAcqUnitContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		theme: 'ht-theme-classic',
	        		data: data,
	        		hiddenColumns: {
	                    columns: importAcqUnitContentHandsontableHelper.hiddenColumns,
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [50,140,60,80,80,80,80,80,80],
	                columns:importAcqUnitContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAcqUnitContentHandsontableHelper.colHeaders,//显示列头
	                nestedRows:true,
	                columnHeaderHeight: 28,
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                outsideClickDeselects:false,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.editor = false;
	                    return cellProperties;
	                }
	        	});
	        }
	        importAcqUnitContentHandsontableHelper.saveData = function () {}
	        importAcqUnitContentHandsontableHelper.clearContainer = function () {
	        	importAcqUnitContentHandsontableHelper.AllData = [];
	        }
	        return importAcqUnitContentHandsontableHelper;
	    }
};

adviceImportAcqUnitCollisionInfoColor = function(val,o,p,e) {
	var saveSign=p.data.saveSign;
	var tipval=val;
	var backgroundColor='#FFFFFF';
 	var color='#DC2828';
 	if(saveSign==0){
 		color='#000000';
 	}
 	var opacity=0;
 	var rgba=color16ToRgba(backgroundColor,opacity);
 	o.style='background-color:'+rgba+';color:'+color+';';
 	if(isNotVal(tipval)){
 		return Ext.String.format(
 		        '<span data-qtip="{0}">{1}</span>',
 		        Ext.String.htmlEncode(tipval),
 		        Ext.String.htmlEncode(val)
 		    );
 	}
}

function saveSingelImportedAcqUnit(unitName,protocolName,protocolDeviceType,saveSign,msg){
	unitName = decodeURIComponent(unitName);
	protocolName = decodeURIComponent(protocolName);
	protocolDeviceType = decodeURIComponent(protocolDeviceType);
	saveSign = decodeURIComponent(saveSign);
	msg = decodeURIComponent(msg);
	if(parseInt(saveSign)>0){
		Ext.Msg.confirm(loginUserLanguageResource.tip, msg,function (btn) {
			if (btn == "yes") {
				Ext.Ajax.request({
					url : context + '/acquisitionUnitManagerController/saveSingelImportedAcqUnit',
					method : "POST",
					params : {
						unitName : unitName,
						protocolName : protocolName,
						protocolDeviceType : protocolDeviceType
					},
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
						if (result.success==true) {
							Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
						}else{
							Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailed+"</font>");
						}
						Ext.getCmp("ImportAcqUnitContentTreeGridPanel_Id").getStore().load();

						var treePanel=Ext.getCmp("AcqUnitProtocolTreeGridPanel_Id");
			    		if(isNotVal(treePanel)){
			    			treePanel.getStore().load();
			    		}else{
			    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitProtocolTreeInfoStore');
			    		}
					},
					failure : function() {
						Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
					}
				});
			}
		});
	}else{
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveSingelImportedAcqUnit',
			method : "POST",
			params : {
				unitName : unitName,
				protocolName : protocolName,
				protocolDeviceType : protocolDeviceType
			},
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				if (result.success==true) {
					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
				}else{
					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailed+"</font>");
				}
				Ext.getCmp("ImportAcqUnitContentTreeGridPanel_Id").getStore().load();

				var treePanel=Ext.getCmp("AcqUnitProtocolTreeGridPanel_Id");
	    		if(isNotVal(treePanel)){
	    			treePanel.getStore().load();
	    		}else{
	    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitProtocolTreeInfoStore');
	    		}
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}
}

function saveAllImportedAcqUnit(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportAcqUnitContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	if(unitNameList.length>0){
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveAllImportedAcqUnit',
			method : "POST",
			params : {
				unitName : unitNameList.join(",")
			},
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				if (result.success==true) {
					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
				}else{
					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailed+"</font>");
				}
				Ext.getCmp("ImportAcqUnitContentTreeGridPanel_Id").getStore().load();
    			var treePanel=Ext.getCmp("AcqUnitProtocolTreeGridPanel_Id");
        		if(isNotVal(treePanel)){
        			treePanel.getStore().load();
        		}else{
        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitProtocolTreeInfoStore');
        		}
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}else{
		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>"+loginUserLanguageResource.noDataCanBeSaved+"</font>");
	}
}

iconImportSingleAcqUnitAction = function(value, e, record) {
	var resultstring='';
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		var unitName=record.data.text;
		var protocolName=record.data.protocol;
		var protocolDeviceType=record.data.protocolDeviceType;
		var saveSign=record.data.saveSign;
		var msg=record.data.msg;
		
		unitName = encodeURIComponent(unitName || '');
		protocolName = encodeURIComponent(protocolName || '');
		saveSign = encodeURIComponent(saveSign || '');
		msg = encodeURIComponent(msg || '');
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAcqUnit('"+unitName+"','"+protocolName+"','"+protocolDeviceType+"','"+saveSign+"','"+msg+"')>"+loginUserLanguageResource.save+"...</a>";
	}
	
	return resultstring;
}