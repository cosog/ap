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
                labelWidth: (getStringLength(loginUserLanguageResource.roleName)*8),
                width: (getStringLength(loginUserLanguageResource.roleName)*8+100),
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
    		}]
        });
        this.callParent(arguments);
    }

});