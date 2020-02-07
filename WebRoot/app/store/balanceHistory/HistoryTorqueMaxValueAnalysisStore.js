Ext.define('AP.store.balanceHistory.HistoryTorqueMaxValueAnalysisStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.historyTorqueMaxValueAnalysisStore',
    model: 'AP.model.balanceHistory.BalanceHistoryAnalysisModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceHistoryQueryController/getHisTorqueMaxValueBalaceAnalysis',
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
        	var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var HistoryTorqueMaxValueAnalysisGrid = Ext.getCmp("HistoryTorqueMaxValueAnalysisGrid_Id");
            if (!isNotVal(HistoryTorqueMaxValueAnalysisGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                HistoryTorqueMaxValueAnalysisGrid = Ext.create('Ext.grid.Panel', {
                    id: "HistoryTorqueMaxValueAnalysisGrid_Id",
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
                    	}
                    }
                });
                var TorqueMaxValueHistoryDataPanel = Ext.getCmp("TorqueMaxValueHistoryDataPanel_Id");
                TorqueMaxValueHistoryDataPanel.add(HistoryTorqueMaxValueAnalysisGrid);
            }
            
            var gtjh="";
            var Gridpanel = Ext.getCmp("TorqueMaxValueHistoryWellListGrid_Id");
        	if(isNotVal(Gridpanel)){
        		var record = Gridpanel.getSelectionModel().getSelection();
        		if(record.length>0){
        			Gridpanel=record[0].data.jh;
        		}
        	}
            
            showBalanceAnalysisSurfaceCardChart(store,"HistoryTorqueMaxValueGtDiv_Id",gtjh);
            var tabPanel = Ext.getCmp("HistoryTorqueMaxValueTorqueCurveTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="HistoryTorqueMaxValueTorqueCurvePanel_Id"){
				showBalanceAnalysisCurveChart(store,"HistoryTorqueMaxValueTorqueCurve1Div_Id","HistoryTorqueMaxValueTorqueCurve2Div_Id");
			}
            else{
				showBalanceAnalysisMotionCurveChart(store,"HistoryTorqueMaxValuePRAndTFPanel_Id","HistoryTorqueMaxValueMotionCurveGrid_Id","HistoryTorqueMaxValueMotionCurveDiv_Id");
			}
        },
        beforeload: function (store, options) {
        	var jlbh=0;
        	var TorqueMaxValueHistoryGtListGrid = Ext.getCmp("TorqueMaxValueHistoryGtListGrid_Id");
        	if(isNotVal(TorqueMaxValueHistoryGtListGrid)){
        		var record = TorqueMaxValueHistoryGtListGrid.getSelectionModel().getSelection();
        		if(record.length>0){
        			jlbh=record[0].data.id;
        		}
        	}
        	var new_params = {
                    jlbh: jlbh
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});