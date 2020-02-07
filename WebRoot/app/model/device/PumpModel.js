Ext.define('AP.model.device.PumpModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'jlbh',
            type: 'int'
        },
        {
            name: 'sccj',
            type: 'string'
        },
        {
            name: 'cybxh',
            type: 'string'
        },
        {
            name: 'blx',
            type: 'string'
        },{
        	name:'blxName',
        	type:'string'
        },{
        	name:'btlxName',
        	type:'string'
        },
        {
            name: 'bjb',
            type: 'int'
        },{
        	name:'bjbName',
        	type:'string'
        },
        {
            name: 'bj',
            type: 'number'
        },
        {
            name: 'zsc',
            type: 'number'
        }
  ],
    idProprety: 'threadId'
});