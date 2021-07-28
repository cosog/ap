Ext.define('AP.store.diagnosis.DiagnosisAnalysisTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.diagnosisAnalysisTableStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisAnalysisOnlyController/getAnalysisAndAcqAndControlData',
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
        	var isControl=get_rawData.isControl;
        	var analysisDataList=get_rawData.analysisDataList;
        	var acquisitionDataList=get_rawData.acquisitionDataList;
    		var dataStr="{\"items\":[";
    		for(var i=0;i<analysisDataList.length;i++){
    			switch(analysisDataList[i].dataIndex.toUpperCase()) {
    			case "wattDegreeBalance".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"wattDegreeBalance\",\"value\":\""+get_rawData.wattDegreeBalance+"\",\"curve\":\"\"},";
        			break;
    			case "wattRatio".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"wattRatio\",\"value\":\""+get_rawData.wattRatio+"\",\"curve\":\"\"},";
        			break;
    			case "iDegreeBalance".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"iDegreeBalance\",\"value\":\""+get_rawData.iDegreeBalance+"\",\"curve\":\"\"},";
        			break;
    			case "iRatio".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"iRatio\",\"value\":\""+get_rawData.iRatio+"\",\"curve\":\"\"},";
        			break;
    			case "deltaRadius".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"deltaRadius\",\"value\":\""+get_rawData.deltaRadius+"\",\"curve\":\"\"},";
        			break;
    			case "theoreticalProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"theoreticalProduction\",\"value\":\""+get_rawData.theoreticalProduction+"\",\"curve\":\"\"},";
        			break;
    			case "liquidWeightProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"liquidWeightProduction\",\"value\":\""+get_rawData.liquidProduction+"\",\"curve\":\"\"},";
        			break;
    			case "oilWeightProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"oilWeightProduction\",\"value\":\""+get_rawData.oilProduction+"\",\"curve\":\"\"},";
        			break;
    			case "waterWeightProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterWeightProduction\",\"value\":\""+get_rawData.waterProduction+"\",\"curve\":\"\"},";
        			break;
        			
    			case "liquidWeightProduction_L".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"liquidWeightProduction_L\",\"value\":\""+get_rawData.liquidProduction_L+"\",\"curve\":\"\"},";
        			break;
    			case "oilWeightProduction_L".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"oilWeightProduction_L\",\"value\":\""+get_rawData.oilProduction_L+"\",\"curve\":\"\"},";
        			break;
    			case "waterWeightProduction_L".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterWeightProduction_L\",\"value\":\""+get_rawData.waterProduction_L+"\",\"curve\":\"\"},";
        			break;
        			
    			case "weightWaterCut".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"weightWaterCut\",\"value\":\""+get_rawData.waterCut+"\",\"curve\":\"\"},";
        			break;
    			case "availablePlungerstrokeProd_W".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"availablePlungerstrokeProd_W\",\"value\":\""+get_rawData.availablePlungerstrokeProd+"\",\"curve\":\"\"},";
        			break;
    			case "pumpClearanceLeakProd_W".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpClearanceLeakProd_W\",\"value\":\""+get_rawData.pumpClearanceLeakProd+"\",\"curve\":\"\"},";
        			break;
    			case "TVLeakWeightProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"TVLeakWeightProduction\",\"value\":\""+get_rawData.tvleakProduction+"\",\"curve\":\"\"},";
        			break;
    			case "SVLeakWeightProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"SVLeakWeightProduction\",\"value\":\""+get_rawData.svleakProduction+"\",\"curve\":\"\"},";
        			break;
    			case "gasInfluenceProd_W".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"gasInfluenceProd_W\",\"value\":\""+get_rawData.gasInfluenceProd+"\",\"curve\":\"\"},";
    	        	break;
    			case "liquidVolumetricProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"liquidVolumetricProduction\",\"value\":\""+get_rawData.liquidProduction+"\",\"curve\":\"\"},";
        			break;
    			case "oilVolumetricProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"oilVolumetricProduction\",\"value\":\""+get_rawData.oilProduction+"\",\"curve\":\"\"},";
        			break;
    			case "waterVolumetricProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterVolumetricProduction\",\"value\":\""+get_rawData.waterProduction+"\",\"curve\":\"\"},";
        			break;
    			case "volumeWaterCut".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"volumeWaterCut\",\"value\":\""+get_rawData.waterCut+"\",\"curve\":\"\"},";
        			break;
    			case "availablePlungerstrokeProd_V".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"availablePlungerstrokeProd_V\",\"value\":\""+get_rawData.availablePlungerstrokeProd+"\",\"curve\":\"\"},";
        			break;
    			case "pumpClearanceLeakProd_V".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpClearanceLeakProd_V\",\"value\":\""+get_rawData.pumpClearanceLeakProd+"\",\"curve\":\"\"},";
        			break;
    			case "TVLeakVolumetricProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"TVLeakVolumetricProduction\",\"value\":\""+get_rawData.tvleakProduction+"\",\"curve\":\"\"},";
        			break;
    			case "SVLeakVolumetricProduction".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"SVLeakVolumetricProduction\",\"value\":\""+get_rawData.svleakProduction+"\",\"curve\":\"\"},";
        			break;
    			case "gasInfluenceProd_V".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"gasInfluenceProd_V\",\"value\":\""+get_rawData.gasInfluenceProd+"\",\"curve\":\"\"},";
    	        	break;
    			case "stroke".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"stroke\",\"value\":\""+get_rawData.stroke+"\",\"curve\":\"\"},";
        			break;
    			case "spm".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"spm\",\"value\":\""+get_rawData.spm+"\",\"curve\":\"\"},";
        			break;
    			case "fmax".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"fmax\",\"value\":\""+get_rawData.fmax+"\",\"curve\":\"\"},";
        			break;
    			case "fmin".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"fmin\",\"value\":\""+get_rawData.fmin+"\",\"curve\":\"\"},";
        			break;
    			case "deltaF".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"deltaF\",\"value\":\""+get_rawData.deltaF+"\",\"curve\":\"\"},";
        			break;
    			case "upperLoadLineOfExact".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"upperLoadLineOfExact\",\"value\":\""+get_rawData.upperLoadLineOfExact+"\",\"curve\":\"\"},";
        			break;
    			case "deltaLoadLine".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"deltaLoadLine\",\"value\":\""+get_rawData.deltaLoadLine+"\",\"curve\":\"\"},";
        			break;
    			case "area".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"area\",\"value\":\""+get_rawData.area+"\",\"curve\":\"\"},";
        			break;
    			case "plungerStroke".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"plungerStroke\",\"value\":\""+get_rawData.plungerStroke+"\",\"curve\":\"\"},";
        			break;
    			case "availablePlungerStroke".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"availablePlungerStroke\",\"value\":\""+get_rawData.availablePlungerStroke+"\",\"curve\":\"\"},";
        			break;
    			case "noLiquidAvailablePlungerStroke".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"noLiquidAvailablePlungerStroke\",\"value\":\""+get_rawData.noLiquidAvailablePlungerStroke+"\",\"curve\":\"\"},";
        			break;
    			case "noLiquidFullnessCoefficient".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"noLiquidFullnessCoefficient\",\"value\":\""+get_rawData.noLiquidFullnessCoefficient+"\",\"curve\":\"\"},";
        			break;
    			case "pumpEff1".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff1\",\"value\":\""+get_rawData.pumpEff1+"\",\"curve\":\"\"},";
        			break;
    			case "pumpEff2".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff2\",\"value\":\""+get_rawData.pumpEff2+"\",\"curve\":\"\"},";
        			break;
    			case "pumpEff3".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff3\",\"value\":\""+get_rawData.pumpEff3+"\",\"curve\":\"\"},";
        			break;
    			case "pumpEff4".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff4\",\"value\":\""+get_rawData.pumpEff4+"\",\"curve\":\"\"},";
        			break;
    			case "pumpEff".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpEff\",\"value\":\""+get_rawData.pumpEff+"\",\"curve\":\"\"},";
        			break;
    			case "rodFlexLength".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"rodFlexLength\",\"value\":\""+get_rawData.rodFlexLength+"\",\"curve\":\"\"},";
        			break;
    			case "tubingFlexLength".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"tubingFlexLength\",\"value\":\""+get_rawData.tubingFlexLength+"\",\"curve\":\"\"},";
        			break;
    			case "inertiaLength".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"inertiaLength\",\"value\":\""+get_rawData.inertiaLength+"\",\"curve\":\"\"},";
        			break;
    			case "surfaceSystemEfficiency".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"surfaceSystemEfficiency\",\"value\":\""+get_rawData.surfaceSystemEfficiency+"\",\"curve\":\"\"},";
        			break;
    			case "welldownSystemEfficiency".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"welldownSystemEfficiency\",\"value\":\""+get_rawData.welldownSystemEfficiency+"\",\"curve\":\"\"},";
        			break;
    			case "systemEfficiency".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"systemEfficiency\",\"value\":\""+get_rawData.systemEfficiency+"\",\"curve\":\"\"},";
        			break;
    			case "energyPer100mLift".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"energyPer100mLift\",\"value\":\""+get_rawData.energyPer100mLift+"\",\"curve\":\"\"},";
        			break;
    			case "averageWatt".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"averageWatt\",\"value\":\""+get_rawData.averageWatt+"\",\"curve\":\"\"},";
        			break;
    			case "polishrodPower".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"polishrodPower\",\"value\":\""+get_rawData.polishrodPower+"\",\"curve\":\"\"},";
        			break;
    			case "waterPower".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"waterPower\",\"value\":\""+get_rawData.waterPower+"\",\"curve\":\"\"},";
        			break;
    			case "pumpIntakeP".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeP\",\"value\":\""+get_rawData.pumpIntakeP+"\",\"curve\":\"\"},";
        			break;
    			case "pumpIntakeT".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeT\",\"value\":\""+get_rawData.pumpIntakeT+"\",\"curve\":\"\"},";
        			break;
    			case "pumpIntakeGOL".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeGOL\",\"value\":\""+get_rawData.pumpIntakeGOL+"\",\"curve\":\"\"},";
        			break;
    			case "pumpIntakeVisl".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeVisl\",\"value\":\""+get_rawData.pumpIntakeVisl+"\",\"curve\":\"\"},";
        			break;
    			case "pumpIntakeBo".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpIntakeBo\",\"value\":\""+get_rawData.pumpIntakeBo+"\",\"curve\":\"\"},";
        			break;
    			case "pumpOutletP".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletP\",\"value\":\""+get_rawData.pumpOutletP+"\",\"curve\":\"\"},";
        			break;
    			case "pumpOutletT".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletT\",\"value\":\""+get_rawData.pumpOutletT+"\",\"curve\":\"\"},";
        			break;
    			case "pumpOutletGOL".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletGOL\",\"value\":\""+get_rawData.pumpOutletGOL+"\",\"curve\":\"\"},";
        			break;
    			case "pumpOutletVisl".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletVisl\",\"value\":\""+get_rawData.pumpOutletVisl+"\",\"curve\":\"\"},";
        			break;
    			case "pumpOutletBo".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpOutletBo\",\"value\":\""+get_rawData.pumpOutletBo+"\",\"curve\":\"\"},";
        			break;
    			case "pumpBoreDiameter".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpBoreDiameter\",\"value\":\""+get_rawData.pumpBoreDiameter+"\",\"curve\":\"\"},";
        			break;
    			case "pumpSettingDepth".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"pumpSettingDepth\",\"value\":\""+get_rawData.pumpSettingDepth+"\",\"curve\":\"\"},";
        			break;
    			case "producingFluidLevel".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"producingFluidLevel\",\"value\":\""+get_rawData.producingFluidLevel+"\",\"curve\":\"\"},";
        			break;
    			case "levelCorrectValue".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"levelCorrectValue\",\"value\":\""+get_rawData.levelCorrectValue+"\",\"curve\":\"\"},";
        			break;
    			case "submergence".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"submergence\",\"value\":\""+get_rawData.submergence+"\",\"curve\":\"\"},";
        			break;
    			case "productionGasOilRatio".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"productionGasOilRatio\",\"value\":\""+get_rawData.productionGasOilRatio+"\",\"curve\":\"\"},";
        			break;
    			case "todayKWattH".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"todayKWattH\",\"value\":\""+get_rawData.todayKWattH+"\",\"curve\":\"\"},";
        			break;
    			case "todayKVarH".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"todayKVarH\",\"value\":\""+get_rawData.todayKVarH+"\",\"curve\":\"\"},";
        			break;
    			case "todayKVAH".toUpperCase():
    				dataStr+="{\"item\":\""+analysisDataList[i].header+"\",\"itemCode\":\"todayKVAH\",\"value\":\""+get_rawData.todayKVAH+"\",\"curve\":\"\"},";
        			break;
    			}
    		}
    		
    		if(stringEndWith(dataStr,",")){
        		dataStr = dataStr.substring(0, dataStr.length - 1);
    		}
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
    		var commStatus="",runStatus="";
    		if(get_rawData.commStatus==1){
    			commStatus="在线";
    			if(get_rawData.runStatus==1){
    				runStatus="运行";
    			}else{
    				runStatus="停抽";
    			}
    		}else{
    			commStatus="离线";
    			runStatus=""
    		}
    		
    		for(var i=0;i<acquisitionDataList.length;i++){
    			switch(acquisitionDataList[i].dataIndex.toUpperCase()) {
    			case "acqTime_d".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"acqTime_d\",\"value\":\""+get_rawData.acqTime_d+"\",\"curve\":\"\"},";
        			break;
    			case "commStatus".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"commStatus\",\"value\":\""+commStatus+"\",\"curve\":\"\"},";
        			break;
    			case "runStatus".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"runStatus\",\"value\":\""+runStatus+"\",\"curve\":\"\"},";
        			break;
    			case "runFrequency".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"runFrequency\",\"value\":\""+get_rawData.runFrequency+"\",\"curve\":\"\"},";
        			break;
    			case "Ia".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Ia\",\"value\":\""+get_rawData.Ia+"\",\"curve\":\"\"},";
        			break;
    			case "IaMax".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"IaMax\",\"value\":\""+get_rawData.IaMax+"\",\"curve\":\"\"},";
        			break;
    			case "IaMin".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"IaMin\",\"value\":\""+get_rawData.IaMin+"\",\"curve\":\"\"},";
        			break;
    			case "Ib".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Ib\",\"value\":\""+get_rawData.Ib+"\",\"curve\":\"\"},";
        			break;
    			case "IbMax".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"IbMax\",\"value\":\""+get_rawData.IbMax+"\",\"curve\":\"\"},";
        			break;
    			case "IbMin".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"IbMin\",\"value\":\""+get_rawData.IbMin+"\",\"curve\":\"\"},";
        			break;
    			case "Ic".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Ic\",\"value\":\""+get_rawData.Ic+"\",\"curve\":\"\"},";
        			break;
    			case "IcMax".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"IcMax\",\"value\":\""+get_rawData.IcMax+"\",\"curve\":\"\"},";
        			break;
    			case "IcMin".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"IcMin\",\"value\":\""+get_rawData.IcMin+"\",\"curve\":\"\"},";
        			break;
    			case "Va".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Va\",\"value\":\""+get_rawData.Va+"\",\"curve\":\"\"},";
        			break;
    			case "Vb".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Vb\",\"value\":\""+get_rawData.Vb+"\",\"curve\":\"\"},";
        			break;
    			case "Vc".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"Vc\",\"value\":\""+get_rawData.Vc+"\",\"curve\":\"\"},";
        			break;
    			case "watt3".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"watt3\",\"value\":\""+get_rawData.watt3+"\",\"curve\":\"\"},";
        			break;
    			case "var3".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"var3\",\"value\":\""+get_rawData.var3+"\",\"curve\":\"\"},";
        			break;
    			case "reversePower".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"reversePower\",\"value\":\""+get_rawData.reversePower+"\",\"curve\":\"\"},";
        			break;
    			case "va3".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"va3\",\"value\":\""+get_rawData.va3+"\",\"curve\":\"\"},";
        			break;
    			case "pf3".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"pf3\",\"value\":\""+get_rawData.pf3+"\",\"curve\":\"\"},";
        			break;
    			case "totalKWattH".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"totalKWattH\",\"value\":\""+get_rawData.totalKWattH+"\",\"curve\":\"\"},";
        			break;
    			case "totalKVarH".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"totalKVarH\",\"value\":\""+get_rawData.totalKVarH+"\",\"curve\":\"\"},";
        			break;
    			case "totalKVAH".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"totalKVAH\",\"value\":\""+get_rawData.totalKVAH+"\",\"curve\":\"\"},";
        			break;
    			case "KWattH".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"totalKVarH\",\"value\":\""+get_rawData.totalKVarH+"\",\"curve\":\"\"},";
        			break;
    			case "KVarH".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"totalKVAH\",\"value\":\""+get_rawData.totalKVAH+"\",\"curve\":\"\"},";
        			break;
    			case "weightWaterCut".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"weightWaterCut\",\"value\":\""+get_rawData.waterCut+"\",\"curve\":\"\"},";
        			break;
    			case "volumeWaterCut".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"volumeWaterCut\",\"value\":\""+get_rawData.waterCut+"\",\"curve\":\"\"},";
        			break;
    			case "tubingPressure".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"tubingPressure\",\"value\":\""+get_rawData.tubingPressure+"\",\"curve\":\"\"},";
        			break;
    			case "casingPressure".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"casingPressure\",\"value\":\""+get_rawData.casingPressure+"\",\"curve\":\"\"},";
        			break;
    			case "wellHeadFluidTemperature".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"wellHeadFluidTemperature\",\"value\":\""+get_rawData.wellHeadFluidTemperature+"\",\"curve\":\"\"},";
        			break;
    			case "producingFluidLevel".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"producingFluidLevel\",\"value\":\""+get_rawData.producingFluidLevel+"\",\"curve\":\"\"},";
        			break;
    			case "signal".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"signal\",\"value\":\""+get_rawData.signal+"\",\"curve\":\"\"},";
        			break;
    			case "deviceVer".toUpperCase():
    				acqSataStr+="{\"item\":\""+acquisitionDataList[i].header+"\",\"itemCode\":\"deviceVer\",\"value\":\""+get_rawData.deviceVer+"\",\"curve\":\"\"},";
        			break;
    			}
    		}
//    		acqSataStr+="{\"item\":\"油压(MPa)\",\"itemCode\":\"tubingPressure\",\"value\":\""+get_rawData.tubingPressure+"\",\"curve\":\"\"},";
//    		acqSataStr+="{\"item\":\"套压(MPa)\",\"itemCode\":\"casingPressure\",\"value\":\""+get_rawData.casingPressure+"\",\"curve\":\"\"},";
//    		acqSataStr+="{\"item\":\"井口油温(℃)\",\"itemCode\":\"wellheadFluidTemperature\",\"value\":\""+get_rawData.wellheadFluidTemperature+"\",\"curve\":\"\"},";
    		if(stringEndWith(acqSataStr,",")){
    			acqSataStr = acqSataStr.substring(0, dataStr.length - 1);
    		}
    		acqSataStr+="]}";
    		
    		var controlSataStr="{\"items\":[";
    		var isHaveBalanceControl=false;
    		for(var i=0;i<get_rawData.controlItems.length;i++){
    			switch(get_rawData.controlItems[i].item) {
    			case "ImmediatelyAcquisition":
   		    	 controlSataStr+="{\"item\":\"即时采集\",\"itemcode\":\"ImmediatelyAcquisition\",\"value\":\"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
   		    	 break; 
    			case "RunControl":
    		    	 var runStatus="停抽";
    		    	 if(get_rawData.commStatus==1){
    		    		 if(get_rawData.runStatus==1){
    		    			 runStatus="运行";
    		    		}
    		    	 }else{
    		    	 	runStatus="";
    		    	 }
    		    	 controlSataStr+="{\"item\":\"运行状态\",\"itemcode\":\"RunControl\",\"value\":\""+runStatus+"\",\"commStatus\":\""+get_rawData.commStatus+"\",\"operation\":true,\"isControl\":"+isControl+",\"showType\":0},";
    		         break;
    		     case "SetFrequency":
    		    	 controlSataStr+="{\"item\":\"变频设置频率(Hz)\",\"itemcode\":\"SetFrequency\",\"value\":\""+(get_rawData.setFrequency==undefined?"":get_rawData.setFrequency)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "FESDiagramAcquisitionInterval":
    		    	 controlSataStr+="{\"item\":\"功图数据采集间隔(min)\",\"itemcode\":\"FESDiagramAcquisitionInterval\",\"value\":\""+(get_rawData.acqcycle_diagram==undefined?"":get_rawData.acqcycle_diagram)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "FESDiagramSetPointCount":
    		    	 controlSataStr+="{\"item\":\"功图点数\",\"itemcode\":\"FESDiagramSetPointCount\",\"value\":\""+(get_rawData.FESDiagramSetPointCount==undefined?"":get_rawData.FESDiagramSetPointCount)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "DiscreteInterval":
    		    	 controlSataStr+="{\"item\":\"离散数据采集间隔(min)\",\"itemcode\":\"acqcycle_discrete\",\"value\":\""+(get_rawData.acqcycle_discrete==undefined||get_rawData.acqcycle_discrete==""?"":parseFloat(get_rawData.acqcycle_discrete).toFixed(2))+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "iUpLimit":
    		    	 controlSataStr+="{\"item\":\"电流上限(A)\",\"itemcode\":\"IaUpLimit\",\"value\":\""+(get_rawData.IaUpLimit==undefined?"":get_rawData.IaUpLimit)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "iDownLimit":
    		    	 controlSataStr+="{\"item\":\"电流下限(A)\",\"itemcode\":\"IaDownLimit\",\"value\":\""+(get_rawData.IaDownLimit==undefined?"":get_rawData.IaDownLimit)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "wattUpLimit":
    		    	 controlSataStr+="{\"item\":\"功率上限(kW)\",\"itemcode\":\"wattUpLimit\",\"value\":\""+(get_rawData.wattUpLimit==undefined?"":get_rawData.wattUpLimit)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "wattDownLimit":
    		    	 controlSataStr+="{\"item\":\"功率下限(kW)\",\"itemcode\":\"wattDownLimit\",\"value\":\""+(get_rawData.wattDownLimit==undefined?"":get_rawData.wattDownLimit)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break; 
    		     case "BalanceControlMode":
    		    	 controlSataStr+="{\"item\":\"远程调节手自动模式\",\"itemcode\":\"balanceControlMode\",\"value\":\""+(get_rawData.balanceControlMode==undefined?"":get_rawData.balanceControlMode)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":2},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    		     case "BalanceCalculateMode":
    		    	 controlSataStr+="{\"item\":\"平衡计算方式\",\"itemcode\":\"balanceCalculateMode\",\"value\":\""+(get_rawData.balanceCalculateMode==undefined?"":get_rawData.balanceCalculateMode)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":2},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    		     case "BalanceAwayTime":
    		    	 controlSataStr+="{\"item\":\"向远离驴头方向调节距离(cm)\",\"itemcode\":\"balanceAwayTime\",\"value\":\""+(get_rawData.balanceAwayTime==undefined?"":get_rawData.balanceAwayTime)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    		     case "BalanceCloseTime":
    		    	 controlSataStr+="{\"item\":\"向接近驴头方向调节距离(cm)\",\"itemcode\":\"balanceCloseTime\",\"value\":\""+(get_rawData.balanceCloseTime==undefined?"":get_rawData.balanceCloseTime)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    		     case "BalanceStrokeCount":
    		    	 controlSataStr+="{\"item\":\"参与平衡计算冲程次数\",\"itemcode\":\"balanceStrokeCount\",\"value\":\""+(get_rawData.balanceStrokeCount==undefined?"":get_rawData.balanceStrokeCount)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    		     case "BalanceOperationUpLimit":
    		    	 controlSataStr+="{\"item\":\"平衡调节上限(%)\",\"itemcode\":\"balanceOperationUpLimit\",\"value\":\""+(get_rawData.balanceOperationUpLimit==undefined?"":get_rawData.balanceOperationUpLimit)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    		     case "BalanceOperationDownLimit":
    		    	 controlSataStr+="{\"item\":\"平衡调节下限(%)\",\"itemcode\":\"balanceOperationDownLimit\",\"value\":\""+(get_rawData.balanceOperationDownLimit==undefined?"":get_rawData.balanceOperationDownLimit)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    		     case "BalanceAwayTimePerBeat":
    		    	 controlSataStr+="{\"item\":\"重心远离支点每拍调节时间(ms)\",\"itemcode\":\"balanceAwayTimePerBeat\",\"value\":\""+(get_rawData.balanceAwayTimePerBeat==undefined?"":get_rawData.balanceAwayTimePerBeat)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    		     case "BalanceCloseTimePerBeat":
    		    	 controlSataStr+="{\"item\":\"重心接近支点每拍调节时间(ms)\",\"itemcode\":\"balanceCloseTimePerBeat\",\"value\":\""+(get_rawData.balanceCloseTimePerBeat==undefined?"":get_rawData.balanceCloseTimePerBeat)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 isHaveBalanceControl=true;
    		    	 break;
    			} 
    		}
    		if(isHaveBalanceControl){
    			controlSataStr+="{\"item\":\"平衡度判别方式\",\"itemcode\":\"balanceCalculateType\",\"value\":\"电流法\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":2},";
        		controlSataStr+="{\"item\":\"平衡远程自动调节\",\"itemcode\":\"balanceAutoControl\",\"value\":\""+(get_rawData.balanceAutoControl==undefined?"":get_rawData.balanceAutoControl)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":false,\"isControl\":"+isControl+"},";
//        		controlSataStr+="{\"item\":\"冲次远程自动调节\",\"itemcode\":\"spmAutoControl\",\"value\":\""+(get_rawData.spmAutoControl==undefined?"":get_rawData.spmAutoControl)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":false,\"isControl\":"+isControl+"},";
        		controlSataStr+="{\"item\":\"平衡前限位\",\"itemcode\":\"balanceFrontLimit\",\"value\":\""+(get_rawData.balanceFrontLimit==undefined?"":get_rawData.balanceFrontLimit)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":false,\"isControl\":"+isControl+"},";
        		controlSataStr+="{\"item\":\"平衡后限位\",\"itemcode\":\"balanceAfterLimit\",\"value\":\""+(get_rawData.balanceAfterLimit==undefined?"":get_rawData.balanceAfterLimit)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":false,\"isControl\":"+isControl+"},";
    		}

    		if(stringEndWith(controlSataStr,",")){
    			controlSataStr = controlSataStr.substring(0, controlSataStr.length - 1);
    		}
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
    		
    		var GridPanel=Ext.getCmp("DiagnosisAnalysisDataGridPanel_Id");
    		if(!isNotVal(GridPanel)){
    			GridPanel=Ext.create('Ext.grid.Panel', {
    				id:'DiagnosisAnalysisDataGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: store,
    			    columns: [
    			        { 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:5,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:3,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:2,renderer :function(value,e,o){return iconDiagnoseAnalysisCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("FSDiagramAnalysisSingleDetailsRightAnalysisPanel_Id").add(GridPanel);
    		}else{
    			GridPanel.reconfigure(store);
    		}
    		
    		var acqGridPanel=Ext.getCmp("DiagnosisAcqDataGridPanel_Id");
    		if(!isNotVal(acqGridPanel)){
    			acqGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'DiagnosisAcqDataGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: acqStore,
    			    columns: [
    			    	{ 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:9,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:10,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:4,renderer :function(value,e,o){return iconDiagnoseAnalysisCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("FSDiagramAnalysisSingleDetailsRightAcqPanel_Id").add(acqGridPanel);
    		}else{
    			acqGridPanel.reconfigure(acqStore);
    		}
    		
    		var controlGridPanel=Ext.getCmp("DiagnosisControlDataGridPanel_Id");
    		if(!isNotVal(controlGridPanel)){
    			controlGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'DiagnosisControlDataGridPanel_Id',
    				requires: [
                       	'Ext.grid.selection.SpreadsheetModel',
                       	'Ext.grid.plugin.Clipboard'
                       	],
                    xtype:'spreadsheet-checked',
                    plugins: [
                        'clipboard',
                        'selectionreplicator',
                        new Ext.grid.plugin.CellEditing({
                      	  clicksToEdit:2
                        })
                    ],
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: controlStore,
    			    columns: [
    			        { 
    			        	header: '操作项',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:9,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:3,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
//    			        	editor:{allowBlank:false}
    			        },
    			        { 	header: '操作', 
    			        	dataIndex: 'operation',
    			        	align:'center',
    			        	flex:4,
    			        	renderer :function(value,e,o){
//    			        		return iconDiagnoseAnalysisCurve(value,e,o)
    			        		var id = e.record.id;
    			        		var item=o.data.item;
    			        		var itemcode=o.data.itemcode;
    			        		var commStatus = o.data.commStatus;
    			        		var isControl=o.data.isControl
    			        		var text="";
    			        		var hand=false;
    			        		var hidden=false;
    			        		if(commStatus==1&&isControl==1){
    			        			hand=false;
    			        		}else{
    			        			hand=true;
    			        		}
    			        		if(!o.data.operation){
    			        			hidden=true;
    			        		}
    			        		if(itemcode==="startOrStopWell"){
    			        			if(o.data.value=="运行"){
    			        				text="停抽";
    			        			}else if(o.data.value=="停抽" ||o.data.value=="停止"){
    			        				text="启抽";
    			        				hand=true;
    			        			}else{
    			        				text="不可用";
    			        			}
    			        		}else if(itemcode==="ImmediatelyAcquisition"){
    			        			text="即时采集";
    			        		}else{
    			        			text="设置";
    			        		}
    		                    Ext.defer(function () {
    		                        Ext.widget('button', {
    		                            renderTo: id,
    		                            height: 18,
    		                            width: 60,
    		                            text: text,
    		                            disabled:hand,
    		                            hidden:hidden,
    		                            handler: function () {
    		                            	var operaName="";
    		                            	if(text=="停抽"||text=="启抽"||text=="即时采集"||text=="即时刷新"){
    		                            		operaName="是否执行"+text+"操作";
    		                            	}else{
    		                            		operaName="是否执行"+text+item.split("(")[0]+"操作";
    		                            	}
    		                            	 Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    		                                 Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
    		                                 Ext.Msg.confirm("操作确认", operaName, function (btn) {
    		                                     if (btn == "yes") {
    		                                         var win_Obj = Ext.getCmp("WellControlCheckPassWindow_Id")
    		                                         if (win_Obj != undefined) {
    		                                             win_Obj.destroy();
    		                                         }
    		                                         var WellControlCheckPassWindow = Ext.create("AP.view.diagnosis.WellControlCheckPassWindow", {
    		                                             title: '控制'
    		                                         });
    		                                         
    		                                         
    		                                     	 var wellName  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.wellName;
    		                                     	 Ext.getCmp("ProductionWellControlWellName_Id").setValue(wellName);
    		                                         Ext.getCmp("ProductionWellControlType_Id").setValue(o.data.itemcode);
    		                                         Ext.getCmp("ProductionWellControlShowType_Id").setValue(o.data.showType);
    		                                         if(o.data.itemcode=="startOrStopWell"){
    		                                        	 if(o.data.value=="运行"){
    		                                        		 Ext.getCmp("ProductionWellControlValue_Id").setValue(2);
    		                                        	 }else if(o.data.value=="停抽" ||o.data.value=="停止"){
    		                                        		 Ext.getCmp("ProductionWellControlValue_Id").setValue(1);
    		             			        			 }
    		                                        	 Ext.getCmp("ProductionWellControlValue_Id").hide();
    		                                        	 Ext.getCmp("ProductionWellControlValueCombo_Id").hide();
    		                                         }else if(o.data.itemcode=="ImmediatelyAcquisition"){//即时采集
    		                                        	 Ext.getCmp("ProductionWellControlValue_Id").setValue(1);
    		                                        	 Ext.getCmp("ProductionWellControlValue_Id").hide();
    		                                        	 Ext.getCmp("ProductionWellControlValueCombo_Id").hide();
    		                                         }else if(o.data.itemcode=="balanceCalculateMode" || o.data.itemcode=="balanceCalculateType" || o.data.itemcode=="balanceControlMode"){
    		                                        	 Ext.getCmp("ProductionWellControlValue_Id").hide();
    		                                        	 Ext.getCmp("ProductionWellControlValueCombo_Id").setFieldLabel(o.data.item);
    		                                        	 var data=[];
    		                                        	 if(o.data.itemcode=="balanceCalculateMode"){
    		                                        		 data=[['1', '下行程最大值/上行程最大值'], ['2', '上行程最大值/下行程最大值']];
    		                                        	 }else if(o.data.itemcode=="balanceCalculateType"){
    		                                        		 data=[['1', '电流法'], ['2', '功率法']];
    		                                        	 }else if(o.data.itemcode=="balanceControlMode"){
    		                                        		 data=[['0', '手动'], ['1', '自动']];
    		                                        	 }
    		                                        	 var controlTypeStore = new Ext.data.SimpleStore({
    		                                             	autoLoad : false,
    		                                                 fields: ['boxkey', 'boxval'],
    		                                                 data: data
    		                                             });
    		                                        	 Ext.getCmp("ProductionWellControlValueCombo_Id").setStore(controlTypeStore);
    		                                        	 Ext.getCmp("ProductionWellControlValueCombo_Id").setRawValue(o.data.value);
    		                                        	 Ext.getCmp("ProductionWellControlValueCombo_Id").show();
    		                                         }else{
    		                                        	 Ext.getCmp("ProductionWellControlValue_Id").show();
    		                                        	 Ext.getCmp("ProductionWellControlValueCombo_Id").hide();
    		                                        	 Ext.getCmp("ProductionWellControlValue_Id").setFieldLabel(o.data.item);
    		                                        	 Ext.getCmp("ProductionWellControlValue_Id").setValue(o.data.value);
    		                                         }
    		                                         
    		                                         WellControlCheckPassWindow.show();
    		                                     }
    		                                 });
    		                            }
    		                        });
    		                    }, 50);
    		                    return Ext.String.format('<div id="{0}"></div>', id);
    			        	} 
    			        }
    			    ]
    			});
    			Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlPanel_Id").add(controlGridPanel);
    		}else{
    			controlGridPanel.reconfigure(controlStore);
    		}
    		
        	if(get_rawData.videourl!=undefined&&get_rawData.videourl!=""){
        		if($("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer")!=null){
            		$("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer").html('');
            	}
            	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").expand(true);
            	var videoUrl=get_rawData.videourl;
            	var videoUrl_rtmp=""; 
            	var videoUrl_hls=""; 
            	if(videoUrl.indexOf("http")>=0){//hls模式
            		videoUrl_hls=videoUrl;
            		videoUrl_rtmp=videoUrl.replace("https","http").replace("http://hls","rtmp://rtmp").replace(".m3u8","");
            	}else{
            		videoUrl_hls=videoUrl.replace("rtmp://rtmp","http://hls")+".m3u8";
            		videoUrl_rtmp=videoUrl;
            	}
            	
            	
            	var videohtml='<video id="FSDiagramAnalysisSingleDetailsRightControlVideoPlayer" style="width:100%;height:100%;"  poster="" controls playsInline webkit-playsinline autoplay><source src="'+videoUrl_rtmp+'" type="rtmp/flv" /><source src="'+videoUrl_hls+'" type="application/x-mpegURL" /></video>';   
            	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").update(videohtml);
            	if(document.getElementById("FSDiagramAnalysisSingleDetailsRightControlVideoPlayer")!=null){
            		var player = new EZUIPlayer('FSDiagramAnalysisSingleDetailsRightControlVideoPlayer');
            	}
            }else{
            	var videohtml=''
                Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").update(videohtml);
                Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").collapse();
            }
        	
    		Ext.getCmp("FSDiagramAnalysisSingleDetailsRightRunRangeTextArea_Id").setValue(get_rawData.runRange);
    		
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.id;
        	var wellName=Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
        	var selectedWellName  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var new_params = {
        			id: id,
        			wellName:wellName,
        			selectedWellName:selectedWellName
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});