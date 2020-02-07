<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
%>
<%  
	String flag="";
	String name="";
	String password="";
	try{
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				if(cookies[i].getName().equals("cookieuser")){
					String value=cookies[i].getValue();
					if(value!=null&&!"".equals(value)){
					name=cookies[i].getValue().split("-")[0];
						if(cookies[i].getValue().split("-")[1]!=null && !cookies[i].getValue().split("-")[1].equals("null")){
							password=cookies[i].getValue().split("-")[1];
							flag="1";
						}
					}
				}
				request.setAttribute("name", name);
				request.setAttribute("password", password);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>

<!DOCTYPE html>
<html>

<head>
	<fmt:setBundle basename="config/messages"/>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="renderer" content="webkit">
    <!--默认360极速模式-->
    <meta http-equiv="Content-Type" content="text/html" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache,must-revalidate" />
    <meta http-equiv="expires" content="0" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/styles/jquery.msgbox.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/styles/jquery.loadmask.css">
    <script type="text/javascript" src="<%=path%>/scripts/jquery/jquery-2.2.0.min.js"></script>
	<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery.dragndrop.min.js"></script>
	<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery.msgbox.js"></script>
	<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery.loadmask.js"></script>
    <title><fmt:message key="cosog.title"/></title>
    <link rel="Bookmark" href="<%=path%>/images/logo/favicon.ico" />
    <link rel="icon" href="<%=path%>/images/logo/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="<%=path%>/images/logo/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/styles/touchLogin.css">
    <script>
    	// 登录
 		function touchLoginFun(){
 			//$("#touchLogining").show();
 			$('#touchLoginButton').mask("正在登录");
   			var get_name=$("#userId").val();
   			var get_pwd=$("#userPwd").val();
   			if(null!=get_name&&get_name!=""&&null!=get_pwd&&get_pwd!=""){
     			touchVerifyFn();
   			}else if(null==get_name||get_name==""){
   				$('#touchLoginButton').unmask();
   				return false;
     			//alert("用户名不能为空");
   			}else if(null!=get_name&&get_name!=""&&null==get_pwd||get_pwd==""){
   				$('#touchLoginButton').unmask();
   				return false;
     			//alert("密码不能为空");
   			} 
 		}
 		username="";
 		// 真实登录进入前台
 		function touchVerifyFn(){
			var getTime=new Date();
			var getSeconds=getTime.getMinutes()+getTime.getSeconds();
			var getLogin_=$("#userId").val();
   			var userId_ = encodeURI(encodeURI(getLogin_));
			var userPwd_ = encodeURI(encodeURI($("#userPwd").val()));
			var flag_=0;
			var paramObj ="?&time="+getSeconds+"&userId=" + userId_ + "&userPwd=" + userPwd_+"&flag=" + flag_;	
			var url="<%=path%>/userLoginManagerController/userLogin"+paramObj;
   			// jquery Ajax请求事件
			$.ajax({
				url: url,
				cache:false,
				success:function(data){
		    		username=getLogin_;
					touchLoginCallback(data,username);
				}
			});
			return false;
 		}
 		// 登录前台的返调函数处理
 		function touchLoginCallback(data,username) {
        	var obj;
			if(data!=""||data!=null){
			 	obj= eval("(" + data + ")");
			}else{
				window.location.href = "<%=path%>/errorinfo.html";
			} 
			if (obj.flag == "normal") {
		    	$.cookie("username", username, { expires: 7 });
		    	//$('#touchLoginButton').unmask();
				window.location.href = "<%=path%>/diagnosisAnalysisOnlyController/toTouchMain";
			}else if(obj.flag == false){
				//new $.msgbox(obj.msg).show();
				alert("用户名或密码错误");
				$('#touchLoginButton').unmask();
			}else {
				$('#touchLoginButton').unmask();
				alert("登录出错");
			}
			return false;
		}
		
    </script>
</head>

<body>
    <div id="loginBody" class="vbox">
        <div class="hbox center">
            <img src="<%=path%>/images/login/touchLogin.png" />
        </div>
        <div class="hbox padding">
            <label class="text">用&nbsp;户：&nbsp;</label>
            <input type="text" id="userId" class="input text" placeholder="请输入用户" value="<%=name%>" />
        </div>
        <div class="padding border"></div>
        <div class="hbox padding">
            <label class="text">密&nbsp;码：&nbsp;</label>
            <input type="password" id="userPwd" class="input text" placeholder="请输入密码" />
        </div>
        <div class="padding border"></div>
        <div class="hbox padding center">
            <button type="button" id="touchLoginButton" class="button" onclick="touchLoginFun();" >登录</button>
        </div>
        <div class="padding border" id="touchLogining" hidden=true>
			<img src="<%=path%>/images/large-loading.gif" width="32" height="32"
				style="margin-right:8px;float:left;vertical-align:top;" /> <span
				id="loading-msg">正在登录...</span>
		</div>
    </div>
</body>

</html>
