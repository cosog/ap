Ext.define('AP.store.historyQuery.HistoryQueryDiagramTiledStatStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.historyQueryDiagramTiledStatStore',
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
        	Ext.getCmp("HistoryQueryTiledDiagramPanel").getEl().unmask();
            var get_rawData = store.proxy.reader.rawData;
            
            var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            }
            var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            }
            
            
            var HistoryQueryFSdiagramTiledStatGrid = Ext.getCmp("HistoryQueryFSdiagramTiledStatGrid_Id");
            if (!isNotVal(HistoryQueryFSdiagramTiledStatGrid)) {
            	var arrColumns = get_rawData.columns;
                var column = createHistoryQueryDiagramOverlayTableColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                HistoryQueryFSdiagramTiledStatGrid = Ext.create('Ext.grid.Panel', {
                    id: "HistoryQueryFSdiagramTiledStatGrid_Id",
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
                    		loadHistoryDiagramTiledList(1);
                    	},
                    	afterlayout: function (t, o) {
                        },
                        deselect: function (v, o, index, p) {
                        	
                        },
                        select: function (v, o, index, p) {
                        	
                        }
                    }
                });
                var HistoryQueryTiledDiagramStatPanel = Ext.getCmp("HistoryQueryTiledDiagramStatPanel");
                HistoryQueryTiledDiagramStatPanel.add(HistoryQueryFSdiagramTiledStatGrid);
            }
            
            var slectModel=HistoryQueryFSdiagramTiledStatGrid.getSelectionModel();
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
        	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
        		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	}
        	var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=0;

            var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=0;
        	var hours=getHistoryQueryHours();
        	
        	Ext.getCmp("HistoryQueryTiledDiagramPanel").el.mask(loginUserLanguageResource.loading).show();
        	var new_params = {
        			orgId: orgId,
            		deviceType:deviceType,
            		deviceId:deviceId,
                    deviceName:deviceName,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                    hours:hours
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});