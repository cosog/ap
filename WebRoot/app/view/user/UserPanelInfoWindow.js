Ext.define("AP.view.user.UserPanelInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.userPanelInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'user_addwin_Id',
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
        var me = this;
        var userTitleStore_A = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
                url: context + '/userManagerController/loadUserTitleType',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var new_params = {
                        type: 'yhzc'
                    };
                    Ext.apply(store.proxy.extraParams,
                        new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var UserTitleCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.userTitle,
                id: 'userTitle_Id1',
                anchor: '100%',
                store: userTitleStore_A,
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                value: '0',
                typeAhead: true,
                hidden:true,
                allowBlank: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("userTitle_Id").setValue(this.value);
                    }
                }
            });
        var UserTypeStore_A = new Ext.data.SimpleStore({
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
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var new_params = {
                        type: 'yhlx'
                    };
                    Ext.apply(store.proxy.extraParams,
                        new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var UserTypeCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: '角色',
                id: 'userType_Id1',
                anchor: '100%',
                value: '',
                store: UserTypeStore_A,
                emptyText: '--请选择角色--',
                blankText: '--请选择角色--',
                typeAhead: true,
                allowBlank: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("userType_Id").setValue(this.value);
                    }
                }
            });
        /**下拉机构数*/
        var OrgTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['orgId', 'text', 'leaf'],
            autoLoad: false,
            proxy: {
                type: 'ajax',
                url: context + '/orgManagerController/constructOrgRightTree',
                reader: 'json'
            },
            root: {
                expanded: true,
                text: 'orgName'
            },
            listeners: {
            	beforeload: function (store, options) {
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var org_name_Id = Ext.getCmp('org_name_Id');
                    if (!Ext.isEmpty(org_name_Id)) {
                        org_name_Id = org_name_Id.getValue();
                    }
                    var new_params = {
                    	orgId:orgId,
                        orgName: org_name_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var xltree=Ext.create('AP.view.well.TreePicker',{
        	id:'userOrgid_Id1',
        	fieldLabel: cosog.string.orgName,
            emptyText: cosog.string.chooseOrg,
            blankText: cosog.string.chooseOrg,
            anchor: '100%',
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:OrgTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("user_addwin_Id").down('form').getChildByElement("userOrgid_Id").setValue(record.data.orgId);
                }
            }
        });
        
        var quidkLoginStore = new Ext.data.SimpleStore({
        	autoLoad : false,
            fields: ['userQuickLogin', 'userQuickLoginName'],
            data: [['0', '否'], ['1', '是']]
        });
        var quidkLoginComboxsimp = new Ext.form.ComboBox({
            id: 'userQuidkLoginComboxfield_Id',
            value: '0',
            fieldLabel: '快捷登录',
            typeAhead : true,
            allowBlank: false,
            autoSelect:true,
            editable:false,
			
            anchor: '100%',
            emptyText: '--请选择--',
            triggerAction: 'all',
            store: quidkLoginStore,
            displayField: 'userQuickLoginName',
            valueField: 'userQuickLogin',
            queryMode : 'local',
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("user_addwin_Id").down('form').getChildByElement("userQuidkLoginValue_Id").setValue(record.data.userQuickLogin);
                }
            }
        });
        
        var postEditUserForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [
                    {
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
         },
                {
                    xtype: "hidden",
                    id: 'userType_Id',
                    anchor: '100%',
                    value: '',
                    name: 'user.userType'
         }, {
                    xtype: "hidden",
                    value: '0',
                    anchor: '100%',
                    id: 'userTitle_Id',
                    name: 'user.userTitle'
         },xltree,UserTypeCombox,{
                    fieldLabel: cosog.string.userName,
                    id: 'userName_Id',
                    anchor: '100%',
                    allowBlank: false,
                    name: "user.userName"
         }
         , {
                    fieldLabel: cosog.string.userId,
                    //minLength : 2,
                    allowBlank: false,
                    anchor: '100%',
                    //minLengthText : '您输入的用户名称太短',
                    id: 'userId_Id',
                    name: "user.userId",
                    value:'',
                    listeners: {
                        blur: function (t, e) {
                            var value_ = t.getValue();
                            // 检索动态列表头及内容信息
                            Ext.Ajax.request({
                                method: 'POST',
                                params: {
                                    userId: t.value
                                },
                                url: context + '/userManagerController/judgeUserExistOrNot',
                                success: function (response, opts) {
                                    // 处理后json
                                    var obj = Ext.decode(response.responseText);
                                    var msg_ = obj.msg;
                                    if (msg_ == "1") {
                                        Ext.Msg.alert(cosog.string.ts, "<font color='red'>【" + cosog.string.userId + ":" + value_ + "】</font>" + cosog.string.exist + "！");
                                        t.setValue("");
                                    }
                                    // ==end
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                                }
                            });
                        }
                    }
         }, {
                    inputType: 'password',
                    id: 'userPwd_Id',
                    anchor: '100%',
                    value:'',
                    allowBlank: false,
                    fieldLabel: cosog.string.userPwd,
                    name: "user.userPwd"
         },quidkLoginComboxsimp,
         {
             	xtype: "hidden",
             	id: 'userQuidkLoginValue_Id',
             	anchor: '100%',
             	value: 0,
             	name: "user.userQuickLogin",
         },{
                    xtype: "hidden",
                    id: 'userHiddenPwd_Id',
                    fieldLabel: "用户隐藏密码",
                    anchor: '100%',
                    name: "userPass"
         } , {
                    fieldLabel: cosog.string.userPhone,
                    id: 'userPhone_Id',
                    anchor: '100%',
                    name: "user.userPhone"
         },{
                    fieldLabel: cosog.string.userInEmail,
                    id: 'userInEmail_Id',
                    anchor: '100%',
                    //vtype : 'email',
                    //vtypeText : '您输入的邮箱格式不正确',
                    name: "user.userInEmail"
         },UserTitleCombox, {
                    xtype:'datetimefield',
                    id: 'userRegTime_Id',
                    format: 'Y-m-d H:i:s',
                    anchor: '100%',
                    fieldLabel: cosog.string.userRegTime,
                    value: new Date(),
                    name: "user.userRegtime"
         }
         ],
            buttons: [{
                id: 'addFormUser_Id',
                xtype: 'button',
                text: '保存',
                iconCls: 'save',
                handler: SaveDataInfoSubmitBtnForm
         }, {
                xtype: 'button',
                id: 'updateFormUser_Id',
                text: '修改',
                width: 40,
                iconCls: 'edit',
                hidden: true,
                handler: UpdateDataInfoSubmitBtnForm
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