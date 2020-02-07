Ext.define('AP.model.device.SccjCybModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'sccj',
            type: 'string'
        },
        {
            name: 'cybxh',
            type: 'string'
        }
 ],
    idProperty: 'threadId'
});