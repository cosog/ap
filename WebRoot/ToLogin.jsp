<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>go</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <script >
  //alert("as")
  //平台、设备和操作系统
    var system ={
        win : false,
        mac : false,
        x11 : false
    };
    //检测平台
    var p = navigator.platform;
	var u = navigator.userAgent;
    system.win = p.indexOf("Win") == 0;
    system.mac = p.indexOf("Mac") == 0;
    system.x11 = (p == "X11") || (p.indexOf("Linux") == 0&&u.indexOf("Android")<0);
    //跳转语句
    if(system.win||system.mac||system.x11){//转向后台登陆页面
        window.location.href = "login/toLogin";
        //window.location.href = "mainManage/diagnosisOnly!toTouchLogin.action";
    }else{
    	window.location.href = "login/toLogin";
        //window.location.href = "mainManage/diagnosisOnly!toTouchLogin.action";
    }
</script>
  </head>
  <body>
  </body>
</html>
