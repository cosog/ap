Ext.define('AP.store.device.SccjStrokefrequencyStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.sccjStrokefrequencyStore',
    model: 'AP.model.device.PumpSccjModel',
    autoLoad: true,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/strokefrequencyController/findBysccj',
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