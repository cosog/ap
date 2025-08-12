Ext.define('AP.store.operationMaintenance.BatchExportModuleTreeInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.batchExportModuleTreeInfoStore',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/operationMaintenanceController/batchExportModuleList',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            rootProperty: 'totalRoot', 
            totalProperty:'totalCount',
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
        	var get_rawData = store.proxy.reader.rawData;
        	var gridPanel = Ext.getCmp("BatchExportModuleTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "BatchExportModuleTreeGridPanel_Id",
//                    layout: "fit",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
//                    selType: (loginUserOperationMaintenanceModuleRight.editFlag==1?'checkboxmodel':''),
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'checkcolumn',
                    	hideDirty: true,
                    	align: 'center',
                    	header: '',
                    	disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                    	dataIndex: 'checked',
                    	width: 30,
//                    	editor: {
//                        	xtype: 'checkbox',
//                            cls: 'x-grid-checkheader-editor',
//                        	allowBlank: false
//                        },
                        renderer: function(value, meta, record) {
                            var disabled = record.get('disabled');
                            if (disabled) {
                                meta.tdCls = (meta.tdCls || '') + ' disabled-checkcolumn';
                            }
                            return Ext.grid.column.Check.prototype.defaultRenderer.apply(this, arguments);
                        },
                    	listeners: {
                    		checkchange: function (sm, e, ival, o, n) {
                    			
                    		},
                    		beforecheckchange: function(column, recordIndex, checked, record) {
                                return !record.data.disabled;
                            }
                    	}
                    },{
                        header: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: 50,
                        xtype: 'rownumberer'
                    },{
                    	text: '数据',
                        flex: 8,
                        align: 'center',
                        dataIndex: 'text',
                        renderer: function (value,o,p,e) {
                        	if(p.data.disabled){
                        		o.style='color:gray;';
                        	}
                        	if(isNotVal(value)){
                        		return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        	}
                        }
                    }],
                    listeners: {
                    	checkchange: function (node, checked) {
                    		
                        },
                        selectionchange ( view, selected, eOpts ){
                        	
                        },select( v, record, index, eOpts ){
                        	Ext.getCmp("BatchExportModuleDataListSelectRow_Id").setValue(index);
                        	Ext.getCmp("BatchExportModuleDataListSelectCode_Id").setValue(record.data.code);
                        	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
                        	var showInfo='导入';
                        	var showInfo="【<font color=red>"+record.data.text+"</font>】"+showInfo+"&nbsp;"
                        	
                        	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').setTitle(showInfo);
                        	if(record.data.disabled){
//                        		Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').disable();
                        		Ext.getCmp('OperationMaintenanceImportForm_Id').disable();
                        		Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
//                        		Ext.getCmp('OperationMaintenanceImportDataBackwardBtn_Id').disable();
                        	}else{
//                        		Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').enable();
                        		Ext.getCmp('OperationMaintenanceImportForm_Id').enable();
                        		Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').enable();
//                        		Ext.getCmp('OperationMaintenanceImportDataBackwardBtn_Id').enable();
                        	}
                        	
                        	var records = Ext.getCmp("BatchExportModuleTreeGridPanel_Id").store.data.items.length;
                        	if(index==records-1){
                        		Ext.getCmp('OperationMaintenanceImportDataBackwardBtn_Id').disable();
                        		Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').enable();
                        	}else if(index==0){
                        		Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').disable();
                        	}else{
                        		Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').enable();
                        		Ext.getCmp('OperationMaintenanceImportDataBackwardBtn_Id').enable();
                        	}
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("BatchExportModuleTreePanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
    			gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            }else{
            	Ext.getCmp("BatchExportModuleDataListSelectRow_Id").setValue(-1);
            	Ext.getCmp("BatchExportModuleDataListSelectCode_Id").setValue("");
            	
            	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').setTitle('导入');
            	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
            	Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').disable();
        		Ext.getCmp('OperationMaintenanceImportForm_Id').disable();
        		Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        		Ext.getCmp('OperationMaintenanceImportDataBackwardBtn_Id').disable();
            }
        }
    }
});