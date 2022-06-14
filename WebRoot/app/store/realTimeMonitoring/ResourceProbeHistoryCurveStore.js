Ext.define('AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.resourceProbeHistoryCurveStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getResourceProbeHistoryCurveData',
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
            var itemCode=Ext.getCmp('ResourceMonitoringCurveItemCode_Id').rawValue;
            var itemName=Ext.getCmp('ResourceMonitoringCurveItem_Id').rawValue;
            var divId="ResourceProbeHistoryCurve_"+itemCode+"_DivId";
            divId="ResourceProbeHistoryCurveDiv_Id";
            var startDate=Ext.getCmp("ResourceProbeHistoryCurve_from_date_Id").getValue();
			if(startDate==""||startDate==null){
				Ext.getCmp("ResourceProbeHistoryCurve_from_date_Id").setValue(get_rawData.startDate);
			}
			ResourceProbeHistoryCurveChartFn(get_rawData,itemName,itemCode,divId);
        },
        beforeload: function (store, options) {
        	
        	var StartDate = Ext.getCmp('ResourceProbeHistoryCurve_from_date_Id').rawValue;
        	var EndDate = Ext.getCmp('ResourceProbeHistoryCurve_end_date_Id').rawValue;
        	var itemName=Ext.getCmp('ResourceMonitoringCurveItem_Id').rawValue;
        	var itemCode=Ext.getCmp('ResourceMonitoringCurveItemCode_Id').rawValue;
        	var new_params = {
                    startDate:StartDate,
                    endDate:EndDate,
                    itemName:itemName,
                    itemCode:itemCode
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});