var allCheck=false,allNotCheck=false;
Ext.define('AP.store.historyQuery.HistoryQueryDiagramOverlayStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.historyQueryDiagramOverlayStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/historyQueryController/getFESDiagramOverlayData',
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
        	Ext.getCmp("HistoryQueryFESDiagramOverlayCenterPanel").getEl().unmask();
        	allCheck=false;
        	allNotCheck=false;
            var get_rawData = store.proxy.reader.rawData;
            if(get_rawData.outOfMemory){
            	Ext.MessageBox.alert(loginUserLanguageResource.message, "数据过大，加载失败，请缩小区间重新查询！");
            }
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var HistoryQueryFSdiagramOverlayGrid = Ext.getCmp("HistoryQueryFSdiagramOverlayGrid_Id");
            if (!isNotVal(HistoryQueryFSdiagramOverlayGrid)) {
            	var arrColumns = get_rawData.columns;
                var column = createHistoryQueryDiagramOverlayTableColumn(arrColumns)
                Ext.getCmp("HistoryQueryDiagramOverlayColumnStr_Id").setValue(column);
                var newColumns = Ext.JSON.decode(column);
                HistoryQueryFSdiagramOverlayGrid = Ext.create('Ext.grid.Panel', {
                    id: "HistoryQueryFSdiagramOverlayGrid_Id",
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
                    		if(column.isCheckerHd){
                    			if(column.allChecked){
                    				allCheck=false;
                    				var store=Ext.getCmp("HistoryQueryFSdiagramOverlayGrid_Id").getStore();
                    				var get_rawData = store.proxy.reader.rawData;
                    				showFSDiagramOverlayChart(get_rawData,"HistoryQueryOverlayDiv_Id",false,0);
                    				showFSDiagramOverlayChart(get_rawData,"HistoryQueryPowerOverlayDiv_Id",false,1);
                    				showFSDiagramOverlayChart(get_rawData,"HistoryQueryCurrentOverlayDiv_Id",false,2);
                                	allNotCheck=true;
                    			}else{
                    				allNotCheck=false;
                    				var store=Ext.getCmp("HistoryQueryFSdiagramOverlayGrid_Id").getStore();
                    				var get_rawData = store.proxy.reader.rawData;
                    				showFSDiagramOverlayChart(get_rawData,"HistoryQueryOverlayDiv_Id",true,0);
                    				showFSDiagramOverlayChart(get_rawData,"HistoryQueryPowerOverlayDiv_Id",true,1);
                    				showFSDiagramOverlayChart(get_rawData,"HistoryQueryCurrentOverlayDiv_Id",true,2);
                    	            allCheck=true;
                    			}
                    		}
                    	},
                    	itemclick:function( view , record , item , index , e , eOpts ) {
                    	},
                    	selectionchange:function(grid, record , eOpts) {
                    	},
                    	afterlayout: function (t, o) {
                        },
                        deselect: function (v, o, index, p) {
                        	if(!allNotCheck){
                        		allCheck=false;
                            	var chart = $("#HistoryQueryOverlayDiv_Id").highcharts(); 
                            	var series=chart.series;
                            	series[index].hide();
                            	var powerchart = $("#HistoryQueryPowerOverlayDiv_Id").highcharts(); 
                            	var powerseries=powerchart.series;
                            	powerseries[index].hide();
                            	
                            	var currentchart = $("#HistoryQueryCurrentOverlayDiv_Id").highcharts(); 
                            	var currentseries=currentchart.series;
                            	currentseries[index].hide();
                        	}
                        },
                        select: function (v, o, index, p) {
                        	if(!allCheck){
                        		allNotCheck=false;
                        		var chart = $("#HistoryQueryOverlayDiv_Id").highcharts(); 
                            	var series=chart.series;
                            	series[index].show();
                            	var powerchart = $("#HistoryQueryPowerOverlayDiv_Id").highcharts(); 
                            	var powerseries=powerchart.series;
                            	powerseries[index].show();
                            	
                            	var currentchart = $("#HistoryQueryCurrentOverlayDiv_Id").highcharts(); 
                            	var currentseries=currentchart.series;
                            	currentseries[index].show();
                        	}
                        }
                    }
                });
                var HistoryQueryFSdiagramOverlayTable = Ext.getCmp("HistoryQueryFSdiagramOverlayTable_Id");
                HistoryQueryFSdiagramOverlayTable.add(HistoryQueryFSdiagramOverlayGrid);
            }
            
            var slectModel=HistoryQueryFSdiagramOverlayGrid.getSelectionModel();
            slectModel.deselectAll(true);
            slectModel.selectAll(true);
            
            showFSDiagramOverlayChart(get_rawData,"HistoryQueryOverlayDiv_Id",true,0);
            showFSDiagramOverlayChart(get_rawData,"HistoryQueryPowerOverlayDiv_Id",true,1);
            showFSDiagramOverlayChart(get_rawData,"HistoryQueryCurrentOverlayDiv_Id",true,2);
            
            updateTotalRecords(get_rawData.totalShow,"HistoryFESDiagramTotalCount_Id");
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
        	var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=0;

            var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=0;
        	var hours=getHistoryQueryHours();
        	var selectedResult=[];
        	var statSelection = Ext.getCmp("HistoryQueryFSdiagramOverlayStatGrid_Id").getSelectionModel().getSelection();
        	Ext.Array.each(statSelection, function (name, index, countriesItSelf) {
        		selectedResult.push(statSelection[index].data.resultCode);
        	});
        	
        	Ext.getCmp("HistoryQueryFESDiagramOverlayCenterPanel").el.mask(loginUserLanguageResource.loading).show();
        	var new_params = {
        			orgId: orgId,
            		deviceType:deviceType,
            		deviceId:deviceId,
                    deviceName:deviceName,
                    resultCode:selectedResult.join(","),
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