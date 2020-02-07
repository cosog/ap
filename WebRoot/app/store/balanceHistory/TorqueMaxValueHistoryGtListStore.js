Ext.define('AP.store.balanceHistory.TorqueMaxValueHistoryGtListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.torqueMaxValueHistoryGtListStore',
    model: 'AP.model.balanceHistory.HistoryWellListModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceHistoryQueryController/getHistoryGtList',
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
            var TorqueMaxValueHistoryGtListGrid = Ext.getCmp("TorqueMaxValueHistoryGtListGrid_Id");
            if (!isNotVal(TorqueMaxValueHistoryGtListGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                TorqueMaxValueHistoryGtListGrid = Ext.create('Ext.grid.Panel', {
                    id: "TorqueMaxValueHistoryGtListGrid_Id",
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
                    			var HistoryTorqueMaxValueAnalysisGrid=Ext.getCmp("HistoryTorqueMaxValueAnalysisGrid_Id");
                    			if(isNotVal(HistoryTorqueMaxValueAnalysisGrid)){
                    				HistoryTorqueMaxValueAnalysisGrid.getStore().load();
                    			}else{
                    				Ext.create("AP.store.balanceHistory.HistoryTorqueMaxValueAnalysisStore");
                    			}
                    		}
                    	}
                    }
                });
                var TorqueMaxValueHistoryGtListPanel = Ext.getCmp("TorqueMaxValueHistoryGtListPanel_Id");
                TorqueMaxValueHistoryGtListPanel.add(TorqueMaxValueHistoryGtListGrid);
            }
            var startDate = Ext.getCmp('TorqueMaxValueHistoryStartDate_Id').getValue();
            if (startDate == "new" || startDate == "" || startDate == null) {
                Ext.getCmp('TorqueMaxValueHistoryStartDate_Id').setValue(get_rawData.startDate);
            }
            var endDate = Ext.getCmp('TorqueMaxValueHistoryEndDate_Id').getValue();
            if (endDate == "new" || endDate == "" || endDate == null) {
                Ext.getCmp('TorqueMaxValueHistoryEndDate_Id').setValue(get_rawData.endDate);
            }
            TorqueMaxValueHistoryGtListGrid.getSelectionModel().select(0,true);//选中第一行
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh = "";
        	var gklx="";
        	var startDate=Ext.getCmp('TorqueMaxValueHistoryStartDate_Id').rawValue;
        	var endtDate=Ext.getCmp('TorqueMaxValueHistoryEndDate_Id').rawValue;
        	var TorqueMaxValueHistoryWellListGrid = Ext.getCmp("TorqueMaxValueHistoryWellListGrid_Id");
        	if(isNotVal(TorqueMaxValueHistoryWellListGrid)){
        		var record=TorqueMaxValueHistoryWellListGrid.getSelectionModel().getSelection();
        		if(record.length>0){
        			jh=record[0].data.jh;
        		}
        	}
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    gklx:gklx,
                    startDate:startDate,
                    endtDate:endtDate
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});