Ext.define('AP.store.device.PumpingunitCssjStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pumpingunitCssjStore',
    model: 'AP.model.device.PumpingunitCssjModel',
    autoLoad: true,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/pumpingunitController/findBysccjList',
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
    }
});