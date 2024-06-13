Ext.define('AP.store.module.ModuleParentInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.moduleParentInfoStore',
    model: 'AP.model.module.ModuleParentInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/moduleManagerController/queryModules?actionType=moduleParent&n=' + new Date().getTime() + '',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: 100000,
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData: true
        }
    },
    listeners: {}
});