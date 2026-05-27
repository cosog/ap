Ext.define("AP.view.historyQuery.ItemHistoryCurveWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.itemHistoryCurveWindow',
    layout: 'fit',
    id:'ItemHistoryCurveWindow_Id',
    title: loginUserLanguageResource.trendCurve,
    iframe: true,
    closeAction: 'destroy',
    width: 1200,
    minWidth: 900,
    height: 450,
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    modal: true,
    border: false,
    draggable: true, // 是否可拖曳
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout: 'border',
        	items: [{
        		region: 'center',
            	id:'ItemHistoryCurvePanel_Id',
        		layout: 'fit',
        		autoScroll: false,
        		tbar:[{
                    id: 'HistoryCurveItemName_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'HistoryCurveItemCode_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'HistoryCurveItemType_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'HistoryCurveItemResolutionMode_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.range,
                    labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                    format: 'Y-m-d ',
                    value: '',
                    id: 'ItemHistoryCurveStartDate_Id',
                    listeners: {
                    	select: function (combo, record, index) {
                    		
                    	}
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ItemHistoryCurveStartTime_Hour_Id',
                    fieldLabel: loginUserLanguageResource.hour,
                    labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
                    minValue: 0,
                    maxValue: 23,
                    value:'',
                    msgTarget: 'none',
                    regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                    listeners: {
                    	blur: function (field, event, eOpts) {
                    		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                    		var flag=r.test(field.value);
                    		if(!flag){
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ItemHistoryCurveStartTime_Minute_Id',
                	fieldLabel: loginUserLanguageResource.minute,
                    labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
                    minValue: 0,
                    maxValue: 59,
                    value:'',
                    msgTarget: 'none',
                    regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                    listeners: {
                    	blur: function (field, event, eOpts) {
                    		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                    		var flag=r.test(field.value);
                    		if(!flag){
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },{
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.timeTo,
                    labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                    format: 'Y-m-d ',
                    value: '',
                    id: 'ItemHistoryCurveEndDate_Id',
                    listeners: {
                    	select: function (combo, record, index) {
                    		
                    	}
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ItemHistoryCurveEndTime_Hour_Id',
                	fieldLabel: loginUserLanguageResource.hour,
                    labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
                    minValue: 0,
                    maxValue: 23,
                    value:'',
                    msgTarget: 'none',
                    regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                    listeners: {
                    	blur: function (field, event, eOpts) {
                    		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                    		var flag=r.test(field.value);
                    		if(!flag){
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ItemHistoryCurveEndTime_Minute_Id',
                    fieldLabel: loginUserLanguageResource.minute,
                    labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
                    minValue: 0,
                    maxValue: 59,
                    value:'',
                    msgTarget: 'none',
                    regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                    listeners: {
                    	blur: function (field, event, eOpts) {
                    		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                    		var flag=r.test(field.value);
                    		if(!flag){
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },{
                    xtype: 'button',
                    text: loginUserLanguageResource.search,
                    iconCls: 'search',
                    handler: function () {
                    	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                    	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                    	var startTime_Hour=Ext.getCmp('ItemHistoryCurveStartTime_Hour_Id').getValue();
                    	if(!r.test(startTime_Hour)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    		Ext.getCmp('ItemHistoryCurveStartTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Minute=Ext.getCmp('ItemHistoryCurveStartTime_Minute_Id').getValue();
                    	if(!r2.test(startTime_Minute)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    		Ext.getCmp('ItemHistoryCurveStartTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	
                    	var endTime_Hour=Ext.getCmp('ItemHistoryCurveEndTime_Hour_Id').getValue();
                    	if(!r.test(endTime_Hour)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    		Ext.getCmp('ItemHistoryCurveEndTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Minute=Ext.getCmp('ItemHistoryCurveEndTime_Minute_Id').getValue();
                    	if(!r2.test(endTime_Minute)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    		Ext.getCmp('ItemHistoryCurveEndTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	
                    	getItemHistoryCurveData();
                    }
                },'->',{
                    id: 'ItemHistoryCurveVacuateCount_Id',
                    xtype: 'component',
                    tpl: loginUserLanguageResource.vacuateCount + ':{vacuateCount} '+loginUserLanguageResource.totalCount + ':{totalCount}', // 抽稀记录数
                    hidden: false,
                    style: 'margin-right:15px'
                }],
            	html: '<div id="ItemHistoryCurveDiv_Id" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if ($("#ItemHistoryCurveDiv_Id").highcharts() != undefined) {
                    		highchartsResize("ItemHistoryCurveDiv_Id");
                    	}else{
            				getItemHistoryCurveData();
            			}
                    },
                    minimize: function (win, opts) {
                        win.collapse();
                    }
                }
        	}]
        });
        me.callParent(arguments);
    }
});

function getItemHistoryCurveData(){
	var selectRowId="RealTimeMonitoringInfoDeviceListSelectRow_Id";
	var gridPanelId="RealTimeMonitoringListGridPanel_Id";
	var panelId="ItemHistoryCurvePanel_Id";
	var deviceName='';
	var deviceId=0;
	var calculateType=0;
	var itemName=Ext.getCmp("HistoryCurveItemName_Id").getValue();
	var itemCode=Ext.getCmp("HistoryCurveItemCode_Id").getValue();
	var itemType=Ext.getCmp("HistoryCurveItemType_Id").getValue();
	var itemResolutionMode=Ext.getCmp("HistoryCurveItemResolutionMode_Id").getValue();
	
	var startDate=Ext.getCmp('ItemHistoryCurveStartDate_Id')!=undefined?Ext.getCmp('ItemHistoryCurveStartDate_Id').rawValue:'';
	var startTime_Hour=Ext.getCmp('ItemHistoryCurveStartTime_Hour_Id')!=undefined?Ext.getCmp('ItemHistoryCurveStartTime_Hour_Id').getValue():'';
	var startTime_Minute=Ext.getCmp('ItemHistoryCurveStartTime_Minute_Id')!=undefined?Ext.getCmp('ItemHistoryCurveStartTime_Minute_Id').getValue():'';
	var startTime_Second=0;

    var endDate=Ext.getCmp('ItemHistoryCurveEndDate_Id')!=undefined?Ext.getCmp('ItemHistoryCurveEndDate_Id').rawValue:'';
    var endTime_Hour=Ext.getCmp('ItemHistoryCurveEndTime_Hour_Id')!=undefined?Ext.getCmp('ItemHistoryCurveEndTime_Hour_Id').getValue():'';
	var endTime_Minute=Ext.getCmp('ItemHistoryCurveEndTime_Minute_Id')!=undefined?Ext.getCmp('ItemHistoryCurveEndTime_Minute_Id').getValue():'';
	var endTime_Second=0;
	
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id")!=undefined && Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
	}
	
	if(Ext.getCmp(panelId)!=undefined){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/historyQueryController/getItemHistoryCurveData',
		success:function(response) {
			if(isNotVal(Ext.getCmp(panelId))){
				Ext.getCmp(panelId).getEl().unmask();
            }
			
			var result =  Ext.JSON.decode(response.responseText);
		    var data = result.list;
		    var graphicSet=result.graphicSet;
		    var hiddenExceptionData=result.hiddenExceptionData;
		    
		    updateVacuateRecords(result.totalCount,result.vacuateCount,"ItemHistoryCurveVacuateCount_Id");
            var divId = 'ItemHistoryCurveDiv_Id';
		    
		    var timeFormat='%m-%d';
		    if(data.length>0 && result.minAcqTime.split(' ')[0]==result.maxAcqTime.split(' ')[0]){
			    timeFormat='%H:%M';
		    }
		    
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
        	var title = result.deviceName+":"+itemName.split("(")[0] + loginUserLanguageResource.trendCurve;
		    
		    var xTitle=loginUserLanguageResource.acqTime;
		    var legendName =result.curveItems;
		    var legendCode =result.curveItemCodes;
		    
		    var color=[];
		    var color_l=[];
		    var color_r=[];
		    var color_all=[];

	    	color.push(defaultColors[0]);
	    	color_l.push(defaultColors[0]);
		    
		    var series = [];
		    var series_l=[];
		    var series_r=[];
		    var yAxis= [];
		    var yAxis_l= [];
		    var yAxis_r= [];
		    
		    for (var i = 0; i < legendName.length; i++) {
		        var maxValue=null;
		        var minValue=null;
		        var allPositive=true;//全部是非负数
		        var allNegative=true;//全部是负值
		        
		        var singleSeries={};legendCode
		        singleSeries.name=legendName[i];
		        singleSeries.code=legendCode[i];
		        singleSeries.type='spline';
		        singleSeries.lineWidth=2;
		        singleSeries.marker={enabled: false};
		        singleSeries.yAxis=i;
		        singleSeries.data=[];
		        for (var j = 0; j < data.length; j++) {
		        	var pointData=[];
		        	pointData.push(Date.parse(data[j].acqTime.replace(/-/g, '/')));
		        	pointData.push(data[j].data[i]);
		        	
		        	if(parseFloat(data[j].data[i])<0){
		            	allPositive=false;
		            }else if(parseFloat(data[j].data[i])>=0){
		            	allNegative=false;
		            }
		        	
		        	if(hiddenExceptionData){
		        		if(isNumber(data[j].data[i])){
		        			singleSeries.data.push(pointData);
		        		}
		        	}else{
		        		singleSeries.data.push(pointData);
		        	}
		        }
		        series_l.push(singleSeries);
		        
		        var opposite=false;
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
		        		code:legendCode[i],
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
		                lineWidth: 1,
			        	tickWidth: 1,      // 刻度线宽度
		                tickLength: 5,     // 刻度线长度（可选）
		                opposite:opposite
		          };
		        yAxis_l.push(singleAxis);
		        
		    }
		    
		    for(var i=yAxis_l.length-1;i>=0;i--){
		    	yAxis.push(yAxis_l[i]);
		    }
		    for(var i=0;i<yAxis_r.length;i++){
		    	yAxis.push(yAxis_r[i]);
		    }
		    
		    for(var i=0;i<series_l.length;i++){
		    	series_l[i].yAxis=series_l.length-1-i;
		    	series.push(series_l[i]);
		    }
		    for(var i=0;i<series_r.length;i++){
		    	series_r[i].yAxis=series_l.length+i;
		    	series.push(series_r[i]);
		    }
		    
		    for(var i=0;i<color_l.length;i++){
		    	color_all.push(color_l[i]);
		    }
		    for(var i=0;i<color_r.length;i++){
		    	color_all.push(color_r[i]);
		    }
		   
		    initItemHistoryCurveChartFn(series, tickInterval, divId, title, '', '', yAxis, color_all,false,timeFormat);
		},
		failure:function(){
			if(Ext.getCmp(panelId)!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			calculateType:calculateType,
			itemName:itemName,
			itemCode:itemCode,
			itemType:itemType,
			itemResolutionMode:itemResolutionMode,
			startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
        }
	});
}

function initItemHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color,legend,timeFormat) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
//		Highcharts.setOptions({
//	        global: {
//	            useUTC: false
//	        }
//	    });

	    var mychart = new Highcharts.Chart({
	        chart: {
	            renderTo: divId,
	            type: 'spline',
	            shadow: false,
	            borderWidth: 0,
	            zoomType: 'xy'
	        },
	        time: {
	            timezoneOffset: new Date().getTimezoneOffset()   // 用户本地时区
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
//	            tickInterval: tickInterval,
	            tickPixelInterval:tickInterval,
	            labels: {
	            	formatter: function () {
	                    // 使用图表的 time 工具，而非全局 Highcharts.dateFormat
	                    return this.axis.chart.time.dateFormat(timeFormat, this.value);
	                },
	                autoRotation:true,//自动旋转
	                rotation: -45 //倾斜度，防止数量过多显示不全  
//	                step: 2
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
	            fallbackToExportServer: false,
	            filename: title,
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null,
	            		buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
	        },
	        plotOptions: {
	            spline: {
	                fillOpacity: 0.3,
	                shadow: true,
	                events: {
	                	legendItemClick: function(e){
//	                		alert("第"+this.index+"个图例被点击，是否可见："+!this.visible);
//	                		return true;
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
	}
};