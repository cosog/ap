var fsToPSElectridHandsontableHelper=null;
Ext.define("AP.view.PSToFS.ElectricInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PSToFSElectricInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;

        var jhStore_A = new Ext.data.JsonStore({
            pageSize: defaultJhComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/PSToFSController/getWellNameList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true
        });
        
        var simpleCombo_A = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: cosog.string.jh,
                    id: 'PSToFSInversionJHCombo_Id',
                    store: jhStore_A,
                    labelWidth: 35,
                    width: 125,
                    queryMode: 'remote',
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    typeAhead: true,
                    autoSelect: true,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: true,
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize: comboxPagingStatus,
                    minChars: 0,
                    listeners: {
                        select: function (combo, record, index) {
                        	Ext.getCmp("PSToFSInversionCjsjCombo_Id").setValue("");
                        	CreateAndLoadFSToPSElectridTable();
                        }
                    }
                });
        
        var cjsjStore_A = new Ext.data.JsonStore({
            pageSize: defaultJhComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/PSToFSController/getAcquisitionTimeList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var wellName = Ext.getCmp('PSToFSInversionJHCombo_Id').getValue();
                    var new_params = {
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        
        var simpleCombo_Cjsj = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '采集时间',
                    id: 'PSToFSInversionCjsjCombo_Id',
                    store: cjsjStore_A,
                    labelWidth: 70,
                    width: 200,
                    queryMode: 'remote',
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    typeAhead: true,
                    autoSelect: true,
                    allowBlank: true,
                    triggerAction: 'all',
                    editable: true,
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize: comboxPagingStatus,
                    minChars: 0,
                    listeners: {
                        select: function (combo, record, index) {
                        	CreateAndLoadFSToPSElectridTable();
                        },
                        expand: function (sm, selections) {
                        	simpleCombo_Cjsj.clearValue();
                        	simpleCombo_Cjsj.getStore().loadPage(1); // 加载井下拉框的store
                        },
                    }
        });
        
        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                tbar:[simpleCombo_A,'-',simpleCombo_Cjsj,{
        			xtype: 'textfield',
        			id: 'fsToPSElecCurveJh_Id',
        			hidden:true
        		},{
        			xtype: 'textfield',
        			id: 'fsToPSElecCurveCjsj_Id',
        			hidden:true
        		},{
        			xtype: 'textfield',
        			id: 'fsToPSInversionResultJson_Id',
        			hidden:true
        		},{
        			xtype: 'textfield',
        			id: 'fsToPSSelectedFSDiagramId_Id',
        			hidden:true
        		},'->',{
                    xtype: 'textfield',
                    id: "PSToFSElectricCycleStartPoint_id",
                    anchor: '100%',
                    labelWidth: 80,
                    width: 120,
                    fieldLabel: '周期开始点',
                    readOnly: false
                },'-',{
                    xtype: 'textfield',
                    id: "PSToFSElectricCycleEndPoint_id",
                    anchor: '100%',
                    labelWidth: 80,
                    width: 120,
                    fieldLabel: '周期结束点',
                    readOnly: false
                },'-',{
                    xtype: 'button',
                    text: '反演',
                    iconCls:'Calculate',
                    pressed: true,
                    handler: function (v, o) {
                    	doInversionCalculate();
                    }
                },'-',{
                    xtype: 'button',
                    text: '保存结果',
                    iconCls:'save',
                    pressed: true,
                    handler: function (v, o) {
                    	saveInversionResult();
                    }
                }],
                items: [{
                        	region: 'west',
                        	width:'30%',
                        	layout: 'fit',
                        	id:'FSToPSElectricInfoPanel_Id',
                        	title: '电参数据',
                        	collapsible: true, // 是否折叠
                    		split: true, // 竖折叠条
                        	border: false,
                        	dockedItems: [{
                                xtype: 'toolbar',
                                dock: 'top',
                                id:'SelectPSToFSElectricFilePanelToolbarId',
                                items:[{
                                		xtype:'form',
                                		id:'PSToFSElectricFilePanelFormId',
                                	    width: "100%",
                                	    bodyPadding: 10,
                                	    frame: true,
                                	    items: [{
                                	    	xtype: 'filefield',
                                        	id:'PSToFSElectricFilefieldId',
//                                        	inputType:'file',
//                                            name: 'SurfaceCardFile',
                                            name: 'file',
                                            fieldLabel: '电参数文件',
                                            labelWidth: 80,
                                            width:'100%',
                                            msgTarget: 'side',
                                            allowBlank: false,
                                            anchor: '100%',
                                            draggable:true,
                                            buttonText: '请选择文件',
//                                            accept:['.xls','.xlsx','.csv'],
                                            accept:['.xls'],
                                            listeners:{
                                                afterrender:function(cmp){
                                                    cmp.fileInputEl.set({
//                                                        multiple:'multiple'
                                                    });
                                                },
                                                change:function(cmp){
                                                	submitPSToFSElectricFile();
                                                }
                                            }
                                	    }]
                                }],
                            }],
                        	html: '<div id="FSToPSElectricInfoDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                            	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            			CreateAndLoadFSToPSElectridTable();
                                }
                            }
                    	},{
                    		region: 'center',
                    		id: 'FSToPSElectricCurvePanel_Id',
                    		title: '电参数据曲线',
                    		border: false,
                    		html: '<div id="FSToPSElectricCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    		listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if($("#FSToPSElectricCurveDiv_Id").highcharts()!=undefined){
                            			$("#FSToPSElectricCurveDiv_Id").highcharts().setSize(adjWidth,adjHeight,true);
                            		}
                                }
                            }
                    	},{
                    		region: 'east',
                    		width:'20%',
                    		id: 'FSToPSCalculateResultPanel_Id',
                    		title: '反演结果',
                    		collapsible: true, // 是否折叠
                    		split: true, // 竖折叠条
                    		border: false,
                    		layout: "border",
                    		items:[{
                    			region: 'north',
                    			height:'50%',
                    			id: 'FSToPSCalculateSelectedCurvePanel_Id',
                    			html: '<div id="FSToPSCalculateSelectedCurveDiv_Id" style="width:100%;height:100%;"></div>',
                    			listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	if($("#FSToPSCalculateSelectedCurveDiv_Id").highcharts()!=undefined){
                                			$("#FSToPSCalculateSelectedCurveDiv_Id").highcharts().setSize(adjWidth,adjHeight,true);
                                		}
                                    }
                                }
                    		},{
                    			region: 'center',
                    			id: 'FSToPSCalculateSurfacePanel_Id',
                    			html: '<div id="FSToPSCalculateSurfaceDiv_Id" style="width:100%;height:100%;"></div>',
                    			listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	if($("#FSToPSCalculateSurfaceDiv_Id").highcharts()!=undefined){
                                			$("#FSToPSCalculateSurfaceDiv_Id").highcharts().setSize(adjWidth,adjHeight,true);
                                		}
                                    }
                                }
                    		}]
                    	}]
                	}]
        });
        me.callParent(arguments);
    }

});

function submitPSToFSElectricFile() {
	var form = Ext.getCmp("PSToFSElectricFilePanelFormId");
	var value=Ext.getCmp("PSToFSElectricFilefieldId").getValue();
    if(form.isValid()) {
        form.submit({
            url: context + '/PSToFSController/getPSToFSElectricReaultData',
            method:'post',
            waitMsg: '文件上传中...',
            success: function(fp, o) {
            	Ext.getCmp("PSToFSElectricFilefieldId").setRawValue(value);
            	var result =  Ext.JSON.decode(o.response.responseText);
            	if(result.success){
            		Ext.getCmp("PSToFSInversionJHCombo_Id").setValue(result.WellName);
            		Ext.getCmp("PSToFSInversionCjsjCombo_Id").setValue(result.cjsj);
            		Ext.getCmp("fsToPSElecCurveJh_Id").setValue(result.WellName);
            		Ext.getCmp("fsToPSElecCurveCjsj_Id").setValue(result.cjsj);
			fsToPSElectridHandsontableHelper = FSToPSElectridHandsontableHelper.createNew("FSToPSElectricInfoDiv_Id");
            		var colHeaders="['序号','A相有功功率(kW)','B相有功功率(kW)','C相有功功率(kW)']";
            		var columns="[{data:'id'},{data:'activePowerA'},{data:'activePowerB'},{data:'activePowerC'}]";
            		fsToPSElectridHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
            		fsToPSElectridHandsontableHelper.columns=Ext.JSON.decode(columns);
            		fsToPSElectridHandsontableHelper.createTable(result.totalRoot);
            		
            		showPSToFSElectricCurve(result,result.totalRoot,"FSToPSElectricCurveDiv_Id");
            		
            		$("#FSToPSCalculateSelectedCurveDiv_Id").html("");
            		$("#FSToPSCalculateSurfaceDiv_Id").html("");
            		
            		Ext.getCmp("PSToFSElectricCycleStartPoint_id").setValue("");
            		Ext.getCmp("PSToFSElectricCycleEndPoint_id").setValue("");
            	}else{
            		Ext.MessageBox.alert("错误","文件格式错误");
            		Ext.getCmp("fsToPSElecCurveJh_Id").setValue("");
            		Ext.getCmp("fsToPSElecCurveCjsj_Id").setValue("");
            	}
            },
            failure:function(){
    			Ext.MessageBox.alert("错误","文件上传失败");
    		}
        });
    }
    return false;
};

function CreateAndLoadFSToPSElectridTable(){
	var wellName=Ext.getCmp("PSToFSInversionJHCombo_Id").getValue();
	var cjsj=Ext.getCmp("PSToFSInversionCjsjCombo_Id").getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getInversionCalaulateResult',
		success:function(response) {
			var result=Ext.JSON.decode(response.responseText);
			fsToPSElectridHandsontableHelper = FSToPSElectridHandsontableHelper.createNew("FSToPSElectricInfoDiv_Id");
			var colHeaders="['序号','A相有功功率(kW)','B相有功功率(kW)','C相有功功率(kW)']";
			var columns="[{data:'id'},{data:'activePowerA'},{data:'activePowerB'},{data:'activePowerC'}]";
			fsToPSElectridHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
			fsToPSElectridHandsontableHelper.columns=Ext.JSON.decode(columns);
			if(result.Total>0){
				fsToPSElectridHandsontableHelper.createTable(result.ElectricData);
			}else{
				fsToPSElectridHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
			}
			
			showPSToFSElectricCurve(result,result.ElectricData,"FSToPSElectricCurveDiv_Id");
			
			showPSToFSSelectedElecCurve(result.ElectricData,"FSToPSCalculateSelectedCurveDiv_Id",result.StartPoint,result.EndPoint);
			
			showSavedInversionSurfaceCard(result, "FSToPSCalculateSurfaceDiv_Id",result.FSDiagramId);
			
			Ext.getCmp("PSToFSInversionJHCombo_Id").setValue(result.WellName);
			Ext.getCmp("PSToFSInversionCjsjCombo_Id").setValue(result.cjsj);
			Ext.getCmp("fsToPSElecCurveJh_Id").setValue(result.WellName);
    		Ext.getCmp("fsToPSElecCurveCjsj_Id").setValue(result.cjsj);
    		Ext.getCmp("PSToFSElectricCycleStartPoint_id").setValue(result.StartPoint);
    		Ext.getCmp("PSToFSElectricCycleEndPoint_id").setValue(result.EndPoint);
    		Ext.getCmp("fsToPSSelectedFSDiagramId_Id").setValue(result.FSDiagramId);
    		
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			wellName:wellName,
			cjsj:cjsj
        }
	}); 
};

function showPSToFSElectricCurve(result,data,divId){
	var activePowerA=[];
	var activePowerB=[];
	var activePowerC=[];
	var activePowerSum=[];
	for(var i=0;i<data.length;i++){
		activePowerA.push(parseFloat(data[i].activePowerA));
		activePowerB.push(parseFloat(data[i].activePowerB));
		activePowerC.push(parseFloat(data[i].activePowerC));
		activePowerSum.push(parseFloat(data[i].activePowerSum));
	}
	var mychart = new Highcharts.Chart({
		chart: {
            type: 'spline',
            borderWidth: 0,
            renderTo: divId
        },
		title: {
			text: '电参数据曲线'
		},
		subtitle: {
			text: result.WellName+' ['+result.cjsj+"]"
		},
		xAxis:{
			crosshair:{
				width:1
			}
		},
		yAxis: [{
			title: {
				text: 'A相有功功率(kW)'
			},
			crosshair:{
				width:1
			},
			offset:0,
			endOnTick:false,
			height:'30%'
		},{
			opposite:false,
			title: {
				text: 'B相有功功率(kW)'
			},
			crosshair:{
				width:1
			},
			offset:0,
			endOnTick:false,
			top:'35%',
			height:'30%'
		},{
			opposite:false,
			title: {
				text: 'C相有功功率(kW)'
			},
			crosshair:{
				width:1
			},
			offset:0,
			endOnTick:false,
			top:'70%',
			height:'30%'
		}],
		tooltip:{
			shared:true
//			crosshairs : true,//十字准线
		},
		legend: {
			enabled:false,
			layout: 'vertical',
			align: 'right',
			verticalAlign: 'middle'
		},
		credits:{
			enabled:false
		},
		series: [{
			name: 'A相有功功率',
			type:'spline',
			yAxis:0,
			data: activePowerA
		}, {
			name: 'B相有功功率',
			type:'spline',
			yAxis:1,
			data: activePowerB
		}, {
			name: 'C相有功功率',
			type:'spline',
			yAxis:2,
			data: activePowerC
		}],
		plotOptions : {
            series: {
                allowPointSelect: true
            },
			 spline: {
				 marker: {
		            	enabled: true,
		            	radius: 2,  //曲线点半径，默认是4
		                states: {
		                   hover: {
		                        enabled: true,
		                        radius: 6
		                    }  
		                }  
		            }, 
		            events:{
		            	click: function(e) {
		            		var updateDate=true;
		            		var selectedStartPoint='',selectedEndPoint='';
		            		var chart = $('#'+divId).highcharts();
		            		selectedPoints = chart.getSelectedPoints();
		            		if(selectedPoints.length>0){//如果有选择的点
		            			if(e.point.index<selectedPoints[0].index){//当此次点击的点小于已选择的最小点时，将该点作为起点
		            				selectedStartPoint=e.point.x;
		            				selectedEndPoint=selectedPoints[selectedPoints.length-1].x
		            			}else if(e.point.index>selectedPoints[selectedPoints.length-1].index){//当此次点击的点大于已选择的最大点时，将该点作为结束点
		            				selectedStartPoint=selectedPoints[0].x
		            				selectedEndPoint=e.point.x
		            			}else{//当选择的点在已选择的范围内时
		            				updateDate=false;
		            			}
		            		}else{//当没有选择点时，将该次点击的点设为开始点
		            			selectedStartPoint=e.point.x;
		            		}
		            		if(updateDate){
		            			Ext.getCmp("PSToFSElectricCycleStartPoint_id").setValue(selectedStartPoint);
			            		Ext.getCmp("PSToFSElectricCycleEndPoint_id").setValue(selectedEndPoint);
		            		}
		            	}
		            }
			 } 
		}
	});
}

function showPSToFSSelectedElecCurve(SelectedElecData,divId,start,end){
	if(start==undefined){
		start=0;
	}
	if(end==undefined){
		end=SelectedElecData.length-1;
	}
	var activePowerA=[];
	var activePowerB=[];
	var activePowerC=[];
	for(var i=start;i<=end;i++){
		activePowerA.push(SelectedElecData[i].activePowerA);
		activePowerB.push(SelectedElecData[i].activePowerB);
		activePowerC.push(SelectedElecData[i].activePowerC);
	}
	var mychart = new Highcharts.Chart({
		chart: {
            type: 'spline',
            borderWidth: 0,
            renderTo: divId
        },
		title: {
			text: '所选电参数据曲线'
		},
		subtitle: {
//			text: result.wellName+' ['+result.cjsj+"]"
		},
		xAxis:{
			crosshair:{
				width:1
			}
		},
		yAxis: [{
			title: {
				text: '三相有功功率(kW)'
			},
			crosshair:{
				width:1
			},
			offset:0,
			endOnTick:false
		}],
		tooltip:{
			shared:true
//			crosshairs : true,//十字准线
		},
		legend: {
			enabled:true,
			layout: 'horizontal',
			align: 'center',
			verticalAlign: 'bottom'
		},
		credits:{
			enabled:false
		},
		series: [{
			name: 'A相有功功率',
			type:'spline',
			data: activePowerA
		}, {
			name: 'B相有功功率',
			type:'spline',
			data: activePowerB
		}, {
			name: 'C相有功功率',
			type:'spline',
			data: activePowerC
		}],
		plotOptions : {
            series: {
                allowPointSelect: true
            },
			 spline: {
				 marker: {
		            	enabled: true,
		            	radius: 2,  //曲线点半径，默认是4
		                states: {
		                   hover: {
		                        enabled: true,
		                        radius: 6
		                    }  
		                }  
		            }
			 } 
		}
	});
}

showInversionSurfaceCard = function(result, divid,index) {
	var data = "["; // 功图data
	for (var i=0; i <= result.CNT; i++) {
		if(i<result.CNT){
			data += "[" + result.FSDiagram[index].S[i] + ","+result.FSDiagram[index].F[i]+"],";
		}else{
			data += "[" + result.FSDiagram[index].S[0] + ","+result.FSDiagram[index].F[0]+"]";//将图形的第一个点拼到最后面，使图形闭合
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);

	var jh=result.WellName;         // 井名
	var cjsj=result.AcquisitionTime;     // 采集时间
	var cch=result.Stroke;       // 冲程
	var cci=result.SPM;       // 冲次
	var cnt=result.CNT;     // 功图点数
	var xtext='<span style="text-align:center;">'+cosog.string.wy+'<br />';
    xtext+='功图点数:' + cnt + '<br />';
    xtext+='冲程:' + cch + 'm 冲次:' + cci + '/min<br /></span>';
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: cosog.string.gt+""+(index+1)  // 地面功图                        
		        },                                                                                   
		        subtitle: {
		        	text: jh+' ['+cjsj+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                text: xtext,    // 坐标+显示文字
		                useHTML: false,
		                margin:5,
                        style: {
                        	fontSize: '12px',
                            padding: '5px'
                        }
		            },                                                                               
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: cosog.string.zh   // 载荷（kN） 
                    },
//                    min: 0 ,                 // 最小值
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: ''   // 不显示次刻度线
		            
		        },
		        legend: {
					enabled:false
				},
		        exporting:{
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },                                                                                 
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        }, 
		        series: [{                                                                           
		            name: '',                                                                  
		            color: '#00ff00',   
		            lineWidth:3,
		            data:  pointdata                                                                                  
		        }]
	});
	return false;
}

showSavedInversionSurfaceCard = function(result, divid,index) {
	var data = "["; // 功图data
	for (var i=0; i <= result.CNT; i++) {
		if(i<result.CNT){
			data += "[" + result.FSDiagram.S[i] + ","+result.FSDiagram.F[i]+"],";
		}else{
			data += "[" + result.FSDiagram.S[0] + ","+result.FSDiagram.F[0]+"]";//将图形的第一个点拼到最后面，使图形闭合
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);

	var jh=result.WellName;         // 井名
	var cjsj=result.cjsj;     // 采集时间
	var cch=result.Stroke;       // 冲程
	var cci=result.SPM;       // 冲次
	var cnt=result.CNT;     // 功图点数
	var xtext='<span style="text-align:center;">'+cosog.string.wy+'<br />';
    xtext+='功图点数:' + cnt + '<br />';
    xtext+='冲程:' + cch + 'm 冲次:' + cci + '/min<br /></span>';
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: cosog.string.gt+""+(index+1)  // 地面功图                        
		        },                                                                                   
		        subtitle: {
		        	text: jh+' ['+cjsj+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                text: xtext,    // 坐标+显示文字
		                useHTML: false,
		                margin:5,
                        style: {
                        	fontSize: '12px',
                            padding: '5px'
                        }
		            },                                                                               
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: cosog.string.zh   // 载荷（kN） 
                    },
//                    min: 0 ,                 // 最小值
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: ''   // 不显示次刻度线
		            
		        },
		        legend: {
					enabled:false
				},
		        exporting:{
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },                                                                                 
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        }, 
		        series: [{                                                                           
		            name: '',                                                                  
		            color: '#00ff00',   
		            lineWidth:3,
		            data:  pointdata                                                                                  
		        }]
	});
	return false;
}

function selectInversionFSDiagram(index){
	Ext.getCmp("fsToPSSelectedFSDiagramId_Id").setValue(index);
	var data=Ext.JSON.decode(Ext.getCmp("fsToPSInversionResultJson_Id").getValue());
	var tabPanel = Ext.getCmp("frame_center_ids");
	var getTabId = tabPanel.getComponent("PSToFS_ElectricInfo");
	if(!getTabId){
		tabPanel.add(Ext.create("AP.view.PSToFS.ElectricInfoView", {
            id: "PSToFS_ElectricInfo",
            closable: false,
            iconCls: "Image",
            closeAction: 'destroy',
            title: '反演结果',
            listeners: {
                afterrender: function () {
                    //all_loading.hide();
                },
                delay: 150
            }
        })).show();
	}
	tabPanel.setActiveTab("PSToFS_ElectricInfo");
	showInversionSurfaceCard(data, "FSToPSCalculateSurfaceDiv_Id",index);
}

function saveInversionResult(){
	var wellName=Ext.getCmp("fsToPSElecCurveJh_Id").getValue();
	var cjsj=Ext.getCmp("fsToPSElecCurveCjsj_Id").getValue();
	var StartPoint= Ext.getCmp("PSToFSElectricCycleStartPoint_id").getValue();
	var EndPoint=Ext.getCmp("PSToFSElectricCycleEndPoint_id").getValue();
	var FSDiagramId=Ext.getCmp("fsToPSSelectedFSDiagramId_Id").getValue();
	var data=Ext.JSON.decode(Ext.getCmp("fsToPSInversionResultJson_Id").getValue());
	var Stroke=data.Stroke;
	var SPM=data.SPM;
	var CNT=data.CNT;
	var FSDiagram=data.FSDiagram[parseInt(FSDiagramId)];
	
	var ElectricData=[];
	var ElectricTableData=fsToPSElectridHandsontableHelper.hot.getData();
	for(var i=0;i<ElectricTableData.length;i++){
		if(ElectricTableData[i][1]!=null){
			var data="{";
        	for(var j=0;j<fsToPSElectridHandsontableHelper.columns.length;j++){
        		data+="\""+fsToPSElectridHandsontableHelper.columns[j].data+"\":"+ElectricTableData[i][j]+"";
        		if(j<fsToPSElectridHandsontableHelper.columns.length-1){
        			data+=","
        		}
        	}
        	data+="}";
        	ElectricData.push(Ext.JSON.decode(data));
		}
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/saveCalaulateResult',
		success:function(response) {
			Ext.MessageBox.alert("信息","保存成功");
		},
		failure:function(){
			Ext.MessageBox.alert("信息","请求失败");
		},
		params: {
			wellName:wellName,
			cjsj: cjsj,
			ElectricData:JSON.stringify(ElectricData),
			StartPoint:StartPoint,
			EndPoint:EndPoint,
			FSDiagramId:FSDiagramId,
			Stroke:Stroke,
			SPM:SPM,
			CNT:CNT,
			FSDiagram:JSON.stringify(FSDiagram)
        }
	}); 
}

function doInversionCalculate(){
	Ext.getCmp("fsToPSInversionResultJson_Id").setValue("");
	Ext.getCmp("fsToPSSelectedFSDiagramId_Id").setValue("");
	var StartPoint= Ext.getCmp("PSToFSElectricCycleStartPoint_id").getValue();
	var EndPoint=Ext.getCmp("PSToFSElectricCycleEndPoint_id").getValue();
	if(StartPoint!=""&&EndPoint!=""){
		StartPoint=parseInt(StartPoint);
		EndPoint=parseInt(EndPoint);
		var ElecData=fsToPSElectridHandsontableHelper.hot.getData();
		if(StartPoint<0){
			StartPoint=0;
		}
		if(EndPoint>=ElecData.length){
			EndPoint=ElecData.length-1;
		}
		var SelectedElecData=[];
		for(var i=StartPoint;i<=EndPoint;i++){
    		if(ElecData[i][1]!=null&&ElecData[i][1]!=""){
    			var data="{";
            	for(var j=0;j<fsToPSElectridHandsontableHelper.columns.length;j++){
            		data+="\""+fsToPSElectridHandsontableHelper.columns[j].data+"\":"+ElecData[i][j]+"";
            		if(j<fsToPSElectridHandsontableHelper.columns.length-1){
            			data+=","
            		}
            	}
            	data+="}";
            	SelectedElecData.push(Ext.JSON.decode(data));
    		}
    	}
		if(SelectedElecData.length>0){
			showPSToFSSelectedElecCurve(SelectedElecData,"FSToPSCalculateSelectedCurveDiv_Id");
			
			var wellName=Ext.getCmp("fsToPSElecCurveJh_Id").getValue();
			var cjsj=Ext.getCmp("fsToPSElecCurveCjsj_Id").getValue();
			Ext.Ajax.request({
        		method:'POST',
        		url:context + '/PSToFSController/getCalaulateResult',
        		success:function(response) {
        			Ext.getCmp("fsToPSInversionResultJson_Id").setValue(response.responseText);
        			var data=Ext.JSON.decode(Ext.getCmp("fsToPSInversionResultJson_Id").getValue());
        			if (data.ResultStatus==1) {
//                    	Ext.MessageBox.alert("信息","计算成功");
        				Ext.getCmp("fsToPSSelectedFSDiagramId_Id").setValue(0);
                    	showInversionSurfaceCard(data, "FSToPSCalculateSurfaceDiv_Id",0);
                    	
                    	var tabPanel = Ext.getCmp("frame_center_ids");
                    	var getTabId = tabPanel.getComponent("PSToFS_ResultInfo");
                    	if(!getTabId){
                    		tabPanel.add(Ext.create("AP.view.PSToFS.ResultInfoView", {
                                id: "PSToFS_ResultInfo",
                                closable: true,
                                iconCls: "Image",
                                closeAction: 'destroy',
                                title: '反演结果',
                                listeners: {
                                    afterrender: function () {
                                        //all_loading.hide();
                                    },
                                    delay: 150
                                }
                            })).show();
                    	}
                    	tabPanel.setActiveTab("PSToFS_ResultInfo");
                    	
                    	$("#PSToFSResultContainer").html("");
                    	var PSToFSResultContent = Ext.getCmp("PSToFSResultContent"); // 获取功图列表panel信息
                    	var panelHeight = PSToFSResultContent.getHeight(); // panel的高度
                        var panelWidth = PSToFSResultContent.getWidth(); // panel的宽度
                        var scrollWidth = getScrollWidth(); // 滚动条的宽度
                        var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
                        var gtWidth = (panelWidth - scrollWidth) / columnCount; // 有滚动条时图形宽度
                        var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
                        var gtWidth2 = gtWidth + 'px';
                        var gtHeight2 = gtHeight + 'px';
                        var htmlResult = '';
                        var divId = '';
                        // 功图列表，创建div
                        
                        Ext.Array.each(data.FSDiagram, function (name, index, countriesItSelf) {
                            divId = 'InversionGTDiv' + index+"_ID";
                            htmlResult += '<div id=\"' + divId + '\"';
                            htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';"';
                            htmlResult += ' ondblclick="selectInversionFSDiagram(' + index + ')"';
                            htmlResult += '></div>';
                        });
                        $("#PSToFSResultContainer").append(htmlResult);
                        
                        var progressBar=Ext.Msg.show({
                            	title:"进度",
                            	msg:"反演进度...",
                            	progress:true,
                            	width:300,
                            	interval:1000
                            });
                        
                        for(var i=0;i<data.FSDiagram.length;i++){
                        	divId = 'InversionGTDiv' + i+"_ID";
                        	showInversionSurfaceCard(data, divId,i);
                        	var total=data.FSDiagram.length;
                        	var percentage=(i+1)/total;
                        	var bartext=percentage*100+"%";
                        	progressBar.updateProgress(percentage,bartext);
                        	if(i+1==total){
                        		progressBar.hide();
                        	}
                        }
//                        Ext.Array.each(data.FSDiagram, function (name, index, countriesItSelf) {
//                        	
//                        });
                    	
                    }else if(data.ResultStatus==-9999){
                    	Ext.MessageBox.alert("信息","计算程序无法回数据,请检查数据完整性");
                    } else {
                    	Ext.MessageBox.alert("信息","计算失败，失败代码:"+data.ResultStatus+",请检查数据完整性");

                    }
        		},
        		failure:function(){
        			Ext.MessageBox.alert("信息","请求失败");
        		},
        		params: {
        			wellName:wellName,
        			cjsj: cjsj,
        			data: JSON.stringify(SelectedElecData)
                }
        	}); 
		}
	}else{
		Ext.MessageBox.alert("错误","请选择反演周期!");
	}
}

var FSToPSElectridHandsontableHelper = {
	    createNew: function (divid) {
	        var fsToPSElectridHandsontableHelper = {};
	        fsToPSElectridHandsontableHelper.hot1 = '';
	        fsToPSElectridHandsontableHelper.divid = divid;
	        fsToPSElectridHandsontableHelper.validresult=true;//数据校验
	        fsToPSElectridHandsontableHelper.colHeaders=[];
	        fsToPSElectridHandsontableHelper.columns=[];
	        fsToPSElectridHandsontableHelper.AllData=[];
	        
	        fsToPSElectridHandsontableHelper.createTable = function (data) {
	        	$('#'+fsToPSElectridHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+fsToPSElectridHandsontableHelper.divid);
	        	fsToPSElectridHandsontableHelper.hot = new Handsontable(hotElement, {
	        		data: data,
	                hiddenColumns: {
	                    columns: [0],
	                    indicators: true
	                },
	                columns:fsToPSElectridHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: true,//显示行头
	                colHeaders:fsToPSElectridHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    cellProperties.readOnly = true;
	                    return cellProperties;
	                }
	        	});
	        }
	        //保存数据
	        fsToPSElectridHandsontableHelper.saveData = function () {}
	        fsToPSElectridHandsontableHelper.clearContainer = function () {
	        	fsToPSElectridHandsontableHelper.AllData = [];
	        }
	        return fsToPSElectridHandsontableHelper;
	    }
};