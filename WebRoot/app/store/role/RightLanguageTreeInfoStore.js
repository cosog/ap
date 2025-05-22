Ext.define('AP.store.role.RightLanguageTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.RightLanguageTreeInfoStore',
    model: 'AP.model.role.RightLanguageTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/constructRightLanguageTreeGridTree',
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