Ext.define('AP.store.acquisitionUnit.ExportProtocolTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.exportProtocolTreeInfoStore',
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
        	var treeGridPanel = Ext.getCmp("ExportProtocolTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ExportProtocolTreeGridPanel_Id",
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
                        	
                        },select( v, record, index, eOpts ){
                        	if(record.data.classes==1){
                        		Ext.getCmp('ExportProtocolWindowExportBtn_Id').enable();
                        	}else{
                        		Ext.getCmp('ExportProtocolWindowExportBtn_Id').disable();
                        	}
                        	var exportProtocolAcqUnitTreeGridPanel = Ext.getCmp("ExportProtocolAcqUnitTreeGridPanel_Id");
                        	if (isNotVal(exportProtocolAcqUnitTreeGridPanel)) {
                        		exportProtocolAcqUnitTreeGridPanel.getStore().load();
                        	}else{
                        		Ext.create('AP.store.acquisitionUnit.ExportProtocolAcqUnitTreeInfoStore');
                        	}
                        	
                        	var exportProtocolDisplayUnitTreeGridPanel = Ext.getCmp("ExportProtocolDisplayUnitTreeGridPanel_Id");
                        	if (isNotVal(exportProtocolDisplayUnitTreeGridPanel)) {
                        		exportProtocolDisplayUnitTreeGridPanel.getStore().load();
                        	}else{
                        		Ext.create('AP.store.acquisitionUnit.ExportProtocolDisplayUnitTreeInfoStore');
                        	}
                        	
                        	var exportProtocolAlarmUnitTreeGridPanel = Ext.getCmp("ExportProtocolAlarmUnitTreeGridPanel_Id");
                        	if (isNotVal(exportProtocolAlarmUnitTreeGridPanel)) {
                        		exportProtocolAlarmUnitTreeGridPanel.getStore().load();
                        	}else{
                        		Ext.create('AP.store.acquisitionUnit.ExportProtocolAlarmUnitTreeInfoStore');
                        	}
                        	
                        	var exportProtocolAcqInstanceTreeGridPanel = Ext.getCmp("ExportProtocolAcqInstanceTreeGridPanel_Id");
                        	if (isNotVal(exportProtocolAcqInstanceTreeGridPanel)) {
                        		exportProtocolAcqInstanceTreeGridPanel.getStore().load();
                        	}else{
                        		Ext.create('AP.store.acquisitionUnit.ExportProtocolAcqInstanceTreeInfoStore');
                        	}
                        	
                        	var exportProtocolDisplayInstanceTreeGridPanel = Ext.getCmp("ExportProtocolDisplayInstanceTreeGridPanel_Id");
                        	if (isNotVal(exportProtocolDisplayInstanceTreeGridPanel)) {
                        		exportProtocolDisplayInstanceTreeGridPanel.getStore().load();
                        	}else{
                        		Ext.create('AP.store.acquisitionUnit.ExportProtocolDisplayInstanceTreeInfoStore');
                        	}
                        	
                        	var exportProtocolAlarmInstanceTreeGridPanel = Ext.getCmp("ExportProtocolAlarmInstanceTreeGridPanel_Id");
                        	if (isNotVal(exportProtocolAlarmInstanceTreeGridPanel)) {
                        		exportProtocolAlarmInstanceTreeGridPanel.getStore().load();
                        	}else{
                        		Ext.create('AP.store.acquisitionUnit.ExportProtocolAlarmInstanceTreeInfoStore');
                        	}
                        	
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("ExportPootocolTreePanel_Id");
                panel.add(treeGridPanel);
            }
            
            var selectRow=0;
            for(var i=0;i<store.data.items.length;i++){
    			if(store.data.items[i].data.classes==1){
    				selectRow=i;
    				break;
    			}
    		}
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().select(selectRow, true);
        }
    }
});