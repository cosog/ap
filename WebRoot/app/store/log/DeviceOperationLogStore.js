Ext.define('AP.store.log.DeviceOperationLogStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.deviceOperationLogStore',
    fields: ['id','deviceType','deviceTypeName','wellName','createTime','user_id','loginIp','action','actionName','remark'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/logQueryController/getDeviceOperationLogData',
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
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createDeviceOperationLogColumn(arrColumns);
            Ext.getCmp("DeviceOperationLogColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("DeviceOperationLogGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "DeviceOperationLogGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		
                    	},
                    	select: function(grid, record, index, eOpts) {}
                    }
                });
                var panel = Ext.getCmp("DeviceOperationLogPanel_Id");
                panel.add(gridPanel);
            }
            
            var startDate=Ext.getCmp('DeviceOperationLogQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('DeviceOperationLogQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('DeviceOperationLogQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('DeviceOperationLogQueryStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('DeviceOperationLogQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('DeviceOperationLogQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('DeviceOperationLogQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('DeviceOperationLogQueryEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
            Ext.getCmp("DeviceOperationLogRootTabPanel").getEl().unmask();
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("DeviceOperationLogRootTabPanel");
        	var deviceName=Ext.getCmp('DeviceOperationLogDeviceListComb_Id').getValue();
        	var operationType=Ext.getCmp('DeviceOperationLogOperationTypeListComb_Id').getValue();
        	var startDate=Ext.getCmp('DeviceOperationLogQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('DeviceOperationLogQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('DeviceOperationLogQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=Ext.getCmp('DeviceOperationLogQueryStartTime_Second_Id').getValue();
            var endDate=Ext.getCmp('DeviceOperationLogQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('DeviceOperationLogQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('DeviceOperationLogQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=Ext.getCmp('DeviceOperationLogQueryEndTime_Second_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceName:deviceName,
                    operationType:operationType,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});