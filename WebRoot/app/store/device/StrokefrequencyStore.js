Ext.define('AP.store.device.StrokefrequencyStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.strokefrequencyStore',
    model: 'AP.model.device.StrokefrequencyModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/strokefrequencyController/findAllList',
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
            var strokefrequencyPanel_sccj_Id = Ext.getCmp('strokefrequencyPanel_sccj_Id');
            var strokefrequencyPanel_cyjxh_Id = Ext.getCmp('strokefrequencyPanel_cyjxh_Id');

            if (!Ext.isEmpty(strokefrequencyPanel_sccj_Id)) {
                strokefrequencyPanel_sccj_Id = strokefrequencyPanel_sccj_Id.getValue();
            }
            if (!Ext.isEmpty(strokefrequencyPanel_cyjxh_Id)) {
                strokefrequencyPanel_cyjxh_Id = strokefrequencyPanel_cyjxh_Id.rawValue;
            }
            var new_params = {
                sccj: strokefrequencyPanel_sccj_Id,
                cyjxh: strokefrequencyPanel_cyjxh_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});