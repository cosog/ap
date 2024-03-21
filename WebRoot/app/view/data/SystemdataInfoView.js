var loginUserDataDictionaryManagementModuleRight=getRoleModuleRight('DataDictionaryManagement');
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
        	tbar:[{
            	id: 'DataDictionaryManagementModuleViewFlag',
            	xtype: 'textfield',
                value: loginUserDataDictionaryManagementModuleRight.viewFlag,
                hidden: true
             },{
            	id: 'DataDictionaryManagementModuleEditFlag',
            	xtype: 'textfield',
                value: loginUserDataDictionaryManagementModuleRight.editFlag,
                hidden: true
             },{
            	id: 'DataDictionaryManagementModuleControlFlag',
            	xtype: 'textfield',
                value: loginUserDataDictionaryManagementModuleRight.controlFlag,
                hidden: true
            }],
            items: [
                SystemdataInfoGridPanel
            ]
        });
        me.callParent(arguments);
    }

});