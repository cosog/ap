<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cosog.model.User" %>
<%
String path = request.getContextPath();
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
    <title>生产报表</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/productionReport.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .report-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff; overflow: hidden;
        }
        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; }
        .mini-panel-body { border: 0 !important; padding: 0 !important; overflow: hidden !important; }
        .mini-panel-toolbar {
            background: #fafafa !important;
            border-bottom: 1px solid #e8e8e8 !important;
            padding: 4px 8px !important;
            box-sizing: border-box !important;
        }
        .mini-panel-toolbar > div { background: transparent !important; border: 0 !important; }
        .mini-splitter-border { border: 0 !important; }
        .mini-splitter-pane { padding: 0 !important; border: 0 !important; }
        .mini-splitter-handler { background: transparent !important; border: 1px solid #e8e8e8 !important; }
        .mini-tabs-buttons-left { background: #fafafa; }

        .report-main { flex: 1; display: flex; overflow: hidden; }

        .level2-sidebar {
            flex-shrink: 0;
            width: 32px;
            background: #f5f7fa;
            border-right: 1px solid #e8e8e8;
            overflow: auto;
            padding: 8px 0;
            display: flex; flex-direction: column;
            align-items: center; justify-content: flex-start;
            align-self: stretch;
            box-sizing: border-box;
        }
        .level2-sidebar.hidden { display: none; }
        .level2-sidebar .tab-item {
            padding: 10px 2px;
            font-size: 12px; cursor: pointer;
            color: #555; background: transparent;
            border-left: 3px solid transparent;
            transition: all 0.15s; user-select: none;
            text-align: center;
            writing-mode: vertical-rl;
            letter-spacing: 2px;
            width: 100%; flex-shrink: 0;
            min-height: 36px; line-height: 1.4;
            box-sizing: border-box;
        }
        .level2-sidebar .tab-item:hover { background: #e8ecf0; color: #333; }
        .level2-sidebar .tab-item.active {
            background: #e6f7ff; color: #1890ff;
            font-weight: bold;
            border-left-color: #1890ff;
        }
        .level2-sidebar .no-child-tip {
            padding: 12px 0; color: #999; font-size: 12px;
            text-align: center;
            writing-mode: vertical-rl;
            letter-spacing: 2px;
        }
        .level1-footer {
            flex-shrink: 0;
            background: #fafafa;
            border-top: 1px solid #e0e0e0;
            padding: 0 10px;
            display: flex; align-items: center;
            gap: 2px; height: 36px; width: 100%;
            box-sizing: border-box;
            overflow-x: auto; overflow-y: hidden;
        }
        .level1-footer .tab-item {
            padding: 4px 16px;
            font-size: 13px; cursor: pointer;
            color: #666; background: transparent;
            border-bottom: 2px solid transparent;
            transition: all 0.2s; user-select: none;
            white-space: nowrap;
        }
        .level1-footer .tab-item:hover { color: #333; }
        .level1-footer .tab-item.active {
            color: #2d6a9f; font-weight: bold;
            border-bottom-color: #2d6a9f;
        }
        .report-content { flex: 1; overflow: hidden; background: #fff; }
        .empty-msg { color: #999; font-size: 13px; text-align: center; padding: 20px; }
        .hot-container { width: 100%; height: 100%; }
        .toolbar-lbl { font-size: 12px; color: #333; margin-left: 6px; }
        .toolbar-count { margin-left: 12px; font-size: 12px; color: #333; }
    </style>
</head>
<body>

<div class="report-container">

    <div class="report-main">

        <div class="level2-sidebar" id="reportLevel2Sidebar"></div>

        <div class="report-content">

            <div id="dailyReportInnerTabs" class="mini-tabs"
                 style="width:100%;height:100%;" tabPosition="left" activeIndex="0"
                 onactivechanged="onDailyReportInnerTabChanged">

                <!-- ============================================================
                     1. 单井日报
                     ============================================================ -->
                <div id="singleWellTab" title="单井报表" name="singleWell">
                    <div class="mini-panel" style="width:100%;height:100%;"
                         showHeader="false" showToolbar="true" showCloseButton="false"
                         bodyStyle="padding:0;overflow:hidden;">
                        <div property="toolbar">
                            <table style="width:100%;border-collapse:collapse;">
                                <tr>
                                    <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                        <button id="swRefreshBtn" class="mini-button"
                                                iconCls="note-refresh" plain="true" onclick="onSwRefresh()"></button>
                                        <span id="swLblDevice" class="toolbar-lbl"></span>
                                        <!-- ★ 设备下拉框：保留 onshowpopup，完全照抄设备管理模块 -->
                                        <input id="swDeviceCombo" class="mini-combobox"
                                               style="width:170px;"
                                               url="<%=path%>/wellInformationManagerController/loadWellComboxList"
                                               dataField="list" valueField="boxkey" textField="boxval"
                                               onbeforeload="onSwDeviceComboBeforeLoad"
                                               onshowpopup="onSwDeviceComboShowPopup"
                                               onvaluechanged="onSwDeviceComboChange" />
                                        <span id="swLblDate" class="toolbar-lbl"></span>
                                        <input id="swStartDate" class="mini-datepicker" style="width:150px;"
                                               format="yyyy-MM-dd" showOkButton="true" showClearButton="false"
                                               allowInput="false" onvaluechanged="onSwRangeDateChanged" />
                                        <span id="swLblTimeTo" class="toolbar-lbl"></span>
                                        <input id="swEndDate" class="mini-datepicker" style="width:150px;"
                                               format="yyyy-MM-dd" showOkButton="true" showClearButton="false"
                                               allowInput="false" onvaluechanged="onSwRangeDateChanged" />
                                        <button id="swSearchBtn" class="mini-button" iconCls="search" plain="true" onclick="onSwSearch()"></button>
                                        <button id="swBatchExportBtn" class="mini-button" iconCls="export" plain="true" onclick="onSwBatchExport()"></button>
                                    </td>
                                </tr>
                            </table>
                        </div>

                        <div class="mini-splitter" style="width:100%;height:100%;"
                             vertical="false" handlerSize="6">
                            <div size="20%" showCollapseButton="true"
                                 collapseDirection="left" minSize="180">
                                <div id="swDeviceListPanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <!-- ★ 去掉 <div property="emptyText">，改用 JS setEmptyText -->
                                    <div id="swDeviceGrid" class="mini-datagrid"
                                         style="width:100%;height:100%;"
                                         idField="id"
                                         allowResize="false"
                                         allowAlternating="true"
                                         showPager="false"
                                         showPageInfo="false"
                                         multiSelect="false"
                                         showEmptyText="true"
                                         dataField="totalRoot"
                                         totalField="totalCount"
                                         onbeforeload="onSwDeviceGridBeforeLoad"
                                         onload="onSwDeviceGridLoad"
                                         onselectionchanged="onSwDeviceGridSelectionChanged">
                                        <div property="columns"></div>
                                    </div>
                                </div>
                            </div>

                            <div size="80%" showCollapseButton="false">
                                <div id="swInnerTabs" class="mini-tabs"
                                     style="width:100%;height:100%;" tabPosition="top" activeIndex="0"
                                     onactivechanged="onSWInnerTabsTabChanged" >

                                    <!-- 班报表 -->
                                    <div id="swHourlyTab" title="班报表" name="hourly">
                                        <div class="mini-splitter" style="width:100%;height:100%;"
                                             vertical="true" handlerSize="6">
                                            <div size="50%" showCollapseButton="true" minSize="180">
                                                <div id="swHourlyCurvePanel" class="mini-panel"
                                                     style="width:100%;height:100%;"
                                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                                     bodyStyle="padding:0;overflow:hidden;">
                                                    <div id="swHourlyCurveDiv" class="hot-container"></div>
                                                </div>
                                            </div>
                                            <div showCollapseButton="false">
                                                <div id="swHourlyDataPanel" class="mini-panel"
                                                     style="width:100%;height:100%;"
                                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                                     bodyStyle="padding:0;overflow:hidden;">
                                                    <div property="toolbar">
                                                        <table style="width:100%;border-collapse:collapse;">
                                                            <tr>
                                                                <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                                                    <button id="swHourlyForwardBtn" class="mini-button"
                                                                            iconCls="forward" plain="true"
                                                                            onclick="onSwHourlyForward()"></button>
                                                                    <!-- ★ 对照 ExtJS readOnly:true → enabled="false" -->
                                                                    <input id="swHourlyReportDate" class="mini-datepicker"
                                                                           style="width:130px;"
                                                                           format="yyyy-MM-dd" allowInput="false"
                                                                           enabled="false" onvaluechanged="onSwHourlyReportDateChanged" />
                                                                    <button id="swHourlyBackBtn" class="mini-button"
                                                                            iconCls="backwards" plain="true"
                                                                            onclick="onSwHourlyBack()"></button>
                                                                    <span id="swHourlyLblInterval" class="toolbar-lbl"></span>
                                                                    <input id="swHourlyInterval" class="mini-combobox"
                                                                           style="width:110px;"
                                                                           valueField="id" textField="text"
                                                                           allowInput="false" onvaluechanged="onSwHourlyIntervalChanged" />
                                                                </td>
                                                                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                                    <button id="swHourlyExportBtn" class="mini-button"
                                                                            iconCls="export" plain="true"
                                                                            onclick="onSwHourlyExport()"></button>
                                                                    <button id="swHourlySaveBtn" class="mini-button"
                                                                            iconCls="save" plain="true"
                                                                            onclick="onSwHourlySave()"></button>
                                                                    <span id="swHourlyTotalCount" class="toolbar-count"></span>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    <div id="swHourlyDataDiv" class="hot-container"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- 日报 -->
                                    <div id="swRangeTab" title="日报" name="range">
                                        <div class="mini-splitter" style="width:100%;height:100%;"
                                             vertical="true" handlerSize="6">
                                            <div size="50%" showCollapseButton="true" minSize="180">
                                                <div id="swRangeCurvePanel" class="mini-panel"
                                                     style="width:100%;height:100%;"
                                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                                     bodyStyle="padding:0;overflow:hidden;">
                                                    <div id="swRangeCurveDiv" class="hot-container"></div>
                                                </div>
                                            </div>
                                            <div showCollapseButton="false">
                                                <div id="swRangeDataPanel" class="mini-panel"
                                                     style="width:100%;height:100%;"
                                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                                     bodyStyle="padding:0;overflow:hidden;">
                                                    <div property="toolbar">
                                                        <table style="width:100%;border-collapse:collapse;">
                                                            <tr>
                                                                <td style="padding:0;vertical-align:middle;white-space:nowrap;"></td>
                                                                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                                    <button id="swRangeExportBtn" class="mini-button"
                                                                            iconCls="export" plain="true"
                                                                            onclick="onSwRangeExport()"></button>
                                                                    <button id="swRangeSaveBtn" class="mini-button"
                                                                            iconCls="save" plain="true"
                                                                            onclick="onSwRangeSave()"></button>
                                                                    <span id="swRangeTotalCount" class="toolbar-count"></span>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    <div id="swRangeDataDiv" class="hot-container"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- ============================================================
                     2. 区域日报
                     ============================================================ -->
                <div id="areaTab" title="区域报表" name="area">
                    <div class="mini-panel" style="width:100%;height:100%;"
                         showHeader="false" showToolbar="true" showCloseButton="false"
                         bodyStyle="padding:0;overflow:hidden;">
                        <div property="toolbar">
                            <table style="width:100%;border-collapse:collapse;">
                                <tr>
                                    <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                        <button id="pdRefreshBtn" class="mini-button"
                                                iconCls="note-refresh" plain="true" onclick="onPdRefresh()"></button>
                                        <input id="pdWellCombo" class="mini-combobox"
                                               style="display:none;" valueField="boxkey" textField="boxval"
                                               allowInput="false" />
                                        <span id="pdLblDate" class="toolbar-lbl"></span>
                                        <input id="pdStartDate" class="mini-datepicker" style="width:150px;"
                                               format="yyyy-MM-dd" showOkButton="true" showClearButton="false"
                                               allowInput="false" onvaluechanged="onPdRangeDateChanged" />
                                        <span id="pdLblTimeTo" class="toolbar-lbl"></span>
                                        <input id="pdEndDate" class="mini-datepicker" style="width:150px;"
                                               format="yyyy-MM-dd" showOkButton="true" showClearButton="false"
                                               allowInput="false" onvaluechanged="onPdRangeDateChanged"/>
                                        <button id="pdSearchBtn" class="mini-button"
                                                iconCls="search" plain="true" onclick="onPdSearch()"></button>
                                    </td>
                                </tr>
                            </table>
                        </div>

                        <div class="mini-splitter" id="pdMainSplitter" style="width:100%;height:100%;"
                             vertical="false" handlerSize="6">
                            <div size="20%" showCollapseButton="true"
                                 collapseDirection="left" minSize="180">
                                <div id="pdInstancePanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="pdInstanceGrid" class="mini-datagrid"
						                 style="width:100%;height:100%;"
						                 idField="instanceCode"
						                 allowResize="false"
						                 allowAlternating="true"
						                 showPager="false"
						                 showPageInfo="false"
						                 multiSelect="false"
						                 showEmptyText="true"
						                 dataField="totalRoot"
						                 totalField="totalCount"
						                 onbeforeload="onPdInstanceGridBeforeLoad"
						                 onload="onPdInstanceGridLoad"
						                 onselectionchanged="onPdInstanceGridSelectionChanged">
						                <div property="columns"></div>
						            </div>
                                </div>
                            </div>

                            <div size="80%" showCollapseButton="false">
                                <div id="pdInnerTabs" class="mini-tabs"
                                     style="width:100%;height:100%;" tabPosition="top" activeIndex="0">
                                    <div id="pdRangeTab" title="日报" name="range">
                                        <div class="mini-splitter" style="width:100%;height:100%;"
                                             vertical="true" handlerSize="6">
                                            <div size="50%" showCollapseButton="true" minSize="180">
                                                <div id="pdCurvePanel" class="mini-panel"
                                                     style="width:100%;height:100%;"
                                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                                     bodyStyle="padding:0;overflow:hidden;">
                                                    <div id="pdCurveDiv" class="hot-container"></div>
                                                </div>
                                            </div>
                                            <div showCollapseButton="false">
                                                <div id="pdDataPanel" class="mini-panel"
                                                     style="width:100%;height:100%;"
                                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                                     bodyStyle="padding:0;overflow:hidden;">
                                                    <div property="toolbar">
                                                        <table style="width:100%;border-collapse:collapse;">
                                                            <tr>
                                                                <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                                                    <button id="pdForwardBtn" class="mini-button"
                                                                            iconCls="forward" plain="true"
                                                                            onclick="onPdForward()"></button>
                                                                    <!-- ★ 对照 ExtJS readOnly:true → enabled="false" -->
                                                                    <input id="pdReportDate" class="mini-datepicker"
                                                                           style="width:130px;"
                                                                           format="yyyy-MM-dd" allowInput="false"
                                                                           enabled="false" onvaluechanged="onPdReportDateChanged"/>
                                                                    <button id="pdBackBtn" class="mini-button"
                                                                            iconCls="backwards" plain="true"
                                                                            onclick="onPdBack()"></button>
                                                                </td>
                                                                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                                    <button id="pdExportBtn" class="mini-button"
                                                                            iconCls="export" plain="true"
                                                                            onclick="onPdExport()"></button>
                                                                    <button id="pdBatchExportBtn" class="mini-button"
                                                                            iconCls="export" plain="true"
                                                                            onclick="onPdBatchExport()"></button>
                                                                    <button id="pdSaveBtn" class="mini-button"
                                                                            iconCls="save" plain="true"
                                                                            onclick="onPdSave()"></button>
                                                                    <span id="pdTotalCount" class="toolbar-count"></span>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    <div id="pdDataDiv" class="hot-container"></div>
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

    <div class="level1-footer" id="reportLevel1Footer"></div>
</div>

<script>
    var context = '<%=path%>';
    var user_ = '<%=loginUserNo%>';
    var loginUserLanguage = '<%=loginUserLanguage%>';

    $(document).ready(function () {
        mini.parse();
        setTimeout(function () {
            initProductionReportPage();
        }, 100);
    });
</script>
</body>
</html>