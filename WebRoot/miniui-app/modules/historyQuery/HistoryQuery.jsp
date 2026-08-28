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

        .history-container {
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

        .device-grid-wrapper {
            flex: 1;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
        }

        .stat-pie-wrapper {
            flex: 1;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
        }

        .right-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #f0f2f5;
            padding: 4px;
        }

        .query-toolbar {
            flex-shrink: 0;
            background: #fff;
            padding: 4px 10px;
            border-bottom: 1px solid #e8e8e8;
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 4px;
            border-radius: 4px 4px 0 0;
        }

        .query-toolbar .mini-label {
            font-size: 12px;
            color: #333;
        }

        .result-tabs {
            flex: 1;
            overflow: hidden;
            background: #fff;
            border-radius: 0 0 4px 4px;
        }

        .result-tabs .mini-tabs {
            width: 100%;
            height: 100%;
        }

        .result-tabs .mini-tabs .mini-tab-body {
            overflow: hidden !important;
            padding: 0 !important;
            margin: 0 !important;
            display: flex !important;
            flex-direction: column !important;
        }

        .chart-container {
            width: 100%;
            height: 100%;
            min-height: 200px;
        }

        .table-container {
            width: 100%;
            height: 100%;
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

        .pie-chart-container {
            width: 100%;
            height: 100%;
            min-height: 100px;
        }

        .hidden {
            display: none;
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
            box-sizing: border-box;
        }

        .device-name-cell {
            white-space: nowrap;
        }

        .tiled-chart-container {
            width: 100%;
            height: 100%;
            overflow: auto;
            padding: 2px;
            box-sizing: border-box;
        }

        .tiled-chart-container .chart-item {
            float: left;
            box-sizing: border-box;
            padding: 2px;
        }

        /* 让 mini-tabs 的左侧标签宽度固定，显得整齐 */
        .mini-tabs-tab-left {
            width: 60px !important;
            text-align: center;
        }

    </style>
</head>

<body>
    <div class="history-container">
        <!-- 主区域 -->
        <div style="display:flex; flex:1; overflow:hidden; order:0;">
            <!-- 二级标签 -->
            <div class="level2-sidebar" id="level2Sidebar">
                <div class="no-child-tip">选择一级</div>
            </div>
            <!-- 内容区域 -->
            <div style="flex:1; overflow:hidden;">
                <div class="mini-splitter" style="width:100%; height:100%;" vertical="false" onresize="onSplitterResize">
                    <!-- 左侧：设备列表 + 饼图（垂直分割，饼图可折叠） -->
                    <div size="35%" showCollapseButton="false" minSize="200">
                        <div class="left-panel">
                            <div class="mini-splitter" style="width:100%; height:100%;" vertical="true">
                                <!-- 设备列表（带工具栏） -->
                                <div id="deviceGridPanel" size="50%" showCollapseButton="false">
                                    <div class="device-grid-wrapper" style="height:100%; display:flex; flex-direction:column;">
                                        <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:4px 8px;display:flex;align-items:center;gap:6px;flex-shrink:0;">
                                            <button id="btnRefresh" class="mini-button" iconCls="note-refresh" onclick="refreshDeviceList()">刷新</button>
                                            <input id="deviceCombo" class="mini-combobox" style="width:140px;" emptyText="-- 全部 --" allowInput="true" url="<%=path%>/wellInformationManagerController/loadWellComboxList" onbeforeload="onDeviceComboBeforeLoad" onshowpopup="onDeviceComboShowPopup" onload="onDeviceComboLoad" dataField="list" totalField="totals" valueField="boxkey" textField="boxval" onvaluechanged="onDeviceComboChange" />
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
                                            <div id="deviceGrid" class="mini-datagrid" style="width:100%; height:100%;" idField="id" pageSize="20" allowResize="true" allowAlternating="true" url="<%=path%>/historyQueryController/getHistoryQueryDeviceList" dataField="totalRoot" totalField="totalCount" onselectionchanged="onDeviceSelect" onload="onDeviceGridLoad" onbeforeload="onDeviceGridBeforeLoad" ondrawcell="onDeviceGridDrawCell">
                                                <div property="columns">
                                                    <!-- 动态生成 -->
                                                </div>
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
        try {
            if (window.parent && window.parent.tabInfo) tabInfo = window.parent.tabInfo;
        } catch (e) {
            console.warn('无法获取 tabInfo', e);
        }
        var currentLevel1 = null,
            currentLevel2 = null,
            level1Data = [],
            level2Data = [];
        var currentDeviceId = 0,
            currentDeviceName = '',
            currentCalculateType = 0;
        var tiledPage = 1,
            totalTiledPages = 0;
        var statTabs = null,
            resultTabs = null,
            deviceGrid = null;

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

        var TILED_CONFIG = {
            'FSDiagram': {
                containerId: 'fsTiledContainer',
                url: context + '/historyQueryController/querySurfaceCard',
                renderFunc: showSurfaceCard,
                divIdPrefix: 'DiagramTiled_FSDiagram_Id_',
                title: _loginUserLanguageResource.FSDiagram
            },
            'PSDiagram': {
                containerId: 'psTiledContainer',
                url: context + '/historyQueryController/getPSDiagramTiledData',
                renderFunc: showPSDiagram,
                divIdPrefix: 'DiagramTiled_PSDiagram_Id_',
                title: _loginUserLanguageResource.PSDiagram
            },
            'ISDiagram': {
                containerId: 'isTiledContainer',
                url: context + '/historyQueryController/getISDiagramTiledData',
                renderFunc: showASDiagram,
                divIdPrefix: 'DiagramTiled_ISDiagram_Id_',
                title: _loginUserLanguageResource.ISDiagram
            }
        };
        var tiledPage = 1;
        var diagramAspectRatio = 1;
        var defaultGraghSize = _defaultGraghSize;
        var _tiledTotalPages = {};
        var _tiledScrollHandlers = {};

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

        function onSplitterResize() {
            //alert('');
        }

        var _tiledTabsResizeObserver = null;

        function panelResizeObserver(divId, callback) {
            var container = document.getElementById(divId);
            if (!container) return;

            // 如果已有 Observer，先断开
            if (_tiledTabsResizeObserver) {
                _tiledTabsResizeObserver.disconnect();
                _tiledTabsResizeObserver = null;
            }

            if (window.ResizeObserver) {
                var observer = new ResizeObserver(function(entries) {
                    for (var entry of entries) {
                        if (entry.target === container) {
                            // 防抖：延迟执行，避免频繁触发
                            clearTimeout(container._resizeTimer);
                            container._resizeTimer = setTimeout(function() {
                                callback(divId);
                            }, 150);
                            break;
                        }
                    }
                });
                observer.observe(container);
                _tiledTabsResizeObserver = observer;
            }
        }

        function destroyTiledTabsResizeObserver() {
            if (_tiledTabsResizeObserver) {
                _tiledTabsResizeObserver.disconnect();
                _tiledTabsResizeObserver = null;
            }
        }
        $(window).on('beforeunload', function() {
            destroyTiledTabsResizeObserver();
        });

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
        }

        /**
         * 增量更新 Tabs 控件（仅增删改，不重建已有标签）
         * @param {mini.Tabs} tabsControl - mini.Tabs 实例
         * @param {Object} config - { key: true/false } 表示哪些key需要显示
         * @param {Array} order - 按顺序排列的key数组
         * @param {Function} tabFactory - 函数(key) => { name, title, body, _key, ... }
         * @param {Function} onRemoveTab - 可选，移除tab时的清理回调
         * @param {Function} onAddTab - 可选，添加tab后的回调
         */
        function updateTabs(tabsControl, config, order, tabFactory, onRemoveTab, onAddTab) {
            if (!tabsControl) return;

            // ★ 第一步：清除所有占位标签（如果存在）
            var allTabs = tabsControl.getTabs();
            for (var i = allTabs.length - 1; i >= 0; i--) {
                if (allTabs[i]._key === 'placeholder' || allTabs[i]._name === 'placeholder') {
                    if (onRemoveTab) onRemoveTab(allTabs[i]);
                    tabsControl.removeTab(allTabs[i]);
                }
            }

            var currentTabs = tabsControl.getTabs();
            var currentKeys = {};
            for (var i = 0; i < currentTabs.length; i++) {
                var tab = currentTabs[i];
                if (tab._key) currentKeys[tab._key] = tab;
            }

            var newKeys = [];
            for (var i = 0; i < order.length; i++) {
                var key = order[i];
                if (config[key]) newKeys.push(key);
            }

            // 无有效标签 → 添加占位标签
            if (newKeys.length === 0) {
                var placeholderTab = tabFactory('placeholder');
                if (!placeholderTab) placeholderTab = {
                    name: 'placeholder',
                    title: _loginUserLanguageResource.emptyMsg,
                    body: '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>',
                    _key: 'placeholder'
                };
                // 检查是否已有占位（但我们已经移除了，所以直接添加）
                tabsControl.addTab(placeholderTab);
                tabsControl.activeTab(placeholderTab);
                return;
            }

            var activeTab = tabsControl.getActiveTab();
            var activeKey = activeTab ? activeTab._key : null;

            // 移除不在新列表中的标签（但占位已被移除，此步骤正常处理其他标签）
            var toRemove = [];
            for (var i = 0; i < currentTabs.length; i++) {
                var tab = currentTabs[i];
                if (tab._key && newKeys.indexOf(tab._key) === -1) {
                    toRemove.push(tab);
                }
            }
            for (var i = 0; i < toRemove.length; i++) {
                if (onRemoveTab) onRemoveTab(toRemove[i]);
                tabsControl.removeTab(toRemove[i]);
            }

            // 重新获取剩余标签
            var remainingTabs = tabsControl.getTabs();
            var remainingMap = {};
            for (var i = 0; i < remainingTabs.length; i++) {
                var tab = remainingTabs[i];
                if (tab._key) remainingMap[tab._key] = tab;
            }

            // 插入缺失的标签，保持顺序
            for (var i = 0; i < newKeys.length; i++) {
                var key = newKeys[i];
                if (!remainingMap[key]) {
                    var newTab = tabFactory(key);
                    if (!newTab) continue;
                    var currentAll = tabsControl.getTabs();
                    var pos = currentAll.length;
                    for (var j = 0; j < currentAll.length; j++) {
                        var t = currentAll[j];
                        if (t._key) {
                            var idx = newKeys.indexOf(t._key);
                            if (idx !== -1 && idx >= i) {
                                pos = j;
                                break;
                            }
                        }
                    }
                    tabsControl.addTab(newTab, pos);
                    if (onAddTab) onAddTab(newTab);
                    remainingMap[key] = newTab;
                }
            }

            // 激活目标标签
            var finalTabs = tabsControl.getTabs();
            var targetTab = null;
            if (activeKey) {
                for (var i = 0; i < finalTabs.length; i++) {
                    if (finalTabs[i]._key === activeKey) {
                        targetTab = finalTabs[i];
                        break;
                    }
                }
            }
            if (!targetTab && finalTabs.length > 0) {
                for (var i = 0; i < finalTabs.length; i++) {
                    if (finalTabs[i]._key && finalTabs[i]._key !== 'placeholder') {
                        targetTab = finalTabs[i];
                        break;
                    }
                }
                if (!targetTab) targetTab = finalTabs[0];
            }
            if (targetTab) {
                tabsControl.activeTab(targetTab);
            }
        }

        function createStatTab(key) {
            if (key === 'placeholder') {
                return {
                    name: 'stat_placeholder',
                    title: _loginUserLanguageResource.emptyMsg,
                    body: '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>',
                    _key: 'placeholder'
                };
            }
            var cfg = STAT_TAB_CONFIG[key];
            if (!cfg) return null;
            var divId = 'pieChart_' + key + '_' + Date.now();
            return {
                name: cfg.id,
                title: cfg.title,
                _key: key,
                _api: cfg.api,
                _divId: divId,
                body: '<div style="width:100%;height:100%;min-height:' + (otherCardMinHeight || 100) + 'px;overflow:hidden;position:relative;"><div id="' + divId + '" style="width:100%;height:100%;"></div></div>'
            };
        }

        function onStatTabRemove(tab) {
            if (tab._divId) {
                var container = document.getElementById(tab._divId);
                if (container) destroyPieChart(container);
            }
        }

        function onStatTabAdd(tab) {
            // 添加后无需额外操作，激活时会触发 onStatTabChanged 加载数据
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
            loadStatCharts(deviceTypeId, orgId);
        }

        // ================================================================
        // 4. 设备列表
        // ================================================================
        function onDeviceGridBeforeLoad(e) {
            var params = e.params || {};
            var pageIndex = params.pageIndex || 0;
            var pageSize = params.pageSize || _defaultPageSize;
            params.start = pageIndex * pageSize;
            params.limit = pageSize;
            var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
            params.orgId = leftOrgId ? leftOrgId.getValue() : '';
            params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var combo = mini.get('deviceCombo');
            params.deviceName = combo ? combo.getValue() : '';
            // 统计筛选
            var getField = function(id) {
                var el = document.getElementById(id);
                return el ? el.value : '';
            };
            params.FESdiagramResultStatValue = getField('HistoryQueryStatSelectFESdiagramResult_Id');
            params.commStatusStatValue = getField('HistoryQueryStatSelectCommStatus_Id');
            params.runStatusStatValue = getField('HistoryQueryStatSelectRunStatus_Id');
            params.numStatusStatValue = getField('HistoryQueryStatSelectNumStatus_Id');
            params.deviceTypeStatValue = getField('HistoryQueryStatSelectDeviceType_Id');
        }

        function onDeviceGridLoad(e) {
            var grid = e.sender,
                result = e.result;
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
                var column = {
                    field: col.dataIndex,
                    header: col.header,
                    headerAlign: 'center',
                    align: 'center',
                    width: col.width || 100
                };
                if (col.dataIndex === 'id') {
                    column.type = 'indexcolumn';
                    column.width = 50;
                    column.header = _loginUserLanguageResource.idx;
                    delete column.field;
                } else if (col.dataIndex === 'deviceName') {
                    column.width = 140;
                    column.locked = true;
                } else if (col.dataIndex === 'commStatusName') {
                    column.width = 80;
                } else if (col.dataIndex === 'runStatusName') {
                    column.width = 80;
                } else if (col.dataIndex === 'acqTime') {
                    column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                    column.width = 150;
                }
                cols.push(column);
            }
            return cols;
        }

        // 设备表格绘制（报警徽章、颜色）
        function onDeviceGridDrawCell(e) {
            var record = e.record,
                field = e.field,
                value = e.value;
            if (!record || !field) return;
            var alarmShowStyle = getAlarmShowStyle() || {};
            var Data = alarmShowStyle.Data || {};
            var Comm = alarmShowStyle.Comm || {};
            var Run = alarmShowStyle.Run || {};
            var alarmInfo = record.alarmInfo || [];
            var fieldUpper = field.toUpperCase();

            if (fieldUpper === 'DEVICENAME') {
                var counts = {
                    100: 0,
                    200: 0,
                    300: 0
                };
                for (var i = 0; i < alarmInfo.length; i++) {
                    var level = alarmInfo[i].alarmLevel;
                    if (level === 100 || level === 200 || level === 300) counts[level] = (counts[level] || 0) + 1;
                }
                var badges = '';
                if (counts[100] > 0) badges += createAlarmBadge(counts[100], Data.FirstLevel ? Data.FirstLevel.Color : 'dc2828');
                if (counts[200] > 0) badges += createAlarmBadge(counts[200], Data.SecondLevel ? Data.SecondLevel.Color : 'f09614');
                if (counts[300] > 0) badges += createAlarmBadge(counts[300], Data.ThirdLevel ? Data.ThirdLevel.Color : 'fae600');
                e.cellHtml = '<span class="device-name-cell">' + badges + (value || '') + '</span>';
                return;
            }
            if (fieldUpper === 'COMMSTATUSNAME') {
                var status = record.commStatus;
                var color = '#999';
                if (status === 0) color = Comm.offline ? '#' + Comm.offline.Color : '#ff4d4f';
                else if (status === 1) color = Comm.online ? '#' + Comm.online.Color : '#52c41a';
                else if (status === 2) color = Comm.goOnline ? '#' + Comm.goOnline.Color : '#faad14';
                e.cellHtml = '<span style="color:' + color + ';font-weight:bold;">' + (value || '') + '</span>';
                return;
            }
            if (fieldUpper === 'RUNSTATUSNAME') {
                var commStat = record.commStatus;
                var runStat = record.runStatus;
                if (commStat == 0 || commStat == 2 || !value) {
                    e.cellHtml = '';
                    return;
                }
                var stopColor = Run.stop ? '#' + Run.stop.Color : '#ff4d4f';
                var runColor = Run.run ? '#' + Run.run.Color : '#52c41a';
                var noDataColor = Run.noData ? '#' + Run.noData.Color : '#999';
                var selColor = (runStat === 0) ? stopColor : (runStat === 1 ? runColor : noDataColor);
                e.cellHtml = '<span style="color:' + selColor + ';font-weight:bold;">' + (value || '') + '</span>';
                return;
            }
            // 其他数据列（报警高亮）
            if (fieldUpper !== 'ID' && fieldUpper !== 'DEVICENAME' && fieldUpper !== 'COMMSTATUSNAME' && fieldUpper !== 'RUNSTATUSNAME') {
                var alarmLevel = 0;
                for (var j = 0; j < alarmInfo.length; j++) {
                    if (alarmInfo[j].item && alarmInfo[j].item.toUpperCase() === fieldUpper) {
                        alarmLevel = alarmInfo[j].alarmLevel || 0;
                        break;
                    }
                }
                if (alarmLevel > 0) {
                    var style = getAlarmStyleByLevel(alarmLevel, alarmShowStyle);
                    if (style && style.bg) e.cellStyle = 'background-color:' + style.bg + ';color:' + style.color + ';';
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

            // ★★★ 关键修复：从 grid 自身获取 totalCount，而不是从 params 中取 ★★★
            var grid = mini.get('historyDataGrid');
            if (grid) {
                params.totalCount = grid.getTotalCount() || 0;
            } else {
                params.totalCount = 0;
            }
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

        function refreshDeviceList() {
            var grid = mini.get('deviceGrid');
            if (grid) grid.load();
        }

        function onDeviceComboChange() {
            refreshDeviceList();
        }

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
        function loadStatCharts(deviceTypeId, orgId) {
            clearStatFilters();
            var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
            var config = {
                FESdiagramResult: projectTabConfig.DeviceHistoryQuery ? projectTabConfig.DeviceHistoryQuery.FESDiagramStatPie : false,
                CommStatus: projectTabConfig.DeviceHistoryQuery ? projectTabConfig.DeviceHistoryQuery.CommStatusStatPie : false,
                RunStatus: projectTabConfig.DeviceHistoryQuery ? projectTabConfig.DeviceHistoryQuery.RunStatusStatPie : false,
                NumStatus: projectTabConfig.DeviceHistoryQuery ? projectTabConfig.DeviceHistoryQuery.NumStatusStatPie : false
            };
            updateStatTabs(config, deviceTypeId, orgId);
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
            if (deviceTypeId && deviceTypeId.indexOf(',') > -1) paramDeviceType = deviceTypeId;
            var order = ['FESdiagramResult', 'CommStatus', 'RunStatus', 'NumStatus'];
            var configMap = {};
            for (var i = 0; i < order.length; i++) {
                var key = order[i];
                configMap[key] = config[key] === true;
            }
            // 调用增量更新
            updateTabs(statTabs, configMap, order, createStatTab, onStatTabRemove, onStatTabAdd);
            // 加载当前激活标签的数据
            var activeTab = statTabs.getActiveTab();
            if (activeTab && activeTab._api) {
                loadStatData(activeTab, paramDeviceType, orgId);
            }
        }

        function loadStatData(tab, deviceTypeId, orgId) {
            if (!tab || !tab._api) return;
            var divId = tab._divId;
            var container = document.getElementById(divId);
            container.innerHTML = '';
            var mask = mini.mask({
                el: divId,
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });
            $.ajax({
                url: context + tab._api,
                type: 'POST',
                data: {
                    orgId: orgId,
                    deviceType: deviceTypeId || '0'
                },
                dataType: 'json',
                timeout: 10000,
                success: function(result) {
                    mini.unmask(divId);
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
                    mini.unmask(divId);
                    if (container) container.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        function extractPieData(result, tabKey, alarmShowStyle) {
            if (!result) return [{
                name: _loginUserLanguageResource.emptyMsg,
                y: 1
            }];
            var list = result.totalRoot || [];
            var data = [];
            var comm = (alarmShowStyle && alarmShowStyle.Comm) || {};
            var run = (alarmShowStyle && alarmShowStyle.Run) || {};
            var dataStyle = (alarmShowStyle && alarmShowStyle.Data) || {};
            for (var i = 0; i < list.length; i++) {
                var item = list[i];
                if (item.itemCode === 'all' || item.count <= 0) continue;
                var point = {
                    name: item.item || item.text,
                    y: item.count
                };
                if (tabKey === 'CommStatus') {
                    if (item.itemCode === 'online') point.color = '#' + (comm.online ? comm.online.Color : '52c41a');
                    else if (item.itemCode === 'goOnline') point.color = '#' + (comm.goOnline ? comm.goOnline.Color : 'faad14');
                    else if (item.itemCode === 'offline') point.color = '#' + (comm.offline ? comm.offline.Color : 'ff4d4f');
                } else if (tabKey === 'RunStatus') {
                    if (item.itemCode === 'run') point.color = '#' + (run.run ? run.run.Color : '52c41a');
                    else if (item.itemCode === 'stop') point.color = '#' + (run.stop ? run.stop.Color : 'ff4d4f');
                    else if (item.itemCode === 'noData') point.color = '#' + (run.noData ? run.noData.Color : '999');
                    else if (item.itemCode === 'goOnline') point.color = '#' + (comm.goOnline ? comm.goOnline.Color : 'faad14');
                    else if (item.itemCode === 'offline') point.color = '#' + (comm.offline ? comm.offline.Color : 'ff4d4f');
                } else if (tabKey === 'NumStatus') {
                    var level = item.level;
                    if (level === 0) point.color = '#' + (dataStyle.Normal ? dataStyle.Normal.BackgroundColor : 'FFFFFF');
                    else if (level === 100) point.color = '#' + (dataStyle.FirstLevel ? dataStyle.FirstLevel.BackgroundColor : 'DC2828');
                    else if (level === 200) point.color = '#' + (dataStyle.SecondLevel ? dataStyle.SecondLevel.BackgroundColor : 'F09614');
                    else if (level === 300) point.color = '#' + (dataStyle.ThirdLevel ? dataStyle.ThirdLevel.BackgroundColor : 'FAE600');
                    point.level = level;
                }
                data.push(point);
            }
            return data.length > 0 ? data : [{
                name: _loginUserLanguageResource.emptyMsg,
                y: 1
            }];
        }

        function renderPieChart(divId, data, title, tabKey) {
            var container = document.getElementById(divId);
            if (!container) return;
            destroyPieChart(container);
            container._pieData = data;
            container._pieTitle = title;
            container._pieTabKey = tabKey;
            if (data.length === 1 && data[0].name === _loginUserLanguageResource.emptyMsg) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
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
                    pointFormat: _loginUserLanguageResource.deviceCount + ': <b>{point.y}</b> ' + _loginUserLanguageResource.proportion + ': <b>{point.percentage:.1f}%</b>'
                },
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.y}'
                        },
                        showInLegend: true,
                        events: {
                            click: function(e) {
                                var fieldId = '';
                                if (tabKey === 'FESdiagramResult') fieldId = 'HistoryQueryStatSelectFESdiagramResult_Id';
                                else if (tabKey === 'CommStatus') fieldId = 'HistoryQueryStatSelectCommStatus_Id';
                                else if (tabKey === 'RunStatus') fieldId = 'HistoryQueryStatSelectRunStatus_Id';
                                else if (tabKey === 'NumStatus') fieldId = 'HistoryQueryStatSelectNumStatus_Id';
                                if (fieldId) {
                                    var input = document.getElementById(fieldId);
                                    if (input) {
                                        if (e.point.selected) input.value = '';
                                        else input.value = (tabKey === 'NumStatus' ? (e.point.level !== undefined ? e.point.level : '') : e.point.name);
                                    }
                                }
                                mini.get('deviceCombo').setValue('');
                                refreshDeviceList();
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
                    name: _loginUserLanguageResource.deviceCount,
                    data: data
                }]
            });
            container._chart = chart;
            if (window.ResizeObserver) {
                var observer = new ResizeObserver(function() {
                    if (container._resizeTimer) clearTimeout(container._resizeTimer);
                    container._resizeTimer = setTimeout(function() {
                        recreatePieChart(container);
                    }, 200);
                });
                observer.observe(container);
                container._resizeObserver = observer;
            }
        }

        function recreatePieChart(container) {
            if (!container || !container._pieData) return;
            var divId = container.id,
                data = container._pieData,
                title = container._pieTitle,
                tabKey = container._pieTabKey;
            if (container._chart) {
                container._chart.destroy();
                container._chart = null;
            }
            container.innerHTML = '';
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
                    pointFormat: _loginUserLanguageResource.deviceCount + ': <b>{point.y}</b> ' + _loginUserLanguageResource.proportion + ': <b>{point.percentage:.1f}%</b>'
                },
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.y}'
                        },
                        showInLegend: true,
                        events: {
                            click: function(e) {
                                var fieldId = '';
                                if (tabKey === 'FESdiagramResult') fieldId = 'HistoryQueryStatSelectFESdiagramResult_Id';
                                else if (tabKey === 'CommStatus') fieldId = 'HistoryQueryStatSelectCommStatus_Id';
                                else if (tabKey === 'RunStatus') fieldId = 'HistoryQueryStatSelectRunStatus_Id';
                                else if (tabKey === 'NumStatus') fieldId = 'HistoryQueryStatSelectNumStatus_Id';
                                if (fieldId) {
                                    var input = document.getElementById(fieldId);
                                    if (input) {
                                        if (e.point.selected) input.value = '';
                                        else input.value = (tabKey === 'NumStatus' ? (e.point.level !== undefined ? e.point.level : '') : e.point.name);
                                    }
                                }
                                mini.get('deviceCombo').setValue('');
                                refreshDeviceList();
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
                    name: _loginUserLanguageResource.deviceCount,
                    data: data
                }]
            });
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
            container._pieData = null;
            container._pieTitle = null;
            container._pieTabKey = null;
        }

        function clearStatFilters() {
            var ids = ['HistoryQueryStatSelectFESdiagramResult_Id', 'HistoryQueryStatSelectCommStatus_Id', 'HistoryQueryStatSelectRunStatus_Id', 'HistoryQueryStatSelectNumStatus_Id', 'HistoryQueryStatSelectDeviceType_Id'];
            for (var i = 0; i < ids.length; i++) {
                var el = document.getElementById(ids[i]);
                if (el) el.value = '';
            }
        }

        function onStatTabChanged(e) {
            var tab = e.tab;
            if (!tab) return;
            clearStatFilters();
            mini.get('deviceCombo').setValue('');
            var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            loadStatData(tab, deviceTypeId, orgId);
            refreshDeviceList();
        }

        // ================================================================
        // 6. 查询与结果Tab切换
        // ================================================================
        function doQuery() {
            if (!currentDeviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }

            var resultTabs = mini.get('resultTabs');
            var active = resultTabs.getActiveTab();
            if (!active) return;
            var name = active._name;
            if (name === 'trendCurve') {
                loadHistoryCurve();
                loadHistoryDataGrid();
            } else if (name === 'tiledDiagram') {
                var combo = mini.get('tiledWorkTypeCombo');
                if (combo) {
                    combo.setValue('');
                    combo.load(combo.url);
                }
            } else if (name === 'diagramOverlay') {
                var combo = mini.get('overlayWorkTypeCombo');
                if (combo) {
                    combo.setValue('');
                    combo.load(combo.url);
                }
            }
        }

        function onResultTabChanged(e) {
            // ★ 如果正在重置中，不执行查询
            if (window._isResetting) return;
            var tab = e.tab;
            var resultTabs = mini.get('resultTabs');
            if (tab && currentDeviceId) {
                var name = tab._name;
                if (!tab._initialized) {
                    tab._initialized = true;
                    var bodyEl = resultTabs.getTabBodyEl(tab);
                    mini.parse(bodyEl);
                }
                doQuery();
                if (name === 'tiledDiagram') {
                    setTimeout(function() {
                        panelResizeObserver("tiledTabs", function() {
                            resizeTiledCharts();
                        });
                    }, 100);
                }
            }
        }

        /**
         * 创建趋势曲线标签的 body HTML（包含查询工具条，时段单独一行）
         */
        function createTrendCurveBody() {
            // 第一行：日期时间 + 按钮+ 抽稀记录数
            var toolbarRow1 = '<div style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fff; border-bottom:1px solid #e8e8e8;">' +
                '<span style="font-size:12px; color:#333;">' + _loginUserLanguageResource.range + '：</span>' +
                '<input id="startDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
                '<span style="margin-left:8px; font-size:12px; color:#333;">' + _loginUserLanguageResource.timeTo + '：</span>' +
                '<input id="endDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
                '<button class="mini-button" iconCls="search" onclick="doQuery()">' + (_loginUserLanguageResource.search) + '</button>' +
                '<button class="mini-button" iconCls="export" onclick="exportData()">' + (_loginUserLanguageResource.exportData) + '</button>' +
                '<span style="flex:1;"></span>' +
                '<span id="vacuateCountLabel" style="font-size:12px; color:#999; display:none;">' + _loginUserLanguageResource.vacuateCount + '：<span id="vacuateCountSpan">0</span></span>' +
                '<input id="HistoryQueryVacuateCount_Id" type="hidden" value="" />' +
                '<span id="totalCountLabel" style="font-size:12px; color:#999; display:none;">' + _loginUserLanguageResource.totalCount + '：<span id="totalCountSpan">0</span></span>' +
                '<input id="HistoryQueryTotalCount_Id" type="hidden" value="" />' +
                '</div>';

            // 第二行：时段复选框 
            var toolbarRow2 = '<div style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fafafa; border-bottom:1px solid #e8e8e8;">' +
                '<span style="font-size:12px; color:#666;">' + _loginUserLanguageResource.timeRange + '：</span>' +
                '<input type="checkbox" id="chkAll" checked onchange="updateTimeRange(event)" /><label for="chkAll">' + _loginUserLanguageResource.all + '</label>' +
                '<input type="checkbox" id="chk1" checked name="00:00:00~06:00:00" onchange="updateTimeRange(event)" /><label for="chk1">0~6h</label>' +
                '<input type="checkbox" id="chk2" checked name="06:00:00~12:00:00" onchange="updateTimeRange(event)" /><label for="chk2">6~12h</label>' +
                '<input type="checkbox" id="chk3" checked name="12:00:00~18:00:00" onchange="updateTimeRange(event)" /><label for="chk3">12~18h</label>' +
                '<input type="checkbox" id="chk4" checked name="18:00:00~23:59:59" onchange="updateTimeRange(event)" /><label for="chk4">18~24h</label>' +
                '</div>';

            // 主体：垂直分割器（曲线 + 表格）
            var splitterHtml = '<div id="historyTrendCurvePanel" class="mini-splitter" style="width:100%; height:100%;" vertical="true">' +
                '<div size="50%" showCollapseButton="true" minSize="80" collapseDirection="top">' +
                '<div style="height:100%; display:flex; flex-direction:column;"><div id="historyCurveContainer" class="chart-container"><div class="loading-placeholder"></div></div></div>' +
                '</div>' +
                '<div size="50%" showCollapseButton="false" style="display:flex; flex-direction:column;">' +
                '<div style="flex:1; min-height:0; height:100%;">' + // 新增包裹层
                '<div id="historyDataGrid" class="mini-datagrid" style="height:100%; width:100%;" ' +
                'idField="id" pageSize="' + parseInt(_defaultPageSize, 10) + '" allowResize="true" showPager="true" ' +
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
            // 第一行：时间选择工具栏
            var toolbarRow1 = '<div style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fff; border-bottom:1px solid #e8e8e8;">' +
                '<span style="font-size:12px; color:#333;">' + _loginUserLanguageResource.range + '：</span>' +
                '<input id="tiledStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
                '<span style="margin-left:8px; font-size:12px; color:#333;">' + _loginUserLanguageResource.timeTo + '：</span>' +
                '<input id="tiledEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
                '<button class="mini-button" iconCls="search" onclick="doTiledWorkTypeComboLoad()">' + (_loginUserLanguageResource.search) + '</button>' +
                '<button class="mini-button" iconCls="export" onclick="exportData()">' + (_loginUserLanguageResource.exportData) + '</button>' +
                '<span style="flex:1;"></span>' +
                '<span id="tiledTotalCountLabel" style="font-size:12px; color:#999; display:none;">' + _loginUserLanguageResource.totalCount + '：<span id="tiledTotalCountSpan">0</span></span>' +
                '</div>';

            // 第二行：时段复选框
            var toolbarRow2 = '<div style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fafafa; border-bottom:1px solid #e8e8e8;">' +
                '<span style="font-size:12px; color:#666;">' + _loginUserLanguageResource.WellFSDiagramWorkType + '：</span>' +
                '<mini-combobox id="tiledWorkTypeCombo" style="width:180px;" popupWidth="280" ' +
                ' textField="resultName" valueField="resultCode" multiSelect="true" showClose="true" oncloseclick="onTiledWorkTypeComboCloseClick" ' +
                'url="' + context + '/historyQueryController/getDeviceResultStatusStatData" ' +
                'dataField="totalRoot" totalField="totalCount" ' +
                'onbeforeload="onTiledWorkTypeComboBeforeLoad" ' +
                'onload="onTiledWorkTypeComboLoad" ' +
                'onvaluechanged="onTiledWorkTypeComboChange">' +
                '<columns>' +
                '<column header="' + _loginUserLanguageResource.WellFSDiagramWorkType + '" field="resultName" headerAlign="left" align="left" width="60%"></column>' +
                '<column header="' + _loginUserLanguageResource.totalCount + '" field="count" headerAlign="left" align="left" width="40%"></column>' +
                '</columns>' +
                '</mini-combobox>' +
                '<span style="font-size:12px; color:#666;">' + _loginUserLanguageResource.timeRange + '：</span>' +
                '<input type="checkbox" id="tiledChkAll" checked onchange="updateTiledTimeRange(event)" /><label for="tiledChkAll">' + _loginUserLanguageResource.all + '</label>' +
                '<input type="checkbox" id="tiledChk1" checked name="00:00:00~06:00:00" onchange="updateTiledTimeRange(event)" /><label for="tiledChk1">0~6h</label>' +
                '<input type="checkbox" id="tiledChk2" checked name="06:00:00~12:00:00" onchange="updateTiledTimeRange(event)" /><label for="tiledChk2">6~12h</label>' +
                '<input type="checkbox" id="tiledChk3" checked name="12:00:00~18:00:00" onchange="updateTiledTimeRange(event)" /><label for="tiledChk3">12~18h</label>' +
                '<input type="checkbox" id="tiledChk4" checked name="18:00:00~23:59:59" onchange="updateTiledTimeRange(event)" /><label for="tiledChk4">18~24h</label>' +
                '</div>';

            // 主体：仅保留右侧图形区域，占满100%（移除 mini-splitter 及其左侧面板）
            var body = '<div id="tiledTabs" class="mini-tabs" style="width:100%; height:100%;" tabPosition="left" activeIndex="0" onactivechanged="onTiledTabActiveChanged">' +
                // 标签1：地面功图
                '<div title="' + _loginUserLanguageResource.FSDiagram + '" _type="FSDiagram">' +
                '<div id="fsTiledContainer" class="tiled-chart-container" style="width:100%; height:100%; overflow:auto; padding:2px; box-sizing:border-box;"></div>' +
                '</div>' +
                // 标签2：电功图
                '<div title="' + _loginUserLanguageResource.PSDiagram + '" _type="PSDiagram">' +
                '<div id="psTiledContainer" class="tiled-chart-container" style="width:100%; height:100%; overflow:auto; padding:2px; box-sizing:border-box;"></div>' +
                '</div>' +
                // 标签3：电流图
                '<div title="' + _loginUserLanguageResource.ISDiagram + '" _type="ISDiagram">' +
                '<div id="isTiledContainer" class="tiled-chart-container" style="width:100%; height:100%; overflow:auto; padding:2px; box-sizing:border-box;"></div>' +
                '</div>' +
                '</div>';

            return '<div style="display:flex; flex-direction:column; height:100%;">' + toolbarRow1 + toolbarRow2 + body + '</div>';
        }

        /**
         * 创建图形叠加标签的 body HTML
         */
        /**
         * 创建图形叠加标签的 body HTML
         * 包含工具条、图形区域（三个图垂直排列）和数据表格（右侧）
         */
        function createDiagramOverlayBody() {
            // 第一行：时间选择工具栏（与图形平铺一致）
            var toolbarRow1 = '<div style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fff; border-bottom:1px solid #e8e8e8;">' +
                '<span style="font-size:12px; color:#333;">' + _loginUserLanguageResource.range + '：</span>' +
                '<input id="overlayStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
                '<span style="margin-left:8px; font-size:12px; color:#333;">' + _loginUserLanguageResource.timeTo + '：</span>' +
                '<input id="overlayEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
                '<button class="mini-button" iconCls="search" onclick="overlayWorkTypeComboLoad()">' + (_loginUserLanguageResource.search) + '</button>' +
                '<button class="mini-button" iconCls="export" onclick="exportData()">' + (_loginUserLanguageResource.exportData) + '</button>' +
                '<span style="flex:1;"></span>' +
                '<span id="overlayVacuateCountLabel" style="font-size:12px; color:#999; display:none;">' + _loginUserLanguageResource.vacuateCount + '：<span id="overlayVacuateCountSpan">0</span></span>' +
                '<span id="overlayTotalCountLabel" style="font-size:12px; color:#999; display:none;">' + _loginUserLanguageResource.totalCount + '：<span id="overlayTotalCountSpan">0</span></span>' +
                '</div>';

            // 第二行：工况 ComboBox + 时段复选框
            var toolbarRow2 = '<div style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fafafa; border-bottom:1px solid #e8e8e8;">' +
                '<span style="font-size:12px; color:#666;">' + _loginUserLanguageResource.WellFSDiagramWorkType + '：</span>' +
                '<mini-combobox id="overlayWorkTypeCombo" style="width:180px;" popupWidth="280" ' +
                'textField="resultName" valueField="resultCode" multiSelect="true" showClose="true" oncloseclick="onOverlayWorkTypeComboCloseClick" ' +
                'url="' + context + '/historyQueryController/getDeviceResultStatusStatData" ' +
                'dataField="totalRoot" totalField="totalCount" ' +
                'onbeforeload="onOverlayWorkTypeComboBeforeLoad" ' +
                'onload="onOverlayWorkTypeComboLoad" ' +
                'onvaluechanged="onOverlayWorkTypeComboChange">' +
                '<columns>' +
                '<column header="' + _loginUserLanguageResource.WellFSDiagramWorkType + '" field="resultName" headerAlign="left" align="left" width="60%"></column>' +
                '<column header="' + _loginUserLanguageResource.totalCount + '" field="count" headerAlign="left" align="left" width="40%"></column>' +
                '</columns>' +
                '</mini-combobox>' +
                '<span style="font-size:12px; color:#666;">' + _loginUserLanguageResource.timeRange + '：</span>' +
                '<input type="checkbox" id="overlayChkAll" checked onchange="updateOverlayTimeRange(event)" /><label for="overlayChkAll">' + _loginUserLanguageResource.all + '</label>' +
                '<input type="checkbox" id="overlayChk1" checked name="00:00:00~06:00:00" onchange="updateOverlayTimeRange(event)" /><label for="overlayChk1">0~6h</label>' +
                '<input type="checkbox" id="overlayChk2" checked name="06:00:00~12:00:00" onchange="updateOverlayTimeRange(event)" /><label for="overlayChk2">6~12h</label>' +
                '<input type="checkbox" id="overlayChk3" checked name="12:00:00~18:00:00" onchange="updateOverlayTimeRange(event)" /><label for="overlayChk3">12~18h</label>' +
                '<input type="checkbox" id="overlayChk4" checked name="18:00:00~23:59:59" onchange="updateOverlayTimeRange(event)" /><label for="overlayChk4">18~24h</label>' +
                '</div>';

            // 主体：水平分割（左侧图形区，右侧表格）
            var body = '<div id="overlayChartPanel" class="mini-splitter" style="width:100%; flex:1;" vertical="false">' +
                // 左侧：三个图形垂直排列，每个固定高度 350px，超出滚动
                '<div size="50%" showCollapseButton="true" minSize="200" collapseDirection="left">' +
                '<div style="display:flex; flex-direction:column; width:100%; height:100%; padding:2px; box-sizing:border-box; overflow-y:auto;">' +
                // 三个图形，每个高度 350px，不拉伸
                '<div style="flex-shrink:0; height:350px; padding:2px; box-sizing:border-box;"><div id="overlayFsChart" style="width:100%;height:100%;"></div></div>' +
                '<div style="flex-shrink:0; height:350px; padding:2px; box-sizing:border-box;"><div id="overlayPowerChart" style="width:100%;height:100%;"></div></div>' +
                '<div style="flex-shrink:0; height:350px; padding:2px; box-sizing:border-box;"><div id="overlayCurrentChart" style="width:100%;height:100%;"></div></div>' +
                '</div>' +
                '</div>' +
                // 右侧：数据表格
                '<div size="45%" showCollapseButton="true" minSize="150" collapseDirection="right">' +
                '<div style="height:100%; padding:4px; box-sizing:border-box;">' +


                '<div id="overlayDataGrid" class="mini-datagrid" style="width:100%; height:100%;" ' +
                'idField="id" showPager="false" allowResize="true" multiSelect="true" showCheckColumn="true" ' +
                'url="' + context + '/historyQueryController/getFESDiagramOverlayData" ' +
                'dataField="totalRoot" totalField="totalCount" ' +
                'onload="onOverlayGridLoad" onbeforeload="onOverlayGridBeforeLoad" ondrawcell="onDeviceGridDrawCell" ' +
                'onselect="onOverlayGridSelect" ondeselect="onOverlayGridDeselect" oncheckall="onOverlayGridCheckAll">' +
                '<div property="columns"></div>' +

                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';

            return '<div style="display:flex; flex-direction:column; height:100%;">' + toolbarRow1 + toolbarRow2 + body + '</div>';
        }

        function createResultTab(key) {
            if (key === 'placeholder') {
                return {
                    title: _loginUserLanguageResource.emptyMsg,
                    _name: 'placeholder',
                    body: '<div class="loading-placeholder">' + (_loginUserLanguageResource.emptyMsg) + '</div>',
                    _key: 'placeholder',
                    _initialized: false
                };
            }
            var title = '',
                body = '',
                name = '';
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
                return null;
            }
            return {
                title: title,
                _name: name,
                body: body,
                _key: key,
                _initialized: false
            };
        }

        // 重置趋势曲线的查询参数
        function resetTrendCurveControls() {
            var startDate = mini.get('startDate');
            var endDate = mini.get('endDate');
            if (startDate) {
                startDate.setValue('');
            }
            if (endDate) {
                endDate.setValue('');
            }
            // 时段复选框保持原样（默认为全选，不强制重置）
        }

        // 重置图形平铺的查询参数
        function resetTiledDiagramControls() {
            var startDate = mini.get('tiledStartDate');
            var endDate = mini.get('tiledEndDate');
            if (startDate) {
                startDate.setValue('');
            }
            if (endDate) {
                endDate.setValue('');
            }
            // 时段复选框全选
            var chkAll = document.getElementById('tiledChkAll');
            if (chkAll) {
                chkAll.checked = true;
                var checkboxes = document.querySelectorAll('[id^="tiledChk"]');
                checkboxes.forEach(function(chk) {
                    if (chk.id !== 'tiledChkAll') chk.checked = true;
                });
            }
            // 刷新工况下拉列表（保留选中值）
            var combo = mini.get('tiledWorkTypeCombo');
            if (combo) {
                combo.setValue('');
            }
        }

        // 重置图形叠加的查询参数
        function resetDiagramOverlayControls() {
            var startDate = mini.get('overlayStartDate');
            var endDate = mini.get('overlayEndDate');
            if (startDate) {
                startDate.setValue('');
            }
            if (endDate) {
                endDate.setValue('');
            }
            // 时段复选框全选
            var chkAll = document.getElementById('overlayChkAll');
            if (chkAll) {
                chkAll.checked = true;
                var checkboxes = document.querySelectorAll('[id^="overlayChk"]');
                checkboxes.forEach(function(chk) {
                    if (chk.id !== 'overlayChkAll') chk.checked = true;
                });
            }
            // 刷新工况下拉列表（保留选中值）
            var combo = mini.get('overlayWorkTypeCombo');
            if (combo) {
                combo.setValue('');
            }
        }

        /**
         * 根据选中设备动态刷新中间标签（趋势曲线、图形平铺、图形叠加）
         * @param {Object} selected 选中的设备记录
         */
        function refreshHistoryTabs(selected) {
            if (!selected) return;
            var deviceInfo = {};
            try {
                if (window.parent && typeof window.parent.getDeviceTabInstanceInfoByDeviceId === 'function') {
                    deviceInfo = window.parent.getDeviceTabInstanceInfoByDeviceId(selected.id);
                } else {
                    deviceInfo = {
                        config: {
                            DeviceHistoryQuery: {}
                        }
                    };
                }
            } catch (e) {
                deviceInfo = {
                    config: {
                        DeviceHistoryQuery: {}
                    }
                };
            }

            var config = deviceInfo.config || {};
            var deviceHistoryQuery = config.DeviceHistoryQuery || {};
            var allowedTabKeys = [];
            if (deviceHistoryQuery.TrendCurve === true) allowedTabKeys.push('TrendCurve');
            if (deviceHistoryQuery.TiledDiagram === true) allowedTabKeys.push('TiledDiagram');
            if (deviceHistoryQuery.DiagramOverlay === true) allowedTabKeys.push('DiagramOverlay');

            var tabs = mini.get('resultTabs');
            if (!tabs) return;

            var order = ['TrendCurve', 'TiledDiagram', 'DiagramOverlay'];
            var configMap = {};
            for (var i = 0; i < allowedTabKeys.length; i++) {
                configMap[allowedTabKeys[i]] = true;
            }

            // 记录当前激活的 key
            var oldActiveTab = tabs.getActiveTab();
            var oldActiveKey = oldActiveTab ? (oldActiveTab._key || oldActiveTab._name) : null;

            // ★ 增量更新标签（不自动激活，因为 updateTabs 内部会激活）
            updateTabs(tabs, configMap, order, createResultTab, null, null);

            // ★ 重置控件的标志，防止 onResultTabChanged 重复查询
            window._isResetting = true;

            // ★ 遍历所有有效标签，重置其内部控件
            var allTabs = tabs.getTabs();
            for (var i = 0; i < allTabs.length; i++) {
                var tab = allTabs[i];
                if (tab._key === 'TrendCurve' || tab._key === 'TiledDiagram' || tab._key === 'DiagramOverlay') {
                    var bodyEl = tabs.getTabBodyEl(tab);
                    if (bodyEl) {
                        mini.parse(bodyEl); // 确保控件已解析
                        if (tab._key === 'TrendCurve') {
                            resetTrendCurveControls();
                        } else if (tab._key === 'TiledDiagram') {
                            resetTiledDiagramControls();
                        } else if (tab._key === 'DiagramOverlay') {
                            resetDiagramOverlayControls();
                        }
                    }
                }
            }

            doQuery();
            window._isResetting = false;

            // 特殊处理：如果激活的是 tiledDiagram，启动尺寸监听（只创建一次）
            var newActiveTab = tabs.getActiveTab();
            if (newActiveTab && newActiveTab._key === 'TiledDiagram') {
                setTimeout(function() {
                    panelResizeObserver("tiledTabs", function() {
                        resizeTiledCharts();
                    });
                }, 100);
            }
        }

        // ================================================================
        // 7. 趋势曲线
        // ================================================================
        function loadHistoryCurve() {
            if (!currentDeviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }
            var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            var hours = getHistoryQueryHours();
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';


            var container = document.getElementById('historyCurveContainer');
            container.innerHTML = '';
            var mask = mini.mask({
                el: 'historyCurveContainer',
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });
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
                    mini.unmask('historyCurveContainer');
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

                    var data = result.list;
                    var graphicSet = result.graphicSet;
                    var hiddenExceptionData = result.hiddenExceptionData;

                    var timeFormat = '%m-%d';
                    if (data.length > 0 && result.minAcqTime.split(' ')[0] == result.maxAcqTime.split(' ')[0]) {
                        timeFormat = '%H:%M';
                    }

                    var defaultColors = ["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];



                    var tickInterval = 1;
                    tickInterval = Math.floor(data.length / 10) + 1;
                    if (tickInterval < 100) {
                        tickInterval = 100;
                    }
                    var title = result.deviceName + (_loginUserLanguage.toUpperCase() == 'ZH_CN' ? "" : " ") + _loginUserLanguageResourceFirstLower.trendCurve;
                    var xTitle = loginUserLanguageResource.acqTime;
                    var legendName = result.curveItems;
                    var legendCode = result.curveItemCodes;
                    var curveConf = result.curveConf;

                    var color = [];
                    var color_l = [];
                    var color_r = [];
                    var color_all = [];
                    for (var i = 0; i < curveConf.length; i++) {
                        var singleColor = defaultColors[i % defaultColors.length];
                        if (curveConf[i].color != '') {
                            singleColor = '#' + curveConf[i].color;
                        }
                        color.push(singleColor);

                        if (curveConf[i].yAxisOpposite) {
                            color_r.push(singleColor);
                        } else {
                            color_l.push(singleColor);
                        }
                    }

                    var series = [];
                    var series_l = [];
                    var series_r = [];
                    var yAxis = [];
                    var yAxis_l = [];
                    var yAxis_r = [];

                    for (var i = 0; i < legendName.length; i++) {
                        var maxValue = null;
                        var minValue = null;
                        var allPositive = true; //全部是非负数
                        var allNegative = true; //全部是负值

                        var singleSeries = {};
                        legendCode
                        singleSeries.name = legendName[i];
                        singleSeries.code = legendCode[i];
                        singleSeries.type = 'spline';
                        singleSeries.lineWidth = curveConf[i].lineWidth;
                        singleSeries.dashStyle = curveConf[i].dashStyle;
                        singleSeries.marker = {
                            enabled: false
                        };
                        singleSeries.yAxis = i;
                        singleSeries.data = [];
                        for (var j = 0; j < data.length; j++) {
                            var pointData = [];
                            pointData.push(Date.parse(data[j].acqTime.replace(/-/g, '/')));
                            pointData.push(data[j].data[i]);

                            if (parseFloat(data[j].data[i]) < 0) {
                                allPositive = false;
                            } else if (parseFloat(data[j].data[i]) >= 0) {
                                allNegative = false;
                            }

                            if (hiddenExceptionData) {
                                if (isNumber(data[j].data[i])) {
                                    singleSeries.data.push(pointData);
                                }
                            } else {
                                singleSeries.data.push(pointData);
                            }
                        }
                        if (curveConf[i].yAxisOpposite) {
                            series_r.push(singleSeries);
                        } else {
                            series_l.push(singleSeries);
                        }

                        var opposite = curveConf[i].yAxisOpposite;
                        if (allNegative) {
                            maxValue = 0;
                        } else if (allPositive) {
                            minValue = 0;
                        }
                        if (JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.History)) {
                            for (var j = 0; j < graphicSet.History.length; j++) {
                                if (graphicSet.History[j].itemCode != undefined && graphicSet.History[j].itemCode.toUpperCase() == result.curveItemCodes[i].toUpperCase()) {
                                    if (isNotVal(graphicSet.History[j].yAxisMaxValue)) {
                                        maxValue = parseFloat(graphicSet.History[j].yAxisMaxValue);
                                    }
                                    if (isNotVal(graphicSet.History[j].yAxisMinValue)) {
                                        minValue = parseFloat(graphicSet.History[j].yAxisMinValue);
                                    }
                                    break;
                                }
                            }
                        }

                        var singleAxis = {
                            max: maxValue,
                            min: minValue,
                            code: legendCode[i],
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
                            tickWidth: 1, // 刻度线宽度
                            tickLength: 5, // 刻度线长度（可选）
                            opposite: opposite
                        };
                        if (curveConf[i].yAxisOpposite) {
                            yAxis_r.push(singleAxis);
                        } else {
                            yAxis_l.push(singleAxis);
                        }

                    }

                    for (var i = yAxis_l.length - 1; i >= 0; i--) {
                        yAxis.push(yAxis_l[i]);
                    }
                    for (var i = 0; i < yAxis_r.length; i++) {
                        yAxis.push(yAxis_r[i]);
                    }

                    for (var i = 0; i < series_l.length; i++) {
                        series_l[i].yAxis = series_l.length - 1 - i;
                        series.push(series_l[i]);
                    }
                    for (var i = 0; i < series_r.length; i++) {
                        series_r[i].yAxis = series_l.length + i;
                        series.push(series_r[i]);
                    }

                    for (var i = 0; i < color_l.length; i++) {
                        color_all.push(color_l[i]);
                    }
                    for (var i = 0; i < color_r.length; i++) {
                        color_all.push(color_r[i]);
                    }
                    initDeviceHistoryCurveChartFn(series, tickInterval, "historyCurveContainer", title, '', '', yAxis, color_all, true, timeFormat);
                },
                error: function() {
                    mini.unmask('historyCurveContainer');
                    container.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        function initDeviceHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color, legend, timeFormat) {
            if ($("#" + divId).length === 0) return;
            var chart = new Highcharts.Chart({
                chart: {
                    renderTo: divId,
                    type: 'spline',
                    animation: false,
                    zoomType: 'xy',
                    zooming: {
                        mouseWheel: {
                            enabled: false
                        }
                    }
                },
                time: {
                    timezoneOffset: new Date().getTimezoneOffset()
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: title,
                    style: {
                        fontSize: chartTitleFontSize || '14px'
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
                    labels: {
                        formatter: function() {
                            return this.axis.chart.time.dateFormat(timeFormat, this.value);
                        },
                        rotation: -45
                    }
                },
                yAxis: yAxis,
                tooltip: {
                    crosshairs: true,
                    shared: true,
                    style: {
                        color: '#333',
                        fontSize: '12px'
                    }
                },
                exporting: {
                    enabled: true,
                    filename: title,
                    fallbackToExportServer: false,
                    buttons: {
                        contextButton: {
                            menuItems: [
                                'viewFullscreen', 'printChart',
                                //'separator',
                                'downloadPNG', 'downloadJPEG', 'downloadSVG',
                                //'separator',
                                'downloadCSV', 'downloadXLS',
                                //'separator',
                                {
                                    text: _loginUserLanguageResource.diagramSet,
                                    onclick: function() {
                                        openCurveSetWindow();
                                    }
                                }
                            ]
                        }
                    }
                },
                plotOptions: {
                    spline: {
                        lineWidth: 1,
                        marker: {
                            enabled: true,
                            radius: 3
                        },
                        shadow: true
                    }
                },
                legend: {
                    layout: 'horizontal',
                    align: 'center',
                    verticalAlign: 'bottom',
                    enabled: legend !== false
                },
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
        function loadHistoryDataGrid() {
            var grid = mini.get('historyDataGrid');
            if (!grid) return;
            if (!currentDeviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }
            var hours = getHistoryQueryHours();
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

            var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            grid.load({
                deviceId: currentDeviceId,
                startDate: start,
                endDate: end,
                hours: hours,
                deviceType: deviceType,
                calculateType: currentCalculateType
            });
        }

        function onHistoryDataLoad(e) {
            var grid = e.sender,
                result = e.result;
            if (result && result.columns) {
                var cols = buildGridColumns(result.columns);

                // ★ 只定义列的基本结构，不设置 renderer
                var detailColumn = {
                    field: 'details',
                    header: _loginUserLanguageResource.details,
                    width: 60,
                    headerAlign: 'center',
                    align: 'center'
                };
                // 插入到索引 1
                cols.splice(1, 0, detailColumn);

                grid.setColumns(cols);
                document.getElementById('HistoryQueryDataColumnStr_Id').value = JSON.stringify(result.columns);

                var startDate = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                if (startDate == '' || null == startDate) {
                    mini.get('startDate').setValue(result.start_date);
                }
                var endDate = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                if (endDate == '' || null == endDate) {
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

            if (fieldUpper === 'ACQTIME') {
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
            showHistoryDetail(params);
        }

        function onOverlayStatSelect(e) {
            if (currentDeviceId) doQuery();
        }

        function onOverlayDataLoad(e) {
            var grid = e.sender,
                result = e.result;
            if (result && result.columns) {
                var cols = [];
                for (var i = 0; i < result.columns.length; i++) {
                    var col = result.columns[i];
                    var column = {
                        field: col.dataIndex,
                        header: col.header,
                        headerAlign: 'center',
                        align: 'center',
                        width: col.width || 100
                    };
                    if (col.dataIndex === 'id') {
                        column.type = 'indexcolumn';
                        column.width = 50;
                        column.header = _loginUserLanguageResource.idx;
                        delete column.field;
                    } else if (col.dataIndex === 'acqTime') {
                        column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                        column.width = 150;
                    }
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
            if (deviceType.indexOf(',') > -1) dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
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

            var key = 'exportHistoryDeviceList_' + deviceType + '_' + Date.now();
            var url = context + '/historyQueryController/exportHistoryQueryDeviceListExcel';

            var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) +
                '&orgId=' + orgId +
                '&deviceType=' + deviceType + '&dictDeviceType=' + dictDeviceType +
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
            exportDataMask(key, document.querySelector('.device-grid-wrapper'), _loginUserLanguageResource.loadingData);
            openExcelWindow(fullUrl);
        }

        function exportData() {
            if (!currentDeviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }
            var resultTabs = mini.get('resultTabs');
            var active = resultTabs.getActiveTab();
            var name = active ? active._name : '';
            var key = 'exportHistory_' + currentDeviceId + '_' + Date.now();
            var url = '';
            var param = '';
            if (name === 'trendCurve') {
            	var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                var hours = getHistoryQueryHours();
                var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            	
            	url = context + '/historyQueryController/exportHistoryQueryDataExcel';
                var columnStr = document.getElementById('HistoryQueryDataColumnStr_Id').value;
                param = '&orgId=' + (window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '') +
                    '&deviceType=' + deviceType + '&deviceId=' + currentDeviceId + '&deviceName=' + encodeURIComponent(encodeURIComponent(currentDeviceName)) +
                    '&calculateType=' + currentCalculateType + '&startDate=' + encodeURIComponent(start) + '&endDate=' + encodeURIComponent(end) +
                    '&hours=' + hours + '&fileName=' + encodeURIComponent(encodeURIComponent(currentDeviceName + _loginUserLanguageResource.historyData)) +
                    '&title=' + encodeURIComponent(encodeURIComponent(currentDeviceName + _loginUserLanguageResource.historyData)) +
                    '&key=' + key;
                exportDataMask(key, 'historyTrendCurvePanel', _loginUserLanguageResource.loadingData);
                openExcelWindow(url + '?flag=true' + param);
            } else if (name === 'tiledDiagram') {
            	var activeType = getCurrentTiledType();
                var config = TILED_CONFIG[activeType];
                if (!config) return;
                var combo = mini.get('tiledWorkTypeCombo');
                var resultCode = combo ? combo.getValue() : '';
                var start = mini.get('tiledStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                var end = mini.get('tiledEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                var hours = getTiledHistoryQueryHours();
                var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
                url = context + '/historyQueryController/exportHistoryQueryDiagramTiledDataExcel';
                param = '&orgId=' + (window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '') +
                    '&deviceType=' + deviceType + '&deviceId=' + currentDeviceId + '&deviceName=' + encodeURIComponent(encodeURIComponent(currentDeviceName)) +
                    '&resultCode=' + resultCode + '&startDate=' + encodeURIComponent(start) + '&endDate=' + encodeURIComponent(end) +
                    '&hours=' + hours + '&diagramType=' + activeType + '&fileName=' + encodeURIComponent(encodeURIComponent(currentDeviceName + '-' + _loginUserLanguageResource.FSDiagramData)) +
                    '&title=' + encodeURIComponent(encodeURIComponent(currentDeviceName + '-' + _loginUserLanguageResource.FSDiagramData)) +
                    '&key=' + key;
                exportDataMask(key, config.containerId, _loginUserLanguageResource.loadingData);
                openExcelWindow(url + '?flag=true' + param);
            } else if (name === 'diagramOverlay') {
            	var combo = mini.get('overlayWorkTypeCombo');
                var resultCode = combo ? combo.getValue() : '';
                var start = mini.get('overlayStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                var end = mini.get('overlayEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                var hours = getOverlayHistoryQueryHours();
                var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
                var key = 'exportOverlay_' + currentDeviceId + '_' + Date.now();
                var url = context + '/historyQueryController/exportHistoryQueryFESDiagramOverlayDataExcel';
                var fields="";
                var heads="";
                var fileName=currentDeviceName+'-'+loginUserLanguageResource.FSDiagramOverlayData;
           	 	var title=currentDeviceName+'-'+loginUserLanguageResource.FSDiagramOverlayData;
           	 	var dictDeviceType = deviceType;
             	if (deviceType && deviceType.indexOf(',') > -1) {
                 	dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
             	}
                var param = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) +
                	'&orgId=' + (window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '') +
                    '&deviceType=' + deviceType +
                    "&dictDeviceType=" + dictDeviceType + 
                    '&deviceId=' + currentDeviceId +
                    '&deviceName=' + URLencode(URLencode(currentDeviceName)) +
                    '&resultCode=' + resultCode +
                    '&calculateType=' + currentCalculateType +
                    '&startDate=' + start +
                    '&endDate=' + end +
                    '&hours=' + hours +
                    "&fileName=" + URLencode(URLencode(fileName)) + 
                    "&title=" + URLencode(URLencode(title))+ 
                    '&key=' + key;
                exportDataMask(key, 'overlayChartPanel', _loginUserLanguageResource.loadingData);
                openExcelWindow(url + '?flag=true' + param);
            } else {
                return;
            }
        }

        // ================================================================
        // 12. 时间范围辅助
        // ================================================================
        function getHistoryQueryHours() {
            var all = document.getElementById('chkAll');
            if (all && all.checked) return 'all';
            var hours = [];
            ['chk1', 'chk2', 'chk3', 'chk4'].forEach(function(id) {
                var chk = document.getElementById(id);
                if (chk && chk.checked) hours.push(chk.name);
            });
            if(hours.length==4){
            	return 'all';
            }
            return hours.length > 0 ? hours.join(',') : '';
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

        window.onTiledWorkTypeComboBeforeLoad = function(e) {
            var type = getCurrentTiledType();
            var config = TILED_CONFIG[type];
            if (!config) return;
            var containerId = config.containerId;
            var mask = mini.mask({
                el: containerId,
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });
            var params = e.params || {};
            var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
            params.orgId = leftOrgId ? leftOrgId.getValue() : '';
            params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            params.deviceId = selected ? selected.id : '';
            params.deviceName = selected ? (selected.deviceName || selected.wellName || '') : '';

            var startDate = mini.get('tiledStartDate');
            var endDate = mini.get('tiledEndDate');
            if (startDate && endDate) {
                params.startDate = startDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '';
                params.endDate = endDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '';
            } else {
                params.startDate = '';
                params.endDate = '';
            }
            params.hours = getTiledHistoryQueryHours();
        };

        window.onOverlayWorkTypeComboBeforeLoad = function(e) {
            var mask = mini.mask({
                el: 'overlayChartPanel',
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });
            var params = e.params || {};
            var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
            params.orgId = leftOrgId ? leftOrgId.getValue() : '';
            params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';

            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            params.deviceId = selected ? selected.id : '';
            params.deviceName = selected ? (selected.deviceName || selected.wellName || '') : '';

            var startDate = mini.get('overlayStartDate');
            var endDate = mini.get('overlayEndDate');
            if (startDate && endDate) {
                params.startDate = startDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '';
                params.endDate = endDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '';
            } else {
                params.startDate = '';
                params.endDate = '';
            }
            params.hours = getOverlayHistoryQueryHours();
        };

        window.onTiledWorkTypeComboLoad = function(e) {
            var type = getCurrentTiledType();
            var config = TILED_CONFIG[type];
            if (!config) return;
            var containerId = config.containerId;
            mini.unmask(containerId);
            var combo = e.sender;
            var data = combo.getData();
            var selectedCodes = [];
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                if (item.resultCode != 1232) {
                    selectedCodes.push(item.resultCode);
                }
            }
            if (selectedCodes.length > 0) {
                combo.setValue(selectedCodes.join(','));
            } else if (data.length > 0) {
                combo.setValue('');
            }

            // 如果当前激活的是 tiledDiagram，自动加载图形
            var tabs = mini.get('resultTabs');
            if (tabs) {
                var activeTab = tabs.getActiveTab();
                if (activeTab && activeTab._name === 'tiledDiagram') {
                    doTiledQuery();
                }
            }
        };

        window.onOverlayWorkTypeComboLoad = function(e) {
            mini.unmask('overlayChartPanel');

            var combo = e.sender;
            var data = combo.getData();
            var result = e.result;

            var startDate = mini.get('overlayStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            if (startDate == '' || null == startDate) {
                mini.get('overlayStartDate').setValue(result.start_date);
            }
            var endDate = mini.get('overlayEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            if (endDate == '' || null == endDate) {
                mini.get('overlayEndDate').setValue(result.end_date);
            }


            var selectedCodes = [];
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                if (item.resultCode != 1232) {
                    selectedCodes.push(item.resultCode);
                }
            }
            if (selectedCodes.length > 0) {
                combo.setValue(selectedCodes.join(','));
            } else {
                combo.setValue('');
            }
            // 加载完成后自动执行查询
            doOverlayQuery();
        };

        window.onTiledWorkTypeComboChange = function(e) {
            var tabs = mini.get('resultTabs');
            if (tabs) {
                var activeTab = tabs.getActiveTab();
                if (activeTab && activeTab._name === 'tiledDiagram') {
                    doTiledQuery();
                }
            }
        };
        window.onOverlayWorkTypeComboChange = function(e) {
            doOverlayQuery();
        };

        function onTiledWorkTypeComboCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");

            var tabs = mini.get('resultTabs');
            if (tabs) {
                var activeTab = tabs.getActiveTab();
                if (activeTab && activeTab._name === 'tiledDiagram') {
                    doTiledQuery();
                }
            }
        }

        window.onOverlayWorkTypeComboCloseClick = function(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
            doOverlayQuery();
        };

        function getCurrentTiledType() {
            var tabs = mini.get('tiledTabs');
            if (!tabs) return 'FSDiagram';
            var activeTab = tabs.getActiveTab();
            if (activeTab) {
                for (var key in TILED_CONFIG) {
                    if (TILED_CONFIG[key].title === activeTab.title) {
                        return key;
                    }
                }
            }
            return 'FSDiagram';
        }

        function onTiledTabActiveChanged(e) {
            var tab = e.tab;
            if (!tab) return;
            doTiledQuery();
        }

        function loadTiledDiagram(type, page) {
            if (!currentDeviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }
            page = page || 1;
            tiledPage = page;
            var config = TILED_CONFIG[type];
            if (!config) return;

            var containerId = config.containerId;
            var container = document.getElementById(containerId);
            if (!container) return;

            var combo = mini.get('tiledWorkTypeCombo');
            var resultCode = '';
            if (combo) {
                var values = combo.getValue();
                if (values) {
                    resultCode = values; // 已经是逗号分隔的字符串
                }
            }

            var start = mini.get('tiledStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            var end = mini.get('tiledEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');

            var hours = getTiledHistoryQueryHours();
            var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var limit = defaultGraghSize || 20;
            var startIdx = (page - 1) * limit;

            var mask = mini.mask({
                el: containerId,
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });

            $.ajax({
                url: config.url,
                type: 'POST',
                data: {
                    deviceId: currentDeviceId,
                    deviceName: currentDeviceName,
                    startDate: start,
                    endDate: end,
                    hours: hours,
                    resultCode: resultCode,
                    deviceType: deviceType,
                    calculateType: currentCalculateType,
                    start: startIdx,
                    limit: limit,
                    page: page
                },
                dataType: 'json',
                timeout: 30000,
                success: function(result) {
                    mini.unmask(containerId);

                    var startDate = mini.get('tiledStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                    if (startDate == '' || null == startDate) {
                        mini.get('tiledStartDate').setValue(result.start_date);
                    }
                    var endDate = mini.get('tiledEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
                    if (endDate == '' || null == endDate) {
                        mini.get('tiledEndDate').setValue(result.end_date);
                    }

                    if (page === 1) {
                        container.innerHTML = '';
                    }
                    var list = result.list || [];
                    var totalPages = result.totalPages || 0;
                    var totalShow = result.totalShow || 0;
                    _tiledTotalPages[type] = totalPages;

                    // 更新总记录数
                    var totalLabel = document.getElementById('tiledTotalCountLabel');
                    var totalSpan = document.getElementById('tiledTotalCountSpan');
                    if (totalLabel && totalSpan) {
                        totalLabel.style.display = 'inline';
                        totalSpan.textContent = totalShow;
                    }

                    if (list.length === 0 && page === 1) {
                        container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                        return;
                    }

                    // 计算图表尺寸
                    var containerRect = container.getBoundingClientRect();
                    var containerWidth = containerRect.width;
                    var paddingTotal = 4; // 左右各2px padding


                    var scrollWidth = getScrollWidth();

                    // ★ 可用宽度 = 容器宽度 - padding - 滚动条宽度
                    var availableWidth = containerWidth - scrollWidth;

                    if (availableWidth <= 0) {
                        // 如果可用宽度太小，使用最小宽度
                        availableWidth = 200;
                    }

                    var columnCount = Math.max(1, Math.floor(availableWidth / graghMinWidth));
                    var chartWidth = Math.floor((availableWidth - (columnCount - 1) * 4) / columnCount);
                    var chartHeight = Math.floor(chartWidth * diagramAspectRatio);
                    chartHeight = Math.max(chartHeight, dynamometerCardMinHeight);
                    chartWidth = Math.max(chartWidth, 200);

                    chartWidth = chartWidth + 'px';
                    chartHeight = chartHeight + 'px';

                    var fragment = document.createDocumentFragment();
                    var renderQueue = [];
                    for (var i = 0; i < list.length; i++) {
                        var diagram = list[i];
                        var divId = config.divIdPrefix + diagram.id;
                        var div = document.createElement('div');
                        div.className = 'chart-item';
                        div.id = divId;
                        div.style.cssText = 'width:' + chartWidth + ';height:' + chartHeight + ';min-height:' + dynamometerCardMinHeight + 'px;float:left;box-sizing:border-box;';
                        fragment.appendChild(div);
                        renderQueue.push({
                            diagram: diagram,
                            divId: divId,
                            renderFunc: config.renderFunc
                        });
                    }
                    container.appendChild(fragment);

                    renderChartsSequentially(renderQueue, function() {
                        if (page === 1 && totalPages > 1) {
                            initTiledDiagramScroll(containerId, type);
                        }
                    });
                },
                error: function(xhr, status, errorThrown) {
                    mini.unmask(container);
                    container.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
                    console.error('加载图形数据失败:', status, errorThrown);
                }
            });
        }

        function renderChartsSequentially(queue, callback) {
            if (queue.length === 0) {
                if (callback) callback();
                return;
            }
            var item = queue.shift();
            try {
                item.renderFunc(item.diagram, item.divId);
            } catch (e) {
                console.warn('渲染图表失败:', e, item.diagram);
            }
            setTimeout(function() {
                renderChartsSequentially(queue, callback);
            }, 10);
        }

        function initTiledDiagramScroll(containerId, type) {
            var container = document.getElementById(containerId);
            if (!container) return;
            if (_tiledScrollHandlers[type]) {
                container.removeEventListener('scroll', _tiledScrollHandlers[type]);
            }
            var handler = function() {
                var totalPages = _tiledTotalPages[type] || 0;
                if (tiledPage >= totalPages) return;
                var scrollTop = container.scrollTop;
                var scrollHeight = container.scrollHeight;
                var clientHeight = container.clientHeight;
                if (scrollTop + clientHeight >= scrollHeight - 50) {
                    tiledPage++;
                    loadTiledDiagram(type, tiledPage);
                }
            };
            container.addEventListener('scroll', handler);
            _tiledScrollHandlers[type] = handler;
        }

        function resizeTiledCharts() {
            // ★ 先判断当前激活的 resultTabs 标签是否为 tiledDiagram
            var resultTabs = mini.get('resultTabs');
            if (!resultTabs) return;
            var activeTab = resultTabs.getActiveTab();
            if (!activeTab || activeTab._name !== 'tiledDiagram') {
                // 当前激活的不是图形平铺标签，不处理
                return;
            }
            // 只刷新当前激活的图形类型
            var activeType = getCurrentTiledType();
            var config = TILED_CONFIG[activeType];
            if (!config) return;

            var container = document.getElementById(config.containerId);
            if (!container) return;
            var children = container.querySelectorAll('.chart-item');
            if (children.length === 0) return;

            var containerRect = container.getBoundingClientRect();
            var containerWidth = containerRect.width;
            var scrollWidth = getScrollWidth();
            var availableWidth = containerWidth - scrollWidth;
            if (availableWidth <= 0) {
                availableWidth = 200;
            }

            var columnCount = Math.max(1, Math.floor(availableWidth / graghMinWidth));
            var chartWidth = Math.floor((availableWidth - (columnCount - 1) * 4) / columnCount);
            var chartHeight = Math.floor(chartWidth * diagramAspectRatio);
            chartHeight = Math.max(chartHeight, dynamometerCardMinHeight);
            chartWidth = Math.max(chartWidth, 200);

            for (var i = 0; i < children.length; i++) {
                var child = children[i];
                child.style.width = chartWidth + 'px';
                child.style.height = chartHeight + 'px';
                highchartsResize(child.id);
            }
        }

        function doTiledWorkTypeComboLoad() {
            var combo = mini.get('tiledWorkTypeCombo');
            if (combo) {
                combo.setValue('');
                combo.load(combo.url);
            }
        }

        function overlayWorkTypeComboLoad() {
            var combo = mini.get('overlayWorkTypeCombo');
            if (combo) {
                combo.setValue('');
                combo.load(combo.url);
            }
        }

        function doTiledQuery() {
            var activeType = getCurrentTiledType();
            tiledPage = 1;
            loadTiledDiagram(activeType, 1);
        }

        function doOverlayQuery() {
            if (!currentDeviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }
            var grid = mini.get('overlayDataGrid');
            if (grid) {
                grid.load(); // 触发 onbeforeload 和 onload
            }
        }

        function onOverlayGridBeforeLoad(e) {
        	_overlayGridReault={};
            var params = e.params || {};
            var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
            params.orgId = leftOrgId ? leftOrgId.getValue() : '';
            params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var dictDeviceType = params.deviceType;
            if (params.deviceType.indexOf(',') > -1) {
                dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : params.deviceType;
            }
            params.dictDeviceType = dictDeviceType;

            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            params.deviceId = selected ? selected.id : '';
            params.deviceName = selected ? (selected.deviceName || selected.wellName || '') : '';

            var combo = mini.get('overlayWorkTypeCombo');
            params.resultCode = combo ? combo.getValue() : '';

            var startDate = mini.get('overlayStartDate');
            var endDate = mini.get('overlayEndDate');
            if (startDate && endDate) {
                params.startDate = startDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '';
                params.endDate = endDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '';
            } else {
                params.startDate = '';
                params.endDate = '';
            }
            params.hours = getOverlayHistoryQueryHours();
            params.calculateType = currentCalculateType || 0;
            e.params = params;
        }
        
		var _overlayGridReault={};
        function onOverlayGridLoad(e) {
            var grid = e.sender;
            var result = e.result;
            if (result.start_date) {
                result.start_date = formatDate(result.start_date, 'yyyy-MM-dd HH:mm:ss');
            }
            if (result.end_date) {
                result.end_date = formatDate(result.end_date, 'yyyy-MM-dd HH:mm:ss');
            }
            _overlayGridReault=result;
            
            
            if (!result) return;
            // 更新记录数标签
            if (result.totalCount !== undefined) {
                document.getElementById('overlayTotalCountLabel').style.display = 'inline';
                document.getElementById('overlayTotalCountSpan').textContent = result.totalCount;
            }
            if (result.vacuateCount !== undefined) {
                document.getElementById('overlayVacuateCountLabel').style.display = 'inline';
                document.getElementById('overlayVacuateCountSpan').textContent = result.vacuateCount;
            }

            // 动态设置列（从 result.columns 生成）
            if (result.columns && result.columns.length > 0) {
                var columns = [];
                // ★ 插入复选框列（必须放在第一位）
                columns.push({
                    type: "checkcolumn",
                    width: 40,
                    header: "",
                    headerAlign: "center",
                    align: "center"
                });
                for (var i = 0; i < result.columns.length; i++) {
                    var col = result.columns[i];
                    var column = {
                        field: col.dataIndex,
                        header: col.header,
                        headerAlign: 'center',
                        align: 'center',
                        width: col.width || 100
                    };
                    if (col.dataIndex === 'id') {
                        column.type = 'indexcolumn';
                        column.width = 50;
                        column.header = _loginUserLanguageResource.idx;
                        delete column.field;
                    } else if (col.dataIndex === 'acqTime') {
                        column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                        column.width = 150;
                    }
                    columns.push(column);
                }
                grid.setColumns(columns);
            }

            // 全选所有行（默认显示所有图形）
           	grid.selectAll();

            showFSDiagramOverlayChart(_overlayGridReault, 'overlayFsChart', true, 0);
            showFSDiagramOverlayChart(_overlayGridReault, 'overlayPowerChart', true, 1);
            showFSDiagramOverlayChart(_overlayGridReault, 'overlayCurrentChart', true, 2);
        }

        // 标志位，防止循环触发

        var _overlayGridSelecting = false;
        var _overlayPendingTimer = null; // 用于合并延迟操作
        var _overlaySkipPending = false;

        function onOverlayGridSelect(e) {
            // 如果正在全选操作，忽略（通过标志）
            if (_overlaySkipPending) return;
            // 延迟执行，以便合并连续操作
            clearTimeout(_overlayPendingTimer);
            _overlayPendingTimer = setTimeout(function() {
                if (_overlaySkipPending) return;
                var grid = e.sender;
                // 检查当前是否全选（若全选则跳过）
                if (grid.getSelecteds().length === grid.getData().length) return;
                setOverlaySeriesVisibility(grid.indexOf(e.record), true);
            }, 50);
        }

        function onOverlayGridDeselect(e) {
            if (_overlaySkipPending) return;
            clearTimeout(_overlayPendingTimer);
            _overlayPendingTimer = setTimeout(function() {
                if (_overlaySkipPending) return;
                var grid = e.sender;
                if (grid.getSelecteds().length === 0) return; // 全不选跳过
                setOverlaySeriesVisibility(grid.indexOf(e.record), false);
            }, 50);
        }

        function onOverlayGridCheckAll(e) {
            // 设置跳过标志，并清除待执行的定时任务
            _overlaySkipPending = true;
            clearTimeout(_overlayPendingTimer);

            try {
                var grid = e.sender;
                var totalData = grid.getData();
                var isChecked = (grid.getSelecteds().length === totalData.length);

                var result = {
                    totalRoot: totalData,
                    deviceName: totalData.length > 0 ? totalData[0].deviceName : '',
                    start_date: totalData.length > 0 ? totalData[totalData.length - 1].acqTime : '',
                    end_date: totalData.length > 0 ? totalData[0].acqTime : ''
                };

                showFSDiagramOverlayChart(_overlayGridReault, "overlayFsChart", isChecked, 0);
                showFSDiagramOverlayChart(_overlayGridReault, "overlayPowerChart", isChecked, 1);
                showFSDiagramOverlayChart(_overlayGridReault, "overlayCurrentChart", isChecked, 2);
            } finally {
                // 重置标志（延迟一点，确保后续的 select 事件也被忽略）
                setTimeout(function() {
                    _overlaySkipPending = false;
                }, 100);
            }
        }

        function setOverlaySeriesVisibility(index, visible) {
            var chartIds = ['overlayFsChart', 'overlayPowerChart', 'overlayCurrentChart'];
            chartIds.forEach(function(chartId) {
                var container = document.getElementById(chartId);
                if (!container) return;
                var chart = $(container).highcharts();
                if (chart && chart.series && chart.series.length > index) {
                    var series = chart.series[index];
                    if (visible) {
                        series.show();
                    } else {
                        series.hide();
                    }
                }
            });
        }



        function showOverlayEmptyState() {
            ['overlayFsChart', 'overlayPowerChart', 'overlayCurrentChart'].forEach(function(id) {
                var el = document.getElementById(id);
                if (el) el.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
            });
        }

        function updateTiledTimeRange(e) {
            var target = e ? e.target : null;
            var all = document.getElementById('tiledChkAll');
            if (!all) return;

            if (target && target.id === 'tiledChkAll') {
                // 点击的是“全部”复选框
                if (all.checked) {
                    // 全选：将所有子复选框设为选中
                    var checkboxes = document.querySelectorAll('[id^="tiledChk"]:not(#tiledChkAll)');
                    checkboxes.forEach(function(chk) {
                        chk.checked = true;
                    });
                } else {
                    // 取消全选：不修改任何子复选框的状态（保持原样）
                    // 什么也不做
                }
            } else {
                // 点击的是子复选框：更新“全部”的选中状态
                var subs = document.querySelectorAll('[id^="tiledChk"]:not(#tiledChkAll)');
                var allChecked = true;
                subs.forEach(function(chk) {
                    if (!chk.checked) allChecked = false;
                });
                all.checked = allChecked;
            }

            // 触发查询（如果当前有设备选中）
            if (currentDeviceId) {
                doQuery();
            }
        }

        function updateOverlayTimeRange(e) {
            var target = e ? e.target : null;
            var all = document.getElementById('overlayChkAll');
            if (!all) return;

            if (target && target.id === 'overlayChkAll') {
                // 点击的是“全部”复选框
                if (all.checked) {
                    // 全选：将所有子复选框设为选中
                    var checkboxes = document.querySelectorAll('[id^="overlayChk"]:not(#overlayChkAll)');
                    checkboxes.forEach(function(chk) {
                        chk.checked = true;
                    });
                } else {
                    // 取消全选：不修改任何子复选框的状态（保持原样）
                    // 什么也不做
                }
            } else {
                // 点击的是子复选框：更新“全部”的选中状态
                var subs = document.querySelectorAll('[id^="overlayChk"]:not(#overlayChkAll)');
                var allChecked = true;
                subs.forEach(function(chk) {
                    if (!chk.checked) allChecked = false;
                });
                all.checked = allChecked;
            }

            // 触发查询（如果当前有设备选中）
            if (currentDeviceId) {
                doOverlayQuery();
            }
        }

        function getTiledHistoryQueryHours() {
            var all = document.getElementById('tiledChkAll');
            if (all && all.checked) return 'all';
            var hours = [];
            ['tiledChk1', 'tiledChk2', 'tiledChk3', 'tiledChk4'].forEach(function(id) {
                var chk = document.getElementById(id);
                if (chk && chk.checked) hours.push(chk.name);
            });
            if(hours.length==4){
            	return 'all';
            }
            return hours.length > 0 ? hours.join(',') : '';
        }

        function getOverlayHistoryQueryHours() {
            var all = document.getElementById('overlayChkAll');
            if (all && all.checked) return 'all';
            var hours = [];
            ['overlayChk1', 'overlayChk2', 'overlayChk3', 'overlayChk4'].forEach(function(id) {
                var chk = document.getElementById(id);
                if (chk && chk.checked) hours.push(chk.name);
            });
            if(hours.length==4){
            	return 'all';
            }
            return hours.length > 0 ? hours.join(',') : '';
        }

        window.onDeviceComboBeforeLoad = function(e) {
            var params = e.params || {};

            var pageIndex = params.pageIndex || 0;
            var pageSize = params.pageSize || defaultWellComboxSize;
            params.start = pageIndex * pageSize;
            params.limit = pageSize;

            var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
            params.orgId = leftOrgId ? leftOrgId.getValue() : '';
            params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
            var combo = mini.get('deviceCombo');
            params.deviceName = combo ? combo.getValue() : '';
        }


        window.onDeviceComboShowPopup = function(e) {
            var combo = e.sender;
            // 如果当前没有数据或数据为空，加载
            var data = combo.getData();
            if (!data || data.length <= 1) {
                // 先隐藏下拉，防止显示空
                combo.hidePopup();
            }
            combo.load(combo.url);
        };

        window.onDeviceComboLoad = function(e) {
            var combo = e.sender;

        };

        function alertInfo(container) {
            alert(container);
        }

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
                    _name: 'placeholder',
                    _key: 'placeholder'
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
