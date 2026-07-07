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
        	        				if(Ext.getCmp("RealTimeMonitoringTabPanel")!=undefined){
            	        				Ext.getCmp("RealTimeMonitoringTabPanel").el.mask(loginUserLanguageResource.loadingData).show();
        	        				}
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
        			items:[
        				{
        				xtype: 'tbfill'
        			},
        			{
        				xtype:"toolbar",
        				width:0,
        				height:0,
        				border: false,
        				id:'ResourceMonitoringToolbar_Id',
        				style: 'background-color: transparent; background-image: none;',
        				items:['->',
        	                {
    	            		xtype: 'button',
    	                    id:"CPUUsedPercentLabel_id",
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
    	                    id:"redisRunStatusProbeLabel_id",
    	                    text: loginUserLanguageResource.resourcesMonitoring_cache+'',
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
    	                    id:"tableSpaceSizeProbeLabel_id",
    	                    text: loginUserLanguageResource.resourcesMonitoring_tablespaces+':',
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
    	                    id:"adRunStatusProbeLabel_id",
    	                    text: loginUserLanguageResource.resourcesMonitoring_ad,
    	                    hidden: !IoTConfig,
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
        			}
//        			,{
//        				header:false,
//        				xtype: 'panel',
//        				width:'70%',
//        				height:0,
//            			tbar: {
//            				border: false,
//            				style: 'background-color: transparent; background-image: none;',
//            				items:[]
//            			}
//        			}
        			]
        		},
        		items: items,
        		listeners: {
    				beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
    					if(Ext.getCmp("RealTimeMonitoringTabPanel")!=undefined){
        					Ext.getCmp("RealTimeMonitoringTabPanel").el.mask(loginUserLanguageResource.loadingData).show();
        				}
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
            }],
            listeners: {
    			afterrender: function ( panel, eOpts) {
    				const myTabPanel = Ext.getCmp('RealTimeMonitoringTabPanel');
    		    	const totalTabsWidth = getTotalTabsWidth(myTabPanel);
    		    	
    		    	var maxWidth = myTabPanel.getWidth() - totalTabsWidth - 10; // 预留少量边距
    		    	var newWidth = Math.min(parseInt((myTabPanel.getWidth() - totalTabsWidth) * 0.95), maxWidth);
    		    	
    		    	var newWidthPercent=parseInt(newWidth/myTabPanel.getWidth()*100)+'%';
    		    	
    		    	Ext.getCmp('ResourceMonitoringToolbar_Id').setWidth(newWidthPercent);
    			},
    			resize: function (abstractcomponent, adjWidth, adjHeight, options) {
//    				var myTabPanel = Ext.getCmp('RealTimeMonitoringTabPanel');
//    			    if (myTabPanel && myTabPanel.rendered) {
//    			        var totalTabsWidth = getTotalTabsWidth(myTabPanel);
//    			        var maxWidth = myTabPanel.getWidth() - totalTabsWidth - 10; // 预留少量边距
//        		    	var newWidth = Math.min(parseInt((myTabPanel.getWidth() - totalTabsWidth) * 0.95), maxWidth);
//    			        Ext.getCmp('ResourceMonitoringToolbar_Id').setWidth(newWidth);
//    			    }
    			}
    		}
        });
        me.callParent(arguments);
    }

});

function realTimeDataRefresh(){
	
	if(videoPlayrHelper!=null && videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus!=undefined && videoPlayrHelper.player1.pluginStatus.state.play){
		videoPlayrHelper.player1.stop();
	}
	if(videoPlayrHelper!=null && videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus!=undefined && videoPlayrHelper.player2.pluginStatus.state.play){
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
	var statTabActiveId =tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
	
	var tabChange=false;
	if(projectTabConfig.DeviceRealTimeMonitoring.FESDiagramStatPie==false){
		tabPanel.remove(Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id"));
		if(statTabActiveId=="RealTimeMonitoringFESdiagramResultStatGraphPanel_Id"){
			tabChange=true;
		}
	}else{
		var RealTimeMonitoringFESdiagramResultStatGraphPanel = tabPanel.getComponent("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id");
		if(RealTimeMonitoringFESdiagramResultStatGraphPanel==undefined){
			tabPanel.insert(0,realtimeStatTabItems[0]);
		}
	}
	
	if(projectTabConfig.DeviceRealTimeMonitoring.CommStatusStatPie==false){
		tabPanel.remove(Ext.getCmp("RealTimeMonitoringStatGraphPanel_Id"));
		if(statTabActiveId=="RealTimeMonitoringStatGraphPanel_Id"){
			tabChange=true;
		}
	}else{
		var RealTimeMonitoringStatGraphPanel = tabPanel.getComponent("RealTimeMonitoringStatGraphPanel_Id");
		if(RealTimeMonitoringStatGraphPanel==undefined){
			tabPanel.insert(1,realtimeStatTabItems[1]);
		}
	}
	
	if(projectTabConfig.DeviceRealTimeMonitoring.RunStatusStatPie==false){
		tabPanel.remove(Ext.getCmp("RealTimeMonitoringRunStatusStatGraphPanel_Id"));
		if(statTabActiveId=="RealTimeMonitoringRunStatusStatGraphPanel_Id"){
			tabChange=true;
		}
	}else{
		var RealTimeMonitoringRunStatusStatGraphPanel = tabPanel.getComponent("RealTimeMonitoringRunStatusStatGraphPanel_Id");
		if(RealTimeMonitoringRunStatusStatGraphPanel==undefined){
			tabPanel.insert(2,realtimeStatTabItems[2]);
		}
	}
	
	if(projectTabConfig.DeviceRealTimeMonitoring.NumStatusStatPie==false){
		tabPanel.remove(Ext.getCmp("RealTimeMonitoringNumStatusStatGraphPanel_Id"));
		if(statTabActiveId=="RealTimeMonitoringNumStatusStatGraphPanel_Id"){
			tabChange=true;
		}
	}else{
		var RealTimeMonitoringNumStatusStatGraphPanel = tabPanel.getComponent("RealTimeMonitoringNumStatusStatGraphPanel_Id");
		if(RealTimeMonitoringNumStatusStatGraphPanel==undefined){
			tabPanel.insert(3,realtimeStatTabItems[3]);
		}
	}
	
	if(projectTabConfig.DeviceRealTimeMonitoring.FESDiagramStatPie==false 
			&& projectTabConfig.DeviceRealTimeMonitoring.CommStatusStatPie==false 
			&& projectTabConfig.DeviceRealTimeMonitoring.RunStatusStatPie==false
			&& projectTabConfig.DeviceRealTimeMonitoring.NumStatusStatPie==false
			){
		tabPanel.hide();
		
		Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').setValue('');
 		Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').setRawValue('');
 		refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp("selectedDeviceId_global").getValue()),deviceType,Ext.getCmp("RealTimeMonitoringListGridPanel_Id"),'AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore');
	}else{
		if(tabPanel.isHidden() ){
			tabPanel.show();
		}
		if(tabPanel.getActiveTab()==undefined){
			if(tabPanel.items.length>0){
				tabPanel.setActiveTab(0);
			}
		}else{
			if(!tabChange){
		 		statTabActiveId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
		 		if(statTabActiveId=="RealTimeMonitoringFESdiagramResultStatGraphPanel_Id"){
		 			loadAndInitFESdiagramResultStat(true);
		 		}else if(statTabActiveId=="RealTimeMonitoringStatGraphPanel_Id"){
		 			loadAndInitCommStatusStat(true);
		 		}else if(statTabActiveId=="RealTimeMonitoringRunStatusStatGraphPanel_Id"){
		 			loadAndInitRunStatusStat(true);
		 		}else if(statTabActiveId=="RealTimeMonitoringNumStatusStatGraphPanel_Id"){
					loadAndInitNumStatusStat(true);
				}else if(statTabActiveId=="RealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
		 			loadAndInitDeviceTypeStat(true);
		 		}
		 		
		 		Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').setValue('');
		 		Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').setRawValue('');
		 		refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp("selectedDeviceId_global").getValue()),deviceType,Ext.getCmp("RealTimeMonitoringListGridPanel_Id"),'AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore');
		 	}
		}
	}
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

function createAlarmSmartDot(color, number, options) {
    options = options || {};
    var height = options.height || 10;
    var numberStr = number.toString();
    var textLength = numberStr.length;
    var width = textLength === 1 ? height : height + (textLength - 1) * 5;
    var radius = height / 2;
    var capsulePath = Ext.String.format(
        'M {0},0 a {0},{0} 0 0 1 0,{1} h {2} a {0},{0} 0 0 1 0,-{1} z',
        radius, height, width - height
    );
    var textX = width / 2;
    var textY = height / 2 + 3;
    var fontSize = Math.floor(height * 0.7);
    return Ext.String.format(
        '<svg width="{0}" height="{1}" style="display: inline-block; vertical-align: middle; margin-right: 2px;">' +
        '  <path d="{2}" fill="{3}" stroke="{4}" stroke-width="1"/>' +
        '  <text x="{5}" y="{6}" text-anchor="middle" font-size="{7}" font-weight="normal" fill="white">{8}</text>' +
        '</svg>',
        width, height, capsulePath, color,
        Ext.isIE ? 'none' : 'rgba(0,0,0,0.2)',
        textX, textY, fontSize, numberStr
    );
}

adviceRealtimeDeviceOverviewDeviceNameColor = function (val, o, p, e) {
    var alarmInfo = p.data.alarmInfo;
    var maxAlarmLevel = 0;
    var alarmCount_Level1 = 0;
    var alarmCount_Level2 = 0;
    var alarmCount_Level3 = 0;
    var alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
    if (isNotVal(alarmShowStyle) && alarmShowStyle != {}) {
        if (isNotVal(alarmInfo) && alarmInfo.length > 0) {
            for (var i = 0; i < alarmInfo.length; i++) {
                if (alarmInfo[i].alarmLevel == 100) {
                    alarmCount_Level1++;
                } else if (alarmInfo[i].alarmLevel == 200) {
                    alarmCount_Level2++;
                } else if (alarmInfo[i].alarmLevel == 300) {
                    alarmCount_Level3++;
                }

                if (alarmInfo[i].alarmLevel > 0 && (maxAlarmLevel == 0 || alarmInfo[i].alarmLevel < maxAlarmLevel)) {
                    maxAlarmLevel = alarmInfo[i].alarmLevel;
                }
            }
        }
    }

    if (val == undefined) {
        val = '';
    }
    var returnInfo = '';
    if (isNotVal(val)) {
        if (maxAlarmLevel == 0) {
            returnInfo= Ext.String.format('<span data-qtip="{0}">{1}</span>',Ext.String.htmlEncode(val),Ext.String.htmlEncode(val));
        } else {
            var color = '';
            if (maxAlarmLevel == 100) {
                color = '#' + alarmShowStyle.Data.FirstLevel.Color;
            } else if (maxAlarmLevel == 200) {
                color = '#' + alarmShowStyle.Data.SecondLevel.Color;
            } else if (maxAlarmLevel == 300) {
                color = '#' + alarmShowStyle.Data.ThirdLevel.Color;
            }

            var rtn = '';
            if (alarmCount_Level1 > 0) {
                var level1SvgDot = createAlarmSmartDot('#' + alarmShowStyle.Data.FirstLevel.Color, alarmCount_Level1);
                rtn += level1SvgDot;
            }
            if (alarmCount_Level2 > 0) {
                var level2SvgDot = createAlarmSmartDot('#' + alarmShowStyle.Data.SecondLevel.Color, alarmCount_Level2);
                rtn += level2SvgDot;
            }
            if (alarmCount_Level3 > 0) {
                var level3SvgDot = createAlarmSmartDot('#' + alarmShowStyle.Data.ThirdLevel.Color, alarmCount_Level3);
                rtn += level3SvgDot;
            }
            var content = rtn + Ext.util.Format.htmlEncode(val);
            return '<span style="display: inline-block; white-space: nowrap;">' + content + '</span>';
        }
        return returnInfo;
    }
}


/**
 * 创建报警数量徽章（CSS 实现，高度固定，避免锁定列行高问题）
 * @param {number} number 报警数量（如 3, 12, 105）
 * @param {string} bgColor 背景色（支持 "dc2828" 或 "#dc2828" 格式）
 * @param {string} textColor 文本颜色（可选，默认白色）
 * @returns {string} HTML 字符串
 */
function createAlarmBadge(number, bgColor, textColor) {
    if (!number || number <= 0) return '';
    
    // 统一格式：确保 # 前缀
    bgColor = (bgColor && bgColor.charAt(0) === '#') ? bgColor : '#' + bgColor;
    textColor = (textColor && textColor.charAt(0) === '#') ? textColor : (textColor ? '#' + textColor : '#ffffff');
    
    // 固定高度 10px，line-height 相等，边框半径设为 10px 可保证圆形/胶囊自适应
    // 内边距左右各 3px，最小宽度 10px，数字字体 7px（可读性）
    var style = 'display: inline-block;' +
                'background-color: ' + bgColor + ';' +
                'color: ' + textColor + ';' +
                'border-radius: 10px;' +           // 高度一半，单数圆形多数胶囊
                'padding: 0 3px;' +
                'min-width: 10px;' +               // 最小宽度等于高度
                'height: 10px;' +
                'line-height: 10px;' +
                'text-align: center;' +
                'font-size: 7px;' +                // 字体缩小适应 10px 高度
                'font-weight: normal;' +
                'margin-right: 3px;' +
                'vertical-align: middle;' +
                'box-sizing: border-box;';
    
    return '<span style="' + style + '">' + number + '</span>';
}

//adviceDeviceOverviewDeviceNameShowInfo = function (val, o, p, e) {
//    var alarmInfo = p.data.alarmInfo;
//    var maxAlarmLevel = 0;
//    var alarmCount_Level1 = 0;
//    var alarmCount_Level2 = 0;
//    var alarmCount_Level3 = 0;
//    var alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
//    
//    if (isNotVal(alarmShowStyle) && alarmShowStyle != {}) {
//        if (isNotVal(alarmInfo) && alarmInfo.length > 0) {
//            for (var i = 0; i < alarmInfo.length; i++) {
//                if (alarmInfo[i].alarmLevel == 100) {
//                    alarmCount_Level1++;
//                } else if (alarmInfo[i].alarmLevel == 200) {
//                    alarmCount_Level2++;
//                } else if (alarmInfo[i].alarmLevel == 300) {
//                    alarmCount_Level3++;
//                }
//                if (alarmInfo[i].alarmLevel > 0 && (maxAlarmLevel == 0 || alarmInfo[i].alarmLevel < maxAlarmLevel)) {
//                    maxAlarmLevel = alarmInfo[i].alarmLevel;
//                }
//            }
//        }
//    }
//
//    if (val == undefined) val = '';
//    if (!isNotVal(val)) return '';
//
//    // 对名称进行 HTML 编码（防 XSS）
//    var encodedVal = Ext.util.Format.htmlEncode(val);
//
//    // 构建内部内容（可能是纯名称，或徽章+名称）
//    var innerHtml = '';
//    if (maxAlarmLevel == 0) {
//        innerHtml = encodedVal;
//    } else {
//        // 拼接报警徽章（原有逻辑）
//        if (alarmCount_Level1 > 0) {
//            var bg1 = alarmShowStyle.Data.FirstLevel.Color || 'dc2828';
//            var text1 = alarmShowStyle.Data.FirstLevel.ColorText || 'ffffff';
//            innerHtml += createAlarmBadge(alarmCount_Level1, bg1, text1);
//        }
//        if (alarmCount_Level2 > 0) {
//            var bg2 = alarmShowStyle.Data.SecondLevel.Color || 'f09614';
//            var text2 = alarmShowStyle.Data.SecondLevel.ColorText || 'ffffff';
//            innerHtml += createAlarmBadge(alarmCount_Level2, bg2, text2);
//        }
//        if (alarmCount_Level3 > 0) {
//            var bg3 = alarmShowStyle.Data.ThirdLevel.Color || 'fae600';
//            var text3 = alarmShowStyle.Data.ThirdLevel.ColorText || '333333';
//            innerHtml += createAlarmBadge(alarmCount_Level3, bg3, text3);
//        }
//        innerHtml += encodedVal;  // 追加设备名称
//    }
//
//    // 统一包裹一个外层容器，并设置 data-qtip 提示设备名称（始终提示）
//    // 同时保持 data-dismissDelay 控制延迟（10秒）
////    return '<span data-qtip="' + encodedVal + '" data-dismissDelay="10000">' + innerHtml + '</span>';
//    
//    return Ext.String.format('<span data-qtip="{0}">{1}</span>',encodedVal,innerHtml);
//};

//adviceDeviceOverviewDeviceNameShowInfo = function (val, o, p, e) {
//    // 1. 获取报警信息和样式配置
//    var alarmInfo = p.data.alarmInfo;
//    var maxAlarmLevel = 0;
//    var alarmCount_Level1 = 0;
//    var alarmCount_Level2 = 0;
//    var alarmCount_Level3 = 0;
//    var alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
//
//    // 2. 统计报警数量
//    if (isNotVal(alarmShowStyle) && alarmShowStyle != {}) {
//        if (isNotVal(alarmInfo) && alarmInfo.length > 0) {
//            for (var i = 0; i < alarmInfo.length; i++) {
//                var level = alarmInfo[i].alarmLevel;
//                if (level == 100) alarmCount_Level1++;
//                else if (level == 200) alarmCount_Level2++;
//                else if (level == 300) alarmCount_Level3++;
//                if (level > 0 && (maxAlarmLevel == 0 || level < maxAlarmLevel)) {
//                    maxAlarmLevel = level;
//                }
//            }
//        }
//    }
//
//    // 3. 处理空值
//    if (val == undefined) val = '';
//    if (!isNotVal(val)) return '';
//
//    var encodedVal = Ext.String.htmlEncode(val);
//    var displayHtml = encodedVal;          // 单元格显示内容
//    var tipText = encodedVal;              // 悬停提示纯文本（默认）
//
//    // 4. 有报警时构建徽章和提示文本
//    if (maxAlarmLevel != 0) {
//        // 4a. 拼接报警徽章（保留颜色）
//        var innerHtml = '';
//        if (alarmCount_Level1 > 0) {
//            var bg1 = alarmShowStyle.Data.FirstLevel.Color || 'dc2828';
//            var text1 = alarmShowStyle.Data.FirstLevel.ColorText || 'ffffff';
//            innerHtml += createAlarmBadge(alarmCount_Level1, bg1, text1);
//        }
//        if (alarmCount_Level2 > 0) {
//            var bg2 = alarmShowStyle.Data.SecondLevel.Color || 'f09614';
//            var text2 = alarmShowStyle.Data.SecondLevel.ColorText || 'ffffff';
//            innerHtml += createAlarmBadge(alarmCount_Level2, bg2, text2);
//        }
//        if (alarmCount_Level3 > 0) {
//            var bg3 = alarmShowStyle.Data.ThirdLevel.Color || 'fae600';
//            var text3 = alarmShowStyle.Data.ThirdLevel.ColorText || '333333';
//            innerHtml += createAlarmBadge(alarmCount_Level3, bg3, text3);
//        }
//        displayHtml = innerHtml + encodedVal;
//
//        // 4b. 构建纯文本提示（不含 HTML 标签）
//        var plainParts = [];
//        if (alarmCount_Level1 > 0) plainParts.push(loginUserLanguageResource.alarmLevel1+':' + alarmCount_Level1);
//        if (alarmCount_Level2 > 0) plainParts.push(loginUserLanguageResource.alarmLevel2+':' + alarmCount_Level2);
//        if (alarmCount_Level3 > 0) plainParts.push(loginUserLanguageResource.alarmLevel3+':' + alarmCount_Level3);
//        if (plainParts.length > 0) {
//            tipText = encodedVal + ' ' + plainParts.join(';');
//        }
//    }
//
//    // 5. 使用 data-qtip 返回（纯文本，兼容所有 Ext JS 版本）
//    return Ext.String.format(
//        '<span data-qtip="{0}" data-dismissDelay="10000" style="display:inline-block;white-space:nowrap;">{1}</span>',
//        Ext.String.htmlEncode(tipText),   // 转义纯文本内容
//        displayHtml
//    );
//};

//adviceDeviceOverviewDeviceNameShowInfo = function (val, o, p, e) {
//    // 1. 获取报警信息和样式配置
//    var alarmInfo = p.data.alarmInfo;
//    var maxAlarmLevel = 0;
//    var alarmCount_Level1 = 0;
//    var alarmCount_Level2 = 0;
//    var alarmCount_Level3 = 0;
//    var alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
//
//    // 2. 统计报警数量
//    if (isNotVal(alarmShowStyle) && alarmShowStyle != {}) {
//        if (isNotVal(alarmInfo) && alarmInfo.length > 0) {
//            for (var i = 0; i < alarmInfo.length; i++) {
//                var level = alarmInfo[i].alarmLevel;
//                if (level == 100) alarmCount_Level1++;
//                else if (level == 200) alarmCount_Level2++;
//                else if (level == 300) alarmCount_Level3++;
//                if (level > 0 && (maxAlarmLevel == 0 || level < maxAlarmLevel)) {
//                    maxAlarmLevel = level;
//                }
//            }
//        }
//    }
//
//    // 3. 处理空值
//    if (val == undefined) val = '';
//    if (!isNotVal(val)) return '';
//
//    // 4. 设备名称 HTML 编码（防 XSS）
//    var encodedVal = Ext.String.htmlEncode(val);
//
//    // 5. 构建显示内容（单元格）和悬停提示内容
//    var displayHtml = encodedVal;          // 默认仅设备名称
//    var tipHtml = encodedVal;              // 默认提示仅设备名称（纯文本）
//
//    if (maxAlarmLevel != 0) {
//        // ---- 有报警时 ----
//        // 5a. 拼接报警徽章（显示用）
//        var innerHtml = '';
//        if (alarmCount_Level1 > 0) {
//            var bg1 = alarmShowStyle.Data.FirstLevel.Color || 'dc2828';
//            var text1 = alarmShowStyle.Data.FirstLevel.ColorText || 'ffffff';
//            innerHtml += createAlarmBadge(alarmCount_Level1, bg1, text1);
//        }
//        if (alarmCount_Level2 > 0) {
//            var bg2 = alarmShowStyle.Data.SecondLevel.Color || 'f09614';
//            var text2 = alarmShowStyle.Data.SecondLevel.ColorText || 'ffffff';
//            innerHtml += createAlarmBadge(alarmCount_Level2, bg2, text2);
//        }
//        if (alarmCount_Level3 > 0) {
//            var bg3 = alarmShowStyle.Data.ThirdLevel.Color || 'fae600';
//            var text3 = alarmShowStyle.Data.ThirdLevel.ColorText || '333333';
//            innerHtml += createAlarmBadge(alarmCount_Level3, bg3, text3);
//        }
//        displayHtml = innerHtml + encodedVal;   // 徽章 + 设备名称（名称无额外颜色）
//
//        // 5b. 构建带颜色的 HTML 提示（使用变量，无硬编码文字）
//        var tipParts = [];
//        if (alarmCount_Level1 > 0) {
//            var color1 = '#' + (alarmShowStyle.Data.FirstLevel.Color || 'dc2828');
//            // 使用单引号包裹 style 值，避免与外层 data-qtip 的双引号冲突
//            tipParts.push('<span style=\'color:' + color1 + ';\'>' + loginUserLanguageResource.alarmLevel1 + ': ' + alarmCount_Level1 + '</span>');
//        }
//        if (alarmCount_Level2 > 0) {
//            var color2 = '#' + (alarmShowStyle.Data.SecondLevel.Color || 'f09614');
//            tipParts.push('<span style=\'color:' + color2 + ';\'>' + loginUserLanguageResource.alarmLevel2 + ': ' + alarmCount_Level2 + '</span>');
//        }
//        if (alarmCount_Level3 > 0) {
//            var color3 = '#' + (alarmShowStyle.Data.ThirdLevel.Color || 'fae600');
//            tipParts.push('<span style=\'color:' + color3 + ';\'>' + loginUserLanguageResource.alarmLevel3 + ': ' + alarmCount_Level3 + '</span>');
//        }
//        if (tipParts.length > 0) {
//            // 设备名称 + 换行 + 各报警信息（空格分隔）
//            tipHtml = encodedVal + '<br/>' + tipParts.join(' ');
//        }
//    }
//
//    // 6. 返回最终 HTML
//    // 使用 data-qtip + data-html="true" 确保 HTML 内容被解析
//    // data-dismissDelay 保留 10 秒延迟关闭
//    return Ext.String.format(
//        '<span data-qtip="{0}" data-html="true" data-dismissDelay="10000" style="display:inline-block;white-space:nowrap;">{1}</span>',
//        tipHtml,        // 直接传入 HTML（已包含转义过的设备名称）
//        displayHtml
//    );
//};

adviceDeviceOverviewDeviceNameShowInfo = function (val, o, p, e) {
    // 1. 读取报警配置
    var alarmShowStyle = null;
    try {
        alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
    } catch (ex) {}

    // 2. 统计报警数量
    var alarmInfo = p.data.alarmInfo || [];
    var c1 = 0, c2 = 0, c3 = 0, hasAlarm = false;
    for (var i = 0; i < alarmInfo.length; i++) {
        var lv = alarmInfo[i].alarmLevel;
        if (lv == 100) { c1++; hasAlarm = true; }
        else if (lv == 200) { c2++; hasAlarm = true; }
        else if (lv == 300) { c3++; hasAlarm = true; }
    }

    // 3. 设备名空值处理
    if (val == undefined) val = '';
    if (!isNotVal(val)) return '';
    var encodedName = Ext.String.htmlEncode(val);

    // 4. 单元格显示（保留 createAlarmBadge）
    var displayHtml = encodedName;
    if (hasAlarm && alarmShowStyle) {
        var badgeHtml = '';
        if (c1 > 0) {
            var bg1 = alarmShowStyle.Data.FirstLevel.Color || 'dc2828';
            var tx1 = alarmShowStyle.Data.FirstLevel.ColorText || 'ffffff';
            badgeHtml += createAlarmBadge(c1, bg1, tx1);
        }
        if (c2 > 0) {
            var bg2 = alarmShowStyle.Data.SecondLevel.Color || 'f09614';
            var tx2 = alarmShowStyle.Data.SecondLevel.ColorText || 'ffffff';
            badgeHtml += createAlarmBadge(c2, bg2, tx2);
        }
        if (c3 > 0) {
            var bg3 = alarmShowStyle.Data.ThirdLevel.Color || 'fae600';
            var tx3 = alarmShowStyle.Data.ThirdLevel.ColorText || '333333';
            badgeHtml += createAlarmBadge(c3, bg3, tx3);
        }
        displayHtml = badgeHtml + encodedName;
    }

    // 5. 悬停提示（带美观样式，全部使用单引号）
    var tipHtml = encodedName;
    if (hasAlarm && alarmShowStyle) {
        var parts = [];
        // 一级
        if (c1 > 0) {
            var bg1 = '#' + (alarmShowStyle.Data.FirstLevel.Color || 'dc2828');
            var tx1 = '#' + (alarmShowStyle.Data.FirstLevel.ColorText || 'ffffff');
            parts.push(
                '<span style=\'display:inline-block;background:' + bg1 + ';color:' + tx1 + ';padding:0 8px;border-radius:12px;font-size:11px;font-weight:bold;line-height:18px;margin-right:4px;white-space:nowrap;\'>' +
                (loginUserLanguageResource.alarmLevel1) + ':' + c1 +
                '</span>'
            );
        }
        // 二级
        if (c2 > 0) {
            var bg2 = '#' + (alarmShowStyle.Data.SecondLevel.Color || 'f09614');
            var tx2 = '#' + (alarmShowStyle.Data.SecondLevel.ColorText || 'ffffff');
            parts.push(
                '<span style=\'display:inline-block;background:' + bg2 + ';color:' + tx2 + ';padding:0 8px;border-radius:12px;font-size:11px;font-weight:bold;line-height:18px;margin-right:4px;white-space:nowrap;\'>' +
                (loginUserLanguageResource.alarmLevel2) + ':' + c2 +
                '</span>'
            );
        }
        // 三级
        if (c3 > 0) {
            var bg3 = '#' + (alarmShowStyle.Data.ThirdLevel.Color || 'fae600');
            var tx3 = '#' + (alarmShowStyle.Data.ThirdLevel.ColorText || '333333');
            parts.push(
                '<span style=\'display:inline-block;background:' + bg3 + ';color:' + tx3 + ';padding:0 8px;border-radius:12px;font-size:11px;font-weight:bold;line-height:18px;margin-right:4px;white-space:nowrap;\'>' +
                (loginUserLanguageResource.alarmLevel3) + ':' + c3 +
                '</span>'
            );
        }
        if (parts.length > 0) {
            tipHtml = encodedName + ' ' + parts.join(' ');
        }
    }

    // 6. 返回（data-html="true" 确保 HTML 渲染）
    return Ext.String.format(
        '<span data-qtip="{0}" data-html="true" data-dismissDelay="10000" style="display:inline-block;white-space:nowrap;">{1}</span>',
        tipHtml,
        displayHtml
    );
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
        	thisColumn.locked=true;
        }
        else if (attr.dataIndex.toUpperCase()=='deviceName'.toUpperCase()) {
        	thisColumn.sortable=false;
        	thisColumn.dataIndex=attr.dataIndex;
        	thisColumn.locked=true;
        	thisColumn.renderer=function(value,o,p,e){
        		return adviceDeviceOverviewDeviceNameShowInfo(value,o,p,e);
        	};
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
//        else if (attr.dataIndex.toUpperCase()=='resultName'.toUpperCase()) {
//        	thisColumn.sortable=false;
//        	thisColumn.dataIndex=attr.dataIndex;
//        	thisColumn.renderer=function(value,o,p,e){
//        		return adviceResultStatusColor(value,o,p,e);
//        	};
//        }
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
//        else if (attr.dataIndex.toUpperCase()=='resultName'.toUpperCase()) {
//            myColumns += ',"sortable":false,"dataIndex":"' + attr.dataIndex + '","renderer":function(value,o,p,e){return adviceResultStatusColor(value,o,p,e);}';
//        }
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
    
    Ext.getCmp('ResourceProbeHistoryCurveWindow_Id').setTitle(title);
    
    var subtitle="[" + get_rawData.startDate + "~" + get_rawData.endDate + "]";
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
    }else if(itemCode.toUpperCase()=='tableSpaceSize'.toUpperCase()){
    	legendName = [];
    	legendName.push(loginUserLanguageResource.dataTablespace+"(%)");
    	legendName.push(loginUserLanguageResource.undoTablespace+"(%)");
    	legend=true;
    }
    
    
    var series = [];
    for (var i = 0; i < legendName.length; i++) {
    	var seriesItem = {
    	        name: legendName[i],
    	        data: []
    	    };
        for (var j = 0; j < data.length; j++) {
    		var year = parseInt(data[j].acqTime.split(" ")[0].split("-")[0]);
            var month = parseInt(data[j].acqTime.split(" ")[0].split("-")[1]);
            var day = parseInt(data[j].acqTime.split(" ")[0].split("-")[2]);
            var hour = parseInt(data[j].acqTime.split(" ")[1].split(":")[0]);
            var minute = parseInt(data[j].acqTime.split(" ")[1].split(":")[1]);
            var second = parseInt(data[j].acqTime.split(" ")[1].split(":")[2]);
            var timestamp=Date.parse(data[j].acqTime.replace(/-/g, '/'));
            if(itemCode.toUpperCase()=='cpuUsedPercent'.toUpperCase()){
            	if(isNotVal(data[j].value)){
            		var values=data[j].value.split(";");
            		if(values.length>i){
            			if(isNumber(values[i])){
            				seriesItem.data.push([timestamp, parseFloat(values[i])]);
            			}else{
            				seriesItem.data.push([timestamp, null]);
            			}
            		}else{
            			seriesItem.data.push([timestamp, null]);
            		}
            	}else{
            		seriesItem.data.push([timestamp, null]);
            	}
            }else if(itemCode.toUpperCase()=='jedisStatus'.toUpperCase()){
            	if(isNotVal(data[j].value)){
            		var values=data[j].value.split(";");
            		if(values.length>i){
            			if(isNumber(values[i])){
            				seriesItem.data.push([timestamp, parseFloat(values[i])]);
            			}else{
            				seriesItem.data.push([timestamp, null]);
            			}
            		}else{
            			seriesItem.data.push([timestamp, null]);
            		}
            	}else{
            		seriesItem.data.push([timestamp, null]);
            	}
            }else if(itemCode.toUpperCase()=='tableSpaceSize'.toUpperCase()){
            	if(isNotVal(data[j].value)){
            		var values=data[j].value.split(";");
            		if(values.length>i){
            			if(isNumber(values[i])){
            				seriesItem.data.push([timestamp, parseFloat(values[i])]);
            			}else{
            				seriesItem.data.push([timestamp, null]);
            			}
            		}else{
            			seriesItem.data.push([timestamp, null]);
            		}
            	}else{
            		seriesItem.data.push([timestamp, null]);
            	}
            }else{
            	if(isNotVal(data[j].value)){
            		seriesItem.data.push([timestamp, parseFloat(data[j].value)]);
        		}else{
        			seriesItem.data.push([timestamp, null]);
        		}
            }
        }
        series.push(seriesItem);  
    }
    
    var color = ['#800000', // 红
       '#008C00', // 绿
       '#000000', // 黑
       '#0000FF', // 蓝
       '#F4BD82', // 黄
       '#FF00FF' // 紫
     ];
    initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title,subtitle , loginUserLanguageResource.time, itemName, color,legend,timeFormat);
    return false;
};

function initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, ytitle, color, legend, timeFormat) {
    if ($("#" + divId) != undefined && $("#" + divId)[0] != undefined) {
    	var isZooming = false;
        var zoomTimer = null;
        var $container = $("#" + divId);
        var panelId = "ResourceProbeHistoryCurvePanel_Id";
        
    	var mychart = new Highcharts.Chart({
            chart: {
                renderTo: divId,
                type: 'spline',
                shadow: false,
                borderWidth: 0,
                zoomType: 'xy',
             // 禁用鼠标滚轮缩放
                zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                },
                events: {
                    // 监听缩放完成事件
                    redraw: function() {
                        // 缩放完成后隐藏遮罩
                        if (isZooming) {
                            setTimeout(function() {
                                Ext.getCmp(panelId).getEl().unmask();
                                isZooming = false;
                                if (zoomTimer) clearTimeout(zoomTimer);
                            }, 300);
                        }
                    }
                }
            },
            time: {
                timezoneOffset: new Date().getTimezoneOffset() // 用户本地时区
            },
            credits: {
                enabled: false
            },
            title: {
                text: title,
                style: {
                    fontSize: chartTitleFontSize
                }
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
                labels: {
                    formatter: function () {
                        return this.axis.chart.time.dateFormat(timeFormat, this.value);
                    },
                    autoRotation: true, //自动旋转
                    rotation: -45 //倾斜度，防止数量过多显示不全  
                },
                events: {
                    // 当范围即将改变时触发（缩放前）
                    setExtremes: function(e) {
                        // 检查范围是否真的会改变
                        var currentMin = this.min;
                        var currentMax = this.max;
                        var newMin = e.min;
                        var newMax = e.max;
                        
                        // 如果范围没有实际变化，不显示遮罩
                        if (currentMin === newMin && currentMax === newMax) {
                            return;
                        }
                        
                        // 实际发生了缩放，显示遮罩
                        if (!isZooming) {
                            isZooming = true;
                            Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
                        }
                        
                        // 设置超时保护
                        if (zoomTimer) clearTimeout(zoomTimer);
                        zoomTimer = setTimeout(function() {
                            if (isZooming) {
                                Ext.getCmp(panelId).getEl().unmask();
                                isZooming = false;
                            }
                        }, 5000);
                    }
                }
            },
            yAxis: [{
                lineWidth: 1,
                tickWidth: 1, // 刻度线宽度
                tickLength: 5, // 刻度线长度（可选）
                title: {
                    text: ytitle
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
                fallbackToExportServer: false,
                sourceWidth: $("#" + divId)[0] != undefined ? $("#" + divId)[0].offsetWidth : null,
                sourceHeight: $("#" + divId)[0] != undefined ? $("#" + divId)[0].offsetHeight : null,
                buttons: {
                    contextButton: {
                        menuItems: [
                        	'viewFullscreen',
                        	'printChart',
                        	'separator',
                        	'downloadPNG',
                        	'downloadJPEG',
                        	'downloadSVG',
                        	'separator',
                        	'downloadCSV',
                        	'downloadXLS'
                        ]
                    }
                }
            },
            plotOptions: {
                spline: {
                    lineWidth: 1,
                    fillOpacity: 0.3,
                    marker: {
                        enabled: true,
                        radius: 3, //曲线点半径，默认是4
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
                borderWidth: 0,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
            },
            series: series
        });
    }
};



function exportRealTimeMonitoringDataExcel(orgId,deviceType,deviceName,dictDeviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,numStatusStatValue,deviceTypeStatValue,fileName,title,columnStr) {
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
    + "&numStatusStatValue=" + URLencode(URLencode(numStatusStatValue))
    + "&deviceTypeStatValue=" + URLencode(URLencode(deviceTypeStatValue))
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
};



function gotoDeviceHistory(deviceId,deviceName,deviceType){
	Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
//	Ext.getCmp("realtimeTurnToHisyorySign_Id").setValue('true');//跳转标志
//	Ext.getCmp("realtimeTurnToHisyory_DeviceName").setValue(deviceName);
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
//				haveModule=true;
				break;
			}
		}
	}else{
		haveModule=true;
	}
	if(haveModule){
		tabPanel.setActiveTab(moduleId);
//		Ext.getCmp('HistoryQueryDeviceListComb_Id').setValue(deviceName);
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
		Ext.getCmp("RealTimeMonitoringStatSelectNumStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").setValue('');
		commStatusStatValue='';
		deviceTypeStatValue='';
	}else{
		deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
		commStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").getValue();
	}
	if(Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id")!=undefined && Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").el!=undefined){
		Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData',
		success:function(response) {
			if(Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id")!=undefined && Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").el!=undefined){
				Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").getEl().unmask();
			}
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringFESDiagramResultStatPieOrColChat(result);
		},
		failure:function(){
			if(Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id")!=undefined && Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").el!=undefined){
				Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanel_Id").getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
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
	
	ShowRealTimeMonitoringFESDiagramResultStatPieOrColChat(title,divId, loginUserLanguageResource.deviceCount, pieData,colors);
};

function ShowRealTimeMonitoringFESDiagramResultStatPieOrColChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		Highcharts.chart(divId, {
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false,
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
			},
			credits : {
				enabled : false
			},
			title : {
				text : title,
				style: {
	            	fontSize: chartTitleFontSize
	            }
			},
//			colors : colors,
			tooltip : {
				pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
			},
			legend :{
				align : 'center',//center left
				verticalAlign : 'bottom',//bottom middle
				layout : 'horizontal', //vertical 竖直 horizontal-水平
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
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
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	    	    sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null,
	    	    	    buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
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
		Ext.getCmp("RealTimeMonitoringStatSelectNumStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").setValue('');
	}

	if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData',
		success:function(response) {
			if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringStatPieOrColChat(result);
		},
		failure:function(){
			if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
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
	var activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
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
	ShowRealTimeMonitoringStatPieOrColChat(title,divId, loginUserLanguageResource.deviceCount, pieData,colors);
};

function ShowRealTimeMonitoringStatPieOrColChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		Highcharts.chart(divId, {
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false,
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
			},
			credits : {
				enabled : false
			},
			title : {
				text : title,
				style: {
	            	fontSize: chartTitleFontSize
	            }
			},
			colors : colors,
			tooltip : {
				pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
			},
			legend: {
				align : 'center',
				verticalAlign : 'bottom',
				layout : 'horizontal', //vertical 竖直 horizontal-水平
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
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
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null,
	            		buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
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
		Ext.getCmp("RealTimeMonitoringStatSelectNumStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").setValue('');
	}else{
		deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
	}

	if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
	}
	
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData',
		success:function(response) {
			if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringRunStatusStatPieOrColChat(result);
		},
		failure:function(){
			if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
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
	var activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
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
	ShowRealTimeMonitoringRunStatusStatPieOrColChat(title,divId,loginUserLanguageResource.deviceCount, pieData,colors);
};

function ShowRealTimeMonitoringRunStatusStatPieOrColChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		Highcharts.chart(divId, {
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false,
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
			},
			credits : {
				enabled : false
			},
			title : {
				text : title,
				style: {
	            	fontSize: chartTitleFontSize
	            }
			},
			colors : colors,
			tooltip : {
				pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
			},
			legend: {
				align : 'center',
				verticalAlign : 'bottom',
				layout : 'horizontal', //vertical 竖直 horizontal-水平
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
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
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null,
	            		buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
			},
			series : [{
						type : 'pie',
						name : name,
						data : data
					}]
			});
	}
};

function loadAndInitNumStatusStat(all){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
	var panelId="RealTimeMonitoringNumStatusStatGraphPanel_Id";
	if(all){
		Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectNumStatus_Id").setValue('');
		Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").setValue('');
	}
	if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
	}
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getRealTimeMonitoringNumStatusStatData',
		success:function(response) {
			if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
			initRealTimeMonitoringNumStatusStatPieOrColChat(result);
		},
		failure:function(){
			if(Ext.getCmp(panelId)!=undefined && Ext.getCmp(panelId).el!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
		},
		params: {
			orgId:orgId,
			deviceType:deviceType
        }
	});
}

function initRealTimeMonitoringNumStatusStatPieOrColChat(get_rawData) {
	var divId="RealTimeMonitoringNumStatusStatGraphPanelPieDiv_Id";
	var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
	var activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
	var title=loginUserLanguageResource.numStatus;
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
				singleData.level=datalist[i].level;
				
				if(datalist[i].level==0){
					singleData.color='#'+alarmShowStyle.Data.Normal.BackgroundColor;
				}else if(datalist[i].level==100){
					singleData.color='#'+alarmShowStyle.Data.FirstLevel.BackgroundColor;
				}else if(datalist[i].level==200){
					singleData.color='#'+alarmShowStyle.Data.SecondLevel.BackgroundColor;
				}else if(datalist[i].level==300){
					singleData.color='#'+alarmShowStyle.Data.ThirdLevel.BackgroundColor;
				}
				pieData.push(singleData);
			}
		}
	}
	ShowRealTimeMonitoringNumStatusStatPieOrColChat(title,divId, loginUserLanguageResource.data, pieData,colors);
};

function ShowRealTimeMonitoringNumStatusStatPieOrColChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		Highcharts.chart(divId, {
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false,
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
			},
			credits : {
				enabled : false
			},
			title : {
				text : title,
				style: {
	            	fontSize: chartTitleFontSize
	            }
			},
			colors : colors,
			tooltip : {
//				pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
//				pointFormat: '{series.name}: <b>{point.y}</b> ({point.percentage:.1f}%)<br/>'
				formatter: function() {
			        // 动态生成 tooltip 内容
			        const point = this.point;
			        var count=point.y;
			        var pointName=point.name;
			        var level=point.level;
			        var showInfo=loginUserLanguageResource.alarmCount;
			        if(level==0){
			        	showInfo=loginUserLanguageResource.deviceCount;
			        }
			        return  pointName+'</br>'+showInfo+':'+count;
			    }
			},
			legend: {
				align : 'center',
				verticalAlign : 'bottom',
				layout : 'horizontal', //vertical 竖直 horizontal-水平
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
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
							var statSelectNumStatusId="RealTimeMonitoringStatSelectNumStatus_Id";
							var deviceListComb_Id="RealTimeMonitoringDeviceListComb_Id";
							var gridPanel_Id="RealTimeMonitoringListGridPanel_Id";
							var store="AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore";
							var selectedDeviceId_global="selectedDeviceId_global";
							var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
							var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;

							Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
							
							if(!e.point.selected){//如果没被选中,则本次是选中
								Ext.getCmp(statSelectNumStatusId).setValue(e.point.level);
							}else{//取消选中
								Ext.getCmp(statSelectNumStatusId).setValue('');
							}
							
							Ext.getCmp(deviceListComb_Id).setValue('');
							Ext.getCmp(deviceListComb_Id).setRawValue('');
							
							Ext.getCmp(gridPanel_Id).getStore().loadPage(1);

//							refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp(selectedDeviceId_global).getValue()),deviceType,Ext.getCmp(gridPanel_Id),store);
						}
					},
					showInLegend : true
				}
			},
			exporting:{ 
	            enabled:true,    
	            filename:title,
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null,
	            		buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
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
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
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
	ShowRealTimeMonitoringDeviceTypeStatPieChat(title,divId, loginUserLanguageResource.deviceCount, pieData,colors);
};

function ShowRealTimeMonitoringDeviceTypeStatPieChat(title,divId, name, data,colors) {
	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
		Highcharts.chart(divId, {
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false,
	            zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                }
			},
			credits : {
				enabled : false
			},
			title : {
				text : title,
				style: {
	            	fontSize: chartTitleFontSize
	            }
			},
			colors : colors,
			tooltip : {
				pointFormat : loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>'
			},
			legend: {
				align : 'center',
				verticalAlign : 'bottom',
				layout : 'horizontal', //vertical 竖直 horizontal-水平
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
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
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	    	    sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null,
	    	    buttons: {
	    	    	contextButton: {
	    	    		menuItems: [
	    	    			'viewFullscreen',
	    	    			'printChart',
	    	    			'separator',
	    	    			'downloadPNG',
	    	    			'downloadJPEG',
	    	    			'downloadPDF',
	    	    			'separator',
	    	    			'downloadSVG',
	    	    			'downloadCSV',
	    	    			'downloadXLS'
	    	    			]
	    	    		}
	    	    }
			},
			series : [{
						type : 'pie',
						name : name,
						data : data
					}]
			});
	}
	
};

function calculateTickInterval(data) {
    if (!data || data.length < 2) return 3600 * 1000; // 默认1小时
    
    // 获取时间范围（毫秒）
    var firstTime = Date.parse(data[0].acqTime.replace(/-/g, '/'));
    var lastTime = Date.parse(data[data.length - 1].acqTime.replace(/-/g, '/'));
    var timeRange = lastTime - firstTime;
    var dataCount = data.length;
    
    // 目标：期望显示 5-10 个刻度
    var targetTickCount = 8;
    var tickInterval = Math.ceil(timeRange / targetTickCount);
    
    // 将间隔调整为合理的时间单位
    var hourMs = 3600 * 1000;
    var dayMs = 24 * hourMs;
    
    if (tickInterval <= hourMs) {
        // 小于1小时，向上取整到小时或半小时
        tickInterval = Math.ceil(tickInterval / (30 * 60 * 1000)) * (30 * 60 * 1000);
    } else if (tickInterval <= dayMs) {
        // 1小时到1天，向上取整到小时
        tickInterval = Math.ceil(tickInterval / hourMs) * hourMs;
    } else {
        // 大于1天，向上取整到天
        tickInterval = Math.ceil(tickInterval / dayMs) * dayMs;
    }
    
    return tickInterval;
}

function deviceRealtimeMonitoringCurve(deviceType){
	var selectRowId="RealTimeMonitoringInfoDeviceListSelectRow_Id";
	var gridPanelId="RealTimeMonitoringListGridPanel_Id";
	var contentId="realTimeMonitoringCurveContent";
	var containerId="realTimeMonitoringCurveContainer";
	var divPrefix="realTimeMonitoringCurveDiv";
	var eastPanelId="RealTimeMonitoringCurveAndTableTabPanel";
	var panelId="RealTimeMonitoringCurveTabPanel_Id";
	
	if(Ext.getCmp(panelId)!=undefined){
		Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
	}
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
		   
		    var tickInterval = calculateTickInterval(data);
            
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
                    htmlResult += ' style="height:'+ chartHeight2 +';width:'+ chartWidth2 +';min-height:' + dynamometerCardMinHeight + 'px;"';
                    htmlResult += '></div>';
            	}
                $('#'+containerId).append(htmlResult);
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
    		        var series = [];  // 直接定义为数组
    		        var seriesItem = {
    		            name: legendName[i],
    		            lineWidth: curveConf[i].lineWidth,
    		            dashStyle: curveConf[i].dashStyle,
    		            marker: { enabled: true },
    		            dataGrouping: { enabled: false },
    		            data: []  // 空数组，下面填充
    		        };
    		        
    		        
    		        for (var j = 0; j < data.length; j++) {
    		            var timestamp = Date.parse(data[j].acqTime.replace(/-/g, '/'));
    		            var value = parseFloat(data[j].data[i]);
    		            seriesItem.data.push([timestamp, value]);
    		            
    		            if(parseFloat(data[j].data[i])<0){
    		            	allPositive=false;
    		            }else if(parseFloat(data[j].data[i])>=0){
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
			if(Ext.getCmp(panelId)!=undefined){
				Ext.getCmp(panelId).getEl().unmask();
			}
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
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
//		Highcharts.setOptions({
//	        global: {
//	            useUTC: false
//	        }
//	    });
	    var mychart = new Highcharts.stockChart({
	        chart: {
	            renderTo: divId,
	            type: 'spline',
	            shadow: false,
	            borderWidth: 0,
	            zooming: {
	                mouseWheel: {
	                    enabled: false // 禁用鼠标滚轮缩放
	                }
	            },
	            zoomType: 'xy'
	        },
	        time: {
	            timezoneOffset: new Date().getTimezoneOffset()   // 用户本地时区
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
	    			count: 24,
	    			type: 'hour',
	    			text: '24'+loginUserLanguageResource.hour
	    		}, {
	    			type: 'all',
	    			text: loginUserLanguageResource.all
	    		}],
	    		buttonTheme: {
	    	        width: getLabelWidth('24'+loginUserLanguageResource.hour,loginUserLanguage)  // 固定按钮宽度，完全掌控大小
	    	    },
//	    		buttonSpacing: 5, // 调整按钮之间的间距
	    		dropdown: 'responsive', // 空间不足时自动折叠为下拉菜单
	    		inputEnabled: false,
	    		selected: 0
	    	},
	        title: {
	            text: title,
	            style: {
	            	fontSize: chartTitleFontSize
	            }
	        },
	        subtitle: {
	            text: subtitle
	        },
	        colors: color,
//	        xAxis: {
//	            type: 'datetime',
//	            title: {
//	                text: xtitle
//	            },
////	            tickInterval: tickInterval,
////	            tickPixelInterval:tickInterval,
////	            tickPixelInterval:100,
//	            labels: {
//	                formatter: function () {
////	                    return Highcharts.dateFormat(timeFormat, this.value);
//	                	return this.axis.chart.time.dateFormat(timeFormat, this.value);
//	                },
//	                autoRotation:true,//自动旋转
//	                rotation: -45 //倾斜度，防止数量过多显示不全  
////	                step: 2
//	            }
//	        },
	        xAxis: {
	            type: 'datetime',
	            title: {
	                text: xtitle
	            },
	            // 核心配置：控制刻度密度
	            tickPixelInterval: 120,      // 每120像素一个刻度，值越小刻度越密
	            minTickInterval: 5 * 60 * 1000,  // 最小5分钟间隔，防止1小时视图下过密
	            labels: {
	                formatter: function () {
	                	var minTime = this.axis.min;
	                    var maxTime = this.axis.max;
	                    
	                    // 判断是否跨天：比较最小值和最大值的日期部分
	                    var minDate = new Date(minTime);
	                    var maxDate = new Date(maxTime);
	                    
	                    // 重置时间为0点进行比较
	                    minDate.setHours(0, 0, 0, 0);
	                    maxDate.setHours(0, 0, 0, 0);
	                    
	                    var isCrossDay = minDate.getTime() !== maxDate.getTime();
	                    
	                    if (isCrossDay) {
	                        // 跨天：显示月-日 时:分
	                        return this.axis.chart.time.dateFormat('%m-%d %H:%M', this.value);
	                    } else {
	                        // 没跨天：只显示时分
	                        return this.axis.chart.time.dateFormat('%H:%M', this.value);
	                    }
	                },
	                autoRotation: true,
	                rotation: -45,
	                style: {
//	                    fontSize: '11px'
	                }
	            }
	        },
	        yAxis: {
	        	max:maxValue,
	    		min:minValue,
	        	lineWidth: 1,
	        	tickWidth: 1,      // 刻度线宽度
                tickLength: 5,     // 刻度线长度（可选）
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
	            fallbackToExportServer: false,
	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
	    	    sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null,
	    	    		buttons: {
	    	    	    	contextButton: {
	    	    	    		menuItems: [
	    	    	    			'viewFullscreen',
	    	    	    			'printChart',
	    	    	    			'separator',
	    	    	    			'downloadPNG',
	    	    	    			'downloadJPEG',
	    	    	    			'downloadSVG',
	    	    	    			'separator',
	    	    	    			'downloadCSV',
	    	    	    			'downloadXLS'
	    	    	    			]
	    	    	    		}
	    	    	    }
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
	            borderWidth: 0,
	            itemHiddenStyle: {
	                textDecoration: 'none'
	            }
	        },
	        series: series
	    });
	}
};

function clearVideo(deviceType){
	var panelId1='RealTimeMonitoringRightVideoPanel1';
	var panelId2='RealTimeMonitoringRightVideoPanel2';
	if(videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus!=undefined && videoPlayrHelper.player1.pluginStatus.state.play){
		videoPlayrHelper.player1.stop();
	}
	if(videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus!=undefined && videoPlayrHelper.player2.pluginStatus.state.play){
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
			if(videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus!=undefined && videoPlayrHelper.player1.pluginStatus.state.play){
				videoPlayrHelper.player1.stop();
			}
			if(!videoPanel.isHidden() ){
				videoPanel.hide();
			}
		}else if(videoNo==2){
			if(videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus!=undefined && videoPlayrHelper.player2.pluginStatus.state.play){
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
				Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.ajaxError);
			},
			params: {
				videoKeyId: videoKeyId
	        }
		});
	}
}

function createVideo(deviceType,data,videoNo,isNew){
	if(videoConfigEnable){
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
}

function getDeviceRealTimeOverviewDataPage(deviceId, deviceType, limit) {
    return new Promise((resolve, reject) => {
        var dataPage = 1;
        var orgId = Ext.getCmp('leftOrg_Id').getValue();
        var deviceName = Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
        var FESdiagramResultStatValue = Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").getValue();
        var commStatusStatValue = Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").getValue();
        var runStatusStatValue = Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").getValue();
        var numStatusStatValue = Ext.getCmp("RealTimeMonitoringStatSelectNumStatus_Id").getValue();
        var deviceTypeStatValue = Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();

        Ext.Ajax.request({
            method: 'POST',
            url: context + '/realTimeMonitoringController/getDeviceRealTimeOverviewDataPage',
            params: {
                orgId: orgId,
                deviceType: deviceType,
                deviceId: deviceId,
                deviceName: deviceName,
                FESdiagramResultStatValue: FESdiagramResultStatValue,
                commStatusStatValue: commStatusStatValue,
                runStatusStatValue: runStatusStatValue,
                numStatusStatValue: numStatusStatValue,
                deviceTypeStatValue: deviceTypeStatValue,
                limit: limit
            },
            success: function(response) {
                var result = Ext.JSON.decode(response.responseText);
                var dataPage = result.dataPage || 1;   // 获取返回的分页数据
                resolve(dataPage);                      // 成功时 resolve
            },
            failure: function() {
                reject(new Error("请求失败"));          // 失败时 reject
            }
        });
    });
}

function getDeviceAddInfoAndControlInfo(deviceId, deviceType) {
    return new Promise((resolve, reject) => {
        var deviceInfo = {
            videoNum: 0,
            controlItemNum: 0,
            addInfoNum: 0,
            auxiliaryDeviceNum: 0
        };
        Ext.Ajax.request({
            method: 'POST',
            url: context + '/realTimeMonitoringController/getDeviceAddInfoAndControlInfo',
            success: function(response) {
                deviceInfo = Ext.JSON.decode(response.responseText);
                resolve(deviceInfo);
            },
            failure: function() {
                reject(new Error("获取设备附加信息和控制信息失败"));
            },
            params: {
                deviceType: deviceType,
                deviceId: deviceId
            }
        });
    });
}

function getRealtimeDefaultDeviceTabInstanceInfo(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDefaultActiveDeviceTypeTab();
	
}

function getRealtimeDefaultDeviceId(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=getDefaultActiveDeviceTypeTab();
	
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
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
};

function continueUpdateDeviceMonitoringData(deviceInfo, deviceId, deviceName, deviceType, record){
	
    var deviceTabInstanceInfo = getDeviceTabInstanceInfoByDeviceId(deviceId);
    var deviceTabInstanceConfig = deviceTabInstanceInfo.config;
    var calculateType = deviceTabInstanceInfo.calculateType == undefined ? 0 : deviceTabInstanceInfo.calculateType;

    var showRealtimeWellboreAnalysis = false;
    var showRealtimeSurfaceAnalysis = false;
    var showRealtimeTrendCurve = false;
    var showRealtimeDynamicData = false;
    var showRealtimeDeviceControl = false;
    var showRealtimeDeviceInformation = false;
    var rodStressChart_MaxRodStress = false;
    var rodStressChart_RodStressRange = false;

    if (deviceTabInstanceConfig != undefined && deviceTabInstanceConfig.DeviceRealTimeMonitoring != undefined) {
        showRealtimeWellboreAnalysis = deviceTabInstanceConfig.DeviceRealTimeMonitoring.WellboreAnalysis != undefined ? deviceTabInstanceConfig.DeviceRealTimeMonitoring.WellboreAnalysis : false;
        showRealtimeSurfaceAnalysis = deviceTabInstanceConfig.DeviceRealTimeMonitoring.SurfaceAnalysis != undefined ? deviceTabInstanceConfig.DeviceRealTimeMonitoring.SurfaceAnalysis : false;
        showRealtimeTrendCurve = deviceTabInstanceConfig.DeviceRealTimeMonitoring.TrendCurve != undefined ? deviceTabInstanceConfig.DeviceRealTimeMonitoring.TrendCurve : false;
        showRealtimeDynamicData = deviceTabInstanceConfig.DeviceRealTimeMonitoring.DynamicData != undefined ? deviceTabInstanceConfig.DeviceRealTimeMonitoring.DynamicData : false;
        showRealtimeDeviceControl = deviceTabInstanceConfig.DeviceRealTimeMonitoring.DeviceControl != undefined ? deviceTabInstanceConfig.DeviceRealTimeMonitoring.DeviceControl : false;
        showRealtimeDeviceInformation = deviceTabInstanceConfig.DeviceRealTimeMonitoring.DeviceInformation != undefined ? deviceTabInstanceConfig.DeviceRealTimeMonitoring.DeviceInformation : false;
        rodStressChart_MaxRodStress = deviceTabInstanceConfig.DeviceRealTimeMonitoring.RodStressChart_MaxRodStress != undefined ? deviceTabInstanceConfig.DeviceRealTimeMonitoring.RodStressChart_MaxRodStress : false;
        rodStressChart_RodStressRange = deviceTabInstanceConfig.DeviceRealTimeMonitoring.RodStressChart_RodStressRange != undefined ? deviceTabInstanceConfig.DeviceRealTimeMonitoring.RodStressChart_RodStressRange : false;
    }

    Ext.getCmp("rodStressChart_ShowMaxRodStress_Id").setValue(rodStressChart_MaxRodStress ? 1 : 0);
    Ext.getCmp("rodStressChart_ShowRodStressRange_Id").setValue(rodStressChart_RodStressRange ? 1 : 0);

    var combDeviceName = Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
    if (combDeviceName != undefined || combDeviceName != '') {
        Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
    }

    var tabPanel = Ext.getCmp("RealTimeMonitoringCurveAndTableTabPanel");
    var activeId = tabPanel.getActiveTab() != undefined ? tabPanel.getActiveTab().id : '';
    var tabChange = false;

    // 井筒分析标签处理
    if (showRealtimeWellboreAnalysis == false) {
        tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"));
        if (activeId == "RealTimeMonitoringFSDiagramAnalysisTabPanel_Id") {
            tabChange = true;
        }
    } else {
        var RealTimeMonitoringFSDiagramAnalysisTabPanel = tabPanel.getComponent("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
        if (calculateType == 1 && RealTimeMonitoringFSDiagramAnalysisTabPanel == undefined) {
            tabPanel.insert(0, realtimeCurveAndTableTabPanelItems[0]);
        } else if (calculateType != 1 && RealTimeMonitoringFSDiagramAnalysisTabPanel != undefined) {
            tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"));
            if (activeId == "RealTimeMonitoringFSDiagramAnalysisTabPanel_Id") {
                tabChange = true;
            }
        }
    }
    // 地面分析标签处理
    if (showRealtimeSurfaceAnalysis == false) {
        tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"));
        if (activeId == "RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id") {
            tabChange = true;
        }
    } else {
        var RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel = tabPanel.getComponent("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
        if (calculateType == 1 && RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel == undefined) {
            tabPanel.insert(1, realtimeCurveAndTableTabPanelItems[1]);
        } else if (calculateType != 1 && RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel != undefined) {
            tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"));
            if (activeId == "RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id") {
                tabChange = true;
            }
        }
    }
    // 趋势曲线标签处理
    if (showRealtimeTrendCurve == false) {
        tabPanel.remove(Ext.getCmp("RealTimeMonitoringCurveTabPanel_Id"));
        if (activeId == "RealTimeMonitoringCurveTabPanel_Id") {
            tabChange = true;
        }
    } else {
        var RealTimeMonitoringCurveTabPanel = tabPanel.getComponent("RealTimeMonitoringCurveTabPanel_Id");
        if (RealTimeMonitoringCurveTabPanel == undefined) {
            tabPanel.insert(2, realtimeCurveAndTableTabPanelItems[2]);
        }
    }
    // 动态数据标签处理
    if (showRealtimeDynamicData == false) {
        tabPanel.remove(Ext.getCmp("RealTimeMonitoringTableTabPanel_Id"));
        if (activeId == "RealTimeMonitoringTableTabPanel_Id") {
            tabChange = true;
        }
    } else {
        var RealTimeMonitoringTableTabPanel = tabPanel.getComponent("RealTimeMonitoringTableTabPanel_Id");
        if (RealTimeMonitoringTableTabPanel == undefined) {
            tabPanel.insert(3, realtimeCurveAndTableTabPanelItems[3]);
        }
    }

    var showDeviceDataTab = true;
    if (showRealtimeWellboreAnalysis == false && showRealtimeSurfaceAnalysis == false && showRealtimeTrendCurve == false && showRealtimeDynamicData == false) {
        showDeviceDataTab = false;
    }
    if (!showDeviceDataTab) {
        tabPanel.hide();
    } else {
        if (tabPanel.isHidden()) {
            tabPanel.show();
        }
        if (!tabChange) {
            if (tabPanel.getActiveTab() == undefined) {
                if (tabPanel.items.length > 0) {
                    tabPanel.setActiveTab(0);
                }
            } else {
                activeId = tabPanel.getActiveTab() != undefined ? tabPanel.getActiveTab().id : '';
                if (activeId == "RealTimeMonitoringCurveTabPanel_Id") {
                    deviceRealtimeMonitoringCurve(deviceType);
                } else if (activeId == "RealTimeMonitoringTableTabPanel_Id") {
                    CreateDeviceRealTimeMonitoringDataTable(deviceId, deviceName, deviceType, calculateType);
                } else {
                    if (calculateType == 1) {
                        Ext.create('AP.store.realTimeMonitoring.SingleFESDiagramDetailsChartsStore');
                    } else {
                        if (tabPanel.items.length > 0) {
                            tabPanel.setActiveTab(0);
                        }
                        tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
                        tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
                    }
                }
            }
        }
    }

    var controlTabChange = false;
    var showControlAndInformationTabPanel = true;
    if (deviceInfo.videoNum == 0) {
        cleanDeviceAddInfoAndControlInfo();
    }
    if (deviceInfo.videoNum == 0 && deviceInfo.controlItemNum == 0) {
        showRealtimeDeviceControl = false;
    }
    if (deviceInfo.addInfoNum == 0 && deviceInfo.auxiliaryDeviceNum == 0) {
        showRealtimeDeviceInformation = false;
    }
    if (showRealtimeDeviceControl == false && showRealtimeDeviceInformation == false) {
        showControlAndInformationTabPanel = false;
    }

    var rightTabPanel = Ext.getCmp("RealTimeMonitoringRightTabPanel");
    var rightTabPanelActiveTabId = rightTabPanel.getActiveTab() != undefined ? rightTabPanel.getActiveTab().id : '';
    // 控制标签处理
    if (showRealtimeDeviceControl == false) {
        rightTabPanel.remove(Ext.getCmp("RealTimeMonitoringRightControlAndVideoPanel"));
        if (rightTabPanelActiveTabId == "RealTimeMonitoringRightControlAndVideoPanel") {
            controlTabChange = true;
        }
    } else {
        var RealTimeMonitoringRightControlAndVideoPanel = rightTabPanel.getComponent("RealTimeMonitoringRightControlAndVideoPanel");
        if (RealTimeMonitoringRightControlAndVideoPanel == undefined) {
            rightTabPanel.insert(0, RealTimeMonitoringRightTabPanelItems[0]);
        }
    }
    // 设备信息标签处理
    if (showRealtimeDeviceInformation == false) {
        rightTabPanel.remove(Ext.getCmp("RealTimeMonitoringRightDeviceInfoPanel"));
        if (rightTabPanelActiveTabId == "RealTimeMonitoringRightDeviceInfoPanel") {
            controlTabChange = true;
        }
    } else {
        var RealTimeMonitoringRightDeviceInfoPanel = rightTabPanel.getComponent("RealTimeMonitoringRightDeviceInfoPanel");
        if (RealTimeMonitoringRightDeviceInfoPanel == undefined) {
            rightTabPanel.insert(1, RealTimeMonitoringRightTabPanelItems[1]);
        }
    }

    if (!showControlAndInformationTabPanel) {
        rightTabPanel.hide();
        Ext.getCmp("RealTimeMonitoringTabPanel").getEl().unmask();
        Ext.getCmp("RealTimeMonitoringInfoPanel_Id").getEl().unmask();
    } else {
        if (rightTabPanel.isHidden()) {
            rightTabPanel.show();
        }
        if (rightTabPanel.getActiveTab() == undefined) {
            rightTabPanel.setActiveTab(0);
        } else {
            if (!controlTabChange) {
                rightTabPanelActiveTabId = rightTabPanel.getActiveTab() != undefined ? rightTabPanel.getActiveTab().id : '';
                if (rightTabPanelActiveTabId == 'RealTimeMonitoringRightControlAndVideoPanel') {
                    if (deviceInfo.videoNum == 0 && deviceInfo.controlItemNum == 0) {
                        cleanDeviceAddInfoAndControlInfo();
                        rightTabPanel.setActiveTab("RealTimeMonitoringRightDeviceInfoPanel");
                        rightTabPanel.remove("RealTimeMonitoringRightControlAndVideoPanel");
                    } else {
                        createVideo(deviceType, record.data);
                        var controlGridPanel = Ext.getCmp("RealTimeMonitoringControlDataGridPanel_Id");
                        if (isNotVal(controlGridPanel)) {
                            controlGridPanel.getStore().load();
                        } else {
                            Ext.create('AP.store.realTimeMonitoring.RealTimeMonitoringDeviceControlStore');
                        }
                        if (deviceInfo.addInfoNum == 0 && deviceInfo.auxiliaryDeviceNum == 0) {
                            rightTabPanel.remove("RealTimeMonitoringRightDeviceInfoPanel");
                        }
                    }
                } else if (rightTabPanelActiveTabId == 'RealTimeMonitoringRightDeviceInfoPanel') {
                    if (deviceInfo.addInfoNum == 0 && deviceInfo.auxiliaryDeviceNum == 0) {
                        rightTabPanel.setActiveTab("RealTimeMonitoringRightControlAndVideoPanel");
                        rightTabPanel.remove("RealTimeMonitoringRightDeviceInfoPanel");
                    } else {
                        Ext.create('AP.store.realTimeMonitoring.RealTimeMonitoringAddInfoStore');
                        if (deviceInfo.videoNum == 0 && deviceInfo.controlItemNum == 0) {
                            cleanDeviceAddInfoAndControlInfo();
                            rightTabPanel.remove("RealTimeMonitoringRightControlAndVideoPanel");
                        }
                    }
                }
            }
        }
    }
}
var _updateDeviceMonitoringData_requestId = 0;
function updateDeviceMonitoringData(record) {
    var deviceType = getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
    var deviceName = '';
    var deviceId = 0;

    if (record != undefined && record.data != undefined) {
        deviceName = record.data.deviceName;
        deviceId = record.data.id;
    }
    
    // 生成当前请求的唯一ID，用于防止并发乱序
    var currentRequestId = ++_updateDeviceMonitoringData_requestId;

    // 异步获取 deviceInfo
    getDeviceAddInfoAndControlInfo(deviceId, deviceType)
        .then(function(deviceInfo) {
        	// 忽略过期响应（后发先至的旧请求）
            if (currentRequestId !== _updateDeviceMonitoringData_requestId) {
                return;
            }
            continueUpdateDeviceMonitoringData(deviceInfo, deviceId, deviceName, deviceType, record);
        })
        .catch(function(error) {
            console.error("获取设备附加信息失败", error);
         // 忽略过期响应
            if (currentRequestId !== _updateDeviceMonitoringData_requestId) {
                return;
            }
            // 失败时使用默认的 deviceInfo（所有数值为0）
            var defaultDeviceInfo = {
                videoNum: 0,
                controlItemNum: 0,
                addInfoNum: 0,
                auxiliaryDeviceNum: 0
            };
            continueUpdateDeviceMonitoringData(defaultDeviceInfo, deviceId, deviceName, deviceType, record);
        });
}

function getTotalTabsWidth(tabPanel) {
    // 获取 TabBar 组件
    var tabBar = tabPanel.getTabBar();
    if (!tabBar) return 0;
    
    // 获取 TabBar 的根 DOM 元素
    var tabBarEl = tabBar.getEl();
    // 查询所有标签头元素（最外层 .x-tab）
    var tabElements = tabBarEl.query('.x-tab');
    
    var totalWidth = 0;
    Ext.each(tabElements, function(tabEl) {
        // 使用原生 offsetWidth，包含边框和内边距
        totalWidth += tabEl.offsetWidth;
    });
    return totalWidth;
}