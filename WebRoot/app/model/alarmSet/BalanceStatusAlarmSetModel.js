Ext.define('AP.model.alarmSet.BalanceStatusAlarmSetModel', {
    extend: 'Ext.data.Model',
    fields: [{
    	name: 'id',
    	type: 'int'
     },{
    	 name: 'workingConditionName',
    	 type: 'string'
     },{
    	 name: 'workingConditionCode',
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