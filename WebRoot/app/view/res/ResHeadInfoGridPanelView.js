Ext.define('AP.view.res.ResHeadInfoGridPanelView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.resHeadInfoGridPanelView',
    layout: "fit",
    border: false,
    id: 'ResHeadInfoGridPanelView_Id',
    initComponent: function () {
        var me = this;
        var ResHeadInfoStore = Ext.create("AP.store.res.ResHeadInfoStore");
        ResHeadInfoStore.load();
        Ext.apply(me, {
            tbar: [{
                iconCls: 'icon-collapse-all', // 收缩按钮
                text: cosog.string.collapse,
                tooltip: {
                    text: cosog.string.collapseAll
                },
                handler: function () {
                    Ext.getCmp("ResHeadInfoGridPanel_Id").collapseAll();
                }
         }, '-', {
                iconCls: 'icon-expand-all', // 展开按钮
                text: cosog.string.expand,
                tooltip: {
                    text: cosog.string.expandAll
                },
                handler: function () {
                    Ext.getCmp("ResHeadInfoGridPanel_Id").expandAll();
                }
         }, '-', {
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
                tooltip: {
                    text: cosog.string.refreshAll
                },
                handler: function () {
                    ResHeadInfoStore.load();
                }
         }, {
                fieldLabel: cosog.string.resName,
                id: 'res_name_Id',
                name: 'res_name',
                emptyText: cosog.string.qyeryRes,
                labelWidth: 70,
                labelAlign: 'right',
                width: 175,
                xtype: 'textfield'
         }, {
                xtype: 'button',
                text: cosog.string.search,
//                hidden: true,
                pressed: true,
                iconCls: 'search',
                handler: function () {
                    ResHeadInfoStore.load();
                }
         }, '->', {
                xtype: 'button',
                itemId: 'addresLableClassBtnId',
                id: 'addresLableClassBtn_Id',
                action: 'addresAction',
                text: cosog.string.add,
                iconCls: 'add'
         }, "-", {
                xtype: 'button',
                itemId: 'editresLableClassBtnId',
                id: 'editresLableClassBtn_Id',
                text: cosog.string.update,
                action: 'editresInfoAction',
                disabled: false,
                iconCls: 'edit'
         }, "-", {
                xtype: 'button',
                itemId: 'delresLableClassBtnId',
                id: 'delresLableClassBtn_Id',
                disabled: false,
                action: 'delresAction',
                text: cosog.string.del,
                iconCls: 'delete'
         }]
        });
        this.callParent(arguments);

    },
    listeners: {}

});