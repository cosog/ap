Ext.define('AP.store.alarmQuery.RPCSwitchingValueAlarmStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RPCSwitchingValueAlarmStore',
    fields: ['id','deviceType','deviceTypeName','wellName','createTime','user_id','loginIp','action','actionName','remark'],
    autoLoad: true,
    pageSize: 50,
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
            Ext.getCmp("RPCSwitchingValueAlarmDetailsColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("RPCSwitchingValueAlarmGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCSwitchingValueAlarmGridPanel_Id",
                    border: false,
                    autoLoad: true,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		
                    	},
                    	select: function(grid, record, index, eOpts) {}
                    }
                });
                var panel = Ext.getCmp("RPCSwitchingValueAlarmDetailsPanel_Id");
                panel.add(gridPanel);
            }
            
            var startDate=Ext.getCmp('RPCSwitchingValueAlarmQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('RPCSwitchingValueAlarmQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('RPCSwitchingValueAlarmQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('RPCSwitchingValueAlarmQueryStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('RPCSwitchingValueAlarmQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('RPCSwitchingValueAlarmQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('RPCSwitchingValueAlarmQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
            	Ext.getCmp('RPCSwitchingValueAlarmQueryEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=0;
        	var deviceName='';
        	var deviceId=0;
        	if(Ext.getCmp("RPCSwitchingValueAlarmOverviewGridPanel_Id").getSelectionModel().getSelection().length>0){
        		deviceName=Ext.getCmp("RPCSwitchingValueAlarmOverviewGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
            	deviceId=  Ext.getCmp("RPCSwitchingValueAlarmOverviewGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	}
        	var alarmLevel=Ext.getCmp('RPCSwitchingValueAlarmLevelComb_Id').getValue();
        	var isSendMessage=Ext.getCmp('RPCSwitchingValueAlarmIsSendMessageComb_Id').getValue();
        	var startDate=Ext.getCmp('RPCSwitchingValueAlarmQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('RPCSwitchingValueAlarmQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('RPCSwitchingValueAlarmQueryStartTime_Minute_Id').getValue();
        	var startTime_Second=Ext.getCmp('RPCSwitchingValueAlarmQueryStartTime_Second_Id').getValue();
            var endDate=Ext.getCmp('RPCSwitchingValueAlarmQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('RPCSwitchingValueAlarmQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('RPCSwitchingValueAlarmQueryEndTime_Minute_Id').getValue();
        	var endTime_Second=Ext.getCmp('RPCSwitchingValueAlarmQueryEndTime_Second_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceId:deviceId,
                    deviceName:deviceName,
                    alarmLevel:alarmLevel,
                    isSendMessage:isSendMessage,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
                    alarmType:0
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});