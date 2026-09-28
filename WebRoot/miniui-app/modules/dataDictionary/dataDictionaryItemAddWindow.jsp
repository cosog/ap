<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据项</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden;
            font-family:"Microsoft YaHei", Arial, sans-serif; background:#fff; }
        .item-add-container { width:100%; height:100%; display:flex; flex-direction:column; background:#fff; }
        .item-add-main { flex:1; min-height:0; overflow:hidden; }
        .item-add-footer { flex-shrink:0; height:44px; border-top:1px solid #e0e0e0; background:#fafafa;
            display:flex; align-items:center; justify-content:flex-end; padding:0 12px; gap:6px;
            box-sizing:border-box; }

        .mini-panel { border:0 !important; }
        .mini-panel-border { border:0 !important; }
        .mini-panel-header { border-bottom:1px solid #e8e8e8 !important; }
        .mini-panel-body { padding:0 !important; overflow:hidden !important; }
        .mini-splitter-border { border:0 !important; }
        .mini-splitter-pane { padding:0 !important; border:0 !important; }
        .mini-splitter-handler { background:#e8e8e8 !important; }

        .mini-fieldset { border:1px solid #e0e0e0; border-radius:3px;
            padding:2px 6px 4px 6px; margin:6px 8px; position:relative; background:#fff; }
        .mini-fieldset > legend { padding:0 6px; font-weight:bold; color:#2d6a9f;
            font-size:13px; line-height:14px; }

        .tip-text { font-size:12px; color:#999; padding:2px 8px 6px 8px;
            border-bottom:1px dashed #eee; margin-bottom:4px; }

        .om-form-table { width:100%; border-collapse:collapse; table-layout:fixed; }
        .om-form-table td { padding:3px 4px; vertical-align:middle; font-size:12px;
            color:#333; overflow:hidden; }
        .om-form-table td.label { text-align:right; white-space:nowrap; text-overflow:ellipsis;
            width:110px; }
        .om-form-table td.input-cell { text-align:left; }

        .om-form-table td.input-cell > .mini-textbox,
        .om-form-table td.input-cell > .mini-textarea,
        .om-form-table td.input-cell > .mini-spinner,
        .om-form-table td.input-cell > .mini-combobox { width:100% !important; max-width:100%; }

        .req-star { color:red; }
        .dict-empty-msg { color:#999; font-size:13px; text-align:center; padding:20px; }
    </style>
</head>
<body>

<div class="item-add-container">
    <div class="item-add-main">
        <div class="mini-splitter" style="width:100%;height:100%;" vertical="false" handlerSize="6">

            <!-- ==================== 左侧（center）：表单 ==================== -->
            <div size="45%" showCollapseButton="false" minSize="320">
                <div class="mini-panel" style="width:100%;height:100%;"
                     showHeader="false" showToolbar="false" showCloseButton="false"
                     bodyStyle="padding:0;overflow:auto;">
                    <fieldset class="mini-fieldset">
                        <legend id="omLegendItemInfo"></legend>
                        <div class="tip-text" id="tipText"></div>
                        <table class="om-form-table">
                            <colgroup>
                                <col style="width:110px;"/>
                                <col/>
                            </colgroup>

                            <!-- 名称行：三个名称字段共用同一个 label，只切换输入框 -->
                            <tr id="rowName">
                                <td class="label"><span id="lblName"></span></td>
                                <td class="input-cell">
                                    <input id="itemName_zh_CN" class="mini-textbox" />
                                    <input id="itemName_en"    class="mini-textbox" style="display:none;" />
                                    <input id="itemName_ru"    class="mini-textbox" style="display:none;" />
                                </td>
                            </tr>

                            <!-- 列数据来源（始终显示） -->
                            <tr id="rowColumnDataSource">
                                <td class="label"><span id="lblColumnDataSource"></span></td>
                                <td class="input-cell">
                                    <input id="columnDataSourceComb" class="mini-combobox"
                                           allowInput="false"
                                           valueField="boxkey"
                                           textField="boxval"
                                           dataField="list"
                                           onbeforeload="onColumnDataSourceBeforeLoad"
                                           onvaluechanged="onColumnDataSourceChange" />
                                </td>
                            </tr>

                            <!-- 数据来源（按需显示） -->
                            <tr id="rowDataSource" style="display:none;">
                                <td class="label"><span id="lblDataSource"></span></td>
                                <td class="input-cell">
                                    <input id="dataSourceComb" class="mini-combobox"
                                           allowInput="false"
                                           valueField="boxkey"
                                           textField="boxval"
                                           dataField="list"
                                           onbeforeload="onDataSourceBeforeLoad"
                                           onvaluechanged="onDataSourceChange" />
                                </td>
                            </tr>

                            <!-- 字段编码（按需显示） -->
                            <tr id="rowCode" style="display:none;">
                                <td class="label"><span id="lblCode"></span></td>
                                <td class="input-cell">
                                    <input id="itemCode" class="mini-textbox" />
                                </td>
                            </tr>

                            <tr id="rowConfigItemName">
                                <td class="label"><span id="lblConfigItemName"></span></td>
                                <td class="input-cell">
                                    <input id="itemConfigItemName" class="mini-textbox"
                                           readonly="true" enabled="false" />
                                </td>
                            </tr>

                            <tr id="rowBitIndex">
                                <td class="label"><span id="lblBitIndex"></span></td>
                                <td class="input-cell">
                                    <input id="itemConfigItemBitIndex" class="mini-textbox"
                                           readonly="true" enabled="false" />
                                </td>
                            </tr>

                            <!-- 单位（初始隐藏） -->
                            <tr id="rowDataUnit" style="display:none;">
                                <td class="label"><span id="lblDataUnit"></span></td>
                                <td class="input-cell">
                                    <input id="itemDataUnit" class="mini-textbox" />
                                </td>
                            </tr>

                            <tr id="rowStatusCn">
                                <td class="label"><span id="lblStatusCn"></span></td>
                                <td class="input-cell">
                                    <input id="itemStatusCn" class="mini-radiobuttonlist"
                                           repeatLayout="flow" repeatDirection="horizontal" value="1" />
                                </td>
                            </tr>

                            <tr id="rowStatusEn">
                                <td class="label"><span id="lblStatusEn"></span></td>
                                <td class="input-cell">
                                    <input id="itemStatusEn" class="mini-radiobuttonlist"
                                           repeatLayout="flow" repeatDirection="horizontal" value="1" />
                                </td>
                            </tr>

                            <tr id="rowStatusRu">
                                <td class="label"><span id="lblStatusRu"></span></td>
                                <td class="input-cell">
                                    <input id="itemStatusRu" class="mini-radiobuttonlist"
                                           repeatLayout="flow" repeatDirection="horizontal" value="1" />
                                </td>
                            </tr>

                            <tr id="rowStatus">
                                <td class="label"><span id="lblStatus"></span></td>
                                <td class="input-cell">
                                    <input id="itemStatus" class="mini-radiobuttonlist"
                                           repeatLayout="flow" repeatDirection="horizontal" value="1" />
                                </td>
                            </tr>

                            <tr id="rowSorts">
                                <td class="label"><span id="lblSorts"></span></td>
                                <td class="input-cell">
                                    <input id="itemSorts" class="mini-spinner"
                                           minValue="0" maxValue="9999999999" value="1" />
                                </td>
                            </tr>

                            <tr id="rowDataValue">
                                <td class="label"><span id="lblDataValue"></span></td>
                                <td class="input-cell">
                                    <textarea id="itemDataValue" class="mini-textarea"
                                              style="width:100%;height:70px;"></textarea>
                                </td>
                            </tr>
                        </table>
                    </fieldset>

                    <!-- 隐藏字段 -->
                    <input id="itemColumnDataSource" class="mini-hidden" />
                    <input id="itemDataSource"       class="mini-hidden" />
                    <input id="itemDeviceType"       class="mini-hidden" />
                    <input id="itemDataItemId"       class="mini-hidden" />
                    <input id="itemSysdataId"        class="mini-hidden" />
                </div>
            </div>

            <!-- ==================== 右侧（east 55%）：字典项来源表格 ==================== -->
            <div size="55%" showCollapseButton="true" collapseDirection="right" minSize="300">
                <div class="mini-panel" style="width:100%;height:100%;"
                     showHeader="false" showToolbar="false" showCloseButton="false"
                     bodyStyle="padding:0;overflow:hidden;">
                    <div id="dictItemSourceGrid" class="mini-datagrid"
                         style="width:100%;height:100%;"
                         idField="id"
                         allowResize="false"
                         allowAlternating="true"
                         showPager="false"
                         showPageInfo="false"
                         multiSelect="false"
                         allowCellEdit="false"
                         allowCellSelect="false"
                         showEmptyText="true"
                         dataField="totalRoot"
                         totalField="totalCount"
                         onbeforeload="onDictItemSourceGridBeforeLoad"
                         onload="onDictItemSourceGridLoad"
                         onselectionchanged="onDictItemSourceGridSelectionChanged">
                        <div property="columns"></div>
                        <div property="emptyText" class="dict-empty-msg"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- ==================== 底部按钮区 ====================
         pick 模式（添加字典里的"添加参数"）：显示"确定"
         save 模式（右侧工具栏"添加"）：显示"保存"
         edit 模式（配置字段超链接）：显示"保存"
    ========================================================= -->
    <div class="item-add-footer">
        <button id="okBtn"     class="mini-button" iconCls="save" plain="true"  onclick="onOk()"></button>
        <button id="saveBtn"   class="mini-button" iconCls="save" plain="true" onclick="onSave()" style="display:none;"></button>
        <button id="editBtn"   class="mini-button" iconCls="save" plain="true" onclick="onEdit()" style="display:none;"></button>
        <button id="cancelBtn" class="mini-button" iconCls="cancel" plain="true" onclick="onCancel()"></button>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var _currentLang   = 'ZH_CN';
    var _currentMode   = 'pick';    // pick / save / edit
    var _sysDataId     = '';
    var _deviceType    = '';
    var _gridLoading   = false;
    var _editInitData  = null;      // edit 模式：来源表格加载后用于匹配选中
    var _editFilling   = false;     // 回填时屏蔽 change 事件

    // ================================================================
    // 行显示/隐藏辅助
    // ================================================================
    function toggleRow(rowId, show) {
        var tr = document.getElementById(rowId);
        if (!tr) return;
        tr.style.display = show ? '' : 'none';
    }

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.getElementById('omLegendItemInfo').textContent = R.tip;
        document.getElementById('tipText').textContent = R.requiredInfo;

        document.getElementById('lblName').innerHTML   = R.fiedName        + ' <span class="req-star">*</span>：';
        document.getElementById('lblColumnDataSource').innerHTML = R.columnDataSource + ' <span class="req-star">*</span>：';
        document.getElementById('lblDataSource').innerHTML       = R.dataSource + ' <span class="req-star">*</span>：';
        document.getElementById('lblCode').innerHTML   = R.fieldCode       + ' <span class="req-star">*</span>：';
        document.getElementById('lblConfigItemName').innerHTML = R.configureField + '：';
        document.getElementById('lblBitIndex').innerHTML = R.bit + '：';
        document.getElementById('lblDataUnit').innerHTML = R.unit + '：';
        document.getElementById('lblStatusCn').innerHTML = R.language_zh_CN + '：';
        document.getElementById('lblStatusEn').innerHTML = R.language_en    + '：';
        document.getElementById('lblStatusRu').innerHTML = R.language_ru    + '：';
        document.getElementById('lblStatus').innerHTML = R.enable + ' <span class="req-star">*</span>：';
        document.getElementById('lblSorts').innerHTML  = R.sequenceNumber  + '：';
        document.getElementById('lblDataValue').innerHTML = R.fieldParameter + '：';

        mini.get('okBtn').setText(R.confirm);
        mini.get('saveBtn').setText(R.save);
        mini.get('editBtn').setText(R.save);
        mini.get('cancelBtn').setText(R.cancel);

        var yesNo = [{ id: 1, text: R.yes }, { id: 0, text: R.no }];
        mini.get('itemStatusCn').setData(yesNo);   mini.get('itemStatusCn').setValue(1);
        mini.get('itemStatusEn').setData(yesNo);   mini.get('itemStatusEn').setValue(1);
        mini.get('itemStatusRu').setData(yesNo);   mini.get('itemStatusRu').setValue(1);
        mini.get('itemStatus').setData(yesNo);     mini.get('itemStatus').setValue(1);
    }

    // ================================================================
    // 按当前语言显示对应名称字段
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
                comp.setRequired(true);
            } else {
                if (el) el.style.display = 'none';
                comp.setEnabled(false);
                comp.setRequired(false);
            }
        }
    }

    // ================================================================
    // 下拉框 beforeload 参数
    // ================================================================
    function onColumnDataSourceBeforeLoad(e) {
        e.params = e.params || {};
        e.params.itemCode = 'DICTDATASOURCE';
        e.params.values = '1,2';
    }

    function onDataSourceBeforeLoad(e) {
        e.params = e.params || {};
        var values = '0,5,1';
        if (typeof moduleContentConfig !== 'undefined'
            && moduleContentConfig.dataDictionary
            && moduleContentConfig.dataDictionary.inputData != 0) {
            values = '0,5,1,2';
        }
        e.params.itemCode = 'DATASOURCE';
        e.params.values = values;
    }

    // ================================================================
    // 加载两个下拉框
    // ================================================================
    function loadComboboxes() {
        var columnDS = mini.get('columnDataSourceComb');
        if(!columnDS.getUrl()){
        	columnDS.setUrl(context + '/wellInformationManagerController/loadCodeComboxListWithoutAll');
        }
        columnDS.load(columnDS.getUrl());

        var ds = mini.get('dataSourceComb');
        if(!ds.getUrl()){
        	ds.setUrl(context + '/wellInformationManagerController/loadCodeComboxListWithoutAll');
        }
        ds.load(ds.getUrl());
    }

    // ================================================================
    // 列数据来源切换
    // ================================================================
    function onColumnDataSourceChange(e) {
        if (_editFilling) return;

        var val = e.value;

        clearDictItemSourceGrid();
        mini.get('itemColumnDataSource').setValue(val);

        var codeComp = mini.get('itemCode');
        var dsComp   = mini.get('dataSourceComb');

        if (val == 1) {
            toggleRow('rowCode',       false);
            toggleRow('rowDataSource', true);

            codeComp.setRequired(false);
            dsComp.setRequired(true);
            dsComp.setValue('');
            mini.get('itemDataSource').setValue('');
        } else if (val == 2) {
            toggleRow('rowCode',       false);
            toggleRow('rowDataSource', false);

            codeComp.setRequired(false);
            dsComp.setRequired(false);
            dsComp.setValue('');
            mini.get('itemDataSource').setValue('');
            loadDictItemSourceGrid();
        } else {
            toggleRow('rowCode',       true);
            toggleRow('rowDataSource', false);

            codeComp.setRequired(true);
            dsComp.setRequired(false);
            dsComp.setValue('');
            mini.get('itemDataSource').setValue('');
        }
    }

    // ================================================================
    // 数据来源切换
    // ================================================================
    function onDataSourceChange(e) {
        if (_editFilling) return;
        mini.get('itemDataSource').setValue(e.value);
        clearDictItemSourceGrid();
        loadDictItemSourceGrid();
    }

    // ================================================================
    // 清空右侧表格
    // ================================================================
    function clearDictItemSourceGrid() {
        var grid = mini.get('dictItemSourceGrid');
        if (!grid) return;
        grid.setData([]);
        grid.setColumns([]);
    }

    // ================================================================
    // 加载字典项来源网格
    // ================================================================
    function loadDictItemSourceGrid() {
        var grid = mini.get('dictItemSourceGrid');
        if (!grid) return;
        if (!grid.getUrl()) {
            grid.setUrl(context + '/dataitemsInfoController/getAddInfoOrDriverConfigItemList');
        }
        grid.load();
    }

    // ================================================================
    // 网格 beforeload
    // ================================================================
    function onDictItemSourceGridBeforeLoad(e) {
        var ds = mini.get('columnDataSourceComb').getValue() || '';
        var dataSource = mini.get('dataSourceComb').getValue() || '';

        if (ds == 1 && !dataSource) { e.cancel = true; return; }
        if (!ds) { e.cancel = true; return; }

        e.params = e.params || {};
        e.params.dictDataSource = ds;
        e.params.dataSource = dataSource;
    }

    // ================================================================
    // 网格 load：动态创建列 + edit 模式匹配选中
    // ================================================================
    function onDictItemSourceGridLoad(e) {
        var grid = e.sender;
        var result = e.result || {};
        var serverColumns = result.columns || [];

        if (serverColumns.length > 0) {
            grid.setColumns(buildColumnsFromServer(serverColumns));
        }

        // ★ edit 模式：加载完成后匹配并选中对应行
        //   （对照 ExtJS DictItemSourceStore.load 里 dictItemAddOrUpdate==1 分支）
        if (_editInitData) {
            var cds = _editInitData.columnDataSource;
            var rows = grid.getData() || [];
            var selectRow = -1;
            for (var i = 0; i < rows.length; i++) {
                var r = rows[i];
                if (cds == 2 && _editInitData.configItemName == r.itemName) {
                    selectRow = i; break;
                } else if (cds == 1
                           && _editInitData.code == r.itemColumn
                           && _editInitData.configItemBitIndex == r.bitIndex) {
                    selectRow = i; break;
                }
            }
            _gridLoading = true;
            grid.deselectAll(true);
            if (selectRow >= 0) {
                grid.select(rows[selectRow]);
            }
            _gridLoading = false;
            _editInitData = null;
        }
    }

    // ================================================================
    // 从服务端列定义构建 MiniUI 列
    // ================================================================
    function buildColumnsFromServer(serverColumns) {
        var R = _loginUserLanguageResource;
        var cols = [];
        cols.push({ type: 'checkcolumn', width: 40, header: '', headerAlign: 'center', align: 'center' });
        cols.push({
            type: 'indexcolumn',
            width: 70,
            header: R.idx,
            headerAlign: 'center', align: 'center'
        });
        cols.push({
            field: 'itemName',
            header: R.name,
            headerAlign: 'center', align: 'center',
            width: '75%',
            renderer: function (ev) {
                var v = ev.value;
                if (v === undefined || v === null || v === '') return '';
                var s = String(v).replace(/"/g, '&quot;');
                return '<span title="' + s + '">' + s + '</span>';
            }
        });
        cols.push({
            field: 'itemUnit',
            header: R.unit,
            headerAlign: 'center', align: 'center',
            width: '25%',
            renderer: function (ev) {
                var v = ev.value;
                if (v === undefined || v === null || v === '') return '';
                var s = String(v).replace(/"/g, '&quot;');
                return '<span title="' + s + '">' + s + '</span>';
            }
        });
        return cols;
    }

    // ================================================================
    // 网格选中
    // ================================================================
    function onDictItemSourceGridSelectionChanged(e) {
        if (_gridLoading) return;
        var grid = e.sender;
        var row = grid.getSelected();
        if (!row) return;

        var suffix = (_currentLang === 'EN') ? 'en'
                   : (_currentLang === 'RU') ? 'ru' : 'zh_CN';
        var nameId = 'itemName_' + suffix;

        var nameVal = (mini.get(nameId).getValue() || '').trim();
        if (!nameVal) {
            var setName = row.itemName || '';
            var itemUnit = row.itemUnit || '';
            if (itemUnit) setName += '(' + itemUnit + ')';
            mini.get(nameId).setValue(setName);
        }

        mini.get('itemConfigItemName').setValue(row.itemName || '');
    }

    // ================================================================
    // 底部按钮显隐（按模式）
    // ================================================================
    function updateButtonVisibility() {
        var okBtn   = mini.get('okBtn');
        var saveBtn = mini.get('saveBtn');
        var editBtn = mini.get('editBtn');
        if (okBtn)   okBtn.setVisible(false);
        if (saveBtn) saveBtn.setVisible(false);
        if (editBtn) editBtn.setVisible(false);

        if (_currentMode === 'save') {
            if (saveBtn) saveBtn.setVisible(true);
        } else if (_currentMode === 'edit') {
            if (editBtn) editBtn.setVisible(true);
        } else {
            if (okBtn) okBtn.setVisible(true);
        }
    }

    // ================================================================
    // 父窗口调用
    // ================================================================
    function setData(data) {
        _currentLang = (data && data.lang) ? data.lang.toUpperCase() : 'ZH_CN';
        _currentMode = (data && data.mode) ? data.mode : 'pick';
        _sysDataId   = (data && data.sysDataId) ? data.sysDataId : '';
        _deviceType  = (data && data.deviceType) ? data.deviceType : '';

        if (_deviceType) {
            mini.get('itemDeviceType').setValue(_deviceType);
        }
        if (_sysDataId) {
            mini.get('itemSysdataId').setValue(_sysDataId);
        }

        setupNameFields();
        updateButtonVisibility();

        // ★ edit 模式：回填整行数据
        if (_currentMode === 'edit' && data && data.record) {
            fillEditData(data.record);
        }
    }

    // ================================================================
    // edit 模式回填（对照 ExtJS callBackDictItemConfig）
    // ================================================================
    function fillEditData(record) {
        _editFilling = true;
        try {
            // ---- 名称 ----
            mini.get('itemName_zh_CN').setValue(record.name_zh_CN || '');
            mini.get('itemName_en').setValue(record.name_en || '');
            mini.get('itemName_ru').setValue(record.name_ru || '');

            // ---- 主键 / 关联 ----
            mini.get('itemDataItemId').setValue(record.dataitemid || '');
            mini.get('itemDeviceType').setValue(record.deviceType || '');
            mini.get('itemDataUnit').setValue(record.dataUnit || '');

            // ---- 字段编码 / 配置字段 / 位 ----
            mini.get('itemCode').setValue(record.code || '');
            mini.get('itemConfigItemName').setValue(record.configItemName || '');
            mini.get('itemConfigItemBitIndex').setValue(record.configItemBitIndex || '');

            // ---- 状态 ----
            mini.get('itemStatusCn').setValue(record.status_cn ? 1 : 0);
            mini.get('itemStatusEn').setValue(record.status_en ? 1 : 0);
            mini.get('itemStatusRu').setValue(record.status_ru ? 1 : 0);
            mini.get('itemStatus').setValue(record.status ? 1 : 0);

            // ---- 顺序 / 参数 ----
            mini.get('itemSorts').setValue(record.sorts || 1);
            mini.get('itemDataValue').setValue(record.datavalue || '');

            // ---- 列数据来源 / 数据来源（联动显隐）----
            var cds = record.columnDataSource;
            mini.get('itemColumnDataSource').setValue(cds);
            mini.get('columnDataSourceComb').setValue(cds);
            mini.get('itemDataSource').setValue(record.dataSource || '');

            var codeComp = mini.get('itemCode');
            var dsComp   = mini.get('dataSourceComb');

            if (cds == 1) {
                toggleRow('rowCode',       false);
                toggleRow('rowDataSource', true);
                codeComp.setRequired(false);
                dsComp.setRequired(true);
                dsComp.setValue(record.dataSource || '');
                // ★ 供 load 完成后匹配选中用
                _editInitData = record;
                loadDictItemSourceGrid();
            } else if (cds == 2) {
                toggleRow('rowCode',       false);
                toggleRow('rowDataSource', false);
                codeComp.setRequired(false);
                dsComp.setRequired(false);
                dsComp.setValue('');
                _editInitData = record;
                loadDictItemSourceGrid();
            } else {
                toggleRow('rowCode',       true);
                toggleRow('rowDataSource', false);
                codeComp.setRequired(true);
                dsComp.setRequired(false);
                dsComp.setValue('');
            }

            // ---- 单位行：有值显示 ----
            if (record.dataUnit) {
                toggleRow('rowDataUnit', true);
            }
        } finally {
            _editFilling = false;
        }
    }

    // ================================================================
    // 公共校验
    // ================================================================
    function validateForm() {
        var R = _loginUserLanguageResource;
        var suffix = (_currentLang === 'EN') ? 'en'
                   : (_currentLang === 'RU') ? 'ru' : 'zh_CN';

        var nameComp = mini.get('itemName_' + suffix);
        var nameVal = (nameComp.getValue() || '').trim();
        if (!nameVal) {
            mini.alert(R.required, R.tip);
            nameComp.focus();
            return false;
        }

        var rowCode = document.getElementById('rowCode');
        if (rowCode && rowCode.style.display !== 'none') {
            var codeComp = mini.get('itemCode');
            if (!(codeComp.getValue() || '').trim()) {
                mini.alert(R.required, R.tip);
                codeComp.focus();
                return false;
            }
        }
        return true;
    }

    // ================================================================
    // 采集 pick 模式需要的 7 个字段（对照 ExtJS oktosysfordata）
    // ================================================================
    function collectPickItem() {
        return {
            name_zh_CN: (mini.get('itemName_zh_CN').getValue() || '').trim(),
            name_en:    (mini.get('itemName_en').getValue()    || '').trim(),
            name_ru:    (mini.get('itemName_ru').getValue()    || '').trim(),
            code:       (mini.get('itemCode').getValue()       || '').trim(),
            datavalue:  (mini.get('itemDataValue').getValue()  || ''),
            sorts:      mini.get('itemSorts').getValue(),
            status:     mini.get('itemStatus').getValue() == 1
        };
    }

    // ================================================================
    // 采集提交服务器的参数
    // ================================================================
    function collectSubmitParams() {
        return {
            'dataitemsInfo.name_zh_CN':         mini.get('itemName_zh_CN').getValue() || '',
            'dataitemsInfo.name_en':            mini.get('itemName_en').getValue()    || '',
            'dataitemsInfo.name_ru':            mini.get('itemName_ru').getValue()    || '',
            'dataitemsInfo.code':               mini.get('itemCode').getValue()       || '',
            'dataitemsInfo.columnDataSource':   mini.get('itemColumnDataSource').getValue() || '',
            'dataitemsInfo.dataSource':         mini.get('itemDataSource').getValue()       || '',
            'dataitemsInfo.deviceType':         mini.get('itemDeviceType').getValue()       || '',
            'dataitemsInfo.dataUnit':           mini.get('itemDataUnit').getValue()         || '',
            'dataitemsInfo.status_cn':          mini.get('itemStatusCn').getValue(),
            'dataitemsInfo.status_en':          mini.get('itemStatusEn').getValue(),
            'dataitemsInfo.status_ru':          mini.get('itemStatusRu').getValue(),
            'dataitemsInfo.status':             mini.get('itemStatus').getValue(),
            'dataitemsInfo.sorts':              mini.get('itemSorts').getValue(),
            'dataitemsInfo.datavalue':          mini.get('itemDataValue').getValue() || '',
            'dataitemsInfo.dataitemid':         mini.get('itemDataItemId').getValue() || '',
            'dataitemsInfo.sysdataid':          mini.get('itemSysdataId').getValue() || ''
        };
    }

    // ================================================================
    // pick 模式 - 确定：把数据交给父窗口本地表格
    // ================================================================
    function onOk() {
        if (!validateForm()) return;
        var item = collectPickItem();
        if (window._parentAddItem) window._parentAddItem(item);
        window.CloseOwnerWindow('ok');
    }

    // ================================================================
    // save 模式 - 保存：直接提交服务器（对照 ExtJS savetoSysDataItems）
    // ================================================================
    function onSave() {
        var R = _loginUserLanguageResource;
        if (!_sysDataId) { mini.alert(R.checkOne, R.tip); return; }
        if (!validateForm()) return;

        var srcGrid = mini.get('dictItemSourceGrid');
        var selected = srcGrid.getSelected();
        if (!selected) { mini.alert(R.checkOne, R.tip); return; }

        var params = collectSubmitParams();
        params.sysId          = _sysDataId;
        params.configItemName = selected.itemName   || '';
        params.itemColumn     = selected.itemColumn || '';
        params.itemBitIndex   = selected.bitIndex   || '';

        var mask = mini.mask({ el: document.body, html: R.submittingData });
        $.ajax({
            url: context + '/dataitemsInfoController/addDataitemsInfo',
            type: 'POST',
            data: params,
            dataType: 'json',
            success: function (resp) {
                mini.unmask(document.body);
                if (resp && resp.msg === true) {
                    if (window._parentRefreshDictItem) window._parentRefreshDictItem();
                    mini.alert(R.addedSuccessfully, R.tip, function () {
                        window.CloseOwnerWindow('ok');
                    });
                } else if (resp && resp.msg === false) {
                    mini.alert('<font color="red">SORRY！</font>' + R.addFailure, R.tip);
                } else {
                    mini.alert('<font color="red">' + R.addFailure + '</font>', R.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert(R.exceptionThrow + ': ' + R.addFailure, R.tip);
            }
        });
    }

    // ================================================================
    // edit 模式 - 保存：更新（对照 ExtJS updateSysDataItems）
    // ================================================================
    function onEdit() {
        var R = _loginUserLanguageResource;
        if (!validateForm()) return;

        var cds = mini.get('itemColumnDataSource').getValue();
        var params = collectSubmitParams();

        params.sysId              = _sysDataId || mini.get('itemSysdataId').getValue() || '';
        params.dictItemDataItemId = mini.get('itemDataItemId').getValue() || '';

        // 非基础字段：必须从右侧来源表格选中行取 3 个参数
        if (cds != 0 && cds != '') {
            var srcGrid = mini.get('dictItemSourceGrid');
            var selected = srcGrid.getSelected();
            if (!selected) { mini.alert(R.checkOne, R.tip); return; }
            params.configItemName = selected.itemName   || '';
            params.itemColumn     = selected.itemColumn || '';
            params.itemBitIndex   = selected.bitIndex   || '';
        } else {
            // 基础字段：直接用表单里的值
            params.configItemName = mini.get('itemConfigItemName').getValue() || '';
            params.itemColumn     = mini.get('itemCode').getValue() || '';
            params.itemBitIndex   = '';
        }

        var mask = mini.mask({ el: document.body, html: R.submittingData });
        $.ajax({
            url: context + '/dataitemsInfoController/updateDataitemsInfo',
            type: 'POST',
            data: params,
            dataType: 'json',
            success: function (resp) {
                mini.unmask(document.body);
                if (resp && resp.msg === true) {
                    if (window._parentRefreshDictItem) window._parentRefreshDictItem();
                    mini.alert(R.savedSuccessfully, R.tip, function () {
                        window.CloseOwnerWindow('ok');
                    });
                } else if (resp && resp.msg === false) {
                    mini.alert('<font color="red">SORRY！</font>' + R.saveFailed, R.tip);
                } else {
                    mini.alert('<font color="red">' + R.saveFailed + '</font>', R.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert(R.exceptionThrow + ': ' + R.saveFailed, R.tip);
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
        loadComboboxes();

        var grid = mini.get('dictItemSourceGrid');
        grid.setColumns([]);
        grid.setData([]);

        var initVal = mini.get('columnDataSourceComb').getValue();
        if (initVal !== undefined && initVal !== null && initVal !== '') {
            onColumnDataSourceChange({ value: initVal });
        }

        updateButtonVisibility();
    });
</script>
</body>
</html>