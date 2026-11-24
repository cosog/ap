/* ============================================================
 * 采集报表 - 全局状态
 * ============================================================ */
var acqReportState = {
    tabInfo: null,
    activeLevel1Index: 0,
    activeLevel2Index: 0,
    activeReportType: 'single',      // 'single' | 'production'
    currentSingleWellDevice: null,
    singleCurve: null,
    productionCurve: null
};

/* ============================================================
 * 入口
 * ============================================================ */
function initAcquisitionDataReportPage() {
    acqReportState.tabInfo = getAcqReportTabInfo();
    renderLevel1Tabs();
    if ((acqReportState.tabInfo.children || []).length > 0) {
        selectLevel1(0);
    }
}

function getAcqReportTabInfo() {
    if (typeof tabInfo !== 'undefined' && tabInfo && tabInfo.children) {
        return tabInfo;
    }
    if (window.tabInfo && window.tabInfo.children) {
        return window.tabInfo;
    }
    return { children: [] };
}

/* ============================================================
 * 一级标签（底部）
 * ============================================================ */
function renderLevel1Tabs() {
    var footer = document.getElementById('level1Footer');
    var children = acqReportState.tabInfo.children || [];
    var html = '';
    for (var i = 0; i < children.length; i++) {
        html += '<div class="tab-item' + (i === 0 ? ' active' : '') + '" data-index="' + i + '">'
            + children[i].text + '</div>';
    }
    footer.innerHTML = html;
    var items = footer.querySelectorAll('.tab-item');
    for (var j = 0; j < items.length; j++) {
        items[j].onclick = function () {
            selectLevel1(parseInt(this.getAttribute('data-index')));
        };
    }
}

function selectLevel1(index) {
    acqReportState.activeLevel1Index = index;
    acqReportState.activeLevel2Index = 0;
    var items = document.querySelectorAll('#level1Footer .tab-item');
    for (var i = 0; i < items.length; i++) {
        items[i].classList.toggle('active', i === index);
    }
    renderLevel2Tabs();
    renderReportArea();
}

/* ============================================================
 * 二级标签（左侧）
 * ============================================================ */
function getActiveLevel2Items() {
    var level1 = acqReportState.tabInfo.children[acqReportState.activeLevel1Index];
    if (!level1) return [];
    var children = level1.children || [];
    var items = [];
    if (children.length > 1) {
        items.push({ text: _loginUserLanguageResource.all, isAll: true, deviceTypeId: null });
    }
    for (var i = 0; i < children.length; i++) {
        items.push({
            text: children[i].text,
            deviceTypeId: children[i].deviceTypeId,
            children: children[i].children
        });
    }
    return items;
}

function renderLevel2Tabs() {
    var sidebar = document.getElementById('level2Sidebar');
    var items = getActiveLevel2Items();
    if (items.length === 0) {
        sidebar.style.display = 'none';
        return;
    }
    sidebar.style.display = 'flex';
    var html = '';
    for (var i = 0; i < items.length; i++) {
        html += '<div class="tab-item' + (i === acqReportState.activeLevel2Index ? ' active' : '')
            + '" data-index="' + i + '">' + items[i].text + '</div>';
    }
    sidebar.innerHTML = html;
    var els = sidebar.querySelectorAll('.tab-item');
    for (var j = 0; j < els.length; j++) {
        els[j].onclick = function () {
            selectLevel2(parseInt(this.getAttribute('data-index')));
        };
    }
}

function selectLevel2(index) {
    acqReportState.activeLevel2Index = index;
    var els = document.querySelectorAll('#level2Sidebar .tab-item');
    for (var i = 0; i < els.length; i++) {
        els[i].classList.toggle('active', i === index);
    }
    renderReportArea();
}

/* ============================================================
 * 报表区域（顶部：单设备报表 / 区域报表；主体：Splitter 布局）
 * ============================================================ */
function renderReportArea() {
    destroyCurves();
    var container = document.getElementById('reportContentArea');
    container.innerHTML =
        '<div class="report-type-tabs">' +
            '<div class="report-tab' + (acqReportState.activeReportType === 'single' ? ' active' : '') +
                '" data-type="single">' + _loginUserLanguageResource.singleDeviceReport + '</div>' +
            '<div class="report-tab' + (acqReportState.activeReportType === 'production' ? ' active' : '') +
                '" data-type="production">' + _loginUserLanguageResource.areaReport + '</div>' +
        '</div>' +
        '<div class="report-type-body" id="reportTypeBody"></div>';

    var tabEls = container.querySelectorAll('.report-tab');
    for (var i = 0; i < tabEls.length; i++) {
        tabEls[i].onclick = function () {
            acqReportState.activeReportType = this.getAttribute('data-type');
            renderReportArea();
        };
    }

    var body = document.getElementById('reportTypeBody');
    if (acqReportState.activeReportType === 'single') {
        buildSingleWellReport(body);
    } else {
        buildProductionReport(body);
    }
}

function destroyCurves() {
    if (acqReportState.singleCurve) {
        try { acqReportState.singleCurve.destroy(); } catch (e) {}
        acqReportState.singleCurve = null;
    }
    if (acqReportState.productionCurve) {
        try { acqReportState.productionCurve.destroy(); } catch (e) {}
        acqReportState.productionCurve = null;
    }
}

/* ============================================================
 * 单设备报表
 * ============================================================ */
function buildSingleWellReport(container) {
    var R = _loginUserLanguageResource;
    container.innerHTML =
        '<div class="report-inner-container">' +
            '<div class="report-toolbar-area">' +
                '<button id="singleWellRefreshBtn" class="mini-button" plain="true" iconCls="note-refresh"></button>' +
                '<span class="toolbar-sep"></span>' +
                '<span class="toolbar-label">' + R.deviceName + '：</span>' +
                '<input id="singleWellDeviceCombo" class="mini-combobox" style="width:150px;" />' +
                '<span class="toolbar-sep"></span>' +
                '<span class="toolbar-label">' + R.date + '：</span>' +
                '<input id="singleWellStartDate" class="mini-datepicker" style="width:130px;" format="yyyy-MM-dd" />' +
                '<span class="toolbar-label">' + R.timeTo + '：</span>' +
                '<input id="singleWellEndDate" class="mini-datepicker" style="width:130px;" format="yyyy-MM-dd" />' +
                '<button id="singleWellSearchBtn" class="mini-button" iconCls="search"></button>' +
                '<button id="singleWellBatchExportBtn" class="mini-button" iconCls="export"></button>' +
                '<span class="report-toolbar-spacer"></span>' +
                '<button id="singleWellExportBtn" class="mini-button" iconCls="export"></button>' +
                '<button id="singleWellSaveBtn" class="mini-button" iconCls="save"></button>' +
                '<span id="singleWellTotalCount" style="font-size:12px;margin-left:8px;color:#666;"></span>' +
            '</div>' +
            '<div class="report-body-area">' +
                '<div id="singleWellSplitter" class="mini-splitter" style="width:100%;height:100%;" vertical="false">' +
                    '<div size="20%" showCollapseButton="true" minSize="150" maxSize="500">' +
                        '<div id="singleWellDeviceListPanel" class="mini-panel" style="width:100%;height:100%;"' +
                            ' showHeader="true" bodyStyle="padding:0;">' +
                            '<div id="singleWellDeviceListGrid" class="mini-datagrid" style="width:100%;height:100%;"' +
                                ' idField="id" showPager="false" allowAlternating="true" allowCellSelect="true"></div>' +
                        '</div>' +
                    '</div>' +
                    '<div size="80%" showCollapseButton="false">' +
                        '<div id="singleWellRightSplitter" class="mini-splitter" style="width:100%;height:100%;" vertical="true">' +
                            '<div size="50%" showCollapseButton="true" minSize="100">' +
                                '<div id="singleWellCurvePanel" class="mini-panel" style="width:100%;height:100%;"' +
                                    ' showHeader="true" bodyStyle="padding:0;">' +
                                    '<div id="singleWellCurveDiv" style="width:100%;height:100%;"></div>' +
                                '</div>' +
                            '</div>' +
                            '<div size="50%" showCollapseButton="true" minSize="100">' +
                                '<div id="singleWellDataPanel" class="mini-panel" style="width:100%;height:100%;"' +
                                    ' showHeader="true" bodyStyle="padding:0;overflow:auto;">' +
                                    '<div id="singleWellDataDiv" style="width:100%;height:100%;"></div>' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</div>';

    mini.parse();

    /* 面板标题 & 按钮文本 */
    mini.get('singleWellRefreshBtn').setText(R.refresh);
    mini.get('singleWellSearchBtn').setText(R.search);
    mini.get('singleWellBatchExportBtn').setText(R.bulkExportData);
    mini.get('singleWellExportBtn').setText(R.exportData);
    mini.get('singleWellSaveBtn').setText(R.save);
    mini.get('singleWellDeviceListPanel').setTitle(R.deviceList);
    mini.get('singleWellCurvePanel').setTitle(R.reportCurve);
    mini.get('singleWellDataPanel').setTitle(R.reportData);

    if (typeof loginUserRoleReportEdit !== 'undefined' && loginUserRoleReportEdit != 1) {
        mini.get('singleWellSaveBtn').setEnabled(false);
    }

    /* 事件 */
    mini.get('singleWellRefreshBtn').on('click', function () {
        mini.get('singleWellStartDate').setValue(null);
        mini.get('singleWellEndDate').setValue(new Date());
        loadSingleWellDeviceList();
    });
    mini.get('singleWellSearchBtn').on('click', function () {
        loadSingleWellReportData();
        loadSingleWellReportCurve();
    });
    mini.get('singleWellBatchExportBtn').on('click', batchExportSingleWellData);
    mini.get('singleWellExportBtn').on('click', exportSingleWellData);
    mini.get('singleWellSaveBtn').on('click', saveSingleWellData);

    mini.get('singleWellEndDate').setValue(new Date());

    initSingleWellDeviceCombo();
    initSingleWellDeviceListGrid();
    loadSingleWellDeviceList();
}

function initSingleWellDeviceCombo() {
    var combo = mini.get('singleWellDeviceCombo');
    if (!combo) return;
    combo.set({
        textField: 'boxval',
        valueField: 'boxkey',
        dataField: 'list',
        pageSize: 20,
        url: context + '/reportDataMamagerController/loadAcquisitionDataReportDeviceComboxList',
        emptyText: '--' + _loginUserLanguageResource.all + '--',
        onbeforeload: function (e) {
            e.params = e.params || {};
            e.params.orgId = getCurrentOrgId();
            e.params.deviceName = combo.getText();
        }
    });
    combo.on('valuechanged', function () {
        loadSingleWellReportData();
        loadSingleWellReportCurve();
    });
}

function initSingleWellDeviceListGrid() {
    var grid = mini.get('singleWellDeviceListGrid');
    if (!grid) return;
    grid.set({
        columns: [
            { type: 'indexcolumn', header: '', width: 40, headerAlign: 'center' },
            { field: 'deviceName', header: _loginUserLanguageResource.deviceName, width: 120, headerAlign: 'center' },
            { field: 'commStatusName', header: _loginUserLanguageResource.communicationStatus, width: 80, headerAlign: 'center' }
        ]
    });
    grid.on('selectionchanged', function () {
        var selected = grid.getSelecteds();
        if (selected && selected.length > 0) {
            acqReportState.currentSingleWellDevice = selected[0];
            loadSingleWellReportData();
            loadSingleWellReportCurve();
        }
    });
}

function loadSingleWellDeviceList() {
    var grid = mini.get('singleWellDeviceListGrid');
    if (!grid) return;
    grid.setUrl(context + '/reportDataMamagerController/loadSingleWellAcquisitionDataReportWellList');
    grid.load({
        orgId: getCurrentOrgId(),
        deviceType: getCurrentDeviceType(),
        deviceName: mini.get('singleWellDeviceCombo').getText() || ''
    });
}

function loadSingleWellReportData() {
    var div = document.getElementById('singleWellDataDiv');
    if (!div) return;
    var R = _loginUserLanguageResource;
    var deviceName = mini.get('singleWellDeviceCombo').getText() || '';
    var deviceId = 0, calculateType = 0;
    if (acqReportState.currentSingleWellDevice) {
        deviceId = acqReportState.currentSingleWellDevice.id || 0;
        deviceName = acqReportState.currentSingleWellDevice.deviceName || deviceName;
        calculateType = acqReportState.currentSingleWellDevice.calculateType || 0;
    }

    div.innerHTML = '<div class="report-empty-msg">' + R.loadingData + '</div>';

    $.ajax({
        url: context + '/reportDataMamagerController/getSingleWellAcquisitionDataRangeReportData',
        type: 'POST',
        dataType: 'json',
        data: {
            orgId: getCurrentOrgId(),
            deviceId: deviceId,
            deviceName: deviceName,
            calculateType: calculateType,
            startDate: mini.get('singleWellStartDate').getFormValue() || '',
            endDate: mini.get('singleWellEndDate').getFormValue() || '',
            reportType: 0,
            deviceType: getCurrentDeviceType()
        },
        success: function (result) {
            if (result && result.success) {
                renderDataTable('singleWellDataDiv', result.template, result.data, null);
                var countEl = document.getElementById('singleWellTotalCount');
                if (countEl) countEl.innerText = R.totalCount + ': ' + ((result.data && result.data.length) || 0);
            } else {
                div.innerHTML = '<div class="report-empty-msg">' + R.noData + '</div>';
            }
        },
        error: function () {
            div.innerHTML = '<div class="report-empty-msg error">' + R.requestFailed + '</div>';
        }
    });
}

function loadSingleWellReportCurve() {
    var R = _loginUserLanguageResource;
    var deviceName = mini.get('singleWellDeviceCombo').getText() || '';
    var deviceId = 0, calculateType = 0;
    if (acqReportState.currentSingleWellDevice) {
        deviceId = acqReportState.currentSingleWellDevice.id || 0;
        deviceName = acqReportState.currentSingleWellDevice.deviceName || deviceName;
        calculateType = acqReportState.currentSingleWellDevice.calculateType || 0;
    }

    $.ajax({
        url: context + '/reportDataMamagerController/getSingleWellRangeReportCurveData',
        type: 'POST',
        dataType: 'json',
        data: {
            orgId: getCurrentOrgId(),
            deviceId: deviceId,
            deviceName: deviceName,
            calculateType: calculateType,
            startDate: mini.get('singleWellStartDate').getFormValue() || '',
            endDate: mini.get('singleWellEndDate').getFormValue() || '',
            reportType: 0,
            deviceType: getCurrentDeviceType(),
            unitClasses: 2
        },
        success: function (result) {
            if (result) renderSingleWellCurve(result);
        }
    });
}

function renderSingleWellCurve(result) {
    if (typeof Highcharts === 'undefined') return;
    var divId = 'singleWellCurveDiv';
    if (!$('#' + divId).length) return;

    if (acqReportState.singleCurve) {
        try { acqReportState.singleCurve.destroy(); } catch (e) {}
    }

    acqReportState.singleCurve = new Highcharts.Chart({
        chart: { renderTo: divId, type: 'spline', borderWidth: 0, zoomType: 'xy' },
        credits: { enabled: false },
        title: {
            text: (result.deviceName || '') + _loginUserLanguageResource.dailyReportCurve,
            style: { fontSize: 14 }
        },
        xAxis: {
            type: 'datetime',
            labels: {
                formatter: function () { return this.axis.chart.time.dateFormat('%m-%d', this.value); },
                rotation: -45
            }
        },
        yAxis: buildYAxisFromResult(result),
        colors: buildColorFromResult(result),
        tooltip: { crosshairs: true, shared: true },
        legend: { layout: 'horizontal', align: 'center', verticalAlign: 'bottom' },
        series: buildSeriesFromResult(result)
    });
}