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
        	var jssj  = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.jssj;
        	var jh  = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.jh;
            var new_params = {
            	jssj: jssj,
                jh:jh
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});