Ext.define('AP.store.realTimeMonitoring.RealTimeMonitoringAddInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.realTimeMonitoringAddInfoStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceAddInfoData',
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
        	var isControl=get_rawData.isControl;
        	var commStatus=get_rawData.commStatus;
        	var deviceInfoDataList=get_rawData.deviceInfoDataList;
        	var auxiliaryDeviceList=get_rawData.auxiliaryDeviceList;
        	var deviceControlList=get_rawData.deviceControlList;
        	
        	//设备附加信息
        	var deviceInfoDataStr="{\"items\":[";
        	for(var i=0;i<deviceInfoDataList.length;i++){
        		deviceInfoDataStr+="{\"item\":\""+deviceInfoDataList[i].name+"\",\"value\":\""+deviceInfoDataList[i].value+"\"},";
        	}
        	if(stringEndWith(deviceInfoDataStr,",")){
        		deviceInfoDataStr = deviceInfoDataStr.substring(0, deviceInfoDataStr.length - 1);
    		}
        	deviceInfoDataStr+="]}";
        	
        	var deviceInfoStoreData=Ext.JSON.decode(deviceInfoDataStr);
        	var deviceInfoStore=Ext.create('Ext.data.Store', {
			    fields:['item', 'itemCode','value'],
			    data:deviceInfoStoreData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
        	var deviceInfoGridPanel=Ext.getCmp("RealTimeMonitoringDeviceInfoDataGridPanel_Id");
    		if(!isNotVal(deviceInfoGridPanel)){
    			deviceInfoGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'RealTimeMonitoringDeviceInfoDataGridPanel_Id',
//    				title:'附加信息',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: deviceInfoStore,
    			    columns: [
    			    	{ 
    			        	header: '名称',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:9,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        },
    			        { 
    			        	header: '变量', 
    			        	dataIndex: 'value',
    			        	align:'center',
    			        	flex:10,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        }
    			    ]
    			});
    			Ext.getCmp("RealTimeMonitoringRightDeviceAddInfoPanel").add(deviceInfoGridPanel);
    		}else{
    			deviceInfoGridPanel.reconfigure(deviceInfoStore);
    		}
        	
    		//辅件设备
    		var deviceAuxiliaryInfoDataStr="{\"items\":[";
        	for(var i=0;i<auxiliaryDeviceList.length;i++){
        		deviceAuxiliaryInfoDataStr+="{\"id\":"+auxiliaryDeviceList[i].id+","
        		+"\"name\":\""+auxiliaryDeviceList[i].name+"\","
        		+"\"detailsInfo\":\""+auxiliaryDeviceList[i].detailsInfo+"\""
        		+"},";
        	}
        	if(stringEndWith(deviceAuxiliaryInfoDataStr,",")){
        		deviceAuxiliaryInfoDataStr = deviceAuxiliaryInfoDataStr.substring(0, deviceAuxiliaryInfoDataStr.length - 1);
    		}
        	deviceAuxiliaryInfoDataStr+="]}";
        	
        	var deviceAuxiliaryInfoStoreData=Ext.JSON.decode(deviceAuxiliaryInfoDataStr);
        	var deviceAuxiliaryInfoStore=Ext.create('Ext.data.Store', {
			    fields:['id', 'name','detailsInfo'],
			    data:deviceAuxiliaryInfoStoreData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
        	var deviceAuxiliaryInfoGridPanel=Ext.getCmp("RealTimeMonitoringAuxiliaryDeviceInfoDataGridPanel_Id");
    		if(!isNotVal(deviceAuxiliaryInfoGridPanel)){
    			var  aaa=[];
//    			aaa.push('<p><b>厂家:</b> {manufacturer}</p>');
//    			aaa.push('<p><b>规格型号:</b> {model}</p>');
//    			aaa.push('<p><b>备注:</b> {remark}</p>');
//    			alert(aaa.join(','));
    			deviceAuxiliaryInfoGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'RealTimeMonitoringAuxiliaryDeviceInfoDataGridPanel_Id',
    				xtype: 'row-expander-grid',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: deviceAuxiliaryInfoStore,
    			    columns: [
    			    	{ 
    			        	header: '序号',  
    			        	xtype: 'rownumberer',
    			        	align: 'center',
    			        	width: 50
    			        },
    			        { 
    			        	header: '名称', 
    			        	dataIndex: 'name',
    			        	align:'center',
    			        	flex:10,
    			        	renderer:function(value){
    			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        	}
    			        }
    			    ],
    			    plugins: [{
    			        ptype: 'rowexpander',
    			        rowBodyTpl : new Ext.XTemplate(
//    			        		'<h style="line-height:1.5;"><b>厂家:</b> {manufacturer}</h><br/><h style="line-height:1.5;"><b>规格型号:</b> {model}</h><br/><h style="line-height:1.5;"><b>备注:</b> {remark}</h>',
    			        		'{detailsInfo}',
    			        {
    			            formatChange: function(v){
    			                var color = v >= 0 ? 'green' : 'red';
    			                return '<span style="color: ' + color + ';">' + Ext.util.Format.usMoney(v) + '</span>';
    			            }
    			        })
    			    }]
    			});
    			Ext.getCmp("RealTimeMonitoringRightAuxiliaryDeviceInfoPanel").add(deviceAuxiliaryInfoGridPanel);
    		}else{
    			deviceAuxiliaryInfoGridPanel.reconfigure(deviceAuxiliaryInfoStore);
    		}
    		var total=deviceAuxiliaryInfoGridPanel.getStore().getCount();
    		if(total>0&&Ext.getCmp("RealTimeMonitoringRightTabPanel").getActiveTab().id=="RealTimeMonitoringRightDeviceInfoPanel"){
    			deviceAuxiliaryInfoGridPanel.plugins[0].toggleRow(0,0);
    		}
        },
        beforeload: function (store, options) {
        	var wellName  = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var deviceId  = Ext.getCmp("RealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	var new_params = {
        			deviceId:deviceId,
        			wellName: wellName,
        			deviceType:getDeviceTypeFromTabId_first("RealTimeMonitoringTabPanel")
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});