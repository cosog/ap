Ext.define('AP.store.device.SccjStrokeStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.sccjStrokeStore',
    model: 'AP.model.device.PumpSccjModel',
    autoLoad: true,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/strokeController/findBysccj',
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