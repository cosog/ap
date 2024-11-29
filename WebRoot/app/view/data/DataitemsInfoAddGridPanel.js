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
                    header: loginUserLanguageResource.dataColumnName,
                    align: 'center',
                    dataIndex: 'cname',
                    field: {
                        xtype: 'textfield'
                    }
                },
                {
                    header: loginUserLanguageResource.dataColumnCode,
                    align: 'center',
                    dataIndex: 'ename',
                    field: {
                        xtype: 'textfield'
                    }
                },
                {
                    header: loginUserLanguageResource.dataColumnParams,
                    align: 'center',
                    dataIndex: 'datavalue',
                    field: {
                        xtype: 'textfield'
                    }
                },
                {
                    header: loginUserLanguageResource.sortNum,
                    align: 'center',
                    dataIndex: 'sorts',
                    field: {
                        xtype: 'textfield'
                    }
                },

                {
                    xtype: 'checkcolumn',
                    header: loginUserLanguageResource.enable,
                    dataIndex: 'status',
                    editor: {
                        xtype: 'checkbox',
                        cls: 'x-grid-checkheader-editor'
                    }
          },
                {
                    header: loginUserLanguageResource.operation,
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