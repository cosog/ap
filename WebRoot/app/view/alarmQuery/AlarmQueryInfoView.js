Ext.define("AP.view.alarmQuery.AlarmQueryInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.alarmQueryInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var RPCAlarmQueryInfoView = Ext.create('AP.view.alarmQuery.RPCAlarmQueryInfoView');
        var PCPAlarmQueryInfoView = Ext.create('AP.view.alarmQuery.PCPAlarmQueryInfoView');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"AlarmQueryTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        				title: '抽油机井',
        				id:'RPCAlarmQueryPanel_Id',
        				items: [RPCAlarmQueryInfoView],
        				layout: "fit",
        				border: false
        			},{
        				title: '螺杆泵井',
        				id:'PCPAlarmQueryPanel_Id',
        				items: [PCPAlarmQueryInfoView],
        				layout: "fit",
        				hidden: pcpHidden,
        				border: false
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        					if(newCard.id=="RPCAlarmQueryPanel_Id"){
        						Ext.getCmp("selectedDeviceType_global").setValue(0); 
        						var secondTabPanel = Ext.getCmp("RPCAlarmQueryTabPanel");
        						var secondActiveId = secondTabPanel.getActiveTab().id;
        						if(secondActiveId=="RPCFESDiagramResultAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("RPCFESDiagramResultAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.RPCFESDiagramResultAlarmOverviewStore');
        							}
        						}else if(secondActiveId=="RPCRunStatusAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("RPCRunStatusAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.RPCRunStatusAlarmOverviewStore');
        							}
        						}else if(secondActiveId=="RPCCommunicationAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("RPCCommunicationAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.RPCCommunicationAlarmOverviewStore');
        							}
        						}else if(secondActiveId=="RPCNumericValueAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("RPCNumericValueAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.RPCNumericValueAlarmOverviewStore');
        							}
        						}else if(secondActiveId=="RPCEnumValueAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("RPCEnumValueAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.RPCEnumValueAlarmOverviewStore');
        							}
        						}else if(secondActiveId=="RPCSwitchingValueAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("RPCSwitchingValueAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.RPCSwitchingValueAlarmOverviewStore');
        							}
        						}
        					}else if(newCard.id=="PCPAlarmQueryPanel_Id"){
        						Ext.getCmp("selectedDeviceType_global").setValue(1); 
        						var secondTabPanel = Ext.getCmp("PCPAlarmQueryTabPanel");
        						var secondActiveId = secondTabPanel.getActiveTab().id;
        						if(secondActiveId=="PCPRunStatusAlarmInfoPanel_Id"){
            						var gridPanel = Ext.getCmp("PCPRunStatusAlarmOverviewGridPanel_Id");
            						if (isNotVal(gridPanel)) {
            							gridPanel.getStore().loadPage(1);
            						}else{
            							Ext.create('AP.store.alarmQuery.PCPRunStatusAlarmOverviewStore');
            						}
            					}else if(secondActiveId=="PCPCommunicationAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("PCPCommunicationAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.PCPCommunicationAlarmOverviewStore');
        							}
        						}else if(secondActiveId=="PCPNumericValueAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("PCPNumericValueAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.PCPNumericValueAlarmOverviewStore');
        							}
        						}else if(secondActiveId=="PCPEnumValueAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("PCPEnumValueAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.PCPEnumValueAlarmOverviewStore');
        							}
        						}else if(secondActiveId=="PCPSwitchingValueAlarmInfoPanel_Id"){
        							var gridPanel = Ext.getCmp("PCPSwitchingValueAlarmOverviewGridPanel_Id");
        							if (isNotVal(gridPanel)) {
        								gridPanel.getStore().loadPage(1);
        							}else{
        								Ext.create('AP.store.alarmQuery.PCPSwitchingValueAlarmOverviewStore');
        							}
        						}
        					}
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