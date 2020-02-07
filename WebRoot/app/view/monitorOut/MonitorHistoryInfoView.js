/*******************************************************************************
 * 定义动态监测采出井的视图
 *
 * @author gao
 *
 */

Ext.define("AP.view.monitorOut.MonitorHistoryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monitorHistoryInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var MonitorPumpingUnitHistoryPanel = Ext.create('AP.view.monitorOut.MonitorPumpingUnitHistoryPanel');
        Ext.apply(me, {
            items: [{
                xtype: 'tabpanel',
                activeTab: 0,
                border: false,
                tabPosition: 'bottom', // 表示该tab位于底部,如果想让tab位于顶部，修改为top即可
                items: [{
                        title: cosog.string.pumpUnit,
                        layout: "fit",
                        id: 'MonitorPumpingUnitHistory',
                        border: false,
                        items: [MonitorPumpingUnitHistoryPanel]
              }
             ]
            }]
        });
        me.callParent(arguments);
    }

});