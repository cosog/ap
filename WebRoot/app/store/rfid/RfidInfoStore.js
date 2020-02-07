Ext.define('AP.store.rfid.RfidInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rfidInfoStore',
    model: 'AP.model.rfid.RfidInfoModel',
    id: "RfidInfoStore_Id",
    autoLoad: true, // 自动加载数据
    autoSync: true, // 同步更新
    loadMask: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/rfidInfoController/doRfidSelectShow',
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
            var rfidInfoGridPanel_Id = Ext.getCmp("rfidInfoGridPanel_Id");
            if (!isNotVal(rfidInfoGridPanel_Id)) {
                var arrColumns = get_rawData.columns;
                var cloums = createRoleHeadColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                //分页工具栏
                var bbar = new Ext.PageNumberToolbar({
                    store: store,
                    pageSize: defaultPageSize,
                    displayInfo: true,
                    displayMsg: '当前记录 {0} -- {1} 条 共 {2} 条记录',
                    emptyMsg: "没有记录可显示",
                    prevText: "上一页",
                    nextText: "下一页",
                    refreshText: "刷新",
                    lastText: "最后页",
                    firstText: "第一页",
                    beforePageText: "当前页",
                    afterPageText: "共{0}页"
                });
                var rfid_Id = Ext.create('Ext.grid.Panel', {
                    id: "rfidInfoGridPanel_Id",
                    border: false,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    selType: 'checkboxmodel',
                    multiSelect: true,
                    bbar: bbar,
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selections) {
                            var n = selections.length || 0;
                            Ext.getCmp('editrfidLabelClassBtn_Id').setDisabled(n != 1);
                            if (n > 0) {
                                Ext.getCmp('addrfidLabelClassBtn_Id').setDisabled(true);
                                Ext.getCmp('delrfidLabelClassBtn_Id').setDisabled(false);
                            } else {
                                Ext.getCmp('addrfidLabelClassBtn_Id').setDisabled(false);
                                Ext.getCmp('delrfidLabelClassBtn_Id').setDisabled(true);
                            }
                        },
                        itemdblclick: function () {
                            modifyrfidInfo();
                        }
                    }
                });
                var RfidInfoGridPanelView_Id = Ext.getCmp("RfidInfoGridPanelView_Id");
                RfidInfoGridPanelView_Id.add(rfid_Id);
            }

        },
        beforeload: function (store, options) {
            var alarmselect_jh_Id = Ext.getCmp('alarmselect_jh_Id');
            if (!Ext.isEmpty(alarmselect_jh_Id)) {
                alarmselect_jh_Id = alarmselect_jh_Id.getValue();
            }
            var alarmselect_bjlx_Id = Ext.getCmp('alarmselect_bjlx_Id');
            if (!Ext.isEmpty(alarmselect_bjlx_Id)) {
                alarmselect_bjlx_Id = alarmselect_bjlx_Id.getValue();
            }
            var alarmselect_bjjb_Id = Ext.getCmp('alarmselect_bjjb_Id');
            if (!Ext.isEmpty(alarmselect_bjjb_Id)) {
                alarmselect_bjjb_Id = alarmselect_bjjb_Id.getValue();
            }
            var new_params = {
                jh: alarmselect_jh_Id,
                bjlx: alarmselect_bjlx_Id,
                bjjb: alarmselect_bjjb_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }

});

//生成Grid-Fields 动态创建报警信息查询的columns
createRoleHeadColumn = function (columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        myColumns += "{ header:'" + attr.header + "'";
        if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable:false,align:'center'";
        } else if (attr.dataIndex == 'roleFlag') {
            myColumns += ",dataIndex:'" + attr.dataIndex + "',renderer :function(value){return showRoleType(value);}";
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