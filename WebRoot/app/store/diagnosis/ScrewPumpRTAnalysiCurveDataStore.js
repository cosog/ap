Ext.define('AP.store.diagnosis.ScrewPumpRTAnalysiCurveDataStore', {
    extend: 'Ext.data.Store',
    id: "ScrewPumpRTAnalysiCurveDataStore_Id",
    alias: 'widget.screwPumpRTAnalysiCurveDataStore',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisAnalysisOnlyController/getScrewPumpRTAnalysiCurveData',
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
        	initScrewPumpRTCurveChartFn(get_rawData,"ScrewPumpRTCurveDataChartDiv_Id");
        },
        beforeload: function (store, options) {
        	var cjsj  = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.gtcjsj;
        	var jh  = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.jh;
            var selectedjh = Ext.getCmp('singleJh_Id').getValue();
            var new_params = {
            	cjsj: cjsj,
                jh:jh
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});