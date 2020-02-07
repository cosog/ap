Ext.define('AP.store.diagnosis.ScrewPumpRTAnalysisTableStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.screwPumpRTAnalysisTableStore',
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
    		dataStr+="{\"item\":\"产液量(t/d)\",\"itemCode\":\"jsdjrcyl\",\"value\":\""+get_rawData.jsdjrcyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"产油量(t/d)\",\"itemCode\":\"jsdjrcyl1\",\"value\":\""+get_rawData.jsdjrcyl1+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"含水率(%)\",\"itemCode\":\"hsl\",\"value\":\""+get_rawData.hsl+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"公称排量(ml/r)\",\"itemCode\":\"gcpl\",\"value\":\""+get_rawData.gcpl+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"原油体积系数\",\"itemCode\":\"yytjxs\",\"value\":\""+get_rawData.yytjxs+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"有功功率(kW)\",\"itemCode\":\"yggl\",\"value\":\""+get_rawData.yggl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"水功率(kW)\",\"itemCode\":\"sgl\",\"value\":\""+get_rawData.sgl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"系统效率(%)\",\"itemCode\":\"xtxl\",\"value\":\""+get_rawData.xtxl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"吨液百米耗电量(kW·h/100m·t)\",\"itemCode\":\"dybmhdl\",\"value\":\""+get_rawData.dybmhdl+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"容积效率(%)\",\"itemCode\":\"ccssxs\",\"value\":\""+get_rawData.ccssxs+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"液体收缩系数(%)\",\"itemCode\":\"cmxs\",\"value\":\""+(get_rawData.cmxs*100)+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"总泵效(%)\",\"itemCode\":\"zbx\",\"value\":\""+get_rawData.zbx+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"抽油杆伸缩量(m)\",\"itemCode\":\"cygssl\",\"value\":\""+get_rawData.cygssl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"油管伸缩量(m)\",\"itemCode\":\"ygssl\",\"value\":\""+get_rawData.ygssl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"惯性增量(m)\",\"itemCode\":\"gxzl\",\"value\":\""+get_rawData.gxzl+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"泵入口压力(MPa)\",\"itemCode\":\"bxrkyl\",\"value\":\""+get_rawData.bxrkyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵入口温度(℃)\",\"itemCode\":\"bxrkwd\",\"value\":\""+get_rawData.bxrkwd+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵入口就地气液比(m^3/t)\",\"itemCode\":\"brkjdqyb\",\"value\":\""+get_rawData.brkjdqyb+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵入口液体粘度(mPa·s)\",\"itemCode\":\"bxrkytnd\",\"value\":\""+get_rawData.bxrkytnd+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵入口原油体积系数\",\"itemCode\":\"bxrkyytjxs\",\"value\":\""+get_rawData.bxrkyytjxs+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"泵出口压力(MPa)\",\"itemCode\":\"bpckyl\",\"value\":\""+get_rawData.bpckyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵出口温度(℃)\",\"itemCode\":\"bpckwd\",\"value\":\""+get_rawData.bpckwd+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵出口就地气液比(m^3/t)\",\"itemCode\":\"bckjdqyb\",\"value\":\""+get_rawData.bckjdqyb+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵出口液体粘度(mPa·s)\",\"itemCode\":\"bpckytnd\",\"value\":\""+get_rawData.bpckytnd+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"泵出口原油体积系数\",\"itemCode\":\"bpckyytjxs\",\"value\":\""+get_rawData.bpckyytjxs+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"一级杆最大载荷(kN)\",\"itemCode\":\"yjzdzh\",\"value\":\""+get_rawData.yjzdzh+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"一级杆最小载荷(kN)\",\"itemCode\":\"yjzxzh\",\"value\":\""+get_rawData.yjzxzh+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"一级杆最大应力(MPa)\",\"itemCode\":\"yjzdyl\",\"value\":\""+get_rawData.yjzdyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"一级杆最小应力(MPa)\",\"itemCode\":\"yjzxyl\",\"value\":\""+get_rawData.yjzxyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"一级杆许用应力(MPa)\",\"itemCode\":\"yjxyyl\",\"value\":\""+get_rawData.yjxyyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"一级杆应力百分比(%)\",\"itemCode\":\"yjylbfb\",\"value\":\""+get_rawData.yjylbfb+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"二级杆最大载荷(kN)\",\"itemCode\":\"ejzdzh\",\"value\":\""+get_rawData.ejzdzh+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"二级杆最小载荷(kN)\",\"itemCode\":\"ejzxzh\",\"value\":\""+get_rawData.ejzxzh+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"二级杆最大应力(MPa)\",\"itemCode\":\"ejzdyl\",\"value\":\""+get_rawData.ejzdyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"二级杆最小应力(MPa)\",\"itemCode\":\"ejzxyl\",\"value\":\""+get_rawData.ejzxyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"二级杆许用应力(MPa)\",\"itemCode\":\"ejxyyl\",\"value\":\""+get_rawData.ejxyyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"二级杆应力百分比(%)\",\"itemCode\":\"ejylbfb\",\"value\":\""+get_rawData.ejylbfb+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"三级杆最大载荷(kN)\",\"itemCode\":\"sjzdzh\",\"value\":\""+get_rawData.sjzdzh+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"三级杆最小载荷(kN)\",\"itemCode\":\"sjzxzh\",\"value\":\""+get_rawData.sjzxzh+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"三级杆最大应力(MPa)\",\"itemCode\":\"sjzdyl\",\"value\":\""+get_rawData.sjzdyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"三级杆最小应力(MPa)\",\"itemCode\":\"sjzxyl\",\"value\":\""+get_rawData.sjzxyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"三级杆许用应力(MPa)\",\"itemCode\":\"sjxyyl\",\"value\":\""+get_rawData.sjxyyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"三级杆应力百分比(%)\",\"itemCode\":\"sjylbfb\",\"value\":\""+get_rawData.sjylbfb+"\",\"curve\":\"\"},";
    		
    		dataStr+="{\"item\":\"四级杆最大载荷(kN)\",\"itemCode\":\"sijzdzh\",\"value\":\""+get_rawData.sijzdzh+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"四级杆最小载荷(kN)\",\"itemCode\":\"sijzxzh\",\"value\":\""+get_rawData.sijzxzh+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"四级杆最大应力(MPa)\",\"itemCode\":\"sijzdyl\",\"value\":\""+get_rawData.sijzdyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"四级杆最小应力(MPa)\",\"itemCode\":\"sijzxyl\",\"value\":\""+get_rawData.sijzxyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"四级杆许用应力(MPa)\",\"itemCode\":\"sijxyyl\",\"value\":\""+get_rawData.sijxyyl+"\",\"curve\":\"\"},";
    		dataStr+="{\"item\":\"四级杆应力百分比(%)\",\"itemCode\":\"sijylbfb\",\"value\":\""+get_rawData.sijylbfb+"\",\"curve\":\"\"}";
    		
    		dataStr+="]}";
    		
    		var acqSataStr="{\"items\":[";
    		var txzt="",yxzt="";
    		if(get_rawData.txzt==1){
    			txzt="在线";
    			if(get_rawData.yxzt==1){
    				yxzt="运行";
    			}else{
    				yxzt="停止";
    			}
    		}else{
    			txzt="离线";
    			yxzt=""
    		}
    		acqSataStr+="{\"item\":\"通信状态\",\"itemCode\":\"txzt\",\"value\":\""+txzt+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"运行状态\",\"itemCode\":\"yxzt\",\"value\":\""+yxzt+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"转速(r/min)\",\"itemCode\":\"rpm\",\"value\":\""+get_rawData.rpm+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"油压(MPa)\",\"itemCode\":\"tubingpressure\",\"value\":\""+get_rawData.tubingpressure+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"套压(MPa)\",\"itemCode\":\"casingpressure\",\"value\":\""+get_rawData.casingpressure+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"井口油温(℃)\",\"itemCode\":\"wellheadfluidtemperature\",\"value\":\""+get_rawData.wellheadfluidtemperature+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电流(A)\",\"itemCode\":\"currenta\",\"value\":\""+get_rawData.currenta+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电流(A)\",\"itemCode\":\"currentb\",\"value\":\""+get_rawData.currentb+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电流(A)\",\"itemCode\":\"currentc\",\"value\":\""+get_rawData.currentc+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"A相电压(V)\",\"itemCode\":\"voltagea\",\"value\":\""+get_rawData.voltagea+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"B相电压(V)\",\"itemCode\":\"voltageb\",\"value\":\""+get_rawData.voltageb+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"C相电压(V)\",\"itemCode\":\"voltagec\",\"value\":\""+get_rawData.voltagec+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"有功功耗(kW·h)\",\"itemCode\":\"activepowerconsumption\",\"value\":\""+get_rawData.activepowerconsumption+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"无功功耗(kVar·h)\",\"itemCode\":\"reactivepowerconsumption\",\"value\":\""+get_rawData.reactivepowerconsumption+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"有功功率(kW)\",\"itemCode\":\"activepower\",\"value\":\""+get_rawData.activepower+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"无功功率(kVar)\",\"itemCode\":\"reactivepower\",\"value\":\""+get_rawData.reactivepower+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"反向功率(kW)\",\"itemCode\":\"reversepower\",\"value\":\""+get_rawData.reversepower+"\",\"curve\":\"\"},";
    		acqSataStr+="{\"item\":\"功率因数\",\"itemCode\":\"powerfactor\",\"value\":\""+get_rawData.powerfactor+"\",\"curve\":\"\"}";
    		acqSataStr+="]}";
    		
    		var controlSataStr="{\"items\":[";
    		var yxzt="停抽";
    		if(get_rawData.txzt==1){
    			if(get_rawData.yxzt==1){
        			yxzt="运行";
        		}
    		}else{
    			yxzt="";
    		}
    		
    		controlSataStr+="{\"item\":\"启/停抽\",\"itemcode\":\"startOrStopWell\",\"value\":\""+yxzt+"\",\"txzt\":\""+get_rawData.txzt+"\",\"operation\":true,\"isControl\":"+isControl+"},";
    		controlSataStr+="{\"item\":\"变频设置频率(Hz)\",\"itemcode\":\"setFreq\",\"value\":\""+(get_rawData.bpszpl==undefined?"":get_rawData.bpszpl)+"\",\"txzt\":"+get_rawData.txzt+",\"operation\":true,\"isControl\":"+isControl+"},";
    		controlSataStr+="{\"item\":\"变频运行频率(Hz)\",\"itemcode\":\"runFreq\",\"value\":\""+(get_rawData.bpyxpl==undefined?"":get_rawData.bpyxpl)+"\",\"txzt\":"+get_rawData.txzt+",\"operation\":false,\"isControl\":"+isControl+"},";
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
//    			        		return iconDiagnoseAnalysisCurve(value,e,o)
    			        		var id = e.record.id;
    			        		var item=o.data.item;
    			        		var txzt = o.data.txzt;
    			        		var text="";
    			        		var hand=false;
    			        		var hidden=false;
    			        		if(txzt==1&&isControl==1){
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
    		                                         
     		                                     	 var jh  = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.jh;
     		                                     	 Ext.getCmp("ProductionWellControlWellName_Id").setValue(jh);
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
        	var jlbh  = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.id;
        	var jh=Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
        	var new_params = {
        			jlbh: jlbh,
        			jh:jh
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});