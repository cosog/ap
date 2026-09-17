<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入组织</title>
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
              action="<%=context%>/orgManagerController/uploadImportedOrganizationFile"
              method="post" enctype="multipart/form-data" target="uploadFrame" style="display:inline;">
            <input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;"
                   limitType="*.json" onfileselect="onFileSelect" />
            <iframe name="uploadFrame" style="display:none;"></iframe>
        </form>
        <span id="infoLabel" style="color:#2d6a9f;font-size:12px;"></span>
        <span style="flex:1;"></span>
        <button id="saveAllBtn" class="mini-button" iconCls="save" onclick="onSaveAll()"></button>
    </div>

    <!-- 主体：预导入组织树，占满整行 -->
    <div class="tree-wrapper">
        <div id="orgTree" class="mini-treegrid"
             style="width:100%;height:100%;"
             showTreeIcon="true"
             treeColumn="taskname"
             idField="orgId"
             textField="text"
             parentField="orgParent"
             dataField="children"
             resultAsTree="true"
             autoLoad="false"
             onbeforeload="onTreeBeforeLoad"
             onload="onTreeLoad"
             ondrawcell="onTreeDrawcell">
        </div>
    </div>
</div>

<script>
    // ================================================================
    // 上下文
    // ================================================================
    var context = '<%=context%>';

    // ================================================================
    // 1. 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.importOrganization;
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.save);
        var tree = mini.get('orgTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 2. 父窗口调用
    // ================================================================
    function setData(data) {
        // 组织导入无特殊参数，仅在需要时触发一次树加载
        // 首次打开窗口先不加载，等用户上传文件后再加载
    }

    // ================================================================
    // 3. 文件上传
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
                    // 重新加载组织树
                    var tree = mini.get('orgTree');
                    if (tree) {
                    	if(!tree.getUrl()){
                    		tree.setUrl(context + '/orgManagerController/getUploadedOrganizationTreeData');
                    	}
                        tree.load();
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
    // 4. 树事件
    // ================================================================
    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        e.params = params;
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        if (!tree._columnsCreated) {
        	tree.setColumns([
	            {
	                field: 'text',
	                name: 'taskname',
	                header: _loginUserLanguageResource.orgName,
	                headerAlign: 'left',
	                align: 'left',
	                width: '40%'
	            },
	            {
	                field: 'orgSeq',
	                header: _loginUserLanguageResource.sequenceNumber,
	                headerAlign: 'center',
	                align: 'center',
	                width: '10%'
	            },
	            {
	                field: 'msg',
	                header: _loginUserLanguageResource.collisionInfo,
	                headerAlign: 'center',
	                align: 'left',
	                width: '50%'
	            }
	        ]);
            tree._columnsCreated = true;
        }
        
        // 展开全部
        tree.expandAll();
    }

    // ★ 自定义单元格渲染：碰撞信息颜色 + 保存操作链接
    function onTreeDrawcell(e) {
        var field = e.field;
        var record = e.record;

        if (field === 'msg') {
            // 碰撞信息：saveSign 0=正常(黑) 1=已存在覆盖(红) 2=无权限(红)
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
    //全部保存
    // ================================================================
    function onSaveAll() {
        var tree = mini.get('orgTree');
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
                url: context + '/orgManagerController/saveAllImportedOrganization',
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
                    // 刷新本窗口树
                    var tree = mini.get('orgTree');
                    if (tree) {
                        tree.load(context + '/orgManagerController/getUploadedOrganizationTreeData');
                    }
                    // 刷新父窗口组织树
                    if (window.parent && window.parent.refreshOrgTreeAfterImport) {
                        window.parent.refreshOrgTreeAfterImport();
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
    // 7. 页面初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>