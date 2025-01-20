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
        
        var RoleVideoKeyEditCombox = new Ext.form.ComboBox({
            id: 'roleVideoKeyEditComboxfield_Id',
            value: 0,
            fieldLabel: loginUserLanguageResource.roleVideoKeyEdit+'<font color=red>*</font>',
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
                data: [['0', loginUserLanguageResource.no], ['1', loginUserLanguageResource.yes]]
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
        
        var RoleLanguageEditCombox = new Ext.form.ComboBox({
            id: 'roleLanguageEditComboxfield_Id',
            value: 0,
            fieldLabel: loginUserLanguageResource.roleLanguageEdit+'<font color=red>*</font>',
            labelWidth: 110,
            typeAhead : true,
            hidden: true,
            allowBlank: false,
            autoSelect:true,
            editable:false,
            anchor: '100%',
            emptyText: '--请选择--',
            triggerAction: 'all',
            store: new Ext.data.SimpleStore({
            	autoLoad : false,
                fields: ['roleLanguageEdit', 'roleLanguageEditName'],
                data: [['0', loginUserLanguageResource.no], ['1', loginUserLanguageResource.yes]]
            }),
            displayField: 'roleLanguageEditName',
            valueField: 'roleLanguageEdit',
            queryMode : 'local',
            listeners: {
            	select: function (v,o) {
					Ext.getCmp("roleLanguageEdit_Id").setValue(this.value);
                }
            }
        });
        
        var postroleEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: loginUserLanguageResource.idx,
                id: 'role_Id',
                anchor: '100%',
                name: "role.roleId"
            }, {
                xtype: "hidden",
                name: 'role.roleVideoKeyEdit',
                id: 'roleVideoKeyEdit_Id',
                value: 0
            }, {
                xtype: "hidden",
                name: 'role.roleLanguageEdit',
                id: 'roleLanguageEdit_Id',
                value: 0
            }, {
                fieldLabel: loginUserLanguageResource.roleName+'<font color=red>*</font>',
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
                                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color='red'>【"+loginUserLanguageResource.role+":" + t.value + "】</font>" + loginUserLanguageResource.exist);
                                    t.setValue("");
                                }
                            },
                            failure: function (response, opts) {
                                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.dataQueryFailure);
                            }
                        });
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: "roleLevel_Id",
                name: 'role.roleLevel',
                fieldLabel: loginUserLanguageResource.roleLevel+'<font color=red>*</font>',
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
                fieldLabel: loginUserLanguageResource.dataShowLevel+'<font color=red>*</font>',
                labelWidth: 110,
                allowBlank: false,
                minValue: 1,
                anchor: '100%',
                msgTarget: 'side'
            },RoleVideoKeyEditCombox, RoleLanguageEditCombox,{
                fieldLabel: loginUserLanguageResource.roleRemark,
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
                text: loginUserLanguageResource.save,
                handler: SaveroleDataInfoSubmitBtnForm
            },{
                text: loginUserLanguageResource.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("role_addwin_Id").close();
                }
            }],
        	layout: 'border',
            items: [{
            	region: 'center',
        		title:loginUserLanguageResource.roleInfo,
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
        			title:loginUserLanguageResource.moduleLicense,
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
                    viewConfig: {　　
                    	markDirty: false//编辑不显示红色三角标志
                    },
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: loginUserLanguageResource.moduleList,
                    	flex: 8,
                    	align: 'left',
                    	dataIndex: 'text'
                    },{
                    	header: 'mdIdaa',
                    	hidden: true,
                    	dataIndex: 'mdId'
                    }, {
                        header: loginUserLanguageResource.viewFlag,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'viewFlagName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        renderer: function (value, metaData, record, rowIdx, colIdx, store, view) {
                            var rn=true;
                            var loginUserRoleModules=getLoginUserRoleModules();
                            var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
                            if(RoleManagerModuleEditFlag==1){
                	    		for(var i=0;i<loginUserRoleModules.length;i++){
                	    			if(loginUserRoleModules[i].mdCode==record.data.mdCode){
                	    				if(loginUserRoleModules[i].viewFlag!=1){
                	    					rn= false;
                	    				}
                	    				break;
                	    			}
                	    		}
                            }else{
                            	rn= false;
                            }
                            return rn ? this.defaultRenderer(value, metaData):'';
                        }
                    }, {
                        header: loginUserLanguageResource.editFlag,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'editFlagName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        renderer: function (value, metaData, record, rowIdx, colIdx, store, view) {
                            var rn=true;
                            var loginUserRoleModules=getLoginUserRoleModules();
                	    	var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
                            if(RoleManagerModuleEditFlag==1){
                            	if(!record.isLeaf()){
                            		rn= false;
                    	    	}else{
                    	    		rn=true;
                    	    		for(var i=0;i<loginUserRoleModules.length;i++){
                    	    			if(loginUserRoleModules[i].mdCode==record.data.mdCode){
                    	    				if(loginUserRoleModules[i].editFlag!=1){
                    	    					rn= false;
                    	    				}
                    	    				break;
                    	    			}
                    	    		}
                    	    	}
                            }else{
                            	rn= false;
                            }
                            return rn ? this.defaultRenderer(value, metaData):'';
                        }
                    }, {
                        header: loginUserLanguageResource.controlFlag,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'controlFlagName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        renderer: function (value, metaData, record, rowIdx, colIdx, store, view) {
                            var rn=true;
                            var loginUserRoleModules=getLoginUserRoleModules();
                            var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
                            if(RoleManagerModuleEditFlag==1){
                            	if(record.data.mdCode.toUpperCase()!='DeviceRealTimeMonitoring'.toUpperCase()){
                            		rn= false;
                    	    	}else{
                    	    		rn=true;
                    	    		for(var i=0;i<loginUserRoleModules.length;i++){
                    	    			if(loginUserRoleModules[i].mdCode==record.data.mdCode){
                    	    				if(loginUserRoleModules[i].controlFlag!=1){
                    	    					rn= false;
                    	    				}
                    	    				break;
                    	    			}
                    	    		}
                    	    	}
                            }else{
                            	rn= false;
                            }
                            return rn ? this.defaultRenderer(value, metaData):'';
                        }
                    }],
                    listeners: {
                        checkchange: function (node, checked) {
                            listenerCheck(node, checked);
                        }
                    }
        		},{
        			region:'south',
        			height:'30%',
        			title:loginUserLanguageResource.deviceTypeLicense,
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
                    	text: loginUserLanguageResource.deviceType,
                    	flex: 8,
                    	align: 'left',
                    	dataIndex: 'text'
                    },{
                    	header: 'deviceTypeIdaa',
                    	hidden: true,
                    	dataIndex: 'deviceTypeId'
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