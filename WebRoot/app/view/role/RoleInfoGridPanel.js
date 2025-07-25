Ext.define('AP.view.role.RoleInfoGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.roleInfoGridPanel',
    id: 'RoleInfoGridPanelView_id',
    layout: "fit",
    border: false,
    initComponent: function () {
        var roleStore = Ext.create("AP.store.role.RoleInfoStore");
        Ext.apply(this, {
        	tbar: [{
                xtype: "hidden",
                id: 'currentUserRoleId_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'currentUserRoleLevel_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'currentUserRoleShowLevel_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'currentUserRoleVideoKeyEdit_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'currentUserRoleLanguageEdit_Id',
                value: 0
            },{
                xtype: "hidden",
                id: 'addRoleFlag_Id',
                value: 0
            },{
                xtype: 'button',
                text: loginUserLanguageResource.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	var gridPanel = Ext.getCmp("RoleInfoGridPanel_Id");
                    if (isNotVal(gridPanel)) {
                    	gridPanel.getStore().load();
                    }else{
                    	Ext.create('AP.store.role.RoleInfoStore');
                    }
                }
    		},{
                id: 'RoleName_Id',
                fieldLabel: loginUserLanguageResource.roleName,
                name: 'RoleName',
                labelWidth: (getLabelWidth(loginUserLanguageResource.roleName,loginUserLanguage)),
                width: (getLabelWidth(loginUserLanguageResource.roleName,loginUserLanguage)+100),
                labelAlign: 'right',
                xtype: 'textfield'
    		}, {
                xtype: 'button',
                name: 'RoleNameBtn_Id',
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                handler: function () {
                    roleStore.load();
                }
    		}, '->', {
                xtype: 'button',
                itemId: 'addroleLabelClassBtnId',
                id: 'addroleLabelClassBtn_Id',
                action: 'addroleAction',
                text: loginUserLanguageResource.add,
                disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                iconCls: 'add'
    		},"-", {
                xtype: 'button',
                text: loginUserLanguageResource.exportData,
                iconCls: 'export',
                disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                hidden: false,
                handler: function (v, o) {
                	exportRoleCompleteData();
                }
            },"-",{
            	xtype: 'button',
    			text: loginUserLanguageResource.importData,
    			disabled:loginUserRoleManagerModuleRight.editFlag!=1,
    			iconCls: 'import',
    			handler: function (v, o) {
    				var window = Ext.create("AP.view.role.ImportRoleWindow");
                    window.show();
    			}
            }]
        });
        this.callParent(arguments);
    }
});

function exportRoleCompleteData(){
	var url = context + '/roleManagerController/exportRoleCompleteData';
	
	var timestamp=new Date().getTime();
	var key='exportRoleCompleteData'+'_'+timestamp;
	var maskPanelId='RoleInfoGridPanelView_id';
	
	var param = "&recordCount=10000" 
    + "&fileName=" + URLencode(URLencode(loginUserLanguageResource.roleExportFileName)) 
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
//    openExcelWindow(url + '?flag=true' + param);
    downloadFile(url + '?flag=true' + param);
}