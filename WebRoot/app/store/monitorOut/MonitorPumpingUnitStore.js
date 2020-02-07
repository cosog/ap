Ext.require([
'Ext.grid.plugin.BufferedRenderer'
]);
Ext.define('AP.store.monitorOut.MonitorPumpingUnitStore', {
    extend: 'Ext.data.BufferedStore',
    alias: 'widget.monitorPumpingUnitStore',
    model: 'AP.model.monitorOut.MonitorPumpingUnitModel',
    id: 'MonitorPumpingUnitStore_Id',
    autoLoad: true, // 自动加载数据
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/monitorDataController/showMonitorData',
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
            var MonitorPumpingUnit_Id = Ext.getCmp("MonitorPumpingUnit_Id");
            if (!isNotVal(MonitorPumpingUnit_Id)) {
                var arrColumns = get_rawData.columns;
                var mycloumns = createMonitorPumpingUnitColumn(arrColumns);
                var newColumns = Ext.JSON.decode(mycloumns);
                MonitorPumpingUnit_Id = Ext.create('Ext.grid.Panel', {
                    id: "MonitorPumpingUnit_Id",
                    border: false,
                    autoLoad: true,
                    columnLines: true,
                    multiSelect: true,
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
                var monitor_MonitorPumpingUnit_view_ids = Ext.getCmp("monitor_MonitorPumpingUnit_view_ids");
                monitor_MonitorPumpingUnit_view_ids.add(MonitorPumpingUnit_Id);
            }
        	
        	//test websocket
//        	var websocket;
//        	var host=window.location.host;
//            if (!'WebSocket' in window) {
//                alert("服务器不支持websocket");
//                return;
//            }
//            websocket = new WebSocket("ws://192.168.1.110:8080/cosog/collectionList");
//            websocket.onopen = function (evnt) {
//            	alert("链接服务器成功!")
//            };
//            websocket.onmessage = function (evnt) {
//            	alert("接收到信息："+evnt.data);
//            };
//            websocket.onerror = function (evnt) {
//            	alert("错误");
//            };
//            websocket.onclose = function (evnt) {
//            	alert("与服务器断开了链接!")
//            }
//            if(websocket!=null){
//            	//alert("发送信息");
//            	//websocket.send("i am client");
//            }
        },
        beforeload: function (store, options) {
            var leftOrg_Id = Ext.getCmp('leftOrg_Id');
            var MonitorPumpingUnitjhh_Id = Ext.getCmp('MonitorPumpingUnitjhh_Id');
            var MonitorPumpingUnitjh_Id = Ext.getCmp('MonitorPumpingUnitjh_Id');
            var MonitorPumpingUnitbjlx_Id = Ext.getCmp('MonitorPumpingUnitbjlx_Id');
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
            var new_params = {
                orgId: leftOrg_Id,
                jhh: MonitorPumpingUnitjhh_Id,
                jh: MonitorPumpingUnitjh_Id,
                bjlx: MonitorPumpingUnitbjlx_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "MonitorPumpingUnitTotalCount_Id");
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
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' ";
        if (attr.dataType == 'gtsj') {
            myColumns += width_ + ",dataIndex:'" + attr.dataIndex + "',renderer :function(value,e,o){return iconGtsj(value,e,o)}"
        } else if (attr.dataType == 'bgt') {
            myColumns += width_ + ",dataIndex:'" + attr.dataIndex + "',renderer :function(value,e,o){return iconBgt(value,e,o)}";
        } else if (attr.dataType == 'dlqx') {
            myColumns += width_ + ",dataIndex:'" + attr.dataIndex + "',renderer :function(value,e,o){return iconDlqx(value,e,o)}";

        } else if (attr.dataIndex == 'bjbz') {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return alarmType(value);}";
        } else if (attr.dataIndex == 'gkmc') {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceColor(value,o,p,e);}";
        } else if (attr.dataType == 'timestamp') {
            myColumns += ",width:" + attr.width + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        } else if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable : false,locked:false";
        } else if (attr.dataIndex == 'jhh' || attr.dataIndex == 'jh') {
            myColumns += width_ + ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "'";
        } else {
            myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};