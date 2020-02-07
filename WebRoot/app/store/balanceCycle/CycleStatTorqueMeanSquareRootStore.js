Ext.define('AP.store.balanceCycle.CycleStatTorqueMeanSquareRootStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.cycleStatTorqueMeanSquareRootStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceCycleController/getCycleBalaceStatistics',
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
        	var tabPanel = Ext.getCmp("CycleStatTorqueMeanSquareRootTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="CycleStatTorqueMeanSquareRootPiePanel_Id"){
				initCycleBalanceStatPieOrColChat(store,"周期评价平衡状态统计图","CycleStatTorqueMeanSquareRootTabpanel_Id","CycleStatTorqueMeanSquareRootPiePanel_Id","CycleBalanceStatTorqueMeanSquareRootPieDiv_Id");
			}else{}
            
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

