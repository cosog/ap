Ext.require([
'Ext.grid.plugin.BufferedRenderer'
]);
Ext.define('AP.store.reportOut.ReportPumpUnitDayStore2', {
    extend: 'Ext.data.BufferedStore',
    id: 'ReportPumpUnitDayStore_ids',
    alias: 'widget.reportPumpUnitDayStore',
    model: 'AP.model.reportOut.ReportPumpUnitDayWellModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/reportPumpingUnitDataController/showDiagnosisDailyReportData',
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
            var arrColumns = get_rawData.columns;
            var DiagnosisDayReport = Ext.getCmp("DiagnosisDayReport_Id");
            if (!isNotVal(DiagnosisDayReport)) {
                var newColumns = Ext.JSON.decode(createGridPanelColumn(arrColumns));
                DiagnosisDayReport = Ext.create('Ext.grid.Panel', {
                    id: "DiagnosisDayReport_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
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
                    columns: [{
                    	text: '序号',
                        width: 50,
                        xtype: 'rownumberer',
                        align: 'center'
                    }, {
                        text: "井名",
                        dataIndex: "jh",
                        width: 60,
                        align: 'center'
                    }, {
                        text: "日期",
                        dataIndex: "jssj",
                        width: 90,
                        align: 'center'
                    },{
                    	text: "时率",
                    	align: 'center',
                    	columns:[{
                            text: "日生产时间(h)",
                            width: 90,
                            dataIndex: "rgzsj",
                            align: 'center'
                        }, {
                            text: "生产时率(%)",
                            width: 85,
                            dataIndex: "scsl",
                            align: 'center'
                        }]
                    },{
                    	text: "工况",
                    	align: 'center',
                    	columns:[{
                    		text: "工况类型",
                            dataIndex: "gkmc",
                            align: 'center'
                        }, {
                        	text: "优化建议",
                            dataIndex: "yhjy",
                            width: 120,
                            align: 'center'
                        }]
                    },{
                    	text: "产量",
                    	align: 'center',
                    	columns:[{
                            text: "产液量(t/d)",
                            width: 80,
                            dataIndex: "jsdjrcyl",
                            align: 'center'
                        }, {
                            text: "产油量(t/d)",
                            width: 80,
                            dataIndex: "jsdjrcyl1",
                            align: 'center'
                        }, {
                            text: "综合含水率(%)",
                            dataIndex: "hsld",
                            align: 'center'
                        }, {
                            text: "产量波动(%)",
                            width: 85,
                            dataIndex: "jsdjrcylbd",
                            align: 'center'
                        }, {
                            text: "综合充满系数",
                            width: 85,
                            dataIndex: "gtcmxs",
                            align: 'center'
                        }]
                    },{
                    	text: "系统效率",
                    	align: 'center',
                    	columns:[{
                            text: "系统效率(%)",
                            width: 85,
                            dataIndex: "xtxl",
                            align: 'center'
                        }, {
                            text: "地面效率(%)",
                            width: 85,
                            dataIndex: "dmxtxl",
                            align: 'center'
                        }, {
                            text: "井下效率(%)",
                            width: 85,
                            dataIndex: "jxxtxl",
                            align: 'center'
                        }]
                    },{
                        text: "备注",
                        dataIndex: "bz",
                        align: 'center'
                    }]
                });
                var ReportPumpingUnitPanel_view = Ext.getCmp("ReportPumpingUnitPanel_view");
                ReportPumpingUnitPanel_view.add(DiagnosisDayReport);
                if(Ext.getCmp("ReportPumpingUnitPaneljssj_Id").getValue()==''||Ext.getCmp("ReportPumpingUnitPaneljssj_Id").getValue()==null){
                	Ext.getCmp("ReportPumpingUnitPaneljssj_Id").setValue(get_rawData.jssj);
                	Ext.getCmp("ReportPumpingUnitPaneljssj_Id").setRawValue(get_rawData.jssj);
                }
                
            }
        },
        beforeload: function (store, options) {
            var task = new Ext.util.DelayedTask(function () {
                // LoadingWin("正在加载数据");
            });
            task.delay(100);
            var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var jh = Ext.getCmp('ReportPumpingUnitPaneljh_Id').getValue();
            var jssj = Ext.getCmp('ReportPumpingUnitPaneljssj_Id').rawValue;

            var new_params = {
                orgId: orgId,
                jh: jh,
                jssj: jssj
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "ReportPumpingUnitDayReportTotalCount_Id");
        }
    }
});
