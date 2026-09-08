<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
otherStaticResourceTimestamp=System.currentTimeMillis()+"";
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>驱动配置</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/common/handsontableHelpers.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <script src="js/modules/protocolConfig.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <script src="js/modules/acqUnitConfig.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <script src="js/modules/displayUnitConfig.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        /* ===== 全局样式 ===== */
        html,
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }

        .driver-container {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #fff;
        }

        .driver-container .mini-splitter {
            flex: 1;
        }

        .left-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #f0f2f5;
            padding: 4px;
        }

        .left-panel .tree-area {
            flex: 1;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
            overflow: hidden;
            display: flex;
            flex-direction: column;
        }

        .left-panel .tree-area .mini-tree {
            flex: 1;
            width: 100%;
            height: 100%;
        }

        .right-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #f0f2f5;
            padding: 4px;
        }

        .right-panel .mini-tabs {
            flex: 1;
            width: 100%;
            height: 100%;
        }

        .right-panel .mini-tabs-body,
        .right-panel .mini-tab-body {
            height: 100% !important;
            padding: 0 !important;
            margin: 0 !important;
            overflow: hidden !important;
        }

        .tab-content-layout {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #fff;
        }

        .tab-content-layout .mini-toolbar {
            flex-shrink: 0;
            border-bottom: 1px solid #e8e8e8;
            padding: 4px 8px;
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 4px;
            background: #fafafa;
        }

        .sub-tab-placeholder {
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #ccc;
            font-size: 14px;
        }

        .mini-toolbar .separator {
            width: 1px;
            height: 20px;
            background: #ddd;
            margin: 0 4px;
        }

        .empty-msg {
            color: #999;
            font-size: 13px;
            text-align: center;
            padding: 20px;
        }

        .mini-tabs-body {
            overflow: hidden !important;
        }

        .inner-toolbar {
            border-bottom: 1px solid #e8e8e8;
            padding: 2px 8px;
            display: flex;
            align-items: center;
            gap: 4px;
            flex-shrink: 0;
            background: #fafafa;
        }

        .mini-tree .tree-node {
            cursor: pointer;
        }

        .mini-tree .tree-node-selected {
            background: #e6f7ff;
            color: #1890ff;
            font-weight: bold;
        }

        /* 单元/实例三栏通用布局 */
        .unit-layout,
        .instance-layout {
            display: flex;
            flex: 1;
            overflow: hidden;
        }

        .unit-layout .left-protocol,
        .instance-layout .left-protocol {
            width: 25%;
            border-right: 1px solid #e8e8e8;
            overflow: auto;
            padding: 4px;
            background: #fafafa;
        }

        .unit-layout .middle-list,
        .instance-layout .middle-list {
            width: 30%;
            border-right: 1px solid #e8e8e8;
            overflow: auto;
            padding: 4px;
            background: #fafafa;
        }

        .unit-layout .right-config,
        .instance-layout .right-property {
            flex: 1;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            padding: 4px;
            background: #fff;
        }

        .unit-layout .right-config .mini-tabs,
        .instance-layout .right-property .mini-tabs {
            flex: 1;
            width: 100%;
            height: 100%;
        }

        .alarm-config-layout {
            display: flex;
            flex: 1;
            overflow: hidden;
        }

        .alarm-config-layout .alarm-left-list {
            width: 40%;
            border-right: 1px solid #e8e8e8;
            overflow: auto;
            padding: 4px;
            background: #fafafa;
        }

        .alarm-config-layout .alarm-right-detail {
            flex: 1;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            padding: 4px;
            background: #fff;
        }

        .alarm-config-layout .alarm-right-detail .mini-tabs {
            flex: 1;
            width: 100%;
            height: 100%;
        }

        .report-config-layout {
            display: flex;
            flex: 1;
            overflow: hidden;
        }

        .report-config-layout .report-left-list {
            width: 30%;
            border-right: 1px solid #e8e8e8;
            overflow: auto;
            padding: 4px;
            background: #fafafa;
        }

        .report-config-layout .report-right-detail {
            flex: 1;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            padding: 4px;
            background: #fff;
        }

        .unit-layout .right-config .mini-tabs-body,
        .unit-layout .right-config .mini-tab-body,
        .instance-layout .right-property .mini-tabs-body,
        .instance-layout .right-property .mini-tab-body {
            height: 100% !important;
            padding: 0 !important;
            margin: 0 !important;
            overflow: hidden !important;
        }

    </style>
</head>

<body>
    <div class="driver-container">
        <div class="mini-splitter" style="width:100%; height:100%;" vertical="false">
            <!-- 左侧区域（设备类型树） -->
            <div size="15%" showCollapseButton="true" minSize="200" collapseDirection="left">
                <div class="left-panel" style="height:100%;">
                    <div class="tree-area">
                        <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;flex-shrink:0;">
                            <span id="deviceTypeTitle">Device Type</span>
                        </div>
                        <div id="deviceTypeTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="deviceTypeId" textField="text" parentField="parentId" resultAsTree="true" onnodeselect="onDeviceTypeSelect" onload="onDeviceTypeTreeLoad">
                            <div property="emptyText" class="empty-msg">Empty</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 右侧主Tabs -->
            <div size="85%" showCollapseButton="false" minSize="300">
                <div class="right-panel" style="height:100%;">
                    <div id="mainTabs" class="mini-tabs" style="height:100%; width:100%; overflow:hidden;" activeIndex="0" tabPosition="bottom" onactivechanged="onMainTabChanged">
                        <!-- ===================== 协议配置 ===================== -->
                        <div title="Protocol" name="protocol" style="height:100%;">
                            <div class="tab-content-layout" style="height:100%;">
                                <div class="mini-toolbar">
                                    <button id="protocolRefreshBtn" class="mini-button" iconCls="note-refresh" onclick="loadProtocolTree()">Refresh</button>
                                    <span style="flex:1;"></span>
                                    <button id="protocolAddBtn" class="mini-button" iconCls="add" onclick="addProtocolData()">Add</button>
                                    <button id="protocolSaveBtn" class="mini-button" iconCls="save" onclick="saveProtocolConfigData()">Save</button>
                                    <button id="protocolMappingBtn" class="mini-button" onclick="openFieldMappingWindow()">Mapping</button>
                                    <button id="protocolExportBtn" class="mini-button" iconCls="export" onclick="openExportProtocolWindow()">Export</button>
                                    <button id="protocolImportBtn" class="mini-button" iconCls="import" onclick="openImportProtocolWindow()">Import</button>
                                    <button id="protocolDeviceTypeChangeBtn" class="mini-button" iconCls="move" onclick="openProtocolDeviceTypeChangeWindow()">Move</button>
                                    <span id="protocolInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                    <input type="hidden" id="ModbusProtocolAddrMappingItemsSelectRow_Id" value="0" />
                                    <input type="hidden" id="ProtocolExtendedFieldHighLowByteSelectRow_Id" value="0" />
                                </div>
                                <div style="flex:1;overflow:hidden;">
                                    <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                                        <!-- 左侧协议树 17% -->
                                        <div size="17%" showCollapseButton="true" collapseDirection="left" minSize="150">
                                            <div style="padding:4px;height:100%;background:#fafafa;">
                                                <div id="protocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true" onload="onProtocolTreeLoad" onbeforeload="onProtocolTreeBeforeLoad" onnodeselect="onProtocolNodeSelect" contextMenu="#protocolTreeMenu">
                                                    <div property="emptyText" class="empty-msg">No Protocol</div>
                                                </div>
                                                <!-- 协议树右键菜单 -->
                                                <ul id="protocolTreeMenu" class="mini-contextmenu" onbeforeopen="onProtocolTreeBeforeMenu">
                                                    <li name="delete" iconCls="delete" onclick="deleteProtocolNode">
                                                        <span id="protocolTreeMenuDeleteText">删除</span>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <!-- 右侧详情 83% -->
                                        <div size="83%" showCollapseButton="false">
                                            <div style="height:100%;padding:4px;background:#fff;">
                                                <div id="protocolSubTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="1" onactivechanged="onProtocolSubTabChanged">
                                                    <!-- 属性 -->
                                                    <div title="Properties" name="props" style="height:100%;">
                                                        <div style="width:100%;height:100%;overflow:hidden;padding:4px;">
                                                            <div id="ModbusProtocolAddrMappingPropertiesTableInfoDiv_id" style="width:100%;height:100%;"></div>
                                                        </div>
                                                    </div>
                                                    <!-- 配置 -->
                                                    <div title="Config" name="config" style="height:100%;">
                                                        <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                                                            <div size="80%" showCollapseButton="false">
                                                                <div style="padding:4px;height:100%;background:#fafafa;">
                                                                    <div id="protocolItemsConfigContainer" style="width:100%;height:100%;">
                                                                        <div id="ModbusProtocolAddrMappingItemsConfigTableInfoDiv_id" style="width:100%;height:100%;"></div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div size="20%" showCollapseButton="true" collapseDirection="right" minSize="150">
                                                                <div id="meaningAndBitStatusSplitter_Id" class="mini-splitter" style="width:100%;height:100%;" vertical="true">
                                                                    <div size="50%" showCollapseButton="false">
                                                                        <div style="padding:4px;height:100%;background:#fff;">
                                                                            <div id="protocolMeaningContainer" style="width:100%;height:100%;">
                                                                                <div id="ModbusProtocolAddrMappingItemsMeaningTableInfoDiv_id" style="width:100%;height:100%;"></div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div size="50%" showCollapseButton="true" collapseDirection="bottom" minSize="80" visible="false">
                                                                        <div style="padding:4px;height:100%;background:#fff;">
                                                                            <div id="protocolBitStatusContainer" style="width:100%;height:100%;">
                                                                                <div id="ProtocolSwitchingValueBitStatusTableInfoDiv_id" style="width:100%;height:100%;"></div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- 扩展字段 -->
                                                    <div title="Extended" name="extended" style="height:100%;">
                                                        <div id="extendedSubTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" onactivechanged="onExtendedSubTabChanged">
                                                            <div title="Numeric Calculation" name="numeric" style="height:100%;">
                                                                <div id="extNumericContainer" style="width:100%;height:100%;">
                                                                    <div id="ProtocolExtendedFieldTableInfoDiv_id" style="width:100%;height:100%;"></div>
                                                                </div>
                                                            </div>
                                                            <div title="High/Low Byte" name="highlow" style="height:100%;">
                                                                <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                                                                    <div size="80%" showCollapseButton="false">
                                                                        <div style="padding:4px;height:100%;background:#fafafa;">
                                                                            <div id="extHighLowContainer" style="width:100%;height:100%;">
                                                                                <div id="ProtocolExtendedFieldHighLowByteTableInfoDiv_id" style="width:100%;height:100%;"></div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div size="20%" showCollapseButton="true" collapseDirection="right" minSize="150">
                                                                        <div id="highLowBitStatusSplitter_Id" class="mini-splitter" style="width:100%;height:100%;" vertical="true">
                                                                            <div size="50%" showCollapseButton="false">
                                                                                <div style="padding:4px;height:100%;background:#fff;">
                                                                                    <div id="extHighLowMeaningContainer" style="width:100%;height:100%;">
                                                                                        <div id="ProtocolExtendedFieldConfigHighLowByteItemsMeaningTableInfoDiv_id" style="width:100%;height:100%;"></div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div size="50%" showCollapseButton="true" collapseDirection="bottom" minSize="80" visible="false">
                                                                                <div style="padding:4px;height:100%;background:#fff;">
                                                                                    <div id="extHighLowBitStatusContainer" style="width:100%;height:100%;">
                                                                                        <div id="ProtocolExtendedFieldSwitchingValueBitStatusConfigTableInfoDiv_id" style="width:100%;height:100%;"></div>
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
                                </div>
                            </div>
                        </div>
                        <!-- ===================== 单元配置 ===================== -->
                        <div title="Unit" name="unit" style="height:100%;">
                            <div class="tab-content-layout">
                                <div style="flex:1;overflow:hidden;">
                                    <div id="unitSubTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" tabPosition="left" onactivechanged="onUnitSubTabChanged">
                                        <!-- 采集单元 -->
                                        <div title="AcqUnit" name="acq" style="height:100%;">
                                            <div class="tab-content-layout" style="height:100%;">
                                                <!-- 工具栏 -->
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="acqUnitRefreshBtn" class="mini-button" iconCls="note-refresh" onclick="refreshAcqUnitProtocolTree">Refresh</button>
                                                    <span style="flex:1;"></span>
                                                    <button id="acqUnitAddBtn" class="mini-button" iconCls="add" onclick="addAcquisitionUnitInfo()">Add Unit</button>
                                                    <button id="acqUnitAddGroupBtn" class="mini-button" iconCls="add" onclick="addAcquisitionGroupInfo()">Add Group</button>
                                                    <button id="acqUnitAddCtrlGroupBtn" class="mini-button" iconCls="add" onclick="addControlGroupInfo()">Add Ctrl Group</button>
                                                    <button id="acqUnitSaveBtn" class="mini-button" iconCls="save" onclick="SaveModbusProtocolAcqUnitConfigTreeData()">Save</button>
                                                    <button id="acqUnitExportBtn" class="mini-button" iconCls="export" onclick="openExportAcqUnitWindow()">Export</button>
                                                    <button id="acqUnitImportBtn" class="mini-button" iconCls="import" onclick="openImportAcqUnitWindow()">Import</button>
                                                    <span id="acqUnitInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <!-- 主体：双层 Splitter -->
                                                <div style="flex:1;overflow:hidden;">
                                                    <!-- 外层 Splitter：协议树 + 右侧 -->
                                                    <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
                                                        <!-- 左侧协议树 20% -->
                                                        <div size="20%" showCollapseButton="true" collapseDirection="left" minSize="150">
                                                            <div style="padding:4px;height:100%;background:#fafafa;">
                                                                <div id="acqUnitProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true" onbeforeload="onAcqProtocolTreeBeforeLoad" onload="onAcqProtocolTreeLoad" onnodeselect="onAcqProtocolTreeSelect">
                                                                    <div property="emptyText" class="empty-msg">No Protocol</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- 右侧：内层 Splitter（单元列表 + 详情） -->
                                                        <div size="80%" showCollapseButton="false">
                                                            <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
                                                                <!-- 左侧单元列表 25% -->
                                                                <div size="25%" showCollapseButton="true" collapseDirection="left" minSize="150">
                                                                    <div style="padding:4px;height:100%;background:#fafafa;">
                                                                        <div id="acqUnitListTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true" onbeforeload="onAcqUnitListBeforeLoad" onload="onAcqUnitListLoad" onnodeselect="onAcqUnitListSelect" contextMenu="#acqUnitTreeMenu">
                                                                            <div property="emptyText" class="empty-msg">No Unit</div>
                                                                        </div>
                                                                        <ul id="acqUnitTreeMenu" class="mini-contextmenu" onbeforeopen="onAcqUnitTreeBeforeMenu">
                                                                            <li name="delete" iconCls="delete" onclick="deleteAcqUnitNode">
                                                                                <span id="acqUnitTreeMenuDeleteText">删除</span>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                                <!-- 右侧详情 Tabs -->
                                                                <div size="75%" showCollapseButton="false">
                                                                    <div style="padding:4px;height:100%;background:#fff;">
                                                                        <div id="acqUnitDetailTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="1" tabPosition="top" onactivechanged="onAcqUnitDetailTabChanged">
                                                                            <div id="acqUnitPropsTab" title="Properties" name="props" style="height:100%;">
                                                                                <div style="width:100%;height:100%;overflow:hidden;padding:4px;">
                                                                                    <div id="acqUnitPropertiesContainer" style="width:100%;height:100%;"></div>
                                                                                </div>
                                                                            </div>
                                                                            <div id="acqUnitConfigTab" title="Config" name="config" style="height:100%;">
                                                                                <div class="mini-toolbar" style="flex-shrink:0; padding:4px 8px; border-bottom:1px solid #e8e8e8; background:#fafafa;">
                                                                                    <button id="acqUnitConfigSelectAllBtn" class="mini-button" onclick="acqUnitConfigSelectAll()">全选</button>
                                                                                    <button id="acqUnitConfigDeselectAllBtn" class="mini-button" onclick="acqUnitConfigDeselectAll()">取消全选</button>
                                                                                </div>
                                                                                <div style="width:100%;height:100%;overflow:hidden;padding:4px;">
                                                                                    <div id="acqUnitConfigContainer" style="width:100%;height:100%;"></div>
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
                                        <!-- ===================== 显示单元 ===================== -->
                                        <div title="DisplayUnit" name="display" style="height:100%;">
                                            <div class="tab-content-layout" style="height:100%;">
                                                <!-- 工具栏 -->
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="displayUnitRefreshBtn" class="mini-button" iconCls="note-refresh" onclick="refreshDisplayUnitProtocolTree()">刷新</button>
                                                    <span style="flex:1;"></span>
                                                    <button id="displayUnitAddBtn" class="mini-button" iconCls="add" onclick="addDisplayUnitInfo()">添加</button>
                                                    <button id="displayUnitSaveBtn" class="mini-button" iconCls="save" onclick="saveDisplayUnitConfigData()">保存</button>
                                                    <button id="displayUnitExportBtn" class="mini-button" iconCls="export" onclick="openExportDisplayUnitWindow()">导出</button>
                                                    <button id="displayUnitImportBtn" class="mini-button" iconCls="import" onclick="openImportDisplayUnitWindow()">导入</button>
                                                    <span id="displayUnitInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>

                                                <!-- 主体：双层 Splitter（与采集单元一致） -->
                                                <div style="flex:1;overflow:hidden;">
                                                    <!-- 外层 Splitter：协议树 + 右侧 -->
                                                    <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
                                                        <!-- 左侧协议树 20% -->
                                                        <div size="20%" showCollapseButton="true" collapseDirection="left" minSize="150">
                                                            <div style="padding:4px;height:100%;background:#fafafa;">
                                                                <div id="displayUnitProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true" onbeforeload="onDisplayUnitProtocolTreeBeforeLoad" onload="onDisplayUnitProtocolTreeLoad" onnodeselect="onDisplayUnitProtocolTreeSelect">
                                                                    <div property="emptyText" class="empty-msg">无协议</div>
                                                                </div>
                                                                <!-- 右键菜单（可后期添加） -->
                                                            </div>
                                                        </div>
                                                        <!-- 右侧：内层 Splitter（单元列表 + 详情） -->
                                                        <div size="80%" showCollapseButton="false">
                                                            <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
                                                                <!-- 左侧单元列表 25% -->
                                                                <div size="25%" showCollapseButton="true" collapseDirection="left" minSize="150">
                                                                    <div style="padding:4px;height:100%;background:#fafafa;">
                                                                        <div id="displayUnitList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true" onbeforeload="onDisplayUnitListBeforeLoad" onload="onDisplayUnitListLoad" onnodeselect="onDisplayUnitListSelect">
                                                                            <div property="emptyText" class="empty-msg">无单元</div>
                                                                        </div>
                                                                        <!-- 右键菜单（可后期添加） -->
                                                                    </div>
                                                                </div>
                                                                <!-- 右侧详情 Tabs -->
                                                                <div size="75%" showCollapseButton="false">
                                                                    <div style="padding:4px;height:100%;background:#fff;">
                                                                        <div id="displayUnitRightTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="1" tabPosition="top" onactivechanged="onDisplayUnitDetailTabChanged">
                                                                            <!-- 属性 Tab -->
                                                                            <div title="属性" name="props" style="height:100%;">
                                                                                <div style="width:100%;height:100%;overflow:hidden;padding:4px;">
                                                                                    <div id="displayUnitPropertiesContainer" style="width:100%;height:100%;">
                                                                                        <!-- 属性表格将在此渲染 -->
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <!-- 配置 Tab -->
                                                                            <div title="配置" name="config" style="height:100%;">
                                                                                <!-- 垂直 Splitter：采集项配置（上） + 控制项配置（下） -->
                                                                                <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
                                                                                    <!-- 上半部分：采集项配置 -->
                                                                                    <div size="50%" showCollapseButton="false">
                                                                                        <div style="padding:4px;height:100%;background:#fafafa;display:flex;flex-direction:column;">
                                                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;flex-shrink:0;background:#fafafa;">
                                                                                                <button id="displayAcqSelectAllBtn" class="mini-button" onclick="displayAcqSelectAll()">全选</button>
                                                                                                <button id="displayAcqDeselectAllBtn" class="mini-button" onclick="displayAcqDeselectAll()">取消全选</button>
                                                                                                <span style="flex:1;"></span>
                                                                                                <span>
                                                                                                    <font color="red">采集项配置</font>
                                                                                                </span>
                                                                                            </div>
                                                                                            <div id="displayAcqItemsContainer" style="flex:1;padding:4px;">
                                                                                                <div id="ModbusProtocolDisplayUnitAcqItemsConfigTableInfoDiv_id" style="width:100%;height:100%;"></div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <!-- 下半部分：控制项配置 -->
                                                                                    <div size="50%" showCollapseButton="true" collapseDirection="bottom">
                                                                                        <div style="padding:4px;height:100%;background:#fafafa;display:flex;flex-direction:column;">
                                                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;flex-shrink:0;background:#fafafa;">
                                                                                                <button id="displayCtrlSelectAllBtn" class="mini-button" onclick="displayCtrlSelectAll()">全选</button>
                                                                                                <button id="displayCtrlDeselectAllBtn" class="mini-button" onclick="displayCtrlDeselectAll()">取消全选</button>
                                                                                                <span style="flex:1;"></span>
                                                                                                <span>
                                                                                                    <font color="red">控制项配置</font>
                                                                                                </span>
                                                                                            </div>
                                                                                            <div id="displayCtrlItemsContainer" style="flex:1;padding:4px;">
                                                                                                <div id="ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoDiv_id" style="width:100%;height:100%;"></div>
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
                                        </div>
                                        <!-- 报警单元 -->
                                        <div title="AlarmUnit" name="alarm" style="height:100%;">
                                            <div class="unit-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="alarmUnitRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="alarmUnitAddBtn" class="mini-button" iconCls="add">Add</button>
                                                    <span class="separator"></span>
                                                    <button id="alarmUnitSaveBtn" class="mini-button" iconCls="save">Save</button>
                                                    <span class="separator"></span>
                                                    <button id="alarmUnitColorBtn" class="mini-button" iconCls="alarm">Alarm Color</button>
                                                    <span class="separator"></span>
                                                    <button id="alarmUnitExportBtn" class="mini-button" iconCls="export">Export</button>
                                                    <button id="alarmUnitImportBtn" class="mini-button" iconCls="import">Import</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="alarmUnitInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="alarmUnitProtocolTreeContainer">
                                                        <div id="alarmUnitProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="alarmUnitListContainer">
                                                        <div id="alarmUnitList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Unit</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-config">
                                                        <div id="alarmUnitRightTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                            <div title="Properties" name="props" style="height:100%;">
                                                                <div id="alarmUnitPropsPlaceholder" class="sub-tab-placeholder">Properties</div>
                                                            </div>
                                                            <div title="Config" name="config" style="height:100%;">
                                                                <div class="alarm-config-layout">
                                                                    <div class="alarm-left-list">
                                                                        <div id="alarmItemsListPlaceholder" class="sub-tab-placeholder">Alarm Items List</div>
                                                                    </div>
                                                                    <div class="alarm-right-detail">
                                                                        <div id="alarmConfigSubTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top" onactivechanged="onAlarmConfigSubTabChanged">
                                                                            <div title="Numeric" name="numeric" style="height:100%;">
                                                                                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                    <button id="alarmNumericSelectAll" class="mini-button">Select All</button>
                                                                                    <button id="alarmNumericDeselectAll" class="mini-button">Deselect All</button>
                                                                                    <span style="flex:1;"></span>
                                                                                </div>
                                                                                <div style="flex:1;overflow:auto;padding:4px;">
                                                                                    <div id="alarmNumericPlaceholder" class="sub-tab-placeholder">Numeric Alarm Config</div>
                                                                                </div>
                                                                            </div>
                                                                            <div title="Switching" name="switching" style="height:100%;">
                                                                                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                    <button id="alarmSwitchSelectAll" class="mini-button">Select All</button>
                                                                                    <button id="alarmSwitchDeselectAll" class="mini-button">Deselect All</button>
                                                                                    <span style="flex:1;"></span>
                                                                                </div>
                                                                                <div style="flex:1;overflow:auto;padding:4px;">
                                                                                    <div id="alarmSwitchPlaceholder" class="sub-tab-placeholder">Switching Alarm Config</div>
                                                                                </div>
                                                                            </div>
                                                                            <div title="Enum" name="enum" style="height:100%;">
                                                                                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                    <button id="alarmEnumSelectAll" class="mini-button">Select All</button>
                                                                                    <button id="alarmEnumDeselectAll" class="mini-button">Deselect All</button>
                                                                                    <span style="flex:1;"></span>
                                                                                </div>
                                                                                <div style="flex:1;overflow:auto;padding:4px;">
                                                                                    <div id="alarmEnumPlaceholder" class="sub-tab-placeholder">Enum Alarm Config</div>
                                                                                </div>
                                                                            </div>
                                                                            <div title="CommStatus" name="comm" style="height:100%;">
                                                                                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                    <button id="alarmCommSelectAll" class="mini-button">Select All</button>
                                                                                    <button id="alarmCommDeselectAll" class="mini-button">Deselect All</button>
                                                                                    <span style="flex:1;"></span>
                                                                                </div>
                                                                                <div style="flex:1;overflow:auto;padding:4px;">
                                                                                    <div id="alarmCommPlaceholder" class="sub-tab-placeholder">Comm Status Alarm Config</div>
                                                                                </div>
                                                                            </div>
                                                                            <div title="RunStatus" name="run" style="height:100%;">
                                                                                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                    <button id="alarmRunSelectAll" class="mini-button">Select All</button>
                                                                                    <button id="alarmRunDeselectAll" class="mini-button">Deselect All</button>
                                                                                    <span style="flex:1;"></span>
                                                                                </div>
                                                                                <div style="flex:1;overflow:auto;padding:4px;">
                                                                                    <div id="alarmRunPlaceholder" class="sub-tab-placeholder">Run Status Alarm Config</div>
                                                                                </div>
                                                                            </div>
                                                                            <div title="FESDiagram" name="fes" style="height:100%;">
                                                                                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                    <button id="alarmFESSelectAll" class="mini-button">Select All</button>
                                                                                    <button id="alarmFESDeselectAll" class="mini-button">Deselect All</button>
                                                                                    <span style="flex:1;"></span>
                                                                                </div>
                                                                                <div style="flex:1;overflow:auto;padding:4px;">
                                                                                    <div id="alarmFESPlaceholder" class="sub-tab-placeholder">FES Diagram Alarm Config</div>
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
                                        <!-- 报表单元 -->
                                        <div title="ReportUnit" name="report" style="height:100%;">
                                            <div class="unit-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="reportUnitRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="reportUnitAddBtn" class="mini-button" iconCls="add">Add</button>
                                                    <span class="separator"></span>
                                                    <button id="reportUnitSaveBtn" class="mini-button" iconCls="save">Save</button>
                                                    <span class="separator"></span>
                                                    <button id="reportUnitExportBtn" class="mini-button" iconCls="export">Export</button>
                                                    <button id="reportUnitImportBtn" class="mini-button" iconCls="import">Import</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="reportUnitInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="reportUnitProtocolTreeContainer">
                                                        <div id="reportUnitProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="reportUnitListContainer">
                                                        <div id="reportUnitList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Unit</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-config">
                                                        <div id="reportUnitRightTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                            <div title="Properties" name="props" style="height:100%;">
                                                                <div id="reportUnitPropsPlaceholder" class="sub-tab-placeholder">Properties</div>
                                                            </div>
                                                            <div title="Config" name="config" style="height:100%;">
                                                                <div class="report-config-layout">
                                                                    <div class="report-left-list">
                                                                        <div id="reportTemplatesPlaceholder" class="sub-tab-placeholder">Report Templates</div>
                                                                    </div>
                                                                    <div class="report-right-detail">
                                                                        <div id="reportConfigSubTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top" onactivechanged="onReportConfigSubTabChanged">
                                                                            <div title="Single Well Report" name="single" style="height:100%;">
                                                                                <div id="reportSinglePlaceholder" class="sub-tab-placeholder">Single Well Report Config</div>
                                                                            </div>
                                                                            <div title="Area Report" name="area" style="height:100%;">
                                                                                <div id="reportAreaPlaceholder" class="sub-tab-placeholder">Area Report Config</div>
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
                        </div>

                        <!-- ===================== 实例配置 ===================== -->
                        <div title="Instance" name="instance" style="height:100%;">
                            <div class="tab-content-layout">
                                <div class="mini-toolbar">
                                    <button id="instanceRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                    <span class="separator"></span>
                                    <button id="instanceAddBtn" class="mini-button" iconCls="add">Add</button>
                                    <button id="instanceSaveBtn" class="mini-button" iconCls="save">Save</button>
                                    <span class="separator"></span>
                                    <button id="instanceExportBtn" class="mini-button" iconCls="export">Export</button>
                                    <button id="instanceImportBtn" class="mini-button" iconCls="import">Import</button>
                                    <span style="flex:1;"></span>
                                    <span id="instanceInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                </div>
                                <div style="flex:1;overflow:hidden;">
                                    <div id="instanceSubTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" tabPosition="left" onactivechanged="onInstanceSubTabChanged">
                                        <!-- 采控实例 -->
                                        <div title="AcqInstance" name="acq" style="height:100%;">
                                            <div class="instance-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="acqInstanceRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="acqInstanceAddBtn" class="mini-button" iconCls="add">Add</button>
                                                    <span class="separator"></span>
                                                    <button id="acqInstanceSaveBtn" class="mini-button" iconCls="save">Save</button>
                                                    <span class="separator"></span>
                                                    <button id="acqInstanceExportBtn" class="mini-button" iconCls="export">Export</button>
                                                    <button id="acqInstanceImportBtn" class="mini-button" iconCls="import">Import</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="acqInstanceInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="acqInstanceProtocolTreeContainer">
                                                        <div id="acqInstanceProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="acqInstanceListContainer">
                                                        <div id="acqInstanceList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Instance</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-property">
                                                        <div id="acqInstancePropsPlaceholder" class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 显示实例 -->
                                        <div title="DisplayInstance" name="display" style="height:100%;">
                                            <div class="instance-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="displayInstanceRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="displayInstanceAddBtn" class="mini-button" iconCls="add">Add</button>
                                                    <span class="separator"></span>
                                                    <button id="displayInstanceSaveBtn" class="mini-button" iconCls="save">Save</button>
                                                    <span class="separator"></span>
                                                    <button id="displayInstanceExportBtn" class="mini-button" iconCls="export">Export</button>
                                                    <button id="displayInstanceImportBtn" class="mini-button" iconCls="import">Import</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="displayInstanceInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="displayInstanceProtocolTreeContainer">
                                                        <div id="displayInstanceProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="displayInstanceListContainer">
                                                        <div id="displayInstanceList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Instance</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-property">
                                                        <div id="displayInstancePropsPlaceholder" class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 报警实例 -->
                                        <div title="AlarmInstance" name="alarm" style="height:100%;">
                                            <div class="instance-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="alarmInstanceRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="alarmInstanceAddBtn" class="mini-button" iconCls="add">Add</button>
                                                    <span class="separator"></span>
                                                    <button id="alarmInstanceSaveBtn" class="mini-button" iconCls="save">Save</button>
                                                    <span class="separator"></span>
                                                    <button id="alarmInstanceExportBtn" class="mini-button" iconCls="export">Export</button>
                                                    <button id="alarmInstanceImportBtn" class="mini-button" iconCls="import">Import</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="alarmInstanceInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="alarmInstanceProtocolTreeContainer">
                                                        <div id="alarmInstanceProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="alarmInstanceListContainer">
                                                        <div id="alarmInstanceList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Instance</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-property">
                                                        <div id="alarmInstancePropsPlaceholder" class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 报表实例 -->
                                        <div title="ReportInstance" name="report" style="height:100%;">
                                            <div class="instance-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="reportInstanceRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="reportInstanceAddBtn" class="mini-button" iconCls="add">Add</button>
                                                    <span class="separator"></span>
                                                    <button id="reportInstanceSaveBtn" class="mini-button" iconCls="save">Save</button>
                                                    <span class="separator"></span>
                                                    <button id="reportInstanceExportBtn" class="mini-button" iconCls="export">Export</button>
                                                    <button id="reportInstanceImportBtn" class="mini-button" iconCls="import">Import</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="reportInstanceInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="reportInstanceProtocolTreeContainer">
                                                        <div id="reportInstanceProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="reportInstanceListContainer">
                                                        <div id="reportInstanceList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Instance</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-property">
                                                        <div id="reportInstancePropsPlaceholder" class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 短信实例 -->
                                        <div title="SMSInstance" name="sms" style="height:100%;">
                                            <div class="instance-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="smsInstanceRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="smsInstanceAddBtn" class="mini-button" iconCls="add">Add</button>
                                                    <button id="smsInstanceUpdateBtn" class="mini-button" iconCls="edit">Update</button>
                                                    <button id="smsInstanceDeleteBtn" class="mini-button" iconCls="delete">Delete</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="smsInstanceInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="smsInstanceProtocolTreeContainer">
                                                        <div id="smsInstanceProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="smsInstanceListContainer">
                                                        <div id="smsInstanceList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Instance</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-property">
                                                        <div id="smsInstancePropsPlaceholder" class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
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

    <script>
        var isInitializing = true;
        var context = '<%=path%>';

        var loginUserProtocolConfigModuleRight = getRoleModuleRight(context + '/roleManagerController/getRoleModuleRight', 'DriverManagement');
        var editFlag = false;
        if (typeof loginUserProtocolConfigModuleRight !== 'undefined') {
            editFlag = (loginUserProtocolConfigModuleRight.editFlag == 1);
        }


        //报警单元
        var protocolConfigAlarmUnitPropertiesHandsontableHelper = null;
        var protocolAlarmUnitConfigNumItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigSwitchItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigEnumItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigCommStatusItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigRunStatusItemsHandsontableHelper = null;



        //报表单元
        var reportUnitPropertiesHandsontableHelper = null;
        var singleWellRangeReportTemplateHandsontableHelper = null;
        var singleWellRangeReportTemplateContentHandsontableHelper = null;
        var productionReportTemplateHandsontableHelper = null;
        var productionReportTemplateContentHandsontableHelper = null;
        var singleWellDailyReportTemplateHandsontableHelper = null;
        var singleWellDailyReportTemplateContentHandsontableHelper = null;
        var hydrologicalWellDailyReportTemplateHandsontableHelper = null;
        var hydrologicalWellDailyReportContentHandsontableHelper = null;

        //采控实例
        var protocolConfigInstancePropertiesHandsontableHelper = null;

        //显示实例
        var protocolDisplayInstancePropertiesHandsontableHelper = null;

        //报警实例
        var protocolAlarmInstancePropertiesHandsontableHelper = null;

        //报表实例
        var protocolReportInstancePropertiesHandsontableHelper = null;

        // ================================================================
        // 设备类型树联动逻辑
        // ================================================================
        // 递归获取当前节点及其所有子节点的 deviceTypeId，返回逗号分隔的字符串
        var selectedDeviceTypeId = null;
        var allDeviceTypeIds = null;
        
        var _selectedUnitConfigProtocolTreeNodeCode= null;

        function foreachAndSearchTabChildId(node) {
            if (!node) return '';
            var ids = [];

            function collect(currentNode) {
                if (currentNode.deviceTypeId) {
                    ids.push(currentNode.deviceTypeId);
                }
                if (currentNode.children && currentNode.children.length > 0) {
                    for (var i = 0; i < currentNode.children.length; i++) {
                        collect(currentNode.children[i]);
                    }
                }
            }
            collect(node);
            return ids.join(',');
        }

        function onDeviceTypeSelect(e) {
            var node = e.node;
            if (node) {
                selectedDeviceTypeId = foreachAndSearchTabChildId(node);
                loadDataForCurrentTab(selectedDeviceTypeId);
            }
        }

        function onDeviceTypeTreeLoad(e) {
            var tree = e.sender;
            var root = tree.getRootNode();
            allDeviceTypeIds = foreachAndSearchTabChildId(root);
            if (root && root.children && root.children.length > 0) {
                var firstChild = root.children[0];
                // 延迟启用事件处理
                setTimeout(function() {
                    tree.selectNode(firstChild);
                }, 500);
            }
        }

        function onMainTabChanged(e) {
            if (isInitializing) return;
            if (selectedDeviceTypeId) loadDataForCurrentTab(selectedDeviceTypeId);
        }

        function onUnitSubTabChanged(e) {
            if (isInitializing) return;
            if (selectedDeviceTypeId) loadDataForCurrentTab(selectedDeviceTypeId);
        }

        function onAlarmConfigSubTabChanged(e) {
            if (isInitializing) return;
            if (selectedDeviceTypeId) loadDataForCurrentTab(selectedDeviceTypeId);
        }

        function onReportConfigSubTabChanged(e) {
            if (isInitializing) return;
            if (selectedDeviceTypeId) loadDataForCurrentTab(selectedDeviceTypeId);
        }

        function onInstanceSubTabChanged(e) {
            if (isInitializing) return;
            if (selectedDeviceTypeId) loadDataForCurrentTab(selectedDeviceTypeId);
        }

        function loadDataForCurrentTab(deviceTypeId) {
            var mainTabs = mini.get('mainTabs');
            if (!mainTabs) return;
            var activeMain = mainTabs.getActiveTab();
            if (!activeMain) return;
            var mainName = activeMain.name;

            if (mainName === 'protocol') {
                var protocolTree = mini.get('protocolTree');
                if (protocolTree) {
                    if (!protocolTree.getUrl()) {
                        protocolTree.setUrl(context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData');
                    }
                    protocolTree.load();
                }
            } else if (mainName === 'unit') {
                var unitSub = mini.get('unitSubTabs');
                if (!unitSub) return;
                var activeUnitTab = unitSub.getActiveTab();
                if (!activeUnitTab) return;
                var unitName = activeUnitTab.name;
                var protocolTreeId = '',
                    listTreeId = '';

                if (unitName === 'acq') {
                    protocolTreeId = 'acqUnitProtocolTree';
                    listTreeId = 'acqUnitListTree'; // 修正：与布局一致
                } else if (unitName === 'display') {
                    protocolTreeId = 'displayUnitProtocolTree';
                    listTreeId = 'displayUnitList';
                } else if (unitName === 'alarm') {
                    protocolTreeId = 'alarmUnitProtocolTree';
                    listTreeId = 'alarmUnitList';
                } else if (unitName === 'report') {
                    protocolTreeId = 'reportUnitProtocolTree';
                    listTreeId = 'reportUnitList';
                }

                var protocolTree = mini.get(protocolTreeId);
                var listTree = mini.get(listTreeId);

                // 加载协议树
                if (protocolTree) {
                    // 设置 URL（根据单元类型，可统一使用同一个接口）
                    if (!protocolTree.getUrl()) {
                        // 若后端有区分接口，可根据 unitName 映射；若无，统一使用同一个
                        protocolTree.setUrl(context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData');
                    }
                    protocolTree.load();
                }

                // 清空列表树（等待用户点击协议后加载）
                if (listTree) {
                    //listTree.loadData([]);
                }
            } else if (mainName === 'instance') {
                var instanceSub = mini.get('instanceSubTabs');
                if (!instanceSub) return;
                var activeInstTab = instanceSub.getActiveTab();
                if (!activeInstTab) return;
                var instName = activeInstTab.name;
                var protocolTreeId = '',
                    listTreeId = '';
                if (instName === 'acq') {
                    protocolTreeId = 'acqInstanceProtocolTree';
                    listTreeId = 'acqInstanceList';
                } else if (instName === 'display') {
                    protocolTreeId = 'displayInstanceProtocolTree';
                    listTreeId = 'displayInstanceList';
                } else if (instName === 'alarm') {
                    protocolTreeId = 'alarmInstanceProtocolTree';
                    listTreeId = 'alarmInstanceList';
                } else if (instName === 'report') {
                    protocolTreeId = 'reportInstanceProtocolTree';
                    listTreeId = 'reportInstanceList';
                } else if (instName === 'sms') {
                    protocolTreeId = 'smsInstanceProtocolTree';
                    listTreeId = 'smsInstanceList';
                }
                var protocolTree = mini.get(protocolTreeId);
                var listTree = mini.get(listTreeId);
                if (protocolTree) {
                    if (!protocolTree.getUrl()) {
                        protocolTree.setUrl(context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData');
                    }
                    protocolTree.load();
                }
                if (listTree) {
                    listTree.loadData([]);
                }
            }
        }

        //获取树节点的绝对路径（从根到当前节点，用 "/" 拼接）
        function getNodePath(tree, node) {
            if (!node) return '';
            var path = [];
            var current = node;
            while (current) {
                path.unshift(current.text);
                // 获取当前节点的父ID
                var parentId = current.parentId;
                // 如果父ID为 null/undefined/'0'，说明到达根节点
                if (!parentId || parentId === '0' || parentId === 0) {
                    break;
                }
                // 通过父ID查找父节点（idField 为 deviceTypeId）
                current = tree.getNode(parentId);
                if (!current) break; // 防止死循环
            }
            return path.join('/');
        }


        // ================================================================
        // 国际化初始化
        // ================================================================
        function initI18n() {
            // ---- 左侧标题 ----
            document.getElementById('deviceTypeTitle').innerHTML = _loginUserLanguageResource.deviceType;

            // ---- 设备树空文本 ----
            var tree = mini.get('deviceTypeTree');
            if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);

            // ---- 主标签页 ----
            var mainTabs = mini.get('mainTabs');
            if (mainTabs) {
                var tabs = mainTabs.getTabs();
                if (tabs && tabs.length >= 3) {
                    mainTabs.updateTab(tabs[0], {
                        title: _loginUserLanguageResource.protocolConfig
                    });
                    mainTabs.updateTab(tabs[1], {
                        title: _loginUserLanguageResource.unitConfig
                    });
                    mainTabs.updateTab(tabs[2], {
                        title: _loginUserLanguageResource.instanceConfig
                    });
                }
            }

            // ---- 协议子标签页 ----
            var protoSub = mini.get('protocolSubTabs');
            if (protoSub) {
                var tabs = protoSub.getTabs();
                if (tabs && tabs.length >= 3) {
                    protoSub.updateTab(tabs[0], {
                        title: _loginUserLanguageResource.properties
                    });
                    protoSub.updateTab(tabs[1], {
                        title: _loginUserLanguageResource.config
                    });
                    protoSub.updateTab(tabs[2], {
                        title: _loginUserLanguageResource.extendedField
                    });
                }
            }
            var extSub = mini.get('extendedSubTabs');
            if (extSub) {
                var tabs = extSub.getTabs();
                if (tabs && tabs.length >= 2) {
                    extSub.updateTab(tabs[0], {
                        title: _loginUserLanguageResource.numericCalculation
                    });
                    extSub.updateTab(tabs[1], {
                        title: _loginUserLanguageResource.highLowByte
                    });
                }
            }

            // ---- 单元子标签页 ----
            var unitSub = mini.get('unitSubTabs');
            if (unitSub) {
                var tabs = unitSub.getTabs();
                if (tabs && tabs.length >= 4) {
                    unitSub.updateTab(tabs[0], {
                        title: _loginUserLanguageResource.acqUnit
                    });
                    unitSub.updateTab(tabs[1], {
                        title: _loginUserLanguageResource.displayUnit
                    });
                    unitSub.updateTab(tabs[2], {
                        title: _loginUserLanguageResource.alarmUnit
                    });
                    unitSub.updateTab(tabs[3], {
                        title: _loginUserLanguageResource.reportUnit
                    });
                }
            }
            ['acqUnitDetailTabs', 'displayUnitRightTabs', 'alarmUnitRightTabs', 'reportUnitRightTabs'].forEach(function(id) {
                var tab = mini.get(id);
                if (tab) {
                    var tabs = tab.getTabs();
                    if (tabs && tabs.length >= 2) {
                        tab.updateTab(tabs[0], {
                            title: _loginUserLanguageResource.properties
                        });
                        tab.updateTab(tabs[1], {
                            title: _loginUserLanguageResource.config
                        });
                    }
                }
            });
            var alarmConfigSub = mini.get('alarmConfigSubTabs');
            if (alarmConfigSub) {
                var tabs = alarmConfigSub.getTabs();
                if (tabs && tabs.length >= 6) {
                    alarmConfigSub.updateTab(tabs[0], {
                        title: _loginUserLanguageResource.numericValue
                    });
                    alarmConfigSub.updateTab(tabs[1], {
                        title: _loginUserLanguageResource.switchingValue
                    });
                    alarmConfigSub.updateTab(tabs[2], {
                        title: _loginUserLanguageResource.enumValue
                    });
                    alarmConfigSub.updateTab(tabs[3], {
                        title: _loginUserLanguageResource.commStatus
                    });
                    alarmConfigSub.updateTab(tabs[4], {
                        title: _loginUserLanguageResource.runStatus
                    });
                    alarmConfigSub.updateTab(tabs[5], {
                        title: _loginUserLanguageResource.FESDiagramResultAlarm
                    });
                }
            }
            var reportConfigSub = mini.get('reportConfigSubTabs');
            if (reportConfigSub) {
                var tabs = reportConfigSub.getTabs();
                if (tabs && tabs.length >= 2) {
                    reportConfigSub.updateTab(tabs[0], {
                        title: _loginUserLanguageResource.singleWellReport
                    });
                    reportConfigSub.updateTab(tabs[1], {
                        title: _loginUserLanguageResource.areaReport
                    });
                }
            }

            // ---- 实例子标签页 ----
            var instanceSub = mini.get('instanceSubTabs');
            if (instanceSub) {
                var tabs = instanceSub.getTabs();
                if (tabs && tabs.length >= 5) {
                    instanceSub.updateTab(tabs[0], {
                        title: _loginUserLanguageResource.acqInstance
                    });
                    instanceSub.updateTab(tabs[1], {
                        title: _loginUserLanguageResource.displayInstance
                    });
                    instanceSub.updateTab(tabs[2], {
                        title: _loginUserLanguageResource.alarmInstance
                    });
                    instanceSub.updateTab(tabs[3], {
                        title: _loginUserLanguageResource.reportInstance
                    });
                    instanceSub.updateTab(tabs[4], {
                        title: _loginUserLanguageResource.SMSInstance
                    });
                }
            }

            // ---- 按钮文本（主工具栏） ----
            var btnMap = {
                'protocolRefreshBtn': 'refresh',
                'protocolAddBtn': 'addProtocol',
                'protocolSaveBtn': 'save',
                'protocolMappingBtn': 'fieldMappingTable',
                'protocolExportBtn': 'exportData',
                'protocolImportBtn': 'importData',
                'protocolDeviceTypeChangeBtn': 'protocoDeviceTypeChange',
                'instanceRefreshBtn': 'refresh',
                'instanceAddBtn': 'addInstance',
                'instanceSaveBtn': 'save',
                'instanceExportBtn': 'exportData',
                'instanceImportBtn': 'importData'
            };
            for (var id in btnMap) {
                var btn = mini.get(id);
                if (btn) btn.setText(_loginUserLanguageResource[btnMap[id]] || btnMap[id]);
            }

            // ---- 各子模块工具栏按钮 ----
            var subBtnMap = {
                'acqUnitRefreshBtn': 'refresh',
                'acqUnitAddBtn': 'addAcqUnit',
                'acqUnitAddGroupBtn': 'addAcqGroup',
                'acqUnitAddCtrlGroupBtn': 'addCtrlGroup',
                'acqUnitSaveBtn': 'save',
                'acqUnitExportBtn': 'exportData',
                'acqUnitImportBtn': 'importData',
                'acqUnitConfigSelectAllBtn': 'selectAll',
                'acqUnitConfigDeselectAllBtn': 'deselectAll',
                'displayUnitRefreshBtn': 'refresh',
                'displayUnitAddBtn': 'addDisplayUnit',
                'displayUnitSaveBtn': 'save',
                'displayUnitExportBtn': 'exportData',
                'displayUnitImportBtn': 'importData',
                'displayAcqSelectAllBtn': 'selectAll',
                'displayAcqDeselectAllBtn': 'deselectAll',
                'displayCtrlSelectAllBtn': 'selectAll',
                'displayCtrlDeselectAllBtn': 'deselectAll',
                'alarmUnitRefreshBtn': 'refresh',
                'alarmUnitAddBtn': 'addAlarmUnit',
                'alarmUnitSaveBtn': 'save',
                'alarmUnitColorBtn': 'alarmColorConfig',
                'alarmUnitExportBtn': 'exportData',
                'alarmUnitImportBtn': 'importData',
                'alarmNumericSelectAll': 'selectAll',
                'alarmNumericDeselectAll': 'deselectAll',
                'alarmSwitchSelectAll': 'selectAll',
                'alarmSwitchDeselectAll': 'deselectAll',
                'alarmEnumSelectAll': 'selectAll',
                'alarmEnumDeselectAll': 'deselectAll',
                'alarmCommSelectAll': 'selectAll',
                'alarmCommDeselectAll': 'deselectAll',
                'alarmRunSelectAll': 'selectAll',
                'alarmRunDeselectAll': 'deselectAll',
                'alarmFESSelectAll': 'selectAll',
                'alarmFESDeselectAll': 'deselectAll',
                'reportUnitRefreshBtn': 'refresh',
                'reportUnitAddBtn': 'addReportUnit',
                'reportUnitSaveBtn': 'save',
                'reportUnitExportBtn': 'exportData',
                'reportUnitImportBtn': 'importData',
                'acqInstanceRefreshBtn': 'refresh',
                'acqInstanceAddBtn': 'addAcqInstance',
                'acqInstanceSaveBtn': 'save',
                'acqInstanceExportBtn': 'exportData',
                'acqInstanceImportBtn': 'importData',
                'displayInstanceRefreshBtn': 'refresh',
                'displayInstanceAddBtn': 'addAcqInstance',
                'displayInstanceSaveBtn': 'save',
                'displayInstanceExportBtn': 'exportData',
                'displayInstanceImportBtn': 'importData',
                'alarmInstanceRefreshBtn': 'refresh',
                'alarmInstanceAddBtn': 'addAcqInstance',
                'alarmInstanceSaveBtn': 'save',
                'alarmInstanceExportBtn': 'exportData',
                'alarmInstanceImportBtn': 'importData',
                'reportInstanceRefreshBtn': 'refresh',
                'reportInstanceAddBtn': 'addAcqInstance',
                'reportInstanceSaveBtn': 'save',
                'reportInstanceExportBtn': 'exportData',
                'reportInstanceImportBtn': 'importData',
                'smsInstanceRefreshBtn': 'refresh',
                'smsInstanceAddBtn': 'add',
                'smsInstanceUpdateBtn': 'update',
                'smsInstanceDeleteBtn': 'deleteData'
            };
            for (var id in subBtnMap) {
                var btn = mini.get(id);
                if (btn) btn.setText(_loginUserLanguageResource[subBtnMap[id]] || subBtnMap[id]);
            }

            // ---- 各占位文本 ----
            var placeholders = {
                // 协议配置内
                '#protocolTree .sub-tab-placeholder': 'protocolList',
                '#protocolItemsPlaceholder': 'acqAndCtrlItemConfig',
                '#protocolMeaningPlaceholder': 'meaning',
                '#protocolBitStatusPlaceholder': 'switchingValueBitStatusConfig',
                '#extNumericPlaceholder': 'config',
                '#extHighLowPlaceholder': 'config',
                '#extHighLowMeaningPlaceholder': 'meaning',
                '#extHighLowBitStatusPlaceholder': 'switchingValueBitStatusConfig',
                // 单元配置
                '#acqUnitProtocolTree .sub-tab-placeholder': 'protocolList',
                '#acqUnitList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.acqUnit + ' List';
                },
                '#acqUnitPropsPlaceholder': 'properties',
                '#acqUnitConfigPlaceholder': function() {
                    return _loginUserLanguageResource.acqUnit + ' Config';
                },
                '#displayUnitProtocolTree .sub-tab-placeholder': 'protocolList',
                '#displayUnitList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.displayUnit + ' List';
                },
                '#displayUnitPropsPlaceholder': 'properties',
                '#displayAcqItemsPlaceholder': function() {
                    return _loginUserLanguageResource.acquisitionItemConfig;
                },
                '#displayCtrlItemsPlaceholder': function() {
                    return _loginUserLanguageResource.controlItemConfig;
                },
                '#alarmUnitProtocolTree .sub-tab-placeholder': 'protocolList',
                '#alarmUnitList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.alarmUnit + ' List';
                },
                '#alarmUnitPropsPlaceholder': 'properties',
                '#alarmItemsListPlaceholder': 'alarmItemsList',
                '#alarmNumericPlaceholder': function() {
                    return _loginUserLanguageResource.numericValue + ' Alarm Config';
                },
                '#alarmSwitchPlaceholder': function() {
                    return _loginUserLanguageResource.switchingValue + ' Alarm Config';
                },
                '#alarmEnumPlaceholder': function() {
                    return _loginUserLanguageResource.enumValue + ' Alarm Config';
                },
                '#alarmCommPlaceholder': function() {
                    return _loginUserLanguageResource.commStatus + ' Alarm Config';
                },
                '#alarmRunPlaceholder': function() {
                    return _loginUserLanguageResource.runStatus + ' Alarm Config';
                },
                '#alarmFESPlaceholder': function() {
                    return _loginUserLanguageResource.FESDiagramResultAlarm + ' Config';
                },
                '#reportUnitProtocolTree .sub-tab-placeholder': 'protocolList',
                '#reportUnitList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.reportUnit + ' List';
                },
                '#reportUnitPropsPlaceholder': 'properties',
                '#reportTemplatesPlaceholder': 'reportTemplates',
                '#reportSinglePlaceholder': function() {
                    return _loginUserLanguageResource.singleWellReport + ' Config';
                },
                '#reportAreaPlaceholder': function() {
                    return _loginUserLanguageResource.areaReport + ' Config';
                },
                // 实例配置
                '#acqInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
                '#acqInstanceList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.acqInstance + ' List';
                },
                '#acqInstancePropsPlaceholder': 'properties',
                '#displayInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
                '#displayInstanceList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.displayInstance + ' List';
                },
                '#displayInstancePropsPlaceholder': 'properties',
                '#alarmInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
                '#alarmInstanceList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.alarmInstance + ' List';
                },
                '#alarmInstancePropsPlaceholder': 'properties',
                '#reportInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
                '#reportInstanceList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.reportInstance + ' List';
                },
                '#reportInstancePropsPlaceholder': 'properties',
                '#smsInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
                '#smsInstanceList .sub-tab-placeholder': function() {
                    return _loginUserLanguageResource.SMSInstance + ' List';
                },
                '#smsInstancePropsPlaceholder': 'properties'
            };
            for (var selector in placeholders) {
                var el = document.querySelector(selector);
                if (el) {
                    var val = placeholders[selector];
                    el.innerHTML = (typeof val === 'function') ? val() : (_loginUserLanguageResource[val] || val);
                }
            }

            // 信息标签置空
            ['protocolInfoLabel', 'unitInfoLabel', 'instanceInfoLabel',
                'acqUnitInfoLabel', 'displayUnitInfoLabel', 'alarmUnitInfoLabel', 'reportUnitInfoLabel',
                'acqInstanceInfoLabel', 'displayInstanceInfoLabel', 'alarmInstanceInfoLabel', 'reportInstanceInfoLabel', 'smsInstanceInfoLabel'
            ].forEach(function(id) {
                var el = document.getElementById(id);
                if (el) el.innerHTML = '';
            });

            updateBtnStatus();
        }

        function updateBtnStatus() {
            var btnIds = ['protocolAddBtn', 'protocolSaveBtn', 'protocolMappingBtn', 'protocolExportBtn', 'protocolImportBtn', 'protocolDeviceTypeChangeBtn',
                'acqUnitAddBtn', 'acqUnitAddGroupBtn', 'acqUnitAddCtrlGroupBtn', 'acqUnitSaveBtn', 'acqUnitExportBtn', 'acqUnitImportBtn'
            ];
            for (var i = 0; i < btnIds.length; i++) {
                var btn = mini.get(btnIds[i]);
                if (btn) {
                    btn.setEnabled(editFlag);
                }
            }
        }

        // ================================================================
        // 页面初始化
        // ================================================================
        $(document).ready(function() {
            mini.parse();
            initI18n();
            console.log('驱动配置模块加载完成（协议配置已重构为 mini-splitter 布局）');


            var deviceTree = mini.get('deviceTypeTree');
            if (deviceTree) {
                deviceTree.load(context + '/roleManagerController/constructProtocolConfigTabTreeGridTree');
            }

            // 延迟启用事件处理
            setTimeout(function() {
                isInitializing = false;
            }, 500);
        });

    </script>
</body>

</html>
