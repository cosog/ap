<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; }
        /* 关键：让 mini-panel 的 body 撑满，使内部 Handsontable 容器能拿到 100% 高度 */
        .mini-panel-body { padding:0 !important; overflow:hidden; }
    </style>
</head>
<body>

<!-- 全局变量接收参数 -->
<script>
    var deviceType = '';
    var orgId = '';
    var orgName = '';
    var dictDeviceType = '';
</script>

<!-- 整体就是一个 mini-panel，工具条挂在 panel 上 -->
<div id="batchAddPanel" class="mini-panel" style="width:100%;height:100%;"
     showHeader="false" showToolbar="true" showCloseButton="false" showCollapseButton="false"
     bodyStyle="padding:0;overflow:hidden;">

    <!-- 工具条 -->
    <div property="toolbar" style="padding:4px 8px;background:#fafafa;border-bottom:1px solid #e8e8e8;">
        <table style="width:100%;border-collapse:collapse;">
            <tr>
                <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                    <span id="batchAddDeviceWinOrgLabel_Id"></span>
                </td>
                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                    <button id="btnBatchSave" class="mini-button" iconCls="save" onclick="onBatchSave()"></button>
                </td>
            </tr>
        </table>
    </div>

    <!-- 表格容器 -->
    <div id="BatchAddDeviceTableDiv_Id" style="width:100%;height:100%;"></div>
</div>

<script>
    var context = '<%=context%>';
    var batchAddDeviceHandsontableHelper = null;

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.batchAdd;
        mini.get('btnBatchSave').setText(R.save);
    }

    // ================================================================
    // 父窗口调用：接收参数
    // ================================================================
    function setData(data) {
        if (!data) data = {};
        orgId = data.orgId || '';
        orgName = data.orgName || '';
        deviceType = data.deviceType || '';
        dictDeviceType = data.dictDeviceType || deviceType;
        
        document.getElementById('batchAddDeviceWinOrgLabel_Id').innerHTML = 
            _loginUserLanguageResource.owningOrg + "【<font color=red>" + orgName + "</font>】," + _loginUserLanguageResource.pleaseConfirm;
        
        // 等待 MiniUI 布局完成后创建 Handsontable
        mini.layout();
        setTimeout(function() {
            CreateAndLoadBatchAddDeviceTable();
        }, 100);
    }

    // ================================================================
    // 加载表格
    // ================================================================
    function CreateAndLoadBatchAddDeviceTable(isNew) {
        if (isNew && batchAddDeviceHandsontableHelper != null) {
            if (batchAddDeviceHandsontableHelper.hot != undefined) {
                batchAddDeviceHandsontableHelper.hot.destroy();
            }
            batchAddDeviceHandsontableHelper = null;
        }
        
        var leftOrg_Id = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        
        var maskEl = 'BatchAddDeviceTableDiv_Id';
        mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.loadingData });

        $.ajax({
            url: context + '/wellInformationManagerController/getBatchAddDeviceTableInfo',
            type: 'POST',
            data: {
                orgId: orgId, orgIds: leftOrg_Id, deviceType: deviceType,
                dictDeviceType: dictDeviceType, recordCount: 50, page: 1, limit: 10000
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(maskEl);
                if (batchAddDeviceHandsontableHelper == null || batchAddDeviceHandsontableHelper.hot == undefined) {
                    batchAddDeviceHandsontableHelper = BatchAddDeviceHandsontableHelper.createNew("BatchAddDeviceTableDiv_Id");
                    var colHeaders = [];
                    var columns = [];
                    
                    for (var i = 0; i < result.columns.length; i++) {
                        var col = result.columns[i];
                        var dataIndex = col.dataIndex;
                        colHeaders.push(col.header);
                        var colConfig = { data: dataIndex };
                        
                        if (dataIndex.toUpperCase() === "ORGNAME") {
                            colConfig.allowInvalid = true;
                            colConfig.validator = function (val, callback) {
                                return handsontableDataCheck_Org(val, callback, this.row, this.col, batchAddDeviceHandsontableHelper);
                            };
                        } else if (dataIndex.toUpperCase() === "DEVICETYPENAME") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = result.deviceTypeDropdownData || [];
                        } else if (dataIndex.toUpperCase() === "INSTANCENAME") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = result.instanceDropdownData || [];
                        } else if (dataIndex.toUpperCase() === "DISPLAYINSTANCENAME") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = result.displayInstanceDropdownData || [];
                        } else if (dataIndex.toUpperCase() === "REPORTINSTANCENAME") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = result.reportInstanceDropdownData || [];
                        } else if (dataIndex.toUpperCase() === "ALARMINSTANCENAME") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = result.alarmInstanceDropdownData || [];
                        } else if (dataIndex.toUpperCase() === "APPLICATIONSCENARIOSNAME") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = result.applicationScenariosDropdownData || [];
                        } else if (dataIndex.toUpperCase() === "MANUALINTERVENTIONRESULTNAME") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = result.resultNameDropdownData || [];
                        } else if (dataIndex.toUpperCase() === "STATUSNAME") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = [_loginUserLanguageResource.enable, _loginUserLanguageResource.disable];
                        } else if (dataIndex.toUpperCase() === "TCPTYPE") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = ['TCP Server', 'TCP Client'];
                        } else if (dataIndex.toUpperCase() === "PUMPTYPE") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = ['杆式泵', '管式泵'];
                        } else if (dataIndex.toUpperCase() === "BARRELTYPE") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = [_loginUserLanguageResource.barrelType_H, _loginUserLanguageResource.barrelType_L];
                        } else if (dataIndex.toUpperCase() === "PUMPGRADE") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = ['1', '2', '3', '4', '5'];
                        } else if (dataIndex.toUpperCase() === "RODGRADE1" || dataIndex.toUpperCase() === "RODGRADE2" ||
                                   dataIndex.toUpperCase() === "RODGRADE3" || dataIndex.toUpperCase() === "RODGRADE4") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = ['A', 'B', 'C', 'D', 'K', 'KD', 'HL', 'HY'];
                        } else if (dataIndex.toUpperCase() === "CRANKROTATIONDIRECTION") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = ['顺时针', '逆时针'];
                        } else if (dataIndex.toUpperCase() === "MANUFACTURER") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            var mList = [];
                            if (result.pumpingModelInfo && result.pumpingModelInfo.manufacturerList) {
                                for (var m = 0; m < result.pumpingModelInfo.manufacturerList.length; m++) {
                                    mList.push(result.pumpingModelInfo.manufacturerList[m].manufacturer);
                                }
                            }
                            colConfig.source = mList;
                        } else if (dataIndex.toUpperCase() === "MODEL") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = [''];
                        } else if (dataIndex.toUpperCase() === "STROKE") {
                            colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                            colConfig.source = [''];
                        } else if (dataIndex.toUpperCase() === "IPPORT") {
                            colConfig.type = 'text'; colConfig.allowInvalid = true;
                            colConfig.validator = function (val, callback) {
                                return handsontableDataCheck_IpPort_Nullable(val, callback, this.row, this.col, batchAddDeviceHandsontableHelper);
                            };
                        } else if (dataIndex.toUpperCase() === "COMMISSIONINGDATE") {
                            colConfig.type = 'intl-date';
                            colConfig.dateFormat = { year: 'numeric', month: '2-digit', day: '2-digit' };
                            colConfig.locale = 'sv-SE';
                        } else {
                            var upperData = dataIndex.toUpperCase();
                            if (upperData !== "WELLNAME" && upperData !== "DEVICENAME" && upperData !== "SIGNINID" &&
                                upperData !== "VIDEOURL1" && upperData !== "VIDEOKEYNAME1" &&
                                upperData !== "VIDEOURL2" && upperData !== "VIDEOKEYNAME2") {
                                colConfig.type = 'text'; colConfig.allowInvalid = true;
                                colConfig.validator = function (val, callback) {
                                    return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, batchAddDeviceHandsontableHelper);
                                };
                            }
                        }
                        columns.push(colConfig);
                    }
                    
                    batchAddDeviceHandsontableHelper.colHeaders = colHeaders;
                    batchAddDeviceHandsontableHelper.columns = columns;
                    if (parseInt(deviceType) < 200) {
                        batchAddDeviceHandsontableHelper.pumpingModelInfo = result.pumpingModelInfo || {};
                    }
                    batchAddDeviceHandsontableHelper.createTable(result.totalRoot || []);
                } else {
                    batchAddDeviceHandsontableHelper.hot.loadData(result.totalRoot || []);
                }
                
                // 创建后强制刷新一次尺寸（防止 iframe 内尺寸计算出错）
                setTimeout(function() {
                    if (batchAddDeviceHandsontableHelper && batchAddDeviceHandsontableHelper.hot) {
                        batchAddDeviceHandsontableHelper.hot.refreshDimensions();
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
        var isCheckout = 1;
        var saveDate = { updatelist: [] };
        
        if (batchAddDeviceHandsontableHelper != null && batchAddDeviceHandsontableHelper.hot != undefined) {
            var batchAddData = batchAddDeviceHandsontableHelper.hot.getData();
            for (var i = 0; i < batchAddData.length; i++) {
                if (isNotVal(batchAddData[i][1])) {
                    var record = {};
                    for (var j = 0; j < batchAddDeviceHandsontableHelper.columns.length; j++) {
                        record[batchAddDeviceHandsontableHelper.columns[j].data] = batchAddData[i][j];
                    }
                    record.id = i;
                    saveDate.updatelist.push(record);
                }
            }
        }
        
        if (saveDate.updatelist.length === 0) {
            mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
            return;
        }

        var maskEl = 'BatchAddDeviceTableDiv_Id';
        mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/batchAddDevice',
            type: 'POST',
            data: { data: JSON.stringify(saveDate), orgId: orgId, deviceType: deviceType, isCheckout: isCheckout },
            dataType: 'json',
            success: function (rdata) {
                mini.unmask(maskEl);
                
                if (rdata.success && rdata.collisionCount == 0 && rdata.overlayCount == 0) {
                    window.CloseOwnerWindow('ok');
                    if (window._parentRefreshDeviceList) {
                        window._parentRefreshDeviceList(true);
                    }
                    if (rdata.overCount > 0) {
                        mini.alert("<font color=red>" + rdata.overCount + "</font>" + _loginUserLanguageResource.saveFailed, _loginUserLanguageResource.tip);
                    } else {
                        mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                    }
                } else if (rdata.success && (rdata.collisionCount > 0 || rdata.overlayCount > 0)) {
                	// 关闭当前窗口，把数据交给主页面去打开冲突窗口
                    if (window._parentOpenCollisionWindow) {
                        window._parentOpenCollisionWindow(rdata, deviceType, orgId);
                    }
                    window.CloseOwnerWindow('collision');
                } else {
                    mini.alert("<font color=red>" + _loginUserLanguageResource.saveFailed + "</font>", _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(maskEl);
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
            }
        });
    }

    // ================================================================
    // 表格辅助类
    // ================================================================
    var BatchAddDeviceHandsontableHelper = {
        createNew: function (divid) {
            var helper = {};
            helper.hot = '';
            helper.divid = divid;
            helper.colHeaders = [];
            helper.columns = [];
            helper.pumpingModelInfo = {};

            helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap'; td.style.overflow = 'hidden'; td.style.textOverflow = 'ellipsis';
            };

            helper.createTable = function (data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: { columns: [0], indicators: false, copyPasteEnabled: false },
                    columns: helper.columns,
                    stretchH: 'all', autoWrapRow: true, rowHeaders: true, colHeaders: helper.colHeaders,
                    columnSorting: true,
                    contextMenu: {
                        items: {
                            "row_above": { name: _loginUserLanguageResource.contextMenu_insertRowAbove },
                            "row_below": { name: _loginUserLanguageResource.contextMenu_insertRowBelow },
                            "col_left": { name: _loginUserLanguageResource.contextMenu_insertColumnLeft },
                            "col_right": { name: _loginUserLanguageResource.contextMenu_insertColumnRight },
                            "remove_row": { name: _loginUserLanguageResource.contextMenu_removeRow },
                            "remove_col": { name: _loginUserLanguageResource.contextMenu_removeColumn },
                            "merge_cell": { name: _loginUserLanguageResource.contextMenu_mergeCell },
                            "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                            "cut": { name: _loginUserLanguageResource.contextMenu_cut }
                        }
                    },
                    sortIndicator: true, manualColumnResize: true, manualRowResize: true,
                    filters: true, renderAllRows: true, search: true,
                    cells: function (row, col, prop) {
                        var cellProperties = {};
                        var visualColIndex = this.instance.toVisualColumn(col);
                        if (helper.hot != undefined && helper.hot.getDataAtCell != undefined) {
                            if (prop.toUpperCase() === "PUMPGRADE") {
                                var barrelType = helper.hot.getDataAtRowProp(row, "barrelType");
                                if (barrelType == _loginUserLanguageResource.barrelType_H) this.source = ['1', '2', '3', '4', '5'];
                                else if (barrelType == _loginUserLanguageResource.barrelType_L) this.source = ['1', '2', '3'];
                                else if (barrelType == '') this.source = ['1', '2', '3', '4', '5'];
                            }
                            if (prop.toUpperCase() === "MODEL" || prop.toUpperCase() === "STROKE") {
                                var pumpingModelInfo = helper.pumpingModelInfo;
                                if (prop.toUpperCase() === "MODEL") {
                                    var pumpingManufacturer = helper.hot.getDataAtRowProp(row, "manufacturer");
                                    for (var i = 0; i < pumpingModelInfo.manufacturerList.length; i++) {
                                        if (pumpingManufacturer == pumpingModelInfo.manufacturerList[i].manufacturer) {
                                            var modelList = [];
                                            for (var j = 0; j < pumpingModelInfo.manufacturerList[i].modelList.length; j++) {
                                                modelList.push(pumpingModelInfo.manufacturerList[i].modelList[j].model);
                                            }
                                            this.source = modelList; break;
                                        }
                                    }
                                } else if (prop.toUpperCase() === "STROKE") {
                                    var pumpingManufacturer = helper.hot.getDataAtRowProp(row, "manufacturer");
                                    var pumpingModel = helper.hot.getDataAtRowProp(row, "model");
                                    for (var i = 0; i < pumpingModelInfo.manufacturerList.length; i++) {
                                        if (pumpingManufacturer == pumpingModelInfo.manufacturerList[i].manufacturer) {
                                            for (var j = 0; j < pumpingModelInfo.manufacturerList[i].modelList.length; j++) {
                                                if (pumpingModel == pumpingModelInfo.manufacturerList[i].modelList[j].model) {
                                                    this.source = pumpingModelInfo.manufacturerList[i].modelList[j].stroke; break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        var colDef = helper.columns[visualColIndex];
                        if (colDef.type != 'dropdown' && colDef.type != 'checkbox' && colDef.type != 'date' && colDef.type != 'intl-date') {
                            cellProperties.renderer = helper.addCellStyle;
                        }
                        return cellProperties;
                    },
                    afterOnCellMouseOver: function (event, coords, TD) {
                        if (coords.col >= 0 && coords.row >= 0 && helper.hot != undefined) {
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