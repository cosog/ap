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
	if (isInitializing) return;
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
	var tree = mini.get('displayUnitList');
    if (!tree) return;
    var selectedNode = tree.getSelectedNode();
    if (!selectedNode) return;
    if (selectedNode.classes !== 2) {
        // 只对显示单元节点进行保存
        return;
    }

    var tabs = mini.get('displayUnitRightTabs');
    if (!tabs) return;
    var activeTab = tabs.getActiveTab();
    if (!activeTab) return;
    var activeName = activeTab.name; // 'props' 或 'config'

    if (activeName === 'props') {
        // 保存属性
        saveDisplayUnitProperties(tree,selectedNode);
    } else if (activeName === 'config') {
        // 保存配置（采集项和控制项）
        grantDisplayAcqItemsPermission();
        grantDisplayCtrlItemsPermission();
    }
}

function saveDisplayUnitProperties(tree,node) {
    var helper = protocolDisplayUnitPropertiesHandsontableHelper;
    if (!helper || !helper.hot) {
        mini.alert(_loginUserLanguageResource.noDataToSave);
        return;
    }

    var propertiesData = helper.hot.getData();
    // propertiesData 每行: [id, title, value]

    // 获取单元名称（第0行，值列）
    var unitName = propertiesData[0] && propertiesData[0][2] ? propertiesData[0][2] : '';
    // 采控单元（第1行，值列）- 存储的是显示文本（boxval）
    var acqUnitName = propertiesData[1] && propertiesData[1][2] ? propertiesData[1][2] : '';
    // 计算类型（第2行，值列）- 存储的是显示文本（如 "功图计算"）
    var calcTypeText = propertiesData[2] && propertiesData[2][2] ? propertiesData[2][2] : '';
    // 排序（第3行，值列）
    var sort = propertiesData[3] && propertiesData[3][2] ? propertiesData[3][2] : '';
    // 备注（第4行，值列）
    var remark = propertiesData[4] && propertiesData[4][2] ? propertiesData[4][2] : '';

    // 计算类型转数字
    var calculateType = 0;
    if (calcTypeText === _loginUserLanguageResource.SRPCalculate) {
        calculateType = 1;
    } else if (calcTypeText === _loginUserLanguageResource.PCPCalculate) {
        calculateType = 2;
    }

    var acqUnitId = node.acqUnitId || '';

    // 构造保存数据
    var displayUnitProperties = {
        classes: node.classes,
        id: node.id,
        unitCode: node.code || '',
        unitName: unitName,
        acqUnitId: acqUnitId,
        acqUnitName: acqUnitName,
        calculateType: calculateType,
        sort: sort,
        remark: remark
    };

    var displayUnitSaveData = {
        updatelist: [displayUnitProperties]
    };

    // 获取协议和 deviceType
    var protocol ='';
    if (_currentDisplayProtocolNode) {
        if (_currentDisplayProtocolNode.classes === 1) {
            protocol = _currentDisplayProtocolNode.code;
        } else if (_currentDisplayProtocolNode.classes === 0) {
            // 目录节点：收集所有子协议 code
            var protocolList = [];
            if (_currentDisplayProtocolNode.children) {
                for (var i = 0; i < _currentDisplayProtocolNode.children.length; i++) {
                    protocolList.push(_currentDisplayProtocolNode.children[i].code);
                }
            }
            protocol = protocolList.join(',');
        }
    }
    
    var parent = tree.getParentNode(node);
    
    var deviceType = 0;
    if(parent){
    	deviceType=parent.deviceType;
    }

    saveDisplayUnitTreeData(displayUnitSaveData, protocol, deviceType);
}



//---- 打开添加显示单元窗口 ----
function addDisplayUnitInfo() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var selectedDeviceNode = deviceTree.getSelectedNode();
    if (!selectedDeviceNode) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var deviceTypeIds = selectedDeviceTypeId || '';

    // 获取当前选中的协议节点（可能为协议或目录）
    var protocolTree = mini.get('displayUnitProtocolTree');
    var selectedProtocolNode = protocolTree ? protocolTree.getSelectedNode() : null;
    var protocolList = '';
    if (selectedProtocolNode) {
        if (selectedProtocolNode.classes === 1) {
            protocolList = selectedProtocolNode.code || '';
        } else if (selectedProtocolNode.classes === 0) {
            var codes = [];
            function collect(node) {
                if (node.children && node.children.length > 0) {
                    for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
                } else {
                    if (node.classes === 1 && node.code) codes.push(node.code);
                }
            }
            collect(selectedProtocolNode);
            protocolList = codes.join(',');
        }
    }

    mini.open({
        title: _loginUserLanguageResource.addDisplayUnit,
        url: context + '/miniui-app/modules/driverConfig/displayUnitAddWindow.jsp',
        width: 450,
        height: 480,
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: deviceTypeIds,
                protocolList: protocolList
            });
            // 暴露刷新父窗口单元列表树的函数
            contentWindow.parent._parentRefreshUnitTree = function() {
            	refreshDisplayUnitList();
            };
            contentWindow.parent._parentSetNewObject = function(name, classes) {
                window._newDisplayUnitObjectName = name;
                window._newDisplayUnitObjectClasses = classes;
            };
        },
        ondestroy: function(action) {
            if (action === 'ok') {
                // 刷新树（已在子窗口中调用）
            }
        }
    });
}

function openExportDisplayUnitWindow() {
    mini.alert(_loginUserLanguageResource.exportData);
}

function openImportDisplayUnitWindow() {
    mini.alert(_loginUserLanguageResource.importData);
}

// ================================================================
// 7. 右键菜单事件
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

//================================================================
//删除显示单元节点
//================================================================
function deleteDisplayUnitNode(e) {
 var tree = mini.get('displayUnitList');
 var node = tree.getSelectedNode();
 if (!node) {
     return;
 }

 var nodeId = node.id;
 var nodeText = node.text;

 // 确认删除
 mini.confirm(
     _loginUserLanguageResource.confirmDelete,
     _loginUserLanguageResource.confirm,
     function(action) {
         if (action === 'ok') {
             // 构造删除数据
             var deleteData = {
                 delidslist: [nodeId]
             };
             // 获取 protocol 和 deviceType（从父节点获取）
             var protocol = node.protocolCode || '';
             
             var parent = tree.getParentNode(node);
             
             var deviceType = 0;
             if(parent){
             	deviceType=parent.deviceType;
             }
             // 调用保存接口（复用保存函数，传入删除数据）
             saveDisplayUnitTreeData(deleteData, protocol, deviceType);
         }
     }
 );
}

function saveDisplayUnitTreeData(displayUnitSaveData, protocol, deviceType) {
    var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.updateWait });

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/saveDisplayUnitHandsontableData',
        data: {
            data: JSON.stringify(displayUnitSaveData),
            protocol: protocol,
            deviceType: deviceType
        },
        dataType: 'json',
        success: function(response) {
            mini.unmask(document.body);
            if (response.success) {
                var msg = displayUnitSaveData.delidslist && displayUnitSaveData.delidslist.length > 0
                    ? _loginUserLanguageResource.deleteSuccessfully
                    : _loginUserLanguageResource.savedSuccessfully;
                mini.alert(msg);
                // 刷新树
                refreshDisplayUnitList();
            } else {
                mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed) + '</font>');
            }
        },
        error: function() {
            mini.unmask(document.body);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

/**
 * 更新曲线配置
 * @param {number} row - 行索引
 * @param {number} col - 列索引（12 或 19）
 * @param {number} tableType - 表类型（0:采集项, 1:控制项等）
 * @param {object} config - 曲线配置对象
 */
window.updateCurveConfig = function(row, col, tableType, config) {
    var helper = null;
    if (tableType === 0) {
        helper = protocolDisplayUnitAcqItemsConfigHandsontableHelper;
    } else if (tableType === 1) {
        helper = protocolDisplayUnitCtrlItemsConfigHandsontableHelper;
    } else {
        return;
    }
    if (!helper || !helper.hot) return;

    // 构造显示字符串（与 ExtJS 格式一致）
    var showValue = _loginUserLanguageResource.curveGroup + ':' + (config.groupName || _loginUserLanguageResource.nothing) + ';' +
                    config.sort + ';' +
                    (config.yAxisOpposite ? _loginUserLanguageResource.right : _loginUserLanguageResource.left) + ';' +
                    config.color;
    // 更新显示列（col=12 实时曲线，col=19 历史曲线）
    helper.hot.setDataAtCell(row, col, showValue);
    // 更新隐藏的配置对象列（实时曲线对应索引21，历史曲线对应索引22）
    var configCol = (col === 12) ? 21 : 22;
    helper.hot.setDataAtCell(row, configCol, config);
    helper.hot.render();
};

/**
 * 更新颜色值
 * @param {number} row - 行索引
 * @param {number} col - 列索引
 * @param {number} tableType - 表类型
 * @param {string} color - 颜色值（不含#）
 */
window.updateColor = function(row, col, tableType, color) {
    var helper = null;
    if (tableType === 0) {
        helper = protocolDisplayUnitAcqItemsConfigHandsontableHelper;
    } else if (tableType === 1) {
        helper = protocolDisplayUnitCtrlItemsConfigHandsontableHelper;
    } else {
        return;
    }

    if (!helper || !helper.hot) return;

    helper.hot.setDataAtCell(row, col, color);
    helper.hot.render();
};

// ================================================================
// 打开曲线配置窗口
// ================================================================
function openCurveConfigWindow(row, column, tableType) {
    // 确定使用的 Helper
    var helper = null;
    if (tableType === 0) {
        helper = protocolDisplayUnitAcqItemsConfigHandsontableHelper;
    } else if (tableType === 1) {
        helper = protocolDisplayUnitCtrlItemsConfigHandsontableHelper;
    } else {
        return;
    }
    if (!helper || !helper.hot) return;

    // 获取当前行数据，提取已有配置
    var rowData = helper.hot.getDataAtRow(row);
    var config = null;
    if (column === 12 && rowData[21]) config = rowData[21];
    else if (column === 19 && rowData[22]) config = rowData[22];
    var curveType = (column === 12) ? 1 : 2; // 1:实时曲线，2:历史曲线
    mini.open({
        title: _loginUserLanguageResource.curveConfig,
        url: context + '/miniui-app/modules/driverConfig/curveConfigWindow.jsp',
        width: 480,
        height: 520,
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                row: row,
                col: column,
                tableType: tableType,
                curveType: curveType,
                config: config
            });
            contentWindow._updateCurveConfig = function(row, col, tableType, config) {
            	updateCurveConfig(row, col, tableType, config);
            };
        },
        ondestroy: function() {
            // 可选：清理
        }
    });
}

// ================================================================
// 打开颜色选择窗口
// ================================================================
function openColorPickerWindow(row, column, tableType) {
    var helper = null;
    if (tableType === 0) {
        helper = protocolDisplayUnitAcqItemsConfigHandsontableHelper;
    } else if (tableType === 1) {
        helper = protocolDisplayUnitCtrlItemsConfigHandsontableHelper;
    } else {
        return;
    }

    if (!helper || !helper.hot) return;

    var currentColor = helper.hot.getDataAtCell(row, column) || 'ff0000';

    mini.open({
        title: _loginUserLanguageResource.colorSelect,
        url: context + '/miniui-app/modules/driverConfig/colorSelectWindow.jsp',
        width: 500,
        height: 300,
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                row: row,
                col: column,
                tableType: tableType,
                currentColor: currentColor
            });
            contentWindow._updateColor = function(row, col, tableType, color) {
            	updateColor(row, col, tableType, color);
            };
        }
    });
}