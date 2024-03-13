Ext.define('AP.store.acquisitionUnit.ProtocolDeviceTypeChangeDeviceTypeListStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.ProtocolDeviceTypeChangeDeviceTypeListStore',
    model: 'AP.model.role.RightTabTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/constructProtocolConfigTabTreeGridTree',
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
            var treeGridPanel = Ext.getCmp("ProtocolDeviceTypeChangeDeviceTypeTreeGridView_Id");
            if (!isNotVal(treeGridPanel)) {
                var treeGridPanel = Ext.create('Ext.tree.Panel', {
                    id: "ProtocolDeviceTypeChangeDeviceTypeTreeGridView_Id",
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
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: [{
                    	xtype: 'treecolumn',
                    	text: '设备类型',
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
                var panel = Ext.getCmp("ProtocolDeviceTypeChangeWinDeviceTypeListPanel_Id");
                panel.add(treeGridPanel);
            }
        }
    }
});