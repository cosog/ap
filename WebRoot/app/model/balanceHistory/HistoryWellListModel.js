Ext.define('AP.model.balanceHistory.HistoryWellListModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'jh',
    	 type: 'string'
     }],
    idProperty: 'threadid'
})