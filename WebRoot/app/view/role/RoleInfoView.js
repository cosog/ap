Ext.define("AP.view.role.RoleInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.roleInfoView',
    layout: 'border',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var RoleInfoGridPanel = Ext.create('AP.view.role.RoleInfoGridPanel');
        var RightModuleInfoGridPanel = Ext.create('AP.view.role.RightModuleInfoTreeGridView');
        Ext.apply(me, {
        	items: [{
        		region:'west',
        		width:'75%',
        		layout: "fit",
        		items:RoleInfoGridPanel
        	},{
        		region:'center',
        		title:'权限角色授予',
        		layout: "fit",
        		items:RightModuleInfoGridPanel,
        		bbar: ['->', {
                    xtype: 'button',
                    itemId: 'addRightModuleLableClassBtnId',
                    id: 'addRightModuleLableClassBtn_Id',
                    text: '保存',
                    iconCls: 'save',
                    pressed: true,
                    handler: function () {
                    	grantRolePermission();
                    }
        		}, {
                    xtype: 'tbspacer',
                    flex: 1
        		}]
        	}]
        });
        me.callParent(arguments);
    }

});