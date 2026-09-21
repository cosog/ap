<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp?timestamp=<%=otherStaticResourceTimestamp%>" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; }
        /* 关键：让 mini-panel 的 body 撑满，使 Handsontable 容器能拿到 100% 高度 */
        .mini-panel-body { padding:0 !important; overflow:hidden; }
    </style>
</head>
<body>

<!-- 全局变量接收参数 -->
<script>
    var orgId = '';
    var orgName = '';
    var deviceType = '';
    var dictDeviceType = '';
</script>

<!-- 单 panel + 工具条 -->
<div id="batchAddAuxiliaryDevicePanel" class="mini-panel" style="width:100%;height:100%;"
     showHeader="false" showToolbar="true" showCloseButton="false" showCollapseButton="false"
     bodyStyle="padding:0;overflow:hidden;">

    <div property="toolbar" style="padding:4px 8px;background:#fafafa;border-bottom:1px solid #e8e8e8;">
        <table style="width:100%;border-collapse:collapse;">
            <tr>
                <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                    <span id="batchAddAuxiliaryDeviceWinOgLabel_Id"></span>
                </td>
                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                    <button id="btnBatchSave" class="mini-button" iconCls="save" onclick="onBatchSave()"></button>
                </td>
            </tr>
        </table>
    </div>

    <div id="BatchAddAuxiliaryDeviceTableDiv_Id" style="width:100%;height:100%;"></div>
</div>

<script>
    var context = '<%=context%>';
    var batchAddAuxiliaryDeviceHandsontableHelper = null;

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.batchAdd;
        mini.get('btnBatchSave').setText(R.save);
    }

    // ================================================================
    // 父窗口调用
    // ================================================================
    function setData(data) {
        if (!data) data = {};
        orgId = data.orgId || '';
        orgName = data.orgName || '';
        deviceType = data.deviceType || '';
        dictDeviceType = data.dictDeviceType || deviceType;

        document.getElementById('batchAddAuxiliaryDeviceWinOgLabel_Id').innerHTML =
            _loginUserLanguageResource.owningOrg + "【<font color=red>" + orgName + "</font>】," + _loginUserLanguageResource.pleaseConfirm;

        mini.layout();
        setTimeout(function () {
            CreateAndLoadBatchAddAuxiliaryDeviceTable();
        }, 100);
    }

    // ================================================================
    // 加载表格
    // ================================================================
    function CreateAndLoadBatchAddAuxiliaryDeviceTable(isNew) {
        if (isNew && batchAddAuxiliaryDeviceHandsontableHelper != null) {
            if (batchAddAuxiliaryDeviceHandsontableHelper.hot != undefined) {
                batchAddAuxiliaryDeviceHandsontableHelper.hot.destroy();
            }
            batchAddAuxiliaryDeviceHandsontableHelper = null;
        }

        var maskEl = 'BatchAddAuxiliaryDeviceTableDiv_Id';
        mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.loadingData });

        $.ajax({
            url: context + '/wellInformationManagerController/getBatchAddAuxiliaryDeviceTableInfo',
            type: 'POST',
            data: { recordCount: 50 },
            dataType: 'json',
            success: function (result) {
                mini.unmask(maskEl);

                if (batchAddAuxiliaryDeviceHandsontableHelper == null
                    || batchAddAuxiliaryDeviceHandsontableHelper.hot == undefined) {

                    batchAddAuxiliaryDeviceHandsontableHelper = BatchAddAuxiliaryDeviceHandsontableHelper.createNew("BatchAddAuxiliaryDeviceTableDiv_Id");

                    var colHeaders = [];
                    var columns = [];

                    for (var i = 0; i < result.columns.length; i++) {
                        var col = result.columns[i];
                        var dataIndex = col.dataIndex;
                        colHeaders.push(col.header);

                        var colConfig = { data: dataIndex };
                        if (dataIndex.toUpperCase() === "TYPE") {
                            colConfig.type = 'dropdown';
                            colConfig.strict = true;
                            colConfig.allowInvalid = false;
                            colConfig.source = ['泵辅件', '管辅件'];
                        } else if (dataIndex.toUpperCase() === "SORT") {
                            colConfig.type = 'text';
                            colConfig.allowInvalid = true;
                            colConfig.validator = function (val, callback) {
                                return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, batchAddAuxiliaryDeviceHandsontableHelper);
                            };
                        }
                        columns.push(colConfig);
                    }

                    batchAddAuxiliaryDeviceHandsontableHelper.colHeaders = colHeaders;
                    batchAddAuxiliaryDeviceHandsontableHelper.columns = columns;
                    batchAddAuxiliaryDeviceHandsontableHelper.createTable(result.totalRoot || []);
                } else {
                    batchAddAuxiliaryDeviceHandsontableHelper.hot.loadData(result.totalRoot || []);
                }

                // 强制刷新尺寸
                setTimeout(function () {
                    if (batchAddAuxiliaryDeviceHandsontableHelper && batchAddAuxiliaryDeviceHandsontableHelper.hot) {
                        batchAddAuxiliaryDeviceHandsontableHelper.hot.refreshDimensions();
                    }
                }, 50);
            },
            error: function () {
                mini.unmask(maskEl);
                mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
            }
        });
    }

    // ================================================================
    // 保存
    // ================================================================
    function onBatchSave() {
        if (batchAddAuxiliaryDeviceHandsontableHelper == null
            || batchAddAuxiliaryDeviceHandsontableHelper.hot == undefined) return;

        var isCheckout = 1;
        var saveDate = { updatelist: [] };

        var batchAddData = batchAddAuxiliaryDeviceHandsontableHelper.hot.getData();
        for (var i = 0; i < batchAddData.length; i++) {
            if (isNotVal(batchAddData[i][1])) {
                var record = {};
                for (var j = 0; j < batchAddAuxiliaryDeviceHandsontableHelper.columns.length; j++) {
                    record[batchAddAuxiliaryDeviceHandsontableHelper.columns[j].data] = batchAddData[i][j];
                }
                record.id = i;
                saveDate.updatelist.push(record);
            }
        }

        if (saveDate.updatelist.length === 0) {
            mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
            return;
        }

        var maskEl = 'BatchAddAuxiliaryDeviceTableDiv_Id';
        mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/batchAddAuxiliaryDevice',
            type: 'POST',
            data: {
                data: JSON.stringify(saveDate),
                isCheckout: isCheckout
            },
            dataType: 'json',
            success: function (rdata) {
                mini.unmask(maskEl);

                if (rdata.success && rdata.overlayCount == 0) {
                    window.CloseOwnerWindow('ok');
                    if (window._parentRefreshDeviceList) {
                        window._parentRefreshDeviceList(true);
                    }
                    mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                } else if (rdata.success && rdata.overlayCount > 0) {
                    // ★ 有覆盖数据，先关闭自己，交由主页面打开覆盖窗口
                    window.CloseOwnerWindow('collision');
                    if (window._parentOpenCollisionWindow) {
                        window._parentOpenCollisionWindow(rdata, deviceType, orgId);
                    }
                } else {
                    mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(maskEl);
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
                if (batchAddAuxiliaryDeviceHandsontableHelper) {
                    batchAddAuxiliaryDeviceHandsontableHelper.clearContainer();
                }
            }
        });
    }

    // ================================================================
    // Handsontable 辅助类
    // ================================================================
    var BatchAddAuxiliaryDeviceHandsontableHelper = {
        createNew: function (divid) {
            var helper = {};
            helper.hot = '';
            helper.divid = divid;
            helper.colHeaders = [];
            helper.columns = [];

            helper.createTable = function (data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    width: '100%',
                    height: '100%',
                    hiddenColumns: { columns: [0], indicators: false },
                    columns: helper.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: true,
                    colHeaders: helper.colHeaders,
                    columnSorting: true,
                    contextMenu: {
                        items: {
                            "row_above":  { name: _loginUserLanguageResource.contextMenu_insertRowAbove },
                            "row_below":  { name: _loginUserLanguageResource.contextMenu_insertRowBelow },
                            "col_left":   { name: _loginUserLanguageResource.contextMenu_insertColumnLeft },
                            "col_right":  { name: _loginUserLanguageResource.contextMenu_insertColumnRight },
                            "remove_row": { name: _loginUserLanguageResource.contextMenu_removeRow },
                            "remove_col": { name: _loginUserLanguageResource.contextMenu_removeColumn },
                            "merge_cell": { name: _loginUserLanguageResource.contextMenu_mergeCell },
                            "copy":       { name: _loginUserLanguageResource.contextMenu_copy },
                            "cut":        { name: _loginUserLanguageResource.contextMenu_cut }
                        }
                    },
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    cells: function (row, col, prop) {
                        var cellProperties = {
                            renderer: function (instance, td, row, col, prop, value, cellProperties) {
                                if (cellProperties.type == 'dropdown') {
                                    Handsontable.renderers.DropdownRenderer.apply(this, arguments);
                                } else {
                                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                                }
                                td.style.whiteSpace = 'nowrap';
                                td.style.overflow = 'hidden';
                                td.style.textOverflow = 'ellipsis';
                            }
                        };
                        return cellProperties;
                    },
                    afterOnCellMouseOver: function (event, coords, TD) {
                        if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                            var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                            if (isNotVal(rawValue)) TD.title = String(rawValue);
                        }
                    }
                });
            };

            helper.clearContainer = function () {
            };

            return helper;
        }
    };

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>