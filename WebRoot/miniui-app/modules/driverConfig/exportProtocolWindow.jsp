<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导出协议</title>
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
        /* 单选按钮组水平排列 */
        .radio-group {
            display: flex;
            align-items: center;
            gap: 4px;
        }
        .radio-group label {
            margin-right: 12px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 工具栏 -->
    <div class="mini-toolbar">
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
        <div id="protocolTree" class="mini-tree" 
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
             onload="onTreeLoad">
            <div property="emptyText" class="empty-msg">No Protocol</div>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeIds = '';

    // 接收父窗口传递的设备类型ID
    function setData(data) {
        if (data && data.deviceTypeIds) {
            deviceTypeIds = data.deviceTypeIds;
        }
        initRadioList();
        
        var tree = mini.get('protocolTree');
        if (tree) {
            tree.load(context + '/acquisitionUnitManagerController/exportProtocolTreeData');
        }
    }
    
    function initRadioList() {
        formatRadio = mini.get('formatRadioList');
        if (!formatRadio) return;
        
        var data = [
            { text: _loginUserLanguageResource.exportProtocolFormat1, value: '1' },
            { text: _loginUserLanguageResource.exportProtocolFormat2, value: '2' }
        ];
        formatRadio.setData(data);
        formatRadio.setValue('1');
        
        // 根据 _exportAdInitData 控制显示/隐藏
        var container = document.getElementById('formatRadioContainer');
        if (container) {
            container.style.display = _exportAdInitData ? '' : 'none';
        }
    }

    // 树加载前附加参数
    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceTypeIds = deviceTypeIds;
        e.params = params;
    }

    // 树加载后默认展开所有节点（可选）
    function onTreeLoad(e) {
        var tree = e.sender;
        tree.expandAll();
    }

    // 国际化
    function initI18n() {
        document.title = _loginUserLanguageResource.exportProtocol;
        var btn = mini.get('exportBtn');
        if (btn) btn.setText(_loginUserLanguageResource.exportData);
    }

    // 导出按钮事件
    function onExport() {
        var tree = mini.get('protocolTree');
        var checkedNodes = tree.getCheckedNodes();
        if (!checkedNodes || checkedNodes.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        // 收集协议 code（只收集叶子节点，即 classes==1）
        var protocolList = [];
        for (var i = 0; i < checkedNodes.length; i++) {
            var node = checkedNodes[i];
            if (node.classes === 1 && node.code) {
                protocolList.push(node.code);
            }
        }
        if (protocolList.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        // 获取导出格式
        var format = mini.get('formatRadioList').getValue();

        var timestamp = new Date().getTime();
        var key = 'exportProtocolData_' + timestamp;
        var url = context + '/acquisitionUnitManagerController/exportProtocolData?key=' + key + '&protocolList=' + protocolList.join(',');

        if (format === '2') {
            key = 'exportProtocolInitData_' + timestamp;
            url = context + '/acquisitionUnitManagerController/exportProtocolInitData?key=' + key + '&protocolList=' + protocolList.join(',');
        }

        exportDataMask(key, document.body, _loginUserLanguageResource.loadingData);
        openExcelWindow(url);
    }

    // 页面初始化
    $(document).ready(function() {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>