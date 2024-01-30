Ext.define('AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.realTimeMonitoringWellListStore',
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
            Ext.getCmp("RealTimeMonitoringColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("RealTimeMonitoringListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	id:'RealTimeMonitoringListGridPagingToolbar',
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RealTimeMonitoringListGridPanel_Id",
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
                    		Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
                    	},
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
                    		var deviceName=record.data.wellName;
                    		var deviceId=record.data.id;
                    		var combDeviceName=Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedDeviceId_global").setValue(record.data.id);
                    		}
                    		
                    		var tabPanel = Ext.getCmp("RealTimeMonitoringCurveAndTableTabPanel");
                    		var activeId = tabPanel.getActiveTab().id;
                    		if(activeId=="RealTimeMonitoringCurveTabPanel_Id"){
                    			deviceRealtimeMonitoringCurve(deviceType);
                    		}else if(activeId=="RealTimeMonitoringTableTabPanel_Id"){
                        		CreateDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType);
                    		}else{
        						Ext.create('AP.store.realTimeMonitoring.SingleFESDiagramDetailsChartsStore');
        					}
                    		if( Ext.getCmp("RealTimeMonitoringRightTabPanel").getActiveTab().id=='RealTimeMonitoringRightControlAndVideoPanel'){
                    			createVideo(deviceType,record.data);
                    			var controlGridPanel=Ext.getCmp("RealTimeMonitoringControlDataGridPanel_Id");
                    			if(isNotVal(controlGridPanel)){
                    				controlGridPanel.getStore().load();
                    			}else{
                    				Ext.create('AP.store.realTimeMonitoring.RealTimeMonitoringDeviceControlStore');
                    			}
                    		}else{
                    			var deviceInfoGridPanel=Ext.getCmp("RealTimeMonitoringDeviceInfoDataGridPanel_Id");
                    			if(isNotVal(deviceInfoGridPanel)){
                    				deviceInfoGridPanel.getStore().load();
                    			}else{
                    				Ext.create('AP.store.realTimeMonitoring.RealTimeMonitoringDeviceInfoStore');
                    			}
                    		}
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		gotoDeviceHistory(record.data.wellName,0);
                    	}
                    }
                });
                var RealTimeMonitoringInfoDeviceListPanel = Ext.getCmp("RealTimeMonitoringInfoDeviceListPanel_Id");
                RealTimeMonitoringInfoDeviceListPanel.add(gridPanel);
            }
            if(get_rawData.totalCount>0){
            	var selectRow=0;
            	var selectedDeviceId=parseInt(Ext.getCmp("selectedDeviceId_global").getValue());
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
            	if(deviceRealTimeMonitoringDataHandsontableHelper!=null){
					if(deviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
						deviceRealTimeMonitoringDataHandsontableHelper.hot.destroy();
					}
					deviceRealTimeMonitoringDataHandsontableHelper=null;
				}
            	Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
//            	Ext.getCmp("selectedDeviceId_global").setValue(0);
            	
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv1_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv2_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv3_id").html('');
            	$("#FSDiagramAnalysisSingleWellboreDetailsDiv4_id").html('');
            	
            	$("#FSDiagramAnalysisSingleSurfaceDetailsDiv1_id").html('');
            	$("#FSDiagramAnalysisSingleSurfaceDetailsDiv2_id").html('');
            	$("#FSDiagramAnalysisSingleSurfaceDetailsDiv3_id").html('');
            	$("#FSDiagramAnalysisSingleSurfaceDetailsDiv4_id").html('');
            	
            	$("#realTimeMonitoringCurveContainer").html('');
            	$("#RealTimeMonitoringInfoDataTableInfoContainer").html('');
            	
            	clearVideo(0);
            	Ext.getCmp("RealTimeMonitoringRightControlPanel").removeAll();
            	Ext.getCmp("RealTimeMonitoringRightDeviceInfoPanel").removeAll();
            	Ext.getCmp("RealTimeMonitoringRightAuxiliaryDeviceInfoPanel").removeAll();
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
        	var deviceName=getTabPanelActiveName("RealTimeMonitoringTabPanel");
        	var FESdiagramResultStatValue=Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").getValue();
        	var commStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").getValue();
        	var runStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").getValue();
        	var deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
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