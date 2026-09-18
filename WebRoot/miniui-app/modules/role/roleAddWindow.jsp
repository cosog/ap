<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加角色</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }

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

        .mini-treegrid,
        .mini-tree { width:100%; height:100%; }

        .form-table { width:100%; border-collapse:collapse; }
        .form-table td { padding: 4px 6px; vertical-align: middle; }
        .form-table .label { text-align: right; width: 100px; font-weight: bold; white-space: nowrap; }
        .form-table .mini-textbox,
        .form-table .mini-password,
        .form-table .mini-combobox,
        .form-table .mini-spinner,
        .form-table .mini-textarea { width: 100% !important; min-width: 100px; }

        .footer {
            flex-shrink: 0; padding: 8px 10px; border-top: 1px solid #e8e8e8;
            background: #fafafa; text-align: right;
        }
        .footer .mini-button { margin-left: 6px; }
        .empty-msg {
            color: #999; font-size: 13px; text-align: center; padding: 20px;
        }
    </style>
</head>
<body>

<div class="main-container">
    <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">

        <!-- ========== 左：表单 ========== -->
        <div size="40%" showCollapseButton="false" minSize="380">
            <div class="mini-panel" style="width:100%;height:100%;"
                 showHeader="false" showToolbar="false" showCloseButton="false"
                 bodyStyle="padding:8px;background:#fff;">
                <form id="roleForm">
                    <table class="form-table">
                        <tr id="rowZhCN">
                            <td class="label"><span style="color:red;">*</span><span id="lblRoleNameZhCN"></span>：</td>
                            <td><input id="roleName_zh_CN" class="mini-textbox"
                                       onblur="checkRoleName('roleName_zh_CN')" /></td>
                        </tr>
                        <tr id="rowEn">
                            <td class="label"><span style="color:red;">*</span><span id="lblRoleNameEn"></span>：</td>
                            <td><input id="roleName_en" class="mini-textbox"
                                       onblur="checkRoleName('roleName_en')" /></td>
                        </tr>
                        <tr id="rowRu">
                            <td class="label"><span style="color:red;">*</span><span id="lblRoleNameRu"></span>：</td>
                            <td><input id="roleName_ru" class="mini-textbox"
                                       onblur="checkRoleName('roleName_ru')" /></td>
                        </tr>
                        <tr>
                            <td class="label"><span style="color:red;">*</span><span id="lblRoleLevel"></span>：</td>
                            <td><input id="roleLevel" class="mini-spinner" minValue="1" value="1" /></td>
                        </tr>
                        <tr>
                            <td class="label"><span style="color:red;">*</span><span id="lblShowLevel"></span>：</td>
                            <td><input id="showLevel" class="mini-spinner" minValue="1" value="1" /></td>
                        </tr>
                        <tr>
                            <td class="label"><span style="color:red;">*</span><span id="lblRoleVideoKeyEdit"></span>：</td>
                            <td><input id="roleVideoKeyEdit" class="mini-combobox"
                                       valueField="id" textField="text" allowInput="false" value="0" /></td>
                        </tr>
                        <tr id="rowRemarkZhCN">
                            <td class="label"><span id="lblRemarkZhCN"></span>：</td>
                            <td><input id="remark_zh_CN" class="mini-textarea" /></td>
                        </tr>
                        <tr id="rowRemarkEn">
                            <td class="label"><span id="lblRemarkEn"></span>：</td>
                            <td><input id="remark_en" class="mini-textarea" /></td>
                        </tr>
                        <tr id="rowRemarkRu">
                            <td class="label"><span id="lblRemarkRu"></span>：</td>
                            <td><input id="remark_ru" class="mini-textarea" /></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <!-- ========== 右：三棵树 ========== -->
        <div size="60%" showCollapseButton="false" minSize="500">
            <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">

                <!-- 模块权限 -->
                <div size="60%" showCollapseButton="false" minSize="260">
                    <div id="modulePermissionPanel" class="mini-panel"
                         style="width:100%;height:100%;"
                         showHeader="true" showToolbar="false" showCloseButton="false"
                         bodyStyle="padding:0;">
                        <div id="moduleTree" class="mini-treegrid" style="width:100%;height:100%;"
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
                             onload="onModuleTreeLoad">
                            <div property="columns"></div>
                            <div property="emptyText" class="empty-msg"></div>
                        </div>
                    </div>
                </div>

                <!-- 设备类型 + 语言 -->
                <div size="40%" showCollapseButton="false" minSize="200">
                    <div class="mini-splitter" style="width:100%;height:100%;" vertical="true">

                        <div size="50%" showCollapseButton="false" minSize="120">
                            <div id="deviceTypePermissionPanel" class="mini-panel"
                                 style="width:100%;height:100%;"
                                 showHeader="true" showToolbar="false" showCloseButton="false"
                                 bodyStyle="padding:0;">
                                <div id="deviceTypeTree" class="mini-tree" style="width:100%;height:100%;"
                                     showTreeIcon="true"
                                     showCheckBox="true"
                                     checkRecursive="false"
                                     expandOnNodeClick="false"
                                     idField="deviceTypeId"
                                     textField="text"
                                     parentField="pid"
                                     resultAsTree="true"
                                     autoLoad="false"
                                     onload="onDeviceTypeTreeLoad">
                                    <div property="emptyText" class="empty-msg"></div>
                                </div>
                            </div>
                        </div>

                        <div size="50%" showCollapseButton="true" collapseDirection="bottom" minSize="120">
                            <div id="languagePermissionPanel" class="mini-panel"
                                 style="width:100%;height:100%;"
                                 showHeader="true" showToolbar="false" showCloseButton="false"
                                 bodyStyle="padding:0;">
                                <div id="languageTree" class="mini-tree" style="width:100%;height:100%;"
                                     showTreeIcon="true"
                                     showCheckBox="true"
                                     checkRecursive="false"
                                     expandOnNodeClick="false"
                                     idField="languageId"
                                     textField="text"
                                     parentField="pid"
                                     resultAsTree="true"
                                     autoLoad="false"
                                     onload="onLanguageTreeLoad">
                                    <div property="emptyText" class="empty-msg"></div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>

    </div>

    <!-- 底部按钮 -->
    <div class="footer">
        <button id="btnSave"   class="mini-button" iconCls="save"   onclick="onSave()"></button>
        <button id="btnCancel" class="mini-button" iconCls="cancel" onclick="onCancel()"></button>
    </div>
</div>

<script>
    var context = '<%=context%>';

    // 由父窗口传入
    var currentUserRoleLevel       = 1;
    var currentUserRoleShowLevel   = 1;
    var currentUserRoleVideoKeyEdit= 0;
    var loginUserLanguage          = 'zh_CN';
    var _loginUserRoleModules      = [];   // 当前用户拥有的模块权限

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;

        document.title = R.addRole;

        document.getElementById('lblRoleNameZhCN').textContent = R.roleName;
        document.getElementById('lblRoleNameEn').textContent   = R.roleName;
        document.getElementById('lblRoleNameRu').textContent   = R.roleName;
        document.getElementById('lblRoleLevel').textContent    = R.roleLevel;
        document.getElementById('lblShowLevel').textContent    = R.dataShowLevel;
        document.getElementById('lblRoleVideoKeyEdit').textContent = R.roleVideoKeyEdit;
        document.getElementById('lblRemarkZhCN').textContent  = R.roleRemark;
        document.getElementById('lblRemarkEn').textContent    = R.roleRemark;
        document.getElementById('lblRemarkRu').textContent    = R.roleRemark;

        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(R.save);
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(R.cancel);

        // 面板标题
        var mp = mini.get('modulePermissionPanel');
        if (mp) mp.setTitle(R.moduleLicense);
        var dp = mini.get('deviceTypePermissionPanel');
        if (dp) dp.setTitle(R.deviceTypeLicense);
        var lp = mini.get('languagePermissionPanel');
        if (lp) lp.setTitle(R.languageLicense);

        // 是/否 下拉
        var vke = mini.get('roleVideoKeyEdit');
        if (vke) {
            vke.setData([
                { id: 0, text: R.no },
                { id: 1, text: R.yes }
            ]);
            vke.setValue(0);
        }

        // 空数据提示
        var moduleTree = mini.get('moduleTree');
        if (moduleTree) moduleTree.setEmptyText(R.emptyMsg);
        var devTree = mini.get('deviceTypeTree');
        if (devTree) devTree.setEmptyText(R.emptyMsg);
        var langTree = mini.get('languageTree');
        if (langTree) langTree.setEmptyText(R.emptyMsg);
    }

    // ================================================================
    // 由父窗口调用
    // ================================================================
    function setData(data) {
        if (!data) data = {};

        currentUserRoleLevel        = data.currentUserRoleLevel || 1;
        currentUserRoleShowLevel    = data.currentUserRoleShowLevel || 1;
        currentUserRoleVideoKeyEdit = data.currentUserRoleVideoKeyEdit || 0;
        loginUserLanguage           = data.loginUserLanguage || 'zh_CN';
        _loginUserRoleModules       = data.loginUserRoleModules || [];

        // 显示/隐藏语言相关行
        var langUpper = (loginUserLanguage || '').toUpperCase();
        document.getElementById('rowZhCN').style.display       = (langUpper === 'ZH_CN') ? '' : 'none';
        document.getElementById('rowEn').style.display         = (langUpper === 'EN') ? '' : 'none';
        document.getElementById('rowRu').style.display         = (langUpper === 'RU') ? '' : 'none';
        document.getElementById('rowRemarkZhCN').style.display = (langUpper === 'ZH_CN') ? '' : 'none';
        document.getElementById('rowRemarkEn').style.display   = (langUpper === 'EN') ? '' : 'none';
        document.getElementById('rowRemarkRu').style.display   = (langUpper === 'RU') ? '' : 'none';

        // 角色级别 / 显示级别 的下限
        var levelSpinner = mini.get('roleLevel');
        if (levelSpinner) {
            levelSpinner.setMinValue(currentUserRoleLevel + 1);
            levelSpinner.setValue(currentUserRoleLevel + 1);
        }
        var showSpinner = mini.get('showLevel');
        if (showSpinner) {
            showSpinner.setMinValue(currentUserRoleShowLevel);
            showSpinner.setValue(currentUserRoleShowLevel);
        }

        // 视频密匙权限：无权限时置为"否"并禁用
        if (currentUserRoleVideoKeyEdit == 0) {
            var vke = mini.get('roleVideoKeyEdit');
            if (vke) {
                vke.setValue(0);
                vke.setEnabled(false);
            }
        }

        // 加载三棵树结构
        loadModuleTreeStructure();
        loadDeviceTypeTreeStructure();
        loadLanguageTreeStructure();
    }

    // ================================================================
    // 角色名称判重（失焦触发）
    //   参照 ExtJS 的 judgeRoleExistsOrNot
    // ================================================================
    function checkRoleName(fieldId) {
        var input = mini.get(fieldId);
        if (!input) return;

        var value = (input.getValue() || '').trim();
        if (!value) return;   // 空值不校验，交给 form 的 required 处理

        $.ajax({
            url: context + '/roleManagerController/judgeRoleExistsOrNot',
            type: 'POST',
            data: { roleName: value },
            dataType: 'json',
            success: function (resp) {
                // 后端返回 msg == "1" 表示已存在
                if (resp && resp.msg == '1') {
                    var confirmMsg = '<font color="red">【'+ _loginUserLanguageResource.role + ':' + value+ '】</font>' + _loginUserLanguageResource.alreadyExist;
                    mini.confirm(confirmMsg, _loginUserLanguageResource.confirm, function (action) {
                        if (action == 'ok') {
                        	//input.setValue('');
                        	input.focus();
                        	input.selectText();
                        }
                    });
                }
            },
            error: function () {
                mini.alert(
                    _loginUserLanguageResource.dataQueryFailure,
                    _loginUserLanguageResource.tip
                );
            }
        });
    }

    // ================================================================
    // 模块权限树
    // ================================================================
    function loadModuleTreeStructure() {
        var tree = mini.get('moduleTree');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/moduleManagerController/constructRightModuleTreeGridTree');
        }
        tree.load();
    }

    function onModuleTreeLoad(e) {
        var tree = e.sender;
        if (!tree._columnsCreated) {
            createModuleTreeColumns(tree);
            tree._columnsCreated = true;
        }
        tree.expandAll();
    }

    // 判断用户对该模块是否有对应权限（可见性）
    function isModuleCheckboxVisible(record, field) {
        if (field === 'editFlagName') {
            var isLeaf = !record.children || record.children.length === 0;
            if (!isLeaf) return false;
        }
        if (field === 'controlFlagName') {
            if (String(record.mdCode).toUpperCase() !== 'DEVICEREALTIMEMONITORING') return false;
        }

        var mdCode = record.mdCode;
        for (var i = 0; i < _loginUserRoleModules.length; i++) {
            if (String(_loginUserRoleModules[i].mdCode) === String(mdCode)) {
                if (field === 'viewFlagName')    return _loginUserRoleModules[i].viewFlag == 1;
                if (field === 'editFlagName')    return _loginUserRoleModules[i].editFlag == 1;
                if (field === 'controlFlagName') return _loginUserRoleModules[i].controlFlag == 1;
            }
        }
        return false;
    }

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
                cellStyle: function () { return 'padding:0;text-align:center;'; },
                renderer: function (e) {
                    if (!isModuleCheckboxVisible(e.record, field)) {
                        return '';
                    }
                    var checked = isTrueVal(e.record[field]);
                    return '<input type="checkbox"'
                        + (checked ? ' checked' : '')
                        + ' onclick="onModuleCheckboxToggle(event, this, \''
                            + field + '\', \'' + e.record.mdId + '\');"'
                        + ' style="vertical-align:middle;width:13px;height:13px;'
                        + 'margin:0;padding:0;cursor:pointer;" />';
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

    function onModuleCheckboxToggle(event, checkbox, field, mdId) {
        if (event) event.stopPropagation();

        var tree = mini.get('moduleTree');
        if (!tree) return;
        var node = tree.getNode(mdId);
        if (!node) return;

        var checked = checkbox.checked;
        node[field] = checked ? 1 : 0;

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

    // ================================================================
    // 设备类型权限树
    // ================================================================
    function loadDeviceTypeTreeStructure() {
        var tree = mini.get('deviceTypeTree');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/roleManagerController/constructRightTabTreeGridTree');
        }
        tree.load();
    }

    function onDeviceTypeTreeLoad(e) {
        var tree = e.sender;
        tree.expandAll();
    }

    // ================================================================
    // 语言权限树
    // ================================================================
    function loadLanguageTreeStructure() {
        var tree = mini.get('languageTree');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/roleManagerController/constructRightLanguageTreeGridTree');
        }
        tree.load();
    }

    function onLanguageTreeLoad(e) {
        var tree = e.sender;
        tree.expandAll();
    }

    // ================================================================
    // 保存
    // ================================================================
    function onSave() {
        var R = _loginUserLanguageResource;

        // 表单校验
        var form = new mini.Form('#roleForm');
        form.validate();
        if (!form.isValid()) {
            mini.alert(R.required, R.tip);
            return;
        }

        // 按当前语言校验角色名
        var langUpper = (loginUserLanguage || '').toUpperCase();
        var roleName = '';
        if (langUpper === 'ZH_CN') {
            roleName = (mini.get('roleName_zh_CN').getValue() || '').trim();
        } else if (langUpper === 'EN') {
            roleName = (mini.get('roleName_en').getValue() || '').trim();
        } else if (langUpper === 'RU') {
            roleName = (mini.get('roleName_ru').getValue() || '').trim();
        }
        if (!roleName) {
            mini.alert(R.roleName + ' ' + R.required, R.tip);
            return;
        }

        // ---------- 收集模块权限 ----------
        var moduleTree = mini.get('moduleTree');
        var addModule = [];
        var matrixData = '';
        (function collect(node) {
            if (node.viewFlagName == 1) {
                addModule.push(node.mdId);
                var matrix = (node.viewFlagName == 1 ? 1 : 0) + ','
                           + (node.editFlagName == 1 ? 1 : 0) + ','
                           + (node.controlFlagName == 1 ? 1 : 0);
                matrixData += node.mdId + ':' + matrix + '|';
            }
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                }
            }
        })(moduleTree.getRootNode());

        if (matrixData.length > 0) {
            matrixData = matrixData.substring(0, matrixData.length - 1);
        }

        if (addModule.length === 0) {
            mini.alert(R.checkOne, R.tip);
            return;
        }

        // ---------- 收集设备类型权限 ----------
        var devTree = mini.get('deviceTypeTree');
        var devChecked = devTree.getCheckedNodes() || [];
        var addDeviceType = [];
        for (var i = 0; i < devChecked.length; i++) {
            if (devChecked[i].deviceTypeId !== undefined
                && devChecked[i].deviceTypeId !== null) {
                addDeviceType.push(devChecked[i].deviceTypeId);
            }
        }
        if (addDeviceType.length === 0) {
            mini.alert(R.checkOne, R.tip);
            return;
        }

        // ---------- 收集语言权限 ----------
        var langTree = mini.get('languageTree');
        var langChecked = langTree.getCheckedNodes() || [];
        var addLanguage = [];
        for (var j = 0; j < langChecked.length; j++) {
            if (langChecked[j].languageId !== undefined
                && langChecked[j].languageId !== null) {
                addLanguage.push(langChecked[j].languageId);
            }
        }
        if (addLanguage.length === 0) {
            mini.alert(R.checkOne, R.tip);
            return;
        }

        // ---------- 提交 ----------
        var mask = mini.mask({ el: document.body, html: R.submittingData });

        $.ajax({
            url: context + '/roleManagerController/doRoleAdd',
            type: 'POST',
            data: {
                'role.roleName_zh_CN': mini.get('roleName_zh_CN').getValue() || '',
                'role.roleName_en':    mini.get('roleName_en').getValue()    || '',
                'role.roleName_ru':    mini.get('roleName_ru').getValue()    || '',
                'role.roleLevel':      mini.get('roleLevel').getValue()      || 1,
                'role.showLevel':      mini.get('showLevel').getValue()      || 1,
                'role.roleVideoKeyEdit': mini.get('roleVideoKeyEdit').getValue() || 0,
                'role.remark_zh_CN':   mini.get('remark_zh_CN').getValue()   || '',
                'role.remark_en':      mini.get('remark_en').getValue()      || '',
                'role.remark_ru':      mini.get('remark_ru').getValue()      || '',
                'role.roleLanguageEdit': 0,

                addModuleIds:     addModule.join(','),
                matrixCodes:      matrixData,
                addDeviceTypeIds: addDeviceType.join(','),
                addLanguageIds:   addLanguage.join(',')
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(document.body);
                if (result && result.msg === true) {
                    // ★ 按当前语言取新角色的名字，回传给父窗口
                    var newRoleName = '';
                    var langUpper = (loginUserLanguage || '').toUpperCase();
                    if (langUpper === 'ZH_CN') {
                        newRoleName = mini.get('roleName_zh_CN').getValue() || '';
                    } else if (langUpper === 'EN') {
                        newRoleName = mini.get('roleName_en').getValue() || '';
                    } else if (langUpper === 'RU') {
                        newRoleName = mini.get('roleName_ru').getValue() || '';
                    }

                    if (window._parentRefreshRoleList) {
                        window._parentRefreshRoleList(newRoleName);
                    }
                    mini.alert(R.addedSuccessfully, R.tip, function () {
                        window.CloseOwnerWindow('ok');
                    });
                } else {
                    mini.alert('<font color="red">' + R.addFailure + '</font>', R.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert(R.exceptionThrow + ': ' + R.contactAdmin, R.tip);
            }
        });
    }

    function onCancel() {
        window.CloseOwnerWindow('cancel');
    }

    function isTrueVal(v) {
        return v === true || v === 1 || v === '1' || v === 'true';
    }

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>