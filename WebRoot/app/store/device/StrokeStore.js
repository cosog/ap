Ext.define('AP.store.device.StrokeStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.strokeStore',
    model: 'AP.model.device.StrokeModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/strokeController/findAllList',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: defaultPageSize,
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
            var strokesPanel_cyjxh_Id = Ext.getCmp('strokesPanel_cyjxh_Id');

            if (!Ext.isEmpty(strokesPanel_sccj_Id)) {
                strokesPanel_sccj_Id = strokesPanel_sccj_Id.getValue();
            }
            if (!Ext.isEmpty(strokesPanel_cyjxh_Id)) {
                strokesPanel_cyjxh_Id = strokesPanel_cyjxh_Id.rawValue;
            }
            var new_params = {
                sccj: strokesPanel_sccj_Id,
                cyjxh: strokesPanel_cyjxh_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});