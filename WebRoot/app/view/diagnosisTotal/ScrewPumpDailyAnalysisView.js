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
        var jhStore_B = new Ext.data.JsonStore({
            pageSize: defaultJhhComboxSize,
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
        var jhComboBox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.jh,
            id: "ScrewPumpDailyAnalysisWellCom_Id",
            store: jhStore_B,
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
                	var statPanelId=getScrewPumpDailyWellListPanelId().replace("WellList","StatGraph");
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
                        tbar: [jhComboBox, '-',{
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
                            	var statPanelId=getScrewPumpDailyWellListPanelId().replace("WellList","StatGraph");
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
                            	var statPanelId=getScrewPumpDailyWellListPanelId().replace("WellList","StatGraph");
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
                                    id: 'ScrewPumpDailyAnalysisGkStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '电参工况',
                                        border: false,
                                        layout: 'border',
                                        id: 'ScrewPumpDailyAnalysisDCGkStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListEGkmc_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphEGkmc_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisDCGkStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisDCGkStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisDCGkStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisDCGkStatPieDiv_Id',
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
                                    id: 'ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '产量分布',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpDailyAnalysisProStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListProdDist_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphProdDist_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisProStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisProStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisProStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisProStatPieDiv_Id',
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
                                        id: 'ScrewPumpDailyAnalysisFluStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListProdFluc_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphProdFluc_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisFluStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisFluStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisFluStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisFluStatPieDiv_Id',
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
                                    id: 'ScrewPumpDailyAnalysisScslStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '运行状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpDailyAnalysisYxztStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListRunStatus_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphRunStatus_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisYxztStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisYxztStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisYxztStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisYxztStatPieDiv_Id',
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
                                        id: 'ScrewPumpDailyAnalysisSlfbStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListTimeDist_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphTimeDist_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisSlfbStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisSlfbStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisSlfbStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisSlfbStatPieDiv_Id',
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
                                    id: 'ScrewPumpDailyAnalysisXlStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '系统效率',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpDailyAnalysisXtxlStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListSystemEff_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphSystemEff_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisXtxlStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisXtxlStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisXtxlStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisXtxlStatPieDiv_Id',
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
                                    id: 'ScrewPumpDailyAnalysisRydlPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '日用电量',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpDailyAnalysisRydlStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListPowerDist_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphPowerDist_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisRydlStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisRydlStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisRydlStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisRydlStatPieDiv_Id',
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
                                    id: 'ScrewPumpDailyAnalysisCommStatPiePanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '通信状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'ScrewPumpDailyAnalysisCommStatusStatPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListCommStatus_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphCommStatus_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisCommStatusStatPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisCommStatusStatPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisCommStatusStatPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisCommStatusStatPieDiv_Id',
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
                                        id: 'ScrewPumpDailyAnalysisCommDistPiePanel_Id',
                                        items: [{
                                            region: 'center',
                                            id: 'ScrewPumpDailyAnalysisWellListCommDist_Id',
                                            header: false,
                                            layout: 'fit'
                                        }, {
                                            region: 'south',
                                            id: 'ScrewPumpDailyAnalysisStatGraphCommDist_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="ScrewPumpDailyAnalysisCommDistPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	if ($("#ScrewPumpDailyAnalysisCommDistPieDiv_Id").highcharts() != undefined) {
                                                        $("#ScrewPumpDailyAnalysisCommDistPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'ScrewPumpDailyAnalysisCommDistPieDiv_Id',
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
                                Ext.getCmp("ScrewPumpDailyAnalysisGkStatPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpDailyAnalysisScslStatPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpDailyAnalysisXlStatPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpDailyAnalysisRydlPiePanel_Id").getTabBar().insert(0, {
                                    xtype: 'tbfill'
                                });
                                Ext.getCmp("ScrewPumpDailyAnalysisCommStatPiePanel_Id").getTabBar().insert(0, {
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


function getScrewPumpDailyWellListPanelId() {
	var type='ScrewPumpDailyAnalysisWellListEGkmc_Id';
	var tabPanel = Ext.getCmp("ScrewPumpDailyAnalysisStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ScrewPumpDailyAnalysisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisDCGkStatPiePanel_Id"){//电参工况
			type='ScrewPumpDailyAnalysisWellListEGkmc_Id';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisProStatPiePanel_Id"){//产量分布
			type='ScrewPumpDailyAnalysisWellListProdDist_Id'
		}else if(activeId2=="ScrewPumpDailyAnalysisFluStatPiePanel_Id"){//产量波动
			type='ScrewPumpDailyAnalysisWellListProdFluc_Id'
		}
	}else if(activeId=="ScrewPumpDailyAnalysisScslStatPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisScslStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisYxztStatPiePanel_Id"){//运行状态
			type='ScrewPumpDailyAnalysisWellListRunStatus_Id'
		}else if(activeId2=="ScrewPumpDailyAnalysisSlfbStatPiePanel_Id"){//运行时率
			type='ScrewPumpDailyAnalysisWellListTimeDist_Id'
		}
	}else if(activeId=="ScrewPumpDailyAnalysisXlStatPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisXlStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisXtxlStatPiePanel_Id"){//系统效率
			type='ScrewPumpDailyAnalysisWellListSystemEff_Id'
		}
	}else if(activeId=="ScrewPumpDailyAnalysisRydlPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisRydlPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisRydlStatPiePanel_Id"){//日用电量
			type='ScrewPumpDailyAnalysisWellListPowerDist_Id';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisCommStatPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisCommStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisCommStatusStatPiePanel_Id"){//通信状态
			type='ScrewPumpDailyAnalysisWellListCommStatus_Id';
		}else if(activeId2=="ScrewPumpDailyAnalysisCommDistPiePanel_Id"){//在线时率
			type='ScrewPumpDailyAnalysisWellListCommDist_Id';
		}
	}else{
		type='ScrewPumpDailyAnalysisWellListEGkmc_Id';
	}
	return type;
}

function getScrewPumpDailyStatType() {
	var type='DCGKLX';
	var tabPanel = Ext.getCmp("ScrewPumpDailyAnalysisStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ScrewPumpDailyAnalysisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisDCGkStatPiePanel_Id"){//电参工况
			type='DCGKLX';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisProStatPiePanel_Id"){//产量分布
			type='CYL'
		}else if(activeId2=="ScrewPumpDailyAnalysisFluStatPiePanel_Id"){//产量波动
			type='CYLBD'
		}
	}else if(activeId=="ScrewPumpDailyAnalysisScslStatPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisScslStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisYxztStatPiePanel_Id"){//运行状态
			type='YXZT'
		}else if(activeId2=="ScrewPumpDailyAnalysisSlfbStatPiePanel_Id"){//运行时率
			type='SCSL'
		}
	}else if(activeId=="ScrewPumpDailyAnalysisXlStatPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisXlStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisXtxlStatPiePanel_Id"){//系统效率
			type='XTXL'
		}
	}else if(activeId=="ScrewPumpDailyAnalysisRydlPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisRydlPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisRydlStatPiePanel_Id"){//日用电量
			type='RYDL';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisCommStatPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisCommStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisCommStatusStatPiePanel_Id"){//通信状态
			type='TXZT';
		}else if(activeId2=="ScrewPumpDailyAnalysisCommDistPiePanel_Id"){//在线时率
			type='TXSL';
		}
	}else{
		type='DCGKLX';
	}
	return type;
}

function initScrewPumpDailyStatPieChat(store) {
	var divid="";
	var title="";
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
	var tabPanel = Ext.getCmp("ScrewPumpDailyAnalysisStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ScrewPumpDailyAnalysisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisDCGkStatPiePanel_Id"){//电参工况
			ShowScrewPumpDailyStatPieChat("电参工况",subtitle,"ScrewPumpDailyAnalysisDCGkStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisProStatPiePanel_Id"){//产量分布
			ShowScrewPumpDailyStatPieChat("产量分布",subtitle,"ScrewPumpDailyAnalysisProStatPieDiv_Id", "井数占", pieData);
		}else if(activeId2=="ScrewPumpDailyAnalysisFluStatPiePanel_Id"){//产量波动
			ShowScrewPumpDailyStatPieChat("产量波动",subtitle,"ScrewPumpDailyAnalysisFluStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpDailyAnalysisScslStatPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisScslStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisYxztStatPiePanel_Id"){//运行状态
			ShowScrewPumpDailyStatPieChat("运行状态",subtitle,"ScrewPumpDailyAnalysisYxztStatPieDiv_Id", "井数占", pieData);
		}else if(activeId2=="ScrewPumpDailyAnalysisSlfbStatPiePanel_Id"){//运行时率
			ShowScrewPumpDailyStatPieChat("运行时率",subtitle,"ScrewPumpDailyAnalysisSlfbStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpDailyAnalysisXlStatPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisXlStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisXtxlStatPiePanel_Id"){//系统效率
			ShowScrewPumpDailyStatPieChat("系统效率",subtitle,"ScrewPumpDailyAnalysisXtxlStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpDailyAnalysisRydlPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisRydlPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisRydlStatPiePanel_Id"){//日用电量
			ShowScrewPumpDailyStatPieChat("日用电量",subtitle,"ScrewPumpDailyAnalysisRydlStatPieDiv_Id", "井数占", pieData);
		}
	}else if(activeId=="ScrewPumpDailyAnalysisCommStatPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisCommStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisCommStatusStatPiePanel_Id"){//通信状态
			ShowScrewPumpDailyStatPieChat("通信状态",subtitle,"ScrewPumpDailyAnalysisCommStatusStatPieDiv_Id", "井数占", pieData);
		}else if(activeId2=="ScrewPumpDailyAnalysisCommDistPiePanel_Id"){//在线时率
			ShowScrewPumpDailyStatPieChat("在线时率",subtitle,"ScrewPumpDailyAnalysisCommDistPieDiv_Id", "井数占", pieData);
		}
	}else{
		ShowScrewPumpDailyStatPieChat("电参工况",subtitle,"ScrewPumpDailyAnalysisDCGkStatPieDiv_Id", "井数占", pieData);
	}
}

function getScrewPumpDailyExportExcelTitle() {
	var title='螺杆泵全天评价-电参工况';
	var tabPanel = Ext.getCmp("ScrewPumpDailyAnalysisStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="ScrewPumpDailyAnalysisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisDCGkStatPiePanel_Id"){//电参工况
			title='螺杆泵全天评价-电参工况';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisProAndFluStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisProStatPiePanel_Id"){//产量分布
			title='螺杆泵全天评价-产量分布';
		}else if(activeId2=="ScrewPumpDailyAnalysisFluStatPiePanel_Id"){//产量波动
			title='螺杆泵全天评价-产量波动';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisScslStatPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisScslStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisYxztStatPiePanel_Id"){//运行状态
			title='螺杆泵全天评价-运行状态';
		}else if(activeId2=="ScrewPumpDailyAnalysisSlfbStatPiePanel_Id"){//运行时率
			title='螺杆泵全天评价-运行时率';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisXlStatPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisXlStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisXtxlStatPiePanel_Id"){//系统效率
			title='螺杆泵全天评价-系统效率';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisRydlPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisRydlPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisRydlStatPiePanel_Id"){//日用电量
			title='螺杆泵全天评价-日用电量';
		}
	}else if(activeId=="ScrewPumpDailyAnalysisCommStatPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("ScrewPumpDailyAnalysisCommStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ScrewPumpDailyAnalysisCommStatusStatPiePanel_Id"){//通信状态
			title='螺杆泵全天评价-通信状态';
		}else if(activeId2=="ScrewPumpDailyAnalysisCommDistPiePanel_Id"){//在线时率
			title='螺杆泵全天评价-在线时率';
		}
	}else{
		title='螺杆泵全天评价-电参工况';
	}
	return title;
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
	var selectjh=Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').getValue();
	var statPanelId=getScrewPumpDailyWellListPanelId().replace("WellList","StatGraph");
	if(selectjh==null||selectjh==""){
    	Ext.getCmp(statPanelId).expand(true);
    }else{
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
    var fileName = getScrewPumpDailyExportExcelTitle();
    var title =  getScrewPumpDailyExportExcelTitle();
    
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var jh = Ext.getCmp('ScrewPumpDailyAnalysisWellCom_Id').getValue();
    var totalDate=Ext.getCmp('ScrewPumpDailyAnalysisDate_Id').rawValue;
    var statValue=Ext.getCmp('ScrewPumpDailyStatSelectedValue_Id').getValue();
	var startDate=Ext.getCmp('ScrewPumpDailyAnalysisStartDate_Id').rawValue;
	var endDate=Ext.getCmp('ScrewPumpDailyAnalysisEndDate_Id').rawValue;
	var type=getScrewPumpDailyStatType();
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
    + "&jh=" + URLencode(URLencode(jh)) 
    + "&statValue=" + URLencode(URLencode(statValue))  
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title))
    + "&type=" + type 
    + "&wellType=" + wellType 
    + "&totalDate=" + totalDate 
    + "&startDate=" + startDate 
    + "&endDate=" + endDate ;
    openExcelWindow(url + '?flag=true' + param);
};


initScrewPumpDailyCurveChartFn = function (get_rawData, divId) {
	var items=get_rawData.totalRoot;
	var RPM=[];
	var currentA=[];
	var currentB=[];
	var currentC=[];
	var voltageA=[];
	var voltageB=[];
	var voltageC=[];
	for(var i=0;i<items.length;i++){
		RPM.push([
            Date.parse(items[i].jssj.replace(/-/g, '/')),
            parseFloat(items[i].rpm)
        ]);
		currentA.push([
            Date.parse(items[i].jssj.replace(/-/g, '/')),
            parseFloat(items[i].currenta)
        ]);
		currentB.push([
            Date.parse(items[i].jssj.replace(/-/g, '/')),
            parseFloat(items[i].currentb)
        ]);
		currentC.push([
            Date.parse(items[i].jssj.replace(/-/g, '/')),
            parseFloat(items[i].currentc)
        ]);
		voltageA.push([
            Date.parse(items[i].jssj.replace(/-/g, '/')),
            parseFloat(items[i].voltagea)
        ]);
		voltageB.push([
            Date.parse(items[i].jssj.replace(/-/g, '/')),
            parseFloat(items[i].voltageb)
        ]);
		voltageC.push([
            Date.parse(items[i].jssj.replace(/-/g, '/')),
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
            text: get_rawData.jh+'井全天评价曲线'
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