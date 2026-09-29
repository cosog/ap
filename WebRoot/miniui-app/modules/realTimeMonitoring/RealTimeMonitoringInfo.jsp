<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.cosog.model.User" %>
<%
String path = request.getContextPath();
String moduleId = request.getParameter("moduleId");
User userLogin = (User)session.getAttribute("userLogin");
String loginUserNo = userLogin != null ? userLogin.getUserNo() + "" : "";
String loginUserLanguage = userLogin != null ? userLogin.getLanguageName() + "" : "zh_CN";
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>实时监控</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/realTimeMonitoringInfo.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        /* ===== 全局样式 ===== */
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .realtime-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
        }

        /* ===== 底部一级标签 ===== */
        .level1-footer {
            flex-shrink: 0;
            background: #fafafa;
            border-top: 1px solid #e0e0e0;
            padding: 0 10px;
            display: flex; align-items: center; gap: 2px;
            height: 36px; overflow-x: auto;
            order: 2;
        }
        .level1-footer .tab-item {
            padding: 4px 16px; font-size: 13px;
            cursor: pointer; color: #666; background: transparent;
            border-bottom: 2px solid transparent;
            transition: all 0.2s; user-select: none; white-space: nowrap;
        }
        .level1-footer .tab-item:hover { color: #333; }
        .level1-footer .tab-item.active {
            color: #2d6a9f; font-weight: bold;
            border-bottom-color: #2d6a9f;
        }
        .level1-footer .loading-tip { color: #999; font-size: 12px; padding: 0 10px; }

        /* ===== 资源监测区域 ===== */
        #resourceMonitorArea {
            margin-left: auto; display: flex; align-items: center; gap: 6px;
            white-space: nowrap; font-size: 12px;
        }
        @keyframes resourceBlink {
            0% { color: #ccc; }
            50% { color: #f0ad4e; }
            100% { color: #ccc; }
        }
        .resource-blink > span:first-child {
            animation: resourceBlink 1s ease-in-out infinite;
        }

        /* ===== 主布局 ===== */
        .main-area {
            flex: 1; display: flex; flex-direction: row;
            overflow: hidden; min-height: 0; order: 0;
        }

        /* ===== 左侧二级标签 ===== */
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
            transition: all 0.15s; user-select: none; text-align: center;
            writing-mode: vertical-rl; letter-spacing: 2px;
            width: 100%; flex-shrink: 0; min-height: 36px;
            line-height: 1.4; box-sizing: border-box;
        }
        .level2-sidebar .tab-item:hover { background: #e8ecf0; color: #333; }
        .level2-sidebar .tab-item.active {
            background: #e6f7ff; color: #1890ff;
            font-weight: bold; border-left-color: #1890ff;
        }

        /* ===== mini-panel 去边框 ===== */
        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; }
        .mini-panel-body {
            border: 0 !important;
            padding: 0 !important;
            overflow: hidden !important;
        }

        /* ===== mini-panel 工具条 ===== */
        .mini-panel-toolbar {
            background: #fafafa !important;
            border-bottom: 1px solid #e8e8e8 !important;
            padding: 4px 8px !important;
            box-sizing: border-box !important;
        }
        .mini-panel-toolbar > div {
            background: transparent !important;
            border: 0 !important;
        }

        /* 工具条内部 flex 布局 */
        .panel-toolbar {
            display: flex;
            align-items: center;
            gap: 6px;
            width: 100%;
            min-height: 24px;
        }
        .panel-toolbar .sep {
            display: inline-block;
            width: 1px; height: 16px;
            background: #ddd;
            margin: 0 2px;
        }
        .panel-toolbar .spacer {
            flex: 1;
        }

        /* ===== 统计饼图滚动包裹层 ===== */
        .pie-chart-scroll-wrapper {
            width: 100%;
            height: 100%;
            overflow: auto;
        }

        /* 统计饼图容器（min-height 由 JS 按 otherCardMinHeight 动态设置到具体的 id 上） */
        .pie-chart-container {
            width: 100%;
            height: 100%;
        }

        /* ===== 通用 ===== */
        .loading-placeholder {
            display: flex; align-items: center; justify-content: center;
            height: 100%; color: #999; font-size: 13px; flex-direction: column;
        }
        .loading-placeholder.error { color: #ff4d4f; }
        .alarm-badge {
            display: inline-block; border-radius: 10px;
            padding: 0 4px; min-width: 14px; height: 14px;
            line-height: 14px; text-align: center;
            font-size: 9px; font-weight: bold;
            margin-right: 2px; vertical-align: middle; box-sizing: border-box;
        }

        /* ===== 井筒/地面分析图表网格 ===== */
        .chart-grid {
            display: flex; flex-wrap: wrap;
            width: 100%; height: 100%; background: #fff;
        }
        .chart-grid .chart-item {
            flex: 1 1 50%; min-width: 300px; height: 50%;
            box-sizing: border-box;
            border: 1px solid #f0f0f0;
            position: relative; overflow: hidden;
        }
        .chart-grid .chart-item .chart-container {
            width: 100%; height: 100%;
        }

        /* ===== 趋势曲线容器 ===== */
        #trendContainer {
            display: flex; flex-wrap: wrap; align-content: flex-start;
            width: 100%; height: 100%; overflow: auto;
            padding: 0; box-sizing: border-box; background: #f5f7fa;
        }
        #trendContainer > .trend-chart-item {
            background: #fff; border-radius: 4px;
            border: 1px solid #e8e8e8;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
            box-sizing: border-box; padding: 2px;
            position: relative;
        }
        .trend-chart-container { width: 100%; height: 100%; }

        /* ===== 设备信息-辅件详情 ===== */
        .aux-detail-wrapper {
            padding: 8px 12px;
            word-break: break-all;
            line-height: 1.6;
            font-size: 12px;
            color: #555;
            background: #fafafa;
        }

        /* ===== 滚动条 ===== */
        .level2-sidebar::-webkit-scrollbar { width: 3px; }
        .level2-sidebar::-webkit-scrollbar-thumb {
            background: #ccc; border-radius: 4px;
        }
    </style>
</head>

<body>
    <div class="realtime-container">
        <div class="main-area">

            <!-- 二级标签（无内容时由 JS 加 hidden 类隐藏） -->
            <div class="level2-sidebar hidden" id="level2Sidebar"></div>

            <!-- 主水平 Splitter -->
            <div id="rmMainSplitter" class="mini-splitter" style="flex:1; height:100%;">

                <!-- 左侧整体 -->
                <div id="leftMainPanel" size="79%" showCollapseButton="false"
                     minSize="300" collapseDirection="left">
                    <div id="rmInnerSplitter" class="mini-splitter" style="width:100%; height:100%;">

                        <!-- 左侧：设备列表 + 统计 -->
                        <div id="deviceStatPanel" size="40%" showCollapseButton="false"
                             minSize="200" collapseDirection="left">
                            <div id="rmLeftVerticalSplitter" class="mini-splitter"
                                 style="width:100%; height:100%;" vertical="true">

                                <!-- 上：设备概览 -->
                                <div size="50%" showCollapseButton="false" minSize="120">
                                    <div id="deviceOverviewPanel" class="mini-panel"
                                         style="width:100%;height:100%;"
                                         showHeader="false" showToolbar="true" showCloseButton="false"
                                         bodyStyle="padding:0;overflow:hidden;">

                                        <div property="toolbar">
                                            <div class="panel-toolbar">
                                                <button id="btnRefresh" class="mini-button" plain="true"
                                                        iconCls="note-refresh"
                                                        onclick="refreshDeviceList()"></button>
                                                <input id="deviceCombo" class="mini-combobox" style="width:140px;"
                                                       emptyText="-- 全部 --"
                                                       url="<%=path%>/wellInformationManagerController/loadWellComboxList"
                                                       onbeforeload="onDeviceComboBeforeLoad"
                                                       onshowpopup="onDeviceComboShowPopup"
                                                       onload="onDeviceComboLoad"
                                                       dataField="list" totalField="totals"
                                                       valueField="boxkey" textField="boxval"
                                                       onvaluechanged="onDeviceComboChange" />
                                                <span class="spacer"></span>
                                                <button id="exportRealTimeMonitoringDeviceListBtn" class="mini-button"
                                                        plain="true" iconCls="export"
                                                        onclick="exportRealTimeMonitoringData()"></button>
                                                <button id="queryDeviceHistoryDataBtn" class="mini-button"
                                                        plain="true" onclick="gotoHistory()"></button>

                                                <!-- 隐藏控件 -->
                                                <input id="RealTimeMonitoringInfoDeviceListSelectRow_Id" type="hidden" value="-1" />
                                                <input id="RealTimeMonitoringStatSelectFESdiagramResult_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringStatSelectCommStatus_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringStatSelectRunStatus_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringStatSelectNumStatus_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringStatSelectDeviceType_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringColumnStr_Id" type="hidden" value="" />
                                                <input id="rodStressChart_ShowMaxRodStress_Id" type="hidden" value="0" />
                                                <input id="rodStressChart_ShowRodStressRange_Id" type="hidden" value="0" />
                                            </div>
                                        </div>

                                        <div id="deviceGrid" class="mini-datagrid"
                                             style="width:100%;height:100%;"
                                             idField="id" pageSize="25"
                                             allowResize="true" allowAlternating="true"
                                             frozenStartColumn="0" frozenEndColumn="1"
                                             url="<%=path%>/realTimeMonitoringController/getDeviceRealTimeOverview"
                                             dataField="totalRoot" totalField="totalCount"
                                             ondrawcell="onDeviceGridDrawCell"
                                             onselectionchanged="onDeviceGridSelectChanged"
                                             onload="onDeviceGridLoad"
                                             onbeforeload="onDeviceGridBeforeLoad">
                                            <div property="columns"></div>
                                        </div>
                                    </div>
                                </div>

                                <!-- 下：统计饼图 -->
                                <div id="statPanelWrapper" size="50%" showCollapseButton="true"
                                     minSize="100" visible="false">
                                    <div id="statPanel" class="mini-panel"
                                         style="width:100%;height:100%;"
                                         showHeader="false" showToolbar="false" showCloseButton="false"
                                         bodyStyle="padding:0;overflow:hidden;">

                                        <div id="statTabs" class="mini-tabs"
                                             style="width:100%;height:100%;"
                                             activeIndex="0"
                                             onactivechanged="onStatTabChanged">
                                            <div title="" name="FESdiagramResult" visible="false">
                                                <div class="pie-chart-scroll-wrapper">
                                                    <div id="pieChart_FESdiagramResult" class="pie-chart-container"></div>
                                                </div>
                                            </div>
                                            <div title="" name="CommStatus" visible="false">
                                                <div class="pie-chart-scroll-wrapper">
                                                    <div id="pieChart_CommStatus" class="pie-chart-container"></div>
                                                </div>
                                            </div>
                                            <div title="" name="RunStatus" visible="false">
                                                <div class="pie-chart-scroll-wrapper">
                                                    <div id="pieChart_RunStatus" class="pie-chart-container"></div>
                                                </div>
                                            </div>
                                            <div title="" name="NumStatus" visible="false">
                                                <div class="pie-chart-scroll-wrapper">
                                                    <div id="pieChart_NumStatus" class="pie-chart-container"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>

                        <!-- 中间：动态标签 -->
                        <div id="middlePanel" size="60%" showCollapseButton="true"
                             minSize="150" collapseDirection="right" visible="true">
                            <div id="middleTabs" class="mini-tabs"
                                 style="width:100%;height:100%;"
                                 activeIndex="0"
                                 onactivechanged="onMiddleTabChanged">

                                <!-- 井筒分析 -->
                                <div title="" name="middle_WellboreAnalysis" visible="false">
                                    <div id="wellboreAnalysisPanel" class="mini-panel"
                                         style="width:100%;height:100%;"
                                         showHeader="false" showToolbar="false" showCloseButton="false"
                                         bodyStyle="padding:0;overflow:hidden;">
                                        <div id="middle_WellboreAnalysis_body" style="width:100%;height:100%;">
                                            <div class="chart-grid">
                                                <div class="chart-item"><div id="wellboreChart1" class="chart-container"></div></div>
                                                <div class="chart-item"><div id="wellboreChart2" class="chart-container"></div></div>
                                                <div class="chart-item"><div id="wellboreChart3" class="chart-container"></div></div>
                                                <div class="chart-item"><div id="wellboreChart4" class="chart-container"></div></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- 地面分析 -->
                                <div title="" name="middle_SurfaceAnalysis" visible="false">
                                    <div id="surfaceAnalysisPanel" class="mini-panel"
                                         style="width:100%;height:100%;"
                                         showHeader="false" showToolbar="false" showCloseButton="false"
                                         bodyStyle="padding:0;overflow:hidden;">
                                        <div id="middle_SurfaceAnalysis_body" style="width:100%;height:100%;">
                                            <div class="chart-grid">
                                                <div class="chart-item"><div id="surfaceChart1" class="chart-container"></div></div>
                                                <div class="chart-item"><div id="surfaceChart2" class="chart-container"></div></div>
                                                <div class="chart-item"><div id="surfaceChart3" class="chart-container"></div></div>
                                                <div class="chart-item"><div id="surfaceChart4" class="chart-container"></div></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- 趋势曲线 -->
                                <div title="" name="middle_TrendCurve" visible="false">
                                    <div id="trendCurvePanel" class="mini-panel"
                                         style="width:100%;height:100%;"
                                         showHeader="false" showToolbar="false" showCloseButton="false"
                                         bodyStyle="padding:0;overflow:hidden;">
                                        <div id="middle_TrendCurve_body" style="width:100%;height:100%;"></div>
                                    </div>
                                </div>

                                <!-- 动态数据 -->
                                <div title="" name="middle_DynamicData" visible="false">
                                    <div id="dynamicDataPanel" class="mini-panel"
                                         style="width:100%;height:100%;"
                                         showHeader="false" showToolbar="true" showCloseButton="false"
                                         bodyStyle="padding:0;overflow:hidden;">

                                        <div property="toolbar">
                                            <div class="panel-toolbar">
                                                <span id="middleDynamicDataLabel" style="font-size:12px;color:#333;"></span>
                                                <span class="spacer"></span>
                                                <button id="dynamicDataExportBtn" class="mini-button"
                                                        plain="true" iconCls="export"
                                                        style="padding:2px 12px;"
                                                        onclick="exportDeviceRealTimeMonitoringData()"></button>
                                            </div>
                                        </div>

                                        <div id="RealTimeMonitoringInfoDataTableInfoDiv_id"
                                             style="width:100%;height:100%;overflow:hidden;background:#fff;"></div>
                                    </div>
                                </div>

                            </div>
                        </div>

                    </div>
                </div>

                <!-- 右侧面板 -->
                <div id="rightPanel" size="21%" showCollapseButton="true"
                     minSize="130" collapseDirection="right" visible="true">
                    <div id="rightTabs" class="mini-tabs"
                         style="width:100%;height:100%;"
                         activeIndex="0"
                         onactivechanged="onRightTabChanged">

                        <!-- 设备控制 -->
                        <div title="" name="right_DeviceControl" visible="false">
                            <div id="deviceControlPanel" class="mini-panel"
                                 style="width:100%;height:100%;"
                                 showHeader="false" showToolbar="false" showCloseButton="false"
                                 bodyStyle="padding:0;overflow:hidden;">
                                <div id="right_DeviceControl_container" style="width:100%;height:100%;"></div>
                            </div>
                        </div>

                        <!-- 设备信息 -->
                        <div title="" name="right_DeviceInfo" visible="false">
                            <div id="deviceInfoPanel" class="mini-panel"
                                 style="width:100%;height:100%;"
                                 showHeader="false" showToolbar="false" showCloseButton="false"
                                 bodyStyle="padding:0;overflow:hidden;">
                                <div id="right_DeviceInfo_container" style="width:100%;height:100%;">
                                    <div class="mini-splitter" style="width:100%;height:100%;" vertical="true">

                                        <!-- 上：附加信息 -->
                                        <div size="50%" showCollapseButton="false" minSize="80">
                                            <div id="deviceInfoAdditionalGrid" class="mini-datagrid"
                                                 style="width:100%;height:100%;"
                                                 showPager="false"
                                                 allowResize="false" allowAlternating="true"
                                                 idField="rowId">
                                                <div property="columns"></div>
                                            </div>
                                        </div>

                                        <!-- 下：辅件设备（带展开行详情） -->
                                        <div size="50%" showCollapseButton="false" minSize="80">
                                            <div id="deviceInfoAuxiliaryGrid" class="mini-datagrid"
                                                 style="width:100%;height:100%;"
                                                 showPager="false"
                                                 allowResize="false" allowAlternating="true"
                                                 idField="rowId"
                                                 autoHideRowDetail="true"
                                                 onshowrowdetail="onAuxiliaryShowRowDetail">
                                                <div property="columns"></div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>

        <!-- 底部一级标签 -->
        <div class="level1-footer" id="level1Footer"></div>
    </div>

    <script>
        var context = '<%=path%>';
        var user_ = '<%=loginUserNo%>';
        var loginUserLanguage = '<%=loginUserLanguage%>';

        $(document).ready(function () {
            mini.parse();
            setTimeout(function () {
                initRealTimeMonitoringPage();
            }, 10);
        });
    </script>
</body>
</html>