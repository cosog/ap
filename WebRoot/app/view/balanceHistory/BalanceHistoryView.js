/*******************************************************************************
 * 平衡历史查询视图
 *
 * @author zhao
 *
 */
Ext.define("AP.view.balanceHistory.BalanceHistoryView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.balanceHistoryMonitorView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var HistoryTorqueMaxValuePanel = Ext.create('AP.view.balanceHistory.HistoryTorqueMaxValueView');
        Ext.apply(me, {
            items: [{
                id: 'BalanceHistoryTab_Id',
                xtype: 'tabpanel',
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                        title: cosog.string.netTorqueMethod,
                        layout: "fit",
                        id: 'HistoryTorqueMaxValue_Id',
                        border: false,
                        items: [HistoryTorqueMaxValuePanel]
                		}]
            }]
        });
        me.callParent(arguments);
    
    }

});