Ext.define('AP.store.reportOut.ReportCustomProductionWellJhStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.reportCustomProductionWellJhStore',
    model: 'AP.model.reportOut.ReportProductionWellModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/reportProductionWellController/findJhReportProductionWell',
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
            //alert("computePumpingUnitInstantjhh_Id");
        }
    }
});