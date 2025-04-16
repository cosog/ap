var deviceRealTimeMonitoringDataHandsontableHelper=null;
var realtimeStatTabItems=[{
	title:loginUserLanguageResource.workType,
	layout: 'fit',
	closable:false,
	closeAction:'hide',
	id:'RealTimeMonitoringFESdiagramResultStatGraphPanel_Id',
	hidden: onlyMonitor,
	iconCls: onlyMonitor?null:'check3',
	html: '<div id="RealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if(isNotVal($("#RealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id"))){
        		if ($("#RealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id").highcharts() != undefined) {
        			highchartsResize("RealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id");
                }else{
                	var toolTip=Ext.getCmp("RealTimeMonitoringFESdiagramResultStatGraphPanelPieToolTip_Id");
                	if(!isNotVal(toolTip)){
                		Ext.create('Ext.tip.ToolTip', {
                            id:'RealTimeMonitoringFESdiagramResultStatGraphPanelPieToolTip_Id',
                    		target: 'RealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id',
                            html: loginUserLanguageResource.statPieChartToolTip
                        });
                	}
                }
        	}
        }
    }
},{
	title:loginUserLanguageResource.commStatus,
	layout: 'fit',
	closable:false,
	hidden: onlyFESDiagramCal,
	id:'RealTimeMonitoringStatGraphPanel_Id',
	html: '<div id="RealTimeMonitoringStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if(isNotVal($("#RealTimeMonitoringStatGraphPanelPieDiv_Id"))){
        		if ($("#RealTimeMonitoringStatGraphPanelPieDiv_Id").highcharts() != undefined) {
        			highchartsResize("RealTimeMonitoringStatGraphPanelPieDiv_Id");
        		}else{
                	var toolTip=Ext.getCmp("RealTimeMonitoringStatGraphPanelPieToolTip_Id");
                	if(!isNotVal(toolTip)){
                		Ext.create('Ext.tip.ToolTip', {
                            id:'RealTimeMonitoringStatGraphPanelPieToolTip_Id',
                    		target: 'RealTimeMonitoringStatGraphPanelPieDiv_Id',
                            html: loginUserLanguageResource.statPieChartToolTip
                        });
                	}
                }
        	}
        }
    }
},{
	title:loginUserLanguageResource.runStatus,
	layout: 'fit',
	closable:false,
	iconCls: onlyMonitor?'check3':null,
	id:'RealTimeMonitoringRunStatusStatGraphPanel_Id',
	html: '<div id="RealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if(isNotVal($("#RealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id"))){
        		if ($("#RealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id").highcharts() != undefined) {
        			highchartsResize("RealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id");
        		}else{
                	var toolTip=Ext.getCmp("RealTimeMonitoringRunStatusStatGraphPanelPieToolTip_Id");
                	if(!isNotVal(toolTip)){
                		Ext.create('Ext.tip.ToolTip', {
                            id:'RealTimeMonitoringRunStatusStatGraphPanelPieToolTip_Id',
                    		target: 'RealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id',
                            html: loginUserLanguageResource.statPieChartToolTip
                        });
                	}
                }
        	}
        }
    }
},{
	title:loginUserLanguageResource.deviceType,
	layout: 'fit',
	hidden: true,
	id:'RealTimeMonitoringDeviceTypeStatGraphPanel_Id',
	html: '<div id="RealTimeMonitoringDeviceTypeStatPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if(isNotVal($("#RealTimeMonitoringStatGraphPanelPieDiv_Id"))){
        		if ($("#RealTimeMonitoringDeviceTypeStatPieDiv_Id").highcharts() != undefined) {
        			highchartsResize("RealTimeMonitoringDeviceTypeStatPieDiv_Id");
        		}else{
                	var toolTip=Ext.getCmp("RealTimeMonitoringDeviceTypeStatPieToolTip_Id");
                	if(!isNotVal(toolTip)){
                		Ext.create('Ext.tip.ToolTip', {
                            id:'RealTimeMonitoringDeviceTypeStatPieToolTip_Id',
                    		target: 'RealTimeMonitoringDeviceTypeStatPieDiv_Id',
                            html: loginUserLanguageResource.statPieChartToolTip
                        });
                	}
                }
        	}
        }
    }
}];

var realtimeCurveAndTableTabPanelItems=[{
	title: loginUserLanguageResource.wellboreAnalysis,
	margin: '0 0 0 0',
    padding: 0,
    autoScroll:false,
    scrollable: false,
    id: 'RealTimeMonitoringFSDiagramAnalysisTabPanel_Id',
    hidden: onlyMonitor,
    iconCls: onlyMonitor?null:'check3',
    layout: {
        type: 'vbox',
        pack: 'start',
        align: 'stretch'
    },
    items: [{
    		border: false,
    		margin: '0 0 0 0',
    		flex: 1,
    		layout: {
    	        type: 'hbox',
    	        pack: 'start',
    	        align: 'stretch'
    	    },
    	    items:[{
    	    	border: true,
    	    	layout: 'fit',
    	    	margin: '0 0 0 0',
                flex: 1,
                align:'stretch',
                html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv1_id\" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").highcharts()!=undefined){
                    		highchartsResize("FSDiagramAnalysisSingleWellboreDetailsDiv1_id");
                    	}
                    }
                }
    	    },{
    	    	border: true,
    	    	layout: 'fit',
    	    	margin: '0 0 0 1',
                flex: 1,
                align:'stretch',
                html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv2_id\" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").highcharts()!=undefined){
                    		highchartsResize("FSDiagramAnalysisSingleWellboreDetailsDiv2_id");
                    	}
                    }
                }
    	    }]
    	},{
    		border: false,
    		flex: 1,
    		margin: '1 0 0 0',
    		layout: {
    	        type: 'hbox',
    	        pack: 'start',
    	        align: 'stretch'
    	    },
    	    items:[{
    	    	border: true,
    	    	layout: 'fit',
    	    	margin: '0 0 0 0',
                flex: 1,
                align:'stretch',
                html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv3_id\" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").highcharts()!=undefined){
                    		highchartsResize("FSDiagramAnalysisSingleWellboreDetailsDiv3_id");
                    	}
                    }
                }
    	    },{
    	    	border: true,
    	    	layout: 'fit',
    	    	margin: '0 0 0 1',
                flex: 1,
                align:'stretch',
                html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv4_id\" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").highcharts()!=undefined){
                    		highchartsResize("FSDiagramAnalysisSingleWellboreDetailsDiv4_id");
                    	}
                    }
                }
    	    }]
    	}]
},{
	title: loginUserLanguageResource.surfaceAnalysis,
	margin: '0 0 0 0',
    padding: 0,
    autoScroll:false,
    scrollable: false,
    id: 'RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id',
    hidden: onlyMonitor,
    layout: {
        type: 'vbox',
        pack: 'start',
        align: 'stretch'
    },
    items: [
    	{
    		border: false,
    		margin: '0 0 0 0',
    		flex: 1,
    		layout: {
    	        type: 'hbox',
    	        pack: 'start',
    	        align: 'stretch'
    	    },
    	    items:[{
    	    	border: true,
    	    	layout: 'fit',
    	    	margin: '0 0 0 0',
                flex: 1,
//                height:300,
                align:'stretch',
                html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv1_id\" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv1_id").highcharts()!=undefined){
                    		highchartsResize("FSDiagramAnalysisSingleSurfaceDetailsDiv1_id");
                    	}
                    }
                }
    	    },{
    	    	border: true,
    	    	layout: 'fit',
    	    	margin: '0 0 0 1',
                flex: 1,
//                height:300,
                align:'stretch',
                html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv2_id\" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv2_id").highcharts()!=undefined){
                    		highchartsResize("FSDiagramAnalysisSingleSurfaceDetailsDiv2_id");
                    	}
                    }
                }
    	    }]
    	},{
    		border: false,
    		flex: 1,
    		margin: '0 0 0 0',
    		layout: {
    	        type: 'hbox',
    	        pack: 'start',
    	        align: 'stretch'
    	    },
    	    items:[{
    	    	border: true,
    	    	layout: 'fit',
    	    	margin: '1 0 0 0',
                flex: 1,
//                height:300,
                align:'stretch',
                html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv3_id\" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv3_id").highcharts()!=undefined){
                    		highchartsResize("FSDiagramAnalysisSingleSurfaceDetailsDiv3_id");
                    	}
                    }
                }
    	    },{
    	    	border: true,
    	    	layout: 'fit',
    	    	margin: '1 0 0 1',
                flex: 1,
//                height:300,
                align:'stretch',
                html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv4_id\" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv4_id").highcharts()!=undefined){
                    		highchartsResize("FSDiagramAnalysisSingleSurfaceDetailsDiv4_id");
                    	}
                    }
                }
    	    }]
    	}
    ]
},{
	title:loginUserLanguageResource.trendCurve,
	id:"RealTimeMonitoringCurveTabPanel_Id",
	layout: 'border',
	iconCls: onlyMonitor?'check3':null,
	items: [{
		region: 'center',
		layout: 'fit',
		autoScroll: true,
		border: false,
		id:"realTimeMonitoringCurveContent",
		html: '<div id="realTimeMonitoringCurveContainer" class="hbox" style="width:100%;height:100%;"></div>',
		listeners: {
            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
            	var container=$('#realTimeMonitoringCurveContainer');
    			if(container!=undefined && container.length>0){
    				var containerChildren=container[0].children;
    				if(containerChildren!=undefined && containerChildren.length>0){
    					for(var i=0;i<containerChildren.length;i++){
    						var chart = $("#"+containerChildren[i].id).highcharts(); 
    						if(isNotVal(chart)){
    							highchartsResize(containerChildren[i].id);
    						}
    					}
    				}
    			}
            }
        }
	}]
},{
	title:loginUserLanguageResource.dynamicData,
	id:"RealTimeMonitoringTableTabPanel_Id",
	layout: 'border',
    border: false,
    items: [{
    	region: 'center',
    	header: false,
    	id: "RealTimeMonitoringInfoDataPanel_Id",
    	layout: 'fit',
    	html:'<div class="RealTimeMonitoringInfoDataTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="RealTimeMonitoringInfoDataTableInfoDiv_id"></div></div>',
    	listeners: {
            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
            	if(deviceRealTimeMonitoringDataHandsontableHelper!=null && deviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
//            		deviceRealTimeMonitoringDataHandsontableHelper.hot.refreshDimensions();
            		var newWidth=width;
            		var newHeight=height;
            		var header=thisPanel.getHeader();
            		if(header){
            			newHeight=newHeight-header.lastBox.height-2;
            		}
            		deviceRealTimeMonitoringDataHandsontableHelper.hot.updateSettings({
            			width:newWidth,
            			height:newHeight
            		});
            	}
            }
        }
    }]
}];

var RealTimeMonitoringRightTabPanelItems=[{
	title:loginUserLanguageResource.deviceControl,
	border: false,
	hidden: onlyFESDiagramCal,
	iconCls: onlyFESDiagramCal?null:'check3',
    layout: 'border',
    hideMode:'offsets',
    id:'RealTimeMonitoringRightControlAndVideoPanel',
    items: [{
    	region: 'north',
    	layout: 'fit',
    	height: 220,
    	id:'RealTimeMonitoringRightVideoPanel1',
    	hidden:true,
    	collapsible: true, // 是否折叠
    	header: false,
        split: true, // 竖折叠条
        autoRender:true,
        html: '<div id="RealTimeMonitoringRightVideoDiv1_Id" style="width:100%;height:100%;"></div>',
        listeners: {
        	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        		if(Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
        			if(videoPlayrHelper.player1!=null){
                		var isFullScreen = isBrowserFullScreen();
                		if(!isFullScreen){
            				var recordData=Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data;
                			createVideo(0,recordData,1,true);
            			}
            		}
        		}
        	}
        }
    },{
    	region: 'center',
    	layout: 'border',
    	items: [{
    		region: 'north',
        	layout: 'fit',
        	height: 220,
        	id:'RealTimeMonitoringRightVideoPanel2',
        	hidden:true,
        	collapsible: true, // 是否折叠
        	header: false,
            split: true, // 竖折叠条
            autoRender:true,
            html: '<div id="RealTimeMonitoringRightVideoDiv2_Id" style="width:100%;height:100%;"></div>',
            listeners: {
            	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
            		if(Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
            			if(videoPlayrHelper.player2!=null){
                    		var isFullScreen = isBrowserFullScreen();
                    		if(!isFullScreen){
                				var recordData=Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data;
                    			createVideo(0,recordData,2,true);
                			}
                		}
            		}
            	}
            }
    	},{
    		region: 'center',
            id: 'RealTimeMonitoringRightControlPanel',
            border: false,
            layout: 'fit',
            autoScroll: true,
            scrollable: true
    	}]
    }]
},{
	title:loginUserLanguageResource.deviceInformation,
	layout: 'border',
	id:'RealTimeMonitoringRightDeviceInfoPanel',
	iconCls: onlyFESDiagramCal?'check3':null,
	items:[{
		region: 'center',
		id: 'RealTimeMonitoringRightDeviceAddInfoPanel',
		title:loginUserLanguageResource.additionalInformation,
        border: false,
        layout: 'fit'
	},{
		region: 'south',
		id: 'RealTimeMonitoringRightAuxiliaryDeviceInfoPanel',
		title:loginUserLanguageResource.auxiliaryDevice,
		height: '50%',
		border: false,
        layout: 'fit',
        split: true,
        hidden: false,
        collapsible: true
	}]
}];


Ext.define("AP.view.realTimeMonitoring.RealTimeMonitoringInfoPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.realTimeMonitoringInfoPanel',
    id: 'RealTimeMonitoringInfoPanel_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var deviceCombStore = new Ext.data.JsonStore({
        	pageSize:defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
            	url: context + '/wellInformationManagerController/loadWellComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var deviceName = Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: getDeviceTypeFromTabId("RealTimeMonitoringTabPanel"),
                        deviceName: deviceName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var deviceCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: loginUserLanguageResource.deviceName,
                    id: "RealTimeMonitoringDeviceListComb_Id",
                    labelWidth: getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage),
                    width: (getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage)+110),
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: deviceCombStore,
                    autoSelect: false,
                    editable: true,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize:comboxPagingStatus,
                    minChars:0,
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    listeners: {
                        expand: function (sm, selections) {
                            deviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
                        	Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                });
        
        Ext.applyIf(me, {
            items: [{
                border: false,
                layout: 'border',
                items: [{
                    region: 'center',
                    layout: 'border',
                    id:'RealTimeMonitoringCenterPanel_Id',
                    items:[{
                    	region: 'center',
                    	title:loginUserLanguageResource.deviceOverview,
                    	id:'RealTimeMonitoringInfoDeviceListPanel_Id',
                        border: false,
                        layout: 'fit',
                        tbar:[{
                        	id: 'RealTimeMonitoringInfoDeviceListSelectRow_Id',
                        	xtype: 'textfield',
                            value: -1,
                            hidden: true
                         },{
                        	id: 'RealTimeMonitoringStatSelectFESdiagramResult_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                        	id: 'RealTimeMonitoringStatSelectCommStatus_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                        	id: 'RealTimeMonitoringStatSelectRunStatus_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                        	id: 'RealTimeMonitoringStatSelectDeviceType_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                             id: 'RealTimeMonitoringColumnStr_Id',
                             xtype: 'textfield',
                             value: '',
                             hidden: true
                         },{
                             xtype: 'button',
                             text: loginUserLanguageResource.refresh,
                             iconCls: 'note-refresh',
                             hidden:false,
                             handler: function (v, o) {
                            	 realTimeDataRefresh();
                    		}
                 		},'-',deviceCombo,'-', {
                             xtype: 'button',
                             text: loginUserLanguageResource.exportData,
                             iconCls: 'export',
                             hidden:false,
                             handler: function (v, o) {
                            	 var orgId = Ext.getCmp('leftOrg_Id').getValue();
                            	 var deviceName = Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
                            	 var FESdiagramResultStatValue=Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").getValue();
                             	 var commStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").getValue();
                             	 var runStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").getValue();
                             	 var deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
                            	 var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
                            	 var fileName=loginUserLanguageResource.realtimeMonitoringExpFileName;
                            	 var title=loginUserLanguageResource.realtimeMonitoringExpFileName;
                            	 var columnStr=Ext.getCmp("RealTimeMonitoringColumnStr_Id").getValue();
                            	 exportRealTimeMonitoringDataExcel(orgId,deviceType,deviceName,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,fileName,title,columnStr);
                             }
                         }, '->', {
                         	xtype: 'button',
                            text:loginUserLanguageResource.showHistory,
                            tooltip:loginUserLanguageResource.showHistoryBtnTooltip,
                            handler: function (v, o) {
                            	var selectRow= Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").getValue();
                        		var gridPanel=Ext.getCmp("RealTimeMonitoringListGridPanel_Id");
                        		if(isNotVal(gridPanel)){
                        			var record=gridPanel.getStore().getAt(selectRow);
                        			gotoDeviceHistory(record.data.wellName,0);
                        		}
                            }
                        }]
                    },{
                    	region: 'south',
                    	split: true,
                        collapsible: true,
                    	height: '50%',
                    	xtype: 'tabpanel',
                    	id:'RealTimeMonitoringStatTabPanel',
                    	activeTab: onlyMonitor?1:0,
                        header: false,
                        closable:true,
            			closeAction:'hide',
                		tabPosition: 'top',
                		items: realtimeStatTabItems,
                		listeners: {
                			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                				oldCard.setIconCls(null);
                				newCard.setIconCls('check3');
                			},
                			tabchange: function (tabPanel, newCard,oldCard, obj) {
            					if(newCard.id=="RealTimeMonitoringFESdiagramResultStatGraphPanel_Id"){
            						loadAndInitFESdiagramResultStat(true);
            					}else if(newCard.id=="RealTimeMonitoringStatGraphPanel_Id"){
            						loadAndInitCommStatusStat(true);
            					}else if(newCard.id=="RealTimeMonitoringRunStatusStatGraphPanel_Id"){
            						loadAndInitRunStatusStat(true);
            					}else if(newCard.id=="RealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
            						loadAndInitDeviceTypeStat(true);
            					}
            					Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').setValue('');
        						Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').setRawValue('');
        						refreshRealtimeDeviceListDataByPage(parseInt(Ext.getCmp("selectedDeviceId_global").getValue()),0,Ext.getCmp('RealTimeMonitoringListGridPanel_Id'),'AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore');
            				}
            			}
                    }]
                }, {
                	region: 'east',
                    width: '68%',
                    id:'RealTimeMonitoringEastPanel_Id',
                    autoScroll: false,
                    split: true,
                    collapsible: true,
                    layout: 'border',
                    header: false,
                    items:[{
                        region: 'center',
                        xtype: 'tabpanel',
                		id:"RealTimeMonitoringCurveAndTableTabPanel",
                		activeTab: onlyMonitor?2:0,
                		border: false,
                		tabPosition: 'top',
                		items: realtimeCurveAndTableTabPanelItems,
                		listeners: {
                			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                				oldCard.setIconCls(null);
                				newCard.setIconCls('check3');
                			},
                			tabchange: function (tabPanel, newCard,oldCard, obj) {
            					var selectRow= Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").getValue();
            					var gridPanel=Ext.getCmp("RealTimeMonitoringListGridPanel_Id");
            					if(isNotVal(gridPanel)&&selectRow>=0){
            						if(newCard.id=="RealTimeMonitoringCurveTabPanel_Id"){
            							deviceRealtimeMonitoringCurve(0);
                					}else if(newCard.id=="RealTimeMonitoringTableTabPanel_Id"){
                						var selectedItem=gridPanel.getStore().getAt(selectRow);
                            			CreateDeviceRealTimeMonitoringDataTable(selectedItem.data.id,selectedItem.data.wellName,getDeviceTypeFromTabId("RealTimeMonitoringTabPanel"),selectedItem.data.calculateType);
                					}else{
                						Ext.create('AP.store.realTimeMonitoring.SingleFESDiagramDetailsChartsStore');
                					}
            					}
            				}
                		}
                    },{
                    	region: 'east',
                    	width: '31%',
                    	xtype: 'tabpanel',
                    	id:"RealTimeMonitoringRightTabPanel",
                		activeTab: onlyFESDiagramCal?1:0,
                		border: false,
                		split: true,
                        collapsible: true,
                        header: false,
                		tabPosition: 'top',
                		items: RealTimeMonitoringRightTabPanelItems,
                		listeners: {
                			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                				oldCard.setIconCls(null);
                				newCard.setIconCls('check3');
                			},
                			tabchange: function (tabPanel, newCard, oldCard,obj) {
                        		if(newCard.id=="RealTimeMonitoringRightControlAndVideoPanel"){
                                	if(Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                		createVideo(0,Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data);
                                		var controlGridPanel=Ext.getCmp("RealTimeMonitoringControlDataGridPanel_Id");
                            			if(isNotVal(controlGridPanel)){
                            				controlGridPanel.getStore().load();
                            			}else{
                            				Ext.create('AP.store.realTimeMonitoring.RealTimeMonitoringDeviceControlStore');
                            			}
                                	}else{
                                		var videoPanel1=Ext.getCmp("RealTimeMonitoringRightVideoPanel1");
                                		var videoPanel2=Ext.getCmp("RealTimeMonitoringRightVideoPanel2");
                                		if(videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus.state.play){
                                			videoPlayrHelper.player1.stop();
                                		}
                                		if(videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus.state.play){
                                			videoPlayrHelper.player2.stop();
                                		}
                                		videoPanel1.hide();
                                		videoPanel2.hide();
                                		Ext.getCmp("RealTimeMonitoringRightControlPanel").removeAll();
                                	}
                        		}else{
                        			if(videoPlayrHelper.player1!=null && videoPlayrHelper.player1.pluginStatus.state.play){
                        				videoPlayrHelper.player1.stop();
                        			}
                        			if(videoPlayrHelper.player2!=null && videoPlayrHelper.player2.pluginStatus.state.play){
                        				videoPlayrHelper.player2.stop();
                        			}
                        			
                        			if(newCard.id=="RealTimeMonitoringRightDeviceInfoPanel"){
                        				if(Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                        					Ext.create('AP.store.realTimeMonitoring.RealTimeMonitoringAddInfoStore');
                        				}
                        			}
                        		}
                            }
                        }
                    }],
                    listeners: {
                        beforeCollapse: function (panel, eOpts) {
                        	var container=$('#realTimeMonitoringCurveContainer');
		        			if(container!=undefined && container.length>0){
		        				var containerChildren=container[0].children;
		        				if(containerChildren!=undefined && containerChildren.length>0){
		        					for(var i=0;i<containerChildren.length;i++){
		        						$("#"+containerChildren[i].id).hide(); 
		        					}
		        				}
		        			}
                        },
                        expand: function (panel, eOpts) {
                        	var container=$('#realTimeMonitoringCurveContainer');
		        			if(container!=undefined && container.length>0){
		        				var containerChildren=container[0].children;
		        				if(containerChildren!=undefined && containerChildren.length>0){
		        					for(var i=0;i<containerChildren.length;i++){
		        						$("#"+containerChildren[i].id).show(); 
		        					}
		        				}
		        			}
                        }
                    }
                }]
            }]
        });
        me.callParent(arguments);
    }
});

function CreateDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType,calculateType){
	if(deviceRealTimeMonitoringDataHandsontableHelper!=null){
		if(deviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
			deviceRealTimeMonitoringDataHandsontableHelper.hot.destroy();
		}
		deviceRealTimeMonitoringDataHandsontableHelper=null;
	}
	
	Ext.getCmp("RealTimeMonitoringInfoDataPanel_Id").el.mask(loginUserLanguageResource.loading).show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getDeviceRealTimeMonitoringData',
		success:function(response) {
			Ext.getCmp("RealTimeMonitoringInfoDataPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(deviceRealTimeMonitoringDataHandsontableHelper==null || deviceRealTimeMonitoringDataHandsontableHelper.hot==undefined){
				deviceRealTimeMonitoringDataHandsontableHelper = DeviceRealTimeMonitoringDataHandsontableHelper.createNew("RealTimeMonitoringInfoDataTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.variable+"']";
				var columns="[" 
						+"{data:'name1'}," 
						+"{data:'value1'}," 
						+"{data:'name2'},"
						+"{data:'value2'}," 
						+"{data:'name3'}," 
						+"{data:'value3'}" 
						+"]";
				deviceRealTimeMonitoringDataHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				deviceRealTimeMonitoringDataHandsontableHelper.columns=Ext.JSON.decode(columns);
				deviceRealTimeMonitoringDataHandsontableHelper.CellInfo=result.CellInfo;
				if(result.totalRoot.length==0){
					deviceRealTimeMonitoringDataHandsontableHelper.sourceData=[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
					deviceRealTimeMonitoringDataHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					deviceRealTimeMonitoringDataHandsontableHelper.sourceData=result.totalRoot;
					deviceRealTimeMonitoringDataHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				deviceRealTimeMonitoringDataHandsontableHelper.CellInfo=result.CellInfo;
				deviceRealTimeMonitoringDataHandsontableHelper.sourceData=result.totalRoot;
				deviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(result.totalRoot);
			}
			//添加单元格属性
			for(var i=0;i<deviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length;i++){
				var row=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
				var col=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col;
				var column=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].column;
				var columnDataType=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].columnDataType;
				deviceRealTimeMonitoringDataHandsontableHelper.hot.setCellMeta(row,col,'columnDataType',columnDataType);
			}
			
		},
		failure:function(){
			Ext.getCmp("RealTimeMonitoringInfoDataPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			deviceId:deviceId,
			deviceName:deviceName,
			deviceType:deviceType,
			calculateType:calculateType
        }
	});
};

var DeviceRealTimeMonitoringDataHandsontableHelper = {
		createNew: function (divid) {
	        var deviceRealTimeMonitoringDataHandsontableHelper = {};
	        deviceRealTimeMonitoringDataHandsontableHelper.divid = divid;
	        deviceRealTimeMonitoringDataHandsontableHelper.validresult=true;//数据校验
	        deviceRealTimeMonitoringDataHandsontableHelper.colHeaders=[];
	        deviceRealTimeMonitoringDataHandsontableHelper.columns=[];
	        deviceRealTimeMonitoringDataHandsontableHelper.CellInfo=[];
	        deviceRealTimeMonitoringDataHandsontableHelper.sourceData=[];
	        
	        deviceRealTimeMonitoringDataHandsontableHelper.addItenmNameColStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.fontWeight = 'bold';
	        }
	        
	        deviceRealTimeMonitoringDataHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
//		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        deviceRealTimeMonitoringDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	            var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue()); 
	            if (row ==0) {
	            	Handsontable.renderers.TextRenderer.apply(this, arguments);
//		        	td.style.fontWeight = 'bold';
			        td.style.fontSize = '20px';
//			        td.style.fontFamily = 'SimSun';
			        td.style.height = '40px';
	            }
	            if (row%2==1&&row>0) {
	            	td.style.backgroundColor = '#f5f5f5';
                }
	            if (col%2==0) {
//	            	td.style.fontWeight = 'bold';
                }else{
//                	td.style.fontFamily = 'SimHei';
                }
	            
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
            	
//	            if(row>0){
//	            	td.style.textAlign='left';//内容左对齐
//	            }
	            for(var i=0;i<deviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length;i++){
                	if( isNotVal(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].realtimeColor) ){
                		var row2=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col*2;
        				if(row==row2 && col==col2 ){
        					td.style.color='#'+deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].realtimeColor;
        				}
                	}
                	
                	if( isNotVal(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].realtimeBgColor) ){
                		var row2=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col*2;
        				if(row==row2 && col==col2 ){
        					td.style.backgroundColor = '#' + deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].realtimeBgColor;
        				}
                	}
	            	
	            	if(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel>=0){
                		var row2=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
        				var col2=deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col*2+1;
        				if(row==row2 && col==col2 ){
        					if(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel>0){
        						td.style.fontWeight = 'bold';
//       			             	td.style.fontFamily = 'SimHei';
        					}
   			             	if(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==0){
   			             		if(AlarmShowStyle.Data.Normal.Opacity!=0){
   			             			td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.Normal.BackgroundColor,AlarmShowStyle.Data.Normal.Opacity);
   			             		}
   			             		td.style.color='#'+AlarmShowStyle.Data.Normal.Color;
   			             	}else if(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==100){
        						if(AlarmShowStyle.Data.FirstLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.FirstLevel.BackgroundColor,AlarmShowStyle.Data.FirstLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.FirstLevel.Color;
        					}else if(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==200){
        						if(AlarmShowStyle.Data.SecondLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.SecondLevel.BackgroundColor,AlarmShowStyle.Data.SecondLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.SecondLevel.Color;
        					}else if(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==300){
        						if(AlarmShowStyle.Data.ThirdLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.ThirdLevel.BackgroundColor,AlarmShowStyle.Data.ThirdLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.ThirdLevel.Color;
        					}
        				}
                	}
    			}
	        }
	        
	        deviceRealTimeMonitoringDataHandsontableHelper.createTable = function (data) {
	        	$('#'+deviceRealTimeMonitoringDataHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+deviceRealTimeMonitoringDataHandsontableHelper.divid);
	        	deviceRealTimeMonitoringDataHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [30,20,30,20,30,20],
	                columns:deviceRealTimeMonitoringDataHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: false,//显示行头
	                colHeaders: false,
	                autoWrapRow: false, //自动换行
	                rowHeights: [40],
	                columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: [{
                        "row": 0,
                        "col": 0,
                        "rowspan": 1,
                        "colspan": 6
                    }],
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.renderer = deviceRealTimeMonitoringDataHandsontableHelper.addCellStyle;
	                    
	                    cellProperties.readOnly = true;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(deviceRealTimeMonitoringDataHandsontableHelper!=null&&deviceRealTimeMonitoringDataHandsontableHelper.hot!=''&&deviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined && deviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=deviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        return deviceRealTimeMonitoringDataHandsontableHelper;
	    }
};
function realTimeMonitoringCurve(item){
	var gridPanel=Ext.getCmp("RealTimeMonitoringListGridPanel_Id")
	var deviceName="";
	if(isNotVal(gridPanel)){
		deviceName=gridPanel.getSelectionModel().getSelection()[0].data.wellName;
		
		Ext.Ajax.request({
			method:'POST',
			url:context + '/realTimeMonitoringController/getRealTimeCurveData',
			success:function(response) {
				var result =  Ext.JSON.decode(response.responseText);
			    var data = result.list;
			    var tickInterval = 1;
			    tickInterval = Math.floor(data.length / 2) + 1;
			    if(tickInterval<100){
			    	tickInterval=100;
			    }
//			    tickInterval=1000;
//			    if(){
//			    	
//			    }
			    var title = result.deviceName  + result.item + loginUserLanguageResource.curve;
			    var xTitle=loginUserLanguageResource.acqTime;
			    var yTitle=result.item;
			    if(isNotVal(result.unit)){
			    	yTitle+='('+result.unit+')';
			    }
			    var legendName = [result.item];
			    var series = "[";
			    for (var i = 0; i < legendName.length; i++) {
			        series += "{\"name\":\"" + legendName[i] + "\",";
			        series += "\"data\":[";
			        for (var j = 0; j < data.length; j++) {
			            if (i == 0) {
			            	series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + data[j].value + "]";
			            }else if(i == 1){
			            	series += "[" + Date.parse(data[j].acqTime.replace(/-/g, '/')) + "," + data[j].value2 + "]";
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
			    initTimeAndDataCurveChartFn(ser, tickInterval, "realTimeMonitoringCurveDiv_Id", title, '', xTitle, yTitle, color,false,'%H:%M:%S');
			},
			failure:function(){
				Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
			},
			params: {
				deviceName:deviceName,
				item:item,
				deviceType:getDeviceTypeFromTabId("RealTimeMonitoringTabPanel")
	        }
		});
	}
}

function renderControlBtn(btn,btnIndex){
	var record = btn.up().getWidgetRecord();
	
	var item = record.data.item;
    var itemcode = record.data.itemcode;
    var isControl = record.data.isControl;
    var commStatus = record.data.commStatus;
    var resolutionMode = record.data.resolutionMode;
    var itemMeaning = record.data.itemMeaning;
	
    var text = "";
    var hand = false;
    if (commStatus > 0 && isControl == 1) {
        hand = false;
    } else {
        hand = true;
    }
    text = loginUserLanguageResource.set;
	
    if(resolutionMode == 1 && itemMeaning.length == 1) {
    	if(btnIndex==0){
    		btn.setText(itemMeaning[0][1]);
        	btn.setTooltip(itemMeaning[0][1]);
        	btn.setWidth(82);
    	}else if(btnIndex==1){
    		btn.setHidden(true);
//    		btn.up().items.items.splic(1,0);
    		
    	}
    }else if(resolutionMode == 1 && itemMeaning.length == 2){
    	if(btnIndex==0){
    		btn.setText(itemMeaning[0][1]);
        	btn.setTooltip(itemMeaning[0][1]);
    	}else if(btnIndex==1){
    		btn.setText(itemMeaning[1][1]);
        	btn.setTooltip(itemMeaning[1][1]);
    	}
    }else if(resolutionMode == 0 && itemMeaning.length > 0){
    	if(btnIndex==0){
    		btn.setText(loginUserLanguageResource.switchingOpenValue);
        	btn.setTooltip(loginUserLanguageResource.switchingOpenValue);
    	}else if(btnIndex==1){
    		btn.setText(loginUserLanguageResource.switchingCloseValue);
        	btn.setTooltip(loginUserLanguageResource.switchingCloseValue);
    	}
    }else{
    	if(btnIndex==0){
    		btn.setText(text);
        	btn.setTooltip(text);
        	btn.setWidth(82);
    	}else if(btnIndex==1){
    		btn.setHidden(true);
    	}
    }
    btn.setDisabled(hand);
//    btn.setDisabled(false);
}

function controlBtnHandler(btn,btnIndex){
	var selection=Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection();
	if(selection.length>0){
		var record = btn.up().getWidgetRecord();
		
		var text=btn.getText();
		var deviceName = record.data.deviceName;
	    var deviceId = record.data.deviceId;
		
		var item = record.data.item;
	    var itemcode = record.data.itemcode;
	    var isControl = record.data.isControl;
	    var commStatus = record.data.commStatus;
	    var resolutionMode = record.data.resolutionMode;
	    var itemMeaning = record.data.itemMeaning;
		
	    var all_loading = new Ext.LoadMask({msg: loginUserLanguageResource.commandSending+'...',target: Ext.getBody().component});
	    
	    if(resolutionMode == 1 && itemMeaning.length == 1) {
	    	if(btnIndex==0){
                all_loading.show();
                Ext.Ajax.request({
                    url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
                    method: "POST",
                    params: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        controlType: itemcode,
                        controlValue: itemMeaning[0][0]
                    },
                    success: function (response, action) {
                        all_loading.hide();
                        var data = Ext.decode(response.responseText);
                        if (data.success == true && data.flag == false) {
                            Ext.MessageBox.show({
                                title: loginUserLanguageResource.tip,
                                msg: "<font color=red>" + loginUserLanguageResource.sessionInvalid + "</font>",
                                icon: Ext.MessageBox.INFO,
                                buttons: Ext.Msg.OK,
                                fn: function () {
                                    window.location.href = context + "/login";
                                }
                            });
                        } else if (data.flag == true && data.error == false) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        } else if (data.flag == true && data.error == true) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        }
                    },
                    failure: function () {
                        all_loading.hide();
                        Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
                    }
                });
            }else if(btnIndex==1){
	    		
	    	}
	    }else if(resolutionMode == 1 && itemMeaning.length == 2){
	    	if(btnIndex==0){
                all_loading.show();
                Ext.Ajax.request({
                    url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
                    method: "POST",
                    params: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        controlType: itemcode,
                        controlValue: itemMeaning[0][0]
                    },
                    success: function (response, action) {
                        all_loading.hide();
                        var data = Ext.decode(response.responseText);
                        if (data.success == true && data.flag == false) {
                            Ext.MessageBox.show({
                                title: loginUserLanguageResource.tip,
                                msg: "<font color=red>" + loginUserLanguageResource.sessionInvalid + "</font>",
                                icon: Ext.MessageBox.INFO,
                                buttons: Ext.Msg.OK,
                                fn: function () {
                                    window.location.href = context + "/login";
                                }
                            });
                        } else if (data.flag == true && data.error == false) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        } else if (data.flag == true && data.error == true) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        }
                    },
                    failure: function () {
                        all_loading.hide();
                        Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
                    }
                });
            }else if(btnIndex==1){
                all_loading.show();
                Ext.Ajax.request({
                    url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
                    method: "POST",
                    params: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        controlType: itemcode,
                        controlValue: itemMeaning[1][0]
                    },
                    success: function (response, action) {
                        all_loading.hide();
                        var data = Ext.decode(response.responseText);
                        if (data.success == true && data.flag == false) {
                            Ext.MessageBox.show({
                                title: loginUserLanguageResource.tip,
                                msg: "<font color=red>" + loginUserLanguageResource.sessionInvalid + "</font>",
                                icon: Ext.MessageBox.INFO,
                                buttons: Ext.Msg.OK,
                                fn: function () {
                                    window.location.href = context + "/login";
                                }
                            });
                        } else if (data.flag == true && data.error == false) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        } else if (data.flag == true && data.error == true) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        }
                    },
                    failure: function () {
                        all_loading.hide();
                        Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
                    }
                });
            }
	    }else if(resolutionMode == 0 && itemMeaning.length > 0){
	    	if(btnIndex==0){
                all_loading.show();
                Ext.Ajax.request({
                    url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
                    method: "POST",
                    params: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        controlType: itemcode,
                        controlValue: 1
                    },
                    success: function (response, action) {
                        all_loading.hide();
                        var data = Ext.decode(response.responseText);
                        if (data.success == true && data.flag == false) {
                            Ext.MessageBox.show({
                                title: loginUserLanguageResource.tip,
                                msg: "<font color=red>" + loginUserLanguageResource.sessionInvalid + "</font>",
                                icon: Ext.MessageBox.INFO,
                                buttons: Ext.Msg.OK,
                                fn: function () {
                                    window.location.href = context + "/login";
                                }
                            });
                        } else if (data.flag == true && data.error == false) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        } else if (data.flag == true && data.error == true) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        }
                    },
                    failure: function () {
                        all_loading.hide();
                        Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
                    }
                });
            }else if(btnIndex==1){
                all_loading.show();
                Ext.Ajax.request({
                    url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
                    method: "POST",
                    params: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        controlType: itemcode,
                        controlValue: 0
                    },
                    success: function (response, action) {
                        all_loading.hide();
                        var data = Ext.decode(response.responseText);
                        if (data.success == true && data.flag == false) {
                            Ext.MessageBox.show({
                                title: loginUserLanguageResource.tip,
                                msg: "<font color=red>" + loginUserLanguageResource.sessionInvalid + "</font>",
                                icon: Ext.MessageBox.INFO,
                                buttons: Ext.Msg.OK,
                                fn: function () {
                                    window.location.href = context + "/login";
                                }
                            });
                        } else if (data.flag == true && data.error == false) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        } else if (data.flag == true && data.error == true) {
                            Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + data.msg + "</font>");
                        }
                    },
                    failure: function () {
                        all_loading.hide();
                        Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font>】:" + loginUserLanguageResource.contactAdmin)
                    }
                });
            }
	    }else{
	    	if(btnIndex==0){
                Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
                Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;

                var win_Obj = Ext.getCmp("DeviceControlCheckPassWindow_Id")
                if (win_Obj != undefined) {
                    win_Obj.destroy();
                }
                var DeviceControlCheckPassWindow = Ext.create("AP.view.realTimeMonitoring.DeviceControlCheckPassWindow", {
                    title: loginUserLanguageResource.deviceControl
                });
                var resolutionModeName='';
                if(resolutionMode==0){
                	resolutionModeName=loginUserLanguageResource.switchingValue;
                }else if(resolutionMode==1){
                	resolutionModeName=loginUserLanguageResource.enumValue;
                }else if(resolutionMode==2){
                	resolutionModeName=loginUserLanguageResource.numericValue;
                }
                var showInfo=loginUserLanguageResource.name+':<font color=red>'+record.data.itemName+'</font>'
                	+','+loginUserLanguageResource.storeDataType+':<font color=red>'+record.data.storeDataType+'</font>'
                	+','+loginUserLanguageResource.quantity+':<font color=red>'+record.data.quantity+'</font>';
                if(isNotVal(resolutionModeName)){
                	showInfo+=','+loginUserLanguageResource.resolutionMode+':<font color=red>'+resolutionModeName+'</font>'
                }
                showInfo+=","+loginUserLanguageResource.unit+":<font color=red>"+(isNotVal(record.data.unit)?record.data.unit:"无")+'</font>'
                
                Ext.getCmp("DeviceControlItemName_Id").setHtml(showInfo);
                
                
                Ext.getCmp("DeviceControlDeviceName_Id").setValue(deviceName);
                Ext.getCmp("DeviceControlDeviceId_Id").setValue(deviceId);
                Ext.getCmp("DeviceControlDeviceType_Id").setValue(getDeviceTypeFromTabId("RealTimeMonitoringTabPanel"));

                Ext.getCmp("DeviceControlType_Id").setValue(record.data.itemcode);
                Ext.getCmp("DeviceControlShowType_Id").setValue(resolutionMode);
                Ext.getCmp("DeviceControlStoreDataType_Id").setValue(record.data.storeDataType);
                Ext.getCmp("DeviceControlQuantity_Id").setValue(record.data.quantity);

                Ext.getCmp("DeviceControlShowType_Id").setValue(2);
                if (resolutionMode == 1 && itemMeaning.length > 0) {
                    Ext.getCmp("DeviceControlShowType_Id").setValue(resolutionMode);
                    Ext.getCmp("DeviceControlItemMeaning_Id").setValue(JSON.stringify(itemMeaning));
                    var data = [];
                    for (var k = 0; k < itemMeaning.length; k++) {
                        data.push(itemMeaning[k]);
                    }
                    var controlTypeStore = new Ext.data.SimpleStore({
                        autoLoad: false,
                        fields: ['boxkey', 'boxval'],
                        data: data
                    });
                }
                DeviceControlCheckPassWindow.show();
            }else if(btnIndex==1){
	    		
	    	}
	    }
	}
}