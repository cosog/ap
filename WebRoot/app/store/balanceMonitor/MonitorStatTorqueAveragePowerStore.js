Ext.define('AP.store.balanceMonitor.MonitorStatTorqueAveragePowerStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorStatTorqueAveragePowerStore',
    model: 'AP.model.balanceMonitor.BalanceMonitorStatModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceMonitorController/getMonitorBalaceStatistics',
        actionMethods: {
            read: 'POST'
        },
    reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, record, f, op, o) {
            //获得列表数
        	var tabPanel = Ext.getCmp("MonitorStatTorqueAveragePowerTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId!="MonitorStatTorqueAveragePowerPanel_Id"){
				initBalanceMonitorPieOrColChat(store,3);
			}else{
				
			}
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    type:3
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});