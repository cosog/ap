Ext.define("AP.view.rfidSelect.RfidSelectInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.rfidSelectInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var RfidInfoGridPanel = Ext.create('AP.view.rfidSelect.RfidSelectInfoGridPanel');
        Ext.apply(me, {
            items: [RfidInfoGridPanel]
        });
        me.callParent(arguments);
    }
});