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
//                hidden: true,
                pressed: true,
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
         }, "-", {
                xtype: 'button',
                itemId: 'editroleLabelClassBtnId',
                id: 'editroleLabelClassBtn_Id',
                text: cosog.string.update,
                action: 'editroleInfoAction',
                disabled: false,
                iconCls: 'edit'
         }, "-", {
                xtype: 'button',
                itemId: 'delroleLabelClassBtnId',
                id: 'delroleLabelClassBtn_Id',
                disabled: false,
                action: 'delroleAction',
                text: cosog.string.del,
                iconCls: 'delete'
         }]
        });
        this.callParent(arguments);
    }

});