Ext.define('AP.view.right.RightOrgInfoView', {
    extend: 'Ext.tree.Panel',
    id: 'RightOrgInfoView_Id',
    alias: 'widget.rightOrgInfoView',
    border: false,
    forceFit: true,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    layout: 'fit',
    //enableDD : false,
    useArrows: true,
    rootVisible: false,
    autoScroll: true,
    animate: true,
    initComponent: function () {
        var righOrgTree = this;
        var RightOrgInfoStore = Ext.create("AP.store.right.RightOrgInfoStore");
        Ext.apply(righOrgTree, {
            store: RightOrgInfoStore,
            tbar: [{
                iconCls: 'icon-collapse-all', // 收缩按钮
                tooltip: {
                    text: cosog.string.collapseAll
                },
                handler: function () {
                    righOrgTree.collapseAll();
                }
         }, '-', {
                iconCls: 'icon-expand-all', // 展开按钮
                tooltip: {
                    text: cosog.string.expandAll
                },
                handler: function () {
                    righOrgTree.expandAll();
                }
         }, '-', {
                iconCls: 'note-refresh',
                tooltip: {
                    text: cosog.string.refreshAll
                },
                handler: function () {
                    righOrgTree.store.load();
                }
         }]

        });
        this.callParent(arguments);
    }

});