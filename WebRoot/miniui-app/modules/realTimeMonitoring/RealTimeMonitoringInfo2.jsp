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

        /* 底部一级标签 */
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

        /* 资源监测区域 */
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

        /* 布局主体 */
        .main-area {
            flex: 1;
            display: flex;
            flex-direction: row;
            overflow: hidden;
            min-height: 0;
            order: 0;
        }

        /* 左侧二级标签 */
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
            height: auto;
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

        /* 左侧主区域 */
        .left-main-area {
            display: flex;
            flex-direction: column;
            overflow: hidden;
            min-width: 0;
            background: #f0f2f5;
            padding: 4px;
        }

        /* 设备概览 */
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

        /* 统计饼图 */
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

        /* 中间区域 */
        .middle-area {
            min-width: 200px;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
            overflow: hidden;
            display: flex;
            flex-direction: column;
            margin: 4px 4px 4px 0;
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

        /* 右侧区域 */
        .right-area {
            min-width: 130px;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
            overflow: hidden;
            display: flex;
            flex-direction: column;
            margin: 4px 4px 4px 0;
        }

        .right-area .mini-tabs {
            flex: 1;
        }

        /* 公共占位 */
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

        /* 报警徽章 */
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

        /* 图表容器 */
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

        #trendContainer {
            display: flex;
            flex-wrap: wrap;
            align-content: flex-start;
            width: 100%;
            height: 100%;
            overflow: auto;
            padding: 4px;
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

        /* 滚动条 */
        .level2-sidebar::-webkit-scrollbar {
            width: 3px;
        }

        .level2-sidebar::-webkit-scrollbar-thumb {
            background: #ccc;
            border-radius: 4px;
        }

        @media (max-width: 768px) {
            .main-area {
                flex-direction: column;
            }

            .level2-sidebar {
                width: 100%;
                height: 32px;
                flex-direction: row;
                padding: 0 8px;
                border-right: none;
                border-bottom: 1px solid #e8e8e8;
                overflow-x: auto;
            }

            .level2-sidebar .tab-item {
                writing-mode: horizontal-tb;
                transform: none;
                min-height: auto;
                padding: 4px 12px;
                border-left: none;
                border-bottom: 2px solid transparent;
            }

            .level2-sidebar .tab-item.active {
                border-left: none;
                border-bottom-color: #1890ff;
            }

            .left-main-area {
                flex: 2;
            }

            .middle-area {
                flex: 1;
                min-height: 150px;
                margin: 0 4px 4px 4px;
            }

            .right-area {
                flex: 0 0 160px;
                margin: 0 4px 4px 4px;
            }

            .stat-charts-area {
                flex: 0 0 40%;
                min-height: 100px;
            }
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
                                                <div id="deviceGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="25" allowResize="true" url="<%=path%>/realTimeMonitoringController/getDeviceRealTimeOverview" dataField="totalRoot" totalField="totalCount" ondrawcell="onDeviceGridDrawCell" onselectionchanged="onDeviceGridSelectChanged" onload="onDeviceGridLoad" onbeforeload="onDeviceGridBeforeLoad">
                                                    <div property="columns">
                                                        <!-- 动态生成 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="statPanel" size="45%" showCollapseButton="true" minSize="100">
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
                title: function() {
                    return _loginUserLanguageResource.workType || '工况类型';
                },
                api: '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData'
            },
            'CommStatus': {
                id: 'stat_CommStatus',
                title: function() {
                    return _loginUserLanguageResource.commStatus || '通信状态';
                },
                api: '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData'
            },
            'RunStatus': {
                id: 'stat_RunStatus',
                title: function() {
                    return _loginUserLanguageResource.runStatus || '运行状态';
                },
                api: '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData'
            },
            'NumStatus': {
                id: 'stat_NumStatus',
                title: function() {
                    return _loginUserLanguageResource.numStatus || '数值状态';
                },
                api: '/realTimeMonitoringController/getRealTimeMonitoringNumStatusStatData'
            }
        };

        // 默认列配置
        var DEFAULT_COLUMNS = [{
                type: 'indexcolumn',
                width: 40,
                headerAlign: 'center',
                header: '序号'
            },
            {
                field: 'deviceName',
                width: 140,
                headerAlign: 'center',
                header: '设备名称',
                locked: true
            },
            {
                field: 'commStatusName',
                width: 80,
                headerAlign: 'center',
                header: '通信状态'
            },
            {
                field: 'runStatusName',
                width: 80,
                headerAlign: 'center',
                header: '运行状态'
            },
            {
                field: 'acqTime',
                width: 150,
                headerAlign: 'center',
                header: _loginUserLanguageResource.avqTime,
                dateFormat: 'yyyy-MM-dd HH:mm:ss'
            }
        ];
        
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

            // 3. 动态数据导出按钮（在 refreshDeviceTabs 中已创建，需在创建后设置）
            // 可通过监听 middleTabs 的 activechanged 事件，在动态数据标签激活时设置
            // 或者在 refreshDeviceTabs 中直接使用国际化文本

            // 4. 右侧面板的表格列标题（在 loadDeviceControl 中设置）
            // 可修改 renderer 中的列标题为国际化

            // 5. 所有 mini.alert 和 mini.confirm 中的文本
            // 已在函数中使用 _loginUserLanguageResource，无需额外修改
        }

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

            // 资源监测区域（右侧）
            var rightArea = document.createElement('div');
            rightArea.id = 'resourceMonitorArea';
            rightArea.style.cssText = 'display:flex; align-items:center; gap:6px; margin-left:auto;';
            container.appendChild(rightArea);

            var resourceButtons = [{
                    id: 'CPUUsedPercentLabel_id',
                    text: 'CPU',
                    onclick: "openResourceChart('cpuUsedPercent','"+_loginUserLanguageResource.cpuUsage+"(%)')"
                },
                {
                    id: 'memUsedPercentLabel_id',
                    text: '内存',
                    onclick: "openResourceChart('memUsedPercent','"+_loginUserLanguageResource.memUsage+"(%)')"
                },
                {
                    id: 'redisRunStatusProbeLabel_id',
                    text: '缓存',
                    onclick: "openResourceChart('jedisStatus','"+_loginUserLanguageResource.cacheDbMemory+"(m)')"
                },
                {
                    id: 'tableSpaceSizeProbeLabel_id',
                    text: '表空间',
                    onclick: "openResourceChart('tableSpaceSize','"+_loginUserLanguageResource.tablespacesUsage+"(%)')"
                },
                {
                    id: 'adRunStatusProbeLabel_id',
                    text: '通信服务',
                    onclick: "openResourceChart('adRunStatus','"+_loginUserLanguageResource.adStatus+"')"
                },
                {
                    id: 'acRunStatusProbeLabel_id',
                    text: '计算服务',
                    onclick: "openResourceChart('acRunStatus','"+_loginUserLanguageResource.acStatus+"')"
                },
                {
                    id: 'adLicenseStatusProbeLabel_id',
                    text: 'License',
                    onclick: ''
                }
            ];

            for (var i = 0; i < resourceButtons.length; i++) {
                var cfg = resourceButtons[i];
                var btn = new mini.Button();
                btn.setId(cfg.id);
                btn.setText(cfg.text);
                btn.setIconCls(''); // 初始无图标
                btn.setPlain(true); // ★ 关键：使按钮透明、无边框，且高度保持默认紧凑
                // 如果高度仍偏高，可取消注释下面一行强制还原
                // btn.setStyle('padding: 0 8px; height: 22px;');
                if (cfg.onclick) {
                    btn.on('click', new Function(cfg.onclick));
                }
                btn.render(rightArea);
            }

            // License 默认隐藏
            var licenseBtn = mini.get('adLicenseStatusProbeLabel_id');
            if (licenseBtn) licenseBtn.hide();

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
                text: '全部',
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
        	    } catch(e) {
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
                var counts = { 100: 0, 200: 0, 300: 0 };
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
            var fileName = _loginUserLanguageResource.realtimeMonitoringExpFileName || '设备实时监测数据';
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
                mini.alert('列配置解析失败');
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
            exportDataMask(key, maskContainer, _loginUserLanguageResource.loadingData || '数据导出中...');
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

            // 无可用标签 → 显示占位
            if (newTabKeys.length === 0) {
                var currentTabs = statTabs.getTabs();
                if (currentTabs.length !== 1 || currentTabs[0].name !== 'stat_placeholder') {
                    statTabs.setTabs([{
                        name: 'stat_placeholder',
                        title: '暂无统计图',
                        body: '<div class="loading-placeholder">无统计图表</div>'
                    }]);
                }
                return;
            }

            // 2. 记录当前激活的标签 key
            var activeTab = statTabs.getActiveTab();
            var activeKey = activeTab ? activeTab._key : null;

            // 3. 移除不在新列表中的标签
            var currentTabs = statTabs.getTabs();
            for (var i = currentTabs.length - 1; i >= 0; i--) {
                var tab = currentTabs[i];
                if (tab._key && newTabKeys.indexOf(tab._key) === -1) {
                    statTabs.removeTab(tab);
                }
            }

            // 4. 获取当前剩余标签（移除后）
            var remainingTabs = statTabs.getTabs();
            var remainingKeys = remainingTabs.map(function(t) {
                return t._key;
            });

            // 5. 按顺序添加新标签（如果不存在），并插入到正确位置
            for (var i = 0; i < newTabKeys.length; i++) {
                var key = newTabKeys[i];
                var cfg = STAT_TAB_CONFIG[key];
                if (!cfg) continue;

                // 检查是否已存在
                var existing = null;
                for (var j = 0; j < remainingTabs.length; j++) {
                    if (remainingTabs[j]._key === key) {
                        existing = remainingTabs[j];
                        break;
                    }
                }

                if (!existing) {
                    // 计算插入位置
                    var insertIndex = 0;
                    // 获取当前实际存在的标签列表
                    var existingTabs = statTabs.getTabs();
                    // 找到已存在的标签中，在 newTabKeys 中位于当前 key 之前的最后一个标签的位置
                    var lastIndex = -1;
                    for (var k = 0; k < existingTabs.length; k++) {
                        var existingKey = existingTabs[k]._key;
                        var pos = newTabKeys.indexOf(existingKey);
                        if (pos !== -1 && pos < i) {
                            if (pos > lastIndex) {
                                lastIndex = pos;
                            }
                        }
                    }
                    // 如果找到了前驱标签，插入到它后面
                    if (lastIndex !== -1) {
                        var targetKey = newTabKeys[lastIndex];
                        var currentAfterRemoval = statTabs.getTabs();
                        for (var m = 0; m < currentAfterRemoval.length; m++) {
                            if (currentAfterRemoval[m]._key === targetKey) {
                                insertIndex = m + 1;
                                break;
                            }
                        }
                    }
                    // 如果 insertIndex 超出范围，追加到末尾
                    if (insertIndex > statTabs.getTabs().length) {
                        insertIndex = statTabs.getTabs().length;
                    }

                    // 创建新标签
                    var divId = 'pieChart_' + key + '_' + Date.now() + '_' + i;
                    var newTab = {
                        name: cfg.id,
                        title: typeof cfg.title === 'function' ? cfg.title() : cfg.title,
                        _key: key,
                        _api: cfg.api,
                        _divId: divId,
                        body: '<div id="' + divId + '" class="pie-chart-container"></div>'
                    };
                    // 插入到指定位置（MiniUI 的 addTab 支持第二个参数为索引）
                    statTabs.addTab(newTab, insertIndex);
                }
            }

            // 6. 决定激活哪个标签
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
                // 加载数据
                loadStatData(targetTab, paramDeviceType, orgId);
            }
        }

        // ================================================================
        // 7. 统计饼图
        // ================================================================
        function loadStatCharts(deviceTypeId, orgId) {
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
                        var data = extractPieData(result);
                        renderPieChart(divId, data, tab.title);
                    } catch (e) {
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
            if (!result) return [{
                name: _loginUserLanguageResource.emptyMsg,
                y: 1
            }];
            var list = result.totalRoot || [];
            var data = [];
            for (var i = 0; i < list.length; i++) {
                if (list[i].itemCode !== 'all' && list[i].count > 0) {
                    data.push({
                        name: list[i].item || list[i].text || '未知',
                        y: list[i].count || 0
                    });
                }
            }
            return data.length > 0 ? data : [{
                name: _loginUserLanguageResource.emptyMsg,
                y: 1
            }];
        }

        function renderPieChart(divId, data, title) {
            var container = document.getElementById(divId);
            if (!container) return;
            if (container._chart) {
                container._chart.destroy();
                container._chart = null;
            }
            if (data.length === 1 && data[0].name === _loginUserLanguageResource.emptyMsg) {
                container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                return;
            }

            var chart = Highcharts.chart(divId, {
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
                    pointFormat: '数量: <b>{point.y}</b><br/>占比: <b>{point.percentage:.1f}%</b>'
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
                        showInLegend: true
                    }
                },
                exporting: {
                    enabled: true,
                    filename: title || '统计图',
                    fallbackToExportServer: false
                },
                series: [{
                    type: 'pie',
                    name: '数量',
                    data: data
                }]
            });
            container._chart = chart;
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
                title: '请选择设备',
                body: '<div class="loading-placeholder">请从列表中选择设备</div>'
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
                    body.innerHTML = '<div class="loading-placeholder">请选择设备</div>';
                }
            }
        }

        function createMiddleTabObject(name) {
            var bodyMap = {
                'middle_WellboreAnalysis': '<div class="loading-placeholder">加载中...</div>',
                'middle_SurfaceAnalysis': '<div class="loading-placeholder">加载中...</div>',
                'middle_TrendCurve': '<div class="loading-placeholder">加载中...</div>',
                'middle_DynamicData': '<div style="display:flex; flex-direction:column; height:100%;">' +
                    '<div class="mini-toolbar" style="border:0;border-bottom:1px solid #ddd;padding:4px 8px;flex-shrink:0;display:flex;align-items:center;gap:6px;">' +
                    '<span style="font-size:12px;color:#333;">' + (_loginUserLanguageResource.viewCurveOrTableData) + '</span>' +
                    '<span style="flex:1;"></span>' +
                    '<button id="dynamicDataExportBtn" class="mini-button" iconCls="export" style="padding:2px 12px;" onclick="exportDeviceRealTimeMonitoringData()">' + (_loginUserLanguageResource.exportData) + '</button>' +
                    '</div>' +
                    '<div id="RealTimeMonitoringInfoDataTableInfoDiv_id" style="flex:1; overflow:hidden; background:#fff; min-height:0;"></div>' +
                    '</div>',
                'middle_placeholder': '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>'
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
                body: bodyMap[name] || '<div class="loading-placeholder">加载中...</div>'
            };
        }

        function createRightTabObject(name) {
            var bodyMap = {
                'right_DeviceControl': '<div id="right_DeviceControl_container" style="width:100%;height:100%;"></div>',
                'right_DeviceInfo': '<div id="right_DeviceInfo_container" style="width:100%;height:100%;"></div>',
                'right_placeholder': '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>'
            };
            var titleMap = {
                'right_DeviceControl': _loginUserLanguageResource.deviceControl,
                'right_DeviceInfo': _loginUserLanguageResource.deviceInformation,
                'right_placeholder': '无信息'
            };
            return {
                name: name,
                title: titleMap[name] || name,
                body: bodyMap[name] || '<div class="loading-placeholder">加载中...</div>'
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
            tabBody.innerHTML = '<div class="chart-grid" id="wellboreGrid">' +
                '<div class="chart-item"><div id="wellboreChart1" class="chart-container"></div></div>' +
                '<div class="chart-item"><div id="wellboreChart2" class="chart-container"></div></div>' +
                '<div class="chart-item"><div id="wellboreChart3" class="chart-container"></div></div>' +
                '<div class="chart-item"><div id="wellboreChart4" class="chart-container"></div></div>' +
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
                            if (el) el.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                        });
                    }
                },
                error: function(xhr, status, errorThrown) {
                    console.log('请求失败:', status, errorThrown);
                    var charts = ['wellboreChart1', 'wellboreChart2', 'wellboreChart3', 'wellboreChart4'];
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
                            document.getElementById('surfaceChart4').innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                        }
                    } else {
                        ['surfaceChart1', 'surfaceChart2', 'surfaceChart3', 'surfaceChart4'].forEach(function(id) {
                            var el = document.getElementById(id);
                            if (el) el.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                        });
                    }
                },
                error: function() {
                    ['surfaceChart1', 'surfaceChart2', 'surfaceChart3', 'surfaceChart4'].forEach(function(id) {
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
            tabBody.innerHTML = '<div class="loading-placeholder"><span class="icon">⏳</span>'+_loginUserLanguageResource.loadingData+'</div>';

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
                        tabBody.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
                        return;
                    }

                    var data = result.list;
                    var curveNames = result.curveItems || [];
                    var deviceNameResult = result.deviceName || deviceName || '';
                    var curveCount = data.length > 0 ? data[0].data.length : 0;

                    if (curveCount === 0) {
                        tabBody.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
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

                        var yTitle = curveNames[i];
                        var titleText = deviceNameResult + ':' + yTitle +   (_loginUserLanguage!='zh_CN'?' ':'') + _loginUserLanguageResource.trendCurve;
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
                        zooming: {
                            mouseWheel: {
                                enabled: false
                            }
                        },
                        zoomType: 'xy'
                    },
                    time: {
                        timezoneOffset: new Date().getTimezoneOffset()
                    },
                    credits: {
                        enabled: false
                    },
                    navigator: {
                        enabled: navigator !== false
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
                            width: getLabelWidth('24' + hourLabel) // 只需要字符串参数
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
                        title: {
                            text: yTitle
                        },
                        opposite: yAxisOpposite || false
                    },
                    tooltip: {
                        crosshairs: true,
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
        }


        var deviceRealTimeMonitoringDataHandsontableHelper = null;
        //---------- 动态数据 ----------
        function loadDynamicData(deviceId) {
            if (deviceRealTimeMonitoringDataHandsontableHelper) {
                if (deviceRealTimeMonitoringDataHandsontableHelper.hot) {
                    deviceRealTimeMonitoringDataHandsontableHelper.hot.destroy();
                }
                deviceRealTimeMonitoringDataHandsontableHelper = null;
            }

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

            container.innerHTML = '<div class="loading-placeholder">加载数据...</div>';

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
                    if (deviceRealTimeMonitoringDataHandsontableHelper == null || deviceRealTimeMonitoringDataHandsontableHelper.hot == undefined) {
                        var colHeaders = [
                            _loginUserLanguageResource.variable,
                            _loginUserLanguageResource.value,
                            _loginUserLanguageResource.variable,
                            _loginUserLanguageResource.value,
                            _loginUserLanguageResource.variable,
                            _loginUserLanguageResource.value
                        ];
                        var columns = [{
                                data: 'name1'
                            },
                            {
                                data: 'value1'
                            },
                            {
                                data: 'name2'
                            },
                            {
                                data: 'value2'
                            },
                            {
                                data: 'name3'
                            },
                            {
                                data: 'value3'
                            }
                        ];
                        deviceRealTimeMonitoringDataHandsontableHelper = DeviceRealTimeMonitoringDataHandsontableHelper.createNew("RealTimeMonitoringInfoDataTableInfoDiv_id");
                        deviceRealTimeMonitoringDataHandsontableHelper.colHeaders = colHeaders;
                        deviceRealTimeMonitoringDataHandsontableHelper.columns = columns;
                        deviceRealTimeMonitoringDataHandsontableHelper.CellInfo = result.CellInfo;

                        if (result.totalRoot.length == 0) {
                            deviceRealTimeMonitoringDataHandsontableHelper.sourceData = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
                            deviceRealTimeMonitoringDataHandsontableHelper.createTable([{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}]);
                        } else {
                            deviceRealTimeMonitoringDataHandsontableHelper.sourceData = result.totalRoot;
                            deviceRealTimeMonitoringDataHandsontableHelper.createTable(result.totalRoot);
                        }

                        deviceRealTimeMonitoringDataHandsontableHelper.hot.addHook('afterOnCellMouseDown', function(event, coords, td) {
                            // 检查是否为双击
                            if (deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer != null &&
                                deviceRealTimeMonitoringDataHandsontableHelper.lastClickRow === coords.row &&
                                deviceRealTimeMonitoringDataHandsontableHelper.lastClickCol === coords.col) {
                                clearTimeout(deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer);
                                deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer = null;
                                viewDeviceRealTimeMonitoringData(coords.row, coords.col);
                                // TODO: 在这里调用打开弹窗的函数
                                deviceRealTimeMonitoringDataHandsontableHelper.lastClickRow = -1;
                                deviceRealTimeMonitoringDataHandsontableHelper.lastClickCol = -1;
                            } else {
                                // 如果是第一次点击，设置定时器
                                deviceRealTimeMonitoringDataHandsontableHelper.lastClickRow = coords.row;
                                deviceRealTimeMonitoringDataHandsontableHelper.lastClickCol = coords.col;
                                if (deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer) {
                                    clearTimeout(deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer);
                                }
                                deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer = setTimeout(() => {
                                    deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer = null;
                                }, 250);
                            }
                        });
                    } else {
                        deviceRealTimeMonitoringDataHandsontableHelper.CellInfo = result.CellInfo;
                        deviceRealTimeMonitoringDataHandsontableHelper.sourceData = result.totalRoot;
                        deviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(result.totalRoot);
                    }
                    //添加单元格属性
                    for (var i = 0; i < deviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length; i++) {
                        var row = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
                        var col = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col;
                        var column = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].column;
                        var columnDataType = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].columnDataType;
                        if (deviceRealTimeMonitoringDataHandsontableHelper.hot != undefined) {
                            deviceRealTimeMonitoringDataHandsontableHelper.hot.setCellMeta(row, col, 'columnDataType', columnDataType);
                        }
                    }
                    deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer = null;
                    deviceRealTimeMonitoringDataHandsontableHelper.lastClickRow = -1;
                    deviceRealTimeMonitoringDataHandsontableHelper.lastClickCol = -1;
                },
                error: function(xhr, status, errorThrown) {
                    console.error('动态数据加载失败:', status, errorThrown);
                    container.innerHTML = '<div class="loading-placeholder error"><span class="icon">❌</span>数据加载失败</div>';
                }
            });
        }

        //Handsontable 相关（动态数据）
        var DeviceRealTimeMonitoringDataHandsontableHelper = {
            createNew: function(divid) {
                var deviceRealTimeMonitoringDataHandsontableHelper = {};
                deviceRealTimeMonitoringDataHandsontableHelper.divid = divid;
                deviceRealTimeMonitoringDataHandsontableHelper.validresult = true; //数据校验
                deviceRealTimeMonitoringDataHandsontableHelper.colHeaders = [];
                deviceRealTimeMonitoringDataHandsontableHelper.columns = [];
                deviceRealTimeMonitoringDataHandsontableHelper.CellInfo = [];
                deviceRealTimeMonitoringDataHandsontableHelper.sourceData = [];

                deviceRealTimeMonitoringDataHandsontableHelper.doubleClickTimer = null;
                deviceRealTimeMonitoringDataHandsontableHelper.lastClickRow = -1;
                deviceRealTimeMonitoringDataHandsontableHelper.lastClickCol = -1;

                deviceRealTimeMonitoringDataHandsontableHelper.addItenmNameColStyle = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.fontWeight = 'bold';
                }

                deviceRealTimeMonitoringDataHandsontableHelper.addSizeBg = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.fontWeight = 'bold';
                    td.style.fontSize = '20px';
                    td.style.height = '40px';
                }

                deviceRealTimeMonitoringDataHandsontableHelper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    var AlarmShowStyle = getAlarmShowStyle();
                    if (row == 0) {
                        Handsontable.renderers.TextRenderer.apply(this, arguments);
                        td.style.fontSize = '20px';
                        td.style.height = '40px';
                    }
                    if (row % 2 == 1 && row > 0) {
                        td.style.backgroundColor = '#f5f5f5';
                    }

                    td.style.whiteSpace = 'nowrap'; //文本不换行
                    td.style.overflow = 'hidden'; //超出部分隐藏
                    td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本

                    for (var i = 0; i < deviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length; i++) {
                        if (isNotVal(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].realtimeColor)) {
                            var row2 = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
                            var col2 = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col * 2;
                            if (row == row2 && col == col2) {
                                td.style.color = '#' + deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].realtimeColor;
                            }
                        }

                        if (isNotVal(deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].realtimeBgColor)) {
                            var row2 = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
                            var col2 = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col * 2;
                            if (row == row2 && col == col2) {
                                td.style.backgroundColor = '#' + deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].realtimeBgColor;
                            }
                        }

                        if (deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel >= 0) {
                            var row2 = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row;
                            var col2 = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col * 2 + 1;
                            if (row == row2 && col == col2) {
                                if (deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel > 0) {
                                    td.style.fontWeight = 'bold';
                                }
                                if (deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel == 0) {
                                    if (AlarmShowStyle.Data.Normal.Opacity != 0) {
                                        td.style.backgroundColor = color16ToRgba('#' + AlarmShowStyle.Data.Normal.BackgroundColor, AlarmShowStyle.Data.Normal.Opacity);
                                    }
                                    td.style.color = '#' + AlarmShowStyle.Data.Normal.Color;
                                } else if (deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel == 100) {
                                    if (AlarmShowStyle.Data.FirstLevel.Opacity != 0) {
                                        td.style.backgroundColor = color16ToRgba('#' + AlarmShowStyle.Data.FirstLevel.BackgroundColor, AlarmShowStyle.Data.FirstLevel.Opacity);
                                    }
                                    td.style.color = '#' + AlarmShowStyle.Data.FirstLevel.Color;
                                } else if (deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel == 200) {
                                    if (AlarmShowStyle.Data.SecondLevel.Opacity != 0) {
                                        td.style.backgroundColor = color16ToRgba('#' + AlarmShowStyle.Data.SecondLevel.BackgroundColor, AlarmShowStyle.Data.SecondLevel.Opacity);
                                    }
                                    td.style.color = '#' + AlarmShowStyle.Data.SecondLevel.Color;
                                } else if (deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].alarmLevel == 300) {
                                    if (AlarmShowStyle.Data.ThirdLevel.Opacity != 0) {
                                        td.style.backgroundColor = color16ToRgba('#' + AlarmShowStyle.Data.ThirdLevel.BackgroundColor, AlarmShowStyle.Data.ThirdLevel.Opacity);
                                    }
                                    td.style.color = '#' + AlarmShowStyle.Data.ThirdLevel.Color;
                                }
                            }
                        }
                    }
                }

                deviceRealTimeMonitoringDataHandsontableHelper.createTable = function(data) {
                    var container = document.getElementById(deviceRealTimeMonitoringDataHandsontableHelper.divid);
                    if (!container) return;
                    container.innerHTML = '';

                    var parent = container.parentNode;
                    var height = 300;
                    if (parent) {
                        var rect = parent.getBoundingClientRect();
                        if (rect.height > 0) {
                            height = rect.height;
                        } else {
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

                    deviceRealTimeMonitoringDataHandsontableHelper.hot = new Handsontable(container, {
                        licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                        theme: 'ht-theme-classic',
                        data: data,
                        colWidths: [30, 20, 30, 20, 30, 20],
                        columns: deviceRealTimeMonitoringDataHandsontableHelper.columns,
                        stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                        rowHeaders: false, //显示行头
                        colHeaders: false,
                        autoWrapRow: false, //自动换行
                        rowHeights: [40],
                        columnSorting: true, //允许排序
                        allowInsertRow: false,
                        sortIndicator: true,
                        manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                        manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                        filters: true,
                        renderAllRows: true,
                        search: true,
                        mergeCells: [{
                            "row": 0,
                            "col": 0,
                            "rowspan": 1,
                            "colspan": 6
                        }],
                        cells: function(row, col, prop) {
                            var cellProperties = {};
                            var visualRowIndex = this.instance.toVisualRow(row);
                            var visualColIndex = this.instance.toVisualColumn(col);
                            cellProperties.editor = false;
                            cellProperties.renderer = deviceRealTimeMonitoringDataHandsontableHelper.addCellStyle;
                            return cellProperties;
                        },
                        afterOnCellMouseOver: function(event, coords, TD) {
                            if (coords.col >= 0 && coords.row >= 0 && deviceRealTimeMonitoringDataHandsontableHelper != null && deviceRealTimeMonitoringDataHandsontableHelper.hot != '' && deviceRealTimeMonitoringDataHandsontableHelper.hot != undefined && deviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell != undefined) {
                                var rawValue = deviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell(coords.row, coords.col);
                                if (isNotVal(rawValue)) {
                                    var showValue = rawValue;
                                    var rowChar = 90;
                                    var maxWidth = rowChar * 10;
                                    if (rawValue.length > rowChar) {
                                        showValue = '';
                                        let arr = [];
                                        let index = 0;
                                        while (index < rawValue.length) {
                                            arr.push(rawValue.slice(index, index += rowChar));
                                        }
                                        for (var i = 0; i < arr.length; i++) {
                                            showValue += arr[i];
                                            if (i < arr.length - 1) {
                                                showValue += '<br>';
                                            }
                                        }
                                    }

                                    // ★ 清除定时器
                                    if (window._hoverTimer) {
                                        clearTimeout(window._hoverTimer);
                                        window._hoverTimer = null;
                                    }

                                    // ★ 强制关闭所有已存在的提示框（使用 DOM 方式）
                                    if (typeof window.closeAllTips === 'function') {
                                        window.closeAllTips();
                                    }

                                    // ★ 延迟创建新提示
                                    window._hoverTimer = setTimeout(function() {
                                        if (typeof mini !== 'undefined' && mini.showTips) {
                                            mini.showTips({
                                                x: event.clientX + 10,
                                                y: event.clientY + 10,
                                                content: showValue,
                                                state: 'default',
                                                timeout: 3000
                                            });
                                        } else {
                                            TD.title = rawValue;
                                        }
                                        window._hoverTimer = null;
                                    }, 300);
                                }
                            }
                        },
                        afterOnCellMouseOut: function(event, coords, TD) {
                            if (window._hoverTimer) {
                                clearTimeout(window._hoverTimer);
                                window._hoverTimer = null;
                            }
                            if (typeof window.closeAllTips === 'function') {
                                window.closeAllTips();
                            }
                        }
                    });
                }
                return deviceRealTimeMonitoringDataHandsontableHelper;
            }
        };

        function viewDeviceRealTimeMonitoringData(row, col) {
            if (deviceRealTimeMonitoringDataHandsontableHelper != null && deviceRealTimeMonitoringDataHandsontableHelper.hot != undefined && row > 0) {
                if (col % 2 == 1) {
                    col = (col - 1) / 2;
                } else {
                    col = col / 2;
                }

                var cellInfo = null;
                for (var i = 0; i < deviceRealTimeMonitoringDataHandsontableHelper.CellInfo.length; i++) {
                    if (deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].row == row && deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i].col == col) {
                        cellInfo = deviceRealTimeMonitoringDataHandsontableHelper.CellInfo[i];
                    }
                }
                if (cellInfo != null) {
                    var itemName = deviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell(row, col * 2);
                    var itemValue = deviceRealTimeMonitoringDataHandsontableHelper.hot.getDataAtCell(row, col * 2 + 1);
                    var info = itemName + ':' + itemValue;
                    if (cellInfo.type == 0) {
                        if (cellInfo.resolutionMode == 2) {
                            info += ',采集数据量';
                            if (cellInfo.columnDataType.toUpperCase() != 'string'.toUpperCase() && isNumber(itemValue)) {
                                viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                            } else {
                                viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                            }
                        } else if (cellInfo.resolutionMode == 0) {
                            info += ',采集开关量';
                            viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                        } else if (cellInfo.resolutionMode == 1) {
                            info += ',采集枚举量';
                            viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                        }
                    } else if (cellInfo.type == 1) {
                        info += ',计算项';
                        if (isNumByCalculateItemCode(cellInfo.column)) {
                            viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                        } else {
                            viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                        }
                    } else if (cellInfo.type == 3) {
                        info += ',录入项';
                        if (isNumber(itemValue)) {
                            viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                        } else {
                            viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                        }
                    } else if (cellInfo.type == 5) {
                        if (cellInfo.resolutionMode == 2) {
                            info += ',协议拓展项数据量';
                            if (isNumber(itemValue)) {
                                viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                            } else {
                                viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                            }
                        } else if (cellInfo.resolutionMode == 0) {
                            info += ',协议拓展项开关量';
                            viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                        } else if (cellInfo.resolutionMode == 1) {
                            info += ',协议拓展项枚举量';
                            viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                        } else if (cellInfo.resolutionMode == 7) {
                            info += ',协议拓展项数值运算项';
                            if (isNumber(itemValue)) {
                                viewItemRealTimeCurve(itemName, itemValue, cellInfo);
                            } else {
                                viewItemRealTimeDataTable(itemName, itemValue, cellInfo);
                            }
                        }
                    }
                    console.log(info);
                }
            }
        }

        //存储当前曲线窗口上下文
        var _curveWindowContext = null;

        function viewItemRealTimeCurve(itemName, itemValue, cellInfo) {
            // 获取当前选中的设备信息
            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            if (!selected) {
                mini.alert('请先选择设备');
                return;
            }

            var deviceId = selected.id;
            var deviceName = selected.deviceName;
            var calculateType = selected.calculateType || 0;

            // 创建 MiniUI 窗口
            var win = new mini.Window();
            win.set({
                title: _loginUserLanguageResource.trendCurve,
                width: '65%',
                height: '50%',
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
            win.setBody('<div id="' + containerId + '" style="width:100%;height:100%;"></div>');

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
                        document.getElementById(ctx.containerId).innerHTML = '<div style="text-align:center;padding:20px;">'+_loginUserLanguageResource.emptyMsg+'</div>';
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
                    if (typeof initDeviceRealtimeMonitoringStockChartFn === 'function') {
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
                    } else {
                        // 降级方案
                        Highcharts.chart(ctx.containerId, {
                            chart: {
                                type: 'spline'
                            },
                            title: {
                                text: title
                            },
                            xAxis: {
                                type: 'datetime',
                                title: {
                                    text: _loginUserLanguageResource.time
                                }
                            },
                            yAxis: {
                                title: {
                                    text: legendName[0]
                                },
                                max: maxValue,
                                min: minValue
                            },
                            series: series,
                            credits: {
                                enabled: false
                            },
                            exporting: {
                                enabled: true,
                                fallbackToExportServer: false
                            }
                        });
                    }
                },
                error: function(xhr, status, errorThrown) {
                    mini.unmask(mask);
                    document.getElementById(ctx.containerId).innerHTML = '<div style="text-align:center;padding:20px;color:red;">加载失败</div>';
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
                mini.alert('请先选择设备');
                return;
            }

            var deviceId = selected.id;
            var deviceName = selected.deviceName;
            var calculateType = selected.calculateType || 0;

            // 创建 MiniUI 窗口
            var win = new mini.Window();
            win.set({
                title: _loginUserLanguageResource.dynamicData || '动态数据',
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
            html += '<span style="font-size:12px; color:#333;">' + (_loginUserLanguageResource.totalCount || '总条数') + ': <span id="itemRealtimeDataCount_' + containerId + '">0</span></span>';
            html += '<span style="flex:1;"></span>';
            html += '<button id="exportItemRealtimeDataBtn_' + containerId + '" class="mini-button" iconCls="export" style="padding:2px 12px;">' + (_loginUserLanguageResource.exportData || '导出') + '</button>';
            html += '</div>';
            // Handsontable 容器
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

            // 窗口关闭时清理
            win.on('beforedestroy', function() {
                if (window._itemRealtimeDataHot) {
                    window._itemRealtimeDataHot.destroy();
                    window._itemRealtimeDataHot = null;
                }
                _dataWindowContext = null;
            });
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
                    if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                        document.getElementById(ctx.containerId).innerHTML = '<div style="text-align:center;padding:20px;">'+_loginUserLanguageResource.emptyMsg+'</div>';
                        return;
                    }

                    // 更新总条数
                    var countEl = document.getElementById(ctx.countId);
                    if (countEl) countEl.innerText = result.totalCount || result.totalRoot.length;

                    var data = result.totalRoot;

                    // ---------- 以下 Handsontable 创建逻辑（复用原有） ----------
                    var container = document.getElementById(ctx.containerId);
                    var parent = container.parentNode;
                    var height = 300;
                    if (parent) {
                        var rect = parent.getBoundingClientRect();
                        if (rect.height > 0) height = rect.height;
                    }
                    container.style.height = height + 'px';
                    container.style.width = '100%';

                    // 销毁旧实例
                    if (window._itemRealtimeDataHot) {
                        window._itemRealtimeDataHot.destroy();
                        window._itemRealtimeDataHot = null;
                    }

                    var hot = new Handsontable(container, {
                        licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                        theme: 'ht-theme-classic',
                        data: data,
                        columns: [{
                                data: 'acqTime',
                                title: _loginUserLanguageResource.acqTime
                            },
                            {
                                data: 'data',
                                title: ctx.itemName
                            }
                        ],
                        stretchH: 'all',
                        rowHeaders: true,
                        colHeaders: true,
                        fixedColumnsStart: 1,
                        autoWrapRow: false,
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
                            cellProperties.editor = false;
                            cellProperties.renderer = function(instance, td, row, col, prop, value, cellProperties) {
                                Handsontable.renderers.TextRenderer.apply(this, arguments);
                                td.style.whiteSpace = 'nowrap';
                                td.style.overflow = 'hidden';
                                td.style.textOverflow = 'ellipsis';
                            };
                            return cellProperties;
                        },
                        afterOnCellMouseOver: function(event, coords, TD) {
                            if (coords.col >= 0 && coords.row >= 0 && hot) {
                                var rawValue = hot.getDataAtCell(coords.row, coords.col);
                                if (rawValue && rawValue !== '') {
                                    TD.title = rawValue;
                                }
                            }
                        }
                    });

                    window._itemRealtimeDataHot = hot;
                    hot.render();

                    // 窗口 resize 时调整高度
                    ctx.win.on('resize', function() {
                        if (window._itemRealtimeDataHot) {
                            var container = document.getElementById(ctx.containerId);
                            if (container) {
                                var parent = container.parentNode;
                                var h = parent ? parent.clientHeight || 300 : 300;
                                container.style.height = h + 'px';
                                window._itemRealtimeDataHot.updateSettings({
                                    height: h
                                });
                            }
                        }
                    });
                },
                error: function(xhr, status, errorThrown) {
                    mini.unmask(mask);
                    document.getElementById(ctx.containerId).innerHTML = '<div style="text-align:center;padding:20px;color:red;">加载失败</div>';
                    console.error('加载数据表失败:', status, errorThrown);
                }
            });
        }

        // 导出数据表（适配 MiniUI）
        function exportItemRealtimeDataTable(deviceId, deviceName, calculateType, itemName, itemCode, itemType, itemResolutionMode, itemBitIndex) {
            if (!deviceId) {
                mini.alert('请先选择设备');
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
            exportDataMask(key, container, _loginUserLanguageResource.loadingData || '数据导出中...');
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
                title: '请选择设备',
                body: '<div class="loading-placeholder">请从列表中选择设备</div>'
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
                container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.noSelectionRecord+'</div>';
                return;
            }
            var selected = grid.getSelected();
            var deviceId = selected.id;
            var deviceName = selected.deviceName;
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

            container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.loadingData+'</div>';

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
                        container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
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
                                header:  _loginUserLanguageResource.operation,
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
                                            ' onclick="onNumericControlClick(' + record.id + ', \'' + record.itemcode + '\', \'' + record.itemName + '\', \'' + (record.unit || '') + '\', ' + record.quantity + ', \'' + record.storeDataType + '\', ' + disabled + ')">'+_loginUserLanguageResource.set+'</button>';
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
                        container.innerHTML = '<div class="loading-placeholder">'+_loginUserLanguageResource.emptyMsg+'</div>';
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
                        html += '<th style="padding:6px 10px; text-align:left; border:1px solid #ddd;">'+_loginUserLanguageResource.variable+'</th>';
                        html += '<th style="padding:6px 10px; text-align:center; border:1px solid #ddd;">'+_loginUserLanguageResource.value+'</th>';
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
                        html += '<div class="loading-placeholder" style="height:100%;">'+_loginUserLanguageResource.emptyMsg+'</div>';
                    }
                    html += '</div>';

                    // ---- 辅件设备（下半部分，默认全部展开，保留列头，无操作列） ----
                    html += '<div style="flex:1; min-height:0; overflow:auto; padding:4px;">';
                    if (auxList.length > 0) {
                        html += '<table style="width:100%; border-collapse:collapse; font-size:12px;">';
                        html += '<thead><tr style="background:#f5f7fa;">';
                        html += '<th style="padding:4px 8px; border:1px solid #ddd; text-align:center; width:50px;">'+_loginUserLanguageResource.idx+'</th>';
                        html += '<th style="padding:4px 8px; border:1px solid #ddd; text-align:left;">'+_loginUserLanguageResource.deviceName+'</th>';
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
                        html += '<div class="loading-placeholder" style="height:100%;">'+_loginUserLanguageResource.emptyMsg+'</div>';
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
                title: itemName +(_loginUserLanguage!='zh_CN'?' ':'')+_loginUserLanguageResource.trendCurve,
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
                        },
                        events: {
                            // 监听缩放完成事件
                            redraw: function() {
                                // 缩放完成后隐藏遮罩
                                if (isZooming) {
                                    setTimeout(function() {
                                        Ext.getCmp(panelId).getEl().unmask();
                                        isZooming = false;
                                        if (zoomTimer) clearTimeout(zoomTimer);
                                    }, 300);
                                }
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
                            formatter: function () {
                                return this.axis.chart.time.dateFormat(timeFormat, this.value);
                            },
                            autoRotation: true, //自动旋转
                            rotation: -45 //倾斜度，防止数量过多显示不全  
                        },
                        events: {
                            // 当范围即将改变时触发（缩放前）
                            setExtremes: function(e) {
                                // 检查范围是否真的会改变
                                var currentMin = this.min;
                                var currentMax = this.max;
                                var newMin = e.min;
                                var newMax = e.max;
                                
                                // 如果范围没有实际变化，不显示遮罩
                                if (currentMin === newMin && currentMax === newMax) {
                                    return;
                                }
                                
                                // 实际发生了缩放，显示遮罩
                                if (!isZooming) {
                                    isZooming = true;
                                    Ext.getCmp(panelId).el.mask(loginUserLanguageResource.loadingData).show();
                                }
                                
                                // 设置超时保护
                                if (zoomTimer) clearTimeout(zoomTimer);
                                zoomTimer = setTimeout(function() {
                                    if (isZooming) {
                                        Ext.getCmp(panelId).getEl().unmask();
                                        isZooming = false;
                                    }
                                }, 5000);
                            }
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
                        document.getElementById(divId).innerHTML = '<div style="text-align:center;padding:20px;">'+_loginUserLanguageResource.emptyMsg+'</div>';
                        return;
                    }
                    var chartData = result.totalRoot;
                    var legend=false;
                    var series = buildResourceSeries(chartData, itemCode, itemName);
                    if(series.length>0){
                    	legend=true;
                    }
                    var title = itemName.split("(")[0];
                    var subtitle="[" + result.startDate + "~" + result.endDate + "]";
                    var yTitle = itemName;
                    var tickInterval = Math.floor(chartData.length / 10) + 1;
                    var color = ['#800000', // 红
                        '#008C00', // 绿
                        '#000000', // 黑
                        '#0000FF', // 蓝
                        '#F4BD82', // 黄
                        '#FF00FF' // 紫
                      ];
                    
                    var timeFormat='%m-%d';
                    if(chartData.length>0 && result.minAcqTime.split(' ')[0]==result.maxAcqTime.split(' ')[0]){
                	    timeFormat='%H:%M';
                    }

                    if (typeof initResourceProbeHistoryCurveChartFn === 'function') {
                        initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, _loginUserLanguageResource.time, yTitle, color, legend, timeFormat);
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
                    name: _loginUserLanguageResource.dataTablespace+"(%)",
                    data: dataSpace
                });
                series.push({
                    name: _loginUserLanguageResource.undoTablespace+"(%)",
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

            var grid = mini.get('deviceGrid');
            if (grid) {
                grid.setColumns(DEFAULT_COLUMNS);
            }

            buildLevel1Tabs();
            initI18n();

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

        // 示例：处理实时数据（需根据原 ExtJS 逻辑实现）
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
                                        '目前扭矩曲线',
                                        data.surfaceChartsData.deviceName || '',
                                        data.surfaceChartsData.acqTime || '',
                                        'surfaceChart2'
                                    );
                                    var deltaRadius = parseFloat(data.surfaceChartsData.deltaRadius) || 0;
                                    var expectedTitle = '扭矩曲线';
                                    if (Math.abs(deltaRadius) > 0) {
                                        expectedTitle = (deltaRadius > 0 ? '外移' : '內移') + Math.abs(deltaRadius) + 'cm' + expectedTitle;
                                    } else {
                                        expectedTitle = '预期扭矩曲线';
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
                                if (deviceRealTimeMonitoringDataHandsontableHelper &&
                                    deviceRealTimeMonitoringDataHandsontableHelper.hot) {
                                    deviceRealTimeMonitoringDataHandsontableHelper.CellInfo = data.CellInfo;
                                    deviceRealTimeMonitoringDataHandsontableHelper.sourceData = data.totalRoot;
                                    deviceRealTimeMonitoringDataHandsontableHelper.hot.loadData(data.totalRoot);
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
                        if (deviceRealTimeMonitoringDataHandsontableHelper &&
                            deviceRealTimeMonitoringDataHandsontableHelper.hot) {
                            var statusText = data.commStatus > 0 ?
                                (_loginUserLanguageResource.goOnline || '上线') :
                                (_loginUserLanguageResource.offline || '离线');
                            var value = data.deviceName + ':' + data.acqTime + ' ' + statusText;
                            deviceRealTimeMonitoringDataHandsontableHelper.hot.setDataAtCell(0, 0, value);
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

        function updateResourceMonitorUI(data) {
            var cpuBtn = mini.get('CPUUsedPercentLabel_id');
            var memBtn = mini.get('memUsedPercentLabel_id');
            var tableBtn = mini.get('tableSpaceSizeProbeLabel_id');
            var redisBtn = mini.get('redisRunStatusProbeLabel_id');
            var adBtn = mini.get('adRunStatusProbeLabel_id');
            var acBtn = mini.get('acRunStatusProbeLabel_id');
            var licenseBtn = mini.get('adLicenseStatusProbeLabel_id');

            // ===== CPU：仅文字颜色 =====
            if (cpuBtn) {
                var cpuText = (_loginUserLanguageResource.resourcesMonitoring_cpu || 'CPU') + ':' + (data.cpuUsedPercent || '0') + '%';
                cpuBtn.setText(cpuText);
                cpuBtn.setIconCls('');
                var textEl = cpuBtn.getEl() ? cpuBtn.getEl().querySelector('.mini-button-text') : null;
                if (textEl) {
                    if (data.cpuUsedPercentAlarmLevel == 1) {
                        textEl.style.color = '#F09614';
                    } else if (data.cpuUsedPercentAlarmLevel == 2) {
                        textEl.style.color = '#DC2828';
                    } else {
                        textEl.style.color = '';
                    }
                }
            }

            // ===== 内存：仅文字颜色 =====
            if (memBtn) {
                var memText = (_loginUserLanguageResource.resourcesMonitoring_mem || '内存') + ':' + (data.memUsedPercent || '0') + '%';
                memBtn.setText(memText);
                memBtn.setIconCls('');
                var textEl = memBtn.getEl() ? memBtn.getEl().querySelector('.mini-button-text') : null;
                if (textEl) {
                    if (data.memUsedPercentAlarmLevel == 1) {
                        textEl.style.color = '#F09614';
                    } else if (data.memUsedPercentAlarmLevel == 2) {
                        textEl.style.color = '#DC2828';
                    } else {
                        textEl.style.color = '';
                    }
                }
            }

            // ===== 表空间：图标 + 文字颜色 =====
            if (tableBtn) {
                if (data.dbConnStatus == 1) {
                    var showInfo = (_loginUserLanguageResource.resourcesMonitoring_tablespaces || '表空间') + ':' +
                        (data.tableSpaceUsedPercent || '0') + '%;' +
                        (data.undoTableSpaceUsedPercent || '0') + '%';
                    tableBtn.setText(showInfo);
                    tableBtn.setIconCls('dtgreen');
                    var textEl = tableBtn.getEl() ? tableBtn.getEl().querySelector('.mini-button-text') : null;
                    if (textEl) {
                        if (data.tableSpaceUsedPercentAlarmLevel == 1) {
                            textEl.style.color = '#F09614';
                        } else if (data.tableSpaceUsedPercentAlarmLevel == 2) {
                            textEl.style.color = '#DC2828';
                        } else {
                            textEl.style.color = '';
                        }
                    }
                } else {
                    tableBtn.setText(_loginUserLanguageResource.resourcesMonitoring_tablespaces || '表空间');
                    tableBtn.setIconCls('dtyellow');
                    var textEl = tableBtn.getEl() ? tableBtn.getEl().querySelector('.mini-button-text') : null;
                    if (textEl) textEl.style.color = '';
                }
            }

            // ===== 缓存（Redis）：图标 + 文字 =====
            if (redisBtn) {
                if (data.redisStatus == 1) {
                    var redisText = (_loginUserLanguageResource.resourcesMonitoring_cache || '缓存') + ':' +
                        (data.cacheUsedMemory || '0') + 'm/' + (data.cacheMaxMemory || '0') + 'm';
                    redisBtn.setText(redisText);
                    redisBtn.setIconCls('dtgreen');
                } else {
                    redisBtn.setText(_loginUserLanguageResource.resourcesMonitoring_cache || '缓存');
                    redisBtn.setIconCls('dtyellow');
                }
                // 确保文字颜色正常（默认）
                var textEl = redisBtn.getEl() ? redisBtn.getEl().querySelector('.mini-button-text') : null;
                if (textEl) textEl.style.color = '';
            }

            // ===== AD（通信服务）：图标 + 文字 =====
            if (adBtn) {
                adBtn.setText(_loginUserLanguageResource.resourcesMonitoring_ad || '通信服务');
                adBtn.setIconCls(data.adRunStatus == 1 ? 'dtgreen' : 'dtyellow');
                var textEl = adBtn.getEl() ? adBtn.getEl().querySelector('.mini-button-text') : null;
                if (textEl) textEl.style.color = '';
            }

            // ===== AC（计算服务）：图标 + 文字 =====
            if (acBtn) {
                acBtn.setText(_loginUserLanguageResource.resourcesMonitoring_ac || '计算服务');
                acBtn.setIconCls(data.acRunStatus == 1 ? 'dtgreen' : 'dtyellow');
                var textEl = acBtn.getEl() ? acBtn.getEl().querySelector('.mini-button-text') : null;
                if (textEl) textEl.style.color = '';
            }

            // ===== License：仅红色文字，无图标 =====
            if (licenseBtn) {
                if (data.licenseSign) {
                    licenseBtn.setText('License:' + (data.deviceAmount || 0) + '/' + (data.license || 0));
                    licenseBtn.setIconCls('');
                    var textEl = licenseBtn.getEl() ? licenseBtn.getEl().querySelector('.mini-button-text') : null;
                    if (textEl) {
                        textEl.style.color = '#DC2828';
                    }
                    licenseBtn.show();
                } else {
                    licenseBtn.hide();
                }
            }
        }

        function updateDBMonitorUI(data) {
            var tableBtn = mini.get('tableSpaceSizeProbeLabel_id');
            if (!tableBtn) return;

            // 数据库连接正常
            if (data.dbConnStatus == 1) {
                var showInfo = (_loginUserLanguageResource.resourcesMonitoring_tablespaces || '表空间') + ':' +
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
                tableBtn.setText(_loginUserLanguageResource.resourcesMonitoring_tablespaces || '表空间');
                tableBtn.setIconCls('dtyellow');
                tableBtn.getEl().dom.style.color = '';
            }
        }

        function handleAdExit(data) {
            // 1. 重新加载当前激活的统计图表
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
                    } else {
                        // 降级方案：重新加载所有统计标签页
                        loadStatCharts(deviceTypeId, orgId);
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
            var helper = null;

            function loadData() {
                mini.mask({
                    el: win.getBodyEl(),
                    cls: 'mini-mask-loading',
                    html: '加载中...'
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
                        mini.unmask(win.getBodyEl()); // 直接移除遮罩
                        console.log('数据加载成功', result);

                        if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                            document.getElementById(containerId).innerHTML = '<div style="text-align:center;padding:20px;">'+_loginUserLanguageResource.emptyMsg+'</div>';
                            return;
                        }

                        var data = result.totalRoot;
                        var isSingleRow = (data.length === 1);

                        var colHeaders = [
                            _loginUserLanguageResource.idx,
                            _loginUserLanguageResource.uplinkValue,
                            _loginUserLanguageResource.uplink,
                            _loginUserLanguageResource.downlinkValue,
                            _loginUserLanguageResource.downlink
                        ];
                        var columns = [];

                        // ★★★ 1. 序号列（只读 + 灰色背景） ★★★
                        columns.push({
                            data: 'index',
                            readOnly: true,
                            renderer: function(instance, td, row, col, prop, value, cellProperties) {
                                Handsontable.renderers.TextRenderer.apply(this, arguments);
                                td.style.backgroundColor = '#f5f5f5';
                                td.style.fontWeight = 'bold';
                                td.style.textAlign = 'center';
                            },
                            editor: false
                        });

                        // ★★★ 2. 上行值列（只读 + 灰色背景） ★★★
                        columns.push({
                            data: 'uplinkStatus',
                            readOnly: true,
                            renderer: function(instance, td, row, col, prop, value, cellProperties) {
                                Handsontable.renderers.TextRenderer.apply(this, arguments);
                                td.style.backgroundColor = '#f5f5f5';
                                td.style.fontWeight = 'bold';
                                td.style.textAlign = 'left';
                            },
                            editor: false
                        });

                        columns.push({
                            data: 'uplink',
                            renderer: function(instance, td, row, col, prop, value, cellProperties) {
                                td.innerHTML = '';
                                var btn = document.createElement('button');
                                btn.textContent = _loginUserLanguageResource.uplink;
                                btn.className = 'mini-button';
                                btn.style.padding = '2px 8px';
                                btn.style.fontSize = '11px';
                                btn.style.cursor = 'pointer';
                                btn.style.border = '1px solid #ccc';
                                btn.style.borderRadius = '3px';
                                btn.style.background = '#f5f5f5';
                                btn.onclick = function(e) {
                                    e.stopPropagation();
                                    performRowUplink(row);
                                };
                                td.appendChild(btn);
                                return td;
                            },
                            readOnly: true
                        });

                        var valueColumn = {
                            data: 'value',
                            type: 'text'
                        };
                        if (storeDataType.toUpperCase() !== 'BCD' && storeDataType.toUpperCase() !== 'STRING') {
                            valueColumn.validator = function(val, callback) {
                                if (val !== null && val !== undefined && val !== '' && isNaN(val)) {
                                    callback(false);
                                    return false;
                                }
                                callback(true);
                                return true;
                            };
                        }
                        columns.push(valueColumn);

                        columns.push({
                            data: 'downlink',
                            renderer: function(instance, td, row, col, prop, value, cellProperties) {
                                td.innerHTML = '';
                                var btn = document.createElement('button');
                                btn.textContent = _loginUserLanguageResource.downlink;
                                btn.className = 'mini-button';
                                btn.style.padding = '2px 8px';
                                btn.style.fontSize = '11px';
                                btn.style.cursor = 'pointer';
                                btn.style.border = '1px solid #ccc';
                                btn.style.borderRadius = '3px';
                                btn.style.background = '#f5f5f5';
                                btn.onclick = function(e) {
                                    e.stopPropagation();
                                    performRowDownlink(row);
                                };
                                td.appendChild(btn);
                                return td;
                            },
                            readOnly: true
                        });

                        // 单行时显示按钮列，多行时隐藏按钮列
                        var hiddenColumns = isSingleRow ? [] : [2, 4];

                        helper = DeviceControlValueHandsontableHelper.createNew(containerId);
                        helper.colHeaders = colHeaders;
                        helper.columns = columns;
                        helper.hiddenColumns = hiddenColumns;
                        helper.colWidths = isSingleRow ? [50, 100, 60, 100, 60] : [50, 80, 60, 80, 60];
                        helper.createTable(data);

                        // 工具栏按钮显隐：单行隐藏，多行显示
                        if (isSingleRow) {
                            if (uplinkBtn) uplinkBtn.hide();
                            if (downlinkBtn) downlinkBtn.hide();
                        } else {
                            if (uplinkBtn) uplinkBtn.show();
                            if (downlinkBtn) downlinkBtn.show();
                        }

                        if (uplinkBtn) {
                            uplinkBtn.on('click', function() {
                                performGlobalUplink();
                            });
                        }
                        if (downlinkBtn) {
                            downlinkBtn.on('click', function() {
                                performGlobalDownlink();
                            });
                        }

                        win.on('resize', function() {
                            if (helper && helper.hot) {
                                var container = document.getElementById(containerId);
                                if (container) {
                                    var parent = container.parentNode;
                                    var h = parent ? parent.clientHeight || 300 : 300;
                                    container.style.height = h + 'px';
                                    helper.hot.updateSettings({
                                        height: h
                                    });
                                }
                            }
                        });

                    },
                    error: function(xhr, status, errorThrown) {
                        mini.unmask(win.getBodyEl());
                        document.getElementById(containerId).innerHTML = '<div style="text-align:center;padding:20px;color:red;">加载失败</div>';
                        console.error('加载控制值列表失败:', status, errorThrown);
                    }
                });
            }

            // ---- 操作函数（略，与之前相同） ----
            function performRowUplink(rowIndex) {
                if (!helper || !helper.hot) return;
                var m = mini.mask({
                    el: win.getBodyEl(),
                    cls: 'mini-mask-loading',
                    html: '上行中...'
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
                        if (result.flag && result.flag == true && result.error == false && result.data) {
                            var uplinkValues = result.data.split(',');
                            if (uplinkValues.length > rowIndex) {
                                helper.hot.setDataAtCell(rowIndex, 1, uplinkValues[rowIndex]);
                            }
                        } else {
                            mini.alert(result.msg || '上行失败');
                        }
                    },
                    error: function() {
                        mini.unmask(win.getBodyEl());
                        mini.alert('上行请求失败');
                    }
                });
            }

            function performRowDownlink(rowIndex) {
                if (!helper || !helper.hot) return;
                var value = helper.hot.getDataAtCell(rowIndex, 3);
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
            }

            function performGlobalUplink() {
                if (!helper || !helper.hot) return;
                var rowCount = helper.hot.countRows();
                mini.mask({
                    el: win.getBodyEl(),
                    cls: 'mini-mask-loading',
                    html: '上行中...'
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
                        if (result.flag && result.flag == true && result.error == false && result.data) {
                            var uplinkValues = result.data.split(',');
                            for (var i = 0; i < Math.min(rowCount, uplinkValues.length); i++) {
                                helper.hot.setDataAtCell(i, 1, uplinkValues[i]);
                            }
                        } else {
                            mini.alert(result.msg || '上行失败');
                        }
                    },
                    error: function() {
                        mini.unmask(win.getBodyEl());
                        mini.alert('上行请求失败');
                    }
                });
            }

            function performGlobalDownlink() {
                if (!helper || !helper.hot) return;
                var rowCount = helper.hot.countRows();
                var values = [];
                var isValid = true;
                for (var i = 0; i < rowCount; i++) {
                    var v = helper.hot.getDataAtCell(i, 3);
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
            }

            function sendBatchControl(values) {
                var controlValue = values.join(',');
                mini.mask({
                    el: win.getBodyEl(),
                    cls: 'mini-mask-loading',
                    html: '指令发送中...'
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
                            mini.alert(result.msg || '会话过期，请重新登录');
                        } else if (result.flag == true && result.error == false) {
                            mini.alert(result.msg || '操作成功');
                        } else if (result.flag == true && result.error == true) {
                            mini.alert(result.msg || '操作失败');
                        }
                    },
                    error: function() {
                        mini.unmask(win.getBodyEl());
                        mini.alert('请求失败');
                    }
                });
            }

            win.on('beforedestroy', function() {
                if (helper && helper.hot) {
                    helper.hot.destroy();
                    helper = null;
                }
                mini.unmask(win.getBodyEl());
            });

            loadData();
        }


        function exportDeviceRealTimeMonitoringData() {
            // 获取当前选中的设备
            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            if (!selected) {
                mini.alert('请先选择设备');
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
