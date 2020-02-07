Ext.define('AP.store.org.OrgParentInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.orgParentInfoStore',
    model: 'AP.model.org.OrgParentInfoModel',
    autoLoad: true,
    pageSize: 100000000,
    proxy: {
        type: 'ajax',
        url: context + '/orgManagerController/execute?actionType=orgParent&n=' + new Date().getTime() + '',
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