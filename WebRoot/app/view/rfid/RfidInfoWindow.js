Ext.define("AP.view.rfid.RfidInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.rfidInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'rfid_addwin_Id',
    closeAction: 'destroy',
    width: 300,
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
        var postrfidEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                    xtype: "hidden",
                    fieldLabel: '记录编号',
                    id: 'jlbh_Id',
                    name: "rfid.jlbh"
         },
                {
                    fieldLabel: "RFID卡号",
                    id: 'rfidkh_Id',
                    name: "rfid.rfidkh",
                    allowBlank: false,
                    listeners: {
                        blur: function (t, e) {
                            var value_ = t.getValue();
                            // 检索动态列表头及内容信息
                            Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                    kh: t.value
                                },
                                url: context + '/rfidInfoController/judgerfidExistOrNot',
                                success: function (response, opts) {
                                    // 处理后json
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                        Ext.Msg.alert("提示", "抱歉！<font color='red'>【RFID卡号:" + value_ + "】</font>已经存在！");
                                        t.setValue("");
                                    }

                                    // ==end
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert("信息提示", "后台获取数据失败！");
                                }
                            });
                        }
                    }
         }, {
                    id: 'ygbh_Id',
                    fieldLabel: "员工编号",
                    value: '',
                    name: "rfid.ygbh"
         }, {
                    id: 'ygxm_Id',
                    fieldLabel: "员工姓名",
                    value: '',
                    name: "rfid.ygxm"
         }, {
                    xtype: "combobox",
                    id: 'xb_Id',
                    fieldLabel: "性别",
                    name: "rfid.xb",
                    value: '男',
                    triggerAction: 'all',
                    selectOnFocus: true,
                    forceSelection: true,
                    allowBlank: false,
                    store: new Ext.data.SimpleStore({
                        fields: ['value', 'text'],
                        data: [['男', '男'],
               ['女', '女']]
                    }),
                    displayField: 'text',
                    valueField: 'value',
                    mode: 'local',
                    emptyText: '请选择用户性别',
                    blankText: '请选择用户性别',
                    listeners: {
                        beforequery: function (qe) {
                            delete qe.combo.lastQuery;
                        },
                        expand: function (v, o) {
                            v.setValue("");
                        },
                        delay: 200
                    }
         }, {
                    id: 'bmbh_Id',
                    fieldLabel: "部门编号",
                    value: '',
                    name: "rfid.bmbh"
         }, {
                    id: 'bmmc_Id',
                    fieldLabel: "部门名称",
                    value: '',
                    name: "rfid.bmmc"
         }],
            buttons: [{
                id: 'addFormrfid_Id',
                xtype: 'button',
                iconCls: 'save',
                text: '保存',
                handler: SaverfidDataInfoSubmitBtnForm
         }, {
                xtype: 'button',
                id: 'updateFormrfid_Id',
                text: '修改',
                hidden: true,
                iconCls: 'edit',
                handler: UpdaterfidDataInfoSubmitBtnForm
         }, {
                text: '取消',
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("rfid_addwin_Id")
                        .close();
                }
         }]
        });
        Ext.apply(me, {
            items: postrfidEditForm
        });
        me.callParent(arguments);
    }
});