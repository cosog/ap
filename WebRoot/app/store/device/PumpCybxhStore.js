Ext.define('AP.store.device.PumpCybxhStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pumpCybxhStore',
    model: 'AP.model.device.PumpCybxhModel',
    autoLoad: false,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/pumpController/findBycybxh',
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
    listeners: {
        beforeload: function (store, options) {
            var pumpPanel_sccj_Id = Ext.getCmp('pumpPanel_sccj_Id');
            if (!Ext.isEmpty(pumpPanel_sccj_Id)) {
                pumpPanel_sccj_Id = pumpPanel_sccj_Id.rawValue;
            }
            var new_params = {
                sccj: pumpPanel_sccj_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});