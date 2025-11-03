Ext.define('AP.store.data.DictItemSourceStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.dictItemSourceStore',
    fields: ['id','itemName','itemColumn','dictDataSource','dataSource','itemUnit'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/dataitemsInfoController/getAddInfoOrDriverConfigItemList',
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
            var column = createDictItemSourceConfigGridPanelColumn(arrColumns);
            var gridPanel = Ext.getCmp("DictItemSourceGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	var newColumns = Ext.JSON.decode(column);
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DictItemSourceGridPanel_Id",
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
                    		var itemNameId='sysDataName_zh_CN_Ids';
                    		if(loginUserLanguage.toUpperCase() == 'EN'){
                    			itemNameId='sysDataName_en_Ids';
                    		}else if(loginUserLanguage.toUpperCase() == 'RU'){
                    			itemNameId='sysDataName_ru_Ids';
                    		}
                    		var itemName=Ext.getCmp(itemNameId).getValue();
                    		if(!isNotVal(itemName)){
                    			var setName=record.data.itemName;
                    			var itemUnit=record.data.itemUnit;
                    			
                    			if(isNotVal(itemUnit)){
                    				setName+="("+itemUnit+")";
                    			}
                    			
                    			Ext.getCmp(itemNameId).setValue(setName);
                    		}
                    		
                    		Ext.getCmp("dictItemConfigItemName_Id").setValue(record.data.itemName);
                    	}
                    }
                });
                var panel = Ext.getCmp("dictItemSelectPanel_Id");
                panel.add(gridPanel);
                
                var dictItemAddOrUpdate=Ext.getCmp("dictItemAddOrUpdate_Id").getValue();
                if(dictItemAddOrUpdate==1){
                	var dictDataSource=Ext.getCmp("dictItemColumnDataSourceComb_Id").getValue();
                	if(get_rawData.totalCount>0){
                    	var selectRow=-1;
                    	var itemName=Ext.getCmp("sysDataName_zh_CN_Ids").getValue();
                    	var itemCode=Ext.getCmp("sysDataCode_Ids").getValue();
                    	var dictItemConfigItemName=Ext.getCmp("dictItemConfigItemName_Id").getValue();
                    	var dictItemConfigItemBitIndex=Ext.getCmp("dictItemConfigItemBitIndex_Id").getValue();
                    	for(var i=0;i<store.data.items.length;i++){
                			if(dictDataSource==2 && dictItemConfigItemName==store.data.items[i].data.itemName){
                				selectRow=i;
                				break;
                			}else if(dictDataSource==1 && itemCode==store.data.items[i].data.itemColumn && dictItemConfigItemBitIndex==store.data.items[i].data.bitIndex){
                				selectRow=i;
                				break;
                			}
                		}
                    	
                    	
            			gridPanel.getSelectionModel().deselectAll(true);
            			if(selectRow>=0){
            				gridPanel.getSelectionModel().select(selectRow, true);
            			}
                    }
                }
                
                
            }
        },
        beforeload: function (store, options) {
        	var dictDataSource=Ext.getCmp("dictItemColumnDataSourceComb_Id").getValue();
        	var dataSource=Ext.getCmp("dictItemDataSourceComb_Id").getValue();
        	var new_params = {
        			dictDataSource: dictDataSource,
        			dataSource: dataSource
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});