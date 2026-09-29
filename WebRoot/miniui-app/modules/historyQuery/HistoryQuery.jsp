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
    <title>历史查询</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />

    <!-- ★ 先定义全局变量，供外部 js 加载时使用 -->
    <script>
        var context = '<%=path%>';
        var user_ = '<%=loginUserNo%>';
        var loginUserLanguage = '<%=loginUserLanguage%>';
    </script>

    <!-- ★ 再引入外部 js（此时 context 已可用） -->
    <script src="js/historyQueryInfo.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>

    <style>
        /* ===== 全局样式 ===== */
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .history-container {
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
            background: transparent !important; border: 0 !important;
        }
        .panel-toolbar {
            display: flex; align-items: center; gap: 6px;
            width: 100%; min-height: 24px;
        }
        .panel-toolbar .spacer { flex: 1; }

        /* ===== 通用 ===== */
        .loading-placeholder {
            display: flex; align-items: center; justify-content: center;
            height: 100%; color: #999; font-size: 13px; flex-direction: column;
        }
        .loading-placeholder.error { color: #ff4d4f; }

        /* ===== 饼图容器（min-height 由 JS 设置到具体 id 上） ===== */
        .pie-chart-scroll-wrapper {
            width: 100%; height: 100%;
            overflow: auto;
        }
        .pie-chart-container {
            width: 100%; height: 100%;
        }

        /* ===== 报警徽章 ===== */
        .alarm-badge {
            display: inline-block; border-radius: 10px;
            padding: 0 4px; min-width: 14px; height: 14px;
            line-height: 14px; text-align: center;
            font-size: 9px; font-weight: bold;
            margin-right: 2px; vertical-align: middle; box-sizing: border-box;
        }
        .device-name-cell { white-space: nowrap; }

        /* ===== 趋势曲线图表容器 ===== */
        .chart-container {
            width: 100%; height: 100%; min-height: 200px;
        }

        /* ===== 平铺图形 ===== */
        .tiled-chart-container {
            width: 100%; height: 100%;
            overflow: auto; padding: 2px; box-sizing: border-box;
        }
        .tiled-chart-container .chart-item {
            float: left; box-sizing: border-box; padding: 2px;
        }

        /* ===== 通用工具条（tab body 内） ===== */
        .tab-toolbar-row1 {
            display: flex; align-items: center; flex-wrap: wrap;
            gap: 4px; padding: 4px 10px;
            background: #fff; border-bottom: 1px solid #e8e8e8;
        }
        .tab-toolbar-row2 {
            display: flex; align-items: center; flex-wrap: wrap;
            gap: 4px; padding: 4px 10px;
            background: #fafafa; border-bottom: 1px solid #e8e8e8;
        }
        .tab-toolbar-row1 .lbl,
        .tab-toolbar-row2 .lbl { font-size: 12px; color: #333; }
        .tab-toolbar-row2 .lbl { color: #666; }
        .tab-toolbar-row1 .count-info { font-size: 12px; color: #999; }
        .tab-toolbar-row1 .spacer { flex: 1; }

        /* ===== 滚动条 ===== */
        .level2-sidebar::-webkit-scrollbar { width: 3px; }
        .level2-sidebar::-webkit-scrollbar-thumb {
            background: #ccc; border-radius: 4px;
        }
    </style>
</head>

<body>
    <div class="history-container">
        <div class="main-area">

            <!-- 二级标签 -->
            <div class="level2-sidebar hidden" id="level2Sidebar"></div>

            <!-- 内容区域 -->
            <div style="flex:1; overflow:hidden;">
                <div id="hqMainSplitter" class="mini-splitter"
                     style="width:100%; height:100%;" vertical="false">

                    <!-- 左侧：设备列表 + 统计 -->
                    <div size="35%" showCollapseButton="false" minSize="200">
                        <div id="hqLeftVerticalSplitter" class="mini-splitter"
                             style="width:100%; height:100%;" vertical="true">

                            <!-- 上：设备列表 -->
                            <div size="50%" showCollapseButton="false" minSize="120">
                                <div id="deviceGridPanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">

                                    <div property="toolbar">
                                        <div class="panel-toolbar">
                                            <button id="btnRefresh" class="mini-button" plain="true"
                                                    iconCls="note-refresh"
                                                    onclick="refreshDeviceList()"></button>
                                            <input id="deviceCombo" class="mini-combobox" style="width:140px;"
                                                   allowInput="true"
                                                   onbeforeload="onDeviceComboBeforeLoad"
                                                   onshowpopup="onDeviceComboShowPopup"
                                                   onload="onDeviceComboLoad"
                                                   dataField="list" totalField="totals"
                                                   valueField="boxkey" textField="boxval"
                                                   onvaluechanged="onDeviceComboChange" />
                                            <span class="spacer"></span>
                                            <button id="exportHistoryQueryDeviceListBtn" class="mini-button"
                                                    plain="true" iconCls="export"
                                                    onclick="exportDeviceList()"></button>

                                            <!-- 隐藏域 -->
                                            <input id="HistoryQueryInfoDeviceListSelectRow_Id" type="hidden" value="-1" />
                                            <input id="HistoryQueryStatSelectFESdiagramResult_Id" type="hidden" value="" />
                                            <input id="HistoryQueryStatSelectCommStatus_Id" type="hidden" value="" />
                                            <input id="HistoryQueryStatSelectRunStatus_Id" type="hidden" value="" />
                                            <input id="HistoryQueryStatSelectNumStatus_Id" type="hidden" value="" />
                                            <input id="HistoryQueryStatSelectDeviceType_Id" type="hidden" value="" />
                                            <input id="HistoryQueryWellListColumnStr_Id" type="hidden" value="" />
                                            <input id="HistoryQueryDataColumnStr_Id" type="hidden" value="" />
                                            <input id="HistoryQueryDiagramOverlayColumnStr_Id" type="hidden" value="" />
                                            <input id="selectedDeviceId_global" type="hidden" value="0" />
                                        </div>
                                    </div>

                                    <div id="deviceGrid" class="mini-datagrid"
                                         style="width:100%;height:100%;"
                                         idField="id" pageSize="20"
                                         allowResize="true" allowAlternating="true"
                                         dataField="totalRoot" totalField="totalCount"
                                         onselectionchanged="onDeviceSelect"
                                         onload="onDeviceGridLoad"
                                         onbeforeload="onDeviceGridBeforeLoad"
                                         ondrawcell="onDeviceGridDrawCell">
                                        <div property="columns"></div>
                                    </div>
                                </div>
                            </div>

                            <!-- 下：统计饼图 -->
                            <div id="statPanelWrapper" size="50%" showCollapseButton="true"
                                 minSize="100" collapseDirection="bottom" visible="false">
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

                    <!-- 右侧：结果 tabs（静态定义，初始全部隐藏） -->
                    <div size="65%" showCollapseButton="true" minSize="300">
                        <div id="resultTabs" class="mini-tabs"
                             style="width:100%;height:100%;"
                             activeIndex="0"
                             onactivechanged="onResultTabChanged">

                            <!-- ============================================ -->
                            <!-- 趋势曲线                                      -->
                            <!-- ============================================ -->
                            <div title="" name="TrendCurve" visible="false">
                                <div class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="trendCurveBodyContainer" style="width:100%;height:100%;">
                                        <div style="display:flex; flex-direction:column; height:100%;">

                                            <!-- 工具条 1 -->
                                            <div class="tab-toolbar-row1">
                                                <span id="trendRangeLabel" class="lbl"></span>
                                                <input id="startDate" class="mini-datepicker" style="width:150px;"
                                                       format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm"
                                                       showTime="true" showOkButton="true" showTodayButton="true"
                                                       showClearButton="false" allowInput="false" />
                                                <span id="trendTimeToLabel" class="lbl" style="margin-left:8px;"></span>
                                                <input id="endDate" class="mini-datepicker" style="width:150px;"
                                                       format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm"
                                                       showTime="true" showOkButton="true" showTodayButton="true"
                                                       showClearButton="false" allowInput="false" />
                                                <button id="trendSearchBtn" class="mini-button" plain="true"
                                                        iconCls="search" onclick="doQuery()"></button>
                                                <button id="trendExportBtn" class="mini-button" plain="true"
                                                        iconCls="export" onclick="exportData()"></button>
                                                <span class="spacer"></span>
                                                <span id="vacuateCountLabel" class="count-info" style="display:none;">
                                                    <span id="vacuateCountText"></span>：<span id="vacuateCountSpan">0</span>
                                                </span>
                                                <input id="HistoryQueryVacuateCount_Id" type="hidden" value="" />
                                                <span id="totalCountLabel" class="count-info" style="display:none;">
                                                    <span id="totalCountText"></span>：<span id="totalCountSpan">0</span>
                                                </span>
                                                <input id="HistoryQueryTotalCount_Id" type="hidden" value="" />
                                            </div>

                                            <!-- 工具条 2 -->
                                            <div class="tab-toolbar-row2">
                                                <span id="trendTimeRangeLabel" class="lbl"></span>
                                                <input type="checkbox" id="chkAll" checked onchange="updateTimeRange(event)" />
                                                <label for="chkAll" id="trendChkAllLabel"></label>
                                                <input type="checkbox" id="chk1" checked name="00:00:00~06:00:00" onchange="updateTimeRange(event)" /><label for="chk1">0~6h</label>
                                                <input type="checkbox" id="chk2" checked name="06:00:00~12:00:00" onchange="updateTimeRange(event)" /><label for="chk2">6~12h</label>
                                                <input type="checkbox" id="chk3" checked name="12:00:00~18:00:00" onchange="updateTimeRange(event)" /><label for="chk3">12~18h</label>
                                                <input type="checkbox" id="chk4" checked name="18:00:00~23:59:59" onchange="updateTimeRange(event)" /><label for="chk4">18~24h</label>
                                            </div>

                                            <!-- 主体 splitter -->
                                            <div id="historyTrendCurvePanel" class="mini-splitter"
                                                 style="width:100%; height:100%;" vertical="true">
                                                <div size="50%" showCollapseButton="true" minSize="80" collapseDirection="top">
                                                    <div style="height:100%; display:flex; flex-direction:column;">
                                                        <div id="historyCurveContainer" class="chart-container"></div>
                                                    </div>
                                                </div>
                                                <div size="50%" showCollapseButton="false" style="display:flex; flex-direction:column;">
                                                    <div style="flex:1; min-height:0; height:100%;">
                                                        <div id="historyDataGrid" class="mini-datagrid"
                                                             style="height:100%; width:100%;"
                                                             idField="id" pageSize="25"
                                                             allowResize="true" showPager="true"
                                                             dataField="totalRoot" totalField="totalCount"
                                                             autoLoad="false"
                                                             onload="onHistoryDataLoad"
                                                             onbeforeload="onHistoryDataBeforeLoad"
                                                             ondrawcell="onHistoryDataDrawCell"
                                                             onrowdblclick="onHistoryDataDblClick">
                                                            <div property="columns"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ============================================ -->
                            <!-- 图形平铺                                      -->
                            <!-- ============================================ -->
                            <div title="" name="TiledDiagram" visible="false">
                                <div class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="tiledDiagramBodyContainer" style="width:100%;height:100%;">
                                        <div style="display:flex; flex-direction:column; height:100%;">

                                            <!-- 工具条 1 -->
                                            <div class="tab-toolbar-row1">
                                                <span id="tiledRangeLabel" class="lbl"></span>
                                                <input id="tiledStartDate" class="mini-datepicker" style="width:150px;"
                                                       format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm"
                                                       showTime="true" showOkButton="true" showTodayButton="true"
                                                       showClearButton="false" allowInput="false" />
                                                <span id="tiledTimeToLabel" class="lbl" style="margin-left:8px;"></span>
                                                <input id="tiledEndDate" class="mini-datepicker" style="width:150px;"
                                                       format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm"
                                                       showTime="true" showOkButton="true" showTodayButton="true"
                                                       showClearButton="false" allowInput="false" />
                                                <button id="tiledSearchBtn" class="mini-button" plain="true"
                                                        iconCls="search" onclick="doTiledWorkTypeComboLoad()"></button>
                                                <button id="tiledExportBtn" class="mini-button" plain="true"
                                                        iconCls="export" onclick="exportData()"></button>
                                                <span class="spacer"></span>
                                                <span id="tiledTotalCountLabel" class="count-info" style="display:none;">
                                                    <span id="tiledTotalCountText"></span>：<span id="tiledTotalCountSpan">0</span>
                                                </span>
                                            </div>

                                            <!-- 工具条 2 -->
                                            <div class="tab-toolbar-row2">
                                                <span id="tiledWorkTypeLabel" class="lbl"></span>
                                                <mini-combobox id="tiledWorkTypeCombo" style="width:180px;" popupWidth="280"
                                                               textField="resultName" valueField="resultCode"
                                                               multiSelect="true" showClose="true"
                                                               oncloseclick="onTiledWorkTypeComboCloseClick"
                                                               dataField="totalRoot" totalField="totalCount"
                                                               onbeforeload="onTiledWorkTypeComboBeforeLoad"
                                                               onload="onTiledWorkTypeComboLoad"
                                                               onvaluechanged="onTiledWorkTypeComboChange">
                                                    <columns>
                                                        <column field="resultName" headerAlign="left" align="left" width="60%"></column>
                                                        <column field="count" headerAlign="left" align="left" width="40%"></column>
                                                    </columns>
                                                </mini-combobox>
                                                <span id="tiledTimeRangeLabel" class="lbl"></span>
                                                <input type="checkbox" id="tiledChkAll" checked onchange="updateTiledTimeRange(event)" />
                                                <label for="tiledChkAll" id="tiledChkAllLabel"></label>
                                                <input type="checkbox" id="tiledChk1" checked name="00:00:00~06:00:00" onchange="updateTiledTimeRange(event)" /><label for="tiledChk1">0~6h</label>
                                                <input type="checkbox" id="tiledChk2" checked name="06:00:00~12:00:00" onchange="updateTiledTimeRange(event)" /><label for="tiledChk2">6~12h</label>
                                                <input type="checkbox" id="tiledChk3" checked name="12:00:00~18:00:00" onchange="updateTiledTimeRange(event)" /><label for="tiledChk3">12~18h</label>
                                                <input type="checkbox" id="tiledChk4" checked name="18:00:00~23:59:59" onchange="updateTiledTimeRange(event)" /><label for="tiledChk4">18~24h</label>
                                            </div>

                                            <!-- 主体 tabs -->
                                            <div id="tiledTabs" class="mini-tabs"
                                                 style="width:100%; height:100%;"
                                                 tabPosition="left" activeIndex="0"
                                                 onactivechanged="onTiledTabActiveChanged">
                                                <div id="tiledFsTab" title="" name="FSDiagram">
                                                    <div id="fsTiledContainer" class="tiled-chart-container"
                                                         style="width:100%; height:100%; overflow:auto; padding:2px; box-sizing:border-box;"></div>
                                                </div>
                                                <div id="tiledPsTab" title="" name="PSDiagram">
                                                    <div id="psTiledContainer" class="tiled-chart-container"
                                                         style="width:100%; height:100%; overflow:auto; padding:2px; box-sizing:border-box;"></div>
                                                </div>
                                                <div id="tiledIsTab" title="" name="ISDiagram">
                                                    <div id="isTiledContainer" class="tiled-chart-container"
                                                         style="width:100%; height:100%; overflow:auto; padding:2px; box-sizing:border-box;"></div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ============================================ -->
                            <!-- 图形叠加                                      -->
                            <!-- ============================================ -->
                            <div title="" name="DiagramOverlay" visible="false">
                                <div class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="false" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="diagramOverlayBodyContainer" style="width:100%;height:100%;">
                                        <div style="display:flex; flex-direction:column; height:100%;">

                                            <!-- 工具条 1 -->
                                            <div class="tab-toolbar-row1">
                                                <span id="overlayRangeLabel" class="lbl"></span>
                                                <input id="overlayStartDate" class="mini-datepicker" style="width:150px;"
                                                       format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm"
                                                       showTime="true" showOkButton="true" showTodayButton="true"
                                                       showClearButton="false" allowInput="false" />
                                                <span id="overlayTimeToLabel" class="lbl" style="margin-left:8px;"></span>
                                                <input id="overlayEndDate" class="mini-datepicker" style="width:150px;"
                                                       format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm"
                                                       showTime="true" showOkButton="true" showTodayButton="true"
                                                       showClearButton="false" allowInput="false" />
                                                <button id="overlaySearchBtn" class="mini-button" plain="true"
                                                        iconCls="search" onclick="overlayWorkTypeComboLoad()"></button>
                                                <button id="overlayExportBtn" class="mini-button" plain="true"
                                                        iconCls="export" onclick="exportData()"></button>
                                                <span class="spacer"></span>
                                                <span id="overlayVacuateCountLabel" class="count-info" style="display:none;">
                                                    <span id="overlayVacuateCountText"></span>：<span id="overlayVacuateCountSpan">0</span>
                                                </span>
                                                <span id="overlayTotalCountLabel" class="count-info" style="display:none;">
                                                    <span id="overlayTotalCountText"></span>：<span id="overlayTotalCountSpan">0</span>
                                                </span>
                                            </div>

                                            <!-- 工具条 2 -->
                                            <div class="tab-toolbar-row2">
                                                <span id="overlayWorkTypeLabel" class="lbl"></span>
                                                <mini-combobox id="overlayWorkTypeCombo" style="width:180px;" popupWidth="280"
                                                               textField="resultName" valueField="resultCode"
                                                               multiSelect="true" showClose="true"
                                                               oncloseclick="onOverlayWorkTypeComboCloseClick"
                                                               dataField="totalRoot" totalField="totalCount"
                                                               onbeforeload="onOverlayWorkTypeComboBeforeLoad"
                                                               onload="onOverlayWorkTypeComboLoad"
                                                               onvaluechanged="onOverlayWorkTypeComboChange">
                                                    <columns>
                                                        <column field="resultName" headerAlign="left" align="left" width="60%"></column>
                                                        <column field="count" headerAlign="left" align="left" width="40%"></column>
                                                    </columns>
                                                </mini-combobox>
                                                <span id="overlayTimeRangeLabel" class="lbl"></span>
                                                <input type="checkbox" id="overlayChkAll" checked onchange="updateOverlayTimeRange(event)" />
                                                <label for="overlayChkAll" id="overlayChkAllLabel"></label>
                                                <input type="checkbox" id="overlayChk1" checked name="00:00:00~06:00:00" onchange="updateOverlayTimeRange(event)" /><label for="overlayChk1">0~6h</label>
                                                <input type="checkbox" id="overlayChk2" checked name="06:00:00~12:00:00" onchange="updateOverlayTimeRange(event)" /><label for="overlayChk2">6~12h</label>
                                                <input type="checkbox" id="overlayChk3" checked name="12:00:00~18:00:00" onchange="updateOverlayTimeRange(event)" /><label for="overlayChk3">12~18h</label>
                                                <input type="checkbox" id="overlayChk4" checked name="18:00:00~23:59:59" onchange="updateOverlayTimeRange(event)" /><label for="overlayChk4">18~24h</label>
                                            </div>

                                            <!-- 主体 splitter -->
                                            <div id="overlayChartPanel" class="mini-splitter"
                                                 style="width:100%; flex:1;" vertical="false">
                                                <div size="50%" showCollapseButton="true" minSize="200" collapseDirection="left">
                                                    <div style="display:flex; flex-direction:column; width:100%; height:100%; padding:2px; box-sizing:border-box; overflow-y:auto;">
                                                        <div style="flex-shrink:0; height:350px; padding:2px; box-sizing:border-box;"><div id="overlayFsChart" style="width:100%;height:100%;"></div></div>
                                                        <div style="flex-shrink:0; height:350px; padding:2px; box-sizing:border-box;"><div id="overlayPowerChart" style="width:100%;height:100%;"></div></div>
                                                        <div style="flex-shrink:0; height:350px; padding:2px; box-sizing:border-box;"><div id="overlayCurrentChart" style="width:100%;height:100%;"></div></div>
                                                    </div>
                                                </div>
                                                <div size="45%" showCollapseButton="true" minSize="150" collapseDirection="right">
                                                    <div style="height:100%; padding:4px; box-sizing:border-box;">
                                                        <div id="overlayDataGrid" class="mini-datagrid"
                                                             style="width:100%; height:100%;"
                                                             idField="id" showPager="false" allowResize="true"
                                                             multiSelect="true" showCheckColumn="true"
                                                             dataField="totalRoot" totalField="totalCount"
                                                             onload="onOverlayGridLoad"
                                                             onbeforeload="onOverlayGridBeforeLoad"
                                                             ondrawcell="onDeviceGridDrawCell"
                                                             onselect="onOverlayGridSelect"
                                                             ondeselect="onOverlayGridDeselect"
                                                             oncheckall="onOverlayGridCheckAll">
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

                </div>
            </div>
        </div>

        <!-- 底部一级标签 -->
        <div class="level1-footer" id="level1Footer"></div>
    </div>

    <script>
        $(document).ready(function () {
            mini.parse();
            setTimeout(function () {
                initHistoryQueryPage();
            }, 10);
        });
    </script>
</body>
</html>