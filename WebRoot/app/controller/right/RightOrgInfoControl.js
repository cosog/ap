/*******************************************************************************
 * 定义 IrightControl AP 为在app.js里定义的命名空间
 *
 * @argument views： 改控制器里使用的view 为right.IrightView
 * @cfg refs： 通过别名来引用 改view irightView init：初始化改控制器 渲染irightViewd 的时候，
 *      配置一个itemclick 事件
 */
Ext.define('AP.controller.right.RightOrgInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'rightOrgInfoView',
        selector: 'rightOrgInfoView'
     }],
    init: function () {
        this.control({
            'rightOrgInfoView': {
                itemclick: extOrgTreeItemsClk
            }
        })
    }
});
/*******************************************************************************
 * 点击左侧组织树时，调用该方法。向后台传入一个组织的ID值orgId
 ******************************************************************************/
function extOrgTreeItemsClk(view, rec, item, index, e) {

    //var org_Id = rec.data.orgId;// 获取到当前点击的组织ID
    selectresult = [];
    var org_Id = selectEachTreeFn(rec); // 获取到当前点击的组织ID
    var RightOrgInfo_Id = Ext.getCmp("RightOrgInfo_Id");
    RightOrgInfo_Id.setValue("");
    RightOrgInfo_Id.setValue(org_Id);
    Ext.getCmp("RightUserInfoGridPanel_Id").getStore().load();
    Ext.getCmp("RightRoleInfoGridPanel_Id").getStore().load();
    return false;
};