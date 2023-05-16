Ext.define('AP.store.alarmQuery.PCPEnumValueAlarmOverviewStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPEnumValueAlarmOverviewStore',
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
            Ext.getCmp("PCPEnumValueAlarmOverviewColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("PCPEnumValueAlarmOverviewGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
//                	displayMsg: '当前 {0}~{1}条  共 {2} 条',
                	displayMsg: '',
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPEnumValueAlarmOverviewGridPanel_Id",
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
                    		Ext.getCmp("selectedPCPDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("PCPEnumValueAlarmOverviewSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('PCPEnumValueAlarmDeviceListComb_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedPCPDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		Ext.getCmp('PCPEnumValueAlarmQueryStartDate_Id').setValue('');
                        	Ext.getCmp('PCPEnumValueAlarmQueryStartTime_Hour_Id').setValue('');
                        	Ext.getCmp('PCPEnumValueAlarmQueryStartTime_Minute_Id').setValue('');
                        	Ext.getCmp('PCPEnumValueAlarmQueryStartTime_Second_Id').setValue('');
                        	Ext.getCmp('PCPEnumValueAlarmQueryEndDate_Id').setValue('');
                        	Ext.getCmp('PCPEnumValueAlarmQueryEndTime_Hour_Id').setValue('');
                        	Ext.getCmp('PCPEnumValueAlarmQueryEndTime_Minute_Id').setValue('');
                        	Ext.getCmp('PCPEnumValueAlarmQueryEndTime_Second_Id').setValue('');
                    		var gridPanel = Ext.getCmp("PCPEnumValueAlarmGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().loadPage(1);
            				}else{
            					Ext.create('AP.store.alarmQuery.PCPEnumValueAlarmStore');
            				}
                    	}
                    }
                });
                var panel = Ext.getCmp("PCPEnumValueAlarmOverviewPanel_Id");
                panel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	var selectRow=0;
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedPCPDeviceId_global").getValue());
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
            	var gridPanel = Ext.getCmp("PCPEnumValueAlarmGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().loadPage(1);
                }else{
                	Ext.create('AP.store.alarmQuery.PCPEnumValueAlarmStore');
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=1;
        	var deviceName=Ext.getCmp('PCPEnumValueAlarmDeviceListComb_Id').getValue();
        	var alarmLevel=Ext.getCmp('PCPEnumValueAlarmLevelComb_Id').getValue();
        	var isSendMessage=Ext.getCmp('PCPEnumValueAlarmIsSendMessageComb_Id').getValue();
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