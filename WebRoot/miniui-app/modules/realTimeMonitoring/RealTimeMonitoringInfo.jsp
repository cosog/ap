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
        /* ================================================================
        1. 全局重置与基础
        ================================================================ */
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

        .realtime-container {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #fff;
        }

        /* ================================================================
        2. 底部一级标签（水平切换栏）
        ================================================================ */
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

        .level1-footer .loading-tip {
            color: #999;
            font-size: 12px;
            padding: 0 10px;
        }

        /* ================================================================
        3. 资源监测区域（右上角状态条）
        ================================================================ */
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
        
        /* 资源监测圆点闪烁动画 */
		@keyframes resourceBlink {
    		0% { color: #ccc; }
    		50% { color: #f0ad4e; }  /* 黄色 */
    		100% { color: #ccc; }
		}
		.resource-blink > span:first-child {
    		animation: resourceBlink 1s ease-in-out infinite;
		}

        /* ================================================================
        4. 主布局区（水平排列）
        ================================================================ */
        .main-area {
            flex: 1;
            display: flex;
            flex-direction: row;
            overflow: hidden;
            min-height: 0;
            order: 0;
        }

        /* ================================================================
        5. 左侧二级标签（竖排）
        ================================================================ */
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

        /* ================================================================
        6. 左侧主区域（设备列表 + 统计饼图）
        ================================================================ */
        .left-main-area {
            display: flex;
            flex-direction: column;
            overflow: hidden;
            min-width: 0;
            background: #f0f2f5;
            padding: 4px;
        }

        .device-overview-area {
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
            overflow: hidden;
            display: flex;
            flex-direction: column;
            margin-bottom: 4px;
        }

        .device-overview-area .grid-wrapper {
            flex: 1;
            overflow: hidden;
            position: relative;
        }

        .device-overview-area .grid-wrapper .mini-datagrid {
            width: 100%;
            height: 100%;
        }

        .stat-charts-area {
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
            overflow: hidden;
            display: flex;
            flex-direction: column;
        }

        .stat-charts-area .mini-tabs {
            flex: 1;
        }

        .stat-charts-area .mini-tabs .mini-tab-active {
            background: #e6f7ff;
            border-bottom: 2px solid #1890ff;
            color: #1890ff;
            font-weight: bold;
        }

        .pie-chart-container {
            width: 100%;
            height: 100%;
            min-height: 100px;
        }

        /* ================================================================
        7. 中间区域（井筒/地面/趋势/动态数据）
        ================================================================ */
        .middle-area {
            min-width: 200px;
            background: #fff;
            border: 1px solid #e8e8e8;
            overflow: hidden;
            display: flex;
            flex-direction: column;
            margin: 4px 0 4px 4px;
        }

        .middle-area .mini-tabs {
            flex: 1;
        }

        .middle-area .mini-tabs .mini-tab-body {
            overflow: hidden !important;
            padding: 0 !important;
            margin: 0 !important;
            display: flex !important;
            flex-direction: column !important;
        }

        /* ================================================================
        8. 右侧区域（设备控制/信息）
        ================================================================ */
        .right-area {
            min-width: 130px;
            background: #fff;
            border: 1px solid #e8e8e8;
            overflow: hidden;
            display: flex;
            flex-direction: column;
            margin: 4px 4px 4px 0;
        }

        .right-area .mini-tabs {
            flex: 1;
        }

        /* ================================================================
        9. 通用占位 & 报警徽章
        ================================================================ */
        .loading-placeholder {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100%;
            color: #999;
            font-size: 13px;
            flex-direction: column;
        }

        .loading-placeholder .icon {
            font-size: 32px;
            margin-bottom: 8px;
        }

        .loading-placeholder.error {
            color: #ff4d4f;
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
        }

        /* ================================================================
        10. 图表网格（井筒/地面分析）
        ================================================================ */
        .chart-grid {
            display: flex;
            flex-wrap: wrap;
            width: 100%;
            height: 100%;
            background: #fff;
        }

        .chart-grid .chart-item {
            flex: 1 1 50%;
            min-width: 300px;
            height: 50%;
            box-sizing: border-box;
            border: 1px solid #f0f0f0;
            position: relative;
            overflow: hidden;
        }

        .chart-grid .chart-item .chart-container {
            width: 100%;
            height: 100%;
        }

        .chart-grid .full-width {
            flex: 1 1 100%;
            height: 100%;
        }

        /* ================================================================
        11. 趋势曲线容器
        ================================================================ */
        #trendContainer {
            display: flex;
            flex-wrap: wrap;
            align-content: flex-start;
            width: 100%;
            height: 100%;
            overflow: auto;
            padding: 0px;
            box-sizing: border-box;
            background: #f5f7fa;
        }

        #trendContainer>.trend-chart-item {
            background: #fff;
            border-radius: 4px;
            border: 1px solid #e8e8e8;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
            box-sizing: border-box;
            padding: 2px;
            position: relative;
        }

        .trend-chart-container {
            width: 100%;
            height: 100%;
        }

        /* ================================================================
        12. 滚动条
        ================================================================ */
        .level2-sidebar::-webkit-scrollbar {
            width: 3px;
        }

        .level2-sidebar::-webkit-scrollbar-thumb {
            background: #ccc;
            border-radius: 4px;
        }

    </style>
</head>

<body>

    <div class="realtime-container">
        <div class="main-area">
            <!-- 二级标签 -->
            <div class="level2-sidebar" id="level2Sidebar">
                <div class="no-child-tip">选择一级</div>
            </div>

            <!-- 水平 Splitter：左侧整体 + 右侧面板 -->
            <div class="mini-splitter" style="flex:1; height:100%;">
                <!-- 左侧整体（设备列表+统计 和 中间标签） -->
                <div id="leftMainPanel" size="79%" showCollapseButton="false" minSize="300" collapseDirection="left">
                    <!-- 内部水平 Splitter：设备列表+统计 | 中间标签 -->
                    <div class="mini-splitter" style="width:100%; height:100%;">
                        <!-- 左侧：设备列表+统计 -->
                        <div id="deviceStatPanel" size="40%" showCollapseButton="false" minSize="200" collapseDirection="left">
                            <div class="left-main-area" style="height:100%; background: #f0f2f5; padding: 4px; display: flex; flex-direction: column; overflow: hidden;">
                                <!-- 垂直 Splitter：设备概览 + 统计饼图 -->
                                <div class="mini-splitter" style="width:100%; height:100%;" vertical="true">
                                    <div id="deviceOverviewPanel" size="50%" showCollapseButton="false" minSize="120">
                                        <div class="device-overview-area" style="height:100%;">
                                            <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:4px 8px;display:flex;align-items:center;gap:6px;">
                                                <button id="btnRefresh" class="mini-button" iconCls="note-refresh" onclick="refreshDeviceList()">刷新</button>
                                                <span class="separator"></span>
                                                <input id="deviceCombo" class="mini-combobox" style="width:140px;" emptyText="-- 全部 --" url="<%=path%>/wellInformationManagerController/loadWellComboxList" dataField="list" totalField="totals" valueField="boxkey" textField="boxval" onvaluechanged="onDeviceComboChange" />
                                                <span style="flex:1;"></span>
                                                <button id="exportRealTimeMonitoringDeviceListBtn" class="mini-button" iconCls="export" onclick="exportRealTimeMonitoringData()">导出</button>
                                                <button id="queryDeviceHistoryDataBtn" class="mini-button" onclick="gotoHistory()">查看历史</button>
                                                <!-- 隐藏控件 -->
                                                <input id="RealTimeMonitoringInfoDeviceListSelectRow_Id" type="hidden" value="-1" />
                                                <input id="RealTimeMonitoringStatSelectFESdiagramResult_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringStatSelectCommStatus_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringStatSelectRunStatus_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringStatSelectNumStatus_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringStatSelectDeviceType_Id" type="hidden" value="" />
                                                <input id="RealTimeMonitoringColumnStr_Id" type="hidden" value="" />
                                                <input id="rodStressChart_ShowMaxRodStress_Id" type="hidden" value="0" />
                                                <input id="rodStressChart_ShowRodStressRange_Id" type="hidden" value="0" />
                                            </div>
                                            <div class="grid-wrapper" style="flex:1;overflow:hidden;">
                                                <div id="deviceGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="25" allowResize="true" allowAlternating="true" frozenStartColumn="0" frozenEndColumn="1" url="<%=path%>/realTimeMonitoringController/getDeviceRealTimeOverview" dataField="totalRoot" totalField="totalCount" ondrawcell="onDeviceGridDrawCell" onselectionchanged="onDeviceGridSelectChanged" onload="onDeviceGridLoad" onbeforeload="onDeviceGridBeforeLoad">
                                                    <div property="columns">
                                                        <!-- 动态生成 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="statPanel" size="50%" showCollapseButton="true" minSize="100">
                                        <div class="stat-charts-area" style="height:100%;">
                                            <div id="statTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" onactivechanged="onStatTabChanged"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 右侧：中间标签（曲线/表） -->
                        <div id="middlePanel" size="60%" showCollapseButton="true" minSize="150" collapseDirection="right">
                            <div class="middle-area" style="height:100%; background:#fff; border-radius:4px; box-shadow:0 1px 4px rgba(0,0,0,0.06); overflow:hidden; display:flex; flex-direction:column;">
                                <div id="middleTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" onactivechanged="onMiddleTabChanged"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 右侧面板（控制/信息标签） -->
                <div id="rightPanel" size="21%" showCollapseButton="true" minSize="130" collapseDirection="right">
                    <div class="right-area" style="height:100%;">
                        <div id="rightTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" onactivechanged="onRightTabChanged"></div>
                    </div>
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
        } catch (e) {
            console.warn('无法从父页面获取 tabInfo:', e);
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

        // 默认列配置
        var DEFAULT_COLUMNS = [];

        function initI18n() {
            // 1. 工具栏按钮
            var btnRefresh = mini.get('btnRefresh');
            if (btnRefresh) btnRefresh.setText(_loginUserLanguageResource.refresh);

            // 导出按钮（需添加 id="exportBtn" 或通过 class 选择）
            var exportBtn = mini.get('exportRealTimeMonitoringDeviceListBtn');
            if (exportBtn) exportBtn.setText(_loginUserLanguageResource.exportData);

            var historyBtn = mini.get('queryDeviceHistoryDataBtn');
            if (historyBtn) historyBtn.setText(_loginUserLanguageResource.showHistory);

            // 2. 设备下拉框（emptyText）
            var deviceCombo = mini.get('deviceCombo');
            if (deviceCombo) {
                deviceCombo.setEmptyText('--' + (_loginUserLanguageResource.all) + '--');
            }
        }

        // ================================================================
        // 3. 构建一级标签（底部）+ 资源监测（右侧）
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

            // 资源监测区域
            var rightArea = document.createElement('div');
            rightArea.id = 'resourceMonitorArea';
            rightArea.style.cssText = 'display:flex; align-items:center; gap:6px; margin-left:auto; font-size:12px;';
            container.appendChild(rightArea);

            // 资源项配置（按顺序显示）
            var resourceItems = [{
                    id: 'CPUUsedPercentLabel_id',
                    text: _loginUserLanguageResource.resourcesMonitoring_cpu,
                    onclick: "openResourceChart('cpuUsedPercent','" + _loginUserLanguageResource.cpuUsage + "(%)')",
                    showDot: false // ★ CPU 不加圆点
                },
                {
                    id: 'memUsedPercentLabel_id',
                    text: _loginUserLanguageResource.resourcesMonitoring_mem,
                    onclick: "openResourceChart('memUsedPercent','" + _loginUserLanguageResource.memUsage + "(%)')",
                    showDot: false // ★ 内存不加圆点
                },
                {
                    id: 'tableSpaceSizeProbeLabel_id',
                    text: _loginUserLanguageResource.resourcesMonitoring_tablespaces,
                    onclick: "openResourceChart('tableSpaceSize','" + _loginUserLanguageResource.tablespacesUsage + "(%)')",
                    showDot: true
                },
                {
                    id: 'redisRunStatusProbeLabel_id',
                    text: _loginUserLanguageResource.resourcesMonitoring_cache,
                    onclick: "openResourceChart('jedisStatus','" + _loginUserLanguageResource.cacheDbMemory + "(m)')",
                    showDot: true
                },
                {
                    id: 'adRunStatusProbeLabel_id',
                    text: _loginUserLanguageResource.resourcesMonitoring_ad,
                    onclick: "openResourceChart('adRunStatus','" + _loginUserLanguageResource.adStatus + "')",
                    showDot: true
                },
                {
                    id: 'acRunStatusProbeLabel_id',
                    text: _loginUserLanguageResource.resourcesMonitoring_ac,
                    onclick: "openResourceChart('acRunStatus','" + _loginUserLanguageResource.acStatus + "')",
                    showDot: true
                },
                {
                    id: 'adLicenseStatusProbeLabel_id',
                    text: 'License',
                    onclick: '',
                    showDot: false // License 不加圆点
                }
            ];

            for (var i = 0; i < resourceItems.length; i++) {
                var cfg = resourceItems[i];
                var span = document.createElement('span');
                span.id = cfg.id;
                span.style.cssText = 'cursor:' + (cfg.onclick ? 'pointer' : 'default') + '; padding:0 3px; white-space:nowrap;';
                // 根据 showDot 决定是否显示圆点
                if (cfg.showDot) {
                    span.innerHTML = '<span style="color:#ccc;"></span> ' + cfg.text;
                } else {
                    span.textContent = cfg.text;
                }
                if (cfg.onclick) {
                    span.onclick = new Function(cfg.onclick);
                }
                rightArea.appendChild(span);
                // 分隔符（除最后一个）
                if (i < resourceItems.length - 1) {
                    var sep = document.createElement('span');
                    sep.style.cssText = 'color:#ddd; padding:0 2px;';
                    sep.textContent = '|';
                    rightArea.appendChild(sep);
                }
            }

            // License 默认隐藏
            var licenseEl = document.getElementById('adLicenseStatusProbeLabel_id');
            if (licenseEl) licenseEl.style.display = 'none';

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
                container.innerHTML = '<div class="no-child-tip">' + _loginUserLanguageResource.emptyMsg + '</div>';
                currentLevel2 = null;
                return;
            }

            level2Data = children;

            var allIds = [];
            for (var i = 0; i < children.length; i++) {
                allIds.push(children[i].deviceTypeId);
            }
            var allTabs = [{
                text: _loginUserLanguageResource.all,
                deviceTypeId: allIds.join(','),
                isAll: true
            }];
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

            var allTabs = [{
                text: _loginUserLanguageResource.all,
                deviceTypeId: '',
                isAll: true
            }];
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
            clearStatFilters();
            refreshDeviceList();
            loadStatCharts(deviceTypeId, orgId);
            // 中间和右侧标签不再在此初始化，由设备选中事件触发
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
                    column.header = _loginUserLanguageResource.idx;
                    delete column.field;
                } else if (col.dataIndex === 'deviceName') {
                    column.width = col.width || 140;
                    column.locked = true;
                } else if (col.dataIndex === 'commStatusName') {
                    column.width = col.width || 80;
                } else if (col.dataIndex === 'runStatusName' || col.dataIndex === 'RunStatusName') {
                    column.width = col.width || 80;
                } else if (col.dataIndex === 'acqTime') {
                    column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                    column.width = col.width || 150;
                }

                columns.push(column);
            }
            return columns;
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
            return {
                bg: bgRgba,
                color: color
            };
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

            // ★★★ 新增：从隐藏字段读取统计筛选条件 ★★★
            var getFieldValue = function(id) {
                var el = document.getElementById(id);
                return el ? el.value : '';
            };
            params.FESdiagramResultStatValue = getFieldValue('RealTimeMonitoringStatSelectFESdiagramResult_Id');
            params.commStatusStatValue = getFieldValue('RealTimeMonitoringStatSelectCommStatus_Id');
            params.runStatusStatValue = getFieldValue('RealTimeMonitoringStatSelectRunStatus_Id');
            params.numStatusStatValue = getFieldValue('RealTimeMonitoringStatSelectNumStatus_Id');
            params.deviceTypeStatValue = getFieldValue('RealTimeMonitoringStatSelectDeviceType_Id');

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
                    var columnStrInput = document.getElementById('RealTimeMonitoringColumnStr_Id');
                    if (columnStrInput) {
                        columnStrInput.value = JSON.stringify(result.columns);
                    }
                    var showIndex = false;
                    var showDeviceName = false;
                    for (var i = 0; i < columns.length; i++) {
                        var col = columns[i];
                        if (col.type == 'indexcolumn' || col.field.toUpperCase() === 'id'.toUpperCase()) {
                            showIndex = true;
                        } else if (col.field.toUpperCase() === 'deviceName'.toUpperCase()) {
                            showDeviceName = true;
                        }
                    }


                    setTimeout(function() {
                        grid.setColumns(columns);
                        if (showIndex && showDeviceName) {
                            grid.frozenColumns(0, 1);
                        } else if (showIndex || showDeviceName) {
                            grid.frozenColumns(0, 0);
                        }
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

                // ★ 动态刷新标签（中间和右侧）
                refreshDeviceTabs(selected);

                // 加载数据（当前激活的标签数据）
                refreshMiddleTabs();
                refreshRightTabs();
            }
        }


        // 处理鼠标进入
        function handleDeviceNameCellMouseEnter(cellElement, event) {
            var alarmData = cellElement.getAttribute('data-alarm');
            if (!alarmData) return;
            var counts;
            try {
                counts = JSON.parse(alarmData);
            } catch (e) {
                return;
            }
            var deviceName = cellElement.getAttribute('data-name') || '';
            var hasAlarm = (counts[100] + counts[200] + counts[300]) > 0;
            if (!hasAlarm) return;

            var alarmShowStyle = getAlarmShowStyle() || {};
            var Data = alarmShowStyle.Data || {};

            // 构建提示 HTML（带背景色的彩色标签）
            var parts = [];
            if (counts[100] > 0) {
                var bg1 = '#' + (Data.FirstLevel && Data.FirstLevel.Color || 'dc2828');
                var tx1 = '#' + (Data.FirstLevel && Data.FirstLevel.ColorText || 'ffffff');
                parts.push('<span style="display:inline-block;background:' + bg1 + ';color:' + tx1 + ';padding:0 8px;border-radius:12px;font-size:11px;font-weight:bold;line-height:18px;margin-right:4px;white-space:nowrap;">' + (_loginUserLanguageResource.alarmLevel1 || '一级') + ':' + counts[100] + '</span>');
            }
            if (counts[200] > 0) {
                var bg2 = '#' + (Data.SecondLevel && Data.SecondLevel.Color || 'f09614');
                var tx2 = '#' + (Data.SecondLevel && Data.SecondLevel.ColorText || 'ffffff');
                parts.push('<span style="display:inline-block;background:' + bg2 + ';color:' + tx2 + ';padding:0 8px;border-radius:12px;font-size:11px;font-weight:bold;line-height:18px;margin-right:4px;white-space:nowrap;">' + (_loginUserLanguageResource.alarmLevel2 || '二级') + ':' + counts[200] + '</span>');
            }
            if (counts[300] > 0) {
                var bg3 = '#' + (Data.ThirdLevel && Data.ThirdLevel.Color || 'fae600');
                var tx3 = '#' + (Data.ThirdLevel && Data.ThirdLevel.ColorText || '333333');
                parts.push('<span style="display:inline-block;background:' + bg3 + ';color:' + tx3 + ';padding:0 8px;border-radius:12px;font-size:11px;font-weight:bold;line-height:18px;margin-right:4px;white-space:nowrap;">' + (_loginUserLanguageResource.alarmLevel3 || '三级') + ':' + counts[300] + '</span>');
            }

            var tipHtml = deviceName;
            if (parts.length > 0) {
                tipHtml = deviceName + ' ' + parts.join(' ');
            }

            // ★★★ 使用鼠标坐标定位 ★★★
            var x = event.clientX + 12;
            var y = event.clientY + 12;

            // 防止超出屏幕右边界
            var tipWidth = 300; // 预估最大宽度
            if (x + tipWidth > window.innerWidth) {
                x = event.clientX - tipWidth - 12;
            }
            // 防止超出屏幕底部
            var tipHeight = 80; // 预估最小高度
            if (y + tipHeight > window.innerHeight) {
                y = event.clientY - tipHeight - 12;
            }

            // 关闭之前的提示
            hideDeviceNameTip();

            var tipDiv = document.createElement('div');
            tipDiv.id = 'deviceNameTip';
            tipDiv.style.cssText =
                'position:fixed;' +
                'background:#fff;' +
                'border:1px solid #ccc;' +
                'padding:6px 10px;' +
                'border-radius:4px;' +
                'box-shadow:0 2px 8px rgba(0,0,0,0.15);' +
                'z-index:99999;' +
                'max-width:400px;' +
                'font-size:12px;' +
                'font-family:"Microsoft YaHei",Arial,sans-serif;' +
                'pointer-events:none;';
            tipDiv.innerHTML = tipHtml;
            tipDiv.style.left = x + 'px';
            tipDiv.style.top = y + 'px';
            document.body.appendChild(tipDiv);
        }

        // 隐藏提示
        function hideDeviceNameTip() {
            var tip = document.getElementById('deviceNameTip');
            if (tip && tip.parentNode) {
                tip.parentNode.removeChild(tip);
            }
        }

        //===== 设备表格绘制单元格 =====
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

            // ---- 设备名称列（徽章 + 悬停提示） ----
            if (fieldUpper === 'DEVICENAME') {
                var badges = '';
                var counts = {
                    100: 0,
                    200: 0,
                    300: 0
                };
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

                var deviceName = value || '';
                // 将 counts 转为 JSON 字符串存入 data-* 属性
                var alarmData = JSON.stringify(counts);
                // 使用 data-* 属性存储报警信息
                e.cellHtml = '<span class="device-name-cell" data-alarm=\'' + alarmData + '\' data-name="' + deviceName + '" style="white-space:nowrap;">' + badges + deviceName + '</span>';
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

            // ---- 其他数据列（报警高亮） ----
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

        function exportRealTimeMonitoringData() {
            // 1. 组织ID
            var orgId = '';
            try {
                var leftOrg = window.parent.mini.get('leftOrg_Id');
                if (leftOrg) orgId = leftOrg.getValue();
            } catch (e) {}

            // 2. 设备名称
            var deviceCombo = mini.get('deviceCombo');
            var deviceName = deviceCombo ? deviceCombo.getValue() : '';

            // 3. 设备类型
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var dictDeviceType = deviceType;
            if (deviceType && deviceType.indexOf(',') > -1) {
                dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
            }

            // 4. 列配置（从隐藏字段读取）
            var columnStrInput = document.getElementById('RealTimeMonitoringColumnStr_Id');
            if (!columnStrInput || !columnStrInput.value) {
                mini.alert('表格列配置未加载，请刷新页面重试');
                return;
            }
            var columnStr = columnStrInput.value;

            // 5. 统计筛选条件（从隐藏字段读取）
            var FESdiagramResultStatValue = document.getElementById('RealTimeMonitoringStatSelectFESdiagramResult_Id')?.value || '';
            var commStatusStatValue = document.getElementById('RealTimeMonitoringStatSelectCommStatus_Id')?.value || '';
            var runStatusStatValue = document.getElementById('RealTimeMonitoringStatSelectRunStatus_Id')?.value || '';
            var numStatusStatValue = document.getElementById('RealTimeMonitoringStatSelectNumStatus_Id')?.value || '';
            var deviceTypeStatValue = document.getElementById('RealTimeMonitoringStatSelectDeviceType_Id')?.value || '';

            // 6. 文件名
            var fileName = _loginUserLanguageResource.realtimeMonitoringExpFileName;
            var title = fileName;

            // 7. 构建 fields / heads
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

            // 8. 生成key
            var key = 'exportDeviceRealTimeOverviewData_' + deviceType + '_' + new Date().getTime();

            // 9. 构建URL
            var url = context + '/realTimeMonitoringController/exportDeviceRealTimeOverviewDataExcel';
            var param = '&fields=' + encodeURIComponent(fields) +
                '&heads=' + encodeURIComponent(encodeURIComponent(heads)) +
                '&orgId=' + encodeURIComponent(orgId) +
                '&deviceType=' + encodeURIComponent(deviceType) +
                '&dictDeviceType=' + encodeURIComponent(dictDeviceType) +
                '&deviceName=' + encodeURIComponent(encodeURIComponent(deviceName)) +
                '&FESdiagramResultStatValue=' + encodeURIComponent(encodeURIComponent(FESdiagramResultStatValue)) +
                '&commStatusStatValue=' + encodeURIComponent(encodeURIComponent(commStatusStatValue)) +
                '&runStatusStatValue=' + encodeURIComponent(encodeURIComponent(runStatusStatValue)) +
                '&numStatusStatValue=' + encodeURIComponent(encodeURIComponent(numStatusStatValue)) +
                '&deviceTypeStatValue=' + encodeURIComponent(encodeURIComponent(deviceTypeStatValue)) +
                '&fileName=' + encodeURIComponent(encodeURIComponent(fileName)) +
                '&title=' + encodeURIComponent(encodeURIComponent(title)) +
                '&key=' + key;

            var fullUrl = url + '?flag=true' + param;

            // 10. 遮罩 + 下载
            var maskContainer = document.querySelector('.device-overview-area') || document.body;
            exportDataMask(key, maskContainer, _loginUserLanguageResource.loadingData);
            openExcelWindow(fullUrl);
        }

        function gotoHistory() {
            if (window.parent) {
                window.parent.postMessage({
                    action: 'switchModule',
                    moduleId: 'DeviceHistoryQuery'
                }, '*');
            }
        }

        /**
         * 动态更新统计标签页（增删改），保持当前激活状态，并按 order 顺序插入
         * @param {Object} config 统计显示配置，如 { FESdiagramResult: true, CommStatus: true, ... }
         * @param {string} deviceTypeId 设备类型ID（用于请求数据）
         * @param {string} orgId 组织ID
         */
        function updateStatTabs(config, deviceTypeId, orgId) {
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

        // ================================================================
        // 7. 统计饼图
        // ================================================================
        function loadStatCharts(deviceTypeId, orgId) {
            clearStatFilters();
            var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
            var config = {
                FESdiagramResult: projectTabConfig.DeviceRealTimeMonitoring.FESDiagramStatPie,
                CommStatus: projectTabConfig.DeviceRealTimeMonitoring.CommStatusStatPie,
                RunStatus: projectTabConfig.DeviceRealTimeMonitoring.RunStatusStatPie,
                NumStatus: projectTabConfig.DeviceRealTimeMonitoring.NumStatusStatPie
            };

            updateStatTabs(config, deviceTypeId, orgId);
        }

        function onStatTabChanged(e) {
            var tab = e.tab;
            if (!tab) return;

            // 1. 清空所有统计筛选条件
            clearStatFilters();

            // 2. 清空设备下拉框
            var deviceCombo = mini.get('deviceCombo');
            if (deviceCombo) {
                deviceCombo.setValue('');
                deviceCombo.setText('');
            }

            // 3. 加载当前统计标签的数据
            var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var orgId = window.parent && window.parent.mini ?
                window.parent.mini.get('leftOrg_Id').getValue() : '';
            loadStatData(tab, deviceTypeId, orgId);

            // 4. ★★★ 刷新设备列表（应用清空后的筛选条件，即显示全部设备） ★★★
            refreshDeviceList();

            console.log('统计标签切换完成，已清空筛选并刷新设备列表');
        }

        function loadStatData(tab, deviceTypeId, orgId) {
            if (!tab || !tab._api) return;
            var divId = tab._divId;
            var container = document.getElementById(divId);
            if (container) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>';
            }

            $.ajax({
                url: context + tab._api,
                type: 'POST',
                data: {
                    orgId: orgId || '',
                    deviceType: deviceTypeId || '0'
                },
                dataType: 'text',
                timeout: 10000,
                success: function(responseText) {
                    try {
                        var fixedJson = responseText
                            .replace(/(\{|\,)\s*([a-zA-Z_][a-zA-Z0-9_]*)\s*(\:)/g, '$1"$2"$3')
                            .replace(/'([^']*)'/g, '"$1"');
                        var result = JSON.parse(fixedJson);

                        if (result.AlarmShowStyle && window.parent && window.parent.mini) {
                            var alarmInput = window.parent.mini.get('AlarmShowStyle_Id');
                            if (alarmInput) {
                                alarmInput.setValue(JSON.stringify(result.AlarmShowStyle));
                            }
                        }
                        var data = extractPieData(result, tab._key, result.AlarmShowStyle);
                        renderPieChart(divId, data, tab.title, tab._key);
                    } catch (e) {
                        console.error('JSON解析失败:', e);
                        if (container) {
                            container.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">数据格式错误</div>';
                        }
                    }
                },
                error: function() {
                    if (container) {
                        container.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">' + _loginUserLanguageResource.requestFailed + '</div>';
                    }
                }
            });
        }



        function handlePieClick(e, tabKey) {
            // 重置行选中
            var selectRowInput = document.getElementById('RealTimeMonitoringInfoDeviceListSelectRow_Id');
            if (selectRowInput) selectRowInput.value = -1;

            // 确定要更新的隐藏字段 ID
            var fieldId = '';
            switch (tabKey) {
                case 'FESdiagramResult':
                    fieldId = 'RealTimeMonitoringStatSelectFESdiagramResult_Id';
                    break;
                case 'CommStatus':
                    fieldId = 'RealTimeMonitoringStatSelectCommStatus_Id';
                    break;
                case 'RunStatus':
                    fieldId = 'RealTimeMonitoringStatSelectRunStatus_Id';
                    break;
                case 'NumStatus':
                    fieldId = 'RealTimeMonitoringStatSelectNumStatus_Id';
                    break;
                default:
                    return;
            }
            var fieldInput = document.getElementById(fieldId);
            if (!fieldInput) return;

            // 切换选中状态
            if (e.point.selected) {
                // 如果已选中，则取消选中
                fieldInput.value = '';
            } else {
                // 选中：数值状态使用 level 值，其他使用名称
                if (tabKey === 'NumStatus') {
                    fieldInput.value = e.point.level !== undefined ? e.point.level : '';
                } else {
                    fieldInput.value = e.point.name;
                }
            }

            // 清空设备下拉框
            var deviceCombo = mini.get('deviceCombo');
            if (deviceCombo) {
                deviceCombo.setValue('');
                deviceCombo.setText('');
            }

            // 刷新设备列表
            refreshDeviceList();
        }

        /**
         * 创建饼图 Highcharts 实例
         * @param {string} divId - 容器ID
         * @param {Array} data - 饼图数据
         * @param {string} title - 图表标题
         * @param {string} tabKey - 统计标签类型（用于点击事件）
         * @returns {Highcharts.Chart} 图表实例
         */
        function createPieChartInstance(divId, data, title, tabKey) {
            return Highcharts.chart(divId, {
                chart: {
                    type: 'pie',
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    zooming: {
                        mouseWheel: {
                            enabled: false
                        }
                    }
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: title || '',
                    style: {
                        fontSize: '13px'
                    }
                },
                tooltip: {
                    pointFormat: _loginUserLanguageResource.deviceCount + ': <b>{point.y}</b> ' +
                        _loginUserLanguageResource.proportion + ': <b>{point.percentage:.1f}%</b>'
                },
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal',
                    itemHiddenStyle: {
                        textDecoration: 'none'
                    }
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            format: '<b>{point.name}</b>: {point.y}'
                        },
                        showInLegend: true,
                        events: {
                            click: function(e) {
                                handlePieClick(e, tabKey);
                            }
                        }
                    }
                },
                exporting: {
                    enabled: true,
                    filename: title,
                    fallbackToExportServer: false
                },
                series: [{
                    type: 'pie',
                    name: '数量',
                    data: data
                }]
            });
        }

        function renderPieChart(divId, data, title, tabKey) {
            var container = document.getElementById(divId);
            if (!container) return;

            // 清理旧图表和 observer
            destroyPieChart(container);

            // 保存数据供重绘使用
            container._pieData = data;
            container._pieTitle = title;
            container._pieTabKey = tabKey;

            // 无数据时显示占位
            if (data.length === 1 && data[0].name === _loginUserLanguageResource.emptyMsg) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            // 创建图表
            var chart = createPieChartInstance(divId, data, title, tabKey);
            container._chart = chart;

            // ★ 添加 ResizeObserver 监听容器尺寸变化（防抖重绘）
            if (window.ResizeObserver) {
                var observer = new ResizeObserver(function() {
                    if (container._resizeTimer) {
                        clearTimeout(container._resizeTimer);
                    }
                    container._resizeTimer = setTimeout(function() {
                        recreatePieChart(container);
                        container._resizeTimer = null;
                    }, 200); // 200ms 防抖
                });
                observer.observe(container);
                container._resizeObserver = observer;
            }
        }

        function recreatePieChart(container) {
            if (!container || !container._pieData) return;
            var divId = container.id;
            var data = container._pieData;
            var title = container._pieTitle;
            var tabKey = container._pieTabKey;

            // 销毁旧图表
            if (container._chart) {
                container._chart.destroy();
                container._chart = null;
            }

            // 清空容器（保留外层样式）
            container.innerHTML = '';

            // 重建图表
            var chart = createPieChartInstance(divId, data, title, tabKey);
            container._chart = chart;
        }

        function destroyPieChart(container) {
            if (!container) return;
            if (container._chart) {
                container._chart.destroy();
                container._chart = null;
            }
            if (container._resizeObserver) {
                container._resizeObserver.disconnect();
                container._resizeObserver = null;
            }
            if (container._resizeTimer) {
                clearTimeout(container._resizeTimer);
                container._resizeTimer = null;
            }
            // 清理保存的数据（可选）
            container._pieData = null;
            container._pieTitle = null;
            container._pieTabKey = null;
        }
        /**
         * 清空所有统计饼图筛选条件
         */
        function clearStatFilters() {
            var ids = [
                'RealTimeMonitoringStatSelectFESdiagramResult_Id',
                'RealTimeMonitoringStatSelectCommStatus_Id',
                'RealTimeMonitoringStatSelectRunStatus_Id',
                'RealTimeMonitoringStatSelectNumStatus_Id',
                'RealTimeMonitoringStatSelectDeviceType_Id'
            ];
            for (var i = 0; i < ids.length; i++) {
                var el = document.getElementById(ids[i]);
                if (el) el.value = '';
            }
        }

        // ================================================================
        // 8. 中间区域
        // ================================================================
        function initMiddleTabs() {
            middleTabs = mini.get('middleTabs');
            if (!middleTabs) return;
            // 不再设置固定标签，等设备选中后由 refreshDeviceTabs 填充
            // 但为了首次加载显示占位，可先放一个临时标签
            middleTabs.setTabs([{
                name: 'middle_placeholder',
                title: _loginUserLanguageResource.checkOne,
                body: '<div class="loading-placeholder">' + _loginUserLanguageResource.checkOne + '</div>'
            }]);
        }

        function onMiddleTabChanged(e) {
            var tab = e.tab;
            if (!tab) return;
            currentMiddleTab = tab.name;
            console.log('中间Tab切换:', currentMiddleTab);
            if (currentDeviceId > 0) {
                refreshMiddleTabs();
                // 如果切换到动态数据，解析 mini-toolbar
                if (currentMiddleTab === 'middle_DynamicData') {
                    var bodyEl = middleTabs.getTabBodyEl(tab);
                    if (bodyEl) {
                        mini.parse(bodyEl);
                    }
                }
            } else {
                var body = middleTabs.getTabBodyEl(currentMiddleTab);
                if (body) {
                    body.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.checkOne + '</div>';
                }
            }
        }

        function createMiddleTabObject(name) {
            var bodyMap = {
                'middle_WellboreAnalysis': '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>',
                'middle_SurfaceAnalysis': '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>',
                'middle_TrendCurve': '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>',
                'middle_DynamicData': '<div style="display:flex; flex-direction:column; height:100%;">' +
                    '<div class="mini-toolbar" style="border:0;border-bottom:1px solid #ddd;padding:4px 8px;flex-shrink:0;display:flex;align-items:center;gap:6px;">' +
                    '<span style="font-size:12px;color:#333;">' + (_loginUserLanguageResource.viewCurveOrTableData) + '</span>' +
                    '<span style="flex:1;"></span>' +
                    '<button id="dynamicDataExportBtn" class="mini-button" iconCls="export" style="padding:2px 12px;" onclick="exportDeviceRealTimeMonitoringData()">' + (_loginUserLanguageResource.exportData) + '</button>' +
                    '</div>' +
                    '<div id="RealTimeMonitoringInfoDataTableInfoDiv_id" style="flex:1; overflow:hidden; background:#fff; min-height:0;"></div>' +
                    '</div>',
                'middle_placeholder': '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>'
            };
            var titleMap = {
                'middle_WellboreAnalysis': _loginUserLanguageResource.wellboreAnalysis,
                'middle_SurfaceAnalysis': _loginUserLanguageResource.surfaceAnalysis,
                'middle_TrendCurve': _loginUserLanguageResource.trendCurve,
                'middle_DynamicData': _loginUserLanguageResource.dynamicData,
                'middle_placeholder': '无可用标签'
            };
            return {
                name: name,
                title: titleMap[name] || name,
                body: bodyMap[name] || '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>'
            };
        }

        function createRightTabObject(name) {
            var bodyMap = {
                'right_DeviceControl': '<div id="right_DeviceControl_container" style="width:100%;height:100%;"></div>',
                'right_DeviceInfo': '<div id="right_DeviceInfo_container" style="width:100%;height:100%;"></div>',
                'right_placeholder': '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>'
            };
            var titleMap = {
                'right_DeviceControl': _loginUserLanguageResource.deviceControl,
                'right_DeviceInfo': _loginUserLanguageResource.deviceInformation,
                'right_placeholder': _loginUserLanguageResource.emptyMsg
            };
            return {
                name: name,
                title: titleMap[name] || name,
                body: bodyMap[name] || '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>'
            };
        }

        /**
         * 根据选中的设备动态刷新中间和右侧标签
         * @param {Object} selected 选中的设备记录（包含 id, deviceName, calculateType, videoNum, controlItemNum, addInfoNum, auxiliaryDeviceNum 等）
         */
        function refreshDeviceTabs(selected) {
            if (!selected) return;

            var deviceInfo = getDeviceTabInstanceInfoByDeviceId(selected.id);
            var config = deviceInfo.config || {};
            var deviceRealTimeMonitoring = config.DeviceRealTimeMonitoring || {};
            var calculateType = selected.calculateType || 0;

            // 构建当前设备允许的标签集合（按顺序）
            var allowedMiddleNames = [];
            if (deviceRealTimeMonitoring.WellboreAnalysis === true && calculateType == 1) {
                allowedMiddleNames.push('middle_WellboreAnalysis');
            }
            if (deviceRealTimeMonitoring.SurfaceAnalysis === true && calculateType == 1) {
                allowedMiddleNames.push('middle_SurfaceAnalysis');
            }
            if (deviceRealTimeMonitoring.TrendCurve === true) {
                allowedMiddleNames.push('middle_TrendCurve');
            }
            if (deviceRealTimeMonitoring.DynamicData === true) {
                allowedMiddleNames.push('middle_DynamicData');
            }
            if (allowedMiddleNames.length === 0) {
                // 占位标签
                allowedMiddleNames.push('middle_placeholder');
            }

            // ---- 2. 更新中间标签 ----
            if (middleTabs) {
                var currentTabs = middleTabs.getTabs();
                var activeTab = middleTabs.getActiveTab();
                var activeName = activeTab ? activeTab.name : null;

                // 移除当前标签中不在 allowedMiddleNames 中的
                for (var i = currentTabs.length - 1; i >= 0; i--) {
                    var tab = currentTabs[i];
                    if (allowedMiddleNames.indexOf(tab.name) === -1) {
                        middleTabs.removeTab(tab);
                    }
                }

                // 按顺序添加缺失的标签
                for (var i = 0; i < allowedMiddleNames.length; i++) {
                    var name = allowedMiddleNames[i];
                    var existing = middleTabs.getTab(name);
                    if (!existing) {
                        // 创建 tab 对象（需根据 name 生成 body）
                        var tabObj = createMiddleTabObject(name);
                        // 计算插入位置：在已有标签中，找到第一个位置大于当前标签的位置
                        var insertIndex = 0;
                        for (var j = 0; j < currentTabs.length; j++) {
                            var pos = allowedMiddleNames.indexOf(currentTabs[j].name);
                            if (pos !== -1 && pos < i) {
                                insertIndex = j + 1;
                            }
                        }
                        middleTabs.addTab(tabObj, insertIndex);
                    }
                }

                // 激活标签
                var targetTab = null;
                if (activeName && middleTabs.getTab(activeName)) {
                    targetTab = middleTabs.getTab(activeName);
                }
                if (!targetTab && allowedMiddleNames.length > 0) {
                    targetTab = middleTabs.getTab(allowedMiddleNames[0]);
                }
                if (targetTab) {
                    middleTabs.activeTab(targetTab);
                    currentMiddleTab = targetTab.name;
                }
            }

            // ---- 3. 右侧标签同理 ----
            var allowedRightNames = [];
            if (deviceRealTimeMonitoring.DeviceControl === true) {
                allowedRightNames.push('right_DeviceControl');
            }
            if (deviceRealTimeMonitoring.DeviceInformation === true) {
                allowedRightNames.push('right_DeviceInfo');
            }
            if (allowedRightNames.length === 0) {
                allowedRightNames.push('right_placeholder');
            }

            if (rightTabs) {
                var currentRightTabs = rightTabs.getTabs();
                var rightActiveTab = rightTabs.getActiveTab();
                var rightActiveName = rightActiveTab ? rightActiveTab.name : null;

                // 移除
                for (var i = currentRightTabs.length - 1; i >= 0; i--) {
                    var tab = currentRightTabs[i];
                    if (allowedRightNames.indexOf(tab.name) === -1) {
                        rightTabs.removeTab(tab);
                    }
                }

                // 按顺序添加
                for (var i = 0; i < allowedRightNames.length; i++) {
                    var name = allowedRightNames[i];
                    var existing = rightTabs.getTab(name);
                    if (!existing) {
                        var tabObj = createRightTabObject(name);
                        var insertIndex = 0;
                        for (var j = 0; j < currentRightTabs.length; j++) {
                            var pos = allowedRightNames.indexOf(currentRightTabs[j].name);
                            if (pos !== -1 && pos < i) {
                                insertIndex = j + 1;
                            }
                        }
                        rightTabs.addTab(tabObj, insertIndex);
                    }
                }

                var targetRightTab = null;
                if (rightActiveName && rightTabs.getTab(rightActiveName)) {
                    targetRightTab = rightTabs.getTab(rightActiveName);
                }
                if (!targetRightTab && allowedRightNames.length > 0) {
                    targetRightTab = rightTabs.getTab(allowedRightNames[0]);
                }
                if (targetRightTab) {
                    rightTabs.activeTab(targetRightTab);
                }
            }

            window._currentCalculateType = calculateType;
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
            var minH = dynamometerCardMinHeight || 350; // 默认350
            tabBody.innerHTML = '<div class="chart-grid" id="wellboreGrid">' +
                '<div class="chart-item" style="min-height:' + minH + 'px;"><div id="wellboreChart1" class="chart-container"></div></div>' +
                '<div class="chart-item" style="min-height:' + minH + 'px;"><div id="wellboreChart2" class="chart-container"></div></div>' +
                '<div class="chart-item" style="min-height:' + minH + 'px;"><div id="wellboreChart3" class="chart-container"></div></div>' +
                '<div class="chart-item" style="min-height:' + minH + 'px;"><div id="wellboreChart4" class="chart-container"></div></div>' +
                '</div>';

            $.ajax({
                url: context + '/realTimeMonitoringController/querySingleFESDiagramDetailsChartsData',
                type: 'POST',
                data: {
                    id: deviceId,
                    type: 1
                },
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
                            if (el) el.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        });
                    }
                },
                error: function(xhr, status, errorThrown) {
                    console.log('请求失败:', status, errorThrown);
                    var charts = ['wellboreChart1', 'wellboreChart2', 'wellboreChart3', 'wellboreChart4'];
                    charts.forEach(function(id) {
                        var el = document.getElementById(id);
                        if (el) el.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">' + _loginUserLanguageResource.requestFailed + ': ' + status + '</div>';
                    });
                }
            });
        }

        // ---------- 地面分析 ----------
        function loadSurfaceAnalysis(deviceId) {
            var tabBody = middleTabs.getTabBodyEl('middle_SurfaceAnalysis');
            if (!tabBody) return;
            var minH = dynamometerCardMinHeight || 350;
            tabBody.innerHTML = '<div class="chart-grid" id="surfaceGrid">' +
                '<div class="chart-item" style="min-height:' + minH + 'px;"><div id="surfaceChart1" class="chart-container"></div></div>' +
                '<div class="chart-item" style="min-height:' + minH + 'px;"><div id="surfaceChart2" class="chart-container"></div></div>' +
                '<div class="chart-item" style="min-height:' + minH + 'px;"><div id="surfaceChart3" class="chart-container"></div></div>' +
                '<div class="chart-item" style="min-height:' + minH + 'px;"><div id="surfaceChart4" class="chart-container"></div></div>' +
                '</div>';

            $.ajax({
                url: context + '/realTimeMonitoringController/querySingleFESDiagramDetailsChartsData',
                type: 'POST',
                data: {
                    id: deviceId,
                    type: 2
                },
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
                                _loginUserLanguageResource.currentTorqueCurve,
                                result.deviceName || '',
                                result.acqTime || '',
                                'surfaceChart2'
                            );
                        } else {
                            document.getElementById('surfaceChart2').innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        }
                        showASDiagram(result, 'surfaceChart3');
                        if (result.crankAngle && result.loadRorque && result.crankTorque && result.expectedBalanceTorque && result.expectedNetTorque) {
                            var deltaRadius = parseFloat(result.deltaRadius) || 0;
                            var expectedTitle = _loginUserLanguageResource.expectTorqueCurve;
                            if (deltaRadius !== 0) {
                                expectedTitle = (deltaRadius > 0 ? _loginUserLanguageResource.moveTowardOutside : _loginUserLanguageResource.moveTowardInside) + Math.abs(deltaRadius) + 'cm ' + expectedTitle;
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
                            document.getElementById('surfaceChart4').innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        }
                    } else {
                        ['surfaceChart1', 'surfaceChart2', 'surfaceChart3', 'surfaceChart4'].forEach(function(id) {
                            var el = document.getElementById(id);
                            if (el) el.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        });
                    }
                },
                error: function() {
                    ['surfaceChart1', 'surfaceChart2', 'surfaceChart3', 'surfaceChart4'].forEach(function(id) {
                        var el = document.getElementById(id);
                        if (el) el.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">' + _loginUserLanguageResource.requestFailed + '</div>';
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
            tabBody.innerHTML = '<div class="loading-placeholder"><span class="icon">⏳</span>' + _loginUserLanguageResource.loadingData + '</div>';

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
                        tabBody.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        return;
                    }

                    var data = result.list;
                    var curveNames = result.curveItems || [];
                    var deviceNameResult = result.deviceName || deviceName || '';
                    var curveCount = data.length > 0 ? data[0].data.length : 0;

                    if (curveCount === 0) {
                        tabBody.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
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

                    // ★★ 计算每个图表的宽高百分比 ★★
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
                    var minChartHeight = dynamometerCardMinHeight; // 最小高度（像素）

                    // 创建 flex wrap 容器
                    var container = document.createElement('div');
                    container.id = 'trendContainer';
                    tabBody.appendChild(container);

                    // 遍历曲线，每个曲线一个图表
                    for (var i = 0; i < curveCount; i++) {
                        var divId = 'trendChart_' + i + '_' + Date.now();
                        var chartDiv = document.createElement('div');
                        chartDiv.className = 'trend-chart-item';

                        // ★ 使用 flex 布局，保留原宽高逻辑，同时设置最小尺寸
                        var flexBasis = chartWidth; // "100%" 或 "50%"
                        var heightPercent = chartHeight; // "100%" 或 "50%"
                        chartDiv.style.cssText =
                            'flex: 1 1 ' + flexBasis + '; ' + // flex-basis 控制宽度比例
                            'height: ' + heightPercent + '; ' +
                            'min-width: 300px; ' +
                            'min-height: ' + dynamometerCardMinHeight + 'px; ' +
                            'box-sizing: border-box; ' +
                            'padding: 2px; ' +
                            'position: relative; ' +
                            'overflow: hidden;';


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
                            innerDiv.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                            continue;
                        }

                        var yTitle = curveNames[i];
                        var titleText = deviceNameResult + ':' + yTitle + (_loginUserLanguage != 'zh_CN' ? ' ' : '') + _loginUserLanguageResource.trendCurve;
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
                            marker: {
                                enabled: true,
                                radius: 2
                            }
                        }];

                        // 计算 yAxis 范围
                        var allPositive = true,
                            allNegative = true;
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
                    console.error(_loginUserLanguageResource.requestFailed + ':', status, errorThrown);
                    tabBody.innerHTML = '<div class="loading-placeholder error"><span class="icon">❌</span>' + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        // ================================================================
        // Highstock 绘图函数（修复变量引用）
        // ================================================================
        function initDeviceRealtimeMonitoringStockChartFn(series, tickInterval, divId, title, subtitle, xtitle, yTitle, color, legend, navigator, scrollbar, timeFormat, maxValue, minValue, yAxisOpposite) {
            if ($("#" + divId).length === 0) return;
            var lang = _loginUserLanguageResource || {};
            var hourLabel = lang.hour;
            var allLabel = lang.all;
            var fontSize = chartTitleFontSize;
            var extremesTimer = null;

            var chart = new Highcharts.stockChart({
                chart: {
                    renderTo: divId,
                    type: 'spline',
                    shadow: false,
                    borderWidth: 0,
                    zooming: {
                        mouseWheel: {
                            enabled: false
                        }
                    },
                    zoomType: 'xy',
                    animation: false
                },
                time: {
                    timezoneOffset: new Date().getTimezoneOffset()
                },
                credits: {
                    enabled: false
                },
                navigator: {
                    enabled: navigator !== false,
                    maskInside: true,
                    series: {
                        data: series[0].data,
                        dataGrouping: {
                            enabled: true,
                            groupPixelWidth: 8,
                            approximation: 'average'
                        },
                        turboThreshold: 5000,
                        animation: false
                    }
                },
                scrollbar: {
                    enabled: scrollbar === true
                },
                rangeSelector: {
                    buttons: [{
                            count: 1,
                            type: 'hour',
                            text: '1' + hourLabel
                        },
                        {
                            count: 6,
                            type: 'hour',
                            text: '6' + hourLabel
                        },
                        {
                            count: 12,
                            type: 'hour',
                            text: '12' + hourLabel
                        },
                        {
                            count: 24,
                            type: 'hour',
                            text: '24' + hourLabel
                        },
                        {
                            type: 'all',
                            text: allLabel
                        }
                    ],
                    buttonTheme: {
                        width: getLabelWidth('24' + hourLabel)
                    },
                    dropdown: 'responsive',
                    inputEnabled: false,
                    selected: 0
                },
                title: {
                    text: title,
                    style: {
                        fontSize: fontSize
                    }
                },
                subtitle: {
                    text: subtitle
                },
                colors: color,
                xAxis: {
                    type: 'datetime',
                    title: {
                        text: xtitle
                    },
                    tickPixelInterval: 120,
                    minTickInterval: 5 * 60 * 1000,
                    labels: {
                        formatter: function() {
                            var minTime = this.axis.min,
                                maxTime = this.axis.max;
                            var minDate = new Date(minTime),
                                maxDate = new Date(maxTime);
                            minDate.setHours(0, 0, 0, 0);
                            maxDate.setHours(0, 0, 0, 0);
                            return minDate.getTime() !== maxDate.getTime() ?
                                this.axis.chart.time.dateFormat('%m-%d %H:%M', this.value) :
                                this.axis.chart.time.dateFormat('%H:%M', this.value);
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
                    title: {
                        text: yTitle
                    },
                    opposite: yAxisOpposite || false
                },
                tooltip: {
                    crosshairs: true,
                    shared: true,
                    valueDecimals: 2,
                    style: {
                        color: '#333333',
                        fontSize: '12px',
                        padding: '8px'
                    },
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
                            menuItems: ['viewFullscreen', 'printChart', 'separator', 'downloadPNG', 'downloadJPEG', 'downloadSVG', 'separator', 'downloadCSV', 'downloadXLS']
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
                            states: {
                                hover: {
                                    enabled: true,
                                    radius: 6
                                }
                            }
                        },
                        shadow: true,
                        dataGrouping: {
                            enabled: false,
                            groupPixelWidth: 20,
                            approximation: 'average'
                        },
                        turboThreshold: 5000,
                        animation: false
                    }
                },
                legend: {
                    layout: 'horizontal',
                    align: 'center',
                    verticalAlign: 'bottom',
                    enabled: legend || false,
                    borderWidth: 0,
                    itemHiddenStyle: {
                        textDecoration: 'none'
                    }
                },
                series: series
            });
        }

        //---------- 动态数据 ----------
        function loadDynamicData(deviceId) {
            var container = document.getElementById('RealTimeMonitoringInfoDataTableInfoDiv_id');
            if (!container) return;

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

            container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>';

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
                    if (result.totalRoot && result.totalRoot.length > 0) {
                        // 创建或更新 MiniUI Grid
                        createDeviceRealTimeMonitoringGrid('RealTimeMonitoringInfoDataTableInfoDiv_id', result.totalRoot, result.CellInfo);
                    } else {
                        container.innerHTML = '<div class="loading-placeholder">无数据</div>';
                    }
                },
                error: function(xhr, status, errorThrown) {
                    console.error(_loginUserLanguageResource.requestFailed + ':', status, errorThrown);
                    container.innerHTML = '<div class="loading-placeholder error"><span class="icon">❌</span>' + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        function viewDeviceRealTimeMonitoringData(row, col) {
            if (!deviceRealTimeMonitoringGrid || row < 1) return;
            var grid = deviceRealTimeMonitoringGrid;
            var record = grid.getAt(row);
            if (!record) return;

            var itemName = record['name' + (col + 1)];
            var itemValue = record['value' + (col + 1)];

            var cellInfo = grid._cellInfo || [];
            var info = null;
            for (var i = 0; i < cellInfo.length; i++) {
                if (cellInfo[i].row === row && cellInfo[i].col === col) {
                    info = cellInfo[i];
                    break;
                }
            }
            if (!info) {
                console.warn('未找到 CellInfo:', row, col);
                return;
            }

            // 判断是否为有效数值（用于曲线显示）
            var isNumeric = function(val) {
                return val !== undefined && val !== null && val !== '' && !isNaN(parseFloat(val));
            };

            var type = info.type;
            var resolutionMode = info.resolutionMode;
            var columnDataType = info.columnDataType || '';
            var column = info.column;

            // 根据类型和分辨率决定打开曲线还是数据表
            if (type == 0) {
                // 采集项
                if (resolutionMode == 2) {
                    // 数值量
                    if (columnDataType.toUpperCase() !== 'STRING' && isNumeric(itemValue)) {
                        viewItemRealTimeCurve(itemName, itemValue, info);
                    } else {
                        viewItemRealTimeDataTable(itemName, itemValue, info);
                    }
                } else if (resolutionMode == 0 || resolutionMode == 1) {
                    // 开关量或枚举量 → 数据表
                    viewItemRealTimeDataTable(itemName, itemValue, info);
                } else {
                    viewItemRealTimeDataTable(itemName, itemValue, info);
                }
            } else if (type == 1) {
                // 计算项
                if (isNumByCalculateItemCode(column)) {
                    viewItemRealTimeCurve(itemName, itemValue, info);
                } else {
                    viewItemRealTimeDataTable(itemName, itemValue, info);
                }
            } else if (type == 3) {
                // 录入项
                if (isNumeric(itemValue)) {
                    viewItemRealTimeCurve(itemName, itemValue, info);
                } else {
                    viewItemRealTimeDataTable(itemName, itemValue, info);
                }
            } else if (type == 5) {
                // 协议拓展项
                if (resolutionMode == 2) {
                    // 数据量
                    if (isNumeric(itemValue)) {
                        viewItemRealTimeCurve(itemName, itemValue, info);
                    } else {
                        viewItemRealTimeDataTable(itemName, itemValue, info);
                    }
                } else if (resolutionMode == 0 || resolutionMode == 1) {
                    // 开关量或枚举量
                    viewItemRealTimeDataTable(itemName, itemValue, info);
                } else if (resolutionMode == 7) {
                    // 数值运算项
                    if (isNumeric(itemValue)) {
                        viewItemRealTimeCurve(itemName, itemValue, info);
                    } else {
                        viewItemRealTimeDataTable(itemName, itemValue, info);
                    }
                } else {
                    viewItemRealTimeDataTable(itemName, itemValue, info);
                }
            } else {
                // 默认打开数据表
                viewItemRealTimeDataTable(itemName, itemValue, info);
            }
        }

        //存储当前曲线窗口上下文
        var _curveWindowContext = null;

        function viewItemRealTimeCurve(itemName, itemValue, cellInfo) {
            // 获取当前选中的设备信息
            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            if (!selected) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }

            var deviceId = selected.id;
            var deviceName = selected.deviceName;
            var calculateType = selected.calculateType || 0;

            // 创建 MiniUI 窗口
            var win = new mini.Window();
            win.set({
                title: _loginUserLanguageResource.trendCurve,
                width: '70%',
                height: '60%',
                minWidth: 500,
                minHeight: 300,
                modal: true,
                showHeader: true,
                allowResize: true,
                maxable: true,
                minable: true,
                showCloseButton: true
            });
            win.show();

            // 图表容器
            var containerId = 'ItemRealtimeCurveContainer_' + Date.now();
            var html = '<div style="width:100%;height:100%;min-height:' + otherCardMinHeight + 'px;overflow:hidden;position:relative;">' +
                '<div id="' + containerId + '" style="width:100%;height:100%;"></div>' +
                '</div>';
            win.setBody(html);

            // 保存上下文
            _curveWindowContext = {
                win: win,
                containerId: containerId,
                deviceId: deviceId,
                deviceName: deviceName,
                calculateType: calculateType,
                itemName: itemName,
                itemCode: cellInfo.column,
                itemType: cellInfo.type,
                itemResolutionMode: cellInfo.resolutionMode
            };

            // 加载数据
            getItemRealTimeCurveData();

            // 窗口关闭时清理
            win.on('beforedestroy', function() {
                // 销毁 Highcharts 实例
                var container = document.getElementById(containerId);
                if (container) {
                    var charts = Highcharts.charts || [];
                    for (var i = 0; i < charts.length; i++) {
                        if (charts[i] && charts[i].renderTo === container) {
                            charts[i].destroy();
                            break;
                        }
                    }
                }
                _curveWindowContext = null;
            });
        }

        // 获取曲线数据（完全复用原有逻辑，仅修改 UI 交互）
        function getItemRealTimeCurveData() {
            var ctx = _curveWindowContext;
            if (!ctx) return;

            // 显示遮罩
            var mask = mini.mask({
                el: ctx.win.getBodyEl(),
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });

            $.ajax({
                url: context + '/realTimeMonitoringController/getItemRealTimeCurveData',
                type: 'POST',
                data: {
                    deviceName: ctx.deviceName,
                    deviceId: ctx.deviceId,
                    calculateType: ctx.calculateType,
                    itemName: ctx.itemName,
                    itemCode: ctx.itemCode,
                    itemType: ctx.itemType,
                    itemResolutionMode: ctx.itemResolutionMode
                },
                dataType: 'json',
                timeout: 15000,
                success: function(result) {
                    mini.unmask(ctx.win.getBodyEl());
                    if (!result || !result.list || result.list.length === 0) {
                        document.getElementById(ctx.containerId).innerHTML = '<div style="text-align:center;padding:20px;">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        return;
                    }

                    // ---------- 以下完全复用原有绘图逻辑 ----------
                    var data = result.list;
                    var legendName = result.curveItems;
                    var title = result.deviceName + ':' + legendName[0].split('(')[0] + (_loginUserLanguageResource.trendCurve);
                    var subtitle = '';

                    // 构建 series
                    var seriesData = [];
                    for (var j = 0; j < data.length; j++) {
                        var timestamp = Date.parse(data[j].acqTime.replace(/-/g, '/'));
                        var value = parseFloat(data[j].data);
                        if (!isNaN(value)) {
                            seriesData.push([timestamp, value]);
                        }
                    }
                    var series = [{
                        name: legendName[0],
                        data: seriesData,
                        lineWidth: 2,
                        marker: {
                            enabled: true
                        }
                    }];

                    // 计算 yAxis 范围
                    var allPositive = true,
                        allNegative = true;
                    for (var k = 0; k < seriesData.length; k++) {
                        var v = seriesData[k][1];
                        if (v < 0) allPositive = false;
                        if (v >= 0) allNegative = false;
                    }
                    var maxValue = allNegative ? 0 : null;
                    var minValue = allPositive ? 0 : null;

                    // 使用 Highcharts 渲染（复用现有绘图函数）
                    initDeviceRealtimeMonitoringStockChartFn(
                        series,
                        undefined,
                        ctx.containerId,
                        title,
                        subtitle,
                        _loginUserLanguageResource.time,
                        legendName[0],
                        ['#7cb5ec'],
                        false,
                        true,
                        false,
                        '%H:%M',
                        maxValue,
                        minValue,
                        false
                    );
                },
                error: function(xhr, status, errorThrown) {
                    mini.unmask(mask);
                    document.getElementById(ctx.containerId).innerHTML = '<div style="text-align:center;padding:20px;color:red;">' + _loginUserLanguageResource.requestFailed + '</div>';
                    console.error('加载曲线数据失败:', status, errorThrown);
                }
            });
        }

        //存储当前数据表窗口上下文
        var _dataWindowContext = null;

        function viewItemRealTimeDataTable(itemName, itemValue, cellInfo) {
            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            if (!selected) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }

            var deviceId = selected.id;
            var deviceName = selected.deviceName;
            var calculateType = selected.calculateType || 0;

            // 创建 MiniUI 窗口
            var win = new mini.Window();
            win.set({
                title: _loginUserLanguageResource.dynamicData,
                width: 500,
                height: '80%',
                minWidth: 400,
                minHeight: 300,
                modal: true,
                showHeader: true,
                allowResize: true,
                maxable: true,
                minable: true,
                showCloseButton: true
            });
            win.show();

            // 构造 HTML：工具栏 + 表格容器
            var containerId = 'ItemRealtimeDataDiv_' + Date.now();
            var html = '<div style="display:flex; flex-direction:column; height:100%;">';
            // 工具栏
            html += '<div style="flex-shrink:0; padding:4px 8px; background:#f5f5f5; border-bottom:1px solid #ddd; display:flex; align-items:center; gap:6px;">';
            html += '<span style="font-size:12px; color:#333;">' + (_loginUserLanguageResource.totalCount) + ': <span id="itemRealtimeDataCount_' + containerId + '">0</span></span>';
            html += '<span style="flex:1;"></span>';
            html += '<button id="exportItemRealtimeDataBtn_' + containerId + '" class="mini-button" iconCls="export" style="padding:2px 12px;">' + (_loginUserLanguageResource.exportData) + '</button>';
            html += '</div>';
            html += '<div id="' + containerId + '" style="flex:1; overflow:hidden; min-height:0;"></div>';
            html += '</div>';
            win.setBody(html);

            mini.parse();

            // 绑定导出按钮
            var exportBtn = mini.get('exportItemRealtimeDataBtn_' + containerId);
            if (exportBtn) {
                exportBtn.on('click', function() {
                    exportItemRealtimeDataTable(deviceId, deviceName, calculateType, itemName, cellInfo.column, cellInfo.type, cellInfo.resolutionMode, cellInfo.bitIndex);
                });
            }

            // 保存上下文
            _dataWindowContext = {
                win: win,
                containerId: containerId,
                countId: 'itemRealtimeDataCount_' + containerId,
                deviceId: deviceId,
                deviceName: deviceName,
                calculateType: calculateType,
                itemName: itemName,
                itemCode: cellInfo.column,
                itemType: cellInfo.type,
                itemResolutionMode: cellInfo.resolutionMode,
                itemBitIndex: cellInfo.bitIndex || ''
            };

            // 加载数据表
            CreateItemRealtimeDataTable();
        }

        // 创建数据表（完全复用原有逻辑，修改 UI 交互）
        function CreateItemRealtimeDataTable() {
            var ctx = _dataWindowContext;
            if (!ctx) return;

            var mask = mini.mask({
                el: ctx.win.getBodyEl(),
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });

            $.ajax({
                url: context + '/realTimeMonitoringController/getItemRealTimeData',
                type: 'POST',
                data: {
                    deviceName: ctx.deviceName,
                    deviceId: ctx.deviceId,
                    calculateType: ctx.calculateType,
                    itemName: ctx.itemName,
                    itemCode: ctx.itemCode,
                    itemType: ctx.itemType,
                    itemResolutionMode: ctx.itemResolutionMode,
                    itemBitIndex: ctx.itemBitIndex
                },
                dataType: 'json',
                timeout: 15000,
                success: function(result) {
                    mini.unmask(ctx.win.getBodyEl());
                    var container = document.getElementById(ctx.containerId);
                    if (!container) {
                        console.warn('容器不存在:', ctx.containerId);
                        return;
                    }

                    // 更新总条数
                    var countEl = document.getElementById(ctx.countId);
                    if (countEl) countEl.innerText = result.totalCount || result.totalRoot.length;

                    // 清空容器
                    container.innerHTML = '';

                    if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                        container.innerHTML = '<div style="text-align:center;padding:20px;color:#999;">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        return;
                    }

                    var data = result.totalRoot;

                    // 创建 MiniUI DataGrid
                    var grid = new mini.DataGrid();
                    grid.set({
                        id: 'itemRealtimeDataGrid_' + Date.now(),
                        style: 'width:100%; height:100%;',
                        showPager: false,
                        allowCellSelect: true,
                        allowCellWrap: false,
                        allowResize: true,
                        virtualScroll: false,
                        allowAlternating: true,
                        data: data,
                        columns: [{
                                type: 'indexcolumn',
                                align: 'center',
                                headerAlign: 'center',
                                width: 70,
                                header: _loginUserLanguageResource.idx
                            },
                            {
                                field: 'acqTime',
                                width: '50%',
                                align: 'center',
                                headerAlign: 'center',
                                header: _loginUserLanguageResource.acqTime,
                                dateFormat: 'yyyy-MM-dd HH:mm:ss'
                            },
                            {
                                field: 'data',
                                width: '50%',
                                align: 'center',
                                headerAlign: 'center',
                                header: ctx.itemName
                            }
                        ]
                    });

                    grid.render(container);

                    // 保存 grid 实例到 window，便于窗口关闭时销毁
                    window._itemRealtimeDataGrid = grid;

                    // 窗口 resize 时调整 grid 大小
                    ctx.win.on('resize', function() {
                        if (window._itemRealtimeDataGrid) {
                            window._itemRealtimeDataGrid.doLayout();
                        }
                    });

                    // 窗口关闭时销毁 grid
                    ctx.win.on('beforedestroy', function() {
                        if (window._itemRealtimeDataGrid) {
                            window._itemRealtimeDataGrid.destroy();
                            window._itemRealtimeDataGrid = null;
                        }
                    });
                },
                error: function(xhr, status, errorThrown) {
                    mini.unmask(mask);
                    var container = document.getElementById(ctx.containerId);
                    if (container) {
                        container.innerHTML = '<div style="text-align:center;padding:20px;color:red;">' + _loginUserLanguageResource.requestFailed + '</div>';
                    }
                    console.error('加载数据表失败:', status, errorThrown);
                }
            });
        }

        // 导出数据表（适配 MiniUI）
        function exportItemRealtimeDataTable(deviceId, deviceName, calculateType, itemName, itemCode, itemType, itemResolutionMode, itemBitIndex) {
            if (!deviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }

            var timestamp = new Date().getTime();
            var key = 'exportItemRealTimeData_' + deviceId + '_' + itemCode + '_' + timestamp;
            var url = context + '/realTimeMonitoringController/exportItemRealTimeData';
            var param = '&deviceId=' + deviceId +
                '&deviceName=' + encodeURIComponent(encodeURIComponent(deviceName)) +
                '&calculateType=' + calculateType +
                '&itemName=' + encodeURIComponent(encodeURIComponent(itemName)) +
                '&itemCode=' + itemCode +
                '&itemType=' + itemType +
                '&itemResolutionMode=' + itemResolutionMode +
                '&itemBitIndex=' + (itemBitIndex || '') +
                '&key=' + key;

            // 遮罩容器为当前窗口或 body
            var container = _dataWindowContext ? _dataWindowContext.win.getBodyEl() : document.body;
            exportDataMask(key, container, _loginUserLanguageResource.loadingData);
            openExcelWindow(url + '?flag=true' + param);
        }

        // ================================================================
        // 9. 右侧区域 - 设备控制 + 设备信息
        // ================================================================
        function initRightTabs() {
            rightTabs = mini.get('rightTabs');
            if (!rightTabs) return;
            rightTabs.setTabs([{
                name: 'right_placeholder',
                title: _loginUserLanguageResource.checkOne,
                body: '<div class="loading-placeholder">' + _loginUserLanguageResource.checkOne + '</div>'
            }]);
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
                if (container) container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.checkOne + '</div>';
                var infoContainer = document.getElementById('right_DeviceInfo_container');
                if (infoContainer) infoContainer.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.checkOne + '</div>';
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
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.noSelectionRecord + '</div>';
                return;
            }
            var selected = grid.getSelected();
            var deviceId = selected.id;
            var deviceName = selected.deviceName;
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

            container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>';

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
                        container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        return;
                    }

                    var gridControl = new mini.DataGrid();
                    gridControl.set({
                        style: 'width:100%;height:100%;',
                        data: result.totalRoot,
                        idField: 'id',
                        showPager: false,
                        allowResize: true,
                        columns: [{
                                field: 'item',
                                header: _loginUserLanguageResource.controlItem,
                                width: '40%',
                                align: 'left'
                            },
                            {
                                field: 'action',
                                header: _loginUserLanguageResource.operation,
                                width: '60%',
                                align: 'center',
                                renderer: function(e) {
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
                                        var btnCount = itemMeaning.length;
                                        for (var i = 0; i < btnCount; i++) {
                                            var text = itemMeaning[i][1];
                                            var value = itemMeaning[i][0];
                                            html += '<button class="mini-button" style="' + btnStyle + '" ' +
                                                (disabled ? 'disabled' : '') +
                                                ' onclick="onEnumControlClick(' + record.id + ', \'' + record.item + '\', \'' + record.itemcode + '\', \'' + record.quantity + '\', \'' + value + '\', \'' + text + '\', ' + disabled + ')">' + text + '</button>';
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
                                            ' onclick="onNumericControlClick(' + record.id + ', \'' + record.itemcode + '\', \'' + record.itemName + '\', \'' + (record.unit || '') + '\', ' + record.quantity + ', \'' + record.storeDataType + '\', ' + disabled + ')">' + _loginUserLanguageResource.set + '</button>';
                                    }

                                    html += '</div>';
                                    return html;
                                }
                            }
                        ]
                    });
                    gridControl.render(container);
                },
                error: function(xhr, status, errorThrown) {
                    console.error('加载控制项失败:', status, errorThrown);
                    container.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
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
                        mini.alert((result.msg));
                    } else if (result.flag == true && result.error == false) {
                        mini.alert(result.msg);
                    } else if (result.flag == true && result.error == true) {
                        mini.alert(result.msg);
                    }
                },
                error: function(xhr, status, errorThrown) {
                    mini.unmask(mask);
                    mini.alert(_loginUserLanguageResource.requestFailed + '：' + status);
                }
            });
        }

        function loadDeviceInfo() {
            var containerId = 'right_DeviceInfo_container';
            var container = document.getElementById(containerId);
            if (!container) return;

            var grid = mini.get('deviceGrid');
            if (!grid || !grid.getSelected()) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.checkOne + '</div>';
                return;
            }
            var selected = grid.getSelected();
            var deviceId = selected.id;
            var deviceName = selected.deviceName || '';
            var calculateType = selected.calculateType || 0;
            var deviceType = currentLevel1 ? currentLevel1.deviceTypeId : '0';

            container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>';

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
                        container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
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
                        html += '<th style="padding:6px 10px; text-align:left; border:1px solid #ddd;">' + _loginUserLanguageResource.variable + '</th>';
                        html += '<th style="padding:6px 10px; text-align:center; border:1px solid #ddd;">' + _loginUserLanguageResource.value + '</th>';
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
                        html += '<div class="loading-placeholder" style="height:100%;">' + _loginUserLanguageResource.emptyMsg + '</div>';
                    }
                    html += '</div>';

                    // ---- 辅件设备（下半部分，默认全部展开，保留列头，无操作列） ----
                    html += '<div style="flex:1; min-height:0; overflow:auto; padding:4px;">';
                    if (auxList.length > 0) {
                        html += '<table style="width:100%; border-collapse:collapse; font-size:12px;">';
                        html += '<thead><tr style="background:#f5f7fa;">';
                        html += '<th style="padding:4px 8px; border:1px solid #ddd; text-align:center; width:50px;">' + _loginUserLanguageResource.idx + '</th>';
                        html += '<th style="padding:4px 8px; border:1px solid #ddd; text-align:left;">' + _loginUserLanguageResource.deviceName + '</th>';
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
                            html += '<div style="word-break:break-all; line-height:1.6;">' + (item.detailsInfo) + '</div>';
                            html += '</td>';
                            html += '</tr>';
                        }
                        html += '</tbody></table>';
                    } else {
                        html += '<div class="loading-placeholder" style="height:100%;">' + _loginUserLanguageResource.emptyMsg + '</div>';
                    }
                    html += '</div>';
                    html += '</div>';

                    container.innerHTML = html;
                },
                error: function(xhr, status, errorThrown) {
                    console.error('加载设备信息失败:', status, errorThrown);
                    container.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        // ================================================================
        // 资源监测相关函数
        // ================================================================
        function openResourceChart(itemCode, itemName) {
            var win = new mini.Window();
            win.set({
                title: itemName.split("(")[0],
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
            var html = '<div style="width:100%;height:100%;min-height:' + otherCardMinHeight + 'px;overflow:hidden;position:relative;">' +
                '<div id="' + divId + '" style="width:100%;height:100%;"></div>' +
                '</div>';
            win.setBody(html);

            loadResourceChartData(itemCode, itemName, divId, win);
        }

        function initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, ytitle, color, legend, timeFormat) {
            if ($("#" + divId) != undefined && $("#" + divId)[0] != undefined) {
                var isZooming = false;
                var zoomTimer = null;
                var $container = $("#" + divId);
                var panelId = "ResourceProbeHistoryCurvePanel_Id";

                var mychart = new Highcharts.Chart({
                    chart: {
                        renderTo: divId,
                        type: 'spline',
                        shadow: false,
                        borderWidth: 0,
                        zoomType: 'xy',
                        // 禁用鼠标滚轮缩放
                        zooming: {
                            mouseWheel: {
                                enabled: false
                            }
                        }
                    },
                    time: {
                        timezoneOffset: new Date().getTimezoneOffset() // 用户本地时区
                    },
                    credits: {
                        enabled: false
                    },
                    title: {
                        text: title,
                        style: {
                            fontSize: chartTitleFontSize
                        }
                    },
                    subtitle: {
                        text: subtitle
                    },
                    colors: color,
                    xAxis: {
                        type: 'datetime',
                        title: {
                            text: xtitle
                        },
                        labels: {
                            formatter: function() {
                                return this.axis.chart.time.dateFormat(timeFormat, this.value);
                            },
                            autoRotation: true, //自动旋转
                            rotation: -45 //倾斜度，防止数量过多显示不全  
                        }
                    },
                    yAxis: [{
                        lineWidth: 1,
                        tickWidth: 1, // 刻度线宽度
                        tickLength: 5, // 刻度线长度（可选）
                        title: {
                            text: ytitle
                        }
                    }],
                    tooltip: {
                        crosshairs: true, //十字准线
                        shared: true,
                        style: {
                            color: '#333333',
                            fontSize: '12px',
                            padding: '8px'
                        },
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
                        sourceWidth: $("#" + divId)[0] != undefined ? $("#" + divId)[0].offsetWidth : null,
                        sourceHeight: $("#" + divId)[0] != undefined ? $("#" + divId)[0].offsetHeight : null,
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
                                radius: 3, //曲线点半径，默认是4
                                states: {
                                    hover: {
                                        enabled: true,
                                        radius: 6
                                    }
                                }
                            },
                            shadow: true
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle',
                        enabled: legend,
                        borderWidth: 0,
                        itemHiddenStyle: {
                            textDecoration: 'none'
                        }
                    },
                    series: series
                });
            }
        };

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
                        document.getElementById(divId).innerHTML = '<div style="text-align:center;padding:20px;">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        return;
                    }
                    var chartData = result.totalRoot;
                    var legend = false;
                    var series = buildResourceSeries(chartData, itemCode, itemName);
                    if (series.length > 0) {
                        legend = true;
                    }
                    var title = itemName.split("(")[0];
                    var subtitle = "[" + result.startDate + "~" + result.endDate + "]";
                    var yTitle = itemName;
                    var tickInterval = Math.floor(chartData.length / 10) + 1;
                    var color = ['#800000', // 红
                        '#008C00', // 绿
                        '#000000', // 黑
                        '#0000FF', // 蓝
                        '#F4BD82', // 黄
                        '#FF00FF' // 紫
                    ];

                    var timeFormat = '%m-%d';
                    if (chartData.length > 0 && result.minAcqTime.split(' ')[0] == result.maxAcqTime.split(' ')[0]) {
                        timeFormat = '%H:%M';
                    }

                    if (typeof initResourceProbeHistoryCurveChartFn === 'function') {
                        initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, _loginUserLanguageResource.time, yTitle, color, legend, timeFormat);
                    }
                },
                error: function() {
                    document.getElementById(divId).innerHTML = '<div style="text-align:center;padding:20px;color:red;">' + _loginUserLanguageResource.requestFailed + '</div>';
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
                    series.push({
                        name: key,
                        data: cpuMap[key]
                    });
                }
            } else if (itemCode === 'jedisStatus') {
                var maxData = [],
                    usedData = [];
                data.forEach(function(item) {
                    var values = item.value.split(';');
                    var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
                    if (values.length >= 2) {
                        maxData.push([ts, parseFloat(values[0])]);
                        usedData.push([ts, parseFloat(values[1])]);
                    }
                });
                series.push({
                    name: 'maxmemory(m)',
                    data: maxData
                });
                series.push({
                    name: 'usedmemory(m)',
                    data: usedData
                });
            } else if (itemCode === 'tableSpaceSize') {
                var dataSpace = [],
                    undoSpace = [];
                data.forEach(function(item) {
                    var values = item.value.split(';');
                    var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
                    if (values.length >= 2) {
                        dataSpace.push([ts, parseFloat(values[0])]);
                        undoSpace.push([ts, parseFloat(values[1])]);
                    }
                });
                series.push({
                    name: _loginUserLanguageResource.dataTablespace + "(%)",
                    data: dataSpace
                });
                series.push({
                    name: _loginUserLanguageResource.undoTablespace + "(%)",
                    data: undoSpace
                });
            } else {
                var singleData = [];
                data.forEach(function(item) {
                    var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
                    var val = parseFloat(item.value);
                    if (!isNaN(val)) singleData.push([ts, val]);
                });
                series.push({
                    name: itemName,
                    data: singleData
                });
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

            buildLevel1Tabs();
            initI18n();

            var grid = mini.get('deviceGrid');
            if (grid && typeof _defaultPageSize !== 'undefined' && _defaultPageSize) {
                grid.setPageSize(parseInt(_defaultPageSize, 10));
            }

            // 使用事件代理监听 .device-name-cell 的 mouseenter/mouseleave（兼容性更好）
            document.addEventListener('mouseover', function(e) {
                var target = e.target.closest('.device-name-cell');
                if (target && !target._tipShown) {
                    // 标记防止重复触发
                    target._tipShown = true;
                    handleDeviceNameCellMouseEnter(target, e);
                }
            });

            document.addEventListener('mouseout', function(e) {
                var target = e.target.closest('.device-name-cell');
                if (target) {
                    target._tipShown = false;
                    hideDeviceNameTip();
                }
            });

            console.log('实时监控模块加载完成');
        });

        //监听父页面消息
        window.addEventListener('message', function(event) {
            var message = event.data;
            if (!message || !message.action) return;
            switch (message.action) {
                case 'updateDeviceData':
                    // 调用已有函数处理实时数据
                    handleRealTimeData(message.data);
                    break;
                case 'updateResourceData':
                    updateResourceMonitorUI(message.data);
                    break;
                case 'updateDBData':
                    updateDBMonitorUI(message.data);
                    break;
                case 'adExitAndDeviceOffline':
                    // AD退出处理
                    handleAdExit(message.data);
                    break;
                    // ---- 父页面刷新指令（切换组织/功能标签） ----
                case 'refresh':
                    console.log('收到父页面刷新指令, orgId:', message.orgId);
                    clearStatFilters();
                    // 如果传递了组织ID，可在此处理（如重新加载设备列表）
                    if (message.orgId) {
                        // 可选的：处理组织切换逻辑
                        // 例如：重新加载统计图表或设备列表
                    }
                    if (typeof refreshData === 'function') {
                        refreshData();
                    } else {
                        console.warn('refreshData 未定义');
                    }
                    break;
                default:
                    break;
            }
        });

        // 示例：处理实时数据
        function handleRealTimeData(data) {
            var funcCode = data.functionCode ? data.functionCode.toUpperCase() : '';
            var isFullData = (funcCode === 'DEVICEREALTIMEMONITORINGDATA');
            var isStatusData = (funcCode === 'DEVICEREALTIMEMONITORINGSTATUSDATA');

            if (!isFullData && !isStatusData) {
                console.warn('未知数据类型:', funcCode);
                return;
            }

            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            var selectedId = selected ? selected.id : null;
            var isSelectWell = (selectedId === data.deviceId);
            var commStatusChange = false;

            // ===== 更新设备概览表格 =====
            if (grid) {
                var rows = grid.getData();
                var foundRow = null;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].id === data.deviceId) {
                        foundRow = rows[i];
                        break;
                    }
                }

                if (foundRow) {
                    var updateObj = {};

                    if (isFullData) {
                        // ===== 全量数据逻辑 =====
                        // ★ 通信状态处理：固定设为在线 (1)，但需映射键名
                        var commStatusKey = null;
                        var commStatusNameKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === 'COMMSTATUS') {
                                commStatusKey = key;
                            } else if (key.toUpperCase() === 'COMMSTATUSNAME') {
                                commStatusNameKey = key;
                            }
                        }
                        if (commStatusKey !== null) {
                            if (foundRow[commStatusKey] == 0) {
                                commStatusChange = true;
                            }
                            updateObj[commStatusKey] = 1;
                        }
                        if (commStatusNameKey !== null) {
                            updateObj[commStatusNameKey] = _loginUserLanguageResource.online;
                        }

                        // ★ 采集时间
                        var acqTimeKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === 'ACQTIME') {
                                acqTimeKey = key;
                                break;
                            }
                        }
                        if (acqTimeKey !== null && data.acqTime) {
                            updateObj[acqTimeKey] = data.acqTime;
                        }

                        // ★ 报警信息：完全重建
                        var alarmInfoKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === 'ALARMINFO') {
                                alarmInfoKey = key;
                                break;
                            }
                        }
                        var alarmInfo = [];
                        if (data.allItemInfo) {
                            for (var j = 0; j < data.allItemInfo.length; j++) {
                                var item = data.allItemInfo[j];
                                if (item.alarmLevel > 0) {
                                    // ★ 将 column 映射为 foundRow 中实际的键名（忽略大小写匹配）
                                    var actualColumnKey = null;
                                    for (var key in foundRow) {
                                        if (key.toUpperCase() === item.column.toUpperCase()) {
                                            actualColumnKey = key;
                                            break;
                                        }
                                    }
                                    if (actualColumnKey === null) {
                                        actualColumnKey = item.column; // 若找不到，用原值
                                    }
                                    alarmInfo.push({
                                        item: actualColumnKey,
                                        alarmLevel: item.alarmLevel
                                    });
                                }
                            }
                        }
                        if (alarmInfoKey !== null) {
                            updateObj[alarmInfoKey] = alarmInfo;
                        }

                        // ★ 遍历 allItemInfo 更新所有采集项
                        if (data.allItemInfo) {
                            for (var m = 0; m < data.allItemInfo.length; m++) {
                                var item = data.allItemInfo[m];
                                var fieldName = item.column;
                                // 跳过通信状态和报警信息（已单独处理）
                                if (fieldName.toUpperCase() === 'COMMSTATUS' || fieldName.toUpperCase() === 'COMMSTATUSNAME' || fieldName.toUpperCase() === 'ALARMINFO') {
                                    continue;
                                }
                                // 在 foundRow 中查找匹配的键（忽略大小写）
                                var matchedKey = null;
                                for (var key in foundRow) {
                                    if (key.toUpperCase() === fieldName.toUpperCase()) {
                                        matchedKey = key;
                                        break;
                                    }
                                }
                                if (matchedKey !== null) {
                                    // 特殊处理运行状态
                                    if (fieldName.toUpperCase() === 'RUNSTATUSNAME') {
                                        // 同时更新 runStatus（如果有）
                                        var runStatusKey = null;
                                        for (var key in foundRow) {
                                            if (key.toUpperCase() === 'RUNSTATUS') {
                                                runStatusKey = key;
                                                break;
                                            }
                                        }
                                        if (runStatusKey !== null) {
                                            updateObj[runStatusKey] = parseInt(item.rawValue) || 0;
                                        }
                                        updateObj[matchedKey] = item.value;
                                    } else {
                                        updateObj[matchedKey] = item.value;
                                    }
                                }
                            }
                        }

                        // ★ 报警级别（顶层字段）
                        var commAlarmKey = null;
                        var runAlarmKey = null;
                        var resultAlarmKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === 'COMMALARMLEVEL') commAlarmKey = key;
                            else if (key.toUpperCase() === 'RUNALARMLEVEL') runAlarmKey = key;
                            else if (key.toUpperCase() === 'RESULTALARMLEVEL') resultAlarmKey = key;
                        }
                        if (commAlarmKey !== null && data.commAlarmLevel !== undefined) updateObj[commAlarmKey] = data.commAlarmLevel;
                        if (runAlarmKey !== null && data.runAlarmLevel !== undefined) updateObj[runAlarmKey] = data.runAlarmLevel;
                        if (resultAlarmKey !== null && data.resultAlarmLevel !== undefined) updateObj[resultAlarmKey] = data.resultAlarmLevel;

                    } else if (isStatusData) {
                        // ===== 状态数据逻辑 =====
                        // ★ 通信状态
                        var commStatusKey = null;
                        var commStatusNameKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === 'COMMSTATUS') {
                                commStatusKey = key;
                            } else if (key.toUpperCase() === 'COMMSTATUSNAME') {
                                commStatusNameKey = key;
                            }
                        }
                        if (commStatusKey !== null && data.commStatus !== undefined) {
                            if (foundRow[commStatusKey] !== data.commStatus) {
                                commStatusChange = true;
                            }
                            updateObj[commStatusKey] = data.commStatus;
                        }
                        if (commStatusNameKey !== null && data.commStatusName !== undefined) {
                            updateObj[commStatusNameKey] = data.commStatusName;
                        }

                        // ★ 采集时间
                        var acqTimeKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === 'ACQTIME') {
                                acqTimeKey = key;
                                break;
                            }
                        }
                        if (acqTimeKey !== null && data.acqTime) {
                            updateObj[acqTimeKey] = data.acqTime;
                        }

                        // ★ 通信相关字段
                        var fieldMap = {
                            'commTime': null,
                            'commTimeEfficiency': null,
                            'commRange': null
                        };
                        for (var key in foundRow) {
                            var upperKey = key.toUpperCase();
                            if (upperKey === 'COMMTIME') fieldMap.commTime = key;
                            else if (upperKey === 'COMMTIMEEFFICIENCY') fieldMap.commTimeEfficiency = key;
                            else if (upperKey === 'COMMRANGE') fieldMap.commRange = key;
                        }
                        if (fieldMap.commTime !== null && data.commTime !== undefined) updateObj[fieldMap.commTime] = data.commTime;
                        if (fieldMap.commTimeEfficiency !== null && data.commTimeEfficiency !== undefined) updateObj[fieldMap.commTimeEfficiency] = data.commTimeEfficiency;
                        if (fieldMap.commRange !== null && data.commRange !== undefined) updateObj[fieldMap.commRange] = data.commRange;

                        // ★ 报警信息：仅更新通信报警（保留其他）
                        var alarmInfoKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === 'ALARMINFO') {
                                alarmInfoKey = key;
                                break;
                            }
                        }
                        if (alarmInfoKey !== null) {
                            var alarmInfo = foundRow[alarmInfoKey] || [];
                            var commStatusNameKeyActual = null;
                            for (var key in foundRow) {
                                if (key.toUpperCase() === 'COMMSTATUSNAME') {
                                    commStatusNameKeyActual = key;
                                    break;
                                }
                            }
                            if (commStatusNameKeyActual !== null) {
                                var existCommAlarm = false;
                                for (var k = 0; k < alarmInfo.length; k++) {
                                    if (alarmInfo[k].item && alarmInfo[k].item.toUpperCase() === commStatusNameKeyActual.toUpperCase()) {
                                        existCommAlarm = true;
                                        if (data.commAlarmLevel > 0) {
                                            alarmInfo[k].alarmLevel = data.commAlarmLevel;
                                        } else {
                                            alarmInfo.splice(k, 1);
                                        }
                                        break;
                                    }
                                }
                                if (!existCommAlarm && data.commAlarmLevel > 0) {
                                    alarmInfo.push({
                                        item: commStatusNameKeyActual,
                                        alarmLevel: data.commAlarmLevel
                                    });
                                }
                                updateObj[alarmInfoKey] = alarmInfo;
                            }
                        }

                        // ★ 通信报警级别
                        var commAlarmKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === 'COMMALARMLEVEL') {
                                commAlarmKey = key;
                                break;
                            }
                        }
                        if (commAlarmKey !== null && data.commAlarmLevel !== undefined) {
                            updateObj[commAlarmKey] = data.commAlarmLevel;
                        }
                    }

                    // ★ 执行更新（updateObj 中的键已经是 foundRow 中的实际键名）
                    grid.updateRow(foundRow, updateObj);
                    grid.acceptRecord(foundRow);
                    // 强制刷新整个表格（确保视图更新）
                    //grid.setData(grid.getData());

                } else {
                    console.log('设备不在当前页');
                }
            }

            // ===== 统计饼图刷新（只刷新当前激活的统计标签页） =====
            var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var orgId = window.parent && window.parent.mini ?
                window.parent.mini.get('leftOrg_Id').getValue() : '';

            var statTabs = mini.get('statTabs');
            if (statTabs) {
                var activeStatTab = statTabs.getActiveTab();
                if (activeStatTab) {
                    var key = activeStatTab._key;
                    if (isFullData) {
                        loadStatData(activeStatTab, deviceTypeId, orgId);
                    } else if (isStatusData) {
                        if (key !== 'FESdiagramResult') {
                            loadStatData(activeStatTab, deviceTypeId, orgId);
                        }
                    }
                }
            }

            // ===== 中间区域更新 =====
            if (isFullData && isSelectWell) {
                var middleTabs = mini.get('middleTabs');
                if (middleTabs) {
                    var activeMiddleTab = middleTabs.getActiveTab();
                    if (activeMiddleTab) {
                        var tabName = activeMiddleTab.name;
                        switch (tabName) {
                            case 'middle_WellboreAnalysis':
                                if (data.wellBoreChartsData) {
                                    if (isNotVal(data.wellBoreChartsData.pumpFSDiagramData)) {
                                        showFSDiagramFromPumpcard(data.wellBoreChartsData, 'wellboreChart1');
                                    } else {
                                        showSurfaceCard(data.wellBoreChartsData, 'wellboreChart1');
                                    }
                                    showRodPress(data.wellBoreChartsData, 'wellboreChart2');
                                    showPumpCard(data.wellBoreChartsData, 'wellboreChart3');
                                    showPumpEfficiency(data.wellBoreChartsData, 'wellboreChart4');
                                }
                                break;
                            case 'middle_SurfaceAnalysis':
                                if (data.surfaceChartsData) {
                                    showPSDiagram(data.surfaceChartsData, 'surfaceChart1');
                                    showASDiagram(data.surfaceChartsData, 'surfaceChart3');
                                    showBalanceAnalysisCurveChart(
                                        data.surfaceChartsData.crankAngle,
                                        data.surfaceChartsData.loadRorque,
                                        data.surfaceChartsData.crankTorque,
                                        data.surfaceChartsData.currentBalanceTorque,
                                        data.surfaceChartsData.currentNetTorque,
                                        _loginUserLanguageResource.currentTorqueCurve,
                                        data.surfaceChartsData.deviceName || '',
                                        data.surfaceChartsData.acqTime || '',
                                        'surfaceChart2'
                                    );
                                    var deltaRadius = parseFloat(data.surfaceChartsData.deltaRadius) || 0;
                                    var expectedTitle = _loginUserLanguageResource.torqueCurve;
                                    if (Math.abs(deltaRadius) > 0) {
                                        expectedTitle = (deltaRadius > 0 ? _loginUserLanguageResource.moveTowardOutside : _loginUserLanguageResource.moveTowardInside) + Math.abs(deltaRadius) + 'cm' + expectedTitle;
                                    } else {
                                        expectedTitle = _loginUserLanguageResource.expectTorqueCurve;
                                    }
                                    showBalanceAnalysisCurveChart(
                                        data.surfaceChartsData.crankAngle,
                                        data.surfaceChartsData.loadRorque,
                                        data.surfaceChartsData.crankTorque,
                                        data.surfaceChartsData.expectedBalanceTorque,
                                        data.surfaceChartsData.expectedNetTorque,
                                        expectedTitle,
                                        data.surfaceChartsData.deviceName || '',
                                        data.surfaceChartsData.acqTime || '',
                                        'surfaceChart4'
                                    );
                                }
                                break;
                            case 'middle_TrendCurve':
                                var container = document.getElementById('trendContainer');
                                if (container && data.CellInfo) {
                                    var chartItems = container.querySelectorAll('.trend-chart-container');
                                    var timestamp = Date.parse(data.acqTime.replace(/-/g, '/'));
                                    for (var ci = 0; ci < chartItems.length; ci++) {
                                        var chartEl = chartItems[ci];
                                        var chart = $(chartEl).highcharts();
                                        if (chart && chart.series && chart.series.length > 0) {
                                            var series = chart.series[0];
                                            var seriesName = series.name.split("(")[0].trim();
                                            for (var cellIdx = 0; cellIdx < data.CellInfo.length; cellIdx++) {
                                                var cell = data.CellInfo[cellIdx];
                                                if (cell.columnName === seriesName) {
                                                    var value = parseFloat(cell.rawValue);
                                                    if (!isNaN(value)) {
                                                        var translation = false;
                                                        if (series.data.length > 100) {
                                                            translation = true;
                                                        }
                                                        series.addPoint([timestamp, value], true, translation);
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            case 'middle_DynamicData':
                                if (deviceRealTimeMonitoringGrid) {
                                    var newData = data.totalRoot;
                                    if (newData && newData.length > 0) {
                                        deviceRealTimeMonitoringGrid.setData(newData);
                                        deviceRealTimeMonitoringGrid._cellInfo = data.CellInfo;
                                        // 重新合并第一行
                                        setTimeout(function() {
                                            mergeDataGridCell(deviceRealTimeMonitoringGrid, [{
                                                rowIndex: 0,
                                                columnIndex: 0,
                                                rowSpan: 1,
                                                colSpan: 6
                                            }]);
                                            deviceRealTimeMonitoringGrid.setStyle('visibility:visible;');
                                        }, 50);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

            // ===== 动态数据表更新（状态数据且设备被选中且当前标签为动态数据） =====
            if (isStatusData && isSelectWell) {
                var middleTabs = mini.get('middleTabs');
                if (middleTabs) {
                    var activeMiddleTab = middleTabs.getActiveTab();
                    if (activeMiddleTab && activeMiddleTab.name === 'middle_DynamicData') {
                        // ★ 替换为 MiniUI Grid 操作
                        if (deviceRealTimeMonitoringGrid) {
                            var statusText = data.commStatus > 0 ?
                                (_loginUserLanguageResource.goOnline) :
                                (_loginUserLanguageResource.offline);
                            var newTitle = data.deviceName + ':' + data.acqTime + ' ' + statusText;

                            // 更新第一行的 name1 字段
                            var firstRow = deviceRealTimeMonitoringGrid.getAt(0);
                            if (firstRow) {
                                deviceRealTimeMonitoringGrid.updateRow(firstRow, {
                                    name1: newTitle
                                });
                            }
                        }
                    }
                }
            }

            // ===== 右侧控制面板刷新（通信状态变化时） =====
            if (commStatusChange && isSelectWell) {
                var rightTabs = mini.get('rightTabs');
                if (rightTabs) {
                    var activeRightTab = rightTabs.getActiveTab();
                    if (activeRightTab && activeRightTab.name === 'right_DeviceControl') {
                        loadDeviceControl();
                    }
                }
            }

            console.log('实时数据处理完成，设备ID:', data.deviceId, '类型:', funcCode, '通信变化:', commStatusChange);
        }


        var deviceRealTimeMonitoringGrid = null; // 存放 MiniUI grid 实例
        function createDeviceRealTimeMonitoringGrid(containerId, data, cellInfo) {
            if (deviceRealTimeMonitoringGrid) {
                deviceRealTimeMonitoringGrid.destroy();
                deviceRealTimeMonitoringGrid = null;
            }

            var container = document.getElementById(containerId);
            if (!container) return;
            container.innerHTML = '';

            deviceRealTimeMonitoringGrid = new mini.DataGrid();
            deviceRealTimeMonitoringGrid._cellInfo = cellInfo || [];
            deviceRealTimeMonitoringGrid.set({
                id: 'deviceRealtimeDataGrid',
                style: 'width:100%; height:100%; visibility:hidden;', // ★ 初始隐藏
                showPager: false,
                showColumns: false,
                allowCellSelect: true,
                allowCellWrap: false,
                allowResize: true,
                allowCellMerge: true,
                virtualScroll: false,
                allowAlternating: true,
                data: data || [],
                columns: [{
                        field: 'name1',
                        width: '16%',
                        align: 'center',
                        headerAlign: 'center'
                    },
                    {
                        field: 'value1',
                        width: '16%',
                        align: 'center',
                        headerAlign: 'center'
                    },
                    {
                        field: 'name2',
                        width: '16%',
                        align: 'center',
                        headerAlign: 'center'
                    },
                    {
                        field: 'value2',
                        width: '16%',
                        align: 'center',
                        headerAlign: 'center'
                    },
                    {
                        field: 'name3',
                        width: '16%',
                        align: 'center',
                        headerAlign: 'center'
                    },
                    {
                        field: 'value3',
                        width: '16%',
                        align: 'center',
                        headerAlign: 'center'
                    }
                ],
                ondrawcell: function(e) {
                    applyCellStyle(e);
                },
                oncellmouseenter: function(e) {},
                oncelldblclick: function(e) {
                    handleCellDblClick(e);
                },
                onrender: function() {
                    var merges = [{
                        rowIndex: 0,
                        columnIndex: 0,
                        rowSpan: 1,
                        colSpan: 6
                    }];
                    mergeDataGridCell(this, merges);
                    // ★ 合并完成后显示
                    this.setStyle('visibility:visible;');
                }
            });

            deviceRealTimeMonitoringGrid.render(container);

            // 兜底：如果数据存在且 onrender 未触发（极少情况），延迟合并并显示
            if (data && data.length > 0) {
                setTimeout(function() {
                    var merges = [{
                        rowIndex: 0,
                        columnIndex: 0,
                        rowSpan: 1,
                        colSpan: 6
                    }];
                    mergeDataGridCell(deviceRealTimeMonitoringGrid, merges);
                    deviceRealTimeMonitoringGrid.setStyle('visibility:visible;');
                }, 100);
            }
        }

        function mergeDataGridCell(grid, marges) {
            if (!grid) return;
            try {
                grid.mergeCells(marges);
            } catch (e) {
                // 若失败，延迟重试
                setTimeout(function() {
                    try {
                        grid.mergeCells(marges);
                    } catch (e2) {}
                }, 200);
            }
        }

        function applyCellStyle(e) {
            // ★ 从 grid 实例获取最新的 cellInfo
            var grid = e.sender;
            var cellInfo = grid._cellInfo || [];

            e.cellStyle = '';

            var record = e.record;
            var field = e.field;
            var rowIndex = e.rowIndex;
            var colIndex = e.columnIndex;
            var value = e.value;

            // 第一行特殊样式：字体大、高度高
            if (rowIndex === 0) {
                e.cellStyle = 'font-size:20px; height:40px; font-weight:bold;';
                return;
            }

            if (!cellInfo) return;

            var alarmShowStyle = getAlarmShowStyle();
            // 遍历 cellInfo 找到匹配当前行和组的项
            // 先确定组索引：field 是 name1/value1 对应组0，name2/value2 对应组1，name3/value3 对应组2
            var groupMap = {
                name1: 0,
                value1: 0,
                name2: 1,
                value2: 1,
                name3: 2,
                value3: 2
            };
            var groupIndex = groupMap[field];
            if (groupIndex === undefined) return;

            for (var i = 0; i < cellInfo.length; i++) {
                var info = cellInfo[i];
                if (info.row === rowIndex && info.col === groupIndex) {
                    // 判断是名称列还是值列：字段名以 'name' 开头为名称列，'value' 开头为值列
                    var isNameColumn = field.indexOf('name') === 0;
                    var isValueColumn = field.indexOf('value') === 0;

                    if (isNameColumn) {
                        // 名称列应用 realtimeColor 和 realtimeBgColor
                        if (isNotVal(info.realtimeColor)) {
                            e.cellStyle = (e.cellStyle || '') + 'color:#' + info.realtimeColor + ';';
                        }
                        if (isNotVal(info.realtimeBgColor)) {
                            e.cellStyle = (e.cellStyle || '') + 'background-color:#' + info.realtimeBgColor + ';';
                        }
                    } else if (isValueColumn) {
                        // 值列应用报警样式
                        var alarmLevel = info.alarmLevel;
                        if (alarmLevel > 0) {
                            e.cellStyle = (e.cellStyle || '') + 'font-weight:bold;';
                        }
                        // 根据 alarmLevel 获取颜色配置
                        var styleCfg = getAlarmStyleByLevel(alarmLevel, alarmShowStyle);
                        if (styleCfg) {
                            if (styleCfg.bg) {
                                e.cellStyle += 'background-color:' + styleCfg.bg + ';';
                            }
                            if (styleCfg.color) {
                                e.cellStyle += 'color:' + styleCfg.color + ';';
                            }
                        }
                    }
                    break;
                }
            }
        }
        
        function handleCellDblClick(e) {
            var grid = e.sender;
            var record = e.record;
            if (!record) return;

            // 获取行索引
            var rowIndex = grid.indexOf(record);

            // 获取字段名（可能来自 e.field 或 e.column.field）
            var field = e.field || (e.column ? e.column.field : null);
            if (!field) return;

            console.log('双击事件触发：', rowIndex, field);

            // 标题行不处理
            if (rowIndex === 0) return;

            // 只处理 name1/value1, name2/value2, name3/value3
            var groupMap = {
                name1: 0,
                value1: 0,
                name2: 1,
                value2: 1,
                name3: 2,
                value3: 2
            };
            var groupIndex = groupMap[field];
            if (groupIndex === undefined) return;

            var itemName = record['name' + (groupIndex + 1)];
            var itemValue = record['value' + (groupIndex + 1)];

            // 查找对应的 CellInfo
            var cellInfo = grid._cellInfo || [];
            var info = null;
            for (var i = 0; i < cellInfo.length; i++) {
                if (cellInfo[i].row === rowIndex && cellInfo[i].col === groupIndex) {
                    info = cellInfo[i];
                    break;
                }
            }
            if (!info) {
                console.warn('未找到对应的 CellInfo，row=' + rowIndex + ', col=' + groupIndex);
                return;
            }

            // 调用原有的 viewDeviceRealTimeMonitoringData
            viewDeviceRealTimeMonitoringData(rowIndex, groupIndex);
        }

        function updateResourceMonitorUI(data) {
            // ---- 工具函数 ----
            function updateResourceItemPlain(id, text, color) {
                var el = document.getElementById(id);
                if (!el) return;
                el.style.display = '';
                el.textContent = text;
                if (color) el.style.color = color;
                else el.style.color = '';
            }

            function updateResourceItem(id, dotColor, text, textColor, blink) {
                var el = document.getElementById(id);
                if (!el) return;
                el.style.display = '';
                var dotHtml = '<span style="color:' + dotColor + '; font-size: 18px; line-height: 1;">●</span>';
                if (textColor) {
                    el.innerHTML = dotHtml + ' <span style="color:' + textColor + ';">' + text + '</span>';
                } else {
                    el.innerHTML = dotHtml + ' ' + text;
                }
                if (blink) {
                    el.classList.add('resource-blink');
                } else {
                    el.classList.remove('resource-blink');
                }
            }
            
            function hideResourceItem(id) {
                var el = document.getElementById(id);
                if (el) el.style.display = 'none';
            }

            // ===== CPU（纯文本） =====
            var cpuColor = '';
            if (data.cpuUsedPercentAlarmLevel == 1) cpuColor = '#F09614';
            else if (data.cpuUsedPercentAlarmLevel == 2) cpuColor = '#DC2828';
            updateResourceItemPlain('CPUUsedPercentLabel_id',
                (_loginUserLanguageResource.resourcesMonitoring_cpu) + ':' + data.cpuUsedPercent,
                cpuColor);

            // ===== 内存（纯文本） =====
            var memColor = '';
            if (data.memUsedPercentAlarmLevel == 1) memColor = '#F09614';
            else if (data.memUsedPercentAlarmLevel == 2) memColor = '#DC2828';
            updateResourceItemPlain('memUsedPercentLabel_id',
                (_loginUserLanguageResource.resourcesMonitoring_mem) + ':' + data.memUsedPercent,
                memColor);

         // ===== 表空间（带圆点，判断断开闪烁） =====
            var tableDot = (data.dbConnStatus == 1) ? '#52c41a' : '#ccc';
            var tableText = _loginUserLanguageResource.resourcesMonitoring_tablespaces;
            var tableTextColor = '';
            var tableBlink = (data.dbConnStatus != 1);   // ★ 断开时闪烁
            if (data.dbConnStatus == 1) {
                tableText = (_loginUserLanguageResource.resourcesMonitoring_tablespaces) + ':' +
                            data.tableSpaceUsedPercent + ';' + data.undoTableSpaceUsedPercent;
                if (data.tableSpaceUsedPercentAlarmLevel == 1) tableTextColor = '#F09614';
                else if (data.tableSpaceUsedPercentAlarmLevel == 2) tableTextColor = '#DC2828';
            }
            //tableDot='#ccc';
            //tableBlink=true;
            updateResourceItem('tableSpaceSizeProbeLabel_id', tableDot, tableText, tableTextColor, tableBlink);

            // ===== 缓存（带圆点，停止时闪烁） =====
            var redisDot = (data.redisStatus == 1) ? '#52c41a' : '#ccc';
            var redisBlink = (data.redisStatus != 1);
            var redisText = (data.redisStatus == 1) ?
                (_loginUserLanguageResource.resourcesMonitoring_cache) + ':' + data.cacheUsedMemory + 'm/' + data.cacheMaxMemory + 'm' :
                _loginUserLanguageResource.resourcesMonitoring_cache;
            updateResourceItem('redisRunStatusProbeLabel_id', redisDot, redisText, null, redisBlink);

            // ===== AD（带圆点，停止时闪烁） =====
            var adDot = (data.adRunStatus == 1) ? '#52c41a' : '#ccc';
            var adBlink = (data.adRunStatus != 1);
            updateResourceItem('adRunStatusProbeLabel_id', adDot,
                _loginUserLanguageResource.resourcesMonitoring_ad, null, adBlink);

            // ===== AC（带圆点，停止时闪烁） =====
            var acDot = (data.acRunStatus == 1) ? '#52c41a' : '#ccc';
            var acBlink = (data.acRunStatus != 1);
            updateResourceItem('acRunStatusProbeLabel_id', acDot,
                _loginUserLanguageResource.resourcesMonitoring_ac, null, acBlink);

            // ===== License（纯红色文本，仅授权时显示） =====
            if (data.licenseSign) {
                updateResourceItemPlain('adLicenseStatusProbeLabel_id',
                    'License:' + data.deviceAmount + '/' + data.license,
                    '#DC2828');
            } else {
                hideResourceItem('adLicenseStatusProbeLabel_id');
            }
        }

        function updateDBMonitorUI(data) {
            var tableBtn = mini.get('tableSpaceSizeProbeLabel_id');
            if (!tableBtn) return;

            // 数据库连接正常
            if (data.dbConnStatus == 1) {
                var showInfo = (_loginUserLanguageResource.resourcesMonitoring_tablespaces) + ':' +
                    (data.tableSpaceUsedPercent || '0') + '%;' +
                    (data.undoTableSpaceUsedPercent || '0') + '%';
                tableBtn.setText(showInfo);
                tableBtn.setIconCls('dtgreen'); // 连接正常默认绿色图标
                var tableEl = tableBtn.getEl().dom;
                if (data.tableSpaceUsedPercentAlarmLevel == 1) {
                    tableEl.style.color = '#F09614';
                } else if (data.tableSpaceUsedPercentAlarmLevel == 2) {
                    tableEl.style.color = '#DC2828';
                } else {
                    tableEl.style.color = '';
                }
            } else {
                // 数据库断开
                tableBtn.setText(_loginUserLanguageResource.resourcesMonitoring_tablespaces);
                tableBtn.setIconCls('dtyellow');
                tableBtn.getEl().dom.style.color = '';
            }
        }

        function handleAdExit(data) {
            // 1. 重新加载当前激活的统计图表
            clearStatFilters();
            var statTabsObj = mini.get('statTabs');
            if (statTabsObj) {
                var activeTab = statTabsObj.getActiveTab();
                if (activeTab) {
                    var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
                    var orgId = window.parent && window.parent.mini ?
                        window.parent.mini.get('leftOrg_Id').getValue() : '';
                    // 优先使用 loadStatData 刷新当前标签页
                    if (typeof loadStatData === 'function') {
                        loadStatData(activeTab, deviceTypeId, orgId);
                    }
                }
            }

            // 2. 清空设备下拉框的值和显示文本
            var deviceCombo = mini.get('deviceCombo');
            if (deviceCombo) {
                deviceCombo.setValue('');
                deviceCombo.setText('');
            }

            // 3. 刷新设备网格：取消选中并重新加载
            var grid = mini.get('deviceGrid');
            if (grid) {
                grid.deselectAll();
                grid.load();
            }

            console.log('AD 退出处理完成，所有设备离线');
        }

        var DeviceControlValueHandsontableHelper = {
            createNew: function(divid) {
                var helper = {};
                helper.divid = divid;
                helper.validresult = true;
                helper.colHeaders = [];
                helper.columns = [];
                helper.colWidths = [];
                helper.hiddenColumns = [];

                helper.addColBg = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.backgroundColor = '#DC2828';
                    td.style.color = '#FFFFFF';
                };

                helper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.backgroundColor = 'rgb(245, 245, 245)';
                };

                helper.addSizeBg = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.fontWeight = 'bold';
                    td.style.fontSize = '20px';
                    td.style.height = '40px';
                };

                helper.createTable = function(data) {
                    var container = document.getElementById(helper.divid);
                    if (!container) return;
                    // 清空
                    container.innerHTML = '';
                    // ★★★ 关键：设置容器高度为父容器高度（窗口内容区域） ★★★
                    var parent = container.parentNode;
                    var height = 300; // 默认
                    if (parent) {
                        // 获取父容器可见高度
                        var rect = parent.getBoundingClientRect();
                        if (rect.height > 0) {
                            height = rect.height;
                        } else {
                            // 尝试递归查找
                            var el = parent;
                            while (el && el !== document.body) {
                                var h = el.clientHeight || el.offsetHeight;
                                if (h > 0) {
                                    height = h;
                                    break;
                                }
                                el = el.parentNode;
                            }
                        }
                    }
                    container.style.height = height + 'px';
                    container.style.width = '100%';

                    // 销毁旧实例
                    if (helper.hot) {
                        helper.hot.destroy();
                        helper.hot = null;
                    }

                    // 创建 Handsontable
                    helper.hot = new Handsontable(container, {
                        licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                        theme: 'ht-theme-classic',
                        data: data,
                        hiddenColumns: {
                            columns: helper.hiddenColumns,
                            indicators: false,
                            copyPasteEnabled: false
                        },
                        columns: helper.columns,
                        stretchH: 'all',
                        rowHeaders: false,
                        colHeaders: helper.colHeaders,
                        colWidths: helper.colWidths,
                        columnSorting: true,
                        allowInsertRow: false,
                        sortIndicator: true,
                        manualColumnResize: true,
                        manualRowResize: true,
                        filters: true,
                        renderAllRows: true,
                        search: true,
                        cells: function(row, col, prop) {
                            var cellProperties = {};
                            var visualRowIndex = this.instance.toVisualRow(row);
                            var visualColIndex = this.instance.toVisualColumn(col);
                            if (prop.toUpperCase() == 'index'.toUpperCase() || prop.toUpperCase() == 'uplinkStatus'.toUpperCase()) {
                                cellProperties.editor = false;
                            }
                            return cellProperties;
                        }
                    });

                    // 强制渲染
                    helper.hot.render();
                    // 延迟刷新
                    setTimeout(function() {
                        if (helper.hot) {
                            helper.hot.render();
                            helper.hot.updateSettings({
                                width: '100%',
                                height: height
                            });
                        }
                    }, 50);
                };
                return helper;
            }
        };

        function onNumericControlClick(recordId, controlType, itemName, unit, quantity, storeDataType, disabled) {
            if (disabled) return;

            var grid = mini.get('deviceGrid');
            var selected = grid.getSelected();
            if (!selected) return;
            var deviceId = selected.id;
            var deviceName = selected.deviceName;
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

            var win = new mini.Window();
            win.set({
                title: _loginUserLanguageResource.deviceControl,
                width: 800,
                height: 410,
                minWidth: 600,
                minHeight: 300,
                modal: true,
                showHeader: true,
                allowResize: true,
                maxable: true,
                minable: true,
                showCloseButton: true
            });
            win.show();

            var containerId = 'DeviceControlValueTableDiv_' + Date.now();
            var html = '<div style="display:flex; flex-direction:column; height:100%;">';
            html += '<div id="toolbar_' + containerId + '" style="flex-shrink:0; padding:4px 8px; background:#f5f5f5; border-bottom:1px solid #ddd; display:flex; align-items:center; gap:6px;">';
            html += '<span style="font-size:12px; color:#333;">' + (itemName || '') + (unit ? ' (' + unit + ')' : '') + '</span>';
            html += '<span style="flex:1;"></span>';
            html += '<button id="uplinkBtn_' + containerId + '" class="mini-button" style="padding:2px 12px;">' + (_loginUserLanguageResource.uplink) + '</button>';
            html += '<button id="downlinkBtn_' + containerId + '" class="mini-button" style="padding:2px 12px;">' + (_loginUserLanguageResource.downlink) + '</button>';
            html += '</div>';
            html += '<div id="' + containerId + '" style="flex:1; overflow:hidden; margin:0; min-height:0; background:#fff;"></div>';
            html += '</div>';
            win.setBody(html);

            mini.parse();

            var uplinkBtn = mini.get('uplinkBtn_' + containerId);
            var downlinkBtn = mini.get('downlinkBtn_' + containerId);
            var dataGrid = null;

            function createGrid(data, isSingleRow) {
                var container = document.getElementById(containerId);
                if (!container) return;
                container.innerHTML = '';

                dataGrid = new mini.DataGrid();
                dataGrid.set({
                    style: 'width:100%; height:100%;',
                    showPager: false,
                    allowCellSelect: true,
                    allowCellEdit: true,
                    allowCellWrap: false,
                    allowResize: true,
                    virtualScroll: false,
                    data: data,
                    columns: [{
                            field: 'index',
                            width: 50,
                            header: _loginUserLanguageResource.idx,
                            align: 'center',
                            headerAlign: 'center',
                            allowSort: false,
                            renderer: function(e) {
                                e.cellStyle = 'background-color:#f5f5f5;';
                                return e.value;
                            }
                        },
                        {
                            field: 'uplinkStatus',
                            flex: 3,
                            header: _loginUserLanguageResource.uplinkValue,
                            align: 'center',
                            headerAlign: 'center',
                            allowSort: false,
                            renderer: function(e) {
                                e.cellStyle = 'background-color:#f5f5f5;';
                                return e.value || '';
                            }
                        },
                        {
                            field: 'uplink',
                            flex: 2,
                            header: _loginUserLanguageResource.uplink,
                            align: 'center',
                            headerAlign: 'center',
                            visible: isSingleRow,
                            allowSort: false,
                            renderer: function(e) {
                                var rowIndex = e.rowIndex;
                                return '<button style="height:24px; line-height:24px; padding:0 14px; font-size:12px; font-weight:500; cursor:pointer; border:none; border-radius:20px; background:#409eff; color:#fff; box-shadow:0 1px 2px rgba(0,0,0,0.1); white-space:nowrap;" onmouseover="this.style.background=\'#66b1ff\'" onmouseout="this.style.background=\'#409eff\'" onclick="performUplink(' + rowIndex + ')">' + (_loginUserLanguageResource.uplink) + '</button>';
                            }
                        },
                        {
                            field: 'value',
                            flex: 3,
                            header: _loginUserLanguageResource.downlinkValue,
                            align: 'center',
                            headerAlign: 'center',
                            allowSort: false,
                            editor: {
                                type: 'textbox'
                            }
                        },
                        {
                            field: 'downlink',
                            flex: 2,
                            header: _loginUserLanguageResource.downlink,
                            align: 'center',
                            headerAlign: 'center',
                            visible: isSingleRow,
                            allowSort: false,
                            renderer: function(e) {
                                var rowIndex = e.rowIndex;
                                return '<button style="height:24px; line-height:24px; padding:0 14px; font-size:12px; font-weight:500; cursor:pointer; border:none; border-radius:20px; background:#67c23a; color:#fff; box-shadow:0 1px 2px rgba(0,0,0,0.1); white-space:nowrap;" onmouseover="this.style.background=\'#85ce61\'" onmouseout="this.style.background=\'#67c23a\'" onclick="performRowDownlink(' + rowIndex + ')">' + (_loginUserLanguageResource.downlink) + '</button>';
                            }
                        }
                    ],
                    oncellvalidation: function(e) {
                        if (e.column.field === 'value') {
                            if (storeDataType.toUpperCase() !== 'BCD' && storeDataType.toUpperCase() !== 'STRING') {
                                if (e.value !== null && e.value !== undefined && e.value !== '' && isNaN(e.value)) {
                                    e.isValid = false;
                                    e.errorText = _loginUserLanguageResource.dataFormattingError;
                                }
                            }
                        }
                    }
                });

                dataGrid.render(container);
                window._deviceControlGrid = dataGrid;

                // 工具栏按钮显隐
                if (isSingleRow) {
                    if (uplinkBtn) uplinkBtn.hide();
                    if (downlinkBtn) downlinkBtn.hide();
                } else {
                    if (uplinkBtn) uplinkBtn.show();
                    if (downlinkBtn) downlinkBtn.show();
                }

                if (uplinkBtn) {
                    uplinkBtn.off('click');
                    uplinkBtn.on('click', function() {
                        performUplink();
                    });
                }
                if (downlinkBtn) {
                    downlinkBtn.off('click');
                    downlinkBtn.on('click', function() {
                        performGlobalDownlink();
                    });
                }

                win.on('resize', function() {
                    if (window._deviceControlGrid) window._deviceControlGrid.doLayout();
                });
            }

            window.performUplink = function() {
                var grid = window._deviceControlGrid;
                if (!grid) return;
                var data = grid.getData();
                if (data.length === 0) return;

                var m = mini.mask({
                    el: win.getBodyEl(),
                    cls: 'mini-mask-loading',
                    html: _loginUserLanguageResource.commandSending + '...'
                });
                $.ajax({
                    url: context + '/wellInformationManagerController/deviceDataUplink',
                    type: 'POST',
                    data: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        controlType: controlType
                    },
                    dataType: 'json',
                    timeout: 10000,
                    success: function(result) {
                        mini.unmask(win.getBodyEl());
                        if (result.flag == false) {
                            mini.alert({
                                title: loginUserLanguageResource.tip,
                                message: '<font color="red">' + loginUserLanguageResource.sessionExpired + '</font>',
                                callback: function() {
                                    window.top.location.href = context + "/login";
                                }
                            });
                            return;
                        }

                        if (result.flag == true && result.error == false) {
                            mini.alert(result.msg);
                        } else if (result.flag == true && result.error == true) {
                            var uplinkData = result.data ? result.data.split(',') : [];
                            for (var i = 0; i < Math.min(data.length, uplinkData.length); i++) {
                                grid.updateRow(grid.getAt(i), {
                                    uplinkStatus: uplinkData[i]
                                });
                            }
                            grid.accept();
                        }
                    },
                    error: function() {
                        mini.unmask(win.getBodyEl());
                        grid.accept();
                        mini.alert(_loginUserLanguageResource.requestFailed);
                    }
                });
            };

            window.performRowDownlink = function(rowIndex) {
                var grid = window._deviceControlGrid;
                if (!grid) return;
                var record = grid.getAt(rowIndex);
                if (!record) return;
                var value = record.value;

                if (storeDataType.toUpperCase() !== 'BCD' && storeDataType.toUpperCase() !== 'STRING') {
                    if (value !== null && value !== undefined && value !== '' && isNaN(value)) {
                        mini.alert(_loginUserLanguageResource.dataFormattingError);
                        return;
                    }
                }
                var deviceNameShow = selected.deviceName;
                var tipInfo = _loginUserLanguageResource.deviceName + ":<font color=red>" + deviceNameShow + "</font>";
                tipInfo += "</br>" + (itemName || '') + (unit ? " (" + unit + ")" : "") + ":<font color=red>" + value + "</font>";
                tipInfo += "</br>" + (_loginUserLanguageResource.confirmOperation);
                mini.confirm(tipInfo, _loginUserLanguageResource.tip, function(action) {
                    if (action == 'ok') {
                        sendBatchControl([value]);
                    }
                });
            };

            window.performGlobalDownlink = function() {
                var grid = window._deviceControlGrid;
                if (!grid) return;
                var data = grid.getData();
                if (data.length === 0) return;

                var values = [];
                var isValid = true;
                for (var i = 0; i < data.length; i++) {
                    var v = data[i].value;
                    if (storeDataType.toUpperCase() !== 'BCD' && storeDataType.toUpperCase() !== 'STRING') {
                        if (v !== null && v !== undefined && v !== '' && isNaN(v)) {
                            isValid = false;
                            break;
                        }
                    }
                    values.push(v !== undefined && v !== null ? v : '');
                }
                if (!isValid) {
                    mini.alert(_loginUserLanguageResource.dataFormattingError);
                    return;
                }

                var deviceNameShow = selected.deviceName;
                var valueStr = values.join(',');
                var tipInfo = _loginUserLanguageResource.deviceName + ":<font color=red>" + deviceNameShow + "</font>";
                tipInfo += "</br>" + (itemName || '') + (unit ? " (" + unit + ")" : "") + ":<font color=red>" + valueStr + "</font>";
                tipInfo += "</br>" + (_loginUserLanguageResource.confirmOperation);
                mini.confirm(tipInfo, _loginUserLanguageResource.tip, function(action) {
                    if (action == 'ok') {
                        sendBatchControl(values);
                    }
                });
            };

            function sendBatchControl(values) {
                var controlValue = values.join(',');
                var m = mini.mask({
                    el: win.getBodyEl(),
                    cls: 'mini-mask-loading',
                    html: _loginUserLanguageResource.commandSending + '...'
                });
                $.ajax({
                    url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
                    type: 'POST',
                    data: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        deviceType: deviceType,
                        controlType: controlType,
                        controlValue: controlValue,
                        storeDataType: storeDataType,
                        quantity: quantity
                    },
                    dataType: 'json',
                    timeout: 10000,
                    success: function(result) {
                        mini.unmask(win.getBodyEl());
                        if (result.flag == false) {
                            mini.alert(result.msg);
                        } else if (result.flag == true && result.error == false) {
                            mini.alert(result.msg);
                        } else if (result.flag == true && result.error == true) {
                            mini.alert(result.msg);
                        }
                    },
                    error: function() {
                        mini.unmask(win.getBodyEl());
                        mini.alert(_loginUserLanguageResource.exceptionThrow);
                    }
                });
            }

            // ---- 加载数据 ----
            function loadData() {
                mini.mask({
                    el: win.getBodyEl(),
                    cls: 'mini-mask-loading',
                    html: _loginUserLanguageResource.loadingData
                });

                $.ajax({
                    url: context + '/realTimeMonitoringController/getDeviceControlValueList',
                    type: 'POST',
                    data: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        deviceType: deviceType,
                        controlType: controlType
                    },
                    dataType: 'json',
                    timeout: 10000,
                    success: function(result) {
                        mini.unmask(win.getBodyEl());
                        if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                            document.getElementById(containerId).innerHTML = '<div style="text-align:center;padding:20px;">' + _loginUserLanguageResource.emptyMsg + '</div>';
                            return;
                        }
                        var data = result.totalRoot;
                        var isSingleRow = (data.length === 1);
                        createGrid(data, isSingleRow);
                    },
                    error: function(xhr, status, errorThrown) {
                        mini.unmask(win.getBodyEl());
                        document.getElementById(containerId).innerHTML = '<div style="text-align:center;padding:20px;color:red;">' + _loginUserLanguageResource.requestFailed + '</div>';
                        console.error('加载控制值列表失败:', status, errorThrown);
                    }
                });
            }

            win.on('beforedestroy', function() {
                if (window._deviceControlGrid) {
                    window._deviceControlGrid.destroy();
                    window._deviceControlGrid = null;
                }
                window.performUplink = undefined;
                window.performRowDownlink = undefined;
                window.performGlobalDownlink = undefined;
                mini.unmask(win.getBodyEl());
            });

            loadData();
        }


        function exportDeviceRealTimeMonitoringData() {
            // 获取当前选中的设备
            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            if (!selected) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }

            var deviceId = selected.id;
            var deviceName = selected.deviceName;
            var calculateType = selected.calculateType || 0;

            // 生成导出任务唯一标识
            var timestamp = new Date().getTime();
            var key = 'exportDeviceRealTimeMonitoringData_' + deviceId + '_' + timestamp;

            // 构建下载 URL
            var url = context + '/realTimeMonitoringController/exportDeviceRealTimeMonitoringData';
            var param = '&deviceId=' + deviceId +
                '&deviceName=' + encodeURIComponent(encodeURIComponent(deviceName)) +
                '&calculateType=' + calculateType +
                '&key=' + key;

            // 调用公共函数：显示遮罩并轮询
            var maskCtrl = exportDataMask(
                key,
                'RealTimeMonitoringInfoDataTableInfoDiv_id',
                _loginUserLanguageResource.loadingData
            );

            // 打开下载链接
            var fullUrl = url + '?flag=true' + param;
            openExcelWindow(fullUrl);
        }

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
        window.exportRealTimeMonitoringData = exportRealTimeMonitoringData;
        window.gotoHistory = gotoHistory;

    </script>
</body>

</html>
