/*******************************************************************************
 * 抽油机全天评价视图
 *
 * @author zhao
 *
 */
var cycleGtPage = 1;
var totalGtPage = 1;

Ext.define("AP.view.diagnosisTotal.PumpingDailyAnalysisView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.pumpingDailyAnalysisView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var wellComboBoxStore = new Ext.data.JsonStore({
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
                    var wellName = Ext.getCmp('FSDiagramAnalysisDailyDetailsWellCom_Id').getValue();
                    var new_params = {
                    	wellName: wellName,
                        orgId: org_Id,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellComboBox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.wellName,
            id: "FSDiagramAnalysisDailyDetailsWellCom_Id",
            store: wellComboBoxStore,
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
//                    wellComboBox.clearValue();
                    wellComboBox.getStore().loadPage(1);
                },
                select: function (combo, record, index) {
                    if(combo.value==""){
                    	Ext.getCmp("DiagnosisTotalWellListPanel_Id").setTitle("统计数据");
                    	Ext.getCmp("TotalDiagnosisDate_Id").show();
                  	  	Ext.getCmp("DiagnosisTotalStartDate_Id").hide();
                  	  	Ext.getCmp("DiagnosisTotalEndDate_Id").hide();
                  	  	Ext.getCmp("DiagnosisTotalAllBtn_Id").hide();
                        Ext.getCmp("DiagnosisTotalHisBtn_Id").show();
                    	var statPanelId=getSelectTotalStatType().piePanelId;
                    	Ext.getCmp(statPanelId).expand(true);
                    }else{
                    	Ext.getCmp("DiagnosisTotalWellListPanel_Id").setTitle("单井历史");
                    	Ext.getCmp("TotalDiagnosisDate_Id").hide();
                    	Ext.getCmp("DiagnosisTotalStartDate_Id").show();
                    	Ext.getCmp("DiagnosisTotalEndDate_Id").show();
                    	Ext.getCmp("DiagnosisTotalHisBtn_Id").hide();
                        Ext.getCmp("DiagnosisTotalAllBtn_Id").show();
                        var statPanelId=getSelectTotalStatType().piePanelId;
                        Ext.getCmp(statPanelId).collapse();
                    }
                    Ext.getCmp("DiagnosisTotalData_Id").getStore().loadPage(1);
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
                        id:'DiagnosisTotalWellListPanel_Id',
                        title: '统计数据',
                        border: false,
                        tbar: [wellComboBox, {
                            id: 'PumpingDailyStatSelectedItems_Id', //饼图选择的统计项
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        }, {
                            id: 'DiagnosisTotalCurveItem_Id', //选择查看曲线的数据项名称
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        }, {
                            id: 'DiagnosisTotalCurveItemCode_Id', //选择查看曲线的数据项代码
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        }, {
                            id: 'DiagnosisTotalColumnStr_Id',//选择查看曲线的数据项代码
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },'-',{
                            xtype: 'datefield',
                            anchor: '100%',
                            fieldLabel: '汇总日期',
                            labelWidth: 60,
                            width: 150,
                            format: 'Y-m-d ',
                            id: 'TotalDiagnosisDate_Id',
                            value: new Date(),
                            listeners: {
                            	select: function (combo, record, index) {
                            		loadTotalStatData();
                                }
                            }
                        },{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: '',
                            labelWidth: 0,
                            width: 90,
                            format: 'Y-m-d ',
                            id: 'DiagnosisTotalStartDate_Id',
                            value: '',
                            listeners: {
                            	select: function (combo, record, index) {
                            		Ext.getCmp('DiagnosisTotalData_Id').getStore().loadPage(1);
                                }
                            }
                        },{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: '至',
                            labelWidth: 15,
                            width: 105,
                            format: 'Y-m-d ',
                            id: 'DiagnosisTotalEndDate_Id',
                            value: new Date(),
                            listeners: {
                            	select: function (combo, record, index) {
                            		Ext.getCmp('DiagnosisTotalData_Id').getStore().loadPage(1);
                                }
                            }
                        },'-', {
                            xtype: 'button',
                            text: cosog.string.exportExcel,
                            pressed: true,
                            handler: function (v, o) {
                                exportDiagnosisTotalDataExcel();
                            }
                        }, '->', {
                            xtype: 'button',
                            text:'单井历史',
                            tooltip:'点击按钮或者双击表格，查看单井历史数据',
                            id:'DiagnosisTotalHisBtn_Id',
                            pressed: true,
                            hidden: false,
                            handler: function (v, o) {
                            	Ext.getCmp("DiagnosisTotalWellListPanel_Id").setTitle("单井历史");
                            	Ext.getCmp("TotalDiagnosisDate_Id").hide();
                            	Ext.getCmp("DiagnosisTotalStartDate_Id").show();
                            	Ext.getCmp("DiagnosisTotalEndDate_Id").show();
                            	
                            	Ext.getCmp("DiagnosisTotalHisBtn_Id").hide();
                                Ext.getCmp("DiagnosisTotalAllBtn_Id").show();
                                
                                var statPanelId=getSelectTotalStatType().piePanelId;
                                Ext.getCmp(statPanelId).collapse();
                                
                                var wellName  = Ext.getCmp("DiagnosisTotalData_Id").getSelectionModel().getSelection()[0].data.wellName;
                                Ext.getCmp("FSDiagramAnalysisDailyDetailsWellCom_Id").setValue(wellName);
                                Ext.getCmp("FSDiagramAnalysisDailyDetailsWellCom_Id").setRawValue(wellName);
                                Ext.getCmp('DiagnosisTotalData_Id').getStore().loadPage(1);
                            }
                      }, {
                          xtype: 'button',
                          text:'返回统计',
                          id:'DiagnosisTotalAllBtn_Id',
                          pressed: true,
                          hidden: true,
                          handler: function (v, o) {
                        	  Ext.getCmp("DiagnosisTotalWellListPanel_Id").setTitle("统计数据");
                        	  Ext.getCmp("TotalDiagnosisDate_Id").show();
                        	  Ext.getCmp("DiagnosisTotalStartDate_Id").hide();
                        	  Ext.getCmp("DiagnosisTotalEndDate_Id").hide();
                        	  
                        	  Ext.getCmp("DiagnosisTotalAllBtn_Id").hide();
                              Ext.getCmp("DiagnosisTotalHisBtn_Id").show();
                              
                              var statPanelId=getSelectTotalStatType().piePanelId;
                              Ext.getCmp(statPanelId).expand(true);
                              
                              Ext.getCmp("FSDiagramAnalysisDailyDetailsWellCom_Id").setValue('');
                              Ext.getCmp("FSDiagramAnalysisDailyDetailsWellCom_Id").setRawValue('');
                              Ext.getCmp('DiagnosisTotalData_Id').getStore().loadPage(1);
                          }
                      }],
                        items: {
                            xtype: 'tabpanel',
                            id: 'FSDiagramAnalysisDailyDetailsStatTabpanel_Id',
                            activeTab: 0,
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            tabPosition: 'top',
                            items: [{
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '功图工况',
                                    iconCls:'select',
                                    id: 'FSDiagramAnalysisDailyWorkCondStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '功图工况',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyWorkCondStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyWorkCondDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyWorkCondStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyWorkCondStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyWorkCondStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyWorkCondStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyWorkCondStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyWorkCondStatGraphPieDiv_Id',
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
                                            loadTotalStatData();
                                        }
                                    }
                            },{

                                xtype: 'tabpanel',
                                tabPosition: 'right',
                                title: '电参工况',
                                id: 'FSDiagramAnalysisDailyElecWorkCondStatTabpanel_Id',
                                tabRotation: 1,
                                items: [{
                                    title: '电参工况',
                                    border: false,
                                    iconCls: 'dtgreen',
                                    layout: 'border',
                                    id: 'FSDiagramAnalysisDailyElecWorkCondStatPanel_Id',
                                    items:[{
                                    	region: 'center',
                                        id:'FSDiagramAnalysisDailyElecWorkCondDataListPanel_Id',
                                        header: false,
                                        layout: 'fit'
                                    },{
                                    	region: 'south',
                                    	id:'FSDiagramAnalysisDailyElecWorkCondStatGraphPanel_Id',
                                        height: '50%',
                                        border: true,
                                        header: false,
                                        collapsible: true, // 是否折叠
                                        split: true, // 竖折叠条
                                        html: '<div id="FSDiagramAnalysisDailyElecWorkCondStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                if ($("#FSDiagramAnalysisDailyElecWorkCondStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyElecWorkCondStatGraphPieDiv_Id").highcharts() != undefined) {
                                                    $("#FSDiagramAnalysisDailyElecWorkCondStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                }else{
                                                	Ext.create('Ext.tip.ToolTip', {
                                                        target: 'FSDiagramAnalysisDailyElecWorkCondStatGraphPieDiv_Id',
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
                                        loadTotalStatData();
                                    }
                                }
                            }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '产量',
                                    id: 'FSDiagramAnalysisDailyProdStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '产量分布',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyProdStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyProdDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyProdStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyProdStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyProdStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyProdStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyProdStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyProdStatGraphPieDiv_Id',
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
                                            loadTotalStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '平衡',
                                    id: 'FSDiagramAnalysisDailyBalanceStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '功率平衡',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyWattBalanceStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyWattBalanceDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyWattBalanceStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyWattBalanceStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyWattBalanceStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyWattBalanceStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyWattBalanceStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyWattBalanceStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    }, {
                                        title: '电流平衡',
                                        border: false,
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyIBalanceStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyIBalanceDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyIBalanceStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyIBalanceStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyIBalanceStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyIBalanceStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyIBalanceStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyIBalanceStatGraphPieDiv_Id',
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
                                            loadTotalStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '时率',
                                    id: 'FSDiagramAnalysisDailyRunTimeEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '运行状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyRunStatusStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyRunStatusDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyRunStatusStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyRunStatusStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyRunStatusStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyRunStatusStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyRunStatusStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyRunStatusStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    },{
                                        title: '运行时率',
                                        border: false,
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyRunTimeEffStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyRunTimeEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyRunTimeEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyRunTimeEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyRunTimeEffStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyRunTimeEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyRunTimeEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'DiagnosisTotalSlfbStatPieDiv_Id',
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
                                            loadTotalStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '效率',
                                    id: 'FSDiagramAnalysisDailySysEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '系统效率',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailySysEffStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailySysEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailySysEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailySysEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailySysEffStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailySysEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailySysEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailySysEffStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }

                                            }
                                        }]
                                    }, {
                                        title: '地面效率',
                                        border: false,
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailySurfaceEffStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailySurfaceEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailySurfaceEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailySurfaceEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailySurfaceEffStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailySurfaceEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailySurfaceEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailySurfaceEffStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    }, {
                                        title: '井下效率',
                                        border: false,
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyWellDownEffStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyWellDownEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyWellDownEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyWellDownEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyWellDownEffStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyWellDownEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyWellDownEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyWellDownEffStatGraphPieDiv_Id',
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
                                            loadTotalStatData();
                                        }
                                    }
                                }, {
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '电量',
                                    id: 'FSDiagramAnalysisDailyEnergyStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '日用电量',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyTodayEnergyStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyTodayEnergyDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyTodayEnergyStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyTodayEnergyStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyTodayEnergyStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyTodayEnergyStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyTodayEnergyStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyTodayEnergyStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                 }]
                                },{
                                    xtype: 'tabpanel',
                                    tabPosition: 'right',
                                    title: '通信',
                                    id: 'FSDiagramAnalysisDailyCommEffStatTabpanel_Id',
                                    tabRotation: 1,
                                    items: [{
                                        title: '通信状态',
                                        border: false,
                                        iconCls: 'dtgreen',
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyCommStatusStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyCommStatusDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyCommStatusStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyCommStatusStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyCommStatusStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyCommStatusStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyCommStatusStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyCommStatusStatGraphPieDiv_Id',
                                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                                        });
                                                    }
                                                }
                                            }
                                        }]
                                    },{
                                        title: '在线时率',
                                        border: false,
                                        layout: 'border',
                                        id: 'FSDiagramAnalysisDailyCommEffStatPanel_Id',
                                        items:[{
                                        	region: 'center',
                                            id:'FSDiagramAnalysisDailyCommEffDataListPanel_Id',
                                            header: false,
                                            layout: 'fit'
                                        },{
                                        	region: 'south',
                                        	id:'FSDiagramAnalysisDailyCommEffStatGraphPanel_Id',
                                            height: '50%',
                                            border: true,
                                            header: false,
                                            collapsible: true, // 是否折叠
                                            split: true, // 竖折叠条
                                            html: '<div id="FSDiagramAnalysisDailyCommEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#FSDiagramAnalysisDailyCommEffStatGraphPieDiv_Id").highcharts() != undefined && $("#FSDiagramAnalysisDailyCommEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                        $("#FSDiagramAnalysisDailyCommEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }else{
                                                    	Ext.create('Ext.tip.ToolTip', {
                                                            target: 'FSDiagramAnalysisDailyCommEffStatGraphPieDiv_Id',
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
                                            loadTotalStatData();
                                        }
                                    }
                                }
                                ],
                            listeners: {
                                tabchange: function (tabPanel, newCard, oldCard, obj) {
                                	oldCard.setIconCls("");
                            		newCard.setIconCls("select");
                                    loadTotalStatData();
                                }
                            }
                     },
                     listeners: {
                     	afterrender: function (comp,eOpts) {
//                     		Ext.getCmp("DiagnosisRealStatTabpanel_Id").getTabBar().insert(0, {
//                     		      xtype: 'tbfill'
//                     		});
                     		Ext.getCmp("FSDiagramAnalysisDailyWorkCondStatTabpanel_Id").getTabBar().insert(0, {
                   		      	xtype: 'tbfill'
                     		});
                     		Ext.getCmp("FSDiagramAnalysisDailyElecWorkCondStatTabpanel_Id").getTabBar().insert(0, {
                   		      	xtype: 'tbfill'
                     		});
                     		Ext.getCmp("FSDiagramAnalysisDailyProdStatTabpanel_Id").getTabBar().insert(0, {
                   		      	xtype: 'tbfill'
                     		});
                     		Ext.getCmp("FSDiagramAnalysisDailyBalanceStatTabpanel_Id").getTabBar().insert(0, {
                   		      	xtype: 'tbfill'
                     		});
                     		Ext.getCmp("FSDiagramAnalysisDailyRunTimeEffStatTabpanel_Id").getTabBar().insert(0, {
                   		      	xtype: 'tbfill'
                     		});
                     		Ext.getCmp("FSDiagramAnalysisDailySysEffStatTabpanel_Id").getTabBar().insert(0, {
                   		      	xtype: 'tbfill'
                     		});
                     		Ext.getCmp("FSDiagramAnalysisDailyEnergyStatTabpanel_Id").getTabBar().insert(0, {
                   		      	xtype: 'tbfill'
                     		});
                     		Ext.getCmp("FSDiagramAnalysisDailyCommEffStatTabpanel_Id").getTabBar().insert(0, {
                   		      	xtype: 'tbfill'
                     		});
                         }
                     }
                },
                    {
                        region: 'east',
                        id: 'DiagnosisTotalAnalysisDataPanel_Id',
                        width: '65%',
                        title: '单井详情',
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
                                flex: 1,
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
                                            height: 300,
                                            layout: 'fit',
                                            id: 'DiagnosisTotalOverlayPanel_Id',
                                            html: '<div id="DiagnosisTotalOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#DiagnosisTotalOverlayDiv_Id").highcharts() != undefined && $("#DiagnosisTotalOverlayDiv_Id").highcharts() != null) {
                                                        $("#DiagnosisTotalOverlayDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }
                                                }
                                            }
                                     }, {
                                            border: false,
                                            margin: '0 0 1 0',
                                            height: 300,
                                            layout: 'fit',
                                            id: 'DiagnosisTotalPowerOverlayPanel_Id',
                                            html: '<div id="DiagnosisTotalPowerOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#DiagnosisTotalPowerOverlayDiv_Id").highcharts() != undefined && $("#DiagnosisTotalPowerOverlayDiv_Id").highcharts() != null) {
                                                        $("#DiagnosisTotalPowerOverlayDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }
                                                }
                                            }
                                     }, {
                                            border: false,
                                            margin: '0 0 0 0',
                                            height: 300,
                                            layout: 'fit',
                                            id: 'DiagnosisTotalCurrentOverlayPanel_Id',
                                            html: '<div id="DiagnosisTotalCurrentOverlayDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    if ($("#DiagnosisTotalCurrentOverlayDiv_Id").highcharts() != undefined && $("#DiagnosisTotalCurrentOverlayDiv_Id").highcharts() != null) {
                                                        $("#DiagnosisTotalCurrentOverlayDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                    }
                                                }
                                            }
                                     }]
                                 }
                                ]
                            },
                            {
                                border: false,
                                flex: 2,
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
                                        border: false,
                                        flex: 1,
                                        height: 900,
                                        autoScroll: true,
                                        scrollable: true,
//                                        collapsible: true,
//                                        header: false,
//                                        collapseDirection: 'right',
//                                        split: true,
                                        layout: 'fit',
                                        id: 'DiagnosisTotalFSdiagramOverlayTable_Id'
                                    },
                                    {
                                        xtype: 'tabpanel',
                                        id: 'TotalAndAcqDataTabpanel_Id',
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
//                                                title: '分析',
//                                                id: 'TotalAnalysisDataPanel_Id',
//                                                border: false,
//                                                layout: 'fit',
//                                                autoScroll: true,
//                                                scrollable: true
                                                
                                                title: '分析',
                                                layout: 'border',
                                                items:[
                                                	{
                                                		region: 'center',
                                                		id: 'TotalAnalysisDataPanel_Id',
                                                        border: false,
                                                        layout: 'fit',
                                                        height:'60%'
//                                                        collapseDirection:'top'
                                                	},{
                                                		region: 'south',
                                                		height:'40%',
                                                		border: false,
                                                		header: false,
                                                        collapsible:true,
                                                        split: true,
                                                		xtype:'form',
                                                		layout: 'auto',
                                                		items: [{
                                                			xtype:'label',
                                                			text:'运行区间:',
                                                			margin:'0 0 20 0'
                                                		},{
                                                        	xtype:'textareafield',
                                                        	id:'FSDiagramAnalysisDailyDetailsRightRunRangeTextArea_Id',
                                                        	grow:true,
                                                        	width:'99.9%',
                                                            height: '45%',
                                                            readOnly:true
                                                        },{
                                                			xtype:'label',
                                                			text:'工况累计:',
                                                			margin:'200 0 20 0'
                                                		},{
                                                        	xtype:'textareafield',
                                                        	id:'FSDiagramAnalysisDailyDetailsRightResultCodeTextArea_Id',
                                                        	grow:true,
                                                        	width:'99.9%',
                                                        	height: '45%',
                                                            readOnly:true
                                                        }]
                                                	}
                                                ]
                                            }, {
                                                title: '采集',
                                                id: 'TotalAcqDataPanel_Id',
                                                border: false,
                                                layout: 'fit',
                                                autoScroll: true,
                                                scrollable: true
                                            }]
                                    }
                                ]
                            }
                        ],
                        listeners: {
                        	beforeCollapse: function (panel, eOpts) {
                                $("#DiagnosisTotalOverlayDiv_Id").hide();
                                $("#DiagnosisTotalPowerOverlayDiv_Id").hide();
                                $("#DiagnosisTotalCurrentOverlayDiv_Id").hide();
                            },
                            expand: function (panel, eOpts) {
                            	$("#DiagnosisTotalOverlayDiv_Id").show();
                                $("#DiagnosisTotalPowerOverlayDiv_Id").show();
                                $("#DiagnosisTotalCurrentOverlayDiv_Id").show();
                            }
                        }
                        }]
                }]
        });
        me.callParent(arguments);
    }

});

function loadTotalStatData() {
	var selectWellName=Ext.getCmp('FSDiagramAnalysisDailyDetailsWellCom_Id').getValue();
	var statPanelId=getSelectTotalStatType().piePanelId;
	if(selectWellName==null||selectWellName==""){
		Ext.getCmp("DiagnosisTotalWellListPanel_Id").setTitle("统计数据");
    	Ext.getCmp("TotalDiagnosisDate_Id").show();
  	  	Ext.getCmp("DiagnosisTotalStartDate_Id").hide();
  	  	Ext.getCmp("DiagnosisTotalEndDate_Id").hide();
  	  	Ext.getCmp("DiagnosisTotalAllBtn_Id").hide();
        Ext.getCmp("DiagnosisTotalHisBtn_Id").show();
    	Ext.getCmp(statPanelId).expand(true);
    }else{
    	Ext.getCmp("DiagnosisTotalWellListPanel_Id").setTitle("单井历史");
    	Ext.getCmp("TotalDiagnosisDate_Id").hide();
    	Ext.getCmp("DiagnosisTotalStartDate_Id").show();
    	Ext.getCmp("DiagnosisTotalEndDate_Id").show();
    	Ext.getCmp("DiagnosisTotalHisBtn_Id").hide();
        Ext.getCmp("DiagnosisTotalAllBtn_Id").show();
    	Ext.getCmp(statPanelId).collapse();
    }
    Ext.getCmp("PumpingDailyStatSelectedItems_Id").setValue("");
    Ext.getCmp("FSDiagramAnalysisDailyDetailsWellCom_Id").setValue("");
	Ext.getCmp("FSDiagramAnalysisDailyDetailsWellCom_Id").setRawValue("");
    if(Ext.getCmp("DiagnosisTotalData_Id")!=undefined&&Ext.getCmp("DiagnosisTotalData_Id")!=null){
    	Ext.getCmp("DiagnosisTotalData_Id").destroy();
    }
    var diagnosisTotalFSdiagramOverlayGrid_Id=Ext.getCmp("DiagnosisTotalFSdiagramOverlayGrid_Id");
    if(isNotVal(diagnosisTotalFSdiagramOverlayGrid_Id)){
    	diagnosisTotalFSdiagramOverlayGrid_Id.getStore().removeAll();
    }
    Ext.create("AP.store.diagnosisTotal.DiagnosisTotalStatStore");
}

function getSelectTotalStatType() {
	var type=1;
	var panelId="FSDiagramAnalysisDailyWorkCondDataListPanel_Id";
	var piePanelId="FSDiagramAnalysisDailyWorkCondStatGraphPanel_Id";
	var pieDivId="FSDiagramAnalysisDailyWorkCondStatGraphPieDiv_Id";
	var pieChartTitle="功图工况";
	var exportExcelTitle = '抽油机全天评价-功图工况';
	var activeTabId= Ext.getCmp(Ext.getCmp("FSDiagramAnalysisDailyDetailsStatTabpanel_Id").getActiveTab().id).getActiveTab().id;
	if(activeTabId=="FSDiagramAnalysisDailyWorkCondStatPanel_Id"){//功图工况
		type=1;
		panelId="FSDiagramAnalysisDailyWorkCondDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyWorkCondStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyWorkCondStatGraphPieDiv_Id";
		pieChartTitle="功图工况";
		exportExcelTitle = '抽油机全天评价-功图工况';
	}else if(activeTabId=="FSDiagramAnalysisDailyElecWorkCondStatPanel_Id"){//电参工况
		type=13;
		panelId="FSDiagramAnalysisDailyElecWorkCondDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyElecWorkCondStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyElecWorkCondStatGraphPieDiv_Id";
		pieChartTitle="电参工况";
		exportExcelTitle = '抽油机全天评价-电参工况';
	}else if(activeTabId=="FSDiagramAnalysisDailyProdStatPanel_Id"){//产量
		type=2;
		panelId="FSDiagramAnalysisDailyProdDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyProdStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyProdStatGraphPieDiv_Id";
		pieChartTitle="产量分布";
		exportExcelTitle = '抽油机全天评价-产量分布';
	}else if(activeTabId=="FSDiagramAnalysisDailyWattBalanceStatPanel_Id"){//功率平衡
		type=3;
		panelId="FSDiagramAnalysisDailyWattBalanceDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyWattBalanceStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyWattBalanceStatGraphPieDiv_Id";
		pieChartTitle="功率平衡";
		exportExcelTitle = '抽油机全天评价-功率平衡';
	}else if(activeTabId=="FSDiagramAnalysisDailyIBalanceStatPanel_Id"){//电流平衡
		type=4;
		panelId="FSDiagramAnalysisDailyIBalanceDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyIBalanceStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyIBalanceStatGraphPieDiv_Id";
		pieChartTitle="电流平衡";
		exportExcelTitle = '抽油机全天评价-电流平衡';
	}else if(activeTabId=="FSDiagramAnalysisDailyRunStatusStatPanel_Id"){//运行状态
		type=5;
		panelId="FSDiagramAnalysisDailyRunStatusDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyRunStatusStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyRunStatusStatGraphPieDiv_Id";
		pieChartTitle="运行状态";
		exportExcelTitle = '抽油机全天评价-运行状态';
	}else if(activeTabId=="FSDiagramAnalysisDailyRunTimeEffStatPanel_Id"){//运行时率
		type=6;
		panelId="FSDiagramAnalysisDailyRunTimeEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyRunTimeEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyRunTimeEffStatGraphPieDiv_Id";
		pieChartTitle="运行时率";
		exportExcelTitle = '抽油机全天评价-运行时率';
	}else if(activeTabId=="FSDiagramAnalysisDailySysEffStatPanel_Id"){//系统效率
		type=7;
		panelId="FSDiagramAnalysisDailySysEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailySysEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailySysEffStatGraphPieDiv_Id";
		pieChartTitle="系统效率";
		exportExcelTitle = '抽油机全天评价-系统效率';
	}else if(activeTabId=="FSDiagramAnalysisDailySurfaceEffStatPanel_Id"){//地面效率
		type=8;
		panelId="FSDiagramAnalysisDailySurfaceEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailySurfaceEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailySurfaceEffStatGraphPieDiv_Id";
		pieChartTitle="地面效率";
		exportExcelTitle = '抽油机全天评价-地面效率';
	}else if(activeTabId=="FSDiagramAnalysisDailyWellDownEffStatPanel_Id"){//井下效率
		type=9;
		panelId="FSDiagramAnalysisDailyWellDownEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyWellDownEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyWellDownEffStatGraphPieDiv_Id";
		pieChartTitle="井下效率";
		exportExcelTitle = '抽油机全天评价-井下效率';
	}else if(activeTabId=="FSDiagramAnalysisDailyTodayEnergyStatPanel_Id"){//如用电量
		type=10;
		panelId="FSDiagramAnalysisDailyTodayEnergyDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyTodayEnergyStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyTodayEnergyStatGraphPieDiv_Id";
		pieChartTitle="日用电量";
		exportExcelTitle = '抽油机全天评价-日用电量';
	}else if(activeTabId=="FSDiagramAnalysisDailyCommStatusStatPanel_Id"){//通信状态
		type=11;
		panelId="FSDiagramAnalysisDailyCommStatusDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyCommStatusStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyCommStatusStatGraphPieDiv_Id";
		pieChartTitle="通信状态";
		exportExcelTitle = '抽油机全天评价-通信状态';
	}else if(activeTabId=="FSDiagramAnalysisDailyCommEffStatPanel_Id"){//在线时率
		type=12;
		panelId="FSDiagramAnalysisDailyCommEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisDailyCommEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisDailyCommEffStatGraphPieDiv_Id";
		pieChartTitle="在线时率";
		exportExcelTitle = '抽油机全天评价-在线时率';
	}
	var result=Ext.JSON.decode("{\"type\":"+type+",\"panelId\":\""+panelId+"\",\"piePanelId\":\""+piePanelId+"\",\"pieDivId\":\""+pieDivId+"\",\"pieChartTitle\":\""+pieChartTitle+"\",\"exportExcelTitle\":\""+exportExcelTitle+"\"}");
	return result;
}

function createDiagnosisTotalColumn(columnInfo) {
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
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+ width_ ;
        if (attr.dataIndex.toUpperCase() == 'workingConditionName'.toUpperCase()) {
            myColumns +=",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='workingConditionName_Elec'.toUpperCase()||attr.dataIndex.toUpperCase()=='workingConditionName_E'.toUpperCase()) {
            myColumns +=",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceElecWorkingConditionColor(value,o,p,e);}";
        }else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns +=",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns +=",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='iDegreeBalanceName'.toUpperCase()||attr.dataIndex.toUpperCase()=='iDegreeBalanceLevel'.toUpperCase()) {
            myColumns +=",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='wattDegreeBalanceName'.toUpperCase()||attr.dataIndex.toUpperCase()=='wattDegreeBalanceLevel'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return advicePowerBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'id') {
            myColumns +=",xtype: 'rownumberer',sortable : false,locked:true";
        } else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns +=",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        }else if (attr.dataIndex.toUpperCase() == 'calcateDateTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

function exportDiagnosisTotalDataExcel() {
	var gridId = "DiagnosisTotalData_Id";
    var url = context + '/diagnosisTotalController/exportDiagnosisTotalDataExcel';
    var fileName = getSelectTotalStatType().exportExcelTitle;
    var title = getSelectTotalStatType().exportExcelTitle;
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('FSDiagramAnalysisDailyDetailsWellCom_Id').getValue();
    var totalDate=Ext.getCmp('TotalDiagnosisDate_Id').rawValue;
    var statValue = Ext.getCmp('PumpingDailyStatSelectedItems_Id').getValue();
	var startDate=Ext.getCmp('DiagnosisTotalStartDate_Id').rawValue;
	var endDate=Ext.getCmp('DiagnosisTotalEndDate_Id').rawValue;
	var type=getSelectTotalStatType().type;
	var wellType=200;
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


function initDiagnosisTotalSataPie(store) {
	var get_rawData = store.proxy.reader.rawData;
	var divId=getSelectTotalStatType().pieDivId;
	var pieChartTitle=getSelectTotalStatType().pieChartTitle;
	
	var list=get_rawData.List;
	var subtitle='日期:'+get_rawData.totalDate+' 统计井数:'+get_rawData.totalCount;
	var PieDataStr="[";
	for(var i=0;i<list.length;i++){
		PieDataStr+="['"+list[i].item+"',"+list[i].count+"]";
		if(i<list.length-1){
			PieDataStr+=",";
		}
	}
	PieDataStr+="]";
	var PieData = Ext.JSON.decode(PieDataStr);
	
	ShowDiagnosisTotalPieChart(divId,pieChartTitle,subtitle,"井数占",PieData);
};

function ShowDiagnosisTotalPieChart(divid,title_,subtitle, name_, data) {
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
			text : title_
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
		exporting:{    
            enabled:true,    
            filename:'class-booking-chart',    
            url:context + '/exportHighcharsPicController/export'
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
						if(!e.point.selected){
							Ext.getCmp("PumpingDailyStatSelectedItems_Id").setValue(e.point.name);
						}else{
							Ext.getCmp("PumpingDailyStatSelectedItems_Id").setValue("");
						}
						Ext.getCmp("FSDiagramAnalysisDailyDetailsWellCom_Id").setValue("");
                		Ext.getCmp("FSDiagramAnalysisDailyDetailsWellCom_Id").setRawValue("");
						Ext.getCmp('DiagnosisTotalData_Id').getStore().loadPage(1);
					}
				},
				showInLegend : true
			}
		},
		series : [{
					type : 'pie',
					name : name_,
					data : data
				}]
	});

};


DiagnosisTotalCurveChartFn = function (get_rawData) {
    var tickInterval = 1;
    var data = get_rawData.totalRoot;
    tickInterval = Math.floor(data.length / 10) + 1;
    var catagories = "[";
    for (var i = 0; i < data.length; i++) {
        catagories += "\"" + data[i].jssj + "\"";
        if (i < data.length - 1) {
            catagories += ",";
        }
    }
    catagories += "]";
    var legendName = ['产液量(t/d)', '产油量(t/d)', '含水率(%)'];
    var BDlegendName = ['产量波动(%)'];
    var series = "[";
    var BDseries = "[";
    for (var i = 0; i < legendName.length; i++) {
        if (i == 0) {
            BDseries += "{\"name\":\"" + BDlegendName[i] + "\",\"data\":[";
        }
        series += "{\"name\":\"" + legendName[i] + "\",";
        if (i == 2) {
            series += "\"yAxis\":1,";
        }
        series += "\"data\":[";
        for (var j = 0; j < data.length; j++) {
            var year = parseInt(data[j].jssj.split("-")[0]);
            var month = parseInt(data[j].jssj.split("-")[1]);
            var day = parseInt(data[j].jssj.split("-")[2]);


            if (i == 0) {
                series += "[" + Date.UTC(year, month - 1, day) + "," + data[j].rcyl + "]";
                BDseries += "[" + Date.UTC(year, month - 1, day) + "," + data[j].rcylbd + "]";
                if (j != data.length - 1) {
                    BDseries += ",";
                }
            } else if (i == 1) {
                series += "[" + Date.UTC(year, month - 1, day) + "," + data[j].rcyl1 + "]";
            } else if (i == 2) {
                series += "[" + Date.UTC(year, month - 1, day) + "," + data[j].hsl + "]";
            }

            if (j != data.length - 1) {
                series += ",";
            }
        }
        series += "]}";
        if (i == 0) {
            BDseries += "]}";
        }
        if (i != legendName.length - 1) {
            series += ",";
        }
    }
    series += "]";
    BDseries += "]";
    var cat = Ext.JSON.decode(catagories);
    var ser = Ext.JSON.decode(series);
    var BDser = Ext.JSON.decode(BDseries);
    var color = ['#800000', // 红
       '#008C00', // 绿
       '#000000', // 黑
       '#0000FF', // 蓝
       '#F4BD82', // 黄
       '#FF00FF' // 紫
     ];
    var tabPanel = Ext.getCmp("DiagnosisTotalCurveTabpanel_Id");
    var activeId = tabPanel.getActiveTab().id;
    if (activeId == "DiagnosisTotalProCurvePanel_Id") {
        initDiagnosisTotalCurveChartFn(cat, ser, tickInterval, "DiagnosisTotalProCurveDiv_Id", get_rawData.jh + "井 产量曲线", "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "日期", color);
    } else {
        initDiagnosisTotalFluChartFn(cat, BDser, tickInterval, "DiagnosisTotalFluCurveDiv_Id", get_rawData.jh + "井 产量波动曲线", "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "日期", color);
    }

    return false;
};

function initDiagnosisTotalCurveChartFn(catagories, series, tickInterval, divId, title, subtitle, xtitle, color) {
    mychart = new Highcharts.Chart({
        chart: {
            renderTo: divId,
            type: 'spline',
            shadow: true,
            //alignTicks: false,
            borderWidth: 0,
            zoomType: 'xy'
        },
        credits: {
            enabled: false
        },
        title: {
            text: title
        },
        subtitle: {
            text: subtitle
        },
        colors: color,
        xAxis: {
            type: 'datetime',
            title: {
                text: xtitle
            },
            labels: {
                formatter: function () {
                    return Highcharts.dateFormat("%Y-%m-%d", this.value);
                },
                rotation: 0, //倾斜度，防止数量过多显示不全  
                step: 2
            }
        },
        yAxis: [{
            lineWidth: 1,
            min: 0,
            //max:200,
            title: {
                text: cosog.string.cl,
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value, 2);
                }
            },
            plotLines: [{
                color: '#d12',
                dashStyle: 'Dash', //Dash,Dot,Solid,默认Solid
                label: {
                    text: '100%',
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: 100, //y轴显示位置
                zIndex: 10
       }]
      }, {
            lineWidth: 1,
            min: 0,
            max: 100,
            opposite: true,
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value, 2);
                }
            },
            title: {
                text: cosog.string.hsl,
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            }
    }],
        tooltip: {
            crosshairs: true, //十字准线
            style: {
                color: '#333333',
                fontSize: '12px',
                padding: '8px'
            },
            dateTimeLabelFormats: {
                millisecond: '%Y-%m-%d %H:%M:%S.%L',
                second: '%Y-%m-%d %H:%M:%S',
                minute: '%Y-%m-%d %H:%M',
                hour: '%Y-%m-%d %H',
                day: '%Y-%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            }
        },
        exporting: {
            enabled: true,
            filename: 'class-booking-chart',
            url: context + '/exportHighcharsPicController/export'
        },
        plotOptions: {
            spline: {
                lineWidth: 1,
                fillOpacity: 0.3,
                marker: {
                    enabled: true,
                    radius: 3, //曲线点半径，默认是4
                    //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                    states: {
                        hover: {
                            enabled: true,
                            radius: 6
                        }
                    }
                },
                shadow: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: series
    });
};

function initDiagnosisTotalFluChartFn(catagories, series, tickInterval, divId, title, subtitle, xtitle, color) {
    mychart = new Highcharts.Chart({
        chart: {
            type: 'spline',
            renderTo: divId,
            shadow: true,
            borderWidth: 0,
            zoomType: 'xy'
        },
        credits: {
            enabled: false
        },
        title: {
            text: title
        },
        subtitle: {
            text: subtitle
        },
        colors: color,
        xAxis: {
            //					categories : catagories,
            type: 'datetime',
            //					tickInterval : 5,
            title: {
                text: xtitle
            },
            labels: {
                formatter: function () {
                    return Highcharts.dateFormat("%Y-%m-%d", this.value);
                },
                rotation: 0, //倾斜度，防止数量过多显示不全  
                step: 2
            }
        },
        yAxis: [{
            lineWidth: 1,
            title: {
                text: '波动(%)',
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value, 2);
                }
            },
            plotLines: [{
                color: '#d12',
                dashStyle: 'Dash', //Dash,Dot,Solid,默认Solid
                label: {
                    text: '100%',
                    align: 'right',
                    x: -10
                },
                width: 3,
                zIndex:10,
                value: 100, //y轴显示位置
                zIndex: 10
       }]
      }],
        tooltip: {
            crosshairs: true, //十字准线
            style: {
                color: '#333333',
                fontSize: '12px',
                padding: '8px'
            },
            dateTimeLabelFormats: {
                millisecond: '%Y-%m-%d %H:%M:%S.%L',
                second: '%Y-%m-%d %H:%M:%S',
                minute: '%Y-%m-%d %H:%M',
                hour: '%Y-%m-%d %H',
                day: '%Y-%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            }
        },
        exporting: {
            enabled: true,
            filename: 'class-booking-chart',
            url: context + '/exportHighcharsPicController/export'
        },
        plotOptions: {
            series: {
                allowPointSelect: true
            },
            spline: {
                lineWidth: 1,
                fillOpacity: 0.3,
                marker: {
                    enabled: true,
                    radius: 3, //曲线点半径，默认是4
                    states: {
                        hover: {
                            enabled: true,
                            radius: 6
                        }
                    }
                },
                shadow: true //阴影
            }
        },
        legend: {
            enabled: true,
            layout: 'vertical', //图例数据项的布局。布局类型： "horizontal" 或 "vertical" 即水平布局和垂直布局 默认是：horizontal.
            align: 'right', //设定图例在图表区中的水平对齐方式，合法值有left，center 和 right,默认为center
            verticalAlign: 'middle', //设定图例在图表区中的垂直对齐方式，合法值有 top，middle 和 bottom,默认为bottom
            borderWidth: 0,
            floating: false //图例是否浮动，设置浮动后，图例将不占位置
        },
        series: series
    });
}

DiagnosisTotalDataCurveChartFn = function (get_rawData, itemName, divId) {
    var tickInterval = 1;
    var data = get_rawData.totalRoot;
    tickInterval = Math.floor(data.length / 10) + 1;
    var catagories = "[";
    var title = get_rawData.wellName + "井" + itemName.split("(")[0] + "曲线";
    for (var i = 0; i < data.length; i++) {
        catagories += "\"" + data[i].cjsj + "\"";
        if (i < data.length - 1) {
            catagories += ",";
        }
    }
    catagories += "]";
    var legendName = [itemName.split("(")[0]];
    if (get_rawData.itemNum > 1) {
    	legendName = ["最大值","最小值","平均值"];
    }
    var series = "[";
    for (var i = 0; i < legendName.length; i++) {
        series += "{\"name\":\"" + legendName[i] + "\",";
        series += "\"data\":[";
        for (var j = 0; j < data.length; j++) {
            var year = parseInt(data[j].calculateDate.split(" ")[0].split("-")[0]);
            var month = parseInt(data[j].calculateDate.split(" ")[0].split("-")[1]);
            var day = parseInt(data[j].calculateDate.split(" ")[0].split("-")[2]);
            if (i == 0) {
            	series += "[" + Date.parse(data[j].calculateDate.replace(/-/g, '/')) + "," + data[j].maxValue + "]";
            } else if (i == 1) {
                series += "[" + Date.parse(data[j].calculateDate.replace(/-/g, '/')) + "," + data[j].minValue + "]";
            } else if (i == 2) {
                series += "[" + Date.parse(data[j].calculateDate.replace(/-/g, '/')) + "," + data[j].value + "]";
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
    var cat = Ext.JSON.decode(catagories);
    var ser = Ext.JSON.decode(series);
    var color = ['#800000', // 红
       '#008C00', // 绿
       '#000000', // 黑
       '#0000FF', // 蓝
       '#F4BD82', // 黄
       '#FF00FF' // 紫
     ];

    initDiagnosisTotalDataCurveChartFn(cat, ser, tickInterval, divId, title, "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "时间", itemName, color);

    return false;
};

function initDiagnosisTotalDataCurveChartFn(catagories, series, tickInterval, divId, title, subtitle, xtitle, ytitle, color) {
	Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
	mychart = new Highcharts.Chart({
        chart: {
            renderTo: divId,
            type: 'spline',
            shadow: true,
            borderWidth: 0,
            zoomType: 'xy'
        },
        credits: {
            enabled: false
        },
        title: {
            text: title
        },
        subtitle: {
            text: subtitle
        },
        colors: color,
        xAxis: {
            type: 'datetime',
            title: {
                text: xtitle
            },
            labels: {
                formatter: function () {
                    return Highcharts.dateFormat("%Y-%m-%d", this.value);
                },
                rotation: 0, //倾斜度，防止数量过多显示不全  
                step: 2
            }
        },
        yAxis: [{
            lineWidth: 1,
            //							min:0,
            //max:200,
            title: {
                text: ytitle,
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            },
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value, 2);
                }
            }
      }],
        tooltip: {
            crosshairs: true, //十字准线
            style: {
                color: '#333333',
                fontSize: '12px',
                padding: '8px'
            },
            dateTimeLabelFormats: {
                millisecond: '%Y-%m-%d %H:%M:%S.%L',
                second: '%Y-%m-%d %H:%M:%S',
                minute: '%Y-%m-%d %H:%M',
                hour: '%Y-%m-%d %H',
                day: '%Y-%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            }
        },
        exporting: {
            enabled: true,
            filename: 'class-booking-chart',
            url: context + '/exportHighcharsPicController/export'
        },
        plotOptions: {
            spline: {
                lineWidth: 1,
                fillOpacity: 0.3,
                marker: {
                    enabled: true,
                    radius: 3, //曲线点半径，默认是4
                    //symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                    states: {
                        hover: {
                            enabled: true,
                            radius: 6
                        }
                    }
                },
                shadow: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            enabled: true,
            borderWidth: 0
        },
        series: series
    });
};