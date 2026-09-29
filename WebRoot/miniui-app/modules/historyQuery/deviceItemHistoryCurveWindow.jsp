<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>趋势曲线</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #fff;
        }
        .curve-wrapper {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
        }
        .curve-toolbar {
            flex-shrink: 0;
            display: flex; align-items: center; flex-wrap: wrap; gap: 4px;
            padding: 4px 10px;
            background: #fff;
            border-bottom: 1px solid #e8e8e8;
        }
        .curve-toolbar .label { font-size: 12px; color: #333; }
        .curve-toolbar .spacer { flex: 1; }
        .curve-toolbar .count-info { font-size: 12px; color: #999; }
        .curve-body {
            flex: 1;
            min-height: 0;
            overflow: hidden;
            position: relative;
        }
        .loading-placeholder {
            display: flex; align-items: center; justify-content: center;
            height: 100%; color: #999; font-size: 13px;
        }
        .loading-placeholder.error { color: #ff4d4f; }
    </style>
</head>
<body>
    <div class="curve-wrapper">
        <div class="curve-toolbar">
            <span class="label" id="rangeLabel"></span>
            <input id="itemCurveStartDate" class="mini-datepicker" style="width:150px;"
                   format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm"
                   showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false"
                   allowInput="false" />
            <span class="label" id="timeToLabel" style="margin-left:8px;"></span>
            <input id="itemCurveEndDate" class="mini-datepicker" style="width:150px;"
                   format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm"
                   showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false"
                   allowInput="false" />
            <button id="searchBtn" class="mini-button" plain="true" iconCls="search"
                    onclick="refreshCurveData()"></button>
            <span class="spacer"></span>
            <span id="curveVacuateCountLabel" class="count-info" style="display:none;">
                <span id="vacuateCountText"></span>：<span id="curveVacuateCountSpan">0</span>
            </span>
            <span id="curveTotalCountLabel" class="count-info" style="display:none;margin-left:8px;">
                <span id="totalCountText"></span>：<span id="curveTotalCountSpan">0</span>
            </span>
        </div>
        <div id="curveContainer" class="curve-body"></div>
    </div>

    <script>
        var context = '<%=path%>';
        var _params = null;

        // ================================================================
        // 1. 接收父页面参数
        // ================================================================
        function setData(params) {
            _params = params || {};
            initI18n();
            // 设置默认时间
            var startCmp = mini.get('itemCurveStartDate');
            var endCmp = mini.get('itemCurveEndDate');
            if (startCmp && _params.startDate) startCmp.setValue(_params.startDate);
            if (endCmp && _params.endDate) endCmp.setValue(_params.endDate);
            // 首次加载
            refreshCurveData();
        }

        // ================================================================
        // 2. 国际化
        // ================================================================
        function initI18n() {
            var R = _loginUserLanguageResource;
            document.getElementById('rangeLabel').textContent = R.range + '：';
            document.getElementById('timeToLabel').textContent = R.timeTo + '：';
            document.getElementById('vacuateCountText').textContent = R.vacuateCount;
            document.getElementById('totalCountText').textContent = R.totalCount;
            mini.get('searchBtn').setText(R.search);
        }

        // ================================================================
        // 3. 刷新曲线（由查询按钮和 setData 触发）
        // ================================================================
        function refreshCurveData() {
            var startCmp = mini.get('itemCurveStartDate');
            var endCmp = mini.get('itemCurveEndDate');
            var startDate = startCmp ? startCmp.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
            var endDate = endCmp ? endCmp.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
            loadCurveData(startDate, endDate);
        }

        // ================================================================
        // 4. 加载曲线数据
        // ================================================================
        function loadCurveData(startDate, endDate) {
            var container = document.getElementById('curveContainer');
            container.innerHTML = '';

            mini.mask({
                el: 'curveContainer',
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });

            $.ajax({
                url: context + '/historyQueryController/getItemHistoryCurveData',
                type: 'POST',
                data: {
                    deviceId: _params.deviceId,
                    deviceName: _params.deviceName,
                    deviceType: _params.deviceType,
                    calculateType: _params.calculateType,
                    itemName: _params.itemName,
                    itemCode: _params.itemCode,
                    itemType: _params.itemType,
                    itemResolutionMode: _params.itemResolutionMode,
                    startDate: startDate,
                    endDate: endDate,
                    hours: 'all'
                },
                dataType: 'json',
                timeout: 15000,
                success: function (result) {
                    mini.unmask('curveContainer');

                    // 更新记录数
                    if (result.vacuateCount !== undefined) {
                        document.getElementById('curveVacuateCountSpan').textContent = result.vacuateCount;
                        var l = document.getElementById('curveVacuateCountLabel');
                        if (l) l.style.display = 'inline';
                    }
                    if (result.totalCount !== undefined) {
                        document.getElementById('curveTotalCountSpan').textContent = result.totalCount;
                        var l2 = document.getElementById('curveTotalCountLabel');
                        if (l2) l2.style.display = 'inline';
                    }

                    if (!result || !result.list || result.list.length === 0) {
                        container.innerHTML = '<div class="loading-placeholder">'
                            + _loginUserLanguageResource.emptyMsg + '</div>';
                        return;
                    }
                    renderCurve(result);
                },
                error: function () {
                    mini.unmask('curveContainer');
                    container.innerHTML = '<div class="loading-placeholder error">'
                        + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        // ================================================================
        // 5. 组装 series 并渲染
        // ================================================================
        function renderCurve(result) {
            var data = result.list;
            var legendNames = result.curveItems || [];
            var legendCodes = result.curveItemCodes || [];
            var curveConf = result.curveConf || [];
            var graphicSet = result.graphicSet || {};
            var hiddenExceptionData = result.hiddenExceptionData || false;
            var defaultColors = ['#7cb5ec','#434348','#90ed7d','#f7a35c','#8085e9',
                                 '#f15c80','#e4d354','#2b908f','#f45b5b','#91e8e1'];

            var series = [];
            var yAxis = [];
            var colors = [];
            for (var i = 0; i < legendNames.length; i++) {
                var color = curveConf[i] && curveConf[i].color
                    ? '#' + curveConf[i].color
                    : defaultColors[i % 10];
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
                            if (graphicSet.History[g].yAxisMaxValue) {
                                maxVal = parseFloat(graphicSet.History[g].yAxisMaxValue);
                            }
                            if (graphicSet.History[g].yAxisMinValue) {
                                minVal = parseFloat(graphicSet.History[g].yAxisMinValue);
                            }
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
                    lineWidth: 1, tickWidth: 1, tickLength: 5
                };
                yAxis.push(axis);
                series.push(singleSeries);
            }

            var timeFormat = '%m-%d';
            if (data.length > 0 && result.minAcqTime && result.maxAcqTime
                && result.minAcqTime.split(' ')[0] === result.maxAcqTime.split(' ')[0]) {
                timeFormat = '%H:%M';
            }

            var title = (result.deviceName || '') + ' - ' + (_params.itemName || '');
            initHistoryCurveChart('curveContainer', series, yAxis, colors, title, '', timeFormat);
        }

        // ================================================================
        // 6. Highcharts 渲染
        // ================================================================
        function initHistoryCurveChart(divId, series, yAxis, colors, title, xtitle, timeFormat) {
            if ($("#" + divId).length === 0) return;
            new Highcharts.Chart({
                chart: {
                    renderTo: divId,
                    type: 'spline',
                    animation: false,
                    zoomType: 'xy',
                    zooming: { mouseWheel: { enabled: false } }
                },
                time: { timezoneOffset: new Date().getTimezoneOffset() },
                credits: { enabled: false },
                title: {
                    text: title,
                    style: { fontSize: (typeof chartTitleFontSize !== 'undefined' ? chartTitleFontSize : '14px') }
                },
                colors: colors,
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
                    enabled: true,
                    filename: title,
                    fallbackToExportServer: false,
                    buttons: {
                        contextButton: {
                            menuItems: [
                                'viewFullscreen', 'printChart', 'separator',
                                'downloadPNG', 'downloadJPEG', 'downloadSVG',
                                'separator', 'downloadCSV', 'downloadXLS'
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
                    layout: 'horizontal',
                    align: 'center',
                    verticalAlign: 'bottom',
                    enabled: false
                },
                series: series
            });
        }

        // ================================================================
        // 7. 初始化
        // ================================================================
        $(document).ready(function () {
            mini.parse();
        });
    </script>
</body>
</html>