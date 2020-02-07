Ext.define('AP.model.action.ActionInfoModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'actionIds',
        type: 'int'
     }, {
        name: 'actionCode',
        type: 'string'
     }, {
        name: 'actionName',
        type: 'string'
     }, {
        name: 'actionSeq',
        type: 'int'
     }]

});