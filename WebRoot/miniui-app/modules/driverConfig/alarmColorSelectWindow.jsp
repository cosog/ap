<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <link rel="stylesheet" href="<%=path%>/scripts/miniui/third-party/spectrum/spectrum.css?timestamp=<%=otherStaticResourceTimestamp%>" />
    <script src="<%=path%>/scripts/miniui/third-party/spectrum/spectrum.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar {
            flex-shrink:0;
            padding:6px 10px;
            border-bottom:1px solid #e8e8e8;
            background:#fafafa;
            display:flex;
            align-items:center;
            justify-content:flex-end;
        }
        .color-body {
            flex:1;
            display:flex;
            overflow:hidden;
        }
        .color-column {
            flex:1;
            display:flex;
            flex-direction:column;
            border-right:1px solid #e8e8e8;
            overflow:auto;
        }
        .color-column:last-child { border-right:none; }
        .column-header {
            padding:8px 12px;
            background:#f5f5f5;
            font-weight:bold;
            font-size:13px;
            color:#333;
            border-bottom:1px solid #e8e8e8;
            flex-shrink:0;
        }
        .color-row {
            display:flex;
            align-items:center;
            padding:6px 12px;
            border-bottom:1px dashed #eee;
        }
        .color-row .label {
            width:90px;
            text-align:right;
            padding-right:10px;
            font-size:13px;
            color:#333;
            flex-shrink:0;
        }
        .color-row .picker {
            flex:1;
        }

        /* ★ 声音行专用：textbox + 设置按钮 */
        .color-row .sound-picker {
            flex:1;
            display:flex;
            align-items:center;
            gap:5px;
            min-width:0;
        }
        .color-row .sound-picker .mini-textbox {
            flex:1;
            min-width:0;
        }
        .color-row .sound-picker .mini-button {
            flex-shrink:0;
        }

        .sp-replacer { border-width:1px; }
        .sp-container { z-index:10002 !important; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 工具栏 -->
    <div class="mini-toolbar">
        <button id="btnSave" class="mini-button" plain="true" iconCls="save" onclick="onSave()"></button>
    </div>

    <!-- 三列主体 -->
    <div class="color-body">
        <!-- 第一列：背景色 -->
        <div class="color-column">
            <div class="column-header" id="bgHeader"></div>

            <div class="color-row">
                <span class="label" data-key="goOnlineBg"></span>
                <div class="picker"><input type="text" id="goOnlineBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="onlineBg"></span>
                <div class="picker"><input type="text" id="onlineBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="offlineBg"></span>
                <div class="picker"><input type="text" id="offlineBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="runBg"></span>
                <div class="picker"><input type="text" id="runBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="stopBg"></span>
                <div class="picker"><input type="text" id="stopBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="noDataBg"></span>
                <div class="picker"><input type="text" id="noRunStatusDataBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="normalBg"></span>
                <div class="picker"><input type="text" id="normalBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="firstLevelBg"></span>
                <div class="picker"><input type="text" id="firstLevelBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="secondLevelBg"></span>
                <div class="picker"><input type="text" id="secondLevelBackgroundColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="thirdLevelBg"></span>
                <div class="picker"><input type="text" id="thirdLevelBackgroundColor_id" /></div>
            </div>
        </div>

        <!-- 第二列：前景色 -->
        <div class="color-column">
            <div class="column-header" id="fgHeader"></div>

            <div class="color-row">
                <span class="label" data-key="goOnlineFg"></span>
                <div class="picker"><input type="text" id="goOnlineColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="onlineFg"></span>
                <div class="picker"><input type="text" id="onlineColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="offlineFg"></span>
                <div class="picker"><input type="text" id="offlineColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="runFg"></span>
                <div class="picker"><input type="text" id="runColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="stopFg"></span>
                <div class="picker"><input type="text" id="stopColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="noDataFg"></span>
                <div class="picker"><input type="text" id="noRunStatusDataColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="normalFg"></span>
                <div class="picker"><input type="text" id="normalColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="firstLevelFg"></span>
                <div class="picker"><input type="text" id="firstLevelColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="secondLevelFg"></span>
                <div class="picker"><input type="text" id="secondLevelColor_id" /></div>
            </div>
            <div class="color-row">
                <span class="label" data-key="thirdLevelFg"></span>
                <div class="picker"><input type="text" id="thirdLevelColor_id" /></div>
            </div>
        </div>

        <!-- ★ 第三列：报警声音 -->
        <div class="color-column">
            <div class="column-header" id="soundHeader"></div>

            <div class="color-row">
                <span class="label" id="lblSoundLevel1"></span>
                <div class="sound-picker">
                    <input id="soundLevel1_id" class="mini-textbox" allowInput="false" />
                    <button id="btnSetSound1" class="mini-button" plain="true"
                            onclick="chooseAlarmSound('soundLevel1_id')"></button>
                </div>
            </div>
            <div class="color-row">
                <span class="label" id="lblSoundLevel2"></span>
                <div class="sound-picker">
                    <input id="soundLevel2_id" class="mini-textbox" allowInput="false" />
                    <button id="btnSetSound2" class="mini-button" plain="true"
                            onclick="chooseAlarmSound('soundLevel2_id')"></button>
                </div>
            </div>
            <div class="color-row">
                <span class="label" id="lblSoundLevel3"></span>
                <div class="sound-picker">
                    <input id="soundLevel3_id" class="mini-textbox" allowInput="false" />
                    <button id="btnSetSound3" class="mini-button" plain="true"
                            onclick="chooseAlarmSound('soundLevel3_id')"></button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';

    var _colorData = {};

    var _bgIds = [
        'goOnlineBackgroundColor_id', 'onlineBackgroundColor_id', 'offlineBackgroundColor_id',
        'runBackgroundColor_id', 'stopBackgroundColor_id', 'noRunStatusDataBackgroundColor_id',
        'normalBackgroundColor_id', 'firstLevelBackgroundColor_id', 'secondLevelBackgroundColor_id',
        'thirdLevelBackgroundColor_id'
    ];
    var _fgIds = [
        'goOnlineColor_id', 'onlineColor_id', 'offlineColor_id',
        'runColor_id', 'stopColor_id', 'noRunStatusDataColor_id',
        'normalColor_id', 'firstLevelColor_id', 'secondLevelColor_id', 'thirdLevelColor_id'
    ];

    $(document).ready(function() {
        mini.parse();
        initI18n();
        initSpectrum();
        loadAlarmColor();
    });

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.alarmAlertSettings;

        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(R.save);

        document.getElementById('bgHeader').innerHTML =
            '<font color="red">' + R.backgroundColor + '</font>';
        document.getElementById('fgHeader').innerHTML =
            '<font color="red">' + R.foregroundColor + '</font>';
        document.getElementById('soundHeader').innerHTML =
            '<font color="red">' + R.alarmSound + '</font>';

        var labelMap = {
            goOnlineBg: R.goOnline, onlineBg: R.online, offlineBg: R.offline,
            runBg: R.run, stopBg: R.stop, noDataBg: R.runStatusNoData,
            normalBg: R.normal,
            firstLevelBg: R.alarmLevel1, secondLevelBg: R.alarmLevel2, thirdLevelBg: R.alarmLevel3,
            goOnlineFg: R.goOnline, onlineFg: R.online, offlineFg: R.offline,
            runFg: R.run, stopFg: R.stop, noDataFg: R.runStatusNoData,
            normalFg: R.normal,
            firstLevelFg: R.alarmLevel1, secondLevelFg: R.alarmLevel2, thirdLevelFg: R.alarmLevel3
        };
        var labels = document.querySelectorAll('.color-row .label[data-key]');
        for (var i = 0; i < labels.length; i++) {
            var key = labels[i].getAttribute('data-key');
            if (key && labelMap[key]) labels[i].innerText = labelMap[key] + '：';
        }

        // 声音三个级别 label & 设置按钮
        document.getElementById('lblSoundLevel1').innerText = R.alarmLevel1 + '：';
        document.getElementById('lblSoundLevel2').innerText = R.alarmLevel2 + '：';
        document.getElementById('lblSoundLevel3').innerText = R.alarmLevel3 + '：';
        mini.get('btnSetSound1').setText(R.set);
        mini.get('btnSetSound2').setText(R.set);
        mini.get('btnSetSound3').setText(R.set);
    }

    // ================================================================
    // Spectrum 初始化
    // ================================================================
    function initSpectrum() {
        var palette = [
            ['#000','#444','#666','#999','#ccc','#eee','#f3f3f3','#fff'],
            ['#f00','#f90','#ff0','#0f0','#0ff','#00f','#90f','#f0f'],
            ['#f4cccc','#fce5cd','#fff2cc','#d9ead3','#d0e0e3','#cfe2f3','#d9d2e9','#ead1dc'],
            ['#ea9999','#f9cb9c','#ffe599','#b6d7a8','#a2c4c9','#9fc5e8','#b4a7d6','#d5a6bd'],
            ['#e06666','#f6b26b','#ffd966','#93c47d','#76a5af','#6fa8dc','#8e7cc3','#c27ba0'],
            ['#c00','#e69138','#f1c232','#6aa84f','#45818e','#3d85c6','#674ea7','#a64d79'],
            ['#900','#b45f06','#bf9000','#38761d','#134f5c','#0b5394','#351c75','#741b47'],
            ['#600','#783f04','#7f6000','#274e13','#0c343d','#073763','#20124d','#4c1130']
        ];

        for (var i = 0; i < _bgIds.length; i++) {
            (function (id) {
                $('#' + id).spectrum({
                    color: '#ffffff', showAlpha: true, showInput: true, showInitial: true,
                    showPalette: true, showButtons: false, clickoutFiresChange: true,
                    preferredFormat: 'hex',
                    cancelText: _loginUserLanguageResource.cancel,
                    chooseText: _loginUserLanguageResource.confirm,
                    palette: palette,
                    change: function (color) {
                        var hex = color ? color.toHexString().replace('#', '') : 'ffffff';
                        var alpha = color ? color.getAlpha() : 1;
                        _colorData[id] = { hex: hex, opacity: alpha };
                    }
                });
            })(_bgIds[i]);
        }
        for (var j = 0; j < _fgIds.length; j++) {
            (function (id) {
                $('#' + id).spectrum({
                    color: '#ffffff', showAlpha: false, showInput: true, showInitial: true,
                    showPalette: true, showButtons: false, clickoutFiresChange: true,
                    preferredFormat: 'hex',
                    cancelText: _loginUserLanguageResource.cancel,
                    chooseText: _loginUserLanguageResource.confirm,
                    palette: palette,
                    change: function (color) {
                        var hex = color ? color.toHexString().replace('#', '') : 'ffffff';
                        _colorData[id] = { hex: hex, opacity: 1 };
                    }
                });
            })(_fgIds[j]);
        }
    }

    // ================================================================
    // 加载颜色 + 声音
    // ================================================================
    function loadAlarmColor() {
        var R = _loginUserLanguageResource;
        var mask = mini.mask({ el: document.body, html: R.loadingData });
        $.ajax({
            url: context + '/alarmSetManagerController/getAlarmLevelColor',
            type: 'POST',
            dataType: 'json',
            success: function (data) {
                mini.unmask(document.body);
                if (!data) return;

                // ---- 背景色 ----
                setBgColor('goOnlineBackgroundColor_id',
                    data.Comm && data.Comm.goOnline && data.Comm.goOnline.BackgroundColor,
                    data.Comm && data.Comm.goOnline && data.Comm.goOnline.Opacity);
                setBgColor('onlineBackgroundColor_id',
                    data.Comm && data.Comm.online && data.Comm.online.BackgroundColor,
                    data.Comm && data.Comm.online && data.Comm.online.Opacity);
                setBgColor('offlineBackgroundColor_id',
                    data.Comm && data.Comm.offline && data.Comm.offline.BackgroundColor,
                    data.Comm && data.Comm.offline && data.Comm.offline.Opacity);
                setBgColor('runBackgroundColor_id',
                    data.Run && data.Run.run && data.Run.run.BackgroundColor,
                    data.Run && data.Run.run && data.Run.run.Opacity);
                setBgColor('stopBackgroundColor_id',
                    data.Run && data.Run.stop && data.Run.stop.BackgroundColor,
                    data.Run && data.Run.stop && data.Run.stop.Opacity);
                setBgColor('noRunStatusDataBackgroundColor_id',
                    data.Run && data.Run.noData && data.Run.noData.BackgroundColor,
                    data.Run && data.Run.noData && data.Run.noData.Opacity);
                setBgColor('normalBackgroundColor_id',
                    data.Data && data.Data.Normal && data.Data.Normal.BackgroundColor,
                    data.Data && data.Data.Normal && data.Data.Normal.Opacity);
                setBgColor('firstLevelBackgroundColor_id',
                    data.Data && data.Data.FirstLevel && data.Data.FirstLevel.BackgroundColor,
                    data.Data && data.Data.FirstLevel && data.Data.FirstLevel.Opacity);
                setBgColor('secondLevelBackgroundColor_id',
                    data.Data && data.Data.SecondLevel && data.Data.SecondLevel.BackgroundColor,
                    data.Data && data.Data.SecondLevel && data.Data.SecondLevel.Opacity);
                setBgColor('thirdLevelBackgroundColor_id',
                    data.Data && data.Data.ThirdLevel && data.Data.ThirdLevel.BackgroundColor,
                    data.Data && data.Data.ThirdLevel && data.Data.ThirdLevel.Opacity);

                // ---- 前景色 ----
                setFgColor('goOnlineColor_id',
                    data.Comm && data.Comm.goOnline && data.Comm.goOnline.Color);
                setFgColor('onlineColor_id',
                    data.Comm && data.Comm.online && data.Comm.online.Color);
                setFgColor('offlineColor_id',
                    data.Comm && data.Comm.offline && data.Comm.offline.Color);
                setFgColor('runColor_id',
                    data.Run && data.Run.run && data.Run.run.Color);
                setFgColor('stopColor_id',
                    data.Run && data.Run.stop && data.Run.stop.Color);
                setFgColor('noRunStatusDataColor_id',
                    data.Run && data.Run.noData && data.Run.noData.Color);
                setFgColor('normalColor_id',
                    data.Data && data.Data.Normal && data.Data.Normal.Color);
                setFgColor('firstLevelColor_id',
                    data.Data && data.Data.FirstLevel && data.Data.FirstLevel.Color);
                setFgColor('secondLevelColor_id',
                    data.Data && data.Data.SecondLevel && data.Data.SecondLevel.Color);
                setFgColor('thirdLevelColor_id',
                    data.Data && data.Data.ThirdLevel && data.Data.ThirdLevel.Color);

                // ---- 声音文件 ----
                var f1 = data.Data && data.Data.FirstLevel ? data.Data.FirstLevel.SoundFile : '';
                var f2 = data.Data && data.Data.SecondLevel ? data.Data.SecondLevel.SoundFile : '';
                var f3 = data.Data && data.Data.ThirdLevel ? data.Data.ThirdLevel.SoundFile : '';
                mini.get('soundLevel1_id').setValue(f1 || R.notConfigured);
                mini.get('soundLevel2_id').setValue(f2 || R.notConfigured);
                mini.get('soundLevel3_id').setValue(f3 || R.notConfigured);
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert(R.requestFailed);
            }
        });
    }

    function setBgColor(id, hex, opacity) {
        if (!hex) hex = 'ffffff';
        if (opacity == null) opacity = 1;
        _colorData[id] = { hex: hex, opacity: opacity };
        var rgba = 'rgba(' + hexToRgb(hex) + ',' + opacity + ')';
        $('#' + id).spectrum('set', rgba);
    }

    function setFgColor(id, hex) {
        if (!hex) hex = 'ffffff';
        _colorData[id] = { hex: hex, opacity: 1 };
        $('#' + id).spectrum('set', '#' + hex);
    }

    function hexToRgb(hex) {
        hex = hex.replace('#', '');
        if (hex.length === 3) hex = hex.split('').map(function (c) { return c + c; }).join('');
        var r = parseInt(hex.substring(0, 2), 16);
        var g = parseInt(hex.substring(2, 4), 16);
        var b = parseInt(hex.substring(4, 6), 16);
        return r + ',' + g + ',' + b;
    }

    // ================================================================
    // ★ 打开声音选择弹窗（风格与 selectSceneImage.jsp 一致，走 onConfirmCallback）
    // ================================================================
    function chooseAlarmSound(fieldId) {
        var R = _loginUserLanguageResource;
        var field = mini.get(fieldId);
        var currentVal = field.getValue();
        if (currentVal === R.notConfigured) currentVal = '';

        mini.open({
            url: context + '/miniui-app/modules/driverConfig/alarmSoundSelect.jsp',
            title: R.alarmSoundSelectTitle,
            width: 820,
            height: 580,
            allowResize: true,
            onload: function () {
                var iframe = this.getIFrameEl();
                iframe.contentWindow.onConfirmCallback = function (fileName) {
                    field.setValue(fileName || R.notConfigured);
                };
                iframe.contentWindow.setData({ currentFile: currentVal });
            }
        });
    }

    // ================================================================
    // ★ 保存：先颜色，成功后声音，都成功才算完成
    // ================================================================
    function onSave() {
        var R = _loginUserLanguageResource;

        function pickBg(id) { return _colorData[id] || { hex: 'ffffff', opacity: 1 }; }
        function pickFg(id) { return _colorData[id] || { hex: 'ffffff', opacity: 1 }; }

        var AlarmShowStyle = {
            Data: {
                Normal:      { Value: 0,   BackgroundColor: pickBg('normalBackgroundColor_id').hex,      Color: pickFg('normalColor_id').hex,      Opacity: pickBg('normalBackgroundColor_id').opacity },
                FirstLevel:  { Value: 100, BackgroundColor: pickBg('firstLevelBackgroundColor_id').hex,  Color: pickFg('firstLevelColor_id').hex,  Opacity: pickBg('firstLevelBackgroundColor_id').opacity },
                SecondLevel: { Value: 200, BackgroundColor: pickBg('secondLevelBackgroundColor_id').hex, Color: pickFg('secondLevelColor_id').hex, Opacity: pickBg('secondLevelBackgroundColor_id').opacity },
                ThirdLevel:  { Value: 300, BackgroundColor: pickBg('thirdLevelBackgroundColor_id').hex,  Color: pickFg('thirdLevelColor_id').hex,  Opacity: pickBg('thirdLevelBackgroundColor_id').opacity }
            },
            Comm: {
                goOnline: { Value: 2, BackgroundColor: pickBg('goOnlineBackgroundColor_id').hex, Color: pickFg('goOnlineColor_id').hex, Opacity: pickBg('goOnlineBackgroundColor_id').opacity },
                online:   { Value: 1, BackgroundColor: pickBg('onlineBackgroundColor_id').hex,   Color: pickFg('onlineColor_id').hex,   Opacity: pickBg('onlineBackgroundColor_id').opacity },
                offline:  { Value: 0, BackgroundColor: pickBg('offlineBackgroundColor_id').hex,  Color: pickFg('offlineColor_id').hex,  Opacity: pickBg('offlineBackgroundColor_id').opacity }
            },
            Run: {
                run:    { Value: 1, BackgroundColor: pickBg('runBackgroundColor_id').hex,               Color: pickFg('runColor_id').hex,               Opacity: pickBg('runBackgroundColor_id').opacity },
                stop:   { Value: 0, BackgroundColor: pickBg('stopBackgroundColor_id').hex,              Color: pickFg('stopColor_id').hex,              Opacity: pickBg('stopBackgroundColor_id').opacity },
                noData: { Value: 2, BackgroundColor: pickBg('noRunStatusDataBackgroundColor_id').hex,   Color: pickFg('noRunStatusDataColor_id').hex,   Opacity: pickBg('noRunStatusDataBackgroundColor_id').opacity }
            }
        };

        var mask = mini.mask({ el: document.body, html: R.updateWait });
        $.ajax({
            url: context + '/alarmSetManagerController/setAlarmColor',
            type: 'POST',
            data: { data: JSON.stringify(AlarmShowStyle) },
            dataType: 'json',
            success: function (response) {
                if (response && response.msg) {
                    // 颜色保存成功 → 保存声音
                    saveAlarmSoundConfig(function (success) {
                        mini.unmask(document.body);
                        if (success) {
                            mini.alert('<font color="blue">' + R.updateSuccessfully + '</font>', R.tip, function () {
                                if (window.CloseOwnerWindow) CloseWindow('ok');
                            });
                        } else {
                            mini.alert('<font color="red">' + R.colorSavedSoundFailed + '</font>', R.tip);
                        }
                    });
                } else {
                    mini.unmask(document.body);
                    mini.alert('<font color="red">' + R.updateFailed + '</font>', R.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert('【<font color="red">' + R.exceptionThrow + '</font>】：' + R.contactAdmin, R.tip);
            }
        });
    }

    // 保存声音（"未配置"还原为空字符串）
    function saveAlarmSoundConfig(callback) {
        var R = _loginUserLanguageResource;
        var val1 = mini.get('soundLevel1_id').getValue();
        var val2 = mini.get('soundLevel2_id').getValue();
        var val3 = mini.get('soundLevel3_id').getValue();
        if (val1 === R.notConfigured) val1 = '';
        if (val2 === R.notConfigured) val2 = '';
        if (val3 === R.notConfigured) val3 = '';

        var soundData = { "100": val1, "200": val2, "300": val3 };

        $.ajax({
            url: context + '/alarmSetManagerController/setAlarmSound',
            type: 'POST',
            data: { data: JSON.stringify(soundData) },
            dataType: 'json',
            success: function (response) {
                callback(response && response.success);
            },
            error: function () { callback(false); }
        });
    }

    function onCancel() {
        CloseWindow('cancel');
    }

    function CloseWindow(action) {
        if (window.CloseOwnerWindow) window.CloseOwnerWindow(action);
        else window.close();
    }
</script>
</body>
</html>