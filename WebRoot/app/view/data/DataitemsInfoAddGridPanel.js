Ext.define('AP.view.data.DataitemsInfoAddGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.dataitemsInfoAddGridPanel',
    id: "DataitemsInfoAddGridPanelId",
    forceFit: true,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_ditmaddsId'><" + loginUserLanguageResource.emptyMsg + "></div>",
        forceFit: true
    },
//    height: 260,
    columnLines: true,
    border: false,
    layout: "fit",
    initComponent: function () {
        var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        });
        Ext.apply(this, {
            plugins: [cellEditing],
            store: Ext.create('Ext.data.Store', {
                fields: [
                'cname',
                'ename',
                'datavalue',
                'sorts',
                'status'
            ],
                data: []
            }),
            columns: [
                {
                    header: cosog.string.dataColumnName,
                    align: 'center',
                    dataIndex: 'cname',
                    field: {
                        xtype: 'textfield'
                    }
                },
                {
                    header: cosog.string.dataColumnCode,
                    align: 'center',
                    dataIndex: 'ename',
                    field: {
                        xtype: 'textfield'
                    }
                },
                {
                    header: cosog.string.dataColumnParams,
                    align: 'center',
                    dataIndex: 'datavalue',
                    field: {
                        xtype: 'textfield'
                    }
                },
                {
                    header: cosog.string.sorts,
                    align: 'center',
                    dataIndex: 'sorts',
                    field: {
                        xtype: 'textfield'
                    }
                },

                {
                    xtype: 'checkcolumn',
                    header: cosog.string.dataColumnEnabled,
                    dataIndex: 'status',
                    editor: {
                        xtype: 'checkbox',
                        cls: 'x-grid-checkheader-editor'
                    }
          },
                {
                    header: cosog.string.command,
                    align: 'center',
                    renderer: function () {
                        var str = "<font color=red style='cursor:pointer'>" + loginUserLanguageResource.deleteData + "</font>";
                        return str;
                    },
                    listeners: {
                        click: function (view, rec, item, index, e) {
                            var st = view.getStore();
                            var selectedRow = st.getAt(item);
                            st.remove(selectedRow);
                        }
                    }
         }
        ]
        });

        this.callParent(arguments);
    }
});