Ext.define("AP.view.role.RoleInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.roleInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'role_addwin_Id',
    closeAction: 'destroy',
    width: 330,
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
        var RoleTypeCombox = new Ext.form.ComboBox({
            id: 'roleFlagComboxfield_Id',
            value: 0,
            fieldLabel: '设备控制权限<font color=red>*</font>',
            typeAhead : true,
            allowBlank: false,
            autoSelect:true,
            editable:false,
            anchor: '100%',
            emptyText: '--请选择--',
            triggerAction: 'all',
            store: new Ext.data.SimpleStore({
            	autoLoad : false,
                fields: ['roleFlag', 'roleFlagName'],
                data: [['0', '否'], ['1', '是']]
            }),
            displayField: 'roleFlagName',
            valueField: 'roleFlag',
            queryMode : 'local',
            listeners: {
            	select: function (v,o) {
					Ext.getCmp("roleFlag_Id").setValue(this.value);
                }
            }
        });
        
        var postroleEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '角色序号',
                id: 'role_Id',
                anchor: '100%',
                name: "role.roleId"
            }, {
                xtype: "hidden",
                name: 'role.roleFlag',
                id: 'roleFlag_Id',
                value: 0
            }, {
                fieldLabel: cosog.string.roleName+'<font color=red>*</font>',
                allowBlank: false,
                anchor: '100%',
                id: 'role_Name_Id',
                name: "role.roleName",
                listeners: {
                    blur: function (t, e) {
                        Ext.Ajax.request({
                            method: 'POST',
                            params: {
                            	roleName: t.value
                            },
                            url: context + '/roleManagerController/judgeRoleExistsOrNot',
                            success: function (response, opts) {
                                var obj = Ext.decode(response.responseText);
                                var msg_ = obj.msg;
                                if (msg_ == "1") {
                                    Ext.Msg.alert(cosog.string.ts, "<font color='red'>【角色:" + t.value + "】</font>" + cosog.string.exist + "！");
                                    t.setValue("");
                                }
                            },
                            failure: function (response, opts) {
                                Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                            }
                        });
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: "roleLevel_Id",
                name: 'role.roleLevel',
                fieldLabel: '角色等级<font color=red>*</font>',
                allowBlank: false,
                minValue: 1,
                value:1,
                anchor: '100%',
                msgTarget: 'side'
            },{
            	xtype: 'numberfield',
            	id: "roleShowLevel_Id",
                name: 'role.showLevel',
                fieldLabel: '数据显示级别<font color=red>*</font>',
                allowBlank: false,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            },RoleTypeCombox, {
                fieldLabel: '角色描述',
                id: 'roleRemark_Id',
                anchor: '100',
                xtype: 'textareafield',
                value: '',
                name: "role.remark"
            }],
            buttons: [{
                id: 'addFormrole_Id',
                xtype: 'button',
                iconCls: 'save',
                text: cosog.string.save,
                handler: SaveroleDataInfoSubmitBtnForm
         }, {
                xtype: 'button',
                id: 'updateFormrole_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: UpdateroleDataInfoSubmitBtnForm
         }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("role_addwin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: postroleEditForm
        });
        me.callParent(arguments);
    }

});