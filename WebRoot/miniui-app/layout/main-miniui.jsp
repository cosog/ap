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

    <!-- ===== 动态引入 MiniUI 资源 ===== -->
    <jsp:include page="./tags-miniui.jsp" flush="true" />

    <style>
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", "Helvetica Neue", Arial, sans-serif;
        }

        /* 顶部栏 */
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

        /* 树样式 - 移除多余边距 */
        .mini-tree {
            background: transparent;
            padding: 0 !important;
            margin: 0 !important;
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
        /* 确保树容器没有额外边距 */
        .tree-container {
            flex: 1;
            overflow: auto;
            padding: 0 !important;
            margin: 0 !important;
        }

        /* 加载提示 */
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

        /* 左侧面板标题 */
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
                    <span id="resourceMonitorContainer" style="margin-left:20px;font-size:12px;"></span>
                </div>
            </div>
        </div>

        <!-- ===== 左侧区域：组织树 (40%) + 功能菜单树 (60%) ===== -->
        <div region="west" width="250" minWidth="180" maxWidth="400"
             showSplitIcon="true" title="导航"
             bodyStyle="padding:0;background:#f5f7fa;border-right:1px solid #e8e8e8;display:flex;flex-direction:column;">

            <!-- 组织树区域（占 40% 高度） -->
            <div style="flex:4;display:flex;flex-direction:column;overflow:hidden;border-bottom:1px solid #e8e8e8;">
                <div class="panel-title panel-title-border">
                    <span>📁 组织机构</span>
                    <span class="count" id="orgTreeCount"></span>
                </div>
                <div class="tree-container" style="flex:1;overflow:auto;padding:0;margin:0;">
                    <ul id="orgTree" class="mini-tree" style="width:100%;height:100%;padding:0;margin:0;"
                        showTreeIcon="true" 
                        showRootNode="false"
                        idField="orgId"
                        expandOnLoad="true"
                        url="<%=path%>/orgManagerController/constructOrgTree"
                        onnodeselect="onOrgTreeSelect"
                        onload="onOrgTreeLoad">
                    </ul>
                </div>
            </div>

            <!-- 功能菜单树区域（占 60% 高度） -->
            <div style="flex:6;display:flex;flex-direction:column;overflow:hidden;">
                <div class="panel-title panel-title-top">
                    <span>📋 功能菜单</span>
                </div>
                <div class="tree-container" style="flex:1;overflow:auto;padding:0;margin:0;">
                    <ul id="menuTree" class="mini-tree" style="width:100%;height:100%;padding:0;margin:0;"
                        showTreeIcon="true" 
                        showRootNode="false"
                        expandOnLoad="true"
                        url="<%=path%>/moduleMenuController/obtainFunctionModuleList"
                        onnodeselect="onMenuTreeSelect"
                        onload="onMenuTreeLoad">
                    </ul>
                </div>
            </div>
        </div>

        <!-- ===== 中间区域：Tabs ===== -->
        <div region="center" bodyStyle="padding:0;">
            <div id="mainTabs" class="mini-tabs" style="width:100%;height:100%;"
                 activeIndex="0" onactivechanged="onTabActiveChanged">
            </div>
        </div>

        <!-- ===== 底部区域 ===== -->
        <div region="south" height="25" showHeader="false" showSplit="false"
             bodyStyle="text-align:center;padding:4px 0;background:#f0f2f5;color:#999;font-size:12px;border-top:1px solid #e8e8e8;">
            Copyright © 上海普加软件有限公司版权所有
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
    <!-- 主 JavaScript                                                   -->
    <!-- ================================================================ -->
    <script>
    // ================================================================
    // 1. 全局变量
    // ================================================================
    var context = '<%=path%>';
    var user_ = '<%=userLoginNo%>';
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
    var tabInfo = JSON.parse('<%= tabInfoJson.replace("'", "\\'") %>');
    var defaultPageSize = configFile.ap.others.pageSize || 25;
    var productionUnit = configFile.ap.others.productionUnit || 't/d';
    var showHelp = configFile.ap.others.showHelp !== false;

    // ================================================================
    // 2. 顶部栏初始化
    // ================================================================
    function initBannerDisplayInformation() {
        var oem = configFile.ap.oem || {};
        var logo = oem.logo || '';
        if (logo && configFile.ap.others.showLogo) {
            var logoUrl = context + logo.substring(logo.indexOf("/"));
            $('#bannerLogoImg').attr('src', logoUrl + '?timestamp=' + (oem.staticResourceTimestamp || '')).show();
        }
        $('#bannerTitle').html(loginUserLanguageResource.projectName || '<%=viewProjectName%>');
        $('#banner_exit_text').html(loginUserLanguageResource.exit || '退出');
        $('#banner_help_text').html(loginUserLanguageResource.help || '帮助');

        var langHtml = '';
        if (loginUserLanguageList && loginUserLanguageList.length > 1) {
            for (var i = 0; i < loginUserLanguageList.length; i++) {
                var val = loginUserLanguageList[i];
                var label = (val == 1) ? '中文' : (val == 2) ? 'English' : 'Русский';
                var isActive = (val == loginUserLanguageValue);
                var cls = isActive ? 'active-lang' : '';
                langHtml += '<a href="#" class="' + cls + '" onclick="switchLanguage(' + val + ')">' + label + '</a>';
                if (i < loginUserLanguageList.length - 1) langHtml += ' ';
            }
        }
        $('#languageContainer').html(langHtml);
        initResourceMonitor();
    }

    // ================================================================
    // 3. 语言切换
    // ================================================================
    function switchLanguage(languageValue) {
        if (parseInt(languageValue) !== parseInt(loginUserLanguageValue)) {
            $.ajax({
                url: context + '/userManagerController/switchUserLanguage',
                data: { languageValue: languageValue },
                success: function() {
                    window.location.href = context + '/miniui-app/layout/main-miniui.jsp';
                }
            });
        }
    }

    // ================================================================
    // 4. 资源监测
    // ================================================================
    function initResourceMonitor() {
        var container = $('#resourceMonitorContainer');
        if (container.length) {
            container.html('<span style="color:#8f8;">●</span> <span style="color:#aaa;">系统运行中</span>');
        }
    }

    // ================================================================
    // 5. 组织树选择
    // ================================================================
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
        // 如果数据包含"组织根节点"，跳过它直接显示子节点
        var data = tree.getData();
        if (data && data.length > 0 && data[0].text && data[0].text.indexOf('根') !== -1) {
            tree.setData(data[0].children || []);
        }
        var root = tree.getRootNode();
        if (root) {
            tree.expandNode(root);
            var count = tree.getNodes(root);
            $('#orgTreeCount').text('共 ' + count.length + ' 个');
        }
    }

    // ================================================================
    // 6. 功能菜单树选择
    // ================================================================
    function onMenuTreeSelect(e) {
        var node = e.node;
        if (!node || !node.isLeaf) return;
        var moduleId = node.id;
        var moduleCode = node.mdCode;
        var viewSrc = node.viewsrc;
        var title = node.text;
        var iconCls = node.md_icon || '';

        if (moduleId === 'backAdmin') {
            window.location.href = context + '/login';
            return;
        }

        var tabs = mini.get('mainTabs');
        var existingTab = tabs.getTab(moduleId);
        if (existingTab) {
            tabs.activeTab(existingTab);
        } else {
            var tab = {
                name: moduleId,
                title: title,
                iconCls: iconCls,
                closable: true,
                body: '<iframe src="' + context + '/' + viewSrc + '" style="width:100%;height:100%;border:0;"></iframe>'
            };
            tabs.addTab(tab);
            tabs.activeTab(tab);
        }

        mini.get('topModule_Id').setValue(moduleCode);
        saveAccessModuleLog(moduleCode);
    }

    function onMenuTreeLoad(e) {
        var tree = e.sender;
        // 如果数据包含虚拟根节点，跳过它
        var data = tree.getData();
        if (data && data.length > 0 && data[0].text && data[0].text.indexOf('功能导航') !== -1) {
            tree.setData(data[0].children || []);
        }
        var root = tree.getRootNode();
        if (root) {
            tree.expandNode(root);
            var firstLeaf = findFirstLeaf(root);
            if (firstLeaf) {
                tree.selectNode(firstLeaf);
            }
        }
    }

    function findFirstLeaf(node) {
        if (node.isLeaf) return node;
        if (node.children) {
            for (var i = 0; i < node.children.length; i++) {
                var result = findFirstLeaf(node.children[i]);
                if (result) return result;
            }
        }
        return null;
    }

    // ================================================================
    // 7. Tab 切换事件
    // ================================================================
    function onTabActiveChanged(e) {
        var tab = e.tab;
        if (!tab) return;
        refreshCurrentModule();
    }

    // ================================================================
    // 8. 刷新当前模块
    // ================================================================
    function refreshCurrentModule() {
        var moduleCode = mini.get('topModule_Id').getValue();
        if (!moduleCode) return;
        var tabs = mini.get('mainTabs');
        var activeTab = tabs.getActiveTab();
        if (!activeTab) return;
        var iframe = $(activeTab.tabEl).find('iframe')[0];
        if (iframe && iframe.contentWindow) {
            iframe.contentWindow.postMessage({
                action: 'refresh',
                orgId: mini.get('leftOrg_Id').getValue()
            }, '*');
        }
    }

    // ================================================================
    // 9. 保存模块访问日志
    // ================================================================
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

    // ================================================================
    // 10. 退出登录
    // ================================================================
    function userLoginOut() {
        miniConfirm(loginUserLanguageResource.exitConfirm || '确定要退出吗？', function(ok) {
            if (ok) {
                $.ajax({
                    url: context + '/userLoginManagerController/userExit',
                    success: function() {
                        window.location.href = context + '/login';
                    }
                });
            }
        }, loginUserLanguageResource.tip || '提示');
    }

    // ================================================================
    // 11. 帮助文档窗口
    // ================================================================
    function showHelpDocumentWinFn() {
        if (!showHelp) return;
        var tabs = mini.get('mainTabs');
        var helpTab = tabs.getTab('HelpDocPanel');
        if (helpTab) {
            tabs.activeTab(helpTab);
        } else {
            var url = context + '/readme/ap/ap.html';
            tabs.addTab({
                name: 'HelpDocPanel',
                title: loginUserLanguageResource.help || '帮助',
                iconCls: 'help',
                closable: true,
                body: '<iframe src="' + url + '" style="width:100%;height:100%;border:0;"></iframe>'
            });
        }
    }

    // ================================================================
    // 12. WebSocket
    // ================================================================
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

    // ================================================================
    // 13. websocketOnMessage
    // ================================================================
    function websocketOnMessage(evt) {
        try {
            var data = JSON.parse(evt.data);
            var tabs = mini.get('mainTabs');
            var activeTab = tabs.getActiveTab();
            if (!activeTab) return;
            var activeId = activeTab.name;

            if (data.functionCode && data.functionCode.toUpperCase() === 'deviceRealTimeMonitoringData'.toUpperCase()) {
                if (activeId === 'DeviceRealTimeMonitoring') {
                    console.log('实时数据推送:', data);
                }
            }
        } catch (e) {
            console.error('WebSocket message error:', e);
        }
    }

    // ================================================================
    // 14. 页面初始化
    // ================================================================
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

    // ================================================================
    // 15. 全屏相关
    // ================================================================
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

    console.log('MiniUI 主页面加载完成');
    </script>
</body>
</html>