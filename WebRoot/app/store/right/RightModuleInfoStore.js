Ext.define('AP.store.right.RightModuleInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.moduleInfoStore',
    model: 'AP.model.right.RightModuleInfoModel',
    autoLoad: true,
    pageSize: 20,
    proxy: {
        type: 'ajax',
        url: context + '/moduleManagerController/doShowRightModules',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: 20,
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData: true
        }
    },
    listeners: {
        beforeload: function (store, options) {
            var RightBottomRoleCodes_Id = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
            // 动态返回当前角色拥有哪些模块信息
            Ext.Ajax.request({
                method: 'POST',
                url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnModules?roleCode=' + RightBottomRoleCodes_Id,
                success: function (response, opts) {
                    // 处理后
                    // var moduleIds = Ext.decode(response.responseText);
                    Ext.getCmp("RighCurrentRoleModules_Id").setValue(response.responseText);

                },
                failure: function (response, opts) {
                    Ext.Msg.alert("信息提示", "后台获取数据失败！");
                }
            });
        }

    }

});