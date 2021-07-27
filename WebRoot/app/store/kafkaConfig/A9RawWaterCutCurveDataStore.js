Ext.define('AP.store.kafkaConfig.A9RawWaterCutCurveDataStore', {
    extend: 'Ext.data.Store',
    id: "A9RawWaterCutCurveDataStore_Id",
    alias: 'widget.A9RawWaterCutCurveDataStore',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/kafkaConfigController/getA9RawWaterCutCurveChartsData',
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
        	
//        	colors: ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#2b908f', '#f45b5b', '#91e8e1']
        	
        	showPContinuousDiagram(get_rawData.intervalCurveData,"采集间隔曲线",get_rawData.deviceId+' ['+get_rawData.acqTime+']',"点数","采集间隔(ms)",'#7cb5ec', "A9RawWaterCutDataCurveChartDiv1_Id");
        	showPContinuousDiagram(get_rawData.waterCutCurveData,"含水率曲线",get_rawData.deviceId+' ['+get_rawData.acqTime+']',"点数","含水率(%)",'#f7a35c', "A9RawWaterCutDataCurveChartDiv2_Id");
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("A9RawWaterCutDataGridPanel_Id").getSelectionModel().getSelection()[0].data.id;// 获取图形数据id
        	var deviceId  = Ext.getCmp("A9RawWaterCutDataGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceId;
            var selectedDeviceId = Ext.getCmp('A9RawDataDeviceCom_Id').getValue();
            var new_params = { // 将图形数据id作为参数传给后台
                id: id,
                deviceId:deviceId,
                selectedDeviceId:selectedDeviceId
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});