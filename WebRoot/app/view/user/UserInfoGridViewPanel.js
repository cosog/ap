Ext.define('AP.view.user.UserInfoGridViewPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.userInfoGridViewPanel',
    layout: "fit",
    id: "UserInfoGridPanelView_Id",
    border: false,
    //forceFit : true,
    initComponent: function () {
        var userStore = Ext.create("AP.store.user.UserPanelInfoStore");
        Ext.apply(this, {
            tbar: [{
                id: 'UserName_Id',
                fieldLabel: cosog.string.userName,
                emptyText: cosog.string.queryUserName,
                labelWidth: 60,
                width: 165,
                labelAlign: 'right',
                xtype: 'textfield'
         }, {
                xtype: 'button',
                text: cosog.string.search,
                pressed: true,
                iconCls: 'search',
                handler: function () {
                    userStore.load();
                }
         }, '->', {
                xtype: 'button',
                itemId: 'addLableClassBtnId',
                id: 'addLableClassBtn_Id',
                action: 'addUserAction',
                text: cosog.string.add,
                iconCls: 'add'
       }, "-", {
                xtype: 'button',
                itemId: 'editLableClassBtnId',
                id: 'editLableClassBtn_Id',
                text: cosog.string.update,
                action: 'editUserInfoAction',
                disabled: false,
                iconCls: 'edit'
       }, "-", {
                xtype: 'button',
                itemId: 'delLableClassBtnId',
                id: 'delLableClassBtn_Id',
                disabled: false,
                action: 'delUserAction',
                text: cosog.string.del,
                iconCls: 'delete'
       }]
        });
        this.callParent(arguments);
    }

});