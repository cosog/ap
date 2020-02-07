Ext.define('AP.store.diagnosisTotal.DiagnosisTotalCurveStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisTotalCurveStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getDiagnosisTotalCurveData',
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
            var startDate=Ext.getCmp("DiagnosisTotalCurve_StartDate_Id").getValue();
			if(startDate==""||startDate==null){
				Ext.getCmp("DiagnosisTotalCurve_StartDate_Id").setValue(get_rawData.startDate);
			}
            DiagnosisTotalCurveChartFn(get_rawData);
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.jh;
        	var StartDate = Ext.getCmp('DiagnosisTotalCurve_StartDate_Id').rawValue;
        	var EndDate = Ext.getCmp('DiagnosisTotalCurve_EndDate_Id').rawValue;
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    startDate:StartDate,
                    endDate:EndDate
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});