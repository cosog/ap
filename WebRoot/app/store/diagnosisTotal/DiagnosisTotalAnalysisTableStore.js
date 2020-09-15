Ext.define('AP.store.diagnosisTotal.DiagnosisTotalAnalysisTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisTotalAnalysisTableStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisTotalController/getAnalysisAndAcqAndControlData',
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
        		case "wattDegreeBalance".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"wattDegreeBalance\",\"value\":\""+get_rawData.wattDegreeBalanceMax+"/"+get_rawData.wattDegreeBalanceMin+"/"+get_rawData.wattDegreeBalance+"\",\"curve\":\"\"},";
        			break;
        		case "upStrokeWattMax".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"upStrokeWattMax\",\"value\":\""+get_rawData.upStrokeWattMax_Max+"/"+get_rawData.upStrokeWattMax_Min+"/"+get_rawData.upStrokeWattMax_Avg+"\",\"curve\":\"\"},";
            		break;
        		case "downStrokeWattMax".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"downStrokeWattMax\",\"value\":\""+get_rawData.downStrokeWattMax_Max+"/"+get_rawData.downStrokeWattMax_Min+"/"+get_rawData.downStrokeWattMax_Avg+"\",\"curve\":\"\"},";
            		break;
        		case "iDegreeBalance".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"iDegreeBalance\",\"value\":\""+get_rawData.iDegreeBalanceMax+"/"+get_rawData.iDegreeBalanceMin+"/"+get_rawData.iDegreeBalance+"\",\"curve\":\"\"},";
            		break;
        		case "upStrokeIMax".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"upStrokeIMax\",\"value\":\""+get_rawData.upStrokeIMax_Max+"/"+get_rawData.upStrokeIMax_Min+"/"+get_rawData.upStrokeIMax_Avg+"\",\"curve\":\"\"},";
            		break;
        		case "downStrokeIMax".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"downStrokeIMax\",\"value\":\""+get_rawData.downStrokeIMax_Max+"/"+get_rawData.downStrokeIMax_Min+"/"+get_rawData.downStrokeIMax_Avg+"\",\"curve\":\"\"},";
            		break;
        		case "deltaRadius".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"deltaRadius\",\"value\":\""+get_rawData.deltaRadiusMax+"/"+get_rawData.deltaRadiusMin+"/"+get_rawData.deltaRadius+"\",\"curve\":\"\"},";
            		break;
        		case "theoreticalProduction".toUpperCase():
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
        		case "availableStrokeProd_w".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"availableStrokeProd_w\",\"value\":\""+get_rawData.availableStrokeProdMax+"/"+get_rawData.availableStrokeProdMin+"/"+get_rawData.availableStrokeProd+"\",\"curve\":\"\"},";
            		break;
        		case "pumpClearanceLeakProd_w".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpClearanceLeakProd_w\",\"value\":\""+get_rawData.pumpClearanceLeakProdMax+"/"+get_rawData.pumpClearanceLeakProdMin+"/"+get_rawData.pumpClearanceLeakProd+"\",\"curve\":\"\"},";
            		break;
        		case "TVLeakWeightProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"TVLeakWeightProduction\",\"value\":\""+get_rawData.TVLeakProductionMax+"/"+get_rawData.TVLeakProductionMin+"/"+get_rawData.TVLeakProduction+"\",\"curve\":\"\"},";
            		break;
        		case "SVLeakWeightProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"SVLeakWeightProduction\",\"value\":\""+get_rawData.SVLeakProductionMax+"/"+get_rawData.SVLeakProductionMin+"/"+get_rawData.SVLeakProduction+"\",\"curve\":\"\"},";
            		break;
        		case "gasInfluenceProd_w".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"gasInfluenceProd_w\",\"value\":\""+get_rawData.gasInfluenceProdMax+"/"+get_rawData.gasInfluenceProdMin+"/"+get_rawData.gasInfluenceProd+"\",\"curve\":\"\"},";
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
        		case "availableStrokeProd_v".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"availableStrokeProd_v\",\"value\":\""+get_rawData.availableStrokeProdMax+"/"+get_rawData.availableStrokeProdMin+"/"+get_rawData.availableStrokeProd+"\",\"curve\":\"\"},";
            		break;
        		case "pumpClearanceLeakProd_v".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpClearanceLeakProd_v\",\"value\":\""+get_rawData.pumpClearanceLeakProdMax+"/"+get_rawData.pumpClearanceLeakProdMin+"/"+get_rawData.pumpClearanceLeakProd+"\",\"curve\":\"\"},";
            		break;
        		case "TVLeakVolumetricProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"TVLeakVolumetricProduction\",\"value\":\""+get_rawData.TVLeakProductionMax+"/"+get_rawData.TVLeakProductionMin+"/"+get_rawData.TVLeakProduction+"\",\"curve\":\"\"},";
            		break;
        		case "SVLeakVolumetricProduction".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"SVLeakVolumetricProduction\",\"value\":\""+get_rawData.SVLeakProductionMax+"/"+get_rawData.SVLeakProductionMin+"/"+get_rawData.SVLeakProduction+"\",\"curve\":\"\"},";
            		break;
        		case "gasInfluenceProd_v".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"gasInfluenceProd_v\",\"value\":\""+get_rawData.gasInfluenceProdMax+"/"+get_rawData.gasInfluenceProdMin+"/"+get_rawData.gasInfluenceProd+"\",\"curve\":\"\"},";
            		break;
        		case "stroke".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"stroke\",\"value\":\""+get_rawData.strokeMax+"/"+get_rawData.strokeMin+"/"+get_rawData.stroke+"\",\"curve\":\"\"},";
            		break;
        		case "SPM".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"SPM\",\"value\":\""+get_rawData.SPMMax+"/"+get_rawData.SPMMin+"/"+get_rawData.SPM+"\",\"curve\":\"\"},";
            		break;
        		case "fMax".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"fMax\",\"value\":\""+get_rawData.fMax_Max+"/"+get_rawData.fMax_Min+"/"+get_rawData.fMax_Avg+"\",\"curve\":\"\"},";
            		break;
        		case "fMin".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"fMin\",\"value\":\""+get_rawData.fMin_Max+"/"+get_rawData.fMin_Min+"/"+get_rawData.fMin_Avg+"\",\"curve\":\"\"},";
            		break;
        		case "deltaF".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"deltaF\",\"value\":\""+get_rawData.deltaFMax+"/"+get_rawData.deltaFMin+"/"+get_rawData.deltaF+"\",\"curve\":\"\"},";
            		break;
        		case "deltaLoadLine".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"deltaLoadLine\",\"value\":\""+get_rawData.deltaLoadLineMax+"/"+get_rawData.deltaLoadLineMin+"/"+get_rawData.deltaLoadLine+"\",\"curve\":\"\"},";
            		break;
        		case "area".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"area\",\"value\":\""+get_rawData.areaMax+"/"+get_rawData.areaMin+"/"+get_rawData.area+"\",\"curve\":\"\"},";
            		break;
        		case "plungerStroke".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"plungerStroke\",\"value\":\""+get_rawData.plungerStrokeMax+"/"+get_rawData.plungerStrokeMin+"/"+get_rawData.plungerStroke+"\",\"curve\":\"\"},";
            		break;
        		case "availablePlungerStroke".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"availablePlungerStroke\",\"value\":\""+get_rawData.availablePlungerStrokeMax+"/"+get_rawData.availablePlungerStrokeMin+"/"+get_rawData.availablePlungerStroke+"\",\"curve\":\"\"},";
            		break;
        		case "noLiquidAvailableStroke".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"noLiquidAvailableStroke\",\"value\":\""+get_rawData.noLiquidAvailableStrokeMax+"/"+get_rawData.noLiquidAvailableStrokeMin+"/"+get_rawData.noLiquidAvailableStroke+"\",\"curve\":\"\"},";
            		break;
        		case "noLiquidFullnessCoefficient".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"noLiquidFullnessCoefficient\",\"value\":\""+get_rawData.noLiquidFullnessCoefficientMax+"/"+get_rawData.noLiquidFullnessCoefficientMin+"/"+get_rawData.noLiquidFullnessCoefficient+"\",\"curve\":\"\"},";
            		break;
        		case "pumpEff1".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff1\",\"value\":\""+get_rawData.pumpEff1Max+"/"+get_rawData.pumpEff1Min+"/"+get_rawData.pumpEff1+"\",\"curve\":\"\"},";
            		break;
        		case "pumpEff2".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff2\",\"value\":\""+get_rawData.pumpEff2Max+"/"+get_rawData.pumpEff2Min+"/"+get_rawData.pumpEff2+"\",\"curve\":\"\"},";
            		break;
        		case "pumpEff3".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff3\",\"value\":\""+get_rawData.pumpEff3Max+"/"+get_rawData.pumpEff3Min+"/"+get_rawData.pumpEff3+"\",\"curve\":\"\"},";
            		break;
        		case "pumpEff4".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff4\",\"value\":\""+get_rawData.pumpEff4Max+"/"+get_rawData.pumpEff4Min+"/"+get_rawData.pumpEff4+"\",\"curve\":\"\"},";
            		break;
        		case "pumpEff".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff\",\"value\":\""+get_rawData.pumpEffMax+"/"+get_rawData.pumpEffMin+"/"+get_rawData.pumpEff+"\",\"curve\":\"\"},";
            		break;
        		case "rodFlexLength".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"rodFlexLength\",\"value\":\""+get_rawData.rodFlexLengthMax+"/"+get_rawData.rodFlexLengthMin+"/"+get_rawData.rodFlexLength+"\",\"curve\":\"\"},";
            		break;
        		case "tubingFlexLength".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"tubingFlexLength\",\"value\":\""+get_rawData.tubingFlexLengthMax+"/"+get_rawData.tubingFlexLengthMin+"/"+get_rawData.tubingFlexLength+"\",\"curve\":\"\"},";
            		break;
        		case "inertiaLength".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"inertiaLength\",\"value\":\""+get_rawData.inertiaLengthMax+"/"+get_rawData.inertiaLengthMin+"/"+get_rawData.inertiaLength+"\",\"curve\":\"\"},";
            		break;
        		case "surfaceSystemEfficiency".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"surfaceSystemEfficiency\",\"value\":\""+get_rawData.surfaceSystemEfficiencyMax+"/"+get_rawData.surfaceSystemEfficiencyMin+"/"+get_rawData.surfaceSystemEfficiency+"\",\"curve\":\"\"},";
            		break;
        		case "welldownSystemEfficiency".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"welldownSystemEfficiency\",\"value\":\""+get_rawData.welldownSystemEfficiencyMax+"/"+get_rawData.welldownSystemEfficiencyMin+"/"+get_rawData.welldownSystemEfficiency+"\",\"curve\":\"\"},";
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
        		case "polishRodPower".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"polishRodPower\",\"value\":\""+get_rawData.polishRodPowerMax+"/"+get_rawData.polishRodPowerMin+"/"+get_rawData.polishRodPower+"\",\"curve\":\"\"},";
            		break;
        		case "waterPower".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterPower\",\"value\":\""+get_rawData.waterPowerMax+"/"+get_rawData.waterPowerMin+"/"+get_rawData.waterPower+"\",\"curve\":\"\"},";
            		break;
        		case "pumpIntakeP".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeP\",\"value\":\""+get_rawData.pumpIntakePMax+"/"+get_rawData.pumpIntakePMin+"/"+get_rawData.pumpIntakeP+"\",\"curve\":\"\"},";
            		break;
        		case "pumpIntakeT".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeT\",\"value\":\""+get_rawData.pumpIntakeTMax+"/"+get_rawData.pumpIntakeTMin+"/"+get_rawData.pumpIntakeT+"\",\"curve\":\"\"},";
            		break;
        		case "pumpIntakeGOL".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeGOL\",\"value\":\""+get_rawData.pumpIntakeGOLMax+"/"+get_rawData.pumpIntakeGOLMin+"/"+get_rawData.pumpIntakeGOL+"\",\"curve\":\"\"},";
            		break;
        		case "pumpIntakeVisl".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeVisl\",\"value\":\""+get_rawData.pumpIntakeVislMax+"/"+get_rawData.pumpIntakeVislMin+"/"+get_rawData.pumpIntakeVisl+"\",\"curve\":\"\"},";
            		break;
        		case "pumpIntakeBo".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeBo\",\"value\":\""+get_rawData.pumpIntakeBoMax+"/"+get_rawData.pumpIntakeBoMin+"/"+get_rawData.pumpIntakeBo+"\",\"curve\":\"\"},";
            		break;
        		case "pumpOutletP".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletP\",\"value\":\""+get_rawData.pumpOutletPMax+"/"+get_rawData.pumpOutletPMin+"/"+get_rawData.pumpOutletP+"\",\"curve\":\"\"},";
            		break;
        		case "pumpOutletT".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletT\",\"value\":\""+get_rawData.pumpOutletTMax+"/"+get_rawData.pumpOutletTMin+"/"+get_rawData.pumpOutletT+"\",\"curve\":\"\"},";
            		break;
        		case "pumpOutletGOL".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletGOL\",\"value\":\""+get_rawData.pumpOutletGOLMax+"/"+get_rawData.pumpOutletGOLMin+"/"+get_rawData.pumpOutletGOL+"\",\"curve\":\"\"},";
            		break;
        		case "pumpOutletVisl".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletVisl\",\"value\":\""+get_rawData.pumpOutletVislMax+"/"+get_rawData.pumpOutletVislMin+"/"+get_rawData.pumpOutletVisl+"\",\"curve\":\"\"},";
            		break;
        		case "pumpOutletBo".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletBo\",\"value\":\""+get_rawData.pumpOutletBoMax+"/"+get_rawData.pumpOutletBoMin+"/"+get_rawData.pumpOutletBo+"\",\"curve\":\"\"},";
            		break;
        		case "pumpBoreDiameter".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpBoreDiameter\",\"value\":\""+get_rawData.pumpBoreDiameterMax+"/"+get_rawData.pumpBoreDiameterMin+"/"+get_rawData.pumpBoreDiameter+"\",\"curve\":\"\"},";
            		break;
        		case "pumpSettingDepth".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpSettingDepth\",\"value\":\""+get_rawData.pumpSettingDepthMax+"/"+get_rawData.pumpSettingDepthMin+"/"+get_rawData.pumpSettingDepth+"\",\"curve\":\"\"},";
            		break;
        		case "producingFluidLevel".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"producingFluidLevel\",\"value\":\""+get_rawData.producingFluidLevelMax+"/"+get_rawData.producingFluidLevelMin+"/"+get_rawData.producingFluidLevel+"\",\"curve\":\"\"},";
            		break;
        		case "levelCorrectValue".toUpperCase():
        			dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"levelCorrectValue\",\"value\":\""+get_rawData.levelCorrectValueMax+"/"+get_rawData.levelCorrectValueMin+"/"+get_rawData.levelCorrectValue+"\",\"curve\":\"\"},";
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
    			case "signal".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"signal\",\"value\":\""+get_rawData.signalMax+"/"+get_rawData.signalMin+"/"+get_rawData.signal+"\",\"curve\":\"\"},";
        			break;
    			}
    		}
    		
    		if(stringEndWith(acqSataStr,",")){
    			acqSataStr = acqSataStr.substring(0, dataStr.length - 1);
    		}
    		acqSataStr+="]}";
    		
    		var controlSataStr="{\"items\":[";
    		controlSataStr+="{\"item\":\"启/停抽\",\"value\":\"运行\",\"operation\":\"\"},";
    		controlSataStr+="{\"item\":\"运行频率(Hz)\",\"value\":\"60.5\",\"operation\":\"\"},";
    		controlSataStr+="{\"item\":\"功图采集周期(min)\",\"value\":\"60\",\"operation\":\"\"}";
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
    		
    		var GridPanel=Ext.getCmp("TotalAnalysisDataGridPanel_Id");
    		if(!isNotVal(GridPanel)){
    			GridPanel=Ext.create('Ext.grid.Panel', {
    				id:'TotalAnalysisDataGridPanel_Id',
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
    			Ext.getCmp("TotalAnalysisDataPanel_Id").add(GridPanel);
    		}else{
    			GridPanel.reconfigure(store);
    		}
    		
    		var acqGridPanel=Ext.getCmp("TotalAcqDataGridPanel_Id");
    		if(!isNotVal(acqGridPanel)){
    			acqGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'TotalAcqDataGridPanel_Id',
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
    			Ext.getCmp("TotalAcqDataPanel_Id").add(acqGridPanel);
    		}else{
    			acqGridPanel.reconfigure(acqStore);
    		}
    		
    		
//    		var controlGridPanel=Ext.getCmp("TotalControlDataGridPanel_Id");
//    		if(!isNotVal(controlGridPanel)){
//    			controlGridPanel=Ext.create('Ext.grid.Panel', {
//    				id:'TotalControlDataGridPanel_Id',
//    				requires: [
//                       	'Ext.grid.selection.SpreadsheetModel',
//                       	'Ext.grid.plugin.Clipboard'
//                       	],
//                    xtype:'spreadsheet-checked',
//                    plugins: [
//                        'clipboard',
//                        'selectionreplicator',
//                        new Ext.grid.plugin.CellEditing({
//                      	  clicksToEdit:2
//                        })
//                    ],
//    				border: false,
//    				columnLines: true,
//    				forceFit: false,
//    				store: controlStore,
//    			    columns: [
//    			        { header: '操作项',  dataIndex: 'item',align:'left',flex:3},
//    			        { header: '状态/值', dataIndex: 'value',align:'center',flex:1,editor:{allowBlank:false}},
//    			        { 	header: '操作', 
//    			        	dataIndex: 'operation',
//    			        	align:'center',
//    			        	flex:1,
//    			        	renderer :function(value,e,o){
//    			        		var id = e.record.id;
//    			        		var item=o.data.item;
//    			        		var text="";
//    			        		if(item=="启/停抽"){
//    			        			if(o.data.value=="运行"){
//    			        				text="停抽";
//    			        			}else{
//    			        				text="启抽";
//    			        			}
//    			        		}else{
//    			        			text="设置";
//    			        		}
//    		                    Ext.defer(function () {
//    		                        Ext.widget('button', {
//    		                            renderTo: id,
//    		                            height: 18,
//    		                            width: 50,
//    		                            text: text,
//    		                            handler: function () {
//    		                            	var operaName="";
//    		                            	if(text=="停抽"||text=="启抽"){
//    		                            		operaName="是否执行"+text+"操作";
//    		                            	}else{
//    		                            		operaName="是否执行"+text+item.split("(")[0]+"操作";
//    		                            	}
//    		                            	 Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
//    		                                 Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
//    		                                 Ext.Msg.confirm("操作确认", operaName, function (btn) {
//    		                                     if (btn == "yes") {
//    		                                         var win_Obj = Ext.getCmp("WellControlCheckPassWindow_Id")
//    		                                         if (win_Obj != undefined) {
//    		                                             win_Obj.destroy();
//    		                                         }
//    		                                         var WellControlCheckPassWindow = Ext.create("AP.view.diagnosis.WellControlCheckPassWindow", {
//    		                                             title: '批量停井'
//    		                                         });
//    		                                         WellControlCheckPassWindow.show();
//    		                                     }
//    		                                 });
//    		                            }
//    		                        });
//    		                    }, 50);
//    		                    return Ext.String.format('<div id="{0}"></div>', id);
//    			        	} 
//    			        }
//    			    ]
//    			});
//    			Ext.getCmp("TotalControlDataPanel_Id").add(controlGridPanel);
//    		}else{
//    			controlGridPanel.reconfigure(controlStore);
//    		}
    		
    		Ext.getCmp("FSDiagramAnalysisDailyDetailsRightRunRangeTextArea_Id").setValue(get_rawData.runRange);
    		Ext.getCmp("FSDiagramAnalysisDailyDetailsRightResultCodeTextArea_Id").setValue(get_rawData.workingConditionString);
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.id;
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