Ext.define('AP.view.device.StrokefrequencyPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.strokefrequencyPanel',
    id: 'strokefrequencyPanel_Id',
    layout: "fit",
    autoScroll: true,
    // bodyStyle :'overflow-x:hidden;overflow-y:scroll',
    border: false,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selType: 'checkboxmodel',
    multiSelect: true,
    initComponent: function () {
        var me = this;
        //var sccjStrokefrequencyStore = Ext.create('AP.store.device.SccjStrokefrequencyStore');
        //var strokefrequencySccjStore = Ext.create('AP.store.device.StrokefrequencySccjStore');
        var strokefrequencyStore = Ext.create('AP.store.device.StrokefrequencyStore');
        //分页工具栏
        var bbar = new Ext.PageNumberToolbar({
            store: strokefrequencyStore,
            pageSize: defaultPageSize,
            displayInfo: true,
            displayMsg: '当前记录 {0} -- {1} 条 共 {2} 条记录',
            emptyMsg: "没有记录可显示",
            prevText: "上一页",
            nextText: "下一页",
            refreshText: "刷新",
            lastText: "最后页",
            firstText: "第一页",
            beforePageText: "当前页",
            afterPageText: "共{0}页"
        });

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
                url: context + '/strokefrequencyController/queryStrokeFrequencyParams',
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
                url: context + '/strokefrequencyController/queryStrokeFrequencyParams',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var t_tobj = Ext.getCmp('strokefrequencyPanel_sccj_Id');
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
            id: "strokefrequencyPanel_sccj_Id",
            labelWidth: 60,
            store: sccjStore_A,
            queryMode: 'remote',
            emptyText: '--全部--',
            blankText: '--全部--',
            typeAhead: true,
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
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'singlewellPanel_Id');
                }
            }
        });
        var simpleCombo_B = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '抽油泵型号',
            id: "strokefrequencyPanel_cyjxh_Id",
            labelWidth: 70,
            queryMode: 'remote',
            typeAhead: true,
            store: cybxhStore_B,
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
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'singlewellPanel_Id');
                }
            }
        });
        Ext.apply(me, {
            layout: 'fit',
            autoScroll: true,
            // bodyStyle :'overflow-x:hidden;overflow-y:scroll',
            store: strokefrequencyStore,
            columnLines: true, // 列线
            stripeRows: true, // 条纹行
            columns: [{
                header: '序号',
                xtype: 'rownumberer',
                width: 30,
                sortable: false
               }, {
                header: '生产厂家',
                flex: 4,
                align: 'center',
                dataIndex: 'sccj'
   }, {
                header: '抽油机型号',
                flex: 4,
                align: 'center',
                dataIndex: 'cyjxh'
   }, {
                header: '冲次(1/min)',
                flex: 4,
                align: 'center',
                dataIndex: 'cc1'
   }, {
                header: '电机皮带轮直径(mm)',
                flex: 4,
                align: 'center',
                dataIndex: 'djpdlzj'
   }],
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [simpleCombo_A, simpleCombo_B, {
                    xtype: 'button',
                    name: 'strokefrequencyPanelJh_Btn_Id',
                    text: '查询',
                    pressed: true,
                    text_align: 'center',
                    // width : 50,
                    iconCls: 'search',
                    handler: function (v, o) {
                        var strokefrequencyPanel_Id = Ext
                            .getCmp("strokefrequencyPanel_Id");
                        strokefrequencyPanel_Id.getStore().load();
                        // alert(strokefrequencyPanel_Id);
                    }
    }, '->', {
                    xtype: 'button',
                    itemId: 'addStrokefrequencyLabelClassBtnId',
                    id: 'addStrokefrequencyLabelClassBtn_Id',
                    action: 'addStrokefrequencyAction',
                    text: "创建",
                    iconCls: 'add'
    }, "-", {
                    xtype: 'button',
                    itemId: 'editStrokefrequencyLabelClassBtnId',
                    id: 'editStrokefrequencyLabelClassBtn_Id',
                    text: "修改",
                    action: 'editStrokefrequencyAction',
                    disabled: true,
                    iconCls: 'edit'
    }, "-", {
                    xtype: 'button',
                    itemId: 'delStrokefrequencyLabelClassBtnId',
                    id: 'delStrokefrequencyLabelClassBtn_Id',
                    disabled: true,
                    action: 'delStrokefrequencyAction',
                    text: "删除",
                    iconCls: 'delete'
    }]
   }],
            bbar: bbar
        });
        this.callParent(arguments)
    },
    listeners: {
        selectionchange: function (sm, selections) {
            var n = selections.length || 0;

            this.down('#editStrokefrequencyLabelClassBtnId')
                .setDisabled(n != 1);
            if (n > 0) {
                this.down('#addStrokefrequencyLabelClassBtnId')
                    .setDisabled(true);
                this.down('#delStrokefrequencyLabelClassBtnId')
                    .setDisabled(false);
            } else {
                this.down('#addStrokefrequencyLabelClassBtnId')
                    .setDisabled(false);
                this.down('#delStrokefrequencyLabelClassBtnId')
                    .setDisabled(true);
            }
        }
    }
});