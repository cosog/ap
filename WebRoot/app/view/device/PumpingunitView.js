Ext.define('AP.view.device.PumpingunitView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.pumpingunitView',
    layout: 'fit',
    id: 'pumpingunitViewId',
    initComponent: function () {
        var PumpingunitPanel = Ext.create('AP.view.device.PumpingunitPanel');
        Ext.apply(this, {
            title: cosog.string.pumpUnit,
            items: [PumpingunitPanel]
        });
        this.callParent(arguments);
    }
});