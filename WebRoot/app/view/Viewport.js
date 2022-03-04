var websocketClient = null;
Ext.define('AP.view.Viewport', {
    extend: 'Ext.container.Viewport',
    id: 'frame_imivport_ids',
    layout: 'border',
    items: [{
        id: 'frame_north',
        region: 'north',
        height: 60,
        border: false,
        bodyStyle:{
        	'z-index':10
        },
        html: '<div id="imgTitle"><img id="logoImg" '+(showLogo?'':'style="display:none;"')+' src="../images/logo/logo.png" /><span id="bannertitle">' +viewInformation.title+ '</span>' +
        		"<div id='passAndExitButton'><a href='#' id='logon_a1' onclick='resetPwdFn()'><span id='logon_a1_text'>修改密码</span></a></div> " +
        		"<div id='passAndExitButton'><a href='#' id='logon_a2' onclick='userLoginOut()'><span id='logon_a2_text'>退出</span></a></div>" +
        		"<div id='passAndExitButton'><a href='#' id='logon_a3' onclick='showHelpDocumentWinFn()'><span id='logon_a3_text'>帮助</span></a></div>" +
        		//"<div id='passAndExitButton2' ><a href='#' title='全屏显示' id='logon_a3' onclick='fullscreen()'></a></div> " +
        		//"<div id='passAndExitButton3' style='display:none;'><a href='#' title='退出全屏' id='logon_a4'  onclick='exitFullscreen()'></a></div> " +
        		"</div>"
   }, {
        id: 'center_ids',
        region: 'center',
        border: false,
        split: true,
        bodyStyle:{
        	'z-index':1
        },
        layout: {
            type: 'border'
        },
        defaults: {
            split: true
        },
        items: [
        	{
            id: "frame_center_ids",
            region: 'center',
            xtype: 'tabpanel',
            layout: 'fit',
            closeAction: 'destroy',
            items: [],
            listeners: {
                tabchange: function (tabPanel, newCard, oldCard,obj) {
                    var tabPanel = Ext.getCmp("frame_center_ids");
                    var tabnums = tabPanel.items.getCount();
                    var curValues = Ext.getCmp("tabNums_Id").getValue();
                    var curId = newCard.id;
                    var modules = curId.split("_");
                    var cyrData = "";
                    if (modules.length > 2) {
                        if (curId.indexOf("diagnosis") > -1) {
                            var secondBottomTab_Id = Ext.getCmp("secondBottomTab_Id").getValue();
                            cyrData = changeTabId(curId, secondBottomTab_Id);
                        } else if (curId.indexOf("compute") > -1) {
                            var productBottomTab_Id = Ext.getCmp("productBottomTab_Id").getValue();
                            cyrData = changeTabId(curId, productBottomTab_Id);

                        } else if (curId.indexOf("image") > -1) {
                            var imageBottomTab_Id = Ext.getCmp("imageBottomTab_Id").getValue();
                            cyrData = changeTabId(curId, imageBottomTab_Id);
                        }
                        addPanelEps(curId, cyrData, curId);
                    } else {
                        addPanelEps(curId, curId, curId);
                    }
                    Ext.getCmp("tabNums_Id").setValue(tabnums);
                },
                delay: 300
            }
      }]
   },{
	   region: 'west',
       border: false,
       layout: 'border',
       collapsible: true,
       split: true,
       flex:0.11,
       items:[{
           region: 'south',
           height:'50%',
           layout: 'fit',
           border: false,
           id: 'frame_west',
           split: true,
           hidden: false,
           collapsible: true,
           autoDestroy: true,
           items: [{
               xtype: 'iframeView'
           }]
       },{
    	   id: 'MainModuleShow_Id',
           region: 'center',
           split: false,
           height:'50%',
           collapsible: false,
           layout: 'fit',
           border: false,
           autoDestroy: true
       }]
   },
   {
        id: 'frame_south',
        region: 'south',
        xtype: "panel",
        border: false,
        hidden:true,
        bodyStyle: 'background-color:#FBFBFB;',
        html: "<div id=\"footer\">" + viewInformation.copy+"&nbsp;<a href='"+viewInformation.linkaddress+"' target='_blank'>"+viewInformation.linkshow+"</a> "+"</div>",
        height: 30
   }],
   listeners: {
	   afterrender: function ( panel, eOpts) {
//			alert(userAccount);
			var baseUrl=getBaseUrl().replace("https","ws").replace("http","ws");
			var moduleCode = "ApWebSocketClient_"+userAccount;
			if ('WebSocket' in window) {
//				websocketClient = new ReconnectingWebSocket(baseUrl+"/websocket/socketServer?module_Code="+moduleCode);
				websocketClient = new ReconnectingWebSocket(baseUrl+"/websocketServer/"+moduleCode);
				websocketClient.debug = true;
				websocketClient.reconnectInterval = 1000;
				websocketClient.timeoutInterval = 2000;
				websocketClient.maxReconnectInterval = 30000;
				websocketClient.reconnectDecay=1.5;
				websocketClient.automaticOpen = true;
//				websocketClient.maxReconnectAttempts = 5;
			}
			else if ('MozWebSocket' in window) {
//				websocketClient = new MozWebSocket(baseUrl+"/websocket/socketServer?module_Code="+moduleCode);
				websocketClient = new MozWebSocket(baseUrl+"/websocketServer/"+moduleCode);
			}else {
//				websocketClient = new SockJS(getBaseUrl()+"/sockjs/socketServer?module_Code="+moduleCode);
				websocketClient = new SockJS(getBaseUrl()+"/websocketServer/"+moduleCode);
			}
			websocketClient.onopen = websocketOnOpen;
			websocketClient.onmessage = websocketOnMessage;
			websocketClient.onerror = websocketOnError;
			websocketClient.onclose = websocketOnClose;
		
	   },
	   beforeclose: function ( panel, eOpts) {
		   websocketClose(websocketClient);
	   }
   }
});

function websocketOnOpen(openEvt) {
//  alert(openEvt.Data);
}

function websocketOnMessage(evt) {
	var activeId = Ext.getCmp("frame_center_ids").getActiveTab().id;
	var receiveData=evt.data;
	if(evt.data.indexOf("}{")>=0){
		var dataStr=evt.data.replace("}{", "}@@@@{");
		receiveData=dataStr.split("@@@@")[0];
	}
	var data=Ext.JSON.decode(receiveData);
	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
	var orgIdArr=leftOrg_Id.split(",");
	if(data.functionCode.toUpperCase()=="adExitAndDeviceOffline".toUpperCase()){//ad退出，所有设备离线
		var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
		var activeId = tabPanel.getActiveTab().id;
		if(activeId=="PumpRealTimeMonitoringInfoPanel_Id"){
			var statTabActiveId = Ext.getCmp("PumpRealTimeMonitoringStatTabPanel").getActiveTab().id;
			if(statTabActiveId=="PumpRealTimeMonitoringStatGraphPanel_Id"){
				loadAndInitCommStatusStat(true);
			}else if(statTabActiveId=="PumpRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
				loadAndInitDeviceTypeStat(true);
			}
			Ext.getCmp('RealTimeMonitoringPumpDeviceListComb_Id').setValue('');
			Ext.getCmp('RealTimeMonitoringPumpDeviceListComb_Id').setRawValue('');
			var gridPanel = Ext.getCmp("PumpRealTimeMonitoringListGridPanel_Id");
			if (isNotVal(gridPanel)) {
				gridPanel.getSelectionModel().deselectAll(true);
				gridPanel.getStore().load();
			}
		}else if(activeId=="PipelineRealTimeMonitoringInfoPanel_Id"){
			var statTabActiveId = Ext.getCmp("PipelineRealTimeMonitoringStatTabPanel").getActiveTab().id;
			if(statTabActiveId=="PipelineRealTimeMonitoringStatGraphPanel_Id"){
				loadAndInitCommStatusStat(true);
			}else if(statTabActiveId=="PipelineRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
				loadAndInitDeviceTypeStat(true);
			}
			Ext.getCmp('RealTimeMonitoringPipelineDeviceListComb_Id').setValue('');
			Ext.getCmp('RealTimeMonitoringPipelineDeviceListComb_Id').setRawValue('');
			var gridPanel = Ext.getCmp("PipelineRealTimeMonitoringListGridPanel_Id");
			if (isNotVal(gridPanel)) {
				gridPanel.getSelectionModel().deselectAll(true);
				gridPanel.getStore().load();
			}
		}
	}else if(data.functionCode.toUpperCase()=="pumpDeviceRealTimeMonitoringData".toUpperCase()){//接收到推送的泵设备实时监控数据
		if(activeId.toUpperCase()=="DeviceRealTimeMonitoring".toUpperCase()){
			var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="PumpRealTimeMonitoringInfoPanel_Id"){
				//更新通信状态统计
				getDeviceCommStatusTotal();
				//更新设备概览列表
				var PumpRealTimeMonitoringListGrid = Ext.getCmp("PumpRealTimeMonitoringListGridPanel_Id");
				if(isNotVal(PumpRealTimeMonitoringListGrid)){
					var store = PumpRealTimeMonitoringListGrid.getStore();
					for(var i=0;i<store.getCount();i++){
						var record=store.getAt(i);
						if(record.data.wellName==data.wellName){
							record.set("commStatusName","在线");
							record.set("commStatus",1);
							record.set("commAlarmLevel",0);
							record.set("acqTime",data.acqTime);
							for(var j=0;j<data.CellInfo.length;j++){
								var cellValue=record.get(data.CellInfo[j].column.toUpperCase());
								var cellValue2=record.get(data.CellInfo[j].column.toLowerCase());
								var cellValue3=record.get(data.CellInfo[j].column);
								if(cellValue!=undefined){
									record.set(data.CellInfo[j].column.toUpperCase(),data.CellInfo[j].value);
								}else if(cellValue2!=undefined){
									record.set(data.CellInfo[j].column.toLowerCase(),data.CellInfo[j].value);
								}else if(cellValue==undefined && cellValue2==undefined && cellValue3!=undefined){
									record.set(data.CellInfo[j].column,data.CellInfo[j].value);
								}
							}
							record.commit();
							break;
						}
					}
				}
				//更新实时表和实时曲线
				if(Ext.getCmp("PumpRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
					var wellName  = Ext.getCmp("PumpRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
					if(wellName==data.wellName && data.CellInfo.length>0){
						var tabPanel = Ext.getCmp("PumpRealTimeMonitoringCurveAndTableTabPanel");
		        		var activeId = tabPanel.getActiveTab().id;
		        		if(activeId=="PumpRealTimeMonitoringCurveTabPanel_Id"){
		        			//更新实时曲线
		        			var container=$('#pumpRealTimeMonitoringCurveContainer');
		        			if(container!=undefined && container.length>0){
		        				var containerChildren=container[0].children;
		        				if(containerChildren!=undefined && containerChildren.length>0){
		        					for(var i=0;i<containerChildren.length;i++){
		        						var chart = $("#"+containerChildren[i].id).highcharts(); 
		    							if(isNotVal(chart)){
	    									var series=chart.series[0];
	    									for(var j=0;j<data.CellInfo.length;j++){
	    										if(series.name.split("(")[0]==data.CellInfo[j].columnName){
	    											var translation=false;//添加点动作  是否平移
	    											if(series.data.length>100){
	    												translation=true;
	    											}
	    											series.addPoint([Date.parse(data.acqTime.replace(/-/g, '/')), parseFloat(data.CellInfo[j].rawValue)], true, translation);
	    											break;
	    										}
	    									}
		    							}
		        					}
		        				}
		        			}
		        		}else if(activeId=="PumpRealTimeMonitoringTableTabPanel_Id"){
		        			//更新实时数据表
		        			if(isNotVal(pumpDeviceRealTimeMonitoringDataHandsontableHelper) &&  isNotVal(pumpDeviceRealTimeMonitoringDataHandsontableHelper.hot)){
		        				pumpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo=data.CellInfo;
								pumpDeviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(data.totalRoot);
		        			}
		        		}
					}
				}
			}
		}
	}else if(data.functionCode.toUpperCase()=="pumpDeviceRealTimeMonitoringStatusData".toUpperCase()){//接收到推送的泵设备通信数据
		if(activeId.toUpperCase()=="DeviceRealTimeMonitoring".toUpperCase()){
			var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="PumpRealTimeMonitoringInfoPanel_Id" && isExist(orgIdArr,data.orgId+"")>0){
				//更新通信状态统计
				getDeviceCommStatusTotal();
				var gridPanel = Ext.getCmp("PumpRealTimeMonitoringListGridPanel_Id");
				if(isNotVal(gridPanel)){
					var store = gridPanel.getStore();
					//更新概览表
					var haveDevice=false;
					var commStatus  = Ext.getCmp("PumpRealTimeMonitoringStatSelectCommStatus_Id").getValue();
					for(var i=0;i<store.getCount();i++){
						var record=store.getAt(i);
						if(record.data.wellName==data.wellName){
							haveDevice=true;
							if((data.commStatus==1&&commStatus=='离线') || (data.commStatus==0&&commStatus=='在线') ){
								store.loadPage(1);
							}else{
								record.set("commStatusName",(data.commStatus==1?"上线":"离线"));
								record.set("commStatus",data.commStatus);
//								record.set("commAlarmLevel",(data.commStatus==1?0:100));
								record.set("commAlarmLevel",data.commAlarmLevel);
								record.set("acqTime",data.acqTime);
								record.commit();
							}
							
							break;
						}
					}
					if((!haveDevice)
							&&(commStatus==''
									|| (data.commStatus==1&&commStatus=='在线') 
									|| (data.commStatus==0&&commStatus=='离线') 
								)
						){
						store.loadPage(1);
					}
					//更新实时表
					var tabPanel = Ext.getCmp("PumpRealTimeMonitoringCurveAndTableTabPanel");
            		var activeId = tabPanel.getActiveTab().id;
            		if(activeId=="PumpRealTimeMonitoringTableTabPanel_Id"){
            			if(isNotVal(pumpDeviceRealTimeMonitoringDataHandsontableHelper) &&  isNotVal(pumpDeviceRealTimeMonitoringDataHandsontableHelper.hot)){
    						var wellName  = Ext.getCmp("PumpRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
    						if(wellName==data.wellName){
    							var value=data.wellName+":"+data.acqTime+" "+(data.commStatus==1?"在线":"离线");
    							pumpDeviceRealTimeMonitoringDataHandsontableHelper.hot.setDataAtCell(0, 0, value);
    						}
    					}
            		}
				}
			}
		}
	}if(data.functionCode.toUpperCase()=="pipelineDeviceRealTimeMonitoringData".toUpperCase()){//接收到推送的管设备实时监控数据
		if(activeId.toUpperCase()=="DeviceRealTimeMonitoring".toUpperCase()){
			var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="PipelineRealTimeMonitoringInfoPanel_Id"){
				//更新通信状态统计
				getDeviceCommStatusTotal();
				//更新设备概览列表
				var gridPanel = Ext.getCmp("PipelineRealTimeMonitoringListGridPanel_Id");
				if(isNotVal(gridPanel)){
					var store = gridPanel.getStore();
					for(var i=0;i<store.getCount();i++){
						var record=store.getAt(i);
						if(record.data.wellName==data.wellName){
							record.set("commStatusName","在线");
							record.set("commStatus",1);
							record.set("commAlarmLevel",0);
							record.set("acqTime",data.acqTime);
							for(var j=0;j<data.CellInfo.length;j++){
								var cellValue=record.get(data.CellInfo[j].column.toUpperCase());
								var cellValue2=record.get(data.CellInfo[j].column.toLowerCase());
								var cellValue3=record.get(data.CellInfo[j].column);
								if(cellValue!=undefined){
									record.set(data.CellInfo[j].column.toUpperCase(),data.CellInfo[j].value);
								}else if(cellValue2!=undefined){
									record.set(data.CellInfo[j].column.toLowerCase(),data.CellInfo[j].value);
								}else if(cellValue==undefined && cellValue2==undefined && cellValue3!=undefined){
									record.set(data.CellInfo[j].column,data.CellInfo[j].value);
								}
							}
							record.commit();
							break;
						}
					}
				}
				//更新实时表和实时曲线
				if(Ext.getCmp("PipelineRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection().length>0){
					var wellName  = Ext.getCmp("PipelineRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
					if(wellName==data.wellName && data.CellInfo.length>0){
						var tabPanel = Ext.getCmp("PipelineRealTimeMonitoringCurveAndTableTabPanel");
		        		var activeId = tabPanel.getActiveTab().id;
		        		if(activeId=="PipelineRealTimeMonitoringCurveTabPanel_Id"){
		        			//更新实时曲线
		        			var container=$('#pipelineRealTimeMonitoringCurveContainer');
		        			if(container!=undefined && container.length>0){
		        				var containerChildren=container[0].children;
		        				if(containerChildren!=undefined && containerChildren.length>0){
		        					for(var i=0;i<containerChildren.length;i++){
		        						var chart = $("#"+containerChildren[i].id).highcharts(); 
		    							if(isNotVal(chart)){
	    									var series=chart.series[0];
	    									for(var j=0;j<data.CellInfo.length;j++){
	    										if(series.name.split("(")[0]==data.CellInfo[j].columnName){
	    											var translation=false;//添加点动作  是否平移
	    											if(series.data.length>100){
	    												translation=true;
	    											}
	    											series.addPoint([Date.parse(data.acqTime.replace(/-/g, '/')), parseFloat(data.CellInfo[j].rawValue)], true, translation);
	    											break;
	    										}
	    									}
		    							}
		        					}
		        				}
		        			}
		        		}else if(activeId=="PipelineRealTimeMonitoringTableTabPanel_Id"){
		        			//更新实时数据表
		        			if(isNotVal(pipelineDeviceRealTimeMonitoringDataHandsontableHelper) &&  isNotVal(pipelineDeviceRealTimeMonitoringDataHandsontableHelper.hot)){
		        				pipelineDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo=data.CellInfo;
		        				pipelineDeviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(data.totalRoot);
		        			}
		        		}
					}
				}
			}
		}
	}else if(data.functionCode.toUpperCase()=="pipelineDeviceRealTimeMonitoringStatusData".toUpperCase()){//接收到推送的管设备通信数据
		if(activeId.toUpperCase()=="DeviceRealTimeMonitoring".toUpperCase()){
			var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
			var activeId = tabPanel.getActiveTab().id;
			if(activeId=="PipelineRealTimeMonitoringInfoPanel_Id"  && isExist(orgIdArr,data.orgId+"")>0 ){
				//更新通信状态统计
				getDeviceCommStatusTotal();
				var gridPanel = Ext.getCmp("PipelineRealTimeMonitoringListGridPanel_Id");
				if(isNotVal(gridPanel)){
					var store = gridPanel.getStore();
					//更新概览表
					var haveDevice=false;
					var commStatus  = Ext.getCmp("PipelineRealTimeMonitoringStatSelectCommStatus_Id").getValue();
					for(var i=0;i<store.getCount();i++){
						var record=store.getAt(i);
						if(record.data.wellName==data.wellName){
							haveDevice=true;
							if((data.commStatus==1&&commStatus=='离线') || (data.commStatus==0&&commStatus=='在线') ){
								store.loadPage(1);
							}else{
								record.set("commStatusName",(data.commStatus==1?"上线":"离线"));
								record.set("commStatus",data.commStatus);
//								record.set("commAlarmLevel",(data.commStatus==1?0:100));
								record.set("commAlarmLevel",data.commAlarmLevel);
								record.set("acqTime",data.acqTime);
								record.commit();
							}
							
							break;
						}
					}
					if((!haveDevice)
							&&(commStatus==''
									|| (data.commStatus==1&&commStatus=='在线') 
									|| (data.commStatus==0&&commStatus=='离线') 
								)
						){
						store.loadPage(1);
					}
					//更新实时表
					var tabPanel = Ext.getCmp("PipelineRealTimeMonitoringCurveAndTableTabPanel");
            		var activeId = tabPanel.getActiveTab().id;
            		if(activeId=="PipelineRealTimeMonitoringTableTabPanel_Id"){
            			if(isNotVal(pipelineDeviceRealTimeMonitoringDataHandsontableHelper) &&  isNotVal(pipelineDeviceRealTimeMonitoringDataHandsontableHelper.hot)){
    						var wellName  = Ext.getCmp("PipelineRealTimeMonitoringListGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
    						if(wellName==data.wellName){
    							var value=data.wellName+":"+data.acqTime+" "+(data.commStatus==1?"在线":"离线");
    							pipelineDeviceRealTimeMonitoringDataHandsontableHelper.hot.setDataAtCell(0, 0, value);
    						}
    					}
            		}
				}
			}
		}
	}else if(data.functionCode.toUpperCase()=="ResourceMonitoringData".toUpperCase()){//接收到资源监测数据
		if(activeId.toUpperCase()=="DeviceRealTimeMonitoring".toUpperCase()){
			if(data.cpuUsedPercentAlarmLevel==1){
				Ext.getCmp("CPUUsedPercentLabel_id").setText("<font color=#F09614 >CPU:"+data.cpuUsedPercent+"</font>");
			}else if(data.cpuUsedPercentAlarmLevel==2){
				Ext.getCmp("CPUUsedPercentLabel_id").setText("<font color=#DC2828 >CPU:"+data.cpuUsedPercent+"</font>");
			}else{
				Ext.getCmp("CPUUsedPercentLabel_id").setText("CPU:"+data.cpuUsedPercent);
			}
			
			if(data.memUsedPercentAlarmLevel==1){
				Ext.getCmp("memUsedPercentLabel_id").setText("<font color=#F09614 >内存:"+data.memUsedPercent+"</font>");
			}else if(data.memUsedPercentAlarmLevel==2){
				Ext.getCmp("memUsedPercentLabel_id").setText("<font color=#DC2828 >内存:"+data.memUsedPercent+"</font>");
			}else{
				Ext.getCmp("memUsedPercentLabel_id").setText("内存:"+data.memUsedPercent);
			}
			
			if(data.tableSpaceUsedPercentAlarmLevel==1){
				Ext.getCmp("tableSpaceSizeProbeLabel_id").setText("<font color=#F09614 >表空间:"+data.tableSpaceUsedPercent+"</font>");
			}else if(data.tableSpaceUsedPercentAlarmLevel==2){
				Ext.getCmp("tableSpaceSizeProbeLabel_id").setText("<font color=#DC2828 >表空间:"+data.tableSpaceUsedPercent+"</font>");
			}else{
				Ext.getCmp("tableSpaceSizeProbeLabel_id").setText("表空间:"+data.tableSpaceUsedPercent);
			}
			
			if(data.adRunStatus=="运行"){
				Ext.getCmp("adRunStatusProbeLabel_id").setIconCls("dtgreen");
			}else{
				Ext.getCmp("adRunStatusProbeLabel_id").setIconCls("dtyellow");
			}
			Ext.getCmp("adRunStatusProbeLabel_id").setText("驱动 v"+data.adVersion);
			
			if(data.adLicenseSign){
				Ext.getCmp("adLicenseStatusProbeLabel_id").setText("<font color=#DC2828 >License超限:"+data.deviceAmount+"/"+data.adLicense+"</font>");
				Ext.getCmp("adLicenseStatusProbeLabel_id").show();
			}else{
				Ext.getCmp("adLicenseStatusProbeLabel_id").setText("");
				Ext.getCmp("adLicenseStatusProbeLabel_id").hide();
			}
		}
	}
}
function websocketOnOpen() {
//	alert("WebSocket连接成功");
}
function websocketOnError() {
//	alert("WebSocket连接发生错误");
}
function websocketOnClose() {
//	alert("WebSocket连接关闭");
}

function websocketClose(websocket) {
	if(websocket!=null){
		websocket.close();
	}
}

function getDeviceCommStatusTotal(){
	var orgId = Ext.getCmp('leftOrg_Id').getValue();
	var deviceType=0;
	var load=false;
	var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
	if(activeId=="PumpRealTimeMonitoringInfoPanel_Id"){
		deviceType=0;
		var statTabActiveId = Ext.getCmp("PumpRealTimeMonitoringStatTabPanel").getActiveTab().id;
		if(statTabActiveId=="PumpRealTimeMonitoringStatGraphPanel_Id"){
			load=true;
		}else if(newCard.id=="PumpRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
			load=false;
		}
		
	}else{
		deviceType=1;
		var statTabActiveId = Ext.getCmp("PipelineRealTimeMonitoringStatTabPanel").getActiveTab().id;
		if(statTabActiveId=="PipelineRealTimeMonitoringStatGraphPanel_Id"){
			load=true;
		}else if(newCard.id=="PipelineRealTimeMonitoringDeviceTypeStatGraphPanel_Id"){
			load=false;
		}
	}
	
	if(load){
		Ext.Ajax.request({
			method:'POST',
			url:context + '/realTimeMonitoringController/getDeviceRealTimeCommStatusStat',
			success:function(response) {
				var result =  Ext.JSON.decode(response.responseText);
				var all=result.all;
				var online=result.online;
				var offline=result.offline;
				var chart =null;
				if(deviceType===0){
					chart= $("#PumpRealTimeMonitoringStatGraphPanelPieDiv_Id").highcharts(); 
				}else if(deviceType===1){
					chart= $("#PipelineRealTimeMonitoringStatGraphPanelPieDiv_Id").highcharts(); 
				}
				
				if(isNotVal(chart)){
					var series=chart.series[0];
					var pieDataStr="[";
					pieDataStr+="['在线',"+online+"],";
					pieDataStr+="['离线',"+offline+"]";
					pieDataStr+="]";
					var pieData = Ext.JSON.decode(pieDataStr);
					series.setData(pieData);
				}
			},
			failure:function(){
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
			},
			params: {
				orgId:orgId,
				deviceType:deviceType
	        }
		});
	}
}

function fullscreen(){
	document.getElementById('passAndExitButton2').style.display='none';
	document.getElementById('passAndExitButton3').style.display='';
	var el = document.documentElement;
    var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen;
    if (typeof rfs != "undefined" && rfs) {
        rfs.call(el);
    } else if (typeof window.ActiveXObject != "undefined") {
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript != null) {
            wscript.SendKeys("{F11}");
        }
    }
}
function exitFullscreen(){
	document.getElementById('passAndExitButton3').style.display='none';
	document.getElementById('passAndExitButton2').style.display='';
	
    var elem=document;
    if(elem.webkitCancelFullScreen){
        elem.webkitCancelFullScreen();    
    }else if(elem.mozCancelFullScreen){
        elem.mozCancelFullScreen();
    }else if(elem.cancelFullScreen){
        elem.cancelFullScreen();
    }else if(elem.exitFullscreen){
        elem.exitFullscreen();
    }else{
        //浏览器不支持全屏API或已被禁用
    }
}

//重置密码
function resetPwdFn() {
    var showResetPwdWin = Ext.create("AP.view.user.SysUserEditPwdWin");
    showResetPwdWin.show();
    return false;
}

//帮助文档窗口
function showHelpDocumentWinFn() {
	var tabPanel = Ext.getCmp("frame_center_ids");
	var getTabId = tabPanel.getComponent("HelpDocPanel");
	if(!getTabId){
		tabPanel.add(Ext.create("AP.view.help.HelpDocPanel", {
            id: 'HelpDocPanel',
            closable: true,
            iconCls: 'help',
            closeAction: 'destroy',
            title: '帮助',
            listeners: {
                afterrender: function () {
                },
                delay: 150
            }
        })).show();
		
		Ext.Ajax.request({
    		method:'POST',
    		url:context + '/helpDocController/getHelpDocHtml',
    		success:function(response) {
    			var p =Ext.getCmp("HelpDocPanel_Id");
    			p.body.update(response.responseText);
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
            }
    	});
	}
	tabPanel.setActiveTab("HelpDocPanel");
    return false;
}

function mOver(obj) {
    var obj_ = obj;
    obj_.style.color = 'blue';
}

function mOut(obj) {
    var obj_ = obj;
    obj_.style.color = '';
}

function changeTabId(val, id) {
    var data_ = val.split("_");
    var tabId_ = "";
    Ext.Array.each(data_,
        function (name, index, countriesItSelf) {
            var str_ = name;
            if (index == countriesItSelf.length - 1) {
                if (id != "") {
                    tabId_ += id;
                } else {
                    tabId_ += str_;
                }
            } else {
                tabId_ += str_ + "_";
            }

        });
    return tabId_;
}