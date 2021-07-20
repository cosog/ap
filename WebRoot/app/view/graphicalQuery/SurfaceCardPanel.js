var diagramPage=1;
Ext.define("AP.view.graphicalQuery.SurfaceCardPanel", { // 定义光杆功图查询panel
    extend: 'Ext.panel.Panel', // 继承
    alias: 'widget.SurfaceCardPanel', // 定义别名
    id: 'SurfaceCardQuery_Id', //模块编号
    layout: 'fit', // 适应屏幕大小
    border: false,
    maskElement: 'body',
    initComponent: function () {
        var me = this;
        var GraphicalQueryWellListStore = Ext.create("AP.store.graphicalQuery.GraphicalQueryWellListStore");
        var org_Id = Ext.getCmp('leftOrg_Id').getValue();
        var wellListStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: 'boxkey',
                type: 'string'
                }, {
                name: 'boxval',
                type: 'string'
                }],
            proxy: {
                url: context + '/wellInformationManagerController/loadWellComboxList',
                type: 'ajax',
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('FSDiagramAnalysisGraphicalQueryWellName_Id').getValue();
                    var new_params = {
                        orgId: org_Id,
                        wellName: wellName,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellListCombo = Ext.create('Ext.form.field.ComboBox', { // Simple ComboBox调用store
            fieldLabel: cosog.string.wellName,
            id: 'FSDiagramAnalysisGraphicalQueryWellName_Id',
            store: wellListStore,
            labelWidth: 35,
            width: 145,
            queryMode: 'remote',
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            typeAhead: true,
            autoSelect: false,
            allowBlank: true,
            triggerAction: 'all',
            editable: true,
            displayField: "boxval",
            valueField: "boxkey",
            pageSize: comboxPagingStatus,
            minChars: 0,
            listeners: {
                expand: function (sm, selections) {
                    wellListCombo.getStore().loadPage(1); // 加载井下拉框的store
                },
                select: function (combo, record, index) {
                	if(combo.value==""){
                		Ext.getCmp("SurfaceCard_from_date_Id").hide();
                		Ext.getCmp("SurfaceCard_to_date_Id").hide();
                	}else{
                		Ext.getCmp("SurfaceCard_from_date_Id").show();
                		Ext.getCmp("SurfaceCard_to_date_Id").show();
                	}
                    loadSurfaceCardList(1);
                }
            }
        });
        Ext.apply(me, {
            tbar: [ // 定义topbar
                wellListCombo, {
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: cosog.string.startDate,
                    labelWidth: 58,
                    width: 178,
                    format: 'Y-m-d ',
                    id: 'SurfaceCard_from_date_Id',
                    hidden:true,
                    listeners: {
                        select: function (combo, record, index) {
                            try {
                            	loadSurfaceCardList(1);
                            } catch (ex) {
                                Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                            }
                        }
                    }
                }, {
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: cosog.string.endDate,
                    labelWidth: 58,
                    width: 178,
                    format: 'Y-m-d',
                    altFormats: 'm,d,Y|m.d.Y',
                    id: 'SurfaceCard_to_date_Id',
                    hidden:true,
                    listeners: {
                        select: function (combo, record, index) {
                            try {
                            	loadSurfaceCardList(1);
                            } catch (ex) {
                                Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                            }
                        }
                    }
                },'-',{
                    xtype: 'button',
                    iconCls: 'note-refresh',
                    text: cosog.string.refresh,
                    pressed: true,
                    hidden:false,
                    handler: function (v, o) {
                    	loadSurfaceCardList(1);
                    }
                
        		}, '->', {
                    id: 'SurfaceCardTotalCount_Id',
                    xtype: 'component',
                    tpl: cosog.string.totalCount + ': {count}', // 总记录数
                    style: 'margin-right:15px'
                }, {
                    id: 'SurfaceCardTotalPages_Id', // 记录总页数
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }],
                layout: 'border',
                border: false,
                items: [{
                	region: 'west',
                	width: '25%',
                	title: '井列表',
                	id: 'GraphicalQueryWellListPanel_Id',
                	collapsible: true, // 是否可折叠
                    collapsed:false,//是否折叠
                    split: true, // 竖折叠条
                	layout: "fit"
                },{
                	region: 'center',
                	title:'图形数据',
                    border: false,
                    layout: "fit",
                    id: 'surfaceCardContent',
                    autoScroll: true,
                    html: '<div id="surfaceCardContainer" class="hbox"></div>',
                    listeners: {
                        render: function (p, o, i, c) {
                            p.body.on('scroll', function () {
                                var totalPages = Ext.getCmp("SurfaceCardTotalPages_Id").getValue(); // 总页数
                                if (diagramPage < totalPages) {
                                    var surfaceCardContent = Ext.getCmp("surfaceCardContent");
                                    var hRatio = surfaceCardContent.getScrollY() / Ext.get("surfaceCardContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                                    if (hRatio > 0.5) {
                                        if (diagramPage < 2) {
                                            diagramPage++;
                                            loadSurfaceCardList(diagramPage);
                                        } else {
                                            var divCount = $("#surfaceCardContainer div ").size();
                                            var count = (diagramPage - 1) * defaultGraghSize * 3;
                                            if (divCount > count) {
                                                diagramPage++;
                                                loadSurfaceCardList(diagramPage);
                                            }
                                        }
                                    }
                                }
                            }, this);
                        }
                    }
                }]
        });
        me.callParent(arguments);
    }
});

//功图列表鼠标滚动时自动加载
loadSurfaceCardList = function (page) {
	diagramPage=page;
    Ext.getCmp("surfaceCardContent").mask(cosog.string.loading); // 数据加载中，请稍后
    var start = (page - 1) * defaultGraghSize;
    page=page;
    if(page==1){
    	$("#surfaceCardContainer").html(''); // 将html内容清空
    }
    var start_date = Ext.getCmp("SurfaceCard_from_date_Id").rawValue;
    var end_date = Ext.getCmp("SurfaceCard_to_date_Id").rawValue;
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('FSDiagramAnalysisGraphicalQueryWellName_Id').getValue();
    Ext.Ajax.request({
        url: context + '/surfaceCardQueryController/querySurfaceCard',
        method: "POST",
        params: {
            orgId: leftOrg_Id,
            wellName: wellName,
            startDate: start_date,
            endDate: end_date,
            limit: defaultGraghSize,
            start: start,
            page: page
        },
        success: function (response) {
        	if(page==1){
        		$("#electricAnalysisRealtimeDiagramContainer").html(''); // 将html内容清空
        	}
            Ext.getCmp("surfaceCardContent").unmask(cosog.string.loading); // 数据加载中，请稍后
            var get_rawData = Ext.decode(response.responseText); // 获取返回数据
            var gtlist = get_rawData.list; // 获取功图数据
            
            var totals = get_rawData.totals; // 总记录数
            var totalPages = get_rawData.totalPages; // 总页数
            Ext.getCmp("SurfaceCardTotalPages_Id").setValue(totalPages);
            updateTotalRecords(totals,"SurfaceCardTotalCount_Id");
            
            var startDate=Ext.getCmp('SurfaceCard_from_date_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("SurfaceCard_from_date_Id").setValue(get_rawData.start_date);
            }
            
            var endDate=Ext.getCmp('SurfaceCard_to_date_Id').rawValue;
            if(startDate==''||null==startDate){
            	Ext.getCmp("SurfaceCard_to_date_Id").setValue(get_rawData.end_date);
            }
            
            
            var surfaceCardContent = Ext.getCmp("surfaceCardContent"); // 获取功图列表panel信息
            var panelHeight = surfaceCardContent.getHeight(); // panel的高度
            var panelWidth = surfaceCardContent.getWidth(); // panel的宽度
            var scrollWidth = getScrollWidth(); // 滚动条的宽度
            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
            var gtWidth = (panelWidth - scrollWidth) / columnCount; // 有滚动条时图形宽度
            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
            var gtWidth2 = gtWidth + 'px';
            var gtHeight2 = gtHeight + 'px';
            var htmlResult = '';
            var divId = '';

            // 功图列表，创建div
            Ext.Array.each(gtlist, function (name, index, countriesItSelf) {
                var gtId = gtlist[index].id;
                divId = 'gt' + gtId;
                htmlResult += '<div id=\"' + divId + '\"';
                htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';"';
                htmlResult += '></div>';
            });
            $("#surfaceCardContainer").append(htmlResult);
            Ext.Array.each(gtlist, function (name, index, countriesItSelf) {
                var gtId = gtlist[index].id;
                divId = 'gt' + gtId;
                showSurfaceCard(gtlist[index], divId); // 调用画功图的函数，功图列表
            });
        },
        failure: function () {
            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
        }
    });
};
function createGraphicalQueryWellListWellListDataColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_;
        if (attr.dataIndex == 'id') {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }else if (attr.dataIndex.toUpperCase()=='slave'.toUpperCase()) {
            myColumns += ",sortable : false,locked:true,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
        } else if (attr.dataIndex.toUpperCase() == 'acqTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
        } else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}";
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};