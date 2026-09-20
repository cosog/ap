<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .form-container { flex:1; overflow:auto; padding:10px; }
        .form-table { width:100%; border-collapse:collapse; }
        .form-table td { padding:6px; vertical-align:middle; }
        .form-table .label { text-align:right; width:80px; font-weight:bold; white-space:nowrap; }
        .form-table .mini-textbox { width:100% !important; }
        .footer { flex-shrink:0; padding:8px 10px; border-top:1px solid #e8e8e8; background:#fafafa; text-align:right; }
    </style>
</head>
<body>

<!-- 全局变量接收参数 -->
<script>
    var orgId = '';
    var orgName = '';
</script>

<div class="main-container">
    <div class="form-container">
        <div id="orgInfoLabel" style="font-size:13px;color:#333;padding-bottom:8px;"></div>
        <table class="form-table">
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblAccount"></span>：</td>
                <td><input id="account" class="mini-textbox" required="true" onblur="checkAccount()" /></td>
            </tr>
            <tr>
                <td class="label"><span style="color:red;">*</span>appKey：</td>
                <td><input id="appKey" class="mini-textbox" required="true" /></td>
            </tr>
            <tr>
                <td class="label"><span style="color:red;">*</span>secret：</td>
                <td><input id="secret" class="mini-textbox" required="true" /></td>
            </tr>
        </table>
    </div>
    <div class="footer">
        <button id="btnSave" class="mini-button" iconCls="save" onclick="onSave()"></button>
        <button id="btnCancel" class="mini-button" iconCls="cancel" onclick="onCancel()"></button>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var _accountValid = true;   // 默认允许（用户还没输入时不做校验）

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.addVideoKey;
        document.getElementById('lblAccount').textContent = R.name;
        mini.get('btnSave').setText(R.save);
        mini.get('btnCancel').setText(R.cancel);
    }

    // ================================================================
    // 父窗口调用：接收参数
    // ================================================================
    function setData(data) {
        if (!data) data = {};
        orgId = data.orgId || '';
        orgName = data.orgName || '';

        document.getElementById('orgInfoLabel').innerHTML =
            _loginUserLanguageResource.targetOrg + "：【<font color='red'>" + orgName + "</font>】，" + _loginUserLanguageResource.pleaseConfirm;
    }

    // ================================================================
    // 校验：名称是否已存在
    // ================================================================
    function checkAccount() {
        var value = (mini.get('account').getValue() || '').trim();
        if (!value) {
            _accountValid = false;
            return;
        }

        $.ajax({
            url: context + '/wellInformationManagerController/judgeVideoKeyExistOrNot',
            type: 'POST',
            async: false,
            data: { account: value },
            dataType: 'json',
            success: function (resp) {
                if (resp && resp.msg == '1') {
                    var input = mini.get('account');
                    var confirmMsg = '<font color="red">【' + value + ' ' + _loginUserLanguageResource.alreadyExist + '】</font>，' + _loginUserLanguageResource.pleaseConfirm;
                    mini.confirm(confirmMsg, _loginUserLanguageResource.tip, function (action) {
                        if (action == 'ok') {
                            input.focus();
                            input.selectText();
                        }
                    });
                    _accountValid = false;
                } else {
                    _accountValid = true;
                }
            },
            error: function () { _accountValid = false; }
        });
    }

    // ================================================================
    // 保存
    // ================================================================
    function onSave() {
        var R = _loginUserLanguageResource;

        var account = (mini.get('account').getValue() || '').trim();
        if (!account) {
            mini.get('account').focus();
            mini.alert(R.required, R.tip);
            return;
        }
        // 再次校验（防止用户修改后未触发 blur）
        checkAccount();
        if (!_accountValid) return;
        
        var appKey = (mini.get('appKey').getValue() || '').trim();
        if (!appKey) {
            mini.get('appKey').focus();
            mini.alert(R.required, R.tip);
            return;
        }
        var secret = (mini.get('secret').getValue() || '').trim();
        if (!secret) {
            mini.get('secret').focus();
            mini.alert(R.required, R.tip);
            return;
        }
        mini.mask({ el: document.body, cls: 'mini-mask-loading', html: R.submittingData });
		
        $.ajax({
            url: context + '/wellInformationManagerController/doVideoKeyAdd',
            type: 'POST',
            data: {
            	orgId: orgId,
            	account: account,
            	appkey: appKey,
            	secret: secret
            },
            dataType: 'json',
            success: function (resp) {
                mini.unmask(document.body);
                if (resp && resp.success === true) {
                    if (window._parentRefreshVideoKeyList) {
                        window._parentRefreshVideoKeyList();
                    }
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
    });
</script>
</body>
</html>