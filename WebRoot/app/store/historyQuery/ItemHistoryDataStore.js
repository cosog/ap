Ext.define('AP.store.historyQuery.ItemHistoryDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.itemHistoryDataStore',
    fields: ['acqTime','data'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/historyQueryController/getItemHistoryData',
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
        	Ext.getCmp("ItemHistoryDataPanel_Id").getEl().unmask();
    		//获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createHistoryQueryColumn(arrColumns);
            var gridPanel = Ext.getCmp("ItemHistoryQueryDataGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
//                	displayMsg: '当前 {0}~{1}条  共 {2} 条',
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ItemHistoryQueryDataGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: [{
                        text: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        width: 50,
                        xtype: 'rownumberer',
                        sortable: false,
                        locked: true
                    }, {
                        text: loginUserLanguageResource.cloudAcqtime,
                        lockable: true,
                        align: 'center',
                        width: 130,
                        sortable: false,
                        locked: true,
                        dataIndex: 'acqTime',
                        renderer: function (value, o, p, e) {
                            return adviceTimeFormat(value, o, p, e);
                        }
                    }, {
                        text: Ext.getCmp("HistoryDataItemName_Id").getValue(),
                        lockable: true,
                        align: 'center',
                        sortable: false,
                        dataIndex: 'data',
                        flex:1,
                        renderer: function (value, o, p, e) {
                            return adviceHistoryDataColor(value, o, p, e);
                        }
                    }],
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		
                    	}
                    }
                });
                var panel = Ext.getCmp("ItemHistoryDataPanel_Id");
                if(isNotVal(panel)){
                	panel.add(gridPanel);
                }
            }
            updateTotalRecords(get_rawData.totalCount,"ItemHistoryDataCount_Id");
    	},
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id')!=undefined?Ext.getCmp('leftOrg_Id').getValue():'0';
        	var deviceName='';
        	var deviceId=0;
        	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
        	var calculateType=0;
        	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id")!=undefined && Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
        		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
        		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
        	}
        	var startDate=Ext.getCmp('ItemHistoryDataStartDate_Id')!=undefined?Ext.getCmp('ItemHistoryDataStartDate_Id').rawValue:'';
        	var startTime_Hour=Ext.getCmp('ItemHistoryDataStartTime_Hour_Id')!=undefined?Ext.getCmp('ItemHistoryDataStartTime_Hour_Id').getValue():'';
        	var startTime_Minute=Ext.getCmp('ItemHistoryDataStartTime_Minute_Id')!=undefined?Ext.getCmp('ItemHistoryDataStartTime_Minute_Id').getValue():'';
        	var startTime_Second=0;

            var endDate=Ext.getCmp('ItemHistoryDataEndDate_Id')!=undefined?Ext.getCmp('ItemHistoryDataEndDate_Id').rawValue:'';
            var endTime_Hour=Ext.getCmp('ItemHistoryDataEndTime_Hour_Id')!=undefined?Ext.getCmp('ItemHistoryDataEndTime_Hour_Id').getValue():'';
        	var endTime_Minute=Ext.getCmp('ItemHistoryDataEndTime_Minute_Id')!=undefined?Ext.getCmp('ItemHistoryDataEndTime_Minute_Id').getValue():'';
        	var endTime_Second=0;
        	
        	if(Ext.getCmp("ItemHistoryDataPanel_Id")!=undefined && Ext.getCmp("ItemHistoryDataPanel_Id").el!=undefined){
        		Ext.getCmp("ItemHistoryDataPanel_Id").el.mask(loginUserLanguageResource.loadingData).show();
            }
        	
        	var totalCount=0;
        	if(store.totalCount!=undefined){
        		totalCount=store.totalCount;
        	}
        	
        	var itemName=Ext.getCmp("HistoryDataItemName_Id").getValue();
        	var itemCode=Ext.getCmp("HistoryDataItemCode_Id").getValue();
        	var itemType=Ext.getCmp("HistoryDataItemType_Id").getValue();
        	var itemResolutionMode=Ext.getCmp("HistoryDataItemResolutionMode_Id").getValue();
        	var bitIndex=Ext.getCmp("HistoryDataItemBitIndex_Id").getValue();
        	
            var new_params = {
            		orgId: orgId,
            		deviceType:deviceType,
            		deviceId:deviceId,
                    deviceName:deviceName,
                    calculateType:calculateType,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                    itemName:itemName,
        			itemCode:itemCode,
        			itemType:itemType,
        			itemResolutionMode:itemResolutionMode,
        			bitIndex:bitIndex,
                    totalCount:totalCount
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});