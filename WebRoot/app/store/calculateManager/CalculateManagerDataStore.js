Ext.define('AP.store.calculateManager.CalculateManagerDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.calculateManagerDataStore',
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
        	var divid='PumpingUnitCalculateManagerDiv_id';
            var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
            if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
            	divid='PumpingUnitCalculateManagerDiv_id';
			}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
				divid='ScrewPumpCalculateManagerDiv_id';
			}else if(tabPanelId=="ElectricInversionCalculateManagerPanel"){
				divid='ElectricInversionCalculateManagerDiv_id';
			}
        	var get_rawData = store.proxy.reader.rawData;
        	CreateAndLoadCalculateManagerTable(true,get_rawData,divid);
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
            var wellName=Ext.getCmp('CalculateManagerWellListComBox_Id').getValue();
            var startDate=Ext.getCmp('CalculateManagerStartDate_Id').rawValue;
            var endDate=Ext.getCmp('CalculateManagerEndDate_Id').rawValue;
            var calculateSign=Ext.getCmp('CalculateManagerCalculateSignComBox_Id').getValue();
            var wellType=200;
            var calculateType=1;//1-抽油机诊断计产 2-螺杆泵诊断计产 3-抽油机汇总计算  4-螺杆泵汇总计算 5-电参反演地面功图计算
            var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
            if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
            	wellType=200;
            	calculateType=1;
			}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
				wellType=400;
				calculateType=2;
			}else if(tabPanelId=="ElectricInversionCalculateManagerPanel"){
				calculateType=5;
			}
            var new_params = {
            		orgId: orgId,
            		wellName: wellName,
                    startDate:startDate,
                    endDate:endDate,
                    calculateSign:calculateSign,
                    wellType:wellType,
                    calculateType:calculateType
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "ProductionOutTotalCount_Id");
        }
    }
});