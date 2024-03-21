Ext.define('AP.store.module.ModuleInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.moduleInfoStore',
    model: 'AP.model.module.ModuleInfoModel',
    autoLoad: true,
    folderSort: true,
    proxy: {
        type: 'ajax',
        url: context + '/moduleManagerController/constructModuleTreeGridTree',
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
            var get_rawData = store.proxy.reader.rawData;
            var ResHeadInfoGridPanel_Id = Ext.getCmp("moduleInfoTreeGridView_Id");
            if (!isNotVal(ResHeadInfoGridPanel_Id)) {
                var arrColumns = get_rawData.columns;
                var cloums = createTreeHeadColumns(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                var ResHeadrInfoGridViewPanelGrid = Ext.create('Ext.tree.Panel', {
                    id: "moduleInfoTreeGridView_Id",
                    border: false,
                    layout: "fit",
                    loadMask: true,
                    rootVisible: false,
                    useArrows: true,
                    draggable: false,
                    forceFit: true,
                    onlyLeafCheckable: false, // 所有结点可选，如果不需要checkbox,该属性去掉
                    singleExpand: false,
                    selType: (loginUserModuleManagementModuleRight.editFlag==1?'checkboxmodel':''),
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                        selectionchange: function (sm, selections) {
                        },
                        itemdblclick: function () {
                        	var ModuleManagementModuleEditFlag=parseInt(Ext.getCmp("ModuleManagementModuleEditFlag").getValue());
                            if(ModuleManagementModuleEditFlag==1){
                            	modifymoduleInfo();
                            }
                        }
                    }
                });
                var ModuleInfoTreeGridViewPanel_Id = Ext.getCmp("ModuleInfoTreeGridViewPanel_Id");
                ModuleInfoTreeGridViewPanel_Id.add(ResHeadrInfoGridViewPanelGrid);
            }

        },
        beforeload: function (store, options) {
            var module_name_Id = Ext.getCmp('module_name_Id');
            if (!Ext.isEmpty(module_name_Id)) {
                module_name_Id = module_name_Id.getValue();
            }

            var new_params = {
                moduleName: module_name_Id

            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }

});