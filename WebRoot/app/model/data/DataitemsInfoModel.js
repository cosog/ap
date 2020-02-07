Ext.define('AP.model.data.DataitemsInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
            'dataitemid',
            'tenantid',
            'sysdataid',
            'cname',
            'ename',
            'datavalue',
            'sorts',
            'status',
            'creator',
            'updateuser',
            'updatetime',
            'createdate'
        ],
    idProperty: 'dataitemid'
});