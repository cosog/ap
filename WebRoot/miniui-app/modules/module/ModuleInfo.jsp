<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*,com.cosog.model.User,com.cosog.utils.ConfigFile,com.google.gson.Gson" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
User userLogin = (User)session.getAttribute("userLogin");
String userLoginNo = userLogin != null ? userLogin.getUserNo() + "" : "";
String loginUserLanguage = userLogin != null ? userLogin.getLanguageName() + "" : "zh_CN";

String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>模块配置</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .module-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
        }
        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; }
        .mini-panel-body { border: 0 !important; padding: 0 !important; }
        .mini-panel-toolbar {
            background: #fafafa !important;
            border-bottom: 1px solid #e8e8e8 !important;
            padding: 4px 8px !important;
            box-sizing: border-box !important;
        }
        .mini-panel-toolbar > div { background: transparent !important; border: 0 !important; }

        .empty-msg {
            color: #999; font-size: 13px;
            text-align: center; padding: 20px;
        }
    </style>
</head>
<body>

<div class="module-container">
    <div id="modulePanel" class="mini-panel"
         style="width:100%;height:100%;"
         showHeader="false" showToolbar="true" showCloseButton="false"
         bodyStyle="padding:0;">

        <!-- ============ 工具条 ============ -->
        <div property="toolbar">
            <table style="width:100%;border-collapse:collapse;">
                <tr>
                    <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                        <button id="moduleCollapseBtn" class="mini-button" iconCls="icon-collapse-all" plain="true"
                                onclick="onModuleCollapseAll()"></button>
                        <span class="mini-toolbar-sep"></span>
                        <button id="moduleExpandBtn" class="mini-button" iconCls="icon-expand-all" plain="true"
                                onclick="onModuleExpandAll()"></button>
                        <span class="mini-toolbar-sep"></span>
                        <button id="moduleRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true"
                                onclick="refreshModuleTree()"></button>
                        <span class="mini-toolbar-sep"></span>
                        <span id="moduleNameLabel" style="font-size:12px;color:#333;"></span>
                        <input id="ModuleName_Id" class="mini-textbox" style="width:160px;"
                               onenter="loadModuleTree()" />
                        <button id="moduleSearchBtn" class="mini-button" iconCls="search" plain="true"
                                onclick="loadModuleTree()"></button>
                    </td>
                    <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                        <button id="addmoduleLableClassBtn_Id" class="mini-button" iconCls="add" plain="true"
                                visible="false" onclick="addmoduleInfo()"></button>
                        <button id="editmoduleLableClassBtn_Id" class="mini-button" iconCls="edit" plain="true"
                                visible="false" onclick="modifymoduleInfo()"></button>
                        <button id="moduleSaveBtn" class="mini-button" iconCls="save" plain="true"
                                onclick="batchUpdateModuleInfo()"></button>
                        <button id="delmoduleLableClassBtn_Id" class="mini-button" iconCls="delete" plain="true"
                                visible="false" onclick="delmoduleInfo()"></button>
                        <button id="moduleExportBtn" class="mini-button" iconCls="export" plain="true"
                                onclick="exportModuleCompleteData()"></button>
                        <button id="moduleImportBtn" class="mini-button" iconCls="import" plain="true"
                                onclick="openImportModuleWindow()"></button>
                    </td>
                </tr>
            </table>
        </div>

        <!-- ============ 主体：模块树表格 ============ -->
        <div id="ModuleInfoTreeGridView_Id" class="mini-treegrid"
             style="width:100%;height:100%;"
             showTreeIcon="true"
             treeColumn="taskname"
             idField="mdId"
             textField="text"
             parentField="mdParentid"
             dataField="children"
             resultAsTree="true"
             autoLoad="false"
             allowResize="false"
             allowAlternating="false"
             showHGridLines="false"
             showVGridLines="false"
             showPager="false"
             allowCellEdit="false"
             allowCellSelect="false"
             showEmptyText="true"
             showLoading="true"
             showCellTip="true"
             cellEditAction="celldblclick"
             expandOnDblClick="false"
             expandOnNodeClick="false"
             onbeforeload="onModuleTreeBeforeLoad"
             onload="onModuleTreeLoad"
             oncellbeginedit="onModuleTreeCellBeginEdit">
            <div property="columns"></div>
            <div property="emptyText" class="empty-msg"></div>
        </div>
    </div>
</div>

<script>
    var context = '<%=path%>';
    var user_ = '<%=userLoginNo%>';
    var loginUserLanguage = '<%=loginUserLanguage%>';
    var isInitializing = true;

    // ================================================================
    // 模块权限
    // ================================================================
    var loginUserModuleManagementModuleRight = getRoleModuleRight(
        context + '/roleManagerController/getRoleModuleRight',
        'ModuleManagement'
    );
    var viewFlag = false;
    var editFlag = false;
    var controlFlag = false;
    if (typeof loginUserModuleManagementModuleRight !== 'undefined') {
        viewFlag    = (loginUserModuleManagementModuleRight.viewFlag    == 1);
        editFlag    = (loginUserModuleManagementModuleRight.editFlag    == 1);
        controlFlag = (loginUserModuleManagementModuleRight.controlFlag == 1);
    }

    // ================================================================
    // 加载模块树
    // ================================================================
    function loadModuleTree() {
        var tree = mini.get('ModuleInfoTreeGridView_Id');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/moduleManagerController/constructModuleTreeGridTree');
        }
        tree.load();
    }
    
    function refreshModuleTree() {
    	var input = mini.get('ModuleName_Id');
    	if (input) input.setValue('');
        var tree = mini.get('ModuleInfoTreeGridView_Id');
        if (!tree) return;
        if (!tree.getUrl()) {
            tree.setUrl(context + '/moduleManagerController/constructModuleTreeGridTree');
        }
        tree.load();
    }

    // ================================================================
    // 加载前：附加查询参数
    // ================================================================
    function onModuleTreeBeforeLoad(e) {
        var params = e.params || {};
        var input = mini.get('ModuleName_Id');
        params.moduleName = input ? (input.getValue() || '') : '';
        e.params = params;
    }

    // ================================================================
    // 加载完成：动态创建列（只创建一次）
    // ================================================================
    function onModuleTreeLoad(e) {
        var tree = e.sender;
        var result = e.result || {};

        if (!tree._columnsCreated) {
            createModuleTreeColumns(tree, result);
            tree._columnsCreated = true;
        }

        // 展开全部
        tree.expandAll();
    }

    // ================================================================
    // 动态创建模块树列
    //   对应 ExtJS ModuleInfoStore 的 columns
    // ================================================================
    function createModuleTreeColumns(tree, result) {
        var showChineseName = (result.showChineseName === undefined) ? true : !!result.showChineseName;
        var showEnglishName = (result.showEnglishName === undefined) ? true : !!result.showEnglishName;
        var showRussianName = (result.showRussianName === undefined) ? true : !!result.showRussianName;

        // 编辑器工厂
        function textEditor(allowBlank) {
            return editFlag ? { type: 'textbox', allowBlank: !!allowBlank } : null;
        }
        function seqEditor() {
            return editFlag
                ? { type: 'spinner', minValue: 1, maxValue: 9999999999 }
                : null;
        }
        function typeEditor() {
            if (!editFlag) return null;
            var list = (result.moduleTypeList || []).slice();
            
            var typeComboxSource = [];
            for (var j = 0; j < list.length; j++) {
            	typeComboxSource.push({
            		boxkey: list[j][0],
            		boxval: list[j][1]
                });
            }
            
            console.log(JSON.stringify(typeComboxSource));
            
            return {
                type: 'combobox',
                data: typeComboxSource,
                valueField: 'boxkey',
                textField: 'boxval',
                allowInput: false,
                allowBlank: false
            };
        }

        var columns = [
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.moduleName,
                headerAlign: 'left',
                align: 'left',
                width: '30%',
                editor: null
            },
            {
                field: 'mdName_zh_CN',
                header: _loginUserLanguageResource.language_zh_CN,
                headerAlign: 'left',
                align: 'left',
                width: '18%',
                visible: showChineseName,
                editor: textEditor(true)
            },
            {
                field: 'mdName_en',
                header: _loginUserLanguageResource.language_en,
                headerAlign: 'left',
                align: 'left',
                width: '18%',
                visible: showEnglishName,
                editor: textEditor(true)
            },
            {
                field: 'mdName_ru',
                header: _loginUserLanguageResource.language_ru,
                headerAlign: 'left',
                align: 'left',
                width: '18%',
                visible: showRussianName,
                editor: textEditor(true)
            },
            {
                field: 'mdShowname',
                header: _loginUserLanguageResource.moduleIntroduction,
                headerAlign: 'left',
                align: 'left',
                width: '15%',
                visible: false
            },
            {
                field: 'mdIcon',
                header: _loginUserLanguageResource.moduleIcon,
                headerAlign: 'left',
                align: 'left',
                width: 100,
                editor: textEditor(true)
            },
            {
                field: 'mdTypeName',
                header: _loginUserLanguageResource.moduleType,
                headerAlign: 'left',
                align: 'left',
                width: 100,
                editor: typeEditor()
            },
            {
                field: 'mdSeq',
                header: _loginUserLanguageResource.moduleSort,
                headerAlign: 'left',
                align: 'left',
                width: 80,
                editor: seqEditor()
            },
            { field: 'mdId', visible: false },
            { field: 'mdParentid', visible: false },
            { field: 'mdType', visible: false }
        ];

        tree.setColumns(columns);
        tree.setAllowCellEdit(editFlag);
        tree.setAllowCellSelect(editFlag);
    }

    // ================================================================
    // 单元格开始编辑前
    // ================================================================
    function onModuleTreeCellBeginEdit(e) {
        if (!editFlag) {
            e.cancel = true;
        }
    }

    // ================================================================
    // 折叠全部 / 展开全部
    // ================================================================
    function onModuleCollapseAll() {
        var tree = mini.get('ModuleInfoTreeGridView_Id');
        if (tree) tree.collapseAll();
    }

    function onModuleExpandAll() {
        var tree = mini.get('ModuleInfoTreeGridView_Id');
        if (tree) tree.expandAll();
    }

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var nameLabel = document.getElementById('moduleNameLabel');
        if (nameLabel) nameLabel.textContent = _loginUserLanguageResource.moduleName + '：';

        var btnMap = {
            'moduleCollapseBtn':           'collapse',
            'moduleExpandBtn':             'expand',
            'moduleRefreshBtn':            'refresh',
            'moduleSearchBtn':             'search',
            'addmoduleLableClassBtn_Id':   'add',
            'editmoduleLableClassBtn_Id':  'update',
            'moduleSaveBtn':               'save',
            'delmoduleLableClassBtn_Id':   'deleteData',
            'moduleExportBtn':             'exportData',
            'moduleImportBtn':             'importData'
        };
        for (var id in btnMap) {
            var btn = mini.get(id);
            if (btn) btn.setText(_loginUserLanguageResource[btnMap[id]]);
        }

        var tree = mini.get('ModuleInfoTreeGridView_Id');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);
    }

    // ================================================================
    // 按钮权限
    // ================================================================
    function updateBtnStatus() {
        var btnIds = [
            'addmoduleLableClassBtn_Id',
            'editmoduleLableClassBtn_Id',
            'moduleSaveBtn',
            'delmoduleLableClassBtn_Id',
            'moduleExportBtn',
            'moduleImportBtn'
        ];
        for (var i = 0; i < btnIds.length; i++) {
            var btn = mini.get(btnIds[i]);
            if (btn) btn.setEnabled(editFlag);
        }
    }

    // ================================================================
    // 事件占位（后续逐步实现）
    // ================================================================
    function addmoduleInfo() {
        console.log('[模块配置] 添加');
    }
    function modifymoduleInfo() {
        console.log('[模块配置] 修改');
    }
    function delmoduleInfo() {
        console.log('[模块配置] 删除');
    }
 // ================================================================
 // 批量保存模块修改
 // ================================================================
 function batchUpdateModuleInfo() {
     if (!editFlag) return;

     var tree = mini.get('ModuleInfoTreeGridView_Id');
     if (!tree) return;

     // ★ 先提交正在编辑的单元格
     tree.commitEdit();

     // ★ 获取所有被修改过的行
     var modifiedRecords = tree.getChanges('modified', false);
     if (!modifiedRecords || modifiedRecords.length === 0) {
         mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
         return;
     }

     // ---- 组装提交数据（字段完全对照 ExtJS） ----
     var modifiedModules = [];
     for (var i = 0; i < modifiedRecords.length; i++) {
         var rec = modifiedRecords[i];
         var m = {
             mdId:            rec.mdId,
             mdParentid:      rec.mdParentid,
             mdName_zh_CN:    rec.mdName_zh_CN,
             mdName_en:       rec.mdName_en,
             mdName_ru:       rec.mdName_ru,
             mdShowname_zh_CN: rec.mdShowname_zh_CN,
             mdShowname_en:    rec.mdShowname_en,
             mdShowname_ru:    rec.mdShowname_ru,
             mdUrl:           rec.mdUrl,
             mdControl:       rec.mdControl,
             mdCode:          rec.mdCode,
             mdSeq:           rec.mdSeq,
             mdIcon:          rec.mdIcon,
             mdTypeName:      rec.mdTypeName
         };
         modifiedModules.push(m);
     }

     var mask = mini.mask({
         el: document.body,
         html: _loginUserLanguageResource.submittingData
     });

     $.ajax({
         url: context + '/moduleManagerController/batchUpdateModuleInfo',
         type: 'POST',
         data: { data: JSON.stringify(modifiedModules) },
         dataType: 'json',
         success: function (result) {
             mini.unmask(document.body);

             if (result.success === true && result.flag === true) {
                 mini.alert(_loginUserLanguageResource.savedSuccessfully,_loginUserLanguageResource.tip);

                 // ★ 保存成功后清空变更标记
                 tree.accept();

                 // ★ 刷新本窗口树
                 loadModuleTree();

                 // ★ 通知主界面刷新模块导航树
                 refreshMainMenuTree();

             } else if (result.success === true && result.flag === false) {
                 mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>',
                     _loginUserLanguageResource.tip);
             } else {
                 mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>',
                     _loginUserLanguageResource.tip);
             }
         },
         error: function () {
             mini.unmask(document.body);
             mini.alert(_loginUserLanguageResource.requestFailed,
                 _loginUserLanguageResource.tip);
         }
     });
 }
//================================================================
//导出模块完整数据
//================================================================
function exportModuleCompleteData() {
  if (!editFlag) return;

  var url = context + '/moduleManagerController/exportModuleCompleteData';

  var timestamp = new Date().getTime();
  var key = 'exportModuleCompleteData' + '_' + timestamp;
  var maskPanelId = 'modulePanel';

  var param = '&recordCount=10000'
      + '&fileName=' + URLencode(URLencode(_loginUserLanguageResource.moduleExportFileName))
      + '&key=' + key;

  exportDataMask(key, maskPanelId, _loginUserLanguageResource.loadingData);
  downloadFile(url + '?flag=true' + param);
}
    
//================================================================
//打开"导入模块"窗口
//================================================================
function openImportModuleWindow() {
 if (!editFlag) return;

 mini.open({
     title: _loginUserLanguageResource.importModule,
     url: context + '/miniui-app/modules/module/importModuleWindow.jsp',
     width: 700,
     height: 620,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;

         // ★ 暴露给子窗口的刷新回调：导入后刷新本窗口模块树 + 通知主界面
         contentWindow.parent.refreshModuleTreeAfterImport = function () {
             loadModuleTree();
             refreshMainMenuTree();
         };
     }
 });
}
    
 // ================================================================
 // 通知主界面刷新模块导航树
 // ================================================================
 function refreshMainMenuTree() {
     if (window.parent && window.parent !== window) {
         window.parent.postMessage({
             action: 'refreshMainMenuTree'
         }, window.location.origin);
     }
 }

 function initModuleManagerMessageListener() {
	 window.addEventListener('message', function (event) {
         var message = event.data;
         if (!message || !message.action) return;
         switch (message.action) {
             case 'refresh':
             	refreshModuleTree();
                 break;
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
            loadModuleTree();
        }, 10);

        // 监听主界面消息
        //initModuleManagerMessageListener();

        console.log('模块配置模块加载完成');
    });
</script>
</body>
</html>