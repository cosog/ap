//显示单元
var protocolDisplayUnitAcqItemsConfigHandsontableHelper = null;
var protocolDisplayUnitCtrlItemsConfigHandsontableHelper = null;
var protocolDisplayUnitPropertiesHandsontableHelper = null;

//显示单元状态变量
var _currentDisplayProtocolNode = null;
var _currentDisplayUnitNode = null;
var _selectedDisplayUnitId = null;
var _selectedDisplayUnitClasses = null;

// 新增显示单元后高亮标记（由添加窗口设置）
var _newDisplayUnitObjectName = null;
var _newDisplayUnitObjectClasses = null;

function onDisplayUnitProtocolTreeBeforeLoad(e) {
    var params = e.params || {};
    if (selectedDeviceTypeId) {
        params.deviceTypeIds = selectedDeviceTypeId;
    }
    e.params = params;
}

function onDisplayUnitProtocolTreeLoad(e) {
    var tree = e.sender;
    var root = tree.getRootNode();
    if (!root) return;

    var targetNode = null;

    // 如果有记录的上次选中的协议 code，优先选中它
    if (_selectedUnitConfigProtocolTreeNodeCode) {
        function findNode(node) {
            if (node.code === _selectedUnitConfigProtocolTreeNodeCode && node.classes === 1) {
                targetNode = node;
                return true;
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) {
                    if (findNode(node.children[i])) return true;
                }
            }
            return false;
        }
        findNode(root);
    }

    // 如果没有找到，选择第一个协议节点
    if (!targetNode) {
        var protocolNodes = [];
        function collect(node) {
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
            } else {
                if (node.classes === 1) protocolNodes.push(node);
            }
        }
        collect(root);
        if (protocolNodes.length > 0) targetNode = protocolNodes[0];
    }

    if (targetNode) {
        setTimeout(function() {
            tree.selectNode(targetNode);
        }, 50);
    }
}

function onDisplayUnitProtocolTreeSelect(e) {
    var node = e.node;
    _currentDisplayProtocolNode = node;
    if (node && node.classes === 1) {
        // 保存选中的协议 code 到全局变量（与采集单元共用）
        _selectedUnitConfigProtocolTreeNodeCode = node.code;
        // 清空新增标记
        _newDisplayUnitObjectName = null;
        _newDisplayUnitObjectClasses = null;
    }
    loadDisplayUnitList(node);
}

function loadDisplayUnitList(protocolNode) {
    var tree = mini.get('displayUnitList');
    if (!tree) return;
    // 设置 URL（如果还未设置）
    if (!tree.getUrl()) {
        tree.setUrl(context + '/acquisitionUnitManagerController/displayUnitTreeData');
    }
    // 加载时会触发 onDisplayUnitListBeforeLoad 传递协议参数
    tree.load();
}

function onDisplayUnitListBeforeLoad(e) {
    var params = e.params || {};
    if (_currentDisplayProtocolNode) {
        if (_currentDisplayProtocolNode.classes === 1) {
            params.protocol = _currentDisplayProtocolNode.code;
        } else if (_currentDisplayProtocolNode.classes === 0) {
            // 目录节点：收集所有子协议 code
            var protocolList = [];
            if (_currentDisplayProtocolNode.children) {
                for (var i = 0; i < _currentDisplayProtocolNode.children.length; i++) {
                    protocolList.push(_currentDisplayProtocolNode.children[i].code);
                }
            }
            params.protocol = protocolList.join(',');
        }
    }
    e.params = params;
}

function onDisplayUnitListLoad(e) {
    var tree = e.sender;
    var root = tree.getRootNode();
    if (!root) return;

    var targetNode = null;

    // 1. 处理新增对象高亮（优先）
    if (_newDisplayUnitObjectName && _newDisplayUnitObjectClasses !== null) {
        function findNewNode(node) {
            if (node.text === _newDisplayUnitObjectName && node.classes === _newDisplayUnitObjectClasses) {
                targetNode = node;
                return true;
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) {
                    if (findNewNode(node.children[i])) return true;
                }
            }
            return false;
        }
        findNewNode(root);
        if (targetNode) {
            _newDisplayUnitObjectName = null;
            _newDisplayUnitObjectClasses = null;
        }
    }

    // 2. 如果没有新增高亮，按记录的 ID 恢复
    if (!targetNode && _selectedDisplayUnitId && _selectedDisplayUnitClasses) {
        function findById(node) {
            if (node.id === _selectedDisplayUnitId && node.classes === _selectedDisplayUnitClasses) {
                targetNode = node;
                return true;
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) {
                    if (findById(node.children[i])) return true;
                }
            }
            return false;
        }
        findById(root);
    }

    // 3. 若都没有，选第一个显示单元（classes === 2）
    if (!targetNode) {
        function collect(node) {
            if (targetNode) return;
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                    if (targetNode) return;
                }
            } else {
                if (node.classes === 2) {
                    targetNode = node;
                }
            }
        }
        collect(root);
    }

    setTimeout(function() {
        if (targetNode) {
            tree.selectNode(targetNode);
        } else {
            // 如果无任何单元，选择根节点的第一个子节点（通常是协议节点）
            if (root.children && root.children.length > 0) {
                tree.selectNode(root.children[0]);
            }
        }
    }, 50);
}

function onDisplayUnitListSelect(e) {
    var node = e.node;
    if (!node) return;
    _currentDisplayUnitNode = node;
    _selectedDisplayUnitId = node.id;
    _selectedDisplayUnitClasses = node.classes;

    var tabs = mini.get('displayUnitRightTabs');
    if (!tabs) return;

    var propsTab = tabs.getTab('props');
    var configTab = tabs.getTab('config');
    if (!propsTab || !configTab) return;

    var classes = node.classes;

    // 根节点或协议节点：隐藏所有 Tab
    if (classes === 0 || classes === 1) {
        tabs.updateTab(propsTab, { visible: false });
        tabs.updateTab(configTab, { visible: false });
        // 可销毁 Helper
        return;
    }

    // 显示单元（classes === 2）：显示两个 Tab
    if (classes === 2) {
        tabs.updateTab(propsTab, { visible: true });
        tabs.updateTab(configTab, { visible: true });

        // 默认激活配置 Tab（与 ExtJS 一致，activeIndex=1）
        var active = tabs.getActiveTab();
        if (!active) {
            tabs.activeTab('config');
        } else {
        	var active = tabs.getActiveTab();
          	if(active.name == 'props'){
          		loadDisplayUnitProperties(node);
          	}else{
          		loadDisplayUnitConfig(node);
          	}
        }
        // 属性数据在切换到属性 Tab 时加载（通过 onDisplayUnitDetailTabChanged）
    }
}

function onDisplayUnitDetailTabChanged(e) {
    if (!_currentDisplayUnitNode) return;
    var tabs = e.sender;
    var active = tabs.getActiveTab();
    if (!active) return;
    var tabName = active.name; // 'props' 或 'config'

    if (tabName === 'props') {
        loadDisplayUnitProperties(_currentDisplayUnitNode);
    } else if (tabName === 'config') {
        loadDisplayUnitConfig(_currentDisplayUnitNode);
    }
}

function loadDisplayUnitProperties(node) {
    var container = document.getElementById('displayUnitPropertiesContainer');
    if (!container) return;

    // 销毁已有表格
    if (protocolDisplayUnitPropertiesHandsontableHelper) {
        if (protocolDisplayUnitPropertiesHandsontableHelper.hot) {
            protocolDisplayUnitPropertiesHandsontableHelper.hot.destroy();
        }
        protocolDisplayUnitPropertiesHandsontableHelper = null;
    }

    var root = [];
    var unitList = [];
    var unitIdNameList = [];

    // 1. 根据节点类型构建基本信息
    if (node.classes === 0) {
        root.push({ id: 1, title: _loginUserLanguageResource.rootNode, value: _loginUserLanguageResource.unitList });
    } else if (node.classes === 1) {
        root.push({ id: 1, title: _loginUserLanguageResource.protocolName, value: node.text });
    } else if (node.classes === 2) {
        // 更新信息标签（右上角显示当前单元名）
        //var infoLabel = document.getElementById('displayUnitInfoLabel');
        //if (infoLabel && node.text) {
        //    infoLabel.innerHTML = '【<font color="red">' + node.text + '</font>】';
        //}

        // 获取当前选中的协议列表（用于请求采集单元列表）
        var protocolList = [];
        var protocolTree = mini.get('displayUnitProtocolTree');
        if (protocolTree) {
            var selectedProtocolNode = protocolTree.getSelectedNode();
            if (selectedProtocolNode) {
                if (selectedProtocolNode.classes === 1) {
                    protocolList.push(selectedProtocolNode.code);
                } else if (selectedProtocolNode.classes === 0) {
                    if (selectedProtocolNode.children) {
                        for (var i = 0; i < selectedProtocolNode.children.length; i++) {
                            protocolList.push(selectedProtocolNode.children[i].code);
                        }
                    }
                }
            }
        }

        // 同步请求获取采集单元列表（用于下拉框）
        $.ajax({
            type: 'POST',
            url: context + '/acquisitionUnitManagerController/getAcqUnitList',
            async: false, // 同步，确保数据就绪
            data: { protocol: protocolList.join(',') },
            dataType: 'json',
            success: function(response) {
            	unitList = response.unitList || [];
                unitIdNameList = response.unitIdNameList || [];
            },
            error: function() {
                // 静默失败，不影响主流程
            }
        });

        // 构建属性行（与 ExtJS 一致）
        root.push({ id: 1, title: _loginUserLanguageResource.unitName, value: node.text || '' });
        var acqUnitDisplay = node.protocol ? node.protocol + '/' + (node.acqUnitName || '') : (node.acqUnitName || '');
        root.push({ id: 2, title: _loginUserLanguageResource.acqUnit, value: acqUnitDisplay });
        root.push({ id: 3, title: _loginUserLanguageResource.calculationType, value: node.calculateTypeName || _loginUserLanguageResource.nothing });
        root.push({ id: 4, title: _loginUserLanguageResource.sequenceNumber, value: node.sort || '' });
        root.push({ id: 5, title: _loginUserLanguageResource.remark, value: node.remark || '' });
    }

    // 2. 创建或更新 Helper
    protocolDisplayUnitPropertiesHandsontableHelper = ProtocolDisplayUnitPropertiesHandsontableHelper.createNew('displayUnitPropertiesContainer');
    var colHeaders = [_loginUserLanguageResource.idx, _loginUserLanguageResource.variable, _loginUserLanguageResource.value];
    var columns = [{ data: 'id' }, { data: 'title' }, { data: 'value' }];
    protocolDisplayUnitPropertiesHandsontableHelper.colHeaders = colHeaders;
    protocolDisplayUnitPropertiesHandsontableHelper.columns = columns;
    protocolDisplayUnitPropertiesHandsontableHelper.classes = node.classes;
    protocolDisplayUnitPropertiesHandsontableHelper.unitList = unitList;
    protocolDisplayUnitPropertiesHandsontableHelper.unitIdNameList = unitIdNameList;
    protocolDisplayUnitPropertiesHandsontableHelper.createTable(root);
}

/**
 * 加载显示单元配置（Config）：包含采集项配置和控制项配置两个表格
 */
function loadDisplayUnitConfig(node) {
    // 先加载采集项配置
    loadDisplayUnitAcqItemsConfig(node);
    // 再加载控制项配置
    loadDisplayUnitCtrlItemsConfig(node);
}

//================================================================
//加载采集项配置数据
//================================================================
function loadDisplayUnitAcqItemsConfig(node) {
 // node 为当前选中的显示单元节点（classes===2）
 if (!node || node.classes !== 2) {
     // 如果不是单元节点，可清空表格或返回
     return;
 }

 // 销毁已有 Helper（如有）
 if (protocolDisplayUnitAcqItemsConfigHandsontableHelper) {
     if (protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot) {
         protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.destroy();
     }
     protocolDisplayUnitAcqItemsConfigHandsontableHelper = null;
 }

 // 遮罩父容器（displayUnitRightTabs 或 displayAcqItemsContainer）
 var maskEl = document.getElementById('displayUnitRightTabs') || document.body;
 var mask = mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 // 准备请求参数
 var protocolCode = node.protocolCode || '';
 var classes = node.classes || 2;
 var code = node.code || '';
 var unitId = node.id || '';
 var acqUnitId = node.acqUnitId || '';
 var calculateType = node.calculateType !== undefined ? node.calculateType : 0;

 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/getProtocolDisplayUnitAcqItemsConfigData',
     data: {
         protocolCode: protocolCode,
         classes: classes,
         code: code,
         unitId: unitId,
         acqUnitId: acqUnitId,
         calculateType: calculateType
     },
     dataType: 'json',
     success: function(result) {
         mini.unmask(maskEl);
         if (!result.success) {
             mini.alert(result.message || _loginUserLanguageResource.requestFailed);
             return;
         }

         var tableData = result.totalRoot || [];
         // 如果数据为空，可填充空行（如 30 行空数据，保持表格可编辑）
         if (tableData.length === 0) {
             for (var i = 0; i < 30; i++) tableData.push({});
         }

         // 创建 Helper（如果未创建）
         if (!protocolDisplayUnitAcqItemsConfigHandsontableHelper) {
             protocolDisplayUnitAcqItemsConfigHandsontableHelper = ProtocolDisplayUnitAcqItemsConfigHandsontableHelper.createNew('ModbusProtocolDisplayUnitAcqItemsConfigTableInfoDiv_id');

             // 配置表头（nestedHeaders）和列定义（从 ExtJS 原版迁移）
             var colHeaders = [
                 ['', '', '', '', '', '',
                     { label: _loginUserLanguageResource.realtimeMonitoring, colspan: 7 },
                     { label: _loginUserLanguageResource.historyQuery, colspan: 7 },
                     '', '', '', '', '', '', '', '', '', ''],
                 ['', '', '', '', '', '',
                     { label: _loginUserLanguageResource.deviceOverview, colspan: 2 },
                     { label: _loginUserLanguageResource.dynamicData, colspan: 4 },
                     _loginUserLanguageResource.trendCurve,
                     { label: _loginUserLanguageResource.deviceOverview, colspan: 2 },
                     { label: _loginUserLanguageResource.historyData, colspan: 4 },
                     _loginUserLanguageResource.trendCurve,
                     '', '', '', '', '', '', '', '', '', ''],
                 ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name, _loginUserLanguageResource.dataSource, _loginUserLanguageResource.unit, _loginUserLanguageResource.showLevel,
                     _loginUserLanguageResource.deviceOverview, _loginUserLanguageResource.columnSort,
                     _loginUserLanguageResource.dynamicData, _loginUserLanguageResource.columnSort, _loginUserLanguageResource.foregroundColor, _loginUserLanguageResource.backgroundColor, _loginUserLanguageResource.curveConfig,
                     _loginUserLanguageResource.deviceOverview, _loginUserLanguageResource.columnSort,
                     _loginUserLanguageResource.historyData, _loginUserLanguageResource.columnSort, _loginUserLanguageResource.foregroundColor, _loginUserLanguageResource.backgroundColor, _loginUserLanguageResource.curveConfig,
                     _loginUserLanguageResource.showName,
                     '', '', '', '', '', '', '', '', '']
             ];

             var columns = [
                 { data: 'checked', type: 'checkbox' },
                 { data: 'id' },
                 { data: 'showTitle' },
                 { data: 'dataSource' },
                 { data: 'unit' },
                 { data: 'showLevel', type: 'text', allowInvalid: true },
                 { data: 'realtimeOverview', type: 'checkbox' },
                 { data: 'realtimeOverviewSort', type: 'text', allowInvalid: true },
                 { data: 'realtimeData', type: 'checkbox' },
                 { data: 'realtimeSort', type: 'text', allowInvalid: true },
                 { data: 'realtimeColor' },
                 { data: 'realtimeBgColor' },
                 { data: 'realtimeCurveConfShowValue' },
                 { data: 'historyOverview', type: 'checkbox' },
                 { data: 'historyOverviewSort', type: 'text', allowInvalid: true },
                 { data: 'historyData', type: 'checkbox' },
                 { data: 'historySort', type: 'text', allowInvalid: true },
                 { data: 'historyColor' },
                 { data: 'historyBgColor' },
                 { data: 'historyCurveConfShowValue' },
                 { data: 'switchingValueShowType', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.meaning, _loginUserLanguageResource.dataColumn + '/' + _loginUserLanguageResource.meaning] },
                 { data: 'realtimeCurveConf' },
                 { data: 'historyCurveConf' },
                 { data: 'resolutionMode', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.switchingValue, _loginUserLanguageResource.enumValue, _loginUserLanguageResource.numericValue] },
                 { data: 'addr', type: 'text', allowInvalid: true },
                 { data: 'highLowByte' },
                 { data: 'bitIndex' },
                 { data: 'type' },
                 { data: 'code' },
                 { data: 'title' }
             ];

             protocolDisplayUnitAcqItemsConfigHandsontableHelper.colHeaders = colHeaders;
             protocolDisplayUnitAcqItemsConfigHandsontableHelper.columns = columns;
             protocolDisplayUnitAcqItemsConfigHandsontableHelper.hiddenColumns = [0, 6, 7, 13, 14, 21, 22, 23, 24, 25, 26, 27, 28, 29];
             protocolDisplayUnitAcqItemsConfigHandsontableHelper.colWidths = [25, 50, 200, 80, 80, 80, 80, 80, 60, 80, 80, 80, 180, 80, 80, 100, 80, 80, 80, 180, 100];
             protocolDisplayUnitAcqItemsConfigHandsontableHelper.createTable(tableData);
         }else{
        	 protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.loadData(tableData);
         }
         // 更新信息标签（可选）
         //var infoLabel = document.getElementById('displayUnitInfoLabel');
         //if (infoLabel && node.text) {
         //    infoLabel.innerHTML = '【<font color="red">' + node.text + '</font>】' + _loginUserLanguageResource.acquisitionItemConfig;
         //}
     },
     error: function() {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.requestFailed);
     }
 });
}

//================================================================
//加载控制项配置数据
//================================================================
function loadDisplayUnitCtrlItemsConfig(node) {
 if (!node || node.classes !== 2) {
     return;
 }

 if (protocolDisplayUnitCtrlItemsConfigHandsontableHelper) {
     if (protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot) {
         protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.destroy();
     }
     protocolDisplayUnitCtrlItemsConfigHandsontableHelper = null;
 }

 var maskEl = document.getElementById('displayUnitRightTabs') || document.body;
 var mask = mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 var protocolCode = node.protocolCode || '';
 var classes = node.classes || 2;
 var code = node.code || '';
 var unitId = node.id || '';
 var acqUnitId = node.acqUnitId || '';

 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/getProtocolDisplayUnitCtrlItemsConfigData',
     data: {
         protocolCode: protocolCode,
         classes: classes,
         code: code,
         unitId: unitId,
         acqUnitId: acqUnitId
     },
     dataType: 'json',
     success: function(result) {
         mini.unmask(maskEl);
         if (!result.success) {
             mini.alert(result.message || _loginUserLanguageResource.requestFailed);
             return;
         }

         var tableData = result.totalRoot || [];
         if (tableData.length === 0) {
             for (var i = 0; i < 30; i++) tableData.push({});
         }

         if (!protocolDisplayUnitCtrlItemsConfigHandsontableHelper) {
             protocolDisplayUnitCtrlItemsConfigHandsontableHelper = ProtocolDisplayUnitCtrlItemsConfigHandsontableHelper.createNew('ModbusProtocolDisplayUnitCtrlItemsConfigTableInfoDiv_id');

             var colHeaders = [
                 '', _loginUserLanguageResource.idx, _loginUserLanguageResource.name, _loginUserLanguageResource.unit,
                 _loginUserLanguageResource.showLevel, _loginUserLanguageResource.columnSort, _loginUserLanguageResource.showName
             ];
             var columns = [
                 { data: 'checked', type: 'checkbox' },
                 { data: 'id' },
                 { data: 'showTitle' },
                 { data: 'unit' },
                 { data: 'showLevel', type: 'text', allowInvalid: true },
                 { data: 'realtimeSort', type: 'text', allowInvalid: true },
                 { data: 'switchingValueShowType', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.meaning, _loginUserLanguageResource.dataColumn + '/' + _loginUserLanguageResource.meaning] },
                 { data: 'resolutionMode', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.switchingValue, _loginUserLanguageResource.enumValue, _loginUserLanguageResource.numericValue] },
                 { data: 'addr', type: 'text', allowInvalid: true },
                 { data: 'highLowByte' },
                 { data: 'bitIndex' },
                 { data: 'title' }
             ];

             protocolDisplayUnitCtrlItemsConfigHandsontableHelper.colHeaders = colHeaders;
             protocolDisplayUnitCtrlItemsConfigHandsontableHelper.columns = columns;
             protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hiddenColumns = [6, 7, 8, 9, 10, 11];
             protocolDisplayUnitCtrlItemsConfigHandsontableHelper.colWidths = [25, 50, 140, 80, 60, 60, 80];
             protocolDisplayUnitCtrlItemsConfigHandsontableHelper.createTable(tableData);
         }else{
        	 protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.loadData(tableData);
         }

         // 更新信息标签（可选）
         //var infoLabel = document.getElementById('displayUnitInfoLabel');
         //if (infoLabel && node.text) {
         //    infoLabel.innerHTML = '【<font color="red">' + node.text + '</font>】' + _loginUserLanguageResource.controlItemConfig;
         //}
     },
     error: function() {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.requestFailed);
     }
 });
}

// ================================================================
// 5. 全选/取消全选（采集项、控制项）
// ================================================================

function displayAcqSelectAll() {
    var helper = protocolDisplayUnitAcqItemsConfigHandsontableHelper;
    if (helper && helper.hot) {
        var rowCount = helper.hot.countRows();
        var updateData = [];
        for (var i = 0; i < rowCount; i++) {
            updateData.push([i, 'checked', true]);
        }
        helper.hot.setDataAtRowProp(updateData);
    }
}

function displayAcqDeselectAll() {
    var helper = protocolDisplayUnitAcqItemsConfigHandsontableHelper;
    if (helper && helper.hot) {
        var rowCount = helper.hot.countRows();
        var updateData = [];
        for (var i = 0; i < rowCount; i++) {
            updateData.push([i, 'checked', false]);
        }
        helper.hot.setDataAtRowProp(updateData);
    }
}

function displayCtrlSelectAll() {
    var helper = protocolDisplayUnitCtrlItemsConfigHandsontableHelper;
    if (helper && helper.hot) {
        var rowCount = helper.hot.countRows();
        var updateData = [];
        for (var i = 0; i < rowCount; i++) {
            updateData.push([i, 'checked', true]);
        }
        helper.hot.setDataAtRowProp(updateData);
    }
}

function displayCtrlDeselectAll() {
    var helper = protocolDisplayUnitCtrlItemsConfigHandsontableHelper;
    if (helper && helper.hot) {
        var rowCount = helper.hot.countRows();
        var updateData = [];
        for (var i = 0; i < rowCount; i++) {
            updateData.push([i, 'checked', false]);
        }
        helper.hot.setDataAtRowProp(updateData);
    }
}

// ================================================================
// 6. 刷新、保存、添加、导出/导入等操作（骨架）
// ================================================================

function refreshDisplayUnitProtocolTree() {
    var tree = mini.get('displayUnitProtocolTree');
    if (tree) tree.load();
}

function refreshDisplayUnitList() {
    var tree = mini.get('displayUnitList');
    if (tree) tree.load();
}

function saveDisplayUnitConfigData() {
    // 待实现保存逻辑
    mini.alert(_loginUserLanguageResource.savedSuccessfully);
}

function addDisplayUnitInfo() {
    // 参考采集单元 addAcquisitionUnitInfo，打开添加窗口
    mini.alert(_loginUserLanguageResource.addDisplayUnit);
}

function openExportDisplayUnitWindow() {
    mini.alert(_loginUserLanguageResource.exportData);
}

function openImportDisplayUnitWindow() {
    mini.alert(_loginUserLanguageResource.importData);
}

// ================================================================
// 7. 右键菜单事件（如有需要可后续添加）
// ================================================================

function onDisplayUnitTreeBeforeMenu(e) {
    var tree = mini.get('displayUnitList');
    var menu = e.sender;
    var node = tree.getSelectedNode();
    if (!node || node.classes !== 2) {
        e.cancel = true;
        e.htmlEvent.preventDefault();
        return;
    }
    var deleteText = _loginUserLanguageResource.deleteData;
    document.getElementById('displayUnitTreeMenuDeleteText').textContent = deleteText;
    var deleteItem = mini.getbyName('delete', menu);
    if (!editFlag) {
        deleteItem.disable();
    } else {
        deleteItem.enable();
    }
}