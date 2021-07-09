<%@page import="java.util.Date"%>
<%@page import="com.cosog.model.User"%>
<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

 <style type="text/css">
body {
	font: 12px/20px , "宋体", Arial, sans-serif, Verdana, Tahoma;
	padding: 0;
	margin: 0;
	height:100%;
}
.divider { clear:both; display:block; overflow:hidden; text-indent:-1000px; width:auto; height:1px; padding:8px 0 0 0; margin-top:10; border-style:dotted; border-width:0 0 1px 0;border-color: #B8D0D6; margin-bottom:40px;}
.div_title{color:#666666;}
</style>
<fmt:setBundle basename="config/messages"/>
<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/scripts/jquery/time-${browserLang}.js"></script>
<script type="text/javascript">
	$(function() {
		showCurrentTime();
	});
</script>
<%
String userLevel="";
String locale=(String)session.getAttribute("browserLang");
if   (locale.equalsIgnoreCase("zh_CN")){
      userLevel="系统管理员";
     User user=(User)session.getAttribute("userLogin");
      if(user.getUserType()==2){
    	 userLevel="区域管理员";
      }else if(user.getUserType()==3){
     	 userLevel="数据维护管理员";
       }
}else if(locale.equalsIgnoreCase("en")){
	    userLevel="The system administrator";
	     User user=(User)session.getAttribute("userLogin");
	      if(user.getUserType()==2){
	    	 userLevel="Regional Manager";
	      }else if(user.getUserType()==3){
	     	 userLevel="The data administrator";
	       }
}
%>
  <div style=" padding-left:60px;height:100%;">
  	<div style=" height:15%"></div>
      <div class="div_title" style=" color:#1E60AA;padding-buttom:15px; font-size:18px; font-weight:bold;"><fmt:message key="cosog.welcome"/>&nbsp;&nbsp;<span style="color:#FD8200"><fmt:message key="cosog.backAdmin"/></span></div>    
    <div class="divider"></div>
      			<div>
                 <div style="line-height:25px;float:left;width:25%; font-size:12px;"><div class="div_title"><fmt:message key="cosog.sysdate"/>：</div>
		<div id="time" style="color:#8FA6E7;"></div>
                 <div style="height:15px;"></div>
                 <div class="div_title"><fmt:message key="cosog.brower"/>：</div><div id="ie_th_ids" style="color:#8FA6E7;"></div>
                 <div style="height:15px;"></div>
                 <div class="div_title"><fmt:message key="cosog.browerJR"/>：</div><div id="le_th_ids" style="color:#8FA6E7;"></div>
                 </div>
                 <div style="line-height:25px;float:left;width:40%;font-size:12px;"><div class="div_title"><fmt:message key="cosog.loginLevel"/>：</div><div id="" style="color:#8FA6E7;"><%=userLevel %></div>
                 <div style="height:15px;"></div>
                 <div class="div_title"><fmt:message key="cosog.right"/>：</div><div id="" style="color:#8FA6E7;"><fmt:message key="cosog.componet"/>。</div>
                 <div style="height:15px;"></div>
                 <div class="div_title"><fmt:message key="cosog.loginIP"/>：</div>
		<div id="ie_th_ids" style="color:#8FA6E7;"><%=request.getRemoteAddr() %></div>
                 </div>
                 <div style="float:left;width:25%;line-height:25px;font-size:12px;">
                   <div id="sys_th_ids" style="width:100%;color:#8FA6E7;">
			                       <span style="color:#666;"><fmt:message key="cosog.version"/></span>：<fmt:message key="cosog.frameVersion"/>
			                       <div style="padding-left:35px;color:#999999;">
			                        <span id="1103_ids" style="color:#8FA6E7;">4.1.6v[<font style="color:red"><fmt:message key="cosog.new"/></font>]<br>
			                        </span>
			                        <span id="1030_ids" style="color:#8FA6E7;">2.3.24v<br>
			                        </span>
			                        <span id="1010_ids" style="color:#8FA6E7;">4.3.10v</span>
			                       </div>
                                   <div style="height:15px;"></div>
			                       <span style="color:#666;"><fmt:message key="cosog.frame"/></span>：<br><fmt:message key="cosog.frameDevelop"/>
                   </div>
                 </div>
    </div>
                 <br />
                 <div style="right:5%; bottom:5%;position:absolute;">
                 <img src="<%=path%>/images/bianqian.png" />
                 </div>
                 </div>
                 </div>
                 <script>
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
			.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
			.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
			.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
	//以下进行测试
	if (Sys.ie) {
		var sys_ie = Sys.ie;
		if (sys_ie < 7.0) {
			document.getElementById("ie_th_ids").innerHTML = 'IE~~ ' + Sys.ie;
			document.getElementById("le_th_ids").innerHTML = '<font style="color:red"><fmt:message key="cosog.bottom"/></font>';
		} else {
			document.getElementById("ie_th_ids").innerHTML = 'IE: ' + Sys.ie;
			document.getElementById("le_th_ids").innerHTML = '<font style="color:#8FA6E7"><fmt:message key="cosog.good"/></font>';
		}
	}

	if (Sys.firefox) {
		document.getElementById("ie_th_ids").innerHTML = 'Firefox: '
				+ Sys.firefox;
		document.getElementById("le_th_ids").innerHTML = '<font style="color:#8FA6E7"><fmt:message key="cosog.good"/></font>';
	}
	if (Sys.chrome) {
		var ll = document.getElementById("ie_th_ids");
		document.getElementById("ie_th_ids").innerHTML = 'Chrome: '
				+ Sys.chrome;
		document.getElementById("le_th_ids").innerHTML = '<font style="color:#8FA6E7"><fmt:message key="cosog.good"/></font>';
	}
	if (Sys.opera) {
		document.getElementById("ie_th_ids").innerHTML = 'Opera: ' + Sys.opera;
		document.getElementById("le_th_ids").innerHTML = '<font style="color:#8FA6E7"><fmt:message key="cosog.good"/></font>';
	}
	if (Sys.safari) {
		document.getElementById("ie_th_ids").innerHTML = 'Safari: '+ Sys.safari;
		document.getElementById("le_th_ids").innerHTML = '<font style="color:#8FA6E7"><fmt:message key="cosog.good"/></font>';
	}
	
</script>