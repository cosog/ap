<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>颜色选择</title>
<jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
<link rel="stylesheet" href="<%=path%>/scripts/miniui/third-party/spectrum/spectrum.css?timestamp=<%=otherStaticResourceTimestamp%>" />
<script src="<%=path%>/scripts/miniui/third-party/spectrum/spectrum.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<style>
    html, body {
        margin: 0;
        padding: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
        background: #fff;
    }

    /* 只让容器撑满宽度，不改内部布局 */
    .sp-container.sp-flat {
        width: 100% !important;
        max-width: 100% !important;
        height: auto !important;
        border: none !important;
        box-shadow: none !important;
        box-sizing: border-box;
        padding: 10px !important;
        margin: 0 !important;
    }

    /* 让色域区域宽度占满 */
    .sp-container.sp-flat .sp-top {
        width: 100% !important;
    }
</style>
</head>
<body>
    <input type="text" id="colorPicker" style="display:none;" />
    <script type="text/javascript">
        mini.parse();

        var lang = window.parent._loginUserLanguageResource;
        var _row = null;
        var _col = null;
        var _tableType = null;

        var currentColor = 'ff0000';

        function setData(data) {
            if (data) {
                _row = data.row;
                _col = data.col;
                _tableType = data.tableType;
                currentColor = data.currentColor || 'ff0000';
                initSpectrum(currentColor);
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
                    // 拖动/选择色块时只更新 currentColor，不关闭
                    change: function(color) {
                    	if (color) {
                            currentColor = color.toHexString().replace('#', '');
                        }
                        if (window._updateColor) {
                            window._updateColor(_row, _col, _tableType, currentColor);
                        }
                        closeWin();
                    }
                });
            } catch(e) {
                console.error('Spectrum init error:', e);
            }
        }

        function closeWin() {
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