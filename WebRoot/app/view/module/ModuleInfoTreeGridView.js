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
                labelWidth: getLabelWidth(loginUserLanguageResource.moduleName,loginUserLanguage),
                labelAlign: 'right',
                width: (getLabelWidth(loginUserLanguageResource.moduleName,loginUserLanguage)+105),
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
                hidden:true,
                disabled:loginUserModuleManagementModuleRight.editFlag!=1,
                iconCls: 'add'
            }, 
//            "-", 
            {
                xtype: 'button',
                itemId: 'editmoduleLableClassBtnId',
                id: 'editmoduleLableClassBtn_Id',
                text: loginUserLanguageResource.update,
                action: 'editmoduleInfoAction',
                disabled: false,
                disabled:loginUserModuleManagementModuleRight.editFlag!=1,
                iconCls: 'edit'
            }, 
//            "-", 
            {
                xtype: 'button',
                itemId: 'delmoduleLableClassBtnId',
                id: 'delmoduleLableClassBtn_Id',
                disabled: false,
                hidden:true,
                action: 'delmoduleAction',
                text: loginUserLanguageResource.deleteData,
                disabled:loginUserModuleManagementModuleRight.editFlag!=1,
                iconCls: 'delete'
            },"-", {
                xtype: 'button',
                text: loginUserLanguageResource.exportData,
                iconCls: 'export',
                disabled:loginUserModuleManagementModuleRight.editFlag!=1,
                hidden: false,
                handler: function (v, o) {
                	exportModuleCompleteData();
                }
            },"-",{
            	xtype: 'button',
    			text: loginUserLanguageResource.importData,
    			disabled:loginUserModuleManagementModuleRight.editFlag!=1,
    			iconCls: 'import',
    			handler: function (v, o) {
//    				var window = Ext.create("AP.view.data.ImportModuleWindow");
//                    window.show();
    			}
            }]
        });

        this.callParent(arguments);
    },
    listeners: {}
});

function exportModuleCompleteData(){
	var url = context + '/moduleManagerController/exportModuleCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportModuleCompleteData'+'_'+timestamp;
	var maskPanelId='ModuleInfoTreeGridViewPanel_Id';
	
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.ModuleExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}