Ext.define('AP.model.device.StrokefrequencyModel', {
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
            name: 'cc1',
            type: 'number'
        },
        {
            name: 'djpdlzj',
            type: 'number'
        }
 ],
    idProperty: 'threadId'
});