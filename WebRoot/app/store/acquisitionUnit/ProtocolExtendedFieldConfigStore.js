Ext.define('AP.store.acquisitionUnit.ProtocolExtendedFieldConfigStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.protocolExtendedFieldConfigStore',
    fields: ['id','protocolName','itemName','itemColumn','calColumn'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldItems',
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
            var column = createProtocolExtendedFieldGridPanelColumn(arrColumns);
            var gridPanel = Ext.getCmp("ProtocolExtendedFieldGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	var newColumns = Ext.JSON.decode(column);
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ProtocolExtendedFieldGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode:'SINGLE',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly:false,
                    	allowDeselect:false
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		
                    	}
                    }
                });
                var panel = Ext.getCmp("protocolExtendedFieldSelectPanel_Id");
                panel.add(gridPanel);
                
                if(get_rawData.totalCount>0){
                	var selectRow=-1;
                	var selectedItemName=Ext.getCmp("protocolExtendedFieldSelectedItemName_Id").getValue();
                	
        			if(isNotVal(selectedItemName)){
        				for(var i=0;i<store.data.items.length;i++){
                			if(selectedItemName==store.data.items[i].data.itemName){
                				selectRow=i;
                				break;
                			}
                		}
        			}
        			gridPanel.getSelectionModel().deselectAll(true);
        			if(selectRow>=0){
        				gridPanel.getSelectionModel().select(selectRow, true);
        			}
                }
            }
        },
        beforeload: function (store, options) {
        	var record= Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getSelectionModel().getSelection()[0];//record.data.text,record.data.classes,record.data.code
        	var protocolExtendedFieldType=Ext.getCmp('protocolExtendedFieldType_Id').getValue();
            var new_params = {
        			protocolCode: record.data.code,
        			protocolExtendedFieldType:protocolExtendedFieldType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});