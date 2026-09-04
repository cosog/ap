<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入协议</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar { flex-shrink:0; padding:4px 8px; border-bottom:1px solid; display:flex; align-items:center; flex-wrap:wrap; gap:4px; }
        .tree-wrapper { width:100%; height:100%; }
        .mini-tree { width:100%; height:100%; }
        .handsontable-container { width:100%; height:100%; }
        .right-tab-area { padding:4px; height:100%; background:#fff; }
        .tab-content-wrapper { height:100%; display:flex; flex-direction:column; }
        .tab-body { flex:1; overflow:hidden; padding:0; }
    </style>
</head>
<body>
<div class="main-container">
	<div class="mini-toolbar">
    	<span style="font-size:12px;color:#666;" id="uploadLabel">上传文件</span>
    	<form id="uploadForm" action="<%=context%>/acquisitionUnitManagerController/uploadImportedProtocolFile" method="post" enctype="multipart/form-data" target="uploadFrame" style="display:inline;">
        	<input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;" limitType="*.json" onfileselect="onFileSelect" />
        	<iframe name="uploadFrame" style="display:none;"></iframe>
    	</form>
    	<span id="infoLabel"></span>
    	<span style="flex:1;"></span>
    	<button id="saveAllBtn" class="mini-button" iconCls="save" onclick="onSaveAll()">保存全部</button>
	</div>
    <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
        <!-- 左侧区域 -->
        <div size="25%" showCollapseButton="true" collapseDirection="left" minSize="200">
    		<div class="mini-panel" id="uploadProtocolListPanel" 
         		title="" 
         		style="width:100%;height:100%;" 
         		showCollapseButton="false" 
         		showCloseButton="false" 
         		allowResize="false" 
         		bodyStyle="padding:4px;">
        		<div id="protocolTree" class="mini-treegrid"
             		style="width:100%;height:100%;" 
             		showTreeIcon="true" 
             		treeColumn="taskname"
             		idField="nodeId" 
             		parentField="nodeParentId" 
             		resultAsTree="true"
             		onbeforeload="onTreeBeforeLoad"
             		onload="onTreeLoad"
             		ondrawcell="onTreeDrawcell"
             		onnodeselect="onTreeSelect">
            		<!-- 列将在 onLoad 中动态创建 -->
        		</div>
    		</div>
		</div>
        <!-- 右侧区域 -->
        <div size="75%" showCollapseButton="false">
            <div class="right-tab-area">
                <div id="mainTabs" class="mini-tabs" style="width:100%;height:calc(100% - 36px);" activeIndex="0" onactivechanged="onTabChanged">
                    <div title="配置" name="config" style="height:100%;">
                        <div class="tab-content-wrapper">
                            <div class="tab-body" style="width:100%;height:100%;">
                                <div id="configHandsontableContainer" class="handsontable-container" style="width:100%;height:100%;"></div>
                            </div>
                        </div>
                    </div>
                    <div title="扩展字段" name="extended" style="height:100%;">
                        <div class="tab-content-wrapper">
                            <div class="tab-body" style="width:100%;height:100%;">
                                <div id="extendedHandsontableContainer" class="handsontable-container" style="width:100%;height:100%;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeId = '';
    var deviceTypeName = '';

    // Handsontable 实例
    var importConfigHelper = null;
    var importExtendedHelper = null;

    // 当前选中的协议节点
    var currentProtocolNode = null;

    // ================================================================
    // 1. 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.importProtocol;
        var uploadProtocolListPanel = mini.get('uploadProtocolListPanel');
        if (uploadProtocolListPanel) {
        	uploadProtocolListPanel.setTitle(_loginUserLanguageResource.uploadProtocolList || '上传协议列表');
        }
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.saveAll);
        var tabs = mini.get('mainTabs');
        if (tabs) {
            var tabsData = tabs.getTabs();
            if (tabsData && tabsData.length >= 2) {
                tabs.updateTab(tabsData[0], { title: _loginUserLanguageResource.config});
                tabs.updateTab(tabsData[1], { title: _loginUserLanguageResource.extendedField});
            }
        }
        var tree = mini.get('protocolTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 2. 接收父窗口参数
    // ================================================================
    function setData(data) {
        if (data && data.deviceTypeId) {
            deviceTypeId = data.deviceTypeId;
            deviceTypeName = data.deviceTypeName || '';
        }
        // 显示设备类型提示
        var infoLabel = document.getElementById('infoLabel');
        if (infoLabel && deviceTypeName) {
            infoLabel.innerHTML = _loginUserLanguageResource.owningDeviceType + ': <font color="red">' + deviceTypeName + '</font>，' + (_loginUserLanguageResource.pleaseConfirm || '请确认');
        }
    }

    // ================================================================
    // 3. 文件上传
    // ================================================================
    
    function onFileSelect(e) {
    var form = document.getElementById('uploadForm');
    // 显示遮罩
    mini.mask({ el: document.body, html: loginUserLanguageResource.uploadingFile+'...'});
    // 提交表单
    form.submit();
    // 监听 iframe 加载完成，解析返回结果
    var iframe = document.getElementsByName('uploadFrame')[0];
    iframe.onload = function() {
        mini.unmask(document.body);
        try {
            var responseText = iframe.contentWindow.document.body.innerText;
            var result = JSON.parse(responseText);
            if (result && result.flag) {
                mini.alert(_loginUserLanguageResource.loadSuccessfully);
                // 重新加载树
                var tree = mini.get('protocolTree');
                if (tree) {
                    tree.load(context + '/acquisitionUnitManagerController/getUploadedProtocolTreeData');
                }
            } else {
                mini.alert(_loginUserLanguageResource.uploadDataError);
            }
        } catch(ex) {
            mini.alert(_loginUserLanguageResource.uploadFail);
        }
        // 重置 iframe onload 避免多次绑定
        iframe.onload = null;
    };
}
    

    // ================================================================
    // 4. 树事件
    // ================================================================
    
    function onTreeBeforeLoad(e) {
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        var result = e.result; // 服务端返回的完整数据
        var root = tree.getRootNode();
        if (!root) return;

        var miniColumns = [
            {
                field: 'text',           // 树节点显示字段
                name:"taskname",
                header: _loginUserLanguageResource.importProtocol,
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
        tree.setColumns(miniColumns);

        // 2. 选中第一个协议节点
        var protocolNodes = [];
        function collect(node) {
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
            } else {
                if (node.classes == 1) protocolNodes.push(node);
            }
        }
        collect(root);

        if (protocolNodes.length > 0) {
            var targetNode = protocolNodes[0];
            setTimeout(function() {
                tree.selectNode(targetNode);
            }, 50);
        }
    }
    
    function onTreeDrawcell(e) {
    	var field = e.field;
        var record = e.record;
        if (field === 'msg') {
            var value = record.msg || '';
            var saveSign = record.saveSign;
            var color = '#DC2828';
            if (saveSign == 0) {
                color = '#000000';
            }
            e.cellStyle = 'color:' + color + ';';
            e.cellHtml = value ? '<span title="' + value + '">' + value + '</span>' : '';
        } else if (field === 'action') {
            if (record.classes == 1 && record.saveSign != 2) {
                var protocolName = encodeURIComponent(record.text || '');
                var deviceType = encodeURIComponent(deviceTypeId || '');
                var saveSign = encodeURIComponent(record.saveSign || '');
                var msg = encodeURIComponent(record.msg || '');
                e.cellHtml = '<a href="javascript:void(0)" onclick="saveSingleProtocol(\'' + protocolName + '\',\'' + deviceType + '\',\'' + saveSign + '\',\'' + msg + '\')" style="text-decoration:none;">' + (_loginUserLanguageResource.save) + '</a>';
            } else {
                e.cellHtml = '';
            }
        }
    }
    

    function onTreeSelect(e) {
        var node = e.node;
        if (node && node.classes === 1) {
            currentProtocolNode = node;
            // 根据当前激活的Tab加载详情
            var tabs = mini.get('mainTabs');
            var active = tabs.getActiveTab();
            if (active) {
                if (active.name === 'config') {
                    loadConfigDetail(node);
                } else if (active.name === 'extended') {
                    loadExtendedDetail(node);
                }
            }
        }
    }

    // ================================================================
    // 5. Tab切换
    // ================================================================
    function onTabChanged(e) {
        if (!currentProtocolNode) return;
        var tabs = mini.get('mainTabs');
        var active = tabs.getActiveTab();
        if (active) {
            if (active.name === 'config') {
                loadConfigDetail(currentProtocolNode);
            } else if (active.name === 'extended') {
                loadExtendedDetail(currentProtocolNode);
            }
        }
    }

    // ================================================================
    // 6. 加载配置详情 (Handsontable)
    // ================================================================
    function loadConfigDetail(node) {
        var protocolCode = node.code;
        var protocolName = node.text;
        var classes = node.classes;

        var container = document.getElementById('configHandsontableContainer');
        if (!container) return;

        // 显示加载状态
        var loading = mini.loading(_loginUserLanguageResource.loadingData, _loginUserLanguageResource.tip);
        $.ajax({
            type: 'POST',
            url: context + '/acquisitionUnitManagerController/getUploadedProtocolItemsConfigData',
            data: {
                protocolName: protocolName,
                classes: classes,
                code: protocolCode
            },
            dataType: 'json',
            success: function(result) {
                mini.hideMessageBox(loading);
                var data = result.totalRoot || [];
                // 如果数据为空，填充30行空对象
                if (data.length === 0) {
                    for (var i = 0; i < 30; i++) data.push({});
                }
                if (importConfigHelper) {
                    if (importConfigHelper.hot) importConfigHelper.hot.destroy();
                    importConfigHelper = null;
                }
                importConfigHelper = createConfigHandsontable(container, data);
            },
            error: function() {
                mini.hideMessageBox(loading);
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    // 创建配置Handsontable（只读）
    function createConfigHandsontable(container, data) {
        var helper = {};
        var colHeaders = [
            ['', '', { label: _loginUserLanguageResource.lowerComputer, colspan: 5 }, { label: _loginUserLanguageResource.upperComputer, colspan: 5 }],
            [
                _loginUserLanguageResource.idx,
                _loginUserLanguageResource.name,
                _loginUserLanguageResource.startAddress,
                _loginUserLanguageResource.storeDataType,
                _loginUserLanguageResource.quantity,
                _loginUserLanguageResource.RWType,
                _loginUserLanguageResource.acqMode,
                _loginUserLanguageResource.IFDataType,
                _loginUserLanguageResource.prec,
                _loginUserLanguageResource.ratio,
                _loginUserLanguageResource.unit,
                _loginUserLanguageResource.resolutionMode
            ]
        ];

        var columns = [
            { data: 'id' },
            { data: 'title' },
            { data: 'addr', type: 'text' },
            { data: 'storeDataType', type: 'dropdown', source: ['bit','byte','int16','uint16','float32','float64','bcd'] },
            { data: 'quantity', type: 'text' },
            { data: 'RWType', type: 'dropdown', source: [_loginUserLanguageResource.readOnly, _loginUserLanguageResource.writeOnly, _loginUserLanguageResource.readWrite] },
            { data: 'acqMode', type: 'dropdown', source: [_loginUserLanguageResource.activeAcqModel, _loginUserLanguageResource.passiveAcqModel] },
            { data: 'IFDataType', type: 'dropdown', source: ['bool','int','float32','float64','string'] },
            { data: 'prec', type: 'text' },
            { data: 'ratio', type: 'text' },
            { data: 'unit' },
            { data: 'resolutionMode', type: 'dropdown', source: [_loginUserLanguageResource.switchingValue, _loginUserLanguageResource.enumValue, _loginUserLanguageResource.numericValue] }
        ];

        var hot = new Handsontable(container, {
            licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            theme: 'ht-theme-classic',
            data: data,
            //colWidths: [50, 130, 80, 90, 90, 80, 80, 90, 80, 80, 80, 80],
            columns: columns,
            stretchH: 'all',          // 列水平拉伸填满容器
            width: '100%',            // 表格宽度填满容器
            height: '100%',           // 高度填满容器
            rowHeaders: false,
            nestedHeaders: colHeaders,
            nestedRows: true,
            columnHeaderHeight: 28,
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

    // ================================================================
    // 7. 加载扩展字段详情 (Handsontable)
    // ================================================================
    function loadExtendedDetail(node) {
        var protocolCode = node.code;
        var protocolName = node.text;
        var classes = node.classes;

        var container = document.getElementById('extendedHandsontableContainer');
        if (!container) return;

        var loading = mini.loading(_loginUserLanguageResource.loadingData, _loginUserLanguageResource.tip);
        $.ajax({
            type: 'POST',
            url: context + '/acquisitionUnitManagerController/getUploadedProtocolExtendedFieldsConfigData',
            data: {
                protocolName: protocolName,
                classes: classes,
                code: protocolCode
            },
            dataType: 'json',
            success: function(result) {
                mini.hideMessageBox(loading);
                var data = result.totalRoot || [];
                if (data.length === 0) {
                    for (var i = 0; i < 20; i++) data.push({});
                }
                if (importExtendedHelper) {
                    if (importExtendedHelper.hot) importExtendedHelper.hot.destroy();
                    importExtendedHelper = null;
                }
                importExtendedHelper = createExtendedHandsontable(container, data, result.operationList, result.additionalConditionsList);
            },
            error: function() {
                mini.hideMessageBox(loading);
                mini.alert(_loginUserLanguageResource.requestFailed || '请求失败');
            }
        });
    }

    function createExtendedHandsontable(container, data, operationList, additionalConditionsList) {
        var helper = {};
        var colHeaders = [
            _loginUserLanguageResource.idx,
            _loginUserLanguageResource.name,
            (_loginUserLanguageResource.dataColumn) + '1',
            _loginUserLanguageResource.fourOperation,
            (_loginUserLanguageResource.dataColumn) + '2',
            _loginUserLanguageResource.prec,
            _loginUserLanguageResource.ratio,
            _loginUserLanguageResource.unit,
            _loginUserLanguageResource.additionalConditions
        ];

        var columns = [
            { data: 'id' },
            { data: 'title' },
            { data: 'title1' },
            { data: 'operation', type: 'dropdown', source: operationList || [] },
            { data: 'title2' },
            { data: 'prec', type: 'text' },
            { data: 'ratio', type: 'text' },
            { data: 'unit' },
            { data: 'additionalConditions', type: 'dropdown', source: additionalConditionsList || [] }
        ];

        var hot = new Handsontable(container, {
            licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            theme: 'ht-theme-classic',
            data: data,
            colWidths: [50, 200, 200, 80, 200, 80, 80, 80, 150],
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
            cells: function(row, col, prop) {
                var cellProperties = {};
                cellProperties.editor = false; // 只读
                // 占位符处理：如果值为空，显示提示文本
                if (!this.instance.getDataAtCell(row, col) && (col === 2 || col === 4)) {
                    cellProperties.renderer = function(instance, td, row, col, prop, value, cellProperties) {
                        Handsontable.renderers.TextRenderer.apply(this, arguments);
                        if (!value || value === '') {
                            td.style.color = 'gray';
                            td.style.fontStyle = 'italic';
                            td.innerHTML = (_loginUserLanguageResource.doubleClickCellTip) + '...';
                        }
                    };
                }
                return cellProperties;
            }
        });
        helper.hot = hot;
        return helper;
    }
    
    function saveSingleProtocol(protocolName, deviceType, saveSign, msg) {
        protocolName = decodeURIComponent(protocolName);
        deviceType = decodeURIComponent(deviceType);
        saveSign = decodeURIComponent(saveSign);
        msg = decodeURIComponent(msg);
        if (parseInt(saveSign) > 0) {
            mini.confirm(msg, _loginUserLanguageResource.confirm, function(action) {
                if (action == 'ok') {
                    doSaveSingleProtocol(protocolName, deviceType);
                }
            });
        } else {
            doSaveSingleProtocol(protocolName, deviceType);
        }
    }

    function doSaveSingleProtocol(protocolName, deviceType) {
        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/saveSingelImportedProtocol',
            type: 'POST',
            data: {
                protocolName: protocolName,
                deviceType: deviceType
            },
            dataType: 'json',
            success: function(result) {
                mini.unmask(document.body);
                if (result.success) {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    // 刷新树
                    var tree = mini.get('protocolTree');
                    if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedProtocolTreeData');
                    // 刷新父页面协议树
                    if (window.parent && window.parent.refreshProtocolTree) {
                        window.parent.refreshProtocolTree();
                    }
                } else {
                    mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed ) + '</font>');
                }
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed );
            }
        });
    }

    // ================================================================
    // 保存全部
    // ================================================================
    function onSaveAll() {
        var tree = mini.get('protocolTree');
        var nodes = tree.getData(); // 获取所有节点数据
        // 收集所有协议节点（classes==1）
        var protocolNames = [];
        function collect(node) {
            if (node.classes === 1 && node.saveSign !== 2) {
                protocolNames.push(node.text);
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
            }
        }
        // 需要从树的数据中收集，但树的数据结构可能为数组
        var root = tree.getRootNode();
        if (root && root.children) {
            for (var i = 0; i < root.children.length; i++) collect(root.children[i]);
        }

        if (protocolNames.length === 0) {
            mini.alert(_loginUserLanguageResource.noDataCanBeSaved);
            return;
        }

        // 检查是否有冲突（saveSign==1表示覆盖，2表示无权限）
        var hasOverlay = false;
        var hasCollision = false;
        // 简单通过树的节点标志判断，实际需从后端获取
        // 这里我们简化：直接询问用户
        var msg = '';
        var overlayCount = 0;
        var collisionCount = 0;
        // 实际应从节点属性获取，但此处简化
        mini.confirm(_loginUserLanguageResource.confirmOperation, _loginUserLanguageResource.confirm, function(action) {
            if (action === 'ok') {
                var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData});
                $.ajax({
                    type: 'POST',
                    url: context + '/acquisitionUnitManagerController/saveAllImportedProtocol',
                    data: {
                        protocolName: protocolNames.join(','),
                        deviceType: deviceTypeId
                    },
                    dataType: 'json',
                    success: function(result) {
                        mini.unmask(document.body);
                        if (result.success) {
                            mini.alert(_loginUserLanguageResource.savedSuccessfully || '保存成功');
                            // 刷新树
                            var tree = mini.get('protocolTree');
                            if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedProtocolTreeData');
                            // 刷新主页面协议树
                            if (window.parent && window.parent.refreshProtocolTree) {
                                window.parent.refreshProtocolTree();
                            }
                        } else {
                            mini.alert(_loginUserLanguageResource.saveFailed || '保存失败');
                        }
                    },
                    error: function() {
                        mini.unmask(document.body);
                        mini.alert(_loginUserLanguageResource.requestFailed || '请求失败');
                    }
                });
            }
        });
    }

    // ================================================================
    // 9. 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>