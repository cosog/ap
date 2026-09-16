<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加用户</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 8px; background: #f5f5f5; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 4px 6px; vertical-align: middle; }
        .label { text-align: right; width: 110px; font-weight: bold; white-space: nowrap; }
        .mini-textbox, .mini-password, .mini-combobox { width: 100% !important; min-width: 100px; }
        .info-line { font-size: 14px; color: #333; padding-bottom: 8px; }
        .error-tip { color: red; font-size: 12px; margin-left: 4px; }
    </style>
</head>
<body>
    <div style="padding:8px;">
        <form id="userForm" class="mini-form">
            <table class="form-table">
                <tr>
                    <td colspan="2" class="info-line"><span id="orgInfo"></span></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserName"></span>：</td>
                    <td><input id="userName" class="mini-textbox"
                               onblur="checkUserName()" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserId"></span>：</td>
                    <td><input id="userId" class="mini-textbox"
                               onblur="checkUserId()" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserPwd"></span>：</td>
                    <td><input id="userPwd" class="mini-password"
                               value="123456"
                               onblur="checkUserPwd()" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserPwdAgain"></span>：</td>
                    <td><input id="userPwdAgain" class="mini-password"
                               value="123456"
                               onblur="checkUserPwdAgain()" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserType"></span>：</td>
                    <td><input id="userType" class="mini-combobox"
                               valueField="boxkey" textField="boxval"
                               allowInput="false"
                               onblur="checkUserType()" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblUserPhone"></span>：</td>
                    <td><input id="userPhone" class="mini-textbox"
                               onblur="checkUserPhone()" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblUserInEmail"></span>：</td>
                    <td><input id="userInEmail" class="mini-textbox"
                               onblur="checkUserInEmail()" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserQuickLogin"></span>：</td>
                    <td><input id="userQuickLogin" class="mini-radiobuttonlist" value="0" /></td>
                </tr>
                <tr id="rowReceiveSMS">
                    <td class="label"><span style="color:red;">*</span><span id="lblReceiveSMS"></span>：</td>
                    <td><input id="receiveSMS" class="mini-radiobuttonlist" value="0" /></td>
                </tr>
                <tr id="rowReceiveMail">
                    <td class="label"><span style="color:red;">*</span><span id="lblReceiveMail"></span>：</td>
                    <td><input id="receiveMail" class="mini-radiobuttonlist" value="0" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserEnable"></span>：</td>
                    <td><input id="userEnable" class="mini-radiobuttonlist" textField="text" valueField="id" value="1" /></td>
                </tr>
            </table>
            <div style="text-align:center;padding-top:16px;">
                <a class="mini-button" onclick="onSave()" style="width:80px;" id="btnSave"></a>
                <a class="mini-button" onclick="onCancel()" style="width:80px;margin-left:10px;" id="btnCancel"></a>
            </div>
        </form>
    </div>

    <script>
        var context = '<%=context%>';
        var orgId = '';
        var orgName = '';
        var language = 'zh_CN';
        var languageValue = 1;
        var emailEnable = false;

        // ================================================================
        // 正则（完全照搬 ExtJS）
        // ================================================================
        var REG_PHONE = /^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\d{8}$/;
        var REG_EMAIL = /^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/;

        // ================================================================
        // 国际化
        // ================================================================
        function initI18n() {
            var R = _loginUserLanguageResource;
            document.getElementById('lblUserName').textContent       = R.userName;
            document.getElementById('lblUserId').textContent         = R.userAccount;
            document.getElementById('lblUserPwd').textContent        = R.userPassword;
            document.getElementById('lblUserPwdAgain').textContent   = R.enterPasswordAgain;
            document.getElementById('lblUserType').textContent       = R.role;
            document.getElementById('lblUserPhone').textContent      = R.phone;
            document.getElementById('lblUserInEmail').textContent    = R.email;
            document.getElementById('lblUserQuickLogin').textContent = R.userQuickLogin;
            document.getElementById('lblReceiveSMS').textContent     = R.receiveAlarmSMS;
            document.getElementById('lblReceiveMail').textContent    = R.receiveAlarmMail;
            document.getElementById('lblUserEnable').textContent     = R.status;
            document.getElementById('btnSave').textContent           = R.save;
            document.getElementById('btnCancel').textContent         = R.cancel;
            document.title = R.addUser;

            var yesNo = [{ id: 1, text: R.yes }, { id: 0, text: R.no }];
            mini.get('userQuickLogin').setData(yesNo);
            mini.get('receiveSMS').setData(yesNo);
            mini.get('receiveMail').setData(yesNo);
            
            mini.get('userQuickLogin').setValue("0");
            mini.get('receiveSMS').setValue("0");
            mini.get('receiveMail').setValue("0");
            
            
            mini.get('userEnable').setData([
                { id: "1", text: R.enable },
                { id: "0", text: R.disable }
            ]);
            mini.get('userEnable').setValue("1");
        }

        // ================================================================
        // 由父窗口调用
        // ================================================================
        function setData(data) {
            orgId = data.orgId || '';
            orgName = data.orgName || '';
            language = data.language || 'zh_CN';
            languageValue = data.languageValue || 1;
            document.getElementById('orgInfo').innerHTML =
                _loginUserLanguageResource.owningOrg
                + "：【<font color='red'>" + orgName + "</font>】，"
                + _loginUserLanguageResource.pleaseConfirm;

            // 邮箱配置开关：控制短信/邮件接收 radio 是否显示
            emailEnable = (typeof _emailEnable !== 'undefined') ? _emailEnable : false;
            if (typeof emailEnable === 'string') {
                emailEnable = (emailEnable === 'true' || emailEnable === '1');
            }
            if (!emailEnable) {
                document.getElementById('rowReceiveSMS').style.display = 'none';
                document.getElementById('rowReceiveMail').style.display = 'none';
            }
        }

        // ================================================================
        // 加载角色列表
        // ================================================================
        function loadRoleList() {
            $.ajax({
                url: context + '/userManagerController/loadUserType',
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    var list = [];
                    if (data && data.length) {
                        for (var i = 0; i < data.length; i++) {
                            list.push({ boxkey: data[i].boxkey, boxval: data[i].boxval });
                        }
                    }
                    mini.get('userType').setData(list);
                }
            });
        }

        // ================================================================
        // 校验函数：全部由 onblur 触发，与服务端约束对齐
        // ================================================================

        // 用户名：非空
        function checkUserName() {
            var input = mini.get('userName');
            var v = (input.getValue() || '').trim();
            if (!v) {
                mini.alert(_loginUserLanguageResource.required,
                           _loginUserLanguageResource.tip);
                return false;
            }
            return true;
        }

        // 账号：非空 + 唯一性（对应 ExtJS 的 blur 里 judgeUserExistOrNot）
        function checkUserId() {
            var input = mini.get('userId');
            var v = (input.getValue() || '').trim();
            if (!v) {
                mini.alert(_loginUserLanguageResource.required,
                           _loginUserLanguageResource.tip);
                return false;
            }
            $.ajax({
                url: context + '/userManagerController/judgeUserExistOrNot',
                type: 'POST',
                data: { userId: v, userNo: '' },
                dataType: 'json',
                success: function (resp) {
                    if (resp && resp.msg == '1') {
                        var confirmMsg = '<font color=red>【'+ _loginUserLanguageResource.userAccount + ':' + v+ '】</font>'+ _loginUserLanguageResource.alreadyExist;
                        mini.confirm(confirmMsg, _loginUserLanguageResource.tip, function (action) {
                            if (action == 'ok') {
                            	input.focus();
                            	input.selectText();
                            }
                        });
                    }
                }
            });
            return true;
        }

        // 密码：非空
        function checkUserPwd() {
            var input = mini.get('userPwd');
            var v = (input.getValue() || '').trim();
            if (!v) {
                mini.confirm(_loginUserLanguageResource.required, _loginUserLanguageResource.tip, function (action) {
                    if (action == 'ok') {
                    	input.focus();
                    	input.selectText();
                    }
                });
                return false;
            }
            return true;
        }

        // 确认密码：非空 + 与上面一致（对应 ExtJS 的 vtype: "password"）
        function checkUserPwdAgain() {
            var pwd     = mini.get('userPwd').getValue() || '';
            var pwdAgain= mini.get('userPwdAgain').getValue() || '';
            if (!pwdAgain) {
                mini.confirm(_loginUserLanguageResource.required, _loginUserLanguageResource.tip, function (action) {
                    if (action == 'ok') {
                    	mini.get('userPwdAgain').focus();
                    	mini.get('userPwdAgain').selectText();
                    }
                });
                return false;
            }
            if (pwd !== pwdAgain) {
                mini.confirm(_loginUserLanguageResource.enterpwdNotEqual, _loginUserLanguageResource.tip, function (action) {
                    if (action == 'ok') {
                    	mini.get('userPwdAgain').focus();
                    	mini.get('userPwdAgain').selectText();
                    }
                });
                return false;
            }
            return true;
        }

        // 角色：非空（下拉默认无值）
        function checkUserType() {
            var v = mini.get('userType').getValue();
            if (v === '' || v === null || v === undefined) {
                mini.confirm(_loginUserLanguageResource.required, _loginUserLanguageResource.tip, function (action) {
                    if (action == 'ok') {
                    	mini.get('userType').focus();
                    	mini.get('userType').selectText();
                    }
                });
                return false;
            }
            return true;
        }

        // 电话：允许为空；有值时按正则校验
        function checkUserPhone() {
            var input = mini.get('userPhone');
            var v = (input.getValue() || '').trim();
            if (!v) return true;                    // 允许空
            if (!REG_PHONE.test(v)) {
                mini.confirm(_loginUserLanguageResource.phoneNumberFormatError, _loginUserLanguageResource.tip, function (action) {
                    if (action == 'ok') {
                    	input.focus();
                    	input.selectText();
                    }
                });
                return false;
            }
            return true;
        }

        // 邮箱：允许为空；有值时按正则校验
        function checkUserInEmail() {
            var input = mini.get('userInEmail');
            var v = (input.getValue() || '').trim();
            if (!v) return true;                    // 允许空
            if (!REG_EMAIL.test(v)) {
                mini.confirm(_loginUserLanguageResource.emailFormatError, _loginUserLanguageResource.tip, function (action) {
                    if (action == 'ok') {
                    	input.focus();
                    	input.selectText();
                    }
                });
                return false;
            }
            return true;
        }

        // ================================================================
        // 保存
        // ================================================================
        function onSave() {
            var R = _loginUserLanguageResource;

            // 逐项校验（顺序与界面一致）
            if (!checkUserName())     return;
            if (!checkUserPwd())      return;
            if (!checkUserPwdAgain()) return;
            if (!checkUserType())     return;
            if (!checkUserPhone())    return;
            if (!checkUserInEmail())  return;

            var userIdVal = (mini.get('userId').getValue() || '').trim();
            if (!userIdVal) {
                mini.alert(R.required, R.tip);
                return;
            }

            var mask = mini.mask({
                el: document.body,
                html: R.submittingData
            });

            $.ajax({
                url: context + '/userManagerController/doUserAdd',
                type: 'POST',
                data: {
                    'user.userOrgid':       orgId,
                    'user.userName':        mini.get('userName').getValue(),
                    'user.userId':          userIdVal,
                    'user.userPwd':         mini.get('userPwd').getValue(),
                    'user.userType':        mini.get('userType').getValue(),
                    'user.userPhone':       mini.get('userPhone').getValue(),
                    'user.userInEmail':     mini.get('userInEmail').getValue(),
                    'user.userQuickLogin':  mini.get('userQuickLogin').getValue(),
                    'user.receiveSMS':      emailEnable ? mini.get('receiveSMS').getValue() : '0',
                    'user.receiveMail':     emailEnable ? mini.get('receiveMail').getValue() : '0',
                    'user.userEnable':      mini.get('userEnable').getValue()
                },
                dataType: 'json',
                success: function (resp) {
                    mini.unmask(document.body);
                    if (resp && resp.msg === true) {
                        if (window._parentRefreshUserList) {
                            window._parentRefreshUserList();
                        }
                        // ★ 三参 mini.alert，避免直接关闭
                        mini.alert(R.addedSuccessfully, R.tip, function () {
                            window.CloseOwnerWindow('ok');
                        });
                    } else {
                        mini.alert('<font color="red">' + R.addFailure + '</font>', R.tip);
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(R.exceptionThrow + ': ' + R.contactAdmin, R.tip);
                }
            });
        }

        function onCancel() {
            window.CloseOwnerWindow('cancel');
        }

        $(document).ready(function () {
            mini.parse();
            initI18n();
            loadRoleList();
        });
    </script>
</body>
</html>