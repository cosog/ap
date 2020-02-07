Ext.define('AP.store.wellTrajectory.WellNumberWellInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'wellNumberWellInfoStore',
    model: 'AP.model.wellTrajectory.WellNumberWellInfoModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/wellTrajectoryController/loadWellJhList',
        actionMethod: {
            read: 'POST'
        },
        start: 0,
        limit: 10000,
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData: true
        }
    },
    listeners: {
        beforeload: function (store, options) {}
    }
});