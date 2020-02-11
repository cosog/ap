Ext.define("AP.view.graphicalQuery.SurfaceCardPanel", { // 定义地面功图查询panel
    extend: 'Ext.panel.Panel', // 继承
    alias: 'widget.SurfaceCardPanel', // 定义别名
    id: 'SurfaceCardQuery_Id', //模块编号
    layout: 'fit', // 适应屏幕大小
    border: false,
    maskElement: 'body',
    initComponent: function () {
        var me = this;
        var org_Id = Ext.getCmp('leftOrg_Id').getValue();
        var wellListStore = new Ext.data.JsonStore({
            pageSize: defaultJhComboxSize,
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
                items: [{
                border: false,
                layout: 'fit',
                id: 'surfaceCardContent',
                autoScroll: true,
                html: '<div id="surfaceCardContainer" class="hbox"></div>',
                listeners: {
                    render: function (p, o, i, c) {
                        p.body.on('scroll', function () {
                            var totalPages = Ext.getCmp("SurfaceCardTotalPages_Id").getValue(); // 总页数
                            if (page < totalPages) {
                                var surfaceCardContent = Ext.getCmp("surfaceCardContent");
                                var hRatio = surfaceCardContent.getScrollY() / Ext.get("surfaceCardContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                                if (hRatio > 0.75) {
                                    if (page < 2) {
                                        page++;
                                        loadSurfaceCardList(page);
                                    } else {
                                        var divCount = $("#surfaceCardContainer div ").size();
                                        var count = (page - 1) * defaultGraghSize * 3;
                                        if (divCount > count) {
                                            page++;
                                            loadSurfaceCardList(page);
                                        }
                                    }
                                }
                            }
                        }, this);
                    }
                }
            }],
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    loadSurfaceCardList(1);
                }
            }
        });
        me.callParent(arguments);
    }
});

//功图列表鼠标滚动时自动加载
loadSurfaceCardList = function (page) {
    Ext.getCmp("SurfaceCardQuery_Id").mask(cosog.string.loading); // 数据加载中，请稍后
    var start = (page - 1) * defaultGraghSize;
    page=page;
    if(page=1){
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
            Ext.getCmp("SurfaceCardQuery_Id").unmask(cosog.string.loading); // 数据加载中，请稍后
            var get_rawData = Ext.decode(response.responseText); // 获取返回数据
            var gtlist = get_rawData.list; // 获取功图数据
            
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
}