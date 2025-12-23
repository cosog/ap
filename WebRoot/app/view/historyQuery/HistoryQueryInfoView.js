Ext.define("AP.view.historyQuery.HistoryQueryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.historyMonitoringInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var HistoryQueryInfoPanel = Ext.create('AP.view.historyQuery.HistoryQueryInfoPanel');
        
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
        	        	id: 'HistoryQueryRootTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        	activeTab: i==firstActiveTab?secondActiveTab:0,
        	        	border: false,
        	        	tabPosition: 'left',
        	        	iconCls: i==firstActiveTab?'check1':null,
        	        	items:[],
        	        	listeners: {
        	        		beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        			Ext.getCmp("HistoryQueryRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
        	        			oldCard.removeAll();
        	        			oldCard.setIconCls(null);
    	        				newCard.setIconCls('check2');
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
        			
        			var allSecondIds='';
        			for(var j=0;j<tabInfo.children[i].children.length;j++){
        				var secondTabPanel={
        					title: tabInfo.children[i].children[j].text,
        					tpl:tabInfo.children[i].children[j].text,
        					layout: 'fit',
        					id: 'HistoryQueryRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
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
        						id: 'HistoryQueryRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].iconCls='check2';
        			}
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].items=[];
        				panelItem.items[secondActiveTab].items.push(HistoryQueryInfoPanel);
    				}
        		}else{
        			panelItem={
        				title: tabInfo.children[i].text,
        				tpl: tabInfo.children[i].text,
        				layout: 'fit',
        				iconCls: i==firstActiveTab?'check1':null,
    					id: 'HistoryQueryRootTabPanel_'+tabInfo.children[i].deviceTypeId,
    					border: false
        			};
        			if(i==firstActiveTab){
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
        		activeTab: firstActiveTab,
        		border: false,
        		tabPosition: 'bottom',
        		items: items,
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				Ext.getCmp("HistoryQueryRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
        				if(oldCard.xtype=='tabpanel'){
        					oldCard.activeTab.removeAll();
        				}else{
        					oldCard.removeAll();
        				}
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check1');
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
	var firstDeviceType=getDeviceTypeFromTabId_first("HistoryQueryRootTabPanel");
//	var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
	
	Ext.getCmp("selectedDeviceType_global").setValue(deviceType); 
	Ext.getCmp("selectedFirstDeviceType_global").setValue(firstDeviceType); 
	
	var realtimeTurnToHisyorySign=Ext.getCmp("realtimeTurnToHisyorySign_Id").getValue();
	var realtimeTurnToHisyoryDeviceName=Ext.getCmp("realtimeTurnToHisyory_DeviceName").getValue();
	var removeHistoryFESdiagramResultStatGraphPanel=false;
	
	var tabPanel = Ext.getCmp("HistoryQueryStatTabPanel");
	var statTabActiveId = tabPanel.getActiveTab().id;
	var projectTabConfig=getProjectTabInstanceInfoByDeviceType(deviceType);
	
	var tabChange=false;
	if(projectTabConfig.DeviceHistoryQuery.FESDiagramStatPie==false){
		tabPanel.remove(Ext.getCmp("HistoryQueryFESdiagramResultStatGraphPanel_Id"));
		if(statTabActiveId=="HistoryQueryFESdiagramResultStatGraphPanel_Id"){
			tabChange=true;
		}
	}
	if(projectTabConfig.DeviceHistoryQuery.CommStatusStatPie==false){
		tabPanel.remove(Ext.getCmp("HistoryQueryStatGraphPanel_Id"));
		if(statTabActiveId=="HistoryQueryStatGraphPanel_Id"){
			tabChange=true;
		}
	}
	if(projectTabConfig.DeviceHistoryQuery.RunStatusStatPie==false){
		tabPanel.remove(Ext.getCmp("HistoryQueryRunStatusStatGraphPanel_Id"));
		if(statTabActiveId=="HistoryQueryRunStatusStatGraphPanel_Id"){
			tabChange=true;
		}
	}
	if(!tabChange){
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
		Ext.getCmp('HistoryQueryDeviceListComb_Id').setValue(realtimeTurnToHisyoryDeviceName);
		Ext.getCmp("realtimeTurnToHisyorySign_Id").setValue('');
		Ext.getCmp("realtimeTurnToHisyory_DeviceName").setValue('');
	}else{
		Ext.getCmp('HistoryQueryDeviceListComb_Id').setValue('');
		Ext.getCmp('HistoryQueryDeviceListComb_Id').setRawValue('');
	}
//	if(!removeHistoryFESdiagramResultStatGraphPanel){//如果删除了工况统计，则不刷新表格，由统计tabchange刷新
//		refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp("selectedDeviceId_global").getValue()),deviceType,Ext.getCmp("HistoryQueryDeviceListGridPanel_Id"),'AP.store.historyQuery.HistoryQueryWellListStore');
//	}
	refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp("selectedDeviceId_global").getValue()),deviceType,Ext.getCmp("HistoryQueryDeviceListGridPanel_Id"),'AP.store.historyQuery.HistoryQueryWellListStore');
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
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:true";
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

function exportHistoryQueryDeviceListExcel(orgId,deviceType,deviceName,dictDeviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,fileName,title,columnStr) {
	var timestamp=new Date().getTime();
	var key='exportHistoryQueryDeviceListData_'+deviceType+'_'+timestamp;
	
	var maskPanelId='HistoryQueryInfoDeviceListPanel_Id';
	
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
    heads=loginUserLanguageResource.idx+(isNotVal(lockedheads)?(","+lockedheads):"")+(isNotVal(unlockedheads)?(","+unlockedheads):"");
    
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&dictDeviceType=" + dictDeviceType 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&FESdiagramResultStatValue=" + URLencode(URLencode(FESdiagramResultStatValue))
    + "&commStatusStatValue=" + URLencode(URLencode(commStatusStatValue))
    + "&runStatusStatValue=" + URLencode(URLencode(runStatusStatValue))
    + "&deviceTypeStatValue=" + URLencode(URLencode(deviceTypeStatValue))
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    openExcelWindow(url + '?flag=true' + param);
};

function exportHistoryQueryDataExcel(orgId,deviceType,deviceId,deviceName,calculateType,startDate,endDate,hours,fileName,title,columnStr) {
	var timestamp=new Date().getTime();
	var key='exportHistoryQueryData_'+deviceType+'_'+timestamp;
	
	var maskPanelId='HistoryQueryDataInfoPanel_Id';
	
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
    heads=loginUserLanguageResource.idx+(isNotVal(lockedheads)?(","+lockedheads):"")+(isNotVal(unlockedheads)?(","+unlockedheads):"");
    fields="";
    heads="";
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&deviceId=" + deviceId 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&calculateType=" + calculateType 
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&hours=" + hours
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    openExcelWindow(url + '?flag=true' + param);
};

function exportHistoryQueryDiagramOverlayDataExcel(orgId,deviceType,dictDeviceType,deviceId,deviceName,resultCode,calculateType,startDate,endDate,hours,fileName,title,columnStr) {
	var timestamp=new Date().getTime();
	var key='exportHistoryQueryData_'+timestamp;
	
	var maskPanelId='HistoryDiagramOverlayTabPanel';
	
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
    heads=loginUserLanguageResource.idx+(isNotVal(lockedheads)?(","+lockedheads):"")+(isNotVal(unlockedheads)?(","+unlockedheads):"");
    fields="";
    heads="";
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&dictDeviceType=" + dictDeviceType 
    + "&deviceId=" + deviceId 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&resultCode=" + resultCode 
    + "&calculateType=" + calculateType 
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&hours=" + hours
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    openExcelWindow(url + '?flag=true' + param);
};

exportHistoryQueryDiagramTiledDataExcel = function (orgId,deviceType,deviceId,deviceName,resultCode,startDate,endDate,hours,fileName,title) {
	var tabPanel = Ext.getCmp("HistoryDiagramTabPanel");
	var activeId = tabPanel.getActiveTab().id;
	var diagramType="FSDiagram";
	var fileName=deviceName+'-'+loginUserLanguageResource.FSDiagramData;
	if(activeId=="FSDiagramTiledTabPanel_Id"){
		diagramType="FSDiagram";
		fileName=deviceName+'-'+loginUserLanguageResource.FSDiagramData;
	}else if(activeId=="PSDiagramTiledTabPanel_Id"){
		diagramType="PSDiagram";
		fileName=deviceName+'-'+loginUserLanguageResource.PSDiagramData;
	}else if(activeId=="ISDiagramTiledTabPanel_Id"){
		diagramType="ISDiagram";
		fileName=deviceName+'-'+loginUserLanguageResource.ISDiagramData;
	}
	var title=fileName;
	exportHistoryQueryFESDiagramDataExcel(orgId,deviceType,deviceId,deviceName,resultCode,startDate,endDate,hours,fileName,title,diagramType);
}


function exportHistoryQueryFESDiagramDataExcel(orgId,deviceType,deviceId,deviceName,resultCode,startDate,endDate,hours,fileName,title,diagramType) {
	var timestamp=new Date().getTime();
	var key='exportHistoryQueryData_'+timestamp;
	var maskPanelId='HistoryQueryTiledDiagramPanel';
	
	var url = context + '/historyQueryController/exportHistoryQueryDiagramTiledDataExcel';
    var fields = "";
    var heads = "";
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&deviceType=" + deviceType 
    + "&deviceId=" + deviceId 
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + "&resultCode=" + resultCode 
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&hours=" + hours
    + "&diagramType=" + diagramType
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    openExcelWindow(url + '?flag=true' + param);
};


function deviceHistoryQueryCurve(deviceType){
	var selectRowId="HistoryQueryInfoDeviceListSelectRow_Id";
	var gridPanelId="HistoryQueryDeviceListGridPanel_Id";
	var startDateId="HistoryQueryStartDate_Id";
	var startHourId="HistoryQueryStartTime_Hour_Id";
	var startMinuteId="HistoryQueryStartTime_Minute_Id";
	
	var endDateId="HistoryQueryEndDate_Id";
	var endHourId="HistoryQueryEndTime_Hour_Id";
	var endMinuteId="HistoryQueryEndTime_Minute_Id";
	
	var divId="historyQueryCurveDiv_Id";
	var panelId='historyQueryCurvePanel_Id';
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var calculateType=0;
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length>0){
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
		calculateType=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.calculateType;
	}
	var startDate=Ext.getCmp(startDateId).rawValue;
	var startTime_Hour=Ext.getCmp(startHourId).getValue();
	var startTime_Minute=Ext.getCmp(startMinuteId).getValue();
	var startTime_Second=0;
    var endDate=Ext.getCmp(endDateId).rawValue;
    var endTime_Hour=Ext.getCmp(endHourId).getValue();
	var endTime_Minute=Ext.getCmp(endMinuteId).getValue();
	var endTime_Second=0;
	var hours=getHistoryQueryHours();
	Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/historyQueryController/getHistoryQueryCurveData',
		success:function(response) {
			Ext.getCmp(panelId).getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
		    var data = result.list;
		    var graphicSet=result.graphicSet;
		    var hiddenExceptionData=result.hiddenExceptionData;
		    
		    updateVacuateRecords(result.totalCount,result.vacuateCount,"HistoryQueryVacuateCount_Id");
		    
		    
		    
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
		    var title = result.deviceName + loginUserLanguageResource.trendCurve;
		    var xTitle=loginUserLanguageResource.acqTime;
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
		        if(curveConf[i].yAxisOpposite){
		        	series_r.push(singleSeries);
		        }else{
		        	series_l.push(singleSeries);
		        }
		        
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
		   
		    initDeviceHistoryCurveChartFn(series, tickInterval, divId, title, '', '', yAxis, color_all,true,timeFormat);
		},
		failure:function(){
			Ext.getCmp(panelId).getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
            hours:hours,
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
            				text: loginUserLanguageResource.diagramSet,
            				onclick: function() {
            					var window = Ext.create("AP.view.historyQuery.HistoryCurveSetWindow", {
                                    title: loginUserLanguageResource.historyDiagramSet
                                });
                                window.show();
            				}
            			}]
            	}
            }
        },
        plotOptions: {
            spline: {
                fillOpacity: 0.3,
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

	Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
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
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initHistoryQueryStatPieOrColChat(get_rawData) {
	var divId="HistoryQueryStatGraphPanelPieDiv_Id";
	var title=loginUserLanguageResource.commStatus;
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
			pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
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
					format : '<b>{point.name}</b>: {point.y}'
				},
				events: {
					click: function(e) {
						var statSelectCommStatusId="HistoryQueryStatSelectCommStatus_Id";
						var deviceListComb_Id="HistoryQueryDeviceListComb_Id";
						var gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.historyQuery.HistoryQueryWellListStore";
						var selectedDeviceId_global="selectedDeviceId_global";
						var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");

						Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
						
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

	Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
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
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initHistoryQueryRunStatusStatPieOrColChat(get_rawData) {
	var divId="HistoryQueryRunStatusStatGraphPanelPieDiv_Id";
	var title=loginUserLanguageResource.runStatus;
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
			pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
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
					format : '<b>{point.name}</b>: {point.y}'
				},
				events: {
					click: function(e) {
						var statSelectRunStatusId="HistoryQueryStatSelectRunStatus_Id";
						var deviceListComb_Id="HistoryQueryDeviceListComb_Id";
						var gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.historyQuery.HistoryQueryWellListStore";
						var selectedDeviceId_global="selectedDeviceId_global";
						var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
						Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
						
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
	Ext.getCmp("HistoryQueryFESdiagramResultStatGraphPanel_Id").el.mask(loginUserLanguageResource.loading).show();
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
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initHistoryQueryFESDiagramResultStatPieOrColChat(get_rawData) {
	var divId="HistoryQueryFESdiagramResultStatGraphPanelPieDiv_Id";
	
	var title=loginUserLanguageResource.workType;
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
			pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
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
					format : '<b>{point.name}</b>: {point.y}'
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

	Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
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
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
};

function initHistoryQueryDeviceTypeStatPieOrColChat(get_rawData) {
	var divId="HistoryQueryDeviceTypeStatPieDiv_Id";
	
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
			pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
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
					format : '<b>{point.name}</b>: {point.y}'
				},
				events: {
					click: function(e) {
						var statSelectDeviceType_Id="HistoryQueryStatSelectDeviceType_Id";
						var deviceListComb_Id="HistoryQueryDeviceListComb_Id";
						var gridPanel_Id="HistoryQueryDeviceListGridPanel_Id";
						var store="AP.store.historyQuery.HistoryQueryWellListStore";
						var selectedDeviceId_global="selectedDeviceId_global";
						var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
						
						Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
						
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

loadHistoryDiagramTiledList = function (page) {
	var tabPanel = Ext.getCmp("HistoryDiagramTabPanel");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="FSDiagramTiledTabPanel_Id"){
		loadSurfaceCardList(page);
	}else if(activeId=="PSDiagramTiledTabPanel_Id"){
		loadPSDiagramTiledList(page);
	}else if(activeId=="ISDiagramTiledTabPanel_Id"){
		loadISDiagramTiledList(page);
	}
}

loadSurfaceCardList = function (page) {
	diagramPage=page;
    Ext.getCmp("HistoryDiagramTabPanel").mask(loginUserLanguageResource.loading); // 数据加载中，请稍后
    var start = (page - 1) * defaultGraghSize;
    page=page;
    if(page==1){
    	$("#surfaceCardContainer").html(''); // 将html内容清空
    }
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	}
	var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id').rawValue;
	var startTime_Hour=Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').getValue();
	var startTime_Minute=Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').getValue();
	var startTime_Second=0;

    var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id').rawValue;
    var endTime_Hour=Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').getValue();
	var endTime_Minute=Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').getValue();
	var endTime_Second=0;
	var selectedResult=[];
	var statSelection = Ext.getCmp("HistoryQueryFSdiagramTiledStatGrid_Id").getSelectionModel().getSelection();
	Ext.Array.each(statSelection, function (name, index, countriesItSelf) {
		selectedResult.push(statSelection[index].data.resultCode);
	});
    var resultCode=selectedResult.join(",");
	
	var hours=getHistoryQueryHours();
    Ext.Ajax.request({
        url: context + '/historyQueryController/querySurfaceCard',
        method: "POST",
        params: {
        	orgId: orgId,
    		deviceType:getDeviceTypeFromTabId("HistoryQueryRootTabPanel"),
    		deviceId:deviceId,
            deviceName:deviceName,
            resultCode:resultCode,
            startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
            hours:hours,
            limit: defaultGraghSize,
            start: start,
            page: page
        },
        success: function (response) {
        	if(page==1){
        		$("#surfaceCardContainer").html(''); // 将html内容清空
        	}
            Ext.getCmp("HistoryDiagramTabPanel").unmask(loginUserLanguageResource.loading); // 取消遮罩
            var get_rawData = Ext.decode(response.responseText); // 获取返回数据
            var diagramList = get_rawData.list; // 获取功图数据
            
            var totals = get_rawData.totals; // 总记录数
            var totalShow=get_rawData.totalShow;
            var totalPages = get_rawData.totalPages; // 总页数
            Ext.getCmp("SurfaceCardTotalPages_Id").setValue(totalPages);
            updateTotalRecords(totalShow,"HistoryFESDiagramTotalCount_Id");
            
            var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            }
            var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            }
            
            
            var HistoryDiagramTabPanel = Ext.getCmp("FSDiagramTiledTabPanel_Id"); // 获取功图列表panel信息
            var panelHeight = HistoryDiagramTabPanel.getHeight(); // panel的高度
            var panelWidth = HistoryDiagramTabPanel.getWidth(); // panel的宽度
            var scrollWidth = getScrollWidth(); // 滚动条的宽度
            
            var scroller = HistoryDiagramTabPanel.getScrollable();
            
            var scrollSize = scroller.getSize().y;      // 内容高度
            var clientSize = scroller.getClientSize().y; // 可视高度
            var hasVerticalScroll = scrollSize > clientSize;
            
            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
            var gtWidth = (panelWidth - scrollWidth) / columnCount-1; // 有滚动条时图形宽度
            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
            
            
            var gtWidth2 = gtWidth + 'px';
            var gtHeight2 = gtHeight + 'px';
//            gtWidth2 = (100/columnCount) + '%';
//            gtHeight2 = 50 + '%';
            var htmlResult = '';
            var divId = '';

            // 功图列表，创建div
            Ext.Array.each(diagramList, function (name, index, countriesItSelf) {
                var diagramId = diagramList[index].id;
                divId = 'DiagramTiled_FSDiagram_Id_' + diagramId;
                htmlResult += '<div id=\"' + divId + '\"';
                htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';float:left;"';
                htmlResult += '></div>';
            });
            $("#surfaceCardContainer").append(htmlResult);
            Ext.Array.each(diagramList, function (name, index, countriesItSelf) {
                var diagramId = diagramList[index].id;
                divId = 'DiagramTiled_FSDiagram_Id_' + diagramId;
                showSurfaceCard(diagramList[index], divId); // 调用画功图的函数，功图列表
            });
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
}


loadPSDiagramTiledList = function (page) {
	diagramPage=page;
    Ext.getCmp("HistoryDiagramTabPanel").mask(loginUserLanguageResource.loading); // 数据加载中，请稍后
    var start = (page - 1) * defaultGraghSize;
    page=page;
    if(page==1){
    	$("#PSDiagramTiledContainer").html(''); // 将html内容清空
    }
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	}
	var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id').rawValue;
	var startTime_Hour=Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').getValue();
	var startTime_Minute=Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').getValue();
	var startTime_Second=0;

    var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id').rawValue;
    var endTime_Hour=Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').getValue();
	var endTime_Minute=Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').getValue();
	var endTime_Second=0;
	var hours=getHistoryQueryHours();
	var selectedResult=[];
	var statSelection = Ext.getCmp("HistoryQueryFSdiagramTiledStatGrid_Id").getSelectionModel().getSelection();
	Ext.Array.each(statSelection, function (name, index, countriesItSelf) {
		selectedResult.push(statSelection[index].data.resultCode);
	});
    var resultCode=selectedResult.join(",");
    Ext.Ajax.request({
        url: context + '/historyQueryController/getPSDiagramTiledData',
        method: "POST",
        params: {
        	orgId: orgId,
    		deviceType:getDeviceTypeFromTabId("HistoryQueryRootTabPanel"),
    		deviceId:deviceId,
            deviceName:deviceName,
            resultCode:resultCode,
            startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
            hours:hours,
            limit: defaultGraghSize,
            start: start,
            page: page
        },
        success: function (response) {
        	if(page==1){
        		$("#PSDiagramTiledContainer").html(''); // 将html内容清空
        	}
            Ext.getCmp("HistoryDiagramTabPanel").unmask(loginUserLanguageResource.loading); // 取消遮罩
            var get_rawData = Ext.decode(response.responseText); // 获取返回数据
            var diagramList = get_rawData.list; // 获取功图数据
            
            var totals = get_rawData.totals; // 总记录数
            var totalShow=get_rawData.totalShow;
            var totalPages = get_rawData.totalPages; // 总页数
            Ext.getCmp("SurfaceCardTotalPages_Id").setValue(totalPages);
            updateTotalRecords(totalShow,"HistoryFESDiagramTotalCount_Id");
            
            var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            }
            var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            }
            
            
            var HistoryDiagramTabPanel = Ext.getCmp("PSDiagramTiledTabPanel_Id"); // 获取功图列表panel信息
            var panelHeight = HistoryDiagramTabPanel.getHeight(); // panel的高度
            var panelWidth = HistoryDiagramTabPanel.getWidth(); // panel的宽度
            var scrollWidth = getScrollWidth(); // 滚动条的宽度
            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
            var gtWidth = (panelWidth - scrollWidth) / columnCount-1; // 有滚动条时图形宽度
            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
            var gtWidth2 = gtWidth + 'px';
            var gtHeight2 = gtHeight + 'px';
//            gtWidth2 = (100/columnCount) + '%';
//            gtHeight2 = 50 + '%';
            var htmlResult = '';
            var divId = '';

            // 功图列表，创建div
            Ext.Array.each(diagramList, function (name, index, countriesItSelf) {
                var diagramId = diagramList[index].id;
                divId = 'DiagramTiled_PSDiagram_Id_' + diagramId;
                htmlResult += '<div id=\"' + divId + '\"';
                htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';float:left;"';
                htmlResult += '></div>';
            });
            $("#PSDiagramTiledContainer").append(htmlResult);
            Ext.Array.each(diagramList, function (name, index, countriesItSelf) {
                var diagramId = diagramList[index].id;
                divId = 'DiagramTiled_PSDiagram_Id_' + diagramId;
                showPSDiagram(diagramList[index], divId); // 调用画功图的函数，功图列表
            });
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
}

loadISDiagramTiledList = function (page) {
	diagramPage=page;
    Ext.getCmp("HistoryDiagramTabPanel").mask(loginUserLanguageResource.loading); // 数据加载中，请稍后
    var start = (page - 1) * defaultGraghSize;
    page=page;
    if(page==1){
    	$("#ISDiagramTiledContainer").html(''); // 将html内容清空
    }
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	}
	var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id').rawValue;
	var startTime_Hour=Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').getValue();
	var startTime_Minute=Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').getValue();
	var startTime_Second=0;

    var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id').rawValue;
    var endTime_Hour=Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').getValue();
	var endTime_Minute=Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').getValue();
	var endTime_Second=0;
	var hours=getHistoryQueryHours();
	var selectedResult=[];
	var statSelection = Ext.getCmp("HistoryQueryFSdiagramTiledStatGrid_Id").getSelectionModel().getSelection();
	Ext.Array.each(statSelection, function (name, index, countriesItSelf) {
		selectedResult.push(statSelection[index].data.resultCode);
	});
    var resultCode=selectedResult.join(",");
    Ext.Ajax.request({
        url: context + '/historyQueryController/getISDiagramTiledData',
        method: "POST",
        params: {
        	orgId: orgId,
    		deviceType:getDeviceTypeFromTabId("HistoryQueryRootTabPanel"),
    		deviceId:deviceId,
            deviceName:deviceName,
            resultCode:resultCode,
            startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
            hours:hours,
            limit: defaultGraghSize,
            start: start,
            page: page
        },
        success: function (response) {
        	if(page==1){
        		$("#ISDiagramTiledContainer").html(''); // 将html内容清空
        	}
            Ext.getCmp("HistoryDiagramTabPanel").unmask(loginUserLanguageResource.loading); // 取消遮罩
            var get_rawData = Ext.decode(response.responseText); // 获取返回数据
            var diagramList = get_rawData.list; // 获取功图数据
            
            var totals = get_rawData.totals; // 总记录数
            var totalShow=get_rawData.totalShow;
            var totalPages = get_rawData.totalPages; // 总页数
            Ext.getCmp("SurfaceCardTotalPages_Id").setValue(totalPages);
            updateTotalRecords(totalShow,"HistoryFESDiagramTotalCount_Id");
            
            var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            }
            var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            }
            
            var HistoryDiagramTabPanel = Ext.getCmp("ISDiagramTiledTabPanel_Id"); // 获取功图列表panel信息
            var panelHeight = HistoryDiagramTabPanel.getHeight(); // panel的高度
            var panelWidth = HistoryDiagramTabPanel.getWidth(); // panel的宽度
            var scrollWidth = getScrollWidth(); // 滚动条的宽度
            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
            var gtWidth = (panelWidth - scrollWidth) / columnCount-1; // 有滚动条时图形宽度
            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
            var gtWidth2 = gtWidth + 'px';
            var gtHeight2 = gtHeight + 'px';
//            gtWidth2 = (100/columnCount) + '%';
//            gtHeight2 = 50 + '%';
            var htmlResult = '';
            var divId = '';

            // 功图列表，创建div
            Ext.Array.each(diagramList, function (name, index, countriesItSelf) {
                var diagramId = diagramList[index].id;
                divId = 'DiagramTiled_ISDiagram_Id_' + diagramId;
                htmlResult += '<div id=\"' + divId + '\"';
                htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';float:left"';
                htmlResult += '></div>';
            });
            $("#ISDiagramTiledContainer").append(htmlResult);
            Ext.Array.each(diagramList, function (name, index, countriesItSelf) {
                var diagramId = diagramList[index].id;
                divId = 'DiagramTiled_ISDiagram_Id_' + diagramId;
                showASDiagram(diagramList[index], divId); // 调用画功图的函数，功图列表
            });
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
}

function createHistoryQueryDiagramOverlayTableColumn(columnInfo) {
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
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:true";
        }
        else if (attr.dataIndex.toUpperCase()=='deviceName'.toUpperCase()) {
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
}

function refreshDeviceHistoryData(){
	var tabPanel = Ext.getCmp("HistoryQueryCenterTabPanel");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="HistoryDataTabPanel"){
		var gridPanel = Ext.getCmp("HistoryQueryDataGridPanel_Id");
        if (isNotVal(gridPanel)) {
        	gridPanel.getStore().loadPage(1);
        }else{
        	Ext.create("AP.store.historyQuery.HistoryDataStore");
        }
	}else if(activeId=="HistoryQueryTiledDiagramPanel"){
		var HistoryQueryFSdiagramTiledStatGrid = Ext.getCmp("HistoryQueryFSdiagramTiledStatGrid_Id");
        if (isNotVal(HistoryQueryFSdiagramTiledStatGrid)) {
        	HistoryQueryFSdiagramTiledStatGrid.getStore().load();
        }else{
        	Ext.create("AP.store.historyQuery.HistoryQueryDiagramTiledStatStore");
        }
	}else if(activeId=="HistoryDiagramOverlayTabPanel"){
        var HistoryQueryFSdiagramOverlayStatGrid = Ext.getCmp("HistoryQueryFSdiagramOverlayStatGrid_Id");
        if (isNotVal(HistoryQueryFSdiagramOverlayStatGrid)) {
        	HistoryQueryFSdiagramOverlayStatGrid.getStore().load();
        }else{
        	Ext.create("AP.store.historyQuery.HistoryQueryDiagramOverlayStatStore");
        }
	}
}

function getHistoryQueryHours(){
	var hours='';
	var hourList=[];
	var timeRangeCheckAllStatus=Ext.getCmp('HistoryDataTimeRangeCheck_All_Id').getValue();
	if(timeRangeCheckAllStatus){
		hours='all';
	}else{
		var timeRangeCheckStatus1=Ext.getCmp('HistoryDataTimeRangeCheck1_Id').getValue();
    	var timeRangeCheckStatus2=Ext.getCmp('HistoryDataTimeRangeCheck2_Id').getValue();
    	var timeRangeCheckStatus3=Ext.getCmp('HistoryDataTimeRangeCheck3_Id').getValue();
    	var timeRangeCheckStatus4=Ext.getCmp('HistoryDataTimeRangeCheck4_Id').getValue();
    	
    	if(timeRangeCheckStatus1 && timeRangeCheckStatus2 && timeRangeCheckStatus3 && timeRangeCheckStatus4){
    		hours='all';
    	}else{
    		if(timeRangeCheckStatus1){
        		hourList.push(Ext.getCmp('HistoryDataTimeRangeCheck1_Id').getName());
        	}
        	
        	if(timeRangeCheckStatus2){
        		hourList.push(Ext.getCmp('HistoryDataTimeRangeCheck2_Id').getName());
        	}
        	
        	if(timeRangeCheckStatus3){
        		hourList.push(Ext.getCmp('HistoryDataTimeRangeCheck3_Id').getName());
        	}
        	
        	if(timeRangeCheckStatus4){
        		hourList.push(Ext.getCmp('HistoryDataTimeRangeCheck4_Id').getName());
        	}
        	
        	if(hourList.length>0){
        		hours=hourList.join(",");
        	}
    	}
	}
	return hours;
}