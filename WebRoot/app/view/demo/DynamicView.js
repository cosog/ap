Ext.define("AP.view.demo.DynamicView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dynamicView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        // LoadingWin("正在加载数据");
        // 检索动态列表头及内容信息
        Ext.Ajax.request({
            method: 'POST',
            url: context + '/app/data/DyC.json',
            success: function (request) {
                // 处理后json
                var obj = Ext.JSON.decode(request.responseText);
                createGrid("dynamicgrid_ids", "周期", "week",
                    obj, me);
                // ==end
            },
            failure: function () {
                Ext.Msg.alert("信息提示", "后台获取数据失败！");
            }
        });

        Ext.apply(me, {
            tbar: [{
                xtype: 'textfield',
                fieldLabel: '名 称',
                width: 210,
                labelWidth: 60,
                id: 'dynamictext_name_Id'
         }, {
                xtype: 'button',
                id: "findDynamicBtnId",
                text: '检索',
                pressed: true,
                iconCls: 'find'
         }]
        });
        me.callParent(arguments);
    }

});
// 生成Grid
/**
 * grid的id grouptitle:分组名称 obj:数组对象 me:添加对象
 */
createGrid = function (gridid, grouptitle, groupf, obj, me) {

    var arrColumns = obj.columns;
    // 应射表头对照fields
    var cfields = createFields(arrColumns).split(",");

    // Grid-store 数据 data
    var totalRoot = obj.totalRoot;
    var ceo = createColumn(arrColumns);
    var columnsa = Ext.JSON.decode(ceo);
    alert(ceo);
    var simpsonsStore = Ext.create('Ext.data.Store', {
        fields: Ext.JSON.decode(cmFields(cfields)),
        data: totalRoot,
        sorters: [groupf, groupf],
        groupField: groupf,
        rootProperty: 'totalRoot',
        listeners: {
            load: function (store, options, eOpts) {
                var task = new Ext.util.DelayedTask(function () {
                    Ext.MessageBox.close();
                });
                task.delay(100);
            },
            beforeload: function (store, options) {
                var task = new Ext.util.DelayedTask(function () {
                    // LoadingWin("正在加载数据");
                });
                task.delay(100);
            }
        }
    });

    /*
     * var bbar = new Ext.PageNumberToolbar({ store:simpsonsStore, pageSize :10,
     * displayInfo : true, displayMsg : '当前记录 {0} -- {1} 条 共 {2} 条记录', emptyMsg :
     * "没有记录可显示", prevText : "上一页", nextText : "下一页", refreshText : "刷新",
     * lastText : "最后页", firstText : "第一页", beforePageText : "当前页",
     * afterPageText : "共{0}页" });
     */
    var groupingFeature = Ext.create('Ext.grid.feature.Grouping', {
        groupHeaderTpl: '{name} ',
        showGroupsText: '按分组显示',
        groupByText: '按此列分组',
        depthToIndent: 100,
        hideGroupedHeader: false,
        hideGroupedColumn: false,
        startCollapsed: false, // 分组后，所有组是展开还是收缩
        forceFit: true
    });
    // ==动态生成grid
    var oogrid = Ext.create('Ext.grid.Panel', {
        id: gridid,
        // bbar:bbar,
        border: false,
        viewConfig: {
            emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
            forceFit: true
        },
        selType: 'checkboxmodel',
        multiSelect: true,
        store: simpsonsStore,
        features: [groupingFeature],
        columns: Ext.JSON.decode(createColumn(arrColumns))
    });
    me.add(oogrid);

    return false;
};
// 生成关联的CM
cmFields = function (cFields) {
    var myFields = "[";
    for (var i = 0; i < cFields.length; i++) {
        myFields += "{name:'" + cFields[i] + "',mapping:'" + cFields[i] + "' }";
        if (i < cFields.length - 1) {
            myFields += ",";
        }
    };
    myFields += "]";
    return myFields;
};
// 生成Fields

createFields = function (columnInfo) {
    var cFields = "";
    var column = columnInfo;
    if (isNotVal(column.length) && column.length > 0) {
        Ext.Array.each(column, function (name, index, countriesItSelf) {
            var row = column[index];
            var col = row.children;
            if (col.length > 0) {
                Ext.Array.each(col, function (name, index,
                    countriesItSelf) {
                    var c_a = col[index];
                    cFields += c_a.dataIndex + ",";
                });
            } else {
                cFields += row.dataIndex + ",";
            }
        });
        if (isNotVal(cFields)) {
            cFields = cFields.substring(0, cFields.length - 1);
        }
    }
    return cFields;
};
// 生成Grid-Fields
createColumn = function (columnInfo) {
        var myArr = columnInfo;
        var myColumns = "[";
        for (var i = 0; i < myArr.length; i++) {
            var attr = myArr[i];
            var cAttrlength = attr.children.length;
            myColumns += "{text:'" + attr.header + "'";
            if (cAttrlength > 0) {
                myColumns += createChildHeader(attr.children);
            } else {
                myColumns += ",width:150,dataIndex:'" + attr.dataIndex + "'";
            }
            myColumns += "}";
            if (i < myArr.length - 1) {
                myColumns += ",";
            }
        }
        myColumns += "]";
        return myColumns;
    }
    // 生成Grid-columns Header
createChildHeader = function (columnInfo) {
    var myArr = columnInfo;
    var myColumns = ",columns:[";
    for (var i = 0; i < myArr.length; i++) {
        var attrObj = myArr[i];
        myColumns += "{text:'" + attrObj.header + "',width:150,dataIndex:'" + attrObj.dataIndex + "'}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
}

function isNotVal(bol) {
    var flag = false;
    if (bol != null) {
        flag = true;
    }
    return flag;
}