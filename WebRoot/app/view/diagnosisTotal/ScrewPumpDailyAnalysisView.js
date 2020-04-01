/*******************************************************************************
 * 螺杆泵全天评价视图
 *
 * @author zhao
 *
 */
Ext.define("AP.view.diagnosisTotal.ScrewPumpDailyAnalysisView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.screwPumpDailyAnalysisView', // 定义别名
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
                    var wellName = Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').getValue();
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
            id: "ScrewPumpDailyAnalysisWellCom_Id",
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
                expand: function (sm, selections) {},
                select: function (combo, record, index) {
                	var statPanelId=getPCPSelectDailyStatType().piePanelId;
                    if(combo.value==""){
                    	Ext.getCmp("ScrewPumpDailyAnalysisWellListPanel_Id").setTitle("统计数据");
                    	Ext.getCmp("ScrewPumpDailyAnalysisDate_Id").show();
                    	Ext.getCmp("ScrewPumpDailyAnalysisStartDate_Id").hide();
                  	  	Ext.getCmp("ScrewPumpDailyAnalysisEndDate_Id").hide();
                        Ext.getCmp("ScrewPumpDailyAnalysisHisBtn_Id").show();
                  	  	Ext.getCmp("ScrewPumpDailyAnalysisAllBtn_Id").hide();
                    	Ext.getCmp(statPanelId).expand(true);
                    }else{
                    	Ext.getCmp("ScrewPumpDailyAnalysisWellListPanel_Id").setTitle("历史数据");
                    	Ext.getCmp("ScrewPumpDailyAnalysisDate_Id").hide();
            			Ext.getCmp("ScrewPumpDailyAnalysisStartDate_Id").show();
                    	Ext.getCmp("ScrewPumpDailyAnalysisEndDate_Id").show();
                    	Ext.getCmp("ScrewPumpDailyAnalysisHisBtn_Id").hide();
                        Ext.getCmp("ScrewPumpDailyAnalysisAllBtn_Id").show();
                    	Ext.getCmp(statPanelId).collapse();
                    }
                    Ext.getCmp('ScrewPumpDailyAnalysisWellList_Id').getStore().loadPage(1);
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
                        id: 'ScrewPumpDailyAnalysisWellListPanel_Id',
                        title: '统计数据',
                        border: false,
                        tbar: [wellComb, '-',{
                            id: 'ScrewPumpDailyStatSelectedValue_Id',//选择的统计项的值
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },'-',{
                            xtype: 'datefield',
                            anchor: '100%',
                            fieldLabel: '日期',
                            labelWidth: 30,
                            width: 120,
                            format: 'Y-m-d ',
                            id: 'ScrewPumpDailyAnalysisDate_Id',
                            value: new Date(),
                            listeners: {
                            	select: function (combo, record, index) {
                            		loadScrewPumpDailyStatData();
                                }
                            }
                        }, {
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: '',
                            labelWidth: 0,
                            width: 90,
                            format: 'Y-m-d ',
                            id: 'ScrewPumpDailyAnalysisStartDate_Id',
                            value: '',
                            listeners: {
                                select: function (combo, record, index) {
                                	Ext.getCmp('ScrewPumpDailyAnalysisWellList_Id').getStore().loadPage(1);
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
                            id: 'ScrewPumpDailyAnalysisEndDate_Id',
                            value: new Date(),
                            listeners: {
                                select: function (combo, record, index) {
                                	Ext.getCmp('ScrewPumpDailyAnalysisWellList_Id').getStore().loadPage(1);
                                }
                            }
                        },'-', {
                            xtype: 'button',
                            text: cosog.string.exportExcel,
                            pressed: true,
                            handler: function (v, o) {
                            	exportScrewPumpDailyAnalisiDataExcel();
                            }
                        }, '->', {
                            xtype: 'button',
                            text: '查看历史',
                            tooltip: '点击按钮或者双击表格，查看单井历史数据',
                            id: 'ScrewPumpDailyAnalysisHisBtn_Id',
                            pressed: true,
                            hidden: false,
                            handler: function (v, o) {
                            	var statPanelId=getPCPSelectDailyStatType().piePanelId;;
                            	Ext.getCmp("ScrewPumpDailyAnalysisWellListPanel_Id").setTitle("历史数据");
                            	Ext.getCmp("ScrewPumpDailyAnalysisDate_Id").hide();
                    			Ext.getCmp("ScrewPumpDailyAnalysisStartDate_Id").show();
                            	Ext.getCmp("ScrewPumpDailyAnalysisEndDate_Id").show();
                            	Ext.getCmp("ScrewPumpDailyAnalysisHisBtn_Id").hide();
                                Ext.getCmp("ScrewPumpDailyAnalysisAllBtn_Id").show();
                            	Ext.getCmp(statPanelId).collapse();
                                
                                var jh  = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.jh;
                                Ext.getCmp("ScrewPumpDailyAnalysisWellCom_Id").setValue(jh);
                                Ext.getCmp("ScrewPumpDailyAnalysisWellCom_Id").setRawValue(jh);
                                Ext.getCmp('ScrewPumpDailyAnalysisWellList_Id').getStore().loadPage(1);
                            }
                      }, {
                            xtype: 'button',
                            text: '返回统计',
                            id: 'ScrewPumpDailyAnalysisAllBtn_Id',
                            pressed: true,
                            hidden: true,
                            handler: function (v, o) {
                            	var statPanelId=getPCPSelectDailyStatType().piePanelId;
                            	Ext.getCmp("ScrewPumpDailyAnalysisWellListPanel_Id").setTitle("统计数据");
                            	Ext.getCmp("ScrewPumpDailyAnalysisDate_Id").show();
                            	Ext.getCmp("ScrewPumpDailyAnalysisStartDate_Id").hide();
                          	  	Ext.getCmp("ScrewPumpDailyAnalysisEndDate_Id").hide();
                                Ext.getCmp("ScrewPumpDailyAnalysisHisBtn_Id").show();
                          	  	Ext.getCmp("ScrewPumpDailyAnalysisAllBtn_Id").hide();
                            	Ext.getCmp(statPanelId).expand(true);
                                
                                Ext.getCmp("ScrewPumpDailyAnalysisWellCom_Id").setValue('');
                                Ext.getCmp("ScrewPumpDailyAnalysisWellCom_Id").setRawValue('');
                                Ext.getCmp('ScrewPumpDailyAnalysisWellList_Id').getStore().loadPage(1);
                            }
                      }, {
                            id: 'ScrewPumpDailyAnalysisCount_Id',
                            xtype: 'component',
                            hidden: true,
                            tpl: cosog.string.totalCount + ': {count}',
                            style: 'margin-right:15px'
                    }],
                        items: {
                            xtype: 'tabpanel',
                            id: 'ScrewPumpDailyAnalysisStatTabpanel_Id',
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
                                    id: 'PCPRPMAnalysisDailyWorkCondStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '电参工况',
                                        border: false,
                                        layout: 'border',
                                        iconCls: 'dtgreen',
                                        id: 'PCPRPMAnalysisDailyWorkCondStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisDailyWorkCondDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisDailyWorkCondStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisDailyWorkCondStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisDailyWorkCondStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisDailyWorkCondStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisDailyWorkCondStatGraphPieDiv_Id',
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
                                            loadScrewPumpDailyStatData();
                                        }
                                    }
                            }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '产量',
                                    id: 'PCPRPMAnalysisDailyProdStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '产量分布',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisDailyProdStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisDailyProdDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisDailyProdStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisDailyProdStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisDailyProdStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisDailyProdStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisDailyProdStatGraphPieDiv_Id',
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
                                            loadScrewPumpDailyStatData();
                                        }
                                    }
                                },{
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '时率',
                                    id: 'PCPRPMAnalysisDailyRunTimeEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '运行状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisDailyRunStatusStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisDailyRunStatusDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisDailyRunStatusStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisDailyRunStatusStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisDailyRunStatusStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisDailyRunStatusStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisDailyRunStatusStatGraphPieDiv_Id',
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
                                        id: 'PCPRPMAnalysisDailyRunTimeEffStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisDailyRunTimeEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisDailyRunTimeEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisDailyRunTimeEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisDailyRunTimeEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisDailyRunTimeEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisDailyRunTimeEffStatGraphPieDiv_Id',
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
                                            loadScrewPumpDailyStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '效率',
                                    id: 'PCPRPMAnalysisDailySysEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '系统效率',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisDailySysEffStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisDailySysEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisDailySysEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisDailySysEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisDailySysEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisDailySysEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisDailySysEffStatGraphPieDiv_Id',
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
                                            loadScrewPumpDailyStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '电量',
                                    id: 'PCPRPMAnalysisDailyEnergyStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '日用电量',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisDailyTodayEnergyStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisDailyTodayEnergyDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisDailyTodayEnergyStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisDailyTodayEnergyStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisDailyTodayEnergyStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisDailyTodayEnergyStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisDailyTodayEnergyStatGraphPieDiv_Id',
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
                                    id: 'PCPRPMAnalysisDailyCommEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '通信状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'PCPRPMAnalysisDailyCommStatusStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisDailyCommStatusDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisDailyCommStatusStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisDailyCommStatusStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisDailyCommStatusStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisDailyCommStatusStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisDailyCommStatusStatGraphPieDiv_Id',
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
                                        id: 'PCPRPMAnalysisDailyCommEffStatPanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'PCPRPMAnalysisDailyCommEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'PCPRPMAnalysisDailyCommEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="PCPRPMAnalysisDailyCommEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#PCPRPMAnalysisDailyCommEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#PCPRPMAnalysisDailyCommEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'PCPRPMAnalysisDailyCommEffStatGraphPieDiv_Id',
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
                                            loadScrewPumpDailyStatData();
                                        }
                                    }
                                }
                            ],
                            listeners: {
                                tabchange: function (tabPanel, newCard, oldCard, obj) {
                                    oldCard.setIconCls("");
                                    newCard.setIconCls("select");
                                    loadScrewPumpDailyStatData();
                                }
                            }
                        },
                        listeners: {
                            afterrender: function (comp, eOpts) {
                                Ext.getCmp("PCPRPMAnalysisDailyWorkCondStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisDailyProdStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisDailyRunTimeEffStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisDailySysEffStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisDailyEnergyStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("PCPRPMAnalysisDailyCommEffStatTabpanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                            }
                        }
                },
                    {
                        region: 'east',
                        id: 'ScrewPumpDailyAnalysisDataPanel_Id',
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
                                            id: 'ScrewPumpDailyCurveDataPanel_Id',
                                            html: '<div id="ScrewPumpDailyCurveDataChartDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyCurveDataChartDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyCurveDataChartDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
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
                                        id: 'ScrewPumpDailyAnalysisAndAcqDataTabpanel_Id',
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
                                                id: 'ScrewPumpDailyAnalysisTableCalDataPanel_Id',
                                                border: false,
                                                layout: 'fit',
                                                autoScroll: true,
                                                scrollable: true
                                            }, {
                                                title: '采集',
                                                id: 'ScrewPumpDailyAnalysisTableAcqDataPanel_Id',
                                                border: false,
                                                layout: 'fit',
                                                autoScroll: true,
                                                scrollable: true
                                            }],
                                			listeners: {
                                            	tabchange: function (tabPanel, newCard, oldCard,obj) {
                                            		
                                            	}
                                            }
                                    }
                                ]
                            }
                        ],
                        listeners: {
                            beforeCollapse: function (panel, eOpts) {
                            	$("#ScrewPumpDailyCurveDataChartDiv_Id").hide();
                            },
                            expand: function (panel, eOpts) {
                            	$("#ScrewPumpDailyCurveDataChartDiv_Id").show();
                            }
                        }
                        }]
                }]
        });
        me.callParent(arguments);
    }
});


function getPCPSelectDailyStatType() {
	var type=1;
	var panelId="PCPRPMAnalysisDailyWorkCondDataListPanel_Id";
	var piePanelId="PCPRPMAnalysisDailyWorkCondStatGraphPanel_Id";
	var pieDivId="PCPRPMAnalysisDailyWorkCondStatGraphPieDiv_Id";
	var pieChartTitle="功图工况";
	var exportExcelTitle = '抽油机全天评价-电参工况';
	var activeTabId= Ext.getCmp(Ext.getCmp("ScrewPumpDailyAnalysisStatTabpanel_Id").getActiveTab().id).getActiveTab().id;
	if(activeTabId=="PCPRPMAnalysisDailyWorkCondStatPanel_Id"){//电参工况
		type=1;
		panelId="PCPRPMAnalysisDailyWorkCondDataListPanel_Id";
		piePanelId="PCPRPMAnalysisDailyWorkCondStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisDailyWorkCondStatGraphPieDiv_Id";
		pieChartTitle="功图工况";
		exportExcelTitle = '抽油机全天评价-功图工况';
	}else if(activeTabId=="PCPRPMAnalysisDailyProdStatPanel_Id"){//产量
		type=2;
		panelId="PCPRPMAnalysisDailyProdDataListPanel_Id";
		piePanelId="PCPRPMAnalysisDailyProdStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisDailyProdStatGraphPieDiv_Id";
		pieChartTitle="产量分布";
		exportExcelTitle = '抽油机全天评价-产量分布';
	}else if(activeTabId=="PCPRPMAnalysisDailyRunStatusStatPanel_Id"){//运行状态
		type=5;
		panelId="PCPRPMAnalysisDailyRunStatusDataListPanel_Id";
		piePanelId="PCPRPMAnalysisDailyRunStatusStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisDailyRunStatusStatGraphPieDiv_Id";
		pieChartTitle="运行状态";
		exportExcelTitle = '抽油机全天评价-运行状态';
	}else if(activeTabId=="PCPRPMAnalysisDailyRunTimeEffStatPanel_Id"){//运行时率
		type=6;
		panelId="PCPRPMAnalysisDailyRunTimeEffDataListPanel_Id";
		piePanelId="PCPRPMAnalysisDailyRunTimeEffStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisDailyRunTimeEffStatGraphPieDiv_Id";
		pieChartTitle="运行时率";
		exportExcelTitle = '抽油机全天评价-运行时率';
	}else if(activeTabId=="PCPRPMAnalysisDailySysEffStatPanel_Id"){//系统效率
		type=7;
		panelId="PCPRPMAnalysisDailySysEffDataListPanel_Id";
		piePanelId="PCPRPMAnalysisDailySysEffStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisDailySysEffStatGraphPieDiv_Id";
		pieChartTitle="系统效率";
		exportExcelTitle = '抽油机全天评价-系统效率';
	}else if(activeTabId=="PCPRPMAnalysisDailyTodayEnergyStatPanel_Id"){//如用电量
		type=10;
		panelId="PCPRPMAnalysisDailyTodayEnergyDataListPanel_Id";
		piePanelId="PCPRPMAnalysisDailyTodayEnergyStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisDailyTodayEnergyStatGraphPieDiv_Id";
		pieChartTitle="日用电量";
		exportExcelTitle = '抽油机全天评价-日用电量';
	}else if(activeTabId=="PCPRPMAnalysisDailyCommStatusStatPanel_Id"){//通信状态
		type=11;
		panelId="PCPRPMAnalysisDailyCommStatusDataListPanel_Id";
		piePanelId="PCPRPMAnalysisDailyCommStatusStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisDailyCommStatusStatGraphPieDiv_Id";
		pieChartTitle="通信状态";
		exportExcelTitle = '抽油机全天评价-通信状态';
	}else if(activeTabId=="PCPRPMAnalysisDailyCommEffStatPanel_Id"){//在线时率
		type=12;
		panelId="PCPRPMAnalysisDailyCommEffDataListPanel_Id";
		piePanelId="PCPRPMAnalysisDailyCommEffStatGraphPanel_Id";
		pieDivId="PCPRPMAnalysisDailyCommEffStatGraphPieDiv_Id";
		pieChartTitle="在线时率";
		exportExcelTitle = '抽油机全天评价-在线时率';
	}
	var result=Ext.JSON.decode("{\"type\":"+type+",\"panelId\":\""+panelId+"\",\"piePanelId\":\""+piePanelId+"\",\"pieDivId\":\""+pieDivId+"\",\"pieChartTitle\":\""+pieChartTitle+"\",\"exportExcelTitle\":\""+exportExcelTitle+"\"}");
	return result;
}

function initScrewPumpDailyStatPieChat(store) {
	var divid=getPCPSelectDailyStatType().pieDivId;
	var title=getPCPSelectDailyStatType().pieChartTitle;
	var get_rawData = store.proxy.reader.rawData;
	var datalist=get_rawData.List;
	var subtitle='日期:'+get_rawData.totalDate+' 统计井数:'+get_rawData.totalCount;
	var pieDataStr="[";
	for(var i=0;i<datalist.length;i++){
		pieDataStr+="['"+datalist[i].item+"',"+datalist[i].count+"]";
		if(i<datalist.length-1){
			pieDataStr+=",";
		}
	}
	pieDataStr+="]";
	var pieData = Ext.JSON.decode(pieDataStr);
	ShowScrewPumpDailyStatPieChat(title,subtitle,divid, "井数占", pieData);
}

function ShowScrewPumpDailyStatPieChat(title,subtitle,divid, name, data) {
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
		subtitle : {
			text : subtitle
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
							Ext.getCmp('ScrewPumpDailyStatSelectedValue_Id').setValue(e.point.name);
						}else{
							Ext.getCmp('ScrewPumpDailyStatSelectedValue_Id').setValue("");
						}
						Ext.getCmp("ScrewPumpDailyAnalysisWellCom_Id").setValue("");
	            		Ext.getCmp("ScrewPumpDailyAnalysisWellCom_Id").setRawValue("");
	            		var gridPanel = Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id");
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


function loadScrewPumpDailyStatData() {
	var selectWellName=Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').getValue();
	var statPanelId=getPCPSelectDailyStatType().piePanelId;
	if(selectWellName==null||selectWellName==""){
		Ext.getCmp("ScrewPumpDailyAnalysisWellListPanel_Id").setTitle("统计数据");
    	Ext.getCmp("ScrewPumpDailyAnalysisDate_Id").show();
    	Ext.getCmp("ScrewPumpDailyAnalysisStartDate_Id").hide();
  	  	Ext.getCmp("ScrewPumpDailyAnalysisEndDate_Id").hide();
        Ext.getCmp("ScrewPumpDailyAnalysisHisBtn_Id").show();
  	  	Ext.getCmp("ScrewPumpDailyAnalysisAllBtn_Id").hide();
    	Ext.getCmp(statPanelId).expand(true);
    }else{
    	Ext.getCmp("ScrewPumpDailyAnalysisWellListPanel_Id").setTitle("历史数据");
    	Ext.getCmp("ScrewPumpDailyAnalysisDate_Id").hide();
		Ext.getCmp("ScrewPumpDailyAnalysisStartDate_Id").show();
    	Ext.getCmp("ScrewPumpDailyAnalysisEndDate_Id").show();
    	Ext.getCmp("ScrewPumpDailyAnalysisHisBtn_Id").hide();
        Ext.getCmp("ScrewPumpDailyAnalysisAllBtn_Id").show();
    	Ext.getCmp(statPanelId).collapse();
    }
	Ext.getCmp("ScrewPumpDailyStatSelectedValue_Id").setValue("");
	
	var gridPanel=Ext.getCmp("ScrewPumpDailyAnalysisWellList_Id");
	if(isNotVal(gridPanel)){
		gridPanel.destroy();
	}
	Ext.create("AP.store.diagnosisTotal.ScrewPumpDailyAnalysisStatStore");
}

function exportScrewPumpDailyAnalisiDataExcel() {
	var gridId = "ScrewPumpDailyAnalysisWellList_Id";
	var url = context + '/diagnosisTotalController/exportDiagnosisTotalDataExcel';
	var fileName = getPCPSelectDailyStatType().exportExcelTitle;
    var title = getPCPSelectDailyStatType().exportExcelTitle;
    
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').getValue();
    var totalDate=Ext.getCmp('ScrewPumpDailyAnalysisDate_Id').rawValue;
    var statValue=Ext.getCmp('ScrewPumpDailyStatSelectedValue_Id').getValue();
	var startDate=Ext.getCmp('ScrewPumpDailyAnalysisStartDate_Id').rawValue;
	var endDate=Ext.getCmp('ScrewPumpDailyAnalysisEndDate_Id').rawValue;
	var type=getPCPSelectDailyStatType().type;
    var wellType=400;
    
    var fields = "";
    var heads = "";
    var lockedheads = "";
    var unlockedheads = "";
    var lockedfields = "";
    var unlockedfields = "";
    var columns_ = Ext.JSON.decode(Ext.getCmp("DiagnosisTotalColumnStr_Id").getValue());
    
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
    + "&totalDate=" + totalDate 
    + "&startDate=" + startDate 
    + "&endDate=" + endDate 
    + "&wellName=" + URLencode(URLencode(wellName)) 
    + "&statValue=" + URLencode(URLencode(statValue)) 
    + "&wellType=" + URLencode(URLencode(wellType)) 
    + "&type=" + URLencode(URLencode(type)) 
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};


initScrewPumpDailyCurveChartFn = function (get_rawData, divId) {
	var items=get_rawData.totalRoot;
	var RPM=[];
	var ia=[];
	var ib=[];
	var ic=[];
	var va=[];
	var vb=[];
	var vc=[];
	for(var i=0;i<items.length;i++){
		RPM.push([
            Date.parse(items[i].calculateDate.replace(/-/g, '/')),
            parseFloat(items[i].rpm)
        ]);
		ia.push([
            Date.parse(items[i].calculateDate.replace(/-/g, '/')),
            parseFloat(items[i].ia)
        ]);
		ib.push([
            Date.parse(items[i].calculateDate.replace(/-/g, '/')),
            parseFloat(items[i].ib)
        ]);
		ic.push([
            Date.parse(items[i].calculateDate.replace(/-/g, '/')),
            parseFloat(items[i].ic)
        ]);
		va.push([
            Date.parse(items[i].calculateDate.replace(/-/g, '/')),
            parseFloat(items[i].va)
        ]);
		vb.push([
            Date.parse(items[i].calculateDate.replace(/-/g, '/')),
            parseFloat(items[i].vb)
        ]);
		vc.push([
            Date.parse(items[i].calculateDate.replace(/-/g, '/')),
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
            text: get_rawData.wellName+'井全天评价曲线'
        },
        tooltip:{  
            // 日期时间格式化  
            xDateFormat: '%Y-%m-%d',
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.2f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
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
            height: '13%',
            top: '14.5%',
            offset: 0,
            lineWidth: 1
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
            height: '13%',
            top: '29%',
            offset: 0,
            lineWidth: 1
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
            height: '13%',
            top: '43.5%',
            offset: 0,
            lineWidth: 1
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
            height: '13%',
            top: '58%',
            offset: 0,
            lineWidth: 1
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
            height: '13%',
            top: '72.5%',
            offset: 0,
            lineWidth: 1
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
            height: '13%',
            top: '87%',
            offset: 0,
            lineWidth: 1
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
            	data: ia,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 1
            },{
            	type: 'spline',
            	name: 'B相电流(A)',
            	data: ib,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 2
            },{
            	type: 'spline',
            	name: 'C相电流(A)',
            	data: ic,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 3
            },{
            	type: 'spline',
            	name: 'A相电压(V)',
            	data: va,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 4
            },{
            	type: 'spline',
            	name: 'B相电压(V)',
            	data: vb,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 5
            },{
            	type: 'spline',
            	name: 'C相电压(V)',
            	data: vc,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 6
            }
            ]
	});
}