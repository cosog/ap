Ext.define('AP.model.balanceHistory.BalanceHistoryAnalysisModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'key',
    	 type: 'string'
     },{
    	 name: 'value',
    	 type: 'number'
     }],
    idProperty: 'threadid'
})