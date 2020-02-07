Ext.define('AP.view.rfid.RfidInfoGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.rfidInfoGridPanel',
    layout: "fit",
    id: "RfidInfoGridPanelView_Id",
    border: false,
    initComponent: function () {
        var rfidStore = Ext.create("AP.store.rfid.RfidInfoStore");
        Ext.apply(this, {
            tbar: ['->', {
                xtype: 'button',
                itemId: 'addrfidLabelClassBtnId',
                id: 'addrfidLabelClassBtn_Id',
                action: 'addrfidAction',
                text: "创建",
                iconCls: 'add',
                handler: function () {
                    addrfidInfo();
                }
         }, "-", {
                xtype: 'button',
                itemId: 'editrfidLabelClassBtnId',
                id: 'editrfidLabelClassBtn_Id',
                text: "修改",
                action: 'editrfidInfoAction',
                disabled: true,
                iconCls: 'edit',
                handler: function () {
                    modifyrfidInfo();
                }
         }, "-", {
                xtype: 'button',
                itemId: 'delrfidLabelClassBtnId',
                id: 'delrfidLabelClassBtn_Id',
                disabled: true,
                action: 'delrfidAction',
                text: "删除",
                iconCls: 'delete',
                handler: function () {
                    delrfidInfo();
                }
         }]
        });

        this.callParent(arguments);
    }
});