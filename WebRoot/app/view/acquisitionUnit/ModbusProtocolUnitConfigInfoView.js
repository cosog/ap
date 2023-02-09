Ext.define('AP.view.acquisitionUnit.ModbusProtocolUnitConfigInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.modbusProtocolUnitConfigInfoView',
    layout: "fit",
    id:'modbusProtocolUnitConfigInfoViewId',
    border: false,
    initComponent: function () {
    	var me = this;
    	var ModbusProtocolAcqUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAcqUnitConfigInfoView');
    	var ModbusProtocolAlarmUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolAlarmUnitConfigInfoView');
    	var ModbusProtocolDisplayUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolDisplayUnitConfigInfoView');
    	var ModbusProtocolReportUnitConfigInfoView = Ext.create('AP.view.acquisitionUnit.ModbusProtocolReportUnitConfigInfoView');
    	Ext.apply(me, {
    		items: [{
    			xtype: 'tabpanel',
                id:"ModbusProtocolUnitConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'left',
                items: [{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">采控单元</div>',
                	id:'ModbusProtocolAcqUnitConfigTabPanel_Id',
                	items: [ModbusProtocolAcqUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">报警单元</div>',
                	id:'ModbusProtocolAlarmUnitConfigTabPanel_Id',
                	items: [ModbusProtocolAlarmUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">显示单元</div>',
                	id:'ModbusProtocolDisplayUnitConfigTabPanel_Id',
                	items: [ModbusProtocolDisplayUnitConfigInfoView],
    				layout: "fit",
    				border: false
                },{
                	title:'<div style="color:#000000;font-size:11px;font-family:SimSun">报表单元</div>',
                	id:'ModbusProtocolReportUnitConfigTabPanel_Id',
                	items: [ModbusProtocolReportUnitConfigInfoView],
    				layout: "fit",
    				border: false
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="ModbusProtocolAlarmUnitConfigTabPanel_Id"){
                    		var treePanel=Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolDisplayUnitConfigTabPanel_Id"){
                    		var treePanel=Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id");
                    		if(isNotVal(treePanel)){
                    			treePanel.getStore().load();
                    		}else{
                    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayUnitTreeInfoStore');
                    		}
                    	}else if(newCard.id=="ModbusProtocolReportUnitConfigTabPanel_Id"){
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
