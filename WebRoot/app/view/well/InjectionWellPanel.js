Ext.define('AP.view.well.InjectionWellPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.InjectionWellPanel',
    layout: 'fit',
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selType: 'checkboxmodel',
    multiSelect: true,
    initComponent: function () {
        var injectionwellStore = Ext.create('Ext.store.well.InjectionwellStore');
        Ext.apply(this, {
            id: 'InjectionwellPanel_Id',
            tbar: [{
                id: 'injectionwellproductionName_Id',
                fieldLabel: '井名',
                name: 'injectionwellName_Id',
                labelWidth: 80,
                labelAlign: 'right',
                xtype: 'textfield'
         }, {
                xtype: 'button',
                name: 'injectionwellproductionNameBtn_Id',
                text: '查询',
                pressed: true,
                text_align: 'center',
                width: 50,
                iconCls: 'search',
                handler: function () {
                    var WellPanel_Id = Ext
                        .getCmp("InjectionwellPanel_Id");
                    WellPanel_Id.getStore().load();
                }
         }, '->', {
                xtype: 'button',
                itemId: 'addreInjectionwellPanelLabelClassBtnId',
                id: 'addreInjectionwellPanelLabelClassBtn_Id',
                action: 'addreInjectionwellPanelAction',
                text: "创建",
                iconCls: 'add'
         }, "-", {
                xtype: 'button',
                itemId: 'editreInjectionwellPanelLabelClassBtnId',
                id: 'editreInjectionwellPanelLabelClassBtn_Id',
                text: "修改",
                action: 'editreInjectionwellPanelInfoAction',
                disabled: true,
                iconCls: 'edit'
         }, {
                xtype: 'button',
                itemId: 'delreInjectionwellPanelLabelClassBtnId',
                id: 'delreInjectionwellPanelLabelClassBtn_Id',
                disabled: true,
                action: 'delreInjectionwellPanelAction',
                text: "删除",
                iconCls: 'delete'
         }],
            dockedItems: [{
                dock: 'bottom',
                xtype: 'pagingtoolbar',
                store: injectionwellStore,
                pageSize: 20,
                displayInfo: true,
                displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
                emptyMsg: '没有数据'
         }],
            store: injectionwellStore,
            columnLines: true, // 列线
            stripeRows: true, // 条纹行
            columns: [new Ext.grid.RowNumberer({
                    text: '序号',
                    width: 50,
                    align: 'center',
                    locked: false,
                    renderer: function (value, metadata,
                        record, rowIndex) {
                        return (injectionwellStore.currentPage - 1) * (injectionwellStore.pageSize) + rowIndex + 1;
                    }
                }),
         /* {
								header : '井名',
								flex : 3,
								dataIndex : 'jh',
								sortable : true
							}, */
                {
                    header: '日配置水量(m^3/d)',
                    flex: 3,
                    dataIndex: 'przrl',
                    align: 'center',
                    sortable: true
       }, {
                    header: '实际日注水量(m^3/d)',
                    flex: 3,
                    dataIndex: 'sjrzrl',
                    align: 'center',
                    sortable: true
       }, {
                    header: '井口注入压力(Mpa)',
                    flex: 3,
                    dataIndex: 'jkzryl',
                    align: 'center',
                    sortable: true
       }, {
                    header: '套压(Mpa)',
                    flex: 3,
                    dataIndex: 'ty',
                    align: 'center',
                    sortable: true
       }, {
                    header: '井口流温(℃)',
                    flex: 3,
                    dataIndex: 'jklw',
                    align: 'center',
                    sortable: true
       }]
        })
        this.callParent(arguments);
    },
    listeners: {
        selectionchange: function (sm, selections) {
            var n = selections.length || 0;

            this.down('#editreInjectionwellPanelLabelClassBtnId')
                .setDisabled(n != 1);
            if (n > 0) {
                this.down('#addreInjectionwellPanelLabelClassBtnId')
                    .setDisabled(true);
                this.down('#delreInjectionwellPanelLabelClassBtnId')
                    .setDisabled(false);
            } else {
                this.down('#addreInjectionwellPanelLabelClassBtnId')
                    .setDisabled(false);
                this.down('#delreInjectionwellPanelLabelClassBtnId')
                    .setDisabled(true);
            }
        }
    }
})