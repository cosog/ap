var historyStatTabItems=[{
	title:'工况诊断',
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
                        html: '点击饼图不同区域或标签，查看相应统计数据'
                    });
            	}
            }
        }
    }
},{
	title:'运行状态',
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
                        html: '点击饼图不同区域或标签，查看相应统计数据'
                    });
            	}
            }
        }
    }
},{
	title:'通信状态',
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
                        html: '点击饼图不同区域或标签，查看相应统计数据'
                    });
            	}
            }
        }
    }
},{
	title:'设备类型',
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
                        html: '点击饼图不同区域或标签，查看相应统计数据'
                    });
            	}
            }
        }
    }
}];

var historyQueryCenterTabPanelItems=[{
	title: '趋势曲线',
	id:"HistoryDataTabPanel",
    layout: 'border',
    border: false,
    iconCls: 'check3',
    items: [{
    	region: 'north',
    	height: '50%',
    	title: '历史曲线',
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
    	title: '历史数据',
    	header: false,
    	id: "HistoryQueryDataInfoPanel_Id",
    	layout: 'fit',
    	border: true
    }]
},{
	title: '图形平铺',
	id:"HistoryDiagramTabPanel",
	hidden:onlyMonitor,
	border: false,
    layout: "fit",
    autoScroll: true,
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
                    var HistoryDiagramTabPanel = Ext.getCmp("HistoryDiagramTabPanel");
                    var hRatio = HistoryDiagramTabPanel.getScrollY() / Ext.get("surfaceCardContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                    if (hRatio > 0.5) {
//                        if (diagramPage < 2) {
//                            diagramPage++;
//                            loadSurfaceCardList(diagramPage);
//                        } else {
//                            var divCount = $("#surfaceCardContainer div ").size();
//                            var count = (diagramPage - 1) * defaultGraghSize * 3;
//                            if (divCount > count) {
//                                diagramPage++;
//                                loadSurfaceCardList(diagramPage);
//                            }
//                        }
                        diagramPage++;
                        loadSurfaceCardList(diagramPage);
                    }
                }
            }, this);
        }
    }
},{
	title: '图形叠加',
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
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
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
                        title:'设备列表',
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
                            text: cosog.string.refresh,
                            iconCls: 'note-refresh',
                            hidden:false,
                            handler: function (v, o) {
                            	historyDataRefresh();
                    		}
                		},'-',deviceListCombo,'-', {
                            xtype: 'button',
                            text: cosog.string.exportExcel,
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
                           	 	var fileName='抽油机井历史数据设备列表';
                           	 	var title='抽油机井历史数据设备列表';
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
                        fieldLabel: '区间',
                        labelWidth: 30,
                        width: 130,
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
                        fieldLabel: '时',
                        labelWidth: 15,
                        width: 60,
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
                        			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryStartTime_Minute_Id',
                        fieldLabel: '分',
                        labelWidth: 15,
                        width: 60,
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
                        			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryStartTime_Second_Id',
                        fieldLabel: '秒',
                        labelWidth: 15,
                        width: 60,
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
                        			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                        xtype: 'datefield',
                        anchor: '100%',
                        fieldLabel: '至',
                        labelWidth: 15,
                        width: 115,
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
                        fieldLabel: '时',
                        labelWidth: 15,
                        width: 60,
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
                        			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryEndTime_Minute_Id',
                        fieldLabel: '分',
                        labelWidth: 15,
                        width: 60,
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
                        			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },{
                    	xtype: 'numberfield',
                    	id: 'HistoryQueryEndTime_Second_Id',
                        fieldLabel: '秒',
                        labelWidth: 15,
                        width: 60,
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
                        			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                        			field.focus(true, 100);
                        		}
                            }
                        }
                    },'-',{
                        xtype: 'button',
                        text: cosog.string.search,
                        iconCls: 'search',
                        handler: function () {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        		Ext.getCmp('HistoryQueryEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
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
        						loadSurfaceCardList(1);
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
                        text: cosog.string.exportExcel,
                        iconCls: 'export',
                        id:'HistoryDiagramOverlayExportBtn_Id',
                        hidden:true,
                        handler: function (v, o) {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        		Ext.getCmp('HistoryQueryEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
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
                       	 	var fileName='抽油机井'+deviceName+'功图叠加数据';
                       	 	var title='抽油机井'+deviceName+'功图叠加数据';
                       	 	var columnStr=Ext.getCmp("HistoryQueryDiagramOverlayColumnStr_Id").getValue();
                       	 	exportHistoryQueryDiagramOverlayDataExcel(orgId,deviceType,deviceId,deviceName,calculateType,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),fileName,title,columnStr);
                        }
                    }, {
                        xtype: 'button',
                        text: cosog.string.exportExcel,
                        iconCls: 'export',
                        id:'HistoryFESDiagramDataExportBtn_Id',
                        hidden:true,
                        handler: function (v, o) {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        		Ext.getCmp('HistoryQueryEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
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
                       	 	var fileName='抽油机井'+deviceName+'功图数据';
                       	 	var title='抽油机井'+deviceName+'功图数据';
                       	 	exportHistoryQueryFESDiagramDataExcel(orgId,deviceType,deviceId,deviceName,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),fileName,title);
                        }
                    }, {
                        xtype: 'button',
                        text: cosog.string.exportExcel,
                        iconCls: 'export',
                        id:'HistoryDataExportBtn_Id',
                        hidden:false,
                        handler: function (v, o) {
                        	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                        	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                        	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
                        	if(!r.test(startTime_Hour)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
                        	if(!r2.test(startTime_Minute)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();
                        	if(!r2.test(startTime_Second)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryStartTime_Second_Id').focus(true, 100);
                        		return;
                        	}
                        	
                        	var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
                        	if(!r.test(endTime_Hour)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                        		Ext.getCmp('HistoryQueryEndTime_Hour_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
                        	if(!r2.test(endTime_Minute)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                        		Ext.getCmp('HistoryQueryEndTime_Minute_Id').focus(true, 100);
                        		return;
                        	}
                        	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
                        	if(!r2.test(endTime_Second)){
                        		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
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
                       	 	var fileName='抽油机井'+deviceName+'历史数据';
                       	 	var title='抽油机井'+deviceName+'历史数据';
                       	 	var columnStr=Ext.getCmp("HistoryQueryDataColumnStr_Id").getValue();
                       	 	exportHistoryQueryDataExcel(orgId,deviceType,deviceId,deviceName,calculateType,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),fileName,title,columnStr);
                        }
                    },'->',{
                        id: 'SurfaceCardTotalCount_Id',
                        xtype: 'component',
                        tpl: cosog.string.totalCount + ': {count}', // 总记录数
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
        						Ext.getCmp("SurfaceCardTotalCount_Id").hide();
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
        						loadSurfaceCardList(1);
        					}else if(newCard.id=="HistoryDiagramOverlayTabPanel"){
        						Ext.getCmp("HistoryDiagramOverlayExportBtn_Id").show();
        						Ext.getCmp("HistoryFESDiagramDataExportBtn_Id").hide();
        						Ext.getCmp("HistoryDataExportBtn_Id").hide();
        						Ext.getCmp("SurfaceCardTotalCount_Id").hide();
        						
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

loadSurfaceCardList = function (page) {
	diagramPage=page;
    Ext.getCmp("HistoryDiagramTabPanel").mask(cosog.string.loading); // 数据加载中，请稍后
    var start = (page - 1) * defaultGraghSize;
    page=page;
    if(page==1){
    	$("#surfaceCardContainer").html(''); // 将html内容清空
    }
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceName='';
	var deviceId=0;
	var selectRow= Ext.getCmp("HistoryQueryInfoDeviceListSelectRow_Id").getValue();
	if(selectRow>=0){
		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
	}
	var startDate=Ext.getCmp('HistoryQueryStartDate_Id').rawValue;
	var startTime_Hour=Ext.getCmp('HistoryQueryStartTime_Hour_Id').getValue();
	var startTime_Minute=Ext.getCmp('HistoryQueryStartTime_Minute_Id').getValue();
	var startTime_Second=Ext.getCmp('HistoryQueryStartTime_Second_Id').getValue();

    var endDate=Ext.getCmp('HistoryQueryEndDate_Id').rawValue;
    var endTime_Hour=Ext.getCmp('HistoryQueryEndTime_Hour_Id').getValue();
	var endTime_Minute=Ext.getCmp('HistoryQueryEndTime_Minute_Id').getValue();
	var endTime_Second=Ext.getCmp('HistoryQueryEndTime_Second_Id').getValue();
    Ext.Ajax.request({
        url: context + '/historyQueryController/querySurfaceCard',
        method: "POST",
        params: {
        	orgId: orgId,
    		deviceType:getDeviceTypeFromTabId("HistoryQueryRootTabPanel"),
    		deviceId:deviceId,
            deviceName:deviceName,
            startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
            endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
            limit: defaultGraghSize,
            start: start,
            page: page
        },
        success: function (response) {
        	if(page==1){
        		$("#surfaceCardContainer").html(''); // 将html内容清空
        	}
            Ext.getCmp("HistoryDiagramTabPanel").unmask(cosog.string.loading); // 取消遮罩
            var get_rawData = Ext.decode(response.responseText); // 获取返回数据
            var gtlist = get_rawData.list; // 获取功图数据
            
            var totals = get_rawData.totals; // 总记录数
            var totalPages = get_rawData.totalPages; // 总页数
            Ext.getCmp("SurfaceCardTotalPages_Id").setValue(totalPages);
            updateTotalRecords(totals,"SurfaceCardTotalCount_Id");
            
            var startDate=Ext.getCmp('HistoryQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('HistoryQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('HistoryQueryStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('HistoryQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('HistoryQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('HistoryQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('HistoryQueryEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
            
            
            var HistoryDiagramTabPanel = Ext.getCmp("HistoryDiagramTabPanel"); // 获取功图列表panel信息
            var panelHeight = HistoryDiagramTabPanel.getHeight(); // panel的高度
            var panelWidth = HistoryDiagramTabPanel.getWidth(); // panel的宽度
            var scrollWidth = getScrollWidth(); // 滚动条的宽度
            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
            var gtWidth = (panelWidth - scrollWidth) / columnCount; // 有滚动条时图形宽度
            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
            var gtWidth2 = gtWidth + 'px';
            var gtHeight2 = gtHeight + 'px';
            gtWidth2 = (100/columnCount) + '%';
            gtHeight2 = 50 + '%';
            var htmlResult = '';
            var divId = '';

            // 功图列表，创建div
            Ext.Array.each(gtlist, function (name, index, countriesItSelf) {
                var gtId = gtlist[index].id;
                divId = 'gt' + gtId;
                htmlResult += '<div id=\"' + divId + '\"';
                htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';"';
                htmlResult += '></div>';
            });
            $("#surfaceCardContainer").append(htmlResult);
            Ext.Array.each(gtlist, function (name, index, countriesItSelf) {
                var gtId = gtlist[index].id;
                divId = 'gt' + gtId;
                showSurfaceCard(gtlist[index], divId); // 调用画功图的函数，功图列表
            });
        },
        failure: function () {
            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
        }
    });
};

function createHistoryQueryDiagramOverlayTableColumn(columnInfo) {
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
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:true";
        }
        else if (attr.dataIndex.toUpperCase()=='deviceName'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } 
        else if (attr.dataIndex.toUpperCase()=='resultName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceResultStatusColor(value,o,p,e);}";
        }
        else {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRealtimeMonitoringDataColor(value,o,p,e);}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};
