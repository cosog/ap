Ext.define('AP.store.balanceMonitor.MonitorTorqueAveragePowerAnalysisStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorTorqueAveragePowerAnalysisStore',
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
            var MonitorTorqueAveragePowerAnalysisGrid = Ext.getCmp("MonitorTorqueAveragePowerAnalysisGrid_Id");
            if (!isNotVal(MonitorTorqueAveragePowerAnalysisGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                MonitorTorqueAveragePowerAnalysisGrid = Ext.create('Ext.grid.Panel', {
                    id: "MonitorTorqueAveragePowerAnalysisGrid_Id",
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
                var TorqueAveragePowerMonitorDataPanel = Ext.getCmp("TorqueAveragePowerMonitorDataPanel_Id");
                TorqueAveragePowerMonitorDataPanel.add(MonitorTorqueAveragePowerAnalysisGrid);
            }
            
            var gtjh="";
            var Gridpanel = Ext.getCmp("MonitorTorqueAveragePowerGrid_Id");
        	if(isNotVal(Gridpanel)){
        		var record = Gridpanel.getSelectionModel().getSelection();
        		if(record.length>0){
        			gtjh=record[0].data.jh;
        		}
        	}
            
            showBalanceAnalysisSurfaceCardChart(store,"MonitorTorqueAveragePowerGtDiv_Id",gtjh);
            var tabPanel = Ext.getCmp("MonitorTorqueAveragePowerTorqueCurveTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="MonitorTorqueAveragePowerTorqueCurvePanel_Id"){
				showBalanceAnalysisCurveChart(store,"MonitorTorqueAveragePowerTorqueCurve1Div_Id","MonitorTorqueAveragePowerTorqueCurve2Div_Id");
			}else{
				showBalanceAnalysisMotionCurveChart(store,"TorqueAveragePowerPRAndTFPanel_Id","MonitorTorqueAveragePowerMotionCurveGrid_Id","MonitorTorqueAveragePowerMotionCurveDiv_Id");
			}
        },
        beforeload: function (store, options) {
        	var jlbh=0;
        	var MonitorTorqueAveragePowerGrid = Ext.getCmp("MonitorTorqueAveragePowerGrid_Id");
        	if(isNotVal(MonitorTorqueAveragePowerGrid)){
        		var gridPanel_model = MonitorTorqueAveragePowerGrid.getSelectionModel();
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