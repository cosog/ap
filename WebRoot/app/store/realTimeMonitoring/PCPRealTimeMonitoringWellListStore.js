Ext.define('AP.store.realTimeMonitoring.PCPRealTimeMonitoringWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.pcpRealTimeMonitoringWellListStore',
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
            Ext.getCmp("PCPRealTimeMonitoringColumnStr_Id").setValue(column);
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true,
                	displayMsg: '共 {2}条'
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "PCPRealTimeMonitoringListGridPanel_Id",
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
                    		Ext.getCmp("PCPRealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		var deviceName=record.data.wellName;
                    		var deviceId=record.data.id;
                    		var deviceType=1;
                    		var tabPanel = Ext.getCmp("PCPRealTimeMonitoringCurveAndTableTabPanel");
                    		var activeId = tabPanel.getActiveTab().id;
                    		if(activeId=="PCPRealTimeMonitoringCurveTabPanel_Id"){
                    			deviceRealtimeMonitoringCurve(1);
                    		}else if(activeId=="PCPRealTimeMonitoringTableTabPanel_Id"){
                        		CreatePCPDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType);
                    		}
                    		Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringControlAndInfoStore');
                    		
                    		if( Ext.getCmp("PCPRealTimeMonitoringRightTabPanel").getActiveTab().id=='PCPRealTimeMonitoringRightControlAndVideoPanel'){
                    			if(videoPlayr!=null){
                    				document.getElementById('PCPRealTimeMonitoringRightVideoDiv_Id').innerHTML="";
                    				videoPlayr=null;
                    			}
                    			var videoUrl  = record.data.videoUrl;
                        		if(videoUrl!=''){
                        			Ext.getCmp("PCPRealTimeMonitoringRightVideoPanel").show();
                        			videoUrl='ezopen://open.ys7.com/G39444019/1.live'
                        			var accessToken='ra.dq7uimnn8tizdx3a0d8o3uk55ui0vpsw-5mmz04uczq-1u97g1h-ebffwqyhm';
                                    videoPlayr = new EZUIKit.EZUIKitPlayer({
                                    	id: 'PCPRealTimeMonitoringRightVideoDiv_Id', // 视频容器ID
                                    	accessToken: accessToken,
                                        url: videoUrl,
                                        template: 'standard', // simple - 极简版;standard-标准版;security - 安防版(预览回放);voice-语音版; theme-可配置主题；
//                                      plugin: ['talk'],                       // 加载插件，talk-对讲
//                                      width: 220,
//                                      height: 220,
                                    });
//                                    var hlsDemo = new EZUIKit.HLS("PCPRealTimeMonitoringRightVideoDiv_Id","https://open.ys7.com/v3/openlive/G39444019_1_2.m3u8?expire=1687487466&id=461840962679742464&t=c076d5a5ad56090e919a6a2500b96169c20a77874cce866a87a7d65e493e6f2f&ev=100");	
                        		}else{
                        			Ext.getCmp("PCPRealTimeMonitoringRightVideoPanel").hide();
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
            	gridPanel.getSelectionModel().select(0, true);
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