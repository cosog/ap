Ext.define('AP.store.well.DeviceOrgChangeDeviceListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.deviceOrgChangeDeviceListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/wellInformationManagerController/getDeviceOrgChangeDeviceList',
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
        load: function (store, record, f, op, o) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var gridPanel = Ext.getCmp("DeviceOrgChangeDeviceListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DeviceOrgChangeDeviceListGridPanel_Id",
                    border: false,
                    autoLoad: true,
                    columnLines: true,
                    forceFit: false,
                    selType: 'checkboxmodel',
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: [{
                        text: '序号',
                        lockable: true,
                        align: 'center',
                        width: 50,
                        xtype: 'rownumberer',
                        sortable: false,
                        locked: false
                    }, {
                        text: '名称',
                        lockable: true,
                        align: 'center',
                        flex: 10,
                        sortable: false,
                        locked: false,
                        dataIndex: 'wellName',
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }]
                });
                var deviceListPanel = Ext.getCmp("DeviceOrgChangeWinDeviceListPanel_Id");
                deviceListPanel.add(gridPanel);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName=Ext.getCmp('DeviceOrgChangeDeviceListComb_Id').getValue();
        	var deviceType=Ext.getCmp("DeviceOrgChangeWinDeviceType_Id").getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    wellName:deviceName
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});