<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { height:100%; margin:0; padding:0; overflow:hidden; }
        .form-row { margin-bottom:12px; display:flex; align-items:center; }
        .form-label { width:90px; text-align:right; padding-right:8px; font-size:13px; flex-shrink:0; white-space:nowrap; }
        .form-control { flex:1; }
        .btn-row { text-align:center; padding:10px 0; border-top:1px solid #e8e8e8; background:#fff; flex-shrink:0; }
        .btn-row .mini-button { margin:0 10px; width:80px; }
    </style>
</head>
<body>
    <div id="treePanel" class="mini-panel" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
        <div id="treeGrid" class="mini-treegrid" style="width:100%;height:100%;"
             showTreeIcon="true" treeColumn="name" showCheckBox="false"
             expandOnLoad="true" showPager="false" expandOnDblClick="false" expandOnNodeClick="false">
        </div>
    </div>

    <script type="text/javascript">
    var context = '<%=path%>';
    var lang = _loginUserLanguageResource;

    /* ============ 父页面引用（由 setParentWin 注入，禁止顶层直接取 window.parent） ============ */
    var parentWin    = null;
    var curveConfig  = null;
    var lastResult   = null;
    var hexToHsl     = null;
    var hslToHex     = null;
    var contrastColor= null;
    var isNotVal     = null;

    var treeGrid  = null;
    var treePanel = null;

    var BASE_HUES = [210, 0, 120, 45, 280, 190, 330, 90, 250, 20];
    var MAX_LIGHTNESS_LEVELS = 4;
    var L_MIN = 35, L_MAX = 72;

    /* ============ 父页面 mini.open 的 onload 中调用 ============ */
    function setParentWin(w) {
        parentWin    = w;
        curveConfig  = parentWin.historyDataComparativeAnalysisCurveConfig;
        lastResult   = parentWin.getHistoryDataComparativeAnalysisLastResult();
        hexToHsl     = parentWin.historyDataComparativeAnalysisHexToHsl;
        hslToHex     = parentWin.historyDataComparativeAnalysisHslToHex;
        contrastColor= parentWin.historyDataComparativeAnalysisContrastColor;
        isNotVal     = parentWin.isNotVal;

        initPage();
    }

    /* ============ 初始化（拿到 parentWin 之后才执行） ============ */
    function initPage() {
        mini.parse();
        treeGrid  = mini.get('treeGrid');
        treePanel = mini.get('treePanel');
        treePanel.setTitle(lang.curveProperty + ' - ' + lang.doubleClickNodeToConfigYAxisOrCurve);

        /* 树列定义（含背景色渲染） */
        treeGrid.setColumns([
            { name: 'name', field: 'name', width: 260, headerAlign: 'left', header: lang.name },
            {
                name: 'configStr', field: 'configStr', width: 170, headerAlign: 'center', header: lang.config,
                renderer: function (e) {
                    var record = e.record;
                    var color = record.type === 'axis' ? record.axisColorValue : record.seriesColorValue;
                    if (color) {
                        e.cellStyle = 'background-color:#' + color + ';color:' + contrastColor(color) + ';';
                    }
                    return e.value || '';
                }
            },
            { name: 'fullConfigStr', field: 'fullConfigStr', width: 150, visible: false, header: lang.allConfig }
        ]);

        /* 双击节点打开配置 */
        treeGrid.on('rowdblclick', function (e) {
            var record = e.record;
            if (record.type === 'axis') openAxisConfigWindow(record);
            else openSeriesConfigWindow(record);
        });

        refreshTree();
    }

    /* ============ 构建树数据 ============ */
    function buildTreeData() {
        var treeData = [];
        if (!lastResult) return treeData;
        var fieldList = lastResult.items || [];

        var fieldDeviceCount = {};
        for (var k = 0; k < lastResult.list.length; k++) {
            var r = lastResult.list[k];
            var key = r.itemColumn + '|' + r.dataSource;
            fieldDeviceCount[key] = (fieldDeviceCount[key] || 0) + 1;
        }

        for (var i = 0; i < fieldList.length; i++) {
            var f = fieldList[i];
            var fieldKey = f.itemColumn + '|' + f.dataSource;
            var cfg = curveConfig.axis[fieldKey] || {};

            var defaultAxisSort = i + 1;
            var defaultOpposite = i % 2 === 1;
            var defaultAxisHex  = hslToHex(BASE_HUES[i % BASE_HUES.length], 70, 50);

            var axisSort = (cfg.axisSort !== undefined && cfg.axisSort !== null && cfg.axisSort !== '')
                ? cfg.axisSort : defaultAxisSort;
            var opposite = (cfg.yAxisOpposite !== undefined && cfg.yAxisOpposite !== null)
                ? cfg.yAxisOpposite : defaultOpposite;
            var oppositeText = opposite ? lang.right : lang.left;
            var axisColor = cfg.axisColor || defaultAxisHex;

            var unitSuffix = isNotVal(f.itemUnit) ? ('(' + f.itemUnit + ')') : '';

            var node = {
                id: 'axis_' + fieldKey,
                type: 'axis',
                fieldKey: fieldKey,
                name: f.itemName + unitSuffix,
                configStr: axisSort + ';' + oppositeText + ';' + axisColor,
                fullConfigStr: JSON.stringify({ axisSort: axisSort, yAxisOpposite: opposite, axisColor: axisColor }),
                axisSortValue: axisSort,
                oppositeValue: opposite,
                axisColorValue: axisColor,
                expanded: true,
                children: []
            };

            var devIdx = 0;
            var devCount = fieldDeviceCount[fieldKey] || 1;

            for (var j = 0; j < lastResult.list.length; j++) {
                var row = lastResult.list[j];
                var rowKey = row.itemColumn + '|' + row.dataSource;
                if (rowKey !== fieldKey) continue;

                var seriesKey = rowKey + '_' + row.deviceId;
                var sCfg = curveConfig.series[seriesKey] || {};

                var hsl = hexToHsl(axisColor);
                var levelCount = Math.min(devCount, MAX_LIGHTNESS_LEVELS);
                var levelIdx = devIdx % levelCount;
                var lightness = (levelCount <= 1) ? 50
                    : (L_MIN + (L_MAX - L_MIN) * (levelIdx / (levelCount - 1)));
                var defaultSeriesHex = hslToHex(hsl.h, hsl.s, Math.round(lightness));
                var seriesColor = sCfg.color || defaultSeriesHex;

                var rowUnitSuffix = isNotVal(row.itemUnit) ? ('(' + row.itemUnit + ')') : '';

                node.children.push({
                    id: 'series_' + seriesKey,
                    type: 'series',
                    fieldKey: fieldKey,
                    deviceId: row.deviceId,
                    seriesKey: seriesKey,
                    name: row.deviceName + '.' + row.itemName + rowUnitSuffix,
                    configStr: seriesColor,
                    fullConfigStr: JSON.stringify({
                        lineWidth: sCfg.lineWidth || '',
                        color: seriesColor,
                        dashStyle: sCfg.dashStyle || 'Solid'
                    }),
                    lineWidthValue: (sCfg.lineWidth !== undefined && sCfg.lineWidth !== '') ? sCfg.lineWidth : 2,
                    seriesColorValue: seriesColor,
                    dashStyleValue: sCfg.dashStyle || 'Solid'
                });
                devIdx++;
            }
            treeData.push(node);
        }
        console.log('对比曲线配置树数据：' + JSON.stringify(treeData));
        return treeData;
    }

    function refreshTree() {
        if (!treeGrid) return;
        treeGrid.setData(buildTreeData());
        treeGrid.expandAll();
    }

    function repaintChart() {
        if (lastResult && parentWin) {
            parentWin.renderHistoryComparativeAnalysisCurve(lastResult);
        }
    }

    /* ============ 把颜色直接刷到 buttonedit 上（背景色 + 文字对比色 + 外框染色） ============ */
    function applyColorToButtonEdit(controlId, color) {
        var btn = mini.get(controlId);
        if (!btn) return;
        var el = btn.getEl();
        if (!el) return;

        /* 内部输入框：背景色 + 文字对比色 */
        var input = el.querySelector('.mini-buttonedit-input');
        if (input) {
            if (color) {
                input.style.backgroundColor = '#' + color;
                input.style.color = contrastColor(color);
            } else {
                input.style.backgroundColor = '';
                input.style.color = '';
            }
        }

        /* 外层整体（含右侧按钮区域）：背景与外框同色 */
        if (color) {
            el.style.backgroundColor = '#' + color;
            el.style.borderColor = '#' + color;
        } else {
            el.style.backgroundColor = '';
            el.style.borderColor = '';
        }
    }

    /* ============ 颜色选择：统一走 colorSelectWindow.jsp ============ */
    function openColorPicker(currentColorVal, onColorSelected) {
        if (currentColorVal && currentColorVal.indexOf('#') === 0) {
            currentColorVal = currentColorVal.substring(1);
        }
        mini.open({
            title: lang.colorSelect,
            url: context + '/miniui-app/modules/driverConfig/colorSelectWindow.jsp',
            width: 400,
            height: 280,
            modal: true,
            allowResize: false,
            onload: function () {
                var iframe = this.getIFrameEl();
                var contentWindow = iframe.contentWindow;
                contentWindow.setData({
                    row: 0, col: 0, tableType: 0,
                    currentColor: currentColorVal
                });
                contentWindow._updateColor = function (row, col, tableType, color) {
                    if (color && color.indexOf('#') === 0) color = color.substring(1);
                    onColorSelected(color);
                };
            }
        });
    }

    /* ============ 轴配置窗口 ============ */
    var _axisWin = null;
    function openAxisConfigWindow(record) {
        if (_axisWin) _axisWin.destroy();

        var axisSort = record.axisSortValue || 1;
        var opposite = record.oppositeValue === true || record.oppositeValue === 'true';
        var axisColor = record.axisColorValue || '';

        var html = ''
            + '<div style="padding:15px 20px;">'
            + '  <div class="form-row">'
            + '    <span class="form-label"><font color="red">*</font>' + lang.curveSort + '：</span>'
            + '    <div class="form-control"><input id="cfgAxisSort" class="mini-spinner" minValue="1" value="' + axisSort + '" style="width:100%;"/></div>'
            + '  </div>'
            + '  <div class="form-row">'
            + '    <span class="form-label">' + lang.yAxisPosition + '：</span>'
            + '    <div class="form-control"><input id="cfgOpposite" class="mini-combobox" style="width:100%;" valueField="value" textField="text" allowInput="false"/></div>'
            + '  </div>'
            + '  <div class="form-row">'
            + '    <span class="form-label">' + lang.yAxisColor + '：</span>'
            + '    <div class="form-control">'
            + '      <input id="cfgAxisColor" class="mini-buttonedit" style="width:100%;" allowInput="false" onbuttonclick="onAxisColorPick"/>'
            + '    </div>'
            + '  </div>'
            + '</div>'
            + '<div class="btn-row">'
            + '  <button class="mini-button" plain="true" iconCls="save" onclick="saveAxisConfig()">' + lang.confirm + '</button>'
            + '  <button class="mini-button" plain="true" iconCls="cancel" onclick="closeAxisConfig()">' + lang.cancel + '</button>'
            + '</div>';

        _axisWin = new mini.Window();
        _axisWin.set({
            title: lang.curveProperty,
            width: 420, 
            modal: true, allowResize: false, showModal: true,
            bodyStyle: 'padding:0;'
        });
        _axisWin.setContent(html);
        _axisWin.show();
        mini.parse(_axisWin.getEl());

        var cmb = mini.get('cfgOpposite');
        cmb.setData([
            { value: false, text: lang.left },
            { value: true,  text: lang.right }
        ]);
        cmb.setValue(opposite);

        var colorBtn = mini.get('cfgAxisColor');
        colorBtn.setValue(axisColor);
        colorBtn.setText('#' + axisColor);
        applyColorToButtonEdit('cfgAxisColor', axisColor);   /* ★ 初始即显示颜色 */

        _axisWin._record = record;
        _axisWin._color  = axisColor;
    }

    function onAxisColorPick() {
        var colorBtn = mini.get('cfgAxisColor');
        var cur = colorBtn.getValue() || _axisWin._color || '';
        openColorPicker(cur, function (color) {
            _axisWin._color = color;
            colorBtn.setValue(color);
            colorBtn.setText('#' + color);
            applyColorToButtonEdit('cfgAxisColor', color);   /* ★ 选色后立即刷新底色 */
        });
    }

    function saveAxisConfig() {
        var axisSort = mini.get('cfgAxisSort').getValue();
        var opposite = mini.get('cfgOpposite').getValue();
        var record   = _axisWin._record;
        var key      = record.fieldKey;

        var cfg = curveConfig.axis[key] || {};
        cfg.axisSort      = axisSort;
        cfg.yAxisOpposite = (opposite === true || opposite === 'true');
        cfg.axisColor     = _axisWin._color || '';
        curveConfig.axis[key] = cfg;

        closeAxisConfig();
        refreshTree();
        repaintChart();
    }

    function closeAxisConfig() {
        if (_axisWin) { _axisWin.destroy(); _axisWin = null; }
    }

    /* ============ 曲线配置窗口 ============ */
    var _seriesWin = null;
    function openSeriesConfigWindow(record) {
        if (_seriesWin) _seriesWin.destroy();

        var lineWidth   = record.lineWidthValue || 2;
        var seriesColor = record.seriesColorValue || '';
        var dashStyle   = record.dashStyleValue || '';

        var dashOptions = ['Solid', 'ShortDash', 'ShortDot', 'ShortDashDot', 'ShortDashDotDot',
            'Dot', 'Dash', 'LongDash', 'DashDot', 'LongDashDot', 'LongDashDotDot'];

        var html = ''
            + '<div style="padding:15px 20px;">'
            + '  <div class="form-row">'
            + '    <span class="form-label"><font color="red">*</font>' + lang.lineWidth + '：</span>'
            + '    <div class="form-control"><input id="cfgLineWidth" class="mini-spinner" minValue="1" value="' + lineWidth + '" style="width:100%;"/></div>'
            + '  </div>'
            + '  <div class="form-row">'
            + '    <span class="form-label">' + lang.lineDash + '：</span>'
            + '    <div class="form-control"><input id="cfgDashStyle" class="mini-combobox" style="width:100%;" valueField="value" textField="text" allowInput="false"/></div>'
            + '  </div>'
            + '  <div class="form-row">'
            + '    <span class="form-label">' + lang.curveColor + '：</span>'
            + '    <div class="form-control">'
            + '      <input id="cfgSeriesColor" class="mini-buttonedit" style="width:100%;" allowInput="false" onbuttonclick="onSeriesColorPick"/>'
            + '    </div>'
            + '  </div>'
            + '</div>'
            + '<div class="btn-row">'
            + '  <button class="mini-button" plain="true" iconCls="save" onclick="saveSeriesConfig()">' + lang.confirm + '</button>'
            + '  <button class="mini-button" plain="true" iconCls="cancel" onclick="closeSeriesConfig()">' + lang.cancel + '</button>'
            + '</div>';

        _seriesWin = new mini.Window();
        _seriesWin.set({
            title: lang.curveProperty,
            width: 420, 
            modal: true, allowResize: false, showModal: true,
            bodyStyle: 'padding:0;'
        });
        _seriesWin.setContent(html);
        _seriesWin.show();
        mini.parse(_seriesWin.getEl());

        var dashData = dashOptions.map(function (d) { return { value: d, text: d }; });
        var cmb = mini.get('cfgDashStyle');
        cmb.setData(dashData);
        cmb.setValue(dashStyle);

        var colorBtn = mini.get('cfgSeriesColor');
        colorBtn.setValue(seriesColor);
        colorBtn.setText('#' + seriesColor);
        applyColorToButtonEdit('cfgSeriesColor', seriesColor);   /* ★ 初始即显示颜色 */

        _seriesWin._record = record;
        _seriesWin._color  = seriesColor;
    }

    function onSeriesColorPick() {
        var colorBtn = mini.get('cfgSeriesColor');
        var cur = colorBtn.getValue() || _seriesWin._color || '';
        openColorPicker(cur, function (color) {
            _seriesWin._color = color;
            colorBtn.setValue(color);
            colorBtn.setText('#' + color);
            applyColorToButtonEdit('cfgSeriesColor', color);   /* ★ 选色后立即刷新底色 */
        });
    }

    function saveSeriesConfig() {
        var lineWidth = mini.get('cfgLineWidth').getValue();
        var dashStyle = mini.get('cfgDashStyle').getValue();
        var record    = _seriesWin._record;
        var key       = record.seriesKey;

        var cfg = curveConfig.series[key] || {};
        cfg.lineWidth = lineWidth || 2;
        cfg.color     = _seriesWin._color || '';
        cfg.dashStyle = dashStyle || '';
        curveConfig.series[key] = cfg;

        closeSeriesConfig();
        refreshTree();
        repaintChart();
    }

    function closeSeriesConfig() {
        if (_seriesWin) { _seriesWin.destroy(); _seriesWin = null; }
    }

    /* ============ 暴露给父页面 ============ */
    window.setData = function (data) { /* 预留 */ };
    window.setParentWin = setParentWin;
    </script>
</body>
</html>