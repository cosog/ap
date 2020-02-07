Ext.define('AP.store.reportOut.ReportMonthProductionWellJssjStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.reportMonthProductionWellJssjStore',
    model: 'AP.model.reportOut.ReportProductionWellModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/reportProductionWellController/findJssjMonthReportProductionWell',
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