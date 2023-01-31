Ext.define('AP.store.acquisitionUnit.DatabaseColumnProtocolTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.databaseColumnProtocolTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData',
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
        	var treeGridPanel = Ext.getCmp("DatabaseColumnProtocolTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "DatabaseColumnProtocolTreeGridPanel_Id",
//                    layout: "fit",
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
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '协议列表',
                        flex: 8,
                        align: 'left',
                        dataIndex: 'text'
                    },{
                        header: 'id',
                        hidden: true,
                        dataIndex: 'id'
                    }],
                    listeners: {
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },
                        select( v, record, index, eOpts ){
                        	var protocolCode="";
                        	if(record.data.classes==1){
                        		protocolCode=record.data.code;
                        	}
                        	CreateDatabaseColumnMappingTable(record.data.classes,record.data.deviceType,protocolCode);
                        }
                    }

                });
                var panel = Ext.getCmp("DatabaseColumnMappingTableLeftTreePanel_Id");
                panel.add(treeGridPanel);
            }
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(0, true);
        }
    }
});