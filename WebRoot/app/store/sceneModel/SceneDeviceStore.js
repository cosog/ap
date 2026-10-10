Ext.define('AP.store.sceneModel.SceneDeviceStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.sceneDeviceStore',
    autoLoad: false,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/sceneModelController/getDeviceList',
        actionMethods: { read: 'POST' },
        start: 0,
        limit: defaultPageSize,
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    fields: [
        { name: 'deviceId',      type: 'string' },
        { name: 'deviceName',    type: 'string' },
        { name: 'deviceType',    type: 'string' },
        { name: 'calculateType', type: 'string' },
        { name: 'protocolCode',  type: 'string' },
        { name: 'orgId',         type: 'int' }
    ],
    listeners: {
        load: function (store, records, success, eOpts) {
            var gridPanel = Ext.getCmp("SceneDeviceGrid_Id");

            // 表格不存在 → 创建
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "SceneDeviceGrid_Id",
                    border: false,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: true,
                    selModel: {
                        selType: (loginUserSceneModelRight.editFlag == 1 ? 'checkboxmodel' : 'rowmodel'),
                        mode: 'SINGLE',
                        checkOnly: false,
                        allowDeselect: false
                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_'>" +
                                   Ext.String.htmlEncode("<" + loginUserLanguageResource.emptyMsg + ">") + "</div>"
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.idx,
                        align: 'center',
                        width: 50,
                        xtype: 'rownumberer'
                    }, {
                        header: loginUserLanguageResource.deviceName,
                        align: 'center',
                        dataIndex: 'deviceName',
                        flex: 1,
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                            }
                        }
                    }],
                    listeners: {
                        selectionchange: function (sm, selected) {
                            var win = Ext.getCmp('sceneDataSourceWindow');
                            if (win && selected.length > 0) {
                                win.onDeviceSelected(selected[0]);
                            }
                        }
                    }
                });

                var container = Ext.getCmp("SceneDeviceContainer_Id");
                if (isNotVal(container)) {
                    container.add(gridPanel);
                }
            }

            // 默认选中：编辑模式优先选中绑定的设备
            if (store.getCount() > 0) {
                var sm = gridPanel.getSelectionModel();
                sm.deselectAll(true);

                var win = Ext.getCmp('sceneDataSourceWindow');
                var targetRec = null;

                if (win && win.currentBinding && win.currentBinding.deviceId) {
                    targetRec = store.findRecord('deviceId', win.currentBinding.deviceId);
                }
                if (!targetRec) {
                    targetRec = store.getAt(0);
                }
                if (targetRec) {
                    sm.select(targetRec, false);
                }
            }
        },
        beforeload: function (store, options) {
            // orgId 由窗口调用 load 前通过 setExtraParams 传入
        }
    }
});