<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
// 强制刷新缓存（开发时可去掉）
//otherStaticResourceTimestamp = System.currentTimeMillis() + "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>颜色选择</title>
<jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
<!-- 引入 Spectrum 资源（确保路径正确） -->
<link rel="stylesheet" href="<%=path%>/scripts/miniui/third-party/spectrum/spectrum.css?timestamp=<%=otherStaticResourceTimestamp%>" />
<script src="<%=path%>/scripts/miniui/third-party/spectrum/spectrum.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<style>
    body { padding: 20px; background: #fff; }
    .row { margin-bottom: 15px; display: flex; align-items: center; }
    .label { width: 80px; text-align: right; padding-right: 10px; font-size: 13px; flex-shrink: 0; }
    .control { flex: 1; display: flex; align-items: center; }
    .btn-row { text-align: center; padding-top: 20px; border-top: 1px solid #e8e8e8; margin-top: 15px; }
    .btn-row .mini-button { margin: 0 10px; width: 80px; }
    /* 移除所有自定义 Spectrum 样式，使用默认 */
</style>
</head>
<body>
    <!-- 隐藏域（用于回调父窗口） -->
    <input id="colorRow" class="mini-hidden" />
    <input id="colorCol" class="mini-hidden" />
    <input id="colorTableType" class="mini-hidden" value="0" />

    <div class="row">
        <span id="currentColorLabel" class="label"></span>
        <div class="control">
            <span id="preview" class="color-preview" style="display:inline-block;width:30px;height:30px;border:1px solid #ccc;margin-left:10px;background-color:#ff0000;"></span>
        </div>
    </div>
    <div class="row">
        <span id="selectColorLabel" class="label"></span>
        <div class="control">
            <input id="colorText" class="mini-textbox" style="width:120px;" />
            <input id="colorPicker" style="margin-left:10px;" />
        </div>
    </div>
    <div class="btn-row">
        <a id="confirmBtn" class="mini-button" onclick="onSave()"></a>
        <a id="cancelBtn" class="mini-button" onclick="onCancel()"></a>
    </div>

    <script type="text/javascript">
        mini.parse();

        // 从父窗口获取国际化对象（必须存在）
        var lang = window.parent._loginUserLanguageResource;

        // 设置国际化文本
        document.getElementById('currentColorLabel').innerHTML = lang.currentColor||'当前颜色' + '：';
        document.getElementById('selectColorLabel').innerHTML = lang.selectColor||'选择颜色' + '：';
        mini.get('confirmBtn').setText(lang.confirm);
        mini.get('cancelBtn').setText(lang.cancel);
        mini.get('colorText').setEmptyText(lang.selectColor);

        var currentColor = 'ff0000';
        var spectrumInited = false;

        function setData(data) {
            if (data) {
                mini.get('colorRow').setValue(data.row || 0);
                mini.get('colorCol').setValue(data.col || 0);
                mini.get('colorTableType').setValue(data.tableType || 0);
                var initColor = data.currentColor || 'ff0000';
                currentColor = initColor;
                $('#preview').css('background-color', '#' + initColor);
                mini.get('colorText').setValue('#' + initColor);

                if (!spectrumInited) {
                    initSpectrum(initColor);
                    spectrumInited = true;
                } else {
                    $('#colorPicker').spectrum('set', '#' + initColor);
                }
            }
        }

        function initSpectrum(initColor) {
            try {
                $('#colorPicker').spectrum({
                    color: '#' + initColor,
                    flat: true,
                    showAlpha: true,
                    showInput: true,
                    showInitial: true,
                    showPalette: true,
                    showButtons: true,
                    cancelText: lang.cancel,
                    chooseText: lang.confirm,
                    appendTo: 'body',
                    preferredFormat: 'hex',
                    palette: [
                        ['#000','#444','#666','#999','#ccc','#eee','#f3f3f3','#fff'],
                        ['#f00','#f90','#ff0','#0f0','#0ff','#00f','#90f','#f0f'],
                        ['#f4cccc','#fce5cd','#fff2cc','#d9ead3','#d0e0e3','#cfe2f3','#d9d2e9','#ead1dc'],
                        ['#ea9999','#f9cb9c','#ffe599','#b6d7a8','#a2c4c9','#9fc5e8','#b4a7d6','#d5a6bd'],
                        ['#e06666','#f6b26b','#ffd966','#93c47d','#76a5af','#6fa8dc','#8e7cc3','#c27ba0'],
                        ['#c00','#e69138','#f1c232','#6aa84f','#45818e','#3d85c6','#674ea7','#a64d79'],
                        ['#900','#b45f06','#bf9000','#38761d','#134f5c','#0b5394','#351c75','#741b47'],
                        ['#600','#783f04','#7f6000','#274e13','#0c343d','#073763','#20124d','#4c1130']
                    ],
                    change: function(color) {
                        if (color) {
                            var hex = color.toHexString();
                            currentColor = hex.replace('#','');
                            mini.get('colorText').setValue(hex);
                        }
                    },
                    move: function(color) {
                        if (color) {
                            var hex = color.toHexString();
                        }
                    }
                });
                $('#colorPicker').spectrum('set', '#' + initColor);
            } catch(e) {
                console.error('Spectrum init error:', e);
            }
        }

        function onSave() {
            var row = parseInt(mini.get('colorRow').getValue() || 0);
            var col = parseInt(mini.get('colorCol').getValue() || 0);
            var tableType = parseInt(mini.get('colorTableType').getValue() || 0);
            
            if (window._updateColor) {
                window._updateColor(row, col, tableType, currentColor);
            }
            
            try { $('#colorPicker').spectrum('destroy'); } catch(e) {}
            if (window.CloseOwnerWindow) window.CloseOwnerWindow('ok');
            else window.close();
        }

        function onCancel() {
            try { $('#colorPicker').spectrum('destroy'); } catch(e) {}
            if (window.CloseOwnerWindow) window.CloseOwnerWindow('cancel');
            else window.close();
        }

        $(window).on('beforeunload', function() {
            try { $('#colorPicker').spectrum('destroy'); } catch(e) {}
        });
    </script>
</body>
</html>