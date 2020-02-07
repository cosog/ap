Ext.define('AP.view.rfidSelect.RfidSelectInfoGridPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.rfidSelectInfoGridPanel',
    id: "RfidSelectInfoGridPanel_Id",
    stateful: true,
    autoScroll: true,
    columnLines: true,
    layout: "fit",
    stripeRows: true,
    forceFit: true,
    loadMask: true,
    multiSelect: true,
    selModel: {
        pruneRemoved: false
    },
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
        forceFit: true,
        trackOver: false
    },
    initComponent: function () {
        var AlarmSelectInfoStore = Ext.create("AP.store.rfidSelect.RfidSelectInfoStore");
        // 分页工具栏
        var bbar = new Ext.PageNumberToolbar({
            store: AlarmSelectInfoStore,
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
                url: context + '/rfidInfoController/queryRfidSelectParam',
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
                url: context + '/rfidInfoController/queryRfidSelectParam',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var t_tobj = Ext.getCmp('rfidselect_jc_Id');
                    var t_t_val = "";
                    if (!Ext.isEmpty(t_tobj)) {
                        t_t_val = t_tobj.getValue();
                    }
                    var new_params = {
                        jc: t_t_val,
                        type: 'xm'

                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });

        var jhhStore_C = new Ext.data.SimpleStore({
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
                url: context + '/rfidInfoController/queryRfidSelectParam',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var t_tobj = Ext.getCmp('rfidselect_jc_Id');
                    var t_t_val = "";
                    if (!Ext.isEmpty(t_tobj)) {
                        t_t_val = t_tobj.getValue();
                    }
                    var rfidselect_xm_Id = Ext.getCmp('rfidselect_xm_Id');
                    if (!Ext.isEmpty(rfidselect_xm_Id)) {
                        rfidselect_xm_Id = rfidselect_xm_Id.getValue();
                    }
                    var new_params = {
                        jc: t_t_val,
                        xm: rfidselect_xm_Id,
                        type: 'hfbz'

                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });



        var simpleCombo_A = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '井场',
            id: "rfidselect_jc_Id",
            labelWidth: 35,
            width: 155,
            queryMode: 'local',
            typeAhead: true,
            store: jcStore_A,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: '--全部--',
            blankText: '--全部--',
            valueField: "boxkey",
            listeners: {
                expand: function (sm, selections) {
                    simpleCombo_A.clearValue();
                    simpleCombo_A.getStore().load(); //加载井下拉框的store    
                    simpleCombo_B.clearValue();
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'RfidSelectInfoGridPanel_Id');
                }
            }
        });
        // Simple ComboBox using the data store
        var simpleCombo_B = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '姓名',
            id: "rfidselect_xm_Id",
            labelWidth: 35,
            width: 155,
            store: jhhStore_B,
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
                    simpleCombo_B.clearValue();
                    simpleCombo_B.getStore().load(); //加载井下拉框的store    
                    simpleCombo_C.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'RfidSelectInfoGridPanel_Id');
                }
            }
        });
        var simpleCombo_C = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '合法标志',
            id: "rfidselect_hfbz_Id",
            labelWidth: 58,
            width: 178,
            queryMode: 'local',
            typeAhead: true,
            store: jhhStore_C,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: '--全部--',
            blankText: '--全部--',
            valueField: "boxkey",
            listeners: {
                expand: function (sm, selections) {
                    simpleCombo_C.clearValue();
                    simpleCombo_C.getStore().load(); //加载井下拉框的store    
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'RfidSelectInfoGridPanel_Id');
                }
            }
        });

        Ext.apply(this, {
            tbar: [simpleCombo_A, simpleCombo_B, simpleCombo_C, {
                xtype: 'button',
                text: '查询',
                pressed: true,
                iconCls: 'search',
                handler: function (v, o) {
                    AlarmSelectInfoStore.load();
                }
      }, {
                xtype: 'button',
                text: '导出到Excel',
                pressed: true,
                handler: function (v, o) {
                    var leftOrg_Id = obtainParams('leftOrg_Id');
                    var Reportrfidselect_jc_Id = obtainParams('rfidselect_jc_Id');
                    var Reportrfidselect_xm_Id = obtainParams('rfidselect_xm_Id');
                    var Reportrfidselect_hfbz_Id = obtainParams('rfidselect_hfbz_Id');
                    var fields = "";
                    var heads = "";
                    var RfidSelectInfoGrid_Panel = Ext.getCmp("RfidSelectInfoGridPanel_Id");
                    var get_columns = RfidSelectInfoGrid_Panel.columns;
                    Ext.Array.each(get_columns, function (name, index,
                        countriesItSelf) {
                        var head = get_columns[index];
                        if (!head.hidden && index > 0) {
                            fields += head.dataIndex + ",";
                            heads += head.text + ",";
                        }
                    });
                    if (isNotVal(fields)) {
                        fields = fields.substring(0, fields.length - 1);
                        heads = heads.substring(0, heads.length - 1);
                    }
                    fields = "id" + fields;
                    var param = "&fields=" + fields + "&orgId=" + leftOrg_Id + "&jc=" + Reportrfidselect_jc_Id + "&xm=" + Reportrfidselect_xm_Id + "&hfbz=" + Reportrfidselect_hfbz_Id;
                    openExcelWindow(context + '/rfidInfoController/exportExcelReportRfid?flag=true&heads=' + param);

                }
      }, '->', {
                id: 'RfidSelectInfoGridPanelTotalCount_Id',
                xtype: 'component',
                tpl: '总记录数: {count}',
                style: 'margin-right:15px'
     }]
        });
        this.callParent(arguments);
    }
});