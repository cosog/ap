Ext.define('AP.store.diagnosis.SinglePumpCardStore', {
    extend: 'Ext.data.Store',
    id: "SinglePumpCardStore_Id",
    alias: 'widget.SinglePumpCardStore',
    model: 'AP.model.graphical.X_Y_Model',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/diagnosisAnalysisOnlyController/querySinglePumpCard',
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
        	var powerData2=powerData.replace(/0.0/g,"").replace(/0/g,"").replace(/,/g,"").replace(/\./g,"");
        	var currentData=get_rawData.currentCurveData;
        	
        	var tabPanel = Ext.getCmp("FSDiagramAnalysisSingleDetailsCenterTabPanel_Id");
            var activeId = tabPanel.getActiveTab().id;
            if(activeId=="FSDiagramAnalysisSingleDetailsCenterPanel1_Id"){
            	if(powerData2==""){
            		showFSDiagramFromPumpcard(get_rawData, "FSDiagramAnalysisSingleDetailsDiv1_id"); // 调用画泵功图的函数
            		showRodPress(get_rawData, "FSDiagramAnalysisSingleDetailsDiv2_id");    // 调用画杆柱应力的函数
                    showPumpCard(get_rawData, "FSDiagramAnalysisSingleDetailsDiv3_id"); // 调用画泵功图的函数
                    showPumpEfficiency(get_rawData, "FSDiagramAnalysisSingleDetailsDiv4_id");    // 调用画泵效组成的函数
                    showPSDiagram(get_rawData, "FSDiagramAnalysisSingleDetailsDiv5_id");
                    showASDiagram(get_rawData, "FSDiagramAnalysisSingleDetailsDiv6_id");
//                    $("#FSDiagramAnalysisSingleDetailsDiv5_id").html("");
//                    $("#FSDiagramAnalysisSingleDetailsDiv6_id").html("");
                    
            	}else{
            		showFSDiagramFromPumpcard(get_rawData, "FSDiagramAnalysisSingleDetailsDiv1_id"); // 调用画泵功图的函数
                	showPSDiagram(get_rawData, "FSDiagramAnalysisSingleDetailsDiv2_id");
                    showPumpCard(get_rawData, "FSDiagramAnalysisSingleDetailsDiv3_id"); // 调用画泵功图的函数
                    showASDiagram(get_rawData, "FSDiagramAnalysisSingleDetailsDiv4_id");
                    showRodPress(get_rawData, "FSDiagramAnalysisSingleDetailsDiv5_id");    // 调用画杆柱应力的函数
                    showPumpEfficiency(get_rawData, "FSDiagramAnalysisSingleDetailsDiv6_id");    // 调用画泵效组成的函数
            	}
            }else if(activeId=="FSDiagramAnalysisSingleDetailsCenterPanel2_Id"){
            	showPContinuousDiagram(get_rawData.IaCurveData,"A相电流曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']','<span style="text-align:center;">点数<br />',"A相电流(A)",'#e3cc19',"FSDiagramAnalysisSingleDetailsDiv7_id");
            	showPContinuousDiagram(get_rawData.IbCurveData,"B相电流曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']','<span style="text-align:center;">点数<br />',"B相电流(A)",'#FF6633',"FSDiagramAnalysisSingleDetailsDiv8_id");
            	showPContinuousDiagram(get_rawData.IcCurveData,"C相电流曲线",get_rawData.wellName+' ['+get_rawData.acquisitionTime+']','<span style="text-align:center;">点数<br />',"C相电流(A)",'#CC0000',"FSDiagramAnalysisSingleDetailsDiv9_id");
            
            }
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.id;// 获取图形数据id
        	var wellName  = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id").getSelectionModel().getSelection()[0].data.wellName;
            var selectedWellName = Ext.getCmp('FSDiagramAnalysisSingleDetailsWellCom_Id').getValue();
            var new_params = { // 将图形数据id作为参数传给后台
                id: id,
                wellName:wellName,
                selectedWellName:selectedWellName
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});