Ext.define('AP.store.role.RightTabTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.RightTabTreeInfoStore',
    model: 'AP.model.role.RightTabTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/constructRightTabTreeGridTree',
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