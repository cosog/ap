Ext.define('AP.store.diagnosisTotal.ScrewPumpDailyAnalysiCurveDataStore', {
    extend: 'Ext.data.Store',
    id: "ScrewPumpDailyAnalysiCurveDataStore",
    alias: 'widget.screwPumpDailyAnalysiCurveDataStore',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getScrewPumpDailyAnalysiCurveData',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            rootProperty: 'list',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
        	var get_rawData=store.proxy.reader.rawData;   // 获取store数据
        	initScrewPumpDailyCurveChartFn(get_rawData,"ScrewPumpDailyCurveDataChartDiv_Id");
        },
        beforeload: function (store, options) {
        	var calculateDate  = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.calculateDate;
        	var wellName  = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.wellName;
            var new_params = {
            		calculateDate: calculateDate,
            		wellName:wellName
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});