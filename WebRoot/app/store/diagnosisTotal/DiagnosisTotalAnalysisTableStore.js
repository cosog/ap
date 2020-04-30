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
        	
        	var dataStr="{\"items\":[";
    		dataStr+="{\"item\":\"运行时间(h)\",\"itemCode\":\"runTime\",\"value\":"+get_rawData.runTime+",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"运行时率(小数)\",\"itemCode\":\"runTimeEfficiency\",\"value\":"+get_rawData.runTimeEfficiency+",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"电流平衡度(%)\",\"itemCode\":\"iDegreeBalance\",\"value\":\""+get_rawData.iDegreeBalanceMax+"/"+get_rawData.iDegreeBalanceMin+"/"+get_rawData.iDegreeBalance+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功率平衡度(%)\",\"itemCode\":\"wattDegreeBalance\",\"value\":\""+get_rawData.wattDegreeBalanceMax+"/"+get_rawData.wattDegreeBalanceMin+"/"+get_rawData.wattDegreeBalance+"\",\"curve\":\"\"},";
    		
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
    		
    		dataStr+="{\"item\":\"功图冲程(m)\",\"itemCode\":\"stroke\",\"value\":\""+get_rawData.strokeMax+"/"+get_rawData.strokeMin+"/"+get_rawData.stroke+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功图冲次(1/min)\",\"itemCode\":\"SPM\",\"value\":\""+get_rawData.SPMMax+"/"+get_rawData.SPMMin+"/"+get_rawData.SPM+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"功图充满系数\",\"itemCode\":\"fullnesscoEfficient\",\"value\":\""+get_rawData.fullnesscoEfficientMax+"/"+get_rawData.fullnesscoEfficientMin+"/"+get_rawData.fullnesscoEfficient+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"总泵效(%)\",\"itemCode\":\"pumpEff\",\"value\":\""+get_rawData.pumpEffMax+"/"+get_rawData.pumpEffMin+"/"+get_rawData.pumpEff+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"地面效率(%)\",\"itemCode\":\"surfaceSystemEfficiency\",\"value\":\""+get_rawData.surfaceSystemEfficiencyMax+"/"+get_rawData.surfaceSystemEfficiencyMin+"/"+get_rawData.surfaceSystemEfficiency+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"井下效率(%)\",\"itemCode\":\"welldownSystemEfficiency\",\"value\":\""+get_rawData.welldownSystemEfficiencyMax+"/"+get_rawData.welldownSystemEfficiencyMin+"/"+get_rawData.welldownSystemEfficiency+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"系统效率(%)\",\"itemCode\":\"systemEfficiency\",\"value\":\""+get_rawData.systemEfficiencyMax+"/"+get_rawData.systemEfficiencyMin+"/"+get_rawData.systemEfficiency+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"动液面(m)\",\"itemCode\":\"producingFluidLevel\",\"value\":\""+get_rawData.producingFluidLevelMax+"/"+get_rawData.producingFluidLevelMin+"/"+get_rawData.producingFluidLevel+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵挂(m)\",\"itemCode\":\"pumpSettingDepth\",\"value\":\""+get_rawData.pumpSettingDepthMax+"/"+get_rawData.pumpSettingDepthMin+"/"+get_rawData.pumpSettingDepth+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"沉没度(m)\",\"itemCode\":\"submergence\",\"value\":\""+get_rawData.submergenceMax+"/"+get_rawData.submergenceMin+"/"+get_rawData.submergence+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"生产气油比(m^3/t)\",\"itemCode\":\"productionGasOilRatio\",\"value\":\""+get_rawData.productionGasOilRatioMax+"/"+get_rawData.productionGasOilRatioMin+"/"+get_rawData.productionGasOilRatio+"\",\"curve\":\"\"}";
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
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
    			        	header: '值', 
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
    			        	header: '值', 
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