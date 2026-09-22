<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*,com.cosog.model.User" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
User userLogin = (User)session.getAttribute("userLogin");
String userLoginNo = userLogin != null ? userLogin.getUserNo() + "" : "";
String loginUserLanguage = userLogin != null ? userLogin.getLanguageName() + "" : "zh_CN";

String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据字典配置</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/dataDictionary.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .dict-container {
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
        .dict-empty-msg { color: #999; font-size: 13px; text-align: center; padding: 20px; }

        /* ============================================================
           右侧：字典项面板
           ============================================================ */
        .dict-item-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
            overflow: hidden;
        }
        .dict-item-main {
            flex: 1;
            display: flex;
            overflow: hidden;
            order: 0;
        }
        /* 左侧二级标签（竖向） */
        .level2-sidebar {
            flex-shrink: 0;
            width: 32px;
            background: #f5f7fa;
            border-right: 1px solid #e8e8e8;
            overflow: auto;
            padding: 8px 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            align-self: stretch;
            box-sizing: border-box;
        }
        .level2-sidebar .tab-item {
            padding: 10px 2px;
            font-size: 12px;
            cursor: pointer;
            color: #555;
            background: transparent;
            border-left: 3px solid transparent;
            transition: all 0.15s;
            user-select: none;
            text-align: center;
            writing-mode: vertical-rl;
            letter-spacing: 2px;
            width: 100%;
            flex-shrink: 0;
            min-height: 36px;
            line-height: 1.4;
            box-sizing: border-box;
        }
        .level2-sidebar .tab-item:hover { background: #e8ecf0; color: #333; }
        .level2-sidebar .tab-item.active {
            background: #e6f7ff;
            color: #1890ff;
            font-weight: bold;
            border-left-color: #1890ff;
        }
        .level2-sidebar .no-child-tip {
            padding: 12px 0;
            color: #999;
            font-size: 12px;
            text-align: center;
            writing-mode: vertical-rl;
            letter-spacing: 2px;
        }
        /* 底部一级标签（横向） */
        .level1-footer {
            flex-shrink: 0;
            background: #fafafa;
            border-top: 1px solid #e0e0e0;
            padding: 0 10px;
            display: flex;
            align-items: center;
            gap: 2px;
            height: 36px;
            width: 100%;
            box-sizing: border-box;
            overflow-x: auto;
            overflow-y: hidden;
            order: 2;
        }
        .level1-footer .tab-item {
            padding: 4px 16px;
            font-size: 13px;
            cursor: pointer;
            color: #666;
            background: transparent;
            border-bottom: 2px solid transparent;
            transition: all 0.2s;
            user-select: none;
            white-space: nowrap;
        }
        .level1-footer .tab-item:hover { color: #333; }
        .level1-footer .tab-item.active {
            color: #2d6a9f;
            font-weight: bold;
            border-bottom-color: #2d6a9f;
        }
        .dict-item-area {
            flex: 1;
            overflow: hidden;
            background: #fff;
        }
    </style>
</head>
<body>

<div class="dict-container">
    <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
        
        <!-- ===== 左侧：数据字典模块列表 ===== -->
        <div size="45%" showCollapseButton="true" collapseDirection="left" minSize="300">
            <div id="sysDataModulePanel" class="mini-panel" 
                 style="width:100%;height:100%;"
                 showHeader="false" showToolbar="true" showCloseButton="false"
                 bodyStyle="padding:0;">
                <div property="toolbar">
                    <table style="width:100%;border-collapse:collapse;">
                        <tr>
                            <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                <button id="sysDataRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true" onclick="onSysDataRefresh()"></button>
                                <span id="sysDataLblType" style="font-size:12px;color:#333;"></span>
                                <input id="sysDataSearchType" class="mini-combobox" style="width:100px;" 
                                       valueField="id" textField="text" value="0" allowInput="false" />
                                <span id="sysDataLblName" style="margin-left:6px;font-size:12px;color:#333;"></span>
                                <input id="sysDataSearchName" class="mini-textbox" style="width:100px;" />
                                <button id="sysDataSearchBtn" class="mini-button" iconCls="search" plain="true" onclick="onSysDataSearch()"></button>
                            </td>
                            <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                <button id="sysDataAddBtn" class="mini-button" iconCls="add" plain="true" onclick="onSysDataAdd()"></button>
                                <button id="sysDataDelBtn" class="mini-button" iconCls="delete" plain="true" onclick="onSysDataDel()"></button>
                                <button id="sysDataSaveBtn" class="mini-button" iconCls="save" plain="true" onclick="onSysDataSave()"></button>
                                <button id="sysDataExportBtn" class="mini-button" iconCls="export" plain="true" onclick="onSysDataExport()"></button>
                                <button id="sysDataImportBtn" class="mini-button" iconCls="import" plain="true" onclick="onSysDataImport()"></button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="sysDataGrid" class="mini-datagrid"
                     style="width:100%;height:100%;"
                     idField="sysdataid"
                     allowResize="false"
                     allowAlternating="true"
                     showPager="false"
                     showPageInfo="false"
                     multiSelect="false"
                     allowCellEdit="false"
                     allowCellSelect="false"
                     showEmptyText="true"
                     dataField="totalRoot"
                     totalField="totalCount"
                     cellEditAction="celldblclick"
                     onbeforeload="onSysDataGridBeforeLoad"
                     onload="onSysDataGridLoad"
                     onselectionchanged="onSysDataGridSelectionChanged">
                    <div property="columns"></div>
                    <div property="emptyText" class="dict-empty-msg"></div>
                </div>
            </div>
        </div>

        <!-- ===== 右侧：字典项面板 ===== -->
        <div size="55%" showCollapseButton="true" collapseDirection="right" minSize="350">
            <div class="dict-item-container">
                <div class="dict-item-main">
                    <!-- 二级标签 -->
                    <div class="level2-sidebar" id="level2Sidebar">
                        <div class="no-child-tip" id="noChildTip">选择一级</div>
                    </div>
                    <!-- 字典项主体区 -->
                    <div class="dict-item-area">
                        <div id="dictItemPanel" class="mini-panel"
                             style="width:100%;height:100%;"
                             showHeader="false" showToolbar="true" showCloseButton="false"
                             bodyStyle="padding:0;">
                            <div property="toolbar">
                                <table style="width:100%;border-collapse:collapse;">
                                    <tr>
                                        <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                            <span id="dictItemLblType" style="font-size:12px;color:#333;"></span>
                                            <input id="dictItemSearchType" class="mini-combobox" style="width:120px;"
                                                   valueField="id" textField="text" value="0" allowInput="false" />
                                            <span id="dictItemLblName" style="margin-left:6px;font-size:12px;color:#333;"></span>
                                            <input id="dictItemSearchName" class="mini-textbox" style="width:120px;" />
                                            <button id="dictItemSearchBtn" class="mini-button" iconCls="search" plain="true"
                                                    onclick="onDictItemSearch()"></button>
                                        </td>
                                        <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                            <button id="dictItemAddBtn" class="mini-button" iconCls="add" plain="true"
                                                    onclick="onDictItemAdd()"></button>
                                            <button id="dictItemDelBtn" class="mini-button" iconCls="delete" plain="true"
                                                    onclick="onDictItemDel()"></button>
                                            <button id="dictItemSaveBtn" class="mini-button" iconCls="save" plain="true"
                                                    onclick="onDictItemSave()"></button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="dictItemGrid" class="mini-datagrid"
                                 style="width:100%;height:100%;"
                                 idField="dataitemid"
                                 allowResize="false"
                                 allowAlternating="true"
                                 showPager="false"
                                 showPageInfo="false"
                                 multiSelect="true"
                                 allowCellEdit="false"
                                 allowCellSelect="false"
                                 showEmptyText="true"
                                 dataField="totalRoot"
                                 totalField="totalCount"
                                 cellEditAction="celldblclick"
                                 onbeforeload="onDictItemGridBeforeLoad"
                                 onload="onDictItemGridLoad"
                                 oncellbeginedit="onDictItemGridCellBeginEdit">
                                <div property="columns"></div>
                                <div property="emptyText" class="dict-empty-msg"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 一级标签 -->
                <div class="level1-footer" id="level1Footer"></div>
            </div>
        </div>
        
    </div>
</div>

<script>
    var context = '<%=path%>';
    var user_ = '<%=userLoginNo%>';
    var loginUserLanguage = '<%=loginUserLanguage%>';
    
    $(document).ready(function () {
        mini.parse();
        setTimeout(function () {
            initDataDictionaryPage();
        }, 100);
    });
</script>
</body>
</html>