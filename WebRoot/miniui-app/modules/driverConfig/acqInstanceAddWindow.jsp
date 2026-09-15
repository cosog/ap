<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加采控实例</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .mini-form { width: 100%; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 4px 8px; vertical-align: middle; }
        .label { text-align: right; width: 150px; font-weight: bold; white-space: nowrap; }
        .mini-textbox, .mini-combobox, .mini-spinner, .mini-treeselect { width: 100% !important; }
        .tip-area { color: red; padding: 5px 0 10px 5px; display: none; font-size: 13px; }
        .radio-cell { white-space: nowrap; }
        .radio-cell .mini-radiobuttonlist { display: inline-block; width: auto !important; }
    </style>
</head>
<body>
<div style="padding:10px;">
    <form id="instanceForm" class="mini-form">
        <table class="form-table">
            <!-- 采集单元（树选择，禁止选中父节点） -->
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblAcqUnit"></span>：</td>
                <td>
                    <input id="acqUnitTreeSelect" class="mini-treeselect"
                           style="width:100%;"
                           multiSelect="false"
                           valueFromSelect="true"
                           textField="text"
                           valueField="id"
                           parentField="pid"
                           resultAsTree="true"
                           allowInput="false"
                           showRadioButton="false"
                           showFolderCheckBox="false"
                           expandOnLoad="true"
                           showTreeIcon="true"
                           required="true"
                           onbeforeload="onAcqUnitTreeBeforeLoad"
                           onload="onAcqUnitTreeLoad"
                           onbeforenodeselect="onAcqUnitTreeBeforeNodeSelect"
                           onvaluechanged="onAcqUnitValueChanged" />
                    <input id="unitId" class="mini-hidden" />
                </td>
            </tr>

            <!-- 实例名称 -->
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblInstanceName"></span>：</td>
                <td><input id="instanceName" class="mini-textbox" required="true" style="width:100%;" onblur="checkInstanceName()" /></td>
            </tr>

            <!-- 采集协议类型 -->
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblAcqProtocolType"></span>：</td>
                <td>
                    <input id="acqProtocolTypeComb" class="mini-combobox"
                           style="width:100%;"
                           valueField="value" textField="text"
                           required="true" allowInput="false"
                           value="modbus-tcp"
                           onvaluechanged="onAcqProtocolTypeChanged" />
                </td>
            </tr>

            <!-- 控制协议类型 -->
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblCtrlProtocolType"></span>：</td>
                <td>
                    <input id="ctrlProtocolTypeComb" class="mini-combobox"
                           style="width:100%;"
                           valueField="value" textField="text"
                           required="true" allowInput="false"
                           value="modbus-tcp" />
                </td>
            </tr>

            <!-- 登录前后缀 HEX -->
            <tr id="rowSignInPrefixSuffixHex">
                <td class="label"><span style="color:red;">*</span><span id="lblSignInPrefixSuffixHex"></span>：</td>
                <td class="radio-cell">
                    <input id="signInPrefixSuffixHex" class="mini-radiobuttonlist"
                           repeatItems="2" repeatLayout="table" repeatDirection="horizontal"
                           textField="text" valueField="id" value="1"
                           data="[{id:'1',text:'HEX'},{id:'0',text:'ASC'}]" />
                </td>
            </tr>
            <tr id="rowSignInPrefix">
                <td class="label"><span id="lblSignInPrefix"></span>：</td>
                <td><input id="signInPrefix" class="mini-textbox" style="width:100%;" /></td>
            </tr>
            <tr id="rowSignInSuffix">
                <td class="label"><span id="lblSignInSuffix"></span>：</td>
                <td><input id="signInSuffix" class="mini-textbox" style="width:100%;" /></td>
            </tr>

            <!-- 登录 ID HEX -->
            <tr id="rowSignInIDHex">
                <td class="label"><span style="color:red;">*</span><span id="lblSignInIDHex"></span>：</td>
                <td class="radio-cell">
                    <input id="signInIDHex" class="mini-radiobuttonlist"
                           repeatItems="2" repeatLayout="table" repeatDirection="horizontal"
                           textField="text" valueField="id" value="1"
                           data="[{id:'1',text:'HEX'},{id:'0',text:'ASC'}]" />
                </td>
            </tr>

            <!-- 心跳前后缀 HEX -->
            <tr id="rowHeartbeatPrefixSuffixHex">
                <td class="label"><span style="color:red;">*</span><span id="lblHeartbeatPrefixSuffixHex"></span>：</td>
                <td class="radio-cell">
                    <input id="heartbeatPrefixSuffixHex" class="mini-radiobuttonlist"
                           repeatItems="2" repeatLayout="table" repeatDirection="horizontal"
                           textField="text" valueField="id" value="1"
                           data="[{id:'1',text:'HEX'},{id:'0',text:'ASC'}]" />
                </td>
            </tr>
            <tr id="rowHeartbeatPrefix">
                <td class="label"><span id="lblHeartbeatPrefix"></span>：</td>
                <td><input id="heartbeatPrefix" class="mini-textbox" style="width:100%;" /></td>
            </tr>
            <tr id="rowHeartbeatSuffix">
                <td class="label"><span id="lblHeartbeatSuffix"></span>：</td>
                <td><input id="heartbeatSuffix" class="mini-textbox" style="width:100%;" /></td>
            </tr>

            <!-- 包发送间隔 -->
            <tr id="rowPacketSendInterval">
                <td class="label"><span id="lblPacketSendInterval"></span>：</td>
                <td><input id="packetSendInterval" class="mini-textbox" style="width:100%;" /></td>
            </tr>

            <!-- 序号 -->
            <tr>
                <td class="label"><span id="lblSort"></span>：</td>
                <td><input id="sort" class="mini-spinner" style="width:100%;" minValue="1" /></td>
            </tr>
        </table>

        <div style="text-align:center;padding-top:20px;">
            <a class="mini-button" onclick="onSave()" style="width:80px;" id="btnSave">保存</a>
            <a class="mini-button" onclick="onCancel()" style="width:80px;margin-left:10px;" id="btnCancel">取消</a>
        </div>
    </form>
</div>

<script>
    var context = '<%=context%>';
    var _deviceTypeIds = '';
    var _protocolList = '';
    var _instanceNameValid = false;
    var _pendingData = null;

    // ================================================================
    // 1. 父窗口调用入口（延迟初始化）
    // ================================================================
    function setData(data) {
        _pendingData = data;
        if (document.readyState === 'complete') {
            processSetData();
        } else {
            $(document).ready(function () {
                processSetData();
            });
        }
    }

    function processSetData() {
        if (!_pendingData) return;
        var data = _pendingData;
        _pendingData = null;

        _deviceTypeIds = data.deviceTypeIds || '';
        _protocolList = data.protocolList || '';

        // 加载采集单元树
        var treeSelect = mini.get('acqUnitTreeSelect');
        if (treeSelect) {
            treeSelect.setUrl(context + '/acquisitionUnitManagerController/modbusProtocolAndAcqUnitTreeData');
            treeSelect.load();
        }
    }

    // ================================================================
    // 2. 采集单元树事件
    // ================================================================
    function onAcqUnitTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceTypeIds = _deviceTypeIds;
        params.protocol = _protocolList;
        e.params = params;
    }

    // 只允许选中采集单元节点（classes === 2）
    function onAcqUnitTreeBeforeNodeSelect(e) {
        if (!e.node) {
            e.cancel = true;
            return;
        }
        var cls = parseInt(e.node.classes);
        if (isNaN(cls) || cls !== 2) {
            e.cancel = true;
        }
    }

    // 树加载完成后：若没有采集单元，则显示提示并禁用所有字段
    function onAcqUnitTreeLoad(e) {
        var treeSelect = e.sender;
        var data = treeSelect.getData() || [];
    }

    // 树选择变化：更新隐藏的 unitId 字段
    function onAcqUnitValueChanged(e) {
        var treeSelect = mini.get('acqUnitTreeSelect');
        var val = treeSelect.getValue();
        var unitIdField = mini.get('unitId');
        if (unitIdField) {
            unitIdField.setValue(val || '');
        }
    }

    // ================================================================
    // 3. 采集协议类型变化：控制隐藏/显示
    // ================================================================
    function onAcqProtocolTypeChanged(e) {
        var value = e.value || '';
        var isPrivate = value.indexOf('private-') === 0;

        var rowIds = [
            'rowSignInPrefixSuffixHex', 'rowSignInPrefix', 'rowSignInSuffix', 'rowSignInIDHex',
            'rowHeartbeatPrefixSuffixHex', 'rowHeartbeatPrefix', 'rowHeartbeatSuffix', 'rowPacketSendInterval'
        ];
        for (var i = 0; i < rowIds.length; i++) {
            var el = document.getElementById(rowIds[i]);
            if (el) el.style.display = isPrivate ? 'none' : '';
        }
    }

    // ================================================================
    // 4. 实例名称查重
    // ================================================================
    function checkInstanceName() {
        var nameInput = mini.get('instanceName');
        var name = nameInput.getValue();
        var unitId = mini.get('unitId').getValue();
        if (!name || name.trim() === '' || !unitId) {
            _instanceNameValid = false;
            return;
        }
        $.ajax({
            url: context + '/acquisitionUnitManagerController/judgeInstanceExistOrNot',
            type: 'POST',
            data: {
                instanceName: name,
                acqUnitId: unitId
            },
            dataType: 'json',
            success: function (resp) {
                if (parseInt(resp.msg) === 1) {
                    mini.confirm(
                        '<font color="red">' + (_loginUserLanguageResource.acqInstanceExist || '实例已存在') +
                        '</font>，' + (_loginUserLanguageResource.pleaseConfirm || '请确认'),
                        _loginUserLanguageResource.confirm || '确认',
                        function (action) {
                            if (action === 'ok') {
                                nameInput.focus();
                                nameInput.selectText();
                            }
                        }
                    );
                    _instanceNameValid = false;
                } else {
                    _instanceNameValid = true;
                }
            },
            error: function () {
                _instanceNameValid = false;
            }
        });
    }

    // ================================================================
    // 5. 保存
    // ================================================================
    function onSave() {
        var form = new mini.Form('#instanceForm');
        form.validate();
        if (!form.isValid()) {
            mini.alert(_loginUserLanguageResource.required || '请完善表单数据');
            return;
        }

        var unitId = mini.get('unitId').getValue();
        if (!unitId) {
            mini.alert('<font color="red">' + (_loginUserLanguageResource.selectAcqUnit || '请选择采集单元') + '</font>');
            return;
        }

        if (!_instanceNameValid) {
            mini.alert('<font color="red">' + (_loginUserLanguageResource.acqInstanceExist || '实例名称无效') + '，请重新输入</font>');
            mini.get('instanceName').focus();
            return;
        }

        var instanceName = mini.get('instanceName').getValue();
        var acqProtocolType = mini.get('acqProtocolTypeComb').getValue();
        var ctrlProtocolType = mini.get('ctrlProtocolTypeComb').getValue();
        var signInPrefixSuffixHex = mini.get('signInPrefixSuffixHex').getValue();
        var signInPrefix = mini.get('signInPrefix').getValue() || '';
        var signInSuffix = mini.get('signInSuffix').getValue() || '';
        var signInIDHex = mini.get('signInIDHex').getValue();
        var heartbeatPrefixSuffixHex = mini.get('heartbeatPrefixSuffixHex').getValue();
        var heartbeatPrefix = mini.get('heartbeatPrefix').getValue() || '';
        var heartbeatSuffix = mini.get('heartbeatSuffix').getValue() || '';
        var packetSendInterval = mini.get('packetSendInterval').getValue() || '';
        var sort = mini.get('sort').getValue() || '';

        // 私有协议时清空登录/心跳等字段，避免提交无效值
        if (acqProtocolType && acqProtocolType.indexOf('private-') === 0) {
            signInPrefix = '';
            signInSuffix = '';
            heartbeatPrefix = '';
            heartbeatSuffix = '';
            packetSendInterval = '';
        }

        var mask = mini.mask({
            el: document.body,
            html: _loginUserLanguageResource.submittingData
        });

        $.ajax({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolInstanceAdd',
            type: 'POST',
            data: {
                'protocolInstance.unitId': unitId,
                'protocolInstance.name': instanceName,
                'protocolInstance.acqProtocolType': acqProtocolType,
                'protocolInstance.ctrlProtocolType': ctrlProtocolType,
                'protocolInstance.signInPrefixSuffixHex': signInPrefixSuffixHex,
                'protocolInstance.signInPrefix': signInPrefix,
                'protocolInstance.signInSuffix': signInSuffix,
                'protocolInstance.signInIDHex': signInIDHex,
                'protocolInstance.heartbeatPrefixSuffixHex': heartbeatPrefixSuffixHex,
                'protocolInstance.heartbeatPrefix': heartbeatPrefix,
                'protocolInstance.heartbeatSuffix': heartbeatSuffix,
                'protocolInstance.packetSendInterval': packetSendInterval,
                'protocolInstance.sort': sort
            },
            dataType: 'json',
            success: function (resp) {
                mini.unmask(document.body);
                if (resp.msg === true) {
                    // 通知父页面：新增实例的高亮名称 + 刷新实例列表树
                    if (window._parentSetNewInstanceName) {
                        window._parentSetNewInstanceName(instanceName);
                    }
                    if (window._parentRefreshInstanceTree) {
                        window._parentRefreshInstanceTree();
                    }
                    mini.alert(_loginUserLanguageResource.addedSuccessfully, function () {
                        window.CloseOwnerWindow('ok');
                    });
                } else {
                    mini.alert('<font color="red">' + (_loginUserLanguageResource.addFailure) + '</font>');
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

    // ================================================================
    // 6. 初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();

        // 采集协议类型下拉数据
        var acqComb = mini.get('acqProtocolTypeComb');
        acqComb.setData([
            { value: 'modbus-tcp',    text: 'modbus-tcp' },
            { value: 'modbus-rtu',    text: 'modbus-rtu' },
            { value: 'private-kd93',  text: 'private-kd93' },
            { value: 'private-lq1000',text: 'private-lq1000' },
            { value: 'private-g771',  text: 'private-g771' }
        ]);
        acqComb.setValue('modbus-tcp');

        // 控制协议类型下拉数据
        var ctrlComb = mini.get('ctrlProtocolTypeComb');
        ctrlComb.setData([
            { value: 'modbus-tcp', text: 'modbus-tcp' },
            { value: 'modbus-rtu', text: 'modbus-rtu' }
        ]);
        ctrlComb.setValue('modbus-tcp');

        // 若已存在待处理数据，立即处理
        if (_pendingData) {
            processSetData();
        }
    });

    // ================================================================
    // 7. 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.addAcqInstance;

        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save);
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(_loginUserLanguageResource.cancel);

        // 标签文本
        document.getElementById('lblAcqUnit').textContent = _loginUserLanguageResource.acqUnit;
        document.getElementById('lblInstanceName').textContent = _loginUserLanguageResource.instanceName;
        document.getElementById('lblAcqProtocolType').textContent = _loginUserLanguageResource.acqProtocolType;
        document.getElementById('lblCtrlProtocolType').textContent = _loginUserLanguageResource.ctrlProtocolType;
        document.getElementById('lblSignInPrefixSuffixHex').textContent = _loginUserLanguageResource.signInPrefixSuffixHex;
        document.getElementById('lblSignInPrefix').textContent = _loginUserLanguageResource.signInPrefix;
        document.getElementById('lblSignInSuffix').textContent = _loginUserLanguageResource.signInSuffix;
        document.getElementById('lblSignInIDHex').textContent = _loginUserLanguageResource.signInIDHex;
        document.getElementById('lblHeartbeatPrefixSuffixHex').textContent = _loginUserLanguageResource.heartbeatPrefixSuffixHex;
        document.getElementById('lblHeartbeatPrefix').textContent = _loginUserLanguageResource.heartbeatPrefix;
        document.getElementById('lblHeartbeatSuffix').textContent = _loginUserLanguageResource.heartbeatSuffix;
        document.getElementById('lblPacketSendInterval').textContent = _loginUserLanguageResource.packetSendInterval + '(ms)';
        document.getElementById('lblSort').textContent = _loginUserLanguageResource.sequenceNumber;

        // 采集单元树的空文本
        var treeSelect = mini.get('acqUnitTreeSelect');
        if (treeSelect) {
            treeSelect.setEmptyText(_loginUserLanguageResource.selectAcqUnit + '...');
        }
    }
</script>
</body>
</html>