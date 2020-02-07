Ext.define("AP.view.res.ResHeadInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.resHeadInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var ResHeadInfoTreeGridView = Ext.create("AP.view.res.ResHeadInfoGridPanelView");
        Ext.apply(me, {
            items: [{
                    layout: 'fit',
                    border: false,
                    items: ResHeadInfoTreeGridView
      }]
        });
        me.callParent(arguments);
    }
});