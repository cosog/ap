Ext.define("AP.view.electricAnalysis.RealtimeProfileInfoView", { // 定义光杆功图查询panel
    extend: 'Ext.panel.Panel', // 继承
    alias: 'widget.electricAnalysisRealtimeProfileInfoView', // 定义别名
    id: 'ElectricAnalysisRealtimeProfileInfoView_Id', //模块编号
    layout: 'fit', // 适应屏幕大小
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
                tbar:[
                	{
                		xtype: 'textfield',
                        id: "electricAnalysisRealtimeProfileRunTextfield_id",
                        anchor: '100%',
                        labelWidth: 30,
                        width: 80,
                        fieldLabel: '运行',
                        readOnly: true,
                        listeners: {
//                        	render: function(p) {
//                        	     p.getEl().on('click', function(p){
//                        	    	 var tabPanel = Ext.getCmp("frame_center_ids");
//                        	    	 var getTabId = tabPanel.getComponent("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                        	    	 Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("runStatus");
//                                	 Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue("运行");
//                                     if (getTabId) {// tab是否已打开panl
//                                         Ext.getCmp("topModule_Id").setValue("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                                         tabPanel.setActiveTab("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                                         Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id").setActiveTab("electricAnalysisRealtimeDetailsDiscretePanel_Id");
//                                     }else {
//                                    	 tabPanel.add(Ext.create("AP.view.electricAnalysis.RealtimeDetailsInfoView", {
//                                             id: "ElectricAnalysis_ElectricAnalysisRealtimeDetails",
//                                             closable: true,
//                                             iconCls: "WellInfo",
//                                             closeAction: 'destroy',
//                                             title: "详情",
//                                             listeners: {
//                                                 afterrender: function () {
//                                                     //all_loading.hide();
//                                                 },
//                                                 delay: 150
//                                             }
//                                         })).show();
//                                     }
//                                     Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//             	            		Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//             	            		Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").hide();
//             	                    Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").hide();
//             	              		Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").hide();
//             	              		Ext.getCmp("electricAnalysisRealtimeDetailsAllWellBtn_Id").show();
//             	              		Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteCurve_Id").collapse();
//                            		$("#electricAnalysisRealtimeDetailsDiscreteCurveDiv_Id").html('');
//                        	     }); 
//                        	  }
                        }
                	},'-',{
                		xtype: 'textfield',
                        id: "electricAnalysisRealtimeProfileStopTextfield_id",
                        anchor: '100%',
                        labelWidth: 30,
                        width: 80,
                        fieldLabel: '停抽',
                        readOnly: true,
                        listeners: {
//                        	render: function(p) {
//                        	     p.getEl().on('click', function(p){
//                        	    	 var tabPanel = Ext.getCmp("frame_center_ids");
//                        	    	 var getTabId = tabPanel.getComponent("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                        	    	 Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("runStatus");
//                                	 Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue("停抽");
//                                     if (getTabId) {// tab是否已打开panl
//                                         Ext.getCmp("topModule_Id").setValue("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                                         tabPanel.setActiveTab("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                                         Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id").setActiveTab("electricAnalysisRealtimeDetailsDiscretePanel_Id");
//                                     }else {
//                                    	 tabPanel.add(Ext.create("AP.view.electricAnalysis.RealtimeDetailsInfoView", {
//                                             id: "ElectricAnalysis_ElectricAnalysisRealtimeDetails",
//                                             closable: true,
//                                             iconCls: "WellInfo",
//                                             closeAction: 'destroy',
//                                             title: "详情",
//                                             listeners: {
//                                                 afterrender: function () {
//                                                     //all_loading.hide();
//                                                 },
//                                                 delay: 150
//                                             }
//                                         })).show();
//                                     }
//                                     Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//                                     Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//             	            		 Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").hide();
//             	                     Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").hide();
//             	              		 Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").hide();
//             	              		 Ext.getCmp("electricAnalysisRealtimeDetailsAllWellBtn_Id").show();
//             	              		 Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteCurve_Id").collapse();
//                            		 $("#electricAnalysisRealtimeDetailsDiscreteCurveDiv_Id").html('');
//                        	     }); 
//                        	  }
                        }
                	},'-',{
                		xtype: 'textfield',
                        id: "electricAnalysisRealtimeProfileWarnningTextfield_id",
                        anchor: '100%',
                        labelWidth: 30,
                        width: 80,
                        fieldLabel: '报警',
                        readOnly: true,
                        listeners: {
//                        	render: function(p) {
//                        	     p.getEl().on('click', function(p){
//                        	    	 var tabPanel = Ext.getCmp("frame_center_ids");
//                        	    	 var getTabId = tabPanel.getComponent("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                        	    	 Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("alarmStatus");
//                                	 Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue("1");
//                                     if (getTabId) {// tab是否已打开panl
//                                         Ext.getCmp("topModule_Id").setValue("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                                         tabPanel.setActiveTab("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                                         Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id").setActiveTab("electricAnalysisRealtimeDetailsDiscretePanel_Id");
//                                     }else {
//                                    	 tabPanel.add(Ext.create("AP.view.electricAnalysis.RealtimeDetailsInfoView", {
//                                             id: "ElectricAnalysis_ElectricAnalysisRealtimeDetails",
//                                             closable: true,
//                                             iconCls: "WellInfo",
//                                             closeAction: 'destroy',
//                                             title: "详情",
//                                             listeners: {
//                                                 afterrender: function () {
//                                                     //all_loading.hide();
//                                                 },
//                                                 delay: 150
//                                             }
//                                         })).show();
//                                     }
//                                     Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//             	            		Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//             	            		Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").hide();
//             	                    Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").hide();
//             	              		Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").hide();
//             	              		Ext.getCmp("electricAnalysisRealtimeDetailsAllWellBtn_Id").show();
//             	              		Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteCurve_Id").collapse();
//                            		$("#electricAnalysisRealtimeDetailsDiscreteCurveDiv_Id").html('');
//                        	     }); 
//                        	  }
                        }
                	},'-',{
                        xtype: 'button',
                        text:'刷新',
                        pressed: true,
                        iconCls: 'note-refresh',
                        handler: function (v, o) {
                        	getElectricAnalysisRealtimeProfileData();
                        }
                  }
                ],
                autoScroll:false,
                items: {
                	border: false,
                    layout: 'border',
                    items:[{
                    	region: 'center',
                    	xtype: 'tabpanel',
                    	tabPosition: 'left',
                    	tabRotation:1,
                    	id:'electricAanlysisRealtimeProfileTabPanel_Id',
                    	items: [{
                    		title:'运行状态',
                    		iconCls: 'select',
                    		id:'electricAanlysisRealtimeRunSatausProfilePanel_Id'
                    	},{
                    		title:'运行时率',
                    		layout: 'fit',
                    		id:'electricAanlysisRealtimeRuntimeEfficiencyProfilePanel_Id'
                    	},{
                    		title:'电参工况',
                    		layout: 'fit',
                    		id:'electricAanlysisRealtimeWorkingConditionProfilePanel_Id'
                    	},{
                    		title:'功率平衡',
                    		layout: 'fit',
                    		id:'electricAanlysisRealtimeWattDegreeBalanceProfilePanel_Id'
                    	},{
                    		title:'电流平衡',
                    		layout: 'fit',
                    		id:'electricAanlysisRealtimeIDegreeBalanceProfilePanel_Id'
                    	},{
                    		title:'日用电量',
                    		layout: 'fit',
                    		id:'electricAanlysisRealtimeTodayWattEnergyProfilePanel_Id'
                    	}],
                    	listeners: {
                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                        		newCard.setIconCls("select");
                        		oldCard.setIconCls("");
                        		var gridPanel=Ext.getCmp("electricAanlysisRealtimeProfileTablePanel_Id");
                        		if(isNotVal(gridPanel)){
                        			gridPanel.destroy();
                        		}
                        		Ext.create("AP.store.electricAnalysis.ElectricAnalysisRealtimeProfileListStore");
                            }
                        }
                    },{
                    	region: 'east',
                    	width:'75%',
                    	border: false,
                        header: false,
                        collapsible: true, // 是否折叠
                        split: true, // 竖折叠条
                    	layout: {
                            type: 'vbox',
                            pack: 'start',
                            align: 'stretch'
                        },
                        items: [
                        	{
                        		border: false,
                        		margin: '0 0 0 0',
                        		flex: 1,
                        		layout: {
                        	        type: 'hbox',
                        	        pack: 'start',
                        	        align: 'stretch'
                        	    },
                        	    items:[{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeProfileDiv1_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeProfileDiv1_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeProfileDiv1_id").highcharts().setSize($("#electricAnalysisRealtimeProfileDiv1_id").offsetWidth, $("#electricAnalysisRealtimeProfileDiv1_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeProfileDiv2_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeProfileDiv2_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeProfileDiv2_id").highcharts().setSize($("#electricAnalysisRealtimeProfileDiv2_id").offsetWidth, $("#electricAnalysisRealtimeProfileDiv2_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeProfileDiv3_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeProfileDiv3_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeProfileDiv3_id").highcharts().setSize($("#electricAnalysisRealtimeProfileDiv3_id").offsetWidth, $("#electricAnalysisRealtimeProfileDiv3_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	},{
                        		border: false,
                        		flex: 1,
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
                                    html: '<div id=\"electricAnalysisRealtimeProfileDiv4_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeProfileDiv4_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeProfileDiv4_id").highcharts().setSize($("#electricAnalysisRealtimeProfileDiv4_id").offsetWidth, $("#electricAnalysisRealtimeProfileDiv4_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeProfileDiv5_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeProfileDiv5_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeProfileDiv5_id").highcharts().setSize($("#electricAnalysisRealtimeProfileDiv5_id").offsetWidth, $("#electricAnalysisRealtimeProfileDiv5_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisRealtimeProfileDiv6_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisRealtimeProfileDiv6_id").highcharts()!=undefined){
                                    			$("#electricAnalysisRealtimeProfileDiv6_id").highcharts().setSize($("#electricAnalysisRealtimeProfileDiv6_id").offsetWidth, $("#electricAnalysisRealtimeProfileDiv6_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	}
                        ],
                        listeners: {
                            beforeCollapse: function (panel, eOpts) {
                                $("#electricAnalysisRealtimeProfileDiv1_id").hide();
                                $("#electricAnalysisRealtimeProfileDiv2_id").hide();
                                $("#electricAnalysisRealtimeProfileDiv3_id").hide();
                                $("#electricAnalysisRealtimeProfileDiv4_id").hide();
                                $("#electricAnalysisRealtimeProfileDiv5_id").hide();
                                $("#electricAnalysisRealtimeProfileDiv6_id").hide();
                            },
                            expand: function (panel, eOpts) {
                                $("#electricAnalysisRealtimeProfileDiv1_id").show();
                                $("#electricAnalysisRealtimeProfileDiv2_id").show();
                                $("#electricAnalysisRealtimeProfileDiv3_id").show();
                                $("#electricAnalysisRealtimeProfileDiv4_id").show();
                                $("#electricAnalysisRealtimeProfileDiv5_id").show();
                                $("#electricAnalysisRealtimeProfileDiv6_id").show();
                            }
                        }
                    }]
                }
                
        });
        me.callParent(arguments);
    }
});

function getElectricAnalysisRealtimeProfileStatusData(){
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getElectricAnalysisRealtimeProfileData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("electricAnalysisRealtimeProfileRunTextfield_id").setValue(result.runWellCount);
			Ext.getCmp("electricAnalysisRealtimeProfileStopTextfield_id").setValue(result.stopWellCount);
			Ext.getCmp("electricAnalysisRealtimeProfileWarnningTextfield_id").setValue(result.warnningWellCount);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:leftOrg_Id
        }
	});
}

function showElectricAnalysisRealtimePrifilePieChart(title,divid, name, data) {
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
//					click: function(e) {
////						alert(e.currentTarget.innerText);
//						var statType="";
//						if(e.currentTarget.innerText.indexOf("运行状态")>-1){
//							statType="运行状态";
//						}else if(e.currentTarget.innerText.indexOf("运行时率")>-1){
//							statType="运行时率";
//						}else if(e.currentTarget.innerText.indexOf("电参工况")>-1){
//							statType="电参工况";
//						}else if(e.currentTarget.innerText.indexOf("功率平衡")>-1){
//							statType="功率平衡";
//						}else if(e.currentTarget.innerText.indexOf("电流平衡")>-1){
//							statType="电流平衡";
//						}else if(e.currentTarget.innerText.indexOf("日用电量")>-1){
//							statType="日用电量";
//						}
//						
//						
//						var tabPanel = Ext.getCmp("frame_center_ids");
//           	    	 	var getTabId = tabPanel.getComponent("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//           	    	 	
//                        if (getTabId) {
//                        	
//                        }else {
//                       	 	tabPanel.add(Ext.create("AP.view.electricAnalysis.RealtimeDetailsInfoView", {
//                                id: "ElectricAnalysis_ElectricAnalysisRealtimeDetails",
//                                closable: true,
//                                iconCls: "WellInfo",
//                                closeAction: 'destroy',
//                                title: "详情",
//                                listeners: {
//                                    afterrender: function () {
//                                        //all_loading.hide();
//                                    },
//                                    delay: 150
//                                }
//                            })).show();
//                        }
//						
//                        Ext.getCmp("topModule_Id").setValue("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                        tabPanel.setActiveTab("ElectricAnalysis_ElectricAnalysisRealtimeDetails");
//                        Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id").setActiveTab("electricAnalysisRealtimeDetailsDiscretePanel_Id");
//                        Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("");
//           	    	 	Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue("");
//                        Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//	            		Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//	            		Ext.getCmp("electricAnalysisRealtimeDetailsRTBtn_Id").hide();
//	                    Ext.getCmp("electricAnalysisRealtimeDetailsStartDate_Id").hide();
//	              		Ext.getCmp("electricAnalysisRealtimeDetailsEndDate_Id").hide();
//	              		Ext.getCmp("electricAnalysisRealtimeDetailsAllWellBtn_Id").show();
//	              		Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteCurve_Id").collapse();
//                		$("#electricAnalysisRealtimeDetailsDiscreteCurveDiv_Id").html('');
//	              		if(statType=="运行状态"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("runStatus");
//								Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue(e.point.name);
//							}else{
//								Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("");
//								Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue('');
//							}
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="运行时率"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setRawValue(e.point.name);
//							}else{
//								Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="电参工况"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setRawValue(e.point.name);
//							}else{
//								Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="功率平衡"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setRawValue(e.point.name);
//							}else{
//    							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="电流平衡"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setRawValue(e.point.name);
//							}else{
//    							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="日用电量"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setRawValue(e.point.name);
//							}else{
//    							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisRealtimeDetailsRydlCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWellCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisRealtimeDetailsIBalanceCom_Id").setRawValue("");
//						}
//	              		
//	              		var ElectricAnalysisRealtimeDetails=Ext.getCmp("ElectricAnalysisRealtimeDetails_Id");
//                		if(isNotVal(ElectricAnalysisRealtimeDetails)){
//                			ElectricAnalysisRealtimeDetails.getStore().loadPage(1);
//                		}else{
//                			Ext.create('AP.store.electricAnalysis.ElectricAnalysisRealtimeDetailsListStore');
//                		}
//					}
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

function initElectricAnalysisRealtimePrifilePieChart(data,divId,title) {
	var get_rawData = data;
	var datalist=get_rawData.totalRoot;
	
	var pieDataStr="[";
	for(var i=0;i<datalist.length;i++){
		pieDataStr+="['"+datalist[i].item+"',"+datalist[i].count+"]";
		if(i<datalist.length-1){
			pieDataStr+=",";
		}
	}
	pieDataStr+="]";
	var pieData = Ext.JSON.decode(pieDataStr);
	showElectricAnalysisRealtimePrifilePieChart(title,divId, "井数占", pieData);
}

function getElectricAnalysisRealtimeProfilePieData(type,divId,title){
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getElectricAnalysisRealtimeProfilePieData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			initElectricAnalysisRealtimePrifilePieChart(result,divId,title)
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:leftOrg_Id,
			type:type
        }
	});
};

function getElectricAnalysisRealtimeProfileType() {
	var type="runStatusName";
	var tabPanel = Ext.getCmp("electricAanlysisRealtimeProfileTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="electricAanlysisRealtimeRunSatausProfilePanel_Id"){
		type="runStatusName";
	}else if(activeId=="electricAanlysisRealtimeRuntimeEfficiencyProfilePanel_Id"){
		type="runtimeefficiencyLevel";
	}else if(activeId=="electricAanlysisRealtimeWorkingConditionProfilePanel_Id"){
		type="workingConditionName_Elec";
	}else if(activeId=="electricAanlysisRealtimeWattDegreeBalanceProfilePanel_Id"){
		type="wattdegreebalanceName";
	}else if(activeId=="electricAanlysisRealtimeIDegreeBalanceProfilePanel_Id"){
		type="idegreebalanceName";
	}else if(activeId=="electricAanlysisRealtimeTodayWattEnergyProfilePanel_Id"){
		type="todayWattEnergyLevel";
	}else{
		type="runStatusName";
	}
	return type;
}

function createElecAnalysisRealtimeProfileTableColumn(columnInfo) {
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
        if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:false";
        }else {
            myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};



function getElectricAnalysisRealtimeProfileData(type,divId,title){
	getElectricAnalysisRealtimeProfileStatusData();
	
	var gridPanel=Ext.getCmp("electricAanlysisRealtimeProfileTablePanel_Id");
	if(isNotVal(gridPanel)){
		gridPanel.destroy();
	}
	Ext.create("AP.store.electricAnalysis.ElectricAnalysisRealtimeProfileListStore");
	
	getElectricAnalysisRealtimeProfilePieData("runStatusName","electricAnalysisRealtimeProfileDiv1_id","运行状态");
	getElectricAnalysisRealtimeProfilePieData("runtimeefficiencyLevel","electricAnalysisRealtimeProfileDiv2_id","运行时率");
	getElectricAnalysisRealtimeProfilePieData("workingConditionName_Elec","electricAnalysisRealtimeProfileDiv3_id","电参工况");
	getElectricAnalysisRealtimeProfilePieData("wattdegreebalanceName","electricAnalysisRealtimeProfileDiv4_id","功率平衡");
	getElectricAnalysisRealtimeProfilePieData("idegreebalanceName","electricAnalysisRealtimeProfileDiv5_id","电流平衡");
	getElectricAnalysisRealtimeProfilePieData("todayWattEnergyLevel","electricAnalysisRealtimeProfileDiv6_id","日用电量");
};
