Ext.define('AP.store.acquisitionUnit.ProtocolDeviceTypeChangeProtocolListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.protocolDeviceTypeChangeProtocolListStore',
    fields: ['id','name','deviceType'],
    autoLoad: true,
    pageSize: defaultPageSize,
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
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: [{
                        text: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        width: 50,
                        xtype: 'rownumberer',
                        sortable: false,
                        locked: false
                    }, {
                        text: loginUserLanguageResource.protocolName,
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
                        text: loginUserLanguageResource.owningDeviceType,
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