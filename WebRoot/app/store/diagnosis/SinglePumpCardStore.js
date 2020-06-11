Ext.define('AP.store.diagnosis.SinglePumpCardStore', {
    extend: 'Ext.data.Store',
    id: "SinglePumpCardStore_Id",
    alias: 'widget.SinglePumpCardStore',
    model: 'AP.model.graphical.X_Y_Model',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisAnalysisOnlyController/querySingleDetailsChartsData',
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
        	
        	var tabPanel = Ext.getCmp("FSDiagramAnalysisSingleDetailsCenterTabPanel_Id");
            var activeId = tabPanel.getActiveTab().id;
            if(activeId=="FSDiagramAnalysisSingleDetailsCenterPanel1_Id"){
            	showFSDiagramFromPumpcard(get_rawData, "FSDiagramAnalysisSingleWellboreDetailsDiv1_id"); // 调用画泵功图的函数
            	showRodPress(get_rawData, "FSDiagramAnalysisSingleWellboreDetailsDiv2_id");    // 调用画杆柱应力的函数
            	showPumpCard(get_rawData, "FSDiagramAnalysisSingleWellboreDetailsDiv3_id"); // 调用画泵功图的函数
            	showPumpEfficiency(get_rawData, "FSDiagramAnalysisSingleWellboreDetailsDiv4_id");    // 调用画泵效组成的函数
            	initWellboreSliceCharts(get_rawData,"FSDiagramAnalysisSingleWellboreDetailsDiv5_id");//剖面曲线
            	initSingleDetailsWellboreTrajectoryCharts(get_rawData,"FSDiagramAnalysisSingleWellboreDetailsDiv6_id");//井身轨迹
            }else if(activeId=="FSDiagramAnalysisSingleDetailsCenterPanel2_Id"){
            	showPSDiagram(get_rawData, "FSDiagramAnalysisSingleSurfaceDetailsDiv1_id");
            	showASDiagram(get_rawData, "FSDiagramAnalysisSingleSurfaceDetailsDiv3_id");
//            	showPowerByAngleContinuousDiagram(get_rawData.powerCurveData,get_rawData.crankAngle,"功率曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']','<span style="text-align:center;">曲柄转角(度)<br />',"功率(kW)",'#f70b0b',"FSDiagramAnalysisSingleSurfaceDetailsDiv3_id");
            	
            	if(get_rawData.IaCurveData==""){
            		showPowerByAngleContinuousDiagram(get_rawData.currentCurveData,get_rawData.crankAngle,"电流曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']','<span style="text-align:center;">曲柄转角(度)<br />',"电流(A)",'#e3cc19',"FSDiagramAnalysisSingleSurfaceDetailsDiv5_id");
//            		showPowerByAngleContinuousDiagram(get_rawData.currentCurveData,"电流曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']','<span style="text-align:center;">曲柄转角(度)<br />',"电流(A)",'#FF6633',"FSDiagramAnalysisSingleSurfaceDetailsDiv5_id");
            	}else{
            		var color=['#e3cc19',
						'#0000FF',
						'#FF6633'
					];
            		showThreeTermsContinuousDiagram(get_rawData.crankAngle,
            				get_rawData.IaCurveData,get_rawData.IbCurveData,get_rawData.IcCurveData,
            				"三相电流曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']',
            				'<span style="text-align:center;">曲柄转角(度)<br />',"电流(A)",color, "FSDiagramAnalysisSingleSurfaceDetailsDiv5_id");
            	}
            	showBalanceAnalysisCurveChart(get_rawData.crankAngle,get_rawData.loadRorque,get_rawData.crankTorque,get_rawData.currentBalanceTorque,get_rawData.currentNetTorque,
            			"目前扭矩曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']',"FSDiagramAnalysisSingleSurfaceDetailsDiv2_id");
            	showBalanceAnalysisCurveChart(get_rawData.crankAngle,get_rawData.loadRorque,get_rawData.crankTorque,get_rawData.expectedBalanceTorque,get_rawData.expectedNetTorque,
            			"预期扭矩曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']',"FSDiagramAnalysisSingleSurfaceDetailsDiv4_id");
            	showBalanceAnalysisMotionCurveChart(get_rawData.crankAngle,get_rawData.positionCurveData,get_rawData.polishrodV,get_rawData.polishrodA,
            			"运动特性曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']',"FSDiagramAnalysisSingleSurfaceDetailsDiv6_id",2);
            }
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.id;// 获取图形数据id
        	var wellName  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.wellName;
            var selectedWellName = Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
            var type=1;
            var tabPanel = Ext.getCmp("FSDiagramAnalysisSingleDetailsCenterTabPanel_Id");
            var activeId = tabPanel.getActiveTab().id;
            if(activeId=="FSDiagramAnalysisSingleDetailsCenterPanel1_Id"){//井筒分析
            	type=1;
            }else if(activeId=="FSDiagramAnalysisSingleDetailsCenterPanel2_Id"){//地面分析
            	type=2;
            }
            var new_params = { // 将图形数据id作为参数传给后台
                id: id,
                wellName:wellName,
                selectedWellName:selectedWellName,
                type:type
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});