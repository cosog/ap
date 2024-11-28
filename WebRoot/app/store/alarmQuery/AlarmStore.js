Ext.define('AP.store.alarmQuery.AlarmStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.alarmStore',
    fields: ['id','deviceType','deviceTypeName','wellName','createTime','user_id','loginIp','action','actionName','remark'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/alarmQueryController/getAlarmData',
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
            var column = createAlarmQueryColumn(arrColumns);
            Ext.getCmp("AlarmDetailsColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("AlarmGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
//                	displayMsg: '当前 {0}~{1}条  共 {2} 条',
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "AlarmGridPanel_Id",
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
                var panel = Ext.getCmp(getAlarmDetailsDataPanIdFromTabActive());
                panel.add(gridPanel);
            }
            
            var startDate=Ext.getCmp('AlarmQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('AlarmQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('AlarmQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('AlarmQueryStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('AlarmQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('AlarmQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('AlarmQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('AlarmQueryEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
        	var alarmType=getAlarmTypeFromTabActive();
        	var deviceName='';
        	var deviceId=0;
        	if(Ext.getCmp("AlarmOverviewGridPanel_Id").getSelectionModel().getSelection().length>0){
        		deviceName=Ext.getCmp("AlarmOverviewGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
            	deviceId=  Ext.getCmp("AlarmOverviewGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	}
        	
        	var alarmLevel=Ext.getCmp('AlarmLevelComb_Id').getValue();
        	var isSendMessage=Ext.getCmp('AlarmIsSendMessageComb_Id').getValue();
        	var startDate=Ext.getCmp('AlarmQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('AlarmQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('AlarmQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=Ext.getCmp('AlarmQueryStartTime_Second_Id').getValue();
            var endDate=Ext.getCmp('AlarmQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('AlarmQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('AlarmQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=Ext.getCmp('AlarmQueryEndTime_Second_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceId:deviceId,
                    deviceName:deviceName,
                    alarmLevel:alarmLevel,
                    isSendMessage:isSendMessage,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                    alarmType:alarmType
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});