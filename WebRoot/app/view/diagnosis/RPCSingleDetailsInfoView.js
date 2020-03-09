Ext.define("AP.view.diagnosis.RPCSingleDetailsInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.RPCSingleDetailsInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
//    	var store =Ext.create('AP.store.diagnosis.SingleAnalysisiListStore');
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
                    var wellName = Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
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
            id: "FSDiagramAnalysisSingleDetailsWellCom_Id",
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
                },
                select: function (combo, record, index) {
                	var statPanelId=getFSDiagramAnalysisSingleStatType().piePanelId;
                	if(combo.value==""){
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsAllBtn_Id").hide();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsHisBtn_Id").show();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").hide();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsEndDate_Id").hide();
                		Ext.getCmp(statPanelId).expand(true);
                	}else{
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsAllBtn_Id").show();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsHisBtn_Id").hide();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").show();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsEndDate_Id").show();
                		Ext.getCmp(statPanelId).collapse();
                	}
                	Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getStore().loadPage(1);
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
                    	title: '统计数据',
                    	layout: "fit",
                    	tbar: [wellComboBox,'-',{
                            xtype: 'datefield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: '',
                            labelWidth: 0,
                            width: 90,
                            format: 'Y-m-d ',
                            id: 'FSDiagramAnalysisSingleDetailsStartDate_Id',
                            value: '',
                            listeners: {
                            	select: function (combo, record, index) {
                            		Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getStore().loadPage(1);
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
                            id: 'FSDiagramAnalysisSingleDetailsEndDate_Id',
                            value: new Date(),
                            listeners: {
                            	select: function (combo, record, index) {
                            		Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getStore().loadPage(1);
                                }
                            }
                        },'-', {
                            xtype: 'button',
                            text: cosog.string.exportExcel,
                            pressed: true,
                            hidden:false,
                            handler: function (v, o) {
                            	exportFSDiagramAnalysisSingleDataExcel();
                            }
                        }, '->', {
                            xtype: 'button',
                            text:'查看历史',
                            tooltip:'点击按钮或者双击表格，查看单井历史数据',
                            id:'FSDiagramAnalysisSingleDetailsHisBtn_Id',
                            pressed: true,
                            hidden: false,
                            handler: function (v, o) {
                        		if(Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection().length>0){
                        			Ext.getCmp("FSDiagramAnalysisSingleDetailsAllBtn_Id").show();
                            		Ext.getCmp("FSDiagramAnalysisSingleDetailsHisBtn_Id").hide();
                            		Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").show();
                            		Ext.getCmp("FSDiagramAnalysisSingleDetailsEndDate_Id").show();
                            		
                            		Ext.getCmp(statPanelId=getFSDiagramAnalysisSingleStatType().piePanelId).collapse();
                            		
                        			var record  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0];
                            		Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').setValue(record.data.wellName);
                                	Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').setRawValue(record.data.wellName);
                                	Ext.getCmp('FSDiagramAnalysisSingleDetails_Id').getStore().loadPage(1);
                        		}
                            }
                      }, {
                          xtype: 'button',
                          text:'返回统计',
                          id:'FSDiagramAnalysisSingleDetailsAllBtn_Id',
                          pressed: true,
                          hidden: true,
                          handler: function (v, o) {
                        	  Ext.getCmp("FSDiagramAnalysisSingleDetailsAllBtn_Id").hide();
                        	  Ext.getCmp("FSDiagramAnalysisSingleDetailsHisBtn_Id").show();
                        	  Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").hide();
                        	  Ext.getCmp("FSDiagramAnalysisSingleDetailsEndDate_Id").hide();
                        	  
                        	  Ext.getCmp(statPanelId=getFSDiagramAnalysisSingleStatType().piePanelId).expand(true);
                        	  
                        	  Ext.getCmp("FSDiagramAnalysisSingleDetailsWellCom_Id").setValue('');
                        	  
                        	  Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getStore().loadPage(1);
                          }
                      },{
                          id: 'FSDiagramAnalysisSingleDetailsSelectedStatValue_Id',//选择的统计项的值
                          xtype: 'textfield',
                          value: '',
                          hidden: true
                      },{
                          id: 'FSDiagramAnalysisSingleDetailsCurveItem_Id', //选择查看曲线的数据项名称
                          xtype: 'textfield',
                          value: '',
                          hidden: true
                      }, {
                          id: 'FSDiagramAnalysisSingleDetailsCurveItemCode_Id', //选择查看曲线的数据项代码
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
                      }],
                      items: {
                    	  xtype: 'tabpanel',
                          id:'FSDiagramAnalysisSingleDetailsStatTabpanel_Id',
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
                          	id: 'FSDiagramAnalysisSingleWorkCondStatTabpanel_Id',
                          	tabRotation:1,
                          	items: [{
                                  title:'功图工况',
                                  border: false,
                                  iconCls: 'dtgreen',
                                  layout: 'border',
                                  id: 'FSDiagramAnalysisSingleWorkCondStatPanel_Id',
                                  items:[{
                                  	  region: 'center',
                                  	  id:'FSDiagramAnalysisSingleWorkCondDataListPanel_Id',
                                  	  header: false,
                                  	  layout: 'fit'
                                  },{
                                  	  region: 'south',
                                  	  id:'FSDiagramAnalysisSingleWorkCondStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleWorkCondStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleWorkCondStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleWorkCondStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleWorkCondStatGraphPieDiv_Id',
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
                              		loadFSDiagramAnalysisSingleStatData();
                                  }
                              }
                          },{
                          	xtype: 'tabpanel',
                          	tabPosition: 'right',
                          	title:'产量',
                          	id: 'FSDiagramAnalysisSingleProdStatTabpanel_Id',
                          	tabRotation:1,
                          	items: [{
                              	title:'产量分布',
                                  border: false,
                                  iconCls: 'dtgreen',
                                  layout: 'border',
                                  id: 'FSDiagramAnalysisSingleProdStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleProdDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleProdStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleProdStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleProdStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleProdStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleProdStatGraphPieDiv_Id',
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
                                  	loadFSDiagramAnalysisSingleStatData();
                                  }
                              }
                          },{
                          	xtype: 'tabpanel',
                          	tabPosition: 'right',
                          	title:'平衡',
                          	id: 'FSDiagramAnalysisSingleBalanceStatTabpanel_Id',
                          	tabRotation:1,
                          	items: [{
                              	title:'功率平衡',
                                  border: false,
                                  iconCls: 'dtgreen',
                                  layout: 'border',
                                  id: 'FSDiagramAnalysisSingleWattBalanceStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleWattBalanceDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleWattBalanceStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleWattBalanceStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleWattBalanceStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleWattBalanceStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleWattBalanceStatGraphPieDiv_Id',
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
                                  id: 'FSDiagramAnalysisSingleIBalanceStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleIBalanceDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleIBalanceStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleIBalanceStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleIBalanceStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleIBalanceStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleIBalanceStatGraphPieDiv_Id',
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
                                  	loadFSDiagramAnalysisSingleStatData();
                                  }
                              }
                          },{
                          	xtype: 'tabpanel',
                          	tabPosition: 'right',
                          	title:'时率',
                          	id: 'FSDiagramAnalysisSingleRunTimeEffStatTabpanel_Id',
                          	tabRotation:1,
                          	items: [{
                                  title: '运行状态',
                                  border: false,
                                  iconCls: 'dtgreen',
                                  layout: 'border',
                                  id: 'FSDiagramAnalysisSingleRunStatusStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleRunStatusDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleRunStatusStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleRunStatusStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                              if ($("#FSDiagramAnalysisSingleRunStatusStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleRunStatusStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleRunStatusStatGraphPieDiv_Id',
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
                                  id: 'FSDiagramAnalysisSingleRunTimeEffStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleRunTimeEffDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleRunTimeEffStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleRunTimeEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                              if ($("#FSDiagramAnalysisSingleRunTimeEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleRunTimeEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleRunTimeEffStatGraphPieDiv_Id',
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
                                  	loadFSDiagramAnalysisSingleStatData();
                                  }
                              }
                          },{
                          	xtype: 'tabpanel',
                          	tabPosition: 'right',
                          	title:'效率',
                          	id: 'FSDiagramAnalysisSingleSysEffStatTabpanel_Id',
                          	tabRotation:1,
                          	items: [{
                              	title:'系统效率',
                                  border: false,
                                  iconCls: 'dtgreen',
                                  layout: 'border',
                                  id: 'FSDiagramAnalysisSingleSysEffStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleSysEffDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleSysEffStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleSysEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleSysEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleSysEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleSysEffStatGraphPieDiv_Id',
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
                                  id: 'FSDiagramAnalysisSingleSurfaceEffStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleSurfaceEffDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleSurfaceEffStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleSurfaceEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleSurfaceEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleSurfaceEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleSurfaceEffStatGraphPieDiv_Id',
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
                                  id: 'FSDiagramAnalysisSingleWellDownEffStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleWellDownEffDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleWellDownEffStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleWellDownEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                              if ($("#FSDiagramAnalysisSingleWellDownEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleWellDownEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleWellDownEffStatGraphPieDiv_Id',
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
                                  	loadFSDiagramAnalysisSingleStatData();
                                  }
                              }
                          },{
                          	xtype: 'tabpanel',
                          	tabPosition: 'right',
                          	title:'电量',
                          	id: 'FSDiagramAnalysisSingleEnergyStatTabpanel_Id',
                          	tabRotation:1,
                          	items: [{
                          		title:'日用电量',
                                  border: false,
                                  iconCls: 'dtgreen',
                                  layout: 'border',
                                  id: 'FSDiagramAnalysisSingleTodayEnergyStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleTodayEnergyDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleTodayEnergyStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleTodayEnergyStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleTodayEnergyStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleTodayEnergyStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleTodayEnergyStatGraphPieDiv_Id',
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
                              		loadFSDiagramAnalysisSingleStatData();
                                  }
                              }
                          },{
                          	xtype: 'tabpanel',
                          	tabPosition: 'right',
                          	title:'通信',
                          	id: 'FSDiagramAnalysisSingleCommEffStatTabpanel_Id',
                          	tabRotation:1,
                          	items: [{
                          		title:'通信状态',
                                  border: false,
                                  iconCls: 'dtgreen',
                                  layout: 'border',
                                  id: 'FSDiagramAnalysisSingleCommStatusStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleCommStatusDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleCommStatusStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleCommStatusStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleCommStatusStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleCommStatusStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleCommStatusStatGraphPieDiv_Id',
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
                                  id: 'FSDiagramAnalysisSingleCommEffStatPanel_Id',
                                  items:[{
                                  	region: 'center',
                                      id:'FSDiagramAnalysisSingleCommEffDataListPanel_Id',
                                      header: false,
                                      layout: 'fit'
                                  },{
                                  	region: 'south',
                                  	id:'FSDiagramAnalysisSingleCommEffStatGraphPanel_Id',
                                      height: '50%',
                                      border: true,
                                      header: false,
                                      collapsible: true, // 是否折叠
                                      split: true, // 竖折叠条
                                      html: '<div id="FSDiagramAnalysisSingleCommEffStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                      listeners: {
                                          resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                          	if ($("#FSDiagramAnalysisSingleCommEffStatGraphPieDiv_Id").highcharts() != undefined) {
                                                  $("#FSDiagramAnalysisSingleCommEffStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                              }else{
                                              	Ext.create('Ext.tip.ToolTip', {
                                                      target: 'FSDiagramAnalysisSingleCommEffStatGraphPieDiv_Id',
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
                              		loadFSDiagramAnalysisSingleStatData();
                                  }
                              }
                          }],
                          listeners: {
                          	tabchange: function (tabPanel, newCard, oldCard,obj) {
                          		oldCard.setIconCls("");
                          		newCard.setIconCls("select");
                          		loadFSDiagramAnalysisSingleStatData();
                              }
                          }
                      },
                      listeners: {
                      	afterrender: function (comp,eOpts) {
                      		Ext.getCmp("FSDiagramAnalysisSingleWorkCondStatTabpanel_Id").getTabBar().insert(0, {
                    		      	xtype: 'tbfill'
                      		});
                      		Ext.getCmp("FSDiagramAnalysisSingleProdStatTabpanel_Id").getTabBar().insert(0, {
                    		      	xtype: 'tbfill'
                      		});
                      		Ext.getCmp("FSDiagramAnalysisSingleBalanceStatTabpanel_Id").getTabBar().insert(0, {
                    		      	xtype: 'tbfill'
                      		});
                      		Ext.getCmp("FSDiagramAnalysisSingleRunTimeEffStatTabpanel_Id").getTabBar().insert(0, {
                    		      	xtype: 'tbfill'
                      		});
                      		Ext.getCmp("FSDiagramAnalysisSingleSysEffStatTabpanel_Id").getTabBar().insert(0, {
                    		      	xtype: 'tbfill'
                      		});
                      		Ext.getCmp("FSDiagramAnalysisSingleEnergyStatTabpanel_Id").getTabBar().insert(0, {
                    		      	xtype: 'tbfill'
                      		});
                      		Ext.getCmp("FSDiagramAnalysisSingleCommEffStatTabpanel_Id").getTabBar().insert(0, {
                    		      	xtype: 'tbfill'
                      		});
                          }
                      }
                }, {
                	region: 'east',
                	title:'数据详情',
                    width: '65%',
                    border: false,
                    collapsible: true, // 是否可折叠
                    collapsed:false,//是否折叠
                    split: true, // 竖折叠条
                    layout: {
                        type: 'hbox',
                        pack: 'start',
                        align: 'stretch'
                    },
                    items: [{
                    	border: false,
                        flex: 2,
                        margin: '0 0 0 0',
                        padding: 0,
                        autoScroll:true,
                        scrollable: true,
                        layout: {
                            type: 'vbox',
                            pack: 'start',
                            align: 'stretch'
                        },
                        items: [
                        	{
                        		border: false,
                        		margin: '0 0 0 0',
//                        		flex: 1,
                        		layout: {
                        	        type: 'hbox',
                        	        pack: 'start',
                        	        align: 'stretch'
                        	    },
                        	    items:[{
                        	    	border: true,
                        	    	margin: '0 0 0 0',
                                    flex: 1,
                                    height:300,
                                    html: '<div id=\"FSDiagramAnalysisSingleDetailsDiv1_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#FSDiagramAnalysisSingleDetailsDiv1_id").highcharts()!=undefined){
                                    			$("#FSDiagramAnalysisSingleDetailsDiv1_id").highcharts().setSize($("#FSDiagramAnalysisSingleDetailsDiv1_id").offsetWidth, $("#FSDiagramAnalysisSingleDetailsDiv1_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    height:300,
                                    html: '<div id=\"FSDiagramAnalysisSingleDetailsDiv2_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#FSDiagramAnalysisSingleDetailsDiv2_id").highcharts()!=undefined){
                                    			$("#FSDiagramAnalysisSingleDetailsDiv2_id").highcharts().setSize($("#FSDiagramAnalysisSingleDetailsDiv2_id").offsetWidth, $("#FSDiagramAnalysisSingleDetailsDiv2_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	},{
                        		border: false,
//                        		flex: 1,
                        		margin: '1 0 0 0',
                        		layout: {
                        	        type: 'hbox',
                        	        pack: 'start',
                        	        align: 'stretch'
                        	    },
                        	    items:[{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    height:300,
                                    html: '<div id=\"FSDiagramAnalysisSingleDetailsDiv3_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#FSDiagramAnalysisSingleDetailsDiv3_id").highcharts()!=undefined){
                                    			$("#FSDiagramAnalysisSingleDetailsDiv3_id").highcharts().setSize($("#FSDiagramAnalysisSingleDetailsDiv3_id").offsetWidth, $("#FSDiagramAnalysisSingleDetailsDiv3_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    height:300,
                                    html: '<div id=\"FSDiagramAnalysisSingleDetailsDiv4_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#FSDiagramAnalysisSingleDetailsDiv4_id").highcharts()!=undefined){
                                    			$("#FSDiagramAnalysisSingleDetailsDiv4_id").highcharts().setSize($("#FSDiagramAnalysisSingleDetailsDiv4_id").offsetWidth, $("#FSDiagramAnalysisSingleDetailsDiv4_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	},{
                        		border: false,
//                        		flex: 1,
                        		margin: '1 0 0 0',
                        		layout: {
                        	        type: 'hbox',
                        	        pack: 'start',
                        	        align: 'stretch'
                        	    },
                        	    items:[{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    height:300,
                                    html: '<div id=\"FSDiagramAnalysisSingleDetailsDiv5_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#FSDiagramAnalysisSingleDetailsDiv5_id").highcharts()!=undefined){
                                    			$("#FSDiagramAnalysisSingleDetailsDiv5_id").highcharts().setSize($("#FSDiagramAnalysisSingleDetailsDiv5_id").offsetWidth, $("#FSDiagramAnalysisSingleDetailsDiv5_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                                    flex: 1,
                                    height:300,
                                    html: '<div id=\"FSDiagramAnalysisSingleDetailsDiv6_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#FSDiagramAnalysisSingleDetailsDiv6_id").highcharts()!=undefined){
                                    			$("#FSDiagramAnalysisSingleDetailsDiv6_id").highcharts().setSize($("#FSDiagramAnalysisSingleDetailsDiv6_id").offsetWidth, $("#FSDiagramAnalysisSingleDetailsDiv6_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	}
                        ]
                    },{
                    	xtype: 'tabpanel',
                        id: 'FSDiagramAnalysisSingleDetailsRightTabPanel_Id',
                        flex: 1,
                        activeTab: 0,
                        header: false,
                        collapsible: true,
                        split: true,
                        collapseDirection: 'right',
                        border: true,
                        tabPosition: 'top',
                        items: [{
                                title: '分析',
                                layout: 'border',
                                items:[
                                	{
                                		region: 'center',
                                		id: 'FSDiagramAnalysisSingleDetailsRightAnalysisPanel_Id',
                                        border: false,
                                        layout: 'fit',
                                        height:'60%'
//                                        collapseDirection:'top'
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
                                        	id:'FSDiagramAnalysisSingleDetailsRightRunRangeTextArea_Id',
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
                                        	id:'FSDiagramAnalysisSingleDetailsRightResultCodeTextArea_Id',
                                        	grow:true,
                                        	width:'99.9%',
                                        	height: '45%',
                                            readOnly:true
                                        }]
                                	}
                                ]
                        }, {
                                title: '采集',
                                id: 'FSDiagramAnalysisSingleDetailsRightAcqPanel_Id',
                                border: false,
                                layout: 'fit',
                                autoScroll: true,
                                scrollable: true
                        }, {
                            title:'控制',
            				border: false,
                            layout: 'border',
                            hideMode:'offsets',
                            id:'FSDiagramAnalysisSingleDetailsRightControlTabPanel_Id',
                            items: [{
                            	region: 'north',
                            	layout: 'fit',
                            	height: '40%',
                            	id:'FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id',
                            	collapsible: true, // 是否折叠
                            	header: false,
                                split: true, // 竖折叠条
                                autoRender:true,
                            	html: ''
                            },{
                            	region: 'center',
                                height: '60%',
                                id:'FSDiagramAnalysisSingleDetailsRightControlPanel_Id',
                				border: false,
                				autoScroll:true,
                                scrollable: true,
                                layout: 'fit',
                                listeners: {
                                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                    }
                                }
                            }]
                        }],
                        listeners: {
                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                        		var selectedLength=Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection().length;
                        		if(newCard.id=="FSDiagramAnalysisSingleDetailsRightControlTabPanel_Id"){
                        			if(selectedLength>0){
                            			var videoUrl=Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.videourl;
                                        if(videoUrl!=undefined&&videoUrl!=""){
                                        	if($("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer")!=null){
                                        		$("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer").html('');
                                        	}
                                        	var videoUrl_rtmp=""; 
                                        	var videoUrl_hls=""; 
                                        	if(videoUrl.indexOf("http")>=0){//hls模式
                                        		videoUrl_hls=videoUrl;
                                        		videoUrl_rtmp=videoUrl.replace("https","http").replace("http://hls","rtmp://rtmp").replace(".m3u8","");
                                        	}else{
                                        		videoUrl_hls=videoUrl.replace("rtmp://rtmp","http://hls")+".m3u8";
                                        		videoUrl_rtmp=videoUrl;
                                        	}
                                        	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").expand(true);
                                        	var videohtml='<video id="FSDiagramAnalysisSingleDetailsRightControlVideoPlayer" style="width:100%;height:100%;"  poster="" controls playsInline webkit-playsinline autoplay><source src="'+videoUrl_rtmp+'" type="rtmp/flv" /><source src="'+videoUrl_hls+'" type="application/x-mpegURL" /></video>';   
                                        	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").update(videohtml);
                                        	if(document.getElementById("FSDiagramAnalysisSingleDetailsRightControlVideoPlayer")!=null){
                                        		var player = new EZUIPlayer('FSDiagramAnalysisSingleDetailsRightControlVideoPlayer');
                                        	}
                                        }else{
                                        	var videohtml=''
                                        	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").update(videohtml);
                                        	Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").collapse();
                                        }
                            		}else{
                        				Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").removeAll();
                        				if($("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer")!=null){
                                    		$("#FSDiagramAnalysisSingleDetailsRightControlVideoPlayer").html('');
                                    	}
                        			}
                        			
                        		}else{
                        			var videohtml='';
                                    Ext.getCmp("FSDiagramAnalysisSingleDetailsRightControlVideoPanel_Id").update(videohtml);
                        		}
                            }
                        }
                    }],
                    listeners: {
                        beforeCollapse: function (panel, eOpts) {
                        	$("#FSDiagramAnalysisSingleDetailsDiv1_id").hide();
                            $("#FSDiagramAnalysisSingleDetailsDiv2_id").hide();
                            $("#FSDiagramAnalysisSingleDetailsDiv3_id").hide();
                            $("#FSDiagramAnalysisSingleDetailsDiv4_id").hide();
                            $("#FSDiagramAnalysisSingleDetailsDiv5_id").hide();
                            $("#FSDiagramAnalysisSingleDetailsDiv6_id").hide();
                        },
                        expand: function (panel, eOpts) {
                        	$("#FSDiagramAnalysisSingleDetailsDiv1_id").show();
                            $("#FSDiagramAnalysisSingleDetailsDiv2_id").show();
                            $("#FSDiagramAnalysisSingleDetailsDiv3_id").show();
                            $("#FSDiagramAnalysisSingleDetailsDiv4_id").show();
                            $("#FSDiagramAnalysisSingleDetailsDiv5_id").show();
                            $("#FSDiagramAnalysisSingleDetailsDiv6_id").show();
                        }
                    }
                }]
             }]
        });
        me.callParent(arguments);
    }

});

function createDiagnosisColumn(columnInfo) {
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
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' ";
        if (attr.dataIndex.toUpperCase() == 'workingConditionName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='workingConditionName_Elec'.toUpperCase()||attr.dataIndex.toUpperCase()=='workingConditionName_E'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceElecWorkingConditionColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='iDegreeBalanceName'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='wattDegreeBalanceName'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return advicePowerBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:true";
        } else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += width_ + ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        } else if (attr.dataIndex.toUpperCase() == 'acquisitionTime'.toUpperCase()) {
            myColumns += width_ + ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

function getFSDiagramAnalysisSingleStatType() {
	var type=1;
	var panelId="FSDiagramAnalysisSingleWorkCondDataListPanel_Id";
	var piePanelId="FSDiagramAnalysisSingleWorkCondStatGraphPanel_Id";
	var pieDivId="FSDiagramAnalysisSingleWorkCondStatGraphPieDiv_Id";
	var pieChartTitle="功图工况";
	var exportExcelTitle='抽油机实时评价-功图工况';
	var activeTabId= Ext.getCmp(Ext.getCmp("FSDiagramAnalysisSingleDetailsStatTabpanel_Id").getActiveTab().id).getActiveTab().id;
	if(activeTabId=="FSDiagramAnalysisSingleWorkCondStatPanel_Id"){//功图工况
		type=1;
		panelId="FSDiagramAnalysisSingleWorkCondDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleWorkCondStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleWorkCondStatGraphPieDiv_Id";
		pieChartTitle="功图工况";
		exportExcelTitle='抽油机实时评价-功图工况';
	}else if(activeTabId=="FSDiagramAnalysisSingleProdStatPanel_Id"){//产量
		type=2;
		panelId="FSDiagramAnalysisSingleProdDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleProdStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleProdStatGraphPieDiv_Id";
		pieChartTitle="产量分布";
		exportExcelTitle='抽油机实时评价-产量分布';
	}else if(activeTabId=="FSDiagramAnalysisSingleWattBalanceStatPanel_Id"){//功率平衡
		type=3;
		panelId="FSDiagramAnalysisSingleWattBalanceDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleWattBalanceStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleWattBalanceStatGraphPieDiv_Id";
		pieChartTitle="功率平衡";
		exportExcelTitle='抽油机实时评价-功率平衡';
	}else if(activeTabId=="FSDiagramAnalysisSingleIBalanceStatPanel_Id"){//电流平衡
		type=4;
		panelId="FSDiagramAnalysisSingleIBalanceDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleIBalanceStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleIBalanceStatGraphPieDiv_Id";
		pieChartTitle="电流平衡";
		exportExcelTitle='抽油机实时评价-电流平衡';
	}else if(activeTabId=="FSDiagramAnalysisSingleRunStatusStatPanel_Id"){//运行状态
		type=5;
		panelId="FSDiagramAnalysisSingleRunStatusDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleRunStatusStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleRunStatusStatGraphPieDiv_Id";
		pieChartTitle="运行状态";
		exportExcelTitle='抽油机实时评价-运行状态';
	}else if(activeTabId=="FSDiagramAnalysisSingleRunTimeEffStatPanel_Id"){//运行时率
		type=6;
		panelId="FSDiagramAnalysisSingleRunTimeEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleRunTimeEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleRunTimeEffStatGraphPieDiv_Id";
		pieChartTitle="运行时率";
		exportExcelTitle='抽油机实时评价-运行时率';
	}else if(activeTabId=="FSDiagramAnalysisSingleSysEffStatPanel_Id"){//系统效率
		type=7;
		panelId="FSDiagramAnalysisSingleSysEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleSysEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleSysEffStatGraphPieDiv_Id";
		pieChartTitle="系统效率";
		exportExcelTitle='抽油机实时评价-系统效率';
	}else if(activeTabId=="FSDiagramAnalysisSingleSurfaceEffStatPanel_Id"){//地面效率
		type=8;
		panelId="FSDiagramAnalysisSingleSurfaceEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleSurfaceEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleSurfaceEffStatGraphPieDiv_Id";
		pieChartTitle="地面效率";
		exportExcelTitle='抽油机实时评价-地面效率';
	}else if(activeTabId=="FSDiagramAnalysisSingleWellDownEffStatPanel_Id"){//井下效率
		type=9;
		panelId="FSDiagramAnalysisSingleWellDownEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleWellDownEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleWellDownEffStatGraphPieDiv_Id";
		pieChartTitle="井下效率";
		exportExcelTitle='抽油机实时评价-井下效率';
	}else if(activeTabId=="FSDiagramAnalysisSingleTodayEnergyStatPanel_Id"){//如用电量
		type=10;
		panelId="FSDiagramAnalysisSingleTodayEnergyDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleTodayEnergyStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleTodayEnergyStatGraphPieDiv_Id";
		pieChartTitle="日用电量";
		exportExcelTitle='抽油机实时评价-日用电量';
	}else if(activeTabId=="FSDiagramAnalysisSingleCommStatusStatPanel_Id"){//通信状态
		type=11;
		panelId="FSDiagramAnalysisSingleCommStatusDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleCommStatusStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleCommStatusStatGraphPieDiv_Id";
		pieChartTitle="通信状态";
		exportExcelTitle='抽油机实时评价-通信状态';
	}else if(activeTabId=="FSDiagramAnalysisSingleCommEffStatPanel_Id"){//在线时率
		type=12;
		panelId="FSDiagramAnalysisSingleCommEffDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleCommEffStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleCommEffStatGraphPieDiv_Id";
		pieChartTitle="在线时率";
		exportExcelTitle='抽油机实时评价-在线时率';
	}
	var result=Ext.JSON.decode("{\"type\":"+type+",\"panelId\":\""+panelId+"\",\"piePanelId\":\""+piePanelId+"\",\"pieDivId\":\""+pieDivId+"\",\"pieChartTitle\":\""+pieChartTitle+"\",\"exportExcelTitle\":\""+exportExcelTitle+"\"}");
	return result;
}

function loadFSDiagramAnalysisSingleStatData() {
	var selectWellName=Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
	var piePanelId=getFSDiagramAnalysisSingleStatType().piePanelId;
	if(selectWellName==null||selectWellName==""){
    	Ext.getCmp(piePanelId).expand(true);
    }else{
    	Ext.getCmp(piePanelId).collapse();
    }
	Ext.getCmp("FSDiagramAnalysisSingleDetailsSelectedStatValue_Id").setValue('');
	var gridPanel=Ext.getCmp("FSDiagramAnalysisSingleDetails_Id");
	if(isNotVal(gridPanel)){
		gridPanel.destroy();
	}
	Ext.create("AP.store.diagnosis.WorkStatusStatisticsInfoStore");
}

function initFSDiagramAnalysisSingleStatPieOrColChat(store) {
	var divid=getFSDiagramAnalysisSingleStatType().pieDivId;
	var title=getFSDiagramAnalysisSingleStatType().pieChartTitle;
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
	ShowFSDiagramAnalysisSingleStatPieChart(title,divid, "井数占", pieData);
}


function ShowFSDiagramAnalysisSingleStatPieChart(title,divid, name, data) {
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
							Ext.getCmp("FSDiagramAnalysisSingleDetailsSelectedStatValue_Id").setValue(e.point.name);
						}else{
							Ext.getCmp("FSDiagramAnalysisSingleDetailsSelectedStatValue_Id").setValue('');
						}
						Ext.getCmp("FSDiagramAnalysisSingleDetailsWellCom_Id").setValue("");
	            		Ext.getCmp("FSDiagramAnalysisSingleDetailsWellCom_Id").setRawValue("");
						Ext.getCmp('FSDiagramAnalysisSingleDetails_Id').getSelectionModel().clearSelections();
                        Ext.getCmp('FSDiagramAnalysisSingleDetails_Id').getStore().loadPage(1);
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

function exportFSDiagramAnalysisSingleDataExcel() {
	var gridId = "FSDiagramAnalysisSingleDetails_Id";
    var url = context + '/diagnosisAnalysisOnlyController/exportProductionWellRTAnalysisDataExcel';
    var fileName = getFSDiagramAnalysisSingleStatType().exportExcelTitle;
    var title =  getFSDiagramAnalysisSingleStatType().exportExcelTitle;
    
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
    var statValue = Ext.getCmp('FSDiagramAnalysisSingleDetailsSelectedStatValue_Id').getValue();
    var startDate=Ext.getCmp('FSDiagramAnalysisSingleDetailsStartDate_Id').rawValue;
    var endDate=Ext.getCmp('FSDiagramAnalysisSingleDetailsEndDate_Id').rawValue;
    var type=getFSDiagramAnalysisSingleStatType().type;
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

DiagnosisDataCurveChartFn = function (get_rawData, itemName, itemCode, divId) {
    var tickInterval = 1;
    var data = get_rawData.totalRoot;
    tickInterval = Math.floor(data.length / 10) + 1;
    var upline = 0,
        downline = 0;
    var uplineName = '',
        downlineName = '';
    var limitlinewidth = 0;
    if (itemCode == 'currenta' || itemCode == 'currentb' || itemCode == 'currentc' || itemCode == 'voltagea' || itemCode == 'voltageb' || itemCode == 'voltagec') {
        upline = parseFloat(get_rawData.uplimit);
        downline = parseFloat(get_rawData.downlimit);
        uplineName = '上限:' + upline;
        downlineName = '下限:' + downline;
        limitlinewidth = 3;
    } else {
        upline = 0;
        downline = 0;
        uplineName = '';
        downlineName = '';
        limitlinewidth = 0;
    }

    var catagories = "[";
    var title = get_rawData.wellName + "井" + itemName.split("(")[0] + "曲线";
    for (var i = 0; i < data.length; i++) {
        catagories += "\"" + data[i].acquisitionTime + "\"";
        if (i < data.length - 1) {
            catagories += ",";
        }
    }
    catagories += "]";
    var legendName = [itemName];
    var series = "[";
    for (var i = 0; i < legendName.length; i++) {
        series += "{\"name\":\"" + legendName[i] + "\",";
        series += "\"data\":[";
        for (var j = 0; j < data.length; j++) {
            var year = parseInt(data[j].acquisitionTime.split(" ")[0].split("-")[0]);
            var month = parseInt(data[j].acquisitionTime.split(" ")[0].split("-")[1]);
            var day = parseInt(data[j].acquisitionTime.split(" ")[0].split("-")[2]);
            var hour = parseInt(data[j].acquisitionTime.split(" ")[1].split(":")[0]);
            var minute = parseInt(data[j].acquisitionTime.split(" ")[1].split(":")[1]);
            var second = parseInt(data[j].acquisitionTime.split(" ")[1].split(":")[2]);
//            series += "[" + Date.UTC(year, month - 1, day, hour, minute, second) + "," + data[j].value + "]";
            series += "[" + Date.parse(data[j].acquisitionTime.replace(/-/g, '/')) + "," + data[j].value + "]";
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

    initDiagnosisDataCurveChartFn(cat, ser, tickInterval, divId, title, "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "时间", itemName, color, upline, downline, uplineName, downlineName, limitlinewidth);

    return false;
};

function initDiagnosisDataCurveChartFn(catagories, series, tickInterval, divId, title, subtitle, xtitle, ytitle, color, upline, downline, uplineName, downlineName, limitlinewidth) {
    var max = null;
    var min = null;
    if (upline != 0) {
        max = upline + 10;
    }
    if (downline != 0) {
        min = downline - 10;
    }
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
            title: {
                text: ytitle,
                style: {
                    color: '#000000',
                    fontWeight: 'bold'
                }
            },
            max: max,
            min: min,
            labels: {
                formatter: function () {
                    return Highcharts.numberFormat(this.value, 2);
                }
            },
            plotLines: [{ //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                color: 'red',
                dashStyle: 'shortdash', //Dash,Dot,Solid,shortdash,默认Solid
                label: {
                    text: uplineName,
                    align: 'right',
                    x: -10
                },
                width: limitlinewidth,
                zIndex:10,
                value: upline //y轴显示位置
                   }, {
                color: 'green',
                dashStyle: 'shortdash',
                label: {
                    text: downlineName,
                    align: 'right',
                    x: -10
                },
                width: limitlinewidth,
                zIndex:10,
                value: downline //y轴显示位置
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
            spline: {
                lineWidth: 1,
                fillOpacity: 0.3,
                marker: {
                    enabled: true,
                    radius: 3, //曲线点半径，默认是4
                    //                            symbol: 'triangle' ,//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
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
            enabled: false,
            borderWidth: 0
        },
        series: series
    });
};