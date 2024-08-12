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
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + cosog.string.nodata + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	select: function(grid, record, index, eOpts) {
                    		Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(index);
                    		
//                    		Ext.getCmp("RealTimeMonitoringInfoPanel_Id").el.mask(cosog.string.loading).show();
                    		
                    		var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
                    		var deviceName=record.data.deviceName;
                    		var deviceId=record.data.id;
                    		var calculateType=record.data.calculateType;
                    		
                    		var deviceInfo=getDeviceAddInfoAndControlInfo(deviceId,deviceType);
                    		
                    		
                    		var combDeviceName=Ext.getCmp('RealTimeMonitoringDeviceListComb_Id').getValue();
                    		if(combDeviceName!=''){
                        		Ext.getCmp("selectedDeviceId_global").setValue(deviceId);
                    		}
                    		
                    		var tabPanel = Ext.getCmp("RealTimeMonitoringCurveAndTableTabPanel");
                    		var activeId = tabPanel.getActiveTab().id;
                    		
                    		if(activeId=="RealTimeMonitoringCurveTabPanel_Id"){
                    			deviceRealtimeMonitoringCurve(deviceType);
                    			
                    			var RealTimeMonitoringFSDiagramAnalysisTabPanel = tabPanel.getComponent("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
                    			var RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel = tabPanel.getComponent("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
                    			if(calculateType==1){
                    				if(RealTimeMonitoringFSDiagramAnalysisTabPanel==undefined){
                    					tabPanel.insert(0,realtimeCurveAndTableTabPanelItems[0]);
                    				}
                    				if(RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel==undefined){
                    					tabPanel.insert(1,realtimeCurveAndTableTabPanelItems[1]);
                    				}
                    			}else{
                    				if(RealTimeMonitoringFSDiagramAnalysisTabPanel!=undefined){
                    					tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
                    				}
                    				if(RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel!=undefined){
                    					tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
                    				}
                    			}
                    		}else if(activeId=="RealTimeMonitoringTableTabPanel_Id"){
                        		CreateDeviceRealTimeMonitoringDataTable(deviceId,deviceName,deviceType,calculateType);
                        		
                        		var RealTimeMonitoringFSDiagramAnalysisTabPanel = tabPanel.getComponent("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
                    			var RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel = tabPanel.getComponent("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
                    			if(calculateType==1){
                    				if(RealTimeMonitoringFSDiagramAnalysisTabPanel==undefined){
                    					tabPanel.insert(0,realtimeCurveAndTableTabPanelItems[0]);
                    				}
                    				if(RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel==undefined){
                    					tabPanel.insert(1,realtimeCurveAndTableTabPanelItems[1]);
                    				}
                    			}else{
                    				if(RealTimeMonitoringFSDiagramAnalysisTabPanel!=undefined){
                    					tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
                    				}
                    				if(RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel!=undefined){
                    					tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
                    				}
                    			}
                    		}else{
        						if(calculateType==1){
        							Ext.create('AP.store.realTimeMonitoring.SingleFESDiagramDetailsChartsStore');
        						}else{
        							tabPanel.setActiveTab("RealTimeMonitoringCurveTabPanel_Id");
        							
        							tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisTabPanel_Id");
        							tabPanel.remove("RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id");
        						}
        					}
                    		
                    		if(deviceInfo.videoNum==0 && deviceInfo.controlItemNum==0 && deviceInfo.addInfoNum==0 && deviceInfo.auxiliaryDeviceNum==0){
                    			cleanDeviceAddInfoAndControlInfo();
                    			Ext.getCmp("RealTimeMonitoringRightTabPanel").hide();
                    			Ext.getCmp("RealTimeMonitoringTabPanel").getEl().unmask();
                    			Ext.getCmp("RealTimeMonitoringInfoPanel_Id").getEl().unmask();
                    		}else{
                    			if(Ext.getCmp("RealTimeMonitoringRightTabPanel").isHidden() ){
                    				Ext.getCmp("RealTimeMonitoringRightTabPanel").show();
                    			}
                    			
                    			var rightTabPanel = Ext.getCmp("RealTimeMonitoringRightTabPanel");
                        		var rightTabPanelActiveTabId=rightTabPanel.getActiveTab().id;
                        		
                        		var RealTimeMonitoringRightControlAndVideoPanel = rightTabPanel.getComponent("RealTimeMonitoringRightControlAndVideoPanel");
                    			var RealTimeMonitoringRightDeviceInfoPanel = rightTabPanel.getComponent("RealTimeMonitoringRightDeviceInfoPanel");
                    			
                    			if( (deviceInfo.videoNum>0 || deviceInfo.controlItemNum>0) && RealTimeMonitoringRightControlAndVideoPanel==undefined){
                    				rightTabPanel.insert(0,RealTimeMonitoringRightTabPanelItems[0]);
                    			}
                    			
                    			if( (deviceInfo.addInfoNum>0 || deviceInfo.auxiliaryDeviceNum>0) && RealTimeMonitoringRightDeviceInfoPanel==undefined){
                    				rightTabPanel.insert(1,RealTimeMonitoringRightTabPanelItems[1]);
                    			}
                    			
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
            	Ext.getCmp("RealTimeMonitoringInfoDeviceListSelectRow_Id").setValue(-1);
            	var tabPanel = Ext.getCmp("RealTimeMonitoringCurveAndTableTabPanel");
            	var activeId = tabPanel.getActiveTab().id;
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
            var new_params = {
                    orgId: orgId,
                    deviceType:deviceType,
                    deviceTypeName:deviceTypeName,
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