Ext.define('AP.view.balanceRecord.BalanceRecordView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.balanceRecordView',
    layout: 'fit',
    id: 'BalanceRecordView_Id',
    initComponent: function () {
        var BalanceRecordPanel = Ext.create('AP.view.balanceRecord.BalanceRecordPanel');
        Ext.apply(this, {
            items: [BalanceRecordPanel]
        });
        this.callParent(arguments);
    }
});