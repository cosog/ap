Ext.define('AP.store.acquisitionUnit.DatabaseColumnMappingStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.databaseColumnMappingStore',
    fields: ['id','itemName','itemColumn'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/acquisitionUnitManagerController/getDatabaseColumnMappingTable',
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
            var gridPanel = Ext.getCmp("DatabaseColumnMappingGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DatabaseColumnMappingGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'>" + Ext.String.htmlEncode("<" + loginUserLanguageResource.emptyMsg + ">") + "</div>"
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
                        text: loginUserLanguageResource.name,
                        lockable: true,
                        align: 'center',
                        flex: 10,
                        sortable: false,
                        locked: false,
                        dataIndex: 'itemName',
                        renderer: function (value,o,p,e) {
                        	if(isNotVal(value)){
                        		return Ext.String.format('<span data-qtip="{0}">{1}</span>',Ext.String.htmlEncode(value),Ext.String.htmlEncode(value));
                        	}
                        }
                    }, {
                        text: loginUserLanguageResource.dataColumn,
                        lockable: true,
                        align: 'center',
                        flex: 10,
                        sortable: false,
                        locked: false,
                        dataIndex: 'itemColumn',
                        renderer: function (value) {
                        	if(isNotVal(value)){
                        		return Ext.String.format('<span data-qtip="{0}">{1}</span>',Ext.String.htmlEncode(value),Ext.String.htmlEncode(value));
                        	}
                        }
                    }]
                });
                var panel = Ext.getCmp("DatabaseColumnMappingPanel_Id");
                if(isNotVal(panel)){
                	panel.add(gridPanel);
                }
            }
        },
        beforeload: function (store, options) {
        	var deviceType = Ext.getCmp("databaseColumnMappingDeviceTypeComb_Id").getValue();
            var new_params = {
            		deviceType: deviceType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
        }
    }
});