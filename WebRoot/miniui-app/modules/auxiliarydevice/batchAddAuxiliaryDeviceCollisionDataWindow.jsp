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
        .mini-panel-body { padding:0 !important; overflow:hidden; }
    </style>
</head>
<body>

<!-- 全局变量接收参数 -->
<script>
    var deviceType = '';
    var orgId = '';
</script>

<!-- 单 panel + 工具条 + 表格 -->
<div id="batchAddAuxiliaryDeviceOverlayPanel" class="mini-panel" style="width:100%;height:100%;"
     showHeader="true" showToolbar="true" showCloseButton="false" showCollapseButton="false"
     bodyStyle="padding:0;overflow:hidden;">

    <div property="toolbar" style="padding:4px 8px;background:#fafafa;border-bottom:1px solid #e8e8e8;">
        <table style="width:100%;border-collapse:collapse;">
            <tr>
                <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                    <span id="batchAddAuxiliaryDeviceCollisionInfoLabel_Id"></span>
                </td>
                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                    <button id="btnCollisionSave" class="mini-button" iconCls="save" onclick="onCollisionSave()"></button>
                </td>
            </tr>
        </table>
    </div>

    <div id="BatchAddAuxiliaryDeviceOverlayDataTableDiv_Id" style="width:100%;height:100%;"></div>
</div>

<script>
    var context = '<%=context%>';
    var batchAddAuxiliaryDeviceOverlayDataHandsontableHelper = null;

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.dataCollision;
        mini.get('btnCollisionSave').setText(R.save);

        // 面板标题
        mini.get('batchAddAuxiliaryDeviceOverlayPanel').setTitle(
            '已有记录(<font color=red>继续保存，表中数据将覆盖已有记录</font>)'
        );
    }

    // ================================================================
    // 父窗口调用
    // ================================================================
    function setData(data) {
        if (!data) return;
        deviceType = data.deviceType || '';
        orgId = data.orgId || '';

        var result = data.result;
        var overlayCount = result.overlayCount || 0;

        document.getElementById('batchAddAuxiliaryDeviceCollisionInfoLabel_Id').innerHTML =
            '覆盖记录数：<font color=red>' + overlayCount + '</font> ';

        if (overlayCount > 0) {
            CreateAndLoadBatchAddAuxiliaryDeviceOverlayDataTable(result);
        }

        setTimeout(function () { mini.layout(); }, 100);
    }

    // ================================================================
    // 加载覆盖数据表格
    // ================================================================
    function CreateAndLoadBatchAddAuxiliaryDeviceOverlayDataTable(result) {
        if (batchAddAuxiliaryDeviceOverlayDataHandsontableHelper == null
            || batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot == undefined) {

            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper =
                BatchAddAuxiliaryDeviceOverlayDataHandsontableHelper.createNew("BatchAddAuxiliaryDeviceOverlayDataTableDiv_Id");

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
                        return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, batchAddAuxiliaryDeviceOverlayDataHandsontableHelper);
                    };
                }
                columns.push(colConfig);
            }

            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.colHeaders = colHeaders;
            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns = columns;
            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.createTable(result.overlayList || []);
        } else {
            batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.loadData(result.overlayList || []);
        }

        setTimeout(function () {
            if (batchAddAuxiliaryDeviceOverlayDataHandsontableHelper
                && batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot) {
                batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.refreshDimensions();
            }
        }, 50);
    }

    // ================================================================
    // 保存（覆盖）
    // ================================================================
    function onCollisionSave() {
        var isCheckout = 0;
        var saveDate = { updatelist: [] };

        if (batchAddAuxiliaryDeviceOverlayDataHandsontableHelper != null
            && batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot != undefined) {
            var batchAddData = batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.hot.getData();
            for (var i = 0; i < batchAddData.length; i++) {
                if (isNotVal(batchAddData[i][1])) {
                    var record = {};
                    for (var j = 0; j < batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns.length; j++) {
                        record[batchAddAuxiliaryDeviceOverlayDataHandsontableHelper.columns[j].data] = batchAddData[i][j];
                    }
                    record.id = i;
                    saveDate.updatelist.push(record);
                }
            }
        }

        mini.mask({ el: document.body, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/batchAddAuxiliaryDevice',
            type: 'POST',
            data: {
                data: JSON.stringify(saveDate),
                isCheckout: isCheckout
            },
            dataType: 'json',
            success: function (rdata) {
                mini.unmask(document.body);

                if (rdata.success && rdata.overlayCount == 0) {
                    window.CloseOwnerWindow('ok');
                    if (window._parentRefreshDeviceList) {
                        window._parentRefreshDeviceList(true);
                    }
                    mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                } else if (rdata.success && rdata.overlayCount > 0) {
                    // 再次出现覆盖：就地刷新
                    CreateAndLoadBatchAddAuxiliaryDeviceOverlayDataTable(rdata);
                    mini.alert('覆盖记录数：<font color=red>' + rdata.overlayCount + '</font>', _loginUserLanguageResource.tip);
                } else {
                    mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
            }
        });
    }

    // ================================================================
    // Handsontable 辅助类
    // ================================================================
    var BatchAddAuxiliaryDeviceOverlayDataHandsontableHelper = {
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
                    allowInsertRow: false,
                    contextMenu: {
                        items: {
                            "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                            "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
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