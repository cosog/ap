<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*,com.cosog.model.User,com.cosog.utils.ConfigFile,com.google.gson.Gson" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
Gson gson = new Gson();
String path = request.getContextPath();
User userLogin = (User)session.getAttribute("userLogin");
String userLoginNo = userLogin != null ? userLogin.getUserNo() + "" : "";
String loginUserLanguage = userLogin != null ? userLogin.getLanguageName() + "" : "zh_CN";
int loginUserLanguageValue = userLogin != null ? userLogin.getLanguage() : 0;
int loginUserRoleVideoKeyEdit = userLogin != null ? userLogin.getRoleVideoKeyEdit() : 0;
int loginUserLanguageKeyEdit = userLogin != null ? userLogin.getRoleLanguageEdit() : 0;
int loginUserRoleLevel = userLogin != null ? userLogin.getRoleLevel() : 0;

String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主设备</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/deviceManagerInfo.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .device-manager-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
        }
        .dm-main { flex: 1; display: flex; overflow: hidden; }
        /* 一级标签 */
        .level1-footer {
            flex-shrink: 0; background: #fafafa;
            border-top: 1px solid #e0e0e0;
            padding: 0 10px; display: flex; align-items: center; gap: 2px;
            height: 36px; overflow-x: auto;
        }
        .level1-footer .tab-item {
            padding: 4px 16px; font-size: 13px;
            cursor: pointer; color: #666; background: transparent;
            border-bottom: 2px solid transparent;
            transition: all 0.2s;
            user-select: none; white-space: nowrap;
        }
        .level1-footer .tab-item:hover { color: #333; }
        .level1-footer .tab-item.active {
            color: #2d6a9f; font-weight: bold;
            border-bottom-color: #2d6a9f;
        }
        /* 二级标签 */
        .level2-sidebar {
            flex-shrink: 0; width: 32px;
            background: #f5f7fa;
            border-right: 1px solid #e8e8e8;
            overflow: auto; padding: 8px 0;
            display: flex; flex-direction: column;
            align-items: center; justify-content: flex-start;
        }
        .level2-sidebar.hidden { display: none; }
        .level2-sidebar .tab-item {
            padding: 10px 2px; font-size: 12px;
            cursor: pointer; color: #555; background: transparent;
            border-left: 3px solid transparent;
            transition: all 0.15s;
            user-select: none; text-align: center;
            writing-mode: vertical-rl;
            letter-spacing: 2px; width: 100%;
            flex-shrink: 0; min-height: 36px; line-height: 1.4;
            box-sizing: border-box;
        }
        .level2-sidebar .tab-item:hover { background: #e8ecf0; color: #333; }
        .level2-sidebar .tab-item.active {
            background: #e6f7ff; color: #1890ff;
            font-weight: bold; border-left-color: #1890ff;
        }
        /* 内容区 */
        .dm-content {
            flex: 1; overflow: hidden;
            background: #f0f2f5; padding: 4px;
            box-sizing: border-box;
        }
        .hot-container { width: 100%; height: 100%; }
        /* Panel 去边框 */
        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; }
        .mini-panel-body { border: 0 !important; }
        /* 工具条统一背景色 */
        .mini-panel-toolbar {
            background: #fafafa !important;
            border-bottom: 1px solid #e8e8e8 !important;
            padding: 4px 8px !important;
            box-sizing: border-box !important;
        }
        .mini-panel-toolbar > div { background: transparent !important; border: 0 !important; }
        /* 工具条 flex 布局 */
        .dm-toolbar {
            display: flex; align-items: center;
            width: 100%; min-height: 24px;
        }
        .dm-toolbar .dm-toolbar-right {
            margin-left: auto;
            display: flex; align-items: center; gap: 2px;
        }
        .dm-toolbar .dm-toolbar-sep {
            display: inline-block;
            width: 1px; height: 16px; background: #ddd;
            margin: 0 6px; vertical-align: middle;
        }
        /* Splitter 去边框 */
        .mini-splitter-border { border: 0 !important; }
        .mini-splitter-pane { padding: 0 !important; border: 0 !important; }
        .mini-splitter-handler {
            background: transparent !important;
            border: 1px solid #e8e8e8 !important;
        }
        /* Tab body 无 padding */
        .mini-tabs-body, .mini-tab-body { padding: 0 !important; border: 0 !important; }
    </style>
</head>
<body>

<div class="device-manager-container">
    <div class="dm-main">
        <div class="level2-sidebar hidden" id="level2Sidebar"></div>

        <div class="dm-content" id="dmContent">
            <div id="deviceManagerPanel" class="mini-panel"
                 style="width:100%;height:100%;"
                 showHeader="false" showToolbar="true" showCloseButton="false"
                 bodyStyle="padding:0;">

                <!-- ============ 大工具条 ============ -->
                <div property="toolbar">
                    <div class="dm-toolbar">
                        <button id="dmRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true" onclick="onDmRefreshDeviceList()"></button>
                        <span class="dm-toolbar-sep"></span>
                        <span id="dmDeviceNameLabel" style="font-size:12px;color:#333;"></span>
                        <input id="dmDeviceCombo" class="mini-combobox" style="width:150px;" emptyText="-- 全部 --"
                               url="<%=path%>/wellInformationManagerController/loadWellComboxList"
                               dataField="list" valueField="boxkey" textField="boxval"
                               onbeforeload="onDmDeviceComboBeforeLoad"
                               onshowpopup="onDmDeviceComboShowPopup"
                               onvaluechanged="onDmDeviceComboChange" />
                        <span class="dm-toolbar-sep"></span>
                        <span id="dmSignInIdLabel" style="font-size:12px;color:#333;"></span>
                        <input id="dmSignInIdCombo" class="mini-combobox" style="width:150px;" emptyText="-- 全部 --"
                               url="<%=path%>/wellInformationManagerController/loadSignInIdComboxList"
                               dataField="list" valueField="boxkey" textField="boxval"
                               onbeforeload="onDmSignInIdComboBeforeLoad"
                               onshowpopup="onDmSignInIdComboShowPopup"
                               onvaluechanged="onDmSignInIdComboChange" />
                        <span class="dm-toolbar-sep"></span>
                        <button id="dmQueryBtn" class="mini-button" iconCls="search" plain="true" onclick="onDmQueryDeviceList()"></button>
                        <span class="dm-toolbar-sep"></span>
                        <span id="dmDeviceTotalCountLabel" style="font-size:12px;color:#666;"></span>

                        <div class="dm-toolbar-right">
                            <button id="dmAddDeviceBtn" class="mini-button" iconCls="add" plain="true" onclick="onDmAddDevice()"></button>
                            <button id="dmDelDeviceBtn" class="mini-button" iconCls="delete" plain="true" onclick="onDmDelDevice()"></button>
                            <button id="dmSaveDeviceBtn" class="mini-button" iconCls="save" plain="true" onclick="onDmSaveDevice()"></button>
                            <button id="dmBatchAddDeviceBtn" class="mini-button" iconCls="batchAdd" plain="true" onclick="onDmBatchAddDevice()"></button>
                            <button id="dmDeviceOrgChangeBtn" class="mini-button" iconCls="move" plain="true" onclick="onDmDeviceOrgChange()"></button>
                            <button id="dmExportDeviceBtn" class="mini-button" iconCls="export" plain="true" onclick="onDmExportDevice()"></button>
                        </div>
                    </div>
                </div>

                <!-- ============ 主体：左右 Splitter ============ -->
                <div id="dmMainSplitter" class="mini-splitter" style="width:100%;height:100%;" vertical="false">

                    <!-- 左侧：设备列表 -->
                    <div size="50%" id="DeviceTablePanel_id" showCollapseButton="false" minSize="300">
                        <div id="DeviceTableDiv_id" class="hot-container"></div>
                    </div>

                    <!-- 右侧：附加信息 -->
                    <div id="dmRightPane" size="50%" showCollapseButton="false" minSize="300" visible="false">
                        <div id="deviceAdditionalTabs" class="mini-tabs" style="width:100%;height:100%;" tabPosition="top" onactivechanged="onDmAdditionalTabChanged">

                            <div id="additionalInfoTab" title="" name="additionalInfo" visible="false">
                                <div id="DeviceAdditionalInfoTableDiv_id" class="hot-container"></div>
                            </div>

                            <div id="auxiliaryDeviceTab" title="" name="auxiliaryDevice" visible="false">
                                <div id="DeviceAuxiliaryDeviceTableDiv_id" class="hot-container"></div>
                            </div>

                            <div id="calculateDataTab" title="" name="calculateData" visible="false">
                                <div id="deviceCalculateDataTabs" class="mini-tabs" style="width:100%;height:100%;" tabPosition="top" onactivechanged="onDmCalculateDataTabChanged">

                                    <div id="wellboreDataTab" title="" name="wellboreData" visible="false">
                                        <div class="mini-panel" style="width:100%;height:100%;"
                                             showHeader="false" showToolbar="true" showCloseButton="false"
                                             bodyStyle="padding:0;">
                                            <div property="toolbar">
                                                <div class="dm-toolbar">
                                                    <div class="dm-toolbar-right">
                                                        <button id="dmWellboreDataDownlinkBtn" class="mini-button" iconCls="downlink" plain="true"
                                                                onclick="onDmProductionDataDownlink()"></button>
                                                        <button id="dmWellboreDataUplinkBtn" class="mini-button" iconCls="uplink" plain="true"
                                                                onclick="onDmProductionDataUplink()"></button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="AdditionalInfoTableDiv_id" class="hot-container"></div>
                                        </div>
                                    </div>

                                    <div id="pumpingUnitDataTab" title="" name="pumpingUnitData" visible="false">
                                        <div class="mini-panel" style="width:100%;height:100%;"
                                             showHeader="false" showToolbar="true" showCloseButton="false"
                                             bodyStyle="padding:0;">
                                            <div property="toolbar">
                                                <div class="dm-toolbar">
                                                    <div class="dm-toolbar-right">
                                                        <button id="dmPumpingUnitDataDownlinkBtn" class="mini-button" iconCls="downlink" plain="true"
                                                                onclick="onDmPumpingUnitDataDownlink()"></button>
                                                        <button id="dmPumpingUnitDataUplinkBtn" class="mini-button" iconCls="uplink" plain="true"
                                                                onclick="onDmPumpingUnitDataUplink()"></button>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="mini-splitter" style="width:100%;height:100%;" vertical="true">
                                                <div size="45%" showCollapseButton="false">
                                                    <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                                                        <div size="50%" showCollapseButton="false" minSize="200">
                                                            <div id="pumpingUnitDataPanel" class="mini-panel"
                                                                 style="width:100%;height:100%;"
                                                                 showHeader="true" showToolbar="false" showCloseButton="false"
                                                                 bodyStyle="padding:0;">
                                                                <div id="PumpingInfoTableDiv_id" class="hot-container"></div>
                                                            </div>
                                                        </div>
                                                        <div size="50%" showCollapseButton="true" collapseDirection="right" minSize="200">
                                                            <div id="pumpingUnitDetailPanel" class="mini-panel"
                                                                 style="width:100%;height:100%;"
                                                                 showHeader="true" showToolbar="false" showCloseButton="false"
                                                                 bodyStyle="padding:0;">
                                                                <div id="DevicePumpingUnitDetailedInformationTableDiv_id" class="hot-container"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div size="55%" showCollapseButton="true" collapseDirection="bottom" minSize="120">
                                                    <div id="pumpingUnitPRTFPanel" class="mini-panel"
                                                         style="width:100%;height:100%;"
                                                         showHeader="true" showToolbar="false" showCloseButton="false"
                                                         bodyStyle="padding:0;">
                                                        <div id="DevicePumpingUnitPRTFTableDiv_id" class="hot-container"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div id="videoInfoTab" title="" name="videoInfo" visible="false">
                                <div class="mini-panel" style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;">
                                    <div property="toolbar">
                                        <div class="dm-toolbar">
                                            <div class="dm-toolbar-right">
                                                <button id="dmEditVideoKeyBtn" class="mini-button" iconCls="save" plain="true"
                                                        onclick="onDmEditVideoKey()"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="VideoInfoTableDiv_id" class="hot-container"></div>
                                </div>
                            </div>

                            <div id="fsDiagramConstructionTab" title="" name="fsDiagramConstruction" visible="false">
                                <div class="mini-panel" style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;">
                                    <div property="toolbar">
                                        <div class="dm-toolbar">
                                            <div class="dm-toolbar-right">
                                                <button id="dmFSDiagramConstructionDownlinkBtn" class="mini-button" iconCls="downlink" plain="true"
                                                        onclick="onDmFSDiagramConstructionDataDownlink()"></button>
                                                <button id="dmFSDiagramConstructionUplinkBtn" class="mini-button" iconCls="uplink" plain="true"
                                                        onclick="onDmFSDiagramConstructionDataUplink()"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="DeviceFSDiagramConstructionInfoTableDiv_id" class="hot-container"></div>
                                </div>
                            </div>

                            <div id="systemParameterTab" title="" name="systemParameter" visible="false">
                                <div class="mini-panel" style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;">
                                    <div property="toolbar">
                                        <div class="dm-toolbar">
                                            <div class="dm-toolbar-right">
                                                <button id="dmSystemParameterDownlinkBtn" class="mini-button" iconCls="downlink" plain="true"
                                                        onclick="onDmSystemParameterDataDownlink()"></button>
                                                <button id="dmSystemParameterUplinkBtn" class="mini-button" iconCls="uplink" plain="true"
                                                        onclick="onDmSystemParameterDataUplink()"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="DeviceSystemParameterConfigurationInfoTableDiv_id" class="hot-container"></div>
                                </div>
                            </div>

                            <div id="intelligentFrequencyConversionTab" title="" name="intelligentFrequencyConversion" visible="false">
                                <div class="mini-panel" style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;">
                                    <div property="toolbar">
                                        <div class="dm-toolbar">
                                            <div class="dm-toolbar-right">
                                                <button id="dmIntelligentFrequencyDownlinkBtn" class="mini-button" iconCls="downlink" plain="true"
                                                        onclick="onDmIntelligentFrequencyConversionDataDownlink()"></button>
                                                <button id="dmIntelligentFrequencyUplinkBtn" class="mini-button" iconCls="uplink" plain="true"
                                                        onclick="onDmIntelligentFrequencyConversionDataUplink()"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="DeviceIntelligentFrequencyConversionInfoTableDiv_id" class="hot-container"></div>
                                </div>
                            </div>

                            <div id="interlockProtectionTab" title="" name="interlockProtection" visible="false">
                                <div class="mini-panel" style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;">
                                    <div property="toolbar">
                                        <div class="dm-toolbar">
                                            <div class="dm-toolbar-right">
                                                <button id="dmInterlockProtectionDownlinkBtn" class="mini-button" iconCls="downlink" plain="true"
                                                        onclick="onDmInterlockProtectionDataDownlink()"></button>
                                                <button id="dmInterlockProtectionUplinkBtn" class="mini-button" iconCls="uplink" plain="true"
                                                        onclick="onDmInterlockProtectionDataUplink()"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="DeviceInterlockProtectionInfoTableDiv_id" class="hot-container"></div>
                                </div>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="level1-footer" id="level1Footer"></div>
</div>

<script>
    var context = '<%=path%>';
    var user_ = '<%=userLoginNo%>';
    var loginUserLanguage = '<%=loginUserLanguage%>';
    var loginUserLanguageValue = <%=loginUserLanguageValue%>;
    var loginUserRoleVideoKeyEdit = <%=loginUserRoleVideoKeyEdit%>;
    var loginUserLanguageKeyEdit = <%=loginUserLanguageKeyEdit%>;
    var loginUserRoleLevel = <%=loginUserRoleLevel%>;

    $(document).ready(function () {
        mini.parse();
        
        setTimeout(function () {
        	initDeviceManagerPage();
        }, 100);
        
    });
</script>
</body>
</html>