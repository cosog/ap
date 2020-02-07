Ext.define('AP.store.right.RightInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rightInfoStore',
    model: 'AP.model.right.rightInfoModel',
    autoLoad: true,
    pageSize: 20,
    proxy: {
        type: 'ajax',
        url: context + '/rightManagerController/dorightShow',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: 20,
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData: true
        }
    }
});