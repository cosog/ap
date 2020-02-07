Ext.define('AP.store.balanceHistory.TorqueMaxValueHistoryWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.torqueMaxValueHistoryWellListStore',
    model: 'AP.model.balanceHistory.HistoryWellListModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceHistoryQueryController/getHistoryWellList',
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
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var TorqueMaxValueHistoryWellListGrid = Ext.getCmp("TorqueMaxValueHistoryWellListGrid_Id");
            if (!isNotVal(TorqueMaxValueHistoryWellListGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                TorqueMaxValueHistoryWellListGrid = Ext.create('Ext.grid.Panel', {
                    id: "TorqueMaxValueHistoryWellListGrid_Id",
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
                    		
                    	},
                    	selectionchange:function(grid, record , eOpts) {
                    		if(record.length>0){
                    			var TorqueMaxValueHistoryGtListGrid=Ext.getCmp("TorqueMaxValueHistoryGtListGrid_Id");
                    			if(isNotVal(TorqueMaxValueHistoryGtListGrid)){
                    				TorqueMaxValueHistoryGtListGrid.getStore().load();
                    			}else{
                    				Ext.create("AP.store.balanceHistory.TorqueMaxValueHistoryGtListStore");
                    			}
                    		}
                    	}
                    }
                });
                var TorqueMaxValueHistoryWellListPanel = Ext.getCmp("TorqueMaxValueHistoryWellListPanel_Id");
                TorqueMaxValueHistoryWellListPanel.add(TorqueMaxValueHistoryWellListGrid);
            }
            TorqueMaxValueHistoryWellListGrid.getSelectionModel().select(0,true);//选中第一行
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = Ext.getCmp('TorqueMaxValueHistoryjh_Id').getValue();
        	var gklx="";
        	
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    gklx:gklx
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});