Ext.define('AP.store.electricAnalysis.ElectricAnalysisRealtimeDetailsDiagramListStore', {
    extend: 'Ext.data.Store',
    fields: ['jlbh','jh','gtcjsj'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/PSToFSController/getDiagramDataList',
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
        load: function (store, sEops) {
        	var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createElecAnalysisRealtimeDetailsDiagramTableColumn(arrColumns);
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
//            	displayMsg: ''
	        });
            var gridPanel = Ext.getCmp("ElectricAnalysisRealtimeDiagramDetails_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'ElectricAnalysisRealtimeDiagramDetails_Id',
                    bbar: bbar,
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
//                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    store:store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    columns:newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {
                            	var id=selected[0].data.id;
                            	var wellName = Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").getValue();
                            	Ext.Ajax.request({
                            		method:'POST',
                            		url:context + '/PSToFSController/getSingleInverDiagramData',
                            		success:function(response) {
                            			var result =  Ext.JSON.decode(response.responseText);
                            			
                            			showPContinuousDiagram(result.powerCurveData,"功率曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"有功功率(kW)",'#FF6633',"electricAnalysisRealtimeDetailsInverDiv1_id");
                            			showPContinuousDiagram(result.currentCurveData,"电流曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"电流(A)",'#CC0000',"electricAnalysisRealtimeDetailsInverDiv2_id");
                            			
                            			
                            			showPContinuousDiagram(result.rpmCurveData,"转速曲线",result.wellName+' ['+result.acquisitionTime+']','<span style="text-align:center;">点数<br />',"转速(r/min)",'#e3cc19',"electricAnalysisRealtimeDetailsInverDiv6_id");
                            			
                            			var positionCurveData=result.positionCurveData.split(",");
                            			if(result.positionCurveData!=undefined && result.positionCurveData!="" && result.positionCurveData.split(",").length>0){
                            				showFSDiagramWithAtrokeSPM(result,"electricAnalysisRealtimeDetailsInverDiv3_id");
                                			showPSDiagram(result,"electricAnalysisRealtimeDetailsInverDiv4_id");
                                			showASDiagram(result,"electricAnalysisRealtimeDetailsInverDiv5_id");
                            			}else{
                                        	$("#electricAnalysisRealtimeDetailsInverDiv3_id").html('');
                                        	$("#electricAnalysisRealtimeDetailsInverDiv4_id").html('');
                                        	$("#electricAnalysisRealtimeDetailsInverDiv5_id").html('');
                                        }
                            		},
                            		failure:function(){
                            			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
                            		},
                            		params: {
                            			id: id,
                            			wellName:wellName
                                    }
                            	});
                            	//加载右侧表格数据
                            	Ext.create("AP.store.electricAnalysis.ElectricAnalysisRealtimeDiagramDetailsRightTableStore");
                            }
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        	Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").show();
                        	Ext.getCmp("electricAnalysisRealtimeDetailsHisBtn_Id").hide();
                            Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").show();
                			Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').setValue(record.data.wellName);
                        	Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').setRawValue(record.data.wellName);
                        	Ext.getCmp('ElectricAnalysisRealtimeDiagramDetails_Id').getStore().loadPage(1);
                        }
                    }
                })
            	Ext.getCmp("electricAnalysisRealtimeDiagramDetailsTable_Id").add(gridPanel);
            }
            if(get_rawData.totalCount>0){
                if (isNotVal(gridPanel)) {
                	gridPanel.getSelectionModel().deselectAll(true);
                	gridPanel.getSelectionModel().select(0, true);
                }
            }else{
            	$("#electricAnalysisRealtimeDetailsInverDiv1_id").html('');
            	$("#electricAnalysisRealtimeDetailsInverDiv2_id").html('');
            	$("#electricAnalysisRealtimeDetailsInverDiv3_id").html('');
            	$("#electricAnalysisRealtimeDetailsInverDiv4_id").html('');
            	$("#electricAnalysisRealtimeDetailsInverDiv5_id").html('');
            	$("#electricAnalysisRealtimeDetailsInverDiv6_id").html('');
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").getValue();
            var startDate=Ext.getCmp('electricAnalysisRealtimeDetailsStartDate_Id').rawValue;
            var endDate=Ext.getCmp('electricAnalysisRealtimeDetailsEndDate_Id').rawValue;
            var new_params = {
            	orgId: orgId,
            	wellName: wellName,
                startDate:startDate,
                endDate:endDate,
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});