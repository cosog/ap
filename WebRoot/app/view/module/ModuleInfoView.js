var loginUserModuleManagementModuleRight=getRoleModuleRight('ModuleManagement');
Ext.define("AP.view.module.ModuleInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.moduleInfoView',
    layout: 'fit',
    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;
        var ModuleInfoTreeGridView = Ext.create('AP.view.module.ModuleInfoTreeGridView');
        Ext.apply(me, {
        	tbar:[{
            	id: 'ModuleManagementModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserModuleManagementModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'ModuleManagementModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserModuleManagementModuleRight.editFlag,
                hidden: true
             },{
            	id: 'ModuleManagementModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserModuleManagementModuleRight.controlFlag,
                hidden: true
            }],
            items: [ModuleInfoTreeGridView]
        });
        me.callParent(arguments);
    }

});