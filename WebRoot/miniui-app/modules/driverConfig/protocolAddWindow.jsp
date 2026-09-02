<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加协议</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .mini-form { width: 100%; }
        .form-table { 
            width: 100%; 
            border-collapse: collapse; 
        }
        .form-table td { 
            padding: 6px 8px; 
            vertical-align: middle;
        }
        .label { 
            text-align: right; 
            width: 80px; 
            font-weight: bold; 
            white-space: nowrap;
        }
        /* 控件宽度铺满 */
        .mini-textbox, 
        .mini-combobox, 
        .mini-spinner {
            width: 100% !important;
            min-width: 100px; /* 保证最小宽度 */
        }
        .info-line { 
            font-size: 14px; 
            color: #333; 
            padding-bottom: 10px; 
        }
    </style>
</head>
<body>
    <div style="padding:10px;">
        <form id="protocolForm" class="mini-form">
            <table class="form-table">
                <tr>
                    <td colspan="2" class="info-line">
                        <span id="deviceTypeInfo"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblProtocolName"></span>：</td>
                    <td><input id="protocolName" class="mini-textbox" required="true" vtype="string" onblur="checkProtocolName()" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblLanguage"></span>：</td>
                    <td>
                        <input id="language" class="mini-combobox" 
                               valueField="boxkey" textField="boxval" 
                               readonly="readonly" enabled="false" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><span id="lblSort"></span>：</td>
                    <td><input id="sort" class="mini-spinner" minValue="1" value="1" /></td>
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
        var deviceTypeId = '';
        var language = '';
        var deviceTypeName = '';
        var languageValue = 0;
        var protocolNameValid = false;

        function initI18n() {
            document.getElementById('lblProtocolName').textContent = _loginUserLanguageResource.protocolName;
            document.getElementById('lblLanguage').textContent = _loginUserLanguageResource.language;
            document.getElementById('lblSort').textContent = _loginUserLanguageResource.sequenceNumber;
            document.getElementById('btnSave').textContent = _loginUserLanguageResource.save;
            document.getElementById('btnCancel').textContent = _loginUserLanguageResource.cancel;
            document.title = _loginUserLanguageResource.addProtoco;
        }

        function setData(data) {
            deviceTypeId = data.deviceTypeId || '';
            language = data.language;
            languageValue = data.languageValue;
            deviceTypeName = data.deviceTypeName || '';
            
            // 设置设备类型提示信息（完全按原ExtJS格式，不做任何改动）
            var infoHtml = _loginUserLanguageResource.owningDeviceType + ":<font color='red'>" + deviceTypeName + "</font>，" + _loginUserLanguageResource.pleaseConfirm;
            document.getElementById('deviceTypeInfo').innerHTML = infoHtml;
            
            var combo = mini.get('language');
            if (combo) {
                combo.setValue(language);
            }
        }

        function checkProtocolName() {
            var nameInput = mini.get('protocolName');
            var name = nameInput.getValue();
            if (!name || name.trim() === '') {
                protocolNameValid = false;
                return;
            }
            $.ajax({
                url: context + '/acquisitionUnitManagerController/judgeProtocolExistOrNot',
                type: 'POST',
                data: {
                    deviceType: deviceTypeId,
                    protocolName: name
                },
                dataType: 'json',
                success: function (resp) {
                    if (resp.msg === 1) {
                        var confirmMsg = '<font color="red">' + (_loginUserLanguageResource.protocolExist) + '，' + (_loginUserLanguageResource.pleaseConfirm) + '</font>';
                        mini.confirm(confirmMsg, _loginUserLanguageResource.confirm, function (action) {
                            if (action == 'ok') {
                                nameInput.focus();
                                nameInput.selectText();
                            }
                        });
                        protocolNameValid = false;
                    } else {
                        protocolNameValid = true;
                    }
                },
                error: function () {
                    protocolNameValid = false;
                }
            });
        }

        function onSave() {
            var form = new mini.Form('#protocolForm');
            form.validate();
            if (!form.isValid()) {
                mini.alert(_loginUserLanguageResource.required);
                return;
            }
            if (!protocolNameValid) {
                mini.alert('<font color="red">' + (_loginUserLanguageResource.protocolExist || '协议名称无效') + '，请重新输入</font>');
                mini.get('protocolName').focus();
                return;
            }

            var protocolName = mini.get('protocolName').getValue();
            var sort = mini.get('sort').getValue();

            var mask = mini.mask({
                el: document.body,
                html: _loginUserLanguageResource.submittingData
            });
            $.ajax({
                url: context + '/acquisitionUnitManagerController/doModbusProtocolAdd',
                type: 'POST',
                data: {
                    name: protocolName,
                    language: languageValue,
                    sort: sort || 1,
                    deviceType: deviceTypeId
                },
                dataType: 'json',
                success: function (resp) {
                    mini.unmask(document.body);
                    if (resp.msg === true) {
                        if (window._parentSetNewProtocolName) {
                            window._parentSetNewProtocolName(protocolName);
                        }
                        if (window._parentRefreshProtocolTree) {
                            window._parentRefreshProtocolTree();
                        }
                        mini.alert(_loginUserLanguageResource.addedSuccessfully, function () {
                            window.CloseOwnerWindow('ok');
                        });
                    } else {
                        mini.alert('<font color="red">' + _loginUserLanguageResource.addFailure + '</font>');
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert((_loginUserLanguageResource.exceptionThrow) + ': ' + (_loginUserLanguageResource.contactAdmin));
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