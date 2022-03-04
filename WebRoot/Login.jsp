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
	String viewProjectName=(String)session.getAttribute("viewProjectName");
	boolean showLogo=(boolean)session.getAttribute("showLogo");
	try{
		Cookie[] cookies=request.getCookies();
		System.out.println(cookies.length);
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				System.out.println(cookies[i].getName());
				if("cookieuser".equals(cookies[i].getName()+":"+cookies[i].getValue())){
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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <!--<fmt:setBundle basename="config/messages" />-->
    <script type="text/javascript" src="<%=path%>/app/locale.js?timestamp=202002160840"></script>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui">
    <meta name="renderer" content="webkit">
    <!--默认360极速模式-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache,must-revalidate" />
    <meta http-equiv="expires" content="0" />
    <title><%=viewProjectName%></title>
    <!-- 链接外部图标，如：中石油、中石化 -->
    <%if(showLogo){ %>
	<link rel="Bookmark" href="<%=path%>/images/logo/favicon.ico?timestamp=202202231815" />
	<link rel="icon" href="<%=path%>/images/logo/favicon.ico?timestamp=202202231815" type="image/x-icon" />
	<link rel="shortcut icon" href="<%=path%>/images/logo/favicon.ico?timestamp=202202231815" type="image/x-icon" />
	<%} %>
    <!-- 链接css -->
    <link rel="stylesheet" href="<%=path%>/scripts/bootstrap/css/bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="<%=path%>/scripts/bootstrap/css/bootstrap-select.min.css" type="text/css" />
    <link rel="stylesheet" href="<%=path%>/scripts/bootstrap/css/site.css" type="text/css" />
    <!--<link rel="stylesheet" href="<%=path%>/scripts/bootstrap/css/login2.css" type="text/css" /> -->
    <link rel="stylesheet" href="<%=path%>/styles/login.css?timestamp=202111252128" type="text/css"/>

    <script type="text/javascript" src="<%=path%>/scripts/jquery/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="<%=path%>/scripts/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=path%>/scripts/bootstrap/js/bootstrap-select.min.js"></script>
    

	<script>
	//document.getElementById("text111111").innerHTML=cosog.string.title;
	//$("#text111111").html("<span>aaaa</span>");
	var viewInformation = ${configFile}.viewInformation;
	$(function () {
		initDisplayInformation();
		var getUserListURL="<%=path%>/userLoginManagerController/getUserList";
		$.ajax({
			url: getUserListURL,
			cache:false,
			success:function(data){
				//alert(data);
				var optionString="<option value=\'\' data-password=\'\'>我自己</option>";
				optionString+="<option data-divider='true'></option>";
				for(var i=0;i<data.length;i++){
					optionString += "<option value=\'"+ data[i].useraccount +"\' data-password=\'"+data[i].userpwd+"\'>" + data[i].username + "</option>";
				}
				var userSelectpicker=document.getElementById("userSelectpicker");
				$("#userSelectpicker").html(optionString);
				$("#userSelectpicker").selectpicker('refresh');
			}
		});
	});
	
	function initDisplayInformation(){
		
		$("#login_userlogin").html(cosog.string.userlogin);
		$("#login_loginInfo").html(viewInformation.profile);
		$("#login_title").html(viewInformation.title);
		$("#login_myself").html(cosog.string.myself);
		$("#userId").attr("placeholder", cosog.string.entername);
		$("#userPwd").attr("placeholder", cosog.string.enterpwd);
		$("#login_rememberpassword").html(cosog.string.rememberpassword);
		$("#login_forgerpassword").html(cosog.string.forgerpassword);
		$("#login_contact").html(cosog.string.contact);
		$("#login_loginButton").html(cosog.string.login);
		$("#login_copy").html(viewInformation.copy);
		$("#login_link").text(viewInformation.linkshow);
		$('#login_link').attr('href',viewInformation.linkaddress);
	}
	
	function userSelectpickerChange(obj){
		//alert(obj[obj.selectedIndex].value);
		//alert(obj[obj.selectedIndex].dataset.password);
		if(obj[obj.selectedIndex].value!=""){
			verifyAutoFn(obj[obj.selectedIndex].value,obj[obj.selectedIndex].dataset.password);
		}else{
			$("#userId").focus();
		}
		
	}
	// 登录
	function loginFn(){
		var get_name=$("#userId").val();
		var get_pwd=$("#userPwd").val();
		if(null!=get_name&&get_name!=""&&null!=get_pwd&&get_pwd!=""){
			$("#errorName").html("<font style='color:#666666'>"+cosog.string.logining+"</font>");
			$("#errorPwd").html("");
			verifyFn();
		}else if(null==get_name||get_name==""){
			error_NameFn();
		}else if(null==get_pwd||get_pwd==""){
			error_PwdFn();
	   	} 
	 }
	
	username="";
	// 真实登录进入前台
	function verifyFn(){
		var getTime=new Date();
		var getSeconds=getTime.getMinutes()+getTime.getSeconds();
		var getLogin_=$("#userId").val();
	   	var userId_ = encodeURI(encodeURI(getLogin_));
		var userPwd_ = encodeURI(encodeURI($("#userPwd").val()));
		var flag_=0;
		if($("#flag").prop("checked")){
			flag_=1;
		}
		var paramObj ="?&time="+getSeconds+"&userId=" + userId_ + "&userPwd=" + userPwd_+"&flag=" + flag_;	
		var url="<%=path%>/userLoginManagerController/userLogin"+paramObj;
	   // jquery Ajax请求事件
		$.ajax({
			url: url,
			cache:false,
			success:function(data){
			    username=getLogin_;
				callback(data,username);
			}
		});
		return false;
	 };
	 //自动登录
	 function verifyAutoFn(selectUsername,selectUserpwd){
		 	$("#errorName").html("<font style='color:#666666'>"+cosog.string.logining+"</font>");
			$("#errorPwd").html("");
			var getTime=new Date();
			var getSeconds=getTime.getMinutes()+getTime.getSeconds();
		   	var userId_ = encodeURI(encodeURI(selectUsername));
			var userPwd_ = encodeURI(encodeURI(selectUserpwd));
			var flag_=0;
			var autoLogin=1;
			//if($("#flag").prop("checked")){
			//	flag_=1;
			//}
			var paramObj ="?&time="+getSeconds+"&userId=" + userId_ + "&userPwd=" + userPwd_+"&flag=" + flag_+"&autoLogin="+autoLogin;	
			var url="<%=path%>/userLoginManagerController/userLogin"+paramObj;
		   // jquery Ajax请求事件
			$.ajax({
				url: url,
				cache:false,
				success:function(data){
				    username=selectUsername;
					callback(data,username);
				}
			});
			return false;
	};
	 
	// 登录前台的返调函数处理
	 function callback(data,username) {
		var obj;
		var resultObj = $("#errorName");
		if(data!=""||data!=null){
			obj= eval("(" + data + ")");
		}else{
			window.location.href = "<%=path%>/errorinfo.html";
		}
		if (obj.flag == "normal") {
			//$.cookie("username", username, { expires: 7 });
			window.location.href = "<%=path%>/login/toMain";
		} else if (obj.flag == "admin") {
			resultObj.html(obj.msg);
			window.location.href = "<%=path%>/login/toBackLogin";
		} else {
			resultObj.html(obj.msg);
		}
		return false;
	}
	// 登录账号处理
	 function error_NameFn(){
	     $("#userId").focus();
	     $("#errorName").html(cosog.string.entername);
	     return false;
	 }
	 
	 // 登录密码处理
	 function error_PwdFn(){
	     $("#userPwd").focus();
	     $("#errorName").html(cosog.string.enterpwd); 
	     return false;
	 }
	</script>
</head>

<body class="page-login layout-full page-dark">
    <div class="page height-full">
        <div class="page-content height-full">
            <div class="page-brand-info vertical-align animation-slide-left hidden-xs">
                <div class="page-brand vertical-align-middle">
                    <ul class="list-icons hidden-sm" style="width:52%;float:right;line-height: 45px;">
                        <li style="text-indent:35px" >
                        	  <span  id= "login_loginInfo"></span>
                        </li>
                    </ul>
                    <div class="hidden-sm">

                    </div>
                </div>
            </div>
            <div class="page-login-main animation-fade">
            <!--  
            	<div class="alert alert-danger alert-dismissible" role="alert">
					用户名或密码错误！
            	</div>
            	-->
                <div class="vertical-align">
                    <div class="vertical-align-middle">
                        <div class="visible-xs text-center">
                            Agile
                        </div>
                        <h3 class="hidden-xs" style="text-align:left;margin-bottom:20px;font-size:22px;color:#009900"><span  id= "login_userlogin"></span> </h3>
                        <h4 class="hidden-xs" style="text-align:left;margin-bottom:0px;font-size:17px;"><span  id= "login_title"></span></h4>
                        <!-- <p class="hidden-xs"></p> -->
                        <form id="frmlogin" class="login-form fv-form fv-form-bootstrap" method="post">
							<div class="form-group " >
								<select  class="selectpicker form-control"  title="我自己" id="userSelectpicker" onchange="userSelectpickerChange(this)">
									<option ><span  id= "login_myself"></span></option>
								</select>
							</div>
                            <div class="form-group">
                                <input type="text" id="userId" class="form-control"  required="" value="<%=name%>">
                            </div>
                            <div class="form-group">
                                <input type="password" id="userPwd" class="form-control" required="" value="<%=password%>" onkeypress="if(event.keyCode==13){loginFn();}">
                            </div>
                            <div class="form-group clearfix">
        						<div class="checkbox-custom checkbox-inline checkbox-primary pull-left">
            						<input type="checkbox" id="flag" name="flag"
            						<%if(flag!=null && flag.equals("1")) 
					       				{ %> checked="checked"<%; %>value="1" <% ;%> <%}
					  				else { %> value="0"<%;} %>>
            						<label for="remember" id="login_rememberpassword"></label>
        						</div>
        						<a class="pull-right collapsed" data-toggle="collapse" href="#forgetPassword" aria-expanded="false" aria-controls="forgetPassword" id="login_forgerpassword">
        						</a>
    						</div>
    						<div class="collapse" id="forgetPassword" aria-expanded="false" style="height: 0px;">
                            	<div class="alert alert-warning alert-dismissible" role="alert" id="login_contact">
                            	</div>
                        	</div>
                            <button type="button" class="btn btn-primary btn-block margin-top-30" id="login_loginButton" onclick="loginFn();"></button>
                            <span id="errorName" class="errorMsg"></span>
                        </form>
                    </div>
                </div>
                <footer class="page-copyright">
                    <p><span id="login_copy"></span><a target='_blank' id='login_link'></a></p>
                </footer>
            </div>
        </div>
    </div>

</body>

</html>
