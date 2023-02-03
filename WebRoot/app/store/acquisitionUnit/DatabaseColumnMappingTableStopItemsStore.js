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
            var column = createDatabaseColumnMappingTableRunStatusItemsColumn(arrColumns);
            var gridPanel = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel2_Id");
            if (!isNotVal(gridPanel)) {
            	var newColumns = Ext.JSON.decode(column);
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DatabaseColumnMappingTableRunStatusMeaningGridPanel2_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode:'MULTI',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly:false,
                    	allowDeselect:false
                    },
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
                    		var gridPanel1 = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel1_Id");
                            if (isNotVal(gridPanel1)) {
                            	gridPanel1.getSelectionModel().deselect(index, true);
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
        	var runStatusItemsSelection=Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsGridPanel_Id").getSelectionModel().getSelection();
        	if(runStatusItemsSelection.length>0){
        		var selectedRunStatusItem=runStatusItemsSelection[0];
        		protocolCode=selectedRunStatusItem.data.protocolCode;
        		itemName=selectedRunStatusItem.data.itemName;
        		itemColumn=selectedRunStatusItem.data.itemColumn;
        		deviceType=selectedRunStatusItem.data.deviceType;
        	}
            var new_params = {
            		status:0,
            		deviceType:deviceType,
            		protocolCode: protocolCode,
            		itemName: itemName,
            		itemColumn: itemColumn
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});