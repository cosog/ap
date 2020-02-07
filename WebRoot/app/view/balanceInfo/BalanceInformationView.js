Ext.define('AP.view.balanceInfo.BalanceInformationView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.balanceInformationView',
    layout: 'fit',
    id: 'BalanceInformationView_Id',
    initComponent: function () {
        var BalanceInformationPanel = Ext.create('AP.view.balanceInfo.BalanceInformationPanel');
        Ext.apply(this, {
            items: [BalanceInformationPanel]
        });
        this.callParent(arguments);
    }
});