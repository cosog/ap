Ext.define("AP.view.balanceCycle.CycleTorqueMaxValueView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.cycleTorqueMaxValueView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var TorqueMaxValueCycleBalanceStatusStore=Ext.create("AP.store.balanceCycle.TorqueMaxValueCycleBalanceStatusStore");
        var jhStore_A = new Ext.data.JsonStore({
            pageSize: defaultJhhComboxSize,
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
                    var jh_tobj = Ext.getCmp('TorqueMaxValueCyclejh_Id').getValue();
                    var new_params = {
                        jh: jh_tobj,
                        type: 'jh',
                        orgId: org_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var jhComboBox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.jh,
            id: "TorqueMaxValueCyclejh_Id",
            store: jhStore_A,
            labelWidth: 35,
            width: 145,
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
//                    jhComboBox.clearValue();
                    jhComboBox.getStore().load();
                },
                select: function (combo, record, index) {
                    Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id").getStore().load();
                }
            }
        });
        Ext.apply(me, {
            items: [
                {
                    layout: "border",
                    border: false,
                    items: [
                        {
                            region: 'west',
                            title: '平衡状态表',
                            layout: 'border',
                            border: false,
                            width: '30%',
                            collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            items: [{
                                region: 'north',
                                id: 'TorqueMaxValueCycleBalanceStatusPanel_Id',
                                layout: 'fit',
                                border: false,
                                height: '50%',
                                collapsible: false, // 是否折叠
                                split: true, // 竖折叠条
                                tbar:[jhComboBox,{
                                    id: 'TorqueMaxValueBalanceCycleSelectedGkmc_Id',
                                    xtype: 'textfield',
                                    value: '',
                                    hidden: true
                                },{
                                    xtype: 'button',
                                    text: cosog.string.exportExcel,
                                    pressed: true,
                                    handler: function (v, o) {
//                                    	exportCycleBalanceStatusExcelData();
                                    	var gridId="TorqueMaxValueCycleWellListGrid_Id";
                                    	var url=context + '/balanceCycleController/exportCycleBalanceStatusExcelData';
                                    	var fileName=cosog.string.bananceCycleExcel;
                                    	var title=cosog.string.bananceCycleExcel;
                                    	var jh = Ext.getCmp('TorqueMaxValueCyclejh_Id').getValue();
                                    	var gkmc=Ext.getCmp('TorqueMaxValueBalanceCycleSelectedGkmc_Id').getValue();
                                    	var type=2;
                                    	exportCycleBalanceStatusExcelData(jh,gkmc,gridId,type,fileName,title,url);
                                    }
                                },'->',{
                                    xtype: 'button',
                                    text: cosog.string.exportBalanceReport,
                                    pressed: true,
                                    handler: function (v, o) {
                                    	var jlbh=Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id").getSelectionModel().getSelection()[0].data.id;
                                    	var jh=Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id").getSelectionModel().getSelection()[0].data.jh;
                                    	if(jlbh>0){
                                    		var cycleBalanceCurve1 = $("#CycleTorqueMaxValueBalanceCurve1Div_Id").highcharts(); 
                                    		cycleBalanceCurveSVG1=cycleBalanceCurve1.getSVG();
//                                    		var svg2=cycleBalanceCurveSVG1.replace(/#/g,"%23");
                                    		var svg2=cycleBalanceCurveSVG1.replace(/</g,'\n&lt;').replace(/>/g,'&gt;').replace(/(\n)+|(\r\n)+/g,"").replace(/#/g,"%23");
                                            var url=context + '/balanceCycleController/exportWellCycleBalanceReport?jlbh='+jlbh+'&jh='+jh+'&fileName='+cosog.string.balanceCycleReportPdf+'&type=pdf&svg=' + svg2;
                                            document.location.href = url;
                                    	}
                                    }
                                }]
                            }, {
                            	region: 'center',
                            	xtype: 'tabpanel',
                            	id:'CycleStatTorqueMaxValueTabpanel_Id',
                            	activeTab: 0,
                            	border: true,
                            	tabPosition: 'top',
                            	items: [
                            		{
                            			title:'统计图',
                            			layout: 'fit',
                            			id:'CycleStatTorqueMaxValuePiePanel_Id',
                            			html:'<div id="CycleBalanceStatTorqueMaxValuePieDiv_Id" style="width:100%;height:100%;"></div>'
                            		}
                            	],
                            	listeners: {
                            		tabchange: function (tabPanel, newCard, oldCard,obj) {},
                            		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            			var TorqueMaxValueCycleWellListGrid = Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id");
                                   	 	if(isNotVal(TorqueMaxValueCycleWellListGrid)){
                                   	 		var tabPanel = Ext.getCmp("CycleStatTorqueMaxValueTabpanel_Id");
                                   	 		var activeId = tabPanel.getActiveTab().id;
                                   	 		if(activeId=="CycleStatTorqueMaxValuePiePanel_Id"){
                                   	 			Ext.create("AP.store.balanceCycle.CycleStatTorqueMaxValueStore");
                                   	 		}
                                   	 	}
                            		}
                            	}
                            }]
                    },{
                            region: 'center',
                            border: false,
                            layout: 'border',
                            items: [
//                            	{
//                                    region: 'west',
//                                    id: 'TorqueMaxValueCycleGtListPanel_Id',
//                                    border: false,
//                                    layout: 'fit',
//                                    width: '25%',
//                                    collapsible: true, // 是否折叠
//                                    split: true, // 竖折叠条
//                                    title: cosog.string.dynList
//                            },
                            {
                                    region: 'center',
                                    layout: 'border',
                                    border: false,
                                    tbar:[{
                                        xtype: 'textfield',
                                        id: "TorqueMaxValueCycleTextfield_id",
                                        anchor: '100%',
                                        labelWidth: 60,
                                        width: 160,
                                        fieldLabel: '评价周期',
                                        readOnly: false
                                    	},'-',{
                                        xtype: 'datefield',
                                        anchor: '100%',
                                        fieldLabel: cosog.string.startDate,
                                        labelWidth: 58,
                                        width: 178,
                                        format: 'Y-m-d ',
                                        id: 'TorqueMaxValueCycleStartDate_Id',
                                        value: 'new',
                                        listeners: {
                                        	select: function (combo, record, index) {
                                        		var startDate=Ext.getCmp("TorqueMaxValueCycleStartDate_Id").getValue();
                                        		var endDate=Ext.getCmp("TorqueMaxValueCycleEndDate_Id").getValue();
                                        		var dateSpan=Date.parse(endDate.replace(/-/g, '/'))-Date.parse(startDate.replace(/-/g, '/'));
                                        		if(dateSpan<0){
                                        			dateSpan=0;
                                        		}
                                        		iDays=Math.floor(dateSpan/(24*60*60*1000));
                                        		Ext.getCmp("TorqueMaxValueCycleTextfield_id").setValue(iDays);
                                        		TorqueMaxValueCycleBalanceInfoGrid1=Ext.getCmp("TorqueMaxValueCycleBalanceInfoGrid1_Id");
                                            	if (isNotVal(TorqueMaxValueCycleBalanceInfoGrid1)) {
                                            		TorqueMaxValueCycleBalanceInfoGrid1.getStore().load();
                                            	}else{
                                            		Ext.create("AP.store.balanceCycle.TorqueMaxValueCycleDataStore");
                                            	}
                                            }
                                        }
                                    },'-', {
                                        xtype: 'datefield',
                                        anchor: '100%',
                                        fieldLabel: cosog.string.endDate,
                                        labelWidth: 58,
                                        width: 178,
                                        format: 'Y-m-d',
                                        id: 'TorqueMaxValueCycleEndDate_Id',
                                        value: new Date(),
                                        listeners: {
                                        	select: function (combo, record, index) {
                                        		var startDate=Ext.getCmp("TorqueMaxValueCycleStartDate_Id").getValue();
                                        		var endDate=Ext.getCmp("TorqueMaxValueCycleEndDate_Id").getValue();
                                        		var dateSpan=Date.parse(endDate.replace(/-/g, '/'))-Date.parse(startDate.replace(/-/g, '/'));
                                        		if(dateSpan<0){
                                        			dateSpan=0;
                                        		}
                                        		iDays=Math.floor(dateSpan/(24*60*60*1000));
                                        		Ext.getCmp("TorqueMaxValueCycleTextfield_id").setValue(iDays);
                                        		TorqueMaxValueCycleBalanceInfoGrid1=Ext.getCmp("TorqueMaxValueCycleBalanceInfoGrid1_Id");
                                            	if (isNotVal(TorqueMaxValueCycleBalanceInfoGrid1)) {
                                            		TorqueMaxValueCycleBalanceInfoGrid1.getStore().load();
                                            	}else{
                                            		Ext.create("AP.store.balanceCycle.TorqueMaxValueCycleDataStore");
                                            	}
                                            }
                                        }
                                    },'-',{
                                        xtype: 'button',
                                        text: '保存周期设置',
                                        pressed: true,
                                        handler: function (v, o) {
                                        	var jh = "";
                                        	var TorqueMaxValueCycleWellListGrid = Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id");
                                        	if(isNotVal(TorqueMaxValueCycleWellListGrid)){
                                        		var record=TorqueMaxValueCycleWellListGrid.getSelectionModel().getSelection();
                                        		if(record.length>0){
                                        			jh=record[0].data.jh;
                                        		}
                                        	}
                                        	var productionCycle=Ext.getCmp("TorqueMaxValueCycleTextfield_id").getValue();
                                        	Ext.Ajax.request({
                                	    		method:'POST',
                                	    		url:context + '/balanceCycleController/setWellProductionCycle',
                                	    		success:function(response) {
//                                	    			Ext.MessageBox.alert("信息","设置成功",function(){
//                                	    				
//                                	    			});
                                	    			Ext.Ajax.request({
                                        	    		method:'POST',
                                        	    		url:context + '/balanceDataAInterfaceController/balanceCycleCalculation',
                                        	    		success:function(response) {
                                        	    			var TorqueMaxValueCycleWellListGrid_Id= Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id");
                                        	    			if(isNotVal(TorqueMaxValueCycleWellListGrid_Id)){
                                        	    				TorqueMaxValueCycleWellListGrid_Id.getStore().load();
                                        	    			}
                                        	    		},
                                        	    		failure:function(){
                                        	    		},
                                        	    		params: {
                                        	    			jh: jh
                                        	            }
                                        	    	});  
                                	    		},
                                	    		failure:function(){
                                	    			Ext.MessageBox.alert("错误","命令发送失败");
                                	    		},
                                	    		params: {
                                	    			jh: jh,
                                	    			productionCycle:productionCycle
                                	            }
                                	    	});  
                                        }
                                    }],
                                    items: [{
                                            region: 'west',
                                            border: true,
                                            layout: 'border',
                                            width: '40%',
                                            collapsible: false, // 是否折叠
                                            split: false, // 竖折叠条
                                            items: [{
                                                title:'目前平衡块信息',
                                            	region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id: 'CycleTorqueMaxValueBalanceInfoPanel1_Id'
                                            }, {
                                            	title:'预期平衡块信息',
                                            	region: 'center',
                                                layout: 'fit',
                                                id: 'CycleTorqueMaxValueBalanceInfoPanel2_Id'
                                            }]
                                            
                                    },{
                                            region: 'center',
                                            border: true,
                                            layout: 'border',
                                            items: [{
                                                region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id: 'CycleTorqueMaxValueBalanceCurve1Panel_Id',
                                                html: '<div id="CycleTorqueMaxValueBalanceCurve1Div_Id" style="width:100%;height:100%;"></div>',
                                                listeners: {
                                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    	var GridPanel = Ext.getCmp("TorqueMaxValueCycleBalanceInfoGrid1_Id");
                                                    	if (isNotVal(GridPanel)) {
                                                    		$("#CycleTorqueMaxValueBalanceCurve1Div_Id").highcharts().setSize($("#CycleTorqueMaxValueBalanceCurve1Div_Id").offsetWidth, $("#CycleTorqueMaxValueBalanceCurve1Div_Id").offsetHeight,true);
                                                    	}
                                                    }
                                                }
                                            }, {
                                                region: 'center',
                                                layout: 'fit',
                                                id: 'CycleTorqueMaxValueBalanceCurve2Panel_Id',
                                                html: '<div id="CycleTorqueMaxValueBalanceCurve2Div_Id" style="width:100%;height:100%;"></div>',
                                                listeners: {
                                                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                		var GridPanel = Ext.getCmp("TorqueMaxValueCycleBalanceInfoGrid1_Id");
                                                    	if (isNotVal(GridPanel)) {
                                                    		$("#CycleTorqueMaxValueBalanceCurve2Div_Id").highcharts().setSize($("#CycleTorqueMaxValueBalanceCurve2Div_Id").offsetWidth, $("#CycleTorqueMaxValueBalanceCurve2Div_Id").offsetHeight,true);
                                                    	}
                                                    }
                                                }
                                                
                                            }]
                                        }]
                        }]
                    }]
                }]
        });
        me.callParent(arguments);
    }

});
