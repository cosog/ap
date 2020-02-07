Ext.define('AP.view.balanceInfo.BalanceInformationPanel', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.balanceInformationPanel',
    id: 'BalanceInformationPanel_Id',
    layout: "border",
    border: false,
    initComponent: function () {
        var me = this;
        var pumpingunitStore = Ext.create('AP.store.balanceInfo.BalanceInformationStore');
        var sccjStore_A = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/balanceInformationController/queryBalanceInformationParams',
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
            		var sccj = Ext.getCmp('balancePumpingunitSccj_Id').getValue();
            		var new_params = {
            				sccj:sccj,
            				type:'sccj'
                     };
                     Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var formSccjStore_A = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/balanceInformationController/queryBalanceSetFormParams',
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
            		var new_params = {
            				type:'sccj'
                     };
                     Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var cybxhStore_B = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/balanceInformationController/queryBalanceInformationParams',
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
            		var sccj = Ext.getCmp('balancePumpingunitSccj_Id').getValue();
            		var cyjxh = Ext.getCmp('balancePumpingunitCyjxh_Id').getValue();
            		var new_params = {
            				sccj: sccj,
            				cyjxh:cyjxh,
            				type: 'cyjxh'
            		};
            		Ext.apply(store.proxy.extraParams, new_params);
            	}
            }
        });
        
        var formCybxhStore_B = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/balanceInformationController/queryBalanceSetFormParams',
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
            		var sccj = Ext.getCmp('balanceInfoSetSccj_Id').getValue();
            		var new_params = {
            				sccj: sccj,
            				type: 'cyjxh'
            		};
            		Ext.apply(store.proxy.extraParams, new_params);
            	}
            }
        });
        
        var formKtztStore_B = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/balanceInformationController/queryBalanceSetFormParams',
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
            		var sccj = Ext.getCmp('balanceInfoSetSccj_Id').getValue();
            		var new_params = {
            				type: 'ktzt'
            		};
            		Ext.apply(store.proxy.extraParams, new_params);
            	}
            }
        });
        
        var phkzlStore = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/balanceInformationController/queryBalanceSetFormParams',
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
            		var sccj = Ext.getCmp('balanceInfoSetSccj_Id').getValue();
            		var cyjxh = Ext.getCmp('balanceInfoSetCyjxh_Id').getValue();
            		var new_params = {
            				sccj: sccj,
            				cyjxh:cyjxh,
            				type: 'phkzl'
            		};
            		Ext.apply(store.proxy.extraParams, new_params);
            	}
            }
        });
        
        var jhstore = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/balanceInformationController/queryBalanceInformationParams',
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
            		var sccj = Ext.getCmp('balancePumpingunitSccj_Id').getValue();
            		var cyjxh = Ext.getCmp('balancePumpingunitCyjxh_Id').getValue();
            		var jh = Ext.getCmp('balancePumpingunitJh_Id').getValue();
            		var new_params = {
            				sccj: sccj,
            				cyjxh:cyjxh,
            				jh:jh,
            				type: 'jh'
            		};
            		Ext.apply(store.proxy.extraParams, new_params);
            	}
            }
        });
        var simpleCombo_A = Ext.create('Ext.form.field.ComboBox', {
        	fieldLabel: cosog.string.sccj,
        	id: "balancePumpingunitSccj_Id",
        	labelWidth: 60,
            width: 180,
        	store: sccjStore_A,
        	queryMode: 'remote',
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            typeAhead: true,
            autoSelect: false,
//            allowBlank: true,
            triggerAction: 'all',
            editable: true,
            displayField: "boxval",
            valueField: "boxkey",
            minChars:0,
            listeners: {
            	expand: function (sm, selections) {
            		simpleCombo_A.clearValue();
            		simpleCombo_A.getStore().load(); //加载井下拉框的store
            		simpleCombo_B.clearValue();
            		simpleCombo_JH.clearValue();
                },
                specialkey: function (field, e) {
                },
                select: function (combo, record, index) {
                	Ext.getCmp("balanceInformationGrid_Id").getStore().load();
                }
            }
        });
        var simpleCombo_B = Ext.create('Ext.form.field.ComboBox', {
        	fieldLabel: cosog.string.cyjxh,
            id: "balancePumpingunitCyjxh_Id",
            labelWidth: 70,
            width: 220,
            queryMode: 'remote',
            typeAhead: true,
            store: cybxhStore_B,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            minChars:0,
            listeners: {
                expand: function (sm, selections) {
                	simpleCombo_B.clearValue();
                	simpleCombo_JH.clearValue();
                    simpleCombo_B.getStore().load(); //加载井下拉框的store
                },
                specialkey: function (field, e) {
                },
                select: function (combo, record, index) {
                	Ext.getCmp("balanceInformationGrid_Id").getStore().load();
                }
            }
        });
        
        var simpleCombo_JH = Ext.create('Ext.form.field.ComboBox', {
        	fieldLabel: cosog.string.jh,
            id: "balancePumpingunitJh_Id",
            labelWidth: 30,
            width: 150,
            queryMode: 'remote',
            typeAhead: true,
            store: jhstore,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            minChars:0,
            listeners: {
                expand: function (sm, selections) {
                	simpleCombo_JH.clearValue();
                	simpleCombo_JH.getStore().load(); //加载井下拉框的store
                },
                specialkey: function (field, e) {
                },
                select: function (combo, record, index) {
                	Ext.getCmp("balanceInformationGrid_Id").getStore().load();
                }
            }
        });
            Ext.apply(me, {
                tbar: [simpleCombo_A, simpleCombo_B,simpleCombo_JH, {
                        xtype: 'button',
                        name: 'balanceInformationQueryBtn_Id',
                        text: '查询',
                        pressed: true,
                        text_align: 'center',
                        // width : 50,
                        iconCls: 'search',
                        handler: function (v, o) {
                            Ext.getCmp("balanceInformationGrid_Id").getStore().load();
                        }
                },{
                	xtype: 'textfield',
                    id: 'balanceInformationSelectedJh_Id',
                    hidden:true
                }, '->',{
                        xtype: 'button',
                        itemId: 'delBalanceInformationBtnId',
                        id: 'delBalanceInformationBtn_Id',
                        disabled: false,
                        hidden:true,
                        action: 'delBalanceInformationAction',
                        text: cosog.string.del,
                        iconCls: 'delete'
                }, "-",{
                    xtype: 'button',
                    itemId: 'savedelBalanceInformationDataBtnId',
                    id: 'savedelBalanceInformationDataBtn_Id',
                    disabled: false,
                    hidden:false,
                    action: 'savedelBalanceInformationDataAction',
                    text: cosog.string.save,
                    iconCls: 'save'
                }],
                items:[{
                	title:'平衡信息表',
                	region: 'center',
                	layout:'fit',
                	id:"BalanceInformationTablePanel_Id"
                },{
                	region:'east',
                	title:'详细信息',
                	width:'25%',
                	autoScroll: true,
                	collapsible: true, // 是否折叠
                    split: true, // 竖折叠条
                    layout:'fit',
                    id:"",
                    items:[{
                    	xtype:'form',
                        baseCls: 'x-plain',
                        bodyStyle: 'padding:5px 5px 0',
                        fieldDefaults: {
                            labelAlign: 'left',
                            msgTarget: 'side'
                        },
                        defaults: {
                            border: true,
                            baseCls: 'x-plain',
                            //xtype: 'panel',
                            flex: 1,
                            defaultType: 'textfield',
                            layout: 'anchor'
                        },
                        layout: 'hbox',
                        items: [
                                {
                                    items: [
                                        {
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetSccj_Id',
                                        	fieldLabel: '抽油机厂家',
                        					typeAhead:true,
                        					store:formSccjStore_A,
                        					anchor: '95%',
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:false,
                        					editable:false,
                        					listeners: {
                        		                change: function (combo , newValue , oldValue , eOpts) {
//                        		                	Ext.getCmp("balanceInfoSetCyjxh_Id").clearValue();
//                        		                	updateCellValue("cyjxh","");
                        		                },
                        		                select: function (combo, record, index) {
                        		                	updateCellValue("sccj",combo.value);
                        		                }
                        		            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetCyjxh_Id',
                                        	fieldLabel: '抽油机型号',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:formCybxhStore_B,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:false,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetCyjxh_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	updateCellValue("cyjxh",combo.value);
                        		                }
                        		            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetKtzt_Id',
                                        	fieldLabel: '抽油机状态',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:formKtztStore_B,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:false,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetKtzt_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	updateCellValue("ktzt",combo.value);
                        		                }
                        		            }
                                        },{
                                        	fieldLabel:'调平衡时间',
                                        	anchor: '95%',
                                        	xtype:'datetimefield',
                                        	reference:'dateTimeText',
                                        	format:"Y-m-d H:i:s",
                                        	id:'balanceInfoSetOperateTime_Id',
                                        	listeners: {
                                            	select: function (dateTimeText, record, index) {
                                            		updateCellValue("gxsj",dateTimeText.rawValue);
                                                }
                                            }
                                        },{
                                        	fieldLabel: '平衡块位置1(cm)',
                                        	anchor: '95%',
                                        	id:'balanceInfoSetPhkwz1_Id',
                                        	listeners: {
                                                blur: function (text ,event, eOpts) {
                                                	getAndUpdatePhkwzzlCellValue();
                                                }
                                            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetPhkzl1_Id',
                                        	fieldLabel: '平衡块重量1(kN)',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:phkzlStore,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:true,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetPhkzl1_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	getAndUpdatePhkwzzlCellValue();
                        		                }
                        		            }
                                        },{
                                        	fieldLabel: '平衡块位置2(cm)',
                                        	anchor: '95%',
                                        	id:'balanceInfoSetPhkwz2_Id',
                                        	listeners: {
                                                blur: function (text ,event, eOpts) {
                                                	getAndUpdatePhkwzzlCellValue();
                                                }
                                            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetPhkzl2_Id',
                                        	fieldLabel: '平衡块重量2(kN)',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:phkzlStore,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:true,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetPhkzl2_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	getAndUpdatePhkwzzlCellValue();
                        		                }
                        		            }
                                        },{
                                        	fieldLabel: '平衡块位置3(cm)',
                                        	anchor: '95%',
                                        	id:'balanceInfoSetPhkwz3_Id',
                                        	listeners: {
                                                blur: function (text ,event, eOpts) {
                                                	getAndUpdatePhkwzzlCellValue();
                                                }
                                            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetPhkzl3_Id',
                                        	fieldLabel: '平衡块重量3(kN)',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:phkzlStore,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:true,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetPhkzl3_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	getAndUpdatePhkwzzlCellValue();
                        		                }
                        		            }
                                        },{
                                        	fieldLabel: '平衡块位置4(cm)',
                                        	anchor: '95%',
                                        	id:'balanceInfoSetPhkwz4_Id',
                                        	listeners: {
                                                blur: function (text ,event, eOpts) {
                                                	getAndUpdatePhkwzzlCellValue();
                                                }
                                            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetPhkzl4_Id',
                                        	fieldLabel: '平衡块重量4(kN)',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:phkzlStore,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:true,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetPhkzl4_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	getAndUpdatePhkwzzlCellValue();
                        		                }
                        		            }
                                        },{
                                        	fieldLabel: '平衡块位置5(cm)',
                                        	anchor: '95%',
                                        	id:'balanceInfoSetPhkwz5_Id',
                                        	listeners: {
                                                blur: function (text ,event, eOpts) {
                                                	getAndUpdatePhkwzzlCellValue();
                                                }
                                            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetPhkzl5_Id',
                                        	fieldLabel: '平衡块重量5(kN)',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:phkzlStore,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:true,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetPhkzl5_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	getAndUpdatePhkwzzlCellValue();
                        		                }
                        		            }
                                        },{
                                        	fieldLabel: '平衡块位置6(cm)',
                                        	anchor: '95%',
                                        	id:'balanceInfoSetPhkwz6_Id',
                                        	listeners: {
                                        		blur: function (text ,event, eOpts) {
                                                	getAndUpdatePhkwzzlCellValue();
                                                }
                                            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetPhkzl6_Id',
                                        	fieldLabel: '平衡块重量6(kN)',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:phkzlStore,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:true,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetPhkzl6_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	getAndUpdatePhkwzzlCellValue();
                        		                }
                        		            }
                                        },{
                                        	fieldLabel: '平衡块位置7(cm)',
                                        	anchor: '95%',
                                        	id:'balanceInfoSetPhkwz7_Id',
                                        	listeners: {
                                        		blur: function (text ,event, eOpts) {
                                                	getAndUpdatePhkwzzlCellValue();
                                                }
                                            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetPhkzl7_Id',
                                        	fieldLabel: '平衡块重量7(kN)',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:phkzlStore,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:true,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetPhkzl7_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	getAndUpdatePhkwzzlCellValue();
                        		                }
                        		            }
                                        },{
                                        	fieldLabel: '平衡块位置8(cm)',
                                        	anchor: '95%',
                                        	id:'balanceInfoSetPhkwz8_Id',
                                        	listeners: {
                                                blur: function (text ,event, eOpts) {
                                                	getAndUpdatePhkwzzlCellValue();
                                                }
                                            }
                                        },{
                                        	xtype:'combobox',
                                        	id:'balanceInfoSetPhkzl8_Id',
                                        	fieldLabel: '平衡块重量8(kN)',
                                        	anchor: '95%',
                        					typeAhead:true,
                        					triggerAction:'all',
                        					displayField: "boxval",
                        		            valueField: "boxkey",
                        					store:phkzlStore,
                        					editable:false,
                        					forceSelection :true,
                        					selectOnFocus:true,
                        					allowBlank:true,
                        					listeners: {
                        		                expand: function (sm, selections) {
                        		                	Ext.getCmp("balanceInfoSetPhkzl8_Id").getStore().load(); //加载井下拉框的store
                        		                },
                        		                select: function (combo, record, index) {
                        		                	getAndUpdatePhkwzzlCellValue();
                        		                }
                        		            }
                                        }]
                             }]
                    }]
                }]
            });
            this.callParent(arguments)
        
    }
});