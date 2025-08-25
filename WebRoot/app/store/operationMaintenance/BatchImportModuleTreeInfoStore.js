Ext.define('AP.store.operationMaintenance.BatchImportModuleTreeInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.batchImportModuleTreeInfoStore',
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
        	var gridPanel = Ext.getCmp("BatchImportModuleTreeGridPanel_Id");
            if (!isNotVal(gridPanel)) {
            	gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "BatchImportModuleTreeGridPanel_Id",
                    border: false,
                    animate: true,
                    enableDD: false,
                    useArrows: false,
                    rootVisible: false,
                    autoScroll: true,
                    forceFit: true,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true,
                        markDirty: false
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
                    	hidden:true,
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
                    	text: loginUserLanguageResource.data,
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
                        	var showInfo=loginUserLanguageResource.importData;
                        	var showInfo="【<font color=red>"+record.data.text+"</font>】"+showInfo+"&nbsp;"
                        	
                        	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').setTitle(showInfo);
                        	
                        	
                        	var records = Ext.getCmp("BatchImportModuleTreeGridPanel_Id").store.data.items.length;
                        	if(index==records-1){
                        		Ext.getCmp('OperationMaintenanceImportDataBackwardBtn_Id').disable();
                        		Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').enable();
                        	}else if(index==0){
                        		Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').disable();
                        	}else{
                        		Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').enable();
                        		Ext.getCmp('OperationMaintenanceImportDataBackwardBtn_Id').enable();
                        	}
                        	Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
                        	Ext.getCmp('OperationMaintenanceImportDataTipLabel_Id').hide();
                        	
                        	var verification=importedFilePermissionVerification(record.data.code);
                        	if(verification.sign){
                        		Ext.getCmp('OperationMaintenanceImportDataTipLabel_Id').setHtml('');
                        		Ext.getCmp('OperationMaintenanceImportDataTipLabel_Id').hide();
                        		
                        		Ext.getCmp('OperationMaintenanceImportForm_Id').enable();
                        	}else{
                        		Ext.getCmp('OperationMaintenanceImportDataTipLabel_Id').setHtml("【<font color=red>"+verification.info+"</font>】");
                        		Ext.getCmp('OperationMaintenanceImportDataTipLabel_Id').show();
                        		Ext.getCmp('OperationMaintenanceImportForm_Id').disable();
                        	}
                        	
                        	if(record.data.disabled){
                        		Ext.getCmp('OperationMaintenanceImportForm_Id').disable();
                        	}else{
                        		Ext.getCmp('OperationMaintenanceImportForm_Id').enable();
                        	}
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("BatchImportModuleTreePanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
    			gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(0, true);
            }else{
            	Ext.getCmp("BatchExportModuleDataListSelectRow_Id").setValue(-1);
            	Ext.getCmp("BatchExportModuleDataListSelectCode_Id").setValue("");
            	
            	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').setTitle(loginUserLanguageResource.importData);
            	Ext.getCmp('OperationMaintenanceDataImportPanel_Id').removeAll();
            	Ext.getCmp('OperationMaintenanceImportDataForwardBtn_Id').disable();
        		Ext.getCmp('OperationMaintenanceImportForm_Id').disable();
        		Ext.getCmp('OperationMaintenanceImportDataSaveBtn_Id').disable();
        		Ext.getCmp('OperationMaintenanceImportDataBackwardBtn_Id').disable();
            }
        }
    }
});