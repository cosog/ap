<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入采控单元</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar { flex-shrink:0; padding:4px 8px; border-bottom:1px solid #e8e8e8; background:#fafafa; display:flex; align-items:center; flex-wrap:wrap; gap:4px; }
        .tree-wrapper { width:100%; height:100%; }
        .mini-treegrid { width:100%; height:100%; }
        .handsontable-container { width:100%; height:100%; }
        .right-tab-area { padding:4px; height:100%; background:#fff; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 工具栏 -->
    <div class="mini-toolbar">
        <span style="font-size:12px;color:#666;" id="uploadLabel">上传文件</span>
        <form id="uploadForm" action="<%=context%>/acquisitionUnitManagerController/uploadImportedAcqUnitFile" method="post" enctype="multipart/form-data" target="uploadFrame" style="display:inline;">
            <input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;" limitType="*.json" onfileselect="onFileSelect" />
            <iframe name="uploadFrame" style="display:none;"></iframe>
        </form>
        <span id="infoLabel"></span>
        <span style="flex:1;"></span>
        <button id="saveAllBtn" class="mini-button" iconCls="save" onclick="onSaveAll()">保存全部</button>
    </div>
    <!-- 主体：左右 Splitter -->
    <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
        <!-- 左侧树 -->
        <div size="25%" showCollapseButton="true" collapseDirection="left" minSize="200">
            <div style="padding:4px;height:100%;background:#fafafa;">
                <div id="unitTree" class="mini-treegrid" 
                     style="width:100%;height:100%;" 
                     showTreeIcon="true" 
                     treeColumn="taskname"
                     idField="id" 
                     parentField="pid" 
                     resultAsTree="true"
                     onbeforeload="onTreeBeforeLoad"
                     onload="onTreeLoad"
                   	 ondrawcell="onTreeDrawcell"
                     onnodeselect="onTreeSelect">
                    <!-- 列将在 onLoad 中动态创建 -->
                </div>
            </div>
        </div>
        <!-- 右侧表格 -->
        <div size="75%" showCollapseButton="false">
            <div style="padding:4px;height:100%;background:#fff;">
                <div id="configContainer" class="handsontable-container"></div>
            </div>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeId = '';
    var deviceTypeName = '';

    // Handsontable 实例
    var importAcqUnitHelper = null;
    // 当前选中的节点
    var currentTreeNode = null;

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.importAcqUnit;
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.saveAll);
        var tree = mini.get('unitTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 接收父窗口数据
    // ================================================================
    function setData(data) {
        if (data && data.deviceTypeId) {
            deviceTypeId = data.deviceTypeId;
            deviceTypeName = data.deviceTypeName || '';
        }
        //var infoLabel = document.getElementById('infoLabel');
        //if (infoLabel && deviceTypeName) {
        //    infoLabel.innerHTML = _loginUserLanguageResource.targetType + ': 【<font color="red">' + deviceTypeName + '</font>】，' + (_loginUserLanguageResource.pleaseConfirm);
        //}
    }

    // ================================================================
    // 文件上传
    // ================================================================
    function onFileSelect(e) {
        var form = document.getElementById('uploadForm');
        mini.mask({ el: document.body, html: _loginUserLanguageResource.uploadingFile || '上传中...' });
        form.submit();
        var iframe = document.getElementsByName('uploadFrame')[0];
        iframe.onload = function() {
            mini.unmask(document.body);
            try {
                var responseText = iframe.contentWindow.document.body.innerText;
                var result = JSON.parse(responseText);
                if (result && result.flag) {
                    mini.alert(_loginUserLanguageResource.loadSuccessfully);
                    var tree = mini.get('unitTree');
                    if (tree) {
                        tree.load(context + '/acquisitionUnitManagerController/getUploadedAcqUnitTreeData');
                    }
                } else {
                    mini.alert(_loginUserLanguageResource.uploadDataError || '上传数据错误');
                }
            } catch(ex) {
                mini.alert(_loginUserLanguageResource.uploadFail || '上传失败');
            }
            iframe.onload = null;
        };
        // 清空 file 控件
        var fileInput = document.getElementById('fileUpload');
        if (fileInput) fileInput.value = '';
    }

    // ================================================================
    // 树事件
    // ================================================================
    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceType = deviceTypeId;
        e.params = params;
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        // 固定列（与 ImportAcqUnitContentTreeInfoStore 的 columns 一致）
        var columns = [
            {
                field: 'text',
                name:"taskname",
                header: _loginUserLanguageResource.importUnit,
                headerAlign: 'left',
                align: 'left',
                width: '40%'
            },
            {
                field: 'msg',
                header: _loginUserLanguageResource.collisionInfo,
                headerAlign: 'center',
                align: 'center',
                width: '40%'
            },
            {
                field: 'action',
                header: _loginUserLanguageResource.save,
                headerAlign: 'center',
                align: 'center',
                width: '20%'
            }
        ];
        tree.setColumns(columns);

        // 默认选中第一个可保存的节点（classes==1 或 classes==2）
        var root = tree.getRootNode();
        if (root && root.children) {
            var targetNode = null;
            // 优先选 classes==2（组），其次 classes==1（单元）
            function findNode(nodes) {
                if (targetNode) return;
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    // 先找 classes==2
                    if (node.classes === 2) {
                        targetNode = node;
                        return;
                    }
                    if (node.children && node.children.length > 0) {
                        findNode(node.children);
                    }
                }
                // 如果没找到 classes==2，再找 classes==1
                if (!targetNode) {
                    for (var i = 0; i < nodes.length; i++) {
                        var node = nodes[i];
                        if (node.classes === 1) {
                            targetNode = node;
                            return;
                        }
                        if (node.children && node.children.length > 0) {
                            findNode(node.children);
                        }
                    }
                }
            }
            findNode(root.children);
            if (targetNode) {
                setTimeout(function() {
                    tree.selectNode(targetNode);
                }, 50);
            }
        }
        // 展开所有节点
        tree.expandAll();
    }
    
    function onTreeDrawcell(e) {
    	var field = e.field;
        var record = e.record;
        if (field === 'msg' && record.classes==1) {
        	onMsgRenderer(e);
        } else if (field === 'action' && record.classes==1) {
        	onActionRenderer(e);
        }
    }

    function onTreeSelect(e) {
        var node = e.node;
        var tree = e.sender;
        if (!node) return;
        currentTreeNode = node;
        // 根据节点类型加载配置数据
        if (node.classes === 0) {
            // 目录节点：如果有子节点，取第一个子节点加载
            if (node.children && node.children.length > 0) {
                var child = node.children[0];
                loadConfigDataByNode(tree,child);
            } else {
                clearHandsontable();
            }
        } else if (node.classes === 1 || node.classes === 2) {
            loadConfigDataByNode(tree,node);
        } else {
            clearHandsontable();
        }
    }

    function loadConfigDataByNode(tree,node) {
        // 根据节点类型准备参数（与 CreateUploadedAcqUnitContentInfoTable 逻辑一致）
        var protocolName = node.protocol || '';
        var protocolDeviceType = node.protocolDeviceType || '';
        var classes = node.classes;
        var unitName = '';
        var groupName = '';
        var groupType = 0;

        if (classes === 1) {
            unitName = node.text || '';
        } else if (classes === 2) {
            // 组节点：需要从父节点取单元名称
            var parent = tree.getParentNode(node);
            if (parent) {
                unitName = parent.text || '';
            }
            groupName = node.text;
            groupType = node.type;
        }

        loadConfigData(protocolName, protocolDeviceType, classes, unitName, groupName, groupType);
    }

    // ================================================================
    // 冲突信息渲染器
    // ================================================================
    function onMsgRenderer(e) {
        var record = e.record;
        var value = record.msg || '';
        var saveSign = record.saveSign;
        var color = '#DC2828';
        if (saveSign == 0) {
            color = '#000000';
        }
        e.cellStyle = 'color:' + color + ';';
        e.cellHtml = value ? '<span title="' + value + '">' + value + '</span>' : '';
    }

    // ================================================================
    // 操作列渲染器（保存按钮）
    // ================================================================
    function onActionRenderer(e) {
        var record = e.record;
        if ((record.classes === 1 || record.classes === 2) && record.saveSign != 2) {
            var unitName = encodeURIComponent(record.text || '');
            var protocolName = encodeURIComponent(record.protocol || '');
            var protocolDeviceType = encodeURIComponent(record.protocolDeviceType || '');
            var saveSign = encodeURIComponent(record.saveSign || '');
            var msg = encodeURIComponent(record.msg || '');
            e.cellHtml = '<a href="javascript:void(0)" onclick="saveSingleUnit(\'' + unitName + '\',\'' + protocolName + '\',\'' + protocolDeviceType + '\',\'' + saveSign + '\',\'' + msg + '\')" style="text-decoration:none;">' + (_loginUserLanguageResource.save || '保存') + '</a>';
        } else {
            e.cellHtml = '';
        }
    }

    // ================================================================
    // 加载配置数据（Handsontable）
    // ================================================================
    function loadConfigData(protocolName, protocolDeviceType, classes, unitName, groupName, groupType) {
        var container = document.getElementById('configContainer');
        if (!container) return;
        // 清空旧表格
        if (importAcqUnitHelper && importAcqUnitHelper.hot) {
            importAcqUnitHelper.hot.destroy();
            importAcqUnitHelper = null;
        }

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.loadingData || '加载中...' });
        $.ajax({
            type: 'POST',
            url: context + '/acquisitionUnitManagerController/getUploadedAcqUnitItemsConfigData',
            data: {
                protocolName: protocolName,
                protocolDeviceType: protocolDeviceType,
                classes: classes,
                unitName: unitName,
                groupName: groupName,
                groupType: groupType
            },
            dataType: 'json',
            success: function(result) {
                mini.unmask(document.body);
                if (!result.success) {
                    mini.alert(result.message || _loginUserLanguageResource.requestFailed);
                    return;
                }
                var data = result.totalRoot || [];
                if (data.length === 0) {
                    // 填充30行空对象
                    for (var i = 0; i < 30; i++) data.push({});
                }
                // 设置面板标题为单元名称
                var title = unitName || groupName || '配置';
                var panel = document.getElementById('configContainer');
                if (panel) {
                    // 如果希望显示标题，可添加顶部标题，但这里简化为直接渲染表格
                }
                importAcqUnitHelper = createAcqUnitHandsontable(container, data, classes, groupType);
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    function createAcqUnitHandsontable(container, data, classes, groupType) {
        var helper = {};
        var colHeaders = [
            _loginUserLanguageResource.idx,
            _loginUserLanguageResource.name,
            _loginUserLanguageResource.startAddress,
            _loginUserLanguageResource.RWType,
            _loginUserLanguageResource.unit,
            _loginUserLanguageResource.resolutionMode,
            '',
            _loginUserLanguageResource.dailyCalculate,
            _loginUserLanguageResource.dailyCalculateColumn
        ];

        var columns = [
            { data: 'id' },
            { data: 'title' },
            { data: 'addr', type: 'text' },
            { data: 'RWType', type: 'dropdown', source: [_loginUserLanguageResource.readOnly, _loginUserLanguageResource.readWrite] },
            { data: 'unit' },
            { data: 'resolutionMode', type: 'dropdown', source: [_loginUserLanguageResource.switchingValue, _loginUserLanguageResource.enumValue, _loginUserLanguageResource.numericValue] },
            { data: 'bitIndex' },
            { data: 'dailyTotalCalculate', type: 'checkbox' },
            { data: 'dailyTotalCalculateName' }
        ];

        var hiddenColumns = [];
        if (classes === 2 && groupType==0) {
            hiddenColumns = [2, 3, 4, 5, 6];
        } else {
            hiddenColumns = [2, 3, 4, 5, 6, 7, 8];
        }

        var hot = new Handsontable(container, {
            licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            theme: 'ht-theme-classic',
            data: data,
            hiddenColumns: {
                columns: hiddenColumns,
                indicators: false,
                copyPasteEnabled: false
            },
            colWidths: [50, 140, 60, 80, 80, 80, 80, 80, 80],
            columns: columns,
            stretchH: 'all',
            rowHeaders: false,
            colHeaders: colHeaders,
            columnSorting: true,
            sortIndicator: true,
            manualColumnResize: true,
            manualRowResize: true,
            filters: true,
            renderAllRows: true,
            search: true,
            outsideClickDeselects: false,
            cells: function(row, col, prop) {
                var cellProperties = {};
                cellProperties.editor = false; // 只读
                return cellProperties;
            }
        });
        helper.hot = hot;
        return helper;
    }

    function clearHandsontable() {
        if (importAcqUnitHelper && importAcqUnitHelper.hot) {
            importAcqUnitHelper.hot.destroy();
            importAcqUnitHelper = null;
        }
        var container = document.getElementById('configContainer');
        if (container) container.innerHTML = '';
    }

    // ================================================================
    // 单个保存
    // ================================================================
    function saveSingleUnit(unitName, protocolName, protocolDeviceType, saveSign, msg) {
        unitName = decodeURIComponent(unitName);
        protocolName = decodeURIComponent(protocolName);
        protocolDeviceType = decodeURIComponent(protocolDeviceType);
        saveSign = decodeURIComponent(saveSign);
        msg = decodeURIComponent(msg);
        if (parseInt(saveSign) > 0) {
            mini.confirm(msg, _loginUserLanguageResource.confirm, function(action) {
                if (action == 'ok') {
                    doSaveSingleUnit(unitName, protocolName, protocolDeviceType);
                }
            });
        } else {
            doSaveSingleUnit(unitName, protocolName, protocolDeviceType);
        }
    }

    function doSaveSingleUnit(unitName, protocolName, protocolDeviceType) {
        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData});
        $.ajax({
            url: context + '/acquisitionUnitManagerController/saveSingelImportedAcqUnit',
            type: 'POST',
            data: {
                unitName: unitName,
                protocolName: protocolName,
                protocolDeviceType: protocolDeviceType
            },
            dataType: 'json',
            success: function(result) {
                mini.unmask(document.body);
                if (result.success) {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully || '保存成功');
                    // 刷新树
                    var tree = mini.get('unitTree');
                    if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedAcqUnitTreeData');
                    // 刷新父页面的单元树
                    if (window.parent && window.parent.refreshAcqUnitTree) {
                        window.parent.refreshAcqUnitTree();
                    }
                } else {
                    mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed || '保存失败') + '</font>');
                }
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed || '请求失败');
            }
        });
    }

    // ================================================================
    // 保存全部
    // ================================================================
    function onSaveAll() {
        var tree = mini.get('unitTree');
        var root = tree.getRootNode();
        var unitNames = [];
        function collect(node) {
            if (node.classes === 1 && node.saveSign !== 2) {
                unitNames.push(node.text);
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                }
            }
        }
        if (root && root.children) {
            for (var i = 0; i < root.children.length; i++) {
                collect(root.children[i]);
            }
        }
        if (unitNames.length === 0) {
            mini.alert(_loginUserLanguageResource.noDataCanBeSaved);
            return;
        }
        alert(unitNames.join(','));
        mini.confirm(_loginUserLanguageResource.confirmOperation, _loginUserLanguageResource.confirm, function(action) {
            if (action == 'ok') {
                var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
                $.ajax({
                    url: context + '/acquisitionUnitManagerController/saveAllImportedAcqUnit',
                    type: 'POST',
                    data: {
                        unitName: unitNames.join(',')
                    },
                    dataType: 'json',
                    success: function(result) {
                        mini.unmask(document.body);
                        if (result.success) {
                            mini.alert(_loginUserLanguageResource.savedSuccessfully);
                            var tree = mini.get('unitTree');
                            if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedAcqUnitTreeData');
                            if (window.parent && window.parent.refreshAcqUnitTree) {
                                window.parent.refreshAcqUnitTree();
                            }
                        } else {
                            mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed ) + '</font>');
                        }
                    },
                    error: function() {
                        mini.unmask(document.body);
                        mini.alert(_loginUserLanguageResource.requestFailed);
                    }
                });
            }
        });
    }

    // ================================================================
    // 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>