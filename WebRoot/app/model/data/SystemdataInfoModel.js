Ext.define('AP.model.data.SystemdataInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
            'sysdataid',
            'tenantid',
            'name_zh_CN',
            'name_en',
            'name_ru',
            'code',
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