Ext.define('AP.view.right.RightUserInfoGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.rightUserInfoGridPanel',
    layout: 'fit',
    border: false,
    autoScroll: true,
    id: "RightUserInfoGridPanel_Id",
    forceFit: true,
    columnLines: true, // 列线
    rowLines: true,
    stripeRows: true, // 条纹行
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selModel:{
    	selType:'checkboxmodel',
    	showHeaderCheckbox:false,
    	mode:'SINGLE'
    },
    initComponent: function () {
        var RightUserInfoStore = Ext.create("AP.store.right.RightUserInfoStore");
        Ext.apply(this, {
            store: RightUserInfoStore,
            columns: [{
                header: 'userNo',
                hidden: true,
                dataIndex: 'userNo'
     }, {
                header: cosog.string.userList,
                align: "center",
                dataIndex: 'userName'
     }],
            listeners: {
                // grid 触发select事件时
                selectionchange: function (view, selected, o) {
                    if (selected.length > 0) {
                        var userNo_ = selected[0].data.userNo;
                        Ext.getCmp("RightUserNo_Id").setValue(userNo_);
                    }
                    Ext.getCmp("RightRoleInfoGridPanel_Id").getStore().load();
                }
            }
        });
        this.callParent(arguments);
    }

});