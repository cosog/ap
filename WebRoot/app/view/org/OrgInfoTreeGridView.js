Ext.define('AP.view.org.OrgInfoTreeGridView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.orgInfoTreeGridView',
    id: 'OrgInfoTreeGridViewPanel_Id',
    layout: "fit",
    border: false,
    initComponent: function () {
        var orgTree = this;
        var orgStore = Ext.create("AP.store.org.OrgInfoStore");
        //orgStore.load();
        Ext.apply(orgTree, {
            tbar: [{
                iconCls: 'icon-collapse-all', // 收缩按钮
                text: cosog.string.collapse,
                tooltip: {
                    text: cosog.string.collapseAll
                },
                handler: function () {
                    Ext.getCmp("OrgInfoTreeGridView_Id").collapseAll();
                }
         }, '-', {
                iconCls: 'icon-expand-all', // 展开按钮
                text: cosog.string.expand,
                tooltip: {
                    text: cosog.string.expandAll
                },
                handler: function () {
                    Ext.getCmp("OrgInfoTreeGridView_Id").expandAll();
                }
         }, '-', {
                iconCls: 'note-refresh',
                text: cosog.string.refresh,
                tooltip: {
                    text: cosog.string.refreshAll
                },
                handler: function () {
                    orgStore.proxy.extraParams.tid = 0;
                    orgStore.load();
                }
         }, {
                fieldLabel: cosog.string.orgName,
                id: 'org_name_Id',
                name: 'org_name',
                emptyText: cosog.string.queryOrg,
                labelWidth: 60,
                labelAlign: 'right',
                width: 165,
                xtype: 'textfield'
         }, {
                xtype: 'button',
                text: cosog.string.search,
//                hidden: true,
                pressed: true,
                iconCls: 'search',
                handler: function () {
                	orgStore.proxy.extraParams.tid = 0;
                    orgStore.load();
                }
         }, '->', {
                xtype: 'button',
                itemId: 'addOrgLableClassBtnId',
                id: 'addOrgLableClassBtn_Id',
                action: 'addOrgAction',
                text: cosog.string.add,
                iconCls: 'add'
         }, "-", {
                xtype: 'button',
                itemId: 'editOrgLableClassBtnId',
                id: 'editOrgLableClassBtn_Id',
                text: cosog.string.update,
                action: 'editOrgInfoAction',
                disabled: false,
                iconCls: 'edit'
         }, "-", {
                xtype: 'button',
                itemId: 'delOrgLableClassBtnId',
                id: 'delOrgLableClassBtn_Id',
                disabled: false,
                action: 'delOrgAction',
                text: cosog.string.del,
                iconCls: 'delete'
         }, "-", {
             xtype: 'button',
             itemId: 'saveCoordClassBtnId',
             id: 'saveCoordClassBtn_Id',
             disabled: false,
             hidden:true,
             action: 'saveOrgCoordAction',
             text: cosog.string.saveCoord,
             iconCls: 'save'
      }]
        });
        this.callParent(arguments);
    }
});