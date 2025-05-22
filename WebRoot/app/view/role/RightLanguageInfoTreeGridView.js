language_add = "";
language_modify = "";
language_delete = "";
languageIndex_ = [];
Ext.define('AP.view.role.RightLanguageInfoTreeGridView', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.rightLanguageInfoTreeGridView',
    layout: "fit",
    border: false,
    animate: true,
    enableDD: false,
    useArrows: false,
    rootVisible: false,
    autoScroll: true,
    forceFit: true,
    id: "RightLanguageTreeInfoGridPanel_Id",
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
        forceFit: true
    },
    initComponent: function () {
        var languageTree = this;
        var languageStore = Ext.create("AP.store.role.RightLanguageTreeInfoStore");
        Ext.apply(languageTree, {
            store: languageStore,
            selType:'',
            columns: [{
            	xtype: 'treecolumn',
            	text: loginUserLanguageResource.language,
            	flex: 8,
            	align: 'left',
            	dataIndex: 'text'
            },{
            	header: 'languageId',
            	hidden: true,
            	dataIndex: 'languageId'
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