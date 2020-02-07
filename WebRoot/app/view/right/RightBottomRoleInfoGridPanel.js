Ext.define('AP.view.right.RightBottomRoleInfoGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.rightBottomRoleInfoGridPanel',
    layout: 'fit',
    border: true,
    autoScroll: true,
    forceFit: true,
    columnLines: true, // 列线
    stripeRows: true, // 条纹行
    forceFit: true,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
//    selModel: Ext.create('Ext.selection.CheckboxModel', {
//        mode: "SINGLE",
//        showHeaderCheckbox: false
//    }),
    
    selModel:{
    	selType:'checkboxmodel',
    	showHeaderCheckbox:false,
    	mode:'SINGLE'
    },
    initComponent: function () {
        var RightBottomRoleInfoStore = Ext.create("AP.store.right.RightBottomRoleInfoStore");
        Ext.apply(this, {
            id: "RightBottomRoleInfoGridPanel_Id",
            store: RightBottomRoleInfoStore,
            columns: [{
                header: 'roleCode',
                hidden: true,
                dataIndex: 'roleCode'
     }, {
                header: cosog.string.roleList,
                align: 'center',
                dataIndex: 'roleName'
     }],
            listeners: {
                // grid 触发select事件时
                selectionchange: function (view, selected, o) {
                    var roleCode_ = selected[0].data.roleCode;
                    Ext.getCmp("RightBottomRoleCodes_Id").setValue(roleCode_);
                    Ext.getCmp("RightModuleTreeInfoGridPanel_Id").getStore().load();
                }
            }
        });

        this.callParent(arguments);

    }
});