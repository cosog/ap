Ext.define("AP.view.diagnosis.SingleDetailsInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.DiagnosisSingleDetailsInfoView', //别名
    layout: 'fit',
    iframe: true,
    border: false,
    referenceHolder: true,
    initComponent: function () {
    	var RPCSingleDetailsInfoView = Ext.create('AP.view.diagnosis.RPCSingleDetailsInfoView');
        var PCPSingleDetailsInfoView = Ext.create('AP.view.diagnosis.PCPSingleDetailsInfoView');
        Ext.apply(this, {
            items: [{
                xtype: 'tabpanel',
                id:"ProductionWellRealtimeAnalisisPanel",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                tabBar:{
                	items: [{
                        xtype: 'tbfill'
                    },{
                		xtype: 'button',
//                		width:180,
                        id:"CPUUsedPercentLabel_id",
                        text: 'CPU:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("CPU使用率(%)");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("cpuUsedPercent");
                            var itemCode= Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
//                		width:150,
                        id:"memUsedPercentLabel_id",
                        text: '内存:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("内存使用率(%)");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("memUsedPercent");
                            var itemCode= Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
//                		width:150,
                        id:"tableSpaceSizeProbeLabel_id",
                        text: '表空间:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("表空间使用率(%)");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("tableSpaceSize");
                            var itemCode= Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
//                		width:150,
                        id:"appRunStatusProbeLabel_id",
                        text: 'ac状态:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("ac运行状态");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("appRunStatus");
                            var itemCode= Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"adRunStatusProbeLabel_id",
                        text: 'ad状态:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("ad运行状态");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("adRunStatus");
                            var itemCode= Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                        id: 'webSocketTest_Id', //选择查看曲线的数据项名称
                        xtype: 'textfield',
                        value: '',
                        hidden: true
                    }]
                },
                items: [{
                    title: cosog.string.pumpUnit,
                    id:'RPCSingleDetailsInfoPanel_Id',
                    layout: "fit",
                    border: false,
                    items: RPCSingleDetailsInfoView
                }, {
                    title: cosog.string.screwPump,
                    id:'PCPSingleDetailsInfoPanel_Id',
                    layout: "fit",
                    border: false,
                    hidden: pcpHidden,
                    items:PCPSingleDetailsInfoView
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="RPCSingleDetailsInfoPanel_Id"){
                    		loadFSDiagramAnalysisSingleStatData();
                    	}else if(newCard.id=="PCPSingleDetailsInfoPanel_Id"){
                    		loadPCPRPMAnalysisSingleStatData();
                    	}
                    },beforeclose: function ( panel, eOpts) {
//        				alert("关闭");
        				probeWebsocketClose(probeWebsocketClient);
        			}
                }
             }]
        });
        this.callParent(arguments);
    }
});

ResourceProbeHistoryCurveChartFn = function (get_rawData, itemName, itemCode, divId) {
    var tickInterval = 1;
    var data = get_rawData.totalRoot;
    tickInterval = Math.floor(data.length / 10) + 1;
    var title = itemName.split("(")[0] + "曲线";
    var legend=false;
    var legendName = [itemName];
    if(itemCode.toUpperCase()=='cpuUsedPercent'.toUpperCase()){
    	legendName = [];
    	for (var i = 0; i < data.length; i++) {
    		if(isNotVal(data[i].value)){
    			var cpus=data[i].value.split(";");
        		if(cpus.length>legendName.length){
        			legendName = [];
        			for(var j = 0; j < cpus.length; j++){
        				legendName.push("CPU"+(j+1));
        			}
        		}
    		}
    		
    	}
    	legend=true;
    	ytitle='CPU使用率(%)';
    }
    var series = "[";
    for (var i = 0; i < legendName.length; i++) {
        series += "{\"name\":\"" + legendName[i] + "\",";
        series += "\"data\":[";
        for (var j = 0; j < data.length; j++) {
        	
    		var year = parseInt(data[j].acqTime.split(" ")[0].split("-")[0]);
            var month = parseInt(data[j].acqTime.split(" ")[0].split("-")[1]);
            var day = parseInt(data[j].acqTime.split(" ")[0].split("-")[2]);
            var hour = parseInt(data[j].acqTime.split(" ")[1].split(":")[0]);
            var minute = parseInt(data[j].acqTime.split(" ")[1].split(":")[1]);
            var second = parseInt(data[j].acqTime.split(" ")[1].split(":")[2]);
            if(itemCode.toUpperCase()=='cpuUsedPercent'.toUpperCase()){
            	if(isNotVal(data[j].value)){
            		var values=data[j].value.split(";");
            		if(values.length>i){
            			series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + values[i] + "]";
            		}else{
            			series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
            		}
            	}else{
            		series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
            	}
            }else{
            	if(isNotVal(data[j].value)){
            		series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + data[j].value + "]";
        		}else{
        			series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
        		}
            }
            if (j != data.length - 1) {
                series += ",";
            }
        }
        series += "]}";
        if (i != legendName.length - 1) {
            series += ",";
        }
    }
    series += "]";
    var ser = Ext.JSON.decode(series);
    var color = ['#800000', // 红
       '#008C00', // 绿
       '#000000', // 黑
       '#0000FF', // 蓝
       '#F4BD82', // 黄
       '#FF00FF' // 紫
     ];

    initResourceProbeHistoryCurveChartFn(ser, tickInterval, divId, title, "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "时间", itemName, color,legend);

    return false;
};

function initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, ytitle, color,legend) {
    
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    mychart = new Highcharts.Chart({
        chart: {
            renderTo: divId,
            type: 'spline',
            shadow: true,
            borderWidth: 0,
            zoomType: 'xy'
        },
        credits: {
            enabled: false
        },
        title: {
            text: title
        },
        subtitle: {
            text: subtitle
        },
        colors: color,
        xAxis: {
            type: 'datetime',
            title: {
                text: xtitle
            },
            labels: {
                formatter: function () {
                    return Highcharts.dateFormat("%Y-%m-%d", this.value);
                },
                rotation: 0, //倾斜度，防止数量过多显示不全  
                step: 2
            }
        },
        yAxis: [{
            lineWidth: 1,
            title: {
                text: ytitle,
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value, 2);
                }
            }
      }],
        tooltip: {
            crosshairs: true, //十字准线
            style: {
                color: '#333333',
                fontSize: '12px',
                padding: '8px'
            },
            dateTimeLabelFormats: {
                millisecond: '%Y-%m-%d %H:%M:%S.%L',
                second: '%Y-%m-%d %H:%M:%S',
                minute: '%Y-%m-%d %H:%M',
                hour: '%Y-%m-%d %H',
                day: '%Y-%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            }
        },
        exporting: {
            enabled: true,
            filename: 'class-booking-chart',
            url: context + '/exportHighcharsPicController/export'
        },
        plotOptions: {
            spline: {
                lineWidth: 1,
                fillOpacity: 0.3,
                marker: {
                    enabled: true,
                    radius: 3, //曲线点半径，默认是4
                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                    states: {
                        hover: {
                            enabled: true,
                            radius: 6
                        }
                    }
                },
                shadow: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            enabled: legend,
            borderWidth: 0
        },
        series: series
    });
};