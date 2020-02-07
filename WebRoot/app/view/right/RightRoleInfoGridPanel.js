Ext.define('AP.view.right.RightRoleInfoGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.rightRoleInfoGridPanel',
    layout: 'fit',
    border: false,
    autoScroll: true,
    forceFit: true,
    columnLines: true, // 列线
    stripeRows: true, // 条纹行
    id: "RightRoleInfoGridPanel_Id",
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selType: 'checkboxmodel',
    multiSelect: true,
    initComponent: function () {
        var RightRoleInfoStore = Ext.create("AP.store.right.RightRoleInfoStore");
        Ext.apply(this, {
            store: RightRoleInfoStore,
            columns: [{
                header: 'roleCode',
                width: 30,
                hidden: true,
                dataIndex: 'roleCode'

   }, {
                header: cosog.string.roleList,
                align: "center",
                dataIndex: 'roleName',
                renderer: function (v, metaData, record, rowIndex, colIndex,
                    store) {
                    var userNo_ = Ext.getCmp("RightUserNo_Id").getValue();
                    var oldCodes_ = [];
                    // 动态返回当前用户拥有哪些角色信息
                    Ext.Ajax.request({
                        method: 'POST',
                        url: context + '/roleManagerController/doShowRightCurrentUsersOwnRoles?userNo=' + userNo_,
                        success: function (response, opts) {
                            // 处理后
                            var obj_ = response.responseText;
                            var roleCodes = Ext.decode(obj_);
                            if (isNotVal(roleCodes)) {
                                Ext.Array.each(roleCodes, function (name, index,
                                    countriesItSelf) {
                                    code_ = roleCodes[index].roleCode; // 当前tab对象
                                    oldCodes_.push(code_);
                                    if (record.data['roleCode'] == code_) { // 选择逻辑
                                        Ext.getCmp("RightRoleInfoGridPanel_Id").getSelectionModel().select(rowIndex, true);
                                    }
                                });
                                var oldCodes_Ids = "" + oldCodes_.join(",");
                                Ext.getCmp("RightOldRoleCodes_Id").setValue(oldCodes_Ids);
                            }
                        },
                        failure: function (response, opts) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    });

                    return v;
                }
   }],
            listeners: {
                /*afterrender: function(sm, selections) {	       
                   //得到grid的El对象
				    var columns_id=sm.columns[0].id;
					Ext.getDom(columns_id+"-titleContainer").innerHTML="";
				}*/
            }
        });

        this.callParent(arguments);

    }
});