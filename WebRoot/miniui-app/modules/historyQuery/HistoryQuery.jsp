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
        html, body {
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
        .level1-footer .tab-item:hover { color: #333; }
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
        .level2-sidebar .tab-item:hover { background: #e8ecf0; color: #333; }
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
            box-shadow: 0 1px 4px rgba(0,0,0,0.06);
            margin-bottom: 4px;
        }
        .stat-pie-wrapper {
            flex: 0 0 40%;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.06);
        }
        .right-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #fff;
            padding: 4px;
            margin-left: 0;
        }
        .query-toolbar {
            flex-shrink: 0;
            background: #f5f7fa;
            padding: 6px 12px;
            border-bottom: 1px solid #e8e8e8;
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 6px;
        }
        .query-toolbar .mini-label {
            font-size: 12px;
            color: #333;
        }
        .result-tabs {
            flex: 1;
            overflow: hidden;
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
            min-height: 300px;
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
        @media (max-width: 768px) {
            .level2-sidebar {
                width: 100%;
                height: 32px;
                flex-direction: row;
                padding: 0 8px;
                border-right: none;
                border-bottom: 1px solid #e8e8e8;
                overflow-x: auto;
                overflow-y: hidden;
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
            .level2-sidebar .no-child-tip {
                writing-mode: horizontal-tb;
                padding: 4px 8px;
            }
            .mini-splitter {
                flex-direction: column !important;
            }
            .left-panel {
                flex: 0 0 40% !important;
            }
            .right-panel {
                flex: 0 0 60% !important;
            }
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
                <div class="mini-splitter" style="width:100%; height:100%;" vertical="false">
                    <!-- 左侧：设备列表 + 统计饼图 -->
                    <div size="35%" showCollapseButton="true" minSize="200">
                        <div class="left-panel">
                            <!-- 设备列表 -->
                            <div class="device-grid-wrapper">
                                <div id="deviceGrid" class="mini-datagrid" style="width:100%; height:100%;"
                                     idField="id" pageSize="20" allowResize="true" allowAlternating="true"
                                     url="<%=path%>/historyController/getDeviceList" dataField="totalRoot" totalField="totalCount"
                                     onselectionchanged="onDeviceSelect">
                                    <div property="columns">
                                        <div type="indexcolumn" width="40" headerAlign="center">序号</div>
                                        <div field="deviceName" width="100%" headerAlign="center">设备名称</div>
                                    </div>
                                </div>
                            </div>
                            <!-- 统计饼图 -->
                            <div class="stat-pie-wrapper">
                                <div id="statTabs" class="mini-tabs" style="width:100%; height:100%;" activeIndex="0">
                                    <div title="工况统计" name="workType">
                                        <div id="pieChart_workType" class="chart-container"></div>
                                    </div>
                                    <div title="通信状态" name="commStatus">
                                        <div id="pieChart_commStatus" class="chart-container"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 右侧：查询工具栏 + 结果Tab -->
                    <div size="65%" showCollapseButton="true" minSize="300">
                        <div class="right-panel">
                            <!-- 查询工具栏 -->
                            <div class="query-toolbar">
                                <span class="mini-label">开始时间：</span>
                                <input id="startDate" class="mini-datepicker" style="width:140px;" format="yyyy-MM-dd HH:mm:ss" showTime="true" />
                                <span class="mini-label">结束时间：</span>
                                <input id="endDate" class="mini-datepicker" style="width:140px;" format="yyyy-MM-dd HH:mm:ss" showTime="true" />
                                <button class="mini-button" iconCls="search" onclick="doQuery()">查询</button>
                                <button class="mini-button" iconCls="export" onclick="exportData()">导出</button>
                            </div>
                            <!-- 结果Tab -->
                            <div class="result-tabs">
                                <div id="resultTabs" class="mini-tabs" activeIndex="0">
                                    <div title="历史数据" iconCls="datagrid">
                                        <div id="dataGrid" class="mini-datagrid table-container"
                                             idField="id" pageSize="25" allowResize="true" showPager="true"
                                             url="<%=path%>/historyController/getHistoryData">
                                            <div property="columns">
                                                <div type="indexcolumn" width="40">序号</div>
                                                <div field="acqTime" width="160" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss">采集时间</div>
                                                <div field="load" width="100" headerAlign="center">载荷(kN)</div>
                                                <div field="stroke" width="100" headerAlign="center">冲程(m)</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div title="图形平铺" iconCls="chart">
                                        <div id="tileContainer" class="chart-container">
                                            <div class="loading-placeholder">请选择设备并查询</div>
                                        </div>
                                    </div>
                                    <div title="图形叠加" iconCls="chart">
                                        <div id="overlayContainer" class="chart-container">
                                            <div class="loading-placeholder">请选择设备并查询</div>
                                        </div>
                                    </div>
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
        // 1. 全局变量和工具（与实时监控一致）
        // ================================================================
        var context = '<%=path%>';
        var tabInfo = null;
        try {
            if (window.parent && window.parent.tabInfo) {
                tabInfo = window.parent.tabInfo;
            }
        } catch(e) { console.warn('无法获取 tabInfo', e); }

        var currentLevel1 = null;
        var currentLevel2 = null;
        var level1Data = [];
        var level2Data = [];
        var currentDeviceId = null;

        // ================================================================
        // 2. 标签构建
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
                div.onclick = function() { selectLevel2(parseInt(this.dataset.index)); };
                container.appendChild(div);
            }
            if (allTabs.length > 0) {
                currentLevel2 = allTabs[0];
                // 加载设备列表（传入设备类型ID）
                loadDeviceGrid(currentLevel2.deviceTypeId);
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
            loadDeviceGrid(currentLevel2.deviceTypeId);
        }

        // ================================================================
        // 3. 设备列表加载
        // ================================================================
        function loadDeviceGrid(deviceTypeId) {
            var grid = mini.get('deviceGrid');
            if (!grid) return;
            // 设置参数并加载
            grid.load({ deviceType: deviceTypeId });
        }

        // ================================================================
        // 4. 设备选择事件
        // ================================================================
        function onDeviceSelect(e) {
            var selected = e.selected;
            if (selected) {
                currentDeviceId = selected.id;
                // 自动加载统计饼图和默认查询（或等待用户点击查询）
                loadStatCharts(currentDeviceId);
                // 可自动查询或等待点击
            }
        }

        // ================================================================
        // 5. 统计饼图
        // ================================================================
        function loadStatCharts(deviceId) {
            // 示例：加载工况统计
            $.ajax({
                url: context + '/historyController/getWorkTypeStat',
                data: { deviceId: deviceId },
                dataType: 'json',
                success: function(result) {
                    renderPieChart('pieChart_workType', result.data, '工况统计');
                }
            });
            // 通信状态统计
            $.ajax({
                url: context + '/historyController/getCommStat',
                data: { deviceId: deviceId },
                dataType: 'json',
                success: function(result) {
                    renderPieChart('pieChart_commStatus', result.data, '通信状态');
                }
            });
        }

        function renderPieChart(divId, data, title) {
            var container = document.getElementById(divId);
            if (!container) return;
            if (container._chart) container._chart.destroy();
            if (!data || data.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">无数据</div>';
                return;
            }
            var chart = Highcharts.chart(divId, {
                chart: { type: 'pie', plotBackgroundColor: null, plotBorderWidth: null, plotShadow: false },
                credits: { enabled: false },
                title: { text: title, style: { fontSize: '13px' } },
                tooltip: { pointFormat: '{point.y} 个 ({point.percentage:.1f}%)' },
                legend: { align: 'center', verticalAlign: 'bottom', layout: 'horizontal' },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: { enabled: true, format: '<b>{point.name}</b>: {point.y}' },
                        showInLegend: true
                    }
                },
                series: [{ type: 'pie', name: '数量', data: data }]
            });
            container._chart = chart;
        }

        // ================================================================
        // 6. 查询
        // ================================================================
        function doQuery() {
            if (!currentDeviceId) {
                mini.alert('请先选择设备');
                return;
            }
            var start = mini.get('startDate').getValue();
            var end = mini.get('endDate').getValue();
            if (!start || !end) {
                mini.alert('请选择时间范围');
                return;
            }
            // 刷新数据表格
            var grid = mini.get('dataGrid');
            grid.load({ deviceId: currentDeviceId, startTime: start, endTime: end });
            // 刷新图形平铺和叠加
            loadTileCharts(currentDeviceId, start, end);
            loadOverlayCharts(currentDeviceId, start, end);
        }

        function loadTileCharts(deviceId, start, end) {
            var container = document.getElementById('tileContainer');
            container.innerHTML = '<div class="loading-placeholder">加载中...</div>';
            // 示例：获取多个图表数据并平铺显示
            // 这里根据实际情况调用接口，返回多个图表配置
            $.ajax({
                url: context + '/historyController/getTileChartsData',
                data: { deviceId: deviceId, start: start, end: end },
                dataType: 'json',
                success: function(result) {
                    // 假设返回 result.charts 数组，每个元素包含 title, data
                    container.innerHTML = '';
                    // 创建平铺容器，flex-wrap
                    var grid = document.createElement('div');
                    grid.style.cssText = 'display:flex; flex-wrap:wrap; width:100%; height:100%;';
                    container.appendChild(grid);
                    if (!result.charts || result.charts.length === 0) {
                        grid.innerHTML = '<div class="loading-placeholder">无数据</div>';
                        return;
                    }
                    result.charts.forEach(function(chartInfo, idx) {
                        var item = document.createElement('div');
                        item.style.cssText = 'flex:0 0 50%; height:50%; box-sizing:border-box; padding:2px;';
                        var chartDiv = document.createElement('div');
                        chartDiv.id = 'tileChart_' + idx;
                        chartDiv.style.cssText = 'width:100%; height:100%;';
                        item.appendChild(chartDiv);
                        grid.appendChild(item);
                        // 绘制图表
                        var series = [{
                            name: chartInfo.seriesName,
                            data: chartInfo.data // 数组 [timestamp, value]
                        }];
                        initDeviceRealtimeMonitoringStockChartFn(series, null, 'tileChart_' + idx, chartInfo.title, '', '时间', chartInfo.yTitle, ['#7cb5ec'], false, true, false);
                    });
                },
                error: function() {
                    container.innerHTML = '<div class="loading-placeholder" style="color:red;">加载失败</div>';
                }
            });
        }

        function loadOverlayCharts(deviceId, start, end) {
            var container = document.getElementById('overlayContainer');
            container.innerHTML = '<div class="loading-placeholder">加载中...</div>';
            // 叠加图表：多个曲线在一个图表中
            $.ajax({
                url: context + '/historyController/getOverlayChartsData',
                data: { deviceId: deviceId, start: start, end: end },
                dataType: 'json',
                success: function(result) {
                    container.innerHTML = '';
                    var chartDiv = document.createElement('div');
                    chartDiv.id = 'overlayChart';
                    chartDiv.style.cssText = 'width:100%; height:100%;';
                    container.appendChild(chartDiv);
                    if (!result.series || result.series.length === 0) {
                        chartDiv.innerHTML = '<div class="loading-placeholder">无数据</div>';
                        return;
                    }
                    // 使用 Highcharts stockChart 绘制叠加曲线
                    var series = result.series; // 每个元素包含 name, data
                    initDeviceRealtimeMonitoringStockChartFn(series, null, 'overlayChart', '叠加对比', '', '时间', '', ['#7cb5ec','#434348','#90ed7d'], true, true, false);
                },
                error: function() {
                    container.innerHTML = '<div class="loading-placeholder" style="color:red;">加载失败</div>';
                }
            });
        }

        // ================================================================
        // 7. 导出
        // ================================================================
        function exportData() {
            if (!currentDeviceId) { mini.alert('请选择设备'); return; }
            var start = mini.get('startDate').getValue();
            var end = mini.get('endDate').getValue();
            var key = 'exportHistory_' + currentDeviceId + '_' + new Date().getTime();
            var url = context + '/historyController/exportData';
            var param = '&deviceId=' + currentDeviceId + '&start=' + encodeURIComponent(start) + '&end=' + encodeURIComponent(end) + '&key=' + key;
            exportDataMask(key, document.querySelector('.right-panel'), '数据导出中...');
            openExcelWindow(url + '?flag=true' + param);
        }

        // ================================================================
        // 8. 初始化
        // ================================================================
        $(document).ready(function() {
            mini.parse();
            buildLevel1Tabs();
            // 默认时间（最近24小时）
            var now = new Date();
            var yesterday = new Date(now.getTime() - 24*3600*1000);
            mini.get('startDate').setValue(yesterday);
            mini.get('endDate').setValue(now);

            window.addEventListener('message', function(event) {
                var msg = event.data;
                if (msg && msg.action === 'refresh') {
                    console.log('父页面刷新指令');
                }
            });
            console.log('历史查询模块加载完成');
        });

        // 暴露全局函数
        window.doQuery = doQuery;
        window.exportData = exportData;
        window.onDeviceSelect = onDeviceSelect;
    </script>
</body>
</html>