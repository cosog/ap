Ext.define('AP.view.right.RightInfoGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.rightInfoGridPanel',
    layout: "fit",
    border: false,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selType: 'checkboxmodel',
    multiSelect: true,
    initComponent: function () {
        Ext.apply(this, {
            id: "rightInfoGridPanel_Id",
            tbar: [{
                id: 'rightName_Id',
                fieldLabel: '权限名称',
                name: 'rightName',
                labelWidth: 60,
                labelAlign: 'right',
                xtype: 'textfield'
         }, {
                xtype: 'button',
                name: 'rightNameBtn_Id',
                text: '检索',
                pressed: true,
                text_align: 'center',
                width: 50,
                iconCls: 'search',
                handler: function () {
                    var rightInfoView_Id = Ext
                        .getCmp("rightInfoGridPanel_Id");
                    rightInfoView_Id.getStore().load();
                }
         }, '->', {
                xtype: 'button',
                itemId: 'addrightLabelClassBtnId',
                id: 'addrightLabelClassBtn_Id',
                action: 'addrightAction',
                text: "创建",
                iconCls: 'note_add'
         }, "-", {
                xtype: 'button',
                itemId: 'delrightLabelClassBtnId',
                id: 'delrightLabelClassBtn_Id',
                disabled: true,
                action: 'delrightAction',
                text: "删除",
                iconCls: 'note_delete'
         }, {
                xtype: 'button',
                itemId: 'editrightLabelClassBtnId',
                id: 'editrightLabelClassBtn_Id',
                text: "修改",
                action: 'editrightInfoAction',
                disabled: true,
                iconCls: 'note_edit'
         }],
            dockedItems: [{
                dock: 'bottom',
                xtype: 'pagingtoolbar',
                // store : rightStore,
                pageSize: 20,
                displayInfo: true,
                displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
                emptyMsg: '没有数据'
         }],
            // store : rightStore,
            columnLines: true, // 列线
            stripeRows: true, // 条纹行
            columns: [{
                header: ' 权限名称',
                flex: 3,
                dataIndex: 'rightName'
         }, {
                header: '权限编码',
                flex: 3,
                dataIndex: 'rightCode',
                align: 'center',
                sortable: true
         }, {
                header: '权限类别',
                flex: 3,
                dataIndex: 'rightFlag'
         }]
        });

        this.callParent(arguments);

    },
    listeners: {
        selectionchange: function (sm, selections) {
            var n = selections.length || 0;
            this.down('#editrightLabelClassBtnId').setDisabled(n != 1);
            if (n > 0) {
                this.down('#addrightLabelClassBtnId').setDisabled(true);
                this.down('#delrightLabelClassBtnId')
                    .setDisabled(false);
            } else {
                this.down('#addrightLabelClassBtnId')
                    .setDisabled(false);
                this.down('#delrightLabelClassBtnId').setDisabled(true);
            }
        }
    }
});