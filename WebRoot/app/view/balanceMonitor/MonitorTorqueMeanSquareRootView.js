/*******************************************************************************
 * 扭矩均方根法平衡监测视图
 *
 * @author zhao
 *
 */

Ext.define("AP.view.balanceMonitor.MonitorTorqueMeanSquareRootView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monitorTorqueMeanSquareRootView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var MonitorTorqueMeanSquareRootStore=Ext.create("AP.store.balanceMonitor.MonitorTorqueMeanSquareRootStore");
        var jhStore_A = new Ext.data.JsonStore({
        	pageSize:defaultJhhComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/monitorPumpingUnitParamsManagerController/queryMonitorPUJhh',
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
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var jh_tobj = Ext.getCmp('TorqueMeanSquareRootBalanceMonitorjh_Id').getValue();
                    var new_params = {
                    	jh:jh_tobj,
                        type: 'jh',
                        orgId: org_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var jhComboBox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.jh,
            id: "TorqueMeanSquareRootBalanceMonitorjh_Id",
            store:jhStore_A,
            labelWidth: 35,
            width:145,
            queryMode: 'remote',
            typeAhead: true,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            pageSize:comboxPagingStatus,
            minChars:0,
            multiSelect:false, 
            listeners: {
                expand: function (sm, selections) {
//                	jhComboBox.clearValue();
                	jhComboBox.getStore().load();
                },
                select: function (combo, record, index) {
                	var surfaceCardUploadGridpanel = Ext.getCmp("MonitorStatTorqueMeanSquareRootGrid_Id");
                	if(isNotVal(surfaceCardUploadGridpanel)){
                		var gridPanel_model = surfaceCardUploadGridpanel.getSelectionModel();
                		var record = gridPanel_model.getSelection();
                		if(record.length>0){
                			gridPanel_model.clearSelections();
                		}
                	}
                    Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id").getStore().load();
                }
            }
        });
        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                items: [
                    {
                        region: 'west',
                        title: cosog.string.balanceStatusTable,
                        layout: 'border',
                        border: false,
                        width: '30%',
                        collapsible: true, // 是否折叠
                        split: true, // 竖折叠条
                        items: [{
                            region: 'north',
                            id: 'TorqueMeanSquareRootBalaceStatusPanel_Id',
                            layout: 'fit',
                            border: false,
                            height: '50%',
                            collapsible: false, // 是否折叠
                            split: true, // 竖折叠条
                            tbar:[jhComboBox,{
                                id: 'TorqueMeanSquareRootBalanceMonitorSelectedGkmc_Id',
                                xtype: 'textfield',
                                value: '',
                                hidden: true
                            },{
                                xtype: 'button',
                                text: cosog.string.exportExcel,
                                pressed: true,
                                handler: function (v, o) {
                                	var gridId="MonitorTorqueMeanSquareRootGrid_Id";
                                	var url=context + '/balanceMonitorController/exportMonitorBalanceStatusExcelData';
                                	var fileName=cosog.string.balanceStatusExcel;
                                	var title=cosog.string.balanceStatusExcel;
                                	var jh = Ext.getCmp('TorqueMeanSquareRootBalanceMonitorjh_Id').getValue();
                                	var gkmc=Ext.getCmp('TorqueMeanSquareRootBalanceMonitorSelectedGkmc_Id').getValue();
                                	var type=1;
                                	exportMonitorBalanceStatusExcelData(jh,gkmc,gridId,type,fileName,title,url);
                                }
                            },'->',{
                                xtype: 'button',
                                text: cosog.string.exportBalanceReport,
                                pressed: true,
                                handler: function (v, o) {
                                	var jlbh=Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id").getSelectionModel().getSelection()[0].data.jlbh;
                                	var jh=Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id").getSelectionModel().getSelection()[0].data.jh;
                                	if(jlbh>0){
                                		var gtChart = $("#MonitorTorqueMeanSquareRootGtDiv_Id").highcharts();  
                                        var torqueCurveChart1 = $("#MonitorTorqueMeanSquareRootTorqueCurve1Div_Id").highcharts();
                                        var torqueCurveChart2 = $("#MonitorTorqueMeanSquareRootTorqueCurve2Div_Id").highcharts();
                                        var svg_gt = gtChart.getSVG();  
                                        var svg_torqueCurve1 = torqueCurveChart1.getSVG();
                                        var svg_torqueCurve2 = torqueCurveChart2.getSVG();
                                        var svg = svg_gt+"_"+svg_torqueCurve1+"_"+svg_torqueCurve2; 
                                        var svg2=svg.replace(/#/g,"%23");
//                                        alert(svg2);
                                        var url=context + '/balanceMonitorController/exportWellBalanceReport?jlbh='+jlbh+'&jh='+jh+'&fileName='+cosog.string.balanceReportPdf+'&type=pdf&svg=' + svg2;
                                        document.location.href = url;
                                	}
                                }
                            }]
                     }, {
                    	 region: 'center',
                         xtype: 'tabpanel',
                         id:'MonitorStatTorqueMeanSquareRootTabpanel_Id',
                         activeTab: 0,
                         border: true,
                         tabPosition: 'top',
                         items: [
//                        	 {
//                             title:cosog.string.balanceStattable,
//                             layout: 'fit',
//                             id:'MonitorStatTorqueMeanSquareRootPanel_Id'
//                             
//                         },
                         {
                             title:'统计图',
                             layout: 'fit',
                             id:'MonitorStatTorqueMeanSquareRootPiePanel_Id',
                             html:'<div id="MonitorStatTorqueMeanSquareRootPieDiv_Id" style="width:100%;height:100%;"></div>',
                         }
//                         ,{
//                             title:cosog.string.column,
//                             layout: 'fit',
//                             id:'MonitorStatTorqueMeanSquareRootColumnPanel_Id',
//                             html:'<div id="MonitorStatTorqueMeanSquareRootColumnDiv_Id" style="width:100%;height:100%;"></div>',
//                         }
                         ],
                         listeners: {
                             tabchange: function (tabPanel, newCard, oldCard,obj) {
                            	 
                             },
                             resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	 var MonitorTorqueMeanSquareRootGrid = Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id");
                            	 if(isNotVal(MonitorTorqueMeanSquareRootGrid)){
                            		 var tabPanel = Ext.getCmp("MonitorStatTorqueMeanSquareRootTabpanel_Id");
                          			 var activeId = tabPanel.getActiveTab().id;
                          			 if(activeId!="MonitorStatTorqueMeanSquareRootPanel_Id"){
                          				 Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMeanSquareRootStore");
                          			 }else{
                          				var MonitorStatTorqueMeanSquareRootGrid_Id = Ext.getCmp("MonitorStatTorqueMeanSquareRootGrid_Id");
                         				if(isNotVal(MonitorStatTorqueMeanSquareRootGrid_Id)){
                         					MonitorStatTorqueMeanSquareRootGrid_Id.getStore().load();
                         				}else{
                         					Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMeanSquareRootStore");
                         				}
                         			}
                            	 }
                             }
                         }
                     }]
                },
                    {
                        region: 'center',
                        border: false,
                        layout: 'fit',
                        items: [
//                            {
////                                region: 'west',
//                                region: 'center',
//                                border: false,
//                                layout: 'fit',
////                                width: '100%',
////                                collapsible: true, // 是否折叠
////                                split: true, // 竖折叠条
////                                html:'<div id="MonitorTorqueMeanSquareRootMapDiv_Id" style="width:100%;height:100%;"></div>',
//                                html:'<div id="MonitorTorqueMeanSquareRootMapDiv_Id" style="width:100%;height:100%;-webket-flex:3;-moz-flex:3;-ms-flex:3;-o-flex:3;flex:3;-webkit-display:flex;-webkit-flex-direction: column;-moz-display:flex;-moz-flex-direction: column;-ms-display:flex;-ms-flex-direction: column;-o-display:flex;-o-flex-direction: column;display:flex;flex-direction: column;margin: 0px;padding: 0px;"></div>',
//                                listeners: {
//                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
////                                    	getCustomMapFrontDataAndShowMap(1,"MonitorTorqueMeanSquareRootMapDiv_Id");
//                                    }
//                                }
//                            },
                            {
//                                region: 'center',
//                                region: 'west',
//                                width: '100%',
                                collapsible: false, // 是否折叠
                                split: false, // 竖折叠条
                                layout: 'border',
                                id: '',
                                border: false,
                                items: [
                                    {
                                    	title:cosog.string.balanceAnalysisData,
                                        region: 'west',
                                        border: false,
                                        layout: 'border',
                                        width: '35%',
                                        collapsible: false, // 是否折叠
                                        split: false, // 竖折叠条
                                        items: [{
                                            region: 'north',
                                            border: false,
                                            layout: 'fit',
                                            height: '50%',
                                            collapsible: false, // 是否折叠
                                            split: true, // 竖折叠条
                                            id: 'TorqueMeanSquareRootMonitorDataPanel_Id'
                                        }, {
                                            region: 'center',
                                            layout: 'fit',
                                            id:'MonitorTorqueMeanSquareRootGtPanel_Id',
                                            html:'<div id="MonitorTorqueMeanSquareRootGtDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	var MonitorTorqueMeanSquareRootAnalysisGrid = Ext.getCmp("MonitorTorqueMeanSquareRootAnalysisGrid_Id");
                                                    if (isNotVal(MonitorTorqueMeanSquareRootAnalysisGrid)) {
                                                    	var gtjh="";
                                                        var Gridpanel = Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id");
                                                    	if(isNotVal(Gridpanel)){
                                                    		var record = Gridpanel.getSelectionModel().getSelection();
                                                    		if(record.length>0){
                                                    			Gridpanel=record[0].data.jh;
                                                    		}
                                                    	}
                                                    	showBalanceAnalysisSurfaceCardChart(MonitorTorqueMeanSquareRootAnalysisGrid.getStore(),"MonitorTorqueMeanSquareRootGtDiv_Id",gtjh);
                                                    }
                                                }
                                            }
                                        }]
                                    },
                                    {
                                        region: 'center',
                                        xtype: 'tabpanel',
                                        activeTab: 0,
                                        border: true,
                                        tabPosition: 'top',
                                        id:'MonitorTorqueMeanSquareRootTorqueCurveTabpanel_Id',
                                        items: [{
                                            title:cosog.string.torqueCurve,
                                            layout: 'border',
                                            id:'MonitorTorqueMeanSquareRootTorqueCurvePanel_Id',
                                            items:[{
                                            	region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id:'MonitorTorqueMeanSquareRootTorqueCurve1Panel_Id',
                                                html:'<div id="MonitorTorqueMeanSquareRootTorqueCurve1Div_Id" style="width:100%;height:100%;"></div>'
                                            },{
                                            	region: 'center',
                                                layout: 'fit',
                                                id:'MonitorTorqueMeanSquareRootTorqueCurve2Panel_Id',
                                                html:'<div id="MonitorTorqueMeanSquareRootTorqueCurve2Div_Id" style="width:100%;height:100%;"></div>'
                                            }]
                                            
                                        },{
                                            title:cosog.string.pumpingUnotMotion,
                                            layout: 'border',
                                            id:'MonitorTorqueMeanSquareRootTorqueMotionPanel_Id',
                                            items:[{
                                            	region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id:'TorqueMeanSquareRootPRAndTFPanel_Id'
                                            },{
                                            	region: 'center',
                                                layout: 'fit',
                                                id:'MonitorTorqueMeanSquareRootMotionCurvePanel_Id',
                                                html:'<div id="MonitorTorqueMeanSquareRootMotionCurveDiv_Id" style="width:100%;height:100%;"></div>'
                                            }]
                                        }],
                                        listeners: {
                                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                                        		var MonitorTorqueMeanSquareRootAnalysisGrid = Ext.getCmp("MonitorTorqueMeanSquareRootAnalysisGrid_Id");
                                                if (isNotVal(MonitorTorqueMeanSquareRootAnalysisGrid)) {
                                                	var tabPanel = Ext.getCmp("MonitorTorqueMeanSquareRootTorqueCurveTabpanel_Id");
                                        			var activeId = tabPanel.getActiveTab().id;
                                        			if(activeId=="MonitorTorqueMeanSquareRootTorqueCurvePanel_Id"){
                                        				showBalanceAnalysisCurveChart(MonitorTorqueMeanSquareRootAnalysisGrid.getStore(),"MonitorTorqueMeanSquareRootTorqueCurve1Div_Id","MonitorTorqueMeanSquareRootTorqueCurve2Div_Id");
                                        			}else{
                                        				showBalanceAnalysisMotionCurveChart(MonitorTorqueMeanSquareRootAnalysisGrid.getStore(),"TorqueMeanSquareRootPRAndTFPanel_Id","MonitorTorqueMeanSquareRootMotionCurveGrid_Id","MonitorTorqueMeanSquareRootMotionCurveDiv_Id");
                                        			}
                                                }
                                            },
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	var MonitorTorqueMeanSquareRootAnalysisGrid = Ext.getCmp("MonitorTorqueMeanSquareRootAnalysisGrid_Id");
                                                if (isNotVal(MonitorTorqueMeanSquareRootAnalysisGrid)) {
                                                	var tabPanel = Ext.getCmp("MonitorTorqueMeanSquareRootTorqueCurveTabpanel_Id");
                                        			var activeId = tabPanel.getActiveTab().id;
                                        			if(activeId=="MonitorTorqueMeanSquareRootTorqueCurvePanel_Id"){
                                        				showBalanceAnalysisCurveChart(MonitorTorqueMeanSquareRootAnalysisGrid.getStore(),"MonitorTorqueMeanSquareRootTorqueCurve1Div_Id","MonitorTorqueMeanSquareRootTorqueCurve2Div_Id");
                                        			}else{
                                        				showBalanceAnalysisMotionCurveChart(MonitorTorqueMeanSquareRootAnalysisGrid.getStore(),"TorqueMeanSquareRootPRAndTFPanel_Id","MonitorTorqueMeanSquareRootMotionCurveGrid_Id","MonitorTorqueMeanSquareRootMotionCurveDiv_Id");
                                        			}
                                                }
                                            }
                                        }
                        }]
                    }
                    ]
                }]
     }]
        });
        me.callParent(arguments);
    }

});