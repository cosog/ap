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
    <title>水文井报表</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/hydrologicalWellReport.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .hywr-container { width:100%; height:100%; display:flex; flex-direction:column;
            background:#fff; overflow:hidden; }
        .mini-panel { border:0 !important; }
        .mini-panel-border { border:0 !important; }
        .mini-panel-header { border-bottom:1px solid #e8e8e8 !important; }
        .mini-panel-body { border:0 !important; padding:0 !important; overflow:hidden !important; }
        .mini-panel-toolbar { background:#fafafa !important;
            border-bottom:1px solid #e8e8e8 !important;
            padding:4px 8px !important; box-sizing:border-box !important; }
        .mini-panel-toolbar > div { background:transparent !important; border:0 !important; }
        .mini-splitter-border { border:0 !important; }
        .mini-splitter-pane { padding:0 !important; border:0 !important; }
        .mini-splitter-handler { background:transparent !important; border:1px solid #e8e8e8 !important; }
        .empty-msg { color:#999; font-size:13px; text-align:center; padding:20px; }
        .hot-container { width:100%; height:100%; }
        .toolbar-lbl { font-size:12px; color:#333; margin-left:6px; }
        .toolbar-count { margin-left:12px; font-size:12px; color:#333; }
    </style>
</head>
<body>

<div class="hywr-container">

    <div id="hywrMainPanel" class="mini-panel"
         style="width:100%;height:100%;"
         showHeader="false" showToolbar="true" showCloseButton="false"
         bodyStyle="padding:0;overflow:hidden;">

        <div property="toolbar">
            <table style="width:100%;border-collapse:collapse;">
                <tr>
                    <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                        <button id="hywrRefreshBtn" class="mini-button"
                                iconCls="note-refresh" plain="true"
                                onclick="onHywrRefresh()"></button>
                        <span id="hywrLblDevice" class="toolbar-lbl"></span>
                        <input id="hywrDeviceCombo" class="mini-combobox"
                               style="width:170px;"
                               url="<%=path%>/reportDataMamagerController/loadHydrologicalWellReportDeviceComboxList"
                               dataField="list" valueField="boxkey" textField="boxval"
                               onbeforeload="onHywrDeviceComboBeforeLoad"
                               onshowpopup="onHywrDeviceComboShowPopup"
                               onvaluechanged="onHywrDeviceComboChange" />
                        <span id="hywrLblDate" class="toolbar-lbl"></span>
                        <input id="hywrStartDate" class="mini-datepicker" style="width:150px;"
                               format="yyyy-MM-dd" showOkButton="true" showClearButton="false"
                               allowInput="false"
                               onvaluechanged="onHywrRangeDateChanged" />
                        <span id="hywrLblTimeTo" class="toolbar-lbl"></span>
                        <input id="hywrEndDate" class="mini-datepicker" style="width:150px;"
                               format="yyyy-MM-dd" showOkButton="true" showClearButton="false"
                               allowInput="false"
                               onvaluechanged="onHywrRangeDateChanged" />
                        <button id="hywrSearchBtn" class="mini-button"
                                iconCls="search" plain="true"
                                onclick="onHywrSearch()"></button>
                        <button id="hywrBatchExportBtn" class="mini-button"
                                iconCls="export" plain="true"
                                onclick="onHywrBatchExport()"></button>
                    </td>
                </tr>
            </table>
        </div>

        <div id="hywrMainSplitter" class="mini-splitter"
             style="width:100%;height:100%;" vertical="false" handlerSize="6">

            <div size="20%" showCollapseButton="true"
                 collapseDirection="left" minSize="180">
                <div id="hywrDeviceListPanel" class="mini-panel"
                     style="width:100%;height:100%;"
                     showHeader="true" showToolbar="false" showCloseButton="false"
                     bodyStyle="padding:0;overflow:hidden;">
                    <div id="hywrDeviceGrid" class="mini-datagrid"
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
                         onbeforeload="onHywrDeviceGridBeforeLoad"
                         onload="onHywrDeviceGridLoad"
                         onselectionchanged="onHywrDeviceGridSelectionChanged">
                        <div property="columns"></div>
                    </div>
                </div>
            </div>

            <div size="80%" showCollapseButton="false">
                <div id="hywrTabs" class="mini-tabs"
                     style="width:100%;height:100%;" tabPosition="top" activeIndex="0"
                     onactivechanged="onHywrTabChanged">

                    <div id="hywrTab1" title="五分钟" name="t1">
                        <div class="mini-splitter" style="width:100%;height:100%;"
                             vertical="false" handlerSize="6">
                            <div size="40%" showCollapseButton="true"
                                 collapseDirection="left" minSize="250">
                                <div id="hywrDataPanel1" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div property="toolbar">
                                        <table style="width:100%;border-collapse:collapse;">
                                            <tr>
                                                <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                                    <button id="hywrFwdBtn1" class="mini-button"
                                                            iconCls="forward" plain="true"
                                                            onclick="onHywrForward(1)"></button>
                                                    <input id="hywrReportDate1" class="mini-datepicker"
                                                           style="width:130px;"
                                                           format="yyyy-MM-dd"
                                                           allowInput="false" enabled="false"
                                                           onvaluechanged="onHywrReportDateChanged1" />
                                                    <button id="hywrBackBtn1" class="mini-button"
                                                            iconCls="backwards" plain="true"
                                                            onclick="onHywrBack(1)"></button>
                                                </td>
                                                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                    <button id="hywrExportBtn1" class="mini-button"
                                                            iconCls="export" plain="true"
                                                            onclick="onHywrExport(1)"></button>
                                                    <button id="hywrSaveBtn1" class="mini-button"
                                                            iconCls="save" plain="true"
                                                            onclick="onHywrSave(1)"></button>
                                                    <span id="hywrTotalCount1" class="toolbar-count"></span>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="hywrDataDiv1" class="hot-container"></div>
                                </div>
                            </div>
                            <div showCollapseButton="false">
                                <div id="hywrCurvePanel1" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="hywrCurveDiv1" class="hot-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="hywrTab2" title="一小时" name="t2">
                        <div class="mini-splitter" style="width:100%;height:100%;"
                             vertical="false" handlerSize="6">
                            <div size="40%" showCollapseButton="true"
                                 collapseDirection="left" minSize="250">
                                <div id="hywrDataPanel2" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div property="toolbar">
                                        <table style="width:100%;border-collapse:collapse;">
                                            <tr>
                                                <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                                    <button id="hywrFwdBtn2" class="mini-button"
                                                            iconCls="forward" plain="true"
                                                            onclick="onHywrForward(2)"></button>
                                                    <input id="hywrReportDate2" class="mini-datepicker"
                                                           style="width:130px;"
                                                           format="yyyy-MM-dd"
                                                           allowInput="false" enabled="false"
                                                           onvaluechanged="onHywrReportDateChanged2" />
                                                    <button id="hywrBackBtn2" class="mini-button"
                                                            iconCls="backwards" plain="true"
                                                            onclick="onHywrBack(2)"></button>
                                                </td>
                                                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                    <button id="hywrExportBtn2" class="mini-button"
                                                            iconCls="export" plain="true"
                                                            onclick="onHywrExport(2)"></button>
                                                    <button id="hywrSaveBtn2" class="mini-button"
                                                            iconCls="save" plain="true"
                                                            onclick="onHywrSave(2)"></button>
                                                    <span id="hywrTotalCount2" class="toolbar-count"></span>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="hywrDataDiv2" class="hot-container"></div>
                                </div>
                            </div>
                            <div showCollapseButton="false">
                                <div id="hywrCurvePanel2" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="hywrCurveDiv2" class="hot-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="hywrTab3" title="六小时" name="t3">
                        <div class="mini-splitter" style="width:100%;height:100%;"
                             vertical="false" handlerSize="6">
                            <div size="40%" showCollapseButton="true"
                                 collapseDirection="left" minSize="250">
                                <div id="hywrDataPanel3" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div property="toolbar">
                                        <table style="width:100%;border-collapse:collapse;">
                                            <tr>
                                                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                    <button id="hywrExportBtn3" class="mini-button"
                                                            iconCls="export" plain="true"
                                                            onclick="onHywrExport(3)"></button>
                                                    <button id="hywrSaveBtn3" class="mini-button"
                                                            iconCls="save" plain="true"
                                                            onclick="onHywrSave(3)"></button>
                                                    <span id="hywrTotalCount3" class="toolbar-count"></span>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="hywrDataDiv3" class="hot-container"></div>
                                </div>
                            </div>
                            <div showCollapseButton="false">
                                <div id="hywrCurvePanel3" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="hywrCurveDiv3" class="hot-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="hywrTab4" title="十二小时" name="t4">
                        <div class="mini-splitter" style="width:100%;height:100%;"
                             vertical="false" handlerSize="6">
                            <div size="40%" showCollapseButton="true"
                                 collapseDirection="left" minSize="250">
                                <div id="hywrDataPanel4" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div property="toolbar">
                                        <table style="width:100%;border-collapse:collapse;">
                                            <tr>
                                                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                    <button id="hywrExportBtn4" class="mini-button"
                                                            iconCls="export" plain="true"
                                                            onclick="onHywrExport(4)"></button>
                                                    <button id="hywrSaveBtn4" class="mini-button"
                                                            iconCls="save" plain="true"
                                                            onclick="onHywrSave(4)"></button>
                                                    <span id="hywrTotalCount4" class="toolbar-count"></span>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="hywrDataDiv4" class="hot-container"></div>
                                </div>
                            </div>
                            <div showCollapseButton="false">
                                <div id="hywrCurvePanel4" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="hywrCurveDiv4" class="hot-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="hywrTab5" title="二十四小时" name="t5">
                        <div class="mini-splitter" style="width:100%;height:100%;"
                             vertical="false" handlerSize="6">
                            <div size="40%" showCollapseButton="true"
                                 collapseDirection="left" minSize="250">
                                <div id="hywrDataPanel5" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div property="toolbar">
                                        <table style="width:100%;border-collapse:collapse;">
                                            <tr>
                                                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                    <button id="hywrExportBtn5" class="mini-button"
                                                            iconCls="export" plain="true"
                                                            onclick="onHywrExport(5)"></button>
                                                    <button id="hywrSaveBtn5" class="mini-button"
                                                            iconCls="save" plain="true"
                                                            onclick="onHywrSave(5)"></button>
                                                    <span id="hywrTotalCount5" class="toolbar-count"></span>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="hywrDataDiv5" class="hot-container"></div>
                                </div>
                            </div>
                            <div showCollapseButton="false">
                                <div id="hywrCurvePanel5" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="hywrCurveDiv5" class="hot-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>

</div>

<script>
    var context = '<%=path%>';
    var user_ = '<%=loginUserNo%>';
    var loginUserLanguage = '<%=loginUserLanguage%>';

    $(document).ready(function () {
        mini.parse();
        setTimeout(function () {
            initHydrologicalWellReportPage();
        }, 100);
    });
</script>
</body>
</html>