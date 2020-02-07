Ext.define('AP.store.wellTrajectory.WellNumberWellTrajectoryInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'wellNumberWellTrajectoryInfoStore',
    model: 'AP.model.wellTrajectory.WellNumberWellTrajectoryInfoModel',
    autoLoad: true,
    pageSize: 200000,
    proxy: {
        type: 'ajax',
        url: context + '/wellTrajectoryController/loadWellTrajectoryJh',
        actionMethod: {
            read: 'POST'
        },
        start: 0,
        limit: 200000,
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