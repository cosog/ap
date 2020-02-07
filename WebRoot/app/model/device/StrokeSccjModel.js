Ext.define('AP.model.device.StrokeSccjModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'sccj',
            type: 'string'
        },
        {
            name: 'cyjxh',
            type: 'string'
        },
        {
            name: 'cyjbh',
            type: 'int'
        }
 ],
    idProperty: 'threadId'
});