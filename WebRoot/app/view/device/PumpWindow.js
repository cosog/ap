Ext.define('AP.view.device.PumpWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.pumpWindow',
    layout: 'fit',
    iframe: true,
    id: 'pumpWindow_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    constrain: true,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var BlxTypeStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
                url: context + '/productionDataController/loadMenuTypeData',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var new_params = {
                        type: 'BLX'
                    };
                    Ext.apply(store.proxy.extraParams,
                        new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var BlxTypeCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.blx,
                id: 'blx_Id1',
                anchor: '90%',
                value: '1',
                store: BlxTypeStore,
                queryMode: 'remote',
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                typeAhead: true,
                allowBlank: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("blx_Id").setValue(this.value);
                    }
                }
            });

        var BjbTypeStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
                url: context + '/productionDataController/loadMenuTypeData',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var new_params = {
                        type: 'BJB'
                    };
                    Ext.apply(store.proxy.extraParams,
                        new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var BjbTypeCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.bjb,
                id: 'bjb_Id1',
                anchor: '90%',
                value: '1',
                store: BjbTypeStore,
                queryMode: 'remote',
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                typeAhead: true,
                allowBlank: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("bjb_Id").setValue(this.value);
                    }
                }
            });
        var wellWindowEditFrom = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: 'hidden',
                fieldLabel: '序号',
                id: 'jlbh_Id',
                name: "Pump.jlbh"
         }, {
                xtype: 'hidden',
                fieldLabel: '',
                value: "1",
                id: 'bjb_Id',
                name: "Pump.bjb"
         }, {
                xtype: 'hidden',
                fieldLabel: '',
                value: "1",
                id: 'blx_Id',
                name: "Pump.blx"
         }, {
                fieldLabel: cosog.string.sccj,
                id: 'sccj_Id',
                anchor: '90%',
                name: "Pump.sccj"
         }, {
                fieldLabel: cosog.string.cybxh,
                id: 'cybxh_Id',
                anchor: '90%',
                name: "Pump.cybxh"
         }, BlxTypeCombox, BjbTypeCombox, {
                xtype: "combobox",
                fieldLabel: cosog.string.bj,
                name: 'Pump.bj',
                anchor: '90%',
                id: 'bj_Id',
                value: '28',
                triggerAction: 'all',
                selectOnFocus: true,
                typeAhead: true,
                //forceSelection : true,
                editable: true,
                allowBlank: true,
                store: new Ext.data.SimpleStore({
                    fields: ['value', 'text'],
                    data: [['28', '28'], ['32', '32'],
               ['38', '38'], ['44', '44'], ['56', '56'], ['60', '60'],
               ['70', '70'], ['83', '83'], ['95', '95'], ['110', '110']]
                }),
                displayField: 'text',
                valueField: 'value',
                mode: 'local',
                emptyText: cosog.string.chooseype,
                blankText: cosog.string.chooseype,
                listeners: {}
         }, {
                fieldLabel: cosog.string.zsc,
                id: 'zsc_Id',
                value: '',
                anchor: '90%',
                name: "Pump.zsc"
         }],
            buttons: [{
                xtype: 'button',
                id: 'addpumpPanelLabelClassBtn_win_Id',
                text: cosog.string.save,
                iconCls: 'save',
                handler: SavePumpPanelSubmitBtnForm
         }, {
                xtype: 'button',
                id: 'editpumpPanelLabelClassBtn_win_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: UpdatePumpPanelSubmitBtnForm
         }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("pumpWindow_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: wellWindowEditFrom
        })
        me.callParent(arguments);
    }
})