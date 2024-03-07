Ext.define("AP.view.log.LogInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.logInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var DeviceOperationLogInfoView = Ext.create('AP.view.log.DeviceOperationLogInfoView');
        var SystemLogInfoView = Ext.create('AP.view.log.SystemLogInfoView');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"LogQueryTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        				title: '设备日志',
        				id:'DeviceOperationLogInfoPanel_Id',
        				items: [DeviceOperationLogInfoView],
        				layout: "fit",
        				border: false
        			},{
        				title: '系统日志',
        				id:'SystemLogInfoPanel_Id',
        				items: [SystemLogInfoView],
        				layout: "fit",
        				border: false
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        					if(newCard.id=="DeviceOperationLogInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("DeviceOperationLogGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.log.DeviceOperationLogStore');
        						}
        					}else if(newCard.id=="SystemLogInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("SystemLogGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.log.SystemLogStore');
        						}
        					}
        				}
        			}
            	}]
        });
        me.callParent(arguments);
    }

});