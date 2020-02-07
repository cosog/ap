Ext.define("AP.view.user.UserPanelInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.userPanelInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var UserInfoGridViewPanel = Ext.create('AP.view.user.UserInfoGridViewPanel');
        Ext.apply(me, {
            items: [{
                id: 'userPanelInfoView_Id',
                layout: 'fit',
                border: false,
                items: UserInfoGridViewPanel
     }]
        });
        me.callParent(arguments);
    }
});