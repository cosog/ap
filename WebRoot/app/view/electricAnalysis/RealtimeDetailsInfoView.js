Ext.define('AP.view.electricAnalysis.RealtimeDetailsInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.electricAnalysisRealtimeDetailsInfoView',
    layout: "fit",
    border: false,
    initComponent: function () {
        var wellComboBoxStore = new Ext.data.JsonStore({
            pageSize: defaultJhComboxSize,
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
                    var wellName = Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName: wellName,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellComboBox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: 'electricAnalysisRealtimeDetailsWellCom_Id',
                store: wellComboBoxStore,
                labelWidth: 35,
                width: 135,
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
                    expand: function (sm, selections) {},
                    specialkey: function (field, e) {},
                    select: function (combo, record, index) {
                    	if(combo.value==""){
                            Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").hide();
                            Ext.getCmp("electricAnalysisRealtimeDetailsHisBtn_Id").show();
                            Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").hide();
                    		Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").hide();
                    	}else{
                    		Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").show();
                        	Ext.getCmp("electricAnalysisRealtimeDetailsHisBtn_Id").hide();
                            Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").show();
                    	}
                    	
                    	var tabPanel = Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id");
                        var activeId = tabPanel.getActiveTab().id;
                    	if(activeId=="electricAnalysisRealtimeDetailsDiscretePanel_Id"){
                    		Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetails_Id").getStore().loadPage(1);
                    	}else if(activeId=="electricAnalysisRealtimeDetailsDiagramPanel_Id"){
                    		var gridPanel=Ext.getCmp('ElectricAnalysisRealtimeDiagramDetails_Id').getStore().loadPage(1);
                    	}
                    }
                }
            });
        
        Ext.apply(this, {
            items: {
                xtype: 'tabpanel',
                id:'electricAnalysisRealtimeDetailsTabpanel_Id',
                activeTab: 0,
                border: true,
                header: false,
                collapsible: true, // 是否折叠
                split: true, // 竖折叠条
                tabPosition: 'bottom',
                tbar: [ // 定义topbar
                    wellComboBox,"-",
                    {
                        xtype: 'datefield',
                        anchor: '100%',
                        hidden: true,
                        fieldLabel: '',
                        labelWidth: 0,
                        width: 90,
                        format: 'Y-m-d ',
                        id: 'electricAnalysisRealtimeDetailsStartDate_Id',
                        value: '',
                        listeners: {
                        	select: function (combo, record, index) {
                        		var tabPanel = Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id");
                        		var activeId = tabPanel.getActiveTab().id;
                            	if(activeId=="electricAnalysisRealtimeDetailsDiscretePanel_Id"){
                            		Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetails_Id").getStore().loadPage(1);
                            	}else if(activeId=="electricAnalysisRealtimeDetailsDiagramPanel_Id"){
                            		var gridPanel=Ext.getCmp('ElectricAnalysisRealtimeDiagramDetails_Id').getStore().loadPage(1);
                            	}
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
                        id: 'electricAnalysisRealtimeDetailsEndDate_Id',
                        value: new Date(),
                        listeners: {
                        	select: function (combo, record, index) {
                        		var tabPanel = Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id");
                        		var activeId = tabPanel.getActiveTab().id;
                            	if(activeId=="electricAnalysisRealtimeDetailsDiscretePanel_Id"){
                            		Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetails_Id").getStore().loadPage(1);
                            	}else if(activeId=="electricAnalysisRealtimeDetailsDiagramPanel_Id"){
                            		var gridPanel=Ext.getCmp('ElectricAnalysisRealtimeDiagramDetails_Id').getStore().loadPage(1);
                            	}
                        	}
                        }
                    }, '->', {
                        xtype: 'button',
                        text:'查看历史',
                        tooltip:'点击按钮或者双击表格，查看单井历史数据',
                        id:'electricAnalysisRealtimeDetailsHisBtn_Id',
                        pressed: true,
                        hidden: false,
                        handler: function (v, o) {
                        	if(Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetails_Id").getSelectionModel().getSelection().length>0){
                    			Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").show();
                            	Ext.getCmp("electricAnalysisRealtimeDetailsHisBtn_Id").hide();
                                Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").show();
                        		Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").show();
                        		
                        		var tabPanel = Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id");
                        		var activeId = tabPanel.getActiveTab().id;
                        		var gridPanelId="ElectricAnalysisRealtimeDiscreteDetails_Id";
                            	if(activeId=="electricAnalysisRealtimeDetailsDiscretePanel_Id"){
                            		gridPanelId="ElectricAnalysisRealtimeDiscreteDetails_Id";
                            	}else if(activeId=="electricAnalysisRealtimeDetailsDiagramPanel_Id"){
                            		gridPanelId='ElectricAnalysisRealtimeDiagramDetails_Id';
                            	}
                        		
                    			var record  = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0];
                        		Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').setValue(record.data.wellName);
                            	Ext.getCmp('electricAnalysisRealtimeDetailsWellCom_Id').setRawValue(record.data.wellName);
                            	Ext.getCmp(gridPanelId).getStore().loadPage(1);
                    		}
                        }
                    }, {
                      xtype: 'button',
                      text:'返回实时',
                      id:'electricAnalysisRealtimeDetailsRTBtn_Id',
                      pressed: true,
                      hidden: true,
                      handler: function (v, o) {
                    	  Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue('');
                          Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue('');
                          Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").hide();
                          Ext.getCmp("electricAnalysisRealtimeDetailsHisBtn_Id").show();
                          Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").hide();
                  		  Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").hide();
                  		  var tabPanel = Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id");
                  		  var activeId = tabPanel.getActiveTab().id;
                  		  if(activeId=="electricAnalysisRealtimeDetailsDiscretePanel_Id"){
                  			  Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetails_Id").getStore().loadPage(1);
                  		  }else if(activeId=="electricAnalysisRealtimeDetailsDiagramPanel_Id"){
                  			  var gridPanel=Ext.getCmp('ElectricAnalysisRealtimeDiagramDetails_Id').getStore().loadPage(1);
                  		  }
                      }
                }, {
                    xtype: 'button',
                    text:'查看全部',
                    id:'electricAnalysisRealtimeDetailsAllWellBtn_Id',
                    pressed: true,
                    hidden: true,
                    handler: function (v, o) {}
              },{
                  id: 'ElectricAnalysisRealtimeDetailsCurveItem_Id', //选择查看曲线的数据项名称
                  xtype: 'textfield',
                  value: '',
                  hidden: true
              }, {
                  id: 'ElectricAnalysisRealtimeDetailsCurveItemCode_Id', //选择查看曲线的数据项代码
                  xtype: 'textfield',
                  value: '',
                  hidden: true
              }],
                items: [{
                	title:'离散数据',
                	id: 'electricAnalysisRealtimeDetailsDiscretePanel_Id',
                    border: false,
                    layout: 'border',
                    items:[{
                    	region: 'center',
                        id:'electricAnalysisRealtimeDiscreteDetailsTable_Id',
                        title:'数据列表',
                        layout: 'fit'
                    },{
                    	region: 'east',
                    	title:'数据详情',
                        width: '22%',
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
                            hidden:true
                        },{
                        	xtype: 'tabpanel',
                            id: 'ElectricAnalysisRealtimeDiscreteDetailsRightTabPanel_Id',
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
                                    		id: 'ElectricAnalysisRealtimeDiscreteDetailsRightAnalysisPanel_Id',
                                            border: false,
                                            layout: 'fit',
                                            height:'60%'
//                                            collapseDirection:'top'
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
                                            	id:'ElectricAnalysisRealtimeDiscreteDetailsRightRunRangeTextArea_Id',
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
                                            	id:'ElectricAnalysisRealtimeDiscreteDetailsRightResultCodeTextArea_Id',
                                            	grow:true,
                                            	width:'99.9%',
                                            	height: '45%',
                                                readOnly:true
                                            }]
                                    	}
                                    ]
                            }, {
                                    title: '采集',
                                    id: 'ElectricAnalysisRealtimeDiscreteDetailsRightAcqPanel_Id',
                                    border: false,
                                    layout: 'fit',
                                    autoScroll: true,
                                    scrollable: true
                            }]
                        }],
                        listeners: {
                            beforeCollapse: function (panel, eOpts) {
                                
                            },
                            expand: function (panel, eOpts) {
                                
                            }
                        }
                    }]
                    
                },{
                	title:'曲线数据',
                	id: 'electricAnalysisRealtimeDetailsDiagramPanel_Id',
                    border: false,
                    layout: 'border',
                    items:[{
                    	region: 'center',
                        id:'electricAnalysisRealtimeDiagramDetailsTable_Id',
                        title:'数据列表',
                        layout: 'fit'
                    },{
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
                                        html: '<div id=\"electricAnalysisRealtimeDetailsInverDiv1_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#electricAnalysisRealtimeDetailsInverDiv1_id").highcharts()!=undefined){
                                        			$("#electricAnalysisRealtimeDetailsInverDiv1_id").highcharts().setSize($("#electricAnalysisRealtimeDetailsInverDiv1_id").offsetWidth, $("#electricAnalysisRealtimeDetailsInverDiv1_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                            	    	margin: '0 1 0 0',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"electricAnalysisRealtimeDetailsInverDiv2_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#electricAnalysisRealtimeDetailsInverDiv2_id").highcharts()!=undefined){
                                        			$("#electricAnalysisRealtimeDetailsInverDiv2_id").highcharts().setSize($("#electricAnalysisRealtimeDetailsInverDiv2_id").offsetWidth, $("#electricAnalysisRealtimeDetailsInverDiv2_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    }]
                            	},{
                            		border: false,
//                            		flex: 1,
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
                                        html: '<div id=\"electricAnalysisRealtimeDetailsInverDiv3_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#electricAnalysisRealtimeDetailsInverDiv3_id").highcharts()!=undefined){
                                        			$("#electricAnalysisRealtimeDetailsInverDiv3_id").highcharts().setSize($("#electricAnalysisRealtimeDetailsInverDiv3_id").offsetWidth, $("#electricAnalysisRealtimeDetailsInverDiv3_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                            	    	margin: '0 1 0 0',
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"electricAnalysisRealtimeDetailsInverDiv4_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#electricAnalysisRealtimeDetailsInverDiv4_id").highcharts()!=undefined){
                                        			$("#electricAnalysisRealtimeDetailsInverDiv4_id").highcharts().setSize($("#electricAnalysisRealtimeDetailsInverDiv4_id").offsetWidth, $("#electricAnalysisRealtimeDetailsInverDiv4_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    }]
                            	},{
                            		border: false,
//                            		flex: 1,
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
                                        html: '<div id=\"electricAnalysisRealtimeDetailsInverDiv5_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#electricAnalysisRealtimeDetailsInverDiv5_id").highcharts()!=undefined){
                                        			$("#electricAnalysisRealtimeDetailsInverDiv5_id").highcharts().setSize($("#electricAnalysisRealtimeDetailsInverDiv5_id").offsetWidth, $("#electricAnalysisRealtimeDetailsInverDiv5_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    },{
                            	    	border: true,
                                        flex: 1,
                                        height:300,
                                        html: '<div id=\"electricAnalysisRealtimeDetailsInverDiv6_id\" style="width:100%;height:100%;"></div>',
                                        listeners: {
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	if($("#electricAnalysisRealtimeDetailsInverDiv6_id").highcharts()!=undefined){
                                        			$("#electricAnalysisRealtimeDetailsInverDiv6_id").highcharts().setSize($("#electricAnalysisRealtimeDetailsInverDiv6_id").offsetWidth, $("#electricAnalysisRealtimeDetailsInverDiv6_id").offsetHeight,true);
                                        		}
                                            }
                                        }
                            	    }]
                            	}
                            ]
                        },{
                			flex: 1,
                			xtype: 'tabpanel',
                            id: 'ElectricAnalysisRealtimeDiagramDetailsRightTabPanel_Id',
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
                                    id: 'ElectricAnalysisRealtimeDiagramDetailsRightAnalysisPanel_Id',
                                    border: false,
                                    layout: 'fit',
                                    autoScroll: true,
                                    scrollable: true
                            }, {
                                    title: '采集',
                                    id: 'ElectricAnalysisRealtimeDiagramDetailsRightAcqPanel_Id',
                                    border: false,
                                    layout: 'fit',
                                    autoScroll: true,
                                    scrollable: true
                            }]
                        }],
                        listeners: {
                            beforeCollapse: function (panel, eOpts) {
                                $("#electricAnalysisRealtimeDetailsInverDiv1_id").hide();
                                $("#electricAnalysisRealtimeDetailsInverDiv2_id").hide();
                                $("#electricAnalysisRealtimeDetailsInverDiv3_id").hide();
                                $("#electricAnalysisRealtimeDetailsInverDiv4_id").hide();
                                $("#electricAnalysisRealtimeDetailsInverDiv5_id").hide();
                                $("#electricAnalysisRealtimeDetailsInverDiv6_id").hide();
                            },
                            expand: function (panel, eOpts) {
                                $("#electricAnalysisRealtimeDetailsInverDiv1_id").show();
                                $("#electricAnalysisRealtimeDetailsInverDiv2_id").show();
                                $("#electricAnalysisRealtimeDetailsInverDiv3_id").show();
                                $("#electricAnalysisRealtimeDetailsInverDiv4_id").show();
                                $("#electricAnalysisRealtimeDetailsInverDiv5_id").show();
                                $("#electricAnalysisRealtimeDetailsInverDiv6_id").show();
                            }
                        }
                    }]
                }],
                listeners: {
                	tabchange: function (tabPanel, newCard, oldCard,obj) {
                		if(newCard.id=="electricAnalysisRealtimeDetailsDiscretePanel_Id"){
                    		Ext.getCmp("ElectricAnalysisRealtimeDiscreteDetails_Id").getStore().loadPage(1);
                    	}else if(newCard.id=="electricAnalysisRealtimeDetailsDiagramPanel_Id"){
                    		var ElectricAnalysisRealtimeDetailsDiagramTable=Ext.getCmp("ElectricAnalysisRealtimeDiagramDetails_Id");
                    		if(isNotVal(ElectricAnalysisRealtimeDetailsDiagramTable)){
                    			ElectricAnalysisRealtimeDetailsDiagramTable.getStore().loadPage(1);
                    		}else{
                    			Ext.create('AP.store.electricAnalysis.ElectricAnalysisRealtimeDetailsDiagramListStore');
                    		}
                    	}
                    }
                }
            },
            listeners: {
            	close: function (panel,eOpts) {
            		Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("");
					Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue('');
            	}
            }
        });
        this.callParent(arguments);
        
    }
});

function createElecAnalysisRealtimeDetailsTableColumn(columnInfo) {
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
        if (attr.dataIndex.toUpperCase()=='workingConditionName_Elec'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceElecWorkingConditionColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'phzt') {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'glphzt') {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return advicePowerBalanceStatusColor(value,o,p,e);}";
        } else if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:true";
        } else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += width_ + ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        } else if (attr.dataIndex.toUpperCase()=='acquisitionTime'.toUpperCase()) {
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

function createElecAnalysisRealtimeDetailsDiagramTableColumn(columnInfo) {
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
        if (attr.dataIndex == 'id'||attr.dataIndex == 'jlbh') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:false";
        }else if (attr.dataIndex.toUpperCase()=='acquisitionTime'.toUpperCase()) {
            myColumns += width_ + ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        }else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += width_ + ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
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

ElecAnalysisRealtimeDetailsCurveChartFn = function (get_rawData, itemName, itemCode, divId) {
    var tickInterval = 1;
    var data = get_rawData.totalRoot;
    var ytitle=itemName;
    tickInterval = Math.floor(data.length / 10) + 1;
    var upline = 0,
        downline = 0,
        zeroline = 0;
    var uplineName = '',
        downlineName = '',
        zerolineName='';
       
    var limitlinewidth = 0;
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
        upline = -1;
        downline = -1;
        zeroline = -1;
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
    if(itemCode.toUpperCase()=='downAndUpStrokeIMax'.toUpperCase() || itemCode.toUpperCase()=='downAndUpStrokeWattMax'.toUpperCase()){
    	legendName = ["下冲程最大值","上冲程最大值"];
    	if(itemCode.toUpperCase()=='downAndUpStrokeIMax'.toUpperCase()){
        	ytitle='电流(A)';
        }else if(itemCode.toUpperCase()=='downAndUpStrokeWattMax'.toUpperCase()){
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

    initElecAnalysisRealtimeDetailsCurveChartFn(cat, ser, tickInterval, divId, title, "[" + get_rawData.startDate + "~" + get_rawData.endDate + "]", "时间", ytitle, color, upline, downline,zeroline, uplineName, downlineName,zerolineName, limitlinewidth);

    return false;
};

function initElecAnalysisRealtimeDetailsCurveChartFn(catagories, series, tickInterval, divId, title, subtitle, xtitle, ytitle, color, upline, downline,zeroline, uplineName, downlineName,zerolineName, limitlinewidth) {
	var max = null;
    var min = null;
    if (upline >= 0) {
        max = upline + 1;
    }
    if (zeroline >= 0) {
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
//            xDateFormat: '%Y-%m-%d %H:%M:%S',
//            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
//            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
//                '<td style="padding:0"><b>{point.y:.2f}</b></td></tr>',
//            footerFormat: '</table>',
//            shared: true,
//            useHTML: true
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
            enabled: true,
            borderWidth: 0
        },
        series: series
    });
};

showAOriginalDiagram = function(diagramData, divid) {
	var currentCurveData=diagramData.currentCurveData.split(",");
	var data = "["; // 功图data
	if(currentCurveData.length>0){
		for (var i=0; i <= currentCurveData.length; i++) {
			data += "[" + (i+1) + ","+currentCurveData[i]+"]";
			if(i<currentCurveData.length-1){
				data += ",";
			}
		}
	}
	data+="]";
	var pointdata = Ext.JSON.decode(data);
	var title="电流原始曲线";
	var jh=diagramData.jh;         // 井名
	var cjsj=diagramData.cjsj;     // 采集时间
	var subtitle=jh+' ['+cjsj+']';
	var xtext='<span style="text-align:center;">点数<br />';
	var ytext="电流(A)";
	initAOriginalDiagramChart(pointdata, divid,title,subtitle,xtext,ytext,'#CC0000');
	return false;
};

function initAOriginalDiagramChart(pointdata, divid,title,subtitle,xtext,ytext,color) {
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: title          
		        },                                                                                   
		        subtitle: {
		        	text: subtitle                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                text: xtext,    // 坐标+显示文字
		                useHTML: false,
		                margin:5,
                        style: {
                        	fontSize: '12px',
                            padding: '5px'
                        }
		            }, 
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: '',   // 不显示次刻度线
		            min: 0                  // 最小值
		        },
		        exporting:{
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {
		        	itemStyle:{
		        		fontSize: '8px'
		        	},
		            enabled: false,
		            layout: 'vertical',
					align: 'right',
					verticalAlign: 'top',
					x: 0,
					y: 55,
					floating: true
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        },
		        series: [{                                                                           
		            name: '',                                                                  
		            color: color,   
		            lineWidth:3,
		            data:  pointdata                                                                                  
		        }]
	});
};

function showInverASDiagram(bgtdata, divid) {
	var positionCurveData=bgtdata.positionCurveData.split(",");
	var currentCurveData=bgtdata.currentCurveData.split(",");
	
	var xtext='<span style="text-align:center;">'+cosog.string.position+'<br />';
    
	if(bgtdata.upstrokepmax!=undefined && bgtdata.downstrokepmax!=undefined){
		xtext+='上冲程最大值:' + bgtdata.upstrokeamax + 'A 下冲程最大值:'  + bgtdata.downstrokeamax + 'A<br />';
	}
	if(bgtdata.adegreeofbalance!=undefined){
		xtext+='平衡度:' + bgtdata.adegreeofbalance + '%<br /></span>';
	}
	var data = "["; // 功图data
	var upStrokeData = "["; // 上冲程数据
	var downStrokeData = "["; // 下冲程数据
	var minIndex=0,maxIndex=0;
	var gtcount=positionCurveData.length; // 功图点数
	if(positionCurveData.length>0){
		for (var i=0; i <= positionCurveData.length; i++) {
			if(i<positionCurveData.length){
				data += "[" + positionCurveData[i] + ","+currentCurveData[i]+"],";
			}else{
				data += "[" + positionCurveData[0] + ","+currentCurveData[0]+"]";//将图形的第一个点拼到最后面，使图形闭合
			}
		}
		
		//获取最大位移和最小位移点数索引
		var minPos=100,maxPos=0;
		for (var i=0; i < gtcount; i++) {
			if(parseFloat(positionCurveData[i])<parseFloat(minPos)){
				minPos=positionCurveData[i];
				minIndex=i;
			}
			if(positionCurveData[i]>parseFloat(maxPos)){
				maxPos=parseFloat(positionCurveData[i]);
				maxIndex=i;
			}
		}
		
		if(minIndex<=maxIndex){//如果最小值索引小于最大值索引
			for(var i=minIndex;i<=maxIndex;i++){
				upStrokeData += "[" + positionCurveData[i] + ","+currentCurveData[i]+"]";
				if(i<maxIndex){
					upStrokeData+=",";
				}
			}
			var upStrokeCount=maxIndex-minIndex+1;//上冲程点数
			var downStrokeCount=gtcount-upStrokeCount;
			for(var i=0;i<downStrokeCount+2;i++){
				var index=i+maxIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				downStrokeData += "[" + positionCurveData[index] + ","+currentCurveData[index]+"]";
				if(i<downStrokeCount+1){
					downStrokeData+=",";
				}
			}
		}else{//如果最小值索引大于最大值索引
			for(var i=maxIndex;i<=minIndex;i++){
				downStrokeData += "[" + positionCurveData[i] + ","+currentCurveData[i]+"]";
				if(i<minIndex){
					downStrokeData+=",";
				}
			}
			var downStrokeCount=minIndex-maxIndex+1;//下冲程点数
			var upStrokeCount=gtcount-downStrokeCount;
			for(var i=0;i<upStrokeCount+2;i++){
				var index=i+minIndex;
				if(index>(gtcount-1)){
					index=index-gtcount;
				}
				upStrokeData +="[" + positionCurveData[index] + ","+currentCurveData[index]+"]";
				if(i<upStrokeCount+1){
					upStrokeData+=",";
				}
			}
		}
	}
	data+="]";
	upStrokeData+="]";
	downStrokeData+="]";
	var pointdata = Ext.JSON.decode(data);
	var upStrokePointdata = Ext.JSON.decode(upStrokeData);
	var downStrokePointdata = Ext.JSON.decode(downStrokeData);
	initInverASDiagramChart(upStrokePointdata,downStrokePointdata, bgtdata, divid,"电流图",xtext,"电流(A)",['#CC0000','#0033FF']);
	return false;
};

function initInverASDiagramChart(upStrokePointdata,downStrokePointdata, gtdata, divid,title,xtext,ytext,color) {
	var jh=gtdata.jh;         // 井名
	var cjsj=gtdata.cjsj;     // 采集时间
	mychart = new Highcharts.Chart({
				chart: {                                                                             
		            type: 'scatter',     // 散点图   
		            renderTo : divid,
		            zoomType: 'xy',
		            reflow: true
		        },                                                                                   
		        title: {
		        	text: title          
		        },                                                                                   
		        subtitle: {
		        	text: jh+' ['+cjsj+']'                                                      
		        },
		        credits: {
		            enabled: false
		        },
		        xAxis: {                                                                             
		            title: {                                                                         
		                text: xtext,    // 坐标+显示文字
		                useHTML: false,
		                margin:5,
                        style: {
                        	fontSize: '12px',
                            padding: '5px'
                        }
		            }, 
		            startOnTick: false,      //是否强制轴线在标线处开始
		            endOnTick: false,        //是否强制轴线在标线处结束                                                        
		            showLastLabel: true,
		            allowDecimals: false,    // 刻度值是否为小数
//		            min:0,
		            minorTickInterval: ''    // 最小刻度间隔
		        },                                                                                   
		        yAxis: {                                                                             
		            title: {                                                                         
		                text: ytext   // 载荷（kN） 
                    },
		            allowDecimals: false,    // 刻度值是否为小数
		            minorTickInterval: '',   // 不显示次刻度线
		            min: 0                  // 最小值
		        },
		        exporting:{
                    enabled:true,    
                    filename:'class-booking-chart',    
                    url:context + '/exportHighcharsPicController/export'
               },
		        legend: {
		        	itemStyle:{
		        		fontSize: '8px'
		        	},
		            enabled: true,
		            layout: 'vertical',
					align: 'right',
					verticalAlign: 'top',
					x: 0,
					y: 55,
					floating: true
		        },                                                                                   
		        plotOptions: {                                                                       
		            scatter: {                                                                       
		                marker: {                                                                    
		                    radius: 0,                                                               
		                    states: {                                                                
		                        hover: {                                                             
		                            enabled: true,                                                   
		                            lineColor: '#646464'                                    
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                states: {                                                                    
		                    hover: {                                                                 
		                        marker: {                                                            
		                            enabled: false                                                   
		                        }                                                                    
		                    }                                                                        
		                },                                                                           
		                tooltip: {                                                                   
		                    headerFormat: '',                                
		                    pointFormat: '{point.x},{point.y}'                                
		                }                                                                            
		            }                                                                                
		        },
		        series: [{                                                                           
		            name: '上冲程',                                                                  
		            color: color[0],   
		            lineWidth:3,
		            data:  upStrokePointdata                                                                                  
		        },{                                                                           
		            name: '下冲程',                                                                  
		            color: color[1],   
		            lineWidth:3,
		            data:  downStrokePointdata                                                                                  
		        }]
	});
};