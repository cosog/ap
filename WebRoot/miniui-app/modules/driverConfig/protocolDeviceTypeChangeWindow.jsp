<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>迁移协议</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-splitter { width:100%; height:100%; }
        .mini-datagrid { width:100%; height:100%; }
        .mini-tree { width:100%; height:100%; }
        .footer-toolbar { flex-shrink:0; padding:6px 10px; border-top:1px solid #e8e8e8; background:#fafafa; text-align:right; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 左右 Splitter -->
    <div class="mini-splitter" vertical="false" style="width:100%; height:100%;" id="mainSplitter">
        <!-- 左侧：协议列表 -->
        <div size="60%" showCollapseButton="false" collapseDirection="left" minSize="250">
            <div id="leftPanel" class="mini-panel" 
                 title="" 
                 style="width:100%;height:100%;" 
                 showCollapseButton="false" 
                 showCloseButton="false" 
                 allowResize="false" 
                 bodyStyle="padding:4px;">
                <div id="protocolGrid" class="mini-datagrid" 
                     style="width:100%; height:100%;" 
                     showPager="false" 
                     multiSelect="true" 
                     allowCellSelect="false" 
                     allowRowSelect="true"
                     dataField="totalRoot" totalField="totalCount"
                     onbeforeload="onProtocolGridBeforeLoad"
                     onload="onProtocolGridLoad">
                    <div property="columns"></div>
                </div>
            </div>
        </div>
        <!-- 右侧：设备类型树 -->
        <div size="40%" showCollapseButton="false" collapseDirection="right" minSize="150">
            <div id="rightPanel" class="mini-panel" 
                 title="" 
                 style="width:100%;height:100%;" 
                 showCollapseButton="false" 
                 showCloseButton="false" 
                 allowResize="false" 
                 bodyStyle="padding:4px;">
                <div id="deviceTypeTree" class="mini-tree" 
                     style="width:100%; height:100%;" 
                     showTreeIcon="true" 
                     expandOnNodeClick="false" 
                     idField="deviceTypeId" 
                     textField="text" 
                     parentField="parentId" 
                     resultAsTree="true"
                     onbeforeload="onTreeBeforeLoad"
                     onload="onTreeLoad"
                     onnodeselect="onTreeSelect">
                    <div property="emptyText" class="empty-msg">No Device Type</div>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部工具栏 -->
    <div class="footer-toolbar">
        <button id="changeOwnerBtn" class="mini-button" iconCls="move" onclick="onChangeOwner()"></button>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeIds = '';      // 从父页面传入的设备类型ID（用于过滤协议列表）
    var selectedDeviceTypeId = null;

    // 接收父窗口数据
    function setData(data) {
        if (data && data.deviceTypeIds) {
            deviceTypeIds = data.deviceTypeIds;
        }
        // 加载左侧协议列表
        var grid = mini.get('protocolGrid');
        if (grid) {
        	if(!grid.getUrl()){
        		grid.setUrl(context + '/acquisitionUnitManagerController/getProtocolDeviceTypeChangeProtocolList');
        	}
            grid.load();
        }
        // 加载右侧设备类型树
        var tree = mini.get('deviceTypeTree');
        if (tree) {
            tree.load(context + '/roleManagerController/constructProtocolConfigTabTreeGridTree');
        }
    }

    // 国际化
    function initI18n() {
        document.title = _loginUserLanguageResource.protocoDeviceTypeChange;
        
        var leftPanel = mini.get('leftPanel');
        if (leftPanel) leftPanel.setTitle(_loginUserLanguageResource.protocolList);
        var rightPanel = mini.get('rightPanel');
        if (rightPanel) rightPanel.setTitle(_loginUserLanguageResource.targetType);
        
        var btn = mini.get('changeOwnerBtn');
        if (btn) btn.setText(_loginUserLanguageResource.changeOwner);
        var tree = mini.get('deviceTypeTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // 左侧协议列表 beforeload：附加 deviceTypeIds
    function onProtocolGridBeforeLoad(e) {
        var params = e.params || {};
        params.deviceTypeIds = deviceTypeIds;
        e.params = params;
    }

    // 左侧协议列表 load：选中所有（或保留选中状态）
    function onProtocolGridLoad(e) {
        var grid = e.sender;
        var columns = columns = [{
            type: 'checkcolumn',
            width: 40
        },
        {
            type: 'indexcolumn',
            header: _loginUserLanguageResource.idx,
            align: 'center',
            headerAlign: 'center',
            width: 50
        },
        {
            field: 'name',
            header: _loginUserLanguageResource.protocolName,
            align: 'center',
            headerAlign: 'center',
            width: '40%'
        },
        {
            field: 'deviceTypeName',
            header: _loginUserLanguageResource.owningDeviceType,
            align: 'center',
            headerAlign: 'center',
            flex: '60%'
        }];
        grid.setColumns(columns);
        //grid.doLayout();
    }

    // 右侧树加载前（无需额外参数）
    function onTreeBeforeLoad(e) {
    }

    // 右侧树加载后：默认选中第一个叶子节点（设备类型）
    function onTreeLoad(e) {
        var tree = e.sender;
    }

    // 右侧树节点选中事件：记录选中的设备类型ID
    function onTreeSelect(e) {
        var node = e.node;
        if (node && node.deviceTypeId) {
            selectedDeviceTypeId = node.deviceTypeId;
        }
    }

    // 变更所属按钮事件
    function onChangeOwner() {
        var grid = mini.get('protocolGrid');
        var selectedProtocols = grid.getSelecteds(); // 选中的行（数组）
        if (!selectedProtocols || selectedProtocols.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }
        if (!selectedDeviceTypeId) {
            mini.alert(_loginUserLanguageResource.selectDeviceType);
            return;
        }

        // 收集选中的协议ID（逗号分隔）
        var protocolIds = [];
        for (var i = 0; i < selectedProtocols.length; i++) {
            protocolIds.push(selectedProtocols[i].id);
        }
        var protocolIdStr = protocolIds.join(',');

        // 显示遮罩
        var mask = mini.mask({
            el: document.body,
            html: _loginUserLanguageResource.submittingData
        });

        $.ajax({
            url: context + '/acquisitionUnitManagerController/changeProtocolDeviceType',
            type: 'POST',
            data: {
                selectedProtocolId: protocolIdStr,
                selectedDeviceTypeId: selectedDeviceTypeId
            },
            dataType: 'json',
            success: function(resp) {
                mini.unmask(document.body);
                if (resp.success) {
                    mini.alert(_loginUserLanguageResource.changeProtocolBelongToSuccess, function() {
                        // 关闭窗口
                        window.CloseOwnerWindow('ok');
                        if (window.parent && window.parent.refreshProtocolTree) {
                            window.parent.refreshProtocolTree();
                        }
                    });
                } else {
                    mini.alert('<font color="red">' + (_loginUserLanguageResource.changeProtocolBelongToFail) + '</font>');
                }
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert((_loginUserLanguageResource.exceptionThro) + ': ' + (_loginUserLanguageResource.contactAdmin));
            }
        });
    }

    $(document).ready(function() {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>