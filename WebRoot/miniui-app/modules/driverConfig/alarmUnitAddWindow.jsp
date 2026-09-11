<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加报警单元</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .mini-form { width: 100%; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 6px 8px; }
        .label { text-align: right; width: 100px; font-weight: bold; }
        .mini-textbox, .mini-combobox, .mini-spinner, .mini-textarea { width: 100%; }
        .mini-textarea { min-height: 60px; }
        .tip-area { color: red; padding: 5px 0; display: none; }
    </style>
</head>
<body>
<div style="padding:10px;">
    <form id="alarmUnitForm" class="mini-form">
        <!-- 无可用协议时的提示 -->
        <div id="noProtocolTip" class="tip-area"></div>

        <table class="form-table">
            <tr>
                <td class="label"><span style="color:red;">*</span>协议：</td>
                <td>
                    <input id="protocolComb" class="mini-combobox"
                           style="width:100%;"
                           textField="boxval"
                           valueField="boxkey"
                           required="true"
                           allowInput="false"
                           showNullItem="true"
                           nullItemText="-- 请选择协议 --" />
                </td>
            </tr>
            <tr>
                <td class="label"><span style="color:red;">*</span>单元名称：</td>
                <td><input id="unitName" class="mini-textbox" required="true" style="width:100%;" onblur="checkUnitName()" /></td>
            </tr>
            <tr>
                <td class="label"><span style="color:red;">*</span>计算类型：</td>
                <td>
                    <input id="calculateTypeComb" class="mini-combobox"
                           style="width:100%;"
                           valueField="value"
                           textField="text"
                           value="0"
                           required="true"
                           allowInput="false" />
                </td>
            </tr>
            <tr>
                <td class="label">排序：</td>
                <td><input id="sort" class="mini-spinner" style="width:100%;" minValue="1" value="1" /></td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td><input id="remark" class="mini-textarea" style="width:100%;height:60px;" /></td>
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
    var deviceTypeIds = '';
    var protocolList = '';
    var _unitNameValid = false;

    // ---- 父窗口调用设置数据 ----
    function setData(data) {
        deviceTypeIds = data.deviceTypeIds || '';
        protocolList = data.protocolList || '';
        loadProtocolList();
    }

    // ---- 加载协议下拉 ----
    function loadProtocolList() {
        var combo = mini.get('protocolComb');
        if (!combo) return;
        $.ajax({
            url: context + '/acquisitionUnitManagerController/getModbusProtoclCombList',
            type: 'POST',
            data: {
                deviceTypeIds: deviceTypeIds,
                protocol: protocolList
            },
            dataType: 'json',
            success: function(result) {
                var list = result.list || [];
                if (list.length === 0) {
                    document.getElementById('noProtocolTip').style.display = 'block';
                    document.getElementById('noProtocolTip').innerHTML = _loginUserLanguageResource.protocolDoesNotExist;
                    combo.disable();
                    mini.get('btnSave').disable();
                } else {
                    document.getElementById('noProtocolTip').style.display = 'none';
                    combo.setData(list);
                    combo.enable();
                    mini.get('btnSave').enable();
                }
            },
            error: function() {
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    // ---- 单元名称校验 ----
    function checkUnitName() {
        var nameInput = mini.get('unitName');
        var name = nameInput.getValue();
        var protocol = mini.get('protocolComb').getValue();
        if (!name || name.trim() === '' || !protocol) {
            _unitNameValid = false;
            return;
        }
        $.ajax({
            url: context + '/acquisitionUnitManagerController/judgeAlarmUnitExistOrNot',
            type: 'POST',
            data: {
                protocolCode: protocol,
                unitName: name
            },
            dataType: 'json',
            success: function(resp) {
                if (parseInt(resp.msg) === 1) {
                    mini.confirm(
                        '<font color="red">' + _loginUserLanguageResource.alarmUnitExist + '</font>，' +
                        _loginUserLanguageResource.pleaseConfirm,
                        _loginUserLanguageResource.confirm,
                        function(action) {
                            if (action === 'ok') {
                                nameInput.focus();
                                nameInput.selectText();
                            }
                        }
                    );
                    _unitNameValid = false;
                } else {
                    _unitNameValid = true;
                }
            },
            error: function() {
                _unitNameValid = false;
            }
        });
    }

    // ---- 保存 ----
    function onSave() {
        var form = new mini.Form('#alarmUnitForm');
        form.validate();
        if (!form.isValid()) {
            mini.alert(_loginUserLanguageResource.required);
            return;
        }
        if (!_unitNameValid) {
            mini.alert('<font color="red">' + _loginUserLanguageResource.alarmUnitExist + '</font>');
            mini.get('unitName').focus();
            return;
        }

        var protocol = mini.get('protocolComb').getValue();
        var unitName = mini.get('unitName').getValue();
        var calculateType = mini.get('calculateTypeComb').getValue() || 0;
        var sort = mini.get('sort').getValue() || '';
        var remark = mini.get('remark').getValue() || '';

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.submittingData });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/doAlarmUnitAdd',
            type: 'POST',
            data: {
                protocol: protocol,
                unitName: unitName,
                unitCode: '',
                calculateType: calculateType,
                sort: sort,
                remark: remark
            },
            dataType: 'json',
            success: function(resp) {
                mini.unmask(document.body);
                if (resp.msg === true) {
                    // 设置新增标记
                    if (window.parent._parentSetNewObject) {
                        window.parent._parentSetNewObject(unitName, 3);
                    }
                    if (window.parent._parentRefreshUnitTree) {
                        window.parent._parentRefreshUnitTree();
                    }
                    mini.alert(_loginUserLanguageResource.addedSuccessfully, function() {
                        window.CloseOwnerWindow('ok');
                    });
                } else {
                    mini.alert('<font color="red">' + _loginUserLanguageResource.addFailure + '</font>');
                }
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.exceptionThrow + ': ' + _loginUserLanguageResource.contactAdmin);
            }
        });
    }

    // ---- 取消 ----
    function onCancel() {
        window.CloseOwnerWindow('cancel');
    }

    // ---- 国际化初始化 ----
    $(document).ready(function() {
        mini.parse();

        document.title = _loginUserLanguageResource.addAlarmUnit;

        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save);
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(_loginUserLanguageResource.cancel);

        // 标签
        var labels = document.querySelectorAll('.label');
        if (labels.length >= 5) {
            labels[0].innerHTML = _loginUserLanguageResource.protocolName + '<span style="color:red;">*</span>';
            labels[1].innerHTML = _loginUserLanguageResource.unitName + '<span style="color:red;">*</span>';
            labels[2].innerHTML = _loginUserLanguageResource.calculationType + '<span style="color:red;">*</span>';
            labels[3].innerHTML = _loginUserLanguageResource.sequenceNumber;
            labels[4].innerHTML = _loginUserLanguageResource.unitDescription;
        }

        // 计算类型下拉
        var calcComb = mini.get('calculateTypeComb');
        calcComb.setData([
            { value: 0, text: _loginUserLanguageResource.nothing },
            { value: 1, text: _loginUserLanguageResource.SRPCalculate },
            { value: 2, text: _loginUserLanguageResource.PCPCalculate }
        ]);
    });
</script>
</body>
</html>