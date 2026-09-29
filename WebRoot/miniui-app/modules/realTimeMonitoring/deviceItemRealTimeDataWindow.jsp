<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>动态数据</title>
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
            padding: 4px 8px;
            background: #f5f5f5;
            border-bottom: 1px solid #ddd;
            display: flex; align-items: center; gap: 6px;
        }
        .data-toolbar .label {
            font-size: 12px; color: #333;
        }
        .data-body {
            flex: 1;
            overflow: hidden;
            min-height: 0;
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
            <span class="label" id="totalCountLabel"></span>
            <span style="flex:1;"></span>
            <button id="exportBtn" class="mini-button" plain="true" iconCls="export"
                    onclick="exportData()"></button>
        </div>
        <div class="data-body">
            <div id="itemRealtimeDataGrid" class="mini-datagrid"
                 style="width:100%;height:100%;"
                 showPager="false" showColumns="true"
                 allowCellSelect="true" allowCellWrap="false"
                 allowResize="true" virtualScroll="false" allowAlternating="true"
                 url="<%=path%>/realTimeMonitoringController/getItemRealTimeData"
                 dataField="totalRoot" totalField="totalCount"
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

            var R = _loginUserLanguageResource;
            mini.get('exportBtn').setText(R.exportData);

            var grid = mini.get('itemRealtimeDataGrid');
            if (grid) {
                grid.updateColumn('acqTime', { header: R.acqTime });
                grid.updateColumn('data',    { header: _params.itemName || '' });
                grid.load();
            }
        }

        // ================================================================
        // 2. 加载前事件：传参
        // ================================================================
        function onDataGridBeforeLoad(e) {
            var params = e.params || {};
            params.deviceName          = _params.deviceName;
            params.deviceId            = _params.deviceId;
            params.calculateType       = _params.calculateType;
            params.itemName            = _params.itemName;
            params.itemCode            = _params.itemCode;
            params.itemType            = _params.itemType;
            params.itemResolutionMode  = _params.itemResolutionMode;
            params.itemBitIndex        = _params.itemBitIndex || '';
            e.params = params;
        }

        // ================================================================
        // 3. 加载后事件：更新总数
        // ================================================================
        function onDataGridLoad(e) {
            var result = e.result || {};
            var totalCount = result.totalCount
                || (result.totalRoot ? result.totalRoot.length : 0);
            document.getElementById('totalCountLabel').textContent =
                _loginUserLanguageResource.totalCount + ': ' + totalCount;
        }

        // ================================================================
        // 4. 导出
        // ================================================================
        function exportData() {
            if (!_params.deviceId) {
                mini.alert(_loginUserLanguageResource.checkOne);
                return;
            }

            var timestamp = new Date().getTime();
            var key = 'exportItemRealTimeData_' + _params.deviceId + '_'
                    + _params.itemCode + '_' + timestamp;
            var url = context + '/realTimeMonitoringController/exportItemRealTimeData';
            var param = '&deviceId=' + _params.deviceId +
                '&deviceName=' + encodeURIComponent(encodeURIComponent(_params.deviceName || '')) +
                '&calculateType=' + (_params.calculateType || 0) +
                '&itemName=' + encodeURIComponent(encodeURIComponent(_params.itemName || '')) +
                '&itemCode=' + (_params.itemCode || '') +
                '&itemType=' + (_params.itemType || 0) +
                '&itemResolutionMode=' + (_params.itemResolutionMode || 0) +
                '&itemBitIndex=' + (_params.itemBitIndex || '') +
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
        // 5. 初始化
        // ================================================================
        $(document).ready(function () {
            mini.parse();
        });
    </script>
</body>
</html>