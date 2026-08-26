<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*,com.cosog.model.User,com.cosog.utils.ConfigFile,com.google.gson.Gson" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
Gson gson = new Gson();

// ===== 1. 定义核心变量 =====
String path = request.getContextPath();
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
String browserLang = (String)session.getAttribute("browserLang");
if(browserLang == null) browserLang = "zh_CN";

// ===== 2. 其他 Java 代码 =====
String viewProjectName = (String)session.getAttribute("viewProjectName");
String favicon = (String)session.getAttribute("favicon");
if(favicon != null) favicon = favicon.substring(favicon.indexOf("/"), favicon.length());
String bannerCSS = (String)session.getAttribute("bannerCSS");
if(bannerCSS != null) bannerCSS = bannerCSS.substring(bannerCSS.indexOf("/"), bannerCSS.length());
boolean showLogo = (boolean)session.getAttribute("showLogo");
String oemStaticResourceTimestamp = (String)session.getAttribute("oemStaticResourceTimestamp");
String helpDocumentUrl = (String)session.getAttribute("helpDocumentUrl");

User userLogin = (User)session.getAttribute("userLogin");
String userLoginNo = userLogin != null ? userLogin.getUserNo() + "" : "";
String userLoginId = userLogin != null ? userLogin.getUserId() : "";
String userLoginName = userLogin != null ? userLogin.getUserName() : "";
String userLoginType = userLogin != null ? userLogin.getUserType() + "" : "";
String userLoginOrgId = userLogin != null ? userLogin.getUserOrgid() + "" : "";
String userLoginOrgIds = userLogin != null ? userLogin.getUserOrgIds() : "";
String userLoginOrgNames = userLogin != null ? userLogin.getUserOrgNames() : "";
String userLoginParentOrgids = userLogin != null ? userLogin.getUserParentOrgids() : "";
String userLoginSyncOrAsync = userLogin != null ? userLogin.getSyncOrAsync() : "";
String userLoginPicUrl = userLogin != null ? userLogin.getPicUrl() : "";

int loginUserRoleLevel = userLogin != null ? userLogin.getRoleLevel() : 0;
int loginUserRoleShowLevel = userLogin != null ? userLogin.getRoleShowLevel() : 0;
int loginUserRoleVideoKeyEdit = userLogin != null ? userLogin.getRoleVideoKeyEdit() : 0;
int loginUserLanguageKeyEdit = userLogin != null ? userLogin.getRoleLanguageEdit() : 0;
String loginUserLanguage = userLogin != null ? userLogin.getLanguageName() + "" : "zh_CN";
int loginUserLanguageValue = userLogin != null ? userLogin.getLanguage() : 0;

String loginUserLanguageListJson = gson.toJson(userLogin != null && userLogin.getLanguageList() != null ? userLogin.getLanguageList() : new ArrayList<>());
String loginUserLanguageResource = userLogin != null ? userLogin.getLanguageResource() : "{}";
String loginUserLanguageResourceFirstLower = userLogin != null ? userLogin.getLanguageResourceFirstLower() : "{}";

String configFileJson = (String)session.getAttribute("configFile");
String tabInfoJson = (String)session.getAttribute("tabInfo");

String loadingUI = (String)session.getAttribute("loadingUI");
if(loadingUI == null) loadingUI = "Loading UI…";

request.setAttribute("browserLang", browserLang);
%>
<html>
<head>
    <meta charset="UTF-8">
    <title><%=viewProjectName%></title>
    <% if(showLogo && favicon != null) { %>
    <link rel="icon" href="<%=path + favicon%>?timestamp=<%=oemStaticResourceTimestamp%>" type="image/x-icon" />
    <% } %>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit">

    <!-- ★★★ 第一步：提前定义所有全局变量（供 miniui-commutils.js 等使用） ★★★ -->
    <script>
        // ================================================================
        // 全局变量（必须在使用它们之前定义）
        // ================================================================
        var context = '<%=path%>';
        var user_ = '<%=userLoginNo%>';
        
        if (user_ == null || user_ == "") {
            window.location.href = context + "/login";
        }
        
        
        var user_Type = '<%=userLoginType%>';
        var userOrg_Ids = '<%=userLoginOrgIds%>';
        var userOrg_Names = "<%=userLoginOrgNames%>";
        var loginUserRoleLevel = <%=loginUserRoleLevel%>;
        var loginUserLanguage = '<%=loginUserLanguage%>';
        var loginUserLanguageValue = <%=loginUserLanguageValue%>;
        var loginUserLanguageList = JSON.parse('<%=loginUserLanguageListJson%>');
        var loginUserLanguageResource = JSON.parse('<%= loginUserLanguageResource.replace("'", "\\'") %>');
        var loginUserLanguageResourceFirstLower = JSON.parse('<%= loginUserLanguageResourceFirstLower.replace("'", "\\'") %>');
        var configFile = JSON.parse('<%= configFileJson.replace("'", "\\'") %>');
        var oem = configFile.ap.oem;
        
        
        var tabInfo = JSON.parse('<%= tabInfoJson.replace("'", "\\'") %>');
        var defaultPageSize = configFile.ap.others.pageSize || 50;
        var productionUnit = configFile.ap.others.productionUnit;
        var showHelp = configFile.ap.others.showHelp !== false;
        
        
        var helpDocumentUrl='<%=helpDocumentUrl%>';
        var helpDocumentTimestamp=oem.helpDocumentTimestamp;
        helpDocumentUrl = context + helpDocumentUrl.substring(helpDocumentUrl.indexOf("/"), helpDocumentUrl.length);
        
        var defaultComboxSize = configFile.ap.others.defaultComboxSize;
        var defaultGraghSize = configFile.ap.others.defaultGraghSize;
        
        // 新增：第一个叶子节点的ID（在 onMenuTreeLoad 中动态获取）
        var FIRST_LEAF_MODULE_ID = null;
    </script>

    <!-- ===== 第二步：动态引入 MiniUI 资源（包含 miniui-commutils.js） ===== -->
    <jsp:include page="./tags-miniui.jsp" flush="true" />

    <style>
        /* ... 样式保持不变 ... */
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", "Helvetica Neue", Arial, sans-serif;
        }
        .app-header {
            background: linear-gradient(135deg, #1a3a5c 0%, #2d6a9f 100%);
            height: 60px;
            color: #fff;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
            z-index: 100;
        }
        .app-header .logo {
            font-size: 20px;
            font-weight: bold;
            display: flex;
            align-items: center;
        }
        .app-header .logo img {
            height: 32px;
            margin-right: 10px;
        }
        .app-header .top-nav {
            display: flex;
            align-items: center;
            flex-wrap: wrap;
        }
        .app-header .top-nav a {
            color: rgba(255, 255, 255, 0.85);
            margin: 0 8px;
            text-decoration: none;
            font-size: 13px;
            cursor: pointer;
        }
        .app-header .top-nav a:hover {
            color: #ffd700;
        }
        .app-header .top-nav .divider {
            color: rgba(255, 255, 255, 0.2);
            margin: 0 4px;
        }
        .app-header .top-nav .active-lang {
            color: #ffd700;
            font-weight: bold;
        }
        .mini-tree {
            background: transparent;
        }
        .mini-tree .mini-tree-node-hover {
            background: #e6f7ff;
        }
        .mini-tree .mini-tree-node-selected {
            background: #bae7ff;
        }
        .mini-tree .mini-tree-node {
            padding: 2px 0;
        }
        #loading_div_id {
            position: absolute;
            left: 40%;
            top: 40%;
            padding: 10px 20px;
            background: rgba(255, 255, 255, 0.9);
            border-radius: 8px;
            z-index: 20001;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
        }
        #loading_div_id .loading-indicator {
            display: flex;
            align-items: center;
            font-size: 18px;
            color: #333;
        }
        #loading_div_id .loading-indicator img {
            margin-right: 12px;
        }
        .panel-title {
            padding: 6px 12px 4px 12px;
            font-weight: bold;
            color: #666;
            font-size: 13px;
            flex-shrink: 0;
            background: #f5f7fa;
        }
        .panel-title-border {
            border-bottom: 1px solid #e8e8e8;
        }
        .panel-title-top {
            border-top: 1px solid #e8e8e8;
            border-bottom: 1px solid #e8e8e8;
        }
        .panel-title .count {
            float: right;
            font-weight: normal;
            font-size: 12px;
            color: #999;
        }
        .tree-wrap {
            flex: 1;
            overflow: auto;
            padding: 0;
            margin: 0;
        }
        .tree-wrap .mini-tree {
            padding: 0;
            margin: 0;
        }
        .west-panel {
            padding: 0;
            background: #f5f7fa;
            border-right: 1px solid #e8e8e8;
            display: flex;
            flex-direction: column;
        }
        .west-section {
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }
        .west-section:first-child {
            border-bottom: 1px solid #e8e8e8;
        }
    </style>
</head>
<body>

    <!-- 加载提示 -->
    <div id="loading_div_id">
        <div class="loading-indicator">
            <img src="<%=path%>/images/loading.gif" width="32" height="32" />
            <span id="loading-msg"><%=loadingUI%></span>
        </div>
    </div>

    <!-- ===== MiniUI 主布局 ===== -->
    <div class="mini-layout" style="width:100%;height:100%;">

        <!-- ===== 顶部区域 ===== -->
        <div region="north" height="60" showHeader="false" showSplit="false"
             bodyStyle="padding:0;overflow:hidden;">
            <div class="app-header">
                <div class="logo">
                    <img id="bannerLogoImg" style="display:none;" />
                    <span id="bannerTitle"><%=viewProjectName%></span>
                </div>
                <div class="top-nav" id="bannerNav">
                    <span id="languageContainer"></span>
                    <span class="divider">|</span>
                    <a href="#" id="banner_help" onclick="showHelpDocumentWinFn()">
                        <span id="banner_help_text">帮助</span>
                    </a>
                    <span class="divider">|</span>
                    <a href="#" id="banner_exit" onclick="userLoginOut()">
                        <span id="banner_exit_text">退出</span>
                    </a>
                </div>
            </div>
        </div>

        <!-- ===== 左侧区域 ===== -->
        <div region="west" width="250" minWidth="180" maxWidth="400"
     showSplitIcon="true" showHeader="false"
     bodyStyle="padding:0;background:#f5f7fa;border-right:1px solid #e8e8e8;">
    
    <!-- ★ 使用 mini-splitter 垂直分割 ★ -->
    <div class="mini-splitter" style="width:100%;height:100%;" vertical="true">
        
        <!-- 上方：功能菜单（不可折叠） -->
        <div id="menuPanel" size="50%" showCollapseButton="false" minSize="80">
            <div style="height:100%;display:flex;flex-direction:column;">
                <div class="panel-title panel-title-top" style="display:flex; justify-content:space-between; align-items:center;">
    				<span id="functionNavigation_text">功能菜单</span>
    				<button class="mini-button" iconCls="note-refresh" plain="true" onclick="refreshMenuTree()" style="margin-right:0px;"></button>
				</div>
                <div class="tree-wrap" style="flex:1;overflow:auto;">
                    <ul id="menuTree" class="mini-tree" style="width:100%;height:100%;padding:0;margin:0;"
                        showTreeIcon="true" 
                        showRootNode="true"
                        resultAsTree="true"
                        expandOnLoad="true"
                        url="<%=path%>/moduleMenuController/obtainFunctionModuleList"
                        onnodeclick="onMenuTreeClick"
                        onload="onMenuTreeLoad">
                    </ul>
                </div>
            </div>
        </div>
        
        <!-- 下方：组织机构（可向下收缩） -->
        <div id="orgPanel" size="50%" showCollapseButton="true" minSize="60" collapseDirection="bottom">
            <div style="height:100%;display:flex;flex-direction:column;">
                <div class="panel-title panel-title-border" style="display:flex; justify-content:space-between; align-items:center;">
    				<span id="organizationNavigation_text">组织机构</span>
    				<button class="mini-button" iconCls="note-refresh" plain="true" onclick="refreshOrgTree()" style="margin-right:0px;"></button>
				</div>
                <div class="tree-wrap" style="flex:1;overflow:auto;">
                    <ul id="orgTree" class="mini-tree" style="width:100%;height:100%;padding:0;margin:0;"
                        showTreeIcon="true" 
                        showRootNode="true"
                        resultAsTree="true"
                        expandOnLoad="true"
                        idField="orgId"
                        url="<%=path%>/orgManagerController/constructOrgTree"
                        onnodeselect="onOrgTreeSelect"
                        onload="onOrgTreeLoad">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
        <!-- ===== 中间区域：Tabs ===== -->
        <div region="center" bodyStyle="padding:0;">
            <div id="mainTabs" class="mini-tabs" style="width:100%;height:100%;"
                 activeIndex="0" onactivechanged="onTabActiveChanged">
            </div>
        </div>
    </div>

    <!-- ===== 隐藏域 ===== -->
    <input id="leftOrg_Id" class="mini-hidden" />
    <input id="leftOrg_Name" class="mini-hidden" />
    <input id="topModule_Id" class="mini-hidden" />
    <input id="bottomTab_Id" class="mini-hidden" />
    <input id="secondBottomTab_Id" class="mini-hidden" />
    <input id="selectedDeviceType_global" class="mini-hidden" value="0" />
    <input id="AlarmShowStyle_Id" class="mini-hidden" />
    <input id="tabNums_Id" class="mini-hidden" />

    <!-- ================================================================ -->
    <!-- 主 JavaScript（与 DOM 相关的初始化，保留在底部）               -->
    <!-- ================================================================ -->
    <script>
    // ================================================================
    // 顶部栏初始化、事件绑定、WebSocket 等（依赖 DOM 元素）
    // 这些函数现在可以使用上面定义的全局变量（如 loginUserLanguageResource）
    // ================================================================
    function initBannerDisplayInformation() {
        var oem = configFile.ap.oem || {};
        var logo = oem.logo || '';
        if (logo && configFile.ap.others.showLogo) {
            var logoUrl = context + logo.substring(logo.indexOf("/"));
            $('#bannerLogoImg').attr('src', logoUrl + '?timestamp=' + (oem.staticResourceTimestamp || '')).show();
        }
        $('#bannerTitle').html(loginUserLanguageResource.projectName || '<%=viewProjectName%>');
        $('#banner_exit_text').html(loginUserLanguageResource.exit);
        $('#banner_help_text').html(loginUserLanguageResource.help);

        var langHtml = '';
        if (loginUserLanguageList && loginUserLanguageList.length > 1) {
            for (var i = 0; i < loginUserLanguageList.length; i++) {
                var val = loginUserLanguageList[i];
                var label = (val == 1) ? loginUserLanguageResource.language_zh_CN : (val == 2) ? loginUserLanguageResource.language_en : loginUserLanguageResource.language_ru;
                var isActive = (val == loginUserLanguageValue);
                var cls = isActive ? 'active-lang' : '';
                langHtml += '<a href="#" class="' + cls + '" onclick="switchLanguage(' + val + ')">' + label + '</a>';
                if (i < loginUserLanguageList.length - 1) langHtml += ' ';
            }
        }
        $('#languageContainer').html(langHtml);
        
        $('#functionNavigation_text').html(loginUserLanguageResource.functionNavigation);
        $('#organizationNavigation_text').html(loginUserLanguageResource.organizationNavigation);
    }

    function switchLanguage(languageValue) {
        if (parseInt(languageValue) !== parseInt(loginUserLanguageValue)) {
            $.ajax({
                url: context + '/userManagerController/switchUserLanguage',
                data: { languageValue: languageValue },
                success: function() {
                	window.location.href = context+"/home";
                }
            });
        }
    }
    
 // 刷新功能菜单树
    function refreshMenuTree() {
        var tree = mini.get('menuTree');
        if (tree) {
            tree.load(); // 重新从服务器加载数据
        }
    }

 // 刷新组织机构树
    function refreshOrgTree() {
        var tree = mini.get('orgTree');
        if (tree) {
            tree.load();
        }
    }

    function onOrgTreeSelect(e) {
        var node = e.node;
        if (!node) return;
        var orgIds = getOrgNodeIds(node);
        var orgNames = getOrgNodeNames(node);
        mini.get('leftOrg_Id').setValue(orgIds);
        mini.get('leftOrg_Name').setValue(orgNames);
        refreshCurrentModule();
    }

    function onOrgTreeLoad(e) {
        var tree = e.sender;
        var data = tree.getData();
        // 可选择性操作
    }

    function onMenuTreeClick(e) {
        var tree = e.sender;
        var node = e.node;
        if (!node || !tree.isLeaf(node)) return;
        var tabs = mini.get('mainTabs');
        if (!tabs) {
            setTimeout(function() { onMenuTreeClick(e); }, 500);
            return;
        }
        var moduleId = node.id;
        var moduleCode = node.mdCode;
        var viewSrc = node.viewsrc;
        var title = node.text;
        var iconCls = node.md_icon || '';

        if (moduleId === 'backAdmin') {
            window.location.href = context + '/login';
            return;
        }

        var miniuiPath = convertExtToMiniuiPath(viewSrc);
        if (!miniuiPath) {
            miniuiPath = context + '/miniui-app/modules/under-construction.jsp';
        }

        var existingTab = tabs.getTab(moduleId);
        if (existingTab) {
        	var currentActive=tabs.getActiveTab();
        	if(!currentActive || currentActive.id!=existingTab.id){
        		tabs.activeTab(existingTab);
        	}
        } else {
            var isDefault = (FIRST_LEAF_MODULE_ID !== null && moduleId === FIRST_LEAF_MODULE_ID);
            var tab = {
                name: moduleId,
                id:moduleId,
                title: title,
                iconCls: iconCls,
                showCloseButton: !isDefault,
                body: '<iframe src="' + miniuiPath + '?moduleId=' + moduleId + '" style="width:100%;height:100%;border:0;"></iframe>'
            };
            tabs.addTab(tab);
            tabs.activeTab(tab);
        }
        mini.get('topModule_Id').setValue(moduleCode);
        saveAccessModuleLog(moduleCode);
    }

    function onMenuTreeLoad(e) {
        var tree = e.sender;
        var root = tree.getRootNode();
        if (root) {
            tree.expandNode(root);
            var firstLeaf = findFirstLeaf(root, tree);
            if (firstLeaf) {
            	tree.select(firstLeaf);
                FIRST_LEAF_MODULE_ID = firstLeaf.id;
                setTimeout(function() {
                	var tabs = mini.get('mainTabs');
                    if (!tabs) {
                        return;
                    }
                	
                    var moduleId = firstLeaf.id;
                    var moduleCode = firstLeaf.mdCode;
                    var viewSrc = firstLeaf.viewsrc;
                    var title = firstLeaf.text;
                    var iconCls = firstLeaf.md_icon || '';

                    if (moduleId === 'backAdmin') {
                        window.location.href = context + '/login';
                        return;
                    }

                    var miniuiPath = convertExtToMiniuiPath(viewSrc);
                    if (!miniuiPath) {
                        miniuiPath = context + '/miniui-app/modules/under-construction.jsp';
                    }

                    var existingTab = tabs.getTab(moduleId);
                    if (existingTab) {
                        tabs.activeTab(existingTab);
                    } else {
                        var isDefault = (FIRST_LEAF_MODULE_ID !== null && moduleId === FIRST_LEAF_MODULE_ID);
                        var tab = {
                            name: moduleId,
                            id:moduleId,
                            title: title,
                            iconCls: iconCls,
                            showCloseButton: !isDefault,
                            body: '<iframe src="' + miniuiPath + '?moduleId=' + moduleId + '" style="width:100%;height:100%;border:0;"></iframe>'
                        };
                        tabs.addTab(tab);
                        tabs.activeTab(tab);
                    }
                    mini.get('topModule_Id').setValue(moduleCode);
                    saveAccessModuleLog(moduleCode);
                    
                    
                }, 300);
            }
        }
    }

    function findFirstLeaf(node, tree) {
        if (!node) return null;
        if (tree.isLeaf(node)) return node;
        if (node.children && node.children.length > 0) {
            for (var i = 0; i < node.children.length; i++) {
                var result = findFirstLeaf(node.children[i], tree);
                if (result) return result;
            }
        }
        return null;
    }

    function onTabActiveChanged(e) {
    	var tab = e.tab;
        if (!tab) return;
        refreshCurrentModule();
    }

    function refreshCurrentModule() {
        var moduleCode = mini.get('topModule_Id').getValue();
        if (!moduleCode) {
            console.warn('refreshCurrentModule: moduleCode 为空');
            return;
        }
        var tabs = mini.get('mainTabs');
        var activeTab = tabs.getActiveTab();
        if (!activeTab) {
            console.warn('refreshCurrentModule: 没有激活的 Tab');
            return;
        }
        // ★ 使用 MiniUI 官方 API 获取 Tab Body 容器
        var bodyEl = tabs.getTabBodyEl(activeTab);
        if (!bodyEl) {
            console.warn('refreshCurrentModule: 无法获取 Tab Body 元素');
            return;
        }
        // 在 body 容器中查找 iframe
        var iframe = $(bodyEl).find('iframe')[0];
        if (iframe && iframe.contentWindow) {
            var msg = {
                action: 'refresh',
                orgId: mini.get('leftOrg_Id').getValue()
            };
            console.log('向子模块发送刷新消息:', msg, 'iframe.src:', iframe.src);
            iframe.contentWindow.postMessage(msg, '*');
        } else {
            console.warn('refreshCurrentModule: 未找到 iframe 或 contentWindow 不可用');
        }
    }

    function saveAccessModuleLog(moduleCode) {
        var tabs = mini.get('mainTabs');
        var activeTab = tabs.getActiveTab();
        if (!activeTab) return;
        $.ajax({
            url: context + '/logQueryController/saveAccessModuleLog',
            data: {
                moduleCode: moduleCode,
                moduleName: activeTab.title
            }
        });
    }

    function userLoginOut() {
        miniConfirm(loginUserLanguageResource.exitConfirm, function(ok) {
            if (ok) {
                $.ajax({
                    url: context + '/userLoginManagerController/userExit',
                    success: function() {
                        window.location.href = context + '/login';
                    }
                });
            }
        }, loginUserLanguageResource.tip);
    }

    function showHelpDocumentWinFn() {
        if (!showHelp) return;  // 全局配置是否显示帮助按钮
        var tabs = mini.get('mainTabs');
        if (!tabs) return;

        // 查找已存在的帮助标签
        var helpTab = tabs.getTab('HelpDocPanel');
        if (helpTab) {
            tabs.activeTab(helpTab);
            return;
        }

        // 构造帮助文档 URL（带时间戳防止缓存）
        var url = helpDocumentUrl;  // 已包含 context，如 /ap/readme/ap/ap.html
        if (helpDocumentTimestamp) {
            url += (url.indexOf('?') > -1 ? '&' : '?') + 'timestamp=' + helpDocumentTimestamp;
        }

        // 添加新标签
        tabs.addTab({
            name: 'HelpDocPanel',
            title: _loginUserLanguageResource.help,
            iconCls: 'help',
            showCloseButton: true,
            body: '<iframe src="' + url + '" style="width:100%;height:100%;border:0;"></iframe>'
        });
        // 激活新标签
        tabs.activeTab('HelpDocPanel');
    }

    var websocketClient = null;
    function initWebSocket() {
        var baseUrl = getBaseUrl().replace('https', 'ws').replace('http', 'ws');
        var moduleCode = 'ApWebSocketClient_' + user_;
        if ('WebSocket' in window) {
            websocketClient = new ReconnectingWebSocket(
                baseUrl + '/websocketServer/' + moduleCode + '?loginUserLanguage=' + loginUserLanguage
            );
            websocketClient.debug = false;
            websocketClient.reconnectInterval = 1000;
            websocketClient.timeoutInterval = 2000;
            websocketClient.maxReconnectInterval = 30000;
            websocketClient.reconnectDecay = 1.5;
            websocketClient.automaticOpen = true;
            websocketClient.onopen = function() {
                console.log('WebSocket 连接已建立');
            };
            websocketClient.onmessage = websocketOnMessage;
            websocketClient.onerror = function() {
                console.error('WebSocket 连接错误');
            };
            websocketClient.onclose = function() {
                console.log('WebSocket 连接已关闭');
            };
        }
    }
    
 // ===== 将信息推送给指定模块 =====
    function forwardToIframe(moduleId, message) {
        var tabs = mini.get('mainTabs');
        var tab = tabs.getTab(moduleId);
        if (!tab) return;
        // 获取 Tab 内容区域的 DOM 元素
        var bodyEl = tabs.getTabBodyEl(tab);
        if (!bodyEl) return;
        // 在内容区域内查找 iframe
        var iframe = $(bodyEl).find('iframe')[0];
        if (iframe && iframe.contentWindow) {
            iframe.contentWindow.postMessage(message, '*');
        } else {
            console.warn('未找到模块 ' + moduleId + ' 的 iframe');
        }
    }

    // ===== 将信息转发给所有模块 =====
    function broadcastToAllIframes(message) {
        var tabs = mini.get('mainTabs');
        var allTabs = tabs.getTabs();
        for (var i = 0; i < allTabs.length; i++) {
            var tab = allTabs[i];
            var bodyEl = tabs.getTabBodyEl(tab);
            if (!bodyEl) continue;
            var iframe = $(bodyEl).find('iframe')[0];
            if (iframe && iframe.contentWindow) {
                iframe.contentWindow.postMessage(message, '*');
            }
        }
    }

    function websocketOnMessage(evt) {
        try {
            var receiveData = evt.data;
            if (evt.data.indexOf("}{") >= 0) {
                var dataStr = evt.data.replace("}{", "}@@@@{");
                receiveData = dataStr.split("@@@@")[0];
            }
            var data = JSON.parse(receiveData);

            var tabs = mini.get('mainTabs');
            var activeTab = tabs.getActiveTab();
            if (!activeTab) return;
            var activeId = activeTab.name;

            var funcCode = data.functionCode ? data.functionCode.toUpperCase() : '';
            
            // 实时监控
            if (activeId === 'DeviceRealTimeMonitoring') {
                if (funcCode === "DEVICEREALTIMEMONITORINGDATA" ||
                    funcCode === "DEVICEREALTIMEMONITORINGSTATUSDATA") {
                    forwardToIframe(activeId, {
                        action: 'updateDeviceData',
                        data: data
                    });
                } else if (funcCode === "RESOURCEMONITORINGDATA") {
                    forwardToIframe(activeId, {
                        action: 'updateResourceData',
                        data: data
                    });
                } else if (funcCode === "DBMONITORINGDATA") {
                    forwardToIframe(activeId, {
                        action: 'updateDBData',
                        data: data
                    });
                } else if (funcCode === "ADEXITANDDEVICEOFFLINE") {
                    forwardToIframe(activeId, {
                        action: 'adExitAndDeviceOffline',
                        data: data
                    });
                }
            }
            // 上下游交互
            else if (activeId === 'UpstreamAndDownstreamInteraction') {
                if (funcCode === "SRPUPONLINEDATA" || funcCode === "SRPDOWNONLINEDATA") {
                    forwardToIframe(activeId, {
                        action: 'updateUpDownData',
                        data: data
                    });
                } else if (funcCode === "ADEXITANDDEVICEOFFLINE_SRP") {
                    forwardToIframe(activeId, {
                        action: 'adExitAndDeviceOffline',
                        data: data
                    });
                }
            }

            //console.log('WebSocket 消息已转发到模块:', activeId, 'functionCode:', funcCode);
        } catch (e) {
            console.error('WebSocket message error:', e);
        }
    }

    function fullscreen() {
        var el = document.documentElement;
        var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen;
        if (rfs) rfs.call(el);
    }

    function exitFullscreen() {
        var elem = document;
        if (elem.webkitCancelFullScreen) elem.webkitCancelFullScreen();
        else if (elem.mozCancelFullScreen) elem.mozCancelFullScreen();
        else if (elem.cancelFullScreen) elem.cancelFullScreen();
        else if (elem.exitFullscreen) elem.exitFullscreen();
    }

    function convertExtToMiniuiPath(viewSrc) {
        var mapping = {
            'AP.view.realTimeMonitoring.RealTimeMonitoringInfoView':  context + '/miniui-app/modules/realTimeMonitoring/RealTimeMonitoringInfo.jsp',
            'AP.view.historyQuery.HistoryQueryInfoView':  context + '/miniui-app/modules/historyQuery/HistoryQuery.jsp',
            'AP.view.alarmQuery.AlarmQueryInfoView': context + '/miniui-app/modules/alarmQuery/AlarmQuery.jsp'
        };
        return mapping[viewSrc] || null;
    }

    // 页面初始化
    $(document).ready(function() {
        mini.parse();
        initBannerDisplayInformation();
        $('#loading_div_id').hide();
        initWebSocket();
        $(window).resize(function() {
            $('.highcharts-container').each(function() {
                try {
                    var chart = $(this).highcharts();
                    if (chart) chart.reflow();
                } catch(e) {}
            });
        });
    });

    console.log('MiniUI 主页面加载完成');
    </script>
</body>
</html>