/*******************************************************************************
 * 扭矩最大值周期性系统评价视图
 *
 * @author zhao
 *
 */
var cycleGtPage=1;
var totalGtPage=1;

Ext.define("AP.view.balanceTotal.TotalTorqueMeanSquareRootView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.totalTorqueMeanSquareRootView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var TorqueMeanSquareRootTotalAnalysisDataStore= Ext.create("AP.store.balanceTotal.TorqueMeanSquareRootTotalAnalysisDataStore");
        var jhStore_B = new Ext.data.JsonStore({
        	pageSize:defaultJhhComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/monitorPumpingUnitParamsManagerController/queryMonitorPUJhh',
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
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var jh_tobj = Ext.getCmp('TorqueMeanSquareRootTotaljh_Id').getValue();
                    var new_params = {
                    	jh:jh_tobj,
                        type: 'jh',
                        orgId: org_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var jhComboBoxTotal = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.jh,
            id: "TorqueMeanSquareRootTotaljh_Id",
            store:jhStore_B,
            labelWidth: 35,
            width:145,
            queryMode: 'remote',
            typeAhead: true,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            pageSize:comboxPagingStatus,
            minChars:0,
            multiSelect:false, 
            listeners: {
                expand: function (sm, selections) {
//                	jhComboBoxTotal.clearValue();
                	jhComboBoxTotal.getStore().load();
                },
                select: function (combo, record, index) {
                	Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id").getStore().load();
                }
            }
        });
        Ext.apply(me, {
            items: [{
                layout: "border",
                border: false,
                items: [
                    {
                        region: 'west',
                        title: cosog.string.balanceStatusTable,
                        layout: 'border',
                        border: false,
                        width: '30%',
                        collapsible: true, // 是否折叠
                        split: true, // 竖折叠条
                        items: [{
                            region: 'north',
                            id: 'TotalTorqueMeanSquareRootBalaceStatusPanel_Id',
                            layout: 'fit',
                            border: false,
                            height: '50%',
                            collapsible: false, // 是否折叠
                            split: true, // 竖折叠条
                            tbar:[jhComboBoxTotal,{
                                id: 'TorqueMeanSquareRootBalanceTotalSelectedGkmc_Id',
                                xtype: 'textfield',
                                value: '',
                                hidden: true
                            },{
                             	id: 'TorqueMeanSquareRootTotalGtTotalPages_Id', // 记录功图总页数
                             	xtype: 'textfield',
                             	value: '',
                             	hidden: true
                             },{
                                xtype: 'button',
                                text: cosog.string.exportExcel,
                                pressed: true,
                                handler: function (v, o) {
                                	var url=context + '/balanceTotalAnalysisController/exportTotalBalanceStatusExcelData';
                                	var jh = Ext.getCmp('TorqueMeanSquareRootTotaljh_Id').getValue();
                                	var gkmc=Ext.getCmp('TorqueMeanSquareRootBalanceTotalSelectedGkmc_Id').getValue();
                                	var gridId="TorqueMeanSquareRootTotalAnalysisiDataGrid_Id";
                                	var type=2;
                                	var fileName=cosog.string.bananceAllDayExcel;
                                	var title=cosog.string.bananceAllDayExcel;
                                	exportTotalBalanceStatusExcelData(jh,gkmc,gridId,type,fileName,title,url);
                                }
                            },'->',{
                                xtype: 'button',
                                text: cosog.string.exportBalanceReport,
                                pressed: true,
                                handler: function (v, o) {
                                	var jlbh=Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id").getSelectionModel().getSelection()[0].data.id;
                                	var jh=Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id").getSelectionModel().getSelection()[0].data.jh;
                                	if(jlbh>0){
                                        var url=context + '/balanceTotalAnalysisController/exportWellTatalBalanceReport?jlbh='+jlbh+'&jh='+jh+'&fileName='+cosog.string.balanceTotalReportPdf+'&type=pdf';
                                        document.location.href = url;
                                	}
                                }
                            }]
                     }, {
                    	 region: 'center',
                         xtype: 'tabpanel',
                         id:'TotalStatTorqueMeanSquareRootTabpanel_Id',
                         activeTab: 0,
                         border: true,
                         tabPosition: 'top',
                         items: [
                         {
                             title:'统计图',
                             layout: 'fit',
                             id:'TotalStatTorqueMeanSquareRootPiePanel_Id',
                             html:'<div id="TotalBalanceStatTorqueMeanSquareRootPieDiv_Id" style="width:100%;height:100%;"></div>',
                         }
                         ],
                         listeners: {
                             tabchange: function (tabPanel, newCard, oldCard,obj) {},
                             resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	 var TorqueMeanSquareRootTotalAnalysisiDataGrid = Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id");
                            	 if(isNotVal(TorqueMeanSquareRootTotalAnalysisiDataGrid)){
                            		 Ext.create("AP.store.balanceTotal.TotalStatTorqueMeanSquareRootStore");
                            	 }
                             }
                         }
                     }]
                },
                    {
                        region: 'center',
                        border: false,
                        layout: 'fit',
                        id: 'TorqueMeanSquareRootTotalGtPanel_Id',
                        title: cosog.string.dynList,
                        autoScroll: true,
                        html: '<div id="TorqueMeanSquareRootTotalGtDiv_Id"  class="hbox"></div>',
                        listeners: {
                            render: function (p, o, i, c) {
                                p.body.on('scroll', function () {
                                    var totalPages = Ext.getCmp("TorqueMeanSquareRootTotalGtTotalPages_Id").getValue(); // 总页数
                                    if (totalGtPage < totalPages) {
                                        var TorqueMeanSquareRootTotalGtPanel = Ext.getCmp("TorqueMeanSquareRootTotalGtPanel_Id");
                                        var hRatio = TorqueMeanSquareRootTotalGtPanel.getScrollY() / Ext.get("TorqueMeanSquareRootTotalGtDiv_Id").dom.clientHeight; // 滚动条所在高度与内容高度的比值
                                        if (hRatio > 0.75) {
                                            if (totalGtPage < 2) {
                                           	 	totalGtPage++;
                                           	 	loadBananceTotalMeanSquareRootGtlList(totalGtPage);
                                            } else {
                                                var divCount = $("#TorqueMeanSquareRootTotalGtDiv_Id div ").size();
                                                var count = (totalGtPage - 1) * defaultGraghSize * 3;
                                                if (divCount > count) {
                                               	 	totalGtPage++;
                                               	 	loadBananceTotalMeanSquareRootGtlList(totalGtPage);
                                                }
                                            }
                                        }
                                    }
                                }, this);
                            },
                            resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                            	var grid=Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id");
                           	 	if(isNotVal(grid)){
                           	 		totalGtPage=1;
                           	 		Ext.create("AP.store.balanceTotal.TorqueMeanSquareRootTotalGtStore");//加载功图
                           	 	}
                            }
                        }
                }]
            }]
        });
        me.callParent(arguments);
    }

});

//功图列表鼠标滚动时自动加载
loadBananceTotalMeanSquareRootGtlList = function (page) {
    Ext.getCmp("TorqueMeanSquareRootTotalGtPanel_Id").mask(cosog.string.loading); // 数据加载中，请稍后
    var start = (page - 1) * defaultGraghSize;
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();;
    var end_date = Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id").getSelectionModel().getSelection()[0].data.jssj;
    var jh =Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id").getSelectionModel().getSelection()[0].data.jh;
    Ext.Ajax.request({
        url: context + '/balanceTotalAnalysisController/getTotalSurfaceCardData',
        method: "POST",
        params: {
            orgId: leftOrg_Id,
            jh: jh,
            endDate: end_date,
            limit: defaultGraghSize,
            start: start,
            page: page,
            type:2
        },
        success: function (response) {
            Ext.getCmp("TorqueMeanSquareRootTotalGtPanel_Id").unmask(cosog.string.loading); // 数据加载中，请稍后
            var get_rawData = Ext.decode(response.responseText); // 获取返回数据
            var graphicallist = get_rawData.list; // 获取功图数据
            
            var TorqueMeanSquareRootTotalGtPanel = Ext.getCmp("TorqueMeanSquareRootTotalGtPanel_Id"); // 获取功图列表panel信息
            var panelHeight = TorqueMeanSquareRootTotalGtPanel.getHeight(); // panel的高度
            var panelWidth = TorqueMeanSquareRootTotalGtPanel.getWidth(); // panel的宽度
            var scrollWidth = getScrollWidth(); // 滚动条的宽度
            var columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
            var gtWidth = (panelWidth - scrollWidth) / columnCount; // 有滚动条时图形宽度
            var gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
            var gtWidth2 = gtWidth + 'px';
            var gtHeight2 = gtHeight + 'px';
            var htmlResult = '';
            var divId = '';

            // 功图列表，创建div
            Ext.Array.each(graphicallist, function (name, index, countriesItSelf) {
                var graphicalId = graphicallist[index].id;
                divId = 'TorqueMeanSquareRootTotalGt' + graphicalId;
                htmlResult += '<div id=\"' + divId + '\"';
                htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';"';
                htmlResult += '></div>';
            });
            $("#TorqueMeanSquareRootTotalGtDiv_Id").append(htmlResult);
            Ext.Array.each(graphicallist, function (name, index, countriesItSelf) {
                var graphicalId = graphicallist[index].id;
                divId = 'TorqueMeanSquareRootTotalGt' + graphicalId;
                showBalanceCycleSurfaceCardChart(graphicallist[index], divId,Ext.getCmp("TorqueMeanSquareRootTotalWellListGrid_Id").getSelectionModel().getSelection()[0].data.jh); // 调用画功图的函数，功图列表
            });
        },
        failure: function () {
            Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
        }
    });
}