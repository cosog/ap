var videoPlayrHelper={};
var accessTokenInfo=null;
var demoAccessTokenInfo=null;
Ext.define("AP.view.realTimeMonitoring.RealTimeMonitoringInfoView", {
    extend: 'Ext.panel.Panel',
    id: 'RealTimeMonitoringInfoView_Id',
    alias: 'widget.realTimeMonitoringInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var RealTimeMonitoringInfoPanel = Ext.create('AP.view.realTimeMonitoring.RealTimeMonitoringInfoPanel');
        
        var items=[];
        var deviceTypeActiveId=getDeviceTypeActiveId();
        var firstActiveTab=deviceTypeActiveId.firstActiveTab;
        var secondActiveTab=deviceTypeActiveId.secondActiveTab;
        
//        var defaultActiveDeviceType=getDefaultActiveDeviceTypeTab();
        if(tabInfo.children!=undefined && tabInfo.children!=null && tabInfo.children.length>0){
        	for(var i=0;i<tabInfo.children.length;i++){
        		var panelItem={};
        		if(tabInfo.children[i].children!=undefined && tabInfo.children[i].children!=null && tabInfo.children[i].children.length>0){
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					xtype: 'tabpanel',
        	        		id: 'RealTimeMonitoringTabPanel_'+tabInfo.children[i].deviceTypeId,
        	        		activeTab: i==firstActiveTab?secondActiveTab:0,
        	        		border: false,
        	        		tabPosition: 'left',
        	        		iconCls: i==firstActiveTab?'check1':null,
        	        		items:[],
        	        		listeners: {
        	        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        	        				Ext.getCmp("RealTimeMonitoringTabPanel").el.mask(loginUserLanguageResource.loading).show();
        	        				cleanDeviceAddInfoAndControlInfo();
        	        				
        	        				if(oldCard!=undefined){
        	        					oldCard.removeAll();
            	        				oldCard.setIconCls(null);
                    				}
                    				if(newCard!=undefined){
                    					newCard.setIconCls('check2');
                    				}
        	        			},
        	        			tabchange: function (tabPanel, newCard,oldCard, obj) {
        	        				var RealTimeMonitoringInfoPanel = Ext.create('AP.view.realTimeMonitoring.RealTimeMonitoringInfoPanel');
        	        				newCard.add(RealTimeMonitoringInfoPanel);
        	        				realTimeDataRefresh();
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
//        						iconCls: (panelItem.items.length==1&&j==0)?'check2':null,
        						id: 'RealTimeMonitoringTabPanel_'+tabInfo.children[i].children[j].deviceTypeId,
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
        						id: 'RealTimeMonitoringTabPanel_'+allSecondIds,
        						border: false
        				};
        				panelItem.items.splice(0, 0, secondTabPanel_all);
        			}
        			
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].iconCls='check2';
        			}
        			
        			if(i==firstActiveTab && panelItem.items.length>secondActiveTab){
        				panelItem.items[secondActiveTab].items=[];
        				panelItem.items[secondActiveTab].items.push(RealTimeMonitoringInfoPanel);
    				}
        		}else{
        			panelItem={
        					title: tabInfo.children[i].text,
        					tpl: tabInfo.children[i].text,
        					layout: 'fit',
    						id: 'RealTimeMonitoringTabPanel_'+tabInfo.children[i].deviceTypeId,
    						iconCls: i==firstActiveTab?'check1':null,
    						border: false
        			};
        			if(i==firstActiveTab){
            			panelItem.items=[];
            			panelItem.items.push(RealTimeMonitoringInfoPanel);
            		}
        		}
        		items.push(panelItem);
        	}
        }
        
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"RealTimeMonitoringTabPanel",
        		activeTab: firstActiveTab,
        		border: false,
        		tabPosition: 'bottom',
        		tabBar:{
                	items: [{
                        xtype: 'tbfill'
                    },{
                		xtype: 'button',
                        id:"CPUUsedPercentLabel_id",
//                        width: 180,
                        height:25,
                        text: loginUserLanguageResource.resourcesMonitoring_cpu+':',
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue(loginUserLanguageResource.cpuUsage+"(%)");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("cpuUsedPercent");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"memUsedPercentLabel_id",
                        text: loginUserLanguageResource.resourcesMonitoring_mem+':',
//                        width: 130,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue(loginUserLanguageResource.memUsage+"(%)");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("memUsedPercent");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"tableSpaceSizeProbeLabel_id",
                        text: loginUserLanguageResource.resourcesMonitoring_tablespaces+':',
//                        width: 130,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue(loginUserLanguageResource.tablespacesUsage+"(%)");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("tableSpaceSize");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"redisRunStatusProbeLabel_id",
                        text: loginUserLanguageResource.resourcesMonitoring_cache+'',
//                        width: 100,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue(loginUserLanguageResource.cacheDbMemory+"(m)");
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("jedisStatus");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"adRunStatusProbeLabel_id",
                        text: loginUserLanguageResource.resourcesMonitoring_ad,
                        hidden: !IoTConfig,
//                        width: 100,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue(loginUserLanguageResource.adStatus);
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("adRunStatus");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"acRunStatusProbeLabel_id",
                        text: loginUserLanguageResource.resourcesMonitoring_ac,
//                        width: 100,
                        handler: function (v, o) {
                        	Ext.getCmp('ResourceMonitoringCurveItem_Id').setValue(loginUserLanguageResource.acStatus);
                            Ext.getCmp('ResourceMonitoringCurveItemCode_Id').setValue("acRunStatus");
                            var itemCode= Ext.getCmp('ResourceMonitoringCurveItemCode_Id').getValue();
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_'+itemCode+'_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                        id:"adLicenseStatusProbeLabel_id",
                        text: 'License:',
                        hidden: true,
                        handler: function (v, o) {}
                	},{
                        id: 'ResourceMonitoringCurveItem_Id',
                        xtype: 'textfield',
                        value: '',
                        hidden: true
                    },{
                        id: 'ResourceMonitoringCurveItemCode_Id',
                        xtype: 'textfield',
                        value: '',
                        hidden: true
                    }]
                },
        		items: items,
        		listeners: {
    				beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
    					Ext.getCmp("RealTimeMonitoringTabPanel").el.mask(loginUserLanguageResource.loading).show();
    					
    					if(oldCard!=undefined){
    						if(oldCard.xtype=='tabpanel'){
            					oldCard.activeTab.removeAll();
            				}else{
            					oldCard.removeAll();
            				}
            				oldCard.setIconCls(null);
        				}
        				if(newCard!=undefined){
        					newCard.setIconCls('check1');
        				}
        			},
        			tabchange: function (tabPanel, newCard,oldCard, obj) {
    					Ext.getCmp("bottomTab_Id").setValue(newCard.id); 
    					
    					var RealTimeMonitoringInfoPanel = Ext.create('AP.view.realTimeMonitoring.RealTimeMonitoringInfoPanel');
        				if(newCard.xtype=='tabpanel'){
        					newCard.activeTab.add(RealTimeMonitoringInfoPanel);
        				}else{
	        				newCard.add(RealTimeMonitoringInfoPanel);
        				}
        				
        				realTimeDataRefresh();
    				}
    			}
            }]
        });
        me.callParent(arguments);
    }

});

function realTimeDataRefresh(){
	
	if(videoPlayrHelper!=null && videoPlayrHelper!=null && videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus.state.play){
		videoPlayrHelper.player1.stop();
	}
	if(videoPlayrHelper!=null && videoPlayrHelper!=null && videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus.state.play){
		videoPlayrHelper.player2.stop();
	}
	
	
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
	var firstDeviceType=getDeviceTypeFromTabId_first("RealTimeMonitoringTabPanel");
//	var deviceCount=getCalculateTypeDeviceCount(orgId,deviceType,1);
	
	
	
	
	Ext.getCmp("selectedDeviceType_global").setValue(deviceType); 
	Ext.getCmp("selectedFirstDeviceType_global").setValue(firstDeviceType); 
	
	var tabPanel=Ext.getCmp("RealTimeMonitoringStatTabPanel");
	var projectTabConfig=getProjectTabInstanceInfoByDeviceType(deviceType);
	var statTabActiveId = tabPanel.getActiveTab().id;
	
	var tabChange=false;
	if(projectTabConfig.DeviceRealTimeMonitoring.FESDiagramStatPie==false){
		tabPanel.remove(Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id"));
		if(statTabActiveId=="RealTimeMonitoringFESdiagramResultStatGraphPanel_Id"){
			tabChange=true;
		}
	}
	if(projectTabConfig.DeviceRealTimeMonitoring.CommStatusStatPie==false){
		tabPanel.remove(Ext.getCmp("RealTimeMonitoringStatGraphPanel_Id"));
		if(statTabActiveId=="RealTimeMonitoringStatGraphPanel_Id"){
			tabChange=true;
		}
	}
	if(projectTabConfig.DeviceRealTimeMonitoring.RunStatusStatPie==false){
		tabPanel.remove(Ext.getCmp("RealTimeMonitoringRunStatusStatGraphPanel_Id"));
		if(statTabActiveId=="RealTimeMonitoringRunStatusStatGraphPanel_Id"){
			tabChange=true;
		}
	}
	
	if(projectTabConfig.DeviceRealTimeMonitoring.FESDiagramStatPie==false && projectTabConfig.DeviceRealTimeMonitoring.CommStatusStatPie==false && projectTabConfig.DeviceRealTimeMonitoring.RunStatusStatPie==false){
		tabPanel.hide();
	}else{
		if(tabPanel.isHidden() ){
			tabPanel.show();
		}
		if(!tabChange){
	 		statTabActiveId = tabPanel.getActiveTab().id;
	 		if(statTabActiveId=="RealTimeMonitoringFESdiagramResultStatGraphPanel_Id"){
	 			loadAndInitFESdiagramResultStat(true);
	 		}else if(statTabActiveId=="RealTimeMonitoringStatGraphPanel_Id"){
	 			loadAndInitCommStatusStat(true);
	 		}else if(statTabActiveId=="RealTimeMonitoringRunStatusStatGraphPanel_Id"){
	 			loadAndInitRunStatusStat(true);
	 		}else if(statTabActiveId=="RealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
	 			loadAndInitDeviceTypeStat(true);
	 		}
	 	}
	}
 	
	 	
	Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').setValue('');
	Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').setRawValue('');
	refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp("selectedDeviceId_global").getValue()),deviceType,Ext.getCmp("RealTimeMonitoringListGridPanel_Id"),'AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore');
}

function createRealTimeMonitoringStatColumn(columnInfo) {
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
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }
        else if (attr.dataIndex.toUpperCase()=='count'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceStatTableCommStatusColor(value,o,p,e);}";
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

function createRealTimeMonitoringColumnObject(columnInfo) {
    var myArr = columnInfo;
    var myColumns = [];
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var thisColumn={};
        var width_ = "";
        var flex_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
        	thisColumn.hidden=true;
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
//        	thisColumn.locked=attr.lock;
        }
        if (isNotVal(attr.width)) {
            thisColumn.width=attr.width;
        }
        
        if(myArr.length<=5){
        	if(myArr.length==1){
        		thisColumn.flex=1;
        	}else{
        		if(attr.dataIndex.toUpperCase() != 'id'.toUpperCase()){
            		thisColumn.flex=1;
            	} 
        	}
        }
        
//        else{
//        	if (isNotVal(attr.flex)) {
//                thisColumn.flex=attr.flex;
//            }
//        }
        
        
        thisColumn.text=attr.header;
        thisColumn.lockable=true;
        thisColumn.align='center';
        
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
        	thisColumn.xtype='rownumberer';
        	thisColumn.sortable=false;
        	thisColumn.locked=false;
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
        	thisColumn.sortable=false;
        	thisColumn.dataIndex=attr.dataIndex;
        	thisColumn.renderer=function(value,o,p,e){
        		return adviceCommStatusColor(value,o,p,e);
        	};
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
        	thisColumn.sortable=false;
        	thisColumn.dataIndex=attr.dataIndex;
        	thisColumn.renderer=function(value,o,p,e){
        		return adviceRunStatusColor(value,o,p,e);
        	};
        }
        else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
        	thisColumn.sortable=false;
        	thisColumn.locked=false;
        	thisColumn.dataIndex=attr.dataIndex;
        	thisColumn.renderer=function(value,o,p,e){
        		return adviceTimeFormat(value,o,p,e);
        	};
        } 
        else if (attr.dataIndex.toUpperCase()=='resultName'.toUpperCase()) {
        	thisColumn.sortable=false;
        	thisColumn.dataIndex=attr.dataIndex;
        	thisColumn.renderer=function(value,o,p,e){
        		return adviceResultStatusColor(value,o,p,e);
        	};
        }
        else {
        	thisColumn.sortable=false;
        	thisColumn.dataIndex=attr.dataIndex;
        	thisColumn.renderer=function(value,o,p,e){
        		return adviceRealtimeMonitoringDataColor(value,o,p,e);
        	};
        }
        
        myColumns.push(thisColumn);
    }
    return myColumns;
};

function createRealTimeMonitoringColumn(columnInfo) {
    var myArr = columnInfo;
    var myColumns = '[';
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ',"hidden":true';
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ',"width":' + attr.width;
        }
        myColumns += '{"text":"' + attr.header + '","lockable":true,"align":"center"'+width_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ',"xtype":"rownumberer","sortable":false,"locked":false';
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ',"sortable":false,"dataIndex":"' + attr.dataIndex + '","renderer":function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}';
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ',"sortable":false,"dataIndex":"' + attr.dataIndex + '","renderer":function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}';
        }
        else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
            myColumns += ',"sortable":false,"locked":false,"dataIndex":"' + attr.dataIndex + '","renderer":function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}';
        } 
        else if (attr.dataIndex.toUpperCase()=='resultName'.toUpperCase()) {
            myColumns += ',"sortable":false,"dataIndex":"' + attr.dataIndex + '","renderer":function(value,o,p,e){return adviceResultStatusColor(value,o,p,e);}';
        }
        else {
            myColumns += ',"sortable":false,"dataIndex":"' + attr.dataIndex + '","renderer":function(value,o,p,e){return adviceRealtimeMonitoringDataColor(value,o,p,e);}';
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

ResourceProbeHistoryCurveChartFn = function (get_rawData, itemName, itemCode, divId) {
    var tickInterval = 1;
    var data = get_rawData.totalRoot;
    tickInterval = Math.floor(data.length / 10) + 1;
    var title = itemName.split("(")[0];
    var legend=false;
    var legendName = [itemName];
    
    var timeFormat='%m-%d';
    if(data.length>0 && get_rawData.minAcqTime.split(' ')[0]==get_rawData.maxAcqTime.split(' ')[0]){
	    timeFormat='%H:%M';
    }
    
    if(itemCode.toUpperCase()=='cpuUsedPercent'.toUpperCase()){
    	legendName = [];
    	for (var i = 0; i < data.length; i++) {
    		if(isNotVal(data[i].value)){
    			var cpus=data[i].value.split(";");
        		if(cpus.length>legendName.length){
        			legendName = [];
        			for(var j = 0; j < cpus.length; j++){
        				legendName.push("cpu"+(j+1)+"(%)");
        			}
        		}
    		}
    		
    	}
    	legend=true;
    	ytitle='CPU使用率(%)';
    }else if(itemCode.toUpperCase()=='jedisStatus'.toUpperCase()){
    	legendName = [];
    	legendName.push("maxmemory(m)");
    	legendName.push("usedmemory(m)");
    	legend=true;
    }
    
    
    var series = "[";
    for (var i = 0; i < legendName.length; i++) {
        series += "{\"name\":\"" + legendName[i] + "\",";
        series += "\"data\":[";
        for (var j = 0; j < data.length; j++) {
        	
    		var year = parseInt(data[j].acqTime.split(" ")[0].split("-")[0]);
            var month = parseInt(data[j].acqTime.split(" ")[0].split("-")[1]);
            var day = parseInt(data[j].acqTime.split(" ")[0].split("-")[2]);
            var hour = parseInt(data[j].acqTime.split(" ")[1].split(":")[0]);
            var minute = parseInt(data[j].acqTime.split(" ")[1].split(":")[1]);
            var second = parseInt(data[j].acqTime.split(" ")[1].split(":")[2]);
            if(itemCode.toUpperCase()=='cpuUsedPercent'.toUpperCase()){
            	if(isNotVal(data[j].value)){
            		var values=data[j].value.split(";");
            		if(values.length>i){
            			if(isNumber(values[i])){
            				series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + values[i] + "]";
            			}else{
            				series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
            			}
            		}else{
            			series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
            		}
            	}else{
            		series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
            	}
            }else if(itemCode.toUpperCase()=='jedisStatus'.toUpperCase()){
            	if(isNotVal(data[j].value)){
            		var values=data[j].value.split(";");
            		if(values.length>i){
            			if(isNumber(values[i])){
            				series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + values[i] + "]";
            			}else{
            				series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
            			}
            		}else{
            			series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
            		}
            	}else{
            		series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
            	}
            }else{
            	if(isNotVal(data[j].value)){
            		series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + data[j].value + "]";
        		}else{
        			series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + null + "]";
        		}
            }
            if (j != data.length - 1) {
                series += ",";
            }
        }
        series += "]}";
        if (i != legendName.length - 1) {
            series += ",";
        }
    }
    series += "]";
    var ser = Ext.JSON.decode(series);
    var color = ['#800000', // 红
       '#008C00', // 绿
       '#000000', // 黑
       '#0000FF', // 蓝
       '#F4BD82', // 黄
       '#FF00FF' // 紫
     ];

    initResourceProbeHistoryCurveChartFn(ser, tickInterval, divId, title, "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "时间", itemName, color,legend,timeFormat);

    return false;
};

function initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, ytitle, color,legend,timeFormat) {
    
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    mychart = new Highcharts.Chart({
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
            },labels: {
                formatter: function () {
                    return Highcharts.dateFormat(timeFormat, this.value);
                },
                autoRotation:true,//自动旋转
                rotation: -45 //倾斜度，防止数量过多显示不全  
//                step: 2
            }
        },
        yAxis: [{
            lineWidth: 1,
            title: {
                text: ytitle,
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            }
      }],
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
            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
    	    sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
        },
        plotOptions: {
            spline: {
                lineWidth: 1,
                fillOpacity: 0.3,
                marker: {
                    enabled: true,
                    radius: 3, //曲线点半径，默认是4
                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                    states: {
                        hover: {
                            enabled: true,
                            radius: 6
                        }
                    }
                },
                shadow: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            enabled: legend,
            borderWidth: 0
        },
        series: series
    });
};


function exportRealTimeMonitoringDataExcel(orgId,deviceType,deviceName,dictDeviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,fileName,title,columnStr) {
	var timestamp=new Date().getTime();
	var key='exportDeviceRealTimeOverviewData'+deviceType+'_'+timestamp;
	
	var maskPanelId='RealTimeMonitoringInfoDeviceListPanel_Id';
	
	var url = context + '/realTimeMonitoringController/exportDeviceRealTimeOverviewDataExcel';
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
    fields = "id," + lockedfields+","+unlockedfields;
    heads = loginUserLanguageResource.idx+"," + lockedheads+","+unlockedheads;
    fields="";
    heads="";
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



function gotoDeviceHistory(deviceId,deviceName,deviceType){
	Ext.getCmp("realtimeTurnToHisyorySign_Id").setValue('true');//跳转标志
	Ext.getCmp("realtimeTurnToHisyory_DeviceName").setValue(deviceName);
	var tabPanel = Ext.getCmp("frame_center_ids");
	var moduleId="DeviceHistoryQuery";
	var getTabId = tabPanel.getComponent(moduleId);
	var haveModule=false;
	if(!getTabId){
		var moduleStore=Ext.getCmp("MainIframeView_Id").getStore();
		var moduleCount=moduleStore.getCount();
		for(var i=0;i<moduleCount;i++){
			var rec=moduleStore.getAt(i);
			if(rec.isLeaf()&&rec.id=="DeviceHistoryQuery"){
				tabPanel.add(Ext.create(rec.data.viewsrc, {
                    id: rec.data.id,
                    closable: true,
                    iconCls: rec.data.md_icon,
                    closeAction: 'destroy',
                    title: rec.data.text,
                    listeners: {
                        afterrender: function () {
                        },
                        delay: 150
                    }
                })).show();
                Ext.getCmp("topModule_Id").setValue(rec.data.mdCode);
				haveModule=true;
				break;
			}
		}
	}else{
		haveModule=true;
	}
	if(haveModule){
		tabPanel.setActiveTab(moduleId);
		Ext.getCmp('HistoryQueryDeviceListComb_Id').setValue(deviceName);
	}
}



function loadAndInitFESdiagramResultStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");

	var deviceTypeStatValue='';
	if(all){
		Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").setValue('');
		commStatusStatValue='';
		deviceTypeStatValue='';
	}else{
		deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
		commStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").getValue();
	}
	Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData',
		success:function(response) {
			Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringFESDiagramResultStatPieOrColChat(result);
		},
		failure:function(){
			Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initRealTimeMonitoringFESDiagramResultStatPieOrColChat(get_rawData) {
	var divId="RealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id";
	
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
	
	ShowRealTimeMonitoringFESDiagramResultStatPieOrColChat(title,divId, "设备数占", pieData,colors);
};

function ShowRealTimeMonitoringFESDiagramResultStatPieOrColChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
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
//			colors : colors,
			tooltip : {
				pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
			},
			legend : {
				align : 'center',//center left
				verticalAlign : 'bottom',//bottom middle
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
							Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
							var statSelectFESdiagramResultId="RealTimeMonitoringStatSelectFESdiagramResult_Id";
							var deviceListComb_Id="RealTimeMonitoringDeviceListComb_Id";
							var gridPanel_Id="RealTimeMonitoringListGridPanel_Id";
							var store="AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore";
							if(!e.point.selected){//如果没被选中,则本次是选中
								Ext.getCmp(statSelectFESdiagramResultId).setValue(e.point.name);
							}else{//取消选中
								Ext.getCmp(statSelectFESdiagramResultId).setValue('');
							}
							Ext.getCmp(deviceListComb_Id).setValue('');
							Ext.getCmp(deviceListComb_Id).setRawValue('');
							refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp("selectedDeviceId_global").getValue()),0,Ext.getCmp(gridPanel_Id),store);
						}
					},
					showInLegend : true
				}
			},
			exporting:{ 
	            enabled:true,    
	            filename:title,   
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	    	    sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
			},
			series : [{
						type : 'pie',
						name : name,
						data : data
					}]
			});
	}
};


function loadAndInitCommStatusStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
	var panelId='';

	panelId="RealTimeMonitoringStatGraphPanel_Id";
	if(all){
		Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").setValue('');
	}

	Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData',
		success:function(response) {
			if(isNotVal(Ext.getCmp(panelId))){
				Ext.getCmp(panelId).getEl().unmask();
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringStatPieOrColChat(result);
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


function initRealTimeMonitoringStatPieOrColChat(get_rawData) {
	var divId="RealTimeMonitoringStatGraphPanelPieDiv_Id";
	var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
	var activeId = tabPanel.getActiveTab().id;
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
	ShowRealTimeMonitoringStatPieOrColChat(title,divId, "设备数占", pieData,colors);
};

function ShowRealTimeMonitoringStatPieOrColChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
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
							var statSelectCommStatusId="RealTimeMonitoringStatSelectCommStatus_Id";
							var deviceListComb_Id="RealTimeMonitoringDeviceListComb_Id";
							var gridPanel_Id="RealTimeMonitoringListGridPanel_Id";
							var selectedDeviceId_global="selectedDeviceId_global";
							var store="AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore";
							var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
							var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;

							Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
	                    	
							statSelectCommStatusId="RealTimeMonitoringStatSelectCommStatus_Id";
							deviceListComb_Id="RealTimeMonitoringDeviceListComb_Id";
							gridPanel_Id="RealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore";
							selectedDeviceId_global="selectedDeviceId_global";
						
							
							if(!e.point.selected){//如果没被选中,则本次是选中
								Ext.getCmp(statSelectCommStatusId).setValue(e.point.name);
							}else{//取消选中
								Ext.getCmp(statSelectCommStatusId).setValue('');
							}
							
							Ext.getCmp(deviceListComb_Id).setValue('');
							Ext.getCmp(deviceListComb_Id).setRawValue('');
							refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp(selectedDeviceId_global).getValue()),deviceType,Ext.getCmp(gridPanel_Id),store);
						}
					},
					showInLegend : true
				}
			},
			exporting:{ 
	            enabled:true,    
	            filename:title,  
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
			},
			series : [{
						type : 'pie',
						name : name,
						data : data
					}]
			});
	}else{
		
	}
};

function loadAndInitRunStatusStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
	var deviceTypeStatValue='';
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
	var panelId='';
	panelId="RealTimeMonitoringRunStatusStatGraphPanel_Id";
	if(all){
		Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").setValue('');
	}else{
		deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
	}

	if(isNotVal(Ext.getCmp(panelId))){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData',
		success:function(response) {
			if(isNotVal(Ext.getCmp(panelId))){
				Ext.getCmp(panelId).getEl().unmask();
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringRunStatusStatPieOrColChat(result);
		},
		failure:function(){
			if(isNotVal(Ext.getCmp(panelId))){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}


function initRealTimeMonitoringRunStatusStatPieOrColChat(get_rawData) {
	var divId="RealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id";
	var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
	var activeId = tabPanel.getActiveTab().id;
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
	ShowRealTimeMonitoringRunStatusStatPieOrColChat(title,divId, "设备数占", pieData,colors);
};

function ShowRealTimeMonitoringRunStatusStatPieOrColChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
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
							var statSelectRunStatusId="RealTimeMonitoringStatSelectRunStatus_Id";
							var deviceListComb_Id="RealTimeMonitoringDeviceListComb_Id";
							var gridPanel_Id="RealTimeMonitoringListGridPanel_Id";
							var store="AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore";
							var selectedDeviceId_global="selectedDeviceId_global";
							var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
							var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;

							Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
	                    	
							statSelectRunStatusId="RealTimeMonitoringStatSelectRunStatus_Id";
							deviceListComb_Id="RealTimeMonitoringDeviceListComb_Id";
							gridPanel_Id="RealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore";
							selectedDeviceId_global="selectedDeviceId_global";
						
							
							if(!e.point.selected){//如果没被选中,则本次是选中
								Ext.getCmp(statSelectRunStatusId).setValue(e.point.name);
							}else{//取消选中
								Ext.getCmp(statSelectRunStatusId).setValue('');
							}
							
							Ext.getCmp(deviceListComb_Id).setValue('');
							Ext.getCmp(deviceListComb_Id).setRawValue('');

							refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp(selectedDeviceId_global).getValue()),deviceType,Ext.getCmp(gridPanel_Id),store);
						}
					},
					showInLegend : true
				}
			},
			exporting:{ 
	            enabled:true,    
	            filename:title,
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
			},
			series : [{
						type : 'pie',
						name : name,
						data : data
					}]
			});
	}
};

function loadAndInitDeviceTypeStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
	
	if(all){
		Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").setValue('');
	}
	

	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringDeviceTypeStatData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringDeviceTypeStatPieOrColChat(result);
		},
		failure:function(){
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
};

function initRealTimeMonitoringDeviceTypeStatPieOrColChat(get_rawData) {
	var divId="RealTimeMonitoringDeviceTypeStatPieDiv_Id";
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
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
	ShowRealTimeMonitoringDeviceTypeStatPieChat(title,divId, "设备数占", pieData,colors);
};

function ShowRealTimeMonitoringDeviceTypeStatPieChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
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
							var statSelectDeviceType_Id="RealTimeMonitoringStatSelectDeviceType_Id";
							var deviceListComb_Id="RealTimeMonitoringDeviceListComb_Id";
							var gridPanel_Id="RealTimeMonitoringListGridPanel_Id";
							var store="AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore";
							var selectedDeviceId_global="selectedDeviceId_global";
							var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
							var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;

							Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
	                    	
							statSelectDeviceType_Id="RealTimeMonitoringStatSelectDeviceType_Id";
							deviceListComb_Id="RealTimeMonitoringDeviceListComb_Id";
							gridPanel_Id="RealTimeMonitoringListGridPanel_Id";
							store="AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore";
							selectedDeviceId_global="selectedDeviceId_global";
						
							
							if(!e.point.selected){//如果没被选中,则本次是选中
								Ext.getCmp(statSelectDeviceType_Id).setValue(e.point.name);
							}else{//取消选中
								Ext.getCmp(statSelectDeviceType_Id).setValue('');
							}
							
							Ext.getCmp(deviceListComb_Id).setValue('');
							Ext.getCmp(deviceListComb_Id).setRawValue('');

							refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp(selectedDeviceId_global).getValue()),deviceType,Ext.getCmp(gridPanel_Id),store);
						}
					},
					showInLegend : true
				}
			},
			exporting:{ 
	            enabled:true,    
	            filename:title,    
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	    	    sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
			},
			series : [{
						type : 'pie',
						name : name,
						data : data
					}]
			});
	}
	
};

function deviceRealtimeMonitoringCurve(deviceType){
	var selectRowId="RealTimeMonitoringInfoDeviceListSelectRow_Id";
	var gridPanelId="RealTimeMonitoringListGridPanel_Id";
	var contentId="realTimeMonitoringCurveContent";
	var containerId="realTimeMonitoringCurveContainer";
	var divPrefix="realTimeMonitoringCurveDiv";
	var eastPanelId="RealTimeMonitoringCurveAndTableTabPanel";
	var panelId="RealTimeMonitoringCurveTabPanel_Id";
	
	Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loading).show();
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var calculateType=0;
	var selectRow= Ext.getCmp(selectRowId).getValue();
	if(Ext.getCmp(gridPanelId).getSelectionModel().getSelection().length>0){
		calculateType=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.calculateType;
		deviceId=Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.id;
		deviceName = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.deviceName;
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringCurveData',
		success:function(response) {
			if(isNotVal(Ext.getCmp(panelId))){
				Ext.getCmp(panelId).getEl().unmask();
            }
			
			var result =  Ext.JSON.decode(response.responseText);
			var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    var data = result.list;
		    var totals=result.curveCount;
		    var legendName =result.curveItems;
		    var curveConf=result.curveConf;
		    var colors=[];
		    for(var i=0;i<curveConf.length;i++){
		    	if(curveConf[i].color==''){
		    		colors.push(defaultColors[i%10]);
		    	}else{
		    		colors.push('#'+curveConf[i].color);
		    	}
		    }
		   
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 2) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
            
		    var columnCount = 2;
		    var rowCount = (totals%columnCount==0)?(totals/columnCount):(parseInt(totals/columnCount)+1);
		    
		    var chartWidth2 = 50 + '%';
            var chartHeight2 = 50 + '%';
            
            if(totals==1){
            	chartWidth2 = 100 + '%';
                chartHeight2 = 100 + '%';
            }else if(totals==2){
            	chartWidth2 = 100 + '%';
                chartHeight2 = 50 + '%';
            }else{
            	chartWidth2 = 50 + '%';
                chartHeight2 = 50 + '%';
            }
            
            $('#'+containerId).html(''); // 将html内容清空
            var htmlResult = '';
            var divId = '';
		    
            if (totals > 0) {
            	//添加div
            	for(var i=0;i<totals;i++){
            		divId = divPrefix + i+"_Id";
                    htmlResult += '<div id=\"' + divId + '\"';
                    htmlResult += ' style="height:'+ chartHeight2 +';width:'+ chartWidth2 +';"';
                    htmlResult += '></div>';
            	}
                $('#'+containerId).append(htmlResult);
//                var aa=($('#'+containerId)[0]);
//                var bb=aa.childElementCount;
//                var cc=aa.children;
//                var dd=cc[0].id;
                //数据处理
                for(var i=0;i<totals;i++){
                	divId = divPrefix + i+"_Id";
                	var xTitle='';
                	var yTitle=legendName[i];
                	var title = result.deviceName+":"+legendName[i].split("(")[0] + loginUserLanguageResource.trendCurve;
                	var subtitle='';
        		    var color=[];
        		    color.push(colors[i]);
        		    if(color[0]==''){
        		    	color[0]=defaultColors[i%10];
        		    }
        		    var maxValue=null;
    		        var minValue=null;
    		        var allPositive=true;//全部是非负数
    		        var allNegative=true;//全部是负值
    		        
    		        var yAxisOpposite=curveConf[i].yAxisOpposite;
        		    
        		    var series = "[";
    		        series += "{\"name\":\"" + legendName[i] + "\"," 
    		        +"\"lineWidth\":"+curveConf[i].lineWidth+"," 
    		        +"\"dashStyle\":\""+curveConf[i].dashStyle+"\"," 
    		        +"\"marker\":{\"enabled\": true}," 
    		        +"\"dataGrouping\":{\"enabled\": false},";
    		        series += "\"data\":[";
    		        for (var j = 0; j < data.length; j++) {
    		        	series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + data[j].data[i] + "]";
    		            if (j != data.length - 1) {
    		                series += ",";
    		            }
    		            if(parseFloat(data[j].data[i])<0){
    		            	allPositive=false;
    		            }else if(parseFloat(data[j].data[i])>=0){
    		            	allNegative=false;
    		            }
    		        }
    		        series += "]}";
        		    series += "]";
        		    
        		    if(allNegative){
    		        	maxValue=0;
    		        }else if(allPositive){
    		        	minValue=0;
    		        }
        		    
        		    var ser = Ext.JSON.decode(series);
        		    
        		    var timeFormat='%H:%M';
        		    initDeviceRealtimeMonitoringStockChartFn(ser, tickInterval, divId, title, subtitle, xTitle, yTitle,color,false,true,false,timeFormat,maxValue,minValue,yAxisOpposite);
                }
            }
            
            var eastPanel=Ext.getCmp(eastPanelId);
			if(eastPanel.collapsed){
            	var container=$('#'+containerId);
    			if(container!=undefined && container.length>0){
    				var containerChildren=container[0].children;
    				if(containerChildren!=undefined && containerChildren.length>0){
    					for(var i=0;i<containerChildren.length;i++){
    						$("#"+containerChildren[i].id).hide(); 
    					}
    				}
    			}
            }
		},
		failure:function(){
			Ext.getCmp(panelId).getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceName:deviceName,
			deviceId:deviceId,
			deviceType:deviceType,
			calculateType:calculateType
        }
	});
};

function initDeviceRealtimeMonitoringStockChartFn(series, tickInterval, divId, title, subtitle, xtitle,yTitle, color,legend,navigator,scrollbar,timeFormat,maxValue,minValue,yAxisOpposite) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		Highcharts.setOptions({
	        global: {
	            useUTC: false
	        }
	    });
	    var mychart = new Highcharts.stockChart({
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
	        navigator: {
	    		enabled: navigator
	    	},
	    	scrollbar: {
	    		enabled: scrollbar
	    	},
	        rangeSelector: {
	    		buttons: [{
	    			count: 1,
	    			type: 'hour',//minute hour week month all
	    			text: '1'+loginUserLanguageResource.hour
	    		}, {
	    			count: 6,
	    			type: 'hour',
	    			text: '6'+loginUserLanguageResource.hour
	    		}, {
	    			count: 12,
	    			type: 'hour',
	    			text: '12'+loginUserLanguageResource.hour
	    		}, {
	    			type: 'all',
	    			text: loginUserLanguageResource.all
	    		}],
	    		inputEnabled: false,
	    		selected: 0
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
	                    return Highcharts.dateFormat(timeFormat, this.value);
	                },
	                autoRotation:true,//自动旋转
	                rotation: -45 //倾斜度，防止数量过多显示不全  
//	                step: 2
	            }
	        },
	        yAxis: {
	        	max:maxValue,
	    		min:minValue,
	        	lineWidth: 1,
	            title: {
	                text: yTitle,
	                style: {
//	                    color: color,
	                }
	            },
	            labels: {
	            	style: {
//	                    color: color,
	                }
	            },
	            opposite:yAxisOpposite
	      },
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
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	    	    sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
	        },
	        plotOptions: {
	            spline: {
	                lineWidth: 1,
	                fillOpacity: 0.3,
	                marker: {
	                    enabled: true,
	                    radius: 3, //曲线点半径，默认是4
	                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
	                    states: {
	                        hover: {
	                            enabled: true,
	                            radius: 6
	                        }
	                    }
	                },
	                shadow: true
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

function clearVideo(deviceType){
	var panelId1='RealTimeMonitoringRightVideoPanel1';
	var panelId2='RealTimeMonitoringRightVideoPanel2';
	if(videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus.state.play){
		videoPlayrHelper.player1.stop();
	}
	if(videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus.state.play){
		videoPlayrHelper.player2.stop();
	}
	
	var videoPanel1=Ext.getCmp(panelId1);
	var videoPanel2=Ext.getCmp(panelId2);
	if(isNotVal(videoPanel1) && !videoPanel1.isHidden() ){
		videoPanel1.hide();
	}
	if(isNotVal(videoPanel2) && !videoPanel2.isHidden() ){
		videoPanel2.hide();
	}
}

function showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew){
	var videoPanel=Ext.getCmp(panelId);
	if(videoUrl!='' && videoUrl!='null'){
		if(videoPanel.isHidden() ){
			videoPanel.show();
		}
		if(videoNo==1){
			if(isNew){
				if(videoPlayrHelper.player1!=null){
					if(videoPlayrHelper.player1.pluginStatus.state.play){
						videoPlayrHelper.player1.stop();
					}
					$("#"+videoPlayrHelper.player1.id).html('');
					videoPanel.setHtml('');
					videoPlayrHelper.player1=null;
				}
			}
			
			
			if(videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus!=null){
				if(videoPlayrHelper.player1.pluginStatus.state.play){
					videoPlayrHelper.player1.stop()
					.then(()=>{
						if(videoPlayrHelper.player1.accessToken==accessToken){
							videoPlayrHelper.player1.play(videoUrl);
						}else{
							videoPlayrHelper.player1.play({url:videoUrl,accessToken: accessToken});
						}
					});
				}else{
					if(videoPlayrHelper.player1.accessToken==accessToken){
						videoPlayrHelper.player1.play(videoUrl);
					}else{
						videoPlayrHelper.player1.play({url:videoUrl,accessToken: accessToken});
					}
				}
			}else{
				videoPanel.setHtml('<div id="'+divId+'" style="width:100%;height:100%;"></div>');
				var videoWidth=$("#"+divId).width();
				var videoHeight=$("#"+divId).height();
				videoPlayrHelper.player1 = new EZUIKit.EZUIKitPlayer({
		        	id: divId, // 视频容器ID
		        	accessToken: accessToken,
		            url: videoUrl,
		            template: 'mobileLive', // pcLive -PC直播全量版;simple - PC直播极简版;standard-PC直播标准版;security - PC直播安防版(预览回放);voice-PC直播语音版; theme-可配置主题；mobileLive-H5直播全量版 
		            plugin: ['talk'],                       // 加载插件，talk-对讲
		            audio:0, //是否默认开启声音 1：打开（默认） 0：关闭
//		            env: {
//		                domain: "https://iusopen.ezvizlife.com", // 北美地区
//		            },
		            width: videoWidth,
		            height: videoHeight
		        });
			}
		}else if(videoNo==2){
			if(isNew){
				if(videoPlayrHelper.player2!=null){
					if(videoPlayrHelper.player2.pluginStatus.state.play){
						videoPlayrHelper.player2.stop();
					}
					$("#"+videoPlayrHelper.player2.id).html('');
					videoPanel.setHtml('');
					videoPlayrHelper.player2=null;
				}
			}
			if(videoPlayrHelper.player2!=null){
				if(videoPlayrHelper.player2.pluginStatus.state.play){
					videoPlayrHelper.player2.stop()
					.then(()=>{
						if(videoPlayrHelper.player2.accessToken==accessToken){
							videoPlayrHelper.player2.play(videoUrl);
						}else{
							videoPlayrHelper.player2.play({url:videoUrl,accessToken: accessToken});
						}
					});
				}else{
					if(videoPlayrHelper.player2.accessToken==accessToken){
						videoPlayrHelper.player2.play(videoUrl);
					}else{
						videoPlayrHelper.player2.play({url:videoUrl,accessToken: accessToken});
					}
				}
			}else{
				videoPanel.setHtml('<div id="'+divId+'" style="width:100%;height:100%;"></div>');
				var videoWidth=$("#"+divId).width();
				var videoHeight=$("#"+divId).height();
				videoPlayrHelper.player2 = new EZUIKit.EZUIKitPlayer({
		        	id: divId, // 视频容器ID
		        	accessToken: accessToken,
		            url: videoUrl,
		            template: 'mobileLive', // pcLive -PC直播全量版;simple - PC直播极简版;standard-PC直播标准版;security - PC直播安防版(预览回放);voice-PC直播语音版; theme-可配置主题；mobileLive-H5直播全量版 
		            plugin: ['talk'],                       // 加载插件，talk-对讲
		            audio:0, //是否默认开启声音 1：打开（默认） 0：关闭
		            width: videoWidth,
		            height: videoHeight
		        });
			}
		}
	}else{
		if(videoNo==1){
			if(videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus.state.play){
				videoPlayrHelper.player1.stop();
			}
			if(!videoPanel.isHidden() ){
				videoPanel.hide();
			}
		}else if(videoNo==2){
			if(videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus.state.play){
				videoPlayrHelper.player2.stop();
			}
			if(!videoPanel.isHidden() ){
				videoPanel.hide();
			}
		}
	}

}

function initVideo(panelId,divId,videoUrl,videoKeyId,deviceType,videoNo,isNew){
	var now=new Date().getTime();
	var accessToken='';
	if(videoUrl=='ezopen://open.ys7.com/G39444019/1.live'){
		if(demoAccessTokenInfo==null || (demoAccessTokenInfo.code!='200') || now>demoAccessTokenInfo.data.expireTime){
			fetch('https://open.ys7.com/jssdk/ezopen/demo/token')
	        .then(response => response.json())
	        .then(res => {
	        	demoAccessTokenInfo = res;
	        	if(demoAccessTokenInfo.code=='200'){
	        		accessToken=demoAccessTokenInfo.data.accessToken;
	        	}
	        	showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew);
	        });
		}else{
			if(demoAccessTokenInfo.code=='200'){
        		accessToken=demoAccessTokenInfo.data.accessToken;
        	}
			showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew);
		}
	}else{
		Ext.Ajax.request({
			method:'POST',
			url:context + '/realTimeMonitoringController/getUIKitAccessToken',
			success:function(response) {
				accessTokenInfo = Ext.JSON.decode(response.responseText);
				if(accessTokenInfo.success){
					accessToken=accessTokenInfo.accessToken;
				}
				showVideo(panelId,divId,videoUrl,accessToken,deviceType,videoNo,isNew);
			},
			failure:function(){
				Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
			},
			params: {
				videoKeyId: videoKeyId
	        }
		});
	}
}

function createVideo(deviceType,data,videoNo,isNew){
	
	var rightTabPanel = Ext.getCmp("RealTimeMonitoringRightTabPanel");
	if(rightTabPanel!=undefined){
		var RealTimeMonitoringRightControlAndVideoPanel = rightTabPanel.getComponent("RealTimeMonitoringRightControlAndVideoPanel");
		if(RealTimeMonitoringRightControlAndVideoPanel!=undefined){
			var panelId1='RealTimeMonitoringRightVideoPanel1';
			var divId1='RealTimeMonitoringRightVideoDiv1_Id';
			
			var panelId2='RealTimeMonitoringRightVideoPanel2';
			var divId2='RealTimeMonitoringRightVideoDiv2_Id';
			
			var videoUrl1=data.videoUrl1,videoUrl2=data.videoUrl2;
			var videoKeyId1=data.videoKeyId1,videoKeyId2=data.videoKeyId2;
			
			if(videoNo==1){
				initVideo(panelId1,divId1,videoUrl1,videoKeyId1,deviceType,1,isNew);
			}else if(videoNo==2){
				initVideo(panelId2,divId2,videoUrl2,videoKeyId2,deviceType,2,isNew);
			}else{
				initVideo(panelId1,divId1,videoUrl1,videoKeyId1,deviceType,1,isNew);
				initVideo(panelId2,divId2,videoUrl2,videoKeyId2,deviceType,2,isNew);
			}
		}
	}
}

function getDeviceRealTimeOverviewDataPage(deviceId,deviceType,limit){
	var dataPage=1;
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var FESdiagramResultStatValue='';
	var commStatusStatValue='';
	var runStatusStatValue='';
	var deviceTypeStatValue='';
	deviceName=Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
	FESdiagramResultStatValue=Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").getValue();
	commStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").getValue();
	runStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").getValue();
	deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
	
	Ext.Ajax.request({
		method:'POST',
		async: false,
		url:context + '/realTimeMonitoringController/getDeviceRealTimeOverviewDataPage',
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

function getDeviceAddInfoAndControlInfo(deviceId,deviceType){
	var deviceInfo={};
	deviceInfo.videoNum=0;
	deviceInfo.controlItemNum=0;
	deviceInfo.addInfoNum=0;
	deviceInfo.auxiliaryDeviceNum=0;
	Ext.Ajax.request({
		method:'POST',
		async: false,
		url:context + '/realTimeMonitoringController/getDeviceAddInfoAndControlInfo',
		success:function(response) {
			deviceInfo = Ext.JSON.decode(response.responseText);
		},
		failure:function(){
		},
		params: {
            deviceType:deviceType,
            deviceId:deviceId
        }
	});
	return deviceInfo;
}




function cleanDeviceAddInfoAndControlInfo(){
	clearVideo();
	if(isNotVal(Ext.getCmp("RealTimeMonitoringRightControlPanel")) && isNotVal(Ext.getCmp("RealTimeMonitoringRightControlPanel"))   ){
		Ext.getCmp("RealTimeMonitoringRightControlPanel").removeAll();
	}
	if(isNotVal(Ext.getCmp("RealTimeMonitoringRightDeviceAddInfoPanel")) && isNotVal(Ext.getCmp("RealTimeMonitoringRightDeviceAddInfoPanel"))){
		Ext.getCmp("RealTimeMonitoringRightDeviceAddInfoPanel").removeAll();
	}
	if(isNotVal(Ext.getCmp("RealTimeMonitoringRightAuxiliaryDeviceInfoPanel")) && isNotVal(Ext.getCmp("RealTimeMonitoringRightAuxiliaryDeviceInfoPanel"))){
		Ext.getCmp("RealTimeMonitoringRightAuxiliaryDeviceInfoPanel").removeAll();
	}
}

function exportDeviceRealTimeMonitoringData(deviceId,deviceName,calculateType) {
	var timestamp=new Date().getTime();
	var key='exportDeviceRealTimeMonitoringData_'+deviceId+'_'+timestamp;
	var maskPanelId='RealTimeMonitoringInfoDataPanel_Id';
	var url = context + '/realTimeMonitoringController/exportDeviceRealTimeMonitoringData';
    var param = "&deviceId=" + deviceId
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + '&calculateType='+calculateType
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    openExcelWindow(url + '?flag=true' + param);
};