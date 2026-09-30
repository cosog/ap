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
        /* ===== 全局基础样式 ===== */
        html, body {
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
        /* 底部一级标签 */
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
        /* 左侧面板 */
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
            box-shadow: 0 1px 4px rgba(0,0,0,0.06);
            display: flex;
            flex-direction: column;
        }
        .stat-chart-area {
            flex: 1;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.06);
            display: flex;
            flex-direction: column;
        }
        .stat-chart-area .mini-tabs {
            flex: 1;
        }
        .stat-chart-area .mini-tabs .mini-tab-body {
            height: 100% !important;
            padding: 0 !important;
        }
        /* ===== 右侧面板 Flex 布局 ===== */
        .right-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #f0f2f5;
            padding: 4px;
        }
        #alarmDetailTabs {
            height: 100%;
            width: 100%;
            overflow: hidden;
        }
        #alarmDetailTabs .mini-tabs-body {
            height: 100% !important;
        }
        #alarmDetailTabs .mini-tab-body {
            height: 100% !important;
            padding: 0 !important;
            margin: 0 !important;
            overflow: hidden !important;
        }
        .detail-grid-container {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
        .detail-grid-container .mini-datagrid {
            width: 100%;
            height: 100%;
        }
        .mini-toolbar .separator {
            width: 1px;
            height: 20px;
            background: #ddd;
            margin: 0 4px;
        }
        #alarmTypeChartContainer, #alarmLevelChartContainer {
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
        .loading-placeholder.error { color: #ff4d4f; }
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
        <div id="alarmQueryPanel"  style="flex:1; overflow:hidden;">
            <div id="alarmMainSplitter" class="mini-splitter" style="width:100%; height:100%;" vertical="false">
                <!-- 左侧：设备概览 + 统计图表（垂直分割） -->
                <div size="45%" showCollapseButton="false" minSize="200">
                    <div class="left-panel" style="height:100%; background:#f0f2f5; padding:4px; display:flex; flex-direction:column; overflow:hidden;">
                        <div class="mini-splitter" style="width:100%; height:100%;" vertical="true">
                            <!-- 设备概览表格 -->
                            <div id="alarmOverviewPanel" size="50%" showCollapseButton="false" minSize="120">
                                <div class="device-overview-area" style="height:100%;">
                                    <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:4px 8px;display:flex;align-items:center;gap:6px;flex-shrink:0;">
                                        <button id="btnRefreshOverview" class="mini-button" plain="true" iconCls="note-refresh" onclick="refreshData()">刷新</button>
                                        <span class="separator"></span>
                                        <span id="statRangeTypeLabel" style="font-size:12px;color:#333;margin-right:4px;">统计类型：</span>
                                        <input id="alarmStatRangeType" class="mini-radiobuttonlist" valueField="id" textField="text" value="0" onvaluechanged="onStatRangeChanged" />
                                        <span class="separator"></span>
                                        <input id="overviewDeviceCombo" class="mini-combobox" style="width:140px;" emptyText="-- 全部 --" url="<%=path%>/wellInformationManagerController/loadWellComboxList" onbeforeload="onDeviceComboBeforeLoad" onshowpopup="onDeviceComboShowPopup" onload="onDeviceComboLoad" dataField="list" totalField="totals" valueField="boxkey" textField="boxval" onvaluechanged="onOverviewDeviceChange" />
                                        <span style="flex:1;"></span>
                                        <button id="exportAlarmOverviewBtn" class="mini-button" plain="true" iconCls="export" onclick="exportAlarmOverview()">导出</button>
                                        <!-- MiniUI 标准 hidden -->
                                        <input id="AlarmOverviewSelectRow_Id" class="mini-hidden" value="-1" />
                                        <input id="AlarmOverviewColumnStr_Id" class="mini-hidden" value="" />
                                        <input id="AlarmDetailsColumnStr_Id"  class="mini-hidden" value="" />
                                        <input id="selectedAlarmStatType_Id"  class="mini-hidden" value="" />
                                        <input id="selectedAlarmStatLevel_Id" class="mini-hidden" value="" />
                                    </div>
                                    <div class="mini-toolbar" style="border:0;border-top:1px solid #f0f0f0;padding:2px 8px;display:flex;align-items:center;justify-content:flex-end;gap:16px;flex-shrink:0;background:#fafafa;height:28px;">
                                        <span style="font-size:12px;color:#333;">
                                            <span id="overviewDeviceCountLabel">设备数：</span><span id="overviewDeviceCount">0</span>
                                        </span>
                                        <span style="font-size:12px;color:#333;">
                                            <span id="overviewAlarmCountLabel">报警数：</span><span id="overviewAlarmCount">0</span>
                                        </span>
                                    </div>
                                    <div style="flex:1;overflow:hidden;">
                                        <div id="alarmOverviewGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="100" allowResize="true" allowAlternating="true"
                                            url="<%=path%>/alarmQueryController/getAlarmOverviewData"
                                            dataField="totalRoot" totalField="totalCount"
                                            onselectionchanged="onOverviewRowSelect"
                                            onload="onOverviewGridLoad" onbeforeload="onOverviewGridBeforeLoad">
                                            <div property="columns"><!-- 动态生成 --></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 统计图表 -->
                            <div id="statPanel" size="50%" showCollapseButton="true" minSize="100" collapseDirection="bottom">
                                <div class="stat-chart-area" style="height:100%;">
                                    <div class="mini-tabs" id="statTabs" style="width:100%;height:100%;" activeIndex="0" onactivechanged="onStatTabChanged">
                                        <div title="报警类型" name="stat_type">
                                            <div id="alarmTypeChartContainer" style="width:100%;height:100%;min-height:200px;"></div>
                                        </div>
                                        <div title="报警级别" name="stat_level">
                                            <div id="alarmLevelChartContainer" style="width:100%;height:100%;min-height:200px;"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 右侧：报警详情 Tabs（HTML 静态定义 6 个 tab，无占位 tab） -->
                <div size="55%" showCollapseButton="true" minSize="300" collapseDirection="right">
                    <div class="right-panel">
                        <!-- 工具栏（固定高度） -->
                        <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:4px 8px;display:flex;align-items:center;flex-wrap:wrap;gap:4px;flex-shrink:0;background:#fff;">
                            <span style="font-size:12px;color:#333;" id="detailRangeLabel">区间：</span>
                            <input id="detailStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                            <span style="margin-left:8px;font-size:12px;color:#333;" id="detailTimeToLabel">至：</span>
                            <input id="detailEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                            <span style="font-size:12px;color:#333;margin-left:8px;" id="detailAlarmLevelLabel">报警级别：</span>
                            <input id="detailAlarmLevel" class="mini-combobox" style="width:100px;" emptyText="-- 全部 --" valueField="id" textField="text" />
                            <button id="detailQueryBtn" class="mini-button" plain="true" iconCls="search" onclick="refreshDetailData()">查询</button>
                            <button id="detailExportBtn" class="mini-button" plain="true" iconCls="export" onclick="exportAlarmDetail()">导出</button>
                            <span style="flex:1;"></span>
                            <span id="detailTotalCountLabel">总记录数：</span><span id="detailTotalCountSpan">0</span>
                        </div>
                        <div class="mini-fit">
                            <!-- ★ 静态定义 6 个报警类型 tab，全部默认 visible=false -->
                            <div id="alarmDetailTabs" class="mini-tabs"
                                 style="height:100%; width:100%; overflow:hidden;"
                                 onactivechanged="onDetailTabChanged">

                                <div title="工况报警" name="FESDiagramResultAlarm" visible="false">
                                    <div class="detail-grid-container">
                                        <div id="alarmDetailGrid_FESDiagramResultAlarm" class="mini-datagrid"
                                             style="width:100%;height:100%;"
                                             showPager="true" pageSize="100" allowResize="true" allowAlternating="true"
                                             url="<%=path%>/alarmQueryController/getAlarmData"
                                             dataField="totalRoot" totalField="totalCount"
                                             onbeforeload="onDetailGridBeforeLoad" onload="onDetailGridLoad">
                                            <div property="columns"></div>
                                        </div>
                                    </div>
                                </div>

                                <div title="通信状态报警" name="CommunicationAlarm" visible="false">
                                    <div class="detail-grid-container">
                                        <div id="alarmDetailGrid_CommunicationAlarm" class="mini-datagrid"
                                             style="width:100%;height:100%;"
                                             showPager="true" pageSize="100" allowResize="true" allowAlternating="true"
                                             url="<%=path%>/alarmQueryController/getAlarmData"
                                             dataField="totalRoot" totalField="totalCount"
                                             onbeforeload="onDetailGridBeforeLoad" onload="onDetailGridLoad">
                                            <div property="columns"></div>
                                        </div>
                                    </div>
                                </div>

                                <div title="运行状态报警" name="RunStatusAlarm" visible="false">
                                    <div class="detail-grid-container">
                                        <div id="alarmDetailGrid_RunStatusAlarm" class="mini-datagrid"
                                             style="width:100%;height:100%;"
                                             showPager="true" pageSize="100" allowResize="true" allowAlternating="true"
                                             url="<%=path%>/alarmQueryController/getAlarmData"
                                             dataField="totalRoot" totalField="totalCount"
                                             onbeforeload="onDetailGridBeforeLoad" onload="onDetailGridLoad">
                                            <div property="columns"></div>
                                        </div>
                                    </div>
                                </div>

                                <div title="数值量报警" name="NumericValueAlarm" visible="false">
                                    <div class="detail-grid-container">
                                        <div id="alarmDetailGrid_NumericValueAlarm" class="mini-datagrid"
                                             style="width:100%;height:100%;"
                                             showPager="true" pageSize="100" allowResize="true" allowAlternating="true"
                                             url="<%=path%>/alarmQueryController/getAlarmData"
                                             dataField="totalRoot" totalField="totalCount"
                                             onbeforeload="onDetailGridBeforeLoad" onload="onDetailGridLoad">
                                            <div property="columns"></div>
                                        </div>
                                    </div>
                                </div>

                                <div title="枚举量报警" name="EnumValueAlarm" visible="false">
                                    <div class="detail-grid-container">
                                        <div id="alarmDetailGrid_EnumValueAlarm" class="mini-datagrid"
                                             style="width:100%;height:100%;"
                                             showPager="true" pageSize="100" allowResize="true" allowAlternating="true"
                                             url="<%=path%>/alarmQueryController/getAlarmData"
                                             dataField="totalRoot" totalField="totalCount"
                                             onbeforeload="onDetailGridBeforeLoad" onload="onDetailGridLoad">
                                            <div property="columns"></div>
                                        </div>
                                    </div>
                                </div>

                                <div title="开关量报警" name="SwitchingValueAlarm" visible="false">
                                    <div class="detail-grid-container">
                                        <div id="alarmDetailGrid_SwitchingValueAlarm" class="mini-datagrid"
                                             style="width:100%;height:100%;"
                                             showPager="true" pageSize="100" allowResize="true" allowAlternating="true"
                                             url="<%=path%>/alarmQueryController/getAlarmData"
                                             dataField="totalRoot" totalField="totalCount"
                                             onbeforeload="onDetailGridBeforeLoad" onload="onDetailGridLoad">
                                            <div property="columns"></div>
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
    } catch(e) { console.warn('无法获取 tabInfo', e); }
    var currentLevel1 = null, currentLevel2 = null;
    var level1Data = [], level2Data = [];
    var currentDeviceId = 0, currentDeviceName = '';
    var alarmDetailTabs = null, statTabs = null, overviewGrid = null;

    var ALARM_TYPE_CONFIG = [
        { id: 'FESDiagramResultAlarm', title: _loginUserLanguageResource.FESDiagramResultAlarm, key: 'FESDiagramResultAlarm', type: 4 },
        { id: 'CommunicationAlarm',    title: _loginUserLanguageResource.commStatusAlarm,       key: 'CommStatusAlarm',       type: 3 },
        { id: 'RunStatusAlarm',        title: _loginUserLanguageResource.runStatusAlarm,        key: 'RunStatusAlarm',        type: 6 },
        { id: 'NumericValueAlarm',     title: _loginUserLanguageResource.numericValueAlarm,     key: 'NumericValueAlarm',     type: 2 },
        { id: 'EnumValueAlarm',        title: _loginUserLanguageResource.enumValueAlarm,        key: 'EnumValueAlarm',        type: 1 },
        { id: 'SwitchingValueAlarm',   title: _loginUserLanguageResource.switchingValueAlarm,   key: 'SwitchingValueAlarm',   type: 0 }
    ];

    //---------- 全局选中状态 ----------
    var _alarmRestoreState = null;
    var _alarmSuppressSave = { value: true };


    // ================================================================
    // 0.1 Hidden 控件的 MiniUI 标准读写 + 参数规范化工具
    // ================================================================
    function getHiddenVal(id) {
        var ctl = mini.get(id);
        if (!ctl) return '';
        var v = ctl.getValue();
        return (v === null || v === undefined) ? '' : v;
    }
    function setHiddenVal(id, val) {
        var ctl = mini.get(id);
        if (!ctl) return;
        ctl.setValue((val === null || val === undefined) ? '' : val);
    }
    function setSelectedAlarmFilter(typeValue, levelValue, keepType, keepLevel) {
        function norm(v) {
            return (v === null || v === undefined
                    || v === 'undefined' || v === 'null') ? '' : v;
        }
        if (!keepType)  setHiddenVal('selectedAlarmStatType_Id',  norm(typeValue));
        if (!keepLevel) setHiddenVal('selectedAlarmStatLevel_Id', norm(levelValue));
    }
    function safeAlarmParam(v) {
        if (v === null || v === undefined) return '';
        var s = String(v);
        return (s === 'undefined' || s === 'null') ? '' : s;
    }
    function getPointField(point, name) {
        if (!point) return '';
        if (point.options && point.options[name] !== undefined) return point.options[name];
        if (point[name] !== undefined) return point[name];
        return '';
    }


    // ================================================================
    // 0.2 通用标签页可见性/激活 收口函数
    // ─ HTML 里定义 tab 结构 + 默认 visible=false + 中文 title
    // ─ 本函数只做「权限集合 → 可见状态」的映射，不做任何 add/remove
    // ─ 无可见 tab 时返回 { visibleCount: 0 }，由调用方决定是否隐藏整个 pane
    // ================================================================
    function applyTabsVisibility(tabsId, allowedNames, options) {
        options = options || {};
        var tabsControl = mini.get(tabsId);
        if (!tabsControl) return { visibleCount: 0, activeTab: null };

        allowedNames = allowedNames || [];

        var allTabs       = tabsControl.getTabs();
        var visibleCount  = 0;
        var firstVisible  = null;
        var currentActive = tabsControl.getActiveTab();
        var currentName   = currentActive ? currentActive.name : '';
        var currentStillVisible = false;

        // 1) 遍历所有 tab，按 allowedNames 设置 visible
        for (var i = 0; i < allTabs.length; i++) {
            var tab = allTabs[i];
            var isVisible = allowedNames.indexOf(tab.name) !== -1;
            tabsControl.updateTab(tab, { visible: isVisible });
            if (isVisible) {
                visibleCount++;
                if (!firstVisible) firstVisible = tab;
                if (tab.name === currentName) currentStillVisible = true;
            }
        }

        // 2) 无可见 tab → 返回，让调用方决定要不要隐藏整个 pane
        if (visibleCount === 0) {
            return { visibleCount: 0, activeTab: null };
        }

        // 3) 决定激活目标
        var targetTab = null;

        if (options.preferActiveName
            && allowedNames.indexOf(options.preferActiveName) !== -1) {
            for (var k = 0; k < allTabs.length; k++) {
                if (allTabs[k].name === options.preferActiveName
                    && allTabs[k].visible !== false) {
                    targetTab = allTabs[k];
                    break;
                }
            }
        }
        if (!targetTab && currentStillVisible) targetTab = currentActive;
        if (!targetTab && firstVisible)         targetTab = firstVisible;

        if (targetTab) {
            tabsControl.activeTab(targetTab);
        }

        return { visibleCount: visibleCount, activeTab: targetTab };
    }


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
            span.onclick = function() { selectLevel1(parseInt(this.dataset.index)); };
            container.appendChild(span);
        }
        if (level1Data.length > 0) {
            var startIndex = 0;
            if (_alarmRestoreState.deviceTypeId) {
                var t = findTargetLevels(level1Data, _alarmRestoreState.deviceTypeId);
                if (t) {
                    _alarmRestoreState.level2Index = t.level2Index;
                    startIndex = t.level1Index;
                }
                _alarmRestoreState.deviceTypeId = '';
            }
            selectLevel1(startIndex);
        }
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

        // 一级无子标签
        if (!children || children.length === 0) {
            container.classList.add('hidden');
            level2Data = [];

            currentLevel2 = {
                text: parentItem.text,
                deviceTypeId: parentItem.deviceTypeId,
                isAll: false,
                isLevel1Direct: true
            };
            _alarmRestoreState.level2Index = -1;

            loadAllData(currentLevel2);
            saveGlobalSelection(currentLevel2.deviceTypeId,
                _alarmRestoreState.deviceId || '',
                { suppress: _alarmSuppressSave.value });
            return;
        }

        // 一级有子标签
        container.classList.remove('hidden');
        level2Data = children;

        var allTabs = [];
        if (children.length > 1) {
            var allIds = [];
            for (var i = 0; i < children.length; i++) {
                if (children[i].deviceTypeId) allIds.push(children[i].deviceTypeId);
            }
            allTabs.push({
                text: _loginUserLanguageResource.all,
                deviceTypeId: allIds.join(','),
                isAll: true
            });
        }
        for (var i = 0; i < children.length; i++) allTabs.push(children[i]);

        var defaultIndex = 0;
        if (_alarmRestoreState.level2Index >= 0 && _alarmRestoreState.level2Index < allTabs.length) {
            defaultIndex = _alarmRestoreState.level2Index;
        }
        _alarmRestoreState.level2Index = -1;

        for (var i = 0; i < allTabs.length; i++) {
            var item = allTabs[i];
            var div = document.createElement('div');
            div.className = 'tab-item' + (i === defaultIndex ? ' active' : '');
            div.dataset.index = i;
            div.dataset.deviceTypeId = item.deviceTypeId;
            div.dataset.isAll = item.isAll || false;
            div.textContent = item.text;
            div.title = item.text;
            div.onclick = function() { selectLevel2(parseInt(this.dataset.index)); };
            container.appendChild(div);
        }

        if (allTabs.length > 0) {
            currentLevel2 = allTabs[defaultIndex];
            loadAllData(currentLevel2);
        }
    }


    function selectLevel2(index) {
        var container = document.getElementById('level2Sidebar');
        var tabs = container.querySelectorAll('.tab-item');
        var allTabs = [{ text: _loginUserLanguageResource.all, deviceTypeId: '', isAll: true }];
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
    // 3. 加载所有数据
    // ================================================================
    var _statTabsInitialized = false;
    var _loadingStats = false;
    function loadAllData(level2Item) {
        if (!level2Item) return;
        var deviceTypeId = level2Item.deviceTypeId || '0';
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        setSelectedAlarmFilter('', '', false, false);
        updateDetailTabs(deviceTypeId);
        refreshOverview();
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
        params.alarmType  = safeAlarmParam(getHiddenVal('selectedAlarmStatType_Id'));
        params.alarmLevel = safeAlarmParam(getHiddenVal('selectedAlarmStatLevel_Id'));
        var statTab = mini.get('statTabs');
        var activeStat = statTab ? statTab.getActiveTab() : null;
        var statType = 0;
        if (activeStat && activeStat.name === 'stat_level') statType = 1;
        params.statType = statType;
        var statRange = mini.get('alarmStatRangeType');
        params.alarmQueryStatRangeType = statRange ? parseInt(statRange.getValue(), 10) : 0;
    }

    function onOverviewGridLoad(e) {
        var grid = e.sender, result = e.result;
        if (result && result.columns) {
            var columns = buildOverviewColumns(result.columns);
            setHiddenVal('AlarmOverviewColumnStr_Id', JSON.stringify(result.columns));
            setTimeout(function() {
                grid.setColumns(columns);
                grid.doLayout();
            }, 50);
        }
        if (result) {
            document.getElementById('overviewDeviceCount').textContent = result.totalCount || 0;
            document.getElementById('overviewAlarmCount').textContent = result.alarmCount || 0;
        }
        var data = grid.getData();
        if (data && data.length > 0){
            var restoreId = _alarmRestoreState.deviceId;
            if (!restoreId && currentDeviceId) {
                restoreId = currentDeviceId;
            }

            var selectRow = 0;
            if (restoreId > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (String(data[i].id) === String(restoreId)) {
                        selectRow = i;
                        break;
                    }
                }
            }
            grid.select(data[selectRow]);
        }else{
            currentDeviceId = 0;
            currentDeviceName = '';
            var startDate = mini.get('detailStartDate');
            if(startDate){ startDate.setValue(''); }
            var endDate = mini.get('detailEndDate');
            if(endDate){ endDate.setValue(''); }
            var alarmLevelCombo = mini.get('detailAlarmLevel');
            if(alarmLevelCombo){ alarmLevelCombo.setValue(''); }
            refreshDetailData();
        }

        if (_alarmSuppressSave.value) {
            setTimeout(function () { _alarmSuppressSave.value = false; }, 100);
        }
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
        var startDate = mini.get('detailStartDate');
        if(startDate){ startDate.setValue(''); }
        var endDate = mini.get('detailEndDate');
        if(endDate){ endDate.setValue(''); }
        var alarmLevelCombo = mini.get('detailAlarmLevel');
        if(alarmLevelCombo){ alarmLevelCombo.setValue(''); }

        var selected = e.selected;
        if (selected) {
            currentDeviceId = selected.id;
            currentDeviceName = selected.deviceName || '';

            _alarmRestoreState.deviceId = String(selected.id);

            saveGlobalSelection(
                currentLevel2 ? currentLevel2.deviceTypeId : '',
                selected.id,
                { suppress: _alarmSuppressSave.value }
            );

            refreshDetailData();
        }
    }
    function onStatRangeChanged() {
        refreshOverview();
        var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        loadStatCharts(deviceTypeId, orgId);
    }
    function onOverviewDeviceChange() { refreshOverview(); }
    function refreshOverview() {
        var grid = mini.get('alarmOverviewGrid');
        if (grid) grid.load();
    }

    // ================================================================
    // 5. 统计图表（柱状图 + 钻取）
    // ================================================================
    function loadStatCharts(deviceTypeId, orgId, callback) {
        var statType = 0;
        var statTab = mini.get('statTabs');
        if (statTab) {
            var active = statTab.getActiveTab();
            if (active && active.name === 'stat_level') statType = 1;
        }
        var statRange = mini.get('alarmStatRangeType');
        var alarmQueryStatRangeType = statRange ? parseInt(statRange.getValue(), 10) : 0;
        var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
        var alarmConfig = projectTabConfig.AlarmQuery || {};
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
        if (!_statTabsInitialized) {
            _statTabsInitialized = true;
            return;
        }
        if (_loadingStats) return;
        setSelectedAlarmFilter('', '', false, false);
        var deviceTypeId = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        loadStatCharts(deviceTypeId, orgId);
        refreshOverview();
    }

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
            var singleSeriesData = { name: _loginUserLanguageResource.FESDiagramResultAlarm, id: 'FESDiagramResultAlarm', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
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
            var singleSeriesData = { name: _loginUserLanguageResource.commStatusAlarm, id: 'commStatusAlarm', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
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
            var singleSeriesData = { name: _loginUserLanguageResource.runStatusAlarm, id: 'runStatusAlarm', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
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
            var singleSeriesData = { name: _loginUserLanguageResource.numericValueAlarm, id: 'numericValueAlarm', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
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
            var singleSeriesData = { name: _loginUserLanguageResource.enumValueAlarm, id: 'enumValueAlarm', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
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
            var singleSeriesData = { name: _loginUserLanguageResource.switchingValueAlarm, id: 'switchingValueAlarm', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
        }
        if (rawSeriesData.length === 0) {
            container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
            return;
        }
        showAlarmStatDrillDownChart(title, 'alarmTypeChartContainer', subtitle, yAxisTitle, rawSeriesData, drilldownSeriesData);
    }

    // ---- 报警级别统计（钻取柱状图：级别 → 类型） ----
    function renderAlarmLevelStat(result, alarmConfig) {
        var container = document.getElementById('alarmLevelChartContainer');
        if (!container) return;
        var title = _loginUserLanguageResource.alarmLevel;
        var subtitle = _loginUserLanguageResource.alarmStatisticsChartSubtitle2;
        var yAxisTitle = _loginUserLanguageResource.deviceCount;
        var rawSeriesData = [];
        var drilldownSeriesData = [];

        var alarmLevel1DeviceCount = 0, alarmLevel2DeviceCount = 0, alarmLevel3DeviceCount = 0;
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

        if (alarmLevel1DeviceCount > 0) {
            rawSeriesData.push({
                name: _loginUserLanguageResource.alarmLevel1,
                y: alarmLevel1DeviceCount,
                drilldown: 'alarmLevel1',
                code: 'alarmLevel1',
                alarmType: '',
                alarmLevel: 100
            });
            var singleSeriesData = { name: _loginUserLanguageResource.alarmLevel1, id: 'alarmLevel1', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
        }
        if (alarmLevel2DeviceCount > 0) {
            rawSeriesData.push({
                name: _loginUserLanguageResource.alarmLevel2,
                y: alarmLevel2DeviceCount,
                drilldown: 'alarmLevel2',
                code: 'alarmLevel2',
                alarmType: '',
                alarmLevel: 200
            });
            var singleSeriesData = { name: _loginUserLanguageResource.alarmLevel2, id: 'alarmLevel2', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
        }
        if (alarmLevel3DeviceCount > 0) {
            rawSeriesData.push({
                name: _loginUserLanguageResource.alarmLevel3,
                y: alarmLevel3DeviceCount,
                drilldown: 'alarmLevel3',
                code: 'alarmLevel3',
                alarmType: '',
                alarmLevel: 300
            });
            var singleSeriesData = { name: _loginUserLanguageResource.alarmLevel3, id: 'alarmLevel3', data: [] };
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
            if (singleSeriesData.data.length > 0) drilldownSeriesData.push(singleSeriesData);
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
        var isTypeChart = (divId === 'alarmTypeChartContainer');
        Highcharts.chart(container, {
            chart: {
                type: 'column',
                zooming: { mouseWheel: { enabled: false } },
                events: {
                    drilldown: function(e) {
                        var point = e.point;
                        var alarmType  = getPointField(point, 'alarmType');
                        var alarmLevel = getPointField(point, 'alarmLevel');
                        if (isTypeChart) {
                            setSelectedAlarmFilter(alarmType, '', false, false);
                        } else {
                            setSelectedAlarmFilter('', alarmLevel, false, false);
                        }
                        refreshOverview();
                        alarmStatDrillDownChartResetAllPoints(this);
                    },
                    drillup: function() {
                        alarmStatDrillDownChartResetAllPoints(this);
                        setSelectedAlarmFilter('', '', false, false);
                        refreshOverview();
                    }
                }
            },
            title: { text: title, style: { fontSize: '13px' } },
            subtitle: { text: subtitle },
            xAxis: { type: 'category' },
            yAxis: {
                title: { text: yAxisTitle },
                lineWidth: 1,
                tickWidth: 1,
                tickLength: 5,
                allowDecimals: false
            },
            legend: { enabled: false },
            credits: { enabled: false },
            tooltip: {
                headerFormat: '<b><span style="font-size:11px">{series.name}</span></b><br>',
                pointFormat: '<b><span style="color:{point.color}">{point.name}</span></b>: {point.y}'
            },
            plotOptions: {
                column: { maxPointWidth: 70 },
                series: {
                    borderWidth: 0,
                    cursor: 'pointer',
                    dataLabels: { enabled: true, format: '{point.y:.0f}' },
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
                                if (point.drilldown || (point.options && point.options.drilldown)) {
                                    return true;
                                }
                                var alarmType  = getPointField(point, 'alarmType');
                                var alarmLevel = getPointField(point, 'alarmLevel');
                                var isSelected = point.selected;
                                if (isSelected) {
                                    if (!point.destroyed) {
                                        point.select(false);
                                        alarmStatDrillDownChartResetPointStyle(point);
                                    }
                                    if (isTypeChart) {
                                        setSelectedAlarmFilter(undefined, '', true, false);
                                    } else {
                                        setSelectedAlarmFilter('', undefined, false, true);
                                    }
                                } else {
                                    if (!point.destroyed) {
                                        alarmStatDrillDownChartResetAllPoints(point.series.chart);
                                        point.select(true);
                                        alarmStatDrillDownChartApplyHighlightEffect(point);
                                    }
                                    if (isTypeChart) {
                                        setSelectedAlarmFilter(undefined, alarmLevel, true, false);
                                    } else {
                                        setSelectedAlarmFilter(alarmType, undefined, false, true);
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
                                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                                stops: [[0, '#f59e0b'], [1, '#b45309']]
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
            series: [{ name: title, colorByPoint: true, data: rawSeriesData }],
            drilldown: { breadcrumbs: { position: { align: 'right' } }, series: drilldownSeriesData }
        });
    }

    // ===== 辅助函数（钻取图表） =====
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
    // 6. 更新报警详情 Tabs（静态 tab + 按权限切 visible + 控制 splitter）
    // ================================================================
    function updateDetailTabs(deviceTypeId) {
        alarmDetailTabs = mini.get('alarmDetailTabs');
        if (!alarmDetailTabs) return;

        var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
        var alarmConfig = projectTabConfig.AlarmQuery || {};

        // 按 ALARM_TYPE_CONFIG 顺序收集「允许显示」的 name 集合
        var allowedNames = [];
        for (var i = 0; i < ALARM_TYPE_CONFIG.length; i++) {
            var cfg = ALARM_TYPE_CONFIG[i];
            if (alarmConfig[cfg.key] === true) {
                allowedNames.push(cfg.id);
            }
        }

        // 交给通用函数处理显隐/激活
        var result = applyTabsVisibility('alarmDetailTabs', allowedNames, {});

        // ★ 关键：无可见 tab → 隐藏整个右侧 pane；有 → 显示
        var splitter = mini.get('alarmMainSplitter');
        if (splitter) {
            if (result.visibleCount === 0) {
                splitter.hidePane(2);   // 右侧 pane 索引从 1 开始，右侧 = 2
            } else {
                splitter.showPane(2);
            }
        }
    }

    // ================================================================
    // 7. 右侧 Tab 切换事件（按 name 反查 grid）
    // ================================================================
    function onDetailTabChanged(e) {
        var tab = e.tab;
        if (!tab) return;
        if (tab.visible === false) return;

        var gridId = 'alarmDetailGrid_' + tab.name;
        var grid = mini.get(gridId);
        if (!grid) return;

        grid.load();
        // 首次显示时确保布局正确（grid 原先在 display:none 容器里）
        setTimeout(function () {
            try { grid.doLayout(); } catch (ex) { /* ignore */ }
        }, 0);
    }

    // ================================================================
    // 8. 刷新详情数据
    // ================================================================
    function refreshDetailData() {
        var detailTabs = mini.get('alarmDetailTabs');
        if (!detailTabs) return;
        var activeTab = detailTabs.getActiveTab();
        if (!activeTab) return;
        if (activeTab.visible === false) return;

        var grid = mini.get('alarmDetailGrid_' + activeTab.name);
        if (grid) grid.load();
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
        var combo = mini.get('overviewDeviceCombo');
        params.deviceName = combo ? combo.getValue() : '';
    }

    window.onDeviceComboShowPopup = function(e) {
        var combo = e.sender;
        var data = combo.getData();
        var hidePopup=false;
        if (!data || data.length <= 1) {
            combo.hidePopup();
            hidePopup=true;
        }
        combo.load(combo.url);
        if(hidePopup){
            combo.showPopup();
        }
    };

    window.onDeviceComboLoad = function(e) {
        var combo = e.sender;
    };

    // ================================================================
    // 9. Grid 事件处理（全局函数，供声明式绑定）
    // ================================================================
    function onDetailGridBeforeLoad(e) {
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

        var detailTabs = mini.get('alarmDetailTabs');
        if (detailTabs) {
            var activeTab = detailTabs.getActiveTab();
            if (activeTab) {
                params.alarmType = getAlarmTypeFromTabName(activeTab.name);
            } else {
                params.alarmType = -1;
            }
        } else {
            params.alarmType = -1;
        }
        var startDate = mini.get('detailStartDate');
        var endDate = mini.get('detailEndDate');
        var alarmLevelCombo = mini.get('detailAlarmLevel');
        params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
        params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
        params.alarmLevel = alarmLevelCombo ? alarmLevelCombo.getValue() : '';
        params.isSendMessage = '';
        e.params = params;
    }

    function onDetailGridLoad(e) {
        var grid = e.sender;
        var result = e.result;
        if (result && result.columns) {
            var cols = buildDetailColumns(result.columns);
            setHiddenVal('AlarmDetailsColumnStr_Id', JSON.stringify(result.columns));
            grid.setColumns(cols);
        }
        var totalSpan = document.getElementById('detailTotalCountSpan');
        if (totalSpan) totalSpan.textContent = result ? result.totalCount : 0;
        document.getElementById('detailTotalCountLabel').style.display = 'inline';
        if (result && result.start_date) {
            var startDate = mini.get('detailStartDate');
            var endDate = mini.get('detailEndDate');
            if (startDate && !startDate.getValue()) startDate.setValue(result.start_date);
            if (endDate && !endDate.getValue()) endDate.setValue(result.end_date);
        }
    }

    // ================================================================
    // 10. 辅助函数：构建表格列
    // ================================================================
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
                column.width = 50;
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
            if (ALARM_TYPE_CONFIG[i].id === tabName) return ALARM_TYPE_CONFIG[i].type;
        }
        return -1;
    }

    // ================================================================
    // 11. 导出功能
    // ================================================================
    function exportAlarmOverview() {
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var deviceName = mini.get('overviewDeviceCombo') ? mini.get('overviewDeviceCombo').getValue() : '';
        var statType = 0;
        var statTab = mini.get('statTabs');
        if (statTab && statTab.getActiveTab() && statTab.getActiveTab().name === 'stat_level') statType = 1;
        var alarmType  = safeAlarmParam(getHiddenVal('selectedAlarmStatType_Id'));
        var alarmLevel = safeAlarmParam(getHiddenVal('selectedAlarmStatLevel_Id'));
        var statRange = mini.get('alarmStatRangeType');
        var alarmQueryStatRangeType = statRange ? parseInt(statRange.getValue(), 10) : 0;
        var fileName = _loginUserLanguageResource.alarmData + '-' + _loginUserLanguageResource.deviceList;
        var title = fileName;
        var columnStr = getHiddenVal('AlarmOverviewColumnStr_Id');
        var fields = '', heads = '';
        try {
            var columns = JSON.parse(columnStr);
            var lockedfields = '', lockedheads = '', unlockedfields = '', unlockedheads = '';
            columns.forEach(function(col) {
                if (col.hidden || col.dataIndex === 'id') return;
                var dataIndex = col.dataIndex || col.field;
                var header = col.header || col.text || col.title || dataIndex;
                if (col.locked) {
                    lockedfields += dataIndex + ','; lockedheads += header + ',';
                } else {
                    unlockedfields += dataIndex + ','; unlockedheads += header + ',';
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
        } catch(e) {
            mini.alert(_loginUserLanguageResource.operationFailed);
            return;
        }
        var key = 'exportAlarmOverview_' + deviceType + '_' + Date.now();
        var url = context + '/alarmQueryController/exportAlarmOverviewData';
        var param = '&fields=' + fields + '&heads=' + URLencode(URLencode(heads)) +
            '&orgId=' + orgId +
            '&deviceType=' + deviceType +
            '&deviceName=' + URLencode(URLencode(deviceName)) +
            '&alarmType=' + alarmType +
            '&alarmLevel=' + alarmLevel +
            '&statType=' + statType +
            '&alarmQueryStatRangeType='+alarmQueryStatRangeType+
            '&fileName=' + URLencode(URLencode(fileName)) +
            '&title=' + URLencode(URLencode(title)) +
            '&key=' + key;
        exportDataMask(key, 'alarmQueryPanel', _loginUserLanguageResource.loadingData);
        openExcelWindow(url + '?flag=true' + param);
    }

    function exportAlarmDetail() {
        var detailTabs = mini.get('alarmDetailTabs');
        var activeTab = detailTabs ? detailTabs.getActiveTab() : null;
        if (!activeTab) { mini.alert('请选择报警类型'); return; }
        var grid = mini.get('alarmDetailGrid_' + activeTab.name);
        if (!grid) { mini.alert('表格未加载完成'); return; }
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var dictDeviceType = deviceType;
        if (deviceType && deviceType.indexOf(',') > -1) {
            dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
        }
        var startDate = mini.get('detailStartDate');
        var endDate = mini.get('detailEndDate');
        var alarmLevelCombo = mini.get('detailAlarmLevel');
        var alarmType = getAlarmTypeFromTabName(activeTab.name);
        var fileName = currentDeviceName + '-' + activeTab.title;
        var title = fileName;
        var columnStr = getHiddenVal('AlarmDetailsColumnStr_Id');
        var fields = '', heads = '';
        try {
            var columns = JSON.parse(columnStr);
            var lockedfields = '', lockedheads = '', unlockedfields = '', unlockedheads = '';
            columns.forEach(function(col) {
                if (col.hidden || col.dataIndex === 'id') return;
                var dataIndex = col.dataIndex || col.field;
                var header = col.header || col.text || col.title || dataIndex;
                if (col.locked) {
                    lockedfields += dataIndex + ','; lockedheads += header + ',';
                } else {
                    unlockedfields += dataIndex + ','; unlockedheads += header + ',';
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
        } catch(e) {
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
        exportDataMask(key, 'alarmQueryPanel', _loginUserLanguageResource.loadingData);
        openExcelWindow(url + '?flag=true' + param);
    }

    // ================================================================
    // 12. 刷新与重置
    // ================================================================
    function refreshData() {
        if (currentLevel2) loadAllData(currentLevel2);
    }

    function initI18n() {
        var btnRefresh = mini.get('btnRefreshOverview');
        if (btnRefresh) btnRefresh.setText(_loginUserLanguageResource.refresh);
        var exportBtn = mini.get('exportAlarmOverviewBtn');
        if (exportBtn) exportBtn.setText(_loginUserLanguageResource.exportData);
        var overviewCombo = mini.get('overviewDeviceCombo');
        if (overviewCombo) overviewCombo.setEmptyText('--' + _loginUserLanguageResource.all + '--');

        document.getElementById('statRangeTypeLabel').textContent = _loginUserLanguageResource.statisticsType + '：';
        document.getElementById('overviewDeviceCountLabel').textContent = _loginUserLanguageResource.deviceCount + '：';
        document.getElementById('overviewAlarmCountLabel').textContent = _loginUserLanguageResource.alarmCount + '：';

        var statTabs = mini.get('statTabs');
        if (statTabs) {
            var tabs = statTabs.getTabs();
            if (tabs && tabs.length >= 2) {
                var tab0 = tabs[0];
                var tab1 = tabs[1];
                if (tab0) statTabs.updateTab(tab0, { title: _loginUserLanguageResource.alarmType });
                if (tab1) statTabs.updateTab(tab1, { title: _loginUserLanguageResource.alarmLevel });
            }
        }

        // 报警详情 tabs 标题（i18n 覆盖 HTML 中文默认）
        for (var i = 0; i < ALARM_TYPE_CONFIG.length; i++) {
            var cfg = ALARM_TYPE_CONFIG[i];
            setAlarmTabTitleByName('alarmDetailTabs', cfg.id, cfg.title);
        }

        // 设置统计类型单选按钮列表的选项（实时 / 历史）
        var statRangeType = mini.get('alarmStatRangeType');
        if (statRangeType) {
            statRangeType.setData([
                { id: "0", text: _loginUserLanguageResource.realtime },
                { id: "1", text: _loginUserLanguageResource.history }
            ]);
            statRangeType.setValue("0", false);
        }

        document.getElementById('detailRangeLabel').textContent = _loginUserLanguageResource.range + '：';
        document.getElementById('detailTimeToLabel').textContent = _loginUserLanguageResource.timeTo + '：';
        document.getElementById('detailAlarmLevelLabel').textContent = _loginUserLanguageResource.alarmLevel + '：';

        mini.get('detailQueryBtn').setText(_loginUserLanguageResource.search);
        mini.get('detailExportBtn').setText(_loginUserLanguageResource.exportData);
        mini.get('detailAlarmLevel').setEmptyText('--' + _loginUserLanguageResource.all + '--');

        document.getElementById('detailTotalCountLabel').textContent = _loginUserLanguageResource.totalCount + '：';

        var alarmLevelCombo = mini.get('detailAlarmLevel');
        if (alarmLevelCombo) {
            alarmLevelCombo.setData([
                { id: '', text: _loginUserLanguageResource.all },
                { id: 100, text: _loginUserLanguageResource.alarmLevel1 },
                { id: 200, text: _loginUserLanguageResource.alarmLevel2 },
                { id: 300, text: _loginUserLanguageResource.alarmLevel3 }
            ]);
            alarmLevelCombo.setValue('');
        }
    }

    /**
     * 按 name 更新 tab 标题
     */
    function setAlarmTabTitleByName(tabsId, name, title) {
        var tabs = mini.get(tabsId);
        if (!tabs || title == null) return;
        var arr = tabs.getTabs();
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name === name) {
                tabs.updateTab(arr[i], { title: title });
                break;
            }
        }
    }

    // ================================================================
    // 13. 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();

        _alarmRestoreState = createRestoreState();
        _alarmSuppressSave = { value: true };

        initI18n();
        buildLevel1Tabs();

        window.addEventListener('message', function(event) {
            var message = event.data;
            if (!message || !message.action) return;
            if (message.action === 'refresh') {
                handleAlarmRefreshFromParent(message);
            }
        });
        console.log('报警查询模块加载完成');
    });

    /**
     * 父窗口发出 refresh（组织切换 / 从其他模块切回本模块）时的处理
     */
    function handleAlarmRefreshFromParent(message) {
        console.log('报警查询收到刷新指令, orgId:', message.orgId);

        setSelectedAlarmFilter('', '', false, false);
        var combo = mini.get('overviewDeviceCombo');
        if (combo) combo.setValue('');

        var gsel = loadGlobalSelection();
        var curType = currentLevel2 ? String(currentLevel2.deviceTypeId) : '';

        if (gsel.deviceTypeId && curType !== String(gsel.deviceTypeId)) {
            var t = findTargetLevels(level1Data, gsel.deviceTypeId);
            if (t) {
                _alarmRestoreState.deviceId    = gsel.deviceId || '';
                _alarmRestoreState.level2Index = t.level2Index;
                selectLevel1(t.level1Index);
                return;
            }
        }

        if (gsel.deviceId) {
            _alarmRestoreState.deviceId = String(gsel.deviceId);
        }
        if (typeof refreshData === 'function') refreshData();
    }

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
    window.onDetailGridBeforeLoad = onDetailGridBeforeLoad;
    window.onDetailGridLoad = onDetailGridLoad;
</script>
</body>
</html>