Ext.define('AP.store.data.SystemdataInfoStore', {
    extend: 'Ext.data.BufferedStore',
    id: "SystemdataInfoStoreId",
    alias: 'widget.systemdataInfoStore',
    model: 'AP.model.data.SystemdataInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/systemdataInfoController/findSystemdataInfo',
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
        }
    },
    //分页监听事件
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var rw_g = Ext.getCmp("SystemdataInfoGridPanelId");
            if (!isNotVal(rw_g)) {
                var newColumns = Ext.JSON.decode(createDiagStatisticsColumn(arrColumns));
                var SystemdataInfoGridPanel_panel = Ext.create('Ext.grid.Panel', {
                    id: "SystemdataInfoGridPanelId",
                    border: false,
                    columnLines: true,
                    forceFit: false,
                    selType: (loginUserDataDictionaryManagementModuleRight.editFlag==1?'checkboxmodel':''),
                    multiSelect: true,
                    emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                    store: store,
                    columns: newColumns,
                    listeners: {
                        itemdblclick: function () {
                        	editSystemdataInfo();
                        },
                        selectionchange: function (sm, selections) {
                        	
                        }
                    }
                });
                Ext.getCmp("SystemdataInfoGridPanelViewId").add(SystemdataInfoGridPanel_panel);
            }
        },
        beforeload: function (store, options) {
            var new_params = {
                typeName: Ext.getCmp('sysdatacomboxfield_Id').getValue(),
                sysName: Ext.getCmp('sysname_Id').getValue()

            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});