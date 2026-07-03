Ext.define('AP.store.well.ExportDeviceInfoApplicationScenariosListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.exportDeviceInfoApplicationScenariosListStore',
    fields: ['id','wellName'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/wellInformationManagerController/getApplicationScenariosList',
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
            var gridPanel = Ext.getCmp("ExportDeviceInfoApplicationScenariosListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ExportDeviceInfoApplicationScenariosListGridPanel_Id",
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
                        text: loginUserLanguageResource.applicationScenarios,
                        lockable: true,
                        align: 'center',
                        flex: 10,
                        sortable: false,
                        locked: false,
                        dataIndex: 'applicationScenariosName',
                        renderer: function (value) {
                        	if(isNotVal(value)){
    			        		return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
			        		}
                        }
                    }],
                    listeners: {
                    	select: function(grid, record, index, eOpts) {
                    		CreateAndLoadExportDeviceInfoTable(true);
                    	}
                    }
                });
                var panel = Ext.getCmp("ExportDeviceInfoApplicationScenariosInfoPanel_Id");
                if(isNotVal(panel)){
                	panel.add(gridPanel);
                }
            }
            gridPanel.getSelectionModel().select(0, true);
        },
        beforeload: function (store, options) {
        	
        },
        datachanged: function (v, o) {
        }
    }
});