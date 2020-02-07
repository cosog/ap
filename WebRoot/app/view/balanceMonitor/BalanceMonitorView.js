/*******************************************************************************
 * 平衡监测视图
 *
 * @author zhao
 *
 */
Ext.define("AP.view.balanceMonitor.BalanceMonitorView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.balanceMonitorView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var monitorTorqueMaxValuePanel = Ext.create('AP.view.balanceMonitor.MonitorTorqueMaxValueView');
        Ext.apply(me, {
            items: [{
                id: 'BalanceMonitorTab_Id',
                xtype: 'tabpanel',
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                        	title: '最大值法',
                        	layout: "fit",
                        	id: 'MonitorTorqueMaxValue_Id',
                        	border: false,
                        	items: [monitorTorqueMaxValuePanel]
                		},{
                            title: '均方根法',
                            layout: "fit",
                            id: 'MonitorTorqueMeanSquareRoot_Id',
                            border: false
                    	},{
                            title: '平均功率法',
                            layout: "fit",
                            id: 'MonitorTorqueAveragePower_Id',
                            border: false
                    	}],
                    	listeners: {
                    		tabchange: function (tabPanel, newCard, oldCard,obj) {
                    			var tabPanel = Ext.getCmp("BalanceMonitorTab_Id");
                    			var activeId = tabPanel.getActiveTab().id;
                    			if(activeId=="MonitorTorqueMaxValue_Id"){
                    				var gridView=Ext.getCmp("MonitorTorqueMaxValueGrid_Id");
                                    if (gridView == undefined) {
                                        var MonitorTorqueMaxValue = Ext.getCmp("MonitorTorqueMaxValue_Id");
                                        MonitorTorqueMaxValue.removeAll();
                                        var MonitorTorqueMaxValueView = Ext.create('AP.view.balanceMonitor.MonitorTorqueMaxValueView');
                                        MonitorTorqueMaxValue.add(MonitorTorqueMaxValueView);
                                    }else{
                                    	var store=gridView.getStore().load();
                                    }
                                    Ext.getCmp("MonitorTorqueMeanSquareRoot_Id").removeAll();
                                    Ext.getCmp("MonitorTorqueAveragePower_Id").removeAll();
                    				
                    			}else if(activeId=="MonitorTorqueMeanSquareRoot_Id"){
                    				var gridView=Ext.getCmp("MonitorTorqueMeanSquareRootGrid_Id");
                                    if (gridView == undefined) {
                                        var MonitorTorqueMeanSquareRoot = Ext.getCmp("MonitorTorqueMeanSquareRoot_Id");
                                        MonitorTorqueMeanSquareRoot.removeAll();
                                        var MonitorTorqueMeanSquareRootView = Ext.create('AP.view.balanceMonitor.MonitorTorqueMeanSquareRootView');
                                        MonitorTorqueMeanSquareRoot.add(MonitorTorqueMeanSquareRootView);
                                    }else{
                                    	var store=gridView.getStore().load();
                                    }
                                    Ext.getCmp("MonitorTorqueMaxValue_Id").removeAll();
                                    Ext.getCmp("MonitorTorqueAveragePower_Id").removeAll();
                    			}else if(activeId=="MonitorTorqueAveragePower_Id"){
                    				var gridView=Ext.getCmp("MonitorTorqueAveragePowerGrid_Id");
                                    if (gridView == undefined) {
                                        var MonitorTorqueAveragePower = Ext.getCmp("MonitorTorqueAveragePower_Id");
                                        MonitorTorqueAveragePower.removeAll();
                                        var MonitorTorqueAveragePowerView = Ext.create('AP.view.balanceMonitor.MonitorTorqueAveragePowerView');
                                        MonitorTorqueAveragePower.add(MonitorTorqueAveragePowerView);
                                    }else{
                                    	var store=gridView.getStore().load();
                                    }
                                    Ext.getCmp("MonitorTorqueMaxValue_Id").removeAll();
                                    Ext.getCmp("MonitorTorqueMeanSquareRoot_Id").removeAll();
                    			}
                    		}
                    	}
            }]
        });
        me.callParent(arguments);
    
    }

});

function exportMonitorBalanceStatusExcelData(jh,gkmc,gridId,type,fileName,title,url){
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
    var param = "&fields=" + fields +"&heads=" + URLencode(URLencode(heads)) + "&orgId=" + leftOrg_Id +"&type="+type + "&jh=" + URLencode(URLencode(jh))  + "&gkmc=" + URLencode(URLencode(gkmc)) + "&fileName="+URLencode(URLencode(fileName))+ "&title="+URLencode(URLencode(title));
    openExcelWindow(url + '?flag=true' + param);
}