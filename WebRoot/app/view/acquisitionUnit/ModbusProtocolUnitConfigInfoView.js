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
    	Ext.apply(me, {
    		items: [{
    			xtype: 'tabpanel',
                id:"ModbusProtocolConfigTabPanel_Id",
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
                    	}
                    },afterrender: function (comp,eOpts) {
//                    	Ext.getCmp("ModbusProtocolConfigTabPanel_Id").getTabBar().insert(0, {
//            		      	xtype: 'tbfill'
//                  		});
                    }
                }
    		}]
    	});
        this.callParent(arguments);
    }
});
