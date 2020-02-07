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
        var RoleTypeStore = new Ext.data.SimpleStore({
        	autoLoad : false,
            fields: ['roleFlag', 'roleFlagName'],
            data: [['0', '否'], ['1', '是']]
        });

        // Simple ComboBox using the data store
        var RoleTypeCombox = new Ext.form.ComboBox({
            id: 'roleFlagComboxfield_Id',
            value: 0,
            fieldLabel: '控制权限',
            typeAhead : true,
            allowBlank: false,
            autoSelect:true,
            editable:false,
			
            anchor: '100%',
            emptyText: '--请选择--',
            triggerAction: 'all',
            store: RoleTypeStore,
            displayField: 'roleFlagName',
            valueField: 'roleFlag',
            queryMode : 'local',
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("role_addwin_Id").down('form').getChildByElement("roleFlag_Id").setValue(record.data.roleFlag);
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
                fieldLabel: cosog.string.roleName,
                anchor: '100%',
                id: 'role_Name_Id',
                name: "role.roleName"
            }, {
                id: 'roleCode_Id',
                fieldLabel: cosog.string.roleCode,
                anchor: '100%',
                value: '',
                name: "role.roleCode"
            },RoleTypeCombox, {
                fieldLabel: '角色描述',
                id: 'roleRemark_Id',
                anchor: '100',
                xtype: 'textareafield',
                value: '',
                name: "role.remark"
            }
            //, RoleTypeCombox
            ],
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