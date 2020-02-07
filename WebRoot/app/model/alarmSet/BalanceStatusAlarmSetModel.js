Ext.define('AP.model.alarmSet.BalanceStatusAlarmSetModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'gkmc',
    	 type: 'string'
     },{
    	 name: 'gklx',
    	 type: 'int'
     },{
    	 name: 'minvalue',
    	 type: 'number'
     },{
    	 name: 'maxvalue',
    	 type: 'number'
     }],
    idProperty: 'threadid'
})