Ext.define("AP.view.orgAndUser.UserPanelInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.userPanelInfoWindow',
    id: 'user_addwin_Id',
    layout: 'fit',
    iframe: true,
    closeAction: 'destroy',
    width: 400,
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
        
        
        var labelWidth=getStringLength(loginUserLanguageResource.userName);
        if(labelWidth<getStringLength(loginUserLanguageResource.userAccount)){
        	labelWidth=getStringLength(loginUserLanguageResource.userAccount);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.userPassword)){
        	labelWidth=getStringLength(loginUserLanguageResource.userPassword);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.enterPasswordAgain)){
        	labelWidth=getStringLength(loginUserLanguageResource.enterPasswordAgain);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.role)){
        	labelWidth=getStringLength(loginUserLanguageResource.role);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.phone)){
        	labelWidth=getStringLength(loginUserLanguageResource.phone);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.email)){
        	labelWidth=getStringLength(loginUserLanguageResource.email);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.userQuickLogin)){
        	labelWidth=getStringLength(loginUserLanguageResource.userQuickLogin);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.receiveAlarmSMS)){
        	labelWidth=getStringLength(loginUserLanguageResource.receiveAlarmSMS);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.receiveAlarmMail)){
        	labelWidth=getStringLength(loginUserLanguageResource.receiveAlarmMail);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.language)){
        	labelWidth=getStringLength(loginUserLanguageResource.language);
        }
        if(labelWidth<getStringLength(loginUserLanguageResource.status)){
        	labelWidth=getStringLength(loginUserLanguageResource.status);
        }
        labelWidth=labelWidth*8;
        
        var roleComboxStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/userManagerController/loadUserType',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json'
                }
            },
            autoLoad: false
        });

        var roleCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: loginUserLanguageResource.role+'<font color=red>*</font>',
                id: 'userType_Id1',
                labelWidth: labelWidth,
                anchor: '100%',
                value: '',
                store: roleComboxStore,
                emptyText: '--'+loginUserLanguageResource.selectRole+'--',
                blankText: '--'+loginUserLanguageResource.selectRole+'--',
                typeAhead: true,
                allowBlank: false,
                blankText: loginUserLanguageResource.required,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("userType_Id").setValue(this.value);
                    }
                }
            });
        
        
        var languageComboxStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/userManagerController/loadLanguageList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json'
                }
            },
            autoLoad: false
        });

        var languageCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: loginUserLanguageResource.language+'<font color=red>*</font>',
                id: 'userLanguage_Id1',
                labelWidth: labelWidth,
                anchor: '100%',
                value: '',
                store: languageComboxStore,
                emptyText: '--'+loginUserLanguageResource.selectLanguage+'--',
                blankText: '--'+loginUserLanguageResource.selectLanguage+'--',
                typeAhead: true,
                allowBlank: false,
                blankText: loginUserLanguageResource.required,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("userLanguage_Id").setValue(this.value);
                    }
                }
            });
        
        var postEditUserForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                	xtype: 'label',
                	id: 'userWinOgLabel_Id',
                	hidden:true,
                	html: ''
            },{
                    xtype: "hidden",
                    id: 'userNo_Id',
                    anchor: '100%',
                    name: "user.userNo"
            }, {
                    xtype: "hidden",
                    id: 'userOrgid_Id',
                    anchor: '100%',
                    value: '0',
                    name: 'user.userOrgid'
            }, {
                    xtype: "hidden",
                    id: 'userType_Id',
                    anchor: '100%',
                    value: '',
                    name: 'user.userType'
            }, {
                xtype: "hidden",
                id: 'userLanguage_Id',
                anchor: '100%',
                value: '',
                name: 'user.language'
            },{
                    fieldLabel: loginUserLanguageResource.userName + '<font color=red>*</font>',
                    id: 'userName_Id',
                    labelWidth: labelWidth,
                    anchor: '100%',
                    allowBlank: false,
                    name: "user.userName",
                    blankText: loginUserLanguageResource.required
            },{
                    fieldLabel: loginUserLanguageResource.userAccount + '<font color=red>*</font>',
                    allowBlank: false,
                    labelWidth: labelWidth,
                    anchor: '100%',
                    id: 'userId_Id',
                    name: "user.userId",
                    blankText: loginUserLanguageResource.required,
                    value: '',
                    listeners: {
                        blur: function (t, e) {
                            var value_ = t.getValue();
                            var userNo=Ext.getCmp("userNo_Id").getValue();
                            Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                    userId: t.value,
                                    userNo: userNo
                                },
                                url: context + '/userManagerController/judgeUserExistOrNot',
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                        Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>【" + loginUserLanguageResource.userAccount + ":" + value_ + "】</font>" + loginUserLanguageResource.alreadyExist);
                                        t.setValue("");
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
                                }
                            });
                        }
                    }
            }, {
                    id: "userPwd_Id",
                    name: "user.userPwd",
                    inputType: 'password',
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.userPassword + '<font color=red>*</font>',
                    emptyText: loginUserLanguageResource.enterPassword,
                    labelWidth: labelWidth,
                    allowBlank: false,
                    msgTarget: 'side',
                    tpl:'aaaa',
                    blankText: loginUserLanguageResource.required
            },{
                    id: "userPwdAgain_Id",
                    inputType: 'password',
                    emptyText: loginUserLanguageResource.enterPasswordAgain,
                    vtype: "password", // 自定义的验证类型  
                    vtypeText: loginUserLanguageResource.enterpwdNotEqual,
                    confirmTo: "userPwd_Id",
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.enterPasswordAgain + '<font color=red>*</font>',
                    allowBlank: false,
                    labelWidth: labelWidth,
                    msgTarget: 'side',
                    blankText: loginUserLanguageResource.required
            },roleCombox,{
                    fieldLabel: loginUserLanguageResource.phone,
                    id: 'userPhone_Id',
                    labelWidth: labelWidth,
                    anchor: '100%',
                    name: "user.userPhone",
                    regex: /^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\d{8}$/,
                    regexText: loginUserLanguageResource.phoneNumberFormatError
            }, {
                    fieldLabel: loginUserLanguageResource.email,
                    id: 'userInEmail_Id',
                    labelWidth: labelWidth,
                    anchor: '100%',
                    regex: /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/,
                    regexText: loginUserLanguageResource.emailFormatError,
                    name: "user.userInEmail"
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : loginUserLanguageResource.userQuickLogin+'<font color=red>*</font>',
                defaultType: 'radiofield',
                labelWidth: labelWidth,
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:loginUserLanguageResource.yes,
                        name:'user.userQuickLogin',
                        inputValue: '1',
                        id: 'userQuickLoginRadio1_Id'
                    }, {
                        boxLabel: loginUserLanguageResource.no,
                        name:'user.userQuickLogin',
                        checked:true,
                        inputValue:'0',
                        id:'userQuickLoginRadio0_Id'
                    }
                ]
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : loginUserLanguageResource.receiveAlarmSMS+'<font color=red>*</font>',
                defaultType: 'radiofield',
                labelWidth: labelWidth,
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:loginUserLanguageResource.yes,
                        name:'user.receiveSMS',
                        inputValue: '1',
                        id: 'userReceiveSMSRadio1_Id'
                    }, {
                        boxLabel: loginUserLanguageResource.no,
                        name:'user.receiveSMS',
                        checked:true,
                        inputValue:'0',
                        id:'userReceiveSMSRadio0_Id'
                    }
                ]
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : loginUserLanguageResource.receiveAlarmMail+'<font color=red>*</font>',
                defaultType: 'radiofield',
                labelWidth: labelWidth,
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:loginUserLanguageResource.yes,
                        name:'user.receiveMail',
                        inputValue: '1',
                        id: 'userReceiveMailRadio1_Id'
                    }, {
                        boxLabel: loginUserLanguageResource.no,
                        name:'user.receiveMail',
                        checked:true,
                        inputValue:'0',
                        id:'userReceiveMailRadio0_Id'
                    }
                ]
            },languageCombox,{
            	xtype: 'fieldcontainer',
                fieldLabel : loginUserLanguageResource.status+'<font color=red>*</font>',
                defaultType: 'radiofield',
                id: 'userEnableRadioGroup_Id',
                labelWidth: labelWidth,
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:loginUserLanguageResource.enable,
                        name:'user.userEnable',
                        checked:true,
                        inputValue: '1',
                        id: 'userEnableRadio1_Id'
                    }, {
                        boxLabel:loginUserLanguageResource.disable,
                        name:'user.userEnable',
                        inputValue:'0',
                        id:'userEnableRadio0_Id'
                    }
                ]
            }],
            buttons: [{
                id: 'addFormUser_Id',
                xtype: 'button',
                text: loginUserLanguageResource.save,
                iconCls: 'save',
                handler: SaveUserDataInfoSubmitBtnForm
            }, {
                xtype: 'button',
                id: 'updateFormUser_Id',
                text: loginUserLanguageResource.update,
                width: 40,
                iconCls: 'edit',
                hidden: true,
                handler: UpdateUserDataInfoSubmitBtnForm
            }, {
                text: loginUserLanguageResource.cancel,
                width: 40,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("user_addwin_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: postEditUserForm
        });
        me.callParent(arguments);
    }
});