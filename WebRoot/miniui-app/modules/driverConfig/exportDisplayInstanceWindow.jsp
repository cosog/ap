<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导出显示实例</title>
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
        <div id="formatRadioContainer" style="display:flex; align-items:center;">
            <div id="formatRadioList" class="mini-radiobuttonlist"
                 repeatItems="true" repeatLayout="inline"
                 textField="text" valueField="value" value="1"
                 onvaluechanged="onFormatChange"></div>
        </div>
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
        initRadioList();
        var tree = mini.get('instanceTree');
        if (tree) {
            tree.load(context + '/acquisitionUnitManagerController/exportProtocolDisplayInstanceTreeData');
        }
    }

    function initRadioList() {
        var formatRadio = mini.get('formatRadioList');
        if (!formatRadio) return;
        formatRadio.setData([
            { text: _loginUserLanguageResource.exportProtocolFormat1, value: '1' },
            { text: _loginUserLanguageResource.exportProtocolFormat2, value: '2' }
        ]);
        formatRadio.setValue('1');
        var container = document.getElementById('formatRadioContainer');
        if (container) container.style.display = _exportAdInitData ? '' : 'none';
    }

    function onFormatChange(e) {}

    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceTypeIds = deviceTypeIds;
        e.params = params;
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        tree.expandAll();
    }

    function onTreeDrawnode(e) {
        if (e.node.classes != 1) e.showCheckBox = false;
    }

    function initI18n() {
        document.title = _loginUserLanguageResource.exportDisplayInstance;
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

        var format = mini.get('formatRadioList').getValue();
        var timestamp = new Date().getTime();
        var key = 'exportProtocolDisplayInstanceData_' + timestamp;
        var url = context + '/acquisitionUnitManagerController/exportProtocolDisplayInstanceData'
                + '?key=' + key + '&instanceList=' + instanceList.join(',');

        if (format === '2') {
            key = 'exportProtocolDisplayInstanceInitData_' + timestamp;
            url = context + '/acquisitionUnitManagerController/exportProtocolDisplayInstanceInitData'
                + '?key=' + key + '&instanceList=' + instanceList.join(',');
        }

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