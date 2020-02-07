Ext.define('AP.model.balanceHistory.HistoryWellListModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'gtcjsj',
    	 type: 'string'
     },{
    	 name: 'gklx',
    	 type: 'int'
     },{
    	 name: 'gkmc',
    	 type: 'string'
     },{
    	 name: 'bjjb',
    	 type: 'int'
     },{
    	 name: 'bjys',
    	 type: 'string'
     }],
    idProperty: 'threadid'
})