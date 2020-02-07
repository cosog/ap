/*******************************************************************************
 * 周期性系统评价视图
 *
 * @author zhao
 *
 */
Ext.define("AP.view.balanceTotal.BalanceTotalAnalysisView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.balanceTotalAnalysisView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var TotalTorqueMaxValueView = Ext.create('AP.view.balanceTotal.TotalTorqueMaxValueView');
        Ext.apply(me, {
            items: [{
                id: 'BalanceTotalTab_Id',
                xtype: 'tabpanel',
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                        	title: '最大值法',
                        	layout: "fit",
                        	id: 'TotalTorqueMaxValue_Id',
                        	border: false,
                        	items: [TotalTorqueMaxValueView]
                		},{
                            title: '均方根法',
                            layout: "fit",
                            id: 'TotalTorqueMeanSquareRoot_Id',
                            border: false
                    	},{
                            title: '功率法',
                            layout: "fit",
                            id: 'TotalTorqueAveragePower_Id',
                            border: false
                 }],
                 listeners: {
             		tabchange: function (tabPanel, newCard, oldCard,obj) {
             			var tabPanel = Ext.getCmp("BalanceTotalTab_Id");
             			var activeId = tabPanel.getActiveTab().id;
             			if(activeId=="TotalTorqueMaxValue_Id"){
             				var gridView=Ext.getCmp("TorqueMaxValueTotalAnalysisiDataGrid_Id");
                             if (gridView == undefined) {
                                 var TotalTorqueMaxValue = Ext.getCmp("TotalTorqueMaxValue_Id");
                                 TotalTorqueMaxValue.removeAll();
                                 var TotalTorqueMaxValueView = Ext.create('AP.view.balanceTotal.TotalTorqueMaxValueView');
                                 TotalTorqueMaxValue.add(TotalTorqueMaxValueView);
                             }else{
                             	var store=gridView.getStore().load();
                             }
                             Ext.getCmp("TotalTorqueMeanSquareRoot_Id").removeAll();
                             Ext.getCmp("TotalTorqueAveragePower_Id").removeAll();
             			}else if(activeId=="TotalTorqueMeanSquareRoot_Id"){
             				var gridView=Ext.getCmp("TorqueMeanSquareRootTotalAnalysisiDataGrid_Id");
                             if (gridView == undefined) {
                                 var TotalTorqueMeanSquareRoot = Ext.getCmp("TotalTorqueMeanSquareRoot_Id");
                                 TotalTorqueMeanSquareRoot.removeAll();
                                 var TotalTorqueMeanSquareRootView = Ext.create('AP.view.balanceTotal.TotalTorqueMeanSquareRootView');
                                 TotalTorqueMeanSquareRoot.add(TotalTorqueMeanSquareRootView);
                             }else{
                             	var store=gridView.getStore().load();
                             }
                             Ext.getCmp("TotalTorqueMaxValue_Id").removeAll();
                             Ext.getCmp("TotalTorqueAveragePower_Id").removeAll();
             			}else if(activeId=="TotalTorqueAveragePower_Id"){
             				var gridView=Ext.getCmp("TorqueAveragePowerTotalAnalysisiDataGrid_Id");
                             if (gridView == undefined) {
                                 var TotalTorqueAveragePower = Ext.getCmp("TotalTorqueAveragePower_Id");
                                 TotalTorqueAveragePower.removeAll();
                                 var TotalTorqueAveragePowerView = Ext.create('AP.view.balanceTotal.TotalTorqueAveragePowerView');
                                 TotalTorqueAveragePower.add(TotalTorqueAveragePowerView);
                             }else{
                             	var store=gridView.getStore().load();
                             }
                             Ext.getCmp("TotalTorqueMaxValue_Id").removeAll();
                             Ext.getCmp("TotalTorqueMeanSquareRoot_Id").removeAll();
             			}
             		}
             	}
            }]
        });
        me.callParent(arguments);
    
    }

});

function exportTotalBalanceStatusExcelData(jh,gkmc,gridId,type,fileName,title,url){
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
    var param = "&fields=" + fields +"&heads=" + URLencode(URLencode(heads)) + "&orgId=" + leftOrg_Id+ "&type="+type+ "&jh=" + URLencode(URLencode(jh))  + "&gkmc=" + URLencode(URLencode(gkmc)) + "&fileName="+URLencode(URLencode(fileName))+ "&title="+URLencode(URLencode(title));
    openExcelWindow(url+'?flag=true' + param);
}