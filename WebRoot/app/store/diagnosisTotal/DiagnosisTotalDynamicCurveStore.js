Ext.define('AP.store.diagnosisTotal.DiagnosisTotalDynamicCurveStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisTotalDynamicCurveStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getDiagnosisTotalDynamicCurveData',
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
            initRPCTotalDynamicCurveChartFn(get_rawData,"DiagnosisTotalDynamicCurveDiv_id");
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var selectedWellName = Ext.getCmp('FSDiagramAnalysisDailyDetailsWellCom_Id').getValue();
        	var calculateDate  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.calculateDate;
        	var startDate=Ext.getCmp('DiagnosisTotalStartDate_Id').rawValue;
        	var endDate=Ext.getCmp('DiagnosisTotalEndDate_Id').rawValue;
        	
        	var new_params = {
                    orgId: leftOrg_Id,
                    wellName: wellName,
                    selectedWellName:selectedWellName,
                    calculateDate:calculateDate,
                    startDate:startDate,
                    endDate:endDate,
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});