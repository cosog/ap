Ext.define('AP.store.diagnosisTotal.DiagnosisTotalStatStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisTotalStatStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getDiagnosisTotalStatistics',
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
        	initDiagnosisTotalSataPie(store);
        	var gridPanel = Ext.getCmp("DiagnosisTotalData_Id");
            if (isNotVal(gridPanel)) {
            	gridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.diagnosisTotal.DiagnosisTotalDataStore');
            }
        },
        beforeload: function (store, options) {
        	var type=getSelectTotalStatType().type;
        	var totalDate=Ext.getCmp('TotalDiagnosisDate_Id').rawValue;
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    totalDate:totalDate,
                    type:type,
                    wellType:200
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});