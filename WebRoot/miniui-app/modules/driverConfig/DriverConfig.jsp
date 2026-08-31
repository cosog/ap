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
        /* 全局样式（与之前相同，略作精简） */
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; font-family:"Microsoft YaHei",Arial,sans-serif; background:#f0f2f5; }
        .driver-container { width:100%; height:100%; display:flex; flex-direction:column; background:#fff; }
        .driver-container .mini-splitter { flex:1; }
        .left-panel { display:flex; flex-direction:column; height:100%; background:#f0f2f5; padding:4px; }
        .left-panel .tree-area { flex:1; background:#fff; border-radius:4px; box-shadow:0 1px 4px rgba(0,0,0,0.06); overflow:hidden; display:flex; flex-direction:column; }
        .left-panel .tree-area .mini-tree { flex:1; width:100%; height:100%; }
        .right-panel { display:flex; flex-direction:column; height:100%; background:#f0f2f5; padding:4px; }
        .right-panel .mini-tabs { flex:1; width:100%; height:100%; }
        .right-panel .mini-tabs-body, .right-panel .mini-tab-body { height:100% !important; padding:0 !important; margin:0 !important; overflow:hidden !important; }
        .tab-content-layout { width:100%; height:100%; display:flex; flex-direction:column; background:#fff; }
        .tab-content-layout .mini-toolbar { flex-shrink:0; border-bottom:1px solid #e8e8e8; padding:4px 8px; display:flex; align-items:center; flex-wrap:wrap; gap:4px; background:#fafafa; }
        .tab-content-layout .tab-body { flex:1; display:flex; overflow:hidden; }
        .tab-content-layout .tab-body .left-tree { width:25%; border-right:1px solid #e8e8e8; overflow:auto; padding:4px; background:#fafafa; }
        .tab-content-layout .tab-body .right-detail { flex:1; display:flex; flex-direction:column; overflow:hidden; padding:4px; background:#fff; }
        .tab-content-layout .tab-body .right-detail .mini-tabs { flex:1; width:100%; height:100%; }
        .tab-content-layout .tab-body .right-detail .mini-tabs-body, .tab-content-layout .tab-body .right-detail .mini-tab-body { height:100% !important; padding:0 !important; margin:0 !important; overflow:hidden !important; }

        .config-layout { display:flex; flex:1; overflow:hidden; }
        .config-layout .left-config { flex:1; border-right:1px solid #e8e8e8; overflow:auto; padding:4px; background:#fafafa; }
        .config-layout .right-meaning { width:30%; display:flex; flex-direction:column; overflow:hidden; padding:4px; background:#fff; }
        .config-layout .right-meaning .meaning-top { flex:1; overflow:auto; border-bottom:1px solid #e8e8e8; padding:4px; }
        .config-layout .right-meaning .meaning-bottom { height:50%; overflow:auto; padding:4px; }

        .extended-layout { display:flex; flex:1; overflow:hidden; }
        .extended-layout .left-ext { flex:1; border-right:1px solid #e8e8e8; overflow:auto; padding:4px; background:#fafafa; }
        .extended-layout .right-ext-meaning { width:30%; display:flex; flex-direction:column; overflow:hidden; padding:4px; background:#fff; }
        .extended-layout .right-ext-meaning .ext-meaning-top { flex:1; overflow:auto; border-bottom:1px solid #e8e8e8; padding:4px; }
        .extended-layout .right-ext-meaning .ext-meaning-bottom { height:50%; overflow:auto; padding:4px; }

        /* 单元/实例通用三栏 */
        .unit-layout, .instance-layout { display:flex; flex:1; overflow:hidden; }
        .unit-layout .left-protocol, .instance-layout .left-protocol { width:25%; border-right:1px solid #e8e8e8; overflow:auto; padding:4px; background:#fafafa; }
        .unit-layout .middle-list, .instance-layout .middle-list { width:30%; border-right:1px solid #e8e8e8; overflow:auto; padding:4px; background:#fafafa; }
        .unit-layout .right-config, .instance-layout .right-property { flex:1; display:flex; flex-direction:column; overflow:hidden; padding:4px; background:#fff; }
        .unit-layout .right-config .mini-tabs, .instance-layout .right-property .mini-tabs { flex:1; width:100%; height:100%; }
        .unit-layout .right-config .mini-tabs-body, .unit-layout .right-config .mini-tab-body,
        .instance-layout .right-property .mini-tabs-body, .instance-layout .right-property .mini-tab-body { height:100% !important; padding:0 !important; margin:0 !important; overflow:hidden !important; }

        /* 报警单元配置内部 */
        .alarm-config-layout { display:flex; flex:1; overflow:hidden; }
        .alarm-config-layout .alarm-left-list { width:40%; border-right:1px solid #e8e8e8; overflow:auto; padding:4px; background:#fafafa; }
        .alarm-config-layout .alarm-right-detail { flex:1; display:flex; flex-direction:column; overflow:hidden; padding:4px; background:#fff; }
        .alarm-config-layout .alarm-right-detail .mini-tabs { flex:1; width:100%; height:100%; }

        /* 报表单元配置内部 */
        .report-config-layout { display:flex; flex:1; overflow:hidden; }
        .report-config-layout .report-left-list { width:30%; border-right:1px solid #e8e8e8; overflow:auto; padding:4px; background:#fafafa; }
        .report-config-layout .report-right-detail { flex:1; display:flex; flex-direction:column; overflow:hidden; padding:4px; background:#fff; }

        .sub-tab-placeholder { width:100%; height:100%; display:flex; align-items:center; justify-content:center; color:#ccc; font-size:14px; }
        .mini-toolbar .separator { width:1px; height:20px; background:#ddd; margin:0 4px; }
        .empty-msg { color:#999; font-size:13px; text-align:center; padding:20px; }
        .mini-tabs-body { overflow:hidden !important; }
        .inner-toolbar { border-bottom:1px solid #e8e8e8; padding:2px 8px; display:flex; align-items:center; gap:4px; flex-shrink:0; background:#fafafa; }
    </style>
</head>
<body>
<div class="driver-container">
    <div class="mini-splitter" style="width:100%; height:100%;" vertical="false">
        <!-- 左侧区域（设备类型树） -->
        <div size="22%" showCollapseButton="true" minSize="200" collapseDirection="left">
            <div class="left-panel" style="height:100%;">
                <div class="tree-area">
                    <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:2px 8px;flex-shrink:0;">
                        <span id="deviceTypeTitle">Device Type</span>
                    </div>
                    <div id="deviceTypeTree" class="mini-tree" style="width:100%;height:100%;"
                         showTreeIcon="true" expandOnNodeClick="true"
                         idField="id" parentField="pid" resultAsTree="true">
                        <div property="emptyText" class="empty-msg">Empty</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 右侧主Tabs -->
        <div size="78%" showCollapseButton="false" minSize="300">
            <div class="right-panel" style="height:100%;">
                <div id="mainTabs" class="mini-tabs" style="height:100%; width:100%; overflow:hidden;"
                     activeIndex="0" tabPosition="bottom">
                    <!-- ===================== 协议配置 ===================== -->
                    <div title="Protocol" name="protocol" style="height:100%;">
                        <div class="tab-content-layout">
                            <div class="mini-toolbar">
                                <button id="protocolRefreshBtn" class="mini-button" iconCls="note-refresh">Refresh</button>
                                <span class="separator"></span>
                                <button id="protocolAddBtn" class="mini-button" iconCls="add">Add</button>
                                <button id="protocolSaveBtn" class="mini-button" iconCls="save">Save</button>
                                <span class="separator"></span>
                                <button id="protocolMappingBtn" class="mini-button" iconCls="table">Mapping</button>
                                <button id="protocolExportBtn" class="mini-button" iconCls="export">Export</button>
                                <button id="protocolImportBtn" class="mini-button" iconCls="import">Import</button>
                                <button id="protocolDeviceTypeChangeBtn" class="mini-button" iconCls="move">Move</button>
                                <span style="flex:1;"></span>
                                <span id="protocolInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                            </div>
                            <div class="tab-body">
                                <div class="left-tree" id="protocolTreeContainer">
                                    <div class="sub-tab-placeholder" style="font-size:13px;color:#666;">Protocol List</div>
                                </div>
                                <div class="right-detail">
                                    <div id="protocolSubTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0">
                                        <div title="Properties" name="props" style="height:100%;">
                                            <div class="sub-tab-placeholder">Properties Content</div>
                                        </div>
                                        <div title="Config" name="config" style="height:100%;">
                                            <div class="config-layout">
                                                <div class="left-config"><div class="sub-tab-placeholder">Config Items</div></div>
                                                <div class="right-meaning">
                                                    <div class="meaning-top"><div class="sub-tab-placeholder">Meaning</div></div>
                                                    <div class="meaning-bottom"><div class="sub-tab-placeholder">Switching Bit Status</div></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div title="Extended" name="extended" style="height:100%;">
                                            <div id="extendedSubTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0">
                                                <div title="Numeric Calculation" name="numeric" style="height:100%;">
                                                    <div class="sub-tab-placeholder">Extended Numeric Config</div>
                                                </div>
                                                <div title="High/Low Byte" name="highlow" style="height:100%;">
                                                    <div class="extended-layout">
                                                        <div class="left-ext"><div class="sub-tab-placeholder">High/Low Byte Config</div></div>
                                                        <div class="right-ext-meaning">
                                                            <div class="ext-meaning-top"><div class="sub-tab-placeholder">Meaning</div></div>
                                                            <div class="ext-meaning-bottom"><div class="sub-tab-placeholder">Switching Bit Status</div></div>
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
                                <div id="unitSubTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" tabPosition="left">
                                    <!-- ===== 采集单元 ===== -->
                                    <div title="AcqUnit" name="acq" style="height:100%;">
                                        <div class="unit-layout" style="flex-direction:column;">
                                            <!-- 采集单元专用工具栏 -->
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
                                                <div class="left-protocol" id="acqUnitProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="acqUnitList"><div class="sub-tab-placeholder">Acq Unit List</div></div>
                                                <div class="right-config">
                                                    <div id="acqUnitRightTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                        <div title="Properties" name="props" style="height:100%;">
                                                            <div class="sub-tab-placeholder">Properties</div>
                                                        </div>
                                                        <div title="Config" name="config" style="height:100%;">
                                                            <!-- 配置内部工具栏：全选/取消全选 -->
                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                <button id="acqUnitConfigSelectAllBtn" class="mini-button">Select All</button>
                                                                <button id="acqUnitConfigDeselectAllBtn" class="mini-button">Deselect All</button>
                                                                <span style="flex:1;"></span>
                                                            </div>
                                                            <div style="flex:1;overflow:auto;padding:4px;">
                                                                <div class="sub-tab-placeholder" style="height:100%;">Acq Unit Config Table</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ===== 显示单元 ===== -->
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
                                                <div class="left-protocol" id="displayUnitProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="displayUnitList"><div class="sub-tab-placeholder">Display Unit List</div></div>
                                                <div class="right-config">
                                                    <div id="displayUnitRightTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                        <div title="Properties" name="props" style="height:100%;"><div class="sub-tab-placeholder">Properties</div></div>
                                                        <div title="Config" name="config" style="height:100%;">
                                                            <!-- 显示单元配置内部有两部分：采集项配置、控制项配置，各有全选/取消全选 -->
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
                                                                        <div class="sub-tab-placeholder">Acq Items Config</div>
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
                                                                        <div class="sub-tab-placeholder">Ctrl Items Config</div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ===== 报警单元 ===== -->
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
                                                <div class="left-protocol" id="alarmUnitProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="alarmUnitList"><div class="sub-tab-placeholder">Alarm Unit List</div></div>
                                                <div class="right-config">
                                                    <div id="alarmUnitRightTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                        <div title="Properties" name="props" style="height:100%;">
                                                            <div class="sub-tab-placeholder">Properties</div>
                                                        </div>
                                                        <div title="Config" name="config" style="height:100%;">
                                                            <div class="alarm-config-layout">
                                                                <div class="alarm-left-list">
                                                                    <div class="sub-tab-placeholder">Alarm Items List</div>
                                                                </div>
                                                                <div class="alarm-right-detail">
                                                                    <div id="alarmConfigSubTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                                        <!-- 数值 -->
                                                                        <div title="Numeric" name="numeric" style="height:100%;">
                                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                <button id="alarmNumericSelectAll" class="mini-button">Select All</button>
                                                                                <button id="alarmNumericDeselectAll" class="mini-button">Deselect All</button>
                                                                                <span style="flex:1;"></span>
                                                                            </div>
                                                                            <div style="flex:1;overflow:auto;padding:4px;">
                                                                                <div class="sub-tab-placeholder">Numeric Alarm Config</div>
                                                                            </div>
                                                                        </div>
                                                                        <!-- 开关 -->
                                                                        <div title="Switching" name="switching" style="height:100%;">
                                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                <button id="alarmSwitchSelectAll" class="mini-button">Select All</button>
                                                                                <button id="alarmSwitchDeselectAll" class="mini-button">Deselect All</button>
                                                                                <span style="flex:1;"></span>
                                                                            </div>
                                                                            <div style="flex:1;overflow:auto;padding:4px;">
                                                                                <div class="sub-tab-placeholder">Switching Alarm Config</div>
                                                                            </div>
                                                                        </div>
                                                                        <!-- 枚举 -->
                                                                        <div title="Enum" name="enum" style="height:100%;">
                                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                <button id="alarmEnumSelectAll" class="mini-button">Select All</button>
                                                                                <button id="alarmEnumDeselectAll" class="mini-button">Deselect All</button>
                                                                                <span style="flex:1;"></span>
                                                                            </div>
                                                                            <div style="flex:1;overflow:auto;padding:4px;">
                                                                                <div class="sub-tab-placeholder">Enum Alarm Config</div>
                                                                            </div>
                                                                        </div>
                                                                        <!-- 通信状态 -->
                                                                        <div title="CommStatus" name="comm" style="height:100%;">
                                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                <button id="alarmCommSelectAll" class="mini-button">Select All</button>
                                                                                <button id="alarmCommDeselectAll" class="mini-button">Deselect All</button>
                                                                                <span style="flex:1;"></span>
                                                                            </div>
                                                                            <div style="flex:1;overflow:auto;padding:4px;">
                                                                                <div class="sub-tab-placeholder">Comm Status Alarm Config</div>
                                                                            </div>
                                                                        </div>
                                                                        <!-- 运行状态 -->
                                                                        <div title="RunStatus" name="run" style="height:100%;">
                                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                <button id="alarmRunSelectAll" class="mini-button">Select All</button>
                                                                                <button id="alarmRunDeselectAll" class="mini-button">Deselect All</button>
                                                                                <span style="flex:1;"></span>
                                                                            </div>
                                                                            <div style="flex:1;overflow:auto;padding:4px;">
                                                                                <div class="sub-tab-placeholder">Run Status Alarm Config</div>
                                                                            </div>
                                                                        </div>
                                                                        <!-- 工况条件 -->
                                                                        <div title="FESDiagram" name="fes" style="height:100%;">
                                                                            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 8px;display:flex;align-items:center;gap:4px;flex-shrink:0;background:#fafafa;">
                                                                                <button id="alarmFESSelectAll" class="mini-button">Select All</button>
                                                                                <button id="alarmFESDeselectAll" class="mini-button">Deselect All</button>
                                                                                <span style="flex:1;"></span>
                                                                            </div>
                                                                            <div style="flex:1;overflow:auto;padding:4px;">
                                                                                <div class="sub-tab-placeholder">FES Diagram Alarm Config</div>
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
                                    <!-- ===== 报表单元 ===== -->
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
                                                <div class="left-protocol" id="reportUnitProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="reportUnitList"><div class="sub-tab-placeholder">Report Unit List</div></div>
                                                <div class="right-config">
                                                    <div id="reportUnitRightTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                        <div title="Properties" name="props" style="height:100%;">
                                                            <div class="sub-tab-placeholder">Properties</div>
                                                        </div>
                                                        <div title="Config" name="config" style="height:100%;">
                                                            <div class="report-config-layout">
                                                                <div class="report-left-list">
                                                                    <div class="sub-tab-placeholder">Report Templates</div>
                                                                </div>
                                                                <div class="report-right-detail">
                                                                    <div id="reportConfigSubTabs" class="mini-tabs" style="flex:1;width:100%;" activeIndex="0" tabPosition="top">
                                                                        <div title="Single Well Report" name="single" style="height:100%;">
                                                                            <div class="sub-tab-placeholder">Single Well Report Config</div>
                                                                        </div>
                                                                        <div title="Area Report" name="area" style="height:100%;">
                                                                            <div class="sub-tab-placeholder">Area Report Config</div>
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
                                <div id="instanceSubTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" tabPosition="left">
                                    <!-- ===== 采控实例 ===== -->
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
                                                <div class="left-protocol" id="acqInstanceProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="acqInstanceList"><div class="sub-tab-placeholder">Acq Instance List</div></div>
                                                <div class="right-property">
                                                    <div class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ===== 显示实例 ===== -->
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
                                                <div class="left-protocol" id="displayInstanceProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="displayInstanceList"><div class="sub-tab-placeholder">Display Instance List</div></div>
                                                <div class="right-property">
                                                    <div class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ===== 报警实例 ===== -->
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
                                                <div class="left-protocol" id="alarmInstanceProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="alarmInstanceList"><div class="sub-tab-placeholder">Alarm Instance List</div></div>
                                                <div class="right-property">
                                                    <div class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ===== 报表实例 ===== -->
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
                                                <div class="left-protocol" id="reportInstanceProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="reportInstanceList"><div class="sub-tab-placeholder">Report Instance List</div></div>
                                                <div class="right-property">
                                                    <div class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ===== 短信实例 ===== -->
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
                                                <div class="left-protocol" id="smsInstanceProtocolTree"><div class="sub-tab-placeholder">Protocol List</div></div>
                                                <div class="middle-list" id="smsInstanceList"><div class="sub-tab-placeholder">SMS Instance List</div></div>
                                                <div class="right-property">
                                                    <div class="sub-tab-placeholder" style="height:100%;">Instance Properties</div>
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
                mainTabs.updateTab(tabs[0], { title: _loginUserLanguageResource.protocolConfig });
                mainTabs.updateTab(tabs[1], { title: _loginUserLanguageResource.unitConfig });
                mainTabs.updateTab(tabs[2], { title: _loginUserLanguageResource.instanceConfig });
            }
        }

        // ---- 协议配置子标签 ----
        var protoSub = mini.get('protocolSubTabs');
        if (protoSub) {
            var tabs = protoSub.getTabs();
            if (tabs && tabs.length >= 3) {
                protoSub.updateTab(tabs[0], { title: _loginUserLanguageResource.properties });
                protoSub.updateTab(tabs[1], { title: _loginUserLanguageResource.config });
                protoSub.updateTab(tabs[2], { title: _loginUserLanguageResource.extendedField });
            }
        }
        var extSub = mini.get('extendedSubTabs');
        if (extSub) {
            var tabs = extSub.getTabs();
            if (tabs && tabs.length >= 2) {
                extSub.updateTab(tabs[0], { title: _loginUserLanguageResource.numericCalculation });
                extSub.updateTab(tabs[1], { title: _loginUserLanguageResource.highLowByte });
            }
        }

        // ---- 单元配置子标签 ----
        var unitSub = mini.get('unitSubTabs');
        if (unitSub) {
            var tabs = unitSub.getTabs();
            if (tabs && tabs.length >= 4) {
                unitSub.updateTab(tabs[0], { title: _loginUserLanguageResource.acqUnit });
                unitSub.updateTab(tabs[1], { title: _loginUserLanguageResource.displayUnit });
                unitSub.updateTab(tabs[2], { title: _loginUserLanguageResource.alarmUnit });
                unitSub.updateTab(tabs[3], { title: _loginUserLanguageResource.reportUnit });
            }
        }
        // 各单元右侧子标签
        ['acqUnitRightTabs','displayUnitRightTabs','alarmUnitRightTabs','reportUnitRightTabs'].forEach(function(id) {
            var tab = mini.get(id);
            if (tab) {
                var tabs = tab.getTabs();
                if (tabs && tabs.length >= 2) {
                    tab.updateTab(tabs[0], { title: _loginUserLanguageResource.properties });
                    tab.updateTab(tabs[1], { title: _loginUserLanguageResource.config });
                }
            }
        });
        // 报警配置子标签
        var alarmConfigSub = mini.get('alarmConfigSubTabs');
        if (alarmConfigSub) {
            var tabs = alarmConfigSub.getTabs();
            if (tabs && tabs.length >= 6) {
                alarmConfigSub.updateTab(tabs[0], { title: _loginUserLanguageResource.numericValue });
                alarmConfigSub.updateTab(tabs[1], { title: _loginUserLanguageResource.switchingValue });
                alarmConfigSub.updateTab(tabs[2], { title: _loginUserLanguageResource.enumValue });
                alarmConfigSub.updateTab(tabs[3], { title: _loginUserLanguageResource.commStatus });
                alarmConfigSub.updateTab(tabs[4], { title: _loginUserLanguageResource.runStatus });
                alarmConfigSub.updateTab(tabs[5], { title: _loginUserLanguageResource.FESDiagramResultAlarm });
            }
        }
        // 报表配置子标签
        var reportConfigSub = mini.get('reportConfigSubTabs');
        if (reportConfigSub) {
            var tabs = reportConfigSub.getTabs();
            if (tabs && tabs.length >= 2) {
                reportConfigSub.updateTab(tabs[0], { title: _loginUserLanguageResource.singleWellReport });
                reportConfigSub.updateTab(tabs[1], { title: _loginUserLanguageResource.areaReport });
            }
        }

        // ---- 实例配置子标签 ----
        var instanceSub = mini.get('instanceSubTabs');
        if (instanceSub) {
            var tabs = instanceSub.getTabs();
            if (tabs && tabs.length >= 5) {
                instanceSub.updateTab(tabs[0], { title: _loginUserLanguageResource.acqInstance });
                instanceSub.updateTab(tabs[1], { title: _loginUserLanguageResource.displayInstance });
                instanceSub.updateTab(tabs[2], { title: _loginUserLanguageResource.alarmInstance });
                instanceSub.updateTab(tabs[3], { title: _loginUserLanguageResource.reportInstance });
                instanceSub.updateTab(tabs[4], { title: _loginUserLanguageResource.SMSInstance });
            }
        }

        // ---- 按钮文本（主工具栏） ----
        var btnMap = {
            'protocolRefreshBtn':'refresh','protocolAddBtn':'addProtocol','protocolSaveBtn':'save',
            'protocolMappingBtn':'fieldMappingTable','protocolExportBtn':'exportData','protocolImportBtn':'importData',
            'protocolDeviceTypeChangeBtn':'protocoDeviceTypeChange',
            'unitRefreshBtn':'refresh','unitAddBtn':'addUnit','unitSaveBtn':'save',
            'unitExportBtn':'exportData','unitImportBtn':'importData',
            'instanceRefreshBtn':'refresh','instanceAddBtn':'addInstance','instanceSaveBtn':'save',
            'instanceExportBtn':'exportData','instanceImportBtn':'importData'
        };
        for (var id in btnMap) {
            var btn = mini.get(id);
            if (btn) btn.setText(_loginUserLanguageResource[btnMap[id]] || btnMap[id]);
        }

        // ---- 各子模块工具栏按钮 ----
        var subBtnMap = {
            // 采集单元
            'acqUnitRefreshBtn':'refresh','acqUnitAddBtn':'addAcqUnit','acqUnitAddGroupBtn':'addAcqGroup',
            'acqUnitAddCtrlGroupBtn':'addCtrlGroup','acqUnitSaveBtn':'save','acqUnitExportBtn':'exportData','acqUnitImportBtn':'importData',
            'acqUnitConfigSelectAllBtn':'selectAll','acqUnitConfigDeselectAllBtn':'deselectAll',
            // 显示单元
            'displayUnitRefreshBtn':'refresh','displayUnitAddBtn':'addDisplayUnit','displayUnitSaveBtn':'save',
            'displayUnitExportBtn':'exportData','displayUnitImportBtn':'importData',
            'displayAcqSelectAllBtn':'selectAll','displayAcqDeselectAllBtn':'deselectAll',
            'displayCtrlSelectAllBtn':'selectAll','displayCtrlDeselectAllBtn':'deselectAll',
            // 报警单元
            'alarmUnitRefreshBtn':'refresh','alarmUnitAddBtn':'addAlarmUnit','alarmUnitSaveBtn':'save',
            'alarmUnitColorBtn':'alarmColorConfig','alarmUnitExportBtn':'exportData','alarmUnitImportBtn':'importData',
            'alarmNumericSelectAll':'selectAll','alarmNumericDeselectAll':'deselectAll',
            'alarmSwitchSelectAll':'selectAll','alarmSwitchDeselectAll':'deselectAll',
            'alarmEnumSelectAll':'selectAll','alarmEnumDeselectAll':'deselectAll',
            'alarmCommSelectAll':'selectAll','alarmCommDeselectAll':'deselectAll',
            'alarmRunSelectAll':'selectAll','alarmRunDeselectAll':'deselectAll',
            'alarmFESSelectAll':'selectAll','alarmFESDeselectAll':'deselectAll',
            // 报表单元
            'reportUnitRefreshBtn':'refresh','reportUnitAddBtn':'addReportUnit','reportUnitSaveBtn':'save',
            'reportUnitExportBtn':'exportData','reportUnitImportBtn':'importData',
            // 采控实例
            'acqInstanceRefreshBtn':'refresh','acqInstanceAddBtn':'addAcqInstance','acqInstanceSaveBtn':'save',
            'acqInstanceExportBtn':'exportData','acqInstanceImportBtn':'importData',
            // 显示实例
            'displayInstanceRefreshBtn':'refresh','displayInstanceAddBtn':'addAcqInstance','displayInstanceSaveBtn':'save',
            'displayInstanceExportBtn':'exportData','displayInstanceImportBtn':'importData',
            // 报警实例
            'alarmInstanceRefreshBtn':'refresh','alarmInstanceAddBtn':'addAcqInstance','alarmInstanceSaveBtn':'save',
            'alarmInstanceExportBtn':'exportData','alarmInstanceImportBtn':'importData',
            // 报表实例
            'reportInstanceRefreshBtn':'refresh','reportInstanceAddBtn':'addAcqInstance','reportInstanceSaveBtn':'save',
            'reportInstanceExportBtn':'exportData','reportInstanceImportBtn':'importData',
            // 短信实例
            'smsInstanceRefreshBtn':'refresh','smsInstanceAddBtn':'add','smsInstanceUpdateBtn':'update',
            'smsInstanceDeleteBtn':'deleteData'
        };
        for (var id in subBtnMap) {
            var btn = mini.get(id);
            if (btn) {
                var key = subBtnMap[id];
                btn.setText(_loginUserLanguageResource[key] || key);
            }
        }

        // ---- 各占位文本 ----
        var placeholders = {
            '#protocolTreeContainer .sub-tab-placeholder': 'protocolList',
            '.config-layout .left-config .sub-tab-placeholder': 'acqAndCtrlItemConfig',
            '.config-layout .right-meaning .meaning-top .sub-tab-placeholder': 'meaning',
            '.config-layout .right-meaning .meaning-bottom .sub-tab-placeholder': 'switchingValueBitStatusConfig',
            '.extended-layout .left-ext .sub-tab-placeholder': 'config',
            '.extended-layout .right-ext-meaning .ext-meaning-top .sub-tab-placeholder': 'meaning',
            '.extended-layout .right-ext-meaning .ext-meaning-bottom .sub-tab-placeholder': 'switchingValueBitStatusConfig',
            '#acqUnitProtocolTree .sub-tab-placeholder': 'protocolList',
            '#acqUnitList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.acqUnit + ' List'; },
            '#displayUnitProtocolTree .sub-tab-placeholder': 'protocolList',
            '#displayUnitList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.displayUnit + ' List'; },
            '#alarmUnitProtocolTree .sub-tab-placeholder': 'protocolList',
            '#alarmUnitList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.alarmUnit + ' List'; },
            '#reportUnitProtocolTree .sub-tab-placeholder': 'protocolList',
            '#reportUnitList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.reportUnit + ' List'; },
            '.alarm-left-list .sub-tab-placeholder': 'alarmItemsList',
            '.report-left-list .sub-tab-placeholder': 'reportTemplates',
            '#acqInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
            '#acqInstanceList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.acqInstance + ' List'; },
            '#displayInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
            '#displayInstanceList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.displayInstance + ' List'; },
            '#alarmInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
            '#alarmInstanceList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.alarmInstance + ' List'; },
            '#reportInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
            '#reportInstanceList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.reportInstance + ' List'; },
            '#smsInstanceProtocolTree .sub-tab-placeholder': 'protocolList',
            '#smsInstanceList .sub-tab-placeholder': function(){ return _loginUserLanguageResource.SMSInstance + ' List'; },
            '.instance-layout .right-property .sub-tab-placeholder': 'properties'
        };
        for (var selector in placeholders) {
            var el = document.querySelector(selector);
            if (el) {
                var val = placeholders[selector];
                if (typeof val === 'function') {
                    el.innerHTML = val();
                } else {
                    el.innerHTML = _loginUserLanguageResource[val] || val;
                }
            }
        }

        // ---- 信息标签置空 ----
        ['protocolInfoLabel','unitInfoLabel','instanceInfoLabel',
         'acqUnitInfoLabel','displayUnitInfoLabel','alarmUnitInfoLabel','reportUnitInfoLabel',
         'acqInstanceInfoLabel','displayInstanceInfoLabel','alarmInstanceInfoLabel','reportInstanceInfoLabel','smsInstanceInfoLabel'
        ].forEach(function(id) {
            var el = document.getElementById(id);
            if (el) el.innerHTML = '';
        });
    }

    // ================================================================
    // 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        initI18n();
        console.log('驱动配置模块布局加载完成（完整嵌套结构，各子模块独立工具栏）');
    });

    // ================================================================
    // 占位事件处理（供按钮点击，避免报错）
    // ================================================================
    // 由于按钮较多，用通用函数避免报错
    $(document).on('click', '.mini-button', function(e) {
        var btn = e.currentTarget;
        var id = btn.id;
        if (id) {
            var key = _loginUserLanguageResource[id] || id;
            mini.alert(key + ' (占位)');
        }
    });
</script>
</body>
</html>