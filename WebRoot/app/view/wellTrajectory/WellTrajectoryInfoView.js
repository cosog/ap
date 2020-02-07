Ext.define("AP.view.wellTrajectory.WellTrajectoryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.wellTrajectoryInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var WellTrajectoryInfoPanel = Ext
            .create('AP.view.wellTrajectory.WellTrajectoryInfoPanel');
        Ext.apply(me, {
            items: [WellTrajectoryInfoPanel]
        });
        me.callParent(arguments);
    }
});