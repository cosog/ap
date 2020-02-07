Ext.define('AP.store.device.StrokeSccjStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.sccjCyjStore',
    model: 'AP.model.device.StrokeSccjModel',
    autoLoad: true,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/strokeController/findByLiIdst',
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
            var strokesPanel_sccj_Id = Ext.getCmp('strokesPanel_sccj_Id');
            if (!Ext.isEmpty(strokesPanel_sccj_Id)) {
                strokesPanel_sccj_Id = strokesPanel_sccj_Id.rawValue;
            }
            var new_params = {
                sccj: strokesPanel_sccj_Id

            };
            Ext.apply(store.proxy.extraParams, new_params);

        }
    }
});