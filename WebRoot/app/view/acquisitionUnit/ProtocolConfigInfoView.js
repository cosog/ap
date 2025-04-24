var loginUserProtocolConfigModuleRight=getRoleModuleRight('DriverManagement');

//协议配置
var protocolItemsConfigHandsontableHelper=null;
var protocolPropertiesHandsontableHelper=null;
var protocolItemsMeaningConfigHandsontableHelper=null;

//采控单元
var protocolAcqUnitConfigItemsHandsontableHelper=null;
var protocolConfigAcqUnitPropertiesHandsontableHelper=null;

//报警单元
var protocolConfigAlarmUnitPropertiesHandsontableHelper=null;
var protocolAlarmUnitConfigNumItemsHandsontableHelper=null;
var protocolAlarmUnitConfigSwitchItemsHandsontableHelper=null;
var protocolAlarmUnitConfigEnumItemsHandsontableHelper=null;
var protocolAlarmUnitConfigCommStatusItemsHandsontableHelper=null;
var protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper=null;
var protocolAlarmUnitConfigCalNumItemsHandsontableHelper=null;
var protocolAlarmUnitConfigRunStatusItemsHandsontableHelper=null;

//显示单元
var protocolDisplayUnitAcqItemsConfigHandsontableHelper=null;
var protocolDisplayUnitCalItemsConfigHandsontableHelper=null;
var protocolDisplayUnitCtrlItemsConfigHandsontableHelper=null;
var protocolDisplayUnitInputItemsConfigHandsontableHelper=null;
var protocolDisplayUnitPropertiesHandsontableHelper=null;

//报表单元
var reportUnitPropertiesHandsontableHelper = null;
var singleWellRangeReportTemplateHandsontableHelper = null;
var singleWellRangeReportTemplateContentHandsontableHelper = null;
var productionReportTemplateHandsontableHelper = null;
var productionReportTemplateContentHandsontableHelper = null;
var singleWellDailyReportTemplateHandsontableHelper = null;
var singleWellDailyReportTemplateContentHandsontableHelper = null;

//采控实例
var protocolConfigInstancePropertiesHandsontableHelper=null;

//显示实例
var protocolDisplayInstancePropertiesHandsontableHelper=null;

//报警实例
var protocolAlarmInstancePropertiesHandsontableHelper=null;

//报表实例
var protocolReportInstancePropertiesHandsontableHelper=null;

Ext.define('AP.view.acquisitionUnit.ProtocolConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.protocolConfigInfoView',
    layout: "fit",
    id:'protocolConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var ModbusProtocolConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolConfigInfoView');
        Ext.create('AP.store.acquisitionUnit.ProtocolConfigDeviceTypeTreeInfoStore');
    	Ext.apply(me, {
    		layout: "border",
    		tbar:[{
            	id: 'ProtocolConfigModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserProtocolConfigModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'ProtocolConfigModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserProtocolConfigModuleRight.editFlag,
                hidden: true
             },{
            	id: 'ProtocolConfigModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserProtocolConfigModuleRight.controlFlag,
                hidden: true
            }],
    		items: [{
            	border: true,
            	region: 'west',
            	width:'15%',
                layout: "border",
                border: true,
                header: false,
                collapsible: true,
                split: true,
                collapseDirection: 'left',
                hideMode:'offsets',
                items: [{
                	region: 'center',
                	title:loginUserLanguageResource.deviceType,
                	layout: 'fit',
                	id:"ProtocolConfigTabTreePanel_Id"
                },{
                	region: 'south',
                	height:'42%',
                	title:loginUserLanguageResource.tabProperties,
                	hidden:true,
                	collapsible: true,
                    split: true,
                	layout: 'fit',
                    html:'<div class="ProtocolConfigTabPropertiesTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProtocolConfigTabPropertiesTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	
                        }
                    }
                }]
            },{
            	region: 'center',
            	header: false,
    			xtype: 'tabpanel',
                id:"ScadaDriverConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: [{
                	title:loginUserLanguageResource.protocolConfig,
                	id:"ScadaDriverModbusProtocolConfigTabPanel_Id",
                	items: [ModbusProtocolConfigInfoView],
    				layout: "fit",
    				iconCls: 'check1',
    				border: false
                },{
                	title:loginUserLanguageResource.unitConfig,
                	id:'ScadaDriverModbusUnitConfigTabPanel_Id',
//                	items: [ModbusProtocolUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:loginUserLanguageResource.instanceConfig,
                	id:'ScadaDriverModbusInstanceConfigTabPanel_Id',
//                	items: [ModbusProtocolInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                }],
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check1');
        				
        				if(newCard.id=="ScadaDriverModbusUnitConfigTabPanel_Id"){
        					var ModbusProtocolUnitConfigInfoView=Ext.getCmp("modbusProtocolUnitConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolUnitConfigInfoView)){
        						ModbusProtocolUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolUnitConfigInfoView');
        						Ext.getCmp("ScadaDriverModbusUnitConfigTabPanel_Id").add(ModbusProtocolUnitConfigInfoView);
        					}
        				}else if(newCard.id=="ScadaDriverModbusInstanceConfigTabPanel_Id"){
        					var ModbusProtocolInstanceConfigInfoView=Ext.getCmp("modbusProtocolInstanceConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolInstanceConfigInfoView)){
        						ModbusProtocolInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolInstanceConfigInfoView');
        						Ext.getCmp("ScadaDriverModbusInstanceConfigTabPanel_Id").add(ModbusProtocolInstanceConfigInfoView);
        					}
        				}
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="ScadaDriverModbusProtocolConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").setValue(0);
                    		Ext.getCmp("ModbusProtocolAddrMappingItemsSelectRow_Id").setValue(0);
                    		
                    		var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
                            if (isNotVal(treeGridPanel)) {
                            	treeGridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore');
                            }
                    	}else if(newCard.id=="ScadaDriverModbusUnitConfigTabPanel_Id"){
                    		var activeId = Ext.getCmp("ModbusProtocolUnitConfigTabPanel_Id").getActiveTab().id;
                    		if(activeId=="ModbusProtocolAcqUnitConfigTabPanel_Id"){
                    			Ext.getCmp("ModbusProtocolAcqUnitProtocolSelectRow_Id").setValue(0);
                    			var treePanel=Ext.getCmp("AcqUnitProtocolTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitProtocolTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
                        		Ext.getCmp("ModbusProtocolAlarmUnitConfigSelectRow_Id").setValue(0);
                        		var treePanel=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
                        		Ext.getCmp("ModbusProtocolDisplayUnitConfigSelectRow_Id").setValue(0);
                        		var treePanel=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolReportUnitConfigTabPanel_Id"){
                        		Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(0);
                        		var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
                                if (isNotVal(treeGridPanel)) {
                                	treeGridPanel.getStore().load();
                                }else{
                                	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
                                }
                        	}
                    	}else if(newCard.id=="ScadaDriverModbusInstanceConfigTabPanel_Id"){
                    		var activeId = Ext.getCmp("ModbusProtocolInstanceConfigTabPanel_Id").getActiveTab().id;
                    		if(activeId=="ModbusProtocolAcqInstanceConfigTabPanel_Id"){
                    			Ext.getCmp("ScadaProtocolModbusInstanceConfigSelectRow_Id").setValue(0);
                    			var treePanel=Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolInstanceTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolDisplayInstanceConfigTabPanel_Id"){
                        		Ext.getCmp("ModbusProtocolDisplayInstanceTreeSelectRow_Id").setValue(0);
                        		var treePanel=Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceConfigTabPanel_Id"){
                        		Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").setValue(0);
                        		var treePanel=Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolSMSInstanceConfigTabPanel_Id"){
                        		Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").setValue(0);
                        		var gridPanel=Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id");
                        		if(isNotVal(gridPanel)){
                        			gridPanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolSMSInstanceStore');
                        		}
                        	}
                    	}
                    }
                }
    		
    		}],
    		listeners: {
    			beforeclose: function ( panel, eOpts) {
    				//地址映射HandsontableHelper资源
    				if(protocolItemsConfigHandsontableHelper!=null){
    					if(protocolItemsConfigHandsontableHelper.hot!=undefined){
    						protocolItemsConfigHandsontableHelper.hot.destroy();
    					}
    					protocolItemsConfigHandsontableHelper=null;
    				}
    				if(protocolPropertiesHandsontableHelper!=null){
    					if(protocolPropertiesHandsontableHelper.hot!=undefined){
    						protocolPropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolPropertiesHandsontableHelper=null;
    				}
    				if(protocolItemsMeaningConfigHandsontableHelper!=null){
    					if(protocolItemsMeaningConfigHandsontableHelper.hot!=undefined){
    						protocolItemsMeaningConfigHandsontableHelper.hot.destroy();
    					}
    					protocolItemsMeaningConfigHandsontableHelper=null;
    				}
    				
    				//采控组HandsontableHelper资源
    				if(protocolAcqUnitConfigItemsHandsontableHelper!=null){
    					if(protocolAcqUnitConfigItemsHandsontableHelper.hot!=undefined){
    						protocolAcqUnitConfigItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAcqUnitConfigItemsHandsontableHelper=null;
    				}
    				if(protocolConfigAcqUnitPropertiesHandsontableHelper!=null){
    					if(protocolConfigAcqUnitPropertiesHandsontableHelper.hot!=undefined){
    						protocolConfigAcqUnitPropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolConfigAcqUnitPropertiesHandsontableHelper=null;
    				}
    				
    				//报警单元HandsontableHelper资源
    				if(protocolConfigAlarmUnitPropertiesHandsontableHelper!=null){
    					if(protocolConfigAlarmUnitPropertiesHandsontableHelper.hot!=undefined){
    						protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolConfigAlarmUnitPropertiesHandsontableHelper=null;
    				}
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
    				if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper!=null){
    					if(protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmUnitConfigSwitchItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmUnitConfigEnumItemsHandsontableHelper!=null){
    					if(protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmUnitConfigEnumItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper!=null){
    					if(protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmUnitConfigCommStatusItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper!=null){
    					if(protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper!=null){
    					if(protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmUnitConfigRunStatusItemsHandsontableHelper=null;
    				}
    				
    				//采控实例HandsontableHelper资源
    				if(protocolConfigInstancePropertiesHandsontableHelper!=null){
    					if(protocolConfigInstancePropertiesHandsontableHelper.hot!=undefined){
    						protocolConfigInstancePropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolConfigInstancePropertiesHandsontableHelper=null;
    				}
    				
    				//显示实例HandsontableHelper资源
    				if(protocolDisplayInstancePropertiesHandsontableHelper!=null){
    					if(protocolDisplayInstancePropertiesHandsontableHelper.hot!=undefined){
    						protocolDisplayInstancePropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayInstancePropertiesHandsontableHelper=null;
    				}
    				
    				//报表实例
    				if(protocolReportInstancePropertiesHandsontableHelper!=null){
    					if(protocolReportInstancePropertiesHandsontableHelper.hot!=undefined){
    						protocolReportInstancePropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolReportInstancePropertiesHandsontableHelper=null;
    				}
    				
    				//报警实例HandsontableHelper资源
    				if(protocolAlarmInstancePropertiesHandsontableHelper!=null){
    					if(protocolAlarmInstancePropertiesHandsontableHelper.hot!=undefined){
    						protocolAlarmInstancePropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmInstancePropertiesHandsontableHelper=null;
    				}
    				
    				//显示单元
    				if(protocolDisplayUnitAcqItemsConfigHandsontableHelper!=null){
    					if(protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot!=undefined){
    						protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayUnitAcqItemsConfigHandsontableHelper=null;
    				}
    				if(protocolDisplayUnitCalItemsConfigHandsontableHelper!=null){
    					if(protocolDisplayUnitCalItemsConfigHandsontableHelper.hot!=undefined){
    						protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayUnitCalItemsConfigHandsontableHelper=null;
    				}
    				if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper!=null){
    					if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot!=undefined){
    						protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayUnitCtrlItemsConfigHandsontableHelper=null;
    				}
    				if(protocolDisplayUnitInputItemsConfigHandsontableHelper!=null){
    					if(protocolDisplayUnitInputItemsConfigHandsontableHelper.hot!=undefined){
    						protocolDisplayUnitInputItemsConfigHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayUnitInputItemsConfigHandsontableHelper=null;
    				}
    				if(protocolDisplayUnitPropertiesHandsontableHelper!=null){
    					if(protocolDisplayUnitPropertiesHandsontableHelper.hot!=undefined){
    						protocolDisplayUnitPropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayUnitPropertiesHandsontableHelper=null;
    				}
    				
    				//报表单元
    				if(singleWellRangeReportTemplateHandsontableHelper!=null){
    					if(singleWellRangeReportTemplateHandsontableHelper.hot!=undefined){
    						singleWellRangeReportTemplateHandsontableHelper.hot.destroy();
    					}
    					singleWellRangeReportTemplateHandsontableHelper=null;
    				}
    				if(singleWellRangeReportTemplateContentHandsontableHelper!=null){
    					if(singleWellRangeReportTemplateContentHandsontableHelper.hot!=undefined){
    						singleWellRangeReportTemplateContentHandsontableHelper.hot.destroy();
    					}
    					singleWellRangeReportTemplateContentHandsontableHelper=null;
    				}
    				if(reportUnitPropertiesHandsontableHelper!=null){
    					if(reportUnitPropertiesHandsontableHelper.hot!=undefined){
    						reportUnitPropertiesHandsontableHelper.hot.destroy();
    					}
    					reportUnitPropertiesHandsontableHelper=null;
    				}
    				if(productionReportTemplateHandsontableHelper!=null){
    					if(productionReportTemplateHandsontableHelper.hot!=undefined){
    						productionReportTemplateHandsontableHelper.hot.destroy();
    					}
    					productionReportTemplateHandsontableHelper=null;
    				}
    				if(productionReportTemplateContentHandsontableHelper!=null){
    					if(productionReportTemplateContentHandsontableHelper.hot!=undefined){
    						productionReportTemplateContentHandsontableHelper.hot.destroy();
    					}
    					productionReportTemplateContentHandsontableHelper=null;
    				}
    				if(singleWellDailyReportTemplateHandsontableHelper!=null){
    					if(singleWellDailyReportTemplateHandsontableHelper.hot!=undefined){
    						singleWellDailyReportTemplateHandsontableHelper.hot.destroy();
    					}
    					singleWellDailyReportTemplateHandsontableHelper=null;
    				}
    				if(singleWellDailyReportTemplateContentHandsontableHelper!=null){
    					if(singleWellDailyReportTemplateContentHandsontableHelper.hot!=undefined){
    						singleWellDailyReportTemplateContentHandsontableHelper.hot.destroy();
    					}
    					singleWellDailyReportTemplateContentHandsontableHelper=null;
    				}
    			},
    			afterrender: function ( panel, eOpts) {}
    		}
    	});
        this.callParent(arguments);
    }
});

function foreachAndSearchTabChildId(rec) {
	var rtnArr=[];
	const recursionTabChildId=function(chlidArray) {
		var ch_length;
		var ch_node = chlidArray.childNodes;
		if (isNotVal(ch_node)) {
			ch_length = ch_node.length;
		} else {
			ch_length = chlidArray.length;
		}
		if (ch_length > 0) {
			if (!Ext.isEmpty(chlidArray)) {
				Ext.Array.each(chlidArray, function(childArrNode, index, fog) {
							var x_node_seId = fog[index].data.deviceTypeId;
							rtnArr.push(x_node_seId);
							// 递归
							if (childArrNode.childNodes != null) {
								recursionTabChildId(childArrNode.childNodes);
							}
						});
			}
		} else {
			if (isNotVal(chlidArray)) {
				var x_node_seId = chlidArray.data.deviceTypeId;
				rtnArr.push(x_node_seId);
			}
		}
	};
	recursionTabChildId(rec);
	return rtnArr.join(",");
};
