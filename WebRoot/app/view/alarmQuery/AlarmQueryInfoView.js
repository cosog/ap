Ext.define("AP.view.alarmQuery.AlarmQueryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.alarmQueryInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var AlarmQueryInfoPanel = Ext.create('AP.view.alarmQuery.AlarmQueryInfoPanel');
        
        var items=[];
        var deviceTypeActiveId=getDeviceTypeActiveId();
        var firstActiveTab=deviceTypeActiveId.firstActiveTab;
        var secondActiveTab=deviceTypeActiveId.secondActiveTab;
        
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        				title: tabInfo.children[i].text,
        				tpl: tabInfo.children[i].text,
        				xtype: 'tabpanel',
        	        	id: 'AlarmQueryRootTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        	activeTab: i==firstActiveTab?secondActiveTab:0,
        	        	iconCls: i==firstActiveTab?'check1':null,
        	        	border: false,
        	        	tabPosition: 'left',
        	        	items:[],
        	        	listeners: {
        	        		beforetabchange:function( tabPanel, newCard, oldCard, eOpts ) {
        	        			if(Ext.getCmp("AlarmQueryRootTabPanel")!=undefined){
            	        			Ext.getCmp("AlarmQueryRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
        	        			}
                				if(oldCard!=undefined){
                					oldCard.setIconCls(null);
                    				oldCard.removeAll();
                        	    }
                        	    if(newCard!=undefined){
                        	    	newCard.setIconCls('check1');		
                        	    }
        	        		},
        	        		tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        			var AlarmQueryInfoPanel = Ext.create('AP.view.alarmQuery.AlarmQueryInfoPanel');
        	        			newCard.add(AlarmQueryInfoPanel);
        	        			alarmQueryDataRefresh();
        	        		},
        	        		afterrender: function (panel, eOpts) {
        	        			
        	        		}
        	        	}
        			}
        			
        			var allSecondIds='';
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={
        					title: tabInfo.children[i].children[j].text,
        					tpl:tabInfo.children[i].children[j].text,
        					layout: 'fit',
        					id: 'AlarmQueryRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
//        					iconCls: (panelItem.items.length==1&&j==0)?'check2':null,
        					border: false
        				};
            			if(j==0){
            				allSecondIds+=tabInfo.children[i].children[j].deviceTypeId;
                		}else{
                			allSecondIds+=(','+tabInfo.children[i].children[j].deviceTypeId);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        			if(panelItem.items.length>1){//添加全部标签
        				var secondTabPanel_all={
        						title: loginUserLanguageResource.all,
        						tpl:loginUserLanguageResource.all,
//        						iconCls:'check2',
        						layout: 'fit',
        						id: 'AlarmQueryRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].iconCls='check2';
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].items=[];
        				panelItem.items[secondActiveTab].items.push(AlarmQueryInfoPanel);
    				}
        		}else{
        			panelItem={
        				title: tabInfo.children[i].text,
        				tpl: tabInfo.children[i].text,
        				layout: 'fit',
    					id: 'AlarmQueryRootTabPanel_'+tabInfo.children[i].deviceTypeId,
    					iconCls: i==firstActiveTab?'check1':null,
    					border: false
        			};
        			if(i==firstActiveTab){
            			panelItem.items=[];
            			panelItem.items.push(AlarmQueryInfoPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"AlarmQueryRootTabPanel",
        		activeTab: firstActiveTab,
        		border: false,
        		tabPosition: 'bottom',
        		items: items,
        		listeners: {
        			beforetabchange: function ( tabPanel, newCard, oldCard, eOpts ) {
        				if(Ext.getCmp("AlarmQueryRootTabPanel")!=undefined){
    	        			Ext.getCmp("AlarmQueryRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
	        			}
        				if(oldCard!=undefined){
        					oldCard.setIconCls(null);
        					if(oldCard.xtype=='tabpanel'){
            					oldCard.activeTab.removeAll();
            				}else{
            					oldCard.removeAll();
            				}
                	    }
                	    if(newCard!=undefined){
                	    	newCard.setIconCls('check1');		
                	    }
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
    					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
    					
    					var AlarmQueryInfoPanel = Ext.create('AP.view.alarmQuery.AlarmQueryInfoPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(AlarmQueryInfoPanel);
        				}else{
	        				newCard.add(AlarmQueryInfoPanel);
        				}
    					
        				alarmQueryDataRefresh();
    				}
    			}
            }]
        });
        me.callParent(arguments);
    }
});

function createAlarmOverviewQueryColumn(columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var flex_ = "";
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
        else if (attr.dataIndex.toUpperCase() == 'alarmtime'.toUpperCase() || attr.dataIndex.toUpperCase() == 'recoverytime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
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

function createAlarmQueryColumn(columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var flex_ = "";
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
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_+hidden_ + lock_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }
        else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        else if (attr.dataIndex.toUpperCase() == 'alarmtime'.toUpperCase() || attr.dataIndex.toUpperCase() == 'recoverytime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } 
        else {
            myColumns +=",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

function exportAlarmOverviewDataExcel() {
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
	var deviceName=Ext.getCmp('AlarmDeviceListComb_Id').getValue();
	var alarmLevel=Ext.getCmp('AlarmLevelComb_Id').getValue();
	var isSendMessage=Ext.getCmp('AlarmIsSendMessageComb_Id').getValue();
	var alarmType=Ext.getCmp('SelectedAlarmStatType_Id').getValue();
	var alarmLevel=Ext.getCmp('SelectedAlarmStatLevel_Id').getValue();
	var alarmQueryStatRangeType=Ext.getCmp("AlarmQueryStatRangeType_Id").getValue().alarmQueryStatRangeType;
	var statType=0;
	var statTabPanel=Ext.getCmp('AlarmQueryStatGraphPanel_Id');
	if(statTabPanel.getActiveTab().id=='AlarmTypeStatGraphPanel_Id'){
		statType=0;
	}else if(statTabPanel.getActiveTab().id=='AlarmLevelStatGraphPanel_Id'){
		statType=1;
	}
	 	
	var fileName='报警查询'+loginUserLanguageResource.deviceList;
	 	
	if(deviceType.indexOf(",")<0){
	 	fileName=deviceTypeName+fileName;
	}
	 	
	var title=fileName;
	var columnStr=Ext.getCmp("AlarmOverviewColumnStr_Id").getValue();
	
	
    var url = context + '/alarmQueryController/exportAlarmOverviewData';
    var fields = "";
    var heads = "";
    var lockedheads = "";
    var unlockedheads = "";
    var lockedfields = "";
    var unlockedfields = "";
    var columns_ = Ext.JSON.decode(columnStr);
    
    fields = "id";
    heads = loginUserLanguageResource.idx;
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
    	fields+=","+lockedfields;
    	heads+= "," + lockedheads;
    }
    if (isNotVal(unlockedfields)) {
    	unlockedfields = unlockedfields.substring(0, unlockedfields.length - 1);
    	unlockedheads = unlockedheads.substring(0, unlockedheads.length - 1);
    	fields+=","+unlockedfields;
    	heads+= "," + unlockedheads;
    }
    
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&alarmType=" + alarmType
    + "&alarmLevel=" + alarmLevel
    + "&statType=" + statType
    + "&alarmQueryStatRangeType=" + alarmQueryStatRangeType
    + "&isSendMessage=" + isSendMessage
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};

function exportAlarmDataExcel(orgId,deviceType,dictDeviceType,deviceId,deviceName,startDate,endDate,alarmType,alarmLevel,isSendMessage,fileName,title,columnStr) {
    var url = context + '/alarmQueryController/exportAlarmData';
    var fields = "";
    var heads = "";
    var lockedheads = "";
    var unlockedheads = "";
    var lockedfields = "";
    var unlockedfields = "";
    var columns_ = Ext.JSON.decode(columnStr);
    
    fields = "id";
    heads = loginUserLanguageResource.idx;
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
    	fields+=","+lockedfields;
    	heads+= "," + lockedheads;
    }
    if (isNotVal(unlockedfields)) {
    	unlockedfields = unlockedfields.substring(0, unlockedfields.length - 1);
    	unlockedheads = unlockedheads.substring(0, unlockedheads.length - 1);
    	fields+=","+unlockedfields;
    	heads+= "," + unlockedheads;
    }
    
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&dictDeviceType=" + dictDeviceType 
    + "&deviceId=" + deviceId 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&alarmType=" + alarmType
    + "&alarmLevel=" + alarmLevel
    + "&isSendMessage=" + isSendMessage
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};

function alarmQueryDataRefresh(){
	if (isNotVal(Ext.getCmp("AlarmQueryRootTabPanel"))) {
    	Ext.getCmp("AlarmQueryRootTabPanel").getEl().unmask();
    }
	Ext.getCmp('SelectedAlarmStatType_Id').setValue('');
	Ext.getCmp('SelectedAlarmStatLevel_Id').setValue('');
	
	updateAlarmQueryTabpanelContent();
	
	loadAlarmQueryStatData();
	
	var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
	if (isNotVal(gridPanel)) {
		gridPanel.getStore().loadPage(1);
	}else{
		Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
	}
}

function loadAlarmQueryStatData(){
	var alarmQueryStatRangeType=Ext.getCmp("AlarmQueryStatRangeType_Id").getValue().alarmQueryStatRangeType;
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
	var firstDeviceType=getDeviceTypeFromTabId_first("AlarmQueryRootTabPanel");
	Ext.getCmp("selectedDeviceType_global").setValue(deviceType);
	Ext.getCmp("selectedFirstDeviceType_global").setValue(firstDeviceType); 
	var projectTabConfig=getProjectTabInstanceInfoByDeviceType(deviceType);
	var statType=0;
	var statTabPanel=Ext.getCmp('AlarmQueryStatGraphPanel_Id');
	if(statTabPanel.getActiveTab().id=='AlarmTypeStatGraphPanel_Id'){
		statType=0;
	}else if(statTabPanel.getActiveTab().id=='AlarmLevelStatGraphPanel_Id'){
		statType=1;
	}
	
	if(Ext.getCmp("AlarmQueryStatGraphPanel_Id")!=undefined && Ext.getCmp("AlarmQueryStatGraphPanel_Id").el!=undefined){
		Ext.getCmp("AlarmQueryStatGraphPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/alarmQueryController/getAlarmStatData',
		success:function(response) {
			if(Ext.getCmp("AlarmQueryStatGraphPanel_Id")!=undefined && Ext.getCmp("AlarmQueryStatGraphPanel_Id").el!=undefined){
				Ext.getCmp("AlarmQueryStatGraphPanel_Id").getEl().unmask();
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			
			var statTabPanel=Ext.getCmp('AlarmQueryStatGraphPanel_Id');
			if(statTabPanel.getActiveTab().id=='AlarmTypeStatGraphPanel_Id'){
//				initAlarmTypeStatData(result,projectTabConfig);
				initAlarmTypeStatDrillDownChartData(result,projectTabConfig);
			}else if(statTabPanel.getActiveTab().id=='AlarmLevelStatGraphPanel_Id'){
//				initAlarmLevelStatData(result,projectTabConfig);
				initAlarmLeveStatDrillDownChartData(result,projectTabConfig);
			}
		},
		failure:function(){
			if(Ext.getCmp("AlarmQueryStatGraphPanel_Id")!=undefined && Ext.getCmp("AlarmQueryStatGraphPanel_Id").el!=undefined){
				Ext.getCmp("AlarmQueryStatGraphPanel_Id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType,
			statType:statType,
			alarmQueryStatRangeType:alarmQueryStatRangeType
        }
	});
}

function initAlarmTypeStatDrillDownChartData(result,projectTabConfig){
	var divId="AlarmTypeStatGraphPanelPieDiv_Id";
	var title=loginUserLanguageResource.alarmType;
	var subtitle=loginUserLanguageResource.alarmStatisticsChartSubtitle1;
	var yAxisTitle=loginUserLanguageResource.deviceCount;
	var rawSeriesData=[];
	var drilldownSeriesData=[];
	if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm && result.diagramResultAlarmDeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.FESDiagramResultAlarm, 
			y: result.diagramResultAlarmDeviceCount, 
			drilldown: 'FESDiagramResultAlarm', 
			code: 'FESDiagramResultAlarm',
			alarmType:4,
			alarmLevel:'' 
		});
		
		var singleSeriesData={name: loginUserLanguageResource.FESDiagramResultAlarm, id: 'FESDiagramResultAlarm', data: [] }
		if(result.diagramResultAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel1, y: result.diagramResultAlarmLevel1DeviceCount, code: 'FESDiagramResultAlarm_Level1',alarmType:4,alarmLevel:100 });
		}
		if(result.diagramResultAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel2, y: result.diagramResultAlarmLevel2DeviceCount, code: 'FESDiagramResultAlarm_Level2',alarmType:4,alarmLevel:200 });
		}
		if(result.diagramResultAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel3, y: result.diagramResultAlarmLevel3DeviceCount, code: 'FESDiagramResultAlarm_Level3',alarmType:4,alarmLevel:300 });
		}
		drilldownSeriesData.push(singleSeriesData);
	}

	if(projectTabConfig.AlarmQuery.CommStatusAlarm && result.commStatusAlarmDeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.commStatusAlarm, 
			y: result.commStatusAlarmDeviceCount, 
			drilldown: 'commStatusAlarm', 
			code: 'commStatusAlarm',
			alarmType:3,
			alarmLevel:'' 
		});
		
		var singleSeriesData={name: loginUserLanguageResource.commStatusAlarm, id: 'commStatusAlarm', data: [] }
		if(result.commStatusAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel1, y: result.commStatusAlarmLevel1DeviceCount, code: 'commStatusAlarm_Level1',alarmType:3,alarmLevel:100 });
		}
		if(result.commStatusAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel2, y: result.commStatusAlarmLevel2DeviceCount, code: 'commStatusAlarm_Level2',alarmType:3,alarmLevel:200 });
		}
		if(result.commStatusAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel3, y: result.commStatusAlarmLevel3DeviceCount, code: 'commStatusAlarm_Level3',alarmType:3,alarmLevel:300 });
		}
		drilldownSeriesData.push(singleSeriesData);
	}
	
	if(projectTabConfig.AlarmQuery.RunStatusAlarm && result.runStatusAlarmDeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.runStatusAlarm, 
			y: result.runStatusAlarmDeviceCount, 
			drilldown: 'runStatusAlarm', 
			code: 'runStatusAlarm',
			alarmType:6,
			alarmLevel:'' 
		});
		
		var singleSeriesData={name: loginUserLanguageResource.runStatusAlarm, id: 'runStatusAlarm', data: [] }
		if(result.runStatusAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel1, y: result.runStatusAlarmLevel1DeviceCount, code: 'runStatusAlarm_Level1',alarmType:6,alarmLevel:100 });
		}
		if(result.runStatusAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel2, y: result.runStatusAlarmLevel2DeviceCount, code: 'runStatusAlarm_Level2',alarmType:6,alarmLevel:200 });
		}
		if(result.runStatusAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel3, y: result.runStatusAlarmLevel3DeviceCount, code: 'runStatusAlarm_Level3',alarmType:6,alarmLevel:300 });
		}
		drilldownSeriesData.push(singleSeriesData);
	}
	if(projectTabConfig.AlarmQuery.NumericValueAlarm && result.numericValueAlarmDeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.numericValueAlarm, 
			y: result.numericValueAlarmDeviceCount, 
			drilldown: 'numericValueAlarm', 
			code: 'numericValueAlarm',
			alarmType:2,
			alarmLevel:'' 
		});
		
		var singleSeriesData={name: loginUserLanguageResource.numericValueAlarm, id: 'numericValueAlarm', data: [] }
		if(result.numericValueAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel1, y: result.numericValueAlarmLevel1DeviceCount, code: 'numericValueAlarm_Level1',alarmType:2,alarmLevel:100 });
		}
		if(result.numericValueAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel2, y: result.numericValueAlarmLevel2DeviceCount, code: 'numericValueAlarm_Level2',alarmType:2,alarmLevel:200 });
		}
		if(result.numericValueAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel3, y: result.numericValueAlarmLevel3DeviceCount, code: 'numericValueAlarm_Level3',alarmType:2,alarmLevel:300 });
		}
		drilldownSeriesData.push(singleSeriesData);
	}
	if(projectTabConfig.AlarmQuery.EnumValueAlarm && result.enumValueAlarmDeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.enumValueAlarm, 
			y: result.enumValueAlarmDeviceCount, 
			drilldown: 'enumValueAlarm', 
			code: 'enumValueAlarm',
			alarmType:1,
			alarmLevel:'' 
		});
		
		var singleSeriesData={name: loginUserLanguageResource.enumValueAlarm, id: 'enumValueAlarm', data: [] }
		if(result.enumValueAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel1, y: result.enumValueAlarmLevel1DeviceCount, code: 'enumValueAlarm_Level1',alarmType:1,alarmLevel:100 });
		}
		if(result.enumValueAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel2, y: result.enumValueAlarmLevel2DeviceCount, code: 'enumValueAlarm_Level2',alarmType:1,alarmLevel:200 });
		}
		if(result.enumValueAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel3, y: result.enumValueAlarmLevel3DeviceCount, code: 'enumValueAlarm_Level3',alarmType:1,alarmLevel:300 });
		}
		drilldownSeriesData.push(singleSeriesData);
	}
	if(projectTabConfig.AlarmQuery.SwitchingValueAlarm && result.switchingValueAlarmDeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.switchingValueAlarm, 
			y: result.switchingValueAlarmDeviceCount, 
			drilldown: 'switchingValueAlarm', 
			code: 'switchingValueAlarm',
			alarmType:0,
			alarmLevel:'' 
		});
		
		var singleSeriesData={name: loginUserLanguageResource.switchingValueAlarm, id: 'switchingValueAlarm', data: [] }
		if(result.switchingValueAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel1, y: result.switchingValueAlarmLevel1DeviceCount, code: 'switchingValueAlarm_Level1',alarmType:0,alarmLevel:100 });
		}
		if(result.switchingValueAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel2, y: result.switchingValueAlarmLevel2DeviceCount, code: 'switchingValueAlarm_Level2',alarmType:0,alarmLevel:200 });
		}
		if(result.switchingValueAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.alarmLevel3, y: result.switchingValueAlarmLevel3DeviceCount, code: 'switchingValueAlarm_Level3',alarmType:0,alarmLevel:300 });
		}
		drilldownSeriesData.push(singleSeriesData);
	}
	
	showAlarmStatDrillDownChart(title,divId, subtitle,yAxisTitle,rawSeriesData,drilldownSeriesData);
}

function initAlarmLeveStatDrillDownChartData(result,projectTabConfig){
	var divId="AlarmLevelStatGraphPanelPieDiv_Id";
	var title=loginUserLanguageResource.alarmLevel;
	var subtitle=loginUserLanguageResource.alarmStatisticsChartSubtitle2;
	var yAxisTitle=loginUserLanguageResource.deviceCount;
	var rawSeriesData=[];
	var drilldownSeriesData=[];
	
	var alarmLevel1DeviceCount=0,alarmLevel2DeviceCount=0,alarmLevel3DeviceCount=0;
	if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm){
		alarmLevel1DeviceCount+=result.diagramResultAlarmLevel1DeviceCount;
		alarmLevel2DeviceCount+=result.diagramResultAlarmLevel2DeviceCount;
		alarmLevel3DeviceCount+=result.diagramResultAlarmLevel3DeviceCount;
	}
	if(projectTabConfig.AlarmQuery.CommStatusAlarm){
		alarmLevel1DeviceCount+=result.commStatusAlarmLevel1DeviceCount;
		alarmLevel2DeviceCount+=result.commStatusAlarmLevel2DeviceCount;
		alarmLevel3DeviceCount+=result.commStatusAlarmLevel3DeviceCount;
	}
	if(projectTabConfig.AlarmQuery.RunStatusAlarm){
		alarmLevel1DeviceCount+=result.runStatusAlarmLevel1DeviceCount;
		alarmLevel2DeviceCount+=result.runStatusAlarmLevel2DeviceCount;
		alarmLevel3DeviceCount+=result.runStatusAlarmLevel3DeviceCount;
	}
	if(projectTabConfig.AlarmQuery.NumericValueAlarm){
		alarmLevel1DeviceCount+=result.numericValueAlarmLevel1DeviceCount;
		alarmLevel2DeviceCount+=result.numericValueAlarmLevel2DeviceCount;
		alarmLevel3DeviceCount+=result.numericValueAlarmLevel3DeviceCount;
	}
	if(projectTabConfig.AlarmQuery.EnumValueAlarm){
		alarmLevel1DeviceCount+=result.enumValueAlarmLevel1DeviceCount;
		alarmLevel2DeviceCount+=result.enumValueAlarmLevel2DeviceCount;
		alarmLevel3DeviceCount+=result.enumValueAlarmLevel3DeviceCount;
	}
	if(projectTabConfig.AlarmQuery.SwitchingValueAlarm){
		alarmLevel1DeviceCount+=result.switchingValueAlarmLevel1DeviceCount;
		alarmLevel2DeviceCount+=result.switchingValueAlarmLevel2DeviceCount;
		alarmLevel3DeviceCount+=result.switchingValueAlarmLevel3DeviceCount;
	}
	
	
	if(alarmLevel1DeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.alarmLevel1, 
			y: result.alarmLevel1DeviceCount, 
			drilldown: 'alarmLevel1', 
			code: 'alarmLevel1',
			alarmType:'',
			alarmLevel:100
		});
		
		var singleSeriesData={name: loginUserLanguageResource.alarmLevel1, id: 'alarmLevel1', data: [] }
		if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm && result.diagramResultAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.FESDiagramResultAlarm, y: result.diagramResultAlarmLevel1DeviceCount, code: 'FESDiagramResultAlarm_Level1',alarmType:4,alarmLevel:100 });
		}
		if(projectTabConfig.AlarmQuery.CommStatusAlarm && result.commStatusAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.commStatusAlarm, y: result.commStatusAlarmLevel1DeviceCount, code: 'commStatusAlarm_Level1',alarmType:3,alarmLevel:100 });
		}
		if(projectTabConfig.AlarmQuery.RunStatusAlarm && result.runStatusAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.runStatusAlarm, y: result.runStatusAlarmLevel1DeviceCount, code: 'runStatusAlarm_Level1',alarmType:6,alarmLevel:100 });
		}
		if(projectTabConfig.AlarmQuery.NumericValueAlarm && result.numericValueAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.numericValueAlarm, y: result.numericValueAlarmLevel1DeviceCount, code: 'numericValueAlarm_Level1',alarmType:2,alarmLevel:100 });
		}
		if(projectTabConfig.AlarmQuery.EnumValueAlarm && result.enumValueAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.enumValueAlarm, y: result.enumValueAlarmLevel1DeviceCount, code: 'enumValueAlarm_Level1',alarmType:1,alarmLevel:100 });
		}
		if(projectTabConfig.AlarmQuery.SwitchingValueAlarm && result.switchingValueAlarmLevel1DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.switchingValueAlarm, y: result.switchingValueAlarmLevel1DeviceCount, code: 'switchingValueAlarm_Level1',alarmType:0,alarmLevel:100 });
		}
		drilldownSeriesData.push(singleSeriesData);
		
	}
	if(alarmLevel2DeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.alarmLevel2, 
			y: result.alarmLevel2DeviceCount, 
			drilldown: 'alarmLevel2', 
			code: 'alarmLevel2',
			alarmType:'',
			alarmLevel:200
		});
		
		var singleSeriesData={name: loginUserLanguageResource.alarmLevel2, id: 'alarmLevel2', data: [] }
		if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm && result.diagramResultAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.FESDiagramResultAlarm, y: result.diagramResultAlarmLevel2DeviceCount, code: 'FESDiagramResultAlarm_Level2',alarmType:4,alarmLevel:200 });
		}
		if(projectTabConfig.AlarmQuery.CommStatusAlarm && result.commStatusAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.commStatusAlarm, y: result.commStatusAlarmLevel2DeviceCount, code: 'commStatusAlarm_Level2',alarmType:3,alarmLevel:200 });
		}
		if(projectTabConfig.AlarmQuery.RunStatusAlarm && result.runStatusAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.runStatusAlarm, y: result.runStatusAlarmLevel2DeviceCount, code: 'runStatusAlarm_Level2',alarmType:6,alarmLevel:200 });
		}
		if(projectTabConfig.AlarmQuery.NumericValueAlarm && result.numericValueAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.numericValueAlarm, y: result.numericValueAlarmLevel2DeviceCount, code: 'numericValueAlarm_Level2',alarmType:2,alarmLevel:200 });
		}
		if(projectTabConfig.AlarmQuery.EnumValueAlarm && result.enumValueAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.enumValueAlarm, y: result.enumValueAlarmLevel2DeviceCount, code: 'enumValueAlarm_Level2',alarmType:1,alarmLevel:200 });
		}
		if(projectTabConfig.AlarmQuery.SwitchingValueAlarm && result.switchingValueAlarmLevel2DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.switchingValueAlarm, y: result.switchingValueAlarmLevel2DeviceCount, code: 'switchingValueAlarm_Level2',alarmType:0,alarmLevel:200 });
		}
		drilldownSeriesData.push(singleSeriesData);
	}
	if(alarmLevel3DeviceCount>0){
		rawSeriesData.push({
			name: loginUserLanguageResource.alarmLevel3, 
			y: result.alarmLevel3DeviceCount, 
			drilldown: 'alarmLevel3', 
			code: 'alarmLevel3',
			alarmType:'',
			alarmLevel:300
		});
		
		var singleSeriesData={name: loginUserLanguageResource.alarmLevel3, id: 'alarmLevel3', data: [] }
		if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm && result.diagramResultAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.FESDiagramResultAlarm, y: result.diagramResultAlarmLevel3DeviceCount, code: 'FESDiagramResultAlarm_Level3',alarmType:4,alarmLevel:300 });
		}
		if(projectTabConfig.AlarmQuery.CommStatusAlarm && result.commStatusAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.commStatusAlarm, y: result.commStatusAlarmLevel3DeviceCount, code: 'commStatusAlarm_Level3',alarmType:3,alarmLevel:300 });
		}
		if(projectTabConfig.AlarmQuery.RunStatusAlarm && result.runStatusAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.runStatusAlarm, y: result.runStatusAlarmLevel3DeviceCount, code: 'runStatusAlarm_Level3',alarmType:6,alarmLevel:300 });
		}
		if(projectTabConfig.AlarmQuery.NumericValueAlarm && result.numericValueAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.numericValueAlarm, y: result.numericValueAlarmLevel3DeviceCount, code: 'numericValueAlarm_Level3',alarmType:2,alarmLevel:300 });
		}
		if(projectTabConfig.AlarmQuery.EnumValueAlarm && result.enumValueAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.enumValueAlarm, y: result.enumValueAlarmLevel3DeviceCount, code: 'enumValueAlarm_Level3',alarmType:1,alarmLevel:300 });
		}
		if(projectTabConfig.AlarmQuery.SwitchingValueAlarm && result.switchingValueAlarmLevel3DeviceCount>0){
			singleSeriesData.data.push({ name: loginUserLanguageResource.switchingValueAlarm, y: result.switchingValueAlarmLevel3DeviceCount, code: 'switchingValueAlarm_Level3',alarmType:0,alarmLevel:300 });
		}
		drilldownSeriesData.push(singleSeriesData);
	}
	
	showAlarmStatDrillDownChart(title,divId, subtitle,yAxisTitle,rawSeriesData,drilldownSeriesData);
}

function showAlarmStatDrillDownChart(title,divId, subtitle,yAxisTitle,rawSeriesData,drilldownSeriesData){
	$('#'+divId).highcharts({
		chart: {
            type: 'column',
            events: {
                drilldown: function(e) {
                    var point = e.point;
                    var opt = point.options || {};
                    var name = opt.name || point.name;
                    var code = opt.code || point.code;
                    var alarmType = opt.alarmType || point.alarmType;
                    var alarmLevel = opt.alarmLevel || point.alarmLevel;
//                    console.log('🔔 [DRILLDOWN] 点击柱子:', name, '| code:', code, '| alarmType:', alarmType, '| alarmLevel:', alarmLevel);
                    
                    Ext.getCmp('SelectedAlarmStatType_Id').setValue(alarmType);
                	Ext.getCmp('SelectedAlarmStatLevel_Id').setValue(alarmLevel);
                	var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
                	if (isNotVal(gridPanel)) {
                		gridPanel.getStore().loadPage(1);
                	}else{
                		Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
                	}
                    
                    window.lastDrillCode = code;
                    alarmStatDrillDownChartResetAllPoints(this);
                },
                drillup: function() {
//                    console.log('🔙 返回第一层');
                    alarmStatDrillDownChartResetAllPoints(this);
                    
                    Ext.getCmp('SelectedAlarmStatType_Id').setValue('');
                	Ext.getCmp('SelectedAlarmStatLevel_Id').setValue('');
                	var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
                	if (isNotVal(gridPanel)) {
                		gridPanel.getStore().loadPage(1);
                	}else{
                		Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
                	}
                }
            }
        },
        title: { text: title },
        subtitle: { text: subtitle },
        xAxis: { type: 'category' },
        yAxis: { title: { text: yAxisTitle } },
        legend: { enabled: false },
        credits: { enabled: false },
        tooltip: {
            headerFormat: '<b><span style="font-size:11px">{series.name}</span></b><br>',
            pointFormat: '<b><span style="color:{point.color}">{point.name}</span></b>: {point.y}'
        },
        plotOptions: {
            column: {
                maxPointWidth: 70
            },
            series: {
                borderWidth: 0,
                cursor: 'pointer',
                dataLabels: { enabled: true, format: '{point.y:.0f}' },
                point: {
                    events: {
                        mouseOver: function() {
                            if (this.series && this.series.chart) {
                                alarmStatDrillDownChartDimOtherPoints(this, this.series.chart);
                            }
                        },
                        mouseOut: function() {
                            if (this.series && this.series.chart) {
                                alarmStatDrillDownChartRestoreAllPointsOpacity(this.series.chart);
                            }
                        },
                        click: function(e) {
                            var point = this;
                            // 如果有 drilldown，直接返回让 Highcharts 处理下钻
                            if (point.drilldown || (point.options && point.options.drilldown)) {
                                return true;
                            }
                            // 第二层逻辑
                            if (!point.series || !point.series.chart) return false;
                            var opt = point.options || {};
                            var name = opt.name || point.name;
                            var code = opt.code || point.code;
                            var alarmType = opt.alarmType || point.alarmType;
                            var alarmLevel = opt.alarmLevel || point.alarmLevel;
                            console.log('🔔 [CLICK] 第二层柱子:', name, '| code:', code, '| alarmType:', alarmType, '| alarmLevel:', alarmLevel);
                            var isSelected = point.selected;
                            if (isSelected) {
                                point.select(false);
                                alarmStatDrillDownChartResetPointStyle(point);
//                                console.log('❌ 取消选中:', point.series.name, point.y, 'code:', code);
                                
                                var statTabPanel=Ext.getCmp('AlarmQueryStatGraphPanel_Id');
                    			if(statTabPanel.getActiveTab().id=='AlarmTypeStatGraphPanel_Id'){
                    				Ext.getCmp('SelectedAlarmStatLevel_Id').setValue('');
                    			}else if(statTabPanel.getActiveTab().id=='AlarmLevelStatGraphPanel_Id'){
                    				Ext.getCmp('SelectedAlarmStatType_Id').setValue('');
                    			}
                            } else {
                                alarmStatDrillDownChartResetAllPoints(point.series.chart);
                                point.select(true);
                                alarmStatDrillDownChartApplyHighlightEffect(point);
//                                console.log('✅ 选中:', point.series.name, point.y, 'code:', code);
                                
                                var statTabPanel=Ext.getCmp('AlarmQueryStatGraphPanel_Id');
                    			if(statTabPanel.getActiveTab().id=='AlarmTypeStatGraphPanel_Id'){
                    				Ext.getCmp('SelectedAlarmStatLevel_Id').setValue(alarmLevel);
                    			}else if(statTabPanel.getActiveTab().id=='AlarmLevelStatGraphPanel_Id'){
                    				Ext.getCmp('SelectedAlarmStatType_Id').setValue(alarmType);
                    			}
                            }
                            e.stopPropagation();
                            var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
                        	if (isNotVal(gridPanel)) {
                        		gridPanel.getStore().loadPage(1);
                        	}else{
                        		Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
                        	}
                           
                            
                            return false;
                        }
                    }
                },
                states: {
                    select: {
                        color: {
                            linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                            stops: [[0, '#f59e0b'], [1, '#b45309']]
                        },
                        borderColor: '#ffffff',
                        borderWidth: 2
                    }
                }
            }
        },
        series: [{
            name: title,
            colorByPoint: true,
            data: rawSeriesData
        }],
        drilldown: {
            breadcrumbs: { position: { align: 'right' } },
            series: drilldownSeriesData
        }
	});
	
	
}


function initAlarmLevelStatData(result,projectTabConfig){
	var divId="AlarmLevelStatGraphPanelPieDiv_Id";
	var title=loginUserLanguageResource.alarmLevel;
	var seriesData=[]
	var categories=[];
	
	var FESDiagramResultAlarmData={name: loginUserLanguageResource.FESDiagramResultAlarm,data: [],stack: 'alarm'};
	var commStatusAlarmData={name: loginUserLanguageResource.commStatusAlarm, data: [],stack: 'alarm'};
	var runStatusAlarmData={name: loginUserLanguageResource.runStatusAlarm,data: [],stack: 'alarm'};
	var numericValueAlarm={name: loginUserLanguageResource.numericValueAlarm,data: [],stack: 'alarm'};
	var enumValueAlarm={name: loginUserLanguageResource.enumValueAlarm,data: [],stack: 'alarm'};
	var switchingValueAlarm={name: loginUserLanguageResource.switchingValueAlarm,data: [],stack: 'alarm'};
	
	if(result.alarmLevel1DeviceCount>0){
		categories.push(loginUserLanguageResource.alarmLevel1);
		FESDiagramResultAlarmData.data.push({
        	y: result.diagramResultAlarmLevel1DeviceCount,
        	name: loginUserLanguageResource.alarmLevel1+'-'+loginUserLanguageResource.FESDiagramResultAlarm, 
			code: 'diagramResultAlarmLevel1',
			alarmType:4,
			alarmLevel:100
        });
		
		commStatusAlarmData.data.push({
        	y: result.commStatusAlarmLevel1DeviceCount,
        	name: loginUserLanguageResource.alarmLevel1+'-'+loginUserLanguageResource.commStatusAlarm, 
			code: 'commStatusAlarmLevel1',
			alarmType:3,
			alarmLevel:100
        });
		
		runStatusAlarmData.data.push({
        	y: result.runStatusAlarmLevel1DeviceCount,
        	name: loginUserLanguageResource.alarmLevel1+'-'+loginUserLanguageResource.runStatusAlarm, 
			code: 'runStatusAlarmLevel1',
			alarmType:6,
			alarmLevel:100
        });
		
		numericValueAlarm.data.push({
        	y: result.numericValueAlarmLevel1DeviceCount,
        	name: loginUserLanguageResource.alarmLevel1+'-'+loginUserLanguageResource.numericValueAlarm, 
			code: 'numericValueAlarmLevel1',
			alarmType:2,
			alarmLevel:100
        });
		
		enumValueAlarm.data.push({
        	y: result.enumValueAlarmLevel1DeviceCount,
        	name: loginUserLanguageResource.alarmLevel1+'-'+loginUserLanguageResource.enumValueAlarm, 
			code: 'enumValueAlarmLevel1',
			alarmType:1,
			alarmLevel:100
        });
		
		switchingValueAlarm.data.push({
        	y: result.switchingValueAlarmLevel1DeviceCount,
        	name: loginUserLanguageResource.alarmLevel1+'-'+loginUserLanguageResource.switchingValueAlarm, 
			code: 'switchingValueAlarmLevel1',
			alarmType:0,
			alarmLevel:100
        });
	}
	
	if(result.alarmLevel2DeviceCount>0){
		categories.push(loginUserLanguageResource.alarmLevel2);
		FESDiagramResultAlarmData.data.push({
        	y: result.diagramResultAlarmLevel2DeviceCount,
        	name: loginUserLanguageResource.alarmLevel2+'-'+loginUserLanguageResource.FESDiagramResultAlarm, 
			code: 'diagramResultAlarmLevel2',
			alarmType:4,
			alarmLevel:200
        });
		
		commStatusAlarmData.data.push({
        	y: result.commStatusAlarmLevel2DeviceCount,
        	name: loginUserLanguageResource.alarmLevel2+'-'+loginUserLanguageResource.commStatusAlarm, 
			code: 'commStatusAlarmLevel2',
			alarmType:3,
			alarmLevel:200
        });
		
		runStatusAlarmData.data.push({
        	y: result.runStatusAlarmLevel2DeviceCount,
        	name: loginUserLanguageResource.alarmLevel2+'-'+loginUserLanguageResource.runStatusAlarm, 
			code: 'runStatusAlarmLevel2',
			alarmType:6,
			alarmLevel:200
        });
		
		numericValueAlarm.data.push({
        	y: result.numericValueAlarmLevel2DeviceCount,
        	name: loginUserLanguageResource.alarmLevel2+'-'+loginUserLanguageResource.numericValueAlarm, 
			code: 'numericValueAlarmLevel2',
			alarmType:2,
			alarmLevel:200
        });
		
		enumValueAlarm.data.push({
        	y: result.enumValueAlarmLevel2DeviceCount,
        	name: loginUserLanguageResource.alarmLevel2+'-'+loginUserLanguageResource.enumValueAlarm, 
			code: 'enumValueAlarmLevel2',
			alarmType:1,
			alarmLevel:200
        });
		
		switchingValueAlarm.data.push({
        	y: result.switchingValueAlarmLevel2DeviceCount,
        	name: loginUserLanguageResource.alarmLevel2+'-'+loginUserLanguageResource.switchingValueAlarm, 
			code: 'switchingValueAlarmLevel2',
			alarmType:0,
			alarmLevel:200
        });
	}
	
	if(result.alarmLevel3DeviceCount>0){
		categories.push(loginUserLanguageResource.alarmLevel3);
		FESDiagramResultAlarmData.data.push({
        	y: result.diagramResultAlarmLevel3DeviceCount,
        	name: loginUserLanguageResource.alarmLevel3+'-'+loginUserLanguageResource.FESDiagramResultAlarm, 
			code: 'diagramResultAlarmLevel3',
			alarmType:4,
			alarmLevel:300
        });
		
		commStatusAlarmData.data.push({
        	y: result.commStatusAlarmLevel3DeviceCount,
        	name: loginUserLanguageResource.alarmLevel3+'-'+loginUserLanguageResource.commStatusAlarm, 
			code: 'commStatusAlarmLevel3',
			alarmType:3,
			alarmLevel:300
        });
		
		runStatusAlarmData.data.push({
        	y: result.runStatusAlarmLevel3DeviceCount,
        	name: loginUserLanguageResource.alarmLevel3+'-'+loginUserLanguageResource.runStatusAlarm, 
			code: 'runStatusAlarmLevel3',
			alarmType:6,
			alarmLevel:300
        });
		
		numericValueAlarm.data.push({
        	y: result.numericValueAlarmLevel3DeviceCount,
        	name: loginUserLanguageResource.alarmLevel3+'-'+loginUserLanguageResource.numericValueAlarm, 
			code: 'numericValueAlarmLevel3',
			alarmType:2,
			alarmLevel:300
        });
		
		enumValueAlarm.data.push({
        	y: result.enumValueAlarmLevel3DeviceCount,
        	name: loginUserLanguageResource.alarmLevel3+'-'+loginUserLanguageResource.enumValueAlarm, 
			code: 'enumValueAlarmLevel3',
			alarmType:1,
			alarmLevel:300
        });
		
		switchingValueAlarm.data.push({
        	y: result.switchingValueAlarmLevel3DeviceCount,
        	name: loginUserLanguageResource.alarmLevel3+'-'+loginUserLanguageResource.switchingValueAlarm, 
			code: 'switchingValueAlarmLevel3',
			alarmType:0,
			alarmLevel:300
        });
	}
	
	
	if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm && result.diagramResultAlarmDeviceCount>0){
		seriesData.push(FESDiagramResultAlarmData);
	}
	if(projectTabConfig.AlarmQuery.CommStatusAlarm && result.commStatusAlarmDeviceCount>0){
		seriesData.push(commStatusAlarmData);
	}
	if(projectTabConfig.AlarmQuery.RunStatusAlarm && result.runStatusAlarmDeviceCount>0){
		seriesData.push(runStatusAlarmData);
	}
	if(projectTabConfig.AlarmQuery.NumericValueAlarm && result.numericValueAlarmDeviceCount>0){
		seriesData.push(numericValueAlarm);
	}
	if(projectTabConfig.AlarmQuery.EnumValueAlarm && result.enumValueAlarmDeviceCount>0){
		seriesData.push(enumValueAlarm);
	}
	if(projectTabConfig.AlarmQuery.SwitchingValueAlarm && result.switchingValueAlarmDeviceCount>0){
		seriesData.push(switchingValueAlarm);
	}
	
	
	showAlarmQueryStatDataColChat(title,divId, loginUserLanguageResource.deviceCount, categories,seriesData);
}

function initAlarmTypeStatData(result,projectTabConfig){
	var divId="AlarmTypeStatGraphPanelPieDiv_Id";
	var title=loginUserLanguageResource.alarmType;
	var categories=[];
	var seriesData=[]
	var level1Data={
			name: loginUserLanguageResource.alarmLevel1,
            data: [],
            stack: 'alarm'
	};
	var level2Data={
			name: loginUserLanguageResource.alarmLevel2,
            data: [],
            stack: 'alarm'
	};
	var level3Data={
			name: loginUserLanguageResource.alarmLevel3,
            data: [],
            stack: 'alarm'
	};
	
	if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm && result.diagramResultAlarmDeviceCount>0){
		categories.push(loginUserLanguageResource.FESDiagramResultAlarm);
		
		level1Data.data.push({
			y: result.diagramResultAlarmLevel1DeviceCount, 
			name: loginUserLanguageResource.FESDiagramResultAlarm+'-'+loginUserLanguageResource.alarmLevel1, 
			code: 'diagramResultAlarmLevel1',
			alarmType:4,
			alarmLevel:100
		});
		
		level2Data.data.push({
			y: result.diagramResultAlarmLevel2DeviceCount, 
			name: loginUserLanguageResource.FESDiagramResultAlarm+'-'+loginUserLanguageResource.alarmLevel2, 
			code: 'diagramResultAlarmLevel2',
			alarmType:4,
			alarmLevel:200
		});
		
		level3Data.data.push({
			y: result.diagramResultAlarmLevel3DeviceCount, 
			name: loginUserLanguageResource.FESDiagramResultAlarm+'-'+loginUserLanguageResource.alarmLevel3, 
			code: 'diagramResultAlarmLevel3',
			alarmType:4,
			alarmLevel:300
		});
	}
	if(projectTabConfig.AlarmQuery.CommStatusAlarm && result.commStatusAlarmDeviceCount>0){
		categories.push(loginUserLanguageResource.commStatusAlarm);
		
		level1Data.data.push({
			y: result.commStatusAlarmLevel1DeviceCount, 
			name: loginUserLanguageResource.commStatusAlarm+'-'+loginUserLanguageResource.alarmLevel1, 
			code: 'commStatusAlarm',
			alarmType:3,
			alarmLevel:100
		});
		
		level2Data.data.push({
			y: result.commStatusAlarmLevel2DeviceCount, 
			name: loginUserLanguageResource.commStatusAlarm+'-'+loginUserLanguageResource.alarmLevel2, 
			code: 'commStatusAlarm',
			alarmType:3,
			alarmLevel:200
		});
		
		level3Data.data.push({
			y: result.commStatusAlarmLevel3DeviceCount, 
			name: loginUserLanguageResource.commStatusAlarm+'-'+loginUserLanguageResource.alarmLevel3, 
			code: 'commStatusAlarm',
			alarmType:3,
			alarmLevel:300
		});
	}
	if(projectTabConfig.AlarmQuery.RunStatusAlarm && result.runStatusAlarmDeviceCount>0){
		categories.push(loginUserLanguageResource.runStatusAlarm);
		level1Data.data.push({
			y: result.runStatusAlarmLevel1DeviceCount, 
			name: loginUserLanguageResource.runStatusAlarm+'-'+loginUserLanguageResource.alarmLevel1, 
			code: 'runStatusAlarm',
			alarmType:6,
			alarmLevel:100
		});
		
		level2Data.data.push({
			y: result.runStatusAlarmLevel2DeviceCount, 
			name: loginUserLanguageResource.runStatusAlarm+'-'+loginUserLanguageResource.alarmLevel2, 
			code: 'runStatusAlarm',
			alarmType:6,
			alarmLevel:200
		});
		
		level3Data.data.push({
			y: result.runStatusAlarmLevel3DeviceCount, 
			name: loginUserLanguageResource.runStatusAlarm+'-'+loginUserLanguageResource.alarmLevel3, 
			code: 'runStatusAlarm',
			alarmType:6,
			alarmLevel:300
		});
	}
	if(projectTabConfig.AlarmQuery.NumericValueAlarm && result.numericValueAlarmDeviceCount>0){
		categories.push(loginUserLanguageResource.numericValueAlarm);
		level1Data.data.push({
			y: result.numericValueAlarmLevel1DeviceCount, 
			name: loginUserLanguageResource.numericValueAlarm+'-'+loginUserLanguageResource.alarmLevel1, 
			code: 'numericValueAlarm',
			alarmType:2,
			alarmLevel:100
		});
		
		level2Data.data.push({
			y: result.numericValueAlarmLevel2DeviceCount, 
			name: loginUserLanguageResource.numericValueAlarm+'-'+loginUserLanguageResource.alarmLevel2, 
			code: 'numericValueAlarm',
			alarmType:2,
			alarmLevel:200
		});
		
		level3Data.data.push({
			y: result.numericValueAlarmLevel3DeviceCount, 
			name: loginUserLanguageResource.numericValueAlarm+'-'+loginUserLanguageResource.alarmLevel3, 
			code: 'numericValueAlarm',
			alarmType:2,
			alarmLevel:300
		});
	}
	if(projectTabConfig.AlarmQuery.EnumValueAlarm && result.enumValueAlarmDeviceCount>0){
		categories.push(loginUserLanguageResource.enumValueAlarm);
		level1Data.data.push({
			y: result.enumValueAlarmLevel1DeviceCount, 
			name: loginUserLanguageResource.enumValueAlarm+'-'+loginUserLanguageResource.alarmLevel1, 
			code: 'enumValueAlarm',
			alarmType:1,
			alarmLevel:100
		});
		
		level2Data.data.push({
			y: result.enumValueAlarmLevel2DeviceCount, 
			name: loginUserLanguageResource.enumValueAlarm+'-'+loginUserLanguageResource.alarmLevel2, 
			code: 'enumValueAlarm',
			alarmType:1,
			alarmLevel:200
		});
		
		level3Data.data.push({
			y: result.enumValueAlarmLevel3DeviceCount, 
			name: loginUserLanguageResource.enumValueAlarm+'-'+loginUserLanguageResource.alarmLevel3, 
			code: 'enumValueAlarm',
			alarmType:1,
			alarmLevel:300
		});
	}
	if(projectTabConfig.AlarmQuery.SwitchingValueAlarm && result.switchingValueAlarmDeviceCount>0){
		categories.push(loginUserLanguageResource.switchingValueAlarm);
		level1Data.data.push({
			y: result.switchingValueAlarmLevel1DeviceCount, 
			name: loginUserLanguageResource.switchingValueAlarm+'-'+loginUserLanguageResource.alarmLevel1, 
			code: 'switchingValueAlarm',
			alarmType:0,
			alarmLevel:100
		});
		
		level2Data.data.push({
			y: result.switchingValueAlarmLevel2DeviceCount, 
			name: loginUserLanguageResource.switchingValueAlarm+'-'+loginUserLanguageResource.alarmLevel2, 
			code: 'switchingValueAlarm',
			alarmType:0,
			alarmLevel:200
		});
		
		level3Data.data.push({
			y: result.switchingValueAlarmLevel3DeviceCount, 
			name: loginUserLanguageResource.switchingValueAlarm+'-'+loginUserLanguageResource.alarmLevel3, 
			code: 'switchingValueAlarm',
			alarmType:0,
			alarmLevel:300
		});
	}
	
	seriesData.push(level1Data);
	seriesData.push(level2Data);
	seriesData.push(level3Data);
	
	showAlarmQueryStatDataColChat(title,divId, loginUserLanguageResource.deviceCount, categories,seriesData);
}

function showAlarmQueryStatDataColChat(title,divId,name,categories,seriesData){
	$('#'+divId).highcharts({
		chart: {                                                                             
            type: 'column',      
            borderWidth : 0,
            zoomType: 'xy'                   
        }, 
        credits : {
			enabled : false
		},
        title: { text: title },
        xAxis: { categories: categories },
        yAxis: { title: { text: name } },
        tooltip: {
//            format: '<b>{point.name}</b><br/>{series.name}: {y}<br/>总报警数(该柱): {point.stackTotal}'
        	formatter: function() {
                // this.point.stackTotal 即为当前柱子所有系列值的总和
                var stackTotal = this.point.stackTotal !== undefined ? this.point.stackTotal : 0;
                return '<b>' + this.point.name + '</b><br/>' +
                       this.series.name + ': ' + this.y + '<br/>' +
                       this.point.category+'-'+'总数'+': ' + stackTotal;
            }
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                cursor: 'pointer',
                maxPointWidth:70,
                states: {
                    select: {
                        // 选中时的渐变颜色（琥珀色渐变）
                        color: {
                            linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                            stops: [
                                [0, '#3b82f6'],
                                [1, '#1d4ed8']
                            ]
                        },
                        borderColor: '#fbbf24',
                        borderWidth: 2
                    }
                },
                point: {
                    events: {
                        // 鼠标移入：淡化其他点，当前点保持正常
                        mouseOver: function () {
                            dimAlarmStatChartOtherPoints(this, this.series.chart);
                        },
                        // 鼠标移出：恢复所有点的透明度
                        mouseOut: function () {
                            restoreAlarmStatChartAllPointsOpacity(this.series.chart);
                        },
                        // 点击选中逻辑（保持不变）
                        click: function () {
                            const currentPoint = this;
                            const chart = currentPoint.series.chart;

                            const isSelected = currentPoint.selected;

                            if (isSelected) {
                            	Ext.getCmp('SelectedAlarmStatType_Id').setValue('');
                            	Ext.getCmp('SelectedAlarmStatLevel_Id').setValue('');
                            	
                                currentPoint.select(false);
                                resetAlarmStatChartPointStyle(currentPoint);
                                console.log('❌ 取消选中:', currentPoint.series.name, currentPoint.category, 'code:', currentPoint.code);
                            } else {
                                resetAlarmStatChartAllPoints(chart);
                                currentPoint.select(true);
                                applyAlarmStatChartHighlightEffect(currentPoint);
                                console.log('✅ 选中:', currentPoint.series.name, currentPoint.category, 'code:', currentPoint.code);
                                
                                Ext.getCmp('SelectedAlarmStatType_Id').setValue(currentPoint.alarmType);
                            	Ext.getCmp('SelectedAlarmStatLevel_Id').setValue(currentPoint.alarmLevel);
                            }
                            
                        	var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
                        	if (isNotVal(gridPanel)) {
                        		gridPanel.getStore().loadPage(1);
                        	}else{
                        		Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
                        	}
                        }
                    }
                }
            }
        },
        exporting:{    
            enabled:true, 
            fallbackToExportServer: false,
            filename: title,    
            sourceWidth: $("#"+divId)[0].offsetWidth,
            sourceHeight: $("#"+divId)[0].offsetHeight
        },
        legend: {                                                                            
            enabled: false
        },  
        series: seriesData
	});
}

//重置所有点的透明度
function restoreAlarmStatChartAllPointsOpacity(chart) {
    chart.series.forEach(series => {
        series.points.forEach(point => {
            if (point.graphic && point.graphic.element) {
                point.graphic.element.style.opacity = '';
            }
        });
    });
}

// 淡化除当前点以及已选中点之外的所有点
function dimAlarmStatChartOtherPoints(currentPoint, chart) {
    chart.series.forEach(series => {
        series.points.forEach(point => {
            if (point === currentPoint) {
                // 当前点恢复正常透明度
                if (point.graphic && point.graphic.element) {
                    point.graphic.element.style.opacity = '';
                }
            } else if (point.selected) {
                // 已选中的点保持不变（不淡化）
                if (point.graphic && point.graphic.element) {
                    point.graphic.element.style.opacity = '';
                }
            } else {
                // 其他点淡化
                if (point.graphic && point.graphic.element) {
                    point.graphic.element.style.opacity = '0.4';
                }
            }
        });
    });
}

// 清除所有系列中所有小片的选中样式（保留选中状态清除）
function resetAlarmStatChartAllPoints(chart) {
    chart.series.forEach(series => {
        series.points.forEach(point => {
            if (point.graphic && point.graphic.element) {
                const el = point.graphic.element;
                el.style.transform = '';
                el.style.filter = '';
            }
            if (point.selected) point.select(false);
        });
    });
}

// 清除单个小片的样式（不改变 selected 状态）
function resetAlarmStatChartPointStyle(point) {
    if (point.graphic && point.graphic.element) {
        const el = point.graphic.element;
        el.style.transform = '';
        el.style.filter = '';
    }
}

// 应用高亮效果：向上平移 + 阴影（无水平偏移）
function applyAlarmStatChartHighlightEffect(point) {
    if (!point.graphic || !point.graphic.element) return;
    const el = point.graphic.element;
    el.style.transform = 'translateY(-6px)';
    el.style.filter = 'drop-shadow(0 4px 8px rgba(0,0,0,0.3))';
}

function updateAlarmQueryTabpanelContent(){
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	if(tabPanel.getActiveTab()==undefined){
		var deviceType=getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
		var projectTabConfig=getProjectTabInstanceInfoByDeviceType(deviceType);
		
		if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm==false
				&& projectTabConfig.AlarmQuery.RunStatusAlarm==false
				&& projectTabConfig.AlarmQuery.CommStatusAlarm==false
				&& projectTabConfig.AlarmQuery.NumericValueAlarm==false
				&& projectTabConfig.AlarmQuery.EnumValueAlarm==false
				&& projectTabConfig.AlarmQuery.SwitchingValueAlarm==false){
			Ext.getCmp('AlarmQuerySecondTabPanel').getDockedItems('toolbar[dock="top"]')[0].setVisible(false);
		}else{
			Ext.getCmp('AlarmQuerySecondTabPanel').getDockedItems('toolbar[dock="top"]')[0].setVisible(true);
		}
		
		if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm==false){
			tabPanel.remove(Ext.getCmp("FESDiagramResultAlarmInfoTabPanel_Id"));
		}else{
			var FESDiagramResultAlarmInfoTabPanel = tabPanel.getComponent("FESDiagramResultAlarmInfoTabPanel_Id");
			if(FESDiagramResultAlarmInfoTabPanel==undefined){
				tabPanel.insert(0,AlarmQuerySecondTabPanelItems[0]);
			}
		}
		
		if(projectTabConfig.AlarmQuery.CommStatusAlarm==false){
			tabPanel.remove(Ext.getCmp("CommunicationAlarmInfoTabPanel_Id"));
		}else{
			var CommunicationAlarmInfoTabPanel = tabPanel.getComponent("CommunicationAlarmInfoTabPanel_Id");
			if(CommunicationAlarmInfoTabPanel==undefined){
				tabPanel.insert(1,AlarmQuerySecondTabPanelItems[1]);
			}
		}
		
		if(projectTabConfig.AlarmQuery.RunStatusAlarm==false){
			tabPanel.remove(Ext.getCmp("RunStatusAlarmInfoTabPanel_Id"));
		}else{
			var RunStatusAlarmInfoTabPanel = tabPanel.getComponent("RunStatusAlarmInfoTabPanel_Id");
			if(RunStatusAlarmInfoTabPanel==undefined){
				tabPanel.insert(2,AlarmQuerySecondTabPanelItems[2]);
			}
		}
		
		if(projectTabConfig.AlarmQuery.NumericValueAlarm==false){
			tabPanel.remove(Ext.getCmp("NumericValueAlarmInfoTabPanel_Id"));
		}else{
			var NumericValueAlarmInfoTabPanel = tabPanel.getComponent("NumericValueAlarmInfoTabPanel_Id");
			if(NumericValueAlarmInfoTabPanel==undefined){
				tabPanel.insert(3,AlarmQuerySecondTabPanelItems[3]);
			}
		}
		
		if(projectTabConfig.AlarmQuery.EnumValueAlarm==false){
			tabPanel.remove(Ext.getCmp("EnumValueAlarmInfoTabPanel_Id"));
		}else{
			var EnumValueAlarmInfoTabPanel = tabPanel.getComponent("EnumValueAlarmInfoTabPanel_Id");
			if(EnumValueAlarmInfoTabPanel==undefined){
				tabPanel.insert(4,AlarmQuerySecondTabPanelItems[4]);
			}
		}

		if(projectTabConfig.AlarmQuery.SwitchingValueAlarm==false){
			tabPanel.remove(Ext.getCmp("SwitchingValueAlarmInfoTabPanel_Id"));
		}else{
			var SwitchingValueAlarmInfoTabPanel = tabPanel.getComponent("SwitchingValueAlarmInfoTabPanel_Id");
			if(SwitchingValueAlarmInfoTabPanel==undefined){
				tabPanel.insert(5,AlarmQuerySecondTabPanelItems[5]);
			}
		}
	}
}

function deviceAlarmQueryDataRefresh(){
	
	
	
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
	var firstDeviceType=getDeviceTypeFromTabId_first("AlarmQueryRootTabPanel");
//	var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
	
	Ext.getCmp("selectedDeviceType_global").setValue(deviceType);
	Ext.getCmp("selectedFirstDeviceType_global").setValue(firstDeviceType); 
	
	var projectTabConfig=getProjectTabInstanceInfoByDeviceType(deviceType);
	
	if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm==false
			&& projectTabConfig.AlarmQuery.RunStatusAlarm==false
			&& projectTabConfig.AlarmQuery.CommStatusAlarm==false
			&& projectTabConfig.AlarmQuery.NumericValueAlarm==false
			&& projectTabConfig.AlarmQuery.EnumValueAlarm==false
			&& projectTabConfig.AlarmQuery.SwitchingValueAlarm==false){
		Ext.getCmp('AlarmQuerySecondTabPanel').getDockedItems('toolbar[dock="top"]')[0].setVisible(false);
	}else{
		Ext.getCmp('AlarmQuerySecondTabPanel').getDockedItems('toolbar[dock="top"]')[0].setVisible(true);
	}
	
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	var activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
	
	var tabChange=false;
	
	if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm==false){
		tabPanel.remove(Ext.getCmp("FESDiagramResultAlarmInfoTabPanel_Id"));
		if(activeId=="FESDiagramResultAlarmInfoTabPanel_Id"){
			tabChange=true;
		}
	}else{
		var FESDiagramResultAlarmInfoTabPanel = tabPanel.getComponent("FESDiagramResultAlarmInfoTabPanel_Id");
		if(FESDiagramResultAlarmInfoTabPanel==undefined){
			tabPanel.insert(0,AlarmQuerySecondTabPanelItems[0]);
		}
	}
	
	if(projectTabConfig.AlarmQuery.CommStatusAlarm==false){
		tabPanel.remove(Ext.getCmp("CommunicationAlarmInfoTabPanel_Id"));
		if(activeId=="CommunicationAlarmInfoTabPanel_Id"){
			tabChange=true;
		}
	}else{
		var CommunicationAlarmInfoTabPanel = tabPanel.getComponent("CommunicationAlarmInfoTabPanel_Id");
		if(CommunicationAlarmInfoTabPanel==undefined){
			tabPanel.insert(1,AlarmQuerySecondTabPanelItems[1]);
		}
	}
	
	if(projectTabConfig.AlarmQuery.RunStatusAlarm==false){
		tabPanel.remove(Ext.getCmp("RunStatusAlarmInfoTabPanel_Id"));
		if(activeId=="RunStatusAlarmInfoTabPanel_Id"){
			tabChange=true;
		}
	}else{
		var RunStatusAlarmInfoTabPanel = tabPanel.getComponent("RunStatusAlarmInfoTabPanel_Id");
		if(RunStatusAlarmInfoTabPanel==undefined){
			tabPanel.insert(2,AlarmQuerySecondTabPanelItems[2]);
		}
	}
	
	if(projectTabConfig.AlarmQuery.NumericValueAlarm==false){
		tabPanel.remove(Ext.getCmp("NumericValueAlarmInfoTabPanel_Id"));
		if(activeId=="NumericValueAlarmInfoTabPanel_Id"){
			tabChange=true;
		}
	}else{
		var NumericValueAlarmInfoTabPanel = tabPanel.getComponent("NumericValueAlarmInfoTabPanel_Id");
		if(NumericValueAlarmInfoTabPanel==undefined){
			tabPanel.insert(3,AlarmQuerySecondTabPanelItems[3]);
		}
	}
	
	if(projectTabConfig.AlarmQuery.EnumValueAlarm==false){
		tabPanel.remove(Ext.getCmp("EnumValueAlarmInfoTabPanel_Id"));
		if(activeId=="EnumValueAlarmInfoTabPanel_Id"){
			tabChange=true;
		}
	}else{
		var EnumValueAlarmInfoTabPanel = tabPanel.getComponent("EnumValueAlarmInfoTabPanel_Id");
		if(EnumValueAlarmInfoTabPanel==undefined){
			tabPanel.insert(4,AlarmQuerySecondTabPanelItems[4]);
		}
	}

	if(projectTabConfig.AlarmQuery.SwitchingValueAlarm==false){
		tabPanel.remove(Ext.getCmp("SwitchingValueAlarmInfoTabPanel_Id"));
		if(activeId=="SwitchingValueAlarmInfoTabPanel_Id"){
			tabChange=true;
		}
	}else{
		var SwitchingValueAlarmInfoTabPanel = tabPanel.getComponent("SwitchingValueAlarmInfoTabPanel_Id");
		if(SwitchingValueAlarmInfoTabPanel==undefined){
			tabPanel.insert(5,AlarmQuerySecondTabPanelItems[5]);
		}
	}
	
	if(tabPanel.getActiveTab()==undefined){
		if(tabPanel.items.length>0){
			tabPanel.setActiveTab(0);
		}else{
			if (isNotVal(Ext.getCmp("AlarmQueryRootTabPanel"))) {
            	Ext.getCmp("AlarmQueryRootTabPanel").getEl().unmask();
            }
		}
	}else{
		if(!tabChange){
			activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
			if(activeId=="FESDiagramResultAlarmInfoTabPanel_Id"){
				Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
				Ext.getCmp("AlarmDeviceListComb_Id").setValue('');
				Ext.getCmp("AlarmDeviceListComb_Id").setRawValue('');
				Ext.getCmp("AlarmLevelComb_Id").setValue('');
				Ext.getCmp("AlarmLevelComb_Id").setRawValue('');
				var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
				if (isNotVal(gridPanel)) {
					gridPanel.getStore().loadPage(1);
				}else{
					Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
				}
			}else if(activeId=="RunStatusAlarmInfoTabPanel_Id"){
				Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
				Ext.getCmp("AlarmDeviceListComb_Id").setValue('');
				Ext.getCmp("AlarmDeviceListComb_Id").setRawValue('');
				Ext.getCmp("AlarmLevelComb_Id").setValue('');
				Ext.getCmp("AlarmLevelComb_Id").setRawValue('');
				var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
				if (isNotVal(gridPanel)) {
					gridPanel.getStore().loadPage(1);
				}else{
					Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
				}
			}else if(activeId=="CommunicationAlarmInfoTabPanel_Id"){
				Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
				Ext.getCmp("AlarmDeviceListComb_Id").setValue('');
				Ext.getCmp("AlarmDeviceListComb_Id").setRawValue('');
				Ext.getCmp("AlarmLevelComb_Id").setValue('');
				Ext.getCmp("AlarmLevelComb_Id").setRawValue('');
				var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
				if (isNotVal(gridPanel)) {
					gridPanel.getStore().loadPage(1);
				}else{
					Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
				}
			}else if(activeId=="NumericValueAlarmInfoTabPanel_Id"){
				Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
				Ext.getCmp("AlarmDeviceListComb_Id").setValue('');
				Ext.getCmp("AlarmDeviceListComb_Id").setRawValue('');
				Ext.getCmp("AlarmLevelComb_Id").setValue('');
				Ext.getCmp("AlarmLevelComb_Id").setRawValue('');
				var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
				if (isNotVal(gridPanel)) {
					gridPanel.getStore().loadPage(1);
				}else{
					Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
				}
			}else if(activeId=="EnumValueAlarmInfoTabPanel_Id"){
				Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
				Ext.getCmp("AlarmDeviceListComb_Id").setValue('');
				Ext.getCmp("AlarmDeviceListComb_Id").setRawValue('');
				Ext.getCmp("AlarmLevelComb_Id").setValue('');
				Ext.getCmp("AlarmLevelComb_Id").setRawValue('');
				var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
				if (isNotVal(gridPanel)) {
					gridPanel.getStore().loadPage(1);
				}else{
					Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
				}
			}else if(activeId=="SwitchingValueAlarmInfoTabPanel_Id"){
				Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
				Ext.getCmp("AlarmDeviceListComb_Id").setValue('');
				Ext.getCmp("AlarmDeviceListComb_Id").setRawValue('');
				Ext.getCmp("AlarmLevelComb_Id").setValue('');
				Ext.getCmp("AlarmLevelComb_Id").setRawValue('');
				var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
				if (isNotVal(gridPanel)) {
					gridPanel.getStore().loadPage(1);
				}else{
					Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
				}
			}
		}
	}
}

function getAlarmTypeFromTabActive(){
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	
	var alarmTypeTabActiveId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
	
	var alarmType=-1;
	if(alarmTypeTabActiveId=="FESDiagramResultAlarmInfoTabPanel_Id"){
		alarmType=4;
	}else if(alarmTypeTabActiveId=="RunStatusAlarmInfoTabPanel_Id"){
		alarmType=6;
	}else if(alarmTypeTabActiveId=="CommunicationAlarmInfoTabPanel_Id"){
		alarmType=3;
	}else if(alarmTypeTabActiveId=="NumericValueAlarmInfoTabPanel_Id"){
		alarmType=2;
	}else if(alarmTypeTabActiveId=="EnumValueAlarmInfoTabPanel_Id"){
		alarmType=1;
	}else if(alarmTypeTabActiveId=="SwitchingValueAlarmInfoTabPanel_Id"){
		alarmType=0;
	}
	return alarmType;
}

function getAlarmTypeNameFromTabActive(){
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	
	var alarmTypeTabActiveId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
	
	
	var alarmTypeName='';
	if(alarmTypeTabActiveId=="FESDiagramResultAlarmInfoTabPanel_Id"){
		alarmTypeName=loginUserLanguageResource.FESDiagramResultAlarm;
	}else if(alarmTypeTabActiveId=="RunStatusAlarmInfoTabPanel_Id"){
		alarmTypeName=loginUserLanguageResource.runStatusAlarm;
	}else if(alarmTypeTabActiveId=="CommunicationAlarmInfoTabPanel_Id"){
		alarmTypeName=loginUserLanguageResource.commStatusAlarm;
	}else if(alarmTypeTabActiveId=="NumericValueAlarmInfoTabPanel_Id"){
		alarmTypeName=loginUserLanguageResource.numericValueAlarm;
	}else if(alarmTypeTabActiveId=="EnumValueAlarmInfoTabPanel_Id"){
		alarmTypeName=loginUserLanguageResource.enumValueAlarm;
	}else if(alarmTypeTabActiveId=="SwitchingValueAlarmInfoTabPanel_Id"){
		alarmTypeName=loginUserLanguageResource.switchingValueAlarm;
	}
	return alarmTypeName;
}

function getAlarmOverViewPanIdFromTabActive(){
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	var alarmTypeTabActiveId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
	
	var alarmOverViewPanelId='';
	if(alarmTypeTabActiveId=="FESDiagramResultAlarmInfoTabPanel_Id"){
		alarmOverViewPanelId='FESDiagramResultAlarmOverviewPanel_Id';
	}else if(alarmTypeTabActiveId=="RunStatusAlarmInfoTabPanel_Id"){
		alarmOverViewPanelId='RunStatusAlarmOverviewPanel_Id';
	}else if(alarmTypeTabActiveId=="CommunicationAlarmInfoTabPanel_Id"){
		alarmOverViewPanelId='CommunicationAlarmOverviewPanel_Id';
	}else if(alarmTypeTabActiveId=="NumericValueAlarmInfoTabPanel_Id"){
		alarmOverViewPanelId='NumericValueAlarmOverviewPanel_Id';
	}else if(alarmTypeTabActiveId=="EnumValueAlarmInfoTabPanel_Id"){
		alarmOverViewPanelId='EnumValueAlarmOverviewPanel_Id';
	}else if(alarmTypeTabActiveId=="SwitchingValueAlarmInfoTabPanel_Id"){
		alarmOverViewPanelId='SwitchingValueAlarmOverviewPanel_Id';
	}
	return alarmOverViewPanelId;
}

function getAlarmDetailsDataPanIdFromTabActive(){
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	var alarmTypeTabActiveId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
	var alarmDetailsPanelId='';
	if(alarmTypeTabActiveId=="FESDiagramResultAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='FESDiagramResultAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="RunStatusAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='RunStatusAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="CommunicationAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='CommunicationAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="NumericValueAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='NumericValueAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="EnumValueAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='EnumValueAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="SwitchingValueAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='SwitchingValueAlarmDetailsPanel_Id';
	}
	return alarmDetailsPanelId;
}

function getAlarmDetailsDataPanIdFromTabActiveId(alarmTypeTabActiveId){
	var alarmDetailsPanelId='';
	if(alarmTypeTabActiveId=="FESDiagramResultAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='FESDiagramResultAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="RunStatusAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='RunStatusAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="CommunicationAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='CommunicationAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="NumericValueAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='NumericValueAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="EnumValueAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='EnumValueAlarmDetailsPanel_Id';
	}else if(alarmTypeTabActiveId=="SwitchingValueAlarmInfoTabPanel_Id"){
		alarmDetailsPanelId='SwitchingValueAlarmDetailsPanel_Id';
	}
	return alarmDetailsPanelId;
}

function alarmStatDrillDownChartSafeForEachSeries(chart, callback) {
    if (chart && chart.series && Array.isArray(chart.series)) {
        for (var i = 0; i < chart.series.length; i++) {
            callback(chart.series[i]);
        }
    }
}

function alarmStatDrillDownChartRestoreAllPointsOpacity(chart) {
    alarmStatDrillDownChartSafeForEachSeries(chart, function(series) {
        if (series.points && Array.isArray(series.points)) {
            for (var i = 0; i < series.points.length; i++) {
                var point = series.points[i];
                if (point.graphic && point.graphic.element) {
                    point.graphic.element.style.opacity = '';
                }
            }
        }
    });
}

function alarmStatDrillDownChartDimOtherPoints(currentPoint, chart) {
    alarmStatDrillDownChartSafeForEachSeries(chart, function(series) {
        if (!series.points) return;
        for (var i = 0; i < series.points.length; i++) {
            var point = series.points[i];
            var el = point.graphic && point.graphic.element;
            if (!el) continue;
            if (point === currentPoint || point.selected) {
                el.style.opacity = '';
            } else {
                el.style.opacity = '0.4';
            }
        }
    });
}

function alarmStatDrillDownChartResetAllPoints(chart) {
    alarmStatDrillDownChartSafeForEachSeries(chart, function(series) {
        if (!series.points) return;
        for (var i = 0; i < series.points.length; i++) {
            var point = series.points[i];
            if (point.graphic && point.graphic.element) {
                point.graphic.element.style.transform = '';
                point.graphic.element.style.filter = '';
            }
            if (point.selected) {
                point.select(false);
            }
        }
    });
}

function alarmStatDrillDownChartResetPointStyle(point) {
    if (point && point.graphic && point.graphic.element) {
        point.graphic.element.style.transform = '';
        point.graphic.element.style.filter = '';
    }
}

function alarmStatDrillDownChartApplyHighlightEffect(point) {
    if (point && point.graphic && point.graphic.element) {
        point.graphic.element.style.transform = 'translateY(-6px)';
        point.graphic.element.style.filter = 'drop-shadow(0 4px 8px rgba(0,0,0,0.3))';
    }
}