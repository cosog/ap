/*******************************************************************************
 * 扭矩最大值法平衡监测视图
 *
 * @author zhao
 *
 */

Ext.define("AP.view.balanceMonitor.MonitorTorqueMaxValueView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monitorTorqueMaxValueView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var MonitorTorqueMaxValueStore=Ext.create("AP.store.balanceMonitor.MonitorTorqueMaxValueStore");
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
                    var jh_tobj = Ext.getCmp('TorqueMaxValueBalanceMonitorjh_Id').getValue();
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
            id: "TorqueMaxValueBalanceMonitorjh_Id",
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
                	var surfaceCardUploadGridpanel = Ext.getCmp("MonitorStatTorqueMaxValueGrid_Id");
                	if(isNotVal(surfaceCardUploadGridpanel)){
                		var gridPanel_model = surfaceCardUploadGridpanel.getSelectionModel();
                		var record = gridPanel_model.getSelection();
                		if(record.length>0){
                			gridPanel_model.clearSelections();
                		}
                	}
                    Ext.getCmp("MonitorTorqueMaxValueGrid_Id").getStore().load();
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
                            id: 'TorqueMaxValueBalaceStatusPanel_Id',
                            layout: 'fit',
                            border: false,
                            height: '50%',
                            collapsible: false, // 是否折叠
                            split: true, // 竖折叠条
                            tbar:[jhComboBox,{
                                id: 'TorqueMaxValueBalanceMonitorSelectedGkmc_Id',
                                xtype: 'textfield',
                                value: '',
                                hidden: true
                            },{
                                xtype: 'button',
                                text: cosog.string.exportExcel,
                                pressed: true,
                                handler: function (v, o) {
                                	var gridId="MonitorTorqueMaxValueGrid_Id";
                                	var url=context + '/balanceMonitorController/exportMonitorBalanceStatusExcelData';
                                	var fileName=cosog.string.balanceStatusExcel;
                                	var title=cosog.string.balanceStatusExcel;
                                	var jh = Ext.getCmp('TorqueMaxValueBalanceMonitorjh_Id').getValue();
                                	var gkmc=Ext.getCmp('TorqueMaxValueBalanceMonitorSelectedGkmc_Id').getValue();
                                	var type=2;
                                	exportMonitorBalanceStatusExcelData(jh,gkmc,gridId,type,fileName,title,url);
                                }
                            },'->',{
                                xtype: 'button',
                                text: cosog.string.exportBalanceReport,
                                pressed: true,
                                handler: function (v, o) {
                                	var jlbh=Ext.getCmp("MonitorTorqueMaxValueGrid_Id").getSelectionModel().getSelection()[0].data.jlbh;
                                	var jh=Ext.getCmp("MonitorTorqueMaxValueGrid_Id").getSelectionModel().getSelection()[0].data.jh;
                                	if(jlbh>0){
                                		var gtChart = $("#MonitorTorqueMaxValueGtDiv_Id").highcharts();  
                                        var torqueCurveChart1 = $("#MonitorTorqueMaxValueTorqueCurve1Div_Id").highcharts();
                                        var torqueCurveChart2 = $("#MonitorTorqueMaxValueTorqueCurve2Div_Id").highcharts();
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
                         id:'MonitorStatTorqueMaxValueTabpanel_Id',
                         activeTab: 0,
                         border: true,
                         tabPosition: 'top',
                         items: [
//                        	 {
//                             title:cosog.string.balanceStattable,
//                             layout: 'fit',
//                             id:'MonitorStatTorqueMaxValuePanel_Id'
//                             
//                         },
                         {
                             title:'统计图',
                             layout: 'fit',
                             id:'MonitorStatTorqueMaxValuePiePanel_Id',
                             html:'<div id="MonitorStatTorqueMaxValuePieDiv_Id" style="width:100%;height:100%;"></div>',
                         }
//                         ,{
//                             title:cosog.string.column,
//                             layout: 'fit',
//                             id:'MonitorStatTorqueMaxValueColumnPanel_Id',
//                             html:'<div id="MonitorStatTorqueMaxValueColumnDiv_Id" style="width:100%;height:100%;"></div>',
//                         }
                         ],
                         listeners: {
                             tabchange: function (tabPanel, newCard, oldCard,obj) {
                            	var tabPanel = Ext.getCmp("MonitorStatTorqueMaxValueTabpanel_Id");
                      			var activeId = tabPanel.getActiveTab().id;
                     			if(activeId!="MonitorStatTorqueMaxValuePanel_Id"){
                    				Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMaxValueStore");
                    			}else{
                    				var MonitorStatTorqueMaxValueGrid_Id = Ext.getCmp("MonitorStatTorqueMaxValueGrid_Id");
                    				if(isNotVal(MonitorStatTorqueMaxValueGrid_Id)){
                    					MonitorStatTorqueMaxValueGrid_Id.getStore().load();
                    				}else{
                    					Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMaxValueStore");
                    				}
                    			}
                             },
                             resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	 var MonitorTorqueMaxValueGrid = Ext.getCmp("MonitorTorqueMaxValueGrid_Id");
                            	 if(isNotVal(MonitorTorqueMaxValueGrid)){
                            		 var tabPanel = Ext.getCmp("MonitorStatTorqueMaxValueTabpanel_Id");
                          			 var activeId = tabPanel.getActiveTab().id;
                          			 if(activeId!="MonitorStatTorqueMaxValuePanel_Id"){
                          				 Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMaxValueStore");
                          			 }else{
                          				var MonitorStatTorqueMaxValueGrid_Id = Ext.getCmp("MonitorStatTorqueMaxValueGrid_Id");
                         				if(isNotVal(MonitorStatTorqueMaxValueGrid_Id)){
                         					MonitorStatTorqueMaxValueGrid_Id.getStore().load();
                         				}else{
                         					Ext.create("AP.store.balanceMonitor.MonitorStatTorqueMaxValueStore");
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
////                                html:'<div id="MonitorTorqueMaxValueMapDiv_Id" style="width:100%;height:100%;"></div>',
//                                html:'<div id="MonitorTorqueMaxValueMapDiv_Id" style="width:100%;height:100%;-webket-flex:3;-moz-flex:3;-ms-flex:3;-o-flex:3;flex:3;-webkit-display:flex;-webkit-flex-direction: column;-moz-display:flex;-moz-flex-direction: column;-ms-display:flex;-ms-flex-direction: column;-o-display:flex;-o-flex-direction: column;display:flex;flex-direction: column;margin: 0px;padding: 0px;"></div>',
//                                listeners: {
//                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
////                                    	getCustomMapFrontDataAndShowMap(1,"MonitorTorqueMaxValueMapDiv_Id");
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
                                            id: 'TorqueMaxValueMonitorDataPanel_Id'
                                        }, {
                                            region: 'center',
                                            layout: 'fit',
                                            id:'MonitorTorqueMaxValueGtPanel_Id',
                                            html:'<div id="MonitorTorqueMaxValueGtDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	var MonitorTorqueMaxValueAnalysisGrid = Ext.getCmp("MonitorTorqueMaxValueAnalysisGrid_Id");
                                                    if (isNotVal(MonitorTorqueMaxValueAnalysisGrid)) {
                                                    	var gtjh="";
                                                        var Gridpanel = Ext.getCmp("MonitorTorqueMaxValueGrid_Id");
                                                    	if(isNotVal(Gridpanel)){
                                                    		var record = Gridpanel.getSelectionModel().getSelection();
                                                    		if(record.length>0){
                                                    			Gridpanel=record[0].data.jh;
                                                    		}
                                                    	}
                                                    	showBalanceAnalysisSurfaceCardChart(MonitorTorqueMaxValueAnalysisGrid.getStore(),"MonitorTorqueMaxValueGtDiv_Id",gtjh);
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
                                        id:'MonitorTorqueMaxValueTorqueCurveTabpanel_Id',
                                        items: [{
                                            title:cosog.string.torqueCurve,
                                            layout: 'border',
                                            id:'MonitorTorqueMaxValueTorqueCurvePanel_Id',
                                            items:[{
                                            	region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id:'MonitorTorqueMaxValueTorqueCurve1Panel_Id',
                                                html:'<div id="MonitorTorqueMaxValueTorqueCurve1Div_Id" style="width:100%;height:100%;"></div>'
                                            },{
                                            	region: 'center',
                                                layout: 'fit',
                                                id:'MonitorTorqueMaxValueTorqueCurve2Panel_Id',
                                                html:'<div id="MonitorTorqueMaxValueTorqueCurve2Div_Id" style="width:100%;height:100%;"></div>'
                                            }]
                                            
                                        },{
                                            title:cosog.string.pumpingUnotMotion,
                                            layout: 'border',
                                            id:'MonitorTorqueMaxValueTorqueMotionPanel_Id',
                                            items:[{
                                            	region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id:'TorqueMaxValuePRAndTFPanel_Id'
                                            },{
                                            	region: 'center',
                                                layout: 'fit',
                                                id:'MonitorTorqueMaxValueMotionCurvePanel_Id',
                                                html:'<div id="MonitorTorqueMaxValueMotionCurveDiv_Id" style="width:100%;height:100%;"></div>'
                                            }]
                                        }],
                                        listeners: {
                                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                                        		var MonitorTorqueMaxValueAnalysisGrid = Ext.getCmp("MonitorTorqueMaxValueAnalysisGrid_Id");
                                                if (isNotVal(MonitorTorqueMaxValueAnalysisGrid)) {
                                                	var tabPanel = Ext.getCmp("MonitorTorqueMaxValueTorqueCurveTabpanel_Id");
                                        			var activeId = tabPanel.getActiveTab().id;
                                        			if(activeId=="MonitorTorqueMaxValueTorqueCurvePanel_Id"){
                                        				showBalanceAnalysisCurveChart(MonitorTorqueMaxValueAnalysisGrid.getStore(),"MonitorTorqueMaxValueTorqueCurve1Div_Id","MonitorTorqueMaxValueTorqueCurve2Div_Id");
                                        			}else{
                                        				showBalanceAnalysisMotionCurveChart(MonitorTorqueMaxValueAnalysisGrid.getStore(),"TorqueMaxValuePRAndTFPanel_Id","MonitorTorqueMaxValueMotionCurveGrid_Id","MonitorTorqueMaxValueMotionCurveDiv_Id");
                                        			}
                                                }
                                            },
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	var MonitorTorqueMaxValueAnalysisGrid = Ext.getCmp("MonitorTorqueMaxValueAnalysisGrid_Id");
                                                if (isNotVal(MonitorTorqueMaxValueAnalysisGrid)) {
                                                	var tabPanel = Ext.getCmp("MonitorTorqueMaxValueTorqueCurveTabpanel_Id");
                                        			var activeId = tabPanel.getActiveTab().id;
                                        			if(activeId=="MonitorTorqueMaxValueTorqueCurvePanel_Id"){
                                        				showBalanceAnalysisCurveChart(MonitorTorqueMaxValueAnalysisGrid.getStore(),"MonitorTorqueMaxValueTorqueCurve1Div_Id","MonitorTorqueMaxValueTorqueCurve2Div_Id");
                                        			}else{
                                        				showBalanceAnalysisMotionCurveChart(MonitorTorqueMaxValueAnalysisGrid.getStore(),"TorqueMaxValuePRAndTFPanel_Id","MonitorTorqueMaxValueMotionCurveGrid_Id","MonitorTorqueMaxValueMotionCurveDiv_Id");
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
