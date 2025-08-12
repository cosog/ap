Ext.define('AP.store.operationMaintenance.ImportBackupUserContentTreeInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.importBackupUserContentTreeInfoStore',
    model: 'AP.model.user.UserPanelInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/userManagerController/getUploadedUserTreeData',
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
            var gridPanel = Ext.getCmp("ImportBackupUserContentTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ImportBackupUserContentTreeGridPanel_Id",
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
                        header: loginUserLanguageResource.userName,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'userName',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.userAccount,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userId',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.role,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userTypeName',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.phone,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userPhone',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.email,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userInEmail',
                        flex:3
                    }, {
                        header: loginUserLanguageResource.userQuickLogin,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.userQuickLogin,loginUserLanguage)+'px',
                        dataIndex: 'userQuickLoginName',
                        listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	return false;
                    	    }
                    	}
                    }, {
                        header: loginUserLanguageResource.receiveSMS,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.receiveSMS,loginUserLanguage)+'px',
                        dataIndex: 'receiveSMSName',
                        listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	return false;
                    	    }
                    	}
                    }, {
                        header: loginUserLanguageResource.receiveMail,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.receiveMail,loginUserLanguage)+'px',
                        dataIndex: 'receiveMailName',
                        listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	return false;
                    	    }
                    	}
                    },{
                        header: loginUserLanguageResource.status,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userEnableName',
                        headerCheckbox: false,
                        width: getLabelWidth(loginUserLanguageResource.enable,loginUserLanguage)+'px',
                        listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	return false;
                    	    }
                    	}
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