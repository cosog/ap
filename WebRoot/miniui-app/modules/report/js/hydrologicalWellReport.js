// ================================================================
// 水文井报表 - hydrologicalWellReport.js
// 严格对照 ExtJS HydrologicalWellReportView
// ================================================================

// ---------- 模块权限 ----------
var _hywrModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };

// ---------- 设备列表上下文 ----------
var _hywrSelectedDeviceId     = 0;
var _hywrCurrentDeviceName    = '';
var _hywrCurrentCalculateType = 0;
var _hywrDeviceTotalCount     = 0;
var _hywrGridLoading          = false;

// ---------- Handsontable 对象（单一变量，切换 tab 时销毁重建） ----------
var hydrologicalWellReportHelper = null;

// ---------- 当前激活的时间维度（1~5） ----------
var _hywrCurrentTimeType = 1;

var isInitializing = true;

// ================================================================
// 页面初始化
// ================================================================
function initHydrologicalWellReportPage() {
    initHywrModuleRight();
    initHywrI18n();
    initHywrDefaultDates();
    initHywrPlaceholderGrids();

    // 加载设备列表
    loadHywrDeviceGrid();
    
    initHywrMessageListener();

    isInitializing = false;
}

function initHywrMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        switch (message.action) {
            case 'refresh':
            	loadHywrDeviceGrid();
                break;
        }
    });
}

// ================================================================
// 权限
// ================================================================
function initHywrModuleRight() {
    _hywrModuleRight = getRoleModuleRight(
        context + '/roleManagerController/getRoleModuleRight',
        'DailyReport'
    ) || { viewFlag: 0, editFlag: 0, controlFlag: 0 };

    _hywrModuleRight.viewFlag    = parseInt(_hywrModuleRight.viewFlag)    || 0;
    _hywrModuleRight.editFlag    = parseInt(_hywrModuleRight.editFlag)    || 0;
    _hywrModuleRight.controlFlag = parseInt(_hywrModuleRight.controlFlag) || 0;

    updateHywrBtnStatus();
}

function updateHywrBtnStatus() {
    var canEdit = (_hywrModuleRight.editFlag == 1);
    var btnIds = [
        'hywrSaveBtn1', 'hywrSaveBtn2', 'hywrSaveBtn3', 'hywrSaveBtn4', 'hywrSaveBtn5',
        'hywrExportBtn1', 'hywrExportBtn2', 'hywrExportBtn3', 'hywrExportBtn4', 'hywrExportBtn5',
        'hywrBatchExportBtn'
    ];
    for (var i = 0; i < btnIds.length; i++) {
        var btn = mini.get(btnIds[i]);
        if (btn) btn.setEnabled(canEdit);
    }
}

// ================================================================
// 国际化
// ================================================================
function initHywrI18n() {
    var R = _loginUserLanguageResource;

    // ---------- 5 个 Tab 标题 ----------
    setTabTitle('hywrTabs', 0, R.fiveMinutes);
    setTabTitle('hywrTabs', 1, R.oneHour);
    setTabTitle('hywrTabs', 2, R.sixHours);
    setTabTitle('hywrTabs', 3, R.twelveHours);
    setTabTitle('hywrTabs', 4, R.twentyFourHours);

    // ---------- 主工具栏 ----------
    setBtnText('hywrRefreshBtn', R.refresh);
    setBtnText('hywrSearchBtn', R.search);
    setBtnText('hywrBatchExportBtn', R.bulkExportData);
    setHtml('hywrLblDevice', R.deviceName + '：');
    setHtml('hywrLblDate', R.date + '：');
    setHtml('hywrLblTimeTo', R.timeTo + '：');

    // ---------- 设备列表面板标题 ----------
    setPanelTitle('hywrDeviceListPanel', R.deviceList);

    // ---------- 5 个时间维度的面板标题、按钮 ----------
    for (var i = 1; i <= 5; i++) {
        setPanelTitle('hywrDataPanel' + i, R.reportData);
        setPanelTitle('hywrCurvePanel' + i, R.reportCurve);

        setBtnText('hywrExportBtn' + i, R.exportData);
        setBtnText('hywrSaveBtn' + i, R.save);

        if (i === 1 || i === 2) {
            setBtnText('hywrFwdBtn' + i, R.forward);
            setBtnText('hywrBackBtn' + i, R.backward);
        }
    }

    var deviceCombo = mini.get('hywrDeviceCombo');
    if (deviceCombo) deviceCombo.setEmptyText('--' + R.all + '--');

    var grid = mini.get('hywrDeviceGrid');
    if (grid) grid.setEmptyText(R.emptyMsg);
}

// ================================================================
// 默认日期
// ================================================================
function initHywrDefaultDates() {
    var today = new Date();

    var startDp = mini.get('hywrStartDate');
    if (startDp) startDp.setValue('', false);
    var endDp = mini.get('hywrEndDate');
    if (endDp) endDp.setValue(today, false);

    var r1 = mini.get('hywrReportDate1');
    if (r1) r1.setValue('', false);
    var r2 = mini.get('hywrReportDate2');
    if (r2) r2.setValue('', false);

    updateHywrNavBtnStatus(1);
    updateHywrNavBtnStatus(2);
}

// ================================================================
// 占位：设备列表列定义
// ================================================================
function initHywrPlaceholderGrids() {
    var R = _loginUserLanguageResource;

    var grid = mini.get('hywrDeviceGrid');
    if (grid) {
        grid.setColumns([
            { type: 'indexcolumn', width: 50, header: R.idx,
              headerAlign: 'center', align: 'center' },
            { field: 'deviceName', header: R.deviceName,
              headerAlign: 'center', align: 'center', width: '100%' }
        ]);
        grid.setData([]);
    }
}

// ================================================================
// 小工具
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

// 根据 timeType 获取当前容器/面板 id
function getHywrDivId(timeType) {
    return 'hywrDataDiv' + timeType;
}
function getHywrCurveDivId(timeType) {
    return 'hywrCurveDiv' + timeType;
}
function getHywrCurvePanelId(timeType) {
    return 'hywrCurvePanel' + timeType;
}
function getHywrDataPanelId(timeType) {
    return 'hywrDataPanel' + timeType;
}
function getHywrTotalCountId(timeType) {
    return 'hywrTotalCount' + timeType;
}
function getHywrReportDateId(timeType) {
    return 'hywrReportDate' + timeType;
}

// ================================================================
// 设备下拉框
// ================================================================
function onHywrDeviceComboBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize
        || (typeof defaultWellComboxSize !== 'undefined' ? defaultWellComboxSize : 50);

    params.start = pageIndex * pageSize;
    params.limit = pageSize;
    params.orgId = getLeftOrgId();

    var combo = mini.get('hywrDeviceCombo');
    params.deviceName = combo ? (combo.getValue() || '') : '';

    e.params = params;
}

function onHywrDeviceComboShowPopup(e) {
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

function onHywrDeviceComboChange(e) {
    _hywrSelectedDeviceId     = 0;
    _hywrCurrentDeviceName    = '';
    _hywrCurrentCalculateType = 0;

    clearHywrTableAndCurve();
    loadHywrDeviceGrid();
}

// ================================================================
// 设备列表
// ================================================================
function loadHywrDeviceGrid() {
    var grid = mini.get('hywrDeviceGrid');
    if (!grid) return;

    if (!grid.getUrl()) {
        grid.setUrl(context + '/reportDataMamagerController/getHydrologicalWellDeviceList');
    }
    grid.load();
}

function onHywrDeviceGridBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize || 10000;

    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var combo = mini.get('hywrDeviceCombo');
    params.deviceName = combo ? (combo.getValue() || '') : '';
    params.orgId      = getLeftOrgId();

    e.params = params;
}

function onHywrDeviceGridLoad(e) {
    var grid = e.sender;
    var result = e.result || {};

    if (!grid._columnsCreated) {
        buildHywrDeviceGridColumns(grid);
        grid._columnsCreated = true;
    }

    var data = result.totalRoot || [];
    _hywrDeviceTotalCount = result.totalCount || 0;

    if (data.length > 0) {
        var selected = grid.getSelecteds() || [];
        if (selected.length === 0) {
            var selectRow = 0;
            if (_hywrSelectedDeviceId > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].id == _hywrSelectedDeviceId) {
                        selectRow = i;
                        break;
                    }
                }
            }
            grid.select(data[selectRow]);
        }
    } else {
        _hywrSelectedDeviceId     = 0;
        _hywrCurrentDeviceName    = '';
        _hywrCurrentCalculateType = 0;
        clearHywrTableAndCurve();
    }
}

function buildHywrDeviceGridColumns(grid) {
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

function onHywrDeviceGridSelectionChanged(e) {
    if (_hywrGridLoading) return;
    if (isInitializing) return;

    var grid = e.sender;
    var row = grid.getSelected();

    if (!row) {
        _hywrSelectedDeviceId     = 0;
        _hywrCurrentDeviceName    = '';
        _hywrCurrentCalculateType = 0;
        clearHywrTableAndCurve();
        return;
    }

    _hywrSelectedDeviceId     = row.id || 0;
    _hywrCurrentDeviceName    = row.deviceName || '';
    _hywrCurrentCalculateType = row.calculateType || 0;

    CreateHydrologicalWellReportTable();
    CreateHydrologicalWellReportCurve();
}

// ================================================================
// Tab 切换
// ================================================================
function onHywrTabChanged(e) {
    if (isInitializing) return;
    var tab = e && e.tab ? e.tab : null;
    if (!tab) return;

    if (tab.name === 't1')      _hywrCurrentTimeType = 1;
    else if (tab.name === 't2') _hywrCurrentTimeType = 2;
    else if (tab.name === 't3') _hywrCurrentTimeType = 3;
    else if (tab.name === 't4') _hywrCurrentTimeType = 4;
    else if (tab.name === 't5') _hywrCurrentTimeType = 5;

    CreateHydrologicalWellReportTable();
    CreateHydrologicalWellReportCurve();
}

// ================================================================
// 日期事件
// ================================================================
function onHywrRangeDateChanged(e) {
    if (isInitializing) return;

    var r1 = mini.get('hywrReportDate1');
    if (r1) r1.setValue('', false);
    var r2 = mini.get('hywrReportDate2');
    if (r2) r2.setValue('', false);

    CreateHydrologicalWellReportTable();
    CreateHydrologicalWellReportCurve();
}

function onHywrReportDateChanged1(e) {
    updateHywrNavBtnStatus(1);
}
function onHywrReportDateChanged2(e) {
    updateHywrNavBtnStatus(2);
}

function updateHywrNavBtnStatus(timeType) {
    var startDp  = mini.get('hywrStartDate');
    var endDp    = mini.get('hywrEndDate');
    var reportDp = mini.get(getHywrReportDateId(timeType));

    var startStr  = startDp  ? (startDp.getFormValue('yyyy-MM-dd')  || '') : '';
    var endStr    = endDp    ? (endDp.getFormValue('yyyy-MM-dd')    || '') : '';
    var reportStr = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';

    var forwardBtn = mini.get('hywrFwdBtn' + timeType);
    var backBtn    = mini.get('hywrBackBtn' + timeType);

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

function onHywrForward(timeType) {
    shiftHywrReportDate(timeType, -1);
}

function onHywrBack(timeType) {
    shiftHywrReportDate(timeType, 1);
}

function shiftHywrReportDate(timeType, day) {
    var reportDp = mini.get(getHywrReportDateId(timeType));
    if (!reportDp) return;
    var str = reportDp.getFormValue('yyyy-MM-dd');
    if (!str) return;

    var d = new Date(Date.parse(str.replace(/-/g, '/')));
    d.setTime(d.getTime() + day * 24 * 3600 * 1000);

    // 让 setValue 触发 onvaluechanged 更新按钮状态
    reportDp.setValue(d);

    CreateHydrologicalWellReportTable();
    CreateHydrologicalWellReportCurve();
}

// ================================================================
// 日期回填（只当为空时）
// ================================================================
function fillHywrDatesFromResult(result, timeType) {
    if (!result) return;

    var startDp = mini.get('hywrStartDate');
    if (startDp && !startDp.getValue() && result.startDate) {
        startDp.setValue(result.startDate, false);
    }
    var endDp = mini.get('hywrEndDate');
    if (endDp && !endDp.getValue() && result.endDate) {
        endDp.setValue(result.endDate, false);
    }

    if (timeType === 1 || timeType === 2) {
        var reportDp = mini.get(getHywrReportDateId(timeType));
        if (reportDp && !reportDp.getValue() && result.reportDate) {
            reportDp.setValue(result.reportDate, false);
        }
    }

    updateHywrNavBtnStatus(timeType);
}

// ================================================================
// 表格创建（对照 ExtJS CreateHydrologicalWellReportTable）
// ================================================================
function CreateHydrologicalWellReportTable() {
    if (_hywrSelectedDeviceId <= 0) return;

    var timeType = _hywrCurrentTimeType;
    var divId = getHywrDivId(timeType);
    var containerId = 'Hywr' + timeType + 'Container';
    var totalCountId = getHywrTotalCountId(timeType);

    var orgId = getLeftOrgId();
    var startDp = mini.get('hywrStartDate');
    var endDp   = mini.get('hywrEndDate');

    var startDate  = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate    = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';
    var reportDate = '';

    if (timeType === 1 || timeType === 2) {
        var reportDp = mini.get(getHywrReportDateId(timeType));
        reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';
    }

    var deviceId   = _hywrSelectedDeviceId;
    var deviceName = _hywrCurrentDeviceName;

    // 先销毁旧的 helper
    if (hydrologicalWellReportHelper != null) {
        if (hydrologicalWellReportHelper.hot != undefined
            && hydrologicalWellReportHelper.hot != '') {
            try { hydrologicalWellReportHelper.hot.destroy(); } catch (e) {}
        }
        hydrologicalWellReportHelper = null;
    }

    mini.mask({ el: getHywrDataPanelId(timeType), cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getHydrologicalWellReportData',
        type: 'POST',
        data: {
            orgId: orgId,
            deviceId: deviceId,
            deviceName: deviceName,
            startDate: startDate,
            endDate: endDate,
            reportDate: reportDate,
            timeType: timeType
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(getHywrDataPanelId(timeType));
            fillHywrDatesFromResult(result, timeType);

            // 再次销毁（防止异步期间 tab 被切换）
            if (hydrologicalWellReportHelper != null) {
                if (hydrologicalWellReportHelper.hot != undefined
                    && hydrologicalWellReportHelper.hot != '') {
                    try { hydrologicalWellReportHelper.hot.destroy(); } catch (e) {}
                }
                hydrologicalWellReportHelper = null;
            }

            if (result.success) {
                var columns = result.columns || [];
                if (columns.length === 0) {
                    columns = ['SaveTime', 'SaveTime', '', '', 'Remark', 'recordId'];
                }
                hydrologicalWellReportHelper = HydrologicalWellReportHelper.createNew(
                    divId, containerId, result.template, result.data, columns
                );
                hydrologicalWellReportHelper.createTable();
            } else {
                document.getElementById(divId).innerHTML = '';
            }

            // 更新总数
            var totalCntEl = document.getElementById(totalCountId);
            if (totalCntEl) {
                var cnt = (result.data && result.data.length) ? result.data.length : 0;
                totalCntEl.textContent = _loginUserLanguageResource.totalCount + ':' + cnt;
            }
        },
        error: function () {
            mini.unmask(getHywrDataPanelId(timeType));
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 曲线创建（对照 ExtJS CreateHydrologicalWellReportCurve）
// ================================================================
function CreateHydrologicalWellReportCurve() {
    if (_hywrSelectedDeviceId <= 0) return;

    var timeType = _hywrCurrentTimeType;
    var curveDivId = getHywrCurveDivId(timeType);
    var curvePanelId = getHywrCurvePanelId(timeType);

    var orgId = getLeftOrgId();
    var startDp = mini.get('hywrStartDate');
    var endDp   = mini.get('hywrEndDate');

    var startDate  = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate    = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';
    var reportDate = '';

    if (timeType === 1 || timeType === 2) {
        var reportDp = mini.get(getHywrReportDateId(timeType));
        reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';
    }

    var deviceId   = _hywrSelectedDeviceId;
    var deviceName = _hywrCurrentDeviceName;

    mini.mask({ el: curvePanelId, cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData });

    $.ajax({
        url: context + '/reportDataMamagerController/getHydrologicalWellReportCurveData',
        type: 'POST',
        data: {
            orgId: orgId,
            deviceId: deviceId,
            deviceName: deviceName,
            startDate: startDate,
            endDate: endDate,
            reportDate: reportDate,
            timeType: timeType
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(curvePanelId);
            fillHywrDatesFromResult(result, timeType);

            // 标题：timeType 1、2 用 reportDate，3~5 用 startDate ~ endDate
            var firstLower = (typeof loginUserLanguageResourceFirstLower !== 'undefined'
                             && loginUserLanguageResourceFirstLower.hydrologicalWellReportCurveTitle)
                             ? loginUserLanguageResourceFirstLower.hydrologicalWellReportCurveTitle : '';

            var langPrefix = (loginUserLanguage && loginUserLanguage.toUpperCase() === 'ZH_CN' ? '' : ' ');

            var title = '';
            if (timeType === 1 || timeType === 2) {
                title = (result.deviceName || '') + langPrefix + firstLower
                      + '-' + (result.reportDate || '');
            } else {
                title = (result.deviceName || '') + langPrefix + firstLower
                      + '-' + (result.startDate || '') + '~' + (result.endDate || '');
            }

            // 用 HydrologicalWellReport 配置键
            buildAndRenderHywrCurve(result, curveDivId, title,
                                    '%H:%M', 'HydrologicalWellReport');
        },
        error: function () {
            mini.unmask(curvePanelId);
            mini.alert(_loginUserLanguageResource.ajaxError,
                       _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 通用曲线组装 + 渲染
// ================================================================
function buildAndRenderHywrCurve(result, divId, title, timeFormat, graphicSetKey) {
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

            // 空字符串 / 非数字 → null
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

    initHywrCurveChartFn(series, tickInterval, divId, title,
                         '', '', yAxis, color_all, true, timeFormat);
}

function initHywrCurveChartFn(series, tickInterval, divId, title,
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
                    menuItems: ['viewFullscreen', 'printChart', 'separator',
                                'downloadPNG', 'downloadJPEG', 'downloadSVG',
                                'separator', 'downloadXLS']
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
// 清空所有时间维度的表格和曲线
// ================================================================
function clearHywrTableAndCurve() {
    if (hydrologicalWellReportHelper != null) {
        if (hydrologicalWellReportHelper.hot != undefined
            && hydrologicalWellReportHelper.hot != '') {
            try { hydrologicalWellReportHelper.hot.destroy(); } catch (e) {}
        }
        hydrologicalWellReportHelper = null;
    }

    for (var i = 1; i <= 5; i++) {
        var dataDiv = document.getElementById(getHywrDivId(i));
        if (dataDiv) dataDiv.innerHTML = '';
        var curveDiv = document.getElementById(getHywrCurveDivId(i));
        if (curveDiv) curveDiv.innerHTML = '';
        var cnt = document.getElementById(getHywrTotalCountId(i));
        if (cnt) cnt.textContent = '';
    }
}

// ================================================================
// 工具条事件
// ================================================================
function onHywrRefresh() {
    var combo = mini.get('hywrDeviceCombo');
    if (combo) combo.setValue('', false);

    mini.get('hywrStartDate').setValue('', false);
    mini.get('hywrEndDate').setValue(new Date(), false);

    var r1 = mini.get('hywrReportDate1');
    if (r1) r1.setValue('', false);
    var r2 = mini.get('hywrReportDate2');
    if (r2) r2.setValue('', false);

    _hywrSelectedDeviceId     = 0;
    _hywrCurrentDeviceName    = '';
    _hywrCurrentCalculateType = 0;

    updateHywrNavBtnStatus(1);
    updateHywrNavBtnStatus(2);
    clearHywrTableAndCurve();
    loadHywrDeviceGrid();
}

function onHywrSearch() {
    var r1 = mini.get('hywrReportDate1');
    if (r1) r1.setValue('', false);
    var r2 = mini.get('hywrReportDate2');
    if (r2) r2.setValue('', false);

    CreateHydrologicalWellReportTable();
    CreateHydrologicalWellReportCurve();
}

// ================================================================
// 导出
// ================================================================
function onHywrExport(timeType) {
    if (_hywrModuleRight.viewFlag != 1) return;
    if (_hywrSelectedDeviceId <= 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'ExportHydrologicalWellReportData' + timestamp;

    var startDp = mini.get('hywrStartDate');
    var endDp   = mini.get('hywrEndDate');
    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    var reportDate = '';
    if (timeType === 1 || timeType === 2) {
        var reportDp = mini.get(getHywrReportDateId(timeType));
        reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';
    }

    var url = context + '/reportDataMamagerController/exportHydrologicalWellReportData'
        + '?timeType=' + timeType
        + '&deviceName=' + URLencode(URLencode(_hywrCurrentDeviceName))
        + '&deviceId=' + _hywrSelectedDeviceId
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&reportDate=' + reportDate
        + '&key=' + key;

    exportDataMask(key, getHywrDataPanelId(timeType), R.loadingData);
    document.location.href = url;
}

function onHywrBatchExport() {
    if (_hywrModuleRight.viewFlag != 1) return;

    var R = _loginUserLanguageResource;
    var timestamp = new Date().getTime();
    var key = 'batchExportHydrologicalWellReportData' + timestamp;

    var timeType = _hywrCurrentTimeType;

    var startDp = mini.get('hywrStartDate');
    var endDp   = mini.get('hywrEndDate');
    var startDate = startDp ? (startDp.getFormValue('yyyy-MM-dd') || '') : '';
    var endDate   = endDp   ? (endDp.getFormValue('yyyy-MM-dd')   || '') : '';

    var reportDate = '';
    if (timeType === 1 || timeType === 2) {
        var reportDp = mini.get(getHywrReportDateId(timeType));
        reportDate = reportDp ? (reportDp.getFormValue('yyyy-MM-dd') || '') : '';
    }

    var combo = mini.get('hywrDeviceCombo');
    var deviceName = combo ? (combo.getValue() || '') : '';

    var url = context + '/reportDataMamagerController/batchExportHydrologicalWellReportData'
        + '?timeType=' + timeType
        + '&deviceName=' + URLencode(URLencode(deviceName))
        + '&startDate=' + startDate
        + '&endDate=' + endDate
        + '&reportDate=' + reportDate
        + '&orgId=' + getLeftOrgId()
        + '&key=' + key;

    exportDataMask(key, getHywrDataPanelId(timeType), R.loadingData);
    document.location.href = url;
}

// ================================================================
// 保存
// ================================================================
function onHywrSave(timeType) {
    if (_hywrModuleRight.editFlag != 1) return;
    if (hydrologicalWellReportHelper != null) {
        hydrologicalWellReportHelper.saveData();
    }
}

// ================================================================
// Handsontable Helper
// ================================================================
var HydrologicalWellReportHelper = {
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
                    var colType = (colConfig ? colConfig.type : '') || 'text';
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

                var deviceName = _hywrCurrentDeviceName;
                var deviceId = _hywrSelectedDeviceId;

                var timeType = _hywrCurrentTimeType;
                var dataPanelId = getHywrDataPanelId(timeType);

                mini.mask({ el: dataPanelId, cls: 'mini-mask-loading',
                            html: _loginUserLanguageResource.submittingData });

                $.ajax({
                    method: 'POST',
                    url: context + '/reportDataMamagerController/saveHydrologicalWellReportData',
                    data: {
                        deviceId: deviceId,
                        deviceName: deviceName,
                        data: JSON.stringify(helper.editData)
                    },
                    dataType: 'json',
                    success: function (response) {
                        mini.unmask(dataPanelId);
                        if (response && response.success) {
                            mini.alert(_loginUserLanguageResource.tip,
                                       _loginUserLanguageResource.savedSuccessfully);
                            helper.clearContainer();
                            CreateHydrologicalWellReportTable();
                            CreateHydrologicalWellReportCurve();
                        } else {
                            helper.clearContainer();
                            mini.alert(_loginUserLanguageResource.tip,
                                       '<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>');
                        }
                    },
                    error: function () {
                        mini.unmask(dataPanelId);
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