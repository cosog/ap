Ext.require([
'Ext.grid.plugin.BufferedRenderer'
]);
Ext.define("AP.view.monitorOut.MonitorPumpingUnitPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monitorPumpingUnitPanel',
    id: "monitor_MonitorPumpingUnit_view_ids",
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        // 加载STORE
        var rMPUStore_ = Ext.create('AP.store.monitorOut.MonitorPumpingUnitStore');
        rMPUStore_.load();
        var org_Id = Ext.getCmp('leftOrg_Id');
        if (isNotVal(org_Id)) {
            org_Id = org_Id.getValue();
        }
        var jhhStore_A = new Ext.data.JsonStore({
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
                    var jhh_tobj = Ext.getCmp('MonitorPumpingUnitjhh_Id');
                    var jhh_val = "";
                    if (!Ext.isEmpty(jhh_tobj)) {
                    	jhh_val = jhh_tobj.getValue();
                    }
                    var new_params = {
                    	jhh:jhh_val,
                        type: 'jhh',
                        orgId: org_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        
        var jhStore_B = new Ext.data.JsonStore({
            pageSize:defaultJhComboxSize,
            fields: [{
                name: 'boxkey',
                type: 'string'
                }, {
                name: 'boxval',
                type: 'string'
                }],
            proxy: {
                url: context + '/monitorPumpingUnitParamsManagerController/queryMonitorPUJhh',
                type: 'ajax',
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
                    var jhh_tobj = Ext.getCmp('MonitorPumpingUnitjhh_Id');
                    var jhh_val = "";
                    if (!Ext.isEmpty(jhh_tobj)) {
                    	jhh_val = jhh_tobj.getValue();
                    }
                    var jh_tobj = Ext.getCmp('MonitorPumpingUnitjh_Id');
					var jh_val = "";
					if (!Ext.isEmpty(jh_tobj)) {
						jh_val = jh_tobj.getValue();
					}
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var new_params = {
                        jhh: jhh_val,
                        jh:jh_val,
                        type: 'jh',
                        orgId: org_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var bjlxStore_C = new Ext.data.JsonStore({
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
                    var jhh_tobj = Ext.getCmp('MonitorPumpingUnitjhh_Id');
                    var jhh_val = "";
                    if (!Ext.isEmpty(jhh_tobj)) {
                    	jhh_val = jhh_tobj.getValue();
                    }
                    var jh_tobj = Ext.getCmp('MonitorPumpingUnitjh_Id');
					var jh_val = "";
					if (!Ext.isEmpty(jh_tobj)) {
						jh_val = jh_tobj.getValue();
					}
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var new_params = {
                        jhh: jhh_val,
                        type: 'bjlx',
                        orgId: org_Id,
                        jh: jh_val
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        // Simple ComboBox using the data store
        var simpleCombo_A = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.jhh,
            id: "MonitorPumpingUnitjhh_Id",
            labelWidth: 45,
            width: 155,
            store: jhhStore_A,
            hidden: sfycjhh,
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
            pageSize:comboxPagingStatus,
            minChars:0,
            listeners: {
                expand: function (sm, selections) {
                    simpleCombo_A.clearValue();
                    simpleCombo_A.getStore().loadPage(1); // 加载井下拉框的store
                    simpleCombo_B.clearValue();
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'MonitorPumpingUnit_Id');
                },
                select: function (combo, record, index) {
                    try {
                        rMPUStore_.load();
                    } catch (ex) {
                        Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                    }
                }
            }
        });
        var simpleCombo_B = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.jh,
            id: "MonitorPumpingUnitjh_Id",
            labelWidth: 35,
            width:145,
            queryMode: 'remote',
            typeAhead: true,
            store: jhStore_B,
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
                    simpleCombo_B.clearValue();
                    simpleCombo_B.getStore().loadPage(1); // 加载井下拉框的store
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'MonitorPumpingUnit_Id');
                },
                select: function (combo, record, index) {
                    try {
                    	//alert(simpleCombo_B.getValue());
                        rMPUStore_.load();
                    } catch (ex) {
                        Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                    }
                }
            }
        });
        var simpleCombo_C = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.bjzt,
            id: 'MonitorPumpingUnitbjlx_Id',
            labelWidth: 55,
            width: 165,
            typeAhead: true,
            store: bjlxStore_C,
            editable: true,
            autoSelect: false,
            triggerAction: 'all',
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            displayField: "boxval",
            valueField: "boxkey",
            listeners: {
                expand: function (sm, selections) {
                    simpleCombo_C.clearValue();
                    simpleCombo_C.getStore().load(); // 加载井下拉框的store
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'MonitorPumpingUnit_Id');
                },
                select: function (combo, record, index) {
                    try {
                        rMPUStore_.load();
                    } catch (ex) {
                        Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                    }
                }
            }
        });
        Ext.apply(this, {
            tbar: [simpleCombo_A, simpleCombo_B, simpleCombo_C, {
                xtype: 'button',
                text: cosog.string.exportExcel,
                pressed: true,
                handler: function (v, o) {
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id');
                    var MonitorPumpingUnitjhh_Id = Ext.getCmp('MonitorPumpingUnitjhh_Id');
                    var MonitorPumpingUnitjh_Id = Ext.getCmp('MonitorPumpingUnitjh_Id');
                    var MonitorPumpingUnitbjlx_Id = Ext.getCmp('MonitorPumpingUnitbjlx_Id');
                    if (!Ext.isEmpty(leftOrg_Id)) {
                        leftOrg_Id = leftOrg_Id.getValue();
                    }
                    if (!Ext.isEmpty(MonitorPumpingUnitjhh_Id)) {
                        MonitorPumpingUnitjhh_Id = MonitorPumpingUnitjhh_Id.getValue();
                    }
                    if (!Ext.isEmpty(MonitorPumpingUnitjh_Id)) {
                        MonitorPumpingUnitjh_Id = MonitorPumpingUnitjh_Id.getValue();
                    }
                    if (!Ext.isEmpty(MonitorPumpingUnitbjlx_Id)) {
                        MonitorPumpingUnitbjlx_Id = MonitorPumpingUnitbjlx_Id.getValue();
                    }
                    var fields = "";
                    var heads = "";
                    var ProductionOut_panel = Ext.getCmp("MonitorPumpingUnit_Id");
                    var items_ = ProductionOut_panel.items.items;
                    if(items_.length==1){//无锁定列时
                    	var columns_ = ProductionOut_panel.columns;
                    	Ext.Array.each(columns_, function (name,
                                index, countriesItSelf) {
                                var locks = columns_[index];
                                if (index > 0 && locks.hidden == false) {
                                    fields += locks.dataIndex + ",";
                                    heads += locks.text + ",";
                                }
                            });
                    }else{
                    	Ext.Array.each(items_, function (name, index,
                                countriesItSelf) {
                                var datas = items_[index];
                                var columns_ = datas.columns;
                                if (index == 0) {
                                    Ext.Array.each(columns_, function (name,
                                        index, countriesItSelf) {
                                        var locks = columns_[index];
                                        if (index > 0 && locks.hidden == false) {
                                            fields += locks.dataIndex + ",";
                                            heads += locks.text + ",";
                                        }
                                    });
                                } else {
                                    Ext.Array.each(columns_, function (name,
                                        index, countriesItSelf) {
                                        var headers_ = columns_[index];
                                        if (headers_.hidden == false) {
                                            fields += headers_.dataIndex + ",";
                                            heads += headers_.text + ",";
                                        }
                                    });
                                }
                            });
                    }
                    if (isNotVal(fields)) {
                        fields = fields.substring(0, fields.length - 1);
                        heads = heads.substring(0, heads.length - 1);
                    }
                    fields = "id," + fields;
                    //alert(fields); 
                    var param = "&fields=" + fields + "&orgId=" + leftOrg_Id + "&jhh=" + URLencode(URLencode(MonitorPumpingUnitjhh_Id)) + "&jh=" + URLencode(URLencode(MonitorPumpingUnitjh_Id)) + "&bjlx=" + MonitorPumpingUnitbjlx_Id+"&fileName="+URLencode(URLencode(cosog.string.monitorExcel));
                    openExcelWindow(context + '/monitorDataController/exportExcelMonitorData?flag=true&heads=' + param);
                }
        },{
            xtype: 'button',
            hidden:true,
            text: '回存测试',
            pressed: true,
            handler: function (v, o) {
            	Ext.Ajax.request({
            		method:'POST',
            		url:context + '/calculateDataController/saveCalculateTest',
            		success:function(response) {
            			Ext.MessageBox.alert("信息","更新成功",function(){
            				
            			});
            		},
            		failure:function(){
            			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
            		},
            		params: {
                    }
            	});
            }
        }, '->', {
                id: 'MonitorPumpingUnitTotalCount_Id',
                xtype: 'component',
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
        }],
            listeners: {
//                afterrender: function (a, b) {
//                    Ext.TaskManager.start({
//                    	interval: 300000,
//                        run: function () {
//                            var tabPanel = Ext.getCmp("frame_center_ids");
//                            var activeId = tabPanel.getActiveTab().id;
//                            //alert("Monitor:"+activeId);
//                            //alert(activeId=="monitor_MonitorPumpingUnit");
//                            if (activeId == "monitor_MonitorPumpingUnit") {
//                                autoRefreshPanelView("MonitorPumpingUnit_Id");
//                            }
//                        }
//                    });
//                }
            }
        });
        me.callParent(arguments);
    }
});