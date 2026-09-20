<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加报表实例</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .mini-form { width: 100%; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 4px 8px; vertical-align: middle; }
        .label { text-align: right; width: 150px; font-weight: bold; white-space: nowrap; }
        .mini-textbox, .mini-combobox, .mini-spinner { width: 100% !important; }
        .tip-area { color: red; padding: 5px 0 10px 5px; display: none; font-size: 13px; }
    </style>
</head>
<body>
<div style="padding:10px;">
    <form id="instanceForm" class="mini-form">
        <table class="form-table">
            <!-- 中文名称 -->
            <tr id="rowZhCN">
                <td class="label"><span style="color:red;">*</span><span id="lblNameZhCN"></span>：</td>
                <td><input id="nameZhCN" class="mini-textbox" style="width:100%;" onblur="checkInstanceName(this)" /></td>
            </tr>
            <!-- 英文名称 -->
            <tr id="rowEn">
                <td class="label"><span style="color:red;">*</span><span id="lblNameEn"></span>：</td>
                <td><input id="nameEn" class="mini-textbox" style="width:100%;" onblur="checkInstanceName(this)" /></td>
            </tr>
            <!-- 俄文名称 -->
            <tr id="rowRu">
                <td class="label"><span style="color:red;">*</span><span id="lblNameRu"></span>：</td>
                <td><input id="nameRu" class="mini-textbox" style="width:100%;" onblur="checkInstanceName(this)" /></td>
            </tr>
            <!-- 报表单元 -->
            <tr>
                <td class="label"><span style="color:red;">*</span><span id="lblReportUnit"></span>：</td>
                <td>
                    <input id="reportUnitComb" class="mini-combobox"
                           style="width:100%;"
                           dataField="list"
                           totalField="totals"
                           textField="boxval" valueField="boxkey"
                           required="true" allowInput="false"
                           showNullItem="true"
                           nullItemText="-- 请选择报表单元 --"
                           onbeforeload="onReportUnitComboBeforeLoad"
                           onload="onReportUnitComboLoad" />
                </td>
            </tr>
            <!-- 序号 -->
            <tr>
                <td class="label"><span id="lblSort"></span>：</td>
                <td><input id="sort" class="mini-spinner" style="width:100%;" minValue="1" maxValue="9999999999"/></td>
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
    var _deviceTypeName = '';
    var _pendingData = null;

    // 每个语言字段的校验状态
    var _nameValid = { zh: true, en: true, ru: true };

    // ================================================================
    // 1. 父窗口调用入口
    // ================================================================
    function setData(data) {
        _pendingData = data;
        if (document.readyState === 'complete') {
            processSetData();
        } else {
            $(document).ready(function () { processSetData(); });
        }
    }

    function processSetData() {
        if (!_pendingData) return;
        var data = _pendingData;
        _pendingData = null;

        _deviceTypeIds = data.deviceTypeIds || '';
        _deviceTypeName = data.deviceTypeName || '';

        // 加载报表单元下拉
        var combo = mini.get('reportUnitComb');
        if (combo) {
            combo.load(context + '/acquisitionUnitManagerController/getReportUnitCombList');
        }
    }

    // ================================================================
    // 2. 报表单元下拉
    // ================================================================
    function onReportUnitComboBeforeLoad(e) {
        // 无参
    }

    function onReportUnitComboLoad(e) {
        var combo = e.sender;
        var data = combo.getData();
    }

    // ================================================================
    // 3. 实例名称查重
    // ================================================================
    function checkInstanceName(input) {
        var name = input.getValue();
        if (!name || name.trim() === '') {
            // 空值：不查重，保存时由表单校验拦截
            return;
        }
        $.ajax({
            url: context + '/acquisitionUnitManagerController/judgeReportInstanceExistOrNot',
            type: 'POST',
            data: { instanceName: name },
            dataType: 'json',
            success: function (resp) {
                if (parseInt(resp.msg) === 1) {
                    mini.confirm(
                        '<font color="red">' + (_loginUserLanguageResource.reportInstanceExist ) +
                        '</font>，' + (_loginUserLanguageResource.pleaseConfirm),
                        _loginUserLanguageResource.confirm,
                        function (action) {
                            if (action === 'ok') {
                                input.focus();
                                input.selectText();
                            }
                        }
                    );
                }
            }
        });
    }

    // ================================================================
    // 4. 保存
    // ================================================================
    function onSave() {
        var nameZhCN = mini.get('nameZhCN').getValue() || '';
        var nameEn = mini.get('nameEn').getValue() || '';
        var nameRu = mini.get('nameRu').getValue() || '';

        // 根据当前语言判断必填
        var lang = (typeof _loginUserLanguage !== 'undefined' ? _loginUserLanguage : '').toUpperCase();
        var currentName = '';
        if (lang === 'ZH_CN') currentName = nameZhCN;
        else if (lang === 'EN') currentName = nameEn;
        else if (lang === 'RU') currentName = nameRu;

        if (!currentName || currentName.trim() === '') {
            mini.alert(_loginUserLanguageResource.required || '请完善表单数据');
            return;
        }

        var unitId = mini.get('reportUnitComb').getValue();
        if (!unitId) {
            mini.alert('<font color="red">' + (_loginUserLanguageResource.selectReportUnit || '请选择报表单元') + '</font>');
            return;
        }

        var sort = mini.get('sort').getValue() || '';

        var mask = mini.mask({
            el: document.body,
            html: _loginUserLanguageResource.submittingData
        });

        $.ajax({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolReportInstanceAdd',
            type: 'POST',
            data: {
                'protocolReportInstance.name_zh_CN': nameZhCN,
                'protocolReportInstance.name_en': nameEn,
                'protocolReportInstance.name_ru': nameRu,
                'protocolReportInstance.unitId': unitId,
                'protocolReportInstance.sort': sort
            },
            dataType: 'json',
            success: function (resp) {
                mini.unmask(document.body);
                if (resp.msg === true) {
                    if (window._parentSetNewInstanceName) {
                        window._parentSetNewInstanceName(currentName);
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
    // 5. 初始化：按语言显示/隐藏名称字段
    // ================================================================s
    $(document).ready(function () {
        mini.parse();

        // 根据 _loginUserLanguageList 显示/隐藏语言字段
        var langList = (typeof _loginUserLanguageList !== 'undefined' && _loginUserLanguageList) ? _loginUserLanguageList : [];

        var showZh = isExist(langList, 1) > 0;
        var showEn = isExist(langList, 2) > 0;
        var showRu = isExist(langList, 3) > 0;

        document.getElementById('rowZhCN').style.display = showZh ? '' : 'none';
        document.getElementById('rowEn').style.display = showEn ? '' : 'none';
        document.getElementById('rowRu').style.display = showRu ? '' : 'none';

        initI18n();

        if (_pendingData) processSetData();
    });

    function initI18n() {
        document.title = _loginUserLanguageResource.addReportInstance;

        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save);
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(_loginUserLanguageResource.cancel);

        document.getElementById('lblNameZhCN').textContent = _loginUserLanguageResource.instanceName + '(' + _loginUserLanguageResource.language_zh_CN + ')';
        document.getElementById('lblNameEn').textContent = _loginUserLanguageResource.instanceName + '(' + _loginUserLanguageResource.language_en + ')';
        document.getElementById('lblNameRu').textContent = _loginUserLanguageResource.instanceName + '(' + _loginUserLanguageResource.language_ru + ')';
        document.getElementById('lblReportUnit').textContent = _loginUserLanguageResource.reportUnit;
        document.getElementById('lblSort').textContent = _loginUserLanguageResource.sequenceNumber;

        var combo = mini.get('reportUnitComb');
        if (combo) {
            combo.setEmptyText('-- ' + _loginUserLanguageResource.selectReportUnit + ' --');
        }
    }
</script>
</body>
</html>