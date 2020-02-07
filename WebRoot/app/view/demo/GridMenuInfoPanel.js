Ext.define('AP.view.demo.GridMenuInfoPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.gridMenuInfoPanel',
    id: 'GridMenuInfoPanel_Id',
    layout: "fit",
    autoScroll: true,
    border: false,
    selType: 'checkboxmodel',
    disableSelection: false,
    columnLines: true, // 列线
    stripeRows: true, // 条纹行
    multiSelect: true,
    forceFit: true,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true,
        trackOver: false
    },
    initComponent: function () {
        var me = this;
        var columns = [
            {
                header: '编号',
                dataIndex: 'id'
            },
            {
                header: '名称',
                dataIndex: 'name'
            },
            {
                header: '描述',
                dataIndex: 'descn'
            }
               ];

        var data = [
          ['1', 'name1', 'descn1'],
          ['2', 'name2', 'descn2'],
          ['3', 'name3', 'descn3'],
          ['4', 'name4', 'descn4'],
          ['5', 'name5', 'descn5']
      ];

        var store = new Ext.data.ArrayStore({
            data: data,
            autoLoad: true,
            fields: [
                {
                    name: 'id'
                },
                {
                    name: 'name'
                },
                {
                    name: 'descn'
                }
          ]
        });
        //store.load();  
        //表格右键菜单  
        var contextmenu = new Ext.menu.Menu({
            id: 'theContextMenu',
            items: [{
                text: '查看',
                iconCls: 'search',
                handler: function () {
                    Ext.Msg.alert("系统提示", "右键菜单查看测试!");
                }
        }, {
                text: '修改',
                iconCls: 'edit',
                handler: function () {
                    Ext.Msg.alert("系统提示", "右键菜单修改测试!");
                }
        }, {
                text: '删除',
                iconCls: 'cancel',
                handler: function () {
                    Ext.Msg.alert("系统提示", "右键菜单删除测试!");
                }
        }]
        });

        me.on("itemcontextmenu", function (view, record, item, index, e) {
            e.preventDefault();
            contextmenu.showAt(e.getXY());
        });
        Ext.apply(me, {
            store: store,
            columns: columns
        });
        me.callParent(arguments);
    }
});