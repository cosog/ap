Ext.define('AP.store.realTimeMonitoring.RPCRealTimeMonitoringControlAndInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.rpcRealTimeMonitoringControlAndInfoStore',
    autoLoad: true,
    pageSize: 10000,
    proxy: {
        type: 'ajax',
        url: context + '/realTimeMonitoringController/getDeviceControlandInfoData',
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
        	
        	//设备信息
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
        	var deviceInfoGridPanel=Ext.getCmp("RPCRealTimeMonitoringDeviceInfoDataGridPanel_Id");
    		if(!isNotVal(deviceInfoGridPanel)){
    			deviceInfoGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'RPCRealTimeMonitoringDeviceInfoDataGridPanel_Id',
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
    			Ext.getCmp("RPCRealTimeMonitoringRightDeviceInfoPanel").add(deviceInfoGridPanel);
    		}else{
    			deviceInfoGridPanel.reconfigure(deviceInfoStore);
    		}
    		
        	//控制
        	var controlDataStr="{\"items\":[";
        	for(var i=0;i<deviceControlList.length;i++){
        		controlDataStr+="{\"item\":\""+deviceControlList[i].title+"\",\"itemcode\":\""+deviceControlList[i].name
        		+"\",\"resolutionMode\":"+deviceControlList[i].resolutionMode
        		+",\"itemMeaning\":"+deviceControlList[i].itemMeaning
        		+",\"value\":\""+deviceControlList[i].value+"\",\"operation\":true,\"isControl\":"+isControl+",\"showType\":1,\"commStatus\":"+commStatus+"},";
        	}
        	if(stringEndWith(controlDataStr,",")){
    			controlDataStr = controlDataStr.substring(0, controlDataStr.length - 1);
    		}
    		controlDataStr+="]}";
        	
    		var controlStoreData=Ext.JSON.decode(controlDataStr);
    		
    		var controlStore=Ext.create('Ext.data.Store', {
			    fields:['item','value','operation'],
			    data:controlStoreData,
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
//    		Ext.getCmp("RPCRealTimeMonitoringRightControlPanel").removeAll();
    		var controlGridPanel=Ext.getCmp("RPCRealTimeMonitoringControlDataGridPanel_Id");
    		if(!isNotVal(controlGridPanel)){
    			controlGridPanel=Ext.create('Ext.grid.Panel', {
    				id:'RPCRealTimeMonitoringControlDataGridPanel_Id',
    				border: false,
    				columnLines: true,
    				forceFit: false,
    				store: controlStore,
    			    columns: [{ 
    			        	header: '操作项',  
    			        	dataIndex: 'item',
    			        	align:'left',
    			        	flex:6,
    			        	renderer:function(value,e,o){
    			        		e.tdStyle ="vertical-align:middle;";
    			        		if(isNotVal(value)){
        			        		return "<span data-qtip=\""+(value==undefined?"":value)+"\">"+(value==undefined?"":value)+"</span>";
    			        		}
    			        	}
    			        },{ 	
    			        	header: '操作', 
    			        	dataIndex: 'operation',
    			        	align:'center',
//    			        	flex:8,
    			        	width:93,
    			        	renderer :function(value,e,o){
    			        		var id = e.record.id;
//    			        		console.log("id:"+id);
    			        		var item=o.data.item;
    			        		var itemcode=o.data.itemcode;
    			        		var isControl=o.data.isControl;
    			        		var commStatus = o.data.commStatus;
    			        		var resolutionMode=o.data.resolutionMode;
    			        		var itemMeaning=o.data.itemMeaning;
    			        		var text="";
    			        		var hand=false;
    			        		var hidden=false;
    			        		if(commStatus>0&&isControl==1){
    			        			hand=false;
    			        		}else{
    			        			hand=true;
    			        		}
    			        		if(!o.data.operation){
    			        			hidden=true;
    			        		}
    			        		text="设置";
    			        		e.tdStyle ="vertical-align:middle;";
    			        		
    			        		var all_loading = new Ext.LoadMask({
    	                            msg: '命令发送中，请稍后...',
    	                            target: Ext.getBody().component
    	                        });
    			        		
    			        		if(resolutionMode==1&&itemMeaning.length==1){
    			        			Ext.defer(function () {
        		                        Ext.widget('button', {
        		                        	renderTo: id,
        		                        	height: 25,
    		                            	width: 38,
    		                            	style: 'margin:1',
    		                            	align:'center',
    		                            	disabled:hand,
    		                            	text: itemMeaning[0][1],
    		                            	tooltip:itemMeaning[0][1],
    		                            	handler: function () {
    		                            		all_loading.show();
    		                            		var wellName  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
    		                            		var deviceId  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
    		                            		Ext.Ajax.request({
    		                            			url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
    		                                        method: "POST",
    		                                        params: {
    		                                        	deviceId:deviceId,
    		                                        	wellName: wellName,
    		                                        	deviceType: 0,
    		                                            controlType:itemcode,
    		                                            controlValue:itemMeaning[0][0]
    		                                        },
    		                                        success: function (response, action) {
    		                                        	all_loading.hide();
    		                                        	var data = Ext.decode(response.responseText);
    		                                        	if (data.success==true && data.flag==false) {
    		                                                Ext.MessageBox.show({
    		                                                    title: cosog.string.ts,
    		                                                    msg: "<font color=red>" + cosog.string.sessionINvalid + "。</font>",
    		                                                    icon: Ext.MessageBox.INFO,
    		                                                    buttons: Ext.Msg.OK,
    		                                                    fn: function () {
    		                                                        window.location.href = context + "/login/toLogin";
    		                                                    }
    		                                                });
    		                                            } else if (data.flag == true && data.error == false) {
    		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
    		                                            }  else if (data.flag == true && data.error == true) {
    		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
    		                                            } 
    		                                        },
    		                                        failure: function () {
    		                                        	all_loading.hide();
    		                                        	Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
    		                                        }
    		                                	});   
    		                            	}
    		                            });
        		                    }, 50);
    			        		}else if(resolutionMode==1&&itemMeaning.length==2){
    			        			Ext.defer(function () {
        		                        Ext.widget('toolbar', {
        		                            renderTo: id,
        		                            dock: 'top',
        		                            style: 'margin:0;padding:0',
        		                            ui: 'footer',
        		                            align:'center',
        		                            height: 25,
        		                            width: 85,
        		                            items: [{
        		                            	xtype: 'button',
        		                            	height: 25,
        		                            	width: 38,
        		                            	style: 'margin:1',
        		                            	align:'center',
        		                            	disabled:hand,
            		                            hidden:hidden,
        		                            	text: itemMeaning[0][1],
        		                            	tooltip:itemMeaning[0][1],
        		                            	handler: function () {
        		                            		all_loading.show();
        		                            		var wellName  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		                            		var deviceId  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		                            		Ext.Ajax.request({
        		                            			url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
        		                                        method: "POST",
//        		                                        waitMsg: cosog.string.updatewait,
//        		                                        waitTitle: 'Please Wait...',
        		                                        params: {
        		                                        	deviceId:deviceId,
        		                                        	wellName: wellName,
        		                                        	deviceType: 0,
        		                                            controlType:itemcode,
        		                                            controlValue:itemMeaning[0][0]
        		                                        },
        		                                        success: function (response, action) {
        		                                        	all_loading.hide();
        		                                        	var data = Ext.decode(response.responseText);
        		                                        	if (data.success==true && data.flag==false) {
        		                                                Ext.MessageBox.show({
        		                                                    title: cosog.string.ts,
        		                                                    msg: "<font color=red>" + cosog.string.sessionINvalid + "。</font>",
        		                                                    icon: Ext.MessageBox.INFO,
        		                                                    buttons: Ext.Msg.OK,
        		                                                    fn: function () {
        		                                                        window.location.href = context + "/login/toLogin";
        		                                                    }
        		                                                });
        		                                            } else if (data.flag == true && data.error == false) {
        		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
        		                                            }  else if (data.flag == true && data.error == true) {
        		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
        		                                            } 
        		                                        },
        		                                        failure: function () {
        		                                        	all_loading.hide();
        		                                        	Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
        		                                        }
        		                                	});   
        		                            	}
        		                            },{
        		                            	xtype: 'button',
        		                            	height: 25,
        		                            	width: 38,
        		                            	style: 'margin:0',
        		                            	align:'center',
        		                            	disabled:hand,
            		                            hidden:hidden,
        		                            	text: itemMeaning[1][1],
        		                            	tooltip:itemMeaning[1][1],
        		                            	handler: function () {
        		                            		all_loading.show();
        		                            		var wellName  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		                            		var deviceId  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		                            		Ext.Ajax.request({
        		                            			url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
        		                                        method: "POST",
//        		                                        waitMsg: cosog.string.updatewait,
//        		                                        waitTitle: 'Please Wait...',
        		                                        params: {
        		                                        	deviceId:deviceId,
        		                                        	wellName: wellName,
        		                                        	deviceType: 0,
        		                                            controlType:itemcode,
        		                                            controlValue:itemMeaning[1][0]
        		                                        },
        		                                        success: function (response, action) {
        		                                        	all_loading.hide();
        		                                        	var data = Ext.decode(response.responseText);
        		                                        	if (data.success==true && data.flag==false) {
        		                                                Ext.MessageBox.show({
        		                                                    title: cosog.string.ts,
        		                                                    msg: "<font color=red>" + cosog.string.sessionINvalid + "。</font>",
        		                                                    icon: Ext.MessageBox.INFO,
        		                                                    buttons: Ext.Msg.OK,
        		                                                    fn: function () {
        		                                                        window.location.href = context + "/login/toLogin";
        		                                                    }
        		                                                });
        		                                            } else if (data.flag == true && data.error == false) {
        		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
        		                                            }  else if (data.flag == true && data.error == true) {
        		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
        		                                            } 
        		                                        },
        		                                        failure: function () {
        		                                        	all_loading.hide();
        		                                        	Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
        		                                        }
        		                                	});   
        		                            	}
        		                            }]
        		                        });
        		                    }, 50);
    			        		}else if(resolutionMode==0&&itemMeaning.length>0){
    			        			Ext.defer(function () {
        		                        Ext.widget('toolbar', {
        		                            renderTo: id,
        		                            dock: 'top',
        		                            style: 'margin:0;padding:0',
        		                            ui: 'footer',
        		                            height: 25,
        		                            width: 85,
        		                            items: [{
        		                            	xtype: 'button',
        		                            	height: 25,
        		                            	width: 38,
        		                            	style: 'margin:1',
        		                            	disabled:hand,
            		                            hidden:hidden,
        		                            	text: '开',
        		                            	tooltip:'开',
        		                            	handler: function () {
        		                            		all_loading.show();
        		                            		var wellName  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		                            		var deviceId  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		                            		Ext.Ajax.request({
        		                            			url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
        		                                        method: "POST",
//        		                                        waitMsg: cosog.string.updatewait,
//        		                                        waitTitle: 'Please Wait...',
        		                                        params: {
        		                                        	deviceId:deviceId,
        		                                        	wellName: wellName,
        		                                        	deviceType: 0,
        		                                            controlType:itemcode,
        		                                            controlValue:1
        		                                        },
        		                                        success: function (response, action) {
        		                                        	all_loading.hide();
        		                                        	var data = Ext.decode(response.responseText);
        		                                        	if (data.success==true && data.flag==false) {
        		                                                Ext.MessageBox.show({
        		                                                    title: cosog.string.ts,
        		                                                    msg: "<font color=red>" + cosog.string.sessionINvalid + "。</font>",
        		                                                    icon: Ext.MessageBox.INFO,
        		                                                    buttons: Ext.Msg.OK,
        		                                                    fn: function () {
        		                                                        window.location.href = context + "/login/toLogin";
        		                                                    }
        		                                                });
        		                                            } else if (data.flag == true && data.error == false) {
        		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
        		                                            }  else if (data.flag == true && data.error == true) {
        		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
        		                                            } 
        		                                        },
        		                                        failure: function () {
        		                                        	all_loading.hide();
        		                                        	Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
        		                                        }
        		                                	});   
        		                            	}
        		                            },{
        		                            	xtype: 'button',
        		                            	height: 25,
        		                            	width: 38,
        		                            	style: 'margin:0,0,0,0',
        		                            	disabled:hand,
            		                            hidden:hidden,
        		                            	text: '关',
        		                            	tooltip:'关',
        		                            	handler: function () {
        		                            		all_loading.show();
        		                            		var wellName  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		                            		var deviceId  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		                            		Ext.Ajax.request({
        		                            			url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
        		                                        method: "POST",
//        		                                        waitMsg: cosog.string.updatewait,
//        		                                        waitTitle: 'Please Wait...',
        		                                        params: {
        		                                        	deviceId:deviceId,
        		                                        	wellName: wellName,
        		                                        	deviceType: 0,
        		                                            controlType:itemcode,
        		                                            controlValue:0
        		                                        },
        		                                        success: function (response, action) {
        		                                        	all_loading.hide();
        		                                        	var data = Ext.decode(response.responseText);
        		                                        	if (data.success==true && data.flag==false) {
        		                                                Ext.MessageBox.show({
        		                                                    title: cosog.string.ts,
        		                                                    msg: "<font color=red>" + cosog.string.sessionINvalid + "。</font>",
        		                                                    icon: Ext.MessageBox.INFO,
        		                                                    buttons: Ext.Msg.OK,
        		                                                    fn: function () {
        		                                                        window.location.href = context + "/login/toLogin";
        		                                                    }
        		                                                });
        		                                            } else if (data.flag == true && data.error == false) {
        		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
        		                                            }  else if (data.flag == true && data.error == true) {
        		                                                Ext.Msg.alert(cosog.string.ts, "<font color=red>" + data.msg + "</font>");
        		                                            } 
        		                                        },
        		                                        failure: function () {
        		                                        	all_loading.hide();
        		                                        	Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font>】：" + cosog.string.contactadmin + "！")
        		                                        }
        		                                	});   
        		                            	}
        		                            }]
        		                        });
        		                    }, 50);
    			        		}else{
    			        			Ext.defer(function () {
        		                        Ext.widget('button', {
        		                            renderTo: id,
        		                            height: 25,
        		                            width: 38,
        		                            text: text,
        		                            tooltip:text,
        		                            disabled:hand,
        		                            hidden:hidden,
        		                            handler: function () {
        		                            	var operaName="";
        		                            	if(text=="停抽"||text=="启抽"||text=="即时采集"||text=="即时刷新"){
        		                            		operaName="是否执行"+text+"操作";
        		                            	}else{
        		                            		operaName="是否执行"+text+item.split("(")[0]+"操作";
        		                            	}
        		                            	 Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        		                                 Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
//        		                                 Ext.Msg.confirm("操作确认", operaName, function (btn) {
//        		                                     if (btn == "yes") {
        		                                         var win_Obj = Ext.getCmp("DeviceControlCheckPassWindow_Id")
        		                                         if (win_Obj != undefined) {
        		                                             win_Obj.destroy();
        		                                         }
        		                                         var DeviceControlCheckPassWindow = Ext.create("AP.view.realTimeMonitoring.DeviceControlCheckPassWindow", {
        		                                             title: '控制'
        		                                         });
        		                                     	 var wellName  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        		                                     	 var deviceId  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        		                                     	 Ext.getCmp("DeviceControlWellName_Id").setValue(wellName);
        		                                     	 Ext.getCmp("DeviceControlDeviceId_Id").setValue(deviceId);
        		                                     	 Ext.getCmp("DeviceControlDeviceType_Id").setValue(0);
        		                                         
        		                                     	 Ext.getCmp("DeviceControlType_Id").setValue(o.data.itemcode);
        		                                         Ext.getCmp("DeviceControlShowType_Id").setValue(resolutionMode);
        		                                         
        		                                         Ext.getCmp("DeviceControlValue_Id").setValue("");
//        		                                         Ext.getCmp("checkPassFromPassword_id").setValue("");
        		                                         
        		                                         Ext.getCmp("DeviceControlShowType_Id").setValue(2);
        		                                         Ext.getCmp("DeviceControlValue_Id").show();
    		                                        	 Ext.getCmp("DeviceControlValueCombo_Id").hide();
    		                                        	 Ext.getCmp("DeviceControlValue_Id").setFieldLabel(o.data.item);
    		                                        	 Ext.getCmp("DeviceControlValue_Id").setValue(o.data.value);
        		                                         
        		                                         if(resolutionMode==0){//开关量
//        		                                        	 Ext.getCmp("DeviceControlValue_Id").hide();
//        		                                        	 Ext.getCmp("DeviceControlValueCombo_Id").hide();
        		                                         }else if(resolutionMode==1&&itemMeaning.length>0){//枚举量
        		                                        	 Ext.getCmp("DeviceControlValue_Id").hide();
        		                                        	 Ext.getCmp("DeviceControlShowType_Id").setValue(resolutionMode);
        		                                        	 Ext.getCmp("DeviceControlValueCombo_Id").setFieldLabel(o.data.item);
        		                                        	 var data=[];
        		                                        	 for(var k=0;k<itemMeaning.length;k++){
        		                                        		 data.push(itemMeaning[k]);
        		                                        	 }
        		                                        	 var controlTypeStore = new Ext.data.SimpleStore({
        		                                             	autoLoad : false,
        		                                                 fields: ['boxkey', 'boxval'],
        		                                                 data: data
        		                                             });
        		                                        	 Ext.getCmp("DeviceControlValueCombo_Id").setStore(controlTypeStore);
        		                                        	 Ext.getCmp("DeviceControlValueCombo_Id").show();
        		                                         }else{
        		                                        	 
        		                                         }
        		                                         DeviceControlCheckPassWindow.show();
        		                                         Ext.getCmp("DeviceControlValue_Id").setValue("");
//        		                                     }
//        		                                 });
        		                            }
        		                        });
        		                    }, 50);
    			        		}
    		                    return Ext.String.format('<div id="{0}"></div>', id);
    			        	} 
    			        }
    			    ]
    			});
    			Ext.getCmp("RPCRealTimeMonitoringRightControlPanel").add(controlGridPanel);
    		}else{
    			controlGridPanel.reconfigure(controlStore);
    		}
        },
        beforeload: function (store, options) {
        	var wellName  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
        	var deviceId  = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
        	var new_params = {
        			deviceId:deviceId,
        			wellName: wellName,
        			deviceType:0
                };
           Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});