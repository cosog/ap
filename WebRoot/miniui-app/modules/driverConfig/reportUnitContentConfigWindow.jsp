<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>报表内容配置</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar { flex-shrink:0; padding:4px 10px; border-bottom:1px solid #e8e8e8;
                        background:#fafafa; display:flex; align-items:center; gap:6px; }
        .hot-wrapper { flex:1; overflow:hidden; padding:2px; width:100%; height:100%;}
        .hot-inner { width:100%; height:100%; }
    </style>
</head>
<body>
<div class="main-container">
    <div class="mini-toolbar">
        <span id="infoLabel" style="font-size:12px;color:#333;"></span>
        <span style="flex:1;"></span>
        <button id="saveBtn" class="mini-button" iconCls="save" onclick="onSave()">保存</button>
    </div>
    <div class="mini-splitter" vertical="false" style="flex:1;width:100%;">
        <div size="35%" showCollapseButton="false">
            <div class="hot-wrapper">
                <div id="colInfoContainer" class="hot-inner"></div>
            </div>
        </div>
        <div size="65%" showCollapseButton="false">
            <div class="hot-wrapper">
                <div id="itemsContainer" class="hot-inner"></div>
            </div>
        </div>
    </div>
</div>

<script>
    // ================================================================
    // 上下文
    // ================================================================
    var context = '<%=context%>';
    var _params = {
        unitId: 0, unitName: '', calculateType: 0, classes: 0,
        unitClasses: 0, reportType: 0,
        selectedRow: 0, selectedCol: 0, templateCode: ''
    };
    var editFlag = false;

    var colInfoHelper = null;
    var itemsHelper   = null;

    // ================================================================
    // 父窗口调用
    // ================================================================
    function setData(data) {
        if (!data) return;
        _params.unitId        = data.unitId || 0;
        _params.unitName      = data.unitName || '';
        _params.calculateType = data.calculateType || 0;
        _params.classes       = data.classes || 0;
        _params.unitClasses   = data.unitClasses || 0;
        _params.reportType    = data.reportType !== undefined ? data.reportType : 0;
        _params.selectedRow   = data.selectedRow || 0;
        _params.selectedCol   = data.selectedCol || 0;
        _params.templateCode  = data.templateCode || '';
        editFlag              = data.editFlag;

        initI18n();

        requestAnimationFrame(function () {
            loadColInfoTable();
            loadItemsTable();
        });
    }

    function initI18n() {
        document.title = _loginUserLanguageResource.reportContentConfig;
        var btn = mini.get('saveBtn');
        if (btn) btn.setText(_loginUserLanguageResource.save);

        var label = document.getElementById('infoLabel');
        if (label) {
            var text = _params.unitName
                ? '【<font color="red">' + _params.unitName + '</font>】'
                    + _loginUserLanguageResource.reportContentConfig
                : _loginUserLanguageResource.reportContentConfig;
            label.innerHTML = text;
        }
    }

    // ================================================================
    // 加载右侧表格
    // ================================================================
    function loadItemsTable() {
        var container = document.getElementById('itemsContainer');
        if (!container) return;
        if (itemsHelper && itemsHelper.hot) { itemsHelper.hot.destroy(); itemsHelper = null; }

        var mask = mini.mask({ el: container, html: _loginUserLanguageResource.loadingData });

        $.ajax({
            url: context + '/acquisitionUnitManagerController/getReportUnitContentConfigItemsData',
            type: 'POST',
            data: {
                unitId: _params.unitId,
                reportType: _params.reportType,
                calculateType: _params.calculateType,
                unitClasses: _params.unitClasses,
                row: _params.selectedRow,
                col: _params.selectedCol
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(container);
                var data = result.totalRoot || [];
                if (data.length === 0) { for (var i = 0; i < 30; i++) data.push({}); }
                itemsHelper = createItemsHandsontable(container, data);

                // 载入后选中已勾选行
                for (var i = 0; i < data.length; i++) {
                    if (data[i].checked) {
                        itemsHelper.hot.selectRows(i);
                        break;
                    }
                }
            },
            error: function () {
                mini.unmask(container);
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    // ================================================================
    // 加载左侧表格
    // ================================================================
    function loadColInfoTable() {
        var container = document.getElementById('colInfoContainer');
        if (!container) return;
        if (colInfoHelper && colInfoHelper.hot) { colInfoHelper.hot.destroy(); colInfoHelper = null; }
        container.innerHTML = '<div style="text-align:center;padding:20px;">'
            + _loginUserLanguageResource.loadingData + '</div>';

        $.ajax({
            url: context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
            type: 'POST',
            data: {
                calculateType: _params.calculateType,
                reportType: _params.reportType,
                unitId: _params.unitId,
                templateCode: _params.templateCode,
                classes: _params.classes,
                unitClasses: _params.unitClasses,
                row: _params.selectedRow
            },
            dataType: 'json',
            success: function (result) {
                container.innerHTML = '';
                var data = result.totalRoot || [];
                var rawData = result.rawData || [];
                if (data.length === 0) {
                    for (var i = 0; i < 30; i++) data.push({});
                }
                colInfoHelper = createColInfoHandsontable(container, data, rawData);

                if (colInfoHelper && colInfoHelper.hot && _params.selectedRow < data.length) {
                    setTimeout(function () {
                        colInfoHelper.hot.selectRows(parseInt(_params.selectedRow));
                    }, 50);
                }
            },
            error: function () {
                container.innerHTML = '<div style="text-align:center;padding:20px;color:red;">'
                    + _loginUserLanguageResource.requestFailed + '</div>';
            }
        });
    }

    // ================================================================
    // 右侧表格
    // ================================================================
    function createItemsHandsontable(container, data) {
        var helper = {};
        helper.hot = null;

        var reportType  = _params.reportType;
        var unitClasses = _params.unitClasses;

        // ---- 列定义（单井报表：reportType 0 / 2） ----
        var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.dataColumn,
            _loginUserLanguageResource.unit, _loginUserLanguageResource.dataSource,
            _loginUserLanguageResource.totalType, _loginUserLanguageResource.showLevel,
            _loginUserLanguageResource.prec, _loginUserLanguageResource.reportCurve, '', '', '', ''];
        var columns = [
            { data: 'checked', type: 'checkbox' },
            { data: 'id' },
            { data: 'showTitle' },
            { data: 'unit' },
            { data: 'dataSource' },
            { data: 'totalType', type: 'dropdown', strict: true, allowInvalid: false, source: [
                _loginUserLanguageResource.maxValue, _loginUserLanguageResource.minValue,
                _loginUserLanguageResource.avgValue, _loginUserLanguageResource.newestValue,
                _loginUserLanguageResource.oldestValue, _loginUserLanguageResource.dailyTotalValue
            ]},
            { data: 'showLevel', type: 'text', allowInvalid: true },
            { data: 'prec', type: 'text', allowInvalid: true },
            { data: 'reportCurveConfShowValue' },
            { data: 'reportCurveConf' },
            { data: 'code' },
            { data: 'dataType' },
            { data: 'remark' },
            { data: 'bitIndex' },
            { data: 'title' }
        ];
        var hiddenColumns = [9, 10, 11, 12, 13, 14];
        var colWidths = [40, 45, 140, 70, 80, 90, 55, 55, 120, 80];

        // ---- 区域报表（reportType 1） ----
        if (reportType === 1) {
            colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.dataColumn,
                _loginUserLanguageResource.unit, _loginUserLanguageResource.dataSource,
                _loginUserLanguageResource.totalType, _loginUserLanguageResource.showLevel,
                _loginUserLanguageResource.prec, _loginUserLanguageResource.sumSign,
                _loginUserLanguageResource.averageSign, _loginUserLanguageResource.reportCurve,
                _loginUserLanguageResource.curveStatType, '', '', '', ''];
            columns = [
                { data: 'checked', type: 'checkbox' },
                { data: 'id' },
                { data: 'showTitle' },
                { data: 'unit' },
                { data: 'dataSource' },
                { data: 'totalType', type: 'dropdown', strict: true, allowInvalid: false, source: [
                    _loginUserLanguageResource.maxValue, _loginUserLanguageResource.minValue,
                    _loginUserLanguageResource.avgValue, _loginUserLanguageResource.newestValue,
                    _loginUserLanguageResource.oldestValue, _loginUserLanguageResource.dailyTotalValue
                ]},
                { data: 'showLevel', type: 'text', allowInvalid: true },
                { data: 'prec', type: 'text', allowInvalid: true },
                { data: 'sumSign', type: 'checkbox' },
                { data: 'averageSign', type: 'checkbox' },
                { data: 'reportCurveConfShowValue' },
                { data: 'curveStatType', type: 'dropdown', strict: true, allowInvalid: false, source: [
                    _loginUserLanguageResource.curveStatType_sum,
                    _loginUserLanguageResource.curveStatType_avg
                ]},
                { data: 'reportCurveConf' },
                { data: 'code' },
                { data: 'dataType' },
                { data: 'remark' },
                { data: 'bitIndex' },
                { data: 'title' }
            ];
            if (unitClasses === 1) {
                hiddenColumns = [5, 9, 10, 11, 12, 13, 14];
                colWidths = [40, 45, 140, 70, 80, 90, 55, 55, 120, 80];
            } else {
                hiddenColumns = [12, 13, 14, 15, 16, 17];
                colWidths = [40, 45, 140, 70, 80, 90, 55, 55, 40, 40, 120, 85];
            }
        } else if (unitClasses === 1) {
            hiddenColumns = [5, 9, 10, 11, 12, 13, 14];
        }

        var addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (value != null && value !== '') {
                var arr = (value + '').split(';');
                if (arr.length === 3) {
                    td.style.backgroundColor = '#' + arr[2];
                }
            }
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        var addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            if (cellProperties.type === 'checkbox') {
                Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
            } else if (cellProperties.type === 'dropdown') {
                Handsontable.renderers.DropdownRenderer.apply(this, arguments);
            } else {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
            }
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        helper.hot = new Handsontable(container, {
            licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            theme: 'ht-theme-classic',
            data: data,
            hiddenColumns: { columns: hiddenColumns, indicators: false, copyPasteEnabled: true },
            columns: columns,
            colWidths: colWidths,
            stretchH: 'all',
            rowHeaders: false,
            colHeaders: colHeaders,
            columnSorting: false,
            sortIndicator: false,
            manualColumnResize: true,
            manualRowResize: true,
            filters: false,
            search: false,
            outsideClickDeselects: false,
            contextMenu: {
                items: {
                    "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                    "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                }
            },

            // ★ 完全照 ExtJS 的写法：每个分支都显式赋 editor
            cells: function (row, col, prop) {
                var cellProperties = {};
                var visualColIndex = this.instance.toVisualColumn(col);
                var colConfig = columns[visualColIndex];
                var colType = (colConfig && colConfig.type) || 'text';

                if (!editFlag) {
                    // 无编辑权限：全部只读
                    cellProperties.editor = false;
                } else {
                    var checked = this.instance.getDataAtRowProp(row, 'checked');
                    if (checked) {
                        // 勾选行：第 1~4 列只读，其余用列类型
                        if (visualColIndex >= 1 && visualColIndex <= 4) {
                            cellProperties.editor = false;
                        } else {
                            cellProperties.editor = colType;
                        }
                    } else {
                        // 未勾选行：除 checkbox 列（第 0 列）外全部只读
                        if (visualColIndex >= 1) {
                            cellProperties.editor = false;
                        }
                    }
                }

                if (prop === 'reportCurveConfShowValue') {
                    cellProperties.renderer = addCurveBg;
                } else if (colConfig && colConfig.type !== 'dropdown' && colConfig.type !== 'checkbox') {
                    cellProperties.renderer = addCellStyle;
                }
                return cellProperties;
            },

            // 单选 checkbox：先清空其他行，再 toggle 当前行
            afterSelectionEnd: function (row, column, row2, column2) {
                if (row === row2 && column === column2 && column === 0) {
                    var hot = this;
                    var rowdata = hot.getDataAtRow(row);
                    var colData = hot.getDataAtCol(0);
                    for (var i = 0; i < colData.length; i++) {
                        if (i !== row && colData[i]) {
                            hot.setDataAtCell(i, 0, false);
                        }
                    }
                    hot.setDataAtCell(row, 0, !rowdata[0]);
                }
            },

            // 双击曲线列 → 弹出曲线配置窗口
            afterOnCellMouseDown: function (event, coords) {
                if (event.detail < 2) return;
                var prop = this.colToProp(coords.col);
                if (prop !== 'reportCurveConfShowValue') return;
                var checked = this.getDataAtRowProp(coords.row, 'checked');
                if (!(checked === true || checked === 'true')) return;
                openReportCurveConfigWindow(coords.row);
            },

            afterChange: function (changes, source) {
                if (!changes) return;
                if (source === 'loadData' || source === 'external') return;

                var hot = this;

                // ---------- 粘贴 ----------
                if (source === 'CopyPaste.paste') {
                    for (var i = 0; i < changes.length; i++) {
                        var r = changes[i][0];
                        var p = changes[i][1];
                        var nv = changes[i][3];
                        if (p === 'reportCurveConfShowValue') {
                            var arr = (nv + '').split(';');
                            if (arr.length === 3) {
                                var curveConfig = {
                                    sort: arr[0],
                                    lineWidth: 3,
                                    dashStyle: 'Solid',
                                    yAxisOpposite: arr[1] === _loginUserLanguageResource.right,
                                    color: arr[2]
                                };
                                hot.setDataAtRowProp(r, 'reportCurveConf', curveConfig, 'external');
                                if (colInfoHelper && colInfoHelper.hot) {
                                    var ar = parseInt(_params.selectedRow) || 0;
                                    colInfoHelper.hot.setDataAtRowProp(ar, 'reportCurveConfShowValue', nv, 'external');
                                    colInfoHelper.hot.setDataAtRowProp(ar, 'reportCurveConf', curveConfig, 'external');
                                    colInfoHelper.refreshRowChanged(ar);
                                    colInfoHelper.hot.render();
                                }
                            } else {
                                hot.setDataAtRowProp(r, 'reportCurveConf', '', 'external');
                            }
                        }
                    }
                    return;
                }

                // ---------- 普通编辑 ----------
                if (source !== 'edit') return;

                for (var j = 0; j < changes.length; j++) {
                    var row = changes[j][0];
                    var prop = changes[j][1];
                    var oldVal = changes[j][2];
                    var newVal = changes[j][3];
                    if (oldVal === newVal) continue;

                    // checkbox 联动
                    if (prop === 'checked') {
                        var activeRow = parseInt(_params.selectedRow) || 0;
                        if (newVal) {
                            copyRowToColInfo(row, activeRow);
                        } else {
                            clearColInfoRow(activeRow);
                        }
                        continue;
                    }

                    // 其它字段编辑：仅勾选行同步到左侧
                    var checked = hot.getDataAtRowProp(row, 'checked');
                    if (!(checked === true || checked === 'true')) continue;

                    var leftProp = mapItemFieldToColInfoProp(prop);
                    if (leftProp && colInfoHelper && colInfoHelper.hot) {
                        var ar2 = parseInt(_params.selectedRow) || 0;
                        colInfoHelper.hot.setDataAtRowProp(ar2, leftProp, newVal, 'external');
                        colInfoHelper.hot.setDataAtRowProp(ar2, 'dataChange', 1, 'external');
                        colInfoHelper.refreshRowChanged(ar2);
                        colInfoHelper.hot.render();
                    }
                }
            },

            afterOnCellMouseOver: _handsontableMakeMouseOver(helper)
        });
        return helper;
    }

    // ================================================================
    // 左侧表格
    // ================================================================
    function createColInfoHandsontable(container, data, rawData) {
        var helper = {};
        helper.hot = null;
        helper.rawData = rawData || [];
        helper.changedRows = {};

        helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        helper.addDataChangeBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(255, 76, 66)';
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        helper.refreshRowChanged = function (row) {
            if (!helper.hot || helper.hot.isDestroyed) return;
            var rowData = helper.hot.getDataAtRow(row);
            var raw = helper.rawData[parseInt(row)];
            var changed = compareRowData(rowData, raw, _params.reportType);
            if (changed) helper.changedRows[row] = true;
            else delete helper.changedRows[row];
        };

        helper.hot = new Handsontable(container, {
            licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            theme: 'ht-theme-classic',
            data: data,
            hiddenColumns: {
                columns: [3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18],
                indicators: false
            },
            columns: [
                { data: 'id' },
                { data: 'headerName' },
                { data: 'itemName' },
                { data: 'unit' },
                { data: 'dataSource' },
                { data: 'totalType' },
                { data: 'showLevel' },
                { data: 'sort' },
                { data: 'prec' },
                { data: 'sumSign' },
                { data: 'averageSign' },
                { data: 'reportCurveConfShowValue' },
                { data: 'reportCurveConf' },
                { data: 'curveStatType' },
                { data: 'dataType' },
                { data: 'itemCode' },
                { data: 'remark' },
                { data: 'dataChange' },
                { data: 'itemBitIndex' }
            ],
            colWidths: [50, 150, 150],
            stretchH: 'all',
            rowHeaders: false,
            colHeaders: [
                _loginUserLanguageResource.idx,
                _loginUserLanguageResource.fiedName,
                _loginUserLanguageResource.dataColumn
            ],
            outsideClickDeselects: false,

            cells: function (row, col, prop) {
                var cellProperties = {};
                cellProperties.editor = false;
                if (prop === 'itemName') {
                    cellProperties.renderer = helper.changedRows[row]
                        ? helper.addDataChangeBg
                        : helper.addCellStyle;
                } else {
                    cellProperties.renderer = helper.addCellStyle;
                }
                return cellProperties;
            },

            afterSelectionEnd: function (row, column, row2, column2) {
                if (row >= 0) {
                    var startRow = row;
                    if (row2 >= 0 && row > row2) startRow = row2;
                    _params.selectedRow = startRow;
                    loadItemsTable();
                }
            },

            afterOnCellMouseOver: _handsontableMakeMouseOver(helper)
        });
        return helper;
    }

    // ================================================================
    // 字段名映射：右表字段 → 左表字段
    // ================================================================
    function mapItemFieldToColInfoProp(itemProp) {
        var map = {
            'showTitle': 'itemName',
            'unit': 'unit',
            'dataSource': 'dataSource',
            'totalType': 'totalType',
            'showLevel': 'showLevel',
            'prec': 'prec',
            'sumSign': 'sumSign',
            'averageSign': 'averageSign',
            'reportCurveConfShowValue': 'reportCurveConfShowValue',
            'reportCurveConf': 'reportCurveConf',
            'curveStatType': 'curveStatType',
            'code': 'itemCode',
            'dataType': 'dataType',
            'remark': 'remark',
            'bitIndex': 'itemBitIndex'
        };
        return map[itemProp] || null;
    }

    // ================================================================
    // 对比行数据 vs 原始数据
    // ================================================================
    function compareRowData(rowData, raw, reportType) {
        if (!rowData) return false;
        if (!raw) return !!rowData[2];

        if (reportType == 1) {
            return !isEquals(rowData[2],  raw.itemName)
                || !isEquals(rowData[5],  raw.totalType)
                || !isEquals(rowData[6],  raw.showLevel)
                || !isEquals(rowData[8],  raw.prec)
                || (rowData[9]  + '') !== (raw.sumSign + '')
                || (rowData[10] + '') !== (raw.averageSign + '')
                || !isEquals(rowData[11], raw.reportCurveConfShowValue)
                || !isEquals(rowData[12], raw.reportCurveConf)
                || !isEquals(rowData[13], raw.curveStatType)
                || !isEquals(rowData[14], raw.dataType)
                || !isEquals(rowData[15], raw.itemCode)
                || !isEquals(rowData[18], raw.itemBitIndex);
        } else {
            return !isEquals(rowData[2],  raw.itemName)
                || !isEquals(rowData[5],  raw.totalType)
                || !isEquals(rowData[6],  raw.showLevel)
                || !isEquals(rowData[8],  raw.prec)
                || !isEquals(rowData[11], raw.reportCurveConfShowValue)
                || !isEquals(rowData[12], raw.reportCurveConf)
                || !isEquals(rowData[14], raw.dataType)
                || !isEquals(rowData[15], raw.itemCode)
                || !isEquals(rowData[18], raw.itemBitIndex);
        }
    }

    // ================================================================
    // 勾选右侧某行 → 把整行值按字段写入左侧活动行
    // ================================================================
    function copyRowToColInfo(itemIdx, activeRow) {
        if (!itemsHelper || !itemsHelper.hot || !colInfoHelper || !colInfoHelper.hot) return;

        var itemFields = ['showTitle', 'unit', 'dataSource', 'totalType', 'showLevel', 'prec',
                          'sumSign', 'averageSign', 'reportCurveConfShowValue', 'reportCurveConf',
                          'curveStatType', 'code', 'dataType', 'remark', 'bitIndex'];

        for (var i = 0; i < itemFields.length; i++) {
            var itemProp = itemFields[i];
            var colProp = mapItemFieldToColInfoProp(itemProp);
            if (!colProp) continue;
            var val = itemsHelper.hot.getDataAtRowProp(itemIdx, itemProp);
            colInfoHelper.hot.setDataAtRowProp(activeRow, colProp, val, 'external');
        }
        colInfoHelper.hot.setDataAtRowProp(activeRow, 'dataChange', 1, 'external');
        colInfoHelper.refreshRowChanged(activeRow);
        colInfoHelper.hot.render();
    }

    // ================================================================
    // 取消勾选 → 清空左侧活动行（保留 sort）
    // ================================================================
    function clearColInfoRow(activeRow) {
        if (!colInfoHelper || !colInfoHelper.hot) return;

        var clearFields = ['itemName', 'unit', 'dataSource', 'totalType', 'showLevel', 'prec',
                           'sumSign', 'averageSign', 'reportCurveConfShowValue', 'reportCurveConf',
                           'curveStatType', 'dataType', 'itemCode', 'remark', 'itemBitIndex'];

        for (var i = 0; i < clearFields.length; i++) {
            colInfoHelper.hot.setDataAtRowProp(activeRow, clearFields[i], '', 'external');
        }
        colInfoHelper.hot.setDataAtRowProp(activeRow, 'dataChange', 1, 'external');
        colInfoHelper.refreshRowChanged(activeRow);
        colInfoHelper.hot.render();
    }

    // ================================================================
    // 打开报表曲线配置窗口
    // ================================================================
    function openReportCurveConfigWindow(row) {
        if (!itemsHelper || !itemsHelper.hot) return;

        var confVal = itemsHelper.hot.getDataAtRowProp(row, 'reportCurveConf');
        var curveConfig = null;
        if (confVal) {
            try {
                curveConfig = (typeof confVal === 'string') ? JSON.parse(confVal) : confVal;
            } catch (e) {
                curveConfig = null;
            }
        }

        var config = {
            sort:          (curveConfig && curveConfig.sort)          || 1,
            lineWidth:     (curveConfig && curveConfig.lineWidth)     || 3,
            dashStyle:     (curveConfig && curveConfig.dashStyle)     || 'Solid',
            yAxisOpposite: (curveConfig && curveConfig.yAxisOpposite) || false,
            color:         (curveConfig && curveConfig.color)         || 'ff0000'
        };

        mini.open({
            title: _loginUserLanguageResource.curveProperty,
            url: context + '/miniui-app/modules/driverConfig/reportCurveConfigWindow.jsp',
            width: 380,
            height: 320,
            modal: true,
            allowResize: false,
            onload: function () {
                var iframe = this.getIFrameEl();
                var cw = iframe.contentWindow;
                cw.setData({
                    row: row,
                    col: 0,
                    tableType: 0,
                    config: config
                });
                cw._updateCurveConfig = function (rowIdx, colIdx, tableType, cfg) {
                    onReportCurveConfigUpdate(rowIdx, cfg);
                };
            }
        });
    }

    function onReportCurveConfigUpdate(row, config) {
        if (!itemsHelper || !itemsHelper.hot) return;

        var sideText = config.yAxisOpposite
            ? _loginUserLanguageResource.right
            : _loginUserLanguageResource.left;
        var showValue = config.sort + ';' + sideText + ';' + config.color;

        itemsHelper.hot.setDataAtRowProp(row, 'reportCurveConfShowValue', showValue, 'external');
        itemsHelper.hot.setDataAtRowProp(row, 'reportCurveConf', config, 'external');
        itemsHelper.hot.render();

        var activeRow = parseInt(_params.selectedRow) || 0;
        if (colInfoHelper && colInfoHelper.hot) {
            colInfoHelper.hot.setDataAtRowProp(activeRow, 'reportCurveConfShowValue', showValue, 'external');
            colInfoHelper.hot.setDataAtRowProp(activeRow, 'reportCurveConf', config, 'external');
            colInfoHelper.hot.setDataAtRowProp(activeRow, 'dataChange', 1, 'external');
            colInfoHelper.refreshRowChanged(activeRow);
            colInfoHelper.hot.render();
        }
    }

    // ================================================================
    // 保存
    // ================================================================
    function onSave() {
        if (!colInfoHelper || !colInfoHelper.hot) return;
        if (!_params.unitId) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }

        var allData = colInfoHelper.hot.getData();
        var itemList = [];
        for (var i = 0; i < allData.length; i++) {
            if (!colInfoHelper.changedRows[i]) continue;
            var item = buildItemFromRow(allData[i], i);
            if (item) itemList.push(item);
        }

        if (itemList.length === 0) {
            mini.alert(_loginUserLanguageResource.noDataChange);
            return;
        }

        var saveData = { itemList: itemList };
        var sort = parseInt(_params.selectedRow) + 1;

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.updateWait });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/grantReportUnitContentItemsPermission',
            type: 'POST',
            data: {
                unitId: _params.unitId,
                reportType: _params.reportType,
                calculateType: _params.calculateType,
                sort: sort,
                saveData: JSON.stringify(saveData)
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(document.body);
                if (result.msg === true) {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    loadColInfoTable();
                    loadItemsTable();

                    if (window.parent && typeof window.parent.refreshReportUnitContentTable === 'function') {
                        window.parent.refreshReportUnitContentTable(_params.reportType, _params.unitClasses);
                    }
                } else {
                    mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>');
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    // ================================================================
    // 行数据 → 提交用 item
    // ================================================================
    function buildItemFromRow(row, idx) {
        var reportType  = _params.reportType;
        var unitClasses = _params.unitClasses;
        var item = { matrix: '0,0,0' };

        if (reportType === 1) {
            item.itemName      = row[2];
            item.totalType     = 0;
            item.dataSource    = row[4];
            item.itemShowLevel = row[6];
            item.itemSort      = idx + 1;
            item.sumSign       = (row[9]  + '' === 'true') ? '1' : '0';
            item.averageSign   = (row[10] + '' === 'true') ? '1' : '0';

            var curveConfStr = '';
            if (isNotVal(row[11]) && isNotVal(row[12])) {
                curveConfStr = JSON.stringify(row[12]);
            }
            item.reportCurveConf = curveConfStr;

            var st = row[13];
            if      (st === _loginUserLanguageResource.curveStatType_sum) item.curveStatType = '1';
            else if (st === _loginUserLanguageResource.curveStatType_avg) item.curveStatType = '2';
            else if (st === _loginUserLanguageResource.curveStatType_max) item.curveStatType = '3';
            else if (st === _loginUserLanguageResource.curveStatType_min) item.curveStatType = '4';

            item.dataType = row[14] + '';
            item.itemCode = row[15];

            if (item.dataType === '2' || item.dataType === 2) {
                item.itemPrec = row[8];
                var t = row[5];
                if      (t === _loginUserLanguageResource.maxValue)        item.totalType = 1;
                else if (t === _loginUserLanguageResource.minValue)        item.totalType = 2;
                else if (t === _loginUserLanguageResource.avgValue)        item.totalType = 3;
                else if (t === _loginUserLanguageResource.newestValue)     item.totalType = 4;
                else if (t === _loginUserLanguageResource.oldestValue)     item.totalType = 5;
                else if (t === _loginUserLanguageResource.dailyTotalValue) item.totalType = 6;
            }
        } else {
            item.itemName      = row[2];
            item.totalType     = 0;
            item.dataSource    = row[4];
            item.itemShowLevel = row[6];
            item.itemSort      = idx + 1;

            var curveConfStr = '';
            if (isNotVal(row[11]) && isNotVal(row[12])) {
                curveConfStr = JSON.stringify(row[12]);
            }
            item.reportCurveConf = curveConfStr;

            item.itemCode = row[15];
            item.dataType = row[14];

            if (item.dataType === '2' || item.dataType === 2) {
                item.itemPrec = row[8];
                if (unitClasses === 1) {
                    item.totalType = 4;
                } else {
                    var t = row[5];
                    if      (t === _loginUserLanguageResource.maxValue)        item.totalType = 1;
                    else if (t === _loginUserLanguageResource.minValue)        item.totalType = 2;
                    else if (t === _loginUserLanguageResource.avgValue)        item.totalType = 3;
                    else if (t === _loginUserLanguageResource.newestValue)     item.totalType = 4;
                    else if (t === _loginUserLanguageResource.oldestValue)     item.totalType = 5;
                    else if (t === _loginUserLanguageResource.dailyTotalValue) item.totalType = 6;
                }
            }
        }
        return item;
    }
</script>
</body>
</html>