Ext.define('AP.store.frame.MainIframeStore2', {
    extend: 'Ext.data.TreeStore',
    storeId: 'MainIframeStore_Id2',
    autoLoad: false,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: context + '/moduleMenuController/obtainFunctionModuleList2',
        reader: {
            type: 'json',
            rootProperty: 'list',
            keepRawData: true
        },
        extraParams: {
            tid: '0'
        }
    },
    root: {
        text: '功能导航',
        expanded: true,
        mdId: '0'
    },
    listeners: {
        nodebeforeexpand: function (node, eOpts) {
            this.proxy.extraParams.tid = node.data.mdId;
        }
    }
});