Ext.define('AP.view.diagnosis.SinglePanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.singlePanel',
//    id: "SingleWellList_Id",
    layout: "fit",
    border: false,
    initComponent: function () {
//    	Ext.create("AP.store.diagnosis.WorkStatusStatisticsInfoStore");
        var jhStore_A = new Ext.data.JsonStore({
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
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('singleJh_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName: wellName,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var simpleCombo_A = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: 'singleJh_Id',
                store: jhStore_A,
                labelWidth: 35,
                width: 125,
                queryMode: 'remote',
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                typeAhead: true,
                autoSelect: false,
                allowBlank: true,
                triggerAction: 'all',
                editable: true,
                displayField: "boxval",
                valueField: "boxkey",
                pageSize: comboxPagingStatus,
                minChars: 0,
                listeners: {
                    expand: function (sm, selections) {
//                        simpleCombo_A.clearValue();
                        simpleCombo_A.getStore().loadPage(1); // 加载井下拉框的store
                    },
                    specialkey: function (field, e) {
//                        onEnterKeyDownFN(field, e, 'DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id');
                    },
                    select: function (combo, record, index) {
                    	var statPanelId=getRealtimeWellDataPanelId().replace("WellList","StatGraph");
                        if(combo.value==""){
                        	Ext.getCmp("SingleWellList_Id").setTitle("统计数据");
                        	Ext.getCmp("DiagnosisAnalysisStartDate_Id").hide();
                      	  	Ext.getCmp("DiagnosisAnalysisEndDate_Id").hide();
                      	  
                      	  	Ext.getCmp("DiagnosisAnalysisAllBtn_Id").hide();
                            Ext.getCmp("DiagnosisAnalysisHisBtn_Id").show();
                        	Ext.getCmp(statPanelId).expand(true);
                        }else{
                        	Ext.getCmp("SingleWellList_Id").setTitle("单井历史");
                        	
                        	Ext.getCmp("DiagnosisAnalysisStartDate_Id").show();
                        	Ext.getCmp("DiagnosisAnalysisEndDate_Id").show();
                        	
                        	Ext.getCmp("DiagnosisAnalysisHisBtn_Id").hide();
                            Ext.getCmp("DiagnosisAnalysisAllBtn_Id").show();
                        	Ext.getCmp(statPanelId).collapse();
                        }
                        Ext.getCmp('DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id').getStore().loadPage(1);
                    }
                }
            });
        
        Ext.apply(this, {
        	tbar: [ // 定义topbar
                simpleCombo_A,
                {
                    id: 'PumpingRTSelectedStatValue_Id',//选择的统计项的值
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'DiagnosisAnalysisCurveItem_Id',//选择查看曲线的数据项名称
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'DiagnosisAnalysisCurveItemCode_Id',//选择查看曲线的数据项代码
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, {
                    id: 'DiagnosisAnalysisColumnStr_Id',//选择查看曲线的数据项代码
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }, "-",{
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: '',
                    labelWidth: 0,
                    width: 90,
                    format: 'Y-m-d ',
                    id: 'DiagnosisAnalysisStartDate_Id',
                    value: '',
                    listeners: {
                    	select: function (combo, record, index) {
                    		Ext.getCmp('DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id').getStore().loadPage(1);
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
                    id: 'DiagnosisAnalysisEndDate_Id',
                    value: new Date(),
                    listeners: {
                    	select: function (combo, record, index) {
                    		Ext.getCmp('DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id').getStore().loadPage(1);
                        }
                    }
                }, {
                    xtype: 'button',
                    text: cosog.string.exportExcel,
                    pressed: true,
                    handler: function (v, o) {
                        exportSingleFSDiagramAnalysisDataExcel();
                    }
                }, '->', {
                    xtype: 'button',
                    text:'单井历史',
                    tooltip:'点击按钮或者双击表格，查看单井历史数据',
                    id:'DiagnosisAnalysisHisBtn_Id',
                    pressed: true,
                    hidden: false,
                    handler: function (v, o) {
                    	Ext.getCmp("SingleWellList_Id").setTitle("单井历史");
                    	Ext.getCmp("DiagnosisAnalysisStartDate_Id").show();
                    	Ext.getCmp("DiagnosisAnalysisEndDate_Id").show();
                    	
                    	Ext.getCmp("DiagnosisAnalysisHisBtn_Id").hide();
                        Ext.getCmp("DiagnosisAnalysisAllBtn_Id").show();
                        
                        var statPanelId=getRealtimeWellDataPanelId().replace("WellList","StatGraph");
                        Ext.getCmp(statPanelId).collapse();
                        
                        var jh  = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id").getSelectionModel().getSelection()[0].data.jh;
                        Ext.getCmp("singleJh_Id").setValue(jh);
                        Ext.getCmp("singleJh_Id").setRawValue(jh);
                        Ext.getCmp('DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id').getStore().loadPage(1);
                    }
              }, {
                  xtype: 'button',
                  text:'返回统计',
                  id:'DiagnosisAnalysisAllBtn_Id',
                  pressed: true,
                  hidden: true,
                  handler: function (v, o) {
                	  Ext.getCmp("SingleWellList_Id").setTitle("统计数据");
                	  Ext.getCmp("DiagnosisAnalysisStartDate_Id").hide();
                	  Ext.getCmp("DiagnosisAnalysisEndDate_Id").hide();
                	  
                	  Ext.getCmp("DiagnosisAnalysisAllBtn_Id").hide();
                      Ext.getCmp("DiagnosisAnalysisHisBtn_Id").show();
                      
                      var statPanelId=getRealtimeWellDataPanelId().replace("WellList","StatGraph");
                      Ext.getCmp(statPanelId).expand(true);
                      
                      Ext.getCmp("singleJh_Id").setValue('');
                      Ext.getCmp("singleJh_Id").setRawValue('');
                      Ext.getCmp('DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id').getStore().loadPage(1);
                  }
              },{
                    xtype: 'button',
                    text: '不正常井',
                    pressed: true,
                    hidden:true,
                    id: 'annormalWellBtn_Id',
                    handler: function (v, o) {
                        Ext.getCmp("annormalWellBtn_Id").hide();
                        Ext.getCmp("normalWellBtn_Id").show();
                        var AbnormalWellType_Id = Ext.getCmp("WellWorkStatusType_Id");
                        AbnormalWellType_Id.setValue("1");
                        Ext.getCmp('singleJh_Id').setValue("");
                        Ext.getCmp("WellWorkStatusSelectedGklx_Id").setValue("0");
                        Ext.getCmp("WellWorkStatusSelectedGkmc_Id").setValue("");
                        Ext.create("AP.store.diagnosis.WorkStatusStatisticsInfoStore");
                    }
                }, {
                    xtype: 'button',
                    text: '全部井',
                    pressed: true,
                    id: 'normalWellBtn_Id',
                    hidden: true,
                    handler: function (v, o) {
                        Ext.getCmp("normalWellBtn_Id").hide();
                        Ext.getCmp("annormalWellBtn_Id").show();
                        var AbnormalWellType_Id = Ext.getCmp("WellWorkStatusType_Id");
                        AbnormalWellType_Id.setValue("0");
                        Ext.getCmp('WellWorkStatusSelectedGklx_Id').setValue("0");
                        Ext.getCmp("WellWorkStatusSelectedGkmc_Id").setValue("");
                        var jh_tobj = Ext.getCmp('singleJh_Id');
                        if (!Ext.isEmpty(jh_tobj)) {
                            jh_tobj.setValue("");
                        }
                        Ext.create("AP.store.diagnosis.WorkStatusStatisticsInfoStore");
                    }
                },{
                    xtype: 'button',
                    itemId: 'editWellWorkStaBtnId',
                    id: 'editWellWorkStaBtn_Id',
                    text: '工况干预',
                    hidden:true,
                    pressed: true,
//                    iconCls: 'edit',
                    handler: function () {
                        modifyWellInfo();
                    }
                }, {
                    id: 'DiagnosisPumpingUnit_SingleDinagnosisAnalysisTotalCount_Id',
                    xtype: 'component',
                    hidden: true,
                    tpl: cosog.string.totalCount + ': {count}',
                    style: 'margin-right:15px'
            }],
            items: {
                xtype: 'tabpanel',
                id:'DiagnosisRealStatTabpanel_Id',
                activeTab: 0,
                border: true,
                header: false,
                collapsible: true, // 是否折叠
                split: true, // 竖折叠条
                tabPosition: 'top',
                items: [{
                	xtype: 'tabpanel',
                	tabPosition: 'right',
                	title:'工况',
                	iconCls: 'select',
                	id: 'DiagnosisGkStatPiePanel_Id',
                	tabRotation:1,
                	items: [{
                        title:'功图工况',
                        border: false,
                        iconCls: 'dtgreen',
                        layout: 'border',
                        id: 'GTWorkStatusStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListGkmc_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphGkmc_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="GTWorkStatusStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#GTWorkStatusStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#GTWorkStatusStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'GTWorkStatusStatisGraphPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                    },{
                        title:'电参工况',
                        border: false,
                        layout: 'border',
                        id: 'DCWorkStatusStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListEGkmc_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphEGkmc_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="DCWorkStatusStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#DCWorkStatusStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#DCWorkStatusStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'DCWorkStatusStatisGraphPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                    }],
                	listeners: {
                    	tabchange: function (tabPanel, newCard, oldCard,obj) {
                    		newCard.setIconCls("dtgreen");
                    		oldCard.setIconCls("");
                    		loadStatData();
                        }
                    }
                },{
                	xtype: 'tabpanel',
                	tabPosition: 'right',
                	title:'产量',
                	id: 'ProductionAndFluStatisGraphPiePanel_Id',
                	tabRotation:1,
                	items: [{
                    	title:'产量分布',
                        border: false,
                        iconCls: 'dtgreen',
                        layout: 'border',
                        id: 'ProductionStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListProdDist_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphProdDist_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="ProductionStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#ProductionStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#ProductionStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'ProductionStatisGraphPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                    },{
                        title: '产量波动',
                        border: false,
                        layout: 'border',
                        id: 'ProductionFluStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListProdFluc_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphProdFluc_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="ProductionFluStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    if ($("#ProductionFluStatisGraphPieDiv_Id").highcharts() != undefined && $("#ProductionFluStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#ProductionFluStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'ProductionFluStatisGraphPieDiv_Id',
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
                        	loadStatData();
                        }
                    }
                },{
                	xtype: 'tabpanel',
                	tabPosition: 'right',
                	title:'平衡',
                	id: 'BalanceStatisGraphPiePanel_Id',
                	tabRotation:1,
                	items: [{
                    	title:'功率平衡',
                        border: false,
                        iconCls: 'dtgreen',
                        layout: 'border',
                        id: 'PowerBalanceStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListPowerBalance_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphPowerBalance_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="PowerBalanceStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#PowerBalanceStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#PowerBalanceStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'PowerBalanceStatisGraphPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                    },{
                    	title:'电流平衡',
                        border: false,
                        layout: 'border',
                        id: 'CurrentBalanceStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListCurrentBalance_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphCurrentBalance_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="CurrentBalanceStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#CurrentBalanceStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#CurrentBalanceStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'CurrentBalanceStatisGraphPieDiv_Id',
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
                        	loadStatData();
                        }
                    }
                },{
                	xtype: 'tabpanel',
                	tabPosition: 'right',
                	title:'时率',
                	id: 'ScslStatisGraphPiePanel_Id',
                	tabRotation:1,
                	items: [{
                        title: '运行状态',
                        border: false,
                        iconCls: 'dtgreen',
                        layout: 'border',
                        id: 'YxztStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListRunStatus_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphRunStatus_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="YxztStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    if ($("#YxztStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#YxztStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'YxztStatisGraphPieDiv_Id',
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
                        id: 'SlfbStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListTimeDist_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphTimeDist_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="SlfbStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    if ($("#SlfbStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#SlfbStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'SlfbStatisGraphPieDiv_Id',
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
                        	loadStatData();
                        }
                    }
                },{
                	xtype: 'tabpanel',
                	tabPosition: 'right',
                	title:'效率',
                	id: 'EffStatisGraphPiePanel_Id',
                	tabRotation:1,
                	items: [{
                    	title:'系统效率',
                        border: false,
                        iconCls: 'dtgreen',
                        layout: 'border',
                        id: 'SysEffStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListSystemEff_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphSystemEff_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="SysEffStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#SysEffStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#SysEffStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'SysEffStatisGraphPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                    },{
                        title: '地面效率',
                        border: false,
                        layout: 'border',
                        id: 'DmEffStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListSurfaceEff_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphSurfaceEff_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="DmEffStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#DmEffStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#DmEffStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'DmEffStatisGraphPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                    },{
                        title: '井下效率',
                        border: false,
                        layout: 'border',
                        id: 'JxEffStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListDownholeEff_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphDownholeEff_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="JxEffStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    if ($("#JxEffStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#JxEffStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'JxEffStatisGraphPieDiv_Id',
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
                        	loadStatData();
                        }
                    }
                },{

                	xtype: 'tabpanel',
                	tabPosition: 'right',
                	title:'电量',
                	id: 'PowerPiePanel_Id',
                	tabRotation:1,
                	items: [{
                		title:'日用电量',
                        border: false,
                        iconCls: 'dtgreen',
                        layout: 'border',
                        id: 'PowerDistPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListPowerDist_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphPowerDist_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="PowerDistPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#PowerDistPieDiv_Id").highcharts() != undefined) {
                                        $("#PowerDistPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'PowerDistPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                	}],
                	listeners: {
                    	tabchange: function (tabPanel, newCard, oldCard,obj) {
                    		newCard.setIconCls("dtgreen");
                    		oldCard.setIconCls("");
                    		loadStatData();
                        }
                    }
                },{
                	xtype: 'tabpanel',
                	tabPosition: 'right',
                	title:'通信',
                	id: 'CommGraphPiePanel_Id',
                	tabRotation:1,
                	items: [{
                		title:'通信状态',
                        border: false,
                        iconCls: 'dtgreen',
                        layout: 'border',
                        id: 'TxztStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListCommStatus_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphCommStatus_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="TxztStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#TxztStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#TxztStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'TxztStatisGraphPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                	},{
                		title:'在线时率',
                        border: false,
                        layout: 'border',
                        id: 'TxslStatisGraphPiePanel_Id',
                        items:[{
                        	region: 'center',
                            id:'SinglePanelWellListCommDist_Id',
                            header: false,
                            layout: 'fit'
                        },{
                        	region: 'south',
                        	id:'SinglePanelStatGraphCommDist_Id',
                            height: '50%',
                            border: true,
                            header: false,
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html: '<div id="TxslStatisGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	if ($("#TxslStatisGraphPieDiv_Id").highcharts() != undefined) {
                                        $("#TxslStatisGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                    }else{
                                    	Ext.create('Ext.tip.ToolTip', {
                                            target: 'TxslStatisGraphPieDiv_Id',
                                            html: '点击饼图不同区域或标签，查看相应统计数据'
                                        });
                                    }
                                }
                            }
                        }]
                	}],
                	listeners: {
                    	tabchange: function (tabPanel, newCard, oldCard,obj) {
                    		newCard.setIconCls("dtgreen");
                    		oldCard.setIconCls("");
                    		loadStatData();
                        }
                    }
                }],
                listeners: {
                	tabchange: function (tabPanel, newCard, oldCard,obj) {
                		oldCard.setIconCls("");
                		newCard.setIconCls("select");
                		loadStatData();
                    }
                }
            },
            listeners: {
            	afterrender: function (comp,eOpts) {
//            		Ext.getCmp("DiagnosisRealStatTabpanel_Id").getTabBar().insert(0, {
//            		      xtype: 'tbfill'
//            		});
            		Ext.getCmp("DiagnosisGkStatPiePanel_Id").getTabBar().insert(0, {
          		      	xtype: 'tbfill'
            		});
            		Ext.getCmp("ProductionAndFluStatisGraphPiePanel_Id").getTabBar().insert(0, {
          		      	xtype: 'tbfill'
            		});
            		Ext.getCmp("BalanceStatisGraphPiePanel_Id").getTabBar().insert(0, {
          		      	xtype: 'tbfill'
            		});
            		Ext.getCmp("ScslStatisGraphPiePanel_Id").getTabBar().insert(0, {
          		      	xtype: 'tbfill'
            		});
            		Ext.getCmp("EffStatisGraphPiePanel_Id").getTabBar().insert(0, {
          		      	xtype: 'tbfill'
            		});
            		Ext.getCmp("PowerPiePanel_Id").getTabBar().insert(0, {
          		      	xtype: 'tbfill'
            		});
            		Ext.getCmp("CommGraphPiePanel_Id").getTabBar().insert(0, {
          		      	xtype: 'tbfill'
            		});
                }
            }
        });
        this.callParent(arguments);
        
    }
});

function modifyWellInfo() {
        var AbnormalWellListGrid = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id");
        var wellPanel_model = AbnormalWellListGrid.getSelectionModel();
        var _record = wellPanel_model.getSelection();
        if (_record.length > 0) {
            var win_Obj = Ext.getCmp("AbnormalWellInfo_addwin_Id")
            if (win_Obj != undefined) {
                win_Obj.destroy();
            }
            var AbnormalWellWorkStaEditWindow = Ext.create("AP.view.abnormalWell.AbnormalWellWorkStaEditWindow", {
                title: cosog.string.gksd
            });
            AbnormalWellWorkStaEditWindow.show();
            SelectWellSetInfoPanel();
        } else {
            Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
        }
    }
    // 复值
SelectWellSetInfoPanel = function () {
    var dataattr_row = Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id").getSelectionModel().getSelection();
    var jlbh = dataattr_row[0].data.id;
    var jh = dataattr_row[0].data.jh;
    var jbh = dataattr_row[0].data.jbh;
    var gtcjsj = dataattr_row[0].data.gtcjsj;
    var gkmc = dataattr_row[0].data.gkmc;
    var gklx = dataattr_row[0].data.gklx;
    var gtbh = dataattr_row[0].data.gtbh;
    var gkjgly = dataattr_row[0].data.gkjgly;
    Ext.getCmp('abnormalWelljlbh_Id').setValue(jlbh);
    Ext.getCmp('abnormalWelljh_Id').setValue(jbh);
    Ext.getCmp('abnormalWelljh_Id1').setValue(jh);
    //Ext.getCmp('jh_Id1').setRawValue(jh);
    Ext.getCmp('abnormalWellgklx_Id').setValue(gklx);
    Ext.getCmp('abnormalWellgklx_Id1').setValue(gkmc);
    Ext.getCmp('abnormalWellgklx_Id1').setRawValue(gkmc);
    if (gkjgly == "软件计算") {
        Ext.getCmp('abnormalWellgkjgly1').setValue(true);
    } else {
        Ext.getCmp('abnormalWellgkjgly2').setValue(true);
    }
};

//窗体上的修改按钮事件
function UpdateAbnormalWellSubmitBtnForm() {
    var getUpdateDataSubmitBtnFormId = Ext.getCmp("AbnormalWellInfo_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("AbnormalWellInfo_addwin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataSubmitBtnFormId.getForm().submit({
            url: context + '/abnormalWellManagerController/doAbnormalWellInformationEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("AbnormalWellInfo_addwin_Id").getEl().unmask();
                Ext.getCmp('AbnormalWellInfo_addwin_Id').close();
                Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id").getStore().loadPage(1);

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.success, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.success,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("AbnormalWellInfo_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

function exportSingleFSDiagramAnalysisDataExcel() {
	var gridId = "DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id";
    var url = context + '/diagnosisAnalysisOnlyController/exportProductionWellRTAnalysisDataExcel';
    var fileName = getRealtimeExportExcelTitle();
    var title =  getRealtimeExportExcelTitle();
    
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var jh = Ext.getCmp('singleJh_Id').getValue();
    var statValue = Ext.getCmp('PCPRPMAnalysisSingleDetailsSelectedStatValue_Id').getValue();
    var startDate=Ext.getCmp('DiagnosisAnalysisStartDate_Id').rawValue;
    var endDate=Ext.getCmp('DiagnosisAnalysisEndDate_Id').rawValue;
    var type=getSelectStatType();
    var wellType=200;
    
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

function loadStatData() {
	var selectjh=Ext.getCmp('singleJh_Id').getValue();
	var statPanelId=getRealtimeWellDataPanelId().replace("WellList","StatGraph");
	if(selectjh==null||selectjh==""){
    	Ext.getCmp(statPanelId).expand(true);
    }else{
    	Ext.getCmp(statPanelId).collapse();
    }
	Ext.getCmp("PumpingRTSelectedStatValue_Id").setValue('');
	var gridPanel=Ext.getCmp("DiagnosisPumpingUnit_SingleDinagnosisAnalysis_Id");
	if(isNotVal(gridPanel)){
		gridPanel.destroy();
	}
	Ext.create("AP.store.diagnosis.WorkStatusStatisticsInfoStore");
}

function getSelectStatType() {
	var type='GKLX';
	var tabPanel = Ext.getCmp("DiagnosisRealStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="DiagnosisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("DiagnosisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="GTWorkStatusStatisGraphPiePanel_Id"){//功图工况
			type='GKLX';
		}else if(activeId2=="DCWorkStatusStatisGraphPiePanel_Id"){//电参工况
			type='DCGKLX';
		}
	}else if(activeId=="ProductionAndFluStatisGraphPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ProductionAndFluStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ProductionStatisGraphPiePanel_Id"){//产量分布
			type='CYL'
		}else if(activeId2=="ProductionFluStatisGraphPiePanel_Id"){//产量波动
			type='CYLBD'
		}
	}else if(activeId=="BalanceStatisGraphPiePanel_Id"){//平衡
		var tabPanel2 = Ext.getCmp("BalanceStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="PowerBalanceStatisGraphPiePanel_Id"){//功率平衡
			type='GLPHZT'
		}else if(activeId2=="CurrentBalanceStatisGraphPiePanel_Id"){//电流平衡
			type='PHZT'
		}
	}else if(activeId=="ScslStatisGraphPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScslStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="SlfbStatisGraphPiePanel_Id"){//时率分布
			type='SCSL'
		}else if(activeId2=="YxztStatisGraphPiePanel_Id"){//运行状态
			type='YXZT'
		}
	}else if(activeId=="EffStatisGraphPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("EffStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="SysEffStatisGraphPiePanel_Id"){//系统效率
			type='XTXL'
		}else if(activeId2=="DmEffStatisGraphPiePanel_Id"){//地面效率
			type='DMXL'
		}else if(activeId2=="JxEffStatisGraphPiePanel_Id"){//井下效率
			type='JXXL'
		}
	}else if(activeId=="PowerPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("PowerPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="PowerDistPiePanel_Id"){//日用电量
			type='RHDL';
		}
	}else if(activeId=="CommGraphPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("CommGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="TxztStatisGraphPiePanel_Id"){//通信状态
			type='TXZT';
		}else if(activeId2=="TxslStatisGraphPiePanel_Id"){//在线时率
			type='TXSL';
		}
	}else{
		type='GKLX';
	}
	
	return type;
}

function getRealtimeExportExcelTitle() {
	var title='抽油机实时评价-功图工况';
	var tabPanel = Ext.getCmp("DiagnosisRealStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="DiagnosisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("DiagnosisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="GTWorkStatusStatisGraphPiePanel_Id"){//功图工况
			title='抽油机实时评价-功图工况';
		}else if(activeId2=="DCWorkStatusStatisGraphPiePanel_Id"){//电参工况
			title='抽油机实时评价-电参工况';
		}
	}else if(activeId=="ProductionAndFluStatisGraphPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ProductionAndFluStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ProductionStatisGraphPiePanel_Id"){//产量分布
			title='抽油机实时评价-产量分布';
		}else if(activeId2=="ProductionFluStatisGraphPiePanel_Id"){//产量波动
			title='抽油机实时评价-产量波动';
		}
	}else if(activeId=="BalanceStatisGraphPiePanel_Id"){//平衡
		var tabPanel2 = Ext.getCmp("BalanceStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="PowerBalanceStatisGraphPiePanel_Id"){//功率平衡
			title='抽油机实时评价-功率平衡';
		}else if(activeId2=="CurrentBalanceStatisGraphPiePanel_Id"){//电流平衡
			title='抽油机实时评价-电流平衡';
		}
	}else if(activeId=="ScslStatisGraphPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScslStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="SlfbStatisGraphPiePanel_Id"){//时率分布
			title='抽油机实时评价-运行时率';
		}else if(activeId2=="YxztStatisGraphPiePanel_Id"){//运行状态
			title='抽油机实时评价-运行状态';
		}
	}else if(activeId=="EffStatisGraphPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("EffStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="SysEffStatisGraphPiePanel_Id"){//系统效率
			title='抽油机实时评价-系统效率';
		}else if(activeId2=="DmEffStatisGraphPiePanel_Id"){//地面效率
			title='抽油机实时评价-地面效率';
		}else if(activeId2=="JxEffStatisGraphPiePanel_Id"){//井下效率
			title='抽油机实时评价-井下效率';
		}
	}else if(activeId=="PowerPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("PowerPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="PowerDistPiePanel_Id"){//日用电量
			title='抽油机实时评价-日用电量';
		}
	}else if(activeId=="CommGraphPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("CommGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="TxztStatisGraphPiePanel_Id"){//通信状态
			title='抽油机实时评价-通信状态';
		}else if(activeId2=="TxslStatisGraphPiePanel_Id"){//在线时率
			title='抽油机实时评价-在线时率';
		}
	}else{
		title='抽油机实时评价-功图工况';
	}
	
	return title;
}

function getRealtimeWellDataPanelId() {
	var type='SinglePanelWellListGkmc_Id';
	var tabPanel = Ext.getCmp("DiagnosisRealStatTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="DiagnosisGkStatPiePanel_Id"){//工况
		var tabPanel2 = Ext.getCmp("DiagnosisGkStatPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="GTWorkStatusStatisGraphPiePanel_Id"){//功图工况
			type='SinglePanelWellListGkmc_Id';
		}else if(activeId2=="DCWorkStatusStatisGraphPiePanel_Id"){//电参工况
			type='SinglePanelWellListEGkmc_Id';
		}
	}else if(activeId=="ProductionAndFluStatisGraphPiePanel_Id"){//产量
		var tabPanel2 = Ext.getCmp("ProductionAndFluStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="ProductionStatisGraphPiePanel_Id"){//产量分布
			type='SinglePanelWellListProdDist_Id'
		}else if(activeId2=="ProductionFluStatisGraphPiePanel_Id"){//产量波动
			type='SinglePanelWellListProdFluc_Id'
		}
	}else if(activeId=="BalanceStatisGraphPiePanel_Id"){//平衡
		var tabPanel2 = Ext.getCmp("BalanceStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="PowerBalanceStatisGraphPiePanel_Id"){//功率平衡
			type='SinglePanelWellListPowerBalance_Id'
		}else if(activeId2=="CurrentBalanceStatisGraphPiePanel_Id"){//电流平衡
			type='SinglePanelWellListCurrentBalance_Id'
		}
	}else if(activeId=="ScslStatisGraphPiePanel_Id"){//时率
		var tabPanel2 = Ext.getCmp("ScslStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="SlfbStatisGraphPiePanel_Id"){//时率分布
			type='SinglePanelWellListTimeDist_Id'
		}else if(activeId2=="YxztStatisGraphPiePanel_Id"){//运行状态
			type='SinglePanelWellListRunStatus_Id'
		}
	}else if(activeId=="EffStatisGraphPiePanel_Id"){//效率
		var tabPanel2 = Ext.getCmp("EffStatisGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="SysEffStatisGraphPiePanel_Id"){//系统效率
			type='SinglePanelWellListSystemEff_Id'
		}else if(activeId2=="DmEffStatisGraphPiePanel_Id"){//地面效率
			type='SinglePanelWellListSurfaceEff_Id'
		}else if(activeId2=="JxEffStatisGraphPiePanel_Id"){//井下效率
			type='SinglePanelWellListDownholeEff_Id'
		}
	}else if(activeId=="PowerPiePanel_Id"){//电量
		var tabPanel2 = Ext.getCmp("PowerPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="PowerDistPiePanel_Id"){//日用电量
			type='SinglePanelWellListPowerDist_Id';
		}
	}else if(activeId=="CommGraphPiePanel_Id"){//通信状态
		var tabPanel2 = Ext.getCmp("CommGraphPiePanel_Id");
		var activeId2 = tabPanel2.getActiveTab().id;
		if(activeId2=="TxztStatisGraphPiePanel_Id"){//通信状态
			type='SinglePanelWellListCommStatus_Id';
		}else if(activeId2=="TxslStatisGraphPiePanel_Id"){//在线时率
			type='SinglePanelWellListCommDist_Id';
		}
	}else{
		type='SinglePanelWellListGkmc_Id';
	}
	
	return type;
}