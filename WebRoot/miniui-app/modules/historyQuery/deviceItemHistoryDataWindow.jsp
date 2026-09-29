<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>历史数据</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #fff;
        }
        .data-wrapper {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
        }
        .data-toolbar {
            flex-shrink: 0;
            display: flex; align-items: center; flex-wrap: wrap; gap: 4px;
            padding: 4px 10px;
            background: #fff;
            border-bottom: 1px solid #e8e8e8;
        }
        .data-toolbar .label { font-size: 12px; color: #333; }
        .data-toolbar .spacer { flex: 1; }
        .data-toolbar .count-info { font-size: 12px; color: #999; margin-left: 8px; }
        .data-body {
            flex: 1;
            min-height: 0;
            overflow: hidden;
            background: #fff;
        }
        .data-body .mini-datagrid {
            width: 100%; height: 100%;
        }
    </style>
</head>
<body>
    <div class="data-wrapper">
        <div class="data-toolbar">
            <span class="label" id="rangeLabel"></span>
            <input id="itemHistoryDataStartDate" class="mini-datepicker" style="width:150px;"
                   format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm"
                   showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false"
                   allowInput="false" />
            <span class="label" id="timeToLabel" style="margin-left:8px;"></span>
            <input id="itemHistoryDataEndDate" class="mini-datepicker" style="width:150px;"
                   format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm"
                   showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false"
                   allowInput="false" />
            <button id="searchBtn" class="mini-button" plain="true" iconCls="search"
                    onclick="refreshHistoryDataGrid()"></button>
            <span class="spacer"></span>
            <button id="exportBtn" class="mini-button" plain="true" iconCls="export"
                    onclick="exportItemHistoryData()"></button>
            <span class="count-info">
                <span id="totalCountText"></span>：<span id="historyDataTotalCountSpan">0</span>
            </span>
        </div>
        <div class="data-body">
            <div id="itemHistoryDataGrid" class="mini-datagrid"
                 style="width:100%;height:100%;"
                 showPager="true"
                 pageSize="100"
                 allowResize="true"
                 allowAlternating="true"
                 virtualScroll="false"
                 url="<%=path%>/historyQueryController/getItemHistoryData"
                 dataField="totalRoot"
                 totalField="totalCount"
                 onbeforeload="onDataGridBeforeLoad"
                 onload="onDataGridLoad">
                <div property="columns">
                    <div type="indexcolumn" width="70" headerAlign="center" align="center"></div>
                    <div field="acqTime" name="acqTime"
                         width="50%" headerAlign="center" align="center"
                         dateFormat="yyyy-MM-dd HH:mm:ss"></div>
                    <div field="data" name="data"
                         width="50%" headerAlign="center" align="center"></div>
                </div>
            </div>
        </div>
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
            var startCmp = mini.get('itemHistoryDataStartDate');
            var endCmp = mini.get('itemHistoryDataEndDate');
            if (startCmp && _params.startDate) startCmp.setValue(_params.startDate);
            if (endCmp && _params.endDate) endCmp.setValue(_params.endDate);

            // 更新列头（值列用 itemName）
            var grid = mini.get('itemHistoryDataGrid');
            if (grid) {
            	grid.setPageSize(parseInt(_defaultPageSize, 10));
                grid.updateColumn('acqTime', { header: _loginUserLanguageResource.acqTime });
                grid.updateColumn('data',    { header: _params.itemName || '' });
                grid.load();
            }
        }

        // ================================================================
        // 2. 国际化
        // ================================================================
        function initI18n() {
            var R = _loginUserLanguageResource;
            document.getElementById('rangeLabel').textContent = R.range + '：';
            document.getElementById('timeToLabel').textContent = R.timeTo + '：';
            document.getElementById('totalCountText').textContent = R.totalCount;
            mini.get('searchBtn').setText(R.search);
            mini.get('exportBtn').setText(R.exportData);
        }

        // ================================================================
        // 3. 加载前事件：传参
        // ================================================================
        function onDataGridBeforeLoad(e) {
            var grid = mini.get('itemHistoryDataGrid');
            var params = e.params || {};

            var pageIndex = params.pageIndex || 0;
            var pageSize = params.pageSize || (typeof _defaultPageSize !== 'undefined' ? _defaultPageSize : 25);
            params.start = pageIndex * pageSize;
            params.limit = pageSize;

            // 从工具条获取时间范围
            var start = mini.get('itemHistoryDataStartDate');
            var end = mini.get('itemHistoryDataEndDate');
            params.startDate = start ? start.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
            params.endDate = end ? end.getFormValue('yyyy-MM-dd HH:mm:ss') : '';

            // 固定业务参数
            params.deviceId = _params.deviceId;
            params.deviceName = _params.deviceName;
            params.calculateType = _params.calculateType;
            params.itemName = _params.itemName;
            params.itemCode = _params.itemCode;
            params.itemType = _params.itemType;
            params.itemResolutionMode = _params.itemResolutionMode;
            params.itemBitIndex = _params.itemBitIndex || '';

            params.totalCount = grid ? (grid.getTotalCount() || 0) : 0;

            e.params = params;
        }

        // ================================================================
        // 4. 加载完成事件
        // ================================================================
        function onDataGridLoad(e) {
            var result = e.result;
            if (result && result.totalCount !== undefined) {
                var sp = document.getElementById('historyDataTotalCountSpan');
                if (sp) sp.textContent = result.totalCount;
            }
        }

        // ================================================================
        // 5. 刷新
        // ================================================================
        function refreshHistoryDataGrid() {
            var grid = mini.get('itemHistoryDataGrid');
            if (grid) grid.load();
        }

        // ================================================================
        // 6. 导出
        // ================================================================
        function exportItemHistoryData() {
            if (!_params.deviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }

            var startCmp = mini.get('itemHistoryDataStartDate');
            var endCmp = mini.get('itemHistoryDataEndDate');
            var startDate = startCmp ? startCmp.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
            var endDate = endCmp ? endCmp.getFormValue('yyyy-MM-dd HH:mm:ss') : '';

            var timestamp = new Date().getTime();
            var key = 'exportItemHistoryData_' + _params.deviceId + '_' + timestamp;
            var url = context + '/historyQueryController/exportItemHistoryData';
            var param = '&deviceId=' + _params.deviceId +
                        '&deviceName=' + encodeURIComponent(encodeURIComponent(_params.deviceName || '')) +
                        '&calculateType=' + (_params.calculateType || 0) +
                        '&itemName=' + encodeURIComponent(encodeURIComponent(_params.itemName || '')) +
                        '&itemCode=' + (_params.itemCode || '') +
                        '&itemType=' + (_params.itemType || 0) +
                        '&itemResolutionMode=' + (_params.itemResolutionMode || 0) +
                        '&itemBitIndex=' + (_params.itemBitIndex || '') +
                        '&startDate=' + encodeURIComponent(startDate) +
                        '&endDate=' + encodeURIComponent(endDate) +
                        '&key=' + key;

            if (typeof exportDataMask === 'function') {
                exportDataMask(key, document.body, _loginUserLanguageResource.loadingData);
            }
            if (typeof openExcelWindow === 'function') {
                openExcelWindow(url + '?flag=true' + param);
            } else {
                document.location.href = url + '?flag=true' + param;
            }
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