/**
 * author 钱邓水
 * 系统数据字典管理
 */
Ext.define("AP.view.data.SystemdataInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.systemdataInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;

        var SystemdataInfoGridPanel = Ext.create('AP.view.data.SystemdataInfoGridPanel');
        Ext.apply(me, {
            items: [
                SystemdataInfoGridPanel
            ]
        });
        me.callParent(arguments);
    }

});