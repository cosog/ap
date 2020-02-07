Ext.require([
'Ext.grid.plugin.BufferedRenderer'
]);
Ext.define('AP.store.monitorOut.MonitorPumpingUnitHistoryStore', {
    extend: 'Ext.data.BufferedStore',
    alias: 'widget.monitorPumpingUnitHistoryStore',
    model: 'AP.model.monitorOut.MonitorPumpingUnitHistoryModel',
    autoLoad: true, // 自动加载数据
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/monitorDataController/showMonitorHistoryData',
        actionMethods: {
            read: 'POST'
        },
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
            var MonitorPumpingUnitHistory_Id = Ext.getCmp("MonitorPumpingUnitHistory_Id");
            if (!isNotVal(MonitorPumpingUnitHistory_Id)) {
                var arrColumns = get_rawData.columns;
                var mycloumns = createMonitorPumpingUnitColumn(arrColumns);
                var newColumns = Ext.JSON.decode(mycloumns);
                MonitorPumpingUnitHistory_Id = Ext.create('Ext.grid.Panel', {
                    id: "MonitorPumpingUnitHistory_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    //forceFit: true,
                    plugins: {
                        ptype: 'bufferedrenderer',
                        numFromEdge: 5,
                        trailingBufferZone: 40,
                        leadingBufferZone: 50
                    },
//                    bufferedRenderer:true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns
                });
                var monitor_MonitorPumpingUnitHistory_view_ids = Ext.getCmp("monitor_MonitorPumpingUnitHistory_view_ids");
                monitor_MonitorPumpingUnitHistory_view_ids.add(MonitorPumpingUnitHistory_Id);
                var MeasurePumpingUnit_from_date_Id = Ext.getCmp('MonitorPumpingUnitHistory_from_date_Id').getValue();
                if (MeasurePumpingUnit_from_date_Id == "new" || MeasurePumpingUnit_from_date_Id == "" || MeasurePumpingUnit_from_date_Id == null) {
                    Ext.getCmp('MonitorPumpingUnitHistory_from_date_Id').setValue(get_rawData.start_date);
                }
                var MeasurePumpingUnit_to_date_Id = Ext.getCmp('MonitorPumpingUnitHistory_to_date_Id').getValue();
                if (MeasurePumpingUnit_to_date_Id == "new" || MeasurePumpingUnit_to_date_Id == "" || MeasurePumpingUnit_to_date_Id == null) {
                    Ext.getCmp('MonitorPumpingUnitHistory_to_date_Id').setValue(get_rawData.end_date);
                }
            }

        },
        beforeload: function (store, options) {
            var leftOrg_Id = Ext.getCmp('leftOrg_Id');
            var MonitorPumpingUnitjhh_Id = Ext.getCmp('MonitorPumpingUnitHistoryjhh_Id');
            var MonitorPumpingUnitjh_Id = Ext.getCmp('MonitorPumpingUnitHistoryjh_Id');
            var MonitorPumpingUnitbjlx_Id = Ext.getCmp('MonitorPumpingUnitHistorybjlx_Id');
            var MonitorPumpingUnitHistory_from_date_Id = Ext.getCmp('MonitorPumpingUnitHistory_from_date_Id');
            var MonitorPumpingUnitHistory_to_date_Id = Ext.getCmp('MonitorPumpingUnitHistory_to_date_Id');
            if (!Ext.isEmpty(leftOrg_Id)) {
                leftOrg_Id = leftOrg_Id.getValue();
            }
            if (!Ext.isEmpty(MonitorPumpingUnitjhh_Id)) {
                MonitorPumpingUnitjhh_Id = MonitorPumpingUnitjhh_Id.getValue();
            }
            if (!Ext.isEmpty(MonitorPumpingUnitjh_Id)) {
                MonitorPumpingUnitjh_Id = MonitorPumpingUnitjh_Id.getValue();
            }
            if (!Ext.isEmpty(MonitorPumpingUnitbjlx_Id)) {
                MonitorPumpingUnitbjlx_Id = MonitorPumpingUnitbjlx_Id.getValue();
            }
            if (!Ext.isEmpty(MonitorPumpingUnitHistory_from_date_Id)) {
                MonitorPumpingUnitHistory_from_date_Id = MonitorPumpingUnitHistory_from_date_Id.rawValue;
            }
            if (!Ext.isEmpty(MonitorPumpingUnitHistory_to_date_Id)) {
                MonitorPumpingUnitHistory_to_date_Id = MonitorPumpingUnitHistory_to_date_Id.rawValue;
            }
            var new_params = {
                orgId: leftOrg_Id,
                jhh: MonitorPumpingUnitjhh_Id,
                jh: MonitorPumpingUnitjh_Id,
                bjlx: MonitorPumpingUnitbjlx_Id,
                startDate: MonitorPumpingUnitHistory_from_date_Id,
                endDate: MonitorPumpingUnitHistory_to_date_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "MonitorPumpingUnitHistoryTotalCount_Id");
        }
    }
});

//生成Grid-Fields
function createMonitorPumpingUnitColumn(columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
//            lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "'";
        if (attr.dataType == 'gtsj') {
            myColumns += width_ + ",align:'center',dataIndex:'" + attr.dataIndex + "',renderer :function(value,e,o){return iconGtsj(value,e,o)}"
        } else if (attr.dataType == 'bgt') {
            myColumns += width_ + ",align:'center',dataIndex:'" + attr.dataIndex + "',renderer :function(value,e,o){return iconBgt(value,e,o)}";
        } else if (attr.dataType == 'dlqx') {
            myColumns += width_ + ",align:'center',dataIndex:'" + attr.dataIndex + "',renderer :function(value,e,o){return iconDlqx(value,e,o)}";

        } else if (attr.dataIndex == 'bjbz') {
            myColumns += ",sortable : false,locked:false,align:'center',dataIndex:'" + attr.dataIndex + "',renderer:function(value){return alarmType(value);}";
        } else if (attr.dataIndex == 'gkmc') {
            myColumns += width_ + ",sortable : false,locked:false,align:'center',dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceColor(value,o,p,e);}";
        } else if (attr.dataType == 'timestamp') {
            myColumns += ",width:" + attr.width + ",sortable : false,align:'center',dataIndex:'" + attr.dataIndex + "'";
        } else if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,align:'center',locked:false";
        } else if (attr.dataIndex == 'jhh' || attr.dataIndex == 'jh') {
            myColumns += ",sortable : false,align:'center',locked:false,dataIndex:'" + attr.dataIndex + "'";
        } else {
            myColumns += hidden_ + lock_ + width_ + ",sortable : false,align:'center',dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};