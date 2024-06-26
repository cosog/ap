Ext.define("AP.view.alarmQuery.AlarmQueryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.alarmQueryInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var AlarmQueryInfoPanel = Ext.create('AP.view.alarmQuery.AlarmQueryInfoPanel');
        
        var items=[];
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        				title: tabInfo.children[i].text,
        				tpl: tabInfo.children[i].text,
        				xtype: 'tabpanel',
        	        	id: 'AlarmQueryRootTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        	activeTab: 0,
        	        	border: false,
        	        	tabPosition: 'left',
        	        	items:[],
        	        	listeners: {
        	        		beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        			oldCard.removeAll();
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
//        					title: '<div style="color:#000000;font-size:11px;font-family:SimSun">'+tabInfo.children[i].children[j].text+'</div>',
        					title: tabInfo.children[i].children[j].text,
        					tpl:tabInfo.children[i].children[j].text,
        					layout: 'fit',
        					id: 'AlarmQueryRootTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
        					border: false
        				};
            			if(j==0){
            				if(i==0){
                				secondTabPanel.items=[];
                				secondTabPanel.items.push(AlarmQueryInfoPanel);
            				}
            				allSecondIds+=tabInfo.children[i].children[j].deviceTypeId;
                		}else{
                			allSecondIds+=(','+tabInfo.children[i].children[j].deviceTypeId);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        			if(panelItem.items.length>1){//添加全部标签
        				var secondTabPanel_all={
//        						title: '<div style="color:#000000;font-size:11px;font-family:SimSun">全部</div>',
        						title: '全部',
        						tpl:'全部',
        						layout: 'fit',
        						id: 'AlarmQueryRootTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.push(secondTabPanel_all);
        			}
        		}else{
        			panelItem={
        				title: tabInfo.children[i].text,
        				tpl: tabInfo.children[i].text,
        				layout: 'fit',
    					id: 'AlarmQueryRootTabPanel_'+tabInfo.children[i].deviceTypeId,
    					border: false
        			};
        			if(i==0){
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
    					
    					var AlarmQueryInfoPanel = Ext.create('AP.view.alarmQuery.AlarmQueryInfoPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(AlarmQueryInfoPanel);
        				}else{
	        				newCard.add(AlarmQueryInfoPanel);
        				}
    					
        				alarmQueryDataRefresh();
    					
//    					if(newCard.id=="RPCAlarmQueryPanel_Id"){
//    						Ext.getCmp("selectedDeviceType_global").setValue(0); 
//    						var secondTabPanel = Ext.getCmp("RPCAlarmQuerySencodTabPanel");
//    						var secondActiveId = secondTabPanel.getActiveTab().id;
//    						if(secondActiveId=="RPCFESDiagramResultAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("RPCFESDiagramResultAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.RPCFESDiagramResultAlarmOverviewStore');
//    							}
//    						}else if(secondActiveId=="RPCRunStatusAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("RPCRunStatusAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.RPCRunStatusAlarmOverviewStore');
//    							}
//    						}else if(secondActiveId=="RPCCommunicationAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("RPCCommunicationAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.RPCCommunicationAlarmOverviewStore');
//    							}
//    						}else if(secondActiveId=="RPCNumericValueAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("RPCNumericValueAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.RPCNumericValueAlarmOverviewStore');
//    							}
//    						}else if(secondActiveId=="RPCEnumValueAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("RPCEnumValueAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.RPCEnumValueAlarmOverviewStore');
//    							}
//    						}else if(secondActiveId=="RPCSwitchingValueAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("RPCSwitchingValueAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.RPCSwitchingValueAlarmOverviewStore');
//    							}
//    						}
//    					}else if(newCard.id=="PCPAlarmQueryPanel_Id"){
//    						Ext.getCmp("selectedDeviceType_global").setValue(1); 
//    						var secondTabPanel = Ext.getCmp("PCPAlarmQuerySencodTabPanel");
//    						var secondActiveId = secondTabPanel.getActiveTab().id;
//    						if(secondActiveId=="PCPRunStatusAlarmInfoPanel_Id"){
//        						var gridPanel = Ext.getCmp("PCPRunStatusAlarmOverviewGridPanel_Id");
//        						if (isNotVal(gridPanel)) {
//        							gridPanel.getStore().loadPage(1);
//        						}else{
//        							Ext.create('AP.store.alarmQuery.PCPRunStatusAlarmOverviewStore');
//        						}
//        					}else if(secondActiveId=="PCPCommunicationAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("PCPCommunicationAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.PCPCommunicationAlarmOverviewStore');
//    							}
//    						}else if(secondActiveId=="PCPNumericValueAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("PCPNumericValueAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.PCPNumericValueAlarmOverviewStore');
//    							}
//    						}else if(secondActiveId=="PCPEnumValueAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("PCPEnumValueAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.PCPEnumValueAlarmOverviewStore');
//    							}
//    						}else if(secondActiveId=="PCPSwitchingValueAlarmInfoPanel_Id"){
//    							var gridPanel = Ext.getCmp("PCPSwitchingValueAlarmOverviewGridPanel_Id");
//    							if (isNotVal(gridPanel)) {
//    								gridPanel.getStore().loadPage(1);
//    							}else{
//    								Ext.create('AP.store.alarmQuery.PCPSwitchingValueAlarmOverviewStore');
//    							}
//    						}
//    					}
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
    heads = "序号";
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

function exportAlarmDataExcel(orgId,deviceType,deviceId,deviceName,startDate,endDate,alarmType,alarmLevel,isSendMessage,fileName,title,columnStr) {
    var url = context + '/alarmQueryController/exportAlarmData';
    var fields = "";
    var heads = "";
    var lockedheads = "";
    var unlockedheads = "";
    var lockedfields = "";
    var unlockedfields = "";
    var columns_ = Ext.JSON.decode(columnStr);
    
    fields = "id";
    heads = "序号";
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
	var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
	
	Ext.getCmp("selectedDeviceType_global").setValue(deviceType); 
	
	var removeFESDiagramResultAlarmInfoPanel=false;
	var alarmTypeTabChange=false;
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	var alarmTypeTabActiveId = tabPanel.getActiveTab().id;
	
	var getTabId = tabPanel.getComponent("FESDiagramResultAlarmInfoTabPanel_Id");
	
	if(deviceCount>0 && getTabId==undefined){
		tabPanel.insert(0,AlarmQuerySecondTabPanelItems[0]);
 	}else if(deviceCount==0 && getTabId!=undefined){
 		if(alarmTypeTabActiveId=='FESDiagramResultAlarmInfoTabPanel_Id'){
 			alarmTypeTabChange=true;
 		}
 		Ext.getCmp("AlarmQuerySecondTabPanel").remove(Ext.getCmp("FESDiagramResultAlarmInfoTabPanel_Id"));
 		removeFESDiagramResultAlarmInfoPanel=true;
 	}
	
	if(!alarmTypeTabChange){
		if(alarmTypeTabActiveId=="FESDiagramResultAlarmInfoTabPanel_Id"){
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
		}else if(alarmTypeTabActiveId=="RunStatusAlarmInfoPanel_Id"){
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
		}else if(alarmTypeTabActiveId=="CommunicationAlarmInfoPanel_Id"){
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
		}else if(alarmTypeTabActiveId=="NumericValueAlarmInfoPanel_Id"){
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
		}else if(alarmTypeTabActiveId=="EnumValueAlarmInfoPanel_Id"){
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
		}else if(alarmTypeTabActiveId=="SwitchingValueAlarmInfoPanel_Id"){
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

function getAlarmTypeFromTabActive(){
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	var alarmTypeTabActiveId = tabPanel.getActiveTab().id;
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
	var alarmTypeTabActiveId = tabPanel.getActiveTab().id;
	var alarmTypeName='';
	if(alarmTypeTabActiveId=="FESDiagramResultAlarmInfoTabPanel_Id"){
		alarmTypeName='工况诊断报警';
	}else if(alarmTypeTabActiveId=="RunStatusAlarmInfoTabPanel_Id"){
		alarmTypeName='运行状态报警';
	}else if(alarmTypeTabActiveId=="CommunicationAlarmInfoTabPanel_Id"){
		alarmTypeName='通信状态报警';
	}else if(alarmTypeTabActiveId=="NumericValueAlarmInfoTabPanel_Id"){
		alarmTypeName='数据量报警';
	}else if(alarmTypeTabActiveId=="EnumValueAlarmInfoTabPanel_Id"){
		alarmTypeName='枚举量报警';
	}else if(alarmTypeTabActiveId=="SwitchingValueAlarmInfoTabPanel_Id"){
		alarmTypeName='开关量报警';
	}
	return alarmTypeName;
}

function getAlarmOverViewPanIdFromTabActive(){
	var tabPanel = Ext.getCmp("AlarmQuerySecondTabPanel");
	var alarmTypeTabActiveId = tabPanel.getActiveTab().id;
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
	var alarmTypeTabActiveId = tabPanel.getActiveTab().id;
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