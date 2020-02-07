Ext.define('AP.model.balanceMonitor.BalanceMonitorStatModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'gklx',
    	 type: 'int'
     },{
    	 name: 'gkmc',
    	 type: 'string'
     },{
    	 name: 'wellcount',
    	 type: 'int'
     }],
    idProperty: 'threadid'
})