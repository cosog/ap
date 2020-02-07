var allCheck=false,allNotCheck=false;
Ext.define('AP.store.electricDailyAnalysis.ElectricAnalysisDailyFSDiagramOverlayStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.electricAnalysisDailyFSDiagramOverlayStore',
//    model: 'AP.model.balanceMonitor.BalanceMonitorModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/PSToFSController/getFSdiagramOverlayData',
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
        	allCheck=false;
        	allNotCheck=false;
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var ElectricAnalysisDailyFSdiagramOverlayGrid = Ext.getCmp("ElectricAnalysisDailyFSdiagramOverlayGrid_Id");
            if (!isNotVal(ElectricAnalysisDailyFSdiagramOverlayGrid)) {
            	var arrColumns = get_rawData.columns;
                var column = createElecAnalysisDailyDetailsTableColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                ElectricAnalysisDailyFSdiagramOverlayGrid = Ext.create('Ext.grid.Panel', {
                    id: "ElectricAnalysisDailyFSdiagramOverlayGrid_Id",
                    border: false,
                    forceFit: false,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
//                    selType: 'checkboxmodel',
//                    multiSelect: true,
                    selModel:{
                    	selType: 'checkboxmodel',
                    	mode:'MULTI',
                    	checkOnly:true,
                    	onHdMouseDown:function(e,t){
                    		alert("全选/全部选");
                    	}
                    },
                    plugins: {
                        ptype: 'bufferedrenderer',
                        numFromEdge: 5,
                        trailingBufferZone: 40,
                        leadingBufferZone: 50
                    },
                    viewConfig: {
//                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	headerclick:function( ct, column, e, t, eOpts ) {
                    		if(column.isCheckerHd){
                    			if(column.allChecked){
                    				allCheck=false;
                    				var store=Ext.getCmp("ElectricAnalysisDailyFSdiagramOverlayGrid_Id").getStore();
                    				var get_rawData = store.proxy.reader.rawData;
                    				showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyFSDiagramOverlayDiv_Id",false,0);
                    	            showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyPSDiagramOverlayDiv_Id",false,1);
                    	            showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyASDiagramOverlayDiv_Id",false,2);
                                	allNotCheck=true;
                    			}else{
                    				allNotCheck=false;
                    				var store=Ext.getCmp("ElectricAnalysisDailyFSdiagramOverlayGrid_Id").getStore();
                    				var get_rawData = store.proxy.reader.rawData;
                    				showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyFSDiagramOverlayDiv_Id",true,0);
                    	            showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyPSDiagramOverlayDiv_Id",true,1);
                    	            showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyASDiagramOverlayDiv_Id",true,2);
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
                            	var chart = $("#ElectricAnalysisDailyFSDiagramOverlayDiv_Id").highcharts(); 
                            	var series=chart.series;
                            	series[index].hide();
                            	var powerchart = $("#ElectricAnalysisDailyPSDiagramOverlayDiv_Id").highcharts(); 
                            	var powerseries=powerchart.series;
                            	powerseries[index].hide();
                            	
                            	var currentchart = $("#ElectricAnalysisDailyASDiagramOverlayDiv_Id").highcharts(); 
                            	var currentseries=currentchart.series;
                            	currentseries[index].hide();
                        	}
                        },
                        select: function (v, o, index, p) {
                        	if(!allCheck){
                        		allNotCheck=false;
                        		var chart = $("#ElectricAnalysisDailyFSDiagramOverlayDiv_Id").highcharts(); 
                            	var series=chart.series;
                            	series[index].show();
                            	var powerchart = $("#ElectricAnalysisDailyPSDiagramOverlayDiv_Id").highcharts(); 
                            	var powerseries=powerchart.series;
                            	powerseries[index].show();
                            	
                            	var currentchart = $("#ElectricAnalysisDailyASDiagramOverlayDiv_Id").highcharts(); 
                            	var currentseries=currentchart.series;
                            	currentseries[index].show();
                        	}
                        }
                    }
                });
                var ElectricAnalysisDailyFSDiagramOverlayTable = Ext.getCmp("ElectricAnalysisDailyFSDiagramOverlayTable_Id");
                ElectricAnalysisDailyFSDiagramOverlayTable.add(ElectricAnalysisDailyFSdiagramOverlayGrid);
                
            }
            var slectModel=Ext.getCmp("ElectricAnalysisDailyFSdiagramOverlayGrid_Id").getSelectionModel();
            slectModel.selectAll(true);
            
            showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyFSDiagramOverlayDiv_Id",true,0);
            showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyPSDiagramOverlayDiv_Id",true,1);
            showInverFSDiagramOverlayChart(get_rawData,"ElectricAnalysisDailyASDiagramOverlayDiv_Id",true,2);
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName  = Ext.getCmp("ElectricAnalysisDailyDetails_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var calculateDate  = Ext.getCmp("ElectricAnalysisDailyDetails_Id").getSelectionModel().getSelection()[0].data.calculateDate;
        	var new_params = {
                    orgId: leftOrg_Id,
                    wellName: wellName,
                    calculateDate:calculateDate
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});