<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp?timestamp=<%=otherStaticResourceTimestamp%>" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .form-container { flex:1; overflow:auto; padding:10px; }
        .form-table { width:100%; border-collapse:collapse; }
        .form-table td { padding:6px; vertical-align:middle; }
        .form-table .label { text-align:right; width:90px; font-weight:bold; white-space:nowrap; }
        .form-table .mini-textbox,
        .form-table .mini-textarea,
        .form-table .mini-spinner { width:100% !important; }
        .footer { flex-shrink:0; padding:8px 10px; border-top:1px solid #e8e8e8; background:#fafafa; text-align:right; }
    </style>
</head>
<body>

<!-- 全局变量接收参数 -->
<script>
    var deviceType = '';
</script>

<div class="main-container">
    <div class="form-container">
        <table class="form-table">
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblDeviceName"></span>：</td>
                <td><input id="auxiliaryDeviceName_Id" class="mini-textbox" required="true" onblur="checkAuxiliaryDeviceExist()" /></td>
            </tr>
            <tr>
                <td class="label"><span id="lblManufacturer"></span>：</td>
                <td><input id="auxiliaryDeviceManufacturer_Id" class="mini-textbox" onblur="checkAuxiliaryDeviceExist()" /></td>
            </tr>
            <tr>
                <td class="label"><span id="lblModel"></span>：</td>
                <td><input id="auxiliaryDeviceModel_Id" class="mini-textbox" onblur="checkAuxiliaryDeviceExist()" /></td>
            </tr>
            <tr>
                <td class="label"><span id="lblRemark"></span>：</td>
                <td><input id="auxiliaryRemark_Id" class="mini-textarea" /></td>
            </tr>
            <tr>
                <td class="label"><span id="lblSort"></span>：</td>
                <td><input id="auxiliaryDeviceSort_Id" class="mini-spinner" minValue="1" maxValue="9999999999" /></td>
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
    var _deviceNameValid = true;

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.addDevice;
        document.getElementById('lblDeviceName').textContent = R.deviceName;
        document.getElementById('lblManufacturer').textContent = R.manufacturer;
        document.getElementById('lblModel').textContent = R.model;
        document.getElementById('lblRemark').textContent = R.remark;
        document.getElementById('lblSort').textContent = R.sequenceNumber;

        mini.get('btnSave').setText(R.save);
        mini.get('btnCancel').setText(R.cancel);
    }

    // ================================================================
    // 父窗口调用
    // ================================================================
    function setData(data) {
        if (!data) data = {};
        deviceType = data.deviceType || '';
    }

    // ================================================================
    // 校验：设备名称 + 型号 是否已存在
    // ================================================================
    function checkAuxiliaryDeviceExist() {
        var name = (mini.get('auxiliaryDeviceName_Id').getValue() || '').trim();
        var manufacturer = (mini.get('auxiliaryDeviceManufacturer_Id').getValue() || '').trim();
        var model = (mini.get('auxiliaryDeviceModel_Id').getValue() || '').trim();

        if (!name || !model) {
            _deviceNameValid = true;
            return;
        }

        $.ajax({
            url: context + '/wellInformationManagerController/judgeAuxiliaryDeviceExistOrNot',
            type: 'POST',
            async: false,
            data: {
                name: name,
                type: deviceType,
                manufacturer: manufacturer,
                model: model
            },
            dataType: 'json',
            success: function (resp) {
                if (resp && resp.msg == 1) {
                    var input = mini.get('auxiliaryDeviceName_Id');
                    var confirmMsg = '<font color="red">' + _loginUserLanguageResource.deviceExist + '</font>，' + _loginUserLanguageResource.pleaseConfirm;
                    mini.confirm(confirmMsg, _loginUserLanguageResource.tip, function (action) {
                        if (action == 'ok') {
                            input.focus();
                            input.selectText();
                        }
                    });
                    _deviceNameValid = false;
                } else {
                    _deviceNameValid = true;
                }
            },
            error: function () {
                _deviceNameValid = false;
            }
        });
    }

    // ================================================================
    // 保存
    // ================================================================
    function onSave() {
        var R = _loginUserLanguageResource;

        var name = (mini.get('auxiliaryDeviceName_Id').getValue() || '').trim();
        if (!name) {
            mini.get('auxiliaryDeviceName_Id').focus();
            mini.alert(R.required, R.tip);
            return;
        }

        // 名称 + 型号都填写时，再校验一次
        checkAuxiliaryDeviceExist();
        if (!_deviceNameValid) return;

        var manufacturer = (mini.get('auxiliaryDeviceManufacturer_Id').getValue() || '').trim();
        var model = (mini.get('auxiliaryDeviceModel_Id').getValue() || '').trim();
        var remark = (mini.get('auxiliaryRemark_Id').getValue() || '').trim();
        var sort = mini.get('auxiliaryDeviceSort_Id').getValue() || '';

        var postData = {
            'auxiliaryDeviceInformation.name': name,
            'auxiliaryDeviceInformation.type': deviceType,
            'auxiliaryDeviceInformation.manufacturer': manufacturer,
            'auxiliaryDeviceInformation.model': model,
            'auxiliaryDeviceInformation.remark': remark,
            'auxiliaryDeviceInformation.sort': sort
        };

        mini.mask({ el: document.body, cls: 'mini-mask-loading', html: R.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/doAuxiliaryDeviceAdd',
            type: 'POST',
            data: postData,
            dataType: 'json',
            success: function (resp) {
                mini.unmask(document.body);
                if (resp && resp.success === true) {
                    if (window._parentRefreshDeviceList) {
                        window._parentRefreshDeviceList();
                    }
                    mini.alert(R.addedSuccessfully, R.tip, function () {
                        window.CloseOwnerWindow('ok');
                    });
                } else {
                    mini.alert('<font color=red>' + R.addFailure + '</font>', R.tip);
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