// ================================================================
// 实时监控模块 - realTimeMonitoringInfo.js
// ================================================================

// ---------- 全局状态 ----------
var _rmTabInfo = null;
var _rmLevel1Data = [];
var _rmLevel2Data = [];
var _rmCurrentLevel1 = null;
var _rmCurrentLevel2 = null;

var currentDeviceId = 0;
var currentMiddleTab = null;

var statTabs = null;
var middleTabs = null;
var rightTabs = null;
var deviceGrid = null;

var deviceRealTimeMonitoringGrid = null;

//---------- 全局选中状态 ----------
var _rmRestoreState = null;              // 在 initRealTimeMonitoringPage 中赋值
var _rmSuppressSave = { value: true };   // 对象包装，便于异步修改

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

var DEFAULT_COLUMNS = [];

// ================================================================
// 页面初始化
// ================================================================
function initRealTimeMonitoringPage() {
    try {
        if (window.parent && window.parent.tabInfo) {
            _rmTabInfo = window.parent.tabInfo;
        }
    } catch (e) {
        console.warn('无法获取 tabInfo', e);
    }
    
    // ★ 初始化全局恢复状态
    _rmRestoreState  = createRestoreState();
    _rmSuppressSave  = { value: true };

    statTabs   = mini.get('statTabs');
    middleTabs = mini.get('middleTabs');
    rightTabs  = mini.get('rightTabs');
    deviceGrid = mini.get('deviceGrid');

    if (deviceGrid && typeof _defaultPageSize !== 'undefined' && _defaultPageSize) {
        deviceGrid.setPageSize(parseInt(_defaultPageSize, 10));
    }

    // 先国际化（含 tab 标题、网格列定义、图表最小高度等）
    initRealTimeMonitoringI18n();

    buildRtmLevel1Tabs();

    // 事件代理：设备名称悬停提示
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

    initRealTimeMonitoringMessageListener();

    console.log('实时监控模块加载完成');
}

// ================================================================
// 国际化
// ================================================================
function initRealTimeMonitoringI18n() {
    var R = _loginUserLanguageResource;

    // 1. 工具栏按钮
    var btnRefresh = mini.get('btnRefresh');
    if (btnRefresh) btnRefresh.setText(R.refresh);

    var exportBtn = mini.get('exportRealTimeMonitoringDeviceListBtn');
    if (exportBtn) exportBtn.setText(R.exportData);

    var historyBtn = mini.get('queryDeviceHistoryDataBtn');
    if (historyBtn) historyBtn.setText(R.showHistory);

    var dynExpBtn = mini.get('dynamicDataExportBtn');
    if (dynExpBtn) dynExpBtn.setText(R.exportData);

    // 2. 设备下拉框
    var deviceCombo = mini.get('deviceCombo');
    if (deviceCombo) deviceCombo.setEmptyText('--' + R.all + '--');

    // 3. 动态数据工具栏标签
    var ddLabel = document.getElementById('middleDynamicDataLabel');
    if (ddLabel) ddLabel.textContent = R.viewCurveOrTableData;

    // 4. 统计 tab 标题
    setRtmTabTitleByName('statTabs', 'FESdiagramResult', R.workType);
    setRtmTabTitleByName('statTabs', 'CommStatus',       R.commStatus);
    setRtmTabTitleByName('statTabs', 'RunStatus',        R.runStatus);
    setRtmTabTitleByName('statTabs', 'NumStatus',        R.numStatus);

    // 5. 中间 tab 标题
    setRtmTabTitleByName('middleTabs', 'middle_WellboreAnalysis', R.wellboreAnalysis);
    setRtmTabTitleByName('middleTabs', 'middle_SurfaceAnalysis',  R.surfaceAnalysis);
    setRtmTabTitleByName('middleTabs', 'middle_TrendCurve',       R.trendCurve);
    setRtmTabTitleByName('middleTabs', 'middle_DynamicData',      R.dynamicData);

    // 6. 右侧 tab 标题
    setRtmTabTitleByName('rightTabs', 'right_DeviceControl', R.deviceControl);
    setRtmTabTitleByName('rightTabs', 'right_DeviceInfo',    R.deviceInformation);

    // 7. 设备信息-附加信息表列
    var addGrid = mini.get('deviceInfoAdditionalGrid');
    if (addGrid) {
        addGrid.setColumns([
            {
                field: 'name',
                header: R.variable,
                headerAlign: 'left', align: 'left',
                width: '50%'
            },
            {
                field: 'value',
                header: R.value,
                headerAlign: 'center', align: 'center',
                width: '50%'
            }
        ]);
    }

    // 8. 设备信息-辅件设备表列（带展开行）
    var auxGrid = mini.get('deviceInfoAuxiliaryGrid');
    if (auxGrid) {
        auxGrid.setColumns([
            { type: 'expandcolumn', width: 15 },
            {
                type: 'indexcolumn',
                header: R.idx,
                headerAlign: 'center', align: 'center',
                width: 50
            },
            {
                field: 'name',
                header: R.deviceName,
                headerAlign: 'center', align: 'center'
            }
        ]);
    }

    // 9. 井筒/地面分析 chart-item 最小高度
    var minH = (typeof dynamometerCardMinHeight !== 'undefined' && dynamometerCardMinHeight)
        ? dynamometerCardMinHeight : 350;
    var wellboreItems = document.querySelectorAll('#middle_WellboreAnalysis_body .chart-item');
    for (var wi = 0; wi < wellboreItems.length; wi++) {
        wellboreItems[wi].style.minHeight = minH + 'px';
    }
    var surfaceItems = document.querySelectorAll('#middle_SurfaceAnalysis_body .chart-item');
    for (var si = 0; si < surfaceItems.length; si++) {
        surfaceItems[si].style.minHeight = minH + 'px';
    }
    
    // ★ 统计饼图内层 div 最小高度
    var pieMinH = (typeof otherCardMinHeight !== 'undefined' && otherCardMinHeight)
        ? otherCardMinHeight : 300;

    var pieIds = [
        'pieChart_FESdiagramResult',
        'pieChart_CommStatus',
        'pieChart_RunStatus',
        'pieChart_NumStatus'
    ];
    for (var pi = 0; pi < pieIds.length; pi++) {
        var pieEl = document.getElementById(pieIds[pi]);
        if (pieEl) {
            pieEl.style.minHeight = pieMinH + 'px';
        }
    }
}

/**
 * 按 name 更新 tab 标题
 */
function setRtmTabTitleByName(tabsId, name, title) {
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
// 消息监听
// ================================================================
function initRealTimeMonitoringMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        switch (message.action) {
            case 'updateDeviceData':
                handleRealTimeData(message.data);
                break;
            case 'updateResourceData':
                updateResourceMonitorUI(message.data);
                break;
            case 'updateDBData':
                updateDBMonitorUI(message.data);
                break;
            case 'adExitAndDeviceOffline':
                handleAdExit(message.data);
                break;
            case 'refresh':
            	handleRefreshFromParent(message);
                break;
            default:
                break;
        }
    });
}

/**
 * 父窗口发出 refresh（组织切换 / 从其他模块切回本模块）时的处理
 * 1. 优先按全局状态同步标签 + 设备
 * 2. 无差异时按常规刷新
 */
function handleRefreshFromParent(message) {
    console.log('实时监控收到刷新指令, orgId:', message.orgId);

    clearStatFilters();
    var combo = mini.get('deviceCombo');
    if (combo) { combo.setValue(''); combo.setText(''); }

    var gsel = loadGlobalSelection();
    var curType = _rmCurrentLevel2 ? String(_rmCurrentLevel2.deviceTypeId) : '';

    // ============ 情况 1：二级（或一级）标签不一致 → 重建标签 + 恢复设备 ============
    if (gsel.deviceTypeId && curType !== String(gsel.deviceTypeId)) {
        var target = findTargetLevels(_rmLevel1Data, gsel.deviceTypeId);
        if (target) {
            _rmRestoreState.deviceId    = gsel.deviceId || '';
            _rmRestoreState.level2Index = target.level2Index;
            selectRtmLevel1(target.level1Index);
            return;
        }
    }

    // ============ 情况 2 & 3：统一重新加载设备列表 ============
    // 无论设备是否相同，都走 grid.load() → onDeviceGridLoad → restoreDeviceSelection
    if (gsel.deviceId) {
        _rmRestoreState.deviceId = String(gsel.deviceId);
    }
    refreshDeviceList();
}

// ================================================================
// 一级标签
// ================================================================
function buildRtmLevel1Tabs() {
    var container = document.getElementById('level1Footer');
    if (!container) return;
    container.innerHTML = '';

    if (!_rmTabInfo || !_rmTabInfo.children || _rmTabInfo.children.length === 0) {
        return;
    }

    _rmLevel1Data = _rmTabInfo.children;

    for (var i = 0; i < _rmLevel1Data.length; i++) {
        (function (idx) {
            var item = _rmLevel1Data[idx];
            var span = document.createElement('span');
            span.className = 'tab-item' + (idx === 0 ? ' active' : '');
            span.dataset.index = idx;
            span.dataset.deviceTypeId = item.deviceTypeId;
            span.textContent = item.text;
            span.onclick = function () {
                selectRtmLevel1(parseInt(this.dataset.index));
            };
            container.appendChild(span);
        })(i);
    }

    buildResourceMonitorArea(container);

    if (_rmLevel1Data.length > 0) {
        // ★ 使用恢复状态决定起始一级
        var startIndex = 0;
        if (_rmRestoreState.deviceTypeId) {
            var t = findTargetLevels(_rmLevel1Data, _rmRestoreState.deviceTypeId);
            if (t) {
                _rmRestoreState.level1Index = t.level1Index;
                _rmRestoreState.level2Index = t.level2Index;
                startIndex = t.level1Index;
            }
            _rmRestoreState.deviceTypeId = '';
        }
        selectRtmLevel1(startIndex);
    }
}

function buildResourceMonitorArea(container) {
    var rightArea = document.createElement('div');
    rightArea.id = 'resourceMonitorArea';
    rightArea.style.cssText = 'display:flex; align-items:center; gap:6px; margin-left:auto; font-size:12px;';
    container.appendChild(rightArea);

    var R = _loginUserLanguageResource;
    var resourceItems = [
        { id: 'CPUUsedPercentLabel_id', text: R.resourcesMonitoring_cpu,
          onclick: "openResourceChart('cpuUsedPercent','" + R.cpuUsage + "(%)')", showDot: false },
        { id: 'memUsedPercentLabel_id', text: R.resourcesMonitoring_mem,
          onclick: "openResourceChart('memUsedPercent','" + R.memUsage + "(%)')", showDot: false },
        { id: 'tableSpaceSizeProbeLabel_id', text: R.resourcesMonitoring_tablespaces,
          onclick: "openResourceChart('tableSpaceSize','" + R.tablespacesUsage + "(%)')", showDot: true },
        { id: 'redisRunStatusProbeLabel_id', text: R.resourcesMonitoring_cache,
          onclick: "openResourceChart('jedisStatus','" + R.cacheDbMemory + "(m)')", showDot: true },
        { id: 'adRunStatusProbeLabel_id', text: R.resourcesMonitoring_ad,
          onclick: "openResourceChart('adRunStatus','" + R.adStatus + "')", showDot: true },
        { id: 'acRunStatusProbeLabel_id', text: R.resourcesMonitoring_ac,
          onclick: "openResourceChart('acRunStatus','" + R.acStatus + "')", showDot: true },
        { id: 'adLicenseStatusProbeLabel_id', text: 'License', onclick: '', showDot: false }
    ];

    for (var i = 0; i < resourceItems.length; i++) {
        var cfg = resourceItems[i];
        var span = document.createElement('span');
        span.id = cfg.id;
        span.style.cssText = 'cursor:' + (cfg.onclick ? 'pointer' : 'default') + '; padding:0 3px; white-space:nowrap;';
        if (cfg.showDot) {
            span.innerHTML = '<span style="color:#ccc;"></span> ' + cfg.text;
        } else {
            span.textContent = cfg.text;
        }
        if (cfg.onclick) span.onclick = new Function(cfg.onclick);
        rightArea.appendChild(span);

        if (i < resourceItems.length - 1) {
            var sep = document.createElement('span');
            sep.style.cssText = 'color:#ddd; padding:0 2px;';
            sep.textContent = '|';
            rightArea.appendChild(sep);
        }
    }

    var licenseEl = document.getElementById('adLicenseStatusProbeLabel_id');
    if (licenseEl) licenseEl.style.display = 'none';
}

function selectRtmLevel1(index) {
    if (index < 0 || index >= _rmLevel1Data.length) return;
    var item = _rmLevel1Data[index];
    _rmCurrentLevel1 = item;

    var container = document.getElementById('level1Footer');
    var tabs = container.querySelectorAll('.tab-item');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    buildRtmLevel2Tabs(item);
    console.log('选择一级:', item.text);
}

// ================================================================
// 二级标签
// ================================================================
function buildRtmLevel2Tabs(parentItem) {
    var container = document.getElementById('level2Sidebar');
    if (!container) return;
    container.innerHTML = '';

    var children = parentItem.children || [];

    // ============ 一级无子标签 ============
    if (!children || children.length === 0) {
        container.classList.add('hidden');
        _rmLevel2Data = [];

        _rmCurrentLevel2 = {
            text: parentItem.text,
            deviceTypeId: parentItem.deviceTypeId,
            isAll: false,
            isLevel1Direct: true
        };
        _rmRestoreState.level2Index = -1;

        loadAllData(_rmCurrentLevel2);
        // ★ 保留设备选中：用恢复候选 deviceId 而非清空
        saveGlobalSelection(_rmCurrentLevel2.deviceTypeId,
            _rmRestoreState.deviceId || '',
            { suppress: _rmSuppressSave.value });
        return;
    }

    // ============ 一级有子标签 ============
    container.classList.remove('hidden');
    _rmLevel2Data = children;

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
            div.className = 'tab-item';           // ← 不再直接标 active
            div.dataset.index = idx;
            div.dataset.deviceTypeId = item.deviceTypeId;
            div.dataset.isAll = item.isAll || false;
            div.textContent = item.text;
            div.title = item.text;
            div.onclick = function () {
                selectRtmLevel2(parseInt(this.dataset.index));
            };
            container.appendChild(div);
        })(i, allTabs[i]);
    }

    // 使用恢复状态决定默认二级
    var defaultIndex = 0;
    if (_rmRestoreState.level2Index >= 0 && _rmRestoreState.level2Index < allTabs.length) {
        defaultIndex = _rmRestoreState.level2Index;
    }
    _rmRestoreState.level2Index = -1;

    var tabEls = container.querySelectorAll('.tab-item');
    for (var t = 0; t < tabEls.length; t++) {
        tabEls[t].className = 'tab-item' + (t === defaultIndex ? ' active' : '');
    }

    if (allTabs.length > 0) {
        _rmCurrentLevel2 = allTabs[defaultIndex];
        loadAllData(_rmCurrentLevel2);
    }
}

function selectRtmLevel2(index) {
    var container = document.getElementById('level2Sidebar');
    var tabs = container.querySelectorAll('.tab-item');

    var allTabs = [];
    // ★ 只有多个二级时才生成"全部"tab
    if (_rmLevel2Data.length > 1) {
        var allIds = [];
        for (var i = 0; i < _rmLevel2Data.length; i++) {
            if (_rmLevel2Data[i].deviceTypeId) allIds.push(_rmLevel2Data[i].deviceTypeId);
        }
        allTabs.push({
            text: _loginUserLanguageResource.all,
            deviceTypeId: allIds.join(','),
            isAll: true
        });
    }
    for (var i = 0; i < _rmLevel2Data.length; i++) allTabs.push(_rmLevel2Data[i]);

    if (index < 0 || index >= allTabs.length) return;

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    _rmCurrentLevel2 = allTabs[index];
    loadAllData(_rmCurrentLevel2);   // 会 resetMiddleAndRightPanels（currentDeviceId=0）

    // ★ 用恢复候选 deviceId 而非清空；真正选中的设备由 onDeviceGridSelectChanged 再写回
    saveGlobalSelection(_rmCurrentLevel2.deviceTypeId,
        _rmRestoreState.deviceId || '',
        { suppress: _rmSuppressSave.value });

    console.log('选择二级:', _rmCurrentLevel2.text, 'deviceTypeId:', _rmCurrentLevel2.deviceTypeId);
}

// ================================================================
// 加载数据
// ================================================================
function loadAllData(level2Item) {
    if (!level2Item) return;

    var deviceTypeId = level2Item.deviceTypeId || '0';
    var orgId = window.parent && window.parent.mini
        ? window.parent.mini.get('leftOrg_Id').getValue() : '';

    resetMiddleAndRightPanels();

    clearStatFilters();
    refreshDeviceList();
    loadStatCharts(deviceTypeId, orgId);
}

function resetMiddleAndRightPanels() {
    currentDeviceId = 0;
    currentMiddleTab = null;
    clearMiddleAndRightContent();
}

/**
 * 清空中间/右侧所有数据（保留结构）
 */
function clearMiddleAndRightContent() {
    // 井筒分析
    ['wellboreChart1', 'wellboreChart2', 'wellboreChart3', 'wellboreChart4'].forEach(function (id) {
        var el = document.getElementById(id);
        if (el) el.innerHTML = '';
    });

    // 地面分析
    ['surfaceChart1', 'surfaceChart2', 'surfaceChart3', 'surfaceChart4'].forEach(function (id) {
        var el = document.getElementById(id);
        if (el) el.innerHTML = '';
    });

    // 趋势曲线
    var trendBody = document.getElementById('middle_TrendCurve_body');
    if (trendBody) trendBody.innerHTML = '';

    // 动态数据
    var dynBody = document.getElementById('RealTimeMonitoringInfoDataTableInfoDiv_id');
    if (dynBody) dynBody.innerHTML = '';

    // 设备控制
    var dcContainer = document.getElementById('right_DeviceControl_container');
    if (dcContainer) dcContainer.innerHTML = '';

    // 设备信息 - 清空两个 datagrid
    var addGrid = mini.get('deviceInfoAdditionalGrid');
    if (addGrid) addGrid.setData([]);
    var auxGrid = mini.get('deviceInfoAuxiliaryGrid');
    if (auxGrid) auxGrid.setData([]);
}

/**
 * 无设备时：隐藏统计 / 中间 / 右侧三个面板
 */
function hideAllPanelsWhenNoDevice() {
    var splitterLeft  = mini.get('rmLeftVerticalSplitter');
    if (splitterLeft)  splitterLeft.hidePane(2);

    var splitterInner = mini.get('rmInnerSplitter');
    if (splitterInner) splitterInner.hidePane(2);

    var splitterMain  = mini.get('rmMainSplitter');
    if (splitterMain)  splitterMain.hidePane(2);
}

/**
 * 有设备时：若统计面板之前被隐藏，且仍有可见 tab，则恢复显示
 */
function restoreStatPanelIfHasVisibleTabs() {
    var tabs = mini.get('statTabs');
    if (!tabs) return;

    var arr = tabs.getTabs();
    var hasVisible = false;
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].visible !== false) {
            hasVisible = true;
            break;
        }
    }

    var splitter = mini.get('rmLeftVerticalSplitter');
    if (splitter && hasVisible) {
        splitter.showPane(2);
    }
}

// ================================================================
// 设备表格 - 列构建
// ================================================================
function buildGridColumns(columnsData) {
    if (!columnsData || columnsData.length === 0) return DEFAULT_COLUMNS;

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

// ================================================================
// 设备表格 - 事件
// ================================================================
function onDeviceGridBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize || 25;
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var leftOrgId = window.parent && window.parent.mini
        ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';

    var deviceCombo = mini.get('deviceCombo');
    params.deviceName = deviceCombo ? deviceCombo.getValue() : '';

    var getFieldValue = function (id) {
        var el = document.getElementById(id);
        return el ? el.value : '';
    };
    params.FESdiagramResultStatValue = getFieldValue('RealTimeMonitoringStatSelectFESdiagramResult_Id');
    params.commStatusStatValue       = getFieldValue('RealTimeMonitoringStatSelectCommStatus_Id');
    params.runStatusStatValue        = getFieldValue('RealTimeMonitoringStatSelectRunStatus_Id');
    params.numStatusStatValue        = getFieldValue('RealTimeMonitoringStatSelectNumStatus_Id');
    params.deviceTypeStatValue       = getFieldValue('RealTimeMonitoringStatSelectDeviceType_Id');
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
            var columnStrInput = document.getElementById('RealTimeMonitoringColumnStr_Id');
            if (columnStrInput) columnStrInput.value = JSON.stringify(result.columns);

            var showIndex = false, showDeviceName = false;
            for (var i = 0; i < columns.length; i++) {
                var col = columns[i];
                if (col.type === 'indexcolumn' ||
                    (col.field && col.field.toUpperCase() === 'ID')) {
                    showIndex = true;
                } else if (col.field && col.field.toUpperCase() === 'DEVICENAME') {
                    showDeviceName = true;
                }
            }

            setTimeout(function () {
                grid.setColumns(columns);
                if (showIndex && showDeviceName) grid.frozenColumns(0, 1);
                else if (showIndex || showDeviceName) grid.frozenColumns(0, 0);
                grid.doLayout();
                grid.refresh();
            }, 50);
        }
    }

    var data = grid.getData();
    if (data && data.length > 0) {
        // ★ 尝试恢复全局设备选中
        restoreDeviceSelection(grid, _rmRestoreState, _rmSuppressSave, null);
    } else {
        currentDeviceId = 0;
        currentMiddleTab = null;
        clearMiddleAndRightContent();
        hideAllPanelsWhenNoDevice();

        if (_rmSuppressSave.value) {
            setTimeout(function () { _rmSuppressSave.value = false; }, 100);
        }
    }
}

function onDeviceGridSelectChanged(e) {
    var grid = e.sender;
    var selected = grid.getSelected();
    if (selected) {
        currentDeviceId = selected.id;
        console.log('选中设备:', selected.deviceName, 'ID:', selected.id);
        refreshDeviceTabs(selected);
        // ★ 记录为恢复候选（后续刷新/切标签都基于它恢复）
        _rmRestoreState.deviceId = String(selected.id);
        // ★ 保存全局状态
        saveGlobalSelection(
            _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '',
            selected.id,
            { suppress: _rmSuppressSave.value }
        );
    }
}

// ================================================================
// 设备表格 - 单元格绘制
// ================================================================
function onDeviceGridDrawCell(e) {
    var record = e.record;
    var field = e.field;
    var value = e.value;
    if (!record || !field) return;

    var alarmShowStyle = getAlarmShowStyle() || {};
    var Data = (alarmShowStyle.Data) || {};
    var Comm = (alarmShowStyle.Comm) || {};
    var Run  = (alarmShowStyle.Run)  || {};
    var alarmInfo = record.alarmInfo || [];
    var fieldUpper = field.toUpperCase();

    // 设备名称（徽章）
    if (fieldUpper === 'DEVICENAME') {
        var badges = '';
        var counts = { 100: 0, 200: 0, 300: 0 };
        for (var i = 0; i < alarmInfo.length; i++) {
            var level = alarmInfo[i].alarmLevel;
            if (level === 100 || level === 200 || level === 300) {
                counts[level] = (counts[level] || 0) + 1;
            }
        }
        var firstColor  = (Data.FirstLevel  && Data.FirstLevel.Color)  || 'dc2828';
        var secondColor = (Data.SecondLevel && Data.SecondLevel.Color) || 'f09614';
        var thirdColor  = (Data.ThirdLevel  && Data.ThirdLevel.Color)  || 'fae600';
        if (counts[100] > 0) badges += createAlarmBadge(counts[100], firstColor);
        if (counts[200] > 0) badges += createAlarmBadge(counts[200], secondColor);
        if (counts[300] > 0) badges += createAlarmBadge(counts[300], thirdColor);

        var deviceName = value || '';
        var alarmData = JSON.stringify(counts);
        e.cellHtml = '<span class="device-name-cell" data-alarm=\'' + alarmData + '\' data-name="' + deviceName + '" style="white-space:nowrap;">' + badges + deviceName + '</span>';
        return;
    }

    // 通信状态
    if (fieldUpper === 'COMMSTATUSNAME') {
        var status = record.commStatus;
        var offlineColor  = (Comm.offline  && Comm.offline.Color)  ? '#' + Comm.offline.Color  : '#ff4d4f';
        var onlineColor   = (Comm.online   && Comm.online.Color)   ? '#' + Comm.online.Color   : '#52c41a';
        var goOnlineColor = (Comm.goOnline && Comm.goOnline.Color) ? '#' + Comm.goOnline.Color : '#faad14';
        var color = '#999';
        if (status === 0) color = offlineColor;
        else if (status === 1) color = onlineColor;
        else if (status === 2) color = goOnlineColor;
        e.cellHtml = '<span style="color:' + color + ';font-weight:bold;">' + (value || '') + '</span>';
        return;
    }

    // 运行状态
    if (fieldUpper === 'RUNSTATUSNAME') {
        var commStatus = record.commStatus;
        var runStatus = record.runStatus;
        if (commStatus == 0 || commStatus == 2 || !value) {
            e.cellHtml = '';
            return;
        }
        var stopColor   = (Run.stop   && Run.stop.Color)   ? '#' + Run.stop.Color   : '#ff4d4f';
        var runColor    = (Run.run    && Run.run.Color)    ? '#' + Run.run.Color    : '#52c41a';
        var noDataColor = (Run.noData && Run.noData.Color) ? '#' + Run.noData.Color : '#999';
        var selColor = (runStatus === 0) ? stopColor : (runStatus === 1 ? runColor : noDataColor);
        e.cellHtml = '<span style="color:' + selColor + ';font-weight:bold;">' + (value || '') + '</span>';
        return;
    }

    // 其他数据列（报警高亮）
    if (fieldUpper !== 'ID' && fieldUpper !== 'DEVICENAME' &&
        fieldUpper !== 'COMMSTATUSNAME' && fieldUpper !== 'RUNSTATUSNAME') {
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

// ================================================================
// 设备名称悬停提示
// ================================================================
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
        return '<span style="display:inline-block;background:' + bg + ';color:' + tx + ';padding:0 8px;border-radius:12px;font-size:11px;font-weight:bold;line-height:18px;margin-right:4px;white-space:nowrap;">' + text + '</span>';
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
// 刷新
// ================================================================
function refreshDeviceList() {
    var grid = mini.get('deviceGrid');
    if (grid) grid.load();
}

function onDeviceComboChange() {
    refreshDeviceList();
}

function refreshData() {
    if (_rmCurrentLevel2) loadAllData(_rmCurrentLevel2);
}

// ================================================================
// 统计饼图 - 显隐控制
// ================================================================
function loadStatCharts(deviceTypeId, orgId) {
    clearStatFilters();

    var projectTabConfig = getProjectTabInstanceInfoByDeviceType(deviceTypeId);
    var cfg = projectTabConfig.DeviceRealTimeMonitoring || {};
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

    var splitter = mini.get('rmLeftVerticalSplitter');
    if (splitter) {
        if (visibleCount === 0) {
            clearAllStatCharts();
            splitter.hidePane(2);
            return;
        } else {
            splitter.showPane(2);
        }
    }

    var currentActive = statTabs.getActiveTab();
    var currentName   = currentActive ? currentActive.name : '';

    if (currentName && config[currentName] === true) {
        loadStatData(currentActive, paramDeviceType, orgId);
    } else if (firstVisibleTab) {
        statTabs.activeTab(firstVisibleTab);
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
    var tab = e.tab;
    if (!tab) return;

    clearStatFilters();

    var deviceCombo = mini.get('deviceCombo');
    if (deviceCombo) {
        deviceCombo.setValue('');
        deviceCombo.setText('');
    }

    var deviceTypeId = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';
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
    var run  = (alarmShowStyle && alarmShowStyle.Run)  || {};
    var dataStyle = (alarmShowStyle && alarmShowStyle.Data) || {};

    for (var i = 0; i < list.length; i++) {
        var item = list[i];
        if (item.itemCode === 'all' || item.count <= 0) continue;
        var point = { name: item.item || item.text, y: item.count };

        if (tabKey === 'CommStatus') {
            if (item.itemCode === 'online')        point.color = '#' + (comm.online   ? comm.online.Color   : '52c41a');
            else if (item.itemCode === 'goOnline') point.color = '#' + (comm.goOnline ? comm.goOnline.Color : 'faad14');
            else if (item.itemCode === 'offline')  point.color = '#' + (comm.offline  ? comm.offline.Color  : 'ff4d4f');
        } else if (tabKey === 'RunStatus') {
            if (item.itemCode === 'run')           point.color = '#' + (run.run    ? run.run.Color    : '52c41a');
            else if (item.itemCode === 'stop')     point.color = '#' + (run.stop   ? run.stop.Color   : 'ff4d4f');
            else if (item.itemCode === 'noData')   point.color = '#' + (run.noData ? run.noData.Color : '999');
            else if (item.itemCode === 'goOnline') point.color = '#' + (comm.goOnline ? comm.goOnline.Color : 'faad14');
            else if (item.itemCode === 'offline')  point.color = '#' + (comm.offline  ? comm.offline.Color  : 'ff4d4f');
        } else if (tabKey === 'NumStatus') {
            var level = item.level;
            if (level === 0)        point.color = '#' + (dataStyle.Normal      ? dataStyle.Normal.BackgroundColor      : 'FFFFFF');
            else if (level === 100) point.color = '#' + (dataStyle.FirstLevel  ? dataStyle.FirstLevel.BackgroundColor  : 'DC2828');
            else if (level === 200) point.color = '#' + (dataStyle.SecondLevel ? dataStyle.SecondLevel.BackgroundColor : 'F09614');
            else if (level === 300) point.color = '#' + (dataStyle.ThirdLevel  ? dataStyle.ThirdLevel.BackgroundColor  : 'FAE600');
            point.level = level;
        }
        data.push(point);
    }
    return data.length > 0 ? data : [{ name: _loginUserLanguageResource.emptyMsg, y: 1 }];
}

function handlePieClick(e, tabKey) {
    var selectRowInput = document.getElementById('RealTimeMonitoringInfoDeviceListSelectRow_Id');
    if (selectRowInput) selectRowInput.value = -1;

    var fieldId = '';
    switch (tabKey) {
        case 'FESdiagramResult': fieldId = 'RealTimeMonitoringStatSelectFESdiagramResult_Id'; break;
        case 'CommStatus':       fieldId = 'RealTimeMonitoringStatSelectCommStatus_Id'; break;
        case 'RunStatus':        fieldId = 'RealTimeMonitoringStatSelectRunStatus_Id'; break;
        case 'NumStatus':        fieldId = 'RealTimeMonitoringStatSelectNumStatus_Id'; break;
        default: return;
    }
    var fieldInput = document.getElementById(fieldId);
    if (!fieldInput) return;

    if (e.point.selected) {
        fieldInput.value = '';
    } else {
        if (tabKey === 'NumStatus') {
            fieldInput.value = e.point.level !== undefined ? e.point.level : '';
        } else {
            fieldInput.value = e.point.name;
        }
    }

    var deviceCombo = mini.get('deviceCombo');
    if (deviceCombo) {
        deviceCombo.setValue('');
        deviceCombo.setText('');
    }
    refreshDeviceList();
}

// ================================================================
// 饼图
// ================================================================
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
            layout: 'horizontal',
            itemHiddenStyle: { textDecoration: 'none' }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true, cursor: 'pointer',
                dataLabels: {
                    enabled: true, color: '#000000', connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.y}'
                },
                showInLegend: true,
                events: {
                    click: function (e) { handlePieClick(e, tabKey); }
                }
            }
        },
        exporting: { enabled: true, filename: title, fallbackToExportServer: false },
        series: [{ type: 'pie', name: '数量', data: data }]
    });
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

function recreatePieChart(container) {
    if (!container || !container._pieData) return;
    var divId = container.id;
    var data = container._pieData;
    var title = container._pieTitle;
    var tabKey = container._pieTabKey;

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
// 中间/右侧 Tab - 根据设备配置显隐
// ================================================================
function refreshDeviceTabs(selected) {
    if (!selected) return;

    restoreStatPanelIfHasVisibleTabs();

    var deviceInfo = getDeviceTabInstanceInfoByDeviceId(selected.id);
    var config = deviceInfo.config || {};
    var drt = config.DeviceRealTimeMonitoring || {};
    var calculateType = selected.calculateType || 0;

    // ---------- 中间 tab ----------
    var allowedMiddleNames = [];
    if (drt.WellboreAnalysis === true && calculateType == 1) allowedMiddleNames.push('middle_WellboreAnalysis');
    if (drt.SurfaceAnalysis  === true && calculateType == 1) allowedMiddleNames.push('middle_SurfaceAnalysis');
    if (drt.TrendCurve === true) allowedMiddleNames.push('middle_TrendCurve');
    if (drt.DynamicData === true) allowedMiddleNames.push('middle_DynamicData');

    applyMiddleTabsVisibility(allowedMiddleNames);

    // ---------- 右侧 tab ----------
    var allowedRightNames = [];
    if (drt.DeviceControl    === true) allowedRightNames.push('right_DeviceControl');
    if (drt.DeviceInformation === true) allowedRightNames.push('right_DeviceInfo');

    applyRightTabsVisibility(allowedRightNames);

    window._currentCalculateType = calculateType;
}

function applyMiddleTabsVisibility(allowedNames) {
    if (!middleTabs) return;

    var all = middleTabs.getTabs();
    var visibleCount = 0;
    var firstVisible = null;

    for (var i = 0; i < all.length; i++) {
        var isVisible = allowedNames.indexOf(all[i].name) !== -1;
        middleTabs.updateTab(all[i], { visible: isVisible });
        if (isVisible) {
            visibleCount++;
            if (!firstVisible) firstVisible = all[i];
        }
    }

    var splitterInner = mini.get('rmInnerSplitter');

    if (visibleCount === 0) {
        if (splitterInner) splitterInner.hidePane(2);
        currentMiddleTab = null;
        return;
    }

    if (splitterInner) splitterInner.showPane(2);

    var currentActive = middleTabs.getActiveTab();
    var currentName   = currentActive ? currentActive.name : '';

    if (currentName && allowedNames.indexOf(currentName) !== -1) {
        currentMiddleTab = currentName;
        refreshMiddleTabs();
    } else if (firstVisible) {
        currentMiddleTab = firstVisible.name;
        middleTabs.activeTab(firstVisible);
    }
}

function applyRightTabsVisibility(allowedNames) {
    if (!rightTabs) return;

    var all = rightTabs.getTabs();
    var visibleCount = 0;
    var firstVisible = null;

    for (var i = 0; i < all.length; i++) {
        var isVisible = allowedNames.indexOf(all[i].name) !== -1;
        rightTabs.updateTab(all[i], { visible: isVisible });
        if (isVisible) {
            visibleCount++;
            if (!firstVisible) firstVisible = all[i];
        }
    }

    var splitterMain = mini.get('rmMainSplitter');

    if (visibleCount === 0) {
        if (splitterMain) splitterMain.hidePane(2);
        return;
    }

    if (splitterMain) splitterMain.showPane(2);

    var currentActive = rightTabs.getActiveTab();
    var currentName   = currentActive ? currentActive.name : '';

    if (currentName && allowedNames.indexOf(currentName) !== -1) {
        refreshRightTabs();
    } else if (firstVisible) {
        rightTabs.activeTab(firstVisible);
    }
}

// ================================================================
// 中间 tab 事件与数据加载
// ================================================================
function onMiddleTabChanged(e) {
    var tab = e.tab;
    if (!tab) return;
    currentMiddleTab = tab.name;
    console.log('中间Tab切换:', currentMiddleTab);

    if (currentDeviceId > 0) {
        refreshMiddleTabs();
    }
}

function refreshMiddleTabs() {
    if (!currentMiddleTab || currentDeviceId <= 0) return;
    switch (currentMiddleTab) {
        case 'middle_WellboreAnalysis': loadWellboreAnalysis(currentDeviceId); break;
        case 'middle_SurfaceAnalysis':  loadSurfaceAnalysis(currentDeviceId);  break;
        case 'middle_TrendCurve':       loadTrendCurve(currentDeviceId);       break;
        case 'middle_DynamicData':      loadDynamicData(currentDeviceId);      break;
        default: break;
    }
}

// ---------- 井筒分析 ----------
function loadWellboreAnalysis(deviceId) {
    if (!deviceId || deviceId <= 0) return;
    var body = document.getElementById('middle_WellboreAnalysis_body');
    if (!body) return;

    // 清空 4 个图表容器，保留结构
    ['wellboreChart1', 'wellboreChart2', 'wellboreChart3', 'wellboreChart4'].forEach(function (id) {
        var el = document.getElementById(id);
        if (el) el.innerHTML = '';
    });

    mini.mask({
        el: 'middle_WellboreAnalysis_body',
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/realTimeMonitoringController/querySingleFESDiagramDetailsChartsData',
        type: 'POST',
        data: { id: deviceId, type: 1 },
        dataType: 'json',
        timeout: 15000,
        success: function (result) {
            mini.unmask('middle_WellboreAnalysis_body');

            if (result && result.positionCurveData && result.loadCurveData) {
                showFSDiagramFromPumpcard(result, 'wellboreChart1');
                showRodPress(result, 'wellboreChart2');
                showPumpCard(result, 'wellboreChart3');
                showPumpEfficiency(result, 'wellboreChart4');
            } else {
                ['wellboreChart1', 'wellboreChart2', 'wellboreChart3', 'wellboreChart4'].forEach(function (id) {
                    var el = document.getElementById(id);
                    if (el) el.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                });
            }
        },
        error: function (xhr, status) {
            mini.unmask('middle_WellboreAnalysis_body');
            console.log('请求失败:', status);
            ['wellboreChart1', 'wellboreChart2', 'wellboreChart3', 'wellboreChart4'].forEach(function (id) {
                var el = document.getElementById(id);
                if (el) el.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">'
                    + _loginUserLanguageResource.requestFailed + '</div>';
            });
        }
    });
}

// ---------- 地面分析 ----------
function loadSurfaceAnalysis(deviceId) {
    if (!deviceId || deviceId <= 0) return;
    var body = document.getElementById('middle_SurfaceAnalysis_body');
    if (!body) return;

    ['surfaceChart1', 'surfaceChart2', 'surfaceChart3', 'surfaceChart4'].forEach(function (id) {
        var el = document.getElementById(id);
        if (el) el.innerHTML = '';
    });

    mini.mask({
        el: 'middle_SurfaceAnalysis_body',
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/realTimeMonitoringController/querySingleFESDiagramDetailsChartsData',
        type: 'POST',
        data: { id: deviceId, type: 2 },
        dataType: 'json',
        timeout: 15000,
        success: function (result) {
            mini.unmask('middle_SurfaceAnalysis_body');

            if (result && result.positionCurveData && result.powerCurveData) {
                showPSDiagram(result, 'surfaceChart1');

                if (result.crankAngle && result.loadRorque && result.crankTorque &&
                    result.currentBalanceTorque && result.currentNetTorque) {
                    showBalanceAnalysisCurveChart(
                        result.crankAngle, result.loadRorque, result.crankTorque,
                        result.currentBalanceTorque, result.currentNetTorque,
                        _loginUserLanguageResource.currentTorqueCurve,
                        result.deviceName || '', result.acqTime || '',
                        'surfaceChart2'
                    );
                } else {
                    document.getElementById('surfaceChart2').innerHTML =
                        '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                }

                showASDiagram(result, 'surfaceChart3');

                if (result.crankAngle && result.loadRorque && result.crankTorque &&
                    result.expectedBalanceTorque && result.expectedNetTorque) {
                    var deltaRadius = parseFloat(result.deltaRadius) || 0;
                    var expectedTitle = _loginUserLanguageResource.expectTorqueCurve;
                    if (deltaRadius !== 0) {
                        expectedTitle = (deltaRadius > 0
                            ? _loginUserLanguageResource.moveTowardOutside
                            : _loginUserLanguageResource.moveTowardInside)
                            + Math.abs(deltaRadius) + 'cm ' + expectedTitle;
                    }
                    showBalanceAnalysisCurveChart(
                        result.crankAngle, result.loadRorque, result.crankTorque,
                        result.expectedBalanceTorque, result.expectedNetTorque,
                        expectedTitle,
                        result.deviceName || '', result.acqTime || '',
                        'surfaceChart4'
                    );
                } else {
                    document.getElementById('surfaceChart4').innerHTML =
                        '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                }
            } else {
                ['surfaceChart1', 'surfaceChart2', 'surfaceChart3', 'surfaceChart4'].forEach(function (id) {
                    var el = document.getElementById(id);
                    if (el) el.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                });
            }
        },
        error: function () {
            mini.unmask('middle_SurfaceAnalysis_body');
            ['surfaceChart1', 'surfaceChart2', 'surfaceChart3', 'surfaceChart4'].forEach(function (id) {
                var el = document.getElementById(id);
                if (el) el.innerHTML = '<div class="loading-placeholder" style="color:#ff4d4f;">'
                    + _loginUserLanguageResource.requestFailed + '</div>';
            });
        }
    });
}

// ---------- 趋势曲线 ----------
function loadTrendCurve(deviceId) {
    if (!deviceId || deviceId <= 0) return;
    var body = document.getElementById('middle_TrendCurve_body');
    if (!body) return;

    body.innerHTML = '';

    mini.mask({
        el: 'middle_TrendCurve_body',
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    var grid = mini.get('deviceGrid');
    var deviceName = '';
    var calculateType = 0;
    if (grid) {
        var selected = grid.getSelected();
        if (selected) {
            deviceName = selected.deviceName || '';
            calculateType = selected.calculateType || 0;
            if (!deviceId || deviceId === 0) deviceId = selected.id || 0;
        }
    }
    var deviceType = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';

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
        success: function (result) {
            mini.unmask('middle_TrendCurve_body');
            body.innerHTML = '';

            if (!result || !result.list || result.list.length === 0) {
                body.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            var data = result.list;
            var curveNames = result.curveItems || [];
            var deviceNameResult = result.deviceName || deviceName || '';
            var curveCount = data.length > 0 ? data[0].data.length : 0;

            if (curveCount === 0) {
                body.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            if (curveNames.length < curveCount) {
                for (var i = curveNames.length; i < curveCount; i++) curveNames.push('曲线' + (i + 1));
            }

            var curveConf = result.curveConf || [];
            var defaultColors = ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9',
                                 '#f15c80', '#e4d354', '#2b908f', '#f45b5b', '#91e8e1'];
            var colors = [];
            for (var i = 0; i < curveConf.length; i++) {
                colors.push(curveConf[i].color ? ('#' + curveConf[i].color) : defaultColors[i % 10]);
            }

            var chartWidth, chartHeight;
            if (curveCount == 1)      { chartWidth = '100%'; chartHeight = '100%'; }
            else if (curveCount == 2) { chartWidth = '100%'; chartHeight = '50%'; }
            else                       { chartWidth = '50%';  chartHeight = '50%'; }

            var container = document.createElement('div');
            container.id = 'trendContainer';
            body.appendChild(container);

            for (var i = 0; i < curveCount; i++) {
                var divId = 'trendChart_' + i + '_' + Date.now();
                var chartDiv = document.createElement('div');
                chartDiv.className = 'trend-chart-item';
                chartDiv.style.cssText =
                    'flex: 1 1 ' + chartWidth + '; ' +
                    'height: ' + chartHeight + '; ' +
                    'min-width: 300px; ' +
                    'min-height: ' + dynamometerCardMinHeight + 'px; ' +
                    'box-sizing: border-box; ' +
                    'padding: 2px; ' +
                    'position: relative; ' +
                    'overflow: hidden;';
                container.appendChild(chartDiv);

                var innerDiv = document.createElement('div');
                innerDiv.className = 'trend-chart-container';
                innerDiv.id = divId + '_inner';
                innerDiv.style.width = '100%';
                innerDiv.style.height = '100%';
                chartDiv.appendChild(innerDiv);

                var seriesData = [];
                for (var j = 0; j < data.length; j++) {
                    var timestamp = Date.parse(data[j].acqTime.replace(/-/g, '/'));
                    var val = parseFloat(data[j].data[i]);
                    if (!isNaN(val)) seriesData.push([timestamp, val]);
                }

                if (seriesData.length === 0) {
                    innerDiv.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                    continue;
                }

                var yTitle = curveNames[i];
                var titleText = deviceNameResult + ':' + yTitle +
                    (_loginUserLanguage != 'zh_CN' ? ' ' : '') + _loginUserLanguageResource.trendCurve;
                var conf = (curveConf && curveConf.length > i) ? curveConf[i] : {};
                var color = (colors && colors.length > i) ? colors[i] : defaultColors[i % 10];
                var lineWidth = conf.lineWidth || 2;
                var dashStyle = conf.dashStyle || 'Solid';
                var yAxisOpposite = conf.yAxisOpposite || false;

                var series = [{
                    name: yTitle, data: seriesData,
                    lineWidth: lineWidth, dashStyle: dashStyle,
                    marker: { enabled: true, radius: 2 }
                }];

                var allPositive = true, allNegative = true;
                for (var k = 0; k < seriesData.length; k++) {
                    var v = seriesData[k][1];
                    if (v < 0) allPositive = false;
                    if (v >= 0) allNegative = false;
                }
                var maxValue = allNegative ? 0 : null;
                var minValue = allPositive ? 0 : null;

                initDeviceRealtimeMonitoringStockChartFn(
                    series, undefined, innerDiv.id, titleText, '',
                    _loginUserLanguageResource.time, yTitle, [color],
                    false, true, false, undefined, maxValue, minValue, yAxisOpposite
                );
            }
        },
        error: function (xhr, status) {
            mini.unmask('middle_TrendCurve_body');
            console.error(_loginUserLanguageResource.requestFailed + ':', status);
            body.innerHTML = '<div class="loading-placeholder error">'
                + _loginUserLanguageResource.requestFailed + '</div>';
        }
    });
}

// ---------- Highstock 绘图 ----------
function initDeviceRealtimeMonitoringStockChartFn(series, tickInterval, divId, title, subtitle,
                                                   xtitle, yTitle, color, legend, navigator,
                                                   scrollbar, timeFormat, maxValue, minValue, yAxisOpposite) {
    if ($("#" + divId).length === 0) return;
    var lang = _loginUserLanguageResource || {};
    var hourLabel = lang.hour;
    var allLabel = lang.all;
    var fontSize = chartTitleFontSize;

    new Highcharts.stockChart({
        chart: {
            renderTo: divId, type: 'spline', shadow: false, borderWidth: 0,
            zooming: { mouseWheel: { enabled: false } },
            zoomType: 'xy', animation: false
        },
        time: { timezoneOffset: new Date().getTimezoneOffset() },
        credits: { enabled: false },
        navigator: {
            enabled: navigator !== false,
            maskInside: true,
            series: {
                data: series[0].data,
                dataGrouping: { enabled: true, groupPixelWidth: 8, approximation: 'average' },
                turboThreshold: 5000, animation: false
            }
        },
        scrollbar: { enabled: scrollbar === true },
        rangeSelector: {
            buttons: [
                { count: 1,  type: 'hour', text: '1'  + hourLabel },
                { count: 6,  type: 'hour', text: '6'  + hourLabel },
                { count: 12, type: 'hour', text: '12' + hourLabel },
                { count: 24, type: 'hour', text: '24' + hourLabel },
                { type: 'all', text: allLabel }
            ],
            buttonTheme: { width: getLabelWidth('24' + hourLabel) },
            dropdown: 'responsive', inputEnabled: false, selected: 0
        },
        title: { text: title, style: { fontSize: fontSize } },
        subtitle: { text: subtitle },
        colors: color,
        xAxis: {
            type: 'datetime',
            title: { text: xtitle },
            tickPixelInterval: 120,
            minTickInterval: 5 * 60 * 1000,
            labels: {
                formatter: function () {
                    var minTime = this.axis.min, maxTime = this.axis.max;
                    var minDate = new Date(minTime), maxDate = new Date(maxTime);
                    minDate.setHours(0, 0, 0, 0);
                    maxDate.setHours(0, 0, 0, 0);
                    return minDate.getTime() !== maxDate.getTime()
                        ? this.axis.chart.time.dateFormat('%m-%d %H:%M', this.value)
                        : this.axis.chart.time.dateFormat('%H:%M', this.value);
                },
                autoRotation: true, rotation: -45
            }
        },
        yAxis: {
            max: maxValue || null, min: minValue || null,
            lineWidth: 1, tickWidth: 1, tickLength: 5,
            title: { text: yTitle },
            opposite: yAxisOpposite || false
        },
        tooltip: {
            crosshairs: true, shared: true, valueDecimals: 2,
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
            enabled: true, filename: title, fallbackToExportServer: false,
            sourceWidth: $("#" + divId)[0] ? $("#" + divId)[0].offsetWidth : null,
            sourceHeight: $("#" + divId)[0] ? $("#" + divId)[0].offsetHeight : null,
            buttons: {
                contextButton: {
                    menuItems: ['viewFullscreen', 'printChart', 'separator',
                                'downloadPNG', 'downloadJPEG', 'downloadSVG',
                                'separator', 'downloadCSV', 'downloadXLS']
                }
            }
        },
        plotOptions: {
            spline: {
                lineWidth: 1, fillOpacity: 0.3,
                marker: {
                    enabled: true, radius: 3,
                    states: { hover: { enabled: true, radius: 6 } }
                },
                shadow: true,
                dataGrouping: { enabled: false, groupPixelWidth: 20, approximation: 'average' },
                turboThreshold: 5000, animation: false
            }
        },
        legend: {
            layout: 'horizontal', align: 'center', verticalAlign: 'bottom',
            enabled: legend || false, borderWidth: 0,
            itemHiddenStyle: { textDecoration: 'none' }
        },
        series: series
    });
}

// ---------- 动态数据 ----------
function loadDynamicData(deviceId) {
    if (!deviceId || deviceId <= 0) return;
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
            if (!deviceId || deviceId === 0) deviceId = selected.id || 0;
        }
    }
    var deviceType = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';

    container.innerHTML = '';
    mini.mask({
        el: 'RealTimeMonitoringInfoDataTableInfoDiv_id',
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/realTimeMonitoringController/getDeviceRealTimeMonitoringData',
        type: 'POST',
        data: { deviceId: deviceId, deviceName: deviceName, deviceType: deviceType, calculateType: calculateType },
        dataType: 'json',
        timeout: 15000,
        success: function (result) {
            mini.unmask('RealTimeMonitoringInfoDataTableInfoDiv_id');

            if (result.totalRoot && result.totalRoot.length > 0) {
                createDeviceRealTimeMonitoringGrid('RealTimeMonitoringInfoDataTableInfoDiv_id', result.totalRoot, result.CellInfo);
            } else {
                container.innerHTML = '<div class="loading-placeholder">'
                    + _loginUserLanguageResource.emptyMsg + '</div>';
            }
        },
        error: function (xhr, status) {
            mini.unmask('RealTimeMonitoringInfoDataTableInfoDiv_id');
            console.error(_loginUserLanguageResource.requestFailed + ':', status);
            container.innerHTML = '<div class="loading-placeholder error">'
                + _loginUserLanguageResource.requestFailed + '</div>';
        }
    });
}

// ================================================================
// 动态数据表格
// ================================================================
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
        style: 'width:100%; height:100%; visibility:hidden;',
        showPager: false, showColumns: false,
        allowCellSelect: true, allowCellWrap: false, allowResize: true,
        allowCellMerge: true, virtualScroll: false, allowAlternating: true,
        data: data || [],
        columns: [
            { field: 'name1',  width: '16%', align: 'center', headerAlign: 'center' },
            { field: 'value1', width: '16%', align: 'center', headerAlign: 'center' },
            { field: 'name2',  width: '16%', align: 'center', headerAlign: 'center' },
            { field: 'value2', width: '16%', align: 'center', headerAlign: 'center' },
            { field: 'name3',  width: '16%', align: 'center', headerAlign: 'center' },
            { field: 'value3', width: '16%', align: 'center', headerAlign: 'center' }
        ],
        ondrawcell: function (e) { applyCellStyle(e); },
        oncelldblclick: function (e) { handleCellDblClick(e); },
        onrender: function () {
            var merges = [{ rowIndex: 0, columnIndex: 0, rowSpan: 1, colSpan: 6 }];
            mergeDataGridCell(this, merges);
            this.setStyle('visibility:visible;');
        }
    });

    deviceRealTimeMonitoringGrid.render(container);

    if (data && data.length > 0) {
        setTimeout(function () {
            var merges = [{ rowIndex: 0, columnIndex: 0, rowSpan: 1, colSpan: 6 }];
            mergeDataGridCell(deviceRealTimeMonitoringGrid, merges);
            deviceRealTimeMonitoringGrid.setStyle('visibility:visible;');
        }, 100);
    }
}

function mergeDataGridCell(grid, merges) {
    if (!grid) return;
    try {
        grid.mergeCells(merges);
    } catch (e) {
        setTimeout(function () {
            try { grid.mergeCells(merges); } catch (e2) {}
        }, 200);
    }
}

function applyCellStyle(e) {
    var grid = e.sender;
    var cellInfo = grid._cellInfo || [];

    e.cellStyle = '';
    var field = e.field;
    var rowIndex = e.rowIndex;

    if (rowIndex === 0) {
        e.cellStyle = 'font-size:20px; height:40px; font-weight:bold;';
        return;
    }
    if (!cellInfo) return;

    var alarmShowStyle = getAlarmShowStyle();
    var groupMap = { name1: 0, value1: 0, name2: 1, value2: 1, name3: 2, value3: 2 };
    var groupIndex = groupMap[field];
    if (groupIndex === undefined) return;

    for (var i = 0; i < cellInfo.length; i++) {
        var info = cellInfo[i];
        if (info.row === rowIndex && info.col === groupIndex) {
            var isNameColumn  = field.indexOf('name') === 0;
            var isValueColumn = field.indexOf('value') === 0;

            if (isNameColumn) {
                if (isNotVal(info.realtimeColor))   e.cellStyle += 'color:#' + info.realtimeColor + ';';
                if (isNotVal(info.realtimeBgColor)) e.cellStyle += 'background-color:#' + info.realtimeBgColor + ';';
            } else if (isValueColumn) {
                var alarmLevel = info.alarmLevel;
                if (alarmLevel > 0) e.cellStyle += 'font-weight:bold;';
                var styleCfg = getAlarmStyleByLevel(alarmLevel, alarmShowStyle);
                if (styleCfg) {
                    if (styleCfg.bg)    e.cellStyle += 'background-color:' + styleCfg.bg + ';';
                    if (styleCfg.color) e.cellStyle += 'color:' + styleCfg.color + ';';
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

    var rowIndex = grid.indexOf(record);
    var field = e.field || (e.column ? e.column.field : null);
    if (!field) return;
    if (rowIndex === 0) return;

    var groupMap = { name1: 0, value1: 0, name2: 1, value2: 1, name3: 2, value3: 2 };
    var groupIndex = groupMap[field];
    if (groupIndex === undefined) return;

    var cellInfo = grid._cellInfo || [];
    var info = null;
    for (var i = 0; i < cellInfo.length; i++) {
        if (cellInfo[i].row === rowIndex && cellInfo[i].col === groupIndex) {
            info = cellInfo[i];
            break;
        }
    }
    if (!info) return;

    viewDeviceRealTimeMonitoringData(rowIndex, groupIndex);
}

// ---------- 双击查看曲线或数据表 ----------
function viewDeviceRealTimeMonitoringData(row, col) {
    if (!deviceRealTimeMonitoringGrid || row < 1) return;
    var grid = deviceRealTimeMonitoringGrid;
    var record = grid.getAt(row);
    if (!record) return;

    var itemName  = record['name' + (col + 1)];
    var itemValue = record['value' + (col + 1)];

    var cellInfo = grid._cellInfo || [];
    var info = null;
    for (var i = 0; i < cellInfo.length; i++) {
        if (cellInfo[i].row === row && cellInfo[i].col === col) {
            info = cellInfo[i];
            break;
        }
    }
    if (!info) { console.warn('未找到 CellInfo:', row, col); return; }

    var isNumeric = function (val) {
        return val !== undefined && val !== null && val !== '' && !isNaN(parseFloat(val));
    };

    var type = info.type;
    var resolutionMode = info.resolutionMode;
    var columnDataType = info.columnDataType || '';
    var column = info.column;

    if (type == 0) {
        if (resolutionMode == 2) {
            if (columnDataType.toUpperCase() !== 'STRING' && isNumeric(itemValue)) {
                viewItemRealTimeCurve(itemName, itemValue, info);
            } else {
                viewItemRealTimeDataTable(itemName, itemValue, info);
            }
        } else {
            viewItemRealTimeDataTable(itemName, itemValue, info);
        }
    } else if (type == 1) {
        if (isNumByCalculateItemCode(column)) viewItemRealTimeCurve(itemName, itemValue, info);
        else viewItemRealTimeDataTable(itemName, itemValue, info);
    } else if (type == 3) {
        if (isNumeric(itemValue)) viewItemRealTimeCurve(itemName, itemValue, info);
        else viewItemRealTimeDataTable(itemName, itemValue, info);
    } else if (type == 5) {
        if (resolutionMode == 2 || resolutionMode == 7) {
            if (isNumeric(itemValue)) viewItemRealTimeCurve(itemName, itemValue, info);
            else viewItemRealTimeDataTable(itemName, itemValue, info);
        } else {
            viewItemRealTimeDataTable(itemName, itemValue, info);
        }
    } else {
        viewItemRealTimeDataTable(itemName, itemValue, info);
    }
}

/**
 * 双击查看曲线 - 打开独立 JSP
 */
function viewItemRealTimeCurve(itemName, itemValue, cellInfo) {
    var grid = mini.get('deviceGrid');
    var selected = grid ? grid.getSelected() : null;
    if (!selected) { mini.alert(_loginUserLanguageResource.checkOne); return; }

    mini.open({
        title: _loginUserLanguageResource.trendCurve,
        url: context + '/miniui-app/modules/realTimeMonitoring/deviceItemRealTimeCurveWindow.jsp',
        width: '70%',
        height: '60%',
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            if (!iframe || !iframe.contentWindow) return;
            iframe.contentWindow.setData({
                deviceId:           selected.id,
                deviceName:         selected.deviceName,
                calculateType:      selected.calculateType || 0,
                itemName:           itemName,
                itemCode:           cellInfo.column,
                itemType:           cellInfo.type,
                itemResolutionMode: cellInfo.resolutionMode
            });
        }
    });
}

/**
 * 双击查看数据表 - 打开独立 JSP
 */
function viewItemRealTimeDataTable(itemName, itemValue, cellInfo) {
    var grid = mini.get('deviceGrid');
    var selected = grid ? grid.getSelected() : null;
    if (!selected) { mini.alert(_loginUserLanguageResource.checkOne); return; }

    mini.open({
        title: _loginUserLanguageResource.dynamicData,
        url: context + '/miniui-app/modules/realTimeMonitoring/deviceItemRealTimeDataWindow.jsp',
        width: 500,
        height: '80%',
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            if (!iframe || !iframe.contentWindow) return;
            iframe.contentWindow.setData({
                deviceId:           selected.id,
                deviceName:         selected.deviceName,
                calculateType:      selected.calculateType || 0,
                itemName:           itemName,
                itemCode:           cellInfo.column,
                itemType:           cellInfo.type,
                itemResolutionMode: cellInfo.resolutionMode,
                itemBitIndex:       cellInfo.bitIndex || ''
            });
        }
    });
}

// ================================================================
// 右侧 tab 事件与数据加载
// ================================================================
function onRightTabChanged(e) {
    var tab = e.tab;
    if (!tab) return;
    refreshRightTabs();
}

function refreshRightTabs() {
    if (!rightTabs) return;

    if (!currentDeviceId || currentDeviceId <= 0) {
        var c1 = document.getElementById('right_DeviceControl_container');
        if (c1) c1.innerHTML = '';
        var addG = mini.get('deviceInfoAdditionalGrid');
        if (addG) addG.setData([]);
        var auxG = mini.get('deviceInfoAuxiliaryGrid');
        if (auxG) auxG.setData([]);
        return;
    }

    var activeTab = rightTabs.getActiveTab();
    if (!activeTab) return;
    switch (activeTab.name) {
        case 'right_DeviceControl': loadDeviceControl(); break;
        case 'right_DeviceInfo':    loadDeviceInfo();    break;
        default: break;
    }
}

function loadDeviceControl() {
    var container = document.getElementById('right_DeviceControl_container');
    if (!container) return;

    var grid = mini.get('deviceGrid');
    if (!grid || !grid.getSelected()) {
        container.innerHTML = '';
        return;
    }
    var selected = grid.getSelected();
    var deviceType = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';

    container.innerHTML = '';
    mini.mask({
        el: 'right_DeviceControl_container',
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/realTimeMonitoringController/getDeviceControlData',
        type: 'POST',
        data: { deviceId: selected.id, deviceName: selected.deviceName, deviceType: deviceType },
        dataType: 'json',
        timeout: 10000,
        success: function (result) {
            mini.unmask('right_DeviceControl_container');
            container.innerHTML = '';

            if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            var g = new mini.DataGrid();
            g.set({
                style: 'width:100%;height:100%;',
                data: result.totalRoot, idField: 'id',
                showPager: false, allowResize: true,
                columns: [
                    { field: 'item', header: _loginUserLanguageResource.controlItem, width: '40%', align: 'left' },
                    { field: 'action', header: _loginUserLanguageResource.operation, width: '60%', align: 'center',
                      renderer: function (e) {
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
                            for (var i = 0; i < itemMeaning.length; i++) {
                                var text = itemMeaning[i][1];
                                var value = itemMeaning[i][0];
                                html += '<button class="mini-button" plain="true" style="' + btnStyle + '" '
                                    + (disabled ? 'disabled' : '')
                                    + ' onclick="onEnumControlClick(' + record.id + ', \'' + record.item + '\', \''
                                    + record.itemcode + '\', \'' + record.quantity + '\', \'' + value + '\', \''
                                    + text + '\', ' + disabled + ')">' + text + '</button>';
                            }
                        } else if (resolutionMode == 0) {
                            for (var i = 0; i < itemMeaning.length; i++) {
                                var text = itemMeaning[i].status;
                                var value = itemMeaning[i].value;
                                var bitIndex = itemMeaning[i].bitIndex;
                                html += '<button class="mini-button" plain="true" style="' + btnStyle + '" '
                                    + (disabled ? 'disabled' : '')
                                    + ' onclick="onSwitchControlClick(' + record.id + ', \'' + record.item + '\', \''
                                    + record.itemcode + '\', \'' + record.quantity + '\', ' + value + ', ' + bitIndex
                                    + ', \'' + text + '\', ' + disabled + ')">' + text + '</button>';
                            }
                        } else {
                            html += '<button class="mini-button" plain="true" style="' + btnStyle + '" '
                                + (disabled ? 'disabled' : '')
                                + ' onclick="onNumericControlClick(' + record.id + ', \'' + record.itemcode + '\', \''
                                + record.itemName + '\', \'' + (record.unit || '') + '\', ' + record.quantity
                                + ', \'' + record.storeDataType + '\', ' + disabled + ')">'
                                + _loginUserLanguageResource.set + '</button>';
                        }
                        html += '</div>';
                        return html;
                      }
                    }
                ]
            });
            g.render(container);
        },
        error: function () {
            mini.unmask('right_DeviceControl_container');
            container.innerHTML = '<div class="loading-placeholder error">'
                + _loginUserLanguageResource.requestFailed + '</div>';
        }
    });
}

// ---------- 控制按钮 ----------
function onEnumControlClick(recordId, item, controlType, quantity, value, text, disabled) {
    if (disabled) return;
    var grid = mini.get('deviceGrid');
    var selected = grid.getSelected();
    if (!selected) return;

    var tipInfo = _loginUserLanguageResource.deviceName + ": <font color=red>" + selected.deviceName + "</font>";
    tipInfo += "</br>" + item + ": <font color=red>" + text + "</font>";
    tipInfo += "</br>" + _loginUserLanguageResource.confirmOperation;

    mini.confirm(tipInfo, _loginUserLanguageResource.tip, function (action) {
        if (action === 'ok') {
            sendDeviceControl(selected.id, selected.deviceName, controlType, quantity, value, null);
        }
    });
}

function onSwitchControlClick(recordId, item, controlType, quantity, value, bitIndex, text, disabled) {
    if (disabled) return;
    var grid = mini.get('deviceGrid');
    var selected = grid.getSelected();
    if (!selected) return;

    var tipInfo = _loginUserLanguageResource.deviceName + ": <font color=red>" + selected.deviceName + "</font>";
    tipInfo += "</br>" + item + ": <font color=red>" + text + "</font>";
    tipInfo += "</br>" + _loginUserLanguageResource.confirmOperation;

    mini.confirm(tipInfo, _loginUserLanguageResource.tip, function (action) {
        if (action === 'ok') {
            sendDeviceControl(selected.id, selected.deviceName, controlType, quantity, value, bitIndex);
        }
    });
}

/**
 * 数值型控制项 - 打开设置窗口（独立 JSP）
 */
function onNumericControlClick(recordId, controlType, itemName, unit, quantity, storeDataType, disabled) {
    if (disabled) return;

    var grid = mini.get('deviceGrid');
    var selected = grid ? grid.getSelected() : null;
    if (!selected) return;

    var deviceType = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';

    mini.open({
        title: _loginUserLanguageResource.deviceControl,
        url: context + '/miniui-app/modules/realTimeMonitoring/deviceNumericControlWindow.jsp',
        width: 800,
        height: 410,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            if (!iframe || !iframe.contentWindow) return;

            iframe.contentWindow.setData({
                deviceId:      selected.id,
                deviceName:    selected.deviceName,
                deviceType:    deviceType,
                calculateType: selected.calculateType || 0,
                controlType:   controlType,
                itemName:      itemName,
                unit:          unit,
                quantity:      quantity,
                storeDataType: storeDataType
            });
        }
    });
}

function sendDeviceControl(deviceId, deviceName, controlType, quantity, controlValue, bitIndex) {
    var params = {
        deviceId: deviceId, deviceName: deviceName,
        controlType: controlType, quantity: quantity, controlValue: controlValue
    };
    if (bitIndex !== null && bitIndex !== undefined) params.bitIndex = bitIndex;

    mini.mask({ el: document.body, cls: 'mini-mask-loading', html: '指令发送中...' });

    $.ajax({
        url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
        type: 'POST', data: params, dataType: 'json', timeout: 10000,
        success: function (result) {
            mini.unmask(document.body);
            if (result.flag == false) mini.alert(result.msg);
            else mini.alert(result.msg);
        },
        error: function (xhr, status) {
            mini.unmask(document.body);
            mini.alert(_loginUserLanguageResource.requestFailed + '：' + status);
        }
    });
}

// ---------- 设备信息（miniui datagrid） ----------
function loadDeviceInfo() {
    var grid = mini.get('deviceGrid');
    if (!grid || !grid.getSelected()) return;

    var selected = grid.getSelected();
    var deviceType = _rmCurrentLevel1 ? _rmCurrentLevel1.deviceTypeId : '0';

    var addGrid = mini.get('deviceInfoAdditionalGrid');
    var auxGrid = mini.get('deviceInfoAuxiliaryGrid');

    if (addGrid) addGrid.loading();
    if (auxGrid) auxGrid.loading();

    $.ajax({
        url: context + '/realTimeMonitoringController/getDeviceAddInfoData',
        type: 'POST',
        data: {
            deviceId: selected.id,
            wellName: selected.deviceName || '',
            calculateType: selected.calculateType || 0,
            deviceType: deviceType
        },
        dataType: 'json',
        timeout: 10000,
        success: function (result) {
            var infoList = (result && result.deviceInfoDataList) || [];
            var auxList  = (result && result.auxiliaryDeviceList) || [];

            // ---------- 附加信息 ----------
            if (addGrid) {
                var addData = [];
                for (var i = 0; i < infoList.length; i++) {
                    addData.push({
                        rowId: i,
                        name:  infoList[i].name || '',
                        value: infoList[i].value || ''
                    });
                }
                addGrid.setData(addData);
            }

            // ---------- 辅件设备 ----------
            if (auxGrid) {
                var auxData = [];
                for (var k = 0; k < auxList.length; k++) {
                    auxData.push({
                        rowId: k,
                        name: auxList[k].name || '',
                        detailsInfo: auxList[k].detailsInfo || ''
                    });
                }
                auxGrid.setData(auxData);

                // 默认全部展开详情
                setTimeout(function () {
                	if(auxData.length>0){
                		auxGrid.showRowDetail(0);
                	}
                }, 50);
            }
        },
        error: function () {
            if (addGrid) addGrid.setData([]);
            if (auxGrid) auxGrid.setData([]);
        }
    });
}

/**
 * 辅件设备 - 展开行详情渲染
 */
function onAuxiliaryShowRowDetail(e) {
    var grid = e.sender;
    var record = e.record;
    var td = grid.getRowDetailCellEl(record);
    if (!td) return;

    var details = record.detailsInfo || '';
    td.innerHTML = '<div class="aux-detail-wrapper">'
        + (details || '&nbsp;') + '</div>';
}

// ================================================================
// 实时数据处理
// ================================================================
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
            if (rows[i].id === data.deviceId) { foundRow = rows[i]; break; }
        }
        if (foundRow) {
            var updateObj = {};

            if (isFullData) {
                var commStatusKey = null, commStatusNameKey = null;
                for (var key in foundRow) {
                    if (key.toUpperCase() === 'COMMSTATUS') commStatusKey = key;
                    else if (key.toUpperCase() === 'COMMSTATUSNAME') commStatusNameKey = key;
                }
                if (commStatusKey !== null) {
                    if (foundRow[commStatusKey] == 0) commStatusChange = true;
                    updateObj[commStatusKey] = 1;
                }
                if (commStatusNameKey !== null) updateObj[commStatusNameKey] = _loginUserLanguageResource.online;

                var acqTimeKey = null;
                for (var key in foundRow) {
                    if (key.toUpperCase() === 'ACQTIME') { acqTimeKey = key; break; }
                }
                if (acqTimeKey !== null && data.acqTime) updateObj[acqTimeKey] = data.acqTime;

                var alarmInfoKey = null;
                for (var key in foundRow) {
                    if (key.toUpperCase() === 'ALARMINFO') { alarmInfoKey = key; break; }
                }
                var alarmInfo = [];
                if (data.allItemInfo) {
                    for (var j = 0; j < data.allItemInfo.length; j++) {
                        var it = data.allItemInfo[j];
                        if (it.alarmLevel > 0) {
                            var actualColumnKey = null;
                            for (var key in foundRow) {
                                if (key.toUpperCase() === it.column.toUpperCase()) { actualColumnKey = key; break; }
                            }
                            if (actualColumnKey === null) actualColumnKey = it.column;
                            alarmInfo.push({ item: actualColumnKey, alarmLevel: it.alarmLevel });
                        }
                    }
                }
                if (alarmInfoKey !== null) updateObj[alarmInfoKey] = alarmInfo;

                if (data.allItemInfo) {
                    for (var m = 0; m < data.allItemInfo.length; m++) {
                        var itm = data.allItemInfo[m];
                        var fn = itm.column;
                        if (fn.toUpperCase() === 'COMMSTATUS' ||
                            fn.toUpperCase() === 'COMMSTATUSNAME' ||
                            fn.toUpperCase() === 'ALARMINFO') continue;
                        var matchedKey = null;
                        for (var key in foundRow) {
                            if (key.toUpperCase() === fn.toUpperCase()) { matchedKey = key; break; }
                        }
                        if (matchedKey !== null) {
                            if (fn.toUpperCase() === 'RUNSTATUSNAME') {
                                var runStatusKey = null;
                                for (var key in foundRow) {
                                    if (key.toUpperCase() === 'RUNSTATUS') { runStatusKey = key; break; }
                                }
                                if (runStatusKey !== null) updateObj[runStatusKey] = parseInt(itm.rawValue) || 0;
                                updateObj[matchedKey] = itm.value;
                            } else {
                                updateObj[matchedKey] = itm.value;
                            }
                        }
                    }
                }

                var commAlarmKey = null, runAlarmKey = null, resultAlarmKey = null;
                for (var key in foundRow) {
                    if (key.toUpperCase() === 'COMMALARMLEVEL') commAlarmKey = key;
                    else if (key.toUpperCase() === 'RUNALARMLEVEL') runAlarmKey = key;
                    else if (key.toUpperCase() === 'RESULTALARMLEVEL') resultAlarmKey = key;
                }
                if (commAlarmKey !== null && data.commAlarmLevel !== undefined) updateObj[commAlarmKey] = data.commAlarmLevel;
                if (runAlarmKey !== null && data.runAlarmLevel !== undefined) updateObj[runAlarmKey] = data.runAlarmLevel;
                if (resultAlarmKey !== null && data.resultAlarmLevel !== undefined) updateObj[resultAlarmKey] = data.resultAlarmLevel;

            } else if (isStatusData) {
                var commStatusKey2 = null, commStatusNameKey2 = null;
                for (var key in foundRow) {
                    if (key.toUpperCase() === 'COMMSTATUS') commStatusKey2 = key;
                    else if (key.toUpperCase() === 'COMMSTATUSNAME') commStatusNameKey2 = key;
                }
                if (commStatusKey2 !== null && data.commStatus !== undefined) {
                    if (foundRow[commStatusKey2] !== data.commStatus) commStatusChange = true;
                    updateObj[commStatusKey2] = data.commStatus;
                }
                if (commStatusNameKey2 !== null && data.commStatusName !== undefined) {
                    updateObj[commStatusNameKey2] = data.commStatusName;
                }

                var acqTimeKey2 = null;
                for (var key in foundRow) {
                    if (key.toUpperCase() === 'ACQTIME') { acqTimeKey2 = key; break; }
                }
                if (acqTimeKey2 !== null && data.acqTime) updateObj[acqTimeKey2] = data.acqTime;

                var fieldMap = { commTime: null, commTimeEfficiency: null, commRange: null };
                for (var key in foundRow) {
                    var uk = key.toUpperCase();
                    if (uk === 'COMMTIME') fieldMap.commTime = key;
                    else if (uk === 'COMMTIMEEFFICIENCY') fieldMap.commTimeEfficiency = key;
                    else if (uk === 'COMMRANGE') fieldMap.commRange = key;
                }
                if (fieldMap.commTime !== null && data.commTime !== undefined) updateObj[fieldMap.commTime] = data.commTime;
                if (fieldMap.commTimeEfficiency !== null && data.commTimeEfficiency !== undefined) updateObj[fieldMap.commTimeEfficiency] = data.commTimeEfficiency;
                if (fieldMap.commRange !== null && data.commRange !== undefined) updateObj[fieldMap.commRange] = data.commRange;

                var alarmInfoKey2 = null;
                for (var key in foundRow) {
                    if (key.toUpperCase() === 'ALARMINFO') { alarmInfoKey2 = key; break; }
                }
                if (alarmInfoKey2 !== null) {
                    var alarmInfo2 = foundRow[alarmInfoKey2] || [];
                    var ck = null;
                    for (var key in foundRow) {
                        if (key.toUpperCase() === 'COMMSTATUSNAME') { ck = key; break; }
                    }
                    if (ck !== null) {
                        var exist = false;
                        for (var k = 0; k < alarmInfo2.length; k++) {
                            if (alarmInfo2[k].item && alarmInfo2[k].item.toUpperCase() === ck.toUpperCase()) {
                                exist = true;
                                if (data.commAlarmLevel > 0) alarmInfo2[k].alarmLevel = data.commAlarmLevel;
                                else alarmInfo2.splice(k, 1);
                                break;
                            }
                        }
                        if (!exist && data.commAlarmLevel > 0) {
                            alarmInfo2.push({ item: ck, alarmLevel: data.commAlarmLevel });
                        }
                        updateObj[alarmInfoKey2] = alarmInfo2;
                    }
                }

                var commAlarmKey2 = null;
                for (var key in foundRow) {
                    if (key.toUpperCase() === 'COMMALARMLEVEL') { commAlarmKey2 = key; break; }
                }
                if (commAlarmKey2 !== null && data.commAlarmLevel !== undefined) {
                    updateObj[commAlarmKey2] = data.commAlarmLevel;
                }
            }

            grid.updateRow(foundRow, updateObj);
            grid.acceptRecord(foundRow);
        }
    }

    // ===== 统计饼图刷新 =====
    var deviceTypeId = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';
    var orgId = window.parent && window.parent.mini
        ? window.parent.mini.get('leftOrg_Id').getValue() : '';

    if (statTabs) {
        var activeStatTab = statTabs.getActiveTab();
        if (activeStatTab) {
            var key = activeStatTab.name;
            if (isFullData) {
                loadStatData(activeStatTab, deviceTypeId, orgId);
            } else if (isStatusData) {
                if (key !== 'FESdiagramResult') loadStatData(activeStatTab, deviceTypeId, orgId);
            }
        }
    }

    // ===== 中间区域更新 =====
    if (isFullData && isSelectWell) {
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
                                expectedTitle = (deltaRadius > 0
                                    ? _loginUserLanguageResource.moveTowardOutside
                                    : _loginUserLanguageResource.moveTowardInside)
                                    + Math.abs(deltaRadius) + 'cm' + expectedTitle;
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
                                    var serie = chart.series[0];
                                    var seriesName = serie.name.split("(")[0].trim();
                                    for (var cellIdx = 0; cellIdx < data.CellInfo.length; cellIdx++) {
                                        var cell = data.CellInfo[cellIdx];
                                        if (cell.columnName === seriesName) {
                                            var value = parseFloat(cell.rawValue);
                                            if (!isNaN(value)) {
                                                var translation = serie.data.length > 100;
                                                serie.addPoint([timestamp, value], true, translation);
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
                                setTimeout(function () {
                                    mergeDataGridCell(deviceRealTimeMonitoringGrid,
                                        [{ rowIndex: 0, columnIndex: 0, rowSpan: 1, colSpan: 6 }]);
                                    deviceRealTimeMonitoringGrid.setStyle('visibility:visible;');
                                }, 50);
                            }
                        }
                        break;
                    default: break;
                }
            }
        }
    }

    // ===== 状态数据且当前是动态数据 tab =====
    if (isStatusData && isSelectWell) {
        if (middleTabs) {
            var activeMiddleTab2 = middleTabs.getActiveTab();
            if (activeMiddleTab2 && activeMiddleTab2.name === 'middle_DynamicData') {
                if (deviceRealTimeMonitoringGrid) {
                    var statusText = data.commStatus > 0
                        ? _loginUserLanguageResource.goOnline
                        : _loginUserLanguageResource.offline;
                    var newTitle = data.deviceName + ':' + data.acqTime + ' ' + statusText;
                    var firstRow = deviceRealTimeMonitoringGrid.getAt(0);
                    if (firstRow) {
                        deviceRealTimeMonitoringGrid.updateRow(firstRow, { name1: newTitle });
                    }
                }
            }
        }
    }

    // ===== 右侧控制面板刷新 =====
    if (commStatusChange && isSelectWell) {
        if (rightTabs) {
            var activeRightTab = rightTabs.getActiveTab();
            if (activeRightTab && activeRightTab.name === 'right_DeviceControl') {
                loadDeviceControl();
            }
        }
    }

    console.log('实时数据处理完成，设备ID:', data.deviceId, '类型:', funcCode, '通信变化:', commStatusChange);
}

// ================================================================
// 资源监测
// ================================================================
function openResourceChart(itemCode, itemName) {
    var win = new mini.Window();
    win.set({
        title: itemName.split("(")[0],
        width: '70%', height: '60%',
        modal: true, showHeader: true, allowResize: true,
        maxable: true, minable: true
    });
    win.show();

    var divId = 'resourceChart_' + itemCode + '_' + Date.now();
    win.setBody('<div style="width:100%;height:100%;min-height:' + otherCardMinHeight
        + 'px;overflow:hidden;position:relative;"><div id="' + divId + '" style="width:100%;height:100%;"></div></div>');

    loadResourceChartData(itemCode, itemName, divId, win);
}

function initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, ytitle, color, legend, timeFormat) {
    if ($("#" + divId) == undefined || $("#" + divId)[0] == undefined) return;

    new Highcharts.Chart({
        chart: {
            renderTo: divId, type: 'spline', shadow: false, borderWidth: 0,
            zoomType: 'xy', zooming: { mouseWheel: { enabled: false } }
        },
        time: { timezoneOffset: new Date().getTimezoneOffset() },
        credits: { enabled: false },
        title: { text: title, style: { fontSize: chartTitleFontSize } },
        subtitle: { text: subtitle },
        colors: color,
        xAxis: {
            type: 'datetime', title: { text: xtitle },
            labels: {
                formatter: function () {
                    return this.axis.chart.time.dateFormat(timeFormat, this.value);
                },
                autoRotation: true, rotation: -45
            }
        },
        yAxis: [{
            lineWidth: 1, tickWidth: 1, tickLength: 5,
            title: { text: ytitle }
        }],
        tooltip: {
            crosshairs: true, shared: true,
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
            enabled: true, filename: title, fallbackToExportServer: false,
            sourceWidth:  $("#" + divId)[0] ? $("#" + divId)[0].offsetWidth  : null,
            sourceHeight: $("#" + divId)[0] ? $("#" + divId)[0].offsetHeight : null,
            buttons: {
                contextButton: {
                    menuItems: ['viewFullscreen', 'printChart', 'separator',
                                'downloadPNG', 'downloadJPEG', 'downloadSVG',
                                'separator', 'downloadCSV', 'downloadXLS']
                }
            }
        },
        plotOptions: {
            spline: {
                lineWidth: 1, fillOpacity: 0.3,
                marker: {
                    enabled: true, radius: 3,
                    states: { hover: { enabled: true, radius: 6 } }
                },
                shadow: true
            }
        },
        legend: {
            layout: 'vertical', align: 'right', verticalAlign: 'middle',
            enabled: legend, borderWidth: 0,
            itemHiddenStyle: { textDecoration: 'none' }
        },
        series: series
    });
}

function loadResourceChartData(itemCode, itemName, divId, win) {
    var endDate = new Date();
    var startDate = new Date(endDate.getTime() - 24 * 3600 * 1000);
    var startStr = formatDate(startDate);
    var endStr = formatDate(endDate);

    $.ajax({
        url: context + '/realTimeMonitoringController/getResourceProbeHistoryCurveData',
        type: 'POST',
        data: { itemCode: itemCode, startDate: startStr, endDate: endStr },
        dataType: 'json', timeout: 10000,
        success: function (result) {
            if (!result || !result.totalRoot || result.totalRoot.length === 0) {
                document.getElementById(divId).innerHTML =
                    '<div style="text-align:center;padding:20px;">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }
            var chartData = result.totalRoot;
            var legend = false;
            var series = buildResourceSeries(chartData, itemCode, itemName);
            if (series.length > 0) legend = true;

            var title = itemName.split("(")[0];
            var subtitle = "[" + result.startDate + "~" + result.endDate + "]";
            var yTitle = itemName;
            var tickInterval = Math.floor(chartData.length / 10) + 1;
            var color = ['#800000', '#008C00', '#000000', '#0000FF', '#F4BD82', '#FF00FF'];

            var timeFormat = '%m-%d';
            if (chartData.length > 0 && result.minAcqTime.split(' ')[0] == result.maxAcqTime.split(' ')[0]) {
                timeFormat = '%H:%M';
            }

            initResourceProbeHistoryCurveChartFn(series, tickInterval, divId, title, subtitle,
                _loginUserLanguageResource.time, yTitle, color, legend, timeFormat);
        },
        error: function () {
            document.getElementById(divId).innerHTML =
                '<div style="text-align:center;padding:20px;color:red;">' + _loginUserLanguageResource.requestFailed + '</div>';
        }
    });
}

function buildResourceSeries(data, itemCode, itemName) {
    var series = [];
    if (itemCode === 'cpuUsedPercent') {
        var cpuMap = {};
        data.forEach(function (item) {
            var values = item.value.split(';');
            values.forEach(function (v, idx) {
                var key = 'CPU' + (idx + 1);
                if (!cpuMap[key]) cpuMap[key] = [];
                var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
                cpuMap[key].push([ts, parseFloat(v)]);
            });
        });
        for (var key in cpuMap) series.push({ name: key, data: cpuMap[key] });
    } else if (itemCode === 'jedisStatus') {
        var maxData = [], usedData = [];
        data.forEach(function (item) {
            var values = item.value.split(';');
            var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
            if (values.length >= 2) {
                maxData.push([ts, parseFloat(values[0])]);
                usedData.push([ts, parseFloat(values[1])]);
            }
        });
        series.push({ name: 'maxmemory(m)',  data: maxData });
        series.push({ name: 'usedmemory(m)', data: usedData });
    } else if (itemCode === 'tableSpaceSize') {
        var dataSpace = [], undoSpace = [];
        data.forEach(function (item) {
            var values = item.value.split(';');
            var ts = Date.parse(item.acqTime.replace(/-/g, '/'));
            if (values.length >= 2) {
                dataSpace.push([ts, parseFloat(values[0])]);
                undoSpace.push([ts, parseFloat(values[1])]);
            }
        });
        series.push({ name: _loginUserLanguageResource.dataTablespace + "(%)", data: dataSpace });
        series.push({ name: _loginUserLanguageResource.undoTablespace + "(%)", data: undoSpace });
    } else {
        var singleData = [];
        data.forEach(function (item) {
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

function updateResourceMonitorUI(data) {
    function updateResourceItemPlain(id, text, color) {
        var el = document.getElementById(id);
        if (!el) return;
        el.style.display = '';
        el.textContent = text;
        el.style.color = color || '';
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
        if (blink) el.classList.add('resource-blink');
        else el.classList.remove('resource-blink');
    }
    function hideResourceItem(id) {
        var el = document.getElementById(id);
        if (el) el.style.display = 'none';
    }

    var cpuColor = '';
    if (data.cpuUsedPercentAlarmLevel == 1) cpuColor = '#F09614';
    else if (data.cpuUsedPercentAlarmLevel == 2) cpuColor = '#DC2828';
    updateResourceItemPlain('CPUUsedPercentLabel_id',
        _loginUserLanguageResource.resourcesMonitoring_cpu + ':' + data.cpuUsedPercent, cpuColor);

    var memColor = '';
    if (data.memUsedPercentAlarmLevel == 1) memColor = '#F09614';
    else if (data.memUsedPercentAlarmLevel == 2) memColor = '#DC2828';
    updateResourceItemPlain('memUsedPercentLabel_id',
        _loginUserLanguageResource.resourcesMonitoring_mem + ':' + data.memUsedPercent, memColor);

    var tableDot = (data.dbConnStatus == 1) ? '#52c41a' : '#ccc';
    var tableText = _loginUserLanguageResource.resourcesMonitoring_tablespaces;
    var tableTextColor = '';
    var tableBlink = (data.dbConnStatus != 1);
    if (data.dbConnStatus == 1) {
        tableText = _loginUserLanguageResource.resourcesMonitoring_tablespaces + ':' +
            data.tableSpaceUsedPercent + ';' + data.undoTableSpaceUsedPercent;
        if (data.tableSpaceUsedPercentAlarmLevel == 1) tableTextColor = '#F09614';
        else if (data.tableSpaceUsedPercentAlarmLevel == 2) tableTextColor = '#DC2828';
    }
    updateResourceItem('tableSpaceSizeProbeLabel_id', tableDot, tableText, tableTextColor, tableBlink);

    var redisDot = (data.redisStatus == 1) ? '#52c41a' : '#ccc';
    var redisBlink = (data.redisStatus != 1);
    var redisText = (data.redisStatus == 1)
        ? _loginUserLanguageResource.resourcesMonitoring_cache + ':' + data.cacheUsedMemory + 'm/' + data.cacheMaxMemory + 'm'
        : _loginUserLanguageResource.resourcesMonitoring_cache;
    updateResourceItem('redisRunStatusProbeLabel_id', redisDot, redisText, null, redisBlink);

    var adDot = (data.adRunStatus == 1) ? '#52c41a' : '#ccc';
    var adBlink = (data.adRunStatus != 1);
    updateResourceItem('adRunStatusProbeLabel_id', adDot, _loginUserLanguageResource.resourcesMonitoring_ad, null, adBlink);

    var acDot = (data.acRunStatus == 1) ? '#52c41a' : '#ccc';
    var acBlink = (data.acRunStatus != 1);
    updateResourceItem('acRunStatusProbeLabel_id', acDot, _loginUserLanguageResource.resourcesMonitoring_ac, null, acBlink);

    if (data.licenseSign) {
        updateResourceItemPlain('adLicenseStatusProbeLabel_id',
            'License:' + data.deviceAmount + '/' + data.license, '#DC2828');
    } else {
        hideResourceItem('adLicenseStatusProbeLabel_id');
    }
}

function updateDBMonitorUI(data) {
    var tableBtn = mini.get('tableSpaceSizeProbeLabel_id');
    if (!tableBtn) return;
    if (data.dbConnStatus == 1) {
        var showInfo = _loginUserLanguageResource.resourcesMonitoring_tablespaces + ':' +
            (data.tableSpaceUsedPercent || '0') + '%;' +
            (data.undoTableSpaceUsedPercent || '0') + '%';
        tableBtn.setText(showInfo);
        tableBtn.setIconCls('dtgreen');
        var tableEl = tableBtn.getEl().dom;
        if (data.tableSpaceUsedPercentAlarmLevel == 1) tableEl.style.color = '#F09614';
        else if (data.tableSpaceUsedPercentAlarmLevel == 2) tableEl.style.color = '#DC2828';
        else tableEl.style.color = '';
    } else {
        tableBtn.setText(_loginUserLanguageResource.resourcesMonitoring_tablespaces);
        tableBtn.setIconCls('dtyellow');
        tableBtn.getEl().dom.style.color = '';
    }
}

function handleAdExit() {
    clearStatFilters();
    var statTabsObj = mini.get('statTabs');
    if (statTabsObj) {
        var activeTab = statTabsObj.getActiveTab();
        if (activeTab) {
            var deviceTypeId = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';
            var orgId = window.parent && window.parent.mini
                ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            loadStatData(activeTab, deviceTypeId, orgId);
        }
    }

    var deviceCombo = mini.get('deviceCombo');
    if (deviceCombo) { deviceCombo.setValue(''); deviceCombo.setText(''); }

    var grid = mini.get('deviceGrid');
    if (grid) { grid.deselectAll(); grid.load(); }
}

// ================================================================
// 设备下拉框
// ================================================================
window.onDeviceComboBeforeLoad = function (e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize || defaultWellComboxSize;
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';

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
    combo.load(combo.url);
    if (hidePopup) combo.showPopup();
};

window.onDeviceComboLoad = function (e) { /* 保持空 */ };

// ================================================================
// 导出
// ================================================================
function exportRealTimeMonitoringData() {
    var orgId = '';
    try {
        var leftOrg = window.parent.mini.get('leftOrg_Id');
        if (leftOrg) orgId = leftOrg.getValue();
    } catch (e) {}

    var deviceCombo = mini.get('deviceCombo');
    var deviceName = deviceCombo ? deviceCombo.getValue() : '';

    var deviceType = _rmCurrentLevel2 ? _rmCurrentLevel2.deviceTypeId : '0';
    var dictDeviceType = deviceType;
    if (deviceType && deviceType.indexOf(',') > -1) {
        dictDeviceType = _rmCurrentLevel1 ? _rmCurrentLevel1.deviceTypeId : deviceType;
    }

    var columnStrInput = document.getElementById('RealTimeMonitoringColumnStr_Id');
    if (!columnStrInput || !columnStrInput.value) {
        mini.alert('表格列配置未加载，请刷新页面重试');
        return;
    }
    var columnStr = columnStrInput.value;

    var FESdiagramResultStatValue = document.getElementById('RealTimeMonitoringStatSelectFESdiagramResult_Id')
        ? document.getElementById('RealTimeMonitoringStatSelectFESdiagramResult_Id').value || '' : '';
    var commStatusStatValue = document.getElementById('RealTimeMonitoringStatSelectCommStatus_Id')
        ? document.getElementById('RealTimeMonitoringStatSelectCommStatus_Id').value || '' : '';
    var runStatusStatValue = document.getElementById('RealTimeMonitoringStatSelectRunStatus_Id')
        ? document.getElementById('RealTimeMonitoringStatSelectRunStatus_Id').value || '' : '';
    var numStatusStatValue = document.getElementById('RealTimeMonitoringStatSelectNumStatus_Id')
        ? document.getElementById('RealTimeMonitoringStatSelectNumStatus_Id').value || '' : '';
    var deviceTypeStatValue = document.getElementById('RealTimeMonitoringStatSelectDeviceType_Id')
        ? document.getElementById('RealTimeMonitoringStatSelectDeviceType_Id').value || '' : '';

    var fileName = _loginUserLanguageResource.realtimeMonitoringExpFileName;
    var title = fileName;

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
                lockedheads  += header + ',';
            } else {
                unlockedfields += dataIndex + ',';
                unlockedheads  += header + ',';
            }
        });
        if (lockedfields)   { lockedfields = lockedfields.slice(0, -1);  lockedheads = lockedheads.slice(0, -1); }
        if (unlockedfields) { unlockedfields = unlockedfields.slice(0, -1); unlockedheads = unlockedheads.slice(0, -1); }
        fields = 'id' + (lockedfields ? ',' + lockedfields : '') + (unlockedfields ? ',' + unlockedfields : '');
        heads = _loginUserLanguageResource.idx + (lockedheads ? ',' + lockedheads : '') + (unlockedheads ? ',' + unlockedheads : '');
    } catch (e) {
        mini.alert(_loginUserLanguageResource.operationFailed);
        return;
    }

    var key = 'exportDeviceRealTimeOverviewData_' + deviceType + '_' + new Date().getTime();
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

    var maskContainer = document.querySelector('.device-overview-area') || document.body;
    exportDataMask(key, maskContainer, _loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
}

function exportDeviceRealTimeMonitoringData() {
    var grid = mini.get('deviceGrid');
    var selected = grid ? grid.getSelected() : null;
    if (!selected) { mini.alert(_loginUserLanguageResource.checkOne); return; }

    var timestamp = new Date().getTime();
    var key = 'exportDeviceRealTimeMonitoringData_' + selected.id + '_' + timestamp;
    var url = context + '/realTimeMonitoringController/exportDeviceRealTimeMonitoringData';
    var param = '&deviceId=' + selected.id +
        '&deviceName=' + encodeURIComponent(encodeURIComponent(selected.deviceName)) +
        '&calculateType=' + (selected.calculateType || 0) +
        '&key=' + key;

    exportDataMask(key, 'RealTimeMonitoringInfoDataTableInfoDiv_id',
                   _loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
}

function gotoHistory() {
    if (window.parent) {
        window.parent.postMessage({
            action: 'switchModule',
            moduleId: 'DeviceHistoryQuery'
        }, '*');
    }
}

// ================================================================
// 暴露到 window（onclick / onevent 里直接调用）
// ================================================================
window.refreshData                        = refreshData;
window.refreshDeviceList                  = refreshDeviceList;
window.onDeviceComboChange                = onDeviceComboChange;
window.selectRtmLevel1                    = selectRtmLevel1;
window.selectRtmLevel2                    = selectRtmLevel2;
window.onStatTabChanged                   = onStatTabChanged;
window.onMiddleTabChanged                 = onMiddleTabChanged;
window.onRightTabChanged                  = onRightTabChanged;
window.onDeviceGridBeforeLoad             = onDeviceGridBeforeLoad;
window.onDeviceGridLoad                   = onDeviceGridLoad;
window.onDeviceGridSelectChanged          = onDeviceGridSelectChanged;
window.onDeviceGridDrawCell               = onDeviceGridDrawCell;
window.exportRealTimeMonitoringData       = exportRealTimeMonitoringData;
window.exportDeviceRealTimeMonitoringData = exportDeviceRealTimeMonitoringData;
window.gotoHistory                        = gotoHistory;
window.openResourceChart                  = openResourceChart;
window.onEnumControlClick                 = onEnumControlClick;
window.onSwitchControlClick               = onSwitchControlClick;
window.onNumericControlClick              = onNumericControlClick;
window.onAuxiliaryShowRowDetail           = onAuxiliaryShowRowDetail;
window.onDeviceComboBeforeLoad            = window.onDeviceComboBeforeLoad;
window.onDeviceComboShowPopup             = window.onDeviceComboShowPopup;
window.onDeviceComboLoad                  = window.onDeviceComboLoad;