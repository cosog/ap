<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<fmt:setBundle basename="config/messages"/>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta http-equiv="Content-Type" content="text/html" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/styles/home.css">
</head>
<body>
    <div id="bodyLeft"></div>
    <div id="bodyCenter">
        <div id="homeTop">
            <img id="topImg" src="<%=path%>/images/project.png" />&nbsp;
            <span><fmt:message key="main.title"/></span>&nbsp;&nbsp;
            <span id="projectEn">Project Overview</span>
        </div>
        <div id="homeCenter"><span><fmt:message key="main.profile"/></span></div>
        <div id="homeBottom">
            <div class="imgTop"></div>
            <div><img id="bottomImg" src="<%=path%>/images/bgimg.png" />
            </div>
            <div class="imgBottom"></div>
        </div>
    </div>
    <div id="bodyRight"></div>
</html>