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
            data: [[0, cosog.string.dataModuleName], [1, cosog.string.dataModuleCode]]
        });
        var sysdatacomboxsimp = new Ext.form.ComboBox({
            id: 'sysdatacomboxfield_Id',
            value: 0,
            fieldLabel: cosog.string.dataType,
            allowBlank: false,
            emptyText: cosog.string.dataCheckType,
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
                text: cosog.string.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	Ext.getCmp('sysdatacomboxfield_Id').setValue(0);
                	Ext.getCmp('sysdatacomboxfield_Id').setRawValue(cosog.string.dataModuleName);
                	Ext.getCmp('sysname_Id').setRawValue('');
                	reFreshg("SystemdataInfoGridPanelId");
                }
    		},'-',sysdatacomboxsimp,'-', {
                xtype: 'textfield',
                fieldLabel: '&nbsp;' + cosog.string.name,
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
                text: cosog.string.search,
                iconCls: 'search',
                handler: function () {
                    reFreshg("SystemdataInfoGridPanelId");
                }
         }, '->', {
                xtype: 'button',
                itemId: 'addBtnId',
                id: 'addSystemdataInfoAction_Id',
                action: 'addSystemdataInfoAction',
                text: cosog.string.add,
                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                iconCls: 'add'
         }, '-', {
                xtype: 'button',
                itemId: 'editBtnId',
                id: 'editSDBtnId',
                text: cosog.string.update,
                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                scope: this,
                action: 'editSystemdataInfoAction',
                disabled: false,
                iconCls: 'edit'

         }, '-', {
                xtype: 'button',
                itemId: 'delBtnId',
                id: 'delSDBtnId',
                disabled: false,
                action: 'delSystemdataInfoAction',
                text: cosog.string.del,
                disabled:loginUserDataDictionaryManagementModuleRight.editFlag!=1,
                iconCls: 'delete'
         }]
        });
        this.callParent(arguments);
        Ext.getCmp("sysname_Id").focus(true, true);
    }

});