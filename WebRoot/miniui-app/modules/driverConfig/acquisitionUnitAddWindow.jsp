<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加采集单元</title>
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
    <form id="unitForm" class="mini-form">
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
                <td><input id="unitName" class="mini-textbox" required="true" vtype="string" style="width:100%;" onblur="checkUnitName()" /></td>
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

    function setData(data) {
        deviceTypeIds = data.deviceTypeIds || '';
        protocolList = data.protocolList || '';
        loadProtocolList();
    }

    function loadProtocolList() {
        var combo = mini.get('protocolComb');
        if (!combo) return;
        // 加载协议下拉列表
        $.ajax({
            url: context + '/acquisitionUnitManagerController/getModbusProtoclCombList',
            type: 'POST',
            data: {
                deviceTypeIds: deviceTypeIds,
                protocol: protocolList  // 传递当前选中的协议，用于过滤
            },
            dataType: 'json',
            success: function(result) {
                var list = result.list || [];
                if (list.length === 0) {
                    document.getElementById('noProtocolTip').style.display = 'block';
                    document.getElementById('noProtocolTip').innerHTML = '当前设备类型下无可用协议，请先添加协议';
                    combo.disable();
                    mini.get('btnSave').disable();
                } else {
                    document.getElementById('noProtocolTip').style.display = 'none';
                    combo.setData(list);
                    // 默认选中当前协议
                    //if (protocolCode) {
                    //    combo.setValue(protocolCode);
                    //} else {
                    //    combo.setValue(list[0].boxkey);
                    //}
                    combo.enable();
                    mini.get('btnSave').enable();
                }
            },
            error: function() {
                mini.alert('加载协议列表失败');
            }
        });
    }

    function checkUnitName() {
        var nameInput = mini.get('unitName');
        var name = nameInput.getValue();
        var protocol = mini.get('protocolComb').getValue();
        if (!name || name.trim() === '' || !protocol) {
            _unitNameValid = false;
            return;
        }
        $.ajax({
            url: context + '/acquisitionUnitManagerController/judgeAcqUnitExistOrNot',
            type: 'POST',
            data: {
                protocolCode: protocol,
                unitName: name
            },
            dataType: 'json',
            success: function(resp) {
                if (resp.msg === '1') {
                    mini.confirm(
                        '<font color="red">' + (_loginUserLanguageResource.acqUnitExist || '单元已存在') + '，' + (_loginUserLanguageResource.pleaseConfirm || '请确认') + '</font>',
                        _loginUserLanguageResource.confirm || '确认',
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

    function onSave() {
        var form = new mini.Form('#unitForm');
        form.validate();
        if (!form.isValid()) {
            mini.alert(_loginUserLanguageResource.required || '请完善表单数据');
            return;
        }
        if (!_unitNameValid) {
            mini.alert('<font color="red">' + (_loginUserLanguageResource.acqUnitExist || '单元名称无效') + '，请重新输入</font>');
            mini.get('unitName').focus();
            return;
        }

        var protocol = mini.get('protocolComb').getValue();
        var unitName = mini.get('unitName').getValue();
        var sort = mini.get('sort').getValue();
        var remark = mini.get('remark').getValue();

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.submittingData || '提交中...' });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/doAcquisitionUnitAdd',
            type: 'POST',
            data: {
                protocol: protocol,
                unitName: unitName,
                unitCode: '',
                sort: sort,
                remark: remark || ''
            },
            dataType: 'json',
            success: function(resp) {
                mini.unmask(document.body);
                if (resp.msg === true) {
                    // 设置新增标记
                    if (window._parentSetNewObject) {
                        window._parentSetNewObject(unitName,2);
                    }
                    if (window._parentRefreshUnitTree) {
                        window._parentRefreshUnitTree();
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

    function onCancel() {
        window.CloseOwnerWindow('cancel');
    }

    $(document).ready(function() {
        mini.parse();
        // 国际化
        document.title = _loginUserLanguageResource.addAcqUnit || '添加采集单元';
        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save || '保存');
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(_loginUserLanguageResource.cancel || '取消');
        // 标签国际化
        var labels = document.querySelectorAll('.label');
        if (labels.length >= 4) {
            labels[0].innerHTML = (_loginUserLanguageResource.protocolName || '协议') + '<span style="color:red;">*</span>';
            labels[1].innerHTML = (_loginUserLanguageResource.unitName || '单元名称') + '<span style="color:red;">*</span>';
            labels[2].innerHTML = _loginUserLanguageResource.sequenceNumber || '排序';
            labels[3].innerHTML = _loginUserLanguageResource.unitDescription || '备注';
        }
    });
</script>
</body>
</html>