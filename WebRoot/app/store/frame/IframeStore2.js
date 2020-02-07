Ext.define('AP.store.frame.IframeStore2', {
    extend: 'Ext.data.TreeStore',
    storeId: 'IframeStore_ids2',
    //folderSort : true,
    autoLoad: false,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: context + '/orgManagerController/constructOrgTree2',
        reader: {
            type: 'json',
            rootProperty: 'list',
            keepRawData: true
        },
        //传参
        //nodeParam : 'parentId',
        extraParams: {
            tid: '0'
        }
    },
    //根节点
    root: {
        text: '组织导航',
        expanded: true,
        orgId: '0'
    },
    listeners: {
        nodebeforeexpand: function (node, eOpts) {
            this.proxy.extraParams.tid = node.data.orgId;
        },
        load: function (store, options, eOpts) {
            Ext.getCmp("leftOrg_Id").setValue(""); // 将org_Id的值赋值给IframeView里的组织隐藏域
            Ext.getCmp("leftOrg_Id").setValue(userOrg_Ids); // 将org_Id的值赋值给IframeView里的组织隐藏域
            Ext.getCmp("leftOrg_Name").setValue("");// 将org_Id的值赋值给IframeView里的组织隐藏域
    		Ext.getCmp("leftOrg_Name").setValue(userOrg_Names);// 将org_Id的值赋值给IframeView里的组织隐藏域
            //alert(userOrg_Ids);
            //alert(userParentOrg_Ids);
        }
    }
});