var inverDiagramPage=1;
Ext.define("AP.view.electricAnalysis.RealtimeDiagramInfoView", { // 定义反演图形查询panel
    extend: 'Ext.panel.Panel', // 继承
    alias: 'widget.electricAnalysisRealtimeDiagramInfoView', // 定义别名
    id: 'ElectricAnalysisRealtimeDiagramInfoView_Id', //模块编号
    layout: 'fit', // 适应屏幕大小
    border: false,
    initComponent: function () {
        var me = this;
        var wellListStore = new Ext.data.JsonStore({
            pageSize: defaultJhComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/wellInformationManagerController/loadWellComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('electricAnalysisRealtimeDiagramWellCombo_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        wellName: wellName,
                        wellType:200
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var wellCombo = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.wellName,
                id: 'electricAnalysisRealtimeDiagramWellCombo_Id',
                store: wellListStore,
                labelWidth: 35,
                width: 135,
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
                    expand: function (sm, selections) {},
                    specialkey: function (field, e) {},
                    select: function (combo, record, index) {
                    	if(combo.value==""){
                    		Ext.getCmp("electricAnalysisRealtimeDiagramStartDate_Id").hide();
                    		Ext.getCmp("electricAnalysisRealtimeDiagramEndDate_Id").hide();
                    	}else{
                    		Ext.getCmp("electricAnalysisRealtimeDiagramStartDate_Id").show();
                    		Ext.getCmp("electricAnalysisRealtimeDiagramEndDate_Id").show();
                    	}
                    	loadElectricInverDiagramList(1);
                    }
                }
            });
        Ext.apply(me, {
                tbar:[wellCombo,"-",
                {
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: '',
                    labelWidth: 0,
                    width: 90,
                    format: 'Y-m-d ',
                    id: 'electricAnalysisRealtimeDiagramStartDate_Id',
                    value: '',
                    listeners: {
                    	select: function (combo, record, index) {
                    		loadElectricInverDiagramList(1);
                    	}
                    }
                },{
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: '至',
                    labelWidth: 15,
                    width: 105,
                    format: 'Y-m-d ',
                    id: 'electricAnalysisRealtimeDiagramEndDate_Id',
                    value: new Date(),
                    listeners: {
                    	select: function (combo, record, index) {
                    		loadElectricInverDiagramList(1);
                    	}
                    }
                },{
                    id: 'electricAnalysisRealtimeDiagramType_Id',//曲线类型
                    xtype: 'textfield',
                    value: 'FSDiagram',
                    hidden: true
                },{
                    xtype: 'radiogroup',
                    columns: 3,
                    vertical: true,
                    items: [
                    	{ boxLabel: '地面功图',width:70, name: 'electricAnalysisRealtimeDiagramType', inputValue: 'FSDiagram',checked:true },
                    	{ boxLabel: '电功图',width:58, name: 'electricAnalysisRealtimeDiagramType', inputValue: 'PSDiagram' },
                    	{ boxLabel: '电流图',width:58, name: 'electricAnalysisRealtimeDiagramType', inputValue: 'ASDiagram' }
                    ],
                    listeners: {
                    	change: function (radiogroup, newValue, oldValue,eOpts) {
                    		Ext.getCmp("electricAnalysisRealtimeDiagramType_Id").setValue(newValue.electricAnalysisRealtimeDiagramType);
//                    		alert(Ext.getCmp("electricAnalysisRealtimeDiagramType_Id").getValue());
                    		loadElectricInverDiagramList(1);
                    	}
                    }
                  
                } ,'->', {
                    id: 'electricAnalysisRealtimeDiagramTotalCount_Id',
                    xtype: 'component',
                    tpl: cosog.string.totalCount + ': {count}', // 总记录数
                    style: 'margin-right:15px'
                },{
                    id: 'electricAnalysisRealtimeDiagramTotalPages_Id', // 记录总页数
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                }],
                items: [{
                    border: false,
                    layout: 'fit',
                    id: 'electricAnalysisRealtimeDiagramContent',
                    autoScroll: true,
                    html: '<div id="electricAnalysisRealtimeDiagramContainer" class="hbox"></div>',
                    listeners: {
                        render: function (p, o, i, c) {
                            p.body.on('scroll', function () {
                                var totalPages = Ext.getCmp("electricAnalysisRealtimeDiagramTotalPages_Id").getValue(); // 总页数
                                if (inverDiagramPage < totalPages) {
                                    var electricAnalysisRealtimeDiagramContent = Ext.getCmp("electricAnalysisRealtimeDiagramContent");
                                    var hRatio = electricAnalysisRealtimeDiagramContent.getScrollY() / Ext.get("electricAnalysisRealtimeDiagramContainer").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                                    if (hRatio > 0.75) {
                                        if (inverDiagramPage < 2) {
                                        	inverDiagramPage++;
                                            loadElectricInverDiagramList(inverDiagramPage);
                                        } else {
                                            var divCount = $("#electricAnalysisRealtimeDiagramContainer div ").size();
                                            var count = (inverDiagramPage - 1) * defaultGraghSize * 3;
                                            if (divCount > count) {
                                            	inverDiagramPage++;
                                                loadElectricInverDiagramList(inverDiagramPage);
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
function loadElectricInverDiagramList(page) {
	inverDiagramPage=page;
    Ext.getCmp("electricAnalysisRealtimeDiagramContent").mask(cosog.string.loading); // 数据加载中，请稍后
    var start = (page - 1) * defaultGraghSize;
    var startDate = Ext.getCmp("electricAnalysisRealtimeDiagramStartDate_Id").rawValue;
    var endDate = Ext.getCmp("electricAnalysisRealtimeDiagramEndDate_Id").rawValue;
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var wellName = Ext.getCmp('electricAnalysisRealtimeDiagramWellCombo_Id').getValue();
    var diagramType=Ext.getCmp('electricAnalysisRealtimeDiagramType_Id').getValue();
    Ext.Ajax.request({
    	url:context + '/PSToFSController/getInverDiagramChartData',
        method: "POST",
        params: {
            orgId: orgId,
            wellName: wellName,
            startDate: startDate,
            endDate: endDate,
            limit: defaultGraghSize,
            start: start,
            page: page,
            diagramType:diagramType
        },
        success: function (response) {
        	if(page==1){
        		$("#electricAnalysisRealtimeDiagramContainer").html(''); // 将html内容清空
        	}
            Ext.getCmp("electricAnalysisRealtimeDiagramContent").unmask(cosog.string.loading); // 数据加载中，请稍后
            var get_rawData = Ext.decode(response.responseText); // 获取返回数据
            
            var totals = get_rawData.totals; // 总记录数
            var totalPages = get_rawData.totalPages; // 总页数
            Ext.getCmp("electricAnalysisRealtimeDiagramTotalPages_Id").setValue(totalPages);
            updateTotalRecords(totals,"electricAnalysisRealtimeDiagramTotalCount_Id");
            
            if(startDate==''){
            	Ext.getCmp("electricAnalysisRealtimeDiagramStartDate_Id").setValue(get_rawData.startDate);
            }
            if(endDate==''){
            	Ext.getCmp("electricAnalysisRealtimeDiagramEndDate_Id").setValue(get_rawData.endDate);
            }
            
            var diagramList = get_rawData.list; // 获取功图数据
            
            var electricAnalysisRealtimeDiagramContent = Ext.getCmp("electricAnalysisRealtimeDiagramContent"); // 获取功图列表panel信息
            var panelHeight = electricAnalysisRealtimeDiagramContent.getHeight(); // panel的高度
            var panelWidth = electricAnalysisRealtimeDiagramContent.getWidth(); // panel的宽度
            var scrollWidth = getScrollWidth(); // 滚动条的宽度
            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
            var gtWidth = (panelWidth - scrollWidth) / columnCount; // 有滚动条时图形宽度
            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
            var gtWidth2 = gtWidth + 'px';
            var gtHeight2 = gtHeight + 'px';
            var htmlResult = '';
            var divId = '';

            // 功图列表，创建div
            Ext.Array.each(diagramList, function (name, index, countriesItSelf) {
                var diagramId = diagramList[index].id;
                divId = 'electricAnalysisRealtimeDiagramDiv' + diagramId;
                htmlResult += '<div id=\"' + divId + '\"';
                htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';"';
                htmlResult += '></div>';
            });
            $("#electricAnalysisRealtimeDiagramContainer").append(htmlResult);
            Ext.Array.each(diagramList, function (name, index, countriesItSelf) {
                var diagramId = diagramList[index].id;
                divId = 'electricAnalysisRealtimeDiagramDiv' + diagramId;
                if(diagramType=="FSDiagram"){
                	showFSDiagramWithAtrokeSPM(diagramList[index],divId);
                }else if(diagramType=="PSDiagram"){
                	showPSDiagram(diagramList[index],divId);
                }else if(diagramType=="ASDiagram"){
                	showASDiagram(diagramList[index],divId);
                }
                
            });
        },
        failure: function () {
            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
        }
    });
}