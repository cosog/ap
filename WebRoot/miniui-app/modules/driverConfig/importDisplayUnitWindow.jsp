<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入显示单元</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar { flex-shrink:0; padding:4px 8px; border-bottom:1px solid #e8e8e8; background:#fafafa; display:flex; align-items:center; flex-wrap:wrap; gap:4px; }
        .grid-title-bar {
            border-bottom: 1px solid #e8e8e8;
            padding: 4px 8px;
            background: #f5f5f5;
            font-weight: bold;
            font-size: 13px;
            color: #333;
            flex-shrink: 0;
        }
        .mini-treegrid { width:100%; height:100%; }
        /* ★ 表格外层 flex 容器：允许收缩 */
        .hot-wrapper {
            flex: 1;
            min-height: 0;
            overflow: hidden;
            padding: 4px;
            box-sizing: border-box;
        }
        /* ★ 内层 div：让 Handsontable 基于它渲染 */
        .hot-inner {
            width: 100%;
            height: 100%;
            overflow: hidden;
            position: relative;
        }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 顶部工具栏 -->
    <div class="mini-toolbar">
        <span style="font-size:12px;color:#666;" id="uploadLabel">上传文件</span>
        <form id="uploadForm" action="<%=context%>/acquisitionUnitManagerController/uploadImportedDisplayUnitFile"
              method="post" enctype="multipart/form-data" target="uploadFrame" style="display:inline;">
            <input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;"
                   limitType="*.json" onfileselect="onFileSelect" />
            <iframe name="uploadFrame" style="display:none;"></iframe>
        </form>
        <span style="flex:1;"></span>
        <button id="saveAllBtn" class="mini-button" iconCls="save" onclick="onSaveAll()">保存全部</button>
    </div>

    <!-- 主体：左右 Splitter -->
    <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
        <!-- 左侧：预导入单元树 -->
        <div size="30%" showCollapseButton="true" collapseDirection="left" minSize="250">
            <div style="padding:4px;height:100%;background:#fafafa;">
                <div id="unitTree" class="mini-treegrid"
                     style="width:100%;height:100%;"
                     showTreeIcon="true"
                     treeColumn="taskname"
                     idField="id"
                     parentField="pid"
                     resultAsTree="true"
                     onbeforeload="onTreeBeforeLoad"
                     onload="onTreeLoad"
                     ondrawcell="onTreeDrawcell"
                     onnodeselect="onTreeSelect">
                </div>
            </div>
        </div>
        <!-- 右侧：上下 Splitter（采集项 + 控制项） -->
        <div size="70%" showCollapseButton="false">
            <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
                <!-- 上半：采集项配置 -->
                <div size="50%" showCollapseButton="false">
                    <div style="padding:4px;height:100%;background:#fff;display:flex;flex-direction:column;overflow:hidden;">
                        <div class="grid-title-bar" id="acqItemsTitle"></div>
                        <!-- ★ 外层 flex 容器 -->
                        <div class="hot-wrapper">
                            <!-- ★ 内层 100%×100% div，Handsontable 在这里创建 -->
                            <div id="importAcqItemsTableDiv_id" class="hot-inner"></div>
                        </div>
                    </div>
                </div>
                <!-- 下半：控制项配置 -->
                <div size="50%" showCollapseButton="true" collapseDirection="bottom">
                    <div style="padding:4px;height:100%;background:#fff;display:flex;flex-direction:column;overflow:hidden;">
                        <div class="grid-title-bar" id="ctrlItemsTitle"></div>
                        <!-- ★ 外层 flex 容器 -->
                        <div class="hot-wrapper">
                            <!-- ★ 内层 100%×100% div -->
                            <div id="importCtrlItemsTableDiv_id" class="hot-inner"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeId = '';
    var deviceTypeName = '';

    var importDisplayUnitAcqItemsHelper = null;
    var importDisplayUnitCtrlItemsHelper = null;
    var currentTreeNode = null;

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.importDisplayUnit;
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.saveAll);
        var tree = mini.get('unitTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);
        document.getElementById('acqItemsTitle').innerText = _loginUserLanguageResource.acquisitionItemConfig;
        document.getElementById('ctrlItemsTitle').innerText = _loginUserLanguageResource.controlItemConfig;
    }

    // ================================================================
    // 接收父窗口数据
    // ================================================================
    function setData(data) {
        if (data && data.deviceTypeId) {
            deviceTypeId = data.deviceTypeId;
            deviceTypeName = data.deviceTypeName || '';
        }
    }

    // ================================================================
    // 文件上传
    // ================================================================
    function onFileSelect(e) {
        var form = document.getElementById('uploadForm');
        mini.mask({ el: document.body, html: _loginUserLanguageResource.uploadingFile || '上传中...' });
        form.submit();
        var iframe = document.getElementsByName('uploadFrame')[0];
        iframe.onload = function() {
            mini.unmask(document.body);
            try {
                var responseText = iframe.contentWindow.document.body.innerText;
                var result = JSON.parse(responseText);
                if (result && result.flag) {
                    mini.alert(_loginUserLanguageResource.loadSuccessfully);
                    var tree = mini.get('unitTree');
                    if (tree) {
                        tree.load(context + '/acquisitionUnitManagerController/getUploadedDisplayUnitTreeData');
                    }
                } else {
                    mini.alert(_loginUserLanguageResource.uploadDataError || '上传数据错误');
                }
            } catch(ex) {
                mini.alert(_loginUserLanguageResource.uploadFail || '上传失败');
            }
            iframe.onload = null;
        };
        var fileInput = document.getElementById('fileUpload');
        if (fileInput) fileInput.value = '';
    }

    // ================================================================
    // 树事件
    // ================================================================
    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceType = deviceTypeId;
        e.params = params;
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        // 设置列（动态）
        var columns = [
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.importUnit,
                headerAlign: 'left',
                align: 'left',
                width: '40%'
            },
            {
                field: 'msg',
                header: _loginUserLanguageResource.collisionInfo,
                headerAlign: 'center',
                align: 'center',
                width: '40%'
            },
            {
                field: 'action',
                header: _loginUserLanguageResource.save,
                headerAlign: 'center',
                align: 'center',
                width: '20%'
            }
        ];
        tree.setColumns(columns);

        // 默认选中第一个可保存的显示单元节点（classes==1）
        var root = tree.getRootNode();
        if (root && root.children) {
            var targetNode = null;
            function findNode(nodes) {
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    if (node.classes === 1) {
                        targetNode = node;
                        return;
                    }
                    if (node.children && node.children.length > 0) {
                        findNode(node.children);
                        if (targetNode) return;
                    }
                }
            }
            findNode(root.children);
            if (targetNode) {
                setTimeout(function() {
                    tree.selectNode(targetNode);
                }, 50);
            }
        }
        // 展开所有节点
        tree.expandAll();
    }

    function onTreeDrawcell(e) {
        var field = e.field;
        var record = e.record;
        if (field === 'msg' && record.classes == 1) {
            onMsgRenderer(e);
        } else if (field === 'action' && record.classes == 1) {
            onActionRenderer(e);
        }
    }

    function onTreeSelect(e) {
        var node = e.node;
        var tree = e.sender;
        if (!node) return;
        currentTreeNode = node;
        // 根据节点类型加载配置数据
        if (node.classes === 0) {
            if (node.children && node.children.length > 0) {
                loadConfigDataByNode(tree, node.children[0]);
            } else {
                clearHandsontable();
            }
        } else if (node.classes === 1) {
            loadConfigDataByNode(tree, node);
        } else {
            clearHandsontable();
        }
    }

    function loadConfigDataByNode(tree, node) {
        // 与 ExtJS 的 CreateImportDisplayUnitAcqItemsInfoTable 参数一致
        var protocolName = node.protocol || '';
        var protocolDeviceType = node.protocolDeviceType || '';
        var acqUnit = node.acqUnit || '';
        var unitName = node.text || '';
        var calculateType = node.calculateType || 0;

        loadAcqItemsConfig(protocolName, protocolDeviceType, acqUnit, unitName, calculateType);
        loadCtrlItemsConfig(protocolName, protocolDeviceType, acqUnit, unitName, calculateType);
    }

    // ================================================================
    // 冲突信息渲染器
    // ================================================================
    function onMsgRenderer(e) {
        var record = e.record;
        var value = record.msg || '';
        var saveSign = record.saveSign;
        var color = '#DC2828';
        if (saveSign == 0) {
            color = '#000000';
        }
        e.cellStyle = 'color:' + color + ';';
        e.cellHtml = value ? '<span title="' + value + '">' + value + '</span>' : '';
    }

    // ================================================================
    // 操作列渲染器（单个保存）
    // ================================================================
    function onActionRenderer(e) {
        var record = e.record;
        if (record.classes === 1 && record.saveSign != 2) {
            var unitName = encodeURIComponent(record.text || '');
            var acqUnit = encodeURIComponent(record.acqUnit || '');
            var protocolName = encodeURIComponent(record.protocol || '');
            var protocolDeviceType = encodeURIComponent(record.protocolDeviceType || '');
            var saveSign = encodeURIComponent(record.saveSign || '');
            var msg = encodeURIComponent(record.msg || '');
            e.cellHtml = '<a href="javascript:void(0)" onclick="saveSingleUnit(\'' + unitName + '\',\'' + acqUnit + '\',\'' + protocolName + '\',\'' + protocolDeviceType + '\',\'' + saveSign + '\',\'' + msg + '\')" style="text-decoration:none;">' + (_loginUserLanguageResource.save || '保存') + '</a>';
        } else {
            e.cellHtml = '';
        }
    }

    // ================================================================
    // 加载采集项配置
    // ================================================================
    function loadAcqItemsConfig(protocolName, protocolDeviceType, acqUnit, unitName, calculateType) {
        // ★ 用内层 div 作为 Handsontable 容器
        var container = document.getElementById('importAcqItemsTableDiv_id');
        if (!container) return;
        if (importDisplayUnitAcqItemsHelper && importDisplayUnitAcqItemsHelper.hot) {
            importDisplayUnitAcqItemsHelper.hot.destroy();
            importDisplayUnitAcqItemsHelper = null;
        }
        container.innerHTML = '';

        // 设置标题
        var titleEl = document.getElementById('acqItemsTitle');
        if (titleEl) {
            var base = _loginUserLanguageResource.acquisitionItemConfig || '采集项配置';
            titleEl.innerText = unitName ? (unitName + '/' + base) : base;
        }

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.loadingData || '加载中...' });
        $.ajax({
            type: 'POST',
            url: context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
            data: {
                protocolName: protocolName,
                protocolDeviceType: protocolDeviceType,
                acqUnitName: acqUnit,
                unitName: unitName,
                calculateType: calculateType,
                type: 0
            },
            dataType: 'json',
            success: function(result) {
                mini.unmask(document.body);
                var data = result.totalRoot || [];
                if (data.length === 0) {
                    for (var i = 0; i < 30; i++) data.push({});
                }
                importDisplayUnitAcqItemsHelper = createDisplayUnitAcqItemsHandsontable(container, data);
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    // ================================================================
    // 加载控制项配置
    // ================================================================
    function loadCtrlItemsConfig(protocolName, protocolDeviceType, acqUnit, unitName, calculateType) {
        // ★ 用内层 div 作为 Handsontable 容器
        var container = document.getElementById('importCtrlItemsTableDiv_id');
        if (!container) return;
        if (importDisplayUnitCtrlItemsHelper && importDisplayUnitCtrlItemsHelper.hot) {
            importDisplayUnitCtrlItemsHelper.hot.destroy();
            importDisplayUnitCtrlItemsHelper = null;
        }
        container.innerHTML = '';

        // 设置标题
        var titleEl = document.getElementById('ctrlItemsTitle');
        if (titleEl) {
            var base = _loginUserLanguageResource.controlItemConfig || '控制项配置';
            titleEl.innerText = unitName ? (unitName + '/' + base) : base;
        }

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.loadingData || '加载中...' });
        $.ajax({
            type: 'POST',
            url: context + '/acquisitionUnitManagerController/getImportDisplayUnitItemsConfigData',
            data: {
                protocolName: protocolName,
                protocolDeviceType: protocolDeviceType,
                acqUnitName: acqUnit,
                unitName: unitName,
                calculateType: calculateType,
                type: 2
            },
            dataType: 'json',
            success: function(result) {
                mini.unmask(document.body);
                var data = result.totalRoot || [];
                if (data.length === 0) {
                    for (var i = 0; i < 30; i++) data.push({});
                }
                importDisplayUnitCtrlItemsHelper = createDisplayUnitCtrlItemsHandsontable(container, data);
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    // ================================================================
    // 创建采集项配置的 Handsontable（只读）
    // ================================================================
    function createDisplayUnitAcqItemsHandsontable(container, data) {
        var helper = {};
        var colHeaders = [
            ['','','','','','',
             {label: _loginUserLanguageResource.realtimeMonitoring, colspan: 7},
             {label: _loginUserLanguageResource.historyQuery, colspan: 7},
             '','','','','','',''],
            ['','','','','','',
             {label: _loginUserLanguageResource.deviceOverview, colspan: 2},
             {label: _loginUserLanguageResource.dynamicData, colspan: 4},
             _loginUserLanguageResource.trendCurve,
             {label: _loginUserLanguageResource.deviceOverview, colspan: 2},
             {label: _loginUserLanguageResource.historyData, colspan: 4},
             _loginUserLanguageResource.trendCurve,
             '','','','','','',''],
            ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
             _loginUserLanguageResource.dataSource, _loginUserLanguageResource.unit,
             _loginUserLanguageResource.showLevel,
             _loginUserLanguageResource.deviceOverview, _loginUserLanguageResource.columnSort,
             _loginUserLanguageResource.dynamicData, _loginUserLanguageResource.columnSort,
             _loginUserLanguageResource.foregroundColor, _loginUserLanguageResource.backgroundColor,
             _loginUserLanguageResource.curveConfig,
             _loginUserLanguageResource.deviceOverview, _loginUserLanguageResource.columnSort,
             _loginUserLanguageResource.historyData, _loginUserLanguageResource.columnSort,
             _loginUserLanguageResource.foregroundColor, _loginUserLanguageResource.backgroundColor,
             _loginUserLanguageResource.curveConfig,
             _loginUserLanguageResource.showName,
             '','','','','','','']
        ];

        var columns = [
            {data: 'checked', type: 'checkbox'},
            {data: 'id'},
            {data: 'title'},
            {data: 'dataSource'},
            {data: 'unit'},
            {data: 'showLevel', type: 'text'},
            {data: 'realtimeOverview', type: 'checkbox'},
            {data: 'realtimeOverviewSort', type: 'text'},
            {data: 'realtimeData', type: 'checkbox'},
            {data: 'realtimeSort', type: 'text'},
            {data: 'realtimeColor'},
            {data: 'realtimeBgColor'},
            {data: 'realtimeCurveConfShowValue'},
            {data: 'historyOverview', type: 'checkbox'},
            {data: 'historyOverviewSort', type: 'text'},
            {data: 'historyData', type: 'checkbox'},
            {data: 'historySort', type: 'text'},
            {data: 'historyColor'},
            {data: 'historyBgColor'},
            {data: 'historyCurveConfShowValue'},
            {data: 'switchingValueShowType'},
            {data: 'realtimeCurveConf'},
            {data: 'historyCurveConf'},
            {data: 'resolutionMode'},
            {data: 'addr'},
            {data: 'bitIndex'},
            {data: 'type'},
            {data: 'code'}
        ];

        var colWidths = [25, 50, 140, 80, 80, 80, 80, 80, 60, 80, 80, 80,
            80, 80, 80, 100, 80, 80, 80, 100, 100, 100, 100, 100, 100, 100, 80, 80];

        var hot = new Handsontable(container, {
            licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            theme: 'ht-theme-classic',
            data: data,
            hiddenColumns: {
                columns: [0,6,7,13,14,21,22,23,24,25,26,27],
                indicators: false,
                copyPasteEnabled: false
            },
            colWidths: colWidths,
            columns: columns,
            stretchH: 'all',
            width: '100%', 
            height: '100%',
            autoWrapRow: true,
            rowHeaders: false,
            nestedHeaders: colHeaders,
            columnSorting: true,
            sortIndicator: true,
            manualColumnResize: true,
            manualRowResize: true,
            filters: true,
            renderAllRows: true,
            search: true,
            cells: function(row, col, prop) {
                var cellProperties = {};
                var visualColIndex = this.instance.toVisualColumn(col);
                cellProperties.editor = false;
                var bg = 'rgb(245, 245, 245)';
                if (visualColIndex === 12 || visualColIndex === 19) {
                    cellProperties.renderer = function(instance, td, row, col, prop, value) {
                        Handsontable.renderers.TextRenderer.apply(this, arguments);
                        if (value != null && value !== '') {
                            var arr = (value + '').split(';');
                            if (arr.length === 3) td.style.backgroundColor = '#' + arr[2];
                            else td.style.backgroundColor = bg;
                        } else {
                            td.style.backgroundColor = bg;
                        }
                        td.style.whiteSpace = 'nowrap';
                        td.style.overflow = 'hidden';
                        td.style.textOverflow = 'ellipsis';
                    };
                } else if (visualColIndex === 10 || visualColIndex === 11 || visualColIndex === 17 || visualColIndex === 18) {
                    cellProperties.renderer = function(instance, td, row, col, prop, value) {
                        Handsontable.renderers.TextRenderer.apply(this, arguments);
                        if (value != null && value !== '') td.style.backgroundColor = '#' + value;
                        else td.style.backgroundColor = bg;
                        td.style.whiteSpace = 'nowrap';
                        td.style.overflow = 'hidden';
                        td.style.textOverflow = 'ellipsis';
                    };
                } else {
                    cellProperties.renderer = function(instance, td, row, col, prop, value, cellProperties) {
                        if (cellProperties.type === 'checkbox') {
                            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
                            td.style.backgroundColor = bg;
                        } else if (cellProperties.type === 'dropdown') {
                            Handsontable.renderers.DropdownRenderer.apply(this, arguments);
                            td.style.backgroundColor = bg;
                        } else {
                            Handsontable.renderers.TextRenderer.apply(this, arguments);
                            td.style.backgroundColor = bg;
                        }
                        td.style.whiteSpace = 'nowrap';
                        td.style.overflow = 'hidden';
                        td.style.textOverflow = 'ellipsis';
                    };
                }
                return cellProperties;
            }
        });
        helper.hot = hot;
        return helper;
    }

    // ================================================================
    // 创建控制项配置的 Handsontable（只读）
    // ================================================================
    function createDisplayUnitCtrlItemsHandsontable(container, data) {
        var helper = {};
        var colHeaders = [
            _loginUserLanguageResource.idx,
            _loginUserLanguageResource.name,
            _loginUserLanguageResource.unit,
            _loginUserLanguageResource.showLevel,
            _loginUserLanguageResource.columnSort,
            _loginUserLanguageResource.showName
        ];
        var columns = [
            {data: 'id'},
            {data: 'title'},
            {data: 'unit'},
            {data: 'showLevel'},
            {data: 'realtimeSort'},
            {data: 'switchingValueShowType'}
        ];

        var hot = new Handsontable(container, {
            licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
            theme: 'ht-theme-classic',
            data: data,
            colWidths: [50, 140, 80, 60, 60, 60],
            columns: columns,
            hiddenColumns: {
                columns: [5],
                indicators: false,
                copyPasteEnabled: false
            },
            stretchH: 'all',
            width: '100%',
            height: '100%',
            autoWrapRow: true,
            rowHeaders: false,
            colHeaders: colHeaders,
            columnSorting: true,
            sortIndicator: true,
            manualColumnResize: true,
            manualRowResize: true,
            filters: true,
            renderAllRows: true,
            search: true,
            cells: function(row, col, prop) {
                var cellProperties = {};
                cellProperties.editor = false;
                cellProperties.renderer = function(instance, td, row, col, prop, value, cellProperties) {
                    if (cellProperties.type === 'checkbox') {
                        Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
                    } else {
                        Handsontable.renderers.TextRenderer.apply(this, arguments);
                    }
                    td.style.backgroundColor = 'rgb(245, 245, 245)';
                    td.style.whiteSpace = 'nowrap';
                    td.style.overflow = 'hidden';
                    td.style.textOverflow = 'ellipsis';
                };
                return cellProperties;
            }
        });
        helper.hot = hot;
        return helper;
    }

    function clearHandsontable() {
        if (importDisplayUnitAcqItemsHelper && importDisplayUnitAcqItemsHelper.hot) {
            importDisplayUnitAcqItemsHelper.hot.destroy();
            importDisplayUnitAcqItemsHelper = null;
        }
        if (importDisplayUnitCtrlItemsHelper && importDisplayUnitCtrlItemsHelper.hot) {
            importDisplayUnitCtrlItemsHelper.hot.destroy();
            importDisplayUnitCtrlItemsHelper = null;
        }
        var acq = document.getElementById('importAcqItemsTableDiv_id');
        if (acq) acq.innerHTML = '';
        var ctrl = document.getElementById('importCtrlItemsTableDiv_id');
        if (ctrl) ctrl.innerHTML = '';
    }

    // ================================================================
    // 单个保存
    // ================================================================
    function saveSingleUnit(unitName, acqUnit, protocolName, protocolDeviceType, saveSign, msg) {
        unitName = decodeURIComponent(unitName);
        acqUnit = decodeURIComponent(acqUnit);
        protocolName = decodeURIComponent(protocolName);
        protocolDeviceType = decodeURIComponent(protocolDeviceType);
        saveSign = decodeURIComponent(saveSign);
        msg = decodeURIComponent(msg);
        if (parseInt(saveSign) > 0) {
            mini.confirm(msg, _loginUserLanguageResource.confirm, function(action) {
                if (action == 'ok') {
                    doSaveSingleUnit(unitName, acqUnit, protocolName, protocolDeviceType);
                }
            });
        } else {
            doSaveSingleUnit(unitName, acqUnit, protocolName, protocolDeviceType);
        }
    }

    function doSaveSingleUnit(unitName, acqUnit, protocolName, protocolDeviceType) {
        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/saveSingelImportedDisplayUnit',
            type: 'POST',
            data: {
                unitName: unitName,
                acqUnit: acqUnit,
                protocolName: protocolName,
                protocolDeviceType: protocolDeviceType
            },
            dataType: 'json',
            success: function(result) {
                mini.unmask(document.body);
                if (result.success) {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    var tree = mini.get('unitTree');
                    if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedDisplayUnitTreeData');
                    if (window.parent && window.parent.refreshDisplayUnitList) {
                        window.parent.refreshDisplayUnitList();
                    }
                } else {
                    mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>');
                }
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    // ================================================================
    // 保存全部
    // ================================================================
    function onSaveAll() {
        var tree = mini.get('unitTree');
        var root = tree.getRootNode();
        var unitNames = [];
        function collect(node) {
            if (node.classes === 1 && node.saveSign !== 2) {
                unitNames.push(node.text);
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                }
            }
        }
        if (root && root.children) {
            for (var i = 0; i < root.children.length; i++) {
                collect(root.children[i]);
            }
        }
        if (unitNames.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }
        mini.confirm(_loginUserLanguageResource.confirmOperation, _loginUserLanguageResource.confirm, function(action) {
            if (action == 'ok') {
                var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
                $.ajax({
                    url: context + '/acquisitionUnitManagerController/saveAllImportedDisplayUnit',
                    type: 'POST',
                    data: {
                        unitName: unitNames.join(',')
                    },
                    dataType: 'json',
                    success: function(result) {
                        mini.unmask(document.body);
                        if (result.success) {
                            mini.alert(_loginUserLanguageResource.savedSuccessfully);
                            var tree = mini.get('unitTree');
                            if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedDisplayUnitTreeData');
                            if (window.parent && window.parent.refreshDisplayUnitList) {
                                window.parent.refreshDisplayUnitList();
                            }
                        } else {
                            mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>');
                        }
                    },
                    error: function() {
                        mini.unmask(document.body);
                        mini.alert(_loginUserLanguageResource.requestFailed);
                    }
                });
            }
        });
    }

    // ================================================================
    // 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>