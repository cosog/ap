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
                        	
                        },select( v, record, index, eOpts ){
//                        	Ext.getCmp("ModbusProtocolAddrMappingConfigSelectRow_Id").setValue(index);
//                        	if(record.data.classes==0){
//                        		if(isNotVal(record.data.children) && record.data.children.length>0){
//                        			CreateModbusProtocolAddrMappingItemsConfigInfoTable(record.data.children[0].text,record.data.children[0].classes,record.data.children[0].code);
//                        		}else{
//                        			Ext.getCmp("ModbusProtocolAddrMappingItemsConfigPanel_Id").setTitle('采控项');
//                        			if(protocolConfigAddrMappingItemsHandsontableHelper!=null && protocolConfigAddrMappingItemsHandsontableHelper.hot!=undefined){
//                        				protocolConfigAddrMappingItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
//                        			}
//                        			if(protocolAddrMappingItemsMeaningConfigHandsontableHelper!=null && protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot!=undefined){
//                        				protocolAddrMappingItemsMeaningConfigHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
//                        			}
//                        		}
//                        	}else if(record.data.classes==1){
//                        		CreateModbusProtocolAddrMappingItemsConfigInfoTable(record.data.text,record.data.classes,record.data.code);
//                        	}
//                        	CreateProtocolConfigAddrMappingPropertiesInfoTable(record.data);
                        	CreateDatabaseColumnMappingTable();
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