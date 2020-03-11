Ext.define('AP.store.diagnosisTotal.ScrewPumpDailyAnalysisStatStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.screwPumpDailyAnalysisStatStore',
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
        	
        	
        	initScrewPumpDailyStatPieChat(store);
        	var gridPanel = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id");
            if (isNotVal(gridPanel)) {
            	gridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.diagnosisTotal.SvrewPumpDailyAnalysisListStore');
            }
        },
        beforeload: function (store, options) {
        	var type=getPCPSelectDailyStatType().type;
        	var totalDate=Ext.getCmp('ScrewPumpDailyAnalysisDate_Id').rawValue;
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    totalDate:totalDate,
                    type:type,
                    wellType:400
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});