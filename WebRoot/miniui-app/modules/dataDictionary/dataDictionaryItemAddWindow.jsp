<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加数据项</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #fff;
        }
        .item-add-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
        }
        .item-add-body {
            flex: 1; min-height: 0;
            padding: 10px 14px;
            overflow: auto;
        }
        .item-add-footer {
            flex-shrink: 0; height: 44px;
            border-top: 1px solid #e0e0e0;
            background: #fafafa;
            display: flex; align-items: center;
            justify-content: flex-end;
            padding: 0 12px; gap: 8px;
            box-sizing: border-box;
        }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 6px 6px; vertical-align: top; }
        .form-table .label {
            text-align: right; white-space: nowrap;
            color: #333; font-size: 13px;
            padding-top: 9px; width: 110px;
        }
        .req-star { color: red; }
    </style>
</head>
<body>

<div class="item-add-container">
    <div class="item-add-body">
        <form id="itemForm" class="mini-form">
            <table class="form-table">
                <tr>
                    <td class="label"><span id="lblName"></span></td>
                    <td>
                        <input id="itemName_zh_CN" class="mini-textbox" style="width:100%;" />
                        <input id="itemName_en"    class="mini-textbox" style="width:100%;" />
                        <input id="itemName_ru"    class="mini-textbox" style="width:100%;" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><span id="lblCode"></span></td>
                    <td><input id="itemCode" class="mini-textbox" style="width:100%;" /></td>
                </tr>
                <tr>
                    <td class="label"><span id="lblDataValue"></span></td>
                    <td>
                        <textarea id="itemDataValue"
                                  class="mini-textarea"
                                  style="width:100%;height:90px;"></textarea>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span id="lblSorts"></span></td>
                    <td>
                        <input id="itemSorts" class="mini-spinner"
                               style="width:100%;"
                               minValue="0" maxValue="9999999999" value="1" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><span id="lblStatus"></span></td>
                    <td>
                        <input id="itemStatus" class="mini-radiobuttonlist" value="1" />
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div class="item-add-footer">
        <button id="okBtn" class="mini-button" iconCls="save" onclick="onOk()"></button>
        <button id="cancelBtn" class="mini-button" iconCls="cancel" onclick="onCancel()"></button>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var _currentLang = 'ZH_CN';

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.getElementById('lblName').innerHTML =
            R.fiedName + ' <span class="req-star">*</span>：';
        document.getElementById('lblCode').innerHTML =
            R.fieldCode + ' <span class="req-star">*</span>：';
        document.getElementById('lblDataValue').innerHTML =
            R.fieldParameter + '：';
        document.getElementById('lblSorts').innerHTML =
            R.sequenceNumber + '：';
        document.getElementById('lblStatus').innerHTML =
            R.enable + ' <span class="req-star">*</span>：';

        mini.get('okBtn').setText(R.confirm);
        mini.get('cancelBtn').setText(R.cancel);

        mini.get('itemStatus').setData([
            { id: 1, text: R.yes },
            { id: 0, text: R.no }
        ]);
        mini.get('itemStatus').setValue(1);
    }

    // ================================================================
    // 按当前语言切换显示名称输入框
    // ================================================================
    function setupNameFields() {
        var suffix = (_currentLang === 'EN') ? 'en'
                   : (_currentLang === 'RU') ? 'ru' : 'zh_CN';
        var all = ['zh_CN', 'en', 'ru'];
        for (var i = 0; i < all.length; i++) {
            var comp = mini.get('itemName_' + all[i]);
            if (!comp) continue;
            var el = comp.getEl();
            if (all[i] === suffix) {
                if (el) el.style.display = '';
                comp.setEnabled(true);
            } else {
                if (el) el.style.display = 'none';
                comp.setEnabled(false);
            }
        }
    }

    // ================================================================
    // 父窗口调用：设置上下文
    // ================================================================
    function setData(data) {
        _currentLang = (data && data.lang) ? data.lang.toUpperCase() : 'ZH_CN';
        setupNameFields();
    }

    // ================================================================
    // 确定：只把数据交给父窗口的本地表格
    // ================================================================
    function onOk() {
        var R = _loginUserLanguageResource;
        var suffix = (_currentLang === 'EN') ? 'en'
                   : (_currentLang === 'RU') ? 'ru' : 'zh_CN';

        var nameVal = (mini.get('itemName_' + suffix).getValue() || '').trim();
        if (!nameVal) { mini.alert(R.required, R.tip); return; }

        var codeVal = (mini.get('itemCode').getValue() || '').trim();
        if (!codeVal) { mini.alert(R.required, R.tip); return; }

        var item = {
            name_zh_CN: (mini.get('itemName_zh_CN').getValue() || '').trim(),
            name_en:    (mini.get('itemName_en').getValue()    || '').trim(),
            name_ru:    (mini.get('itemName_ru').getValue()    || '').trim(),
            code:       codeVal,
            datavalue:  (mini.get('itemDataValue').getValue() || ''),
            sorts:      mini.get('itemSorts').getValue(),
            status:     mini.get('itemStatus').getValue() == 1
        };

        if (window._parentAddItem) {
            window._parentAddItem(item);
        }
        window.CloseOwnerWindow('ok');
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