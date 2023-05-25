Ext.define('AP.store.acquisitionUnit.ImportProtocolContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importProtocolContentTreeInfoStore',
    model: 'AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getImportedProtocolContentTreeData',
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
                    selModel: {
                    	selType: 'checkboxmodel',
                    	mode:'MULTI',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly:true,
                    	allowDeselect:true
                    },
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
//                    		alert(record.data.classes);
                    		if(record.data.classes>0){
                    			var selectedType=parseInt(Ext.getCmp("ImportProtocolSelectItemType_Id").getValue());
                    			var typeChange=false;
                    			var type=-99;
                    			if(record.data.classes==1){
                    				type=record.parentNode.data.type;
                    			}else if(record.data.classes==2){
                    				type=record.parentNode.parentNode.data.type;
                    			}
                    			typeChange=(selectedType==type);
//                    			typeChange=!(selectedType==type || (selectedType+type)==3  );
                    			Ext.getCmp("ImportProtocolSelectItemType_Id").setValue(type);
                    			Ext.getCmp("ImportProtocolSelectItemId_Id").setValue(record.data.id);
                    			CreateImportProtocolContentInfoTable(record.data.id,record.data.classes,type,typeChange);
                    		}else{
                    			Ext.getCmp("ImportProtocolSelectItemType_Id").setValue(-99);
                    			Ext.getCmp("ImportProtocolSelectItemId_Id").setValue(-99);
                    			Ext.getCmp("importedProtocolItemInfoTablePanel_Id").removeAll();
                    		}
                    	},
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },
                        select( v, record, index, eOpts ){
                        	
                        },
                        beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("importPootocolTreePanel_Id");
                panel.add(treeGridPanel);
            }
            treeGridPanel.getSelectionModel().deselectAll(true);
            treeGridPanel.getSelectionModel().selectAll(true);
            
//            var selectRow=0;
//            for(var i=0;i<store.data.items.length;i++){
//    			if(store.data.items[i].data.classes==1){
//    				selectRow=i;
//    				break;
//    			}
//    		}
//            treeGridPanel.getSelectionModel().deselectAll(true);
//            treeGridPanel.getSelectionModel().select(selectRow, true);
        }
    }
});