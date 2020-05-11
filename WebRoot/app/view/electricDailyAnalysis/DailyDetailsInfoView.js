/*******************************************************************************
 * 电参反演全天评价视图
 *
 * @author zhao
 *
 */
Ext.define("AP.view.electricDailyAnalysis.DailyDetailsInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.electricAnalysisDailyDetailsInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
    	var store =Ext.create('AP.store.electricDailyAnalysis.ElectricAnalysisDailyDetailsListStore');
        var wellComboBoxStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/wellInformationManagerController/loadWellComboxList',
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
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('electricAnalysisDailyDetailsWellCom_Id').getValue();
                    var new_params = {
                    	wellName: wellName,
                        orgId: org_Id,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellComboBox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.wellName,
            id: "electricAnalysisDailyDetailsWellCom_Id",
            store: wellComboBoxStore,
            labelWidth: 35,
            width: 125,
            queryMode: 'remote',
            typeAhead: true,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            pageSize: comboxPagingStatus,
            minChars: 0,
            multiSelect: false,
            listeners: {
                expand: function (sm, selections) {
                	wellComboBox.getStore().loadPage(1);
                },
                select: function (combo, record, index) {
                	if(combo.value==""){
                		Ext.getCmp("ElectricAnalysisDailyDetailsAllBtn_Id").hide();
                		Ext.getCmp("ElectricAnalysisDailyDetailsHisBtn_Id").show();
                		Ext.getCmp("ElectricAnalysisDailyDetailsDate_Id").show();
                		Ext.getCmp("ElectricAnalysisDailyDetailsStartDate_Id").hide();
                		Ext.getCmp("ElectricAnalysisDailyDetailsEndDate_Id").hide();
                	}else{
                		Ext.getCmp("ElectricAnalysisDailyDetailsAllBtn_Id").show();
                		Ext.getCmp("ElectricAnalysisDailyDetailsHisBtn_Id").hide();
                		Ext.getCmp("ElectricAnalysisDailyDetailsDate_Id").hide();
                		Ext.getCmp("ElectricAnalysisDailyDetailsStartDate_Id").show();
                		Ext.getCmp("ElectricAnalysisDailyDetailsEndDate_Id").show();
                	}
                	Ext.getCmp("ElectricAnalysisDailyDetails_Id").getStore().loadPage(1);
                }
            }
        });
        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                items: [
                    {
                        region: 'center',
                        layout: 'fit',
                        id:'ElectricAnalysisDailyDetailsDataListPanel_Id',
                        title: '统计数据',
                        border: false,
                        tbar: [wellComboBox,'-',{
                            xtype: 'datefield',
                            anchor: '100%',
                            fieldLabel: '日期',
                            labelWidth: 30,
                            width: 120,
                            format: 'Y-m-d ',
                            id: 'ElectricAnalysisDailyDetailsDate_Id',
                            value: new Date(),
                            listeners: {
                            	select: function (combo, record, index) {
                            		Ext.getCmp("ElectricAnalysisDailyDetails_Id").getStore().loadPage(1);
                                }
                            }
                        },{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: '',
                            labelWidth: 0,
                            width: 90,
                            format: 'Y-m-d ',
                            id: 'ElectricAnalysisDailyDetailsStartDate_Id',
                            value: '',
                            listeners: {
                            	select: function (combo, record, index) {
                            		Ext.getCmp("ElectricAnalysisDailyDetails_Id").getStore().loadPage(1);
                                }
                            }
                        },{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: '至',
                            labelWidth: 15,
                            width: 105,
                            format: 'Y-m-d ',
                            id: 'ElectricAnalysisDailyDetailsEndDate_Id',
                            value: new Date(),
                            listeners: {
                            	select: function (combo, record, index) {
                            		Ext.getCmp("ElectricAnalysisDailyDetails_Id").getStore().loadPage(1);
                                }
                            }
                        },'-', {
                            xtype: 'button',
                            text: cosog.string.exportExcel,
                            pressed: true,
                            hidden:true,
                            handler: function (v, o) {
                            }
                        }, '->', {
                            xtype: 'button',
                            text:'单井历史',
                            tooltip:'点击按钮或者双击表格，查看单井历史数据',
                            id:'ElectricAnalysisDailyDetailsHisBtn_Id',
                            pressed: true,
                            hidden: false,
                            handler: function (v, o) {
                        		if(Ext.getCmp("ElectricAnalysisDailyDetails_Id").getSelectionModel().getSelection().length>0){
                        			Ext.getCmp("ElectricAnalysisDailyDetailsAllBtn_Id").show();
                            		Ext.getCmp("ElectricAnalysisDailyDetailsHisBtn_Id").hide();
                            		Ext.getCmp("ElectricAnalysisDailyDetailsDate_Id").hide();
                            		Ext.getCmp("ElectricAnalysisDailyDetailsStartDate_Id").show();
                            		Ext.getCmp("ElectricAnalysisDailyDetailsEndDate_Id").show();
                            		
                        			var record  = Ext.getCmp("ElectricAnalysisDailyDetails_Id").getSelectionModel().getSelection()[0];
                            		Ext.getCmp('electricAnalysisDailyDetailsWellCom_Id').setValue(record.data.wellName);
                                	Ext.getCmp('electricAnalysisDailyDetailsWellCom_Id').setRawValue(record.data.wellName);
                                	Ext.getCmp('ElectricAnalysisDailyDetails_Id').getStore().loadPage(1);
                        		}
                            }
                      }, {
                          xtype: 'button',
                          text:'返回统计',
                          id:'ElectricAnalysisDailyDetailsAllBtn_Id',
                          pressed: true,
                          hidden: true,
                          handler: function (v, o) {
                        	  Ext.getCmp("ElectricAnalysisDailyDetailsAllBtn_Id").hide();
                        	  Ext.getCmp("ElectricAnalysisDailyDetailsHisBtn_Id").show();
                        	  Ext.getCmp("ElectricAnalysisDailyDetailsDate_Id").show();
                        	  Ext.getCmp("ElectricAnalysisDailyDetailsStartDate_Id").hide();
                        	  Ext.getCmp("ElectricAnalysisDailyDetailsEndDate_Id").hide();
                        	  
                        	  Ext.getCmp("electricAnalysisDailyDetailsWellCom_Id").setValue('');
                        	  
                        	  Ext.getCmp("ElectricAnalysisDailyDetails_Id").getStore().loadPage(1);
                          }
                      },{
                          id: 'ElectricAnalysisDailyDetailsCurveItem_Id', //选择查看曲线的数据项名称
                          xtype: 'textfield',
                          value: '',
                          hidden: true
                      }, {
                          id: 'ElectricAnalysisDailyDetailsCurveItemCode_Id', //选择查看曲线的数据项代码
                          xtype: 'textfield',
                          value: '',
                          hidden: true
                      }],
                     listeners: {
                     }
                },
                    {
                        region: 'east',
                        id: 'ElectricAnalysisDailyDetailsDataPanel_Id',
                        width: '65%',
                        title: '单井详情',
                        collapsible: true, // 是否折叠
                        split: true, // 竖折叠条
                        border: false,
                        layout: {
                            type: 'hbox',
                            pack: 'start',
                            align: 'stretch'
                        },
                        items: [
                            {
                                border: false,
                                flex: 1,
                                height: 900,
                                margin: '0 0 0 0',
                                padding: 0,
                                autoScroll: true,
                                scrollable: true,
                                layout: {
                                    type: 'hbox',
                                    pack: 'start',
                                    align: 'stretch'
                                },
                                items: [
                                    {
                                        border: false,
                                        margin: '0 0 0 0',
                                        flex: 1,
                                        height: 900,
                                        autoScroll: true,
                                        scrollable: true,
                                        layout: {
                                            type: 'vbox',
                                            pack: 'start',
                                            align: 'stretch'
                                        },
                                        items: [{
                                            border: false,
                                            margin: '0 0 1 0',
                                            height: 300,
                                            layout: 'fit',
                                            id: 'ElectricAnalysisDailyFSDiagramOverlayPanel_Id',
                                            html: '<div id="ElectricAnalysisDailyFSDiagramOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#ElectricAnalysisDailyFSDiagramOverlayDiv_Id").highcharts() != undefined && $("#ElectricAnalysisDailyFSDiagramOverlayDiv_Id").highcharts() != null) {
                                                        $("#ElectricAnalysisDailyFSDiagramOverlayDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }
                                                }
                                            }
                                     }, {
                                            border: false,
                                            margin: '0 0 1 0',
                                            height: 300,
                                            layout: 'fit',
                                            id: 'ElectricAnalysisDailyPSDiagramOverlayPanel_Id',
                                            html: '<div id="ElectricAnalysisDailyPSDiagramOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#ElectricAnalysisDailyPSDiagramOverlayDiv_Id").highcharts() != undefined && $("#ElectricAnalysisDailyPSDiagramOverlayDiv_Id").highcharts() != null) {
                                                        $("#ElectricAnalysisDailyPSDiagramOverlayDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }
                                                }
                                            }
                                     }, {
                                            border: false,
                                            margin: '0 0 0 0',
                                            height: 300,
                                            layout: 'fit',
                                            id: 'ElectricAnalysisDailyASDiagramOverlayPanel_Id',
                                            html: '<div id="ElectricAnalysisDailyASDiagramOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#ElectricAnalysisDailyASDiagramOverlayDiv_Id").highcharts() != undefined && $("#ElectricAnalysisDailyASDiagramOverlayDiv_Id").highcharts() != null) {
                                                        $("#ElectricAnalysisDailyASDiagramOverlayDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }
                                                }
                                            }
                                     }]
                                 }
                                ]
                            },
                            {
                                border: false,
                                flex: 2,
                                height: 900,
                                margin: '0 0 0 0',
                                padding: 0,
                                autoScroll: true,
                                scrollable: true,
                                collapsible: true,
                                header: false,
                                collapseDirection: 'right',
                                split: true,
                                layout: {
                                    type: 'hbox',
                                    pack: 'start',
                                    align: 'stretch'
                                },
                                items: [
                                    {
                                        border: false,
                                        flex: 1,
                                        height: 900,
                                        autoScroll: true,
                                        scrollable: true,
                                        layout: 'fit',
                                        id: 'ElectricAnalysisDailyFSDiagramOverlayTable_Id'
                                    },
                                    {
                                        xtype: 'tabpanel',
                                        id: 'ElectricAnalysisDailyDetailsRightTabPanel_Id',
                                        flex: 1,
                                        activeTab: 0,
                                        header: false,
                                        collapsible: true,
                                        split: true,
                                        collapseDirection: 'right',
                                        border: true,
                                        tabPosition: 'top',
                                        items: [
                                            {
                                                title: '分析',
                                                layout: 'border',
                                                items:[
                                                	{
                                                		region: 'center',
                                                		id: 'ElectricAnalysisDailyDetailsRightAnalysisPanel_Id',
                                                        border: false,
                                                        layout: 'fit',
                                                        height:'60%'
//                                                        collapseDirection:'top'
                                                	},{
                                                		region: 'south',
                                                		height:'40%',
                                                		border: false,
                                                		header: false,
                                                        collapsible:true,
                                                        split: true,
                                                		xtype:'form',
                                                		layout: 'auto',
                                                		items: [{
                                                			xtype:'label',
                                                			text:'运行区间:',
                                                			margin:'0 0 20 0'
                                                		},{
                                                        	xtype:'textareafield',
                                                        	id:'ElectricAnalysisDailyDetailsRightRunRangeTextArea_Id',
                                                        	grow:true,
                                                        	width:'99.9%',
                                                            height: '45%',
                                                            readOnly:true
                                                        },{
                                                			xtype:'label',
                                                			text:'工况累计:',
                                                			margin:'200 0 20 0'
                                                		},{
                                                        	xtype:'textareafield',
                                                        	id:'ElectricAnalysisDailyDetailsRightResultCodeTextArea_Id',
                                                        	grow:true,
                                                        	width:'99.9%',
                                                        	height: '45%',
                                                            readOnly:true
                                                        }]
                                                	}
                                                ]
                                            }, {
                                                title: '采集',
                                                id: 'ElectricAnalysisDailyDetailsRightAcqPanel_Id',
                                                border: false,
                                                layout: 'fit',
                                                autoScroll: true,
                                                scrollable: true
                                            }]
                                    }
                                ]
                            }
                        ],
                        listeners: {
                        	beforeCollapse: function (panel, eOpts) {
                                $("#ElectricAnalysisDailyFSDiagramOverlayDiv_Id").hide();
                                $("#ElectricAnalysisDailyPSDiagramOverlayDiv_Id").hide();
                                $("#ElectricAnalysisDailyASDiagramOverlayDiv_Id").hide();
                            },
                            expand: function (panel, eOpts) {
                            	$("#ElectricAnalysisDailyFSDiagramOverlayDiv_Id").show();
                                $("#ElectricAnalysisDailyPSDiagramOverlayDiv_Id").show();
                                $("#ElectricAnalysisDailyASDiagramOverlayDiv_Id").show();
                            }
                        }
                        }]
                }]
        });
        me.callParent(arguments);
    }

});

function createElecAnalysisDailyDetailsTableColumn(columnInfo) {
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
        if (attr.dataIndex.toUpperCase()=='workingConditionName_Elec'.toUpperCase()||attr.dataIndex.toUpperCase()=='workingConditionName_E'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceElecWorkingConditionColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='iDegreeBalanceLevel'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='wattDegreeBalanceLevel'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return advicePowerBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:true";
        } else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += width_ + ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        } else if (attr.dataIndex.toUpperCase()=='acquisitionTime'.toUpperCase()) {
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

ElectricAnalysisDailyHistoryCurveChartFn = function (get_rawData, itemName, divId) {
    var tickInterval = 1;
    var data = get_rawData.totalRoot;
    tickInterval = Math.floor(data.length / 10) + 1;
    var catagories = "[";
    var title = get_rawData.wellName + "井" + itemName.split("(")[0] + "曲线";
    for (var i = 0; i < data.length; i++) {
        catagories += "\"" + data[i].calculateDate + "\"";
        if (i < data.length - 1) {
            catagories += ",";
        }
    }
    catagories += "]";
    var legendName = [itemName.split("(")[0]];
    if (get_rawData.itemNum > 1) {
//        legendName = [itemName.split("(")[0], itemName.split("(")[0] + "最大值", itemName.split("(")[0] + "最小值"];
        legendName = ["最大值","最小值","平均值"];
    }
    var series = "[";
    for (var i = 0; i < legendName.length; i++) {
        series += "{\"name\":\"" + legendName[i] + "\",";
        series += "\"data\":[";
        for (var j = 0; j < data.length; j++) {
            var year = parseInt(data[j].calculateDate.split(" ")[0].split("-")[0]);
            var month = parseInt(data[j].calculateDate.split(" ")[0].split("-")[1]);
            var day = parseInt(data[j].calculateDate.split(" ")[0].split("-")[2]);
            if (i == 0) {
            	if(legendName.length==1){
            		series += "[" + Date.parse(data[j].calculateDate.replace(/-/g, '/')) + "," + data[j].value + "]";
            	}else{
            		series += "[" + Date.parse(data[j].calculateDate.replace(/-/g, '/')) + "," + data[j].maxValue + "]";
            	}
            	
            } else if (i == 1) {
            	series += "[" + Date.parse(data[j].calculateDate.replace(/-/g, '/')) + "," + data[j].minValue + "]";
            } else if (i == 2) {
            	series += "[" + Date.parse(data[j].calculateDate.replace(/-/g, '/')) + "," + data[j].value + "]";
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
    var cat = Ext.JSON.decode(catagories);
    var ser = Ext.JSON.decode(series);
    var color = ['#800000', // 红
       '#008C00', // 绿
       '#000000', // 黑
       '#0000FF', // 蓝
       '#F4BD82', // 黄
       '#FF00FF' // 紫
     ];

    initElectricAnalysisDailyHistoryCurveChartFn(cat, ser, tickInterval, divId, title, "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "时间", itemName, color);

    return false;
};

function initElectricAnalysisDailyHistoryCurveChartFn(catagories, series, tickInterval, divId, title, subtitle, xtitle, ytitle, color) {
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
            //							min:0,
            //max:200,
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
                    //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
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
            enabled: true,
            borderWidth: 0
        },
        series: series
    });
};