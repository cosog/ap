Ext.define('AP.view.device.PumpingunitPanel', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.pumpingunitPanel',
    id: 'PumpingunitPanel_Id',
    layout: "fit",
    border: false,
    initComponent: function () {
        var me = this;
        var pumpingunitStore = Ext.create('AP.store.device.PumpingunitStore');
        var sccjStore_A = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/pumpingunitController/queryPumpUnitParams',
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
            		var sccj = Ext.getCmp('pumpingunitPanel_sccj_Id').getValue();
            		var new_params = {
            				sccj:sccj,
            				type:'sccj'
                     };
                     Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var cybxhStore_B = new Ext.data.SimpleStore({
        	fields: [{name: "boxkey",type: "string"},{name: "boxval",type: "string"}],
            proxy: {
            	url: context + '/pumpingunitController/queryPumpUnitParams',
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
            		var sccj = Ext.getCmp('pumpingunitPanel_sccj_Id').getValue();
            		var cyjxh = Ext.getCmp('pumpingunitPanel_cyjxh_Id').getValue();
            		var new_params = {
            				sccj: sccj,
            				cyjxh:cyjxh,
            				type: 'cyjxh'
            		};
            		Ext.apply(store.proxy.extraParams, new_params);
            	}
            }
        });
        var simpleCombo_A = Ext.create('Ext.form.field.ComboBox', {
        	fieldLabel: cosog.string.sccj,
        	id: "pumpingunitPanel_sccj_Id",
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
                },
                specialkey: function (field, e) {
                },
                select: function (combo, record, index) {
                	Ext.getCmp("pumpingunitGrid_Id").getStore().load();
                }
            }
        });
        var simpleCombo_B = Ext.create('Ext.form.field.ComboBox', {
        	fieldLabel: cosog.string.cyjxh,
            id: "pumpingunitPanel_cyjxh_Id",
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
                    simpleCombo_B.getStore().load(); //加载井下拉框的store
                },
                specialkey: function (field, e) {
                },
                select: function (combo, record, index) {
                	Ext.getCmp("pumpingunitGrid_Id").getStore().load();
                }
            }
        });
            Ext.apply(me, {
                tbar: [simpleCombo_A, simpleCombo_B, {
                        xtype: 'button',
                        name: 'pumpingunitPanelJh_Btn_Id',
                        text: '查询',
                        pressed: true,
                        text_align: 'center',
                        // width : 50,
                        iconCls: 'search',
                        handler: function (v, o) {
                            Ext.getCmp("pumpingunitGrid_Id").getStore().load();
                        }
                }, '->', {
                        xtype: 'button',
                        itemId: 'addPumpingunitPanelLabelClassBtnId',
                        id: 'addPumpingunitPanelLabelClassBtn_Id',
                        action: 'addPumpingunitPanelAction',
                        hidden:true,
                        text: "创建",
                        iconCls: 'add'
                }, "-", {
                        xtype: 'button',
                        itemId: 'editPumpingunitPanelLabelClassBtnId',
                        id: 'editPumpingunitPanelLabelClassBtn_Id',
                        text: "修改",
                        action: 'editPumpingunitPanelAction',
                        disabled: true,
                        hidden:true,
                        iconCls: 'edit'
                }, "-", {
                        xtype: 'button',
                        itemId: 'delPumpingunitPanelLabelClassBtnId',
                        id: 'delPumpingunitPanelLabelClassBtn_Id',
                        disabled: false,
                        action: 'delPumpingunitPanelAction',
                        text: "删除",
                        iconCls: 'delete'
                }, "-",{
                    xtype: 'button',
                    itemId: 'savePumpingUnitGridDataBtnId',
                    id: 'savePumpingUnitGridDataBtn_Id',
                    disabled: false,
                    hidden:false,
                    action: 'savePumpingUnitGridDataAction',
                    text: cosog.string.save,
                    iconCls: 'save'
                }]

            });
            this.callParent(arguments)
        
    }
});