<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入显示实例</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar { flex-shrink:0; padding:4px 8px; border-bottom:1px solid #e8e8e8; background:#fafafa; display:flex; align-items:center; flex-wrap:wrap; gap:4px; }
        .tree-wrapper { flex: 1; min-height: 0; overflow: hidden; padding: 4px; box-sizing: border-box; }
        .tree-wrapper .mini-treegrid { width:100%; height:100%; }
    </style>
</head>
<body>
<div class="main-container">
    <div class="mini-toolbar">
        <span style="font-size:12px;color:#666;" id="uploadLabel"></span>
        <form id="uploadForm"
              action="<%=context%>/acquisitionUnitManagerController/uploadImportedDisplayInstanceFile"
              method="post" enctype="multipart/form-data" target="uploadFrame" style="display:inline;">
            <input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;"
                   limitType="*.json" onfileselect="onFileSelect" />
            <iframe name="uploadFrame" style="display:none;"></iframe>
        </form>
        <span style="flex:1;"></span>
        <button id="saveAllBtn" class="mini-button" iconCls="save" onclick="onSaveAll()"></button>
    </div>

    <div class="tree-wrapper">
        <div id="instanceTree" class="mini-treegrid"
             style="width:100%;height:100%;"
             showTreeIcon="true" treeColumn="taskname"
             idField="id" parentField="pid" resultAsTree="true"
             onbeforeload="onTreeBeforeLoad"
             onload="onTreeLoad"
             ondrawcell="onTreeDrawcell">
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeId = '';
    var deviceTypeName = '';

    function initI18n() {
        document.title = _loginUserLanguageResource.importDisplayInstance;
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.saveAll);
        var tree = mini.get('instanceTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    function setData(data) {
        if (data) {
            deviceTypeId = data.deviceTypeId || '';
            deviceTypeName = data.deviceTypeName || '';
        }
        //var tree = mini.get('instanceTree');
        //if (tree) {
        //    tree.load(context + '/acquisitionUnitManagerController/getUploadedDisplayInstanceTreeData');
        //}
    }

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
                    var tree = mini.get('instanceTree');
                    if (tree) {
                        tree.load(context + '/acquisitionUnitManagerController/getUploadedDisplayInstanceTreeData');
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

    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceType = deviceTypeId;
        e.params = params;
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        tree.setColumns([
            { field: 'text', name: 'taskname', header: _loginUserLanguageResource.importDisplayInstance,
              headerAlign: 'left', align: 'left', width: '50%' },
            { field: 'msg', header: _loginUserLanguageResource.collisionInfo,
              headerAlign: 'center', align: 'center', width: '35%' },
            { field: 'action', header: _loginUserLanguageResource.save,
              headerAlign: 'center', align: 'center', width: '15%' }
        ]);

        // 默认选中第一个实例节点
        var root = tree.getRootNode();
        if (root && root.children) {
            var targetNode = null;
            (function find(nodes) {
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    if (node.classes === 1) { targetNode = node; return; }
                    if (node.children && node.children.length > 0) {
                        find(node.children);
                        if (targetNode) return;
                    }
                }
            })(root.children);
            if (targetNode) {
                setTimeout(function () { tree.selectNode(targetNode); }, 50);
            }
        }
        tree.expandAll();
    }

    function onTreeDrawcell(e) {
        var field = e.field;
        var record = e.record;

        if (field === 'msg' && record.classes == 1) {
            var value = record.msg || '';
            var color = (record.saveSign == 0) ? '#000000' : '#DC2828';
            e.cellStyle = 'color:' + color + ';';
            e.cellHtml = value ? '<span title="' + value + '">' + value + '</span>' : '';
        } else if (field === 'action' && record.classes == 1) {
            if (record.saveSign != 2) {
                var instanceName = encodeURIComponent(record.text || '');
                var unitName = encodeURIComponent(record.unitName || '');
                var protocolName = encodeURIComponent(record.protocol || '');
                var protocolDeviceType = encodeURIComponent(record.protocolDeviceType || '');
                var saveSign = encodeURIComponent(record.saveSign || '');
                var msg = encodeURIComponent(record.msg || '');
                e.cellHtml = '<a href="javascript:void(0)" onclick="saveSingleImportedDisplayInstance(\''
                    + instanceName + '\',\'' + unitName + '\',\'' + protocolName + '\',\''
                    + protocolDeviceType + '\',\'' + saveSign + '\',\'' + msg + '\')" '
                    + 'style="text-decoration:none;">' + (_loginUserLanguageResource.save) + '...</a>';
            } else {
                e.cellHtml = '';
            }
        }
    }

    function saveSingleImportedDisplayInstance(instanceName, unitName, protocolName, protocolDeviceType, saveSign, msg) {
        instanceName = decodeURIComponent(instanceName);
        unitName = decodeURIComponent(unitName);
        protocolName = decodeURIComponent(protocolName);
        protocolDeviceType = decodeURIComponent(protocolDeviceType);
        saveSign = decodeURIComponent(saveSign);
        msg = decodeURIComponent(msg);

        function doSave() {
            var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
            $.ajax({
                url: context + '/acquisitionUnitManagerController/saveSingelImportedDisplayInstance',
                type: 'POST',
                data: {
                    instanceName: instanceName,
                    unitName: unitName,
                    protocolName: protocolName,
                    protocolDeviceType: protocolDeviceType
                },
                dataType: 'json',
                success: function (result) {
                    mini.unmask(document.body);
                    if (result.success === true) {
                        mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    } else {
                        mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>');
                    }
                    var tree = mini.get('instanceTree');
                    if (tree) {
                        tree.load(context + '/acquisitionUnitManagerController/getUploadedDisplayInstanceTreeData');
                    }
                    if (window.parent && window.parent.refreshDisplayInstanceList) {
                        window.parent.refreshDisplayInstanceList();
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

    function onSaveAll() {
        var tree = mini.get('instanceTree');
        var root = tree.getRootNode();
        var instanceNames = [];
        var overlayCount = 0;
        var collisionCount = 0;

        function collect(node) {
            if (node.classes === 1) {
                if (node.saveSign === 1) overlayCount++;
                else if (node.saveSign === 2) collisionCount++;
                if (node.saveSign !== 2) instanceNames.push(node.text);
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
            }
        }

        if (root && root.children) {
            for (var i = 0; i < root.children.length; i++) collect(root.children[i]);
        }

        if (instanceNames.length === 0) {
            mini.alert(_loginUserLanguageResource.noDataCanBeSaved || '无数据可保存');
            return;
        }

        function doSaveAll() {
            var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
            $.ajax({
                url: context + '/acquisitionUnitManagerController/saveAllImportedDisplayInstance',
                type: 'POST',
                data: { unitName: instanceNames.join(',') },
                dataType: 'json',
                success: function (result) {
                    mini.unmask(document.body);
                    if (result.success === true) {
                        mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    } else {
                        mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>');
                    }
                    var tree = mini.get('instanceTree');
                    if (tree) {
                        tree.load(context + '/acquisitionUnitManagerController/getUploadedDisplayInstanceTreeData');
                    }
                    if (window.parent && window.parent.refreshDisplayInstanceList) {
                        window.parent.refreshDisplayInstanceList();
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }

        if (overlayCount > 0 || collisionCount > 0) {
            var info = '';
            if (overlayCount > 0) {
                info += overlayCount + (_loginUserLanguageResource.importInstanceExistCount || '个实例已存在');
                if (collisionCount > 0) info += '，';
            }
            if (collisionCount > 0) {
                info += collisionCount + (_loginUserLanguageResource.importInstanceNoPermissionCount || '个实例无权限修改');
            }
            info += (_loginUserLanguageResource.importConfirmSaveAll || '！是否执行全部保存？');
            mini.confirm(info, _loginUserLanguageResource.confirm, function (action) {
                if (action === 'ok') doSaveAll();
            });
        } else {
            doSaveAll();
        }
    }

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>