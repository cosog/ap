Ext.define("AP.view.diagnosis.DiagnosisSingleInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.DiagnosisSingleInfoView', //别名
    layout: 'fit',
    iframe: true,
    border: false,
    referenceHolder: true,
    initComponent: function () {
        var SinglePanel = Ext.create('AP.view.diagnosis.SinglePanel');
        var SingleAnalysisPanel = Ext.create('AP.view.diagnosis.SingleAnalysisPanel');
        var ScrewPumpRealtimeAnalysisView=Ext.create('AP.view.diagnosis.ScrewPumpRealtimeAnalysisView');
        Ext.apply(this, {
            items: [{
                xtype: 'tabpanel',
                id:"ProductionWellRealtimeAnalisisPanel",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                items: [{
                    title: cosog.string.pumpUnit,
                    id:'pumpUnitRealtimeAnalysisPanel_Id',
                    layout: "fit",
                    border: false,
                    layout: "border",
                    border: false,
                    items: [{
                        region: 'center',
                        title: '统计数据', // 井名列表
                        id: 'SingleWellList_Id',
                        collapsible: false, // 是否折叠
                        split: true, // 竖折叠条
                        border: false,
                        layout: 'fit',
                        items: SinglePanel
                    }, {
                        region: 'east',
                        id: 'SingleAnalysisPanel_Id',
                        width: '65%',
                        title: '单井数据',
                        border: false,
                        collapsible: true, // 是否折叠
                        split: true, // 竖折叠条
                        layout: 'fit',
                        items: SingleAnalysisPanel, // 单张功图诊断分析内容
                        listeners: {
                            beforeCollapse: function (panel, eOpts) {
                                $("#singleAnalysisDiagramDiv1_id").hide();
                                $("#singleAnalysisDiagramDiv2_id").hide();
                                $("#singleAnalysisDiagramDiv3_id").hide();
                                $("#singleAnalysisDiagramDiv4_id").hide();
                                $("#singleAnalysisDiagramDiv5_id").hide();
                                $("#singleAnalysisDiagramDiv6_id").hide();
                            },
                            expand: function (panel, eOpts) {
                                $("#singleAnalysisDiagramDiv1_id").show();
                                $("#singleAnalysisDiagramDiv2_id").show();
                                $("#singleAnalysisDiagramDiv3_id").show();
                                $("#singleAnalysisDiagramDiv4_id").show();
                                $("#singleAnalysisDiagramDiv5_id").show();
                                $("#singleAnalysisDiagramDiv6_id").show();
                            }
                        }
                    }]
                }, {
                    title: cosog.string.screwPump,
                    id:'screwPumpRealtimeAnalysisPanel_Id',
                    layout: "fit",
                    border: false,
                    items:[ScrewPumpRealtimeAnalysisView]
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="pumpUnitRealtimeAnalysisPanel_Id"){
                    		loadStatData();
                    	}else if(newCard.id=="screwPumpRealtimeAnalysisPanel_Id"){
                    		loadScrewPumpRealtimeStatData();
                    	}
                    }
                }
             }]
        });
        this.callParent(arguments);
    }
});

function createDiagnosisColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' ";
        if (attr.dataIndex == 'gkmc') {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'egkmc') {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceElecWorkingConditionColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'txzt') {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTxztColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'yxzt') {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceYxztColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'phzt') {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'glphzt') {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return advicePowerBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:true";
        } else if (attr.dataIndex == 'jhh' || attr.dataIndex == 'jh') {
            myColumns += width_ + ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        } else if (attr.dataIndex == 'cjsj' || attr.dataIndex == 'gtcjsj') {
            myColumns += width_ + ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

DiagnosisDataCurveChartFn = function (get_rawData, itemName, itemCode, divId) {
    var tickInterval = 1;
    var data = get_rawData.totalRoot;
    tickInterval = Math.floor(data.length / 10) + 1;
    var upline = 0,
        downline = 0;
    var uplineName = '',
        downlineName = '';
    var limitlinewidth = 0;
    if (itemCode == 'currenta' || itemCode == 'currentb' || itemCode == 'currentc' || itemCode == 'voltagea' || itemCode == 'voltageb' || itemCode == 'voltagec') {
        upline = parseFloat(get_rawData.uplimit);
        downline = parseFloat(get_rawData.downlimit);
        uplineName = '上限:' + upline;
        downlineName = '下限:' + downline;
        limitlinewidth = 3;
    } else {
        upline = 0;
        downline = 0;
        uplineName = '';
        downlineName = '';
        limitlinewidth = 0;
    }

    var catagories = "[";
    var title = get_rawData.jh + "井" + itemName.split("(")[0] + "曲线";
    for (var i = 0; i < data.length; i++) {
        catagories += "\"" + data[i].cjsj + "\"";
        if (i < data.length - 1) {
            catagories += ",";
        }
    }
    catagories += "]";
    var legendName = [itemName];
    var series = "[";
    for (var i = 0; i < legendName.length; i++) {
        series += "{\"name\":\"" + legendName[i] + "\",";
        series += "\"data\":[";
        for (var j = 0; j < data.length; j++) {
            var year = parseInt(data[j].cjsj.split(" ")[0].split("-")[0]);
            var month = parseInt(data[j].cjsj.split(" ")[0].split("-")[1]);
            var day = parseInt(data[j].cjsj.split(" ")[0].split("-")[2]);
            var hour = parseInt(data[j].cjsj.split(" ")[1].split(":")[0]);
            var minute = parseInt(data[j].cjsj.split(" ")[1].split(":")[1]);
            var second = parseInt(data[j].cjsj.split(" ")[1].split(":")[2]);
//            series += "[" + Date.UTC(year, month - 1, day, hour, minute, second) + "," + data[j].value + "]";
            series += "[" + Date.parse(data[j].cjsj.replace(/-/g, '/')) + "," + data[j].value + "]";
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
    var cat = Ext.JSON.decode(catagories);
    var ser = Ext.JSON.decode(series);
    var color = ['#800000', // 红
       '#008C00', // 绿
       '#000000', // 黑
       '#0000FF', // 蓝
       '#F4BD82', // 黄
       '#FF00FF' // 紫
     ];

    initDiagnosisDataCurveChartFn(cat, ser, tickInterval, divId, title, "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "时间", itemName, color, upline, downline, uplineName, downlineName, limitlinewidth);

    return false;
};

function initDiagnosisDataCurveChartFn(catagories, series, tickInterval, divId, title, subtitle, xtitle, ytitle, color, upline, downline, uplineName, downlineName, limitlinewidth) {
    var max = null;
    var min = null;
    if (upline != 0) {
        max = upline + 10;
    }
    if (downline != 0) {
        min = downline - 10;
    }
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
            max: max,
            min: min,
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value, 2);
                }
            },
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: uplineName,
                    align: 'right',
                    x: -10
                },
                width: limitlinewidth,
                zIndex:10,
                value: upline //y轴显示位置
                   }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                    text: downlineName,
                    align: 'right',
                    x: -10
                },
                width: limitlinewidth,
                zIndex:10,
                value: downline //y轴显示位置
                   }]
      }],
        tooltip: {
            crosshairs: true, //十字准线
            style: {
                color: '#333333',
                fontSize: '12px',
                padding: '8px'
            },
//            xDateFormat: '%Y-%m-%d %H:%M:%S',
//            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
//            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
//                '<td style="padding:0"><b>{point.y:.2f}</b></td></tr>',
//            footerFormat: '</table>',
//            shared: true,
//            useHTML: true
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
            enabled: false,
            borderWidth: 0
        },
        series: series
    });
};