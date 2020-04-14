/*******************************************************************************
 * 螺杆泵实时评价视图
 *
 * @author zhao
 *
 */
Ext.define("AP.view.diagnosis.PCPSingleDetailsInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.PCPSingleDetailsInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var wellCombStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
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
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
                    var new_params = {
                    	wellName: wellName,
                        orgId: org_Id,
                        wellType: 400
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellComb = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.wellName,
            id: "ScrewPumpRealtimeAnalysisWellCom_Id",
            store: wellCombStore,
            labelWidth: 35,
            width: 125,
            queryMode: 'remote',
            typeAhead: true,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            pageSize: comboxPagingStatus,
            minChars: 0,
            multiSelect: false,
            listeners: {
                expand: function (sm, selections) {
                	wellComb.getStore().loadPage(1);
                },
                select: function (combo, record, index) {
                	var statPanelId=getPCPRPMAnalysisSingleStatType().piePanelId;;
                    if(combo.value==""){
                    	Ext.getCmp("ScrewPumpRealtimeAnalysisWellListPanel_Id").setTitle("统计数据");
                    	Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").hide();
                  	  	Ext.getCmp("ScrewPumpRealtimeAnalysisEndDate_Id").hide();
                        Ext.getCmp("ScrewPumpRealtimeAnalysisHisBtn_Id").show();
                  	  	Ext.getCmp("ScrewPumpRealtimeAnalysisAllBtn_Id").hide();
                    	Ext.getCmp(statPanelId).expand(true);
                    }else{
                    	Ext.getCmp("ScrewPumpRealtimeAnalysisWellListPanel_Id").setTitle("历史数据");
            			Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").show();
                    	Ext.getCmp("ScrewPumpRealtimeAnalysisEndDate_Id").show();
                    	Ext.getCmp("ScrewPumpRealtimeAnalysisHisBtn_Id").hide();
                        Ext.getCmp("ScrewPumpRealtimeAnalysisAllBtn_Id").show();
                    	Ext.getCmp(statPanelId).collapse();
                    }
                    Ext.getCmp('ScrewPumpRTAnalysisWellList_Id').getStore().loadPage(1);
                }
            }
        });
        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                items: [
                    {
                        region: 'center',
                        layout: 'fit',
                        id: 'ScrewPumpRealtimeAnalysisWellListPanel_Id',
                        title: '统计数据',
                        border: false,
                        tbar: [wellComb, '-',{
                            id: 'PCPRPMAnalysisSingleDetailsSelectedStatValue_Id',//选择的统计项的值
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        }, {
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: '',
                            labelWidth: 0,
                            width: 90,
                            format: 'Y-m-d ',
                            id: 'ScrewPumpRealtimeAnalysisStartDate_Id',
                            value: '',
                            listeners: {
                                select: function (combo, record, index) {
                                	Ext.getCmp('ScrewPumpRTAnalysisWellList_Id').getStore().loadPage(1);
                                }
                            }
                        }, {
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: '至',
                            labelWidth: 15,
                            width: 105,
                            format: 'Y-m-d ',
                            id: 'ScrewPumpRealtimeAnalysisEndDate_Id',
                            value: new Date(),
                            listeners: {
                                select: function (combo, record, index) {
                                	Ext.getCmp('ScrewPumpRTAnalysisWellList_Id').getStore().loadPage(1);
                                }
                            }
                        }, {
                            xtype: 'button',
                            text: cosog.string.exportExcel,
                            pressed: true,
                            handler: function (v, o) {
                            	exportScrewPumpRTAnalisiDataExcel();
                            }
                        }, '->', {
                            xtype: 'button',
                            text: '查看历史',
                            tooltip: '点击按钮或者双击表格，查看单井历史数据',
                            id: 'ScrewPumpRealtimeAnalysisHisBtn_Id',
                            pressed: true,
                            hidden: false,
                            handler: function (v, o) {
                            	var statPanelId=getPCPRPMAnalysisSingleStatType().piePanelId;
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisWellListPanel_Id").setTitle("历史数据");
                    			Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").show();
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisEndDate_Id").show();
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisHisBtn_Id").hide();
                                Ext.getCmp("ScrewPumpRealtimeAnalysisAllBtn_Id").show();
                            	Ext.getCmp(statPanelId).collapse();
                                
                                var wellName  = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.wellName;
                                Ext.getCmp("ScrewPumpRealtimeAnalysisWellCom_Id").setValue(wellName);
                                Ext.getCmp("ScrewPumpRealtimeAnalysisWellCom_Id").setRawValue(wellName);
                                Ext.getCmp('ScrewPumpRTAnalysisWellList_Id').getStore().loadPage(1);
                            }
                      }, {
                            xtype: 'button',
                            text: '返回统计',
                            id: 'ScrewPumpRealtimeAnalysisAllBtn_Id',
                            pressed: true,
                            hidden: true,
                            handler: function (v, o) {
                            	var statPanelId=getPCPRPMAnalysisSingleStatType().piePanelId;
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisWellListPanel_Id").setTitle("统计数据");
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").hide();
                          	  	Ext.getCmp("ScrewPumpRealtimeAnalysisEndDate_Id").hide();
                                Ext.getCmp("ScrewPumpRealtimeAnalysisHisBtn_Id").show();
                          	  	Ext.getCmp("ScrewPumpRealtimeAnalysisAllBtn_Id").hide();
                            	Ext.getCmp(statPanelId).expand(true);
                                
                                Ext.getCmp("ScrewPumpRealtimeAnalysisWellCom_Id").setValue('');
                                Ext.getCmp("ScrewPumpRealtimeAnalysisWellCom_Id").setRawValue('');
                                Ext.getCmp('ScrewPumpRTAnalysisWellList_Id').getStore().loadPage(1);
                            }
                      }, {
                            id: 'ScrewPumpRealtimeAnalysisCount_Id',
                            xtype: 'component',
                            hidden: true,
                            tpl: cosog.string.totalCount + ': {count}',
                            style: 'margin-right:15px'
                    }],
                        items: {
                            xtype: 'tabpanel',
                            id: 'PCPRPMAnalysisSingleDetailsStatTabpanel_Id',
                            activeTab: 0,
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            tabPosition: 'top',
                            items: [{
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '工况',
                                    iconCls: 'select',
                                    id: 'PCPRPMAnalysisSingleWorkCondStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '电参工况',
                                        border: false,
                                        layout: 'border',
                                        iconCls: 'dtgreen',
                                        id: 'PCPRPMAnalysisSingleElecWorkCondStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisSingleElecWorkCondDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisSingleElecWorkCondStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisSingleElecWorkCondStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisSingleElecWorkCondStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisSingleElecWorkCondStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisSingleElecWorkCondStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    }],
                                    listeners: {
                                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                                            newCard.setIconCls("dtgreen");
                                            oldCard.setIconCls("");
                                            loadPCPRPMAnalysisSingleStatData();
                                        }
                                    }
                            }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '产量',
                                    id: 'PCPRPMAnalysisSingleProdStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '产量分布',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisSingleProdStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisSingleProdDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisSingleProdStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisSingleProdStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisSingleProdStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisSingleProdStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisSingleProdStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                 }],
                                    listeners: {
                                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                                            newCard.setIconCls("dtgreen");
                                            oldCard.setIconCls("");
                                            loadPCPRPMAnalysisSingleStatData();
                                        }
                                    }
                                },{
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '时率',
                                    id: 'PCPRPMAnalysisSingleRunTimeEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '运行状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisSingleRunStatusStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisSingleRunStatusDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisSingleRunStatusStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisSingleRunStatusStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisSingleRunStatusStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisSingleRunStatusStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisSingleRunStatusStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    }, {
                                        title: '运行时率',
                                        border: false,
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisSingleRunTimeEffStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisSingleRunTimeEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisSingleRunTimeEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisSingleRunTimeEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisSingleRunTimeEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisSingleRunTimeEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisSingleRunTimeEffStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    }],
                                    listeners: {
                                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                                            newCard.setIconCls("dtgreen");
                                            oldCard.setIconCls("");
                                            loadPCPRPMAnalysisSingleStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '效率',
                                    id: 'PCPRPMAnalysisSingleSysEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '系统效率',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisSingleSysEffStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisSingleSysEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisSingleSysEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisSingleSysEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisSingleSysEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisSingleSysEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisSingleSysEffStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }

                                            }
                                        }]
                                    }],
                                    listeners: {
                                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                                            newCard.setIconCls("dtgreen");
                                            oldCard.setIconCls("");
                                            loadPCPRPMAnalysisSingleStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '电量',
                                    id: 'PCPRPMAnalysisSingleEnergyStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '日用电量',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisSingleTodayEnergyStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisSingleTodayEnergyDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisSingleTodayEnergyStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisSingleTodayEnergyStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisSingleTodayEnergyStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisSingleTodayEnergyStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisSingleTodayEnergyStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                 }]
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '通信',
                                    id: 'PCPRPMAnalysisSingleCommEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '通信状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisSingleCommStatusStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisSingleCommStatusDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisSingleCommStatusStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisSingleCommStatusStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisSingleCommStatusStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisSingleCommStatusStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisSingleCommStatusStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    }, {
                                        title: '在线时率',
                                        border: false,
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisSingleCommEffStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisSingleCommEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisSingleCommEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisSingleCommEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisSingleCommEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisSingleCommEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisSingleCommEffStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    }],
                                    listeners: {
                                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                                            newCard.setIconCls("dtgreen");
                                            oldCard.setIconCls("");
                                            loadPCPRPMAnalysisSingleStatData();
                                        }
                                    }
                                }
                            ],
                            listeners: {
                                tabchange: function (tabPanel, newCard, oldCard, obj) {
                                    oldCard.setIconCls("");
                                    newCard.setIconCls("select");
                                    loadPCPRPMAnalysisSingleStatData();
                                }
                            }
                        },
                        listeners: {
                            afterrender: function (comp, eOpts) {
                                Ext.getCmp("PCPRPMAnalysisSingleWorkCondStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisSingleProdStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisSingleRunTimeEffStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisSingleSysEffStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisSingleEnergyStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisSingleCommEffStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                            }
                        }
                },
                    {
                        region: 'east',
                        id: 'ScrewPumpRealtimeAnalysisDataPanel_Id',
                        width: '65%',
                        title: '单井数据',
                        collapsible: true, // 是否折叠
                        split: true, // 竖折叠条
                        border: false,
                        layout: {
                            type: 'hbox',
                            pack: 'start',
                            align: 'stretch'
                        },
                        items: [
                            {
                                border: false,
                                flex: 2,
                                height: 900,
                                margin: '0 0 0 0',
                                padding: 0,
                                autoScroll: true,
                                scrollable: true,
                                layout: {
                                    type: 'hbox',
                                    pack: 'start',
                                    align: 'stretch'
                                },
                                items: [
                                    {
                                        border: false,
                                        margin: '0 0 0 0',
                                        flex: 1,
                                        height: 900,
                                        autoScroll: true,
                                        scrollable: true,
                                        layout: {
                                            type: 'vbox',
                                            pack: 'start',
                                            align: 'stretch'
                                        },
                                        items: [{
                                            border: false,
                                            margin: '0 0 1 0',
                                            height: 1000,
                                            layout: 'fit',
                                            id: 'ScrewPumpRTCurveDataPanel_Id',
                                            html: '<div id="ScrewPumpRTCurveDataChartDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRTCurveDataChartDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRTCurveDataChartDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }
                                                }
                                            }
                                     }]
                                 }
                                ]
                            },
                            {
                                border: false,
                                flex: 1,
                                height: 900,
                                margin: '0 0 0 0',
                                padding: 0,
                                autoScroll: true,
                                scrollable: true,
                                collapsible: true,
                                header: false,
                                collapseDirection: 'right',
                                split: true,
                                layout: {
                                    type: 'hbox',
                                    pack: 'start',
                                    align: 'stretch'
                                },
                                items: [
                                    {
                                        xtype: 'tabpanel',
                                        id: 'ScrewPumpRealtimeAnalysisAndAcqDataTabpanel_Id',
                                        flex: 1,
                                        activeTab: 0,
                                        header: false,
                                        collapsible: true,
                                        split: true,
                                        collapseDirection: 'right',
                                        border: true,
                                        tabPosition: 'top',
                                        items: [
                                            {
                                                title: '分析',
                                                id: 'ScrewPumpRTAnalysisTableCalDataPanel_Id',
                                                border: false,
                                                layout: 'fit',
                                                autoScroll: true,
                                                scrollable: true
                                            }, {
                                                title: '采集',
                                                id: 'ScrewPumpRTAnalysisTableAcqDataPanel_Id',
                                                border: false,
                                                layout: 'fit',
                                                autoScroll: true,
                                                scrollable: true
                                            },{
                                				title:'控制',
                                				border: false,
                                                layout: 'border',
                                                hideMode:'offsets',
                                                id:'ScrewPumpRTAnalysisTableControlDataPanel_Id',
                                                items: [{
                                                	region: 'north',
                                                	layout: 'fit',
                                                	height: '40%',
                                                	id:'ScrewPumpRTAnalysisControlVideoPanel_Id',
                                                	collapsible: true, // 是否折叠
                                                	header: false,
                                                    split: true, // 竖折叠条
                                                    autoRender:true,
                                                	html: ''
                                                },{
                                                	region: 'center',
                                                    height: '60%',
                                                    id:'ScrewPumpRTAnalysisControlDataPanel_Id',
                                    				border: false,
                                    				autoScroll:true,
                                                    scrollable: true,
                                                    layout: 'fit',
                                                    listeners: {
                                                    	resize: function (abstractcomponent, adjWidth, adjHeight, options) {}
                                                    }
                                                }]
                                			}],
                                			listeners: {
                                            	tabchange: function (tabPanel, newCard, oldCard,obj) {
                                            		var selectedLength=Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection().length;
                                            		if(newCard.id=="ScrewPumpRTAnalysisTableControlDataPanel_Id"){
                                            			if(selectedLength>0){
                                            				var videoUrl=Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.videourl;
                                            				if(videoUrl!=undefined&&videoUrl!=""){
                                            					var videoUrl_rtmp=""; 
                                            	            	var videoUrl_hls=""; 
                                            	            	if(videoUrl.indexOf("http")>=0){//hls模式
                                            	            		videoUrl_hls=videoUrl;
                                            	            		videoUrl_rtmp=videoUrl.replace("https","http").replace("http://hls","rtmp://rtmp").replace(".m3u8","");
                                            	            	}else{
                                            	            		videoUrl_hls=videoUrl.replace("rtmp://rtmp","http://hls")+".m3u8";
                                            	            		videoUrl_rtmp=videoUrl;
                                            	            	}
                                            	        		
                                            	        		
                                            	            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").expand(true);
                                            	            	var videohtml='<video id="ScrewPumpRTAnalysisControlVideoDiv_Id" style="width:100%;height:100%;"  poster="" controls playsInline webkit-playsinline autoplay><source src="'+videoUrl_rtmp+'" type="rtmp/flv" /><source src="'+videoUrl_hls+'" type="application/x-mpegURL" /></video>';   
                                            	            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").update(videohtml);
                                            	            	if(document.getElementById("ScrewPumpRTAnalysisControlVideoDiv_Id")!=null){
                                            	            		var player = new EZUIPlayer('ScrewPumpRTAnalysisControlVideoDiv_Id');
                                            	            	}
                                            				}else{
                                            	            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").update('');
                                            	            	Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").collapse();
                                            	            }
                                            			}else{
                                            				Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").removeAll();
                                            				if($("#ScrewPumpRTAnalysisControlVideoDiv_Id")!=null){
                                                        		$("#ScrewPumpRTAnalysisControlVideoDiv_Id").html('');
                                                        	}
                                            			}
                                            			
                                            		}else{
                                            			Ext.getCmp("ScrewPumpRTAnalysisControlVideoPanel_Id").update('');
                                            		}
                                                }
                                            }
                                    }
                                ]
                            }
                        ],
                        listeners: {
                            beforeCollapse: function (panel, eOpts) {
                                $("#ScrewPumpRTCurveDataChartDiv_Id").hide();
                            },
                            expand: function (panel, eOpts) {
                                $("#ScrewPumpRTCurveDataChartDiv_Id").show();
                            }
                        }
                        }]
                }]
        });
        me.callParent(arguments);
    }
});

function getPCPRPMAnalysisSingleStatType() {
	var type=1;
	var panelId="PCPRPMAnalysisSingleElecWorkCondDataListPanel_Id";
	var piePanelId="PCPRPMAnalysisSingleElecWorkCondStatGraphPanel_Id";
	var pieDivId="PCPRPMAnalysisSingleElecWorkCondStatGraphPieDiv_Id";
	var pieChartTitle="电参工况";
	var exportExcelTitle='螺杆泵实时评价-电参工况';
	var activeTabId= Ext.getCmp(Ext.getCmp("PCPRPMAnalysisSingleDetailsStatTabpanel_Id").getActiveTab().id).getActiveTab().id;
	if(activeTabId=="PCPRPMAnalysisSingleElecWorkCondStatPanel_Id"){//电参工况
		type=1;
		panelId="PCPRPMAnalysisSingleElecWorkCondDataListPanel_Id";
		piePanelId="PCPRPMAnalysisSingleElecWorkCondStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisSingleElecWorkCondStatGraphPieDiv_Id";
		pieChartTitle="电参工况";
		exportExcelTitle='螺杆泵实时评价-电参工况';
	}else if(activeTabId=="PCPRPMAnalysisSingleProdStatPanel_Id"){//产量
		type=2;
		panelId="PCPRPMAnalysisSingleProdDataListPanel_Id";
		piePanelId="PCPRPMAnalysisSingleProdStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisSingleProdStatGraphPieDiv_Id";
		pieChartTitle="产量分布";
		exportExcelTitle='螺杆泵实时评价-产量分布';
	}else if(activeTabId=="PCPRPMAnalysisSingleRunStatusStatPanel_Id"){//运行状态
		type=5;
		panelId="PCPRPMAnalysisSingleRunStatusDataListPanel_Id";
		piePanelId="PCPRPMAnalysisSingleRunStatusStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisSingleRunStatusStatGraphPieDiv_Id";
		pieChartTitle="运行状态";
		exportExcelTitle='螺杆泵实时评价-运行状态';
	}else if(activeTabId=="PCPRPMAnalysisSingleRunTimeEffStatPanel_Id"){//运行时率
		type=6;
		panelId="PCPRPMAnalysisSingleRunTimeEffDataListPanel_Id";
		piePanelId="PCPRPMAnalysisSingleRunTimeEffStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisSingleRunTimeEffStatGraphPieDiv_Id";
		pieChartTitle="运行时率";
		exportExcelTitle='螺杆泵实时评价-运行时率';
	}else if(activeTabId=="PCPRPMAnalysisSingleSysEffStatPanel_Id"){//系统效率
		type=7;
		panelId="PCPRPMAnalysisSingleSysEffDataListPanel_Id";
		piePanelId="PCPRPMAnalysisSingleSysEffStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisSingleSysEffStatGraphPieDiv_Id";
		pieChartTitle="系统效率";
		exportExcelTitle='螺杆泵实时评价-系统效率';
	}else if(activeTabId=="PCPRPMAnalysisSingleTodayEnergyStatPanel_Id"){//日用电量
		type=10;
		panelId="PCPRPMAnalysisSingleTodayEnergyDataListPanel_Id";
		piePanelId="PCPRPMAnalysisSingleTodayEnergyStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisSingleTodayEnergyStatGraphPieDiv_Id";
		pieChartTitle="日用电量";
		exportExcelTitle='螺杆泵实时评价-日用电量';
	}else if(activeTabId=="PCPRPMAnalysisSingleCommStatusStatPanel_Id"){//通信状态
		type=11;
		panelId="PCPRPMAnalysisSingleCommStatusDataListPanel_Id";
		piePanelId="PCPRPMAnalysisSingleCommStatusStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisSingleCommStatusStatGraphPieDiv_Id";
		pieChartTitle="通信状态";
		exportExcelTitle='螺杆泵实时评价-通信状态';
	}else if(activeTabId=="PCPRPMAnalysisSingleCommEffStatPanel_Id"){//在线时率
		type=12;
		panelId="PCPRPMAnalysisSingleCommEffDataListPanel_Id";
		piePanelId="PCPRPMAnalysisSingleCommEffStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisSingleCommEffStatGraphPieDiv_Id";
		pieChartTitle="在线时率";
		exportExcelTitle='螺杆泵实时评价-在线时率';
	}
	var result=Ext.JSON.decode("{\"type\":"+type+",\"panelId\":\""+panelId+"\",\"piePanelId\":\""+piePanelId+"\",\"pieDivId\":\""+pieDivId+"\",\"pieChartTitle\":\""+pieChartTitle+"\",\"exportExcelTitle\":\""+exportExcelTitle+"\"}");
	return result;
}

function initScrewPumpRTStatPieChat(store) {
	var divid=getPCPRPMAnalysisSingleStatType().pieDivId;
	var title=getPCPRPMAnalysisSingleStatType().pieChartTitle;
	var get_rawData = store.proxy.reader.rawData;
	var datalist=get_rawData.List;
	
	var pieDataStr="[";
	for(var i=0;i<datalist.length;i++){
		pieDataStr+="['"+datalist[i].item+"',"+datalist[i].count+"]";
		if(i<datalist.length-1){
			pieDataStr+=",";
		}
	}
	pieDataStr+="]";
	var pieData = Ext.JSON.decode(pieDataStr);
	ShowScrewPumpRTStatPieChat(title,divid, "井数占", pieData);
}


function ShowScrewPumpRTStatPieChat(title,divid, name, data) {
	$('#'+divid).highcharts({
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
		colors : ['#058DC7', '#50B432', '#ED561B', '#24CBE5', '#64E572',
				'#FF9655', '#FFF263', '#6AF9C4', '#DDDF00'],
		tooltip : {
			pointFormat : '井数: <b>{point.y}</b> 占: <b>{point.percentage:.1f}%</b>'
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
					format : '<b>{point.name}</b>: {point.y}口'
				},
				events: {
					click: function(e) {
						if(!e.point.selected){//如果没被选中
							Ext.getCmp('PCPRPMAnalysisSingleDetailsSelectedStatValue_Id').setValue(e.point.name);
						}else{
							Ext.getCmp('PCPRPMAnalysisSingleDetailsSelectedStatValue_Id').setValue("");
						}
						Ext.getCmp("ScrewPumpRealtimeAnalysisWellCom_Id").setValue("");
	            		Ext.getCmp("ScrewPumpRealtimeAnalysisWellCom_Id").setRawValue("");
	            		var gridPanel = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id");
	                    if (isNotVal(gridPanel)) {
	                    	gridPanel.getSelectionModel().clearSelections();
	                    	gridPanel.getStore().loadPage(1);
	                    }
					}
				},
				showInLegend : true
			}
		},
		exporting:{    
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
       },
		series : [{
					type : 'pie',
					name : name,
					data : data
				}]
	});
}


function loadPCPRPMAnalysisSingleStatData() {
	var selectWellName=Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
	var statPanelId=getPCPRPMAnalysisSingleStatType().piePanelId;
	if(selectWellName==null||selectWellName==""){
		Ext.getCmp("ScrewPumpRealtimeAnalysisWellListPanel_Id").setTitle("统计数据");
    	Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").hide();
  	  	Ext.getCmp("ScrewPumpRealtimeAnalysisEndDate_Id").hide();
        Ext.getCmp("ScrewPumpRealtimeAnalysisHisBtn_Id").show();
  	  	Ext.getCmp("ScrewPumpRealtimeAnalysisAllBtn_Id").hide();
    	Ext.getCmp(statPanelId).expand(true);
    }else{
    	Ext.getCmp("ScrewPumpRealtimeAnalysisWellListPanel_Id").setTitle("历史数据");
		Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").show();
    	Ext.getCmp("ScrewPumpRealtimeAnalysisEndDate_Id").show();
    	Ext.getCmp("ScrewPumpRealtimeAnalysisHisBtn_Id").hide();
        Ext.getCmp("ScrewPumpRealtimeAnalysisAllBtn_Id").show();
    	Ext.getCmp(statPanelId).collapse();
    }
	Ext.getCmp("PCPRPMAnalysisSingleDetailsSelectedStatValue_Id").setValue("");
	
	var gridPanel=Ext.getCmp("ScrewPumpRTAnalysisWellList_Id");
	if(isNotVal(gridPanel)){
		gridPanel.destroy();
	}
	Ext.create("AP.store.diagnosis.ScrewPumpRTAnalysisStatStore");
}

function exportScrewPumpRTAnalisiDataExcel() {
	var gridId = "ScrewPumpRTAnalysisWellList_Id";
    var url = context + '/diagnosisAnalysisOnlyController/exportProductionWellRTAnalysisDataExcel';
    var fileName = getPCPRPMAnalysisSingleStatType().exportExcelTitle;
    var title =  getPCPRPMAnalysisSingleStatType().exportExcelTitle;
    
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
    var startDate=Ext.getCmp('ScrewPumpRealtimeAnalysisStartDate_Id').rawValue;
    var endDate=Ext.getCmp('ScrewPumpRealtimeAnalysisEndDate_Id').rawValue;
    var statValue = Ext.getCmp('PCPRPMAnalysisSingleDetailsSelectedStatValue_Id').getValue();
    var type=getPCPRPMAnalysisSingleStatType().type;
    var wellType=400;
    
    var fields = "";
    var heads = "";
    var lockedheads = "";
    var unlockedheads = "";
    var lockedfields = "";
    var unlockedfields = "";
    var columns_ = Ext.JSON.decode(Ext.getCmp("DiagnosisAnalysisColumnStr_Id").getValue());
    
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
    heads = "序号," + lockedheads+","+unlockedheads;
    var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId 
    + "&wellName=" + URLencode(URLencode(wellName))
    + "&statValue=" + URLencode(URLencode(statValue)) 
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title))
    + "&type=" + type 
    + "&wellType=" + wellType 
    + "&startDate=" + startDate 
    + "&endDate=" + endDate ;
    openExcelWindow(url + '?flag=true' + param);
};


initScrewPumpRTCurveChartFn = function (get_rawData, divId) {
	var items=get_rawData.totalRoot;
	var RPM=[];
	var Ia=[];
	var Ib=[];
	var Ic=[];
	var Va=[];
	var Vb=[];
	var Vc=[];
	
	var IaMax,IbMax,IcMax,VaMax,VbMax,VcMax;
	var IaMin,IbMin,IcMin,VaMin,VbMin,VcMin;
	IaMax=IbMax=IcMax=VaMax=VbMax=VcMax=2000;
	IaMin=IbMin=IcMin=VaMin=VbMin=VcMin=0;
	for(var i=0;i<items.length;i++){
		if(i==0){
			IaMax=IaMin=items[i].ia;
			IbMax=IbMin=items[i].ib;
			IcMax=IcMin=items[i].ic;
			VaMax=VaMin=items[i].va;
			VbMax=VbMin=items[i].vb;
			VcMax=VcMin=items[i].vc;
		}else{
			if(items[i].ia>IaMax){
				IaMax=items[i].ia;
			}
			if(items[i].ia<IaMin){
				IaMin=items[i].ia;
			}
			
			if(items[i].ib>IbMax){
				IbMax=items[i].ib;
			}
			if(items[i].ib<IbMin){
				IbMin=items[i].ib;
			}
			
			if(items[i].ic>IcMax){
				IcMax=items[i].ic;
			}
			if(items[i].ic<IcMin){
				IcMin=items[i].ic;
			}
			
			if(items[i].va>VaMax){
				VaMax=items[i].va;
			}
			if(items[i].va<VaMin){
				VaMin=items[i].va;
			}
			
			if(items[i].vb>VbMax){
				VbMax=items[i].vb;
			}
			if(items[i].vb<VbMin){
				VbMin=items[i].vb;
			}
			
			if(items[i].vc>VcMax){
				VcMax=items[i].vc;
			}
			if(items[i].vc<VcMin){
				VcMin=items[i].vc;
			}
		}
		RPM.push([
            Date.parse(items[i].acquisitionTime.replace(/-/g, '/')),
            parseFloat(items[i].rpm)
        ]);
		Ia.push([
            Date.parse(items[i].acquisitionTime.replace(/-/g, '/')),
            parseFloat(items[i].ia)
        ]);
		Ib.push([
            Date.parse(items[i].acquisitionTime.replace(/-/g, '/')),
            parseFloat(items[i].ib)
        ]);
		Ic.push([
            Date.parse(items[i].acquisitionTime.replace(/-/g, '/')),
            parseFloat(items[i].ic)
        ]);
		Va.push([
            Date.parse(items[i].acquisitionTime.replace(/-/g, '/')),
            parseFloat(items[i].va)
        ]);
		Vb.push([
            Date.parse(items[i].acquisitionTime.replace(/-/g, '/')),
            parseFloat(items[i].vb)
        ]);
		Vc.push([
            Date.parse(items[i].acquisitionTime.replace(/-/g, '/')),
            parseFloat(items[i].vc)
        ]);
	}
	
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
	mychart = new Highcharts.StockChart({
		chart: {
            renderTo : divId
        }, 
        exporting:{    
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
       },
        legend: {
        	enabled:false,
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
		rangeSelector: {
            selected: 1
        },
        title: {
            text: get_rawData.wellName+'井实时曲线'
        },
        tooltip:{  
            // 日期时间格式化  
            xDateFormat: '%Y-%m-%d %H:%M:%S',
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.2f}</b></td></tr>',
            footerFormat: '</table>',
//            shared: true,
            useHTML: true
        },
        credits: {
            enabled: false
        },
        plotOptions:{
        	series:{
        		dataGrouping:{
        			groupPixelWidth:0.1
        		}
        	}
        },
        xAxis: {  
            //tickPixelInterval: 200,//x轴上的间隔  
        	title:{
        		text:'时间'
        	},
            type: 'datetime', //定义x轴上日期的显示格式  
            labels: {  
            formatter: function() {  
                var vDate=new Date(this.value);
                //return vDate.getFullYear()+"-"+(vDate.getMonth()+1)+"-"+vDate.getDate()+" "+vDate.getDay()+":"+vDate.getMinutes()+":"+vDate.getSeconds();  
                return vDate.getFullYear()+"-"+(vDate.getMonth()+1)+"-"+vDate.getDate(); 
                },  
            align: 'center'  
            }
        },
        yAxis: [{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: '转速(r/min)',
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            },
            endOnTick: false,
//            min:0,
            height: '13%',
            offset: 0,
            lineWidth: 1
        },{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'A相电流(A)'
            },
            endOnTick: false,
            max:IaMax>get_rawData.iauplimit?IaMax+10:get_rawData.iauplimit+10,
            min:IaMin<get_rawData.iadownlimit?(IaMin<5?0:IaMin-5):(get_rawData.iadownlimit<5?0:get_rawData.iadownlimit-5),
            height: '13%',
            top: '14.5%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.iauplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.iauplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.iadownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.iadownlimit //y轴显示位置
            }]
        },{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'B相电流(A)'
            },
            endOnTick: false,
            max:IbMax>get_rawData.ibuplimit?IbMax+10:get_rawData.ibuplimit+10,
            min:IbMin<get_rawData.ibdownlimit?(IbMin<5?0:IbMin-5):(get_rawData.ibdownlimit<5?0:get_rawData.ibdownlimit-5),
            height: '13%',
            top: '29%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.ibuplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.ibuplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.ibdownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.ibdownlimit //y轴显示位置
            }]
        },{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'C相电流(A)'
            },
            endOnTick: false,
            max:IcMax>get_rawData.icuplimit?IcMax+10:get_rawData.icuplimit+10,
            min:IcMin<get_rawData.icdownlimit?(IcMin<5?0:IcMin-5):(get_rawData.icdownlimit<5?0:get_rawData.icdownlimit-5),
            height: '13%',
            top: '43.5%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.icuplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.icuplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.icdownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.icdownlimit //y轴显示位置
            }]
        },{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'A相电压(V)'
            },
            endOnTick: false,
            max:VaMax>get_rawData.vauplimit?VaMax+10:get_rawData.vauplimit+10,
            min:VaMin<get_rawData.vadownlimit?(VaMin<5?0:VaMin-5):(get_rawData.vadownlimit<5?0:get_rawData.vadownlimit-5),
            height: '13%',
            top: '58%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.vauplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.vauplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.vadownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.vadownlimit //y轴显示位置
            }]
        },{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'B相电压(V)'
            },
            endOnTick: false,
            max:VbMax>get_rawData.vbuplimit?VbMax+10:get_rawData.vbuplimit+10,
            min:VbMin<get_rawData.vbdownlimit?(VbMin<5?0:VbMin-5):(get_rawData.vbdownlimit<5?0:get_rawData.vbdownlimit-5),
            height: '13%',
            top: '72.5%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.vbuplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.vbuplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.vbdownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.vbdownlimit //y轴显示位置
            }]
        },{
        	opposite:false,
            labels: {
                align: 'left',
                x: 0
            },
            title: {
                text: 'C相电压(V)'
            },
            endOnTick: false,
            max:VcMax>get_rawData.vcuplimit?VcMax+10:get_rawData.vcuplimit+10,
            min:VcMin<get_rawData.vcdownlimit?(VcMin<5?0:VcMin-5):(get_rawData.vcdownlimit<5?0:get_rawData.vcdownlimit-5),
            height: '13%',
            top: '87%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.vcuplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.vcuplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.vcdownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.vcdownlimit //y轴显示位置
            }]
        }],
        rangeSelector: {  
        	enabled:false,
            buttons: [{//定义一组buttons,下标从0开始  
            type: 'week',  
            count: 1,  
            text: '一周'  
        },{  
        	type: 'week',  
            count: 2,  
            text: '两周'   
        }, {  
        	type: 'week',  
            count: 3,  
            text: '三周' 
        }, {  
            type: 'month',  
            count: 1,  
            text: '一月'  
        },{  
            type: 'all',  
            text: '全部'  
        }],  
            selected: 4,//表示以上定义button的index,从0开始  
            inputDateFormat:'%Y-%m-%d'
        },  
        navigator:{
        	enabled:false
        },
        scrollbar:{
        	enabled:false
        },
        series: [{
        		type: 'spline',
        		name: '转速(r/min)',
        		data: RPM,
        		marker:{
        			enabled:true,
        			radius: 3
        		},
        		yAxis: 0
        	},{
            	type: 'spline',
            	name: 'A相电流(A)',
            	data: Ia,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 1
            },{
            	type: 'spline',
            	name: 'B相电流(A)',
            	data: Ib,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 2
            },{
            	type: 'spline',
            	name: 'C相电流(A)',
            	data: Ic,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 3
            },{
            	type: 'spline',
            	name: 'A相电压(V)',
            	data: Va,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 4
            },{
            	type: 'spline',
            	name: 'B相电压(V)',
            	data: Vb,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 5
            },{
            	type: 'spline',
            	name: 'C相电压(V)',
            	data: Vc,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 6
            }
            ]
	});
}