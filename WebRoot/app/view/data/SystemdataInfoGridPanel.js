/**
 * author 钱邓水 数据字典面板信息 GridPanel
 */
Ext.define('AP.view.data.SystemdataInfoGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.systemdataInfoGridPanel',
    id: "SystemdataInfoGridPanelViewId",
    border: false,
    layout: 'fit',
    initComponent: function () {
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
        var geSystemdataInfoStore = Ext.create("AP.store.data.SystemdataInfoStore");
        Ext.apply(this, {
            tbar: [sysdatacomboxsimp, {
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
         }, {
                xtype: 'button',
                id: "findSystemdataInfoId",
                text: cosog.string.search,
//                hidden: true,
                pressed: true,
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
                iconCls: 'add'
         }, '-', {
                xtype: 'button',
                itemId: 'editBtnId',
                id: 'editSDBtnId',
                text: cosog.string.update,
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
                iconCls: 'delete'
         }]
        });
        this.callParent(arguments);
        Ext.getCmp("sysname_Id").focus(true, true);
    }

});