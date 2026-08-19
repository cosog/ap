<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>历史曲线设置</title>
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
        <button id="saveBtn" class="mini-button" iconCls="save" onclick="saveCurveSet()" style="margin-left:auto;"></button>
    </div>

    <!-- 主体布局 -->
    <div class="app-layout">
        <div class="mini-splitter" style="width:100%; height:100%;" vertical="false">
            <!-- 左侧面板 60% -->
            <div size="60%" showCollapseButton="false">
                <div class="pane-body">
                    <div class="pane-title"><span id="leftTitle"></span></div>
                    <div class="grid-box" id="curveGridBox">
                        <!-- 由 JS 动态创建表格 -->
                    </div>
                </div>
            </div>
            <!-- 右侧面板 40%，可向右折叠 -->
            <div size="40%" showCollapseButton="true" collapseDirection="right" collapsed="false">
                <div class="pane-body">
                    <div class="pane-title"><span id="rightTitle"></span></div>
                    <div class="grid-box" id="filterGridBox">
                        <!-- 由 JS 动态创建表格 -->
                    </div>
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
            _params = params;
            loadCurveSetData();
        }
        // ★ 只有 setData 需要暴露给父页面
        //window.setData = setData;

        // ================================================================
        // 2. 加载数据并动态创建表格
        // ================================================================
        function loadCurveSetData() {
            if (!_params) {
                mini.alert('参数错误');
                return;
            }

            var curveBox = document.getElementById('curveGridBox');
            var filterBox = document.getElementById('filterGridBox');
            curveBox.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>';
            filterBox.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.loadingData + '</div>';

            $.ajax({
                url: context + '/historyQueryController/getHistoryQueryCurveSetData',
                type: 'POST',
                data: {
                    deviceId: _params.deviceId,
                    deviceName: _params.deviceName,
                    deviceType: _params.deviceType
                },
                dataType: 'json',
                timeout: 15000,
                success: function(result) {
                    createCurveGrid('curveGridBox', result.totalRoot || []);
                    createFilterGrid('filterGridBox', result.dataFilterTotalRoot || []);
                },
                error: function() {
                    curveBox.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
                    filterBox.innerHTML = '<div class="loading-placeholder error">' + _loginUserLanguageResource.requestFailed + '</div>';
                }
            });
        }

        // ================================================================
        // 3. 动态创建曲线设置表格（指定 id）
        // ================================================================
        function createCurveGrid(containerId, data) {
            var container = document.getElementById(containerId);
            if (!container) return;
            container.innerHTML = '';

            var oldGrid = mini.get('historyCurveSetGrid');
            if (oldGrid) {
                oldGrid.destroy();
            }

            if (!data || data.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            var grid = new mini.DataGrid();
            grid.set({
                id: 'historyCurveSetGrid',
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
                    { type: 'indexcolumn', width: 50, align: 'center', headerAlign: 'center', header: _loginUserLanguageResource.idx },
                    { field: 'curveName', header: _loginUserLanguageResource.curve, width: '35%', align: 'center', headerAlign: 'center', allowSort: false, readOnly: true },
                    { field: 'yAxisMaxValue', header: _loginUserLanguageResource.yAxisMaxSetValue, width: '30%', align: 'center', headerAlign: 'center', allowSort: false, editor: { type: 'textbox' } },
                    { field: 'yAxisMinValue', header: _loginUserLanguageResource.yAxisMinSetValue, width: '30%', align: 'center', headerAlign: 'center', allowSort: false, editor: { type: 'textbox' } }
                ],
                oncellvalidation: function(e) {
                    if (e.column.field === 'yAxisMaxValue' || e.column.field === 'yAxisMinValue') {
                        if (e.value !== '' && isNaN(e.value)) {
                            e.isValid = false;
                            e.errorText = _loginUserLanguageResource.invalidData;
                        }
                    }
                }
            });
            grid.render(container);
        }

        // ================================================================
        // 4. 动态创建筛选表格（指定 id）
        // ================================================================
        function createFilterGrid(containerId, data) {
            var container = document.getElementById(containerId);
            if (!container) return;
            container.innerHTML = '';

            var oldGrid = mini.get('historyFilterSetGrid');
            if (oldGrid) {
                oldGrid.destroy();
            }

            if (!data || data.length === 0) {
                container.innerHTML = '<div class="loading-placeholder">' + _loginUserLanguageResource.emptyMsg + '</div>';
                return;
            }

            var grid = new mini.DataGrid();
            grid.set({
                id: 'historyFilterSetGrid',
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
                    { type: 'indexcolumn', width: 50, align: 'center', headerAlign: 'center', header: _loginUserLanguageResource.idx },
                    { field: 'title', header: _loginUserLanguageResource.name, width: '50%', align: 'center', headerAlign: 'center', allowSort: false, readOnly: true },
                    { field: 'value', header: _loginUserLanguageResource.whetherDisplay, width: '40%', align: 'center', headerAlign: 'center', allowSort: false, type: 'checkboxcolumn', trueValue: true, falseValue: false }
                ]
            });
            grid.render(container);
        }

        // ================================================================
        // 5. 保存设置（内部函数，不暴露到 window）
        // ================================================================
        function saveCurveSet() {
            var curveGrid = mini.get('historyCurveSetGrid');
            var filterGrid = mini.get('historyFilterSetGrid');

            if (!curveGrid || !filterGrid) {
                mini.alert('表格未加载完成');
                return;
            }

            var curveData = curveGrid.getData();
            var filterData = filterGrid.getData();

            var commData = false, exceptionData = false;
            filterData.forEach(function(row) {
                if (row.code === 'commData') commData = row.value === true;
                else if (row.code === 'exceptionData') exceptionData = row.value === true;
            });

            var graphicSet = {
                History: [],
                HistoryDataFilter: { commData: commData, exceptionData: exceptionData }
            };

            // 保存 itemCode + itemType + Y轴范围
            curveData.forEach(function(row) {
                var itemCode = row.itemCode;
                var itemType = row.itemType;
                if (!itemCode) return;
                graphicSet.History.push({
                    itemCode: itemCode,
                    itemType: itemType || '',
                    yAxisMaxValue: row.yAxisMaxValue || '',
                    yAxisMinValue: row.yAxisMinValue || ''
                });
            });

            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.loadingData
            });

            $.ajax({
                url: context + '/historyQueryController/setHistoryDataGraphicInfo',
                type: 'POST',
                data: {
                    deviceId: _params.deviceId,
                    deviceName: _params.deviceName,
                    deviceType: _params.deviceType,
                    graphicSetData: JSON.stringify(graphicSet)
                },
                dataType: 'json',
                timeout: 15000,
                success: function(result) {
                    mini.unmask(document.body);
                    if (result.success) {
                    	// 1. 刷新父页面曲线
                        if (typeof window._parentLoadHistoryCurve === 'function') {
                            window._parentLoadHistoryCurve();
                        }
                        
                        // 2. 让父页面弹出提示框（独立于子窗口）
                        if (typeof window._parentShowAlert === 'function') {
                            window._parentShowAlert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                        }
                        
                        // 3. 关闭子窗口（提示框由父页面显示，不受影响）
                        setTimeout(function() {
                            window.CloseOwnerWindow('ok');
                        }, 100);
                    } else {
                        mini.alert(_loginUserLanguageResource.operationFailed);
                    }
                },
                error: function() {
                    mini.unmask(document.body);
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }

        // ================================================================
        // 6. 初始化：绑定按钮事件 + 国际化
        // ================================================================
        $(document).ready(function() {
        	mini.parse();
            // 国际化
            document.getElementById('tipMessage').textContent = _loginUserLanguageResource.diagramSetTooltip;
            mini.get('saveBtn').setText(_loginUserLanguageResource.save);
            document.getElementById('leftTitle').textContent = _loginUserLanguageResource.yAxisConfig;
            document.getElementById('rightTitle').textContent = _loginUserLanguageResource.dataFilter;
        });
    </script>
</body>
</html>