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
        .level1-footer { flex-shrink: 0; background: #fafafa; border-top: 2px solid #e8e8e8; padding: 0 10px; display: flex; align-items: center; gap: 2px; height: 36px; overflow-x: auto; order: 2; }
        .level1-footer .tab-item { padding: 4px 16px; font-size: 13px; cursor: pointer; color: #666; background: transparent; border-bottom: 2px solid transparent; transition: all 0.2s; user-select: none; white-space: nowrap; }
        .level1-footer .tab-item:hover { color: #333; }
        .level1-footer .tab-item.active { color: #2d6a9f; font-weight: bold; border-bottom-color: #2d6a9f; }
        .level1-footer .loading-tip { color: #999; font-size: 12px; padding: 0 10px; }
        .main-area { flex: 1; display: flex; flex-direction: row; overflow: hidden; min-height: 0; }
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
        .right-area { flex: 0 0 21%; min-width: 130px; background: #fff; border-radius: 4px; box-shadow: 0 1px 4px rgba(0,0,0,0.06); overflow: hidden; display: flex; flex-direction: column; margin: 4px 4px 4px 0; }
        .right-area .mini-tabs { flex: 1; }
        .loading-placeholder { display: flex; align-items: center; justify-content: center; height: 100%; color: #999; font-size: 13px; flex-direction: column; }
        .alarm-badge { display: inline-block; border-radius: 10px; padding: 0 4px; min-width: 14px; height: 14px; line-height: 14px; text-align: center; font-size: 9px; font-weight: bold; margin-right: 2px; vertical-align: middle; }
        .level2-sidebar::-webkit-scrollbar { width: 3px; }
        .level2-sidebar::-webkit-scrollbar-thumb { background: #ccc; border-radius: 4px; }
        @media (max-width: 768px) { .main-area { flex-direction: column; } .level2-sidebar { width: 100%; height: 32px; flex-direction: row; padding: 0 8px; border-right: none; border-bottom: 1px solid #e8e8e8; overflow-x: auto; } .level2-sidebar .tab-item { writing-mode: horizontal-tb; transform: none; min-height: auto; padding: 4px 12px; border-left: none; border-bottom: 2px solid transparent; } .level2-sidebar .tab-item.active { border-left: none; border-bottom-color: #1890ff; } .left-main-area { flex: 2; } .middle-area { flex: 1; min-height: 150px; margin: 0 4px 4px 4px; } .right-area { flex: 0 0 160px; margin: 0 4px 4px 4px; } .stat-charts-area { flex: 0 0 40%; min-height: 100px; } }
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
                        <button class="mini-button" iconCls="note-refresh" onclick="refreshDeviceList()">刷新</button>
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
                             onselectchanged="onDeviceGridSelectChanged"
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

        <!-- 底部一级标签 -->
        <div class="level1-footer" id="level1Footer">
            <span class="loading-tip">加载中...</span>
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

    var statTabs = null;
    var middleTabs = null;
    var rightTabs = null;
    var deviceGrid = null;

    // 统计 Tab 配置
    var STAT_TAB_CONFIG = {
        'FESdiagramResult': {
            id: 'stat_FESdiagramResult',
            title: function() { return loginUserLanguageResource.workType || '工况类型'; },
            api: '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData'
        },
        'CommStatus': {
            id: 'stat_CommStatus',
            title: function() { return loginUserLanguageResource.commStatus || '通信状态'; },
            api: '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData'
        },
        'RunStatus': {
            id: 'stat_RunStatus',
            title: function() { return loginUserLanguageResource.runStatus || '运行状态'; },
            api: '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData'
        },
        'NumStatus': {
            id: 'stat_NumStatus',
            title: function() { return loginUserLanguageResource.numStatus || '数值状态'; },
            api: '/realTimeMonitoringController/getRealTimeMonitoringNumStatusStatData'
        }
    };

    // 中间 Tab 配置
    var MIDDLE_TAB_CONFIG = [
        { id: 'middle_WellboreAnalysis', title: function() { return loginUserLanguageResource.wellboreAnalysis || '井筒分析'; } },
        { id: 'middle_SurfaceAnalysis', title: function() { return loginUserLanguageResource.surfaceAnalysis || '地面分析'; } },
        { id: 'middle_TrendCurve', title: function() { return loginUserLanguageResource.trendCurve || '趋势曲线'; } },
        { id: 'middle_DynamicData', title: function() { return loginUserLanguageResource.dynamicData || '动态数据'; } }
    ];

    // 右侧 Tab 配置
    var RIGHT_TAB_CONFIG = [
        { id: 'right_DeviceControl', title: function() { return loginUserLanguageResource.deviceControl || '设备控制'; } },
        { id: 'right_DeviceInfo', title: function() { return loginUserLanguageResource.deviceInformation || '设备信息'; } }
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
    // 3. 构建一级标签（底部）
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

            // 序号列
            if (col.dataIndex === 'id') {
                column.type = 'indexcolumn';
                column.width = col.width || 40;
                column.header = '序号';
                delete column.field;
            }
            // 设备名称列
            else if (col.dataIndex === 'deviceName') {
                column.width = col.width || 140;
                column.locked = true;
            }
            // 通信状态列
            else if (col.dataIndex === 'commStatusName') {
                column.width = col.width || 80;
            }
            // 运行状态列
            else if (col.dataIndex === 'runStatusName' || col.dataIndex === 'RunStatusName') {
                column.width = col.width || 80;
            }
            // 采集时间列
            else if (col.dataIndex === 'acqTime') {
                column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                column.width = col.width || 150;
            }

            columns.push(column);
        }
        return columns;
    }

    // ===== 设备表格加载前 - 注入参数 =====
    function onDeviceGridBeforeLoad(e) {
        var params = e.params || {};

        // 从 e.params 中获取分页参数
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || 25;
        params.start = pageIndex * pageSize;
        params.limit = pageSize;

        // 业务参数
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
            // 1. 保存 AlarmShowStyle 到父页面
            if (result.AlarmShowStyle && window.parent && window.parent.mini) {
                var alarmInput = window.parent.mini.get('AlarmShowStyle_Id');
                if (alarmInput) {
                    alarmInput.setValue(JSON.stringify(result.AlarmShowStyle));
                }
            }

            // 2. 动态生成列
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

            // 3. 选中第一行
            var data = grid.getData();
            if (data && data.length > 0) {
                grid.select(0);
                var selected = grid.getSelected();
                if (selected) {
                    onDeviceGridSelectChanged({ sender: grid });
                }
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
        }
    }

    // ===== 设备表格绘制单元格 =====
    function onDeviceGridDrawCell(e) {
        var record = e.record;
        var field = e.field;
        var value = e.value;

        if (field === 'deviceName') {
            var alarmInfo = record.alarmInfo || [];
            var hasAlarm = false;
            var count1 = 0, count2 = 0, count3 = 0;
            for (var i = 0; i < alarmInfo.length; i++) {
                var level = alarmInfo[i].alarmLevel;
                if (level == 100) { count1++; hasAlarm = true; }
                else if (level == 200) { count2++; hasAlarm = true; }
                else if (level == 300) { count3++; hasAlarm = true; }
            }
            if (hasAlarm) {
                var badgeHtml = '';
                if (count1 > 0) badgeHtml += '<span class="alarm-badge" style="background:#dc2828;color:#fff;">' + count1 + '</span>';
                if (count2 > 0) badgeHtml += '<span class="alarm-badge" style="background:#f09614;color:#fff;">' + count2 + '</span>';
                if (count3 > 0) badgeHtml += '<span class="alarm-badge" style="background:#fae600;color:#333;">' + count3 + '</span>';
                e.cellHtml = badgeHtml + (value || '');
            }
            return;
        }

        if (field === 'commStatusName') {
            var status = record.commStatus || 0;
            var colors = { 0: '#ff4d4f', 1: '#52c41a', 2: '#faad14' };
            var texts = { 0: '离线', 1: '在线', 2: '上线中' };
            e.cellHtml = '<span style="color:' + (colors[status] || '#999') + ';font-weight:bold;">' + (texts[status] || value || '') + '</span>';
            return;
        }

        if (field === 'runStatusName' || field === 'RunStatusName') {
            var status = record.runStatus || 0;
            var colors = { 0: '#ff4d4f', 1: '#52c41a', 2: '#999' };
            var texts = { 0: '停机', 1: '运行', 2: '无数据' };
            e.cellHtml = '<span style="color:' + (colors[status] || '#999') + ';font-weight:bold;">' + (texts[status] || value || '') + '</span>';
            return;
        }
    }

    // ===== 刷新设备列表 =====
    function refreshDeviceList() {
        var grid = mini.get('deviceGrid');
        if (grid) grid.load();
    }

    // ===== 设备下拉框变化 =====
    function onDeviceComboChange() {
        refreshDeviceList();
    }

    // ===== 导出数据 =====
    function exportData() {
        mini.alert('导出功能开发中...');
    }

    // ===== 跳转到历史查询 =====
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
    // 8. 中间/右侧区域
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
                body: '<div class="loading-placeholder">加载中...</div>'
            });
        }
        middleTabs.setTabs(tabs);
    }

    function onMiddleTabChanged(e) {
        console.log('中间Tab切换:', e.tab ? e.tab.name : '');
    }

    function initRightTabs() {
        rightTabs = mini.get('rightTabs');
        if (!rightTabs) return;
        var tabs = [];
        for (var i = 0; i < RIGHT_TAB_CONFIG.length; i++) {
            var cfg = RIGHT_TAB_CONFIG[i];
            tabs.push({
                name: cfg.id,
                title: typeof cfg.title === 'function' ? cfg.title() : cfg.title,
                body: '<div class="loading-placeholder">加载中...</div>'
            });
        }
        rightTabs.setTabs(tabs);
    }

    function onRightTabChanged(e) {
        console.log('右侧Tab切换:', e.tab ? e.tab.name : '');
    }

    // ================================================================
    // 9. 刷新数据
    // ================================================================
    function refreshData() {
        if (currentLevel2) loadAllData(currentLevel2);
    }

    // ================================================================
    // 10. 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        statTabs = mini.get('statTabs');
        middleTabs = mini.get('middleTabs');
        rightTabs = mini.get('rightTabs');

        // 初始化设备表格
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
            }, 500);
        });

        console.log('实时监控模块加载完成');
    });

    // ================================================================
    // 11. 暴露给外部
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