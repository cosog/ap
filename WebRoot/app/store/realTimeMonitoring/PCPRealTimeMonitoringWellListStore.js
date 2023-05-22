Ext.define('AP.store.realTimeMonitoring.PCPRealTimeMonitoringWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pcpRealTimeMonitoringWellListStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: false,
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceRealTimeOverview',
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
            var column = createRealTimeMonitoringColumn(arrColumns);
            Ext.getCmp("PCPRealTimeMonitoringColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	id:'PCPRealTimeMonitoringListGridPagingToolbar',
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPRealTimeMonitoringListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
//                    stripeRows: true,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    	},
                    	rowclick: function( grid, record, element, index, e, eOpts) {
                    		var deviceId=record.data.id;
                    		Ext.getCmp("selectedPCPDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("PCPRealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		var deviceName=record.data.wellName;
                    		var deviceType=1;
                    		var deviceId=record.data.id;
                    		
                    		var combDeviceName=Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedPCPDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		var tabPanel = Ext.getCmp("PCPRealTimeMonitoringCurveAndTableTabPanel");
                    		var activeId = tabPanel.getActiveTab().id;
                    		if(activeId=="PCPRealTimeMonitoringCurveTabPanel_Id"){
                    			deviceRealtimeMonitoringCurve(1);
                    		}else if(activeId=="PCPRealTimeMonitoringTableTabPanel_Id"){
                        		CreatePCPDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType);
                    		}
                    		
                    		if( Ext.getCmp("PCPRealTimeMonitoringRightTabPanel").getActiveTab().id=='PCPRealTimeMonitoringRightControlAndVideoPanel'){
                    			createVideo(1,record.data);
                    			var controlGridPanel=Ext.getCmp("PCPRealTimeMonitoringControlDataGridPanel_Id");
                    			if(isNotVal(controlGridPanel)){
                    				controlGridPanel.getStore().load();
                    			}else{
                    				Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringDeviceControlStore');
                    			}
                    		}else{
                    			var deviceInfoGridPanel=Ext.getCmp("PCPRealTimeMonitoringDeviceInfoDataGridPanel_Id");
                    			if(isNotVal(deviceInfoGridPanel)){
                    				deviceInfoGridPanel.getStore().load();
                    			}else{
                    				Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringDeviceInfoStore');
                    			}
                    		}
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		gotoDeviceHistory(record.data.wellName,1);
                    	}
                    }
                });
                var PCPRealTimeMonitoringInfoDeviceListPanel = Ext.getCmp("PCPRealTimeMonitoringInfoDeviceListPanel_Id");
                PCPRealTimeMonitoringInfoDeviceListPanel.add(gridPanel);
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
    			gridPanel.getSelectionModel().deselectAll(true);
    			gridPanel.getSelectionModel().select(selectRow, true);
            }else{
            	if(pcpDeviceRealTimeMonitoringDataHandsontableHelper!=null){
					if(pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
						pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot.destroy();
					}
					pcpDeviceRealTimeMonitoringDataHandsontableHelper=null;
				}
            	Ext.getCmp("PCPRealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
            	
            	$("#pcpRealTimeMonitoringCurveContainer").html('');
            	$("#PCPRealTimeMonitoringInfoDataTableInfoContainer").html('');
            	
            	clearVideo(1);
            	Ext.getCmp("PCPRealTimeMonitoringRightControlPanel").removeAll();
            	Ext.getCmp("PCPRealTimeMonitoringRightDeviceInfoPanel").removeAll();
            	Ext.getCmp("PCPRealTimeMonitoringRightAuxiliaryDeviceInfoPanel").removeAll();
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName=Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').getValue();
        	var commStatusStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectCommStatus_Id").getValue();
        	var runStatusStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectRunStatus_Id").getValue();
        	var deviceTypeStatValue=Ext.getCmp("PCPRealTimeMonitoringStatSelectDeviceType_Id").getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:1,
                    deviceName:deviceName,
                    commStatusStatValue:commStatusStatValue,
                    runStatusStatValue:runStatusStatValue,
                    deviceTypeStatValue:deviceTypeStatValue
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});