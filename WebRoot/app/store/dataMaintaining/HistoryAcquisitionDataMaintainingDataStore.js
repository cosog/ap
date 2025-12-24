Ext.define('AP.store.dataMaintaining.HistoryAcquisitionDataMaintainingDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.HistoryAcquisitionDataMaintainingDataStore',
    remoteSort: true,
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/calculateManagerController/getHistoryAcquisitionData',
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
        	Ext.getCmp("HistoryAcquisitionDataMaintainingPanel").getEl().unmask();
        	var get_rawData = store.proxy.reader.rawData;
        	var bbar=Ext.getCmp("HistoryAcquisitionDataMaintainingBbar");
			if (isNotVal(bbar)) {
				if(bbar.getStore().isEmptyStore){
					bbar.setStore(store);
				}
			}
        	
        	CreateAndLoadHistoryAcquisitionDataMaintainingTable(true,get_rawData,"HistoryAcquisitionDataMaintainingDiv_id");
        	
        	var startDate=Ext.getCmp('AcquisitionDataMaintainingStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('AcquisitionDataMaintainingStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('AcquisitionDataMaintainingStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            }
            var endDate=Ext.getCmp('AcquisitionDataMaintainingEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('AcquisitionDataMaintainingEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('AcquisitionDataMaintainingEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	
        	var deviceName='';
        	var deviceId=0;
        	var applicationScenarios=0;
        	var selectRow= Ext.getCmp("DataMaintainingDeviceListSelectRow_Id").getValue();
        	if(Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
        		deviceName = Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
        		deviceId=Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		applicationScenarios=Ext.getCmp("DataMaintainingDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.applicationScenarios;
        	}
            
            var startDate=Ext.getCmp('AcquisitionDataMaintainingStartDate_Id').rawValue;
            var startTime_Hour=Ext.getCmp('AcquisitionDataMaintainingStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('AcquisitionDataMaintainingStartTime_Minute_Id').getValue();
        	var startTime_Second=0;
        	
            var endDate=Ext.getCmp('AcquisitionDataMaintainingEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('AcquisitionDataMaintainingEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('AcquisitionDataMaintainingEndTime_Minute_Id').getValue();
        	var endTime_Second=0;
            
            var deviceType=getDeviceTypeFromTabId("CalculateMaintainingRootTabPanel");
            
            var dictDeviceType=deviceType;
        	if(dictDeviceType.includes(",")){
        		dictDeviceType=getDeviceTypeFromTabId_first("CalculateMaintainingRootTabPanel");
        	}
            
            var timeType = 1;
            var calculateType=0;
            var new_params = {
            		orgId: orgId,
            		deviceName: deviceName,
            		deviceId:deviceId,
            		applicationScenarios:applicationScenarios,
            		startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                    deviceType:deviceType,
                    dictDeviceType:dictDeviceType,
                    timeType:timeType,
                    calculateType:calculateType
            };
            Ext.apply(store.proxy.extraParams, new_params);
            Ext.getCmp("HistoryAcquisitionDataMaintainingPanel").el.mask(loginUserLanguageResource.loading).show();
        },
        datachanged: function (v, o) {
//            onStoreSizeChange(v, o, "ProductionOutTotalCount_Id");
        }
    }
});