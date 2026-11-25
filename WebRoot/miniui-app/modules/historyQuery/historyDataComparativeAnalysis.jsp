<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String context = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f0f2f5; font-family:"Microsoft YaHei", Arial, sans-serif; }
        
        /* ============================================================
           	去除 MiniUI 默认的边框、Padding，让组件完美贴合 Splitter
           ============================================================ */
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; background: #fafafa !important; }
        .mini-panel-body { border: 0 !important; }
        .mini-splitter-border { border: 0 !important; }
        .mini-splitter-pane { padding: 0 !important; }
        .mini-splitter-handler { background: transparent !important; border-left: 1px solid #e8e8e8 !important; }
        .tab-toolbar-row1 {
            display: flex; align-items: center; flex-wrap: wrap;
            gap: 4px; padding: 4px 10px;
            background: #fafafa; border-bottom: 1px solid #e8e8e8;
        }
        
        /* 工具条统一背景与边距 */
        .mini-panel-toolbar { background: #fafafa !important; border-bottom: 1px solid #e8e8e8 !important; padding: 4px 8px !important; }
    </style>
</head>
<body>
    <div class="mini-panel" style="width:100%;height:100%;" showHeader="false" showToolbar="true" showCloseButton="false" showCollapseButton="false" bodyStyle="padding:0;border:0;">
        <div property="toolbar">
            <table style="width:100%;border-collapse:collapse;">
                <tr>
                    <td class="tab-toolbar-row1">
                        <span id="lblRange"></span>
                        <input id="startDate" class="mini-datepicker" format="yyyy-MM-dd" style="width:130px;" />
                        <span id="lblHour"></span>
                        <input id="startHour" class="mini-spinner" minValue="0" maxValue="23" value="0" style="width:48px;" />
                        <span id="lblMinute"></span>
                        <input id="startMinute" class="mini-spinner" minValue="0" maxValue="59" value="0" style="width:48px;" />
                        <span class="sep" id="lblTimeTo"></span>
                        <input id="endDate" class="mini-datepicker" format="yyyy-MM-dd" style="width:130px;" />
                        
                        <span id="lblEndHour"></span>
                        <input id="endHour" class="mini-spinner" minValue="0" maxValue="23" value="23" style="width:48px;" />
                        <span id="lblEndMinute"></span>
                        <input id="endMinute" class="mini-spinner" minValue="0" maxValue="59" value="59" style="width:48px;" />
                        <button id="btnSearch" class="mini-button" plain="true" iconCls="search" onclick="getComparativeAnalysisCurveData()"></button>
                    </td>
                    <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                    </td>
                </tr>
            </table>
        </div>
        <!-- 第一层：左 45%（可左收） | 右 55% 曲线 -->
        <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">

            <!-- 左窗格：不要再写 class="mini-panel" 和 bodyStyle -->
            <div size="45%" showCollapseButton="true" collapseDirection="left" style="border:0;">

                <!-- 第二层：设备 40%（可左收） | 字段 60% -->
                <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                    <div size="40%" showCollapseButton="true" collapseDirection="left" style="border:0;">
                        <div id="deviceListPanel" class="mini-panel" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
                            <div id="deviceListGrid" class="mini-datagrid" style="width:100%;height:100%;" 
                            	multiSelect="true" 
                            	showPager="false" 
                            	showEmptyText="true"
                            	idField="deviceId"
                         		dataField="totalRoot"
                         		totalField="totalCount"
                            	allowAlternating="true" 
                            	onbeforeload="onDeviceGridBeforeLoad"
                         		onload="onDeviceGridLoad">
                                <div property="columns"></div>
                        		<div property="emptyText" class="empty-msg"></div>
                            </div>
                        </div>
                    </div>
                    <div size="60%" showCollapseButton="false" style="border:0;">
                        <div id="fieldListPanel" class="mini-panel" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
                            <div id="fieldListGrid" class="mini-datagrid" style="width:100%;height:100%;" 
                            	multiSelect="true" 
                            	showPager="false" 
                            	showEmptyText="true"
                            	idField="deviceId"
                         		dataField="totalRoot"
                         		totalField="totalCount"
                            	allowAlternating="true" 
                            	onbeforeload="onFieldGridBeforeLoad"
                         		onload="onFieldGridLoad">
                                <div property="columns"></div>
                        		<div property="emptyText" class="empty-msg"></div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <!-- 右窗格：曲线 -->
            <div size="55%" showCollapseButton="false" style="border:0;">
                <div id="curvePanel" class="mini-panel" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
                    <div property="toolbar" style="padding:2px 8px;">
                        <span id="curveHint" style="color:#888;font-style:italic;font-size:12px;"></span>
                    </div>
                    <div id="curveDiv" style="width:100%;height:100%;"></div>
                </div>
            </div>

        </div>
    </div>

    

    <script type="text/javascript">
        var context = '<%=context%>';
        var _params = null;
        
        /* ============ 全局配置（持久化） ============ */
        var historyDataComparativeAnalysisCurveConfig = { axis: {}, series: {} };
        var historyDataComparativeAnalysisLastResult = null;
        var historyDataComparativeAnalysisTimeFormat = '%m-%d';
        var _curveChart = null;
        
        function setData(params) {
        	_params = params || {};
        	initI18n();
        	/* ============ 初始化日期 ============ */
            mini.get('startDate').setValue(new Date());
            mini.get('endDate').setValue(new Date());
            
            loadDeviceList();
            loadFieldList();
        }
        
        function setCurvePanelTitle(title){
        	var curvePanel= mini.get('curvePanel');
        	if (curvePanel) curvePanel.setTitle(title);
        }
        
        function initI18n() {
        	var deviceListPanel  = mini.get('deviceListPanel');
            var fieldListPanel   = mini.get('fieldListPanel');
            var curvePanel       = mini.get('curvePanel');

            /* ============ 初始化标题 / 文本 ============ */
            if (deviceListPanel) deviceListPanel.setTitle(_loginUserLanguageResource.deviceList);
            if (fieldListPanel) fieldListPanel.setTitle(_loginUserLanguageResource.dataColumn);
            if (curvePanel) curvePanel.setTitle(_loginUserLanguageResource.curve);
            document.getElementById('lblRange').textContent = _loginUserLanguageResource.range+":";
            document.getElementById('lblHour').textContent = _loginUserLanguageResource.hour+":";
            document.getElementById('lblMinute').textContent = _loginUserLanguageResource.minute+":";
            
            document.getElementById('lblTimeTo').textContent = _loginUserLanguageResource.timeTo+":";
            document.getElementById('lblEndHour').textContent = _loginUserLanguageResource.hour+":";
            document.getElementById('lblEndMinute').textContent = _loginUserLanguageResource.minute+":";
            
            
            document.getElementById('curveHint').innerHTML ='(' + _loginUserLanguageResource.doubleClickToConfigCurveStyle + ')';
            
            var btnSearch = mini.get('btnSearch');
            if (btnSearch) {
            	btnSearch.setText(_loginUserLanguageResource.search);
            }
            var deviceListGrid  = mini.get('deviceListGrid');
            var fieldListGrid  = mini.get('fieldListGrid');
            if (deviceListGrid) deviceListGrid.setEmptyText(_loginUserLanguageResource.emptyMsg);
            if (fieldListGrid) fieldListGrid.setEmptyText(_loginUserLanguageResource.emptyMsg);
        }
        
        function loadDeviceList() {
            var grid = mini.get('deviceListGrid');
            if (!grid) return;
            if (!grid.getUrl()) {
                grid.setUrl(context + '/historyQueryController/getComparativeAnalysisDeviceList');
            }
            grid.load();
        }
        
        function onDeviceGridBeforeLoad(e) {
            var params = e.params || {};
            params.orgId = _params.orgId;
            params.deviceType = _params.deviceType;
            e.params = params;
        }
        
        function onDeviceGridLoad(e) {
            var grid = e.sender;
            var result = e.result || {};

            // ★ 首次加载时动态创建列（只创建一次）
            if (!grid._columnsCreated) {
                createDeviceGridColumns(grid, result);
                grid._columnsCreated = true;
            }
        }
        
        function createDeviceGridColumns(grid, result) {
        	var columns = [];
        	columns.push({
                type: 'checkcolumn',
                width: 40,
                header: '',
                headerAlign: 'center',
                align: 'center'
            });
        	columns.push({
                type: 'indexcolumn',
                width: 50,
                headerAlign: 'center',
                align: 'center',
                header: _loginUserLanguageResource.idx
            });
        	columns.push({
                field: 'deviceName',
                header: _loginUserLanguageResource.deviceName,
                headerAlign: 'center',
                align: 'center',
                width: 120
            });
        	grid.setColumns(columns);
        }
        
        function loadFieldList() {
            var grid = mini.get('fieldListGrid');
            if (!grid) return;
            if (!grid.getUrl()) {
                grid.setUrl(context + '/historyQueryController/getComparativeAnalysisItemList');
            }
            grid.load();
        }
        
        function onFieldGridBeforeLoad(e) {
            var params = e.params || {};
            params.orgId = _params.orgId;
            params.deviceType = _params.deviceType;
            e.params = params;
        }
        
        function onFieldGridLoad(e) {
            var grid = e.sender;
            var result = e.result || {};

            // ★ 首次加载时动态创建列（只创建一次）
            if (!grid._columnsCreated) {
                createFieldGridColumns(grid, result);
                grid._columnsCreated = true;
            }
        }
        
        function createFieldGridColumns(grid, result) {
        	var columns = [];
        	columns.push({
                type: 'checkcolumn',
                width: 40,
                header: '',
                headerAlign: 'center',
                align: 'center'
            });
        	columns.push({
                type: 'indexcolumn',
                width: 50,
                headerAlign: 'center',
                align: 'center',
                header: _loginUserLanguageResource.idx
            });
        	columns.push({
                field: 'itemName',
                header: _loginUserLanguageResource.deviceName,
                headerAlign: 'center',
                align: 'center',
                width: 120
            });
        	columns.push({
                field: 'itemUnit',
                header: _loginUserLanguageResource.deviceName,
                headerAlign: 'center',
                align: 'center',
                width: 80
            });
        	columns.push({
                field: 'dataSourceName',
                header: _loginUserLanguageResource.deviceName,
                headerAlign: 'center',
                align: 'center',
                width: 80
            });
        	grid.setColumns(columns);
        }

        /* ============ 工具函数 ============ */
        function getDateAndTime(date, hour, minute, second) {
            return date + ' ' + hour + ':' + minute + ':' + second;
        }

        function historyDataComparativeAnalysisHexToHsl(hex) {
            if (!hex) return { h: 210, s: 70 };
            hex = hex.replace('#', '');
            if (hex.length === 3) hex = hex.split('').map(function (c) { return c + c; }).join('');
            var r = parseInt(hex.substr(0, 2), 16) / 255;
            var g = parseInt(hex.substr(2, 2), 16) / 255;
            var b = parseInt(hex.substr(4, 2), 16) / 255;
            var max = Math.max(r, g, b), min = Math.min(r, g, b);
            var h, s, l = (max + min) / 2;
            if (max === min) { h = s = 0; }
            else {
                var d = max - min;
                s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
                switch (max) {
                    case r: h = ((g - b) / d + (g < b ? 6 : 0)) / 6; break;
                    case g: h = ((b - r) / d + 2) / 6; break;
                    case b: h = ((r - g) / d + 4) / 6; break;
                }
            }
            return { h: Math.round(h * 360), s: Math.round(s * 100) };
        }

        function historyDataComparativeAnalysisHslToHex(h, s, l) {
            l /= 100;
            var a = s * Math.min(l, 1 - l) / 100;
            function f(n) {
                var k = (n + h / 30) % 12;
                var color = l - a * Math.max(Math.min(k - 3, 9 - k, 1), -1);
                return Math.round(255 * color).toString(16).padStart(2, '0');
            }
            return f(0) + f(8) + f(4);
        }

        function historyDataComparativeAnalysisContrastColor(hex) {
            if (!hex) return '#000000';
            hex = hex.replace('#', '');
            if (hex.length === 3) hex = hex.split('').map(function (c) { return c + c; }).join('');
            var r = parseInt(hex.substr(0, 2), 16);
            var g = parseInt(hex.substr(2, 2), 16);
            var b = parseInt(hex.substr(4, 2), 16);
            var brightness = (r * 299 + g * 587 + b * 114) / 1000;
            return brightness > 128 ? '#000000' : '#ffffff';
        }

        /* ============ 查询数据 ============ */
        function getComparativeAnalysisCurveData() {
        	var deviceListGrid  = mini.get('deviceListGrid');
            var fieldListGrid  = mini.get('fieldListGrid');
        	
            var deviceRecords = deviceListGrid.getSelecteds();
            var itemRecords   = fieldListGrid.getSelecteds();

            if (!deviceRecords.length || !itemRecords.length) {
                mini.showMessageBox({
                    title: _loginUserLanguageResource.tip,
                    message: _loginUserLanguageResource.checkOne,
                    buttons: ['ok'],
                    iconCls: 'mini-messagebox-warning'
                });
                return;
            }

            var deviceIds = [], deviceNames = [];
            deviceRecords.forEach(function (r) {
                deviceIds.push(r.deviceId);
                deviceNames.push(r.deviceName);
            });

            var items = [], seenKey = {};
            itemRecords.forEach(function (r) {
                var itemColumn = r.itemColumn;
                var dataSource = r.dataSource;
                if (!isNotVal(itemColumn)) return;
                var key = itemColumn + '|' + dataSource;
                if (!seenKey[key]) {
                    seenKey[key] = true;
                    items.push({
                        itemName: r.itemName,
                        itemColumn: itemColumn,
                        itemUnit: isNotVal(r.itemUnit) ? r.itemUnit : '',
                        dataSource: dataSource
                    });
                }
            });
            if (!items.length) {
                mini.showMessageBox({
                    title: _loginUserLanguageResource.tip,
                    message: _loginUserLanguageResource.checkOne,
                    buttons: ['ok'],
                    iconCls: 'mini-messagebox-warning'
                });
                return;
            }

            var startDate   = mini.get('startDate').getFormValue();
            var startHour   = mini.get('startHour').getValue()   || 0;
            var startMinute = mini.get('startMinute').getValue() || 0;
            var endDate     = mini.get('endDate').getFormValue();
            var endHour     = mini.get('endHour').getValue()     || 23;
            var endMinute   = mini.get('endMinute').getValue()   || 59;

            var mask = mini.loading(_loginUserLanguageResource.loadingData, '');

            $.ajax({
                method: 'POST',
                url: context + '/historyQueryController/getComparativeAnalysisCurveData',
                data: {
                    deviceNames: deviceNames.join(','),
                    deviceIds: deviceIds.join(','),
                    items: JSON.stringify(items),
                    startDate: getDateAndTime(startDate, startHour, startMinute, 0),
                    endDate:   getDateAndTime(endDate,   endHour,   endMinute,   0)
                },
                success: function (response) {
                    mini.hideMessageBox(mask);
                    
                    var curvePanelTitle=_loginUserLanguageResource.curve + 
       	           '<span style="font-size: 12px; font-weight: normal; color: #888; margin-left: 10px; font-style: italic;">' +
     	           '('+_loginUserLanguageResource.doubleClickToConfigCurveStyle+')';
                    setCurvePanelTitle(curvePanelTitle);
                    
                    
                    var result = typeof response === 'string' ? JSON.parse(response) : response;

                    if (String(startDate).split(' ')[0] === String(endDate).split(' ')[0]) {
                        historyDataComparativeAnalysisTimeFormat = '%H:%M';
                    } else {
                        historyDataComparativeAnalysisTimeFormat = '%m-%d';
                    }

                    historyDataComparativeAnalysisLastResult = result;
                    renderHistoryComparativeAnalysisCurve(result);
                },
                error: function () {
                    mini.hideMessageBox(mask);
                    setCurvePanelTitle(_loginUserLanguageResource.curve);
                    mini.showMessageBox({
                        title: _loginUserLanguageResource.error,
                        message: _loginUserLanguageResource.ajaxError,
                        buttons: ['ok'],
                        iconCls: 'mini-messagebox-error'
                    });
                }
            });
        }

        /* ============ 绘制图表 ============ */
        function renderHistoryComparativeAnalysisCurve(result) {
		    if (!result) return;
		    var fieldList = result.items || [];
		    var timeFormat = historyDataComparativeAnalysisTimeFormat;
		    var BASE_HUES = [210, 0, 120, 45, 280, 190, 330, 90, 250, 20];
		    var DASH_STYLES = ['Solid', 'Dash', 'Dot', 'DashDot', 'ShortDash', 'ShortDot'];
		    var MAX_LIGHTNESS_LEVELS = 4;
		    var L_MIN = 35, L_MAX = 72;
		
		    var sortOrigIndexMap = {};
		    for (var i = 0; i < fieldList.length; i++) {
		        sortOrigIndexMap[fieldList[i].itemColumn + "|" + fieldList[i].dataSource] = i;
		    }
		
		    // 解析所有轴的配置
		    var axisOrder = [];
		    for (var i = 0; i < fieldList.length; i++) {
		        var sort = fieldList[i].itemColumn + "|" + fieldList[i].dataSource;
		        var cfg = historyDataComparativeAnalysisCurveConfig.axis[sort] || {};
		        var pos = (cfg.axisSort !== undefined && cfg.axisSort !== '' && cfg.axisSort !== null) ? parseInt(cfg.axisSort) : (i + 1); 
		        var opposite = (cfg.yAxisOpposite !== undefined && cfg.yAxisOpposite !== null) ? (cfg.yAxisOpposite === true || cfg.yAxisOpposite === 'true') : (i % 2 === 1);
		        var unitSuffix = isNotVal(fieldList[i].itemUnit) ? ("(" + fieldList[i].itemUnit + ")") : "";
		        axisOrder.push({
		            sort: sort, pos: pos, origIndex: i, itemName: fieldList[i].itemName + unitSuffix, opposite: opposite, cfg: cfg
		        });
		    }
		
		    // 分离左右轴
		    var leftAxes = [];
		    var rightAxes = [];
		    for (var i = 0; i < axisOrder.length; i++) {
		        if (axisOrder[i].opposite) rightAxes.push(axisOrder[i]);
		        else leftAxes.push(axisOrder[i]);
		    }
		    leftAxes.sort(function(a, b) { return a.pos !== b.pos ? a.pos - b.pos : a.origIndex - b.origIndex; });
		    rightAxes.sort(function(a, b) { return a.pos !== b.pos ? a.pos - b.pos : a.origIndex - b.origIndex; });
		
		    // 构建 Y 轴配置
		    var yAxis = [];
		    var sortAxisMap = {};
		
		    for (var i = leftAxes.length - 1; i >= 0; i--) {
		        var f = leftAxes[i];
		        var baseColor = f.cfg.axisColor ? '#' + f.cfg.axisColor : 'hsl(' + BASE_HUES[f.origIndex % BASE_HUES.length] + ',70%,50%)';
		        sortAxisMap[f.sort] = yAxis.length;
		        yAxis.push({
		            title: { text: f.itemName, style: { color: baseColor } },
		            labels: { style: { color: baseColor } },
		            lineWidth: 1, tickWidth: 1, tickLength: 5, opposite: false
		        });
		    }
		    for (var i = 0; i < rightAxes.length; i++) {
		        var f = rightAxes[i];
		        var baseColor = f.cfg.axisColor ? '#' + f.cfg.axisColor : 'hsl(' + BASE_HUES[f.origIndex % BASE_HUES.length] + ',70%,50%)';
		        sortAxisMap[f.sort] = yAxis.length;
		        yAxis.push({
		            title: { text: f.itemName, style: { color: baseColor } },
		            labels: { style: { color: baseColor } },
		            lineWidth: 1, tickWidth: 1, tickLength: 5, opposite: true
		        });
		    }
		
		    // 同字段设备数统计
		    var fieldDeviceCount = {};
		    for (var i = 0; i < result.list.length; i++) {
		        var s = result.list[i].itemColumn + "|" + result.list[i].dataSource;
		        fieldDeviceCount[s] = (fieldDeviceCount[s] || 0) + 1;
		    }
		
		    // series 组装
		    var series = [];
		    var fieldDeviceIndex = {};
		
		    function buildSeriesForAxis(axisInfo) {
		        var sort = axisInfo.sort;
		        var axisIdx = sortAxisMap[sort];
		
		        for (var i = 0; i < result.list.length; i++) {
		            var row = result.list[i];
		            var rowSort = row.itemColumn + "|" + row.dataSource;
		            if (rowSort !== sort) continue;
		
		            if (fieldDeviceIndex[rowSort] === undefined) fieldDeviceIndex[rowSort] = 0;
		            var devIdx = fieldDeviceIndex[rowSort]++;
		            var devCount = fieldDeviceCount[rowSort] || 1;
		            
		            var seriesKey = rowSort + '_' + row.deviceId;
		            var sCfg = historyDataComparativeAnalysisCurveConfig.series[seriesKey] || {};
		
		            // 颜色
		            var color;
		            if (sCfg.color) {
		                color = '#' + sCfg.color;
		            } else {
		                var aCfg = historyDataComparativeAnalysisCurveConfig.axis[rowSort] || {};
		                var hue, sat;
		                if (aCfg.axisColor) {
		                    var hsl = historyDataComparativeAnalysisHexToHsl(aCfg.axisColor);
		                    hue = hsl.h; sat = hsl.s;
		                } else {
		                    hue = BASE_HUES[sortOrigIndexMap[rowSort] % BASE_HUES.length];
		                    sat = 70;
		                }
		                var levelCount = Math.min(devCount, MAX_LIGHTNESS_LEVELS);
		                var levelIdx = devIdx % levelCount;
		                var lightness = (levelCount <= 1) ? 50 : (L_MIN + (L_MAX - L_MIN) * (levelIdx / (levelCount - 1)));
		                color = 'hsl(' + hue + ',' + sat + '%,' + Math.round(lightness) + '%)';
		            }
		
		            // 线宽
		            var lineWidth = 2;
		            if (sCfg.lineWidth !== undefined && sCfg.lineWidth !== '' && sCfg.lineWidth !== null) {
		                lineWidth = parseFloat(sCfg.lineWidth);
		            }
		
		            // 线型
		            var dashStyle;
		            if (sCfg.dashStyle) {
		                dashStyle = sCfg.dashStyle;
		            } else if (devCount > MAX_LIGHTNESS_LEVELS) {
		                dashStyle = DASH_STYLES[Math.floor(devIdx / MAX_LIGHTNESS_LEVELS) % DASH_STYLES.length];
		            } else {
		                dashStyle = 'Solid';
		            }
		
		            var unitSuffix = isNotVal(row.itemUnit) ? ("(" + row.itemUnit + ")") : "";
		            var singleSeries = {
		                name: row.deviceName + '.' + row.itemName + unitSuffix,
		                color: color,
		                dashStyle: dashStyle,
		                sort: rowSort,
		                deviceId: row.deviceId,
		                type: 'spline',
		                marker: { enabled: false },
		                yAxis: axisIdx,
		                lineWidth: lineWidth,
		                data: []
		            };
		
		            for (var j = 0; j < row.acqTime.length; j++) {
		                var ts = Date.parse(row.acqTime[j].replace(/-/g, '/'));
		                var v = parseFloat(row.data[j]);
		                singleSeries.data.push([ts, isNaN(v) ? null : v]);
		            }
		            series.push(singleSeries);
		        }
		    }
		
		    var allAxesSorted = leftAxes.concat(rightAxes);
		    allAxesSorted.sort(function(a, b) { return a.pos !== b.pos ? a.pos - b.pos : a.origIndex - b.origIndex; });
		    for (var i = 0; i < allAxesSorted.length; i++) {
		        buildSeriesForAxis(allAxesSorted[i]);
		    }
		
		    // 绘制图表
		    var xTitle = _loginUserLanguageResource.acqTime;
		    initComparativeAnalysisCurveChartFn(
		        series, 'curveDiv', _loginUserLanguageResource.comparativeCurve, '', xTitle, yAxis, true, timeFormat
		    );
		
		    // 绑定双击事件
		    var container = document.getElementById('curveDiv');
		    if (container && !container._dblclickBound) {
		        container._dblclickBound = true;
		        container.addEventListener('dblclick', function () {
		            if (!historyDataComparativeAnalysisLastResult) return;
		            var exist = mini.get('HistoryDataComparativeAnalysisCurveConfigWindow');
		            if (exist) exist.destroy();
		            mini.open({
		                title: _loginUserLanguageResource.curveProperty,
		                id:'HistoryDataComparativeAnalysisCurveConfigWindow',
		                url: context + '/miniui-app/modules/historyQuery/historyDataComparativeAnalysisCurveConfig.jsp',
		                width: 520,
		                height: '75%',
		                modal: true,
		                allowResize: true,
		                onload: function () {
		                	var iframe = this.getIFrameEl();
		                    if (iframe && iframe.contentWindow && iframe.contentWindow.setParentWin) {
		                        iframe.contentWindow.setParentWin(window);  // ★ 把本页面 window 传给子页面
		                    }
		                }
		            });
		        });
		    }
		}
        
        function initComparativeAnalysisCurveChartFn(series, divId, title, subtitle, xtitle, yAxis,legend,timeFormat) {
        	if($("#"+divId)!=undefined && $("#"+divId)[0]!=undefined){
        		_curveChart = new Highcharts.Chart({
        	        chart: {
        	            renderTo: divId, type: 'spline', shadow: false, borderWidth: 0, zoomType: 'xy',
        	            zooming: { mouseWheel: { enabled: false } }
        	        },
        	        time: { timezoneOffset: new Date().getTimezoneOffset() },
        	        credits: { enabled: false },
        	        title: { text: title, style: { fontSize: chartTitleFontSize } },
        	        subtitle: { text: subtitle },
        	        xAxis: {
        	            type: 'datetime', title: { text: xtitle },
        	            labels: {
        	                formatter: function () { return this.axis.chart.time.dateFormat(timeFormat, this.value); },
        	                autoRotation:true, rotation: -45
        	            }
        	        },
        	        yAxis: yAxis,
        	        tooltip: {
        	            crosshairs: true, shared: true, style: { color: '#333333', fontSize: '12px', padding: '8px' },
        	            dateTimeLabelFormats: {
        	                millisecond: '%Y-%m-%d %H:%M:%S.%L', second: '%Y-%m-%d %H:%M:%S', minute: '%Y-%m-%d %H:%M',
        	                hour: '%Y-%m-%d %H', day: '%Y-%m-%d', week: '%m-%d', month: '%Y-%m', year: '%Y'
        	            }
        	        },
        	        exporting: {
        	            enabled: true, fallbackToExportServer: false, filename: title,
        	            buttons: {
        	    	    	contextButton: {
        	    	    		menuItems: ['viewFullscreen','printChart','separator','downloadPNG','downloadJPEG','downloadSVG','separator','downloadXLS']
        	    	    		}
        	    	    },
        	            sourceWidth: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetWidth:null,
        	            sourceHeight: $("#"+divId)[0]!=undefined?$("#"+divId)[0].offsetHeight:null
        	        },
        	        plotOptions: {
        	            spline: { fillOpacity: 0.3, shadow: true }
        	        },
        	        legend: {
        	            layout: 'horizontal', align: 'center', verticalAlign: 'bottom',
        	            enabled: legend, borderWidth: 0, itemHiddenStyle: { textDecoration: 'none' }
        	        },
        	        series: series
        	    });
        	}
        };
        
        function getHistoryDataComparativeAnalysisLastResult(){
        	return historyDataComparativeAnalysisLastResult;
        }

        $(document).ready(function () {
            mini.parse();
        });
        
        window.setData = setData;
        
        

        // 暴露给配置窗口
        window.getHistoryDataComparativeAnalysisLastResult=getHistoryDataComparativeAnalysisLastResult;
        window.renderHistoryComparativeAnalysisCurve  = renderHistoryComparativeAnalysisCurve;
        window.historyDataComparativeAnalysisCurveConfig = historyDataComparativeAnalysisCurveConfig;
        window.historyDataComparativeAnalysisHexToHsl   = historyDataComparativeAnalysisHexToHsl;
        window.historyDataComparativeAnalysisHslToHex   = historyDataComparativeAnalysisHslToHex;
        window.historyDataComparativeAnalysisContrastColor = historyDataComparativeAnalysisContrastColor;
    </script>
</body>
</html>