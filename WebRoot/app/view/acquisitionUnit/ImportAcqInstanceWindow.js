var importAcqInstanceConfigItemsHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportAcqInstanceWindow", {
    extend: 'Ext.window.Window',
    id:'ImportAcqInstanceWindow_Id',
    alias: 'widget.ImportAcqInstanceWindow',
    layout: 'fit',
    title:'采控实例导入',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: false,          // 禁用内置按钮，完全自定义
    maximizable: false,
    minimizable: false,
    collapsible: false,
    width: 500,
    minWidth: 500,
    height: 700,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar: [{
        		xtype:'form',
        		id:'AcqInstanceImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'AcqInstanceImportFilefield_Id',
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
                        	submitImportedAcqInstanceFile();
                        }
                    }
        	    },{
                    id: 'ImportAcqInstanceSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportAcqInstanceSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},{
            	xtype: 'label',
            	id: 'ImportAcqInstanceWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
				xtype : "hidden",
				id : 'ImportAcqInstanceWinDeviceType_Id',
				value:'0'
			},'->',{
    	    	xtype: 'button',
                text: loginUserLanguageResource.saveAll,
                iconCls: 'save',
                handler: function (v, o) {
                	var treeStore = Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore();
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
                			info+=overlayCount+"个实例已存在";
                			if(collisionCount>0){
                				info+="，";
                			}
                		}
                		if(collisionCount>0){
                			info+=overlayCount+"个实例无权限修改";
                		}
                		info+="！是否执行全部保存？";
                		
                		Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
                            if (btn == "yes") {
                            	saveAllImportedAcqInstance();
                            }
                        });
                	}else{
                		saveAllImportedAcqInstance();
                	}
                }
    	    }],
    	    
          layout: 'border',
          items: [{
          	region: 'center',
          	title:loginUserLanguageResource.instanceList,
          	layout: 'fit',
          	split: true,
            collapsible: true,
          	id:"importAcqInstanceTreePanel_Id"
          }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	clearImportAcqInstanceHandsontable();
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
                    var panel = Ext.getCmp('importAcqInstanceTreePanel_Id');
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
                    var panel = Ext.getCmp('importAcqInstanceTreePanel_Id');
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

                    win._panel = win.down('#importAcqInstanceTreePanel_Id');
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
                        var panel = Ext.getCmp('importAcqInstanceTreePanel_Id');
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
                            var panel = Ext.getCmp('importAcqInstanceTreePanel_Id');
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

function clearImportAcqInstanceHandsontable(){
	if(importAcqInstanceConfigItemsHandsontableHelper!=null){
		if(importAcqInstanceConfigItemsHandsontableHelper.hot!=undefined){
			importAcqInstanceConfigItemsHandsontableHelper.hot.destroy();
		}
		importAcqInstanceConfigItemsHandsontableHelper=null;
	}
}

function submitImportedAcqInstanceFile() {
	clearImportAcqInstanceHandsontable();
	var form = Ext.getCmp("AcqInstanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAcqInstanceFile',
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
            	
            	var importAcqInstanceContentTreeGridPanel = Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id");
            	if (isNotVal(importAcqInstanceContentTreeGridPanel)) {
            		importAcqInstanceContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportAcqInstanceContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
			}
        });
    }
    return false;
};

adviceImportAcqInstanceCollisionInfoColor = function(val,o,p,e) {
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

function saveSingelImportedAcqInstance(instanceName,unitName,protocolName,protocolDeviceType,saveSign,msg){
	instanceName = decodeURIComponent(instanceName);
	unitName = decodeURIComponent(unitName);
	protocolName = decodeURIComponent(protocolName);
	protocolDeviceType = decodeURIComponent(protocolDeviceType);
	saveSign = decodeURIComponent(saveSign);
	msg = decodeURIComponent(msg);
	if(parseInt(saveSign)>0){
		Ext.Msg.confirm(loginUserLanguageResource.tip, msg,function (btn) {
			if (btn == "yes") {
				Ext.Ajax.request({
					url : context + '/acquisitionUnitManagerController/saveSingelImportedAcqInstance',
					method : "POST",
					params : {
						instanceName : instanceName,
						unitName : unitName,
						protocolName : protocolName,
						protocolDeviceType:protocolDeviceType
					},
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
						if (result.success==true) {
							Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
						}else{
							Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailed+"</font>");
						}
						Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore().load();

			        	var treePanel=Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id");
			    		if(isNotVal(treePanel)){
			    			treePanel.getStore().load();
			    		}else{
			    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqInstanceProtocolTreeInfoStore');
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
			url : context + '/acquisitionUnitManagerController/saveSingelImportedAcqInstance',
			method : "POST",
			params : {
				instanceName : instanceName,
				unitName : unitName,
				protocolName : protocolName,
				protocolDeviceType:protocolDeviceType
			},
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				if (result.success==true) {
					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
				}else{
					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailed+"</font>");
				}
				Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore().load();

	        	var treePanel=Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id");
	    		if(isNotVal(treePanel)){
	    			treePanel.getStore().load();
	    		}else{
	    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqInstanceProtocolTreeInfoStore');
	    		}
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}
	
}

function saveAllImportedAcqInstance(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	if(unitNameList.length>0){
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveAllImportedAcqInstance',
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
				Ext.getCmp("ImportAcqInstanceContentTreeGridPanel_Id").getStore().load();
				
            	var treePanel=Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id");
        		if(isNotVal(treePanel)){
        			treePanel.getStore().load();
        		}else{
        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqInstanceProtocolTreeInfoStore');
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

iconImportSingleAcqInstanceAction = function(value, e, record) {
	var resultstring='';
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		var instanceName=record.data.text;
		var unitName=record.data.unitName;
		var protocolName=record.data.protocol;
		var protocolDeviceType=record.data.protocolDeviceType;
		var saveSign=record.data.saveSign;
		var msg=record.data.msg;
		
		instanceName = encodeURIComponent(instanceName || '');
		unitName = encodeURIComponent(unitName || '');
		protocolName = encodeURIComponent(protocolName || '');
		
		saveSign = encodeURIComponent(saveSign || '');
		msg = encodeURIComponent(msg || '');
		
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAcqInstance('"+instanceName+"','"+unitName+"','"+protocolName+"','"+protocolDeviceType+"','"+saveSign+"','"+msg+"')>"+loginUserLanguageResource.save+"...</a>";
	}
	return resultstring;
}

var ImportAcqInstanceConfigItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAcqInstanceConfigItemsHandsontableHelper = {};
	        importAcqInstanceConfigItemsHandsontableHelper.hot1 = '';
	        importAcqInstanceConfigItemsHandsontableHelper.divid = divid;
	        importAcqInstanceConfigItemsHandsontableHelper.validresult=true;//数据校验
	        importAcqInstanceConfigItemsHandsontableHelper.colHeaders=[];
	        importAcqInstanceConfigItemsHandsontableHelper.columns=[];
	        importAcqInstanceConfigItemsHandsontableHelper.AllData=[];
	        
	        importAcqInstanceConfigItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAcqInstanceConfigItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAcqInstanceConfigItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAcqInstanceConfigItemsHandsontableHelper.divid);
	        	importAcqInstanceConfigItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		theme: 'ht-theme-classic',
	        		data: data,
	        		colWidths: [50,130,80,90,90,80,80,90,80,80,80,80],
	                columns:importAcqInstanceConfigItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importAcqInstanceConfigItemsHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importAcqInstanceConfigItemsHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);

	                    cellProperties.editor = false;
	                    
	                    if(importAcqInstanceConfigItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAcqInstanceConfigItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAcqInstanceConfigItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAcqInstanceConfigItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAcqInstanceConfigItemsHandsontableHelper!=null
	                		&& importAcqInstanceConfigItemsHandsontableHelper.hot!=''
	                		&& importAcqInstanceConfigItemsHandsontableHelper.hot!=undefined 
	                		&& importAcqInstanceConfigItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAcqInstanceConfigItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        return importAcqInstanceConfigItemsHandsontableHelper;
	    }
};