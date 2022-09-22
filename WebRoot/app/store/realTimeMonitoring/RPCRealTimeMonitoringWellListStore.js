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
                    		
                    		if( Ext.getCmp("RPCRealTimeMonitoringRightTabPanel").getActiveTab().id=='RPCRealTimeMonitoringRightControlAndVideoPanel'){
                    			if(videoPlayr!=null){
                    				document.getElementById('RPCRealTimeMonitoringRightVideoDiv_Id').innerHTML="";
                    				videoPlayr=null;
                    			}
                    			var videoUrl  = record.data.videoUrl;
                        		if(videoUrl!=''){
                        			Ext.getCmp("RPCRealTimeMonitoringRightVideoPanel").show();
                        			videoUrl='ezopen://open.ys7.com/G39444019/1.live'
                        			var accessToken='ra.dq7uimnn8tizdx3a0d8o3uk55ui0vpsw-5mmz04uczq-1u97g1h-ebffwqyhm';
                                    videoPlayr = new EZUIKit.EZUIKitPlayer({
                                    	id: 'RPCRealTimeMonitoringRightVideoDiv_Id', // 视频容器ID
                                    	accessToken: accessToken,
                                        url: videoUrl,
                                        template: 'standard', // simple - 极简版;standard-标准版;security - 安防版(预览回放);voice-语音版; theme-可配置主题；
//                                      plugin: ['talk'],                       // 加载插件，talk-对讲
//                                      width: 220,
//                                      height: 220,
                                    });
//                                    var hlsDemo = new EZUIKit.HLS("RPCRealTimeMonitoringRightVideoDiv_Id","https://open.ys7.com/v3/openlive/G39444019_1_2.m3u8?expire=1687487466&id=461840962679742464&t=c076d5a5ad56090e919a6a2500b96169c20a77874cce866a87a7d65e493e6f2f&ev=100");	
                        		}else{
                        			Ext.getCmp("RPCRealTimeMonitoringRightVideoPanel").hide();
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