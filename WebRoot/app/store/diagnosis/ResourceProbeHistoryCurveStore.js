Ext.define('AP.store.diagnosis.ResourceProbeHistoryCurveStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.resourceProbeHistoryCurveStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisAnalysisOnlyController/getDiagnosisDataCurveData',
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
            var itemCode=Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').rawValue;
            var itemName=Ext.getCmp('DiagnosisAnalysisCurveItem_Id').rawValue;
            var divId="HistoryCurve_"+itemCode+"_DivId";
            divId="SingleFSDiagramHistoryCurveDiv_Id";
            var startDate=Ext.getCmp("DiagnosisData_from_date_Id").getValue();
			if(startDate==""||startDate==null){
				Ext.getCmp("DiagnosisData_from_date_Id").setValue(get_rawData.startDate);
			}
			DiagnosisDataCurveChartFn(get_rawData,itemName,itemCode,divId);
        },
        beforeload: function (store, options) {
        	var tabPanel = Ext.getCmp("ProductionWellRealtimeAnalisisPanel");
    		var activeId = tabPanel.getActiveTab().id;
    		var gridPanelId="FSDiagramAnalysisSingleDetails_Id";
    		var wellType=200;
    		if(activeId=="RPCSingleDetailsInfoPanel_Id"){
    			gridPanelId="FSDiagramAnalysisSingleDetails_Id";
    			wellType=200;
    		}else if(activeId=="PCPSingleDetailsInfoPanel_Id"){
    			gridPanelId="ScrewPumpRTAnalysisWellList_Id";
    			wellType=400;
    		}
        	var wellName  = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.wellName;
        	var StartDate = Ext.getCmp('DiagnosisData_from_date_Id').rawValue;
        	var EndDate = Ext.getCmp('DiagnosisData_end_date_Id').rawValue;
        	var itemName=Ext.getCmp('DiagnosisAnalysisCurveItem_Id').rawValue;
        	var itemCode=Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').rawValue;
        	var new_params = {
        			wellName: wellName,
                    startDate:StartDate,
                    endDate:EndDate,
                    itemName:itemName,
                    itemCode:itemCode,
                    wellType:wellType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});