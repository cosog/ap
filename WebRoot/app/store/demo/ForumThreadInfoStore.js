Ext.define('AP.store.demo.ForumThreadInfoStore', {
    extend: 'Ext.data.Store',
    id: "ForumThreadInfoStore_Id",
    pageSize: 50,
    autoLoad: false,
    model: 'AP.model.demo.ForumThreadInfoModel',
    remoteSort: true,
    // allow the grid to interact with the paging scroller by buffering
    buffered: true,
    proxy: {
        // load using script tags for cross domain, if the data in on the same
        // domain as
        // this page, an HttpProxy would be better
        type: 'jsonp',
        url: 'http://www.sencha.com/forum/remote_topics/index.php',
        extraParams: {
            total: 5000
        },
        reader: {
            rootProperty: 'topics',
            totalProperty: 'totalCount',
            keepRawData: true
        },
        // sends single sort as multi parameter
        simpleSortMode: true
    },
    sorters: [{
        property: 'lastpost',
        direction: 'DESC'
   }],
    listeners: {
        load: function (store, options, eOpts) {

        },
        beforeload: function (store, options) {}
    }
});