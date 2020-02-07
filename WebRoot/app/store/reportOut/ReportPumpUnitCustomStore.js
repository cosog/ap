Ext.require([
'Ext.grid.plugin.BufferedRenderer'
]);
Ext.define('AP.store.reportOut.ReportPumpUnitCustomStore', {
    extend: 'Ext.data.BufferedStore',
    id: 'ReportPumpUnitCustomStore_ids',
    alias: 'widget.reportPumpUnitCustomStore',
    model: 'AP.model.reportOut.ReportPumpUnitCustomWellModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/reportPumpingUnitCustomController/showReportPumpingUnitCustom',
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
            var rw_g = Ext.getCmp("ReportPumpingUnitCustomReport_Id");
            if (!isNotVal(rw_g)) {
                var newColumns = Ext.JSON.decode(createGridPanelColumn(arrColumns));
                var ReportPumpingUnitCustom_panel = Ext.create('Ext.grid.Panel', {
                    id: "ReportPumpingUnitCustomReport_Id",
                    border: false,
                    autoLoad: true,
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

                Ext.getCmp("ReportPumpingUnitCustomPanel_view").add(ReportPumpingUnitCustom_panel);
                Ext.getCmp('ReportPumpingUnitCustomPanel_from_date_Id').setValue(get_rawData.start_date);
                Ext.getCmp('ReportPumpingUnitCustomPanel_to_date_Id').setValue(get_rawData.end_date);
            }

        },
        beforeload: function (store, options) {
            var task = new Ext.util.DelayedTask(function () {
                // LoadingWin("正在加载数据");
            });
            task.delay(100);
            var leftOrg_Id = Ext.getCmp('leftOrg_Id');
            var ReportPumpingUnitCustomPaneljh_Id = Ext
                .getCmp('ReportPumpingUnitCustomPaneljh_Id');
            var ReportPumpingUnitCustomPanel_from_date_Id = Ext
                .getCmp('ReportPumpingUnitCustomPanel_from_date_Id');
            var ReportPumpingUnitCustomPanel_to_date_Id = Ext
                .getCmp('ReportPumpingUnitCustomPanel_to_date_Id');

            if (!Ext.isEmpty(leftOrg_Id)) {
                leftOrg_Id = leftOrg_Id.getValue();
            }
            if (!Ext.isEmpty(ReportPumpingUnitCustomPaneljh_Id)) {
                ReportPumpingUnitCustomPaneljh_Id = ReportPumpingUnitCustomPaneljh_Id
                    .getValue();
            }
            if (!Ext.isEmpty(ReportPumpingUnitCustomPanel_from_date_Id)) {
                ReportPumpingUnitCustomPanel_from_date_Id = ReportPumpingUnitCustomPanel_from_date_Id.rawValue;
            }
            if (!Ext.isEmpty(ReportPumpingUnitCustomPanel_to_date_Id)) {
                ReportPumpingUnitCustomPanel_to_date_Id = ReportPumpingUnitCustomPanel_to_date_Id.rawValue;
            }

            var new_params = {
                orgId: leftOrg_Id,
                jh: ReportPumpingUnitCustomPaneljh_Id,
                startDate: ReportPumpingUnitCustomPanel_from_date_Id,
                endDate: ReportPumpingUnitCustomPanel_to_date_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});