Ext.define("AP.view.log.LogInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.logInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var DeviceOperationLogInfoView = Ext.create('AP.view.log.DeviceOperationLogInfoView');
        var SystemLogInfoView = Ext.create('AP.view.log.SystemLogInfoView');
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"LogQueryTabPanel",
        		activeTab: 0,
        		border: false,
        		tabPosition: 'bottom',
        		items: [{
        				title: '设备日志',
        				id:'DeviceOperationLogInfoPanel_Id',
        				items: [DeviceOperationLogInfoView],
        				layout: "fit",
        				border: false
        			},{
        				title: '系统日志',
        				id:'SystemLogInfoPanel_Id',
        				items: [SystemLogInfoView],
        				layout: "fit",
        				border: false
        			}],
        			listeners: {
        				tabchange: function (tabPanel, newCard,oldCard, obj) {
        					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
        					if(newCard.id=="DeviceOperationLogInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("DeviceOperationLogGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.log.DeviceOperationLogStore');
        						}
        					}else if(newCard.id=="SystemLogInfoPanel_Id"){
        						var gridPanel = Ext.getCmp("SystemLogGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.log.SystemLogStore');
        						}
        					}
        				}
        			}
            	}]
        });
        me.callParent(arguments);
    }

});

function createDeviceOperationLogColumn(columnInfo) {
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
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase() == 'createTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } 
        else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
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

function createSystemLogColumn(columnInfo) {
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
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase() == 'createTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } 
        else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
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

function exportDeviceOperationLogExcel(orgId,deviceType,deviceName,operationType,startDate,endDate,fileName,title,columnStr) {
    var url = context + '/logQueryController/exportDeviceOperationLogExcelData';
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
    + "&operationType=" + operationType
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};

function exportSystemLogExcel(orgId,startDate,endDate,fileName,title,columnStr) {
    var url = context + '/logQueryController/exportSystemLogExcelData';
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
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};