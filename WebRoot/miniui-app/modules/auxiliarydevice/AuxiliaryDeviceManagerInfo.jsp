<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>辅件设备</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/auxiliaryDeviceManagerInfo.js"></script>
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .auxiliary-device-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
        }
        .ad-main { flex: 1; display: flex; overflow: hidden; }
        .ad-content {
            flex: 1; overflow: hidden;
            background: #f0f2f5; padding: 4px;
            box-sizing: border-box;
        }

        /* 一级标签（底部） */
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

        /* Panel 去边框 */
        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; }
        .mini-panel-body { border: 0 !important; }
        .mini-panel-toolbar {
            background: #fafafa !important;
            border-bottom: 1px solid #e8e8e8 !important;
            padding: 4px 8px !important;
            box-sizing: border-box !important;
        }
        .mini-panel-toolbar > div { background: transparent !important; border: 0 !important; }

        /* 工具条 flex 布局 */
        .ad-toolbar {
            display: flex; align-items: center;
            width: 100%; min-height: 24px;
        }
        .ad-toolbar .ad-toolbar-right {
            margin-left: auto;
            display: flex; align-items: center; gap: 2px;
        }
        .ad-toolbar .ad-toolbar-sep {
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

        /* Handsontable 容器占位 */
        .hot-container { width: 100%; height: 100%; }
    </style>
</head>
<body>

<div class="auxiliary-device-container">
    <div class="ad-main">
        <div class="ad-content">

            <!-- 顶层 panel，承载工具条 + 主体 -->
            <div id="auxiliaryDevicePanel" class="mini-panel"
                 style="width:100%;height:100%;"
                 showHeader="false" showToolbar="true" showCloseButton="false"
                 bodyStyle="padding:0;">

                <!-- ============ 大工具条 ============ -->
                <div property="toolbar">
                    <div class="ad-toolbar">
                        <button id="adRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true"
                                onclick="onAdRefreshDeviceList()"></button>
                        <span class="ad-toolbar-sep"></span>
                        <span id="adDeviceTotalCountLabel" style="font-size:12px;color:#666;"></span>

                        <div class="ad-toolbar-right">
                            <button id="adAddDeviceBtn" class="mini-button" iconCls="add" plain="true"
                                    onclick="onAdAddDevice()"></button>
                            <button id="adDelDeviceBtn" class="mini-button" iconCls="delete" plain="true"
                                    onclick="onAdDelDevice()"></button>
                            <button id="adSaveDeviceBtn" class="mini-button" iconCls="save" plain="true"
                                    onclick="onAdSaveDevice()"></button>
                            <button id="adBatchAddDeviceBtn" class="mini-button" iconCls="batchAdd" plain="true"
                                    onclick="onAdBatchAddDevice()"></button>
                            <button id="adExportDeviceBtn" class="mini-button" iconCls="export" plain="true"
                                    onclick="onAdExportDevice()"></button>
                        </div>
                    </div>
                </div>

                <!-- ============ 主体：左右 Splitter ============ -->
                <div id="adMainSplitter" class="mini-splitter" style="width:100%;height:100%;" vertical="false">

                    <!-- 左：设备列表 -->
                    <div size="50%" showCollapseButton="false" minSize="300">
                        <div id="auxiliaryDeviceListPanel" class="mini-panel"
                             style="width:100%;height:100%;"
                             showHeader="true" showToolbar="false" showCloseButton="false"
                             bodyStyle="padding:0;overflow:hidden;">
                            <div id="AuxiliaryDeviceTableDiv_id" class="hot-container"></div>
                        </div>
                    </div>

                    <!-- 右：详细信息 + PRTF -->
                    <div size="50%" showCollapseButton="true" collapseDirection="right" minSize="300">
                        <div class="mini-splitter" id="adRightSplitter" style="width:100%;height:100%;" vertical="true">

                            <!-- 上：详细信息（含类型切换） -->
                            <div size="45%" showCollapseButton="false" minSize="150">
                                <div id="auxiliaryDeviceDetailsPanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div property="toolbar">
                                        <div class="ad-toolbar">
                                            <span id="adTypeLabel" style="font-size:12px;color:#333;margin-right:4px;"></span>
                                            <input id="AuxiliaryDeviceSpecificType_Id"
                                                   class="mini-radiobuttonlist"
                                                   onvaluechanged="onAdSpecificTypeChanged" />
                                        </div>
                                    </div>
                                    <div id="AuxiliaryDeviceDetailsTableDiv_id" class="hot-container"></div>
                                </div>
                            </div>

                            <!-- 下：抽油机 PRTF -->
                            <div size="55%" showCollapseButton="true" collapseDirection="bottom" minSize="150" visible="false">
                                <div id="auxiliaryDevicePRTFPanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="true" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div property="toolbar">
                                        <div class="ad-toolbar">
                                            <span id="adStrokeLabel" style="font-size:12px;color:#333;margin-right:4px;"></span>
                                            <input id="AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id"
                                                   class="mini-combobox"
                                                   style="width:150px;"
                                                   valueField="boxkey" textField="boxval"
                                                   allowInput="false"
                                                   onvaluechanged="onAdPRTFStrokeChanged" />
                                            <div class="ad-toolbar-right">
                                                <button id="adSavePRTFBtn" class="mini-button" iconCls="save" plain="true"
                                                        onclick="onAdSavePRTF()"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="AuxiliaryDevicePumpingUnitPRTFTableDiv_id" class="hot-container"></div>
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
    var context = '<%=context%>';

    $(document).ready(function () {
        mini.parse();
        setTimeout(function () {
            initAuxiliaryDeviceManagerPage();
        }, 100);
    });
</script>
</body>
</html>