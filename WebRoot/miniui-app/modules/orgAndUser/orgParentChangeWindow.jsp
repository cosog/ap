<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>组织隶属迁移</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-splitter { flex:1; width:100%; }
        .mini-tree { width:100%; height:100%; }
        .empty-msg { color:#999; font-size:13px; text-align:center; padding:20px; }
        .footer-toolbar {
            flex-shrink:0; padding:6px 10px;
            border-top:1px solid #e8e8e8;
            background:#fafafa; text-align:right;
        }
        .mini-splitter-border { border:0 !important; }
        .mini-splitter-pane { padding:0 !important; border:0 !important; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 左右 Splitter -->
    <div class="mini-splitter" vertical="false" style="width:100%; height:100%;" id="mainSplitter">

        <!-- 左侧：当前组织列表（可多选） -->
        <div size="60%" showCollapseButton="false" collapseDirection="left" minSize="250">
            <div id="leftPanel" class="mini-panel"
                 title=""
                 style="width:100%;height:100%;"
                 showCollapseButton="false"
                 showCloseButton="false"
                 allowResize="false"
                 bodyStyle="padding:4px;">

                <div id="currentOrgTree" class="mini-tree"
                     style="width:100%;height:100%;"
                     showTreeIcon="true"
                     showCheckBox="false"
                     checkRecursive="false"
                     autoCheckParent="false"
                     expandOnNodeClick="false"
                     resultAsTree="true"
                     idField="orgId"
                     textField="text"
                     parentField="orgParent"
                     dataField="children"
                     autoLoad="false"
                     onbeforeload="onCurrentTreeBeforeLoad"
                     onload="onCurrentTreeLoad">
                    <div property="emptyText" class="empty-msg">No Data</div>
                </div>
            </div>
        </div>

        <!-- 右侧：目标组织列表（单选） -->
        <div size="40%" showCollapseButton="false" collapseDirection="right" minSize="150">
            <div id="rightPanel" class="mini-panel"
                 title=""
                 style="width:100%;height:100%;"
                 showCollapseButton="false"
                 showCloseButton="false"
                 allowResize="false"
                 bodyStyle="padding:4px;">

                <div id="destinationOrgTree" class="mini-tree"
                     style="width:100%;height:100%;"
                     showTreeIcon="true"
                     expandOnNodeClick="false"
                     resultAsTree="true"
                     idField="orgId"
                     textField="text"
                     parentField="orgParent"
                     dataField="children"
                     autoLoad="false"
                     onbeforeload="onDestinationTreeBeforeLoad"
                     onload="onDestinationTreeLoad"
                     onnodeselect="onDestinationNodeSelect">
                    <div property="emptyText" class="empty-msg">No Data</div>
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
    var selectedDestinationOrgId = null;

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.orgParentChange;

        var leftPanel = mini.get('leftPanel');
        if (leftPanel) leftPanel.setTitle(_loginUserLanguageResource.orgList);
        var rightPanel = mini.get('rightPanel');
        if (rightPanel) rightPanel.setTitle(_loginUserLanguageResource.targetOrg);

        var btn = mini.get('changeOwnerBtn');
        if (btn) btn.setText(_loginUserLanguageResource.changeOwner);

        var leftTree = mini.get('currentOrgTree');
        if (leftTree) leftTree.setEmptyText(_loginUserLanguageResource.emptyMsg);
        var rightTree = mini.get('destinationOrgTree');
        if (rightTree) rightTree.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 左树（当前组织）事件
    // ================================================================
    function onCurrentTreeBeforeLoad(e) {
        e.params = e.params || {};
    }

    function onCurrentTreeLoad(e) {
        var tree = e.sender;
        var root = tree.getRootNode();
        if (root) tree.expandNode(root);
    }

    // ================================================================
    // 右树（目标组织）事件
    // ================================================================
    function onDestinationTreeBeforeLoad(e) {
        e.params = e.params || {};
    }

    function onDestinationTreeLoad(e) {
        var tree = e.sender;
        var root = tree.getRootNode();
        if (root) tree.expandNode(root);
    }

    function onDestinationNodeSelect(e) {
        var node = e.node;
        if (node && node.orgId !== undefined && node.orgId !== null) {
            selectedDestinationOrgId = node.orgId;
        }
    }

    // ================================================================
    // 变更归属
    // ================================================================
    function onChangeOwner() {
        var leftTree = mini.get('currentOrgTree');
        if (!leftTree) return;

        // ★ 获取所有勾选的节点
        var checkedNodes = leftTree.getSelectedNode();
        if (!checkedNodes) {
            mini.alert(_loginUserLanguageResource.checkOne,_loginUserLanguageResource.tip);
            return;
        }

        if (!selectedDestinationOrgId || parseInt(selectedDestinationOrgId) <= 0) {
            mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
            return;
        }

        
        var selectedCurrentOrgId = checkedNodes.orgId;

        var mask = mini.mask({
            el: document.body,
            html: _loginUserLanguageResource.submittingData
        });

        $.ajax({
            url: context + '/orgManagerController/changeOrgParent',
            type: 'POST',
            data: {
                selectedCurrentOrgId: selectedCurrentOrgId,
                selectedDestinationOrgId: selectedDestinationOrgId
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(document.body);
                if (result.success === true && result.resultStatus > 0) {
                	if (window._parentRefreshOrgTree) {
                        window._parentRefreshOrgTree();
                    }
                    mini.alert(_loginUserLanguageResource.migrationSuccessful, _loginUserLanguageResource.tip,
                        function () {
                            window.CloseOwnerWindow('ok');
                        });
                } else if (result.success === true && result.resultStatus === -1) {
                    mini.alert('<font color=red>'+ _loginUserLanguageResource.orgMigrationFailed+ '</font>', _loginUserLanguageResource.tip);
                } else if (result.success === false) {
                    mini.alert('<font color=red>'+ _loginUserLanguageResource.migrationFailed+ '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert('【<font color=red>'+ _loginUserLanguageResource.exceptionThrow+ '</font>】' + _loginUserLanguageResource.contactAdmin,_loginUserLanguageResource.tip);
            }
        });
    }

    // ================================================================
    // 页面初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();

        // 加载左右两棵树
        var leftTree = mini.get('currentOrgTree');
        if (leftTree) {
            leftTree.setUrl(context + '/orgManagerController/constructOrgTreeGridTree');
            leftTree.load();
        }
        var rightTree = mini.get('destinationOrgTree');
        if (rightTree) {
            rightTree.setUrl(context + '/orgManagerController/constructOrgTreeGridTree');
            rightTree.load();
        }
    });
</script>
</body>
</html>