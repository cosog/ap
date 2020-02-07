Ext.define('AP.model.res.ResHeadInfoModel', {
    extend: 'Ext.data.TreeModel',
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
     }, {
        name: 'resLevel',
        type: 'string'
     }, {
        name: 'resParent',
        type: 'string'
     }, {
        name: 'resSeq',
        type: 'string'
     }]
});