Ext.define('AP.model.data.DataitemsInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
            'dataitemid',
            'tenantid',
            'sysdataid',
            'name_zh_CN',
            'name_en',
            'name_ru',
            'code',
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