<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String moduleId = request.getParameter("moduleId");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>报警查询</title>
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

        .alarm-container {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #fff;
        }

        .level1-footer {
            flex-shrink: 0;
            background: #fafafa;
            border-top: 1px solid #e0e0e0;
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

        .level1-footer .tab-item:hover {
            color: #333;
        }

        .level1-footer .tab-item.active {
            color: #2d6a9f;
            font-weight: bold;
            border-bottom-color: #2d6a9f;
        }

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

        .level2-sidebar .tab-item:hover {
            background: #e8ecf0;
            color: #333;
        }

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

        .left-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #f0f2f5;
            padding: 4px;
        }

        .device-overview-area {
            flex: 1;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
            display: flex;
            flex-direction: column;
        }

        .stat-chart-area {
            flex: 1;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
            display: flex;
            flex-direction: column;
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
        }

        /* 确保 Tabs 主体区域能 flex 伸展 */
.right-panel .mini-tabs .mini-tab-body {
    overflow: hidden !important;
    padding: 0 !important;
    margin: 0 !important;
    display: flex !important;
    flex-direction: column !important;
    flex: 1 !important;      /* 新增：撑满剩余高度 */
    min-height: 0 !important; /* 防止 flex 溢出 */
}

/* 确保 Tabs 容器自身也能 flex 伸展 */
#alarmDetailTabs {
    flex: 1;
    overflow: hidden;
    min-height: 0;
}

        .chart-container {
            width: 100%;
            height: 100%;
            min-height: 200px;
        }

        .loading-placeholder {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100%;
            color: #999;
            font-size: 13px;
            flex-direction: column;
        }

        .loading-placeholder.error {
            color: #ff4d4f;
        }

        .mini-toolbar .separator {
            width: 1px;
            height: 20px;
            background: #ddd;
            margin: 0 4px;
        }
        

        .alarm-badge {
            display: inline-block;
            border-radius: 10px;
            padding: 0 4px;
            min-width: 14px;
            height: 14px;
            line-height: 14px;
            text-align: center;
            font-size: 9px;
            font-weight: bold;
            margin-right: 2px;
            vertical-align: middle;
            box-sizing: border-box;
        }

        /* 报警级别颜色（概览表格） */
        .alarm-level-100 {
            background-color: #dc2828;
            color: #fff;
        }

        .alarm-level-200 {
            background-color: #f09614;
            color: #fff;
        }

        .alarm-level-300 {
            background-color: #fae600;
            color: #333;
        }

    </style>
</head>

<body>
    <div class="alarm-container">
        <!-- 主区域 -->
        <div style="display:flex; flex:1; overflow:hidden; order:0;">
            <!-- 二级标签 -->
            <div class="level2-sidebar" id="level2Sidebar">
                <div class="no-child-tip">选择一级</div>
            </div>
            <!-- 内容区域 -->
            <div style="flex:1; overflow:hidden;">
                <div class="mini-splitter" style="width:100%; height:100%;" vertical="false">
                    <!-- 左侧：设备概览 + 统计图表（垂直分割） -->
                    <div size="45%" showCollapseButton="false" minSize="200">
                        <div class="left-panel" style="height:100%; background:#f0f2f5; padding:4px; display:flex; flex-direction:column; overflow:hidden;">
                            <div class="mini-splitter" style="width:100%; height:100%;" vertical="true">
                                <!-- 设备概览表格 -->
                                <div id="alarmOverviewPanel" size="50%" showCollapseButton="false" minSize="120">
                                    <div class="device-overview-area" style="height:100%;">
                                        <!-- 第一行工具栏 -->
                                        <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:4px 8px;display:flex;align-items:center;gap:6px;flex-shrink:0;">
                                            <button id="btnRefreshOverview" class="mini-button" iconCls="note-refresh" onclick="refreshData()">刷新</button>
                                            <span class="separator"></span>
                                            <span style="font-size:12px;color:#333;margin-right:4px;">统计类型：</span>
                                            <input id="alarmStatRangeType" class="mini-radiobuttonlist" data="[{'id':0,'text':'实时'},{'id':1,'text':'历史'}]" valueField="id" textField="text" value="0" onvaluechanged="onStatRangeChanged" />
                                            <span class="separator"></span>
                                            <input id="overviewDeviceCombo" class="mini-combobox" style="width:140px;" emptyText="-- 全部 --" url="<%=path%>/wellInformationManagerController/loadWellComboxList" dataField="list" totalField="totals" valueField="boxkey" textField="boxval" onvaluechanged="onOverviewDeviceChange" />
                                            <span style="flex:1;"></span>
                                            <button id="exportAlarmOverviewBtn" class="mini-button" iconCls="export" onclick="exportAlarmOverview()">导出</button>
                                            <!-- 隐藏域 -->
                                            <input id="AlarmOverviewSelectRow_Id" type="hidden" value="-1" />
                                            <input id="AlarmOverviewColumnStr_Id" type="hidden" value="" />
                                            <input id="AlarmDetailsColumnStr_Id" type="hidden" value="" />
                                            <input id="selectedAlarmStatType_Id" type="hidden" value="" />
                                            <input id="selectedAlarmStatLevel_Id" type="hidden" value="" />
                                        </div>
                                        <!-- 第二行工具栏：统计信息 -->
                                        <div class="mini-toolbar" style="border:0;border-top:1px solid #f0f0f0;padding:2px 8px;display:flex;align-items:center;justify-content:flex-end;gap:16px;flex-shrink:0;background:#fafafa;height:28px;">
                                            <span style="font-size:12px;color:#333;">
                                                <span id="overviewDeviceCountLabel">设备数：</span><span id="overviewDeviceCount">0</span>
                                            </span>
                                            <span style="font-size:12px;color:#333;">
                                                <span id="overviewAlarmCountLabel">报警数：</span><span id="overviewAlarmCount">0</span>
                                            </span>
                                        </div>
                                        <div style="flex:1;overflow:hidden;">
                                            <div id="alarmOverviewGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="100" allowResize="true" allowAlternating="true" url="<%=path%>/alarmQueryController/getAlarmOverviewData" dataField="totalRoot" totalField="totalCount" onselectionchanged="onOverviewRowSelect" onload="onOverviewGridLoad" onbeforeload="onOverviewGridBeforeLoad">
                                                <div property="columns">
                                                    <!-- 动态生成 -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 统计图表（柱状图，带钻取） -->
                                <div id="statPanel" size="50%" showCollapseButton="true" minSize="100" collapseDirection="bottom">
                                    <div class="stat-chart-area" style="height:100%;">
                                        <div class="mini-tabs" id="statTabs" style="width:100%;height:100%;" activeIndex="0" onactivechanged="onStatTabChanged">
                                            <div title="报警类型" name="stat_type">
                                                <div id="alarmTypeChartContainer" style="width:100%;height:100%;min-height:100px;"></div>
                                            </div>
                                            <div title="报警级别" name="stat_level">
                                                <div id="alarmLevelChartContainer" style="width:100%;height:100%;min-height:100px;"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 右侧：报警详情 Tabs -->
                    <div size="55%" showCollapseButton="true" minSize="300" collapseDirection="right">
                        <div class="right-panel" style="height:100%; background:#f0f2f5; padding:4px;">
                            <div style="width:100%; height:100%; display:flex; flex-direction:column;">
                                <!-- 公共工具栏 -->
                                <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:4px 8px;display:flex;align-items:center;flex-wrap:wrap;gap:4px;flex-shrink:0;">
                                    <span style="font-size:12px;color:#333;" id="detailRangeLabel"></span>
                                    <input id="detailStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                    <span style="margin-left:8px;font-size:12px;color:#333;" id="detailTimeToLabel"></span>
                                    <input id="detailEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                    <span style="font-size:12px;color:#333;margin-left:8px;" id="detailAlarmLevelLabel"></span>
                                    <input id="detailAlarmLevel" class="mini-combobox" style="width:100px;" emptyText="-- 全部 --" valueField="id" textField="text" />
                                    <button class="mini-button" iconCls="search" onclick="refreshDetailData()">查询</button>
                                    <button class="mini-button" iconCls="export" onclick="exportAlarmDetail()">导出</button>
                                    <span style="flex:1;"></span>
                                    <span id="detailTotalCountLabel" style="font-size:12px;color:#999;display:none;">总记录数：<span id="detailTotalCountSpan">0</span></span>
                                </div>
                                <!-- 报警类型 Tabs -->
                                <div id="alarmDetailTabs" class="mini-tabs" style="width:100%; flex:1;" activeIndex="0" onactivechanged="onDetailTabChanged">
                                    <!-- 由 JS 动态生成标签 -->
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
        // ================================================================
        // 0. 全局变量
        // ================================================================
        var context = '<%=path%>';
        var tabInfo = null;
        try {
            if (window.parent && window.parent.tabInfo) tabInfo = window.parent.tabInfo;
        } catch (e) {
            console.warn('无法获取 tabInfo', e);
        }
        var currentLevel1 = null,
            currentLevel2 = null;
        var level1Data = [],
            level2Data = [];
        var currentDeviceId = 0,
            currentDeviceName = '';
        var alarmDetailTabs = null,
            statTabs = null,
            overviewGrid = null;

        // 报警类型配置（对应原 ExtJS 的 AlarmQuerySecondTabPanelItems）
        var ALARM_TYPE_CONFIG = [{
                id: 'FESDiagramResultAlarm',
                title: _loginUserLanguageResource.FESDiagramResultAlarm,
                key: 'FESDiagramResultAlarm',
                type: 4
            },
            {
                id: 'CommunicationAlarm',
                title: _loginUserLanguageResource.commStatusAlarm,
                key: 'CommStatusAlarm',
                type: 3
            },
            {
                id: 'RunStatusAlarm',
                title: _loginUserLanguageResource.runStatusAlarm,
                key: 'RunStatusAlarm',
                type: 6
            },
            {
                id: 'NumericValueAlarm',
                title: _loginUserLanguageResource.numericValueAlarm,
                key: 'NumericValueAlarm',
                type: 2
            },
            {
                id: 'EnumValueAlarm',
                title: _loginUserLanguageResource.enumValueAlarm,
                key: 'EnumValueAlarm',
                type: 1
            },
            {
                id: 'SwitchingValueAlarm',
                title: _loginUserLanguageResource.switchingValueAlarm,
                key: 'SwitchingValueAlarm',
                type: 0
            }
        ];

        // ================================================================
        // 1. 构建一级标签
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
        }

        // ================================================================
        // 2. 构建二级标签（左侧）
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
            for (var i = 0; i < children.length; i++) allIds.push(children[i].deviceTypeId);
            var allTabs = [{
                text: _loginUserLanguageResource.all,
                deviceTypeId: allIds.join(','),
                isAll: true
            }];
            for (var i = 0; i < children.length; i++) allTabs.push(children[i]);
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
            var allTabs = [{
                text: _loginUserLanguageResource.all,
                deviceTypeId: '',
                isAll: true
            }];
            for (var i = 0; i < level2Data.length; i++) allTabs.push(level2Data[i]);
            var allIds = [];
            for (var i = 0; i < level2Data.length; i++) allIds.push(level2Data[i].deviceTypeId);
            allTabs[0].deviceTypeId = allIds.join(',');
            if (index < 0 || index >= allTabs.length) return;
            for (var i = 0; i < tabs.length; i++) {
                tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
            }
            currentLevel2 = allTabs[index];
            loadAllData(currentLevel2);
        }

        // ================================================================
        // 3. 加载所有数据（概览 + 统计）
        // ================================================================
        var _statTabsInitialized = false;
		var _loadingStats = false;
        function loadAllData(level2Item) {
            if (!level2Item) return;
            var deviceTypeId = level2Item.deviceTypeId || '0';
            var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            // 重置统计筛选
            document.getElementById('selectedAlarmStatType_Id').value = '';
            document.getElementById('selectedAlarmStatLevel_Id').value = '';
            // 刷新右侧报警详情标签（根据配置动态生成）
            updateDetailTabs(deviceTypeId);
            // 刷新概览表格
            refreshOverview();
         	// ★ 加载统计图表前设置标志，防止 onStatTabChanged 重复请求
            _loadingStats = true;
            loadStatCharts(deviceTypeId, orgId, function() {
                _loadingStats = false;
            });
        }

        // ================================================================
        // 4. 设备概览表格
        // ================================================================
        function onOverviewGridBeforeLoad(e) {
            var params = e.params || {};
            var pageIndex = params.pageIndex || 0;
            var pageSize = params.pageSize || 20;
            params.start = pageIndex * pageSize;
            params.limit = pageSize;
            var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
            params.orgId = leftOrgId ? leftOrgId.getValue() : '';
            params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var combo = mini.get('overviewDeviceCombo');
            params.deviceName = combo ? combo.getValue() : '';
            // 统计筛选（从隐藏域读取）
            params.alarmType = document.getElementById('selectedAlarmStatType_Id').value || '';
            params.alarmLevel = document.getElementById('selectedAlarmStatLevel_Id').value || '';
            // 统计类型（当前激活的统计图表Tab: type 或 level）
            var statTab = mini.get('statTabs');
            var activeStat = statTab ? statTab.getActiveTab() : null;
            var statType = 0; // 0: 按类型, 1: 按级别
            if (activeStat && activeStat.name === 'stat_level') statType = 1;
            params.statType = statType;
            // ★ 读取统计类型（实时/历史）
            var statRange = mini.get('alarmStatRangeType');
            params.alarmQueryStatRangeType = statRange ? parseInt(statRange.getValue(), 10) : 0;
        }

        function onOverviewGridLoad(e) {
            var grid = e.sender,
                result = e.result;
            if (result && result.columns) {
                var columns = buildOverviewColumns(result.columns);
                document.getElementById('AlarmOverviewColumnStr_Id').value = JSON.stringify(result.columns);
                setTimeout(function() {
                    grid.setColumns(columns);
                    grid.doLayout();
                }, 50);
            }
            // ★ 更新设备数和报警数统计信息 ★
            if (result) {
                document.getElementById('overviewDeviceCount').textContent = result.totalCount || 0;
                document.getElementById('overviewAlarmCount').textContent = result.alarmCount || 0;
            }
            var data = grid.getData();
            if (data && data.length > 0) grid.select(0);
        }

        function buildOverviewColumns(colsData) {
            var cols = [];
            for (var i = 0; i < colsData.length; i++) {
                var col = colsData[i];
                var column = {
                    field: col.dataIndex,
                    header: col.header,
                    headerAlign: 'center',
                    align: 'center',
                    width: col.width || 100
                };
                if (col.dataIndex === 'id') {
                    column.type = 'indexcolumn';
                    column.width = 40;
                    column.header = _loginUserLanguageResource.idx;
                    delete column.field;
                } else if (col.dataIndex === 'deviceName') {
                    column.width = 140;
                    column.locked = true;
                } else if (col.dataIndex === 'acqTime' || col.dataIndex === 'alarmTime') {
                    column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                    column.width = 150;
                }
                cols.push(column);
            }
            return cols;
        }

        function onOverviewRowSelect(e) {
            var selected = e.selected;
            if (selected) {
                currentDeviceId = selected.id;
                currentDeviceName = selected.deviceName || '';
             // 强制重置加载跳过标志，确保后续能加载数据
                window._skipDetailDataLoad = false;
                var detailTabs = mini.get('alarmDetailTabs');
                if (detailTabs) {
                    var activeTab = detailTabs.getActiveTab();
                    // 如果当前没有激活标签（或激活的是占位），则激活第一个非占位标签
                    if (!activeTab || activeTab.name === 'placeholder') {
                        var tabs = detailTabs.getTabs();
                        for (var i = 0; i < tabs.length; i++) {
                            if (tabs[i].name !== 'placeholder') {
                                detailTabs.activeTab(tabs[i]); // 触发 onDetailTabChanged 自动加载数据
                                break;
                            }
                        }
                    } else {
                    	refreshDetailData();
                    }
                } else {
                    // 没有 detailTabs（异常情况），尝试直接加载
                    refreshDetailData();
                }
            }
        }

        function onStatRangeChanged() {
            // 刷新设备概览表格
            refreshOverview();
            // 刷新统计图表
            var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            loadStatCharts(deviceTypeId, orgId);
        }

        function onOverviewDeviceChange() {
            refreshOverview();
        }

        function refreshOverview() {
            var grid = mini.get('alarmOverviewGrid');
            if (grid) grid.load();
        }

        // ================================================================
        // 5. 统计图表（柱状图 + 钻取）
        // ================================================================
        function loadStatCharts(deviceTypeId, orgId,callback) {
            var statType = 0;
            var statTab = mini.get('statTabs');
            if (statTab) {
                var active = statTab.getActiveTab();
                if (active && active.name === 'stat_level') statType = 1;
            }
            // ★ 读取统计类型（实时/历史）
            var statRange = mini.get('alarmStatRangeType');
            var alarmQueryStatRangeType = statRange ? parseInt(statRange.getValue(), 10) : 0;
            // 获取项目配置（哪些报警类型启用）
            var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
            var alarmConfig = projectTabConfig.AlarmQuery || {};
            // 构造请求参数
            $.ajax({
                url: context + '/alarmQueryController/getAlarmStatData',
                type: 'POST',
                data: {
                    orgId: orgId,
                    deviceType: deviceTypeId,
                    statType: statType,
                    alarmQueryStatRangeType: alarmQueryStatRangeType
                },
                dataType: 'json',
                timeout: 10000,
                success: function(result) {
                    if (callback) callback();
                    if (statType === 0) {
                        renderAlarmTypeStat(result, alarmConfig);
                    } else {
                        renderAlarmLevelStat(result, alarmConfig);
                    }
                },
                error: function() {
                	if (callback) callback();
                    var container = document.getElementById('alarmTypeChartContainer');
                    if (container) container.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        function onStatTabChanged(e) {
            var tab = e.tab;
            if (!tab) return;
         	// 如果 currentLevel2 还未设置（页面初始化阶段），跳过本次事件
         	// 首次激活（初始化时）跳过
    		if (!_statTabsInitialized) {
        		_statTabsInitialized = true;
        		return;
    		}
    		if (_loadingStats) return;
            // 清空统计筛选
            document.getElementById('selectedAlarmStatType_Id').value = '';
            document.getElementById('selectedAlarmStatLevel_Id').value = '';
            // 重新加载统计数据
            var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            loadStatCharts(deviceTypeId, orgId);
            // 刷新概览表格（应用清空筛选）
            refreshOverview();
        }

        // ---- 渲染报警类型统计（柱状图，可钻取到级别） ----
        // ---- 报警类型统计（钻取柱状图：类型 → 级别） ----
        function renderAlarmTypeStat(result, alarmConfig) {
            var container = document.getElementById('alarmTypeChartContainer');
            if (!container) return;

            var title = _loginUserLanguageResource.alarmType;
            var subtitle = _loginUserLanguageResource.alarmStatisticsChartSubtitle1;
            var yAxisTitle = _loginUserLanguageResource.deviceCount;
            var rawSeriesData = [];
            var drilldownSeriesData = [];

            // 1. 工况报警
            if (alarmConfig.FESDiagramResultAlarm && result.diagramResultAlarmDeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.FESDiagramResultAlarm,
                    y: result.diagramResultAlarmDeviceCount,
                    drilldown: 'FESDiagramResultAlarm',
                    code: 'FESDiagramResultAlarm',
                    alarmType: 4,
                    alarmLevel: ''
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.FESDiagramResultAlarm,
                    id: 'FESDiagramResultAlarm',
                    data: []
                };
                if (result.diagramResultAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel1,
                        y: result.diagramResultAlarmLevel1DeviceCount,
                        code: 'FESDiagramResultAlarm_Level1',
                        alarmType: 4,
                        alarmLevel: 100
                    });
                }
                if (result.diagramResultAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel2,
                        y: result.diagramResultAlarmLevel2DeviceCount,
                        code: 'FESDiagramResultAlarm_Level2',
                        alarmType: 4,
                        alarmLevel: 200
                    });
                }
                if (result.diagramResultAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel3,
                        y: result.diagramResultAlarmLevel3DeviceCount,
                        code: 'FESDiagramResultAlarm_Level3',
                        alarmType: 4,
                        alarmLevel: 300
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            // 2. 通信状态报警
            if (alarmConfig.CommStatusAlarm && result.commStatusAlarmDeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.commStatusAlarm,
                    y: result.commStatusAlarmDeviceCount,
                    drilldown: 'commStatusAlarm',
                    code: 'commStatusAlarm',
                    alarmType: 3,
                    alarmLevel: ''
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.commStatusAlarm,
                    id: 'commStatusAlarm',
                    data: []
                };
                if (result.commStatusAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel1,
                        y: result.commStatusAlarmLevel1DeviceCount,
                        code: 'commStatusAlarm_Level1',
                        alarmType: 3,
                        alarmLevel: 100
                    });
                }
                if (result.commStatusAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel2,
                        y: result.commStatusAlarmLevel2DeviceCount,
                        code: 'commStatusAlarm_Level2',
                        alarmType: 3,
                        alarmLevel: 200
                    });
                }
                if (result.commStatusAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel3,
                        y: result.commStatusAlarmLevel3DeviceCount,
                        code: 'commStatusAlarm_Level3',
                        alarmType: 3,
                        alarmLevel: 300
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            // 3. 运行状态报警
            if (alarmConfig.RunStatusAlarm && result.runStatusAlarmDeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.runStatusAlarm,
                    y: result.runStatusAlarmDeviceCount,
                    drilldown: 'runStatusAlarm',
                    code: 'runStatusAlarm',
                    alarmType: 6,
                    alarmLevel: ''
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.runStatusAlarm,
                    id: 'runStatusAlarm',
                    data: []
                };
                if (result.runStatusAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel1,
                        y: result.runStatusAlarmLevel1DeviceCount,
                        code: 'runStatusAlarm_Level1',
                        alarmType: 6,
                        alarmLevel: 100
                    });
                }
                if (result.runStatusAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel2,
                        y: result.runStatusAlarmLevel2DeviceCount,
                        code: 'runStatusAlarm_Level2',
                        alarmType: 6,
                        alarmLevel: 200
                    });
                }
                if (result.runStatusAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel3,
                        y: result.runStatusAlarmLevel3DeviceCount,
                        code: 'runStatusAlarm_Level3',
                        alarmType: 6,
                        alarmLevel: 300
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            // 4. 数值量报警
            if (alarmConfig.NumericValueAlarm && result.numericValueAlarmDeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.numericValueAlarm,
                    y: result.numericValueAlarmDeviceCount,
                    drilldown: 'numericValueAlarm',
                    code: 'numericValueAlarm',
                    alarmType: 2,
                    alarmLevel: ''
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.numericValueAlarm,
                    id: 'numericValueAlarm',
                    data: []
                };
                if (result.numericValueAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel1,
                        y: result.numericValueAlarmLevel1DeviceCount,
                        code: 'numericValueAlarm_Level1',
                        alarmType: 2,
                        alarmLevel: 100
                    });
                }
                if (result.numericValueAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel2,
                        y: result.numericValueAlarmLevel2DeviceCount,
                        code: 'numericValueAlarm_Level2',
                        alarmType: 2,
                        alarmLevel: 200
                    });
                }
                if (result.numericValueAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel3,
                        y: result.numericValueAlarmLevel3DeviceCount,
                        code: 'numericValueAlarm_Level3',
                        alarmType: 2,
                        alarmLevel: 300
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            // 5. 枚举量报警
            if (alarmConfig.EnumValueAlarm && result.enumValueAlarmDeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.enumValueAlarm,
                    y: result.enumValueAlarmDeviceCount,
                    drilldown: 'enumValueAlarm',
                    code: 'enumValueAlarm',
                    alarmType: 1,
                    alarmLevel: ''
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.enumValueAlarm,
                    id: 'enumValueAlarm',
                    data: []
                };
                if (result.enumValueAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel1,
                        y: result.enumValueAlarmLevel1DeviceCount,
                        code: 'enumValueAlarm_Level1',
                        alarmType: 1,
                        alarmLevel: 100
                    });
                }
                if (result.enumValueAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel2,
                        y: result.enumValueAlarmLevel2DeviceCount,
                        code: 'enumValueAlarm_Level2',
                        alarmType: 1,
                        alarmLevel: 200
                    });
                }
                if (result.enumValueAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel3,
                        y: result.enumValueAlarmLevel3DeviceCount,
                        code: 'enumValueAlarm_Level3',
                        alarmType: 1,
                        alarmLevel: 300
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            // 6. 开关量报警
            if (alarmConfig.SwitchingValueAlarm && result.switchingValueAlarmDeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.switchingValueAlarm,
                    y: result.switchingValueAlarmDeviceCount,
                    drilldown: 'switchingValueAlarm',
                    code: 'switchingValueAlarm',
                    alarmType: 0,
                    alarmLevel: ''
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.switchingValueAlarm,
                    id: 'switchingValueAlarm',
                    data: []
                };
                if (result.switchingValueAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel1,
                        y: result.switchingValueAlarmLevel1DeviceCount,
                        code: 'switchingValueAlarm_Level1',
                        alarmType: 0,
                        alarmLevel: 100
                    });
                }
                if (result.switchingValueAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel2,
                        y: result.switchingValueAlarmLevel2DeviceCount,
                        code: 'switchingValueAlarm_Level2',
                        alarmType: 0,
                        alarmLevel: 200
                    });
                }
                if (result.switchingValueAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.alarmLevel3,
                        y: result.switchingValueAlarmLevel3DeviceCount,
                        code: 'switchingValueAlarm_Level3',
                        alarmType: 0,
                        alarmLevel: 300
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            if (rawSeriesData.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            showAlarmStatDrillDownChart(title, 'alarmTypeChartContainer', subtitle, yAxisTitle, rawSeriesData, drilldownSeriesData);
        }

        //---- 报警级别统计（钻取柱状图：级别 → 类型） ----
        function renderAlarmLevelStat(result, alarmConfig) {
            var container = document.getElementById('alarmLevelChartContainer');
            if (!container) return;

            var title = _loginUserLanguageResource.alarmLevel;
            var subtitle = _loginUserLanguageResource.alarmStatisticsChartSubtitle2;
            var yAxisTitle = _loginUserLanguageResource.deviceCount;
            var rawSeriesData = [];
            var drilldownSeriesData = [];

            // 计算各级别总报警设备数（汇总所有报警类型）
            var alarmLevel1DeviceCount = 0,
                alarmLevel2DeviceCount = 0,
                alarmLevel3DeviceCount = 0;

            if (alarmConfig.FESDiagramResultAlarm) {
                alarmLevel1DeviceCount += (result.diagramResultAlarmLevel1DeviceCount || 0);
                alarmLevel2DeviceCount += (result.diagramResultAlarmLevel2DeviceCount || 0);
                alarmLevel3DeviceCount += (result.diagramResultAlarmLevel3DeviceCount || 0);
            }
            if (alarmConfig.CommStatusAlarm) {
                alarmLevel1DeviceCount += (result.commStatusAlarmLevel1DeviceCount || 0);
                alarmLevel2DeviceCount += (result.commStatusAlarmLevel2DeviceCount || 0);
                alarmLevel3DeviceCount += (result.commStatusAlarmLevel3DeviceCount || 0);
            }
            if (alarmConfig.RunStatusAlarm) {
                alarmLevel1DeviceCount += (result.runStatusAlarmLevel1DeviceCount || 0);
                alarmLevel2DeviceCount += (result.runStatusAlarmLevel2DeviceCount || 0);
                alarmLevel3DeviceCount += (result.runStatusAlarmLevel3DeviceCount || 0);
            }
            if (alarmConfig.NumericValueAlarm) {
                alarmLevel1DeviceCount += (result.numericValueAlarmLevel1DeviceCount || 0);
                alarmLevel2DeviceCount += (result.numericValueAlarmLevel2DeviceCount || 0);
                alarmLevel3DeviceCount += (result.numericValueAlarmLevel3DeviceCount || 0);
            }
            if (alarmConfig.EnumValueAlarm) {
                alarmLevel1DeviceCount += (result.enumValueAlarmLevel1DeviceCount || 0);
                alarmLevel2DeviceCount += (result.enumValueAlarmLevel2DeviceCount || 0);
                alarmLevel3DeviceCount += (result.enumValueAlarmLevel3DeviceCount || 0);
            }
            if (alarmConfig.SwitchingValueAlarm) {
                alarmLevel1DeviceCount += (result.switchingValueAlarmLevel1DeviceCount || 0);
                alarmLevel2DeviceCount += (result.switchingValueAlarmLevel2DeviceCount || 0);
                alarmLevel3DeviceCount += (result.switchingValueAlarmLevel3DeviceCount || 0);
            }

            // 一级报警
            if (alarmLevel1DeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.alarmLevel1,
                    y: alarmLevel1DeviceCount,
                    drilldown: 'alarmLevel1',
                    code: 'alarmLevel1',
                    alarmType: '',
                    alarmLevel: 100
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.alarmLevel1,
                    id: 'alarmLevel1',
                    data: []
                };
                if (alarmConfig.FESDiagramResultAlarm && result.diagramResultAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.FESDiagramResultAlarm,
                        y: result.diagramResultAlarmLevel1DeviceCount,
                        code: 'FESDiagramResultAlarm_Level1',
                        alarmType: 4,
                        alarmLevel: 100
                    });
                }
                if (alarmConfig.CommStatusAlarm && result.commStatusAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.commStatusAlarm,
                        y: result.commStatusAlarmLevel1DeviceCount,
                        code: 'commStatusAlarm_Level1',
                        alarmType: 3,
                        alarmLevel: 100
                    });
                }
                if (alarmConfig.RunStatusAlarm && result.runStatusAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.runStatusAlarm,
                        y: result.runStatusAlarmLevel1DeviceCount,
                        code: 'runStatusAlarm_Level1',
                        alarmType: 6,
                        alarmLevel: 100
                    });
                }
                if (alarmConfig.NumericValueAlarm && result.numericValueAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.numericValueAlarm,
                        y: result.numericValueAlarmLevel1DeviceCount,
                        code: 'numericValueAlarm_Level1',
                        alarmType: 2,
                        alarmLevel: 100
                    });
                }
                if (alarmConfig.EnumValueAlarm && result.enumValueAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.enumValueAlarm,
                        y: result.enumValueAlarmLevel1DeviceCount,
                        code: 'enumValueAlarm_Level1',
                        alarmType: 1,
                        alarmLevel: 100
                    });
                }
                if (alarmConfig.SwitchingValueAlarm && result.switchingValueAlarmLevel1DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.switchingValueAlarm,
                        y: result.switchingValueAlarmLevel1DeviceCount,
                        code: 'switchingValueAlarm_Level1',
                        alarmType: 0,
                        alarmLevel: 100
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            // 二级报警
            if (alarmLevel2DeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.alarmLevel2,
                    y: alarmLevel2DeviceCount,
                    drilldown: 'alarmLevel2',
                    code: 'alarmLevel2',
                    alarmType: '',
                    alarmLevel: 200
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.alarmLevel2,
                    id: 'alarmLevel2',
                    data: []
                };
                if (alarmConfig.FESDiagramResultAlarm && result.diagramResultAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.FESDiagramResultAlarm,
                        y: result.diagramResultAlarmLevel2DeviceCount,
                        code: 'FESDiagramResultAlarm_Level2',
                        alarmType: 4,
                        alarmLevel: 200
                    });
                }
                if (alarmConfig.CommStatusAlarm && result.commStatusAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.commStatusAlarm,
                        y: result.commStatusAlarmLevel2DeviceCount,
                        code: 'commStatusAlarm_Level2',
                        alarmType: 3,
                        alarmLevel: 200
                    });
                }
                if (alarmConfig.RunStatusAlarm && result.runStatusAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.runStatusAlarm,
                        y: result.runStatusAlarmLevel2DeviceCount,
                        code: 'runStatusAlarm_Level2',
                        alarmType: 6,
                        alarmLevel: 200
                    });
                }
                if (alarmConfig.NumericValueAlarm && result.numericValueAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.numericValueAlarm,
                        y: result.numericValueAlarmLevel2DeviceCount,
                        code: 'numericValueAlarm_Level2',
                        alarmType: 2,
                        alarmLevel: 200
                    });
                }
                if (alarmConfig.EnumValueAlarm && result.enumValueAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.enumValueAlarm,
                        y: result.enumValueAlarmLevel2DeviceCount,
                        code: 'enumValueAlarm_Level2',
                        alarmType: 1,
                        alarmLevel: 200
                    });
                }
                if (alarmConfig.SwitchingValueAlarm && result.switchingValueAlarmLevel2DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.switchingValueAlarm,
                        y: result.switchingValueAlarmLevel2DeviceCount,
                        code: 'switchingValueAlarm_Level2',
                        alarmType: 0,
                        alarmLevel: 200
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            // 三级报警
            if (alarmLevel3DeviceCount > 0) {
                rawSeriesData.push({
                    name: _loginUserLanguageResource.alarmLevel3,
                    y: alarmLevel3DeviceCount,
                    drilldown: 'alarmLevel3',
                    code: 'alarmLevel3',
                    alarmType: '',
                    alarmLevel: 300
                });
                var singleSeriesData = {
                    name: _loginUserLanguageResource.alarmLevel3,
                    id: 'alarmLevel3',
                    data: []
                };
                if (alarmConfig.FESDiagramResultAlarm && result.diagramResultAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.FESDiagramResultAlarm,
                        y: result.diagramResultAlarmLevel3DeviceCount,
                        code: 'FESDiagramResultAlarm_Level3',
                        alarmType: 4,
                        alarmLevel: 300
                    });
                }
                if (alarmConfig.CommStatusAlarm && result.commStatusAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.commStatusAlarm,
                        y: result.commStatusAlarmLevel3DeviceCount,
                        code: 'commStatusAlarm_Level3',
                        alarmType: 3,
                        alarmLevel: 300
                    });
                }
                if (alarmConfig.RunStatusAlarm && result.runStatusAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.runStatusAlarm,
                        y: result.runStatusAlarmLevel3DeviceCount,
                        code: 'runStatusAlarm_Level3',
                        alarmType: 6,
                        alarmLevel: 300
                    });
                }
                if (alarmConfig.NumericValueAlarm && result.numericValueAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.numericValueAlarm,
                        y: result.numericValueAlarmLevel3DeviceCount,
                        code: 'numericValueAlarm_Level3',
                        alarmType: 2,
                        alarmLevel: 300
                    });
                }
                if (alarmConfig.EnumValueAlarm && result.enumValueAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.enumValueAlarm,
                        y: result.enumValueAlarmLevel3DeviceCount,
                        code: 'enumValueAlarm_Level3',
                        alarmType: 1,
                        alarmLevel: 300
                    });
                }
                if (alarmConfig.SwitchingValueAlarm && result.switchingValueAlarmLevel3DeviceCount > 0) {
                    singleSeriesData.data.push({
                        name: _loginUserLanguageResource.switchingValueAlarm,
                        y: result.switchingValueAlarmLevel3DeviceCount,
                        code: 'switchingValueAlarm_Level3',
                        alarmType: 0,
                        alarmLevel: 300
                    });
                }
                if (singleSeriesData.data.length > 0) {
                    drilldownSeriesData.push(singleSeriesData);
                }
            }

            if (rawSeriesData.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            showAlarmStatDrillDownChart(title, 'alarmLevelChartContainer', subtitle, yAxisTitle, rawSeriesData, drilldownSeriesData);
        }

        //===== 钻取图表绘制核心函数 =====
        function showAlarmStatDrillDownChart(title, divId, subtitle, yAxisTitle, rawSeriesData, drilldownSeriesData) {
            var container = document.getElementById(divId);
            if (!container) return;
            container.innerHTML = '';

            var isTypeChart = (divId === 'alarmTypeChartContainer'); // 区分图表类型

            Highcharts.chart(container, {
                chart: {
                    type: 'column',
                    zooming: {
                        mouseWheel: {
                            enabled: false
                        }
                    },
                    events: {
                        drilldown: function(e) {
                            var point = e.point;
                            var opt = point.options || {};
                            var alarmType = opt.alarmType || point.alarmType;
                            var alarmLevel = opt.alarmLevel || point.alarmLevel;

                            if (isTypeChart) {
                                // 类型图钻取：设置类型，清空级别
                                document.getElementById('selectedAlarmStatType_Id').value = alarmType;
                                document.getElementById('selectedAlarmStatLevel_Id').value = '';
                            } else {
                                // 级别图钻取：设置级别，清空类型
                                document.getElementById('selectedAlarmStatLevel_Id').value = alarmLevel;
                                document.getElementById('selectedAlarmStatType_Id').value = '';
                            }
                            refreshOverview();
                            alarmStatDrillDownChartResetAllPoints(this);
                        },
                        drillup: function() {
                            // 返回顶层：清空所有筛选
                            document.getElementById('selectedAlarmStatType_Id').value = '';
                            document.getElementById('selectedAlarmStatLevel_Id').value = '';
                            refreshOverview();
                            alarmStatDrillDownChartResetAllPoints(this);
                        }
                    }
                },
                title: {
                    text: title,
                    style: {
                        fontSize: '13px'
                    }
                },
                subtitle: {
                    text: subtitle
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: yAxisTitle
                    },
                    lineWidth: 1,
                    tickWidth: 1,
                    tickLength: 5,
                    allowDecimals: false
                },
                legend: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    headerFormat: '<b><span style="font-size:11px">{series.name}</span></b><br>',
                    pointFormat: '<b><span style="color:{point.color}">{point.name}</span></b>: {point.y}'
                },
                plotOptions: {
                    column: {
                        maxPointWidth: 70
                    },
                    series: {
                        borderWidth: 0,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '{point.y:.0f}'
                        },
                        point: {
                            events: {
                                mouseOver: function() {
                                    alarmStatDrillDownChartDimOtherPoints(this, this.series.chart);
                                },
                                mouseOut: function() {
                                    alarmStatDrillDownChartRestoreAllPointsOpacity(this.series.chart);
                                },
                                click: function(e) {
                                    var point = this;
                                    // 有 drilldown 则让 Highcharts 处理（下钻）
                                    if (point.drilldown || (point.options && point.options.drilldown)) {
                                        return true;
                                    }
                                    // 第二层点击：选中/取消选中（级别或类型）
                                    var opt = point.options || {};
                                    var alarmType = opt.alarmType || point.alarmType;
                                    var alarmLevel = opt.alarmLevel || point.alarmLevel;
                                    var isSelected = point.selected;

                                    if (isSelected) {
                                        point.select(false);
                                        alarmStatDrillDownChartResetPointStyle(point);
                                        // 取消选中：根据图表类型清空对应筛选
                                        if (isTypeChart) {
                                            document.getElementById('selectedAlarmStatLevel_Id').value = '';
                                        } else {
                                            document.getElementById('selectedAlarmStatType_Id').value = '';
                                        }
                                    } else {
                                        alarmStatDrillDownChartResetAllPoints(point.series.chart);
                                        point.select(true);
                                        alarmStatDrillDownChartApplyHighlightEffect(point);
                                        // 选中：根据图表类型设置对应筛选
                                        if (isTypeChart) {
                                            document.getElementById('selectedAlarmStatLevel_Id').value = alarmLevel;
                                        } else {
                                            document.getElementById('selectedAlarmStatType_Id').value = alarmType;
                                        }
                                    }
                                    refreshOverview();
                                    e.stopPropagation();
                                    return false;
                                }
                            }
                        },
                        states: {
                            select: {
                                color: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [0, '#f59e0b'],
                                        [1, '#b45309']
                                    ]
                                },
                                borderColor: '#ffffff',
                                borderWidth: 2
                            }
                        }
                    }
                },
                exporting: {
                    enabled: true,
                    filename: title,
                    fallbackToExportServer: false,
                    sourceWidth: container.offsetWidth || null,
                    sourceHeight: container.offsetHeight || null,
                    buttons: {
                        contextButton: {
                            menuItems: [
                                'viewFullscreen', 'printChart', 'separator',
                                'downloadPNG', 'downloadJPEG', 'downloadSVG', 'separator',
                                'downloadCSV', 'downloadXLS'
                            ]
                        }
                    }
                },
                series: [{
                    name: title,
                    colorByPoint: true,
                    data: rawSeriesData
                }],
                drilldown: {
                    breadcrumbs: {
                        position: {
                            align: 'right'
                        }
                    },
                    series: drilldownSeriesData
                }
            });
        }

        // ===== 辅助函数（与 ExtJS 原版一致） =====
        function alarmStatDrillDownChartSafeForEachSeries(chart, callback) {
            if (chart && chart.series && Array.isArray(chart.series)) {
                for (var i = 0; i < chart.series.length; i++) {
                    callback(chart.series[i]);
                }
            }
        }

        function alarmStatDrillDownChartRestoreAllPointsOpacity(chart) {
            alarmStatDrillDownChartSafeForEachSeries(chart, function(series) {
                if (series.points && Array.isArray(series.points)) {
                    for (var i = 0; i < series.points.length; i++) {
                        var point = series.points[i];
                        if (point.graphic && point.graphic.element) {
                            point.graphic.element.style.opacity = '';
                        }
                    }
                }
            });
        }

        function alarmStatDrillDownChartDimOtherPoints(currentPoint, chart) {
            alarmStatDrillDownChartSafeForEachSeries(chart, function(series) {
                if (!series.points) return;
                for (var i = 0; i < series.points.length; i++) {
                    var point = series.points[i];
                    var el = point.graphic && point.graphic.element;
                    if (!el) continue;
                    if (point === currentPoint || point.selected) {
                        el.style.opacity = '';
                    } else {
                        el.style.opacity = '0.4';
                    }
                }
            });
        }

        function alarmStatDrillDownChartResetAllPoints(chart) {
            alarmStatDrillDownChartSafeForEachSeries(chart, function(series) {
                if (!series.points) return;
                for (var i = 0; i < series.points.length; i++) {
                    var point = series.points[i];
                    if (point.graphic && point.graphic.element) {
                        point.graphic.element.style.transform = '';
                        point.graphic.element.style.filter = '';
                    }
                    if (point.selected) {
                        point.select(false);
                    }
                }
            });
        }

        function alarmStatDrillDownChartResetPointStyle(point) {
            if (point && point.graphic && point.graphic.element) {
                point.graphic.element.style.transform = '';
                point.graphic.element.style.filter = '';
            }
        }

        function alarmStatDrillDownChartApplyHighlightEffect(point) {
            if (point && point.graphic && point.graphic.element) {
                point.graphic.element.style.transform = 'translateY(-6px)';
                point.graphic.element.style.filter = 'drop-shadow(0 4px 8px rgba(0,0,0,0.3))';
            }
        }

        // ================================================================
        // 6. 右侧报警详情 Tabs（动态创建）
        // ================================================================
        function updateDetailTabs(deviceTypeId) {
    alarmDetailTabs = mini.get('alarmDetailTabs');
    if (!alarmDetailTabs) return;

    var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
    var alarmConfig = projectTabConfig.AlarmQuery || {};

    // 1. 计算允许显示的标签（按权限）
    var enabledTabs = [];
    for (var i = 0; i < ALARM_TYPE_CONFIG.length; i++) {
        var cfg = ALARM_TYPE_CONFIG[i];
        if (alarmConfig[cfg.key] === true) {
            enabledTabs.push(cfg);
        }
    }

    var currentTabs = alarmDetailTabs.getTabs();
    var currentMap = {};
    for (var i = 0; i < currentTabs.length; i++) {
        currentMap[currentTabs[i].name] = currentTabs[i];
    }

    var activeTab = alarmDetailTabs.getActiveTab();
    var activeName = activeTab ? activeTab.name : null;

    // 2. 移除不再需要的标签（保留占位）
    for (var i = 0; i < currentTabs.length; i++) {
        var tab = currentTabs[i];
        if (tab.name === 'placeholder') {
            if (enabledTabs.length > 0) {
                alarmDetailTabs.removeTab(tab);
            }
            continue;
        }
        var exists = enabledTabs.some(function(cfg) { return cfg.id === tab.name; });
        if (!exists) {
            alarmDetailTabs.removeTab(tab);
        }
    }

    // 3. 如果没有启用标签，添加占位
    if (enabledTabs.length === 0) {
        var placeholder = alarmDetailTabs.getTab('placeholder');
        if (!placeholder) {
            alarmDetailTabs.addTab({
                name: 'placeholder',
                title: _loginUserLanguageResource.emptyMsg,
                body: '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>'
            });
        }
        alarmDetailTabs.activeTab('placeholder');
        return;
    }

    // 4. 按顺序添加缺失的标签
    var remainingTabs = alarmDetailTabs.getTabs();
    var remainingMap = {};
    for (var i = 0; i < remainingTabs.length; i++) {
        remainingMap[remainingTabs[i].name] = remainingTabs[i];
    }

    for (var i = 0; i < enabledTabs.length; i++) {
        var cfg = enabledTabs[i];
        if (!remainingMap[cfg.id]) {
        	var tabObj = {
        		    name: cfg.id,
        		    title: cfg.title,
        		    body: '<div id="detailGridContainer_' + cfg.id + '" style="flex:1;overflow:hidden;min-height:0;"></div>',
        		    _parsed: false   // 添加标记
        		};
            // 插入到正确位置（保持顺序）
            var insertIndex = 0;
            for (var j = 0; j < remainingTabs.length; j++) {
                var tab = remainingTabs[j];
                if (tab.name === 'placeholder') continue;
                var idx = enabledTabs.findIndex(function(c) { return c.id === tab.name; });
                if (idx !== -1 && idx < i) {
                    insertIndex = j + 1;
                }
            }
            alarmDetailTabs.addTab(tabObj, insertIndex);
        }
    }

    // 5. 决定激活标签
    var targetTab = null;
    // 优先保留之前的激活标签
    if (activeName && alarmDetailTabs.getTab(activeName)) {
        targetTab = alarmDetailTabs.getTab(activeName);
    } else {
        // 否则不激活任何标签（等待设备选择后激活第一个）
        // 但是 miniui 会自动激活第一个，所以我们需要清除激活状态（设置 activeIndex=-1 不行，则激活占位后马上取消？）
        // 更稳妥：激活第一个，但通过标记阻止数据加载
        var firstTab = alarmDetailTabs.getTab(enabledTabs[0].id);
        if (firstTab) {
            targetTab = firstTab;
        }
    }

    if (targetTab) {
        alarmDetailTabs.activeTab(targetTab);
        // 设置标记，阻止 onDetailTabChanged 加载数据
        window._skipDetailDataLoad = true;
        // 清除标记（在 onDetailTabChanged 中处理）
    }
}

        function initAlarmLevelCombo() {
            var combo = mini.get('detailAlarmLevel');
            if (combo && !combo.getData()) {
                combo.setData([{
                        id: '',
                        text: _loginUserLanguageResource.all
                    },
                    {
                        id: 100,
                        text: _loginUserLanguageResource.alarmLevel1
                    },
                    {
                        id: 200,
                        text: _loginUserLanguageResource.alarmLevel2
                    },
                    {
                        id: 300,
                        text: _loginUserLanguageResource.alarmLevel3
                    }
                ]);
                // 默认选中全部
                combo.setValue('');
            }
        }

        function onDetailTabChanged(e) {
            var tab = e.tab;
            if (!tab) return;

            // 如果有跳过加载标记，则跳过本次加载
            if (window._skipDetailDataLoad) {
        		window._skipDetailDataLoad = false;
        		return;
    		}
            
            var detailTabs = mini.get('alarmDetailTabs');
            // ★ 首次激活标签时，解析 body
            if (tab && !tab._parsed) {
                var bodyEl = detailTabs.getTabBodyEl(tab);
                if (bodyEl) {
                    mini.parse(bodyEl);
                    tab._parsed = true; // 标记已解析
                }
            }

            // 仅当有设备选中时才加载数据
            if (currentDeviceId && currentDeviceId > 0) {
                refreshDetailData();
            }
        }

        function refreshDetailData() {
        	if (window._skipDetailDataLoad) {
                return;
            }
            if (!currentDeviceId) return;
            var detailTabs = mini.get('alarmDetailTabs');
            if (!detailTabs) return;
            var activeTab = detailTabs.getActiveTab();
            if (!activeTab || activeTab.name === 'placeholder') return;
            var tabName = activeTab.name;
            var containerId = 'detailGridContainer_' + tabName;
            var container = document.getElementById(containerId);
            if (!container) return;

            var grid = mini.get('alarmDetailGrid_' + tabName);
            console.log(grid);
            if (!grid) {
                var gridObj = new mini.DataGrid();
                gridObj.set({
                    id: 'alarmDetailGrid_' + tabName,
                    style: 'width:100%; height:100%;',
                    showPager: true,
                    pageSize: 100,
                    allowResize: true,
                    allowAlternating: true,
                    url: context + '/alarmQueryController/getAlarmData',
                    dataField: 'totalRoot',
                    totalField: 'totalCount',
                    onbeforeload: function(e) {
                        var params = e.params || {};
                        var pageIndex = params.pageIndex || 0;
                        var pageSize = params.pageSize || 100;
                        params.start = pageIndex * pageSize;
                        params.limit = pageSize;
                        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
                        params.orgId = leftOrgId ? leftOrgId.getValue() : '';
                        params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
                        params.deviceId = currentDeviceId;
                        params.deviceName = currentDeviceName;
                        // 报警类型：当前激活的 tab
                        var currentTab = mini.get('alarmDetailTabs').getActiveTab();
                        if (currentTab) {
                            params.alarmType = getAlarmTypeFromTabName(currentTab.name);
                        } else {
                            params.alarmType = -1;
                        }
                        // 日期和报警级别：全局控件
                        var startDate = mini.get('detailStartDate');
                        var endDate = mini.get('detailEndDate');
                        var alarmLevelCombo = mini.get('detailAlarmLevel');
                        params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
                        params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
                        params.alarmLevel = alarmLevelCombo ? alarmLevelCombo.getValue() : '';
                        params.isSendMessage = '';
                        e.params = params;
                    },
                    onload: function(e) {
                        var result = e.result;
                        if (result && result.columns) {
                            var cols = buildDetailColumns(result.columns);
                            document.getElementById('AlarmDetailsColumnStr_Id').value = JSON.stringify(result.columns);
                            this.setColumns(cols);
                        }
                        // 更新总记录数
                        var totalSpan = document.getElementById('detailTotalCountSpan');
                        if (totalSpan) {
                            totalSpan.textContent = result ? result.totalCount : 0;
                            document.getElementById('detailTotalCountLabel').style.display = 'inline';
                        }
                        // 设置起止时间（首次加载）
                        if (result && result.start_date) {
                            var startDate = mini.get('detailStartDate');
                            var endDate = mini.get('detailEndDate');
                            if (startDate && !startDate.getValue()) startDate.setValue(result.start_date);
                            if (endDate && !endDate.getValue()) endDate.setValue(result.end_date);
                        }
                    }
                });
                gridObj.render(container);
                gridObj.load();
                // ★ 延迟调整布局，确保容器高度正确
                setTimeout(function() {
                    var containerEl = document.getElementById(containerId);
                    if (containerEl) {
                        var h = containerEl.clientHeight || 300;
                        gridObj.setHeight(h);
                        gridObj.doLayout();
                    }
                }, 100);
                window['_detailGrid_' + tabName] = gridObj;
            } else {
                grid.load();
                console.log('grid.load');
            }
        }

        function buildDetailColumns(colsData) {
            var cols = [];
            for (var i = 0; i < colsData.length; i++) {
                var col = colsData[i];
                var column = {
                    field: col.dataIndex,
                    header: col.header,
                    headerAlign: 'center',
                    align: 'center',
                    width: col.width || 100
                };
                if (col.dataIndex === 'id') {
                    column.type = 'indexcolumn';
                    column.width = 40;
                    column.header = _loginUserLanguageResource.idx;
                    delete column.field;
                } else if (col.dataIndex === 'acqTime' || col.dataIndex === 'alarmTime') {
                    column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                    column.width = 150;
                }
                cols.push(column);
            }
            return cols;
        }

        function getAlarmTypeFromTabName(tabName) {
            for (var i = 0; i < ALARM_TYPE_CONFIG.length; i++) {
                if (ALARM_TYPE_CONFIG[i].id === tabName) {
                    return ALARM_TYPE_CONFIG[i].type;
                }
            }
            return -1;
        }

        // ================================================================
        // 7. 导出功能
        // ================================================================
        function exportAlarmOverview() {
            var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var deviceName = mini.get('overviewDeviceCombo') ? mini.get('overviewDeviceCombo').getValue() : '';
            var statType = 0;
            var statTab = mini.get('statTabs');
            if (statTab && statTab.getActiveTab() && statTab.getActiveTab().name === 'stat_level') statType = 1;
            var alarmType = document.getElementById('selectedAlarmStatType_Id').value || '';
            var alarmLevel = document.getElementById('selectedAlarmStatLevel_Id').value || '';
            var fileName = _loginUserLanguageResource.alarmData + '-' + _loginUserLanguageResource.deviceList;
            var title = fileName;
            var columnStr = document.getElementById('AlarmOverviewColumnStr_Id').value;
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
            var key = 'exportAlarmOverview_' + deviceType + '_' + Date.now();
            var url = context + '/alarmQueryController/exportAlarmOverviewData';
            var param = '&fields=' + fields + '&heads=' + URLencode(URLencode(heads)) +
                '&orgId=' + orgId + '&deviceType=' + deviceType + '&deviceName=' + encodeURIComponent(encodeURIComponent(deviceName)) +
                '&alarmType=' + alarmType + '&alarmLevel=' + alarmLevel + '&statType=' + statType +
                '&alarmQueryStatRangeType=0&isSendMessage=' +
                '&fileName=' + encodeURIComponent(encodeURIComponent(fileName)) +
                '&title=' + encodeURIComponent(encodeURIComponent(title)) +
                '&key=' + key;
            exportDataMask(key, document.querySelector('.device-overview-area'), _loginUserLanguageResource.loadingData);
            openExcelWindow(url + '?flag=true' + param);
        }

        // ★★★ 修复 exportAlarmDetail 函数，补全缺失的变量 ★★★
        function exportAlarmDetail() {
            if (!currentDeviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }
            var detailTabs = mini.get('alarmDetailTabs');
            var activeTab = detailTabs ? detailTabs.getActiveTab() : null;
            if (!activeTab || activeTab.name === 'placeholder') {
                mini.alert('请选择报警类型');
                return;
            }
            var tabName = activeTab.name;
            var grid = mini.get('alarmDetailGrid_' + tabName);
            if (!grid) {
                mini.alert('表格未加载完成');
                return;
            }

            // 补全变量定义
            var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var dictDeviceType = deviceType;
            if (deviceType && deviceType.indexOf(',') > -1) {
                dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
            }

            // 获取全局控件值
            var startDate = mini.get('detailStartDate');
            var endDate = mini.get('detailEndDate');
            var alarmLevelCombo = mini.get('detailAlarmLevel');

            var alarmType = getAlarmTypeFromTabName(tabName);
            var fileName = currentDeviceName + '-' + activeTab.title;
            var title = fileName;
            var columnStr = document.getElementById('AlarmDetailsColumnStr_Id').value;

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

            var key = 'exportAlarmDetail_' + currentDeviceId + '_' + Date.now();
            var url = context + '/alarmQueryController/exportAlarmData';
            var param = '&fields=' + fields + '&heads=' + URLencode(URLencode(heads)) +
                '&orgId=' + orgId +
                '&deviceType=' + deviceType +
                '&dictDeviceType=' + dictDeviceType +
                '&deviceId=' + currentDeviceId +
                '&deviceName=' + encodeURIComponent(encodeURIComponent(currentDeviceName)) +
                '&startDate=' + encodeURIComponent(startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '') +
                '&endDate=' + encodeURIComponent(endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '') +
                '&alarmType=' + alarmType +
                '&alarmLevel=' + (alarmLevelCombo ? alarmLevelCombo.getValue() : '') +
                '&isSendMessage=' +
                '&fileName=' + encodeURIComponent(encodeURIComponent(fileName)) +
                '&title=' + encodeURIComponent(encodeURIComponent(title)) +
                '&key=' + key;
            exportDataMask(key, document.querySelector('.right-panel'), _loginUserLanguageResource.loadingData);
            openExcelWindow(url + '?flag=true' + param);
        }

        // ================================================================
        // 9. 刷新与重置
        // ================================================================
        function refreshData() {
            if (currentLevel2) loadAllData(currentLevel2);
        }

        // ================================================================
        // 10. 页面初始化
        // ================================================================
        $(document).ready(function() {
            mini.parse();
            buildLevel1Tabs();
            // 初始化工具栏文本
            var btnRefresh = mini.get('btnRefreshOverview');
            if (btnRefresh) btnRefresh.setText(_loginUserLanguageResource.refresh);
            var exportBtn = mini.get('exportAlarmOverviewBtn');
            if (exportBtn) exportBtn.setText(_loginUserLanguageResource.exportData);
            var overviewCombo = mini.get('overviewDeviceCombo');
            if (overviewCombo) overviewCombo.setEmptyText('--' + _loginUserLanguageResource.all + '--');

            document.getElementById('detailRangeLabel').textContent = _loginUserLanguageResource.range + '：';
            document.getElementById('detailTimeToLabel').textContent = _loginUserLanguageResource.timeTo + '：';
            document.getElementById('detailAlarmLevelLabel').textContent = _loginUserLanguageResource.alarmLevel + '：';

            // 初始化报警级别下拉框
            var alarmLevelCombo = mini.get('detailAlarmLevel');
            if (alarmLevelCombo) {
                alarmLevelCombo.setData([{
                        id: '',
                        text: _loginUserLanguageResource.all
                    },
                    {
                        id: 100,
                        text: _loginUserLanguageResource.alarmLevel1
                    },
                    {
                        id: 200,
                        text: _loginUserLanguageResource.alarmLevel2
                    },
                    {
                        id: 300,
                        text: _loginUserLanguageResource.alarmLevel3
                    }
                ]);
                alarmLevelCombo.setValue(''); // 默认全部
            }

            // 监听父页面消息
            window.addEventListener('message', function(event) {
                var message = event.data;
                if (!message || !message.action) return;
                if (message.action === 'refresh') {
                    console.log('报警查询收到刷新指令');
                    // 清空统计筛选
                    document.getElementById('selectedAlarmStatType_Id').value = '';
                    document.getElementById('selectedAlarmStatLevel_Id').value = '';
                    mini.get('overviewDeviceCombo').setValue('');
                    if (typeof refreshData === 'function') refreshData();
                }
            });
            console.log('报警查询模块加载完成');
        });

        // 暴露全局函数
        window.selectLevel1 = selectLevel1;
        window.selectLevel2 = selectLevel2;
        window.refreshOverview = refreshOverview;
        window.onOverviewDeviceChange = onOverviewDeviceChange;
        window.onStatTabChanged = onStatTabChanged;
        window.onDetailTabChanged = onDetailTabChanged;
        window.refreshDetailData = refreshDetailData;
        window.exportAlarmOverview = exportAlarmOverview;
        window.exportAlarmDetail = exportAlarmDetail;
        window.refreshData = refreshData;
        window.onOverviewGridBeforeLoad = onOverviewGridBeforeLoad;
        window.onOverviewGridLoad = onOverviewGridLoad;
        window.onOverviewRowSelect = onOverviewRowSelect;

    </script>
</body>

</html>
