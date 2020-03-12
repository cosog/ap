Ext.define('AP.store.diagnosisTotal.DiagnosisTotalDataCurveStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisDataCurveStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getDiagnosisTotalDataCurveData',
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
            var itemCode=Ext.getCmp('DiagnosisTotalCurveItemCode_Id').rawValue;
            var itemName=Ext.getCmp('DiagnosisTotalCurveItem_Id').rawValue;
            var divId="TotalHistoryCurve_"+itemCode+"_DivId";
            divId="TotalFSDiagramHistoryCurveDiv_Id";
            var startDate=Ext.getCmp("TotalDiagnosisDataStartDate_Id").getValue();
			if(startDate==""||startDate==null){
				Ext.getCmp("TotalDiagnosisDataStartDate_Id").setValue(get_rawData.startDate);
			}
			DiagnosisTotalDataCurveChartFn(get_rawData,itemName,divId);
        },
        beforeload: function (store, options) {
        	var tabPanel = Ext.getCmp("ProductionWellDailyAnalisisPanel");
        	var activeId = tabPanel.getActiveTab().id;
        	var wellName;
        	var wellType=200;
        	if(activeId=="pumpUnitDailyAnalysisPanel_Id"){
        		wellName  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.wellName;
        		wellType=200;
        	}else if(activeId=="screwPumpDailyAnalysisPanel_Id"){
        		wellName  = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.wellName;
        		wellType=400;
        	}
        	var StartDate = Ext.getCmp('TotalDiagnosisDataStartDate_Id').rawValue;
        	var EndDate = Ext.getCmp('TotalDiagnosisDataEndDate_Id').rawValue;
        	var itemName=Ext.getCmp('DiagnosisTotalCurveItem_Id').rawValue;
        	var itemCode=Ext.getCmp('DiagnosisTotalCurveItemCode_Id').rawValue;
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