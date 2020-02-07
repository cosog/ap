Ext.define("AP.view.PSToFS.ResultInfoView", { // 定义地面功图查询panel
    extend: 'Ext.panel.Panel', // 继承
    alias: 'widget.PSToFSResultInfoView', // 定义别名
    id: 'PSToFSResultInfoView_Id', //模块编号
    layout: 'fit', // 适应屏幕大小
    border: false,
    maskElement: 'body',
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
                items: [{
                border: false,
                layout: 'fit',
                id: 'PSToFSResultContent',
                autoScroll: true,
                html: '<div id="PSToFSResultContainer" class="hbox"></div>',
                listeners: {
                    render: function (p, o, i, c) {}
                }
                }],
                listeners: {
                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                }
            }
        });
        me.callParent(arguments);
    }
});
