Ext.define('AP.view.device.StrokefrequencyWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.strokefrequencyWindow',
    layout: 'fit',
    iframe: true,
    id: 'strokefrequencyWindow_Id',
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
        var pumpingunitCssjStore = Ext.create('AP.store.device.PumpingunitCssjStore');
        var pumpingunitCyjxhStore = Ext.create('AP.store.device.PumpingunitCyjxhStore');
        var strokeSccjStore = Ext.create('AP.store.device.SccjCyjStore');
        var wellWindowEditFrom = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: 'hidden',
                fieldLabel: '序号',
                id: 'jlbh_Id',
                name: "Strokefrequency.jlbh"
     }, {
                xtype: 'hidden',
                fieldLabel: '抽油机编号', //抽油机编号对应jlbh
                id: 'cyjbh_Id',
                anchor: '95%',
                name: "Strokefrequency.cyjbh"
     }, {
                xtype: "combobox",
                id: 'sccj_Id',
                fieldLabel: '生产厂家',
                displayField: 'sccj',
                valueField: 'sccj',
                store: pumpingunitCssjStore,
                triggerAction: 'all',
                queryMode: 'remote',
                selectOnFocus: true,
                forceSelection: true,
                allowBlank: false,
                editable: true,
                anchor: '95%',
                emptyText: '',
                blankText: '',
                listeners: {
                    select: function (combo, record, index) {
                        try {
                            Ext.getCmp("cyjxh_Id").setValue(this.value);
                            var parent = Ext.getCmp('cyjxh_Id');
                            parent.clearValue();
                            parent.store.load({
                                params: {
                                    sccj: this.value
                                }
                            });
                        } catch (ex) {
                            Ext.MessageBox.alert("错误",
                                "抽油机冲程的生产厂家数据加载失败。");
                        }
                    }
                }
     }, {
                xtype: "combobox",
                id: 'cyjxh_Id',
                fieldLabel: '抽油机型号',
                displayField: 'cyjxh',
                valueField: 'cyjxh',
                store: pumpingunitCyjxhStore,
                triggerAction: 'all',
                queryMode: 'remote',
                selectOnFocus: true,
                forceSelection: true,
                allowBlank: false,
                editable: true,
                anchor: '95%',
                emptyText: '',
                blankText: '',
                listeners: {
                    select: function (combo, record, index) {
                        try {
                            Ext.getCmp("cyjbh_Id").setValue(this.value);
                            //alert(this.value);
                        } catch (ex) {
                            Ext.MessageBox.alert("错误", "抽油机冲程的抽油机型号数据加载失败。");
                        }
                    }
                }
     }, {
                fieldLabel: '冲次(1/min)',
                id: 'cc1_Id',
                value: '',
                anchor: '95%',
                name: "Strokefrequency.cc1"
     }, {
                fieldLabel: '电机皮带轮直径(mm)',
                id: 'djpdlzj_Id',
                value: '',
                anchor: '95%',
                name: "Strokefrequency.djpdlzj"
     }],
            buttons: [{
                xtype: 'button',
                id: 'addStrokefrequencyLabelClassBtn_win_Id',
                text: '保存',
                iconCls: 'save',
                handler: SaveStrokefrequencySubmitBtnForm
     }, {
                xtype: 'button',
                id: 'editStrokefrequencyLabelClassBtn_win_Id',
                text: '修改',
                hidden: true,
                iconCls: 'edit',
                handler: UpdateStrokefrequencySubmitBtnForm
     }, {
                text: '取消',
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("strokefrequencyWindow_Id").close();
                }
     }]
        });
        Ext.apply(me, {
            items: wellWindowEditFrom
        })
        me.callParent(arguments);
    }
})