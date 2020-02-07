Ext.define('AP.store.device.SccjCyjStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.sccjCyjStore',
    model: 'AP.model.device.SccjCyjModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/pumpingunitController/findByLiIdst',
        actionMethods: {
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
        beforeload: function (store, options) {
            var pumpingunitPanel_sccj_Id = Ext.getCmp('pumpingunitPanel_sccj_Id');
            if (!Ext.isEmpty(pumpingunitPanel_sccj_Id)) {
                pumpingunitPanel_sccj_Id = pumpingunitPanel_sccj_Id.rawValue;
            }
            var new_params = {
                sccj: pumpingunitPanel_sccj_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);

        }
    }
});