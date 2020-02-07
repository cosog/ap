Ext.define("AP.view.demo.IndexView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.indexView',
    layout: 'fit',
    iframe: true,
    border: false,
    html: "<iframe src='../../images/zh_CN/index.jpg' width='100%' height='100%'/>",
    initComponent: function () {
        var me = this;
        Ext.apply(me, {

        });
        me.callParent(arguments);
    }

});