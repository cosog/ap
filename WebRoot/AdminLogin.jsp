<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<fmt:setBundle basename="config/messages"/>
<title><fmt:message key="cosog.title"/></title>
<meta charset="UTF-8">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="renderer" content="webkit">   <!--默认360极速模式-->
<meta http-equiv="Content-Type" content="text/html" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache,must-revalidate" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery.cookie.js"></script>
<link rel="icon" href="<%=path%>/images/logo/favicon.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="<%=path%>/images/logo/favicon.ico" type="image/x-icon"/>
<link rel="Bookmark" href="<%=path%>/images/logo/favicon.ico">
<script type="text/javascript">
	$(function() {
		var imgCode = $("#imgCode");
		changeValidateCode(imgCode);
	});
	function changeValidateCode(obj) {
		/*** 
		 *   获取当前的时间作为参数，无具体意义    
		 *   每次请求需要一个不同的参数，否则可能会返回同样的验证码     
		 *   这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
		 */
		var timenow = new Date().getTime();
		obj.src = "<%=path%>/randPic?d=" + timenow;
	}
	function adminLoginVerify() {
		var getTime=new Date();
		var getSeconds=getTime.getMinutes()+getTime.getSeconds();
		var userId_ = encodeURI(encodeURI($("#userId").val()));
		var userPwd_ = encodeURI(encodeURI($("#userPwd").val()));
		var imgCode_ = encodeURI(encodeURI($("#imgCode").val()));
		var paramObj = "?time="+getSeconds+"&userId=" + userId_ + "&userPwd=" + userPwd_ + "&imgCode="+ imgCode_;	
		var url="<%=path%>/userLoginManagerController/adminLogin"+paramObj;
		$.ajax({
		url: url,
		cache:false,
		success:function(data){
			adminCallback(data);
		}
	});
	}
	var adminre_set = function() {
		$("#userId").val("");
		$("#userPwd").val("");
		$("#imgCode").val("");

	}
	function adminCallback(data) {
		var resultObj = $("#result");
		var obj = eval("(" + data + ")");
		if (obj.flag == "admin") {
			window.location.href = "<%=path%>/login/toBackMain";
		} else if (obj.flag == "normal") {
			// alert(obj.msg);
			resultObj.html(obj.msg);
			window.location.href = "<%=path%>/ToLogin.jsp";
		} else {
			// alert(obj.msg);
			resultObj.html(obj.msg);
		}
	}
</script>
<link rel="stylesheet" href="<%=path%>/styles/adminLogin.css" type="text/css"></link>
</head>
<body width="100%" leftmargin="0" topmargin="0" marginwidth="0" onload="document.getElementById('userId').focus()"
	marginheight="0">
	<form action="" method="post" name="form1">
		<div class="hbox">
		    <img src="<%=path%>/images/logo/backtitle_name0.jpg" />
		    <div id="imgTitleAdmin">
		        <img id="logoImgAdmin" src="<%=path%>/images/logo/ytlogo.png" />
		        <span id="titleTextAdmin"><fmt:message key="cosog.backtitle"/></span>
		    </div>
		    <img class="fit" src="<%=path%>/images/C411.gif" />
		    <img src="<%=path%>/images/C222.jpg" />
		</div>		
		<div class="BG"
			style=" width:100%;height:425px;background-image: url(<%=path%>/images/AdminBG.gif); background-repeat:repeat-x;">
			<div align="center">
				<table>
					<tr>
						<td class="td">&nbsp;</td>
					</tr>
					<tr>
						<td class="td">&nbsp;</td>
					</tr>
				</table>
			</div>
			<table width=1250 height="332" align="center" cellspacing="0"
				background="<%=path%>/images/enterback1.png"
				style="background-repeat:no-repeat;">
				<tr>
					<td></td>
					<td
						style="background-image:url(<%=path%>/images/yonghu.png); background-repeat:no-repeat;background-position:right;"></td>
					<td style="font-size:18px; font-weight:bold; color:#FFFFFF;"><fmt:message key="cosog.userLogin"/></td>
				</tr>
				<tr>
					<td width="30%" height="32">&nbsp;</td>
					<td width="30%" align="right" valign="middle"
						style=" font-size:13px;color:#214A98"><fmt:message key="cosog.username"/></a>：</td>
					<td colspan="2"><input type="text" value='' name="userId" id="userId">
						</font></td>
				</tr>
				<tr>
					<td height="32">&nbsp;</td>
					<td height="32" align="right" valign="middle"
						style="font-size:13px;color:#214A98"><fmt:message key="cosog.password"/>：</td>
					<td colspan="2"><input type="password" value='' name="userPwd"
						id="userPwd" size="20" /></td>
				</tr>
				<tr>
					<td height="32">&nbsp;</td>
					<td height="32" align="right" valign="middle"
						style="font-size:13px;color:#214A98"><fmt:message key="cosog.imgCode"/>：</td>
					<td colspan="2"><input type="text"  id="imgCode" name="imgCode"
						size="9" id="imgCode"
						onKeyPress="if(event.keyCode==13) {adminLoginVerify();}" /> <img
						src="<%=path%>/randPic"
						onclick="changeValidateCode(this)"
						onMouseOver="this.style.cursor='pointer'"  
							title="<fmt:message key='cosog.changeImg'/>"
							width="46" height="15" />
					</td>
				</tr>
				<tr>
					<td height="32">&nbsp;</td>
					<td height="32" align="left" valign="middle">&nbsp;</td>
					<td colspan="2"><table width="208">
							<tr>
								<td class="td"><div align="left">
										<font size="2"> <img src="<%=path%>/images/DENGLU1.png"
											width="45" height="20" border="0"
											onclick="adminLoginVerify();"
											onMouseOver="this.style.cursor='hand'">&nbsp;&nbsp; <img
											src="<%=path%>/images/chongxie1.png" width="45" height="20"
											onClick="adminre_set();"
											onMouseOver="this.style.cursor='pointer'"> </font>
									</div></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td></td>
					<td height="102" colspan="2">&nbsp;<font color="blue"><div
								align="center" id="result"></div></font>
					</td>
				</tr>
			</table>
		</div>
		<table width="100%">
			<tr>
				<td width="100%" id="mao"
					style="align:center;font-size:13px; text-align:center;height: 40px;
	background-image: url(<%=path%>/images/Adminbg-d.gif);background-repeat: repeat-x;text-align: center;color: #999999;font-size: 12px;line-height:20px">
	                     <fmt:message key="main.copy"/>&nbsp;&nbsp;<a href='http://www.cosogoil.com/' target='_blank'><fmt:message key="main.cosog"/></a> 
	</td>
			</tr>
		</table>
	</form>
</body>
</html>
