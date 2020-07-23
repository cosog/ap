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
                	wellComboBox.getStore().loadPage(1);
                },
                select: function (combo, record, index) {
                	var statPanelId=getFSDiagramAnalysisSingleStatType().piePanelId;
                	if(combo.value==""){
                		Ext.getCmp("RPCRealtimeAnalysisWellListPanel_Id").setTitle("统计数据");
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsAllBtn_Id").hide();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsHisBtn_Id").show();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").hide();
                		Ext.getCmp("FSDiagramAnalysisSingleDetailsEndDate_Id").hide();
                		Ext.getCmp(statPanelId).expand(true);
                	}else{
                		Ext.getCmp("RPCRealtimeAnalysisWellListPanel_Id").setTitle("单井历史");
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
                    	id: 'RPCRealtimeAnalysisWellListPanel_Id',
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
                            text:'单井历史',
                            tooltip:'点击按钮或者双击表格，查看单井历史数据',
                            id:'FSDiagramAnalysisSingleDetailsHisBtn_Id',
                            pressed: true,
                            hidden: false,
                            handler: function (v, o) {
                        		if(Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection().length>0){
                        			Ext.getCmp("RPCRealtimeAnalysisWellListPanel_Id").setTitle("单井历史");
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
                        	  Ext.getCmp("RPCRealtimeAnalysisWellListPanel_Id").setTitle("统计数据");
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
                      }, {
                          id: 'FSDiagramMaxAcquisitionTime_Id',//功图最新采集时间
                          xtype: 'textfield',
                          value: '',
                          hidden: true
                      }, {
                          id: 'DiscreteMaxAcquisitionTime_Id',//功图最新采集时间
                          xtype: 'textfield',
                          value: '',
                          hidden: true
                      }, {
                          id: 'FSDiagramAnalysisSingleDetailsSelectRow_Id',//功图最新采集时间
                          xtype: 'textfield',
                          value: 0,
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
                          	title:'功图工况',
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
                              	title:'电参工况',
                              	id: 'FSDiagramAnalysisSingleElecWorkCondStatTabpanel_Id',
                              	tabRotation:1,
                              	items: [{
                                      title:'电参工况',
                                      border: false,
                                      iconCls: 'dtgreen',
                                      layout: 'border',
                                      id: 'FSDiagramAnalysisSingleElecWorkCondStatPanel_Id',
                                      items:[{
                                      	  region: 'center',
                                      	  id:'FSDiagramAnalysisSingleElecWorkCondDataListPanel_Id',
                                      	  header: false,
                                      	  layout: 'fit'
                                      },{
                                      	  region: 'south',
                                      	  id:'FSDiagramAnalysisSingleElecWorkCondStatGraphPanel_Id',
                                          height: '50%',
                                          border: true,
                                          header: false,
                                          collapsible: true, // 是否折叠
                                          split: true, // 竖折叠条
                                          html: '<div id="FSDiagramAnalysisSingleElecWorkCondStatGraphPieDiv_Id" style="width:100%;height:100%;"></div>',
                                          listeners: {
                                              resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                              	if ($("#FSDiagramAnalysisSingleElecWorkCondStatGraphPieDiv_Id").highcharts() != undefined) {
                                                      $("#FSDiagramAnalysisSingleElecWorkCondStatGraphPieDiv_Id").highcharts().setSize(adjWidth, adjHeight, true);
                                                  }else{
                                                  	Ext.create('Ext.tip.ToolTip', {
                                                          target: 'FSDiagramAnalysisSingleElecWorkCondStatGraphPieDiv_Id',
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
                      		Ext.getCmp("FSDiagramAnalysisSingleElecWorkCondStatTabpanel_Id").getTabBar().insert(0, {
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
                	title:'单井详情',
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
                        xtype: 'tabpanel',
                        activeTab: 0,
                        tabPosition: 'top',
                        id: 'FSDiagramAnalysisSingleDetailsCenterTabPanel_Id',
                        items: [{
                        	title: '井筒分析',
                        	margin: '0 0 0 0',
                            padding: 0,
                            autoScroll:true,
                            scrollable: true,
                            id: 'FSDiagramAnalysisSingleDetailsCenterPanel1_Id',
                            layout: {
                                type: 'vbox',
                                pack: 'start',
                                align: 'stretch'
                            },
                            items: [
                            	{
                            		border: false,
                            		margin: '0 0 0 0',
//                            		flex: 1,
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
                                        html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv1_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").highcharts().setSize($("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").offsetWidth, $("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                            	    	margin: '0 0 0 1',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv2_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").highcharts().setSize($("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").offsetWidth, $("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    }]
                            	},{
                            		border: false,
//                            		flex: 1,
                            		margin: '0 0 0 0',
                            		layout: {
                            	        type: 'hbox',
                            	        pack: 'start',
                            	        align: 'stretch'
                            	    },
                            	    items:[{
                            	    	border: true,
                            	    	margin: '1 0 0 0',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv3_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").highcharts().setSize($("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").offsetWidth, $("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                            	    	margin: '1 0 0 1',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv4_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").highcharts().setSize($("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").offsetWidth, $("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    }]
                            	},{
                            		border: false,
//                            		flex: 1,
                            		margin: '0 0 0 0',
                            		layout: {
                            	        type: 'hbox',
                            	        pack: 'start',
                            	        align: 'stretch'
                            	    },
                            	    items:[{
                            	    	border: true,
                            	    	margin: '1 0 0 0',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv5_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleWellboreDetailsDiv5_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleWellboreDetailsDiv5_id").highcharts().setSize($("#FSDiagramAnalysisSingleWellboreDetailsDiv5_id").offsetWidth, $("#FSDiagramAnalysisSingleWellboreDetailsDiv5_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                            	    	margin: '1 0 0 1',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleWellboreDetailsDiv6_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	var myChart = echarts.getInstanceByDom(document.getElementById("FSDiagramAnalysisSingleWellboreDetailsDiv6_id"));
                                            	if(myChart!=undefined){
                                            		myChart.resize({
                                            			width: $("#FSDiagramAnalysisSingleWellboreDetailsDiv6_id").offsetWidth,
                                            			height: $("#FSDiagramAnalysisSingleWellboreDetailsDiv6_id").offsetHeight,
                                            		});
                                            	}
                                            }
                                        }
                            	    }]
                            	}
                            ]
                        },{
                        	title: '地面分析',
                        	margin: '0 0 0 0',
                            padding: 0,
                            autoScroll:true,
                            scrollable: true,
                            id: 'FSDiagramAnalysisSingleDetailsCenterPanel2_Id',
                            layout: {
                                type: 'vbox',
                                pack: 'start',
                                align: 'stretch'
                            },
                            items: [
                            	{
                            		border: false,
                            		margin: '0 0 0 0',
//                            		flex: 1,
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
                                        html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv1_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv1_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleSurfaceDetailsDiv1_id").highcharts().setSize($("#FSDiagramAnalysisSingleSurfaceDetailsDiv1_id").offsetWidth, $("#FSDiagramAnalysisSingleSurfaceDetailsDiv1_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                            	    	margin: '0 0 0 1',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv2_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv2_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleSurfaceDetailsDiv2_id").highcharts().setSize($("#FSDiagramAnalysisSingleSurfaceDetailsDiv2_id").offsetWidth, $("#FSDiagramAnalysisSingleSurfaceDetailsDiv2_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    }]
                            	},{
                            		border: false,
//                            		flex: 1,
                            		margin: '0 0 0 0',
                            		layout: {
                            	        type: 'hbox',
                            	        pack: 'start',
                            	        align: 'stretch'
                            	    },
                            	    items:[{
                            	    	border: true,
                            	    	margin: '1 0 0 0',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv3_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv3_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleSurfaceDetailsDiv3_id").highcharts().setSize($("#FSDiagramAnalysisSingleSurfaceDetailsDiv3_id").offsetWidth, $("#FSDiagramAnalysisSingleSurfaceDetailsDiv3_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                            	    	margin: '1 0 0 1',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv4_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv4_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleSurfaceDetailsDiv4_id").highcharts().setSize($("#FSDiagramAnalysisSingleSurfaceDetailsDiv4_id").offsetWidth, $("#FSDiagramAnalysisSingleSurfaceDetailsDiv4_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    }]
                            	},{
                            		border: false,
//                            		flex: 1,
                            		margin: '0 0 0 0',
                            		layout: {
                            	        type: 'hbox',
                            	        pack: 'start',
                            	        align: 'stretch'
                            	    },
                            	    items:[{
                            	    	border: true,
                            	    	margin: '1 0 0 0',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv5_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv5_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleSurfaceDetailsDiv5_id").highcharts().setSize($("#FSDiagramAnalysisSingleSurfaceDetailsDiv5_id").offsetWidth, $("#FSDiagramAnalysisSingleSurfaceDetailsDiv5_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                            	    	margin: '1 0 0 1',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"FSDiagramAnalysisSingleSurfaceDetailsDiv6_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#FSDiagramAnalysisSingleSurfaceDetailsDiv6_id").highcharts()!=undefined){
                                        			$("#FSDiagramAnalysisSingleSurfaceDetailsDiv6_id").highcharts().setSize($("#FSDiagramAnalysisSingleSurfaceDetailsDiv6_id").offsetWidth, $("#FSDiagramAnalysisSingleSurfaceDetailsDiv6_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    }]
                            	}
                            ]
                        }],
                        listeners: {
                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                            	Ext.create("AP.store.diagnosis.SinglePumpCardStore");
                            }
                        }
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
                        	$("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").hide();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").hide();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").hide();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").hide();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv5_id").hide();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv6_id").hide();
                        },
                        expand: function (panel, eOpts) {
                        	$("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").show();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").show();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").show();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").show();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv5_id").show();
                            $("#FSDiagramAnalysisSingleWellboreDetailsDiv6_id").show();
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
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_;
        if (attr.dataIndex.toUpperCase() == 'workingConditionName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='workingConditionName_Elec'.toUpperCase()||attr.dataIndex.toUpperCase()=='workingConditionName_E'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceElecWorkingConditionColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='iDegreeBalanceName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='wattDegreeBalanceName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return advicePowerBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'id') {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:true";
        } else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        } else if (attr.dataIndex.toUpperCase() == 'acquisitionTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
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
	}else if(activeTabId=="FSDiagramAnalysisSingleElecWorkCondStatPanel_Id"){//功图工况
		type=13;
		panelId="FSDiagramAnalysisSingleElecWorkCondDataListPanel_Id";
		piePanelId="FSDiagramAnalysisSingleElecWorkCondStatGraphPanel_Id";
		pieDivId="FSDiagramAnalysisSingleElecWorkCondStatGraphPieDiv_Id";
		pieChartTitle="电参工况";
		exportExcelTitle='抽油机实时评价-电参工况';
	}
	var result=Ext.JSON.decode("{\"type\":"+type+",\"panelId\":\""+panelId+"\",\"piePanelId\":\""+piePanelId+"\",\"pieDivId\":\""+pieDivId+"\",\"pieChartTitle\":\""+pieChartTitle+"\",\"exportExcelTitle\":\""+exportExcelTitle+"\"}");
	return result;
}

function loadFSDiagramAnalysisSingleStatData() {
	var selectWellName=Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
	var piePanelId=getFSDiagramAnalysisSingleStatType().piePanelId;
	if(selectWellName==null||selectWellName==""){
		Ext.getCmp("RPCRealtimeAnalysisWellListPanel_Id").setTitle("统计数据");
		Ext.getCmp("FSDiagramAnalysisSingleDetailsAllBtn_Id").hide();
		Ext.getCmp("FSDiagramAnalysisSingleDetailsHisBtn_Id").show();
		Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").hide();
		Ext.getCmp("FSDiagramAnalysisSingleDetailsEndDate_Id").hide();
    	Ext.getCmp(piePanelId).expand(true);
    }else{
    	Ext.getCmp("RPCRealtimeAnalysisWellListPanel_Id").setTitle("单井历史");
    	Ext.getCmp("FSDiagramAnalysisSingleDetailsAllBtn_Id").show();
		Ext.getCmp("FSDiagramAnalysisSingleDetailsHisBtn_Id").hide();
		Ext.getCmp("FSDiagramAnalysisSingleDetailsStartDate_Id").show();
		Ext.getCmp("FSDiagramAnalysisSingleDetailsEndDate_Id").show();
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
    	downline = 0,
    	zeroline = 0;
    var uplineName = '',
    	downlineName = '',
    	zerolineName='';
    var limitlinewidth = 0;
    var legend=false;
    if (data.length>0 && (itemCode.toUpperCase()=='Ia'.toUpperCase() || itemCode.toUpperCase()=='Ib'.toUpperCase() || itemCode.toUpperCase()=='Ic'.toUpperCase() || itemCode.toUpperCase()=='Va'.toUpperCase() || itemCode.toUpperCase()=='Vb'.toUpperCase() || itemCode.toUpperCase()=='Vc'.toUpperCase())) {
        upline = parseFloat(get_rawData.uplimit);
        downline = parseFloat(get_rawData.downlimit);
        zeroline= parseFloat(get_rawData.zero);
        if(isNaN(upline)){
        	upline=0;
        }
        if(isNaN(downline)){
        	downline=0;
        }
        if(isNaN(zeroline)){
        	zeroline=0;
        }
        if (itemCode.toUpperCase()=='Ia'.toUpperCase() || itemCode.toUpperCase()=='Ib'.toUpperCase() || itemCode.toUpperCase()=='Ic'.toUpperCase()){
        	uplineName = '过载限值:' + upline;
            downlineName = '空载限值:' + downline;
            zerolineName = '零点限值:' + zeroline;
        }else if(itemCode.toUpperCase()=='Va'.toUpperCase() || itemCode.toUpperCase()=='Vb'.toUpperCase() || itemCode.toUpperCase()=='Vc'.toUpperCase()){
        	uplineName = '过电压限值:' + upline;
            downlineName = '欠电压限值:' + downline;
            zerolineName = '零点限值:' + zeroline;
        }
        limitlinewidth = 3;
    } else {
        upline = 0;
        downline = 0;
        zeroline = 0;
        uplineName = '';
        downlineName = '';
        zerolineName='';
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
    if(itemCode.toUpperCase()=='iRatio'.toUpperCase() || itemCode.toUpperCase()=='wattRatio'.toUpperCase()){
    	legendName = ["下冲程最大值","上冲程最大值"];
    	legend=true;
    	if(itemCode.toUpperCase()=='iRatio'.toUpperCase()){
        	ytitle='电流(A)';
        }else if(itemCode.toUpperCase()=='wattRatio'.toUpperCase()){
        	legendName = ["下冲程最大值","上冲程最大值"];
        	ytitle='功率(kW)';
        }
    }
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
//            series += "[" + Date.parse(data[j].acquisitionTime.replace(/-/g, '/')) + "," + data[j].value + "]";
            if (i == 0) {
            	series += "[" + Date.parse(data[j].acquisitionTime.replace(/-/g, '/')) + "," + data[j].value + "]";
            }else if(i == 1){
            	series += "[" + Date.parse(data[j].acquisitionTime.replace(/-/g, '/')) + "," + data[j].value2 + "]";
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

    initDiagnosisDataCurveChartFn(cat, ser, tickInterval, divId, title, "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "时间", itemName, color, upline, downline,zeroline, uplineName, downlineName,zerolineName, limitlinewidth,legend);

    return false;
};

function initDiagnosisDataCurveChartFn(catagories, series, tickInterval, divId, title, subtitle, xtitle, ytitle, color, upline, downline,zeroline, uplineName, downlineName,zerolineName, limitlinewidth,legend) {
    var max = null;
    var min = null;
    if (upline != 0) {
        max = upline + 10;
    }
    if (downline != 0) {
        min = downline - 10;
    }
    if (zeroline != 0) {
        min = zeroline - 1;
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
          }, {
        	  color: 'blue',
              dashStyle: 'shortdash',
              label: {
                  text: zerolineName,
                  align: 'right',
                  x: -10
              },
              width: limitlinewidth,
              zIndex:10,
              value: zeroline //y轴显示位置
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
            enabled: legend,
            borderWidth: 0
        },
        series: series
    });
};

function initWellboreSliceCharts(result,divId){
	var measuringDepth=result.wellboreSlice_MeasuringDepth.split(",");
	var P=result.wellboreSlice_P.split(",");
	var Bo=result.wellboreSlice_Bo.split(",");
	var GLRis=result.wellboreSlice_GLRis.split(",");
	var tickInterval = Math.floor(measuringDepth.length / 10) + 1;
	var title='物性剖面';
	var legendName = ['压力','就地气液比'];
	var series = "[";
	var PData="{\"name\":\"压力\",\"yAxis\":0,\"data\":[";
	var GLRisData="{\"name\":\"就地气液比\",\"yAxis\":1,\"data\":[";
	var color=['#800000',
		'#0000FF'
	];
	var maxMeasuringDepth=0;
	var xMaxValue=null;
    if(measuringDepth.length>0){
        for(var i=0;i<measuringDepth.length;i++){
        	PData += "[" + (0-changeTwoDecimal(measuringDepth[i])) + ","+changeTwoDecimal(P[i])+"]";
        	GLRisData += "[" + (0-changeTwoDecimal(measuringDepth[i])) + ","+changeTwoDecimal(GLRis[i])+"]";
        	maxMeasuringDepth=parseFloat(measuringDepth[i]);
        	if(i<measuringDepth.length-1){
        		PData+=",";
        		GLRisData+=",";
        	}
        }
    }
    PData+="]}";
    GLRisData+="]}";
	series+=PData+","+GLRisData;
	series+="]";
	
	if(maxMeasuringDepth!=0){
		xMaxValue=Math.ceil(maxMeasuringDepth/100)*100;
		tickInterval = 100;
	}
	
	var ser1 = Ext.JSON.decode(series);
	
	var chart = new Highcharts.Chart({
		chart: {
			renderTo : divId,
			type: 'spline',
			zoomType: 'xy',
			shadow : false,
			borderWidth : 0,
			inverted: true
		},
		credits : {
			enabled : false
		},
		title: {
			text: title
		},
		subtitle: {
        	text: result.wellName+' ['+result.acquisitionTime+']'                                                      
        },
		colors: color,
		xAxis: {
			reversed: false,
			tickInterval : tickInterval,
			title: {
				enabled: true,
				text: '深度(m)'
			},
			labels: {
				formatter: function () {
					return this.value;
				}
			},
			maxPadding: 0.05,
			max: 0,
			min:0-xMaxValue,
			showLastLabel: true
		},
		yAxis: [{
			title: {
				text: '压力(Mpa)'
			},
			labels: {
				formatter: function () {
					return this.value;
				}
			},
			lineWidth: 2
		},{
			title: {
				text: '就地气液比'
			},
			opposite:true,
			labels: {
				formatter: function () {
					return this.value;
				}
			},
			lineWidth: 2
		}],
		legend: {
			enabled: false
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br/>',
			pointFormat: '深度:{point.x} m, {series.name}:{point.y}'
		},
		plotOptions : {
			 spline: {
				 lineWidth: 3,  
		         fillOpacity: 0.3,  
		         marker: {
		        	 enabled: true,
		        	 radius: 0,  //曲线点半径，默认是4
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
		series: ser1
	});
}

function initSingleDetailsWellboreTrajectoryCharts(result,divId){
	var data = [];
	var zMaxValue=0;
	var zMin=null,zMax=null,xMin=null,xMax=null,yMin=null,yMax=null;
	
	var wellboreTrajectoryX=result.wellboreTrajectoryX.split(",");
	var wellboreTrajectoryY=result.wellboreTrajectoryY.split(",");
	var wellboreTrajectoryZ=result.wellboreTrajectoryZ.split(",");
	
	var reservoirDepth=result.reservoirDepth;
	
	if(result.wellboreTrajectoryX!=""&&result.wellboreTrajectoryX!="null"&&result.wellboreTrajectoryX!=null){
		for(var i=0;i<wellboreTrajectoryX.length;i++){
			data.push([parseFloat(wellboreTrajectoryX[i]),parseFloat(wellboreTrajectoryY[i]),0-parseFloat(wellboreTrajectoryZ[i])]);
			zMaxValue=parseFloat(wellboreTrajectoryZ[i]);
		}
	}else{
		data.push([0,0,0]);
		data.push([0,0,0-parseFloat(reservoirDepth)]);
		zMaxValue=parseFloat(reservoirDepth);
	}
	
	if(zMaxValue!=0 && zMaxValue!=undefined){
		zMax=Math.ceil(zMaxValue/100)*100;
		xMin=yMin=0-zMax/2;
		xMax=yMax=zMax/2;
		zMin=0-zMax;
	}
	var myChart = echarts.init(document.getElementById(divId),'macarons');
	var option = {
		    tooltip: {},
		    backgroundColor: '#fff',
		    title: {
		        left: 'center',
		        text: '井身轨迹',
		    },
		    visualMap: {
		        show: false,
		        dimension: 2,
		        min: 0,
		        max: 30,
		        inRange: {
		            color: ['#0000FF']
//		    		color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026']
		        }
		    },
		    toolbox: {
                show: true,
                feature: {
                    saveAsImage: {}   //保存为图片
                }
            },
		    xAxis3D: {
		        type: 'value',
		        min:xMin,
		        max:xMax
		    },
		    yAxis3D: {
		        type: 'value',
		        min:yMin,
		        max:yMax
		    },
		    zAxis3D: {
		        type: 'value',
		        min:zMin,
		        max:0,
		        inverse: true //反转坐标轴
		    },
		    grid3D: {
		        viewControl: {
		            projection: 'orthographic',
		            autoRotate: false
		        },
		        boxWidth: 70, //图件宽
		        boxHeight: 70, //图件高
		        boxDepth: 70 //图件长
		    },
		    series: [{
		        type: 'line3D',
		        data: data,
		        zoom: 0.5, //当前视角的缩放比例
		        roam: true, //是否开启平游或缩放
		        lineStyle: {
		            width: 4
		        }
		    }]
		};
	// 使用刚指定的配置项和数据显示图表。
	myChart.clear();
    myChart.setOption(option);

};

var FSDiagramAnalysisRealtimeRefreshTask = {
	    run: function() {
	    	var activeId = Ext.getCmp("frame_center_ids").getActiveTab().id;
			if (activeId == "FSDiagramAnalysis_FSDiagramAnalysisSingleDetails") {
				if (isNotVal(Ext.getCmp("FSDiagramAnalysisSingleDetails_Id"))) {
					var FSDiagramMaxAcquisitionTime=Ext.getCmp("FSDiagramMaxAcquisitionTime_Id").getValue();
					var DiscreteMaxAcquisitionTime=Ext.getCmp("DiscreteMaxAcquisitionTime_Id").getValue();
					var orgId = Ext.getCmp('leftOrg_Id').getValue();
					Ext.Ajax.request({
			    		method:'POST',
			    		url:context + '/diagnosisAnalysisOnlyController/getNewestAcquisitionTime',
			    		success:function(response) {
			    			var result = Ext.decode(response.responseText);
			    			if(result.diagramRecords>0 || result.discreteRecords>0){
			    				if(result.diagramRecords>0){
			    					Ext.getCmp("FSDiagramMaxAcquisitionTime_Id").setValue(result.newestFSDiagramAcquisitionTime);
			    				}
			    				if(result.discreteRecords>0){
			    					Ext.getCmp("DiscreteMaxAcquisitionTime_Id").setValue(result.newestDiscreteAcquisitionTime);
			    				}
			    				Ext.create("AP.store.diagnosis.WorkStatusStatisticsInfoStore");
			    			}
			    		},
			    		failure:function(){
			    			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			    		},
			    		params: {
			    			orgId: orgId,
			    			FSDiagramMaxAcquisitionTime: FSDiagramMaxAcquisitionTime,
			    			DiscreteMaxAcquisitionTime:DiscreteMaxAcquisitionTime
			            }
			    	});
		    	}
			}
	    },
	    interval: 5000
};

Ext.TaskManager.start(FSDiagramAnalysisRealtimeRefreshTask);