Ext.define("AP.view.orgAndUser.UserPanelInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.userPanelInfoWindow',
    id: 'user_addwin_Id',
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
                fieldLabel: '角色<font color=red>*</font>',
                id: 'userType_Id1',
                anchor: '100%',
                value: '',
                store: roleComboxStore,
                emptyText: '--请选择角色--',
                blankText: '--请选择角色--',
                typeAhead: true,
                allowBlank: false,
                blankText: cosog.string.required,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("userType_Id").setValue(this.value);
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
            },{
                    fieldLabel: cosog.string.userName + '<font color=red>*</font>',
                    id: 'userName_Id',
                    anchor: '100%',
                    allowBlank: false,
                    name: "user.userName",
                    blankText: cosog.string.required
            },{
                    fieldLabel: cosog.string.userId + '<font color=red>*</font>',
                    allowBlank: false,
                    anchor: '100%',
                    id: 'userId_Id',
                    name: "user.userId",
                    blankText: cosog.string.required,
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
                                        Ext.Msg.alert(cosog.string.ts, "<font color='red'>【" + cosog.string.userId + ":" + value_ + "】</font>" + cosog.string.exist + "！");
                                        t.setValue("");
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                                }
                            });
                        }
                    }
            }, {
                    id: "userPwd_Id",
                    name: "user.userPwd",
                    inputType: 'password',
                    anchor: '100%',
                    fieldLabel: cosog.string.userPwd + '<font color=red>*</font>',
                    emptyText: cosog.string.enterpwd,
                    labelWidth: 100,
                    allowBlank: false,
                    msgTarget: 'side',
                    tpl:'aaaa',
                    blankText: cosog.string.required
            },{
                    id: "userPwdAgain_Id",
                    inputType: 'password',
                    emptyText: cosog.string.enterNewPwdAgain,
                    vtype: "password", // 自定义的验证类型  
                    vtypeText: cosog.string.enterpwdNotEqual,
                    confirmTo: "userPwd_Id",
                    anchor: '100%',
                    fieldLabel: cosog.string.enterNewPwdAgain1 + '<font color=red>*</font>',
                    allowBlank: false,
                    labelWidth: 100,
                    msgTarget: 'side',
                    blankText: cosog.string.required
            },roleCombox,{
                    fieldLabel: '电话',
                    id: 'userPhone_Id',
                    anchor: '100%',
                    name: "user.userPhone",
                    regex: /^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\d{8}$/,
                    regexText: '您输入的手机号码格式不正确'
            }, {
                    fieldLabel: '邮箱',
                    id: 'userInEmail_Id',
                    anchor: '100%',
                    regex: /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/,
                    regexText: '您输入的邮箱格式不正确',
                    name: "user.userInEmail"
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : '快捷登录<font color=red>*</font>',
                defaultType: 'radiofield',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'是',
                        name:'user.userQuickLogin',
                        inputValue: '1',
                        id: 'userQuickLoginRadio1_Id'
                    }, {
                        boxLabel: '否',
                        name:'user.userQuickLogin',
                        checked:true,
                        inputValue:'0',
                        id:'userQuickLoginRadio0_Id'
                    }
                ]
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : '接收报警短信<font color=red>*</font>',
                defaultType: 'radiofield',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'是',
                        name:'user.receiveSMS',
                        inputValue: '1',
                        id: 'userReceiveSMSRadio1_Id'
                    }, {
                        boxLabel: '否',
                        name:'user.receiveSMS',
                        checked:true,
                        inputValue:'0',
                        id:'userReceiveSMSRadio0_Id'
                    }
                ]
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : '接收报警邮件<font color=red>*</font>',
                defaultType: 'radiofield',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'是',
                        name:'user.receiveMail',
                        inputValue: '1',
                        id: 'userReceiveMailRadio1_Id'
                    }, {
                        boxLabel: '否',
                        name:'user.receiveMail',
                        checked:true,
                        inputValue:'0',
                        id:'userReceiveMailRadio0_Id'
                    }
                ]
            },{
            	xtype: 'fieldcontainer',
                fieldLabel : '状态<font color=red>*</font>',
                defaultType: 'radiofield',
                id: 'userEnableRadioGroup_Id',
                anchor: '100%',
                defaults: {
                    flex: 1
                },
                layout: 'hbox',
                items: [
                    {
                        boxLabel:'使能',
                        name:'user.userEnable',
                        checked:true,
                        inputValue: '1',
                        id: 'userEnableRadio1_Id'
                    }, {
                        boxLabel:'失效',
                        name:'user.userEnable',
                        inputValue:'0',
                        id:'userEnableRadio0_Id'
                    }
                ]
            }, {
                xtype: 'datetimefield',
                id: 'userRegTime_Id',
                format: 'Y-m-d H:i:s',
                anchor: '100%',
                hidden:true,
                fieldLabel: cosog.string.userRegTime,
                value: new Date(),
                name: "user.userRegtime"
            }],
            buttons: [{
                id: 'addFormUser_Id',
                xtype: 'button',
                text: '保存',
                iconCls: 'save',
                handler: SaveUserDataInfoSubmitBtnForm
            }, {
                xtype: 'button',
                id: 'updateFormUser_Id',
                text: '修改',
                width: 40,
                iconCls: 'edit',
                hidden: true,
                handler: UpdateUserDataInfoSubmitBtnForm
            }, {
                text: '取消',
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