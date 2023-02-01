Ext.define('AP.store.acquisitionUnit.DatabaseColumnMappingTableRunItemsStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.databaseColumnMappingTableRunItemsStore',
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
            var gridPanel = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel1_Id");
            if (!isNotVal(gridPanel)) {
            	var newColumns = Ext.JSON.decode(column);
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DatabaseColumnMappingTableRunStatusMeaningGridPanel1_Id",
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
                    		var gridPanel2 = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel2_Id");
                            if (isNotVal(gridPanel2)) {
                            	gridPanel2.getSelectionModel().deselect(index, true);
                            }
                    	}
                    }
                });
                var panel = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningPanel1_Id");
                panel.add(gridPanel);
            }
//            gridPanel.getSelectionModel().deselectAll(true);
//            gridPanel.getSelectionModel().select(0, true);
        },
        beforeload: function (store, options) {
        	var protocolCode="";
        	var itemName="";
        	var itemColumn="";
        	var deviceType=0;
        	var protocolTreeSelection=Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsGridPanel_Id").getSelectionModel().getSelection();
        	if(protocolTreeSelection.length>0){
        		var selectedProtocol=protocolTreeSelection[0];
        		protocolCode=selectedProtocol.data.protocolCode;
        		itemName=selectedProtocol.data.itemName;
        		itemColumn=selectedProtocol.data.itemColumn;
        		deviceType=selectedProtocol.data.deviceType;
        	}
            var new_params = {
            		status:1,
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