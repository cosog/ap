<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导出报表实例</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar { flex-shrink:0; padding:6px 10px; border-bottom:1px solid #e8e8e8; background:#fafafa; display:flex; align-items:center; flex-wrap:wrap; gap:8px; }
        .tree-wrapper { flex:1; overflow:hidden; padding:4px; }
        .tree-wrapper .mini-tree { width:100%; height:100%; }
    </style>
</head>
<body>
<div class="main-container">
    <div class="mini-toolbar">
        <span style="flex:1;"></span>
        <button id="exportBtn" class="mini-button" iconCls="export" onclick="onExport()"></button>
    </div>

    <div class="tree-wrapper">
        <div id="instanceTree" class="mini-tree"
             style="width:100%;height:100%;"
             showTreeIcon="true" expandOnNodeClick="false"
             idField="id" textField="text" parentField="pid" resultAsTree="true"
             showCheckbox="true" checkRecursive="true"
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

    function setData(data) {
        if (data && data.deviceTypeIds) deviceTypeIds = data.deviceTypeIds;
        var tree = mini.get('instanceTree');
        if (tree) {
            tree.load(context + '/acquisitionUnitManagerController/exportProtocolReportInstanceTreeData');
        }
    }

    function onTreeBeforeLoad(e) {
        // 报表实例导出树无参（与 ExtJS 一致）
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        tree.expandAll();
    }

    function onTreeDrawnode(e) {
        if (e.node.classes != 1) e.showCheckBox = false;
    }

    function initI18n() {
        document.title = _loginUserLanguageResource.exportReportInstance;
        var btn = mini.get('exportBtn');
        if (btn) btn.setText(_loginUserLanguageResource.exportData);
    }

    function onExport() {
        var tree = mini.get('instanceTree');
        var checkedNodes = tree.getCheckedNodes();
        if (!checkedNodes || checkedNodes.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        var instanceList = [];
        for (var i = 0; i < checkedNodes.length; i++) {
            var node = checkedNodes[i];
            if (node.classes === 1 && node.id) instanceList.push(node.id);
        }
        if (instanceList.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        var timestamp = new Date().getTime();
        var key = 'exportProtocolReportInstanceData_' + timestamp;
        var url = context + '/acquisitionUnitManagerController/exportProtocolReportInstanceData'
                + '?key=' + key + '&instanceList=' + instanceList.join(',');

        exportDataMask(key, document.body, _loginUserLanguageResource.loadingData);
        openExcelWindow(url);
    }

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>