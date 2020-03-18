Ext.define('AP.store.diagnosis.ScrewPumpRTAnalysisTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.screwPumpRTAnalysisTableStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisAnalysisOnlyController/getPCPAnalysisAndAcqAndControlData',
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
    		dataStr+="{\"item\":\"产液量(t/d)\",\"itemCode\":\"liquidWeightProduction\",\"value\":\""+get_rawData.liquidWeightProduction+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"产油量(t/d)\",\"itemCode\":\"oilWeightProduction\",\"value\":\""+get_rawData.oilWeightProduction+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"含水率(%)\",\"itemCode\":\"waterCut\",\"value\":\""+get_rawData.waterCut+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"公称排量(ml/r)\",\"itemCode\":\"qpr\",\"value\":\""+get_rawData.qpr+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"有功功率(kW)\",\"itemCode\":\"motorInputActivePower\",\"value\":\""+get_rawData.motorInputActivePower+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"水功率(kW)\",\"itemCode\":\"waterPower\",\"value\":\""+get_rawData.waterPower+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"系统效率(%)\",\"itemCode\":\"systemEfficiency\",\"value\":\""+get_rawData.systemEfficiency+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"吨液百米耗电量(kW·h/100m·t)\",\"itemCode\":\"powerConsumptionPerthm\",\"value\":\""+get_rawData.powerConsumptionPerthm+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"容积效率(%)\",\"itemCode\":\"pumpEff1\",\"value\":\""+get_rawData.pumpEff1+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"液体收缩系数(%)\",\"itemCode\":\"pumpEff2\",\"value\":\""+get_rawData.pumpEff2+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"总泵效(%)\",\"itemCode\":\"pumpEff\",\"value\":\""+get_rawData.pumpEff+"\",\"curve\":\"\"},";
    		
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
    		acqSataStr+="{\"item\":\"油压(MPa)\",\"itemCode\":\"tubingPressure\",\"value\":\""+get_rawData.tubingPressure+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"套压(MPa)\",\"itemCode\":\"casingPressure\",\"value\":\""+get_rawData.casingPressure+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"井口油温(℃)\",\"itemCode\":\"wellheadFluidTemperature\",\"value\":\""+get_rawData.wellheadFluidTemperature+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电流(A)\",\"itemCode\":\"Ia\",\"value\":\""+get_rawData.Ia+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电流(A)\",\"itemCode\":\"Ib\",\"value\":\""+get_rawData.Ib+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电流(A)\",\"itemCode\":\"Ic\",\"value\":\""+get_rawData.Ic+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电压(V)\",\"itemCode\":\"Va\",\"value\":\""+get_rawData.Va+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电压(V)\",\"itemCode\":\"Vb\",\"value\":\""+get_rawData.Vb+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电压(V)\",\"itemCode\":\"Vc\",\"value\":\""+get_rawData.Vc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"有功功耗(kW·h)\",\"itemCode\":\"totalWattEnergy\",\"value\":\""+get_rawData.totalWattEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"无功功耗(kVar·h)\",\"itemCode\":\"totalVarEnergy\",\"value\":\""+get_rawData.totalVarEnergy+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"有功功率(kW)\",\"itemCode\":\"wattSum\",\"value\":\""+get_rawData.wattSum+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"无功功率(kVar)\",\"itemCode\":\"varSum\",\"value\":\""+get_rawData.varSum+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"反向功率(kW)\",\"itemCode\":\"reversePower\",\"value\":\""+get_rawData.reversePower+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"功率因数\",\"itemCode\":\"pfSum\",\"value\":\""+get_rawData.pfSum+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"变频运行频率(Hz)\",\"itemCode\":\"frequencyRunValue\",\"value\":\""+get_rawData.frequencyRunValue+"\",\"curve\":\"\"}";
    		acqSataStr+="]}";
    		
var controlSataStr="{\"items\":[";
    		
    		for(var i=0;i<get_rawData.controlItems.length;i++){
    			switch(get_rawData.controlItems[i].tiem) {
    		     case "RunControl":
    		    	 var runStatus="停抽";
    		    	 if(get_rawData.commStatus==1){
    		    		 if(get_rawData.runStatus==1){
    		    			 runStatus="运行";
    		    		}
    		    	 }else{
    		    	 	runStatus="";
    		    	 }
    		    	 controlSataStr+="{\"item\":\"启/停抽\",\"itemcode\":\"startOrStopWell\",\"value\":\""+runStatus+"\",\"commStatus\":\""+get_rawData.commStatus+"\",\"operation\":true,\"isControl\":"+isControl+"},";
    		         break;
    		     case "SetFrequency":
    		    	 controlSataStr+="{\"item\":\"变频设置频率(Hz)\",\"itemcode\":\"frequencySetValue\",\"value\":\""+(get_rawData.frequencySetValue==undefined?"":get_rawData.frequencySetValue)+"\",\"commStatus\":\""+get_rawData.commStatus+"\",\"operation\":true,\"isControl\":"+isControl+"},";
    		    	 break;
    			} 
    		}if(stringEndWith(controlSataStr,",")){
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
    		
    		var GridPanel=Ext.getCmp("ScrewPumpRTCalDataGridPanel_Id");
    		if(!isNotVal(GridPanel)){
    			GridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ScrewPumpRTCalDataGridPanel_Id',
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
    			Ext.getCmp("ScrewPumpRTAnalysisTableCalDataPanel_Id").add(GridPanel);
    		}else{
    			GridPanel.reconfigure(store);
    		}
    		
    		var acqGridPanel=Ext.getCmp("ScrewPumpRTAcqDataGridPanel_Id");
    		if(!isNotVal(acqGridPanel)){
    			acqGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ScrewPumpRTAcqDataGridPanel_Id',
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
    			Ext.getCmp("ScrewPumpRTAnalysisTableAcqDataPanel_Id").add(acqGridPanel);
    		}else{
    			acqGridPanel.reconfigure(acqStore);
    		}
    		
    		var controlGridPanel=Ext.getCmp("ScrewPumpRTControlDataGridPanel_Id");
    		if(!isNotVal(controlGridPanel)){
    			controlGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'ScrewPumpRTControlDataGridPanel_Id',
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
    			        { header: '操作项',  dataIndex: 'item',align:'left',flex:3},
    			        { header: '状态/值', dataIndex: 'value',align:'center',flex:1,editor:{allowBlank:false}},
    			        { 	header: '操作', 
    			        	dataIndex: 'operation',
    			        	align:'center',
    			        	flex:1,
    			        	renderer :function(value,e,o){
    			        		var id = e.record.id;
    			        		var item=o.data.item;
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
    			        		if(item=="启/停抽"){
    			        			if(o.data.value=="运行"){
    			        				text="停抽";
    			        			}else if(o.data.value=="停抽" ||o.data.value=="停止"){
    			        				text="启抽";
    			        			}else{
    			        				text="不可用";
    			        			}
    			        		}else{
    			        			text="设置";
    			        		}
    		                    Ext.defer(function () {
    		                        Ext.widget('button', {
    		                            renderTo: id,
    		                            height: 18,
    		                            width: 50,
    		                            text: text,
    		                            disabled:hand,
    		                            hidden:hidden,
    		                            handler: function () {
    		                            	var operaName="";
    		                            	if(text=="停抽"||text=="启抽"){
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
    		                                             title: '停井'
    		                                         });
    		                                         WellControlCheckPassWindow.show();
    		                                         
     		                                     	 var wellName  = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.wellName;
     		                                     	 Ext.getCmp("ProductionWellControlWellName_Id").setValue(wellName);
    		                                         Ext.getCmp("ProductionWellControlType_Id").setValue(o.data.itemcode);
    		                                         if(o.data.itemcode=="startOrStopWell"){
    		                                        	 if(o.data.value=="运行"){
    		                                        		 Ext.getCmp("ProductionWellControlValue_Id").setValue(2);
    		                                        	 }else if(o.data.value=="停抽" ||o.data.value=="停止"){
    		                                        		 Ext.getCmp("ProductionWellControlValue_Id").setValue(1);
    		             			        			 }
    		                                         }else{
    		                                        	 Ext.getCmp("ProductionWellControlValue_Id").setValue(o.data.value);
    		                                         }
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
    			Ext.getCmp("ScrewPumpRTAnalysisControlDataPanel_Id").add(controlGridPanel);
    		}else{
    			controlGridPanel.reconfigure(controlStore);
    		}
        	if(get_rawData.videourl!=undefined&&get_rawData.videourl!=""){
        		if($("#ScrewPumpRTAnalysisControlVideoDiv_Id")!=null){
            		$("#ScrewPumpRTAnalysisControlVideoDiv_Id").html('');
            	}
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
        		
        		
            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").expand(true);
            	var videohtml='<video id="ScrewPumpRTAnalysisControlVideoDiv_Id" style="width:100%;height:100%;"  poster="" controls playsInline webkit-playsinline autoplay><source src="'+videoUrl_rtmp+'" type="rtmp/flv" /><source src="'+videoUrl_hls+'" type="application/x-mpegURL" /></video>';   
            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").update(videohtml);
            	if(document.getElementById("ScrewPumpRTAnalysisControlVideoDiv_Id")!=null){
            		var player = new EZUIPlayer('ScrewPumpRTAnalysisControlVideoDiv_Id');
            	}
            }else{
            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").update('');
            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").collapse();
            }
    		
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.id;
        	var wellName=Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
        	var selectedWellName  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var new_params = {
        			id:id,
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