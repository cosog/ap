Ext.define('AP.store.role.ImportRoleContentTreeInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.importRoleContentTreeInfoStore',
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
            var gridPanel = Ext.getCmp("ImportRoleContentTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ImportRoleContentTreeGridPanel_Id",
                    border: false,
                    stateful: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'>" + Ext.String.htmlEncode("<" + loginUserLanguageResource.emptyMsg + ">") + "</div>",
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
                        header: loginUserLanguageResource.language_zh_CN,
                        hidden:isExist(loginUserLanguageList,1)==0,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleName_zh_CN'
                    }, {
                        header: loginUserLanguageResource.language_en,
                        hidden:isExist(loginUserLanguageList,2)==0,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleName_en'
                    }, {
                        header: loginUserLanguageResource.language_ru,
                        hidden:isExist(loginUserLanguageList,3)==0,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleName_ru'
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
                        disabled:loginUserRoleManagerModuleRight.editFlag!=1,
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
                        disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                        width: getLabelWidth(loginUserLanguageResource.roleLanguageEdit,loginUserLanguage)+'px',
                        dataIndex: 'roleLanguageEditName',
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	return false;
                    	    }
                    	}
                    },{
                        header: loginUserLanguageResource.roleRemark,
                        hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 3,
                        dataIndex: 'remark_zh_CN'
                    },{
                        header: loginUserLanguageResource.roleRemark,
                        hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 3,
                        dataIndex: 'remark_en'
                    },{
                        header: loginUserLanguageResource.roleRemark,
                        hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 3,
                        dataIndex: 'remark_ru'
                    },{
                    	header: loginUserLanguageResource.collisionInfo,
                    	flex: 5,
                    	dataIndex: 'msg',
                    	align: 'center',
                    	renderer:function(value,o,p,e){
                    		return adviceImportRoleCollisionInfoColor(value,o,p,e);
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
                var panel = Ext.getCmp("importRoleTreePanel_Id");
                
                if(isNotVal(panel)){
                	panel.add(gridPanel);
                }
            }
        }
    }
});