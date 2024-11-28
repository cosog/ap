Ext.define('AP.store.well.DeviceOrgChangeOrgListStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.deviceOrgChangeOrgListStore',
    model: 'AP.model.org.OrgInfoModel',
    autoLoad: false, // 自动加载数据
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: context + '/orgManagerController/constructOrgTreeGridTree',
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    //根节点
    root: {
        text: '组织导航',
        expanded: true,
        orgId: '0'
    },
    listeners: {
        load: function (store, options, eOpts) {
            var get_rawData = store.proxy.reader.rawData;
            var treePanel = Ext.getCmp("DeviceOrgChangeOrgListTreePanel_Id");
            if (!isNotVal(treePanel)) {
                var treePanel = Ext.create('Ext.tree.Panel', {
                    id: "DeviceOrgChangeOrgListTreePanel_Id",
                    border: false,
                    layout: "fit",
                    loadMask: true,
                    rootVisible: false,
                    useArrows: true,
                    draggable: false,
                    forceFit: true,
                    onlyLeafCheckable: false, // 所有结点可选，如果不需要checkbox,该属性去掉
                    singleExpand: false,
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>",
                        forceFit: true
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.orgName,
                        xtype: 'treecolumn',
                        dataIndex: 'text'
                    }]
                });
                var orgListPanel_Id = Ext.getCmp("DeviceOrgChangeWinOrgListPanel_Id");
                orgListPanel_Id.add(treePanel);
            }

        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var new_params = {
//            	orgId:orgId
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }

});