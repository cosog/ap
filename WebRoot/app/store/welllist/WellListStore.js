Ext.define('AP.store.welllist.WellListStore', {
    extend: 'Ext.data.Store',
    model: 'AP.model.welllist.WellListModel',
    autoLoad: true,
    pageSize: 20,
    proxy: {
        type: 'ajax',
        url: context + '/wellListManagerController/findjhInWellList',
        actionMethods: {
            read: 'POST'
        },
        params: {
            start: 0,
            limit: 20
        },
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var WellListInfo_All_Id = Ext.getCmp("WellListInfo_All_Id");
            if (!isNotVal(WellListInfo_All_Id)) {
                var arrColumns = get_rawData.columns;
                var cloums = createAlarmMonitorColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                var WellListInfo_All_Id = Ext.create('Ext.grid.Panel', {
                    id: "WellListInfo_All_Id",
                    layout: 'fit',
                    forceFit: true,
                    columnLines: true,
                    frame: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (view, selected, o) {
                            if (selected.length > 0) {
                                var jbh_ = selected[0].data.jlbh;
                                // AJAX提交方式
                                var action = '/wellListManagerController/queryOnlyData';
                                right_FnAjax(jbh_, action);
                            }
                        }

                    }
                });
                var WellListInfo_Allpanel_Id = Ext.getCmp("WellListInfo_Allpanel_Id");
                WellListInfo_Allpanel_Id.add(WellListInfo_All_Id);
            }
            if (options.length > 0) {
                var jbh_ = options[0].data.jlbh;
                var action = '/wellListManagerController/queryOnlyData';
                right_FnAjax(jbh_, action);
            }
        }
    }
});


createAlarmMonitorColumn = function (columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        myColumns += "{text:'" + attr.header + "'";
        if (attr.dataIndex == 'jlbh') {
            myColumns += ",sortable : false,align:'center',dataIndex:'" + attr.dataIndex + "',hidden:true";
        } else if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,align:'center'";
        } else {
            myColumns += ",sortable : false,align:'center',dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};