<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户隶属迁移</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-splitter { flex:1; width:100%; }
        .mini-datagrid, .mini-tree { width:100%; height:100%; }
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
    <div class="mini-splitter" vertical="false" style="width:100%; height:100%;" id="mainSplitter">

        <!-- 左侧：用户列表（可多选） -->
        <div size="70%" showCollapseButton="false" collapseDirection="left" minSize="350">
            <div id="leftPanel" class="mini-panel"
                 title=""
                 style="width:100%;height:100%;"
                 showCollapseButton="false"
                 showCloseButton="false"
                 allowResize="false"
                 bodyStyle="padding:0px;">

                <div id="userGrid" class="mini-datagrid"
                     style="width:100%; height:100%;"
                     showPager="false"
                     multiSelect="true"
                     allowCellSelect="false"
                     allowRowSelect="true"
                     idField="id"
                     dataField="totalRoot"
                     totalField="totalCount"
                     onbeforeload="onUserGridBeforeLoad"
                     onload="onUserGridLoad">
                    <div property="columns"></div>
                    <div property="emptyText" class="empty-msg">No Data</div>
                </div>
            </div>
        </div>

        <!-- 右侧：目标组织树（单选） -->
        <div size="30%" showCollapseButton="false" collapseDirection="right" minSize="200">
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
    var currentOrgIds = '';       // 从父窗口传入的"当前组织"筛选条件
    var userNameFilter = '';      // 从父窗口传入的用户名搜索条件
    var selectedDestinationOrgId = null;
    var isInitializing = true;
    var user_=null;

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.userOrgChange;

        var leftPanel = mini.get('leftPanel');
        if (leftPanel) leftPanel.setTitle(_loginUserLanguageResource.userList);
        var rightPanel = mini.get('rightPanel');
        if (rightPanel) rightPanel.setTitle(_loginUserLanguageResource.targetOrg);

        var btn = mini.get('changeOwnerBtn');
        if (btn) btn.setText(_loginUserLanguageResource.changeOwner);

        var tree = mini.get('destinationOrgTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);

        var grid = mini.get('userGrid');
        if (grid) grid.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 接收父窗口数据
    // ================================================================
    function setData(data) {
        if (!data) data = {};
        currentOrgIds = data.orgIds || '';
        userNameFilter = data.userName || '';
        user_ = data.user_;

        var grid = mini.get('userGrid');
        if (grid) {
            if (!grid.getUrl()) {
                grid.setUrl(context + '/userManagerController/getUserOrgChangeUserList');
            }
            grid.load();
        }

        var tree = mini.get('destinationOrgTree');
        if (tree) {
            tree.setUrl(context + '/orgManagerController/constructOrgTreeGridTree');
            tree.load();
        }
    }

    // ================================================================
    // 用户列表 beforeload：附加参数
    // ================================================================
    function onUserGridBeforeLoad(e) {
        var params = e.params || {};
        params.orgId = currentOrgIds || '';
        params.userName = userNameFilter || '';
        e.params = params;
    }

    // ================================================================
    // 用户列表 load：动态创建列
    // ================================================================
    function onUserGridLoad(e) {
        var grid = e.sender;
        if (grid._columnsCreated) return;
        grid._columnsCreated = true;

        var columns = [
            {
                type: 'checkcolumn',
                width: 40,
                header: '',
                headerAlign: 'center',
                align: 'center'
            },
            {
                type: 'indexcolumn',
                width: 50,
                headerAlign: 'center',
                align: 'center',
                header: _loginUserLanguageResource.idx
            },
            {
                field: 'userName',
                header: _loginUserLanguageResource.userName,
                headerAlign: 'center',
                align: 'center',
                width: '25%',
                renderer: function (e) {
                    var val = e.value || '';
                    var showVal = (String(e.record.id) === String(user_))
                        ? '*' + val : val;
                    return '<span title="' + showVal.replace(/"/g, '&quot;') + '">'
                        + showVal.replace(/"/g, '&quot;') + '</span>';
                }
            },
            {
                field: 'userID',
                header: _loginUserLanguageResource.userAccount,
                headerAlign: 'center',
                align: 'center',
                width: '25%'
            },
            {
                field: 'orgName',
                header: _loginUserLanguageResource.owningOrg,
                headerAlign: 'center',
                align: 'center',
                width: '40%'
            }
        ];
        grid.setColumns(columns);
    }

    // ================================================================
    // 目标组织树
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
        var grid = mini.get('userGrid');
        if (!grid) return;

        // ★ 获取所有被勾选/选中的行
        var selectedUsers = grid.getSelecteds() || [];
        if (selectedUsers.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne,
                       _loginUserLanguageResource.tip);
            return;
        }

        if (!selectedDestinationOrgId || parseInt(selectedDestinationOrgId) <= 0) {
            mini.alert(_loginUserLanguageResource.checkOne,
                       _loginUserLanguageResource.tip);
            return;
        }

        // 组装用户 ID 列表（逗号分隔）
        var arr = [];
        for (var i = 0; i < selectedUsers.length; i++) {
            arr.push(selectedUsers[i].id);
        }
        var selectedUserId = arr.join(',');

        var mask = mini.mask({
            el: document.body,
            html: _loginUserLanguageResource.submittingData
        });

        $.ajax({
            url: context + '/userManagerController/changeUserOrg',
            type: 'POST',
            data: {
                selectedUserId: selectedUserId,
                selectedOrgId: selectedDestinationOrgId
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(document.body);
                if (result.success === true) {
                	if (window._parentRefreshData) {
                        window._parentRefreshData();
                    }
                    mini.alert(_loginUserLanguageResource.migrationSuccessful,_loginUserLanguageResource.tip,
                        function () {
                            window.CloseOwnerWindow('ok');
                        });
                } else {
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
        setTimeout(function () { isInitializing = false; }, 100);
    });
</script>
</body>
</html>