Ext.define('AP.model.role.RoleInfoModel', {
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
     }, {
        name: 'roleFlagName',
        type: 'string'
     }],
    idProperty: 'threadid'

});