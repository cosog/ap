Ext.define('AP.store.device.PumpingunitCyjxhStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PumpingunitCyjxhStore',
    model: 'AP.model.device.PumpingunitCyjxhModel',
    autoLoad: false,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/pumpingunitController/findBycyjxhList',
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
            var sccj_Id = Ext.getCmp('sccj_Id');
            if (!Ext.isEmpty(sccj_Id)) {
                sccj_Id = sccj_Id.rawValue;
            }
            var new_params = {
                sccj: strokesPanel_sccj_Id,
                sccj: sccj_Id

            };
            Ext.apply(store.proxy.extraParams, new_params);

        }
    }
});