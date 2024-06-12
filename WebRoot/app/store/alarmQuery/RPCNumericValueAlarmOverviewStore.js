Ext.define('AP.store.alarmQuery.RPCNumericValueAlarmOverviewStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rpcNumericValueAlarmOverviewStore',
    fields: ['id','deviceType','deviceTypeName','wellName','alarmTime','user_id','loginIp','action','actionName','remark'],
    autoLoad: true,
    pageSize: 100,
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
            Ext.getCmp("RPCNumericValueAlarmOverviewColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("RPCNumericValueAlarmOverviewGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg:''
//                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCNumericValueAlarmOverviewGridPanel_Id",
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
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		var deviceId=record.data.id;
                    		Ext.getCmp("selectedRPCDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RPCNumericValueAlarmOverviewSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('RPCNumericValueAlarmDeviceListComb_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedRPCDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		Ext.getCmp('RPCNumericValueAlarmQueryStartDate_Id').setValue('');
                        	Ext.getCmp('RPCNumericValueAlarmQueryStartTime_Hour_Id').setValue('');
                        	Ext.getCmp('RPCNumericValueAlarmQueryStartTime_Minute_Id').setValue('');
                        	Ext.getCmp('RPCNumericValueAlarmQueryStartTime_Second_Id').setValue('');
                        	Ext.getCmp('RPCNumericValueAlarmQueryEndDate_Id').setValue('');
                        	Ext.getCmp('RPCNumericValueAlarmQueryEndTime_Hour_Id').setValue('');
                        	Ext.getCmp('RPCNumericValueAlarmQueryEndTime_Minute_Id').setValue('');
                        	Ext.getCmp('RPCNumericValueAlarmQueryEndTime_Second_Id').setValue('');
                    		var gridPanel = Ext.getCmp("RPCNumericValueAlarmGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().loadPage(1);
            				}else{
            					Ext.create('AP.store.alarmQuery.RPCNumericValueAlarmStore');
            				}
                    	}
                    }
                });
                var panel = Ext.getCmp("RPCNumericValueAlarmOverviewPanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	var selectRow=0;
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedRPCDeviceId_global").getValue());
    			if(selectedDeviceId>0){
    				for(var i=0;i<store.data.items.length;i++){
            			if(selectedDeviceId==store.data.items[i].data.id){
            				selectRow=i;
            				break;
            			}
            		}
    			}
    			if(gridPanel.getSelectionModel().getSelection().length>0){
            		gridPanel.getSelectionModel().deselectAll(true);
            	}
            	gridPanel.getSelectionModel().select(selectRow, true);
            }else{
            	var gridPanel = Ext.getCmp("RPCNumericValueAlarmGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().loadPage(1);
                }else{
                	Ext.create('AP.store.alarmQuery.RPCNumericValueAlarmStore');
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=0;
        	var deviceName=Ext.getCmp('RPCNumericValueAlarmDeviceListComb_Id').getValue();
        	var alarmLevel=Ext.getCmp('RPCNumericValueAlarmLevelComb_Id').getValue();
        	var isSendMessage=Ext.getCmp('RPCNumericValueAlarmIsSendMessageComb_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceName:deviceName,
                    alarmLevel:alarmLevel,
                    isSendMessage:isSendMessage,
                    alarmType:2
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});