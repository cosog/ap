Ext.define('AP.view.data.SystemdataInfoGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.systemdataInfoGridPanel',
    id: "SystemdataInfoGridPanelViewId",
    border: false,
    layout: 'fit',
    initComponent: function () {
        var geSystemdataInfoStore = Ext.create("AP.store.data.SystemdataInfoStore");
        
        var sysdatastore = new Ext.data.SimpleStore({
            fields: ['sysdataId', 'sysdataName'],
            data: [[0, loginUserLanguageResource.dataModuleName], [1, loginUserLanguageResource.dataModuleCode]]
        });
        var sysdatacomboxsimp = new Ext.form.ComboBox({
            id: 'sysdatacomboxfield_Id',
            value: 0,
            fieldLabel: loginUserLanguageResource.type,
            allowBlank: false,
            triggerAction: 'all',
            store: sysdatastore,
            labelWidth: 35,
            width: 155,
            displayField: 'sysdataName',
            valueField: 'sysdataId',
            mode: 'local'
        });
        Ext.apply(this, {
            tbar: [{
                xtype: 'button',
                text: loginUserLanguageResource.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	Ext.getCmp('sysdatacomboxfield_Id').setValue(0);
                	Ext.getCmp('sysdatacomboxfield_Id').setRawValue(loginUserLanguageResource.dataModuleName);
                	Ext.getCmp('sysname_Id').setRawValue('');
                	reFreshg("SystemdataInfoGridPanelId");
                }
    		},'-',sysdatacomboxsimp,'-', {
                xtype: 'textfield',
                fieldLabel: '&nbsp;' + loginUserLanguageResource.name,
                labelWidth: 35,
                width: 155,
                id: 'sysname_Id',
                name: 'sysdata_name',
                action: "findsysdatatextaction"
    		}, {
                xtype: "textfield",
                id: "sys_txt_find_ids",
                hidden: true
    		},'-', {
                xtype: 'button',
                id: "findSystemdataInfoId",
                text: loginUserLanguageResource.search,
                iconCls: 'search',
                handler: function () {
                    reFreshg("SystemdataInfoGridPanelId");
                }
    		}, '->', {
                xtype: 'button',
                itemId: 'addBtnId',
                id: 'addSystemdataInfoAction_Id',
                action: 'addSystemdataInfoAction',
                text: loginUserLanguageResource.add,
                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                iconCls: 'add'
    		}, '-', {
                xtype: 'button',
                itemId: 'editBtnId',
                id: 'editSDBtnId',
                text: loginUserLanguageResource.update,
                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                action: 'editSystemdataInfoAction',
                iconCls: 'edit'

    		}, '-', {
                xtype: 'button',
                itemId: 'delBtnId',
                id: 'delSDBtnId',
                disabled: false,
                action: 'delSystemdataInfoAction',
                text: loginUserLanguageResource.deleteData,
                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                iconCls: 'delete'
    		}]
        });
        this.callParent(arguments);
        Ext.getCmp("sysname_Id").focus(true, true);
    }

});