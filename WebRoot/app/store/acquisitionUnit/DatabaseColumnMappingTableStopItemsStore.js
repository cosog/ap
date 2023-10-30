Ext.define('AP.store.acquisitionUnit.DatabaseColumnMappingTableStopItemsStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.databaseColumnMappingTableStopItemsStore',
    fields: ['id','protocolName','itemName','itemColumn','calColumn'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getProtocolRunStatusItemsMeaning',
        actionMethods: {
            read: 'POST'
        },
    reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, record, f, op, o) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
//            var column = createDatabaseColumnMappingTableRunStatusItemsColumn(arrColumns);
            var gridPanel = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel2_Id");
            if (!isNotVal(gridPanel)) {
//            	var newColumns = Ext.JSON.decode(column);
            	var resolutionMode=get_rawData.resolutionMode;
            	var newColumns=[{
        		    text: '序号',
        		    lockable: true,
        		    align: 'center',
        		    width: 50,
        		    xtype: 'rownumberer',
        		    sortable: false,
        		    locked: false
        		}, {
        		    text: '运算关系',
        		    lockable: true,
        		    align: 'center',
        		    flex: 1,
        		    sortable: false,
        		    dataIndex: 'condition',
        		    renderer: function (value) {
        		        if (isNotVal(value)) {
        		            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
        		        }
        		    }
        		}, {
        		    text: '值',
        		    lockable: true,
        		    align: 'center',
        		    flex: 1,
        		    sortable: false,
        		    dataIndex: 'value',
        		    editor: {
                        allowBlank: false
                    },
        		    renderer: function (value) {
        		        if (isNotVal(value)) {
        		            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
        		        }
        		    }
        		}];
            	
            	var selModel_mode='SINGLE';
            	
            	var plugins=[{
                    ptype: 'cellediting',//cellediting rowediting
                    clicksToEdit: 2
                }];
            	
            	if(resolutionMode==1){
            		newColumns=[{
                	    text: '序号',
                	    lockable: true,
                	    align: 'center',
                	    width: 50,
                	    xtype: 'rownumberer',
                	    sortable: false,
                	    locked: false
                	}, {
                	    text: '数值',
                	    lockable: true,
                	    align: 'center',
                	    flex: 1,
                	    sortable: false,
                	    dataIndex: 'value',
                	    renderer: function (value) {
                	        if (isNotVal(value)) {
                	            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                	        }
                	    }
                	}, {
                	    text: '含义',
                	    lockable: true,
                	    align: 'center',
                	    flex: 2,
                	    sortable: false,
                	    dataIndex: 'meaning',
                	    renderer: function (value) {
                	        if (isNotVal(value)) {
                	            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                	        }
                	    }
                	}];
            		plugins=null;
            		selModel_mode='MULTI';
            	}
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DatabaseColumnMappingTableRunStatusMeaningGridPanel2_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode: selModel_mode,//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly: false,
                    	allowDeselect: false
                    },
                    plugins: plugins,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		var resolutionMode=1;
                        	var runStatusItemsSelection=Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsGridPanel_Id").getSelectionModel().getSelection();
                        	if(runStatusItemsSelection.length>0){
                        		var selectedRunStatusItem=runStatusItemsSelection[0];
                        		resolutionMode=selectedRunStatusItem.data.resolutionMode;
                        	}
                        	if(resolutionMode==1){
                        		var gridPanel1 = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel1_Id");
                                if (isNotVal(gridPanel1)) {
                                	gridPanel1.getSelectionModel().deselect(index, true);
                                }
                    		}
                    	}
                    }
                });
                var panel = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningPanel2_Id");
                panel.add(gridPanel);
            }
            gridPanel.getSelectionModel().deselectAll(true);
            if(get_rawData.stopValueIndex.length>0){
            	for(var i=0;i<get_rawData.stopValueIndex.length;i++){
            		gridPanel.getSelectionModel().select(get_rawData.stopValueIndex[i], true);
            	}
            }
        },
        beforeload: function (store, options) {
        	var protocolCode="";
        	var itemName="";
        	var itemColumn="";
        	var deviceType=0;
        	var resolutionMode=1;
        	var runStatusItemsSelection=Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsGridPanel_Id").getSelectionModel().getSelection();
        	if(runStatusItemsSelection.length>0){
        		var selectedRunStatusItem=runStatusItemsSelection[0];
        		protocolCode=selectedRunStatusItem.data.protocolCode;
        		itemName=selectedRunStatusItem.data.itemName;
        		itemColumn=selectedRunStatusItem.data.itemColumn;
        		deviceType=selectedRunStatusItem.data.deviceType;
        		resolutionMode=selectedRunStatusItem.data.resolutionMode;
        	}
            var new_params = {
            		status:0,
            		deviceType:deviceType,
            		protocolCode: protocolCode,
            		itemName: itemName,
            		itemColumn: itemColumn,
            		resolutionMode: resolutionMode
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});