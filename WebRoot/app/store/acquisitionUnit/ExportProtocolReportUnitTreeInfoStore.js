Ext.define('AP.store.acquisitionUnit.ExportProtocolReportUnitTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.exportProtocolReportUnitTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/exportReportUnitTreeData',
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
        	var gridPanel = Ext.getCmp("ExportProtocolReportUnitTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ExportProtocolReportUnitTreeGridPanel_Id",
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
                    	text: loginUserLanguageResource.alarmUnitList,
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
                var panel = Ext.getCmp("ProtocolExportReportUnitPanel_Id");
                panel.add(gridPanel);
            }
        }
    }
});