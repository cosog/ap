Ext.define('AP.store.well.DeviceTypeChangeDeviceTypeListStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.DeviceTypeChangeDeviceTypeListStore',
    model: 'AP.model.role.RightTabTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/constructProtocolConfigTabTreeGridTreeWithoutRoot',
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
        },
        load: function (store, options, eOpts) {
        	var get_rawData = store.proxy.reader.rawData;
            var treeGridPanel = Ext.getCmp("DeviceTypeChangeDeviceTypeTreeGridView_Id");
            if (!isNotVal(treeGridPanel)) {
                var treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "DeviceTypeChangeDeviceTypeTreeGridView_Id",
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
                    	xtype: 'treecolumn',
                    	text: loginUserLanguageResource.deviceTypeList,
                    	flex: 8,
                    	align: 'left',
                    	dataIndex: 'text'
                    },{
                    	header: 'deviceTypeIdaa',
                    	hidden: true,
                    	dataIndex: 'deviceTypeId'
                    }],
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	
                        },
                        itemdblclick: function (grid, record, item, index, e, eOpts) {
                        	
                        },
                        itemclick: function (view,record,item,ndex,e,eOpts) {
                        	
                        },
                        select( v, record, index, eOpts ){
                        	
                        }
                    }

                });
                var panel = Ext.getCmp("DeviceTypeChangeWinTypeListPanel_Id");
                panel.add(treeGridPanel);
            }
        }
    }
});