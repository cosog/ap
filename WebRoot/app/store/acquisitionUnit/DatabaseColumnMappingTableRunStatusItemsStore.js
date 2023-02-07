Ext.define('AP.store.acquisitionUnit.DatabaseColumnMappingTableRunStatusItemsStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.databaseColumnMappingTableRunStatusItemsStore',
    fields: ['id','protocolName','itemName','itemColumn','calColumn'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getProtocolRunStatusItems',
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
            var gridPanel = Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	var newColumns = Ext.JSON.decode(column);
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DatabaseColumnMappingTableRunStatusItemsGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
//                    selModel:{
//                    	selType: 'checkboxmodel',
//                    	mode:'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
//                    	checkOnly:false,
//                    	allowDeselect:false
//                    },
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
                    		var gridPanel = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel1_Id");
                            if (isNotVal(gridPanel)) {
                            	gridPanel.getSelectionModel().deselectAll(true);
                            	gridPanel.getStore().load();
                            }else{
                            	Ext.create('AP.store.acquisitionUnit.DatabaseColumnMappingTableRunItemsStore');
                            }
                            
                            var gridPanel2 = Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningGridPanel2_Id");
                            if (isNotVal(gridPanel2)) {
                            	gridPanel2.getSelectionModel().deselectAll(true);
                            	gridPanel2.getStore().load();
                            }else{
                            	Ext.create('AP.store.acquisitionUnit.DatabaseColumnMappingTableStopItemsStore');
                            }
                    	}
                    }
                });
                var panel = Ext.getCmp("DatabaseColumnMappingTableRunStatusItemsPanel_Id");
                panel.add(gridPanel);
            }
            gridPanel.getSelectionModel().deselectAll(true);
            if(get_rawData.totalCount>0){
            	gridPanel.getSelectionModel().select(0, true);
            }else{
            	Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningPanel1_Id").removeAll();
            	Ext.getCmp("DatabaseColumnMappingTableRunStatusMeaningPanel2_Id").removeAll();
            }
        },
        beforeload: function (store, options) {
        	var classes=0;
        	var deviceType=0;
        	var protocolCode="";
        	var protocolTreeSelection=Ext.getCmp("DatabaseColumnProtocolTreeGridPanel_Id").getSelectionModel().getSelection();
        	if(protocolTreeSelection.length>0){
        		var selectedProtocol=protocolTreeSelection[0];
        		classes=selectedProtocol.data.classes;
        		deviceType=selectedProtocol.data.deviceType;
        		if(classes==1){
            		protocolCode=selectedProtocol.data.code;
            	}
        	}
            var new_params = {
            		classes: classes,
        			deviceType: deviceType,
        			protocolCode: protocolCode
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});