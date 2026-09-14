<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入报表单元</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar { flex-shrink:0; padding:4px 8px; border-bottom:1px solid #e8e8e8;
                        background:#fafafa; display:flex; align-items:center; flex-wrap:wrap; gap:4px; }
        .hot-wrapper {width:100%; height:100%;  }
        .hot-inner { width:100%; height:100%;  }
        .tab-pad { padding:2px; height:100%; background:#fff; display:flex; flex-direction:column; overflow:hidden; }
        .mini-tabs-body, .mini-tab-body { padding:0 !important; margin:0 !important; overflow:hidden !important; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 工具栏 -->
    <div class="mini-toolbar">
        <span style="font-size:12px;color:#666;" id="uploadLabel"></span>
        <form id="uploadForm"
              action="<%=context%>/acquisitionUnitManagerController/uploadImportedReportUnitFile"
              method="post" enctype="multipart/form-data" target="uploadFrame" style="display:inline;">
            <input id="fileUpload" class="mini-htmlfile" name="file" style="width:300px;"
                   limitType="*.json" onfileselect="onFileSelect" />
            <iframe name="uploadFrame" style="display:none;"></iframe>
        </form>
        <span style="flex:1;"></span>
        <button id="saveAllBtn" class="mini-button" iconCls="save" onclick="onSaveAll()"></button>
    </div>

    <!-- 主体：左右 Splitter -->
    <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
        <!-- 左侧：预导入单元树 -->
        <div size="25%" showCollapseButton="true" collapseDirection="left" minSize="220">
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

        <!-- 右侧：多级 Tabs -->
        <div size="75%" showCollapseButton="false">
            <div style="padding:2px;height:100%;background:#fff;">
                <div id="reportUnitTabs" class="mini-tabs" tabPosition="top" activeIndex="0"
                     style="width:100%;height:100%;" onactivechanged="onMainTabChanged">

                    <!-- ============ 一级 Tab 1：单井报表 ============ -->
                    <div title="" name="singleWell" style="height:100%;">
                        <div id="singleWellSubTabs" class="mini-tabs" tabPosition="top" activeIndex="0"
                             style="width:100%;height:100%;" onactivechanged="onSubTabChanged">

                            <!-- 时报表 -->
                            <div title="" name="hourly" style="height:100%;">
                                <div class="tab-pad">
                                    <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
                                        <div size="50%" showCollapseButton="false">
                                            <div class="mini-panel" id="hourlyTemplatePanel" title=""
                                                 style="width:100%;height:100%;" showCloseButton="false"
                                                 showCollapseButton="false" bodyStyle="padding:0;">
                                                <div class="hot-wrapper">
                                                    <div id="hourlyTemplateContainer" class="hot-inner"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div size="50%" showCollapseButton="true">
                                            <div class="mini-panel" id="hourlyContentPanel" title=""
                                                 style="width:100%;height:100%;" showCloseButton="false"
                                                 showCollapseButton="false" bodyStyle="padding:0;">
                                                <div class="hot-wrapper">
                                                    <div id="hourlyContentContainer" class="hot-inner"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- 日报表 -->
                            <div title="" name="daily" style="height:100%;">
                                <div class="tab-pad">
                                    <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
                                        <div size="45%" showCollapseButton="false">
                                            <div class="mini-panel" id="dailyTemplatePanel" title=""
                                                 style="width:100%;height:100%;" showCloseButton="false"
                                                 showCollapseButton="false" bodyStyle="padding:0;">
                                                <div class="hot-wrapper">
                                                    <div id="dailyTemplateContainer" class="hot-inner"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div size="55%" showCollapseButton="true">
                                            <div class="mini-panel" id="dailyContentPanel" title=""
                                                 style="width:100%;height:100%;" showCloseButton="false"
                                                 showCollapseButton="false" bodyStyle="padding:0;">
                                                <div class="hot-wrapper">
                                                    <div id="dailyContentContainer" class="hot-inner"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>

                    <!-- ============ 一级 Tab 2：区域报表 ============ -->
                    <div title="" name="area" style="height:100%;">
                        <div class="tab-pad">
                            <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
                                <div size="50%" showCollapseButton="false">
                                    <div class="mini-panel" id="areaTemplatePanel" title=""
                                         style="width:100%;height:100%;" showCloseButton="false"
                                         showCollapseButton="false" bodyStyle="padding:0;">
                                        <div class="hot-wrapper">
                                            <div id="areaTemplateContainer" class="hot-inner"></div>
                                        </div>
                                    </div>
                                </div>
                                <div size="50%" showCollapseButton="true">
                                    <div class="mini-panel" id="areaContentPanel" title=""
                                         style="width:100%;height:100%;" showCloseButton="false"
                                         showCollapseButton="false" bodyStyle="padding:0;">
                                        <div class="hot-wrapper">
                                            <div id="areaContentContainer" class="hot-inner"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // ================================================================
    // 上下文
    // ================================================================
    var context = '<%=context%>';
    var deviceTypeId = '';
    var deviceTypeName = '';
    var currentTreeNode = null;
    var isInitializing = true;

    // 6 个 Handsontable 实例
    var hotHourlyTemplate = null, hotHourlyContent = null,
        hotDailyTemplate  = null, hotDailyContent  = null,
        hotAreaTemplate   = null, hotAreaContent   = null;

    // ================================================================
    // 国际化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.importReportUnit;
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.saveAll);

        var tree = mini.get('unitTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);

        // 一级 Tab 标题
        var tabs = mini.get('reportUnitTabs');
        if (tabs) {
            var ts = tabs.getTabs();
            if (ts && ts.length >= 2) {
                tabs.updateTab(ts[0], { title: _loginUserLanguageResource.singleDeviceReport });
                tabs.updateTab(ts[1], { title: _loginUserLanguageResource.areaReport });
            }
        }
        // 二级 Tab 标题
        var swTabs = mini.get('singleWellSubTabs');
        if (swTabs) {
            var ts2 = swTabs.getTabs();
            if (ts2 && ts2.length >= 2) {
                swTabs.updateTab(ts2[0], { title: _loginUserLanguageResource.hourlyReport });
                swTabs.updateTab(ts2[1], { title: _loginUserLanguageResource.dailyReport });
            }
        }
        // Panel 标题
        var hp = mini.get('hourlyTemplatePanel');
        if (hp) hp.setTitle(_loginUserLanguageResource.deviceHourlyReportTemplate);
        var hc = mini.get('hourlyContentPanel');
        if (hc) hc.setTitle(_loginUserLanguageResource.deviceHourlyReportContentConfig);
        var dp = mini.get('dailyTemplatePanel');
        if (dp) dp.setTitle(_loginUserLanguageResource.deviceDailyReportTemplate);
        var dc = mini.get('dailyContentPanel');
        if (dc) dc.setTitle(_loginUserLanguageResource.deviceDailyReportContentConfig);
        var ap = mini.get('areaTemplatePanel');
        if (ap) ap.setTitle(_loginUserLanguageResource.areaDailyReportTemplate);
        var ac = mini.get('areaContentPanel');
        if (ac) ac.setTitle(_loginUserLanguageResource.areaDailyReportContentConfig);
    }

    // ================================================================
    // 父窗口调用
    // ================================================================
    function setData(data) {
        if (data) {
            deviceTypeId = data.deviceTypeId || '';
            deviceTypeName = data.deviceTypeName || '';
        }
    }

    // ================================================================
    // 文件上传
    // ================================================================
    function onFileSelect(e) {
        var form = document.getElementById('uploadForm');
        mini.mask({ el: document.body, html: _loginUserLanguageResource.uploadingFile });
        form.submit();
        var iframe = document.getElementsByName('uploadFrame')[0];
        iframe.onload = function () {
            mini.unmask(document.body);
            try {
                var responseText = iframe.contentWindow.document.body.innerText;
                var result = JSON.parse(responseText);
                if (result && result.flag) {
                    mini.alert(_loginUserLanguageResource.loadSuccessfully);
                    var tree = mini.get('unitTree');
                    if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedReportUnitTreeData');
                } else {
                    mini.alert(_loginUserLanguageResource.uploadDataError);
                }
            } catch (ex) {
                mini.alert(_loginUserLanguageResource.uploadFail);
            }
            iframe.onload = null;
        };
        var fileInput = document.getElementById('fileUpload');
        if (fileInput) fileInput.value = '';
    }

    // ================================================================
    // 树
    // ================================================================
    function onTreeBeforeLoad(e) {
        var params = e.params || {};
        params.deviceType = deviceTypeId;
        e.params = params;
    }

    function onTreeLoad(e) {
        var tree = e.sender;
        tree.setColumns([
            { field: 'text', name: 'taskname', header: _loginUserLanguageResource.importUnit,
              headerAlign: 'left', align: 'left', width: '40%' },
            { field: 'msg', header: _loginUserLanguageResource.collisionInfo,
              headerAlign: 'center', align: 'center', width: '40%' },
            { field: 'action', header: _loginUserLanguageResource.save,
              headerAlign: 'center', align: 'center', width: '20%' }
        ]);

        // 默认选中第一个报表单元（classes === 1）
        var root = tree.getRootNode();
        if (root && root.children) {
            var target = null;
            (function find(nodes) {
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    if (node.classes === 1) { target = node; return; }
                    if (node.children && node.children.length > 0) {
                        find(node.children);
                        if (target) return;
                    }
                }
            })(root.children);
            if (target) {
                setTimeout(function () { tree.selectNode(target); }, 50);
            }
        }
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
        if (!node) return;
        currentTreeNode = node;

        if (node.classes === 0) {
            if (node.children && node.children.length > 0) {
                loadConfigDataByNode(node.children[0]);
            } else {
                clearAllHandsontable();
            }
        } else if (node.classes === 1) {
            loadConfigDataByNode(node);
        } else {
            clearAllHandsontable();
        }
    }

    // ================================================================
    // 根据当前激活的 Tab 决定 reportType
    //   单井-时报表：2
    //   单井-日报表：0
    //   区域-日报表：1
    // ================================================================
    function getActiveReportType() {
        var tabs = mini.get('reportUnitTabs');
        if (!tabs) return 0;
        var activeMain = tabs.getActiveTab();
        if (!activeMain) return 0;

        if (activeMain.name === 'singleWell') {
            var swTabs = mini.get('singleWellSubTabs');
            if (!swTabs) return 0;
            var activeSub = swTabs.getActiveTab();
            if (!activeSub) return 0;
            return (activeSub.name === 'hourly') ? 2 : 0;
        } else if (activeMain.name === 'area') {
            return 1;
        }
        return 0;
    }

    function loadConfigDataByNode(node) {
        var reportType = getActiveReportType();
        loadByReportType(node.text, reportType);
    }

    // ================================================================
    // Tab 切换事件
    // ================================================================
    function onMainTabChanged(e) {
        if (isInitializing) return;
        if (!currentTreeNode) return;
        if (currentTreeNode.classes !== 1) return;
        setTimeout(function () { loadConfigDataByNode(currentTreeNode); }, 30);
    }
    function onSubTabChanged(e) {
        if (isInitializing) return;
        if (!currentTreeNode) return;
        if (currentTreeNode.classes !== 1) return;
        setTimeout(function () { loadConfigDataByNode(currentTreeNode); }, 30);
    }

    // ================================================================
    // 根据 reportType 加载模板 + 内容
    // ================================================================
    function loadByReportType(unitName, reportType) {
        if (reportType === 2) {
            loadTemplate('hourlyTemplateContainer', unitName, 2, function (hot) { hotHourlyTemplate = hot; });
            loadContent('hourlyContentContainer', unitName, 2, function (hot) { hotHourlyContent = hot; });
        } else if (reportType === 0) {
            loadTemplate('dailyTemplateContainer', unitName, 0, function (hot) { hotDailyTemplate = hot; });
            loadContent('dailyContentContainer', unitName, 0, function (hot) { hotDailyContent = hot; });
        } else if (reportType === 1) {
            loadTemplate('areaTemplateContainer', unitName, 1, function (hot) { hotAreaTemplate = hot; });
            loadContent('areaContentContainer', unitName, 1, function (hot) { hotAreaContent = hot; });
        }
    }

    // ================================================================
    // 加载模板表
    // ================================================================
    function loadTemplate(containerId, unitName, reportType, onCreated) {
        var container = document.getElementById(containerId);
        if (!container) return;

        $.ajax({
            url: context + '/acquisitionUnitManagerController/getImportReportUnitTemplateData',
            type: 'POST',
            data: { reportType: reportType, unitName: unitName },
            dataType: 'json',
            success: function (templateData) {
                if (!templateData) return;
                var colWidths = getTemplateColWidths(templateData);

                // 组装显示数据：每行一个单元格
                var data = [];
                for (var i = 0; i < templateData.header.length; i++) {
                    var h = templateData.header[i];
                    var title = '';
                    if (_loginUserLanguage === 'zh_CN') title = h.title_zh_CN;
                    else if (_loginUserLanguage === 'en') title = h.title_en;
                    else if (_loginUserLanguage === 'ru') title = h.title_ru;
                    data.push(title);
                }

                // 销毁旧实例
                destroyHotByContainer(containerId);

                container.innerHTML = '';
                var hot = new Handsontable(container, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    fixedRowsTop: templateData.fixedRowsTop,
                    fixedRowsBottom: templateData.fixedRowsBottom,
                    rowHeaders: false,
                    colHeaders: false,
                    rowHeights: templateData.rowHeights,
                    colWidths: colWidths,
                    stretchH: 'all',
                    columnSorting: true,
                    allowInsertRow: false,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    mergeCells: templateData.mergeCells,
                    cells: function (row, col, prop) {
                        var cp = {};
                        cp.editor = false;
                        cp.renderer = function (instance, td, row, col, prop, value) {
                            Handsontable.renderers.TextRenderer.apply(this, arguments);
                            var idx = instance.toVisualRow(row);
                            var h = templateData.header[idx];
                            if (h && h.tdStyle) {
                                var s = h.tdStyle;
                                if (s.fontWeight)      td.style.fontWeight = s.fontWeight;
                                if (s.fontSize)        td.style.fontSize = s.fontSize;
                                if (s.height)          td.style.height = s.height;
                                if (s.color)           td.style.color = s.color;
                                if (s.backgroundColor) td.style.backgroundColor = s.backgroundColor;
                                if (s.textAlign)       td.style.textAlign = s.textAlign;
                            }
                            td.style.whiteSpace = 'nowrap';
                            td.style.overflow = 'hidden';
                            td.style.textOverflow = 'ellipsis';
                        };
                        return cp;
                    }
                });
                if (typeof onCreated === 'function') onCreated(hot);
            }
        });
    }

    function getTemplateColWidths(templateData) {
        if (_loginUserLanguage === 'zh_CN') return templateData.columnWidths_zh_CN;
        if (_loginUserLanguage === 'en')    return templateData.columnWidths_en;
        if (_loginUserLanguage === 'ru')    return templateData.columnWidths_ru;
        return templateData.columnWidths_zh_CN;
    }

    // ================================================================
    // 加载内容表
    // ================================================================
    function loadContent(containerId, unitName, reportType, onCreated) {
        var container = document.getElementById(containerId);
        if (!container) return;

        $.ajax({
            url: context + '/acquisitionUnitManagerController/getImportReportUnitItemsConfigData',
            type: 'POST',
            data: { reportType: reportType, unitName: unitName },
            dataType: 'json',
            success: function (result) {
                var data = result.totalRoot || [];
                if (data.length === 0) {
                    for (var i = 0; i < 20; i++) data.push({});
                }

                // 销毁旧实例
                destroyHotByContainer(containerId);
                container.innerHTML = '';

                var cfg = getContentTableConfig(reportType);

                var hot = new Handsontable(container, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: { columns: cfg.hiddenColumns, indicators: false, copyPasteEnabled: false },
                    colWidths: cfg.colWidths,
                    columns: cfg.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: false,
                    colHeaders: cfg.colHeaders,
                    columnSorting: true,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    cells: function (row, col, prop) {
                        var cp = {};
                        cp.editor = false;
                        var visualCol = this.instance.toVisualColumn(col);
                        if (prop === 'reportCurveConfShowValue') {
                            cp.renderer = function (instance, td, row, col, prop, value) {
                                Handsontable.renderers.TextRenderer.apply(this, arguments);
                                if (value) {
                                    var arr = (value + '').split(';');
                                    if (arr.length === 3) td.style.backgroundColor = '#' + arr[2];
                                }
                                td.style.whiteSpace = 'nowrap';
                                td.style.overflow = 'hidden';
                                td.style.textOverflow = 'ellipsis';
                            };
                        } else if (cfg.columns[visualCol]
                                   && cfg.columns[visualCol].type !== 'dropdown'
                                   && cfg.columns[visualCol].type !== 'checkbox') {
                            cp.renderer = function (instance, td, row, col, prop, value) {
                                Handsontable.renderers.TextRenderer.apply(this, arguments);
                                td.style.whiteSpace = 'nowrap';
                                td.style.overflow = 'hidden';
                                td.style.textOverflow = 'ellipsis';
                            };
                        }
                        return cp;
                    }
                });
                if (typeof onCreated === 'function') onCreated(hot);
            }
        });
    }

    // ================================================================
    // 内容表列配置（按 reportType 区分）
    // ================================================================
    function getContentTableConfig(reportType) {
        if (reportType === 1) {
            // 区域报表
            return {
                colHeaders: [_loginUserLanguageResource.idx, _loginUserLanguageResource.name,
                    _loginUserLanguageResource.unit, _loginUserLanguageResource.dataSource,
                    _loginUserLanguageResource.totalType, _loginUserLanguageResource.showLevel,
                    _loginUserLanguageResource.dataSort, _loginUserLanguageResource.prec,
                    _loginUserLanguageResource.sumSign, _loginUserLanguageResource.averageSign,
                    _loginUserLanguageResource.reportCurve, _loginUserLanguageResource.curveStatType,
                    '', '', ''],
                columns: [
                    { data: 'id' },
                    { data: 'title' },
                    { data: 'unit' },
                    { data: 'dataSource' },
                    { data: 'totalType' },
                    { data: 'showLevel' },
                    { data: 'sort' },
                    { data: 'prec' },
                    { data: 'sumSign', type: 'checkbox' },
                    { data: 'averageSign', type: 'checkbox' },
                    { data: 'reportCurveConfShowValue' },
                    { data: 'curveStatType', type: 'dropdown', strict: true, allowInvalid: false,
                      source: [_loginUserLanguageResource.curveStatType_sum,
                               _loginUserLanguageResource.curveStatType_avg] },
                    { data: 'reportCurveConf' },
                    { data: 'code' },
                    { data: 'dataType' }
                ],
                hiddenColumns: [12, 13, 14],
                colWidths: [40, 140, 80, 80, 80, 60, 60, 60, 50, 60, 100, 100]
            };
        }
        // 单井报表（0 / 2）
        return {
            colHeaders: [_loginUserLanguageResource.idx, _loginUserLanguageResource.name,
                _loginUserLanguageResource.unit, _loginUserLanguageResource.dataSource,
                _loginUserLanguageResource.totalType, _loginUserLanguageResource.showLevel,
                _loginUserLanguageResource.dataSort, _loginUserLanguageResource.prec,
                _loginUserLanguageResource.reportCurve, '', '', ''],
            columns: [
                { data: 'id' },
                { data: 'title' },
                { data: 'unit' },
                { data: 'dataSource' },
                { data: 'totalType' },
                { data: 'showLevel' },
                { data: 'sort' },
                { data: 'prec' },
                { data: 'reportCurveConfShowValue' },
                { data: 'reportCurveConf' },
                { data: 'code' },
                { data: 'dataType' }
            ],
            hiddenColumns: [9, 10, 11],
            colWidths: [40, 140, 80, 80, 80, 60, 60, 60, 100, 80]
        };
    }

    // ================================================================
    // 辅助：销毁指定容器上的 Handsontable
    // ================================================================
    function destroyHotByContainer(containerId) {
        var refs = {
            'hourlyTemplateContainer': function () { if (hotHourlyTemplate) try { hotHourlyTemplate.destroy(); } catch(e){} hotHourlyTemplate = null; },
            'hourlyContentContainer':  function () { if (hotHourlyContent)  try { hotHourlyContent.destroy();  } catch(e){} hotHourlyContent  = null; },
            'dailyTemplateContainer':  function () { if (hotDailyTemplate)  try { hotDailyTemplate.destroy();  } catch(e){} hotDailyTemplate  = null; },
            'dailyContentContainer':   function () { if (hotDailyContent)   try { hotDailyContent.destroy();   } catch(e){} hotDailyContent   = null; },
            'areaTemplateContainer':   function () { if (hotAreaTemplate)   try { hotAreaTemplate.destroy();   } catch(e){} hotAreaTemplate   = null; },
            'areaContentContainer':    function () { if (hotAreaContent)    try { hotAreaContent.destroy();    } catch(e){} hotAreaContent    = null; }
        };
        if (refs[containerId]) refs[containerId]();
    }

    function clearAllHandsontable() {
        destroyHotByContainer('hourlyTemplateContainer');
        destroyHotByContainer('hourlyContentContainer');
        destroyHotByContainer('dailyTemplateContainer');
        destroyHotByContainer('dailyContentContainer');
        destroyHotByContainer('areaTemplateContainer');
        destroyHotByContainer('areaContentContainer');

        var ids = ['hourlyTemplateContainer', 'hourlyContentContainer',
                   'dailyTemplateContainer', 'dailyContentContainer',
                   'areaTemplateContainer', 'areaContentContainer'];
        for (var i = 0; i < ids.length; i++) {
            var el = document.getElementById(ids[i]);
            if (el) el.innerHTML = '';
        }
    }

    // ================================================================
    // 冲突信息渲染器
    // ================================================================
    function onMsgRenderer(e) {
        var record = e.record;
        var value = record.msg || '';
        var saveSign = record.saveSign;
        var color = (saveSign == 0) ? '#000000' : '#DC2828';
        e.cellStyle = 'color:' + color + ';';
        e.cellHtml = value ? '<span title="' + value + '">' + value + '</span>' : '';
    }

    // 操作列渲染器（单个保存）
    function onActionRenderer(e) {
        var record = e.record;
        if (record.classes === 1 && record.saveSign != 2) {
            var unitName = encodeURIComponent(record.text || '');
            var saveSign = encodeURIComponent(record.saveSign || '');
            var msg = encodeURIComponent(record.msg || '');
            e.cellHtml = '<a href="javascript:void(0)" onclick="saveSingleUnit(\''
                + unitName + '\',\'' + saveSign + '\',\'' + msg + '\')" style="text-decoration:none;">'
                + (_loginUserLanguageResource.save) + '</a>';
        } else {
            e.cellHtml = '';
        }
    }

    // ================================================================
    // 单个保存
    // ================================================================
    function saveSingleUnit(unitName, saveSign, msg) {
        unitName = decodeURIComponent(unitName);
        saveSign = decodeURIComponent(saveSign);
        msg = decodeURIComponent(msg);

        if (parseInt(saveSign) > 0) {
            mini.confirm(msg, _loginUserLanguageResource.confirm, function (action) {
                if (action === 'ok') doSaveSingleUnit(unitName);
            });
        } else {
            doSaveSingleUnit(unitName);
        }
    }

    function doSaveSingleUnit(unitName) {
        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/saveSingelImportedReportUnit',
            type: 'POST',
            data: { unitName: unitName },
            dataType: 'json',
            success: function (result) {
                mini.unmask(document.body);
                if (result.success) {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    var tree = mini.get('unitTree');
                    if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedReportUnitTreeData');
                    if (window.parent && window.parent.refreshReportUnitList) {
                        window.parent.refreshReportUnitList();
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
    // 全部保存
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
        mini.confirm(_loginUserLanguageResource.confirmOperation, _loginUserLanguageResource.confirm, function (action) {
            if (action === 'ok') {
                var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
                $.ajax({
                    url: context + '/acquisitionUnitManagerController/saveAllImportedReportUnit',
                    type: 'POST',
                    data: { unitName: unitNames.join(',') },
                    dataType: 'json',
                    success: function (result) {
                        mini.unmask(document.body);
                        if (result.success) {
                            mini.alert(_loginUserLanguageResource.savedSuccessfully);
                            var tree = mini.get('unitTree');
                            if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedReportUnitTreeData');
                            if (window.parent && window.parent.refreshReportUnitList) {
                                window.parent.refreshReportUnitList();
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
        });
    }

    // ================================================================
    // 页面初始化
    // ================================================================
    $(document).ready(function () {
        mini.parse();
        initI18n();
        setTimeout(function () { isInitializing = false; }, 300);
    });
</script>
</body>
</html>