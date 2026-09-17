<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入用户</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar {
            flex-shrink:0; padding:4px 8px; border-bottom:1px solid #e8e8e8;
            background:#fafafa; display:flex; align-items:center; flex-wrap:wrap; gap:4px;
        }
        .grid-wrapper {
            flex: 1;
            min-height: 0;
            overflow: hidden;
            padding: 4px;
            box-sizing: border-box;
        }
        .grid-wrapper .mini-datagrid { width:100%; height:100%; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 工具栏 -->
    <div class="mini-toolbar">
        <span style="font-size:12px;color:#666;" id="uploadLabel"></span>
        <form id="uploadForm"
              action="<%=context%>/userManagerController/uploadImportedUserFile"
              method="post" enctype="multipart/form-data" target="uploadFrame" style="display:inline;">
            <input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;"
                   limitType="*.json" onfileselect="onFileSelect" />
            <iframe name="uploadFrame" style="display:none;"></iframe>
        </form>
        <span id="infoLabel" style="color:#2d6a9f;font-size:12px;"></span>
        <span style="flex:1;"></span>
        <button id="saveAllBtn" class="mini-button" iconCls="save" onclick="onSaveAll()"></button>
    </div>

    <!-- 主体：预导入用户列表 -->
    <div class="grid-wrapper">
        <div id="userGrid" class="mini-datagrid"
             style="width:100%;height:100%;"
             showPager="false"
             allowCellSelect="false"
             allowRowSelect="true"
             allowCellEdit="false"
             idField="userNo"
             dataField="totalRoot"
             totalField="totalCount"
             onbeforeload="onGridBeforeLoad"
             onload="onGridLoad"
             oncellclick="onGridCellClick"
             ondrawcell="onGridDrawcell">
            <div property="columns"></div>
            <div property="emptyText" class="empty-msg">No Data</div>
        </div>
    </div>
</div>

<script>
    // ================================================================
    // 上下文
    // ================================================================
    var context = '<%=context%>';
    var emailEnable = false;
    var raw = (typeof _emailEnable !== 'undefined') ? _emailEnable : false;
    emailEnable = (raw === true || raw === 'true' || raw === 1 || raw === '1');

    // ================================================================
    // 1. 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.importUser;
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.save);
        var grid = mini.get('userGrid');
        if (grid) grid.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 2. 父窗口调用
    // ================================================================
    function setData(data) {
        // 用户导入无特殊参数，仅在用户上传文件后才加载预览数据
    }

    // ================================================================
    // 3. 文件上传
    // ================================================================
    function onFileSelect(e) {
        var form = document.getElementById('uploadForm');
        mini.mask({ el: document.body, html: _loginUserLanguageResource.uploadingFile });
        form.submit();

        var iframe = document.getElementsByName('uploadFrame')[0];
        iframe.onload = function () {
            mini.unmask(document.body);
            try {
                var responseText = iframe.contentWindow.document.body.innerText;
                var result = JSON.parse(responseText);
                if (result && result.flag) {
                    mini.alert(_loginUserLanguageResource.loadSuccessfully);
                    // 重新加载用户列表
                    var grid = mini.get('userGrid');
                    if (grid) {
                        if (!grid.getUrl()) {
                            grid.setUrl(context + '/userManagerController/getUploadedUserTreeData');
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
    // 4. 表格事件
    // ================================================================
    function onGridBeforeLoad(e) {
        var params = e.params || {};
        e.params = params;
    }

    function onGridLoad(e) {
        var grid = e.sender;

        // ★ 首次加载时创建列（只创建一次）
        if (!grid._columnsCreated) {
            createUserGridColumns(grid);
            grid._columnsCreated = true;
        }
    }

    // ================================================================
    // 4.1 单元格点击：预览场景，checkbox 全部只读
    //     与主界面用户列表保持视觉一致，但禁止切换
    // ================================================================
    function onGridCellClick(e) {
        var col = e.column;
        if (!col) return;

        // 命中 checkboxcolumn（有 field 的数据勾选列）→ 直接拦截
        if (col.type === 'checkboxcolumn' && col.field) {
            e.cancel = true;
            return;
        }
    }

    // ================================================================
    // 5. 动态创建列（与主界面用户列表保持一致）
    // ================================================================
    function createUserGridColumns(grid) {
        var columns = [];

        // ---------- 勾选列工厂：与主界面保持一致 ----------
        function checkColumn(field, header, width) {
            return {
                field: field,
                header: header,
                headerAlign: 'center',
                align: 'center',
                width: width,
                trueValue: true,
                falseValue: false,
                type: 'checkboxcolumn'
            };
        }

        // 序号列
        columns.push({
            type: 'indexcolumn',
            width: 50,
            headerAlign: 'center',
            align: 'center',
            header: _loginUserLanguageResource.idx
        });

        // 姓名
        columns.push({
            field: 'userName',
            header: _loginUserLanguageResource.userName,
            headerAlign: 'center',
            align: 'center',
            width: 120
        });

        // 账号
        columns.push({
            field: 'userId',
            header: _loginUserLanguageResource.userAccount,
            headerAlign: 'center',
            align: 'center',
            width: 120
        });

        // 角色
        columns.push({
            field: 'userTypeName',
            header: _loginUserLanguageResource.role,
            headerAlign: 'center',
            align: 'center',
            width: 120
        });

        // 电话
        columns.push({
            field: 'userPhone',
            header: _loginUserLanguageResource.phone,
            headerAlign: 'center',
            align: 'center',
            width: 120
        });

        // 邮箱
        columns.push({
            field: 'userInEmail',
            header: _loginUserLanguageResource.email,
            headerAlign: 'center',
            align: 'center',
            width: 180
        });

        // 快速登录
        columns.push(checkColumn(
            'userQuickLoginName',
            _loginUserLanguageResource.userQuickLogin,
            90
        ));

        // 短信接收（受 emailConfig.enable 控制）
        if (emailEnable) {
            columns.push(checkColumn(
                'receiveSMSName',
                _loginUserLanguageResource.receiveSMS,
                90
            ));
        }

        // 邮件接收（受 emailConfig.enable 控制）
        if (emailEnable) {
            columns.push(checkColumn(
                'receiveMailName',
                _loginUserLanguageResource.receiveMail,
                90
            ));
        }

        // 状态
        columns.push(checkColumn(
            'userEnableName',
            _loginUserLanguageResource.status,
            80
        ));

        // 冲突信息
        columns.push({
            field: 'msg',
            header: _loginUserLanguageResource.collisionInfo,
            headerAlign: 'center',
            align: 'left',
            width: 260
        });

        grid.setColumns(columns);
    }

    // ================================================================
    // 6. 单元格渲染：冲突信息颜色 + 保存操作
    // ================================================================
    function onGridDrawcell(e) {
        var field = e.field;
        var record = e.record;

        if (field === 'msg') {
            // saveSign 0=正常(黑) 1/2=异常(红)
            var value = record.msg || '';
            var color = (record.saveSign == 0) ? '#000000' : '#DC2828';
            e.cellStyle = 'color:' + color + ';';
            e.cellHtml = value
                ? '<span title="' + String(value).replace(/"/g, '&quot;') + '">'
                    + String(value).replace(/</g, '&lt;') + '</span>'
                : '';

        }
    }

    // ================================================================
    // 全部保存
    // ================================================================
    function onSaveAll() {
        var grid = mini.get('userGrid');
        if (!grid) return;

        var rows = grid.getData();
        if (!rows || rows.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        // 统计 saveSign=1（已存在覆盖）、saveSign=2（冲突无权限）
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
                url: context + '/userManagerController/saveAllImportedUser',
                type: 'POST',
                data: {},
                dataType: 'json',
                success: function (result) {
                    mini.unmask(document.body);
                    if (result.success === true) {
                        mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    } else {
                        mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>');
                    }
                    // 刷新本窗口列表
                    var grid = mini.get('userGrid');
                    if (grid) grid.load();
                    // 刷新父窗口用户列表
                    if (window.parent && window.parent.refreshUserListAfterImport) {
                        window.parent.refreshUserListAfterImport();
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }

        if (overlayCount > 0 || collisionCount > 0) {
            mini.confirm(_loginUserLanguageResource.collisionInfo4 + "?",_loginUserLanguageResource.tip,
                function (action) {
                    if (action === 'ok') doSaveAll();
                });
        } else {
            doSaveAll();
        }
    }

    // ================================================================
    // 9. 页面初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>