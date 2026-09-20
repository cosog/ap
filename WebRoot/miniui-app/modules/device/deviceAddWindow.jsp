<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>设备信息</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .form-table { width:100%; border-collapse:collapse; }
        .form-table td { padding:4px 6px; vertical-align:middle; }
        .form-table .label { text-align:right; width:110px; font-weight:bold; white-space:nowrap; }
        .form-table .mini-textbox,
        .form-table .mini-password,
        .form-table .mini-combobox,
        .form-table .mini-spinner,
        .form-table .mini-datepicker { width:100% !important; min-width:100px; }
        .info-line { font-size:13px; color:#333; padding-bottom:6px; }
        .footer { flex-shrink:0; padding:8px 10px; border-top:1px solid #e8e8e8; background:#fafafa; text-align:right; }
    </style>
</head>
<body>

<div class="main-container">
    <div class="mini-panel" style="width:100%;height:100%;"
         showHeader="false" showToolbar="false" showCloseButton="false"
         bodyStyle="padding:8px;background:#fff;">
        <form id="deviceForm">
            <table class="form-table">
                <tr>
                    <td colspan="2" class="info-line"><span id="orgInfo"></span></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblDeviceName"></span>：</td>
                    <td><input id="deviceName" class="mini-textbox" required="true" onblur="checkDeviceName()" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblCommissioningDate"></span>：</td>
                    <td><input id="commissioningDate" class="mini-datepicker" format="yyyy-MM-dd" allowInput="false" /></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblDeviceType"></span>：</td>
                    <td><input id="deviceType" class="mini-combobox" required="true" valueField="boxkey" textField="boxval" allowInput="false" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblApplicationScenarios"></span>：</td>
                    <td><input id="applicationScenarios" class="mini-combobox" valueField="boxkey" textField="boxval" allowInput="false" /></td>
                </tr>
                <tr id="rowDeviceTabInstance">
                    <td class="label"><span id="lblDeviceTabInstance"></span>：</td>
                    <td><input id="deviceTabInstance" class="mini-combobox" valueField="boxkey" textField="boxval" allowInput="false" /></td>
                </tr>
                <tr id="rowAcqInstance">
                    <td class="label"><span id="lblAcqInstance"></span>：</td>
                    <td><input id="acqInstance" class="mini-combobox" valueField="boxkey" textField="boxval" allowInput="false"
                               onvaluechanged="onAcqInstanceChanged" /></td>
                </tr>
                <tr id="rowDisplayInstance">
                    <td class="label"><span id="lblDisplayInstance"></span>：</td>
                    <td><input id="displayInstance" class="mini-combobox" valueField="boxkey" textField="boxval" allowInput="false" /></td>
                </tr>
                <tr id="rowReportInstance">
                    <td class="label"><span id="lblReportInstance"></span>：</td>
                    <td><input id="reportInstance" class="mini-combobox" valueField="boxkey" textField="boxval" allowInput="false" /></td>
                </tr>
                <tr id="rowAlarmInstance">
                    <td class="label"><span id="lblAlarmInstance"></span>：</td>
                    <td><input id="alarmInstance" class="mini-combobox" valueField="boxkey" textField="boxval" allowInput="false" /></td>
                </tr>
                <tr id="rowTcpType">
                    <td class="label"><span id="lblTcpType"></span>：</td>
                    <td><input id="tcpType" class="mini-combobox" valueField="value" textField="text" allowInput="false"
                               onvaluechanged="onTcpTypeChanged" /></td>
                </tr>
                <tr id="rowSignInId">
                    <td class="label"><span id="lblSignInId"></span>：</td>
                    <td><input id="signInId" class="mini-textbox" onblur="checkSignInIdAndSlave()" /></td>
                </tr>
                <tr id="rowIpPort">
                    <td class="label"><span id="lblIpPort"></span>：</td>
                    <td><input id="ipPort" class="mini-textbox" onblur="checkIpPortAndSlave()" /></td>
                </tr>
                <tr id="rowSlave">
                    <td class="label"><span id="lblSlave"></span>：</td>
                    <td><input id="slave" class="mini-textbox" value="01" onblur="checkSignInIdAndSlave()" /></td>
                </tr>
                <tr id="rowPeakDelay">
                    <td class="label"><span id="lblPeakDelay"></span>：</td>
                    <td><input id="peakDelay" class="mini-spinner" allowNull="true" value="" minValue="0" maxValue="9999999999"/></td>
                </tr>
                <tr>
                    <td class="label"><span style="color:red;">*</span><span id="lblStatus"></span>：</td>
                    <td><input id="status" class="mini-radiobuttonlist" required="true" value="1" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblSortNum"></span>：</td>
                    <td><input id="sortNum" class="mini-spinner" minValue="1" maxValue="9999999999" /></td>
                </tr>
            </table>
        </form>
    </div>

    <div class="footer">
        <button id="btnSave"   class="mini-button" iconCls="save"   onclick="onSave()"></button>
        <button id="btnCancel" class="mini-button" iconCls="cancel" onclick="onCancel()"></button>
    </div>
</div>

<script>
    var context = '<%=context%>';

    // 父窗口传入
    var orgId = '';
    var orgName = '';
    var defaultDeviceType = '';       // 若父窗口已锁定设备类型，传具体值；否则空
    var dictDeviceType = '';
    var iotEnable = false;
    var editMode = false;             // 编辑模式
    var editDeviceId = 0;

    // 校验状态
    var _deviceNameValid = false;
    var _signInIdValid = false;
    var _ipPortValid = false;

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = editMode ? R.editDevice : R.addDevice;

        document.getElementById('lblDeviceName').textContent = R.deviceName;
        document.getElementById('lblCommissioningDate').textContent = R.commissioningDate;
        document.getElementById('lblDeviceType').textContent = R.deviceType;
        document.getElementById('lblApplicationScenarios').textContent = R.applicationScenarios;
        document.getElementById('lblDeviceTabInstance').textContent = R.deviceTagInstance;
        document.getElementById('lblAcqInstance').textContent = R.acqInstance;
        document.getElementById('lblDisplayInstance').textContent = R.displayInstance;
        document.getElementById('lblReportInstance').textContent = R.reportInstance;
        document.getElementById('lblAlarmInstance').textContent = R.alarmInstance;
        document.getElementById('lblTcpType').textContent = R.deviceTcpType;
        document.getElementById('lblSignInId').textContent = R.signInId;
        document.getElementById('lblIpPort').textContent = R.ipPort;
        document.getElementById('lblSlave').textContent = R.slave;
        document.getElementById('lblPeakDelay').textContent = R.peakDelay + '(s)';
        document.getElementById('lblStatus').textContent = R.status;
        document.getElementById('lblSortNum').textContent = R.sequenceNumber;

        mini.get('btnSave').setText(R.save);
        mini.get('btnCancel').setText(R.cancel);

        // 状态 radio
        mini.get('status').setData([{ id: 1, text: R.enable }, { id: 0, text: R.disable }]);
        mini.get('status').setValue("1");

        // TCP 类型
        mini.get('tcpType').setData([{ value: 'TCP Client', text: 'TCP Client' }, { value: 'TCP Server', text: 'TCP Server' }]);
        mini.get('tcpType').setValue('TCP Client');
        mini.get('signInId').setEnabled(true);
        mini.get('ipPort').setEnabled(false);
    }

    // ================================================================
    // 由父窗口调用
    // ================================================================
    function setData(data) {
        if (!data) data = {};

        orgId             = data.orgId || '';
        orgName           = data.orgName || '';
        defaultDeviceType = data.deviceType || '';
        dictDeviceType    = data.dictDeviceType || data.deviceType || '';
        iotEnable         = data.iotEnable || false;
        editMode          = data.editMode || false;
        editDeviceId      = data.deviceId || 0;

        document.getElementById('orgInfo').innerHTML =
            _loginUserLanguageResource.owningOrg
            + "：【<font color='red'>" + orgName + "</font>】，"
            + _loginUserLanguageResource.pleaseConfirm;

        // 根据 IoT 开关显示/隐藏相关字段
        if (!iotEnable) {
            document.getElementById('rowDeviceTabInstance').style.display = 'none';
            document.getElementById('rowAcqInstance').style.display       = 'none';
            document.getElementById('rowDisplayInstance').style.display   = 'none';
            document.getElementById('rowReportInstance').style.display    = 'none';
            document.getElementById('rowAlarmInstance').style.display     = 'none';
            document.getElementById('rowTcpType').style.display           = 'none';
            document.getElementById('rowSignInId').style.display          = 'none';
            document.getElementById('rowIpPort').style.display            = 'none';
            document.getElementById('rowSlave').style.display             = 'none';
            document.getElementById('rowPeakDelay').style.display         = 'none';
        }

        // 加载各下拉
        loadDeviceTypeList();
        loadApplicationScenariosList();
        loadDeviceTabInstanceList();
        loadAcqInstanceList();
        loadReportInstanceList();

        // 编辑模式回填
        if (editMode && editDeviceId > 0) {
            loadDeviceForEdit(editDeviceId);
        }
    }

    // ================================================================
    // 加载各下拉列表
    // ================================================================
    function loadDeviceTypeList() {
        $.ajax({
            url: context + '/wellInformationManagerController/getDeviceTypeComb',
            type: 'POST',
            data: { deviceTypes: defaultDeviceType },
            dataType: 'json',
            success: function (resp) {
                var list = [];
                if (resp && resp.list) {
                    for (var i = 0; i < resp.list.length; i++) {
                        list.push({ boxkey: resp.list[i].boxkey, boxval: resp.list[i].boxval });
                    }
                }
                var combo = mini.get('deviceType');
                combo.setData(list);
                if (defaultDeviceType && isNumber(defaultDeviceType)) {
                    combo.setValue(defaultDeviceType);
                    combo.setEnabled(false);
                }
            }
        });
    }

    function loadApplicationScenariosList() {
        $.ajax({
            url: context + '/wellInformationManagerController/getApplicationScenariosComb',
            type: 'POST',
            dataType: 'json',
            success: function (resp) {
                var list = [];
                if (resp && resp.list) {
                    for (var i = 0; i < resp.list.length; i++) {
                        list.push({ boxkey: resp.list[i].boxkey, boxval: resp.list[i].boxval });
                    }
                }
                mini.get('applicationScenarios').setData(list);
            }
        });
    }

    function loadDeviceTabInstanceList() {
        $.ajax({
            url: context + '/wellInformationManagerController/getDeviceTabInstanceCombList',
            type: 'POST',
            dataType: 'json',
            success: function (resp) {
                var list = [];
                if (resp && resp.list) {
                    for (var i = 0; i < resp.list.length; i++) {
                        list.push({ boxkey: resp.list[i].boxkey, boxval: resp.list[i].boxval });
                    }
                }
                mini.get('deviceTabInstance').setData(list);
            }
        });
    }

    function loadAcqInstanceList() {
        $.ajax({
            url: context + '/wellInformationManagerController/getAcqInstanceCombList',
            type: 'POST',
            data: { dictDeviceType: dictDeviceType },
            dataType: 'json',
            success: function (resp) {
                var list = [];
                if (resp && resp.list) {
                    for (var i = 0; i < resp.list.length; i++) {
                        list.push({ boxkey: resp.list[i].boxkey, boxval: resp.list[i].boxval });
                    }
                }
                mini.get('acqInstance').setData(list);
            }
        });
    }

    function loadReportInstanceList() {
        $.ajax({
            url: context + '/wellInformationManagerController/getReportInstanceCombList',
            type: 'POST',
            data: { deviceType: 101 },
            dataType: 'json',
            success: function (resp) {
                var list = [];
                if (resp && resp.list) {
                    for (var i = 0; i < resp.list.length; i++) {
                        list.push({ boxkey: resp.list[i].boxkey, boxval: resp.list[i].boxval });
                    }
                }
                mini.get('reportInstance').setData(list);
            }
        });
    }

    // ================================================================
    // 采控实例改变 → 刷新显示实例 / 报警实例
    // ================================================================
    function onAcqInstanceChanged(e) {
        var acqInstance = mini.get('acqInstance').getValue() || '';
        var acqInfo = getInstanceUnitAndProtocol(acqInstance, 0, 0);

        // 显示实例：根据 acqUnitId 加载
        $.ajax({
            url: context + '/wellInformationManagerController/getDisplayInstanceCombList',
            type: 'POST',
            data: { dictDeviceType: dictDeviceType, acqUnitId: acqInfo.acqUnitId },
            dataType: 'json',
            success: function (resp) {
                var list = [];
                if (resp && resp.list) {
                    for (var i = 0; i < resp.list.length; i++) {
                        list.push({ boxkey: resp.list[i].boxkey, boxval: resp.list[i].boxval });
                    }
                }
                mini.get('displayInstance').setData(list);
                mini.get('displayInstance').setValue('');
            }
        });

        // 报警实例：根据 protocolCode 加载
        $.ajax({
            url: context + '/wellInformationManagerController/getAlarmInstanceCombList',
            type: 'POST',
            data: { dictDeviceType: dictDeviceType, protocolCode: acqInfo.protocolCode },
            dataType: 'json',
            success: function (resp) {
                var list = [];
                if (resp && resp.list) {
                    for (var i = 0; i < resp.list.length; i++) {
                        list.push({ boxkey: resp.list[i].boxkey, boxval: resp.list[i].boxval });
                    }
                }
                mini.get('alarmInstance').setData(list);
                mini.get('alarmInstance').setValue('');
            }
        });
    }

    // ================================================================
    // TCP 类型改变 → 控制 signInId / ipPort 可用性
    // ================================================================
    function onTcpTypeChanged(e) {
        var v = mini.get('tcpType').getValue() || '';
        if (v === 'TCP Server') {
            mini.get('signInId').setEnabled(false);
            mini.get('ipPort').setEnabled(true);
        } else if (v === 'TCP Client') {
            mini.get('signInId').setEnabled(true);
            mini.get('ipPort').setEnabled(false);
        } else {
            mini.get('signInId').setEnabled(true);
            mini.get('ipPort').setEnabled(true);
        }
    }

    // ================================================================
    // 校验：设备名称重复
    // ================================================================
    function checkDeviceName() {
        var value = (mini.get('deviceName').getValue() || '').trim();
        if (!value) { _deviceNameValid = false; return; }
        var deviceType = mini.get('deviceType').getValue();

        $.ajax({
            url: context + '/wellInformationManagerController/judgeDeviceExistOrNot',
            type: 'POST',
            async: false,
            data: { orgId: orgId, deviceName: value, deviceType: deviceType, deviceId: editDeviceId },
            dataType: 'json',
            success: function (resp) {
                if (resp && resp.msg == 1) {
                    var input=mini.get('deviceName');
                    var confirmMsg = '<font color="red">【' + value + ' ' + _loginUserLanguageResource.deviceExist + '】</font>，' + _loginUserLanguageResource.pleaseConfirm;
                    mini.confirm(confirmMsg, _loginUserLanguageResource.confirm, function (action) {
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
            error: function () { _deviceNameValid = false; }
        });
    }

    // ================================================================
    // 校验：signInId + slave 冲突
    // ================================================================
    function checkSignInIdAndSlave() {
        if (!iotEnable) { _signInIdValid = true; return; }
        var signInId = (mini.get('signInId').getValue() || '').trim();
        var slave    = (mini.get('slave').getValue() || '').trim();
        var deviceType = mini.get('deviceType').getValue();
        if (!signInId || !slave) { _signInIdValid = true; return; }

        $.ajax({
            url: context + '/wellInformationManagerController/judgeDeviceExistOrNotBySigninIdAndSlave',
            type: 'POST',
            async: false,
            data: { signinId: signInId, slave: slave, deviceType: deviceType, deviceId: editDeviceId },
            dataType: 'json',
            success: function (resp) {
                if (resp && resp.msg == 1) {
                    var input=mini.get('signInId');
                    var confirmMsg = '<font color="red">'+ _loginUserLanguageResource.collisionInfo1 + '<br/>'+ _loginUserLanguageResource.affiliatedOrg + ':' + resp.org + '<br/>'+ _loginUserLanguageResource.deviceName + ':' + resp.device + '<br/>'+ '</font><br/>' + _loginUserLanguageResource.pleaseConfirm;
                    mini.confirm(confirmMsg, _loginUserLanguageResource.confirm, function (action) {
                        if (action == 'ok') {
                        	input.focus();
                        	input.selectText();
                        }
                    });
                    
                    _signInIdValid = false;
                } else {
                    _signInIdValid = true;
                }
            },
            error: function () { _signInIdValid = false; }
        });
    }

    // ================================================================
    // 校验：ipPort + slave 冲突
    // ================================================================
    function checkIpPortAndSlave() {
        if (!iotEnable) { _ipPortValid = true; return; }
        var ipPort = (mini.get('ipPort').getValue() || '').trim();
        var slave  = (mini.get('slave').getValue() || '').trim();
        var deviceType = mini.get('deviceType').getValue();
        if (!ipPort || !slave) { _ipPortValid = true; return; }

        // IP 端口格式校验
        var ipPortReg = /^(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
        if (!ipPortReg.test(ipPort)) {
            var confirmMsg = _loginUserLanguageResource.dataFormattingError;
            mini.confirm(confirmMsg, _loginUserLanguageResource.confirm, function (action) {
                if (action == 'ok') {
                	mini.get('ipPort').focus();
                	mini.get('ipPort').selectText();
                }
            });
            
            _ipPortValid = false;
            return;
        }

        $.ajax({
            url: context + '/wellInformationManagerController/judgeDeviceExistOrNotByIpPortAndSlave',
            type: 'POST',
            async: false,
            data: { ipPort: ipPort, slave: slave, deviceType: deviceType, deviceId: editDeviceId },
            dataType: 'json',
            success: function (resp) {
                if (resp && resp.msg == 1) {
                    var input=mini.get('ipPort');
                    var confirmMsg = '<font color="red">'
                        + _loginUserLanguageResource.collisionInfo1 + '<br/>'
                        + _loginUserLanguageResource.affiliatedOrg + ':' + resp.org + '<br/>'
                        + _loginUserLanguageResource.deviceName + ':' + resp.device + '<br/>'
                        + '</font><br/>' + _loginUserLanguageResource.pleaseConfirm;
                    mini.confirm(confirmMsg, _loginUserLanguageResource.confirm, function (action) {
                        if (action == 'ok') {
                        	input.focus();
                        	input.selectText();
                        }
                    });
                    _ipPortValid = false;
                } else {
                    _ipPortValid = true;
                }
            },
            error: function () { _ipPortValid = false; }
        });
    }

    // ================================================================
    // 编辑回填
    // ================================================================
    function loadDeviceForEdit(deviceId) {
        $.ajax({
            url: context + '/wellInformationManagerController/getDeviceInfoById',
            type: 'POST',
            data: { deviceId: deviceId },
            dataType: 'json',
            success: function (resp) {
                var d = resp || {};
                mini.get('deviceName').setValue(d.deviceName);
                mini.get('commissioningDate').setValue(d.commissioningDate);
                mini.get('deviceType').setValue(d.deviceType);
                mini.get('applicationScenarios').setValue(d.applicationScenarios);
                mini.get('deviceTabInstance').setValue(d.deviceTabInstance);
                mini.get('acqInstance').setValue(d.instanceName);
                mini.get('displayInstance').setValue(d.displayInstanceName);
                mini.get('reportInstance').setValue(d.reportInstanceName);
                mini.get('alarmInstance').setValue(d.alarmInstanceName);
                mini.get('tcpType').setValue(d.tcpType);
                mini.get('signInId').setValue(d.signInId);
                mini.get('ipPort').setValue(d.ipPort);
                mini.get('slave').setValue(d.slave);
                mini.get('peakDelay').setValue(d.peakDelay);
                mini.get('status').setValue(d.status != undefined ? d.status : 1);
                mini.get('sortNum').setValue(d.sortNum);
                onTcpTypeChanged();
            }
        });
    }

    // ================================================================
    // 保存
    // ================================================================
    function onSave() {
        var R = _loginUserLanguageResource;

        var deviceName = (mini.get('deviceName').getValue() || '').trim();
        if (!deviceName) { 
        	mini.get('deviceName').focus();
        	mini.alert(R.required, R.tip); 
        	return; 
        }
        if (!_deviceNameValid) { checkDeviceName(); }
        if (!_deviceNameValid) return;

        var deviceType = mini.get('deviceType').getValue();
        if (!deviceType) {
        	mini.get('deviceType').focus();
        	mini.alert(R.required, R.tip); 
        	return; 
        }

        if (iotEnable) {
            checkSignInIdAndSlave();
            checkIpPortAndSlave();
            if (!_signInIdValid || !_ipPortValid) return;
        }

        var url = editMode ? '/wellInformationManagerController/doDeviceEdit'
                           : '/wellInformationManagerController/doDeviceAdd';

        var data = {
            'deviceInformation.id':                   editMode ? editDeviceId : '',
            'deviceInformation.orgId':                orgId,
            'deviceInformation.deviceName':           deviceName,
            'deviceInformation.commissioningDate':    mini.get('commissioningDate').getFormValue('yyyy-MM-dd') || '',
            'deviceInformation.deviceType':           deviceType,
            'deviceInformation.applicationScenarios': mini.get('applicationScenarios').getValue() || '',
            'deviceInformation.calculateType':        iotEnable ? (mini.get('deviceTabInstance').getValue() || '') : '',
            'deviceInformation.instanceCode':         iotEnable ? (mini.get('acqInstance').getValue() || '') : '',
            'deviceInformation.displayInstanceCode':  iotEnable ? (mini.get('displayInstance').getValue() || '') : '',
            'deviceInformation.reportInstanceCode':   iotEnable ? (mini.get('reportInstance').getValue() || '') : '',
            'deviceInformation.alarmInstanceCode':    iotEnable ? (mini.get('alarmInstance').getValue() || '') : '',
            'deviceInformation.tcpType':              iotEnable ? (mini.get('tcpType').getValue() || '') : '',
            'deviceInformation.signInId':             iotEnable ? (mini.get('signInId').getValue() || '') : '',
            'deviceInformation.ipPort':               iotEnable ? (mini.get('ipPort').getValue() || '') : '',
            'deviceInformation.slave':                iotEnable ? (mini.get('slave').getValue() || '') : '',
            'deviceInformation.peakDelay':            iotEnable ? (mini.get('peakDelay').getValue() || '') : '',
            'deviceInformation.status':               mini.get('status').getValue(),
            'deviceInformation.sortNum':              mini.get('sortNum').getValue() || ''
        };

        var mask = mini.mask({ el: document.body, html: R.submittingData });

        $.ajax({
            url: context + url,
            type: 'POST',
            data: data,
            dataType: 'json',
            success: function (resp) {
                mini.unmask(document.body);
                if (resp && resp.msg === true) {
                    if (resp.resultCode == -66) {
                        mini.alert('<font color="red">设备数许可超限</font>', R.tip);
                        return;
                    }
                    if (window._parentRefreshDeviceList) {
                        window._parentRefreshDeviceList(editMode);
                    }
                    var successMsg = editMode ? R.updateSuccessfully : R.addedSuccessfully;
                    mini.alert(successMsg, R.tip, function () {
                        window.CloseOwnerWindow('ok');
                    });
                } else {
                    mini.alert('<font color="red">'
                        + (editMode ? R.updateFailed : R.addFailure)
                        + '</font>', R.tip);
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

    // ================================================================
    // 辅助
    // ================================================================
    function getInstanceUnitAndProtocol(instance, condition, type) {
        var info = { protocolCode: '', acqUnitId: '', displayUnitId: '', alarmUnitId: '', displayInstanceList: [], alarmInstanceList: [] };
        $.ajax({
            url: context + '/wellInformationManagerController/getInstanceUnitAndProtocol',
            type: 'POST',
            async: false,
            data: { instance: instance, condition: condition, type: type },
            dataType: 'json',
            success: function (result) {
                info.protocolCode = result.protocolCode;
                info.acqUnitId = result.acqUnitId;
                info.displayUnitId = result.displayUnitId;
                info.alarmUnitId = result.alarmUnitId;
                info.displayInstanceList = result.displayInstanceList || [];
                info.alarmInstanceList = result.alarmInstanceList || [];
            }
        });
        return info;
    }

    function isNumber(v) {
        if (v === undefined || v === null || v === '') return false;
        return !isNaN(v);
    }

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>