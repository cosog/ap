<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改密码</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 6px 8px; vertical-align: middle; }
        .label { text-align: right; width: 110px; font-weight: bold; white-space: nowrap; }
        .mini-textbox, .mini-password { width: 100% !important; min-width: 100px; }
    </style>
</head>
<body>
    <div style="padding:6px;">
        <form id="pwdForm" class="mini-form">
            <table class="form-table">
                <tr>
                    <td class="label"><span id="lblUserName"></span>：</td>
                    <td><input id="userName" class="mini-textbox" enabled="false" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblUserId"></span>：</td>
                    <td><input id="userId" class="mini-textbox" enabled="false" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserPwd"></span>：</td>
                    <td><input id="userPwd" class="mini-password"
                               onblur="checkUserPwd()" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblUserPwdAgain"></span>：</td>
                    <td><input id="userPwdAgain" class="mini-password"
                               onblur="checkUserPwdAgain()" /></td>
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
        var userNo = '';
        var userName = '';
        var userId = '';

        // ================================================================
        // 国际化
        // ================================================================
        function initI18n() {
            var R = _loginUserLanguageResource;
            document.getElementById('lblUserName').textContent     = R.userName;
            document.getElementById('lblUserId').textContent       = R.userAccount;
            document.getElementById('lblUserPwd').textContent      = R.userPassword;
            document.getElementById('lblUserPwdAgain').textContent = R.enterPasswordAgain;
            document.getElementById('btnSave').textContent         = R.update;
            document.getElementById('btnCancel').textContent       = R.cancel;
            document.title = R.passwordReset;
        }

        // ================================================================
        // 由父窗口调用，填充数据
        // ================================================================
        function setData(data) {
            userNo   = data.userNo   || '';
            userName = data.userName || '';
            userId   = data.userId   || '';

            mini.get('userName').setValue(userName);
            mini.get('userId').setValue(userId);
        }

        // ================================================================
        // 校验
        // ================================================================
        function checkUserPwd() {
            var v = (mini.get('userPwd').getValue() || '').trim();
            if (!v) {
                mini.alert(_loginUserLanguageResource.required,
                           _loginUserLanguageResource.tip);
                return false;
            }
            return true;
        }

        function checkUserPwdAgain() {
            var pwd      = mini.get('userPwd').getValue() || '';
            var pwdAgain = mini.get('userPwdAgain').getValue() || '';
            if (!pwdAgain) {
                mini.alert(_loginUserLanguageResource.required,
                           _loginUserLanguageResource.tip);
                return false;
            }
            if (pwd !== pwdAgain) {
                mini.alert(_loginUserLanguageResource.enterpwdNotEqual,
                           _loginUserLanguageResource.tip);
                return false;
            }
            return true;
        }

        // ================================================================
        // 保存
        // ================================================================
        function onSave() {
            var R = _loginUserLanguageResource;
            if (!checkUserPwd()) return;
            if (!checkUserPwdAgain()) return;

            var pwd = mini.get('userPwd').getValue();

            var mask = mini.mask({
                el: document.body,
                html: R.submittingData
            });

            $.ajax({
                url: context + '/userManagerController/doUserEditPassword',
                type: 'POST',
                data: {
                    'user.userNo':   userNo,
                    'user.userName': userName,
                    'user.userId':   userId,
                    'user.userPwd':  pwd
                },
                dataType: 'json',
                success: function (resp) {
                    mini.unmask(document.body);
                    if (resp && (resp.flag === true || resp.msg === true)) {
                    	if (window._parentRefreshData) {
                            window._parentRefreshData();
                        }
                    	mini.alert(R.updateSuccessfully, R.tip, function () {
                            window.CloseOwnerWindow('ok');
                        });
                    } else {
                        mini.alert('<font color="red">' + R.updateFailed + '</font>',R.tip);
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
        });
    </script>
</body>
</html>