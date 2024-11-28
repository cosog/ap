// AJAX异常抛出
Ext.Ajax.on("requestexception", function(conn, response, options, eOpts) {

	// 系统状态
	var httpStatus = response.status;
	var httpError = response.requestId;
	Ext.MessageBox.msgButtons['ok'].text ="<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"+context+"/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
	switch (httpStatus) {
		case 400 :
			xxErrorMsg("400", loginUserLanguageResource.ajaxError400);
			break;
		case 404 :
			if (httpError == 5) {
				xxErrorMsg("session", loginUserLanguageResource.sessionTimeoutInfo);
			} else {
				xxErrorMsg("404", loginUserLanguageResource.ajaxError404);
			}
			break;
		case 500 :
			xxErrorMsg("500", loginUserLanguageResource.ajaxError500);
			break;
		case 505 :
			xxErrorMsg("505", loginUserLanguageResource.ajaxError505);
			break;
		case 888 :
			Ext.MessageBox.show({
				title : loginUserLanguageResource.tip,
				msg : "[<font color='red' font-weight=bold;>"+loginUserLanguageResource.unauthorized+"</font>]"+loginUserLanguageResource.contactSupplier,
				icon : Ext.MessageBox.WARNING,
				buttons : Ext.Msg.OK,
				fn : function() {
					window.location.href = context + "/error.jsp";
				}
			});
			break;
		case 999 :
			Ext.MessageBox.show({
				title : loginUserLanguageResource.tip,
				msg : "[<font color='red' font-weight=bold;>"+loginUserLanguageResource.sessionTimeoutInfo+"</font>]",
				icon : Ext.MessageBox.WARNING,
				buttons : Ext.Msg.OK,
				fn : function() {
					window.location.href = "toLogin";
				}
			});
			break;
	}
	// 清空回调函数
	options.failure = Ext.emptyFn;
});
xxErrorMsg = function(code, msg) {
	Ext.MessageBox.msgButtons['ok'].text ="<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'"+context+"/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
	Ext.MessageBox.show({
				title : loginUserLanguageResource.tip,
				msg : "<font style='color:red'>["+loginUserLanguageResource.exceptionCode+" " + code + "]</font> ，"
						+ msg,
				icon : Ext.MessageBox.ERRO,
				buttons : Ext.Msg.OK,
				fn : function(btn) {
					if (btn == 'ok') {
						if (code == "session") {
							window.location.href = "toLogin";
						}
					}
				}
			});

	return false;
}
// AJAX成功回调处理机智
Ext.Ajax.on("requestcomplete", function(conn, response, options, eOpts) {
	// 系统状态
	var httpStatus = response.status;
	// 上传方式
	var xmlUrl = response.responseXML;
	if (httpStatus != undefined && httpStatus != "undefined"
			&& httpStatus != "null") {
		// 系统异常
		if (httpStatus == 200) {
			if (xmlUrl == null || xmlUrl == undefined) {
				var errorOut = response.getResponseHeader('errorOut');
				if (errorOut != undefined && errorOut != "undefined"
						&& errorOut != "null") {
					var httpText = response.responseText;
					var message_ = null;
					try {
						// 转为JSON格式
						message_ = Ext.JSON.decode(httpText);
						// 处理返回的结果信息
						var f_message = message_.success;
						if (f_message != undefined && f_message != "undefined"
								&& f_message != "null") {
							if (!f_message || f_message == "false") {

								// 清空回调函数
								options.success = Ext.emptyFn;
								// 取消信息框
								Ext.MessageBox.close();
								// 错误弹出
								ajaxError(message_.title, message_.code,
										message_.msg, message_.error);
							}
						}
					} catch (e) {
						Ext.Msg.alert(loginUserLanguageResource.tip,""+loginUserLanguageResource.jsonException+" <font color=red>"+loginUserLanguageResource.jsonExceptionInfo+"</font>");
					}
				}
			}
			// 错误处理
		} else {
			ajaxStatus(httpStatus);
			// 清空回调函数
			options.failure = Ext.emptyFn;
			options.success = Ext.emptyFn;
		}
	}
	return false;
});
// 消息说明
ajaxError = function(title, code, msg, error) {

	// 异常描述
	var error_1s = Ext.create("Ext.form.DisplayField", {
				id : "error_1s",
				fieldLabel : '<font color="red">*</font>'+loginUserLanguageResource.exceptionDescription,
				width : "100%",
				labelWidth : 70,
				value : "<div style='color:red'>" + title + "</div>"
			});
	// 异常编码
	var error_2s = Ext.create("Ext.form.DisplayField", {
				id : "error_2s",
				fieldLabel : '<font color="red">*</font>异常编码',
				width : "100%",
				value : "<div style='color:red'>" + code + "</div>",
				labelWidth : 70
			});

	// 报错代码
	var error_3s = Ext.create("Ext.form.DisplayField", {
				id : "error_3s",
				fieldLabel : '<font color="red">*</font>'+loginUserLanguageResource.errotCode,
				width : "100%",
				labelWidth : 70,
				value : "<div style='color:red'>" + msg + "</div>"
			});
	// 备注
	var error_remackIds = Ext.create("Ext.form.TextArea", {
				id : "error_remackIds",
				anchor : '100% -47',
				value : error
			});
	// 表单组件
	var fromerrorpanel = Ext.create("Ext.form.Panel", {
				id : "fromerrorpanelId",
				layout : 'auto',
				border : false,
				labelSeparator : ':',
				autoWidth : 'auto',
				bodyStyle : 'padding:15px;',
				defaults : {
					width : 320
				},
				anchor : '100% 44%',
				items : [error_1s, {
							xtype : 'container',
							height : 7
						}, error_2s, {
							xtype : 'container',
							height : 7
						}, error_3s, {
							xtype : 'container',
							height : 1
						}]
			});
	var win2 = Ext.create('widget.window', {
				id : "errorWinId",
				title : loginUserLanguageResource.exceptionTip,
				width : 490,
				height : 290,
				maximizable : true,
				layout : 'anchor',
				closeAction : 'destroy',
				plain : true,
				modal : true,
				bodyStyle : 'background-color:#ffffff;',
				items : [fromerrorpanel, {
							xtype : 'fieldset',
							title : loginUserLanguageResource.detailedInformation,
							collapsible : true,
							collapsed : false,
							anchor : '100% -47',
							items : [error_remackIds]
						}],
				buttons : [{
							id : "errorasBtnId",
							text : loginUserLanguageResource.close,
							handler : function() {
								Ext.getCmp("errorWinId").close();
							}
						}]

			});
	win2.show();
	return false;
};

// 请求的系统异常处理   
ajaxStatus = function(httpStatus) {
	switch (httpStatus) {
		case 400 :
			ajaxError("400", loginUserLanguageResource.ajaxError400);
		case 404 :
			ajaxError("404", loginUserLanguageResource.ajaxError404);
		case 500 :
			ajaxError("500", loginUserLanguageResource.ajaxError500);
	}
	return false;
};