var historyStatTabItems=[{
	title:loginUserLanguageResource.workType,
	layout: 'fit',
	id:'HistoryQueryFESdiagramResultStatGraphPanel_Id',
	hidden: onlyMonitor,
	iconCls: onlyMonitor?null:'check3',
	html: '<div id="HistoryQueryFESdiagramResultStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if ($("#HistoryQueryFESdiagramResultStatGraphPanelPieDiv_Id").highcharts() != undefined) {
        		highchartsResize("HistoryQueryFESdiagramResultStatGraphPanelPieDiv_Id");
        	}else{
            	var toolTip=Ext.getCmp("HistoryQueryFESdiagramResultStatGraphPanelPieToolTip_Id");
            	if(!isNotVal(toolTip)){
            		Ext.create('Ext.tip.ToolTip', {
                        id:'HistoryQueryFESdiagramResultStatGraphPanelPieToolTip_Id',
                		target: 'HistoryQueryFESdiagramResultStatGraphPanelPieDiv_Id',
                        html: loginUserLanguageResource.statPieChartToolTip
                    });
            	}
            }
        }
    }
},{
	title:loginUserLanguageResource.commStatus,
	layout: 'fit',
	hidden: onlyFESDiagramCal,
	id:'HistoryQueryStatGraphPanel_Id',
	html: '<div id="HistoryQueryStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if ($("#HistoryQueryStatGraphPanelPieDiv_Id").highcharts() != undefined) {
        		highchartsResize("HistoryQueryStatGraphPanelPieDiv_Id");
        	}else{
            	var toolTip=Ext.getCmp("HistoryQueryStatGraphPanelPieToolTip_Id");
            	if(!isNotVal(toolTip)){
            		Ext.create('Ext.tip.ToolTip', {
                        id:'HistoryQueryStatGraphPanelPieToolTip_Id',
                		target: 'HistoryQueryStatGraphPanelPieDiv_Id',
                        html: loginUserLanguageResource.statPieChartToolTip
                    });
            	}
            }
        }
    }
},{
	title:loginUserLanguageResource.runStatus,
	layout: 'fit',
	id:'HistoryQueryRunStatusStatGraphPanel_Id',
	iconCls: onlyMonitor?'check3':null,
	html: '<div id="HistoryQueryRunStatusStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if ($("#HistoryQueryRunStatusStatGraphPanelPieDiv_Id").highcharts() != undefined) {
        		highchartsResize("HistoryQueryRunStatusStatGraphPanelPieDiv_Id");
        	}else{
            	var toolTip=Ext.getCmp("HistoryQueryRunStatusStatGraphPanelPieToolTip_Id");
            	if(!isNotVal(toolTip)){
            		Ext.create('Ext.tip.ToolTip', {
                        id:'HistoryQueryRunStatusStatGraphPanelPieToolTip_Id',
                		target: 'HistoryQueryRunStatusStatGraphPanelPieDiv_Id',
                        html: loginUserLanguageResource.statPieChartToolTip
                    });
            	}
            }
        }
    }
},{
	title:loginUserLanguageResource.deviceType,
	layout: 'fit',
	hidden:true,
	id:'HistoryQueryDeviceTypeStatGraphPanel_Id',
	html: '<div id="HistoryQueryDeviceTypeStatPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if ($("#HistoryQueryDeviceTypeStatPieDiv_Id").highcharts() != undefined) {
        		highchartsResize("HistoryQueryDeviceTypeStatPieDiv_Id");
        	}else{
            	var toolTip=Ext.getCmp("HistoryQueryDeviceTypeStatPieToolTip_Id");
            	if(!isNotVal(toolTip)){
            		Ext.create('Ext.tip.ToolTip', {
                        id:'HistoryQueryDeviceTypeStatPieToolTip_Id',
                		target: 'HistoryQueryDeviceTypeStatPieDiv_Id',
                        html: loginUserLanguageResource.statPieChartToolTip
                    });
            	}
            }
        }
    }
}];

var historyQueryCenterTabPanelItems=[{
	title: loginUserLanguageResource.trendCurve,
	id:"HistoryDataTabPanel",
    layout: 'border',
    border: false,
    iconCls: 'check3',
    items: [{
    	region: 'north',
    	height: '50%',
    	title: loginUserLanguageResource.historyCurve,
    	layout: 'fit',
    	header: false,
    	border: true,
    	split: true,
        collapsible: true,
        id:'historyQueryCurvePanel_Id',
        html: '<div id="historyQueryCurveDiv_Id" style="width:100%;height:100%;"></div>',
        listeners: {
            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                if ($("#historyQueryCurveDiv_Id").highcharts() != undefined) {
                	highchartsResize("historyQueryCurveDiv_Id");
                }
            }
        }
    },{
    	region: 'center',
    	title: loginUserLanguageResource.historyData,
    	header: false,
    	id: "HistoryQueryDataInfoPanel_Id",
    	layout: 'fit',
    	border: true
    }]
},{
	title: loginUserLanguageResource.tiledDiagram,
	id:"HistoryDiagramTabPanel",
	hidden:onlyMonitor,
	xtype: 'tabpanel',
	activeTab: 0,
    border: false,
    tabPosition: 'left',
    items: [{
    	title:loginUserLanguageResource.FSDiagram,
    	id:'FSDiagramTiledTabPanel_Id',
    	layout: "fit",
        autoScroll: true,
        iconCls: 'check3',
        html: '<div id="surfaceCardContainer" class="hbox" style="width:100%;height:100%;"></div>',
        listeners: {
        	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
            	var container=$('#surfaceCardContainer');
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
            },
            render: function (p, o, i, c) {
                p.body.on('scroll', function () {
                    var totalPages = Ext.getCmp("SurfaceCardTotalPages_Id").getValue(); // 总页数
                    if (diagramPage < totalPages) {
                        var HistoryDiagramTabPanel = Ext.getCmp("FSDiagramTiledTabPanel_Id");
                        var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("surfaceCardContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                        if (hRatio > 0.5) {
                            diagramPage++;
                            loadHistoryDiagramTiledList(diagramPage);
                        }
                    }
                }, this);
            }
        }
    },{
    	title:loginUserLanguageResource.PSDiagram,
    	id:'PSDiagramTiledTabPanel_Id',
    	layout: "fit",
        autoScroll: true,
        html: '<div id="PSDiagramTiledContainer" class="hbox" style="width:100%;height:100%;"></div>',
        listeners: {
        	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
            	var container=$('#PSDiagramTiledContainer');
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
            },
            render: function (p, o, i, c) {
                p.body.on('scroll', function () {
                    var totalPages = Ext.getCmp("SurfaceCardTotalPages_Id").getValue(); // 总页数
                    if (diagramPage < totalPages) {
                        var HistoryDiagramTabPanel = Ext.getCmp("PSDiagramTiledTabPanel_Id");
                        var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("PSDiagramTiledContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                        if (hRatio > 0.5) {
                            diagramPage++;
                            loadHistoryDiagramTiledList(diagramPage);
                        }
                    }
                }, this);
            }
        }
    },{
    	title:loginUserLanguageResource.ISDiagram,
    	id:'ISDiagramTiledTabPanel_Id',
    	layout: "fit",
        autoScroll: true,
        html: '<div id="ISDiagramTiledContainer" class="hbox" style="width:100%;height:100%;"></div>',
        listeners: {
        	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
            	var container=$('#ISDiagramTiledContainer');
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
            },
            render: function (p, o, i, c) {
                p.body.on('scroll', function () {
                    var totalPages = Ext.getCmp("SurfaceCardTotalPages_Id").getValue(); // 总页数
                    if (diagramPage < totalPages) {
                        var HistoryDiagramTabPanel = Ext.getCmp("ISDiagramTiledTabPanel_Id");
                        var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("ISDiagramTiledContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                        if (hRatio > 0.5) {
                            diagramPage++;
                            loadHistoryDiagramTiledList(diagramPage);
                        }
                    }
                }, this);
            }
        }
    }],
    listeners: {
    	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
			oldCard.setIconCls(null);
			newCard.setIconCls('check3');
		},
		tabchange: function (tabPanel, newCard,oldCard, obj) {
    		if(newCard.id=="FSDiagramTiledTabPanel_Id"){
    			
    		}else if(newCard.id=="PSDiagramTiledTabPanel_Id"){
    			
    		}else if(newCard.id=="ISDiagramTiledTabPanel_Id"){
    			
    		}
    		loadHistoryDiagramTiledList(1);
    	}
    }
},{
	title: loginUserLanguageResource.diagramOverlay,
	id:"HistoryDiagramOverlayTabPanel",
	hidden:onlyMonitor,
	layout: 'border',
    items: [
        {
        	region: 'west',
        	width:'50%',
        	border: false,
            header: false,
            margin: '0 0 0 0',
            flex: 1,
            height: 900,
            autoScroll: true,
            scrollable: true,
            split: true,
            collapsible: true,
            collapseDirection:'left',
            layout: {
                type: 'vbox',
                pack: 'start',
                align: 'stretch'
            },
            items: [{
                border: false,
                margin: '0 0 1 0',
                height: 300,
                align:'stretch',
                layout: 'fit',
                id: 'HistoryQueryFESDiagramOverlayPanel_Id',
                html: '<div id="HistoryQueryOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        if ($("#HistoryQueryOverlayDiv_Id").highcharts() != undefined && $("#HistoryQueryOverlayDiv_Id").highcharts() != null) {
                        	highchartsResize("HistoryQueryOverlayDiv_Id");
                        }
                    }
                }
         }, {
                border: false,
                margin: '0 0 1 0',
                height: 300,
                align:'stretch',
                layout: 'fit',
                id: 'HistoryQueryPowerOverlayPanel_Id',
                html: '<div id="HistoryQueryPowerOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        if ($("#HistoryQueryPowerOverlayDiv_Id").highcharts() != undefined && $("#HistoryQueryPowerOverlayDiv_Id").highcharts() != null) {
                        	highchartsResize("HistoryQueryPowerOverlayDiv_Id");
                        }
                    }
                }
         }, {
                border: false,
                margin: '0 0 0 0',
                height: 300,
                align:'stretch',
                layout: 'fit',
                id: 'HistoryQueryCurrentOverlayPanel_Id',
                html: '<div id="HistoryQueryCurrentOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        if ($("#HistoryQueryCurrentOverlayDiv_Id").highcharts() != undefined && $("#HistoryQueryCurrentOverlayDiv_Id").highcharts() != null) {
                        	highchartsResize("HistoryQueryCurrentOverlayDiv_Id");
                        }
                    }
                }
         }]
     },{
    	 region: 'center',
    	 border: false,
    	 header: false,
         flex: 1,
         autoScroll: true,
         scrollable: true,
         split: true,
         layout: 'fit',
         id: 'HistoryQueryFSdiagramOverlayTable_Id'
     }]
}];
Ext.define("AP.view.historyQuery.HistoryQueryInfoPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.historyQueryInfoPanel',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var deviceListCombStore = new Ext.data.JsonStore({
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
                    var deviceName = Ext.getCmp('HistoryQueryDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: getDeviceTypeFromTabId("HistoryQueryRootTabPanel"),
                        deviceName: deviceName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var deviceListCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: deviceShowName,
                    id: "HistoryQueryDeviceListComb_Id",
                    labelWidth: 8*deviceShowNameLength,
                    width: (8*deviceShowNameLength+110),
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: deviceListCombStore,
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
                            deviceListCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").setValue(-1);
                        	Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                });
        
        Ext.applyIf(me, {
            items: [{
                border: false,
                layout: 'border',
                items: [{
                	region: 'west',
                    width: '32%',
                    layout: 'border',
                    split: true,
                    collapsible: true,
            		border: false,
            		header:false,
                    items:[{
                    	region: 'center',
                        title:loginUserLanguageResource.deviceList,
                        id:'HistoryQueryInfoDeviceListPanel_Id',
                        border: false,
                        layout: 'fit',
                        tbar:[{
                            id: 'HistoryQueryInfoDeviceListSelectRow_Id',
                            xtype: 'textfield',
                            value: -1,
                            hidden: true
                        },{
                        	id: 'HistoryQueryStatSelectFESdiagramResult_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                            id: 'HistoryQueryStatSelectCommStatus_Id',
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },{
                            id: 'HistoryQueryStatSelectRunStatus_Id',
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },{
                            id: 'HistoryQueryStatSelectDeviceType_Id',
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },{
                            id: 'HistoryQueryWellListColumnStr_Id',
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },{
                            id: 'HistoryQueryDataColumnStr_Id',
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },{
                            id: 'HistoryQueryDiagramOverlayColumnStr_Id',
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },{
                            xtype: 'button',
                            text: loginUserLanguageResource.refresh,
                            iconCls: 'note-refresh',
                            hidden:false,
                            handler: function (v, o) {
                            	historyDataRefresh();
                    		}
                		},'-',deviceListCombo,'-', {
                            xtype: 'button',
                            text: loginUserLanguageResource.exportData,
                            iconCls: 'export',
                            hidden:false,
                            handler: function (v, o) {
                            	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                            	var deviceName=Ext.getCmp('HistoryQueryDeviceListComb_Id').getValue();
                            	var FESdiagramResultStatValue=Ext.getCmp("HistoryQueryStatSelectFESdiagramResult_Id").getValue();
                            	var commStatusStatValue=Ext.getCmp("HistoryQueryStatSelectCommStatus_Id").getValue();
                            	var runStatusStatValue=Ext.getCmp("HistoryQueryStatSelectRunStatus_Id").getValue();
                    			var deviceTypeStatValue=Ext.getCmp("HistoryQueryStatSelectDeviceType_Id").getValue();
                           	 	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                           	 	var fileName=loginUserLanguageResource.historyQueryDeviceList;
                           	 	var title=loginUserLanguageResource.historyQueryDeviceList;
                           	 	var columnStr=Ext.getCmp("HistoryQueryWellListColumnStr_Id").getValue();
                           	 	exportHistoryQueryDeviceListExcel(orgId,deviceType,deviceName,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,fileName,title,columnStr);
                            }
                        }]
                    },{
                    	region: 'south',
                    	split: true,
                        collapsible: true,
                    	height: '50%',
                    	xtype: 'tabpanel',
                    	id:'HistoryQueryStatTabPanel',
                    	activeTab: onlyMonitor?1:0,
                        header: false,
                		tabPosition: 'top',
                		items: historyStatTabItems,
                		listeners: {
                			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                				oldCard.setIconCls(null);
                				newCard.setIconCls('check3');
                			},
                			tabchange: function (tabPanel, newCard,oldCard, obj) {
            					if(newCard.id=="HistoryQueryFESdiagramResultStatGraphPanel_Id"){
            						loadAndInitHistoryQueryFESdiagramResultStat(true);
            					}else if(newCard.id=="HistoryQueryStatGraphPanel_Id"){
            						loadAndInitHistoryQueryCommStatusStat(true);
            					}else if(newCard.id=="HistoryQueryRunStatusStatGraphPanel_Id"){
            						loadAndInitHistoryQueryRunStatusStat(true);
            					}else if(newCard.id=="HistoryQueryDeviceTypeStatGraphPanel_Id"){
            						loadAndInitHistoryQueryDeviceTypeStat(true);
            					}
            					Ext.getCmp('HistoryQueryDeviceListComb_Id').setValue('');
            					Ext.getCmp('HistoryQueryDeviceListComb_Id').setRawValue('');
            					refreshHistoryDeviceListDataByPage(parseInt(Ext.getCmp("selectedDeviceId_global").getValue()),0,Ext.getCmp("HistoryQueryDeviceListGridPanel_Id"),'AP.store.historyQuery.HistoryQueryWellListStore');
            				}
            			}
                    }]
                }, {
                	region: 'center',
                	layout: 'border',
                    xtype: 'tabpanel',
                    id:"HistoryQueryCenterTabPanel",
            		activeTab: 0,
            		autoScroll: false,
                    split: true,
                    collapsible: true,
            		border: false,
            		header:false,
            		tabPosition: 'top',
            		tbar:[{
                        xtype: 'datefield',
                        anchor: '100%',
                        fieldLabel: loginUserLanguageResource.range,
                        labelWidth: getStringLength(loginUserLanguageResource.range)*8,
                        width: getStringLength(loginUserLanguageResource.range)*8+100,
                        format: 'Y-m-d ',
                        value: '',
                        id: 'HistoryQueryStartDate_Id',
                        listeners: {
                        	select: function (combo, record, index) {
//                        		Ext.getCmp("HistoryQueryDataGridPanel_Id").getStore().loadPage(1);
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryStartTime_Hour_Id',
                        fieldLabel: loginUserLanguageResource.hour,
                        labelWidth: getStringLength(loginUserLanguageResource.hour)*8,
                        width: getStringLength(loginUserLanguageResource.hour)*8+45,
                        minValue: 0,
                        maxValue: 23,
                        value:'',
                        msgTarget: 'none',
                        regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryStartTime_Minute_Id',
                    	fieldLabel: loginUserLanguageResource.minute,
                        labelWidth: getStringLength(loginUserLanguageResource.minute)*8,
                        width: getStringLength(loginUserLanguageResource.minute)*8+45,
                        minValue: 0,
                        maxValue: 59,
                        value:'',
                        msgTarget: 'none',
                        regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryStartTime_Second_Id',
                        fieldLabel: loginUserLanguageResource.second,
                        labelWidth: getStringLength(loginUserLanguageResource.second)*8,
                        width: getStringLength(loginUserLanguageResource.second)*8+45,
                        minValue: 0,
                        maxValue: 59,
                        value:'',
                        msgTarget: 'none',
                        regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                        xtype: 'datefield',
                        anchor: '100%',
                        fieldLabel: loginUserLanguageResource.timeTo,
                        labelWidth: getStringLength(loginUserLanguageResource.timeTo)*8,
                        width: getStringLength(loginUserLanguageResource.timeTo)*8+95,
                        format: 'Y-m-d ',
                        value: '',
                        id: 'HistoryQueryEndDate_Id',
                        listeners: {
                        	select: function (combo, record, index) {
//                        		Ext.getCmp("HistoryQueryDataGridPanel_Id").getStore().loadPage(1);
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryEndTime_Hour_Id',
                    	fieldLabel: loginUserLanguageResource.hour,
                        labelWidth: getStringLength(loginUserLanguageResource.hour)*8,
                        width: getStringLength(loginUserLanguageResource.hour)*8+45,
                        minValue: 0,
                        maxValue: 23,
                        value:'',
                        msgTarget: 'none',
                        regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryEndTime_Minute_Id',
                        fieldLabel: loginUserLanguageResource.minute,
                        labelWidth: getStringLength(loginUserLanguageResource.minute)*8,
                        width: getStringLength(loginUserLanguageResource.minute)*8+45,
                        minValue: 0,
                        maxValue: 59,
                        value:'',
                        msgTarget: 'none',
                        regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryEndTime_Second_Id',
                    	fieldLabel: loginUserLanguageResource.second,
                        labelWidth: getStringLength(loginUserLanguageResource.second)*8,
                        width: getStringLength(loginUserLanguageResource.second)*8+45,
                        minValue: 0,
                        maxValue: 59,
                        value:'',
                        msgTarget: 'none',
                        regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                        listeners: {
                        	blur: function (field, event, eOpts) {
                        		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        		var flag=r.test(field.value);
                        		if(!flag){
                        			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },'-',{
                        xtype: 'button',
                        text: loginUserLanguageResource.search,
                        iconCls: 'search',
                        handler: function () {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var tabPanel = Ext.getCmp("HistoryQueryCenterTabPanel");
            				var activeId = tabPanel.getActiveTab().id;
            				if(activeId=="HistoryDataTabPanel"){
        						var gridPanel = Ext.getCmp("HistoryQueryDataGridPanel_Id");
                                if (isNotVal(gridPanel)) {
                                	gridPanel.getStore().loadPage(1);
                                }else{
                                	Ext.create("AP.store.historyQuery.HistoryDataStore");
                                }
        					}else if(activeId=="HistoryDiagramTabPanel"){
        						loadHistoryDiagramTiledList(1);
        					}else if(activeId=="HistoryDiagramOverlayTabPanel"){
        						var gridPanel = Ext.getCmp("HistoryQueryFSdiagramOverlayGrid_Id");
                                if (isNotVal(gridPanel)) {
                                	gridPanel.getStore().load();
                                }else{
                                	Ext.create("AP.store.historyQuery.HistoryQueryDiagramOverlayStore");
                                }
        					}
                        }
                    }, {
                        xtype: 'button',
                        text: loginUserLanguageResource.exportData,
                        iconCls: 'export',
                        id:'HistoryDiagramOverlayExportBtn_Id',
                        hidden:true,
                        handler: function (v, o) {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                        	var deviceName='';
                        	var deviceId=0;
                        	var calculateType=0;
                        	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
                        	if(selectRow>=0){
                        		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.deviceName;
                        		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.id;
                        		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.calculateType;
                        	}
                        	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
                            var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
                            
                       	 	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                       	 	var fileName=deviceName+'-'+loginUserLanguageResource.FSDiagramOverlayData;
                       	 	var title=deviceName+'-'+loginUserLanguageResource.FSDiagramOverlayData;
                       	 	var columnStr=Ext.getCmp("HistoryQueryDiagramOverlayColumnStr_Id").getValue();
                       	 	exportHistoryQueryDiagramOverlayDataExcel(orgId,deviceType,deviceId,deviceName,calculateType,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),fileName,title,columnStr);
                        }
                    }, {
                        xtype: 'button',
                        text: loginUserLanguageResource.exportData,
                        iconCls: 'export',
                        id:'HistoryFESDiagramDataExportBtn_Id',
                        hidden:true,
                        handler: function (v, o) {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                        	var deviceName='';
                        	var deviceId=0;
                        	var calculateType=0;
                        	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
                        	if(selectRow>=0){
                        		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.deviceName;
                        		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.id;
                        		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.calculateType;
                        	}
                        	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
                            var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
                            
                       	 	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                       	 	var fileName=deviceName+'-'+loginUserLanguageResource.FSDiagramData;
                       	 	var title=deviceName+'-'+loginUserLanguageResource.FSDiagramData;
                       	 	exportHistoryQueryDiagramTiledDataExcel(orgId,deviceType,deviceId,deviceName,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),fileName,title);
                        }
                    }, {
                        xtype: 'button',
                        text: loginUserLanguageResource.exportData,
                        iconCls: 'export',
                        id:'HistoryDataExportBtn_Id',
                        hidden:false,
                        handler: function (v, o) {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('HistoryQueryStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                        		Ext.getCmp('HistoryQueryEndTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                        	var deviceName='';
                        	var deviceId=0;
                        	var calculateType=0;
                        	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
                        	if(selectRow>=0){
                        		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
                        		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                        		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
                        		
                        	}
                        	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
                            var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
                            
                            var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                       	 	var fileName=deviceName+loginUserLanguageResource.historyData;
                       	 	var title=deviceName+loginUserLanguageResource.historyData;
                       	 	var columnStr=Ext.getCmp("HistoryQueryDataColumnStr_Id").getValue();
                       	 	exportHistoryQueryDataExcel(orgId,deviceType,deviceId,deviceName,calculateType,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),fileName,title,columnStr);
                        }
                    },'->',{
                        id: 'SurfaceCardTotalCount_Id',
                        xtype: 'component',
                        tpl: loginUserLanguageResource.totalCount + ': {count}', // 总记录数
                        hidden:true,
                        style: 'margin-right:15px'
                    }, {
                        id: 'SurfaceCardTotalPages_Id', // 记录总页数
                        xtype: 'textfield',
                        value: '',
                        hidden: true
                    }],
            		items: historyQueryCenterTabPanelItems,
            		listeners: {
            			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
            				oldCard.setIconCls(null);
            				newCard.setIconCls('check3');
            			},
            			tabchange: function (tabPanel, newCard,oldCard, obj) {
        					if(newCard.id=="HistoryDataTabPanel"){
        						Ext.getCmp("HistoryDiagramOverlayExportBtn_Id").hide();
        						Ext.getCmp("HistoryFESDiagramDataExportBtn_Id").hide();
        						Ext.getCmp("HistoryDataExportBtn_Id").show();
        						Ext.getCmp("SurfaceCardTotalCount_Id").show();
        						var gridPanel = Ext.getCmp("HistoryQueryDataGridPanel_Id");
                                if (isNotVal(gridPanel)) {
                                	gridPanel.getStore().loadPage(1);
                                }else{
                                	Ext.create("AP.store.historyQuery.HistoryDataStore");
                                }
        					}else if(newCard.id=="HistoryDiagramTabPanel"){
        						Ext.getCmp("HistoryDiagramOverlayExportBtn_Id").hide();
        						Ext.getCmp("HistoryDataExportBtn_Id").hide();
        						Ext.getCmp("HistoryFESDiagramDataExportBtn_Id").show();
        						Ext.getCmp("SurfaceCardTotalCount_Id").show();
        						loadHistoryDiagramTiledList(1);
        					}else if(newCard.id=="HistoryDiagramOverlayTabPanel"){
        						Ext.getCmp("HistoryDiagramOverlayExportBtn_Id").show();
        						Ext.getCmp("HistoryFESDiagramDataExportBtn_Id").hide();
        						Ext.getCmp("HistoryDataExportBtn_Id").hide();
        						Ext.getCmp("SurfaceCardTotalCount_Id").show();
        						
        						var gridPanel = Ext.getCmp("HistoryQueryFSdiagramOverlayGrid_Id");
                                if (isNotVal(gridPanel)) {
                                	gridPanel.getStore().load();
                                }else{
                                	Ext.create("AP.store.historyQuery.HistoryQueryDiagramOverlayStore");
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


