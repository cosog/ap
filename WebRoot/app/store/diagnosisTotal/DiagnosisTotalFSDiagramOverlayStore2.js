Ext.define('AP.store.diagnosisTotal.DiagnosisTotalFSDiagramOverlayStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisTotalFSDiagramOverlayStore',
//    model: 'AP.model.balanceMonitor.BalanceMonitorModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getFSdiagramOverlayData',
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
            showFSDiagramOverlayChart(get_rawData,"DiagnosisTotalOverlayDiv_Id");
            showPSDiagramOverlayChart(get_rawData,"DiagnosisTotalPowerOverlayDiv_Id",1);
            showPSDiagramOverlayChart(get_rawData,"DiagnosisTotalCurrentOverlayDiv_Id",2);
            
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
                    selType: 'checkboxmodel',
                    multiSelect: true,
                    plugins: {
                        ptype: 'bufferedrenderer',
                        numFromEdge: 5,
                        trailingBufferZone: 40,
                        leadingBufferZone: 50
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	itemclick:function( view , record , item , index , e , eOpts ) {
                    	},
                    	selectionchange:function(grid, record , eOpts) {
                    	},
                    	afterlayout: function (t, o) {
                            Ext.getCmp("DiagnosisTotalFSdiagramOverlayGrid_Id").getSelectionModel().selectAll(true);
                        },
                        deselect: function (v, o, i, p) {
                        	var chart = $("#DiagnosisTotalOverlayDiv_Id").highcharts(); 
                        	var series=chart.series;
                        	for(var i=0;i<series.length;i++){
                        		if(series[i].name==o.data.id){
                        			series[i].hide();
                        			break;
                        		}
                        	}
                        },
                        select: function (v, o, i, p) {
                        	var chart = $("#DiagnosisTotalOverlayDiv_Id").highcharts(); 
                        	var series=chart.series;
                        	for(var i=0;i<series.length;i++){
                        		if(series[i].name==o.data.id){
                        			series[i].show();
                        			break;
                        		}
                        	}
                        }
                    }
                });
                var DiagnosisTotalFSdiagramOverlayTable = Ext.getCmp("DiagnosisTotalFSdiagramOverlayTable_Id");
                DiagnosisTotalFSdiagramOverlayTable.add(DiagnosisTotalFSdiagramOverlayGrid);
            }
            
            var PSdiagramOverlayGrid = Ext.getCmp("DiagnosisTotalPSdiagramOverlayGrid_Id");
            if (!isNotVal(PSdiagramOverlayGrid)) {
            	var arrColumns = get_rawData.columnsPS;
                var column = createDiagnosisTotalColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                PSdiagramOverlayGrid = Ext.create('Ext.grid.Panel', {
                    id: "DiagnosisTotalPSdiagramOverlayGrid_Id",
                    border: false,
                    forceFit: false,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    selType: 'checkboxmodel',
                    multiSelect: true,
                    plugins: {
                        ptype: 'bufferedrenderer',
                        numFromEdge: 5,
                        trailingBufferZone: 40,
                        leadingBufferZone: 50
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	afterlayout: function (t, o) {
                            Ext.getCmp("DiagnosisTotalPSdiagramOverlayGrid_Id").getSelectionModel().selectAll(true);
                        },
                        deselect: function (v, o, i, p) {
                        	var chart = $("#DiagnosisTotalPowerOverlayDiv_Id").highcharts(); 
                        	var series=chart.series;
                        	for(var i=0;i<series.length;i++){
                        		if(series[i].name==o.data.id){
                        			series[i].hide();
                        			break;
                        		}
                        	}
                        },
                        select: function (v, o, i, p) {
                        	var chart = $("#DiagnosisTotalPowerOverlayDiv_Id").highcharts(); 
                        	var series=chart.series;
                        	for(var i=0;i<series.length;i++){
                        		if(series[i].name==o.data.id){
                        			series[i].show();
                        			break;
                        		}
                        	}
                        }
                    }
                });
                var PSdiagramOverlayTable = Ext.getCmp("DiagnosisTotalPSdiagramOverlayTable_Id");
                PSdiagramOverlayTable.add(PSdiagramOverlayGrid);
            }
            
            var ASdiagramOverlayGrid = Ext.getCmp("DiagnosisTotalASdiagramOverlayGrid_Id");
            if (!isNotVal(ASdiagramOverlayGrid)) {
            	var arrColumns = get_rawData.columnsAS;
                var column = createDiagnosisTotalColumn(arrColumns)
                var newColumns = Ext.JSON.decode(column);
                ASdiagramOverlayGrid = Ext.create('Ext.grid.Panel', {
                    id: "DiagnosisTotalASdiagramOverlayGrid_Id",
                    border: false,
                    forceFit: false,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    selType: 'checkboxmodel',
                    multiSelect: true,
                    plugins: {
                        ptype: 'bufferedrenderer',
                        numFromEdge: 5,
                        trailingBufferZone: 40,
                        leadingBufferZone: 50
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	afterlayout: function (t, o) {
                            Ext.getCmp("DiagnosisTotalASdiagramOverlayGrid_Id").getSelectionModel().selectAll(true);
                        },
                        deselect: function (v, o, i, p) {
                        	var chart = $("#DiagnosisTotalCurrentOverlayDiv_Id").highcharts(); 
                        	var series=chart.series;
                        	for(var i=0;i<series.length;i++){
                        		if(series[i].name==o.data.id){
                        			series[i].hide();
                        			break;
                        		}
                        	}
                        },
                        select: function (v, o, i, p) {
                        	var chart = $("#DiagnosisTotalCurrentOverlayDiv_Id").highcharts(); 
                        	var series=chart.series;
                        	for(var i=0;i<series.length;i++){
                        		if(series[i].name==o.data.id){
                        			series[i].show();
                        			break;
                        		}
                        	}
                        }
                    }
                });
                var ASdiagramOverlayTable = Ext.getCmp("DiagnosisTotalASdiagramOverlayTable_Id");
                ASdiagramOverlayTable.add(ASdiagramOverlayGrid);
            }
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var jh  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.jh;
        	var totalDate  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.jssj;
        	var new_params = {
                    orgId: leftOrg_Id,
                    jh: jh,
                    jssj:totalDate
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});