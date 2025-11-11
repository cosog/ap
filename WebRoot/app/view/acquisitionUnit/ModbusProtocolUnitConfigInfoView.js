Ext.define('AP.view.acquisitionUnit.ModbusProtocolUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolUnitConfigInfoView',
    layout: "fit",
    id:'modbusProtocolUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var ModbusProtocolAcqUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAcqUnitConfigInfoView');
//    	var ModbusProtocolAlarmUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAlarmUnitConfigInfoView');
//    	var ModbusProtocolDisplayUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolDisplayUnitConfigInfoView');
//    	var ModbusProtocolReportUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportUnitConfigInfoView');
    	Ext.apply(me, {
    		items: [{
    			xtype: 'tabpanel',
                id:"ModbusProtocolUnitConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'left',
                tbar:[{
                    id: 'UnitConfigProtocolSelectCode_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }],
                items: [{
                	title:loginUserLanguageResource.acqUnit,
                	id:'ModbusProtocolAcqUnitConfigTabPanel_Id',
                	items: [ModbusProtocolAcqUnitConfigInfoView],
    				layout: "fit",
    				iconCls: 'check2',
    				border: false
                },{
                	title:loginUserLanguageResource.displayUnit,
                	id:'ModbusProtocolDisplayUnitConfigTabPanel_Id',
//                	items: [ModbusProtocolDisplayUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:loginUserLanguageResource.alarmUnit,
                	id:'ModbusProtocolAlarmUnitConfigTabPanel_Id',
//                	items: [ModbusProtocolAlarmUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:loginUserLanguageResource.reportUnit,
                	id:'ModbusProtocolReportUnitConfigTabPanel_Id',
//                	items: [ModbusProtocolReportUnitConfigInfoView],
    				layout: "fit",
    				border: false
                }],
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check2');
        				
        				if(newCard.id=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
        					var ModbusProtocolAlarmUnitConfigInfoView=Ext.getCmp("modbusProtocolAlarmUnitConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolAlarmUnitConfigInfoView)){
        						ModbusProtocolAlarmUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAlarmUnitConfigInfoView');
        						Ext.getCmp("ModbusProtocolAlarmUnitConfigTabPanel_Id").add(ModbusProtocolAlarmUnitConfigInfoView);
        					}
        				}else if(newCard.id=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
        					var ModbusProtocolDisplayUnitConfigInfoView=Ext.getCmp("modbusProtocolDisplayUnitConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolDisplayUnitConfigInfoView)){
        						ModbusProtocolDisplayUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolDisplayUnitConfigInfoView');
        						Ext.getCmp("ModbusProtocolDisplayUnitConfigTabPanel_Id").add(ModbusProtocolDisplayUnitConfigInfoView);
        					}
        				}else if(newCard.id=="ModbusProtocolReportUnitConfigTabPanel_Id"){
        					var ModbusProtocolReportUnitConfigInfoView=Ext.getCmp("modbusProtocolReportUnitConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolReportUnitConfigInfoView)){
        						ModbusProtocolReportUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportUnitConfigInfoView');
        						Ext.getCmp("ModbusProtocolReportUnitConfigTabPanel_Id").add(ModbusProtocolReportUnitConfigInfoView);
        					}
        				}
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="ModbusProtocolAcqUnitConfigTabPanel_Id"){
//                    		Ext.getCmp("ModbusProtocolAcqUnitProtocolSelectRow_Id").setValue(0);
                			var treePanel=Ext.getCmp("AcqUnitProtocolTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqUnitProtocolTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
//                    		Ext.getCmp("ModbusProtocolAlarmUnitProtocolSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("AlarmUnitProtocolTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitProtocolTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
//                    		Ext.getCmp("ModbusProtocolDisplayUnitProtocolSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("DisplayUnitProtocolTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitProtocolTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolReportUnitConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolReportUnitConfigSelectRow_Id").setValue(0);
                    		Ext.getCmp("ReportUnitTreeSelectUnitId_Id").setValue(0);
                    		var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
                            if (isNotVal(treeGridPanel)) {
                            	treeGridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
                            }
                    	}
                    },afterrender: function (comp,eOpts) {
                    	
                    }
                }
    		}]
    	});
        this.callParent(arguments);
    }
});
