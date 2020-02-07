Ext.define('AP.store.wellTrajectory.WellTrajectoryInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'wellTrajectoryInfoStore',
    model: 'AP.model.wellTrajectory.WellTrajectoryInfoModel',
    autoLoad: true,
    pageSize: 100000,
    proxy: {
        type: 'ajax',
        url: context + '/wellTrajectoryController/showWellTracjectoryInfo',
        actionMethod: {
            read: 'POST'
        },
        start: 0,
        limit: 100000,
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var ResHeadInfoGridPanel_Id = Ext.getCmp("welltrajectoryPanel_Id");
            if (!isNotVal(ResHeadInfoGridPanel_Id)) {
                var arrColumns = get_rawData.columns;
                var cloums = createDiagStatisticsColumn(arrColumns);
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
                var SystemdataInfoGridPanel_panel = Ext.create('Ext.grid.Panel', {
                    id: "welltrajectoryPanel_Id",
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
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    bbar: bbar,
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selections) {
//                            var n = selections.length || 0;
//                            var edit = Ext.getCmp("editrewelltrajectoryLabelClassBtn_Id");
//                            edit.setDisabled(n != 1);
//                            if (n > 0) {
//                                var add = Ext.getCmp("addrewelltrajectoryLabelClassBtn_Id")
//                                add.setDisabled(true);
//                                Ext.getCmp("delrewelltrajectoryLabelClassBtn_Id").setDisabled(false);
//                            } else {
//                                Ext.getCmp("addrewelltrajectoryLabelClassBtn_Id").setDisabled(false);
//                                Ext.getCmp("delrewelltrajectoryLabelClassBtn_Id").setDisabled(true);
//                            }
                        },
                        itemdblclick: function () {
                            modifywelltrajectory();
                        }
                    }
                });
                var WellTrajectoryInfoPanelView_Id = Ext.getCmp("WellTrajectoryInfoPanelView_Id");
                WellTrajectoryInfoPanelView_Id.add(SystemdataInfoGridPanel_panel);
            }

        },
        beforeload: function (store, options) {
            var ProductionOutDataorg_Id = Ext.getCmp('WellTrajectoryorg_Id');
            if (!Ext.isEmpty(ProductionOutDataorg_Id)) {
                ProductionOutDataorg_Id = ProductionOutDataorg_Id.getValue();
            }
            var produceOutDatares_Id = Ext.getCmp('WellTrajectoryres_Id');
            if (!Ext.isEmpty(produceOutDatares_Id)) {
                produceOutDatares_Id = produceOutDatares_Id.getValue();
            }
            var wellTrajectoryjh_Id = Ext.getCmp('wellTrajectoryjh_Id');
            if (!Ext.isEmpty(wellTrajectoryjh_Id)) {
                wellTrajectoryjh_Id = wellTrajectoryjh_Id.getValue();
            }
            var new_params = {
                jh: wellTrajectoryjh_Id,
                orgCode: ProductionOutDataorg_Id,
                resCode: produceOutDatares_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});