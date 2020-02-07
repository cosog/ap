Ext.define('AP.model.demo.ForumThreadInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
            'title', 'forumtitle', 'forumid', 'author',
        {
            name: 'replycount',
            type: 'int'
        },
        {
            name: 'lastpost',
            mapping: 'lastpost',
            type: 'date',
            dateFormat: 'timestamp'
        },
            'lastposter', 'username', 'threadid'
        ],
    idProperty: 'threadid'
});