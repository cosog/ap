<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String moduleId = request.getParameter("moduleId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>历史查询</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        /* ===== 全局样式 ===== */
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; font-family:"Microsoft YaHei",Arial,sans-serif; background:#f0f2f5; }
        .history-container { width:100%; height:100%; display:flex; flex-direction:column; background:#fff; }
        .level1-footer { flex-shrink:0; background:#fafafa; border-top:1px solid #e0e0e0; padding:0 10px; display:flex; align-items:center; gap:2px; height:36px; overflow-x:auto; order:2; }
        .level1-footer .tab-item { padding:4px 16px; font-size:13px; cursor:pointer; color:#666; background:transparent; border-bottom:2px solid transparent; transition:all 0.2s; user-select:none; white-space:nowrap; }
        .level1-footer .tab-item:hover { color:#333; }
        .level1-footer .tab-item.active { color:#2d6a9f; font-weight:bold; border-bottom-color:#2d6a9f; }
        .level2-sidebar { flex-shrink:0; width:32px; background:#f5f7fa; border-right:1px solid #e8e8e8; overflow:auto; padding:8px 0; display:flex; flex-direction:column; align-items:center; justify-content:flex-start; }
        .level2-sidebar .tab-item { padding:10px 2px; font-size:12px; cursor:pointer; color:#555; background:transparent; border-left:3px solid transparent; transition:all 0.15s; user-select:none; text-align:center; writing-mode:vertical-rl; letter-spacing:2px; width:100%; flex-shrink:0; min-height:36px; line-height:1.4; box-sizing:border-box; }
        .level2-sidebar .tab-item:hover { background:#e8ecf0; color:#333; }
        .level2-sidebar .tab-item.active { background:#e6f7ff; color:#1890ff; font-weight:bold; border-left-color:#1890ff; }
        .level2-sidebar .no-child-tip { padding:12px 0; color:#999; font-size:12px; text-align:center; writing-mode:vertical-rl; letter-spacing:2px; }

        .left-panel { display:flex; flex-direction:column; height:100%; background:#f0f2f5; padding:4px; }
        .device-grid-wrapper { flex:1; overflow:hidden; background:#fff; border-radius:4px; box-shadow:0 1px 4px rgba(0,0,0,0.06); }
        .stat-pie-wrapper { flex:1; overflow:hidden; background:#fff; border-radius:4px; box-shadow:0 1px 4px rgba(0,0,0,0.06); }

        .right-panel { display:flex; flex-direction:column; height:100%; background:#f0f2f5; padding:4px; }
        .query-toolbar { flex-shrink:0; background:#fff; padding:4px 10px; border-bottom:1px solid #e8e8e8; display:flex; align-items:center; flex-wrap:wrap; gap:4px; border-radius:4px 4px 0 0; }
        .query-toolbar .mini-label { font-size:12px; color:#333; }
        .result-tabs { flex:1; overflow:hidden; background:#fff; border-radius:0 0 4px 4px; }
        .result-tabs .mini-tabs { width:100%; height:100%; }
        .result-tabs .mini-tabs .mini-tab-body { overflow:hidden !important; padding:0 !important; margin:0 !important; display:flex !important; flex-direction:column !important; }
        .chart-container { width:100%; height:100%; min-height:200px; }
        .table-container { width:100%; height:100%; }
        .loading-placeholder { display:flex; align-items:center; justify-content:center; height:100%; color:#999; font-size:13px; flex-direction:column; }
        .pie-chart-container { width:100%; height:100%; min-height:100px; }
        .hidden { display:none; }
        /* 报警徽章 */
        .alarm-badge { display:inline-block; border-radius:10px; padding:0 4px; min-width:14px; height:14px; line-height:14px; text-align:center; font-size:9px; font-weight:bold; margin-right:2px; vertical-align:middle; box-sizing:border-box; }
        .device-name-cell { white-space:nowrap; }
    </style>
</head>
<body>
<div class="history-container">
    <!-- 主区域 -->
    <div style="display:flex; flex:1; overflow:hidden; order:0;">
        <!-- 二级标签 -->
        <div class="level2-sidebar" id="level2Sidebar"><div class="no-child-tip">选择一级</div></div>
        <!-- 内容区域 -->
        <div style="flex:1; overflow:hidden;">
            <div class="mini-splitter" style="width:100%; height:100%;" vertical="false">
                <!-- 左侧：设备列表 + 饼图（垂直分割，饼图可折叠） -->
                <div size="35%" showCollapseButton="false" minSize="200">
                    <div class="left-panel">
                        <div class="mini-splitter" style="width:100%; height:100%;" vertical="true">
                            <!-- 设备列表（带工具栏） -->
                            <div id="deviceGridPanel" size="50%" showCollapseButton="false">
                                <div class="device-grid-wrapper" style="height:100%; display:flex; flex-direction:column;">
                                    <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:4px 8px;display:flex;align-items:center;gap:6px;flex-shrink:0;">
                                        <button id="btnRefresh" class="mini-button" iconCls="note-refresh" onclick="refreshDeviceList()">刷新</button>
                                        <input id="deviceCombo" class="mini-combobox" style="width:140px;" emptyText="-- 全部 --"
                                               url="<%=path%>/wellInformationManagerController/loadWellComboxList"
                                               dataField="list" totalField="totals" valueField="boxkey" textField="boxval"
                                               onvaluechanged="onDeviceComboChange" />
                                        <span style="flex:1;"></span>
                                        <button id="exportHistoryQueryDeviceListBtn" class="mini-button" iconCls="export" onclick="exportDeviceList()">导出</button>
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
                                    <div style="flex:1;overflow:hidden;">
                                        <div id="deviceGrid" class="mini-datagrid" style="width:100%; height:100%;"
                                             idField="id" pageSize="20" allowResize="true" allowAlternating="true"
                                             url="<%=path%>/historyQueryController/getHistoryQueryDeviceList"
                                             dataField="totalRoot" totalField="totalCount"
                                             onselectionchanged="onDeviceSelect" onload="onDeviceGridLoad" onbeforeload="onDeviceGridBeforeLoad"
                                             ondrawcell="onDeviceGridDrawCell">
                                            <div property="columns"><!-- 动态生成 --></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 统计饼图（可折叠） -->
                            <div id="statPanel" size="50%" showCollapseButton="true" minSize="80" collapseDirection="bottom">
                                <div class="stat-pie-wrapper" style="height:100%;">
                                    <div id="statTabs" class="mini-tabs" style="width:100%; height:100%;" activeIndex="0" onactivechanged="onStatTabChanged"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 右侧：结果 TabPanel（容器为空，由 JS 动态填充） -->
                <div size="65%" showCollapseButton="true" minSize="300">
                    <div class="right-panel">
                        <div class="result-tabs">
                            <div id="resultTabs" class="mini-tabs" style="width:100%; height:100%;" activeIndex="0" onactivechanged="onResultTabChanged"></div>
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
    // ================================================================
    // 0. 定义 context 和全局变量
    // ================================================================
    var context = '<%=path%>';
    var tabInfo = null;
    try { if (window.parent && window.parent.tabInfo) tabInfo = window.parent.tabInfo; } catch(e) { console.warn('无法获取 tabInfo', e); }
    var currentLevel1 = null, currentLevel2 = null, level1Data = [], level2Data = [];
    var currentDeviceId = 0, currentDeviceName = '', currentCalculateType = 0;
    var tiledPage = 1, totalTiledPages = 0;
    var statTabs = null, resultTabs = null, deviceGrid = null;

    // 统计 Tab 配置（与实时监控一致，但 API 指向历史查询）
    var STAT_TAB_CONFIG = {
        'FESdiagramResult': {
            id: 'stat_FESdiagramResult',
            title: _loginUserLanguageResource.workType,
            api: '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData'
        },
        'CommStatus': {
            id: 'stat_CommStatus',
            title: _loginUserLanguageResource.commStatus,
            api: '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData'
        },
        'RunStatus': {
            id: 'stat_RunStatus',
            title: _loginUserLanguageResource.runStatus,
            api: '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData'
        },
        'NumStatus': {
            id: 'stat_NumStatus',
            title: _loginUserLanguageResource.numStatus,
            api: '/realTimeMonitoringController/getRealTimeMonitoringNumStatusStatData'
        }
    };
    
    function initI18n() {
        // 1. 工具栏按钮
        var btnRefresh = mini.get('btnRefresh');
        if (btnRefresh) btnRefresh.setText(_loginUserLanguageResource.refresh);

        // 导出按钮（需添加 id="exportBtn" 或通过 class 选择）
        var exportBtn = mini.get('exportHistoryQueryDeviceListBtn');
        if (exportBtn) exportBtn.setText(_loginUserLanguageResource.exportData);


        // 2. 设备下拉框（emptyText）
        var deviceCombo = mini.get('deviceCombo');
        if (deviceCombo) {
            deviceCombo.setEmptyText('--' + (_loginUserLanguageResource.all) + '--');
        }

        // 3. 动态数据导出按钮（在 refreshDeviceTabs 中已创建，需在创建后设置）
        // 可通过监听 middleTabs 的 activechanged 事件，在动态数据标签激活时设置
        // 或者在 refreshDeviceTabs 中直接使用国际化文本

        // 4. 右侧面板的表格列标题（在 loadDeviceControl 中设置）
        // 可修改 renderer 中的列标题为国际化

        // 5. 所有 mini.alert 和 mini.confirm 中的文本
        // 已在函数中使用 _loginUserLanguageResource，无需额外修改
    }

    // ================================================================
    // 1. 构建一级标签（底部）
    // ================================================================
    function buildLevel1Tabs() {
        var container = document.getElementById('level1Footer');
        if (!container) return;
        container.innerHTML = '';
        if (!tabInfo || !tabInfo.children || tabInfo.children.length === 0) {
            container.innerHTML = '<span class="loading-tip">' + _loginUserLanguageResource.emptyMsg + '</span>';
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
            span.onclick = function() { selectLevel1(parseInt(this.dataset.index)); };
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
    }

    // ================================================================
    // 2. 构建二级标签（左侧竖排）
    // ================================================================
    function buildLevel2Tabs(parentItem) {
        var container = document.getElementById('level2Sidebar');
        if (!container) return;
        container.innerHTML = '';
        var children = parentItem.children || [];
        if (!children || children.length === 0) {
            container.innerHTML = '<div class="no-child-tip">' + _loginUserLanguageResource.emptyMsg + '</div>';
            currentLevel2 = null;
            return;
        }
        level2Data = children;
        var allIds = [];
        for (var i = 0; i < children.length; i++) {
            allIds.push(children[i].deviceTypeId);
        }
        var allTabs = [{ text: _loginUserLanguageResource.all, deviceTypeId: allIds.join(','), isAll: true }];
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
            div.onclick = function() { selectLevel2(parseInt(this.dataset.index)); };
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
        var allTabs = [{ text: _loginUserLanguageResource.all, deviceTypeId: '', isAll: true }];
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
    }

    // ================================================================
    // 3. 加载所有数据（设备列表 + 统计饼图）
    // ================================================================
    function loadAllData(level2Item) {
        if (!level2Item) return;
        var deviceTypeId = level2Item.deviceTypeId || '0';
        var orgId = window.parent && window.parent.mini ?
                window.parent.mini.get('leftOrg_Id').getValue() : '';
        clearStatFilters();
        refreshDeviceList();
        loadStatCharts(deviceTypeId,orgId);
    }

    // ================================================================
    // 4. 设备列表
    // ================================================================
    function onDeviceGridBeforeLoad(e) {
        var params = e.params || {};
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || 50;
        params.start = pageIndex * pageSize;
        params.limit = pageSize;
        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
        params.orgId = leftOrgId ? leftOrgId.getValue() : '';
        params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var combo = mini.get('deviceCombo');
        params.deviceName = combo ? combo.getValue() : '';
        // 统计筛选
        var getField = function(id) { var el = document.getElementById(id); return el ? el.value : ''; };
        params.FESdiagramResultStatValue = getField('HistoryQueryStatSelectFESdiagramResult_Id');
        params.commStatusStatValue = getField('HistoryQueryStatSelectCommStatus_Id');
        params.runStatusStatValue = getField('HistoryQueryStatSelectRunStatus_Id');
        params.numStatusStatValue = getField('HistoryQueryStatSelectNumStatus_Id');
        params.deviceTypeStatValue = getField('HistoryQueryStatSelectDeviceType_Id');
    }

    function onDeviceGridLoad(e) {
        var grid = e.sender, result = e.result;
        if (result) {
            if (result.AlarmShowStyle) {
            	var alarmInput = window.parent.mini.get('AlarmShowStyle_Id');
                if (alarmInput) {
                    alarmInput.setValue(JSON.stringify(result.AlarmShowStyle));
                }
            }
            if (result.columns) {
                var columns = buildGridColumns(result.columns);
                document.getElementById('HistoryQueryWellListColumnStr_Id').value = JSON.stringify(result.columns);
                setTimeout(function() {
                    grid.setColumns(columns);
                    grid.frozenColumns(0, 1);
                    grid.doLayout();
                }, 50);
            }
            var data = grid.getData();
            if (data && data.length > 0) grid.select(0);
        }
    }

    function buildGridColumns(colsData) {
        var cols = [];
        for (var i = 0; i < colsData.length; i++) {
            var col = colsData[i];
            var column = { field: col.dataIndex, header: col.header, headerAlign: 'center', align: 'center', width: col.width || 100 };
            if (col.dataIndex === 'id') { column.type = 'indexcolumn'; column.width = 40; column.header = _loginUserLanguageResource.idx; delete column.field; }
            else if (col.dataIndex === 'deviceName') { column.width = 140; column.locked = true; }
            else if (col.dataIndex === 'commStatusName') { column.width = 80; }
            else if (col.dataIndex === 'runStatusName') { column.width = 80; }
            else if (col.dataIndex === 'acqTime') { column.dateFormat = 'yyyy-MM-dd HH:mm:ss'; column.width = 150; }
            cols.push(column);
        }
        return cols;
    }

    // 设备表格绘制（报警徽章、颜色）
    function onDeviceGridDrawCell(e) {
        var record = e.record, field = e.field, value = e.value;
        if (!record || !field) return;
        var alarmShowStyle = getAlarmShowStyle() || {};
        var Data = alarmShowStyle.Data || {};
        var Comm = alarmShowStyle.Comm || {};
        var Run = alarmShowStyle.Run || {};
        var alarmInfo = record.alarmInfo || [];
        var fieldUpper = field.toUpperCase();

        if (fieldUpper === 'DEVICENAME') {
            var counts = {100:0,200:0,300:0};
            for (var i=0; i<alarmInfo.length; i++) {
                var level = alarmInfo[i].alarmLevel;
                if (level===100 || level===200 || level===300) counts[level] = (counts[level]||0)+1;
            }
            var badges = '';
            if (counts[100]>0) badges += createAlarmBadge(counts[100], Data.FirstLevel?Data.FirstLevel.Color:'dc2828');
            if (counts[200]>0) badges += createAlarmBadge(counts[200], Data.SecondLevel?Data.SecondLevel.Color:'f09614');
            if (counts[300]>0) badges += createAlarmBadge(counts[300], Data.ThirdLevel?Data.ThirdLevel.Color:'fae600');
            e.cellHtml = '<span class="device-name-cell">' + badges + (value||'') + '</span>';
            return;
        }
        if (fieldUpper === 'COMMSTATUSNAME') {
            var status = record.commStatus;
            var color = '#999';
            if (status===0) color = Comm.offline ? '#'+Comm.offline.Color : '#ff4d4f';
            else if (status===1) color = Comm.online ? '#'+Comm.online.Color : '#52c41a';
            else if (status===2) color = Comm.goOnline ? '#'+Comm.goOnline.Color : '#faad14';
            e.cellHtml = '<span style="color:'+color+';font-weight:bold;">'+(value||'')+'</span>';
            return;
        }
        if (fieldUpper === 'RUNSTATUSNAME') {
            var commStat = record.commStatus;
            var runStat = record.runStatus;
            if (commStat==0 || commStat==2 || !value) { e.cellHtml = ''; return; }
            var stopColor = Run.stop ? '#'+Run.stop.Color : '#ff4d4f';
            var runColor = Run.run ? '#'+Run.run.Color : '#52c41a';
            var noDataColor = Run.noData ? '#'+Run.noData.Color : '#999';
            var selColor = (runStat===0)?stopColor:(runStat===1?runColor:noDataColor);
            e.cellHtml = '<span style="color:'+selColor+';font-weight:bold;">'+(value||'')+'</span>';
            return;
        }
        // 其他数据列（报警高亮）
        if (fieldUpper!=='ID' && fieldUpper!=='DEVICENAME' && fieldUpper!=='COMMSTATUSNAME' && fieldUpper!=='RUNSTATUSNAME') {
            var alarmLevel = 0;
            for (var j=0; j<alarmInfo.length; j++) {
                if (alarmInfo[j].item && alarmInfo[j].item.toUpperCase()===fieldUpper) { alarmLevel = alarmInfo[j].alarmLevel||0; break; }
            }
            if (alarmLevel>0) {
                var style = getAlarmStyleByLevel(alarmLevel, alarmShowStyle);
                if (style && style.bg) e.cellStyle = 'background-color:'+style.bg+';color:'+style.color+';';
            }
        }
    }
    
    function onHistoryDataBeforeLoad(e) {
        var params = e.params || {};
        // 分页转换
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || 25;
        params.pageNum = pageIndex + 1;
        params.numPerPage = pageSize;
        params.start = pageIndex * pageSize;
        params.limit = pageSize;

        // 业务参数
        params.deviceId = currentDeviceId;
        params.deviceName = currentDeviceName;
        params.calculateType = currentCalculateType;
        params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        params.startDate = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        params.endDate = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        
        params.hours = getHistoryQueryHours();
        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
        params.orgId = leftOrgId ? leftOrgId.getValue() : '';
        // 可选 totalCount（后端可能用于优化）
        params.totalCount = params.totalCount || 0;
    }

    function getAlarmStyleByLevel(level, styleConfig) {
        var config = styleConfig || getAlarmShowStyle();
        var cfg = (config && config.Data) || {};
        var levelMap = {100: cfg.FirstLevel||{}, 200: cfg.SecondLevel||{}, 300: cfg.ThirdLevel||{}};
        var lvl = levelMap[level] || {};
        var bg = lvl.BackgroundColor ? '#'+lvl.BackgroundColor : 'transparent';
        var color = lvl.Color ? '#'+lvl.Color : '#000';
        var opacity = (lvl.Opacity!==undefined) ? lvl.Opacity : 1;
        var bgRgba = (opacity===0) ? 'transparent' : color16ToRgba(bg, opacity);
        return { bg: bgRgba, color: color };
    }

    function refreshDeviceList() {
        var grid = mini.get('deviceGrid');
        if (grid) grid.load();
    }

    function onDeviceComboChange() { refreshDeviceList(); }

    function onDeviceSelect(e) {
        var selected = e.selected;
        if (selected) {
            currentDeviceId = selected.id;
            currentDeviceName = selected.deviceName || '';
            currentCalculateType = selected.calculateType || 0;
            document.getElementById('selectedDeviceId_global').value = selected.id;

            // 动态刷新中间标签（根据设备配置）
            refreshHistoryTabs(selected);
        }
    }

    // ================================================================
    // 5. 统计饼图（与实时监控一致）
    // ================================================================
    function loadStatCharts(deviceTypeId,orgId) {
        clearStatFilters();
        var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
        var config = {
            FESdiagramResult: projectTabConfig.DeviceHistoryQuery ? projectTabConfig.DeviceHistoryQuery.FESDiagramStatPie : false,
            CommStatus: projectTabConfig.DeviceHistoryQuery ? projectTabConfig.DeviceHistoryQuery.CommStatusStatPie : false,
            RunStatus: projectTabConfig.DeviceHistoryQuery ? projectTabConfig.DeviceHistoryQuery.RunStatusStatPie : false,
            NumStatus: projectTabConfig.DeviceHistoryQuery ? projectTabConfig.DeviceHistoryQuery.NumStatusStatPie : false
        };
        updateStatTabs(config, deviceTypeId,orgId);
    }

    /**
     * 动态更新统计标签页（增删改），保持当前激活状态，并按 order 顺序插入
     * @param {Object} config 统计显示配置，如 { FESdiagramResult: true, CommStatus: true, ... }
     * @param {string} deviceTypeId 设备类型ID（用于请求数据）
     * @param {string} orgId 组织ID
     */
    function updateStatTabs(config, deviceTypeId, orgId) {
        statTabs = mini.get('statTabs');
        if (!statTabs) return;

        var paramDeviceType = deviceTypeId || '0';
        if (deviceTypeId && deviceTypeId.indexOf(',') > -1) {
            paramDeviceType = deviceTypeId;
        }

        // 1. 定义固定顺序
        var order = ['FESdiagramResult', 'CommStatus', 'RunStatus', 'NumStatus'];
        var newTabKeys = [];
        for (var i = 0; i < order.length; i++) {
            if (config[order[i]]) {
                newTabKeys.push(order[i]);
            }
        }

        // 记录当前激活的标签 key（在移除前保存）
        var activeTab = statTabs.getActiveTab();
        var activeKey = activeTab ? activeTab._key : null;

        // 2. 移除所有现有标签（包括占位标签）
        var currentTabs = statTabs.getTabs();
        for (var i = currentTabs.length - 1; i >= 0; i--) {
            var tab = currentTabs[i];
            // 清理饼图资源（如果有）
            if (tab._divId) {
                var container = document.getElementById(tab._divId);
                if (container) destroyPieChart(container);
            }
            statTabs.removeTab(tab);
        }

        // 无可用标签 → 显示占位
        if (newTabKeys.length === 0) {
            statTabs.setTabs([{
                name: 'stat_placeholder',
                title: _loginUserLanguageResource.emptyMsg,
                body: '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>'
            }]);
            return;
        }

        // 3. 按顺序添加新标签
        for (var i = 0; i < newTabKeys.length; i++) {
            var key = newTabKeys[i];
            var cfg = STAT_TAB_CONFIG[key];
            if (!cfg) continue;

            var divId = 'pieChart_' + key + '_' + Date.now() + '_' + i;
            var newTab = {
                name: cfg.id,
                title: cfg.title,
                _key: key,
                _api: cfg.api,
                _divId: divId,
                body: '<div style="width:100%;height:100%;min-height:' + (otherCardMinHeight || 100) + 'px;overflow:hidden;position:relative;"><div id="' + divId + '" style="width:100%;height:100%;"></div></div>'
            };
            statTabs.addTab(newTab);
        }

        // 4. 决定激活哪个标签
        var targetTab = null;
        // 优先恢复之前的激活标签
        if (activeKey && newTabKeys.indexOf(activeKey) !== -1) {
            var tabs = statTabs.getTabs();
            for (var i = 0; i < tabs.length; i++) {
                if (tabs[i]._key === activeKey) {
                    targetTab = tabs[i];
                    break;
                }
            }
        }
        // 否则激活第一个
        if (!targetTab) {
            var tabs = statTabs.getTabs();
            if (tabs.length > 0) {
                targetTab = tabs[0];
            }
        }
        if (targetTab) {
            statTabs.activeTab(targetTab);
            // 加载数据（传递 orgId）
            loadStatData(targetTab, paramDeviceType, orgId);
        }
    }

    function loadStatData(tab, deviceTypeId,orgId) {
        if (!tab || !tab._api) return;
        var divId = tab._divId;
        var container = document.getElementById(divId);
        if (container) container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.loadingData+'</div>';
        $.ajax({
            url: context + tab._api,
            type: 'POST',
            data: { orgId: orgId, deviceType: deviceTypeId || '0' },
            dataType: 'json',
            timeout: 10000,
            success: function(result) {
                if (result.AlarmShowStyle) {
                	var alarmInput = window.parent.mini.get('AlarmShowStyle_Id');
                    if (alarmInput) {
                        alarmInput.setValue(JSON.stringify(result.AlarmShowStyle));
                    }
                }
                var data = extractPieData(result, tab._key, result.AlarmShowStyle);
                renderPieChart(divId, data, tab.title, tab._key);
            },
            error: function() {
                if (container) container.innerHTML = '<div class="loading-placeholder error">'+_loginUserLanguageResource.requestFailed+'</div>';
            }
        });
    }

    function extractPieData(result, tabKey, alarmShowStyle) {
        if (!result) return [{ name:_loginUserLanguageResource.emptyMsg, y:1 }];
        var list = result.totalRoot || [];
        var data = [];
        var comm = (alarmShowStyle&&alarmShowStyle.Comm)||{};
        var run = (alarmShowStyle&&alarmShowStyle.Run)||{};
        var dataStyle = (alarmShowStyle&&alarmShowStyle.Data)||{};
        for (var i=0; i<list.length; i++) {
            var item = list[i];
            if (item.itemCode==='all' || item.count<=0) continue;
            var point = { name: item.item || item.text || '未知', y: item.count };
            if (tabKey==='CommStatus') {
                if (item.itemCode==='online') point.color = '#'+(comm.online?comm.online.Color:'52c41a');
                else if (item.itemCode==='goOnline') point.color = '#'+(comm.goOnline?comm.goOnline.Color:'faad14');
                else if (item.itemCode==='offline') point.color = '#'+(comm.offline?comm.offline.Color:'ff4d4f');
            } else if (tabKey==='RunStatus') {
                if (item.itemCode==='run') point.color = '#'+(run.run?run.run.Color:'52c41a');
                else if (item.itemCode==='stop') point.color = '#'+(run.stop?run.stop.Color:'ff4d4f');
                else if (item.itemCode==='noData') point.color = '#'+(run.noData?run.noData.Color:'999');
                else if (item.itemCode==='goOnline') point.color = '#'+(comm.goOnline?comm.goOnline.Color:'faad14');
                else if (item.itemCode==='offline') point.color = '#'+(comm.offline?comm.offline.Color:'ff4d4f');
            } else if (tabKey==='NumStatus') {
                var level = item.level;
                if (level===0) point.color = '#'+(dataStyle.Normal?dataStyle.Normal.BackgroundColor:'FFFFFF');
                else if (level===100) point.color = '#'+(dataStyle.FirstLevel?dataStyle.FirstLevel.BackgroundColor:'DC2828');
                else if (level===200) point.color = '#'+(dataStyle.SecondLevel?dataStyle.SecondLevel.BackgroundColor:'F09614');
                else if (level===300) point.color = '#'+(dataStyle.ThirdLevel?dataStyle.ThirdLevel.BackgroundColor:'FAE600');
                point.level = level;
            }
            data.push(point);
        }
        return data.length>0 ? data : [{ name:_loginUserLanguageResource.emptyMsg, y:1 }];
    }

    function renderPieChart(divId, data, title, tabKey) {
        var container = document.getElementById(divId);
        if (!container) return;
        destroyPieChart(container);
        container._pieData = data;
        container._pieTitle = title;
        container._pieTabKey = tabKey;
        if (data.length===1 && data[0].name===_loginUserLanguageResource.emptyMsg) {
            container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
            return;
        }
        var chart = Highcharts.chart(divId, {
            chart: { type:'pie', plotBackgroundColor:null, plotBorderWidth:null, plotShadow:false, zooming:{ mouseWheel:{ enabled:false } } },
            credits: { enabled:false },
            title: { text: title||'', style:{ fontSize:'13px' } },
            tooltip: { pointFormat: _loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+_loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>' },
            legend: { align:'center', verticalAlign:'bottom', layout:'horizontal' },
            plotOptions: {
                pie: {
                    allowPointSelect: true, cursor:'pointer',
                    dataLabels: { enabled:true, format:'<b>{point.name}</b>: {point.y}' },
                    showInLegend: true,
                    events: {
                        click: function(e) {
                            var fieldId = '';
                            if (tabKey==='FESdiagramResult') fieldId = 'HistoryQueryStatSelectFESdiagramResult_Id';
                            else if (tabKey==='CommStatus') fieldId = 'HistoryQueryStatSelectCommStatus_Id';
                            else if (tabKey==='RunStatus') fieldId = 'HistoryQueryStatSelectRunStatus_Id';
                            else if (tabKey==='NumStatus') fieldId = 'HistoryQueryStatSelectNumStatus_Id';
                            if (fieldId) {
                                var input = document.getElementById(fieldId);
                                if (input) {
                                    if (e.point.selected) input.value = '';
                                    else input.value = (tabKey==='NumStatus' ? (e.point.level!==undefined?e.point.level:'') : e.point.name);
                                }
                            }
                            mini.get('deviceCombo').setValue('');
                            refreshDeviceList();
                        }
                    }
                }
            },
            exporting: { enabled:true, filename:title, fallbackToExportServer:false },
            series: [{ type:'pie', name:_loginUserLanguageResource.deviceCount, data:data }]
        });
        container._chart = chart;
        if (window.ResizeObserver) {
            var observer = new ResizeObserver(function() {
                if (container._resizeTimer) clearTimeout(container._resizeTimer);
                container._resizeTimer = setTimeout(function() { recreatePieChart(container); }, 200);
            });
            observer.observe(container);
            container._resizeObserver = observer;
        }
    }

    function recreatePieChart(container) {
        if (!container || !container._pieData) return;
        var divId = container.id, data = container._pieData, title = container._pieTitle, tabKey = container._pieTabKey;
        if (container._chart) { container._chart.destroy(); container._chart = null; }
        container.innerHTML = '';
        var chart = Highcharts.chart(divId, {
            chart: { type:'pie', plotBackgroundColor:null, plotBorderWidth:null, plotShadow:false, zooming:{ mouseWheel:{ enabled:false } } },
            credits: { enabled:false },
            title: { text: title||'', style:{ fontSize:'13px' } },
            tooltip: { pointFormat: _loginUserLanguageResource.deviceCount+': <b>{point.y}</b> '+_loginUserLanguageResource.proportion+': <b>{point.percentage:.1f}%</b>' },
            legend: { align:'center', verticalAlign:'bottom', layout:'horizontal' },
            plotOptions: {
                pie: {
                    allowPointSelect: true, cursor:'pointer',
                    dataLabels: { enabled:true, format:'<b>{point.name}</b>: {point.y}' },
                    showInLegend: true,
                    events: {
                        click: function(e) {
                            var fieldId = '';
                            if (tabKey==='FESdiagramResult') fieldId = 'HistoryQueryStatSelectFESdiagramResult_Id';
                            else if (tabKey==='CommStatus') fieldId = 'HistoryQueryStatSelectCommStatus_Id';
                            else if (tabKey==='RunStatus') fieldId = 'HistoryQueryStatSelectRunStatus_Id';
                            else if (tabKey==='NumStatus') fieldId = 'HistoryQueryStatSelectNumStatus_Id';
                            if (fieldId) {
                                var input = document.getElementById(fieldId);
                                if (input) {
                                    if (e.point.selected) input.value = '';
                                    else input.value = (tabKey==='NumStatus' ? (e.point.level!==undefined?e.point.level:'') : e.point.name);
                                }
                            }
                            mini.get('deviceCombo').setValue('');
                            refreshDeviceList();
                        }
                    }
                }
            },
            exporting: { enabled:true, filename:title, fallbackToExportServer:false },
            series: [{ type:'pie', name:_loginUserLanguageResource.deviceCount, data:data }]
        });
        container._chart = chart;
    }

    function destroyPieChart(container) {
        if (!container) return;
        if (container._chart) { container._chart.destroy(); container._chart = null; }
        if (container._resizeObserver) { container._resizeObserver.disconnect(); container._resizeObserver = null; }
        if (container._resizeTimer) { clearTimeout(container._resizeTimer); container._resizeTimer = null; }
        container._pieData = null; container._pieTitle = null; container._pieTabKey = null;
    }

    function clearStatFilters() {
        var ids = ['HistoryQueryStatSelectFESdiagramResult_Id','HistoryQueryStatSelectCommStatus_Id','HistoryQueryStatSelectRunStatus_Id','HistoryQueryStatSelectNumStatus_Id','HistoryQueryStatSelectDeviceType_Id'];
        for (var i=0; i<ids.length; i++) { var el = document.getElementById(ids[i]); if (el) el.value = ''; }
    }

    function onStatTabChanged(e) {
        var tab = e.tab;
        if (!tab) return;
        clearStatFilters();
        mini.get('deviceCombo').setValue('');
        var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var orgId = window.parent && window.parent.mini ?
                window.parent.mini.get('leftOrg_Id').getValue() : '';
        loadStatData(tab, deviceTypeId,orgId);
        refreshDeviceList();
    }

    // ================================================================
    // 6. 查询与结果Tab切换
    // ================================================================
    function doQuery() {
        if (!currentDeviceId) { mini.alert('请选择设备'); return; }
        var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        
        //if (!start || !end) { mini.alert('请选择时间范围'); return; }
        var resultTabs = mini.get('resultTabs');
        var active = resultTabs.getActiveTab();
        if (!active) return;
        var name = active._name;
        if (name === 'trendCurve') {
            loadHistoryCurve();
            loadHistoryDataGrid(start, end);
        } else if (name === 'tiledDiagram') {
            tiledPage = 1;
            loadTiledDiagram(start, end);
        } else if (name === 'diagramOverlay') {
            loadOverlayDiagram(start, end);
        }
    }

    function onResultTabChanged(e) {
        var tab = e.tab;
        if (tab && currentDeviceId) doQuery();
        var vacLabel = document.getElementById('vacuateCountLabel');
        var totalLabel = document.getElementById('totalCountLabel');
        if (tab && tab.name === 'trendCurve') {
            if (vacLabel) vacLabel.style.display = 'inline';
            if (totalLabel) totalLabel.style.display = 'inline';
        } else {
            if (vacLabel) vacLabel.style.display = 'none';
            if (totalLabel) totalLabel.style.display = 'none';
        }
    }
    
     /**
      * 创建趋势曲线标签的 body HTML（包含查询工具条，时段单独一行）
      */
     function createTrendCurveBody() {
         // 第一行：日期时间 + 按钮+ 抽稀记录数
         var toolbarRow1 = '<div style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fff; border-bottom:1px solid #e8e8e8;">' +
             '<span style="font-size:12px; color:#333;">'+_loginUserLanguageResource.range+'：</span>' +
             '<input id="startDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
             '<span style="margin-left:8px; font-size:12px; color:#333;">'+_loginUserLanguageResource.timeTo+'：</span>' +
             '<input id="endDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
             '<button class="mini-button" iconCls="search" onclick="doQuery()">' + (_loginUserLanguageResource.search) + '</button>' +
             '<button class="mini-button" iconCls="export" onclick="exportData()">' + (_loginUserLanguageResource.exportData) + '</button>' +
             '<span style="flex:1;"></span>' +
             '<span id="vacuateCountLabel" style="font-size:12px; color:#999; display:none;">'+_loginUserLanguageResource.vacuateCount+'：<span id="vacuateCountSpan">0</span></span>' +
             '<input id="HistoryQueryVacuateCount_Id" type="hidden" value="" />' +
             '<span id="totalCountLabel" style="font-size:12px; color:#999; display:none;">'+_loginUserLanguageResource.totalCount+'：<span id="totalCountSpan">0</span></span>' +
             '<input id="HistoryQueryTotalCount_Id" type="hidden" value="" />' +
             '</div>';

         // 第二行：时段复选框 
         var toolbarRow2 = '<div style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fafafa; border-bottom:1px solid #e8e8e8;">' +
    '<span style="font-size:12px; color:#666;">'+_loginUserLanguageResource.timeRange+'：</span>' +
    '<input type="checkbox" id="chkAll" checked onchange="updateTimeRange(event)" /><label for="chkAll">'+_loginUserLanguageResource.all+'</label>' +
    '<input type="checkbox" id="chk1" checked name="00:00:00~06:00:00" onchange="updateTimeRange(event)" /><label for="chk1">0~6h</label>' +
    '<input type="checkbox" id="chk2" checked name="06:00:00~12:00:00" onchange="updateTimeRange(event)" /><label for="chk2">6~12h</label>' +
    '<input type="checkbox" id="chk3" checked name="12:00:00~18:00:00" onchange="updateTimeRange(event)" /><label for="chk3">12~18h</label>' +
    '<input type="checkbox" id="chk4" checked name="18:00:00~23:59:59" onchange="updateTimeRange(event)" /><label for="chk4">18~24h</label>' +
    '</div>';

         // 主体：垂直分割器（曲线 + 表格）
             var splitterHtml = '<div class="mini-splitter" style="width:100%; height:100%;" vertical="true">' +
             '<div size="50%" showCollapseButton="true" minSize="80" collapseDirection="top">' +
             '<div style="height:100%; display:flex; flex-direction:column;"><div id="historyCurveContainer" class="chart-container"><div class="loading-placeholder">请选择设备并查询</div></div></div>' +
             '</div>' +
             '<div size="50%" showCollapseButton="false" style="display:flex; flex-direction:column;">' +
             '<div style="flex:1; min-height:0; height:100%;">' +   // 新增包裹层
             '<div id="historyDataGrid" class="mini-datagrid" style="height:100%; width:100%;" ' +
             'idField="id" pageSize="'+parseInt(_defaultPageSize, 10)+'" allowResize="true" showPager="true" ' +
             'url="' + context + '/historyQueryController/getDeviceHistoryData" ' +
             'dataField="totalRoot" totalField="totalCount" ' +
             'onload="onHistoryDataLoad" onbeforeload="onHistoryDataBeforeLoad" ondrawcell="onHistoryDataDrawCell" onrowdblclick="onHistoryDataDblClick">' +
             '<div property="columns"></div>' +
             '</div>' +
             '</div>' +
             '</div>' +
             '</div>';
         return '<div style="display:flex; flex-direction:column; height:100%;">' + toolbarRow1 + toolbarRow2 + splitterHtml + '</div>';
     }

    /**
     * 创建图形平铺标签的 body HTML
     */
    function createTiledDiagramBody() {
        return '<div class="mini-splitter" style="width:100%; height:100%;" vertical="false">' +
            '<div size="25%" showCollapseButton="true" minSize="100" collapseDirection="left" collapsed="true">' +
            '<div style="height:100%; padding:4px; overflow:auto;"><div id="tiledStatGrid" class="mini-datagrid" style="width:100%; height:100%;" idField="resultCode" showPager="false" allowResize="true" url="' + context + '/historyQueryController/getDiagramTiledStat" onselectionchanged="onTiledStatSelect"><div property="columns"><div field="resultName" width="100%" headerAlign="center">工况类型</div></div></div></div>' +
            '</div>' +
            '<div size="75%">' +
            '<div style="height:100%;"><div id="tiledDiagramTabs" class="mini-tabs" style="width:100%; height:100%;" tabPosition="left" activeIndex="0" onactivechanged="onTiledTabChanged">' +
            '<div title="光杆功图" iconCls="chart" name="FSDiagram"><div id="fsTiledContainer" class="chart-container"><div class="loading-placeholder">请选择设备并查询</div></div></div>' +
            '<div title="电功图" iconCls="chart" name="PSDiagram"><div id="psTiledContainer" class="chart-container"><div class="loading-placeholder">请选择设备并查询</div></div></div>' +
            '<div title="电流图" iconCls="chart" name="ISDiagram"><div id="isTiledContainer" class="chart-container"><div class="loading-placeholder">请选择设备并查询</div></div></div>' +
            '</div></div>' +
            '</div>' +
            '</div>';
    }

    /**
     * 创建图形叠加标签的 body HTML
     */
    function createDiagramOverlayBody() {
        return '<div class="mini-splitter" style="width:100%; height:100%;" vertical="false">' +
            '<div size="25%" showCollapseButton="true" minSize="100" collapseDirection="left" collapsed="true">' +
            '<div style="height:100%; padding:4px; overflow:auto;"><div id="overlayStatGrid" class="mini-datagrid" style="width:100%; height:100%;" idField="resultCode" showPager="false" allowResize="true" url="' + context + '/historyQueryController/getDiagramOverlayStat" onselectionchanged="onOverlayStatSelect"><div property="columns"><div field="resultName" width="100%" headerAlign="center">工况类型</div></div></div></div>' +
            '</div>' +
            '<div size="75%">' +
            '<div class="mini-splitter" style="width:100%; height:100%;" vertical="false">' +
            '<div size="50%" showCollapseButton="true" minSize="150" collapseDirection="left">' +
            '<div style="height:100%; display:flex; flex-direction:column; padding:2px;"><div style="flex:1; min-height:0; margin-bottom:2px;"><div id="overlayFsChart" class="chart-container"><div class="loading-placeholder">功图叠加</div></div></div><div style="flex:1; min-height:0; margin-bottom:2px;"><div id="overlayPowerChart" class="chart-container"><div class="loading-placeholder">电功图叠加</div></div></div><div style="flex:1; min-height:0;"><div id="overlayCurrentChart" class="chart-container"><div class="loading-placeholder">电流图叠加</div></div></div></div>' +
            '</div>' +
            '<div size="50%" showCollapseButton="true" minSize="100" collapseDirection="right">' +
            '<div style="height:100%; padding:2px;"><div id="overlayDataGrid" class="mini-datagrid table-container" idField="id" pageSize="25" allowResize="true" showPager="true" url="' + context + '/historyQueryController/getDiagramOverlayData" onload="onOverlayDataLoad"><div property="columns"></div></div></div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
    }
    
    /**
     * 根据选中设备动态刷新中间标签（趋势曲线、图形平铺、图形叠加）
     * @param {Object} selected 选中的设备记录
     */
     function refreshHistoryTabs(selected) {
    	    if (!selected) return;

    	    // 从父窗口获取设备配置
    	    var deviceInfo = {};
    	    try {
    	        if (window.parent && typeof window.parent.getDeviceTabInstanceInfoByDeviceId === 'function') {
    	            deviceInfo = window.parent.getDeviceTabInstanceInfoByDeviceId(selected.id);
    	        } else {
    	            console.warn('父窗口未提供 getDeviceTabInstanceInfoByDeviceId，使用默认配置');
    	            deviceInfo = { config: { DeviceHistoryQuery: {} } };
    	        }
    	    } catch (e) {
    	        console.warn('获取设备配置失败', e);
    	        deviceInfo = { config: { DeviceHistoryQuery: {} } };
    	    }

    	    var config = deviceInfo.config || {};
    	    var deviceHistoryQuery = config.DeviceHistoryQuery || {};

    	    // 定义标签顺序（与显示顺序一致）
    	    var allowedTabKeys = [];
    	    if (deviceHistoryQuery.TrendCurve === true) allowedTabKeys.push('TrendCurve');
    	    if (deviceHistoryQuery.TiledDiagram === true) allowedTabKeys.push('TiledDiagram');
    	    if (deviceHistoryQuery.DiagramOverlay === true) allowedTabKeys.push('DiagramOverlay');
    	    if (allowedTabKeys.length === 0) allowedTabKeys.push('placeholder');

    	    var tabs = mini.get('resultTabs');
    	    if (!tabs) return;

    	    // 记录当前激活的标签名称（用于恢复）
    	    var activeTab = tabs.getActiveTab();
    	    var activeName = activeTab ? activeTab._name : null;

    	    // 移除所有现有标签
    	    var currentTabs = tabs.getTabs();
    	    for (var i = currentTabs.length - 1; i >= 0; i--) {
    	        tabs.removeTab(currentTabs[i]);
    	    }

    	    // 按顺序添加标签
    	    for (var i = 0; i < allowedTabKeys.length; i++) {
    	        var key = allowedTabKeys[i];
    	        var title = '';
    	        var body = '';
    	        var name = '';
    	        if (key === 'TrendCurve') {
    	            title = _loginUserLanguageResource.trendCurve;
    	            name = 'trendCurve';
    	            body = createTrendCurveBody();
    	        } else if (key === 'TiledDiagram') {
    	            title = _loginUserLanguageResource.tiledDiagram;
    	            name = 'tiledDiagram';
    	            body = createTiledDiagramBody();
    	        } else if (key === 'DiagramOverlay') {
    	            title = _loginUserLanguageResource.diagramOverlay;
    	            name = 'diagramOverlay';
    	            body = createDiagramOverlayBody();
    	        } else {
    	            title = _loginUserLanguageResource.emptyMsg;
    	            name = 'placeholder';
    	            body = '<div class="loading-placeholder">' + (_loginUserLanguageResource.emptyMsg) + '</div>';
    	        }

    	        var tab = {
    	            title: title,
    	            _name: name,
    	            body: body
    	        };
    	        tabs.addTab(tab);
    	    }

    	    // 恢复之前激活的标签（如果还存在）
    	    var targetTab = null;
    	    if (activeName) {
    	        var tabsList = tabs.getTabs();
    	        for (var i = 0; i < tabsList.length; i++) {
    	            if (tabsList[i]._name === activeName) {
    	                targetTab = tabsList[i];
    	                break;
    	            }
    	        }
    	    }
    	    if (!targetTab) {
    	        var tabsList = tabs.getTabs();
    	        if (tabsList.length > 0) targetTab = tabsList[0];
    	    }
    	    if (targetTab) {
    	        tabs.activeTab(targetTab);
    	        // 重新解析动态添加的 MiniUI 组件
    	        mini.parse(tabs.getTabBodyEl(targetTab));
    	        // 若当前已有设备且切换到趋势曲线标签，自动执行查询
    	        if (currentDeviceId && targetTab._name === 'trendCurve') {
    	            doQuery();
    	        }
    	    }
    	}

    // ================================================================
    // 7. 趋势曲线
    // ================================================================
    function loadHistoryCurve() {
    	if (!currentDeviceId) { mini.alert('请选择设备'); return; }
        var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        
    	
    var container = document.getElementById('historyCurveContainer');
    container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.loadingData+'</div>';
    var hours = getHistoryQueryHours();
    var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
    $.ajax({
        url: context + '/historyQueryController/getHistoryQueryCurveData',
        type: 'POST',
        data: {
            deviceId: currentDeviceId,
            deviceName: currentDeviceName,
            startDate: start,
            endDate: end,
            hours: hours,
            deviceType: deviceType,
            calculateType: currentCalculateType
        },
        dataType: 'json',
        timeout: 15000,
        success: function(result) {
            // ★ 先更新抽稀记录数和总记录数
            if (result.vacuateCount !== undefined) {
                var vacSpan = document.getElementById('vacuateCountSpan');
                if (vacSpan) vacSpan.textContent = result.vacuateCount;
                var vacLabel = document.getElementById('vacuateCountLabel');
                if (vacLabel) vacLabel.style.display = 'inline';
            }
            if (result.totalCount !== undefined) {
                var totalSpan = document.getElementById('totalCountSpan');
                if (totalSpan) totalSpan.textContent = result.totalCount;
                var totalLabel = document.getElementById('totalCountLabel');
                if (totalLabel) totalLabel.style.display = 'inline';
            }

            // 然后渲染曲线
            container.innerHTML = '<div id="historyCurveChart" style="width:100%;height:100%;"></div>';
            if (!result || !result.list || result.list.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                return;
            }
            var data = result.list;
		    var graphicSet=result.graphicSet;
		    var hiddenExceptionData=result.hiddenExceptionData;
		    
		    var timeFormat='%m-%d';
		    if(data.length>0 && result.minAcqTime.split(' ')[0]==result.maxAcqTime.split(' ')[0]){
			    timeFormat='%H:%M';
		    }
		    
		    var defaultColors=["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
		    
		    
		    
		    var tickInterval = 1;
		    tickInterval = Math.floor(data.length / 10) + 1;
		    if(tickInterval<100){
		    	tickInterval=100;
		    }
		    var title = result.deviceName+ (_loginUserLanguage.toUpperCase()=='ZH_CN'?"":" ") + _loginUserLanguageResourceFirstLower.trendCurve;
		    var xTitle=loginUserLanguageResource.acqTime;
		    var legendName =result.curveItems;
		    var legendCode =result.curveItemCodes;
		    var curveConf=result.curveConf;
		    
		    var color=[];
		    var color_l=[];
		    var color_r=[];
		    var color_all=[];
		    for(var i=0;i<curveConf.length;i++){
		    	var singleColor=defaultColors[i%defaultColors.length];
		    	if(curveConf[i].color!=''){
		    		singleColor='#'+curveConf[i].color;
		    	}
		    	color.push(singleColor);
		    	
		    	if(curveConf[i].yAxisOpposite){
		    		color_r.push(singleColor);
		    	}else{
		    		color_l.push(singleColor);
		    	}
		    }
		    
		    var series = [];
		    var series_l=[];
		    var series_r=[];
		    var yAxis= [];
		    var yAxis_l= [];
		    var yAxis_r= [];
		    
		    for (var i = 0; i < legendName.length; i++) {
		        var maxValue=null;
		        var minValue=null;
		        var allPositive=true;//全部是非负数
		        var allNegative=true;//全部是负值
		        
		        var singleSeries={};legendCode
		        singleSeries.name=legendName[i];
		        singleSeries.code=legendCode[i];
		        singleSeries.type='spline';
		        singleSeries.lineWidth=curveConf[i].lineWidth;
		        singleSeries.dashStyle=curveConf[i].dashStyle;
		        singleSeries.marker={enabled: false};
		        singleSeries.yAxis=i;
		        singleSeries.data=[];
		        for (var j = 0; j < data.length; j++) {
		        	var pointData=[];
		        	pointData.push(Date.parse(data[j].acqTime.replace(/-/g, '/')));
		        	pointData.push(data[j].data[i]);
		        	
		        	if(parseFloat(data[j].data[i])<0){
		            	allPositive=false;
		            }else if(parseFloat(data[j].data[i])>=0){
		            	allNegative=false;
		            }
		        	
		        	if(hiddenExceptionData){
		        		if(isNumber(data[j].data[i])){
		        			singleSeries.data.push(pointData);
		        		}
		        	}else{
		        		singleSeries.data.push(pointData);
		        	}
		        }
		        if(curveConf[i].yAxisOpposite){
		        	series_r.push(singleSeries);
		        }else{
		        	series_l.push(singleSeries);
		        }
		        
		        var opposite=curveConf[i].yAxisOpposite;
		        if(allNegative){
		        	maxValue=0;
		        }else if(allPositive){
		        	minValue=0;
		        }
		        if(JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.History) ){
			    	for(var j=0;j<graphicSet.History.length;j++){
			    		if(graphicSet.History[j].itemCode!=undefined && graphicSet.History[j].itemCode.toUpperCase()==result.curveItemCodes[i].toUpperCase()){
			    			if(isNotVal(graphicSet.History[j].yAxisMaxValue)){
					    		maxValue=parseFloat(graphicSet.History[j].yAxisMaxValue);
					    	}
					    	if(isNotVal(graphicSet.History[j].yAxisMinValue)){
					    		minValue=parseFloat(graphicSet.History[j].yAxisMinValue);
					    	}
					    	break;
			    		}
			    	}
			    }
		        
		        var singleAxis={
		        		max:maxValue,
		        		min:minValue,
		        		code:legendCode[i],
		        		title: {
		                    text: legendName[i],
		                    style: {
		                        color: color[i],
		                    }
		                },
		                labels: {
		                	style: {
		                        color: color[i],
		                    }
		                },
		                lineWidth: 1,
			        	tickWidth: 1,      // 刻度线宽度
		                tickLength: 5,     // 刻度线长度（可选）
		                opposite:opposite
		          };
		        if(curveConf[i].yAxisOpposite){
		        	yAxis_r.push(singleAxis);
		        }else{
		        	yAxis_l.push(singleAxis);
		        }
		        
		    }
		    
		    for(var i=yAxis_l.length-1;i>=0;i--){
		    	yAxis.push(yAxis_l[i]);
		    }
		    for(var i=0;i<yAxis_r.length;i++){
		    	yAxis.push(yAxis_r[i]);
		    }
		    
		    for(var i=0;i<series_l.length;i++){
		    	series_l[i].yAxis=series_l.length-1-i;
		    	series.push(series_l[i]);
		    }
		    for(var i=0;i<series_r.length;i++){
		    	series_r[i].yAxis=series_l.length+i;
		    	series.push(series_r[i]);
		    }
		    
		    for(var i=0;i<color_l.length;i++){
		    	color_all.push(color_l[i]);
		    }
		    for(var i=0;i<color_r.length;i++){
		    	color_all.push(color_r[i]);
		    }
		    initDeviceHistoryCurveChartFn(series, tickInterval, "historyCurveContainer", title, '', '', yAxis, color_all,true,timeFormat);
        },
        error: function() {
            container.innerHTML = '<div class="loading-placeholder error">'+_loginUserLanguageResource.requestFailed+'</div>';
        }
    });
}

    function initDeviceHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color, legend, timeFormat) {
        if ($("#"+divId).length===0) return;
        var chart = new Highcharts.Chart({
            chart: { renderTo: divId, type:'spline', animation:false, zoomType:'xy', zooming:{ mouseWheel:{ enabled:false } } },
            time: { timezoneOffset: new Date().getTimezoneOffset() },
            credits: { enabled:false },
            title: { text: title, style:{ fontSize: chartTitleFontSize||'14px' } },
            subtitle: { text: subtitle },
            colors: color,
            xAxis: {
                type:'datetime', title:{ text: xtitle },
                tickPixelInterval: 120,
                labels: {
                    formatter: function() { return this.axis.chart.time.dateFormat(timeFormat, this.value); },
                    rotation: -45
                }
            },
            yAxis: yAxis,
            tooltip: { crosshairs:true, shared:true, style:{ color:'#333', fontSize:'12px' } },
            exporting: {
                enabled: true,
                filename: title,
                fallbackToExportServer: false,
                buttons: {
                    contextButton: {
                        menuItems: [
                            'viewFullscreen','printChart',
                            //'separator',
                            'downloadPNG','downloadJPEG','downloadSVG',
                            //'separator',
                            'downloadCSV','downloadXLS',
                            //'separator',
                            {
                                text: _loginUserLanguageResource.diagramSet,
                                onclick: function() { openCurveSetWindow(); }
                            }
                        ]
                    }
                }
            },
            plotOptions: { spline: { lineWidth:1, marker:{ enabled:true, radius:3 }, shadow:true } },
            legend: { layout:'horizontal', align:'center', verticalAlign:'bottom', enabled: legend!==false },
            series: series
        });
    }

    // 打开曲线设置窗口
    function openCurveSetWindow() {
        if (!currentDeviceId) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }
        var params = {
            deviceId: currentDeviceId,
            deviceName: currentDeviceName,
            deviceType: currentLevel2 ? currentLevel2.deviceTypeId : '0'
        };
        mini.open({
            title: _loginUserLanguageResource.historyDiagramSet,
            url: context + '/miniui-app/modules/historyQuery/historyCurveSet.jsp',
            width: '50%',
            height: '60%',
            modal: true,
            allowResize: true,
            onload: function() {
                var iframe = this.getIFrameEl();
                iframe.contentWindow.setData(params);
             // ★★★ 将 doQuery 函数挂载到子窗口，方便调用 ★★★
                iframe.contentWindow._parentLoadHistoryCurve = loadHistoryCurve;
                iframe.contentWindow._parentShowAlert = window.showAlert;
            },
            ondestroy: function() {
                // 关闭后可选额外操作
            }
        });
    }
    
    showAlert = function(message, title) {
        mini.alert(message, title);
    };

    // ================================================================
    // 8. 历史数据表格
    // ================================================================
    function loadHistoryDataGrid(start, end) {
        var grid = mini.get('historyDataGrid');
        if (!grid) return;
        var hours = getHistoryQueryHours();
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        grid.load({ deviceId: currentDeviceId, startDate: start, endDate: end, hours: hours, deviceType: deviceType, calculateType: currentCalculateType });
    }

    function onHistoryDataLoad(e) {
        var grid = e.sender, result = e.result;
        if (result && result.columns) {
            var cols = [];
            for (var i=0; i<result.columns.length; i++) {
                var col = result.columns[i];
                var column = { field: col.dataIndex, header: col.header, headerAlign:'center', align:'center', width: col.width||100 };
                if (col.dataIndex==='id') { column.type='indexcolumn'; column.width=40; column.header=_loginUserLanguageResource.idx; delete column.field; }
                else if (col.dataIndex==='acqTime') { column.dateFormat='yyyy-MM-dd HH:mm:ss'; column.width=150; }
                cols.push(column);
            }
            
         	// ★ 只定义列的基本结构，不设置 renderer
            var detailColumn = {
                field: 'details',
                header: _loginUserLanguageResource.details,
                width: 60,
                headerAlign:'center',
                align: 'center'
            };
            // 插入到索引 1
            cols.splice(1, 0, detailColumn);
            
            grid.setColumns(cols);
            document.getElementById('HistoryQueryDataColumnStr_Id').value = JSON.stringify(result.columns);
            
            var startDate=mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            if(startDate==''||null==startDate){
            	mini.get('startDate').setValue(result.start_date);
            }
            var endDate=mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            if(endDate==''||null==endDate){
            	mini.get('endDate').setValue(result.end_date);
            }
        }
    }
    
    /**
     * 显示历史数据详情（目前为占位实现）
     * @param {string} params - 经过 URL 编码的 JSON 参数对象
     */
     function showHistoryDetail(params) {
    	    var obj = JSON.parse(decodeURIComponent(params));
    	    // 补充 deviceType（从当前上下文中获取）
    	    obj.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

    	    mini.open({
    	        title: _loginUserLanguageResource.detailsData,
    	        url: context + '/miniui-app/modules/historyQuery/historyDetail.jsp',
    	        width: '80%',
    	        height: '80%',
    	        modal: true,
    	        allowResize: true,
    	        onload: function() {
    	            var iframe = this.getIFrameEl();
    	            iframe.contentWindow.setData(obj);
    	        }
    	    });
    	}
    
    /**
     * 历史数据表格绘制单元格
     * 参照实时监控的 onDeviceGridDrawCell 逻辑
     */
    function onHistoryDataDrawCell(e) {
        var record = e.record;
        var field = e.field;
        var value = e.value;
        if (!record || !field) return;

        // 获取报警样式配置（从隐藏域读取）
        var alarmShowStyle = getAlarmShowStyle() || {};
        var Data = alarmShowStyle.Data || {};
        var Comm = alarmShowStyle.Comm || {};
        var Run = alarmShowStyle.Run || {};
        var alarmInfo = record.alarmInfo || [];
        var fieldUpper = field.toUpperCase();

        if (field === 'details') {
            if (!record) {
                e.cellHtml = '';
                return;
            }
            var recordId = record.id || '';
            var deviceId = record.deviceId || '';
            var deviceName = record.deviceName || record.wellName || '';
            var calculateType = record.calculateType !== undefined ? record.calculateType : 0;
            if (!recordId && !deviceId) {
                e.cellHtml = '';
                return;
            }
         	// ★ 获取当前查询的时间范围
            var startDate = mini.get('startDate') ? mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';
            var endDate = mini.get('endDate') ? mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';
            var params = encodeURIComponent(JSON.stringify({
                recordId: recordId,
                deviceId: deviceId,
                deviceName: deviceName,
                calculateType: calculateType,
                startDate: startDate,
                endDate: endDate
            }));
            e.cellHtml = '<a href="javascript:void(0)" onclick="showHistoryDetail(\'' + params + '\')" style="text-decoration:none;">' + (_loginUserLanguageResource.details) + '...</a>';
            return;
        }
        
        // ---- 通信状态列 ----
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

        // ---- 运行状态列 ----
        if (fieldUpper === 'RUNSTATUSNAME') {
            var commStatus = record.commStatus;
            var runStatus = record.runStatus;
            if (commStatus == 0 || commStatus == 2 || !value) {
                e.cellHtml = ''; // 离线或无数据时不显示
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

        // ---- 其他数据列（报警高亮） ----
        // 排除 id、设备名、状态、时间等已处理的列
        if (fieldUpper !== 'ID' && fieldUpper !== 'DEVICENAME' && fieldUpper !== 'WELLNAME' &&
            fieldUpper !== 'COMMSTATUSNAME' && fieldUpper !== 'RUNSTATUSNAME' && fieldUpper !== 'ACQTIME') {
            var alarmLevel = 0;
            // 在 alarmInfo 中查找匹配的字段（忽略大小写）
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
            // 如果 value 为空，显示占位符
            e.cellHtml = value !== undefined && value !== null ? value : '';
            return;
        }

        // 默认：直接显示值
        e.cellHtml = (value !== undefined && value !== null) ? value : '';
    }

    function onHistoryDataDblClick(e) {
        var record = e.record;
        if (!record) return;
        // 弹出数据详情窗口
        openHistoryDataDetails(record.id, record.deviceId, record.wellName, record.calculateType);
    }

    function openHistoryDataDetails(recordId, deviceId, deviceName, calculateType) {
        var win = new mini.Window();
        win.set({
            title: _loginUserLanguageResource.detailsData,
            width: '80%', height: '80%',
            modal: true, showHeader: true, allowResize: true
        });
        win.show();
        var containerId = 'detailsContainer_'+Date.now();
        win.setBody('<div id="'+containerId+'" style="width:100%;height:100%;"></div>');
        // 加载详情数据
        loadHistoryDetailsData(containerId, recordId, deviceId, deviceName, calculateType, win);
    }

    function loadHistoryDetailsData(containerId, recordId, deviceId, deviceName, calculateType, win) {
        var mask = mini.mask({ el: win.getBodyEl(), cls:'mini-mask-loading', html:_loginUserLanguageResource.loadingData });
        var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        $.ajax({
            url: context + '/historyQueryController/getDeviceHistoryDetailsData',
            type: 'POST',
            data: {
                recordId: recordId,
                deviceId: deviceId,
                deviceName: deviceName,
                calculateType: calculateType,
                deviceType: deviceType,
                startDate: start,
                endDate: end
            },
            dataType: 'json',
            timeout: 15000,
            success: function(result) {
                mini.unmask(win.getBodyEl());
                var container = document.getElementById(containerId);
                if (!container) return;
                // 使用 Handsontable 或 MiniUI Grid，这里用 MiniUI Grid 简化
                var data = result.totalRoot || [];
                var cellInfo = result.CellInfo || [];
                if (data.length===0) {
                    container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                    return;
                }
                // 构建列
                var cols = [];
                for (var i=0; i<3; i++) {
                    cols.push({ field: 'name'+(i+1), width: '16%', align:'center' });
                    cols.push({ field: 'value'+(i+1), width: '16%', align:'center' });
                }
                var grid = new mini.DataGrid();
                grid.set({
                    style: 'width:100%; height:100%;',
                    showPager: false,
                    allowAlternating: true,
                    data: data,
                    columns: cols,
                    ondrawcell: function(e) {
                        // 可添加报警颜色
                    }
                });
                grid.render(container);
                // 合并第一行
                setTimeout(function() {
                    try { grid.mergeCells([{ rowIndex:0, columnIndex:0, rowSpan:1, colSpan:6 }]); } catch(e) {}
                }, 100);
                // 添加导出按钮
                var toolbar = document.createElement('div');
                toolbar.style.cssText = 'padding:4px 8px; background:#f5f5f5; border-bottom:1px solid #ddd; display:flex; align-items:center;';
                toolbar.innerHTML = '<span style="flex:1;"></span><button class="mini-button" onclick="exportDetailsData(\''+recordId+'\',\''+deviceId+'\',\''+deviceName+'\','+calculateType+')">'+_loginUserLanguageResource.exportData+'</button>';
                container.parentNode.insertBefore(toolbar, container);
                mini.parse();
            },
            error: function() { mini.unmask(win.getBodyEl()); mini.alert(_loginUserLanguageResource.requestFailed); }
        });
    }

    window.exportDetailsData = function(recordId, deviceId, deviceName, calculateType) {
    	var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var key = 'exportDetails_'+recordId+'_'+Date.now();
        var url = context + '/historyQueryController/exportDeviceHistoryQueryDetailsData';
        var param = '&recordId='+recordId+'&deviceId='+deviceId+'&deviceName='+encodeURIComponent(encodeURIComponent(deviceName))+
                    '&calculateType='+calculateType+'&deviceType='+(currentLevel2?currentLevel2.deviceTypeId:'0')+
                    '&startDate='+encodeURIComponent(start)+'&endDate='+encodeURIComponent(end)+'&key='+key;
        exportDataMask(key, document.body, _loginUserLanguageResource.loadingData);
        openExcelWindow(url+'?flag=true'+param);
    };

    // ================================================================
    // 9. 图形平铺
    // ================================================================
    function loadTiledDiagram(start, end) {
        var tabs = mini.get('tiledDiagramTabs');
        var active = tabs.getActiveTab();
        var type = active ? active.name : 'FSDiagram';
        var containerId = '';
        if (type === 'FSDiagram') containerId = 'fsTiledContainer';
        else if (type === 'PSDiagram') containerId = 'psTiledContainer';
        else if (type === 'ISDiagram') containerId = 'isTiledContainer';
        if (!containerId) return;
        var container = document.getElementById(containerId);
        container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.loadingData+'</div>';
        var hours = getHistoryQueryHours();
        var statGrid = mini.get('tiledStatGrid');
        var selected = statGrid.getSelected();
        var resultCode = selected ? selected.resultCode : '';
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var limit = defaultGraghSize || 20;
        var startIdx = (tiledPage-1)*limit;
        $.ajax({
            url: context + '/historyQueryController/getDiagramTiledData',
            type: 'POST',
            data: {
                deviceId: currentDeviceId,
                deviceName: currentDeviceName,
                startDate: start,
                endDate: end,
                hours: hours,
                diagramType: type,
                resultCode: resultCode,
                deviceType: deviceType,
                calculateType: currentCalculateType,
                start: startIdx,
                limit: limit
            },
            dataType: 'json',
            success: function(result) {
                container.innerHTML = '';
                var list = result.list || [];
                if (list.length===0) {
                    container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                    return;
                }
                // 计算布局
                var panelWidth = container.clientWidth;
                var scrollWidth = getScrollWidth();
                var columnCount = Math.max(1, Math.floor((panelWidth - scrollWidth) / graghMinWidth));
                var itemWidth = (panelWidth - scrollWidth) / columnCount - 1;
                var itemHeight = itemWidth * diagramAspectRatio;
                var gridDiv = document.createElement('div');
                gridDiv.style.cssText = 'display:flex; flex-wrap:wrap; width:100%; align-content:flex-start;';
                container.appendChild(gridDiv);
                for (var i=0; i<list.length; i++) {
                    var item = list[i];
                    var divId = 'tiled_'+type+'_'+item.id+'_'+i;
                    var itemDiv = document.createElement('div');
                    itemDiv.style.cssText = 'flex:0 0 '+itemWidth+'px; height:'+itemHeight+'px; padding:2px; box-sizing:border-box; min-height:150px;';
                    var chartDiv = document.createElement('div');
                    chartDiv.id = divId;
                    chartDiv.style.cssText = 'width:100%; height:100%;';
                    itemDiv.appendChild(chartDiv);
                    gridDiv.appendChild(itemDiv);
                    // 根据类型绘制
                    if (type === 'FSDiagram') showSurfaceCard(item, divId);
                    else if (type === 'PSDiagram') showPSDiagram(item, divId);
                    else if (type === 'ISDiagram') showASDiagram(item, divId);
                }
                // 更新分页信息
                totalTiledPages = result.totalPages || 1;
                if (tiledPage < totalTiledPages) {
                    // 监听滚动加载更多
                    var scrollable = mini.get(containerId).getScrollable();
                    if (scrollable) {
                        scrollable.on('scroll', function() {
                            var pos = this.getPosition();
                            var maxScroll = this.getSize().y - this.getClientSize().y;
                            if (maxScroll>0 && pos.y/maxScroll > 0.8 && tiledPage < totalTiledPages) {
                                tiledPage++;
                                loadTiledDiagram(start, end);
                            }
                        });
                    }
                }
            },
            error: function() {
                container.innerHTML = '<div class="loading-placeholder error">'+_loginUserLanguageResource.requestFailed+'</div>';
            }
        });
    }

    function onTiledStatSelect(e) {
        if (currentDeviceId) doQuery();
    }

    function onTiledTabChanged(e) {
        if (currentDeviceId) doQuery();
    }

    // ================================================================
    // 10. 图形叠加
    // ================================================================
    function loadOverlayDiagram(start, end) {
        var hours = getHistoryQueryHours();
        var statGrid = mini.get('overlayStatGrid');
        var selected = statGrid.getSelected();
        var resultCode = selected ? selected.resultCode : '';
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        // 加载三个图表
        loadOverlayChart('overlayFsChart', 'FSDiagram', start, end, hours, resultCode);
        loadOverlayChart('overlayPowerChart', 'PSDiagram', start, end, hours, resultCode);
        loadOverlayChart('overlayCurrentChart', 'ISDiagram', start, end, hours, resultCode);
        // 加载数据表格
        var grid = mini.get('overlayDataGrid');
        if (grid) {
            grid.load({
                deviceId: currentDeviceId,
                deviceName: currentDeviceName,
                startDate: start,
                endDate: end,
                hours: hours,
                resultCode: resultCode,
                deviceType: deviceType,
                calculateType: currentCalculateType
            });
        }
    }

    function loadOverlayChart(divId, diagramType, start, end, hours, resultCode) {
        var container = document.getElementById(divId);
        container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.loadingData+'</div>';
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        $.ajax({
            url: context + '/historyQueryController/getDiagramOverlayData',
            type: 'POST',
            data: {
                deviceId: currentDeviceId,
                deviceName: currentDeviceName,
                startDate: start,
                endDate: end,
                hours: hours,
                diagramType: diagramType,
                resultCode: resultCode,
                deviceType: deviceType,
                calculateType: currentCalculateType
            },
            dataType: 'json',
            timeout: 15000,
            success: function(result) {
                container.innerHTML = '';
                if (!result || !result.totalRoot || result.totalRoot.length===0) {
                    container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                    return;
                }
                var chartDiv = document.createElement('div');
                chartDiv.style.cssText = 'width:100%; height:100%;';
                var chartId = divId + '_chart';
                chartDiv.id = chartId;
                container.appendChild(chartDiv);
                // 使用 showFSDiagramOverlayChart 或自定义
                var visible = true;
                if (diagramType === 'FSDiagram') {
                    showFSDiagramOverlayChart(result, chartId, visible, 0);
                } else if (diagramType === 'PSDiagram') {
                    showFSDiagramOverlayChart(result, chartId, visible, 1);
                } else if (diagramType === 'ISDiagram') {
                    showFSDiagramOverlayChart(result, chartId, visible, 2);
                }
            },
            error: function() {
                container.innerHTML = '<div class="loading-placeholder error">'+_loginUserLanguageResource.requestFailed+'</div>';
            }
        });
    }

    function onOverlayStatSelect(e) {
        if (currentDeviceId) doQuery();
    }

    function onOverlayDataLoad(e) {
        var grid = e.sender, result = e.result;
        if (result && result.columns) {
            var cols = [];
            for (var i=0; i<result.columns.length; i++) {
                var col = result.columns[i];
                var column = { field: col.dataIndex, header: col.header, headerAlign:'center', align:'center', width: col.width||100 };
                if (col.dataIndex==='id') { column.type='indexcolumn'; column.width=40; column.header=_loginUserLanguageResource.idx; delete column.field; }
                else if (col.dataIndex==='acqTime') { column.dateFormat='yyyy-MM-dd HH:mm:ss'; column.width=150; }
                cols.push(column);
            }
            grid.setColumns(cols);
            document.getElementById('HistoryQueryDiagramOverlayColumnStr_Id').value = JSON.stringify(result.columns);
        }
        // 更新抽稀记录数
        if (result) {
            document.getElementById('HistoryFESDiagramVacuateCount_Id').value = result.vacuateCount || 0;
        }
    }

    // ================================================================
    // 11. 导出功能
    // ================================================================
    function exportDeviceList() {
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var dictDeviceType = deviceType;
        if (deviceType.indexOf(',')>-1) dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
        var deviceName = mini.get('deviceCombo') ? mini.get('deviceCombo').getValue() : '';
        var FESdiagramResultStatValue = document.getElementById('HistoryQueryStatSelectFESdiagramResult_Id').value;
        var commStatusStatValue = document.getElementById('HistoryQueryStatSelectCommStatus_Id').value;
        var runStatusStatValue = document.getElementById('HistoryQueryStatSelectRunStatus_Id').value;
        var numStatusStatValue = document.getElementById('HistoryQueryStatSelectNumStatus_Id').value;
        var deviceTypeStatValue = document.getElementById('HistoryQueryStatSelectDeviceType_Id').value;
        
        var fileName = _loginUserLanguageResource.historyQueryDeviceList;
        var title = fileName;
        
     // 7. 构建 fields / heads
     	var columnStrInput = document.getElementById('HistoryQueryWellListColumnStr_Id');
        if (!columnStrInput || !columnStrInput.value) {
             mini.alert('表格列配置未加载，请刷新页面重试');
             return;
       }
       var columnStr = columnStrInput.value;
        var fields = '',
            heads = '';
        try {
            var columns = JSON.parse(columnStr);
            var lockedfields = '',
                lockedheads = '',
                unlockedfields = '',
                unlockedheads = '';
            columns.forEach(function(col) {
                if (col.hidden || col.dataIndex === 'id') return;
                var dataIndex = col.dataIndex || col.field;
                var header = col.header || col.text || col.title || dataIndex;
                if (col.locked) {
                    lockedfields += dataIndex + ',';
                    lockedheads += header + ',';
                } else {
                    unlockedfields += dataIndex + ',';
                    unlockedheads += header + ',';
                }
            });
            if (lockedfields) {
                lockedfields = lockedfields.slice(0, -1);
                lockedheads = lockedheads.slice(0, -1);
            }
            if (unlockedfields) {
                unlockedfields = unlockedfields.slice(0, -1);
                unlockedheads = unlockedheads.slice(0, -1);
            }
            fields = 'id' + (lockedfields ? ',' + lockedfields : '') + (unlockedfields ? ',' + unlockedfields : '');
            heads = (_loginUserLanguageResource.idx) + (lockedheads ? ',' + lockedheads : '') + (unlockedheads ? ',' + unlockedheads : '');
        } catch (e) {
            mini.alert(_loginUserLanguageResource.operationFailed);
            return;
        }
        
        var key = 'exportHistoryDeviceList_'+deviceType+'_'+Date.now();
        var url = context + '/historyQueryController/exportHistoryQueryDeviceListExcel';
        
        var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) +
        			'&orgId='+orgId+
        			'&deviceType='+deviceType+'&dictDeviceType='+dictDeviceType+
                    '&deviceName='+encodeURIComponent(encodeURIComponent(deviceName))+
                    '&FESdiagramResultStatValue='+encodeURIComponent(encodeURIComponent(FESdiagramResultStatValue))+
                    '&commStatusStatValue='+encodeURIComponent(encodeURIComponent(commStatusStatValue))+
                    '&runStatusStatValue='+encodeURIComponent(encodeURIComponent(runStatusStatValue))+
                    '&numStatusStatValue='+encodeURIComponent(encodeURIComponent(numStatusStatValue))+
                    '&deviceTypeStatValue='+encodeURIComponent(encodeURIComponent(deviceTypeStatValue))+
                    '&fileName='+encodeURIComponent(encodeURIComponent(fileName))+
                    '&title='+encodeURIComponent(encodeURIComponent(title))+
                    '&key='+key;
        var fullUrl = url + '?flag=true' + param;
        exportDataMask(key, document.querySelector('.device-grid-wrapper'), _loginUserLanguageResource.loadingData);
        openExcelWindow(fullUrl);
    }

    function exportData() {
        if (!currentDeviceId) { mini.alert('请选择设备'); return; }
        var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        if (!start || !end) { mini.alert('请选择时间范围'); return; }
        var resultTabs = mini.get('resultTabs');
        var active = resultTabs.getActiveTab();
        var name = active ? active._name : '';
        var key = 'exportHistory_'+currentDeviceId+'_'+Date.now();
        var url = '';
        var param = '';
        var hours = getHistoryQueryHours();
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        if (name === 'trendCurve') {
            url = context + '/historyQueryController/exportHistoryQueryDataExcel';
            var columnStr = document.getElementById('HistoryQueryDataColumnStr_Id').value;
            param = '&orgId='+(window.parent&&window.parent.mini?window.parent.mini.get('leftOrg_Id').getValue():'')+
                    '&deviceType='+deviceType+'&deviceId='+currentDeviceId+'&deviceName='+encodeURIComponent(encodeURIComponent(currentDeviceName))+
                    '&calculateType='+currentCalculateType+'&startDate='+encodeURIComponent(start)+'&endDate='+encodeURIComponent(end)+
                    '&hours='+hours+'&fileName='+encodeURIComponent(encodeURIComponent(currentDeviceName+_loginUserLanguageResource.historyData))+
                    '&title='+encodeURIComponent(encodeURIComponent(currentDeviceName+_loginUserLanguageResource.historyData))+
                    '&key='+key;
        } else if (name === 'tiledDiagram') {
            var type = mini.get('tiledDiagramTabs').getActiveTab().name;
            var statGrid = mini.get('tiledStatGrid');
            var selected = statGrid.getSelected();
            var resultCode = selected ? selected.resultCode : '';
            url = context + '/historyQueryController/exportHistoryQueryFESDiagramDataExcel';
            param = '&orgId='+(window.parent&&window.parent.mini?window.parent.mini.get('leftOrg_Id').getValue():'')+
                    '&deviceType='+deviceType+'&deviceId='+currentDeviceId+'&deviceName='+encodeURIComponent(encodeURIComponent(currentDeviceName))+
                    '&resultCode='+resultCode+'&startDate='+encodeURIComponent(start)+'&endDate='+encodeURIComponent(end)+
                    '&hours='+hours+'&diagramType='+type+'&fileName='+encodeURIComponent(encodeURIComponent(currentDeviceName+'-'+_loginUserLanguageResource.FSDiagramData))+
                    '&title='+encodeURIComponent(encodeURIComponent(currentDeviceName+'-'+_loginUserLanguageResource.FSDiagramData))+
                    '&key='+key;
        } else if (name === 'diagramOverlay') {
            url = context + '/historyQueryController/exportHistoryQueryFESDiagramOverlayDataExcel';
            var statGrid2 = mini.get('overlayStatGrid');
            var selected2 = statGrid2.getSelected();
            var resultCode2 = selected2 ? selected2.resultCode : '';
            var columnStr2 = document.getElementById('HistoryQueryDiagramOverlayColumnStr_Id').value;
            param = '&orgId='+(window.parent&&window.parent.mini?window.parent.mini.get('leftOrg_Id').getValue():'')+
                    '&deviceType='+deviceType+'&deviceId='+currentDeviceId+'&deviceName='+encodeURIComponent(encodeURIComponent(currentDeviceName))+
                    '&resultCode='+resultCode2+'&calculateType='+currentCalculateType+
                    '&startDate='+encodeURIComponent(start)+'&endDate='+encodeURIComponent(end)+
                    '&hours='+hours+'&fileName='+encodeURIComponent(encodeURIComponent(currentDeviceName+'-'+_loginUserLanguageResource.FSDiagramOverlayData))+
                    '&title='+encodeURIComponent(encodeURIComponent(currentDeviceName+'-'+_loginUserLanguageResource.FSDiagramOverlayData))+
                    '&key='+key;
        } else {
            mini.alert('当前Tab不支持导出');
            return;
        }
        exportDataMask(key, document.querySelector('.right-panel'), _loginUserLanguageResource.loadingData);
        openExcelWindow(url+'?flag=true'+param);
    }

    // ================================================================
    // 12. 时间范围辅助
    // ================================================================
    function getHistoryQueryHours() {
        var all = document.getElementById('chkAll');
        if (all && all.checked) return 'all';
        var hours = [];
        ['chk1','chk2','chk3','chk4'].forEach(function(id) {
            var chk = document.getElementById(id);
            if (chk && chk.checked) hours.push(chk.name);
        });
        return hours.length>0 ? hours.join(',') : 'all';
    }

    function updateTimeRange(e) {
        var target = e ? e.target : null;
        var all = document.getElementById('chkAll');
        var chk1 = document.getElementById('chk1');
        var chk2 = document.getElementById('chk2');
        var chk3 = document.getElementById('chk3');
        var chk4 = document.getElementById('chk4');
        if (!all) return;

        // 如果触发源是“全部”复选框，则同步所有子复选框
        if (target && target.id === 'chkAll') {
            if (all.checked) {
                if (chk1) chk1.checked = true;
                if (chk2) chk2.checked = true;
                if (chk3) chk3.checked = true;
                if (chk4) chk4.checked = true;
            } else {
                //if (chk1) chk1.checked = false;
                //if (chk2) chk2.checked = false;
                //if (chk3) chk3.checked = false;
                //if (chk4) chk4.checked = false;
            }
        } else {
            // 子复选框变化，只更新“全部”的选中状态，不改变子复选框
            var allChecked = chk1 && chk1.checked && chk2 && chk2.checked && chk3 && chk3.checked && chk4 && chk4.checked;
            all.checked = allChecked;
        }

        if (currentDeviceId) doQuery();
    }
    
    function refreshData() {
        if (currentLevel2) {
            var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            clearStatFilters();
            // 如果有选中设备，刷新中间标签
            if (currentDeviceId) {
                var grid = mini.get('deviceGrid');
                var selected = grid ? grid.getSelected() : null;
                if (selected) {
                    refreshHistoryTabs(selected);
                }
            }
            // 刷新统计图表和设备列表
            var deviceTypeId = currentLevel2.deviceTypeId || '0';
            loadStatCharts(deviceTypeId, orgId);
            refreshDeviceList();
        }
    }
    
    window.refreshHistoryCurve = function() {
        if (typeof doQuery === 'function') {
            doQuery();
        } else {
            console.warn('doQuery 未定义，无法刷新曲线');
        }
    };

    // ================================================================
    // 13. 初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        buildLevel1Tabs();
        
        initI18n();
        
        var grid = mini.get('deviceGrid');
        if (grid && typeof _defaultPageSize !== 'undefined' && _defaultPageSize) {
            grid.setPageSize(parseInt(_defaultPageSize, 10));
        }
        
     // 清空 resultTabs 并添加占位
        var resultTabs = mini.get('resultTabs');
        if (resultTabs) {
            var current = resultTabs.getTabs();
            for (var i = current.length - 1; i >= 0; i--) {
                resultTabs.removeTab(current[i]);
            }
            resultTabs.addTab({
                title: _loginUserLanguageResource.emptyMsg,
                body: '<div class="loading-placeholder">' + (_loginUserLanguageResource.emptyMsg) + '</div>',
                _name: 'placeholder'
            });
        }
        
     // ===== 监听父页面消息 =====
        window.addEventListener('message', function(event) {
            var message = event.data;
            if (!message || !message.action) {
                return;
            }

            switch (message.action) {
                // ---- 父页面刷新指令（切换组织/功能标签） ----
                case 'refresh':
                    console.log('历史查询模块收到刷新指令, orgId:', message.orgId);
                    // 清空统计筛选条件（与实时监控保持一致）
                    clearStatFilters();
                    // 清空设备下拉框（可选）
                    mini.get('deviceCombo').setValue('');
                    // 如果传递了组织ID，可在此处理（如重新加载设备列表）
                    if (message.orgId) {
                        // 可选的：处理组织切换逻辑，例如重新加载统计图表或设备列表
                        // 目前无需额外操作，因为设备列表加载时会从父页面重新获取 orgId
                    }
                    // 执行刷新（若已选择设备则重新查询，否则仅刷新设备列表）
                    if (typeof refreshData === 'function') {
                        refreshData();
                    }
                    break;
                case 'refreshCurve':
                	alert('refreshCurve');
                	doQuery();
                    break;
            }
        });
        console.log('历史查询模块加载完成');
    });

    // 暴露全局函数
    window.doQuery = doQuery;
    window.exportData = exportData;
    window.refreshDeviceList = refreshDeviceList;
    window.selectLevel1 = selectLevel1;
    window.selectLevel2 = selectLevel2;
    window.onDeviceSelect = onDeviceSelect;
    window.onDeviceComboChange = onDeviceComboChange;
    window.updateTimeRange = updateTimeRange;
    window.onStatTabChanged = onStatTabChanged;
    window.onResultTabChanged = onResultTabChanged;
    window.onTiledStatSelect = onTiledStatSelect;
    window.onTiledTabChanged = onTiledTabChanged;
    window.onOverlayStatSelect = onOverlayStatSelect;
    window.onDeviceGridBeforeLoad = onDeviceGridBeforeLoad;
    window.onDeviceGridLoad = onDeviceGridLoad;
    window.onDeviceGridDrawCell = onDeviceGridDrawCell;
    window.onHistoryDataLoad = onHistoryDataLoad;
    window.onHistoryDataDblClick = onHistoryDataDblClick;
    window.onOverlayDataLoad = onOverlayDataLoad;
    window.exportDeviceList = exportDeviceList;
    window.openCurveSetWindow = openCurveSetWindow;
</script>
</body>
</html>