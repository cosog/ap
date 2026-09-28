// ================================================================
// 生产报表模块 - productionReport.js
// 严格对照 ExtJS：SingleWellDailyReportPanel + ProductionDailyReportPanel
// ================================================================

var _reportLevel1Data = [];
var _reportLevel2Data = [];
var _reportCurrentLevel1 = null;
var _reportCurrentLevel2 = null;
var _reportCurrentDeviceType = '';

var _reportModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };

// ---------- 单井日报 - 全局 ----------
var singleWellDailyReportHelper = null;
var singleWellRangeReportHelper = null;

var _reportSelectedDeviceId     = 0;
var _reportSelectedRowIndex     = 0;
var _reportCurrentDeviceName    = '';
var _reportCurrentCalculateType = 0;
var _reportDeviceTotalCount     = 0;
var _reportGridLoading          = false;

// ---------- 区域日报 - 全局 ----------
var productionDailyReportHelper = null;

var _pdSelectedInstanceCode = '';
var _pdSelectedUnitId       = 0;
var _pdSelectedInstanceName = '';
var _pdSelectedRowId        = 0;

var isInitializing = true;

// ================================================================
// 页面初始化
// ================================================================
function initProductionReportPage() {
    initReportModuleRight();
    buildReportLevel1Tabs();
    initReportI18n();
    initReportDefaultDates();

    initProductionReportMessageListener();

    isInitializing = false;
}

// ================================================================
// 权限
// ================================================================
function initReportModuleRight() {
    _reportModuleRight = getRoleModuleRight(
        context + '/roleManagerController/getRoleModuleRight',
        'DailyReport'
    ) || { viewFlag: 0, editFlag: 0, controlFlag: 0 };

    _reportModuleRight.viewFlag    = parseInt(_reportModuleRight.viewFlag)    || 0;
    _reportModuleRight.editFlag    = parseInt(_reportModuleRight.editFlag)    || 0;
    _reportModuleRight.controlFlag = parseInt(_reportModuleRight.controlFlag) || 0;

    updateReportBtnStatus();
}

function updateReportBtnStatus() {
    var canEdit = (_reportModuleRight.editFlag == 1);
    var btnIds = [
        'swHourlySaveBtn', 'swRangeSaveBtn', 'pdSaveBtn',
        'swBatchExportBtn', 'swHourlyExportBtn', 'swRangeExportBtn',
        'pdExportBtn', 'pdBatchExportBtn'
    ];
    for (var j = 0; j < btnIds.length; j++) {
        var btn = mini.get(btnIds[j]);
        if (btn) btn.setEnabled(canEdit);
    }
}

// ================================================================
// 消息监听
// ================================================================
function initProductionReportMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        switch (message.action) {
            case 'refresh':
                var tabs = mini.get('dailyReportInnerTabs');
                if (!tabs) return;
                var tab = tabs.getActiveTab();
                if (!tab) return;
                if (tab.name === 'singleWell') {
                    loadSingleWellDeviceGrid();
                } else if (tab.name === 'area') {
                    loadProductionDailyReportInstanceGrid();
                }
                break;
        }
    });
}

// ================================================================
// 一二级标签
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
    if (_reportLevel1Data.length > 0) selectReportLevel1(0);
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

    if (children.length === 0) {
        container.innerHTML = '<div class="no-child-tip">'
            + _loginUserLanguageResource.emptyMsg + '</div>';
        _reportCurrentLevel2 = null;
        _reportCurrentDeviceType = parentItem.deviceTypeId || '';
        onReportDeviceTypeChanged();
        return;
    }

    _reportLevel2Data = children;

    var allIds = [];
    for (var i = 0; i < children.length; i++) {
        if (children[i].deviceTypeId) allIds.push(children[i].deviceTypeId);
    }

    var allTabs = [];
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
            div.className = 'tab-item' + (idx === 0 ? ' active' : '');
            div.dataset.index = idx;
            div.dataset.deviceTypeId = item.deviceTypeId || '';
            div.textContent = item.text;
            div.title = item.text;
            div.onclick = function () { selectReportLevel2(parseInt(this.dataset.index)); };
            container.appendChild(div);
        })(i, allTabs[i]);
    }

    if (allTabs.length > 0) selectReportLevel2(0);
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
    // 重置单井日报
    _reportSelectedDeviceId     = 0;
    _reportSelectedRowIndex     = 0;
    _reportCurrentDeviceName    = '';
    _reportCurrentCalculateType = 0;

    var combo = mini.get('swDeviceCombo');
    if (combo) combo.setValue('', false);

    clearSingleWellReportContent();

    // 重置区域日报
    _pdSelectedInstanceCode = '';
    _pdSelectedUnitId       = 0;
    _pdSelectedInstanceName = '';
    _pdSelectedRowId        = 0;

    clearProductionDailyReportContent();

    // ★ 只加载当前激活 tab 的数据
    var tabs = mini.get('dailyReportInnerTabs');
    if (!tabs) return;
    var tab = tabs.getActiveTab();
    if (!tab) return;

    if (tab.name === 'singleWell') {
        loadSingleWellDeviceGrid();
    } else if (tab.name === 'area') {
        loadProductionDailyReportInstanceGrid();
    }
}

// ================================================================
// 国际化
// ================================================================
function initReportI18n() {
    var R = _loginUserLanguageResource;

    setTabTitle('dailyReportInnerTabs', 0, R.singleDeviceReport);
    setTabTitle('dailyReportInnerTabs', 1, R.areaReport);
    setTabTitle('swInnerTabs', 0, R.hourlyReport);
    setTabTitle('swInnerTabs', 1, R.dailyReport);
    setTabTitle('pdInnerTabs', 0, R.dailyReport);

    setPanelTitle('swDeviceListPanel', R.deviceList);
    setPanelTitle('pdInstancePanel', R.reportInstanceList);
    setPanelTitle('swHourlyCurvePanel', R.reportCurve);
    setPanelTitle('swHourlyDataPanel', R.reportData);
    setPanelTitle('swRangeCurvePanel', R.reportCurve);
    setPanelTitle('swRangeDataPanel', R.reportData);
    setPanelTitle('pdCurvePanel', R.reportCurve);
    setPanelTitle('pdDataPanel', R.reportData);

    setBtnText('swRefreshBtn', R.refresh);
    setBtnText('swSearchBtn', R.search);
    setBtnText('swBatchExportBtn', R.bulkExportData);
    setHtml('swLblDevice', R.deviceName + '：');
    setHtml('swLblDate', R.date + '：');
    setHtml('swLblTimeTo', R.timeTo + '：');

    setBtnText('swHourlyForwardBtn', R.forward);
    setBtnText('swHourlyBackBtn', R.backward);
    setBtnText('swHourlyExportBtn', R.exportData);
    setBtnText('swHourlySaveBtn', R.save);
    setHtml('swHourlyLblInterval', R.interval + '：');

    var hourlyInterval = mini.get('swHourlyInterval');
    if (hourlyInterval) {
        hourlyInterval.setData([
            { id: 2, text: R.twoHours },
            { id: 1, text: R.oneHour }
        ]);
        hourlyInterval.setValue(2, false);
        hourlyInterval.setEmptyText(R.selectInterval);
    }

    setBtnText('swRangeExportBtn', R.exportData);
    setBtnText('swRangeSaveBtn', R.save);

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

    var pdWellCombo = mini.get('pdWellCombo');
    if (pdWellCombo) pdWellCombo.setEmptyText('--' + R.all + '--');

    var swGrid = mini.get('swDeviceGrid');
    if (swGrid) swGrid.setEmptyText(R.emptyMsg);

    var pdGrid = mini.get('pdInstanceGrid');
    if (pdGrid) pdGrid.setEmptyText(R.emptyMsg);
}

// ================================================================
// 默认日期
// ================================================================
function initReportDefaultDates() {
    var today = new Date();

    mini.get('swStartDate').setValue('', false);
    mini.get('swEndDate').setValue(today, false);
    mini.get('swHourlyReportDate').setValue('', false);

    mini.get('pdStartDate').setValue('', false);
    mini.get('pdEndDate').setValue(today, false);
    mini.get('pdReportDate').setValue('', false);

    updateSwHourlyNavBtnStatus();
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
// 单井日报 - 设备下拉框
// ================================================================
function onSwDeviceComboBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize
        || (typeof defaultWellComboxSize !== 'undefined' ? defaultWellComboxSize : 50);

    params.start = pageIndex * pageSize;
    params.limit = pageSize;
    params.orgId      = getLeftOrgId();
    params.deviceType = _reportCurrentDeviceType;

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
// 单井日报 - 设备列表
// ================================================================
function loadSingleWellDeviceGrid() {
    var grid = mini.get('swDeviceGrid');
    if (!grid) return;

    if (!grid.getUrl()) {
        grid.setUrl(context + '/reportDataMamagerController/getDeviceList');
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
        var selected = grid.getSelecteds() || [];
        if (selected.length === 0) {
            var selectRow = 0;
            if (_reportSelectedDeviceId > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].id == _reportSelectedDeviceId) {
                        selectRow = i;
                        break;
                    }
                }
            }
            grid.select(data[selectRow]);
        }
    } else {
        _reportSelectedDeviceId     = 0;
        _reportSelectedRowIndex     = 0;
        _reportCurrentDeviceName    = '';
        _reportCurrentCalculateType = 0;
        clearSingleWellReportContent();
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

    CreateSingleWellReportTable();
    CreateSingleWellReportCurve();
}

// ================================================================
// 单井日报 - 内层 tab 切换
// ================================================================
function onSWInnerTabsTabChanged(e) {
    if (isInitializing) return;
    var tab = e && e.tab ? e.tab : null;
    if (!tab) return;

    if (tab.name === 'hourly') {
        var reportDp = mini.get('swHourlyReportDate');
        if (reportDp) reportDp.setValue('', false);
    }
    CreateSingleWellReportTable();
    CreateSingleWellReportCurve();
}

// ================================================================
// 单井日报 - 分派器（对照 ExtJS CreateSingleWellReportTable/Curve）
// ================================================================
function CreateSingleWellReportTable() {
    var tabs = mini.get('swInnerTabs');
    if (!tabs) return;
    var activeTab = tabs.getActiveTab();
    if (!activeTab) return;

    if (activeTab.name === 'hourly') {
        CreateSingleWellDailyReportTable();
    } else if (activeTab.name === 'range') {
        CreateSingleWellRangeReportTable();
    }
}

function CreateSingleWellReportCurve() {
    var tabs = mini.get('swInnerTabs');
    if (!tabs) return;
    var activeTab = tabs.getActiveTab();
    if (!activeTab) return;

    if (activeTab.name === 'hourly') {
        CreateSingleWellDailyReportCurve();
    } else if (activeTab.name === 'range') {
        CreateSingleWellRangeReportCurve();
    }
}

// ================================================================
// 单井日报 - 日期事件
// ================================================================
function onSwRangeDateChanged(e) {
    if (isInitializing) return;

    var tabs = mini.get('swInnerTabs');
    if (tabs) {
        var activeTab = tabs.getActiveTab();
        if (activeTab && activeTab.name === 'hourly') {
            var reportDp = mini.get('swHourlyReportDate');
            if (reportDp) reportDp.setValue('', false);
        }
    }
    CreateSingleWellReportTable();
    CreateSingleWellReportCurve();
}

function onSwHourlyReportDateChanged(e) {
    updateSwHourlyNavBtnStatus();
}

function onSwHourlyIntervalChanged(e) {
    if (isInitializing) return;
    CreateSingleWellDailyReportTable();
    CreateSingleWellDailyReportCurve();
}

function updateSwHourlyNavBtnStatus() {
    var startDp  = mini.get('swStartDate');
    var endDp    = mini.get('swEndDate');
    var reportDp = mini.get('swHourlyReportDate');

    var startStr  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endStr    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportStr = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var forwardBtn = mini.get('swHourlyForwardBtn');
    var backBtn    = mini.get('swHourlyBackBtn');

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

function onSwHourlyForward() { shiftSwHourlyReportDate(-1); }
function onSwHourlyBack()    { shiftSwHourlyReportDate(1); }

function shiftSwHourlyReportDate(day) {
    var reportDp = mini.get('swHourlyReportDate');
    if (!reportDp) return;
    var str = reportDp.getFormValue('yyyy-MM-dd');
    if (!str) return;

    var d = new Date(Date.parse(str.replace(/-/g, '/')));
    d.setTime(d.getTime() + day * 24 * 3600 * 1000);

    // setValue 触发 onvaluechanged → updateSwHourlyNavBtnStatus
    reportDp.setValue(d);

    CreateSingleWellDailyReportTable();
    CreateSingleWellDailyReportCurve();
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
    var reportDp = mini.get('swHourlyReportDate');
    if (reportDp && !reportDp.getValue() && result.reportDate) {
        reportDp.setValue(result.reportDate, false);
    }

    updateSwHourlyNavBtnStatus();
}

// ================================================================
// 单井日报 - 班报表表格
// ================================================================
function CreateSingleWellDailyReportTable() {
    if (_reportSelectedDeviceId <= 0) return;

    var orgId         = getLeftOrgId();
    var deviceId      = _reportSelectedDeviceId;
    var deviceName    = _reportCurrentDeviceName;
    var calculateType = _reportCurrentCalculateType;
    var deviceType    = _reportCurrentDeviceType;

    var startDp    = mini.get('swStartDate');
    var endDp      = mini.get('swEndDate');
    var reportDp   = mini.get('swHourlyReportDate');
    var intervalCb = mini.get('swHourlyInterval');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';
    var interval   = intervalCb ? intervalCb.getValue() : 2;

    mini.mask({ el: 'swHourlyDataPanel', cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getSingleWellDailyReportData',
        type: 'POST',
        data: {
            orgId: orgId,
            deviceId: deviceId,
            deviceName: deviceName,
            startDate: startDate,
            endDate: endDate,
            reportDate: reportDate,
            reportType: 2,
            interval: interval,
            deviceType: deviceType,
            calculateType: calculateType
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask('swHourlyDataPanel');
            fillSwDatesFromResult(result);

            if (singleWellDailyReportHelper != null) {
                if (singleWellDailyReportHelper.hot != undefined
                    && singleWellDailyReportHelper.hot != '') {
                    singleWellDailyReportHelper.hot.destroy();
                }
                singleWellDailyReportHelper = null;
            }

            if (result.success) {
                singleWellDailyReportHelper = SingleWellDailyReportHelper.createNew(
                    'swHourlyDataDiv', 'SingleWellDailyReportContainer',
                    result.template, result.data, result.columns, result.totalCount
                );
                singleWellDailyReportHelper.createTable();
            } else {
                document.getElementById('swHourlyDataDiv').innerHTML = '';
            }

            var totalCnt = document.getElementById('swHourlyTotalCount');
            if (totalCnt) {
                var cnt = (result.data && result.data.length) ? result.data.length : 0;
                totalCnt.textContent = _loginUserLanguageResource.totalCount + ':' + cnt;
            }
        },
        error: function () {
            mini.unmask('swHourlyDataPanel');
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 单井日报 - 日报表格
// ================================================================
function CreateSingleWellRangeReportTable() {
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

    mini.mask({ el: 'swRangeDataPanel', cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getSingleWellRangeReportData',
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
            mini.unmask('swRangeDataPanel');
            fillSwDatesFromResult(result);

            if (singleWellRangeReportHelper != null) {
                if (singleWellRangeReportHelper.hot != undefined
                    && singleWellRangeReportHelper.hot != '') {
                    singleWellRangeReportHelper.hot.destroy();
                }
                singleWellRangeReportHelper = null;
            }

            if (result.success) {
                singleWellRangeReportHelper = SingleWellRangeReportHelper.createNew(
                    'swRangeDataDiv', 'SingleWellRangeReportContainer',
                    result.template, result.data, result.columns
                );
                singleWellRangeReportHelper.createTable();
            } else {
                document.getElementById('swRangeDataDiv').innerHTML = '';
            }

            var totalCnt = document.getElementById('swRangeTotalCount');
            if (totalCnt) {
                var cnt = (result.data && result.data.length) ? result.data.length : 0;
                totalCnt.textContent = _loginUserLanguageResource.totalCount + ':' + cnt;
            }
        },
        error: function () {
            mini.unmask('swRangeDataPanel');
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 单井日报 - 班报表曲线
// ================================================================
function CreateSingleWellDailyReportCurve() {
    if (_reportSelectedDeviceId <= 0) return;

    var orgId         = getLeftOrgId();
    var deviceId      = _reportSelectedDeviceId;
    var deviceName    = _reportCurrentDeviceName;
    var calculateType = _reportCurrentCalculateType;
    var deviceType    = _reportCurrentDeviceType;

    var startDp    = mini.get('swStartDate');
    var endDp      = mini.get('swEndDate');
    var reportDp   = mini.get('swHourlyReportDate');
    var intervalCb = mini.get('swHourlyInterval');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';
    var interval   = intervalCb ? intervalCb.getValue() : 2;

    mini.mask({ el: 'swHourlyCurvePanel', cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getSingleWellDailyReportCurveData',
        type: 'POST',
        data: {
            orgId: orgId,
            deviceId: deviceId,
            deviceName: deviceName,
            calculateType: calculateType,
            startDate: startDate,
            endDate: endDate,
            reportDate: reportDate,
            reportType: 2,
            interval: interval,
            deviceType: deviceType
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask('swHourlyCurvePanel');
            fillSwDatesFromResult(result);
            // 班报表 → DailyReport
            buildAndRenderCurve(result, 'swHourlyCurveDiv', '%H:%M',
                                'hourlyReportCurve', 'DailyReport');
        },
        error: function () {
            mini.unmask('swHourlyCurvePanel');
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 单井日报 - 日报曲线
// ================================================================
function CreateSingleWellRangeReportCurve() {
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

    mini.mask({ el: 'swRangeCurvePanel', cls: 'mini-mask-loading',
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
            deviceType: deviceType
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask('swRangeCurvePanel');
            fillSwDatesFromResult(result);
            // 单井日报 → Report
            buildAndRenderCurve(result, 'swRangeCurveDiv', '%m-%d',
                                'dailyReportCurve', 'Report');
        },
        error: function () {
            mini.unmask('swRangeCurvePanel');
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 通用曲线组装 + 渲染
//   graphicSetKey：'DailyReport' / 'Report' / 'History'
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

    var firstLower = (typeof loginUserLanguageResourceFirstLower !== 'undefined'
                     && loginUserLanguageResourceFirstLower[titleKey])
                     ? loginUserLanguageResourceFirstLower[titleKey] : '';

    var title = (result.deviceName || '')
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
    
    var enableChartConfig = (graphicSetKey === 'DailyReport' || graphicSetKey === 'Report');
    initSingleWellReportCurveChartFn(series, tickInterval, divId, title,
                                     '', '', yAxis, color_all, true, timeFormat,
                                     enableChartConfig);
}

function initSingleWellReportCurveChartFn(series, tickInterval, divId, title,
                                          subtitle, xtitle, yAxis, color, legend, timeFormat,
                                          enableChartConfig) {
    if ($('#' + divId) == undefined || $('#' + divId)[0] == undefined) return;
    
    // ★ 构造导出菜单项
    var menuItems = ['viewFullscreen', 'printChart',
                     'downloadPNG', 'downloadJPEG', 'downloadSVG',
                     'downloadXLS'];
    var menuItemDefinitions = {};
 // ★ 只有启用图形设置时才追加 "图形设置" 菜单
    if (enableChartConfig) {
        menuItems.push('chartConfig');
        menuItemDefinitions.chartConfig = {
            text: _loginUserLanguageResource.diagramSet,
            onclick: function () {
                openSingleWellCurveSetWindow();
            }
        };
    }

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
            menuItemDefinitions: menuItemDefinitions,
            buttons: {
                contextButton: {
                    menuItems: menuItems
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

//================================================================
//单井日报 - 打开曲线设置窗口（图形设置）
//================================================================
function openSingleWellCurveSetWindow() {
 if (_reportSelectedDeviceId <= 0) {
     mini.alert(_loginUserLanguageResource.checkOne,
                _loginUserLanguageResource.tip);
     return;
 }

 // 根据当前激活的内层 tab 决定 reportType：
 //   班报表 hourly → 2（DailyReport）
 //   日报表 range   → 0（Report）
 var tabs = mini.get('swInnerTabs');
 var activeTab = tabs ? tabs.getActiveTab() : null;
 var reportType = (activeTab && activeTab.name === 'hourly') ? 2 : 0;

 var params = {
     deviceId:   _reportSelectedDeviceId,
     deviceName: _reportCurrentDeviceName,
     deviceType: _reportCurrentDeviceType,
     reportType: reportType
 };

 mini.open({
     title: _loginUserLanguageResource.reportDiagramSet,
     url: context + '/miniui-app/modules/report/productionReportCurveSet.jsp',
     width: '50%',
     height: '60%',
     modal: true,
     allowResize: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         if (!iframe || !iframe.contentWindow) return;

         iframe.contentWindow.setData(params);

         // ★ 将父页面的刷新曲线 / 提示方法挂到子窗口
         iframe.contentWindow._parentReloadCurve = function () {
             if (reportType === 2) {
                 CreateSingleWellDailyReportCurve();
             } else {
                 CreateSingleWellRangeReportCurve();
             }
         };
         iframe.contentWindow._parentShowAlert = function (message, title) {
             mini.alert(message, title);
         };
     }
 });
}

// ================================================================
// 单井日报 - 清空
// ================================================================
function clearSingleWellReportContent() {
    if (singleWellDailyReportHelper != null) {
        if (singleWellDailyReportHelper.hot != undefined
            && singleWellDailyReportHelper.hot != '') {
            singleWellDailyReportHelper.hot.destroy();
        }
        singleWellDailyReportHelper = null;
    }
    if (singleWellRangeReportHelper != null) {
        if (singleWellRangeReportHelper.hot != undefined
            && singleWellRangeReportHelper.hot != '') {
            singleWellRangeReportHelper.hot.destroy();
        }
        singleWellRangeReportHelper = null;
    }

    var hourlyData = document.getElementById('swHourlyDataDiv');
    if (hourlyData) hourlyData.innerHTML = '';
    var hourlyCurve = document.getElementById('swHourlyCurveDiv');
    if (hourlyCurve) hourlyCurve.innerHTML = '';
    var rangeData = document.getElementById('swRangeDataDiv');
    if (rangeData) rangeData.innerHTML = '';
    var rangeCurve = document.getElementById('swRangeCurveDiv');
    if (rangeCurve) rangeCurve.innerHTML = '';

    var hourlyCnt = document.getElementById('swHourlyTotalCount');
    if (hourlyCnt) hourlyCnt.textContent = '';
    var rangeCnt = document.getElementById('swRangeTotalCount');
    if (rangeCnt) rangeCnt.textContent = '';
}

// ================================================================
// 单井日报 - 工具条事件
// ================================================================
function onDailyReportInnerTabChanged(e) {
    if (isInitializing) return;

    var tab = e && e.tab ? e.tab : null;
    if (!tab) return;

    if (tab.name === 'singleWell') {
        loadSingleWellDeviceGrid();
    } else if (tab.name === 'area') {
        loadProductionDailyReportInstanceGrid();
    }
}

function onSwRefresh() {
    var combo = mini.get('swDeviceCombo');
    if (combo) combo.setValue('', false);

    mini.get('swStartDate').setValue('', false);
    mini.get('swEndDate').setValue(new Date(), false);
    mini.get('swHourlyReportDate').setValue('', false);

    _reportSelectedDeviceId     = 0;
    _reportSelectedRowIndex     = 0;
    _reportCurrentDeviceName    = '';
    _reportCurrentCalculateType = 0;

    updateSwHourlyNavBtnStatus();
    clearSingleWellReportContent();
    loadSingleWellDeviceGrid();
}

function onSwSearch() {
    var tabs = mini.get('swInnerTabs');
    if (tabs) {
        var activeTab = tabs.getActiveTab();
        if (activeTab && activeTab.name === 'hourly') {
            var reportDp = mini.get('swHourlyReportDate');
            if (reportDp) reportDp.setValue('', false);
        }
    }
    CreateSingleWellReportTable();
    CreateSingleWellReportCurve();
}

// ================================================================
// 单井日报 - 导出
// ================================================================
function onSwHourlyExport() {
    if (_reportModuleRight.viewFlag != 1) return;
    if (_reportSelectedDeviceId <= 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }
    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'ExportSingleWellDailyReportData' + timestamp;

    var startDp    = mini.get('swStartDate');
    var endDp      = mini.get('swEndDate');
    var reportDp   = mini.get('swHourlyReportDate');
    var intervalCb = mini.get('swHourlyInterval');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';
    var interval   = intervalCb ? intervalCb.getValue() : 2;

    var url = context + '/reportDataMamagerController/exportSingleWellDailyReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=2'
        + '&deviceName=' + URLencode(URLencode(_reportCurrentDeviceName))
        + '&deviceId=' + _reportSelectedDeviceId
        + '&calculateType=' + _reportCurrentCalculateType
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&reportDate=' + reportDate
        + '&interval=' + interval
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'swHourlyDataPanel', R.loadingData);
    document.location.href = url;
}

function onSwRangeExport() {
    if (_reportModuleRight.viewFlag != 1) return;
    if (_reportSelectedDeviceId <= 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }
    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'ExportSingleWellRangeReportData' + timestamp;

    var startDp = mini.get('swStartDate');
    var endDp   = mini.get('swEndDate');
    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    var url = context + '/reportDataMamagerController/exportSingleWellRangeReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=0'
        + '&calculateType=' + _reportCurrentCalculateType
        + '&deviceName=' + URLencode(URLencode(_reportCurrentDeviceName))
        + '&deviceId=' + _reportSelectedDeviceId
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'swRangeDataPanel', R.loadingData);
    document.location.href = url;
}

function onSwBatchExport() {
    if (_reportModuleRight.viewFlag != 1) return;

    var tabs = mini.get('swInnerTabs');
    if (!tabs) return;
    var activeTab = tabs.getActiveTab();
    if (!activeTab) return;

    if (activeTab.name === 'hourly') {
        batchExportSingleWellDailyReportData();
    } else if (activeTab.name === 'range') {
        batchExportSingleWellRangeReportData();
    }
}

function batchExportSingleWellDailyReportData() {
    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'batchExportSingleWellDailyReportData_' + timestamp;

    var startDp    = mini.get('swStartDate');
    var endDp      = mini.get('swEndDate');
    var reportDp   = mini.get('swHourlyReportDate');
    var intervalCb = mini.get('swHourlyInterval');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';
    var interval   = intervalCb ? intervalCb.getValue() : 2;

    var combo = mini.get('swDeviceCombo');
    var deviceName = combo ? (combo.getValue() || '') : '';

    var deviceTypeName = _reportCurrentLevel2 ? (_reportCurrentLevel2.text || '') : '';

    var url = context + '/reportDataMamagerController/batchExportSingleWellDailyReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&deviceTypeName=' + URLencode(URLencode(deviceTypeName))
        + '&reportType=2'
        + '&deviceName=' + URLencode(URLencode(deviceName))
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&reportDate=' + reportDate
        + '&interval=' + interval
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'swHourlyDataPanel', R.loadingData);
    document.location.href = url;
}

function batchExportSingleWellRangeReportData() {
    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'batchExportSingleWellRangeReportData' + timestamp;

    var startDp  = mini.get('swStartDate');
    var endDp    = mini.get('swEndDate');
    var reportDp = mini.get('swHourlyReportDate');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var combo = mini.get('swDeviceCombo');
    var deviceName = combo ? (combo.getValue() || '') : '';

    var deviceTypeName = _reportCurrentLevel2 ? (_reportCurrentLevel2.text || '') : '';

    var url = context + '/reportDataMamagerController/batchExportSingleWellRangeReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=0'
        + '&deviceName=' + URLencode(URLencode(deviceName))
        + '&deviceTypeName=' + URLencode(URLencode(deviceTypeName))
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&reportDate=' + reportDate
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'swRangeDataPanel', R.loadingData);
    document.location.href = url;
}

// ================================================================
// 单井日报 - 保存
// ================================================================
function onSwHourlySave() {
    if (_reportModuleRight.editFlag != 1) return;
    if (singleWellDailyReportHelper != null) {
        singleWellDailyReportHelper.saveData();
    }
}

function onSwRangeSave() {
    if (_reportModuleRight.editFlag != 1) return;
    if (singleWellRangeReportHelper != null) {
        singleWellRangeReportHelper.saveData();
    }
}

// ================================================================
// 区域日报 - 实例列表（对照 ExtJS ProductionDailyReportInstanceListStore）
// ================================================================
function loadProductionDailyReportInstanceGrid() {
    var grid = mini.get('pdInstanceGrid');
    if (!grid) return;

    if (!grid.getUrl()) {
        grid.setUrl(context + '/reportDataMamagerController/getReportInstanceList');
    }
    grid.load();
}

function onPdInstanceGridBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize
        || (typeof defaultPageSize !== 'undefined' ? defaultPageSize : 10000);

    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    // 对照 ExtJS：beforeload 里 Panel.show()
    var splitter = mini.get('pdMainSplitter');
    if (splitter) splitter.showPane(1);

    params.orgId      = getLeftOrgId();
    params.reportType = 1;
    params.deviceType = _reportCurrentDeviceType;

    e.params = params;
}

function onPdInstanceGridLoad(e) {
    var grid = e.sender;
    var result = e.result || {};

    if (!grid._columnsCreated) {
        buildPdInstanceGridColumns(grid);
        grid._columnsCreated = true;
    }

    var data = result.totalRoot || [];
    var totalCount = result.totalCount || 0;

    var splitter = mini.get('pdMainSplitter');

    if (totalCount > 0) {
        // 只有 1 条 → 隐藏左侧；否则显示
        if (splitter) {
            if (totalCount == 1) {
                splitter.hidePane(1);
            } else {
                splitter.showPane(1);
            }
        }

        // 选中第 0 行（触发 onselectionchanged → CreateTable/Curve）
        
        var selected = grid.getSelecteds() || [];
        if (selected.length === 0) {
        	grid.deselectAll();
            grid.select(0);
        }
    } else {
        _pdSelectedInstanceCode = '';
        _pdSelectedUnitId       = 0;
        _pdSelectedInstanceName = '';
        _pdSelectedRowId        = 0;

        if (splitter) splitter.hidePane(1);

        // 对照 ExtJS：无数据时也手动调 Create
        CreateProductionDailyReportTable();
        CreateProductionDailyReportCurve();
    }
}

function buildPdInstanceGridColumns(grid) {
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
        field: 'instanceName',
        header: R.reportInstanceList,
        headerAlign: 'center',
        align: 'center',
        width: '100%'
    });
    grid.setColumns(columns);
}

function onPdInstanceGridSelectionChanged(e) {
    if (isInitializing) return;

    var grid = e.sender;
    var row = grid.getSelected();

    if (!row) {
        _pdSelectedInstanceCode = '';
        _pdSelectedUnitId       = 0;
        _pdSelectedInstanceName = '';
        _pdSelectedRowId        = 0;
        clearProductionDailyReportContent();
        return;
    }

    _pdSelectedInstanceCode = row.instanceCode || '';
    _pdSelectedUnitId       = row.unitId || 0;
    _pdSelectedInstanceName = row.instanceName || '';
    _pdSelectedRowId        = row.id || 0;

    CreateProductionDailyReportTable();
    CreateProductionDailyReportCurve();
}

// ================================================================
// 区域日报 - 表格
// ================================================================
function CreateProductionDailyReportTable() {
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
        url: context + '/reportDataMamagerController/getProductionDailyReportData',
        type: 'POST',
        data: {
            orgId: orgId,
            instanceCode: _pdSelectedInstanceCode,
            unitId: _pdSelectedUnitId,
            wellName: '',
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

            if (productionDailyReportHelper != null) {
                if (productionDailyReportHelper.hot != undefined
                    && productionDailyReportHelper.hot != '') {
                    productionDailyReportHelper.hot.destroy();
                }
                productionDailyReportHelper = null;
            }

            if (result.success) {
                productionDailyReportHelper = ProductionDailyReportHelper.createNew(
                    'pdDataDiv', 'ProductionDailyReportContainer',
                    result.template, result.data, result.statData, result.columns
                );
                productionDailyReportHelper.createTable();
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
// 区域日报 - 曲线（用 History）
// ================================================================
function CreateProductionDailyReportCurve() {
    var orgId = getLeftOrgId();
    var startDp = mini.get('pdStartDate');
    var endDp   = mini.get('pdEndDate');

    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    var selectedOrgName = getSelectedOrgName();

    mini.mask({ el: 'pdCurvePanel', cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getProductionDailyReportCurveData',
        type: 'POST',
        data: {
            orgId: orgId,
            instanceCode: _pdSelectedInstanceCode,
            unitId: _pdSelectedUnitId,
            wellName: '',
            selectedOrgName: selectedOrgName,
            startDate: startDate,
            endDate: endDate,
            reportType: 1,
            deviceType: _reportCurrentDeviceType
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask('pdCurvePanel');
            fillPdDatesFromResult(result);
            // 区域日报 → History
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
// 区域日报 - 日期回填
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

// ================================================================
// 区域日报 - 清空
// ================================================================
function clearProductionDailyReportContent() {
    if (productionDailyReportHelper != null) {
        if (productionDailyReportHelper.hot != undefined
            && productionDailyReportHelper.hot != '') {
            productionDailyReportHelper.hot.destroy();
        }
        productionDailyReportHelper = null;
    }

    var pdData = document.getElementById('pdDataDiv');
    if (pdData) pdData.innerHTML = '';
    var pdCurve = document.getElementById('pdCurveDiv');
    if (pdCurve) pdCurve.innerHTML = '';

    var cnt = document.getElementById('pdTotalCount');
    if (cnt) cnt.textContent = '';
}

// ================================================================
// 区域日报 - 前进/后退状态
// ================================================================
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

// ================================================================
// 区域日报 - 日期事件
// ================================================================
function onPdRangeDateChanged(e) {
    if (isInitializing) return;

    var reportDp = mini.get('pdReportDate');
    if (reportDp) reportDp.setValue('', false);

    CreateProductionDailyReportTable();
    CreateProductionDailyReportCurve();
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

    // 让 setValue 触发 onvaluechanged → updatePdNavBtnStatus
    reportDp.setValue(d);

    // 对照 ExtJS：只刷新表格
    CreateProductionDailyReportTable();
}

// ================================================================
// 区域日报 - 刷新/搜索
// ================================================================
function onPdRefresh() {
    mini.get('pdStartDate').setValue('', false);
    mini.get('pdEndDate').setValue(new Date(), false);
    mini.get('pdReportDate').setValue('', false);

    _pdSelectedInstanceCode = '';
    _pdSelectedUnitId       = 0;
    _pdSelectedInstanceName = '';
    _pdSelectedRowId        = 0;

    updatePdNavBtnStatus();
    clearProductionDailyReportContent();

    loadProductionDailyReportInstanceGrid();
}

function onPdSearch() {
    var reportDp = mini.get('pdReportDate');
    if (reportDp) reportDp.setValue('', false);

    CreateProductionDailyReportTable();
    CreateProductionDailyReportCurve();
}

// ================================================================
// 区域日报 - 导出
// ================================================================
function onPdExport() {
    if (_reportModuleRight.viewFlag != 1) return;

    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'exportProductionDailyReportData' + timestamp;

    var startDp  = mini.get('pdStartDate');
    var endDp    = mini.get('pdEndDate');
    var reportDp = mini.get('pdReportDate');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var selectedOrgName = getSelectedOrgName();

    var url = context + '/reportDataMamagerController/exportProductionDailyReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=1'
        + '&wellName=' + URLencode(URLencode(''))
        + '&selectedOrgName=' + URLencode(URLencode(selectedOrgName))
        + '&instanceCode=' + _pdSelectedInstanceCode
        + '&unitId=' + _pdSelectedUnitId
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
    var key = 'batchExportProductionDailyReportData' + timestamp;

    var startDp  = mini.get('pdStartDate');
    var endDp    = mini.get('pdEndDate');
    var reportDp = mini.get('pdReportDate');

    var startDate  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endDate    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var selectedOrgName = getSelectedOrgName();

    var url = context + '/reportDataMamagerController/batchExportProductionDailyReportData'
        + '?deviceType=' + _reportCurrentDeviceType
        + '&reportType=1'
        + '&wellName=' + URLencode(URLencode(''))
        + '&selectedOrgName=' + URLencode(URLencode(selectedOrgName))
        + '&instanceCode=' + _pdSelectedInstanceCode
        + '&unitId=' + _pdSelectedUnitId
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&reportDate=' + reportDate
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, 'pdDataPanel', R.loadingData);
    document.location.href = url;
}

// ================================================================
// 区域日报 - 保存
// ================================================================
function onPdSave() {
    if (_reportModuleRight.editFlag != 1) return;
    if (productionDailyReportHelper != null) {
        productionDailyReportHelper.saveData();
    }
}

// ================================================================
// Handsontable Helper - 班报表（SingleWellDailyReportHelper）
// ================================================================
var SingleWellDailyReportHelper = {
    createNew: function (divId, containerid, templateData, contentData, columns, totalCount) {
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
        helper.totalCount   = totalCount;

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
                    var valueLength = 12;
                    if ((!editable) && value.length > valueLength) {
                        value = value.substring(0, valueLength - 1) + "...";
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
                    columns: [helper.columnCount - 1],
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
                    if (row >= helper.templateData.header.length + helper.totalCount) {
                        cellProperties.editor = false;
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
                var deviceType = _reportCurrentDeviceType;

                if (_reportSelectedDeviceId > 0) {
                    deviceName = _reportCurrentDeviceName;
                    deviceId = _reportSelectedDeviceId;
                }

                mini.mask({ el: 'swHourlyDataPanel', cls: 'mini-mask-loading',
                            html: _loginUserLanguageResource.submittingData });

                $.ajax({
                    method: 'POST',
                    url: context + '/reportDataMamagerController/saveSingleWellDailyDailyReportData',
                    data: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        data: JSON.stringify(helper.editData),
                        deviceType: deviceType
                    },
                    dataType: 'json',
                    success: function (response) {
                        mini.unmask('swHourlyDataPanel');
                        if (response && response.success) {
                            mini.alert(_loginUserLanguageResource.tip,
                                       _loginUserLanguageResource.savedSuccessfully);
                            helper.clearContainer();
                            CreateSingleWellReportTable();
                            CreateSingleWellReportCurve();
                        } else {
                            helper.clearContainer();
                            mini.alert(_loginUserLanguageResource.tip,
                                       '<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>');
                        }
                    },
                    error: function () {
                        mini.unmask('swHourlyDataPanel');
                        mini.alert(_loginUserLanguageResource.tip,
                                   _loginUserLanguageResource.requestFailed);
                    }
                });
            } else {
                mini.alert(_loginUserLanguageResource.tip,
                           _loginUserLanguageResource.noDataChange);
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
// Handsontable Helper - 单井日报（SingleWellRangeReportHelper）
// ================================================================
var SingleWellRangeReportHelper = {
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
                    if ((!editable) && value.length > 12) {
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
                    columns: [helper.columnCount - 1],
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
                var deviceType = _reportCurrentDeviceType;

                if (_reportSelectedDeviceId > 0) {
                    deviceName = _reportCurrentDeviceName;
                    deviceId = _reportSelectedDeviceId;
                }

                mini.mask({ el: 'swRangeDataPanel', cls: 'mini-mask-loading',
                            html: _loginUserLanguageResource.submittingData });

                $.ajax({
                    method: 'POST',
                    url: context + '/reportDataMamagerController/saveSingleWellRangeDailyReportData',
                    data: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        data: JSON.stringify(helper.editData),
                        deviceType: deviceType
                    },
                    dataType: 'json',
                    success: function (response) {
                        mini.unmask('swRangeDataPanel');
                        if (response && response.success) {
                            mini.alert(_loginUserLanguageResource.tip,
                                       _loginUserLanguageResource.savedSuccessfully);
                            helper.clearContainer();
                            CreateSingleWellReportTable();
                            CreateSingleWellReportCurve();
                        } else {
                            helper.clearContainer();
                            mini.alert(_loginUserLanguageResource.tip,
                                       '<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>');
                        }
                    },
                    error: function () {
                        mini.unmask('swRangeDataPanel');
                        mini.alert(_loginUserLanguageResource.tip,
                                   _loginUserLanguageResource.requestFailed);
                    }
                });
            } else {
                mini.alert(_loginUserLanguageResource.tip,
                           _loginUserLanguageResource.noDataChange);
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
// Handsontable Helper - 区域日报（ProductionDailyReportHelper）
// ================================================================
var ProductionDailyReportHelper = {
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
            // 区域日报特有：统计行
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
                    if ((!editable) && value.length > 12) {
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
                    columns: [helper.columnCount - 1],
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

                var wellName = '';
                var wellId = 0;
                if (_pdSelectedInstanceName) wellName = _pdSelectedInstanceName;
                if (_pdSelectedRowId > 0) wellId = _pdSelectedRowId;

                mini.mask({ el: 'pdDataPanel', cls: 'mini-mask-loading',
                            html: _loginUserLanguageResource.submittingData });

                $.ajax({
                    method: 'POST',
                    url: context + '/reportDataMamagerController/saveSingleWellRangeDailyReportData',
                    data: {
                        wellId: wellId,
                        wellName: wellName,
                        data: JSON.stringify(helper.editData),
                        deviceType: 0
                    },
                    dataType: 'json',
                    success: function (response) {
                        mini.unmask('pdDataPanel');
                        if (response && response.success) {
                            mini.alert(_loginUserLanguageResource.tip,
                                       _loginUserLanguageResource.savedSuccessfully);
                            helper.clearContainer();
                            CreateProductionDailyReportTable();
                            CreateProductionDailyReportCurve();
                        } else {
                            helper.clearContainer();
                            mini.alert(_loginUserLanguageResource.tip,
                                       '<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>');
                        }
                    },
                    error: function () {
                        mini.unmask('pdDataPanel');
                        mini.alert(_loginUserLanguageResource.tip,
                                   _loginUserLanguageResource.requestFailed);
                    }
                });
            } else {
                mini.alert(_loginUserLanguageResource.tip,
                           _loginUserLanguageResource.noDataChange);
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