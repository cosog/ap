Ext.define("AP.view.well.InjectionWellView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.InjectionWellView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {});
        me.callParent(arguments);
    }
});