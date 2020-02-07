Ext.define("AP.view.demo.MuchHeadInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.muchHeadInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var MuchHeadInfoPanel = Ext.create('AP.view.demo.MuchHeadInfoPanel');
        Ext.apply(me, {
            items: [{
                xtype: 'tabpanel',
                activeTab: 0,
                border: false,
                tabPosition: 'bottom', // 表示该tab位于底部,如果想让tab位于顶部，修改为top即可
                items: [
                    {
                        title: "抽油机",
                        layout: "fit",
                        id: 'MuchHeadTab',
                        border: false,
                        items: MuchHeadInfoPanel
              }
             ]
     }]
        });
        me.callParent(arguments);
    }

});