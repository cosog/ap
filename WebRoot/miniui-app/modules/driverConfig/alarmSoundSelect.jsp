<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
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

        /* 顶部工具栏 */
        .toolbar-row {
            padding:4px 8px;
            border-bottom:1px solid #e8e8e8;
            background:#fafafa;
            display:flex;
            justify-content:space-between;
            align-items:center;
            flex-shrink:0;
        }

        /* 音频画廊 */
        .audio-gallery {
            flex:1;
            padding:10px;
            display:flex;
            flex-wrap:wrap;
            align-content:flex-start;
            gap:10px;
            overflow-y:auto;
            box-sizing:border-box;
        }

        .scene-audio-item {
            width:140px;
            height:140px;
            border:2px solid #e9ecef;
            border-radius:6px;
            padding:10px;
            background:#fff;
            cursor:pointer;
            text-align:center;
            display:flex;
            flex-direction:column;
            align-items:center;
            justify-content:center;
            box-sizing:border-box;
            transition:all 0.2s ease;
            user-select:none;
        }
        .scene-audio-item:hover {
            border-color:#66b1ff;
            box-shadow:0 2px 8px rgba(0,0,0,0.08);
        }
        .scene-audio-item.selected {
            border-color:#4a6cf7;
            background-color:#f4f7ff;
            box-shadow:0 0 6px rgba(74,108,247,0.4);
        }
        .scene-audio-item .icon {
            height:60px;
            font-size:36px;
            color:#1677ff;
            display:flex;
            align-items:center;
            justify-content:center;
            overflow:hidden;
        }
        .scene-audio-item .name {
            font-size:12px;
            color:#666;
            margin-top:10px;
            width:100%;
            overflow:hidden;
            text-overflow:ellipsis;
            white-space:nowrap;
        }
        .scene-audio-item.selected .name {
            color:#4a6cf7;
            font-weight:bold;
        }

        /* 底部 */
        .footer {
            padding:8px 10px;
            border-top:1px solid #e8e8e8;
            background:#fafafa;
            flex-shrink:0;
            display:flex;
            justify-content:space-between;
            align-items:center;
        }
        .footer-left { display:flex; gap:5px; }
        .footer-right { display:flex; gap:5px; }
    </style>
</head>
<body>

<div class="main-container">
    <!-- 工具栏 -->
    <div class="toolbar-row">
        <div>
            <input id="searchField" class="mini-textbox" style="width:220px;" onvaluechanged="onSearchChange" />
        </div>
        <div>
            <button id="btnUpload" class="mini-button" plain="true" onclick="onUploadClick()"></button>
            <button id="btnDelete" class="mini-button" iconCls="delete" plain="true"
                    onclick="onDeleteClick()" enabled="false"></button>
            <button id="btnRefresh" class="mini-button" iconCls="note-refresh" plain="true"
                    onclick="loadAudios()"></button>
        </div>
    </div>

    <!-- 画廊 -->
    <div id="audioGallery" class="audio-gallery"></div>

    <!-- 底部 -->
    <div class="footer">
        <div class="footer-left">
            <button id="btnPlay"  class="mini-button" plain="true" onclick="onPlayPreview()"></button>
            <button id="btnPause" class="mini-button" plain="true" onclick="onPausePreview()"></button>
            <button id="btnStop"  class="mini-button" plain="true" onclick="onStopPreview()"></button>
        </div>
        <div class="footer-right">
            <button id="btnConfirm" class="mini-button" plain="true" iconCls="save" onclick="onConfirm()"></button>
            <button id="btnCancel"  class="mini-button" plain="true" iconCls="cancel" onclick="onCancel()"></button>
        </div>
    </div>
</div>

<input type="file" id="fileInput" accept="audio/*" style="display:none;" onchange="uploadFile(this)" />

<script>
    var context = '<%=context%>';

    var currentFile = '';        // 当前选中
    var configFiles = [];        // 三个级别已配置的音频（不可删除）
    var allAudios = [];
    var currentFilteredAudios = [];
    var _previewAudio = null;

    $(document).ready(function () {
        mini.parse();
        initI18n();
        loadAudios();
    });

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.alarmSoundSelectTitle;

        mini.get('searchField').setEmptyText(R.searchFileName + '...');
        mini.get('btnUpload').setText('📤 ' + R.uploadNewAudio);
        mini.get('btnDelete').setText(R.deleteData);
        mini.get('btnDelete').setTooltip(R.selectAudioFirstTip);
        mini.get('btnRefresh').setText(R.refresh);

        mini.get('btnPlay').setText('▶ ' + R.playAudio);
        mini.get('btnPause').setText('⏸ ' + R.pauseAudio);
        mini.get('btnStop').setText('⏹ ' + R.stopAudio);

        mini.get('btnConfirm').setText(R.confirm);
        mini.get('btnCancel').setText(R.cancel);
    }

    // ================================================================
    // 父页面传参
    // ================================================================
    function setData(data) {
        data = data || {};
        currentFile = data.currentFile || '';
        if (allAudios.length > 0) {
            filterAudios(mini.get('searchField').getValue() || '');
        }
    }

    // ================================================================
    // 加载：先取已配置声音 -> 再取音频池
    // ================================================================
    function loadAudios() {
        $.ajax({
            url: context + '/alarmSetManagerController/getAlarmLevelColor',
            type: 'POST',
            dataType: 'json',
            success: function (r) {
                if (r && r.Data) {
                    configFiles = [];
                    if (r.Data.FirstLevel && r.Data.FirstLevel.SoundFile) configFiles.push(r.Data.FirstLevel.SoundFile);
                    if (r.Data.SecondLevel && r.Data.SecondLevel.SoundFile) configFiles.push(r.Data.SecondLevel.SoundFile);
                    if (r.Data.ThirdLevel && r.Data.ThirdLevel.SoundFile) configFiles.push(r.Data.ThirdLevel.SoundFile);
                }
                _loadAudioPool();
            },
            error: function () { _loadAudioPool(); }
        });
    }

    function _loadAudioPool() {
        $.ajax({
            url: context + '/alarmSetManagerController/getAlarmSoundPool',
            type: 'POST',
            dataType: 'json',
            success: function (r) {
                if (r && r.success) {
                    allAudios = r.data || [];
                    filterAudios(mini.get('searchField').getValue() || '');
                }
            },
            error: function () {
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.error);
            }
        });
    }

    function onSearchChange(e) {
        filterAudios(e.value);
    }

    function filterAudios(keyword) {
        if (!keyword) {
            currentFilteredAudios = allAudios;
        } else {
            currentFilteredAudios = allAudios.filter(function (item) {
                return (item.fileName || '').toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
            });
        }
        renderGallery();
    }

    // ================================================================
    // 渲染画廊
    // ================================================================
    function renderGallery() {
        var container = document.getElementById('audioGallery');
        var R = _loginUserLanguageResource;
        container.innerHTML = '';

        if (currentFilteredAudios.length === 0) {
            container.innerHTML = '<div style="width:100%; text-align:center; padding:40px; color:#aaa;">'
                + R.noAudioFiles + '</div>';
            onSelectionChanged();
            return;
        }

        var html = '';
        for (var i = 0; i < currentFilteredAudios.length; i++) {
            var item = currentFilteredAudios[i];
            var fileName = item.fileName || '';
            var isSelected = (fileName === currentFile);
            var selectedClass = isSelected ? ' selected' : '';
            var safeName = fileName.replace(/"/g, '&quot;');

            html += '<div class="scene-audio-item' + selectedClass + '" data-file="' + safeName + '">';
            html += '  <div class="icon">🎵</div>';
            html += '  <div class="name" title="' + safeName + '">' + fileName + '</div>';
            html += '</div>';
        }
        container.innerHTML = html;

        var items = container.querySelectorAll('.scene-audio-item');
        for (var j = 0; j < items.length; j++) {
            items[j].onclick = function () {
                for (var k = 0; k < items.length; k++) items[k].classList.remove('selected');
                this.classList.add('selected');
                currentFile = this.getAttribute('data-file');
                onSelectionChanged();
            };
            items[j].ondblclick = function () {
                this.click();
                onConfirm();
            };
        }

        var selectedEl = container.querySelector('.scene-audio-item.selected');
        if (selectedEl) {
            (function (el) {
                setTimeout(function () {
                    el.scrollIntoView({ block: 'nearest', inline: 'nearest' });
                }, 100);
            })(selectedEl);
        } else if (items.length > 0 && !currentFile) {
            // 没有历史选中时，默认选中第一个
            items[0].click();
        }
        onSelectionChanged();
    }

    // ================================================================
    // 删除按钮状态
    // ================================================================
    function onSelectionChanged() {
        var R = _loginUserLanguageResource;
        var btnDelete = mini.get('btnDelete');
        if (!btnDelete) return;

        if (!currentFile) {
            btnDelete.setEnabled(false);
            btnDelete.setTooltip(R.selectAudioFirstTip);
            return;
        }
        if (configFiles.indexOf(currentFile) > -1) {
            btnDelete.setEnabled(false);
            btnDelete.setTooltip(R.audioInUseCannotDelete);
        } else {
            btnDelete.setEnabled(true);
            btnDelete.setTooltip(R.deleteSelectedAudio);
        }
    }

    // ================================================================
    // 试听：播放 / 暂停 / 停止
    // ================================================================
    function ensureAudio() {
        if (!_previewAudio) _previewAudio = new Audio();
        return _previewAudio;
    }

    function onPlayPreview() {
        var R = _loginUserLanguageResource;
        if (!currentFile) {
            mini.alert(R.selectOneAudioFirst, R.tip);
            return;
        }
        var audio = ensureAudio();
        var url = context + '/alarmSetManagerController/playAlarmSound?fileName='
            + encodeURIComponent(currentFile) + '&_t=' + new Date().getTime();

        // 同一首暂停中 → 续播
        if (audio.src && audio.src.indexOf(encodeURIComponent(currentFile)) > -1 && audio.paused) {
            try { audio.play(); } catch (e) { mini.alert(R.audioPlayFailed, R.tip); }
            return;
        }
        audio.src = url;
        try {
            var p = audio.play();
            if (p && p.catch) p.catch(function () { mini.alert(R.audioPlayFailed, R.tip); });
        } catch (e) {
            mini.alert(R.audioPlayFailed, R.tip);
        }
    }

    function onPausePreview() {
        if (_previewAudio && !_previewAudio.paused) _previewAudio.pause();
    }

    function onStopPreview() {
        if (_previewAudio) {
            _previewAudio.pause();
            _previewAudio.currentTime = 0;
        }
    }

    // ================================================================
    // 删除
    // ================================================================
    function onDeleteClick() {
        var R = _loginUserLanguageResource;
        if (!currentFile) return;
        if (configFiles.indexOf(currentFile) > -1) {
            mini.alert(R.audioInUseCannotDelete, R.tip);
            return;
        }
        var fileName = currentFile;
        var confirmInfo = R.confirmDeleteAudio + ' [<font color="red">' + fileName + '</font>]?';

        mini.confirm(confirmInfo, R.tip, function (action) {
            if (action !== 'ok') return;
            mini.mask({ el: document.body, html: R.deleting + '...' });
            $.ajax({
                url: context + '/alarmSetManagerController/deleteAlarmSoundPool',
                type: 'POST',
                data: { fileName: fileName },
                dataType: 'json',
                success: function (res) {
                    mini.unmask(document.body);
                    if (res && res.success) {
                        mini.alert(R.deleteSuccessfully, R.tip);
                        onStopPreview();
                        currentFile = '';
                        loadAudios();
                    } else {
                        mini.alert(R.deleteFailed, R.tip);
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(R.networkErrorCannotDelete, R.error);
                }
            });
        });
    }

    // ================================================================
    // 上传
    // ================================================================
    function onUploadClick() {
        document.getElementById('fileInput').click();
    }

    function uploadFile(input) {
        var R = _loginUserLanguageResource;
        var file = input.files[0];
        if (!file) return;

        var formData = new FormData();
        formData.append('file', file);
        mini.mask({ el: document.body, html: R.uploading + '...' });

        $.ajax({
            url: context + '/alarmSetManagerController/uploadAlarmSound',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (res) {
                mini.unmask(document.body);
                input.value = '';
                if (res && res.success) {
                    mini.alert(R.uploadSuccess, R.tip);
                    currentFile = res.fileName || file.name;   // 自动选中刚上传的文件
                    loadAudios();
                } else {
                    mini.alert(R.uploadFail, R.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                input.value = '';
                mini.alert(R.uploadFail, R.error);
            }
        });
    }

    // ================================================================
    // 确定 / 取消（与 selectSceneImage.jsp 一致，走 onConfirmCallback）
    // ================================================================
    function onConfirm() {
        var R = _loginUserLanguageResource;
        if (!currentFile) {
            mini.confirm(R.clearAudioConfirm, R.confirm, function (action) {
                if (action === 'ok') doSelect('');
            });
            return;
        }
        doSelect(currentFile);
    }

    function doSelect(fileName) {
        onStopPreview();
        if (window.onConfirmCallback) window.onConfirmCallback(fileName);
        window.CloseOwnerWindow('ok');
    }

    function onCancel() {
        onStopPreview();
        window.CloseOwnerWindow('cancel');
    }

    window.onbeforeunload = function () {
        onStopPreview();
        _previewAudio = null;
    };
</script>
</body>
</html>