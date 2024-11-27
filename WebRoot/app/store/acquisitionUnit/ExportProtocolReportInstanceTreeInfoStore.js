Ext.define('AP.store.acquisitionUnit.ExportProtocolReportInstanceTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.exportProtocolReportInstanceTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/exportProtocolReportInstanceTreeData',
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
        	var treeGridPanel = Ext.getCmp("ExportProtocolReportInstanceTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
                treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ExportProtocolReportInstanceTreeGridPanel_Id",
//                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: loginUserLanguageResource.alarmInstanceList,
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text',
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                        header: 'id',
                        hidden: true,
                        dataIndex: 'id'
                    }],
                    listeners: {
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },select( v, record, index, eOpts ){
                        	
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }
                });
                var panel = Ext.getCmp("ProtocolExportReportInstancePanel_Id");
                panel.add(treeGridPanel);
            }
        }
    }
});