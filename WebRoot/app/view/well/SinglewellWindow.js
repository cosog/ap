Ext.define('AP.view.well.SinglewellWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.singlewellWindow',
    layout: 'fit',
    iframe: true,
    id: 'singlewellWindow_Id',
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
        var WellJcStore = Ext.create("AP.store.well.WellJcStore");
        var WellJhhStore = Ext.create("AP.store.well.WellJhhStore");
        var WellJhStore = Ext.create("AP.store.well.WellJhStore");
        var WellInStore = Ext.create("AP.store.well.WellInStore");
        var wellWindowEditFrom = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                    xtype: 'hidden',
                    fieldLabel: '序号',
                    id: 'jlbh_Id',
                    name: "outputwellproduction.jlbh"
       }, {
                    xtype: 'hidden',
                    fieldLabel: '井编号',
                    id: 'jbh_Id',
                    name: "outputwellproduction.jbh"
       }, {
                    xtype: "combobox",
                    id: 'singlewellWindow_jc_Id',
                    fieldLabel: '井场',
                    displayField: 'jc',
                    valueField: 'jc',
                    store: WellJcStore,
                    triggerAction: 'all',
                    // pageSize : 8, // 设置每页显示的条数
                    queryMode: 'remote',
                    selectOnFocus: true,
                    //forceSelection : true,
                    allowBlank: true,
                    editable: true,
                    queryParam: 'jc',
                    emptyText: '--全部--',
                    blankText: '--全部--',
                    listeners: {
                        select: function (combo, record, index) {
                            try {
                                var parent = Ext.getCmp('singlewellWindow_jhh_Id');
                                parent.clearValue();
                                var parent1 = Ext.getCmp('singlewellWindow_jh_Id');
                                parent1.clearValue();
                                parent.store.load({
                                    params: {
                                        jc: this.value
                                    }
                                });
                            } catch (ex) {
                                Ext.MessageBox.alert("错误", "井场数据加载失败。");
                            }
                        },
                        beforequery: function (qe) {
                            delete qe.combo.lastQuery;
                        },
                        afterRender: function (combo, o) {　　
                            if (WellJcStore.getTotalCount() > 0) {
                                var orgId = WellJcStore.data.items[0].data.jc;
                                var orgName = WellJcStore.data.items[0].data.jc;
                                combo.setValue(orgId);
                                combo.setRawValue(orgName);
                            }
                        },
                        delay: 500
                    }

       }, {
                    xtype: "combobox",
                    id: 'singlewellWindow_jhh_Id',
                    fieldLabel: '井环号',
                    displayField: 'jhh',
                    valueField: 'jhh',
                    store: WellJhhStore,
                    triggerAction: 'all',
                    queryMode: 'remote',
                    selectOnFocus: true,
                    //forceSelection : true,
                    allowBlank: true,
                    editable: true,
                    queryParam: 'jhh',
                    emptyText: '--全部--',
                    blankText: '--全部--',
                    listeners: {
                        select: function (combo, record, index) {
                            try {
                                var parent = Ext.getCmp('singlewellWindow_jh_Id');
                                // alert(this.value);
                                parent.clearValue();
                                parent.store.load({
                                    params: {
                                        jhh: this.value
                                    }
                                });
                            } catch (ex) {
                                Ext.MessageBox.alert("错误", "井环号数据加载失败。");
                            }
                        },
                        beforequery: function (qe) {
                            delete qe.combo.lastQuery;
                        },
                        afterRender: function (combo, o) {　　
                            if (WellJhhStore.getTotalCount() > 0) {
                                var orgId = WellJhhStore.data.items[0].data.jhh;
                                var orgName = WellJhhStore.data.items[0].data.jhh;
                                combo.setValue(orgId);
                                combo.setRawValue(orgName);
                            }
                        },
                        delay: 500
                    }
       }, {
                    xtype: "combobox",
                    name: 'singlewellWindow_jh',
                    id: 'singlewellWindow_jh_Id',
                    fieldLabel: '井名',
                    displayField: 'jh',
                    valueField: 'jhId',
                    store: WellJhStore,
                    triggerAction: 'all',
                    queryMode: 'remote',
                    selectOnFocus: true,
                    //forceSelection : true,
                    allowBlank: true,
                    editable: true,
                    queryParam: 'jh',
                    emptyText: '--全部--',
                    blankText: '--全部--',
                    listeners: {
                        expand: function (sm, selections) {
                            sm.clearValue();
                            sm.getStore().load(); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                            try {
                                Ext.getCmp("jbh_Id").setValue(this.value);
                                //alert(this.value);
                            } catch (ex) {
                                Ext.MessageBox.alert("错误", "井名数据加载失败。");
                            }
                        },
                        afterRender: function (combo, o) {　　
                            if (WellJhStore.getTotalCount() > 0) {
                                var orgId = WellJhStore.data.items[0].data.jhId;
                                var orgName = WellJhStore.data.items[0].data.jh;
                                combo.setValue(orgId);
                                combo.setRawValue(orgName);
                            }
                        },
                        blur: function (t, e) {
                            var value_ = t.rawValue;
                            // 检索动态列表头及内容信息
                            Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                    jbh: t.value
                                },
                                url: context + '/wellsManagerController/judgeWellExistOrNot',
                                success: function (response, opts) {
                                    // 处理后json
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                        Ext.Msg.alert("提示", "抱歉！<font color='red'>【井名:" + value_ + "】</font>已经存在！");
                                        t.setValue("");
                                    }

                                    // ==end
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert("信息提示", "后台获取数据失败！");
                                }
                            });
                        },
                        beforequery: function (qe) {
                            delete qe.combo.lastQuery;
                        },
                        delay: 500
                    }
       }, {
                    fieldLabel: '计量日产液量(m^3/d)',
                    id: 'rcyl_Id',
                    value: '',
                    regex: /^[0-9]+(.[0-9]*)$/,
                    invalidText: '只能输入【0-9】之间的数!',
                    name: "outputwellproduction.rcyl"
           },
                {
                    xtype: 'datetimefield',
                    fieldLabel: '更新时间',
                    id: 'cjsj_Id',
                    // value : new date,
                    format: 'Y-m-d H:i:s',
                    value: new Date(),
                    name: "outputwellproduction.cjsj"
       }],
            buttons: [{
                xtype: 'button',
                id: 'addFromSinglewellWindowBtn_Id',
                text: '保存',
                handler: SaveSinglewellSubmitBtnForm
       }, {
                xtype: 'button',
                id: 'updateFromSinglewellWindowBtn_Id',
                text: '修改',
                hidden: true,
                handler: UpdateSinglewellSubmitBtnForm
       }, {
                text: '取消',
                handler: function () {
                    Ext.getCmp("singlewellWindow_Id").close();
                }
       }]
        });
        Ext.apply(me, {
            items: wellWindowEditFrom
        })
        me.callParent(arguments);
    }
})