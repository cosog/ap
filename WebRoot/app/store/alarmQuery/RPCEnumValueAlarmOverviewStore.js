Ext.define('AP.store.alarmQuery.RPCEnumValueAlarmOverviewStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rpcEnumValueAlarmOverviewStore',
    fields: ['id','deviceType','deviceTypeName','wellName','alarmTime','user_id','loginIp','action','actionName','remark'],
    autoLoad: true,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/alarmQueryController/getAlarmOverviewData',
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
            var column = createAlarmOverviewQueryColumn(arrColumns);
            Ext.getCmp("RPCEnumValueAlarmOverviewColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("RPCEnumValueAlarmOverviewGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg:''
//                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCEnumValueAlarmOverviewGridPanel_Id",
                    border: false,
//                    autoLoad: true,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RPCEnumValueAlarmOverviewSelectRow_Id").setValue(index);
                    		Ext.getCmp('RPCEnumValueAlarmQueryStartDate_Id').setValue('');
                        	Ext.getCmp('RPCEnumValueAlarmQueryStartTime_Hour_Id').setValue('');
                        	Ext.getCmp('RPCEnumValueAlarmQueryStartTime_Minute_Id').setValue('');
                        	Ext.getCmp('RPCEnumValueAlarmQueryStartTime_Second_Id').setValue('');
                        	Ext.getCmp('RPCEnumValueAlarmQueryEndDate_Id').setValue('');
                        	Ext.getCmp('RPCEnumValueAlarmQueryEndTime_Hour_Id').setValue('');
                        	Ext.getCmp('RPCEnumValueAlarmQueryEndTime_Minute_Id').setValue('');
                        	Ext.getCmp('RPCEnumValueAlarmQueryEndTime_Second_Id').setValue('');
                    		var gridPanel = Ext.getCmp("RPCEnumValueAlarmGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().loadPage(1);
            				}else{
            					Ext.create('AP.store.alarmQuery.RPCEnumValueAlarmStore');
            				}
                    	}
                    }
                });
                var panel = Ext.getCmp("RPCEnumValueAlarmOverviewPanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	if(gridPanel.getSelectionModel().getSelection().length>0){
            		gridPanel.getSelectionModel().deselectAll(true);
            	}
            	var index=Ext.getCmp("RPCEnumValueAlarmOverviewSelectRow_Id").getValue();
            	gridPanel.getSelectionModel().select(parseInt(index), true);
            }else{
            	var gridPanel = Ext.getCmp("RPCEnumValueAlarmGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().loadPage(1);
                }else{
                	Ext.create('AP.store.alarmQuery.RPCEnumValueAlarmStore');
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=0;
        	var deviceName=Ext.getCmp('RPCEnumValueAlarmDeviceListComb_Id').getValue();
        	var alarmLevel=Ext.getCmp('RPCEnumValueAlarmLevelComb_Id').getValue();
        	var isSendMessage=Ext.getCmp('RPCEnumValueAlarmIsSendMessageComb_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceName:deviceName,
                    alarmLevel:alarmLevel,
                    isSendMessage:isSendMessage,
                    alarmType:1
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});