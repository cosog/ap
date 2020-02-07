all_loading = "";

Ext.define('AP.store.right.RightModuleTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.RightModuleTreeInfoStore',
    model: 'AP.model.right.RightModuleTreeInfoModel',
    autoLoad: true,
    folderSort: false,
    defaultRootId: '0',
    proxy: {
        type: 'ajax',
        url: context + '/moduleManagerController/constructRightModuleTreeGridTree',
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
            all_loading = new Ext.LoadMask(Ext.getBody().component, {
                msg: cosog.string.loading
            });
            all_loading.show();
        },
        load: function (store, options, eOpts) {
            var store_ = store;
            clkLoadAjaxFn(store);
            all_loading.hide();
        }
    }
});

function selectEachCombox(node, root) {
    if (null != root && root != "") {
        var chlidArray = node;
        if (!Ext.isEmpty(chlidArray)) {
            Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
                var x_node_seId = chlidArray[index].data.mdId;

                Ext.Array.each(root, function (name, index,
                    countriesItSelf) {
                    var menuselectid = root[index].rmModuleid;

                    // 处理已选择的节点
                    if (x_node_seId == menuselectid) {
                        childArrNode.set('checked', true);
                        childArrNode.expand('true');
                    }
                });
                // 递归
                if (childArrNode.childNodes != null) {
                    selectEachCombox(childArrNode.childNodes, root);
                }
            });
        }
    }
    return false;
};

clkLoadAjaxFn = function (store_) {
    var RightBottomRoleCodes_Id = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnModules?roleCode=' + RightBottomRoleCodes_Id,
        success: function (response, opts) {
            // 处理后
            var moduleIds = Ext.decode(response.responseText);
            if (null != moduleIds && moduleIds != "") {
                var getNode = store_.root.childNodes;
                selectEachCombox(getNode, moduleIds);
            }
        },
        failure: function (response, opts) {
            Ext.Msg.alert("信息提示", "后台获取数据失败！");
        }
    });
    return false;
}