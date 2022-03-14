module_add = "";
module_modify = "";
module_delete = "";
moduleIndex_ = [];
Ext.define('AP.view.role.RightModuleInfoTreeGridView', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.rightModuleInfoTreeGridView',
    layout: "fit",
    border: false,
    animate: true,
    enableDD: false,
    useArrows: false,
    rootVisible: false,
    autoScroll: true,
    forceFit: true,
    id: "RightModuleTreeInfoGridPanel_Id", // 模块编码加id，定义的命名规则moduleCode是从库里取的值对应
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },

    initComponent: function () {
        var moduleTree = this;
        var moduleStore = Ext.create("AP.store.role.RightModuleTreeInfoStore");
        // LoadingWin("正在加载数据");
        Ext.apply(moduleTree, {
            store: moduleStore,
            columns: [
                {
                    xtype: 'treecolumn',
//                    text: cosog.string.funcList,
                    text: '模块列表',
                    flex: 8,
                    align: 'left',
                    dataIndex: 'text'
                },
                {
                    header: 'mdIdaa',
                    hidden: true,
                    dataIndex: 'mdId'
                }]
        });

        this.callParent(arguments);
    },
    listeners: {
        checkchange: function (node, checked) {
            listenerCheck(node, checked);
        }
    }
});