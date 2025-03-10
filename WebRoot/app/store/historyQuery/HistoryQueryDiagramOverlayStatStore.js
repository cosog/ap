Ext.define('AP.store.historyQuery.HistoryQueryDiagramOverlayStatStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.historyQueryDiagramOverlayStatStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/historyQueryController/getDeviceResultStatusStatData',
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
        	Ext.getCmp("HistoryQueryFSdiagramOverlayStatTable_Id").getEl().unmask();
            var get_rawData = store.proxy.reader.rawData;
            
            var startDate=Ext.getCmp('HistoryQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('HistoryQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('HistoryQueryStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('HistoryQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('HistoryQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('HistoryQueryEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
            
            
            var HistoryQueryFSdiagramOverlayStatGrid = Ext.getCmp("HistoryQueryFSdiagramOverlayStatGrid_Id");
            if (!isNotVal(HistoryQueryFSdiagramOverlayStatGrid)) {
            	var arrColumns = get_rawData.columns;
                var column = createHistoryQueryDiagramOverlayTableColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                HistoryQueryFSdiagramOverlayStatGrid = Ext.create('Ext.grid.Panel', {
                    id: "HistoryQueryFSdiagramOverlayStatGrid_Id",
                    border: false,
                    forceFit: false,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode:'MULTI',
                    	checkOnly:true,
                    	onHdMouseDown:function(e,t){
                    		
                    	}
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	headerclick:function( ct, column, e, t, eOpts ) {
                    	},
                    	itemclick:function( view , record , item , index , e , eOpts ) {
                    	},
                    	selectionchange:function(grid, record , eOpts) {
    						var gridPanel = Ext.getCmp("HistoryQueryFSdiagramOverlayGrid_Id");
    						if (isNotVal(gridPanel)) {
    							gridPanel.getStore().load();
    						}else{
    							Ext.create("AP.store.historyQuery.HistoryQueryDiagramOverlayStore");
    						}
                    	},
                    	afterlayout: function (t, o) {
                        },
                        deselect: function (v, o, index, p) {
                        	
                        },
                        select: function (v, o, index, p) {
                        	
                        }
                    }
                });
                var HistoryQueryFSdiagramOverlayStatTable = Ext.getCmp("HistoryQueryFSdiagramOverlayStatTable_Id");
                HistoryQueryFSdiagramOverlayStatTable.add(HistoryQueryFSdiagramOverlayStatGrid);
            }
            
            var slectModel=HistoryQueryFSdiagramOverlayStatGrid.getSelectionModel();
            var selected=[];
            for(var i=0;i<store.data.items.length;i++){
    			if(1232!=store.data.items[i].data.resultCode){
    				selected.push(store.data.items[i]);
    			}
    		}
            slectModel.deselectAll(true);
            slectModel.select(selected);
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName='';
        	var deviceId=0;
        	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
        	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
        	if(selectRow>=0){
        		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	}
        	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();

            var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
        	
        	var resultCode=Ext.getCmp('HistoryQueryResultNameComBox_Id').getValue();
        	
        	Ext.getCmp("HistoryQueryFSdiagramOverlayStatTable_Id").el.mask(loginUserLanguageResource.loading).show();
        	var new_params = {
        			orgId: orgId,
            		deviceType:deviceType,
            		deviceId:deviceId,
                    deviceName:deviceName,
                    resultCode:resultCode,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});