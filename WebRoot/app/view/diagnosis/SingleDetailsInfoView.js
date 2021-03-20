var probeWebsocketClient = null;
Ext.define("AP.view.diagnosis.SingleDetailsInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.DiagnosisSingleDetailsInfoView', //别名
    layout: 'fit',
    iframe: true,
    border: false,
    referenceHolder: true,
    initComponent: function () {
    	var RPCSingleDetailsInfoView = Ext.create('AP.view.diagnosis.RPCSingleDetailsInfoView');
        var PCPSingleDetailsInfoView = Ext.create('AP.view.diagnosis.PCPSingleDetailsInfoView');
        Ext.apply(this, {
            items: [{
                xtype: 'tabpanel',
                id:"ProductionWellRealtimeAnalisisPanel",
                activeTab: 0,
                border: false,
                tabPosition: 'bottom',
                tabBar:{
                	items: [{
                        xtype: 'tbfill'
                    },{
                		xtype: 'button',
                		width:180,
                        id:"CPUUsedPercentLabel_id",
                        text: 'CPU利用率:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("CPU利用率(%)");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("cpuUsedPercent");
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                		width:150,
                        id:"memUsedPercentLabel_id",
                        text: '内存使用率:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("内存使用率(%)");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("memUsedPercent");
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                		width:150,
                        id:"tableSpaceSizeProbeLabel_id",
                        text: '表空间大小:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("表空间大小(Mb)");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("tableSpaceSize");
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                		width:150,
                        id:"appRunStatusProbeLabel_id",
                        text: 'SDK运行状态:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("SDK运行状态");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("appRunStatus");
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	},{
                		xtype: 'button',
                		width:150,
                        id:"appVersionProbeLabel_id",
                        text: 'SDK版本:',
                        handler: function (v, o) {
                        	Ext.getCmp('DiagnosisAnalysisCurveItem_Id').setValue("SDK版本");
                            Ext.getCmp('DiagnosisAnalysisCurveItemCode_Id').setValue("appVersion");
                        	var ResourceProbeHistoryCurveWindow=Ext.create("AP.view.diagnosis.ResourceProbeHistoryCurveWindow", {
            				    html:'<div id="ResourceProbeHistoryCurve_DivId" style="width:100%;height:100%;"></div>'
                        	});
                        	ResourceProbeHistoryCurveWindow.show();
                        }
                	}]
                },
                items: [{
                    title: cosog.string.pumpUnit,
                    id:'RPCSingleDetailsInfoPanel_Id',
                    layout: "fit",
                    border: false,
                    items: RPCSingleDetailsInfoView
                }, {
                    title: cosog.string.screwPump,
                    id:'PCPSingleDetailsInfoPanel_Id',
                    layout: "fit",
                    border: false,
                    hidden: pcpHidden,
                    items:PCPSingleDetailsInfoView
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	if(newCard.id=="RPCSingleDetailsInfoPanel_Id"){
                    		loadFSDiagramAnalysisSingleStatData();
                    	}else if(newCard.id=="PCPSingleDetailsInfoPanel_Id"){
                    		loadPCPRPMAnalysisSingleStatData();
                    	}
                    },beforeclose: function ( panel, eOpts) {
//        				alert("关闭");
        				probeWebsocketClose(probeWebsocketClient);
        			},
        			afterrender: function ( panel, eOpts) {
        				var curWwwPath=window.document.location.href;
        				var pathName=window.document.location.pathname;
        				var pos=curWwwPath.indexOf(pathName);
        				var localhostPaht=curWwwPath.substring(0,pos);
        				var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
        				var baseRoot = localhostPaht+projectName;
        				var baseUrl=baseRoot.replace("https","ws").replace("http","ws");
        				
        				var moduleCode = Ext.getCmp("frame_center_ids").getActiveTab().id;
        				if ('WebSocket' in window) {
        					probeWebsocketClient = new ReconnectingWebSocket(baseUrl+"/websocket/socketServer?module_Code="+moduleCode);
        					probeWebsocketClient.debug = true;
        					
        					probeWebsocketClient.reconnectInterval = 1000;
        					probeWebsocketClient.timeoutInterval = 2000;
        					
        					probeWebsocketClient.maxReconnectInterval = 30000;
        					
        					probeWebsocketClient.reconnectDecay=1.5;
        					
        					probeWebsocketClient.automaticOpen = true;
        					
//        					probeWebsocketClient.maxReconnectAttempts = 5;
        					
        					
        				}
        				else if ('MozWebSocket' in window) {
        					probeWebsocketClient = new MozWebSocket(baseUrl+"/websocket/socketServer?module_Code="+moduleCode);
        				}else {
        					probeWebsocketClient = new SockJS(baseRoot+"/sockjs/socketServer?module_Code="+moduleCode);
        				}
        				probeWebsocketClient.onopen = probeWebsocketOnOpen;
        				probeWebsocketClient.onmessage = probeWebsocketOnMessage;
        				probeWebsocketClient.onerror = probeWebsocketOnError;
        				probeWebsocketClient.onclose = probeWebsocketOnClose;
        			}
                }
             }]
        });
        this.callParent(arguments);
    }
});

function probeWebsocketOnOpen(openEvt) {
//  alert(openEvt.Data);
}

function probeWebsocketOnMessage(evt) {
	var activeId = Ext.getCmp("frame_center_ids").getActiveTab().id;
	var data=Ext.JSON.decode(evt.data);
	if (activeId === "FSDiagramAnalysis_FSDiagramAnalysisSingleDetails") {
		Ext.getCmp("CPUUsedPercentLabel_id").setText("CPU利用率:"+data.cpuUsedPercent);
		Ext.getCmp("memUsedPercentLabel_id").setText("内存使用率:"+data.memUsedPercent);
		Ext.getCmp("tableSpaceSizeProbeLabel_id").setText("表空间大小:"+data.tableSpaceSize);
		Ext.getCmp("appRunStatusProbeLabel_id").setText("SDK运行状态:"+data.appRunStatus);
		Ext.getCmp("appVersionProbeLabel_id").setText("SDK版本:"+data.appVersion);
	}
}
function probeWebsocketOnOpen() {
//	alert("WebSocket连接成功");
}
function probeWebsocketOnError() {
//	alert("WebSocket连接发生错误");
}
function probeWebsocketOnClose() {
//	alert("WebSocket连接关闭");
}

function probeWebsocketClose(websocket) {
	if(websocket!=null){
		websocket.close();
	}
}