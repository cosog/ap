Ext.define('AP.store.alarmQuery.PCPNumericValueAlarmOverviewStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPNumericValueAlarmOverviewStore',
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
            Ext.getCmp("PCPNumericValueAlarmOverviewColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("PCPNumericValueAlarmOverviewGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg:''
//                	displayMsg: '当前 {0}~{1}条  共 {2} 条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPNumericValueAlarmOverviewGridPanel_Id",
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
                    		Ext.getCmp("PCPNumericValueAlarmOverviewSelectRow_Id").setValue(index);
                    		
                    		var combDeviceName=Ext.getCmp('PCPNumericValueAlarmDeviceListComb_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedPCPDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		Ext.getCmp('PCPNumericValueAlarmQueryStartDate_Id').setValue('');
                        	Ext.getCmp('PCPNumericValueAlarmQueryStartTime_Hour_Id').setValue('');
                        	Ext.getCmp('PCPNumericValueAlarmQueryStartTime_Minute_Id').setValue('');
                        	Ext.getCmp('PCPNumericValueAlarmQueryStartTime_Second_Id').setValue('');
                        	Ext.getCmp('PCPNumericValueAlarmQueryEndDate_Id').setValue('');
                        	Ext.getCmp('PCPNumericValueAlarmQueryEndTime_Hour_Id').setValue('');
                        	Ext.getCmp('PCPNumericValueAlarmQueryEndTime_Minute_Id').setValue('');
                        	Ext.getCmp('PCPNumericValueAlarmQueryEndTime_Second_Id').setValue('');
                    		var gridPanel = Ext.getCmp("PCPNumericValueAlarmGridPanel_Id");
            				if (isNotVal(gridPanel)) {
            					gridPanel.getStore().loadPage(1);
            				}else{
            					Ext.create('AP.store.alarmQuery.PCPNumericValueAlarmStore');
            				}
                    	}
                    }
                });
                var panel = Ext.getCmp("PCPNumericValueAlarmOverviewPanel_Id");
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
            	var gridPanel = Ext.getCmp("PCPNumericValueAlarmGridPanel_Id");
                if (isNotVal(gridPanel)) {
                	gridPanel.getStore().loadPage(1);
                }else{
                	Ext.create('AP.store.alarmQuery.PCPNumericValueAlarmStore');
                }
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=1;
        	var deviceName=Ext.getCmp('PCPNumericValueAlarmDeviceListComb_Id').getValue();
        	var alarmLevel=Ext.getCmp('PCPNumericValueAlarmLevelComb_Id').getValue();
        	var isSendMessage=Ext.getCmp('PCPNumericValueAlarmIsSendMessageComb_Id').getValue();
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