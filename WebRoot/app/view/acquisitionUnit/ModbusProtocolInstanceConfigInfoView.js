Ext.define('AP.view.acquisitionUnit.ModbusProtocolInstanceConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolInstanceConfigInfoView',
    layout: "fit",
    id:'modbusProtocolInstanceConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var ModbusProtocolAcqInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAcqInstanceConfigInfoView');
    	var ModbusProtocolDisplayInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolDisplayInstanceConfigInfoView');
    	var ModbusProtocolAlarmInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAlarmInstanceConfigInfoView');
    	var ModbusProtocolSMSInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolSMSInstanceConfigInfoView');
    	var ModbusProtocolReportInstanceConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportInstanceConfigInfoView');
    	Ext.apply(me, {
    		items: [{
    			xtype: 'tabpanel',
                id:"ModbusProtocolInstanceConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'left',
                items: [{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">采控实例</div>',
                	id:"ModbusProtocolAcqInstanceConfigTabPanel_Id",
                	items: [ModbusProtocolAcqInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">显示实例</div>',
                	id:'ModbusProtocolDisplayInstanceConfigTabPanel_Id',
                	items: [ModbusProtocolDisplayInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">报警实例</div>',
                	id:'ModbusProtocolAlarmInstanceConfigTabPanel_Id',
                	items: [ModbusProtocolAlarmInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">报表实例</div>',
                	id:'ModbusProtocolReportInstanceConfigTabPanel_Id',
                	items: [ModbusProtocolReportInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">短信实例</div>',
                	id:'ModbusProtocolSMSInstanceConfigTabPanel_Id',
                	items: [ModbusProtocolSMSInstanceConfigInfoView],
    				layout: "fit",
    				border: false
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="ModbusProtocolAcqInstanceConfigTabPanel_Id"){
                    		Ext.getCmp("ScadaProtocolModbusInstanceConfigSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolInstanceTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolDisplayInstanceConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolDisplayInstanceTreeSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolAlarmInstanceConfigTabPanel_Id"){
                    		Ext.getCmp("ModbusProtocolAlarmInstanceTreeSelectRow_Id").setValue(0);
                    		var treePanel=Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceTreeInfoStore');
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
