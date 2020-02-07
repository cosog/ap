Ext.define('AP.model.balanceRecord.BalanceRecordModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'jh',
    	 type: 'string'
     },{
    	 name: 'manufacturer',
    	 type: 'string'
     },{
    	 name: 'model',
    	 type: 'string'
     },{
    	 name: 'ktztmc',
    	 type: 'string'
     },{
    	 name: 'positionandweight',
    	 type: 'string'
     },{
    	 name: 'updatetime',
    	 type: 'string'
     }],
    idProperty: 'threadid'
})