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
        	    }]
    		},'->',{
    	    	xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: function (v, o) {
                	
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
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function submitImportedProtocolFile() {
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

function CreateImportProtocolContentInfoTable(id,classes,type){
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
//                        		importProtocolAlarmUnitAndInstanceConfigNumItemsHandsontableHelper.hot.refreshDimensions();
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
//            		var selectRow= Ext.getCmp("ImportProtocolAlarmUnitAndInstanceTreeSelectRow_Id").getValue();
//        			var selectedItem=Ext.getCmp("ImportProtocolAlarmUnitAndInstanceConfigTreeGridPanel_Id").getStore().getAt(selectRow);
//            		if(newCard.id=="ImportProtocolAlarmUnitAndInstanceNumItemsTableInfoPanel_Id"){
//            			if(selectedItem.data.classes==0){
//                    		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
//                    			CreateProtocolAlarmInstanceNumItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
//                    			CreateProtocolAlarmInstanceCalNumItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes,selectedItem.data.children[0].deviceType);
//                    		}else{
//                    			CreateProtocolAlarmInstanceNumItemsConfigInfoTable(-1,'',1);
//                    			CreateProtocolAlarmInstanceCalNumItemsConfigInfoTable(-1,'',1,selectedItem.data.deviceType);
//                    		}
//                    	}else{
//                    		CreateProtocolAlarmInstanceNumItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
//                    		CreateProtocolAlarmInstanceCalNumItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes,selectedItem.data.deviceType);
//                    	}
//                	}else if(newCard.id=="ImportProtocolAlarmUnitAndInstanceSwitchItemsTableInfoPanel_Id"){
//                		if(selectedItem.data.classes==0){
//                    		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
//                    			CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
//                    		}else{
//                    			CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(-1,'',1);
//                    		}
//                    	}else{
//                    		CreateProtocolAlarmInstanceSwitchItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
//                    	}
//                	}else if(newCard.id=="ImportProtocolAlarmUnitAndInstanceEnumItemsTableInfoPanel_Id"){
//                		if(selectedItem.data.classes==0){
//                    		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
//                    			CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
//                    		}else{
//                    			CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(-1,'',1);
//                    		}
//                    	}else{
//                    		CreateProtocolAlarmInstanceEnumItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
//                    	}
//                	}else if(newCard.id=="ImportProtocolAlarmUnitAndInstanceFESDiagramResultItemsTableInfoPanel_Id"){
//                		if(selectedItem.data.classes==0){
//                    		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
//                    			CreateProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
//                    		}else{
//                    			CreateProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(-1,'',1);
//                    		}
//                    	}else{
//                    		CreateProtocolAlarmInstanceFESDiagramResultItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
//                    	}
//                	}else if(newCard.id=="ImportProtocolAlarmUnitAndInstanceRunStatusItemsTableInfoPanel_Id"){
//                		if(selectedItem.data.classes==0){
//                    		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
//                    			CreateProtocolAlarmInstanceRunStatusItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
//                    		}else{
//                    			CreateProtocolAlarmInstanceRunStatusItemsConfigInfoTable(-1,'',1);
//                    		}
//                    	}else{
//                    		CreateProtocolAlarmInstanceRunStatusItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
//                    	}
//                	}else if(newCard.id=="ImportProtocolAlarmUnitAndInstanceCommStatusItemsTableInfoPanel_Id"){
//                		if(selectedItem.data.classes==0){
//                    		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
//                    			CreateProtocolAlarmInstanceCommStatusItemsConfigInfoTable(selectedItem.data.children[0].id,selectedItem.data.children[0].text,selectedItem.data.children[0].classes);
//                    		}else{
//                    			CreateProtocolAlarmInstanceCommStatusItemsConfigInfoTable(-1,'',1);
//                    		}
//                    	}else{
//                    		CreateProtocolAlarmInstanceCommStatusItemsConfigInfoTable(selectedItem.data.id,selectedItem.data.text,selectedItem.data.classes);
//                    	}
//                	}
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
		CreateImportProtocolDisplayUnitAndInstanceAcqItemsInfoTable(id);
		CreateImportProtocolDisplayUnitAndInstanceCalItemsInfoTable(id);
		CreateImportProtocolDisplayUnitAndInstanceCtrlItemsInfoTable(id);
	}else if(type==2 || type==5){
		
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

function CreateImportProtocolDisplayUnitAndInstanceAcqItemsInfoTable(id,instanceName,classes){
	if(importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceAcqItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceAcqItemsTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayInstanceAcqItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceAcqItemsTablePanel_Id").getEl().unmask();
			
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
			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceAcqItemsTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes
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

function CreateImportProtocolDisplayUnitAndInstanceCalItemsInfoTable(id,instanceName,classes,deviceType){
	if(importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceCalItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCalItemsTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayInstanceCalItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCalItemsTablePanel_Id").getEl().unmask();
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
			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCalItemsTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes,
			deviceType:deviceType
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

function CreateImportProtocolDisplayUnitAndInstanceCtrlItemsInfoTable(id,instanceName,classes){
	if(importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper!=null){
		if(importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot!=undefined){
			importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper.hot.destroy();
		}
		importProtocolDisplayUnitAndInstanceCtrlItemsHandsontableHelper=null;
	}
	Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCtrlItemsTablePanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getProtocolDisplayInstanceCtrlItemsConfigData',
		success:function(response) {
			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCtrlItemsTablePanel_Id").getEl().unmask();
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
			Ext.getCmp("ImportedProtocolDisplayUnitAndInstanceCtrlItemsTablePanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			id:id,
			classes:classes
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
