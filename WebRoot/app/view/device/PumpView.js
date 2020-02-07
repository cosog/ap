Ext.define('AP.view.device.PumpView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.pumpView',
    layout: 'fit',
    // width : 600,
    // height : 500,
    id: 'pumpViewId',
    initComponent: function () {
        var pumpPanel = Ext.create('AP.view.device.PumpPanel');
        Ext.apply(this, {
            title: cosog.string.pump,
            items: [pumpPanel]
        });
        this.callParent(arguments);
    }
});