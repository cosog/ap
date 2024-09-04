Ext.define('AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.resourceProbeHistoryCurveStore',
    autoLoad: true,
    pageSize: defaultPageSize,
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
			
			var startDate=Ext.getCmp('ResourceProbeHistoryCurve_from_date_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.startDate.split(' ')[0]);
            	Ext.getCmp('ResourceProbeHistoryCurveStartTime_Hour_Id').setValue(get_rawData.startDate.split(' ')[1].split(':')[0]);
            	Ext.getCmp('ResourceProbeHistoryCurveStartTime_Minute_Id').setValue(get_rawData.startDate.split(' ')[1].split(':')[1]);
            	Ext.getCmp('ResourceProbeHistoryCurveStartTime_Second_Id').setValue(get_rawData.startDate.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('ResourceProbeHistoryCurve_end_date_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.endDate.split(' ')[0]);
            	Ext.getCmp('ResourceProbeHistoryCurveEndTime_Hour_Id').setValue(get_rawData.endDate.split(' ')[1].split(':')[0]);
            	Ext.getCmp('ResourceProbeHistoryCurveEndTime_Minute_Id').setValue(get_rawData.endDate.split(' ')[1].split(':')[1]);
            	Ext.getCmp('ResourceProbeHistoryCurveEndTime_Second_Id').setValue(get_rawData.endDate.split(' ')[1].split(':')[2]);
            }
			
			ResourceProbeHistoryCurveChartFn(get_rawData,itemName,itemCode,divId);
        },
        beforeload: function (store, options) {
        	var startDate = Ext.getCmp('ResourceProbeHistoryCurve_from_date_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Minute_Id').getValue();
        	var startTime_Second=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Second_Id').getValue();
        	
        	var endDate = Ext.getCmp('ResourceProbeHistoryCurve_end_date_Id').rawValue;
        	var endTime_Hour=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Minute_Id').getValue();
        	var endTime_Second=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Second_Id').getValue();
        	
        	
        	
        	
        	var itemName=Ext.getCmp('ResourceMonitoringCurveItem_Id').rawValue;
        	var itemCode=Ext.getCmp('ResourceMonitoringCurveItemCode_Id').rawValue;
        	var new_params = {
        			startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),
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