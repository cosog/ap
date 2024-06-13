Ext.define('AP.store.realTimeMonitoring.RealTimeMonitoringDeviceProductionDataStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.RealTimeMonitoringDeviceProductionDataStore',
    autoLoad: true,
    pageSize: defaultPageSize,
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
        	var deviceInfoGridPanel=Ext.getCmp("RealTimeMonitoringDeviceProductionDataGridPanel_Id");
    		if(!isNotVal(deviceInfoGridPanel)){
    			deviceInfoGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'RealTimeMonitoringDeviceProductionDataGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: store,
    			    columns: [
    			    	{ 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:1,
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
    			        	flex:1,
    			        	renderer:function(value){
    			        		if(isNotVal(value)){
        			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        		}
    			        	}
    			        }
    			    ]
    			});
    			Ext.getCmp("RealTimeMonitoringRightCalculateDataPanel").add(deviceInfoGridPanel);
    		}
        },
        beforeload: function (store, options) {
        	var wellName  = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var deviceId  = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	var deviceType=getDeviceTypeFromTabId("RealTimeMonitoringTabPanel");
        	var new_params = {
        			deviceId:deviceId,
        			wellName: wellName,
        			deviceType:deviceType
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});