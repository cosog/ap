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
                id: 'currentUserRoleFlag_Id',
                value: 0
            },{
                xtype: 'button',
                text: cosog.string.refresh,
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
                fieldLabel: cosog.string.roleName,
                name: 'RoleName',
                emptyText: cosog.string.queryRole,
                labelWidth: 60,
                width: 165,
                labelAlign: 'right',
                xtype: 'textfield'
    		}, {
                xtype: 'button',
                name: 'RoleNameBtn_Id',
                text: cosog.string.search,
                iconCls: 'search',
                handler: function () {
                    roleStore.load();
                }
    		}, '->', {
                xtype: 'button',
                itemId: 'addroleLabelClassBtnId',
                id: 'addroleLabelClassBtn_Id',
                action: 'addroleAction',
                text: cosog.string.add,
                iconCls: 'add'
    		}
//    		, "-", {
//                xtype: 'button',
//                itemId: 'editroleLabelClassBtnId',
//                id: 'editroleLabelClassBtn_Id',
//                text: cosog.string.update,
//                action: 'editroleInfoAction',
//                disabled: false,
//                iconCls: 'edit'
//    		}, "-", {
//                xtype: 'button',
//                itemId: 'delroleLabelClassBtnId',
//                id: 'delroleLabelClassBtn_Id',
//                disabled: false,
//                action: 'delroleAction',
//                text: cosog.string.del,
//                iconCls: 'delete'
//    		}
    		]
        });
        this.callParent(arguments);
    }

});