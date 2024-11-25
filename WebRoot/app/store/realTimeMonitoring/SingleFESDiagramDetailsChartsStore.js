Ext.define('AP.store.realTimeMonitoring.SingleFESDiagramDetailsChartsStore', {
    extend: 'Ext.data.Store',
    id: "SingleFESDiagramDetailsChartsStore_Id",
    alias: 'widget.SingleFESDiagramDetailsChartsStore',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/querySingleFESDiagramDetailsChartsData',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            rootProperty: 'list',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
        	var get_rawData=store.proxy.reader.rawData;   // 获取store数据
        	var powerData=get_rawData.powerCurveData;
        	var currentData=get_rawData.currentCurveData;
        	
        	var tabPanel = Ext.getCmp("RealTimeMonitoringCurveAndTableTabPanel");
            var activeId = tabPanel.getActiveTab().id;
            Ext.getCmp(activeId).getEl().unmask();
            if(activeId=="RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"){
            	showFSDiagramFromPumpcard(get_rawData, "FSDiagramAnalysisSingleWellboreDetailsDiv1_id"); // 调用画泵功图的函数
            	showRodPress(get_rawData, "FSDiagramAnalysisSingleWellboreDetailsDiv2_id");    // 调用画杆柱应力的函数
            	showPumpCard(get_rawData, "FSDiagramAnalysisSingleWellboreDetailsDiv3_id"); // 调用画泵功图的函数
            	showPumpEfficiency(get_rawData, "FSDiagramAnalysisSingleWellboreDetailsDiv4_id");    // 调用画泵效组成的函数
            }else if(activeId=="RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"){
            	var deltaRadius=parseFloat(get_rawData.deltaRadius);
            	var expectedTorqueChartTitle=loginUserLanguageResource.torqueCurve;
            	if(Math.abs(deltaRadius)>0){
            		if(deltaRadius>0){
            			expectedTorqueChartTitle=loginUserLanguageResource.moveTowardOutside+' '+deltaRadius+"cm "+expectedTorqueChartTitle;
            		}else{
            			expectedTorqueChartTitle=loginUserLanguageResource.moveTowardInside+' '+Math.abs(deltaRadius)+"cm "+expectedTorqueChartTitle;
            		}
            	}else {
            		expectedTorqueChartTitle=loginUserLanguageResource.expectTorqueCurve;
            	}
            	
            	
            	showPSDiagram(get_rawData, "FSDiagramAnalysisSingleSurfaceDetailsDiv1_id");
            	showASDiagram(get_rawData, "FSDiagramAnalysisSingleSurfaceDetailsDiv3_id");
            	showBalanceAnalysisCurveChart(
            			get_rawData.crankAngle,
            			get_rawData.loadRorque,
            			get_rawData.crankTorque,
            			get_rawData.currentBalanceTorque,
            			get_rawData.currentNetTorque,
            			loginUserLanguageResource.currentTorqueCurve,
            			get_rawData.deviceName,
            			get_rawData.acqTime,
            			"FSDiagramAnalysisSingleSurfaceDetailsDiv2_id"
            		);
            	showBalanceAnalysisCurveChart(
            			get_rawData.crankAngle,
            			get_rawData.loadRorque,
            			get_rawData.crankTorque,
            			get_rawData.expectedBalanceTorque,
            			get_rawData.expectedNetTorque,
            			expectedTorqueChartTitle,
            			get_rawData.deviceName,
            			get_rawData.acqTime,
            			"FSDiagramAnalysisSingleSurfaceDetailsDiv4_id"
            		);
//            	showBalanceAnalysisMotionCurveChart(get_rawData.crankAngle,get_rawData.positionCurveData,get_rawData.polishrodV,get_rawData.polishrodA,
//            			"运动特性曲线",get_rawData.deviceName+' ['+get_rawData.acqTime+']',"FSDiagramAnalysisSingleSurfaceDetailsDiv5_id",2);
            }
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;// 获取图形数据id
        	var deviceName  = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
            var type=1;
            var tabPanel = Ext.getCmp("RealTimeMonitoringCurveAndTableTabPanel");
            var activeId = tabPanel.getActiveTab().id;
            if(activeId=="RealTimeMonitoringFSDiagramAnalysisTabPanel_Id"){//井筒分析
            	type=1;
            }else if(activeId=="RealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id"){//地面分析
            	type=2;
            }
            Ext.getCmp(activeId).el.mask(cosog.string.loading).show();
            var new_params = { // 将图形数据id作为参数传给后台
                id: id,
                deviceName:deviceName,
                type:type
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});