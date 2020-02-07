Ext.define('AP.view.well.SinglewellPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.singlewellPanel',
    layout: 'fit',
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selType: 'checkboxmodel',
    multiSelect: true,
    initComponent: function () {
        //var singleJcStore = Ext.create('AP.store.well.SingleJcStore');
        //var singleJhhStore = Ext.create('AP.store.well.SingleJhhStore');
        //var singleJhStore = Ext.create('AP.store.well.SingleJhStore');
        var singlewellStore = Ext.create('AP.store.well.SinglewellStore');
        //分页工具栏
        var bbar = new Ext.PageNumberToolbar({
            store: singlewellStore,
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
        var jcStore_A = new Ext.data.SimpleStore({
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
                url: context + '/outputwellproductionController/querySingleWellParam',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {

                    var new_params = {
                        type: 'jc'
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var jhhStore_B = new Ext.data.SimpleStore({
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
                url: context + '/outputwellproductionController/querySingleWellParam',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var t_tobj = Ext.getCmp('SinglewellPanel_jc_Id');
                    var t_t_val = "";
                    if (!Ext.isEmpty(t_tobj)) {
                        t_t_val = t_tobj.getValue();
                    }
                    var new_params = {
                        jc: t_t_val,
                        type: 'jhh'

                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var jhStore_C = new Ext.data.SimpleStore({
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
                url: context + '/outputwellproductionController/querySingleWellParam',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var t_tobj = Ext.getCmp('SinglewellPanel_jc_Id');
                    var t_t_val = "";
                    if (!Ext.isEmpty(t_tobj)) {
                        t_t_val = t_tobj.getValue();
                    }
                    var SinglewellPanel_jhh_Id = Ext.getCmp('SinglewellPanel_jhh_Id');
                    if (!Ext.isEmpty(SinglewellPanel_jhh_Id)) {
                        SinglewellPanel_jhh_Id = SinglewellPanel_jhh_Id.getValue();
                    }
                    var new_params = {
                        jc: t_t_val,
                        type: 'jh',
                        jhh: SinglewellPanel_jhh_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var simpleCombo_A = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '井场',
            id: "SinglewellPanel_jc_Id",
            labelWidth: 35,
            store: jcStore_A,
            queryMode: 'local',
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
//                    simpleCombo_A.clearValue();
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
            fieldLabel: '井环号',
            id: "SinglewellPanel_jhh_Id",
            labelWidth: 45,
            queryMode: 'local',
            typeAhead: true,
            store: jhhStore_B,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: '--全部--',
            blankText: '--全部--',
            valueField: "boxkey",
            listeners: {
                expand: function (sm, selections) {
//                    simpleCombo_B.clearValue();
                    simpleCombo_B.getStore().load(); //加载井下拉框的store    
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'singlewellPanel_Id');
                }
            }
        });
        var simpleCombo_C = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '井名',
            id: 'SinglewellPanel_jh_Id',
            labelWidth: 35,
            typeAhead: true,
            store: jhStore_C,
            editable: true,
            autoSelect: false,
            triggerAction: 'all',
            emptyText: '--全部--',
            blankText: '--全部--',
            displayField: "boxval",
            valueField: "boxkey",
            listeners: {
                expand: function (sm, selections) {
//                    simpleCombo_C.clearValue();
                    simpleCombo_C.getStore().load(); //加载井下拉框的store    
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'singlewellPanel_Id');
                }
            }
        });

        Ext.apply(this, {
            id: 'singlewellPanel_Id',
            bbar: bbar,
            tbar: [simpleCombo_A, simpleCombo_B, simpleCombo_C, {
                xtype: 'button',
                name: 'singlewellNameBtn_Id',
                text: '查询',
                pressed: true,
                text_align: 'center',
                width: 50,
                iconCls: 'search',
                handler: function () {
                    var singlewellPanel_Id = Ext
                        .getCmp("singlewellPanel_Id");
                    singlewellPanel_Id.getStore().load();
                }
     }, '->', {
                xtype: 'button',
                itemId: 'addreSinglewellLabelClassBtnId',
                id: 'addreSinglewellLabelClassBtn_Id',
                action: 'addreSinglewellAction',
                text: "创建",
                iconCls: 'add'
     }, "-", {
                xtype: 'button',
                itemId: 'editreSinglewellLabelClassBtnId',
                id: 'editreSinglewellLabelClassBtn_Id',
                text: "修改",
                action: 'editreSinglewellAction',
                disabled: true,
                iconCls: 'edit'
     }, "-", {
                xtype: 'button',
                itemId: 'delreSinglewellLabelClassBtnId',
                id: 'delreSinglewellLabelClassBtn_Id',
                disabled: true,
                action: 'delreSinglewellAction',
                text: "删除",
                iconCls: 'delete'
     }],
            store: singlewellStore,
            columnLines: true, // 列线
            stripeRows: true, // 条纹行
            columns: [{
                header: '序号',
                xtype: 'rownumberer',
                width: 30,
                sortable: false
               }, {
                header: '井编号',
                flex: 3,
                hidden: true,
                dataIndex: 'jbh',
                align: 'center',
                sortable: true
   }, {
                header: '井场',
                flex: 3,
                hidden: true,
                dataIndex: 'jc',
                align: 'center',
                sortable: true
   }, {
                header: '井环号',
                flex: 3,
                hidden: true,
                dataIndex: 'jhh',
                align: 'center',
                sortable: true
   }, {
                header: '井名',
                flex: 3,
                dataIndex: 'jh',
                align: 'center',
                sortable: true
   }, {
                header: '计量日产液量(t/d)',
                flex: 3,
                dataIndex: 'rcyl',
                align: 'center',
                sortable: true
   }, {
                header: '更新时间',
                flex: 3,
                dataIndex: 'cjsj',
                align: 'center',
                sortable: true
                    //renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
    }]
        })
        this.callParent(arguments);
    },
    listeners: {
        selectionchange: function (sm, selections) {
            var n = selections.length || 0;

            this.down('#editreSinglewellLabelClassBtnId').setDisabled(n != 1);
            if (n > 0) {
                this.down('#addreSinglewellLabelClassBtnId').setDisabled(true);
                this.down('#delreSinglewellLabelClassBtnId').setDisabled(false);
            } else {
                this.down('#addreSinglewellLabelClassBtnId').setDisabled(false);
                this.down('#delreSinglewellLabelClassBtnId').setDisabled(true);
            }
        }
    }
})