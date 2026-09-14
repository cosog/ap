<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导出报表单元</title>
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
            justify-content: flex-end;
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
        <button id="exportBtn" class="mini-button" iconCls="export" onclick="onExport()"></button>
    </div>
    <!-- 树区域 -->
    <div class="tree-wrapper">
        <div id="unitTree" class="mini-tree"
             style="width:100%;height:100%;"
             showTreeIcon="true"
             expandOnNodeClick="false"
             idField="id"
             textField="text"
             parentField="pid"
             resultAsTree="true"
             showCheckbox="true"
             checkRecursive="false"
             onbeforeload="onTreeBeforeLoad"
             onload="onTreeLoad"
             ondrawnode="onTreeDrawnode">
            <div property="emptyText" class="empty-msg">No Unit</div>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeIds = '';

    function setData(data) {
        if (data && data.deviceTypeIds) {
            deviceTypeIds = data.deviceTypeIds;
        }
        var tree = mini.get('unitTree');
        if (tree) {
            tree.load(context + '/acquisitionUnitManagerController/exportReportUnitTreeData');
        }
    }

    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceTypeIds = deviceTypeIds;
        e.params = params;
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        // 不需要额外处理
    }

    function onTreeDrawnode(e) {
        // ★ 只对报表单元（classes === 1）显示复选框
        // 目录 / 其它节点不显示
        if (e.node.classes != 1) e.showCheckBox = false;
    }

    function initI18n() {
        document.title = _loginUserLanguageResource.exportReportUnit;
        var btn = mini.get('exportBtn');
        if (btn) btn.setText(_loginUserLanguageResource.exportData);
    }

    function onExport() {
        var tree = mini.get('unitTree');
        var checkedNodes = tree.getCheckedNodes();
        if (!checkedNodes || checkedNodes.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        var unitList = [];
        for (var i = 0; i < checkedNodes.length; i++) {
            var node = checkedNodes[i];
            // ★ 报表单元 classes === 1
            if (node.classes === 1 && node.id) {
                unitList.push(node.id);
            }
        }
        if (unitList.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        var timestamp = new Date().getTime();
        var key = 'exportProtocolReportUnitData_' + timestamp;
        var url = context + '/acquisitionUnitManagerController/exportProtocolReportUnitData?key=' + key + '&unitList=' + unitList.join(',');

        exportDataMask(key, document.body, _loginUserLanguageResource.loadingData);
        openExcelWindow(url);
    }

    $(document).ready(function() {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>