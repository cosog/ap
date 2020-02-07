Ext.define('AP.store.balanceMonitor.MonitorStatTorqueMeanSquareRootStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorStatTorqueMeanSquareRootStore',
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
        	var tabPanel = Ext.getCmp("MonitorStatTorqueMeanSquareRootTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId!="MonitorStatTorqueMeanSquareRootPanel_Id"){
				initBalanceMonitorPieOrColChat(store,1);
			}else{
				
			}
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    type:1
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});