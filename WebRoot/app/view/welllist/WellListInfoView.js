Ext.define("AP.view.welllist.WellListInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.wellListInfoView',
    layout: 'auto',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        //var singlePanel =Ext.create('AP.view.diagnosis.SinglePanel');
        var welllistinfoPanel = Ext.create('AP.view.welllist.WellListInfoPanel');
        Ext.apply(me, {
            width: 800,
            height: 800,
            layout: 'fit',
            title: '全部井数',
            items: [welllistinfoPanel]
        });
        me.callParent(arguments);
    }
});