Ext.define('AP.store.balanceMonitor.MonitorTorqueMaxValueAnalysisStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.monitorTorqueMaxValueAnalysisStore',
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
            var MonitorTorqueMaxValueAnalysisGrid = Ext.getCmp("MonitorTorqueMaxValueAnalysisGrid_Id");
            if (!isNotVal(MonitorTorqueMaxValueAnalysisGrid)) {
                var column = createBalanceMinitorListColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                MonitorTorqueMaxValueAnalysisGrid = Ext.create('Ext.grid.Panel', {
                    id: "MonitorTorqueMaxValueAnalysisGrid_Id",
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
                var TorqueMaxValueMonitorDataPanel = Ext.getCmp("TorqueMaxValueMonitorDataPanel_Id");
                TorqueMaxValueMonitorDataPanel.add(MonitorTorqueMaxValueAnalysisGrid);
            }
            
            var gtjh="";
            var Gridpanel = Ext.getCmp("MonitorTorqueMaxValueGrid_Id");
        	if(isNotVal(Gridpanel)){
        		var record = Gridpanel.getSelectionModel().getSelection();
        		if(record.length>0){
        			gtjh=record[0].data.jh;
        		}
        	}
            
            showBalanceAnalysisSurfaceCardChart(store,"MonitorTorqueMaxValueGtDiv_Id",gtjh);
            var tabPanel = Ext.getCmp("MonitorTorqueMaxValueTorqueCurveTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="MonitorTorqueMaxValueTorqueCurvePanel_Id"){
				showBalanceAnalysisCurveChart(store,"MonitorTorqueMaxValueTorqueCurve1Div_Id","MonitorTorqueMaxValueTorqueCurve2Div_Id");
			}else{
				showBalanceAnalysisMotionCurveChart(store,"TorqueMaxValuePRAndTFPanel_Id","MonitorTorqueMaxValueMotionCurveGrid_Id","MonitorTorqueMaxValueMotionCurveDiv_Id");
			}
        },
        beforeload: function (store, options) {
        	var jlbh=0;
        	var MonitorTorqueMaxValueGrid = Ext.getCmp("MonitorTorqueMaxValueGrid_Id");
        	if(isNotVal(MonitorTorqueMaxValueGrid)){
        		var gridPanel_model = MonitorTorqueMaxValueGrid.getSelectionModel();
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