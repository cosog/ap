var rpcDeviceRealTimeMonitoringDataHandsontableHelper=null;
Ext.define("AP.view.realTimeMonitoring.RPCRealTimeMonitoringInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.rpcRealTimeMonitoringInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var rpcCombStore = new Ext.data.JsonStore({
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
                    var wellName = Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 0,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var rpcDeviceCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '井名',
                    id: "RealTimeMonitoringRPCDeviceListComb_Id",
                    labelWidth: 35,
                    width: 145,
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: rpcCombStore,
                    autoSelect: false,
                    editable: true,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize:comboxPagingStatus,
                    minChars:0,
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    listeners: {
                        expand: function (sm, selections) {
                            rpcDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getStore().loadPage(1);
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
                    id:'RPCRealTimeMonitoringCenterPanel_Id',
                    items:[{
                    	region: 'center',
                    	title:'设备概览',
                    	id:'RPCRealTimeMonitoringInfoDeviceListPanel_Id',
                        border: false,
                        layout: 'fit',
                        tbar:[{
                        	id: 'RPCRealTimeMonitoringInfoDeviceListSelectRow_Id',
                        	xtype: 'textfield',
                            value: -1,
                            hidden: true
                         },{
                        	id: 'RPCRealTimeMonitoringStatSelectFESdiagramResult_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                        	id: 'RPCRealTimeMonitoringStatSelectCommStatus_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                        	id: 'RPCRealTimeMonitoringStatSelectRunStatus_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                        	id: 'RPCRealTimeMonitoringStatSelectDeviceType_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                             id: 'RPCRealTimeMonitoringColumnStr_Id',
                             xtype: 'textfield',
                             value: '',
                             hidden: true
                         },rpcDeviceCombo,'-', {
                             xtype: 'button',
                             text: cosog.string.exportExcel,
                             iconCls: 'export',
                             hidden:false,
                             handler: function (v, o) {
                            	 var orgId = Ext.getCmp('leftOrg_Id').getValue();
                            	 var deviceName=Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').getValue();
                            	 var FESdiagramResultStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectFESdiagramResult_Id").getValue();
                             	 var commStatusStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").getValue();
                             	 var runStatusStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectRunStatus_Id").getValue();
                             	 var deviceTypeStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").getValue();
                            	 var deviceType=0;
                            	 var fileName='抽油机井实时监控数据';
                            	 var title='抽油机井实时监控数据';
                            	 var columnStr=Ext.getCmp("RPCRealTimeMonitoringColumnStr_Id").getValue();
                            	 exportRealTimeMonitoringDataExcel(orgId,deviceType,deviceName,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,fileName,title,columnStr);
                             }
                         }, '->', {
                         	xtype: 'button',
                            text:'查看历史',
                            tooltip:'点击按钮或者双击表格，查看历史数据',
                            handler: function (v, o) {
                            	var selectRow= Ext.getCmp("RPCRealTimeMonitoringInfoDeviceListSelectRow_Id").getValue();
                        		var gridPanel=Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
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
                    	id:'RPCRealTimeMonitoringStatTabPanel',
                    	activeTab: 0,
                        header: false,
                		tabPosition: 'top',
                		items: [{
                			title:'工况诊断',
                			layout: 'fit',
                        	id:'RPCRealTimeMonitoringFESdiagramResultStatGraphPanel_Id',
                        	html: '<div id="RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if(isNotVal($("#RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id"))){
                                		if ($("#RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id").highcharts() != undefined) {
                                			highchartsResize("RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id");
                                        }else{
                                        	var toolTip=Ext.getCmp("RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieToolTip_Id");
                                        	if(!isNotVal(toolTip)){
                                        		Ext.create('Ext.tip.ToolTip', {
                                                    id:'RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieToolTip_Id',
                                            		target: 'RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id',
                                                    html: '点击饼图不同区域或标签，查看相应统计数据'
                                                });
                                        	}
                                        }
                                	}
                                }
                            }
                		},{
                			title:'运行状态',
                			layout: 'fit',
                        	id:'RPCRealTimeMonitoringRunStatusStatGraphPanel_Id',
                        	html: '<div id="RPCRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if(isNotVal($("#RPCRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id"))){
                                		if ($("#RPCRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id").highcharts() != undefined) {
                                			highchartsResize("RPCRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id");
                                		}else{
                                        	var toolTip=Ext.getCmp("RPCRealTimeMonitoringRunStatusStatGraphPanelPieToolTip_Id");
                                        	if(!isNotVal(toolTip)){
                                        		Ext.create('Ext.tip.ToolTip', {
                                                    id:'RPCRealTimeMonitoringRunStatusStatGraphPanelPieToolTip_Id',
                                            		target: 'RPCRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id',
                                                    html: '点击饼图不同区域或标签，查看相应统计数据'
                                                });
                                        	}
                                        }
                                	}
                                }
                            }
                		},{
                			title:'通信状态',
                			layout: 'fit',
                        	id:'RPCRealTimeMonitoringStatGraphPanel_Id',
                        	html: '<div id="RPCRealTimeMonitoringStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if(isNotVal($("#RPCRealTimeMonitoringStatGraphPanelPieDiv_Id"))){
                                		if ($("#RPCRealTimeMonitoringStatGraphPanelPieDiv_Id").highcharts() != undefined) {
                                			highchartsResize("RPCRealTimeMonitoringStatGraphPanelPieDiv_Id");
                                		}else{
                                        	var toolTip=Ext.getCmp("RPCRealTimeMonitoringStatGraphPanelPieToolTip_Id");
                                        	if(!isNotVal(toolTip)){
                                        		Ext.create('Ext.tip.ToolTip', {
                                                    id:'RPCRealTimeMonitoringStatGraphPanelPieToolTip_Id',
                                            		target: 'RPCRealTimeMonitoringStatGraphPanelPieDiv_Id',
                                                    html: '点击饼图不同区域或标签，查看相应统计数据'
                                                });
                                        	}
                                        }
                                	}
                                }
                            }
                		},{
                			title:'设备类型',
                			layout: 'fit',
                			hidden: true,
                        	id:'RPCRealTimeMonitoringDeviceTypeStatGraphPanel_Id',
                        	html: '<div id="RPCRealTimeMonitoringDeviceTypeStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if(isNotVal($("#RPCRealTimeMonitoringStatGraphPanelPieDiv_Id"))){
                                		if ($("#RPCRealTimeMonitoringDeviceTypeStatPieDiv_Id").highcharts() != undefined) {
                                			highchartsResize("RPCRealTimeMonitoringDeviceTypeStatPieDiv_Id");
                                		}else{
                                        	var toolTip=Ext.getCmp("RPCRealTimeMonitoringDeviceTypeStatPieToolTip_Id");
                                        	if(!isNotVal(toolTip)){
                                        		Ext.create('Ext.tip.ToolTip', {
                                                    id:'RPCRealTimeMonitoringDeviceTypeStatPieToolTip_Id',
                                            		target: 'RPCRealTimeMonitoringDeviceTypeStatPieDiv_Id',
                                                    html: '点击饼图不同区域或标签，查看相应统计数据'
                                                });
                                        	}
                                        }
                                	}
                                }
                            }
                		}],
                		listeners: {
            				tabchange: function (tabPanel, newCard,oldCard, obj) {
            					if(newCard.id=="RPCRealTimeMonitoringFESdiagramResultStatGraphPanel_Id"){
            						loadAndInitFESdiagramResultStat(true);
            					}else if(newCard.id=="RPCRealTimeMonitoringStatGraphPanel_Id"){
            						loadAndInitCommStatusStat(true);
            					}else if(newCard.id=="RPCRealTimeMonitoringRunStatusStatGraphPanel_Id"){
            						loadAndInitRunStatusStat(true);
            					}else if(newCard.id=="RPCRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
            						loadAndInitDeviceTypeStat(true);
            					}
            					Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setValue('');
        						Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setRawValue('');
        						var gridPanel = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getSelectionModel().deselectAll(true);
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore');
        						}
            				}
            			}
                    }]
                }, {
                	region: 'east',
                    width: '68%',
                    id:'RPCRealTimeMonitoringEastPanel_Id',
                    autoScroll: true,
                    split: true,
                    collapsible: true,
                    layout: 'border',
                    header: false,
                    items:[{
                        region: 'center',
                        xtype: 'tabpanel',
                		id:"RPCRealTimeMonitoringCurveAndTableTabPanel",
                		activeTab: 0,
                		border: false,
                		tabPosition: 'top',
                		items: [{
                        	title: '井筒分析',
                        	margin: '0 0 0 0',
                            padding: 0,
                            autoScroll:true,
                            scrollable: true,
                            id: 'RPCRealTimeMonitoringFSDiagramAnalysisTabPanel_Id',
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
                        	title: '地面分析',
                        	margin: '0 0 0 0',
                            padding: 0,
                            autoScroll:true,
                            scrollable: true,
                            id: 'RPCRealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id',
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
//                                        height:300,
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
//                                        height:300,
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
//                                        height:300,
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
//                                        height:300,
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
//                            	,{
//                            		border: false,
////                            		flex: 1,
//                            		margin: '0 0 0 0',
//                            		layout: {
//                            	        type: 'hbox',
//                            	        pack: 'start',
//                            	        align: 'stretch'
//                            	    },
//                            	    items:[{
//                            	    	border: true,
//                            	    	layout: 'fit',
//                            	    	margin: '1 0 0 0',
//                                        flex: 1,
//                                        height:300,
//                                        align:'stretch',
//                                        html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv5_id\" style="width:100%;height:100%;"></div>',
//                                        listeners: {
//                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
//                                            	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv5_id").highcharts()!=undefined){
//                                        			highchartsResize("FSDiagramAnalysisSingleSurfaceDetailsDiv5_id");
//                                        		}
//                                            }
//                                        }
//                            	    }]
//                            	}
                            ]
                        },{
                			title:'趋势曲线',
                			id:"RPCRealTimeMonitoringCurveTabPanel_Id",
                			layout: 'border',
                			items: [{
                				region: 'center',
                				layout: 'fit',
                    			autoScroll: true,
                    			border: false,
                    			id:"rpcRealTimeMonitoringCurveContent",
                    			html: '<div id="rpcRealTimeMonitoringCurveContainer" class="hbox" style="width:100%;height:100%;"></div>',
                    			listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	var container=$('#rpcRealTimeMonitoringCurveContainer');
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
                			title:'动态数据',
                			id:"RPCRealTimeMonitoringTableTabPanel_Id",
                			layout: 'border',
                            border: false,
                            items: [{
                            	region: 'center',
                            	header: false,
                            	id: "RPCRealTimeMonitoringInfoDataPanel_Id",
                            	layout: 'fit',
                            	html:'<div class="RPCRealTimeMonitoringInfoDataTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="RPCRealTimeMonitoringInfoDataTableInfoDiv_id"></div></div>',
                            	listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	if(rpcDeviceRealTimeMonitoringDataHandsontableHelper!=null && rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
                                    		rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot.refreshDimensions();
                                    	}
                                    }
                                }
                            }]
                		}],
                		listeners: {
            				tabchange: function (tabPanel, newCard,oldCard, obj) {
            					var selectRow= Ext.getCmp("RPCRealTimeMonitoringInfoDeviceListSelectRow_Id").getValue();
            					var gridPanel=Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
            					if(isNotVal(gridPanel)&&selectRow>=0){
            						if(newCard.id=="RPCRealTimeMonitoringCurveTabPanel_Id"){
            							deviceRealtimeMonitoringCurve(0);
                					}else if(newCard.id=="RPCRealTimeMonitoringTableTabPanel_Id"){
                						var selectedItem=gridPanel.getStore().getAt(selectRow);
                            			CreateRPCDeviceRealTimeMonitoringDataTable(selectedItem.data.id,selectedItem.data.wellName,0);
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
                    	id:"RPCRealTimeMonitoringRightTabPanel",
                		activeTab: 0,
                		border: false,
                		split: true,
                        collapsible: true,
                        header: false,
                		tabPosition: 'top',
                		items: [{
                			title:'设备信息',
                			layout: 'border',
                			items:[{
                				region: 'center',
                				id: 'RPCRealTimeMonitoringRightDeviceInfoPanel',
                                border: false,
                                layout: 'fit',
                                autoScroll: true,
                                scrollable: true
                			},{
                				region: 'south',
                				id: 'RPCRealTimeMonitoringRightAuxiliaryDeviceInfoPanel',
                				title:'辅件设备',
                				height: '50%',
                				border: false,
                                layout: 'fit',
                                split: true,
                                hidden:true,
                                collapsible: true,
                                autoScroll: true,
                                scrollable: true
                			}]
                		},{
                			title:'设备控制',
                			border: false,
                            layout: 'border',
                            hideMode:'offsets',
                            id:'RPCRealTimeMonitoringRightControlAndVideoPanel',
                            items: [{
                            	region: 'north',
                            	layout: 'fit',
                            	height: 220,
                            	id:'RPCRealTimeMonitoringRightVideoPanel1',
                            	hidden:true,
                            	collapsible: true, // 是否折叠
                            	header: false,
                                split: true, // 竖折叠条
                                autoRender:true,
                                html: '<div id="RPCRealTimeMonitoringRightVideoDiv1_Id" style="width:100%;height:100%;"></div>',
                                listeners: {
                                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                		if(Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                			if(videoPlayrHelper.rpc.player1!=null){
                                        		var isFullScreen = isBrowserFullScreen();
                                        		if(!isFullScreen){
                                    				var recordData=Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data;
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
                                	id:'RPCRealTimeMonitoringRightVideoPanel2',
                                	hidden:true,
                                	collapsible: true, // 是否折叠
                                	header: false,
                                    split: true, // 竖折叠条
                                    autoRender:true,
                                    html: '<div id="RPCRealTimeMonitoringRightVideoDiv2_Id" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                    	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    		if(Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                    			if(videoPlayrHelper.rpc.player2!=null){
                                            		var isFullScreen = isBrowserFullScreen();
                                            		if(!isFullScreen){
                                        				var recordData=Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data;
                                            			createVideo(0,recordData,2,true);
                                        			}
                                        		}
                                    		}
                                    	}
                                    }
                            	},{
                            		region: 'center',
                                    id: 'RPCRealTimeMonitoringRightControlPanel',
                                    border: false,
                                    layout: 'fit',
                                    autoScroll: true,
                                    scrollable: true
                            	}]
                            }]
                		}],
                		listeners: {
                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                        		if(newCard.id=="RPCRealTimeMonitoringRightControlAndVideoPanel"){
                                	if(Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                		createVideo(0,Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data);
                                		var controlGridPanel=Ext.getCmp("RPCRealTimeMonitoringControlDataGridPanel_Id");
                            			if(isNotVal(controlGridPanel)){
                            				controlGridPanel.getStore().load();
                            			}else{
                            				Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringDeviceControlStore');
                            			}
                                	}else{
                                		var videoPanel1=Ext.getCmp("RPCRealTimeMonitoringRightVideoPanel1");
                                		var videoPanel2=Ext.getCmp("RPCRealTimeMonitoringRightVideoPanel2");
                                		if(videoPlayrHelper.rpc.player1!=null && videoPlayrHelper.rpc.player1.pluginStatus.state.play){
                                			videoPlayrHelper.rpc.player1.stop();
                                		}
                                		if(videoPlayrHelper.rpc.player2!=null && videoPlayrHelper.rpc.player2.pluginStatus.state.play){
                                			videoPlayrHelper.rpc.player2.stop();
                                		}
                                		videoPanel1.hide();
                                		videoPanel2.hide();
                                		Ext.getCmp("RPCRealTimeMonitoringRightControlPanel").removeAll();
                                	}
                        		}else{
                        			if(videoPlayrHelper.rpc.player1!=null && videoPlayrHelper.rpc.player1.pluginStatus.state.play){
                        				videoPlayrHelper.rpc.player1.stop();
                        			}
                        			if(videoPlayrHelper.rpc.player2!=null && videoPlayrHelper.rpc.player2.pluginStatus.state.play){
                        				videoPlayrHelper.rpc.player2.stop();
                        			}
                        			if(Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                        				var deviceInfoGridPanel=Ext.getCmp("RPCRealTimeMonitoringDeviceInfoDataGridPanel_Id");
                            			if(isNotVal(deviceInfoGridPanel)){
                            				deviceInfoGridPanel.getStore().load();
                            			}else{
                            				Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringDeviceInfoStore');
                            			}
                        			}else{
                        				Ext.getCmp("RPCRealTimeMonitoringRightDeviceInfoPanel").removeAll();
                                    	Ext.getCmp("RPCRealTimeMonitoringRightAuxiliaryDeviceInfoPanel").removeAll();
                        			}
                        		}
                            }
                        }
                    }],
                    listeners: {
                        beforeCollapse: function (panel, eOpts) {
                        	var container=$('#rpcRealTimeMonitoringCurveContainer');
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
                        	var container=$('#rpcRealTimeMonitoringCurveContainer');
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

function CreateRPCDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getDeviceRealTimeMonitoringData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(rpcDeviceRealTimeMonitoringDataHandsontableHelper==null || rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot==undefined){
				rpcDeviceRealTimeMonitoringDataHandsontableHelper = RPCDeviceRealTimeMonitoringDataHandsontableHelper.createNew("RPCRealTimeMonitoringInfoDataTableInfoDiv_id");
				var colHeaders="['名称','变量','名称','变量','名称','变量']";
				var columns="[" 
						+"{data:'name1'}," 
						+"{data:'value1'}," 
						+"{data:'name2'},"
						+"{data:'value2'}," 
						+"{data:'name3'}," 
						+"{data:'value3'}" 
						+"]";
				rpcDeviceRealTimeMonitoringDataHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				rpcDeviceRealTimeMonitoringDataHandsontableHelper.columns=Ext.JSON.decode(columns);
				rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo=result.CellInfo;
				if(result.totalRoot.length==0){
					rpcDeviceRealTimeMonitoringDataHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					rpcDeviceRealTimeMonitoringDataHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo=result.CellInfo;
				rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(result.totalRoot);
			}
			//添加单元格属性
			for(var i=0;i<rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length;i++){
				var row=rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
				var col=rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col;
				var column=rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].column;
				var columnDataType=rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].columnDataType;
				rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot.setCellMeta(row,col,'columnDataType',columnDataType);
			}
			
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			deviceId:deviceId,
			deviceName:deviceName,
			deviceType:deviceType
        }
	});
};

var RPCDeviceRealTimeMonitoringDataHandsontableHelper = {
		createNew: function (divid) {
	        var rpcDeviceRealTimeMonitoringDataHandsontableHelper = {};
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.divid = divid;
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.validresult=true;//数据校验
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.colHeaders=[];
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.columns=[];
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo=[];
	        
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.addFirstAlarmLevelColBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue()); 
//	        	var BackgroundColor='#'+AlarmShowStyle.FirstLevel.BackgroundColor;
//	        	var Color='#'+AlarmShowStyle.FirstLevel.Color;
	        	var Color='#'+AlarmShowStyle.FirstLevel.BackgroundColor;
	        	var Opacity=AlarmShowStyle.FirstLevel.Opacity;
	     		
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
//	             td.style.backgroundColor = BackgroundColor;   
	             td.style.color=Color;
	             td.style.fontWeight = 'bold';
	             td.style.fontFamily = 'SimHei';
	             if(row%2==1){
	            	 td.style.backgroundColor = '#E6E6E6';
	             }
	        }
	        
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.addSecondAlarmLevelColBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue()); 
//	        	var BackgroundColor='#'+AlarmShowStyle.SecondLevel.BackgroundColor;
//	        	var Color='#'+AlarmShowStyle.SecondLevel.Color;
	        	var Color='#'+AlarmShowStyle.SecondLevel.BackgroundColor;
	        	var Opacity=AlarmShowStyle.SecondLevel.Opacity;
	     		
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
//	             td.style.backgroundColor = BackgroundColor;   
	             td.style.color=Color;
	             td.style.fontWeight = 'bold';
	             td.style.fontFamily = 'SimHei';
	             if(row%2==1){
	            	 td.style.backgroundColor = '#E6E6E6';
	             }
	             
	        }
	        
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.addThirdAlarmLevelColBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue()); 
//	        	var BackgroundColor='#'+AlarmShowStyle.ThirdLevel.BackgroundColor;
//	        	var Color='#'+AlarmShowStyle.ThirdLevel.Color;
	        	var Color='#'+AlarmShowStyle.ThirdLevel.BackgroundColor;
	        	var Opacity=AlarmShowStyle.ThirdLevel.Opacity;
	     		
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
//	             td.style.backgroundColor = BackgroundColor;   
	             td.style.color=Color;
	             td.style.fontWeight = 'bold';
	             td.style.fontFamily = 'SimHei';
	             if(row%2==1){
	            	 td.style.backgroundColor = '#E6E6E6';
	             }
	        }
	        
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = '#E6E6E6';
	        }
	        
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.addItenmNameColStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.fontWeight = 'bold';
	        }
	        
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	if(row>0 && value!=null && value.length>11){
	        		value=value.substring(0, 8)+"...";
                }
	        	
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	            var AlarmShowStyle=Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue()); 
	            if (row ==0) {
	            	Handsontable.renderers.TextRenderer.apply(this, arguments);
//		        	td.style.fontWeight = 'bold';
			        td.style.fontSize = '20px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '40px';
	            }
	            if (row%2==1&&row>0) {
	            	td.style.backgroundColor = '#f5f5f5';
                }
	            if (col%2==0) {
//	            	td.style.fontWeight = 'bold';
                }else{
                	td.style.fontFamily = 'SimHei';
                }
	            for(var i=0;i<rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length;i++){
                	if(rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel>=0){
                		var row2=rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
        				var col2=rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col*2+1;
        				if(row==row2 && col==col2 ){
        					if(rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel>0){
        						td.style.fontWeight = 'bold';
       			             	td.style.fontFamily = 'SimHei';
        					}
   			             	if(rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==0){
   			             		if(AlarmShowStyle.Data.Normal.Opacity!=0){
   			             			td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.Normal.BackgroundColor,AlarmShowStyle.Data.Normal.Opacity);
   			             		}
   			             		td.style.color='#'+AlarmShowStyle.Data.Normal.Color;
   			             	}else if(rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==100){
        						if(AlarmShowStyle.Data.FirstLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.FirstLevel.BackgroundColor,AlarmShowStyle.Data.FirstLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.FirstLevel.Color;
        					}else if(rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==200){
        						if(AlarmShowStyle.Data.SecondLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.SecondLevel.BackgroundColor,AlarmShowStyle.Data.SecondLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.SecondLevel.Color;
        					}else if(rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==300){
        						if(AlarmShowStyle.Data.ThirdLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.ThirdLevel.BackgroundColor,AlarmShowStyle.Data.ThirdLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.ThirdLevel.Color;
        					}
        				}
                	}
    			}
	        }
	        
	        rpcDeviceRealTimeMonitoringDataHandsontableHelper.createTable = function (data) {
	        	$('#'+rpcDeviceRealTimeMonitoringDataHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+rpcDeviceRealTimeMonitoringDataHandsontableHelper.divid);
	        	rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [30,20,30,20,30,20],
	                columns:rpcDeviceRealTimeMonitoringDataHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: false,//显示行头
	                colHeaders: false,
	                autoWrapRow: false, //自动换行
	                rowHeights: [40],
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
	                    cellProperties.renderer = rpcDeviceRealTimeMonitoringDataHandsontableHelper.addCellStyle;
	                    
	                    cellProperties.readOnly = true;
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
	                	
	                }
	        	});
	        }
	        return rpcDeviceRealTimeMonitoringDataHandsontableHelper;
	    }
};
function rpcRealTimeMonitoringCurve(item){
	var gridPanel=Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id")
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
			    var title = result.deviceName  + result.item + "曲线";
			    var xTitle='采集时间';
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
			    initTimeAndDataCurveChartFn(ser, tickInterval, "rpcRealTimeMonitoringCurveDiv_Id", title, '', xTitle, yTitle, color,false,'%H:%M:%S');
			},
			failure:function(){
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			},
			params: {
				deviceName:deviceName,
				item:item,
				deviceType:0
	        }
		});
	}
}