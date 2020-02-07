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
<meta charset="UTF-8">
<fmt:setBundle basename="config/messages"/>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="renderer" content="webkit">   <!--默认360极速模式-->
<meta http-equiv="Content-Type" content="text/html" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache,must-revalidate" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery.cookie.js"></script>
<!-- 项目名称，关联cosog\src\config\messages_zh_CN.properties -->
<title><fmt:message key="cosog.title" /></title>  
<!-- 链接外部图标，如：中石油、中石化 -->
<link rel="Bookmark" href="<%=path%>/images/logo/favicon.ico" />
<link rel="icon" href="<%=path%>/images/logo/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=path%>/images/logo/favicon.ico" type="image/x-icon" />
<!-- 链接css -->
<link rel="stylesheet" type="text/css" href="<%=path%>/styles/hytxlogin.css" >

<script>
    var titleFontSize = 26; // 标题字体大小
    var titleLetterSpacing = 8; // 标题字间距
    var logoImgWidth = logoImgHeight = 42; // logo图片的宽和高
    var currentImg = 0;
    var loginImages = ['<%=path%>/images/login/02.png', '<%=path%>/images/login/01.png']; // 登录图片数组
  //加载页面邦定事件
    $(function () {
        var loginTop = document.getElementById('loginTop'); // 获得引用
        var imgTop = document.getElementById('imgTop'); // 获得引用
        var loginImg = document.getElementById('loginImg'); // 获得引用
        var logoImg = document.getElementById('logoImg'); // 获得引用
        var imgTitle = document.getElementById('imgTitle'); // 获得引用
        var titleText = document.getElementById('titleText'); // 获得引用
        var loginInfo = document.getElementById('loginInfo'); // 获得引用
        var loginInfoFrom = document.getElementById('loginInfoFrom'); // 获得引用
        var loginInfoTable = document.getElementById('loginInfoTable'); // 获得引用

        $(window).resize(function () {
            var pageWidth = $(window).width(); // 屏幕宽度
            var pageHeight = $(window).height(); // 屏幕高度
            //判断横屏或竖屏
            if (pageWidth > pageHeight) {
                loginImg.style.flex = 2;
                loginImg.style.webkitFlex = 2;
                loginImg.style.mozFlex = 2;
                loginImg.style.oFlex = 2;
                loginImg.style.msFlex = 2;
            } else {
                loginImg.style.flex = 1;
                loginImg.style.webkitFlex = 1;
                loginImg.style.mozFlex = 1;
                loginImg.style.oFlex = 1;
                loginImg.style.msFlex = 1;
            }

            // 获取图片的伸缩比例
            var loginImgScale = loginImg.offsetHeight / loginImg.naturalHeight;
            var loginImgScaleW=loginImg.offsetWidth / loginImg.naturalWidth;

            // 获得伸缩后的图片宽度,若大于屏幕宽度取屏幕宽度
            loginImg.style.width = Math.min(loginImg.naturalWidth * loginImgScale, pageWidth) + 'px';

            // 获得图片标题的top、left定位
            //imgTitle.style.top = loginTop.offsetHeight + imgTop.clientHeight + loginImg.offsetHeight * 0.035 + 'px';
            //imgTitle.style.left = pageWidth * 0.5 - loginImg.offsetWidth * 0.46 + 'px';
            // 获得伸缩后的logo图片宽度、高度
           // logoImg.style.width = logoImgWidth * loginImgScale + 'px';
           // logoImg.style.height = logoImgHeight * loginImgScale + 'px';
            // 获得伸缩后的title字体大小、字间距
            //titleText.style.fontSize = titleFontSize * loginImgScale + 'px';
            //titleText.style.letterSpacing = titleLetterSpacing * loginImgScale + 'px';

            // 获取登录信息的top、left定位
            var scale=Math.min(loginImgScale,loginImgScaleW);
            
            var loginInfoTableWidth=200*scale;
            if(loginImg.offsetWidth*scale*0.15<200){
            	loginInfoTableWidth=loginImg.offsetWidth*scale*0.15;
            }
            
            loginInfo.style.width = loginInfoTableWidth + 'px';
            loginInfo.style.top = loginImg.offsetHeight*0.45 + 'px';
            loginInfo.style.left = loginImg.offsetWidth * 0.7+(pageWidth-loginImg.offsetWidth)/2 + 'px';
            loginInfoFrom.style.width = loginInfoTableWidth + 'px';
           	loginInfoTable.style.width = loginInfoTableWidth + 'px';
        })

        var set = setInterval(loadImg, 40); // 加载图片，单位：ms
        function loadImg() {
            $('<img/>') // 在内存中创建一个img标记
                .attr('src', $(loginImg).attr('src'))
                .load(function () {
                    if (this.width > 0 || this.height > 0) { // 如果图片已加载
                        clearInterval(set); // 停止加载
                        loginImg.style.visibility = 'visible'; // 图片显示
                        $(window).resize(); // 开始计算其他参数
                    }
                });
        }

       // setInterval('changeImg()', 3000); // 自动切换图片，单位：ms
    
 //focus事件
 var get_cook_name= $.cookie("username");   
 if(null!=get_cook_name&&get_cook_name!=""){
    $("#userId").val(get_cook_name);
    $("#userPwd").focus(); 
 }
 
 // name blur fn 
 $("#userId").bind("blur",function(){
  var $var=$(this);
  if(null==$var||$var==""){
   error_NameFn(); 
  }else{
   $("#errorName").html("");
  }
 });
 // pwd blur fn 
 $("#userPwd").bind("blur",function(){
  var $var=$(this);
  if(null==$var||$var==""){
   error_PwdFn();
  }else{
   $("#errorPwd").html("");
  }
 });
});
    
 function changeImg() {
     if (currentImg >= loginImages.length) {
         currentImg = 0;
     }
     loginImg.src = loginImages[currentImg];
     currentImg += 1;
 }

 // 登录
 function loginFn(){
   var get_name=$("#userId").val();
   var get_pwd=$("#userPwd").val();
   if(null!=get_name&&get_name!=""&&null!=get_pwd&&get_pwd!=""){
     $("#errorName").html("<font style='color:#666666'><fmt:message key='cosog.logining'/></font>");
     $("#errorPwd").html("");
     verifyFn();
   }else if(null==get_name||get_name==""){
     error_NameFn();
   }else if(null==get_pwd||get_pwd==""){
     error_PwdFn();
   } 
 }
 
 // 登录闭环报警
 function loginAlarmFn(){
   var get_name=$("#userId").val();
   var get_pwd=$("#userPwd").val();
   if(null!=get_name&&get_name!=""&&null!=get_pwd&&get_pwd!=""){
     $("#errorName").html("<font style='color:#666666'><fmt:message key='cosog.logining'/></font>");
     $("#errorPwd").html("");
     verifyAlarmFn();
   }else if(null==get_name||get_name==""){
     error_NameFn();
   }else if(null==get_pwd||get_pwd==""){
     error_PwdFn();
   } 
 }
 
 // 登录账号处理
 function error_NameFn(){
     $("#userId").focus();
     $("#errorName").html("<fmt:message key='cosog.entername'/>");
     return false;
 }
 
 // 登录密码处理
 function error_PwdFn(){
     $("#userPwd").focus();
     $("#errorPwd").html("<fmt:message key='cosog.enterpwd'/>"); 
     return false;
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
 
 // 真实登录进入闭环报警
 function verifyAlarmFn(){
	var getTime=new Date();
	var getSeconds=getTime.getMinutes()+getTime.getSeconds();
	var getLogin_=$("#userId").val();
   var userId_ = encodeURI(encodeURI(getLogin_));
	var userPwd_ = encodeURI(encodeURI($("#userPwd").val()));
	var paramObj ="?time="+getSeconds+"&userId=" + userId_ + "&userPwd=" + userPwd_;	
	var url="<%=path%>/userLoginManagerController/userLogin"+paramObj;
   //jquery Ajax请求事件
	$.ajax({
		url: url,
		cache:false,
		success:function(data){
		    username=getLogin_;
			callAlarmback(data,username);
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
		    $.cookie("username", username, { expires: 7 });
			window.location.href = "<%=path%>/login/toMain";

		} else if (obj.flag == "admin") {
			resultObj.html(obj.msg);
			window.location.href = "<%=path%>/login/toBackLogin";
		} else {
			resultObj.html(obj.msg);
		}
		return false;
	}
 
 // 登录闭环报警的返调函数处理
 function callAlarmback(data,username) {
       var obj;
		var resultObj = $("#errorName");
		if(data!=""||data!=null){
			 obj= eval("(" + data + ")");
		}else{
			window.location.href = "<%=path%>/errorinfo.html";
		} 
		if (obj.flag == "normal") {
		    $.cookie("username", username, { expires: 7 });
			window.location.href = "<%=path%>/app/alarm.jsp";
		} else if (obj.flag == "admin") {
			resultObj.html(obj.msg);
			window.location.href = "./AdminLogin.jsp";
		} else {
			resultObj.html(obj.msg);
		}
		return false;
	}

// 取消
function re_set() {
		$("#userId").val("");
		$("#userPwd").val("");
	}
	
$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			loginFn();
		}
	});
</script>
</head>
<body>
    <div id="loginBody">
        <img id="loginImg" src="<%=path%>/images/hytx/logo_login1.png"/>
        <!-- 推荐使用谷歌浏览器 
        <div id="imgBottom">
            <span><fmt:message key="cosog.advice"/></span>
            <a href="<%=path%>/download/chrome.rar"	target='_blank'><fmt:message key="cosog.download"/></a>
        </div>-->
        <!-- 登录信息 -->
        <div id="loginInfo">
        <form id="loginInfoFrom">
        <table id="loginInfoTable">
        	<tr>
        		<td><span><fmt:message key="cosog.username"/>：</span></td>
        		<td><input type="text" id="userId" class="input" value="<%=name%>"/></td>
        	</tr>
        	<tr>
        		<td><span><fmt:message key="cosog.password"/>：</span></td>
        		<td><input type="password" id="userPwd" class="input" value="<%=password%>" onkeypress="if(event.keyCode==13){loginFn();}"/></td>
        	</tr>
        	<tr>
        		<td colspan=2>
                	<button type="button" class="button" onclick="loginFn();"><span aria-hidden="true" data-icon="&#xf007;">&nbsp;&nbsp;<fmt:message key="cosog.login"/></span></button>
        			<button type="button" class="button" onclick="re_set();"><span aria-hidden="true" data-icon="&#xf00d;">&nbsp;&nbsp;<fmt:message key="cosog.cancel"/></span></button>
            	</td>
        	</tr>
        	<tr>
        		<td colspan=2>
                	<span id="errorName" class="errorMsg"></span>
            		<span id="errorPwd" class="errorMsg"></span>
            	</td>
        	</tr>
        	<tr>
        		<td colspan=2>
        			<span><fmt:message key="cosog.advice"/></span>
            		<a href="<%=path%>/download/chromeXP.rar"	target='_blank'><fmt:message key="cosog.downloadXP"/></a>
            		<a href="<%=path%>/download/chrome32.rar"	target='_blank'><fmt:message key="cosog.download32"/></a>
            		<a href="<%=path%>/download/chrome64.rar"	target='_blank'><fmt:message key="cosog.download64"/></a>
                </td>
        	</tr>
        </table>
        </form>
            
        </div>
    </div>
</body>
</html>
