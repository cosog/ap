Ext.define('AP.store.electricAnalysis.ElectricAnalysisRealtimeHistoryCurveStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.ElectricAnalysisRealtimeHistoryCurveStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/PSToFSController/getElecAnalysisRealtimeDetailsCurveData',
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
            var itemCode=Ext.getCmp('ElectricAnalysisRealtimeDetailsCurveItemCode_Id').rawValue;
            var itemName=Ext.getCmp('ElectricAnalysisRealtimeDetailsCurveItem_Id').rawValue;
            var divId="ElectricAnalysisRealtimeHistoryCurve_"+itemCode+"_DivId";
            divId="ElectricAnalysisRealtimeHistoryCurveDiv_Id";
            var startDate=Ext.getCmp("ElectricAnalysisRealtimeHistoryCurveStartDate_Id").getValue();
			if(startDate==""||startDate==null){
				Ext.getCmp("ElectricAnalysisRealtimeHistoryCurveStartDate_Id").setValue(get_rawData.startDate);
			}
			ElecAnalysisRealtimeDetailsCurveChartFn(get_rawData,itemName,itemCode,divId);
        },
        beforeload: function (store, options) {
        	var tabPanel = Ext.getCmp("electricAnalysisRealtimeDetailsTabpanel_Id");
    		var activeId = tabPanel.getActiveTab().id;
        	var gridPanelId="ElectricAnalysisRealtimeDiscreteDetails_Id";
        	var type="Discrete";
        	if(activeId=="electricAnalysisRealtimeDetailsDiscretePanel_Id"){
        		gridPanelId="ElectricAnalysisRealtimeDiscreteDetails_Id";
        		type="Discrete";
        	}else if(activeId=="electricAnalysisRealtimeDetailsDiagramPanel_Id"){
        		gridPanelId='ElectricAnalysisRealtimeDiagramDetails_Id';
        		type="Diagram";
        	}
        	var wellName  = Ext.getCmp(gridPanelId).getSelectionModel().getSelection()[0].data.wellName;
        	var StartDate = Ext.getCmp('ElectricAnalysisRealtimeHistoryCurveStartDate_Id').rawValue;
        	var EndDate = Ext.getCmp('ElectricAnalysisRealtimeHistoryCurveEndDate_Id').rawValue;
        	var itemName=Ext.getCmp('ElectricAnalysisRealtimeDetailsCurveItem_Id').rawValue;
        	var itemCode=Ext.getCmp('ElectricAnalysisRealtimeDetailsCurveItemCode_Id').rawValue;
        	var new_params = {
        			wellName: wellName,
                    startDate:StartDate,
                    endDate:EndDate,
                    itemName:itemName,
                    itemCode:itemCode,
                    type:type
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});