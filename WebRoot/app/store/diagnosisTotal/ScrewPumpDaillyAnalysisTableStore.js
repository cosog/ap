Ext.define('AP.store.diagnosisTotal.ScrewPumpDaillyAnalysisTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.dcrewPumpDaillyAnalysisTableStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getPCPAnalysisAndAcqAndControlData',
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
        	var get_rawData = store.proxy.reader.rawData;
        	var analysisDataList=get_rawData.analysisDataList;
        	var acquisitionDataList=get_rawData.acquisitionDataList;
        	
        	var dataStr="{\"items\":[";
        	for(var i=0;i<analysisDataList.length;i++){
        		switch(analysisDataList[i].dataIndex.toUpperCase()) {
        		case "runTime".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"runTime\",\"value\":"+get_rawData.runTime+",\"curve\":\"\"},";
        			break;
        		case "runTimeEfficiency".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"runTimeEfficiency\",\"value\":"+get_rawData.runTimeEfficiency+",\"curve\":\"\"},";
        			break;
        		case "runTimeEfficiency".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"theoreticalProduction\",\"value\":\""+get_rawData.theoreticalProductionMax+"/"+get_rawData.theoreticalProductionMin+"/"+get_rawData.theoreticalProduction+"\",\"curve\":\"\"},";
        			break;
        		case "liquidWeightProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"liquidWeightProduction\",\"value\":\""+get_rawData.liquidProductionMax+"/"+get_rawData.liquidProductionMin+"/"+get_rawData.liquidProduction+"\",\"curve\":\"\"},";
        			break;
        		case "oilWeightProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"oilWeightProduction\",\"value\":\""+get_rawData.oilProductionMax+"/"+get_rawData.oilProductionMin+"/"+get_rawData.oilProduction+"\",\"curve\":\"\"},";
        			break;
        		case "waterWeightProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterWeightProduction\",\"value\":\""+get_rawData.waterProductionMax+"/"+get_rawData.waterProductionMin+"/"+get_rawData.waterProduction+"\",\"curve\":\"\"},";
        			break;
        		case "waterCut_W".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterCut_W\",\"value\":\""+get_rawData.waterCutMax+"/"+get_rawData.waterCutMin+"/"+get_rawData.waterCut+"\",\"curve\":\"\"},";
    	        	break;
        		case "liquidVolumetricProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"liquidVolumetricProduction\",\"value\":\""+get_rawData.liquidProductionMax+"/"+get_rawData.liquidProductionMin+"/"+get_rawData.liquidProduction+"\",\"curve\":\"\"},";
        			break;
        		case "oilVolumetricProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"oilVolumetricProduction\",\"value\":\""+get_rawData.oilProductionMax+"/"+get_rawData.oilProductionMin+"/"+get_rawData.oilProduction+"\",\"curve\":\"\"},";
        			break;
        		case "waterVolumetricProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterVolumetricProduction\",\"value\":\""+get_rawData.waterProductionMax+"/"+get_rawData.waterProductionMin+"/"+get_rawData.waterProduction+"\",\"curve\":\"\"},";
        			break;
        		case "waterCut".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterCut\",\"value\":\""+get_rawData.waterCutMax+"/"+get_rawData.waterCutMin+"/"+get_rawData.waterCut+"\",\"curve\":\"\"},";
    	        	break;
        		case "pumpEff1".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff1\",\"value\":\""+get_rawData.pumpEff1Max+"/"+get_rawData.pumpEff1Min+"/"+get_rawData.pumpEff1+"\",\"curve\":\"\"},";
        			break;
        		case "pumpEff2".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff2\",\"value\":\""+get_rawData.pumpEff2Max+"/"+get_rawData.pumpEff2Min+"/"+get_rawData.pumpEff2+"\",\"curve\":\"\"},";
        			break;
        		case "pumpEff".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff\",\"value\":\""+get_rawData.pumpEffMax+"/"+get_rawData.pumpEffMin+"/"+get_rawData.pumpEff+"\",\"curve\":\"\"},";
        			break;
        		case "systemEfficiency".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"systemEfficiency\",\"value\":\""+get_rawData.systemEfficiencyMax+"/"+get_rawData.systemEfficiencyMin+"/"+get_rawData.systemEfficiency+"\",\"curve\":\"\"},";
        			break;
        		case "powerConsumptionPerTHM".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"powerConsumptionPerTHM\",\"value\":\""+get_rawData.powerConsumptionPerTHMMax+"/"+get_rawData.powerConsumptionPerTHMMin+"/"+get_rawData.powerConsumptionPerTHM+"\",\"curve\":\"\"},";
        			break;
        		case "avgWatt".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"avgWatt\",\"value\":\""+get_rawData.avgWattMax+"/"+get_rawData.avgWattMin+"/"+get_rawData.avgWatt+"\",\"curve\":\"\"},";
        			break;
        		case "waterPower".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterPower\",\"value\":\""+get_rawData.waterPowerMax+"/"+get_rawData.waterPowerMin+"/"+get_rawData.waterPower+"\",\"curve\":\"\"},";
        			break;
        		case "pumpSettingDepth".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpSettingDepth\",\"value\":\""+get_rawData.pumpSettingDepthMax+"/"+get_rawData.pumpSettingDepthMin+"/"+get_rawData.pumpSettingDepth+"\",\"curve\":\"\"},";
        			break;
        		case "producingFluidLevel".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"producingFluidLevel\",\"value\":\""+get_rawData.producingFluidLevelMax+"/"+get_rawData.producingFluidLevelMin+"/"+get_rawData.producingFluidLevel+"\",\"curve\":\"\"},";
        			break;
        		case "submergence".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"submergence\",\"value\":\""+get_rawData.submergenceMax+"/"+get_rawData.submergenceMin+"/"+get_rawData.submergence+"\",\"curve\":\"\"},";
        			break;
        		case "productionGasOilRatio".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"productionGasOilRatio\",\"value\":\""+get_rawData.productionGasOilRatioMax+"/"+get_rawData.productionGasOilRatioMin+"/"+get_rawData.productionGasOilRatio+"\",\"curve\":\"\"},";
        			break;
        		case "todayKWattH".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"todayKWattH\",\"value\":\""+get_rawData.todayKWattH+"\",\"curve\":\"\"},";
        			break;
        		case "todayKVarH".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"todayKVarH\",\"value\":\""+get_rawData.todayKVarH+"\",\"curve\":\"\"},";
        			break;
        		case "todayVAEnergy".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"todayVAEnergy\",\"value\":\""+get_rawData.todayVAEnergy+"\",\"curve\":\"\"},";
        			break;
        		}
        	}
        	if(stringEndWith(dataStr,",")){
        		dataStr = dataStr.substring(0, dataStr.length - 1);
    		}
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
    		for(var i=0;i<acquisitionDataList.length;i++){
    			switch(acquisitionDataList[i].dataIndex.toUpperCase()) {
    			case "rpm".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"rpm\",\"value\":\""+get_rawData.rpm+"/"+get_rawData.rpmMax+"/"+get_rawData.rpMmin+"\",\"curve\":\"\"},";
        			break;
    			case "frequency".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"frequency\",\"value\":\""+get_rawData.frequencyMax+"/"+get_rawData.frequencyMin+"/"+get_rawData.frequency+"\",\"curve\":\"\"},";
        			break;
    			case "Ia".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Ia\",\"value\":\""+get_rawData.IaMax+"/"+get_rawData.IaMin+"/"+get_rawData.Ia+"\",\"curve\":\"\"},";
        			break;
    			case "Ib".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Ib\",\"value\":\""+get_rawData.IbMax+"/"+get_rawData.IbMin+"/"+get_rawData.Ib+"\",\"curve\":\"\"},";
        			break;
    			case "Ic".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Ic\",\"value\":\""+get_rawData.IcMax+"/"+get_rawData.IcMin+"/"+get_rawData.Ic+"\",\"curve\":\"\"},";
        			break;
    			case "Va".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Va\",\"value\":\""+get_rawData.VaMax+"/"+get_rawData.VaMin+"/"+get_rawData.Va+"\",\"curve\":\"\"},";
        			break;
    			case "Vb".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Vb\",\"value\":\""+get_rawData.VbMax+"/"+get_rawData.VbMin+"/"+get_rawData.Vb+"\",\"curve\":\"\"},";
        			break;
    			case "Vc".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Vc\",\"value\":\""+get_rawData.VcMax+"/"+get_rawData.VcMin+"/"+get_rawData.Vc+"\",\"curve\":\"\"},";
        			break;
    			case "wattSum".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"wattSum\",\"value\":\""+get_rawData.wattSumMax+"/"+get_rawData.wattSumMin+"/"+get_rawData.wattSum+"\",\"curve\":\"\"},";
        			break;
    			case "varSum".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"varSum\",\"value\":\""+get_rawData.varSumMax+"/"+get_rawData.varSumMin+"/"+get_rawData.varSum+"\",\"curve\":\"\"},";
        			break;
    			case "vaSum".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"vaSum\",\"value\":\""+get_rawData.vaSumMax+"/"+get_rawData.vaSumMin+"/"+get_rawData.vaSum+"\",\"curve\":\"\"},";
        			break;
    			case "PFSum".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"PFSum\",\"value\":\""+get_rawData.PFSumMax+"/"+get_rawData.PFSumMin+"/"+get_rawData.PFSum+"\",\"curve\":\"\"},";
        			break;
    			case "tubingPressure".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"tubingPressure\",\"value\":\""+get_rawData.tubingPressureMax+"/"+get_rawData.tubingPressureMin+"/"+get_rawData.tubingPressure+"\",\"curve\":\"\"},";
        			break;
    			case "casingPressure".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"casingPressure\",\"value\":\""+get_rawData.casingPressureMax+"/"+get_rawData.casingPressureMin+"/"+get_rawData.casingPressure+"\",\"curve\":\"\"},";
        			break;
    			case "wellHeadFluidTemperature".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"wellHeadFluidTemperature\",\"value\":\""+get_rawData.wellHeadFluidTemperatureMax+"/"+get_rawData.wellHeadFluidTemperatureMin+"/"+get_rawData.wellHeadFluidTemperature+"\",\"curve\":\"\"},";
        			break;
    			}
    		}
    		if(stringEndWith(acqSataStr,",")){
    			acqSataStr = acqSataStr.substring(0, dataStr.length - 1);
    		}
    		acqSataStr+="]}";
    		
    		var controlSataStr="{\"items\":[";
    		controlSataStr+="{\"item\":\"启/停抽\",\"value\":\"运行\",\"operation\":\"\"},";
    		controlSataStr+="{\"item\":\"运行频率(Hz)\",\"value\":\"60.5\",\"operation\":\"\"}";
    		controlSataStr+="]}";
    		
    		var storeData=Ext.JSON.decode(dataStr);
    		var acqStoreData=Ext.JSON.decode(acqSataStr);
    		var controlStoreData=Ext.JSON.decode(controlSataStr);
    		
    		var store=Ext.create('Ext.data.Store', {
			    fields:['item', 'itemCode','value', 'curve'],
			    data:storeData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
    		var acqStore=Ext.create('Ext.data.Store', {
			    fields:['item', 'itemCode','value', 'curve'],
			    data:acqStoreData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
    		var controlStore=Ext.create('Ext.data.Store', {
			    fields:['item','value','operation'],
			    data:controlStoreData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
    		
    		var GridPanel=Ext.getCmp("ScrewPumpDailyCalDataGridPanel_Id");
    		if(!isNotVal(GridPanel)){
    			GridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ScrewPumpDailyCalDataGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: store,
    			    columns: [
    			    	{ 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconDiagnoseTotalCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ScrewPumpDailyAnalysisTableCalDataPanel_Id").add(GridPanel);
    		}else{
    			GridPanel.reconfigure(store);
    		}
    		
    		var acqGridPanel=Ext.getCmp("ScrewPumpDailyAcqDataGridPanel_Id");
    		if(!isNotVal(acqGridPanel)){
    			acqGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ScrewPumpDailyAcqDataGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: acqStore,
    			    columns: [
    			    	{ 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconDiagnoseTotalCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ScrewPumpDailyAnalysisTableAcqDataPanel_Id").add(acqGridPanel);
    		}else{
    			acqGridPanel.reconfigure(acqStore);
    		}
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.id;
        	var new_params = {
        			id: id
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});