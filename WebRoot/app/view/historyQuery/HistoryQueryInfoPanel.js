var historyStatTabItems=[{
	title:loginUserLanguageResource.workType,
	layout: 'fit',
	id:'HistoryQueryFESdiagramResultStatGraphPanel_Id',
//	hidden: !moduleContentConfig.historyQuery.FESDiagramResultData,
//	iconCls: (!moduleContentConfig.historyQuery.FESDiagramResultData)?null:'check3',
	iconCls:'check3',
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
	id:'HistoryQueryStatGraphPanel_Id',
//	iconCls: (!moduleContentConfig.historyQuery.FESDiagramResultData)?'check3':null,
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
	title:loginUserLanguageResource.numStatus,
	layout: 'fit',
	id:'HistoryQueryNumStatusStatGraphPanel_Id',
	html: '<div id="HistoryQueryNumStatusStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
	listeners: {
        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
        	if ($("#HistoryQueryNumStatusStatGraphPanelPieDiv_Id").highcharts() != undefined) {
        		highchartsResize("HistoryQueryNumStatusStatGraphPanelPieDiv_Id");
        	}else{
            	var toolTip=Ext.getCmp("HistoryQueryNumStatusStatGraphPanelPieToolTip_Id");
            	if(!isNotVal(toolTip)){
            		Ext.create('Ext.tip.ToolTip', {
                        id:'HistoryQueryNumStatusStatGraphPanelPieToolTip_Id',
                		target: 'HistoryQueryNumStatusStatGraphPanelPieDiv_Id',
                        html: loginUserLanguageResource.statPieChartToolTip
                    });
            	}
            }
        }
    }
}

//,{
//	title:loginUserLanguageResource.deviceType,
//	layout: 'fit',
//	hidden:true,
//	id:'HistoryQueryDeviceTypeStatGraphPanel_Id',
//	html: '<div id="HistoryQueryDeviceTypeStatPieDiv_Id" style="width:100%;height:100%;"></div>',
//	listeners: {
//        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
//        	if ($("#HistoryQueryDeviceTypeStatPieDiv_Id").highcharts() != undefined) {
//        		highchartsResize("HistoryQueryDeviceTypeStatPieDiv_Id");
//        	}else{
//            	var toolTip=Ext.getCmp("HistoryQueryDeviceTypeStatPieToolTip_Id");
//            	if(!isNotVal(toolTip)){
//            		Ext.create('Ext.tip.ToolTip', {
//                        id:'HistoryQueryDeviceTypeStatPieToolTip_Id',
//                		target: 'HistoryQueryDeviceTypeStatPieDiv_Id',
//                        html: loginUserLanguageResource.statPieChartToolTip
//                    });
//            	}
//            }
//        }
//    }
//}
];

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
//	hidden:!moduleContentConfig.historyQuery.FESDiagramResultData,
	id:"HistoryQueryTiledDiagramPanel",
	layout: 'border',
    border: false,
    items: [{
    	region: 'west',
    	width: '25%',
    	id: "HistoryQueryTiledDiagramStatPanel",
    	title: loginUserLanguageResource.WellFSDiagramWorkType,
    	layout: 'fit',
    	border: true,
    	split: true,
        collapsible: true,
        collapsed:true
    },{
    	region: 'center',
    	id:"HistoryDiagramTabPanel",
    	xtype: 'tabpanel',
    	activeTab: 0,
        border: false,
        header: false,
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
        					var HistoryDiagramTabPanel = Ext.getCmp("FSDiagramTiledTabPanel_Id"); // 获取功图列表panel信息
        		            var panelHeight = HistoryDiagramTabPanel.getHeight(); // panel的高度
        		            var panelWidth = HistoryDiagramTabPanel.getWidth(); // panel的宽度
        		            var scrollWidth = getScrollWidth(); // 滚动条的宽度
        		            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
        		            var gtWidth = (panelWidth - scrollWidth) / columnCount-1; // 有滚动条时图形宽度
        		            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
        		            var gtWidth2 = gtWidth + 'px';
        		            var gtHeight2 = gtHeight + 'px';
        					
        					
        					
        					for(var i=0;i<containerChildren.length;i++){
        						var divElement = document.getElementById(containerChildren[i].id);
        						divElement.style.width = gtWidth2;
        						divElement.style.height = gtHeight2;
        					}
        					
        					
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
                            
                            let scroller = HistoryDiagramTabPanel.getScrollable();
                            let position = scroller.getPosition(); // {x:0, y: scrollTop}
                            let scrollHeight = scroller.getSize().y; // 内容总高度
                            let clientHeight = scroller.getClientSize().y; // 可视区域高度
//                          然后，最大滚动距离为：scrollHeight - clientHeight
                            var hRatio = (position.y / (scrollHeight - clientHeight));
//                            var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("surfaceCardContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                            if (hRatio > 0.8) {
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
        					
        					var HistoryDiagramTabPanel = Ext.getCmp("PSDiagramTiledTabPanel_Id"); // 获取功图列表panel信息
        		            var panelHeight = HistoryDiagramTabPanel.getHeight(); // panel的高度
        		            var panelWidth = HistoryDiagramTabPanel.getWidth(); // panel的宽度
        		            var scrollWidth = getScrollWidth(); // 滚动条的宽度
        		            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
        		            var gtWidth = (panelWidth - scrollWidth) / columnCount-1; // 有滚动条时图形宽度
        		            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
        		            var gtWidth2 = gtWidth + 'px';
        		            var gtHeight2 = gtHeight + 'px';
        					
        					for(var i=0;i<containerChildren.length;i++){
        						var divElement = document.getElementById(containerChildren[i].id);
        						divElement.style.width = gtWidth2;
        						divElement.style.height = gtHeight2;
        					}
        					
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
//                            var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("PSDiagramTiledContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
//                            if (hRatio > 0.5) {
//                                diagramPage++;
//                                loadHistoryDiagramTiledList(diagramPage);
//                            }
                            
                            let scroller = HistoryDiagramTabPanel.getScrollable();
                            let position = scroller.getPosition(); // {x:0, y: scrollTop}
                            let scrollHeight = scroller.getSize().y; // 内容总高度
                            let clientHeight = scroller.getClientSize().y; // 可视区域高度
//                          然后，最大滚动距离为：scrollHeight - clientHeight
                            var hRatio = (position.y / (scrollHeight - clientHeight));
//                            var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("surfaceCardContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                            if (hRatio > 0.8) {
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
        					var HistoryDiagramTabPanel = Ext.getCmp("ISDiagramTiledTabPanel_Id"); // 获取功图列表panel信息
        		            var panelHeight = HistoryDiagramTabPanel.getHeight(); // panel的高度
        		            var panelWidth = HistoryDiagramTabPanel.getWidth(); // panel的宽度
        		            var scrollWidth = getScrollWidth(); // 滚动条的宽度
        		            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
        		            var gtWidth = (panelWidth - scrollWidth) / columnCount-1; // 有滚动条时图形宽度
        		            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
        		            var gtWidth2 = gtWidth + 'px';
        		            var gtHeight2 = gtHeight + 'px';
        					
        					for(var i=0;i<containerChildren.length;i++){
        						var divElement = document.getElementById(containerChildren[i].id);
        						divElement.style.width = gtWidth2;
        						divElement.style.height = gtHeight2;
        					}
        					
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
//                            var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("ISDiagramTiledContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
//                            if (hRatio > 0.5) {
//                                diagramPage++;
//                                loadHistoryDiagramTiledList(diagramPage);
//                            }
                            
                            let scroller = HistoryDiagramTabPanel.getScrollable();
                            let position = scroller.getPosition(); // {x:0, y: scrollTop}
                            let scrollHeight = scroller.getSize().y; // 内容总高度
                            let clientHeight = scroller.getClientSize().y; // 可视区域高度
//                          然后，最大滚动距离为：scrollHeight - clientHeight
                            var hRatio = (position.y / (scrollHeight - clientHeight));
//                            var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("surfaceCardContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                            if (hRatio > 0.8) {
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
        		if(oldCard!=undefined){
        			oldCard.setIconCls(null);
        	    }
        	    if(newCard!=undefined){
        	    	newCard.setIconCls('check3');				
        	    }
    		},
    		tabchange: function (tabPanel, newCard,oldCard, obj) {
        		loadHistoryDiagramTiledList(1);
        	}
        }
    }]
},{
	title: loginUserLanguageResource.diagramOverlay,
	id:"HistoryDiagramOverlayTabPanel",
//	hidden:!moduleContentConfig.historyQuery.FESDiagramResultData,
	layout: 'border',
    items: [{
    	region: 'west',
    	width: '25%',
    	id: "HistoryQueryFSdiagramOverlayStatPanel",
    	title: loginUserLanguageResource.WellFSDiagramWorkType,
    	layout: 'fit',
    	border: true,
    	split: true,
        collapsible: true,
        collapsed:true
    },{
    	region: 'center',
    	layout: 'border',
    	border: false,
        header: false,
        id:'HistoryQueryFESDiagramOverlayCenterPanel',
        items: [{
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
                    fieldLabel: loginUserLanguageResource.deviceName,
                    id: "HistoryQueryDeviceListComb_Id",
                    labelWidth: getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage),
                    width: (getLabelWidth(loginUserLanguageResource.deviceName,loginUserLanguage)+110),
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
        
        var resultNameStore = new Ext.data.JsonStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/historyQueryController/getResultNameList',
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
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                	var deviceName='';
                	var deviceId=0;
                	var calculateType=0;
                	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
                	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
                		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
                		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                		calculateType=Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
                	}
                	var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id').rawValue;
                	var startTime_Hour=Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').getValue();
                	var startTime_Minute=Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').getValue();
                	var startTime_Second=0;

                    var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id').rawValue;
                    var endTime_Hour=Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').getValue();
                	var endTime_Minute=Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').getValue();
                	var endTime_Second=0;
                    
                    var new_params = {
                    		orgId: orgId,
                    		deviceId: deviceId,
                    		deviceName: deviceName,
                    		startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                            calculateType:calculateType
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                },
                load: function (store, record, f, op, o) {
                	
                }
            }
        });
        
        var resultNameComb = Ext.create(
                'AP.ux.CheckboxComboBox', {
                	fieldLabel: loginUserLanguageResource.FSDiagramWorkType,
                    labelWidth: getLabelWidth(loginUserLanguageResource.FSDiagramWorkType,loginUserLanguage),
                    width: (getLabelWidth(loginUserLanguageResource.FSDiagramWorkType,loginUserLanguage)+150),
                    id: 'HistoryQueryResultNameComBox_Id',
                    store: resultNameStore,
                    queryMode: 'remote',
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    typeAhead: false,
                    autoSelect: false,
                    allowBlank: true,
                    hidden: true,
                    triggerAction: 'all',
                    editable: false,
                    displayField: "boxval",
                    valueField: "boxkey",
                    minChars: 0,
                    multiSelect: true,    // 启用多选
                    listeners: {
                    	expand: function (sm, selections) {
                    		if(resultNameComb.getValue()==''){
                    			resultNameComb.clearValue();
                        		resultNameComb.getStore().load();
                    		}
                        },
                        select: function (combo, record, index) {
                        	
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
                    split: true,
                    collapsible: true,
            		border: false,
            		header:false,
                    items:[{
                    	region: 'center',
                        title:loginUserLanguageResource.deviceOverview,
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
                            id: 'HistoryQueryStatSelectNumStatus_Id',
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
                            	var numStatusStatValue=Ext.getCmp("HistoryQueryStatSelectNumStatus_Id").getValue();
                    			var deviceTypeStatValue=Ext.getCmp("HistoryQueryStatSelectDeviceType_Id").getValue();
                           	 	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                           	 	var fileName=loginUserLanguageResource.historyQueryDeviceList;
                           	 	var title=loginUserLanguageResource.historyQueryDeviceList;
                           	 	var columnStr=Ext.getCmp("HistoryQueryWellListColumnStr_Id").getValue();
                           	 	
                           	 	var dictDeviceType=deviceType;
                           	 	if(dictDeviceType.includes(",")){
                           	 		dictDeviceType=getDeviceTypeFromTabId_first("HistoryQueryRootTabPanel");
                           	 	}
                           	 	
                           	 	exportHistoryQueryDeviceListExcel(orgId,deviceType,deviceName,dictDeviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,numStatusStatValue,deviceTypeStatValue,fileName,title,columnStr);
                            }
                        }]
                    },{
                    	region: 'south',
                    	split: true,
                        collapsible: true,
                    	height: '50%',
                    	xtype: 'tabpanel',
                    	id:'HistoryQueryStatTabPanel',
//                    	activeTab: (!moduleContentConfig.historyQuery.FESDiagramResultData)?1:0,
                    	activeTab:0,
                        header: false,
                		tabPosition: 'top',
//                		items: historyStatTabItems,
                		items:[],
                		hidden:true,
                		listeners: {
                			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
                				if(oldCard!=undefined){
                        			oldCard.setIconCls(null);
                        	    }
                        	    if(newCard!=undefined){
                        	    	newCard.setIconCls('check3');				
                        	    }
                			},
                			tabchange: function (tabPanel, newCard,oldCard, obj) {
            					if(newCard.id=="HistoryQueryFESdiagramResultStatGraphPanel_Id"){
            						loadAndInitHistoryQueryFESdiagramResultStat(true);
            					}else if(newCard.id=="HistoryQueryStatGraphPanel_Id"){
            						loadAndInitHistoryQueryCommStatusStat(true);
            					}else if(newCard.id=="HistoryQueryRunStatusStatGraphPanel_Id"){
            						loadAndInitHistoryQueryRunStatusStat(true);
            					}else if(newCard.id=="HistoryQueryNumStatusStatGraphPanel_Id"){
            						loadAndInitHistoryQueryNumStatusStat(true);
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
                	
                	region: 'east',
                    width: '68%',
                    split: true,
                    collapsible: true,
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
            		
            		tbar:{
            			xtype:"container",
            			id:'HistoryQueryCenterToolbar_id',
            			border:false,
            			items:[{
            				xtype:"toolbar",
            				id: "HistoryQueryCenterToolbar1_id",
            				items:[{
                                xtype: 'datefield',
                                anchor: '100%',
                                fieldLabel: loginUserLanguageResource.range,
                                labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                                format: 'Y-m-d ',
                                value: '',
                                id: 'HistoryQueryStartDate_Id',
                                listeners: {
                                	select: function (combo, record, index) {
                                		
                                	}
                                }
                            },{
                            	xtype: 'numberfield',
                            	id: 'HistoryQueryStartTime_Hour_Id',
                                fieldLabel: loginUserLanguageResource.hour,
                                labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
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
                                labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
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
                                xtype: 'datefield',
                                anchor: '100%',
                                fieldLabel: loginUserLanguageResource.timeTo,
                                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                                format: 'Y-m-d ',
                                value: '',
                                id: 'HistoryQueryEndDate_Id',
                                listeners: {
                                	select: function (combo, record, index) {
                                		
                                	}
                                }
                            },{
                            	xtype: 'numberfield',
                            	id: 'HistoryQueryEndTime_Hour_Id',
                            	fieldLabel: loginUserLanguageResource.hour,
                                labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
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
                                labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
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
                                	
                                	refreshDeviceHistoryData();
                                }
                            },{
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
                                	var startTime_Second=0;
                                	
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
                                	var endTime_Second=0;
                                	
                                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                                	var deviceName='';
                                	var deviceId=0;
                                	var calculateType=0;
                                	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
                                	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
                                		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                                		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
                                		
                                	}
                                	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
                                    var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
                                    
                                    var hours=getHistoryQueryHours();
                                    
                                    var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                               	 	var fileName=deviceName+loginUserLanguageResource.historyData;
                               	 	var title=deviceName+loginUserLanguageResource.historyData;
                               	 	var columnStr=Ext.getCmp("HistoryQueryDataColumnStr_Id").getValue();
                               	 	exportHistoryQueryDataExcel(orgId,deviceType,deviceId,deviceName,calculateType,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),hours,fileName,title,columnStr);
                                }
                            },'->',{
                                id: 'HistoryQueryVacuateCount_Id',
                                xtype: 'component',
                                tpl: loginUserLanguageResource.vacuateCount + ':{vacuateCount} '+loginUserLanguageResource.totalCount + ':{totalCount}', // 抽稀记录数
                                hidden: false,
                                style: 'margin-right:15px'
                            }]
            			},{
            				xtype:"toolbar",
            				id: "HistoryQueryCenterToolbar2_id",
            				hidden:true,
            				items:[{
                                xtype: 'datefield',
                                anchor: '100%',
                                fieldLabel: loginUserLanguageResource.range,
                                labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                                format: 'Y-m-d ',
                                value: '',
                                id: 'HistoryFSDiagramQueryStartDate_Id',
                                listeners: {
                                	select: function (combo, record, index) {
                                		
                                	}
                                }
                            },{
                            	xtype: 'numberfield',
                            	id: 'HistoryFSDiagramQueryStartTime_Hour_Id',
                                fieldLabel: loginUserLanguageResource.hour,
                                labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
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
                            	id: 'HistoryFSDiagramQueryStartTime_Minute_Id',
                            	fieldLabel: loginUserLanguageResource.minute,
                                labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
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
                                xtype: 'datefield',
                                anchor: '100%',
                                fieldLabel: loginUserLanguageResource.timeTo,
                                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                                format: 'Y-m-d ',
                                value: '',
                                id: 'HistoryFSDiagramQueryEndDate_Id',
                                listeners: {
                                	select: function (combo, record, index) {
                                		
                                	}
                                }
                            },{
                            	xtype: 'numberfield',
                            	id: 'HistoryFSDiagramQueryEndTime_Hour_Id',
                            	fieldLabel: loginUserLanguageResource.hour,
                                labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
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
                            	id: 'HistoryFSDiagramQueryEndTime_Minute_Id',
                                fieldLabel: loginUserLanguageResource.minute,
                                labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                                width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
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
                                	
                                	refreshDeviceHistoryData();
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
                                	var startTime_Hour=Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').getValue();
                                	if(!r.test(startTime_Hour)){
                                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                                		Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').focus(true, 100);
                                		return;
                                	}
                                	var startTime_Minute=Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').getValue();
                                	if(!r2.test(startTime_Minute)){
                                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                                		Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').focus(true, 100);
                                		return;
                                	}
                                	var startTime_Second=0;
                                	
                                	var endTime_Hour=Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').getValue();
                                	if(!r.test(endTime_Hour)){
                                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                                		Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').focus(true, 100);
                                		return;
                                	}
                                	var endTime_Minute=Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').getValue();
                                	if(!r2.test(endTime_Minute)){
                                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                                		Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').focus(true, 100);
                                		return;
                                	}
                                	var endTime_Second=0;
                                	
                                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                                	var deviceName='';
                                	var deviceId=0;
                                	var calculateType=0;
                                	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
                                	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.deviceName;
                                		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.id;
                                		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.calculateType;
                                	}
                                	var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id').rawValue;
                                    var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id').rawValue;
                                    var hours=getHistoryQueryHours();
                                    
                                    var selectedResult=[];
                                	var statSelection = Ext.getCmp("HistoryQueryFSdiagramOverlayStatGrid_Id").getSelectionModel().getSelection();
                                	Ext.Array.each(statSelection, function (name, index, countriesItSelf) {
                                		selectedResult.push(statSelection[index].data.resultCode);
                                	});
                                	var resultCode=selectedResult.join(",");
                                    
                                    
                               	 	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                               	 	var dictDeviceType=deviceType;
                               	 	if(dictDeviceType.includes(",")){
                               	 		dictDeviceType=getDeviceTypeFromTabId_first("HistoryQueryRootTabPanel");
                               	 	}
                               	 	
                               	 	
                               	 	var fileName=deviceName+'-'+loginUserLanguageResource.FSDiagramOverlayData;
                               	 	var title=deviceName+'-'+loginUserLanguageResource.FSDiagramOverlayData;
                               	 	var columnStr=Ext.getCmp("HistoryQueryDiagramOverlayColumnStr_Id").getValue();
                               	 	
                               	 	exportHistoryQueryDiagramOverlayDataExcel(orgId,deviceType,dictDeviceType,deviceId,deviceName,resultCode,calculateType,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),hours,fileName,title,columnStr);
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
                                	var startTime_Hour=Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').getValue();
                                	if(!r.test(startTime_Hour)){
                                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                                		Ext.getCmp('HistoryFSDiagramQueryStartTime_Hour_Id').focus(true, 100);
                                		return;
                                	}
                                	var startTime_Minute=Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').getValue();
                                	if(!r2.test(startTime_Minute)){
                                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                                		Ext.getCmp('HistoryFSDiagramQueryStartTime_Minute_Id').focus(true, 100);
                                		return;
                                	}
                                	var startTime_Second=0;
                                	
                                	var endTime_Hour=Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').getValue();
                                	if(!r.test(endTime_Hour)){
                                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                                		Ext.getCmp('HistoryFSDiagramQueryEndTime_Hour_Id').focus(true, 100);
                                		return;
                                	}
                                	var endTime_Minute=Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').getValue();
                                	if(!r2.test(endTime_Minute)){
                                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                                		Ext.getCmp('HistoryFSDiagramQueryEndTime_Minute_Id').focus(true, 100);
                                		return;
                                	}
                                	var endTime_Second=0;
                                	
                                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                                	var deviceName='';
                                	var deviceId=0;
                                	var calculateType=0;
                                	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
                                	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.deviceName;
                                		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.id;
                                		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getStore().getAt(selectRow).data.calculateType;
                                	}
                                	var startDate=Ext.getCmp('HistoryFSDiagramQueryStartDate_Id').rawValue;
                                    var endDate=Ext.getCmp('HistoryFSDiagramQueryEndDate_Id').rawValue;
                                    var hours=getHistoryQueryHours();
                                    
                                    var selectedResult=[];
                                	var statSelection = Ext.getCmp("HistoryQueryFSdiagramTiledStatGrid_Id").getSelectionModel().getSelection();
                                	Ext.Array.each(statSelection, function (name, index, countriesItSelf) {
                                		selectedResult.push(statSelection[index].data.resultCode);
                                	});
                                    var resultCode=selectedResult.join(",");
                                    
                               	 	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
                               	 	var fileName=deviceName+'-'+loginUserLanguageResource.FSDiagramData;
                               	 	var title=deviceName+'-'+loginUserLanguageResource.FSDiagramData;
                               	 	exportHistoryQueryDiagramTiledDataExcel(orgId,deviceType,deviceId,deviceName,resultCode,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),hours,fileName,title);
                                }
                            },'->',{
                                id: 'HistoryFESDiagramTotalCount_Id',
                                xtype: 'component',
                                tpl: loginUserLanguageResource.totalCount + ': {count}', // 总记录数
                                hidden: true,
                                style: 'margin-right:15px'
                            },{
                                id: 'HistoryFESDiagramVacuateCount_Id',
                                xtype: 'component',
                                tpl: loginUserLanguageResource.vacuateCount + ':{vacuateCount} '+loginUserLanguageResource.totalCount + ':{totalCount}', // 抽稀记录数
                                hidden: true,
                                style: 'margin-right:15px'
                            }, {
                                id: 'SurfaceCardTotalPages_Id', // 记录总页数
                                xtype: 'textfield',
                                value: '',
                                hidden: true
                            }]
            			},{
            				xtype:"toolbar",
            				items:[{
                            	xtype: 'label',
                            	html: loginUserLanguageResource.timeRange+':'
            				},{
                                xtype: 'button',
                                text: loginUserLanguageResource.all,
                                id: 'HistoryDataTimeRangeBtn_All_Id',
                                iconCls: 'check2',
                                hidden:true,
                                handler: function (v, o) {
                    				v.setIconCls('check2');
                    				Ext.getCmp('HistoryDataTimeRangeBtn1_Id').setIconCls(null);
                    				Ext.getCmp('HistoryDataTimeRangeBtn2_Id').setIconCls(null);
                    				Ext.getCmp('HistoryDataTimeRangeBtn3_Id').setIconCls(null);
                                }
                            },{
                                xtype: 'button',
                                text: '0~8h',
                                id: 'HistoryDataTimeRangeBtn1_Id',
                                hidden:true,
                                handler: function (v, o) {
                                	v.setIconCls('check2');
                                	Ext.getCmp('HistoryDataTimeRangeBtn_All_Id').setIconCls(null);
                    				Ext.getCmp('HistoryDataTimeRangeBtn2_Id').setIconCls(null);
                    				Ext.getCmp('HistoryDataTimeRangeBtn3_Id').setIconCls(null);
                                }
                            },{
                                xtype: 'button',
                                text: '8~16h',
                                id: 'HistoryDataTimeRangeBtn2_Id',
                                hidden:true,
                                handler: function (v, o) {
                                	v.setIconCls('check2');
                                	Ext.getCmp('HistoryDataTimeRangeBtn_All_Id').setIconCls(null);
                    				Ext.getCmp('HistoryDataTimeRangeBtn1_Id').setIconCls(null);
                    				Ext.getCmp('HistoryDataTimeRangeBtn3_Id').setIconCls(null);
                                }
                            },{
                                xtype: 'button',
                                text: '16~24h',
                                id: 'HistoryDataTimeRangeBtn3_Id',
                                hidden:true,
                                handler: function (v, o) {
                                	v.setIconCls('check2');
                                	Ext.getCmp('HistoryDataTimeRangeBtn_All_Id').setIconCls(null);
                    				Ext.getCmp('HistoryDataTimeRangeBtn1_Id').setIconCls(null);
                    				Ext.getCmp('HistoryDataTimeRangeBtn2_Id').setIconCls(null);
                                }
                            },{
                            	xtype: 'checkbox',
                                boxLabel: loginUserLanguageResource.all,
                                inputValue: '1',
                                id:'HistoryDataTimeRangeCheck_All_Id',
                                checked:true,
                                name:'all',
                                handler: function(checkbox, checked) {
                                    if (checked) {
                                    	Ext.getCmp('HistoryDataTimeRangeCheck1_Id').setValue(true);
                                    	Ext.getCmp('HistoryDataTimeRangeCheck2_Id').setValue(true);
                                    	Ext.getCmp('HistoryDataTimeRangeCheck3_Id').setValue(true);
                                    	Ext.getCmp('HistoryDataTimeRangeCheck4_Id').setValue(true);
                                    } else {
                                    	
                                    }
                                    refreshDeviceHistoryData();
                                }
                            },'-',{
                            	xtype: 'checkbox',
                                boxLabel: '0~6h',
                                inputValue: '1',
                                id:'HistoryDataTimeRangeCheck1_Id',
                                checked:true,
                                name:'00:00:00~06:00:00',
                                handler: function(checkbox, checked) {
                                	var checkStatus2=Ext.getCmp('HistoryDataTimeRangeCheck2_Id').getValue();
                                	var checkStatus3=Ext.getCmp('HistoryDataTimeRangeCheck3_Id').getValue();
                                	var checkStatus4=Ext.getCmp('HistoryDataTimeRangeCheck4_Id').getValue();
                                	Ext.getCmp('HistoryDataTimeRangeCheck_All_Id').setValue(checked && checkStatus2 && checkStatus3 && checkStatus4);
                                	refreshDeviceHistoryData();
                                }
                            },'-',{
                            	xtype: 'checkbox',
                                boxLabel: '6~12h',
                                inputValue: '1',
                                id:'HistoryDataTimeRangeCheck2_Id',
                                checked:true,
                                name:'06:00:00~12:00:00',
                                handler: function(checkbox, checked) {
                                	var checkStatus1=Ext.getCmp('HistoryDataTimeRangeCheck1_Id').getValue();
                                	var checkStatus3=Ext.getCmp('HistoryDataTimeRangeCheck3_Id').getValue();
                                	var checkStatus4=Ext.getCmp('HistoryDataTimeRangeCheck4_Id').getValue();
                                	Ext.getCmp('HistoryDataTimeRangeCheck_All_Id').setValue(checked && checkStatus1 && checkStatus3 && checkStatus4);
                                	refreshDeviceHistoryData();
                                }
                            },'-',{
                            	xtype: 'checkbox',
                                boxLabel: '12~18h',
                                inputValue: '1',
                                id:'HistoryDataTimeRangeCheck3_Id',
                                checked:true,
                                name:'12:00:00~18:00:00',
                                handler: function(checkbox, checked) {
                                	var checkStatus1=Ext.getCmp('HistoryDataTimeRangeCheck1_Id').getValue();
                                	var checkStatus2=Ext.getCmp('HistoryDataTimeRangeCheck2_Id').getValue();
                                	var checkStatus4=Ext.getCmp('HistoryDataTimeRangeCheck4_Id').getValue();
                                	Ext.getCmp('HistoryDataTimeRangeCheck_All_Id').setValue(checked && checkStatus1 && checkStatus2 && checkStatus4);
                                	refreshDeviceHistoryData();
                                }
                            },'-',{
                            	xtype: 'checkbox',
                                boxLabel: '18~24h',
                                inputValue: '1',
                                id:'HistoryDataTimeRangeCheck4_Id',
                                checked:true,
                                name:'18:00:00~23:59:59',
                                handler: function(checkbox, checked) {
                                	var checkStatus1=Ext.getCmp('HistoryDataTimeRangeCheck1_Id').getValue();
                                	var checkStatus2=Ext.getCmp('HistoryDataTimeRangeCheck2_Id').getValue();
                                	var checkStatus3=Ext.getCmp('HistoryDataTimeRangeCheck3_Id').getValue();
                                	Ext.getCmp('HistoryDataTimeRangeCheck_All_Id').setValue(checked && checkStatus1 && checkStatus2 && checkStatus3);
                                	refreshDeviceHistoryData();
                                }
                            }]
            			}]
            		},
//            		items: historyQueryCenterTabPanelItems,
            		items:[],
//            		hidden:true,
            		listeners: {
            			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
            				if(oldCard!=undefined){
                    			oldCard.setIconCls(null);
                    	    }
                    	    if(newCard!=undefined){
                    	    	newCard.setIconCls('check3');				
                    	    }
            			},
            			tabchange: function (tabPanel, newCard,oldCard, obj) {
        					if(newCard.id=="HistoryDataTabPanel"){
        						Ext.getCmp("HistoryQueryCenterToolbar1_id").show();
        						Ext.getCmp("HistoryQueryCenterToolbar2_id").hide();
        						
        						var gridPanel = Ext.getCmp("HistoryQueryDataGridPanel_Id");
                                if (isNotVal(gridPanel)) {
                                	gridPanel.getStore().loadPage(1);
                                }else{
                                	Ext.create("AP.store.historyQuery.HistoryDataStore");
                                }
        					}else if(newCard.id=="HistoryQueryTiledDiagramPanel"){
        						Ext.getCmp("HistoryQueryCenterToolbar1_id").hide();
        						Ext.getCmp("HistoryQueryCenterToolbar2_id").show();
        						
//        						Ext.getCmp("HistoryQueryResultNameComBox_Id").show();
        						Ext.getCmp("HistoryFESDiagramDataExportBtn_Id").show();
        						Ext.getCmp("HistoryDiagramOverlayExportBtn_Id").hide();
        						
        						Ext.getCmp("HistoryFESDiagramVacuateCount_Id").hide();
        						Ext.getCmp("HistoryFESDiagramTotalCount_Id").show();
        						
        						var HistoryQueryFSdiagramTiledStatGrid = Ext.getCmp("HistoryQueryFSdiagramTiledStatGrid_Id");
                                if (isNotVal(HistoryQueryFSdiagramTiledStatGrid)) {
                                	HistoryQueryFSdiagramTiledStatGrid.getStore().load();
                                }else{
                                	Ext.create("AP.store.historyQuery.HistoryQueryDiagramTiledStatStore");
                                }
        					}else if(newCard.id=="HistoryDiagramOverlayTabPanel"){
        						Ext.getCmp("HistoryQueryCenterToolbar1_id").hide();
        						Ext.getCmp("HistoryQueryCenterToolbar2_id").show();
        						
        						Ext.getCmp("HistoryFESDiagramVacuateCount_Id").show();
        						Ext.getCmp("HistoryFESDiagramTotalCount_Id").hide();
        						
//        						Ext.getCmp("HistoryQueryResultNameComBox_Id").hide();
        						Ext.getCmp("HistoryFESDiagramDataExportBtn_Id").hide();
        						Ext.getCmp("HistoryDiagramOverlayExportBtn_Id").show();
                                
                                var HistoryQueryFSdiagramOverlayStatGrid = Ext.getCmp("HistoryQueryFSdiagramOverlayStatGrid_Id");
                                if (isNotVal(HistoryQueryFSdiagramOverlayStatGrid)) {
                                	HistoryQueryFSdiagramOverlayStatGrid.getStore().load();
                                }else{
                                	Ext.create("AP.store.historyQuery.HistoryQueryDiagramOverlayStatStore");
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


