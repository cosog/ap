Ext.define('AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rpcRealTimeMonitoringWellListStore',
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
            Ext.getCmp("RPCRealTimeMonitoringColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	id:'RPCRealTimeMonitoringListGridPagingToolbar',
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCRealTimeMonitoringListGridPanel_Id",
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
                    		Ext.getCmp("selectedRPCDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RPCRealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		var deviceType=0;
                    		var deviceName=record.data.wellName;
                    		
                    		var combDeviceName=Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedRPCDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		var tabPanel = Ext.getCmp("RPCRealTimeMonitoringCurveAndTableTabPanel");
                    		var activeId = tabPanel.getActiveTab().id;
                    		if(activeId=="RPCRealTimeMonitoringCurveTabPanel_Id"){
                    			deviceRealtimeMonitoringCurve(0);
                    		}else if(activeId=="RPCRealTimeMonitoringTableTabPanel_Id"){
                        		CreateRPCDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType);
                    		}else{
        						Ext.create('AP.store.realTimeMonitoring.SingleFESDiagramDetailsChartsStore');
        					}
                    		if( Ext.getCmp("RPCRealTimeMonitoringRightTabPanel").getActiveTab().id=='RPCRealTimeMonitoringRightControlAndVideoPanel'){
                    			createVideo(0,record.data);
                    			var controlGridPanel=Ext.getCmp("RPCRealTimeMonitoringControlDataGridPanel_Id");
                    			if(isNotVal(controlGridPanel)){
                    				controlGridPanel.getStore().load();
                    			}else{
                    				Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringDeviceControlStore');
                    			}
                    		}else{
                    			var deviceInfoGridPanel=Ext.getCmp("RPCRealTimeMonitoringDeviceInfoDataGridPanel_Id");
                    			if(isNotVal(deviceInfoGridPanel)){
                    				deviceInfoGridPanel.getStore().load();
                    			}else{
                    				Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringDeviceInfoStore');
                    			}
                    		}
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		gotoDeviceHistory(record.data.wellName,0);
                    	}
                    }
                });
                var RPCRealTimeMonitoringInfoDeviceListPanel = Ext.getCmp("RPCRealTimeMonitoringInfoDeviceListPanel_Id");
                RPCRealTimeMonitoringInfoDeviceListPanel.add(gridPanel);
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
    			gridPanel.getSelectionModel().deselectAll(true);
            	gridPanel.getSelectionModel().select(selectRow, true);
            }else{
            	if(rpcDeviceRealTimeMonitoringDataHandsontableHelper!=null){
					if(rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
						rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot.destroy();
					}
					rpcDeviceRealTimeMonitoringDataHandsontableHelper=null;
				}
            	Ext.getCmp("RPCRealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
//            	Ext.getCmp("selectedRPCDeviceId_global").setValue(0);
            	
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").html('');
            	
            	$("#FSDiagramAnalysisSingleSurfaceDetailsDiv1_id").html('');
            	$("#FSDiagramAnalysisSingleSurfaceDetailsDiv2_id").html('');
            	$("#FSDiagramAnalysisSingleSurfaceDetailsDiv3_id").html('');
            	$("#FSDiagramAnalysisSingleSurfaceDetailsDiv4_id").html('');
            	
            	$("#rpcRealTimeMonitoringCurveContainer").html('');
            	$("#RPCRealTimeMonitoringInfoDataTableInfoContainer").html('');
            	
            	clearVideo(0);
            	Ext.getCmp("RPCRealTimeMonitoringRightControlPanel").removeAll();
            	Ext.getCmp("RPCRealTimeMonitoringRightDeviceInfoPanel").removeAll();
            	Ext.getCmp("RPCRealTimeMonitoringRightAuxiliaryDeviceInfoPanel").removeAll();
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceName=Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').getValue();
        	var FESdiagramResultStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectFESdiagramResult_Id").getValue();
        	var commStatusStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").getValue();
        	var runStatusStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectRunStatus_Id").getValue();
        	var deviceTypeStatValue=Ext.getCmp("RPCRealTimeMonitoringStatSelectDeviceType_Id").getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:0,
                    deviceName:deviceName,
                    FESdiagramResultStatValue:FESdiagramResultStatValue,
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