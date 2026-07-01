Ext.define('AP.store.module.ModuleInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.moduleInfoStore',
    model: 'AP.model.module.ModuleInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
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
            var showChineseName=get_rawData.showChineseName;
            var showEnglishName=get_rawData.showEnglishName;
            var showRussianName=get_rawData.showRussianName;
            var ResHeadInfoGridPanel_Id = Ext.getCmp("moduleInfoTreeGridView_Id");
            if (!isNotVal(ResHeadInfoGridPanel_Id)) {
                var ResHeadrInfoGridViewPanelGrid = Ext.create('Ext.tree.Panel', {
                    id: "moduleInfoTreeGridView_Id",
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
                        header: loginUserLanguageResource.moduleName,
                        flex: 2,
                        xtype: 'treecolumn',
                        dataIndex: 'text'
                    },{
                    	text: loginUserLanguageResource.language_zh_CN,
                    	flex: 2,
                    	align: 'left',
                    	dataIndex: 'mdName_zh_CN',
                    	hidden:!showChineseName,
                    	editor: loginUserModuleManagementModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserModuleManagementModuleRight.editFlag!=1
                        }:"",
                    },{
                    	text: loginUserLanguageResource.language_en,
                    	flex: 2,
                    	align: 'left',
                    	dataIndex: 'mdName_en',
                    	hidden:!showEnglishName,
                    	editor: loginUserModuleManagementModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserModuleManagementModuleRight.editFlag!=1
                        }:"",
                    },{
                    	text: loginUserLanguageResource.language_ru,
                    	flex: 2,
                    	align: 'left',
                    	dataIndex: 'mdName_ru',
                    	hidden:!showRussianName,
                    	editor: loginUserModuleManagementModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserModuleManagementModuleRight.editFlag!=1
                        }:"",
                    }, {
                        header: loginUserLanguageResource.moduleIntroduction,
                        hidden: true,
                        flex: 2,
                        dataIndex: 'mdShowname'
                    }, {
                        header: loginUserLanguageResource.moduleIcon,
                        flex: 1,
                        dataIndex: 'mdIcon',
                        editor: loginUserModuleManagementModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserModuleManagementModuleRight.editFlag!=1
                        }:"",
                    }, {
                        header: loginUserLanguageResource.moduleType,
                        flex: 1,
                        dataIndex: 'mdTypeName',
                        editor: loginUserModuleManagementModuleRight.editFlag==1?{
                            xtype: 'combo',
                            typeAhead: true,
                            triggerAction: 'all',
                            allowBlank: false,
                            editable: false,
                            store: get_rawData.moduleTypeList,
                            disabled:loginUserModuleManagementModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                            return "<span data-qtip=" + (value == undefined ? "" : value) + ">" + (value == undefined ? "" : value) + "</span>";
                        }
                    }, {
                        header: loginUserLanguageResource.moduleSort,
                        flex: 1,
                        dataIndex: 'mdSeq',
                        editor: loginUserModuleManagementModuleRight.editFlag==1?{
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: true,
                            disabled:loginUserModuleManagementModuleRight.editFlag!=1,
                            minValue: 1
                        }:""
                    }],
                    listeners: {
                        selectionchange: function (sm, selections) {
                        },
                        itemdblclick: function () {
//                        	modifymoduleInfo();
                        }
                    }
                });
                var ModuleInfoTreeGridViewPanel_Id = Ext.getCmp("ModuleInfoTreeGridViewPanel_Id");
                
                if(isNotVal(ModuleInfoTreeGridViewPanel_Id)){
                	ModuleInfoTreeGridViewPanel_Id.add(ResHeadrInfoGridViewPanelGrid);
                }
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