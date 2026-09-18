<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*,com.cosog.model.User,com.cosog.utils.ConfigFile,com.google.gson.Gson" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
Gson gson = new Gson();
String path = request.getContextPath();
User userLogin = (User)session.getAttribute("userLogin");
String userLoginNo = userLogin != null ? userLogin.getUserNo() + "" : "";

int loginUserRoleLevel         = userLogin != null ? userLogin.getRoleLevel() : 0;
int loginUserRoleShowLevel     = userLogin != null ? userLogin.getRoleShowLevel() : 0;
int loginUserRoleVideoKeyEdit  = userLogin != null ? userLogin.getRoleVideoKeyEdit() : 0;
int loginUserLanguageKeyEdit   = userLogin != null ? userLogin.getRoleLanguageEdit() : 0;
String loginUserLanguage       = userLogin != null ? userLogin.getLanguageName() + "" : "zh_CN";
int loginUserLanguageValue     = userLogin != null ? userLogin.getLanguage() : 0;
String loginUserLanguageListJson = gson.toJson(
    userLogin != null && userLogin.getLanguageList() != null
        ? userLogin.getLanguageList()
        : new ArrayList<>()
);

String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if (otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>角色管理</title>
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

        .role-container { width: 100%; height: 100%; background: #fff; }

        .mini-splitter-border { border: 0 !important; }
        .mini-splitter-pane   { padding: 0 !important; border: 0 !important; }

        .mini-splitter-handler {
            background: transparent !important;
            border: 1px solid #e8e8e8 !important;
        }

        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; }
        .mini-panel-body { border: 0 !important; }

        .mini-datagrid,
        .mini-treegrid,
        .mini-tree {
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

<div class="role-container">
    <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">

        <!-- ========== 左：角色列表 ========== -->
        <div size="60%" showCollapseButton="false" minSize="400">
            <div id="roleListPanel" class="mini-panel" style="width:100%;height:100%;" showToolbar="true" showHeader="false" showCollapseButton="false" showCloseButton="false" bodyStyle="padding:0;">
                <div property="toolbar" style="padding:4px 8px;background: #fafafa;">
                    <table style="width:100%;border-collapse:collapse;">
                        <tr>
                            <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                <button id="roleRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true" onclick="loadRoleList()"></button>
                                <span id="roleNameLabel" style="margin-left:4px;"></span>
                                <input id="RoleName_Id" class="mini-textbox" style="width:160px;" onenter="loadRoleList()" />
                                <button id="roleSearchBtn" class="mini-button" iconCls="search" plain="true" onclick="loadRoleList()"></button>
                            </td>
                            <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                <button id="addroleLabelClassBtn_Id" class="mini-button" iconCls="add" plain="true" onclick="addroleInfo()"></button>
                                <button id="delRoleBtn" class="mini-button" iconCls="delete" plain="true" onclick="delroleInfo()"></button>
                                <button id="roleSaveBtn" class="mini-button" iconCls="save" plain="true" onclick="updateRoleInfo()"></button>
                                <button id="roleExportBtn" class="mini-button" iconCls="export" plain="true" onclick="exportRoleCompleteData()"></button>
                                <button id="roleImportBtn" class="mini-button" iconCls="import" plain="true" onclick="openImportRoleWindow()"></button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="RoleInfoGridPanel_Id" class="mini-datagrid" style="width:100%;height:100%;"
                     idField="roleId"
                     allowResize="false"
                     allowAlternating="true"
                     showPager="false"
                     pageSize="100"
                     showPageInfo="false"
                     multiSelect="false"
                     allowCellEdit="false"
                     allowCellSelect="false"
                     showEmptyText="true"
                     dataField="totalRoot"
                     totalField="totalCount"
                     cellEditAction="celldblclick"
                     onbeforeload="onRoleGridBeforeLoad"
                     onload="onRoleGridLoad"
                     onselect="onRoleGridSelect"
                     oncellbeginedit="onRoleGridCellBeginEdit">
                    <div property="columns"></div>
                    <div property="emptyText" class="empty-msg"></div>
                </div>
            </div>
        </div>

        <!-- ========== 右：权限区 ========== -->
        <div size="40%" showCollapseButton="true" collapseDirection="right" minSize="350">
            <div id="permissionPanel" class="mini-panel" style="width:100%;height:100%;" showToolbar="true" showHeader="false" showCollapseButton="false" showCloseButton="false" bodyStyle="padding:0;">
                <div property="toolbar" style="padding:4px 8px;background: #fafafa;">
                    <table style="width:100%;border-collapse:collapse;">
                        <tr>
                            <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                <span id="permissionLabel"></span>
                            </td>
                            <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                <button id="roleGrantRightBtn_Id" class="mini-button" iconCls="save" plain="true" onclick="grantRoleRight()"></button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">

                    <!-- 模块权限 -->
                    <div size="60%" showCollapseButton="false" minSize="200">
                        <div id="modulePermissionPanel" class="mini-panel" style="width:100%;height:100%;" showToolbar="false" showHeader="true" showCollapseButton="false" showCloseButton="false" bodyStyle="padding:0;">
                            <div id="RightModuleTreeInfoGridPanel_Id" class="mini-treegrid" style="width:100%;height:100%;"
                                 showTreeIcon="true"
                                 treeColumn="taskname"
                                 idField="mdId"
                                 textField="text"
                                 parentField="pid"
                                 dataField="children"
                                 resultAsTree="true"
                                 allowResize="false"
                                 allowAlternating="false"
                                 showPager="false"
                                 showEmptyText="true"
                                 showHGridLines="false"
                                 showVGridLines="false"
                                 allowCellSelect="false"
                                 autoLoad="false"
                                 expandOnDblClick="false"
                                 expandOnNodeClick="false"
                                 onload="onRightModuleTreeLoad">
                                <div property="columns"></div>
                                <div property="emptyText" class="empty-msg"></div>
                            </div>
                        </div>
                    </div>

                    <!-- 设备类型 + 语言 -->
                    <div size="40%" showCollapseButton="true" collapseDirection="right" minSize="200">
                        <div class="mini-splitter" style="width:100%;height:100%;" vertical="true">

                            <div size="50%" showCollapseButton="false" minSize="120">
                                <div id="deviceTypePermissionPanel" class="mini-panel" style="width:100%;height:100%;" showToolbar="false" showHeader="true" showCollapseButton="false" showCloseButton="false" bodyStyle="padding:0;">
                                    <div id="RightTabTreeInfoGridPanel_Id" class="mini-tree" style="width:100%;height:100%;"
                                         showTreeIcon="true"
                                         showCheckBox="true"
                                         checkRecursive="false"
                                         expandOnNodeClick="false"
                                         idField="deviceTypeId"
                                         textField="text"
                                         parentField="pid"
                                         resultAsTree="true"
                                         autoLoad="false"
                                         onload="onRightTabTreeLoad"
                                         onbeforenodecheck="onRightTabTreeBeforeNodeCheck">
                                        <div property="emptyText" class="empty-msg"></div>
                                    </div>
                                </div>
                            </div>

                            <div size="50%" showCollapseButton="true" collapseDirection="bottom" minSize="120">
                                <div id="languagePermissionPanel" class="mini-panel" style="width:100%;height:100%;" showToolbar="false" showHeader="true" showCollapseButton="false" showCloseButton="false" bodyStyle="padding:0;">
                                    <div id="RightLanguageTreeInfoGridPanel_Id" class="mini-tree" style="width:100%;height:100%;"
                                         showTreeIcon="true"
                                         showCheckBox="true"
                                         checkRecursive="false"
                                         expandOnNodeClick="false"
                                         idField="languageId"
                                         textField="text"
                                         parentField="pid"
                                         resultAsTree="true"
                                         autoLoad="false"
                                         onload="onRightLanguageTreeLoad"
                                         onbeforenodecheck="onRightLanguageTreeBeforeNodeCheck">
                                        <div property="emptyText" class="empty-msg"></div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>

<script>
    var context = '<%=path%>';
    var user_ = '<%=userLoginNo%>';
    var isInitializing = true;

    var loginUserRoleLevel          = <%=loginUserRoleLevel%>;
    var loginUserRoleShowLevel      = <%=loginUserRoleShowLevel%>;
    var loginUserRoleReportEdit     = true;
    var loginUserRoleVideoKeyEdit   = <%=loginUserRoleVideoKeyEdit%>;
    var loginUserLanguageKeyEdit    = <%=loginUserLanguageKeyEdit%>;
    var loginUserLanguage           = '<%=loginUserLanguage%>';
    var loginUserLanguageValue      = <%=loginUserLanguageValue%>;

    var loginUserLanguageList = JSON.parse('<%=loginUserLanguageListJson%>');

    // ================================================================
    // 模块权限
    // ================================================================
    var loginUserRoleManagerModuleRight = getRoleModuleRight(
        context + '/roleManagerController/getRoleModuleRight',
        'RoleManagement'
    );
    var viewFlag = false;
    var editFlag = false;
    var controlFlag = false;
    if (typeof loginUserRoleManagerModuleRight !== 'undefined') {
        viewFlag    = (loginUserRoleManagerModuleRight.viewFlag    == 1);
        editFlag    = (loginUserRoleManagerModuleRight.editFlag    == 1);
        controlFlag = (loginUserRoleManagerModuleRight.controlFlag == 1);
    }

    // ================================================================
    // 状态变量
    // ================================================================
    var _selectedRoleId             = null;
    var _loginUserRoleModules       = null;
    var _addRoleFlag                = null;

    var currentId          = null;
    var currentLevel       = null;
    var currentShowLevel   = null;
    var currentFlag        = null;
    var currentReportEdit  = null;
    var currentVideoKeyEdit= null;
    var currentLanguageEdit= null;

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;

        var roleListPanel = mini.get('roleListPanel');
        if (roleListPanel) roleListPanel.setTitle(R.roleList);

        var permissionPanel = mini.get('permissionPanel');
        if (permissionPanel) permissionPanel.setTitle(R.permission);

        var modulePermissionPanel = mini.get('modulePermissionPanel');
        if (modulePermissionPanel) modulePermissionPanel.setTitle(R.moduleLicense);

        var deviceTypePermissionPanel = mini.get('deviceTypePermissionPanel');
        if (deviceTypePermissionPanel) deviceTypePermissionPanel.setTitle(R.deviceTypeLicense);

        var languagePermissionPanel = mini.get('languagePermissionPanel');
        if (languagePermissionPanel) languagePermissionPanel.setTitle(R.languageLicense);

        var roleNameLabel = document.getElementById('roleNameLabel');
        if (roleNameLabel) roleNameLabel.textContent = R.roleName + '：';

        var btnMap = {
            'roleRefreshBtn':            'refresh',
            'roleSearchBtn':             'search',
            'addroleLabelClassBtn_Id':   'add',
            'delRoleBtn':                'deleteData',
            'roleSaveBtn':               'save',
            'roleExportBtn':             'exportData',
            'roleImportBtn':             'importData',
            'roleGrantRightBtn_Id':      'save'
        };
        for (var id in btnMap) {
            var btn = mini.get(id);
            if (btn) btn.setText(R[btnMap[id]]);
        }

        var permissionLabel = document.getElementById('permissionLabel');
        if (permissionLabel) permissionLabel.textContent = R.permission;

        var roleGrid = mini.get('RoleInfoGridPanel_Id');
        if (roleGrid) roleGrid.setEmptyText(R.emptyMsg);

        var moduleTree = mini.get('RightModuleTreeInfoGridPanel_Id');
        if (moduleTree) moduleTree.setEmptyText(R.emptyMsg);

        var tabTree = mini.get('RightTabTreeInfoGridPanel_Id');
        if (tabTree) tabTree.setEmptyText(R.emptyMsg);

        var langTree = mini.get('RightLanguageTreeInfoGridPanel_Id');
        if (langTree) langTree.setEmptyText(R.emptyMsg);

        var roleNameInput = mini.get('RoleName_Id');
        if (roleNameInput) roleNameInput.setEmptyText('');
    }

    function updateBtnStatus() {
        var editBtnIds = [
            'addroleLabelClassBtn_Id',
            'roleSaveBtn',
            'roleExportBtn',
            'roleImportBtn',
            'roleGrantRightBtn_Id'
        ];
        for (var i = 0; i < editBtnIds.length; i++) {
            var btn = mini.get(editBtnIds[i]);
            if (btn) btn.setEnabled(editFlag);
        }
        var delBtn = mini.get('delRoleBtn');
        if (delBtn) delBtn.setEnabled(false);
    }

    // ================================================================
    // 1. 角色列表
    // ================================================================
    function loadRoleList() {
        var grid = mini.get('RoleInfoGridPanel_Id');
        if (!grid) return;
        if (!grid.getUrl()) {
            grid.setUrl(context + '/roleManagerController/doRoleShow');
        }
        grid.load();
    }

    function onRoleGridBeforeLoad(e) {
    	var grid=e.sender;
    	var params = e.params || {};
    	
    	// 主动清理：请求发出前清掉选中，避免数据返回后 MiniUI 自动恢复
    	grid.deselectAll(false);
        
        var input = mini.get('RoleName_Id');
        params.roleName = input ? (input.getValue() || '') : '';
        e.params = params;
    }

    function onRoleGridLoad(e) {
        var grid = e.sender;
        var result = e.result || {};

        currentId          = result.currentId;
        currentLevel       = result.currentLevel;
        currentShowLevel   = result.currentShowLevel;
        currentFlag        = result.currentFlag;
        currentReportEdit  = result.currentReportEdit;
        currentVideoKeyEdit= result.currentVideoKeyEdit;
        currentLanguageEdit= result.currentLanguageEdit;

        if (!grid._columnsCreated) {
            createRoleGridColumns(grid, result);
            grid._columnsCreated = true;
        }

        var rows = grid.getData();
        if (!rows || rows.length === 0) return;

        // ★ 情况 1：有新添加的角色 → 我们手动选中新角色
        if (_addRoleFlag) {
            var lang = (loginUserLanguage || '').toUpperCase();
            var newRow = null;
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var rowName = '';
                if (lang === 'ZH_CN')      rowName = row.roleName_zh_CN || '';
                else if (lang === 'EN')    rowName = row.roleName_en || '';
                else if (lang === 'RU')    rowName = row.roleName_ru || '';
                if (rowName === _addRoleFlag) {
                    newRow = row;
                    break;
                }
            }
            _addRoleFlag = null;
            if (newRow) {
                grid.select(newRow);
                return;
            }
            // 找不到新角色，继续往下走
        }

        // ★ 情况 2：MiniUI 已经自动恢复了上次选中行 → 什么都不做
        if (grid.getSelected()) return;

        // ★ 情况 3：MiniUI 没有恢复 → 我们手动按 _selectedRoleId 恢复
        var targetRow = null;
        if (_selectedRoleId) {
            for (var j = 0; j < rows.length; j++) {
                if (String(rows[j].roleId) === String(_selectedRoleId)) {
                    targetRow = rows[j];
                    break;
                }
            }
        }

        // ★ 兜底：选中第一行
        if (!targetRow) targetRow = rows[0];
        grid.select(targetRow);
    }

    // ================================================================
    // 2. 角色列表动态列
    // ================================================================
    function createRoleGridColumns(grid, result) {
        var showChineseName = (result.showChineseName === undefined) ? true : !!result.showChineseName;
        var showEnglishName = (result.showEnglishName === undefined) ? true : !!result.showEnglishName;
        var showRussianName = (result.showRussianName === undefined) ? true : !!result.showRussianName;

        var columns = [];
        function textEditor(allowBlank) {
            return editFlag ? { type: 'textbox', allowBlank: !!allowBlank } : null;
        }
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
        function seqEditor(minValue) {
            return editFlag ? { type: 'spinner', minValue: minValue } : null;
        }

        if (editFlag) {
            columns.push({
                type: 'checkcolumn',
                width: 40,
                header: '',
                headerAlign: 'center',
                align: 'center'
            });
        }

        columns.push({
            type: 'indexcolumn',
            width: 50,
            headerAlign: 'center',
            align: 'center',
            header: _loginUserLanguageResource.idx
        });

        columns.push({
            field: 'roleName_zh_CN',
            header: _loginUserLanguageResource.language_zh_CN,
            headerAlign: 'center', align: 'center',
            width: 150,
            visible: showChineseName,
            editor: textEditor(true),
            renderer: function (e) { return adviceCurrentRoleName(e.value, e.record); }
        });

        columns.push({
            field: 'roleName_en',
            header: _loginUserLanguageResource.language_en,
            headerAlign: 'center', align: 'center',
            width: 150,
            visible: showEnglishName,
            editor: textEditor(true),
            renderer: function (e) { return adviceCurrentRoleName(e.value, e.record); }
        });

        columns.push({
            field: 'roleName_ru',
            header: _loginUserLanguageResource.language_ru,
            headerAlign: 'center', align: 'center',
            width: 150,
            visible: showRussianName,
            editor: textEditor(true),
            renderer: function (e) { return adviceCurrentRoleName(e.value, e.record); }
        });

        columns.push({
            field: 'roleLevel',
            header: _loginUserLanguageResource.roleLevel,
            headerAlign: 'center', align: 'center',
            width: 80,
            editor: seqEditor(currentLevel + 1)
        });

        columns.push({
            field: 'showLevel',
            header: _loginUserLanguageResource.dataShowLevel,
            headerAlign: 'center', align: 'center',
            width: 100,
            editor: seqEditor(currentShowLevel)
        });

        columns.push(checkColumn(
            'roleVideoKeyEditName',
            _loginUserLanguageResource.roleVideoKeyEdit,
            90
        ));

        var lang = (loginUserLanguage || '').toUpperCase();
        var remarkField = 'remark_zh_CN';
        if (lang === 'EN') remarkField = 'remark_en';
        else if (lang === 'RU') remarkField = 'remark_ru';

        columns.push({
            field: remarkField,
            header: _loginUserLanguageResource.roleRemark,
            headerAlign: 'left', align: 'left',
            editor: textEditor(true),
            width: 150
        });

        grid.setColumns(columns);
        grid.setAllowCellEdit(editFlag);
        grid.setAllowCellSelect(editFlag);
    }

    function adviceCurrentRoleName(val, record) {
        var showVal = val || '';
        if (record && String(record.roleId) === String(currentId)) {
            showVal = '*' + showVal;
        }
        if (showVal) {
            return '<span title="' + String(showVal).replace(/"/g, '&quot;') + '">'
                + String(showVal).replace(/"/g, '&quot;') + '</span>';
        }
        return '';
    }

    function onRoleGridCellBeginEdit(e) {
        var record = e.record;
        if (!record) return;

        if (editFlag){
        	var f = (e.field || '').toLowerCase();
        	if (String(record.roleId) === String(currentId)) {
                if (f === 'rolelevel'
                    || f === 'showlevel'
                    || f === 'rolevideokeyeditname') {
                    e.cancel = true;
                }
            }else if(f === 'roleVideoKeyEditName'.toLowerCase() && currentVideoKeyEdit == 0){
            	e.cancel = true;
            }
        } else {
            e.cancel = true;
        }
    }

    // ================================================================
    // 3. 角色选中 → 更新三棵树
    // ================================================================
    function onRoleGridSelect(e) {
        var grid = e.sender;
        var row = e.record;
        if (!row) return;

        _selectedRoleId = row.roleId;

        var roleName = row.roleName_zh_CN || '';
        var lang = (loginUserLanguage || '').toUpperCase();
        if (lang === 'EN') roleName = row.roleName_en || '';
        else if (lang === 'RU') roleName = row.roleName_ru || '';

        updatePermissionTitles(roleName);

        var isSelf = String(row.roleId) === String(currentId);

        var moduleTree = mini.get('RightModuleTreeInfoGridPanel_Id');
        if (moduleTree) {
            applyModuleRightToTree(moduleTree, row.roleId);
            moduleTree.setEnabled(!isSelf);
        }

        var tabTree = mini.get('RightTabTreeInfoGridPanel_Id');
        if (tabTree){
        	applyTabRightToTree(tabTree, row.roleId);
        	tabTree.setEnabled(!isSelf);
        } 

        var langTree = mini.get('RightLanguageTreeInfoGridPanel_Id');
        if (langTree){
        	applyLanguageRightToTree(langTree, row.roleId);
        	langTree.setEnabled(!isSelf);
        } 

        var grantBtn = mini.get('roleGrantRightBtn_Id');
        if (grantBtn) grantBtn.setEnabled(editFlag && !isSelf);

        var delBtn = mini.get('delRoleBtn');
        if (delBtn) delBtn.setEnabled(editFlag && !isSelf);
    }

    function updatePermissionTitles(roleName) {
        var R = _loginUserLanguageResource;
        var prefix = R.role + '【<font color="red">' + roleName + '</font>】 ';

        var modulePanel = mini.get('modulePermissionPanel');
        if (modulePanel) modulePanel.setTitle(prefix + R.moduleLicense);

        var deviceTypePanel = mini.get('deviceTypePermissionPanel');
        if (deviceTypePanel) deviceTypePanel.setTitle(prefix + R.deviceTypeLicense);

        var languagePanel = mini.get('languagePermissionPanel');
        if (languagePanel) languagePanel.setTitle(prefix + R.languageLicense);
    }

    // ================================================================
    // 4. 当前用户模块权限缓存
    // ================================================================
    function getLoginUserRoleModules() {
        if (_loginUserRoleModules !== null) return _loginUserRoleModules;
        var result = [];
        $.ajax({
            url: context + '/roleManagerController/getLoginUserRoleModules',
            type: 'POST',
            async: false,
            dataType: 'json',
            success: function (resp) { result = resp || []; }
        });
        _loginUserRoleModules = result;
        return result;
    }

    // ================================================================
    // 5. 模块权限树
    // ================================================================
    function loadRightModuleTreeStructure() {
        var tree = mini.get('RightModuleTreeInfoGridPanel_Id');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/moduleManagerController/constructRightModuleTreeGridTree');
        }
        tree.load();
    }

    function onRightModuleTreeLoad(e) {
        var tree = e.sender;

        if (!tree._columnsCreated) {
            createModuleTreeColumns(tree);
            tree._columnsCreated = true;
        }

        tree.expandAll();
        if (_selectedRoleId) {
            applyModuleRightToTree(tree, _selectedRoleId);
        }
    }

    // ------------------------------------------------------------------
    // 可见性判断：该位置是否应该出现 checkbox（不含"是否能改"）
    // ------------------------------------------------------------------
    function isModuleCheckboxVisible(record, field) {
        // 业务规则：编辑只对叶子，控制只对实时监控
        if (field === 'editFlagName') {
            var isLeaf = !record.children || record.children.length === 0;
            if (!isLeaf) return false;
        }
        if (field === 'controlFlagName') {
            if (String(record.mdCode).toUpperCase() !== 'DEVICEREALTIMEMONITORING') return false;
        }

        // 用户对该模块有对应权限才显示
        var mdCode = record.mdCode;
        for (var i = 0; i < _loginUserRoleModules.length; i++) {
            if (String(_loginUserRoleModules[i].mdCode) === String(mdCode)) {
                if (field === 'viewFlagName')         return _loginUserRoleModules[i].viewFlag == 1;
                if (field === 'editFlagName')         return _loginUserRoleModules[i].editFlag == 1;
                if (field === 'controlFlagName')      return _loginUserRoleModules[i].controlFlag == 1;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // 编辑性判断：该位置的 checkbox 是否可点击
    // ------------------------------------------------------------------
    function isModuleCheckboxEditable(record, field) {
        if (!editFlag) return false;
        // 当前登录用户自己的角色只读
        if (String(_selectedRoleId) === String(currentId)) return false;
        // 不可见当然不可编辑
        return isModuleCheckboxVisible(record, field);
    }

    // ------------------------------------------------------------------
    // 点击 checkbox：切换 + 联动
    // ------------------------------------------------------------------
    function onModuleCheckboxToggle(event, checkbox, field, mdId) {
        if (event) event.stopPropagation();

        var tree = mini.get('RightModuleTreeInfoGridPanel_Id');
        if (!tree) return;
        var node = tree.getNode(mdId);
        if (!node) return;

        var checked = checkbox.checked;
        node[field] = checked ? 1 : 0;

        // 联动规则
        if (field === 'viewFlagName' && !checked) {
            node.editFlagName    = 0;
            node.controlFlagName = 0;
        } else if (field === 'editFlagName' && checked) {
            node.viewFlagName = 1;
        } else if (field === 'controlFlagName' && checked) {
            node.viewFlagName = 1;
        }

        tree.updateNode(node);
    }

    // ------------------------------------------------------------------
    // 三列定义
    // ------------------------------------------------------------------
    function createModuleTreeColumns(tree) {
        function makeCheckboxColumn(field, headerText, width) {
            return {
                field: field,
                header: headerText,
                headerAlign: 'center',
                align: 'center',
                width: width,
                type: 'checkboxcolumn',
                trueValue: 1,
                falseValue: 0,
                cellStyle: function () {
                    return 'padding:0;text-align:center;';
                },
                renderer: function (e) {
                    // 不可见 → 空白
                    if (!isModuleCheckboxVisible(e.record, field)) {
                        return '';
                    }

                    var checked = isTrueVal(e.record[field]);
                    var editable = isModuleCheckboxEditable(e.record, field);

                    if (editable) {
                        // 可编辑：点击触发切换 + 联动
                        return '<input type="checkbox"'
                            + (checked ? ' checked' : '')
                            + ' onclick="onModuleCheckboxToggle(event, this, \''
                                + field + '\', \'' + e.record.mdId + '\');"'
                            + ' style="vertical-align:middle;width:13px;height:13px;'
                            + 'margin:0;padding:0;cursor:pointer;" />';
                    } else {
                        // 可见但不可编辑：显示灰色 disabled
                        return '<input type="checkbox"'
                            + (checked ? ' checked' : '')
                            + ' disabled'
                            + ' style="vertical-align:middle;width:13px;height:13px;'
                            + 'margin:0;padding:0;cursor:not-allowed;opacity:0.6;" />';
                    }
                }
            };
        }

        tree.setColumns([
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.moduleList,
                headerAlign: 'left',
                align: 'left',
                width: '55%'
            },
            makeCheckboxColumn('viewFlagName',    _loginUserLanguageResource.viewFlag,    '15%'),
            makeCheckboxColumn('editFlagName',    _loginUserLanguageResource.editFlag,    '15%'),
            makeCheckboxColumn('controlFlagName', _loginUserLanguageResource.controlFlag, '15%')
        ]);
    }

    // ------------------------------------------------------------------
    // 拉取某角色已有权限并回填
    // ------------------------------------------------------------------
    function applyModuleRightToTree(tree, roleId) {
        var divId = 'RightModuleTreeInfoGridPanel_Id';
        var mask = mini.mask({
            el: divId,
            cls: 'mini-mask-loading',
            html: _loginUserLanguageResource.loadingData
        });

        $.ajax({
            url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnModules',
            type: 'POST',
            data: { roleId: roleId },
            dataType: 'json',
            success: function (moduleIds) {
                mini.unmask(divId);

                // 1) mdId -> "1,0,1"
                var map = {};
                if (moduleIds && moduleIds.length > 0) {
                    for (var i = 0; i < moduleIds.length; i++) {
                        map[String(moduleIds[i].rmModuleid)] = moduleIds[i].rmMatrix || '';
                    }
                }

                // 2) 遍历赋值
                var changed = [];
                function applyNode(node) {
                    var key = String(node.mdId);
                    var matrix = map[key];
                    if (matrix) {
                        var arr = matrix.split(',');
                        if (arr.length === 3) {
                            node.viewFlagName    = parseInt(arr[0], 10) === 1 ? 1 : 0;
                            node.editFlagName    = parseInt(arr[1], 10) === 1 ? 1 : 0;
                            node.controlFlagName = parseInt(arr[2], 10) === 1 ? 1 : 0;
                        }
                    } else {
                        node.viewFlagName    = 0;
                        node.editFlagName    = 0;
                        node.controlFlagName = 0;
                    }
                    changed.push(node);

                    if (node.children && node.children.length > 0) {
                        for (var i = 0; i < node.children.length; i++) {
                            applyNode(node.children[i]);
                        }
                    }
                }

                var root = tree.getRootNode();
                if (root && root.children) {
                    for (var i = 0; i < root.children.length; i++) {
                        applyNode(root.children[i]);
                    }
                }

                // 3) 逐个刷新触发 renderer
                for (var k = 0; k < changed.length; k++) {
                    try { tree.updateNode(changed[k]); } catch (e) {}
                }
            },
            error: function () {
                mini.unmask(divId);
                mini.alert(_loginUserLanguageResource.ajaxError);
            }
        });
    }

    // ================================================================
    // 6. 设备类型权限树
    // ================================================================
    function loadRightTabTreeStructure() {
        var tree = mini.get('RightTabTreeInfoGridPanel_Id');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/roleManagerController/constructRightTabTreeGridTree');
        }
        tree.load();
    }

    function onRightTabTreeLoad(e) {
        var tree = e.sender;
        tree.expandAll();
        if (_selectedRoleId) applyTabRightToTree(tree, _selectedRoleId);
    }
    
    function onRightTabTreeBeforeNodeCheck(e) {
    	if (editFlag){
            if (String(_selectedRoleId) === String(currentId)) {
            	e.cancel = true;
            }
        }else{
       	 	e.cancel = true;
        }
    }

    function applyTabRightToTree(tree, roleId) {
        var divId = 'RightTabTreeInfoGridPanel_Id';
        var mask = mini.mask({
            el: divId,
            cls: 'mini-mask-loading',
            html: _loginUserLanguageResource.loadingData
        });
        $.ajax({
            url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnTabs',
            type: 'POST',
            data: { roleId: roleId },
            dataType: 'json',
            success: function (tabs) {
                mini.unmask(divId);
                var allLeaves = [];
                function collectLeaves(node) {
                    var children = node.children;
                    if (!children || children.length === 0) {
                        allLeaves.push(node);
                    } else {
                    	allLeaves.push(node);
                    	for (var i = 0; i < children.length; i++) {
                            collectLeaves(children[i]);
                        }
                    }
                }
                var root = tree.getRootNode();
                if (root && root.children) {
                    for (var i = 0; i < root.children.length; i++) {
                        collectLeaves(root.children[i]);
                    }
                }
                
                for (var j = 0; j < allLeaves.length; j++) {
                    tree.uncheckNode(allLeaves[j]);
                }

                var checkedIds = {};
                if (tabs) {
                    for (var k = 0; k < tabs.length; k++) {
                        checkedIds[String(tabs[k].rdDeviceTypeId)] = true;
                    }
                }
                for (var m = 0; m < allLeaves.length; m++) {
                    if (checkedIds[String(allLeaves[m].deviceTypeId)]) {
                        tree.checkNode(allLeaves[m]);
                    }
                }
            },
            error: function () {
                mini.unmask(divId);
                mini.alert(_loginUserLanguageResource.ajaxError);
            }
        });
    }

    // ================================================================
    // 7. 语言权限树
    // ================================================================
    function loadRightLanguageTreeStructure() {
        var tree = mini.get('RightLanguageTreeInfoGridPanel_Id');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/roleManagerController/constructRightLanguageTreeGridTree');
        }
        tree.load();
    }

    function onRightLanguageTreeLoad(e) {
        var tree = e.sender;
        tree.expandAll();
        if (_selectedRoleId) applyLanguageRightToTree(tree, _selectedRoleId);
    }
    
    function onRightLanguageTreeBeforeNodeCheck(e) {
    	if (editFlag){
            if (String(_selectedRoleId) === String(currentId)) {
            	e.cancel = true;
            }
        }else{
       	 	e.cancel = true;
        }
    }

    function applyLanguageRightToTree(tree, roleId) {
        var divId = 'RightLanguageTreeInfoGridPanel_Id';
        var mask = mini.mask({
            el: divId,
            cls: 'mini-mask-loading',
            html: _loginUserLanguageResource.loadingData
        });
        $.ajax({
            url: context + '/moduleShowRightManagerController/doShowRightCurrentRoleOwnLanguages',
            type: 'POST',
            data: { roleId: roleId },
            dataType: 'json',
            success: function (langs) {
                mini.unmask(divId);
                var allLeaves = [];
                function collectLeaves(node) {
                    var children = node.children;
                    if (!children || children.length === 0) {
                        allLeaves.push(node);
                    } else {
                        for (var i = 0; i < children.length; i++) {
                            collectLeaves(children[i]);
                        }
                    }
                }
                var root = tree.getRootNode();
                if (root && root.children) {
                    for (var i = 0; i < root.children.length; i++) {
                        collectLeaves(root.children[i]);
                    }
                }

                for (var j = 0; j < allLeaves.length; j++) {
                    tree.uncheckNode(allLeaves[j]);
                }

                var checkedIds = {};
                if (langs) {
                    for (var k = 0; k < langs.length; k++) {
                        checkedIds[String(langs[k].language)] = true;
                    }
                }
                for (var m = 0; m < allLeaves.length; m++) {
                    if (checkedIds[String(allLeaves[m].languageId)]) {
                        tree.checkNode(allLeaves[m]);
                    }
                }
            },
            error: function () {
                mini.unmask(divId);
                mini.alert(_loginUserLanguageResource.ajaxError);
            }
        });
    }

    // ================================================================
    // 8. isTrueVal
    // ================================================================
    function isTrueVal(v) {
        return v === true || v === 1 || v === '1' || v === 'true';
    }
    
 // ================================================================
 // 添加角色（打开独立窗口）
 // ================================================================
 function addroleInfo() {
     if (!editFlag) return;

     mini.open({
         title: _loginUserLanguageResource.addRole,
         url: context + '/miniui-app/modules/role/roleAddWindow.jsp',
         width: 1100,
         height: 700,
         modal: true,
         allowResize: true,
         maxable: true,
         onload: function () {
             var iframe = this.getIFrameEl();
             var contentWindow = iframe.contentWindow;

             contentWindow.setData({
                 currentUserRoleLevel:        currentLevel,
                 currentUserRoleShowLevel:    currentShowLevel,
                 currentUserRoleVideoKeyEdit: currentVideoKeyEdit,
                 loginUserLanguage:           loginUserLanguage,
                 loginUserRoleModules:        _loginUserRoleModules
             });

             // ★ 子窗口回调：刷新角色列表
             contentWindow._parentRefreshRoleList = function (newRoleName) {
                 _addRoleFlag = newRoleName || null;
                 loadRoleList();
             };
         }
     });
 }
 
//================================================================
//删除角色
//================================================================
function delroleInfo() {
  if (!editFlag) return;

  var grid = mini.get('RoleInfoGridPanel_Id');
  if (!grid) return;

  // 取当前选中行（multiSelect=false，只有一行）
  var selectedRows = grid.getSelecteds() || [];
  if (selectedRows.length === 0) {
      mini.alert(_loginUserLanguageResource.checkOne,_loginUserLanguageResource.tip);
      return;
  }

  // 只选了一行且是当前登录用户的角色 → 直接拒绝
  if (selectedRows.length === 1
      && String(selectedRows[0].roleId) === String(currentId)) {
      mini.alert(_loginUserLanguageResource.cannotDeleteLoginUserRole,_loginUserLanguageResource.tip);
      return;
  }

  // 过滤掉当前登录用户的角色
  var selectRoleId = [];
  for (var i = 0; i < selectedRows.length; i++) {
      if (String(selectedRows[i].roleId) !== String(currentId)) {
          selectRoleId.push(selectedRows[i].roleId);
      }
  }

  if (selectRoleId.length === 0) {
      mini.alert(_loginUserLanguageResource.cannotDeleteLoginUserRole,_loginUserLanguageResource.tip);
      return;
  }

  mini.confirm(_loginUserLanguageResource.confirmDelete,_loginUserLanguageResource.tip,
      function (action) {
          if (action !== 'ok') return;
          $.ajax({
              url: context + '/roleManagerController/doRoleBulkDelete',
              type: 'POST',
              data: { paramsId: selectRoleId.join(',') },
              dataType: 'json',
              success: function (result) {
                  if (result.flag === true) {
                      mini.alert(_loginUserLanguageResource.deleteSuccessfully,_loginUserLanguageResource.tip);
                  } else {
                      mini.alert('<font color=red>'+ _loginUserLanguageResource.deleteFailed+ '</font>', _loginUserLanguageResource.tip);
                  }
                  // 清掉选中记录，避免刷新后按已删除的角色ID恢复选中
                  _selectedRoleId = null;
                  loadRoleList();
              },
              error: function () {
                  mini.alert(_loginUserLanguageResource.requestFailed,_loginUserLanguageResource.tip);
              }
          });
      });
}
	
//================================================================
//保存角色列表修改
//================================================================
function updateRoleInfo() {
 if (!editFlag) return;

 var grid = mini.get('RoleInfoGridPanel_Id');
 if (!grid) return;

 // 先提交正在编辑的单元格
 grid.commitEdit();

 var modifiedRecords = grid.getChanges('modified', false);
 if (!modifiedRecords || modifiedRecords.length === 0) {
     mini.alert(_loginUserLanguageResource.noDataChange,_loginUserLanguageResource.tip);
     return;
 }

 // 组装提交数据
 var modifiedRole = [];
 for (var i = 0; i < modifiedRecords.length; i++) {
     var rec = modifiedRecords[i];
     var role = {};

     role.roleId        = rec.roleId;
     role.roleName_zh_CN = rec.roleName_zh_CN;
     role.roleName_en    = rec.roleName_en;
     role.roleName_ru    = rec.roleName_ru;
     role.remark_zh_CN   = rec.remark_zh_CN;
     role.remark_en      = rec.remark_en;
     role.remark_ru      = rec.remark_ru;

     role.roleLevel        = rec.roleLevel;
     role.showLevel        = rec.showLevel;
     role.roleVideoKeyEdit = isTrueVal(rec.roleVideoKeyEditName) ? 1 : 0;
     role.roleLanguageEdit = isTrueVal(rec.roleLanguageEditName) ? 1 : 0;

     modifiedRole.push(role);
 }

 $.ajax({
     url: context + '/roleManagerController/batchUpdateRoleInfo',
     type: 'POST',
     data: { data: JSON.stringify(modifiedRole) },
     dataType: 'json',
     success: function (result) {
         if (result.success === true && result.flag === true) {
             mini.alert(_loginUserLanguageResource.savedSuccessfully,_loginUserLanguageResource.tip);
             // 清空变更标记
             grid.accept();
             loadRoleList();
         } else if (result.success === true && result.flag === false) {
             mini.alert('<font color=red>'+ _loginUserLanguageResource.saveFailed+ '</font>', _loginUserLanguageResource.tip);
         } else {
             mini.alert('<font color=red>'+ _loginUserLanguageResource.saveFailed+ '</font>', _loginUserLanguageResource.tip);
         }
     },
     error: function () {
         mini.alert(_loginUserLanguageResource.requestFailed,_loginUserLanguageResource.tip);
     }
 });
}

//================================================================
//授权保存主入口
//================================================================
function grantRoleRight() {
 if (!editFlag) return;

 var grid = mini.get('RoleInfoGridPanel_Id');
 if (!grid) return;

 var row = grid.getSelected();
 if (!row) {
     mini.alert(_loginUserLanguageResource.pleaseChooseRole, _loginUserLanguageResource.tip);
     return;
 }

 var roleId    = row.roleId;
 var roleLevel = row.roleLevel;

 // 判断是否为超级管理员（roleLevel == 1）
 var isSuperAdmin = (parseInt(roleLevel) === 1);

 // ---------- 1) 收集模块权限 ----------
 var moduleTree = mini.get('RightModuleTreeInfoGridPanel_Id');
 var addModule = [];
 var moduleMatrixData = '';
 (function collectModule(node) {
     if (node && isTrueVal(node.viewFlagName)) {
         addModule.push(node.mdId);
         var matrix = (isTrueVal(node.viewFlagName) ? 1 : 0) + ','
                    + (isTrueVal(node.editFlagName) ? 1 : 0) + ','
                    + (isTrueVal(node.controlFlagName) ? 1 : 0);
         moduleMatrixData += node.mdId + ':' + matrix + '|';
     }
     if (node && node.children && node.children.length > 0) {
         for (var i = 0; i < node.children.length; i++) {
             collectModule(node.children[i]);
         }
     }
 })(moduleTree.getRootNode());
 if (moduleMatrixData.length > 0) {
     moduleMatrixData = moduleMatrixData.substring(0, moduleMatrixData.length - 1);
 }

 // ---------- 2) 收集设备类型权限 ----------
 var devTree = mini.get('RightTabTreeInfoGridPanel_Id');
 var addDeviceType = [];
 var devMatrixData = '';

 // 所有叶子节点
 var devLeaves = collectLeafNodes(devTree);

 if (isSuperAdmin) {
     // 超级管理员：全部授予
     for (var di = 0; di < devLeaves.length; di++) {
         addDeviceType.push(devLeaves[di].deviceTypeId);
         devMatrixData += devLeaves[di].deviceTypeId + ':0,0,0|';
     }
 } else {
     var devChecked = devTree.getCheckedNodes() || [];
     for (var dj = 0; dj < devChecked.length; dj++) {
         if (devChecked[dj].deviceTypeId !== undefined
             && devChecked[dj].deviceTypeId !== null) {
             addDeviceType.push(devChecked[dj].deviceTypeId);
             devMatrixData += devChecked[dj].deviceTypeId + ':0,0,0|';
         }
     }
 }
 if (devMatrixData.length > 0) {
     devMatrixData = devMatrixData.substring(0, devMatrixData.length - 1);
 }

 // ---------- 3) 收集语言权限 ----------
 var langTree = mini.get('RightLanguageTreeInfoGridPanel_Id');
 var addLanguage = [];
 var langMatrixData = '';

 var langLeaves = collectLeafNodes(langTree);

 if (isSuperAdmin) {
     for (var li = 0; li < langLeaves.length; li++) {
         addLanguage.push(langLeaves[li].languageId);
         langMatrixData += langLeaves[li].languageId + ':0,0,0|';
     }
 } else {
     var langChecked = langTree.getCheckedNodes() || [];
     for (var lj = 0; lj < langChecked.length; lj++) {
         if (langChecked[lj].languageId !== undefined
             && langChecked[lj].languageId !== null) {
             addLanguage.push(langChecked[lj].languageId);
             langMatrixData += langChecked[lj].languageId + ':0,0,0|';
         }
     }
 }
 if (langMatrixData.length > 0) {
     langMatrixData = langMatrixData.substring(0, langMatrixData.length - 1);
 }

 // ---------- 4) 校验 ----------
 if (addModule.length === 0
     || addDeviceType.length === 0
     || addLanguage.length === 0) {
     mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
     return;
 }

 // ---------- 5) 依次调用三个接口，全部完成后统一提示 ----------
 var total = 3;
 var done = 0;
 var allSuccess = true;

 function onDone(success) {
     if (!success) allSuccess = false;
     done++;
     if (done < total) return;

     if (allSuccess) {
         mini.alert(_loginUserLanguageResource.grantSuccess,
             _loginUserLanguageResource.tip);

         // 清缓存，让三棵树重新拉权限
         if (moduleTree) moduleTree._appliedRoleId = null;
         if (devTree)    devTree._appliedRoleId    = null;
         if (langTree)   langTree._appliedRoleId   = null;

         loadRoleList();
     } else {
         mini.alert('<font color=red>SORRY！'
             + _loginUserLanguageResource.grantFailure
             + '</font>', _loginUserLanguageResource.tip);
     }
 }

 var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.submittingData });

 grantRolePermission(roleId, addModule, moduleMatrixData, function (ok) {
     if (done + 1 === total) mini.unmask(document.body);
     onDone(ok);
 });
 grantRoleTabPermission(roleId, addDeviceType, devMatrixData, function (ok) {
     if (done + 1 === total) mini.unmask(document.body);
     onDone(ok);
 });
 grantRoleLanguagePermission(roleId, addLanguage, langMatrixData, function (ok) {
     if (done + 1 === total) mini.unmask(document.body);
     onDone(ok);
 });
}

//收集树的所有叶子节点（用于超级管理员授权）
function collectLeafNodes(tree) {
 var leaves = [];
 if (!tree) return leaves;

 function collect(node) {
     var children = node.children;
     if (!children || children.length === 0) {
         leaves.push(node);
     } else {
         for (var i = 0; i < children.length; i++) {
             collect(children[i]);
         }
     }
 }

 var root = tree.getRootNode();
 if (root && root.children) {
     for (var i = 0; i < root.children.length; i++) {
         collect(root.children[i]);
     }
 }
 return leaves;
}

//================================================================
//三个子接口
//================================================================
function grantRolePermission(roleId, addModule, matrixData, callback) {
 $.ajax({
     url: context + '/moduleShowRightManagerController/doModuleSaveOrUpdate',
     type: 'POST',
     data: {
         paramsId:     addModule.join(','),
         oldModuleIds: '',
         roleId:       roleId,
         matrixCodes:  matrixData
     },
     dataType: 'json',
     success: function (result) {
         callback(result && result.msg === true);
     },
     error: function () {
         callback(false);
     }
 });
}

function grantRoleTabPermission(roleId, addDeviceType, matrixData, callback) {
 $.ajax({
     url: context + '/moduleShowRightManagerController/doRoleDeviceTypeSaveOrUpdate',
     type: 'POST',
     data: {
         paramsId:     addDeviceType.join(','),
         oldModuleIds: '',
         roleId:       roleId,
         matrixCodes:  matrixData
     },
     dataType: 'json',
     success: function (result) {
         callback(result && result.msg === true);
     },
     error: function () {
         callback(false);
     }
 });
}

function grantRoleLanguagePermission(roleId, addLanguage, matrixData, callback) {
 $.ajax({
     url: context + '/moduleShowRightManagerController/doRoleLanguageSaveOrUpdate',
     type: 'POST',
     data: {
         paramsId:     addLanguage.join(','),
         roleId:       roleId,
         matrixCodes:  matrixData
     },
     dataType: 'json',
     success: function (result) {
         callback(result && result.msg === true);
     },
     error: function () {
         callback(false);
     }
 });
}

//================================================================
//导出角色完整数据
//================================================================
function exportRoleCompleteData() {
 if (!editFlag) return;

 var url = context + '/roleManagerController/exportRoleCompleteData';

 var timestamp = new Date().getTime();
 var key = 'exportRoleCompleteData' + '_' + timestamp;
 var maskPanelId = 'roleListPanel';   // ★ 用面板 id 做遮罩容器

 var param = '&recordCount=10000'
     + '&fileName=' + URLencode(URLencode(_loginUserLanguageResource.roleExportFileName))
     + '&key=' + key;

 exportDataMask(key, maskPanelId, _loginUserLanguageResource.loadingData);
 downloadFile(url + '?flag=true' + param);
}

//================================================================
//打开"导入角色"窗口
//对应 ExtJS ImportRoleWindow
//================================================================
function openImportRoleWindow() {
 if (!editFlag) return;

 mini.open({
     title: _loginUserLanguageResource.importRole,
     url: context + '/miniui-app/modules/role/importRoleWindow.jsp',
     width: '65%',
     minWidth: 600,
     height: '70%',
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;
         contentWindow.setData({
        	 loginUserLanguage: loginUserLanguage,
        	 loginUserLanguageList: loginUserLanguageList
         });
         // ★ 暴露给子窗口的刷新回调
         contentWindow.parent.refreshRoleListAfterImport = function () {
             // 清掉选中标记，让刷新后自动选中第一行
             _selectedRoleId = null;
             _addRoleFlag = null;
             loadRoleList();
         };
     }
 });
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
            _loginUserRoleModules = getLoginUserRoleModules();

            loadRightModuleTreeStructure();
            loadRightTabTreeStructure();
            loadRightLanguageTreeStructure();
            setTimeout(function () {
            	loadRoleList();
            }, 100);
        }, 50);

        console.log('角色管理模块加载完成');
    });
</script>
</body>
</html>