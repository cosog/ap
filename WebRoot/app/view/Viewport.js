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
            bodyStyle: {
                'z-index': 10
            },
            html: '<div id="bannerDiv"><img id="bannerLogoImg" ' + (showLogo ? '' : 'style="display:none;"') + ' src="' + bannerLogoImg + '?timestamp='+oemStaticResourceTimestamp+'" /><span id="bannerTitle">' + oem.title + '</span>' +
                "<div id='bannerToolbar'><a href='#' id='banner_exit' onclick='userLoginOut()'><span id='banner_exit_text'>退出</span></a></div>" +
                "<div id='bannerToolbar'><a href='#' id='banner_help' onclick='showHelpDocumentWinFn()'><span id='banner_help_text'>帮助</span></a></div>" +
                "</div>"
   }, {
            id: 'center_ids',
            region: 'center',
            border: false,
            split: true,
            bodyStyle: {
                'z-index': 1
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
                        tabchange: function (tabPanel, newCard, oldCard, obj) {
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
   }, {
            region: 'west',
            border: false,
            layout: 'border',
            collapsible: true,
            split: true,
            flex: 0.11,
            items: [{
                region: 'south',
                height: '50%',
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
       }, {
                id: 'MainModuleShow_Id',
                region: 'center',
                split: false,
                height: '50%',
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
            hidden: true,
            bodyStyle: 'background-color:#FBFBFB;',
            html: "<div id=\"footer\">" + oem.copy + "&nbsp;<a href='" + oem.linkaddress + "' target='_blank'>" + oem.linkshow + "</a> " + "</div>",
            height: 30
   }],
    listeners: {
        afterrender: function (panel, eOpts) {
            //			alert(user_);
            initBannerDisplayInformation();
            var baseUrl = getBaseUrl().replace("https", "ws").replace("http", "ws");
            var moduleCode = "ApWebSocketClient_" + user_;
            if ('WebSocket' in window) {
                //				websocketClient = new ReconnectingWebSocket(baseUrl+"/websocket/socketServer?module_Code="+moduleCode);
                websocketClient = new ReconnectingWebSocket(baseUrl + "/websocketServer/" + moduleCode);
                websocketClient.debug = true;
                websocketClient.reconnectInterval = 1000;
                websocketClient.timeoutInterval = 2000;
                websocketClient.maxReconnectInterval = 30000;
                websocketClient.reconnectDecay = 1.5;
                websocketClient.automaticOpen = true;
                //				websocketClient.maxReconnectAttempts = 5;
            } else if ('MozWebSocket' in window) {
                //				websocketClient = new MozWebSocket(baseUrl+"/websocket/socketServer?module_Code="+moduleCode);
                websocketClient = new MozWebSocket(baseUrl + "/websocketServer/" + moduleCode);
            } else {
                //				websocketClient = new SockJS(getBaseUrl()+"/sockjs/socketServer?module_Code="+moduleCode);
                websocketClient = new SockJS(getBaseUrl() + "/websocketServer/" + moduleCode);
            }
            websocketClient.onopen = websocketOnOpen;
            websocketClient.onmessage = websocketOnMessage;
            websocketClient.onerror = websocketOnError;
            websocketClient.onclose = websocketOnClose;

        },
        beforeclose: function (panel, eOpts) {
            websocketClose(websocketClient);
        }
    }
});

function websocketOnOpen(openEvt) {
    //  alert(openEvt.Data);
}

function websocketOnMessage(evt) {
    var activeId = Ext.getCmp("frame_center_ids").getActiveTab().id;
    var receiveData = evt.data;
    if (evt.data.indexOf("}{") >= 0) {
        var dataStr = evt.data.replace("}{", "}@@@@{");
        receiveData = dataStr.split("@@@@")[0];
    }
    var data = Ext.JSON.decode(receiveData);
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var orgIdArr = leftOrg_Id.split(",");
    if (data.functionCode.toUpperCase() == "adExitAndDeviceOffline".toUpperCase()) { //ad退出，所有设备离线
        if (activeId.toUpperCase() == "DeviceRealTimeMonitoring".toUpperCase()) {
            var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
            var activeId = tabPanel.getActiveTab().id;
            if (activeId == "RPCRealTimeMonitoringInfoPanel_Id") {
                var statTabActiveId = Ext.getCmp("RPCRealTimeMonitoringStatTabPanel").getActiveTab().id;
                if (statTabActiveId == "RPCRealTimeMonitoringStatGraphPanel_Id") {
                    loadAndInitCommStatusStat(true);
                } else if (statTabActiveId == "RPCRealTimeMonitoringDeviceTypeStatGraphPanel_Id") {
                    loadAndInitDeviceTypeStat(true);
                }
                Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setValue('');
                Ext.getCmp('RealTimeMonitoringRPCDeviceListComb_Id').setRawValue('');
                var gridPanel = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
                if (isNotVal(gridPanel)) {
                    gridPanel.getSelectionModel().deselectAll(true);
                    gridPanel.getStore().load();
                }
            } else if (activeId == "PCPRealTimeMonitoringInfoPanel_Id") {
                var statTabActiveId = Ext.getCmp("PCPRealTimeMonitoringStatTabPanel").getActiveTab().id;
                if (statTabActiveId == "PCPRealTimeMonitoringStatGraphPanel_Id") {
                    loadAndInitCommStatusStat(true);
                } else if (statTabActiveId == "PCPRealTimeMonitoringDeviceTypeStatGraphPanel_Id") {
                    loadAndInitDeviceTypeStat(true);
                }
                Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').setValue('');
                Ext.getCmp('RealTimeMonitoringPCPDeviceListComb_Id').setRawValue('');
                var gridPanel = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
                if (isNotVal(gridPanel)) {
                    gridPanel.getSelectionModel().deselectAll(true);
                    gridPanel.getStore().load();
                }
            }
        }
    } else if (data.functionCode.toUpperCase() == "rpcDeviceRealTimeMonitoringData".toUpperCase()) { //接收到推送的抽油机井实时监控数据
        if (activeId.toUpperCase() == "DeviceRealTimeMonitoring".toUpperCase()) {
            var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
            var activeId = tabPanel.getActiveTab().id;
            if (activeId == "RPCRealTimeMonitoringInfoPanel_Id") {
                //更新通信状态统计
                getDeviceCommStatusTotal();
                getDeviceRunStatusTotal();
                getDeviceFESDiagramResultTotal();

                var isSelectWell = false;
                var commStatusChange = false;
                //更新设备概览列表
                var RPCRealTimeMonitoringListGrid = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
                if (isNotVal(RPCRealTimeMonitoringListGrid)) {
                    if (RPCRealTimeMonitoringListGrid.getSelectionModel().getSelection().length > 0) {
                        var selectedWellId = RPCRealTimeMonitoringListGrid.getSelectionModel().getSelection()[0].data.id;
                        if (selectedWellId == data.wellId) {
                            isSelectWell = true;
                            if (RPCRealTimeMonitoringListGrid.getSelectionModel().getSelection()[0].data.commStatus == 0) {
                                commStatusChange = true;
                            }
                        }
                    }

                    var store = RPCRealTimeMonitoringListGrid.getStore();
                    for (var i = 0; i < store.getCount(); i++) {
                        var record = store.getAt(i);
                        if (record.data.id == data.wellId) {
                            record.set("commStatusName", "在线");
                            record.set("commStatus", 1);
                            record.set("acqTime", data.acqTime);
                            for (var j = 0; j < data.allItemInfo.length; j++) {
                                for (let item in record.data) {
                                    if (item.toUpperCase() == data.allItemInfo[j].column.toUpperCase()) {
                                        record.set(item, data.allItemInfo[j].value);
                                        if (item.toUpperCase() == "runStatusName".toUpperCase()) {
                                            record.set("runStatus", parseInt(data.allItemInfo[j].rawValue));
                                        }
                                        break;
                                    }
                                }
                            }
                            record.set("commAlarmLevel", data.commAlarmLevel);
                            record.set("runAlarmLevel", data.runAlarmLevel);
                            record.set("resultAlarmLevel", data.resultAlarmLevel);
                            record.commit();
                            break;
                        }
                    }
                }
                //更新实时表和实时曲线
                if (isSelectWell) {
                    var tabPanel = Ext.getCmp("RPCRealTimeMonitoringCurveAndTableTabPanel");
                    var activeId = tabPanel.getActiveTab().id;
                    if (activeId == "RPCRealTimeMonitoringCurveTabPanel_Id") {
                        //更新实时曲线
                        var container = $('#rpcRealTimeMonitoringCurveContainer');
                        if (container != undefined && container.length > 0) {
                            var containerChildren = container[0].children;
                            if (containerChildren != undefined && containerChildren.length > 0) {
                                for (var i = 0; i < containerChildren.length; i++) {
                                    var chart = $("#" + containerChildren[i].id).highcharts();
                                    if (isNotVal(chart)) {
                                        var series = chart.series[0];
                                        for (var j = 0; j < data.CellInfo.length; j++) {
                                            if (series.name.split("(")[0] == data.CellInfo[j].columnName) {
                                                var translation = false; //添加点动作  是否平移
                                                if (series.data.length > 100) {
                                                    translation = true;
                                                }
                                                series.addPoint([Date.parse(data.acqTime.replace(/-/g, '/')), parseFloat(data.CellInfo[j].rawValue)], true, translation);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (activeId == "RPCRealTimeMonitoringTableTabPanel_Id") {
                        //更新实时数据表
                        if (isNotVal(rpcDeviceRealTimeMonitoringDataHandsontableHelper) && isNotVal(rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot)) {
                            rpcDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo = data.CellInfo;
                            rpcDeviceRealTimeMonitoringDataHandsontableHelper.sourceData=data.totalRoot;
                            rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(data.totalRoot);
                        }
                    } else if (activeId == "RPCRealTimeMonitoringFSDiagramAnalysisTabPanel_Id") {
                        //井筒分析
                        showFSDiagramFromPumpcard(data.wellBoreChartsData, "FSDiagramAnalysisSingleWellboreDetailsDiv1_id"); // 调用画泵功图的函数
                        showRodPress(data.wellBoreChartsData, "FSDiagramAnalysisSingleWellboreDetailsDiv2_id"); // 调用画杆柱应力的函数
                        showPumpCard(data.wellBoreChartsData, "FSDiagramAnalysisSingleWellboreDetailsDiv3_id"); // 调用画泵功图的函数
                        showPumpEfficiency(data.wellBoreChartsData, "FSDiagramAnalysisSingleWellboreDetailsDiv4_id"); // 调用画泵效组成的函数
                    } else if (activeId == "RPCRealTimeMonitoringFSDiagramAnalysisSurfaceTabPanel_Id") {
                        //地面分析
                        var deltaRadius = parseFloat(data.surfaceChartsData.deltaRadius);
                        var expectedTorqueChartTitle = "扭矩曲线";
                        if (Math.abs(deltaRadius) > 0) {
                            if (deltaRadius > 0) {
                                expectedTorqueChartTitle = "外移" + deltaRadius + "cm" + expectedTorqueChartTitle;
                            } else {
                                expectedTorqueChartTitle = "內移" + Math.abs(deltaRadius) + "cm" + expectedTorqueChartTitle;
                            }
                        } else {
                            expectedTorqueChartTitle = "预期扭矩曲线";
                        }
                        showPSDiagram(data.surfaceChartsData, "FSDiagramAnalysisSingleSurfaceDetailsDiv1_id");
                        showASDiagram(data.surfaceChartsData, "FSDiagramAnalysisSingleSurfaceDetailsDiv3_id");
                        showBalanceAnalysisCurveChart(data.surfaceChartsData.crankAngle, data.surfaceChartsData.loadRorque, data.surfaceChartsData.crankTorque, data.surfaceChartsData.currentBalanceTorque, data.surfaceChartsData.currentNetTorque,
                            "目前扭矩曲线", data.surfaceChartsData.wellName + ' [' + data.surfaceChartsData.acqTime + ']', "FSDiagramAnalysisSingleSurfaceDetailsDiv2_id");
                        showBalanceAnalysisCurveChart(data.surfaceChartsData.crankAngle, data.surfaceChartsData.loadRorque, data.surfaceChartsData.crankTorque, data.surfaceChartsData.expectedBalanceTorque, data.surfaceChartsData.expectedNetTorque,
                            expectedTorqueChartTitle, data.surfaceChartsData.wellName + ' [' + data.surfaceChartsData.acqTime + ']', "FSDiagramAnalysisSingleSurfaceDetailsDiv4_id");
                        //	                	showBalanceAnalysisMotionCurveChart(data.surfaceChartsData.crankAngle,data.surfaceChartsData.positionCurveData,data.surfaceChartsData.polishrodV,data.surfaceChartsData.polishrodA,
                        //	                			"运动特性曲线",data.surfaceChartsData.wellName+' ['+data.surfaceChartsData.acqTime+']',"FSDiagramAnalysisSingleSurfaceDetailsDiv5_id",2);
                    }
                    if (commStatusChange) {
                        Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringControlAndInfoStore');
                    }
                }
            }
        } else if (activeId.toUpperCase() == "UpstreamAndDownstreamInteraction".toUpperCase()) {
            var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id");
            if (isNotVal(gridPanel)) {
                var store = gridPanel.getStore();
                //更新概览表
                for (var i = 0; i < store.getCount(); i++) {
                    var record = store.getAt(i);
                    if (record.data.id == data.wellId) {
                        record.set("commStatusName", "在线");
                        record.set("commStatus", 1);
                        record.commit();
                        var selectedWellId = parseInt(Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListSelectRow_Id").getValue());
                        if (selectedWellId == record.data.id) { //如果选中的是更新的井
                            if (parseInt(record.data.commStatus) == 0) {
                                Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
                            } else {
                                Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
                            }
                        }
                        break;
                    }
                }
            }
        }
    } else if (data.functionCode.toUpperCase() == "rpcDeviceRealTimeMonitoringStatusData".toUpperCase()) { //接收到推送的抽油机井通信数据
        if (activeId.toUpperCase() == "DeviceRealTimeMonitoring".toUpperCase()) {
            var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
            var activeId = tabPanel.getActiveTab().id;
            if (activeId == "RPCRealTimeMonitoringInfoPanel_Id" && isExist(orgIdArr, data.orgId + "") > 0) {
                //更新通信状态统计
                getDeviceCommStatusTotal();
                getDeviceRunStatusTotal();

                var isSelectWell = false;
                var commStatusChange = false;
                var gridPanel = Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id");
                if (isNotVal(gridPanel)) {
                    if (gridPanel.getSelectionModel().getSelection().length > 0) {
                        var selectedWellId = gridPanel.getSelectionModel().getSelection()[0].data.id;
                        if (selectedWellId == data.wellId) {
                            isSelectWell = true;
                            if (gridPanel.getSelectionModel().getSelection()[0].data.commStatus != data.commStatus) {
                                commStatusChange = true;
                            }
                        }
                    }

                    var store = gridPanel.getStore();
                    //更新概览表
                    var haveDevice = false;
                    var commStatus = Ext.getCmp("RPCRealTimeMonitoringStatSelectCommStatus_Id").getValue();
                    for (var i = 0; i < store.getCount(); i++) {
                        var record = store.getAt(i);
                        if (record.data.id == data.wellId) {
                            haveDevice = true;
                            if (commStatus != '' && commStatus != data.commStatusName) {
                                store.loadPage(1);
                            } else {
                                record.set("commStatusName", data.commStatusName);
                                record.set("commStatus", data.commStatus);
                                record.set("commTime", data.commTime);
                                record.set("commTimeEfficiency", data.commTimeEfficiency);
                                record.set("commRange", data.commRange);
                                record.set("commAlarmLevel", data.commAlarmLevel);
                                record.set("acqTime", data.acqTime);
                                record.commit();
                            }
                            break;
                        }
                    }
                    if ((!haveDevice) &&
                        (commStatus == '' || commStatus == data.commStatusName)
                    ) {
                        store.loadPage(1);
                    }
                    //更新实时表
                    if (isSelectWell) {
                        var tabPanel = Ext.getCmp("RPCRealTimeMonitoringCurveAndTableTabPanel");
                        var activeId = tabPanel.getActiveTab().id;
                        if (activeId == "RPCRealTimeMonitoringTableTabPanel_Id") {
                            if (isNotVal(rpcDeviceRealTimeMonitoringDataHandsontableHelper) && isNotVal(rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot)) {
                                var value = data.wellName + ":" + data.acqTime + " " + (data.commStatus > 0 ? "上线" : "离线");
                                rpcDeviceRealTimeMonitoringDataHandsontableHelper.hot.setDataAtCell(0, 0, value);
                            }
                        }
                        if (commStatusChange) {
                            Ext.create('AP.store.realTimeMonitoring.RPCRealTimeMonitoringControlAndInfoStore');
                        }
                    }
                }
            }
        } else if (activeId.toUpperCase() == "UpstreamAndDownstreamInteraction".toUpperCase()) {
            var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id");
            if (isNotVal(gridPanel)) {
                var store = gridPanel.getStore();
                //更新概览表
                for (var i = 0; i < store.getCount(); i++) {
                    var record = store.getAt(i);
                    if (record.data.id == data.wellId) {
                        record.set("commStatusName", data.commStatusName);
                        record.set("commStatus", data.commStatus);
                        record.commit();
                        var selectedWellId = parseInt(Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListSelectRow_Id").getValue());
                        if (selectedWellId == record.data.id) { //如果选中的是更新的井
                            if (parseInt(record.data.commStatus) == 0) {
                                Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
                            } else {
                                Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
                            }
                        }
                        break;
                    }
                }
            }
        }
    } else if (data.functionCode.toUpperCase() == "pcpDeviceRealTimeMonitoringData".toUpperCase()) { //接收到推送的螺杆泵井实时监控数据
        if (activeId.toUpperCase() == "DeviceRealTimeMonitoring".toUpperCase()) {
            var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
            var activeId = tabPanel.getActiveTab().id;
            if (activeId == "PCPRealTimeMonitoringInfoPanel_Id") {
                //更新通信状态统计
                getDeviceCommStatusTotal();
                getDeviceRunStatusTotal();
                var isSelectWell = false;
                var commStatusChange = false;
                //更新设备概览列表
                var gridPanel = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
                if (isNotVal(gridPanel)) {
                    if (gridPanel.getSelectionModel().getSelection().length > 0) {
                        var selectedWellId = gridPanel.getSelectionModel().getSelection()[0].data.id;
                        if (selectedWellId == data.wellId) {
                            isSelectWell = true;
                            if (gridPanel.getSelectionModel().getSelection()[0].data.commStatus == 0) {
                                commStatusChange = true;
                            }
                        }
                    }

                    var store = gridPanel.getStore();
                    for (var i = 0; i < store.getCount(); i++) {
                        var record = store.getAt(i);
                        if (record.data.id == data.wellId) {
                            record.set("commStatusName", "在线");
                            record.set("commStatus", 1);
                            record.set("acqTime", data.acqTime);
                            for (var j = 0; j < data.allItemInfo.length; j++) {
                                for (let item in record.data) {
                                    if (item.toUpperCase() == data.allItemInfo[j].column.toUpperCase()) {
                                        record.set(item, data.allItemInfo[j].value);
                                        if (item.toUpperCase() == "runStatusName".toUpperCase()) {
                                            record.set("runStatus", parseInt(data.allItemInfo[j].rawValue));
                                        }
                                        break;
                                    }
                                }
                            }
                            record.set("commAlarmLevel", data.commAlarmLevel);
                            record.set("runAlarmLevel", data.runAlarmLevel);
                            record.commit();
                            break;
                        }
                    }
                }
                //更新实时表和实时曲线
                if (isSelectWell) {
                    var tabPanel = Ext.getCmp("PCPRealTimeMonitoringCurveAndTableTabPanel");
                    var activeId = tabPanel.getActiveTab().id;
                    if (activeId == "PCPRealTimeMonitoringCurveTabPanel_Id") {
                        //更新实时曲线
                        var container = $('#pcpRealTimeMonitoringCurveContainer');
                        if (container != undefined && container.length > 0) {
                            var containerChildren = container[0].children;
                            if (containerChildren != undefined && containerChildren.length > 0) {
                                for (var i = 0; i < containerChildren.length; i++) {
                                    var chart = $("#" + containerChildren[i].id).highcharts();
                                    if (isNotVal(chart)) {
                                        var series = chart.series[0];
                                        for (var j = 0; j < data.CellInfo.length; j++) {
                                            if (series.name.split("(")[0] == data.CellInfo[j].columnName) {
                                                var translation = false; //添加点动作  是否平移
                                                if (series.data.length > 100) {
                                                    translation = true;
                                                }
                                                series.addPoint([Date.parse(data.acqTime.replace(/-/g, '/')), parseFloat(data.CellInfo[j].rawValue)], true, translation);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (activeId == "PCPRealTimeMonitoringTableTabPanel_Id") {
                        //更新实时数据表
                        if (isNotVal(pcpDeviceRealTimeMonitoringDataHandsontableHelper) && isNotVal(pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot)) {
                            pcpDeviceRealTimeMonitoringDataHandsontableHelper.CellInfo = data.CellInfo;
                            pcpDeviceRealTimeMonitoringDataHandsontableHelper.sourceData=data.totalRoot;
                            pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(data.totalRoot);
                        }
                    }
                    if (commStatusChange) {
                        Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringControlAndInfoStore');
                    }
                }
            }
        }
    } else if (data.functionCode.toUpperCase() == "pcpDeviceRealTimeMonitoringStatusData".toUpperCase()) { //接收到推送的螺杆泵井通信数据
        if (activeId.toUpperCase() == "DeviceRealTimeMonitoring".toUpperCase()) {
            var tabPanel = Ext.getCmp("RealTimeMonitoringTabPanel");
            var activeId = tabPanel.getActiveTab().id;
            if (activeId == "PCPRealTimeMonitoringInfoPanel_Id" && isExist(orgIdArr, data.orgId + "") > 0) {
                //更新通信状态统计
                getDeviceCommStatusTotal();
                getDeviceRunStatusTotal();

                var isSelectWell = false;
                var commStatusChange = false;
                var gridPanel = Ext.getCmp("PCPRealTimeMonitoringListGridPanel_Id");
                if (isNotVal(gridPanel)) {
                    if (gridPanel.getSelectionModel().getSelection().length > 0) {
                        var selectedWellId = gridPanel.getSelectionModel().getSelection()[0].data.id;
                        if (selectedWellId == data.wellId) {
                            isSelectWell = true;
                            if (gridPanel.getSelectionModel().getSelection()[0].data.commStatus != data.commStatus) {
                                commStatusChange = true;
                            }
                        }
                    }
                    var store = gridPanel.getStore();
                    //更新概览表
                    var haveDevice = false;
                    var commStatus = Ext.getCmp("PCPRealTimeMonitoringStatSelectCommStatus_Id").getValue();
                    for (var i = 0; i < store.getCount(); i++) {
                        var record = store.getAt(i);
                        if (record.data.id == data.wellId) {
                            haveDevice = true;
                            if (commStatus != '' && commStatus != data.commStatusName) {
                                store.loadPage(1);
                            } else {
                                record.set("commStatusName", data.commStatusName);
                                record.set("commStatus", data.commStatus);
                                record.set("commTime", data.commTime);
                                record.set("commTimeEfficiency", data.commTimeEfficiency);
                                record.set("commRange", data.commRange);
                                record.set("commAlarmLevel", data.commAlarmLevel);
                                record.set("acqTime", data.acqTime);
                                record.commit();
                            }
                            break;
                        }
                    }
                    if ((!haveDevice) &&
                        (commStatus == '' || commStatus == data.commStatusName)
                    ) {
                        store.loadPage(1);
                    }
                    //更新实时表
                    if (isSelectWell) {
                        var tabPanel = Ext.getCmp("PCPRealTimeMonitoringCurveAndTableTabPanel");
                        var activeId = tabPanel.getActiveTab().id;
                        if (activeId == "PCPRealTimeMonitoringTableTabPanel_Id") {
                            if (isNotVal(pcpDeviceRealTimeMonitoringDataHandsontableHelper) && isNotVal(pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot)) {
                                var value = data.wellName + ":" + data.acqTime + " " + (data.commStatus > 0 ? "上线" : "离线");
                                pcpDeviceRealTimeMonitoringDataHandsontableHelper.hot.setDataAtCell(0, 0, value);
                            }
                        }
                        if (commStatusChange) {
                            Ext.create('AP.store.realTimeMonitoring.PCPRealTimeMonitoringControlAndInfoStore');
                        }
                    }
                }
            }
        }
    } else if (data.functionCode.toUpperCase() == "ResourceMonitoringData".toUpperCase()) { //接收到资源监测数据
        if (activeId.toUpperCase() == "DeviceRealTimeMonitoring".toUpperCase()) {
            if (data.cpuUsedPercentAlarmLevel == 1) {
                Ext.getCmp("CPUUsedPercentLabel_id").setText("<font color=#F09614 >CPU:" + data.cpuUsedPercent + "</font>");
            } else if (data.cpuUsedPercentAlarmLevel == 2) {
                Ext.getCmp("CPUUsedPercentLabel_id").setText("<font color=#DC2828 >CPU:" + data.cpuUsedPercent + "</font>");
            } else {
                Ext.getCmp("CPUUsedPercentLabel_id").setText("CPU:" + data.cpuUsedPercent);
            }

            if (data.memUsedPercentAlarmLevel == 1) {
                Ext.getCmp("memUsedPercentLabel_id").setText("<font color=#F09614 >内存:" + data.memUsedPercent + "</font>");
            } else if (data.memUsedPercentAlarmLevel == 2) {
                Ext.getCmp("memUsedPercentLabel_id").setText("<font color=#DC2828 >内存:" + data.memUsedPercent + "</font>");
            } else {
                Ext.getCmp("memUsedPercentLabel_id").setText("内存:" + data.memUsedPercent);
            }

            if (data.dbConnStatus == 1) {
                Ext.getCmp("tableSpaceSizeProbeLabel_id").setIconCls("dtgreen");
                if (data.tableSpaceUsedPercentAlarmLevel == 1) {
                    Ext.getCmp("tableSpaceSizeProbeLabel_id").setText("<font color=#F09614 >oracle表空间:" + data.tableSpaceUsedPercent + "</font>");
                } else if (data.tableSpaceUsedPercentAlarmLevel == 2) {
                    Ext.getCmp("tableSpaceSizeProbeLabel_id").setText("<font color=#DC2828 >oracle表空间:" + data.tableSpaceUsedPercent + "</font>");
                } else {
                    Ext.getCmp("tableSpaceSizeProbeLabel_id").setText("oracle表空间:" + data.tableSpaceUsedPercent);
                }
            } else {
                Ext.getCmp("tableSpaceSizeProbeLabel_id").setIconCls("dtyellow");
                Ext.getCmp("tableSpaceSizeProbeLabel_id").setText("oracle");
            }

            if (data.redisStatus == 1) {
                Ext.getCmp("redisRunStatusProbeLabel_id").setIconCls("dtgreen");
                Ext.getCmp("redisRunStatusProbeLabel_id").setText("redis v" + data.redisVersion);
            } else {
                Ext.getCmp("redisRunStatusProbeLabel_id").setIconCls("dtyellow");
                Ext.getCmp("redisRunStatusProbeLabel_id").setText("redis");
            }

            if (data.adRunStatus == 1) {
                Ext.getCmp("adRunStatusProbeLabel_id").setIconCls("dtgreen");
                Ext.getCmp("adRunStatusProbeLabel_id").setText("ad v" + data.adVersion);
            } else {
                Ext.getCmp("adRunStatusProbeLabel_id").setIconCls("dtyellow");
                Ext.getCmp("adRunStatusProbeLabel_id").setText("ad");
            }


            if (data.licenseSign) {
                Ext.getCmp("adLicenseStatusProbeLabel_id").setText("<font color=#DC2828 >License超限:" + data.deviceAmount + "/" + data.license + "</font>");
                Ext.getCmp("adLicenseStatusProbeLabel_id").show();
            } else {
                Ext.getCmp("adLicenseStatusProbeLabel_id").setText("");
                Ext.getCmp("adLicenseStatusProbeLabel_id").hide();
            }

            if (data.acRunStatus == 1) {
                Ext.getCmp("acRunStatusProbeLabel_id").setIconCls("dtgreen");
                Ext.getCmp("acRunStatusProbeLabel_id").setText("ac v" + data.acVersion);
            } else {
                Ext.getCmp("acRunStatusProbeLabel_id").setIconCls("dtyellow");
                Ext.getCmp("acRunStatusProbeLabel_id").setText("ac");
            }
        }
    } else if (data.functionCode.toUpperCase() == "rpcUpOnlineData".toUpperCase()) { //接收到推送RPC uponline 数据
        if (activeId.toUpperCase() == "UpstreamAndDownstreamInteraction".toUpperCase()) {
            var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id");
            if (isNotVal(gridPanel)) {
                var store = gridPanel.getStore();
                //更新概览表
                for (var i = 0; i < store.getCount(); i++) {
                    var record = store.getAt(i);
                    if (record.data.signinId == data.signinId) {
                        record.set("upCommStatusName", (data.commStatus == 1 ? "上线" : "离线"));
                        record.set("upCommStatus", data.commStatus);
                        record.commit();
                        var selectedWellId = parseInt(Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListSelectRow_Id").getValue());
                        if (selectedWellId == record.data.id) { //如果选中的是更新的井
                            if (parseInt(record.data.downCommStatus) == 0) {
                                Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
                            } else if (parseInt(record.data.downCommStatus) == 1) {
                                Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
                            }
                        }
                    }
                }
            }
        }
    } else if (data.functionCode.toUpperCase() == "rpcDownOnlineData".toUpperCase()) { //接收到推送RPC uponline 数据
        if (activeId.toUpperCase() == "UpstreamAndDownstreamInteraction".toUpperCase()) {
            var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id");
            if (isNotVal(gridPanel)) {
                var store = gridPanel.getStore();
                //更新概览表
                for (var i = 0; i < store.getCount(); i++) {
                    var record = store.getAt(i);
                    if (record.data.signinId == data.signinId) {
                        record.set("downCommStatusName", (data.commStatus == 1 ? "上线" : "离线"));
                        record.set("downCommStatus", data.commStatus);
                        record.commit();
                        var selectedWellId = parseInt(Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListSelectRow_Id").getValue());
                        if (selectedWellId == record.data.id) { //如果选中的是更新的井
                            if (parseInt(record.data.downCommStatus) == 0) {
                                Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
                            } else if (parseInt(record.data.downCommStatus) == 1) {
                                Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
                            }
                        }
                    }
                }
            }
        }
    } else if (data.functionCode.toUpperCase() == "adExitAndDeviceOffline_rpc".toUpperCase()) { //ad退出，所有设备离线
        if (activeId.toUpperCase() == "UpstreamAndDownstreamInteraction".toUpperCase()) {
            Ext.getCmp('UpstreamAndDownstreamInteractionRPCDeviceListComb_Id').setValue('');
            Ext.getCmp('UpstreamAndDownstreamInteractionRPCDeviceListComb_Id').setRawValue('');
            var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id");
            if (isNotVal(gridPanel)) {
                gridPanel.getSelectionModel().deselectAll(true);
                gridPanel.getStore().load();
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
    if (websocket != null) {
        websocket.close();
    }
}

function getDeviceFESDiagramResultTotal() {
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var deviceType = 0;
    var load = false;
    var statTabActiveId = Ext.getCmp("RPCRealTimeMonitoringStatTabPanel").getActiveTab().id;
    if (statTabActiveId == "RPCRealTimeMonitoringFESdiagramResultStatGraphPanel_Id") {
        load = true;
    }

    if (load) {
        Ext.Ajax.request({
            method: 'POST',
            url: context + '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData',
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText);
                Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));

                if (isNotVal($("#RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id"))) {
                    var chart = $("#RPCRealTimeMonitoringFESdiagramResultStatGraphPanelPieDiv_Id").highcharts();
                    if (isNotVal(chart)) {
                        var series = chart.series[0];
                        var pieDataStr = "[";
                        var datalist = result.totalRoot;
                        for (var i = 0; i < datalist.length; i++) {
                            pieDataStr += "['" + datalist[i].item + "'," + datalist[i].count + "],";
                        }
                        if (stringEndWith(pieDataStr, ",")) {
                            pieDataStr = pieDataStr.substring(0, pieDataStr.length - 1);
                        }
                        pieDataStr += "]";
                        var pieData = Ext.JSON.decode(pieDataStr);
                        series.setData(pieData);
                    }
                }


            },
            failure: function () {
                Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
            },
            params: {
                orgId: orgId,
                deviceType: deviceType
            }
        });
    }
}

function getDeviceCommStatusTotal() {
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var deviceType = 0;
    var load = false;
    var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
    if (activeId == "RPCRealTimeMonitoringInfoPanel_Id") {
        deviceType = 0;
        var statTabActiveId = Ext.getCmp("RPCRealTimeMonitoringStatTabPanel").getActiveTab().id;
        if (statTabActiveId == "RPCRealTimeMonitoringStatGraphPanel_Id") {
            load = true;
        } else if (statTabActiveId == "RPCRealTimeMonitoringDeviceTypeStatGraphPanel_Id") {
            load = false;
        }

    } else {
        deviceType = 1;
        var statTabActiveId = Ext.getCmp("PCPRealTimeMonitoringStatTabPanel").getActiveTab().id;
        if (statTabActiveId == "PCPRealTimeMonitoringStatGraphPanel_Id") {
            load = true;
        } else if (statTabActiveId == "PCPRealTimeMonitoringDeviceTypeStatGraphPanel_Id") {
            load = false;
        }
    }

    if (load) {
        Ext.Ajax.request({
            method: 'POST',
            url: context + '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData',
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText);
                var all = result.all;
                var online = result.online;
                var goOnline = result.goOnline;
                var offline = result.offline;
                var chart = null;
                if (deviceType === 0) {
                    chart = $("#RPCRealTimeMonitoringStatGraphPanelPieDiv_Id").highcharts();
                } else if (deviceType === 1) {
                    chart = $("#PCPRealTimeMonitoringStatGraphPanelPieDiv_Id").highcharts();
                }

                if (isNotVal(chart)) {
                    var series = chart.series[0];
                    var colors = [];
                    var alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
                    var pieData = [];
                    var datalist = result.totalRoot;
                    for (var i = 0; i < datalist.length; i++) {
                        if (datalist[i].itemCode != 'all') {
                            if (datalist[i].count > 0) {
                                var singleData = {};
                                singleData.name = datalist[i].item;
                                singleData.y = datalist[i].count;

                                if (datalist[i].itemCode == 'online') {
                                    singleData.color = '#' + alarmShowStyle.Comm.online.Color;
                                } else if (datalist[i].itemCode == 'goOnline') {
                                    singleData.color = '#' + alarmShowStyle.Comm.goOnline.Color;
                                } else if (datalist[i].itemCode == 'offline') {
                                    singleData.color = '#' + alarmShowStyle.Comm.offline.Color;
                                }
                                pieData.push(singleData);
                            }
                        }
                    }
                    series.setData(pieData);
                }
            },
            failure: function () {
                Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
            },
            params: {
                orgId: orgId,
                deviceType: deviceType
            }
        });
    }
}

function getDeviceRunStatusTotal() {
    var orgId = Ext.getCmp('leftOrg_Id').getValue();
    var deviceType = 0;
    var load = false;
    var activeId = Ext.getCmp("RealTimeMonitoringTabPanel").getActiveTab().id;
    if (activeId == "RPCRealTimeMonitoringInfoPanel_Id") {
        deviceType = 0;
        var statTabActiveId = Ext.getCmp("RPCRealTimeMonitoringStatTabPanel").getActiveTab().id;
        if (statTabActiveId == "RPCRealTimeMonitoringRunStatusStatGraphPanel_Id") {
            load = true;
        }

    } else {
        deviceType = 1;
        var statTabActiveId = Ext.getCmp("PCPRealTimeMonitoringStatTabPanel").getActiveTab().id;
        if (statTabActiveId == "PCPRealTimeMonitoringRunStatusStatGraphPanel_Id") {
            load = true;
        }
    }

    if (load) {
        Ext.Ajax.request({
            method: 'POST',
            url: context + '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData',
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText);
                Ext.getCmp("AlarmShowStyle_Id").setValue(JSON.stringify(result.AlarmShowStyle));
                var chart = null;
                if (deviceType === 0) {
                    chart = $("#RPCRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id").highcharts();
                } else if (deviceType === 1) {
                    chart = $("#PCPRealTimeMonitoringRunStatusStatGraphPanelPieDiv_Id").highcharts();
                }

                if (isNotVal(chart)) {
                    var series = chart.series[0];
                    var colors = [];
                    var alarmShowStyle = Ext.JSON.decode(Ext.getCmp("AlarmShowStyle_Id").getValue());
                    var pieData = [];
                    var datalist = result.totalRoot;
                    for (var i = 0; i < datalist.length; i++) {
                        if (datalist[i].itemCode != 'all') {
                            if (datalist[i].count > 0) {
                                var singleData = {};
                                singleData.name = datalist[i].item;
                                singleData.y = datalist[i].count;

                                if (datalist[i].itemCode == 'run') {
                                    singleData.color = '#' + alarmShowStyle.Run.run.Color;
                                } else if (datalist[i].itemCode == 'stop') {
                                    singleData.color = '#' + alarmShowStyle.Run.stop.Color;
                                } else if (datalist[i].itemCode == 'goOnline') {
                                    singleData.color = '#' + alarmShowStyle.Comm.goOnline.Color;
                                } else if (datalist[i].itemCode == 'offline') {
                                    singleData.color = '#' + alarmShowStyle.Comm.offline.Color;
                                }  else {
                                	singleData.color = '#' + alarmShowStyle.Run.noData.Color;
                                }
                                pieData.push(singleData);
                            }
                        }
                    }
                    series.setData(pieData);
                }
            },
            failure: function () {
                Ext.MessageBox.alert("错误", "与后台联系的时候出了问题");
            },
            params: {
                orgId: orgId,
                deviceType: deviceType
            }
        });
    }
}

function fullscreen() {
    document.getElementById('bannerToolbar2').style.display = 'none';
    document.getElementById('bannerToolbar3').style.display = '';
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

function exitFullscreen() {
    document.getElementById('bannerToolbar3').style.display = 'none';
    document.getElementById('bannerToolbar2').style.display = '';

    var elem = document;
    if (elem.webkitCancelFullScreen) {
        elem.webkitCancelFullScreen();
    } else if (elem.mozCancelFullScreen) {
        elem.mozCancelFullScreen();
    } else if (elem.cancelFullScreen) {
        elem.cancelFullScreen();
    } else if (elem.exitFullscreen) {
        elem.exitFullscreen();
    } else {
        //浏览器不支持全屏API或已被禁用
    }
}

//重置密码
function resetPwdFn() {
    var showResetPwdWin = Ext.create("AP.view.orgAndUser.SysUserEditPwdWin");
    showResetPwdWin.show();
    return false;
}

//帮助文档窗口
function showHelpDocumentWinFn() {
    var tabPanel = Ext.getCmp("frame_center_ids");
    var getTabId = tabPanel.getComponent("HelpDocPanel");
    if (!getTabId) {
        var url=context+'/readme/ap/ap.html';//\ap\readme\ap
//        var url=context+'/test.html';
    	tabPanel.add(Ext.create("AP.view.help.HelpDocPanel", {
            id: 'HelpDocPanel',
            closable: true,
            iconCls: 'help',
            closeAction: 'destroy',
            title: '帮助',
//            html:"<iframe src='"+url+"' width=100% height=100%></iframe>",
//            html: "<iframe src='https://www.baidu.com' width=100% height=100%></iframe>",
//            loader: {
//                url: url,
//                autoLoad: true
//            },
            listeners: {
                afterrender: function () {},
                delay: 150
            }
        })).show();

//        Ext.Ajax.request({
//            method: 'POST',
//            url: context + '/helpDocController/getHelpDocHtml',
//            success: function (response) {
//                var p = Ext.getCmp("HelpDocPanel_Id");
//                p.body.update(response.responseText);
//            },
//            failure: function () {
//                Ext.MessageBox.alert("信息", "请求失败");
//            },
//            params: {}
//        });
    }
    tabPanel.setActiveTab("HelpDocPanel");
    return false;
}

//注销session
function invalidateSession() {
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/userManagerController/invalidateSession',
        success: function (response) {

        },
        failure: function () {
            Ext.MessageBox.alert("信息", "请求失败");
        },
        params: {}
    });
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