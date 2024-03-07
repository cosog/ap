Ext.define('AP.view.log.DeviceOperationLogInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.deviceOperationLogInfoView',
    layout: "fit",
    id: "DeviceOperationLogInfoView",
    border: false,
    initComponent: function () {
    	var DeviceOperationLogInfoPanel = Ext.create('AP.view.log.DeviceOperationLogInfoPanel');
        
        var items=[];
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					xtype: 'tabpanel',
        	        		id: 'DeviceOperationLogRootTabPanel_'+tabInfo.children[i].tabId,
        	        		activeTab: 0,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				oldCard.removeAll();
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        				var DeviceOperationLogInfoPanel = Ext.create('AP.view.log.DeviceOperationLogInfoPanel');
        	        				newCard.add(DeviceOperationLogInfoPanel);
        	        				
        	        				var gridPanel = Ext.getCmp("DeviceOperationLogGridPanel_Id");
        	        				if (isNotVal(gridPanel)) {
        	        					gridPanel.getStore().load();
        	        				}else{
        	        					Ext.create('AP.store.log.DeviceOperationLogStore');
        	        				}
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
        						id: 'DeviceOperationLogRootTabPanel_'+tabInfo.children[i].children[j].tabId,
        						border: false
        				};
            			if(j==0){
            				secondTabPanel.items=[];
            				secondTabPanel.items.push(DeviceOperationLogInfoPanel);
                		}
            			panelItem.items.push(secondTabPanel);
        			}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'DeviceOperationLogRootTabPanel_'+tabInfo.children[i].tabId,
    						border: false
        			};
        			if(i==0){
            			panelItem.items=[];
            			panelItem.items.push(DeviceOperationLogInfoPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
    	Ext.apply(this, {
    		items: [{
        		xtype: 'tabpanel',
        		id:"DeviceOperationLogRootTabPanel",
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
    					
    					var DeviceOperationLogInfoPanel = Ext.create('AP.view.log.DeviceOperationLogInfoPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(DeviceOperationLogInfoPanel);
        				}else{
	        				newCard.add(DeviceOperationLogInfoPanel);
        				}
        				
        				var gridPanel = Ext.getCmp("DeviceOperationLogGridPanel_Id");
        				if (isNotVal(gridPanel)) {
        					gridPanel.getStore().load();
        				}else{
        					Ext.create('AP.store.log.DeviceOperationLogStore');
        				}
    				}
    			}
            }]
        });
        this.callParent(arguments);
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
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
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

function exportDeviceOperationLogExcel(orgId,deviceType,deviceName,operationType,startDate,endDate,fileName,title,columnStr) {
	var timestamp=new Date().getTime();
	var key='exportDeviceOperationLogExcelData'+timestamp;
	var maskPanelId='DeviceOperationLogPanel_Id';
	
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
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,cosog.string.loading);
    openExcelWindow(url + '?flag=true' + param);
};