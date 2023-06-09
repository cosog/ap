var importProtocolContentHandsontableHelper=null;

var importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper=null;
var importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper=null;
var importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper=null;

var importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper=null;
var importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper=null;
var importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper=null;
var importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper=null;
var importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper=null;
var importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper=null;
var importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper=null;

Ext.define("AP.view.acquisitionUnit.ProtocolImportWindow", {
    extend: 'Ext.window.Window',
    id:'ProtocolImportWindow_Id',
    alias: 'widget.protocolImportWindow',
    layout: 'fit',
    title:'协议导入',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
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
        		xtype:'form',
        		id:'ProtocolImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'ProtocolImportFilefield_Id',
                    name: 'file',
                    fieldLabel: '上传文件',
                    labelWidth: 60,
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable:true,
                    buttonText: '请选择上传文件',
                    accept:'.json',
                    listeners:{
                        change:function(cmp){
                        	submitImportedProtocolFile();
                        }
                    }
        	    },{
                    id: 'ImportProtocolSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportProtocolSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},'->',{
    	    	xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	var treeGridPanel = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id");
                	if(isNotVal(treeGridPanel)){
//                		var _record = treeGridPanel.store.data.items;
                		var _record = treeGridPanel.getChecked();
                		var addContent={};
                		addContent.acqUnitList=[];
                		addContent.displayUnitList=[];
                		addContent.alarmUnitList=[];

                		addContent.acqInstanceList=[];
                		addContent.displayInstanceList=[];
                		addContent.alarmInstanceList=[];
                		
                		Ext.Array.each(_record, function (name, index, countriesItSelf) {
                			var classes=_record[index].data.classes;
                			if(classes==1){
                				var type=_record[index].parentNode.data.type;
                				if(type==0){//采控单元
                					var acqUnit={};
                					acqUnit.id=_record[index].data.id;
                					acqUnit.acqGroupList=[];
                					if(_record[index].childNodes!=null && _record[index].childNodes.length>0){
                						for(var i=0;i<_record[index].childNodes.length;i++){
                							acqUnit.acqGroupList.push(_record[index].childNodes[i].data.id); 
                						}
                					}
                					addContent.acqUnitList.push(acqUnit);
                				}else if(type==1){//显示单元
                					addContent.displayUnitList.push(_record[index].data.id);
                				}else if(type==2){//报警单元
                					addContent.alarmUnitList.push(_record[index].data.id);
                				}else if(type==3){//采控实例
                					addContent.acqInstanceList.push(_record[index].data.id);
                				}else if(type==4){//显示实例
                					addContent.displayInstanceList.push(_record[index].data.id);
                				}else if(type==5){//报警实例
                					addContent.alarmInstanceList.push(_record[index].data.id);
                				}
                			}
                	    });
                		
                		Ext.Ajax.request({
                            method: 'POST',
                            url: context + '/acquisitionUnitManagerController/saveImportProtocolData',
                            success: function (response) {
                            	rdata = Ext.JSON.decode(response.responseText);
                            	if (rdata.overlayList.length==0 && rdata.errorDataList.length==0) {//无冲突
                            		Ext.Ajax.request({
                                        method: 'POST',
                                        url: context + '/acquisitionUnitManagerController/saveImportProtocolData',
                                        success: function (response) {
                                        	rdata = Ext.JSON.decode(response.responseText);
                                        	if (rdata.success) {
                                        		Ext.MessageBox.alert("信息", "协议及关联内容导入成功！");
                                        	}else{
                                        		Ext.MessageBox.alert("信息", "协议及关联内容导入失败！");
                                        	}
                                        },
                                        failure: function () {
                                            Ext.MessageBox.alert("信息", "请求失败");
                                        },
                                        params: {
                                        	data: JSON.stringify(addContent),
                                        	check: 0
                                        }
                                    });
                            	}else{
                            		var PrototolImportOverlayDataWindow = Ext.create("AP.view.acquisitionUnit.PrototolImportOverlayDataWindow");
                            		if(rdata.overlayList.length==0){
                            			Ext.getCmp("ProtocolImportOverlayTablePanel_Id").hide();
                                    	Ext.getCmp("ProtocolImportErrorTablePanel_Id").setHeight('100%');
                                    }else if(rdata.errorDataList.length==0){
                                    	Ext.getCmp("ProtocolImportErrorTablePanel_Id").hide();
                                    	Ext.getCmp("ProtocolImportErrorTablePanel_Id").setHeight('0%');
                                    	Ext.getCmp("ProtocolImportOverlayTablePanel_Id").setHeight('100%');
                                    }
                            		
                            		PrototolImportOverlayDataWindow.show();
                            		if(rdata.overlayList.length>0){
                            			CreateProtocolImportOverlayTreeTable(rdata);
                            		}
                            		if(rdata.errorDataList.length>0){
                            			CreateProtocolImportErrorTable(rdata);
                            		}
//                            		CreateProtocolImportOverlayTreeTable(rdata);
//                            		CreateProtocolImportOverlayTable(rdata);
//                            		CreateProtocolImportErrorTable(rdata);
                            	}
                            },
                            failure: function () {
                                Ext.MessageBox.alert("信息", "请求失败");
                            },
                            params: {
                            	data: JSON.stringify(addContent),
                            	check: 1
                            }
                        });
                	}
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'25%',
            	title:'协议相关内容',
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importPootocolTreePanel_Id"
            },{
            	region: 'center',
            	id:"importedProtocolItemInfoTablePanel_Id",
            	layout: "fit"
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	clearImportProtocolHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportProtocolHandsontable(){
	if(importProtocolContentHandsontableHelper!=null){
		if(importProtocolContentHandsontableHelper.hot!=undefined){
			importProtocolContentHandsontableHelper.hot.destroy();
		}
		importProtocolContentHandsontableHelper=null;
	}
	
	if(importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper=null;
	}
	if(importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper=null;
	}
	if(importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper=null;
	}
	
	if(importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper=null;
	}
	if(importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper=null;
	}
	if(importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper=null;
	}
	if(importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper=null;
	}
	if(importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper=null;
	}
	if(importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper=null;
	}
	if(importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper=null;
	}
}

function importProtocolContentTreeSelectClear(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', false);
		if (childArrNode.childNodes != null) {
			importProtocolContentTreeSelectClear(childArrNode.childNodes);
        }
	});
}

function importProtocolContentTreeSelectAll(node){
	var chlidArray = node;
	Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
		childArrNode.set('checked', true);
		if (childArrNode.childNodes != null) {
			importProtocolContentTreeSelectAll(childArrNode.childNodes);
        }
	});
}

function submitImportedProtocolFile() {
	clearImportProtocolHandsontable();
	var form = Ext.getCmp("ProtocolImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/getImportedProtocolFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert("提示", "上传成功 ");
            		Ext.getCmp("importPootocolTreePanel_Id").setTitle("<font color=red>"+result.protocolName+"</font>协议相关内容");
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            		Ext.getCmp("importPootocolTreePanel_Id").setTitle("协议相关内容");
            	}
            	var importProtocolContentTreeGridPanel = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id");
            	if (isNotVal(importProtocolContentTreeGridPanel)) {
            		importProtocolContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportProtocolContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};

function CreateImportProtocolContentInfoTable(id,classes,type,typeChange){
	clearImportProtocolHandsontable();
	var importedProtocolItemInfoTablePanel=Ext.getCmp("importedProtocolItemInfoTablePanel_Id");
	var importProtocolContentPanel=null;
	if(type==0 || type==3){
		importProtocolContentPanel=Ext.create('Ext.panel.Panel', {
			html: '<div id="importedProtocolItemInfoTableDiv_Id" style="width:100%;height:100%;"></div>',
	    	listeners: {
	    		resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                	if(importProtocolContentHandsontableHelper!=null && importProtocolContentHandsontableHelper.hot!=undefined){
                		var newWidth=width;
                		var newHeight=height;
                		var header=thisPanel.getHeader();
                		if(header){
                			newHeight=newHeight-header.lastBox.height-2;
                		}
                		importProtocolContentHandsontableHelper.hot.updateSettings({
                			width:newWidth,
                			height:newHeight
                		});
                	}
                }
	    	}
		});
	}else if(type==1 || type==4){
		importProtocolContentPanel=Ext.create('Ext.panel.Panel', {
        	border: true,
        	layout: "border",
        	items: [{
        		region: 'center',
        		layout: "border",
        		items: [{
            		region: 'center',
            		title:'采集项',
            		id:"ImportedProtocolDisplayUnitAndInstanceAcqItemsTablePanel_Id",
                    layout: 'fit',
                    html:'<div class="ImportedProtocolDisplayUnitAndInstanceAcqItemsTableContainer" style="width:100%;height:100%;"><div class="con" id="ImportedProtocolDisplayUnitAndInstanceAcqItemsTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper!=null && importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot!=undefined){
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
            	},{
            		region: 'south',
                	height:'50%',
                	title:'计算项',
                	collapsible: true,
                    split: true,
                	layout: 'fit',
                	id:"ImportedProtocolDisplayUnitAndInstanceCalItemsTablePanel_Id",
                    layout: 'fit',
                    html:'<div class="ImportedProtocolDisplayUnitAndInstanceCalItemsTableContainer" style="width:100%;height:100%;"><div class="con" id="ImportedProtocolDisplayUnitAndInstanceCalItemsTableDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper!=null && importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot!=undefined){
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
            	}]
        	},{
        		region: 'east',
        		width:'40%',
        		title:'控制项',
        		id:"ImportedProtocolDisplayUnitAndInstanceCtrlItemsTablePanel_Id",
                layout: 'fit',
                collapsible: true,
                split: true,
                html:'<div class="ImportedProtocolDisplayUnitAndInstanceCtrlItemsTableContainer" style="width:100%;height:100%;"><div class="con" id="ImportedProtocolDisplayUnitAndInstanceCtrlItemsTableDiv_id"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper!=null && importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot!=undefined){
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
                }
        	}]
        });
	}else if(type==2 || type==5){
		importProtocolContentPanel=Ext.create('Ext.TabPanel', {
            id:"ImportProtocolAlarmUnitAndInstanceItemsTabPanel_Id",
            activeTab: 0,
            border: false,
            tabPosition: 'top',
            items: [{
            	title:'数据量',
            	id:"ImportProtocolAlarmUnitAndInstanceNumItemsTableInfoPanel_Id",
            	region: 'center',
        		layout: "border",
        		items: [{
            		region: 'center',
            		title:'采集项',
            		layout: 'fit',
            		id:'ImportProtocolAlarmUnitAndInstanceNumItemsConfigTableInfoPanel_id',
                    html:'<div class="ImportProtocolAlarmUnitAndInstanceNumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ImportProtocolAlarmUnitAndInstanceNumItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper!=null && importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot!=undefined){
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
            	},{
            		region: 'south',
                	height:'50%',
                	title:'计算项',
                	collapsible: true,
                    split: true,
                    layout: 'fit',
                    id:'ImportProtocolAlarmUnitAndInstanceCalNumItemsConfigTableInfoPanel_id',
                    html:'<div class="ImportProtocolAlarmUnitAndInstanceCalNumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ImportProtocolAlarmUnitAndInstanceCalNumItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	if(importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper!=null && importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined){
                        		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                        	}
                        }
                    }
            	}]
            },{
            	title:'开关量',
            	id:"ImportProtocolAlarmUnitAndInstanceSwitchItemsTableInfoPanel_Id",
                layout: 'fit',
                html:'<div class="ImportProtocolAlarmUnitAndInstanceSwitchItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ImportProtocolAlarmUnitAndInstanceSwitchItemsConfigTableInfoDiv_id"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper!=null && importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined){
//                    		importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot.refreshDimensions();
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
                }
            },{
            	title:'枚举量',
            	id:"ImportProtocolAlarmUnitAndInstanceEnumItemsTableInfoPanel_Id",
                layout: 'fit',
                html:'<div class="ImportProtocolAlarmUnitAndInstanceEnumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ImportProtocolAlarmUnitAndInstanceEnumItemsConfigTableInfoDiv_id"></div></div>',
                listeners: {
                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                    	if(importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper!=null && importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot!=undefined){
//                    		importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot.refreshDimensions();
                    		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                    	}
                    }
                }
            },{
            	title:'工况诊断',
            	id:"ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsTableInfoPanel_Id",
            	 layout: 'fit',
                 html:'<div class="ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsConfigTableInfoDiv_id"></div></div>',
                 listeners: {
                     resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                     	if(importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper!=null && importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
//                     		importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.refreshDimensions();
                     		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                     	}
                     }
                 }
            },{
            	title:'运行状态',
            	id:"ImportProtocolAlarmUnitAndInstanceRunStatusItemsTableInfoPanel_Id",
            	 layout: 'fit',
                 html:'<div class="ImportProtocolAlarmUnitAndInstanceRunStatusItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ImportProtocolAlarmUnitAndInstanceRunStatusItemsConfigTableInfoDiv_id"></div></div>',
                 listeners: {
                     resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                     	if(importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper!=null && importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined){
//                     		importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot.refreshDimensions();
                     		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                     	}
                     }
                 }
            },{
            	title:'通信状态',
            	id:"ImportProtocolAlarmUnitAndInstanceCommStatusItemsTableInfoPanel_Id",
            	 layout: 'fit',
                 html:'<div class="ImportProtocolAlarmUnitAndInstanceCommStatusItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ImportProtocolAlarmUnitAndInstanceCommStatusItemsConfigTableInfoDiv_id"></div></div>',
                 listeners: {
                     resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                     	if(importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper!=null && importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined){
//                     		importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot.refreshDimensions();
                     		var newWidth=width;
                    		var newHeight=height;
                    		var header=thisPanel.getHeader();
                    		if(header){
                    			newHeight=newHeight-header.lastBox.height-2;
                    		}
                    		importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot.updateSettings({
                    			width:newWidth,
                    			height:newHeight
                    		});
                     	}
                     }
                 }
            }],
            listeners: {
            	tabchange: function (tabPanel, newCard, oldCard, obj) {
            		var id  = Ext.getCmp("ImportProtocolSelectItemId_Id").getValue();
                	var type  = Ext.getCmp("ImportProtocolSelectItemType_Id").getValue();
            		CreateImportProtocolAlarmContentTable(id,type);
            	}
            }
        });
	}
	
	
	importedProtocolItemInfoTablePanel.removeAll();
	importedProtocolItemInfoTablePanel.add(importProtocolContentPanel);
	
	if(type==0 || type==3){
		importedProtocolItemInfoTablePanel.el.mask(cosog.string.updatewait).show();
		if(importProtocolContentHandsontableHelper!=null){
			if(importProtocolContentHandsontableHelper.hot!=undefined){
				importProtocolContentHandsontableHelper.hot.destroy();
			}
			importProtocolContentHandsontableHelper=null;
		}
		Ext.Ajax.request({
			method:'POST',
			url:context + '/acquisitionUnitManagerController/getImportProtocolContentData',
			success:function(response) {
				importedProtocolItemInfoTablePanel.getEl().unmask();
				var result =  Ext.JSON.decode(response.responseText);
				if(importProtocolContentHandsontableHelper==null || importProtocolContentHandsontableHelper.hot==undefined){
					importProtocolContentHandsontableHelper = ImportProtocolContentHandsontableHelper.createNew("importedProtocolItemInfoTableDiv_Id");
					var colHeaders="[" 
						+"['','',{label: '下位机', colspan: 5},{label: '上位机', colspan: 5}]," 
						+"['序号','名称','起始地址','存储数据类型','存储数据数量','读写类型','响应模式','接口数据类型','小数位数','换算比例','单位','解析模式']" 
						+"]";
					var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}},"
					 	+"{data:'storeDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bit','byte','int16','uint16','float32','bcd']}," 
					 	+"{data:'quantity',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}}," 
					 	+"{data:'RWType',type:'dropdown',strict:true,allowInvalid:false,source:['只读', '只写', '读写']}," 
					 	+"{data:'acqMode',type:'dropdown',strict:true,allowInvalid:false,source:['主动上传', '被动响应']}," 
						+"{data:'IFDataType',type:'dropdown',strict:true,allowInvalid:false,source:['bool','int','float32','float64','string']}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}}," 
						+"{data:'ratio',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolConfigAddrMappingItemsHandsontableHelper);}}," 
						+"{data:'unit'}," 
						+"{data:'resolutionMode',type:'dropdown',strict:true,allowInvalid:false,source:['开关量', '枚举量','数据量']}" 
						+"]";
					importProtocolContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
					importProtocolContentHandsontableHelper.columns=Ext.JSON.decode(columns);
					if(result.totalRoot.length==0){
						importProtocolContentHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
					}else{
						importProtocolContentHandsontableHelper.createTable(result.totalRoot);
					}
				}else{
					if(result.totalRoot.length==0){
						importProtocolContentHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
					}else{
						importProtocolContentHandsontableHelper.hot.loadData(result.totalRoot);
					}
				}
			},
			failure:function(){
				importedProtocolItemInfoTablePanel.getEl().unmask();
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			},
			params: {
				id:id,
				type:type,
				classes:classes
	        }
		});
	}else if(type==1 || type==4){
		importedProtocolItemInfoTablePanel.el.mask(cosog.string.updatewait).show();
		CreateImportProtocolDisplayUnitAndInstanceAcqItemsInfoTable(id,type);
		CreateImportProtocolDisplayUnitAndInstanceCalItemsInfoTable(id,type);
		CreateImportProtocolDisplayUnitAndInstanceCtrlItemsInfoTable(id,type);
		importedProtocolItemInfoTablePanel.getEl().unmask();
	}else if(type==2 || type==5){
		CreateImportProtocolAlarmContentTable(id,type);
	}else{
		
	}
};

var ImportProtocolContentHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolContentHandsontableHelper = {};
	        importProtocolContentHandsontableHelper.hot1 = '';
	        importProtocolContentHandsontableHelper.divid = divid;
	        importProtocolContentHandsontableHelper.validresult=true;//数据校验
	        importProtocolContentHandsontableHelper.colHeaders=[];
	        importProtocolContentHandsontableHelper.columns=[];
	        importProtocolContentHandsontableHelper.AllData=[];
	        
	        importProtocolContentHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolContentHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolContentHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolContentHandsontableHelper.divid);
	        	importProtocolContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,130,80,90,90,80,80,90,80,80,80,80],
	                columns:importProtocolContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
//	                colHeaders:importProtocolContentHandsontableHelper.colHeaders,//显示列头
	                nestedHeaders:importProtocolContentHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolContentHandsontableHelper;
	    }
};

function CreateImportProtocolDisplayUnitAndInstanceAcqItemsInfoTable(id,type){
	if(importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper=null;
	}
//	Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceAcqItemsTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolDisplayInstanceAcqItemsConfigData',
		success:function(response) {
//			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceAcqItemsTablePanel_Id").getEl().unmask();
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper==null || importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot==undefined){
				importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper = PmportProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.createNew("ImportedProtocolDisplayUnitAndInstanceAcqItemsTableDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','数据顺序','实时曲线','历史曲线']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historyCurveConfShowValue'}"
						+"]";
				importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
//			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceAcqItemsTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type
        }
	});
};

var PmportProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper = {};
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot1 = '';
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.divid = divid;
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.colHeaders=[];
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.columns=[];
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.AllData=[];
	        
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.divid);
	        	importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60,85,85],
	                columns:importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==5||visualColIndex==6){
		                	cellProperties.renderer = importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.addCurveBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper;
	    }
};

function CreateImportProtocolDisplayUnitAndInstanceCalItemsInfoTable(id,type){
	if(importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper=null;
	}
//	Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCalItemsTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolDisplayInstanceCalItemsConfigData',
		success:function(response) {
//			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCalItemsTablePanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper==null || importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot==undefined){
				importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper = ImportProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.createNew("ImportedProtocolDisplayUnitAndInstanceCalItemsTableDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','数据顺序','实时曲线','历史曲线']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,protocolDisplayUnitAcqItemsConfigHandsontableHelper);}}," 
						+"{data:'realtimeCurveConfShowValue'},"
						+"{data:'historyCurveConfShowValue'}"
						+"]";
				importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
//			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCalItemsTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type
        }
	});
};

var ImportProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper = {};
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot1 = '';
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.divid = divid;
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.colHeaders=[];
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.columns=[];
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.AllData=[];
	        
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==2){
	            		td.style.backgroundColor = '#'+arr[1];
	            	}
	            }
	        }
	        
	        importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.divid);
	        	importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60,85,85],
	                columns:importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(visualColIndex==5||visualColIndex==6){
		                	cellProperties.renderer = importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.addCurveBg;
		                }
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper;
	    }
};

function CreateImportProtocolDisplayUnitAndInstanceCtrlItemsInfoTable(id,type){
	if(importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper=null;
	}
//	Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCtrlItemsTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolDisplayInstanceCtrlItemsConfigData',
		success:function(response) {
//			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCtrlItemsTablePanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper==null || importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot==undefined){
				importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper = ImportProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.createNew("ImportedProtocolDisplayUnitAndInstanceCtrlItemsTableDiv_id");
				var colHeaders="['序号','名称','单位','显示级别','数据顺序']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
						+"{data:'unit'},"
						+"{data:'showLevel'}," 
						+"{data:'sort'}"
						+"]";
				importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
//			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCtrlItemsTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type
        }
	});
};

var ImportProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper = {};
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot1 = '';
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.divid = divid;
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.colHeaders=[];
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.columns=[];
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.AllData=[];
	        
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.divid);
	        	importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,140,80,60,60],
	                columns:importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper;
	    }
};

function CreateImportProtocolAlarmContentTable(id,type){
	var activeId = Ext.getCmp("ImportProtocolAlarmUnitAndInstanceItemsTabPanel_Id").getActiveTab().id;
	if(activeId=="ImportProtocolAlarmUnitAndInstanceNumItemsTableInfoPanel_Id"){
		CreateImportProtocolAlarmInstanceNumItemsConfigInfoTable(id,type);
		CreateImportProtocolAlarmInstanceCalNumItemsConfigInfoTable(id,type);
	}else if(activeId=="ImportProtocolAlarmUnitAndInstanceSwitchItemsTableInfoPanel_Id"){
		CreateImportProtocolAlarmInstanceSwitchItemsConfigInfoTable(id,type);
	}else if(activeId=="ImportProtocolAlarmUnitAndInstanceEnumItemsTableInfoPanel_Id"){
		CreateImportProtocolAlarmInstanceEnumItemsConfigInfoTable(id,type);
	}else if(activeId=="ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsTableInfoPanel_Id"){
		CreateImportProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(id,type);
	}else if(activeId=="ImportProtocolAlarmUnitAndInstanceRunStatusItemsTableInfoPanel_Id"){
		CreateImportProtocolAlarmInstanceRunStatusItemsConfigInfoTable(id,type);
	}else if(activeId=="ImportProtocolAlarmUnitAndInstanceCommStatusItemsTableInfoPanel_Id"){
		CreateImportProtocolAlarmInstanceCommStatusItemsConfigInfoTable(id,type);
	}

}


function CreateImportProtocolAlarmInstanceNumItemsConfigInfoTable(id,type){
	if(importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportProtocolAlarmUnitAndInstanceNumItemsConfigTableInfoPanel_id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolAlarmContentNumItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceNumItemsConfigTableInfoPanel_id").getEl().unmask();
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper==null || importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot==undefined){
				importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper = ImportProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.createNew("ImportProtocolAlarmUnitAndInstanceNumItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','地址','上限','下限','回差','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
						+"{data:'upperLimit'},"
						+"{data:'lowerLimit'}," 
						+"{data:'hystersis'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type
        }
	});
};

var ImportProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper = {};
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot1 = '';
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.divid = divid;
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.colHeaders=[];
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.columns=[];
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.AllData=[];
	        
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.divid);
	        	importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper;
	    }
};

function CreateImportProtocolAlarmInstanceCalNumItemsConfigInfoTable(id,type){
	if(importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper=null;
	}
	
	Ext.getCmp("ImportProtocolAlarmUnitAndInstanceCalNumItemsConfigTableInfoPanel_id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolAlarmContentCalNumItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceCalNumItemsConfigTableInfoPanel_id").getEl().unmask();
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper==null || importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot==undefined){
				importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper = ImportProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.createNew("ImportProtocolAlarmUnitAndInstanceCalNumItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','上限','下限','回差','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'upperLimit'},"
						+"{data:'lowerLimit'}," 
						+"{data:'hystersis'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']},"
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceCalNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type,
			resolutionMode:5
        }
	});
};

var ImportProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper = {};
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot1 = '';
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.divid = divid;
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.colHeaders=[];
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.columns=[];
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.AllData=[];
	        
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.divid);
	        	importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolAlarmUnitAndInstanceConfigCalNumItemsHandsontableHelper;
	    }
};

function CreateImportProtocolAlarmInstanceSwitchItemsConfigInfoTable(id,type){
	if(importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportProtocolAlarmUnitAndInstanceSwitchItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolAlarmContentSwitchItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceSwitchItemsTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper==null || importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot==undefined){
				importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper = ImportProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.createNew("ImportProtocolAlarmUnitAndInstanceSwitchItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','地址','位','含义','触发状态','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
						+"{data:'bitIndex'},"
						+"{data:'meaning'}," 
						+"{data:'value'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceSwitchItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type,
			resolutionMode:0
        }
	});
};

var ImportProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper = {};
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot1 = '';
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.divid = divid;
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.colHeaders=[];
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.columns=[];
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.AllData=[];
	        
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.divid);
	        	importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolAlarmUnitAndInstanceConfigSwitchItemsHandsontableHelper;
	    }
};

function CreateImportProtocolAlarmInstanceEnumItemsConfigInfoTable(id,type){
	if(importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportProtocolAlarmUnitAndInstanceEnumItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolAlarmContentEnumItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceEnumItemsTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper==null || importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot==undefined){
				importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper = ImportProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.createNew("ImportProtocolAlarmUnitAndInstanceEnumItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','地址','值','含义','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
					 	+"{data:'value'}," 
						+"{data:'meaning'},"
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceEnumItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type,
			resolutionMode:1
        }
	});
};

var ImportProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper = {};
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot1 = '';
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.divid = divid;
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.colHeaders=[];
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.columns=[];
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.AllData=[];
	        
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.divid);
	        	importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80],
	                columns:importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolAlarmUnitAndInstanceConfigEnumItemsHandsontableHelper;
	    }
};

function CreateImportProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(id,type){
	if(importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolAlarmContentFESDiagramResultItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper==null || importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot==undefined){
				importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper = ImportProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.createNew("ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type,
			resolutionMode:4
        }
	});
};

var ImportProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper = {};
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot1 = '';
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.divid = divid;
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders=[];
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.columns=[];
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.AllData=[];
	        
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.divid);
	        	importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolAlarmUnitAndInstanceConfigFESDiagramResultItemsHandsontableHelper;
	    }
};

function CreateImportProtocolAlarmInstanceRunStatusItemsConfigInfoTable(id,type){
	if(importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportProtocolAlarmUnitAndInstanceRunStatusItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolAlarmContentRunStatusItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceRunStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/工况诊断报警项");
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper==null || importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot==undefined){
				importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper = ImportProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.createNew("ImportProtocolAlarmUnitAndInstanceRunStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceRunStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type,
			resolutionMode:6
        }
	});
};

var ImportProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper = {};
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot1 = '';
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.divid = divid;
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.colHeaders=[];
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.columns=[];
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.AllData=[];
	        
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.divid);
	        	importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolAlarmUnitAndInstanceConfigRunStatusItemsHandsontableHelper;
	    }
};

function CreateImportProtocolAlarmInstanceCommStatusItemsConfigInfoTable(id,type){
	if(importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper!=null){
		if(importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined){
			importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot.destroy();
		}
		importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportProtocolAlarmUnitAndInstanceCommStatusItemsTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportProtocolAlarmContentCommStatusItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceCommStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("ModbusProtocolAlarmInstanceItemsConfigTabPanel_Id").setTitle(name+"/工况诊断报警项");
			var result =  Ext.JSON.decode(response.responseText);
			if(importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper==null || importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot==undefined){
				importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper = ImportProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.createNew("ImportProtocolAlarmUnitAndInstanceCommStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','延时(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ImportProtocolAlarmUnitAndInstanceCommStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			type:type,
			resolutionMode:3
        }
	});
};

var ImportProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper = {};
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot1 = '';
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.divid = divid;
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.validresult=true;//数据校验
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.colHeaders=[];
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.columns=[];
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.AllData=[];
	        
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';    
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.divid);
	        	importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                }
	        	});
	        }
	        return importProtocolAlarmUnitAndInstanceConfigCommStatusItemsHandsontableHelper;
	    }
};