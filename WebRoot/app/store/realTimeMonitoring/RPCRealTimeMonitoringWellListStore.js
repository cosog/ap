Ext.define('AP.store.realTimeMonitoring.RPCRealTimeMonitoringWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rpcRealTimeMonitoringWellListStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: true,
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
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RPCRealTimeMonitoringListGridPanel_Id",
                    border: false,
                    autoLoad: true,
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
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RPCRealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		var deviceName=record.data.wellName;
                    		var deviceId=record.data.id;
                    		var deviceType=0;
                    		var tabPanel = Ext.getCmp("RPCRealTimeMonitoringCurveAndTableTabPanel");
                    		var activeId = tabPanel.getActiveTab().id;
                    		if(activeId=="RPCRealTimeMonitoringCurveTabPanel_Id"){
                    			deviceRealtimeMonitoringCurve(0);
                    		}else if(activeId=="RPCRealTimeMonitoringTableTabPanel_Id"){
                        		CreateRPCDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType);
                    		}else{
        						Ext.create('AP.store.realTimeMonitoring.SingleFESDiagramDetailsChartsStore');
        					}
                    		Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringControlAndInfoStore');
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
            	gridPanel.getSelectionModel().select(0, true);
            }else{
            	if(rpcDeviceRealTimeMonitoringDataHandsontableHelper!=null){
					if(rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
						rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot.destroy();
					}
					rpcDeviceRealTimeMonitoringDataHandsontableHelper=null;
				}
            	Ext.getCmp("RPCRealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
            	
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