Ext.define('AP.store.acquisitionUnit.ProtocolDeviceTypeChangeProtocolListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.protocolDeviceTypeChangeProtocolListStore',
    fields: ['id','name','deviceType'],
    autoLoad: true,
    pageSize: 100,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getProtocolDeviceTypeChangeProtocolList',
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
            var gridPanel = Ext.getCmp("ProtocolDeviceTypeChangeProtocolListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ProtocolDeviceTypeChangeProtocolListGridPanel_Id",
                    border: false,
                    autoLoad: false,
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
                        text: '协议名称',
                        lockable: true,
                        align: 'center',
                        flex: 5,
                        sortable: false,
                        locked: false,
                        dataIndex: 'name',
                        renderer: function (value,o,p,e) {
                        	var showVal=value;
                         	if(isNotVal(showVal)){
                         		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (showVal == undefined ? "" : showVal) + "</span>";
                         	}
                        }
                    }, {
                        text: '隶属设备类型',
                        lockable: true,
                        align: 'center',
                        flex: 10,
                        sortable: false,
                        locked: false,
                        dataIndex: 'deviceTypeName',
                        renderer: function (value,o,p,e) {
                        	var showVal=value;
                         	if(isNotVal(showVal)){
                         		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (showVal == undefined ? "" : showVal) + "</span>";
                         	}
                        }
                    }]
                });
                var protocolListPanel = Ext.getCmp("ProtocolDeviceTypeChangeWinProtocolListPanel_Id");
                protocolListPanel.add(gridPanel);
            }
        },
        beforeload: function (store, options) {
        	var deviceTypeIds='';
        	var tabTreeGridPanelSelection= Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
        	if(tabTreeGridPanelSelection.length>0){
        		deviceTypeIds=foreachAndSearchTabChildId(tabTreeGridPanelSelection[0]);
        	}
        	var new_params = {
        			deviceTypeIds: deviceTypeIds
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});