<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导出采控实例</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            background: #f5f5f5;
        }
        .main-container {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
        }
        .mini-toolbar {
            flex-shrink: 0;
            padding: 6px 10px;
            border-bottom: 1px solid #e8e8e8;
            background: #fafafa;
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 8px;
        }
        .tree-wrapper {
            flex: 1;
            overflow: hidden;
            padding: 4px;
        }
        .tree-wrapper .mini-tree {
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 工具栏 -->
    <div class="mini-toolbar">
        <!-- 导出格式单选（受 _exportAdInitData 控制显示） -->
        <div id="formatRadioContainer" style="display:flex; align-items:center;">
            <div id="formatRadioList" class="mini-radiobuttonlist"
                 repeatItems="true"
                 repeatLayout="inline"
                 textField="text"
                 valueField="value"
                 value="1"
                 onvaluechanged="onFormatChange">
            </div>
        </div>
        <span style="flex:1;"></span>
        <button id="exportBtn" class="mini-button" iconCls="export" onclick="onExport()"></button>
    </div>

    <!-- 树区域 -->
    <div class="tree-wrapper">
        <div id="instanceTree" class="mini-tree"
             style="width:100%;height:100%;"
             showTreeIcon="true"
             expandOnNodeClick="false"
             idField="id"
             textField="text"
             parentField="pid"
             resultAsTree="true"
             showCheckbox="true"
             checkRecursive="true"
             onbeforeload="onTreeBeforeLoad"
             onload="onTreeLoad"
             ondrawnode="onTreeDrawnode">
            <div property="emptyText" class="empty-msg">No Instance</div>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeIds = '';

    // ================================================================
    // 1. 父窗口调用入口
    // ================================================================
    function setData(data) {
        if (data && data.deviceTypeIds) {
            deviceTypeIds = data.deviceTypeIds;
        }
        initRadioList();

        var tree = mini.get('instanceTree');
        if (tree) {
            tree.load(context + '/acquisitionUnitManagerController/exportProtocolAcqInstanceTreeData');
        }
    }

    // ================================================================
    // 2. 格式单选按钮（受 _exportAdInitData 控制显示）
    // ================================================================
    function initRadioList() {
        var formatRadio = mini.get('formatRadioList');
        if (!formatRadio) return;

        formatRadio.setData([
            { text: _loginUserLanguageResource.exportProtocolFormat1, value: '1' },
            { text: _loginUserLanguageResource.exportProtocolFormat2, value: '2' }
        ]);
        formatRadio.setValue('1');

        var container = document.getElementById('formatRadioContainer');
        if (container) {
            container.style.display = _exportAdInitData ? '' : 'none';
        }
    }

    function onFormatChange(e) {
        // 预留：格式变化时可做额外处理
    }

    // ================================================================
    // 3. 树事件
    // ================================================================
    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceTypeIds = deviceTypeIds;
        e.params = params;
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        tree.expandAll();
    }

    // 只对实例节点（classes === 1）显示复选框
    function onTreeDrawnode(e) {
        if (e.node.classes != 1) {
            e.showCheckBox = false;
        }
    }

    // ================================================================
    // 4. 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.exportAcqInstance;
        var btn = mini.get('exportBtn');
        if (btn) btn.setText(_loginUserLanguageResource.exportData);
    }

    // ================================================================
    // 5. 导出
    // ================================================================
    function onExport() {
        var tree = mini.get('instanceTree');
        var checkedNodes = tree.getCheckedNodes();
        if (!checkedNodes || checkedNodes.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        // 收集实例 id（只收集 classes === 1 的实例节点）
        var instanceList = [];
        for (var i = 0; i < checkedNodes.length; i++) {
            var node = checkedNodes[i];
            if (node.classes === 1 && node.id) {
                instanceList.push(node.id);
            }
        }
        if (instanceList.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        var format = mini.get('formatRadioList').getValue();
        var timestamp = new Date().getTime();

        var key = 'exportProtocolAcqInstanceData_' + timestamp;
        var url = context + '/acquisitionUnitManagerController/exportProtocolAcqInstanceData'
                + '?key=' + key
                + '&instanceList=' + instanceList.join(',');

        if (format === '2') {
            key = 'exportProtocolAcqInstanceInitData_' + timestamp;
            url = context + '/acquisitionUnitManagerController/exportProtocolAcqInstanceInitData'
                + '?key=' + key
                + '&instanceList=' + instanceList.join(',');
        }

        exportDataMask(key, document.body, _loginUserLanguageResource.loadingData);
        openExcelWindow(url);
    }

    // ================================================================
    // 6. 页面初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>