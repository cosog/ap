Ext.define("AP.view.user.UserMuchHeadInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.muchHeadInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var UserMuchHeadInfoPanel = Ext.create('AP.view.user.UserMuchHeadInfoPanel');
        Ext.apply(me, {
            items: [{
                border: false,
                items: UserMuchHeadInfoPanel
     }]
        });
        me.callParent(arguments);
    }
});