/*******************************************************************************
 * 螺杆泵实时评价视图
 *
 * @author zhao
 *
 */
Ext.define("AP.view.diagnosis.ScrewPumpRealtimeAnalysisView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.screwPumpRealtimeAnalysisView', // 定义别名
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
                expand: function (sm, selections) {},
                select: function (combo, record, index) {
                	var statPanelId=getScrewPumpRealtimeWellListPanelId().replace("WellList","StatGraph");
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
                            id: 'ScrewPumpSelectedStatValue_Id',//选择的统计项的值
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
                            	var statPanelId=getScrewPumpRealtimeWellListPanelId().replace("WellList","StatGraph");
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisWellListPanel_Id").setTitle("历史数据");
                    			Ext.getCmp("ScrewPumpRealtimeAnalysisStartDate_Id").show();
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisEndDate_Id").show();
                            	Ext.getCmp("ScrewPumpRealtimeAnalysisHisBtn_Id").hide();
                                Ext.getCmp("ScrewPumpRealtimeAnalysisAllBtn_Id").show();
                            	Ext.getCmp(statPanelId).collapse();
                                
                                var jh  = Ext.getCmp("ScrewPumpRTAnalysisWellList_Id").getSelectionModel().getSelection()[0].data.jh;
                                Ext.getCmp("ScrewPumpRealtimeAnalysisWellCom_Id").setValue(jh);
                                Ext.getCmp("ScrewPumpRealtimeAnalysisWellCom_Id").setRawValue(jh);
                                Ext.getCmp('ScrewPumpRTAnalysisWellList_Id').getStore().loadPage(1);
                            }
                      }, {
                            xtype: 'button',
                            text: '返回统计',
                            id: 'ScrewPumpRealtimeAnalysisAllBtn_Id',
                            pressed: true,
                            hidden: true,
                            handler: function (v, o) {
                            	var statPanelId=getScrewPumpRealtimeWellListPanelId().replace("WellList","StatGraph");
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
                            id: 'ScrewPumpRealtimeAnalysisStatTabpanel_Id',
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
                                    id: 'ScrewPumpRealtimeAnalysisGkStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '电参工况',
                                        border: false,
                                        layout: 'border',
                                        id: 'ScrewPumpRealtimeAnalysisDCGkStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListEGkmc_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphEGkmc_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisDCGkStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisDCGkStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisDCGkStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisDCGkStatPieDiv_Id',
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
                                            loadScrewPumpRealtimeStatData();
                                        }
                                    }
                            }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '产量',
                                    id: 'ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '产量分布',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpRealtimeAnalysisProStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListProdDist_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphProdDist_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisProStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisProStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisProStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisProStatPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                 }, {
                                        title: '产量波动',
                                        border: false,
                                        layout: 'border',
                                        id: 'ScrewPumpRealtimeAnalysisFluStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListProdFluc_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphProdFluc_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisFluStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisFluStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisFluStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisFluStatPieDiv_Id',
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
                                            loadScrewPumpRealtimeStatData();
                                        }
                                    }
                                },{
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '时率',
                                    id: 'ScrewPumpRealtimeAnalysisScslStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '运行状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpRealtimeAnalysisYxztStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListRunStatus_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphRunStatus_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisYxztStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisYxztStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisYxztStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisYxztStatPieDiv_Id',
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
                                        id: 'ScrewPumpRealtimeAnalysisSlfbStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListTimeDist_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphTimeDist_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisSlfbStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisSlfbStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisSlfbStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisSlfbStatPieDiv_Id',
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
                                            loadScrewPumpRealtimeStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '效率',
                                    id: 'ScrewPumpRealtimeAnalysisXlStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '系统效率',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpRealtimeAnalysisXtxlStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListSystemEff_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphSystemEff_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisXtxlStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisXtxlStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisXtxlStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisXtxlStatPieDiv_Id',
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
                                            loadScrewPumpRealtimeStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '电量',
                                    id: 'ScrewPumpRealtimeAnalysisRydlPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '日用电量',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpRealtimeAnalysisRydlStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListPowerDist_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphPowerDist_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisRydlStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisRydlStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisRydlStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisRydlStatPieDiv_Id',
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
                                    id: 'ScrewPumpRealtimeAnalysisCommStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '通信状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpRealtimeAnalysisCommStatusStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListCommStatus_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphCommStatus_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisCommStatusStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisCommStatusStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisCommStatusStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisCommStatusStatPieDiv_Id',
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
                                        id: 'ScrewPumpRealtimeAnalysisCommDistPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpRealtimeAnalysisWellListCommDist_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpRealtimeAnalysisStatGraphCommDist_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpRealtimeAnalysisCommDistPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpRealtimeAnalysisCommDistPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpRealtimeAnalysisCommDistPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpRealtimeAnalysisCommDistPieDiv_Id',
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
                                            loadScrewPumpRealtimeStatData();
                                        }
                                    }
                                }
                            ],
                            listeners: {
                                tabchange: function (tabPanel, newCard, oldCard, obj) {
                                    oldCard.setIconCls("");
                                    newCard.setIconCls("select");
                                    loadScrewPumpRealtimeStatData();
                                }
                            }
                        },
                        listeners: {
                            afterrender: function (comp, eOpts) {
                                Ext.getCmp("ScrewPumpRealtimeAnalysisGkStatPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpRealtimeAnalysisScslStatPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpRealtimeAnalysisXlStatPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpRealtimeAnalysisRydlPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpRealtimeAnalysisCommStatPiePanel_Id").getTabBar().insert(0, {
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


function getScrewPumpRealtimeWellListPanelId() {
	var type='ScrewPumpRealtimeAnalysisWellListEGkmc_Id';
	var tabPanel = Ext.getCmp("ScrewPumpRealtimeAnalysisStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ScrewPumpRealtimeAnalysisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisDCGkStatPiePanel_Id"){//电参工况
			type='ScrewPumpRealtimeAnalysisWellListEGkmc_Id';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisProStatPiePanel_Id"){//产量分布
			type='ScrewPumpRealtimeAnalysisWellListProdDist_Id'
		}else if(activeId2=="ScrewPumpRealtimeAnalysisFluStatPiePanel_Id"){//产量波动
			type='ScrewPumpRealtimeAnalysisWellListProdFluc_Id'
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisScslStatPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisScslStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisYxztStatPiePanel_Id"){//运行状态
			type='ScrewPumpRealtimeAnalysisWellListRunStatus_Id'
		}else if(activeId2=="ScrewPumpRealtimeAnalysisSlfbStatPiePanel_Id"){//运行时率
			type='ScrewPumpRealtimeAnalysisWellListTimeDist_Id'
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisXlStatPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisXlStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisXtxlStatPiePanel_Id"){//系统效率
			type='ScrewPumpRealtimeAnalysisWellListSystemEff_Id'
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisRydlPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisRydlPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisRydlStatPiePanel_Id"){//日用电量
			type='ScrewPumpRealtimeAnalysisWellListPowerDist_Id';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisCommStatPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisCommStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisCommStatusStatPiePanel_Id"){//通信状态
			type='ScrewPumpRealtimeAnalysisWellListCommStatus_Id';
		}else if(activeId2=="ScrewPumpRealtimeAnalysisCommDistPiePanel_Id"){//在线时率
			type='ScrewPumpRealtimeAnalysisWellListCommDist_Id';
		}
	}else{
		type='ScrewPumpRealtimeAnalysisWellListEGkmc_Id';
	}
	return type;
}

function getScrewPumpRTStatType() {
	var type='DCGKLX';
	var tabPanel = Ext.getCmp("ScrewPumpRealtimeAnalysisStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ScrewPumpRealtimeAnalysisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisDCGkStatPiePanel_Id"){//电参工况
			type='DCGKLX';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisProStatPiePanel_Id"){//产量分布
			type='CYL'
		}else if(activeId2=="ScrewPumpRealtimeAnalysisFluStatPiePanel_Id"){//产量波动
			type='CYLBD'
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisScslStatPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisScslStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisYxztStatPiePanel_Id"){//运行状态
			type='YXZT'
		}else if(activeId2=="ScrewPumpRealtimeAnalysisSlfbStatPiePanel_Id"){//运行时率
			type='SCSL'
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisXlStatPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisXlStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisXtxlStatPiePanel_Id"){//系统效率
			type='XTXL'
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisRydlPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisRydlPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisRydlStatPiePanel_Id"){//日用电量
			type='RHDL';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisCommStatPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisCommStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisCommStatusStatPiePanel_Id"){//通信状态
			type='TXZT';
		}else if(activeId2=="ScrewPumpRealtimeAnalysisCommDistPiePanel_Id"){//在线时率
			type='TXSL';
		}
	}else{
		type='DCGKLX';
	}
	return type;
}

function initScrewPumpRTStatPieChat(store) {
	var divid="";
	var title="";
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
	var tabPanel = Ext.getCmp("ScrewPumpRealtimeAnalysisStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ScrewPumpRealtimeAnalysisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisDCGkStatPiePanel_Id"){//电参工况
			ShowScrewPumpRTStatPieChat("电参工况","ScrewPumpRealtimeAnalysisDCGkStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisProStatPiePanel_Id"){//产量分布
			ShowScrewPumpRTStatPieChat("产量分布","ScrewPumpRealtimeAnalysisProStatPieDiv_Id", "井数占", pieData);
		}else if(activeId2=="ScrewPumpRealtimeAnalysisFluStatPiePanel_Id"){//产量波动
			ShowScrewPumpRTStatPieChat("产量波动","ScrewPumpRealtimeAnalysisFluStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisScslStatPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisScslStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisYxztStatPiePanel_Id"){//运行状态
			ShowScrewPumpRTStatPieChat("运行状态","ScrewPumpRealtimeAnalysisYxztStatPieDiv_Id", "井数占", pieData);
		}else if(activeId2=="ScrewPumpRealtimeAnalysisSlfbStatPiePanel_Id"){//运行时率
			ShowScrewPumpRTStatPieChat("运行时率","ScrewPumpRealtimeAnalysisSlfbStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisXlStatPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisXlStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisXtxlStatPiePanel_Id"){//系统效率
			ShowScrewPumpRTStatPieChat("系统效率","ScrewPumpRealtimeAnalysisXtxlStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisRydlPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisRydlPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisRydlStatPiePanel_Id"){//日用电量
			ShowScrewPumpRTStatPieChat("日用电量","ScrewPumpRealtimeAnalysisRydlStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisCommStatPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisCommStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisCommStatusStatPiePanel_Id"){//通信状态
			ShowScrewPumpRTStatPieChat("通信状态","ScrewPumpRealtimeAnalysisCommStatusStatPieDiv_Id", "井数占", pieData);
		}else if(activeId2=="ScrewPumpRealtimeAnalysisCommDistPiePanel_Id"){//在线时率
			ShowScrewPumpRTStatPieChat("在线时率","ScrewPumpRealtimeAnalysisCommDistPieDiv_Id", "井数占", pieData);
		}
	}else{
		ShowScrewPumpRTStatPieChat("电参工况","ScrewPumpRealtimeAnalysisDCGkStatPieDiv_Id", "井数占", pieData);
	}
}

function getScrewPumpRTExportExcelTitle() {
	var title='螺杆泵实时评价-电参工况';
	var tabPanel = Ext.getCmp("ScrewPumpRealtimeAnalysisStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ScrewPumpRealtimeAnalysisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisDCGkStatPiePanel_Id"){//电参工况
			title='螺杆泵实时评价-电参工况';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisProAndFluStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisProStatPiePanel_Id"){//产量分布
			title='螺杆泵实时评价-产量分布';
		}else if(activeId2=="ScrewPumpRealtimeAnalysisFluStatPiePanel_Id"){//产量波动
			title='螺杆泵实时评价-产量波动';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisScslStatPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisScslStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisYxztStatPiePanel_Id"){//运行状态
			title='螺杆泵实时评价-运行状态';
		}else if(activeId2=="ScrewPumpRealtimeAnalysisSlfbStatPiePanel_Id"){//运行时率
			title='螺杆泵实时评价-运行时率';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisXlStatPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisXlStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisXtxlStatPiePanel_Id"){//系统效率
			title='螺杆泵实时评价-系统效率';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisRydlPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisRydlPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisRydlStatPiePanel_Id"){//日用电量
			title='螺杆泵实时评价-日用电量';
		}
	}else if(activeId=="ScrewPumpRealtimeAnalysisCommStatPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("ScrewPumpRealtimeAnalysisCommStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpRealtimeAnalysisCommStatusStatPiePanel_Id"){//通信状态
			title='螺杆泵实时评价-通信状态';
		}else if(activeId2=="ScrewPumpRealtimeAnalysisCommDistPiePanel_Id"){//在线时率
			title='螺杆泵实时评价-在线时率';
		}
	}else{
		title='螺杆泵实时评价-电参工况';
	}
	return title;
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
							Ext.getCmp('ScrewPumpSelectedStatValue_Id').setValue(e.point.name);
						}else{
							Ext.getCmp('ScrewPumpSelectedStatValue_Id').setValue("");
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


function loadScrewPumpRealtimeStatData() {
	var selectjh=Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
	var statPanelId=getScrewPumpRealtimeWellListPanelId().replace("WellList","StatGraph");
	if(selectjh==null||selectjh==""){
    	Ext.getCmp(statPanelId).expand(true);
    }else{
    	Ext.getCmp(statPanelId).collapse();
    }
	Ext.getCmp("ScrewPumpSelectedStatValue_Id").setValue("");
	
	var gridPanel=Ext.getCmp("ScrewPumpRTAnalysisWellList_Id");
	if(isNotVal(gridPanel)){
		gridPanel.destroy();
	}
	Ext.create("AP.store.diagnosis.ScrewPumpRTAnalysisStatStore");
}

function exportScrewPumpRTAnalisiDataExcel() {
	var gridId = "ScrewPumpRTAnalysisWellList_Id";
    var url = context + '/diagnosisAnalysisOnlyController/exportProductionWellRTAnalysisDataExcel';
    var fileName = getScrewPumpRTExportExcelTitle();
    var title =  getScrewPumpRTExportExcelTitle();
    
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var jh = Ext.getCmp('ScrewPumpRealtimeAnalysisWellCom_Id').getValue();
    var startDate=Ext.getCmp('DiagnosisAnalysisStartDate_Id').rawValue;
    var endDate=Ext.getCmp('DiagnosisAnalysisEndDate_Id').rawValue;
    var statValue = Ext.getCmp('ScrewPumpSelectedStatValue_Id').getValue();
    var type=getSelectStatType();
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
    + "&jh=" + URLencode(URLencode(jh)) 
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
	var currentA=[];
	var currentB=[];
	var currentC=[];
	var voltageA=[];
	var voltageB=[];
	var voltageC=[];
	
	var currentAMax,currentBMax,currentCMax,voltageAMax,voltageBMax,voltageCMax;
	var currentAMin,currentBMin,currentCMin,voltageAMin,voltageBMin,voltageCMin;
	currentAMax=currentBMax=currenCAMax=voltageAMax=voltageBMax=voltageCMax=2000;
	currentAMin=currentBMin=currentCMin=voltageAMin=voltageBMin=voltageCMin=0;
	for(var i=0;i<items.length;i++){
		if(i==0){
			currentAMax=currentAMin=items[i].currenta;
			currentBMax=currentBMin=items[i].currentb;
			currentCMax=currentCMin=items[i].currentc;
			voltageAMax=voltageAMin=items[i].voltagea;
			voltageBMax=voltageBMin=items[i].voltageb;
			voltageCMax=voltageCMin=items[i].voltagec;
		}else{
			if(items[i].currenta>currentAMax){
				currentAMax=items[i].currenta;
			}
			if(items[i].currenta<currentAMin){
				currentAMin=items[i].currenta;
			}
			
			if(items[i].currentb>currentBMax){
				currentBMax=items[i].currentb;
			}
			if(items[i].currentb<currentBMin){
				currentBMin=items[i].currentb;
			}
			
			if(items[i].currentc>currentCMax){
				currentCMax=items[i].currentc;
			}
			if(items[i].currentc<currentCMin){
				currentCMin=items[i].currentc;
			}
			
			if(items[i].voltagea>voltageAMax){
				voltageAMax=items[i].voltagea;
			}
			if(items[i].voltagea<voltageAMin){
				voltageAMin=items[i].voltagea;
			}
			
			if(items[i].voltageb>voltageBMax){
				voltageBMax=items[i].voltageb;
			}
			if(items[i].voltageb<voltageBMin){
				voltageBMin=items[i].voltageb;
			}
			
			if(items[i].voltagec>voltageCMax){
				voltageCMax=items[i].voltagec;
			}
			if(items[i].voltagec<voltageCMin){
				voltageCMin=items[i].voltagec;
			}
		}
		RPM.push([
            Date.parse(items[i].cjsj.replace(/-/g, '/')),
            parseFloat(items[i].rpm)
        ]);
		currentA.push([
            Date.parse(items[i].cjsj.replace(/-/g, '/')),
            parseFloat(items[i].currenta)
        ]);
		currentB.push([
            Date.parse(items[i].cjsj.replace(/-/g, '/')),
            parseFloat(items[i].currentb)
        ]);
		currentC.push([
            Date.parse(items[i].cjsj.replace(/-/g, '/')),
            parseFloat(items[i].currentc)
        ]);
		voltageA.push([
            Date.parse(items[i].cjsj.replace(/-/g, '/')),
            parseFloat(items[i].voltagea)
        ]);
		voltageB.push([
            Date.parse(items[i].cjsj.replace(/-/g, '/')),
            parseFloat(items[i].voltageb)
        ]);
		voltageC.push([
            Date.parse(items[i].cjsj.replace(/-/g, '/')),
            parseFloat(items[i].voltagec)
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
            text: get_rawData.jh+'井实时曲线'
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
            max:currentAMax>get_rawData.currentauplimit?currentAMax+10:get_rawData.currentauplimit+10,
            min:currentAMin<get_rawData.currentadownlimit?(currentAMin<5?0:currentAMin-5):(get_rawData.currentadownlimit<5?0:get_rawData.currentadownlimit-5),
            height: '13%',
            top: '14.5%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.currentauplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.currentauplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.currentadownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.currentadownlimit //y轴显示位置
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
            max:currentBMax>get_rawData.currentbuplimit?currentBMax+10:get_rawData.currentbuplimit+10,
            min:currentBMin<get_rawData.currentbdownlimit?(currentBMin<5?0:currentBMin-5):(get_rawData.currentbdownlimit<5?0:get_rawData.currentbdownlimit-5),
            height: '13%',
            top: '29%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.currentbuplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.currentbuplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.currentbdownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.currentbdownlimit //y轴显示位置
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
            max:currentCMax>get_rawData.currentcuplimit?currentCMax+10:get_rawData.currentcuplimit+10,
            min:currentCMin<get_rawData.currentcdownlimit?(currentCMin<5?0:currentCMin-5):(get_rawData.currentcdownlimit<5?0:get_rawData.currentcdownlimit-5),
            height: '13%',
            top: '43.5%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.currentcuplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.currentcuplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.currentcdownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.currentcdownlimit //y轴显示位置
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
            max:voltageAMax>get_rawData.voltageauplimit?voltageAMax+10:get_rawData.voltageauplimit+10,
            min:voltageAMin<get_rawData.voltageadownlimit?(voltageAMin<5?0:voltageAMin-5):(get_rawData.voltageadownlimit<5?0:get_rawData.voltageadownlimit-5),
            height: '13%',
            top: '58%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.voltageauplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.voltageauplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.voltageadownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.voltageadownlimit //y轴显示位置
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
            max:voltageBMax>get_rawData.voltagebuplimit?voltageBMax+10:get_rawData.voltagebuplimit+10,
            min:voltageBMin<get_rawData.voltagebdownlimit?(voltageBMin<5?0:voltageBMin-5):(get_rawData.voltagebdownlimit<5?0:get_rawData.voltagebdownlimit-5),
            height: '13%',
            top: '72.5%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.voltagebuplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.voltagebuplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.voltagebdownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.voltagebdownlimit //y轴显示位置
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
            max:voltageCMax>get_rawData.voltagecuplimit?voltageCMax+10:get_rawData.voltagecuplimit+10,
            min:voltageCMin<get_rawData.voltagecdownlimit?(voltageCMin<5?0:voltageCMin-5):(get_rawData.voltagecdownlimit<5?0:get_rawData.voltagecdownlimit-5),
            height: '13%',
            top: '87%',
            offset: 0,
            lineWidth: 1,
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: '上限:'+get_rawData.voltagecuplimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.voltagecuplimit //y轴显示位置
           }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                	text: '下限:'+get_rawData.voltagecdownlimit,
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: get_rawData.voltagecdownlimit //y轴显示位置
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
            	data: currentA,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 1
            },{
            	type: 'spline',
            	name: 'B相电流(A)',
            	data: currentB,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 2
            },{
            	type: 'spline',
            	name: 'C相电流(A)',
            	data: currentC,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 3
            },{
            	type: 'spline',
            	name: 'A相电压(V)',
            	data: voltageA,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 4
            },{
            	type: 'spline',
            	name: 'B相电压(V)',
            	data: voltageB,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 5
            },{
            	type: 'spline',
            	name: 'C相电压(V)',
            	data: voltageC,
            	marker:{
            		enabled:true,
            		radius: 3
            	},
            	yAxis: 6
            }
            ]
	});
}