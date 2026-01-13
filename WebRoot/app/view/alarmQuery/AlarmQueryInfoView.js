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
        	        		beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        			Ext.getCmp("AlarmQueryRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
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
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				Ext.getCmp("AlarmQueryRootTabPanel").el.mask(loginUserLanguageResource.loading).show();
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

function exportAlarmOverviewDataExcel(orgId,deviceType,deviceName,alarmType,alarmLevel,isSendMessage,fileName,title,columnStr) {
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
	if(projectTabConfig.AlarmQuery.NumericValueAlarm==false){
		tabPanel.remove(Ext.getCmp("NumericValueAlarmInfoTabPanel_Id"));
		if(activeId=="NumericValueAlarmInfoTabPanel_Id"){
			tabChange=true;
		}
	}else{
		var NumericValueAlarmInfoTabPanel = tabPanel.getComponent("NumericValueAlarmInfoTabPanel_Id");
		if(NumericValueAlarmInfoTabPanel==undefined){
			tabPanel.insert(0,AlarmQuerySecondTabPanelItems[0]);
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
			tabPanel.insert(1,AlarmQuerySecondTabPanelItems[1]);
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
			tabPanel.insert(2,AlarmQuerySecondTabPanelItems[2]);
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
			tabPanel.insert(3,AlarmQuerySecondTabPanelItems[3]);
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
			tabPanel.insert(4,AlarmQuerySecondTabPanelItems[4]);
		}
	}
	
	if(projectTabConfig.AlarmQuery.FESDiagramResultAlarm==false){
		tabPanel.remove(Ext.getCmp("FESDiagramResultAlarmInfoTabPanel_Id"));
		if(activeId=="FESDiagramResultAlarmInfoTabPanel_Id"){
			tabChange=true;
		}
	}else{
		var FESDiagramResultAlarmInfoTabPanel = tabPanel.getComponent("FESDiagramResultAlarmInfoTabPanel_Id");
		if(FESDiagramResultAlarmInfoTabPanel==undefined){
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