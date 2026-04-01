Ext.define('AP.store.operationMaintenance.ProjectTabConfigInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.projectTabConfigInfoStore',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/operationMaintenanceController/loadDeviceTypeContentConfigTreeData',
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
            var projectTabConfigTreeGridView = Ext.getCmp("projectTabConfigTreeGridView_Id");
            if (!isNotVal(projectTabConfigTreeGridView)) {
                projectTabConfigTreeGridView = Ext.create('Ext.tree.Panel', {
                    id: "projectTabConfigTreeGridView_Id",
                    border: false,
                    layout: "fit",
                    loadMask: true,
                    rootVisible: false,
                    useArrows: true,
                    draggable: false,
                    forceFit: false,
                    onlyLeafCheckable: false, // 所有结点可选，如果不需要checkbox,该属性去掉
                    singleExpand: false,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.featureList,
                        flex: 2,
                        xtype: 'treecolumn',
                        dataIndex: 'text'
                    }],
                    listeners: {
                        selectionchange: function (sm, selections) {
                        },
                        itemdblclick: function () {
                        	modifymoduleInfo();
                        }
                    }
                });
                var OperationMaintenanceDeviceTypeContentConfigPanel = Ext.getCmp("OperationMaintenanceDeviceTypeContentConfigPanel_Id");
                
                if(isNotVal(OperationMaintenanceDeviceTypeContentConfigPanel)){
                	OperationMaintenanceDeviceTypeContentConfigPanel.add(projectTabConfigTreeGridView);
                }
            }

        },
        beforeload: function (store, options) {
            var deviceTypeId=0;
            if(Ext.getCmp("deviceTypeMaintenanceTreeGridView_Id").getSelectionModel().getSelection().length>0){
            	deviceTypeId = Ext.getCmp("deviceTypeMaintenanceTreeGridView_Id").getSelectionModel().getSelection()[0].data.deviceTypeId;
            }

            var new_params = {
            	deviceTypeId: deviceTypeId

            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }

});