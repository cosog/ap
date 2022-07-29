Ext.define('AP.store.role.RoleInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.roleInfoStore',
    model: 'AP.model.role.RoleInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/doRoleShow',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: defaultPageSize,
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            
            var currentId=get_rawData.currentId;
            var currentLevel=get_rawData.currentLevel;
            var currentShowLevel=get_rawData.currentShowLevel;
            var currentFlag=get_rawData.currentFlag;
            
            Ext.getCmp("currentUserRoleId_Id").setValue(currentId);
            Ext.getCmp("currentUserRoleLevel_Id").setValue(currentLevel);
            Ext.getCmp("currentUserRoleShowLevel_Id").setValue(currentShowLevel);
            Ext.getCmp("currentUserRoleFlag_Id").setValue(currentFlag);
            
            var gridPanel = Ext.getCmp("RoleInfoGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createRoleGridColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RoleInfoGridPanel_Id",
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    border: false,
                    stateful: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    selType: 'checkboxmodel',
                    multiSelect: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: [{
                        header: '序号',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 50,
                        xtype: 'rownumberer'
                    }, {
                        header: '角色名称',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleName',
                        editor: {
                            allowBlank: false
                        },
                        renderer: function (value, o, p, e) {
                            return adviceCurrentRoleName(value, o, p, e);
                        }
                    }, {
                        header: '角色等级',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleLevel',
                        editor: {
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            minValue: currentLevel+1
                        }
                    }, {
                        header: '数据显示级别',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'showLevel',
                        editor: {
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            minValue: currentShowLevel+1
                        }
                    }, {
                        header: '设备控制权限',
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 85,
                        dataIndex: 'roleFlagName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	var currentId=Ext.getCmp("currentUserRoleId_Id").getValue();
                    	    	if(parseInt(record.data.roleId)==parseInt(currentId)){
                    	    		return false;
                    	    	}else{
                                    return true;
                                }
                    	    }
                    	}
                    },{
                        header: '角色描述',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 3,
                        dataIndex: 'remark',
                        editor: {
                        	allowBlank: true
                        },
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                            }
                        }
                    },{
                    	header: '保存',
                    	xtype: 'actioncolumn',
                    	width: 40,
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'submit',
                            tooltip: '保存',
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	updateRoleInfoByGridBtn(record);
                            }
                        }]
                    },{
                    	header: '删除',
                    	xtype: 'actioncolumn',
                    	width: 40,
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'delete',
                            tooltip: '删除角色',
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	delRoleInfoByGridBtn(record);
                            }
                        }]
                    }],
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	var roleId='';
                        	var roleCode='';
                        	if(selected.length>0){
                        		roleId = selected[0].data.roleId;
                        		roleCode = selected[0].data.roleCode;
                        	}
                            Ext.getCmp("RightBottomRoleCodes_Id").setValue(roleId);
                            Ext.getCmp("RightModuleTreeInfoGridPanel_Id").getStore().load();
                        },
                        celldblclick: function ( grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	var currentId=Ext.getCmp("currentUserRoleId_Id").getValue();
                            var currentLevel=Ext.getCmp("currentUserRoleLevel_Id").getValue();
                            var currentShowLevel=Ext.getCmp("currentUserRoleShowLevel_Id").getValue();
                            var currentFlag=Ext.getCmp("currentUserRoleFlag_Id").getValue();
                        	
                        	
                        	var record = grid.getStore().getAt(rowIndex);
                            var dataIndex=grid.getHeaderAtIndex(cellIndex).dataIndex;
                            if (parseInt(record.data.roleId)==parseInt(currentId) && ( dataIndex.toUpperCase()=='roleLevel'.toUpperCase() || dataIndex.toUpperCase()=='showLevel'.toUpperCase() || dataIndex.toUpperCase()=='roleFlagName'.toUpperCase()  )) {
                                return false;
                            }else if(parseInt(currentFlag)==0 && dataIndex.toUpperCase()=='roleFlagName'.toUpperCase()){
                            	return false;
                            } else {
                                return true;
                            }
                        }
                    }
                });
                var panel = Ext.getCmp("RoleInfoGridPanelView_id");
                panel.add(gridPanel);
            }
            
        },
        beforeload: function (store, options) {
            var RoleName_Id = Ext.getCmp('RoleName_Id');

            if (!Ext.isEmpty(RoleName_Id)) {
                RoleName_Id = RoleName_Id.getValue();
            }
            var new_params = {
                roleName: RoleName_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});