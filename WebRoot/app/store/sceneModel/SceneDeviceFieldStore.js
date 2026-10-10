Ext.define('AP.store.sceneModel.SceneDeviceFieldStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.sceneDeviceFieldStore',
    autoLoad: false,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/sceneModelController/getDeviceFields',
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
        { name: 'itemCode',   type: 'string' },
        { name: 'itemName',   type: 'string' },
        { name: 'unit',       type: 'string' },
        { name: 'dataSource', type: 'string' }
    ],
    listeners: {
        load: function (store, records, success, eOpts) {
            var gridPanel = Ext.getCmp("SceneDeviceFieldGrid_Id");

            // 表格不存在 → 创建
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "SceneDeviceFieldGrid_Id",
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
                        header: loginUserLanguageResource.dataColumn,
                        align: 'center',
                        dataIndex: 'itemName',
                        flex: 2,
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                            }
                        }
                    }, {
                        header: loginUserLanguageResource.unit,
                        align: 'center',
                        dataIndex: 'unit',
                        width: 80
                    }, {
                        header: loginUserLanguageResource.columnDataSource,
                        align: 'center',
                        dataIndex: 'dataSource',
                        flex: 1
                    }],
                    listeners: {
                        itemdblclick: function (view, record) {
                            view.getSelectionModel().select(record);
                            var win = Ext.getCmp('sceneDataSourceWindow');
                            if (win) win.doConfirm();
                        }
                    }
                });

                var container = Ext.getCmp("SceneDeviceFieldContainer_Id");
                if (isNotVal(container)) {
                    container.add(gridPanel);
                }
            }

            // 默认选中：编辑模式优先选中绑定的字段（用 itemCode 匹配）
            if (store.getCount() > 0) {
                var sm = gridPanel.getSelectionModel();
                sm.deselectAll(true);

                var win = Ext.getCmp('sceneDataSourceWindow');
                var selectedField = null;

                if (win && win.currentBinding && win.currentBinding.fieldName) {
                    selectedField = store.findRecord('itemCode', win.currentBinding.fieldName);   // ★ itemCode
                }

                if (selectedField) {
                    sm.select(selectedField, false);
                } else {
                    sm.select(0, false);
                }
            }
        },
        beforeload: function (store, options) {
            // deviceId 由窗口调用 load 前通过 setExtraParams 传入
        }
    }
});