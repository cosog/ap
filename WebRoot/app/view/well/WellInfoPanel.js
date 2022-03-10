Ext.define("AP.view.well.WellInfoPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.wellInfoPanel', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var RPCDeviceInfoPanel = Ext.create('AP.view.well.RPCDeviceInfoPanel');
        var PCPDeviceInfoPanel = Ext.create('AP.view.well.PCPDeviceInfoPanel');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"DeviceManagerTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        				title: '抽油机',
        				layout: "fit",
        				id:'RPCDeviceInfoTabPanel_Id',
        				border: false,
        				items: [RPCDeviceInfoPanel]
        			},{
        				title: '螺杆泵',
        				layout: "fit",
        				id:'PCPDeviceInfoTabPanel_Id',
        				border: false,
        				items: [PCPDeviceInfoPanel]
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); //
        					if(newCard.id=="RPCDeviceInfoTabPanel_Id"){
        						CreateAndLoadRPCDeviceInfoTable(true);
        					}else if(newCard.id=="PCPDeviceInfoTabPanel_Id"){
        						CreateAndLoadPCPDeviceInfoTable(true);
        					}
        				}
        			}
            	}],
            	listeners: {
        			beforeclose: function ( panel, eOpts) {
        				//抽油机
        				if (diaphragmPumpDeviceInfoHandsontableHelper != null) {
                            if (diaphragmPumpDeviceInfoHandsontableHelper.hot != undefined) {
                                diaphragmPumpDeviceInfoHandsontableHelper.hot.destroy();
                            }
                            diaphragmPumpDeviceInfoHandsontableHelper = null;
                        }
                        //螺杆泵
                        if (screwPumpDeviceInfoHandsontableHelper != null) {
                            if (screwPumpDeviceInfoHandsontableHelper.hot != undefined) {
                                screwPumpDeviceInfoHandsontableHelper.hot.destroy();
                            }
                            screwPumpDeviceInfoHandsontableHelper = null;
                        }
        			}
            	}
        });
        me.callParent(arguments);
    }
});