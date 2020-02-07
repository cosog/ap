Ext.define("AP.view.balanceCycle.BalanceCycleInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.balanceCycleInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var CycleTorqueMaxValueView = Ext.create('AP.view.balanceCycle.CycleTorqueMaxValueView');
        Ext.apply(me, {
            items: [{
                id: 'BalanceCycleTab_Id',
                xtype: 'tabpanel',
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                        	title: '最大值法',
                        	layout: "fit",
                        	id: 'CycleTorqueMaxValue_Id',
                        	border: false,
                        	items: [CycleTorqueMaxValueView]
                		},{
                            title: '均方根法',
                            layout: "fit",
                            id: 'CycleTorqueMeanSquareRoot_Id',
                            border: false
                    	},{
                            title: '平均功率法',
                            layout: "fit",
                            id: 'CycleTorqueAveragePower_Id',
                            border: false
                    	}],
                    	listeners: {
                    		tabchange: function (tabPanel, newCard, oldCard,obj) {
                    			var tabPanel = Ext.getCmp("BalanceCycleTab_Id");
                    			var activeId = tabPanel.getActiveTab().id;
                    			if(activeId=="CycleTorqueMaxValue_Id"){
                    				var gridView=Ext.getCmp("TorqueMaxValueCycleWellListGrid_Id");
                                    if (gridView == undefined) {
                                        var CycleTorqueMaxValue = Ext.getCmp("CycleTorqueMaxValue_Id");
                                        CycleTorqueMaxValue.removeAll();
                                        var CycleTorqueMaxValueView = Ext.create('AP.view.balanceCycle.CycleTorqueMaxValueView');
                                        CycleTorqueMaxValue.add(CycleTorqueMaxValueView);
                                    }else{
                                    	var store=gridView.getStore().load();
                                    }
                                    Ext.getCmp("CycleTorqueMeanSquareRoot_Id").removeAll();
                                    Ext.getCmp("CycleTorqueAveragePower_Id").removeAll();
                    				
                    			}else if(activeId=="CycleTorqueMeanSquareRoot_Id"){
                    				var gridView=Ext.getCmp("TorqueMeanSquareRootCycleWellListGrid_Id");
                                    if (gridView == undefined) {
                                        var CycleTorqueMeanSquareRoot = Ext.getCmp("CycleTorqueMeanSquareRoot_Id");
                                        CycleTorqueMeanSquareRoot.removeAll();
                                        var CycleTorqueMeanSquareRootView = Ext.create('AP.view.balanceCycle.CycleTorqueMeanSquareRootView');
                                        CycleTorqueMeanSquareRoot.add(CycleTorqueMeanSquareRootView);
                                    }else{
                                    	var store=gridView.getStore().load();
                                    }
                                    Ext.getCmp("CycleTorqueMaxValue_Id").removeAll();
                                    Ext.getCmp("CycleTorqueAveragePower_Id").removeAll();
                    			}else if(activeId=="CycleTorqueAveragePower_Id"){
                    				var gridView=Ext.getCmp("TorqueAveragePowerCycleWellListGrid_Id");
                                    if (gridView == undefined) {
                                        var CycleTorqueAveragePower = Ext.getCmp("CycleTorqueAveragePower_Id");
                                        CycleTorqueAveragePower.removeAll();
                                        var CycleTorqueAveragePowerView = Ext.create('AP.view.balanceCycle.CycleTorqueAveragePowerView');
                                        CycleTorqueAveragePower.add(CycleTorqueAveragePowerView);
                                    }else{
                                    	var store=gridView.getStore().load();
                                    }
                                    Ext.getCmp("CycleTorqueMaxValue_Id").removeAll();
                                    Ext.getCmp("CycleTorqueMeanSquareRoot_Id").removeAll();
                    			}
                    		}
                    	}
            }]
        });
        me.callParent(arguments);
    
    }

});
function exportCycleBalanceStatusExcelData(jh,gkmc,gridId,type,fileName,title,url){
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var fields = "";
    var heads = "";
    var gridPanel = Ext.getCmp(gridId);
    var items_ = gridPanel.items.items;
    if(items_.length==1){//无锁定列时
    	var columns_ = gridPanel.columns;
    	Ext.Array.each(columns_, function (name,index, countriesItSelf) {
                var locks = columns_[index];
                if (index > 0 && locks.hidden == false) {
                    fields += locks.dataIndex + ",";
                    heads += locks.text + ",";
                }
            });
    }else{
    	Ext.Array.each(items_, function (name, index,countriesItSelf) {
                var datas = items_[index];
                var columns_ = datas.columns;
                if (index == 0) {
                    Ext.Array.each(columns_, function (name,
                        index, countriesItSelf) {
                        var locks = columns_[index];
                        if (index > 0 && locks.hidden == false) {
                            fields += locks.dataIndex + ",";
                            heads += locks.text + ",";
                        }
                    });
                } else {
                    Ext.Array.each(columns_, function (name,
                        index, countriesItSelf) {
                        var headers_ = columns_[index];
                        if (headers_.hidden == false) {
                            fields += headers_.dataIndex + ",";
                            heads += headers_.text + ",";
                        }
                    });
                }
            });
    }
    if (isNotVal(fields)) {
        fields = fields.substring(0, fields.length - 1);
        heads = heads.substring(0, heads.length - 1);
    }
    fields = "id," + fields;
    heads = "序号," + heads;
    var param = "&fields=" + fields +"&heads=" + URLencode(URLencode(heads)) + "&orgId=" + leftOrg_Id+ "&type="+type  + "&jh=" + URLencode(URLencode(jh))  + "&gkmc=" + URLencode(URLencode(gkmc)) + "&fileName="+URLencode(URLencode(fileName))+ "&title="+URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
};
function initCycleBalanceStatPieOrColChat(store,title,TabPanelId,PiePanelId,PieDivId) {
	var get_rawData = store.proxy.reader.rawData;
	var datalist=get_rawData.totalRoot;
	var pieDataStr="[";
	var columnDataStr = "[";
	for(var i=0;i<datalist.length;i++){
		pieDataStr+="['"+datalist[i].gkmc+"',"+datalist[i].wellcount+"]";
		columnDataStr+="{name:'"+datalist[i].gkmc+"',data:["+datalist[i].wellcount+"]}";
		if(i<datalist.length-1){
			pieDataStr+=",";
			columnDataStr+=",";
		}
	}
	pieDataStr+="]";
	columnDataStr+="]";
	var pieData = Ext.JSON.decode(pieDataStr);
	
	var categories_="['平衡状态']";
	var categories = Ext.JSON.decode(categories_);
	var colunmData = Ext.JSON.decode(columnDataStr);
	
	var tabPanel = Ext.getCmp(TabPanelId);
	var activeId = tabPanel.getActiveTab().id;
	if(activeId==PiePanelId){
		ShowCycleBalanceStatPieChart(PieDivId,title, "井数占", pieData);
	}else{
		
	}
};
//创建或更新实时监测列表数据
function CreateOrUpdateCycleBalanceData(CycleBalance,PanelId,GridPanelId) {
    Ext.getCmp(PanelId).removeAll();
    var dataStr = "{'items':" + JSON.stringify(CycleBalance) + "}";
    var storeData = Ext.JSON.decode(dataStr);
    var GridPanel = Ext.getCmp(GridPanelId);
    var store = Ext.create('Ext.data.Store', {
        fields: [{name: 'id',type: 'int'},{name: 'name',type: 'string'},{name: 'position',type: 'float'},{name: 'weight',type: 'float'}],
        data: storeData,
        proxy: {
            type: 'memory',
            reader: {
                type: 'json',
                root: 'items'
            }
        }
    });

    GridPanel = Ext.create('Ext.grid.Panel', {
        id: GridPanelId,
//        requires: [
//           	'Ext.grid.selection.SpreadsheetModel',
//           	'Ext.grid.plugin.Clipboard'
//        ],
//    xtype:'spreadsheet-checked',
//    selModel: {
//    	type: 'spreadsheet',
//    	columnSelect: true,
//    	checkboxSelect: true,
//    	pruneRemoved: false,
//    	extensible: 'xy'
//    },
//    plugins: [
//            'clipboard',
//            'selectionreplicator',
//            new Ext.grid.plugin.CellEditing({
//          	  clicksToEdit:2
//            })
//        ],
    border: false,
    stateful: true,
    autoScroll: true,
    columnLines: true,
    layout: "fit",
    stripeRows: true,
    forceFit: true,
    multiSelect: true,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
        store: store,
        columns: [
        	{ header: '序号',  dataIndex: 'id',align:'center',lockable:true,locked:false,width:50,xtype:'rownumberer' },
	        { header: '平衡块', dataIndex: 'name',align:'center',lockable:true,locked:false },
	        { header: '位置(cm)', dataIndex: 'position',align:'center',editor:{allowBlank:false} },
	        { header: '重量(kN)', dataIndex: 'weight',align:'center',editor:{allowBlank:false} }
      ]
    });

    Ext.getCmp(PanelId).add(GridPanel);
};