Ext.define('AP.model.balanceCycle.CycleWellListModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'jh',
    	 type: 'string'
     },{
    	 name: 'jlbh',
    	 type: 'int'
     },{
    	 name: 'gtcjsj',
    	 type: 'string'
     },{
    	 name:'phd',
    	 type:'float'
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