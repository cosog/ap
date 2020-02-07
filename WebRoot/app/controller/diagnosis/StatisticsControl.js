Ext.define('AP.model.diagnosis.StatisticsControl', {
    extend: 'Ext.app.Controller',
    alias: 'widget.Cont',
    stores: ['StatisticsSrore'],
    views: ['diagnosis.StatisticsViews'],
    init: function () {
        this.control({})
    }
})
showTypeFn = function (val) {
    alert();
}