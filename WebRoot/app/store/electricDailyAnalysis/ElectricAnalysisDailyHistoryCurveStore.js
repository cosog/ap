Ext.define('AP.store.electricDailyAnalysis.ElectricAnalysisDailyHistoryCurveStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.electricAnalysisDailyHistoryCurveStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/PSToFSController/getDailyHistoryCurveData',
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
            var itemCode=Ext.getCmp('ElectricAnalysisDailyDetailsCurveItemCode_Id').rawValue;
            var itemName=Ext.getCmp('ElectricAnalysisDailyDetailsCurveItem_Id').rawValue;
            var divId="ElectricAnalysisDailyHistoryCurve_"+itemCode+"_DivId";
            divId="ElectricAnalysisDailyHistoryCurveDiv_Id";
            var startDate=Ext.getCmp("ElectricAnalysisDailyHistoryCurveStartDate_Id").getValue();
			if(startDate==""||startDate==null){
				Ext.getCmp("ElectricAnalysisDailyHistoryCurveStartDate_Id").setValue(get_rawData.startDate);
			}
			ElectricAnalysisDailyHistoryCurveChartFn(get_rawData,itemName,divId);
        },
        beforeload: function (store, options) {
        	var wellName  = Ext.getCmp("ElectricAnalysisDailyDetails_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var StartDate = Ext.getCmp('ElectricAnalysisDailyHistoryCurveStartDate_Id').rawValue;
        	var EndDate = Ext.getCmp('ElectricAnalysisDailyHistoryCurveEndDate_Id').rawValue;
        	var itemName=Ext.getCmp('ElectricAnalysisDailyDetailsCurveItem_Id').rawValue;
        	var itemCode=Ext.getCmp('ElectricAnalysisDailyDetailsCurveItemCode_Id').rawValue;
        	var new_params = {
        			wellName: wellName,
                    startDate:StartDate,
                    endDate:EndDate,
                    itemName:itemName,
                    itemCode:itemCode
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});