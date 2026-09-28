<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>曲线设置</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
        }
        .curve-toolbar {
            height: 36px;
            background: #f0f0f0;
            border-bottom: 1px solid #d0d0d0;
            display: flex;
            align-items: center;
            padding: 0 12px;
            flex-shrink: 0;
        }
        .curve-toolbar .tip {
            color: #e60000;
            font-size: 12px;
            flex: 1;
        }
        .curve-toolbar .tip .icon {
            color: #e60000;
        }
        .app-layout {
            width: 100%;
            height: calc(100% - 36px);
        }
        .pane-body {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #f5f7fa;
        }
        .pane-title {
            flex-shrink: 0;
            padding: 4px 12px;
            font-weight: bold;
            font-size: 13px;
            color: #333;
            background: #fafafa;
            border-bottom: 1px solid #e8e8e8;
            height: 30px;
            line-height: 30px;
        }
        .grid-box {
            flex: 1;
            position: relative;
            overflow: hidden;
            background: #fff;
            min-height: 0;
        }
        .grid-box .mini-datagrid {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            border: none !important;
        }
        .loading-placeholder {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100%;
            color: #999;
            font-size: 13px;
        }
        .loading-placeholder.error {
            color: #ff4d4f;
        }
        .mini-splitter .mini-splitter-pane {
            padding: 0 !important;
        }
    </style>
</head>
<body>

    <!-- 顶部工具条 -->
    <div class="curve-toolbar">
        <div class="tip">
            <span class="icon">⚠</span>
            <span id="tipMessage"></span>
        </div>
        <button id="saveBtn" class="mini-button" plain="true" iconCls="save"
                onclick="saveCurveSet()" style="margin-left:auto;"></button>
    </div>

    <!-- 主体布局：Splitter 充满窗口 -->
    <div class="app-layout">
        <div id="curveMainSplitter" class="mini-splitter"
             style="width:100%; height:100%;" vertical="false">
            <div size="100%" showCollapseButton="false">
                <div class="pane-body">
                    <div class="pane-title"><span id="curvePaneTitle"></span></div>
                    <div class="grid-box" id="curveGridBox"></div>
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
            loadCurveSetData();
        }

        // ================================================================
        // 2. 加载曲线设置数据
        // ================================================================
        function loadCurveSetData() {
            if (!_params) {
                mini.alert('参数错误');
                return;
            }

            var curveBox = document.getElementById('curveGridBox');
            curveBox.innerHTML = '<div class="loading-placeholder">'
                + _loginUserLanguageResource.loadingData + '</div>';

            $.ajax({
                url: context + '/reportDataMamagerController/getReportQueryCurveSetData',
                type: 'POST',
                data: {
                    deviceId:   _params.deviceId,
                    deviceName: _params.deviceName,
                    deviceType: _params.deviceType,
                    reportType: _params.reportType
                },
                dataType: 'json',
                timeout: 15000,
                success: function (result) {
                    createCurveGrid('curveGridBox', result.totalRoot || []);
                },
                error: function () {
                    curveBox.innerHTML = '<div class="loading-placeholder error">'
                        + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        // ================================================================
        // 3. 动态创建曲线设置 datagrid
        // ================================================================
        function createCurveGrid(containerId, data) {
            var container = document.getElementById(containerId);
            if (!container) return;
            container.innerHTML = '';

            var oldGrid = mini.get('productionCurveSetGrid');
            if (oldGrid) oldGrid.destroy();

            if (!data || data.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">'
                    + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            var grid = new mini.DataGrid();
            grid.set({
                id: 'productionCurveSetGrid',
                style: 'width:100%; height:100%;',
                showPager: false,
                allowCellEdit: true,
                allowCellSelect: true,
                allowCellWrap: false,
                allowResize: true,
                virtualScroll: false,
                allowAlternating: true,
                data: data,
                columns: [
                    {
                        type: 'indexcolumn', width: 50,
                        header: _loginUserLanguageResource.idx,
                        headerAlign: 'center', align: 'center'
                    },
                    {
                        field: 'curveName',
                        header: _loginUserLanguageResource.curve,
                        width: '35%',
                        headerAlign: 'center', align: 'center',
                        allowSort: false, readOnly: true
                    },
                    {
                        field: 'yAxisMaxValue',
                        header: _loginUserLanguageResource.yAxisMaxSetValue,
                        width: '30%',
                        headerAlign: 'center', align: 'center',
                        allowSort: false,
                        editor: { type: 'textbox' }
                    },
                    {
                        field: 'yAxisMinValue',
                        header: _loginUserLanguageResource.yAxisMinSetValue,
                        width: '30%',
                        headerAlign: 'center', align: 'center',
                        allowSort: false,
                        editor: { type: 'textbox' }
                    },
                    // 隐藏列：itemCode / itemType，保存时需要用到
                    { field: 'itemCode', header: '', visible: false },
                    { field: 'itemType', header: '', visible: false }
                ],
                oncellvalidation: function (e) {
                    if (e.column.field === 'yAxisMaxValue'
                        || e.column.field === 'yAxisMinValue') {
                        if (e.value !== '' && e.value !== null
                            && e.value !== undefined && isNaN(e.value)) {
                            e.isValid = false;
                            e.errorText = _loginUserLanguageResource.invalidData;
                        }
                    }
                }
            });
            grid.render(container);
        }

        // ================================================================
        // 4. 保存曲线设置
        // ================================================================
        function saveCurveSet() {
            var grid = mini.get('productionCurveSetGrid');
            if (!grid) {
                mini.alert('表格未加载完成');
                return;
            }

            var curveData = grid.getData();
            var reportType = _params.reportType;
            var graphicSetData = {};

            if (reportType === 0) {
                graphicSetData.Report = [];
            } else if (reportType === 2) {
                graphicSetData.DailyReport = [];
            }

            curveData.forEach(function (row) {
                var itemCode = row.itemCode;
                if (!itemCode) return;

                var graphicInfo = {
                    itemCode:      itemCode,
                    itemType:      row.itemType || '',
                    yAxisMaxValue: (row.yAxisMaxValue != null ? row.yAxisMaxValue : ''),
                    yAxisMinValue: (row.yAxisMinValue != null ? row.yAxisMinValue : '')
                };

                if (reportType === 0) {
                    graphicSetData.Report.push(graphicInfo);
                } else if (reportType === 2) {
                    graphicSetData.DailyReport.push(graphicInfo);
                }
            });

            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });

            $.ajax({
                url: context + '/reportDataMamagerController/setReportDataGraphicInfo',
                type: 'POST',
                data: {
                    deviceId:   _params.deviceId,
                    deviceName: _params.deviceName,
                    deviceType: _params.deviceType,
                    reportType: reportType,
                    graphicSetData: JSON.stringify(graphicSetData)
                },
                dataType: 'json',
                timeout: 15000,
                success: function (result) {
                    mini.unmask(document.body);
                    if (result && result.success) {
                        // 1. 刷新父页面曲线
                        if (typeof window._parentReloadCurve === 'function') {
                            window._parentReloadCurve();
                        }
                        // 2. 父页面弹出提示（不受子窗口关闭影响）
                        if (typeof window._parentShowAlert === 'function') {
                            window._parentShowAlert(
                                _loginUserLanguageResource.savedSuccessfully,
                                _loginUserLanguageResource.tip
                            );
                        }
                        // 3. 关闭子窗口
                        setTimeout(function () {
                            window.CloseOwnerWindow('ok');
                        }, 100);
                    } else {
                        mini.alert(_loginUserLanguageResource.operationFailed);
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }

        // ================================================================
        // 5. 初始化：绑定国际化
        // ================================================================
        $(document).ready(function () {
            mini.parse();
            document.getElementById('tipMessage').textContent =
                _loginUserLanguageResource.diagramSetTooltip;
            document.getElementById('curvePaneTitle').textContent =
                _loginUserLanguageResource.yAxisConfig;
            mini.get('saveBtn').setText(_loginUserLanguageResource.save);
        });
    </script>
</body>
</html>