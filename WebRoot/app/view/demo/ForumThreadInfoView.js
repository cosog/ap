Ext.define("AP.view.demo.ForumThreadInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.forumThreadInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var ForumThreadScrollInfoPanel = Ext.create('AP.view.demo.ForumThreadScrollInfoPanel');
        Ext.apply(me, {
            items: ForumThreadScrollInfoPanel
        });
        me.callParent(arguments);
    }

});