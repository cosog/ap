<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加字典</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #fff;
        }
        .dict-add-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
        }
        .dict-add-main { flex: 1; min-height: 0; overflow: hidden; }
        .dict-add-footer {
            flex-shrink: 0; height: 44px;
            border-top: 1px solid #e0e0e0;
            background: #fafafa;
            display: flex; align-items: center;
            justify-content: flex-end;
            padding: 0 12px; gap: 6px;
            box-sizing: border-box;
        }

        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; }
        .mini-panel-body { padding: 0 !important; overflow: hidden !important; }
        .mini-splitter-border { border: 0 !important; }
        .mini-splitter-pane { padding: 0 !important; border: 0 !important; }
        .mini-splitter-handler { background: #e8e8e8 !important; }

        /* ============ FieldSet 样式（与运维配置一致） ============ */
        .mini-fieldset {
            border: 1px solid #e0e0e0;
            border-radius: 3px;
            padding: 2px 6px 4px 6px;
            margin: 8px 10px;
            position: relative;
            background: #fff;
        }
        .mini-fieldset > legend {
            padding: 0 6px;
            font-weight: bold;
            color: #2d6a9f;
            font-size: 13px;
            line-height: 14px;
        }

        /* ============ 表单表格布局 ============ */
        .om-form-table {
            width: 100%;
            border-collapse: collapse;
            table-layout: fixed;
        }
        .om-form-table td {
            padding: 3px 6px;
            vertical-align: middle;
            font-size: 12px;
            color: #333;
            overflow: hidden;
        }
        .om-form-table td.label {
            text-align: right;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
        .om-form-table td.input-cell { text-align: left; }

        .om-form-table td.input-cell > .mini-textbox,
        .om-form-table td.input-cell > .mini-combobox,
        .om-form-table td.input-cell > .mini-spinner,
        .om-form-table td.input-cell > .mini-treeselect {
            width: 100% !important;
            max-width: 100%;
        }
        .req-star { color: red; }
        .dict-empty-msg { color: #999; font-size: 13px; text-align: center; padding: 20px; }
    </style>
</head>
<body>

<div class="dict-add-container">
    <div class="dict-add-main">
        <div class="mini-splitter" style="width:100%;height:100%;" vertical="true" handlerSize="6">

            <!-- ==================== 顶部：字典表单 ==================== -->
            <div size="175" showCollapseButton="false">
                <div class="mini-panel" style="width:100%;height:100%;"
                     showHeader="false" showToolbar="false" showCloseButton="false"
                     bodyStyle="padding:0;overflow:auto;">
                    <fieldset class="mini-fieldset">
                        <legend id="omLegendTips"></legend>
                        <table class="om-form-table">
                            <colgroup>
                                <col style="width:13%;"/>
                                <col style="width:37%;"/>
                                <col style="width:13%;"/>
                                <col style="width:37%;"/>
                            </colgroup>
                            <tr>
                                <td class="label"><span id="lblName"></span></td>
                                <td class="input-cell">
                                    <input id="sysDataName_zh_CN" class="mini-textbox" />
                                    <input id="sysDataName_en"    class="mini-textbox" style="display:none;" />
                                    <input id="sysDataName_ru"    class="mini-textbox" style="display:none;" />
                                </td>
                            </tr>
                            <tr>
                            	<td class="label"><span id="lblCode"></span></td>
                                <td class="input-cell">
                                    <input id="sysDataCode" class="mini-textbox" required="true"/>
                                </td>
                            </tr>
                            <tr>
                            	<td class="label"><span id="lblModule"></span></td>
                                <td class="input-cell">
                                    <input id="sysDataModule" class="mini-treeselect"
                                    	   showTreeIcon="true"
                                           valueFromSelect="true"
                                           textField="text" 
                                           valueField="id"
                                           parentField="pid" 
                                           resultAsTree="true" 
                                           valueFromSelect="true" 
                                           allowInput="false"
                                           required="true" />
                                </td>
                            </tr>
                            <tr>
                                <td class="label"><span id="lblSorts"></span></td>
                                <td class="input-cell">
                                    <input id="sysDataSorts" class="mini-spinner"
                                           minValue="0" maxValue="9999999999" value="1" required="true"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </div>

            <!-- ==================== 底部：字典项列表 ==================== -->
            <div showCollapseButton="false">
                <div class="mini-panel" style="width:100%;height:100%;"
                     showHeader="false" showToolbar="false" showCloseButton="false"
                     bodyStyle="padding:0;overflow:hidden;">
                    <div id="dictItemAddGrid" class="mini-datagrid"
                         style="width:100%;height:100%;"
                         idField="_tmpId"
                         allowResize="false"
                         allowAlternating="true"
                         showPager="false"
                         showPageInfo="false"
                         multiSelect="false"
                         allowCellEdit="false"
                         allowCellSelect="false"
                         showEmptyText="true">
                        <div property="columns"></div>
                        <div property="emptyText" class="dict-empty-msg"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- ==================== 底部按钮区 ==================== -->
    <div class="dict-add-footer">
        <button id="addParamBtn"  class="mini-button" iconCls="add"    onclick="onAddParam()"></button>
        <button id="saveDictBtn"  class="mini-button" iconCls="save"   onclick="onSaveDict()"></button>
        <button id="cancelDictBtn" class="mini-button" iconCls="cancel" onclick="onCancel()"></button>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var _addItemRowSeq = 0;
    var _currentLang = (_loginUserLanguage || 'zh_CN').toUpperCase();

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;

        document.getElementById('omLegendTips').textContent = R.tip;
        document.getElementById('lblName').innerHTML   = R.fiedName        + ' <span class="req-star">*</span>：';
        document.getElementById('lblCode').innerHTML   = R.dataModuleCode  + ' <span class="req-star">*</span>：';
        document.getElementById('lblModule').innerHTML = R.dictionaryBelongTo + ' <span class="req-star">*</span>：';
        document.getElementById('lblSorts').innerHTML  = R.displayOrder    + ' <span class="req-star">*</span>：';

        mini.get('addParamBtn').setText(R.addParameter);
        mini.get('saveDictBtn').setText(R.save);
        mini.get('cancelDictBtn').setText(R.cancel);
    }

    // ================================================================
    // 按当前语言显示对应的名称输入框
    // ================================================================
    function setupNameFields() {
        var suffix = (_currentLang === 'EN') ? 'en'
                   : (_currentLang === 'RU') ? 'ru' : 'zh_CN';
        var all = ['zh_CN', 'en', 'ru'];
        for (var i = 0; i < all.length; i++) {
            var comp = mini.get('sysDataName_' + all[i]);
            if (!comp) continue;
            var el = comp.getEl();
            if (all[i] === suffix) {
                if (el) el.style.display = '';
                comp.setEnabled(true);
                comp.setRequired(true);
            } else {
                if (el) el.style.display = 'none';
                comp.setEnabled(false);
                comp.setRequired(false);
            }
        }
    }

    // ================================================================
    // 构建字典项表格列
    // ================================================================
    function createItemColumns() {
        var R = _loginUserLanguageResource;
        var columns = [];

        columns.push({
            type: 'indexcolumn', width: 50, header: R.idx,
            headerAlign: 'center', align: 'center'
        });

        // 只显示当前语言对应的名称列（与 ExtJS 一致）
        var nameField = 'name_zh_CN';
        if (_currentLang === 'EN')      nameField = 'name_en';
        else if (_currentLang === 'RU') nameField = 'name_ru';

        columns.push({
            field: nameField, header: R.fiedName,
            headerAlign: 'center', align: 'center', width: 150,
            renderer: renderItemCell
        });
        columns.push({
            field: 'code', header: R.fieldCode,
            headerAlign: 'center', align: 'center', width: 130,
            renderer: renderItemCell
        });
        columns.push({
            field: 'datavalue', header: R.fieldParameter,
            headerAlign: 'center', align: 'center', width: 150,
            renderer: renderItemCell
        });
        columns.push({
            field: 'sorts', header: R.sequenceNumber,
            headerAlign: 'center', align: 'center', width: 60,
            renderer: renderItemCell
        });
        columns.push({
            field: 'status', header: R.enable,
            type: 'checkboxcolumn',
            trueValue: true, falseValue: false,
            headerAlign: 'center', align: 'center', width: 60
        });
        columns.push({
            header: R.operation,
            headerAlign: 'center', align: 'center', width: 80,
            renderer: function (e) {
                return '<a href="javascript:void(0)" '
                     + 'style="color:red;text-decoration:none;" '
                     + 'onclick="onDeleteItem(' + e.recordIndex + ')">'
                     + R.deleteData + '</a>';
            }
        });
        return columns;
    }

    function renderItemCell(e) {
        var v = e.value;
        if (v === undefined || v === null || v === '') return '';
        var s = String(v).replace(/"/g, '&quot;');
        return '<span title="' + s + '">' + s + '</span>';
    }

    // ================================================================
    // 添加参数：打开数据项窗口，追加到本地表格
    // ================================================================
    function onAddParam() {
        var R = _loginUserLanguageResource;
        mini.open({
            title: R.addDataItem,
            url: context + '/miniui-app/modules/dataDictionary/dataDictionaryItemAddWindow.jsp',
            width: 620,
            height: 480,
            modal: true,
            allowResize: true,
            onload: function () {
                var iframe = this.getIFrameEl();
                var cw = iframe.contentWindow;
                cw.setData({ lang: _currentLang });
                // ★ 关键：子窗口回调——把新增项追加到本窗口表格
                cw._parentAddItem = function (item) {
                    var grid = mini.get('dictItemAddGrid');
                    _addItemRowSeq++;
                    item._tmpId = 'tmp_' + _addItemRowSeq + '_' + Date.now();
                    var data = grid.getData() || [];
                    data.push(item);
                    grid.setData(data);
                };
            }
        });
    }

    // ================================================================
    // 删除字典项
    // ================================================================
    function onDeleteItem(rowIndex) {
        var R = _loginUserLanguageResource;
        mini.confirm(R.confirmDelete, R.tip, function (action) {
            if (action !== 'ok') return;
            var grid = mini.get('dictItemAddGrid');
            var data = grid.getData() || [];
            data.splice(rowIndex, 1);
            grid.setData(data);
        });
    }

    // ================================================================
    // 保存字典（主表单 + paramsdtblstringId）
    // ================================================================
    function onSaveDict() {
        var R = _loginUserLanguageResource;
        var suffix = (_currentLang === 'EN') ? 'en'
                   : (_currentLang === 'RU') ? 'ru' : 'zh_CN';

        // ---- 校验 ----
        var nameVal = (mini.get('sysDataName_' + suffix).getValue() || '').trim();
        if (!nameVal) { 
        	mini.alert(R.required, R.tip); 
        	mini.get('sysDataName_' + suffix).focus();
        	mini.get('sysDataName_' + suffix).selectText();
        	return; 
        }

        var codeVal = (mini.get('sysDataCode').getValue() || '').trim();
        if (!codeVal) { mini.alert(R.required, R.tip); return; }
        if (!/^[a-zA-Z]+$/.test(codeVal)) {
            mini.alert(R.dataFormattingError, R.tip);
            return;
        }

        var moduleVal = mini.get('sysDataModule').getValue();
        if (!moduleVal) { mini.alert(R.required, R.tip); return; }

        var sortsVal = mini.get('sysDataSorts').getValue();
        if (sortsVal === '' || sortsVal === null || sortsVal === undefined) {
            mini.alert(R.required, R.tip); return;
        }

        // ---- 组装字典项字符串（与 ExtJS 完全一致） ----
        var grid = mini.get('dictItemAddGrid');
        var items = grid.getData() || [];
        var addparamstr = '';
        for (var i = 0; i < items.length; i++) {
            var it = items[i];
            var st = it.status ? 1 : 0;
            addparamstr += (it.name_zh_CN || '') + '&'
                         + (it.name_en    || '') + '&'
                         + (it.name_ru    || '') + '&'
                         + (it.code       || '') + '&'
                         + (it.datavalue  || '') + '&'
                         + (it.sorts      || '') + '&'
                         + st + '|';
        }
        if (addparamstr.length > 0) {
            addparamstr = addparamstr.substring(0, addparamstr.length - 1);
        }

        // ---- 提交 ----
        var mask = mini.mask({ el: document.body, html: R.submittingData });
        $.ajax({
            url: context + '/systemdataInfoController/addSystemdataInfo',
            type: 'POST',
            data: {
                'systemdataInfo.name_zh_CN': mini.get('sysDataName_zh_CN').getValue() || '',
                'systemdataInfo.name_en':    mini.get('sysDataName_en').getValue()    || '',
                'systemdataInfo.name_ru':    mini.get('sysDataName_ru').getValue()    || '',
                'systemdataInfo.code':       codeVal,
                'systemdataInfo.moduleId':   moduleVal,
                'systemdataInfo.sorts':      sortsVal,
                'systemdataInfo.status':     '',
                paramsdtblstringId:          addparamstr
            },
            dataType: 'json',
            success: function (resp) {
                mini.unmask(document.body);
                if (resp && resp.msg === true) {
                    if (window._parentRefreshDictList) {
                        window._parentRefreshDictList();
                    }
                    mini.alert(R.addedSuccessfully, R.tip, function () {
                        window.CloseOwnerWindow('ok');
                    });
                } else {
                    var errMsg = (resp && resp.error) ? resp.error : R.addFailure;
                    mini.alert('<font color="red">' + errMsg + '</font>', R.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert(R.exceptionThrow + ': ' + R.contactAdmin, R.tip);
            }
        });
    }

    function onCancel() {
        window.CloseOwnerWindow('cancel');
    }

    // ================================================================
    // 初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();
        setupNameFields();

        var grid = mini.get('dictItemAddGrid');
        grid.setColumns(createItemColumns());
        grid.setData([]);

        // 模块树选择器
        var treeSelect = mini.get('sysDataModule');
        treeSelect.setUrl(context + '/moduleMenuController/obtainAddModuleList');
        treeSelect.setResultAsTree(true);
        treeSelect.setDataField('children');
        treeSelect.setEmptyText('--'+_loginUserLanguageResource.checkModule+'--');

        // 只能选叶子节点（与 ExtJS 一致）
        treeSelect.on('nodeclick', function (e) {
            if (e.node && e.node.children && e.node.children.length > 0) {
                mini.alert('<font color="red">'
                    + _loginUserLanguageResource.selectLeafNode + '</font>',
                    _loginUserLanguageResource.tip);
                e.cancel = true;
            }
        });
    });
</script>
</body>
</html>