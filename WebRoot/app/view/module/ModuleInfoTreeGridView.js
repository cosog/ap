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
                text: loginUserLanguageResource.collapse,
                tooltip: {
                    text: loginUserLanguageResource.collapseAll
                },
                handler: function () {
                    Ext.getCmp("moduleInfoTreeGridView_Id").collapseAll();
                }
            }, '-', {
                iconCls: 'icon-expand-all', // 展开按钮
                text: loginUserLanguageResource.expand,
                tooltip: {
                    text: loginUserLanguageResource.expandAll
                },
                handler: function () {
                    Ext.getCmp("moduleInfoTreeGridView_Id").expandAll();
                }
            }, '-', {
                iconCls: 'note-refresh',
                text: loginUserLanguageResource.refresh,
                tooltip: {
                    text: loginUserLanguageResource.refresh
                },
                handler: function () {
                    moduleStore.load();
                }
            }, {
                fieldLabel: loginUserLanguageResource.moduleName,
                id: 'module_name_Id',
                name: 'module_name',
                labelWidth: getStringLength(loginUserLanguageResource.moduleName)*8,
                labelAlign: 'right',
                width: (getStringLength(loginUserLanguageResource.moduleName)*8+105),
                xtype: 'textfield'
            }, {
                xtype: 'button',
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                handler: function () {
                    moduleStore.load();
                }
            }, '->', {
                xtype: 'button',
                itemId: 'addmoduleLableClassBtnId',
                id: 'addmoduleLableClassBtn_Id',
                action: 'addmoduleAction',
                text: loginUserLanguageResource.add,
                disabled:loginUserModuleManagementModuleRight.editFlag!=1,
                iconCls: 'add'
            }, "-", {
                xtype: 'button',
                itemId: 'editmoduleLableClassBtnId',
                id: 'editmoduleLableClassBtn_Id',
                text: loginUserLanguageResource.update,
                action: 'editmoduleInfoAction',
                disabled: false,
                disabled:loginUserModuleManagementModuleRight.editFlag!=1,
                iconCls: 'edit'
            }, "-", {
                xtype: 'button',
                itemId: 'delmoduleLableClassBtnId',
                id: 'delmoduleLableClassBtn_Id',
                disabled: false,
                action: 'delmoduleAction',
                text: loginUserLanguageResource.deleteData,
                disabled:loginUserModuleManagementModuleRight.editFlag!=1,
                iconCls: 'delete'
            }]
        });

        this.callParent(arguments);
    },
    listeners: {}
});