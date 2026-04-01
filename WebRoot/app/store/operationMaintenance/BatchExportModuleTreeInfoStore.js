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
                        	
                        },beforecellcontextmenu: function (pl, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("BatchExportModuleTreePanel_Id");
                
                if(isNotVal(panel)){
                	panel.add(gridPanel);
                }
            }
        }
    }
});