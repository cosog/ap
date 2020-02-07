Ext.define('AP.store.device.PumpSccjStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pumpSccjStore',
    model: 'AP.model.device.PumpSccjModel',
    autoLoad: true,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/pumpController/findBySCCJ',
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