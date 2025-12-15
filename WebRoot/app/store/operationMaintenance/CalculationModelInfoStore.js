Ext.define('AP.store.operationMaintenance.CalculationModelInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.calculationModelInfoStore',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/operationMaintenanceController/getCalculationModelData',
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
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var gridPanel = Ext.getCmp("operationMaintenanceCalculationModelGridView_Id");
            if (!isNotVal(gridPanel)) {
                var gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "operationMaintenanceCalculationModelGridView_Id",
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    border: false,
                    stateful: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    selType: 'checkboxmodel',
                    multiSelect: true,
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
                        header: loginUserLanguageResource.name,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        sortable: false,
                        dataIndex: 'name',
                        flex:2,
                        editor: loginUserOperationMaintenanceModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    }, {
                        header: loginUserLanguageResource.calculateType,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'calculateType',
                        flex:2,
                        editor: loginUserOperationMaintenanceModuleRight.editFlag==1?{
                            xtype: 'combo',
                            typeAhead: true,
                            triggerAction: 'all',
                            allowBlank: false,
                            editable: false,
                            store: get_rawData.calculateTypeList,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: loginUserLanguageResource.sortNum,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'sort',
                        editor: loginUserOperationMaintenanceModuleRight.editFlag==1?{
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                            minValue: 1
                        }:""
                    },{
                    	header: 'modelId',
                    	hidden: true,
                    	dataIndex: 'modelId'
                    },{
                    	header: loginUserLanguageResource.save,
                    	xtype: 'actioncolumn',
                    	width: getLabelWidth(loginUserLanguageResource.save,loginUserLanguage)+'px',
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'submit',
                            tooltip: loginUserLanguageResource.save,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	
                            }
                        }]
                    },{
                    	header: loginUserLanguageResource.deleteData,
                    	xtype: 'actioncolumn',
                    	width: getLabelWidth(loginUserLanguageResource.deleteData,loginUserLanguage)+'px',
                        align: 'center',
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'delete',
                            tooltip: loginUserLanguageResource.deleteData,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	
                            }
                        }]
                    }],
                    listeners: {
                    	celldblclick : function( grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                    		
                    	}
                    }
                });
                var panel = Ext.getCmp("OperationMaintenanceCalculationModelListPanel_Id");
                panel.add(gridPanel);
            }
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().select(0, true);
        },
        beforeload: function (store, options) {
            var new_params = {
            		
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});