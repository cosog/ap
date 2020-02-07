Ext.require([
'Ext.grid.plugin.BufferedRenderer'
]);
Ext.define('AP.store.balanceRecord.BalanceRecordStore', {
    extend: 'Ext.data.BufferedStore',
    alias: 'widget.balanceRecordStore',
    model: 'AP.model.balanceRecord.BalanceRecordModel',
    autoLoad: true, // 自动加载数据
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/balanceRecordController/getBalanceRecordData',
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
            var gridPanel = Ext.getCmp("BalanceRecordGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var mycloumns = createMonitorPumpingUnitColumn(arrColumns);
                var newColumns = Ext.JSON.decode(mycloumns);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "BalanceRecordGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    autoScroll: true,
                    forceFit: true,
                    plugins: {
                        ptype: 'bufferedrenderer',
                        numFromEdge: 5,
                        trailingBufferZone: 40,
                        leadingBufferZone: 50
                    },
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns
                });
                var panel = Ext.getCmp("BalanceRecordTablePanel_Id");
                panel.add(gridPanel);
                var startDate = Ext.getCmp('BalanceRecordStartDate_Id').getValue();
                if (startDate == "new" || startDate == "" || startDate == null) {
                    Ext.getCmp('BalanceRecordStartDate_Id').setValue(get_rawData.start_date);
                }
                var endDate = Ext.getCmp('BalanceRecordEndDate_Id').getValue();
                if (endDate == "new" || endDate == "" || endDate == null) {
                    Ext.getCmp('BalanceRecordEndDate_Id').setValue(get_rawData.end_date);
                }
            }

        },
        beforeload: function (store, options) {
            var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            var jh = Ext.getCmp('BalanceRecordjh_Id').getValue();
            var startDate = Ext.getCmp('BalanceRecordStartDate_Id').rawValue;
            var endDate = Ext.getCmp('BalanceRecordEndDate_Id').rawValue;
            
            var new_params = {
                orgId: leftOrg_Id,
                jh: jh,
                startDate: startDate,
                endDate: endDate
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "BalanceRecordTotalCount_Id");
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