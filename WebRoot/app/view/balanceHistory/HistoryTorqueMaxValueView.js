/*******************************************************************************
 * 扭矩最大值历史查询视图
 *
 * @author zhao
 *
 */

Ext.define("AP.view.balanceHistory.HistoryTorqueMaxValueView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.historyTorqueMaxValueView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var TorqueMaxValueHistoryWellListStore=Ext.create("AP.store.balanceHistory.TorqueMaxValueHistoryWellListStore");
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
                    var jh_tobj = Ext.getCmp('TorqueMaxValueHistoryjh_Id').getValue();
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
            id: "TorqueMaxValueHistoryjh_Id",
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
                	Ext.getCmp("TorqueMaxValueHistoryWellListGrid_Id").getStore().load();
                }
            }
        });
        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                tbar:[jhComboBox,{
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: cosog.string.startDate,
                    labelWidth: 58,
                    width: 178,
                    format: 'Y-m-d ',
                    id: 'TorqueMaxValueHistoryStartDate_Id',
                    value: 'new',
                    listeners: {
                    	select: function (combo, record, index) {
                    		Ext.getCmp("TorqueMaxValueHistoryGtListGrid_Id").getStore().load();
                        }
                    }
                }, {
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: cosog.string.endDate,
                    labelWidth: 58,
                    width: 178,
                    format: 'Y-m-d',
                    id: 'TorqueMaxValueHistoryEndDate_Id',
                    value: 'new',
                    listeners: {
                    	select: function (combo, record, index) {
                    		Ext.getCmp("TorqueMaxValueHistoryGtListGrid_Id").getStore().load();
                        }
                    }
                },{
                    xtype: 'button',
                    text: cosog.string.exportExcel,
                    pressed: true,
                    hidden:true,
                    handler: function (v, o) {
                    	
                    }
                }],
                items: [
                    {
                        region: 'west',
                        title: cosog.string.wellList,
                        id:'TorqueMaxValueHistoryWellListPanel_Id',
                        layout: 'fit',
                        border: false,
                        width: '15%',
                        collapsible: true, // 是否折叠
                        split: true // 竖折叠条
                    },
                    {
                        region: 'center',
                        border: false,
                        layout: 'border',
                        items: [
                            {
                                region: 'west',
                                id:'TorqueMaxValueHistoryGtListPanel_Id',
                                border: false,
                                layout: 'fit',
                                width: '25%',
                                collapsible: true, // 是否折叠
                                split: true, // 竖折叠条
                                title:cosog.string.dynList
                            },
                            {
                                region: 'center',
                                layout: 'border',
                                border: false,
                                items: [
                                    {
                                    	title:cosog.string.balanceAnalysisData,
                                        region: 'west',
                                        border: false,
                                        layout: 'border',
                                        width: '40%',
                                        collapsible: false, // 是否折叠
                                        split: false, // 竖折叠条
                                        items: [{
                                            region: 'north',
                                            border: false,
                                            layout: 'fit',
                                            height: '50%',
                                            collapsible: false, // 是否折叠
                                            split: true, // 竖折叠条
                                            id: 'TorqueMaxValueHistoryDataPanel_Id'
                                        }, {
                                            region: 'center',
                                            layout: 'fit',
                                            id:'HistoryTorqueMaxValueGtPanel_Id',
                                            html:'<div id="HistoryTorqueMaxValueGtDiv_Id" style="width:100%;height:100%;"></div>',
                                            listeners: {
                                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                	var HistoryTorqueMaxValueAnalysisGrid = Ext.getCmp("HistoryTorqueMaxValueAnalysisGrid_Id");
                                                    if (isNotVal(HistoryTorqueMaxValueAnalysisGrid)) {
                                                    	var gtjh="";
                                                        var Gridpanel = Ext.getCmp("TorqueMaxValueHistoryWellListGrid_Id");
                                                    	if(isNotVal(Gridpanel)){
                                                    		var record = Gridpanel.getSelectionModel().getSelection();
                                                    		if(record.length>0){
                                                    			Gridpanel=record[0].data.jh;
                                                    		}
                                                    	}
                                                    	showBalanceAnalysisSurfaceCardChart(HistoryTorqueMaxValueAnalysisGrid.getStore(),"HistoryTorqueMaxValueGtDiv_Id",gtjh);
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
                                        id:'HistoryTorqueMaxValueTorqueCurveTabpanel_Id',
                                        items: [{
                                            title:cosog.string.torqueCurve,
                                            layout: 'border',
                                            id:'HistoryTorqueMaxValueTorqueCurvePanel_Id',
                                            items:[{
                                            	region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id:'HistoryTorqueMaxValueTorqueCurve1Panel_Id',
                                                html:'<div id="HistoryTorqueMaxValueTorqueCurve1Div_Id" style="width:100%;height:100%;"></div>'
                                            },{
                                            	region: 'center',
                                                layout: 'fit',
                                                id:'HistoryTorqueMaxValueTorqueCurve2Panel_Id',
                                                html:'<div id="HistoryTorqueMaxValueTorqueCurve2Div_Id" style="width:100%;height:100%;"></div>'
                                            }]
                                            
                                        },{
                                            title:cosog.string.pumpingUnotMotion,
                                            layout: 'border',
                                            id:'HistoryTorqueMaxValueTorqueMotionPanel_Id',
                                            items:[{
                                            	region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id:'HistoryTorqueMaxValuePRAndTFPanel_Id'
                                            },{
                                            	region: 'center',
                                                layout: 'fit',
                                                id:'HistoryTorqueMaxValueMotionCurvePanel_Id',
                                                html:'<div id="HistoryTorqueMaxValueMotionCurveDiv_Id" style="width:100%;height:100%;"></div>'
                                            }]
                                        }],
                                        listeners: {
                                        	tabchange: function (tabPanel, newCard, oldCard,obj) {
                                        		var HistoryTorqueMaxValueAnalysisGrid = Ext.getCmp("HistoryTorqueMaxValueAnalysisGrid_Id");
                                                if (isNotVal(HistoryTorqueMaxValueAnalysisGrid)) {
                                                	var tabPanel = Ext.getCmp("HistoryTorqueMaxValueTorqueCurveTabpanel_Id");
                                        			var activeId = tabPanel.getActiveTab().id;
                                        			if(activeId=="HistoryTorqueMaxValueTorqueCurvePanel_Id"){
                                        				showBalanceAnalysisCurveChart(HistoryTorqueMaxValueAnalysisGrid.getStore(),"HistoryTorqueMaxValueTorqueCurve1Div_Id","HistoryTorqueMaxValueTorqueCurve2Div_Id");
                                        			}
                                                    else{
                                        				showBalanceAnalysisMotionCurveChart(HistoryTorqueMaxValueAnalysisGrid.getStore(),"HistoryTorqueMaxValuePRAndTFPanel_Id","HistoryTorqueMaxValueMotionCurveGrid_Id","HistoryTorqueMaxValueMotionCurveDiv_Id");
                                        			}
                                                }
                                        	},
                                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                            	var HistoryTorqueMaxValueAnalysisGrid = Ext.getCmp("HistoryTorqueMaxValueAnalysisGrid_Id");
                                            	if (isNotVal(HistoryTorqueMaxValueAnalysisGrid)) {
                                                	var tabPanel = Ext.getCmp("HistoryTorqueMaxValueTorqueCurveTabpanel_Id");
                                        			var activeId = tabPanel.getActiveTab().id;
                                        			if(activeId=="HistoryTorqueMaxValueTorqueCurvePanel_Id"){
                                        				showBalanceAnalysisCurveChart(HistoryTorqueMaxValueAnalysisGrid.getStore(),"HistoryTorqueMaxValueTorqueCurve1Div_Id","HistoryTorqueMaxValueTorqueCurve2Div_Id");
                                        			}
                                                    else{
                                        				showBalanceAnalysisMotionCurveChart(HistoryTorqueMaxValueAnalysisGrid.getStore(),"HistoryTorqueMaxValuePRAndTFPanel_Id","HistoryTorqueMaxValueMotionCurveGrid_Id","HistoryTorqueMaxValueMotionCurveDiv_Id");
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