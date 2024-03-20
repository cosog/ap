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
        	var deviceTypeIds='';
        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        	if(tabTreeGridPanelSelection.length>0){
        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
        	}
        	var new_params = {
        			deviceTypeIds: deviceTypeIds
                };
           Ext.apply(store.proxy.extraParams, new_params);
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
                        	
                        },
                        select( v, record, index, eOpts ){
                        	var protocolName='';
                        	var protocolCode="";
                        	var classes=1;
                        	if(record.data.classes==1){
                        		protocolCode=record.data.code;
                        		protocolName=record.data.text;
                        		classes=record.data.classes;
                        	}else{
                        		if(record.childNodes.length>0){
                        			protocolCode=record.childNodes[0].data.code;
                        			protocolName=record.childNodes[0].data.text;
                        			classes=record.childNodes[0].data.classes;
                        		}
                        	}
                        	CreateDatabaseColumnMappingTable(classes,record.data.deviceType,protocolCode,protocolName);
                        	
                        	var databaseColumnMappingTableRunStatusItemsGridPanel = Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsGridPanel_Id");
                        	if (isNotVal(databaseColumnMappingTableRunStatusItemsGridPanel)) {
                        		databaseColumnMappingTableRunStatusItemsGridPanel.getStore().load();
                        	}else{
                        		Ext.create('AP.store.acquisitionUnit.DatabaseColumnMappingTableRunStatusItemsStore');
                        	}
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