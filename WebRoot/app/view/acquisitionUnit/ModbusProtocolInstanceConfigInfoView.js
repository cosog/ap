Ext.define('AP.view.acquisitionUnit.ModbusProtocolInstanceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolInstanceConfigInfoView',
    layout: "fit",
    id:'modbusProtocolInstanceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var ModbusProtocolAcqInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAcqInstanceConfigInfoView');
//    	var ModbusProtocolDisplayInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolDisplayInstanceConfigInfoView');
//    	var ModbusProtocolAlarmInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAlarmInstanceConfigInfoView');
//    	var ModbusProtocolSMSInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolSMSInstanceConfigInfoView');
//    	var ModbusProtocolReportInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportInstanceConfigInfoView');
    	Ext.apply(me, {
    		items: [{
    			xtype: 'tabpanel',
                id:"ModbusProtocolInstanceConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'left',
                items: [{
                	title:loginUserLanguageResource.acqInstance,
                	id:"ModbusProtocolAcqInstanceConfigTabPanel_Id",
                	items: [ModbusProtocolAcqInstanceConfigInfoView],
                	iconCls: 'check2',
    				layout: "fit",
    				border: false
                },{
                	title:loginUserLanguageResource.displayInstance,
                	id:'ModbusProtocolDisplayInstanceConfigTabPanel_Id',
//                	items: [ModbusProtocolDisplayInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:loginUserLanguageResource.alarmInstance,
                	id:'ModbusProtocolAlarmInstanceConfigTabPanel_Id',
//                	items: [ModbusProtocolAlarmInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:loginUserLanguageResource.reportInstance,
                	id:'ModbusProtocolReportInstanceConfigTabPanel_Id',
//                	items: [ModbusProtocolReportInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:loginUserLanguageResource.SMSInstance,
                	id:'ModbusProtocolSMSInstanceConfigTabPanel_Id',
//                	items: [ModbusProtocolSMSInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                }],
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check2');
        				
        				if(newCard.id=="ModbusProtocolDisplayInstanceConfigTabPanel_Id"){
        					var ModbusProtocolDisplayInstanceConfigInfoView=Ext.getCmp("modbusProtocolDisplayInstanceConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolDisplayInstanceConfigInfoView)){
        						ModbusProtocolDisplayInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolDisplayInstanceConfigInfoView');
        						Ext.getCmp("ModbusProtocolDisplayInstanceConfigTabPanel_Id").add(ModbusProtocolDisplayInstanceConfigInfoView);
        					}
        				}else if(newCard.id=="ModbusProtocolAlarmInstanceConfigTabPanel_Id"){
        					var ModbusProtocolAlarmInstanceConfigInfoView=Ext.getCmp("modbusProtocolAlarmInstanceConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolAlarmInstanceConfigInfoView)){
        						ModbusProtocolAlarmInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAlarmInstanceConfigInfoView');
        						Ext.getCmp("ModbusProtocolAlarmInstanceConfigTabPanel_Id").add(ModbusProtocolAlarmInstanceConfigInfoView);
        					}
        				}else if(newCard.id=="ModbusProtocolSMSInstanceConfigTabPanel_Id"){
        					var ModbusProtocolSMSInstanceConfigInfoView=Ext.getCmp("modbusProtocolSMSInstanceConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolSMSInstanceConfigInfoView)){
        						ModbusProtocolSMSInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolSMSInstanceConfigInfoView');
        						Ext.getCmp("ModbusProtocolSMSInstanceConfigTabPanel_Id").add(ModbusProtocolSMSInstanceConfigInfoView);
        					}
        				}else if(newCard.id=="ModbusProtocolReportInstanceConfigTabPanel_Id"){
        					var ModbusProtocolReportInstanceConfigInfoView=Ext.getCmp("modbusProtocolReportInstanceConfigInfoViewId");
        					if(!isNotVal(ModbusProtocolReportInstanceConfigInfoView)){
        						ModbusProtocolReportInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportInstanceConfigInfoView');
        						Ext.getCmp("ModbusProtocolReportInstanceConfigTabPanel_Id").add(ModbusProtocolReportInstanceConfigInfoView);
        					}
        				}
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="ModbusProtocolAcqInstanceConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolAcqInstanceProtocolSelectRow_Id").setValue(0);
                        	var treePanel=Ext.getCmp("AcqInstanceProtocolTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAcqInstanceProtocolTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolDisplayInstanceConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolDisplayInstanceProtocolSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceProtocolTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolAlarmInstanceConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolAlarmInstanceProtocolSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("AlarmInstanceProtocolTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceProtocolTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolSMSInstanceConfigTabPanel_Id"){
                    		var gridPanel=Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id");
                    		if(isNotVal(gridPanel)){
                    			gridPanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolSMSInstanceStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolReportInstanceConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolReportInstanceTreeSelectRow_Id").setValue(0);
                    		Ext.getCmp("ReportInstanceTreeSelectInstanceId_Id").setValue(0);
                    		var gridPanel=Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id");
                    		if(isNotVal(gridPanel)){
                    			gridPanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportInstanceTreeInfoStore');
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
