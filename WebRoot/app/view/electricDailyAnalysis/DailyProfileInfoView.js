Ext.define("AP.view.electricDailyAnalysis.DailyProfileInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.electricAnalysisDailyProfileInfoView',
    id: 'ElectricAnalysisDailyProfileInfoView_Id',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
                tbar:[
                	{
                		xtype: 'textfield',
                        id: "electricAnalysisDailyProfileRunTextfield_id",
                        anchor: '100%',
                        labelWidth: 30,
                        width: 80,
                        fieldLabel: '运行',
                        readOnly: true,
                        listeners: {
                        }
                	},'-',{
                		xtype: 'textfield',
                        id: "electricAnalysisDailyProfileStopTextfield_id",
                        anchor: '100%',
                        labelWidth: 30,
                        width: 80,
                        fieldLabel: '停抽',
                        readOnly: true,
                        listeners: {}
                	},'-',{
                		xtype: 'textfield',
                        id: "electricAnalysisDailyProfileWarnningTextfield_id",
                        anchor: '100%',
                        labelWidth: 30,
                        width: 80,
                        fieldLabel: '报警',
                        readOnly: true,
                        listeners: {}
                	},'-',{
                        xtype: 'datefield',
                        anchor: '100%',
                        fieldLabel: '',
                        labelWidth: 0,
                        width: 90,
                        format: 'Y-m-d ',
                        id: 'electricAnalysisDailyDetailsDate_Id',
                        value: new Date(),
                        listeners: {
                        	select: function (combo, record, index) {}
                        }
                    },'-',{
                        xtype: 'button',
                        text:'刷新',
                        pressed: true,
                        iconCls: 'note-refresh',
                        handler: function (v, o) {
                        	getElectricAnalysisDailyProfileData();
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
                    	id:'electricAanlysisDailyProfileTabPanel_Id',
                    	items: [{
                    		title:'运行状态',
                    		iconCls: 'select',
                    		id:'electricAanlysisDailyRunSatausProfilePanel_Id'
                    	},{
                    		title:'运行时率',
                    		layout: 'fit',
                    		id:'electricAanlysisDailyRuntimeEfficiencyProfilePanel_Id'
                    	},{
                    		title:'电参工况',
                    		layout: 'fit',
                    		id:'electricAanlysisDailyWorkingConditionProfilePanel_Id'
                    	},{
                    		title:'功率平衡',
                    		layout: 'fit',
                    		id:'electricAanlysisDailyWattDegreeBalanceProfilePanel_Id'
                    	},{
                    		title:'电流平衡',
                    		layout: 'fit',
                    		id:'electricAanlysisDailyIDegreeBalanceProfilePanel_Id'
                    	},{
                    		title:'日用电量',
                    		layout: 'fit',
                    		id:'electricAanlysisDailyTodayWattEnergyProfilePanel_Id'
                    	}],
                    	listeners: {
                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                        		newCard.setIconCls("select");
                        		oldCard.setIconCls("");
                        		var gridPanel=Ext.getCmp("electricAanlysisDailyProfileTablePanel_Id");
                        		if(isNotVal(gridPanel)){
                        			gridPanel.destroy();
                        		}
                        		Ext.create("AP.store.electricDailyAnalysis.ElectricAnalysisDailyProfileListStore");
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
                                    html: '<div id=\"electricAnalysisDailyProfileDiv1_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisDailyProfileDiv1_id").highcharts()!=undefined){
                                    			$("#electricAnalysisDailyProfileDiv1_id").highcharts().setSize($("#electricAnalysisDailyProfileDiv1_id").offsetWidth, $("#electricAnalysisDailyProfileDiv1_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisDailyProfileDiv2_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisDailyProfileDiv2_id").highcharts()!=undefined){
                                    			$("#electricAnalysisDailyProfileDiv2_id").highcharts().setSize($("#electricAnalysisDailyProfileDiv2_id").offsetWidth, $("#electricAnalysisDailyProfileDiv2_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisDailyProfileDiv3_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisDailyProfileDiv3_id").highcharts()!=undefined){
                                    			$("#electricAnalysisDailyProfileDiv3_id").highcharts().setSize($("#electricAnalysisDailyProfileDiv3_id").offsetWidth, $("#electricAnalysisDailyProfileDiv3_id").offsetHeight,true);
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
                                    html: '<div id=\"electricAnalysisDailyProfileDiv4_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisDailyProfileDiv4_id").highcharts()!=undefined){
                                    			$("#electricAnalysisDailyProfileDiv4_id").highcharts().setSize($("#electricAnalysisDailyProfileDiv4_id").offsetWidth, $("#electricAnalysisDailyProfileDiv4_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                        	    	margin: '0 1 0 0',
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisDailyProfileDiv5_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisDailyProfileDiv5_id").highcharts()!=undefined){
                                    			$("#electricAnalysisDailyProfileDiv5_id").highcharts().setSize($("#electricAnalysisDailyProfileDiv5_id").offsetWidth, $("#electricAnalysisDailyProfileDiv5_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    },{
                        	    	border: true,
                                    flex: 1,
                                    html: '<div id=\"electricAnalysisDailyProfileDiv6_id\" style="width:100%;height:100%;"></div>',
                                    listeners: {
                                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                        	if($("#electricAnalysisDailyProfileDiv6_id").highcharts()!=undefined){
                                    			$("#electricAnalysisDailyProfileDiv6_id").highcharts().setSize($("#electricAnalysisDailyProfileDiv6_id").offsetWidth, $("#electricAnalysisDailyProfileDiv6_id").offsetHeight,true);
                                    		}
                                        }
                                    }
                        	    }]
                        	}
                        ]
                    }]
                }
        });
        me.callParent(arguments);
    }
});

function getElectricAnalysisDailyProfileStatusData(){
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	var date=Ext.getCmp('electricAnalysisDailyDetailsDate_Id').rawValue;
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getElectricAnalysisDailyProfileData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			Ext.getCmp("electricAnalysisDailyProfileRunTextfield_id").setValue(result.runWellCount);
			Ext.getCmp("electricAnalysisDailyProfileStopTextfield_id").setValue(result.stopWellCount);
			Ext.getCmp("electricAnalysisDailyProfileWarnningTextfield_id").setValue(result.warnningWellCount);
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:leftOrg_Id,
			date:date
        }
	});
}

function showElectricAnalysisDailyPrifilePieChart(title,divid, name, data) {
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
//           	    	 	var getTabId = tabPanel.getComponent("ElectricAnalysis_ElectricAnalysisDailyDetails");
//           	    	 	
//                        if (getTabId) {
//                        	
//                        }else {
//                       	 	tabPanel.add(Ext.create("AP.view.electricAnalysis.DailyDetailsInfoView", {
//                                id: "ElectricAnalysis_ElectricAnalysisDailyDetails",
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
//                        Ext.getCmp("topModule_Id").setValue("ElectricAnalysis_ElectricAnalysisDailyDetails");
//                        tabPanel.setActiveTab("ElectricAnalysis_ElectricAnalysisDailyDetails");
//                        Ext.getCmp("electricAnalysisDailyDetailsTabpanel_Id").setActiveTab("electricAnalysisDailyDetailsDiscretePanel_Id");
//                        Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatType_Id").setValue("");
//           	    	 	Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatValue_Id").setValue("");
//                        Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setValue("");
//	            		Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setRawValue("");
//	            		Ext.getCmp("electricAnalysisDailyDetailsRTBtn_Id").hide();
//	                    Ext.getCmp("electricAnalysisDailyDetailsStartDate_Id").hide();
//	              		Ext.getCmp("electricAnalysisDailyDetailsEndDate_Id").hide();
//	              		Ext.getCmp("electricAnalysisDailyDetailsAllWellBtn_Id").show();
//	              		Ext.getCmp("electricAnalysisDailyDetailsDiscreteCurve_Id").collapse();
//                		$("#electricAnalysisDailyDetailsDiscreteCurveDiv_Id").html('');
//	              		if(statType=="运行状态"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatType_Id").setValue("runStatus");
//								Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatValue_Id").setValue(e.point.name);
//							}else{
//								Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatType_Id").setValue("");
//								Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatValue_Id").setValue('');
//							}
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="运行时率"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setRawValue(e.point.name);
//							}else{
//								Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="电参工况"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setRawValue(e.point.name);
//							}else{
//								Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="功率平衡"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setRawValue(e.point.name);
//							}else{
//    							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="电流平衡"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setRawValue(e.point.name);
//							}else{
//    							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setRawValue("");
//						}else if(statType=="日用电量"){
//							if(!e.point.selected){//如果被选中
//								Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setValue(e.point.name);
//    							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setRawValue(e.point.name);
//							}else{
//    							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setValue("");
//    							Ext.getCmp("electricAnalysisDailyDetailsRydlCom_Id").setRawValue("");
//							}
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatType_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsDiscreteStatValue_Id").setValue('');
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsJh_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsTimeEffCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsEgkmcCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsWattBalanceCom_Id").setRawValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setValue("");
//							Ext.getCmp("electricAnalysisDailyDetailsIBalanceCom_Id").setRawValue("");
//						}
//	              		
//	              		var ElectricAnalysisDailyDetails=Ext.getCmp("ElectricAnalysisDailyDetails_Id");
//                		if(isNotVal(ElectricAnalysisDailyDetails)){
//                			ElectricAnalysisDailyDetails.getStore().loadPage(1);
//                		}else{
//                			Ext.create('AP.store.electricAnalysis.ElectricAnalysisDailyDetailsListStore');
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

function initElectricAnalysisDailyPrifilePieChart(data,divId,title) {
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
	showElectricAnalysisDailyPrifilePieChart(title,divId, "井数占", pieData);
};

function getElectricAnalysisDailyProfilePieData(type,divId,title){
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	var date=Ext.getCmp('electricAnalysisDailyDetailsDate_Id').rawValue;
	Ext.Ajax.request({
		method:'POST',
		url:context + '/PSToFSController/getElectricAnalysisDailyProfilePieData',
		success:function(response) {
			var result =  Ext.JSON.decode(response.responseText);
			initElectricAnalysisDailyPrifilePieChart(result,divId,title)
		},
		failure:function(){
			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
		},
		params: {
			orgId:leftOrg_Id,
			date:date,
			type:type
        }
	});
};

function getElectricAnalysisDailyProfileType() {
	var type="runStatusName";
	var tabPanel = Ext.getCmp("electricAanlysisDailyProfileTabPanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="electricAanlysisDailyRunSatausProfilePanel_Id"){
		type="runStatusName";
	}else if(activeId=="electricAanlysisDailyRuntimeEfficiencyProfilePanel_Id"){
		type="runtimeefficiencyLevel";
	}else if(activeId=="electricAanlysisDailyWorkingConditionProfilePanel_Id"){
		type="workingconditionname_e";
	}else if(activeId=="electricAanlysisDailyWattDegreeBalanceProfilePanel_Id"){
		type="wattdegreebalanceLevel";
	}else if(activeId=="electricAanlysisDailyIDegreeBalanceProfilePanel_Id"){
		type="idegreebalanceLevel";
	}else if(activeId=="electricAanlysisDailyTodayWattEnergyProfilePanel_Id"){
		type="todayWattEnergyLevel";
	}else{
		type="runStatusName";
	}
	return type;
}

function createElecAnalysisDailyProfileTableColumn(columnInfo) {
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

function getElectricAnalysisDailyProfileData(){
	getElectricAnalysisDailyProfileStatusData();
	
	var gridPanel=Ext.getCmp("electricAanlysisDailyProfileTablePanel_Id");
	if(isNotVal(gridPanel)){
		gridPanel.destroy();
	}
	Ext.create("AP.store.electricDailyAnalysis.ElectricAnalysisDailyProfileListStore");
	
	
	getElectricAnalysisDailyProfilePieData("runStatusName","electricAnalysisDailyProfileDiv1_id","运行状态");
	getElectricAnalysisDailyProfilePieData("runtimeefficiencyLevel","electricAnalysisDailyProfileDiv2_id","运行时率")
	getElectricAnalysisDailyProfilePieData("workingconditionname_e","electricAnalysisDailyProfileDiv3_id","电参工况")
	getElectricAnalysisDailyProfilePieData("wattdegreebalanceLevel","electricAnalysisDailyProfileDiv4_id","功率平衡")
	getElectricAnalysisDailyProfilePieData("idegreebalanceLevel","electricAnalysisDailyProfileDiv5_id","电流平衡")
	getElectricAnalysisDailyProfilePieData("todaywattenergyLevel","electricAnalysisDailyProfileDiv6_id","日用电量")
}
