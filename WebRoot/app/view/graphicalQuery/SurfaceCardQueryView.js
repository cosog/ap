Ext.define("AP.view.graphicalQuery.SurfaceCardQueryView", {
    extend: 'Ext.panel.Panel', // 继承
    alias: 'widget.SurfaceCardQueryView', // 定义别名
    layout: 'fit', // 适应屏幕大小
    border: false, //
    initComponent: function () {
        var me = this;
        var SurfaceCardPanel = Ext.create('AP.view.graphicalQuery.SurfaceCardPanel');
        Ext.apply(me, {
            items: [{
                xtype: 'tabpanel', // 定义一个tab用来存放抽油机、自喷等
                activeTab: 0,
                border: false,
                layout: 'fit',
                tabPosition: 'bottom', // tab位置
                items: [{
                    title: cosog.string.pumpUnit, // tab显示文字，关联WebRoot\scripts\extjs\locale\ext-lang-zh_CN.js
                    id: 'SurfaceCardPumpingUnit',
                    layout: "fit",
                    border: false,
                    items: SurfaceCardPanel //引用SurfaceCardPanel
                }]
            }]
        });

        me.callParent(arguments);
    }
});