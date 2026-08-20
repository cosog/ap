<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>历史数据详情</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; font-family:"Microsoft YaHei",Arial,sans-serif; background:#f5f7fa; }
        .detail-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .detail-toolbar { flex-shrink:0; background:#fff; padding:4px 10px; border-bottom:1px solid #e8e8e8; display:flex; align-items:center; }
        .detail-grid-wrapper { flex:1; overflow:hidden; padding:4px; }
        .loading-placeholder { display:flex; align-items:center; justify-content:center; height:100%; color:#999; font-size:13px; flex-direction:column; }
    </style>
</head>
<body>
<div class="detail-container">
    <!-- 工具栏 -->
    <div class="detail-toolbar">
        <span style="font-size:12px;color:#333;" id="detailTitle"></span>
        <span style="flex:1;"></span>
        <button id="exportHistoryDetailBtn" class="mini-button" iconCls="export" onclick="exportDetailData()"></button>
    </div>
    <!-- 表格容器 -->
    <div class="detail-grid-wrapper">
        <div id="detailGridContainer" style="width:100%; height:100%;">
            <div class="loading-placeholder"></div>
        </div>
    </div>
</div>

<script>
    var context = '<%=path%>';
    var _params = null; // 存储父页面传递的参数
    var detailGrid = null;

    // ================================================================
    // 1. 接收父页面数据 & 加载详情
    // ================================================================
    function setData(params) {
        _params = params;
        loadDetailData(params);
    }

    function loadDetailData(params) {
        var container = document.getElementById('detailGridContainer');
        container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>';

        var recordId = params.recordId || '';
        var deviceId = params.deviceId || '';
        var deviceName = params.deviceName || '';
        var calculateType = params.calculateType || 0;
        var deviceType = params.deviceType || '0';

        $.ajax({
            url: context + '/historyQueryController/getDeviceHistoryDetailsData',
            type: 'POST',
            data: {
                recordId: recordId,
                deviceId: deviceId,
                deviceName: deviceName,
                calculateType: calculateType,
                deviceType: deviceType
            },
            dataType: 'json',
            timeout: 15000,
            success: function(result) {
                if (result.totalRoot && result.totalRoot.length > 0) {
                    createDetailGrid('detailGridContainer', result.totalRoot, result.CellInfo);
                } else {
                    container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                }
            },
            error: function(xhr, status, errorThrown) {
                container.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
            }
        });
    }

    // ================================================================
    // 2. 创建 MiniUI 详情表格
    // ================================================================
    function createDetailGrid(containerId, data, cellInfo) {
        var container = document.getElementById(containerId);
        if (!container) return;
        container.innerHTML = '';

        if (detailGrid) {
            detailGrid.destroy();
            detailGrid = null;
        }

        detailGrid = new mini.DataGrid();
        detailGrid._cellInfo = cellInfo || [];
        detailGrid.set({
            id: 'detailDataGrid',
            style: 'width:100%; height:100%; visibility:hidden;',
            showPager: false,
            showColumns: false,
            allowCellSelect: true,
            allowCellWrap: false,
            allowResize: true,
            allowCellMerge: true,
            virtualScroll: false,
            allowAlternating: true,
            data: data || [],
            columns: [
                { field: 'name1', width: '16%', align: 'center', headerAlign: 'center' },
                { field: 'value1', width: '16%', align: 'center', headerAlign: 'center' },
                { field: 'name2', width: '16%', align: 'center', headerAlign: 'center' },
                { field: 'value2', width: '16%', align: 'center', headerAlign: 'center' },
                { field: 'name3', width: '16%', align: 'center', headerAlign: 'center' },
                { field: 'value3', width: '16%', align: 'center', headerAlign: 'center' }
            ],
            ondrawcell: function(e) {
                applyDetailCellStyle(e);
            },
            onrender: function() {
                var merges = [{ rowIndex: 0, columnIndex: 0, rowSpan: 1, colSpan: 6 }];
                mergeGridCells(this, merges);
                this.setStyle('visibility:visible;');
            },
            oncelldblclick: function(e) {
                handleDetailCellDblClick(e);
            }
        });

        detailGrid.render(container);
        // 兜底
        if (data && data.length > 0) {
            setTimeout(function() {
                var merges = [{ rowIndex: 0, columnIndex: 0, rowSpan: 1, colSpan: 6 }];
                mergeGridCells(detailGrid, merges);
                detailGrid.setStyle('visibility:visible;');
            }, 100);
        }
    }

    function mergeGridCells(grid, merges) {
        if (!grid) return;
        try {
            grid.mergeCells(merges);
        } catch (e) {
            setTimeout(function() {
                try { grid.mergeCells(merges); } catch(e2) {}
            }, 200);
        }
    }

    function applyDetailCellStyle(e) {
        var grid = e.sender;
        var cellInfo = grid._cellInfo || [];
        var record = e.record;
        var field = e.field;
        var rowIndex = e.rowIndex;
        var colIndex = e.columnIndex;

        if (rowIndex === 0) {
            e.cellStyle = 'font-size:20px; height:40px; font-weight:bold;';
            return;
        }
        if (!cellInfo) return;

        var groupMap = { name1:0, value1:0, name2:1, value2:1, name3:2, value3:2 };
        var groupIndex = groupMap[field];
        if (groupIndex === undefined) return;

        var alarmShowStyle = getAlarmShowStyle();

        for (var i = 0; i < cellInfo.length; i++) {
            var info = cellInfo[i];
            if (info.row === rowIndex && info.col === groupIndex) {
                var isNameColumn = field.indexOf('name') === 0;
                var isValueColumn = field.indexOf('value') === 0;
                if (isNameColumn) {
                    if (isNotVal(info.historyColor)) {
                        e.cellStyle = (e.cellStyle || '') + 'color:#' + info.historyColor + ';';
                    }
                    if (isNotVal(info.historyBgColor)) {
                        e.cellStyle = (e.cellStyle || '') + 'background-color:#' + info.historyBgColor + ';';
                    }
                } else if (isValueColumn) {
                    var alarmLevel = info.alarmLevel || 0;
                    if (alarmLevel > 0) {
                        e.cellStyle = (e.cellStyle || '') + 'font-weight:bold;';
                    }
                    var styleCfg = getAlarmStyleByLevel(alarmLevel, alarmShowStyle);
                    if (styleCfg) {
                        if (styleCfg.bg) e.cellStyle += 'background-color:' + styleCfg.bg + ';';
                        if (styleCfg.color) e.cellStyle += 'color:' + styleCfg.color + ';';
                    }
                }
                break;
            }
        }
    }

    // ================================================================
    // 3. 单元格双击处理
    // ================================================================
    function handleDetailCellDblClick(e) {
        var grid = e.sender;
        var record = e.record;
        if (!record) return;

        var rowIndex = grid.indexOf(record);
        var field = e.field || (e.column ? e.column.field : null);
        if (!field) return;
        if (rowIndex === 0) return;

        var groupMap = { name1:0, value1:0, name2:1, value2:1, name3:2, value3:2 };
        var groupIndex = groupMap[field];
        if (groupIndex === undefined) return;

        var itemName = record['name' + (groupIndex + 1)];
        var itemValue = record['value' + (groupIndex + 1)];

        var cellInfo = grid._cellInfo || [];
        var info = null;
        for (var i = 0; i < cellInfo.length; i++) {
            if (cellInfo[i].row === rowIndex && cellInfo[i].col === groupIndex) {
                info = cellInfo[i];
                break;
            }
        }
        if (!info) {
            console.warn('未找到 CellInfo，row=' + rowIndex + ', col=' + groupIndex);
            return;
        }

        // 判断是否应该打开曲线
        var type = info.type;
        var resolutionMode = info.resolutionMode;
        var columnDataType = info.columnDataType || '';
        var column = info.column;

        var isNumeric = function(val) {
            return val !== undefined && val !== null && val !== '' && !isNaN(parseFloat(val));
        };

        var shouldOpenCurve = false;
        if (type == 0) { // 采集项
            if (resolutionMode == 2) {
                if (columnDataType.toUpperCase() !== 'STRING' && isNumeric(itemValue)) {
                    shouldOpenCurve = true;
                }
            }
        } else if (type == 1) { // 计算项
            if (isNumByCalculateItemCode(column)) {
                shouldOpenCurve = true;
            }
        } else if (type == 3) { // 录入项
            if (isNumeric(itemValue)) {
                shouldOpenCurve = true;
            }
        } else if (type == 5) { // 协议拓展项
            if (resolutionMode == 2 || resolutionMode == 7) {
                if (isNumeric(itemValue)) {
                    shouldOpenCurve = true;
                }
            }
        }

        if (shouldOpenCurve) {
            viewItemHistoryCurve(itemName, itemValue, info);
        } else {
            viewItemHistoryDataTable(itemName, itemValue, info);
        }
    }

    // ================================================================
    // 4. 查看历史曲线（Highcharts）
    // ================================================================
   function viewItemHistoryCurve(itemName, itemValue, cellInfo) {
    var deviceId = _params.deviceId || '';
    var deviceName = _params.deviceName || '';
    var calculateType = _params.calculateType || 0;
    var deviceType = _params.deviceType || '0';

    var win = new mini.Window();
    win.set({
        title: _loginUserLanguageResource.trendCurve + ' - ' + itemName,
        width: '80%',
        height: '60%',
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

    // 1. 强制 body 为相对定位容器
    var body = win.getBodyEl();
    body.style.cssText = 'padding:0; margin:0; height:100%; width:100%; overflow:hidden; position:relative;';

    var containerId = 'itemHistoryCurveContainer_' + Date.now();

    // 工具条固定高度 46px（含 padding 和边框），为了兼容不同字体，可适当加大
    var toolbarHtml = '<div id="curveToolbar" style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fff; border-bottom:1px solid #e8e8e8; height:32px; box-sizing:border-box;">' +
        '<span style="font-size:12px; color:#333;">' + _loginUserLanguageResource.range + '：</span>' +
        '<input id="itemCurveStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
        '<span style="margin-left:8px; font-size:12px; color:#333;">' + _loginUserLanguageResource.timeTo + '：</span>' +
        '<input id="itemCurveEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
        '<button class="mini-button" iconCls="search" onclick="refreshCurveData(\'' + containerId + '\', \'' + itemName + '\', \'' + cellInfo.column + '\', \'' + cellInfo.type + '\', \'' + cellInfo.resolutionMode + '\')">' + _loginUserLanguageResource.search + '</button>' +
        '<span style="flex:1;"></span>' +
        '<span id="curveVacuateCountLabel" style="font-size:12px; color:#999; display:none;">' + _loginUserLanguageResource.vacuateCount + '：<span id="curveVacuateCountSpan">0</span></span>' +
        '<span id="curveTotalCountLabel" style="font-size:12px; color:#999; display:none;">' + _loginUserLanguageResource.totalCount + '：<span id="curveTotalCountSpan">0</span></span>' +
        '</div>';

    // 图表容器：绝对定位，顶部紧贴工具条下方
    var html = '<div style="position:relative; width:100%; height:100%;">' +
        toolbarHtml +
        '<div id="' + containerId + '" style="position:absolute; left:0; right:0; bottom:0; top:46px; overflow:hidden;"></div>' +
        '</div>';

    win.setBody(html);
    mini.parse(win.getBodyEl());

    // 设置默认时间
    var startDateCmp = mini.get('itemCurveStartDate');
    var endDateCmp = mini.get('itemCurveEndDate');
    if (startDateCmp && _params.startDate) {
        startDateCmp.setValue(_params.startDate);
    }
    if (endDateCmp && _params.endDate) {
        endDateCmp.setValue(_params.endDate);
    }

    var startDate = startDateCmp ? startDateCmp.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
    var endDate = endDateCmp ? endDateCmp.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
    loadCurveData(containerId, itemName, cellInfo.column, cellInfo.type, cellInfo.resolutionMode, startDate, endDate);
}
    
    	
   function refreshCurveData(containerId, itemName, itemCode, itemType, itemResolutionMode) {
	    var startDate = mini.get('itemCurveStartDate').getFormValue('yyyy-MM-dd HH:mm:ss');
	    var endDate = mini.get('itemCurveEndDate').getFormValue('yyyy-MM-dd HH:mm:ss');
	    loadCurveData(containerId, itemName, itemCode, itemType, itemResolutionMode, startDate, endDate);
	}

	function loadCurveData(containerId, itemName, itemCode, itemType, itemResolutionMode, startDate, endDate) {
	    var deviceId = _params.deviceId || '';
	    var deviceName = _params.deviceName || '';
	    var calculateType = _params.calculateType || 0;
	    var deviceType = _params.deviceType || '0';
	    var hours = 'all';

	    var mask = mini.mask({
	        el: document.body,
	        cls: 'mini-mask-loading',
	        html: _loginUserLanguageResource.loadingData
	    });

	    $.ajax({
	        url: context + '/historyQueryController/getItemHistoryCurveData',
	        type: 'POST',
	        data: {
	            deviceId: deviceId,
	            deviceName: deviceName,
	            deviceType: deviceType,
	            calculateType: calculateType,
	            itemName: itemName,
	            itemCode: itemCode,
	            itemType: itemType,
	            itemResolutionMode: itemResolutionMode,
	            startDate: startDate,
	            endDate: endDate,
	            hours: hours
	        },
	        dataType: 'json',
	        timeout: 15000,
	        success: function(result) {
	            mini.unmask(document.body);
	            // 更新记录数显示
	            var vacLabel = document.getElementById('curveVacuateCountLabel');
	            var totalLabel = document.getElementById('curveTotalCountLabel');
	            if (result.vacuateCount !== undefined) {
	                document.getElementById('curveVacuateCountSpan').textContent = result.vacuateCount;
	                if (vacLabel) vacLabel.style.display = 'inline';
	            }
	            if (result.totalCount !== undefined) {
	                document.getElementById('curveTotalCountSpan').textContent = result.totalCount;
	                if (totalLabel) totalLabel.style.display = 'inline';
	            }

	            if (!result || !result.list || result.list.length === 0) {
	                document.getElementById(containerId).innerHTML = '<div style="text-align:center;padding:20px;">' + _loginUserLanguageResource.emptyMsg + '</div>';
	                return;
	            }
	            // 绘图（与之前相同）
	            var data = result.list;
	            var legendNames = result.curveItems || [];
	            var legendCodes = result.curveItemCodes || [];
	            var curveConf = result.curveConf || [];
	            var graphicSet = result.graphicSet || {};
	            var hiddenExceptionData = result.hiddenExceptionData || false;
	            var defaultColors = ['#7cb5ec','#434348','#90ed7d','#f7a35c','#8085e9','#f15c80','#e4d354','#2b908f','#f45b5b','#91e8e1'];

	            var series = [];
	            var yAxis = [];
	            var colors = [];
	            for (var i = 0; i < legendNames.length; i++) {
	                var color = curveConf[i] && curveConf[i].color ? '#' + curveConf[i].color : defaultColors[i % 10];
	                colors.push(color);
	                var singleSeries = {
	                    name: legendNames[i],
	                    data: [],
	                    lineWidth: curveConf[i] ? curveConf[i].lineWidth : 2,
	                    dashStyle: curveConf[i] ? curveConf[i].dashStyle : 'Solid',
	                    marker: { enabled: false },
	                    yAxis: i
	                };
	                for (var j = 0; j < data.length; j++) {
	                    var ts = Date.parse(data[j].acqTime.replace(/-/g, '/'));
	                    var val = parseFloat(data[j].data[i]);
	                    if (!isNaN(val)) {
	                        if (hiddenExceptionData && !isNumber(val)) continue;
	                        singleSeries.data.push([ts, val]);
	                    }
	                }
	                // 计算 yAxis 范围
	                var maxVal = null, minVal = null;
	                var allPos = true, allNeg = true;
	                for (var k = 0; k < singleSeries.data.length; k++) {
	                    var v = singleSeries.data[k][1];
	                    if (v < 0) allPos = false;
	                    if (v >= 0) allNeg = false;
	                }
	                if (allNeg) maxVal = 0;
	                if (allPos) minVal = 0;
	                if (graphicSet.History && graphicSet.History.length > 0) {
	                    for (var g = 0; g < graphicSet.History.length; g++) {
	                        if (graphicSet.History[g].itemCode === legendCodes[i]) {
	                            if (graphicSet.History[g].yAxisMaxValue) maxVal = parseFloat(graphicSet.History[g].yAxisMaxValue);
	                            if (graphicSet.History[g].yAxisMinValue) minVal = parseFloat(graphicSet.History[g].yAxisMinValue);
	                            break;
	                        }
	                    }
	                }
	                var axis = {
	                    max: maxVal,
	                    min: minVal,
	                    title: { text: legendNames[i], style: { color: color } },
	                    labels: { style: { color: color } },
	                    opposite: curveConf[i] ? curveConf[i].yAxisOpposite : false,
	                    lineWidth: 1,
	                    tickWidth: 1,
	                    tickLength: 5
	                };
	                yAxis.push(axis);
	                series.push(singleSeries);
	            }

	            var timeFormat = '%m-%d';
	            if (data.length > 0 && result.minAcqTime && result.maxAcqTime && result.minAcqTime.split(' ')[0] === result.maxAcqTime.split(' ')[0]) {
	                timeFormat = '%H:%M';
	            }
	            var chart = initHistoryCurveChart(containerId, series, yAxis, colors, result.deviceName + ' - ' + itemName, '', timeFormat);
	        },
	        error: function(xhr, status, errorThrown) {
	            mini.unmask(document.body);
	            document.getElementById(containerId).innerHTML = '<div style="text-align:center;padding:20px;color:red;">' + _loginUserLanguageResource.requestFailed + '</div>';
	            console.error('加载曲线数据失败:', status, errorThrown);
	        }
	    });
	}
    


    // 曲线绘制函数（Highcharts）
    function initHistoryCurveChart(divId, series, yAxis, colors, title, xtitle, timeFormat) {
        if ($("#" + divId).length === 0) return;
        var chart = new Highcharts.Chart({
            chart: {
                renderTo: divId,
                type: 'spline',
                animation: false,
                zoomType: 'xy',
                zooming: { mouseWheel: { enabled: false } }
            },
            time: { timezoneOffset: new Date().getTimezoneOffset() },
            credits: { enabled: false },
            title: { text: title, style: { fontSize: chartTitleFontSize || '14px' } },
            colors: colors,
            xAxis: {
                type: 'datetime',
                title: { text: xtitle },
                tickPixelInterval: 120,
                labels: {
                    formatter: function() { return this.axis.chart.time.dateFormat(timeFormat, this.value); },
                    rotation: -45
                }
            },
            yAxis: yAxis,
            tooltip: {
                crosshairs: true,
                shared: true,
                style: { color: '#333', fontSize: '12px' }
            },
            exporting: {
                enabled: true,
                filename: title,
                fallbackToExportServer: false
            },
            plotOptions: {
                spline: {
                    lineWidth: 1,
                    marker: { enabled: true, radius: 3 },
                    shadow: true
                }
            },
            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'bottom',
                enabled: false
            },
            series: series
        });
    }

    // ================================================================
    // 5. 查看历史数据表（MiniUI Grid）
    // ================================================================
    // ================================================================
// 5. 查看历史数据表（MiniUI Grid）—— 带分页和工具条
// ================================================================
function viewItemHistoryDataTable(itemName, itemValue, cellInfo) {
    var deviceId = _params.deviceId || '';
    var deviceName = _params.deviceName || '';
    var calculateType = _params.calculateType || 0;
    var startDate = _params.startDate || '';
    var endDate = _params.endDate || '';
    var itemCode = cellInfo.column;
    var itemType = cellInfo.type;
    var itemResolutionMode = cellInfo.resolutionMode;
    var bitIndex = cellInfo.bitIndex || '';

    var win = new mini.Window();
    win.set({
        title: _loginUserLanguageResource.historyData + ' - ' + itemName,
        width: '50%',
        height: '90%',
        minWidth: 350,
        minHeight: 350,
        modal: true,
        showHeader: true,
        allowResize: true,
        maxable: true,
        minable: true,
        showCloseButton: true
    });
    win.show();

    // 强制 body 为相对定位，防止布局错乱
    var body = win.getBodyEl();
    body.style.cssText = 'padding:0; margin:0; height:100%; width:100%; overflow:hidden; position:relative;';

    var containerId = 'itemHistoryDataGridContainer_' + Date.now();

    // ★ 工具条 HTML：包含时间范围、查询、导出按钮和总记录数
    var toolbarHtml = '<div id="historyDataToolbar" style="display:flex; align-items:center; flex-wrap:wrap; gap:4px; padding:4px 10px; background:#fff; border-bottom:1px solid #e8e8e8; height:32px; box-sizing:border-box;">' +
        '<span style="font-size:12px; color:#333;">' + _loginUserLanguageResource.range + '：</span>' +
        '<input id="itemHistoryDataStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
        '<span style="margin-left:8px; font-size:12px; color:#333;">' + _loginUserLanguageResource.timeTo + '：</span>' +
        '<input id="itemHistoryDataEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />' +
        '<button class="mini-button" iconCls="search" onclick="refreshHistoryDataGrid(\'' + containerId + '\')">' + _loginUserLanguageResource.search + '</button>' +
        '<span style="flex:1;"></span>' +
        '<button class="mini-button" iconCls="export" onclick="exportItemHistoryData(\'' + containerId + '\', \'' + itemName + '\', \'' + itemCode + '\', \'' + itemType + '\', \'' + itemResolutionMode + '\', \'' + bitIndex + '\')">' + (_loginUserLanguageResource.exportData || '导出') + '</button>' +
        // ★ 总记录数标签（初始隐藏）
        '<span id="historyDataTotalCountLabel" style="font-size:12px; color:#999; margin-left:8px;">' + _loginUserLanguageResource.totalCount + '：<span id="historyDataTotalCountSpan">0</span></span>' +
        '</div>';

    // ★ 主体布局：工具条固定高度，grid 填充剩余空间
    var html = '<div style="position:relative; width:100%; height:100%;">' +
        toolbarHtml +
        // grid 容器：绝对定位，顶部紧贴工具条下方
        '<div id="' + containerId + '" style="position:absolute; left:0; right:0; bottom:0; top:32px; overflow:hidden;"></div>' +
        '</div>';

    win.setBody(html);
    mini.parse(win.getBodyEl());

    // ★ 设置默认时间（从父页面传递）
    var startDateCmp = mini.get('itemHistoryDataStartDate');
    var endDateCmp = mini.get('itemHistoryDataEndDate');
    if (startDateCmp && startDate) {
        startDateCmp.setValue(startDate);
    }
    if (endDateCmp && endDate) {
        endDateCmp.setValue(endDate);
    }

    // ★ 创建分页 Grid
    var gridContainer = document.getElementById(containerId);
    if (!gridContainer) return;

    var grid = new mini.DataGrid();
    grid.set({
        id: 'historyDataGrid_' + containerId,
        style: 'width:100%; height:100%;',
        showPager: true,
        pageSize: parseInt(_defaultPageSize, 10) || 25,
        allowResize: true,
        allowAlternating: true,
        virtualScroll: false,
        url: context + '/historyQueryController/getItemHistoryData',
        dataField: 'totalRoot',
        totalField: 'totalCount',
        columns: [
            { type: 'indexcolumn', width: getLabelWidth(_loginUserLanguageResource.idx,_loginUserLanguage)+30, headerAlign:'center', align: 'center', header: _loginUserLanguageResource.idx },
            { field: 'acqTime', width: '50%', headerAlign:'center', align: 'center', header: _loginUserLanguageResource.acqTime, dateFormat: 'yyyy-MM-dd HH:mm:ss' },
            { field: 'data', width: '50%', headerAlign:'center', align: 'center', header: itemName }
        ],
        onbeforeload: function(e) {
            var params = e.params || {};
            
            var pageIndex = params.pageIndex || 0;
            var pageSize = params.pageSize || _defaultPageSize;
            params.start = pageIndex * pageSize;
            params.limit = pageSize;
            
            // ★ 从工具条获取时间
            var start = mini.get('itemHistoryDataStartDate');
            var end = mini.get('itemHistoryDataEndDate');
            params.startDate = start ? start.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
            params.endDate = end ? end.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
            // ★ 固定业务参数
            params.deviceId = deviceId;
            params.deviceName = deviceName;
            params.calculateType = calculateType;
            params.itemName = itemName;
            params.itemCode = itemCode;
            params.itemType = itemType;
            params.itemResolutionMode = itemResolutionMode;
            params.itemBitIndex = bitIndex || '';
            
            params.totalCount = grid.getTotalCount() || 0;
            
            e.params = params;
        },
        // ★ 加载完成后更新总记录数
        onload: function(e) {
            var result = e.result;
            if (result && result.totalCount !== undefined) {
                var totalSpan = document.getElementById('historyDataTotalCountSpan');
                if (totalSpan) {
                    totalSpan.textContent = result.totalCount;
                }
            }
        }
    });
    grid.render(gridContainer);

    // ★ 首次加载
    grid.load();

    win.on('beforedestroy', function() {
        var gridId = 'historyDataGrid_' + containerId;
        var grid = mini.get(gridId);
        if (grid) {
            grid.destroy();
        }
    });
}

// ★ 刷新 Grid（由查询按钮触发）
function refreshHistoryDataGrid(containerId) {
    var grid = mini.get('historyDataGrid_' + containerId);
    if (grid) {
        grid.load();
    }
}

    // ================================================================
    // 6. 导出历史数据表
    // ================================================================
   function exportItemHistoryData(containerId, itemName, itemCode, itemType, itemResolutionMode, bitIndex) {
    var deviceId = _params.deviceId || '';
    var deviceName = _params.deviceName || '';
    var calculateType = _params.calculateType || 0;
    var start = mini.get('itemHistoryDataStartDate');
    var end = mini.get('itemHistoryDataEndDate');
    var startDate = start ? start.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
    var endDate = end ? end.getFormValue('yyyy-MM-dd HH:mm:ss') : '';

    var key = 'exportItemHistoryData_' + deviceId + '_' + Date.now();
    var url = context + '/historyQueryController/exportItemHistoryData';
    var param = '&deviceId=' + deviceId +
                '&deviceName=' + encodeURIComponent(encodeURIComponent(deviceName)) +
                '&calculateType=' + calculateType +
                '&itemName=' + encodeURIComponent(encodeURIComponent(itemName)) +
                '&itemCode=' + itemCode +
                '&itemType=' + itemType +
                '&itemResolutionMode=' + itemResolutionMode +
                '&itemBitIndex=' + (bitIndex || '') +
                '&startDate=' + encodeURIComponent(startDate) +
                '&endDate=' + encodeURIComponent(endDate) +
                '&key=' + key;
    exportDataMask(key, document.body, _loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
}

    // ================================================================
    // 7. 导出详情数据
    // ================================================================
    function exportDetailData() {
        if (!_params) return;
        var recordId = _params.recordId || '';
        var deviceId = _params.deviceId || '';
        var deviceName = _params.deviceName || '';
        var calculateType = _params.calculateType || 0;
        var deviceType = _params.deviceType || '0';

        var key = 'exportDetail_' + recordId + '_' + Date.now();
        var url = context + '/historyQueryController/exportDeviceHistoryQueryDetailsData';
        var param = '&recordId=' + recordId +
                    '&deviceId=' + deviceId +
                    '&deviceName=' + encodeURIComponent(encodeURIComponent(deviceName)) +
                    '&calculateType=' + calculateType +
                    '&deviceType=' + deviceType +
                    '&key=' + key;
        exportDataMask(key, document.body, _loginUserLanguageResource.loadingData);
        openExcelWindow(url + '?flag=true' + param);
    }

    // ================================================================
    // 8. 工具函数（报警样式）
    // ================================================================
    function getAlarmStyleByLevel(level, styleConfig) {
        var cfg = (styleConfig && styleConfig.Data) || {};
        var levelMap = {100: cfg.FirstLevel||{}, 200: cfg.SecondLevel||{}, 300: cfg.ThirdLevel||{}};
        var lvl = levelMap[level] || {};
        var bg = lvl.BackgroundColor ? '#'+lvl.BackgroundColor : 'transparent';
        var color = lvl.Color ? '#'+lvl.Color : '#000';
        var opacity = (lvl.Opacity!==undefined) ? lvl.Opacity : 1;
        var bgRgba = (opacity===0) ? 'transparent' : color16ToRgba(bg, opacity);
        return { bg: bgRgba, color: color };
    }

    // ================================================================
    // 9. 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();

        var titleEl = document.getElementById('detailTitle');
        if (titleEl) {
            titleEl.textContent = _loginUserLanguageResource.viewCurveOrTableData;
        }

        var exportBtn = mini.get('exportHistoryDetailBtn');
        if (exportBtn) exportBtn.setText(_loginUserLanguageResource.exportData);

        var placeholder = document.querySelector('.loading-placeholder');
        if (placeholder) {
            placeholder.innerHTML = _loginUserLanguageResource.loadingData;
        }
    });
</script>
</body>
</html>