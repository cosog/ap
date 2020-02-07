Ext.define('AP.model.demo.ForumThreadScrollInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
            'title', 'forumtitle', 'forumid', 'username', {
            name: 'replycount',
            type: 'int'
            }, {
            name: 'lastpost',
            mapping: 'lastpost',
            type: 'date',
            dateFormat: 'timestamp'
            },
            'lastposter', 'excerpt', 'threadid'
        ]
});