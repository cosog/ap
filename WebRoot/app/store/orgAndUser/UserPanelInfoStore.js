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
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "UserInfoGridPanel_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
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
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selections) {
                        },
                        itemdblclick: function () {
                            modifyUserInfo();
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