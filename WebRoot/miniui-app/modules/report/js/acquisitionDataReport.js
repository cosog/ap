// ================================================================
// 采集报表模块 - acquisitionReport.js
// 严格对照 ExtJS：AcquisitionDataReportView + AcquisitionDataReportPanel
// 布局样式对照 MiniUI 生产报表（productionReport）
// ================================================================

var _reportLevel1Data = [];
var _reportLevel2Data = [];
var _reportCurrentLevel1 = null;
var _reportCurrentLevel2 = null;
var _reportCurrentDeviceType = '';

var _reportModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };

// ---------- 单井采集报表 - 全局 ----------
var singleWellAcquisitionDataReportHelper = null;

var _reportSelectedDeviceId     = 0;
var _reportSelectedRowIndex     = 0;
var _reportCurrentDeviceName    = '';
var _reportCurrentCalculateType = 0;
var _reportDeviceTotalCount     = 0;
var _reportGridLoading          = false;

// ---------- 区域采集报表 - 全局 ----------
var productionAcquisitionDataReportHelper = null;

var isInitializing = true;

//---------- 全局选中状态 ----------
var _reportRestoreState = null;              // 在 initAcquisitionReportPage 中赋值
var _reportSuppressSave = { value: true };   // 对象包装，便于异步修改

// ================================================================
// 页面初始化
// ================================================================
function initAcquisitionReportPage() {
    initReportModuleRight();

    _reportRestoreState = createRestoreState();
    _reportSuppressSave = { value: true };

    buildReportLevel1Tabs();
    initReportI18n();
    initReportDefaultDates();

    initAcquisitionReportMessageListener();

    isInitializing = false;
}

// ================================================================
// 权限
// ================================================================
function initReportModuleRight() {
    _reportModuleRight = getRoleModuleRight(
        context + '/roleManagerController/getRoleModuleRight',
        'AcquisitionDataReport'
    ) || { viewFlag: 0, editFlag: 0, controlFlag: 0 };

    _reportModuleRight.viewFlag    = parseInt(_reportModuleRight.viewFlag)    || 0;
    _reportModuleRight.editFlag    = parseInt(_reportModuleRight.editFlag)    || 0;
    _reportModuleRight.controlFlag = parseInt(_reportModuleRight.controlFlag) || 0;

    updateReportBtnStatus();
}

function updateReportBtnStatus() {
    var canEdit = (_reportModuleRight.editFlag == 1);
    var btnIds = [
        'swSaveBtn', 'pdSaveBtn',
        'swExportBtn', 'swBatchExportBtn', 'pdExportBtn', 'pdBatchExportBtn'
    ];
    for (var j = 0; j < btnIds.length; j++) {
        var btn = mini.get(btnIds[j]);
        if (btn) btn.setEnabled(canEdit);
    }
}

// ================================================================
// 消息监听（组织切换 / 从其他模块切回）
// ================================================================
function initAcquisitionReportMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        switch (message.action) {
            case 'refresh':
                handleReportRefreshFromParent(message);
                break;
        }
    });
}

function handleReportRefreshFromParent(message) {
    console.log('采集报表收到刷新指令, orgId:', message.orgId);

    var tabs = mini.get('acquisitionReportInnerTabs');
    var tab = tabs ? tabs.getActiveTab() : null;
    var tabName = tab ? tab.name : '';

    if (tabName !== 'singleWell') {
        if (tabName === 'area') {
            CreateProductionAcquisitionDataReportTable();
            CreateProductionAcquisitionDataReportCurve();
        }
        return;
    }

    var gsel = loadGlobalSelection();
    var curType = _reportCurrentDeviceType ? String(_reportCurrentDeviceType) : '';

    // 情况 1：二级（或一级）标签不一致
    if (gsel.deviceTypeId && curType !== String(gsel.deviceTypeId)) {
        var t = findTargetLevels(_reportLevel1Data, gsel.deviceTypeId);
        if (t) {
            _reportRestoreState.deviceId    = gsel.deviceId || '';
            _reportRestoreState.level2Index = t.level2Index;
            selectReportLevel1(t.level1Index);
            return;
        }
    }

    // 情况 2 & 3：统一重新加载设备列表
    if (gsel.deviceId) {
        _reportRestoreState.deviceId = String(gsel.deviceId);
    }
    loadSingleWellDeviceGrid();
}

// ================================================================
// 一二级标签（对照 ExtJS AcquisitionDataReportRootTabPanel）
// ================================================================
function buildReportLevel1Tabs() {
    var container = document.getElementById('reportLevel1Footer');
    if (!container) return;
    container.innerHTML = '';

    var tabInfo = null;
    try {
        if (window.parent && window.parent.tabInfo) tabInfo = window.parent.tabInfo;
    } catch (e) { console.warn('无法获取 tabInfo', e); }

    var children = (tabInfo && tabInfo.children) ? tabInfo.children : [];

    if (children.length === 0) {
        container.innerHTML = '<span style="padding:8px 16px;color:#999;font-size:13px;">'
            + _loginUserLanguageResource.emptyMsg + '</span>';
        var level2 = document.getElementById('reportLevel2Sidebar');
        if (level2) {
            level2.innerHTML = '<div class="no-child-tip">'
                + _loginUserLanguageResource.emptyMsg + '</div>';
        }
        return;
    }

    _reportLevel1Data = children;
    for (var i = 0; i < _reportLevel1Data.length; i++) {
        (function (idx) {
            var item = _reportLevel1Data[idx];
            var span = document.createElement('span');
            span.className = 'tab-item' + (idx === 0 ? ' active' : '');
            span.dataset.index = idx;
            span.dataset.deviceTypeId = item.deviceTypeId || '';
            span.textContent = item.text;
            span.title = item.text;
            span.onclick = function () { selectReportLevel1(parseInt(this.dataset.index)); };
            container.appendChild(span);
        })(i);
    }

    if (_reportLevel1Data.length > 0) {
        var startIndex = 0;
        if (_reportRestoreState.deviceTypeId) {
            var t = findTargetLevels(_reportLevel1Data, _reportRestoreState.deviceTypeId);
            if (t) {
                _reportRestoreState.level2Index = t.level2Index;
                startIndex = t.level1Index;
            }
            _reportRestoreState.deviceTypeId = '';
        }
        selectReportLevel1(startIndex);
    }
}

function selectReportLevel1(index) {
    if (index < 0 || index >= _reportLevel1Data.length) return;
    var item = _reportLevel1Data[index];
    _reportCurrentLevel1 = item;

    var container = document.getElementById('reportLevel1Footer');
    var tabs = container.querySelectorAll('.tab-item');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }
    buildReportLevel2Tabs(item);
}

function buildReportLevel2Tabs(parentItem) {
    var container = document.getElementById('reportLevel2Sidebar');
    if (!container) return;
    container.innerHTML = '';

    var children = (parentItem && parentItem.children) ? parentItem.children : [];

    // ============ 一级无子标签：隐藏侧边栏，用一级 deviceTypeId 加载 ============
    if (children.length === 0) {
        container.classList.add('hidden');
        _reportLevel2Data = [];

        _reportCurrentLevel2 = {
            text: parentItem.text,
            deviceTypeId: parentItem.deviceTypeId,
            isAll: false,
            isLevel1Direct: true
        };
        _reportCurrentDeviceType = _reportCurrentLevel2.deviceTypeId || '';
        _reportRestoreState.level2Index = -1;

        onReportDeviceTypeChanged();
        return;
    }

    // ============ 一级有子标签 ============
    container.classList.remove('hidden');
    _reportLevel2Data = children;

    var allIds = [];
    for (var i = 0; i < children.length; i++) {
        if (children[i].deviceTypeId) allIds.push(children[i].deviceTypeId);
    }

    var allTabs = [];
    // ★ 只有多个二级时才生成"全部"tab
    if (children.length > 1) {
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
            div.dataset.deviceTypeId = item.deviceTypeId || '';
            div.textContent = item.text;
            div.title = item.text;
            div.onclick = function () { selectReportLevel2(parseInt(this.dataset.index)); };
            container.appendChild(div);
        })(i, allTabs[i]);
    }

    var defaultIndex = 0;
    if (_reportRestoreState.level2Index >= 0 && _reportRestoreState.level2Index < allTabs.length) {
        defaultIndex = _reportRestoreState.level2Index;
    }
    _reportRestoreState.level2Index = -1;

    var tabEls = container.querySelectorAll('.tab-item');
    for (var t = 0; t < tabEls.length; t++) {
        tabEls[t].className = 'tab-item' + (t === defaultIndex ? ' active' : '');
    }

    if (allTabs.length > 0) {
        _reportCurrentLevel2 = allTabs[defaultIndex];
        _reportCurrentDeviceType = _reportCurrentLevel2.deviceTypeId || '';
        onReportDeviceTypeChanged();
    }
}

function selectReportLevel2(index) {
    var container = document.getElementById('reportLevel2Sidebar');
    if (!container) return;
    var tabs = container.querySelectorAll('.tab-item');

    var allTabs = [];
    var children = _reportCurrentLevel1 ? (_reportCurrentLevel1.children || []) : [];
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

    if (index < 0 || index >= allTabs.length) return;

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    _reportCurrentLevel2 = allTabs[index];
    _reportCurrentDeviceType = _reportCurrentLevel2.deviceTypeId || '';

    onReportDeviceTypeChanged();
}

function onReportDeviceTypeChanged() {
    // ★ 保留 _reportSelectedDeviceId 作为恢复候选，由 onSwDeviceGridLoad 消费
    _reportSelectedRowIndex     = 0;
    _reportCurrentDeviceName    = '';
    _reportCurrentCalculateType = 0;

    var combo = mini.get('swDeviceCombo');
    if (combo) combo.setValue('', false);

    clearSingleWellReportContent();
    clearProductionAcquisitionReportContent();

    // 只加载当前激活 tab 的数据
    var tabs = mini.get('acquisitionReportInnerTabs');
    if (!tabs) return;
    var tab = tabs.getActiveTab();
    if (!tab) return;

    if (tab.name === 'singleWell') {
        loadSingleWellDeviceGrid();
    } else if (tab.name === 'area') {
        CreateProductionAcquisitionDataReportTable();
        CreateProductionAcquisitionDataReportCurve();
    }
}

// ================================================================
// 国际化
// ================================================================
function initReportI18n() {
    var R = _loginUserLanguageResource;

    setTabTitle('acquisitionReportInnerTabs', 0, R.singleDeviceReport);
    setTabTitle('acquisitionReportInnerTabs', 1, R.areaReport);

    setPanelTitle('swDeviceListPanel', R.deviceList);
    setPanelTitle('swCurvePanel', R.reportCurve);
    setPanelTitle('swDataPanel', R.reportData);
    setPanelTitle('pdCurvePanel', R.reportCurve);
    setPanelTitle('pdDataPanel', R.reportData);

    setBtnText('swRefreshBtn', R.refresh);
    setBtnText('swSearchBtn', R.search);
    setBtnText('swBatchExportBtn', R.bulkExportData);
    setBtnText('swComparativeBtn', R.comparativeAnalysis);
    setHtml('swLblDevice', R.deviceName + '：');
    setHtml('swLblDate', R.date + '：');
    setHtml('swLblTimeTo', R.timeTo + '：');

    setBtnText('swExportBtn', R.exportData);
    setBtnText('swSaveBtn', R.save);

    setBtnText('pdRefreshBtn', R.refresh);
    setBtnText('pdSearchBtn', R.search);
    setHtml('pdLblDate', R.date + '：');
    setHtml('pdLblTimeTo', R.timeTo + '：');

    setBtnText('pdForwardBtn', R.forward);
    setBtnText('pdBackBtn', R.backward);
    setBtnText('pdExportBtn', R.exportData);
    setBtnText('pdBatchExportBtn', R.bulkExportData);
    setBtnText('pdSaveBtn', R.save);

    var swDeviceCombo = mini.get('swDeviceCombo');
    if (swDeviceCombo) swDeviceCombo.setEmptyText('--' + R.all + '--');

    var swGrid = mini.get('swDeviceGrid');
    if (swGrid) swGrid.setEmptyText(R.emptyMsg);
}

// ================================================================
// 默认日期
// ================================================================
function initReportDefaultDates() {
    var today = new Date();

    mini.get('swStartDate').setValue('', false);
    mini.get('swEndDate').setValue(today, false);

    mini.get('pdStartDate').setValue('', false);
    mini.get('pdEndDate').setValue(today, false);
    mini.get('pdReportDate').setValue('', false);

    updatePdNavBtnStatus();
}

// ================================================================
// 工具
// ================================================================
function getLeftOrgId() {
    try {
        if (window.parent && window.parent.mini) {
            var c = window.parent.mini.get('leftOrg_Id');
            if (c) return c.getValue() || '';
        }
    } catch (e) { /* ignore */ }
    return '';
}

function getSelectedOrgName() {
    try {
        if (window.parent && window.parent.getSelectOrgNodePath) {
            return window.parent.getSelectOrgNodePath() || '';
        }
    } catch (e) {}
    return '';
}

function setBtnText(id, text) {
    var btn = mini.get(id);
    if (btn && text != null) btn.setText(text);
}

function setHtml(id, html) {
    var el = document.getElementById(id);
    if (el && html != null) el.innerHTML = html;
}

function setPanelTitle(id, title) {
    var comp = mini.get(id);
    if (comp && comp.setTitle && title != null) comp.setTitle(title);
}

function setTabTitle(tabsId, index, title) {
    var tabs = mini.get(tabsId);
    if (!tabs) return;
    var tab = tabs.getTab(index);
    if (tab && title != null) {
        tabs.updateTab(tab, { title: title });
    }
}

// ================================================================
// 单井采集报表 - 设备下拉框
// ================================================================
function onSwDeviceComboBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize
        || (typeof defaultWellComboxSize !== 'undefined' ? defaultWellComboxSize : 50);

    params.start = pageIndex * pageSize;
    params.limit = pageSize;
    /* ★ 对照 ExtJS：采集报表下拉只传 orgId + deviceName */
    params.orgId = getLeftOrgId();

    var combo = mini.get('swDeviceCombo');
    params.deviceName = combo ? (combo.getValue() || '') : '';

    e.params = params;
}

function onSwDeviceComboShowPopup(e) {
    var combo = e.sender;
    var data = combo.getData();
    var hidePopup = false;
    if (!data || data.length <= 1) {
        combo.hidePopup();
        hidePopup = true;
    }
    combo.load(combo.url);
    if (hidePopup) {
        combo.showPopup();
    }
}

function onSwDeviceComboChange(e) {
    _reportSelectedDeviceId     = 0;
    _reportSelectedRowIndex     = 0;
    _reportCurrentDeviceName    = '';
    _reportCurrentCalculateType = 0;

    clearSingleWellReportContent();
    loadSingleWellDeviceGrid();
}

// ================================================================
// 单井采集报表 - 设备列表
// ================================================================
function loadSingleWellDeviceGrid() {
    var grid = mini.get('swDeviceGrid');
    if (!grid) return;

    if (!grid.getUrl()) {
        grid.setUrl(context + '/reportDataMamagerController/getAcquisitionDataReportDeviceList');
    }
    grid.load();
}

function onSwDeviceGridBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize || 10000;

    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var combo = mini.get('swDeviceCombo');
    params.deviceName = combo ? (combo.getValue() || '') : '';
    params.deviceType = _reportCurrentDeviceType;
    params.orgId      = getLeftOrgId();

    e.params = params;
}

function onSwDeviceGridLoad(e) {
    var grid = e.sender;
    var result = e.result || {};

    if (!grid._columnsCreated) {
        buildSingleWellDeviceGridColumns(grid);
        grid._columnsCreated = true;
    }

    var data = result.totalRoot || [];
    _reportDeviceTotalCount = result.totalCount || 0;

    if (data.length > 0) {
        var restoreId = _reportRestoreState.deviceId;
        if (!restoreId) {
            restoreId = _reportSelectedDeviceId;
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
        setTimeout(function () {
            grid.scrollIntoView(selectRow);
        }, 150);
    } else {
        _reportSelectedDeviceId     = 0;
        _reportSelectedRowIndex     = 0;
        _reportCurrentDeviceName    = '';
        _reportCurrentCalculateType = 0;
        clearSingleWellReportContent();
    }

    // ★ 首次加载后解除抑制
    if (_reportSuppressSave.value) {
        setTimeout(function () { _reportSuppressSave.value = false; }, 100);
    }
}

function buildSingleWellDeviceGridColumns(grid) {
    var R = _loginUserLanguageResource;
    var columns = [];
    columns.push({
        type: 'indexcolumn',
        width: 50,
        header: R.idx,
        headerAlign: 'center',
        align: 'center'
    });
    columns.push({
        field: 'deviceName',
        header: R.deviceName,
        headerAlign: 'center',
        align: 'center',
        width: '100%'
    });
    grid.setColumns(columns);
}

function onSwDeviceGridSelectionChanged(e) {
    if (_reportGridLoading) return;
    if (isInitializing) return;

    var grid = e.sender;
    var row = grid.getSelected();

    if (!row) {
        _reportSelectedDeviceId     = 0;
        _reportSelectedRowIndex     = 0;
        _reportCurrentDeviceName    = '';
        _reportCurrentCalculateType = 0;
        clearSingleWellReportContent();
        return;
    }

    var data = grid.getData() || [];
    for (var i = 0; i < data.length; i++) {
        if (data[i] === row) {
            _reportSelectedRowIndex = i;
            break;
        }
    }

    _reportSelectedDeviceId     = row.id || 0;
    _reportCurrentDeviceName    = row.deviceName || '';
    _reportCurrentCalculateType = row.calculateType || 0;

    CreateSingleWellAcquisitionDataReportTable();
    CreateSingleWellAcquisitionDataReportCurve();

    // ★ 记录为恢复候选并保存全局状态
    _reportRestoreState.deviceId = String(_reportSelectedDeviceId);
    saveGlobalSelection(
        _reportCurrentDeviceType,
        _reportSelectedDeviceId,
        { suppress: _reportSuppressSave.value }
    );
}

// ================================================================
// 单井采集报表 - 日期事件
// ================================================================
function onSwRangeDateChanged(e) {
	return;
    if (isInitializing) return;
    CreateSingleWellAcquisitionDataReportTable();
    CreateSingleWellAcquisitionDataReportCurve();
}

function fillSwDatesFromResult(result) {
    if (!result) return;

    var startDp = mini.get('swStartDate');
    if (startDp && !startDp.getValue() && result.startDate) {
        startDp.setValue(result.startDate, false);
    }
    var endDp = mini.get('swEndDate');
    if (endDp && !endDp.getValue() && result.endDate) {
        endDp.setValue(result.endDate, false);
    }
}

// ================================================================
// 单井采集报表 - 表格（对照 ExtJS CreateSingleWellAcquisitionDataReportTable）
// ================================================================
function CreateSingleWellAcquisitionDataReportTable() {
    if (_reportSelectedDeviceId <= 0) return;

    var orgId         = getLeftOrgId();
    var deviceId      = _reportSelectedDeviceId;
    var deviceName    = _reportCurrentDeviceName;
    var calculateType = _reportCurrentCalculateType;
    var deviceType    = _reportCurrentDeviceType;

    var startDp = mini.get('swStartDate');
    var endDp   = mini.get('swEndDate');
    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    mini.mask({ el: 'swDataPanel', cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getSingleWellAcquisitionDataRangeReportData',
        type: 'POST',
        data: {
            orgId: orgId,
            deviceId: deviceId,
            deviceName: deviceName,
            calculateType: calculateType,
            startDate: startDate,
            endDate: endDate,
            reportType: 0,
            deviceType: deviceType
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask('swDataPanel');
            fillSwDatesFromResult(result);

            if (singleWellAcquisitionDataReportHelper != null) {
                if (singleWellAcquisitionDataReportHelper.hot != undefined
                    && singleWellAcquisitionDataReportHelper.hot != '') {
                    singleWellAcquisitionDataReportHelper.hot.destroy();
                }
                singleWellAcquisitionDataReportHelper = null;
            }

            if (result.success) {
                singleWellAcquisitionDataReportHelper = SingleWellAcquisitionDataReportHelper.createNew(
                    'swDataDiv', 'SingleWellAcquisitionDataReportContainer',
                    result.template, result.data, result.columns
                );
                singleWellAcquisitionDataReportHelper.createTable();
            } else {
                document.getElementById('swDataDiv').innerHTML = '';
            }

            var totalCnt = document.getElementById('swTotalCount');
            if (totalCnt) {
                var cnt = (result.data && result.data.length) ? result.data.length : 0;
                totalCnt.textContent = _loginUserLanguageResource.totalCount + ':' + cnt;
            }
        },
        error: function () {
            mini.unmask('swDataPanel');
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 单井采集报表 - 曲线（对照 ExtJS CreateSingleWellAcquisitionDataReportCurve）
// ================================================================
function CreateSingleWellAcquisitionDataReportCurve() {
    if (_reportSelectedDeviceId <= 0) return;

    var orgId         = getLeftOrgId();
    var deviceId      = _reportSelectedDeviceId;
    var deviceName    = _reportCurrentDeviceName;
    var calculateType = _reportCurrentCalculateType;
    var deviceType    = _reportCurrentDeviceType;

    var startDp = mini.get('swStartDate');
    var endDp   = mini.get('swEndDate');
    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    mini.mask({ el: 'swCurvePanel', cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getSingleWellRangeReportCurveData',
        type: 'POST',
        data: {
            orgId: orgId,
            deviceId: deviceId,
            deviceName: deviceName,
            calculateType: calculateType,
            startDate: startDate,
            endDate: endDate,
            reportType: 0,
            deviceType: deviceType,
            unitClasses: 2
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask('swCurvePanel');
            fillSwDatesFromResult(result);
            // 单井采集报表 → Report（对照 ExtJS graphicSet.Report）
            buildAndRenderCurve(result, 'swCurveDiv', '%m-%d',
                                'dailyReportCurve', 'Report');
        },
        error: function () {
            mini.unmask('swCurvePanel');
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 通用曲线组装 + 渲染（对照 ExtJS initSingleWellAcquisitionDataReportCurveChartFn）
//   graphicSetKey：'Report' / 'History'
// ================================================================
function buildAndRenderCurve(result, divId, timeFormat, titleKey, graphicSetKey) {
    if (!result || !result.list) return;

    var data           = result.list || [];
    var graphicSet     = result.graphicSet || {};
    var legendName     = result.curveItems || [];
    var curveConf      = result.curveConf || [];
    var curveItemCodes = result.curveItemCodes || [];

    var defaultColors = ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9',
                         '#f15c80', '#e4d354', '#2b908f', '#f45b5b', '#91e8e1'];

    var tickInterval = Math.floor(data.length / 10) + 1;
    if (tickInterval < 100) tickInterval = 100;

    var firstLower = (typeof _loginUserLanguageResourceFirstLower !== 'undefined'
                     && _loginUserLanguageResourceFirstLower[titleKey])
                     ? _loginUserLanguageResourceFirstLower[titleKey] : '';

    var title = (result.deviceName || result.selectedOrgName || '')
        + (loginUserLanguage && loginUserLanguage.toUpperCase() === 'ZH_CN' ? '' : ' ')
        + firstLower
        + (result.reportDate ? ('-' + result.reportDate) : '');

    var color = [], color_l = [], color_r = [], color_all = [];

    for (var i = 0; i < curveConf.length; i++) {
        var singleColor = defaultColors[i % defaultColors.length];
        if (curveConf[i].color) singleColor = '#' + curveConf[i].color;
        color.push(singleColor);
        if (curveConf[i].yAxisOpposite) color_r.push(singleColor);
        else color_l.push(singleColor);
    }

    var series_l = [], series_r = [], yAxis_l = [], yAxis_r = [];

    for (var i = 0; i < legendName.length; i++) {
        var maxValue = null, minValue = null;
        var allPositive = true, allNegative = true;

        var singleSeries = {
            name: legendName[i],
            type: 'spline',
            lineWidth: curveConf[i].lineWidth,
            dashStyle: curveConf[i].dashStyle,
            marker: { enabled: false },
            yAxis: i,
            data: []
        };

        for (var j = 0; j < data.length; j++) {
            var calDate = (data[j].calDate || '').replace(/-/g, '/');
            var rawVal = data[j].data ? data[j].data[i] : null;

            // 空字符串 / 非数字 → null（避免 Highcharts error #14）
            var numVal = null;
            if (rawVal !== '' && rawVal !== null && rawVal !== undefined) {
                var parsed = parseFloat(rawVal);
                if (!isNaN(parsed)) numVal = parsed;
            }
            singleSeries.data.push([Date.parse(calDate), numVal]);

            if (numVal !== null) {
                if (numVal < 0) allPositive = false;
                else allNegative = false;
            }
        }

        if (allNegative) maxValue = 0;
        else if (allPositive) minValue = 0;

        // 按 graphicSetKey 取对应配置
        var graphicList = graphicSet[graphicSetKey] || null;

        if (graphicList && curveItemCodes[i]) {
            for (var k = 0; k < graphicList.length; k++) {
                var g = graphicList[k];
                if (g.itemCode && g.itemCode.toUpperCase() === curveItemCodes[i].toUpperCase()) {
                    if (g.yAxisMaxValue) maxValue = parseFloat(g.yAxisMaxValue);
                    if (g.yAxisMinValue) minValue = parseFloat(g.yAxisMinValue);
                    break;
                }
            }
        }

        var singleAxis = {
            max: maxValue,
            min: minValue,
            title: { text: legendName[i], style: { color: color[i] } },
            labels: { style: { color: color[i] } },
            opposite: curveConf[i].yAxisOpposite,
            lineWidth: 1,
            tickWidth: 1,
            tickLength: 5
        };

        if (curveConf[i].yAxisOpposite) {
            series_r.push(singleSeries);
            yAxis_r.push(singleAxis);
        } else {
            series_l.push(singleSeries);
            yAxis_l.push(singleAxis);
        }
    }

    var series = [], yAxis = [];
    for (var a = yAxis_l.length - 1; a >= 0; a--) yAxis.push(yAxis_l[a]);
    for (var b = 0; b < yAxis_r.length; b++) yAxis.push(yAxis_r[b]);
    for (var c = 0; c < series_l.length; c++) {
        series_l[c].yAxis = series_l.length - 1 - c;
        series.push(series_l[c]);
    }
    for (var d = 0; d < series_r.length; d++) {
        series_r[d].yAxis = series_l.length + d;
        series.push(series_r[d]);
    }
    for (var e = 0; e < color_l.length; e++) color_all.push(color_l[e]);
    for (var f = 0; f < color_r.length; f++) color_all.push(color_r[f]);

    initAcquisitionReportCurveChartFn(series, tickInterval, divId, title,
                                      '', '', yAxis, color_all, true, timeFormat);
}

function initAcquisitionReportCurveChartFn(series, tickInterval, divId, title,
                                           subtitle, xtitle, yAxis, color, legend, timeFormat) {
    if ($('#' + divId) == undefined || $('#' + divId)[0] == undefined) return;

    new Highcharts.Chart({
        chart: {
            renderTo: divId,
            type: 'spline',
            shadow: false,
            borderWidth: 0,
            zoomType: 'xy',
            zooming: { mouseWheel: { enabled: false } }
        },
        time: { timezoneOffset: new Date().getTimezoneOffset() },
        credits: { enabled: false },
        title: {
            text: title,
            style: { fontSize: (typeof chartTitleFontSize !== 'undefined' ? chartTitleFontSize : 14) }
        },
        subtitle: { text: subtitle },
        colors: color,
        xAxis: {
            type: 'datetime',
            title: { text: xtitle },
            tickPixelInterval: tickInterval,
            labels: {
                formatter: function () {
                    return this.axis.chart.time.dateFormat(timeFormat, this.value);
                },
                autoRotation: true,
                rotation: -45
            }
        },
        yAxis: yAxis,
        tooltip: {
            crosshairs: true,
            shared: true,
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
            enabled: true,
            filename: title,
            fallbackToExportServer: false,
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
                        'downloadXLS'
                    ]
                }
            }
        },
        plotOptions: {
            spline: {
                fillOpacity: 0.3,
                marker: {
                    enabled: true, radius: 3,
                    states: { hover: { enabled: true, radius: 6 } }
                },
                shadow: true
            }
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            enabled: legend,
            borderWidth: 0,
            itemHiddenStyle: { textDecoration: 'none' }
        },
        series: series
    });
}

// ================================================================
// 对比分析（参考历史数据对比分析实现）
// ================================================================
function openAcquisitionComparativeAnalysis() {
    var R = _loginUserLanguageResource;
    var reportName = R.acquisitionReport;

    mini.open({
        title: reportName + '/' + R.comparativeAnalysis,
        url: context + '/miniui-app/modules/report/acquisitionDataComparativeAnalysis.jsp',
        width: '85%',
        height: '85%',
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            if (iframe && iframe.contentWindow && iframe.contentWindow.setData) {
                iframe.contentWindow.setData({
                    orgId: getLeftOrgId(),
                    deviceType: _reportCurrentDeviceType
                });
            }
        }
    });
}

// ================================================================
// 单井采集报表 - 内层事件
// ================================================================
function onAcquisitionReportInnerTabChanged(e) {
    if (isInitializing) return;

    var tab = e && e.tab ? e.tab : null;
    if (!tab) return;

    if (tab.name === 'singleWell') {
        loadSingleWellDeviceGrid();
    } else if (tab.name === 'area') {
        CreateProductionAcquisitionDataReportTable();
        CreateProductionAcquisitionDataReportCurve();
    }
}

function onSwRefresh() {
    var combo = mini.get('swDeviceCombo');
    if (combo) combo.setValue('', false);

    mini.get('swStartDate').setValue('', false);
    mini.get('swEndDate').setValue(new Date(), false);

    _reportSelectedDeviceId     = 0;
    _reportSelectedRowIndex     = 0;
    _reportCurrentDeviceName    = '';
    _reportCurrentCalculateType = 0;

    clearSingleWellReportContent();
    loadSingleWellDeviceGrid();
}

function onSwSearch() {
    CreateSingleWellAcquisitionDataReportTable();
    CreateSingleWellAcquisitionDataReportCurve();
}

// ================================================================
// 单井采集报表 - 导出
// ================================================================
function onSwExport() {
    if (_reportModuleRight.viewFlag != 1) return;
    if (_reportSelectedDeviceId <= 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }
    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'ExportSingleWellAcquisitionDataReportData' + timestamp;

    var startDp = mini.get('swStartDate');
    var endDp   = mini.get('swEndDate');
    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    var url = context + '/reportDataMamagerController/exportSingleWellAcquisitionDataReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=0'
        + '&calculateType=' + _reportCurrentCalculateType
        + '&deviceName=' + URLencode(URLencode(_reportCurrentDeviceName))
        + '&deviceId=' + _reportSelectedDeviceId
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'swDataPanel', R.loadingData);
    document.location.href = url;
}

function onSwBatchExport() {
    if (_reportModuleRight.viewFlag != 1) return;

    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'batchExportSingleWellAcquisitionDataReportData' + timestamp;

    var startDp = mini.get('swStartDate');
    var endDp   = mini.get('swEndDate');
    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    var combo = mini.get('swDeviceCombo');
    var deviceName = combo ? (combo.getValue() || '') : '';

    var deviceTypeName = _reportCurrentLevel2 ? (_reportCurrentLevel2.text || '') : '';

    var url = context + '/reportDataMamagerController/batchExportSingleWellAcquisitionDataReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=0'
        + '&deviceName=' + URLencode(URLencode(deviceName))
        + '&deviceTypeName=' + URLencode(URLencode(deviceTypeName))
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'swDataPanel', R.loadingData);
    document.location.href = url;
}

// ================================================================
// 单井采集报表 - 保存 / 清空
// ================================================================
function onSwSave() {
    if (_reportModuleRight.editFlag != 1) return;
    if (singleWellAcquisitionDataReportHelper != null) {
        singleWellAcquisitionDataReportHelper.saveData();
    }
}

function clearSingleWellReportContent() {
    if (singleWellAcquisitionDataReportHelper != null) {
        if (singleWellAcquisitionDataReportHelper.hot != undefined
            && singleWellAcquisitionDataReportHelper.hot != '') {
            singleWellAcquisitionDataReportHelper.hot.destroy();
        }
        singleWellAcquisitionDataReportHelper = null;
    }

    var dataDiv = document.getElementById('swDataDiv');
    if (dataDiv) dataDiv.innerHTML = '';
    var curveDiv = document.getElementById('swCurveDiv');
    if (curveDiv) curveDiv.innerHTML = '';

    var cnt = document.getElementById('swTotalCount');
    if (cnt) cnt.textContent = '';
}

// ================================================================
// 区域采集报表 - 表格（对照 ExtJS CreateProductionAcquisitionDataReportTable）
// ================================================================
function CreateProductionAcquisitionDataReportTable() {
    var orgId = getLeftOrgId();
    var startDp  = mini.get('pdStartDate');
    var endDp    = mini.get('pdEndDate');
    var reportDp = mini.get('pdReportDate');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var selectedOrgName = getSelectedOrgName();

    mini.mask({ el: 'pdDataPanel', cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getProductionAcquisitionDataDailyReportData',
        type: 'POST',
        data: {
            orgId: orgId,
            selectedOrgName: selectedOrgName,
            startDate: startDate,
            endDate: endDate,
            reportDate: reportDate,
            reportType: 1,
            deviceType: _reportCurrentDeviceType
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask('pdDataPanel');
            fillPdDatesFromResult(result);

            if (productionAcquisitionDataReportHelper != null) {
                if (productionAcquisitionDataReportHelper.hot != undefined
                    && productionAcquisitionDataReportHelper.hot != '') {
                    productionAcquisitionDataReportHelper.hot.destroy();
                }
                productionAcquisitionDataReportHelper = null;
            }

            if (result.success) {
                productionAcquisitionDataReportHelper = ProductionAcquisitionDataReportHelper.createNew(
                    'pdDataDiv', 'ProductionAcquisitionDataReportContainer',
                    result.template, result.data, result.statData, result.columns
                );
                productionAcquisitionDataReportHelper.createTable();
            } else {
                document.getElementById('pdDataDiv').innerHTML = '';
            }

            var totalCnt = document.getElementById('pdTotalCount');
            if (totalCnt) {
                var cnt = (result.data && result.data.length) ? result.data.length : 0;
                totalCnt.textContent = _loginUserLanguageResource.totalCount + ':' + cnt;
            }
        },
        error: function () {
            mini.unmask('pdDataPanel');
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 区域采集报表 - 曲线（对照 ExtJS CreateProductionAcquisitionDataReportCurve）
// ================================================================
function CreateProductionAcquisitionDataReportCurve() {
    var orgId = getLeftOrgId();
    var startDp = mini.get('pdStartDate');
    var endDp   = mini.get('pdEndDate');

    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    var selectedOrgName = getSelectedOrgName();

    mini.mask({ el: 'pdCurvePanel', cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getProductionAcquisitionDataDailyReportCurveData',
        type: 'POST',
        data: {
            orgId: orgId,
            selectedOrgName: selectedOrgName,
            startDate: startDate,
            endDate: endDate,
            reportType: 1,
            deviceType: _reportCurrentDeviceType,
            unitClasses: 2
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask('pdCurvePanel');
            fillPdDatesFromResult(result);
            // 区域采集报表 → History（对照 ExtJS graphicSet.History）
            buildAndRenderCurve(result, 'pdCurveDiv', '%m-%d',
                                'dailyReportCurve', 'History');
        },
        error: function () {
            mini.unmask('pdCurvePanel');
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 区域采集报表 - 日期回填 / 导航
// ================================================================
function fillPdDatesFromResult(result) {
    if (!result) return;

    var startDp = mini.get('pdStartDate');
    if (startDp && !startDp.getValue() && result.startDate) {
        startDp.setValue(result.startDate, false);
    }
    var endDp = mini.get('pdEndDate');
    if (endDp && !endDp.getValue() && result.endDate) {
        endDp.setValue(result.endDate, false);
    }
    var reportDp = mini.get('pdReportDate');
    if (reportDp && !reportDp.getValue() && result.reportDate) {
        reportDp.setValue(result.reportDate, false);
    }

    updatePdNavBtnStatus();
}

function updatePdNavBtnStatus() {
    var startDp  = mini.get('pdStartDate');
    var endDp    = mini.get('pdEndDate');
    var reportDp = mini.get('pdReportDate');

    var startStr  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endStr    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportStr = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var forwardBtn = mini.get('pdForwardBtn');
    var backBtn    = mini.get('pdBackBtn');

    if (!startStr || !endStr || !reportStr) {
        if (forwardBtn) forwardBtn.setEnabled(false);
        if (backBtn)    backBtn.setEnabled(false);
        return;
    }

    var startTime  = new Date(Date.parse(startStr.replace(/-/g, '/'))).getTime();
    var endTime    = new Date(Date.parse(endStr.replace(/-/g, '/'))).getTime();
    var reportTime = new Date(Date.parse(reportStr.replace(/-/g, '/'))).getTime();

    if (forwardBtn) forwardBtn.setEnabled(reportTime > startTime);
    if (backBtn)    backBtn.setEnabled(reportTime < endTime);
}

function onPdRangeDateChanged(e) {
    if (isInitializing) return;

    var reportDp = mini.get('pdReportDate');
    if (reportDp) reportDp.setValue('', false);

    CreateProductionAcquisitionDataReportTable();
    CreateProductionAcquisitionDataReportCurve();
}

function onPdReportDateChanged(e) {
    updatePdNavBtnStatus();
}

function onPdForward() { shiftPdReportDate(-1); }
function onPdBack()    { shiftPdReportDate(1); }

function shiftPdReportDate(day) {
    var reportDp = mini.get('pdReportDate');
    if (!reportDp) return;
    var str = reportDp.getFormValue('yyyy-MM-dd');
    if (!str) return;

    var d = new Date(Date.parse(str.replace(/-/g, '/')));
    d.setTime(d.getTime() + day * 24 * 3600 * 1000);

    reportDp.setValue(d);

    // 对照 ExtJS：只刷新表格
    CreateProductionAcquisitionDataReportTable();
}

// ================================================================
// 区域采集报表 - 刷新 / 搜索 / 清空
// ================================================================
function onPdRefresh() {
    mini.get('pdStartDate').setValue('', false);
    mini.get('pdEndDate').setValue(new Date(), false);
    mini.get('pdReportDate').setValue('', false);

    updatePdNavBtnStatus();
    clearProductionAcquisitionReportContent();

    CreateProductionAcquisitionDataReportTable();
    CreateProductionAcquisitionDataReportCurve();
}

function onPdSearch() {
    var reportDp = mini.get('pdReportDate');
    if (reportDp) reportDp.setValue('', false);

    CreateProductionAcquisitionDataReportTable();
    CreateProductionAcquisitionDataReportCurve();
}

function clearProductionAcquisitionReportContent() {
    if (productionAcquisitionDataReportHelper != null) {
        if (productionAcquisitionDataReportHelper.hot != undefined
            && productionAcquisitionDataReportHelper.hot != '') {
            productionAcquisitionDataReportHelper.hot.destroy();
        }
        productionAcquisitionDataReportHelper = null;
    }

    var pdData = document.getElementById('pdDataDiv');
    if (pdData) pdData.innerHTML = '';
    var pdCurve = document.getElementById('pdCurveDiv');
    if (pdCurve) pdCurve.innerHTML = '';

    var cnt = document.getElementById('pdTotalCount');
    if (cnt) cnt.textContent = '';
}

// ================================================================
// 区域采集报表 - 导出
// ================================================================
function onPdExport() {
    if (_reportModuleRight.viewFlag != 1) return;

    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'exportProductionAcquisitionDataReportData' + timestamp;

    var startDp  = mini.get('pdStartDate');
    var endDp    = mini.get('pdEndDate');
    var reportDp = mini.get('pdReportDate');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var selectedOrgName = getSelectedOrgName();

    var url = context + '/reportDataMamagerController/exportProductionAcquisitionDataReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=1'
        + '&selectedOrgName=' + URLencode(URLencode(selectedOrgName))
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&reportDate=' + reportDate
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'pdDataPanel', R.loadingData);
    document.location.href = url;
}

function onPdBatchExport() {
    if (_reportModuleRight.viewFlag != 1) return;

    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'batchExportProductionAcquisitionDataReportData' + timestamp;

    var startDp  = mini.get('pdStartDate');
    var endDp    = mini.get('pdEndDate');
    var reportDp = mini.get('pdReportDate');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var selectedOrgName = getSelectedOrgName();

    var url = context + '/reportDataMamagerController/batchExportProductionAcquisitionDataReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=1'
        + '&selectedOrgName=' + URLencode(URLencode(selectedOrgName))
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&reportDate=' + reportDate
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'pdDataPanel', R.loadingData);
    document.location.href = url;
}

// ================================================================
// 区域采集报表 - 保存
// ================================================================
function onPdSave() {
    if (_reportModuleRight.editFlag != 1) return;
    if (productionAcquisitionDataReportHelper != null) {
        productionAcquisitionDataReportHelper.saveData();
    }
}

// ================================================================
// Handsontable Helper - 单井采集报表（对照 ExtJS SingleWellAcquisitionDataReportHelper）
// ================================================================
var SingleWellAcquisitionDataReportHelper = {
    createNew: function (divId, containerid, templateData, contentData, columns) {
        var helper = {};
        helper.templateData = templateData;
        helper.contentData  = contentData;
        helper.columns      = columns;
        helper.data         = [];
        helper.sourceData   = [];
        helper.hot          = '';
        helper.container    = document.getElementById(divId);
        helper.columnCount  = 0;
        helper.editData     = {};
        helper.contentUpdateList = [];

        helper.hiddenColumns = [];
        if (helper.templateData.hiddenColumns != undefined) {
            helper.hiddenColumns = helper.templateData.hiddenColumns;
        }

        helper.colWidths = [];
        if (loginUserLanguage == 'zh_CN') {
            helper.colWidths = helper.templateData.columnWidths_zh_CN;
        } else if (loginUserLanguage == 'en') {
            helper.colWidths = helper.templateData.columnWidths_en;
        } else if (loginUserLanguage == 'ru') {
            helper.colWidths = helper.templateData.columnWidths_ru;
        }

        helper.initData = function () {
            helper.data = [];
            helper.sourceData = [];
            for (var i = 0; i < helper.templateData.header.length; i++) {
                if (loginUserLanguage == 'zh_CN') {
                    helper.templateData.header[i].title = helper.templateData.header[i].title_zh_CN;
                } else if (loginUserLanguage == 'en') {
                    helper.templateData.header[i].title = helper.templateData.header[i].title_en;
                } else if (loginUserLanguage == 'ru') {
                    helper.templateData.header[i].title = helper.templateData.header[i].title_ru;
                }
                helper.templateData.header[i].title.push('');
                helper.columnCount = helper.templateData.header[i].title.length;
                helper.hiddenColumns.push(helper.columnCount - 1);

                var valueArr = [], sourceArr = [];
                for (var j = 0; j < helper.templateData.header[i].title.length; j++) {
                    valueArr.push(helper.templateData.header[i].title[j]);
                    sourceArr.push(helper.templateData.header[i].title[j]);
                }
                helper.data.push(valueArr);
                helper.sourceData.push(sourceArr);
            }
            for (var i2 = 0; i2 < helper.contentData.length; i2++) {
                var v2 = [], s2 = [];
                for (var j2 = 0; j2 < helper.contentData[i2].length; j2++) {
                    v2.push(helper.contentData[i2][j2]);
                    s2.push(helper.contentData[i2][j2]);
                }
                helper.data.push(v2);
                helper.sourceData.push(s2);
            }
            // 截断长文本
            for (var i3 = helper.templateData.header.length; i3 < helper.data.length; i3++) {
                for (var j3 = 0; j3 < helper.data[i3].length; j3++) {
                    var editable = false;
                    for (var k = 0; k < helper.templateData.editable.length; k++) {
                        if (i3 >= helper.templateData.editable[k].startRow
                            && i3 <= helper.templateData.editable[k].endRow
                            && j3 >= helper.templateData.editable[k].startColumn
                            && j3 <= helper.templateData.editable[k].endColumn) {
                            editable = true;
                            break;
                        }
                    }
                    var value = helper.data[i3][j3];
                    if ((!editable) && value && value.length > 12) {
                        value = value.substring(0, 11) + "...";
                        helper.data[i3][j3] = value;
                    }
                }
            }
        };

        helper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (helper != null && helper.hot != null) {
                for (var i = 0; i < helper.templateData.header.length; i++) {
                    if (row == i) {
                        if (isNotVal(helper.templateData.header[i].tdStyle)) {
                            var tdStyle = helper.templateData.header[i].tdStyle;
                            if (isNotVal(tdStyle.fontWeight))      td.style.fontWeight = tdStyle.fontWeight;
                            if (isNotVal(tdStyle.fontSize))        td.style.fontSize = tdStyle.fontSize;
                            if (isNotVal(tdStyle.height))          td.style.height = tdStyle.height;
                            if (isNotVal(tdStyle.color))           td.style.color = tdStyle.color;
                            if (isNotVal(tdStyle.backgroundColor)) td.style.backgroundColor = tdStyle.backgroundColor;
                            if (isNotVal(tdStyle.textAlign))       td.style.textAlign = tdStyle.textAlign;
                        }
                        break;
                    }
                }
                if (row >= helper.templateData.header.length) {
                    td.style.whiteSpace = 'nowrap';
                    td.style.overflow = 'hidden';
                    td.style.textOverflow = 'ellipsis';
                }
            }
        };

        helper.addEditableColor = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        helper.createTable = function () {
            helper.container.innerHTML = '';
            helper.hot = new Handsontable(helper.container, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                theme: 'ht-theme-classic',
                data: helper.data,
                hiddenColumns: {
                    columns: helper.hiddenColumns,
                    indicators: false,
                    copyPasteEnabled: false
                },
                fixedRowsTop: helper.templateData.fixedRowsTop,
                fixedRowsBottom: helper.templateData.fixedRowsBottom,
                rowHeaders: false,
                colHeaders: false,
                rowHeights: helper.templateData.rowHeights,
                colWidths: helper.colWidths,
                stretchH: 'all',
                columnSorting: true,
                allowInsertRow: false,
                sortIndicator: true,
                manualColumnResize: true,
                manualRowResize: true,
                filters: true,
                renderAllRows: true,
                search: true,
                mergeCells: helper.templateData.mergeCells,
                contextMenu: {
                    items: {
                        'copy': { name: _loginUserLanguageResource.contextMenu_copy },
                        'cut':  { name: _loginUserLanguageResource.contextMenu_cut }
                    }
                },
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var colConfig = helper.columns[col];
                    var colType = (colConfig && colConfig.type) ? colConfig.type : 'text';
                    cellProperties.renderer = helper.addStyle;
                    cellProperties.editor = false;
                    if (helper.templateData.editable != null
                        && helper.templateData.editable.length > 0) {
                        for (var i = 0; i < helper.templateData.editable.length; i++) {
                            var ed = helper.templateData.editable[i];
                            if (row >= ed.startRow && row <= ed.endRow
                                && col >= ed.startColumn && col <= ed.endColumn) {
                                cellProperties.editor = colType;
                                cellProperties.renderer = helper.addEditableColor;
                            }
                        }
                    }
                    return cellProperties;
                },
                afterOnCellMouseOver: function (event, coords, TD) {
                    if (coords.col >= 0 && coords.row >= 0
                        && helper != null && helper.hot != ''
                        && helper.hot != undefined
                        && helper.hot.getDataAtCell != undefined) {
                        var rawValue = helper.sourceData[coords.row] ? helper.sourceData[coords.row][coords.col] : null;
                        if (isNotVal(rawValue)) TD.title = String(rawValue);
                    }
                },
                afterChange: function (changes, source) {
                    if (helper != null && helper.hot != undefined
                        && helper.hot != '' && changes != null) {
                        for (var i = 0; i < changes.length; i++) {
                            var index = changes[i][0];
                            var rowdata = helper.hot.getDataAtRow(index);

                            var editCellInfo = {};
                            editCellInfo.editRow = changes[i][0];
                            editCellInfo.editCol = changes[i][1];
                            editCellInfo.column  = helper.columns[editCellInfo.editCol];
                            editCellInfo.recordId = rowdata[rowdata.length - 1];
                            editCellInfo.oldValue = changes[i][2];
                            editCellInfo.newValue = changes[i][3];
                            editCellInfo.header = false;
                            if (editCellInfo.editRow < helper.templateData.header.length) {
                                editCellInfo.header = true;
                            }

                            var isExit = false;
                            for (var j = 0; j < helper.contentUpdateList.length; j++) {
                                if (editCellInfo.editRow == helper.contentUpdateList[j].editRow
                                    && editCellInfo.editCol == helper.contentUpdateList[j].editCol) {
                                    helper.contentUpdateList[j].newValue = editCellInfo.newValue;
                                    isExit = true;
                                    break;
                                }
                            }
                            if (!isExit) helper.contentUpdateList.push(editCellInfo);
                        }
                    }
                }
            });
        };

        helper.saveData = function () {
            if (helper.contentUpdateList.length > 0) {
                helper.editData.contentUpdateList = helper.contentUpdateList;

                var deviceName = '';
                var deviceId = 0;
                if (_reportSelectedDeviceId > 0) {
                    deviceName = _reportCurrentDeviceName;
                    deviceId = _reportSelectedDeviceId;
                }

                mini.mask({ el: 'swDataPanel', cls: 'mini-mask-loading',
                            html: _loginUserLanguageResource.submittingData });

                $.ajax({
                    method: 'POST',
                    /* ★ 对照 ExtJS singleWellAcquisitionDataReportHelper.saveData */
                    url: context + '/reportDataMamagerController/saveSingleWellRangeDailyReportData',
                    data: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        data: JSON.stringify(helper.editData),
                        deviceType: _reportCurrentDeviceType
                    },
                    dataType: 'json',
                    success: function (response) {
                        mini.unmask('swDataPanel');
                        if (response && response.success) {
                            mini.alert(_loginUserLanguageResource.savedSuccessfully,
                                       _loginUserLanguageResource.tip);
                            helper.clearContainer();
                            CreateSingleWellAcquisitionDataReportTable();
                            CreateSingleWellAcquisitionDataReportCurve();
                        } else {
                            helper.clearContainer();
                            mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>',
                                       _loginUserLanguageResource.tip);
                        }
                    },
                    error: function () {
                        mini.unmask('swDataPanel');
                        mini.alert(_loginUserLanguageResource.requestFailed,
                                   _loginUserLanguageResource.tip);
                    }
                });
            } else {
                mini.alert(_loginUserLanguageResource.noDataChange,
                           _loginUserLanguageResource.tip);
            }
        };

        helper.clearContainer = function () {
            helper.editData = {};
            helper.contentUpdateList = [];
        };

        helper.initData();
        return helper;
    }
};

// ================================================================
// Handsontable Helper - 区域采集报表（对照 ExtJS ProductionAcquisitionDataReportHelper，含统计行）
// ================================================================
var ProductionAcquisitionDataReportHelper = {
    createNew: function (divId, containerid, templateData, contentData, statData, columns) {
        var helper = {};
        helper.templateData = templateData;
        helper.contentData  = contentData;
        helper.statData     = statData;
        helper.columns      = columns;
        helper.data         = [];
        helper.sourceData   = [];
        helper.hot          = '';
        helper.container    = document.getElementById(divId);
        helper.columnCount  = 0;
        helper.editData     = {};
        helper.contentUpdateList = [];

        helper.hiddenColumns = [];
        if (helper.templateData.hiddenColumns != undefined) {
            helper.hiddenColumns = helper.templateData.hiddenColumns;
        }

        helper.colWidths = [];
        if (loginUserLanguage == 'zh_CN') {
            helper.colWidths = helper.templateData.columnWidths_zh_CN;
        } else if (loginUserLanguage == 'en') {
            helper.colWidths = helper.templateData.columnWidths_en;
        } else if (loginUserLanguage == 'ru') {
            helper.colWidths = helper.templateData.columnWidths_ru;
        }

        helper.initData = function () {
            helper.data = [];
            helper.sourceData = [];
            for (var i = 0; i < helper.templateData.header.length; i++) {
                if (loginUserLanguage == 'zh_CN') {
                    helper.templateData.header[i].title = helper.templateData.header[i].title_zh_CN;
                } else if (loginUserLanguage == 'en') {
                    helper.templateData.header[i].title = helper.templateData.header[i].title_en;
                } else if (loginUserLanguage == 'ru') {
                    helper.templateData.header[i].title = helper.templateData.header[i].title_ru;
                }
                helper.templateData.header[i].title.push('');
                helper.columnCount = helper.templateData.header[i].title.length;
                helper.hiddenColumns.push(helper.columnCount - 1);

                var valueArr = [], sourceArr = [];
                for (var j = 0; j < helper.templateData.header[i].title.length; j++) {
                    valueArr.push(helper.templateData.header[i].title[j]);
                    sourceArr.push(helper.templateData.header[i].title[j]);
                }
                helper.data.push(valueArr);
                helper.sourceData.push(sourceArr);
            }
            for (var i2 = 0; i2 < helper.contentData.length; i2++) {
                var v2 = [], s2 = [];
                for (var j2 = 0; j2 < helper.contentData[i2].length; j2++) {
                    v2.push(helper.contentData[i2][j2]);
                    s2.push(helper.contentData[i2][j2]);
                }
                helper.data.push(v2);
                helper.sourceData.push(s2);
            }
            // 区域报表特有：统计行
            for (var i3 = 0; i3 < helper.statData.length; i3++) {
                var v3 = [], s3 = [];
                for (var j3 = 0; j3 < helper.statData[i3].length; j3++) {
                    v3.push(helper.statData[i3][j3]);
                    s3.push(helper.statData[i3][j3]);
                }
                helper.data.push(v3);
                helper.sourceData.push(s3);
            }
            // 截断长文本
            for (var i4 = helper.templateData.header.length; i4 < helper.data.length; i4++) {
                for (var j4 = 0; j4 < helper.data[i4].length; j4++) {
                    var editable = false;
                    for (var k = 0; k < helper.templateData.editable.length; k++) {
                        if (i4 >= helper.templateData.editable[k].startRow
                            && i4 <= helper.templateData.editable[k].endRow
                            && j4 >= helper.templateData.editable[k].startColumn
                            && j4 <= helper.templateData.editable[k].endColumn) {
                            editable = true;
                            break;
                        }
                    }
                    var value = helper.data[i4][j4];
                    if ((!editable) && value && value.length > 12) {
                        value = value.substring(0, 11) + "...";
                        helper.data[i4][j4] = value;
                    }
                }
            }
        };

        helper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (helper != null && helper.hot != null) {
                for (var i = 0; i < helper.templateData.header.length; i++) {
                    if (row == i) {
                        if (isNotVal(helper.templateData.header[i].tdStyle)) {
                            var tdStyle = helper.templateData.header[i].tdStyle;
                            if (isNotVal(tdStyle.fontWeight))      td.style.fontWeight = tdStyle.fontWeight;
                            if (isNotVal(tdStyle.fontSize))        td.style.fontSize = tdStyle.fontSize;
                            if (isNotVal(tdStyle.height))          td.style.height = tdStyle.height;
                            if (isNotVal(tdStyle.color))           td.style.color = tdStyle.color;
                            if (isNotVal(tdStyle.backgroundColor)) td.style.backgroundColor = tdStyle.backgroundColor;
                            if (isNotVal(tdStyle.textAlign))       td.style.textAlign = tdStyle.textAlign;
                        }
                        break;
                    }
                }
                if (row >= helper.templateData.header.length) {
                    td.style.whiteSpace = 'nowrap';
                    td.style.overflow = 'hidden';
                    td.style.textOverflow = 'ellipsis';
                }
            }
        };

        helper.addEditableColor = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.color = '#ff0000';
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        helper.createTable = function () {
            helper.container.innerHTML = '';
            helper.hot = new Handsontable(helper.container, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                theme: 'ht-theme-classic',
                data: helper.data,
                hiddenColumns: {
                    columns: helper.hiddenColumns,
                    indicators: false,
                    copyPasteEnabled: false
                },
                fixedRowsTop: helper.templateData.fixedRowsTop,
                fixedRowsBottom: helper.templateData.fixedRowsBottom,
                rowHeaders: false,
                colHeaders: false,
                rowHeights: helper.templateData.rowHeights,
                colWidths: helper.colWidths,
                stretchH: 'all',
                columnSorting: true,
                allowInsertRow: false,
                sortIndicator: true,
                manualColumnResize: true,
                manualRowResize: true,
                filters: true,
                renderAllRows: true,
                search: true,
                mergeCells: helper.templateData.mergeCells,
                contextMenu: {
                    items: {
                        'copy': { name: _loginUserLanguageResource.contextMenu_copy },
                        'cut':  { name: _loginUserLanguageResource.contextMenu_cut }
                    }
                },
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var colConfig = helper.columns[col];
                    var colType = (colConfig && colConfig.type) ? colConfig.type : 'text';
                    cellProperties.renderer = helper.addStyle;
                    cellProperties.editor = false;
                    if (helper.templateData.editable != null
                        && helper.templateData.editable.length > 0) {
                        for (var i = 0; i < helper.templateData.editable.length; i++) {
                            var ed = helper.templateData.editable[i];
                            // 对照 ExtJS：统计行不可编辑
                            if (row >= ed.startRow && row <= ed.endRow
                                && col >= ed.startColumn && col <= ed.endColumn
                                && row < helper.templateData.header.length + helper.contentData.length) {
                                cellProperties.editor = colType;
                                cellProperties.renderer = helper.addEditableColor;
                            }
                        }
                    }
                    return cellProperties;
                },
                afterOnCellMouseOver: function (event, coords, TD) {
                    if (coords.col >= 0 && coords.row >= 0
                        && helper != null && helper.hot != ''
                        && helper.hot != undefined
                        && helper.hot.getDataAtCell != undefined) {
                        var rawValue = helper.sourceData[coords.row] ? helper.sourceData[coords.row][coords.col] : null;
                        if (isNotVal(rawValue)) TD.title = String(rawValue);
                    }
                },
                afterChange: function (changes, source) {
                    if (helper != null && helper.hot != undefined
                        && helper.hot != '' && changes != null) {
                        for (var i = 0; i < changes.length; i++) {
                            var index = changes[i][0];
                            var rowdata = helper.hot.getDataAtRow(index);

                            var editCellInfo = {};
                            editCellInfo.editRow = changes[i][0];
                            editCellInfo.editCol = changes[i][1];
                            editCellInfo.column  = helper.columns[editCellInfo.editCol];
                            editCellInfo.recordId = rowdata[rowdata.length - 1];
                            editCellInfo.oldValue = changes[i][2];
                            editCellInfo.newValue = changes[i][3];
                            editCellInfo.header = false;
                            if (editCellInfo.editRow < helper.templateData.header.length) {
                                editCellInfo.header = true;
                            }

                            var isExit = false;
                            for (var j = 0; j < helper.contentUpdateList.length; j++) {
                                if (editCellInfo.editRow == helper.contentUpdateList[j].editRow
                                    && editCellInfo.editCol == helper.contentUpdateList[j].editCol) {
                                    helper.contentUpdateList[j].newValue = editCellInfo.newValue;
                                    isExit = true;
                                    break;
                                }
                            }
                            if (!isExit) helper.contentUpdateList.push(editCellInfo);
                        }
                    }
                }
            });
        };

        helper.saveData = function () {
            if (helper.contentUpdateList.length > 0) {
                helper.editData.contentUpdateList = helper.contentUpdateList;

                mini.mask({ el: 'pdDataPanel', cls: 'mini-mask-loading',
                            html: _loginUserLanguageResource.submittingData });

                $.ajax({
                    method: 'POST',
                    /* ★ 对照 ExtJS productionAcquisitionDataReportHelper.saveData（deviceType=1） */
                    url: context + '/reportDataMamagerController/saveSingleWellRangeDailyReportData',
                    data: {
                        data: JSON.stringify(helper.editData),
                        deviceType: 1
                    },
                    dataType: 'json',
                    success: function (response) {
                        mini.unmask('pdDataPanel');
                        if (response && response.success) {
                            mini.alert(_loginUserLanguageResource.savedSuccessfully,
                                       _loginUserLanguageResource.tip);
                            helper.clearContainer();
                            CreateProductionAcquisitionDataReportTable();
                            CreateProductionAcquisitionDataReportCurve();
                        } else {
                            helper.clearContainer();
                            mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>',
                                       _loginUserLanguageResource.tip);
                        }
                    },
                    error: function () {
                        mini.unmask('pdDataPanel');
                        mini.alert(_loginUserLanguageResource.requestFailed,
                                   _loginUserLanguageResource.tip);
                    }
                });
            } else {
                mini.alert(_loginUserLanguageResource.noDataChange,
                           _loginUserLanguageResource.tip);
            }
        };

        helper.clearContainer = function () {
            helper.editData = {};
            helper.contentUpdateList = [];
        };

        helper.initData();
        return helper;
    }
};