Ext.define("AP.view.realTimeMonitoring.ItemRealtimeCurveWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.itemRealtimeCurveWindow',
    layout: 'fit',
    id:'ItemRealtimeCurveWindow_Id',
    title: loginUserLanguageResource.trendCurve,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
//    width: 1200,
//    minWidth: 900,
    width: '65%',
    height: '50%',
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout: 'border',
        	items: [{
        		region: 'center',
        		layout: 'fit',
        		autoScroll: false,
        		border: false,
        		tbar:[{
                    id: 'RealtimeCurveItemName_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'RealtimeCurveItemCode_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'RealtimeCurveItemType_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'RealtimeCurveItemResolutionMode_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }],
            	html: '<div id="ItemRealtimeCurveContainer" class="hbox" style="width:100%;height:100%;display:flex;flex-wrap:wrap;align-content:flex-start;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	var chartCreated=false;
                    	var container=$('#ItemRealtimeCurveContainer');
            			if(container!=undefined && container.length>0){
            				var containerChildren=container[0].children;
            				if(containerChildren!=undefined && containerChildren.length>0){
            					for(var i=0;i<containerChildren.length;i++){
            						var chart = $("#"+containerChildren[i].id).highcharts(); 
            						if(isNotVal(chart)){
            							chartCreated=true;
            							highchartsResize(containerChildren[i].id);
            						}
            					}
            				}
            			}
            			if(!chartCreated){
            				getItemRealTimeCurveData();
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

function getItemRealTimeCurveData(){
	var selectRowId="RealTimeMonitoringInfoDeviceListSelectRow_Id";
	var gridPanelId="RealTimeMonitoringListGridPanel_Id";
	var panelId="ItemRealtimeCurveWindow_Id";
	var deviceName='';
	var deviceId=0;
	var calculateType=0;
	var itemName=Ext.getCmp("RealtimeCurveItemName_Id").getValue();
	var itemCode=Ext.getCmp("RealtimeCurveItemCode_Id").getValue();
	var itemType=Ext.getCmp("RealtimeCurveItemType_Id").getValue();
	var itemResolutionMode=Ext.getCmp("RealtimeCurveItemResolutionMode_Id").getValue();
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length>0){
		calculateType=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.calculateType;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
	}
	if(Ext.getCmp(panelId)!=undefined){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getItemRealTimeCurveData',
		success:function(response) {
			if(isNotVal(Ext.getCmp(panelId))){
				Ext.getCmp(panelId).getEl().unmask();
            }
			
			var result =  Ext.JSON.decode(response.responseText);
			var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var data = result.list;
		    var totals=result.curveCount;
		    var legendName =result.curveItems;
		    
		    var colors=["#7cb5ec"];
		   
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 2) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    
		    $('#ItemRealtimeCurveContainer').append('<div id="ItemRealtimeCurveDiv_Id" style="width:100%;height:100%;"></div>');
            var divId = 'ItemRealtimeCurveDiv_Id';
		    
        	var xTitle='';
        	var yTitle=legendName[0];
        	var title = result.deviceName+":"+legendName[0].split("(")[0] + loginUserLanguageResource.trendCurve;
        	var subtitle='';
        	
		    var color=["#7cb5ec"];
		    
		    var maxValue=null;
	        var minValue=null;
	        var allPositive=true;//全部是非负数
	        var allNegative=true;//全部是负值
	        
	        var yAxisOpposite=false;
	        
	        var series = [];  // 直接定义为数组
	        var seriesItem = {
	            name: legendName[0],
	            lineWidth: 2,
	            marker: { enabled: true },
	            dataGrouping: { enabled: false },
	            data: []  // 空数组，下面填充
	        };
	        
	        
	        for (var j = 0; j < data.length; j++) {
	            var timestamp = Date.parse(data[j].acqTime.replace(/-/g, '/'));
	            var value = parseFloat(data[j].data);
	            seriesItem.data.push([timestamp, value]);
	            
	            if(parseFloat(data[j].data)<0){
	            	allPositive=false;
	            }else if(parseFloat(data[j].data)>=0){
	            	allNegative=false;
	            }
	        }
	        series.push(seriesItem);
		    if(allNegative){
	        	maxValue=0;
	        }else if(allPositive){
	        	minValue=0;
	        }
		    var timeFormat='%H:%M';
		    initDeviceRealtimeMonitoringStockChartFn(series, tickInterval, divId, title, subtitle, xTitle, yTitle,color,false,true,false,timeFormat,maxValue,minValue,yAxisOpposite);
		},
		failure:function(){
			if(Ext.getCmp(panelId)!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			calculateType:calculateType,
			itemName:itemName,
			itemCode:itemCode,
			itemType:itemType,
			itemResolutionMode:itemResolutionMode
        }
	});
}