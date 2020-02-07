Ext.define("AP.view.balanceCycle.CycleTorqueAveragePowerView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.cycleTorqueAveragePowerView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var TorqueAveragePowerCycleBalanceStatusStore=Ext.create("AP.store.balanceCycle.TorqueAveragePowerCycleBalanceStatusStore");
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
                    var jh_tobj = Ext.getCmp('TorqueAveragePowerCyclejh_Id').getValue();
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
            id: "TorqueAveragePowerCyclejh_Id",
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
                    Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id").getStore().load();
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
                                id: 'TorqueAveragePowerCycleBalanceStatusPanel_Id',
                                layout: 'fit',
                                border: false,
                                height: '50%',
                                collapsible: false, // 是否折叠
                                split: true, // 竖折叠条
                                tbar:[jhComboBox,{
                                    id: 'TorqueAveragePowerBalanceCycleSelectedGkmc_Id',
                                    xtype: 'textfield',
                                    value: '',
                                    hidden: true
                                },{
                                    xtype: 'button',
                                    text: cosog.string.exportExcel,
                                    pressed: true,
                                    handler: function (v, o) {
                                    	var gridId="TorqueAveragePowerCycleWellListGrid_Id";
                                    	var url=context + '/balanceCycleController/exportCycleBalanceStatusExcelData';
                                    	var fileName=cosog.string.bananceCycleExcel;
                                    	var title=cosog.string.bananceCycleExcel;
                                    	var jh = Ext.getCmp('TorqueAveragePowerCyclejh_Id').getValue();
                                    	var gkmc=Ext.getCmp('TorqueAveragePowerBalanceCycleSelectedGkmc_Id').getValue();
                                    	var type=3;
                                    	exportCycleBalanceStatusExcelData(jh,gkmc,gridId,type,fileName,title,url);
                                    }
                                },'->',{
                                    xtype: 'button',
                                    text: cosog.string.exportBalanceReport,
                                    pressed: true,
                                    handler: function (v, o) {
                                    	var jlbh=Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id").getSelectionModel().getSelection()[0].data.id;
                                    	var jh=Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id").getSelectionModel().getSelection()[0].data.jh;
                                    	if(jlbh>0){
                                    		var cycleBalanceCurve1 = $("#CycleTorqueAveragePowerBalanceCurve1Div_Id").highcharts(); 
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
                            	id:'CycleStatTorqueAveragePowerTabpanel_Id',
                            	activeTab: 0,
                            	border: true,
                            	tabPosition: 'top',
                            	items: [
                            		{
                            			title:'统计图',
                            			layout: 'fit',
                            			id:'CycleStatTorqueAveragePowerPiePanel_Id',
                            			html:'<div id="CycleBalanceStatTorqueAveragePowerPieDiv_Id" style="width:100%;height:100%;"></div>'
                            		}
                            	],
                            	listeners: {
                            		tabchange: function (tabPanel, newCard, oldCard,obj) {},
                            		resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            			var TorqueAveragePowerCycleWellListGrid = Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id");
                                   	 	if(isNotVal(TorqueAveragePowerCycleWellListGrid)){
                                   	 		var tabPanel = Ext.getCmp("CycleStatTorqueAveragePowerTabpanel_Id");
                                   	 		var activeId = tabPanel.getActiveTab().id;
                                   	 		if(activeId=="CycleStatTorqueAveragePowerPiePanel_Id"){
                                   	 			Ext.create("AP.store.balanceCycle.CycleStatTorqueAveragePowerStore");
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
//                                    id: 'TorqueAveragePowerCycleGtListPanel_Id',
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
                                        id: "TorqueAveragePowerCycleTextfield_id",
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
                                        id: 'TorqueAveragePowerCycleStartDate_Id',
                                        value: 'new',
                                        listeners: {
                                        	select: function (combo, record, index) {
                                        		var startDate=Ext.getCmp("TorqueAveragePowerCycleStartDate_Id").getValue();
                                        		var endDate=Ext.getCmp("TorqueAveragePowerCycleEndDate_Id").getValue();
                                        		var dateSpan=Date.parse(endDate.replace(/-/g, '/'))-Date.parse(startDate.replace(/-/g, '/'));
                                        		if(dateSpan<0){
                                        			dateSpan=0;
                                        		}
                                        		iDays=Math.floor(dateSpan/(24*60*60*1000));
                                        		Ext.getCmp("TorqueAveragePowerCycleTextfield_id").setValue(iDays);
                                        		TorqueAveragePowerCycleBalanceInfoGrid1=Ext.getCmp("TorqueAveragePowerCycleBalanceInfoGrid1_Id");
                                            	if (isNotVal(TorqueAveragePowerCycleBalanceInfoGrid1)) {
                                            		TorqueAveragePowerCycleBalanceInfoGrid1.getStore().load();
                                            	}else{
                                            		Ext.create("AP.store.balanceCycle.TorqueAveragePowerCycleDataStore");
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
                                        id: 'TorqueAveragePowerCycleEndDate_Id',
                                        value: new Date(),
                                        listeners: {
                                        	select: function (combo, record, index) {
                                        		var startDate=Ext.getCmp("TorqueAveragePowerCycleStartDate_Id").getValue();
                                        		var endDate=Ext.getCmp("TorqueAveragePowerCycleEndDate_Id").getValue();
                                        		var dateSpan=Date.parse(endDate.replace(/-/g, '/'))-Date.parse(startDate.replace(/-/g, '/'));
                                        		if(dateSpan<0){
                                        			dateSpan=0;
                                        		}
                                        		iDays=Math.floor(dateSpan/(24*60*60*1000));
                                        		Ext.getCmp("TorqueAveragePowerCycleTextfield_id").setValue(iDays);
                                        		TorqueAveragePowerCycleBalanceInfoGrid1=Ext.getCmp("TorqueAveragePowerCycleBalanceInfoGrid1_Id");
                                            	if (isNotVal(TorqueAveragePowerCycleBalanceInfoGrid1)) {
                                            		TorqueAveragePowerCycleBalanceInfoGrid1.getStore().load();
                                            	}else{
                                            		Ext.create("AP.store.balanceCycle.TorqueAveragePowerCycleDataStore");
                                            	}
                                            }
                                        }
                                    },'-',{
                                        xtype: 'button',
                                        text: '保存周期设置',
                                        pressed: true,
                                        handler: function (v, o) {
                                        	var jh = "";
                                        	var TorqueAveragePowerCycleWellListGrid = Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id");
                                        	if(isNotVal(TorqueAveragePowerCycleWellListGrid)){
                                        		var record=TorqueAveragePowerCycleWellListGrid.getSelectionModel().getSelection();
                                        		if(record.length>0){
                                        			jh=record[0].data.jh;
                                        		}
                                        	}
                                        	var productionCycle=Ext.getCmp("TorqueAveragePowerCycleTextfield_id").getValue();
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
                                        	    			var TorqueAveragePowerCycleWellListGrid_Id= Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id");
                                        	    			if(isNotVal(TorqueAveragePowerCycleWellListGrid_Id)){
                                        	    				TorqueAveragePowerCycleWellListGrid_Id.getStore().load();
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
                                                id: 'CycleTorqueAveragePowerBalanceInfoPanel1_Id'
                                            }, {
                                            	title:'预期平衡块信息',
                                            	region: 'center',
                                                layout: 'fit',
                                                id: 'CycleTorqueAveragePowerBalanceInfoPanel2_Id'
                                            }]
                                            
                                    },{
                                            region: 'center',
                                            border: true,
                                            layout: 'border',
                                            items: [{
                                                region: 'north',
                                                layout: 'fit',
                                                height: '50%',
                                                id: 'CycleTorqueAveragePowerBalanceCurve1Panel_Id',
                                                html: '<div id="CycleTorqueAveragePowerBalanceCurve1Div_Id" style="width:100%;height:100%;"></div>',
                                                listeners: {
                                                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                    	var GridPanel = Ext.getCmp("TorqueAveragePowerCycleBalanceInfoGrid1_Id");
                                                    	if (isNotVal(GridPanel)) {
                                                    		$("#CycleTorqueAveragePowerBalanceCurve1Div_Id").highcharts().setSize($("#CycleTorqueAveragePowerBalanceCurve1Div_Id").offsetWidth, $("#CycleTorqueAveragePowerBalanceCurve1Div_Id").offsetHeight,true);
                                                    	}
                                                    }
                                                }
                                            }, {
                                                region: 'center',
                                                layout: 'fit',
                                                id: 'CycleTorqueAveragePowerBalanceCurve2Panel_Id',
                                                html: '<div id="CycleTorqueAveragePowerBalanceCurve2Div_Id" style="width:100%;height:100%;"></div>',
                                                listeners: {
                                                	resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                                		var GridPanel = Ext.getCmp("TorqueAveragePowerCycleBalanceInfoGrid1_Id");
                                                    	if (isNotVal(GridPanel)) {
                                                    		$("#CycleTorqueAveragePowerBalanceCurve2Div_Id").highcharts().setSize($("#CycleTorqueAveragePowerBalanceCurve2Div_Id").offsetWidth, $("#CycleTorqueAveragePowerBalanceCurve2Div_Id").offsetHeight,true);
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
