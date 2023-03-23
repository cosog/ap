<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
   	String browserLang=(String)request.getAttribute("browserLang");
   	String viewProjectName=(String)session.getAttribute("viewProjectName");
   	String favicon=(String)session.getAttribute("favicon");
	favicon=favicon.substring(favicon.indexOf("/"),favicon.length());
   	String bannerCSS=(String)session.getAttribute("bannerCSS");
   	bannerCSS=bannerCSS.substring(bannerCSS.indexOf("/"),bannerCSS.length());
   	boolean showLogo=(boolean)session.getAttribute("showLogo");
   	request.setAttribute("browserLang",browserLang );
%>
<html>
<head>
<!--<fmt:setBundle basename="config/messages"/>-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=viewProjectName%></title>
<%if(showLogo){ %>
<link rel="Bookmark" href="<%=path+favicon%>?timestamp=202202231815" />
<link rel="icon" href="<%=path+favicon%>?timestamp=202202231815" type="image/x-icon" />
<link rel="shortcut icon" href="<%=path+favicon%>?timestamp=202202231815" type="image/x-icon" />
<link rel="Bookmark" href="favicon.ico" />
<%} %>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="computer,mobile,internet">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit">
<script> 
 var user_ = "${userLogin.userNo}";
 var oem = ${configFile}.ap.oem;
 
 var logoImg=oem.logo;
 logoImg=".."+logoImg.substring(logoImg.indexOf("/"),logoImg.length);
 
 var productionUnit = ${configFile}.ap.others.productionUnit;
 var pcpHidden = ${configFile}.ap.others.pcpHidden;
 var onlyMonitor = ${configFile}.ap.others.onlyMonitor;
 var showLogo = ${configFile}.ap.others.showLogo;
 
 var defaultComboxSize=${configFile}.ap.others.defaultComboxSize;
 var defaultGraghSize=${configFile}.ap.others.defaultGraghSize;
 
 
 var userAccount="${userLogin.userId}";
 
 var loginUserRoleLevel="${userLogin.roleLevel}";
 var loginUserRoleFlag="${userLogin.roleFlag}";
 var loginUserRoleShowLevel="${userLogin.roleShowLevel}";
 var loginUserRoleReportEdit="${userLogin.roleReportEdit}";
 if (user_ == null || "" == (user_)) {
	 window.location.href = "../login/toLogin";
 }
</script>
<!-- 加载类库 -->
<jsp:include flush="true" page="./tags.jsp" />
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
			<img src="<%=path%>/images/loading.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;" /> <span id="loading-msg">正在加载前台UI ...</span>
		</div>
	</div>
	<script type="text/javascript"> 
	var m_DefaultPosition = '39.9,116.4';   //初始化地图，中心点
    var m_DefaultZoomLevel = 10;        //初始化地图，缩放级别
    var mapHelper=null;                      //地图工具库返回来的操作对象
    var mapHelperOrg = null;                      //地图工具库返回来的操作对象
	var mapHelperWellsite = null;                      //地图工具库返回来的操作对象
	var mapHelperWell = null;                      //地图工具库返回来的操作对象
	
    var m_FrontData;
    var m_BackOrgData = null;
	var m_BackWellData = null;
	var m_BackWellsiteData = null;
	
    var m_animateOrgId = null;
    var m_animateWellsiteId = null;
    var m_animateWellId = null;
    var m_PagerSize = 5;
    var userSyncOrAsync=1;
    var treeInfo;
    var nav_html="";
    var user_Type="";
 	  //启动Extjs
	  Ext.onReady(function(){
			    Ext.BLANK_IMAGE_URL="<%=path%>/scripts/extjs/resources/themesimages/default/s.gif";
		        Ext.BLANK_IMAGE_URL = "<%=path%>/scripts/extjs/resources/themes/images/default/tree/s.gif";
		        //Ext.Ajax.setTimeout(180000);
		        //alert(isConnectionInternet);
					var user = "${userLogin.userNo}";
					var user_Name = "${userLogin.userName}";
					user_Type = "${userLogin.userType}";
					userOrg_Id = "${userLogin.userOrgid}";
					userOrg_Ids ="${userLogin.userorgids}";
					userOrg_Names ="${userLogin.userOrgNames}";
					userParentOrg_Ids ="${userLogin.userParentOrgids}";
					userSyncOrAsync = "${userLogin.syncOrAsync}";
					pic_url = "${userLogin.picUrl}";//动态监测功图查询时，需要的图形URL地址
					//默认前台分页数据量
					defaultPageSize = "${userLogin.pageSize}";
					
					_clientWidth = document.body.clientWidth;
					
					// MsgTip.msg('消息', '设置10秒后自动隐藏',true,2000);
				});
 	  
	</script>
</body>
</html>