Ext.define('AP.store.rfidSelect.RfidSelectInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rfidSelectInfoStore',
    model: 'AP.model.rfidSelect.RfidSelectInfoModel',
    id: "RfidSelectInfoStore_Id",
    buffered: true,
    leadingBufferZone: 200,
    autoLoad: true, // 自动加载数据
    autoSync: true, // 同步更新
    loadMask: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/rfidInfoController/doRfidfrontInfoShow',
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
        },
        simpleSortMode: true
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var RfidSelectInfoGridViewPanel_Id = Ext.getCmp("RfidSelectInfoGridViewPanel_Id");
            if (!isNotVal(RfidSelectInfoGridViewPanel_Id)) {
                var arrColumns = get_rawData.columns;
                var newColumns = Ext.JSON.decode(createAlarmSelectColumn(arrColumns));
                var RfidSelectInfoGridViewPanelGrid = Ext.create('Ext.grid.Panel', {
                    id: "RfidSelectInfoGridViewPanel_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    loadMask: true,
                    selModel: {
                        pruneRemoved: false
                    },
                    multiSelect: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true,
                        trackOver: false
                    },
                    store: store,
                    columns: newColumns
                });
                var RfidSelectInfoGridPanel_Id = Ext.getCmp("RfidSelectInfoGridPanel_Id");
                RfidSelectInfoGridPanel_Id.add(RfidSelectInfoGridViewPanelGrid);
            }
        },
        beforeload: function (store, options) {
            var leftOrg_Id = Ext.getCmp('leftOrg_Id');
            if (!Ext.isEmpty(leftOrg_Id)) {
                leftOrg_Id = leftOrg_Id.getValue();
            }
            var alarmselect_jh_Id = Ext.getCmp('rfidselect_jc_Id');
            if (!Ext.isEmpty(alarmselect_jh_Id)) {
                alarmselect_jh_Id = alarmselect_jh_Id.getValue();
            }
            var alarmselect_bjlx_Id = Ext.getCmp('rfidselect_xm_Id');
            if (!Ext.isEmpty(alarmselect_bjlx_Id)) {
                alarmselect_bjlx_Id = alarmselect_bjlx_Id.getValue();
            }
            var alarmselect_bjjb_Id = Ext.getCmp('rfidselect_hfbz_Id');
            if (!Ext.isEmpty(alarmselect_bjjb_Id)) {
                alarmselect_bjjb_Id = alarmselect_bjjb_Id.getValue();
            }
            var new_params = {
                orgId: leftOrg_Id,
                jc: alarmselect_jh_Id,
                xm: alarmselect_bjlx_Id,
                hfbz: alarmselect_bjjb_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "RfidSelectInfoGridPanelTotalCount_Id");
        }
    }

});

//生成Grid-Fields 动态创建报警信息查询的columns
createAlarmSelectColumn = function (columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        myColumns += "{text:'" + attr.header + "'";
        if (attr.dataIndex == 'hfbz') {
            myColumns += ",sortable : false,align:'center',dataIndex:'" + attr.dataIndex + "',renderer:function(value){return rfidhfbzselectType(value);}";
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

rfidhfbzselectType = function (val) {
    var rawValue = "非法";
    if (val == '1') {
        rawValue = "有卡合法";
    } else if (val == '2') {
        rawValue = "有卡非法";
    } else if (val == '3') {
        rawValue = "无卡非法";
    }
    return rawValue;
}