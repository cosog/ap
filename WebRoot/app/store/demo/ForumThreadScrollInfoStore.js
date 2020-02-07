Ext.define('AP.store.demo.ForumThreadScrollInfoStore', {
    extend: 'Ext.data.Store',
    model: 'AP.model.demo.ForumThreadScrollInfoModel',
    buffered: true,
    leadingBufferZone: 120,
    pageSize: 30,
    autoDestroy: true,
    remoteSort: true,
    autoLoad: true,
    proxy: {
        // load using script tags for cross domain, if the data in on
        // the same domain as
        // this page, an Ajax proxy would be better
        type: 'ajax',
        url: context + '/app/data/grid.json',
        start: 0,
        limit: 30,
        reader: {
            rootProperty: 'topics',
            totalProperty: 'totalCount',
            keepRawData: true
        },
        simpleSortMode: true
    },
    listeners: {
        beforeload: function (store, options) {
            Ext.getCmp('ForumThreadScrollInfoPanel_Id').getSelectionModel().clearSelections();
        }
    }
});