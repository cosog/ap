// ================================================================
// 历史查询模块 - historyQueryInfo.js
// ================================================================

// ---------- URL 集中配置（原来硬编码在 JSP 上的 url 属性，现集中在此） ----------
var HQ_URLS = {
    deviceCombo:     context + '/wellInformationManagerController/loadWellComboxList',
    deviceGrid:      context + '/historyQueryController/getHistoryQueryDeviceList',
    historyDataGrid: context + '/historyQueryController/getDeviceHistoryData',
    workTypeStat:    context + '/historyQueryController/getDeviceResultStatusStatData',
    overlayDataGrid: context + '/historyQueryController/getFESDiagramOverlayData'
};

// ---------- 全局状态 ----------
var _hqTabInfo = null;
var _hqLevel1Data = [];
var _hqLevel2Data = [];
var _hqCurrentLevel1 = null;
var _hqCurrentLevel2 = null;

var currentDeviceId = 0;
var currentDeviceName = '';
var currentCalculateType = 0;

var statTabs = null;
var resultTabs = null;
var deviceGrid = null;

var isInitializing = true;

//---------- 全局选中状态 ----------
var _hqRestoreState = null;              // 在 initHistoryQueryPage 中赋值
var _hqSuppressSave = { value: true };   // 对象包装

// ---------- 统计 Tab 静态映射 ----------
var STAT_TAB_INFO = {
    'FESdiagramResult': {
        api:   '/realTimeMonitoringController/getRealTimeMonitoringFESDiagramResultStatData',
        divId: 'pieChart_FESdiagramResult'
    },
    'CommStatus': {
        api:   '/realTimeMonitoringController/getRealTimeMonitoringCommStatusStatData',
        divId: 'pieChart_CommStatus'
    },
    'RunStatus': {
        api:   '/realTimeMonitoringController/getRealTimeMonitoringRunStatusStatData',
        divId: 'pieChart_RunStatus'
    },
    'NumStatus': {
        api:   '/realTimeMonitoringController/getRealTimeMonitoringNumStatusStatData',
        divId: 'pieChart_NumStatus'
    }
};

// ---------- 平铺图形配置 ----------
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
var defaultGraghSize = (typeof _defaultGraghSize !== 'undefined') ? _defaultGraghSize : 20;
var _tiledTotalPages = {};
var _tiledScrollHandlers = {};
var _tiledTabsResizeObserver = null;

var _overlayGridReault = {};
var _overlayPendingTimer = null;
var _overlaySkipPending = false;

// ================================================================
// 页面初始化
// ================================================================
function initHistoryQueryPage() {
    try {
        if (window.parent && window.parent.tabInfo) {
            _hqTabInfo = window.parent.tabInfo;
        }
    } catch (e) {
        console.warn('无法获取 tabInfo', e);
    }
    
    // ★ 初始化全局恢复状态
    _hqRestoreState = createRestoreState();
    _hqSuppressSave = { value: true };

    statTabs   = mini.get('statTabs');
    resultTabs = mini.get('resultTabs');
    deviceGrid = mini.get('deviceGrid');

    if (deviceGrid && typeof _defaultPageSize !== 'undefined' && _defaultPageSize) {
        deviceGrid.setPageSize(parseInt(_defaultPageSize, 10));
    }

    // 国际化
    initHistoryQueryI18n();

    // 解析三个 result tab body（静态 HTML 里的控件）
    initResultTabBodies();

    // 一级标签
    buildHqLevel1Tabs();

    // 监听消息
    initHistoryQueryMessageListener();
    
 // ★ 事件代理：设备名称悬停提示（与实时监控模块一致）
    document.addEventListener('mouseover', function (e) {
        var target = e.target.closest('.device-name-cell');
        if (target && !target._tipShown) {
            target._tipShown = true;
            handleDeviceNameCellMouseEnter(target, e);
        }
    });
    document.addEventListener('mouseout', function (e) {
        var target = e.target.closest('.device-name-cell');
        if (target) {
            target._tipShown = false;
            hideDeviceNameTip();
        }
    });

    
    setTimeout(function () {
    	isInitializing = false;
    }, 100);
    console.log('历史查询模块加载完成');
}

// ================================================================
// 国际化
// ================================================================
function initHistoryQueryI18n() {
    var R = _loginUserLanguageResource;

    // ============ 设备列表工具条 ============
    var btnRefresh = mini.get('btnRefresh');
    if (btnRefresh) btnRefresh.setText(R.refresh);

    var exportBtn = mini.get('exportHistoryQueryDeviceListBtn');
    if (exportBtn) exportBtn.setText(R.exportData);

    var deviceCombo = mini.get('deviceCombo');
    if (deviceCombo) deviceCombo.setEmptyText('--' + R.all + '--');

    // ============ 统计 tab 标题 ============
    setHqTabTitleByName('statTabs', 'FESdiagramResult', R.workType);
    setHqTabTitleByName('statTabs', 'CommStatus',       R.commStatus);
    setHqTabTitleByName('statTabs', 'RunStatus',        R.runStatus);
    setHqTabTitleByName('statTabs', 'NumStatus',        R.numStatus);

    // ============ result tab 标题 ============
    setHqTabTitleByName('resultTabs', 'TrendCurve',     R.trendCurve);
    setHqTabTitleByName('resultTabs', 'TiledDiagram',   R.tiledDiagram);
    setHqTabTitleByName('resultTabs', 'DiagramOverlay', R.diagramOverlay);

    // ============ 平铺内部三个子 tab 标题 ============
    setHqTabTitleByName('tiledTabs', 'FSDiagram', R.FSDiagram);
    setHqTabTitleByName('tiledTabs', 'PSDiagram', R.PSDiagram);
    setHqTabTitleByName('tiledTabs', 'ISDiagram', R.ISDiagram);

    // ============ 趋势曲线工具条 ============
    setHqText('trendRangeLabel',       R.range + '：');
    setHqText('trendTimeToLabel',      R.timeTo + '：');
    setHqText('trendTimeRangeLabel',   R.timeRange + '：');
    setHqText('trendChkAllLabel',      R.all);
    setHqText('vacuateCountText',      R.vacuateCount);
    setHqText('totalCountText',        R.totalCount);

    var trendSearchBtn = mini.get('trendSearchBtn');
    if (trendSearchBtn) trendSearchBtn.setText(R.search);
    var trendExportBtn = mini.get('trendExportBtn');
    if (trendExportBtn) trendExportBtn.setText(R.exportData);

    // ============ 平铺图形工具条 ============
    setHqText('tiledRangeLabel',       R.range + '：');
    setHqText('tiledTimeToLabel',      R.timeTo + '：');
    setHqText('tiledWorkTypeLabel',    R.WellFSDiagramWorkType + '：');
    setHqText('tiledTimeRangeLabel',   R.timeRange + '：');
    setHqText('tiledChkAllLabel',      R.all);
    setHqText('tiledTotalCountText',   R.totalCount);

    var tiledSearchBtn = mini.get('tiledSearchBtn');
    if (tiledSearchBtn) tiledSearchBtn.setText(R.search);
    var tiledExportBtn = mini.get('tiledExportBtn');
    if (tiledExportBtn) tiledExportBtn.setText(R.exportData);

    // 平铺工况下拉列头
    var tiledCombo = mini.get('tiledWorkTypeCombo');
    if (tiledCombo) {
        tiledCombo.setColumns([
            { header: R.WellFSDiagramWorkType, field: 'resultName',
              headerAlign: 'left', align: 'left', width: '60%' },
            { header: R.totalCount, field: 'count',
              headerAlign: 'left', align: 'left', width: '40%' }
        ]);
    }

    // ============ 叠加图形工具条 ============
    setHqText('overlayRangeLabel',       R.range + '：');
    setHqText('overlayTimeToLabel',      R.timeTo + '：');
    setHqText('overlayWorkTypeLabel',    R.WellFSDiagramWorkType + '：');
    setHqText('overlayTimeRangeLabel',   R.timeRange + '：');
    setHqText('overlayChkAllLabel',      R.all);
    setHqText('overlayVacuateCountText', R.vacuateCount);
    setHqText('overlayTotalCountText',   R.totalCount);

    var overlaySearchBtn = mini.get('overlaySearchBtn');
    if (overlaySearchBtn) overlaySearchBtn.setText(R.search);
    var overlayExportBtn = mini.get('overlayExportBtn');
    if (overlayExportBtn) overlayExportBtn.setText(R.exportData);

    var overlayCombo = mini.get('overlayWorkTypeCombo');
    if (overlayCombo) {
        overlayCombo.setColumns([
            { header: R.WellFSDiagramWorkType, field: 'resultName',
              headerAlign: 'left', align: 'left', width: '60%' },
            { header: R.totalCount, field: 'count',
              headerAlign: 'left', align: 'left', width: '40%' }
        ]);
    }

    // ============ 统计饼图容器 min-height ============
    var pieMinH = (typeof otherCardMinHeight !== 'undefined' && otherCardMinHeight)
        ? otherCardMinHeight : 100;
    ['pieChart_FESdiagramResult', 'pieChart_CommStatus',
     'pieChart_RunStatus', 'pieChart_NumStatus'].forEach(function (id) {
        var el = document.getElementById(id);
        if (el) el.style.minHeight = pieMinH + 'px';
    });
}

function setHqTabTitleByName(tabsId, name, title) {
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

function setHqText(id, text) {
    var el = document.getElementById(id);
    if (el && text != null) el.textContent = text;
}

// ================================================================
// 消息监听
// ================================================================
function initHistoryQueryMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        switch (message.action) {
            case 'refresh':
            	handleHqRefreshFromParent(message);
                break;
            case 'refreshCurve':
                doQuery();
                break;
        }
    });
}

/**
 * 父窗口发出 refresh（组织切换 / 从其他模块切回本模块）时的处理
 */
function handleHqRefreshFromParent(message) {
    console.log('历史查询模块收到刷新指令, orgId:', message.orgId);

    clearStatFilters();
    var combo = mini.get('deviceCombo');
    if (combo) { combo.setValue(''); combo.setText(''); }

    var gsel = loadGlobalSelection();
    var curType = _hqCurrentLevel2 ? String(_hqCurrentLevel2.deviceTypeId) : '';

    // 情况 1：二级（或一级）标签不一致
    if (gsel.deviceTypeId && curType !== String(gsel.deviceTypeId)) {
        var target = findTargetLevels(_hqLevel1Data, gsel.deviceTypeId);
        if (target) {
            _hqRestoreState.deviceId    = gsel.deviceId || '';
            _hqRestoreState.level2Index = target.level2Index;
            selectHqLevel1(target.level1Index);
            return;
        }
    }

    // 情况 2 & 3：统一重新加载设备列表
    if (gsel.deviceId) {
        _hqRestoreState.deviceId = String(gsel.deviceId);
    }
    refreshDeviceList();
}

// ================================================================
// 一级标签
// ================================================================
function buildHqLevel1Tabs() {
    var container = document.getElementById('level1Footer');
    if (!container) return;
    container.innerHTML = '';

    if (!_hqTabInfo || !_hqTabInfo.children || _hqTabInfo.children.length === 0) {
        container.innerHTML = '<span class="loading-tip">'
            + _loginUserLanguageResource.emptyMsg + '</span>';
        return;
    }

    _hqLevel1Data = _hqTabInfo.children;

    for (var i = 0; i < _hqLevel1Data.length; i++) {
        (function (idx) {
            var item = _hqLevel1Data[idx];
            var span = document.createElement('span');
            span.className = 'tab-item' + (idx === 0 ? ' active' : '');
            span.dataset.index = idx;
            span.dataset.deviceTypeId = item.deviceTypeId;
            span.textContent = item.text;
            span.onclick = function () {
                selectHqLevel1(parseInt(this.dataset.index));
            };
            container.appendChild(span);
        })(i);
    }

    if (_hqLevel1Data.length > 0) {
        // ★ 使用恢复状态决定起始一级
        var startIndex = 0;
        if (_hqRestoreState.deviceTypeId) {
            var t = findTargetLevels(_hqLevel1Data, _hqRestoreState.deviceTypeId);
            if (t) {
                _hqRestoreState.level1Index = t.level1Index;
                _hqRestoreState.level2Index = t.level2Index;
                startIndex = t.level1Index;
            }
            _hqRestoreState.deviceTypeId = '';
        }
        selectHqLevel1(startIndex);
    }
}

function selectHqLevel1(index) {
    if (index < 0 || index >= _hqLevel1Data.length) return;
    var item = _hqLevel1Data[index];
    _hqCurrentLevel1 = item;

    var container = document.getElementById('level1Footer');
    var tabs = container.querySelectorAll('.tab-item');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    buildHqLevel2Tabs(item);
}

// ================================================================
// 二级标签
// ================================================================
function buildHqLevel2Tabs(parentItem) {
    var container = document.getElementById('level2Sidebar');
    if (!container) return;
    container.innerHTML = '';

    var children = parentItem.children || [];

    // ============ 一级无子标签 ============
    if (!children || children.length === 0) {
        container.classList.add('hidden');
        _hqLevel2Data = [];

        _hqCurrentLevel2 = {
            text: parentItem.text,
            deviceTypeId: parentItem.deviceTypeId,
            isAll: false,
            isLevel1Direct: true
        };
        _hqRestoreState.level2Index = -1;

        loadAllData(_hqCurrentLevel2);
        // ★ 保留设备选中
        saveGlobalSelection(_hqCurrentLevel2.deviceTypeId,
            _hqRestoreState.deviceId || '',
            { suppress: _hqSuppressSave.value });
        return;
    }

    // ============ 一级有子标签 ============
    container.classList.remove('hidden');
    _hqLevel2Data = children;

    var allTabs = [];
    // ★ 只有多个二级时才生成"全部"tab
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

    for (var i = 0; i < allTabs.length; i++) {
        (function (idx, item) {
            var div = document.createElement('div');
            div.className = 'tab-item';
            div.dataset.index = idx;
            div.dataset.deviceTypeId = item.deviceTypeId;
            div.dataset.isAll = item.isAll || false;
            div.textContent = item.text;
            div.title = item.text;
            div.onclick = function () {
            	selectHqLevel2(parseInt(this.dataset.index));   // ← 错误，应为 selectHqLevel2
            };
            container.appendChild(div);
        })(i, allTabs[i]);
    }

    var defaultIndex = 0;
    if (_hqRestoreState.level2Index >= 0 && _hqRestoreState.level2Index < allTabs.length) {
        defaultIndex = _hqRestoreState.level2Index;
    }
    _hqRestoreState.level2Index = -1;

    var tabEls = container.querySelectorAll('.tab-item');
    for (var t = 0; t < tabEls.length; t++) {
        tabEls[t].className = 'tab-item' + (t === defaultIndex ? ' active' : '');
    }

    if (allTabs.length > 0) {
        _hqCurrentLevel2 = allTabs[defaultIndex];
        loadAllData(_hqCurrentLevel2);
    }
}

function selectHqLevel2(index) {
    var container = document.getElementById('level2Sidebar');
    var tabs = container.querySelectorAll('.tab-item');

    var allTabs = [];
    if (_hqLevel2Data.length > 1) {
        var allIds = [];
        for (var i = 0; i < _hqLevel2Data.length; i++) {
            if (_hqLevel2Data[i].deviceTypeId) allIds.push(_hqLevel2Data[i].deviceTypeId);
        }
        allTabs.push({
            text: _loginUserLanguageResource.all,
            deviceTypeId: allIds.join(','),
            isAll: true
        });
    }
    for (var i = 0; i < _hqLevel2Data.length; i++) allTabs.push(_hqLevel2Data[i]);
    
    allTabs[0].deviceTypeId = allIds.join(',');

    if (index < 0 || index >= allTabs.length) return;

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    _hqCurrentLevel2 = allTabs[index];
    loadAllData(_hqCurrentLevel2);

    // ★ 用恢复候选 deviceId 而非清空
    saveGlobalSelection(_hqCurrentLevel2.deviceTypeId,
        _hqRestoreState.deviceId || '',
        { suppress: _hqSuppressSave.value });
}

// ================================================================
// 加载数据
// ================================================================
function loadAllData(level2Item) {
    if (!level2Item) return;

    var deviceTypeId = level2Item.deviceTypeId || '0';
    var orgId = window.parent && window.parent.mini
        ? window.parent.mini.get('leftOrg_Id').getValue() : '';

    resetAllPanels();

    clearStatFilters();
    refreshDeviceList();
    loadStatCharts(deviceTypeId, orgId);
}

function resetAllPanels() {
    currentDeviceId = 0;
    currentDeviceName = '';
    currentCalculateType = 0;

    clearResultTabContents();
}

function clearResultTabContents() {
    // 清空曲线容器
    var curveContainer = document.getElementById('historyCurveContainer');
    if (curveContainer) curveContainer.innerHTML = '';

    var historyGrid = mini.get('historyDataGrid');
    if (historyGrid) historyGrid.setData([]);

    // 清空平铺容器
    ['fsTiledContainer', 'psTiledContainer', 'isTiledContainer'].forEach(function (id) {
        var el = document.getElementById(id);
        if (el) el.innerHTML = '';
    });

    // 清空叠加图表
    ['overlayFsChart', 'overlayPowerChart', 'overlayCurrentChart'].forEach(function (id) {
        var el = document.getElementById(id);
        if (el) el.innerHTML = '';
    });
    var overlayGrid = mini.get('overlayDataGrid');
    if (overlayGrid) overlayGrid.setData([]);
}

function hideAllPanelsWhenNoDevice() {
    var splitterLeft = mini.get('hqLeftVerticalSplitter');
    if (splitterLeft) splitterLeft.hidePane(2);

    var splitterMain = mini.get('hqMainSplitter');
    if (splitterMain) splitterMain.hidePane(2);
}

// ================================================================
// 设备表格
// ================================================================
function onDeviceGridBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize = params.pageSize || (typeof _defaultPageSize !== 'undefined' ? _defaultPageSize : 20);
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var leftOrgId = window.parent && window.parent.mini
        ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';

    var combo = mini.get('deviceCombo');
    params.deviceName = combo ? combo.getValue() : '';

    var getField = function (id) {
        var el = document.getElementById(id);
        return el ? el.value : '';
    };
    params.FESdiagramResultStatValue = getField('HistoryQueryStatSelectFESdiagramResult_Id');
    params.commStatusStatValue       = getField('HistoryQueryStatSelectCommStatus_Id');
    params.runStatusStatValue        = getField('HistoryQueryStatSelectRunStatus_Id');
    params.numStatusStatValue        = getField('HistoryQueryStatSelectNumStatus_Id');
    params.deviceTypeStatValue       = getField('HistoryQueryStatSelectDeviceType_Id');
}

function onDeviceGridLoad(e) {
    var grid = e.sender;
    var result = e.result;

    if (result) {
        if (result.AlarmShowStyle && window.parent && window.parent.mini) {
            var alarmInput = window.parent.mini.get('AlarmShowStyle_Id');
            if (alarmInput) alarmInput.setValue(JSON.stringify(result.AlarmShowStyle));
        }

        if (result.columns && result.columns.length > 0) {
            var columns = buildGridColumns(result.columns);
            var columnStrInput = document.getElementById('HistoryQueryWellListColumnStr_Id');
            if (columnStrInput) columnStrInput.value = JSON.stringify(result.columns);

            setTimeout(function () {
                grid.setColumns(columns);
                grid.frozenColumns(0, 1);
                grid.doLayout();
            }, 50);
        }
    }

    var data = grid.getData();
    if (data && data.length > 0) {
        // ★ 尝试恢复全局设备选中
        restoreDeviceSelection(grid, _hqRestoreState, _hqSuppressSave, null);
    } else {
        currentDeviceId = 0;
        currentDeviceName = '';
        currentCalculateType = 0;
        resetAllPanels();
        hideAllPanelsWhenNoDevice();

        if (_hqSuppressSave.value) {
            setTimeout(function () { _hqSuppressSave.value = false; }, 100);
        }
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
        var counts = { 100: 0, 200: 0, 300: 0 };
        for (var i = 0; i < alarmInfo.length; i++) {
            var level = alarmInfo[i].alarmLevel;
            if (level === 100 || level === 200 || level === 300) {
                counts[level] = (counts[level] || 0) + 1;
            }
        }
        var badges = '';
        if (counts[100] > 0) badges += createAlarmBadge(counts[100], Data.FirstLevel ? Data.FirstLevel.Color : 'dc2828');
        if (counts[200] > 0) badges += createAlarmBadge(counts[200], Data.SecondLevel ? Data.SecondLevel.Color : 'f09614');
        if (counts[300] > 0) badges += createAlarmBadge(counts[300], Data.ThirdLevel ? Data.ThirdLevel.Color : 'fae600');

        // ★ 加上 data-alarm / data-name，供悬停提示读取
        var deviceName = value || '';
        var alarmData = JSON.stringify(counts);
        e.cellHtml = '<span class="device-name-cell" data-alarm=\''
            + alarmData + '\' data-name="' + deviceName
            + '" style="white-space:nowrap;">' + badges + deviceName + '</span>';
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
    if (fieldUpper !== 'ID' && fieldUpper !== 'DEVICENAME' &&
        fieldUpper !== 'COMMSTATUSNAME' && fieldUpper !== 'RUNSTATUSNAME') {
        var alarmLevel = 0;
        for (var j = 0; j < alarmInfo.length; j++) {
            if (alarmInfo[j].item && alarmInfo[j].item.toUpperCase() === fieldUpper) {
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

function refreshDeviceList() {
    var grid = mini.get('deviceGrid');
    if (grid){
    	if (!grid.getUrl()) grid.setUrl(HQ_URLS.deviceGrid);
    	grid.load();
    }
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
        refreshHistoryTabs(selected);
        // ★ 记录为恢复候选
        _hqRestoreState.deviceId = String(selected.id);

        // ★ 保存全局状态
        saveGlobalSelection(
            _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '',
            selected.id,
            { suppress: _hqSuppressSave.value }
        );
    }
}

// ================================================================
// 统计饼图（静态 tab 显隐）
// ================================================================
function loadStatCharts(deviceTypeId, orgId) {
    clearStatFilters();

    var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
    var cfg = projectTabConfig.DeviceHistoryQuery || {};
    var config = {
        FESdiagramResult: cfg.FESDiagramStatPie === true,
        CommStatus:       cfg.CommStatusStatPie === true,
        RunStatus:        cfg.RunStatusStatPie === true,
        NumStatus:        cfg.NumStatusStatPie === true
    };

    updateStatTabs(config, deviceTypeId, orgId);
}

function updateStatTabs(config, deviceTypeId, orgId) {
    statTabs = mini.get('statTabs');
    if (!statTabs) return;

    var paramDeviceType = deviceTypeId || '0';

    var order = ['FESdiagramResult', 'CommStatus', 'RunStatus', 'NumStatus'];
    var tabs = statTabs.getTabs();
    var visibleCount = 0;
    var firstVisibleTab = null;

    for (var i = 0; i < order.length; i++) {
        var key = order[i];
        var isVisible = config[key] === true;
        for (var j = 0; j < tabs.length; j++) {
            if (tabs[j].name === key) {
                statTabs.updateTab(tabs[j], { visible: isVisible });
                if (isVisible) {
                    visibleCount++;
                    if (!firstVisibleTab) firstVisibleTab = tabs[j];
                }
                break;
            }
        }
    }

    var splitterLeft = mini.get('hqLeftVerticalSplitter');
    if (splitterLeft) {
        if (visibleCount === 0) {
            clearAllStatCharts();
            splitterLeft.hidePane(2);
            return;
        } else {
            splitterLeft.showPane(2);
        }
    }

    var currentActive = statTabs.getActiveTab();
    var currentName = currentActive ? currentActive.name : '';

    if (currentName && config[currentName] === true) {
        loadStatData(currentActive, paramDeviceType, orgId);
    } else if (firstVisibleTab) {
        statTabs.activeTab(firstVisibleTab);
        // ★ 初始化期间主动补一次，避免被 isInitializing 拦截
        if (isInitializing) {
            loadStatData(firstVisibleTab, paramDeviceType, orgId);
        }
    }
}

function clearAllStatCharts() {
    for (var key in STAT_TAB_INFO) {
        var container = document.getElementById(STAT_TAB_INFO[key].divId);
        if (container) {
            destroyPieChart(container);
            container.innerHTML = '';
        }
    }
}

function onStatTabChanged(e) {
	if(isInitializing) return;
    var tab = e.tab;
    if (!tab) return;

    clearStatFilters();

    var deviceCombo = mini.get('deviceCombo');
    if (deviceCombo) {
        deviceCombo.setValue('');
        deviceCombo.setText('');
    }

    var deviceTypeId = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';
    var orgId = window.parent && window.parent.mini
        ? window.parent.mini.get('leftOrg_Id').getValue() : '';

    loadStatData(tab, deviceTypeId, orgId);
    refreshDeviceList();
}

function loadStatData(tab, deviceTypeId, orgId) {
    if (!tab || !tab.name) return;
    var info = STAT_TAB_INFO[tab.name];
    if (!info) return;

    var divId = info.divId;
    var container = document.getElementById(divId);
    if (container) {
        destroyPieChart(container);
        container.innerHTML = '';
    }

    mini.mask({
        el: divId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + info.api,
        type: 'POST',
        data: { orgId: orgId || '', deviceType: deviceTypeId || '0' },
        dataType: 'text',
        timeout: 10000,
        success: function (responseText) {
            mini.unmask(divId);
            try {
                var fixedJson = responseText
                    .replace(/(\{|\,)\s*([a-zA-Z_][a-zA-Z0-9_]*)\s*(\:)/g, '$1"$2"$3')
                    .replace(/'([^']*)'/g, '"$1"');
                var result = JSON.parse(fixedJson);

                if (result.AlarmShowStyle && window.parent && window.parent.mini) {
                    var alarmInput = window.parent.mini.get('AlarmShowStyle_Id');
                    if (alarmInput) alarmInput.setValue(JSON.stringify(result.AlarmShowStyle));
                }
                var data = extractPieData(result, tab.name, result.AlarmShowStyle);
                renderPieChart(divId, data, tab.title, tab.name);
            } catch (e) {
                console.error('JSON解析失败:', e);
                if (container) {
                    container.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">数据格式错误</div>';
                }
            }
        },
        error: function () {
            mini.unmask(divId);
            if (container) {
                container.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">'
                    + _loginUserLanguageResource.requestFailed + '</div>';
            }
        }
    });
}

function extractPieData(result, tabKey, alarmShowStyle) {
    if (!result) return [{ name: _loginUserLanguageResource.emptyMsg, y: 1 }];
    var list = result.totalRoot || [];
    var data = [];
    var comm = (alarmShowStyle && alarmShowStyle.Comm) || {};
    var run = (alarmShowStyle && alarmShowStyle.Run) || {};
    var dataStyle = (alarmShowStyle && alarmShowStyle.Data) || {};

    for (var i = 0; i < list.length; i++) {
        var item = list[i];
        if (item.itemCode === 'all' || item.count <= 0) continue;
        var point = { name: item.item || item.text, y: item.count };

        if (tabKey === 'CommStatus') {
            if (item.itemCode === 'online')        point.color = '#' + (comm.online ? comm.online.Color : '52c41a');
            else if (item.itemCode === 'goOnline') point.color = '#' + (comm.goOnline ? comm.goOnline.Color : 'faad14');
            else if (item.itemCode === 'offline')  point.color = '#' + (comm.offline ? comm.offline.Color : 'ff4d4f');
        } else if (tabKey === 'RunStatus') {
            if (item.itemCode === 'run')           point.color = '#' + (run.run ? run.run.Color : '52c41a');
            else if (item.itemCode === 'stop')     point.color = '#' + (run.stop ? run.stop.Color : 'ff4d4f');
            else if (item.itemCode === 'noData')   point.color = '#' + (run.noData ? run.noData.Color : '999');
            else if (item.itemCode === 'goOnline') point.color = '#' + (comm.goOnline ? comm.goOnline.Color : 'faad14');
            else if (item.itemCode === 'offline')  point.color = '#' + (comm.offline ? comm.offline.Color : 'ff4d4f');
        } else if (tabKey === 'NumStatus') {
            var level = item.level;
            if (level === 0)        point.color = '#' + (dataStyle.Normal ? dataStyle.Normal.BackgroundColor : 'FFFFFF');
            else if (level === 100) point.color = '#' + (dataStyle.FirstLevel ? dataStyle.FirstLevel.BackgroundColor : 'DC2828');
            else if (level === 200) point.color = '#' + (dataStyle.SecondLevel ? dataStyle.SecondLevel.BackgroundColor : 'F09614');
            else if (level === 300) point.color = '#' + (dataStyle.ThirdLevel ? dataStyle.ThirdLevel.BackgroundColor : 'FAE600');
            point.level = level;
        }
        data.push(point);
    }
    return data.length > 0 ? data : [{ name: _loginUserLanguageResource.emptyMsg, y: 1 }];
}

function renderPieChart(divId, data, title, tabKey) {
    var container = document.getElementById(divId);
    if (!container) return;
    destroyPieChart(container);

    container._pieData = data;
    container._pieTitle = title;
    container._pieTabKey = tabKey;

    if (data.length === 1 && data[0].name === _loginUserLanguageResource.emptyMsg) {
        container.innerHTML = '<div class="loading-placeholder">'
            + _loginUserLanguageResource.emptyMsg + '</div>';
        return;
    }

    var chart = createPieChartInstance(divId, data, title, tabKey);
    container._chart = chart;

    if (window.ResizeObserver) {
        var observer = new ResizeObserver(function () {
            if (container._resizeTimer) clearTimeout(container._resizeTimer);
            container._resizeTimer = setTimeout(function () {
                recreatePieChart(container);
                container._resizeTimer = null;
            }, 200);
        });
        observer.observe(container);
        container._resizeObserver = observer;
    }
}

function createPieChartInstance(divId, data, title, tabKey) {
    return Highcharts.chart(divId, {
        chart: {
            type: 'pie',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            zooming: { mouseWheel: { enabled: false } }
        },
        credits: { enabled: false },
        title: { text: title || '', style: { fontSize: '13px' } },
        tooltip: {
            pointFormat: _loginUserLanguageResource.deviceCount + ': <b>{point.y}</b> '
                + _loginUserLanguageResource.proportion + ': <b>{point.percentage:.1f}%</b>'
        },
        legend: {
            align: 'center', verticalAlign: 'bottom',
            layout: 'horizontal'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true, cursor: 'pointer',
                dataLabels: { enabled: true, format: '<b>{point.name}</b>: {point.y}' },
                showInLegend: true,
                events: {
                    click: function (e) {
                        handlePieClick(e, tabKey);
                    }
                }
            }
        },
        exporting: { enabled: true, filename: title, fallbackToExportServer: false },
        series: [{ type: 'pie', name: _loginUserLanguageResource.deviceCount, data: data }]
    });
}

function handlePieClick(e, tabKey) {
    var fieldId = '';
    if (tabKey === 'FESdiagramResult') fieldId = 'HistoryQueryStatSelectFESdiagramResult_Id';
    else if (tabKey === 'CommStatus') fieldId = 'HistoryQueryStatSelectCommStatus_Id';
    else if (tabKey === 'RunStatus') fieldId = 'HistoryQueryStatSelectRunStatus_Id';
    else if (tabKey === 'NumStatus') fieldId = 'HistoryQueryStatSelectNumStatus_Id';
    if (fieldId) {
        var input = document.getElementById(fieldId);
        if (input) {
            if (e.point.selected) input.value = '';
            else input.value = (tabKey === 'NumStatus'
                ? (e.point.level !== undefined ? e.point.level : '')
                : e.point.name);
        }
    }
    var combo = mini.get('deviceCombo');
    if (combo) combo.setValue('');
    refreshDeviceList();
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
    container._chart = createPieChartInstance(divId, data, title, tabKey);
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
    var ids = [
        'HistoryQueryStatSelectFESdiagramResult_Id',
        'HistoryQueryStatSelectCommStatus_Id',
        'HistoryQueryStatSelectRunStatus_Id',
        'HistoryQueryStatSelectNumStatus_Id',
        'HistoryQueryStatSelectDeviceType_Id'
    ];
    for (var i = 0; i < ids.length; i++) {
        var el = document.getElementById(ids[i]);
        if (el) el.value = '';
    }
}

// ================================================================
// 结果 Tab 显隐（静态 tab）
// ================================================================
function refreshHistoryTabs(selected) {
    if (!selected) return;

    var deviceInfo = {};
    try {
        if (window.parent && typeof window.parent.getDeviceTabInstanceInfoByDeviceId === 'function') {
            deviceInfo = window.parent.getDeviceTabInstanceInfoByDeviceId(selected.id);
        } else {
            deviceInfo = { config: { DeviceHistoryQuery: {} } };
        }
    } catch (e) {
        deviceInfo = { config: { DeviceHistoryQuery: {} } };
    }

    var config = deviceInfo.config || {};
    var dhq = config.DeviceHistoryQuery || {};
    var allowedKeys = [];
    if (dhq.TrendCurve === true)     allowedKeys.push('TrendCurve');
    if (dhq.TiledDiagram === true)   allowedKeys.push('TiledDiagram');
    if (dhq.DiagramOverlay === true) allowedKeys.push('DiagramOverlay');

    var tabs = mini.get('resultTabs');
    if (!tabs) return;

    var allTabs = tabs.getTabs();
    var visibleCount = 0;
    var firstVisible = null;

    for (var i = 0; i < allTabs.length; i++) {
        var isVisible = allowedKeys.indexOf(allTabs[i].name) !== -1;
        tabs.updateTab(allTabs[i], { visible: isVisible });
        if (isVisible) {
            visibleCount++;
            if (!firstVisible) firstVisible = allTabs[i];
        }
    }

    var splitterMain = mini.get('hqMainSplitter');

    if (visibleCount === 0) {
        if (splitterMain) splitterMain.hidePane(2);
        return;
    }

    if (splitterMain) splitterMain.showPane(2);

    var currentActive = tabs.getActiveTab();
    var currentName = currentActive ? currentActive.name : '';

    if (currentName && allowedKeys.indexOf(currentName) !== -1) {
        window._isResetting = true;
        doQuery();
        window._isResetting = false;
    } else if (firstVisible) {
        tabs.activeTab(firstVisible);
    }
}

// ================================================================
// Result tab body 初始化（只 parse，静态 HTML 已在 JSP 中定义）
// ================================================================
function initResultTabBodies() {
    var trendEl = document.getElementById('trendCurveBodyContainer');
    if (trendEl) mini.parse(trendEl);

    var tiledEl = document.getElementById('tiledDiagramBodyContainer');
    if (tiledEl) mini.parse(tiledEl);

    var overlayEl = document.getElementById('diagramOverlayBodyContainer');
    if (overlayEl) mini.parse(overlayEl);

    // 按项目全局参数更新历史数据表分页
    var historyGrid = mini.get('historyDataGrid');
    if (historyGrid && typeof _defaultPageSize !== 'undefined' && _defaultPageSize) {
        historyGrid.setPageSize(parseInt(_defaultPageSize, 10));
    }
}

// ================================================================
// 查询分发
// ================================================================
function doQuery() {
    if (!currentDeviceId) return;

    var tabs = mini.get('resultTabs');
    if (!tabs) return;
    var active = tabs.getActiveTab();
    if (!active) return;

    var name = active.name;
    if (name === 'TrendCurve') {
        loadHistoryCurve();
        loadHistoryDataGrid();
    } else if (name === 'TiledDiagram') {
        var combo = mini.get('tiledWorkTypeCombo');
        if (combo) {
            combo.setValue('');
            if(!combo.getUrl()){
            	combo.setUrl(HQ_URLS.workTypeStat);
            }
            combo.load(combo.getUrl());
        }
    } else if (name === 'DiagramOverlay') {
        var combo2 = mini.get('overlayWorkTypeCombo');
        if (combo2) {
            combo2.setValue('');
            if(!combo2.getUrl()){
            	combo2.setUrl(HQ_URLS.workTypeStat);
            }
            combo2.load(combo2.getUrl());
        }
    }
}

function onResultTabChanged(e) {
	if(isInitializing) return;
    if (window._isResetting) return;
    var tab = e.tab;
    if (!tab) return;

    if (currentDeviceId) {
        doQuery();
        if (tab.name === 'TiledDiagram') {
            setTimeout(function () {
                panelResizeObserver("tiledTabs", function () { resizeTiledCharts(); });
            }, 100);
        }
    }
}

// ================================================================
// 重置各 tab 的查询参数
// ================================================================
function resetTrendCurveControls() {
    var startDate = mini.get('startDate');
    var endDate = mini.get('endDate');
    if (startDate) startDate.setValue('');
    if (endDate) endDate.setValue('');
}

function resetTiledDiagramControls() {
    var startDate = mini.get('tiledStartDate');
    var endDate = mini.get('tiledEndDate');
    if (startDate) startDate.setValue('');
    if (endDate) endDate.setValue('');

    var chkAll = document.getElementById('tiledChkAll');
    if (chkAll) {
        chkAll.checked = true;
        var checkboxes = document.querySelectorAll('[id^="tiledChk"]');
        checkboxes.forEach(function (chk) {
            if (chk.id !== 'tiledChkAll') chk.checked = true;
        });
    }
    var combo = mini.get('tiledWorkTypeCombo');
    if (combo) combo.setValue('');
}

function resetDiagramOverlayControls() {
    var startDate = mini.get('overlayStartDate');
    var endDate = mini.get('overlayEndDate');
    if (startDate) startDate.setValue('');
    if (endDate) endDate.setValue('');

    var chkAll = document.getElementById('overlayChkAll');
    if (chkAll) {
        chkAll.checked = true;
        var checkboxes = document.querySelectorAll('[id^="overlayChk"]');
        checkboxes.forEach(function (chk) {
            if (chk.id !== 'overlayChkAll') chk.checked = true;
        });
    }
    var combo = mini.get('overlayWorkTypeCombo');
    if (combo) combo.setValue('');
}

// ================================================================
// 趋势曲线
// ================================================================
function loadHistoryCurve() {
    if (!currentDeviceId) return;

    var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
    var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
    var hours = getHistoryQueryHours();
    var deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';

    var container = document.getElementById('historyCurveContainer');
    if (!container) return;
    container.innerHTML = '';

    mini.mask({
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
        success: function (result) {
            mini.unmask('historyCurveContainer');

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

            var tickInterval = Math.floor(data.length / 10) + 1;
            if (tickInterval < 100) tickInterval = 100;

            var title = result.deviceName + (loginUserLanguage.toUpperCase() == 'ZH_CN' ? "" : " ")
                + _loginUserLanguageResourceFirstLower.trendCurve;
            var xTitle = _loginUserLanguageResource.acqTime;
            var legendName = result.curveItems;
            var legendCode = result.curveItemCodes;
            var curveConf = result.curveConf;

            var color = [], color_l = [], color_r = [], color_all = [];
            for (var i = 0; i < curveConf.length; i++) {
                var singleColor = defaultColors[i % defaultColors.length];
                if (curveConf[i].color != '') singleColor = '#' + curveConf[i].color;
                color.push(singleColor);
                if (curveConf[i].yAxisOpposite) color_r.push(singleColor);
                else color_l.push(singleColor);
            }

            var series = [], series_l = [], series_r = [];
            var yAxis = [], yAxis_l = [], yAxis_r = [];

            for (var i = 0; i < legendName.length; i++) {
                var maxValue = null, minValue = null;
                var allPositive = true, allNegative = true;

                var singleSeries = {};
                singleSeries.name = legendName[i];
                singleSeries.code = legendCode[i];
                singleSeries.type = 'spline';
                singleSeries.lineWidth = curveConf[i].lineWidth;
                singleSeries.dashStyle = curveConf[i].dashStyle;
                singleSeries.marker = { enabled: false };
                singleSeries.yAxis = i;
                singleSeries.data = [];
                for (var j = 0; j < data.length; j++) {
                    var pointData = [];
                    pointData.push(Date.parse(data[j].acqTime.replace(/-/g, '/')));
                    pointData.push(data[j].data[i]);

                    if (parseFloat(data[j].data[i]) < 0) allPositive = false;
                    else if (parseFloat(data[j].data[i]) >= 0) allNegative = false;

                    if (hiddenExceptionData) {
                        if (isNumber(data[j].data[i])) singleSeries.data.push(pointData);
                    } else {
                        singleSeries.data.push(pointData);
                    }
                }
                if (curveConf[i].yAxisOpposite) series_r.push(singleSeries);
                else series_l.push(singleSeries);

                var opposite = curveConf[i].yAxisOpposite;
                if (allNegative) maxValue = 0;
                else if (allPositive) minValue = 0;

                if (JSON.stringify(graphicSet) != "{}" && isNotVal(graphicSet.History)) {
                    for (var j = 0; j < graphicSet.History.length; j++) {
                        if (graphicSet.History[j].itemCode != undefined
                            && graphicSet.History[j].itemCode.toUpperCase() == result.curveItemCodes[i].toUpperCase()) {
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
                    title: { text: legendName[i], style: { color: color[i] } },
                    labels: { style: { color: color[i] } },
                    lineWidth: 1, tickWidth: 1, tickLength: 5,
                    opposite: opposite
                };
                if (curveConf[i].yAxisOpposite) yAxis_r.push(singleAxis);
                else yAxis_l.push(singleAxis);
            }

            for (var i = yAxis_l.length - 1; i >= 0; i--) yAxis.push(yAxis_l[i]);
            for (var i = 0; i < yAxis_r.length; i++) yAxis.push(yAxis_r[i]);

            for (var i = 0; i < series_l.length; i++) {
                series_l[i].yAxis = series_l.length - 1 - i;
                series.push(series_l[i]);
            }
            for (var i = 0; i < series_r.length; i++) {
                series_r[i].yAxis = series_l.length + i;
                series.push(series_r[i]);
            }

            for (var i = 0; i < color_l.length; i++) color_all.push(color_l[i]);
            for (var i = 0; i < color_r.length; i++) color_all.push(color_r[i]);

            initDeviceHistoryCurveChartFn(series, tickInterval, "historyCurveContainer",
                title, '', '', yAxis, color_all, true, timeFormat);
        },
        error: function () {
            mini.unmask('historyCurveContainer');
            container.innerHTML = '<div class="loading-placeholder error">'
                + _loginUserLanguageResource.requestFailed + '</div>';
        }
    });
}

function initDeviceHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle,
                                       yAxis, color, legend, timeFormat) {
    if ($("#" + divId).length === 0) return;
    new Highcharts.Chart({
        chart: {
            renderTo: divId, type: 'spline', animation: false,
            zoomType: 'xy', zooming: { mouseWheel: { enabled: false } }
        },
        time: { timezoneOffset: new Date().getTimezoneOffset() },
        credits: { enabled: false },
        title: { text: title, style: { fontSize: chartTitleFontSize || '14px' } },
        subtitle: { text: subtitle },
        colors: color,
        xAxis: {
            type: 'datetime',
            title: { text: xtitle },
            tickPixelInterval: 120,
            labels: {
                formatter: function () {
                    return this.axis.chart.time.dateFormat(timeFormat, this.value);
                },
                rotation: -45
            }
        },
        yAxis: yAxis,
        tooltip: {
            crosshairs: true, shared: true,
            style: { color: '#333', fontSize: '12px' }
        },
        exporting: {
            enabled: true, filename: title, fallbackToExportServer: false,
            buttons: {
                contextButton: {
                    menuItems: [
                        'viewFullscreen', 'printChart',
                        'downloadPNG', 'downloadJPEG', 'downloadSVG',
                        'downloadCSV', 'downloadXLS',
                        {
                            text: _loginUserLanguageResource.diagramSet,
                            onclick: function () { openCurveSetWindow(); }
                        }
                    ]
                }
            }
        },
        plotOptions: {
            spline: {
                lineWidth: 1,
                marker: { enabled: true, radius: 3 },
                shadow: true
            }
        },
        legend: {
            layout: 'horizontal', align: 'center', verticalAlign: 'bottom',
            enabled: legend !== false
        },
        series: series
    });
}

function openCurveSetWindow() {
    if (!currentDeviceId) {
        mini.alert(_loginUserLanguageResource.checkOne);
        return;
    }
    var params = {
        deviceId: currentDeviceId,
        deviceName: currentDeviceName,
        deviceType: _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0'
    };
    mini.open({
        title: _loginUserLanguageResource.historyDiagramSet,
        url: context + '/miniui-app/modules/historyQuery/historyCurveSet.jsp',
        width: '50%',
        height: '60%',
        modal: true,
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            iframe.contentWindow.setData(params);
            iframe.contentWindow._parentLoadHistoryCurve = loadHistoryCurve;
            iframe.contentWindow._parentShowAlert = window.showAlert;
        }
    });
}

function showAlert(message, title) {
    mini.alert(message, title);
}

// ================================================================
// 历史数据表格
// ================================================================
function loadHistoryDataGrid() {
    var grid = mini.get('historyDataGrid');
    if (!grid) return;
    if (!currentDeviceId) return;
    
    if(!grid.getUrl()){
    	grid.setUrl(HQ_URLS.historyDataGrid);
    }

    grid.load();
}

function onHistoryDataBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize = params.pageSize || 25;
    params.pageNum = pageIndex + 1;
    params.numPerPage = pageSize;
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    params.deviceId = currentDeviceId;
    params.deviceName = currentDeviceName;
    params.calculateType = currentCalculateType;
    params.deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';
    params.startDate = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
    params.endDate = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
    params.hours = getHistoryQueryHours();

    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';

    var grid = mini.get('historyDataGrid');
    params.totalCount = grid ? (grid.getTotalCount() || 0) : 0;
}

function onHistoryDataLoad(e) {
    var grid = e.sender,
        result = e.result;
    if (result && result.columns) {
        var cols = buildGridColumns(result.columns);

        var detailColumn = {
            field: 'details',
            header: _loginUserLanguageResource.details,
            width: 60, headerAlign: 'center', align: 'center'
        };
        cols.splice(1, 0, detailColumn);

        grid.setColumns(cols);
        document.getElementById('HistoryQueryDataColumnStr_Id').value = JSON.stringify(result.columns);

        var startDate = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        if (startDate == '' || null == startDate) mini.get('startDate').setValue(result.start_date);

        var endDate = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        if (endDate == '' || null == endDate) mini.get('endDate').setValue(result.end_date);
    }
}

function showHistoryDetail(params) {
    var obj = JSON.parse(decodeURIComponent(params));
    obj.deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';

    mini.open({
        title: _loginUserLanguageResource.detailsData,
        url: context + '/miniui-app/modules/historyQuery/historyDetail.jsp',
        width: '80%',
        height: '80%',
        modal: true,
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            iframe.contentWindow.setData(obj);
        }
    });
}

function onHistoryDataDrawCell(e) {
    var record = e.record;
    var field = e.field;
    var value = e.value;
    if (!record || !field) return;

    var alarmShowStyle = getAlarmShowStyle() || {};
    var Data = alarmShowStyle.Data || {};
    var Comm = alarmShowStyle.Comm || {};
    var Run = alarmShowStyle.Run || {};
    var alarmInfo = record.alarmInfo || [];
    var fieldUpper = field.toUpperCase();

    if (field === 'details') {
        var recordId = record.id || '';
        var deviceId = record.deviceId || '';
        var deviceName = record.deviceName || record.wellName || '';
        var calculateType = record.calculateType !== undefined ? record.calculateType : 0;
        if (!recordId && !deviceId) {
            e.cellHtml = '';
            return;
        }
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

    if (fieldUpper === 'ACQTIME') return;

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

    if (fieldUpper === 'RUNSTATUSNAME') {
        var commStatus = record.commStatus;
        var runStatus = record.runStatus;
        if (commStatus == 0 || commStatus == 2 || !value) {
            e.cellHtml = '';
            return;
        }
        var stopColor = (Run.stop && Run.stop.Color) ? '#' + Run.stop.Color : '#ff4d4f';
        var runColor = (Run.run && Run.run.Color) ? '#' + Run.run.Color : '#52c41a';
        var noDataColor = (Run.noData && Run.noData.Color) ? '#' + Run.noData.Color : '#999';
        var runColorSelected = (runStatus === 0) ? stopColor : (runStatus === 1 ? runColor : noDataColor);
        e.cellHtml = '<span style="color:' + runColorSelected + ';font-weight:bold;">' + (value || '') + '</span>';
        return;
    }

    if (fieldUpper !== 'ID' && fieldUpper !== 'DEVICENAME' && fieldUpper !== 'WELLNAME' &&
        fieldUpper !== 'COMMSTATUSNAME' && fieldUpper !== 'RUNSTATUSNAME' && fieldUpper !== 'ACQTIME') {
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
        e.cellHtml = value !== undefined && value !== null ? value : '';
        return;
    }

    e.cellHtml = (value !== undefined && value !== null) ? value : '';
}

function onHistoryDataDblClick(e) {
    var record = e.record;
    if (!record) return;

    var recordId = record.id || '';
    var deviceId = record.deviceId || '';
    var deviceName = record.deviceName || record.wellName || '';
    var calculateType = record.calculateType !== undefined ? record.calculateType : 0;
    if (!recordId && !deviceId) return;

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

// ================================================================
// 平铺图形
// ================================================================
function panelResizeObserver(divId, callback) {
    var container = document.getElementById(divId);
    if (!container) return;

    if (_tiledTabsResizeObserver) {
        _tiledTabsResizeObserver.disconnect();
        _tiledTabsResizeObserver = null;
    }

    if (window.ResizeObserver) {
        var observer = new ResizeObserver(function (entries) {
            for (var entry of entries) {
                if (entry.target === container) {
                    clearTimeout(container._resizeTimer);
                    container._resizeTimer = setTimeout(function () {
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
$(window).on('beforeunload', function () { destroyTiledTabsResizeObserver(); });

function getCurrentTiledType() {
    var tabs = mini.get('tiledTabs');
    if (!tabs) return 'FSDiagram';
    var activeTab = tabs.getActiveTab();
    if (activeTab && activeTab.name) return activeTab.name;
    return 'FSDiagram';
}

function onTiledTabActiveChanged(e) {
	if(isInitializing) return;
    var tab = e.tab;
    if (!tab) return;
    doTiledQuery();
}

function doTiledWorkTypeComboLoad() {
    var combo = mini.get('tiledWorkTypeCombo');
    if (combo) {
    	combo.setValue('');
    	if(!combo.getUrl()){
        	combo.setUrl(HQ_URLS.workTypeStat);
        }
        combo.load(combo.getUrl());
    }
}

function doTiledQuery() {
    var activeType = getCurrentTiledType();
    tiledPage = 1;
    loadTiledDiagram(activeType, 1);
}

function loadTiledDiagram(type, page) {
    if (!currentDeviceId) return;
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
        if (values) resultCode = values;
    }

    var start = mini.get('tiledStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
    var end = mini.get('tiledEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
    var hours = getTiledHistoryQueryHours();
    var deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';
    var limit = defaultGraghSize || 20;
    var startIdx = (page - 1) * limit;

    mini.mask({
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
        success: function (result) {
            mini.unmask(containerId);

            var startDate = mini.get('tiledStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            if (startDate == '' || null == startDate) mini.get('tiledStartDate').setValue(result.start_date);

            var endDate = mini.get('tiledEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
            if (endDate == '' || null == endDate) mini.get('tiledEndDate').setValue(result.end_date);

            if (page === 1) container.innerHTML = '';
            var list = result.list || [];
            var totalPages = result.totalPages || 0;
            var totalShow = result.totalShow || 0;
            _tiledTotalPages[type] = totalPages;

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

            var containerRect = container.getBoundingClientRect();
            var containerWidth = containerRect.width;
            var scrollWidth = getScrollWidth();
            var availableWidth = containerWidth - scrollWidth;
            if (availableWidth <= 0) availableWidth = 200;

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
                div.style.cssText = 'width:' + chartWidth + ';height:' + chartHeight
                    + ';min-height:' + dynamometerCardMinHeight + 'px;float:left;box-sizing:border-box;';
                fragment.appendChild(div);
                renderQueue.push({
                    diagram: diagram,
                    divId: divId,
                    renderFunc: config.renderFunc
                });
            }
            container.appendChild(fragment);

            renderChartsSequentially(renderQueue, function () {
                if (page === 1 && totalPages > 1) {
                    initTiledDiagramScroll(containerId, type);
                }
            });
        },
        error: function (xhr, status, errorThrown) {
            mini.unmask(containerId);
            container.innerHTML = '<div class="loading-placeholder error">'
                + _loginUserLanguageResource.requestFailed + '</div>';
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
    setTimeout(function () {
        renderChartsSequentially(queue, callback);
    }, 10);
}

function initTiledDiagramScroll(containerId, type) {
    var container = document.getElementById(containerId);
    if (!container) return;
    if (_tiledScrollHandlers[type]) {
        container.removeEventListener('scroll', _tiledScrollHandlers[type]);
    }
    var handler = function () {
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
    var resultTabsObj = mini.get('resultTabs');
    if (!resultTabsObj) return;
    var activeTab = resultTabsObj.getActiveTab();
    if (!activeTab || activeTab.name !== 'TiledDiagram') return;

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
    if (availableWidth <= 0) availableWidth = 200;

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

window.onTiledWorkTypeComboBeforeLoad = function (e) {
    var type = getCurrentTiledType();
    var config = TILED_CONFIG[type];
    if (!config) return;
    mini.mask({
        el: config.containerId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });
    var params = e.params || {};
    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';

    var grid = mini.get('deviceGrid');
    var selected = grid ? grid.getSelected() : null;
    params.deviceId = selected ? selected.id : '';
    params.deviceName = selected ? (selected.deviceName || selected.wellName || '') : '';

    var startDate = mini.get('tiledStartDate');
    var endDate = mini.get('tiledEndDate');
    params.startDate = startDate ? (startDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '') : '';
    params.endDate = endDate ? (endDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '') : '';
    params.hours = getTiledHistoryQueryHours();
};

window.onTiledWorkTypeComboLoad = function (e) {
    var type = getCurrentTiledType();
    var config = TILED_CONFIG[type];
    if (!config) return;
    mini.unmask(config.containerId);

    var combo = e.sender;
    var data = combo.getData();
    var selectedCodes = [];
    for (var i = 0; i < data.length; i++) {
        if (data[i].resultCode != 1232) selectedCodes.push(data[i].resultCode);
    }
    if (selectedCodes.length > 0) combo.setValue(selectedCodes.join(','));
    else if (data.length > 0) combo.setValue('');

    var tabs = mini.get('resultTabs');
    if (tabs) {
        var activeTab = tabs.getActiveTab();
        if (activeTab && activeTab.name === 'TiledDiagram') doTiledQuery();
    }
};

window.onTiledWorkTypeComboChange = function (e) {
    var tabs = mini.get('resultTabs');
    if (tabs) {
        var activeTab = tabs.getActiveTab();
        if (activeTab && activeTab.name === 'TiledDiagram') doTiledQuery();
    }
};

window.onTiledWorkTypeComboCloseClick = function (e) {
    var obj = e.sender;
    obj.setText('');
    obj.setValue('');
    var tabs = mini.get('resultTabs');
    if (tabs) {
        var activeTab = tabs.getActiveTab();
        if (activeTab && activeTab.name === 'TiledDiagram') doTiledQuery();
    }
};

// ================================================================
// 图形叠加
// ================================================================
function overlayWorkTypeComboLoad() {
    var combo = mini.get('overlayWorkTypeCombo');
    if (combo) {
        combo.setValue('');
        if(!combo.getUrl()){
        	combo.setUrl(HQ_URLS.workTypeStat);
        }
        combo.load(combo.getUrl());
    }
}

function doOverlayQuery() {
    if (!currentDeviceId) return;
    var grid = mini.get('overlayDataGrid');
    if (grid){
    	if(!grid.getUrl()){
    		grid.setUrl(HQ_URLS.overlayDataGrid);
    	}
    	grid.load();
    } 
}

function onOverlayGridBeforeLoad(e) {
    _overlayGridReault = {};
    var params = e.params || {};
    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';
    var dictDeviceType = params.deviceType;
    if (params.deviceType.indexOf(',') > -1) {
        dictDeviceType = _hqCurrentLevel1 ? _hqCurrentLevel1.deviceTypeId : params.deviceType;
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
    params.startDate = startDate ? (startDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '') : '';
    params.endDate = endDate ? (endDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '') : '';
    params.hours = getOverlayHistoryQueryHours();
    params.calculateType = currentCalculateType || 0;
    e.params = params;
}

function onOverlayGridLoad(e) {
    var grid = e.sender;
    var result = e.result;
    if (!result) return;

    _overlayGridReault = result;

    if (result.totalCount !== undefined) {
        var el = document.getElementById('overlayTotalCountLabel');
        if (el) el.style.display = 'inline';
        var sp = document.getElementById('overlayTotalCountSpan');
        if (sp) sp.textContent = result.totalCount;
    }
    if (result.vacuateCount !== undefined) {
        var el2 = document.getElementById('overlayVacuateCountLabel');
        if (el2) el2.style.display = 'inline';
        var sp2 = document.getElementById('overlayVacuateCountSpan');
        if (sp2) sp2.textContent = result.vacuateCount;
    }

    if (result.columns && result.columns.length > 0) {
        var columns = [];
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

    grid.selectAll();

    showFSDiagramOverlayChart(_overlayGridReault, 'overlayFsChart', true, 0);
    showFSDiagramOverlayChart(_overlayGridReault, 'overlayPowerChart', true, 1);
    showFSDiagramOverlayChart(_overlayGridReault, 'overlayCurrentChart', true, 2);
}

function onOverlayGridSelect(e) {
    if (_overlaySkipPending) return;
    clearTimeout(_overlayPendingTimer);
    _overlayPendingTimer = setTimeout(function () {
        if (_overlaySkipPending) return;
        var grid = e.sender;
        if (grid.getSelecteds().length === grid.getData().length) return;
        setOverlaySeriesVisibility(grid.indexOf(e.record), true);
    }, 50);
}

function onOverlayGridDeselect(e) {
    if (_overlaySkipPending) return;
    clearTimeout(_overlayPendingTimer);
    _overlayPendingTimer = setTimeout(function () {
        if (_overlaySkipPending) return;
        var grid = e.sender;
        if (grid.getSelecteds().length === 0) return;
        setOverlaySeriesVisibility(grid.indexOf(e.record), false);
    }, 50);
}

function onOverlayGridCheckAll(e) {
    _overlaySkipPending = true;
    clearTimeout(_overlayPendingTimer);

    try {
        var grid = e.sender;
        var totalData = grid.getData();
        var isChecked = (grid.getSelecteds().length === totalData.length);

        showFSDiagramOverlayChart(_overlayGridReault, "overlayFsChart", isChecked, 0);
        showFSDiagramOverlayChart(_overlayGridReault, "overlayPowerChart", isChecked, 1);
        showFSDiagramOverlayChart(_overlayGridReault, "overlayCurrentChart", isChecked, 2);
    } finally {
        setTimeout(function () { _overlaySkipPending = false; }, 100);
    }
}

function setOverlaySeriesVisibility(index, visible) {
    var chartIds = ['overlayFsChart', 'overlayPowerChart', 'overlayCurrentChart'];
    chartIds.forEach(function (chartId) {
        var container = document.getElementById(chartId);
        if (!container) return;
        var chart = $(container).highcharts();
        if (chart && chart.series && chart.series.length > index) {
            var series = chart.series[index];
            if (visible) series.show();
            else series.hide();
        }
    });
}

window.onOverlayWorkTypeComboBeforeLoad = function (e) {
    mini.mask({
        el: 'overlayChartPanel',
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });
    var params = e.params || {};
    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';

    var grid = mini.get('deviceGrid');
    var selected = grid ? grid.getSelected() : null;
    params.deviceId = selected ? selected.id : '';
    params.deviceName = selected ? (selected.deviceName || selected.wellName || '') : '';

    var startDate = mini.get('overlayStartDate');
    var endDate = mini.get('overlayEndDate');
    params.startDate = startDate ? (startDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '') : '';
    params.endDate = endDate ? (endDate.getFormValue('yyyy-MM-dd HH:mm:ss') || '') : '';
    params.hours = getOverlayHistoryQueryHours();
};

window.onOverlayWorkTypeComboLoad = function (e) {
    mini.unmask('overlayChartPanel');
    var combo = e.sender;
    var data = combo.getData();
    var result = e.result;

    var startDate = mini.get('overlayStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
    if (startDate == '' || null == startDate) mini.get('overlayStartDate').setValue(result.start_date);

    var endDate = mini.get('overlayEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
    if (endDate == '' || null == endDate) mini.get('overlayEndDate').setValue(result.end_date);

    var selectedCodes = [];
    for (var i = 0; i < data.length; i++) {
        if (data[i].resultCode != 1232) selectedCodes.push(data[i].resultCode);
    }
    if (selectedCodes.length > 0) combo.setValue(selectedCodes.join(','));
    else combo.setValue('');

    doOverlayQuery();
};

window.onOverlayWorkTypeComboChange = function (e) { doOverlayQuery(); };

window.onOverlayWorkTypeComboCloseClick = function (e) {
    var obj = e.sender;
    obj.setText('');
    obj.setValue('');
    doOverlayQuery();
};

// ================================================================
// 时间范围
// ================================================================
function getHistoryQueryHours() {
    var all = document.getElementById('chkAll');
    if (all && all.checked) return 'all';
    var hours = [];
    ['chk1', 'chk2', 'chk3', 'chk4'].forEach(function (id) {
        var chk = document.getElementById(id);
        if (chk && chk.checked) hours.push(chk.name);
    });
    if (hours.length == 4) return 'all';
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

    if (target && target.id === 'chkAll') {
        if (all.checked) {
            if (chk1) chk1.checked = true;
            if (chk2) chk2.checked = true;
            if (chk3) chk3.checked = true;
            if (chk4) chk4.checked = true;
        }
    } else {
        var allChecked = chk1 && chk1.checked && chk2 && chk2.checked && chk3 && chk3.checked && chk4 && chk4.checked;
        all.checked = allChecked;
    }

    if (currentDeviceId) doQuery();
}

function updateTiledTimeRange(e) {
    var target = e ? e.target : null;
    var all = document.getElementById('tiledChkAll');
    if (!all) return;

    if (target && target.id === 'tiledChkAll') {
        if (all.checked) {
            document.querySelectorAll('[id^="tiledChk"]:not(#tiledChkAll)').forEach(function (chk) {
                chk.checked = true;
            });
        }
    } else {
        var subs = document.querySelectorAll('[id^="tiledChk"]:not(#tiledChkAll)');
        var allChecked = true;
        subs.forEach(function (chk) { if (!chk.checked) allChecked = false; });
        all.checked = allChecked;
    }

    if (currentDeviceId) doQuery();
}

function updateOverlayTimeRange(e) {
    var target = e ? e.target : null;
    var all = document.getElementById('overlayChkAll');
    if (!all) return;

    if (target && target.id === 'overlayChkAll') {
        if (all.checked) {
            document.querySelectorAll('[id^="overlayChk"]:not(#overlayChkAll)').forEach(function (chk) {
                chk.checked = true;
            });
        }
    } else {
        var subs = document.querySelectorAll('[id^="overlayChk"]:not(#overlayChkAll)');
        var allChecked = true;
        subs.forEach(function (chk) { if (!chk.checked) allChecked = false; });
        all.checked = allChecked;
    }

    if (currentDeviceId) doOverlayQuery();
}

function getTiledHistoryQueryHours() {
    var all = document.getElementById('tiledChkAll');
    if (all && all.checked) return 'all';
    var hours = [];
    ['tiledChk1', 'tiledChk2', 'tiledChk3', 'tiledChk4'].forEach(function (id) {
        var chk = document.getElementById(id);
        if (chk && chk.checked) hours.push(chk.name);
    });
    if (hours.length == 4) return 'all';
    return hours.length > 0 ? hours.join(',') : '';
}

function getOverlayHistoryQueryHours() {
    var all = document.getElementById('overlayChkAll');
    if (all && all.checked) return 'all';
    var hours = [];
    ['overlayChk1', 'overlayChk2', 'overlayChk3', 'overlayChk4'].forEach(function (id) {
        var chk = document.getElementById(id);
        if (chk && chk.checked) hours.push(chk.name);
    });
    if (hours.length == 4) return 'all';
    return hours.length > 0 ? hours.join(',') : '';
}

// ================================================================
// 设备下拉框
// ================================================================
window.onDeviceComboBeforeLoad = function (e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize = params.pageSize || defaultWellComboxSize;
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';

    var combo = mini.get('deviceCombo');
    params.deviceName = combo ? combo.getValue() : '';
};

window.onDeviceComboShowPopup = function (e) {
    var combo = e.sender;
    var data = combo.getData();
    var hidePopup = false;
    if (!data || data.length <= 1) {
        combo.hidePopup();
        hidePopup = true;
    }
    if(!combo.getUrl()){
    	combo.setUrl(HQ_URLS.deviceCombo);
    }
    combo.load(combo.getUrl());
    
    if (hidePopup) combo.showPopup();
};

window.onDeviceComboLoad = function (e) { /* 保持空 */ };

// ================================================================
// 导出
// ================================================================
function exportDeviceList() {
    var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
    var deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';
    var dictDeviceType = deviceType;
    if (deviceType.indexOf(',') > -1) dictDeviceType = _hqCurrentLevel1 ? _hqCurrentLevel1.deviceTypeId : deviceType;
    var deviceName = mini.get('deviceCombo') ? mini.get('deviceCombo').getValue() : '';

    var FESdiagramResultStatValue = document.getElementById('HistoryQueryStatSelectFESdiagramResult_Id').value;
    var commStatusStatValue = document.getElementById('HistoryQueryStatSelectCommStatus_Id').value;
    var runStatusStatValue = document.getElementById('HistoryQueryStatSelectRunStatus_Id').value;
    var numStatusStatValue = document.getElementById('HistoryQueryStatSelectNumStatus_Id').value;
    var deviceTypeStatValue = document.getElementById('HistoryQueryStatSelectDeviceType_Id').value;

    var fileName = _loginUserLanguageResource.historyQueryDeviceList;
    var title = fileName;

    var columnStrInput = document.getElementById('HistoryQueryWellListColumnStr_Id');
    if (!columnStrInput || !columnStrInput.value) {
        mini.alert('表格列配置未加载，请刷新页面重试');
        return;
    }
    var columnStr = columnStrInput.value;
    var fields = '', heads = '';
    try {
        var columns = JSON.parse(columnStr);
        var lockedfields = '', lockedheads = '', unlockedfields = '', unlockedheads = '';
        columns.forEach(function (col) {
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
        if (lockedfields) { lockedfields = lockedfields.slice(0, -1); lockedheads = lockedheads.slice(0, -1); }
        if (unlockedfields) { unlockedfields = unlockedfields.slice(0, -1); unlockedheads = unlockedheads.slice(0, -1); }
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

    var maskEl = document.querySelector('.device-grid-wrapper') || document.body;
    exportDataMask(key, maskEl, _loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
}

function exportData() {
    if (!currentDeviceId) {
        mini.alert(_loginUserLanguageResource.checkOne);
        return;
    }
    var tabs = mini.get('resultTabs');
    var active = tabs ? tabs.getActiveTab() : null;
    var name = active ? active.name : '';
    var key = 'exportHistory_' + currentDeviceId + '_' + Date.now();
    var url = '', param = '';

    if (name === 'TrendCurve') {
        var start = mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var end = mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var hours = getHistoryQueryHours();
        var deviceType = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';

        url = context + '/historyQueryController/exportHistoryQueryDataExcel';
        param = '&orgId=' + (window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '') +
            '&deviceType=' + deviceType + '&deviceId=' + currentDeviceId + '&deviceName=' + encodeURIComponent(encodeURIComponent(currentDeviceName)) +
            '&calculateType=' + currentCalculateType + '&startDate=' + encodeURIComponent(start) + '&endDate=' + encodeURIComponent(end) +
            '&hours=' + hours + '&fileName=' + encodeURIComponent(encodeURIComponent(currentDeviceName + _loginUserLanguageResource.historyData)) +
            '&title=' + encodeURIComponent(encodeURIComponent(currentDeviceName + _loginUserLanguageResource.historyData)) +
            '&key=' + key;
        exportDataMask(key, 'historyTrendCurvePanel', _loginUserLanguageResource.loadingData);
        openExcelWindow(url + '?flag=true' + param);
    } else if (name === 'TiledDiagram') {
        var activeType = getCurrentTiledType();
        var config = TILED_CONFIG[activeType];
        if (!config) return;
        var combo = mini.get('tiledWorkTypeCombo');
        var resultCode = combo ? combo.getValue() : '';
        var start2 = mini.get('tiledStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var end2 = mini.get('tiledEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var hours2 = getTiledHistoryQueryHours();
        var deviceType2 = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';
        url = context + '/historyQueryController/exportHistoryQueryDiagramTiledDataExcel';
        param = '&orgId=' + (window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '') +
            '&deviceType=' + deviceType2 + '&deviceId=' + currentDeviceId + '&deviceName=' + encodeURIComponent(encodeURIComponent(currentDeviceName)) +
            '&resultCode=' + resultCode + '&startDate=' + encodeURIComponent(start2) + '&endDate=' + encodeURIComponent(end2) +
            '&hours=' + hours2 + '&diagramType=' + activeType + '&fileName=' + encodeURIComponent(encodeURIComponent(currentDeviceName + '-' + _loginUserLanguageResource.FSDiagramData)) +
            '&title=' + encodeURIComponent(encodeURIComponent(currentDeviceName + '-' + _loginUserLanguageResource.FSDiagramData)) +
            '&key=' + key;
        exportDataMask(key, config.containerId, _loginUserLanguageResource.loadingData);
        openExcelWindow(url + '?flag=true' + param);
    } else if (name === 'DiagramOverlay') {
        var combo2 = mini.get('overlayWorkTypeCombo');
        var resultCode2 = combo2 ? combo2.getValue() : '';
        var start3 = mini.get('overlayStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var end3 = mini.get('overlayEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
        var hours3 = getOverlayHistoryQueryHours();
        var deviceType3 = _hqCurrentLevel2 ? _hqCurrentLevel2.deviceTypeId : '0';
        var key3 = 'exportOverlay_' + currentDeviceId + '_' + Date.now();
        var url3 = context + '/historyQueryController/exportHistoryQueryFESDiagramOverlayDataExcel';
        var fields = "";
        var heads = "";
        var fileName = currentDeviceName + '-' + _loginUserLanguageResource.FSDiagramOverlayData;
        var title = currentDeviceName + '-' + _loginUserLanguageResource.FSDiagramOverlayData;
        var dictDeviceType = deviceType3;
        if (deviceType3 && deviceType3.indexOf(',') > -1) {
            dictDeviceType = _hqCurrentLevel1 ? _hqCurrentLevel1.deviceTypeId : deviceType3;
        }
        var param3 = "&fields=" + fields + "&heads=" + URLencode(URLencode(heads)) +
            '&orgId=' + (window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '') +
            '&deviceType=' + deviceType3 +
            "&dictDeviceType=" + dictDeviceType +
            '&deviceId=' + currentDeviceId +
            '&deviceName=' + URLencode(URLencode(currentDeviceName)) +
            '&resultCode=' + resultCode2 +
            '&calculateType=' + currentCalculateType +
            '&startDate=' + start3 +
            '&endDate=' + end3 +
            '&hours=' + hours3 +
            "&fileName=" + URLencode(URLencode(fileName)) +
            "&title=" + URLencode(URLencode(title)) +
            '&key=' + key3;
        exportDataMask(key3, 'overlayChartPanel', _loginUserLanguageResource.loadingData);
        openExcelWindow(url3 + '?flag=true' + param3);
    } else {
        return;
    }
}

// ================================================================
// 刷新
// ================================================================
function refreshData() {
    if (_hqCurrentLevel2) {
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        clearStatFilters();
        if (currentDeviceId) {
            var grid = mini.get('deviceGrid');
            var selected = grid ? grid.getSelected() : null;
            if (selected) refreshHistoryTabs(selected);
        }
        var deviceTypeId = _hqCurrentLevel2.deviceTypeId || '0';
        loadStatCharts(deviceTypeId, orgId);
        refreshDeviceList();
    }
}

//================================================================
//设备名称悬停提示
//================================================================
function handleDeviceNameCellMouseEnter(cellElement, event) {
	 var alarmData = cellElement.getAttribute('data-alarm');
	 if (!alarmData) return;
	 var counts;
	 try { counts = JSON.parse(alarmData); } catch (e) { return; }
	
	 var deviceName = cellElement.getAttribute('data-name') || '';
	 var hasAlarm = (counts[100] + counts[200] + counts[300]) > 0;
	 if (!hasAlarm) return;
	
	 var alarmShowStyle = getAlarmShowStyle() || {};
	 var Data = alarmShowStyle.Data || {};
	
	 var parts = [];
	 function badge(text, bg, tx) {
	     return '<span style="display:inline-block;background:' + bg
	         + ';color:' + tx + ';padding:0 8px;border-radius:12px;'
	         + 'font-size:11px;font-weight:bold;line-height:18px;'
	         + 'margin-right:4px;white-space:nowrap;">' + text + '</span>';
	 }
	
	 var R = _loginUserLanguageResource;
	 if (counts[100] > 0) {
	     parts.push(badge((R.alarmLevel1 || '一级') + ':' + counts[100],
	         '#' + (Data.FirstLevel && Data.FirstLevel.Color || 'dc2828'),
	         '#' + (Data.FirstLevel && Data.FirstLevel.ColorText || 'ffffff')));
	 }
	 if (counts[200] > 0) {
	     parts.push(badge((R.alarmLevel2 || '二级') + ':' + counts[200],
	         '#' + (Data.SecondLevel && Data.SecondLevel.Color || 'f09614'),
	         '#' + (Data.SecondLevel && Data.SecondLevel.ColorText || 'ffffff')));
	 }
	 if (counts[300] > 0) {
	     parts.push(badge((R.alarmLevel3 || '三级') + ':' + counts[300],
	         '#' + (Data.ThirdLevel && Data.ThirdLevel.Color || 'fae600'),
	         '#' + (Data.ThirdLevel && Data.ThirdLevel.ColorText || '333333')));
	 }
	
	 var tipHtml = deviceName + ' ' + parts.join(' ');
	
	 var x = event.clientX + 12;
	 var y = event.clientY + 12;
	 var tipWidth = 300, tipHeight = 80;
	 if (x + tipWidth > window.innerWidth)  x = event.clientX - tipWidth - 12;
	 if (y + tipHeight > window.innerHeight) y = event.clientY - tipHeight - 12;
	
	 hideDeviceNameTip();
	
	 var tipDiv = document.createElement('div');
	 tipDiv.id = 'deviceNameTip';
	 tipDiv.style.cssText =
	     'position:fixed;background:#fff;border:1px solid #ccc;padding:6px 10px;' +
	     'border-radius:4px;box-shadow:0 2px 8px rgba(0,0,0,0.15);z-index:99999;' +
	     'max-width:400px;font-size:12px;font-family:"Microsoft YaHei",Arial,sans-serif;' +
	     'pointer-events:none;';
	 tipDiv.innerHTML = tipHtml;
	 tipDiv.style.left = x + 'px';
	 tipDiv.style.top  = y + 'px';
	 document.body.appendChild(tipDiv);
}

function hideDeviceNameTip() {
	var tip = document.getElementById('deviceNameTip');
	if (tip && tip.parentNode) tip.parentNode.removeChild(tip);
}

// ================================================================
// 暴露到 window
// ================================================================
window.doQuery                          = doQuery;
window.exportData                       = exportData;
window.refreshDeviceList                = refreshDeviceList;
window.selectHqLevel1                   = selectHqLevel1;
window.selectHqLevel2                   = selectHqLevel2;
window.onDeviceSelect                   = onDeviceSelect;
window.onDeviceComboChange              = onDeviceComboChange;
window.updateTimeRange                  = updateTimeRange;
window.updateTiledTimeRange             = updateTiledTimeRange;
window.updateOverlayTimeRange           = updateOverlayTimeRange;
window.onStatTabChanged                 = onStatTabChanged;
window.onResultTabChanged               = onResultTabChanged;
window.onDeviceGridBeforeLoad           = onDeviceGridBeforeLoad;
window.onDeviceGridLoad                 = onDeviceGridLoad;
window.onDeviceGridDrawCell             = onDeviceGridDrawCell;
window.onHistoryDataBeforeLoad          = onHistoryDataBeforeLoad;
window.onHistoryDataLoad                = onHistoryDataLoad;
window.onHistoryDataDrawCell            = onHistoryDataDrawCell;
window.onHistoryDataDblClick            = onHistoryDataDblClick;
window.onTiledTabActiveChanged          = onTiledTabActiveChanged;
window.onOverlayGridBeforeLoad          = onOverlayGridBeforeLoad;
window.onOverlayGridLoad                = onOverlayGridLoad;
window.onOverlayGridSelect              = onOverlayGridSelect;
window.onOverlayGridDeselect            = onOverlayGridDeselect;
window.onOverlayGridCheckAll            = onOverlayGridCheckAll;
window.doTiledWorkTypeComboLoad         = doTiledWorkTypeComboLoad;
window.overlayWorkTypeComboLoad         = overlayWorkTypeComboLoad;
window.doTiledQuery                     = doTiledQuery;
window.doOverlayQuery                   = doOverlayQuery;
window.exportDeviceList                 = exportDeviceList;
window.openCurveSetWindow               = openCurveSetWindow;
window.showHistoryDetail                = showHistoryDetail;
window.resetTrendCurveControls          = resetTrendCurveControls;
window.resetTiledDiagramControls        = resetTiledDiagramControls;
window.resetDiagramOverlayControls      = resetDiagramOverlayControls;
window.panelResizeObserver              = panelResizeObserver;
window.destroyTiledTabsResizeObserver   = destroyTiledTabsResizeObserver;
window.getCurrentTiledType              = getCurrentTiledType;
window.initDeviceHistoryCurveChartFn    = initDeviceHistoryCurveChartFn;