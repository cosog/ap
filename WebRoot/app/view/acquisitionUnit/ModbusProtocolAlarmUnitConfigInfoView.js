//var protocolConfigAlarmUnitPropertiesHandsontableHelper=null;
//var protocolAlarmUnitConfigNumItemsHandsontableHelper=null;
//var protocolAlarmUnitConfigSwitchItemsHandsontableHelper=null;
//var protocolAlarmUnitConfigEnumItemsHandsontableHelper=null;
//var protocolAlarmUnitConfigCommStatusItemsHandsontableHelper=null;
//var protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper=null;
//var protocolAlarmUnitConfigCalNumItemsHandsontableHelper=null;
//var protocolAlarmUnitConfigRunStatusItemsHandsontableHelper=null;

Ext.define('AP.view.acquisitionUnit.ModbusProtocolAlarmUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolAlarmUnitConfigInfoView',
    layout: "fit",
    id:'modbusProtocolAlarmUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
            	tbar: [{
                    id: 'ModbusProtocolAlarmUnitConfigSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ModbusProtocolAlarmUnitEnumItemsSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    id: 'ModbusProtocolAlarmUnitSwitchItemsSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: cosog.string.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
                        if (isNotVal(treeGridPanel)) {
                        	treeGridPanel.getStore().load();
                        }else{
                        	Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
                        }
                    }
        		},'->',{
        			xtype: 'button',
                    text: '添加报警单元',
                    disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
                    iconCls: 'add',
                    handler: function (v, o) {
                    	addAlarmUnitInfo();
        			}
        		},"-",{
                	xtype: 'button',
        			text: cosog.string.save,
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'save',
        			handler: function (v, o) {
        				SaveModbusProtocolAlarmUnitConfigTreeData();
        			}
                },"-",{
                	xtype: 'button',
        			text: '报警颜色配置',
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'alarm',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.AlarmColorSelectWindow", {
        			        title: '报警颜色配置'
        			    });
        			    window.show();
        			    getAlarmLevelSetColor();
        			}
                },"-",{
                	xtype: 'button',
        			text: '导出',
        			iconCls: 'export',
        			handler: function (v, o) {
        				var window = Ext.create("AP.view.acquisitionUnit.ExportProtocolAlarmUnitWindow");
                        window.show();
        			}
                },"-",{
                	xtype: 'button',
        			text: '导入',
        			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
        			iconCls: 'import',
        			handler: function (v, o) {
        				var selectedDeviceTypeName="";
        				var selectedDeviceTypeId="";
        				var tabTreeStore = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getStore();
        				var count=tabTreeStore.getCount();
        				var tabTreeSelection = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        				var rec=null;
        				if (tabTreeSelection.length > 0) {
        					rec=tabTreeSelection[0];
        					selectedDeviceTypeName=foreachAndSearchTabAbsolutePath(tabTreeStore.data.items,tabTreeSelection[0].data.deviceTypeId);
        					selectedDeviceTypeId=tabTreeSelection[0].data.deviceTypeId;
        				} else {
        					if(count>0){
        						rec=orgTreeStore.getAt(0);
        						selectedDeviceTypeName=orgTreeStore.getAt(0).data.text;
        						selectedDeviceTypeId=orgTreeStore.getAt(0).data.deviceTypeId;
        					}
        				}
        				var window = Ext.create("AP.view.acquisitionUnit.ImportAlarmUnitWindow");
                        window.show();
        				Ext.getCmp("ImportAlarmUnitWinTabLabel_Id").setHtml("单元将导入到【<font color=red>"+selectedDeviceTypeName+"</font>】标签下,请确认<br/>&nbsp;");
//        			    Ext.getCmp("ImportAlarmUnitWinTabLabel_Id").show();
        			    
        			    Ext.getCmp('ImportAlarmUnitWinDeviceType_Id').setValue(selectedDeviceTypeId);
        				
        			}
                }],
                layout: "border",
                items: [{
                	border: true,
                	region: 'west',
                	width:'25%',
                    layout: "border",
                    border: true,
                    header: false,
                    split: true,
                    collapsible: true,
                    collapseDirection: 'left',
                    hideMode:'offsets',
                    items: [{
                    	region: 'center',
                    	title:'报警单元配置',
                    	layout: 'fit',
                    	id:"ModbusProtocolAlarmUnitConfigPanel_Id"
                    },{
                    	region: 'south',
                    	height:'42%',
                    	title:'属性',
                    	collapsible: true,
                        split: true,
                    	layout: 'fit',
                        html:'<div class="ModbusProtocolAlarmUnitPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmUnitPropertiesTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolConfigAlarmUnitPropertiesHandsontableHelper!=null && protocolConfigAlarmUnitPropertiesHandsontableHelper.hot!=undefined){
//                            		protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    }]
                },{
                	border: true,
//                    flex: 4,
                	region: 'center',
                    title:'报警项配置',
                    xtype: 'tabpanel',
                    id:"ModbusProtocolAlarmUnitItemsConfigTabPanel_Id",
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                    	title:'数据量',
                    	region: 'center',
                		layout: "border",
                		id:"ModbusProtocolAlarmUnitNumItemsConfigTableInfoPanel_Id",
                		iconCls: 'check3',
                		items: [{
                    		region: 'center',
                    		title:'采集项配置',
                            layout: 'fit',
                            id:'ModbusProtocolAlarmUnitItemsConfigTableInfoPanel_id',
                            html:'<div class="ModbusProtocolAlarmUnitItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmUnitItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolAlarmUnitConfigNumItemsHandsontableHelper!=null && protocolAlarmUnitConfigNumItemsHandsontableHelper.hot!=undefined){
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                    	},{
                    		region: 'south',
                        	height:'50%',
                        	title:'计算项配置',
                        	collapsible: true,
                            split: true,
                        	layout: 'fit',
                        	id:'ModbusProtocolAlarmUnitCalNumItemsConfigTableInfoPanel_id',
                            html:'<div class="ModbusProtocolAlarmUnitCalNumItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmUnitCalNumItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper!=null && protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot!=undefined){
//                                		protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                    	}]
                    },{
                    	title:'开关量',
                    	id:"ModbusProtocolAlarmUnitSwitchItemsConfigTableInfoPanel_Id",
                    	layout: "border",
                        border: true,
                        items:[{
                        	region: 'west',
                        	width:'25%',
                        	collapsible: true,
                            split: true,
                            id: 'ModbusProtocolAlarmUnitSwitchItemsPanel_Id',
                        	title:'开关量列表'
                        },{
                        	region: 'center',
                            title:'开关量报警项配置',
                            layout: 'fit',
                            id:'ModbusProtocolAlarmUnitSwitchItemsConfigHandsontablePanel_id',
                            html:'<div class="ModbusProtocolAlarmUnitSwitchItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmUnitSwitchItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper!=null && protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
//                                		protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                        }]
                    },{
                    	title:'枚举量',
                    	id:"ModbusProtocolAlarmUnitEnumItemsConfigTableInfoPanel_Id",
                    	layout: "border",
                        border: true,
                        items:[{
                        	region: 'west',
                        	width:'25%',
                        	collapsible: true,
                            split: true,
                            id: 'ModbusProtocolAlarmUnitEnumItemsPanel_Id',
                        	title:'枚举量列表'
                        },{
                        	region: 'center',
                            title:'枚举量报警项配置',
                            layout: 'fit',
                            id:'ModbusProtocolAlarmUnitEnumItemsConfigHandsontablePanel_id',
                            html:'<div class="ModbusProtocolAlarmUnitEnumItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmUnitEnumItemsConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	if(protocolAlarmUnitConfigEnumItemsHandsontableHelper!=null && protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot!=undefined){
//                                		protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.refreshDimensions();
                                		var newWidth=width;
                                		var newHeight=height;
                                		var header=thisPanel.getHeader();
                                		if(header){
                                			newHeight=newHeight-header.lastBox.height-2;
                                		}
                                		protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.updateSettings({
                                			width:newWidth,
                                			height:newHeight
                                		});
                                	}
                                }
                            }
                        }]
                    },{
                    	title:'工况诊断',
                    	id:"ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        hidden: onlyMonitor,
                        html:'<div class="ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper!=null && protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot!=undefined){
//                            		protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    },{
                    	title:'运行状态',
                    	id:"ModbusProtocolAlarmUnitRunStatusConfigTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="ModbusProtocolAlarmUnitRunStatusItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmUnitRunStatusItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper!=null && protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined){
//                            		protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    },{
                    	title:'通信状态',
                    	id:"ModbusProtocolAlarmUnitCommStatusConfigTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="ModbusProtocolAlarmUnitCommStatusItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolAlarmUnitCommStatusItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper!=null && protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined){
//                            		protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.refreshDimensions();
                            		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                            	}
                            }
                        }
                    }],
                    listeners: {
                    	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
            				oldCard.setIconCls(null);
            				newCard.setIconCls('check3');
            			},
            			tabchange: function (tabPanel, newCard, oldCard, obj) {
                    		if(newCard.id=="ModbusProtocolAlarmUnitNumItemsConfigTableInfoPanel_Id"){
                    			var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
                    			var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
                    			if(selectedItem.data.classes==0){
//                            		if(isNotVal(selectedItem.data.children) && selectedItem.data.children.length>0){
//                            			CreateProtocolAlarmUnitNumItemsConfigInfoTable(selectedItem.data.children[0].text,selectedItem.data.children[0].classes,selectedItem.data.children[0].code);
//                            			CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(selectedItem.data.deviceType,selectedItem.data.children[0].classes,selectedItem.data.children[0].code);
//                            		}
                    				if(protocolAlarmUnitConfigNumItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigNumItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigNumItemsHandsontableHelper=null;
                    				}
                    				if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigCalNumItemsHandsontableHelper=null;
                    				}
                            	}else if(selectedItem.data.classes==1){
                            		CreateProtocolAlarmUnitNumItemsConfigInfoTable(selectedItem.data.text,selectedItem.data.classes,selectedItem.data.code);
                            		CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(selectedItem.data.deviceType,selectedItem.data.classes,selectedItem.data.code);
                            	}else if(selectedItem.data.classes==2||selectedItem.data.classes==3){
                            		CreateProtocolAlarmUnitNumItemsConfigInfoTable(selectedItem.data.protocol,selectedItem.data.classes,selectedItem.data.code);
                            		CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(selectedItem.data.deviceType,selectedItem.data.classes,selectedItem.data.code);
                            	}
                        	}else if(newCard.id=="ModbusProtocolAlarmUnitSwitchItemsConfigTableInfoPanel_Id"){
                        		var treePanel=Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitSwitchItemsStore');
                        		}
                        	}else if(newCard.id=="ModbusProtocolAlarmUnitEnumItemsConfigTableInfoPanel_Id"){
                        		var gridPanel=Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsGridPanel_Id");
                        		if(isNotVal(gridPanel)){
                        			gridPanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitEnumItemsStore');
                        		}
                        	}else if(newCard.id=="ModbusProtocolAlarmUnitCommStatusConfigTableInfoPanel_Id"){
                    			var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
                    			var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
                    			if(selectedItem.data.classes==0){
                    				if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigCommStatusItemsHandsontableHelper=null;
                    				}
                    			}else if(selectedItem.data.classes==1){
                            		CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(selectedItem.data.text,selectedItem.data.classes,selectedItem.data.code);
                            	}else if(selectedItem.data.classes==2||selectedItem.data.classes==3){
                            		CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(selectedItem.data.protocol,selectedItem.data.classes,selectedItem.data.code);
                            	}
                        	}else if(newCard.id=="ModbusProtocolAlarmUnitRunStatusConfigTableInfoPanel_Id"){
                    			var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
                    			var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
                    			if(selectedItem.data.classes==0){
                    				if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigRunStatusItemsHandsontableHelper=null;
                    				}
                    			}else if(selectedItem.data.classes==1){
                            		CreateProtocolAlarmUnitRunStatusItemsConfigInfoTable(selectedItem.data.text,selectedItem.data.classes,selectedItem.data.code);
                            	}else if(selectedItem.data.classes==2||selectedItem.data.classes==3){
                            		CreateProtocolAlarmUnitRunStatusItemsConfigInfoTable(selectedItem.data.protocol,selectedItem.data.classes,selectedItem.data.code);
                            	}
                        	}else if(newCard.id=="ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id"){
                    			var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
                    			var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
                    			if(selectedItem.data.classes==0){
                    				if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper!=null){
                    					if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot!=undefined){
                    						protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.destroy();
                    					}
                    					protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper=null;
                    				}
                    			}else if(selectedItem.data.classes==1){
                            		CreateProtocolAlarmUnitFESDiagramConditionsConfigInfoTable(selectedItem.data.text,selectedItem.data.classes,selectedItem.data.code);
                            	}else if(selectedItem.data.classes==2||selectedItem.data.classes==3){
                            		CreateProtocolAlarmUnitFESDiagramConditionsConfigInfoTable(selectedItem.data.protocol,selectedItem.data.classes,selectedItem.data.code);
                            	}
                        	}
                    	}
                    }
                }]
            }]
    	});
        this.callParent(arguments);
    }
});

function CreateProtocolAlarmUnitNumItemsConfigInfoTable(protocolName,classes,code){
	Ext.getCmp("ModbusProtocolAlarmUnitItemsConfigTableInfoPanel_id").el.mask(cosog.string.updatewait).show();
	Ext.getCmp("ModbusProtocolAddrMappingItemsConfigTabPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getModbusProtocolNumAlarmItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmUnitItemsConfigTableInfoPanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmUnitConfigNumItemsHandsontableHelper==null || protocolAlarmUnitConfigNumItemsHandsontableHelper.hot==undefined){
				protocolAlarmUnitConfigNumItemsHandsontableHelper = ProtocolAlarmUnitConfigNumItemsHandsontableHelper.createNew("ModbusProtocolAlarmUnitItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','地址','上限','下限','回差','延时持续检测(s)','再次触发等待时间(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'title'},"
					 	+"{data:'addr',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}},"
						+"{data:'upperLimit',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}},"
						+"{data:'lowerLimit',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}}," 
						
						+"{data:'hystersis',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}}," 
						+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}}," 
						+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}}," 
						
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
						+"]";
				protocolAlarmUnitConfigNumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmUnitConfigNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmUnitItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ProtocolAlarmUnitConfigNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmUnitConfigNumItemsHandsontableHelper = {};
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.hot1 = '';
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.divid = divid;
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.columns=[];
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolAlarmUnitConfigNumItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolAlarmUnitConfigNumItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolAlarmUnitConfigNumItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolAlarmUnitConfigNumItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolAlarmUnitConfigNumItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        
	        
	        
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmUnitConfigNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmUnitConfigNumItemsHandsontableHelper.divid);
	        	protocolAlarmUnitConfigNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [25,50,120,80,80,80,80,120,130,80,80,90,90],
	                columns:protocolAlarmUnitConfigNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmUnitConfigNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	 var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
	 	                	if(selectRow!=''){
	 	                		var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
	 	                		if(selectedItem.data.classes!=3){
	 	                			cellProperties.readOnly = true;
	 	                			cellProperties.renderer=protocolAlarmUnitConfigNumItemsHandsontableHelper.addReadOnlyBg;
	 	                		}else{
	 	                			if (visualColIndex >=1 && visualColIndex <=3) {
	 	    							cellProperties.readOnly = true;
	 	    							cellProperties.renderer=protocolAlarmUnitConfigNumItemsHandsontableHelper.addReadOnlyBg;
	 	    		                }else{
	 	    		                	if(protocolAlarmUnitConfigNumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		                    	&& protocolAlarmUnitConfigNumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    	                    		cellProperties.renderer = protocolAlarmUnitConfigNumItemsHandsontableHelper.addCellStyle;
		    	                    	}
	 	    		                }
	 	                		}
	 	                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolAlarmUnitConfigNumItemsHandsontableHelper.addReadOnlyBg;
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmUnitConfigNumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                			&& protocolAlarmUnitConfigNumItemsHandsontableHelper!=null
	                			&& protocolAlarmUnitConfigNumItemsHandsontableHelper.hot!=''
	                			&& protocolAlarmUnitConfigNumItemsHandsontableHelper.hot!=undefined 
	                			&& protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.saveData = function () {}
	        protocolAlarmUnitConfigNumItemsHandsontableHelper.clearContainer = function () {
	        	protocolAlarmUnitConfigNumItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAlarmUnitConfigNumItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(deviceType,classes,code){
	Ext.getCmp("ModbusProtocolAlarmUnitCalNumItemsConfigTableInfoPanel_id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getModbusProtocolCalNumAlarmItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmUnitCalNumItemsConfigTableInfoPanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper==null || protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot==undefined){
				protocolAlarmUnitConfigCalNumItemsHandsontableHelper = ProtocolAlarmUnitConfigCalNumItemsHandsontableHelper.createNew("ModbusProtocolAlarmUnitCalNumItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','单位','上限','下限','回差','延时持续检测(s)','再次触发等待时间(s)','报警级别','报警使能','是否发送短信','是否发送邮件','代码']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'title'},{data:'unit'},"
					 	+"{data:'upperLimit',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCalNumItemsHandsontableHelper);}},"
						+"{data:'lowerLimit',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCalNumItemsHandsontableHelper);}}," 
						
						+"{data:'hystersis',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCalNumItemsHandsontableHelper);}}," 
						+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCalNumItemsHandsontableHelper);}}," 
						+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCalNumItemsHandsontableHelper);}}," 
						
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," +
						"{data:'code'}" 
						+"]";
				protocolAlarmUnitConfigCalNumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmUnitConfigCalNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigCalNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigCalNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmUnitCalNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceType:deviceType,
			classes:classes,
			code:code
        }
	});
};

var ProtocolAlarmUnitConfigCalNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmUnitConfigCalNumItemsHandsontableHelper = {};
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot1 = '';
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.divid = divid;
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.columns=[];
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmUnitConfigCalNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmUnitConfigCalNumItemsHandsontableHelper.divid);
	        	protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [13],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [25,50,120,80,80,80,80,120,130,80,80,90,90],
	                columns:protocolAlarmUnitConfigCalNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmUnitConfigCalNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
		                	if(selectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
		                		if(selectedItem.data.classes!=3){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex <=3) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addReadOnlyBg;
		    		                }else{
	 	    		                	if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		                    	&& protocolAlarmUnitConfigCalNumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    	                    		cellProperties.renderer = protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addCellStyle;
		    	                    	}
	 	    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolAlarmUnitConfigCalNumItemsHandsontableHelper.addReadOnlyBg;
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmUnitConfigCalNumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmUnitConfigCalNumItemsHandsontableHelper!=null
	                		&& protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.saveData = function () {}
	        protocolAlarmUnitConfigCalNumItemsHandsontableHelper.clearContainer = function () {
	        	protocolAlarmUnitConfigCalNumItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAlarmUnitConfigCalNumItemsHandsontableHelper;
	    }
};


function CreateProtocolAlarmUnitConfigPropertiesInfoTable(data){
	var root=[];
	if(data.classes==0){
		var item1={};
		item1.id=1;
		item1.title='根节点';
		item1.value='单元列表';
		root.push(item1);
	}if(data.classes==1){
		var item1={};
		item1.id=1;
		item1.title='协议名称';
		item1.value=data.text;
		root.push(item1);
	}else if(data.classes==3){
		var item1={};
		item1.id=1;
		item1.title='单元名称';
		item1.value=data.text;
		root.push(item1);
		
		var item2={};
		item2.id=2;
		item2.title='备注';
		item2.value=data.remark;
		root.push(item2);
	}
	
	if(protocolConfigAlarmUnitPropertiesHandsontableHelper==null || protocolConfigAlarmUnitPropertiesHandsontableHelper.hot==undefined){
		protocolConfigAlarmUnitPropertiesHandsontableHelper = ProtocolConfigAlarmUnitPropertiesHandsontableHelper.createNew("ModbusProtocolAlarmUnitPropertiesTableInfoDiv_id");
		var colHeaders="['序号','名称','变量']";
		var columns="[{data:'id'},{data:'title'},{data:'value'}]";
		protocolConfigAlarmUnitPropertiesHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
		protocolConfigAlarmUnitPropertiesHandsontableHelper.columns=Ext.JSON.decode(columns);
		protocolConfigAlarmUnitPropertiesHandsontableHelper.classes=data.classes;
		protocolConfigAlarmUnitPropertiesHandsontableHelper.createTable(root);
	}else{
		protocolConfigAlarmUnitPropertiesHandsontableHelper.classes=data.classes;
		protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.loadData(root);
	}
};

var ProtocolConfigAlarmUnitPropertiesHandsontableHelper = {
		createNew: function (divid) {
	        var protocolConfigAlarmUnitPropertiesHandsontableHelper = {};
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.hot = '';
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.classes =null;
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.divid = divid;
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.validresult=true;//数据校验
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.colHeaders=[];
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.columns=[];
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.AllData=[];
	        
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolConfigAlarmUnitPropertiesHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolConfigAlarmUnitPropertiesHandsontableHelper.divid);
	        	protocolConfigAlarmUnitPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [2,5,5],
	                columns:protocolConfigAlarmUnitPropertiesHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolConfigAlarmUnitPropertiesHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	if(protocolConfigAlarmUnitPropertiesHandsontableHelper.classes===0 || protocolConfigAlarmUnitPropertiesHandsontableHelper.classes===1){
								cellProperties.readOnly = true;
								cellProperties.renderer = protocolConfigAlarmUnitPropertiesHandsontableHelper.addBoldBg;
			                }else if(protocolConfigAlarmUnitPropertiesHandsontableHelper.classes===3){
			                	if (visualColIndex ==0 || visualColIndex ==1) {
									cellProperties.readOnly = true;
									cellProperties.renderer = protocolConfigAlarmUnitPropertiesHandsontableHelper.addBoldBg;
				                }else{
				                	if(visualColIndex === 2 && visualRowIndex===0){
				                    	this.validator=function (val, callback) {
				                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolConfigAlarmUnitPropertiesHandsontableHelper);
				                    	}
				                    }
				                	cellProperties.renderer = protocolConfigAlarmUnitPropertiesHandsontableHelper.addCellStyle;
				                }
			                }
	                    }else{
	                    	cellProperties.readOnly = true;
							cellProperties.renderer = protocolConfigAlarmUnitPropertiesHandsontableHelper.addBoldBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolConfigAlarmUnitPropertiesHandsontableHelper!=null&&protocolConfigAlarmUnitPropertiesHandsontableHelper.hot!=''&&protocolConfigAlarmUnitPropertiesHandsontableHelper.hot!=undefined && protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.saveData = function () {}
	        protocolConfigAlarmUnitPropertiesHandsontableHelper.clearContainer = function () {
	        	protocolConfigAlarmUnitPropertiesHandsontableHelper.AllData = [];
	        }
	        return protocolConfigAlarmUnitPropertiesHandsontableHelper;
	    }
};

function CreateProtocolAlarmUnitEnumItemsConfigInfoTable(protocolName,classes,unitCode,itemAddr){
	Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsConfigHandsontablePanel_id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getModbusProtocolEnumAlarmItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsConfigHandsontablePanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmUnitConfigEnumItemsHandsontableHelper==null || protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot==undefined){
				protocolAlarmUnitConfigEnumItemsHandsontableHelper = ProtocolAlarmUnitConfigEnumItemsHandsontableHelper.createNew("ModbusProtocolAlarmUnitEnumItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','数值','含义','延时持续检测(s)','再次触发等待时间(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'}," 
					+"{data:'value',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigEnumItemsHandsontableHelper);}}," 
					+"{data:'meaning'},"
					+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigEnumItemsHandsontableHelper);}}," 
					+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigEnumItemsHandsontableHelper);}}," 
					+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
					+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
					+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
					+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
					+"]";
				protocolAlarmUnitConfigEnumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolAlarmUnitConfigEnumItemsHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsConfigHandsontablePanel_id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			unitCode:unitCode,
			itemAddr:itemAddr,
			itemResolutionMode:1
        }
	});
};

var ProtocolAlarmUnitConfigEnumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmUnitConfigEnumItemsHandsontableHelper = {};
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot1 = '';
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.divid = divid;
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns=[];
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolAlarmUnitConfigEnumItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolAlarmUnitConfigEnumItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolAlarmUnitConfigEnumItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmUnitConfigEnumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmUnitConfigEnumItemsHandsontableHelper.divid);
	        	protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [25,50,50,120,120,130,80,80,90,90],
	                columns:protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmUnitConfigEnumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
		                	if(selectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
		                		if(selectedItem.data.classes!=3){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolAlarmUnitConfigEnumItemsHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex <=3) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolAlarmUnitConfigEnumItemsHandsontableHelper.addReadOnlyBg;
		    		                }else{
		    		                	if(protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		    	            	&& protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    		                    	cellProperties.renderer = protocolAlarmUnitConfigEnumItemsHandsontableHelper.addCellStyle;
		    		    	            }
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolAlarmUnitConfigEnumItemsHandsontableHelper.addReadOnlyBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmUnitConfigEnumItemsHandsontableHelper!=null
	                		&& protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.saveData = function () {}
	        protocolAlarmUnitConfigEnumItemsHandsontableHelper.clearContainer = function () {
	        	protocolAlarmUnitConfigEnumItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAlarmUnitConfigEnumItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmUnitSwitchItemsConfigInfoTable(protocolName,classes,unitCode,itemAddr){
	Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsConfigHandsontablePanel_id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getModbusProtocolEnumAlarmItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsConfigHandsontablePanel_id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper==null || protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot==undefined){
				protocolAlarmUnitConfigSwitchItemsHandsontableHelper = ProtocolAlarmUnitConfigSwitchItemsHandsontableHelper.createNew("ModbusProtocolAlarmUnitSwitchItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','位','含义','触发状态','延时持续检测(s)','再次触发等待时间(s)','报警级别','报警使能','是否发送短信','是否发送邮件']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'}," 
					+"{data:'bitIndex',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigSwitchItemsHandsontableHelper);}}," 
					+"{data:'meaning'},"
					+"{data:'value',type:'dropdown',strict:true,allowInvalid:false,source:['开','关']},"
					+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigSwitchItemsHandsontableHelper);}}," 
					+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigSwitchItemsHandsontableHelper);}}," 
					+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
					+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
					+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
					+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}" 
					+"]";
				protocolAlarmUnitConfigSwitchItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigSwitchItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigSwitchItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsConfigHandsontablePanel_id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			unitCode:unitCode,
			itemAddr:itemAddr,
			itemResolutionMode:0
        }
	});
};

var ProtocolAlarmUnitConfigSwitchItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmUnitConfigSwitchItemsHandsontableHelper = {};
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot1 = '';
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.divid = divid;
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns=[];
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmUnitConfigSwitchItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmUnitConfigSwitchItemsHandsontableHelper.divid);
	        	protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [25,50,50,120,80,120,130,80,80,90,90],
	                columns:protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmUnitConfigSwitchItemsHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
		                	if(selectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
		                		if(selectedItem.data.classes!=3){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex <=3) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addReadOnlyBg;
		    		                }else{
		    		                	if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		    	            	&& protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    		                    	cellProperties.renderer = protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addCellStyle;
		    		    	            }
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolAlarmUnitConfigSwitchItemsHandsontableHelper.addReadOnlyBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmUnitConfigSwitchItemsHandsontableHelper!=null
	                		&& protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.saveData = function () {}
	        protocolAlarmUnitConfigSwitchItemsHandsontableHelper.clearContainer = function () {
	        	protocolAlarmUnitConfigSwitchItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAlarmUnitConfigSwitchItemsHandsontableHelper;
	    }
};


function CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(protocolName,classes,code){
	Ext.getCmp("ModbusProtocolAlarmUnitCommStatusConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getModbusProtocolCommStatusAlarmItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmUnitCommStatusConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper==null || protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot==undefined){
				protocolAlarmUnitConfigCommStatusItemsHandsontableHelper = ProtocolAlarmUnitConfigCommStatusItemsHandsontableHelper.createNew("ModbusProtocolAlarmUnitCommStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','延时持续检测(s)','再次触发等待时间(s)','报警级别','报警使能','是否发送短信','是否发送邮件','编码','值']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCommStatusItemsHandsontableHelper);}},"
					 	+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCommStatusItemsHandsontableHelper);}}," 
					 	+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'code'},{data:'value'}" 
						+"]";
				protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmUnitCommStatusConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ProtocolAlarmUnitConfigCommStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmUnitConfigCommStatusItemsHandsontableHelper = {};
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot1 = '';
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.divid = divid;
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns=[];
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.divid);
	        	protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [9,10],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [25,50,80,120,130,80,80,80,80],
	                columns:protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
		                	if(selectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
		                		if(selectedItem.data.classes!=3){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex <=2) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addReadOnlyBg;
		    		                }else{
		    		                	if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		    	            	&& protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    		                    	cellProperties.renderer = protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addCellStyle;
		    		    	            }
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.addReadOnlyBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmUnitConfigCommStatusItemsHandsontableHelper!=null
	                		&& protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.saveData = function () {}
	        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.clearContainer = function () {
	        	protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAlarmUnitConfigCommStatusItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmUnitFESDiagramConditionsConfigInfoTable(protocolName,classes,code){
	Ext.getCmp("ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getModbusProtocolFESDiagramConditionsAlarmItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper==null || protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot==undefined){
				protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = ProtocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.createNew("ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','延时持续检测(s)','再次触发等待时间(s)','报警级别','报警使能','是否发送短信','是否发送邮件','编码']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
					 	+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}}," 
					 	+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'code'}"
						+"]";
				protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ProtocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = {
		createNew: function (divid) {
			var protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = {};
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot1 = '';
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.divid = divid;
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns=[];
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.divid);
	        	protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [9],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [25,50,80,120,130,80,80,80,80],
	                columns:protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
		                	if(selectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
		                		if(selectedItem.data.classes!=3){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex <=2) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addReadOnlyBg;
		    		                }else{
		    		                	if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		    	            	&& protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    		                    	cellProperties.renderer = protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addCellStyle;
		    		    	            }
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.addReadOnlyBg;
	                    }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper!=null
	                		&& protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.saveData = function () {}
	        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.clearContainer = function () {
	        	protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper;
	    }
};

function CreateProtocolAlarmUnitRunStatusItemsConfigInfoTable(protocolName,classes,code){
	Ext.getCmp("ModbusProtocolAlarmUnitRunStatusConfigTableInfoPanel_Id").el.mask(cosog.string.updatewait).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getModbusProtocolRunStatusAlarmItemsConfigData',
		success:function(response) {
			Ext.getCmp("ModbusProtocolAlarmUnitRunStatusConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper==null || protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot==undefined){
				protocolAlarmUnitConfigRunStatusItemsHandsontableHelper = ProtocolAlarmUnitConfigRunStatusItemsHandsontableHelper.createNew("ModbusProtocolAlarmUnitRunStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['','序号','名称','延时持续检测(s)','再次触发等待时间(s)','报警级别','报警使能','是否发送短信','是否发送邮件','编码','值']";
				var columns="[{data:'checked',type:'checkbox'},{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigRunStatusItemsHandsontableHelper);}},"
					 	+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigRunStatusItemsHandsontableHelper);}}," 
					 	+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['正常','一级报警','二级报警','三级报警']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['使能','失效']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['是','否']},"
						+"{data:'code'},{data:'value'}" 
						+"]";
				protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.createTable(result.totalRoot);
			}else{
				protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("ModbusProtocolAlarmUnitRunStatusConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			protocolName:protocolName,
			classes:classes,
			code:code
        }
	});
};

var ProtocolAlarmUnitConfigRunStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var protocolAlarmUnitConfigRunStatusItemsHandsontableHelper = {};
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot1 = '';
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.divid = divid;
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.validresult=true;//数据校验
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.colHeaders=[];
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns=[];
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.AllData=[];
	        
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns[col].type=='checkbox'){
	        		protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns[col].type=='dropdown'){
	        		protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}else{
	        		protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
	        	}
	        }
	        
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	        }
	        
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.DropdownRenderer.apply(this, arguments);//CheckboxRenderer TextRenderer NumericRenderer
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(245, 245, 245)';
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.divid);
	        	protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [9,10],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	        		colWidths: [25,50,80,120,130,80,80,80,80],
	                columns:protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    var protocolConfigModuleEditFlag=parseInt(Ext.getCmp("ProtocolConfigModuleEditFlag").getValue());
	                    if(protocolConfigModuleEditFlag==1){
	                    	var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
		                	if(selectRow!=''){
		                		var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
		                		if(selectedItem.data.classes!=3){
		                			cellProperties.readOnly = true;
		                			cellProperties.renderer=protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addReadOnlyBg;
		                		}else{
		                			if (visualColIndex >=1 && visualColIndex <=2) {
		    							cellProperties.readOnly = true;
		    							cellProperties.renderer=protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addReadOnlyBg;
		    		                }else{
		    		                	if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    		    	            	&& protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		    		                    	cellProperties.renderer = protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addCellStyle;
		    		    	            }
		    		                }
		                		}
		                	}
	                    }else{
	                    	cellProperties.readOnly = true;
	                    	cellProperties.renderer=protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.addReadOnlyBg;
	                    }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& protocolAlarmUnitConfigRunStatusItemsHandsontableHelper!=null
	                		&& protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=''
	                		&& protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined 
	                		&& protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        //保存数据
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.saveData = function () {}
	        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.clearContainer = function () {
	        	protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.AllData = [];
	        }
	        return protocolAlarmUnitConfigRunStatusItemsHandsontableHelper;
	    }
};

function SaveModbusProtocolAlarmUnitConfigTreeData(){
	var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").getValue();
	if(selectRow!=''){
		var selectedItem=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
		var propertiesData=protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.getData();
		var alarmItemsData = protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.getData();
		var calAlarmItemsData=null;
		if(selectedItem.data.classes==3){//选中的是报警单元
			var saveData={};
			saveData.id=selectedItem.data.id;
			saveData.unitCode=selectedItem.data.code;
			saveData.oldUnitName=selectedItem.data.text;
			saveData.protocol=selectedItem.data.protocol;
			saveData.unitName=propertiesData[0][2];
			saveData.remark=propertiesData[1][2];
			var activeId = Ext.getCmp("ModbusProtocolAlarmUnitItemsConfigTabPanel_Id").getActiveTab().id;
			if(activeId=="ModbusProtocolAlarmUnitNumItemsConfigTableInfoPanel_Id"){
				saveData.resolutionMode=2;
				alarmItemsData = protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.getData();
				calAlarmItemsData = protocolAlarmUnitConfigCalNumItemsHandsontableHelper.hot.getData();
        	}else if(activeId=="ModbusProtocolAlarmUnitSwitchItemsConfigTableInfoPanel_Id"){
        		saveData.resolutionMode=0;
        		alarmItemsData = protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.getData();
        		
        		var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsSelectRow_Id").getValue();
				var gridStore=Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsGridPanel_Id").getStore();
				if(gridStore.getCount()>0){
					var selectedItem=gridStore.getAt(selectRow);
					saveData.alarmItemName=selectedItem.data.title;
					saveData.alarmItemAddr=selectedItem.data.addr;
				}
        	}else if(activeId=="ModbusProtocolAlarmUnitEnumItemsConfigTableInfoPanel_Id"){
        		saveData.resolutionMode=1;
        		alarmItemsData = protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.getData();
        		
        		var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsSelectRow_Id").getValue();
				var gridStore=Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsGridPanel_Id").getStore();
				if(gridStore.getCount()>0){
					var selectedItem=gridStore.getAt(selectRow);
					saveData.alarmItemName=selectedItem.data.title;
					saveData.alarmItemAddr=selectedItem.data.addr;
				}
        	}else if(activeId=="ModbusProtocolAlarmUnitCommStatusConfigTableInfoPanel_Id"){
        		saveData.resolutionMode=3;
        		alarmItemsData = protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.getData();
        	}else if(activeId=="ModbusProtocolAlarmUnitRunStatusConfigTableInfoPanel_Id"){
        		saveData.resolutionMode=6;
        		alarmItemsData = protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.getData();
        	}else if(activeId=="ModbusProtocolAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id"){
        		saveData.resolutionMode=4;
        		alarmItemsData = protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.getData();
        	}
			saveData.alarmItems=[];
			Ext.Array.each(alarmItemsData, function (name, index, countriesItSelf) {
				var checked=(alarmItemsData[index][0]+'')==='true';
				if(checked){
					var item={};
					if(saveData.resolutionMode==2){//数据量
						item.itemName=alarmItemsData[index][2];
						item.itemAddr=parseInt(alarmItemsData[index][3]);
						item.upperLimit=alarmItemsData[index][4];
						item.lowerLimit=alarmItemsData[index][5];
						item.hystersis=alarmItemsData[index][6];
						item.delay=alarmItemsData[index][7];
						item.retriggerTime=alarmItemsData[index][8];
						item.alarmLevel=alarmItemsData[index][9];
						item.alarmSign=alarmItemsData[index][10];
						item.isSendMessage=alarmItemsData[index][11];
						item.isSendMail=alarmItemsData[index][12];
						item.type=saveData.resolutionMode;
					}else if(saveData.resolutionMode==0){//开关量
						var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsSelectRow_Id").getValue();
						var gridStore=Ext.getCmp("ModbusProtocolAlarmUnitSwitchItemsGridPanel_Id").getStore();
						if(gridStore.getCount()>0){
							var selectedItem=gridStore.getAt(selectRow);
							item.bitIndex=alarmItemsData[index][2];
							item.itemName=selectedItem.data.title;
							item.itemAddr=selectedItem.data.addr;
							if(alarmItemsData[index][4]=='开'){
								item.value=1;
							}if(alarmItemsData[index][4]=='关'){
								item.value=0;
							}
							item.delay=alarmItemsData[index][5];
							item.retriggerTime=alarmItemsData[index][6];
							item.alarmLevel=alarmItemsData[index][7];
							item.alarmSign=alarmItemsData[index][8];
							item.isSendMessage=alarmItemsData[index][9];
							item.isSendMail=alarmItemsData[index][10];
							item.type=saveData.resolutionMode;
						}
					}else if(saveData.resolutionMode==1){//枚举量
						var selectRow= Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsSelectRow_Id").getValue();
						var gridStore=Ext.getCmp("ModbusProtocolAlarmUnitEnumItemsGridPanel_Id").getStore();
						if(gridStore.getCount()>0){
							var selectedItem=gridStore.getAt(selectRow);
							item.itemName=selectedItem.data.title;
							item.itemAddr=selectedItem.data.addr;
							item.value=alarmItemsData[index][2];
							item.delay=alarmItemsData[index][4];
							item.retriggerTime=alarmItemsData[index][5];
							item.alarmLevel=alarmItemsData[index][6];
							item.alarmSign=alarmItemsData[index][7];
							item.isSendMessage=alarmItemsData[index][8];
							item.isSendMail=alarmItemsData[index][9];
							item.type=saveData.resolutionMode;
						}
					}else if(saveData.resolutionMode==3){//通信状态
						item.itemName=alarmItemsData[index][2];
						item.delay=alarmItemsData[index][3];
						item.retriggerTime=alarmItemsData[index][4];
						item.alarmLevel=alarmItemsData[index][5];
						item.alarmSign=alarmItemsData[index][6];
						item.isSendMessage=alarmItemsData[index][7];
						item.isSendMail=alarmItemsData[index][8];
						item.itemCode=alarmItemsData[index][9];
						item.value=alarmItemsData[index][10];
						item.type=saveData.resolutionMode;
					}else if(saveData.resolutionMode==6){//运行状态
						item.itemName=alarmItemsData[index][2];
						item.delay=alarmItemsData[index][3];
						item.retriggerTime=alarmItemsData[index][4];
						item.alarmLevel=alarmItemsData[index][5];
						item.alarmSign=alarmItemsData[index][6];
						item.isSendMessage=alarmItemsData[index][7];
						item.isSendMail=alarmItemsData[index][8];
						item.itemCode=alarmItemsData[index][9];
						item.value=alarmItemsData[index][10];
						item.type=saveData.resolutionMode;
					}else if(saveData.resolutionMode==4){//功图工况
						item.itemName=alarmItemsData[index][2];
						item.delay=alarmItemsData[index][3];
						item.retriggerTime=alarmItemsData[index][4];
						item.alarmLevel=alarmItemsData[index][5];
						item.alarmSign=alarmItemsData[index][6];
						item.isSendMessage=alarmItemsData[index][7];
						item.isSendMail=alarmItemsData[index][8];
						item.itemCode=alarmItemsData[index][9];
						item.value=alarmItemsData[index][9];
						item.type=saveData.resolutionMode;
					}
					saveData.alarmItems.push(item);
				}
			});
			
			//计算项
			if(calAlarmItemsData!=null){
				Ext.Array.each(calAlarmItemsData, function (name, index, countriesItSelf) {
					var checked=(calAlarmItemsData[index][0]+'')==='true';
					if(checked){
						var item={};
						item.itemName=calAlarmItemsData[index][2];
						item.upperLimit=calAlarmItemsData[index][4];
						item.lowerLimit=calAlarmItemsData[index][5];
						item.hystersis=calAlarmItemsData[index][6];
						item.delay=calAlarmItemsData[index][7];
						item.retriggerTime=calAlarmItemsData[index][8];
						item.alarmLevel=calAlarmItemsData[index][9];
						item.alarmSign=calAlarmItemsData[index][10];
						item.isSendMessage=calAlarmItemsData[index][11];
						item.isSendMail=calAlarmItemsData[index][12];
						item.itemCode=calAlarmItemsData[index][13];
						item.type=5;
						saveData.alarmItems.push(item);
					}
				});
			}
			
			SaveModbusProtocolAlarmUnitConfigData(saveData);
		}
	}
};

function SaveModbusProtocolAlarmUnitConfigData(saveData){
	Ext.Ajax.request({
		method:'POST',
		async :  false,
		url:context + '/acquisitionUnitManagerController/saveModbusProtocolAlarmUnitData',
		success:function(response) {
			data=Ext.JSON.decode(response.responseText);
			if (data.success) {
				Ext.MessageBox.alert("信息","保存成功");
				if(saveData.delidslist!=undefined && saveData.delidslist.length>0){
					Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").setValue(0);
				}
				Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().load();
            	
            } else {
            	Ext.MessageBox.alert("信息","采控单元数据保存失败");
            }
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			data: JSON.stringify(saveData),
        }
	});
};

function createModbusProtocolAddrMappingEnumOrSwitchItemsColumn(columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};
