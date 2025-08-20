Ext.define('AP.store.operationMaintenance.ImportBackupAuxiliaryDeviceContentTreeInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.importBackupAuxiliaryDeviceContentTreeInfoStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/wellInformationManagerController/getUploadedAuxiliaryDeviceTreeData',
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
    listeners: {
        beforeload: function (store, options) {
        	var new_params = {
        			
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var gridPanel = Ext.getCmp("ImportBackupContentGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "ImportBackupContentGridPanel_Id",
                    border: false,
                    stateful: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.idx,loginUserLanguage)+'px',
                        xtype: 'rownumberer'
                    }, {
                        header: loginUserLanguageResource.deviceName,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'name',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.manufacturer,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'manufacturer',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.model,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'model',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.remark,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'remark',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.sortNum,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'sort',
                        flex:1
                    } ,{
                    	header: loginUserLanguageResource.collisionInfo,
                    	flex: 5,
                    	dataIndex: 'msg',
                    	align: 'center',
                    	renderer:function(value,o,p,e){
                    		return adviceImportBackupDataCollisionInfoColor(value,o,p,e);
                    	}
                    }],
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	
                        },
                        itemdblclick: function (grid, record, item, index, e, eOpts) {
                        	
                        },
                        itemclick: function (view,record,item,ndex,e,eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("OperationMaintenanceDataImportPanel_Id");
                panel.add(gridPanel);
            }
        }
    }
});