Ext.define('AP.view.demo.ForumThreadInfoPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.forumThreadInfoPanel',
    id: 'ForumThreadInfoPanel_Id',
    layout: "fit",
    autoScroll: true,
    border: false,
    selType: 'checkboxmodel',
    columnLines: true,
    multiSelect: true,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true,
        trackOver: false
    },
    initComponent: function () {
        var me = this;
        var ForumThreadInfoStore = Ext.create('AP.store.demo.ForumThreadInfoStore');
        ForumThreadInfoStore.guaranteeRange(0, 59);
        Ext.apply(me, {
            layout: 'fit',
            store: ForumThreadInfoStore,
            verticalScrollerType: 'paginggridscroller',
            //loadMask : true,
            disableSelection: false,
            columnLines: true, // 列线
            stripeRows: true, // 条纹行
            invalidateScrollerOnRefresh: false,
            // grid columns
            columns: [{
                xtype: 'rownumberer',
                width: 50,
                sortable: false
         }, {
                // id assigned so we can apply custom
                // css (e.g. .x-grid-cell-topic b {
                // color:#333 })
                // TODO: This poses an issue in
                // subclasses of Grid now because
                // Headers are now Components
                // therefore the id will be registered
                // in the ComponentManager and conflict.
                // Need a way to
                // add additional CSS classes to the
                // rendered cells.
                id: 'topic',
                text: "Topic",
                dataIndex: 'title',
                flex: 1,
                renderer: renderTopic,
                sortable: false
         }, {
                text: "Author",
                dataIndex: 'username',
                width: 100,
                hidden: false,
                sortable: true
         }, {
                text: "Replies",
                dataIndex: 'replycount',
                align: 'center',
                width: 70,
                sortable: false
         }, {
                id: 'last',
                text: "Last Post",
                dataIndex: 'lastpost',
                width: 130,
                renderer: Ext.util.Format
                    .dateRenderer('n/j/Y g:i A'),
                sortable: true
         }]
        });
        me.callParent(arguments);
    }
});

function renderTopic(value, p, record) {
    return Ext.String
        .format(
            '<a href="http://sencha.com/forum/showthread.php?t={2}" target="_blank">{0}</a>',
            value, record.data.forumtitle, record.getId(),
            record.data.forumid);
}