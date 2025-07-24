Ext.define('AP.store.orgAndUser.ImportOrganizationContentTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.importOrganizationContentTreeInfoStore',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/orgManagerController/getUploadedOrganizationTreeData',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
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
            var treeGridPanel = Ext.getCmp("ImportOrganizationContentTreeGridPanel_Id");
            if (!isNotVal(treeGridPanel)) {
                var treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ImportOrganizationContentTreeGridPanel_Id",
                    border: false,
                    layout: "fit",
                    loadMask: true,
                    rootVisible: false,
                    useArrows: true,
                    draggable: false,
                    forceFit: false,
                    onlyLeafCheckable: false, // 所有结点可选，如果不需要checkbox,该属性去掉
                    singleExpand: false,
//                    selType: 'checkboxmodel',
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.orgName,
                        lockable: true,
                        align: 'left',
                        flex: 3,
                        xtype: 'treecolumn',
                        dataIndex: 'text'
                    }, {
                        header: loginUserLanguageResource.sortNum,
                        lockable: true,
                        align: 'left',
                        flex: 1,
                        dataIndex: 'orgSeq'
                    },{
                    	header: loginUserLanguageResource.collisionInfo,
                    	flex: 5,
                    	dataIndex: 'msg',
                    	renderer:function(value,o,p,e){
                    		return adviceImportOrganizationCollisionInfoColor(value,o,p,e);
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
                var panel = Ext.getCmp("importOrganizationTreePanel_Id");
                panel.add(treeGridPanel);
            }
        }
    }
});