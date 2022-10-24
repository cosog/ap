var pcpDeviceRealTimeMonitoringDataHandsontableHelper=null;
Ext.define("AP.view.realTimeMonitoring.PCPRealTimeMonitoringInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.pcpRealTimeMonitoringInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var pcpCombStore = new Ext.data.JsonStore({
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
                    var wellName = Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 1,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var pcpDeviceCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '井名',
                    id: "RealTimeMonitoringPCPDeviceListComb_Id",
                    labelWidth: 35,
                    width: 145,
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: pcpCombStore,
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
                            pcpDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getStore().loadPage(1);
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
                    id:'PCPRealTimeMonitoringCenterPanel_Id',
                    items:[{
                    	region: 'center',
                    	title:'设备概览',
                    	id:'PCPRealTimeMonitoringInfoDeviceListPanel_Id',
                        border: false,
                        layout: 'fit',
                        tbar:[{
                        	id: 'PCPRealTimeMonitoringInfoDeviceListSelectRow_Id',
                        	xtype: 'textfield',
                            value: -1,
                            hidden: true
                         },{
                        	id: 'PCPRealTimeMonitoringStatSelectCommStatus_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                        	id: 'PCPRealTimeMonitoringStatSelectRunStatus_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                        	id: 'PCPRealTimeMonitoringStatSelectDeviceType_Id',
                        	xtype: 'textfield',
                            value: '',
                            hidden: true
                         },{
                             id: 'PCPRealTimeMonitoringColumnStr_Id',
                             xtype: 'textfield',
                             value: '',
                             hidden: true
                         },pcpDeviceCombo,'-', {
                             xtype: 'button',
                             text: cosog.string.exportExcel,
                             iconCls: 'export',
                             hidden:false,
                             handler: function (v, o) {
                            	 var orgId = Ext.getCmp('leftOrg_Id').getValue();
                            	 var deviceName=Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').getValue();
                            	 var commStatusStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectCommStatus_Id").getValue();
                             	 var runStatusStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectRunStatus_Id").getValue();
                             	 var deviceTypeStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectDeviceType_Id").getValue();
                            	 var deviceType=1;
                            	 var fileName='螺杆泵井实时监控数据';
                            	 var title='螺杆泵井实时监控数据';
                            	 var columnStr=Ext.getCmp("PCPRealTimeMonitoringColumnStr_Id").getValue();
                            	 exportRealTimeMonitoringDataExcel(orgId,deviceType,deviceName,'',commStatusStatValue,runStatusStatValue,deviceTypeStatValue,fileName,title,columnStr);
                             }
                         }, '->', {
                         	xtype: 'button',
                            text:'查看历史',
                            tooltip:'点击按钮或者双击表格，查看历史数据',
//                            pressed: true,
                            handler: function (v, o) {
                            	var selectRow= Ext.getCmp("PCPRealTimeMonitoringInfoDeviceListSelectRow_Id").getValue();
                        		var gridPanel=Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
                        		if(isNotVal(gridPanel)){
                        			var record=gridPanel.getStore().getAt(selectRow);
                        			gotoDeviceHistory(record.data.wellName,1);
                        		}
                            }
                        }]
                    },{
                    	region: 'south',
                    	split: true,
                        collapsible: true,
                    	height: '50%',
                    	xtype: 'tabpanel',
                    	id:'PCPRealTimeMonitoringStatTabPanel',
                    	activeTab: 0,
                        header: false,
                		tabPosition: 'top',
                		items: [{
                			title:'运行状态',
                			layout: 'fit',
                        	id:'PCPRealTimeMonitoringRunStatusStatGraphPanel_Id',
                        	html: '<div id="PCPRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if(isNotVal($("#PCPRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id"))){
                                		if ($("#PCPRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id").highcharts() != undefined) {
                                			highchartsResize("PCPRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id");
                                		}else{
                                        	var toolTip=Ext.getCmp("PCPRealTimeMonitoringRunStatusStatGraphPanelPieToolTip_Id");
                                        	if(!isNotVal(toolTip)){
                                        		Ext.create('Ext.tip.ToolTip', {
                                                    id:'PCPRealTimeMonitoringRunStatusStatGraphPanelPieToolTip_Id',
                                            		target: 'PCPRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id',
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
                        	id:'PCPRealTimeMonitoringStatGraphPanel_Id',
                        	html: '<div id="PCPRealTimeMonitoringStatGraphPanelPieDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if(isNotVal($("#PCPRealTimeMonitoringStatGraphPanelPieDiv_Id"))){
                                		if ($("#PCPRealTimeMonitoringStatGraphPanelPieDiv_Id").highcharts() != undefined) {
                                			highchartsResize("PCPRealTimeMonitoringStatGraphPanelPieDiv_Id");
                                		}else{
                                        	Ext.create('Ext.tip.ToolTip', {
                                                target: 'PCPRealTimeMonitoringStatGraphPanelPieDiv_Id',
                                                html: '点击饼图不同区域或标签，查看相应统计数据'
                                            });
                                        }
                                	}
                                }
                            }
                		},{
                			title:'设备类型',
                			layout: 'fit',
                			hidden: true,
                        	id:'PCPRealTimeMonitoringDeviceTypeStatGraphPanel_Id',
                        	html: '<div id="PCPRealTimeMonitoringDeviceTypeStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                        	listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if(isNotVal($("#PCPRealTimeMonitoringDeviceTypeStatPieDiv_Id"))){
                                		if ($("#PCPRealTimeMonitoringDeviceTypeStatPieDiv_Id").highcharts() != undefined) {
                                			highchartsResize("PCPRealTimeMonitoringDeviceTypeStatPieDiv_Id");
                                		}else{
                                        	Ext.create('Ext.tip.ToolTip', {
                                                target: 'PCPRealTimeMonitoringDeviceTypeStatPieDiv_Id',
                                                html: '点击饼图不同区域或标签，查看相应统计数据'
                                            });
                                        }
                                	}
                                }
                            }
                		}],
                		listeners: {
            				tabchange: function (tabPanel, newCard,oldCard, obj) {
            					if(newCard.id=="PCPRealTimeMonitoringStatGraphPanel_Id"){
            						loadAndInitCommStatusStat(true);
            					}else if(newCard.id=="PCPRealTimeMonitoringRunStatusStatGraphPanel_Id"){
            						loadAndInitRunStatusStat(true);
            					}else if(newCard.id=="PCPRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
            						loadAndInitDeviceTypeStat(true);
            					}
            					Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').setValue('');
        						Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').setRawValue('');
        						var gridPanel = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
        						if (isNotVal(gridPanel)) {
        							gridPanel.getSelectionModel().deselectAll(true);
        							gridPanel.getStore().load();
        						}else{
        							Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringWellListStore');
        						}
            				}
            			}
                    }]
                }, {
                	region: 'east',
                    width: '68%',
                    id:'PCPRealTimeMonitoringEastPanel_Id',
                    autoScroll: true,
                    split: true,
                    collapsible: true,
                    layout: 'border',
                    header: false,
                    items:[{
                        region: 'center',
                        xtype: 'tabpanel',
                		id:"PCPRealTimeMonitoringCurveAndTableTabPanel",
                		activeTab: 0,
                		border: false,
                		tabPosition: 'top',
                		items: [{
                			title:'趋势曲线',
                			id:"PCPRealTimeMonitoringCurveTabPanel_Id",
                			layout: 'border',
                			items: [{
                				region: 'center',
                				layout: 'fit',
                    			autoScroll: true,
                    			border: false,
                    			id:"pcpRealTimeMonitoringCurveContent",
                    			html: '<div id="pcpRealTimeMonitoringCurveContainer" class="hbox" style="width:100%;height:100%;"></div>',
                    			listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	var container=$('#pcpRealTimeMonitoringCurveContainer');
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
//                			layout: 'fit',
//                			html: '<div id="pcpRealTimeMonitoringCurveDiv_Id" style="width:100%;height:100%;"></div>',
//                            listeners: {
//                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
//                                    if ($("#pcpRealTimeMonitoringCurveDiv_Id").highcharts() != undefined) {
//                                        highchartsResize("pcpRealTimeMonitoringCurveDiv_Id");
//                                    }
//                                }
//                            }
                		},{
                			title:'动态数据',
                			id:"PCPRealTimeMonitoringTableTabPanel_Id",
                			layout: 'border',
                            border: false,
                            items: [{
                            	region: 'center',
//                            	title: '实时数据',
                            	header: false,
                            	id: "PCPRealTimeMonitoringInfoDataPanel_Id",
                            	layout: 'fit',
                            	html:'<div class="PCPRealTimeMonitoringInfoDataTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="PCPRealTimeMonitoringInfoDataTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    	if(pcpDeviceRealTimeMonitoringDataHandsontableHelper!=null && pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
                                    		pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot.refreshDimensions();
                                    	}
                                    }
                                }
                            }]
                		}],
                		listeners: {
            				tabchange: function (tabPanel, newCard,oldCard, obj) {
            					var selectRow= Ext.getCmp("PCPRealTimeMonitoringInfoDeviceListSelectRow_Id").getValue();
            					var gridPanel=Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
            					if(newCard.id=="PCPRealTimeMonitoringCurveTabPanel_Id"){
            						if(isNotVal(gridPanel)&&selectRow>=0){
            							deviceRealtimeMonitoringCurve(1);
            						}
            					}else if(newCard.id=="PCPRealTimeMonitoringTableTabPanel_Id"){
                            		if(isNotVal(gridPanel)&&selectRow>=0){
                            			var selectedItem=gridPanel.getStore().getAt(selectRow);
                            			CreatePCPDeviceRealTimeMonitoringDataTable(selectedItem.data.id,selectedItem.data.wellName,1)
                            		}
            					}
            				}
                		}
                    },{
                    	region: 'east',
                    	width: '31%',
                    	xtype: 'tabpanel',
                    	id:"PCPRealTimeMonitoringRightTabPanel",
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
                				id: 'PCPRealTimeMonitoringRightDeviceInfoPanel',
                                border: false,
                                layout: 'fit',
                                autoScroll: true,
                                scrollable: true
                			},{
                				region: 'south',
                				id: 'PCPRealTimeMonitoringRightAuxiliaryDeviceInfoPanel',
                				title:'辅件设备',
                				height: '50%',
                				border: false,
                                layout: 'fit',
                                hidden:true,
                                split: true,
                                collapsible: true,
                                autoScroll: true,
                                scrollable: true
                			}]
                		},{
                			title:'设备控制',
                			border: false,
                            layout: 'border',
                            hideMode:'offsets',
                            id:'PCPRealTimeMonitoringRightControlAndVideoPanel',
                            items: [{
                            	region: 'north',
                            	layout: 'fit',
                            	height: 220,
                            	id:'PCPRealTimeMonitoringRightVideoPanel1',
                            	collapsible: true, // 是否折叠
                            	header: false,
                                split: true, // 竖折叠条
                                autoRender:true,
                                html: '<div id="PCPRealTimeMonitoringRightVideoDiv1_Id" style="width:100%;height:100%;"></div>',
                                listeners: {
                                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                		if(Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                			if(videoPlayrHelper.pcp.player1!=null){
                                        		var isFullScreen = isBrowserFullScreen();
                                        		if(!isFullScreen){
                                    				var recordData=Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data;
                                        			createVideo(1,recordData,1,true);
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
                                	id:'PCPRealTimeMonitoringRightVideoPanel2',
                                	collapsible: true, // 是否折叠
                                	header: false,
                                    split: true, // 竖折叠条
                                    autoRender:true,
                                    html: '<div id="PCPRealTimeMonitoringRightVideoDiv2_Id" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                    	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    		if(Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                    			if(videoPlayrHelper.pcp.player2!=null){
                                            		var isFullScreen = isBrowserFullScreen();
                                            		if(!isFullScreen){
                                        				var recordData=Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data;
                                            			createVideo(1,recordData,2,true);
                                        			}
                                        		}
                                    		}
                                    	}
                                    }
                            	},{
                            		region: 'center',
                                    id: 'PCPRealTimeMonitoringRightControlPanel',
                                    border: false,
                                    layout: 'fit',
                                    autoScroll: true,
                                    scrollable: true
                            	}]
                            }]
                		}],
                		listeners: {
                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                        		if(newCard.id=="PCPRealTimeMonitoringRightControlAndVideoPanel"){
                                	if(Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
                                		createVideo(1,Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data);
                                	}else{
                                		var videoPanel1=Ext.getCmp("PCPRealTimeMonitoringRightVideoPanel1");
                                		var videoPanel2=Ext.getCmp("PCPRealTimeMonitoringRightVideoPanel2");
                                		if(videoPlayrHelper.pcp.player1!=null && videoPlayrHelper.pcp.player1.pluginStatus.state.play){
                                			videoPlayrHelper.pcp.player1.stop();
                                		}
                                		if(videoPlayrHelper.pcp.player2!=null && videoPlayrHelper.pcp.player2.pluginStatus.state.play){
                                			videoPlayrHelper.pcp.player2.stop();
                                		}
                                		videoPanel1.hide();
                                		videoPanel2.hide();
                                	}
                                	
                                	var controlGridPanel=Ext.getCmp("PCPRealTimeMonitoringControlDataGridPanel_Id");
                        			if(isNotVal(controlGridPanel)){
                        				controlGridPanel.getStore().load();
                        			}else{
                        				Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringDeviceControlStore');
                        			}
                        		}else{
                        			if(videoPlayrHelper.pcp.player1!=null && videoPlayrHelper.pcp.player1.pluginStatus.state.play){
                        				videoPlayrHelper.pcp.player1.stop();
                        			}
                        			if(videoPlayrHelper.pcp.player2!=null && videoPlayrHelper.pcp.player2.pluginStatus.state.play){
                        				videoPlayrHelper.pcp.player2.stop();
                        			}
                        			
                        			var deviceInfoGridPanel=Ext.getCmp("PCPRealTimeMonitoringDeviceInfoDataGridPanel_Id");
                        			if(isNotVal(deviceInfoGridPanel)){
                        				deviceInfoGridPanel.getStore().load();
                        			}else{
                        				Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringDeviceInfoStore');
                        			}
                        		}
                            }
                        }
                    }],
                    listeners: {
                        beforeCollapse: function (panel, eOpts) {
                        	var container=$('#pcpRealTimeMonitoringCurveContainer');
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
                        	var container=$('#pcpRealTimeMonitoringCurveContainer');
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

function CreatePCPDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType){
	Ext.Ajax.request({
		method:'POST',
		url:context + '/realTimeMonitoringController/getDeviceRealTimeMonitoringData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			if(pcpDeviceRealTimeMonitoringDataHandsontableHelper==null || pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot==undefined){
				pcpDeviceRealTimeMonitoringDataHandsontableHelper = PCPDeviceRealTimeMonitoringDataHandsontableHelper.createNew("PCPRealTimeMonitoringInfoDataTableInfoDiv_id");
				var colHeaders="['名称','变量','名称','变量','名称','变量']";
				var columns="[" 
						+"{data:'name1'}," 
						+"{data:'value1'}," 
						+"{data:'name2'},"
						+"{data:'value2'}," 
						+"{data:'name3'}," 
						+"{data:'value3'}" 
						+"]";
				pcpDeviceRealTimeMonitoringDataHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				pcpDeviceRealTimeMonitoringDataHandsontableHelper.columns=Ext.JSON.decode(columns);
				pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo=result.CellInfo;
				if(result.totalRoot.length==0){
					pcpDeviceRealTimeMonitoringDataHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					pcpDeviceRealTimeMonitoringDataHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo=result.CellInfo;
				pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(result.totalRoot);
			}
			//添加单元格属性
			for(var i=0;i<pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length;i++){
				var row=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
				var col=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col;
				var column=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].column;
				var columnDataType=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].columnDataType;
				pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot.setCellMeta(row,col,'columnDataType',columnDataType);
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

var PCPDeviceRealTimeMonitoringDataHandsontableHelper = {
		createNew: function (divid) {
	        var pcpDeviceRealTimeMonitoringDataHandsontableHelper = {};
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.divid = divid;
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.validresult=true;//数据校验
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.colHeaders=[];
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.columns=[];
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo=[];
	        
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.addFirstAlarmLevelColBg = function (instance, td, row, col, prop, value, cellProperties) {
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
	        
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.addSecondAlarmLevelColBg = function (instance, td, row, col, prop, value, cellProperties) {
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
	        
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.addThirdAlarmLevelColBg = function (instance, td, row, col, prop, value, cellProperties) {
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
	        
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = '#E6E6E6';
	        }
	        
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.addItenmNameColStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.fontWeight = 'bold';
	        }
	        
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	td.style.fontWeight = 'bold';
		        td.style.fontSize = '20px';
		        td.style.fontFamily = 'SimSun';
		        td.style.height = '40px';
	        }
	        
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
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
	            for(var i=0;i<pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length;i++){
                	if(pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel>=0){
                		var row2=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
        				var col2=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col*2+1;
        				if(row==row2 && col==col2 ){
        					if(pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel>0){
        						td.style.fontWeight = 'bold';
       			             	td.style.fontFamily = 'SimHei';
        					}
   			             	if(pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==0){
			             		if(AlarmShowStyle.Data.Normal.Opacity!=0){
			             			td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.Normal.BackgroundColor,AlarmShowStyle.Data.Normal.Opacity);
			             		}
			             		td.style.color='#'+AlarmShowStyle.Data.Normal.Color;
			             	}else if(pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==100){
        						if(AlarmShowStyle.Data.FirstLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.FirstLevel.BackgroundColor,AlarmShowStyle.Data.FirstLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.FirstLevel.Color;
        					}else if(pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==200){
        						if(AlarmShowStyle.Data.SecondLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.SecondLevel.BackgroundColor,AlarmShowStyle.Data.SecondLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.SecondLevel.Color;
        					}else if(pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel==300){
        						if(AlarmShowStyle.Data.ThirdLevel.Opacity!=0){
        							td.style.backgroundColor=color16ToRgba('#'+AlarmShowStyle.Data.ThirdLevel.BackgroundColor,AlarmShowStyle.Data.ThirdLevel.Opacity);
        						}
        						td.style.color='#'+AlarmShowStyle.Data.ThirdLevel.Color;
        					}
        				}
                	}
    			}
	        }
	        
	        pcpDeviceRealTimeMonitoringDataHandsontableHelper.createTable = function (data) {
	        	$('#'+pcpDeviceRealTimeMonitoringDataHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+pcpDeviceRealTimeMonitoringDataHandsontableHelper.divid);
	        	pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [30,20,30,20,30,20],
	                columns:pcpDeviceRealTimeMonitoringDataHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                rowHeaders: false,//显示行头
	                colHeaders: false,
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
	                    cellProperties.renderer = pcpDeviceRealTimeMonitoringDataHandsontableHelper.addCellStyle;
	                    
	                    cellProperties.readOnly = true;
	                    return cellProperties;
	                },
	                afterSelectionEnd : function (row,column,row2,column2, preventScrolling,selectionLayerLevel) {
//	                	if(row>0||column>0){
//	                		var relRow=row;
//	                		var relColumn=column;
//	                		if(column%2==1){
//	                			relColumn=column-1;
//	                		}else if(column%2==0){
//	                			
//	                		}
//		                	
//		                	var item=pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell(relRow,relColumn);
//		                	var selectecCell=pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot.getCell(relRow,relColumn);
//		                	var columnDataType='';
//		                	var resolutionMode=0;
//		                	for(var i=0;i<pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length;i++){
//		        				if(relRow==pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row && relColumn==pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col*2){
//		        					item=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].columnName;
//		        					columnDataType=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].columnDataType;
//		        					resolutionMode=pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].resolutionMode;
//		        					break;
//		        				}
//		        			}
//		                	
//		                	if(isNotVal(item)&&resolutionMode==2){
//		                		Ext.getCmp("PCPRealTimeMonitoringSelectedCurve_Id").setValue(item);
//			                	pcpRealTimeMonitoringCurve(item);
//		                	}
//	                	}
	                }
	        	});
	        }
	        return pcpDeviceRealTimeMonitoringDataHandsontableHelper;
	    }
};
function pcpRealTimeMonitoringCurve(item){
	var gridPanel=Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id")
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
			    initTimeAndDataCurveChartFn(ser, tickInterval, "pcpRealTimeMonitoringCurveDiv_Id", title, '', xTitle, yTitle, color,false,'%H:%M:%S');
			},
			failure:function(){
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			},
			params: {
				deviceName:deviceName,
				item:item,
				deviceType:1
	        }
		});
	}
}