Ext.define('AP.store.reportOut.ReportCustomProductionWellJssjStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.reportCustomProductionWellJssjStore',
    model: 'AP.model.reportOut.ReportProductionWellModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/reportProductionWellController/findJssjReportProductionWell',
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