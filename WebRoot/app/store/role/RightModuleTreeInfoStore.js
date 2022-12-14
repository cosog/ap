Ext.define('AP.store.role.RightModuleTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.RightModuleTreeInfoStore',
    model: 'AP.model.role.RightModuleTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/moduleManagerController/constructRightModuleTreeGridTree',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    listeners: {
        beforeload: function (store, options) {
        },
        load: function (store, options, eOpts) {
        }
    }
});