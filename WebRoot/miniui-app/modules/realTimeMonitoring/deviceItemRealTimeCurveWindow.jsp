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
        .curve-body {
            width: 100%; height: 100%;
            min-height: 300px;
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
    <div id="curveContainer" class="curve-body"></div>

    <script>
        var context = '<%=path%>';
        var _params = null;

        // ================================================================
        // 1. 接收父页面参数
        // ================================================================
        function setData(params) {
            _params = params || {};
            loadCurveData();
        }

        // ================================================================
        // 2. 加载曲线数据
        // ================================================================
        function loadCurveData() {
            var container = document.getElementById('curveContainer');
            container.innerHTML = '';

            mini.mask({
                el: 'curveContainer',
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });

            $.ajax({
                url: context + '/realTimeMonitoringController/getItemRealTimeCurveData',
                type: 'POST',
                data: {
                    deviceName: _params.deviceName,
                    deviceId: _params.deviceId,
                    calculateType: _params.calculateType,
                    itemName: _params.itemName,
                    itemCode: _params.itemCode,
                    itemType: _params.itemType,
                    itemResolutionMode: _params.itemResolutionMode
                },
                dataType: 'json',
                timeout: 15000,
                success: function (result) {
                    mini.unmask('curveContainer');
                    if (!result || !result.list || result.list.length === 0) {
                        container.innerHTML =
                            '<div class="loading-placeholder">'
                            + _loginUserLanguageResource.emptyMsg + '</div>';
                        return;
                    }
                    renderCurve(result);
                },
                error: function () {
                    mini.unmask('curveContainer');
                    container.innerHTML =
                        '<div class="loading-placeholder error">'
                        + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        // ================================================================
        // 3. 组装 series 并渲染（参数与原版调用完全一致）
        // ================================================================
        function renderCurve(result) {
            var data = result.list;
            var legendName = result.curveItems || [];
            var title = (result.deviceName || '')
                + ':' + (legendName[0] || '').split('(')[0]
                + _loginUserLanguageResource.trendCurve;

            var seriesData = [];
            for (var j = 0; j < data.length; j++) {
                var ts = Date.parse(data[j].acqTime.replace(/-/g, '/'));
                var v = parseFloat(data[j].data);
                if (!isNaN(v)) seriesData.push([ts, v]);
            }

            var series = [{
                name: legendName[0] || '',
                data: seriesData,
                lineWidth: 2,
                marker: { enabled: true }
            }];

            var allPositive = true, allNegative = true;
            for (var k = 0; k < seriesData.length; k++) {
                var val = seriesData[k][1];
                if (val < 0) allPositive = false;
                if (val >= 0) allNegative = false;
            }
            var maxValue = allNegative ? 0 : null;
            var minValue = allPositive ? 0 : null;

            // ★ 与主 JS 中的调用一字不差
            initDeviceRealtimeMonitoringStockChartFn(
                series, undefined, 'curveContainer', title, '',
                _loginUserLanguageResource.time, legendName[0], ['#7cb5ec'],
                false, true, false, '%H:%M', maxValue, minValue, false
            );
        }

        // ================================================================
        // 4. Highstock 渲染（完整复制主 JS 的实现）
        // ================================================================
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

        // ================================================================
        // 5. 初始化
        // ================================================================
        $(document).ready(function () {
            mini.parse();
        });
    </script>
</body>
</html>