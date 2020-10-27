Ext.define('AP.store.kafkaConfig.A9RawCurveDataStore', {
    extend: 'Ext.data.Store',
    id: "A9RawCurveDataStore_Id",
    alias: 'widget.A9RawCurveDataStore',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: context + '/kafkaConfigController/getA9RawCurveChartsData',
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
        	
        	showPContinuousDiagram(get_rawData.intervalCurveData,"采集间隔曲线",get_rawData.deviceId+' ['+get_rawData.acqTime+']',"点数","采集间隔(ms)",'#7cb5ec', "A9RwaDataCurveChartDiv1_Id");
        	showPContinuousDiagram(get_rawData.aCurveData,"角度电流值曲线",get_rawData.deviceId+' ['+get_rawData.acqTime+']',"点数","角度电流值(mA)",'#f7a35c', "A9RwaDataCurveChartDiv2_Id");
        	showPContinuousDiagram(get_rawData.fCurveData,"载荷电流值曲线",get_rawData.deviceId+' ['+get_rawData.acqTime+']',"点数","载荷电流值(mA)",'#f15c80', "A9RwaDataCurveChartDiv3_Id");
        	showPContinuousDiagram(get_rawData.wattCurveData,"有功功率曲线",get_rawData.deviceId+' ['+get_rawData.acqTime+']',"点数","有功功率(kW)",'#2b908f', "A9RwaDataCurveChartDiv4_Id");
        	showPContinuousDiagram(get_rawData.iCurveData,"电流曲线",get_rawData.deviceId+' ['+get_rawData.acqTime+']',"点数","电流(A)",'#f45b5b', "A9RwaDataCurveChartDiv5_Id");
        },
        beforeload: function (store, options) {
        	var id  = Ext.getCmp("A9RawDataGridPanel_Id").getSelectionModel().getSelection()[0].data.id;// 获取图形数据id
        	var deviceId  = Ext.getCmp("A9RawDataGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceId;
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