Ext.define('AP.view.device.StrokeWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.strokeWindow',
    layout: 'fit',
    iframe: true,
    id: 'strokeWindow_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        //var pumpingunitCssjStore = Ext.create('AP.store.device.PumpingunitCssjStore');
        //var pumpingunitCyjxhStore = Ext.create('AP.store.device.PumpingunitCyjxhStore');
        //var strokeSccjStore = Ext.create('AP.store.device.SccjCyjStore');
        var sccjStore_A = new Ext.data.SimpleStore({
            fields: [
                {
                    name: "boxkey",
                    type: "string"
                },
                {
                    name: "boxval",
                    type: "string"
                }
        ],
            proxy: {
                url: context + '/strokeController/queryStrokeWinParams',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var new_params = {
                        type: 'sccj'
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var cybxhStore_B = new Ext.data.SimpleStore({
            fields: [
                {
                    name: "boxkey",
                    type: "string"
                },
                {
                    name: "boxval",
                    type: "string"
                }
        ],
            proxy: {
                url: context + '/strokeController/queryStrokeWinParams',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var t_tobj = Ext.getCmp('strokeswin_sccj_Id');
                    var t_t_val = "";
                    if (!Ext.isEmpty(t_tobj)) {
                        t_t_val = t_tobj.getValue();
                    }
                    var new_params = {
                        sccj: t_t_val,
                        type: 'cyjxh'

                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });


        // Simple ComboBox using the data store
        var simpleCombo_A = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '生产厂家',
            id: "strokeswin_sccj_Id",
            store: sccjStore_A,
            queryMode: 'local',
            emptyText: '--全部--',
            blankText: '--全部--',
            name: "Stroke.sccj",
            typeAhead: true,
            anchor: '95%',
            autoSelect: false,
            allowBlank: true,
            triggerAction: 'all',
            editable: true,
            displayField: "boxval",
            valueField: "boxkey",
            listeners: {
                expand: function (sm, selections) {
                    simpleCombo_A.clearValue();
                    simpleCombo_A.getStore().load(); //加载井下拉框的store    
                    simpleCombo_B.clearValue();
                }
            }
        });
        var simpleCombo_B = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '抽油机型号',
            id: "strokeswin_cyjxh_Id",
            queryMode: 'local',
            name: "Stroke.cyjxh",
            typeAhead: true,
            store: cybxhStore_B,
            anchor: '95%',
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: '--全部--',
            blankText: '--全部--',
            valueField: "boxkey",
            listeners: {
                expand: function (sm, selections) {
                    simpleCombo_B.clearValue();
                    simpleCombo_B.getStore().load(); //加载井下拉框的store    
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
                name: "Stroke.jlbh"
     }, {
                xtype: 'hidden',
                fieldLabel: '抽油机编号', //抽油机编号对应jlbh
                id: 'cyjbh_Id',
                anchor: '95%',
                name: "Stroke.cyjbh"
     }, simpleCombo_A, simpleCombo_B, {
                fieldLabel: '冲程(m)',
                id: 'cc_Id',
                value: '',
                anchor: '95%',
                name: "Stroke.cc"
     }, {
                fieldLabel: '曲柄孔距(mm)',
                id: 'qbkj_Id',
                value: '',
                anchor: '95%',
                name: "Stroke.qbkj"
     }, {
                fieldLabel: '位置及扭矩因素',
                id: 'wzjnjys_Id',
                value: '',
                anchor: '95%',
                name: "Stroke.wzjnjys"
     }],
            buttons: [{
                xtype: 'button',
                id: 'addStrokeLabelClassBtn_win_Id',
                text: '保存',
                iconCls: 'save',
                handler: SaveStrokeSubmitBtnForm
     }, {
                xtype: 'button',
                id: 'editStrokeLabelClassBtn_win_Id',
                text: '修改',
                iconCls: 'edit',
                hidden: true,
                handler: UpdateStrokeSubmitBtnForm
     }, {
                text: '取消',
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("strokeWindow_Id").close();
                }
     }]
        });
        Ext.apply(me, {
            items: wellWindowEditFrom
        })
        me.callParent(arguments);
    }
})