Ext.define('AP.view.orgAndUser.SysUserEditPwdWin', {
    extend: 'Ext.Window',
    alias: 'widget.sysUserEditPwdWin',
    id: "SysUserEditPwdWinId",
    width: 360,
    //height:360,		
    layout: 'fit',
    closeAction: 'destroy',
    constrain: true,
    resizable: false,
    modal: true,
    title: cosog.string.editPwd,
    initComponent: function () {
        Ext.apply(Ext.form.VTypes, {
            password: function (val, field) { // val指这里的文本框值，field指这个文本框组件，大家要明白这个意思  
                if (field.confirmTo) { // confirmTo是我们自定义的配置参数，一般用来保存另外的组件的id值  
                    var pwd = Ext.get(field.confirmTo); // 取得confirmTo的那个id的值  
                    var newpwd = Ext.getCmp(pwd.id).getValue();
                    return (val == newpwd);
                }

            }
        });
        var me = this;
        //老密码
        var oldPassword = Ext.create("Ext.form.field.Text", {
            id: "sysreSetPasswordWinId_old_password",
            inputType: 'password',
            fieldLabel: '<font color="red">*</font>' + cosog.string.enterOldPwd,
            //vtype:"loginnum_",
            allowBlank: false,
            emptyText: cosog.string.enterOldPwd,
            labelWidth: 100,
            msgTarget: 'side',
            blankText: cosog.string.required
        });
        //新密码
        var newPassword = Ext.create("Ext.form.field.Text", {
            id: "sysreSetPasswordWinId_new_password",
            inputType: 'password',
            fieldLabel: '<font color="red">*</font>' + cosog.string.enterNewPwd,
            emptyText: cosog.string.enterNewPwd,
            // vtype:"loginnum_",
            labelWidth: 100,
            allowBlank: false,
            msgTarget: 'side',
            blankText: cosog.string.required
        });
        //再次密码
        var againPassword = Ext.create("Ext.form.field.Text", {
            id: "sysreSetPasswordWinId_again_password",
            inputType: 'password',
            emptyText: cosog.string.enterNewPwdAgain,
            // vtype:"loginnum_",
            vtype: "password", // 自定义的验证类型  
            vtypeText: cosog.string.enterpwdNotEqual,
            confirmTo: "sysreSetPasswordWinId_new_password",
            fieldLabel: '<font color="red">*</font>' + cosog.string.enterNewPwdAgain1,
            allowBlank: false,
            labelWidth: 100,
            msgTarget: 'side',
            blankText: cosog.string.required
        });

        //表单组件		
        var sysreSetPasswordWinIdForm = Ext.create("Ext.form.Panel", {
            id: "sysreSetPasswordWinId_form_id",
            layout: 'auto',
            border: false,
            labelSeparator: ':',
            autoWidth: 'auto',
            bodyStyle: 'padding:15px',
            items: [
                {
                    xtype: 'container',
                    height: 51,
                    width: 501,
                    html: '<table width="468" height="42" border="0" cellspacing="0" style="font-size: 12px;color: #999999;"><tr><td width="95" height="21">温馨提示：</td><td width="357">&nbsp;</td></tr><tr><td height="26"></td><td> ' + cosog.string.requiredItem + '. </td></tr></table><div  class="divider_s"></div>'
                },
                {
                    xtype: 'container',
                    height: 10,
                    width: 501
                },
       oldPassword, newPassword, againPassword,
                {
                    xtype: 'textfield',
                    name: '',
                    //id: '',
                    hidden: true,
                    hideLabel: true
                },
                {
                    xtype: 'container',
                    height: 51,
                    width: 501,
                    html: '<table width="468" height="42" border="0" cellspacing="0" style="font-size: 12px;color: #999999;"><tr><td width="95" height="21"></td><td width="357">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp' + cosog.string.pwdPattern + '。</td></tr></table>'
                }
                ]
        });
        Ext.apply(me, {
            items: [sysreSetPasswordWinIdForm]
        });
        me.callParent(arguments);
    },
    buttons: [
        {
            id: "saveReSetPasswordSubmitFormBtnId",
            iconCls: 'save',
            text: cosog.string.sure,
            handler: function () {
                var resertForm = Ext.getCmp("sysreSetPasswordWinId_form_id");
                //var resertForm=Ext.getCmp("sysreSetPasswordWinId_new_password");
                // var resertForm=Ext.getCmp("sysreSetPasswordWinId_form_id");
                if (resertForm.getForm().isValid()) {

                    resertForm.getForm().submit({
                        url: context + '/userLoginManagerController/resetPwdmessage',
                        method: "POST",
                        waitMsg: cosog.string.updatewait,
                        waitTitle: 'Please Wait...',
                        params: {
                            oldPassword: Ext.getCmp('sysreSetPasswordWinId_old_password').getValue(),
                            newPassword: Ext.getCmp('sysreSetPasswordWinId_new_password').getValue()
                        },
                        success: function (response, action) {
                            if (action.result.flag == false) {
                                Ext.MessageBox.show({
                                    title: cosog.string.ts,
                                    msg: "<font color=red>" + cosog.string.sessionINvalid + "。</font>",
                                    icon: Ext.MessageBox.INFO,
                                    buttons: Ext.Msg.OK,
                                    fn: function () {
                                        Ext.getCmp("SysUserEditPwdWinId").close();
                                        window.location.href = context + "/login/toLogin";
                                    }
                                });

                            } else if (action.result.flag == true && action.result.error == false) {

                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + action.result.msg + "</font>");
                            } else if (action.result.flag == true && action.result.error == true) {
                                Ext.MessageBox.show({
                                    title: cosog.string.ts,
                                    msg: "<font color=red>" + action.result.msg + "</font>",
                                    icon: Ext.MessageBox.INFO,
                                    buttons: Ext.Msg.OK,
                                    fn: function () {
                                        Ext.getCmp("SysUserEditPwdWinId").close();
                                        window.location.href = " " + context + "/Login.jsp";
                                    }
                                });
                            } else {
                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + action.result.msg + "。</font>");
                            }
                        },
                        failure: function () {
                            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
                        }
                    });
                }
            }
        },
        {
            id: "cancelsysReSetPasswordSubmitFormBtnId",
            iconCls: 'cancel',
            text: cosog.string.cancel,
            handler: function () {
                Ext.getCmp("SysUserEditPwdWinId").close()
            }
        }

         ]
});