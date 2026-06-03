Ext.define("AP.view.orgAndUser.UserEditPasswordWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.userEditPasswordWindow',
    id: 'userEditPasswordWindow_Id',
    layout: 'fit',
    iframe: true,
    closeAction: 'destroy',
    width: 300,
    shadow: 'sides',
    resizable: true,
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
        var labelWidth=getLabelWidth(loginUserLanguageResource.userName,loginUserLanguage);
        if(labelWidth<getLabelWidth(loginUserLanguageResource.userAccount,loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.userAccount,loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.userPassword+'*',loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.userPassword+'*',loginUserLanguage);
        }
        if(labelWidth<getLabelWidth(loginUserLanguageResource.enterPasswordAgain+'*',loginUserLanguage)){
        	labelWidth=getLabelWidth(loginUserLanguageResource.enterPasswordAgain+'*',loginUserLanguage);
        }
        var postEditUserForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                    xtype: "hidden",
                    id: 'userEditPassword_UserNo_Id',
                    anchor: '100%',
                    name: "user.userNo"
            },{
                    fieldLabel: loginUserLanguageResource.userName,
                    labelWidth: labelWidth,
                    id: 'userEditPassword_UserName_Id',
                    anchor: '100%',
                    allowBlank: false,
                    editable: false,
                    name: "user.userName",
                    blankText: loginUserLanguageResource.required
            },{
                    fieldLabel: loginUserLanguageResource.userAccount,
                    labelWidth: labelWidth,
                    allowBlank: false,
                    editable: false,
                    anchor: '100%',
                    id: 'userEditPassword_UserId_Id',
                    name: "user.userId",
                    blankText: loginUserLanguageResource.required,
                    value: ''
            }, {
                    id: "userEditPassword_UserPwd_Id",
                    name: "user.userPwd",
                    inputType: 'password',
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.userPassword + '<font color=red>*</font>',
                    labelWidth: labelWidth,
                    emptyText: loginUserLanguageResource.enterPassword,
                    allowBlank: false,
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
            },{
                    id: "userEditPassword_UserPwdAgain_Id",
                    inputType: 'password',
                    emptyText: loginUserLanguageResource.enterPasswordAgain,
                    vtype: "password", // 自定义的验证类型  
                    vtypeText: loginUserLanguageResource.enterpwdNotEqual,
                    confirmTo: "userEditPassword_UserPwd_Id",
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.enterPasswordAgain + '<font color=red>*</font>',
                    labelWidth: labelWidth,
                    allowBlank: false,
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
            }],
            buttons: [{
                xtype: 'button',
                id: 'editUserPasswordBtn_Id',
                text: loginUserLanguageResource.update,
                width: 40,
                iconCls: 'edit',
                handler: EditUserPasswordSubmitBtnForm
            }, {
                text: loginUserLanguageResource.cancel,
                width: 40,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("userEditPasswordWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: postEditUserForm,
            listeners: {
    			afterrender: function ( panel, eOpts) {
    				var windowWidth =Ext.getCmp("userEditPasswordWindow_Id").getWidth();
    				if(labelWidth>windowWidth*0.5){
    					Ext.getCmp("userEditPasswordWindow_Id").setWidth(labelWidth*2.5);
    				}
    			}
    		}
        });
        me.callParent(arguments);
    }
});