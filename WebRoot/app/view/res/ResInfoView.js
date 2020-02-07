Ext.define("AP.view.res.ResInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.resInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var ResInfoTreeGridView = Ext.create('AP.view.res.ResInfoTreeGridView');
        Ext.apply(me, {
            items: [ResInfoTreeGridView]
        });
        me.callParent(arguments);
    }
});