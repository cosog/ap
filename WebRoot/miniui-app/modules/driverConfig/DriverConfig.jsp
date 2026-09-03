<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>驱动配置</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
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
                                    <button id="protocolMappingBtn" class="mini-button" onclick="openFieldMappingWindow()" >Mapping</button>
                                    <button id="protocolExportBtn" class="mini-button" iconCls="export">Export</button>
                                    <button id="protocolImportBtn" class="mini-button" iconCls="import">Import</button>
                                    <button id="protocolDeviceTypeChangeBtn" class="mini-button" iconCls="move">Move</button>
                                    <span id="protocolInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                    <input type="hidden" id="ModbusProtocolAddrMappingItemsSelectRow_Id" value="0" />
                                    <input type="hidden" id="ProtocolExtendedFieldHighLowByteSelectRow_Id" value="0" />
                                </div>
                                <div style="flex:1;overflow:hidden;">
                                    <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                                        <!-- 左侧协议树 17% -->
                                        <div size="17%" showCollapseButton="true" collapseDirection="left" minSize="150">
                                            <div style="padding:4px;height:100%;background:#fafafa;">
                                                <div id="protocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" 
                                                	idField="id" textField="text" parentField="pid" resultAsTree="true" 
                                                	onload="onProtocolTreeLoad" onbeforeload="onProtocolTreeBeforeLoad" onnodeselect="onProtocolNodeSelect"
                                                	contextMenu="#protocolTreeMenu" >
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
                                                                <div id="meaningAndBitStatusSplitter_Id"  class="mini-splitter" style="width:100%;height:100%;" vertical="true">
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
                                <div class="mini-toolbar">
                                    <button id="unitRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                    <span class="separator"></span>
                                    <button id="unitAddBtn" class="mini-button" iconCls="add">Add</button>
                                    <button id="unitSaveBtn" class="mini-button" iconCls="save">Save</button>
                                    <span class="separator"></span>
                                    <button id="unitExportBtn" class="mini-button" iconCls="export">Export</button>
                                    <button id="unitImportBtn" class="mini-button" iconCls="import">Import</button>
                                    <span style="flex:1;"></span>
                                    <span id="unitInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                </div>
                                <div style="flex:1;overflow:hidden;">
                                    <div id="unitSubTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" tabPosition="left" onactivechanged="onUnitSubTabChanged">
                                        <!-- 采集单元 -->
                                        <div title="AcqUnit" name="acq" style="height:100%;">
                                            <div class="unit-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="acqUnitRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="acqUnitAddBtn" class="mini-button" iconCls="add">Add Unit</button>
                                                    <button id="acqUnitAddGroupBtn" class="mini-button" iconCls="add">Add Group</button>
                                                    <button id="acqUnitAddCtrlGroupBtn" class="mini-button" iconCls="add">Add Ctrl Group</button>
                                                    <span class="separator"></span>
                                                    <button id="acqUnitSaveBtn" class="mini-button" iconCls="save">Save</button>
                                                    <span class="separator"></span>
                                                    <button id="acqUnitExportBtn" class="mini-button" iconCls="export">Export</button>
                                                    <button id="acqUnitImportBtn" class="mini-button" iconCls="import">Import</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="acqUnitInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="acqUnitProtocolTreeContainer">
                                                        <div id="acqUnitProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="acqUnitListContainer">
                                                        <div id="acqUnitList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Unit</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-config">
                                                        <div id="acqUnitRightTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                            <div title="Properties" name="props" style="height:100%;">
                                                                <div id="acqUnitPropsPlaceholder" class="sub-tab-placeholder">Properties</div>
                                                            </div>
                                                            <div title="Config" name="config" style="height:100%;">
                                                                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                    <button id="acqUnitConfigSelectAllBtn" class="mini-button">Select All</button>
                                                                    <button id="acqUnitConfigDeselectAllBtn" class="mini-button">Deselect All</button>
                                                                    <span style="flex:1;"></span>
                                                                </div>
                                                                <div style="flex:1;overflow:auto;padding:4px;">
                                                                    <div id="acqUnitConfigPlaceholder" class="sub-tab-placeholder" style="height:100%;">Acq Unit Config Table</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 显示单元 -->
                                        <div title="DisplayUnit" name="display" style="height:100%;">
                                            <div class="unit-layout" style="flex-direction:column;">
                                                <div class="mini-toolbar" style="flex-shrink:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;background:#fafafa;">
                                                    <button id="displayUnitRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                                    <span class="separator"></span>
                                                    <button id="displayUnitAddBtn" class="mini-button" iconCls="add">Add</button>
                                                    <span class="separator"></span>
                                                    <button id="displayUnitSaveBtn" class="mini-button" iconCls="save">Save</button>
                                                    <span class="separator"></span>
                                                    <button id="displayUnitExportBtn" class="mini-button" iconCls="export">Export</button>
                                                    <button id="displayUnitImportBtn" class="mini-button" iconCls="import">Import</button>
                                                    <span style="flex:1;"></span>
                                                    <span id="displayUnitInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                                </div>
                                                <div style="display:flex;flex:1;overflow:hidden;">
                                                    <div class="left-protocol" id="displayUnitProtocolTreeContainer">
                                                        <div id="displayUnitProtocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Protocol</div>
                                                        </div>
                                                    </div>
                                                    <div class="middle-list" id="displayUnitListContainer">
                                                        <div id="displayUnitList" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true">
                                                            <div property="emptyText" class="empty-msg">No Unit</div>
                                                        </div>
                                                    </div>
                                                    <div class="right-config">
                                                        <div id="displayUnitRightTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                            <div title="Properties" name="props" style="height:100%;">
                                                                <div id="displayUnitPropsPlaceholder" class="sub-tab-placeholder">Properties</div>
                                                            </div>
                                                            <div title="Config" name="config" style="height:100%;">
                                                                <div style="display:flex;flex-direction:column;height:100%;">
                                                                    <div style="flex:1;display:flex;flex-direction:column;border-bottom:1px solid #e8e8e8;">
                                                                        <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                            <span>Acq Items</span>
                                                                            <span class="separator"></span>
                                                                            <button id="displayAcqSelectAllBtn" class="mini-button">Select All</button>
                                                                            <button id="displayAcqDeselectAllBtn" class="mini-button">Deselect All</button>
                                                                            <span style="flex:1;"></span>
                                                                        </div>
                                                                        <div style="flex:1;overflow:auto;padding:4px;">
                                                                            <div id="displayAcqItemsPlaceholder" class="sub-tab-placeholder">Acq Items Config</div>
                                                                        </div>
                                                                    </div>
                                                                    <div style="flex:1;display:flex;flex-direction:column;">
                                                                        <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                            <span>Ctrl Items</span>
                                                                            <span class="separator"></span>
                                                                            <button id="displayCtrlSelectAllBtn" class="mini-button">Select All</button>
                                                                            <button id="displayCtrlDeselectAllBtn" class="mini-button">Deselect All</button>
                                                                            <span style="flex:1;"></span>
                                                                        </div>
                                                                        <div style="flex:1;overflow:auto;padding:4px;">
                                                                            <div id="displayCtrlItemsPlaceholder" class="sub-tab-placeholder">Ctrl Items Config</div>
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
        
        var loginUserProtocolConfigModuleRight=getRoleModuleRight(context + '/roleManagerController/getRoleModuleRight','DriverManagement');
        var editFlag=false;
        if (typeof loginUserProtocolConfigModuleRight !== 'undefined') {
            editFlag = (loginUserProtocolConfigModuleRight.editFlag == 1);
        }

        //协议配置
        var protocolItemsConfigHandsontableHelper = null;
        var protocolPropertiesHandsontableHelper = null;
        var protocolItemsMeaningConfigHandsontableHelper = null;
        var protocolExtendedFieldConfigHandsontableHelper = null;
        var protocolSwitchingValueBitStatusConfigHandsontableHelper = null;

        var protocolExtendedFieldHighLowByteConfigHandsontableHelper = null;
        var protocolExtendedFieldMeaningConfigHandsontableHelper = null;
        var protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = null;

        //采控单元
        var protocolAcqUnitConfigItemsHandsontableHelper = null;
        var protocolConfigAcqUnitPropertiesHandsontableHelper = null;

        //报警单元
        var protocolConfigAlarmUnitPropertiesHandsontableHelper = null;
        var protocolAlarmUnitConfigNumItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigSwitchItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigEnumItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigCommStatusItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = null;
        var protocolAlarmUnitConfigRunStatusItemsHandsontableHelper = null;

        //显示单元
        var protocolDisplayUnitAcqItemsConfigHandsontableHelper = null;
        var protocolDisplayUnitCtrlItemsConfigHandsontableHelper = null;
        var protocolDisplayUnitPropertiesHandsontableHelper = null;

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

        function onProtocolSubTabChanged(e) {
            if (isInitializing) return;
            if (selectedDeviceTypeId) loadDataForCurrentTab(selectedDeviceTypeId);
        }
        
        function onProtocolSubTabChanged(e) {
            if (isInitializing) return;
            // 获取当前选中的协议节点
            var protocolTree = mini.get('protocolTree');
            if (!protocolTree) return;
            var selectedNode = protocolTree.getSelectedNode();
            if (selectedNode && selectedNode.classes === 1) {
                // 直接根据选中的协议节点和当前子标签加载数据
                loadProtocolDetailData(selectedNode);
            } else {
                // 如果没有选中协议节点，可能需要清空或显示空状态
                // 可选：清空表格
            }
        }

        function onExtendedSubTabChanged(e) {
            if (isInitializing) return;
            var protocolTree = mini.get('protocolTree');
            if (!protocolTree) return;
            var selectedNode = protocolTree.getSelectedNode();
            if (selectedNode && selectedNode.classes === 1) {
                // 根据当前协议和扩展子标签重新加载
                var extSub = mini.get('extendedSubTabs');
                if (!extSub) return;
                var extActive = extSub.getActiveTab();
                if (!extActive) return;

                var protocolCode = selectedNode.code || '';
                var protocolName = selectedNode.text || '';

                if (extActive.name === 'numeric') {
                    CreateProtocolExtendedFieldConfigInfoTable(protocolName, 1, protocolCode);
                } else if (extActive.name === 'highlow') {
                    CreateProtocolExtendedFieldHighLowByteConfigInfoTable(protocolName, 1, protocolCode);
                }
            }
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
                    protocolTree.load(context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData');
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
                    listTreeId = 'acqUnitList';
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
                if (protocolTree) {
                    console.log('加载单元协议树：' + protocolTreeId + ', 设备类型ID：' + deviceTypeId);
                    protocolTree.loadData([{
                        id: 1,
                        text: '协议A'
                    }, {
                        id: 2,
                        text: '协议B'
                    }]);
                }
                if (listTree) {
                    console.log('加载单元列表：' + listTreeId + ', 设备类型ID：' + deviceTypeId);
                    listTree.loadData([{
                        id: 1,
                        text: '单元1'
                    }, {
                        id: 2,
                        text: '单元2'
                    }]);
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
                    console.log('加载实例协议树：' + protocolTreeId + ', 设备类型ID：' + deviceTypeId);
                    protocolTree.loadData([{
                        id: 1,
                        text: '协议X'
                    }, {
                        id: 2,
                        text: '协议Y'
                    }]);
                }
                if (listTree) {
                    console.log('加载实例列表：' + listTreeId + ', 设备类型ID：' + deviceTypeId);
                    listTree.loadData([{
                        id: 1,
                        text: '实例1'
                    }, {
                        id: 2,
                        text: '实例2'
                    }]);
                }
            }
        }
        
        function loadProtocolTree(){
        	var protocolTree = mini.get('protocolTree');
            if (protocolTree) {
                protocolTree.load();
            }
        }

        function onProtocolTreeBeforeLoad(e) {
            var params = e.params || {};
            var deviceTypeIds = selectedDeviceTypeId || '';
            params.deviceTypeIds = deviceTypeIds;
            e.params = params;
        }

        function onProtocolTreeLoad(e) {
            var tree = e.sender;
            var root = tree.getRootNode();
            if (!root) return;

            var protocolNodes = [];
            function collect(node) {
                if (node.children && node.children.length > 0) {
                    for (var i = 0; i < node.children.length; i++) {
                        collect(node.children[i]);
                    }
                } else {
                    if (node.classes == 1) {
                        protocolNodes.push(node);
                    }
                }
            }
            collect(root);

            if (protocolNodes.length > 0) {
                var targetNode = null;
                // 如果有新添加的协议名称，优先选中它
                if (window._newProtocolName) {
                    for (var i = 0; i < protocolNodes.length; i++) {
                        if (protocolNodes[i].text === window._newProtocolName) {
                            targetNode = protocolNodes[i];
                            break;
                        }
                    }
                    // 清除标记，避免重复选中
                    window._newProtocolName = null;
                }
                // 如果没有找到或没有新协议，默认选中第一个
                if (!targetNode) {
                    targetNode = protocolNodes[0];
                }
                setTimeout(function() {
                	tree.selectNode(targetNode);
                }, 50);
            }
        }
        function onProtocolNodeSelect(e) {
            var node = e.node;
            if (node && node.classes === 1) { // 协议节点
                // 显示信息标签
                //var infoLabel = document.getElementById('protocolInfoLabel');
                //if (infoLabel) {
                //    infoLabel.innerHTML = '【<font color="red">' + node.text + '</font>】';
                //}
                // 根据当前激活的子标签加载对应数据
                loadProtocolDetailData(node);
            }
        }
        

     // 右键菜单打开前事件（控制显示/隐藏）
     function onProtocolTreeBeforeMenu(e) {
         var tree = mini.get('protocolTree');
         var menu = e.sender;
         var node = tree.getSelectedNode(); // 注意：右键点击时，树会自动选中该节点

         // 只对协议节点（classes===1）显示菜单，目录节点不显示
         if (!node || node.classes !== 1) {
             e.cancel = true; // 阻止菜单弹出
             e.htmlEvent.preventDefault();
             return;
         }

         // 更新菜单项文字（国际化）
         var deleteText = _loginUserLanguageResource.deleteData;
         document.getElementById('protocolTreeMenuDeleteText').textContent = deleteText;

         // 根据权限禁用菜单项（如果需要）
         var deleteItem = mini.getbyName('delete', menu);
         if (!editFlag) {
              deleteItem.disable();
         } else {
              deleteItem.enable();
         }
     }

     // 删除协议节点
     function deleteProtocolNode(e) {
    	 var tree = mini.get('protocolTree');
         var menu = e.sender;
         var node = tree.getSelectedNode();
    	 if (!node) {
             return;
         }
         var protocolCode = node.code;
         var protocolName = node.text;

         mini.confirm(_loginUserLanguageResource.confirmDelete, _loginUserLanguageResource.confirm, function(action) {
             if (action == 'ok') {
                 var configInfo = {
                     delidslist: [protocolCode]
                 };
                 saveModbusProtocolAddrMappingConfigData(configInfo);
             }
         });
     }
        
        
        function getCurrentProtocolCode() {
            var tree = mini.get('protocolTree');
            if (!tree) return '';
            var selected = tree.getSelectedNode();
            if (selected && selected.classes === 1) {
                return selected.code || '';
            }
            return '';
        }
     // ================================================================
     // 根据激活子标签加载数据
     // ================================================================
     function loadProtocolDetailData(node) {
         if (!node) return;
         var protoSub = mini.get('protocolSubTabs');
         if (!protoSub) return;
         var activeTab = protoSub.getActiveTab();
         if (!activeTab) return;
         var tabName = activeTab.name; // props, config, extended

         // 获取协议 code
         var protocolCode = node.code || '';
         var protocolName = node.text || '';

         if (tabName === 'props') {
             // 加载属性
             CreateProtocolConfigAddrMappingPropertiesInfoTable(node);
         } else if (tabName === 'config') {
             // 加载配置项
             CreateModbusProtocolAddrMappingItemsConfigInfoTable(protocolName, 1, protocolCode);
         } else if (tabName === 'extended') {
             // 加载扩展字段，根据扩展子标签决定
             var extSub = mini.get('extendedSubTabs');
             if (extSub) {
                 var extActive = extSub.getActiveTab();
                 if (extActive) {
                     if (extActive.name === 'numeric') {
                         CreateProtocolExtendedFieldConfigInfoTable(protocolName, 1, protocolCode);
                     } else if (extActive.name === 'highlow') {
                         CreateProtocolExtendedFieldHighLowByteConfigInfoTable(protocolName, 1, protocolCode);
                     }
                 }
             }
         }
     }
     
     function CreateProtocolConfigAddrMappingPropertiesInfoTable(data) {
    	    var root = [];
    	    if (data.classes === 0) {
    	        root.push({ id: 1, title: _loginUserLanguageResource.rootNode, value: _loginUserLanguageResource.protocolList });
    	    } else if (data.classes === 1) {
    	        root.push({ id: 1, title: _loginUserLanguageResource.protocolName, value: data.text });
    	        root.push({ id: 2, title: _loginUserLanguageResource.sequenceNumber, value: data.sort });
    	        root.push({ id: 3, title: _loginUserLanguageResource.language, value: data.languageName });
    	        root.push({ id: 4, title: _loginUserLanguageResource.protocolBelongTo, value: data.deviceTypeAllPath });
    	    }
    	    if (protocolPropertiesHandsontableHelper === null || protocolPropertiesHandsontableHelper.hot === undefined) {
    	        protocolPropertiesHandsontableHelper = ProtocolPropertiesHandsontableHelper.createNew("ModbusProtocolAddrMappingPropertiesTableInfoDiv_id");
    	        
    	        
    	        var colHeaders=[_loginUserLanguageResource.idx,_loginUserLanguageResource.variable,_loginUserLanguageResource.value];
    			var columns=[{data:'id'},{data:'title'},{data:'value'}];
    			protocolPropertiesHandsontableHelper.colHeaders=colHeaders;
    			protocolPropertiesHandsontableHelper.columns=columns;
    	        protocolPropertiesHandsontableHelper.classes = data.classes;
    	        protocolPropertiesHandsontableHelper.createTable(root);
    	    } else {
    	        protocolPropertiesHandsontableHelper.classes = data.classes;
    	        protocolPropertiesHandsontableHelper.hot.loadData(root);
    	        protocolPropertiesHandsontableHelper.hot.render();
    	    }
    	}
     function CreateModbusProtocolAddrMappingItemsConfigInfoTable(protocolName, classes, code) {
    	    // 可选：显示加载遮罩
    	    $.ajax({
    	        type: 'POST',
    	        url: context + '/acquisitionUnitManagerController/getProtocolItemsConfigData',
    	        data: {
    	            protocolName: protocolName,
    	            classes: classes,
    	            code: code
    	        },
    	        dataType: 'json',
    	        success: function (result) {
    	            var dataLength = result.totalRoot.length;
    	            var defultNullData = [];
    	            var defultDataLength = 100;
    	            for (var i = 0; i < defultDataLength; i++) {
    	                defultNullData.push({});
    	            }
    	            var tableData = result.totalRoot;
    	            if (dataLength < defultDataLength) {
    	                if (defultDataLength - dataLength < 10) {
    	                    for (var i = 0; i < 10; i++) tableData.push({});
    	                } else {
    	                    for (var i = dataLength; i < defultDataLength; i++) tableData.push({});
    	                }
    	            } else {
    	                for (var i = 0; i < 10; i++) tableData.push({});
    	            }

    	            if (!protocolItemsConfigHandsontableHelper || !protocolItemsConfigHandsontableHelper.hot) {
    	                protocolItemsConfigHandsontableHelper = ProtocolItemsConfigHandsontableHelper.createNew('ModbusProtocolAddrMappingItemsConfigTableInfoDiv_id');

    	                var colHeaders = [
    	                    ['', '', { label: (_loginUserLanguageResource.lowerComputer || 'Lower Computer'), colspan: 6 }, { label: (_loginUserLanguageResource.upperComputer || 'Upper Computer'), colspan: 5 }],
    	                    [
    	                        _loginUserLanguageResource.idx,
    	                        _loginUserLanguageResource.name,
    	                        _loginUserLanguageResource.startAddress,
    	                        _loginUserLanguageResource.highLowByte,
    	                        _loginUserLanguageResource.storeDataType,
    	                        _loginUserLanguageResource.quantity,
    	                        _loginUserLanguageResource.RWType,
    	                        _loginUserLanguageResource.acqMode,
    	                        _loginUserLanguageResource.IFDataType,
    	                        _loginUserLanguageResource.prec,
    	                        _loginUserLanguageResource.ratio,
    	                        _loginUserLanguageResource.unit,
    	                        _loginUserLanguageResource.resolutionMode
    	                    ]
    	                ];
    	                var columns = [
    	                    { data: 'id' },
    	                    { data: 'title' },
    	                    { data: 'addr', type: 'text', allowInvalid: true, validator: function (val, callback) { return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolItemsConfigHandsontableHelper); } },
    	                    { data: 'highLowByte', type: 'dropdown', strict: true, allowInvalid: false, source: ['', (_loginUserLanguageResource.highByte || 'High'), (_loginUserLanguageResource.lowByte || 'Low')] },
    	                    { data: 'storeDataType', type: 'dropdown', strict: true, allowInvalid: false, source: ['bit', 'byte', 'int16', 'uint16', 'float32', 'float64', 'bcd'] },
    	                    { data: 'quantity', type: 'text', allowInvalid: true, validator: function (val, callback) { return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolItemsConfigHandsontableHelper); } },
    	                    { data: 'RWType', type: 'dropdown', strict: true, allowInvalid: false, source: [(_loginUserLanguageResource.readOnly || 'Read Only'), (_loginUserLanguageResource.writeOnly || 'Write Only'), (_loginUserLanguageResource.readWrite || 'Read Write')] },
    	                    { data: 'acqMode', type: 'dropdown', strict: true, allowInvalid: false, source: [(_loginUserLanguageResource.activeAcqModel || 'Active'), (_loginUserLanguageResource.passiveAcqModel || 'Passive')] },
    	                    { data: 'IFDataType', type: 'dropdown', strict: true, allowInvalid: false, source: ['bool', 'int', 'float32', 'float64', 'string'] },
    	                    { data: 'prec', type: 'text', allowInvalid: true, validator: function (val, callback) { return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolItemsConfigHandsontableHelper); } },
    	                    { data: 'ratio', type: 'text', allowInvalid: true, validator: function (val, callback) { return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolItemsConfigHandsontableHelper); } },
    	                    { data: 'unit' },
    	                    { data: 'resolutionMode', type: 'dropdown', strict: true, allowInvalid: false, source: [(_loginUserLanguageResource.switchingValue || 'Switching'), (_loginUserLanguageResource.enumValue || 'Enum'), (_loginUserLanguageResource.numericValue || 'Numeric')] }
    	                ];
    	                protocolItemsConfigHandsontableHelper.colHeaders = colHeaders;
    	                protocolItemsConfigHandsontableHelper.columns = columns;
    	                protocolItemsConfigHandsontableHelper.createTable(tableData);
    	            } else {
    	                protocolItemsConfigHandsontableHelper.hot.deselectCell();
    	                protocolItemsConfigHandsontableHelper.hot.loadData(tableData);
    	            }

    	            // 初始化数据映射（用于重复检查）
    	            protocolItemsConfigHandsontableHelper.initTitleDataMap(tableData);
    	            protocolItemsConfigHandsontableHelper.initAddressDataMap(tableData);

    	            // 选中第一行或保持之前的选中
    	            var selectedRow = 0;
    	            var savedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
    	            if (dataLength > savedRow) {
    	                selectedRow = savedRow;
    	            }
    	            protocolItemsConfigHandsontableHelper.hot.selectCell(selectedRow ,'title');
    	            // 更新含义表
    	            var protocolCode = getCurrentProtocolCode();
    	            var itemTitle = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'title');
    	            var itemAddr = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'addr');
    	            var highLowByte = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'highLowByte');
    	            var resolutionMode = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'resolutionMode');
    	            var quantity = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'quantity');
    	            setTimeout(function() {
    	            	CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
                    }, 50);
    	        },
    	        error: function () {
    	            mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
    	        }
    	    });
    	}
     function CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, isNew) {
    	    // 销毁旧的 helper
    	    if (protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
    	        protocolItemsMeaningConfigHandsontableHelper.hot.destroy();
    	        protocolItemsMeaningConfigHandsontableHelper = null;
    	    }

    	    var highLow = '';
    	    if (highLowByte === (_loginUserLanguageResource.highByte || 'High')) highLow = 'high';
    	    else if (highLowByte === (_loginUserLanguageResource.lowByte || 'Low')) highLow = 'low';

    	    var resolutionModeValue = 2;
    	    if (resolutionMode === (_loginUserLanguageResource.switchingValue || 'Switching')) resolutionModeValue = 0;
    	    else if (resolutionMode === (_loginUserLanguageResource.enumValue || 'Enum')) resolutionModeValue = 1;

    	    quantity = parseInt(quantity) || 0;

    	    $.ajax({
    	        type: 'POST',
    	        url: context + '/acquisitionUnitManagerController/getProtocolItemMeaningConfigData',
    	        data: {
    	            protocolCode: protocolCode,
    	            itemAddr: itemAddr,
    	            highLowByte: highLow,
    	            resolutionMode: resolutionModeValue,
    	            quantity: quantity
    	        },
    	        dataType: 'json',
    	        success: function (result) {
    	            var data = result.totalRoot || [];
    	            // 更新含义面板标题（使用 jQuery 操作 DOM，或通过 miniui 标签）
    	            var showInfo = (itemTitle) ? '【<font color="red">' + itemTitle + '</font>】' + (_loginUserLanguageResource.meaning || 'Meaning') : (_loginUserLanguageResource.meaning || 'Meaning');
    	            // 假设含义面板标题对应的元素是 id 为 ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id 的 mini-panel 或 div
    	            // 这里直接设置其标题（如果是 miniui 面板可用 mini.get 设置，但为了兼容，我们使用 jQuery 查找）
    	            // 若该面板是 miniui 控件，可以用 mini.get('ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id').setTitle(showInfo);
    	            // 由于此处可能是 div，我们直接修改其标题文本（根据实际 DOM 结构）
    	            // 假设面板是 miniui 的 Panel，使用 mini.get
    	            var meaningPanel = mini.get('ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id');
    	            if (meaningPanel) {
    	                meaningPanel.setTitle(showInfo);
    	            } else {
    	                // 否则直接修改 HTML
    	                $('#ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id .mini-panel-title').html(showInfo);
    	            }

    	            // 开关量特殊处理：显示/隐藏位状态表
    	            if (resolutionModeValue === 0) {
    	                // 显示位状态表
    	                var splitter = mini.get('meaningAndBitStatusSplitter_Id');
    	                if(splitter){
    	                	splitter.showPane(2);
    	                }
    	                CreateProtocolSwitchingValueBitStatusConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, isNew);
    	            } else {
    	            	var splitter = mini.get('meaningAndBitStatusSplitter_Id');
    	                if(splitter){
    	                	splitter.hidePane(2);
    	                }
    	                if (protocolSwitchingValueBitStatusConfigHandsontableHelper && protocolSwitchingValueBitStatusConfigHandsontableHelper.hot) {
    	                    protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.destroy();
    	                    protocolSwitchingValueBitStatusConfigHandsontableHelper = null;
    	                }
    	            }

    	            // 补充空行（非开关量模式）
    	            if (resolutionModeValue !== 0) {
    	                var dataLength = data.length;
    	                var defultDataLength = 50;
    	                if (dataLength < defultDataLength) {
    	                    if (defultDataLength - dataLength < 10) {
    	                        for (var i = 0; i < 10; i++) data.push({});
    	                    } else {
    	                        for (var i = dataLength; i < defultDataLength; i++) data.push({});
    	                    }
    	                } else {
    	                    for (var i = 0; i < 10; i++) data.push({});
    	                }
    	            }

    	            if (!protocolItemsMeaningConfigHandsontableHelper || !protocolItemsMeaningConfigHandsontableHelper.hot) {
    	                protocolItemsMeaningConfigHandsontableHelper = ProtocolItemsMeaningConfigHandsontableHelper.createNew('ModbusProtocolAddrMappingItemsMeaningTableInfoDiv_id');
    	                var colHeaders, columns;
    	                if (resolutionModeValue === 0) {
    	                    colHeaders = [(_loginUserLanguageResource.bit || 'Bit'), (_loginUserLanguageResource.meaning || 'Meaning'), ''];
    	                    columns = [
    	                        { data: 'title' },
    	                        { data: 'meaning' },
    	                        { data: 'value' }
    	                    ];
    	                    protocolItemsMeaningConfigHandsontableHelper.hiddenColumns = [2];
    	                    protocolItemsMeaningConfigHandsontableHelper.contextMenu = false;
    	                } else {
    	                    colHeaders = [(_loginUserLanguageResource.value || 'Value'), (_loginUserLanguageResource.meaning || 'Meaning')];
    	                    columns = [
    	                        { data: 'value', type: 'text', allowInvalid: true, validator: function (val, callback) { return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolItemsMeaningConfigHandsontableHelper); } },
    	                        { data: 'meaning' }
    	                    ];
    	                    protocolItemsMeaningConfigHandsontableHelper.hiddenColumns = [];
    	                    protocolItemsMeaningConfigHandsontableHelper.contextMenu = {
    	                        items: {
    	                            "row_above": { name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above') },
    	                            "row_below": { name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below') },
    	                            "col_left": { name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left') },
    	                            "col_right": { name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right') },
    	                            "remove_row": { name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row') },
    	                            "remove_col": { name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column') },
    	                            "merge_cell": { name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells') },
    	                            "copy": { name: (_loginUserLanguageResource.contextMenu_copy || 'Copy') },
    	                            "cut": { name: (_loginUserLanguageResource.contextMenu_cut || 'Cut') }
    	                        }
    	                    };
    	                }
    	                protocolItemsMeaningConfigHandsontableHelper.colHeaders = colHeaders;
    	                protocolItemsMeaningConfigHandsontableHelper.columns = columns;
    	                protocolItemsMeaningConfigHandsontableHelper.itemResolutionMode = resolutionModeValue;
    	                protocolItemsMeaningConfigHandsontableHelper.createTable(data);
    	            } else {
    	                protocolItemsMeaningConfigHandsontableHelper.hot.loadData(data);
    	                protocolItemsMeaningConfigHandsontableHelper.itemResolutionMode = resolutionModeValue;
    	            }
    	        },
    	        error: function () {
    	            mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
    	        }
    	    });
    	}
     function CreateProtocolSwitchingValueBitStatusConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, isNew) {
    	    if (protocolSwitchingValueBitStatusConfigHandsontableHelper && protocolSwitchingValueBitStatusConfigHandsontableHelper.hot) {
    	        protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.destroy();
    	        protocolSwitchingValueBitStatusConfigHandsontableHelper = null;
    	    }
    	    $.ajax({
    	        type: 'POST',
    	        url: context + '/acquisitionUnitManagerController/getProtocolSwitchingValueBitStatusConfigData',
    	        data: {
    	            protocolCode: protocolCode,
    	            itemAddr: itemAddr,
    	            highLowByte: highLowByte,
    	            quantity: quantity,
    	            resolutionMode: resolutionMode
    	        },
    	        dataType: 'json',
    	        success: function (result) {
    	            var data = result.totalRoot || [];
    	            // 更新位状态面板标题
    	            var showInfo = (itemTitle) ? '【<font color="red">' + itemTitle + '</font>】' + (_loginUserLanguageResource.switchingValueBitStatusConfig || 'Bit Status Config') : (_loginUserLanguageResource.switchingValueBitStatusConfig || 'Bit Status Config');
    	            var bitPanel = mini.get('ProtocolSwitchingValueBitStatusConfigPanel_Id');
    	            if (bitPanel) {
    	                bitPanel.setTitle(showInfo);
    	            } else {
    	                $('#ProtocolSwitchingValueBitStatusConfigPanel_Id .mini-panel-title').html(showInfo);
    	            }

    	            if (!protocolSwitchingValueBitStatusConfigHandsontableHelper || !protocolSwitchingValueBitStatusConfigHandsontableHelper.hot) {
    	                protocolSwitchingValueBitStatusConfigHandsontableHelper = ProtocolSwitchingValueBitStatusConfigHandsontableHelper.createNew('ProtocolSwitchingValueBitStatusTableInfoDiv_id');
    	                var colHeaders = [(_loginUserLanguageResource.bit || 'Bit') + '/' + (_loginUserLanguageResource.value || 'Value'), (_loginUserLanguageResource.switchingValuStatus || 'Status'), '', ''];
    	                var columns = [
    	                    { data: 'title', type: 'text' },
    	                    { data: 'status' },
    	                    { data: 'bitIndex' },
    	                    { data: 'value' }
    	                ];
    	                protocolSwitchingValueBitStatusConfigHandsontableHelper.colHeaders = colHeaders;
    	                protocolSwitchingValueBitStatusConfigHandsontableHelper.columns = columns;
    	                protocolSwitchingValueBitStatusConfigHandsontableHelper.createTable(data);
    	            } else {
    	                protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.loadData(data);
    	            }
    	        },
    	        error: function () {
    	            mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
    	        }
    	    });
    	}
     
     var ProtocolPropertiesHandsontableHelper = {
    			createNew: function (divid) {
    		        var protocolPropertiesHandsontableHelper = {};
    		        protocolPropertiesHandsontableHelper.hot = '';
    		        protocolPropertiesHandsontableHelper.classes =null;
    		        protocolPropertiesHandsontableHelper.divid = divid;
    		        protocolPropertiesHandsontableHelper.validresult=true;//数据校验
    		        protocolPropertiesHandsontableHelper.colHeaders=[];
    		        protocolPropertiesHandsontableHelper.columns=[];
    		        protocolPropertiesHandsontableHelper.AllData=[];
    		        
    		        protocolPropertiesHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
    		            Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            td.style.backgroundColor = 'rgb(245, 245, 245)';
    		            td.style.whiteSpace='nowrap'; //文本不换行
    	            	td.style.overflow='hidden';//超出部分隐藏
    	            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
    		        }
    		        
    		        protocolPropertiesHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
    		            Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            td.style.whiteSpace='nowrap'; //文本不换行
    	            	td.style.overflow='hidden';//超出部分隐藏
    	            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
    		        }
    		        
    		        protocolPropertiesHandsontableHelper.createTable = function (data) {
    		        	$('#'+protocolPropertiesHandsontableHelper.divid).empty();
    		        	var hotElement = document.querySelector('#'+protocolPropertiesHandsontableHelper.divid);
    		        	protocolPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
    		        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
    		        		theme: 'ht-theme-classic',
    		        		data: data,
    		        		colWidths: [1,8,10],
    		                columns:protocolPropertiesHandsontableHelper.columns,
    		                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
    		                autoWrapRow: true,
    		                rowHeaders: false,//显示行头
    		                colHeaders:protocolPropertiesHandsontableHelper.colHeaders,//显示列头
    		                columnSorting: true,//允许排序
    		                sortIndicator: true,
    		                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
    		                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
    		                filters: true,
    		                renderAllRows: true,
    		                search: true,
    		                contextMenu: {
    		                    items: {
    		                        "copy": {
    		                            name: loginUserLanguageResource.contextMenu_copy
    		                        },
    		                        "cut": {
    		                            name: loginUserLanguageResource.contextMenu_cut
    		                        }
    		                    }
    		                }, 
    		                cells: function (row, col, prop) {
    		                	var cellProperties = {};
    		                    var visualRowIndex = this.instance.toVisualRow(row);
    		                    var visualColIndex = this.instance.toVisualColumn(col);
    		                    
    		                    var protocolConfigModuleEditFlag=1;
    		                    if(protocolConfigModuleEditFlag==1){
    		                    	if(protocolPropertiesHandsontableHelper.classes===0){
    									cellProperties.editor = false;
    									cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
    			                    }else if(protocolPropertiesHandsontableHelper.classes===1){
    			                    	if (visualColIndex ==0 || visualColIndex ==1) {
    										cellProperties.editor = false;
    										cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
    					                }else if(visualColIndex === 2 && visualRowIndex===0){
    				                    	this.validator=function (val, callback) {
    				                    	    return handsontableDataCheck_NotNull(val, callback, row, col, protocolPropertiesHandsontableHelper);
    				                    	}
    				                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
    				                    }else if (visualColIndex === 2 && visualRowIndex===1) {
    				                    	this.validator=function (val, callback) {
    				                    	    return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolPropertiesHandsontableHelper);
    				                    	}
    				                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
    				                    }else if (visualColIndex === 2 && (visualRowIndex===2||visualRowIndex===3) ) {
    				                    	cellProperties.editor = false;
    				                    	cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
    				                    }
    			                    }
    		                    }else{
    								cellProperties.editor = false;
    								cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
    		                    }
    		                    
    		                    return cellProperties;
    		                }
    		        	});
    		        }
    		        protocolPropertiesHandsontableHelper.saveData = function () {}
    		        protocolPropertiesHandsontableHelper.clearContainer = function () {
    		        	protocolPropertiesHandsontableHelper.AllData = [];
    		        }
    		        return protocolPropertiesHandsontableHelper;
    		    }
    	};
     var ProtocolItemsConfigHandsontableHelper = {
    		    createNew: function (divid) {
    		        var helper = {};
    		        helper.hot = null;
    		        helper.divid = divid;
    		        helper.validresult = true;
    		        helper.colHeaders = [];
    		        helper.columns = [];
    		        helper.AllData = [];
    		        helper.Data = [];
    		        helper.titleDataMap = new Map();
    		        helper.addressDataMap = new Map();

    		        helper.initTitleDataMap = function (data) {
    		            helper.titleDataMap.clear();
    		            data.forEach((row, index) => {
    		                const value = row.title;
    	                    if (row.title==undefined || !value) return;
    	                    if (!helper.titleDataMap.has(value)) {
    	                    	helper.titleDataMap.set(value, [index]);
    	                    } else {
    	                    	helper.titleDataMap.get(value).push(index);
    	                    }
    		            });
    		        };

    		        helper.getDuplicateCount = function () {
    		            var count = 0;
    		            for (var [value, indexes] of helper.titleDataMap.entries()) {
    		                if (indexes.length > 1) count += indexes.length;
    		            }
    		            return count;
    		        };

    		        helper.getDuplicateRowList = function () {
    		            var list = [];
    		            for (var [value, indexes] of helper.titleDataMap.entries()) {
    		                if (indexes.length > 1) {
    		                    for (var i = 0; i < indexes.length; i++) list.push(indexes[i]);
    		                }
    		            }
    		            return list;
    		        };

    		        helper.initAddressDataMap = function (data) {
    		            helper.addressDataMap.clear();
    		            data.forEach((row, index) => {
    		                var value = "";
    	                    var addr=row.addr+"";
    	                    var highLowByte=row.highLowByte+"";
    	                    if(addr!='' || highLowByte!=''){
    	                    	value=addr+'_'+highLowByte;
    	                	}
    	                    if ( (row.addr==undefined && row.highLowByte==undefined) || !value) return;
    	                    
    	                    if (!helper.addressDataMap.has(value)) {
    	                    	helper.addressDataMap.set(value, [index]);
    	                    } else {
    	                    	helper.addressDataMap.get(value).push(index);
    	                    }
    		            });
    		        };

    		        helper.getAddrDuplicateCount = function () {
    		            var count = 0;
    		            for (var [key, indexes] of helper.addressDataMap.entries()) {
    		                if (indexes.length > 1) count += indexes.length;
    		            }
    		            return count;
    		        };

    		        helper.getAddrDuplicateRowList = function () {
    		            var list = [];
    		            for (var [key, indexes] of helper.addressDataMap.entries()) {
    		                if (indexes.length > 1) {
    		                    for (var i = 0; i < indexes.length; i++) list.push(indexes[i]);
    		                }
    		            }
    		            return list;
    		        };

    		        helper.uniqueTitleRenderer = function (instance, td, row, col, prop, value, cellProperties) {
    		            if (cellProperties.type === 'checkbox') {
    		                Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
    		            } else if (cellProperties.type === 'dropdown') {
    		                Handsontable.renderers.DropdownRenderer.apply(this, arguments);
    		            } else {
    		                Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            }
    		            if (cellProperties.type !== 'checkbox') {
    		                td.style.whiteSpace = 'nowrap';
    		                td.style.overflow = 'hidden';
    		                td.style.textOverflow = 'ellipsis';
    		            }
    		            if (prop === 'title') {
    		                if (value && helper.titleDataMap.has(value)) {
    		                    var rows = helper.titleDataMap.get(value);
    		                    if (rows.length > 1 && rows.includes(row)) {
    		                        td.style.backgroundColor = '#FF4C42';
    		                    }
    		                }
    		            } else if (prop === 'addr' || prop === 'highLowByte') {
    		                var oldAddr = (prop === 'addr') ? value : instance.getDataAtRowProp(row, 'addr');
    		                var oldHigh = (prop === 'highLowByte') ? value : instance.getDataAtRowProp(row, 'highLowByte');
    		                if (oldAddr !== '' || oldHigh !== '') {
    		                    var key = oldAddr + '_' + oldHigh;
    		                    if (helper.addressDataMap.has(key)) {
    		                        var rows = helper.addressDataMap.get(key);
    		                        if (rows.length > 1 && rows.includes(row)) {
    		                            td.style.backgroundColor = '#FF4C42';
    		                        }
    		                    }
    		                }
    		            }
    		        };

    		        helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
    		            Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            td.style.whiteSpace = 'nowrap';
    		            td.style.overflow = 'hidden';
    		            td.style.textOverflow = 'ellipsis';
    		        };

    		        helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
    		            Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            td.style.backgroundColor = 'rgb(245, 245, 245)';
    		            td.style.whiteSpace = 'nowrap';
    		            td.style.overflow = 'hidden';
    		            td.style.textOverflow = 'ellipsis';
    		        };

    		        helper.createTable = function (data) {
    		            $('#' + helper.divid).empty();
    		            var hotElement = document.querySelector('#' + helper.divid);
    		            helper.hot = new Handsontable(hotElement, {
    		                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
    		                theme: 'ht-theme-classic',
    		                data: data,
    		                hiddenColumns: {
    		                    columns: [0, 3],
    		                    indicators: false,
    		                    copyPasteEnabled: false
    		                },
    		                colWidths: [50, 200, 80, 80, 90, 90, 80, 80, 90, 80, 80, 80, 160],
    		                columns: helper.columns,
    		                stretchH: 'all',
    		                autoWrapRow: true,
    		                rowHeaders: true,
    		                bindRowsWithHeaders: 'strict',
    		                nestedHeaders: helper.colHeaders,
    		                columnHeaderHeight: 28,
    		                columnSorting: true,
    		                sortIndicator: true,
    		                manualColumnResize: true,
    		                manualRowResize: true,
    		                filters: true,
    		                renderAllRows: true,
    		                search: true,
    		                outsideClickDeselects: false,
    		                contextMenu: {
    		                    items: {
    		                        "row_above": { name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above') },
    		                        "row_below": { name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below') },
    		                        "col_left": { name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left') },
    		                        "col_right": { name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right') },
    		                        "remove_row": { name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row') },
    		                        "remove_col": { name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column') },
    		                        "merge_cell": { name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells') },
    		                        "copy": { name: (_loginUserLanguageResource.contextMenu_copy || 'Copy') },
    		                        "cut": { name: (_loginUserLanguageResource.contextMenu_cut || 'Cut') }
    		                    }
    		                },
    		                cells: function (row, col, prop) {
    		                    var cellProperties = {};
    		                    var visualRowIndex = this.instance.toVisualRow(row);
    		                    var visualColIndex = this.instance.toVisualColumn(col);
    		                    // 权限控制（可从全局变量读取）
    		                    if (!editFlag) {
    		                        cellProperties.editor = false;
    		                    } else {
    		                        if (visualColIndex === 0) {
    		                            cellProperties.editor = false;
    		                        }
    		                    }
    		                    if (prop === 'storeDataType') {
    		                        var highLowByte=this.instance.getDataAtRowProp(row,'highLowByte')
    	            				this.type = 'dropdown';
    	                            this.strict = true;
    	                            this.allowInvalid = false;
    	                            if(isNotVal(highLowByte)){
    	                            	this.source = ['bit','byte'];
    	                            }else{
    	                            	this.source = ['bit','byte','int16','uint16','float32','float64','bcd'];
    	                            }
    		                    }
    		                    if (visualColIndex === 0) {
    		                        cellProperties.renderer = helper.addBoldBg;
    		                    } else {
    		                        cellProperties.renderer = helper.uniqueTitleRenderer;
    		                    }
    		                    return cellProperties;
    		                },
    		                afterChange: function (changes, source) {
    		                    if (!changes) return;
    		                    var needUpdate = false;
    		                    changes.forEach(function (change) {
    		                        var row = change[0], prop = change[1], oldVal = change[2], newVal = change[3];
    		                        if (prop === 'title') {
    		                            // 更新 titleDataMap
    		                            if (oldVal && helper.titleDataMap.has(oldVal)) {
    		                                var rows = helper.titleDataMap.get(oldVal);
    		                                var idx = rows.indexOf(row);
    		                                if (idx !== -1) rows.splice(idx, 1);
    		                                if (rows.length === 0) helper.titleDataMap.delete(oldVal);
    		                            }
    		                            if (newVal) {
    		                                if (!helper.titleDataMap.has(newVal)) {
    		                                    helper.titleDataMap.set(newVal, [row]);
    		                                } else {
    		                                    var rows = helper.titleDataMap.get(newVal);
    		                                    if (!rows.includes(row)) rows.push(row);
    		                                }
    		                            }
    		                            if (oldVal !== newVal) needUpdate = true;
    		                        } else if (prop === 'addr' || prop === 'highLowByte') {
    		                            var oldAddr = (prop === 'addr') ? oldVal : helper.hot.getDataAtRowProp(row, 'addr');
    		                            var oldHigh = (prop === 'highLowByte') ? oldVal : helper.hot.getDataAtRowProp(row, 'highLowByte');
    		                            var newAddr = (prop === 'addr') ? newVal : helper.hot.getDataAtRowProp(row, 'addr');
    		                            var newHigh = (prop === 'highLowByte') ? newVal : helper.hot.getDataAtRowProp(row, 'highLowByte');
    		                            var oldKey = (oldAddr !== '' || oldHigh !== '') ? oldAddr + '_' + oldHigh : null;
    		                            var newKey = (newAddr !== '' || newHigh !== '') ? newAddr + '_' + newHigh : null;
    		                            if (oldKey && helper.addressDataMap.has(oldKey)) {
    		                                var rows = helper.addressDataMap.get(oldKey);
    		                                var idx = rows.indexOf(row);
    		                                if (idx !== -1) rows.splice(idx, 1);
    		                                if (rows.length === 0) helper.addressDataMap.delete(oldKey);
    		                            }
    		                            if (newKey) {
    		                                if (!helper.addressDataMap.has(newKey)) {
    		                                    helper.addressDataMap.set(newKey, [row]);
    		                                } else {
    		                                    var rows = helper.addressDataMap.get(newKey);
    		                                    if (!rows.includes(row)) rows.push(row);
    		                                }
    		                            }
    		                            if (oldKey !== newKey) needUpdate = true;

    		                            // 若当前选中行变化，刷新含义表
    		                            if (prop === 'highLowByte') {
    		                                var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
    		                                if (selectedRow === row && typeof protocolItemsMeaningConfigHandsontableHelper !== 'undefined' && protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
    		                                    var resolutionMode = helper.hot.getDataAtRowProp(row, 'resolutionMode');
    		                                    if (resolutionMode === (_loginUserLanguageResource.switchingValue || 'Switching')) {
    		                                        var itemTitle = helper.hot.getDataAtRowProp(row, 'title');
    		                                        var itemAddr = helper.hot.getDataAtRowProp(row, 'addr');
    		                                        var highLowByte = newVal;
    		                                        var quantity = helper.hot.getDataAtRowProp(row, 'quantity');
    		                                        var protocolCode = getCurrentProtocolCode();
    		                                        CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
    		                                    }
    		                                }
    		                            }
    		                        } else if (prop === 'resolutionMode') {
    		                            var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
    		                            if (selectedRow === row && typeof protocolItemsMeaningConfigHandsontableHelper !== 'undefined' && protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
    		                                var resolutionMode = newVal;
    		                                var itemTitle = helper.hot.getDataAtRowProp(row, 'title');
    		                                var itemAddr = helper.hot.getDataAtRowProp(row, 'addr');
    		                                var highLowByte = helper.hot.getDataAtRowProp(row, 'highLowByte');
    		                                var quantity = helper.hot.getDataAtRowProp(row, 'quantity');
    		                                var protocolCode = getCurrentProtocolCode();
    		                                CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
    		                            }
    		                        } else if (prop === 'quantity') {
    		                            var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
    		                            if (selectedRow === row && typeof protocolItemsMeaningConfigHandsontableHelper !== 'undefined' && protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
    		                                var resolutionMode = helper.hot.getDataAtRowProp(row, 'resolutionMode');
    		                                if (resolutionMode === (_loginUserLanguageResource.switchingValue || 'Switching')) {
    		                                    var itemTitle = helper.hot.getDataAtRowProp(row, 'title');
    		                                    var itemAddr = helper.hot.getDataAtRowProp(row, 'addr');
    		                                    var highLowByte = helper.hot.getDataAtRowProp(row, 'highLowByte');
    		                                    var quantity = newVal;
    		                                    var protocolCode = getCurrentProtocolCode();
    		                                    CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
    		                                }
    		                            }
    		                        }
    		                    });
    		                    if (needUpdate) helper.hot.render();
    		                },
    		                afterSelectionEnd: function (row, column, row2, column2, selectionLayerLevel) {
    		                    if (row < 0 && row2 < 0) {
    		                        // 只选中表头
    		                        $('#ModbusProtocolAddrMappingItemsSelectRow_Id').val('');
    		                        CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable('', '', '', '', '', '', true);
    		                        return;
    		                    }
    		                    var startRow = (row < 0) ? 0 : row;
    		                    if (row2 < 0) row2 = 0;
    		                    if (row > row2) { startRow = row2; row2 = row; }
    		                    var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
    		                    if (selectedRow !== startRow) {
    		                        $('#ModbusProtocolAddrMappingItemsSelectRow_Id').val(startRow);
    		                        var protocolCode = getCurrentProtocolCode();
    		                        var itemTitle = helper.hot.getDataAtRowProp(startRow, 'title');
    		                        var itemAddr = helper.hot.getDataAtRowProp(startRow, 'addr');
    		                        var highLowByte = helper.hot.getDataAtRowProp(startRow, 'highLowByte');
    		                        var resolutionMode = helper.hot.getDataAtRowProp(startRow, 'resolutionMode');
    		                        var quantity = helper.hot.getDataAtRowProp(startRow, 'quantity');
    		                        CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
    		                    }
    		                }
    		            });
    		        };
    		        return helper;
    		    }
    		};
     var ProtocolItemsMeaningConfigHandsontableHelper = {
    		    createNew: function (divid) {
    		        var helper = {};
    		        helper.hot = null;
    		        helper.divid = divid;
    		        helper.validresult = true;
    		        helper.colHeaders = [];
    		        helper.columns = [];
    		        helper.AllData = [];
    		        helper.itemResolutionMode = 2;
    		        helper.hiddenColumns = [];
    		        helper.contextMenu = null;

    		        helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
    		            Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            td.style.whiteSpace = 'nowrap';
    		            td.style.overflow = 'hidden';
    		            td.style.textOverflow = 'ellipsis';
    		        };

    		        helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
    		            Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            td.style.backgroundColor = 'rgb(245, 245, 245)';
    		            td.style.whiteSpace = 'nowrap';
    		            td.style.overflow = 'hidden';
    		            td.style.textOverflow = 'ellipsis';
    		        };

    		        helper.createTable = function (data) {
    		            $('#' + helper.divid).empty();
    		            var hotElement = document.querySelector('#' + helper.divid);
    		            helper.hot = new Handsontable(hotElement, {
    		                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
    		                theme: 'ht-theme-classic',
    		                data: data,
    		                hiddenColumns: {
    		                    columns: helper.hiddenColumns,
    		                    indicators: false,
    		                    copyPasteEnabled: false
    		                },
    		                colWidths: [1, 3],
    		                columns: helper.columns,
    		                stretchH: 'all',
    		                autoWrapRow: true,
    		                rowHeaders: false,
    		                colHeaders: helper.colHeaders,
    		                columnSorting: true,
    		                sortIndicator: true,
    		                manualColumnResize: true,
    		                manualRowResize: true,
    		                filters: true,
    		                renderAllRows: true,
    		                search: true,
    		                contextMenu: helper.contextMenu,
    		                cells: function (row, col, prop) {
    		                    var cellProperties = {};
    		                    if (!editFlag) {
    		                        cellProperties.editor = false;
    		                        cellProperties.renderer = helper.addCellStyle;
    		                    } else {
    		                        if (helper.itemResolutionMode === 0 && prop === 'title') {
    		                            cellProperties.editor = false;
    		                            cellProperties.renderer = helper.addBoldBg;
    		                        } else {
    		                            cellProperties.renderer = helper.addCellStyle;
    		                        }
    		                    }
    		                    return cellProperties;
    		                }
    		            });
    		        };
    		        return helper;
    		    }
    		};
     var ProtocolSwitchingValueBitStatusConfigHandsontableHelper = {
    		    createNew: function (divid) {
    		        var helper = {};
    		        helper.hot = null;
    		        helper.divid = divid;
    		        helper.validresult = true;
    		        helper.colHeaders = [];
    		        helper.columns = [];
    		        helper.AllData = [];

    		        helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
    		            Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            td.style.whiteSpace = 'nowrap';
    		            td.style.overflow = 'hidden';
    		            td.style.textOverflow = 'ellipsis';
    		        };

    		        helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
    		            Handsontable.renderers.TextRenderer.apply(this, arguments);
    		            td.style.backgroundColor = 'rgb(245, 245, 245)';
    		            td.style.whiteSpace = 'nowrap';
    		            td.style.overflow = 'hidden';
    		            td.style.textOverflow = 'ellipsis';
    		        };

    		        helper.createTable = function (data) {
    		            $('#' + helper.divid).empty();
    		            var hotElement = document.querySelector('#' + helper.divid);
    		            helper.hot = new Handsontable(hotElement, {
    		                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
    		                theme: 'ht-theme-classic',
    		                data: data,
    		                hiddenColumns: {
    		                    columns: [2, 3],
    		                    indicators: false,
    		                    copyPasteEnabled: false
    		                },
    		                colWidths: [2, 5],
    		                columns: helper.columns,
    		                stretchH: 'all',
    		                autoWrapRow: true,
    		                rowHeaders: false,
    		                colHeaders: helper.colHeaders,
    		                columnSorting: true,
    		                sortIndicator: true,
    		                manualColumnResize: true,
    		                manualRowResize: true,
    		                filters: true,
    		                renderAllRows: true,
    		                search: true,
    		                cells: function (row, col, prop) {
    		                    var cellProperties = {};
    		                    if (!editFlag) {
    		                        cellProperties.editor = false;
    		                        cellProperties.renderer = helper.addCellStyle;
    		                    } else {
    		                        if (prop === 'title') {
    		                            cellProperties.editor = false;
    		                            cellProperties.renderer = helper.addBoldBg;
    		                        } else {
    		                            cellProperties.renderer = helper.addCellStyle;
    		                        }
    		                    }
    		                    return cellProperties;
    		                }
    		            });
    		        };
    		        return helper;
    		    }
    		};
     
  // ================================================================
  // 加载数值运算表格
  // ================================================================
  function CreateProtocolExtendedFieldConfigInfoTable(protocolName, classes, code) {
      // 销毁旧的 helper
      if (protocolExtendedFieldConfigHandsontableHelper && protocolExtendedFieldConfigHandsontableHelper.hot) {
          protocolExtendedFieldConfigHandsontableHelper.hot.destroy();
          protocolExtendedFieldConfigHandsontableHelper = null;
      }

      // 更新信息标签
      var showInfo = (_loginUserLanguageResource.extendedField || 'Extended Field');
      if (protocolName) {
          showInfo = '【<font color="red">' + protocolName + '</font>】' + showInfo;
      }
      //$('#protocolInfoLabel').html(showInfo);

      $.ajax({
          type: 'POST',
          url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldsConfigData',
          data: {
              protocolName: protocolName,
              classes: classes,
              code: code,
              type: 0   // 数值运算
          },
          dataType: 'json',
          success: function (result) {
              var dataLength = result.totalRoot.length;
              var defultDataLength = 100;
              var tableData = result.totalRoot;
              if (dataLength < defultDataLength) {
                  for (var i = dataLength; i < defultDataLength; i++) {
                      tableData.push({});
                  }
              }

              if (!protocolExtendedFieldConfigHandsontableHelper) {
                  protocolExtendedFieldConfigHandsontableHelper = ProtocolExtendedFieldConfigHandsontableHelper.createNew('ProtocolExtendedFieldTableInfoDiv_id');

                  var operationList = result.operationList || [];
                  var additionalConditionsList = result.additionalConditionsList || [];

                  var colHeaders = [
                      _loginUserLanguageResource.idx || 'Idx',
                      _loginUserLanguageResource.name || 'Name',
                      (_loginUserLanguageResource.dataColumn || 'Column') + '1',
                      _loginUserLanguageResource.fourOperation || 'Operation',
                      (_loginUserLanguageResource.dataColumn || 'Column') + '2',
                      _loginUserLanguageResource.prec || 'Prec',
                      _loginUserLanguageResource.ratio || 'Ratio',
                      _loginUserLanguageResource.unit || 'Unit',
                      _loginUserLanguageResource.additionalConditions || 'Additional Conditions'
                  ];

                  var columns = [
                      { data: 'id' },
                      { data: 'title', renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle },
                      { data: 'title1', renderer: protocolExtendedFieldConfigHandsontableHelper.placeholderRenderer },
                      {
                          data: 'operation',
                          type: 'dropdown',
                          strict: true,
                          allowInvalid: false,
                          source: operationList,
                          renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle
                      },
                      { data: 'title2', renderer: protocolExtendedFieldConfigHandsontableHelper.placeholderRenderer },
                      {
                          data: 'prec',
                          type: 'text',
                          allowInvalid: true,
                          renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle,
                          validator: function (val, callback) {
                              return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldConfigHandsontableHelper);
                          }
                      },
                      {
                          data: 'ratio',
                          type: 'text',
                          allowInvalid: true,
                          renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle,
                          validator: function (val, callback) {
                              return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldConfigHandsontableHelper);
                          }
                      },
                      { data: 'unit', renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle },
                      {
                          data: 'additionalConditions',
                          type: 'dropdown',
                          strict: true,
                          allowInvalid: false,
                          source: additionalConditionsList,
                          renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle
                      }
                  ];

                  protocolExtendedFieldConfigHandsontableHelper.colHeaders = colHeaders;
                  protocolExtendedFieldConfigHandsontableHelper.columns = columns;
                  protocolExtendedFieldConfigHandsontableHelper.createTable(tableData);
              } else {
                  protocolExtendedFieldConfigHandsontableHelper.hot.loadData(tableData);
                  protocolExtendedFieldConfigHandsontableHelper.hot.render();
              }
          },
          error: function () {
              mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
          }
      });
  }

     
  // ================================================================
  // 扩展字段 - 数值运算表格 Helper
  // ================================================================
  var ProtocolExtendedFieldConfigHandsontableHelper = {
      createNew: function (divid) {
          var helper = {};
          helper.hot = null;
          helper.divid = divid;
          helper.validresult = true;
          helper.colHeaders = [];
          helper.columns = [];
          helper.AllData = [];

          helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
              if (cellProperties.type === 'checkbox') {
                  Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
              } else if (cellProperties.type === 'dropdown') {
                  Handsontable.renderers.DropdownRenderer.apply(this, arguments);
              } else {
                  Handsontable.renderers.TextRenderer.apply(this, arguments);
              }
              if (cellProperties.type !== 'checkbox') {
                  td.style.whiteSpace = 'nowrap';
                  td.style.overflow = 'hidden';
                  td.style.textOverflow = 'ellipsis';
              }
          };

          helper.placeholderRenderer = function (instance, td, row, col, prop, value, cellProperties) {
              Handsontable.renderers.TextRenderer.apply(this, arguments);
              td.style.whiteSpace = 'nowrap';
              td.style.overflow = 'hidden';
              td.style.textOverflow = 'ellipsis';

              var children = td.children;
              if (children.length > 0) {
                  for (var i = 0; i < children.length; i++) {
                      var child = children[i];
                      child.style.whiteSpace = 'nowrap';
                      child.style.overflow = 'hidden';
                      child.style.textOverflow = 'ellipsis';
                      child.style.display = 'block';
                      child.style.maxWidth = '100%';
                  }
              }

              if (value === null || value === '') {
                  td.style.color = 'gray';
                  td.style.fontStyle = 'italic';
                  td.innerHTML = (_loginUserLanguageResource.doubleClickCellTip || 'Double click') + '...';
              }
          };

          helper.createTable = function (data) {
              $('#' + helper.divid).empty();
              var hotElement = document.querySelector('#' + helper.divid);
              helper.hot = new Handsontable(hotElement, {
                  licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                  theme: 'ht-theme-classic',
                  data: data,
                  hiddenColumns: {
                      columns: [0],
                      indicators: false,
                      copyPasteEnabled: false
                  },
                  colWidths: [50, 200, 200, 80, 200, 80, 80, 80, 150],
                  columns: helper.columns,
                  stretchH: 'all',
                  autoWrapRow: true,
                  rowHeaders: true,
                  colHeaders: helper.colHeaders,
                  columnSorting: true,
                  sortIndicator: true,
                  manualColumnResize: true,
                  manualRowResize: true,
                  filters: true,
                  renderAllRows: true,
                  search: true,
                  contextMenu: {
                      items: {
                          "row_above": { name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above') },
                          "row_below": { name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below') },
                          "col_left": { name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left') },
                          "col_right": { name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right') },
                          "remove_row": { name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row') },
                          "remove_col": { name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column') },
                          "merge_cell": { name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells') },
                          "copy": { name: (_loginUserLanguageResource.contextMenu_copy || 'Copy') },
                          "cut": { name: (_loginUserLanguageResource.contextMenu_cut || 'Cut') }
                      }
                  },
                  cells: function (row, col, prop) {
                      var cellProperties = {};
                      if (!editFlag) {
                          cellProperties.editor = false;
                      }
                      return cellProperties;
                  },
                  afterBeginEditing: function (row, column) {
                	  var cellMeta = helper.hot.getCellMeta(row, column);
                	    var selectedItemName = helper.hot.getDataAtCell(row, column);
                	    if (cellMeta.prop.toUpperCase() === 'title1'.toUpperCase() || 
                	        cellMeta.prop.toUpperCase() === 'title2'.toUpperCase()) {
                	        
                	        var tree = mini.get('protocolTree');
                	        var selectedNode = tree.getSelectedNode();
                	        if (!selectedNode || selectedNode.classes !== 1) {
                	            return;
                	        }
                	        var protocolCode = selectedNode.code || '';

                	        mini.open({
                	            title: _loginUserLanguageResource.config,
                	            url: context + '/miniui-app/modules/driverConfig/protocolExtendedFieldSelectWindow.jsp',
                	            width: 600,
                	            height: 800,
                	            modal: true,
                	            allowResize: true,
                	            onload: function () {
                	                var iframe = this.getIFrameEl();
                	                var contentWindow = iframe.contentWindow;
                	                contentWindow.setData({
                	                    protocolCode: protocolCode,
                	                    row: row,
                	                    col: column,
                	                    fieldType: 0,
                	                    currentValue: selectedItemName || ''
                	                });
                	                contentWindow.parent.setExtendedFieldValue = function (r, c, value) {
                	                    helper.hot.setDataAtCell(r, c, value);
                	                    //helper.hot.render();
                	                };
                	            },
                	            ondestroy: function (action) {
                	                // 关闭后无额外操作
                	            }
                	        });
                	    }
                  },
                  afterOnCellMouseOver: function (event, coords, TD) {
                	  
                  }
              });
          };
          return helper;
      }
  };
  
//================================================================
//加载高低字节主表
//================================================================
function CreateProtocolExtendedFieldHighLowByteConfigInfoTable(protocolName, classes, code) {
   if (protocolExtendedFieldHighLowByteConfigHandsontableHelper && protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot) {
       protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.destroy();
       protocolExtendedFieldHighLowByteConfigHandsontableHelper = null;
   }

   var showInfo = (_loginUserLanguageResource.extendedField || 'Extended Field');
   if (protocolName) {
       showInfo = '【<font color="red">' + protocolName + '</font>】' + showInfo;
   }
   //$('#protocolInfoLabel').html(showInfo);

   $.ajax({
       type: 'POST',
       url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldsConfigData',
       data: {
           protocolName: protocolName,
           classes: classes,
           code: code,
           type: 1   // 高低字节
       },
       dataType: 'json',
       success: function (result) {
           var dataLength = result.totalRoot.length;
           var defultDataLength = 100;
           var tableData = result.totalRoot;
           if (dataLength < defultDataLength) {
               for (var i = dataLength; i < defultDataLength; i++) {
                   tableData.push({});
               }
           }

           if (!protocolExtendedFieldHighLowByteConfigHandsontableHelper) {
               protocolExtendedFieldHighLowByteConfigHandsontableHelper = ProtocolExtendedFieldHighLowByteConfigHandsontableHelper.createNew('ProtocolExtendedFieldHighLowByteTableInfoDiv_id');

               var colHeaders = [
                   _loginUserLanguageResource.idx || 'Idx',
                   _loginUserLanguageResource.name || 'Name',
                   (_loginUserLanguageResource.dataColumn || 'Column'),
                   _loginUserLanguageResource.highLowByte || 'High/Low Byte',
                   _loginUserLanguageResource.resolutionMode || 'Resolution Mode',
                   _loginUserLanguageResource.prec || 'Prec',
                   _loginUserLanguageResource.ratio || 'Ratio',
                   _loginUserLanguageResource.unit || 'Unit'
               ];

               var columns = [
                   { data: 'id' },
                   { data: 'title', renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle },
                   { data: 'title1', renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.placeholderRenderer },
                   {
                       data: 'highLowByte',
                       type: 'dropdown',
                       strict: true,
                       allowInvalid: false,
                       source: [(_loginUserLanguageResource.highByte || 'High'), (_loginUserLanguageResource.lowByte || 'Low')],
                       renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle
                   },
                   {
                       data: 'resolutionMode',
                       type: 'dropdown',
                       strict: true,
                       allowInvalid: false,
                       source: [(_loginUserLanguageResource.switchingValue || 'Switching'), (_loginUserLanguageResource.enumValue || 'Enum'), (_loginUserLanguageResource.numericValue || 'Numeric')],
                       renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle
                   },
                   {
                       data: 'prec',
                       type: 'text',
                       allowInvalid: true,
                       renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle,
                       validator: function (val, callback) {
                           return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldHighLowByteConfigHandsontableHelper);
                       }
                   },
                   {
                       data: 'ratio',
                       type: 'text',
                       allowInvalid: true,
                       renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle,
                       validator: function (val, callback) {
                           return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldHighLowByteConfigHandsontableHelper);
                       }
                   },
                   { data: 'unit', renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle }
               ];

               protocolExtendedFieldHighLowByteConfigHandsontableHelper.colHeaders = colHeaders;
               protocolExtendedFieldHighLowByteConfigHandsontableHelper.columns = columns;
               protocolExtendedFieldHighLowByteConfigHandsontableHelper.createTable(tableData);
           } else {
               protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.loadData(tableData);
               protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.render();
           }

           // 默认选中第一行并加载含义表
           if (dataLength > 0) {
               protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.selectCell(0, 'title');
               $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val(0);
               var protocolCode = getCurrentProtocolCode();
               var itemTitle = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(0, 'title');
               var resolutionMode = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(0, 'resolutionMode');
               CreateProtocolExtendedFieldMeaningConfigInfoTable(protocolCode, itemTitle, resolutionMode, true);
           } else {
               // 无数据，清空含义和位状态
               $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val('');
               CreateProtocolExtendedFieldMeaningConfigInfoTable('', '', '', true);
           }
       },
       error: function () {
           mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
       }
   });
}
  
//================================================================
//扩展字段 - 高低字节主表 Helper
//================================================================
var ProtocolExtendedFieldHighLowByteConfigHandsontableHelper = {
   createNew: function (divid) {
       var helper = {};
       helper.hot = null;
       helper.divid = divid;
       helper.validresult = true;
       helper.colHeaders = [];
       helper.columns = [];
       helper.AllData = [];

       helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
           if (cellProperties.type === 'checkbox') {
               Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
           } else if (cellProperties.type === 'dropdown') {
               Handsontable.renderers.DropdownRenderer.apply(this, arguments);
           } else {
               Handsontable.renderers.TextRenderer.apply(this, arguments);
           }
           if (cellProperties.type !== 'checkbox') {
               td.style.whiteSpace = 'nowrap';
               td.style.overflow = 'hidden';
               td.style.textOverflow = 'ellipsis';
           }
       };

       helper.placeholderRenderer = function (instance, td, row, col, prop, value, cellProperties) {
           Handsontable.renderers.TextRenderer.apply(this, arguments);
           td.style.whiteSpace = 'nowrap';
           td.style.overflow = 'hidden';
           td.style.textOverflow = 'ellipsis';

           var children = td.children;
           if (children.length > 0) {
               for (var i = 0; i < children.length; i++) {
                   var child = children[i];
                   child.style.whiteSpace = 'nowrap';
                   child.style.overflow = 'hidden';
                   child.style.textOverflow = 'ellipsis';
                   child.style.display = 'block';
                   child.style.maxWidth = '100%';
               }
           }

           if (value === null || value === '') {
               td.style.color = 'gray';
               td.style.fontStyle = 'italic';
               td.innerHTML = (_loginUserLanguageResource.doubleClickCellTip || 'Double click') + '...';
           }
       };

       helper.createTable = function (data) {
           $('#' + helper.divid).empty();
           var hotElement = document.querySelector('#' + helper.divid);
           helper.hot = new Handsontable(hotElement, {
               licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
               theme: 'ht-theme-classic',
               data: data,
               hiddenColumns: {
                   columns: [0],
                   indicators: false,
                   copyPasteEnabled: false
               },
               colWidths: [50, 200, 200, 80, 80, 80, 80, 80],
               columns: helper.columns,
               stretchH: 'all',
               autoWrapRow: true,
               rowHeaders: true,
               colHeaders: helper.colHeaders,
               columnSorting: true,
               sortIndicator: true,
               manualColumnResize: true,
               manualRowResize: true,
               filters: true,
               renderAllRows: true,
               search: true,
               outsideClickDeselects: false,
               contextMenu: {
                   items: {
                       "row_above": { name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above') },
                       "row_below": { name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below') },
                       "col_left": { name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left') },
                       "col_right": { name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right') },
                       "remove_row": { name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row') },
                       "remove_col": { name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column') },
                       "merge_cell": { name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells') },
                       "copy": { name: (_loginUserLanguageResource.contextMenu_copy || 'Copy') },
                       "cut": { name: (_loginUserLanguageResource.contextMenu_cut || 'Cut') }
                   }
               },
               cells: function (row, col, prop) {
                   var cellProperties = {};
                   if (!editFlag) {
                       cellProperties.editor = false;
                   }
                   return cellProperties;
               },
               afterBeginEditing: function (row, column) {
            	   var cellMeta = helper.hot.getCellMeta(row, column);
           	    var selectedItemName = helper.hot.getDataAtCell(row, column);
           	    if (cellMeta.prop.toUpperCase() === 'title1'.toUpperCase() || 
           	        cellMeta.prop.toUpperCase() === 'title2'.toUpperCase()) {
           	        
           	        var tree = mini.get('protocolTree');
           	        var selectedNode = tree.getSelectedNode();
           	        if (!selectedNode || selectedNode.classes !== 1) {
           	            return;
           	        }
           	        var protocolCode = selectedNode.code || '';

           	        mini.open({
           	            title: _loginUserLanguageResource.config,
           	            url: context + '/miniui-app/modules/driverConfig/protocolExtendedFieldSelectWindow.jsp',
           	            width: 600,
           	            height: 800,
           	            modal: true,
           	            allowResize: true,
           	            onload: function () {
           	                var iframe = this.getIFrameEl();
           	                var contentWindow = iframe.contentWindow;
           	                contentWindow.setData({
           	                    protocolCode: protocolCode,
           	                    row: row,
           	                    col: column,
           	                    fieldType: 1,  // 1:高低字节
           	                    currentValue: selectedItemName || ''
           	                });
           	                contentWindow.parent.setExtendedFieldValue = function (r, c, value) {
           	                    helper.hot.setDataAtCell(r, c, value);
           	                };
           	            },
           	            ondestroy: function (action) {
           	                // 关闭后无额外操作
           	            }
           	        });
           	    }
               },
               afterChange: function (changes, source) {
                   if (!changes) return;
                   changes.forEach(function (change) {
                       var row = change[0], prop = change[1], oldVal = change[2], newVal = change[3];
                       if (prop === 'resolutionMode') {
                           var selectedRow = parseInt($('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val() || 0);
                           if (selectedRow === row &&
                               typeof protocolExtendedFieldMeaningConfigHandsontableHelper !== 'undefined' &&
                               protocolExtendedFieldMeaningConfigHandsontableHelper &&
                               protocolExtendedFieldMeaningConfigHandsontableHelper.hot) {
                               var resolutionMode = newVal;
                               var itemTitle = helper.hot.getDataAtRowProp(row, 'title');
                               var protocolCode = getCurrentProtocolCode();
                               CreateProtocolExtendedFieldMeaningConfigInfoTable(protocolCode, itemTitle, resolutionMode, true);
                           }
                       }
                   });
               },
               afterSelectionEnd: function (row, column, row2, column2, selectionLayerLevel) {
                   if (row < 0 && row2 < 0) {
                       // 只选中表头
                       $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val('');
                       CreateProtocolExtendedFieldMeaningConfigInfoTable('', '', '', true);
                       return;
                   }
                   var startRow = (row < 0) ? 0 : row;
                   if (row2 < 0) row2 = 0;
                   if (row > row2) { startRow = row2; row2 = row; }
                   var selectedRow = parseInt($('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val() || 0);
                   if (selectedRow !== startRow) {
                       $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val(startRow);
                       var protocolCode = getCurrentProtocolCode();
                       var itemTitle = helper.hot.getDataAtRowProp(startRow, 'title');
                       var resolutionMode = helper.hot.getDataAtRowProp(startRow, 'resolutionMode');
                       CreateProtocolExtendedFieldMeaningConfigInfoTable(protocolCode, itemTitle, resolutionMode, true);
                   }
               },
               afterOnCellMouseOver: function (event, coords, TD) {
               }
           });
       };
       return helper;
   }
};

//================================================================
//加载高低字节含义表格
//================================================================
function CreateProtocolExtendedFieldMeaningConfigInfoTable(protocolCode, itemTitle, resolutionMode, isNew) {
 if (protocolExtendedFieldMeaningConfigHandsontableHelper && protocolExtendedFieldMeaningConfigHandsontableHelper.hot) {
     protocolExtendedFieldMeaningConfigHandsontableHelper.hot.destroy();
     protocolExtendedFieldMeaningConfigHandsontableHelper = null;
 }

 var resolutionModeValue = 2;
 if (resolutionMode === (_loginUserLanguageResource.switchingValue || 'Switching')) resolutionModeValue = 0;
 else if (resolutionMode === (_loginUserLanguageResource.enumValue || 'Enum')) resolutionModeValue = 1;

 var quantity = 8; // 默认8位

 var showInfo = (_loginUserLanguageResource.meaning || 'Meaning');
 if (itemTitle) {
     showInfo = '【<font color="red">' + itemTitle + '</font>】' + showInfo;
 }
 // 更新含义面板标题（假设面板id是 ProtocolExtendedFieldConfigHighLowByteItemsMeaningConfigPanel_Id）
 var meaningPanel = mini.get('ProtocolExtendedFieldConfigHighLowByteItemsMeaningConfigPanel_Id');
 if (meaningPanel) {
     meaningPanel.setTitle(showInfo);
 } else {
     $('#ProtocolExtendedFieldConfigHighLowByteItemsMeaningConfigPanel_Id .mini-panel-title').html(showInfo);
 }

 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldMeaningConfigData',
     data: {
         protocolCode: protocolCode,
         itemTitle: itemTitle,
         resolutionMode: resolutionModeValue,
         quantity: quantity
     },
     dataType: 'json',
     success: function (result) {
         var data = result.totalRoot || [];

         // 补充空行
         if (resolutionModeValue !== 0) {
             var dataLength = data.length;
             var defultDataLength = 50;
             if (dataLength < defultDataLength) {
                 if (defultDataLength - dataLength < 10) {
                     for (var i = 0; i < 10; i++) data.push({});
                 } else {
                     for (var i = dataLength; i < defultDataLength; i++) data.push({});
                 }
             } else {
                 for (var i = 0; i < 10; i++) data.push({});
             }
         }

         if (!protocolExtendedFieldMeaningConfigHandsontableHelper) {
             protocolExtendedFieldMeaningConfigHandsontableHelper = ProtocolExtendedFieldMeaningConfigHandsontableHelper.createNew('ProtocolExtendedFieldConfigHighLowByteItemsMeaningTableInfoDiv_id');

             var colHeaders, columns;
             if (resolutionModeValue === 0) {
                 colHeaders = [(_loginUserLanguageResource.bit || 'Bit'), (_loginUserLanguageResource.meaning || 'Meaning'), ''];
                 columns = [
                     { data: 'title' },
                     { data: 'meaning' },
                     { data: 'value' }
                 ];
                 protocolExtendedFieldMeaningConfigHandsontableHelper.hiddenColumns = [2];
                 protocolExtendedFieldMeaningConfigHandsontableHelper.contextMenu = false;
             } else {
                 colHeaders = [(_loginUserLanguageResource.value || 'Value'), (_loginUserLanguageResource.meaning || 'Meaning')];
                 columns = [
                     {
                         data: 'value',
                         type: 'text',
                         allowInvalid: true,
                         validator: function (val, callback) {
                             return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolExtendedFieldMeaningConfigHandsontableHelper);
                         }
                     },
                     { data: 'meaning' }
                 ];
                 protocolExtendedFieldMeaningConfigHandsontableHelper.hiddenColumns = [];
                 protocolExtendedFieldMeaningConfigHandsontableHelper.contextMenu = {
                     items: {
                         "row_above": { name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above') },
                         "row_below": { name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below') },
                         "col_left": { name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left') },
                         "col_right": { name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right') },
                         "remove_row": { name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row') },
                         "remove_col": { name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column') },
                         "merge_cell": { name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells') },
                         "copy": { name: (_loginUserLanguageResource.contextMenu_copy || 'Copy') },
                         "cut": { name: (_loginUserLanguageResource.contextMenu_cut || 'Cut') }
                     }
                 };
             }
             protocolExtendedFieldMeaningConfigHandsontableHelper.colHeaders = colHeaders;
             protocolExtendedFieldMeaningConfigHandsontableHelper.columns = columns;
             protocolExtendedFieldMeaningConfigHandsontableHelper.itemResolutionMode = resolutionModeValue;
             protocolExtendedFieldMeaningConfigHandsontableHelper.createTable(data);
         } else {
             protocolExtendedFieldMeaningConfigHandsontableHelper.hot.loadData(data);
             protocolExtendedFieldMeaningConfigHandsontableHelper.itemResolutionMode = resolutionModeValue;
             protocolExtendedFieldMeaningConfigHandsontableHelper.hot.render();
         }

         // 控制位状态面板显示
         if (resolutionModeValue === 0) {
             // 显示位状态面板
        	 var splitter = mini.get('highLowBitStatusSplitter_Id');
             if(splitter){
             	splitter.showPane(2);
             }
             CreateProtocolExtendedFieldSwitchingValueBitStatusConfigInfoTable(protocolCode, itemTitle, resolutionMode, isNew);
         } else {
        	 var splitter = mini.get('highLowBitStatusSplitter_Id');
             if(splitter){
             	splitter.hidePane(2);
             }
             if (protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper && protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot) {
                 protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.destroy();
                 protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = null;
             }
         }
     },
     error: function () {
         mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
     }
 });
}

//================================================================
//扩展字段 - 高低字节含义表格 Helper
//================================================================
var ProtocolExtendedFieldMeaningConfigHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.validresult = true;
     helper.colHeaders = [];
     helper.columns = [];
     helper.AllData = [];
     helper.itemResolutionMode = 2;
     helper.hiddenColumns = [];
     helper.contextMenu = null;

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: helper.hiddenColumns,
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [1, 3],
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             contextMenu: helper.contextMenu,
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addCellStyle;
                 } else {
                     if (helper.itemResolutionMode === 0 && prop === 'title') {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addBoldBg;
                     } else {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             afterOnCellMouseOver: function (event, coords, TD) {
             }
         });
     };
     return helper;
 }
};

//================================================================
//加载高低字节开关量位状态表格
//================================================================
function CreateProtocolExtendedFieldSwitchingValueBitStatusConfigInfoTable(protocolCode, itemTitle, resolutionMode, isNew) {
 if (protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper && protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot) {
     protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.destroy();
     protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = null;
 }

 var quantity = 8;
 var showInfo = (_loginUserLanguageResource.switchingValueBitStatusConfig || 'Bit Status Config');
 if (itemTitle) {
     showInfo = '【<font color="red">' + itemTitle + '</font>】' + showInfo;
 }
 var bitPanel = mini.get('ProtocolExtendedFieldSwitchingValueBitStatusConfigPanel_Id');
 if (bitPanel) {
     bitPanel.setTitle(showInfo);
 } else {
     $('#ProtocolExtendedFieldSwitchingValueBitStatusConfigPanel_Id .mini-panel-title').html(showInfo);
 }

 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldSwitchingValueBitStatusConfigData',
     data: {
         protocolCode: protocolCode,
         itemTitle: itemTitle,
         quantity: quantity,
         resolutionMode: resolutionMode
     },
     dataType: 'json',
     success: function (result) {
         var data = result.totalRoot || [];

         if (!protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper) {
             protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = ProtocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.createNew('ProtocolExtendedFieldSwitchingValueBitStatusConfigTableInfoDiv_id');

             var colHeaders = [(_loginUserLanguageResource.bit || 'Bit') + '/' + (_loginUserLanguageResource.value || 'Value'), (_loginUserLanguageResource.switchingValuStatus || 'Status'), '', ''];
             var columns = [
                 { data: 'title', type: 'text' },
                 { data: 'status' },
                 { data: 'bitIndex' },
                 { data: 'value' }
             ];

             protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.colHeaders = colHeaders;
             protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.columns = columns;
             protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.createTable(data);
         } else {
             protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.loadData(data);
             protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.render();
         }
     },
     error: function () {
         mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
     }
 });
}

//================================================================
//扩展字段 - 高低字节开关量位状态表格 Helper
//================================================================
var ProtocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.validresult = true;
     helper.colHeaders = [];
     helper.columns = [];
     helper.AllData = [];

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: [2, 3],
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [2, 5],
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addCellStyle;
                 } else {
                     if (prop === 'title') {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addBoldBg;
                     } else {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             afterOnCellMouseOver: function (event, coords, TD) {
             }
         });
     };
     return helper;
 }
};

function saveProtocolConfigData() {
    // 1. 获取当前选中的协议节点
    var protocolTree = mini.get('protocolTree');
    if (!protocolTree) {
        mini.alert(_loginUserLanguageResource.tip || '提示', '请先选择协议');
        return;
    }
    var selectedNode = protocolTree.getSelectedNode();
    if (!selectedNode || selectedNode.classes !== 1) {
        mini.alert(_loginUserLanguageResource.tip || '提示', '请选择一个协议节点');
        return;
    }

    // 2. 确定保存类型
    var saveType = 0; // 默认属性
    var protoSub = mini.get('protocolSubTabs');
    if (protoSub) {
        var activeTab = protoSub.getActiveTab();
        if (activeTab) {
            var tabName = activeTab.name; // props, config, extended
            if (tabName === 'props') {
                saveType = 0;
            } else if (tabName === 'config') {
                saveType = 1;
            } else if (tabName === 'extended') {
                var extSub = mini.get('extendedSubTabs');
                if (extSub) {
                    var extActive = extSub.getActiveTab();
                    if (extActive) {
                        if (extActive.name === 'numeric') {
                            saveType = 2; // 数值运算
                        } else if (extActive.name === 'highlow') {
                            saveType = 3; // 高低字节
                        }
                    }
                }
            }
        }
    }

    // 3. 准备协议基础数据
    var protocolConfigData = {
        text: selectedNode.text,
        code: selectedNode.code,
        deviceType: selectedNode.deviceType || '',
        sort: selectedNode.sort || ''
    };

    // 4. 如果是属性标签，从属性表格获取最新名称和排序
    if (saveType === 0 && protocolPropertiesHandsontableHelper && protocolPropertiesHandsontableHelper.hot) {
        var propertiesData = protocolPropertiesHandsontableHelper.hot.getData();
        if (propertiesData && propertiesData.length > 0) {
            var nameVal = (propertiesData[0] && propertiesData[0][2] !== undefined) ? propertiesData[0][2] : '';
            var sortVal = (propertiesData[1] && propertiesData[1][2] !== undefined) ? propertiesData[1][2] : '';
            protocolConfigData.text = isNotVal(nameVal) ? nameVal : '';
            protocolConfigData.sort = isNotVal(sortVal) ? sortVal : '';
        }
    }

    // 5. 校验协议名称不能为空
    if (!isNotVal(protocolConfigData.text)) {
        mini.alert(_loginUserLanguageResource.tip || '提示',
            (_loginUserLanguageResource.protocolName || '协议名称') + ',' +
            (_loginUserLanguageResource.canNotBeEmpty || '不能为空') + '!');
        return;
    }

    // 6. 构造 configInfo 对象
    var configInfo = {
        ProtocolName: protocolConfigData.text,
        ProtocolCode: protocolConfigData.code,
        DeviceType: protocolConfigData.deviceType,
        Sort: protocolConfigData.sort,
        DataConfig: [],
        ExtendedFieldConfig: []
    };

    // 7. 根据 saveType 收集对应的表格数据
    if (saveType === 1) {
        // 配置项表格
        if (protocolItemsConfigHandsontableHelper && protocolItemsConfigHandsontableHelper.hot) {
            var itemsData = protocolItemsConfigHandsontableHelper.hot.getData();
            var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);

            for (var i = 0; i < itemsData.length; i++) {
                var itemTitle = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title');
                if (!isNotVal(itemTitle)) continue;

                var item = {};
                item.Title = itemTitle;
                item.Addr = parseInt(protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'addr')) || 0;

                var highLow = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'highLowByte');
                item.HighLowByte = '';
                if (highLow === (_loginUserLanguageResource.highByte || '高字节')) {
                    item.HighLowByte = 'high';
                } else if (highLow === (_loginUserLanguageResource.lowByte || '低字节')) {
                    item.HighLowByte = 'low';
                }

                item.StoreDataType = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'storeDataType') || '';
                item.Quantity = parseInt(protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'quantity')) || 0;
                item.RWType = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'RWType') || '';
                item.AcqMode = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'acqMode') || '';
                item.IFDataType = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'IFDataType') || '';

                var precStr = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'prec') + '';
                precStr = precStr.replace(/\s/g, '');
                item.Prec = (item.IFDataType && item.IFDataType.toLowerCase().indexOf('float') >= 0) ?
                    (isNumber(parseFloat(precStr)) ? parseFloat(precStr) : 0) : 0;

                var ratioVal = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'ratio');
                item.Ratio = isNumber(parseFloat(ratioVal)) ? parseFloat(ratioVal) : 1;

                var unitVal = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'unit') + '';
                item.Unit = unitVal.replace(/\s/g, '');

                item.ResolutionMode = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'resolutionMode') || '';

                // 只有选中行才收集含义/位状态
                if (i === selectedRow) {
                    item.Meaning = [];
                    if (item.ResolutionMode === (_loginUserLanguageResource.enumValue || '枚举量')) {
                        // 枚举量：从含义表收集 value/meaning
                        if (protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
                            var meaningData = protocolItemsMeaningConfigHandsontableHelper.hot.getData();
                            for (var j = 0; j < meaningData.length; j++) {
                                var val = protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'value');
                                var meaning = protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'meaning');
                                if (isNotVal(val) && isNotVal(meaning)) {
                                    item.Meaning.push({ Value: val, Meaning: meaning });
                                }
                            }
                        }
                    } else if (item.ResolutionMode === (_loginUserLanguageResource.switchingValue || '开关量')) {
                        // 开关量：从位状态表收集，并结合含义表补充 Meaning
                        if (protocolSwitchingValueBitStatusConfigHandsontableHelper && protocolSwitchingValueBitStatusConfigHandsontableHelper.hot) {
                            var bitData = protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.getData();
                            for (var k = 0; k < bitData.length; k++) {
                                var status = protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'status');
                                var bitIndex = protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'bitIndex');
                                var bitValue = protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'value');
                                if (isNotVal(status)) {
                                    var exist = false;
                                    for (var m = 0; m < item.Meaning.length; m++) {
                                        if (bitIndex == item.Meaning[m].Value) {
                                            if (bitValue == 0) item.Meaning[m].Status0 = status;
                                            else if (bitValue == 1) item.Meaning[m].Status1 = status;
                                            exist = true;
                                            break;
                                        }
                                    }
                                    if (!exist) {
                                        var newMeaning = { Value: bitIndex };
                                        if (bitValue == 0) newMeaning.Status0 = status;
                                        else if (bitValue == 1) newMeaning.Status1 = status;
                                        item.Meaning.push(newMeaning);
                                    }
                                }
                            }
                        }
                        // 补充含义表的 Meaning 字段
                        if (protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
                            var meaningData = protocolItemsMeaningConfigHandsontableHelper.hot.getData();
                            for (var j = 0; j < meaningData.length; j++) {
                                var bitIdx = protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'value');
                                var meaningText = protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'meaning');
                                for (var k = 0; k < item.Meaning.length; k++) {
                                    if (bitIdx == item.Meaning[k].Value) {
                                        item.Meaning[k].Meaning = meaningText;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                configInfo.DataConfig.push(item);
            }
        }
    } else if (saveType === 2) {
        // 扩展字段 - 数值运算
        if (protocolExtendedFieldConfigHandsontableHelper && protocolExtendedFieldConfigHandsontableHelper.hot) {
            var extData = protocolExtendedFieldConfigHandsontableHelper.hot.getData();
            for (var i = 0; i < extData.length; i++) {
                var title = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title');
                if (!isNotVal(title)) continue;
                var extItem = {};
                extItem.Title = title;
                var title1 = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title1');
                extItem.Title1 = (title1 === (_loginUserLanguageResource.doubleClickCellTip + '...')) ? '' : title1;
                extItem.Operation = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'operation') || '';
                var title2 = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title2');
                extItem.Title2 = (title2 === (_loginUserLanguageResource.doubleClickCellTip + '...')) ? '' : title2;

                var precStr = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'prec') + '';
                precStr = precStr.replace(/\s/g, '');
                extItem.Prec = isNumber(parseFloat(precStr)) ? parseFloat(precStr) : 0;

                var ratioVal = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'ratio');
                extItem.Ratio = isNumber(parseFloat(ratioVal)) ? parseFloat(ratioVal) : 1;

                var unitVal = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'unit') + '';
                extItem.Unit = unitVal.replace(/\s/g, '');
                extItem.AdditionalConditions = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'additionalConditions') || '';

                configInfo.ExtendedFieldConfig.push(extItem);
            }
        }
    } else if (saveType === 3) {
        // 扩展字段 - 高低字节
        if (protocolExtendedFieldHighLowByteConfigHandsontableHelper && protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot) {
            var extData = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getData();
            var selectedRow = parseInt($('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val() || 0);
            for (var i = 0; i < extData.length; i++) {
                var title = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title');
                if (!isNotVal(title)) continue;
                var extItem = {};
                extItem.Title = title;
                var title1 = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title1');
                extItem.Title1 = (title1 === (_loginUserLanguageResource.doubleClickCellTip + '...')) ? '' : title1;

                var highLow = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'highLowByte');
                extItem.HighLowByte = '';
                if (highLow === (_loginUserLanguageResource.highByte || '高字节')) {
                    extItem.HighLowByte = 'high';
                } else if (highLow === (_loginUserLanguageResource.lowByte || '低字节')) {
                    extItem.HighLowByte = 'low';
                }

                extItem.ResolutionMode = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'resolutionMode') || '';

                var precStr = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'prec') + '';
                precStr = precStr.replace(/\s/g, '');
                extItem.Prec = isNumber(parseFloat(precStr)) ? parseFloat(precStr) : 0;

                var ratioVal = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'ratio');
                extItem.Ratio = isNumber(parseFloat(ratioVal)) ? parseFloat(ratioVal) : 1;

                var unitVal = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'unit') + '';
                extItem.Unit = unitVal.replace(/\s/g, '');

                // 只有选中行才收集含义/位状态
                if (i === selectedRow) {
                    extItem.Meaning = [];
                    if (extItem.ResolutionMode === (_loginUserLanguageResource.enumValue || '枚举量')) {
                        if (protocolExtendedFieldMeaningConfigHandsontableHelper && protocolExtendedFieldMeaningConfigHandsontableHelper.hot) {
                            var meaningData = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getData();
                            for (var j = 0; j < meaningData.length; j++) {
                                var val = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'value');
                                var meaning = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'meaning');
                                if (isNotVal(val) && isNotVal(meaning)) {
                                    extItem.Meaning.push({ Value: val, Meaning: meaning });
                                }
                            }
                        }
                    } else if (extItem.ResolutionMode === (_loginUserLanguageResource.switchingValue || '开关量')) {
                        if (protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper && protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot) {
                            var bitData = protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.getData();
                            for (var k = 0; k < bitData.length; k++) {
                                var status = protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'status');
                                var bitIndex = protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'bitIndex');
                                var bitValue = protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'value');
                                if (isNotVal(status)) {
                                    var exist = false;
                                    for (var m = 0; m < extItem.Meaning.length; m++) {
                                        if (bitIndex == extItem.Meaning[m].Value) {
                                            if (bitValue == 0) extItem.Meaning[m].Status0 = status;
                                            else if (bitValue == 1) extItem.Meaning[m].Status1 = status;
                                            exist = true;
                                            break;
                                        }
                                    }
                                    if (!exist) {
                                        var newMeaning = { Value: bitIndex };
                                        if (bitValue == 0) newMeaning.Status0 = status;
                                        else if (bitValue == 1) newMeaning.Status1 = status;
                                        extItem.Meaning.push(newMeaning);
                                    }
                                }
                            }
                        }
                        if (protocolExtendedFieldMeaningConfigHandsontableHelper && protocolExtendedFieldMeaningConfigHandsontableHelper.hot) {
                            var meaningData = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getData();
                            for (var j = 0; j < meaningData.length; j++) {
                                var bitIdx = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'value');
                                var meaningText = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'meaning');
                                for (var k = 0; k < extItem.Meaning.length; k++) {
                                    if (bitIdx == extItem.Meaning[k].Value) {
                                        extItem.Meaning[k].Meaning = meaningText;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                configInfo.ExtendedFieldConfig.push(extItem);
            }
        }
    }

    // 8. 重复检查（仅配置项表格）
    var hasDuplicate = false;
    if (saveType === 1 && protocolItemsConfigHandsontableHelper) {
        var dupList = protocolItemsConfigHandsontableHelper.getDuplicateRowList();
        var addrDupList = protocolItemsConfigHandsontableHelper.getAddrDuplicateRowList();
        if (dupList.length > 0 || addrDupList.length > 0) {
            hasDuplicate = true;
        }
    }

    // 9. 如果有重复，弹出确认对话框（用 mini.confirm）
    if (hasDuplicate) {
        mini.confirm(_loginUserLanguageResource.protocolSaveConfirm,
            _loginUserLanguageResource.confirm,
            function (action) {
                if (action == 'ok') {
                    saveModbusProtocolAddrMappingConfigData(configInfo, saveType);
                }
            }
        );
    } else {
        saveModbusProtocolAddrMappingConfigData(configInfo, saveType);
    }
}

function saveModbusProtocolAddrMappingConfigData(configInfo, saveType) {
    // 可选：显示页面遮罩（这里用 mini.loading 或直接控制 DOM）
    var loading = mini.loading(_loginUserLanguageResource.updateWait, _loginUserLanguageResource.tip);

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/saveModbusProtocolAddrMappingConfigData',
        data: {
            data: JSON.stringify(configInfo),
            saveType: saveType
        },
        dataType: 'json',
        success: function (response) {
            mini.hideMessageBox(loading);
            if (response.success) {
                // 清空临时数据（原逻辑调用了 clearContainer，这里保留空实现）
                if (protocolItemsConfigHandsontableHelper && typeof protocolItemsConfigHandsontableHelper.clearContainer === 'function') {
                    protocolItemsConfigHandsontableHelper.clearContainer();
                }

                if (configInfo.delidslist && configInfo.delidslist.length > 0) {
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                } else {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                }

                // 刷新协议树（根据原逻辑：删除时刷新，或 saveType==0 时刷新）
                if (configInfo.delidslist && configInfo.delidslist.length > 0) {
                    // 删除后刷新并重置选中状态
                    var tree = mini.get('protocolTree');
                    if (tree) {
                        tree.load(context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData');
                    }
                    // 清空隐藏域
                    $('#ModbusProtocolAddrMappingItemsSelectRow_Id').val(0);
                    $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val(0);
                } else {
                    if (saveType === 0) {
                        // 仅属性保存时刷新树（协议名称可能变化）
                        var tree = mini.get('protocolTree');
                        if (tree) {
                            tree.load(context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData');
                        }
                    }
                }
            } else {
                mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed) + '</font>');
            }
        },
        error: function () {
        	mini.hideMessageBox(loading);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

function addProtocolData() {
    // 获取当前选中的设备类型节点
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }

    var deviceTypeId = selectedNode.deviceTypeId;
    var deviceTypeName = selectedNode.text;

    // 打开添加窗口
    mini.open({
        title: _loginUserLanguageResource.addProtoco,
        url: context + '/miniui-app/modules/driverConfig/protocolAddWindow.jsp',
        width: 330,
        height: 280,
        modal: true,
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            // 传递参数
            contentWindow.setData({
                deviceTypeId: deviceTypeId,
                deviceTypeName: deviceTypeName,
                languageValue: _loginUserLanguageValue,
                language: _loginUserLanguage
            });
            // 暴露刷新树的函数给子窗口
            contentWindow._parentRefreshProtocolTree = function () {
                var tree = mini.get('protocolTree');
                if (tree) {
                    tree.load();
                }
            };
            // 暴露设置新协议名称的函数，用于树加载后高亮
            contentWindow._parentSetNewProtocolName = function (name) {
                window._newProtocolName = name;
                // 在树加载完成后会自动选中（见 onProtocolTreeLoad 中的处理）
            };
        },
        ondestroy: function (action) {
            // 如果子窗口成功添加，会刷新树，这里无需额外操作
        }
    });
}

function openFieldMappingWindow() {
    // 获取当前设备类型树选中节点的所有子节点 ID
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        return;
    }

    mini.open({
        title: _loginUserLanguageResource.fieldMappingTable,
        url: context + '/miniui-app/modules/driverConfig/databaseColumnMappingWindow.jsp',
        width: '65%',
        height: '80%',
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            // 传递 deviceTypeIds
            contentWindow.setData({
                deviceTypeIds: selectedDeviceTypeId
            });
        },
        ondestroy: function() {
            // 关闭后可选刷新
        }
    });
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
            ['acqUnitRightTabs', 'displayUnitRightTabs', 'alarmUnitRightTabs', 'reportUnitRightTabs'].forEach(function(id) {
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
                'unitRefreshBtn': 'refresh',
                'unitAddBtn': 'addUnit',
                'unitSaveBtn': 'save',
                'unitExportBtn': 'exportData',
                'unitImportBtn': 'importData',
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
        
        function updateBtnStatus(){
        	var btnIds = ['protocolAddBtn','protocolSaveBtn','protocolMappingBtn','protocolExportBtn','protocolImportBtn','protocolDeviceTypeChangeBtn'];
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