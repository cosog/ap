Ext.define('AP.view.demo.ForumThreadScrollInfoPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.forumThreadScrollInfoPanel',
    id: 'ForumThreadScrollInfoPanel_Id',
    autoScroll: true,
    border: false,
    columnLines: true,
    forceFit: true,
    layout: 'fit',
    loadMask: true,
    multiSelect: true,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true,
        trackOver: false
    },
    initComponent: function () {
        var me = this;
        var ForumThreadInfoStore = Ext.create('AP.store.demo.ForumThreadScrollInfoStore');
        Ext.apply(me, {
            tbar: [{
                xtype: 'button',
                text: '查询',
                pressed: true,
                iconCls: 'search',
                handler: function (v, o) {
                    Ext.getCmp("ForumThreadScrollInfoPanel_Id").getStore().load();
                }
        }],
            store: ForumThreadInfoStore,
            // grid columns
            columns: [{
                xtype: 'rownumberer',
                width: 50,
                align: 'center',
                text: "序号",
                sortable: false
         }, {
                tdCls: 'x-grid-cell-topic',
                text: "Topic",
                dataIndex: 'title',
                //renderer : renderTopic,
                sortable: true
         }, {
                text: "Author",
                dataIndex: 'username',
                hidden: false,
                sortable: true
         }, {
                text: "Replies",
                dataIndex: 'replycount',
                align: 'center',
                sortable: false
         }, {
                id: 'last',
                text: "Last Post",
                dataIndex: 'lastpost',
                renderer: Ext.util.Format.dateRenderer('n/j/Y g:i A'),
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