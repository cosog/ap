Ext.define('AP.store.user.UserMuchHeadInfoStore', {
    extend: 'Ext.data.Store',
    id: "UserMuchHeadInfoStore_ids",
    alias: 'widget.userMuchHeadInfoStore',
    model: 'AP.model.user.UserPanelInfoModel',
    autoLoad: true,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/monitorDataController/showDemo',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: 100000,
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
            var arrColumns = get_rawData.columns;
            var UserMuchHeadGrid_Id = Ext.getCmp("UserMuchHeadGrid_Id");
            if (!isNotVal(UserMuchHeadGrid_Id)) {
                var columnMeta = createMuchHeadColumn(arrColumns);
                var newColumns = Ext.JSON.decode(columnMeta)
                var MuchHead_panel = Ext.create('Ext.grid.Panel', {
                    id: "UserMuchHeadGrid_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: newColumns
                });
                var MuchHeadInfoPanel_id = Ext.getCmp("UserMuchHeadInfoPanel_id");
                MuchHeadInfoPanel_id.add(MuchHead_panel);
            }
        },
        beforeload: function (store, options) {
            var task = new Ext.util.DelayedTask(function () {
                // LoadingWin("正在加载数据");
            });
            task.delay(100);
            var leftOrg_Id = Ext.getCmp('leftOrg_Id');
            var new_params = {
                orgId: leftOrg_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

// 生成Grid-Fields
createMuchHeadColumn = function (columnInfo) {
        var myArr = columnInfo;
        var myColumns = "[";
        for (var i = 0; i < myArr.length; i++) {
            var attr = myArr[i];
            var cAttrlength = attr.children.length;
            myColumns += "{text:'" + attr.header + "'";
            if (cAttrlength > 0) {
                myColumns += createChildHeader(attr.children);
            } else if (attr.dataIndex == 'userNo') {
                myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,align:'center'";
            } else {
                myColumns += ",align:'center',dataIndex:'" + attr.dataIndex + "'";
            }
            myColumns += "}";
            if (i < myArr.length - 1) {
                myColumns += ",";
            }
        }
        myColumns += "]";
        return myColumns;
    }
    // 生成Grid-columns Header
createChildHeader = function (columnInfo) {
    var myArr = columnInfo;
    var myColumns = ",columns:[";
    for (var i = 0; i < myArr.length; i++) {
        var attrObj = myArr[i];
        var cAttrlength = attrObj.children.length;
        myColumns += "{text:'" + attrObj.header + "'";
        if (cAttrlength > 0) {
            myColumns += createChildHeader(attrObj.children);
        } else {
            myColumns += ",align:'center',dataIndex:'" + attrObj.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
}