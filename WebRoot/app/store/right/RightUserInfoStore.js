Ext.define('AP.store.right.RightUserInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rightUserInfoStore',
    model: 'AP.model.right.RightUserInfoModel',
    autoLoad: true,
    pageSize: 1000000000,
    proxy: {
        type: 'ajax',
        url: context + '/userManagerController/doShowRightUsers',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: 100000000,
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData: true
        }
    },
    listeners: {
        beforeload: function (store, options) {
            var RightOrgInfo_Id = Ext.getCmp("RightOrgInfo_Id");

            if (!Ext.isEmpty(RightOrgInfo_Id)) {
                RightOrgInfo_Id = RightOrgInfo_Id.getValue();
            }
            var new_params = {
                orgId: RightOrgInfo_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});