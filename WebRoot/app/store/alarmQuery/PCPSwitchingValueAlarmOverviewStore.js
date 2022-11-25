Ext.define('AP.store.alarmQuery.PCPSwitchingValueAlarmOverviewStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPSwitchingValueAlarmOverviewStore',
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
            Ext.getCmp("PCPSwitchingValueAlarmOverviewColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("PCPSwitchingValueAlarmOverviewGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg:''
//                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPSwitchingValueAlarmOverviewGridPanel_Id",
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
                    		Ext.getCmp("PCPSwitchingValueAlarmOverviewSelectRow_Id").setValue(index);
                    		Ext.getCmp('PCPSwitchingValueAlarmQueryStartDate_Id').setValue('');
                        	Ext.getCmp('PCPSwitchingValueAlarmQueryStartTime_Hour_Id').setValue('');
                        	Ext.getCmp('PCPSwitchingValueAlarmQueryStartTime_Minute_Id').setValue('');
                        	Ext.getCmp('PCPSwitchingValueAlarmQueryStartTime_Second_Id').setValue('');
                        	Ext.getCmp('PCPSwitchingValueAlarmQueryEndDate_Id').setValue('');
                        	Ext.getCmp('PCPSwitchingValueAlarmQueryEndTime_Hour_Id').setValue('');
                        	Ext.getCmp('PCPSwitchingValueAlarmQueryEndTime_Minute_Id').setValue('');
                        	Ext.getCmp('PCPSwitchingValueAlarmQueryEndTime_Second_Id').setValue('');
                    		var gridPanel = Ext.getCmp("PCPSwitchingValueAlarmGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().loadPage(1);
            				}else{
            					Ext.create('AP.store.alarmQuery.PCPSwitchingValueAlarmStore');
            				}
                    	}
                    }
                });
                var panel = Ext.getCmp("PCPSwitchingValueAlarmOverviewPanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	if(gridPanel.getSelectionModel().getSelection().length>0){
            		gridPanel.getSelectionModel().deselectAll(true);
            	}
            	var index=Ext.getCmp("PCPSwitchingValueAlarmOverviewSelectRow_Id").getValue();
            	gridPanel.getSelectionModel().select(parseInt(index), true);
            }else{
            	var gridPanel = Ext.getCmp("PCPSwitchingValueAlarmGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().loadPage(1);
                }else{
                	Ext.create('AP.store.alarmQuery.PCPSwitchingValueAlarmStore');
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=1;
        	var deviceName=Ext.getCmp('PCPSwitchingValueAlarmDeviceListComb_Id').getValue();
        	var alarmLevel=Ext.getCmp('PCPSwitchingValueAlarmLevelComb_Id').getValue();
        	var isSendMessage=Ext.getCmp('PCPSwitchingValueAlarmIsSendMessageComb_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceName:deviceName,
                    alarmLevel:alarmLevel,
                    isSendMessage:isSendMessage,
                    alarmType:0
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});