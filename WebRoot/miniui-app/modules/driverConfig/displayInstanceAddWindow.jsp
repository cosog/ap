<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加显示实例</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .mini-form { width: 100%; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 4px 8px; vertical-align: middle; }
        .label { text-align: right; width: 150px; font-weight: bold; white-space: nowrap; }
        .mini-textbox, .mini-spinner, .mini-treeselect { width: 100% !important; }
        .tip-area { color: red; padding: 5px 0 10px 5px; display: none; font-size: 13px; }
    </style>
</head>
<body>
<div style="padding:10px;">
    <form id="instanceForm" class="mini-form">
        <table class="form-table">
            <!-- 显示单元（树选择，禁止选中父节点） -->
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblDisplayUnit"></span>：</td>
                <td>
                    <input id="displayUnitTreeSelect" class="mini-treeselect"
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
                           onbeforeload="onDisplayUnitTreeBeforeLoad"
                           onload="onDisplayUnitTreeLoad"
                           onbeforenodeselect="onDisplayUnitTreeBeforeNodeSelect"
                           onvaluechanged="onDisplayUnitValueChanged" />
                    <input id="unitId" class="mini-hidden" />
                </td>
            </tr>

            <!-- 实例名称 -->
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblInstanceName"></span>：</td>
                <td><input id="instanceName" class="mini-textbox" required="true" style="width:100%;" onblur="checkInstanceName()" /></td>
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

        // 加载显示单元树
        var treeSelect = mini.get('displayUnitTreeSelect');
        if (treeSelect) {
            treeSelect.setUrl(context + '/acquisitionUnitManagerController/modbusProtocolAndDisplayUnitTreeData');
            treeSelect.load();
        }
    }

    // ================================================================
    // 2. 显示单元树事件
    // ================================================================
    function onDisplayUnitTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceTypeIds = _deviceTypeIds;
        params.protocol = _protocolList;
        e.params = params;
    }

    // 只允许选中显示单元节点（classes === 2）
    function onDisplayUnitTreeBeforeNodeSelect(e) {
        if (!e.node) {
            e.cancel = true;
            return;
        }
        var cls = parseInt(e.node.classes);
        if (isNaN(cls) || cls !== 2) {
            e.cancel = true;
        }
    }

    // 树加载完成
    function onDisplayUnitTreeLoad(e) {
        var treeSelect = e.sender;
        var data = treeSelect.getData() || [];
        // 预留：如需做"无单元时提示"等额外处理
    }

    // 树选择变化：更新隐藏的 unitId 字段
    function onDisplayUnitValueChanged(e) {
        var treeSelect = mini.get('displayUnitTreeSelect');
        var val = treeSelect.getValue();
        var unitIdField = mini.get('unitId');
        if (unitIdField) {
            unitIdField.setValue(val || '');
        }
    }

    // ================================================================
    // 3. 实例名称查重
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
            url: context + '/acquisitionUnitManagerController/judgeDisplayInstanceExistOrNot',
            type: 'POST',
            data: {
                instanceName: name,
                unitId: unitId
            },
            dataType: 'json',
            success: function (resp) {
                if (parseInt(resp.msg) === 1) {
                    mini.confirm(
                        '<font color="red">' + (_loginUserLanguageResource.displayInstanceExist || '实例已存在') +
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
    // 4. 保存
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
            mini.alert('<font color="red">' + (_loginUserLanguageResource.selectDisplayUnit || '请选择显示单元') + '</font>');
            return;
        }

        if (!_instanceNameValid) {
            mini.alert('<font color="red">' + (_loginUserLanguageResource.displayInstanceExist || '实例名称无效') + '，请重新输入</font>');
            mini.get('instanceName').focus();
            return;
        }

        var instanceName = mini.get('instanceName').getValue();
        var sort = mini.get('sort').getValue() || '';

        var mask = mini.mask({
            el: document.body,
            html: _loginUserLanguageResource.submittingData
        });

        $.ajax({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolDisplayInstanceAdd',
            type: 'POST',
            data: {
                'protocolDisplayInstance.DisplayUnitId': unitId,
                'protocolDisplayInstance.name': instanceName,
                'protocolDisplayInstance.sort': sort
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
    // 5. 初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();

        // 若已存在待处理数据，立即处理
        if (_pendingData) {
            processSetData();
        }
    });

    // ================================================================
    // 6. 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.addDisplayInstance;

        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save);
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(_loginUserLanguageResource.cancel);

        // 标签文本
        document.getElementById('lblDisplayUnit').textContent = _loginUserLanguageResource.displayUnit;
        document.getElementById('lblInstanceName').textContent = _loginUserLanguageResource.instanceName;
        document.getElementById('lblSort').textContent = _loginUserLanguageResource.sequenceNumber;

        // 显示单元树的空文本
        var treeSelect = mini.get('displayUnitTreeSelect');
        if (treeSelect) {
            treeSelect.setEmptyText(_loginUserLanguageResource.selectDisplayUnit + '...');
        }
    }
</script>
</body>
</html>