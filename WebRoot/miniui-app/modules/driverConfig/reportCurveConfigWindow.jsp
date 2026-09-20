<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>曲线属性</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .form-wrap { padding: 15px 20px; display:flex; flex-direction:column; height:100%; box-sizing:border-box; }
        .form-row { margin-bottom:12px; display:flex; align-items:center; }
        .form-label { width:90px; text-align:right; padding-right:10px; font-size:13px; flex-shrink:0; white-space:nowrap; }
        .form-control { flex:1; }
        .color-preview { display:inline-block; width:24px; height:24px; border:1px solid #ccc; border-radius:4px; margin-left:5px; cursor:pointer; vertical-align:middle; }
        .btn-row { text-align:center; padding:10px 0; flex-shrink:0; }
        .btn-row .mini-button { margin:0 10px; width:80px; }
    </style>
</head>
<body>
    <div class="form-wrap">
        <div style="flex:1;">
            <div class="form-row">
                <span class="form-label"><font color="red">*</font><span id="lblSort">排序</span>：</span>
                <div class="form-control">
                    <input id="curveSort" class="mini-spinner" style="width:100%;" minValue="1" value="1" maxValue="9999999999" required="true" />
                </div>
            </div>
            <div class="form-row">
                <span class="form-label"><font color="red">*</font><span id="lblLineWidth">线宽</span>：</span>
                <div class="form-control">
                    <input id="curveLineWidth" class="mini-spinner" style="width:100%;" minValue="1" value="3" maxValue="9999999999" required="true" />
                </div>
            </div>
            <div class="form-row">
                <span class="form-label"><span id="lblDashStyle">线型</span>：</span>
                <div class="form-control">
                    <input id="curveDashStyle" class="mini-combobox" style="width:100%;"
                           valueField="value" textField="text"
                           data='[{value:"Solid",text:"Solid"},{value:"ShortDash",text:"ShortDash"},{value:"ShortDot",text:"ShortDot"},{value:"ShortDashDot",text:"ShortDashDot"},{value:"ShortDashDotDot",text:"ShortDashDotDot"},{value:"Dot",text:"Dot"},{value:"Dash",text:"Dash"},{value:"LongDash",text:"LongDash"},{value:"DashDot",text:"DashDot"},{value:"LongDashDot",text:"LongDashDot"},{value:"LongDashDotDot",text:"LongDashDotDot"}]'
                           value="Solid" allowInput="false" />
                </div>
            </div>
            <div class="form-row">
                <span class="form-label"><span id="lblYAxis">Y轴位置</span>：</span>
                <div class="form-control">
                    <input id="curveYAxisOpposite" class="mini-combobox" style="width:100%;"
                           valueField="value" textField="text"
                           value="false" allowInput="false" />
                </div>
            </div>
            <div class="form-row">
                <span class="form-label"><font color="red">*</font><span id="lblColor">颜色</span>：</span>
                <div class="form-control">
                    <div style="display:flex;align-items:center;">
                        <input id="curveColor" class="mini-buttonedit" style="width:calc(100% - 30px);"
                               onbuttonclick="onColorButtonClick" required="true" />
                        <span id="colorPreview" class="color-preview" style="background-color:#ff0000;" onclick="onColorButtonClick()"></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="btn-row">
            <button id="btnSave" class="mini-button" iconCls="save" onclick="onSave()">保存</button>
            <button id="btnCancel" class="mini-button" iconCls="cancel" onclick="onCancel()">取消</button>
        </div>
    </div>

    <script>
        var context = '<%=context%>';
        var _params = {
            row: -1,
            col: -1,
            tableType: 0,
            config: null
        };
        var currentColor = 'ff0000';

        // ================================================================
        // 父窗口调用
        // ================================================================
        function setData(data) {
            if (!data) return;
            _params.row       = data.row !== undefined ? data.row : -1;
            _params.col       = data.col !== undefined ? data.col : -1;
            _params.tableType = data.tableType || 0;

            var config = data.config || {};
            _params.config = config;

            initI18n();

            // 回填表单
            mini.get('curveSort').setValue(config.sort || 1);
            mini.get('curveLineWidth').setValue(config.lineWidth || 3);
            mini.get('curveDashStyle').setValue(config.dashStyle || 'Solid');
            mini.get('curveYAxisOpposite').setValue(!!config.yAxisOpposite);

            currentColor = config.color || 'ff0000';
            var colorBtn = mini.get('curveColor');
            colorBtn.setValue(currentColor);
            colorBtn.setText('#' + currentColor);
            document.getElementById('colorPreview').style.backgroundColor = '#' + currentColor;
        }

        function initI18n() {
            document.title = _loginUserLanguageResource.curveProperty;

            document.getElementById('lblSort').innerText      = _loginUserLanguageResource.curveSort;
            document.getElementById('lblLineWidth').innerText = _loginUserLanguageResource.lineWidth;
            document.getElementById('lblDashStyle').innerText = _loginUserLanguageResource.lineDash;
            document.getElementById('lblYAxis').innerText     = _loginUserLanguageResource.yAxisPosition;
            document.getElementById('lblColor').innerText     = _loginUserLanguageResource.curveColor;

            mini.get('btnSave').setText(_loginUserLanguageResource.save);
            mini.get('btnCancel').setText(_loginUserLanguageResource.cancel);

            // Y 轴位置下拉
            mini.get('curveYAxisOpposite').setData([
                { value: false, text: _loginUserLanguageResource.left },
                { value: true,  text: _loginUserLanguageResource.right }
            ]);
        }

        // ================================================================
        // 颜色选择
        // ================================================================
        function onColorButtonClick() {
            var currentVal = currentColor;
            if (currentVal && currentVal.indexOf('#') === 0) {
                currentVal = currentVal.substring(1);
            }

            mini.open({
                title: _loginUserLanguageResource.colorSelect,
                url: context + '/miniui-app/modules/driverConfig/colorSelectWindow.jsp',
                width: 400,
                height: 280,
                modal: true,
                allowResize: false,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var cw = iframe.contentWindow;
                    cw.setData({
                        row: 0,
                        col: 0,
                        tableType: 0,
                        currentColor: currentVal
                    });
                    cw._updateColor = function (row, col, tableType, color) {
                        currentColor = color;
                        var btn = mini.get('curveColor');
                        btn.setValue(color);
                        btn.setText('#' + color);
                        document.getElementById('colorPreview').style.backgroundColor = '#' + color;
                    };
                }
            });
        }

        // ================================================================
        // 保存
        // ================================================================
        function onSave() {
            var sort = mini.get('curveSort').getValue();
            var lineWidth = mini.get('curveLineWidth').getValue();
            var dashStyle = mini.get('curveDashStyle').getValue();
            var yAxisOpposite = mini.get('curveYAxisOpposite').getValue();

            if (!sort || parseInt(sort) < 1) {
                mini.alert(_loginUserLanguageResource.pleaseCompleteForm);
                return;
            }
            if (!lineWidth || parseInt(lineWidth) < 1) {
                mini.alert(_loginUserLanguageResource.pleaseCompleteForm);
                return;
            }
            if (!currentColor) {
                mini.alert(_loginUserLanguageResource.pleaseCompleteForm);
                return;
            }

            var config = {
                sort: parseInt(sort),
                lineWidth: parseInt(lineWidth),
                dashStyle: dashStyle,
                yAxisOpposite: !!yAxisOpposite,
                color: currentColor
            };

            // 交给父窗口回写
            if (window._updateCurveConfig) {
                window._updateCurveConfig(_params.row, _params.col, _params.tableType, config);
            }
            CloseWindow('ok');
        }

        function onCancel() {
            CloseWindow('cancel');
        }

        function CloseWindow(action) {
            if (window.CloseOwnerWindow) window.CloseOwnerWindow(action);
            else window.close();
        }

        $(document).ready(function () {
            mini.parse();
            // 兜底：即使父窗口没调用 setData，也做一次初始化
            if (!document.title || document.title === '曲线属性') {
                initI18n();
            }
        });
    </script>
</body>
</html>