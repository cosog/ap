var videoPlayrHelper={rpc:{},pcp:{}};
var accessTokenInfo=null;
var demoAccessTokenInfo=null;
Ext.define("AP.view.realTimeMonitoring.RealTimeMonitoringInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.realTimeMonitoringInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var RPCRealTimeMonitoringInfoView = Ext.create('AP.view.realTimeMonitoring.RPCRealTimeMonitoringInfoView');
        var PCPRealTimeMonitoringInfoView = Ext.create('AP.view.realTimeMonitoring.PCPRealTimeMonitoringInfoView');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"RealTimeMonitoringTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		tabBar:{
                	items: [{
                        xtype: 'tbfill'
                    },{
                		xtype: 'button',
                        id:"CPUUsedPercentLabel_id",
//                        width: 180,
                        text: 'CPU:',
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue("CPU使用率(%)");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("cpuUsedPercent");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"memUsedPercentLabel_id",
                        text: '内存:',
//                        width: 130,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue("内存使用率(%)");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("memUsedPercent");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"tableSpaceSizeProbeLabel_id",
                        text: '表空间:',
//                        width: 130,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue("表空间使用率(%)");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("tableSpaceSize");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"jedisRunStatusProbeLabel_id",
                        text: 'redis',
//                        width: 100,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue("实时数据库运行状态");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("jedisStatus");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"adRunStatusProbeLabel_id",
                        text: 'ad',
//                        width: 100,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue("ad运行状态");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("adRunStatus");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"adLicenseStatusProbeLabel_id",
                        text: 'License超限:',
                        hidden: true,
                        handler: function (v, o) {}
                	},{
                		xtype: 'button',
                        id:"acRunStatusProbeLabel_id",
                        text: 'ac',
//                        width: 100,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue("ac运行状态");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("acRunStatus");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                        id: 'ResourceMonitoringCurveItem_Id',
                        xtype: 'textfield',
                        value: '',
                        hidden: true
                    },{
                        id: 'ResourceMonitoringCurveItemCode_Id',
                        xtype: 'textfield',
                        value: '',
                        hidden: true
                    }]
                },
        		items: [{
        				title: '抽油机井',
        				id:'RPCRealTimeMonitoringInfoPanel_Id',
        				items: [RPCRealTimeMonitoringInfoView],
        				layout: "fit",
        				border: false
        			},{
        				title: '螺杆泵井',
        				id:'PCPRealTimeMonitoringInfoPanel_Id',
        				items: [PCPRealTimeMonitoringInfoView],
        				layout: "fit",
        				hidden: pcpHidden,
        				border: false
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        					if(newCard.id=="RPCRealTimeMonitoringInfoPanel_Id"){
        						if(videoPlayrHelper.pcp.player1!=null && videoPlayrHelper.pcp.player1.pluginStatus.state.play){
                    				videoPlayrHelper.pcp.player1.stop();
                    			}
                    			if(videoPlayrHelper.pcp.player2!=null && videoPlayrHelper.pcp.player2.pluginStatus.state.play){
                    				videoPlayrHelper.pcp.player2.stop();
                    			}
                    			
        						var statTabActiveId = Ext.getCmp("RPCRealTimeMonitoringStatTabPanel").getActiveTab().id;
        						if(statTabActiveId=="RPCRealTimeMonitoringFESdiagramResultStatGraphPanel_Id"){
        							loadAndInitFESdiagramResultStat(true);
        						}else if(statTabActiveId=="RPCRealTimeMonitoringStatGraphPanel_Id"){
        							loadAndInitCommStatusStat(true);
        						}else if(statTabActiveId=="RPCRealTimeMonitoringRunStatusStatGraphPanel_Id"){
            						loadAndInitRunStatusStat(true);
            					}else if(statTabActiveId=="RPCRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
        							loadAndInitDeviceTypeStat(true);
        						}
        						Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setValue('');
        						Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setRawValue('');
        						var gridPanel = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getSelectionModel().deselectAll(true);
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore');
        						}
        					}else if(newCard.id=="PCPRealTimeMonitoringInfoPanel_Id"){
        						if(videoPlayrHelper.rpc.player1!=null && videoPlayrHelper.rpc.player1.pluginStatus.state.play){
                    				videoPlayrHelper.rpc.player1.stop();
                    			}
                    			if(videoPlayrHelper.rpc.player2!=null && videoPlayrHelper.rpc.player2.pluginStatus.state.play){
                    				videoPlayrHelper.rpc.player2.stop();
                    			}
                    			
        						var statTabActiveId = Ext.getCmp("PCPRealTimeMonitoringStatTabPanel").getActiveTab().id;
        						if(statTabActiveId=="PCPRealTimeMonitoringStatGraphPanel_Id"){
        							loadAndInitCommStatusStat(true);
        						}else if(statTabActiveId=="PCPRealTimeMonitoringRunStatusStatGraphPanel_Id"){
            						loadAndInitRunStatusStat(true);
            					}else if(statTabActiveId=="PCPRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
        							loadAndInitDeviceTypeStat(true);
        						}
        						Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').setValue('');
        						Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').setRawValue('');
        						var gridPanel = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getSelectionModel().deselectAll(true);
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringWellListStore');
        						}
        					}
        				}
        			}
            	}]
        });
        me.callParent(arguments);
    }

});

function createRealTimeMonitoringStatColumn(columnInfo) {
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
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }
        else if (attr.dataIndex.toUpperCase()=='count'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceStatTableCommStatusColor(value,o,p,e);}";
        }
        else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
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

function createRealTimeMonitoringColumn(columnInfo) {
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
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:true";
        }
        else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } 
        else if (attr.dataIndex.toUpperCase()=='resultName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceResultStatusColor(value,o,p,e);}";
        }
        else {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRealtimeMonitoringDataColor(value,o,p,e);}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

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


function exportRealTimeMonitoringDataExcel(orgId,deviceType,deviceName,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,fileName,title,columnStr) {
    var url = context + '/realTimeMonitoringController/exportDeviceRealTimeOverviewDataExcel';
    var fields = "";
    var heads = "";
    var lockedheads = "";
    var unlockedheads = "";
    var lockedfields = "";
    var unlockedfields = "";
    var columns_ = Ext.JSON.decode(columnStr);
    
    Ext.Array.each(columns_, function (name, index, countriesItSelf) {
        var column = columns_[index];
        if (index > 0 && !column.hidden) {
        	if(column.locked){
        		lockedfields += column.dataIndex + ",";
        		lockedheads += column.text + ",";
        	}else{
        		unlockedfields += column.dataIndex + ",";
        		unlockedheads += column.text + ",";
        	}
            
        }
    });
    if (isNotVal(lockedfields)) {
    	lockedfields = lockedfields.substring(0, lockedfields.length - 1);
    	lockedheads = lockedheads.substring(0, lockedheads.length - 1);
    }
    if (isNotVal(unlockedfields)) {
    	unlockedfields = unlockedfields.substring(0, unlockedfields.length - 1);
    	unlockedheads = unlockedheads.substring(0, unlockedheads.length - 1);
    }
    fields = "id," + lockedfields+","+unlockedfields;
    heads = "序号," + lockedheads+","+unlockedheads;
    fields="";
    heads="";
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&FESdiagramResultStatValue=" + URLencode(URLencode(FESdiagramResultStatValue))
    + "&commStatusStatValue=" + URLencode(URLencode(commStatusStatValue))
    + "&runStatusStatValue=" + URLencode(URLencode(runStatusStatValue))
    + "&deviceTypeStatValue=" + URLencode(URLencode(deviceTypeStatValue))
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};



function gotoDeviceHistory(deviceName,deviceType){
	Ext.getCmp("realtimeTurnToHisyorySign_Id").setValue('true');//跳转标志
	var tabPanel = Ext.getCmp("frame_center_ids");
	var moduleId="DeviceHistoryQuery";
	var getTabId = tabPanel.getComponent(moduleId);
	var haveModule=false;
	if(!getTabId){
		var moduleStore=Ext.getCmp("MainIframeView_Id").getStore();
		var moduleCount=moduleStore.getCount();
		for(var i=0;i<moduleCount;i++){
			var rec=moduleStore.getAt(i);
			if(rec.isLeaf()&&rec.id=="DeviceHistoryQuery"){
				tabPanel.add(Ext.create(rec.data.viewsrc, {
                    id: rec.data.id,
                    closable: true,
                    iconCls: rec.data.md_icon,
                    closeAction: 'destroy',
                    title: rec.data.text,
                    listeners: {
                        afterrender: function () {
                        },
                        delay: 150
                    }
                })).show();
                Ext.getCmp("topModule_Id").setValue(rec.data.mdCode);
				haveModule=true;
				break;
			}
		}
	}else{
		haveModule=true;
	}
	if(haveModule){
		if(deviceType===0){
			Ext.getCmp('HistoryQueryRPCDeviceListComb_Id').setValue(deviceName);
			var historyGridPanel=Ext.getCmp("RPCHistoryQueryListGridPanel_Id");
			tabPanel.setActiveTab(moduleId);
			var historyQueryTabPanel = Ext.getCmp("HistoryQueryTabPanel");
			if(historyQueryTabPanel.getActiveTab().id=="PCPHistoryQueryInfoPanel_Id"){
				historyQueryTabPanel.setActiveTab("RPCHistoryQueryInfoPanel_Id");
			}
		}if(deviceType===1){
			Ext.getCmp('HistoryQueryPCPDeviceListComb_Id').setValue(deviceName);
			var historyGridPanel=Ext.getCmp("PCPHistoryQueryListGridPanel_Id");
			tabPanel.setActiveTab(moduleId);
			var historyQueryTabPanel = Ext.getCmp("HistoryQueryTabPanel");
			if(historyQueryTabPanel.getActiveTab().id=="RPCHistoryQueryInfoPanel_Id"){
				historyQueryTabPanel.setActiveTab("PCPHistoryQueryInfoPanel_Id");
			}
		}
	}
}

function loadAndInitFESdiagramResultStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var deviceTypeStatValue='';
	var deviceTypeStatValue='';
	deviceType=0;
	if(all){
		Ext.getCmp("RPCRealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("RPCRealTimeMonitoringStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").setValue('');
		commStatusStatValue='';
		deviceTypeStatValue='';
	}else{
		deviceTypeStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").getValue();
		commStatusStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").getValue();
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringFESDiagramResultStatPieOrColChat(result);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType,
			commStatusStatValue:commStatusStatValue,
			deviceTypeStatValue:deviceTypeStatValue
        }
	});
}

function initRealTimeMonitoringFESDiagramResultStatPieOrColChat(get_rawData) {
	var divid="RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id";
	
	var title="工况诊断";
	var datalist=get_rawData.totalRoot;
	
	var pieData=[];
	for(var i=0;i<datalist.length;i++){
		var singleData={};
		singleData.name=datalist[i].item;
		singleData.y=datalist[i].count;
		pieData.push(singleData);
	}
	
	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	var colors=[];
	
	ShowRealTimeMonitoringFESDiagramResultStatPieOrColChat(title,divid, "设备数占", pieData,colors);
};

function ShowRealTimeMonitoringFESDiagramResultStatPieOrColChat(title,divid, name, data,colors) {
	Highcharts.chart(divid, {
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		credits : {
			enabled : false
		},
		title : {
			text : title
		},
//		colors : colors,
		tooltip : {
			pointFormat : '设备数: <b>{point.y}</b> 占: <b>{point.percentage:.1f}%</b>'
		},
		legend : {
			align : 'center',//center left
			verticalAlign : 'bottom',//bottom middle
			layout : 'horizontal' //vertical 竖直 horizontal-水平
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.y}台'
				},
				events: {
					click: function(e) {
						var statSelectFESdiagramResultId="RPCRealTimeMonitoringStatSelectFESdiagramResult_Id";
						var deviceListComb_Id="RealTimeMonitoringRPCDeviceListComb_Id";
						var gridPanel_Id="RPCRealTimeMonitoringListGridPanel_Id";
						var store="AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore";
						
						
						if(!e.point.selected){//如果没被选中,则本次是选中
							Ext.getCmp(statSelectFESdiagramResultId).setValue(e.point.name);
						}else{//取消选中
							Ext.getCmp(statSelectFESdiagramResultId).setValue('');
						}
						
						Ext.getCmp(deviceListComb_Id).setValue('');
						Ext.getCmp(deviceListComb_Id).setRawValue('');
						var gridPanel = Ext.getCmp(gridPanel_Id);
						if (isNotVal(gridPanel)) {
							gridPanel.getSelectionModel().deselectAll(true);
							gridPanel.getStore().load();
						}else{
							Ext.create(store);
						}
					}
				},
				showInLegend : true
			}
		},
		exporting:{ 
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
		},
		series : [{
					type : 'pie',
					name : name,
					data : data
				}]
		});
};


function loadAndInitCommStatusStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var deviceTypeStatValue='';
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
	if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
		deviceType=0;
		if(all){
			Ext.getCmp("RPCRealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectRunStatus_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").setValue('');
			deviceTypeStatValue='';
		}else{
			deviceTypeStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").getValue();
		}
	}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
		deviceType=1;
		if(all){
			Ext.getCmp("PCPRealTimeMonitoringStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("PCPRealTimeMonitoringStatSelectDeviceType_Id").setValue('');
			deviceTypeStatValue='';
		}else{
			deviceTypeStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectDeviceType_Id").getValue();
		}
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringStatPieOrColChat(result);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType,
			deviceTypeStatValue:deviceTypeStatValue
        }
	});
}


function initRealTimeMonitoringStatPieOrColChat(get_rawData) {
	var divid="RPCRealTimeMonitoringStatGraphPanelPieDiv_Id";
	var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
		divid="RPCRealTimeMonitoringStatGraphPanelPieDiv_Id";
	}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
		divid="PCPRealTimeMonitoringStatGraphPanelPieDiv_Id";
	}
	var title="通信状态";
	var datalist=get_rawData.totalRoot;
	var colors=[];
	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	var pieData=[];
	for(var i=0;i<datalist.length;i++){
		if(datalist[i].itemCode!='all'){
			if(datalist[i].count>0){
				var singleData={};
				singleData.name=datalist[i].item;
				singleData.y=datalist[i].count;
				
				if(datalist[i].itemCode=='online'){
					singleData.color='#'+alarmShowStyle.Comm.online.Color;
				}else if(datalist[i].itemCode=='goOnline'){
					singleData.color='#'+alarmShowStyle.Comm.goOnline.Color;
				}else if(datalist[i].itemCode=='offline'){
					singleData.color='#'+alarmShowStyle.Comm.offline.Color;
				}
				pieData.push(singleData);
			}
		}
	}
	ShowRealTimeMonitoringStatPieOrColChat(title,divid, "设备数占", pieData,colors);
};

function ShowRealTimeMonitoringStatPieOrColChat(title,divid, name, data,colors) {
	Highcharts.chart(divid, {
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		credits : {
			enabled : false
		},
		title : {
			text : title
		},
		colors : colors,
		tooltip : {
			pointFormat : '设备数: <b>{point.y}</b> 占: <b>{point.percentage:.1f}%</b>'
		},
		legend : {
			align : 'center',
			verticalAlign : 'bottom',
			layout : 'horizontal' //vertical 竖直 horizontal-水平
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.y}台'
				},
				events: {
					click: function(e) {
						var statSelectCommStatusId="RPCRealTimeMonitoringStatSelectCommStatus_Id";
						var deviceListComb_Id="RealTimeMonitoringRPCDeviceListComb_Id";
						var gridPanel_Id="RPCRealTimeMonitoringListGridPanel_Id";
						var store="AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore";
						var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
						if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
							statSelectCommStatusId="RPCRealTimeMonitoringStatSelectCommStatus_Id";
							deviceListComb_Id="RealTimeMonitoringRPCDeviceListComb_Id";
							gridPanel_Id="RPCRealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore";
						}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
							statSelectCommStatusId="PCPRealTimeMonitoringStatSelectCommStatus_Id";
							deviceListComb_Id="RealTimeMonitoringPCPDeviceListComb_Id";
							gridPanel_Id="PCPRealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.PCPRealTimeMonitoringWellListStore";
						}
						
						if(!e.point.selected){//如果没被选中,则本次是选中
							Ext.getCmp(statSelectCommStatusId).setValue(e.point.name);
						}else{//取消选中
							Ext.getCmp(statSelectCommStatusId).setValue('');
						}
						
						Ext.getCmp(deviceListComb_Id).setValue('');
						Ext.getCmp(deviceListComb_Id).setRawValue('');
						var gridPanel = Ext.getCmp(gridPanel_Id);
						if (isNotVal(gridPanel)) {
							gridPanel.getSelectionModel().deselectAll(true);
							gridPanel.getStore().load();
						}else{
							Ext.create(store);
						}
					}
				},
				showInLegend : true
			}
		},
		exporting:{ 
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
		},
		series : [{
					type : 'pie',
					name : name,
					data : data
				}]
		});
};

function loadAndInitRunStatusStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var deviceTypeStatValue='';
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
	if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
		deviceType=0;
		if(all){
			Ext.getCmp("RPCRealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectRunStatus_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").setValue('');
		}else{
			deviceTypeStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").getValue();
		}
	}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
		deviceType=1;
		if(all){
			Ext.getCmp("PCPRealTimeMonitoringStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("PCPRealTimeMonitoringStatSelectRunStatus_Id").setValue('');
			Ext.getCmp("PCPRealTimeMonitoringStatSelectDeviceType_Id").setValue('');
			deviceTypeStatValue='';
		}else{
			deviceTypeStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectDeviceType_Id").getValue();
		}
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringRunStatusStatPieOrColChat(result);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}


function initRealTimeMonitoringRunStatusStatPieOrColChat(get_rawData) {
	var divid="RPCRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id";
	var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
		divid="RPCRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id";
	}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
		divid="PCPRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id";
	}
	var title="运行状态";
	var datalist=get_rawData.totalRoot;
	var colors=[];
	var alarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
	var pieData=[];
	for(var i=0;i<datalist.length;i++){
		if(datalist[i].itemCode!='all'){
			if(datalist[i].count>0){
				var singleData={};
				singleData.name=datalist[i].item;
				singleData.y=datalist[i].count;
				
				if(datalist[i].itemCode=='run'){
					singleData.color='#'+alarmShowStyle.Run.run.Color;
				}else if(datalist[i].itemCode=='stop'){
					singleData.color='#'+alarmShowStyle.Run.stop.Color;
				}else if(datalist[i].itemCode=='offline'){
					singleData.color='#'+alarmShowStyle.Comm.offline.Color;
				}
				pieData.push(singleData);
			}
		}
	}
	ShowRealTimeMonitoringRunStatusStatPieOrColChat(title,divid, "设备数占", pieData,colors);
};

function ShowRealTimeMonitoringRunStatusStatPieOrColChat(title,divid, name, data,colors) {
	Highcharts.chart(divid, {
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		credits : {
			enabled : false
		},
		title : {
			text : title
		},
		colors : colors,
		tooltip : {
			pointFormat : '设备数: <b>{point.y}</b> 占: <b>{point.percentage:.1f}%</b>'
		},
		legend : {
			align : 'center',
			verticalAlign : 'bottom',
			layout : 'horizontal' //vertical 竖直 horizontal-水平
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.y}台'
				},
				events: {
					click: function(e) {
						var statSelectRunStatusId="RPCRealTimeMonitoringStatSelectRunStatus_Id";
						var deviceListComb_Id="RealTimeMonitoringRPCDeviceListComb_Id";
						var gridPanel_Id="RPCRealTimeMonitoringListGridPanel_Id";
						var store="AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore";
						var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
						if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
							statSelectRunStatusId="RPCRealTimeMonitoringStatSelectRunStatus_Id";
							deviceListComb_Id="RealTimeMonitoringRPCDeviceListComb_Id";
							gridPanel_Id="RPCRealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore";
						}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
							statSelectRunStatusId="PCPRealTimeMonitoringStatSelectRunStatus_Id";
							deviceListComb_Id="RealTimeMonitoringPCPDeviceListComb_Id";
							gridPanel_Id="PCPRealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.PCPRealTimeMonitoringWellListStore";
						}
						
						if(!e.point.selected){//如果没被选中,则本次是选中
							Ext.getCmp(statSelectRunStatusId).setValue(e.point.name);
						}else{//取消选中
							Ext.getCmp(statSelectRunStatusId).setValue('');
						}
						
						Ext.getCmp(deviceListComb_Id).setValue('');
						Ext.getCmp(deviceListComb_Id).setRawValue('');
						var gridPanel = Ext.getCmp(gridPanel_Id);
						if (isNotVal(gridPanel)) {
							gridPanel.getSelectionModel().deselectAll(true);
							gridPanel.getStore().load();
						}else{
							Ext.create(store);
						}
					}
				},
				showInLegend : true
			}
		},
		exporting:{ 
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
		},
		series : [{
					type : 'pie',
					name : name,
					data : data
				}]
		});
};

function loadAndInitDeviceTypeStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var commStatusStatValue='';
	
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
	if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
		deviceType=0;
		if(all){
			Ext.getCmp("RPCRealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectRunStatus_Id").setValue('');
			Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").setValue('');
			commStatusStatValue='';
		}else{
			commStatusStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").getValue();
		}
		
	}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
		deviceType=1;
		if(all){
			Ext.getCmp("PCPRealTimeMonitoringStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("PCPRealTimeMonitoringStatSelectCommStatus_Id").setValue('');
			commStatusStatValue='';
		}else{
			commStatusStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectCommStatus_Id").getValue();
		}
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringDeviceTypeStatData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringDeviceTypeStatPieOrColChat(result);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType,
			commStatusStatValue:commStatusStatValue
        }
	});
};

function initRealTimeMonitoringDeviceTypeStatPieOrColChat(get_rawData) {
	var divid="RPCRealTimeMonitoringDeviceTypeStatPieDiv_Id";
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
	if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
		divid="RPCRealTimeMonitoringDeviceTypeStatPieDiv_Id";
	}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
		divid="PCPRealTimeMonitoringDeviceTypeStatPieDiv_Id";
	}
	var title="设备类型";
	var datalist=get_rawData.totalRoot;
	
	var pieDataStr="[";
	for(var i=0;i<datalist.length;i++){
		pieDataStr+="['"+datalist[i].item+"',"+datalist[i].count+"],";
	}
	
	if(stringEndWith(pieDataStr,",")){
		pieDataStr = pieDataStr.substring(0, pieDataStr.length - 1);
	}
	pieDataStr+="]";
	var pieData = Ext.JSON.decode(pieDataStr);
	var colors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
	ShowRealTimeMonitoringDeviceTypeStatPieChat(title,divid, "设备数占", pieData,colors);
};

function ShowRealTimeMonitoringDeviceTypeStatPieChat(title,divid, name, data,colors) {
	Highcharts.chart(divid, {
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		credits : {
			enabled : false
		},
		title : {
			text : title
		},
		colors : colors,
		tooltip : {
			pointFormat : '设备数: <b>{point.y}</b> 占: <b>{point.percentage:.1f}%</b>'
		},
		legend : {
			align : 'center',
			verticalAlign : 'bottom',
			layout : 'horizontal' //vertical 竖直 horizontal-水平
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.y}台'
				},
				events: {
					click: function(e) {
						var statSelectDeviceType_Id="RPCRealTimeMonitoringStatSelectDeviceType_Id";
						var deviceListComb_Id="RealTimeMonitoringRPCDeviceListComb_Id";
						var gridPanel_Id="RPCRealTimeMonitoringListGridPanel_Id";
						var store="AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore";
						var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
						if(activeId=="RPCRealTimeMonitoringInfoPanel_Id"){
							statSelectDeviceType_Id="RPCRealTimeMonitoringStatSelectDeviceType_Id";
							deviceListComb_Id="RealTimeMonitoringRPCDeviceListComb_Id";
							gridPanel_Id="RPCRealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore";
						}else if(activeId=="PCPRealTimeMonitoringInfoPanel_Id"){
							statSelectDeviceType_Id="PCPRealTimeMonitoringStatSelectDeviceType_Id";
							deviceListComb_Id="RealTimeMonitoringPCPDeviceListComb_Id";
							gridPanel_Id="PCPRealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.PCPRealTimeMonitoringWellListStore";
						}
						
						if(!e.point.selected){//如果没被选中,则本次是选中
							Ext.getCmp(statSelectDeviceType_Id).setValue(e.point.name);
						}else{//取消选中
							Ext.getCmp(statSelectDeviceType_Id).setValue('');
						}
						
						Ext.getCmp(deviceListComb_Id).setValue('');
						Ext.getCmp(deviceListComb_Id).setRawValue('');
						var gridPanel = Ext.getCmp(gridPanel_Id);
						if (isNotVal(gridPanel)) {
							gridPanel.getSelectionModel().deselectAll(true);
							gridPanel.getStore().load();
						}else{
							Ext.create(store);
						}
					}
				},
				showInLegend : true
			}
		},
		exporting:{ 
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
		},
		series : [{
					type : 'pie',
					name : name,
					data : data
				}]
		});
};

function deviceRealtimeMonitoringCurve(deviceType){
	var selectRowId="RPCRealTimeMonitoringInfoDeviceListSelectRow_Id";
	var gridPanelId="RPCRealTimeMonitoringListGridPanel_Id";
	var contentId="rpcRealTimeMonitoringCurveContent";
	var containerId="rpcRealTimeMonitoringCurveContainer";
	var divPrefix="rpcRealTimeMonitoringCurveDiv";
	var eastPanelId="RPCRealTimeMonitoringEastPanel_Id";
	if(deviceType==1){
		selectRowId="PCPRealTimeMonitoringInfoDeviceListSelectRow_Id";
		gridPanelId="PCPRealTimeMonitoringListGridPanel_Id";
		contentId="pcpRealTimeMonitoringCurveContent";
		containerId="pcpRealTimeMonitoringCurveContainer";
		divPrefix="pcpRealTimeMonitoringCurveDiv";
		eastPanelId="PCPRealTimeMonitoringEastPanel_Id";
	}
	
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(selectRow>=0){
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.wellName;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringCurveData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
		    var data = result.list;
		    var totals=result.curveCount;
		    var legendName =result.curveItems;
        	var colors=result.curveColors;
		    
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		   
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 2) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
            
		    var columnCount = 2;
		    var rowCount = (totals%columnCount==0)?(totals/columnCount):(parseInt(totals/columnCount)+1);
		    
		    var chartWidth2 = 50 + '%';
            var chartHeight2 = 50 + '%';
            
            if(totals==1){
            	chartWidth2 = 100 + '%';
                chartHeight2 = 100 + '%';
            }else if(totals==2){
            	chartWidth2 = 100 + '%';
                chartHeight2 = 50 + '%';
            }else{
            	chartWidth2 = 50 + '%';
                chartHeight2 = 50 + '%';
            }
            
            $('#'+containerId).html(''); // 将html内容清空
            var htmlResult = '';
            var divId = '';
		    
            if (totals > 0) {
            	//添加div
            	for(var i=0;i<totals;i++){
            		divId = divPrefix + i+"_Id";
                    htmlResult += '<div id=\"' + divId + '\"';
                    htmlResult += ' style="height:'+ chartHeight2 +';width:'+ chartWidth2 +';"';
                    htmlResult += '></div>';
            	}
                $('#'+containerId).append(htmlResult);
                var aa=($('#'+containerId)[0]);
                var bb=aa.childElementCount;
                var cc=aa.children;
                var dd=cc[0].id;
                //数据处理
                for(var i=0;i<totals;i++){
                	divId = divPrefix + i+"_Id";
                	var xTitle='';
                	var yTitle=legendName[i];
                	var title = result.deviceName+":"+legendName[i].split("(")[0] + "趋势曲线";
                	var subtitle='';
        		    var color=[];
        		    color.push(colors[i]);
        		    if(color[0]==''){
        		    	color[0]=defaultColors[i%10];
        		    }else{
        		    	color[0]='#'+colors[i];
        		    }
        		    var maxValue=null;
    		        var minValue=null;
    		        var allPositive=true;//全部是非负数
    		        var allNegative=true;//全部是负值
        		    
        		    var series = "[";
    		        series += "{\"name\":\"" + legendName[i] + "\"," 
    		        series += "\"data\":[";
    		        for (var j = 0; j < data.length; j++) {
    		        	series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + data[j].data[i] + "]";
    		            if (j != data.length - 1) {
    		                series += ",";
    		            }
    		            if(parseFloat(data[j].data[i])<0){
    		            	allPositive=false;
    		            }else if(parseFloat(data[j].data[i])>=0){
    		            	allNegative=false;
    		            }
    		        }
    		        series += "]}";
        		    series += "]";
        		    
        		    if(allNegative){
    		        	maxValue=0;
    		        }else if(allPositive){
    		        	minValue=0;
    		        }
        		    
        		    var ser = Ext.JSON.decode(series);
        		    
        		    var timeFormat='%H:%M';
        		    initDeviceRealtimeMonitoringStockChartFn(ser, tickInterval, divId, title, subtitle, xTitle, yTitle,color,false,true,false,timeFormat,maxValue,minValue);
                }
            }
            
            var eastPanel=Ext.getCmp(eastPanelId);
			if(eastPanel.collapsed){
            	var container=$('#'+containerId);
    			if(container!=undefined && container.length>0){
    				var containerChildren=container[0].children;
    				if(containerChildren!=undefined && containerChildren.length>0){
    					for(var i=0;i<containerChildren.length;i++){
    						$("#"+containerChildren[i].id).hide(); 
    					}
    				}
    			}
            }
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			deviceType:deviceType
        }
	});
};

function initDeviceRealtimeMonitoringStockChartFn(series, tickInterval, divId, title, subtitle, xtitle,yTitle, color,legend,navigator,scrollbar,timeFormat,maxValue,minValue) {
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    var mychart = new Highcharts.stockChart({
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
        navigator: {
    		enabled: navigator
    	},
    	scrollbar: {
    		enabled: scrollbar
    	},
        rangeSelector: {
    		buttons: [{
    			count: 1,
    			type: 'hour',//minute hour week month all
    			text: '1小时'
    		}, {
    			count: 6,
    			type: 'hour',
    			text: '6小时'
    		}, {
    			count: 12,
    			type: 'hour',
    			text: '12小时'
    		}, {
    			type: 'all',
    			text: '全部'
    		}],
    		inputEnabled: false,
    		selected: 0
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
//            tickInterval: tickInterval,
            tickPixelInterval:tickInterval,
            labels: {
                formatter: function () {
                    return Highcharts.dateFormat(timeFormat, this.value);
                },
                autoRotation:true,//自动旋转
                rotation: -45 //倾斜度，防止数量过多显示不全  
//                step: 2
            }
        },
        yAxis: {
        	max:maxValue,
    		min:minValue,
        	lineWidth: 1,
            title: {
                text: yTitle,
                style: {
//                    color: color,
                }
            },
            labels: {
            	style: {
//                    color: color,
                }
            },
            opposite:false
      },
        tooltip: {
            crosshairs: true, //十字准线
            shared: true,
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
            layout: 'horizontal',//horizontal水平 vertical 垂直
            align: 'center',  //left，center 和 right
            verticalAlign: 'bottom',//top，middle 和 bottom
            enabled: legend,
            borderWidth: 0
        },
        series: series
    });
};

//function createVideo(playrHelper,deviceType,data,videoNo){
//	var panelId1='RPCRealTimeMonitoringRightVideoPanel1';
//	var otherPanelId1='PCPRealTimeMonitoringRightVideoPanel1';
//	var divId1='RPCRealTimeMonitoringRightVideoDiv1_Id';
//	
//	var panelId2='RPCRealTimeMonitoringRightVideoPanel2';
//	var otherPanelId2='PCPRealTimeMonitoringRightVideoPanel2';
//	var divId2='RPCRealTimeMonitoringRightVideoDiv2_Id';
//	
//	var gridPanelId='RPCRealTimeMonitoringListGridPanel_Id';
//	if(deviceType==1){
//		panelId1='PCPRealTimeMonitoringRightVideoPanel1';
//		otherPanelId1='RPCRealTimeMonitoringRightVideoPanel1';
//		divId1='PCPRealTimeMonitoringRightVideoDiv1_Id';
//		
//		panelId2='PCPRealTimeMonitoringRightVideoPanel2';
//		otherPanelId2='RPCRealTimeMonitoringRightVideoPanel2';
//		divId2='PCPRealTimeMonitoringRightVideoDiv2_Id';
//		
//		gridPanelId='PCPRealTimeMonitoringListGridPanel_Id';
//	}
//	
//	var videoUrl  = data.videoUrl;
//	var videoAccessToken = data.videoAccessToken;
//	
//	var videoUrl1="",videoUrl2="";
//	var videoAccessToken1="",videoAccessToken2="";
//	
//	if(isNotVal(videoUrl)){
//		var videoUrlArr=videoUrl.split(";");
//		if(videoUrlArr.length>0){
//			videoUrl1=videoUrlArr[0];
//			if(videoUrlArr.length>1){
//				videoUrl2=videoUrlArr[1];
//			}
//		}
//	}
//	if(isNotVal(videoAccessToken)){
//		var videoAccessTokenArr=videoAccessToken.split(";");
//		if(videoAccessTokenArr.length>0){
//			videoAccessToken1=videoAccessTokenArr[0];
//			if(videoAccessTokenArr.length>1){
//				videoAccessToken2=videoAccessTokenArr[1];
//			}
//		}
//	}
//	
//	var videoPanel1=Ext.getCmp(panelId1);
//	var otherVideoPanel1=Ext.getCmp(otherPanelId1);
//	
//	var videoPanel2=Ext.getCmp(panelId2);
//	var otherVideoPanel2=Ext.getCmp(otherPanelId2);
//	
//	if( (videoNo==undefined||videoNo==1) && playrHelper.playr1!=null){
//		playrHelper.playr1.stop();
//		$("#"+playrHelper.playr1.id).html('');
//		videoPanel1.setHtml('');
//		if(isNotVal(otherVideoPanel1)){
//			otherVideoPanel1.setHtml('');
//		}
//		
//		playrHelper.playr1=null;
//	}
//	
//	if( (videoNo==undefined||videoNo==2) && playrHelper.playr2!=null){
//		playrHelper.playr2.stop();
//		$("#"+playrHelper.playr2.id).html('');
//		videoPanel2.setHtml('');
//		if(isNotVal(otherVideoPanel2)){
//			otherVideoPanel2.setHtml('');
//		}
//		
//		playrHelper.playr2=null;
//	}
//	
//	if(videoNo==undefined||videoNo==1){
//		if(videoUrl1!=''&&videoUrl1!='null'){
//			if(videoPanel1.isHidden() ){
//				videoPanel1.show();
//			}
//			videoPanel1.setHtml('<div id="'+divId1+'" style="width:100%;height:100%;"></div>');
//			var offsetWidth=videoPanel1.getWidth();
//			var offsetHeight=videoPanel1.getHeight();
//			
//			var divWidth=$("#"+divId1).width();
//			var divHeight=$("#"+divId1).height();
//			
//			var videoWidth=offsetWidth;
//			var videoHeight=offsetHeight;
//			
//			if(divWidth>offsetWidth && divHeight>offsetHeight){
//				videoWidth=divWidth;
//				videoHeight=divHeight;
//			}
//			
//			playrHelper.playr1 = new EZUIKit.EZUIKitPlayer({
//	        	id: divId1, // 视频容器ID
//	        	accessToken: videoAccessToken1,
//	            url: videoUrl1,
//	            template: 'mobileLive', // pcLive -PC直播全量版;simple - PC直播极简版;standard-PC直播标准版;security - PC直播安防版(预览回放);voice-PC直播语音版; theme-可配置主题；mobileLive-H5直播全量版 
//	            audio:0, //是否默认开启声音 1：打开（默认） 0：关闭
////	            autoplay:0,
////	          plugin: ['talk'],                       // 加载插件，talk-对讲
////	            header:['capturePicture'],
////	            footer:['fullScreen'],
//	            width: videoWidth,
//	            height: videoHeight
//	        });	
//		}else{
//			if(!videoPanel1.isHidden() ){
//				videoPanel1.hide();
//			}
//		}
//	}
//	
//	if(videoNo==undefined||videoNo==2){
//		if(videoUrl2!=''&&videoUrl2!='null'){
//			if(videoPanel2.isHidden() ){
//				videoPanel2.show();
//			}
//			videoPanel2.setHtml('<div id="'+divId2+'" style="width:100%;height:100%;"></div>');
//			var offsetWidth=videoPanel2.getWidth();
//			var offsetHeight=videoPanel2.getHeight();
//			
//			var divWidth=$("#"+divId2).width();
//			var divHeight=$("#"+divId2).height();
//			
//			var videoWidth=offsetWidth;
//			var videoHeight=offsetHeight;
//			
//			if(divWidth>offsetWidth && divHeight>offsetHeight){
//				videoWidth=divWidth;
//				videoHeight=divHeight;
//			}
//			
//			playrHelper.playr2 = new EZUIKit.EZUIKitPlayer({
//	        	id: divId2, // 视频容器ID
//	        	accessToken: videoAccessToken2,
//	            url: videoUrl2,
//	            template: 'mobileLive', // pcLive -PC直播全量版;simple - PC直播极简版;standard-PC直播标准版;security - PC直播安防版(预览回放);voice-PC直播语音版; theme-可配置主题；mobileLive-H5直播全量版 
//	            audio:0, //是否默认开启声音 1：打开（默认） 0：关闭
////	            autoplay:0,
////	          plugin: ['talk'],                       // 加载插件，talk-对讲
////	            header:['capturePicture'],
////	            footer:['fullScreen'],
//	            width: videoWidth,
//	            height: videoHeight
//	        });	
//		}else{
//			if(!videoPanel2.isHidden() ){
//				videoPanel2.hide();
//			}
//		}
//	}
//}
function showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew){
	var videoPanel=Ext.getCmp(panelId);
	if(videoUrl!='' && videoUrl!='null'){
		if(videoPanel.isHidden() ){
			videoPanel.show();
		}
		if(deviceType==0 && videoNo==1){
			if(isNew){
				if(videoPlayrHelper.rpc.player1!=null){
					if(videoPlayrHelper.rpc.player1.pluginStatus.state.play){
						videoPlayrHelper.rpc.player1.stop();
					}
					$("#"+videoPlayrHelper.rpc.player1.id).html('');
					videoPanel.setHtml('');
					videoPlayrHelper.rpc.player1=null;
				}
			}
			
			
			if(videoPlayrHelper.rpc.player1!=null){
				if(videoPlayrHelper.rpc.player1.pluginStatus.state.play){
					videoPlayrHelper.rpc.player1.stop()
					.then(()=>{
						if(videoPlayrHelper.rpc.player1.accessToken==accessToken){
							videoPlayrHelper.rpc.player1.play(videoUrl);
						}else{
							videoPlayrHelper.rpc.player1.play({url:videoUrl,accessToken: accessToken});
						}
					});
				}else{
					if(videoPlayrHelper.rpc.player1.accessToken==accessToken){
						videoPlayrHelper.rpc.player1.play(videoUrl);
					}else{
						videoPlayrHelper.rpc.player1.play({url:videoUrl,accessToken: accessToken});
					}
				}
			}else{
				videoPanel.setHtml('<div id="'+divId+'" style="width:100%;height:100%;"></div>');
				var videoWidth=$("#"+divId).width();
				var videoHeight=$("#"+divId).height();
				videoPlayrHelper.rpc.player1 = new EZUIKit.EZUIKitPlayer({
		        	id: divId, // 视频容器ID
		        	accessToken: accessToken,
		            url: videoUrl,
		            template: 'mobileLive', // pcLive -PC直播全量版;simple - PC直播极简版;standard-PC直播标准版;security - PC直播安防版(预览回放);voice-PC直播语音版; theme-可配置主题；mobileLive-H5直播全量版 
		            audio:0, //是否默认开启声音 1：打开（默认） 0：关闭
		            width: videoWidth,
		            height: videoHeight
		        });
			}
		}else if(deviceType==0 && videoNo==2){
			if(isNew){
				if(videoPlayrHelper.rpc.player2!=null){
					if(videoPlayrHelper.rpc.player2.pluginStatus.state.play){
						videoPlayrHelper.rpc.player2.stop();
					}
					$("#"+videoPlayrHelper.rpc.player2.id).html('');
					videoPanel.setHtml('');
					videoPlayrHelper.rpc.player2=null;
				}
			}
			if(videoPlayrHelper.rpc.player2!=null){
				if(videoPlayrHelper.rpc.player2.pluginStatus.state.play){
					videoPlayrHelper.rpc.player2.stop()
					.then(()=>{
						if(videoPlayrHelper.rpc.player2.accessToken==accessToken){
							videoPlayrHelper.rpc.player2.play(videoUrl);
						}else{
							videoPlayrHelper.rpc.player2.play({url:videoUrl,accessToken: accessToken});
						}
					});
				}else{
					if(videoPlayrHelper.rpc.player2.accessToken==accessToken){
						videoPlayrHelper.rpc.player2.play(videoUrl);
					}else{
						videoPlayrHelper.rpc.player2.play({url:videoUrl,accessToken: accessToken});
					}
				}
			}else{
				videoPanel.setHtml('<div id="'+divId+'" style="width:100%;height:100%;"></div>');
				var videoWidth=$("#"+divId).width();
				var videoHeight=$("#"+divId).height();
				videoPlayrHelper.rpc.player2 = new EZUIKit.EZUIKitPlayer({
		        	id: divId, // 视频容器ID
		        	accessToken: accessToken,
		            url: videoUrl,
		            template: 'mobileLive', // pcLive -PC直播全量版;simple - PC直播极简版;standard-PC直播标准版;security - PC直播安防版(预览回放);voice-PC直播语音版; theme-可配置主题；mobileLive-H5直播全量版 
		            audio:0, //是否默认开启声音 1：打开（默认） 0：关闭
		            width: videoWidth,
		            height: videoHeight
		        });
			}
		}else if(deviceType==1 && videoNo==1){
			if(isNew){
				if(videoPlayrHelper.pcp.player1!=null){
					if(videoPlayrHelper.pcp.player1.pluginStatus.state.play){
						videoPlayrHelper.pcp.player1.stop();
					}
					$("#"+videoPlayrHelper.pcp.player1.id).html('');
					videoPanel.setHtml('');
					videoPlayrHelper.pcp.player1=null;
				}
			}
			if(videoPlayrHelper.pcp.player1!=null){
				if(videoPlayrHelper.pcp.player1.pluginStatus.state.play){
					videoPlayrHelper.pcp.player1.stop()
					.then(()=>{
						if(videoPlayrHelper.pcp.player1.accessToken==accessToken){
							videoPlayrHelper.pcp.player1.play(videoUrl);
						}else{
							videoPlayrHelper.pcp.player1.play({url:videoUrl,accessToken: accessToken});
						}
					});
				}else{
					if(videoPlayrHelper.pcp.player1.accessToken==accessToken){
						videoPlayrHelper.pcp.player1.play(videoUrl);
					}else{
						videoPlayrHelper.pcp.player1.play({url:videoUrl,accessToken: accessToken});
					}
				}
			}else{
				videoPanel.setHtml('<div id="'+divId+'" style="width:100%;height:100%;"></div>');
				var videoWidth=$("#"+divId).width();
				var videoHeight=$("#"+divId).height();
				videoPlayrHelper.pcp.player1 = new EZUIKit.EZUIKitPlayer({
		        	id: divId, // 视频容器ID
		        	accessToken: accessToken,
		            url: videoUrl,
		            template: 'mobileLive', // pcLive -PC直播全量版;simple - PC直播极简版;standard-PC直播标准版;security - PC直播安防版(预览回放);voice-PC直播语音版; theme-可配置主题；mobileLive-H5直播全量版 
		            audio:0, //是否默认开启声音 1：打开（默认） 0：关闭
		            width: videoWidth,
		            height: videoHeight
		        });
			}
		}else if(deviceType==1 && videoNo==2){
			if(isNew){
				if(videoPlayrHelper.pcp.player2!=null){
					if(videoPlayrHelper.pcp.player2.pluginStatus.state.play){
						videoPlayrHelper.pcp.player2.stop();
					}
					$("#"+videoPlayrHelper.pcp.player2.id).html('');
					videoPanel.setHtml('');
					videoPlayrHelper.pcp.player2=null;
				}
			}
			if(videoPlayrHelper.pcp.player2!=null){
				if(videoPlayrHelper.pcp.player2.pluginStatus.state.play){
					videoPlayrHelper.pcp.player2.stop()
					.then(()=>{
						if(videoPlayrHelper.pcp.player2.accessToken==accessToken){
							videoPlayrHelper.pcp.player2.play(videoUrl);
						}else{
							videoPlayrHelper.pcp.player2.play({url:videoUrl,accessToken: accessToken});
						}
					});
				}else{
					if(videoPlayrHelper.pcp.player2.accessToken==accessToken){
						videoPlayrHelper.pcp.player2.play(videoUrl);
					}else{
						videoPlayrHelper.pcp.player2.play({url:videoUrl,accessToken: accessToken});
					}
				}
			}else{
				videoPanel.setHtml('<div id="'+divId+'" style="width:100%;height:100%;"></div>');
				var videoWidth=$("#"+divId).width();
				var videoHeight=$("#"+divId).height();
				videoPlayrHelper.pcp.player2 = new EZUIKit.EZUIKitPlayer({
		        	id: divId, // 视频容器ID
		        	accessToken: accessToken,
		            url: videoUrl,
		            template: 'mobileLive', // pcLive -PC直播全量版;simple - PC直播极简版;standard-PC直播标准版;security - PC直播安防版(预览回放);voice-PC直播语音版; theme-可配置主题；mobileLive-H5直播全量版 
		            audio:0, //是否默认开启声音 1：打开（默认） 0：关闭
		            width: videoWidth,
		            height: videoHeight
		        });
			}
		}
	}else{
		if(deviceType==0 && videoNo==1){
			if(videoPlayrHelper.rpc.player1!=null && videoPlayrHelper.rpc.player1.pluginStatus.state.play){
				videoPlayrHelper.rpc.player1.stop();
			}
			if(!videoPanel.isHidden() ){
				videoPanel.hide();
			}
		}else if(deviceType==0 && videoNo==2){
			if(videoPlayrHelper.rpc.player2!=null && videoPlayrHelper.rpc.player2.pluginStatus.state.play){
				videoPlayrHelper.rpc.player2.stop();
			}
			if(!videoPanel.isHidden() ){
				videoPanel.hide();
			}
		}else if(deviceType==1 && videoNo==1){
			if(videoPlayrHelper.pcp.player1!=null && videoPlayrHelper.pcp.player1.pluginStatus.state.play){
				videoPlayrHelper.pcp.player1.stop();
			}
			if(!videoPanel.isHidden() ){
				videoPanel.hide();
			}
		}else if(deviceType==1 && videoNo==2){
			if(videoPlayrHelper.pcp.player2!=null && videoPlayrHelper.pcp.player2.pluginStatus.state.play){
				videoPlayrHelper.pcp.player2.stop();
			}
			if(!videoPanel.isHidden() ){
				videoPanel.hide();
			}
		}
	}

}

function initVideo(panelId,divId,videoUrl,deviceType,videoNo,isNew){
	var now=new Date().getTime();
	var accessToken='';
	if(videoUrl=='ezopen://open.ys7.com/G39444019/1.live'){
		if(demoAccessTokenInfo==null || (demoAccessTokenInfo.code!='200') || now>demoAccessTokenInfo.data.expireTime){
			fetch('https://open.ys7.com/jssdk/ezopen/demo/token')
	        .then(response => response.json())
	        .then(res => {
	        	demoAccessTokenInfo = res;
	        	if(demoAccessTokenInfo.code=='200'){
	        		accessToken=demoAccessTokenInfo.data.accessToken;
	        	}
	        	showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew);
	        });
		}else{
			if(demoAccessTokenInfo.code=='200'){
        		accessToken=demoAccessTokenInfo.data.accessToken;
        	}
			showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew);
		}
	}else{
		if(accessTokenInfo==null || (!accessTokenInfo.success) || now>accessTokenInfo.expireTime){
			Ext.Ajax.request({
				method:'POST',
				url:context + '/realTimeMonitoringController/getUIKitAccessToken',
				success:function(response) {
					accessTokenInfo = Ext.JSON.decode(response.responseText);
					if(accessTokenInfo.success){
						accessToken=accessTokenInfo.accessToken;
					}
					showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew);
				},
				failure:function(){
					Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
				}
			});
		}else{
			if(accessTokenInfo.success){
				accessToken=accessTokenInfo.accessToken;
			}
			showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew);
		}
	}
}

function createVideo(deviceType,data,videoNo,isNew){
	var panelId1='RPCRealTimeMonitoringRightVideoPanel1';
	var divId1='RPCRealTimeMonitoringRightVideoDiv1_Id';
	
	var panelId2='RPCRealTimeMonitoringRightVideoPanel2';
	var divId2='RPCRealTimeMonitoringRightVideoDiv2_Id';
	
	if(deviceType==1){
		panelId1='PCPRealTimeMonitoringRightVideoPanel1';
		divId1='PCPRealTimeMonitoringRightVideoDiv1_Id';
		
		panelId2='PCPRealTimeMonitoringRightVideoPanel2';
		divId2='PCPRealTimeMonitoringRightVideoDiv2_Id';
	}
	
	var videoUrl  = data.videoUrl;
	var url="";
	var videoUrl1="",videoUrl2="";
	if(isNotVal(videoUrl)){
		var videoUrlArr=videoUrl.split(";");
		if(videoUrlArr.length>0){
			videoUrl1=videoUrlArr[0];
			if(videoUrlArr.length>1){
				videoUrl2=videoUrlArr[1];
			}
		}
	}
	if(videoNo==1){
		initVideo(panelId1,divId1,videoUrl1,deviceType,1,isNew);
	}else if(videoNo==2){
		initVideo(panelId2,divId2,videoUrl2,deviceType,2,isNew);
	}else{
		initVideo(panelId1,divId1,videoUrl1,deviceType,1,isNew);
		initVideo(panelId2,divId2,videoUrl2,deviceType,2,isNew);
	}
}