<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cosog.model.User" %>
<%
String path = request.getContextPath();
User userLogin = (User)session.getAttribute("userLogin");
String loginUserLanguage = userLogin != null ? userLogin.getLanguageName() + "" : "zh_CN";
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加设备标签</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0;
            width: 100%; height: 100%;
            overflow: hidden;
            background: #f5f5f5;
            font-family: "Microsoft YaHei", Arial, sans-serif;
        }
        .add-form-wrap { padding: 16px 20px; }
        .add-form-wrap .mini-textbox,
        .add-form-wrap .mini-combobox,
        .add-form-wrap .mini-spinner {
            width: 220px;
        }
        .add-form-wrap td.label {
            text-align: right;
            padding: 4px 8px;
            width: 110px;
            font-size: 12px;
            color: #333;
        }
        .add-form-wrap td.input-cell {
            padding: 4px 8px;
        }
        .required-star { color: red; }
    </style>
</head>
<body>

<div id="addDeviceTabPanel" class="mini-panel"
     style="width:100%;height:100%;"
     showHeader="false" showToolbar="true" showCloseButton="false"
     bodyStyle="padding:0;">
    <div property="toolbar">
        <table style="width:100%;border-collapse:collapse;">
            <tr>
                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                    <button id="addSaveBtn" class="mini-button" iconCls="save" plain="true"
                            onclick="onAddSave()"></button>
                    <button id="addCancelBtn" class="mini-button" iconCls="cancel" plain="true"
                            onclick="onAddCancel()"></button>
                </td>
            </tr>
        </table>
    </div>

    <div class="add-form-wrap">
        <table style="width:100%;border-collapse:collapse;">
            <!-- ★ 三行：中文/英文/俄文，只显示当前语言对应的那一行 -->
            <tr id="rowName_zh_CN" style="display:none;">
                <td class="label" id="lblName_zh_CN"></td>
                <td class="input-cell">
                    <input id="add_name_zh_CN" class="mini-textbox" />
                </td>
            </tr>
            <tr id="rowName_en" style="display:none;">
                <td class="label" id="lblName_en"></td>
                <td class="input-cell">
                    <input id="add_name_en" class="mini-textbox" />
                </td>
            </tr>
            <tr id="rowName_ru" style="display:none;">
                <td class="label" id="lblName_ru"></td>
                <td class="input-cell">
                    <input id="add_name_ru" class="mini-textbox" />
                </td>
            </tr>

            <tr>
                <td class="label" id="lblCalculateType"></td>
                <td class="input-cell">
                    <input id="add_calculateType" class="mini-combobox"
                           valueField="id" textField="text" allowInput="false" />
                </td>
            </tr>
            <tr>
                <td class="label" id="lblSort"></td>
                <td class="input-cell">
                    <input id="add_sort" class="mini-spinner" minValue="1" maxValue="9999999999" />
                </td>
            </tr>
        </table>
    </div>
</div>

<script>
    var context = '<%=path%>';
    var loginUserLanguage = '<%=loginUserLanguage%>';

    // ★ 父窗口通过 contentWindow.setData(...) 调用
    function setData(data) {
        data = data || {};
        // 设备标签添加无需上下文参数，此方法保留作为约定
    }

    // ============================================================
    // 初始化国际化 + 语言字段显隐
    // ============================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        var lang = (loginUserLanguage || 'ZH_CN').toUpperCase();

        document.title = R.addDeviceTagInstance || R.deviceTag;

        // ★ 按当前语言决定显示哪一行
        var rowZh = document.getElementById('rowName_zh_CN');
        var rowEn = document.getElementById('rowName_en');
        var rowRu = document.getElementById('rowName_ru');

        rowZh.style.display = 'none';
        rowEn.style.display = 'none';
        rowRu.style.display = 'none';

        if (lang === 'ZH_CN') {
            rowZh.style.display = '';
        } else if (lang === 'EN') {
            rowEn.style.display = '';
        } else if (lang === 'RU') {
            rowRu.style.display = '';
        }

        // 三个 label 的文本
        var zhLbl = document.getElementById('lblName_zh_CN');
        if (zhLbl) zhLbl.innerHTML = R.language_zh_CN + '<span class="required-star">*</span>：';
        var enLbl = document.getElementById('lblName_en');
        if (enLbl) enLbl.innerHTML = R.language_en + '：';
        var ruLbl = document.getElementById('lblName_ru');
        if (ruLbl) ruLbl.innerHTML = R.language_ru + '：';

        var calcLbl = document.getElementById('lblCalculateType');
        if (calcLbl) calcLbl.innerHTML = R.calculationType + '：';

        var sortLbl = document.getElementById('lblSort');
        if (sortLbl) sortLbl.innerHTML = R.sequenceNumber + '：';

        // 按钮文本
        var saveBtn = mini.get('addSaveBtn');
        if (saveBtn) saveBtn.setText(R.save);
        var cancelBtn = mini.get('addCancelBtn');
        if (cancelBtn) cancelBtn.setText(R.cancel);

        // 计算类型下拉数据源（对齐 ExtJS）
        var calcCombo = mini.get('add_calculateType');
        if (calcCombo) {
            calcCombo.setData([
                { id: 0, text: R.nothing },
                { id: 1, text: R.SRPCalculate },
                { id: 2, text: R.PCPCalculate }
            ]);
            calcCombo.setValue(0);
        }
    }

    // ============================================================
    // 保存
    // ============================================================
    function onAddSave() {
        var R = _loginUserLanguageResource;
        var lang = (loginUserLanguage || 'ZH_CN').toUpperCase();

        var name_zh_CN = mini.get('add_name_zh_CN').getValue() || '';
        var name_en    = mini.get('add_name_en').getValue()    || '';
        var name_ru    = mini.get('add_name_ru').getValue()    || '';

        // ★ 只校验当前语言字段（对照 ExtJS allowBlank 逻辑）
        if (lang === 'ZH_CN' && !name_zh_CN) { mini.alert(R.required, R.tip); return; }
        if (lang === 'EN'    && !name_en)    { mini.alert(R.required, R.tip); return; }
        if (lang === 'RU'    && !name_ru)    { mini.alert(R.required, R.tip); return; }

        var params = {
            'deviceTabManager.name_zh_CN':    name_zh_CN,
            'deviceTabManager.name_en':       name_en,
            'deviceTabManager.name_ru':       name_ru,
            'deviceTabManager.calculateType': mini.get('add_calculateType').getValue(),
            'deviceTabManager.sort':          mini.get('add_sort').getValue()
        };

        mini.mask({ el: document.body, html: R.submittingData });

        $.ajax({
            url: context + '/operationMaintenanceController/addDeviceTabManagerInstance',
            type: 'POST',
            data: params,
            dataType: 'json',
            success: function (result) {
                mini.unmask(document.body);
                if (result && result.msg === true) {
                    mini.alert('<font color="blue">' + R.addedSuccessfully + '</font>',
                        R.tip, function () {

                            // ★ 参照 userAddWindow 约定：先调用父窗口回调刷新
                            try {
                                if (window.parent && typeof window.parent._parentRefreshDeviceTabList === 'function') {
                                    window.parent._parentRefreshDeviceTabList();
                                }
                            } catch (e) {
                                console.warn('_parentRefreshDeviceTabList 调用失败', e);
                            }

                            // ★ 再关闭自己
                            if (window.CloseOwnerWindow) {
                                window.CloseOwnerWindow('ok');
                            }
                        });
                } else {
                    mini.alert('<font color="red">' + R.addFailure + '</font>', R.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert('【<font color="red">' + R.exceptionThrow + '</font>】：' + R.contactAdmin, R.tip);
            }
        });
    }

    function onAddCancel() {
        if (window.CloseOwnerWindow) {
            window.CloseOwnerWindow('cancel');
        }
    }

    // ============================================================
    // 页面初始化
    // ============================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>