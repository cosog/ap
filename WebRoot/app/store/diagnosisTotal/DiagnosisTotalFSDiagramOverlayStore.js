var allCheck=false,allNotCheck=false;
Ext.define('AP.store.diagnosisTotal.DiagnosisTotalFSDiagramOverlayStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisTotalFSDiagramOverlayStore',
//    model: 'AP.model.balanceMonitor.BalanceMonitorModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getFSDiagramOverlayData',
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
            var DiagnosisTotalFSdiagramOverlayGrid = Ext.getCmp("DiagnosisTotalFSdiagramOverlayGrid_Id");
            if (!isNotVal(DiagnosisTotalFSdiagramOverlayGrid)) {
            	var arrColumns = get_rawData.columns;
                var column = createDiagnosisTotalColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                DiagnosisTotalFSdiagramOverlayGrid = Ext.create('Ext.grid.Panel', {
                    id: "DiagnosisTotalFSdiagramOverlayGrid_Id",
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
                    				var store=Ext.getCmp("DiagnosisTotalFSdiagramOverlayGrid_Id").getStore();
                    				var get_rawData = store.proxy.reader.rawData;
                    				showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalOverlayDiv_Id",false,0);
                    	            showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalPowerOverlayDiv_Id",false,1);
                    	            showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalCurrentOverlayDiv_Id",false,2);
                                	allNotCheck=true;
                    			}else{
                    				allNotCheck=false;
                    				var store=Ext.getCmp("DiagnosisTotalFSdiagramOverlayGrid_Id").getStore();
                    				var get_rawData = store.proxy.reader.rawData;
                    				showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalOverlayDiv_Id",true,0);
                    				showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalPowerOverlayDiv_Id",true,1);
                    	            showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalCurrentOverlayDiv_Id",true,2);
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
                            	var chart = $("#DiagnosisTotalOverlayDiv_Id").highcharts(); 
                            	var series=chart.series;
                            	series[index].hide();
                            	var powerchart = $("#DiagnosisTotalPowerOverlayDiv_Id").highcharts(); 
                            	var powerseries=powerchart.series;
                            	powerseries[index].hide();
                            	
                            	var currentchart = $("#DiagnosisTotalCurrentOverlayDiv_Id").highcharts(); 
                            	var currentseries=currentchart.series;
                            	currentseries[index].hide();
                        	}
                        },
                        select: function (v, o, index, p) {
                        	if(!allCheck){
                        		allNotCheck=false;
                        		var chart = $("#DiagnosisTotalOverlayDiv_Id").highcharts(); 
                            	var series=chart.series;
                            	series[index].show();
                            	var powerchart = $("#DiagnosisTotalPowerOverlayDiv_Id").highcharts(); 
                            	var powerseries=powerchart.series;
                            	powerseries[index].show();
                            	
                            	var currentchart = $("#DiagnosisTotalCurrentOverlayDiv_Id").highcharts(); 
                            	var currentseries=currentchart.series;
                            	currentseries[index].show();
                        	}
                        }
                    }
                });
                var DiagnosisTotalFSdiagramOverlayTable = Ext.getCmp("DiagnosisTotalFSdiagramOverlayTable_Id");
                DiagnosisTotalFSdiagramOverlayTable.add(DiagnosisTotalFSdiagramOverlayGrid);
                
            }
            var slectModel=Ext.getCmp("DiagnosisTotalFSdiagramOverlayGrid_Id").getSelectionModel();
            slectModel.selectAll(true);
//            
//            showFSDiagramOverlayChart(get_rawData,"DiagnosisTotalOverlayDiv_Id",true);
//            showPSDiagramOverlayChart(get_rawData,"DiagnosisTotalPowerOverlayDiv_Id",1,true);
//            showPSDiagramOverlayChart(get_rawData,"DiagnosisTotalCurrentOverlayDiv_Id",2,true);
            
            showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalOverlayDiv_Id",true,0);
            showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalPowerOverlayDiv_Id",true,1);
            showInverFSDiagramOverlayChart(get_rawData,"DiagnosisTotalCurrentOverlayDiv_Id",true,2);
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var wellName  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var calculateDate  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.calculateDate;
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