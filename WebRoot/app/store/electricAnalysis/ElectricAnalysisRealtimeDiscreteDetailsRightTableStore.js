Ext.define('AP.store.electricAnalysis.ElectricAnalysisRealtimeDiscreteDetailsRightTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.electricAnalysisRealtimeDiscreteDetailsRightTableStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/PSToFSController/getRealtimeAnalysisAndAcqData',
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
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
    		acqSataStr+="{\"item\":\"A相电流(A)\",\"itemCode\":\"Ia\",\"value\":\""+get_rawData.Ia+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电流(A)\",\"itemCode\":\"Ib\",\"value\":\""+get_rawData.Ib+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电流(A)\",\"itemCode\":\"Ic\",\"value\":\""+get_rawData.Ic+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"三相平均电流(A)\",\"itemCode\":\"IAvg\",\"value\":\""+get_rawData.IAvg+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电压(V)\",\"itemCode\":\"Va\",\"value\":\""+get_rawData.Va+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电压(V)\",\"itemCode\":\"Vb\",\"value\":\""+get_rawData.Vb+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电压(V)\",\"itemCode\":\"Vc\",\"value\":\""+get_rawData.Vc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"三相平均电压(V)\",\"itemCode\":\"VAvg\",\"value\":\""+get_rawData.VAvg+"\",\"curve\":\"\"},";
    		
    		acqSataStr+="{\"item\":\"A相有功功率(kW)\",\"itemCode\":\"WattA\",\"value\":\""+get_rawData.WattA+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相有功功率(kW)\",\"itemCode\":\"WattB\",\"value\":\""+get_rawData.WattB+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相有功功率(kW)\",\"itemCode\":\"WattC\",\"value\":\""+get_rawData.WattC+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"三相总有功功率(kW)\",\"itemCode\":\"WattSum\",\"value\":\""+get_rawData.WattSum+"\",\"curve\":\"\"},";
    		
    		acqSataStr+="{\"item\":\"A相无功功率(kVar)\",\"itemCode\":\"VarA\",\"value\":\""+get_rawData.VarA+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相无功功率(kVar)\",\"itemCode\":\"VarB\",\"value\":\""+get_rawData.VarB+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相无功功率(kVar)\",\"itemCode\":\"VarC\",\"value\":\""+get_rawData.VarC+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"三相总无功功率(kVar)\",\"itemCode\":\"VarSum\",\"value\":\""+get_rawData.VarSum+"\",\"curve\":\"\"},";
    		
    		acqSataStr+="{\"item\":\"A相功率因数\",\"itemCode\":\"PFA\",\"value\":\""+get_rawData.PFA+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相功率因数\",\"itemCode\":\"PFB\",\"value\":\""+get_rawData.PFB+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相功率因数\",\"itemCode\":\"PFC\",\"value\":\""+get_rawData.PFC+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"三相综合功率因数\",\"itemCode\":\"PFSum\",\"value\":\""+get_rawData.PFSum+"\",\"curve\":\"\"},";
    		
    		acqSataStr+="{\"item\":\"有功功耗(kW·h)\",\"itemCode\":\"totalWattEnergy\",\"value\":\""+get_rawData.totalWattEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"正向有功功耗(kW·h)\",\"itemCode\":\"totalPWattEnergy\",\"value\":\""+get_rawData.totalPWattEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"反向有功功耗(kW·h)\",\"itemCode\":\"totalNWattEnergy\",\"value\":\""+get_rawData.totalNWattEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"无功功耗(kVar·h)\",\"itemCode\":\"totalVarEnergy\",\"value\":\""+get_rawData.totalVarEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"正向无功功耗(kVar·h)\",\"itemCode\":\"totalPVarEnergy\",\"value\":\""+get_rawData.totalPVarEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"反向无功功耗(kVar·h)\",\"itemCode\":\"totalNVarEnergy\",\"value\":\""+get_rawData.totalNVarEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"视在功耗(kVA·h)\",\"itemCode\":\"totalVAEnergy\",\"value\":\""+get_rawData.totalVAEnergy+"\",\"curve\":\"\"},";
    		
    		acqSataStr+="{\"item\":\"日有功功耗(kW·h)\",\"itemCode\":\"todayWattEnergy\",\"value\":\""+get_rawData.todayWattEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日正向有功功耗(kW·h)\",\"itemCode\":\"todayPWattEnergy\",\"value\":\""+get_rawData.todayPWattEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日反向有功功耗(kW·h)\",\"itemCode\":\"todayNWattEnergy\",\"value\":\""+get_rawData.todayNWattEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日无功功耗(kVar·h)\",\"itemCode\":\"todayVarEnergy\",\"value\":\""+get_rawData.todayVarEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日正向无功功耗(kVar·h)\",\"itemCode\":\"todayPVarEnergy\",\"value\":\""+get_rawData.todayPVarEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日反向无功功耗(kVar·h)\",\"itemCode\":\"todayNVarEnergy\",\"value\":\""+get_rawData.todayNVarEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"日视在功耗(kVA·h)\",\"itemCode\":\"todayVAEnergy\",\"value\":\""+get_rawData.todayVAEnergy+"\",\"curve\":\"\"},";
    		
    		
    		acqSataStr+="{\"item\":\"频率(Hz)\",\"itemCode\":\"frequencyRunValue\",\"value\":\""+get_rawData.frequencyRunValue+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"信号强度\",\"itemCode\":\"signal\",\"value\":\""+get_rawData.signal+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"传输间隔(min)\",\"itemCode\":\"interval\",\"value\":\""+get_rawData.interval+"\",\"curve\":\"\"}";
    		acqSataStr+="]}";
    		
    		
    		var storeData=Ext.JSON.decode(dataStr);
    		var acqStoreData=Ext.JSON.decode(acqSataStr);
    		
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
    		
    		var GridPanel=Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightGridPanel_Id");
    		if(!isNotVal(GridPanel)){
    			GridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ElectricAnalysisRealtimeDiscreteDetailsRightGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: store,
    			    columns: [
    			    	{ 
    			    		header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',//内容靠左
    			        	style:{
    			        		'text-align':'center'//表头居中
    			        	},
    			        	flex:3,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '数值', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconElectricAnalysisRealtimeCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightAnalysisPanel_Id").add(GridPanel);
    		}else{
    			GridPanel.reconfigure(store);
    		}
    		
    		var acqGridPanel=Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightAcqGridPanel_Id");
    		if(!isNotVal(acqGridPanel)){
    			acqGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ElectricAnalysisRealtimeDiscreteDetailsRightAcqGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: acqStore,
    			    columns: [
    			    	{ 
    			    		header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',//内容靠左
    			        	style:{
    			        		'text-align':'center'//表头居中
    			        	},
    			        	flex:3,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '数值', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:2,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconElectricAnalysisRealtimeCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightAcqPanel_Id").add(acqGridPanel);
    		}else{
    			acqGridPanel.reconfigure(acqStore);
    		}
    		Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightRunRangeTextArea_Id").setValue(get_rawData.runRange);
    		Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetailsRightResultCodeTextArea_Id").setValue(get_rawData.workingConditionString_Elec);
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetails_Id").getSelectionModel().getSelection()[0].data.id;
        	var wellName = Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').getValue();
        	var new_params = {
        			id: id,
        			wellName:wellName
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});