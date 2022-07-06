Ext.define("AP.view.orgAndUser.UserEditPasswordWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.userEditPasswordWindow',
    id: 'userEditPasswordWindow_Id',
    layout: 'fit',
    iframe: true,
    closeAction: 'destroy',
    width: 300,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
    	Ext.apply(Ext.form.VTypes, {
            password: function (val, field) {
                if (field.confirmTo) {
                    var pwd = Ext.get(field.confirmTo);
                    var newpwd = Ext.getCmp(pwd.id).getValue();
                    return (val == newpwd);
                }

            }
        });
        var me = this;
        
        var postEditUserForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                    xtype: "hidden",
                    id: 'userEditPassword_UserNo_Id',
                    anchor: '100%',
                    name: "user.userNo"
            },{
                    fieldLabel: cosog.string.userName,
                    id: 'userEditPassword_UserName_Id',
                    anchor: '100%',
                    allowBlank: false,
                    editable: false,
                    name: "user.userName",
                    blankText: cosog.string.required
            },{
                    fieldLabel: cosog.string.userId,
                    allowBlank: false,
                    editable: false,
                    anchor: '100%',
                    id: 'userEditPassword_UserId_Id',
                    name: "user.userId",
                    blankText: cosog.string.required,
                    value: ''
            }, {
                    id: "userEditPassword_UserPwd_Id",
                    name: "user.userPwd",
                    inputType: 'password',
                    anchor: '100%',
                    fieldLabel: cosog.string.userPwd + '<font color=red>*</font>',
                    emptyText: cosog.string.enterpwd,
                    labelWidth: 100,
                    allowBlank: false,
                    msgTarget: 'side',
                    blankText: cosog.string.required
            },{
                    id: "userEditPassword_UserPwdAgain_Id",
                    inputType: 'password',
                    emptyText: cosog.string.enterNewPwdAgain,
                    vtype: "password", // 自定义的验证类型  
                    vtypeText: cosog.string.enterpwdNotEqual,
                    confirmTo: "userEditPassword_UserPwd_Id",
                    anchor: '100%',
                    fieldLabel: cosog.string.enterNewPwdAgain1 + '<font color=red>*</font>',
                    allowBlank: false,
                    labelWidth: 100,
                    msgTarget: 'side',
                    blankText: cosog.string.required
            }],
            buttons: [{
                xtype: 'button',
                id: 'editUserPasswordBtn_Id',
                text: '修改',
                width: 40,
                iconCls: 'edit',
                handler: EditUserPasswordSubmitBtnForm
            }, {
                text: '取消',
                width: 40,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("userEditPasswordWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: postEditUserForm
        });
        me.callParent(arguments);
    }
});