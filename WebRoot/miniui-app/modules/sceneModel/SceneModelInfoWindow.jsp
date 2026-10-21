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
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0;
            width: 100%; height: 100%;
            overflow: hidden;
            background: #f5f5f5;
            font-family: "Microsoft YaHei", Arial, sans-serif;
        }

        /* ============ 外层：flex 上下布局 ============ */
        .main-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
        }
        .main-body {
            flex: 1; min-height: 0;
            overflow: hidden;
        }

        /* ============ 底部按钮区 ============ */
        .footer {
            flex-shrink: 0;
            padding: 8px 10px;
            border-top: 1px solid #e8e8e8;
            background: #fafafa;
            text-align: right;
        }

        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-body { padding: 0 !important; overflow: hidden !important; }

        /* ============ 表单样式 ============ */
        .add-form-wrap { padding: 16px 20px; }
        .add-form-wrap .mini-textbox,
        .add-form-wrap .mini-spinner {
            width: 220px;
        }
        .add-form-wrap td.label {
            text-align: right;
            padding: 6px 8px;
            width: 110px;
            font-size: 13px;
            color: #333;
            white-space: nowrap;
        }
        .add-form-wrap td.input-cell {
            padding: 6px 8px;
        }
        .required-star { color: red; }
    </style>
</head>
<body>

<div class="main-container">
    <!-- 主体：表单面板 -->
    <div class="main-body">
        <div id="addSceneModelPanel" class="mini-panel"
             style="width:100%;height:100%;"
             showHeader="false" showToolbar="false" showCloseButton="false"
             bodyStyle="padding:0;overflow:auto;">

            <div class="add-form-wrap">
                <input id="addOrgId" class="mini-hidden" />
                <input id="addDevicetype" class="mini-hidden" value="1" />
                
                <table style="width:100%;border-collapse:collapse;">
                    <!-- 三行：中文/英文/俄文，只显示当前语言对应的那一行 -->
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
                        <td class="label" id="lblSort"></td>
                        <td class="input-cell">
                            <input id="add_sort" class="mini-spinner" minValue="1" maxValue="9999999999" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <!-- 底部按钮区 -->
    <div class="footer">
        <button id="addSaveBtn" class="mini-button" plain="true" iconCls="save" onclick="onAddSave()"></button>
        <button id="addCancelBtn" class="mini-button" plain="true" iconCls="cancel" onclick="onAddCancel()"></button>
    </div>
</div>

<script>
    var context = '<%=path%>';
    var loginUserLanguage = '<%=loginUserLanguage%>';

    // 父窗口通过 contentWindow.setData(...) 调用
    function setData(data) {
        data = data || {};
        if (data.orgId) mini.get('addOrgId').setValue(data.orgId);
    }

    // ============================================================
    // 初始化国际化 + 语言字段显隐
    // ============================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        var lang = (loginUserLanguage || 'ZH_CN').toUpperCase();

        document.title = R.add;

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

        var zhLbl = document.getElementById('lblName_zh_CN');
        if (zhLbl) zhLbl.innerHTML = R.language_zh_CN + '<span class="required-star">*</span>：';
        var enLbl = document.getElementById('lblName_en');
        if (enLbl) enLbl.innerHTML = R.language_en + '：';
        var ruLbl = document.getElementById('lblName_ru');
        if (ruLbl) ruLbl.innerHTML = R.language_ru + '：';

        var sortLbl = document.getElementById('lblSort');
        if (sortLbl) sortLbl.innerHTML = R.sequenceNumber + '：';

        var saveBtn = mini.get('addSaveBtn');
        if (saveBtn) saveBtn.setText(R.save);
        var cancelBtn = mini.get('addCancelBtn');
        if (cancelBtn) cancelBtn.setText(R.cancel);
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

        // 校验当前语言必填
        if (lang === 'ZH_CN' && !name_zh_CN) { mini.alert(R.required, R.tip); return; }
        if (lang === 'EN'    && !name_en)    { mini.alert(R.required, R.tip); return; }
        if (lang === 'RU'    && !name_ru)    { mini.alert(R.required, R.tip); return; }

        var params = {
            'sceneModel.orgId':       mini.get('addOrgId').getValue(),
            'sceneModel.devicetype':  mini.get('addDevicetype').getValue(),
            'sceneModel.name_zh_CN':  name_zh_CN,
            'sceneModel.name_en':     name_en,
            'sceneModel.name_ru':     name_ru,
            'sceneModel.sort':        mini.get('add_sort').getValue() || ''
        };

        mini.mask({ el: document.body, html: R.submittingData });

        $.ajax({
            url: context + '/sceneModelController/addSceneModel',
            type: 'POST',
            data: params,
            dataType: 'json',
            success: function (result) {
                mini.unmask(document.body);
                if (result && result.success === true) {
                    mini.alert('<font color="blue">' + R.addedSuccessfully + '</font>', R.tip, function () {
                        
                        // 确定当前语言下的名称用于父窗口匹配选中
                        var addedName = '';
                        if (lang === 'ZH_CN') addedName = name_zh_CN;
                        else if (lang === 'EN') addedName = name_en;
                        else if (lang === 'RU') addedName = name_ru;
                        
                        // 如果后端返回了 id 则优先用 id
                        var newId = result.modelId || result.id || '';

                        try {
                            if (window.parent && typeof window.parent._parentRefreshSceneList === 'function') {
                                window.parent._parentRefreshSceneList(true, newId, addedName);
                            }
                        } catch (e) {
                            console.warn('_parentRefreshSceneList 调用失败', e);
                        }

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

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>