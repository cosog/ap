Ext.define("AP.view.user.UserMuchHeadInfoPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.userMuchHeadInfoPanel',
    id: "UserMuchHeadInfoPanel_id",
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        // 加载STORE
        var UserMuchHeadInfoStore = Ext.create('AP.store.user.UserMuchHeadInfoStore');
        alert("ass")
        Ext.apply(me, {
            tbar: [{
                xtype: 'button',
                text: '查询',
                pressed: true,
                iconCls: 'search',
                handler: function (v, o) {
                    var MuchHeadGrid_Id = Ext.getCmp("UserMuchHeadGrid_Id");
                    MuchHeadGrid_Id.getStore().load();
                }
     }, '->', {
                id: 'UserMuchHeadInfoTotalCount_Id',
                xtype: 'component',
                tpl: '总记录数: {count}',
                style: 'margin-right:15px'
     }],
            listeners: {}
        });
        me.callParent(arguments);
    }
});