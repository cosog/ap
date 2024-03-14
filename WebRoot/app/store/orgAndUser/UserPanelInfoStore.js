Ext.define('AP.store.orgAndUser.UserPanelInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.userPanelInfoStore',
    model: 'AP.model.user.UserPanelInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/userManagerController/doUserShow',
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
            var gridPanel = Ext.getCmp("UserInfoGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createUserGridColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "UserInfoGridPanel_Id",
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
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
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
                        header: '用户名称',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'userName',
                        flex:2,
                        editor: {
                            allowBlank: false
                        },
                        renderer: function (value, o, p, e) {
                            return adviceCurrentUserName(value, o, p, e);
                        }
                    }, {
                        header: '用户账号',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userId',
                        flex:2,
                        editor: {
                            allowBlank: false
                        },
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    }, {
                        header: '角色',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userTypeName',
                        flex:2,
                        editor: {
                            xtype: 'combo',
                            typeAhead: true,
                            triggerAction: 'all',
                            allowBlank: false,
                            editable: false,
                            store: get_rawData.roleList
                        },
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '电话',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userPhone',
                        flex:2,
                        editor: {
                        	regex: /^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\d{8}$/,
                        	allowBlank: true
                        },
                        renderer: function (value) {
                            if(isNotVal(value)){
                            	return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? " " : value) + "</span>";
                            }
                        }
                    }, {
                        header: '邮箱',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userInEmail',
                        flex:3,
                        editor: {
                        	vtype: 'email',
                            regex: /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/,
                            allowBlank: true
                        },
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    }, {
                        header: '快捷登录',
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 65,
                        dataIndex: 'userQuickLoginName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        }
                    }, {
                        header: '接收短信',
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 65,
                        dataIndex: 'receiveSMSName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        }
                    }, {
                        header: '接收邮件',
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 65,
                        dataIndex: 'receiveMailName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        }
                    }, {
                        header: '使能',
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userEnableName',
                        headerCheckbox: false,
                        width: 40,
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	if(rowIndex==0){
                    	    		return false;
                    	    	}else{
                                    return true;
                                }
                    	    }
                    	}
                    }, {
                        header: '隶属组织',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex:2,
                        dataIndex: 'allPath',
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    }, {
                        header: '创建时间',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex:2,
                        dataIndex: 'userRegtime',
                        renderer:function(value,o,p,e){
                        	return adviceTimeFormat(value,o,p,e);
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
                            	updateUserInfoByGridBtn(record);
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
                            tooltip: '删除用户',
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	delUserInfoByGridBtn(record);
                            }
                        }]
                    }],
                    listeners: {
                    	celldblclick : function( grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                    		var record = grid.getStore().getAt(rowIndex);
                            var dataIndex=grid.getHeaderAtIndex(cellIndex).dataIndex;
                            if (rowIndex==0 && ( dataIndex.toUpperCase()=='userId'.toUpperCase() || dataIndex.toUpperCase()=='userTypeName'.toUpperCase() || dataIndex.toUpperCase()=='userEnableName'.toUpperCase()  )) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                });
                var panel = Ext.getCmp("OrgAndUserUserInfoPanel_Id");
                panel.add(gridPanel);
            }

        },
        beforeload: function (store, options) {
        	var selectedOrgId="";
        	var orgTreeSelection = Ext.getCmp("OrgInfoTreeGridView_Id").getSelectionModel().getSelection();
        	if (orgTreeSelection.length > 0) {
        		selectedOrgId=foreachAndSearchOrgChildId(orgTreeSelection[0]);
        	}
        	var UserName_Id = Ext.getCmp('UserName_Id').getValue();
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            if(selectedOrgId==""){
            	selectedOrgId=Ext.getCmp('leftOrg_Id').getValue();
            }
            var new_params = {
            	orgId: selectedOrgId,
            	userName: UserName_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
//生成Grid-Fields 动态创建报警信息查询的columns
createUserHeadColumn = function (columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        myColumns += "{ header:'" + attr.header + "'";
        if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable:false,align:'center'";
        } else {
            myColumns += ",dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};