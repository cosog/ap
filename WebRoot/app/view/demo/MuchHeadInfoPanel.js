Ext.define("AP.view.demo.MuchHeadInfoPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.muchHeadInfoPanel',
    id: "MuchHeadInfoPanel_id",
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        // 加载STORE
        var MuchHeadInfoStore = Ext.create('AP.store.demo.MuchHeadInfoStore');
        Ext.apply(me, {
            tbar: [{
                xtype: 'button',
                text: '查询',
                pressed: true,
                iconCls: 'search',
                handler: function (v, o) {
                    var MuchHeadGrid_Id = Ext.getCmp("MuchHeadGrid_Id");
                    MuchHeadGrid_Id.getStore().load();
                }
     }, '->', {
                id: 'MuchHeadInfoTotalCount_Id',
                xtype: 'component',
                tpl: '总记录数: {count}',
                style: 'margin-right:15px'
     }],
            listeners: {}
        });
        me.callParent(arguments);
    }
});