Ext.define('AP.store.dataMaintaining.PCPCalculateMaintainingDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPCalculateMaintainingDataStore',
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
        	Ext.getCmp("PCPCalculateMaintainingPanel").getEl().unmask();
        	var get_rawData = store.proxy.reader.rawData;
        	var bbar=Ext.getCmp("PCPFESDiagramCalculateMaintainingBbar");
			if (isNotVal(bbar)) {
				if(bbar.getStore().isEmptyStore){
					bbar.setStore(store);
				}
			}
        	
        	CreateAndLoadPCPCalculateMaintainingTable(true,get_rawData,"PCPCalculateMaintainingDiv_id");
        	
        	var startDate=Ext.getCmp('PCPCalculateMaintainingStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
//            	Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('PCPCalculateMaintainingEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
//            	Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName='';
        	var deviceId=0;
        	var applicationScenarios=0;
        	var selectRow= Ext.getCmp("PCPCalculateMaintainingDeviceListSelectRow_Id").getValue();
        	if(Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection().length>0){
        		deviceName = Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
        		deviceId=Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		applicationScenarios=Ext.getCmp("PCPCalculateMaintainingWellListGridPanel_Id").getSelectionModel().getSelection()[0].data.applicationScenarios;
        	}
            var startDate=Ext.getCmp('PCPCalculateMaintainingStartDate_Id').rawValue;
            var startTime_Hour=Ext.getCmp('PCPCalculateMaintainingStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('PCPCalculateMaintainingStartTime_Minute_Id').getValue();
//        	var startTime_Second=Ext.getCmp('PCPCalculateMaintainingStartTime_Second_Id').getValue();
        	var startTime_Second=0;
            var endDate=Ext.getCmp('PCPCalculateMaintainingEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('PCPCalculateMaintainingEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('PCPCalculateMaintainingEndTime_Minute_Id').getValue();
//        	var endTime_Second=Ext.getCmp('PCPCalculateMaintainingEndTime_Second_Id').getValue();
        	var endTime_Second=0;
            var calculateSign=Ext.getCmp('PCPCalculateMaintainingCalculateSignComBox_Id').getValue();
            var deviceType=getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel");
            var dictDeviceType=deviceType;
        	if(dictDeviceType.includes(",")){
        		dictDeviceType=getDeviceTypeFromTabId_first("CalculateMaintainingRootTabPanel");
        	}
            var calculateType=2;//1-抽油机井诊断计产 2-螺杆泵井诊断计产 3-抽油机井汇总计算  4-螺杆泵井汇总计算 5-电参反演地面功图计算
            var new_params = {
            		orgId: orgId,
            		deviceName: deviceName,
            		deviceId:deviceId,
            		applicationScenarios:applicationScenarios,
            		startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                    calculateSign:calculateSign,
                    deviceType:deviceType,
                    dictDeviceType:dictDeviceType,
                    calculateType:calculateType
            };
            Ext.apply(store.proxy.extraParams, new_params);
            Ext.getCmp("PCPCalculateMaintainingPanel").el.mask(loginUserLanguageResource.loading).show();
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "ProductionOutTotalCount_Id");
        }
    }
});