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
        <button id="exportHistoryDetailBtn" class="mini-button"  plain="true" iconCls="export" onclick="exportDetailData()"></button>
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
	// 查看历史曲线（独立 JSP）
	// ================================================================
	function viewItemHistoryCurve(itemName, itemValue, cellInfo) {
	    var deviceId = _params.deviceId || '';
	    var deviceName = _params.deviceName || '';
	    var calculateType = _params.calculateType || 0;
	    var deviceType = _params.deviceType || '0';
	
	    mini.open({
	        title: _loginUserLanguageResource.trendCurve + ' - ' + itemName,
	        url: context + '/miniui-app/modules/historyQuery/deviceItemHistoryCurveWindow.jsp',
	        width: '80%',
	        height: '60%',
	        modal: true,
	        allowResize: true,
	        maxable: true,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            if (!iframe || !iframe.contentWindow) return;
	            iframe.contentWindow.setData({
	                deviceId:            deviceId,
	                deviceName:          deviceName,
	                deviceType:          deviceType,
	                calculateType:       calculateType,
	                itemName:            itemName,
	                itemCode:            cellInfo.column,
	                itemType:            cellInfo.type,
	                itemResolutionMode:  cellInfo.resolutionMode,
	                startDate:           _params.startDate || '',
	                endDate:             _params.endDate || ''
	            });
	        }
	    });
	}

	 // ================================================================
	 // 查看历史数据表（独立 JSP）
	 // ================================================================
	 function viewItemHistoryDataTable(itemName, itemValue, cellInfo) {
	     var deviceId = _params.deviceId || '';
	     var deviceName = _params.deviceName || '';
	     var calculateType = _params.calculateType || 0;
	     var startDate = _params.startDate || '';
	     var endDate = _params.endDate || '';
	
	     mini.open({
	         title: _loginUserLanguageResource.historyData + ' - ' + itemName,
	         url: context + '/miniui-app/modules/historyQuery/deviceItemHistoryDataWindow.jsp',
	         width: '50%',
	         height: '90%',
	         modal: true,
	         allowResize: true,
	         maxable: true,
	         onload: function () {
	             var iframe = this.getIFrameEl();
	             if (!iframe || !iframe.contentWindow) return;
	             iframe.contentWindow.setData({
	                 deviceId:            deviceId,
	                 deviceName:          deviceName,
	                 calculateType:       calculateType,
	                 itemName:            itemName,
	                 itemCode:            cellInfo.column,
	                 itemType:            cellInfo.type,
	                 itemResolutionMode:  cellInfo.resolutionMode,
	                 itemBitIndex:        cellInfo.bitIndex || '',
	                 startDate:           startDate,
	                 endDate:             endDate
	             });
	         }
	     });
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