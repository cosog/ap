Ext.define('AP.store.balanceMonitor.MonitorTorqueMeanSquareRootAnalysisStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorTorqueMeanSquareRootAnalysisStore',
    model: 'AP.model.balanceMonitor.BalanceMonitorAnalysisModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceMonitorController/getMonitorBalaceAnalysis',
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
            var MonitorTorqueMeanSquareRootAnalysisGrid = Ext.getCmp("MonitorTorqueMeanSquareRootAnalysisGrid_Id");
            if (!isNotVal(MonitorTorqueMeanSquareRootAnalysisGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                MonitorTorqueMeanSquareRootAnalysisGrid = Ext.create('Ext.grid.Panel', {
                    id: "MonitorTorqueMeanSquareRootAnalysisGrid_Id",
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
                var TorqueMeanSquareRootMonitorDataPanel = Ext.getCmp("TorqueMeanSquareRootMonitorDataPanel_Id");
                TorqueMeanSquareRootMonitorDataPanel.add(MonitorTorqueMeanSquareRootAnalysisGrid);
            }
            
            var gtjh="";
            var Gridpanel = Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id");
        	if(isNotVal(Gridpanel)){
        		var record = Gridpanel.getSelectionModel().getSelection();
        		if(record.length>0){
        			gtjh=record[0].data.jh;
        		}
        	}
            
            showBalanceAnalysisSurfaceCardChart(store,"MonitorTorqueMeanSquareRootGtDiv_Id",gtjh);
            var tabPanel = Ext.getCmp("MonitorTorqueMeanSquareRootTorqueCurveTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="MonitorTorqueMeanSquareRootTorqueCurvePanel_Id"){
				showBalanceAnalysisCurveChart(store,"MonitorTorqueMeanSquareRootTorqueCurve1Div_Id","MonitorTorqueMeanSquareRootTorqueCurve2Div_Id");
			}else{
				showBalanceAnalysisMotionCurveChart(store,"TorqueMeanSquareRootPRAndTFPanel_Id","MonitorTorqueMeanSquareRootMotionCurveGrid_Id","MonitorTorqueMeanSquareRootMotionCurveDiv_Id");
			}
        },
        beforeload: function (store, options) {
        	var jlbh=0;
        	var MonitorTorqueMeanSquareRootGrid = Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id");
        	if(isNotVal(MonitorTorqueMeanSquareRootGrid)){
        		var gridPanel_model = MonitorTorqueMeanSquareRootGrid.getSelectionModel();
        		var record = gridPanel_model.getSelection();
        		if(record.length>0){
        			jlbh=record[0].data.jlbh;
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