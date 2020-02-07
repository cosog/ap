Ext.define('AP.view.device.StrokePanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.strokePanel',
    id: 'strokePanel_Id',
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
        //var sccjStore = Ext.create('AP.store.device.SccjStrokeStore');
        //var strokeSccjStore = Ext.create('AP.store.device.StrokeSccjStore');
        var strokeStore = Ext.create('AP.store.device.StrokeStore');
        //分页工具栏
        var bbar = new Ext.PageNumberToolbar({
            store: strokeStore,
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
                url: context + '/strokeController/queryStrokeParams',
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
                url: context + '/strokeController/queryStrokeParams',
                type: "ajax"
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var t_tobj = Ext.getCmp('strokesPanel_sccj_Id');
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
            id: "strokesPanel_sccj_Id",
            labelWidth: 60,
            store: sccjStore_A,
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
                    simpleCombo_A.clearValue();
                    simpleCombo_A.getStore().load(); //加载井下拉框的store    
                    simpleCombo_B.clearValue();
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'strokePanel_Id');
                }
            }
        });
        var simpleCombo_B = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '抽油机型号',
            id: "strokesPanel_cyjxh_Id",
            labelWidth: 70,
            queryMode: 'local',
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
                },
                specialkey: function (field, e) {
                    onEnterKeyDownFN(field, e, 'strokePanel_Id');
                }
            }
        });

        Ext.apply(me, {
            layout: 'fit',
            autoScroll: true,
            // bodyStyle :'overflow-x:hidden;overflow-y:scroll',
            store: strokeStore,
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
                header: '冲程(m)',
                flex: 4,
                align: 'center',
                dataIndex: 'cc'
     }, {
                header: '曲柄孔距(mm)',
                flex: 4,
                align: 'center',
                dataIndex: 'qbkj'
     }, {
                header: '位置及扭矩因素',
                flex: 4,
                align: 'center',
                dataIndex: 'wzjnjys',
                renderer: function (v) {
                    var result = "";
                    if (isNotVal(v)) {
                        result = v
                    }
                    return result;
                }
     }],
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [simpleCombo_A, simpleCombo_B, {
                    xtype: 'button',
                    name: 'strokePanelJh_Btn_Id',
                    text: '查询',
                    pressed: true,
                    text_align: 'center',
                    // width : 50,
                    iconCls: 'search',
                    handler: function (v, o) {
                        var strokePanel_Id = Ext
                            .getCmp("strokePanel_Id");
                        strokePanel_Id.getStore().load();
                        // alert(strokePanel_Id);
                    }
      }, '->', {
                    xtype: 'button',
                    itemId: 'addStrokePanelLabelClassBtnId',
                    id: 'addStrokePanelLabelClassBtn_Id',
                    action: 'addStrokePanelAction',
                    text: "创建",
                    iconCls: 'add'
      }, "-", {
                    xtype: 'button',
                    itemId: 'editStrokePanelLabelClassBtnId',
                    id: 'editStrokePanelLabelClassBtn_Id',
                    text: "修改",
                    action: 'editStrokePanelAction',
                    disabled: true,
                    iconCls: 'edit'
      }, "-", {
                    xtype: 'button',
                    itemId: 'delStrokePanelLabelClassBtnId',
                    id: 'delStrokePanelLabelClassBtn_Id',
                    disabled: true,
                    action: 'delStrokePanelAction',
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

            this.down('#editStrokePanelLabelClassBtnId').setDisabled(n != 1);
            if (n > 0) {
                this.down('#addStrokePanelLabelClassBtnId').setDisabled(true);
                this.down('#delStrokePanelLabelClassBtnId').setDisabled(false);
            } else {
                this.down('#addStrokePanelLabelClassBtnId').setDisabled(false);
                this.down('#delStrokePanelLabelClassBtnId').setDisabled(true);
            }
        }
    }
});
//// 第一级下拉框
//var pumpingunitCssjCombo = new Ext.form.ComboBox({
//	width : 200,
//	fieldLabel : "生产厂家",
//	name : 'strokePanel_sccj',
//	id : 'strokesPanel_sccj_Ids',
//	emptyText: "--请选择生产厂家--",
//	mode: 'local',
//	autoLoad: true,
//	editable: false,
//	allowBlank: false,
//    	blankText:"不能为空",
//	triggerAction: 'all',
//	valueField: 'id',// 实际值
//	displayField: 'sccj',// 显示值
//	store: pumpingunitCssjStore,// 数据源
//	listeners: {// select监听函数
//            select : function(combo, record){
//            	pumpingunitCyjxhCombo.reset();
//                pumpingunitCyjxhStore.load({
//                	url: "/pumpingunitController/findBycyjxhList",
//                	params: {
//                		typeId: combo.value
//                	}
//                });   
//            }  
//    	}
//});
//// 第二级下拉框
//var pumpingunitCyjxhCombo = new Ext.form.ComboBox({
//	width : 200,
//	fieldLabel : "抽油机型号",
//	name : 'strokesPanel_cyjxh',
//	id : 'strokesPanel_cyjxh_Ids',
//	emptyText: "--请选择抽油机类型--",
//	mode: 'local',
//	autoLoad: true,
//	editable: false,
//	allowBlank: false,
//	blankText:"不能为空",
//	triggerAction: 'all',
//	valueField: 'id',// 实际值
//	displayField: 'cyjxh',// 显示值
//	store: pumpingunitCyjxhStore// 数据源
//});