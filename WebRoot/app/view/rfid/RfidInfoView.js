Ext.define("AP.view.rfid.RfidInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.rfidInfoView',
    layout: 'fit',
    //iframe : true,
    border: false,
    initComponent: function () {
        var me = this;
        var RfidInfoGridPanel = Ext.create('AP.view.rfid.RfidInfoGridPanel');
        Ext.apply(me, {
            items: [RfidInfoGridPanel]
        });
        me.callParent(arguments);
    }
});