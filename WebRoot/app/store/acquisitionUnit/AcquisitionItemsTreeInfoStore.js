all_loading = "";

Ext.define('AP.store.acquisitionUnit.AcquisitionItemsTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.acquisitionItemsTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/constructAcquisitionItemsTreeData',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    listeners: {
        beforeload: function (store, options) {
        },
        load: function (store, options, eOpts) {
        	var acquisitionItemsTreeGridPanel = Ext.getCmp("acquisitionItemsTreeGridPanel_Id");
            if (!isNotVal(acquisitionItemsTreeGridPanel)) {
                acquisitionItemsTreeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "acquisitionItemsTreeGridPanel_Id",
                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [
                        {
                            xtype: 'treecolumn',
                            text: '采集项列表',
                            flex: 8,
                            align: 'left',
                            dataIndex: 'text'
                        },
                        {
                            header: 'jlbh',
                            hidden: true,
                            dataIndex: 'jlbh'
                        }],
                    listeners: {
                    	checkchange: function (node, checked) {
                            listenerCheck(node, checked);
                        }
                    }

                });
                var acquisitionItemsTreePanel = Ext.getCmp("acquisitionItemsTreePanel_Id");
                acquisitionItemsTreePanel.add(acquisitionItemsTreeGridPanel);
            }
            showAcquisitionUnitOwnItems(store);
        }
    }
});