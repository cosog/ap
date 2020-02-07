Ext.define('AP.view.right.TestInfoGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.testInfoGridPanel',
    layout: "fit",
    border: false,
    forceFit: true,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selType: 'checkboxmodel',
    multiSelect: true,
    initComponent: function () {
        var TestCheckStore = Ext
            .create("AP.store.right.TestCheckStore");
        Ext.apply(this, {
            id: "TestInfoGridPanel_Id",
            store: TestCheckStore,
            columns: [{
                text: 'Name',
                dataIndex: 'name'
         }, {
                text: 'Email',
                dataIndex: 'email',
                flex: 1
         }, {
                text: 'Phone',
                dataIndex: 'phone'
         }, {
                xtype: 'checkcolumn',
                text: 'Active',
                dataIndex: 'active'
         }]
        });
        this.callParent(arguments);
    }
});