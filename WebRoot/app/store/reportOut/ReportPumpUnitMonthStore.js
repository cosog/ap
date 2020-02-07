Ext.require([
'Ext.grid.plugin.BufferedRenderer'
]);
Ext.define('AP.store.reportOut.ReportPumpUnitMonthStore', {
    extend: 'Ext.data.BufferedStore',
    id: 'ReportPumpUnitMonthStore_ids',
    alias: 'widget.reportPumpUnitMonthStore',
    model: 'AP.model.reportOut.ReportPumpUnitMonthWellModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/reportPumpingUnitMonthController/showReportPumpingUnitMonth',
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
            var rw_g = Ext.getCmp("ReportPumpingUnitMonthReport_Id");
            if (!isNotVal(rw_g)) {
                var newColumns = Ext.JSON.decode(createGridPanelColumn(arrColumns));
                var ReportPumpingUnitMonth_panel = Ext.create('Ext.grid.Panel', {
                    id: "ReportPumpingUnitMonthReport_Id",
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
                var ReportPumpingUnitMonthPanel_view = Ext.getCmp("ReportPumpingUnitMonthPanel_view");
                ReportPumpingUnitMonthPanel_view.add(ReportPumpingUnitMonth_panel);
                var ReportPumpingUnitMonthPaneljssj_Id = Ext.getCmp('ReportPumpingUnitMonthPaneljssj_Id').getValue();
                if (ReportPumpingUnitMonthPaneljssj_Id == "new" || ReportPumpingUnitMonthPaneljssj_Id == "" || ReportPumpingUnitMonthPaneljssj_Id == null) {
                    Ext.getCmp('ReportPumpingUnitMonthPaneljssj_Id').setValue(get_rawData.jssj);
                }
            }
        },
        beforeload: function (store, options) {
            var task = new Ext.util.DelayedTask(function () {
                // LoadingWin("正在加载数据");
            });
            task.delay(100);

            var leftOrg_Id = Ext.getCmp('leftOrg_Id');
            var ReportPumpingUnitMonthPaneljh_Id = Ext
                .getCmp('ReportPumpingUnitMonthPaneljh_Id');
            var ReportPumpingUnitMonthPaneljssj_Id = Ext
                .getCmp('ReportPumpingUnitMonthPaneljssj_Id');

            if (!Ext.isEmpty(leftOrg_Id)) {
                leftOrg_Id = leftOrg_Id.getValue();
            }
            if (!Ext.isEmpty(ReportPumpingUnitMonthPaneljh_Id)) {
                ReportPumpingUnitMonthPaneljh_Id = ReportPumpingUnitMonthPaneljh_Id
                    .getValue();
            }
            if (!Ext.isEmpty(ReportPumpingUnitMonthPaneljssj_Id)) {
                ReportPumpingUnitMonthPaneljssj_Id = ReportPumpingUnitMonthPaneljssj_Id.rawValue;
            }

            var new_params = {
                orgId: leftOrg_Id,
                jh: ReportPumpingUnitMonthPaneljh_Id,
                jssj: ReportPumpingUnitMonthPaneljssj_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "ReportPumpingUnitMonthReportTotalCount_Id");
        }
    }
});