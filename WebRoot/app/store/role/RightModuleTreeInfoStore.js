all_loading = "";

Ext.define('AP.store.role.RightModuleTreeInfoStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.RightModuleTreeInfoStore',
    model: 'AP.model.role.RightModuleTreeInfoModel',
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
//            all_loading = new Ext.LoadMask(Ext.getBody().component, {
//                msg: cosog.string.loading
//            });
//            all_loading.show();
        },
        load: function (store, options, eOpts) {
            var store_ = store;
            clkLoadAjaxFn(store);
//            all_loading.hide();
            var roleCode="";
            if(Ext.getCmp("RoleInfoGridPanel_Id")!=undefined){
            	var _record = Ext.getCmp("RoleInfoGridPanel_Id").getSelectionModel().getSelection();
                if(_record.length>0){
                	roleCode=_record[0].data.roleCode;
                }
            }
            if(roleCode=="sysAdmin"){
                Ext.getCmp("RightModuleTreeInfoGridPanel_Id").disable();
            }else{
            	Ext.getCmp("RightModuleTreeInfoGridPanel_Id").enable();
            }
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
    var RightBottomRoleId = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnModules?roleId=' + RightBottomRoleId,
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