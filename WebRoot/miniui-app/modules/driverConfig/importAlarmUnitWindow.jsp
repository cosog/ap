<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入报警单元</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        .main-container { width:100%; height:100%; display:flex; flex-direction:column; }
        .mini-toolbar { flex-shrink:0; padding:4px 8px; border-bottom:1px solid #e8e8e8; background:#fafafa; display:flex; align-items:center; flex-wrap:wrap; gap:4px; }
        .mini-treegrid { width:100%; height:100%; }
        /* 表格外层 flex 容器 */
        .hot-wrapper {
            flex: 1;
            min-height: 0;
            overflow: hidden;
            padding: 4px;
            box-sizing: border-box;
        }
        /* 内层 100%×100% div，Handsontable 在这里创建 */
        .hot-inner {
            width: 100%;
            height: 100%;
            overflow: hidden;
            position: relative;
        }
        .tab-pad { padding:4px;height:100%;background:#fff;display:flex;flex-direction:column;overflow:hidden; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- 顶部工具栏 -->
    <div class="mini-toolbar">
        <span style="font-size:12px;color:#666;" id="uploadLabel"></span>
        <form id="uploadForm"
              action="<%=context%>/acquisitionUnitManagerController/uploadImportedAlarmUnitFile"
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

        <!-- 右侧：报警类型 Tabs（顺序与报警单元配置一致） -->
        <div size="70%" showCollapseButton="false">
            <div style="padding:4px;height:100%;background:#fff;">
                <div id="alarmConfigSubTabs" class="mini-tabs"
                     style="width:100%;height:100%;" tabPosition="top"
                     onactivechanged="onAlarmConfigTabChanged">

                    <!-- ① 功图工况 FESDiagram -->
                    <div title="" name="fes" style="height:100%;">
                        <div class="tab-pad">
                            <div class="hot-wrapper"><div id="importAlarmFESTableDiv_id" class="hot-inner"></div></div>
                        </div>
                    </div>

                    <!-- ② 通信状态 CommStatus -->
                    <div title="" name="comm" style="height:100%;">
                        <div class="tab-pad">
                            <div class="hot-wrapper"><div id="importAlarmCommTableDiv_id" class="hot-inner"></div></div>
                        </div>
                    </div>

                    <!-- ③ 运行状态 RunStatus -->
                    <div title="" name="run" style="height:100%;">
                        <div class="tab-pad">
                            <div class="hot-wrapper"><div id="importAlarmRunTableDiv_id" class="hot-inner"></div></div>
                        </div>
                    </div>

                    <!-- ④ 数值量 Numeric -->
                    <div title="" name="numeric" style="height:100%;">
                        <div class="tab-pad">
                            <div class="hot-wrapper"><div id="importAlarmNumericTableDiv_id" class="hot-inner"></div></div>
                        </div>
                    </div>

                    <!-- ⑤ 枚举量 Enum -->
                    <div title="" name="enum" style="height:100%;">
                        <div class="tab-pad">
                            <div class="hot-wrapper"><div id="importAlarmEnumTableDiv_id" class="hot-inner"></div></div>
                        </div>
                    </div>

                    <!-- ⑥ 开关量 Switching -->
                    <div title="" name="switching" style="height:100%;">
                        <div class="tab-pad">
                            <div class="hot-wrapper"><div id="importAlarmSwitchTableDiv_id" class="hot-inner"></div></div>
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
    var currentTreeNode = null;
    
    var isInitializing = true;

    // 各表格的 Helper 实例
    var _hotFES = null, _hotComm = null, _hotRun = null,
        _hotNumeric = null, _hotEnum = null, _hotSwitch = null;

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        document.title = _loginUserLanguageResource.importAlarmUnit;
        var uploadLabel = document.getElementById('uploadLabel');
        if (uploadLabel) uploadLabel.textContent = _loginUserLanguageResource.uploadFile;
        var saveAll = mini.get('saveAllBtn');
        if (saveAll) saveAll.setText(_loginUserLanguageResource.saveAll);

        var tree = mini.get('unitTree');
        if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);

        // 设置 Tabs 标题（顺序与报警单元配置一致）
        var tabs = mini.get('alarmConfigSubTabs');
        if (tabs) {
            var ts = tabs.getTabs();
            if (ts && ts.length >= 6) {
                tabs.updateTab(ts[0], { title: _loginUserLanguageResource.FESDiagramResultAlarm });
                tabs.updateTab(ts[1], { title: _loginUserLanguageResource.commStatus });
                tabs.updateTab(ts[2], { title: _loginUserLanguageResource.runStatus });
                tabs.updateTab(ts[3], { title: _loginUserLanguageResource.numericValue });
                tabs.updateTab(ts[4], { title: _loginUserLanguageResource.enumValue });
                tabs.updateTab(ts[5], { title: _loginUserLanguageResource.switchingValue });

                // 根据 _onlyMonitor 隐藏功图工况标签
                var onlyMonitor = (typeof _onlyMonitor !== 'undefined') ? _onlyMonitor : false;
                if (onlyMonitor) {
                    tabs.updateTab(ts[0], { visible: false });
                    tabs.activeTab(ts[1]);
                } else {
                    tabs.activeTab(ts[0]);
                }
            }
        }
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
        iframe.onload = function() {
            mini.unmask(document.body);
            try {
                var responseText = iframe.contentWindow.document.body.innerText;
                var result = JSON.parse(responseText);
                if (result && result.flag) {
                    mini.alert(_loginUserLanguageResource.loadSuccessfully);
                    var tree = mini.get('unitTree');
                    if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedAlarmUnitTreeData');
                } else {
                    mini.alert(_loginUserLanguageResource.uploadDataError);
                }
            } catch(ex) {
                mini.alert(_loginUserLanguageResource.uploadFail);
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
        var columns = [
            { field: 'text', name: 'taskname', header: _loginUserLanguageResource.importUnit,
              headerAlign: 'left', align: 'left', width: '40%' },
            { field: 'msg', header: _loginUserLanguageResource.collisionInfo,
              headerAlign: 'center', align: 'center', width: '40%' },
            { field: 'action', header: _loginUserLanguageResource.save,
              headerAlign: 'center', align: 'center', width: '20%' }
        ];
        tree.setColumns(columns);

        // 默认选中第一个可保存的报警单元节点（classes==1）
        var root = tree.getRootNode();
        if (root && root.children) {
            var targetNode = null;
            function findNode(nodes) {
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    if (node.classes === 1) { targetNode = node; return; }
                    if (node.children && node.children.length > 0) {
                        findNode(node.children);
                        if (targetNode) return;
                    }
                }
            }
            findNode(root.children);
            if (targetNode) {
                setTimeout(function() { tree.selectNode(targetNode); }, 50);
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

    /**
     * 根据节点加载当前激活 Tab 对应的配置
     */
    function loadConfigDataByNode(node) {
        var tabs = mini.get('alarmConfigSubTabs');
        if (!tabs) return;
        var active = tabs.getActiveTab();
        if (!active) return;
        var tabName = active.name;
        loadAlarmConfigByType(tabName, node);
    }

    /**
     * Tab 切换事件：根据当前节点和激活 Tab 重新加载
     */
    function onAlarmConfigTabChanged(e) {
    	if (isInitializing) return;
        if (!currentTreeNode) return;
        if (currentTreeNode.classes !== 1) return;
        var tabs = e.sender;
        var active = tabs.getActiveTab();
        if (!active) return;
        loadAlarmConfigByType(active.name, currentTreeNode);
    }

    /**
     * 根据报警类型加载数据
     * @param {string} type fes/comm/run/numeric/enum/switching
     * @param {object} node 树节点
     */
    function loadAlarmConfigByType(type, node) {
        if (!node) return;
        var protocolName = node.protocol || '';
        var protocolDeviceType = node.protocolDeviceType || '';
        var unitName = node.text || '';
        var calculateType = node.calculateType || 0;

        if (type === 'fes') {
            loadFESConfig(protocolName, protocolDeviceType, unitName, calculateType);
        } else if (type === 'comm') {
            loadCommConfig(protocolName, protocolDeviceType, unitName, calculateType);
        } else if (type === 'run') {
            loadRunConfig(protocolName, protocolDeviceType, unitName, calculateType);
        } else if (type === 'numeric') {
            loadNumericConfig(protocolName, protocolDeviceType, unitName, calculateType);
        } else if (type === 'enum') {
            loadEnumConfig(protocolName, protocolDeviceType, unitName, calculateType);
        } else if (type === 'switching') {
            loadSwitchConfig(protocolName, protocolDeviceType, unitName, calculateType);
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
            var protocolName = encodeURIComponent(record.protocol || '');
            var protocolDeviceType = encodeURIComponent(record.protocolDeviceType || '');
            var saveSign = encodeURIComponent(record.saveSign || '');
            var msg = encodeURIComponent(record.msg || '');
            e.cellHtml = '<a href="javascript:void(0)" onclick="saveSingleUnit(\'' + unitName + '\',\'' + protocolName + '\',\'' + protocolDeviceType + '\',\'' + saveSign + '\',\'' + msg + '\')" style="text-decoration:none;">' + (_loginUserLanguageResource.save || '保存') + '</a>';
        } else {
            e.cellHtml = '';
        }
    }

    // ================================================================
    // 通用加载函数：请求后端 + 创建只读 Handsontable
    // ================================================================
    function loadAlarmConfigData(alarmType, protocolName, protocolDeviceType, unitName, calculateType, containerId, setHelper, colHeaders, columns, colWidths) {
        // 销毁旧实例
        var helper = (typeof setHelper === 'function') ? setHelper() : null;
        if (helper && helper.hot) { helper.hot.destroy(); helper = null; }
        var container = document.getElementById(containerId);
        if (!container) return;
        container.innerHTML = '';

        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.loadingData });
        $.ajax({
            type: 'POST',
            url: context + '/acquisitionUnitManagerController/getImportAlarmUnitItemsData',
            data: {
                protocolName: protocolName,
                protocolDeviceType: protocolDeviceType,
                unitName: unitName,
                alarmType: alarmType,
                calculateType: calculateType
            },
            dataType: 'json',
            success: function(result) {
                mini.unmask(document.body);
                var data = result.totalRoot || [];
                if (data.length === 0) {
                    for (var i = 0; i < 30; i++) data.push({});
                }
                var hot = new Handsontable(container, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    colWidths: colWidths,
                    columns: columns,
                    width: '100%',
                    height: '100%',
                    stretchH: 'all',
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
                        var visualColIndex = this.instance.toVisualColumn(col);
                        cellProperties.editor = false;
                        if (columns[visualColIndex] && columns[visualColIndex].type !== 'dropdown'
                            && columns[visualColIndex].type !== 'checkbox') {
                            cellProperties.renderer = function(instance, td, row, col, prop, value, cellProperties) {
                                Handsontable.renderers.TextRenderer.apply(this, arguments);
                                td.style.whiteSpace = 'nowrap';
                                td.style.overflow = 'hidden';
                                td.style.textOverflow = 'ellipsis';
                            };
                        }
                        return cellProperties;
                    }
                });
                // 保存实例
                if (typeof setHelper === 'function') {
                    setHelper(hot);
                }
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    }

    // ================================================================
    // 6 个报警类型配置的加载
    // ================================================================

    // ① 功图工况 FES
    function loadFESConfig(protocolName, protocolDeviceType, unitName, calculateType) {
        var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
            _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
            _loginUserLanguageResource.alarmLevel, _loginUserLanguageResource.isSendMessage,
            _loginUserLanguageResource.isSendEmail, _loginUserLanguageResource.code];
        var columns = [
            { data: 'checked', type: 'checkbox' },
            { data: 'id' },
            { data: 'title' },
            { data: 'delay' },
            { data: 'retriggerTime' },
            { data: 'alarmLevel' },
            { data: 'isSendMessage' },
            { data: 'isSendMail' },
            { data: 'code' }
        ];
        var colWidths = [40, 50, 80, 100, 100, 120, 120, 120, 120];
        loadAlarmConfigData(4, protocolName, protocolDeviceType, unitName, calculateType,
            'importAlarmFESTableDiv_id', setFESHelper, colHeaders, columns, colWidths);
    }

    // ② 通信状态 CommStatus
    function loadCommConfig(protocolName, protocolDeviceType, unitName, calculateType) {
        var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
            _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
            _loginUserLanguageResource.alarmLevel, _loginUserLanguageResource.isSendMessage,
            _loginUserLanguageResource.isSendEmail];
        var columns = [
            { data: 'checked', type: 'checkbox' },
            { data: 'id' },
            { data: 'title' },
            { data: 'delay' },
            { data: 'retriggerTime' },
            { data: 'alarmLevel' },
            { data: 'isSendMessage' },
            { data: 'isSendMail' }
        ];
        var colWidths = [40, 50, 80, 100, 100, 120, 120, 120];
        loadAlarmConfigData(3, protocolName, protocolDeviceType, unitName, calculateType,
            'importAlarmCommTableDiv_id', setCommHelper, colHeaders, columns, colWidths);
    }

    // ③ 运行状态 RunStatus
    function loadRunConfig(protocolName, protocolDeviceType, unitName, calculateType) {
        var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
            _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
            _loginUserLanguageResource.alarmLevel, _loginUserLanguageResource.isSendMessage,
            _loginUserLanguageResource.isSendEmail];
        var columns = [
            { data: 'checked', type: 'checkbox' },
            { data: 'id' },
            { data: 'title' },
            { data: 'delay' },
            { data: 'retriggerTime' },
            { data: 'alarmLevel' },
            { data: 'isSendMessage' },
            { data: 'isSendMail' }
        ];
        var colWidths = [40, 50, 80, 100, 100, 120, 120, 120];
        loadAlarmConfigData(6, protocolName, protocolDeviceType, unitName, calculateType,
            'importAlarmRunTableDiv_id', setRunHelper, colHeaders, columns, colWidths);
    }

    // ④ 数值量 Numeric
    function loadNumericConfig(protocolName, protocolDeviceType, unitName, calculateType) {
        var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
            _loginUserLanguageResource.unit, _loginUserLanguageResource.dataSource,
            _loginUserLanguageResource.upperLimit, _loginUserLanguageResource.lowerLimit,
            _loginUserLanguageResource.hystersis, _loginUserLanguageResource.delay + '(s)',
            _loginUserLanguageResource.retriggerTime + '(s)',
            _loginUserLanguageResource.alarmLevel, _loginUserLanguageResource.isSendMessage,
            _loginUserLanguageResource.isSendEmail];
        var columns = [
            { data: 'checked', type: 'checkbox' },
            { data: 'id' },
            { data: 'title' },
            { data: 'unit' },
            { data: 'dataSource' },
            { data: 'upperLimit' },
            { data: 'lowerLimit' },
            { data: 'hystersis' },
            { data: 'delay' },
            { data: 'retriggerTime' },
            { data: 'alarmLevel' },
            { data: 'isSendMessage' },
            { data: 'isSendMail' }
        ];
        var colWidths = [40, 50, 120, 80, 80, 80, 80, 80, 100, 100, 120, 120, 120];
        loadAlarmConfigData(2, protocolName, protocolDeviceType, unitName, calculateType,
            'importAlarmNumericTableDiv_id', setNumericHelper, colHeaders, columns, colWidths);
    }

    // ⑤ 枚举量 Enum
    function loadEnumConfig(protocolName, protocolDeviceType, unitName, calculateType) {
        var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.value,
            _loginUserLanguageResource.meaning, _loginUserLanguageResource.delay + '(s)',
            _loginUserLanguageResource.retriggerTime + '(s)',
            _loginUserLanguageResource.alarmLevel, _loginUserLanguageResource.isSendMessage,
            _loginUserLanguageResource.isSendEmail];
        var columns = [
            { data: 'checked', type: 'checkbox' },
            { data: 'id' },
            { data: 'value' },
            { data: 'meaning' },
            { data: 'delay' },
            { data: 'retriggerTime' },
            { data: 'alarmLevel' },
            { data: 'isSendMessage' },
            { data: 'isSendMail' }
        ];
        var colWidths = [40, 50, 50, 120, 100, 100, 120, 120, 120];
        loadAlarmConfigData(1, protocolName, protocolDeviceType, unitName, calculateType,
            'importAlarmEnumTableDiv_id', setEnumHelper, colHeaders, columns, colWidths);
    }

    // ⑥ 开关量 Switching
    function loadSwitchConfig(protocolName, protocolDeviceType, unitName, calculateType) {
        var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.bit,
            _loginUserLanguageResource.meaning, _loginUserLanguageResource.switchItemAlarmValue,
            _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
            _loginUserLanguageResource.alarmLevel, _loginUserLanguageResource.isSendMessage,
            _loginUserLanguageResource.isSendEmail];
        var columns = [
            { data: 'checked', type: 'checkbox' },
            { data: 'id' },
            { data: 'bitIndex' },
            { data: 'meaning' },
            { data: 'value' },
            { data: 'delay' },
            { data: 'retriggerTime' },
            { data: 'alarmLevel' },
            { data: 'isSendMessage' },
            { data: 'isSendMail' }
        ];
        var colWidths = [40, 50, 50, 120, 150, 100, 100, 120, 120, 120];
        loadAlarmConfigData(0, protocolName, protocolDeviceType, unitName, calculateType,
            'importAlarmSwitchTableDiv_id', setSwitchHelper, colHeaders, columns, colWidths);
    }

    // ---- 各 helper 的 setter/getter ----
    function setFESHelper(hot) { if (hot !== undefined) { _hotFES = hot; } return _hotFES; }
    function setCommHelper(hot) { if (hot !== undefined) { _hotComm = hot; } return _hotComm; }
    function setRunHelper(hot) { if (hot !== undefined) { _hotRun = hot; } return _hotRun; }
    function setNumericHelper(hot) { if (hot !== undefined) { _hotNumeric = hot; } return _hotNumeric; }
    function setEnumHelper(hot) { if (hot !== undefined) { _hotEnum = hot; } return _hotEnum; }
    function setSwitchHelper(hot) { if (hot !== undefined) { _hotSwitch = hot; } return _hotSwitch; }

    // ================================================================
    // 清空全部 Handsontable
    // ================================================================
    function clearAllHandsontable() {
        var ids = ['importAlarmFESTableDiv_id', 'importAlarmCommTableDiv_id',
            'importAlarmRunTableDiv_id', 'importAlarmNumericTableDiv_id',
            'importAlarmEnumTableDiv_id', 'importAlarmSwitchTableDiv_id'];
        for (var i = 0; i < ids.length; i++) {
            var el = document.getElementById(ids[i]);
            if (el) el.innerHTML = '';
        }
        // 销毁 Handsontable
        if (_hotFES && _hotFES.destroy) try { _hotFES.destroy(); } catch(e) {} _hotFES = null;
        if (_hotComm && _hotComm.destroy) try { _hotComm.destroy(); } catch(e) {} _hotComm = null;
        if (_hotRun && _hotRun.destroy) try { _hotRun.destroy(); } catch(e) {} _hotRun = null;
        if (_hotNumeric && _hotNumeric.destroy) try { _hotNumeric.destroy(); } catch(e) {} _hotNumeric = null;
        if (_hotEnum && _hotEnum.destroy) try { _hotEnum.destroy(); } catch(e) {} _hotEnum = null;
        if (_hotSwitch && _hotSwitch.destroy) try { _hotSwitch.destroy(); } catch(e) {} _hotSwitch = null;
    }

    // ================================================================
    // 单个保存
    // ================================================================
    function saveSingleUnit(unitName, protocolName, protocolDeviceType, saveSign, msg) {
        unitName = decodeURIComponent(unitName);
        protocolName = decodeURIComponent(protocolName);
        protocolDeviceType = decodeURIComponent(protocolDeviceType);
        saveSign = decodeURIComponent(saveSign);
        msg = decodeURIComponent(msg);
        if (parseInt(saveSign) > 0) {
            mini.confirm(msg, _loginUserLanguageResource.confirm, function(action) {
                if (action == 'ok') {
                    doSaveSingleUnit(unitName, protocolName, protocolDeviceType);
                }
            });
        } else {
            doSaveSingleUnit(unitName, protocolName, protocolDeviceType);
        }
    }
    
    function doSaveSingleUnit(unitName, protocolName, protocolDeviceType) {
        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/saveSingelImportedAlarmUnit',
            type: 'POST',
            data: {
                unitName: unitName,
                protocolName: protocolName,
                protocolDeviceType: protocolDeviceType
            },
            dataType: 'json',
            success: function(result) {
                mini.unmask(document.body);
                if (result.success) {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                    var tree = mini.get('unitTree');
                    if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedAlarmUnitTreeData');
                    if (window.parent && window.parent.refreshAlarmUnitList) {
                        window.parent.refreshAlarmUnitList();
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
        mini.confirm(_loginUserLanguageResource.confirmOperation, _loginUserLanguageResource.confirm, function(action) {
            if (action == 'ok') {
                var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.savingData });
                $.ajax({
                    url: context + '/acquisitionUnitManagerController/saveAllImportedAlarmUnit',
                    type: 'POST',
                    data: { unitName: unitNames.join(',') },
                    dataType: 'json',
                    success: function(result) {
                        mini.unmask(document.body);
                        if (result.success) {
                            mini.alert(_loginUserLanguageResource.savedSuccessfully);
                            var tree = mini.get('unitTree');
                            if (tree) tree.load(context + '/acquisitionUnitManagerController/getUploadedAlarmUnitTreeData');
                            if (window.parent && window.parent.refreshAlarmUnitList) {
                                window.parent.refreshAlarmUnitList();
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
        
        setTimeout(function() { isInitializing = false; }, 500);
    });
</script>
</body>
</html>