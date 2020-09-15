Ext.define('AP.store.diagnosis.SingleAnalysisiListStore', {
    extend: 'Ext.data.Store',
    fields: ['wellName','acquisitionTime',
    	'commStatusName','commTime','commTimeEfficiency',
    	'runStatusName', 'runTime','runTimeEfficiency', 'runRange', 
    	'workingConditionName', 'optimizationSuggestion', 
    	'liquidWeightProduction', 'oilWeightProduction', 'waterWeightProduction', 'waterCut', 
    	'productionGasOilRatio','tubingPressure', 'casingPressure', 'wellheadFluidTemperature',
        'stroke', 'SPM', 'fullnesscoEfficient', 
        'pumpboreDiameter','pumpEff', 'pumpSettingDepth', 'producingFluidLevel', 'submergence', 
        'wattDegreeBalanceName', 'wattDegreeBalance','wattRatio', 'iDegreeBalanceName', 'iDegreeBalance', 'iRatio', 
        'systemEfficiency', 'surfaceSystemEfficiency','welldownSystemEfficiency', 'motorInputActivePower', 'polishrodPower', 'waterPower','powerConsumptionPerthm', 
        'todayKWattH', 
        'theoreticalProduction', 'availablePlungerstrokeProd', 'pumpClearanceLeakProd', 'pumpOutletGol'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
    	type: 'ajax',
        url: context + '/diagnosisAnalysisOnlyController/getProductionWellRTAnalysisWellList',
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
            var column = createDiagnosisColumn(arrColumns);
            Ext.getCmp("DiagnosisAnalysisColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var newColumns = Ext.JSON.decode(column);
            var bbar = new Ext.PagingToolbar({
            	store: store,
            	displayInfo: true,
            	displayMsg: '当前 {0}~{1}条  共 {2} 条'
	        });
            var gridPanel = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel= Ext.create('Ext.grid.Panel', {
                    id: 'FSDiagramAnalysisSingleDetails_Id',
                    border: false,
                    forceFit: false,
                    bbar: bbar,
                    store:store,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    columnLines: true,
                    columns:newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {
                            	if(isNaN(selected[0].id)){
                            		$("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").html('');
                                	$("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").html('');
                                	$("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").html('');
                                	$("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").html('');
                                	$("#FSDiagramAnalysisSingleWellboreDetailsDiv5_id").html('');
                                	$("#FSDiagramAnalysisSingleWellboreDetailsDiv6_id").html('');
                            		Ext.getCmp("FSDiagramAnalysisSingleDetailsRightRunRangeTextArea_Id").setValue("");
                            		Ext.getCmp("FSDiagramAnalysisSingleDetailsRightResultCodeTextArea_Id").setValue("");
                                	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightAnalysisPanel_Id").removeAll();
                                	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightAcqPanel_Id").removeAll();
                                	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").removeAll();
                                	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlPanel_Id").removeAll();
                                	if($("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer")!=null){
                                		$("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer").html('');
                                	}
                            	}else{
                            		//请求图形数据
                            		Ext.create("AP.store.diagnosis.SinglePumpCardStore");
                            		Ext.create("AP.store.diagnosis.DiagnosisAnalysisTableStore");
                            	}
                            }
                            
                        },
                        itemdblclick: function (view,record,item,index,e,eOpts) {
                        	var wellName=Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
                    		if(wellName==null||wellName==""){
                    			Ext.getCmp("RPCRealtimeAnalysisWellListPanel_Id").setTitle("单井历史");
                    			Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").show();
                            	Ext.getCmp("FSDiagramAnalysisSingleDetailsEndDate_Id").show();
                            	
                            	Ext.getCmp("FSDiagramAnalysisSingleDetailsHisBtn_Id").hide();
                                Ext.getCmp("FSDiagramAnalysisSingleDetailsAllBtn_Id").show();
                                
                                var statPanelId=getFSDiagramAnalysisSingleStatType().piePanelId;
                            	Ext.getCmp(statPanelId).collapse();
                            	
                    			Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').setValue(record.data.wellName);
                            	Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').setRawValue(record.data.wellName);
                            	Ext.getCmp('FSDiagramAnalysisSingleDetails_Id').getStore().loadPage(1);
                            	
                    		}
                        },
                        select: function(grid, record, index, eOpts) {
                        	Ext.getCmp('FSDiagramAnalysisSingleDetailsSelectRow_Id').setValue(index);
                       }
                    }
                });
            	var panelId=getFSDiagramAnalysisSingleStatType().panelId;
            	Ext.getCmp(panelId).add(gridPanel);
            	
            	var length = gridPanel.dockedItems.keys.length;
                var refreshStr= "";
                for (var i = 0; i < length; i++) {
                   if (gridPanel.dockedItems.keys[i].indexOf("pagingtoolbar") !== -1) {
                      refreshStr= gridPanel.dockedItems.keys[i];
                   }
                }
            	gridPanel.dockedItems.get(refreshStr).child('#refresh').setHandler(   
            	        function() {
            	        	Ext.create("AP.store.diagnosis.WorkStatusStatisticsInfoStore");
            	         }
            	)
            	
            }else{
            	gridPanel.reconfigure(newColumns);
            }
            var startDate=Ext.getCmp('FSDiagramAnalysisSingleDetailsStartDate_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").setValue(get_rawData.start_date==undefined?get_rawData.startDate:get_rawData.start_date);
            }
            if(get_rawData.totalCount>0){
            	var SingleAnalysisGridPanel = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id");
                if (isNotVal(SingleAnalysisGridPanel)) {
                	SingleAnalysisGridPanel.getSelectionModel().deselectAll(true);
                	
                	
                	var FSDiagramAnalysisSingleDetailsSelectRow=Ext.getCmp('FSDiagramAnalysisSingleDetailsSelectRow_Id').getValue();
                	var wellName = Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
                	if(wellName==null||wellName==""){
                		SingleAnalysisGridPanel.getSelectionModel().select(parseInt(FSDiagramAnalysisSingleDetailsSelectRow), true);
                	}else{
                		SingleAnalysisGridPanel.getSelectionModel().select(0, true);
                	}
                }
            }else{
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv5_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv6_id").html('');
        		Ext.getCmp("FSDiagramAnalysisSingleDetailsRightRunRangeTextArea_Id").setValue("");
        		Ext.getCmp("FSDiagramAnalysisSingleDetailsRightResultCodeTextArea_Id").setValue("");
            	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightAnalysisPanel_Id").removeAll();
            	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightAcqPanel_Id").removeAll();
            	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").removeAll();
            	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlPanel_Id").removeAll();
            	if($("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer")!=null){
            		$("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer").html('');
            	}
            }
            
            
        },
        beforeload: function (store, options) {
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
            var startDate=Ext.getCmp('FSDiagramAnalysisSingleDetailsStartDate_Id').rawValue;
            var endDate=Ext.getCmp('FSDiagramAnalysisSingleDetailsEndDate_Id').rawValue;
            var statValue = Ext.getCmp('FSDiagramAnalysisSingleDetailsSelectedStatValue_Id').getValue();
            var type=getFSDiagramAnalysisSingleStatType().type;
            var new_params = {
                orgId: orgId,
                wellName: wellName,
                startDate:startDate,
                endDate:endDate,
                statValue:statValue,
                type:type,
                wellType:200
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        	onStoreSizeChange(v, o, "DiagnosisPumpingUnit_SingleDinagnosisAnalysisTotalCount_Id");
        }
    }
});