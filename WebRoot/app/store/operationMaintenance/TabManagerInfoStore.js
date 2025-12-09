Ext.define('AP.store.operationMaintenance.TabManagerInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.tabManagerInfoStore',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/operationMaintenanceController/constructDeviceTypeTreeData',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            rootProperty: 'children',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
        	
        	store.commitChanges();
        	
            var get_rawData = store.proxy.reader.rawData;
            var showChineseName=get_rawData.showChineseName;
            var showEnglishName=get_rawData.showEnglishName;
            var showRussianName=get_rawData.showRussianName;
            var deviceTypeMaintenanceTreeGridView = Ext.getCmp("deviceTypeMaintenanceTreeGridView_Id");
            if (!isNotVal(deviceTypeMaintenanceTreeGridView)) {
                deviceTypeMaintenanceTreeGridView = Ext.create('Ext.tree.Panel', {
                    id: "deviceTypeMaintenanceTreeGridView_Id",
                    border: false,
                    layout: "fit",
                    loadMask: true,
                    rootVisible: false,
                    useArrows: true,
                    draggable: false,
                    forceFit: false,
                    onlyLeafCheckable: false, // 所有结点可选，如果不需要checkbox,该属性去掉
                    singleExpand: false,
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: loginUserLanguageResource.deviceType,
                    	flex: 4,
                    	align: 'left',
                    	dataIndex: 'text'
                    },{
                    	text: loginUserLanguageResource.language_zh_CN,
                    	flex: 4,
                    	align: 'left',
                    	dataIndex: 'text_zh_CN',
                    	hidden:!showChineseName,
                    	editor: loginUserOperationMaintenanceModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1
                        }:"",
                    },{
                    	text: loginUserLanguageResource.language_en,
                    	flex: 4,
                    	align: 'left',
                    	dataIndex: 'text_en',
                    	hidden:!showEnglishName,
                    	editor: loginUserOperationMaintenanceModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1
                        }:"",
                    },{
                    	text: loginUserLanguageResource.language_ru,
                    	flex: 4,
                    	align: 'left',
                    	dataIndex: 'text_ru',
                    	hidden:!showRussianName,
                    	editor: loginUserOperationMaintenanceModuleRight.editFlag==1?{
                            allowBlank: false,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1
                        }:"",
                    }, {
                        header: loginUserLanguageResource.sortNum,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
//                        width: getLabelWidth(loginUserLanguageResource.sortNum,loginUserLanguage)+'px',
                        dataIndex: 'sortNum',
                        editor: loginUserOperationMaintenanceModuleRight.editFlag==1?{
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                            minValue: 1
                        }:""
                    }, {
                        header: loginUserLanguageResource.status,
                        xtype: 'checkcolumn',
                        disabled:loginUserOperationMaintenanceModuleRight.editFlag!=1,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        dataIndex: 'deviceTypeEnable',
                        headerCheckbox: false,
                        width: getLabelWidth(loginUserLanguageResource.enable,loginUserLanguage)+'px',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	
                    	    }
                    	}
                    },{
                    	header: 'deviceTypeId',
                    	hidden: true,
                    	dataIndex: 'deviceTypeId'
                    }],
                    listeners: {
                        selectionchange: function (sm, selections) {
                        },
                        itemdblclick: function () {
                        	
                        }
                    }
                });
                var operationMaintenanceDeviceTypeMaintenancePanel = Ext.getCmp("OperationMaintenanceDeviceTypeMaintenancePanel_Id");
                operationMaintenanceDeviceTypeMaintenancePanel.add(deviceTypeMaintenanceTreeGridView);
            }

        },
        beforeload: function (store, options) {
        	
        }
    }

});