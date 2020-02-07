Ext.define('AP.view.device.PumpingunitWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.pumpingunitWindow',
    layout: 'fit',
    iframe: true,
    id: 'pumpingunitWindow_Id',
    closeAction: 'destroy',
    width: 1024,
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    autoScroll: true,
    maximizable: true,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var wellWindowEditFrom = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            bodyStyle: 'padding:5px 5px 0',
            fieldDefaults: {
                labelAlign: 'left',
                msgTarget: 'side'
            },
            defaults: {
                border: false,
                baseCls: 'x-plain',
                xtype: 'panel',
                flex: 1,
                defaultType: 'textfield',
                layout: 'anchor'
            },
            layout: 'hbox',
            items: [{
                items: [{
                        xtype: 'hidden',
                        fieldLabel: '序号',
                        id: 'jlbh_Id',
                        anchor: '95%',
                        name: "Pumpingunit.jlbh"
        }, {
                        xtype: 'hidden',
                        fieldLabel: '',
                        value: '1',
                        id: 'xzfx_Id',
                        anchor: '95%',
                        name: "Pumpingunit.xzfx"
        }, {
                        xtype: 'hidden',
                        fieldLabel: '',
                        value: '1',
                        id: 'njysjsxz_Id',
                        anchor: '95%',
                        name: "Pumpingunit.njysjsxz"
        }, {
                        xtype: 'hidden',
                        fieldLabel: '',
                        value: '101',
                        id: 'cyjlx_Id',
                        anchor: '95%',
                        name: "Pumpingunit.cyjlx"
        },

                    {
                        //xtype : 'hidden',
                        fieldLabel: '生产厂家',
                        id: 'sccj_Id',
                        anchor: '95%',
                        name: "Pumpingunit.sccj"
        }, {
                        fieldLabel: '抽油机型号',
                        id: 'cyjxh_Id',
                        anchor: '95%',
                        name: "Pumpingunit.cyjxh"
        }, {
                        fieldLabel: '悬点额定载荷(kN)',
                        id: 'xdedzh_Id',
                        anchor: '95%',
                        name: "Pumpingunit.xdedzh"
        }, {
                        fieldLabel: '减速器额定扭矩(kN*m)',
                        id: 'jsqednj_Id',
                        anchor: '95%',
                        name: "Pumpingunit.jsqednj"
        }, {
                        fieldLabel: '连杆长(m)',
                        id: 'lgc_Id',
                        anchor: '95%',
                        name: "Pumpingunit.lgc"
        }, {
                        fieldLabel: '前臂长(m)',
                        id: 'qbc_Id',
                        anchor: '95%',
                        name: "Pumpingunit.qbc"
        }, {
                        fieldLabel: '后臂长(m)',
                        id: 'hbc_Id',
                        anchor: '95%',
                        name: "Pumpingunit.hbc"
        }, {
                        fieldLabel: '单块曲柄重量(kN)',
                        id: 'dkqbzl_Id',
                        anchor: '95%',
                        name: "Pumpingunit.dkqbzl"
        }, {
                        fieldLabel: '曲柄重心半径(m)',
                        id: 'qbzxbj_Id',
                        anchor: '95%',
                        name: "Pumpingunit.qbzxbj"
        }]
     }, {
                items: [{
                    fieldLabel: '相对距离(m)',
                    id: 'xdjl_Id',
                    anchor: '95%',
                    name: "Pumpingunit.xdjl"
        }, {
                    fieldLabel: 'HG距离(m)',
                    id: 'hg_Id',
                    anchor: '95%',
                    name: "Pumpingunit.hg"
        }, {
                    fieldLabel: '结构不平衡重(kN)',
                    id: 'jgbphz_Id',
                    anchor: '95%',
                    name: "Pumpingunit.jgbphz"
        }, {
                    fieldLabel: '单块平衡块重量(kN)',
                    id: 'dkphkzl_Id',
                    anchor: '95%',
                    name: "Pumpingunit.dkphkzl"
        }, {
                    fieldLabel: '曲柄偏置角(°)',
                    id: 'qbpzj_Id',
                    anchor: '95%',
                    name: "Pumpingunit.qbpzj"
        }, {
                    fieldLabel: '减速箱传动比',
                    id: 'jsxcdb_Id',
                    anchor: '95%',
                    name: "Pumpingunit.jsxcdb"
        }, {
                    fieldLabel: '减速箱皮带轮直径(m)',
                    id: 'jsxpdlzj_Id',
                    anchor: '95%',
                    name: "Pumpingunit.jsxpdlzj"
        }, {
                    anchor: '95%',
                    xtype: "combobox",
                    fieldLabel: '旋转方向',
                    id: 'xzfx_Id1',
                    triggerAction: 'all',
                    selectOnFocus: true,
                    forceSelection: true,
                    allowBlank: false,
                    store: new Ext.data.SimpleStore({
                        fields: ['value', 'text'],
                        data: [['1', '顺时针'],
               ['2', '逆时针']]
                    }),
                    displayField: 'text',
                    valueField: 'value',
                    mode: 'local',
                    emptyText: '请选择类别',
                    blankText: '请选择类别',
                    listeners: {
                        select: function (v, o) {
                            Ext.getCmp("xzfx_Id").setValue(this.value);
                        }
                    }
         }, {
                    fieldLabel: '最大调整距离(m)',
                    id: 'zdtzjl_Id',
                    anchor: '95%',
                    name: "Pumpingunit.zdtzjl"
        }]
     }, {
                items: [{
                    anchor: '95%',
                    xtype: "combobox",
                    fieldLabel: '扭矩因素计算选择',
                    id: 'njysjsxz_Id1',
                    triggerAction: 'all',
                    selectOnFocus: true,
                    forceSelection: true,
                    allowBlank: false,
                    store: new Ext.data.SimpleStore({
                        fields: ['value', 'text'],
                        data: [['1', '几何尺寸计算'],
               ['2', '厂家说明书输入']]
                    }),
                    displayField: 'text',
                    valueField: 'value',
                    mode: 'local',
                    emptyText: '请选择类别',
                    blankText: '请选择类别',
                    listeners: {
                        select: function (v, o) {
                            Ext.getCmp("njysjsxz_Id").setValue(this.value);
                        }
                    }
         }, {
                    fieldLabel: '平衡块自带块数',
                    id: 'phkzdks_Id',
                    anchor: '95%',
                    name: "Pumpingunit.phkzdks"
        }, {
                    xtype: "combobox",
                    fieldLabel: '抽油机类型',
                    id: 'cyjlx_Id1',
                    triggerAction: 'all',
                    anchor: '95%',
                    selectOnFocus: true,
                    forceSelection: true,
                    allowBlank: false,
                    store: new Ext.data.SimpleStore({
                        fields: ['value', 'text'],
                        data: [['101', '常规抽油机'], ['102', '异相型抽油机'], ['103', '双驴头抽油机'], ['104', '下偏杠铃抽油机'],
               ['105', '调径变矩抽油机'], ['106', '立式皮带机'], ['107', '立式链条机'], ['108', '直线驱抽油机']]
                    }),
                    displayField: 'text',
                    valueField: 'value',
                    mode: 'local',
                    emptyText: '请选择类别',
                    blankText: '请选择类别',
                    listeners: {
                        select: function (v, o) {
                            Ext.getCmp("cyjlx_Id").setValue(this.value);
                        }
                    }
         }, {
                    fieldLabel: '下偏杠铃R值(m)',
                    id: 'xpglr_Id',
                    anchor: '95%',
                    name: "Pumpingunit.xpglr"
        }, {
                    fieldLabel: '下偏杠铃单块重量(kN)',
                    id: 'xpgldkzl_Id',
                    anchor: '95%',
                    name: "Pumpingunit.xpgldkzl"
        }, {
                    fieldLabel: '下偏杠铃自带块数',
                    id: 'xpglzdks_Id',
                    anchor: '95%',
                    name: "Pumpingunit.xpglzdks"
        }, {
                    fieldLabel: '皮带效率(小数)',
                    id: 'pdxl_Id',
                    anchor: '95%',
                    name: "Pumpingunit.pdxl"
        }, {
                    fieldLabel: '减速箱效率(小数)',
                    id: 'jsxxl_Id',
                    anchor: '95%',
                    name: "Pumpingunit.jsxxl"
        }, {
                    fieldLabel: '四连杆效率(小数)',
                    id: 'slgxl_Id',
                    anchor: '95%',
                    name: "Pumpingunit.slgxl"
        }]
     }],
            buttons: [{
                xtype: 'button',
                id: 'addPumpingunitLabelClassBtn_win_Id',
                text: '保存',
                iconCls: 'save',
                handler: SavePumpingunitSubmitBtnForm
     }, {
                xtype: 'button',
                id: 'editPumpingunitLabelClassBtn_win_Id',
                text: '修改',
                hidden: true,
                iconCls: 'edit',
                handler: UpdatePumpingunitSubmitBtnForm
     }, {
                text: '取消',
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("pumpingunitWindow_Id").close();
                }
     }]
        });
        Ext.apply(me, {
            items: wellWindowEditFrom
        })
        me.callParent(arguments);
    }
})