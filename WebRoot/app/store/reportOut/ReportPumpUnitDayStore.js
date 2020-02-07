Ext.require([
'Ext.grid.plugin.BufferedRenderer'
]);
Ext.define('AP.store.reportOut.ReportPumpUnitDayStore', {
    extend: 'Ext.data.BufferedStore',
    id: 'ReportPumpUnitDayStore_ids',
    alias: 'widget.reportPumpUnitDayStore',
    model: 'AP.model.reportOut.ReportPumpUnitDayWellModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/reportPumpingUnitDataController/showReportPumpingUnitWell',
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
            var ReportPumpingUnitDayReport_Id = Ext.getCmp("ReportPumpingUnitDayReport_Id");
            if (!isNotVal(ReportPumpingUnitDayReport_Id)) {
                var newColumns = Ext.JSON.decode(createGridPanelColumn(arrColumns));
                var ReportPumpingUnit_panel = Ext.create('Ext.grid.Panel', {
                    id: "ReportPumpingUnitDayReport_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: true,
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
                var ReportPumpingUnitPanel_view = Ext.getCmp("ReportPumpingUnitPanel_view");
                ReportPumpingUnitPanel_view.add(ReportPumpingUnit_panel);
                var ReportPumpingUnitPaneljssj_Id = Ext.getCmp("ReportPumpingUnitPaneljssj_Id");
                ReportPumpingUnitPaneljssj_Id.setValue(get_rawData.jssj);
            }
        },
        beforeload: function (store, options) {
            var task = new Ext.util.DelayedTask(function () {
                // LoadingWin("正在加载数据");
            });
            task.delay(100);
            var leftOrg_Id = Ext.getCmp('leftOrg_Id');
            var ReportPumpingUnitPaneljh_Id = Ext.getCmp('ReportPumpingUnitPaneljh_Id');
            var ReportPumpingUnitPaneljssj_Id = Ext.getCmp('ReportPumpingUnitPaneljssj_Id');
            if (!Ext.isEmpty(leftOrg_Id)) {
                leftOrg_Id = leftOrg_Id.getValue();
            }
            if (!Ext.isEmpty(ReportPumpingUnitPaneljh_Id)) {
                ReportPumpingUnitPaneljh_Id = ReportPumpingUnitPaneljh_Id.getValue();
            }
            if (!Ext.isEmpty(ReportPumpingUnitPaneljssj_Id)) {
                ReportPumpingUnitPaneljssj_Id = ReportPumpingUnitPaneljssj_Id.rawValue;
            }

            var new_params = {
                orgId: leftOrg_Id,
                jh: ReportPumpingUnitPaneljh_Id,
                jssj: ReportPumpingUnitPaneljssj_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "ReportPumpingUnitDayReportTotalCount_Id");
        }
    }
});
