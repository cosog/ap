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
        	
        	
        	var dataStr="{\"items\":[";
    		dataStr+="{\"item\":\"运行时间(h)\",\"itemCode\":\"runTime\",\"value\":"+get_rawData.runTime+",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"运行时率(小数)\",\"itemCode\":\"runTimeEfficiency\",\"value\":"+get_rawData.runTimeEfficiency+",\"curve\":\"\"},";
    		
    		if(productionUnit==0){
    			dataStr+="{\"item\":\"产液量(t/d)\",\"itemCode\":\"liquidWeightProduction\",\"value\":\""+get_rawData.liquidProductionMax+"/"+get_rawData.liquidProductionMin+"/"+get_rawData.liquidProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"产油量(t/d)\",\"itemCode\":\"oilWeightProduction\",\"value\":\""+get_rawData.oilProductionMax+"/"+get_rawData.oilProductionMin+"/"+get_rawData.oilProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"产水量(t/d)\",\"itemCode\":\"waterWeightProduction\",\"value\":\""+get_rawData.waterProductionMax+"/"+get_rawData.waterProductionMin+"/"+get_rawData.waterProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"含水率(%)\",\"itemCode\":\"waterCut_W\",\"value\":\""+get_rawData.waterCutMax+"/"+get_rawData.waterCutMin+"/"+get_rawData.waterCut+"\",\"curve\":\"\"},";
	        }else{
	        	dataStr+="{\"item\":\"产液量(m^3/d)\",\"itemCode\":\"liquidVolumetricProduction\",\"value\":\""+get_rawData.liquidProductionMax+"/"+get_rawData.liquidProductionMin+"/"+get_rawData.liquidProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"产油量(m^3/d)\",\"itemCode\":\"oilVolumetricProduction\",\"value\":\""+get_rawData.oilProductionMax+"/"+get_rawData.oilProductionMin+"/"+get_rawData.oilProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"产水量(m^3/d)\",\"itemCode\":\"waterVolumetricProduction\",\"value\":\""+get_rawData.waterProductionMax+"/"+get_rawData.waterProductionMin+"/"+get_rawData.waterProduction+"\",\"curve\":\"\"},";
        		dataStr+="{\"item\":\"含水率(%)\",\"itemCode\":\"waterCut\",\"value\":\""+get_rawData.waterCutMax+"/"+get_rawData.waterCutMin+"/"+get_rawData.waterCut+"\",\"curve\":\"\"},";
	        }
    		
    		dataStr+="{\"item\":\"总泵效(%)\",\"itemCode\":\"pumpEff\",\"value\":\""+get_rawData.pumpEffMax+"/"+get_rawData.pumpEffMin+"/"+get_rawData.pumpEff+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"系统效率(%)\",\"itemCode\":\"systemEfficiency\",\"value\":\""+get_rawData.systemEfficiencyMax+"/"+get_rawData.systemEfficiencyMin+"/"+get_rawData.systemEfficiency+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"动液面(m)\",\"itemCode\":\"producingFluidLevel\",\"value\":\""+get_rawData.producingFluidLevelMax+"/"+get_rawData.producingFluidLevelMin+"/"+get_rawData.producingFluidLevel+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵挂(m)\",\"itemCode\":\"pumpSettingDepth\",\"value\":\""+get_rawData.pumpSettingDepthMax+"/"+get_rawData.pumpSettingDepthMin+"/"+get_rawData.pumpSettingDepth+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"沉没度(m)\",\"itemCode\":\"submergence\",\"value\":\""+get_rawData.submergenceMax+"/"+get_rawData.submergenceMin+"/"+get_rawData.submergence+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"生产气油比(m^3/t)\",\"itemCode\":\"productionGasOilRatio\",\"value\":\""+get_rawData.productionGasOilRatioMax+"/"+get_rawData.productionGasOilRatioMin+"/"+get_rawData.productionGasOilRatio+"\",\"curve\":\"\"}";
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
    		acqSataStr+="{\"item\":\"转速(r/min)\",\"itemCode\":\"rpm\",\"value\":\""+get_rawData.rpm+"/"+get_rawData.rpmMax+"/"+get_rawData.rpMmin+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电流(A)\",\"itemCode\":\"Ia\",\"value\":\""+get_rawData.IaMax+"/"+get_rawData.IaMin+"/"+get_rawData.Ia+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电流(A)\",\"itemCode\":\"Ib\",\"value\":\""+get_rawData.IbMax+"/"+get_rawData.IbMin+"/"+get_rawData.Ib+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电流(A)\",\"itemCode\":\"Ic\",\"value\":\""+get_rawData.IcMax+"/"+get_rawData.IcMin+"/"+get_rawData.Ic+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电压(V)\",\"itemCode\":\"Va\",\"value\":\""+get_rawData.VaMax+"/"+get_rawData.VaMin+"/"+get_rawData.Va+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电压(V)\",\"itemCode\":\"Vb\",\"value\":\""+get_rawData.VbMax+"/"+get_rawData.VbMin+"/"+get_rawData.Vb+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电压(V)\",\"itemCode\":\"Vc\",\"value\":\""+get_rawData.VcMax+"/"+get_rawData.VcMin+"/"+get_rawData.Vc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日用电量(kW·h)\",\"itemCode\":\"todayWattEnergy\",\"value\":\""+get_rawData.todayWattEnergy+"\",\"curve\":\"\"},";
//    		acqSataStr+="{\"item\":\"有功功率(kW)\",\"itemCode\":\"wattSum\",\"value\":\""+get_rawData.wattSumMax+"/"+get_rawData.wattSumMin+"/"+get_rawData.wattSum+"\",\"curve\":\"\"},";
//    		acqSataStr+="{\"item\":\"无功功率(kVar)\",\"itemCode\":\"varSum\",\"value\":\""+get_rawData.varSumMax+"/"+get_rawData.varSumMin+"/"+get_rawData.varSum+"\",\"curve\":\"\"},";
//    		acqSataStr+="{\"item\":\"功率因数\",\"itemCode\":\"PFSum\",\"value\":\""+get_rawData.PFSumMax+"/"+get_rawData.PFSumMin+"/"+get_rawData.PFSum+"\",\"curve\":\"\"}";
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