Ext.define('AP.model.balanceTotal.TotalWellListModel', {
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