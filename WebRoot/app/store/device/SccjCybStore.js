Ext.define('AP.store.device.SccjCybStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.sccjCyjStore',
    model: 'AP.model.device.SccjCybModel',
    autoLoad: true,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/pumpController/findByLiIdst',
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
            var pumpPanel_cybxh_Id = Ext.getCmp('pumpPanel_cybxh_Id');
            if (!Ext.isEmpty(pumpPanel_cybxh_Id)) {
                pumpPanel_cybxh_Id = pumpPanel_cybxh_Id.rawValue;
            }
            var new_params = {
                sccj: pumpPanel_sccj_Id,
                cybxh: pumpPanel_cybxh_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);

        }
    }
});