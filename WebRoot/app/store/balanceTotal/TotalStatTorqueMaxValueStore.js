Ext.define('AP.store.balanceTotal.TotalStatTorqueMaxValueStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.totalStatTorqueMaxValueStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/balanceTotalAnalysisController/getTotalBalaceStatistics',
        actionMethods: {
            read: 'POST'
        },
    reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, record, f, op, o) {
            //获得列表数
        	var tabPanel = Ext.getCmp("TotalStatTorqueMaxValueTabpanel_Id");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="TotalStatTorqueMaxValuePiePanel_Id"){
				initTotalBalanceStatPieOrColChat(store);
			}else{}
            
        },
        beforeload: function (store, options) {
        	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
        	var new_params = {
                    orgId: leftOrg_Id,
                    type:2
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});

function initTotalBalanceStatPieOrColChat(store) {
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
	
	var tabPanel = Ext.getCmp("TotalStatTorqueMaxValueTabpanel_Id");
	var activeId = tabPanel.getActiveTab().id;
	if(activeId=="TotalStatTorqueMaxValuePiePanel_Id"){
		ShowTotalBalanceStatPieChart("TotalBalanceStatTorqueMaxValuePieDiv_Id","全天评价平衡状态统计图", "井数占", pieData);
	}else{
		
	}
}