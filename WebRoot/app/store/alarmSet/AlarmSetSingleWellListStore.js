Ext.define('AP.store.alarmSet.AlarmSetSingleWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.alarmSetSingleWellListStore',
    model: 'AP.model.alarmSet.AlarmSetSingleWellListModel',
    id: "AlarmSetSingleWellListStore_Id",
    autoLoad: true, // 自动加载数据
    autoSync: true, // 同步更新
    loadMask: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/alarmSetManagerController/getAlarmSetSingleWellList',
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
            var AlarmSetSingleWellListGrid = Ext.getCmp("AlarmSetSingleWellListGrid_Id");
            if (!isNotVal(AlarmSetSingleWellListGrid)) {
                var arrColumns = get_rawData.columns;
                var cloums = createAlarmSetSingleWellListGridColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                var AlarmSetSingleWellListGrid = Ext.create('Ext.grid.Panel', {
                    id: "AlarmSetSingleWellListGrid_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    multiSelect: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	itemclick: function (view,record,item,ndex,e,eOpts){
                    		updateDiatreteAlarmLimitValue(record.data);
                    	}
                    }
                });
                var alarmsetSingleWellListPanel_Id = Ext.getCmp("alarmsetSingleWellListPanel_Id");
                alarmsetSingleWellListPanel_Id.add(AlarmSetSingleWellListGrid);
            }
            if(get_rawData.totalCount>0){
            	updateDiatreteAlarmLimitValue(get_rawData.totalRoot[0]);
            }else{
            	//clearDiatreteAlarmLimitValue();
            }

        },
        beforeload: function (store, options) {
            var leftOrg_Id = Ext.getCmp('leftOrg_Id');
            var alarmWellPanel_jh = Ext.getCmp('alarmWellPanel_jh_Id');
            if (!Ext.isEmpty(leftOrg_Id)) {
                leftOrg_Id = leftOrg_Id.getValue();
            }
            if (!Ext.isEmpty(alarmWellPanel_jh)) {
            	alarmWellPanel_jh = alarmWellPanel_jh.getValue();
            }
            var new_params = {
                orgId: leftOrg_Id,
                jh: alarmWellPanel_jh
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }

});
//生成Grid-Fields 动态创建报警信息查询的columns
createAlarmSetSingleWellListGridColumn = function (columnInfo) {
    var myArr = columnInfo;
    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        myColumns += "{ header:'" + attr.header + "',align:'center'";
        if (attr.dataIndex == 'id') {
            myColumns += ",width:" + attr.width + ",xtype: 'rownumberer',sortable:false";
        } else if (attr.dataIndex == 'bjbz') {
            myColumns += ",dataIndex:'" + attr.dataIndex + "',renderer :function(value){return alarmSetType(value);}";
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