Ext.define("AP.view.reservoirProperty.ReservoirPropertyInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.reservoirPropertyInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var ReservoirPropertyInfoGridPanel = Ext.create('AP.view.reservoirProperty.ReservoirPropertyInfoGridPanel');
        Ext.apply(me, {
            items: [ReservoirPropertyInfoGridPanel]
        });
        me.callParent(arguments);
    }
});