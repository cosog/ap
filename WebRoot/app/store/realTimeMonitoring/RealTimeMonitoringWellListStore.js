Ext.define('AP.store.realTimeMonitoring.RealTimeMonitoringWellListStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.realTimeMonitoringWellListStore',
    fields: ['id','commStatus','commStatusName','wellName'],
    autoLoad: false,
    pageSize: defaultPageSize,
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
            var column = createRealTimeMonitoringColumnObject(arrColumns);
            Ext.getCmp("RealTimeMonitoringColumnStr_Id").setValue(JSON.stringify(column));
            Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(get_rawData.AlarmShowStyle));
            var gridPanel = Ext.getCmp("RealTimeMonitoringListGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = column;
                var bbar = new Ext.PagingToolbar({
                	id:'RealTimeMonitoringListGridPagingToolbar',
                	store: store,
//                	displayMsg: '共 {2}条',
                	displayInfo: true
    	        });
                
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RealTimeMonitoringListGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		
                    		var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
                    		var deviceName=record.data.deviceName;
                    		var deviceId=record.data.id;
                    		
                    		
                    		var deviceInfo=getDeviceAddInfoAndControlInfo(deviceId,deviceType);
                    		
                    		
                    		var deviceTabInstanceInfo=getDeviceTabInstanceInfoByDeviceId(deviceId);
                    		var deviceTabInstanceConfig=deviceTabInstanceInfo.config;
                    		var calculateType=deviceTabInstanceInfo.calculateType==undefined?0:deviceTabInstanceInfo.calculateType;
                    		
                    		var showRealtimeWellboreAnalysis=false;
                    		var showRealtimeSurfaceAnalysis=false;
                    		var showRealtimeTrendCurve=false;
                    		var showRealtimeDynamicData=false;
                    		
                    		var showRealtimeDeviceControl=false;
                    		var showRealtimeDeviceInformation=false;
                    		
                    		if(deviceTabInstanceConfig!=undefined && deviceTabInstanceConfig.DeviceRealTimeMonitoring!=undefined){
                    			showRealtimeWellboreAnalysis=deviceTabInstanceConfig.DeviceRealTimeMonitoring.WellboreAnalysis!=undefined?deviceTabInstanceConfig.DeviceRealTimeMonitoring.WellboreAnalysis:false;
                    			showRealtimeSurfaceAnalysis=deviceTabInstanceConfig.DeviceRealTimeMonitoring.SurfaceAnalysis!=undefined?deviceTabInstanceConfig.DeviceRealTimeMonitoring.SurfaceAnalysis:false;
                    			showRealtimeTrendCurve=deviceTabInstanceConfig.DeviceRealTimeMonitoring.TrendCurve!=undefined?deviceTabInstanceConfig.DeviceRealTimeMonitoring.TrendCurve:false;
                    			showRealtimeDynamicData=deviceTabInstanceConfig.DeviceRealTimeMonitoring.DynamicData!=undefined?deviceTabInstanceConfig.DeviceRealTimeMonitoring.DynamicData:false;
                    			
                    			showRealtimeDeviceControl=deviceTabInstanceConfig.DeviceRealTimeMonitoring.DeviceControl!=undefined?deviceTabInstanceConfig.DeviceRealTimeMonitoring.DeviceControl:false;
                    			showRealtimeDeviceInformation=deviceTabInstanceConfig.DeviceRealTimeMonitoring.DeviceInformation!=undefined?deviceTabInstanceConfig.DeviceRealTimeMonitoring.DeviceInformation:false;
                    		}
                    		
                    		var combDeviceName=Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
                    		if(combDeviceName!=undefined || combDeviceName!=''){
                        		Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
                    		}
                    		
                    		var tabPanel = Ext.getCmp("RealTimeMonitoringCurveAndTableTabPanel");
                    		var activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
                    		
                    		var tabChange=false;
                    		//井筒分析标签处理
                    		if(showRealtimeWellboreAnalysis==false){
                    			tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"));
                    			if(activeId=="RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"){
                    				tabChange=true;
                    			}
                    		}else{
                    			var RealTimeMonitoringFSDiagramAnalysisTabPanel = tabPanel.getComponent("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
                				if(calculateType==1 && RealTimeMonitoringFSDiagramAnalysisTabPanel==undefined){
                					tabPanel.insert(0,realtimeCurveAndTableTabPanelItems[0]);
//                					tabPanel.setActiveTab(0);
//                					tabChange=true;
                				}else if(calculateType!=1 && RealTimeMonitoringFSDiagramAnalysisTabPanel!=undefined){
                					tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"));
                        			if(activeId=="RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"){
                        				tabChange=true;
                        			}
                				}
                    		}
                    		//地面分析标签处理
                    		if(showRealtimeSurfaceAnalysis==false){
                    			tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"));
                    			if(activeId=="RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"){
                    				tabChange=true;
                    			}
                    		}else{
                    			var RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel = tabPanel.getComponent("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
                				if(calculateType==1 && RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel==undefined){
                					tabPanel.insert(1,realtimeCurveAndTableTabPanelItems[1]);
                				}else if(calculateType!=1 && RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel!=undefined){
                					tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"));
                					if(activeId=="RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"){
                        				tabChange=true;
                        			}
                				}
                    		}
                    		
                    		//趋势曲线标签处理
                    		if(showRealtimeTrendCurve==false){
                    			tabPanel.remove(Ext.getCmp("RealTimeMonitoringCurveTabPanel_Id"));
                    			if(activeId=="RealTimeMonitoringCurveTabPanel_Id"){
                    				tabChange=true;
                    			}
                    		}else{
                    			var RealTimeMonitoringCurveTabPanel = tabPanel.getComponent("RealTimeMonitoringCurveTabPanel_Id");
                    			if(RealTimeMonitoringCurveTabPanel==undefined){
                    				tabPanel.insert(2,realtimeCurveAndTableTabPanelItems[2]);
                    			}
                    		}
                    		//动态数据标签处理
                    		if(showRealtimeDynamicData==false){
                    			tabPanel.remove(Ext.getCmp("RealTimeMonitoringTableTabPanel_Id"));
                    			if(activeId=="RealTimeMonitoringTableTabPanel_Id"){
                    				tabChange=true;
                    			}
                    		}else{
                    			var RealTimeMonitoringTableTabPanel = tabPanel.getComponent("RealTimeMonitoringTableTabPanel_Id");
                    			if(RealTimeMonitoringTableTabPanel==undefined){
                    				tabPanel.insert(3,realtimeCurveAndTableTabPanelItems[3]);
                    			}
                    		}
                    		
                    		
                    		var showDeviceDataTab=true;
                    		if(showRealtimeWellboreAnalysis==false && showRealtimeSurfaceAnalysis==false && showRealtimeTrendCurve==false && showRealtimeDynamicData==false){
                    			showDeviceDataTab=false;
                    		}
                    		if(!showDeviceDataTab){
                    			tabPanel.hide();
                    		}else{
                    			if(tabPanel.isHidden() ){
                    				tabPanel.show();
                    			}
                    			if(!tabChange){
                    				if(tabPanel.getActiveTab()==undefined){
                    					if(tabPanel.items.length>0){
                    						tabPanel.setActiveTab(0);
                    					}
                    				}else{
                    					activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
                            			if(activeId=="RealTimeMonitoringCurveTabPanel_Id"){
                            				deviceRealtimeMonitoringCurve(deviceType);
                            			}else if(activeId=="RealTimeMonitoringTableTabPanel_Id"){
                            				CreateDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType,calculateType);
                            			}else{
                    						if(calculateType==1){
                    							Ext.create('AP.store.realTimeMonitoring.SingleFESDiagramDetailsChartsStore');
                    						}else{
                    							if(tabPanel.items.length>0){
                    								tabPanel.setActiveTab(0);
                    							}
                    							
                    							tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
                    							tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
                    						}
                    					}
                    				}
                        			
                        		}
                    		}
                    		
                    		
                    		
                    		var controlTabChange=false;
                    		var showControlAndInformationTabPanel=true;
                    		if(deviceInfo.videoNum==0){
                    			cleanDeviceAddInfoAndControlInfo();
                    		}
                    		
                    		if(deviceInfo.videoNum==0 && deviceInfo.controlItemNum==0){
                    			showRealtimeDeviceControl=false;
                    		}
                    		if(deviceInfo.addInfoNum==0 && deviceInfo.auxiliaryDeviceNum==0){
                    			showRealtimeDeviceInformation=false;
                    		}
                    		
                    		
                    		if(showRealtimeDeviceControl==false && showRealtimeDeviceInformation==false){
                    			showControlAndInformationTabPanel=false;
                    		}
                    		
                    		var rightTabPanel = Ext.getCmp("RealTimeMonitoringRightTabPanel");
                    		var rightTabPanelActiveTabId=rightTabPanel.getActiveTab()!=undefined?rightTabPanel.getActiveTab().id:'';
                    		//控制标签处理
                    		if(showRealtimeDeviceControl==false){
                    			rightTabPanel.remove(Ext.getCmp("RealTimeMonitoringRightControlAndVideoPanel"));
                    			if(rightTabPanelActiveTabId=="RealTimeMonitoringRightControlAndVideoPanel"){
                    				controlTabChange=true;
                    			}
                    		}else{
                    			var RealTimeMonitoringRightControlAndVideoPanel = rightTabPanel.getComponent("RealTimeMonitoringRightControlAndVideoPanel");
                    			if(RealTimeMonitoringRightControlAndVideoPanel==undefined){
                					rightTabPanel.insert(0,RealTimeMonitoringRightTabPanelItems[0]);
//                    				rightTabPanel.setActiveTab(0);
//                    				controlTabChange=true;
                				}
                    		}
                    		
                    		//设备信息标签处理
                    		if(showRealtimeDeviceInformation==false){
                    			rightTabPanel.remove(Ext.getCmp("RealTimeMonitoringRightDeviceInfoPanel"));
                    			if(rightTabPanelActiveTabId=="RealTimeMonitoringRightDeviceInfoPanel"){
                    				controlTabChange=true;
                    			}
                    		}else{
                    			var RealTimeMonitoringRightDeviceInfoPanel = rightTabPanel.getComponent("RealTimeMonitoringRightDeviceInfoPanel");
                    			if(RealTimeMonitoringRightDeviceInfoPanel==undefined){
                					rightTabPanel.insert(1,RealTimeMonitoringRightTabPanelItems[1]);
                    			}
                    		}
                    		
                    		
                    		if(!showControlAndInformationTabPanel){
                    			rightTabPanel.hide();
                    			Ext.getCmp("RealTimeMonitoringTabPanel").getEl().unmask();
                    			Ext.getCmp("RealTimeMonitoringInfoPanel_Id").getEl().unmask();
                    		}else{
                    			if(rightTabPanel.isHidden() ){
                    				rightTabPanel.show();
                    			}
                        		
                        		if(rightTabPanel.getActiveTab()==undefined){
                        			rightTabPanel.setActiveTab(0);
                        		}else{
                        			if(!controlTabChange){
                        				rightTabPanelActiveTabId=rightTabPanel.getActiveTab()!=undefined?rightTabPanel.getActiveTab().id:'';
                            			if(rightTabPanelActiveTabId=='RealTimeMonitoringRightControlAndVideoPanel' ){
                                			if(deviceInfo.videoNum==0 && deviceInfo.controlItemNum==0){
                                				cleanDeviceAddInfoAndControlInfo();
                                				rightTabPanel.setActiveTab("RealTimeMonitoringRightDeviceInfoPanel");
                                				rightTabPanel.remove("RealTimeMonitoringRightControlAndVideoPanel");
                                			}else{
                                				createVideo(deviceType,record.data);
                                    			var controlGridPanel=Ext.getCmp("RealTimeMonitoringControlDataGridPanel_Id");
                                    			if(isNotVal(controlGridPanel)){
                                    				controlGridPanel.getStore().load();
                                    			}else{
                                    				Ext.create('AP.store.realTimeMonitoring.RealTimeMonitoringDeviceControlStore');
                                    			}
                                    			if(deviceInfo.addInfoNum==0 && deviceInfo.auxiliaryDeviceNum==0){
                                    				rightTabPanel.remove("RealTimeMonitoringRightDeviceInfoPanel");
                                    			}
                                			}
                                			
                                		}else if(rightTabPanelActiveTabId=='RealTimeMonitoringRightDeviceInfoPanel'){
                                			if(deviceInfo.addInfoNum==0 && deviceInfo.auxiliaryDeviceNum==0){
                                				rightTabPanel.setActiveTab("RealTimeMonitoringRightControlAndVideoPanel");
                                				rightTabPanel.remove("RealTimeMonitoringRightDeviceInfoPanel");
                                			}else{
                                				Ext.create('AP.store.realTimeMonitoring.RealTimeMonitoringAddInfoStore');
                                				if(deviceInfo.videoNum==0 && deviceInfo.controlItemNum==0){
                                					cleanDeviceAddInfoAndControlInfo();
                                					rightTabPanel.remove("RealTimeMonitoringRightControlAndVideoPanel");
                                				}
                                			}
                                		}
                            		}
                        		}
                    		}
                    	},
                    	itemdblclick: function (view,record,item,index,e,eOpts) {
                    		gotoDeviceHistory(record.data.id,record.data.deviceName,record.data.deviceType);
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
            	Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
            	var tabPanel = Ext.getCmp("RealTimeMonitoringCurveAndTableTabPanel");
            	var activeId = tabPanel.getActiveTab()!=undefined?tabPanel.getActiveTab().id:'';
            	if(activeId=="RealTimeMonitoringCurveTabPanel_Id"){
            		tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"));
                	tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"));
                	$("#realTimeMonitoringCurveContainer").html('');
                	$("#RealTimeMonitoringInfoDataTableInfoContainer").html('');
            	}else if(activeId=="RealTimeMonitoringTableTabPanel_Id"){
            		tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"));
                	tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"));
                	if(deviceRealTimeMonitoringDataHandsontableHelper!=null){
            			if(deviceRealTimeMonitoringDataHandsontableHelper.hot!=undefined){
            				deviceRealTimeMonitoringDataHandsontableHelper.hot.destroy();
            			}
            			deviceRealTimeMonitoringDataHandsontableHelper=null;
            		}
            	}else{
            		tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"));
                	tabPanel.remove(Ext.getCmp("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"));
            	}
            	cleanDeviceAddInfoAndControlInfo();
            	
            	Ext.getCmp("RealTimeMonitoringTabPanel").getEl().unmask();
            	Ext.getCmp("RealTimeMonitoringInfoPanel_Id").getEl().unmask();
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
        	var deviceTypeName=getTabPanelActiveName("RealTimeMonitoringTabPanel");
        	var deviceName = Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
        	var FESdiagramResultStatValue=Ext.getCmp("RealTimeMonitoringStatSelectFESdiagramResult_Id").getValue();
        	var commStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectCommStatus_Id").getValue();
        	var runStatusStatValue=Ext.getCmp("RealTimeMonitoringStatSelectRunStatus_Id").getValue();
        	var deviceTypeStatValue=Ext.getCmp("RealTimeMonitoringStatSelectDeviceType_Id").getValue();
        	
        	var dictDeviceType=deviceType;
        	if(dictDeviceType.includes(",")){
        		dictDeviceType=getDeviceTypeFromTabId_first("RealTimeMonitoringTabPanel");
        	}
        	
        	
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceTypeName:deviceTypeName,
                    deviceName:deviceName,
                    dictDeviceType:dictDeviceType,
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