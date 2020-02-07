Ext.define('AP.model.device.StrokefrequencySccjModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'sccj',
            type: 'string'
        },
        {
            name: 'cyjxh',
            type: 'string'
        }
 ],
    idProperty: 'threadId'
});