<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入模块</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar {
            flex-shrink:0; padding:4px 8px; border-bottom:1px solid #e8e8e8;
            background:#fafafa; display:flex; align-items:center; flex-wrap:wrap; gap:4px;
        }
        .tree-wrapper {
            flex: 1;
            min-height: 0;
            overflow: hidden;
            padding: 4px;
            box-sizing: border-box;
        }
        .tree-wrapper .mini-treegrid { width:100%; height:100%; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 工具栏 -->
    <div class="mini-toolbar">
        <span style="font-size:12px;color:#666;" id="uploadLabel"></span>
        <form id="uploadForm"
              action="<%=context%>/moduleManagerController/uploadImportedModuleFile"
              method="post" enctype="multipart/form-data" target="uploadFrame" style="display:inline;">
            <input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;"
                   limitType="*.json" onfileselect="onFileSelect" />
            <iframe name="uploadFrame" style="display:none;"></iframe>
        </form>
        <span id="infoLabel" style="color:#2d6a9f;font-size:12px;"></span>
        <span style="flex:1;"></span>
        <button id="saveAllBtn" class="mini-button" iconCls="save" onclick="onSaveAll()"></button>
    </div>

    <!-- 主体：预导入模块树，占满整行 -->
    <div class="tree-wrapper">
        <div id="moduleTree" class="mini-treegrid"
             style="width:100%;height:100%;"
             showTreeIcon="true"
             treeColumn="taskname"
             idField="mdId"
             textField="text"
             parentField="mdParentid"
             dataField="children"
             resultAsTree="true"
             autoLoad="false"
             allowResize="false"
             allowAlternating="false"
             showPager="false"
             showHGridLines="false"
             showVGridLines="false"
             onbeforeload="onTreeBeforeLoad"
             onload="onTreeLoad"
             ondrawcell="onTreeDrawcell">
            <div property="columns"></div>
            <div property="emptyText" style="color:#999;font-size:13px;text-align:center;padding:20px;"></div>
        </div>
    </div>
</div>

<script>
    // ================================================================
    // 上下文
    // ================================================================
    var context = '<%=context%>';
    var _moduleTreeInited = false;

    // ================================================================
    // 1. 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.importModule;
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.save);
        var tree = mini.get('moduleTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 2. 加载预导入模块树（对应 ExtJS ImportModuleContentTreeInfoStore）
    // ================================================================
    function loadImportedModuleTree() {
        var tree = mini.get('moduleTree');
        if (!tree) return;

        if (!tree.getUrl()) {
            tree.setUrl(context + '/moduleManagerController/getUploadedModuleTreeData');
        }
        tree.load();
    }

    // ================================================================
    // 3. 树加载前：附加参数（与 ExtJS beforeload 一致，无参数）
    // ================================================================
    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        // ExtJS 里 beforeload 传的是空对象，这里保持一致
        e.params = params;
    }

    // ================================================================
    // 4. 树加载完成：初始化列 + 展开全部
    // ================================================================
    function onTreeLoad(e) {
        var tree = e.sender;
        var result = e.result || {};

        // 首次加载时动态创建列
        if (!tree._columnsCreated) {
            createImportedModuleTreeColumns(tree, result);
            tree._columnsCreated = true;
        }

        // 展开全部
        tree.expandAll();
    }

    // ================================================================
    // 5. 动态创建预导入模块树列（对应 ExtJS ImportModuleContentTreeInfoStore.columns）
    // ================================================================
    function createImportedModuleTreeColumns(tree, result) {
        var columns = [
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.moduleName,
                headerAlign: 'left',
                align: 'left',
                width: '35%'
            },
            {
                field: 'msg',
                header: _loginUserLanguageResource.collisionInfo,
                headerAlign: 'left',
                align: 'left',
                width: '65%'
            },
            { field: 'mdId',         visible: false },
            { field: 'mdParentid',   visible: false },
            { field: 'mdType',       visible: false },
            { field: 'classes',      visible: false },
            { field: 'saveSign',     visible: false },
            { field: 'unitName',     visible: false },
            { field: 'protocol',     visible: false }
        ];
        tree.setColumns(columns);
    }

    // ================================================================
    // 6. 自定义单元格渲染：碰撞信息颜色
    //    对应 ExtJS adviceImportModuleCollisionInfoColor
    //    saveSign: 0=正常(黑) 1=已存在覆盖(红) 2=冲突(红)
    // ================================================================
    function onTreeDrawcell(e) {
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
        }
    }

    // ================================================================
    // 7. 父窗口调用：接收参数
    //    （当前无特殊参数，但按规范保留 + 兜底加载）
    // ================================================================
    function setData(data) {
        // 若页面初始化时树还没解析完，setData 时再兜底加载一次
        if (!_moduleTreeInited) {
            setTimeout(function () {
                if (!_moduleTreeInited) {
                    //loadImportedModuleTree();
                    _moduleTreeInited = true;
                }
            }, 50);
        }
    }

    // ================================================================
    // 8. 文件上传
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
                    // 上传成功后重新加载预导入模块树
                    loadImportedModuleTree();
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
    // 9. 全部保存
    // ================================================================
    function onSaveAll() {
        var tree = mini.get('moduleTree');
        if (!tree) return;

        var root = tree.getRootNode();
        if (!root || !root.children || root.children.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        // 统计 saveSign=1（已存在覆盖）、saveSign=2（冲突无权限）
        var overlayCount   = 0;
        var collisionCount = 0;
        (function collect(nodes) {
            for (var i = 0; i < nodes.length; i++) {
                var n = nodes[i];
                if (n.saveSign == 1)      overlayCount++;
                else if (n.saveSign == 2) collisionCount++;
                if (n.children && n.children.length > 0) {
                    collect(n.children);
                }
            }
        })(root.children);

        function doSaveAll() {
            var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });

            $.ajax({
                url: context + '/moduleManagerController/saveAllImportedModule',
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

                    // 刷新本窗口的预导入模块树
                    loadImportedModuleTree();

                    // 通知父窗口刷新模块树 + 通知主界面刷新模块导航树
                    if (window.parent && window.parent.refreshModuleTreeAfterImport) {
                        window.parent.refreshModuleTreeAfterImport();
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

    // ================================================================
    // 10. 页面初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();

        // 对应 ExtJS store 的 autoLoad: true —— 打开窗口即加载一次（无文件时返回空树）
        setTimeout(function () {
            //loadImportedModuleTree();
            _moduleTreeInited = true;
        }, 50);
    });
</script>
</body>
</html>