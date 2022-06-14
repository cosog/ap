Ext.define('AP.store.dataMaintaining.RPCCalculateMaintainingDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RPCCalculateMaintainingDataStore',
    remoteSort: true,
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/calculateManagerController/getCalculateResultData',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: defaultPageSize,
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
        	var get_rawData = store.proxy.reader.rawData;
        	var bbar=Ext.getCmp("RPCFESDiagramCalculateMaintainingBbar");
			if (isNotVal(bbar)) {
				if(bbar.getStore().isEmptyStore){
					bbar.setStore(store);
				}
			}
        	
        	CreateAndLoadRPCCalculateMaintainingTable(true,get_rawData,"RPCCalculateMaintainingDiv_id");
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName=Ext.getCmp('RPCCalculateMaintainingWellListComBox_Id').getValue();
            var startDate=Ext.getCmp('RPCCalculateMaintainingStartDate_Id').rawValue;
            var endDate=Ext.getCmp('RPCCalculateMaintainingEndDate_Id').rawValue;
            var calculateSign=Ext.getCmp('RPCCalculateMaintainingCalculateSignComBox_Id').getValue();
            var deviceType=0;
            var calculateType=1;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
            var new_params = {
            		orgId: orgId,
            		wellName: wellName,
                    startDate:startDate,
                    endDate:endDate,
                    calculateSign:calculateSign,
                    deviceType:deviceType,
                    calculateType:calculateType
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "ProductionOutTotalCount_Id");
        }
    }
});