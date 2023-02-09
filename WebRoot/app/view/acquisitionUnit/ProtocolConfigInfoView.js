Ext.define('AP.view.acquisitionUnit.ProtocolConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.protocolConfigInfoView',
    layout: "fit",
    id:'protocolConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var ModbusProtocolConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolConfigInfoView');
    	var ModbusProtocolUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolUnitConfigInfoView');
        var ModbusProtocolInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolInstanceConfigInfoView');
    	Ext.apply(me, {
    		items: [{
    			xtype: 'tabpanel',
                id:"ScadaDriverConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: [{
                	title:'协议配置',
                	id:"ScadaDriverModbusProtocolConfigTabPanel_Id",
                	items: [ModbusProtocolConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'单元配置',
                	id:'ScadaDriverModbusUnitConfigTabPanel_Id',
                	items: [ModbusProtocolUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'实例配置',
                	id:'ScadaDriverModbusInstanceConfigTabPanel_Id',
                	items: [ModbusProtocolInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="ScadaDriverModbusProtocolConfigTabPanel_Id"){
                    		var treeGridPanel = Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id");
                            if (isNotVal(treeGridPanel)) {
                            	treeGridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolTreeInfoStore');
                            }
                    	}else if(newCard.id=="ScadaDriverModbusUnitConfigTabPanel_Id"){
                    		var activeId = Ext.getCmp("ModbusProtocolUnitConfigTabPanel_Id").getActiveTab().id;
                    		if(activeId=="ModbusProtocolAcqUnitConfigTabPanel_Id"){
                    			var treePanel=Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
                        		var treePanel=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
                        		var treePanel=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolReportUnitConfigTabPanel_Id"){
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
                        		var treePanel=Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolInstanceTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolDisplayInstanceConfigTabPanel_Id"){
                        		var treePanel=Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolAlarmInstanceConfigTabPanel_Id"){
                        		var treePanel=Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
                        		if(isNotVal(treePanel)){
                        			treePanel.getStore().load();
                        		}else{
                        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceTreeInfoStore');
                        		}
                        	}else if(activeId=="ModbusProtocolSMSInstanceConfigTabPanel_Id"){
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
    				if(protocolConfigAddrMappingItemsHandsontableHelper!=null){
    					if(protocolConfigAddrMappingItemsHandsontableHelper.hot!=undefined){
    						protocolConfigAddrMappingItemsHandsontableHelper.hot.destroy();
    					}
    					protocolConfigAddrMappingItemsHandsontableHelper=null;
    				}
    				if(protocolConfigAddrMaooingPropertiesHandsontableHelper!=null){
    					if(protocolConfigAddrMaooingPropertiesHandsontableHelper.hot!=undefined){
    						protocolConfigAddrMaooingPropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolConfigAddrMaooingPropertiesHandsontableHelper=null;
    				}
    				if(protocolAddrMappingItemsMeaningConfigHandsontableHelper!=null){
    					if(protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot!=undefined){
    						protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot.destroy();
    					}
    					protocolAddrMappingItemsMeaningConfigHandsontableHelper=null;
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
    				if(protocolInstanceConfigItemsHandsontableHelper!=null){
    					if(protocolInstanceConfigItemsHandsontableHelper.hot!=undefined){
    						protocolInstanceConfigItemsHandsontableHelper.hot.destroy();
    					}
    					protocolInstanceConfigItemsHandsontableHelper=null;
    				}
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
    				if(protocolDisplayInstanceAcqItemsHandsontableHelper!=null){
    					if(protocolDisplayInstanceAcqItemsHandsontableHelper.hot!=undefined){
    						protocolDisplayInstanceAcqItemsHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayInstanceAcqItemsHandsontableHelper=null;
    				}
    				if(protocolDisplayInstanceCalItemsHandsontableHelper!=null){
    					if(protocolDisplayInstanceCalItemsHandsontableHelper.hot!=undefined){
    						protocolDisplayInstanceCalItemsHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayInstanceCalItemsHandsontableHelper=null;
    				}
    				if(protocolDisplayInstanceCtrlItemsHandsontableHelper!=null){
    					if(protocolDisplayInstanceCtrlItemsHandsontableHelper.hot!=undefined){
    						protocolDisplayInstanceCtrlItemsHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayInstanceCtrlItemsHandsontableHelper=null;
    				}
    				
    				//报警实例HandsontableHelper资源
    				if(protocolAlarmInstanceConfigNumItemsHandsontableHelper!=null){
    					if(protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmInstanceConfigNumItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmInstanceConfigNumItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmInstanceConfigCalNumItemsHandsontableHelper!=null){
    					if(protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmInstanceConfigCalNumItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmInstanceConfigSwitchItemsHandsontableHelper!=null){
    					if(protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmInstanceConfigSwitchItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmInstanceConfigEnumItemsHandsontableHelper!=null){
    					if(protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmInstanceConfigEnumItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmInstanceConfigEnumItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper!=null){
    					if(protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper!=null){
    					if(protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmInstanceConfigRunStatusItemsHandsontableHelper=null;
    				}
    				if(protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper!=null){
    					if(protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined){
    						protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.destroy();
    					}
    					protocolAlarmInstanceConfigCommStatusItemsHandsontableHelper=null;
    				}
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
    				if(protocolDisplayUnitPropertiesHandsontableHelper!=null){
    					if(protocolDisplayUnitPropertiesHandsontableHelper.hot!=undefined){
    						protocolDisplayUnitPropertiesHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayUnitPropertiesHandsontableHelper=null;
    				}
    				if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper!=null){
    					if(protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot!=undefined){
    						protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.destroy();
    					}
    					protocolDisplayUnitCtrlItemsConfigHandsontableHelper=null;
    				}
    				
    				//报表单元
    				if(reportTemplateHandsontableHelper!=null){
    					if(reportTemplateHandsontableHelper.hot!=undefined){
    						reportTemplateHandsontableHelper.hot.destroy();
    					}
    					reportTemplateHandsontableHelper=null;
    				}
    			},
    			afterrender: function ( panel, eOpts) {}
    		}
    	});
        this.callParent(arguments);
    }
});
