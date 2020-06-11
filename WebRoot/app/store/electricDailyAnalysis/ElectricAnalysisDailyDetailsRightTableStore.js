Ext.define('AP.store.electricDailyAnalysis.ElectricAnalysisDailyDetailsRightTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.electricAnalysisDailyDetailsRightTableStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/PSToFSController/getDailyAnalysisAndAcqData',
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
    		dataStr+="{\"item\":\"电流平衡度(%)\",\"itemCode\":\"iDegreeBalance\",\"value\":\""+get_rawData.iDegreeBalanceMax+"/"+get_rawData.iDegreeBalanceMin+"/"+get_rawData.iDegreeBalance+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功率平衡度(%)\",\"itemCode\":\"wattDegreeBalance\",\"value\":\""+get_rawData.wattDegreeBalanceMax+"/"+get_rawData.wattDegreeBalanceMin+"/"+get_rawData.wattDegreeBalance+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"日有功功耗(kW·h)\",\"itemCode\":\"todayWattEnergy\",\"value\":\""+get_rawData.todayWattEnergy+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"日正向有功功耗(kW·h)\",\"itemCode\":\"todayPWattEnergy\",\"value\":\""+get_rawData.todayPWattEnergy+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"日反向有功功耗(kW·h)\",\"itemCode\":\"todayNWattEnergy\",\"value\":\""+get_rawData.todayNWattEnergy+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"日无功功耗(kVar·h)\",\"itemCode\":\"todayVarEnergy\",\"value\":\""+get_rawData.todayVarEnergy+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"日正向无功功耗(kVar·h)\",\"itemCode\":\"todayPVarEnergy\",\"value\":\""+get_rawData.todayPVarEnergy+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"日反向无功功耗(kVar·h)\",\"itemCode\":\"todayNVarEnergy\",\"value\":\""+get_rawData.todayNVarEnergy+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"日视在功耗(kVA·h)\",\"itemCode\":\"todayVAEnergy\",\"value\":\""+get_rawData.todayVAEnergy+"\",\"curve\":\"\"}";
    		dataStr+="]}";
    		
    		dataStr=dataStr.replace(/\/\//g,"");
    		
    		var acqSataStr="{\"items\":[";
    		acqSataStr+="{\"item\":\"冲次(1/min)\",\"itemCode\":\"SPM\",\"value\":\""+get_rawData.SPMMax+"/"+get_rawData.SPMMin+"/"+get_rawData.SPM+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"载荷(kN)\",\"itemCode\":\"SPM\",\"value\":\""+get_rawData.FMax+"/"+get_rawData.FMin+"/"+get_rawData.F+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电流(A)\",\"itemCode\":\"Ia\",\"value\":\""+get_rawData.IaMax+"/"+get_rawData.IaMin+"/"+get_rawData.Ia+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电流(A)\",\"itemCode\":\"Ib\",\"value\":\""+get_rawData.IbMax+"/"+get_rawData.IbMin+"/"+get_rawData.Ib+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电流(A)\",\"itemCode\":\"Ic\",\"value\":\""+get_rawData.IcMax+"/"+get_rawData.IcMin+"/"+get_rawData.Ic+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电压(V)\",\"itemCode\":\"Va\",\"value\":\""+get_rawData.VaMax+"/"+get_rawData.VaMin+"/"+get_rawData.Va+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电压(V)\",\"itemCode\":\"Vb\",\"value\":\""+get_rawData.VbMax+"/"+get_rawData.VbMin+"/"+get_rawData.Vb+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电压(V)\",\"itemCode\":\"Vc\",\"value\":\""+get_rawData.VcMax+"/"+get_rawData.VcMin+"/"+get_rawData.Vc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"信号强度\",\"itemCode\":\"signal\",\"value\":\""+get_rawData.signalMax+"/"+get_rawData.signalMin+"/"+get_rawData.signal+"\",\"curve\":\"\"}";
    		acqSataStr+="]}";
    		
    		acqSataStr=acqSataStr.replace(/\/\//g,"");
    		
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
    		
    		var GridPanel=Ext.getCmp("ElectricAnalysisDailyDetailsRightGridPanel_Id");
    		if(!isNotVal(GridPanel)){
    			GridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ElectricAnalysisDailyDetailsRightGridPanel_Id',
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
    			        	flex:5,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:5,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:2,renderer :function(value,e,o){return iconElectricAnalysisDailyCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ElectricAnalysisDailyDetailsRightAnalysisPanel_Id").add(GridPanel);
    		}else{
    			GridPanel.reconfigure(store);
    		}
    		
    		var acqGridPanel=Ext.getCmp("ElectricAnalysisDailyDetailsRightAcqGridPanel_Id");
    		if(!isNotVal(acqGridPanel)){
    			acqGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ElectricAnalysisDailyDetailsRightAcqGridPanel_Id',
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
    			        	flex:2,
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
    			        { header: '曲线', dataIndex: 'curve',align:'center',flex:1,renderer :function(value,e,o){return iconElectricAnalysisDailyCurve(value,e,o)} }
    			    ]
    			});
    			Ext.getCmp("ElectricAnalysisDailyDetailsRightAcqPanel_Id").add(acqGridPanel);
    		}else{
    			acqGridPanel.reconfigure(acqStore);
    		}
    		
    		Ext.getCmp("ElectricAnalysisDailyDetailsRightRunRangeTextArea_Id").setValue(get_rawData.runRange);
    		Ext.getCmp("ElectricAnalysisDailyDetailsRightResultCodeTextArea_Id").setValue(get_rawData.workingConditionString_E);
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("ElectricAnalysisDailyDetails_Id").getSelectionModel().getSelection()[0].data.id;
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