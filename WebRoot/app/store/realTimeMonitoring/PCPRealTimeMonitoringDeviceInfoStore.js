Ext.define('AP.store.realTimeMonitoring.PCPRealTimeMonitoringDeviceInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.PCPRealTimeMonitoringDeviceInfoStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceInfoData',
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
        	var get_rawData = store.proxy.reader.rawData;
        	
        	var deviceInfoGridPanel=Ext.getCmp("PCPRealTimeMonitoringDeviceInfoDataGridPanel_Id");
    		if(!isNotVal(deviceInfoGridPanel)){
    			deviceInfoGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'PCPRealTimeMonitoringDeviceInfoDataGridPanel_Id',
//    				title:'附加信息',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: store,
    			    columns: [
    			    	{ 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:9,
    			        	renderer:function(value){
    			        		if(isNotVal(value)){
        			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        		}
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:10,
    			        	renderer:function(value){
    			        		if(isNotVal(value)){
        			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        		}
    			        	}
    			        }
    			    ]
    			});
    			Ext.getCmp("PCPRealTimeMonitoringRightDeviceInfoPanel").add(deviceInfoGridPanel);
    		}
        },
        beforeload: function (store, options) {
        	var wellName  = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var deviceId  = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	var new_params = {
        			deviceId:deviceId,
        			wellName: wellName,
        			deviceType:1
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});