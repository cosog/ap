<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
   	String browserLang=(String)session.getAttribute("browserLang");
   	String viewProjectName=(String)session.getAttribute("viewProjectName");
   	String favicon=(String)session.getAttribute("favicon");
	favicon=favicon.substring(favicon.indexOf("/"),favicon.length());
   	String bannerCSS=(String)session.getAttribute("bannerCSS");
   	bannerCSS=bannerCSS.substring(bannerCSS.indexOf("/"),bannerCSS.length());
   	boolean showLogo=(boolean)session.getAttribute("showLogo");
   	String oemStaticResourceTimestamp=(String)session.getAttribute("oemStaticResourceTimestamp");
	String otherStaticResourceTimestamp=(String)session.getAttribute("otherStaticResourceTimestamp");
   	request.setAttribute("browserLang",browserLang );
   	String loadingUI="Loading UI…";
   	loadingUI=(String)session.getAttribute("loadingUI");
%>
<html>

<head>
    <!--<fmt:setBundle basename="config/messages"/>-->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%=viewProjectName%></title>
    <%if(showLogo){ %>
    <link rel="Bookmark" href="<%=path+favicon%>?timestamp=<%=oemStaticResourceTimestamp%>" />
    <link rel="icon" href="<%=path+favicon%>?timestamp=<%=oemStaticResourceTimestamp%>" type="image/x-icon" />
    <link rel="shortcut icon" href="<%=path+favicon%>?timestamp=<%=oemStaticResourceTimestamp%>" type="image/x-icon" />
    <link rel="Bookmark" href="<%=path+favicon%>?timestamp=<%=oemStaticResourceTimestamp%>" type="image/x-icon" />
    <%} %>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="computer,mobile,internet">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit">
    <!-- 加载类库 -->
    <jsp:include flush="true" page="./tags.jsp" />
    <script>
        var context = '<%=path%>';

        var user_ = "${userLogin.userNo}";
        if (user_ == null || "" == (user_)) {
           // window.location.href = "../login";
            window.location.href = "<%=path%>/login";
        }
        
        var userAccount = "${userLogin.userId}";

        var loginUserRoleLevel = "${userLogin.roleLevel}";
        var loginUserRoleShowLevel = "${userLogin.roleShowLevel}";
        var loginUserRoleReportEdit = true;
        var loginUserRoleVideoKeyEdit = "${userLogin.roleVideoKeyEdit}";
        var loginUserLanguageKeyEdit = "${userLogin.roleLanguageEdit}";
        var loginUserLanguage = "${userLogin.languageName}";
        
        var loginUserLanguageResource='${userLogin.languageResource}';
        if(isNotVal(loginUserLanguageResource)){
        	loginUserLanguageResource=JSON.parse(loginUserLanguageResource);
        }else{
        	loginUserLanguageResource={};
        }
        
        
        var loginUserLanguageResourceFirstLower='${userLogin.languageResourceFirstLower}';
        if(isNotVal(loginUserLanguageResourceFirstLower)){
        	loginUserLanguageResourceFirstLower=JSON.parse(loginUserLanguageResourceFirstLower);
        }else{
        	loginUserLanguageResourceFirstLower={};
        }
        
        
        var oem = ${configFile}.ap.oem;

        var bannerLogoImg = oem.logo;
        bannerLogoImg = context + bannerLogoImg.substring(bannerLogoImg.indexOf("/"), bannerLogoImg.length);

        var helpButtonIcon = oem.helpButtonIcon;
        helpButtonIcon = context + helpButtonIcon.substring(helpButtonIcon.indexOf("/"), helpButtonIcon.length);

        var exitButtonIcon = oem.exitButtonIcon;
        exitButtonIcon = context + exitButtonIcon.substring(exitButtonIcon.indexOf("/"), exitButtonIcon.length);
        
        var zoomInButtonIcon = oem.zoomInButtonIcon;
        zoomInButtonIcon = context + zoomInButtonIcon.substring(zoomInButtonIcon.indexOf("/"), zoomInButtonIcon.length);
        
        var zoomoutButtonIcon = oem.zoomoutButtonIcon;
        zoomoutButtonIcon = context + zoomoutButtonIcon.substring(zoomoutButtonIcon.indexOf("/"), zoomoutButtonIcon.length);

        var productionUnit = ${configFile}.ap.others.productionUnit;
        
        var IoTConfig = ${ configFile}.ap.others.iot;     //物联网
        var sceneConfig = ${ configFile}.ap.others.scene; //应用场景 all-全部 oil-油井 cbm-煤层气井
        var moduleConfig = ${ configFile}.ap.others.module;//模块 ""-仅监测 all-全部 srp-抽油机井功图计算 pcp-螺杆泵井转速计算
        
        
        
        var pcpHidden = true;
        if(moduleConfig=='all' || moduleConfig=='pcp'){
        	pcpHidden = false
        }
        
        var onlyMonitor=false;
        if(IoTConfig && moduleConfig==""){
        	onlyMonitor=true;
        }
        
        var onlyFESDiagramCal=false;
        if(!IoTConfig){
        	onlyFESDiagramCal=true;
        }
        
        var exportAdInitData = ${configFile}.ap.others.exportAdInitData;
        
        var showVideoConfig = ${configFile}.ap.others.showVideo;
        
        var showLogo = ${configFile}.ap.others.showLogo;
        
        var oemStaticResourceTimestamp = ${configFile}.ap.oem.staticResourceTimestamp;
        var otherStaticResourceTimestamp = ${configFile}.ap.others.otherStaticResourceTimestamp;

        //var helpDocumentUrl=oem.helpDocument;
        var helpDocumentUrl="${helpDocumentUrl}";
        var helpDocumentTimestamp=oem.helpDocumentTimestamp;
        helpDocumentUrl = context + helpDocumentUrl.substring(helpDocumentUrl.indexOf("/"), helpDocumentUrl.length);
        
        var defaultComboxSize = ${configFile}.ap.others.defaultComboxSize;
        var defaultGraghSize = ${configFile}.ap.others.defaultGraghSize;
        
        

        var defaultPageSize = ${configFile}.ap.others.pageSize;
        var tabInfo=${tabInfo};
        
        var resourceMonitoringSaveData=${configFile}.ap.others.resourceMonitoringSaveData; 
        
        function initBannerDisplayInformation() {
            $("#banner_exit").css("background", "url(" + exitButtonIcon + "?timestamp="+oemStaticResourceTimestamp+")  no-repeat");
            $("#banner_help").css("background", "url(" + helpButtonIcon + "?timestamp="+oemStaticResourceTimestamp+")  no-repeat");
            
            if(!showLogo){
            	 $('#bannerLogoImg').css('display', 'none');
            }else{
            	$('#bannerLogoImg').css('display', 'block');
            	$("#bannerLogoImg").attr("src", bannerLogoImg + "?timestamp="+oemStaticResourceTimestamp);
            }
            $('#bannerTitle').html(loginUserLanguageResource.projectName);
            
            $('#banner_exit_text').html(loginUserLanguageResource.exit);
            $('#banner_help_text').html(loginUserLanguageResource.help);
        }

    </script>

    <style type="text/css">
        #loading_div_id {
            position: absolute;
            left: 40%;
            top: 40%;
            padding: 2px;
            z-index: 20001;
            height: auto;
        }

        #loading_div_id .loading-indicator {
            background: white;
            color: #444;
            font: bold 20px tahoma, arial, helvetica;
            padding: 10px;
            margin: 0;
            height: auto;
        }

        #loading-msg {
            font: normal 18px arial, tahoma, sans-serif;
        }

    </style>
</head>

<body>
    <!-- 加载效果 -->
    <div id="loading_div_id">
        <div class="loading-indicator">
            <img src="<%=path%>/images/loading.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;" /> <span id="loading-msg"><%=loadingUI%></span>
        </div>
    </div>
    <script type="text/javascript">
        var m_DefaultPosition = '39.9,116.4'; //初始化地图，中心点
        var m_DefaultZoomLevel = 10; //初始化地图，缩放级别
        var mapHelper = null; //地图工具库返回来的操作对象
        var mapHelperOrg = null; //地图工具库返回来的操作对象
        var mapHelperWellsite = null; //地图工具库返回来的操作对象
        var mapHelperWell = null; //地图工具库返回来的操作对象

        var m_FrontData;
        var m_BackOrgData = null;
        var m_BackWellData = null;
        var m_BackWellsiteData = null;

        var m_animateOrgId = null;
        var m_animateWellsiteId = null;
        var m_animateWellId = null;
        var m_PagerSize = 5;
        var userSyncOrAsync = 1;
        var treeInfo;
        var nav_html = "";
        var user_Type = "";
        //启动Extjs
        Ext.onReady(function() {
            Ext.BLANK_IMAGE_URL = "<%=path%>/scripts/extjs/resources/themesimages/default/s.gif";
            Ext.BLANK_IMAGE_URL = "<%=path%>/scripts/extjs/resources/themes/images/default/tree/s.gif";
            var user = "${userLogin.userNo}";
            var user_Name = "${userLogin.userName}";
            user_Type = "${userLogin.userType}";
            userOrg_Id = "${userLogin.userOrgid}";
            userOrg_Ids = "${userLogin.userorgids}";
            userOrg_Names = "${userLogin.userOrgNames}";
            userParentOrg_Ids = "${userLogin.userParentOrgids}";
            userSyncOrAsync = "${userLogin.syncOrAsync}";
            pic_url = "${userLogin.picUrl}"; //动态监测功图查询时，需要的图形URL地址
            
            _clientWidth = document.body.clientWidth;
        });

    </script>
</body>

</html>
