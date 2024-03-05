Ext.define("AP.view.historyQuery.HistoryQueryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.historyMonitoringInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var HistoryQueryInfoPanel = Ext.create('AP.view.historyQuery.HistoryQueryInfoPanel');
        
        var items=[];
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        				title: tabInfo.children[i].text,
        				tpl: tabInfo.children[i].text,
        				xtype: 'tabpanel',
        	        	id: 'HistoryQueryRootTabPanel_'+tabInfo.children[i].tabId,
        	        	activeTab: 0,
        	        	border: false,
        	        	tabPosition: 'left',
        	        	items:[],
        	        	listeners: {
        	        		beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        			oldCard.removeAll();
        	        		},
        	        		tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        			var HistoryQueryInfoPanel = Ext.create('AP.view.historyQuery.HistoryQueryInfoPanel');
        	        			newCard.add(HistoryQueryInfoPanel);

        	        			historyDataRefresh();
        	        		},
        	        		afterrender: function (panel, eOpts) {
        	        			
        	        		}
        	        	}
        			}
        			
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={
        					title: '<div style="color:#000000;font-size:11px;font-family:SimSun">'+tabInfo.children[i].children[j].text+'</div>',
        					tpl:tabInfo.children[i].children[j].text,
        					layout: 'fit',
        					id: 'HistoryQueryRootTabPanel_'+tabInfo.children[i].children[j].tabId,
        					border: false
        				};
            			if(j==0){
            				secondTabPanel.items=[];
            				secondTabPanel.items.push(HistoryQueryInfoPanel);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        		}else{
        			panelItem={
        				title: tabInfo.children[i].text,
        				tpl: tabInfo.children[i].text,
        				layout: 'fit',
    					id: 'HistoryQueryRootTabPanel_'+tabInfo.children[i].tabId,
    					border: false
        			};
        			if(i==0){
            			panelItem.items=[];
            			panelItem.items.push(HistoryQueryInfoPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
        
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"HistoryQueryRootTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: items,
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				if(oldCard.xtype=='tabpanel'){
        					oldCard.activeTab.removeAll();
        				}else{
        					oldCard.removeAll();
        				}
        			},
    				tabchange: function (tabPanel, newCard,oldCard, obj) {
    					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
    					
    					var HistoryQueryInfoPanel = Ext.create('AP.view.historyQuery.HistoryQueryInfoPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(HistoryQueryInfoPanel);
        				}else{
	        				newCard.add(HistoryQueryInfoPanel);
        				}
        				
        				historyDataRefresh();
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

function historyDataRefresh(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
	var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
	
	Ext.getCmp("selectedDeviceType_global").setValue(deviceType); 
	
	var realtimeTurnToHisyorySign=Ext.getCmp("realtimeTurnToHisyorySign_Id").getValue();
	var removeHistoryFESdiagramResultStatGraphPanel=false;
	var tabPanel = Ext.getCmp("HistoryQueryStatTabPanel");
	var statTabActiveId = tabPanel.getActiveTab().id;
	
	
	var getTabId = tabPanel.getComponent("HistoryQueryFESdiagramResultStatGraphPanel_Id");
	if(deviceCount>0 && getTabId==undefined){
 		Ext.getCmp("HistoryQueryStatTabPanel").insert(0,historyStatTabItems[0]);
 	}else if(deviceCount==0 && getTabId!=undefined){
 		Ext.getCmp("HistoryQueryStatTabPanel").remove(Ext.getCmp("HistoryQueryFESdiagramResultStatGraphPanel_Id"));
 		removeHistoryFESdiagramResultStatGraphPanel=true;
 	}

 	if(!(statTabActiveId=="HistoryQueryFESdiagramResultStatGraphPanel_Id" && removeHistoryFESdiagramResultStatGraphPanel)){
 		if(statTabActiveId=="HistoryQueryFESdiagramResultStatGraphPanel_Id"){
 			loadAndInitHistoryQueryFESdiagramResultStat(true);
 		}else if(statTabActiveId=="HistoryQueryStatGraphPanel_Id"){
 			loadAndInitHistoryQueryCommStatusStat(true);
 		}else if(statTabActiveId=="HistoryQueryRunStatusStatGraphPanel_Id"){
 			loadAndInitHistoryQueryRunStatusStat(true);
 		}else if(statTabActiveId=="HistoryQueryDeviceTypeStatGraphPanel_Id"){
 			loadAndInitHistoryQueryDeviceTypeStat(true);
 		}
 	}
	
	
	if(isNotVal(realtimeTurnToHisyorySign)){//如果是实时跳转
		Ext.getCmp("realtimeTurnToHisyorySign_Id").setValue('');
	}else{
		Ext.getCmp('HistoryQueryDeviceListComb_Id').setValue('');
		Ext.getCmp('HistoryQueryDeviceListComb_Id').setRawValue('');
	}
	refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp("selectedRPCDeviceId_global").getValue()),deviceType,Ext.getCmp("HistoryQueryDeviceListGridPanel_Id"),'AP.store.historyQuery.HistoryQueryWellListStore');
}

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
	var timestamp=new Date().getTime();
	var key='exportHistoryQueryDeviceListData_'+deviceType+'_'+timestamp;
	
	var maskPanelId='HistoryQueryInfoPanel_Id';
	if(deviceType==1){
		maskPanelId='PCPHistoryQueryInfoPanel_Id';
	}
	
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
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,cosog.string.loading);
    openExcelWindow(url + '?flag=true' + param);
};

function exportHistoryQueryDataExcel(orgId,deviceType,deviceId,deviceName,startDate,endDate,fileName,title,columnStr) {
	var timestamp=new Date().getTime();
	var key='exportHistoryQueryData_'+deviceType+'_'+timestamp;
	
	var maskPanelId='HistoryQueryInfoPanel_Id';
	
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
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,cosog.string.loading);
    openExcelWindow(url + '?flag=true' + param);
};

function exportHistoryQueryDiagramOverlayDataExcel(orgId,deviceType,deviceId,deviceName,startDate,endDate,fileName,title,columnStr) {
	var timestamp=new Date().getTime();
	var key='exportHistoryQueryData_'+timestamp;
	
	var maskPanelId='HistoryQueryInfoPanel_Id';
	
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
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,cosog.string.loading);
    openExcelWindow(url + '?flag=true' + param);
};

function exportHistoryQueryFESDiagramDataExcel(orgId,deviceType,deviceId,deviceName,startDate,endDate,fileName,title) {
	var timestamp=new Date().getTime();
	var key='exportHistoryQueryData_'+timestamp;
	var maskPanelId='HistoryQueryInfoPanel_Id';
	
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
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,cosog.string.loading);
    openExcelWindow(url + '?flag=true' + param);
};


function deviceHistoryQueryCurve(deviceType){
	var selectRowId="HistoryQueryInfoDeviceListSelectRow_Id";
	var gridPanelId="HistoryQueryDeviceListGridPanel_Id";
	var startDateId="HistoryQueryStartDate_Id";
	var startHourId="HistoryQueryStartTime_Hour_Id";
	var startMinuteId="HistoryQueryStartTime_Minute_Id";
	var startSecondId="HistoryQueryStartTime_Second_Id";
	
	var endDateId="HistoryQueryEndDate_Id";
	var endHourId="HistoryQueryEndTime_Hour_Id";
	var endMinuteId="HistoryQueryEndTime_Minute_Id";
	var endSecondId="HistoryQueryEndTime_Second_Id";
	
	var divId="historyQueryCurveDiv_Id";
	var panelId='historyQueryCurvePanel_Id';
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var calculateType=0;
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(selectRow>=0){
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
		calculateType=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.calculateType;
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
		    var title = result.deviceName + "趋势曲线";
		    var xTitle='采集时间';
		    var legendName =result.curveItems;
		    var legendCode =result.curveItemCodes;
		    var curveConf=result.curveConf;
		    
		    var color=[];
		    var color_l=[];
		    var color_r=[];
		    var color_all=[];
		    for(var i=0;i<curveConf.length;i++){
		    	var singleColor=defaultColors[i%defaultColors.length];
		    	if(curveConf[i].color!=''){
		    		singleColor='#'+curveConf[i].color;
		    	}
		    	color.push(singleColor);
		    	
		    	if(curveConf[i].yAxisOpposite){
		    		color_r.push(singleColor);
		    	}else{
		    		color_l.push(singleColor);
		    	}
		    }
		    
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
		        singleSeries.lineWidth=curveConf[i].lineWidth;
		        singleSeries.dashStyle=curveConf[i].dashStyle;
		        singleSeries.marker={enabled: false};
		        singleSeries.yAxis=i;
		        singleSeries.data=[];
		        for (var j = 0; j < data.length; j++) {
		        	var pointData=[];
		        	pointData.push(Date.parse(data[j].acqTime.replace(/-/g, '/')));
		        	pointData.push(data[j].data[i]);
		        	singleSeries.data.push(pointData);
		        	if(parseFloat(data[j].data[i])<0){
		            	allPositive=false;
		            }else if(parseFloat(data[j].data[i])>=0){
		            	allNegative=false;
		            }
		        }
		        if(curveConf[i].yAxisOpposite){
		        	series_r.push(singleSeries);
		        }else{
		        	series_l.push(singleSeries);
		        }
		        
		        
//		    	series += "{\"name\":\"" + legendName[i] + "\",type:'spline',lineWidth:"+curveConf[i].lineWidth+",dashStyle:'"+curveConf[i].dashStyle+"',marker:{enabled: false},"+"\"yAxis\":"+i+",";
//		        series += "\"data\":[";
//		        for (var j = 0; j < data.length; j++) {
//		        	series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + data[j].data[i] + "]";
//		            if (j != data.length - 1) {
//		                series += ",";
//		            }
//		            if(parseFloat(data[j].data[i])<0){
//		            	allPositive=false;
//		            }else if(parseFloat(data[j].data[i])>=0){
//		            	allNegative=false;
//		            }
//		        }
//		        series += "]}";
//		        if (i != legendName.length - 1) {
//		            series += ",";
//		        }
		        
		        
		        
		        var opposite=curveConf[i].yAxisOpposite;
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
		                opposite:opposite
		          };
		        if(curveConf[i].yAxisOpposite){
		        	yAxis_r.push(singleAxis);
		        }else{
		        	yAxis_l.push(singleAxis);
		        }
//		        yAxis.push(singleAxis);
		        
		    }
//		    series += "]";
		    
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
		    
//		    var ser = Ext.JSON.decode(series);
		   
		    initDeviceHistoryCurveChartFn(series, tickInterval, divId, title, '', '', yAxis, color_all,true,timeFormat);
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
			deviceType:deviceType,
			calculateType:calculateType
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
            filename: title,
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight,
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
//                marker: {
//                    enabled: true,
//                    radius: 3, //曲线点半径，默认是4
//                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
//                    states: {
//                        hover: {
//                            enabled: true,
//                            radius: 6
//                        }
//                    }
//                },
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
	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
	var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
	var panelId="HistoryQueryStatGraphPanel_Id";
	if(all){
		Ext.getCmp("HistoryQueryStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectDeviceType_Id").setValue('');
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
	var divId="HistoryQueryStatGraphPanelPieDiv_Id";
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
	ShowHistoryQueryStatPieOrColChat(title,divId, "设备数占", pieData,colors);
};

function ShowHistoryQueryStatPieOrColChat(title,divId, name, data,colors) {
	Highcharts.chart(divId, {
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
						var statSelectCommStatusId="HistoryQueryStatSelectCommStatus_Id";
						var deviceListComb_Id="HistoryQueryDeviceListComb_Id";
						var gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.historyQuery.HistoryQueryWellListStore";
						var selectedDeviceId_global="selectedDeviceId_global";
						var deviceType=0;
						var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
						if(activeId=="HistoryQueryInfoPanel_Id"){
							Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                        	
							statSelectCommStatusId="HistoryQueryStatSelectCommStatus_Id";
							deviceListComb_Id="HistoryQueryDeviceListComb_Id";
							gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.HistoryQueryWellListStore";
							selectedDeviceId_global="selectedDeviceId_global";
							deviceType=0;
						}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
							Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
			            	
							statSelectCommStatusId="PCPHistoryQueryStatSelectCommStatus_Id";
							deviceListComb_Id="HistoryQueryPCPDeviceListComb_Id";
							gridPanel_Id="PCPHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.PCPHistoryQueryWellListStore";
							selectedDeviceId_global="selectedPCPDeviceId_global";
							deviceType=1;
						}
						
						if(!e.point.selected){//如果没被选中,则本次是选中
							Ext.getCmp(statSelectCommStatusId).setValue(e.point.name);
						}else{//取消选中
							Ext.getCmp(statSelectCommStatusId).setValue('');
						}
						
						Ext.getCmp(deviceListComb_Id).setValue('');
						Ext.getCmp(deviceListComb_Id).setRawValue('');

						refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp(selectedDeviceId_global).getValue()),deviceType,Ext.getCmp(gridPanel_Id),store);
					}
				},
				showInLegend : true
			}
		},
		exporting:{ 
            enabled:true,    
            filename:title,
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight
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
	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
	var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
	var panelId='HistoryQueryRunStatusStatGraphPanel_Id';
	if(all){
		Ext.getCmp("HistoryQueryStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectDeviceType_Id").setValue('');
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
	var divId="HistoryQueryRunStatusStatGraphPanelPieDiv_Id";
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
				}else if(datalist[i].itemCode=='noData'){
					singleData.color='#'+alarmShowStyle.Run.noData.Color;
				}else if(datalist[i].itemCode=='goOnline'){
					singleData.color='#'+alarmShowStyle.Comm.goOnline.Color;
				}else if(datalist[i].itemCode=='offline'){
					singleData.color='#'+alarmShowStyle.Comm.offline.Color;
				}
				pieData.push(singleData);
			}
		}
	}
	ShowHistoryQueryRunStatusStatPieOrColChat(title,divId, "设备数占", pieData,colors);
};

function ShowHistoryQueryRunStatusStatPieOrColChat(title,divId, name, data,colors) {
	Highcharts.chart(divId, {
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
						var statSelectRunStatusId="HistoryQueryStatSelectRunStatus_Id";
						var deviceListComb_Id="HistoryQueryDeviceListComb_Id";
						var gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.historyQuery.HistoryQueryWellListStore";
						var selectedDeviceId_global="selectedDeviceId_global";
						var deviceType=0;
						var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
						if(activeId=="HistoryQueryInfoPanel_Id"){
							Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                        	
							statSelectRunStatusId="HistoryQueryStatSelectRunStatus_Id";
							deviceListComb_Id="HistoryQueryDeviceListComb_Id";
							gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.HistoryQueryWellListStore";
							selectedDeviceId_global="selectedDeviceId_global";
							deviceType=0;
						}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
							Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
			            	
			            	statSelectRunStatusId="PCPHistoryQueryStatSelectRunStatus_Id";
							deviceListComb_Id="HistoryQueryPCPDeviceListComb_Id";
							gridPanel_Id="PCPHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.PCPHistoryQueryWellListStore";
							selectedDeviceId_global="selectedPCPDeviceId_global";
							deviceType=1;
						}
						
						if(!e.point.selected){//如果没被选中,则本次是选中
							Ext.getCmp(statSelectRunStatusId).setValue(e.point.name);
						}else{//取消选中
							Ext.getCmp(statSelectRunStatusId).setValue('');
						}
						
						Ext.getCmp(deviceListComb_Id).setValue('');
						Ext.getCmp(deviceListComb_Id).setRawValue('');
						refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp(selectedDeviceId_global).getValue()),deviceType,Ext.getCmp(gridPanel_Id),store);
					}
				},
				showInLegend : true
			}
		},
		exporting:{ 
            enabled:true,    
            filename:title,    
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight
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
	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
	if(all){
		Ext.getCmp("HistoryQueryStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectDeviceType_Id").setValue('');
	}
	Ext.getCmp("HistoryQueryFESdiagramResultStatGraphPanel_Id").el.mask(cosog.string.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData',
		success:function(response) {
			Ext.getCmp("HistoryQueryFESdiagramResultStatGraphPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initHistoryQueryFESDiagramResultStatPieOrColChat(result);
		},
		failure:function(){
			Ext.getCmp("HistoryQueryFESdiagramResultStatGraphPanel_Id").getEl().unmask();
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initHistoryQueryFESDiagramResultStatPieOrColChat(get_rawData) {
	var divId="HistoryQueryFESdiagramResultStatGraphPanelPieDiv_Id";
	
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
	
	ShowHistoryQueryFESDiagramResultStatPieOrColChat(title,divId, "设备数占", pieData,colors);
};

function ShowHistoryQueryFESDiagramResultStatPieOrColChat(title,divId, name, data,colors) {
	Highcharts.chart(divId, {
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
						Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                    	
						var statSelectFESdiagramResultId="HistoryQueryStatSelectFESdiagramResult_Id";
						var deviceListComb_Id="HistoryQueryDeviceListComb_Id";
						var gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.realTimeMonitoring.HistoryQueryWellListStore";
						
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
            filename:title,    
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight
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
	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
	var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
	var panelId='HistoryQueryDeviceTypeStatGraphPanel_Id';
	if(all){
		Ext.getCmp("HistoryQueryStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("HistoryQueryStatSelectDeviceType_Id").setValue('');
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
	var divId="HistoryQueryDeviceTypeStatPieDiv_Id";
	var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
	if(activeId=="HistoryQueryInfoPanel_Id"){
		divId="HistoryQueryDeviceTypeStatPieDiv_Id";
	}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
		divId="PCPHistoryQueryDeviceTypeStatPieDiv_Id";
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
	ShowHistoryQueryDeviceTypeStatPieChat(title,divId, "设备数占", pieData,colors);
};

function ShowHistoryQueryDeviceTypeStatPieChat(title,divId, name, data,colors) {
	Highcharts.chart(divId, {
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
						var statSelectDeviceType_Id="HistoryQueryStatSelectDeviceType_Id";
						var deviceListComb_Id="HistoryQueryDeviceListComb_Id";
						var gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.historyQuery.HistoryQueryWellListStore";
						var selectedDeviceId_global="selectedDeviceId_global";
						var deviceType=0;
						var activeId = Ext.getCmp("HistoryQueryRootTabPanel").getActiveTab().id;
						if(activeId=="HistoryQueryInfoPanel_Id"){
							Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                        	
                        	statSelectDeviceType_Id="HistoryQueryStatSelectDeviceType_Id";
							deviceListComb_Id="HistoryQueryDeviceListComb_Id";
							gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.HistoryQueryWellListStore";
							selectedDeviceId_global="selectedDeviceId_global";
							deviceType=0;
						}else if(activeId=="PCPHistoryQueryInfoPanel_Id"){
							Ext.getCmp("PCPHistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
			            	
			            	statSelectDeviceType_Id="PCPHistoryQueryStatSelectDeviceType_Id";
							deviceListComb_Id="HistoryQueryPCPDeviceListComb_Id";
							gridPanel_Id="PCPHistoryQueryDeviceListGridPanel_Id";
							store="AP.store.historyQuery.PCPHistoryQueryWellListStore";
							selectedDeviceId_global="selectedPCPDeviceId_global";
							deviceType=1;
						}
						
						if(!e.point.selected){//如果没被选中,则本次是选中
							Ext.getCmp(statSelectDeviceType_Id).setValue(e.point.name);
						}else{//取消选中
							Ext.getCmp(statSelectDeviceType_Id).setValue('');
						}
						
						Ext.getCmp(deviceListComb_Id).setValue('');
						Ext.getCmp(deviceListComb_Id).setRawValue('');
						refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp(selectedDeviceId_global).getValue()),deviceType,Ext.getCmp(gridPanel_Id),store);
					}
				},
				showInLegend : true
			}
		},
		exporting:{ 
            enabled:true,    
            filename:title,    
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight
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

	var deviceName=Ext.getCmp('HistoryQueryDeviceListComb_Id').getValue();
	var FESdiagramResultStatValue=Ext.getCmp("HistoryQueryStatSelectFESdiagramResult_Id").getValue();
	var commStatusStatValue=Ext.getCmp("HistoryQueryStatSelectCommStatus_Id").getValue();
	var runStatusStatValue=Ext.getCmp("HistoryQueryStatSelectRunStatus_Id").getValue();
	var deviceTypeStatValue=Ext.getCmp("HistoryQueryStatSelectDeviceType_Id").getValue();
	
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