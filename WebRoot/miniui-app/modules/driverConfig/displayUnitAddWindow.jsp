<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加显示单元</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 6px 8px; }
        .label { text-align: right; width: 120px; font-weight: bold; }
        .mini-textbox, .mini-combobox, .mini-spinner, .mini-textarea { width: 100%; }
        .mini-textarea { min-height: 60px; }
        .tip-area { color: red; padding: 5px 0; display: none; }
        .btn-row { text-align: center; padding-top: 15px; border-top: 1px solid #e8e8e8; margin-top: 15px; }
        .btn-row .mini-button { margin: 0 10px; width: 80px; }
    </style>
</head>
<body>
<div style="padding:10px;">
    <form id="displayUnitForm" class="mini-form">
        <table class="form-table">
            <tr>
                <td class="label" id="lblProtocol"><span style="color:red;">*</span>协议：</td>
                <td>
                    <input id="protocolComb" class="mini-combobox" 
                           name="protocol"
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
                <td class="label" id="lblAcqUnit"><span style="color:red;">*</span>采控单元：</td>
                <td>
                    <input id="acqUnitComb" class="mini-combobox" 
                           name="acqUnitId"
                           style="width:100%;" 
                           textField="boxval" 
                           valueField="boxkey" 
                           required="true" 
                           allowInput="false" 
                           showNullItem="true"
                           nullItemText="-- 请选择采控单元 --" />
                </td>
            </tr>
            <tr>
                <td class="label" id="lblUnitName"><span style="color:red;">*</span>单元名称：</td>
                <td><input id="unitName" class="mini-textbox" name="unitName" required="true" style="width:100%;" onblur="checkUnitName()" /></td>
            </tr>
            <tr>
                <td class="label" id="lblCalcType"><span style="color:red;">*</span>计算类型：</td>
                <td>
                    <input id="calcTypeComb" class="mini-combobox" 
                           name="calculateType"
                           style="width:100%;" 
                           valueField="value" 
                           textField="text"
                           value="0"
                           required="true" 
                           allowInput="false" />
                </td>
            </tr>
            <tr>
                <td class="label" id="lblSort">排序：</td>
                <td><input id="sort" class="mini-spinner" name="sort" style="width:100%;" minValue="1" /></td>
            </tr>
            <tr>
                <td class="label" id="lblRemark">备注：</td>
                <td><input id="remark" class="mini-textarea" name="remark" style="width:100%;height:60px;" /></td>
            </tr>
        </table>
        <div class="btn-row">
            <button id="btnSave" class="mini-button" onclick="onSave()">保存</button>
            <button id="btnCancel" class="mini-button" onclick="onCancel()">取消</button>
        </div>
    </form>
</div>

<script>
    var context = '<%=context%>';
    var _deviceTypeIds = '';
    var _protocolList = '';
    var _unitNameValid = false;
    var _pendingData = null;

    // ---- 延迟初始化数据 ----
    function setData(data) {
        _pendingData = data;
        if (document.readyState === 'complete') {
            processSetData();
        } else {
            $(document).ready(function() {
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
                deviceTypeIds: _deviceTypeIds,
                protocol: _protocolList
            },
            dataType: 'json',
            success: function(result) {
                var list = result.list || [];
                combo.setData(list);
            },
            error: function() {}
        });
    }

    // ---- 加载采控单元下拉 ----
    function loadAcqUnitList() {
        var combo = mini.get('acqUnitComb');
        if (!combo) return;
        var protocol = mini.get('protocolComb').getValue();
        if (!protocol) {
            combo.setData([]);
            return;
        }
        $.ajax({
            url: context + '/acquisitionUnitManagerController/getAcquisitionUnitCombList',
            type: 'POST',
            data: {
                protocol: protocol,
                deviceTypeIds: _deviceTypeIds,
                selectedProtocol: _protocolList
            },
            dataType: 'json',
            success: function(result) {
                var list = result.list || [];
                combo.setData(list);
            },
            error: function() {}
        });
    }

    // ---- 协议变更时重新加载采控单元 ----
    function onProtocolChange(e) {
        mini.get('acqUnitComb').setValue('');
        loadAcqUnitList();
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
            url: context + '/acquisitionUnitManagerController/judgeDisplayUnitExistOrNot',
            type: 'POST',
            data: {
                protocolCode: protocol,
                unitName: name
            },
            dataType: 'json',
            success: function(resp) {
                if (resp.msg === 1) {
                    mini.confirm(
                        '<font color="red">' + (_loginUserLanguageResource.acqUnitExist) + '，' + (_loginUserLanguageResource.pleaseConfirm) + '</font>',
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
        var form = new mini.Form('#displayUnitForm');
        form.validate();
        if (!form.isValid()) {
            mini.alert(_loginUserLanguageResource.required);
            return;
        }
        if (!_unitNameValid) {
            mini.get('unitName').focus();
            return;
        }

        var data = form.getData();
        data.unitCode="";
        // 补充空值处理
        data.sort = data.sort || '';
        data.remark = data.remark || '';

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.submittingData });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/doDisplayUnitAdd',
            type: 'POST',
            data: data,
            dataType: 'json',
            success: function(resp) {
                mini.unmask(document.body);
                if (resp.msg === true) {
                    if (window.parent._parentSetNewObject) {
                        window.parent._parentSetNewObject(data.unitName, 2);
                    }
                    if (window.parent._parentRefreshUnitTree) {
                        window.parent._parentRefreshUnitTree();
                    }
                    mini.alert(_loginUserLanguageResource.addedSuccessfully, function() {
                        window.CloseOwnerWindow('ok');
                    });
                } else {
                    mini.alert('<font color="red">' + (_loginUserLanguageResource.addFailure) + '</font>');
                }
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert((_loginUserLanguageResource.exceptionThrow) + ': ' + (_loginUserLanguageResource.contactAdmin));
            }
        });
    }

    // ---- 取消 ----
    function onCancel() {
        window.CloseOwnerWindow('cancel');
    }

    // ---- DOM 就绪 ----
    $(document).ready(function() {
        mini.parse();

        // 协议变更事件
        var protocolComb = mini.get('protocolComb');
        if (protocolComb) {
            protocolComb.on('valuechanged', onProtocolChange);
        }

        // ---- 国际化 ----
        document.getElementById('lblProtocol').innerHTML = (_loginUserLanguageResource.protocolName) + '<span style="color:red;">*</span>';
        document.getElementById('lblAcqUnit').innerHTML = (_loginUserLanguageResource.acqUnit) + '<span style="color:red;">*</span>';
        document.getElementById('lblUnitName').innerHTML = (_loginUserLanguageResource.unitName) + '<span style="color:red;">*</span>';
        document.getElementById('lblCalcType').innerHTML = (_loginUserLanguageResource.calculationType) + '<span style="color:red;">*</span>';
        document.getElementById('lblSort').innerHTML = _loginUserLanguageResource.sequenceNumber;
        document.getElementById('lblRemark').innerHTML = _loginUserLanguageResource.unitDescription;

        // 计算类型下拉数据
        var calcComb = mini.get('calcTypeComb');
        calcComb.setData([
            {value: 0, text: _loginUserLanguageResource.nothing},
            {value: 1, text: _loginUserLanguageResource.SRPCalculate},
            {value: 2, text: _loginUserLanguageResource.PCPCalculate}
        ]);

        // 按钮文本
        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save);
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(_loginUserLanguageResource.cancel);

        // 处理待处理数据
        if (_pendingData) {
            processSetData();
        }
    });
</script>
</body>
</html>