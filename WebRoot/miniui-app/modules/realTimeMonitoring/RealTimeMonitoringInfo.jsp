<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String moduleId = request.getParameter("moduleId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>实时监控</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />

    <style>
        /* ===== 全局样式 ===== */
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .realtime-container { width: 100%; height: 100%; display: flex; flex-direction: column; background: #fff; }
        .level1-footer {
            flex-shrink: 0;
            background: #fafafa;
            border-top: 2px solid #e8e8e8;
            padding: 0 10px;
            display: flex;
            align-items: center;
            gap: 2px;
            height: 36px;
            overflow-x: auto;
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
        .level1-footer .loading-tip { color: #999; font-size: 12px; padding: 0 10px; }

        /* ===== 资源监测区域（与一级标签同行右侧） ===== */
        #resourceMonitorArea {
            margin-left: auto;
            display: flex;
            align-items: center;
            gap: 6px;
            white-space: nowrap;
            font-size: 12px;
        }
        #resourceMonitorArea .res-btn {
            padding: 0 6px;
            height: 22px;
            line-height: 22px;
            font-size: 11px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #f5f5f5;
            cursor: pointer;
            color: #333;
        }
        #resourceMonitorArea .res-btn:hover {
            background: #e6e6e6;
        }
        #resourceMonitorArea .res-label {
            color: #333;
            cursor: default;
        }
        #resourceMonitorArea .status-green {
            color: #52c41a;
        }
        #resourceMonitorArea .status-yellow {
            color: #faad14;
        }
        #resourceMonitorArea .status-red {
            color: #ff4d4f;
        }

        /* ===== 其他已有样式（保持原样） ===== */
        .main-area { flex: 1; display: flex; flex-direction: row; overflow: hidden; min-height: 0; order: 0; }
        .level2-sidebar { flex-shrink: 0; width: 32px; background: #f5f7fa; border-right: 1px solid #e8e8e8; overflow: auto; padding: 8px 0; display: flex; flex-direction: column; align-items: center; justify-content: flex-start; }
        .level2-sidebar .tab-item { padding: 10px 2px; font-size: 12px; cursor: pointer; color: #555; background: transparent; border-left: 3px solid transparent; transition: all 0.15s; user-select: none; text-align: center; writing-mode: vertical-rl; letter-spacing: 2px; width: 100%; flex-shrink: 0; height: auto; min-height: 36px; line-height: 1.4; box-sizing: border-box; }
        .level2-sidebar .tab-item:hover { background: #e8ecf0; color: #333; }
        .level2-sidebar .tab-item.active { background: #e6f7ff; color: #1890ff; font-weight: bold; border-left-color: #1890ff; }
        .level2-sidebar .no-child-tip { padding: 12px 0; color: #999; font-size: 12px; text-align: center; writing-mode: vertical-rl; letter-spacing: 2px; }
        .left-main-area { flex: 4; display: flex; flex-direction: column; overflow: hidden; min-width: 0; background: #f0f2f5; padding: 4px; }
        .device-overview-area { flex: 1; min-height: 120px; background: #fff; border-radius: 4px; box-shadow: 0 1px 4px rgba(0,0,0,0.06); overflow: hidden; display: flex; flex-direction: column; margin-bottom: 4px; }
        .device-overview-area .toolbar { flex-shrink: 0; padding: 4px 8px; background: #fafafa; border-bottom: 1px solid #e8e8e8; display: flex; align-items: center; gap: 6px; flex-wrap: wrap; }
        .device-overview-area .toolbar .mini-combobox { width: 140px; }
        .device-overview-area .toolbar .spacer { flex: 1; }
        .device-overview-area .grid-wrapper { flex: 1; overflow: hidden; position: relative; }
        .device-overview-area .grid-wrapper .mini-datagrid { width: 100%; height: 100%; }
        .stat-charts-area { flex: 0 0 45%; min-height: 140px; background: #fff; border-radius: 4px; box-shadow: 0 1px 4px rgba(0,0,0,0.06); overflow: hidden; display: flex; flex-direction: column; }
        .stat-charts-area .mini-tabs { flex: 1; }
        .stat-charts-area .mini-tabs .mini-tab-active { background: #e6f7ff; border-bottom: 2px solid #1890ff; color: #1890ff; font-weight: bold; }
        .pie-chart-container { width: 100%; height: 100%; min-height: 100px; }
        .middle-area { flex: 6; min-width: 200px; background: #fff; border-radius: 4px; box-shadow: 0 1px 4px rgba(0,0,0,0.06); overflow: hidden; display: flex; flex-direction: column; margin: 4px 4px 4px 0; }
        .middle-area .mini-tabs { flex: 1; }
        .middle-area .mini-tabs .mini-tab-body { overflow: hidden !important; padding: 0 !important; margin: 0 !important; display: flex !important; flex-direction: column !important; }
        .right-area { flex: 0 0 21%; min-width: 130px; background: #fff; border-radius: 4px; box-shadow: 0 1px 4px rgba(0,0,0,0.06); overflow: hidden; display: flex; flex-direction: column; margin: 4px 4px 4px 0; }
        .right-area .mini-tabs { flex: 1; }
        .loading-placeholder { display: flex; align-items: center; justify-content: center; height: 100%; color: #999; font-size: 13px; flex-direction: column; }
        .loading-placeholder .icon { font-size: 32px; margin-bottom: 8px; }
        .loading-placeholder.error { color: #ff4d4f; }
        .alarm-badge { display: inline-block; border-radius: 10px; padding: 0 4px; min-width: 14px; height: 14px; line-height: 14px; text-align: center; font-size: 9px; font-weight: bold; margin-right: 2px; vertical-align: middle; }
        .level2-sidebar::-webkit-scrollbar { width: 3px; }
        .level2-sidebar::-webkit-scrollbar-thumb { background: #ccc; border-radius: 4px; }
        @media (max-width: 768px) {
            .main-area { flex-direction: column; }
            .level2-sidebar { width: 100%; height: 32px; flex-direction: row; padding: 0 8px; border-right: none; border-bottom: 1px solid #e8e8e8; overflow-x: auto; }
            .level2-sidebar .tab-item { writing-mode: horizontal-tb; transform: none; min-height: auto; padding: 4px 12px; border-left: none; border-bottom: 2px solid transparent; }
            .level2-sidebar .tab-item.active { border-left: none; border-bottom-color: #1890ff; }
            .left-main-area { flex: 2; }
            .middle-area { flex: 1; min-height: 150px; margin: 0 4px 4px 4px; }
            .right-area { flex: 0 0 160px; margin: 0 4px 4px 4px; }
            .stat-charts-area { flex: 0 0 40%; min-height: 100px; }
        }

        /* 图表容器内布局（井筒分析、地面分析） */
        .chart-grid { display: flex; flex-wrap: wrap; width: 100%; height: 100%; background: #fff; }
        .chart-grid .chart-item { flex: 1 1 50%; min-width: 300px; height: 50%; box-sizing: border-box; border: 1px solid #f0f0f0; position: relative; overflow: hidden; }
        .chart-grid .chart-item .chart-container { width: 100%; height: 100%; }
        .chart-grid .full-width { flex: 1 1 100%; height: 100%; }

        /* 趋势容器 */
        #trendContainer { display: flex; flex-wrap: wrap; align-content: flex-start; width: 100%; height: 100%; overflow: auto; padding: 4px; box-sizing: border-box; background: #f5f7fa; }
        #trendContainer > .trend-chart-item { background: #fff; border-radius: 4px; border: 1px solid #e8e8e8; box-shadow: 0 1px 2px rgba(0,0,0,0.05); box-sizing: border-box; padding: 2px; position: relative; }
        .trend-chart-container { width: 100%; height: 100%; }
    </style>
</head>
<body>

<div class="realtime-container">
    <div class="main-area">
        <!-- 二级标签 -->
        <div class="level2-sidebar" id="level2Sidebar">
            <div class="no-child-tip">选择一级</div>
        </div>

        <!-- 左侧主区域 -->
        <div class="left-main-area">
            <!-- 设备概览表格 -->
            <div class="device-overview-area">
                <div class="toolbar" id="overviewToolbar">
                    <button id="btnRefresh" class="mini-button" iconCls="note-refresh" onclick="refreshDeviceList()">刷新</button>
                    <span style="color:#ddd;margin:0 4px;">|</span>
                    <input id="deviceCombo" class="mini-combobox" style="width:140px;"
                           emptyText="-- 全部 --"
                           url="<%=path%>/wellInformationManagerController/loadWellComboxList"
                           dataField="list" totalField="totals"
                           valueField="boxkey" textField="boxval"
                           onvaluechanged="onDeviceComboChange" />
                    <span class="spacer"></span>
                    <button class="mini-button" iconCls="export" onclick="exportData()">导出</button>
                    <button class="mini-button" onclick="gotoHistory()">查看历史</button>
                </div>
                <div class="grid-wrapper">
                    <div id="deviceGrid" class="mini-datagrid" style="width:100%;height:100%;"
                         idField="id" pageSize="25" allowResize="true"
                         url="<%=path%>/realTimeMonitoringController/getDeviceRealTimeOverview"
                         dataField="totalRoot" totalField="totalCount"
                         ondrawcell="onDeviceGridDrawCell"
                         onselectionchanged="onDeviceGridSelectChanged"
                         onload="onDeviceGridLoad"
                         onbeforeload="onDeviceGridBeforeLoad">
                        <div property="columns">
                            <!-- 由 JavaScript 动态生成 -->
                        </div>
                    </div>
                </div>
            </div>

            <!-- 统计饼图 -->
            <div class="stat-charts-area">
                <div id="statTabs" class="mini-tabs" style="width:100%;height:100%;"
                     activeIndex="0" onactivechanged="onStatTabChanged">
                </div>
            </div>
        </div>

        <!-- 中间区域 -->
        <div class="middle-area">
            <div id="middleTabs" class="mini-tabs" style="width:100%;height:100%;"
                 activeIndex="0" onactivechanged="onMiddleTabChanged">
            </div>
        </div>

        <!-- 右侧区域 -->
        <div class="right-area">
            <div id="rightTabs" class="mini-tabs" style="width:100%;height:100%;"
                 activeIndex="0" onactivechanged="onRightTabChanged">
            </div>
        </div>
    </div>

    <!-- 底部一级标签（含资源监测） -->
    <div class="level1-footer" id="level1Footer">
        <!-- 标签和资源监测均由 JavaScript 动态生成 -->
    </div>
</div>

<script>
// ================================================================
// 0. 定义 context
// ================================================================
var context = '<%=path%>';
console.log('context:', context);

// ================================================================
// 1. 从父页面获取 tabInfo
// ================================================================
var tabInfo = null;
try {
    if (window.parent && window.parent.tabInfo) {
        tabInfo = window.parent.tabInfo;
    }
} catch(e) { console.warn('无法从父页面获取 tabInfo:', e); }

if (!tabInfo || !tabInfo.children) {
    tabInfo = {
        children: [{
            text: "举升类型", expanded: true, deviceTypeId: "2", parentId: "1",
            children: [
                { text: "抽油机", deviceTypeId: "3", parentId: "2", leaf: true },
                { text: "螺杆泵", deviceTypeId: "4", parentId: "2", leaf: true },
                { text: "电潜泵", deviceTypeId: "5", parentId: "2", leaf: true }
            ]
        }]
    };
}

// ================================================================
// 2. 状态变量
// ================================================================
var currentLevel1 = null;
var currentLevel2 = null;
var level1Data = [];
var level2Data = [];
var currentDeviceId = 0;
var currentMiddleTab = null;

var statTabs = null;
var middleTabs = null;
var rightTabs = null;
var deviceGrid = null;

// 统计 Tab 配置（使用 _loginUserLanguageResource）
var STAT_TAB_CONFIG = {
    'FESdiagramResult': {
        id: 'stat_FESdiagramResult',
        title: function() { return _loginUserLanguageResource.workType || '工况类型'; },
        api: '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData'
    },
    'CommStatus': {
        id: 'stat_CommStatus',
        title: function() { return _loginUserLanguageResource.commStatus || '通信状态'; },
        api: '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData'
    },
    'RunStatus': {
        id: 'stat_RunStatus',
        title: function() { return _loginUserLanguageResource.runStatus || '运行状态'; },
        api: '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData'
    },
    'NumStatus': {
        id: 'stat_NumStatus',
        title: function() { return _loginUserLanguageResource.numStatus || '数值状态'; },
        api: '/realTimeMonitoringController/getRealTimeMonitoringNumStatusStatData'
    }
};

// 中间 Tab 配置
var MIDDLE_TAB_CONFIG = [
    { id: 'middle_WellboreAnalysis', title: function() { return _loginUserLanguageResource.wellboreAnalysis || '井筒分析'; } },
    { id: 'middle_SurfaceAnalysis', title: function() { return _loginUserLanguageResource.surfaceAnalysis || '地面分析'; } },
    { id: 'middle_TrendCurve', title: function() { return _loginUserLanguageResource.trendCurve || '趋势曲线'; } },
    { id: 'middle_DynamicData', title: function() { return _loginUserLanguageResource.dynamicData || '动态数据'; } }
];

// 右侧 Tab 配置
var RIGHT_TAB_CONFIG = [
    { id: 'right_DeviceControl', title: _loginUserLanguageResource.deviceControl },
    { id: 'right_DeviceInfo', title: _loginUserLanguageResource.deviceInformation }
];

// 默认列配置
var DEFAULT_COLUMNS = [
    { type: 'indexcolumn', width: 40, headerAlign: 'center', header: '序号' },
    { field: 'deviceName', width: 140, headerAlign: 'center', header: '设备名称', locked: true },
    { field: 'commStatusName', width: 80, headerAlign: 'center', header: '通信状态' },
    { field: 'runStatusName', width: 80, headerAlign: 'center', header: '运行状态' },
    { field: 'acqTime', width: 150, headerAlign: 'center', header: '采集时间', dateFormat: 'yyyy-MM-dd HH:mm:ss' }
];

// ================================================================
// 3. 构建一级标签（底部）+ 资源监测（右侧）
// ================================================================
function buildLevel1Tabs() {
    var container = document.getElementById('level1Footer');
    if (!container) return;
    container.innerHTML = '';

    if (!tabInfo || !tabInfo.children || tabInfo.children.length === 0) {
        container.innerHTML = '<span class="loading-tip">暂无设备类型</span>';
        return;
    }

    level1Data = tabInfo.children;

    // 添加一级标签
    for (var i = 0; i < level1Data.length; i++) {
        var item = level1Data[i];
        var span = document.createElement('span');
        span.className = 'tab-item' + (i === 0 ? ' active' : '');
        span.dataset.index = i;
        span.dataset.deviceTypeId = item.deviceTypeId;
        span.textContent = item.text;
        span.onclick = function() {
            selectLevel1(parseInt(this.dataset.index));
        };
        container.appendChild(span);
    }

    // ★ 资源监测区域（右侧），与 ExtJS 设计完全一致
    var rightArea = document.createElement('div');
    rightArea.id = 'resourceMonitorArea';
    // 按钮 ID 与 ExtJS 一致，便于后续通过 ID 更新文本
    rightArea.innerHTML =
        '<button class="res-btn" onclick="openResourceChart(\'cpuUsedPercent\',\'CPU使用率(%)\')" id="CPUUsedPercentLabel_id">CPU</button>' +
        '<button class="res-btn" onclick="openResourceChart(\'memUsedPercent\',\'内存使用率(%)\')" id="memUsedPercentLabel_id">内存</button>' +
        '<button class="res-btn" onclick="openResourceChart(\'jedisStatus\',\'缓存数据库内存(m)\')" id="redisRunStatusProbeLabel_id">缓存</button>' +
        '<button class="res-btn" onclick="openResourceChart(\'tableSpaceSize\',\'表空间使用率(%)\')" id="tableSpaceSizeProbeLabel_id">表空间</button>' +
        '<button class="res-btn" onclick="openResourceChart(\'adRunStatus\',\'AD状态\')" id="adRunStatusProbeLabel_id">通信服务</button>' +
        '<button class="res-btn" onclick="openResourceChart(\'acRunStatus\',\'AC状态\')" id="acRunStatusProbeLabel_id">计算服务</button>' +
        '<span id="adLicenseStatusProbeLabel_id" style="color:#dc2828;font-size:12px;display:none;"></span>';
    container.appendChild(rightArea);

    if (level1Data.length > 0) selectLevel1(0);
}

function selectLevel1(index) {
    if (index < 0 || index >= level1Data.length) return;
    var item = level1Data[index];
    currentLevel1 = item;

    var container = document.getElementById('level1Footer');
    var tabs = container.querySelectorAll('.tab-item');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    buildLevel2Tabs(item);
    console.log('选择一级:', item.text);
}

// ================================================================
// 4. 构建二级标签（左侧竖排）
// ================================================================
function buildLevel2Tabs(parentItem) {
    var container = document.getElementById('level2Sidebar');
    if (!container) return;
    container.innerHTML = '';

    var children = parentItem.children || [];

    if (!children || children.length === 0) {
        container.innerHTML = '<div class="no-child-tip">无子类型</div>';
        currentLevel2 = null;
        return;
    }

    level2Data = children;

    var allIds = [];
    for (var i = 0; i < children.length; i++) {
        allIds.push(children[i].deviceTypeId);
    }
    var allTabs = [{ text: '全部', deviceTypeId: allIds.join(','), isAll: true }];
    for (var i = 0; i < children.length; i++) {
        allTabs.push(children[i]);
    }

    for (var i = 0; i < allTabs.length; i++) {
        var item = allTabs[i];
        var div = document.createElement('div');
        div.className = 'tab-item' + (i === 0 ? ' active' : '');
        div.dataset.index = i;
        div.dataset.deviceTypeId = item.deviceTypeId;
        div.dataset.isAll = item.isAll || false;
        div.textContent = item.text;
        div.title = item.text;
        div.onclick = function() {
            selectLevel2(parseInt(this.dataset.index));
        };
        container.appendChild(div);
    }

    if (allTabs.length > 0) {
        currentLevel2 = allTabs[0];
        loadAllData(currentLevel2);
    }
}

function selectLevel2(index) {
    var container = document.getElementById('level2Sidebar');
    var tabs = container.querySelectorAll('.tab-item');

    var allTabs = [{ text: '全部', deviceTypeId: '', isAll: true }];
    for (var i = 0; i < level2Data.length; i++) {
        allTabs.push(level2Data[i]);
    }
    var allIds = [];
    for (var i = 0; i < level2Data.length; i++) {
        allIds.push(level2Data[i].deviceTypeId);
    }
    allTabs[0].deviceTypeId = allIds.join(',');

    if (index < 0 || index >= allTabs.length) return;

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    currentLevel2 = allTabs[index];
    loadAllData(currentLevel2);
    console.log('选择二级:', currentLevel2.text, 'deviceTypeId:', currentLevel2.deviceTypeId);
}

// ================================================================
// 5. 加载所有数据
// ================================================================
function loadAllData(level2Item) {
    if (!level2Item) return;
    var deviceTypeId = level2Item.deviceTypeId || '0';
    var orgId = window.parent && window.parent.mini ?
                 window.parent.mini.get('leftOrg_Id').getValue() : '';
    refreshDeviceList();
    loadStatCharts(deviceTypeId, orgId);
    initMiddleTabs();
    initRightTabs();
}

// ================================================================
// 6. 设备表格
// ================================================================
function buildGridColumns(columnsData) {
    if (!columnsData || columnsData.length === 0) {
        return DEFAULT_COLUMNS;
    }

    var columns = [];
    for (var i = 0; i < columnsData.length; i++) {
        var col = columnsData[i];
        var column = {
            field: col.dataIndex,
            header: col.header,
            headerAlign: 'center',
            align: 'center',
            width: col.width || 100
        };

        if (col.dataIndex === 'id') {
            column.type = 'indexcolumn';
            column.width = col.width || 40;
            column.header = '序号';
            delete column.field;
        }
        else if (col.dataIndex === 'deviceName') {
            column.width = col.width || 140;
            column.locked = true;
        }
        else if (col.dataIndex === 'commStatusName') {
            column.width = col.width || 80;
        }
        else if (col.dataIndex === 'runStatusName' || col.dataIndex === 'RunStatusName') {
            column.width = col.width || 80;
        }
        else if (col.dataIndex === 'acqTime') {
            column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
            column.width = col.width || 150;
        }

        columns.push(column);
    }
    return columns;
}

// ================================================================
// 报警样式与徽章工具函数
// ================================================================
function getAlarmShowStyle() {
    var val = null;
    try {
        var input = mini.get('AlarmShowStyle_Id');
        if (input) val = input.getValue();
    } catch (e) {}
    if (!isNotVal(val) && window.parent && window.parent.mini) {
        try {
            var parentInput = window.parent.mini.get('AlarmShowStyle_Id');
            if (parentInput) val = parentInput.getValue();
        } catch (e) {}
    }
    if (isNotVal(val) && typeof val === 'string') {
        try { return JSON.parse(val); } catch(e) { return {}; }
    }
    return {};
}

function getAlarmStyleByLevel(level, styleConfig) {
    var config = styleConfig || getAlarmShowStyle();
    var cfg = (config && config.Data) || {};
    var levelMap = {
        100: cfg.FirstLevel || {},
        200: cfg.SecondLevel || {},
        300: cfg.ThirdLevel || {}
    };
    var lvl = levelMap[level] || {};
    var bg = lvl.BackgroundColor ? '#' + lvl.BackgroundColor : 'transparent';
    var color = lvl.Color ? '#' + lvl.Color : '#000';
    var opacity = (lvl.Opacity !== undefined) ? lvl.Opacity : 1;
    var bgRgba = (opacity === 0) ? 'transparent' : color16ToRgba(bg, opacity);
    return { bg: bgRgba, color: color };
}

function createAlarmBadge(count, color, textColor, height) {
    if (!count || count <= 0) return '';
    var bgColor = (color && color.charAt(0) === '#') ? color : '#' + color;
    var fgColor = textColor || '#ffffff';
    var h = height || 14;
    var fontSize = Math.max(9, Math.floor(h * 0.65));
    var numStr = String(count);
    var minWidth = h;
    var padding = 4;
    var extraWidth = (numStr.length - 1) * 6;
    var width = minWidth + extraWidth + padding;
    return '<span style="display:inline-block;' +
           'background-color:' + bgColor + ';' +
           'color:' + fgColor + ';' +
           'border-radius:' + (h / 2) + 'px;' +
           'padding:0 ' + padding + 'px;' +
           'min-width:' + minWidth + 'px;' +
           'height:' + h + 'px;' +
           'line-height:' + h + 'px;' +
           'text-align:center;' +
           'font-size:' + fontSize + 'px;' +
           'font-weight:bold;' +
           'margin-right:2px;' +
           'vertical-align:middle;' +
           'box-sizing:border-box;' +
           'white-space:nowrap;">' + count + '</span>';
}

// ===== 设备表格加载前 =====
function onDeviceGridBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize = params.pageSize || 25;
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var leftOrgId = window.parent && window.parent.mini ?
                     window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
    params.deviceType = deviceType;
    var deviceCombo = mini.get('deviceCombo');
    params.deviceName = deviceCombo ? deviceCombo.getValue() : '';
    console.log('加载设备列表参数:', params);
}

// ===== 设备表格加载完成 =====
function onDeviceGridLoad(e) {
    var grid = e.sender;
    var result = e.result;
    console.log('完整响应数据:', result);

    if (result) {
        if (result.AlarmShowStyle && window.parent && window.parent.mini) {
            var alarmInput = window.parent.mini.get('AlarmShowStyle_Id');
            if (alarmInput) {
                alarmInput.setValue(JSON.stringify(result.AlarmShowStyle));
            }
        }
        if (result.columns && result.columns.length > 0) {
            var columns = buildGridColumns(result.columns);
            console.log('生成的列数量:', columns.length);
            grid.setColumns([]);
            setTimeout(function() {
                grid.setColumns(columns);
                grid.doLayout();
                grid.refresh();
            }, 50);
        }
        var data = grid.getData();
        if (data && data.length > 0) {
            grid.select(0);
        }
    }
    console.log('设备数据加载完成，总数:', result ? result.totalCount : 0);
}

// ===== 设备表格选中变化 =====
function onDeviceGridSelectChanged(e) {
    var grid = e.sender;
    var selected = grid.getSelected();
    if (selected) {
        currentDeviceId = selected.id;
        console.log('选中设备:', selected.deviceName, 'ID:', selected.id);
        refreshMiddleTabs();
        refreshRightTabs();
    }
}

// ===== 设备表格绘制单元格 =====
function onDeviceGridDrawCell(e) {
    var record = e.record;
    var field = e.field;
    var value = e.value;
    if (!record || !field) return;

    var alarmShowStyle = getAlarmShowStyle() || {};
    var Data = (alarmShowStyle.Data) || {};
    var Comm = (alarmShowStyle.Comm) || {};
    var Run = (alarmShowStyle.Run) || {};
    var alarmInfo = record.alarmInfo || [];
    var fieldUpper = field.toUpperCase();

    if (fieldUpper === 'DEVICENAME') {
        var badges = '';
        var counts = {100:0, 200:0, 300:0};
        for (var i = 0; i < alarmInfo.length; i++) {
            var level = alarmInfo[i].alarmLevel;
            if (level === 100 || level === 200 || level === 300) {
                counts[level] = (counts[level] || 0) + 1;
            }
        }
        var firstColor = (Data.FirstLevel && Data.FirstLevel.Color) || 'dc2828';
        var secondColor = (Data.SecondLevel && Data.SecondLevel.Color) || 'f09614';
        var thirdColor = (Data.ThirdLevel && Data.ThirdLevel.Color) || 'fae600';
        if (counts[100] > 0) badges += createAlarmBadge(counts[100], firstColor);
        if (counts[200] > 0) badges += createAlarmBadge(counts[200], secondColor);
        if (counts[300] > 0) badges += createAlarmBadge(counts[300], thirdColor);
        e.cellHtml = badges + (value || '');
        return;
    }

    if (fieldUpper === 'COMMSTATUSNAME') {
        var status = record.commStatus;
        var offlineColor = (Comm.offline && Comm.offline.Color) ? '#' + Comm.offline.Color : '#ff4d4f';
        var onlineColor = (Comm.online && Comm.online.Color) ? '#' + Comm.online.Color : '#52c41a';
        var goOnlineColor = (Comm.goOnline && Comm.goOnline.Color) ? '#' + Comm.goOnline.Color : '#faad14';
        var color = '#999';
        if (status === 0) color = offlineColor;
        else if (status === 1) color = onlineColor;
        else if (status === 2) color = goOnlineColor;
        e.cellHtml = '<span style="color:' + color + ';font-weight:bold;">' + (value || '') + '</span>';
        return;
    }

    if (fieldUpper === 'RUNSTATUSNAME' || fieldUpper === 'RUNSTATUSNAME') {
        var commStatus = record.commStatus;
        var runStatus = record.runStatus;
        if (commStatus == 0 || commStatus == 2 || !value) {
            e.cellHtml = '';
            return;
        } else {
            var stopColor = (Run.stop && Run.stop.Color) ? '#' + Run.stop.Color : '#ff4d4f';
            var runColor = (Run.run && Run.run.Color) ? '#' + Run.run.Color : '#52c41a';
            var noDataColor = (Run.noData && Run.noData.Color) ? '#' + Run.noData.Color : '#999';
            var runColorSelected = (runStatus === 0) ? stopColor : (runStatus === 1 ? runColor : noDataColor);
            e.cellHtml = '<span style="color:' + runColorSelected + ';font-weight:bold;">' + (value || '') + '</span>';
            return;
        }
    }

    if (fieldUpper !== 'ID' && fieldUpper !== 'DEVICENAME' && fieldUpper !== 'COMMSTATUSNAME' && fieldUpper !== 'RUNSTATUSNAME') {
        var alarmLevel = 0;
        for (var j = 0; j < alarmInfo.length; j++) {
            var item = alarmInfo[j].item;
            if (item && item.toUpperCase() === fieldUpper) {
                alarmLevel = alarmInfo[j].alarmLevel || 0;
                break;
            }
        }
        if (alarmLevel > 0) {
            var style = getAlarmStyleByLevel(alarmLevel, alarmShowStyle);
            if (style && style.bg) {
                e.cellStyle = 'background-color:' + style.bg + ';color:' + style.color + ';';
            }
        }
    }
}

// ===== 刷新设备列表 =====
function refreshDeviceList() {
    var grid = mini.get('deviceGrid');
    if (grid) grid.load();
}

function onDeviceComboChange() {
    refreshDeviceList();
}

function exportData() {
    mini.alert('导出功能开发中...');
}

function gotoHistory() {
    if (window.parent) {
        window.parent.postMessage({ action: 'switchModule', moduleId: 'DeviceHistoryQuery' }, '*');
    }
}

// ================================================================
// 7. 统计饼图
// ================================================================
function loadStatCharts(deviceTypeId, orgId) {
    var defaultConfig = {
        FESdiagramResult: true,
        CommStatus: true,
        RunStatus: true,
        NumStatus: true
    };
    buildStatTabs(defaultConfig, deviceTypeId, orgId);
}

function buildStatTabs(config, deviceTypeId, orgId) {
    statTabs = mini.get('statTabs');
    if (!statTabs) return;

    var paramDeviceType = deviceTypeId || '0';
    if (deviceTypeId && deviceTypeId.indexOf(',') > -1) {
        paramDeviceType = deviceTypeId;
    }

    var tabKeys = [];
    var order = ['FESdiagramResult', 'CommStatus', 'RunStatus', 'NumStatus'];
    for (var i = 0; i < order.length; i++) {
        if (config[order[i]]) tabKeys.push(order[i]);
    }

    if (tabKeys.length === 0) {
        statTabs.setTabs([{
            name: 'stat_placeholder',
            title: '暂无统计图',
            body: '<div class="loading-placeholder">无统计图表</div>'
        }]);
        return;
    }

    var tabs = [];
    for (var i = 0; i < tabKeys.length; i++) {
        var key = tabKeys[i];
        var cfg = STAT_TAB_CONFIG[key];
        if (cfg) {
            var divId = 'pieChart_' + key + '_' + Date.now() + '_' + i;
            tabs.push({
                name: cfg.id,
                title: typeof cfg.title === 'function' ? cfg.title() : cfg.title,
                _key: key,
                _api: cfg.api,
                _divId: divId,
                body: '<div id="' + divId + '" class="pie-chart-container"></div>'
            });
        }
    }

    statTabs.setTabs(tabs);
    if (tabs.length > 0) {
        statTabs.activeTab(tabs[0]);
        loadStatData(tabs[0], paramDeviceType, orgId);
    }
}

function onStatTabChanged(e) {
    var tab = e.tab;
    if (!tab) return;
    var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
    var orgId = window.parent && window.parent.mini ?
                 window.parent.mini.get('leftOrg_Id').getValue() : '';
    loadStatData(tab, deviceTypeId, orgId);
}

function loadStatData(tab, deviceTypeId, orgId) {
    if (!tab || !tab._api) return;
    var divId = tab._divId;
    var container = document.getElementById(divId);
    if (container) {
        container.innerHTML = '<div class="loading-placeholder">加载中...</div>';
    }

    $.ajax({
        url: context + tab._api,
        type: 'POST',
        data: { orgId: orgId || '', deviceType: deviceTypeId || '0' },
        dataType: 'text',
        timeout: 10000,
        success: function(responseText) {
            try {
                var fixedJson = responseText
                    .replace(/(\{|\,)\s*([a-zA-Z_][a-zA-Z0-9_]*)\s*(\:)/g, '$1"$2"$3')
                    .replace(/'([^']*)'/g, '"$1"');
                var result = JSON.parse(fixedJson);
                var data = extractPieData(result);
                renderPieChart(divId, data, tab.title);
            } catch(e) {
                console.error('JSON解析失败:', e);
                if (container) {
                    container.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">数据格式错误</div>';
                }
            }
        },
        error: function() {
            if (container) {
                container.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">加载失败</div>';
            }
        }
    });
}

function extractPieData(result) {
    if (!result) return [{ name: '暂无数据', y: 1 }];
    var list = result.totalRoot || [];
    var data = [];
    for (var i = 0; i < list.length; i++) {
        if (list[i].itemCode !== 'all' && list[i].count > 0) {
            data.push({ name: list[i].item || list[i].text || '未知', y: list[i].count || 0 });
        }
    }
    return data.length > 0 ? data : [{ name: '暂无数据', y: 1 }];
}

function renderPieChart(divId, data, title) {
    var container = document.getElementById(divId);
    if (!container) return;
    if (container._chart) {
        container._chart.destroy();
        container._chart = null;
    }
    if (data.length === 1 && data[0].name === '暂无数据') {
        container.innerHTML = '<div class="loading-placeholder">暂无数据</div>';
        return;
    }

    var chart = Highcharts.chart(divId, {
        chart: { type: 'pie', plotBackgroundColor: null, plotBorderWidth: null, plotShadow: false,
            zooming: { mouseWheel: { enabled: false } } },
        credits: { enabled: false },
        title: { text: title || '', style: { fontSize: '13px' } },
        tooltip: { pointFormat: '数量: <b>{point.y}</b><br/>占比: <b>{point.percentage:.1f}%</b>' },
        legend: { align: 'center', verticalAlign: 'bottom', layout: 'horizontal',
            itemHiddenStyle: { textDecoration: 'none' } },
        plotOptions: {
            pie: {
                allowPointSelect: true, cursor: 'pointer',
                dataLabels: { enabled: true, color: '#000000', connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.y}' },
                showInLegend: true
            }
        },
        exporting: { enabled: true, filename: title || '统计图', fallbackToExportServer: false },
        series: [{ type: 'pie', name: '数量', data: data }]
    });
    container._chart = chart;
}

// ================================================================
// 8. 中间区域
// ================================================================
function initMiddleTabs() {
    middleTabs = mini.get('middleTabs');
    if (!middleTabs) return;
    var tabs = [];
    for (var i = 0; i < MIDDLE_TAB_CONFIG.length; i++) {
        var cfg = MIDDLE_TAB_CONFIG[i];
        tabs.push({
            name: cfg.id,
            title: typeof cfg.title === 'function' ? cfg.title() : cfg.title,
            body: '<div class="loading-placeholder">请选择设备</div>'
        });
    }
    middleTabs.setTabs(tabs);
    if (tabs.length > 0) {
        currentMiddleTab = tabs[0].name;
    }
}

function onMiddleTabChanged(e) {
    var tab = e.tab;
    if (!tab) return;
    currentMiddleTab = tab.name;
    console.log('中间Tab切换:', currentMiddleTab);
    if (currentDeviceId > 0) {
        refreshMiddleTabs();
    } else {
        var body = middleTabs.getTabBodyEl(currentMiddleTab);
        if (body) {
            body.innerHTML = '<div class="loading-placeholder">请选择设备</div>';
        }
    }
}

function refreshMiddleTabs() {
    if (!currentMiddleTab || currentDeviceId <= 0) return;
    var tabName = currentMiddleTab;
    switch (tabName) {
        case 'middle_WellboreAnalysis':
            loadWellboreAnalysis(currentDeviceId);
            break;
        case 'middle_SurfaceAnalysis':
            loadSurfaceAnalysis(currentDeviceId);
            break;
        case 'middle_TrendCurve':
            loadTrendCurve(currentDeviceId);
            break;
        case 'middle_DynamicData':
            loadDynamicData(currentDeviceId);
            break;
        default:
            break;
    }
}

// ---------- 井筒分析 ----------
function loadWellboreAnalysis(deviceId) {
    var tabBody = middleTabs.getTabBodyEl('middle_WellboreAnalysis');
    if (!tabBody) return;
    tabBody.innerHTML = '<div class="chart-grid" id="wellboreGrid">' +
        '<div class="chart-item"><div id="wellboreChart1" class="chart-container"></div></div>' +
        '<div class="chart-item"><div id="wellboreChart2" class="chart-container"></div></div>' +
        '<div class="chart-item"><div id="wellboreChart3" class="chart-container"></div></div>' +
        '<div class="chart-item"><div id="wellboreChart4" class="chart-container"></div></div>' +
        '</div>';

    $.ajax({
        url: context + '/realTimeMonitoringController/querySingleFESDiagramDetailsChartsData',
        type: 'POST',
        data: { id: deviceId, type: 1 },
        dataType: 'json',
        timeout: 15000,
        success: function(result) {
            if (result && result.positionCurveData && result.loadCurveData) {
                showFSDiagramFromPumpcard(result, 'wellboreChart1');
                showRodPress(result, 'wellboreChart2');
                showPumpCard(result, 'wellboreChart3');
                showPumpEfficiency(result, 'wellboreChart4');
            } else {
                var charts = ['wellboreChart1', 'wellboreChart2', 'wellboreChart3', 'wellboreChart4'];
                charts.forEach(function(id) {
                    var el = document.getElementById(id);
                    if (el) el.innerHTML = '<div class="loading-placeholder">无数据</div>';
                });
            }
        },
        error: function(xhr, status, errorThrown) {
            console.log('请求失败:', status, errorThrown);
            var charts = ['wellboreChart1','wellboreChart2','wellboreChart3','wellboreChart4'];
            charts.forEach(function(id) {
                var el = document.getElementById(id);
                if (el) el.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">加载失败: ' + status + '</div>';
            });
        }
    });
}

// ---------- 地面分析 ----------
function loadSurfaceAnalysis(deviceId) {
    var tabBody = middleTabs.getTabBodyEl('middle_SurfaceAnalysis');
    if (!tabBody) return;
    tabBody.innerHTML = '<div class="chart-grid" id="surfaceGrid">' +
        '<div class="chart-item"><div id="surfaceChart1" class="chart-container"></div></div>' +
        '<div class="chart-item"><div id="surfaceChart2" class="chart-container"></div></div>' +
        '<div class="chart-item"><div id="surfaceChart3" class="chart-container"></div></div>' +
        '<div class="chart-item"><div id="surfaceChart4" class="chart-container"></div></div>' +
        '</div>';

    $.ajax({
        url: context + '/realTimeMonitoringController/querySingleFESDiagramDetailsChartsData',
        type: 'POST',
        data: { id: deviceId, type: 2 },
        dataType: 'json',
        timeout: 15000,
        success: function(result) {
            if (result && result.positionCurveData && result.powerCurveData) {
                showPSDiagram(result, 'surfaceChart1');
                if (result.crankAngle && result.loadRorque && result.crankTorque && result.currentBalanceTorque && result.currentNetTorque) {
                    showBalanceAnalysisCurveChart(
                        result.crankAngle,
                        result.loadRorque,
                        result.crankTorque,
                        result.currentBalanceTorque,
                        result.currentNetTorque,
                        _loginUserLanguageResource.currentTorqueCurve || '当前扭矩曲线',
                        result.deviceName || '',
                        result.acqTime || '',
                        'surfaceChart2'
                    );
                } else {
                    document.getElementById('surfaceChart2').innerHTML = '<div class="loading-placeholder">无扭矩数据</div>';
                }
                showASDiagram(result, 'surfaceChart3');
                if (result.crankAngle && result.loadRorque && result.crankTorque && result.expectedBalanceTorque && result.expectedNetTorque) {
                    var deltaRadius = parseFloat(result.deltaRadius) || 0;
                    var expectedTitle = _loginUserLanguageResource.expectTorqueCurve || '预期扭矩曲线';
                    if (deltaRadius !== 0) {
                        expectedTitle = (deltaRadius > 0 ? '外移' : '内移') + Math.abs(deltaRadius) + 'cm ' + expectedTitle;
                    }
                    showBalanceAnalysisCurveChart(
                        result.crankAngle,
                        result.loadRorque,
                        result.crankTorque,
                        result.expectedBalanceTorque,
                        result.expectedNetTorque,
                        expectedTitle,
                        result.deviceName || '',
                        result.acqTime || '',
                        'surfaceChart4'
                    );
                } else {
                    document.getElementById('surfaceChart4').innerHTML = '<div class="loading-placeholder">无预期扭矩数据</div>';
                }
            } else {
                ['surfaceChart1','surfaceChart2','surfaceChart3','surfaceChart4'].forEach(function(id) {
                    var el = document.getElementById(id);
                    if (el) el.innerHTML = '<div class="loading-placeholder">无数据</div>';
                });
            }
        },
        error: function() {
            ['surfaceChart1','surfaceChart2','surfaceChart3','surfaceChart4'].forEach(function(id) {
                var el = document.getElementById(id);
                if (el) el.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">加载失败</div>';
            });
        }
    });
}

// ================================================================
// ★★ 趋势曲线 - 动态布局（1条100%x100%，2条100%x50%，>2条50%x50%）★★
// ================================================================
function loadTrendCurve(deviceId) {
    var tabBody = middleTabs.getTabBodyEl('middle_TrendCurve');
    if (!tabBody) return;

    // 显示加载占位
    tabBody.innerHTML = '<div class="loading-placeholder"><span class="icon">⏳</span>加载趋势数据...</div>';

    // 获取选中的设备信息
    var grid = mini.get('deviceGrid');
    var deviceName = '';
    var calculateType = 0;
    if (grid) {
        var selected = grid.getSelected();
        if (selected) {
            deviceName = selected.deviceName || '';
            calculateType = selected.calculateType || 0;
            if (!deviceId || deviceId === 0) {
                deviceId = selected.id || 0;
            }
        }
    }
    var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

    $.ajax({
        url: context + '/realTimeMonitoringController/getRealTimeMonitoringCurveData',
        type: 'POST',
        data: { 
            deviceId: deviceId, 
            deviceType: deviceType, 
            calculateType: calculateType,
            deviceName: deviceName
        },
        dataType: 'json',
        timeout: 15000,
        success: function(result) {
            tabBody.innerHTML = '';

            if (!result || !result.list || result.list.length === 0) {
                tabBody.innerHTML = '<div class="loading-placeholder">暂无趋势数据</div>';
                return;
            }

            var data = result.list;
            var curveNames = result.curveItems || [];
            var deviceNameResult = result.deviceName || deviceName || '设备';
            var curveCount = data.length > 0 ? data[0].data.length : 0;

            if (curveCount === 0) {
                tabBody.innerHTML = '<div class="loading-placeholder">无曲线数据</div>';
                return;
            }

            // 补齐曲线名称
            if (curveNames.length < curveCount) {
                for (var i = curveNames.length; i < curveCount; i++) {
                    curveNames.push('曲线' + (i + 1));
                }
            }

            // 获取曲线配置（颜色、线宽、线型、yAxis 位置等）
            var curveConf = result.curveConf || [];
            var defaultColors = ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#2b908f', '#f45b5b', '#91e8e1'];
            var colors = [];
            for (var i = 0; i < curveConf.length; i++) {
                colors.push(curveConf[i].color ? ('#' + curveConf[i].color) : defaultColors[i % 10]);
            }

            // ★★ 计算每个图表的宽高百分比（与 ExtJS 逻辑一致） ★★
            var chartWidth, chartHeight;
            if (curveCount == 1) {
                chartWidth = '100%';
                chartHeight = '100%';
            } else if (curveCount == 2) {
                chartWidth = '100%';
                chartHeight = '50%';
            } else {
                chartWidth = '50%';
                chartHeight = '50%';
            }
            var minChartHeight = 200; // 最小高度（像素）

            // 创建 flex wrap 容器
            var container = document.createElement('div');
            container.id = 'trendContainer';
            tabBody.appendChild(container);

            // 遍历曲线，每个曲线一个图表
            for (var i = 0; i < curveCount; i++) {
                var divId = 'trendChart_' + i + '_' + Date.now();
                var chartDiv = document.createElement('div');
                chartDiv.className = 'trend-chart-item';
                // 动态设置宽高和最小高度
                chartDiv.style.width = chartWidth;
                chartDiv.style.height = chartHeight;
                chartDiv.style.minHeight = minChartHeight + 'px';
                chartDiv.style.boxSizing = 'border-box';
                chartDiv.style.padding = '2px';
                container.appendChild(chartDiv);

                // 内部容器（Highcharts 渲染目标）
                var innerDiv = document.createElement('div');
                innerDiv.className = 'trend-chart-container';
                innerDiv.id = divId + '_inner';
                innerDiv.style.width = '100%';
                innerDiv.style.height = '100%';
                chartDiv.appendChild(innerDiv);

                // 构建该曲线的 series 数据
                var seriesData = [];
                for (var j = 0; j < data.length; j++) {
                    var timestamp = Date.parse(data[j].acqTime.replace(/-/g, '/'));
                    var val = parseFloat(data[j].data[i]);
                    if (!isNaN(val)) {
                        seriesData.push([timestamp, val]);
                    }
                }

                if (seriesData.length === 0) {
                    innerDiv.innerHTML = '<div class="loading-placeholder">无有效数据</div>';
                    continue;
                }

                var yTitle = curveNames[i] || ('曲线' + (i + 1));
                var titleText = deviceNameResult + ':' + yTitle + ' 趋势';
                var conf = (curveConf && curveConf.length > i) ? curveConf[i] : {};
                var color = (colors && colors.length > i) ? colors[i] : defaultColors[i % 10];
                var lineWidth = conf.lineWidth || 2;
                var dashStyle = conf.dashStyle || 'Solid';
                var yAxisOpposite = conf.yAxisOpposite || false;

                var series = [{
                    name: yTitle,
                    data: seriesData,
                    lineWidth: lineWidth,
                    dashStyle: dashStyle,
                    marker: { enabled: true, radius: 2 }
                }];

                // 计算 yAxis 范围
                var allPositive = true, allNegative = true;
                for (var k = 0; k < seriesData.length; k++) {
                    var val = seriesData[k][1];
                    if (val < 0) allPositive = false;
                    if (val >= 0) allNegative = false;
                }
                var maxValue = allNegative ? 0 : null;
                var minValue = allPositive ? 0 : null;

                // 调用绘图函数
                initDeviceRealtimeMonitoringStockChartFn(
                    series,
                    undefined,
                    innerDiv.id,
                    titleText,
                    '',
                    _loginUserLanguageResource.time,
                    yTitle,
                    [color],
                    false,
                    true,
                    false,
                    undefined,
                    maxValue,
                    minValue,
                    yAxisOpposite
                );
            }

            // 延迟触发重绘
            setTimeout(function() {
                $(window).resize();
            }, 100);
        },
        error: function(xhr, status, errorThrown) {
            console.error('趋势数据加载失败:', status, errorThrown);
            tabBody.innerHTML = '<div class="loading-placeholder error"><span class="icon">❌</span>趋势数据加载失败</div>';
        }
    });
}

// ================================================================
// Highstock 绘图函数（修复变量引用）
// ================================================================
function initDeviceRealtimeMonitoringStockChartFn(series, tickInterval, divId, title, subtitle, xtitle, yTitle, color, legend, navigator, scrollbar, timeFormat, maxValue, minValue, yAxisOpposite) {
    if ($("#" + divId) != undefined && $("#" + divId)[0] != undefined) {
        var lang = _loginUserLanguageResource || {};
        var hourLabel = lang.hour || '时';
        var allLabel = lang.all || '全部';
        var fontSize = chartTitleFontSize || '14px';

        var chart = new Highcharts.stockChart({
            chart: {
                renderTo: divId,
                type: 'spline',
                shadow: false,
                borderWidth: 0,
                zooming: { mouseWheel: { enabled: false } },
                zoomType: 'xy'
            },
            time: {
                timezoneOffset: new Date().getTimezoneOffset()
            },
            credits: { enabled: false },
            navigator: { enabled: navigator !== false },
            scrollbar: { enabled: scrollbar === true },
            rangeSelector: {
                buttons: [
                    { count: 1, type: 'hour', text: '1' + hourLabel },
                    { count: 6, type: 'hour', text: '6' + hourLabel },
                    { count: 12, type: 'hour', text: '12' + hourLabel },
                    { count: 24, type: 'hour', text: '24' + hourLabel },
                    { type: 'all', text: allLabel }
                ],
                buttonTheme: {
                    width: getLabelWidth('24' + hourLabel)  // 只需要字符串参数
                },
                dropdown: 'responsive',
                inputEnabled: false,
                selected: 0
            },
            title: {
                text: title,
                style: { fontSize: fontSize }
            },
            subtitle: { text: subtitle },
            colors: color,
            xAxis: {
                type: 'datetime',
                title: { text: xtitle },
                tickPixelInterval: 120,
                minTickInterval: 5 * 60 * 1000,
                labels: {
                    formatter: function () {
                        var minTime = this.axis.min;
                        var maxTime = this.axis.max;
                        var minDate = new Date(minTime);
                        var maxDate = new Date(maxTime);
                        minDate.setHours(0, 0, 0, 0);
                        maxDate.setHours(0, 0, 0, 0);
                        var isCrossDay = minDate.getTime() !== maxDate.getTime();
                        if (isCrossDay) {
                            return this.axis.chart.time.dateFormat('%m-%d %H:%M', this.value);
                        } else {
                            return this.axis.chart.time.dateFormat('%H:%M', this.value);
                        }
                    },
                    autoRotation: true,
                    rotation: -45
                }
            },
            yAxis: {
                max: maxValue || null,
                min: minValue || null,
                lineWidth: 1,
                tickWidth: 1,
                tickLength: 5,
                title: { text: yTitle },
                opposite: yAxisOpposite || false
            },
            tooltip: {
                crosshairs: true,
                shared: true,
                style: { color: '#333333', fontSize: '12px', padding: '8px' },
                dateTimeLabelFormats: {
                    millisecond: '%Y-%m-%d %H:%M:%S.%L',
                    second: '%Y-%m-%d %H:%M:%S',
                    minute: '%Y-%m-%d %H:%M',
                    hour: '%Y-%m-%d %H',
                    day: '%Y-%m-%d',
                    week: '%m-%d',
                    month: '%Y-%m',
                    year: '%Y'
                }
            },
            exporting: {
                enabled: true,
                filename: title,
                fallbackToExportServer: false,
                sourceWidth: $("#" + divId)[0] ? $("#" + divId)[0].offsetWidth : null,
                sourceHeight: $("#" + divId)[0] ? $("#" + divId)[0].offsetHeight : null,
                buttons: {
                    contextButton: {
                        menuItems: [
                            'viewFullscreen',
                            'printChart',
                            'separator',
                            'downloadPNG',
                            'downloadJPEG',
                            'downloadSVG',
                            'separator',
                            'downloadCSV',
                            'downloadXLS'
                        ]
                    }
                }
            },
            plotOptions: {
                spline: {
                    lineWidth: 1,
                    fillOpacity: 0.3,
                    marker: {
                        enabled: true,
                        radius: 3,
                        states: { hover: { enabled: true, radius: 6 } }
                    },
                    shadow: true
                }
            },
            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'bottom',
                enabled: legend || false,
                borderWidth: 0,
                itemHiddenStyle: { textDecoration: 'none' }
            },
            series: series
        });
    }
}

// ---------- 动态数据 ----------
function loadDynamicData(deviceId) {
    var tabBody = middleTabs.getTabBodyEl('middle_DynamicData');
    if (!tabBody) return;

    // 清空并创建容器
    tabBody.innerHTML = '<div id="dynamicDataContainer" style="width:100%;height:100%;padding:5px;box-sizing:border-box;"></div>';

    // 获取设备信息
    var grid = mini.get('deviceGrid');
    var deviceName = '';
    var calculateType = 0;
    if (grid) {
        var selected = grid.getSelected();
        if (selected) {
            deviceName = selected.deviceName || '';
            calculateType = selected.calculateType || 0;
            if (!deviceId || deviceId === 0) {
                deviceId = selected.id || 0;
            }
        }
    }
    var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

    // 显示加载遮罩（简单文字）
    var container = document.getElementById('dynamicDataContainer');
    container.innerHTML = '<div class="loading-placeholder">加载数据...</div>';

    // 发起请求
    $.ajax({
        url: context + '/realTimeMonitoringController/getDeviceRealTimeMonitoringData',
        type: 'POST',
        data: {
            deviceId: deviceId,
            deviceName: deviceName,
            deviceType: deviceType,
            calculateType: calculateType
        },
        dataType: 'json',
        timeout: 15000,
        success: function(result) {
            // 清空容器
            container.innerHTML = '';

            if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">无数据</div>';
                return;
            }

            // 创建 Handsontable
            createDynamicDataTable(result, container);
        },
        error: function(xhr, status, errorThrown) {
            console.error('动态数据加载失败:', status, errorThrown);
            container.innerHTML = '<div class="loading-placeholder error"><span class="icon">❌</span>数据加载失败</div>';
        }
    });
}

// Handsontable 相关（动态数据）
var deviceRealTimeMonitoringDataHandsontableHelper = null;

function createDynamicDataTable(result, container) {
    if (deviceRealTimeMonitoringDataHandsontableHelper) {
        if (deviceRealTimeMonitoringDataHandsontableHelper.hot) {
            deviceRealTimeMonitoringDataHandsontableHelper.hot.destroy();
        }
        deviceRealTimeMonitoringDataHandsontableHelper = null;
    }

    deviceRealTimeMonitoringDataHandsontableHelper = DeviceRealTimeMonitoringDataHandsontableHelper.createNew(
        'dynamicDataContainer',  // 容器 ID
        result
    );
}

var DeviceRealTimeMonitoringDataHandsontableHelper = {
    createNew: function (divid, result) {
        var helper = {};
        helper.divid = divid;
        helper.validresult = true;
        helper.colHeaders = [];
        helper.columns = [];
        helper.CellInfo = result.CellInfo || [];
        helper.sourceData = result.totalRoot || [];

        var colHeaders = ['变量', '值', '变量', '值', '变量', '值'];
        var columns = [
            { data: 'name1' },
            { data: 'value1' },
            { data: 'name2' },
            { data: 'value2' },
            { data: 'name3' },
            { data: 'value3' }
        ];
        helper.colHeaders = colHeaders;
        helper.columns = columns;

        function getAlarmShowStyle() {
            var val = null;
            try {
                var input = mini.get('AlarmShowStyle_Id');
                if (input) val = input.getValue();
            } catch (e) {}
            if (!val && window.parent && window.parent.mini) {
                try {
                    var parentInput = window.parent.mini.get('AlarmShowStyle_Id');
                    if (parentInput) val = parentInput.getValue();
                } catch (e) {}
            }
            if (val && typeof val === 'string') {
                try { return JSON.parse(val); } catch(e) { return {}; }
            }
            return {};
        }

        helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            var AlarmShowStyle = getAlarmShowStyle();
            var Data = AlarmShowStyle.Data || {};

            if (row === 0) {
                td.style.fontSize = '20px';
                td.style.height = '40px';
                td.style.fontWeight = 'bold';
            }
            if (row % 2 === 1 && row > 0) {
                td.style.backgroundColor = '#f5f5f5';
            }
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';

            var cellInfo = helper.CellInfo || [];
            for (var i = 0; i < cellInfo.length; i++) {
                var info = cellInfo[i];
                var row2 = info.row;
                var col2 = info.col * 2;
                if (row === row2 && col === col2) {
                    if (info.realtimeColor) {
                        td.style.color = '#' + info.realtimeColor;
                    }
                    if (info.realtimeBgColor) {
                        td.style.backgroundColor = '#' + info.realtimeBgColor;
                    }
                }
                if (row === row2 && col === col2 + 1) {
                    if (info.alarmLevel !== undefined && info.alarmLevel >= 0) {
                        if (info.alarmLevel > 0) td.style.fontWeight = 'bold';
                        var level = info.alarmLevel;
                        var levelCfg = Data.Normal || {};
                        if (level === 100) levelCfg = Data.FirstLevel || {};
                        else if (level === 200) levelCfg = Data.SecondLevel || {};
                        else if (level === 300) levelCfg = Data.ThirdLevel || {};
                        if (levelCfg.Opacity && levelCfg.Opacity !== 0) {
                            td.style.backgroundColor = color16ToRgba('#' + (levelCfg.BackgroundColor || 'FFFFFF'), levelCfg.Opacity);
                        }
                        td.style.color = '#' + (levelCfg.Color || '000000');
                    }
                }
            }
        };

        helper.createTable = function (data) {
            var container = document.getElementById(helper.divid);
            if (!container) return;
            container.innerHTML = '';

            var hotElement = document.createElement('div');
            hotElement.style.width = '100%';
            hotElement.style.height = '100%';
            container.appendChild(hotElement);

            var hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                theme: 'ht-theme-classic',
                data: data,
                colWidths: [30, 20, 30, 20, 30, 20],
                columns: helper.columns,
                stretchH: 'all',
                rowHeaders: false,
                colHeaders: false,
                autoWrapRow: false,
                rowHeights: [40],
                columnSorting: true,
                allowInsertRow: false,
                sortIndicator: true,
                manualColumnResize: true,
                manualRowResize: true,
                filters: true,
                renderAllRows: true,
                search: true,
                mergeCells: [{ row: 0, col: 0, rowspan: 1, colspan: 6 }],
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    cellProperties.editor = false;
                    cellProperties.renderer = helper.addCellStyle;
                    return cellProperties;
                },
                afterOnCellMouseOver: function (event, coords, TD) {
                    if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                        var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                        if (isNotVal(rawValue)) {
                            TD.title = rawValue;
                        }
                    }
                },
                afterOnCellMouseDown: function (event, coords, TD) {
                    if (helper.doubleClickTimer != null &&
                        helper.lastClickRow === coords.row &&
                        helper.lastClickCol === coords.col) {
                        clearTimeout(helper.doubleClickTimer);
                        helper.doubleClickTimer = null;
                        viewDeviceRealTimeMonitoringData(coords.row, coords.col);
                        helper.lastClickRow = -1;
                        helper.lastClickCol = -1;
                    } else {
                        helper.lastClickRow = coords.row;
                        helper.lastClickCol = coords.col;
                        if (helper.doubleClickTimer) {
                            clearTimeout(helper.doubleClickTimer);
                        }
                        helper.doubleClickTimer = setTimeout(function () {
                            helper.doubleClickTimer = null;
                        }, 250);
                    }
                }
            });

            helper.hot = hot;
            helper.doubleClickTimer = null;
            helper.lastClickRow = -1;
            helper.lastClickCol = -1;

            for (var i = 0; i < helper.CellInfo.length; i++) {
                var info = helper.CellInfo[i];
                if (helper.hot) {
                    helper.hot.setCellMeta(info.row, info.col, 'columnDataType', info.columnDataType);
                }
            }
        };

        helper.createTable(helper.sourceData);
        return helper;
    }
};

function viewDeviceRealTimeMonitoringData(row, col) {
    if (!deviceRealTimeMonitoringDataHandsontableHelper || !deviceRealTimeMonitoringDataHandsontableHelper.hot) return;
    if (row > 0 && col % 2 === 1) {
        var colIndex = (col - 1) / 2;
        var cellInfo = null;
        var CellInfo = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo || [];
        for (var i = 0; i < CellInfo.length; i++) {
            if (CellInfo[i].row === row && CellInfo[i].col === colIndex) {
                cellInfo = CellInfo[i];
                break;
            }
        }
        if (cellInfo) {
            var itemName = deviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell(row, col - 1);
            var itemValue = deviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell(row, col);
            var info = itemName + ':' + itemValue;
            if (cellInfo.type === 0 || cellInfo.type === 5) {
                if (cellInfo.resolutionMode === 2 || cellInfo.resolutionMode === 7) {
                    mini.alert('查看趋势曲线: ' + info);
                } else {
                    mini.alert('查看数据表: ' + info);
                }
            } else if (cellInfo.type === 1 || cellInfo.type === 3) {
                if (isNumByCalculateItemCode(cellInfo.column)) {
                    mini.alert('查看趋势曲线: ' + info);
                } else {
                    mini.alert('查看数据表: ' + info);
                }
            } else {
                mini.alert('查看详情: ' + info);
            }
        }
    }
}

// ================================================================
// 9. 右侧区域 - 设备控制 + 设备信息
// ================================================================
function initRightTabs() {
    rightTabs = mini.get('rightTabs');
    if (!rightTabs) return;
    var tabs = [];
    for (var i = 0; i < RIGHT_TAB_CONFIG.length; i++) {
        var cfg = RIGHT_TAB_CONFIG[i];
        tabs.push({
            name: cfg.id,
            title: cfg.title,
            body: '<div id="' + cfg.id + '_container" style="width:100%;height:100%;"></div>'
        });
    }
    rightTabs.setTabs(tabs);
    if (tabs.length > 0) {
        rightTabs.activeTab(tabs[0]);
    }
}

function onRightTabChanged(e) {
    var tab = e.tab;
    if (!tab) return;
    console.log('右侧Tab切换:', tab.name);
    refreshRightTabs();
}

function refreshRightTabs() {
    if (!rightTabs) return;
    if (!currentDeviceId || currentDeviceId <= 0) {
        var container = document.getElementById('right_DeviceControl_container');
        if (container) container.innerHTML = '<div class="loading-placeholder">请选择设备</div>';
        var infoContainer = document.getElementById('right_DeviceInfo_container');
        if (infoContainer) infoContainer.innerHTML = '<div class="loading-placeholder">请选择设备</div>';
        return;
    }
    var rightActiveTab = rightTabs.getActiveTab();
    if (!rightActiveTab) return;
    var tabName = rightActiveTab.name;
    switch (tabName) {
        case 'right_DeviceControl':
            loadDeviceControl();
            break;
        case 'right_DeviceInfo':
            loadDeviceInfo();
            break;
        default:
            break;
    }
}

function loadDeviceControl() {
    var containerId = 'right_DeviceControl_container';
    var container = document.getElementById(containerId);
    if (!container) return;

    var grid = mini.get('deviceGrid');
    if (!grid || !grid.getSelected()) {
        container.innerHTML = '<div class="loading-placeholder">请选择设备</div>';
        return;
    }
    var selected = grid.getSelected();
    var deviceId = selected.id;
    var deviceName = selected.deviceName;
    var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

    container.innerHTML = '<div class="loading-placeholder">加载控制项...</div>';

    $.ajax({
        url: context + '/realTimeMonitoringController/getDeviceControlData',
        type: 'POST',
        data: {
            deviceId: deviceId,
            deviceName: deviceName,
            deviceType: deviceType
        },
        dataType: 'json',
        timeout: 10000,
        success: function(result) {
            container.innerHTML = '';
            if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">无控制项</div>';
                return;
            }

            var gridControl = new mini.DataGrid();
            gridControl.set({
                style: 'width:100%;height:100%;',
                data: result.totalRoot,
                idField: 'id',
                showPager: false,
                allowResize: true,
                columns: [
                    { field: 'item', header: '控制项', width: 150, align: 'left' },
                    { field: 'action', header: '操作', width: 200, align: 'center', renderer: function(e) {
                        var record = e.record;
                        var resolutionMode = record.resolutionMode;
                        var itemMeaning = record.itemMeaning || [];
                        var commStatus = parseInt(record.commStatus, 10) || 0;
                        var isControl = parseInt(record.isControl, 10) || 0;
                        var disabled = !(commStatus > 0 && isControl === 1);

                        var btnStyle = 'width:80%;';
                        if (disabled) {
                            btnStyle += 'opacity:0.5; cursor:not-allowed; pointer-events:none; background-color:#ccc; color:#666;';
                        }

                        var html = '<div style="display:flex; flex-direction:column; align-items:center; gap:4px;">';

                        if (resolutionMode == 1) {
                            var btnCount = Math.min(itemMeaning.length, 3);
                            for (var i = 0; i < btnCount; i++) {
                                var text = itemMeaning[i][1];
                                var value = itemMeaning[i][0];
                                html += '<button class="mini-button" style="' + btnStyle + '" ' +
                                        (disabled ? 'disabled' : '') +
                                        ' onclick="onEnumControlClick(' + record.id + ', \'' + record.item + '\', \'' + record.itemcode + '\', \'' + record.quantity + '\', \'' + value + '\', \'' + text + '\', ' + disabled + ')">' + text + '</button>';
                            }
                            if (itemMeaning.length > 3) {
                                html += '<button class="mini-button" style="' + btnStyle + '" ' + (disabled ? 'disabled' : '') +
                                        ' onclick="showMoreEnum(' + record.id + ')">更多</button>';
                            }
                        } else if (resolutionMode == 0) {
                            for (var i = 0; i < itemMeaning.length; i++) {
                                var text = itemMeaning[i].status;
                                var value = itemMeaning[i].value;
                                var bitIndex = itemMeaning[i].bitIndex;
                                html += '<button class="mini-button" style="' + btnStyle + '" ' +
                                        (disabled ? 'disabled' : '') +
                                        ' onclick="onSwitchControlClick(' + record.id + ', \'' + record.item + '\', \'' + record.itemcode + '\', \'' + record.quantity + '\', ' + value + ', ' + bitIndex + ', \'' + text + '\', ' + disabled + ')">' + text + '</button>';
                            }
                        } else {
                            html += '<button class="mini-button" style="' + btnStyle + '" ' +
                                    (disabled ? 'disabled' : '') +
                                    ' onclick="onNumericControlClick(' + record.id + ', \'' + record.itemcode + '\', \'' + record.itemName + '\', \'' + (record.unit || '') + '\', ' + record.quantity + ', \'' + record.storeDataType + '\', ' + disabled + ')">设置</button>';
                        }

                        html += '</div>';
                        return html;
                    }}
                ]
            });
            gridControl.render(container);
        },
        error: function(xhr, status, errorThrown) {
            console.error('加载控制项失败:', status, errorThrown);
            container.innerHTML = '<div class="loading-placeholder error">加载失败</div>';
        }
    });
}

function onEnumControlClick(recordId, item, controlType, quantity, value, text, disabled) {
    if (disabled) return;
    var grid = mini.get('deviceGrid');
    var selected = grid.getSelected();
    if (!selected) return;
    var deviceId = selected.id;
    var deviceName = selected.deviceName;

    var tipInfo = _loginUserLanguageResource.deviceName + ": <font color=red>" + deviceName + "</font>";
    tipInfo += "</br>" + item + ": <font color=red>" + text + "</font>";
    tipInfo += "</br>" + _loginUserLanguageResource.confirmOperation;

    mini.confirm(tipInfo, _loginUserLanguageResource.tip, function(action) {
        if (action === 'ok') {
            sendDeviceControl(deviceId, deviceName, controlType, quantity, value, null);
        }
    });
}

function onSwitchControlClick(recordId, item, controlType, quantity, value, bitIndex, text, disabled) {
    if (disabled) return;
    var grid = mini.get('deviceGrid');
    var selected = grid.getSelected();
    if (!selected) return;
    var deviceId = selected.id;
    var deviceName = selected.deviceName;

    var tipInfo = _loginUserLanguageResource.deviceName + ": <font color=red>" + deviceName + "</font>";
    tipInfo += "</br>" + item + ": <font color=red>" + text + "</font>";
    tipInfo += "</br>" + _loginUserLanguageResource.confirmOperation;

    mini.confirm(tipInfo, _loginUserLanguageResource.tip, function(action) {
        if (action === 'ok') {
            sendDeviceControl(deviceId, deviceName, controlType, quantity, value, bitIndex);
        }
    });
}

function onNumericControlClick(recordId, controlType, itemName, unit, quantity, storeDataType, disabled) {
    if (disabled) return;
    var grid = mini.get('deviceGrid');
    var selected = grid.getSelected();
    if (!selected) return;
    var deviceId = selected.id;
    var deviceName = selected.deviceName;

    var win = new mini.Window();
    win.set({
        title: '设置 ' + itemName,
        width: 400,
        height: 200,
        modal: true,
        showHeader: true,
        allowResize: false
    });

    var html = '<div style="padding:20px;">';
    html += '<table>';
    html += '<tr><td>设备：</td><td>' + deviceName + '</td></tr>';
    html += '<tr><td>参数：</td><td>' + itemName + (unit ? ' (' + unit + ')' : '') + '</td></tr>';
    html += '<tr><td>数值：</td><td><input id="numericInput" class="mini-textbox" style="width:200px;" /></td></tr>';
    html += '</table>';
    html += '<div style="text-align:center;margin-top:20px;">';
    html += '<button class="mini-button" onclick="submitNumericControl()">确定</button>';
    html += '<button class="mini-button" style="margin-left:10px;" onclick="closeNumericWin()">取消</button>';
    html += '</div>';
    html += '</div>';

    win.setBody(html);
    win.show();

    win._context = {
        deviceId: deviceId,
        deviceName: deviceName,
        controlType: controlType,
        quantity: quantity,
        storeDataType: storeDataType
    };

    window.submitNumericControl = function() {
        var input = mini.get('numericInput');
        var value = input.getValue();
        if (value === '' || isNaN(value)) {
            mini.alert('请输入有效的数值');
            return;
        }
        var ctx = win._context;
        sendDeviceControl(ctx.deviceId, ctx.deviceName, ctx.controlType, ctx.quantity, value, null);
        win.close();
    };
    window.closeNumericWin = function() {
        win.close();
    };
}

function showMoreEnum(recordId) {
    mini.alert('更多枚举功能开发中...');
}

function sendDeviceControl(deviceId, deviceName, controlType, quantity, controlValue, bitIndex) {
    var params = {
        deviceId: deviceId,
        deviceName: deviceName,
        controlType: controlType,
        quantity: quantity,
        controlValue: controlValue
    };
    if (bitIndex !== null && bitIndex !== undefined) {
        params.bitIndex = bitIndex;
    }

    var mask = mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: '指令发送中...'
    });

    $.ajax({
        url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
        type: 'POST',
        data: params,
        dataType: 'json',
        timeout: 10000,
        success: function(result) {
            mini.unmask(mask);
            if (result.flag == false) {
                mini.alert((result.msg || '会话过期，请重新登录'));
            } else if (result.flag == true && result.error == false) {
                mini.alert(result.msg);
            } else if (result.flag == true && result.error == true) {
                mini.alert(result.msg);
            }
        },
        error: function(xhr, status, errorThrown) {
            mini.unmask(mask);
            mini.alert('请求失败：' + status);
        }
    });
}

function loadDeviceInfo() {
    var containerId = 'right_DeviceInfo_container';
    var container = document.getElementById(containerId);
    if (!container) return;

    var grid = mini.get('deviceGrid');
    if (!grid || !grid.getSelected()) {
        container.innerHTML = '<div class="loading-placeholder">请选择设备</div>';
        return;
    }
    var selected = grid.getSelected();
    var deviceId = selected.id;
    var deviceName = selected.deviceName || '';
    var calculateType = selected.calculateType || 0;
    var deviceType = currentLevel1 ? currentLevel1.deviceTypeId : '0';

    container.innerHTML = '<div class="loading-placeholder">加载设备信息...</div>';

    $.ajax({
        url: context + '/realTimeMonitoringController/getDeviceAddInfoData',
        type: 'POST',
        data: {
            deviceId: deviceId,
            wellName: deviceName,
            calculateType: calculateType,
            deviceType: deviceType
        },
        dataType: 'json',
        timeout: 10000,
        success: function(result) {
            container.innerHTML = '';
            if (!result) {
                container.innerHTML = '<div class="loading-placeholder">无数据</div>';
                return;
            }

            var infoList = result.deviceInfoDataList || [];
            var auxList = result.auxiliaryDeviceList || [];

            var html = '<div style="display:flex; flex-direction:column; height:100%;">';
            
            // ---- 附加信息（上半部分） ----
            html += '<div style="flex:1; min-height:0; overflow:auto; border-bottom:1px solid #e8e8e8;">';
            if (infoList.length > 0) {
                html += '<table style="width:100%; border-collapse:collapse; font-size:12px;">';
                html += '<thead><tr style="background:#f5f7fa;">';
                html += '<th style="padding:6px 10px; text-align:left; border:1px solid #ddd;">变量</th>';
                html += '<th style="padding:6px 10px; text-align:center; border:1px solid #ddd;">值</th>';
                html += '</tr></thead><tbody>';
                for (var i = 0; i < infoList.length; i++) {
                    var item = infoList[i];
                    var rowStyle = (i % 2 === 0) ? '' : 'background:#f9f9f9;';
                    html += '<tr style="' + rowStyle + '">';
                    html += '<td style="padding:4px 10px; border:1px solid #ddd;" title="' + (item.name || '') + '">' + (item.name || '') + '</td>';
                    html += '<td style="padding:4px 10px; border:1px solid #ddd; text-align:center;" title="' + (item.value || '') + '">' + (item.value || '') + '</td>';
                    html += '</tr>';
                }
                html += '</tbody></table>';
            } else {
                html += '<div class="loading-placeholder" style="height:100%;">无附加信息</div>';
            }
            html += '</div>';

            // ---- 辅件设备（下半部分，默认全部展开，保留列头，无操作列） ----
            html += '<div style="flex:1; min-height:0; overflow:auto; padding:4px;">';
            if (auxList.length > 0) {
                html += '<table style="width:100%; border-collapse:collapse; font-size:12px;">';
                html += '<thead><tr style="background:#f5f7fa;">';
                html += '<th style="padding:4px 8px; border:1px solid #ddd; text-align:center; width:50px;">序号</th>';
                html += '<th style="padding:4px 8px; border:1px solid #ddd; text-align:left;">设备名称</th>';
                html += '</tr></thead>';
                html += '<tbody>';
                for (var i = 0; i < auxList.length; i++) {
                    var item = auxList[i];
                    var rowStyle = (i % 2 === 0) ? '' : 'background:#f9f9f9;';
                    // 主行：序号 + 名称
                    html += '<tr style="' + rowStyle + '">';
                    html += '<td style="padding:4px 8px; border:1px solid #ddd; text-align:center;">' + (i + 1) + '</td>';
                    html += '<td style="padding:4px 8px; border:1px solid #ddd;">' + (item.name || '') + '</td>';
                    html += '</tr>';
                    // 详情行（紧跟在主行下方，默认显示，跨两列）
                    html += '<tr style="background:#fafafa;">';
                    html += '<td colspan="2" style="padding:8px 12px; border:1px solid #ddd;">';
                    html += '<div style="word-break:break-all; line-height:1.6;">' + (item.detailsInfo || '无详情') + '</div>';
                    html += '</td>';
                    html += '</tr>';
                }
                html += '</tbody></table>';
            } else {
                html += '<div class="loading-placeholder" style="height:100%;">无辅件设备</div>';
            }
            html += '</div>';
            html += '</div>';

            container.innerHTML = html;
        },
        error: function(xhr, status, errorThrown) {
            console.error('加载设备信息失败:', status, errorThrown);
            container.innerHTML = '<div class="loading-placeholder error">加载失败</div>';
        }
    });
}

// ================================================================
// 资源监测相关函数
// ================================================================
function openResourceChart(itemCode, itemName) {
    var win = new mini.Window();
    win.set({
        title: itemName + ' - 趋势曲线',
        width: '70%',
        height: '60%',
        modal: true,
        showHeader: true,
        allowResize: true,
        maxable: true,
        minable: true
    });
    win.show();

    var divId = 'resourceChart_' + itemCode + '_' + Date.now();
    var html = '<div id="' + divId + '" style="width:100%;height:100%;"></div>';
    win.setBody(html);

    loadResourceChartData(itemCode, itemName, divId, win);
}

function loadResourceChartData(itemCode, itemName, divId, win) {
    var endDate = new Date();
    var startDate = new Date(endDate.getTime() - 24 * 3600 * 1000);
    var startStr = formatDate(startDate);
    var endStr = formatDate(endDate);

    $.ajax({
        url: context + '/realTimeMonitoringController/getResourceProbeHistoryCurveData',
        type: 'POST',
        data: {
            itemCode: itemCode,
            startDate: startStr,
            endDate: endStr
        },
        dataType: 'json',
        timeout: 10000,
        success: function(result) {
            if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                document.getElementById(divId).innerHTML = '<div style="text-align:center;padding:20px;">无数据</div>';
                return;
            }
            var chartData = result.totalRoot;
            var series = buildResourceSeries(chartData, itemCode, itemName);
            var title = itemName;
            var subtitle = result.startDate + ' ~ ' + result.endDate;
            var yTitle = itemName;

            if (typeof initResourceProbeHistoryCurveChartFn === 'function') {
                initResourceProbeHistoryCurveChartFn(series, 3600 * 1000, divId, title, subtitle, '时间', yTitle, ['#2caffe'], true, '%m-%d %H:%M');
            } else {
                // 降级方案
                Highcharts.chart(divId, {
                    chart: { type: 'spline', zooming: { mouseWheel: { enabled: false } } },
                    title: { text: title },
                    subtitle: { text: subtitle },
                    xAxis: { type: 'datetime', title: { text: '时间' } },
                    yAxis: { title: { text: yTitle } },
                    series: series,
                    credits: { enabled: false },
                    exporting: { enabled: true, fallbackToExportServer: false }
                });
            }
        },
        error: function() {
            document.getElementById(divId).innerHTML = '<div style="text-align:center;padding:20px;color:red;">加载失败</div>';
        }
    });
}

function buildResourceSeries(data, itemCode, itemName) {
    var series = [];
    if (itemCode === 'cpuUsedPercent') {
        var cpuMap = {};
        data.forEach(function(item) {
            var values = item.value.split(';');
            values.forEach(function(v, idx) {
                var key = 'CPU' + (idx + 1);
                if (!cpuMap[key]) cpuMap[key] = [];
                var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
                cpuMap[key].push([ts, parseFloat(v)]);
            });
        });
        for (var key in cpuMap) {
            series.push({ name: key, data: cpuMap[key] });
        }
    } else if (itemCode === 'jedisStatus') {
        var maxData = [], usedData = [];
        data.forEach(function(item) {
            var values = item.value.split(';');
            var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
            if (values.length >= 2) {
                maxData.push([ts, parseFloat(values[0])]);
                usedData.push([ts, parseFloat(values[1])]);
            }
        });
        series.push({ name: '最大内存(m)', data: maxData });
        series.push({ name: '已用内存(m)', data: usedData });
    } else if (itemCode === 'tableSpaceSize') {
        var dataSpace = [], undoSpace = [];
        data.forEach(function(item) {
            var values = item.value.split(';');
            var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
            if (values.length >= 2) {
                dataSpace.push([ts, parseFloat(values[0])]);
                undoSpace.push([ts, parseFloat(values[1])]);
            }
        });
        series.push({ name: '数据表空间(%)', data: dataSpace });
        series.push({ name: 'UNDO表空间(%)', data: undoSpace });
    } else {
        var singleData = [];
        data.forEach(function(item) {
            var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
            var val = parseFloat(item.value);
            if (!isNaN(val)) singleData.push([ts, val]);
        });
        series.push({ name: itemName, data: singleData });
    }
    return series;
}

function formatDate(date) {
    var y = date.getFullYear();
    var m = String(date.getMonth() + 1).padStart(2, '0');
    var d = String(date.getDate()).padStart(2, '0');
    var h = String(date.getHours()).padStart(2, '0');
    var min = String(date.getMinutes()).padStart(2, '0');
    var s = String(date.getSeconds()).padStart(2, '0');
    return y + '-' + m + '-' + d + ' ' + h + ':' + min + ':' + s;
}

// ================================================================
// 10. 刷新数据
// ================================================================
function refreshData() {
    if (currentLevel2) loadAllData(currentLevel2);
}

// ================================================================
// 11. 页面初始化
// ================================================================
$(document).ready(function() {
    mini.parse();
    statTabs = mini.get('statTabs');
    middleTabs = mini.get('middleTabs');
    rightTabs = mini.get('rightTabs');

    var grid = mini.get('deviceGrid');
    if (grid) {
        grid.setColumns(DEFAULT_COLUMNS);
    }

    buildLevel1Tabs();

    window.addEventListener('message', function(event) {
        if (event.data && event.data.action === 'refresh') refreshData();
    });

    $(window).resize(function() {
        if (window.resizeTimer) clearTimeout(window.resizeTimer);
        window.resizeTimer = setTimeout(function() {
            document.querySelectorAll('.pie-chart-container').forEach(function(el) {
                if (el._chart) el._chart.reflow();
            });
            $('.chart-container').each(function() {
                var chart = $(this).highcharts();
                if (chart) chart.reflow();
            });
            $('#trendContainer .trend-chart-container').each(function() {
                var chart = $(this).highcharts();
                if (chart) chart.reflow();
            });
        }, 500);
    });

    console.log('实时监控模块加载完成');
});

// ================================================================
// 12. 暴露给外部
// ================================================================
window.refreshData = refreshData;
window.selectLevel1 = selectLevel1;
window.selectLevel2 = selectLevel2;
window.onStatTabChanged = onStatTabChanged;
window.onMiddleTabChanged = onMiddleTabChanged;
window.onRightTabChanged = onRightTabChanged;
window.onDeviceGridBeforeLoad = onDeviceGridBeforeLoad;
window.onDeviceGridLoad = onDeviceGridLoad;
window.onDeviceGridSelectChanged = onDeviceGridSelectChanged;
window.onDeviceGridDrawCell = onDeviceGridDrawCell;
window.onDeviceComboChange = onDeviceComboChange;
window.refreshDeviceList = refreshDeviceList;
window.exportData = exportData;
window.gotoHistory = gotoHistory;
</script>
</body>
</html>