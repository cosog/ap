Ext.define('AP.store.device.PumpStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pumpStore',
    model: 'AP.model.device.PumpModel',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/pumpController/findAllList',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: 10000,
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
            var gridPanel = Ext.getCmp("pumpPanel_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createEditGridColumn(arrColumns);
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
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "pumpPanel_Id",
                    requires: [
                           	'Ext.grid.selection.SpreadsheetModel',
                           	'Ext.grid.plugin.Clipboard'
                           ],
                  xtype:'spreadsheet-checked',
                  selModel: {
                  	type: 'spreadsheet',
                  	columnSelect: true,
                      checkboxSelect: true,
                      pruneRemoved: false,
                      extensible: 'xy'
                  },
                  plugins: [
                            'clipboard',
                            'selectionreplicator',
                            new Ext.grid.plugin.CellEditing({
                          	  clicksToEdit:2
                            })
                        ],
                    border: false,
                    stateful: true,
                    autoScroll: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
//                    selType: 'checkboxmodel',
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
//                            var edit = Ext.getCmp("editpumpPanelLabelClassBtn_Id");
//                            edit.setDisabled(n != 1);
//                            if (n > 0) {
//                                var add = Ext.getCmp("addpumpPanelLabelClassBtn_Id")
//                                add.setDisabled(true);
//                                Ext.getCmp("delpumpPanelLabelClassBtn_Id").setDisabled(false);
//                            } else {
//                                Ext.getCmp("addpumpPanelLabelClassBtn_Id").setDisabled(false);
//                                Ext.getCmp("delpumpPanelLabelClassBtn_Id").setDisabled(true);
//                            }
                        },
                        itemdblclick: function () {
//                            modifyPump();
                        }
                    }
                });
                var pumpPanelView_Id = Ext.getCmp("pumpPanelView_Id");
                pumpPanelView_Id.add(gridPanel);
            }
            for(var i=1;i<=500-get_rawData.totalCount;i++){
            	gridPanel.getStore().add(new Ext.data.Record({}));
            }
        },
        beforeload: function (store, options) {
            var pumpPanel_sccj_Id = Ext.getCmp('pumpPanel_sccj_Id');
            var pumpPanel_cybxh_Id = Ext.getCmp('pumpPanel_cybxh_Id');

            if (!Ext.isEmpty(pumpPanel_sccj_Id)) {
                pumpPanel_sccj_Id = pumpPanel_sccj_Id.getValue();
            }
            if (!Ext.isEmpty(pumpPanel_cybxh_Id)) {
                pumpPanel_cybxh_Id = pumpPanel_cybxh_Id.getValue();
            }
            var new_params = {
                sccj: pumpPanel_sccj_Id,
                cybxh: pumpPanel_cybxh_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});