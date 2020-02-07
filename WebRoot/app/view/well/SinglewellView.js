Ext.define("AP.view.well.SinglewellView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.singlewellView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var SinglewellPanel = Ext.create('AP.view.well.SinglewellPanel');
        Ext.apply(me, {
            items: [SinglewellPanel]
        });
        me.callParent(arguments);
    }
});