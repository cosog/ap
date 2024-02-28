Ext.define("AP.view.role.RoleInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.roleInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'role_addwin_Id',
    closeAction: 'destroy',
    width: 600,
    minWidth: 600,
    height: 600,
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
        
        var moduleStore = Ext.create("AP.store.role.RightModuleTreeInfoStore");
        var tabStore = Ext.create("AP.store.role.RightTabTreeInfoStore");
        
        var RoleTypeCombox = new Ext.form.ComboBox({
            id: 'roleFlagComboxfield_Id',
            value: 0,
            fieldLabel: '设备控制权限<font color=red>*</font>',
            labelWidth: 110,
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
        
        var RoleReportEditCombox = new Ext.form.ComboBox({
            id: 'roleReportEditComboxfield_Id',
            value: 0,
            fieldLabel: '报表编辑权限<font color=red>*</font>',
            labelWidth: 110,
            typeAhead : true,
            allowBlank: false,
            autoSelect:true,
            editable:false,
            anchor: '100%',
            emptyText: '--请选择--',
            triggerAction: 'all',
            store: new Ext.data.SimpleStore({
            	autoLoad : false,
                fields: ['roleReportEdit', 'roleReportEditName'],
                data: [['0', '否'], ['1', '是']]
            }),
            displayField: 'roleReportEditName',
            valueField: 'roleReportEdit',
            queryMode : 'local',
            listeners: {
            	select: function (v,o) {
					Ext.getCmp("roleReportEdit_Id").setValue(this.value);
                }
            }
        });
        
        var RoleVideoKeyEditCombox = new Ext.form.ComboBox({
            id: 'roleVideoKeyEditComboxfield_Id',
            value: 0,
            fieldLabel: '视频密钥编辑权限<font color=red>*</font>',
            labelWidth: 110,
            typeAhead : true,
            allowBlank: false,
            autoSelect:true,
            editable:false,
            anchor: '100%',
            emptyText: '--请选择--',
            triggerAction: 'all',
            store: new Ext.data.SimpleStore({
            	autoLoad : false,
                fields: ['roleVideoKeyEdit', 'roleVideoKeyEditName'],
                data: [['0', '否'], ['1', '是']]
            }),
            displayField: 'roleVideoKeyEditName',
            valueField: 'roleVideoKeyEdit',
            queryMode : 'local',
            listeners: {
            	select: function (v,o) {
					Ext.getCmp("roleVideoKeyEdit_Id").setValue(this.value);
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
                xtype: "hidden",
                name: 'role.roleReportEdit',
                id: 'roleReportEdit_Id',
                value: 0
            }, {
                xtype: "hidden",
                name: 'role.roleVideoKeyEdit',
                id: 'roleVideoKeyEdit_Id',
                value: 0
            }, {
                fieldLabel: cosog.string.roleName+'<font color=red>*</font>',
                labelWidth: 110,
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
                labelWidth: 110,
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
                labelWidth: 110,
                allowBlank: false,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            },RoleTypeCombox,RoleReportEditCombox,RoleVideoKeyEditCombox, {
                fieldLabel: '角色描述',
                labelWidth: 110,
                id: 'roleRemark_Id',
                anchor: '100',
                xtype: 'textareafield',
                value: '',
                name: "role.remark"
            }]
        });
        Ext.apply(me, {
        	bbar:['->',{
                id: 'addFormrole_Id',
                xtype: 'button',
                iconCls: 'save',
                text: cosog.string.save,
                handler: SaveroleDataInfoSubmitBtnForm
            },{
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("role_addwin_Id").close();
                }
            }],
        	layout: 'border',
            items: [{
            	region: 'center',
        		title:'角色基础信息',
        		layout: 'fit',
        		layout: 'fit',
        	    iframe: true,
        	    shadow: 'sides',
        	    resizable: false,
        	    collapsible: false,
        	    constrain: true,
        	    maximizable: false,
        	    plain: true,
        	    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
        	    modal: true,
        	    border: true,
        		items: postroleEditForm
            },{
            	region: 'east',
        		width: '50%',
        		layout: 'border',
        		header:false,
        		border: true,
        		items:[{
        			region:'center',
        			layout: "fit",
        			title:'模块授权',
            		split: true,
                    collapsible: false,
                    border: false,
                	xtype:'treepanel',
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    id: "RoleInfoWindowRightModuleTreeInfoGridPanel_Id", // 模块编码加id，定义的命名规则moduleCode是从库里取的值对应
                    store: moduleStore,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '模块列表',
                    	flex: 8,
                    	align: 'left',
                    	dataIndex: 'text'
                    },{
                    	header: 'mdIdaa',
                    	hidden: true,
                    	dataIndex: 'mdId'
                    }],
                    listeners: {
                        checkchange: function (node, checked) {
                            listenerCheck(node, checked);
                        }
                    }
        		},{
        			region:'south',
        			height:'30%',
        			title:'标签授权',
        			split: true,
                    collapsible: true,
                    border: false,
            		layout: "fit",
                	xtype:'treepanel',
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    id: "RoleInfoWindowRightTabTreeInfoGridPanel_Id", // 模块编码加id，定义的命名规则moduleCode是从库里取的值对应
                    store: tabStore,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '标签列表',
                    	flex: 8,
                    	align: 'left',
                    	dataIndex: 'text'
                    },{
                    	header: 'tabIdaa',
                    	hidden: true,
                    	dataIndex: 'tabId'
                    }],
                    listeners: {
                        checkchange: function (node, checked) {
                            listenerCheck(node, checked);
                        }
                    }
        		
        		}]
            }]
        });
        me.callParent(arguments);
    }

});