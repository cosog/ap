Ext.define('AP.model.role.RightTabTreeInfoModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'tabId',
        type: 'int'
    }, {
        name: 'text',
        type: 'string'
    },{
        name: 'sortNum',
        type: 'string'
    }, {
        name: 'parentId',
        type: 'string'
    }],
    idProperty: 'tabId'
});