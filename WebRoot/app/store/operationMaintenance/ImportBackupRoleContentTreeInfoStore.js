Ext.define('AP.store.operationMaintenance.ImportBackupRoleContentTreeInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.importBackupRoleContentTreeInfoStore',
    model: 'AP.model.role.RoleInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/getUploadedRoleTreeData',
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
        beforeload: function (store, options) {
        	var new_params = {
        			
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ImportBackupContentGridPanel_Id",
                    border: false,
                    stateful: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.idx,loginUserLanguage)+'px',
                        xtype: 'rownumberer'
                    }, {
                        header: loginUserLanguageResource.roleName,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleName'
                    }, {
                        header: loginUserLanguageResource.roleLevel,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleLevel'
                    }, {
                        header: loginUserLanguageResource.dataShowLevel,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'showLevel'
                    }, {
                        header: loginUserLanguageResource.roleVideoKeyEdit,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.roleVideoKeyEdit,loginUserLanguage)+'px',
                        dataIndex: 'roleVideoKeyEditName',
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	return false;
                    	    }
                    	}
                    }, {
                        header: loginUserLanguageResource.roleLanguageEdit,
                        xtype: 'checkcolumn',
                        hidden: true,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.roleLanguageEdit,loginUserLanguage)+'px',
                        dataIndex: 'roleLanguageEditName',
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	return false;
                    	    }
                    	}
                    },{
                        header: loginUserLanguageResource.roleRemark,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 3,
                        dataIndex: 'remark'
                    },{
                    	header: loginUserLanguageResource.collisionInfo,
                    	flex: 5,
                    	dataIndex: 'msg',
                    	align: 'center',
                    	renderer:function(value,o,p,e){
                    		return adviceImportBackupDataCollisionInfoColor(value,o,p,e);
                    	}
                    }],
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	
                        },
                        itemdblclick: function (grid, record, item, index, e, eOpts) {
                        	
                        },
                        itemclick: function (view,record,item,ndex,e,eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("OperationMaintenanceDataImportPanel_Id");
                panel.add(gridPanel);
            }
        }
    }
});