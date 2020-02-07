Ext.define('AP.model.right.RightRoleInfoModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'roleId',
        type: 'int'
     }, {
        name: 'roleCode',
        type: 'string'
     }, {
        name: 'roleName',
        type: 'string'
     }, {
        name: 'roleFlag',
        type: 'string'
     }],
    idProperty: 'threadid'

});