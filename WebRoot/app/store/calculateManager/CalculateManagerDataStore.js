Ext.define('AP.store.calculateManager.CalculateManagerDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.productionOutInfoStore',
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
            var tabPanelId = Ext.getCmp("CalculateManagerTabPanel").getActiveTab().id;
            if(tabPanelId=="PumpingUnitCalculateManagerPanel"){
            	wellType=200;
			}else if(tabPanelId=="ScrewPumpCalculateManagerPanel"){
				wellType=400;
			}
            var new_params = {
            		orgId: orgId,
            		wellName: wellName,
                    startDate:startDate,
                    endDate:endDate,
                    calculateSign:calculateSign,
                    wellType:wellType
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "ProductionOutTotalCount_Id");
        }
    }
});