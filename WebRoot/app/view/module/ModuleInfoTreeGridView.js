Ext.define('AP.view.module.ModuleInfoTreeGridView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.moduleInfoTreeGridView',
    layout: "fit",
    border: false,
    id: 'ModuleInfoTreeGridViewPanel_Id',
    initComponent: function () {
        var moduleTree = this;
        var moduleStore = Ext.create("AP.store.module.ModuleInfoStore");
        moduleStore.load();
        Ext.apply(moduleTree, {
            tbar: [{
                iconCls: 'icon-collapse-all', // 收缩按钮
                text: cosog.string.collapse,
                tooltip: {
                    text: cosog.string.collapseAll
                },
                handler: function () {
                    Ext.getCmp("moduleInfoTreeGridView_Id").collapseAll();
                }
         }, '-', {
                iconCls: 'icon-expand-all', // 展开按钮
                text: cosog.string.expand,
                tooltip: {
                    text: cosog.string.expandAll
                },
                handler: function () {
                    Ext.getCmp("moduleInfoTreeGridView_Id").expandAll();
                }
         }, '-', {
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
                tooltip: {
                    text: cosog.string.refreshAll
                },
                handler: function () {
                    moduleStore.load();
                }
         }, {
                fieldLabel: cosog.string.moduleName,
                id: 'module_name_Id',
                name: 'module_name',
                emptyText: cosog.string.queryModule,
                labelWidth: 60,
                labelAlign: 'right',
                width: 165,
                xtype: 'textfield'
         }, {
                xtype: 'button',
                text: cosog.string.search,
//                hidden: true,
                pressed: true,
                iconCls: 'search',
                handler: function () {
                    moduleStore.load();
                }
         }, '->', {
                xtype: 'button',
                itemId: 'addmoduleLableClassBtnId',
                id: 'addmoduleLableClassBtn_Id',
                action: 'addmoduleAction',
                text: cosog.string.add,
                iconCls: 'add'
         }, "-", {
                xtype: 'button',
                itemId: 'editmoduleLableClassBtnId',
                id: 'editmoduleLableClassBtn_Id',
                text: cosog.string.update,
                action: 'editmoduleInfoAction',
                disabled: false,
                iconCls: 'edit'
         }, "-", {
                xtype: 'button',
                itemId: 'delmoduleLableClassBtnId',
                id: 'delmoduleLableClassBtn_Id',
                disabled: false,
                action: 'delmoduleAction',
                text: cosog.string.del,
                iconCls: 'delete'
         }]
        });

        this.callParent(arguments);
    },
    listeners: {}
});