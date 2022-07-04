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
                //分页工具栏
                var bbar = new Ext.PageNumberToolbar({
                    store: store,
                    pageSize: defaultPageSize,
                    displayInfo: true,
//                    displayMsg: '当前记录 {0} -- {1} 条 共 {2} 条记录',
                    displayMsg: '当前 {0}~{1}条  共 {2} 条',
                    emptyMsg: "没有记录可显示",
                    prevText: "上一页",
                    nextText: "下一页",
                    refreshText: "刷新",
                    lastText: "最后页",
                    firstText: "第一页",
                    beforePageText: "当前页",
                    afterPageText: "共{0}页"
                });
//                var rowEditing = Ext.create('Ext.grid.RowEditor', {
//                	    clicksToMoveEditor: 1,
//                	    autoCancel: false
//                	});
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "UserInfoGridPanel_Id",
                    selModel: 'rowmodel',
                    plugins: [{
                        ptype: 'rowediting',
                        clicksToEdit: 2
                    }],
                    border: false,
                    stateful: true,
//                    autoScroll: true,
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
//                    bbar: bbar,
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
                        editor: {
                            allowBlank: false
                        },
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '角色',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userTypeName',
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '电话',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userPhone',
                        editor: {
                        	regex: /^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\d{8}$/,
                        	allowBlank: true
                        },
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '邮箱',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userInEmail',
                        editor: {
                        	vtype: 'email',
                            regex: /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/,
                            allowBlank: true
                        },
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '快捷登录',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userQuickLoginName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '接收报警短信',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'receiveSMSName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '接收报警邮件',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'receiveMailName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '状态',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'userEnableName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '隶属单位',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 300,
                        dataIndex: 'allPath',
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: '创建时间',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 150,
                        dataIndex: 'userRegtime',
                        renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
                    }],
                    listeners: {
//                        selectionchange: function (sm, selections) {
//                        },
//                        itemdblclick: function () {
////                            modifyUserInfo();
//                        }
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