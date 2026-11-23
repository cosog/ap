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
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; font-family:"Microsoft YaHei", Arial, sans-serif; }
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

        .sound-body {
            flex:1;
            padding:15px 20px;
            overflow:auto;
            background:#f5f7fa;
        }

        .sound-row {
            display:flex;
            align-items:center;
            margin-bottom:10px;
            padding:10px 14px;
            background:#fff;
            border:1px solid #e8e8e8;
            border-radius:4px;
        }
        .sound-row .label {
            width:100px;
            text-align:right;
            padding-right:12px;
            font-size:13px;
            color:#333;
            flex-shrink:0;
        }
        .sound-row .input-wrapper {
            flex:1;
            min-width:0;
        }
        .sound-row .input-wrapper > .mini-textbox {
            width:100% !important;
            box-sizing:border-box;
        }
        .sound-row .btn-set {
            flex-shrink:0;
            margin-left:8px;
        }
    </style>
</head>
<body>

<div class="main-container">
    <!-- 工具栏 -->
    <div class="mini-toolbar">
        <button id="btnSave" class="mini-button" plain="true" iconCls="save" onclick="onSave()"></button>
    </div>

    <!-- 声音配置主体 -->
    <div class="sound-body">
        <div class="sound-row">
            <span class="label" id="lblLevel1"></span>
            <div class="input-wrapper">
                <input id="soundLevel1_id" class="mini-textbox" allowInput="false" />
            </div>
            <button id="btnSetLevel1" class="mini-button btn-set" plain="true"
                    onclick="chooseAlarmSound('soundLevel1_id')"></button>
        </div>

        <div class="sound-row">
            <span class="label" id="lblLevel2"></span>
            <div class="input-wrapper">
                <input id="soundLevel2_id" class="mini-textbox" allowInput="false" />
            </div>
            <button id="btnSetLevel2" class="mini-button btn-set" plain="true"
                    onclick="chooseAlarmSound('soundLevel2_id')"></button>
        </div>

        <div class="sound-row">
            <span class="label" id="lblLevel3"></span>
            <div class="input-wrapper">
                <input id="soundLevel3_id" class="mini-textbox" allowInput="false" />
            </div>
            <button id="btnSetLevel3" class="mini-button btn-set" plain="true"
                    onclick="chooseAlarmSound('soundLevel3_id')"></button>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';

    $(document).ready(function () {
        mini.parse();
        initI18n();
        loadAlarmSound();
    });

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.alarmAlertSettings;

        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(R.save);

        document.getElementById('lblLevel1').innerText = R.alarmLevel1 + '：';
        document.getElementById('lblLevel2').innerText = R.alarmLevel2 + '：';
        document.getElementById('lblLevel3').innerText = R.alarmLevel3 + '：';

        mini.get('btnSetLevel1').setText(R.set);
        mini.get('btnSetLevel2').setText(R.set);
        mini.get('btnSetLevel3').setText(R.set);
    }

    // ================================================================
    // 加载已配置声音
    // ================================================================
    function loadAlarmSound() {
        var R = _loginUserLanguageResource;
        var mask = mini.mask({ el: document.body, html: R.loadingData });
        $.ajax({
            url: context + '/alarmSetManagerController/getAlarmLevelColor',
            type: 'POST',
            dataType: 'json',
            success: function (data) {
                mini.unmask(document.body);
                if (!data || !data.Data) return;

                var f1 = data.Data.FirstLevel ? data.Data.FirstLevel.SoundFile : '';
                var f2 = data.Data.SecondLevel ? data.Data.SecondLevel.SoundFile : '';
                var f3 = data.Data.ThirdLevel ? data.Data.ThirdLevel.SoundFile : '';

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

    // ================================================================
    // 打开选择弹窗
    // ================================================================
    function chooseAlarmSound(fieldId) {
        var R = _loginUserLanguageResource;
        var field = mini.get(fieldId);
        var currentVal = field.getValue();
        if (currentVal === R.notConfigured) currentVal = '';

        mini.open({
            // ★ 请按实际目录调整
            url: context + '/pages/acquisitionUnit/alarmSoundSelect.jsp',
            title: R.alarmSoundSelectTitle,
            width: 820,
            height: 580,
            allowResize: true,
            onload: function () {
                var iframe = this.getIFrameEl();
                iframe.contentWindow.setData({ currentFile: currentVal });
            },
            ondestroy: function (action) {
                if (action === 'ok') {
                    var iframe = this.getIFrameEl();
                    var fileName = iframe.contentWindow.getSelectedFile();
                    field.setValue(fileName || R.notConfigured);
                }
            }
        });
    }

    // ================================================================
    // 保存声音配置
    // ================================================================
    function onSave() {
        var R = _loginUserLanguageResource;

        var val1 = mini.get('soundLevel1_id').getValue();
        var val2 = mini.get('soundLevel2_id').getValue();
        var val3 = mini.get('soundLevel3_id').getValue();

        // "未配置" 还原为空字符串
        if (val1 === R.notConfigured) val1 = '';
        if (val2 === R.notConfigured) val2 = '';
        if (val3 === R.notConfigured) val3 = '';

        var soundData = {
            "100": val1,
            "200": val2,
            "300": val3
        };

        var mask = mini.mask({ el: document.body, html: R.updateWait });
        $.ajax({
            url: context + '/alarmSetManagerController/setAlarmSound',
            type: 'POST',
            data: { data: JSON.stringify(soundData) },
            dataType: 'json',
            success: function (response) {
                mini.unmask(document.body);
                if (response && response.success) {
                    mini.alert('<font color="blue">' + R.updateSuccessfully + '</font>', R.tip);
                    // 尝试通知父窗口中的声音管理器刷新（兼容 ExtJS 主框架）
                    try {
                        var p = window.parent;
                        if (p && p.AP && p.AP.alert && p.AP.alert.AlarmSoundManager) {
                            p.AP.alert.AlarmSoundManager.refresh();
                        }
                    } catch (e) { }
                } else {
                    mini.alert('<font color="red">' + R.updateFailed + '</font>', R.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert('【<font color="red">' + R.exceptionThrow + '</font>】：' + R.contactAdmin, R.tip);
            }
        });
    }
</script>
</body>
</html>