Ext.define('AP.store.operationMaintenance.ImportBackupPrimaryDeviceContentTreeInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.ImportBackupPrimaryDeviceContentTreeInfoStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/wellInformationManagerController/getUploadedPrimaryDeviceTreeData',
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
                        dataIndex: 'deviceName',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.acqInstance,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'instanceName',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.displayInstance,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'displayInstanceName',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.alarmInstance,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'alarmInstanceName',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.reportInstance,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'reportInstanceName',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.deviceTcpType,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'tcpType',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.signInId,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'signInId',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.ipPort,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'ipPort',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.slave,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'slave',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.peakDelay,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'peakDelay',
                        flex:2
                    }, {
                        header: loginUserLanguageResource.sortNum,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'sortNum',
                        flex:1
                    } ,{
                    	header: loginUserLanguageResource.collisionInfo,
                    	flex: 4,
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