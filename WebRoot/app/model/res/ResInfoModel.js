Ext.define('AP.model.org.OrgInfoModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'resId',
        type: 'int'
     }, {
        name: 'text',
        type: 'string'
     }, {
        name: 'resMemo',
        type: 'string'
     }, {
        name: 'resCode',
        type: 'string'
     }]
});