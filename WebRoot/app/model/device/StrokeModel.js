Ext.define('AP.model.device.StrokeModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'jlbh',
            type: 'int'
        },
        {
            name: 'cyjbh',
            type: 'int'
        },
        {
            name: 'sccj',
            type: 'string'
        },
        {
            name: 'cyjxh',
            type: 'string'
        },
        {
            name: 'cc',
            type: 'number'
        },
        {
            name: 'qbkj',
            type: 'number'
        },
        {
            name: 'wzjnjys',
            type: 'string'
        }
 ],
    idProperty: 'threadId'
});