Ext.define('AP.model.demo.MuchHeadInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
            'week', 'smokesales', 'smokedist', 'winesales', 'winedist', 'sumdist', 'fruits', 'blue', 'xing', 'tea'
        ],
    idProperty: 'threadid'
});