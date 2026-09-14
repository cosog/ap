<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加报表单元</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .mini-form { width: 100%; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 6px 8px; }
        .label { text-align: right; width: 100px; font-weight: bold; }
        .mini-textbox, .mini-combobox, .mini-spinner, .mini-textarea { width: 100%; }
        .tip-area { color: red; padding: 5px 0; display: none; }
    </style>
</head>
<body>
<div style="padding:10px;">
    <form id="reportUnitForm" class="mini-form">
        <table class="form-table">
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
                <td class="label"><span style="color:red;">*</span>报表类别：</td>
                <td>
                    <input id="reportClassesComb" class="mini-combobox"
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
    var deviceTypeIds = '';
    var _unitNameValid = false;

    // ---- 父窗口调用设置数据 ----
    function setData(data) {
        deviceTypeIds = (data && data.deviceTypeIds) || '';
    }

    // ---- 单元名称查重（后端接口与原 ExtJS 一致） ----
    function checkUnitName() {
        var nameInput = mini.get('unitName');
        var name = nameInput.getValue();
        if (!name || name.trim() === '') {
            _unitNameValid = false;
            return;
        }
        $.ajax({
            url: context + '/acquisitionUnitManagerController/judgeReportUnitExistOrNot',
            type: 'POST',
            data: { unitName: name },
            dataType: 'json',
            success: function(resp) {
                if (parseInt(resp.msg) === 1) {
                    mini.confirm(
                        '<font color="red">' + _loginUserLanguageResource.reportUnitExist + '</font>，' +
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
        var form = new mini.Form('#reportUnitForm');
        form.validate();
        if (!form.isValid()) {
            mini.alert(_loginUserLanguageResource.required);
            return;
        }
        if (!_unitNameValid) {
            mini.alert('<font color="red">' + _loginUserLanguageResource.reportUnitExist + '</font>');
            mini.get('unitName').focus();
            return;
        }

        var unitName = mini.get('unitName').getValue();
        var calculateType = mini.get('calculateTypeComb').getValue() || 0;
        var classes = mini.get('reportClassesComb').getValue() || 0;
        var sort = mini.get('sort').getValue() || '';

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.submittingData });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolReportUnitAdd',
            type: 'POST',
            data: {
                unitName: unitName,
                unitCode: '',
                calculateType: calculateType,
                classes: classes,
                sort: sort
            },
            dataType: 'json',
            success: function(resp) {
                mini.unmask(document.body);
                if (resp.msg === true) {
                    // 通知父页面：设置新增对象高亮 + 刷新树
                    if (window.parent._parentSetNewObject) {
                        window.parent._parentSetNewObject(unitName, 1);
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

        document.title = _loginUserLanguageResource.addReportUnit;

        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save);
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(_loginUserLanguageResource.cancel);

        // 标签国际化
        var labels = document.querySelectorAll('.label');
        if (labels.length >= 4) {
            labels[0].innerHTML = _loginUserLanguageResource.unitName + '<span style="color:red;">*</span>';
            labels[1].innerHTML = _loginUserLanguageResource.calculationType + '<span style="color:red;">*</span>';
            labels[2].innerHTML = _loginUserLanguageResource.reportClasses + '<span style="color:red;">*</span>';
            labels[3].innerHTML = _loginUserLanguageResource.sequenceNumber;
        }

        // 计算类型下拉（与原 ExtJS 值对应：0=无 1=功图计算 2=转速计产）
        var calcComb = mini.get('calculateTypeComb');
        calcComb.setData([
            { value: 0, text: _loginUserLanguageResource.nothing },
            { value: 1, text: _loginUserLanguageResource.SRPCalculate },
            { value: 2, text: _loginUserLanguageResource.PCPCalculate }
        ]);

        // 报表类别下拉（与原 ExtJS 值对应：0=标准报表 1=水文井报表）
        var classComb = mini.get('reportClassesComb');
        classComb.setData([
            { value: 0, text: _loginUserLanguageResource.standardReport },
            { value: 1, text: _loginUserLanguageResource.hydrologicalWellReport }
        ]);
    });
</script>
</body>
</html>