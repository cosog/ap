Ext.define('AP.store.acquisitionUnit.ImportProtocolContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importProtocolContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getUploadedProtocolTreeData',
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
        	var treeGridPanel = Ext.getCmp("ImportProtocolContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
            	treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportProtocolContentTreeGridPanel_Id",
//                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
//                    selModel: {
//                    	selType: 'checkboxmodel',
//                    	mode:'MULTI',//"SINGLE" / "SIMPLE" / "MULTI" 
//                    	checkOnly:true,
//                    	allowDeselect:true
//                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '协议相关内容',
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
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		
                    	},
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },
                        select( v, record, index, eOpts ){
                        	if(record.data.classes==0){
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			CreateUploadedProtocolContentInfoTable(record.data.children[0].text,record.data.children[0].classes,record.data.children[0].code);
                        		}else{
                        			Ext.getCmp("ModbusProtocolAddrMappingItemsConfigPanel_Id").setTitle('采控项');
                        			if(importProtocolContentHandsontableHelper!=null && importProtocolContentHandsontableHelper.hot!=undefined){
                        				importProtocolContentHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
                        			}
//                        			if(protocolAddrMappingItemsMeaningConfigHandsontableHelper!=null && protocolAddrMappingItemsMeaningConfigHandsontableHelper.hot!=undefined){
//                        				protocolAddrMappingItemsMeaningConfigHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
//                        			}
                        		}
                        	}else if(record.data.classes==1){
                        		CreateUploadedProtocolContentInfoTable(record.data.text,record.data.classes,record.data.code);
                        	}
                        },
                        beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        },
                        checkchange: function (node, checked) {
                            listenerCheck(node, checked);
                        }
                    }

                });
                var panel = Ext.getCmp("importPootocolTreePanel_Id");
                panel.add(treeGridPanel);
            }
            
            treeGridPanel.getSelectionModel().deselectAll(true);
            if(store.data.length>1){
            	treeGridPanel.getSelectionModel().select(1, true);
            }else{
            	treeGridPanel.getSelectionModel().select(0, true);
            }
            
        }
    }
});