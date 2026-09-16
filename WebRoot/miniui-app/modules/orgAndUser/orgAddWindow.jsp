<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加组织</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .mini-form { width: 100%; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 6px 8px; vertical-align: middle; }
        .label { text-align: right; width: 110px; font-weight: bold; white-space: nowrap; }
        .mini-textbox, .mini-spinner { width: 100% !important; min-width: 100px; }
        .info-line { font-size: 14px; color: #333; padding-bottom: 10px; }
    </style>
</head>
<body>
    <div style="padding:10px;">
        <form id="orgForm" class="mini-form">
            <table class="form-table">
                <tr>
                    <td colspan="2" class="info-line">
                        <span id="orgParentInfo"></span>
                    </td>
                </tr>
                <tr id="rowZhCN">
                    <td class="label">
                        <span style="color:red;">*</span><span id="lblOrgNameZhCN"></span>：
                    </td>
                    <td><input id="orgName_zh_CN" class="mini-textbox" /></td>
                </tr>
                <tr id="rowEn">
                    <td class="label">
                        <span style="color:red;">*</span><span id="lblOrgNameEn"></span>：
                    </td>
                    <td><input id="orgName_en" class="mini-textbox" /></td>
                </tr>
                <tr id="rowRu">
                    <td class="label">
                        <span style="color:red;">*</span><span id="lblOrgNameRu"></span>：
                    </td>
                    <td><input id="orgName_ru" class="mini-textbox" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblSort"></span>：</td>
                    <td><input id="orgSeq" class="mini-spinner" minValue="1" value="1" /></td>
                </tr>
            </table>
            <div style="text-align:center;padding-top:20px;">
                <a class="mini-button" onclick="onSave()" style="width:80px;" id="btnSave"></a>
                <a class="mini-button" onclick="onCancel()" style="width:80px;margin-left:10px;" id="btnCancel"></a>
            </div>
        </form>
    </div>

    <script>
        var context = '<%=context%>';
        var orgParentId = '';
        var orgParentName = '';
        var language = _loginUserLanguage;
        var languageValue = _loginUserLanguageValue;

        function initI18n() {
            document.getElementById('lblOrgNameZhCN').textContent = _loginUserLanguageResource.orgName ;
            document.getElementById('lblOrgNameEn').textContent = _loginUserLanguageResource.orgName;
            document.getElementById('lblOrgNameRu').textContent = _loginUserLanguageResource.orgName ;
            document.getElementById('lblSort').textContent = _loginUserLanguageResource.sequenceNumber;
            
            var btn = mini.get('btnSave');
            if (btn) btn.setText(_loginUserLanguageResource.save);
            var btn = mini.get('btnCancel');
            if (btn) btn.setText(_loginUserLanguageResource.cancel);
            
            document.title = _loginUserLanguageResource.addOrg;
        }

        // 由父窗口调用
        function setData(data) {
            orgParentId = data.orgParentId || '';
            orgParentName = data.orgParentName || '';

            //document.getElementById('orgParentInfo').innerHTML =_loginUserLanguageResource.owningOrg+ "：【<font color='red'>" + orgParentName + "</font>】，"+ _loginUserLanguageResource.pleaseConfirm;

            // 依据当前语言决定哪一列可见
            var langUpper = (language || '').toUpperCase();
            document.getElementById('rowZhCN').style.display = (langUpper === 'ZH_CN') ? '' : 'none';
            document.getElementById('rowEn').style.display = (langUpper === 'EN') ? '' : 'none';
            document.getElementById('rowRu').style.display = (langUpper === 'RU') ? '' : 'none';
        }

        function onSave() {
            var zhVal = (mini.get('orgName_zh_CN').getValue() || '').trim();
            var enVal = (mini.get('orgName_en').getValue() || '').trim();
            var ruVal = (mini.get('orgName_ru').getValue() || '').trim();
            var seqVal = mini.get('orgSeq').getValue();

            var langUpper = (language || '').toUpperCase();
            if (langUpper === 'ZH_CN' && !zhVal) {
                mini.alert(_loginUserLanguageResource.orgName + _loginUserLanguageResource.required);
                return;
            }
            if (langUpper === 'EN' && !enVal) {
                mini.alert(_loginUserLanguageResource.orgName + _loginUserLanguageResource.required);
                return;
            }
            if (langUpper === 'RU' && !ruVal) {
                mini.alert(_loginUserLanguageResource.orgName + _loginUserLanguageResource.required);
                return;
            }

            var mask = mini.mask({
                el: document.body,
                html: _loginUserLanguageResource.submittingData
            });

            $.ajax({
                url: context + '/orgManagerController/doOrgAdd',
                type: 'POST',
                data: {
                    'org.orgId':          0,
                	'org.orgParent':      orgParentId,
                    'org.orgName_zh_CN':  zhVal,
                    'org.orgName_en':     enVal,
                    'org.orgName_ru':     ruVal,
                    'org.orgMemo':        '',
                    'org.orgSeq':         seqVal || ''
                },
                dataType: 'json',
                success: function (resp) {
                    mini.unmask(document.body);
                    if (resp && resp.msg === true) {
                        if (window._parentRefreshOrgTree) {
                            window._parentRefreshOrgTree();
                        }
                        mini.alert("<font color=blue>" + _loginUserLanguageResource.addedSuccessfully + "</font>",_loginUserLanguageResource.tip, function () {
                            window.CloseOwnerWindow('ok');
                        });
                    } else {
                        mini.alert('<font color="red">'
                            + _loginUserLanguageResource.addFailure + '</font>');
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(_loginUserLanguageResource.exceptionThrow
                        + ': ' + _loginUserLanguageResource.contactAdmin);
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