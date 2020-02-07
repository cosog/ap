Ext.define('AP.view.device.PumpPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.pumpPanel',
    id: 'pumpPanelView_Id',
    layout: "fit",
    border: false,
    initComponent: function () {
        var me = this;
        var pumpStore = Ext.create('AP.store.device.PumpStore');
        var sccjStore_A = new Ext.data.JsonStore({
        	pageSize:defaultComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/pumpController/queryPumpParams',
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
                	var sccj_tobj = Ext.getCmp('pumpPanel_sccj_Id');
					var sccj_val = "";
					if (!Ext.isEmpty(sccj_tobj)) {
						sccj_val = sccj_tobj.getValue();
					}
                    var new_params = {
                    	sccj:sccj_val,
                        type: 'sccj'
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var cybxhStore_B = new Ext.data.JsonStore({
        	pageSize:defaultComboxSize,
            fields: [
                {
                    name: "boxkey",
                    type: "string"
                },
                {
                    name: "boxval",
                    type: "string"
                }],
            proxy: {
                url: context + '/pumpController/queryPumpParams',
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
                	var sccj_tobj = Ext.getCmp('pumpPanel_sccj_Id');
					var sccj_val = "";
					if (!Ext.isEmpty(sccj_tobj)) {
						sccj_val = sccj_tobj.getValue();
					}
					var cybxh_tobj = Ext.getCmp('pumpPanel_cybxh_Id');
					var cybxh_val = "";
					if (!Ext.isEmpty(cybxh_tobj)) {
						cybxh_val = cybxh_tobj.getValue();
					}
                    var new_params = {
                        sccj: sccj_val,
                        cybxh:cybxh_val,
                        type: 'cybxh'

                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var simpleCombo_A = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.sccj,
            id: "pumpPanel_sccj_Id",
            labelWidth: 60,
            width: 180,
            store: sccjStore_A,
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
//            pageSize:100,
            minChars:0,
            listeners: {
                expand: function (sm, selections) {
                    simpleCombo_A.clearValue();
                    simpleCombo_A.getStore().loadPage(1); //加载井下拉框的store    
                    simpleCombo_B.clearValue();
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'singlewellPanel_Id');
                },
                select: function (combo, record, index) {
                    try {
                    	pumpStore.load();
                    } catch (ex) {
                        Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                    }
                }
            }
        });
        var simpleCombo_B = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.cybxh,
            id: "pumpPanel_cybxh_Id",
            labelWidth: 70,
            width: 190,
            queryMode: 'remote',
            typeAhead: true,
            store: cybxhStore_B,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            displayField: "boxval",
            valueField: "boxkey",
//            pageSize:100,
            minChars:0,
            listeners: {
                expand: function (sm, selections) {
                    simpleCombo_B.clearValue();
                    simpleCombo_B.getStore().loadPage(1); //加载井下拉框的store    
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'singlewellPanel_Id');
                },
                select: function (combo, record, index) {
                    try {
                    	pumpStore.load();
                    } catch (ex) {
                        Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                    }
                }
            }
        });

        Ext.apply(me, {
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [simpleCombo_A, simpleCombo_B, {
                    xtype: 'button',
                    name: 'pumpPanelJh_Btn_Id',
                    text: cosog.string.search,
//                    hidden: true,
                    pressed: true,
                    text_align: 'center',
                    // width : 50,
                    iconCls: 'search',
                    handler: function (v, o) {
                        pumpStore.load();
                    }
      }, '->', {
                    xtype: 'button',
                    itemId: 'addpumpPanelLabelClassBtnId',
                    id: 'addpumpPanelLabelClassBtn_Id',
                    action: 'addpumpPanelAction',
                    hidden:true,
                    text: cosog.string.add,
                    iconCls: 'add'
      }, "-", {
                    xtype: 'button',
                    itemId: 'editpumpPanelLabelClassBtnId',
                    id: 'editpumpPanelLabelClassBtn_Id',
                    text: cosog.string.update,
                    action: 'editpumpPanelAction',
                    hidden:true,
                    disabled: false,
                    iconCls: 'edit'
      }, "-", {
                    xtype: 'button',
                    itemId: 'delpumpPanelLabelClassBtnId',
                    id: 'delpumpPanelLabelClassBtn_Id',
                    disabled: false,
                    action: 'delpumpPanelAction',
                    text: cosog.string.del,
                    iconCls: 'delete'
      }, "-",{
          xtype: 'button',
          itemId: 'savePumpGridDataBtnId',
          id: 'savePumpGridDataBtnId_Id',
          disabled: false,
          hidden:false,
          action: 'savePumpGridDataAction',
          text: cosog.string.save,
          iconCls: 'save'
   }]
     }]
        });
        this.callParent(arguments)
    }
});