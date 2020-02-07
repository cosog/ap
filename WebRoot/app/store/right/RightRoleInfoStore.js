Ext.define('AP.store.right.RightRoleInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rightRoleInfoStore',
    model: 'AP.model.right.RightRoleInfoModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/doShowRightRoles',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: 20,
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData: true
        }
    },
    listeners: {
        beforeload: function (store, options) {
            var RightUserNo_Id = Ext.getCmp('RightUserNo_Id');

            if (!Ext.isEmpty(RightUserNo_Id)) {
                RightUserNo_Id = RightUserNo_Id.getValue();
            }
            var new_params = {
                userNo: RightUserNo_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});