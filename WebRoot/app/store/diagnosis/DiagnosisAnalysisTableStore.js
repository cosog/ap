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
    		var dataStr="{\"items\":[";
    		dataStr+="{\"item\":\"功率平衡度(%)\",\"itemCode\":\"wattDegreeBalance\",\"value\":\""+get_rawData.wattDegreeBalance+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"电流平衡度(%)\",\"itemCode\":\"iDegreeBalance\",\"value\":\""+get_rawData.iDegreeBalance+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"曲柄平衡移动距离(cm)\",\"itemCode\":\"deltaRadius\",\"value\":\""+get_rawData.deltaRadius+"\",\"curve\":\"\"},";
    		if(productionUnit==0){
    			dataStr+="{\"item\":\"产液量(t/d)\",\"itemCode\":\"liquidWeightProduction\",\"value\":\""+get_rawData.liquidProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"产油量(t/d)\",\"itemCode\":\"oilWeightProduction\",\"value\":\""+get_rawData.oilProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"含水率(%)\",\"itemCode\":\"waterCut_W\",\"value\":\""+get_rawData.waterCut+"\",\"curve\":\"\"},";
	        }else{
	        	dataStr+="{\"item\":\"产液量(m^3/d)\",\"itemCode\":\"liquidVolumetricProduction\",\"value\":\""+get_rawData.liquidProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"产油量(m^3/d)\",\"itemCode\":\"oilVolumetricProduction\",\"value\":\""+get_rawData.oilProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"含水率(%)\",\"itemCode\":\"waterCut\",\"value\":\""+get_rawData.waterCut+"\",\"curve\":\"\"},";
	        }
    		
    		dataStr+="{\"item\":\"理论排量(m^3/d)\",\"itemCode\":\"theoreticalProduction\",\"value\":\""+get_rawData.theoreticalProduction+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"柱塞冲程(m)\",\"itemCode\":\"plungerStroke\",\"value\":\""+get_rawData.plungerStroke+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"柱塞有效冲程(m)\",\"itemCode\":\"availablePlungerStroke\",\"value\":\""+get_rawData.availablePlungerStroke+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"冲程损失系数(%)\",\"itemCode\":\"pumpEff1\",\"value\":\""+get_rawData.pumpEff1+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功图充满系数(%)\",\"itemCode\":\"pumpEff2\",\"value\":\""+get_rawData.pumpEff2+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"漏失系数(%)\",\"itemCode\":\"pumpEff3\",\"value\":\""+get_rawData.pumpEff3+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"液体收缩系数(%)\",\"itemCode\":\"pumpEff4\",\"value\":\""+get_rawData.pumpEff4+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"总泵效(%)\",\"itemCode\":\"pumpEff\",\"value\":\""+get_rawData.pumpEff+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"抽油杆伸缩量(m)\",\"itemCode\":\"rodFlexLength\",\"value\":\""+get_rawData.rodFlexLength+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"油管伸缩量(m)\",\"itemCode\":\"tubingFlexLength\",\"value\":\""+get_rawData.tubingFlexLength+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"惯性增量(m)\",\"itemCode\":\"inertiaLength\",\"value\":\""+get_rawData.inertiaLength+"\",\"curve\":\"\"},";
    		
    		if(productionUnit==0){
    			dataStr+="{\"item\":\"有效冲程计算产量(t/d)\",\"itemCode\":\"availablePlungerstrokeProd_W\",\"value\":\""+get_rawData.availablePlungerstrokeProd+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"间隙漏失量(t/d)\",\"itemCode\":\"pumpClearanceLeakProd_W\",\"value\":\""+get_rawData.pumpClearanceLeakProd+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"游动凡尔漏失量(t/d)\",\"itemCode\":\"tvleakWeightProduction\",\"value\":\""+get_rawData.tvleakProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"固定凡尔漏失量(t/d)\",\"itemCode\":\"svleakWeightProduction\",\"value\":\""+get_rawData.svleakProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"气影响(t/d)\",\"itemCode\":\"gasInfluenceProd_W\",\"value\":\""+get_rawData.gasInfluenceProd+"\",\"curve\":\"\"},";
	        }else{
	        	dataStr+="{\"item\":\"有效冲程计算产量(m^3/d)\",\"itemCode\":\"availablePlungerstrokeProd_V\",\"value\":\""+get_rawData.availablePlungerstrokeProd+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"间隙漏失量(m^3/d)\",\"itemCode\":\"pumpClearanceLeakProd_V\",\"value\":\""+get_rawData.pumpClearanceLeakProd+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"游动凡尔漏失量(m^3/d)\",\"itemCode\":\"tvleakVolumetricProduction\",\"value\":\""+get_rawData.tvleakProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"固定凡尔漏失量(m^3/d)\",\"itemCode\":\"svleakVolumetricProduction\",\"value\":\""+get_rawData.svleakProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"气影响(m^3/d)\",\"itemCode\":\"gasInfluenceProd_V\",\"value\":\""+get_rawData.gasInfluenceProd+"\",\"curve\":\"\"},";
	        }
    		dataStr+="{\"item\":\"泵径(mm)\",\"itemCode\":\"pumpBoreDiameter\",\"value\":\""+get_rawData.pumpBoreDiameter+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵挂(m)\",\"itemCode\":\"pumpSettingDepth\",\"value\":\""+get_rawData.pumpSettingDepth+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"动液面(m)\",\"itemCode\":\"producingFluidLevel\",\"value\":\""+get_rawData.producingFluidLevel+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"沉没度(m)\",\"itemCode\":\"submergence\",\"value\":\""+get_rawData.submergence+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功图冲程(m)\",\"itemCode\":\"stroke\",\"value\":\""+get_rawData.stroke+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功图冲次(1/min)\",\"itemCode\":\"spm\",\"value\":\""+get_rawData.spm+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"上载荷(kN)\",\"itemCode\":\"upperLoadLineOfExact\",\"value\":\""+get_rawData.upperLoadLineOfExact+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"有功功率(kW)\",\"itemCode\":\"motorInputActivePower\",\"value\":\""+get_rawData.motorInputActivePower+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"光杆功率(kW)\",\"itemCode\":\"polishrodPower\",\"value\":\""+get_rawData.polishrodPower+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"水功率(kW)\",\"itemCode\":\"waterPower\",\"value\":\""+get_rawData.waterPower+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"地面效率(%)\",\"itemCode\":\"surfaceSystemEfficiency\",\"value\":\""+get_rawData.surfaceSystemEfficiency+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"井下效率(%)\",\"itemCode\":\"welldownSystemEfficiency\",\"value\":\""+get_rawData.welldownSystemEfficiency+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"系统效率(%)\",\"itemCode\":\"systemEfficiency\",\"value\":\""+get_rawData.systemEfficiency+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"吨液百米耗电量(kW·h/100m·t)\",\"itemCode\":\"powerConsumptionPerthm\",\"value\":\""+get_rawData.powerConsumptionPerthm+"\",\"curve\":\"\"},";
    		
    		
    		
    		dataStr+="{\"item\":\"泵入口压力(MPa)\",\"itemCode\":\"pumpintakep\",\"value\":\""+get_rawData.pumpintakep+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵入口温度(℃)\",\"itemCode\":\"pumpintaket\",\"value\":\""+get_rawData.pumpintaket+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵入口就地气液比(m^3/m^3)\",\"itemCode\":\"pumpintakegol\",\"value\":\""+get_rawData.pumpintakegol+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵入口液体粘度(mPa·s)\",\"itemCode\":\"pumpinletvisl\",\"value\":\""+get_rawData.pumpinletvisl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵入口原油体积系数\",\"itemCode\":\"pumpinletbo\",\"value\":\""+get_rawData.pumpinletbo+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"泵出口压力(MPa)\",\"itemCode\":\"pumpoutletp\",\"value\":\""+get_rawData.pumpoutletp+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵出口温度(℃)\",\"itemCode\":\"pumpoutlett\",\"value\":\""+get_rawData.pumpoutlett+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵出口就地气液比(m^3/m^3)\",\"itemCode\":\"pumpOutletGol\",\"value\":\""+get_rawData.pumpOutletGol+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵出口液体粘度(mPa·s)\",\"itemCode\":\"pumpoutletvisl\",\"value\":\""+get_rawData.pumpoutletvisl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵出口原油体积系数\",\"itemCode\":\"pumpoutletbo\",\"value\":\""+get_rawData.pumpoutletbo+"\",\"curve\":\"\"}";
    		
//    		dataStr+="{\"item\":\"一级杆最大载荷(kN)\",\"itemCode\":\"yjzdzh\",\"value\":\""+get_rawData.yjzdzh+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"一级杆最小载荷(kN)\",\"itemCode\":\"yjzxzh\",\"value\":\""+get_rawData.yjzxzh+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"一级杆最大应力(MPa)\",\"itemCode\":\"yjzdyl\",\"value\":\""+get_rawData.yjzdyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"一级杆最小应力(MPa)\",\"itemCode\":\"yjzxyl\",\"value\":\""+get_rawData.yjzxyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"一级杆许用应力(MPa)\",\"itemCode\":\"yjxyyl\",\"value\":\""+get_rawData.yjxyyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"一级杆应力百分比(%)\",\"itemCode\":\"yjylbfb\",\"value\":\""+get_rawData.yjylbfb+"\",\"curve\":\"\"},";
//    		
//    		dataStr+="{\"item\":\"二级杆最大载荷(kN)\",\"itemCode\":\"ejzdzh\",\"value\":\""+get_rawData.ejzdzh+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"二级杆最小载荷(kN)\",\"itemCode\":\"ejzxzh\",\"value\":\""+get_rawData.ejzxzh+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"二级杆最大应力(MPa)\",\"itemCode\":\"ejzdyl\",\"value\":\""+get_rawData.ejzdyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"二级杆最小应力(MPa)\",\"itemCode\":\"ejzxyl\",\"value\":\""+get_rawData.ejzxyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"二级杆许用应力(MPa)\",\"itemCode\":\"ejxyyl\",\"value\":\""+get_rawData.ejxyyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"二级杆应力百分比(%)\",\"itemCode\":\"ejylbfb\",\"value\":\""+get_rawData.ejylbfb+"\",\"curve\":\"\"},";
//    		
//    		dataStr+="{\"item\":\"三级杆最大载荷(kN)\",\"itemCode\":\"sjzdzh\",\"value\":\""+get_rawData.sjzdzh+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"三级杆最小载荷(kN)\",\"itemCode\":\"sjzxzh\",\"value\":\""+get_rawData.sjzxzh+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"三级杆最大应力(MPa)\",\"itemCode\":\"sjzdyl\",\"value\":\""+get_rawData.sjzdyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"三级杆最小应力(MPa)\",\"itemCode\":\"sjzxyl\",\"value\":\""+get_rawData.sjzxyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"三级杆许用应力(MPa)\",\"itemCode\":\"sjxyyl\",\"value\":\""+get_rawData.sjxyyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"三级杆应力百分比(%)\",\"itemCode\":\"sjylbfb\",\"value\":\""+get_rawData.sjylbfb+"\",\"curve\":\"\"},";
//    		
//    		dataStr+="{\"item\":\"四级杆最大载荷(kN)\",\"itemCode\":\"sijzdzh\",\"value\":\""+get_rawData.sijzdzh+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"四级杆最小载荷(kN)\",\"itemCode\":\"sijzxzh\",\"value\":\""+get_rawData.sijzxzh+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"四级杆最大应力(MPa)\",\"itemCode\":\"sijzdyl\",\"value\":\""+get_rawData.sijzdyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"四级杆最小应力(MPa)\",\"itemCode\":\"sijzxyl\",\"value\":\""+get_rawData.sijzxyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"四级杆许用应力(MPa)\",\"itemCode\":\"sijxyyl\",\"value\":\""+get_rawData.sijxyyl+"\",\"curve\":\"\"},";
//    		dataStr+="{\"item\":\"四级杆应力百分比(%)\",\"itemCode\":\"sijylbfb\",\"value\":\""+get_rawData.sijylbfb+"\",\"curve\":\"\"}";
    		
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
    		var commStatus="",runStatus="";
    		if(get_rawData.commStatus==1){
    			commStatus="在线";
    			if(get_rawData.runStatus==1){
    				runStatus="运行";
    			}else{
    				runStatus="停止";
    			}
    		}else{
    			commStatus="离线";
    			runStatus=""
    		}
    		acqSataStr+="{\"item\":\"采集时间:"+get_rawData.acquisitionTime_d+"\",\"itemCode\":\"acquisitionTime_d\",\"value\":\"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"通信状态\",\"itemCode\":\"commStatus\",\"value\":\""+commStatus+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"运行状态\",\"itemCode\":\"runStatus\",\"value\":\""+runStatus+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"运行频率(Hz)\",\"itemCode\":\"frequencyRunValue\",\"value\":\""+get_rawData.frequencyRunValue+"\",\"curve\":\"\"},";
//    		acqSataStr+="{\"item\":\"油压(MPa)\",\"itemCode\":\"tubingPressure\",\"value\":\""+get_rawData.tubingPressure+"\",\"curve\":\"\"},";
//    		acqSataStr+="{\"item\":\"套压(MPa)\",\"itemCode\":\"casingPressure\",\"value\":\""+get_rawData.casingPressure+"\",\"curve\":\"\"},";
//    		acqSataStr+="{\"item\":\"井口油温(℃)\",\"itemCode\":\"wellheadFluidTemperature\",\"value\":\""+get_rawData.wellheadFluidTemperature+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电流(A)\",\"itemCode\":\"Ia\",\"value\":\""+get_rawData.Ia+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电流最大值(A)\",\"itemCode\":\"IaMax\",\"value\":\""+get_rawData.IaMax+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电流最小值(A)\",\"itemCode\":\"IaMin\",\"value\":\""+get_rawData.IaMin+"\",\"curve\":\"\"},";
    		
    		acqSataStr+="{\"item\":\"B相电流(A)\",\"itemCode\":\"Ib\",\"value\":\""+get_rawData.Ib+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电流最大值(A)\",\"itemCode\":\"IbMax\",\"value\":\""+get_rawData.IbMax+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电流最小值(A)\",\"itemCode\":\"IbMin\",\"value\":\""+get_rawData.IbMin+"\",\"curve\":\"\"},";
    		
    		acqSataStr+="{\"item\":\"C相电流(A)\",\"itemCode\":\"Ic\",\"value\":\""+get_rawData.Ic+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电流最大值(A)\",\"itemCode\":\"IcMax\",\"value\":\""+get_rawData.IcMax+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电流最小值(A)\",\"itemCode\":\"IcMin\",\"value\":\""+get_rawData.IcMin+"\",\"curve\":\"\"},";
    		
    		acqSataStr+="{\"item\":\"A相电压(V)\",\"itemCode\":\"Va\",\"value\":\""+get_rawData.Va+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电压(V)\",\"itemCode\":\"Vb\",\"value\":\""+get_rawData.Vb+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电压(V)\",\"itemCode\":\"Vc\",\"value\":\""+get_rawData.Vc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"有功功耗(kW·h)\",\"itemCode\":\"totalWattEnergy\",\"value\":\""+get_rawData.totalWattEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"无功功耗(kVar·h)\",\"itemCode\":\"totalVarEnergy\",\"value\":\""+get_rawData.totalVarEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"有功功率(kW)\",\"itemCode\":\"wattSum\",\"value\":\""+get_rawData.wattSum+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"无功功率(kVar)\",\"itemCode\":\"varSum\",\"value\":\""+get_rawData.varSum+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"反向功率(kW)\",\"itemCode\":\"reversePower\",\"value\":\""+get_rawData.reversePower+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"功率因数\",\"itemCode\":\"pfSum\",\"value\":\""+get_rawData.pfSum+"\",\"curve\":\"\"}";
    		acqSataStr+="]}";
    		
    		var controlSataStr="{\"items\":[";
    		var isHaveBalanceControl=false;
    		for(var i=0;i<get_rawData.controlItems.length;i++){
    			switch(get_rawData.controlItems[i].tiem) {
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
    		    	 controlSataStr+="{\"item\":\"运行状态\",\"itemcode\":\"startOrStopWell\",\"value\":\""+runStatus+"\",\"commStatus\":\""+get_rawData.commStatus+"\",\"operation\":true,\"isControl\":"+isControl+",\"showType\":0},";
    		         break;
    		     case "SetFrequency":
    		    	 controlSataStr+="{\"item\":\"变频设置频率(Hz)\",\"itemcode\":\"frequencySetValue\",\"value\":\""+(get_rawData.frequencySetValue==undefined?"":get_rawData.frequencySetValue)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "FSDiagramInterval":
    		    	 controlSataStr+="{\"item\":\"功图数据采集间隔(min)\",\"itemcode\":\"acqcycle_diagram\",\"value\":\""+(get_rawData.acqcycle_diagram==undefined?"":get_rawData.acqcycle_diagram)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
    		    	 break;
    		     case "DiscreteInterval":
    		    	 controlSataStr+="{\"item\":\"离散数据采集间隔(min)\",\"itemcode\":\"acqcycle_discrete\",\"value\":\""+(get_rawData.acqcycle_discrete==undefined?"":get_rawData.acqcycle_discrete)+"\",\"commStatus\":"+get_rawData.commStatus+",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1},";
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
    			        	flex:3,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '值', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:1,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconDiagnoseAnalysisCurve(value,e,o)} }
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
    			        	flex:3,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '值', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:1,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconDiagnoseAnalysisCurve(value,e,o)} }
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
    			        	flex:9
    			        },
    			        { 
    			        	header: '状态/值', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:3
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