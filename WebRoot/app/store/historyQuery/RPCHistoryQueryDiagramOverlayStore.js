var allCheck=false,allNotCheck=false;
Ext.define('AP.store.historyQuery.RPCHistoryQueryDiagramOverlayStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RPCHistoryQueryDiagramOverlayStore',
    autoLoad: true,
    pageSize: 10000,
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
        	Ext.getCmp("RPCHistoryDiagramOverlayTabPanel").getEl().unmask();
        	allCheck=false;
        	allNotCheck=false;
            var get_rawData = store.proxy.reader.rawData;
            if(get_rawData.outOfMemory){
            	Ext.MessageBox.alert("信息", "数据过大，加载失败，请缩小区间重新查询！");
            }
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var RPCHistoryQueryFSdiagramOverlayGrid = Ext.getCmp("RPCHistoryQueryFSdiagramOverlayGrid_Id");
            if (!isNotVal(RPCHistoryQueryFSdiagramOverlayGrid)) {
            	var arrColumns = get_rawData.columns;
                var column = createRPCHistoryQueryDiagramOverlayTableColumn(arrColumns)
                Ext.getCmp("RPCHistoryQueryDiagramOverlayColumnStr_Id").setValue(column);
                var newColumns = Ext.JSON.decode(column);
                RPCHistoryQueryFSdiagramOverlayGrid = Ext.create('Ext.grid.Panel', {
                    id: "RPCHistoryQueryFSdiagramOverlayGrid_Id",
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
                    		alert("全选/全不选");
                    	}
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	headerclick:function( ct, column, e, t, eOpts ) {
                    		if(column.isCheckerHd){
                    			if(column.allChecked){
                    				allCheck=false;
                    				var store=Ext.getCmp("RPCHistoryQueryFSdiagramOverlayGrid_Id").getStore();
                    				var get_rawData = store.proxy.reader.rawData;
                    				showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryOverlayDiv_Id",false,0);
                    				showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryPowerOverlayDiv_Id",false,1);
                    				showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryCurrentOverlayDiv_Id",false,2);
                                	allNotCheck=true;
                    			}else{
                    				allNotCheck=false;
                    				var store=Ext.getCmp("RPCHistoryQueryFSdiagramOverlayGrid_Id").getStore();
                    				var get_rawData = store.proxy.reader.rawData;
                    				showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryOverlayDiv_Id",true,0);
                    				showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryPowerOverlayDiv_Id",true,1);
                    				showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryCurrentOverlayDiv_Id",true,2);
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
                            	var chart = $("#RPCHistoryQueryOverlayDiv_Id").highcharts(); 
                            	var series=chart.series;
                            	series[index].hide();
                            	var powerchart = $("#RPCHistoryQueryPowerOverlayDiv_Id").highcharts(); 
                            	var powerseries=powerchart.series;
                            	powerseries[index].hide();
                            	
                            	var currentchart = $("#RPCHistoryQueryCurrentOverlayDiv_Id").highcharts(); 
                            	var currentseries=currentchart.series;
                            	currentseries[index].hide();
                        	}
                        },
                        select: function (v, o, index, p) {
                        	if(!allCheck){
                        		allNotCheck=false;
                        		var chart = $("#RPCHistoryQueryOverlayDiv_Id").highcharts(); 
                            	var series=chart.series;
                            	series[index].show();
                            	var powerchart = $("#RPCHistoryQueryPowerOverlayDiv_Id").highcharts(); 
                            	var powerseries=powerchart.series;
                            	powerseries[index].show();
                            	
                            	var currentchart = $("#RPCHistoryQueryCurrentOverlayDiv_Id").highcharts(); 
                            	var currentseries=currentchart.series;
                            	currentseries[index].show();
                        	}
                        }
                    }
                });
                var RPCHistoryQueryFSdiagramOverlayTable = Ext.getCmp("RPCHistoryQueryFSdiagramOverlayTable_Id");
                RPCHistoryQueryFSdiagramOverlayTable.add(RPCHistoryQueryFSdiagramOverlayGrid);
                
            }
            var startDate=Ext.getCmp('RPCHistoryQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('RPCHistoryQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('RPCHistoryQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('RPCHistoryQueryStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('RPCHistoryQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('RPCHistoryQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('RPCHistoryQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('RPCHistoryQueryEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
            
            var slectModel=RPCHistoryQueryFSdiagramOverlayGrid.getSelectionModel();
            slectModel.deselectAll(true);
            slectModel.selectAll(true);
            
            showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryOverlayDiv_Id",true,0);
            showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryPowerOverlayDiv_Id",true,1);
            showFSDiagramOverlayChart(get_rawData,"RPCHistoryQueryCurrentOverlayDiv_Id",true,2);
            
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName='';
        	var deviceId=0;
        	var selectRow= Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectRow_Id").getValue();
        	if(selectRow>=0){
        		deviceName = Ext.getCmp("RPCHistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		deviceId = Ext.getCmp("RPCHistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	}
        	var startDate=Ext.getCmp('RPCHistoryQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('RPCHistoryQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('RPCHistoryQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=Ext.getCmp('RPCHistoryQueryStartTime_Second_Id').getValue();

            var endDate=Ext.getCmp('RPCHistoryQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('RPCHistoryQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('RPCHistoryQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=Ext.getCmp('RPCHistoryQueryEndTime_Second_Id').getValue();
        	
        	Ext.getCmp("RPCHistoryDiagramOverlayTabPanel").el.mask(cosog.string.loading).show();
        	var new_params = {
        			orgId: orgId,
            		deviceType:0,
            		deviceId:deviceId,
                    deviceName:deviceName,
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