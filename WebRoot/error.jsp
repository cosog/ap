<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>软件未授权界面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style>
* {
    margin: 0px;
    padding: 0px;
    border: 0px;
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -o-box-sizing: border-box;
    -ms-box-sizing: border-box;
}

html,
body {
    background-image: url(<%=path%>/images/loginbg.jpg); 
    background-attachment:fixed;
    background-position:center;
    background-repeat:no-repeat;
}

#infoTitle {
    margin-top: 10%;
    text-align: center;
    color: #FF6600;
    font-size: 30px;
    font-weight: bold;
}

#Nav {
    margin-top: 5%;
    margin-left: 40%;
	list-style: none;
	text-align: center;
	font-size: 14px;
}

#Nav li {
	height: 30px;
	text-align:left;
	line-height: 30px;
}

#company {
    margin-top: 10%;
    text-align:center;
}

</style>
</head>

<body>
	<div id="infoTitle">该软件尚未授权，请与软件开发商联系！</div>
	<!-- 
	<div>
		<ul id="Nav">
			<li>软件授权人：赵金猛</li>
			<li>地址：北京市海淀区安宁庄路26号悦MOMA 705</li>
			<li>电话：010-82921872</li>
			<li>E-mail：cosogoil@126.com</li>
			<li>京ICP备06035083号</li>
		</ul>
	</div>
	<div id="company">
		<a href="http://www.cosogoil.com/" target="_blank">北京科斯奇石油科技有限公司</a>
	</div>
	 -->
</body>
</html>
