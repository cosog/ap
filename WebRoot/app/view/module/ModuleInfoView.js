Ext.define("AP.view.module.ModuleInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.moduleInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var ModuleInfoTreeGridView = Ext.create('AP.view.module.ModuleInfoTreeGridView');
        Ext.apply(me, {
            items: [ModuleInfoTreeGridView]
        });
        me.callParent(arguments);
    }

});