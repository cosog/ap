<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入角色</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0;
            width: 100%; height: 100%;
            overflow: hidden; background: #f5f5f5;
        }
    </style>
</head>
<body>

<div id="importRolePanel" class="mini-panel"
     style="width:100%;height:100%;"
     showHeader="false"
     showToolbar="true"
     showCloseButton="false"
     bodyStyle="padding:0;">

    <!-- ★ 标准工具条 -->
    <div property="toolbar" style="padding:4px 8px;background: #fafafa;">
        <span id="uploadLabel" style="font-size:12px;color:#666;"></span>
        <form id="uploadForm"
              action="<%=context%>/roleManagerController/uploadImportedRoleFile"
              method="post" enctype="multipart/form-data" target="uploadFrame"
              style="display:inline;">
            <input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;"
                   limitType="*.json" onfileselect="onFileSelect" />
            <iframe name="uploadFrame" style="display:none;"></iframe>
        </form>
        <span id="infoLabel" style="color:#2d6a9f;font-size:12px;margin-left:8px;"></span>

        <!-- ★ 右侧按钮组 -->
        <div style="float:right;">
            <button id="saveAllBtn" class="mini-button" iconCls="save" plain="true"
                    onclick="onSaveAll()"></button>
        </div>
    </div>

    <!-- body 内容 -->
    <div id="roleGrid" class="mini-datagrid"
         style="width:100%;height:100%;"
         showPager="false"
         allowCellSelect="false"
         allowRowSelect="true"
         allowCellEdit="false"
         idField="roleId"
         dataField="totalRoot"
         totalField="totalCount"
         onbeforeload="onGridBeforeLoad"
         onload="onGridLoad"
         ondrawcell="onGridDrawcell">
        <div property="columns"></div>
        <div property="emptyText" class="empty-msg">No Data</div>
    </div>

</div>

<script>
    var context = '<%=context%>';
    var loginUserLanguageList = [];
    var loginUserLanguage = null;

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;

        document.title = R.importRole;

        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = R.uploadFile;

        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(R.save);

        var grid = mini.get('roleGrid');
        if (grid) grid.setEmptyText(R.emptyMsg);
    }

    // ================================================================
    // 父窗口调用
    // ================================================================
    function setData(data) {
        if (!data) data = {};
        loginUserLanguageList = data.loginUserLanguageList || [];
        loginUserLanguage= data.loginUserLanguage;
    }

    // ================================================================
    // 文件上传
    // ================================================================
    function onFileSelect(e) {
        var form = document.getElementById('uploadForm');
        mini.mask({ el: document.body, html: _loginUserLanguageResource.uploadingFile || '上传中...' });
        form.submit();

        var iframe = document.getElementsByName('uploadFrame')[0];
        iframe.onload = function () {
            mini.unmask(document.body);
            try {
                var responseText = iframe.contentWindow.document.body.innerText;
                var result = JSON.parse(responseText);
                if (result && result.flag) {
                    mini.alert(_loginUserLanguageResource.loadSuccessfully);
                    var grid = mini.get('roleGrid');
                    if (grid) {
                        if (!grid.getUrl()) {
                            grid.setUrl(context + '/roleManagerController/getUploadedRoleTreeData');
                        }
                        grid.load();
                    }
                } else {
                    mini.alert(_loginUserLanguageResource.uploadDataError);
                }
            } catch (ex) {
                mini.alert(_loginUserLanguageResource.uploadFail);
            }
            iframe.onload = null;
        };

        var fileInput = document.getElementById('fileUpload');
        if (fileInput) fileInput.value = '';
    }

    // ================================================================
    // 表格事件
    // ================================================================
    function onGridBeforeLoad(e) {
        e.params = e.params || {};
    }

    function onGridLoad(e) {
        var grid = e.sender;
        if (!grid._columnsCreated) {
            createRoleGridColumns(grid);
            grid._columnsCreated = true;
        }
    }

    // ================================================================
    // 动态创建列
    // ================================================================
    function createRoleGridColumns(grid) {
        var columns = [];

        function hasLanguage(langValue) {
            for (var i = 0; i < loginUserLanguageList.length; i++) {
                if (String(loginUserLanguageList[i]) === String(langValue)) return true;
            }
            return false;
        }

        columns.push({
            type: 'indexcolumn', width: 50,
            headerAlign: 'center', align: 'center',
            header: _loginUserLanguageResource.idx
        });

        columns.push({
            field: 'roleName_zh_CN',
            header: _loginUserLanguageResource.language_zh_CN,
            headerAlign: 'center', align: 'center', width: 130,
            visible: hasLanguage(1)
        });

        columns.push({
            field: 'roleName_en',
            header: _loginUserLanguageResource.language_en,
            headerAlign: 'center', align: 'center', width: 130,
            visible: hasLanguage(2)
        });

        columns.push({
            field: 'roleName_ru',
            header: _loginUserLanguageResource.language_ru,
            headerAlign: 'center', align: 'center', width: 130,
            visible: hasLanguage(3)
        });

        columns.push({
            field: 'roleLevel',
            header: _loginUserLanguageResource.roleLevel,
            headerAlign: 'center', align: 'center', width: 80
        });

        columns.push({
            field: 'showLevel',
            header: _loginUserLanguageResource.dataShowLevel,
            headerAlign: 'center', align: 'center', width: 100
        });

        columns.push({
            field: 'roleVideoKeyEditName',
            trueValue: true,
            falseValue: false,
            type: 'checkboxcolumn',
            header: _loginUserLanguageResource.roleVideoKeyEdit,
            headerAlign: 'center', align: 'center', width: 90
        });

        var remarkField = 'remark_zh_CN';
        var lang = (loginUserLanguage || '').toUpperCase();
        if (lang === 'EN') remarkField = 'remark_en';
        else if (lang === 'RU') remarkField = 'remark_ru';

        columns.push({
            field: remarkField,
            header: _loginUserLanguageResource.roleRemark,
            headerAlign: 'center', align: 'left', width: 200
        });

        columns.push({
            field: 'msg',
            header: _loginUserLanguageResource.collisionInfo,
            headerAlign: 'center', align: 'left', width: 260
        });

        grid.setColumns(columns);
    }

    // ================================================================
    // 单元格渲染
    // ================================================================
    function onGridDrawcell(e) {
        var field = e.field;
        var record = e.record;

        if (field === 'msg') {
            var value = record.msg || '';
            var color = (record.saveSign == 0) ? '#000000' : '#DC2828';
            e.cellStyle = 'color:' + color + ';';
            e.cellHtml = value
                ? '<span title="' + String(value).replace(/"/g, '&quot;') + '">'
                    + String(value).replace(/</g, '&lt;') + '</span>'
                : '';
        } else if (field === 'action') {
            if (record.saveSign != 2) {
                var roleName = encodeURIComponent(record.roleName_zh_CN
                    || record.roleName_en
                    || record.roleName_ru || '');
                var saveSign = encodeURIComponent(record.saveSign || '');
                var msg      = encodeURIComponent(record.msg || '');
                e.cellHtml = '<a href="javascript:void(0)" onclick="saveSingleImportedRole(\''
                    + roleName + '\',\'' + saveSign + '\',\'' + msg + '\')" '
                    + 'style="text-decoration:none;">' + (_loginUserLanguageResource.save) + '...</a>';
            } else {
                e.cellHtml = '';
            }
        }
    }

    // ================================================================
    // 单个保存
    // ================================================================
    function saveSingleImportedRole(roleName, saveSign, msg) {
        roleName = decodeURIComponent(roleName);
        saveSign = decodeURIComponent(saveSign);
        msg      = decodeURIComponent(msg);

        function doSave() {
            var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });

            $.ajax({
                url: context + '/roleManagerController/saveSingelImportedRole',
                type: 'POST',
                data: { roleName: roleName, saveSign: saveSign, msg: msg },
                dataType: 'json',
                success: function (result) {
                    mini.unmask(document.body);
                    if (result.success === true || result.flag === true) {
                        mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    } else {
                        mini.alert('<font color="red">'
                            + _loginUserLanguageResource.saveFailed + '</font>');
                    }
                    var grid = mini.get('roleGrid');
                    if (grid) grid.load();
                    if (window.parent && window.parent.refreshRoleListAfterImport) {
                        window.parent.refreshRoleListAfterImport();
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }

        if (parseInt(saveSign) > 0) {
            mini.confirm(msg, _loginUserLanguageResource.confirm, function (action) {
                if (action === 'ok') doSave();
            });
        } else {
            doSave();
        }
    }

    // ================================================================
    // 全部保存
    // ================================================================
    function onSaveAll() {
        var grid = mini.get('roleGrid');
        if (!grid) return;

        var rows = grid.getData();
        if (!rows || rows.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        var overlayCount   = 0;
        var collisionCount = 0;
        for (var i = 0; i < rows.length; i++) {
            var s = rows[i].saveSign;
            if (s == 1)      overlayCount++;
            else if (s == 2) collisionCount++;
        }

        function doSaveAll() {
            var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });

            $.ajax({
                url: context + '/roleManagerController/saveAllImportedRole',
                type: 'POST',
                data: {},
                dataType: 'json',
                success: function (result) {
                    mini.unmask(document.body);
                    if (result.success === true) {
                        mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    } else {
                        mini.alert('<font color="red">'+ _loginUserLanguageResource.saveFailed + '</font>');
                    }
                    var grid = mini.get('roleGrid');
                    if (grid) grid.load();
                    if (window.parent && window.parent.refreshRoleListAfterImport) {
                        window.parent.refreshRoleListAfterImport();
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }

        if (overlayCount > 0 || collisionCount > 0) {
            mini.confirm(_loginUserLanguageResource.collisionInfo4 + "?",
                _loginUserLanguageResource.tip,
                function (action) {
                    if (action === 'ok') doSaveAll();
                });
        } else {
            doSaveAll();
        }
    }

    function isTrueVal(v) {
        return v === true || v === 1 || v === '1' || v === 'true';
    }

    // ================================================================
    // 页面初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>