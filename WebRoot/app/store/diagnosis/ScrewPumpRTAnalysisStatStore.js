Ext.define('AP.store.diagnosis.ScrewPumpRTAnalysisStatStore', {
	extend : 'Ext.data.Store',
	autoLoad : true,
	pageSize : defaultPageSize,
	proxy : {
		type : 'ajax',
		url : context
				+ '/diagnosisAnalysisOnlyController/statisticsData',
		actionMethods : {
			read : 'POST'
		},
		params : {
			start : 0,
			limit : defaultPageSize
		},
		reader : {
			type : 'json',
			rootProperty : 'list',
			totalProperty : 'totals',
            keepRawData: true
		}
	},
	listeners : {
		load:function(store,record,f,op,o){
			initScrewPumpRTStatPieChat(store);
			
			var gridPanel = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id");
            if (isNotVal(gridPanel)) {
            	gridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.diagnosis.ScrewPumpRTAnalysisiWellListStore');
            }
		},
		beforeload : function(store, options) {
			var type=getPCPRPMAnalysisSingleStatType().type;
			var leftOrg_Id = Ext.getCmp('leftOrg_Id');
			if (!Ext.isEmpty(leftOrg_Id)) {
				leftOrg_Id = leftOrg_Id.getValue();
			}
			var new_params = {
				orgId : leftOrg_Id,
				type:type,
				wellType:400
			};
			Ext.apply(store.proxy.extraParams, new_params);
		}
	}
});

