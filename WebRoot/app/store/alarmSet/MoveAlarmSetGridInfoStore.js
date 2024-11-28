Ext.define('AP.store.alarmSet.MoveAlarmSetGridInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.moveAlarmSetGridInfoStore',
    model: 'AP.model.alarmSet.MoveAlarmSetInfoGridModel',
    id: "MoveAlarmSetGridInfoStore_Id",
    autoLoad: true, // 自动加载数据
    autoSync: true, // 同步更新
    loadMask: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/moveAlarmSetManagerController/doMoveAlarmsSetShow',
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
            var ResHeadInfoGridPanel_Id = Ext.getCmp("MoveAlarmSetInfoGridPanel_Id");
            if (!isNotVal(ResHeadInfoGridPanel_Id)) {
                var arrColumns = get_rawData.columns;
                var cloums = createMoveAlarmSetHeadColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                //分页工具栏
//                var bbar = new Ext.PageNumberToolbar({
//                    store: store,
//                    pageSize: defaultPageSize,
//                    displayInfo: true,
//                    displayMsg: '当前记录 {0} -- {1} 条 共 {2} 条记录',
//                    emptyMsg: "没有记录可显示",
//                    prevText: "上一页",
//                    nextText: "下一页",
//                    refreshText: "刷新",
//                    lastText: "最后页",
//                    firstText: "第一页",
//                    beforePageText: "当前页",
//                    afterPageText: "共{0}页"
//                });
                
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                
                var SystemdataInfoGridPanel_panel = Ext.create('Ext.grid.Panel', {
                    id: "MoveAlarmSetInfoGridPanel_Id",
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    selType: 'checkboxmodel',
                    multiSelect: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    bbar: bbar,
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selections) {
                            var n = selections.length || 0;
                            var edit = Ext.getCmp("editMoveAlarmSetLabelClassBtn_Id");
                            edit.setDisabled(n != 1);
                            if (n > 0) {
                                var add = Ext.getCmp("addMoveAlarmSetLabelClassBtn_Id")
                                add.setDisabled(true);
                                Ext.getCmp("delMoveAlarmSetLabelClassBtn_Id").setDisabled(false);
                            } else {
                                Ext.getCmp("addMoveAlarmSetLabelClassBtn_Id").setDisabled(false);
                                Ext.getCmp("delMoveAlarmSetLabelClassBtn_Id").setDisabled(true);
                            }
                        },
                        itemdblclick: function () {
                            modifyMoveAlarmSet();
                        }
                    }
                });
                var OrgInfoTreeGridViewPanel_Id = Ext.getCmp("MoveAlarmSetInfoGridPanelView_Id");
                OrgInfoTreeGridViewPanel_Id.add(SystemdataInfoGridPanel_panel);
            }

        },
        beforeload: function (store, options) {
            var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp('CurvePumpingUnitjh_Id').getValue();
            var startDate = Ext.getCmp('CurvePumpingUnit_from_date_Id').getValue();
            var endDate = Ext.getCmp('CurvePumpingUnit_to_date_Id').getValue();

            var new_params = {
                orgId: leftOrg_Id,
                wellName: wellName,
                startDate: startDate,
                endDate: endDate
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }

});
//生成Grid-Fields 动态创建报警信息查询的columns
createMoveAlarmSetHeadColumn = function (columnInfo) {
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