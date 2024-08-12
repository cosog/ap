var importDisplayUnitAcqItemsHandsontableHelper=null;
var importDisplayUnitCtrlItemsHandsontableHelper=null;
var importDisplayUnitCalItemsHandsontableHelper=null;
var importDisplayUnitInputItemsHandsontableHelper=null;
Ext.define("AP.view.acquisitionUnit.ImportDisplayUnitWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportDisplayUnitWindow_Id',
    alias: 'widget.ImportDisplayUnitWindow',
    layout: 'fit',
    title: '显示单元导入',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader: true, //True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
    //    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1500,
    minWidth: 1500,
    height: 700,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            tbar: [{
                xtype: 'form',
                id: 'DisplayUnitImportForm_Id',
                width: 300,
                bodyPadding: 0,
                frame: true,
                items: [{
                    xtype: 'filefield',
                    id: 'DisplayUnitImportFilefield_Id',
                    name: 'file',
                    fieldLabel: '上传文件',
                    labelWidth: 60,
                    width: '100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable: true,
                    buttonText: '请选择上传文件',
                    accept: '.json',
                    listeners: {
                        change: function (cmp) {
                            submitImportedDisplayUnitFile();
                        }
                    }
        	    }, {
                    id: 'ImportDisplayUnitSelectItemType_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }, {
                    id: 'ImportDisplayUnitSelectItemId_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		}, {
                xtype: 'label',
                id: 'ImportDisplayUnitWinTabLabel_Id',
                hidden: true,
                html: ''
            }, {
                xtype: "hidden",
                id: 'ImportDisplayUnitWinDeviceType_Id',
                value: '0'
			}, '->', {
                xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                    var treeStore = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore();
                    var count = treeStore.getCount();
                    var overlayCount = 0;
                    var collisionCount = 0;
                    for (var i = 0; i < count; i++) {
                        if (treeStore.getAt(i).data.classes == 1 && treeStore.getAt(i).data.saveSign == 1) {
                            overlayCount++;
                        } else if (treeStore.getAt(i).data.classes == 1 && treeStore.getAt(i).data.saveSign == 2) {
                            collisionCount++;
                        }
                    }
                    if (overlayCount > 0 || collisionCount > 0) {
                        var info = "";
                        if (overlayCount > 0) {
                            info += overlayCount + "个单元已存在";
                            if (collisionCount > 0) {
                                info += "，";
                            }
                        }
                        if (collisionCount > 0) {
                            info += overlayCount + "个单元无权限修改";
                        }
                        info += "！是否执行全部保存？";

                        Ext.Msg.confirm('提示', info, function (btn) {
                            if (btn == "yes") {
                                saveAllImportedDisplayUnit();
                            }
                        });
                    } else {
                        saveAllImportedDisplayUnit();
                    }
                }
    	    }],
            layout: 'border',
            items: [{
                region: 'west',
                width: '25%',
                title: '上传单元列表',
                layout: 'fit',
                split: true,
                collapsible: true,
                id: "importDisplayUnitTreePanel_Id"
            }, {
            	border: true,
            	region: 'center',
            	layout: "border",
            	items: [{
            		region: 'center',
            		layout: "border",
            		items: [{
                		region: 'center',
                		title:'采集项配置',
                		id:"importDisplayUnitAcqItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="importDisplayUnitAcqItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayUnitAcqItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayUnitAcqItemsHandsontableHelper!=null && importDisplayUnitAcqItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayUnitAcqItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	title:'控制项配置',
                		id:"importDisplayUnitCtrlItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        collapsible: true,
                        split: true,
                        html:'<div class="importDisplayUnitCtrlItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayUnitCtrlItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayUnitCtrlItemsHandsontableHelper!=null && importDisplayUnitCtrlItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayUnitCtrlItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	}]
            	},{
            		region: 'east',
            		width:'50%',
            		layout: "border",
            		header: false,
            		split: true,
                    collapsible: true,
                	items: [{
                		region: 'center',
                    	layout: 'fit',
                    	title:'录入项配置',
                		id:"importDisplayUnitInputItemsConfigTableInfoPanel_Id",
                        html:'<div class="importDisplayUnitInputItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayUnitInputItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayUnitInputItemsHandsontableHelper!=null && importDisplayUnitInputItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayUnitInputItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	layout: 'fit',
                        collapsible: true,
                        split: true,
                    	title:'计算项配置',
                    	id:"importDisplayUnitCalItemsConfigTableInfoPanel_Id",
                        html:'<div class="importDisplayUnitCalItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayUnitCalItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importDisplayUnitCalItemsHandsontableHelper!=null && importDisplayUnitCalItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importDisplayUnitCalItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                	}]
            	}] 
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	clearImportDisplayUnitHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportDisplayUnitHandsontable(){
	if(importDisplayUnitAcqItemsHandsontableHelper!=null){
		if(importDisplayUnitAcqItemsHandsontableHelper.hot!=undefined){
			importDisplayUnitAcqItemsHandsontableHelper.hot.destroy();
		}
		importDisplayUnitAcqItemsHandsontableHelper=null;
	}
	
	if(importDisplayUnitCtrlItemsHandsontableHelper!=null){
		if(importDisplayUnitCtrlItemsHandsontableHelper.hot!=undefined){
			importDisplayUnitCtrlItemsHandsontableHelper.hot.destroy();
		}
		importDisplayUnitCtrlItemsHandsontableHelper=null;
	}
	
	if(importDisplayUnitCalItemsHandsontableHelper!=null){
		if(importDisplayUnitCalItemsHandsontableHelper.hot!=undefined){
			importDisplayUnitCalItemsHandsontableHelper.hot.destroy();
		}
		importDisplayUnitCalItemsHandsontableHelper=null;
	}
	
	if(importDisplayUnitInputItemsHandsontableHelper!=null){
		if(importDisplayUnitInputItemsHandsontableHelper.hot!=undefined){
			importDisplayUnitInputItemsHandsontableHelper.hot.destroy();
		}
		importDisplayUnitInputItemsHandsontableHelper=null;
	}
}

function submitImportedDisplayUnitFile() {
	clearImportDisplayUnitHandsontable();
	var form = Ext.getCmp("DisplayUnitImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedDisplayUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert("提示", "加载成功 ");
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            	}
            	
            	
            	var importDisplayUnitContentTreeGridPanel = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id");
            	if (isNotVal(importDisplayUnitContentTreeGridPanel)) {
            		importDisplayUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportDisplayUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};


adviceImportDisplayUnitCollisionInfoColor = function(val,o,p,e) {
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
 		return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
 	}
}

iconImportSingleDisplayUnitAction = function(value, e, record) {
	var resultstring='';
	var unitName=record.data.text;
	var acqUnit=record.data.acqUnit;
	var protocolName=record.data.protocol;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedDisplayUnit(\""+unitName+"\",\""+acqUnit+"\",\""+protocolName+"\")>保存...</a>";
	}
	return resultstring;
}

function saveSingelImportedDisplayUnit(unitName,acqUnit,protocolName){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedDisplayUnit',
		method : "POST",
		params : {
			unitName : unitName,
			acqUnit : acqUnit,
			protocolName : protocolName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function saveAllImportedDisplayUnit(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveAllImportedDisplayUnit',
		method : "POST",
		params : {
			unitName : unitNameList.join(",")
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportDisplayUnitContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function CreateImportDisplayUnitAcqItemsInfoTable(protocolName,acqUnitName,unitName,calculateType){
	Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(unitName!=''){
				Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle(unitName+"/采集项");
			}else{
				Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").setTitle("采集项");
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitAcqItemsHandsontableHelper==null || importDisplayUnitAcqItemsHandsontableHelper.hot==undefined){
				importDisplayUnitAcqItemsHandsontableHelper = ImportDisplayUnitAcqItemsHandsontableHelper.createNew("importDisplayUnitAcqItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','实时字段顺序','历史字段顺序','实时曲线','历史曲线']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}},"
						+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historyCurveConfShowValue'}"
						+"]";
				importDisplayUnitAcqItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayUnitAcqItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayUnitAcqItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitAcqItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayUnitAcqItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitAcqItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayUnitAcqItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName: protocolName,
			acqUnitName: acqUnitName,
			unitName: unitName,
			calculateType: calculateType,
			type: 0
        }
	});
};

var ImportDisplayUnitAcqItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitAcqItemsHandsontableHelper = {};
	        importDisplayUnitAcqItemsHandsontableHelper.hot1 = '';
	        importDisplayUnitAcqItemsHandsontableHelper.divid = divid;
	        importDisplayUnitAcqItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitAcqItemsHandsontableHelper.colHeaders=[];
	        importDisplayUnitAcqItemsHandsontableHelper.columns=[];
	        importDisplayUnitAcqItemsHandsontableHelper.AllData=[];
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitAcqItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitAcqItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitAcqItemsHandsontableHelper.divid);
	        	importDisplayUnitAcqItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,85,85,85,85],
	                columns:importDisplayUnitAcqItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importDisplayUnitAcqItemsHandsontableHelper.colHeaders,//显示列头
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

	                    cellProperties.readOnly = true;
	                    if(visualColIndex==6||visualColIndex==7){
		                	cellProperties.renderer = importDisplayUnitAcqItemsHandsontableHelper.addCurveBg;
		                }else{
		                	cellProperties.renderer = importDisplayUnitAcqItemsHandsontableHelper.addCellStyle;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayUnitAcqItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayUnitAcqItemsHandsontableHelper!=null
	                		&& importDisplayUnitAcqItemsHandsontableHelper.hot!=''
	                		&& importDisplayUnitAcqItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayUnitAcqItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayUnitAcqItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importDisplayUnitAcqItemsHandsontableHelper;
	    }
};

function CreateImportDisplayUnitCtrlItemsInfoTable(protocolName,acqUnitName,unitName,calculateType){
	Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(unitName!=''){
				Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle(unitName+"/控制项");
			}else{
				Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").setTitle("控制项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitCtrlItemsHandsontableHelper==null || importDisplayUnitCtrlItemsHandsontableHelper.hot==undefined){
				importDisplayUnitCtrlItemsHandsontableHelper = ImportDisplayUnitCtrlItemsHandsontableHelper.createNew("importDisplayUnitCtrlItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','字段顺序']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel'}," 
						+"{data:'realtimeSort'}"
						+"]";
				importDisplayUnitCtrlItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayUnitCtrlItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayUnitCtrlItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitCtrlItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayUnitCtrlItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitCtrlItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayUnitCtrlItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName: protocolName,
			acqUnitName: acqUnitName,
			unitName: unitName,
			calculateType: calculateType,
			type: 2
        }
	});
};

var ImportDisplayUnitCtrlItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitCtrlItemsHandsontableHelper = {};
	        importDisplayUnitCtrlItemsHandsontableHelper.hot1 = '';
	        importDisplayUnitCtrlItemsHandsontableHelper.divid = divid;
	        importDisplayUnitCtrlItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitCtrlItemsHandsontableHelper.colHeaders=[];
	        importDisplayUnitCtrlItemsHandsontableHelper.columns=[];
	        importDisplayUnitCtrlItemsHandsontableHelper.AllData=[];
	        
	        importDisplayUnitCtrlItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitCtrlItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitCtrlItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitCtrlItemsHandsontableHelper.divid);
	        	importDisplayUnitCtrlItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60],
	                columns:importDisplayUnitCtrlItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importDisplayUnitCtrlItemsHandsontableHelper.colHeaders,//显示列头
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
	                    cellProperties.readOnly = true;
	                    if(importDisplayUnitCtrlItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importDisplayUnitCtrlItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importDisplayUnitCtrlItemsHandsontableHelper.addCellStyle;
	    	            }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayUnitCtrlItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayUnitCtrlItemsHandsontableHelper!=null
	                		&& importDisplayUnitCtrlItemsHandsontableHelper.hot!=''
	                		&& importDisplayUnitCtrlItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayUnitCtrlItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayUnitCtrlItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importDisplayUnitCtrlItemsHandsontableHelper;
	    }
};

function CreateImportDisplayUnitCalItemsInfoTable(protocolName,acqUnitName,unitName,calculateType){
	Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(unitName!=''){
				Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle(unitName+"/计算项");
			}else{
				Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").setTitle("计算项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitCalItemsHandsontableHelper==null || importDisplayUnitCalItemsHandsontableHelper.hot==undefined){
				importDisplayUnitCalItemsHandsontableHelper = ImportDisplayUnitCalItemsHandsontableHelper.createNew("importDisplayUnitCalItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','实时字段顺序','历史字段顺序','实时曲线','历史曲线','数据来源']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitCalItemsHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitCalItemsHandsontableHelper);}}," 
						+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitCalItemsHandsontableHelper);}}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historyCurveConfShowValue'},"
						+"{data:'dataSource'}"
						+"]";
				importDisplayUnitCalItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayUnitCalItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayUnitCalItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitCalItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayUnitCalItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitCalItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayUnitCalItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName: protocolName,
			acqUnitName: acqUnitName,
			unitName: unitName,
			calculateType: calculateType,
			type: 1
        }
	});
};

var ImportDisplayUnitCalItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitCalItemsHandsontableHelper = {};
	        importDisplayUnitCalItemsHandsontableHelper.hot1 = '';
	        importDisplayUnitCalItemsHandsontableHelper.divid = divid;
	        importDisplayUnitCalItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitCalItemsHandsontableHelper.colHeaders=[];
	        importDisplayUnitCalItemsHandsontableHelper.columns=[];
	        importDisplayUnitCalItemsHandsontableHelper.AllData=[];
	        
	        importDisplayUnitCalItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitCalItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitCalItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitCalItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitCalItemsHandsontableHelper.divid);
	        	importDisplayUnitCalItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,85,85,85,85,85],
	                columns:importDisplayUnitCalItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importDisplayUnitCalItemsHandsontableHelper.colHeaders,//显示列头
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

	                    cellProperties.readOnly = true;
	                    if(visualColIndex==6||visualColIndex==7){
		                	cellProperties.renderer = importDisplayUnitCalItemsHandsontableHelper.addCurveBg;
		                }else{
		                	if(importDisplayUnitCalItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& importDisplayUnitCalItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = importDisplayUnitCalItemsHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayUnitCalItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayUnitCalItemsHandsontableHelper!=null
	                		&& importDisplayUnitCalItemsHandsontableHelper.hot!=''
	                		&& importDisplayUnitCalItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayUnitCalItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayUnitCalItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importDisplayUnitCalItemsHandsontableHelper;
	    }
};

function CreateImportDisplayUnitInputItemsInfoTable(protocolName,acqUnitName,unitName,calculateType){
	Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			if(unitName!=''){
				Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").setTitle(unitName+"/录入项");
			}else{
				Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").setTitle("录入项");
			}
			var result =  Ext.JSON.decode(response.responseText);
			if(importDisplayUnitInputItemsHandsontableHelper==null || importDisplayUnitInputItemsHandsontableHelper.hot==undefined){
				importDisplayUnitInputItemsHandsontableHelper = ImportDisplayUnitInputItemsHandsontableHelper.createNew("importDisplayUnitInputItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','实时字段顺序','历史字段顺序','实时曲线','历史曲线']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitInputItemsHandsontableHelper);}}," 
						+"{data:'realtimeSort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitInputItemsHandsontableHelper);}}," 
						+"{data:'historySort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importDisplayUnitInputItemsHandsontableHelper);}}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historyCurveConfShowValue'}"
						+"]";
				importDisplayUnitInputItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importDisplayUnitInputItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importDisplayUnitInputItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitInputItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importDisplayUnitInputItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importDisplayUnitInputItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importDisplayUnitInputItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName: protocolName,
			acqUnitName: acqUnitName,
			unitName: unitName,
			calculateType: calculateType,
			type: 3
        }
	});
};

var ImportDisplayUnitInputItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importDisplayUnitInputItemsHandsontableHelper = {};
	        importDisplayUnitInputItemsHandsontableHelper.hot1 = '';
	        importDisplayUnitInputItemsHandsontableHelper.divid = divid;
	        importDisplayUnitInputItemsHandsontableHelper.validresult=true;//数据校验
	        importDisplayUnitInputItemsHandsontableHelper.colHeaders=[];
	        importDisplayUnitInputItemsHandsontableHelper.columns=[];
	        importDisplayUnitInputItemsHandsontableHelper.AllData=[];
	        
	        importDisplayUnitInputItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitInputItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importDisplayUnitInputItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importDisplayUnitInputItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importDisplayUnitInputItemsHandsontableHelper.divid);
	        	importDisplayUnitInputItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,85,85,85,85],
	                columns:importDisplayUnitInputItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importDisplayUnitInputItemsHandsontableHelper.colHeaders,//显示列头
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

	                    cellProperties.readOnly = true;
	                    if(visualColIndex==6||visualColIndex==7){
		                	cellProperties.renderer = importDisplayUnitInputItemsHandsontableHelper.addCurveBg;
		                }else{
		                	if(importDisplayUnitInputItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& importDisplayUnitInputItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = importDisplayUnitInputItemsHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importDisplayUnitInputItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importDisplayUnitInputItemsHandsontableHelper!=null
	                		&& importDisplayUnitInputItemsHandsontableHelper.hot!=''
	                		&& importDisplayUnitInputItemsHandsontableHelper.hot!=undefined 
	                		&& importDisplayUnitInputItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importDisplayUnitInputItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importDisplayUnitInputItemsHandsontableHelper;
	    }
};