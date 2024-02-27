tab_add = "";
tab_modify = "";
tab_delete = "";
tabIndex_ = [];
Ext.define('AP.view.role.RightTabInfoTreeGridView', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.rightTabInfoTreeGridView',
    layout: "fit",
    border: false,
    animate: true,
    enableDD: false,
    useArrows: false,
    rootVisible: false,
    autoScroll: true,
    forceFit: true,
    id: "RightTabTreeInfoGridPanel_Id", // 模块编码加id，定义的命名规则tabCode是从库里取的值对应
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    initComponent: function () {
        var tabTree = this;
        var tabStore = Ext.create("AP.store.role.RightTabTreeInfoStore");
        Ext.apply(tabTree, {
            store: tabStore,
            columns: [{
            	xtype: 'treecolumn',
            	text: '标签列表',
            	flex: 8,
            	align: 'left',
            	dataIndex: 'text'
            },{
            	header: 'tabIdaa',
            	hidden: true,
            	dataIndex: 'tabId'
            }]
        });
        this.callParent(arguments);
    },
    listeners: {
        checkchange: function (node, checked) {
//            listenerCheck(node, checked);
        }
    }
});