Ext.define("AP.view.historyQuery.HistoryQueryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.historyMonitoringInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var RPCHistoryQueryInfoView = Ext.create('AP.view.historyQuery.RPCHistoryQueryInfoView');
        var PCPHistoryQueryInfoView = Ext.create('AP.view.historyQuery.PCPHistoryQueryInfoView');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"HistoryQueryTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        				title: '抽油机井',
        				id:'RPCHistoryQueryInfoPanel_Id',
        				items: [RPCHistoryQueryInfoView],
        				layout: "fit",
        				border: false
        			},{
        				title: '螺杆泵井',
        				id:'PCPHistoryQueryInfoPanel_Id',
        				items: [PCPHistoryQueryInfoView],
        				layout: "fit",
        				hidden: pcpHidden||onlyMonitor,
        				border: false
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        					if(newCard.id=="RPCHistoryQueryInfoPanel_Id"){
        						var statTabActiveId = Ext.getCmp("RPCHistoryQueryStatTabPanel").getActiveTab().id;
        						if(statTabActiveId=="RPCHistoryQueryFESdiagramResultStatGraphPanel_Id"){
            						loadAndInitHistoryQueryFESdiagramResultStat(true);
            					}else if(statTabActiveId=="RPCHistoryQueryStatGraphPanel_Id"){
        							loadAndInitHistoryQueryCommStatusStat(true);
        						}else if(statTabActiveId=="RPCHistoryQueryRunStatusStatGraphPanel_Id"){
            						loadAndInitHistoryQueryRunStatusStat(true);
            					}else if(newCard.id=="RPCHistoryQueryDeviceTypeStatGraphPanel_Id"){
        							loadAndInitHistoryQueryDeviceTypeStat(true);
        						}
//        						Ext.getCmp('HistoryQueryRPCDeviceListComb_Id').setValue('');
//        						Ext.getCmp('HistoryQueryRPCDeviceListComb_Id').setRawValue('');
        						var gridPanel = Ext.getCmp("RPCHistoryQueryDeviceListGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.historyQuery.RPCHistoryQueryWellListStore');
        						}
        					}else if(newCard.id=="PCPHistoryQueryInfoPanel_Id"){
        						var statTabActiveId = Ext.getCmp("PCPHistoryQueryStatTabPanel").getActiveTab().id;
        						if(statTabActiveId=="PCPHistoryQueryStatGraphPanel_Id"){
        							loadAndInitHistoryQueryCommStatusStat(true);
        						}else if(statTabActiveId=="PCPHistoryQueryRunStatusStatGraphPanel_Id"){
            						loadAndInitHistoryQueryRunStatusStat(true);
            					}else if(newCard.id=="PCPHistoryQueryDeviceTypeStatGraphPanel_Id"){
        							loadAndInitHistoryQueryDeviceTypeStat(true);
        						}
//        						Ext.getCmp('HistoryQueryPCPDeviceListComb_Id').setValue('');
//        						Ext.getCmp('HistoryQueryPCPDeviceListComb_Id').setRawValue('');
        						var gridPanel = Ext.getCmp("PCPHistoryQueryDeviceListGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.historyQuery.PCPHistoryQueryWellListStore');
        						}
        					}
        				}
        			}
            	}],
        		listeners: {
        			beforeclose: function ( panel, eOpts) {},
        			afterrender: function ( panel, eOpts) {}
        		}
        });
        me.callParent(arguments);
    }

});

function createHistoryQueryDeviceListColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        var flex_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }
        else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
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

function createHistoryQueryColumn(columnInfo) {
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

function exportHistoryQueryDeviceListExcel(orgId,deviceType,deviceName,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,fileName,title,columnStr) {
    var url = context + '/historyQueryController/exportHistoryQueryDeviceListExcel';
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
    fields="id"+(isNotVal(lockedfields)?(","+lockedfields):"")+(isNotVal(unlockedfields)?(","+unlockedfields):"");
    heads="序号"+(isNotVal(lockedheads)?(","+lockedheads):"")+(isNotVal(unlockedheads)?(","+unlockedheads):"");
    
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

function exportHistoryQueryDataExcel(orgId,deviceType,deviceId,deviceName,startDate,endDate,fileName,title,columnStr) {
    var url = context + '/historyQueryController/exportHistoryQueryDataExcel';
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
    fields="id"+(isNotVal(lockedfields)?(","+lockedfields):"")+(isNotVal(unlockedfields)?(","+unlockedfields):"");
    heads="序号"+(isNotVal(lockedheads)?(","+lockedheads):"")+(isNotVal(unlockedheads)?(","+unlockedheads):"");
    fields="";
    heads="";
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&deviceId=" + deviceId 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};

function exportHistoryQueryDiagramOverlayDataExcel(orgId,deviceType,deviceId,deviceName,startDate,endDate,fileName,title,columnStr) {
    var url = context + '/historyQueryController/exportHistoryQueryFESDiagramOverlayDataExcel';
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
    fields="id"+(isNotVal(lockedfields)?(","+lockedfields):"")+(isNotVal(unlockedfields)?(","+unlockedfields):"");
    heads="序号"+(isNotVal(lockedheads)?(","+lockedheads):"")+(isNotVal(unlockedheads)?(","+unlockedheads):"");
    fields="";
    heads="";
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&deviceId=" + deviceId 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};

function exportHistoryQueryFESDiagramDataExcel(orgId,deviceType,deviceId,deviceName,startDate,endDate,fileName,title) {
    var url = context + '/historyQueryController/exportHistoryQueryFESDiagramDataExcel';
    var fields = "";
    var heads = "";
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&deviceId=" + deviceId 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};


function deviceHistoryQueryCurve(deviceType){
	var selectRowId="RPCHistoryQueryInfoDeviceListSelectRow_Id";
	var gridPanelId="RPCHistoryQueryDeviceListGridPanel_Id";
	var startDateId="RPCHistoryQueryStartDate_Id";
	var startHourId="RPCHistoryQueryStartTime_Hour_Id";
	var startMinuteId="RPCHistoryQueryStartTime_Minute_Id";
	var startSecondId="RPCHistoryQueryStartTime_Second_Id";
	
	var endDateId="RPCHistoryQueryEndDate_Id";
	var endHourId="RPCHistoryQueryEndTime_Hour_Id";
	var endMinuteId="RPCHistoryQueryEndTime_Minute_Id";
	var endSecondId="RPCHistoryQueryEndTime_Second_Id";
	
	var divId="rpcHistoryQueryCurveDiv_Id";
	var panelId='rpcHistoryQueryCurvePanel_Id';
	
	if(deviceType==1){
		selectRowId="PCPHistoryQueryInfoDeviceListSelectRow_Id";
		gridPanelId="PCPHistoryQueryDeviceListGridPanel_Id";
		startDateId="PCPHistoryQueryStartDate_Id";
		startHourId="PCPHistoryQueryStartTime_Hour_Id";
		startMinuteId="PCPHistoryQueryStartTime_Minute_Id";
		startSecondId="PCPHistoryQueryStartTime_Second_Id";
		endDateId="PCPHistoryQueryEndDate_Id";
		endHourId="PCPHistoryQueryEndTime_Hour_Id";
		endMinuteId="PCPHistoryQueryEndTime_Minute_Id";
		endSecondId="PCPHistoryQueryEndTime_Second_Id";
		divId="pcpHistoryQueryCurveDiv_Id";
		panelId='pcpHistoryQueryCurvePanel_Id';
	}
	
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(selectRow>=0){
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.wellName;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
	}
	var startDate=Ext.getCmp(startDateId).rawValue;
	var startTime_Hour=Ext.getCmp(startHourId).getValue();
	var startTime_Minute=Ext.getCmp(startMinuteId).getValue();
	var startTime_Second=Ext.getCmp(startSecondId).getValue();
    var endDate=Ext.getCmp(endDateId).rawValue;
    var endTime_Hour=Ext.getCmp(endHourId).getValue();
	var endTime_Minute=Ext.getCmp(endMinuteId).getValue();
	var endTime_Second=Ext.getCmp(endSecondId).getValue();
	Ext.getCmp(panelId).el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/historyQueryController/getHistoryQueryCurveData',
		success:function(response) {
			Ext.getCmp(panelId).getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
		    var data = result.list;
		    var graphicSet=result.graphicSet;
		    
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    var title = result.deviceName + "趋势曲线";
		    var xTitle='采集时间';
		    var legendName =result.curveItems;
		    
		    var color=result.curveColors;
		    for(var i=0;i<color.length;i++){
		    	if(color[i]==''){
		    		color[i]=defaultColors[i%10];
		    	}else{
		    		color[i]='#'+color[i];
		    	}
		    }
		    
		    var yTitle=legendName[0];
		    
		    var series = "[";
		    var yAxis= [];
		    for (var i = 0; i < legendName.length; i++) {
		        var maxValue=null;
		        var minValue=null;
		        var allPositive=true;//全部是非负数
		        var allNegative=true;//全部是负值
		    	series += "{\"name\":\"" + legendName[i] + "\",marker:{enabled: false},"+"\"yAxis\":"+i+",";
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
		        if (i != legendName.length - 1) {
		            series += ",";
		        }
		        var opposite=false;
		        if(i>0){
		        	opposite=true;
		        }
		        if(allNegative){
		        	maxValue=0;
		        }else if(allPositive){
		        	minValue=0;
		        }
		        if(JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.History) ){
			    	for(var j=0;j<graphicSet.History.length;j++){
			    		if(graphicSet.History[j].itemCode!=undefined && graphicSet.History[j].itemCode.toUpperCase()==result.curveItemCodes[i].toUpperCase()){
			    			if(isNotVal(graphicSet.History[j].yAxisMaxValue)){
					    		maxValue=parseFloat(graphicSet.History[j].yAxisMaxValue);
					    	}
					    	if(isNotVal(graphicSet.History[j].yAxisMinValue)){
					    		minValue=parseFloat(graphicSet.History[j].yAxisMinValue);
					    	}
					    	break;
			    		}
			    	}
			    }
		        
		        var singleAxis={
		        		max:maxValue,
		        		min:minValue,
		        		title: {
		                    text: legendName[i],
		                    style: {
		                        color: color[i],
		                    }
		                },
		                labels: {
		                	style: {
		                        color: color[i],
		                    }
		                },
		                opposite:opposite
		          };
		        yAxis.push(singleAxis);
		        
		    }
		    series += "]";
		    
		    var ser = Ext.JSON.decode(series);
		    var timeFormat='%m-%d';
//		    timeFormat='%H:%M';
		    initDeviceHistoryCurveChartFn(ser, tickInterval, divId, title, '', '', yAxis, color,true,timeFormat);
		},
		failure:function(){
			Ext.getCmp(panelId).getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
			deviceType:deviceType
        }
	});
};

function initDeviceHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
	var dafaultMenuItem = Highcharts.getOptions().exporting.buttons.contextButton.menuItems;
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    var mychart = new Highcharts.Chart({
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
        yAxis: yAxis,
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
            url: context + '/exportHighcharsPicController/export',
            buttons: {
            	contextButton: {
            		menuItems:[dafaultMenuItem[0],dafaultMenuItem[1],dafaultMenuItem[2],dafaultMenuItem[3],dafaultMenuItem[4],dafaultMenuItem[5],dafaultMenuItem[6],dafaultMenuItem[7],
            			,dafaultMenuItem[2],{
            				text: '图形设置',
            				onclick: function() {
            					var window = Ext.create("AP.view.historyQuery.HistoryCurveSetWindow", {
                                    title: '历史曲线设置'
                                });
                                window.show();
            				}
            			}]
            	}
            }
        },
        plotOptions: {
            spline: {
//                lineWidth: 1,
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
                shadow: true,
                events: {
                	legendItemClick: function(e){
//                		alert("第"+this.index+"个图例被点击，是否可见："+!this.visible);
//                		return true;
                	}
                }
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

function loadAndInitHistoryQueryCommStatusStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var deviceTypeStatValue='';
	var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
	var panelId="";
	if(activeId=="RPCHistoryQueryInfoPanel_Id"){
		deviceType=0;
		panelId="RPCHistoryQueryStatGraphPanel_Id";
		if(all){
			Ext.getCmp("RPCHistoryQueryStatSelectFESdiagramResult_Id").setValue('');
			Ext.getCmp("RPCHistoryQueryStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("RPCHistoryQueryStatSelectRunStatus_Id").setValue('');
			Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").setValue('');
			deviceTypeStatValue='';
		}else{
			deviceTypeStatValue=Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").getValue();
		}
	}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
		deviceType=1;
		panelId="PCPHistoryQueryStatGraphPanel_Id";
		if(all){
			Ext.getCmp("PCPHistoryQueryStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("PCPHistoryQueryStatSelectRunStatus_Id").setValue('');
			Ext.getCmp("PCPHistoryQueryStatSelectDeviceType_Id").setValue('');
			deviceTypeStatValue='';
		}else{
			deviceTypeStatValue=Ext.getCmp("PCPHistoryQueryStatSelectDeviceType_Id").getValue();
		}
	}
	Ext.getCmp(panelId).el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData',
		success:function(response) {
			Ext.getCmp(panelId).getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initHistoryQueryStatPieOrColChat(result);
		},
		failure:function(){
			Ext.getCmp(panelId).getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initHistoryQueryStatPieOrColChat(get_rawData) {
	var divid="RPCHistoryQueryStatGraphPanelPieDiv_Id";
	var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
	if(activeId=="RPCHistoryQueryInfoPanel_Id"){
		divid="RPCHistoryQueryStatGraphPanelPieDiv_Id";
	}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
		divid="PCPHistoryQueryStatGraphPanelPieDiv_Id";
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
	ShowHistoryQueryStatPieOrColChat(title,divid, "设备数占", pieData,colors);
};

function ShowHistoryQueryStatPieOrColChat(title,divid, name, data,colors) {
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
						var statSelectCommStatusId="RPCHistoryQueryStatSelectCommStatus_Id";
						var deviceListComb_Id="HistoryQueryRPCDeviceListComb_Id";
						var gridPanel_Id="RPCHistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.realTimeMonitoring.RPCHistoryQueryWellListStore";
						var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
						if(activeId=="RPCHistoryQueryInfoPanel_Id"){
							Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                        	Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectedDevice_Id").setValue(0);
                        	
							statSelectCommStatusId="RPCHistoryQueryStatSelectCommStatus_Id";
							deviceListComb_Id="HistoryQueryRPCDeviceListComb_Id";
							gridPanel_Id="RPCHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.RPCHistoryQueryWellListStore";
						}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
							Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
			            	Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectedDevice_Id").setValue(0);
			            	
							statSelectCommStatusId="PCPHistoryQueryStatSelectCommStatus_Id";
							deviceListComb_Id="HistoryQueryPCPDeviceListComb_Id";
							gridPanel_Id="PCPHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.PCPHistoryQueryWellListStore";
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

function loadAndInitHistoryQueryRunStatusStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var deviceTypeStatValue='';
	var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
	var panelId='';
	if(activeId=="RPCHistoryQueryInfoPanel_Id"){
		deviceType=0;
		panelId='RPCHistoryQueryRunStatusStatGraphPanel_Id';
		if(all){
			Ext.getCmp("RPCHistoryQueryStatSelectFESdiagramResult_Id").setValue('');
			Ext.getCmp("RPCHistoryQueryStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("RPCHistoryQueryStatSelectRunStatus_Id").setValue('');
			Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").setValue('');
			deviceTypeStatValue='';
		}else{
			deviceTypeStatValue=Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").getValue();
		}
	}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
		deviceType=1;
		panelId='PCPHistoryQueryRunStatusStatGraphPanel_Id';
		if(all){
			Ext.getCmp("PCPHistoryQueryStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("PCPHistoryQueryStatSelectRunStatus_Id").setValue('');
			Ext.getCmp("PCPHistoryQueryStatSelectDeviceType_Id").setValue('');
			deviceTypeStatValue='';
		}else{
			deviceTypeStatValue=Ext.getCmp("PCPHistoryQueryStatSelectDeviceType_Id").getValue();
		}
	}
	Ext.getCmp(panelId).el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData',
		success:function(response) {
			Ext.getCmp(panelId).getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initHistoryQueryRunStatusStatPieOrColChat(result);
		},
		failure:function(){
			Ext.getCmp(panelId).getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initHistoryQueryRunStatusStatPieOrColChat(get_rawData) {
	var divid="RPCHistoryQueryRunStatusStatGraphPanelPieDiv_Id";
	var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
	if(activeId=="RPCHistoryQueryInfoPanel_Id"){
		divid="RPCHistoryQueryRunStatusStatGraphPanelPieDiv_Id";
	}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
		divid="PCPHistoryQueryRunStatusStatGraphPanelPieDiv_Id";
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
	ShowHistoryQueryRunStatusStatPieOrColChat(title,divid, "设备数占", pieData,colors);
};

function ShowHistoryQueryRunStatusStatPieOrColChat(title,divid, name, data,colors) {
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
						var statSelectRunStatusId="RPCHistoryQueryStatSelectRunStatus_Id";
						var deviceListComb_Id="HistoryQueryRPCDeviceListComb_Id";
						var gridPanel_Id="RPCHistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.realTimeMonitoring.RPCHistoryQueryWellListStore";
						var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
						if(activeId=="RPCHistoryQueryInfoPanel_Id"){
							Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                        	Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectedDevice_Id").setValue(0);
                        	
							statSelectRunStatusId="RPCHistoryQueryStatSelectRunStatus_Id";
							deviceListComb_Id="HistoryQueryRPCDeviceListComb_Id";
							gridPanel_Id="RPCHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.RPCHistoryQueryWellListStore";
						}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
							Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
			            	Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectedDevice_Id").setValue(0);
			            	
			            	statSelectRunStatusId="PCPHistoryQueryStatSelectRunStatus_Id";
							deviceListComb_Id="HistoryQueryPCPDeviceListComb_Id";
							gridPanel_Id="PCPHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.PCPHistoryQueryWellListStore";
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

function loadAndInitHistoryQueryFESdiagramResultStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var deviceTypeStatValue='';
	var deviceTypeStatValue='';
	deviceType=0;
	if(all){
		Ext.getCmp("RPCHistoryQueryStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("RPCHistoryQueryStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("RPCHistoryQueryStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").setValue('');
		commStatusStatValue='';
		deviceTypeStatValue='';
	}else{
		deviceTypeStatValue=Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").getValue();
		commStatusStatValue=Ext.getCmp("RPCHistoryQueryStatSelectCommStatus_Id").getValue();
	}
	Ext.getCmp("RPCHistoryQueryFESdiagramResultStatGraphPanel_Id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData',
		success:function(response) {
			Ext.getCmp("RPCHistoryQueryFESdiagramResultStatGraphPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initHistoryQueryFESDiagramResultStatPieOrColChat(result);
		},
		failure:function(){
			Ext.getCmp("RPCHistoryQueryFESdiagramResultStatGraphPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initHistoryQueryFESDiagramResultStatPieOrColChat(get_rawData) {
	var divid="RPCHistoryQueryFESdiagramResultStatGraphPanelPieDiv_Id";
	
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
	
	ShowHistoryQueryFESDiagramResultStatPieOrColChat(title,divid, "设备数占", pieData,colors);
};

function ShowHistoryQueryFESDiagramResultStatPieOrColChat(title,divid, name, data,colors) {
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
						Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                    	Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectedDevice_Id").setValue(0);
                    	
						var statSelectFESdiagramResultId="RPCHistoryQueryStatSelectFESdiagramResult_Id";
						var deviceListComb_Id="HistoryQueryRPCDeviceListComb_Id";
						var gridPanel_Id="RPCHistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.realTimeMonitoring.RPCHistoryQueryWellListStore";
						
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

function loadAndInitHistoryQueryDeviceTypeStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var commStatusStatValue='';
	var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
	var panelId='';
	if(activeId=="RPCHistoryQueryInfoPanel_Id"){
		deviceType=0;
		panelId='RPCHistoryQueryDeviceTypeStatGraphPanel_Id';
		if(all){
			Ext.getCmp("RPCHistoryQueryStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").setValue('');
			commStatusStatValue='';
		}else{
			commStatusStatValue=Ext.getCmp("RPCHistoryQueryStatSelectCommStatus_Id").getValue();
		}
		
	}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
		deviceType=1;
		panelId='PCPHistoryQueryDeviceTypeStatGraphPanel_Id';
		if(all){
			Ext.getCmp("PCPHistoryQueryStatSelectCommStatus_Id").setValue('');
			Ext.getCmp("PCPHistoryQueryStatSelectDeviceType_Id").setValue('');
			commStatusStatValue='';
		}else{
			commStatusStatValue=Ext.getCmp("PCPHistoryQueryStatSelectCommStatus_Id").getValue();
		}
	}
	Ext.getCmp(panelId).el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringDeviceTypeStatData',
		success:function(response) {
			Ext.getCmp(panelId).getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initHistoryQueryDeviceTypeStatPieOrColChat(result);
		},
		failure:function(){
			Ext.getCmp(panelId).getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
};

function initHistoryQueryDeviceTypeStatPieOrColChat(get_rawData) {
	var divid="RPCHistoryQueryDeviceTypeStatPieDiv_Id";
	var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
	if(activeId=="RPCHistoryQueryInfoPanel_Id"){
		divid="RPCHistoryQueryDeviceTypeStatPieDiv_Id";
	}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
		divid="PCPHistoryQueryDeviceTypeStatPieDiv_Id";
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
	ShowHistoryQueryDeviceTypeStatPieChat(title,divid, "设备数占", pieData,colors);
};

function ShowHistoryQueryDeviceTypeStatPieChat(title,divid, name, data,colors) {
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
						var statSelectDeviceType_Id="RPCHistoryQueryStatSelectDeviceType_Id";
						var deviceListComb_Id="HistoryQueryRPCDeviceListComb_Id";
						var gridPanel_Id="RPCHistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.historyQuery.RPCHistoryQueryWellListStore";
						var activeId = Ext.getCmp("HistoryQueryTabPanel").getActiveTab().id;
						if(activeId=="RPCHistoryQueryInfoPanel_Id"){
							Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                        	Ext.getCmp("RPCHistoryQueryInfoDeviceListSelectedDevice_Id").setValue(0);
                        	
                        	statSelectDeviceType_Id="RPCHistoryQueryStatSelectDeviceType_Id";
							deviceListComb_Id="HistoryQueryRPCDeviceListComb_Id";
							gridPanel_Id="RPCHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.RPCHistoryQueryWellListStore";
						}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
							Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
			            	Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectedDevice_Id").setValue(0);
			            	
			            	statSelectDeviceType_Id="PCPHistoryQueryStatSelectDeviceType_Id";
							deviceListComb_Id="HistoryQueryPCPDeviceListComb_Id";
							gridPanel_Id="PCPHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.PCPHistoryQueryWellListStore";
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

function getHistoryQueryDeviceListDataPage(deviceId,deviceType,limit){
	var dataPage=1;
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var FESdiagramResultStatValue='';
	var commStatusStatValue='';
	var runStatusStatValue='';
	var deviceTypeStatValue='';
	if(deviceType==0){
		deviceName=Ext.getCmp('HistoryQueryRPCDeviceListComb_Id').getValue();
		FESdiagramResultStatValue=Ext.getCmp("RPCHistoryQueryStatSelectFESdiagramResult_Id").getValue();
    	commStatusStatValue=Ext.getCmp("RPCHistoryQueryStatSelectCommStatus_Id").getValue();
    	runStatusStatValue=Ext.getCmp("RPCHistoryQueryStatSelectRunStatus_Id").getValue();
		deviceTypeStatValue=Ext.getCmp("RPCHistoryQueryStatSelectDeviceType_Id").getValue();
	}else{
		deviceName=Ext.getCmp('HistoryQueryPCPDeviceListComb_Id').getValue();
    	commStatusStatValue=Ext.getCmp("PCPHistoryQueryStatSelectCommStatus_Id").getValue();
    	runStatusStatValue=Ext.getCmp("PCPHistoryQueryStatSelectRunStatus_Id").getValue();
		deviceTypeStatValue=Ext.getCmp("PCPHistoryQueryStatSelectDeviceType_Id").getValue();
	}
	
	Ext.Ajax.request({
		method:'POST',
		async :  false,
		url:context + '/historyQueryController/getHistoryQueryDeviceListDataPage',
		success:function(response) {
			dataPage = Ext.JSON.decode(response.responseText).dataPage;
		},
		failure:function(){
		},
		params: {
			orgId: orgId,
            deviceType:deviceType,
            deviceId:deviceId,
            deviceName:deviceName,
            FESdiagramResultStatValue:FESdiagramResultStatValue,
            commStatusStatValue:commStatusStatValue,
            runStatusStatValue:runStatusStatValue,
            deviceTypeStatValue:deviceTypeStatValue,
            limit:limit
        }
	});
	return dataPage;
}