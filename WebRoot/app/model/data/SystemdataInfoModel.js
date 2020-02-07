Ext.define('AP.model.data.SystemdataInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
            'sysdataid',
            'tenantid',
            'cname',
            'ename',
            'sorts',
            'status',
            'creator',
            'updateuser',
            'updatetime',
            'createdate'
        ]
        //,
        //idProperty: 'sysdataid'
});