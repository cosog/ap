var store; //store   
var cm; //columnModel   
var pagingBar; //pagingBar   
var grid; //grid   
var pageSize = 10; //pageSize   
Ext.define("AP.view.demo.DynamicGridPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dynamicGridPanel',
    id: "dynamicPanelView_Id",
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        /*  
         *  获取表字段
         */
        Ext.Ajax.request({
            url: context + '/monitorDataController/showMonitorHistoryData',
            params: {
                id: '6'
            },
            success: function (response, options) {
                var array = Ext.decode(response.responseText);
                if (array != null && array.columns != null) {
                    var record = array.columns; //data是前台传递的JavaBean   
                    grid = createGrid(record);
                    //Ext.getCmp("dynamicGridPanel_Id").reconfigure(store,cm);  //定义grid的store和column   
                    var dynamicGridPanel_Id = Ext.getCmp("dynamicGridPanel_Id");
                    if (isNotVal(dynamicGridPanel_Id)) {
                        me.add(grid);
                    }
                    //Ext.getCmp("dynamicGridPanel_Id").render();    
                }
            }
        });
        Ext.apply(me, {
            tbar: ['->', {
                xtype: 'button',
                text: '查询',
                pressed: true,
                iconCls: 'search',
                handler: function (v, o) {
                    Ext.getCmp('dynamicGridPanel_Id').getSelectionModel().clearSelections();
                    var dynamicGridPanel_Id = Ext.getCmp("dynamicGridPanel_Id");
                    dynamicGridPanel_Id.getStore().load();
                }
     }]


        });
        me.callParent(arguments);
    }
});



/*  
 * 创建store
 */
var createStore = function (record) {
    // var arrayField = record.name.split(',');   
    var str = "{'fields':[";
    for (var i = 0; i < record.length; i++) {
        str = str + "'" + record[i].dataIndex + "',";
    }
    var fields = str.substring(0, str.length - 1) + ']}';
    var jsonFields = Ext.decode(fields);
    store = new Ext.data.JsonStore({
        buffered: true,
        leadingBufferZone: 200,
        pageSize: defaultPageSize,
        remoteSort: true,
        autoLoad: true,
        proxy: {
            type: 'ajax',
            url: context + '/monitorDataController/showMonitorHistoryData',
            actionMethods: {
                read: 'POST'
            },
            start: 0,
            limit: defaultPageSize,
            reader: {
                type: 'json',
                rootProperty: 'totalRoot',
                totalProperty: 'totalCount'
            }
        },
        fields: jsonFields.fields,
        autoLoad: true
    });
    return store;
};
/*  
 * 创建column
 */
var createColumn = function (record) {
    // var arrayHead = record.fieldName.split(',');   
    // var arrayIndex= record.name.split(',');   
    // var columnArray = new Array();   
    //var rowNumber = new Ext.grid.RowNumberer();   
    // columnArray[record.length]=rowNumber;   

    var str = "{'columnModel':[ {header : '序号',xtype: 'rownumberer','width':40,'align':'center'},";
    for (var i = 1; i < record.length; i++) {
        var data = record[i];
        if (data.dataType == 'gtsj') {
            str = str + "{'header':'" + data.header + "',dataIndex:'" + data.dataIndex + "','align':'center','hidden':" + data.hidden + ",renderer :function(value,e,o){return iconGtsj(value,e,o)} },";
        } else if (data.dataType == 'dlqx') {
            str = str + "{'header':'" + data.header + "',dataIndex:'" + data.dataIndex + "','align':'center','hidden':" + data.hidden + ",renderer :function(value,e,o){return iconDlqx(value,e,o)} },";
        } else if (data.dataType == 'bgt') {
            str = str + "{'header':'" + data.header + "',dataIndex:'" + data.dataIndex + "','align':'center','hidden':" + data.hidden + ",renderer :function(value,e,o){return iconBgt(value,e,o)} },";
        } else if (data.dataIndex == 'bjbz') {
            str = str + "{'header':'" + data.header + "',dataIndex:'" + data.dataIndex + "','align':'center','hidden':" + data.hidden + ",renderer:function(value){return alarmType(value) } },";
        } else if (data.dataIndex == 'gkmc') {
            str = str + "{'header':'" + data.header + "',dataIndex:'" + data.dataIndex + "','align':'center','hidden':" + data.hidden + ",renderer:function(value,o,p,e){return adviceColor(value,o,p,e) } },";
        } else if (data.dataType == 'timestamp') {
            str = str + "{'header':'" + data.header + "',dataIndex:'" + data.dataIndex + "','align':'center','hidden':" + data.hidden + ",'width':" + data.width + " },";
        } else {
            str = str + "{'header':'" + data.header + "',dataIndex:'" + data.dataIndex + "','align':'center','hidden':" + data.hidden + "},";
        }
    }
    str = str.substring(0, str.length - 1) + "]}";
    var jsonColumn = Ext.decode(str);
    return jsonColumn.columnModel;
};

/*  
 * 创建pagingToolbar
 */
var createPage = function (store) {
    pagingBar = new Ext.PagingToolbar({
        pageSize: pageSize,
        store: store,
        displayInfo: true
    });
    return pagingBar;
};
/*  
 * 创建grid
 */
createGrid = function (record) {
    store = createStore(record);
    cm = createColumn(record);
    pagingBar = createPage(store);
    grid = Ext.create("Ext.grid.Panel", {
        id: 'dynamicGridPanel_Id',
        stateful: true,
        autoScroll: true,
        columnLines: true,
        layout: "fit",
        loadMask: true,
        stripeRows: true,
        displayInfo: true,
        //forceFit : true,
        viewConfig: {
            emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
            forceFit: true,
            trackOver: false

        },
        multiSelect: true,
        selModel: {
            pruneRemoved: false
        },
        columns: cm,
        store: store
    });
    return grid;
};