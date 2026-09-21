<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*,com.cosog.model.User,com.cosog.utils.ConfigFile,com.google.gson.Gson" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
User userLogin = (User)session.getAttribute("userLogin");
String userLoginNo = userLogin != null ? userLogin.getUserNo() + "" : "";
String userLoginOrgId=userLogin!=null?userLogin.getUserOrgid()+"":"";

String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>组织用户管理</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }

        .org-and-user-container {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #fff;
        }

        .org-and-user-container .mini-splitter {
            flex: 1;
            border: 0 !important;
        }

        .mini-splitter-border {
            border: 0 !important;
        }

        .mini-splitter-pane {
            padding: 0 !important;
            border: 0 !important;
        }

        .mini-splitter-pane .mini-splitter-handler {
            background: transparent !important;
            border: 0 !important;
        }

        .org-panel,
        .user-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #fff;
        }

        .panel-toolbar {
            flex-shrink: 0;
            padding: 4px 8px;
            border-bottom: 1px solid #e8e8e8;
            background: #fafafa;
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 4px;
        }

        .panel-toolbar .separator {
            width: 1px;
            height: 20px;
            background: #ddd;
            margin: 0 4px;
        }

        .panel-body {
            flex: 1;
            min-height: 0;
            overflow: hidden;
            padding: 1px;
            box-sizing: border-box;
        }

        .panel-body .mini-treegrid,
        .panel-body .mini-datagrid {
            width: 100%;
            height: 100%;
        }

        .empty-msg {
            color: #999;
            font-size: 13px;
            text-align: center;
            padding: 20px;
        }
    </style>
</head>
<body>

<div class="org-and-user-container">
    <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
        <!-- ==================== 左侧：组织信息 ==================== -->
        <div size="40%" showCollapseButton="true" collapseDirection="left" minSize="300">
            <div class="org-panel" id="orgPanel">
                <div class="panel-toolbar">
                    <button id="orgRefreshBtn" class="mini-button" iconCls="note-refresh" onclick="loadOrgTree()"></button>
                    <span style="flex:1;"></span>
                    <button id="addOrgLableClassBtn_Id" class="mini-button" iconCls="add" onclick="addOrgInfo()"></button>
                    <button id="editOrgLableClassBtn_Id" class="mini-button" iconCls="edit" visible="false"></button>
                    <button id="delOrgLableClassBtn_Id" class="mini-button" iconCls="delete" onclick="delOrgInfo()"></button>
                    <button id="orgSaveBtn" class="mini-button" iconCls="save" onclick="saveOrgInfo()"></button>
                    <button id="orgParentChangeBtn" class="mini-button" iconCls="move" onclick="orgParentChangeInfo()"></button>
                    <button id="orgExportBtn" class="mini-button" iconCls="export" onclick="exportOrganizationCompleteData()"></button>
                    <button id="orgImportBtn" class="mini-button" iconCls="import" onclick="openImportOrganizationWindow()"></button>
                </div>

                <div class="panel-body">
                    <div id="OrgInfoTreeGridView_Id" class="mini-treegrid"
                         style="width:100%;height:100%;"
                         showTreeIcon="true"
                         treeColumn="taskname"
                         idField="orgId"
                         textField="text"
                         parentField="orgParent"
                         dataField="children"
                         resultAsTree="true"
                         allowResize="false"
                         allowAlternating="true"
                         showPager="false"
                         allowCellEdit="false"
                         allowCellSelect="false"
                         showEmptyText="true"
                         onbeforeload="onOrgTreeBeforeLoad"
                         onload="onOrgTreeLoad"
                         onnodeselect="onOrgTreeSelect"
                         autoLoad="false"
                         showLoading="true"
                         showCellTip="true"
                         cellEditAction="celldblclick"
                         expandOnDblClick="false"
                         expandOnNodeClick="false">
                        <div property="columns"></div>
                        <div property="emptyText" class="empty-msg"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ==================== 右侧：用户信息 ==================== -->
        <div size="60%" showCollapseButton="false" minSize="300">
            <div class="user-panel" id ="userPanel">
                <div class="panel-toolbar">
                    <span id="userNameLabel"></span>
                    <input id="UserName_Id" class="mini-textbox" style="width:180px;" />
                    <button id="userSearchBtn" class="mini-button" iconCls="search" onclick="loadUserList()"></button>
                    <span style="flex:1;"></span>
                    <button id="addUserLableClassBtn_Id" class="mini-button" iconCls="add" onclick="addUserInfo()"></button>
                    <button id="userDeleteBtn" class="mini-button" iconCls="delete" onclick="batchDeleteUser()"></button>
                    <button id="userSaveBtn" class="mini-button" iconCls="save" onclick="batchUpdateUserInfo()"></button>
                    <button id="editUserLableClassBtn_Id" class="mini-button" iconCls="edit" onclick="modifyUserInfo()"></button>
                    <button id="userOrgChangeBtn" class="mini-button" iconCls="move" onclick="userOrgChangeInfo()"></button>
                    <button id="userExportBtn" class="mini-button" iconCls="export" onclick="exportUserCompleteData()"></button>
                    <button id="userImportBtn" class="mini-button" iconCls="import" onclick="openImportUserWindow()"></button>
                </div>

                <div class="panel-body">
                    <div id="UserInfoGridPanel_Id" class="mini-datagrid"
                         style="width:100%;height:100%;"
                         idField="userNo"
                         allowResize="false"
                         allowAlternating="true"
                         showPager="false"
                         pageSize="50"
                         showPageInfo="false"
                         multiSelect="true"
                         allowCellEdit="false"
                         allowCellSelect="false"
                         showEmptyText="true"
                         dataField="totalRoot"
                         totalField="totalCount"
                         onbeforeload="onUserGridBeforeLoad"
                         onload="onUserGridLoad"
                       	 onselectionchanged="onUserGridSelectionchanged"
                       	 oncellbeginedit="onUserGridCellBeginEdit"
     					 oncellendedit="onUserGridCellEndEdit"
                         cellEditAction="celldblclick">
                        <div property="columns"></div>
                        <div property="emptyText" class="empty-msg"></div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script>
    var context = '<%=path%>';
    var user_ = '<%=userLoginNo%>';
    var userOrg_Id = '<%=userLoginOrgId%>';
    var isInitializing = true;

    // ================================================================
    // 模块权限
    // ================================================================
    var loginUserOrgAndUserModuleRight = getRoleModuleRight(context + '/roleManagerController/getRoleModuleRight', 'OrganizationAndUserManagement');
    var viewFlag = false;
    var editFlag = false;
    var controlFlag = false;
    if (typeof loginUserOrgAndUserModuleRight !== 'undefined') {
        viewFlag = (loginUserOrgAndUserModuleRight.viewFlag == 1);
        editFlag = (loginUserOrgAndUserModuleRight.editFlag == 1);
        controlFlag = (loginUserOrgAndUserModuleRight.controlFlag == 1);
    }
    var emailEnable = false;
    var raw = (typeof _emailEnable !== 'undefined') ? _emailEnable : false;
    emailEnable = (raw === true || raw === 'true' || raw === 1 || raw === '1');

    // ================================================================
    // 状态变量
    // ================================================================
    var _currentOrgNode = null;      // 当前选中的组织节点
    var _selectedOrgId = null;          // 上次选中的组织ID（用于重新加载后恢复）
    var _allOrgIds = null;

    // ================================================================
    // 1. 加载组织树
    // ================================================================
    function loadOrgTree() {
        var tree = mini.get('OrgInfoTreeGridView_Id');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/orgManagerController/constructOrgTreeGridTree');
        }
        tree.load();
    }

    // ================================================================
    // 2. 组织树加载前：附加查询参数
    // ================================================================
    function onOrgTreeBeforeLoad(e) {
        var params = e.params || {};
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        params.orgId = orgId || '';
        e.params = params;
    }

    // ================================================================
    // 3. 组织树加载完成
    // ================================================================
    function onOrgTreeLoad(e) {
        var tree = e.sender;
        var result = e.result || {};

        if (!tree._columnsCreated) {
            createOrgTreeColumns(tree, result);
            tree._columnsCreated = true;
        }

        var selectNode=tree.getSelectedNode();
        if(!selectNode){
        	var root = tree.getRootNode();
            if (!root || !root.children || root.children.length === 0) return;

            var targetNode = null;

            if (_selectedOrgId && _selectedOrgId !== '0' && _selectedOrgId !== 0) {
                targetNode = findOrgNodeById(root, _selectedOrgId);
            }
            if (!targetNode) {
                targetNode = root.children[0];
            }

            if (targetNode) {
                setTimeout(function () {
                    tree.selectNode(targetNode);
                }, 50);
            }
        }
    }

    function findOrgNodeById(root, orgId) {
        var target = null;
        (function collect(node) {
            if (target) return;
            if (node.orgId == orgId) {
                target = node;
                return;
            }
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                    if (target) return;
                }
            }
        })(root);
        return target;
    }

    // ================================================================
    // 4. 动态创建组织树列
    // ================================================================
    function createOrgTreeColumns(tree, result) {
        var showChineseName = (result.showChineseName === undefined) ? true : !!result.showChineseName;
        var showEnglishName = (result.showEnglishName === undefined) ? true : !!result.showEnglishName;
        var showRussianName = (result.showRussianName === undefined) ? true : !!result.showRussianName;

        var textEditor = editFlag ? { type: 'textbox', allowBlank: true } : null;
        var seqEditor = editFlag ? { type: 'spinner', minValue: 1, maxValue:9999999999 } : null;

        var columns = [
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.orgName,
                headerAlign: 'left',
                align: 'left',
                width: '33%',
                editor: null
            },
            {
                field: 'orgName_zh_CN',
                header: _loginUserLanguageResource.language_zh_CN,
                headerAlign: 'left',
                align: 'left',
                width: '22%',
                visible: showChineseName,
                editor: textEditor
            },
            {
                field: 'orgName_en',
                header: _loginUserLanguageResource.language_en,
                headerAlign: 'left',
                align: 'left',
                width: '22%',
                visible: showEnglishName,
                editor: textEditor
            },
            {
                field: 'orgName_ru',
                header: _loginUserLanguageResource.language_ru,
                headerAlign: 'left',
                align: 'left',
                width: '22%',
                visible: showRussianName,
                editor: textEditor
            },
            {
                field: 'orgSeq',
                header: _loginUserLanguageResource.sequenceNumber,
                headerAlign: 'center',
                align: 'center',
                width: 80,
                editor: seqEditor
            },
            { field: 'orgId', visible: false },
            { field: 'orgParent', visible: false }
        ];

        tree.setColumns(columns);
        tree.setAllowCellEdit(editFlag);
        tree.setAllowCellSelect(editFlag);
    }

    // ================================================================
    // 5. 组织树节点选中
    // ================================================================
    function onOrgTreeSelect(e) {
    	var tree = e.sender;
    	var node = e.node;
        if (!node) return;

        _currentOrgNode = node;
        _selectedOrgId = node.orgId;
        _allOrgIds = foreachAndSearchOrgChildId(node);

     // ★ 判断当前节点是否为"根节点"或"当前登录用户所属组织"
        var isRootNode = isOrgRootNode(tree, node);
        var isLoginUserOrg = (String(node.orgId) === String(userOrg_Id));

        // 删除按钮：根节点 或 登录用户所属组织 时禁用
        var delBtn = mini.get('delOrgLableClassBtn_Id');
        if (delBtn) delBtn.setEnabled(editFlag && !isRootNode && !isLoginUserOrg);

        // 编辑按钮：如果有独立的编辑按钮，同样处理
        var editBtn = mini.get('editOrgLableClassBtn_Id');
        if (editBtn) editBtn.setEnabled(editFlag && !isRootNode && !isLoginUserOrg);
        
        // ★ 刷新右侧用户列表
        loadUserList();
    }
    
    function isOrgRootNode(tree, node) {
        if (!node) return false;
        var parent = tree.getParentNode(node);
        // 父节点是 MiniUI 的隐藏 root（无 orgId 或 orgId=0）
        if (!parent) return true;
        var pid = parent.orgId;
        return (pid === undefined || pid === null || pid === 0 || pid === '0');
    }

    // ================================================================
    // 6. 深度优先遍历组织节点及其所有子节点的 orgId
    // ================================================================
    function foreachAndSearchOrgChildId(node) {
        if (!node) return '';
        var ids = [];
        function collect(currentNode) {
            if (!currentNode) return;
            var orgId = currentNode.orgId;
            if (orgId !== undefined && orgId !== null && orgId !== '') {
                ids.push(orgId);
            }
            if (currentNode.children && currentNode.children.length > 0) {
                for (var i = 0; i < currentNode.children.length; i++) {
                    collect(currentNode.children[i]);
                }
            }
        }
        collect(node);
        return ids.join(',');
    }

    // ================================================================
    // 7. 加载用户列表
    // ================================================================
    function loadUserList() {
        var grid = mini.get('UserInfoGridPanel_Id');
        if (!grid) return;
        if (!grid.getUrl()) {
            grid.setUrl(context + '/userManagerController/doUserShow');
        }
        grid.load();
    }

    // ================================================================
    // 8. 用户列表加载前：附加参数
    // ================================================================
    function onUserGridBeforeLoad(e) {
        var params = e.params || {};

        // 分页参数
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || 50;
        params.start = pageIndex * pageSize;
        params.limit = pageSize;

        // 组织ID：优先当前选中的组织节点，否则取左侧组织树选中值
        var orgId = _allOrgIds;
        if (!orgId) {
        	orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        }
        params.orgId = orgId || '';

        // 用户名查询条件
        var userInput = mini.get('UserName_Id');
        params.userName = userInput ? userInput.getValue() : '';

        e.params = params;
    }

    // ================================================================
    // 9. 用户列表加载完成：动态创建列（只创建一次）
    // ================================================================
    function onUserGridLoad(e) {
        var grid = e.sender;
        var result = e.result || {};

        // ★ 首次加载时动态创建列（只创建一次）
        if (!grid._columnsCreated) {
            createUserGridColumns(grid, result);
            grid._columnsCreated = true;
        }
    }
    
    function onUserGridSelectionchanged(){
    	 var grid = mini.get('UserInfoGridPanel_Id');
         if (!grid) return;
         var btn = mini.get('userDeleteBtn');
         if (!btn) return;
         var rows = grid.getSelecteds();
         if(rows.length>0){
        	 btn.setEnabled(true && editFlag);
         }else{
        	 btn.setEnabled(false);
         }
    }

    // ================================================================
    // 10. 动态创建用户列表列
    //     对应 ExtJS createUserGridColumn + UserPanelInfoStore 的 columns
    // ================================================================
    function createUserGridColumns(userGrid, result) {
    var columns = [];

    // ---------- 编辑器工厂（editFlag=false 时返回 null，等于不可编辑） ----------
    function textEditor(allowBlank) {
        return editFlag ? { type: 'textbox', allowBlank: !!allowBlank } : null;
    }
    function comboboxEditor(source, allowBlank) {
        if (!editFlag) return null;
        return {
            type: 'combobox',
            data: source,
            valueField: 'id',
            textField: 'text',
            allowInput: false,
            allowBlank: !!allowBlank
        };
    }
    /**
     * 勾选列：editFlag=true 时用 checkcolumn（可点击切换），
     *         editFlag=false 时退化为只读渲染（避免误改）
     */
    function checkColumn(field, header, width) {
    	return {
            field: field,
            header: header,
            headerAlign: 'center',
            align: 'center',
            width: width,
            trueValue: true,
            falseValue: false,
            type: 'checkboxcolumn'
        };
    }

    // ---------- 下拉数据源 ----------
    var roleComboxSource = [];
    if (result && result.roleList) {
        for (var j = 0; j < result.roleList.length; j++) {
            roleComboxSource.push({
                id: result.roleList[j][0],
                text: result.roleList[j][1]
            });
        }
    }

    var languageComboxSource = [];
    if (result && result.languageList) {
        for (var k = 0; k < result.languageList.length; k++) {
            languageComboxSource.push({
                id: result.languageList[k][0],
                text: result.languageList[k][1]
            });
        }
    }

    // ---------- 1. 多选列（对应 ExtJS 的 checkboxmodel） ----------
    if (editFlag) {
        columns.push({
            type: 'checkcolumn',
            width: 40,
            header: '',
            headerAlign: 'center',
            align: 'center'
        });
    }

    // ---------- 2. 序号列 ----------
    columns.push({
        type: 'indexcolumn',
        width: 50,
        headerAlign: 'center',
        align: 'center',
        header: _loginUserLanguageResource.idx
    });

    // ---------- 3. 姓名 ----------
    columns.push({
        field: 'userName',
        header: _loginUserLanguageResource.userName,
        headerAlign: 'center',
        align: 'center',
        width: 120,
        editor: textEditor(false),          // 对应 allowBlank:false
        renderer: function (e) {
            return adviceCurrentUserName(e.value, e.record);
        }
    });

    // ---------- 4. 用户账号 ----------
    columns.push({
        field: 'userId',
        header: _loginUserLanguageResource.userAccount,
        headerAlign: 'center',
        align: 'center',
        width: 120,
        editor: textEditor(false)          // 对应 allowBlank:false
    });

    // ---------- 5. 角色 ----------
    columns.push({
        field: 'userTypeName',
        header: _loginUserLanguageResource.role,
        headerAlign: 'center',
        align: 'center',
        width: 120,
        editor: comboboxEditor(roleComboxSource, false)  // 对应 allowBlank:false
    });

    // ---------- 6. 电话（正则校验在 oncellendedit 里做） ----------
    columns.push({
        field: 'userPhone',
        header: _loginUserLanguageResource.phone,
        headerAlign: 'center',
        align: 'center',
        width: 120,
        editor: textEditor(true)           // 对应 allowBlank:true
    });

    // ---------- 7. 邮箱（正则校验在 oncellendedit 里做） ----------
    columns.push({
        field: 'userInEmail',
        header: _loginUserLanguageResource.email,
        headerAlign: 'center',
        align: 'center',
        width: 180,
        editor: textEditor(true)          // 对应 allowBlank:true
    });

    // ---------- 8. 快速登录 ----------
    columns.push(checkColumn(
        'userQuickLoginName',
        _loginUserLanguageResource.userQuickLogin,
        90
    ));

    // ---------- 9. 短信接收（受 emailConfig.enable 控制） ----------
    if (emailEnable) {
        columns.push(checkColumn(
            'receiveSMSName',
            _loginUserLanguageResource.receiveSMS,
            90
        ));
    }

    // ---------- 10. 邮件接收（受 emailConfig.enable 控制） ----------
    if (emailEnable) {
        columns.push(checkColumn(
            'receiveMailName',
            _loginUserLanguageResource.receiveMail,
            90
        ));
    }

    // ---------- 11. 语言（默认隐藏，对应 ExtJS hidden:true） ----------
    columns.push({
        field: 'userLanguageName',
        header: _loginUserLanguageResource.language,
        headerAlign: 'center',
        align: 'center',
        width: 100,
        visible: false,
        editor: comboboxEditor(languageComboxSource, false)
    });

    // ---------- 12. 状态 ----------
    columns.push(checkColumn(
        'userEnableName',
        _loginUserLanguageResource.status,
        80
    ));

    // ---------- 13. 所属组织 ----------
    columns.push({
        field: 'allPath',
        header: _loginUserLanguageResource.owningOrg,
        headerAlign: 'center',
        align: 'center',
        width: 200
    });

    // ---------- 14. 创建时间 ----------
    columns.push({
        field: 'userRegtime',
        header: _loginUserLanguageResource.createTime,
        headerAlign: 'center',
        align: 'center',
        width: 140,
        dateFormat: 'yyyy-MM-dd HH:mm:ss'
    });

    userGrid.setColumns(columns);
    userGrid.setAllowCellEdit(editFlag);
    userGrid.setAllowCellSelect(editFlag);
}
    
 // ================================================================
 // 10.1 单元格开始编辑前：当前登录用户敏感字段禁止编辑
 // ================================================================
 function onUserGridCellBeginEdit(e) {
     var record = e.record;
     if (!record) return;

     if (editFlag){
    	// 当前登录用户本人
         if (String(record.userNo) === String(user_)) {
             var f = (e.field || '').toLowerCase();
             if (f === 'userid'        // 账号
                 || f === 'usertypename'  // 角色
                 || f === 'userenablename') { // 状态
                 e.cancel = true;
             }
         }
     }else{
    	 e.cancel = true;
     }
 }
 
//================================================================
//10.2 单元格结束编辑后：电话 / 邮箱格式校验
//    对应 ExtJS 编辑器里的 regex 配置
//================================================================
function onUserGridCellEndEdit(e) {
  var grid = e.sender;
  var field = (e.field || '').toLowerCase();
  var value = e.value;

  // 为空时不做格式校验（业务上允许为空）
  if (!isNotVal(value)) return;

  // 电话
  if (field === 'userphone') {
      var phoneReg = /^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\d{8}$/;
      if (!phoneReg.test(value)) {
          mini.alert((_loginUserLanguageResource.dataFormattingError));
          e.record.userPhone = '';
          grid.updateRow(e.record);
      }
      return;
  }

  // 邮箱
  if (field === 'userinemail') {
      var emailReg = /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/;
      if (!emailReg.test(value)) {
          mini.alert((_loginUserLanguageResource.dataFormattingError));
          e.record.userInEmail = '';
          grid.updateRow(e.record);
      }
      return;
  }
}

//================================================================
//11.1 判断是否为真值（勾选列渲染用）
//================================================================
function isTrueVal(v) {
 return v === true || v === 1 || v === '1' || v === 'true';
}

    // ================================================================
    // 11. 当前登录用户名加 * 前缀（对应 ExtJS adviceCurrentUserName）
    // ================================================================
    function adviceCurrentUserName(val, record) {
        var showVal = val;
        if (record && record.userNo == user_) {
            showVal = '*' + val;
        }
        if (isNotVal(showVal)) {
            return '<span title="' + String(val || '').replace(/"/g, '&quot;') + '">'
                + String(showVal || '').replace(/"/g, '&quot;') + '</span>';
        }
        return '';
    }
    
    function refreshMainOrgTree(type){
    	if (window.parent && window.parent !== window) {
			window.parent.postMessage({
				action: 'refreshMainOrgTree',
				type:type,
				orgId: _selectedOrgId,
				orgName: _currentOrgNode ? _currentOrgNode.text : ''
			}, window.location.origin);
		}
    }
    
    function saveOrgInfo() {
        if (!editFlag) return;

        var tree = mini.get('OrgInfoTreeGridView_Id');
        if (!tree) return;

        // ★ 关键：先结束当前单元格编辑，把编辑器里的值提交到 record
        tree.commitEdit();

        // ★ 获取所有被修改过的行（_state === 'modified'）
        //   第二个参数 onlyField=true 只返回被修改的字段，
        //   但这里我们要取全部业务字段，所以传 false（或不传）
        var modifiedRecords = tree.getChanges('modified', false);
        if (!modifiedRecords || modifiedRecords.length === 0) {
            mini.alert(_loginUserLanguageResource.noDataChange);
            return;
        }

        // ---- 组装提交数据（字段完全对照 ExtJS） ----
        var modifiedOrg = [];
        for (var i = 0; i < modifiedRecords.length; i++) {
            var rec = modifiedRecords[i];
            var org = {
                orgId:         rec.orgId,
                orgParent:     rec.orgParent,
                orgName_zh_CN: rec.orgName_zh_CN,
                orgName_en:    rec.orgName_en,
                orgName_ru:    rec.orgName_ru,
                orgMemo:       rec.orgMemo
            };
            
            if (isNotVal(rec.orgSeq) && isNumber(rec.orgSeq)) {
                org.orgSeq = rec.orgSeq;
            }
            modifiedOrg.push(org);
        }

        $.ajax({
            url: context + '/orgManagerController/batchUpdateOrgInfo',
            type: 'POST',
            data: { data: JSON.stringify(modifiedOrg) },
            dataType: 'json',
            success: function (result) {
                if (result.success === true && result.flag === true) {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    // ★ 保存成功后清空变更标记，避免重复提交
                    tree.accept();
                    // ★ 通知主界面刷新左侧组织树
    				if (window.parent && window.parent !== window) {
    					refreshMainOrgTree("update");
    				} else {
        				// 该 JSP 被独立打开（无父窗口）时，退化成本地刷新
        				loadOrgTree();
    				}
                    
                } else if (result.success === true && result.flag === false) {
                    mini.alert(_loginUserLanguageResource.saveFailed);
                } else {
                    mini.alert(_loginUserLanguageResource.saveFailed);
                }
            },
            error: function () {
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }
    
 // ================================================================
 // 添加组织（打开独立窗口）
 // ================================================================
 function addOrgInfo() {
     var tree = mini.get('OrgInfoTreeGridView_Id');
     if (!tree) return;

     // 默认父节点：当前选中节点；若无选中，取第一个子节点
     var selectedNode = tree.getSelectedNode();
     var selectedOrgId = '';
     var selectedOrgName = '';
     if (selectedNode) {
         selectedOrgId = selectedNode.orgId;
         selectedOrgName = selectedNode.text;
     } else {
         var root = tree.getRootNode();
         if (root && root.children && root.children.length > 0) {
             selectedOrgId = root.children[0].orgId;
             selectedOrgName = root.children[0].text;
         }
     }

     mini.open({
         title: _loginUserLanguageResource.addOrg,
         url: context + '/miniui-app/modules/orgAndUser/orgAddWindow.jsp',
         width: 420,
         height: 300,
         modal: true,
         allowResize: true,
         onload: function () {
             var iframe = this.getIFrameEl();
             var contentWindow = iframe.contentWindow;
             contentWindow.setData({
                 orgParentId: selectedOrgId,
                 orgParentName: selectedOrgName
             });

             // ★ 子窗口回调：刷新本地组织树 + 通知主界面
             contentWindow._parentRefreshOrgTree = function () {
            	 loadOrgTree();
                 refreshMainOrgTree("add");
             };
         }
     });
 }

 // ================================================================
 // 4.4 获取组织关联信息（设备数 / 用户数）
 // ================================================================
 function getOrgAssociatedInformation(orgId) {
     var result = { deviceCount: 0, userCount: 0 };
     $.ajax({
         url: context + '/orgManagerController/getOrgAssociatedInformation',
         type: 'POST',
         data: { orgId: orgId },
         dataType: 'json',
         async: false,             // 同步：确认框里要立刻用这两个数
         success: function (resp) {
             if (resp) {
                 result.deviceCount = parseInt(resp.deviceCount || 0);
                 result.userCount = parseInt(resp.userCount || 0);
             }
         }
     });
     return result;
 }

 // ================================================================
 // 4.5 删除组织
 // ================================================================
 function delOrgInfo() {
     if (!editFlag) return;

     var tree = mini.get('OrgInfoTreeGridView_Id');
     if (!tree) return;

     var node = tree.getSelectedNode();
     if (!node || !node.orgId || parseInt(node.orgId) <= 0) {
         mini.alert(_loginUserLanguageResource.checkOne);
         return;
     }
     var parentNode=tree.getParentNode(node);
     // 按当前语言取删除组织名
     var delOrgName = node.text || '';
     var lang = (_loginUserLanguage || '').toUpperCase();
     if (lang === 'ZH_CN' && node.orgName_zh_CN) {
         delOrgName = node.orgName_zh_CN;
     } else if (lang === 'EN' && node.orgName_en) {
         delOrgName = node.orgName_en;
     } else if (lang === 'RU' && node.orgName_ru) {
         delOrgName = node.orgName_ru;
     }

     var confirmInfo = _loginUserLanguageResource.orgName
         + ":<font color=red>" + delOrgName + "</font>";

     // 关联信息
     var assoc = getOrgAssociatedInformation(node.orgId);
     if (assoc.deviceCount > 0 || assoc.userCount > 0) {
         confirmInfo += "<br/>" + _loginUserLanguageResource.orgAssociatedInformation;
         if (assoc.userCount > 0) {
             confirmInfo += "<br/>" + _loginUserLanguageResource.userCount
                 + ":<font color=red>" + assoc.userCount + "</font>";
         }
         if (assoc.deviceCount > 0) {
             confirmInfo += "<br/>" + _loginUserLanguageResource.deviceCount
                 + ":<font color=red>" + assoc.deviceCount + "</font>";
         }
     }
     confirmInfo += '<br/>' + _loginUserLanguageResource.confirmDelete;

     mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
         if (action !== 'ok') return;
         $.ajax({
             url: context + '/orgManagerController/doOrgBulkDelete',
             type: 'POST',
             data: { paramsId: node.orgId },
             dataType: 'json',
             success: function (result) {
                 if (result.flag === true) {
                     mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                     // 本地树刷新：选中父节点
                     _selectedOrgId = parentNode.orgId;
                     loadOrgTree();
                     // 通知主界面
                     refreshMainOrgTree("delete");
                 } else {
                     mini.alert('<font color=red>'+ _loginUserLanguageResource.deleteFailed + '</font>');
                 }
             },
             error: function () {
                 mini.alert('【<font color=red>'+ _loginUserLanguageResource.exceptionThrow+ '</font>】' + _loginUserLanguageResource.contactAdmin);
             }
         });
     });
 }
 
//================================================================
//保存用户列表修改
//使用 MiniUI getChanges('modified') 获取修改过的用户
//================================================================
function batchUpdateUserInfo() {
  if (!editFlag) return;

  var grid = mini.get('UserInfoGridPanel_Id');
  if (!grid) return;

  // ★ 先提交正在编辑的单元格，确保 getChanges 能拿到最新值
  grid.commitEdit();

  // ★ 获取所有被修改过的行（_state === 'modified'）
  //   第二个参数 onlyField=false：返回整行数据，方便组装所有业务字段
  var modifiedRecords = grid.getChanges('modified', false);
  if (!modifiedRecords || modifiedRecords.length === 0) {
      mini.alert(_loginUserLanguageResource.noDataChange,_loginUserLanguageResource.tip);
      return;
  }

  // ---- 组装提交数据 ----
  var modifiedUsers = [];
  for (var i = 0; i < modifiedRecords.length; i++) {
      var rec = modifiedRecords[i];
      var u = {};
      u.userNo            = rec.userNo;
      u.userName          = rec.userName;
      u.userId            = rec.userId;
      u.userTypeName_zh_CN = rec.userTypeName;
      u.userTypeName_en    = rec.userTypeName;
      u.userTypeName_ru    = rec.userTypeName;
      u.userPhone         = rec.userPhone;
      u.userInEmail       = rec.userInEmail;
      u.userQuickLogin    = isTrueVal(rec.userQuickLoginName) ? 1 : 0;
      u.receiveSMS        = isTrueVal(rec.receiveSMSName) ? 1 : 0;
      u.receiveMail       = isTrueVal(rec.receiveMailName) ? 1 : 0;
      u.userEnable        = isTrueVal(rec.userEnableName) ? 1 : 0;
      u.userLanguageName  = rec.userLanguageName;
      modifiedUsers.push(u);
  }

  $.ajax({
      url: context + '/userManagerController/batchUpdateUserInfo',
      type: 'POST',
      data: { data: JSON.stringify(modifiedUsers) },
      dataType: 'json',
      success: function (result) {
          if (result.success === true && result.flag === true) {
              mini.alert(_loginUserLanguageResource.savedSuccessfully,_loginUserLanguageResource.tip);
              // ★ 清空修改标记
              grid.accept();
              // 重新加载用户列表（保持当前组织筛选）
              loadUserList();
          } else if (result.success === true && result.flag === false) {
              mini.alert('<font color="red">'+ _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
          } else {
              mini.alert('<font color="red">'+ _loginUserLanguageResource.saveFailed+ '</font>', _loginUserLanguageResource.tip);
          }
      },
      error: function () {
          mini.alert(_loginUserLanguageResource.requestFailed,_loginUserLanguageResource.tip);
      }
  });
}

//================================================================
//添加用户（打开独立窗口）
//================================================================
function addUserInfo() {
 if (!editFlag) return;

 var tree = mini.get('OrgInfoTreeGridView_Id');
 if (!tree) return;

 var selectedNode = tree.getSelectedNode();
 var selectedOrgId = '';
 var selectedOrgName = '';

 if (selectedNode) {
     selectedOrgId = selectedNode.orgId;
     selectedOrgName = getOrgFullPath(selectedNode, tree);
 } else {
     var root = tree.getRootNode();
     if (root && root.children && root.children.length > 0) {
         selectedOrgId = root.children[0].orgId;
         selectedOrgName = root.children[0].text;
     }
 }

 if (!selectedOrgId) {
     mini.alert(_loginUserLanguageResource.addOrgFirst,_loginUserLanguageResource.tip);
     return;
 }

 mini.open({
     title: _loginUserLanguageResource.addUser,
     url: context + '/miniui-app/modules/orgAndUser/userAddWindow.jsp',
     width: 480,
     height: 560,
     modal: true,
     allowResize: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;
         contentWindow.setData({
             orgId: selectedOrgId,
             orgName: selectedOrgName
         });
         // ★ 子窗口回调：刷新用户列表
         contentWindow._parentRefreshUserList = function () {
             loadUserList();
         };
     }
 });
}

//拼接组织全路径（同级节点 -> 根节点）
function getOrgFullPath(node, tree) {
    if (!node || !tree) return '';

    var path = [];
    var visited = {};        // 防止脏数据成环
    var guard = 0;           // 硬上限兜底
    var current = node;

    while (current && guard++ < 200) {
        // ① 访问去重：同一个 orgId 处理过一次就停
        var key = (current.orgId !== undefined && current.orgId !== null)
            ? String(current.orgId)
            : null;
        if (key !== null) {
            if (visited[key]) break;
            visited[key] = true;
        }

        // ② 收集当前节点文本
        path.unshift(current.text || '');

        // ③ 到根即停
        var parentId = current.orgParent;
        if (parentId === undefined || parentId === null || parentId === ''
            || parentId === '0' || parentId === 0) {
            break;
        }

        // ④ 用 MiniUI 官方 API 取父节点
        var parent = null;
        try {
            parent = tree.getParentNode(current);
        } catch (e) {
            parent = null;
        }

        // ⑤ 取不到父，或父就是自己 → 停
        if (!parent || parent === current) break;

        current = parent;
    }

    return path.join('/');
}

//================================================================
//批量删除用户
//================================================================
function batchDeleteUser() {
 if (!editFlag) return;

 var grid = mini.get('UserInfoGridPanel_Id');
 if (!grid) return;

 // 与 ExtJS 一致：先拿 selModel 选中的行
 var rows = grid.getSelecteds();
 if (!rows || rows.length === 0) {
     mini.alert(_loginUserLanguageResource.checkOne,
                _loginUserLanguageResource.tip);
     return;
 }

 // 只选了一行且是当前登录用户 → 直接提示
 if (rows.length === 1 && String(rows[0].userNo) === String(user_)) {
     mini.alert('<font color=red>'
         + _loginUserLanguageResource.cannotDeleteLoginUser
         + '</font>', _loginUserLanguageResource.tip);
     return;
 }

 // 过滤掉当前登录用户
 var selectUserNo = [];
 var selectUserNameList = [];
 for (var i = 0; i < rows.length; i++) {
     if (String(rows[i].userNo) !== String(user_)) {
         selectUserNo.push(rows[i].userNo);
         selectUserNameList.push(rows[i].userName);
     }
 }

 // 拼装确认信息（完全对照 ExtJS）
 var deleteInfo = _loginUserLanguageResource.confirmDelete;
 if (selectUserNo.length === 1) {
     deleteInfo = _loginUserLanguageResource.userName
         + ":<font color=red>" + selectUserNameList[0] + "</font>"
         + "</br>" + _loginUserLanguageResource.confirmDelete;
 } else {
     deleteInfo = _loginUserLanguageResource.sparseRecordCount
         + ":<font color=red>" + selectUserNo.length + "</font>"
         + "</br>" + _loginUserLanguageResource.confirmDelete;
 }

 if (selectUserNo.length === 0) {
     mini.alert(_loginUserLanguageResource.checkOne,
                _loginUserLanguageResource.tip);
     return;
 }

 mini.confirm(deleteInfo, _loginUserLanguageResource.tip, function (action) {
     if (action !== 'ok') return;

     $.ajax({
         url: context + '/userManagerController/doUserBulkDelete',
         type: 'POST',
         data: { paramsId: selectUserNo.join(',') },
         dataType: 'json',
         success: function (result) {
             if (result.flag === true) {
                 mini.alert(_loginUserLanguageResource.deleteSuccessfully,
                            _loginUserLanguageResource.tip);
             } else {
                 mini.alert('<font color=red>'
                     + _loginUserLanguageResource.deleteFailed
                     + '</font>', _loginUserLanguageResource.tip);
             }
             loadUserList();
         },
         error: function () {
             mini.alert(_loginUserLanguageResource.requestFailed,
                        _loginUserLanguageResource.tip);
         }
     });
 });
}

//================================================================
//组织隶属迁移
//================================================================
function orgParentChangeInfo() {
 if (!editFlag) return;

 mini.open({
     title: _loginUserLanguageResource.orgParentChange,
     url: context + '/miniui-app/modules/orgAndUser/orgParentChangeWindow.jsp',
     width: 600,
     height: 600,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
    	 var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;
         // ★ 子窗口回调：刷新本地组织树 + 通知主界面
         contentWindow._parentRefreshOrgTree = function () {
        	 loadOrgTree();
             refreshMainOrgTree("update");
         };
     }
 });
}

//================================================================
//用户隶属迁移（打开独立窗口）
//================================================================
function userOrgChangeInfo() {
 if (!editFlag) return;

 // 当前用户列表使用的组织筛选和用户名搜索，直接传给子窗口
 var orgIds = _allOrgIds || '';
 if (!orgIds) {
     // 兜底：用左侧主界面的组织树选中值
     var tree = mini.get('OrgInfoTreeGridView_Id');
     var node = tree ? tree.getSelectedNode() : null;
     if (node) orgIds = foreachAndSearchOrgChildId(node);
 }

 var userInput = mini.get('UserName_Id');
 var userName = userInput ? (userInput.getValue() || '') : '';

 mini.open({
     title: _loginUserLanguageResource.userOrgChange,
     url: context + '/miniui-app/modules/orgAndUser/userOrgChangeWindow.jsp',
     width: 750,
     height: 600,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
    	 var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;
         contentWindow.setData({
             orgIds: orgIds,
             userName: userName,
             user_:user_
         });
         // ★ 子窗口回调：刷新本地组织树 + 通知主界面
         contentWindow._parentRefreshData = function () {
        	 loadUserList();
             refreshMainOrgTree("update");
         };
     }
 });
}

//================================================================
//10.6 修改密码（打开独立窗口）
//   对应 ExtJS 的 modifyUserInfo()
//================================================================
function modifyUserInfo() {
 if (!editFlag) return;

 var grid = mini.get('UserInfoGridPanel_Id');
 if (!grid) return;

 // 只允许操作一行（ExtJS 用的 getLastSelected，这里保持一致）
 var selected = grid.getSelected();
 if (!selected) {
     mini.alert(_loginUserLanguageResource.checkOne,
                _loginUserLanguageResource.tip);
     return;
 }

 mini.open({
     title: _loginUserLanguageResource.passwordReset,
     url: context + '/miniui-app/modules/orgAndUser/userEditPasswordWindow.jsp',
     width: 380,
     height: 260,
     modal: true,
     allowResize: true,
     onload: function () {
         var iframe = this.getIFrameEl();var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;
         contentWindow.setData({
             userNo:   selected.userNo,
             userName: selected.userName,
             userId:   selected.userId
         });
         contentWindow._parentRefreshData = function () {
        	 loadUserList();
         };
     }
 });
}

//================================================================
//导出组织完整数据
//================================================================
function exportOrganizationCompleteData() {
 if (!editFlag) return;

 var url = context + '/orgManagerController/exportOrganizationCompleteData';

 var timestamp = new Date().getTime();
 var key = 'exportOrganizationCompleteData' + '_' + timestamp;
 var maskPanelId = 'orgPanel';

 var param = "&recordCount=10000"
     + "&fileName=" + URLencode(URLencode(_loginUserLanguageResource.organizationExportFileName))
     + '&key=' + key;

 exportDataMask(key, maskPanelId, _loginUserLanguageResource.loadingData);
 downloadFile(url + '?flag=true' + param);
}

//================================================================
//导出用户完整数据
//================================================================
function exportUserCompleteData() {
 if (!editFlag) return;

 var url = context + '/userManagerController/exportUserCompleteData';

 var timestamp = new Date().getTime();
 var key = 'exportUserCompleteData' + '_' + timestamp;
 var maskPanelId = 'userPanel';

 var param = "&recordCount=10000"
     + "&fileName=" + URLencode(URLencode(_loginUserLanguageResource.userExportFileName))
     + '&key=' + key;

 exportDataMask(key, maskPanelId, _loginUserLanguageResource.loadingData);
 downloadFile(url + '?flag=true' + param);
}

//================================================================
//打开"导入组织"窗口
//================================================================
function openImportOrganizationWindow() {
 if (!editFlag) return;

 mini.open({
     title: _loginUserLanguageResource.importOrganization,
     url: context + '/miniui-app/modules/orgAndUser/importOrganizationWindow.jsp',
     width: 700,
     height: 620,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;

         // ★ 暴露给子窗口的刷新回调：导入后刷新组织树 + 通知主界面
         contentWindow.parent.refreshOrgTreeAfterImport = function () {
             loadOrgTree();
             refreshMainOrgTree("import");
         };
     }
 });
}

//================================================================
//打开"导入用户"窗口
//================================================================
function openImportUserWindow() {
 if (!editFlag) return;

 mini.open({
     title: _loginUserLanguageResource.importUser,
     url: context + '/miniui-app/modules/orgAndUser/importUserWindow.jsp',
     width: 900,
     height: 620,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;

         // ★ 暴露给子窗口的刷新回调：导入后刷新用户列表
         contentWindow.parent.refreshUserListAfterImport = function () {
             loadUserList();
         };
     }
 });
}
	
    // ================================================================
    // 12. 国际化
    // ================================================================
    function initI18n() {
        var userNameLabel = document.getElementById('userNameLabel');
        if (userNameLabel) {
            userNameLabel.textContent = (_loginUserLanguageResource.userName) + '：';
        }

        var userNameInput = mini.get('UserName_Id');
        if (userNameInput) {
            userNameInput.setEmptyText('');
        }

        var orgBtnMap = {
            'orgRefreshBtn': 'refresh',
            'addOrgLableClassBtn_Id': 'add',
            'editOrgLableClassBtn_Id': 'update',
            'delOrgLableClassBtn_Id': 'deleteData',
            'orgSaveBtn': 'save',
            'orgParentChangeBtn': 'orgParentChange',
            'orgExportBtn': 'exportData',
            'orgImportBtn': 'importData'
        };
        for (var id in orgBtnMap) {
            var btn = mini.get(id);
            if (btn) btn.setText(_loginUserLanguageResource[orgBtnMap[id]] || orgBtnMap[id]);
        }

        var userBtnMap = {
            'userSearchBtn': 'search',
            'addUserLableClassBtn_Id': 'add',
            'userDeleteBtn': 'deleteData',
            'userSaveBtn': 'save',
            'editUserLableClassBtn_Id': 'passwordReset',
            'userOrgChangeBtn': 'userOrgChange',
            'userExportBtn': 'exportData',
            'userImportBtn': 'importData'
        };
        for (var id2 in userBtnMap) {
            var btn2 = mini.get(id2);
            if (btn2) btn2.setText(_loginUserLanguageResource[userBtnMap[id2]] || userBtnMap[id2]);
        }

        var orgTree = mini.get('OrgInfoTreeGridView_Id');
        if (orgTree) orgTree.setEmptyText(_loginUserLanguageResource.emptyMsg);

        var userGrid = mini.get('UserInfoGridPanel_Id');
        if (userGrid) userGrid.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 13. 按钮权限控制
    // ================================================================
    function updateBtnStatus() {
        var btnIds = [
            'addOrgLableClassBtn_Id', 'editOrgLableClassBtn_Id',
            'orgSaveBtn', 'orgParentChangeBtn', 'orgExportBtn', 'orgImportBtn',
            'addUserLableClassBtn_Id', 'userSaveBtn', 'editUserLableClassBtn_Id',
            'userOrgChangeBtn', 'userExportBtn', 'userImportBtn'
        ];
        var disabledIds = [
            'delOrgLableClassBtn_Id', 'userDeleteBtn'
        ];
        for (var i = 0; i < btnIds.length; i++) {
            var btn = mini.get(btnIds[i]);
            if (btn) btn.setEnabled(editFlag);
        }
        for (var i = 0; i < disabledIds.length; i++) {
            var btn = mini.get(disabledIds[i]);
            if (btn) btn.setEnabled(false);
        }
    }

    // ================================================================
    // 页面初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();
        updateBtnStatus();

        setTimeout(function () {
            isInitializing = false;
            loadOrgTree();
        }, 100);

        window.addEventListener('message', function (event) {
            var message = event.data;
            if (!message || !message.action) return;
            switch (message.action) {
                case 'refresh':
                    loadOrgTree();
                    break;
            }
        });

        console.log('组织用户管理模块加载完成');
    });
</script>
</body>
</html>