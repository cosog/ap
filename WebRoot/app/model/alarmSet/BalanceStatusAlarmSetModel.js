Ext.define('AP.model.alarmSet.BalanceStatusAlarmSetModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'resultName',
    	 type: 'string'
     },{
    	 name: 'resultCode',
    	 type: 'int'
     },{
    	 name: 'minValue',
    	 type: 'number'
     },{
    	 name: 'maxValue',
    	 type: 'number'
     }],
    idProperty: 'threadid'
})