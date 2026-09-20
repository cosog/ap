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
        /* 关键：让 mini-panel 的 body 撑满，使 Handsontable 容器能拿到 100% 高度 */
        .mini-panel-body { padding:0 !important; overflow:hidden; }
    </style>
</head>
<body>

<!-- 全局变量接收参数 -->
<script>
    var orgId = '';
    var orgName = '';
    var selectedOrgId = '';
</script>

<!-- 整体就是一个 mini-panel，工具条挂在 panel 上 -->
<div id="videoKeyPanel" class="mini-panel" style="width:100%;height:100%;"
     showHeader="false" showToolbar="true" showCollapseButton="false" showCloseButton="false"
     bodyStyle="padding:0;overflow:hidden;">

    <!-- 工具条 -->
    <div property="toolbar" style="padding:4px 8px;background:#fafafa;border-bottom:1px solid #e8e8e8;">
        <table style="width:100%;border-collapse:collapse;">
            <tr>
                <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                    <span id="videoKeyOrgLabel"></span>
                </td>
                <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                    <button id="btnAddVideoKey" class="mini-button" iconCls="add" plain="true" onclick="onAddVideoKey()"></button>
                    <button id="btnDelVideoKey" class="mini-button" iconCls="delete" plain="true" onclick="onDelVideoKey()"></button>
                    <button id="btnSaveVideoKey" class="mini-button" iconCls="save" plain="true" onclick="onSaveVideoKey()"></button>
                </td>
            </tr>
        </table>
    </div>

    <!-- 表格容器 -->
    <div id="VideoKeyDiv_Id" style="width:100%;height:100%;"></div>
</div>

<script>
    var context = '<%=context%>';
    var videoKeyDataHandsontableHelper = null;
    var _vkSelectRow = -1;
    var _vkSelectEndRow = -1;

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.videoKey;
        mini.get('btnAddVideoKey').setText(R.addVideoKey);
        mini.get('btnDelVideoKey').setText(R.deleteVideoKey);
        mini.get('btnSaveVideoKey').setText(R.save);
    }

    // ================================================================
    // 父窗口调用：接收参数并加载表格
    // ================================================================
    function setData(data) {
        if (!data) data = {};
        orgId = data.orgId || '';
        orgName = data.orgName || '';
        selectedOrgId = data.selectedOrgId || '';

        document.getElementById('videoKeyOrgLabel').innerHTML =
            _loginUserLanguageResource.targetOrg + "：【<font color=red>" + orgName + "</font>】，" + _loginUserLanguageResource.pleaseConfirm;

        mini.layout();
        setTimeout(function () {
            CreateDeviceKeyDataTable();
        }, 100);
    }

    // ================================================================
    // 加载表格
    // ================================================================
    function CreateDeviceKeyDataTable() {
        var maskEl = 'VideoKeyDiv_Id';
        mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.loadingData });

        $.ajax({
            url: context + '/wellInformationManagerController/getVideoKeyData',
            type: 'POST',
            data: { orgId: orgId },
            dataType: 'json',
            success: function (result) {
                mini.unmask(maskEl);

                if (videoKeyDataHandsontableHelper == null || videoKeyDataHandsontableHelper.hot == undefined) {
                    videoKeyDataHandsontableHelper = VideoKeyDataHandsontableHelper.createNew("VideoKeyDiv_Id");

                    videoKeyDataHandsontableHelper.colHeaders = [
                        _loginUserLanguageResource.idx,
                        _loginUserLanguageResource.name,
                        'appKey',
                        'secret'
                    ];
                    videoKeyDataHandsontableHelper.columns = [
                        { data: 'id' },
                        { data: 'account' },
                        { data: 'appKey' },
                        { data: 'secret' }
                    ];
                    videoKeyDataHandsontableHelper.CellInfo = result.CellInfo;

                    if (result.totalRoot.length == 0) {
                        videoKeyDataHandsontableHelper.hiddenRows = [0];
                        videoKeyDataHandsontableHelper.createTable([{}]);
                    } else {
                        videoKeyDataHandsontableHelper.hiddenRows = [];
                        videoKeyDataHandsontableHelper.createTable(result.totalRoot);
                    }
                } else {
                    if (result.totalRoot.length == 0) {
                        videoKeyDataHandsontableHelper.hiddenRows = [0];
                        videoKeyDataHandsontableHelper.hot.loadData([{}]);
                    } else {
                        videoKeyDataHandsontableHelper.hiddenRows = [];
                        videoKeyDataHandsontableHelper.hot.loadData(result.totalRoot);
                    }
                }

                if (videoKeyDataHandsontableHelper.hiddenRows.length > 0) {
                    var plugin = videoKeyDataHandsontableHelper.hot.getPlugin('hiddenRows');
                    plugin.hideRows(videoKeyDataHandsontableHelper.hiddenRows);
                    videoKeyDataHandsontableHelper.hot.render();
                }

                // 强制刷新尺寸
                setTimeout(function () {
                    if (videoKeyDataHandsontableHelper && videoKeyDataHandsontableHelper.hot) {
                        videoKeyDataHandsontableHelper.hot.refreshDimensions();
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
    // 添加视频密钥
    // ================================================================
    function onAddVideoKey() {
        mini.open({
            title: _loginUserLanguageResource.addVideoKey,
            url: context + '/miniui-app/modules/device/videoKeyAddWindow.jsp',
            width: 420,
            height: 300,
            modal: true,
            allowResize: false,
            maxable: false,
            onload: function () {
                var iframe = this.getIFrameEl();
                var contentWindow = iframe.contentWindow;
                contentWindow.setData({
                    orgId: selectedOrgId,
                    orgName: orgName
                });
                // 子窗口保存成功回调刷新当前列表
                contentWindow._parentRefreshVideoKeyList = function () {
                    CreateDeviceKeyDataTable();
                };
            }
        });
    }

    // ================================================================
    // 删除视频密钥
    // ================================================================
    function onDelVideoKey() {
        if (_vkSelectRow === -1 || _vkSelectEndRow === -1) {
            mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
            return;
        }

        var startRow = _vkSelectRow;
        var endRow = _vkSelectEndRow;

        mini.confirm(_loginUserLanguageResource.confirmDelete, _loginUserLanguageResource.tip, function (action) {
            if (action !== 'ok') return;

            var delidslist = [];
            for (var i = startRow; i <= endRow; i++) {
                var rowdata = videoKeyDataHandsontableHelper.hot.getDataAtRow(i);
                if (rowdata[0] != null && parseInt(rowdata[0]) > 0) {
                    delidslist.push(rowdata[0]);
                }
            }

            if (delidslist.length === 0) {
                mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
                return;
            }

            var saveData = {
                updatelist: [],
                insertlist: [],
                delidslist: delidslist
            };

            mini.mask({ el: 'VideoKeyDiv_Id', cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

            $.ajax({
                url: context + '/wellInformationManagerController/saveVideoKeyHandsontableData',
                type: 'POST',
                data: { data: JSON.stringify(saveData) },
                dataType: 'json',
                success: function (rdata) {
                    mini.unmask('VideoKeyDiv_Id');
                    if (rdata.success) {
                        mini.alert(_loginUserLanguageResource.deleteSuccessfully, _loginUserLanguageResource.tip);
                        videoKeyDataHandsontableHelper.clearContainer();
                        _vkSelectRow = -1;
                        _vkSelectEndRow = -1;
                        CreateDeviceKeyDataTable();
                    } else {
                        mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
                    }
                },
                error: function () {
                    mini.unmask('VideoKeyDiv_Id');
                    mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
                    videoKeyDataHandsontableHelper.clearContainer();
                }
            });
        });
    }

    // ================================================================
    // 保存
    // ================================================================
    function onSaveVideoKey() {
        if (videoKeyDataHandsontableHelper == null || videoKeyDataHandsontableHelper.hot == undefined) return;

        var data = videoKeyDataHandsontableHelper.hot.getData();
        if (data.length === 0) {
            mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
            return;
        }

        videoKeyDataHandsontableHelper.insertExpressCount();

        mini.mask({ el: 'VideoKeyDiv_Id', cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/saveVideoKeyHandsontableData',
            type: 'POST',
            data: {
                data: JSON.stringify(videoKeyDataHandsontableHelper.AllData),
                orgId: orgId
            },
            dataType: 'json',
            success: function (rdata) {
                mini.unmask('VideoKeyDiv_Id');
                if (rdata.success) {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                    videoKeyDataHandsontableHelper.clearContainer();
                    CreateDeviceKeyDataTable();
                } else {
                    mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask('VideoKeyDiv_Id');
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
                videoKeyDataHandsontableHelper.clearContainer();
            }
        });
    }

    // ================================================================
    // Handsontable 辅助类
    // ================================================================
    var VideoKeyDataHandsontableHelper = {
        createNew: function (divid) {
            var helper = {};
            helper.divid = divid;
            helper.hot = '';
            helper.colHeaders = [];
            helper.columns = [];
            helper.hiddenRows = [];
            helper.AllData = {};
            helper.updatelist = [];
            helper.delidslist = [];
            helper.insertlist = [];
            helper.CellInfo = {};

            helper.createTable = function (data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: { columns: [0], indicators: false, copyPasteEnabled: false },
                    hiddenRows: { rows: [], indicators: false, copyPasteEnabled: false },
                    colWidths: [2, 3, 10, 10],
                    columns: helper.columns,
                    stretchH: 'all',
                    rowHeaders: true,
                    colHeaders: helper.colHeaders,
                    columnSorting: true,
                    allowInsertRow: false,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    contextMenu: {
                        items: {
                            "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                            "cut": { name: _loginUserLanguageResource.contextMenu_cut }
                        }
                    },
                    cells: function (row, col, prop) {
                        return {};
                    },
                    afterSelectionEnd: function (row, column, row2, column2, preventScrolling, selectionLayerLevel) {
                        if (row < 0 && row2 < 0) {
                            _vkSelectRow = -1;
                            _vkSelectEndRow = -1;
                        } else {
                            if (row < 0) row = 0;
                            if (row2 < 0) row2 = 0;
                            var startRow = row;
                            var endRow = row2;
                            if (row > row2) {
                                startRow = row2;
                                endRow = row;
                            }
                            _vkSelectRow = startRow;
                            _vkSelectEndRow = endRow;
                        }
                    },
                    beforeRemoveRow: function (index, amount) {
                        var ids = [];
                        if (amount != 0) {
                            for (var i = index; i < amount + index; i++) {
                                var rowdata = helper.hot.getDataAtRow(i);
                                ids.push(rowdata[0]);
                            }
                            helper.delExpressCount(ids);
                            helper.screening();
                        }
                    },
                    afterChange: function (changes, source) {
                        if (changes != null) {
                            for (var i = 0; i < changes.length; i++) {
                                var row = changes[i][0];
                                var oldValue = changes[i][2];
                                var newValue = changes[i][3];
                                var rowdata = helper.hot.getDataAtRow(row);
                                var recordId = rowdata[0];

                                if (oldValue != newValue && recordId != null && recordId > 0) {
                                    var data = {};
                                    for (var j = 0; j < helper.columns.length; j++) {
                                        data[helper.columns[j].data] = rowdata[j];
                                    }
                                    helper.updateExpressCount(data);
                                }
                            }
                        }
                    }
                });
            };

            helper.insertExpressCount = function () {
                var idsdata = helper.hot.getDataAtCol(0);
                for (var i = 0; i < idsdata.length; i++) {
                    if (idsdata[i] == null || idsdata[i] < 0) {
                        var rowdata = helper.hot.getDataAtRow(i);
                        if (rowdata != null) {
                            var data = {};
                            for (var j = 0; j < helper.columns.length; j++) {
                                data[helper.columns[j].data] = rowdata[j];
                            }
                            helper.insertlist.push(data);
                        }
                    }
                }
                if (helper.insertlist.length != 0) {
                    helper.AllData.insertlist = helper.insertlist;
                }
            };

            helper.delExpressCount = function (ids) {
                for (var i = 0; i < ids.length; i++) {
                    if (ids[i] != null) {
                        helper.delidslist.push(ids[i]);
                    }
                }
                helper.AllData.delidslist = helper.delidslist;
            };

            helper.screening = function () {
                if (helper.updatelist.length != 0 && helper.delidslist.length != 0) {
                    for (var i = 0; i < helper.delidslist.length; i++) {
                        for (var j = 0; j < helper.updatelist.length; j++) {
                            if (helper.updatelist[j].id == helper.delidslist[i]) {
                                helper.updatelist.splice(j, 1);
                            }
                        }
                    }
                    helper.AllData.updatelist = helper.updatelist;
                }
            };

            helper.updateExpressCount = function (data) {
                if (JSON.stringify(data) != "{}") {
                    var flag = true;
                    for (var i = 0; i < helper.updatelist.length; i++) {
                        if (helper.updatelist[i].id == data.id) {
                            flag = false;
                            helper.updatelist[i] = data;
                            break;
                        }
                    }
                    if (flag) helper.updatelist.push(data);
                    helper.AllData.updatelist = helper.updatelist;
                }
            };

            helper.clearContainer = function () {
                helper.AllData = {};
                helper.updatelist = [];
                helper.delidslist = [];
                helper.insertlist = [];
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