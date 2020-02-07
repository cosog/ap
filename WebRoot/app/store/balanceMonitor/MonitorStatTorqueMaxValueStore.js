Ext.define('AP.store.balanceMonitor.MonitorStatTorqueMaxValueStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorTorqueMaxValueStore',
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
        	var tabPanel = Ext.getCmp("MonitorStatTorqueMaxValueTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId!="MonitorStatTorqueMaxValuePanel_Id"){
				initBalanceMonitorPieOrColChat(store,2);
			}else{
				var get_rawData = store.proxy.reader.rawData;
	            var arrColumns = get_rawData.columns;
	            var MonitorStatTorqueMaxValueGrid = Ext.getCmp("MonitorStatTorqueMaxValueGrid_Id");
	            if (!isNotVal(MonitorStatTorqueMaxValueGrid)) {
	                var column = createBalanceMinitorListColumn(arrColumns)
	                var newColumns = Ext.JSON.decode(column);
	                MonitorStatTorqueMaxValueGrid = Ext.create('Ext.grid.Panel', {
	                    id: "MonitorStatTorqueMaxValueGrid_Id",
	                    border: false,
	                    autoLoad: false,
	                    columnLines: true,
	                    forceFit: true,
	                    viewConfig: {
	                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
	                    },
	                    store: store,
	                    columns: newColumns,
	                    listeners: {
	                    	itemclick:function( view , record , item , index , e , eOpts ) {
	                    		Ext.getCmp("TorqueMaxValueBalanceMonitorjh_Id").setValue("");
	                    		Ext.getCmp("TorqueMaxValueBalanceMonitorjh_Id").setRawValue("");
	                    		Ext.getCmp('MonitorTorqueMaxValueGrid_Id').getStore().load();
	                    	}
	                    }
	                });
	                var MonitorStatTorqueMaxValuePanel = Ext.getCmp("MonitorStatTorqueMaxValuePanel_Id");
	                MonitorStatTorqueMaxValuePanel.add(MonitorStatTorqueMaxValueGrid);
	            }
			}
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    type:2
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});