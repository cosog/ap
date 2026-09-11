// ================================================================
// 报警单元配置模块
// ================================================================

// 全局 Helper 变量
var protocolConfigAlarmUnitPropertiesHandsontableHelper = null;
var protocolAlarmUnitConfigNumItemsHandsontableHelper = null;
var protocolAlarmUnitConfigSwitchItemsHandsontableHelper = null;
var protocolAlarmUnitConfigEnumItemsHandsontableHelper = null;
var protocolAlarmUnitConfigCommStatusItemsHandsontableHelper = null;
var protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = null;
var protocolAlarmUnitConfigRunStatusItemsHandsontableHelper = null;

// 报警单元状态变量
var _currentAlarmProtocolNode = null;
var _currentAlarmUnitNode = null;
var _selectedAlarmUnitId = null;
var _selectedAlarmUnitClasses = null;

// 新增报警单元后高亮标记（由添加窗口设置）
var _newAlarmUnitObjectName = null;
var _newAlarmUnitObjectClasses = null;

// ================================================================
// 1. 报警单元 - 协议树事件
// ================================================================

function onAlarmProtocolTreeBeforeLoad(e) {
    var params = e.params || {};
    if (selectedDeviceTypeId) {
        params.deviceTypeIds = selectedDeviceTypeId;
    }
    e.params = params;
}

function onAlarmProtocolTreeLoad(e) {
    var tree = e.sender;
    var root = tree.getRootNode();
    if (!root) return;

    var targetNode = null;

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

    if (!targetNode) {
        var protocolNodes = [];
        function collect(node) {
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
            } else {
                if (node.classes == 1) protocolNodes.push(node);
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

function onAlarmProtocolTreeSelect(e) {
    var node = e.node;
    _currentAlarmProtocolNode = node;
    if (node && node.classes === 1) {
        _selectedUnitConfigProtocolTreeNodeCode = node.code;
        _newAlarmUnitObjectName = null;
        _newAlarmUnitObjectClasses = null;
    }
    loadAlarmUnitList(node);
}

function loadAlarmUnitList(protocolNode) {
    var tree = mini.get('alarmUnitList');
    if (!tree) return;
    if (!tree.getUrl()) {
        tree.setUrl(context + '/acquisitionUnitManagerController/modbusProtocolAlarmUnitTreeData');
    }
    tree.load();
}

// ================================================================
// 2. 报警单元列表树事件
// ================================================================

function onAlarmUnitListBeforeLoad(e) {
    var params = e.params || {};
    if (_currentAlarmProtocolNode) {
        if (_currentAlarmProtocolNode.classes == 1) {
            params.protocol = _currentAlarmProtocolNode.code;
        } else {
            var protocolList = [];
            if (isNotVal(_currentAlarmProtocolNode.children)) {
                for (var i = 0; i < _currentAlarmProtocolNode.children.length; i++) {
                    protocolList.push(_currentAlarmProtocolNode.children[i].code);
                }
            }
            params.protocol = protocolList.join(",");
        }
    }
    e.params = params;
}

function onAlarmUnitListLoad(e) {
    var tree = e.sender;
    var root = tree.getRootNode();
    if (!root) return;

    var targetNode = null;

    // 1. 处理新增对象高亮（优先）
    if (_newAlarmUnitObjectName && _newAlarmUnitObjectClasses !== null) {
        function findNewNode(node) {
            if (node.text === _newAlarmUnitObjectName && node.classes === _newAlarmUnitObjectClasses) {
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
            _newAlarmUnitObjectName = null;
            _newAlarmUnitObjectClasses = null;
        }
    }

    // 2. 如果没有新增高亮，按记录的 ID 恢复
    if (!targetNode && _selectedAlarmUnitId && _selectedAlarmUnitClasses) {
        function findById(node) {
            if (node.id === _selectedAlarmUnitId && node.classes === _selectedAlarmUnitClasses) {
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

    // 3. 若都没有，选第一个报警单元（classes === 3）
    if (!targetNode) {
        function collect(node) {
            if (targetNode) return;
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                    if (targetNode) return;
                }
            } else {
                if (node.classes === 3) {
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
            if (root.children && root.children.length > 0) {
                tree.selectNode(root.children[0]);
            }
        }
    }, 50);
}

/**
 * 报警单元列表选中事件（清理重复代码）
 */
function onAlarmUnitListSelect(e) {
    var node = e.node;
    if (!node) return;
    _currentAlarmUnitNode = node;
    _selectedAlarmUnitId = node.id;
    _selectedAlarmUnitClasses = node.classes;

    var tabs = mini.get('alarmUnitRightTabs');
    if (!tabs) return;

    var propsTab = tabs.getTab('props');
    var configTab = tabs.getTab('config');
    if (!propsTab || !configTab) return;

    var classes = node.classes;

    // 根/协议节点：隐藏所有
    if (classes === 0 || classes === 1) {
        tabs.updateTab(propsTab, { visible: false });
        tabs.updateTab(configTab, { visible: false });
        return;
    }

    // 报警单元（classes === 3）：显示两个 Tab
    if (classes === 3) {
        tabs.updateTab(propsTab, { visible: true });
        tabs.updateTab(configTab, { visible: true });

        // 默认激活配置 Tab，与 ExtJS 一致
        var active = tabs.getActiveTab();
        if (!active) {
            tabs.activeTab('config');
        } else if (active.name === 'props') {
            loadAlarmUnitProperties(node);
        } else {
            loadAlarmUnitConfig(node);
        }
    }
}

// ================================================================
// 3. Tab 切换事件
// ================================================================

function onAlarmUnitDetailTabChanged(e) {
    if (isInitializing) return;
    if (!_currentAlarmUnitNode) return;
    var tabs = e.sender;
    var active = tabs.getActiveTab();
    if (!active) return;
    var tabName = active.name; // 'props' 或 'config'
    if (tabName === 'props') {
        loadAlarmUnitProperties(_currentAlarmUnitNode);
    } else if (tabName === 'config') {
        loadAlarmUnitConfig(_currentAlarmUnitNode);
    }
}

function onAlarmConfigSubTabChanged(e) {
    if (isInitializing) return;
    if (!_currentAlarmUnitNode) return;
    var tabs = e.sender;
    var active = tabs.getActiveTab();
    if (!active) return;
    loadAlarmUnitConfig(_currentAlarmUnitNode);
}

// ================================================================
// 4. 加载报警单元属性（完整实现）
// ================================================================
function loadAlarmUnitProperties(node) {
    var container = document.getElementById('alarmUnitPropertiesContainer');
    if (!container) return;

    // 销毁已有表格
    if (protocolConfigAlarmUnitPropertiesHandsontableHelper) {
        if (protocolConfigAlarmUnitPropertiesHandsontableHelper.hot) {
            protocolConfigAlarmUnitPropertiesHandsontableHelper.hot.destroy();
        }
        protocolConfigAlarmUnitPropertiesHandsontableHelper = null;
    }

    // 构建表格数据（root）
    var root = [];
    var classes = node.classes;

    if (classes === 0) {
        // 根节点
        root.push({
            id: 1,
            title: _loginUserLanguageResource.rootNode,
            value: _loginUserLanguageResource.unitList
        });
    } else if (classes === 1) {
        // 协议节点
        root.push({
            id: 1,
            title: _loginUserLanguageResource.protocolName,
            value: node.text
        });
    } else if (classes === 3) {
        // 报警单元节点
        root.push({
            id: 1,
            title: _loginUserLanguageResource.unitName,
            value: node.text
        });
        root.push({
            id: 2,
            title: _loginUserLanguageResource.calculationType,
            value: node.calculateTypeName
        });
        root.push({
            id: 3,
            title: _loginUserLanguageResource.sequenceNumber,
            value: node.sort
        });
        root.push({
            id: 4,
            title: _loginUserLanguageResource.remark,
            value: node.remark
        });
    }

    // 创建 Helper
    protocolConfigAlarmUnitPropertiesHandsontableHelper =ProtocolConfigAlarmUnitPropertiesHandsontableHelper.createNew('alarmUnitPropertiesContainer');

    // 设置列头
    var colHeaders = [
        _loginUserLanguageResource.idx,
        _loginUserLanguageResource.variable,
        _loginUserLanguageResource.value
    ];
    var columns = [
        { data: 'id' },
        { data: 'title' },
        { data: 'value' }
    ];

    protocolConfigAlarmUnitPropertiesHandsontableHelper.colHeaders = colHeaders;
    protocolConfigAlarmUnitPropertiesHandsontableHelper.columns = columns;
    protocolConfigAlarmUnitPropertiesHandsontableHelper.classes = classes;
    protocolConfigAlarmUnitPropertiesHandsontableHelper.createTable(root);
}

// ================================================================
// 5. 加载报警单元配置（按激活的报警类型子标签分发）
// ================================================================
function loadAlarmUnitConfig(node) {
    if (!node || node.classes !== 3) return;

    var subTabs = mini.get('alarmConfigSubTabs');
    if (!subTabs) return;

    var activeSub = subTabs.getActiveTab();
    if (!activeSub) return;
    var subName = activeSub.name; // fes / comm / run / numeric / enum / switching

    var protocol = node.protocol || '';
    var classes = node.classes || 3;
    var code = node.code || '';
    var calculateType = node.calculateType !== undefined ? node.calculateType : 0;

    switch (subName) {
        case 'fes':
            loadAlarmFESConfig(protocol, classes, code, calculateType);
            break;
        case 'comm':
            loadAlarmCommConfig(protocol, classes, code);
            break;
        case 'run':
            loadAlarmRunConfig(protocol, classes, code);
            break;
        case 'numeric':
            loadAlarmNumericConfig(protocol, classes, code, calculateType);
            break;
        case 'enum':
            loadAlarmEnumConfig(protocol, classes, code, calculateType);
            break;
        case 'switching':
            loadAlarmSwitchConfig(protocol, classes, code, calculateType);
            break;
    }
}

// ================================================================
// 6. 各报警类型配置加载
// ================================================================
function loadAlarmFESConfig(protocolCode, classes, code, calculateType) {
	if (protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper) {
        if (protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot) protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.hot.destroy();
        protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = null;
    }
    var container = document.getElementById('alarmFESTableDiv_id');
    if (!container) return;

    var mask = mini.mask({ el: 'alarmFESTableDiv_id', html: _loginUserLanguageResource.loadingData });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getModbusProtocolFESDiagramConditionsAlarmItemsConfigData',
        data: { protocolCode: protocolCode, classes: classes, code: code, calculateType: calculateType },
        dataType: 'json',
        success: function(result) {
            mini.unmask('alarmFESTableDiv_id');
            var data = result.totalRoot || [];
            if (data.length === 0) { for (var i = 0; i < 30; i++) data.push({}); }

            protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = ProtocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.createNew('alarmFESTableDiv_id');

            var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
                _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
                _loginUserLanguageResource.alarmLevel,
                _loginUserLanguageResource.isSendMessage, _loginUserLanguageResource.isSendEmail,
                _loginUserLanguageResource.code];
            var columns = [
                { data: 'checked', type: 'checkbox' },
                { data: 'id' },
                { data: 'title' },
                { data: 'delay', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper); } },
                { data: 'retriggerTime', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper); } },
                { data: 'alarmLevel', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.normal, _loginUserLanguageResource.alarmLevel1, _loginUserLanguageResource.alarmLevel2, _loginUserLanguageResource.alarmLevel3] },
                { data: 'isSendMessage', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'isSendMail', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'code' }
            ];
            protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.colHeaders = colHeaders;
            protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.columns = columns;
            protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper.createTable(data);
        },
        error: function() { mini.unmask('alarmFESTableDiv_id'); mini.alert(_loginUserLanguageResource.requestFailed); }
    });
}
function loadAlarmCommConfig(protocolName, classes, code) {
	if (protocolAlarmUnitConfigCommStatusItemsHandsontableHelper) {
        if (protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot) protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.destroy();
        protocolAlarmUnitConfigCommStatusItemsHandsontableHelper = null;
    }
    var container = document.getElementById('alarmCommTableDiv_id');
    if (!container) return;

    var mask = mini.mask({ el: 'alarmCommTableDiv_id', html: _loginUserLanguageResource.loadingData });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getModbusProtocolCommStatusAlarmItemsConfigData',
        data: { protocolName: protocolName, classes: classes, code: code },
        dataType: 'json',
        success: function(result) {
            mini.unmask('alarmCommTableDiv_id');
            var data = result.totalRoot || [];
            if (data.length === 0) { for (var i = 0; i < 30; i++) data.push({}); }

            protocolAlarmUnitConfigCommStatusItemsHandsontableHelper = ProtocolAlarmUnitConfigCommStatusItemsHandsontableHelper.createNew('alarmCommTableDiv_id');

            var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
                _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
                _loginUserLanguageResource.alarmLevel,
                _loginUserLanguageResource.isSendMessage, _loginUserLanguageResource.isSendEmail,
                _loginUserLanguageResource.code, _loginUserLanguageResource.value];
            var columns = [
                { data: 'checked', type: 'checkbox' },
                { data: 'id' },
                { data: 'title' },
                { data: 'delay', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigCommStatusItemsHandsontableHelper); } },
                { data: 'retriggerTime', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigCommStatusItemsHandsontableHelper); } },
                { data: 'alarmLevel', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.normal, _loginUserLanguageResource.alarmLevel1, _loginUserLanguageResource.alarmLevel2, _loginUserLanguageResource.alarmLevel3] },
                { data: 'isSendMessage', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'isSendMail', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'code' },
                { data: 'value' }
            ];
            protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.colHeaders = colHeaders;
            protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.columns = columns;
            protocolAlarmUnitConfigCommStatusItemsHandsontableHelper.createTable(data);
        },
        error: function() { mini.unmask('alarmCommTableDiv_id'); mini.alert(_loginUserLanguageResource.requestFailed); }
    });
}
function loadAlarmRunConfig(protocolName, classes, code) {
	if (protocolAlarmUnitConfigRunStatusItemsHandsontableHelper) {
        if (protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot) protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.destroy();
        protocolAlarmUnitConfigRunStatusItemsHandsontableHelper = null;
    }
    var container = document.getElementById('alarmRunTableDiv_id');
    if (!container) return;

    var mask = mini.mask({ el: 'alarmRunTableDiv_id', html: _loginUserLanguageResource.loadingData });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getModbusProtocolRunStatusAlarmItemsConfigData',
        data: { protocolName: protocolName, classes: classes, code: code },
        dataType: 'json',
        success: function(result) {
            mini.unmask('alarmRunTableDiv_id');
            var data = result.totalRoot || [];
            if (data.length === 0) { for (var i = 0; i < 30; i++) data.push({}); }

            protocolAlarmUnitConfigRunStatusItemsHandsontableHelper = ProtocolAlarmUnitConfigRunStatusItemsHandsontableHelper.createNew('alarmRunTableDiv_id');

            var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
                _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
                _loginUserLanguageResource.alarmLevel,
                _loginUserLanguageResource.isSendMessage, _loginUserLanguageResource.isSendEmail,
                _loginUserLanguageResource.code, _loginUserLanguageResource.value];
            var columns = [
                { data: 'checked', type: 'checkbox' },
                { data: 'id' },
                { data: 'title' },
                { data: 'delay', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigRunStatusItemsHandsontableHelper); } },
                { data: 'retriggerTime', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigRunStatusItemsHandsontableHelper); } },
                { data: 'alarmLevel', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.normal, _loginUserLanguageResource.alarmLevel1, _loginUserLanguageResource.alarmLevel2, _loginUserLanguageResource.alarmLevel3] },
                { data: 'isSendMessage', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'isSendMail', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'code' },
                { data: 'value' }
            ];
            protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.colHeaders = colHeaders;
            protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.columns = columns;
            protocolAlarmUnitConfigRunStatusItemsHandsontableHelper.createTable(data);
        },
        error: function() { mini.unmask('alarmRunTableDiv_id'); mini.alert(_loginUserLanguageResource.requestFailed); }
    });
}
function loadAlarmNumericConfig(protocolCode, classes, code, calculateType) {
	if (protocolAlarmUnitConfigNumItemsHandsontableHelper) {
        if (protocolAlarmUnitConfigNumItemsHandsontableHelper.hot) protocolAlarmUnitConfigNumItemsHandsontableHelper.hot.destroy();
        protocolAlarmUnitConfigNumItemsHandsontableHelper = null;
    }
    var container = document.getElementById('alarmNumericTableDiv_id');
    if (!container) return;

    var mask = mini.mask({ el: 'alarmNumericTableDiv_id', html: _loginUserLanguageResource.loadingData });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getModbusProtocolNumAlarmItemsConfigData',
        data: { protocolCode: protocolCode, classes: classes, code: code, calculateType: calculateType },
        dataType: 'json',
        success: function(result) {
            mini.unmask('alarmNumericTableDiv_id');
            var data = result.totalRoot || [];
            if (data.length === 0) { for (var i = 0; i < 30; i++) data.push({}); }

            protocolAlarmUnitConfigNumItemsHandsontableHelper = ProtocolAlarmUnitConfigNumItemsHandsontableHelper.createNew('alarmNumericTableDiv_id');

            var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name,
                _loginUserLanguageResource.unit, _loginUserLanguageResource.dataSource,
                _loginUserLanguageResource.upperLimit, _loginUserLanguageResource.lowerLimit, _loginUserLanguageResource.hystersis,
                _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
                _loginUserLanguageResource.alarmLevel,
                _loginUserLanguageResource.isSendMessage, _loginUserLanguageResource.isSendEmail];

            var columns = [
                { data: 'checked', type: 'checkbox' },
                { data: 'id' },
                { data: 'title' },
                { data: 'unit' },
                { data: 'dataSource' },
                { data: 'upperLimit', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigNumItemsHandsontableHelper); } },
                { data: 'lowerLimit', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigNumItemsHandsontableHelper); } },
                { data: 'hystersis', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigNumItemsHandsontableHelper); } },
                { data: 'delay', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigNumItemsHandsontableHelper); } },
                { data: 'retriggerTime', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigNumItemsHandsontableHelper); } },
                { data: 'alarmLevel', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.normal, _loginUserLanguageResource.alarmLevel1, _loginUserLanguageResource.alarmLevel2, _loginUserLanguageResource.alarmLevel3] },
                { data: 'isSendMessage', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'isSendMail', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'addr' },
                { data: 'code' },
                { data: 'type' }
            ];
            protocolAlarmUnitConfigNumItemsHandsontableHelper.colHeaders = colHeaders;
            protocolAlarmUnitConfigNumItemsHandsontableHelper.columns = columns;
            protocolAlarmUnitConfigNumItemsHandsontableHelper.createTable(data);
        },
        error: function() { mini.unmask('alarmNumericTableDiv_id'); mini.alert(_loginUserLanguageResource.requestFailed); }
    });
}
function loadAlarmEnumConfig(protocolCode, classes, unitCode, itemAddr, itemHighLowByte, itemTitle, itemCode) {
	// 1. 加载上方列表（枚举量项）
    loadAlarmEnumOrSwitchItemsGrid('enum', protocolCode,unitCode, 1, function() {
        // 2. 选中行后加载下方 Handsontable 由 grid 选中事件处理
    });
}
function loadAlarmSwitchConfig(protocolCode, classes, unitCode, itemAddr, itemHighLowByte, itemTitle, itemCode) {
	loadAlarmEnumOrSwitchItemsGrid('switch', protocolCode,unitCode, 0, function() {
        // 详情由 grid 选中事件处理
    });
}

/**
 * 加载枚举量/开关量列表（上方 mini-datagrid）
 * @param {string} type 'enum' | 'switch'
 * @param {string} protocolCode 协议编码
 * @param {number} resolutionMode 1（枚举量）/ 0（开关量）
 * @param {function} callback 加载完成后的回调
 */
function loadAlarmEnumOrSwitchItemsGrid(type, protocolCode, unitCode, resolutionMode, callback) {
    var gridId = (type === 'enum') ? 'alarmEnumItemsGrid' : 'alarmSwitchItemsGrid';
    var grid = mini.get(gridId);
    if (!grid) return;

    // ★ 每次调用都更新参数，供事件处理器读取（不重新绑定事件）
    grid._alarmType = type;
    grid._alarmProtocolCode = protocolCode;
    grid._alarmUnitCode = unitCode;
    grid._alarmResolutionMode = resolutionMode;

    // ★ 只在首次调用时绑定事件，避免重复绑定
    if (!grid._alarmEventsBound) {
        grid._alarmEventsBound = true;

        // 设置 URL 和基础属性
        grid.setUrl(context + '/acquisitionUnitManagerController/getProtocolEnumOrSwitchItemsConfigData');
        grid.set({
            idField: 'id',
            dataField: 'totalRoot',
            totalField: 'totalCount',
            showPager: false,
            style: 'width:100%;height:100%;'
        });

        // load 事件：动态设置列 + 默认选中第一行
        grid.on('load', function(e) {
            var rawData = e.result;
            var data = e.data;
            var columnsData = (rawData && rawData.columns) ? rawData.columns : [];
            if (columnsData.length > 0) {
                grid.setColumns(convertToMiniuiColumns(columnsData));
            }
            // 默认选中第一行（会触发 selectionchanged，进而加载下方详情）
            if (data && data.length > 0) {
                grid.select(0);
            }
        });

        // selectionchanged：根据当前参数加载下方详情
        grid.on('selectionchanged', function(e) {
            var records = grid.getSelecteds();
            if (!records || records.length === 0) return;
            var record = records[0];

            // ★ 从 grid 上读当前参数（每次调用 loadAlarmEnumOrSwitchItemsGrid 时已被更新）
            var curType = grid._alarmType;
            var curProtocolCode = grid._alarmProtocolCode;
            var curUnitCode = grid._alarmUnitCode;

            if (curType === 'enum') {
                loadAlarmEnumItemsDetail(curProtocolCode, 1, curUnitCode, record);
            } else {
                loadAlarmSwitchItemsDetail(curProtocolCode, 0, curUnitCode, record);
            }
        });

        // beforeload：从 grid 上读参数注入请求
        grid.on('beforeload', function(e) {
            var params = e.params || {};
            params.protocolCode = grid._alarmProtocolCode;
            params.resolutionMode = grid._alarmResolutionMode;
            e.params = params;
        });
    }

    // ★ 触发加载（此时参数已更新，事件处理器读到的就是最新参数）
    grid.load();
}

/**
 * 将 ExtJS 的 columns 数据转成 miniui datagrid 的列
 */
function convertToMiniuiColumns(columnsData) {
    var cols = [];
    for (var i = 0; i < columnsData.length; i++) {
        var c = columnsData[i];
        var col = {
            field: c.dataIndex,
            header: c.header,
            headerAlign: 'center',
            align: 'center'
        };
        if (c.width) col.width = c.width;
        if (c.hidden) col.visible = false;
        
        if (col.dataIndex === 'id') {
            column.type = 'indexcolumn';
            column.width = 50;
            column.header = _loginUserLanguageResource.idx;
            delete column.field;
        }
        
        cols.push(col);
    }
    return cols;
}

/**
 * 加载枚举量报警详情（下方 Handsontable）
 */
function loadAlarmEnumItemsDetail(protocolCode, resolutionMode, unitCode, record) {
    if (protocolAlarmUnitConfigEnumItemsHandsontableHelper) {
        if (protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot) protocolAlarmUnitConfigEnumItemsHandsontableHelper.hot.destroy();
        protocolAlarmUnitConfigEnumItemsHandsontableHelper = null;
    }
    var container = document.getElementById('alarmEnumTableDiv_id');
    if (!container) return;

    var itemAddr = record.addr || '';
    var itemHighLowByte = record.highLowByte || '';
    var itemTitle = record.title || '';
    var itemCode = record.itemCode || '';

    var mask = mini.mask({ el: 'alarmEnumTableDiv_id', html: _loginUserLanguageResource.loadingData });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getModbusProtocolEnumAlarmItemsConfigData',
        data: {
            protocolCode: protocolCode,
            classes: 3,
            unitCode: unitCode,
            itemAddr: itemAddr,
            itemHighLowByte: itemHighLowByte,
            itemResolutionMode: resolutionMode,
            itemTitle: itemTitle,
            itemCode: itemCode
        },
        dataType: 'json',
        success: function(result) {
            mini.unmask('alarmEnumTableDiv_id');
            var data = result.totalRoot || [];
            if (data.length === 0) { for (var i = 0; i < 30; i++) data.push({}); }

            protocolAlarmUnitConfigEnumItemsHandsontableHelper = ProtocolAlarmUnitConfigEnumItemsHandsontableHelper.createNew('alarmEnumTableDiv_id');

            var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.value, _loginUserLanguageResource.meaning,
                _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
                _loginUserLanguageResource.alarmLevel,
                _loginUserLanguageResource.isSendMessage, _loginUserLanguageResource.isSendEmail];
            var columns = [
                { data: 'checked', type: 'checkbox' },
                { data: 'id' },
                { data: 'value', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num(val, cb, this.row, this.col, protocolAlarmUnitConfigEnumItemsHandsontableHelper); } },
                { data: 'meaning' },
                { data: 'delay', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigEnumItemsHandsontableHelper); } },
                { data: 'retriggerTime', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigEnumItemsHandsontableHelper); } },
                { data: 'alarmLevel', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.normal, _loginUserLanguageResource.alarmLevel1, _loginUserLanguageResource.alarmLevel2, _loginUserLanguageResource.alarmLevel3] },
                { data: 'isSendMessage', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'isSendMail', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] }
            ];
            protocolAlarmUnitConfigEnumItemsHandsontableHelper.colHeaders = colHeaders;
            protocolAlarmUnitConfigEnumItemsHandsontableHelper.columns = columns;
            protocolAlarmUnitConfigEnumItemsHandsontableHelper.createTable(data);
        },
        error: function() { mini.unmask('alarmEnumTableDiv_id'); mini.alert(_loginUserLanguageResource.requestFailed); }
    });
}

function loadAlarmSwitchItemsDetail(protocolCode, resolutionMode, unitCode, record) {
    if (protocolAlarmUnitConfigSwitchItemsHandsontableHelper) {
        if (protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot) protocolAlarmUnitConfigSwitchItemsHandsontableHelper.hot.destroy();
        protocolAlarmUnitConfigSwitchItemsHandsontableHelper = null;
    }
    var container = document.getElementById('alarmSwitchTableDiv_id');
    if (!container) return;

    var itemAddr = record.addr || '';
    var itemHighLowByte = record.highLowByte || '';
    var itemTitle = record.title || '';
    var itemCode = record.itemCode || '';

    var mask = mini.mask({ el: 'alarmSwitchTableDiv_id', html: _loginUserLanguageResource.loadingData });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getModbusProtocolEnumAlarmItemsConfigData',
        data: {
            protocolCode: protocolCode,
            classes: 3,
            unitCode: unitCode,
            itemAddr: itemAddr,
            itemHighLowByte: itemHighLowByte,
            itemResolutionMode: resolutionMode,
            itemTitle: itemTitle,
            itemCode: itemCode
        },
        dataType: 'json',
        success: function(result) {
            mini.unmask('alarmSwitchTableDiv_id');
            var data = result.totalRoot || [];
            if (data.length === 0) { for (var i = 0; i < 30; i++) data.push({}); }

            protocolAlarmUnitConfigSwitchItemsHandsontableHelper = ProtocolAlarmUnitConfigSwitchItemsHandsontableHelper.createNew('alarmSwitchTableDiv_id');

            var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.bit, _loginUserLanguageResource.meaning,
                _loginUserLanguageResource.switchItemAlarmValue,
                _loginUserLanguageResource.delay + '(s)', _loginUserLanguageResource.retriggerTime + '(s)',
                _loginUserLanguageResource.alarmLevel,
                _loginUserLanguageResource.isSendMessage, _loginUserLanguageResource.isSendEmail];
            var columns = [
                { data: 'checked', type: 'checkbox' },
                { data: 'id' },
                { data: 'bitIndex', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num(val, cb, this.row, this.col, protocolAlarmUnitConfigSwitchItemsHandsontableHelper); } },
                { data: 'meaning' },
                { data: 'value', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.switchingOpenValue, _loginUserLanguageResource.switchingCloseValue] },
                { data: 'delay', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigSwitchItemsHandsontableHelper); } },
                { data: 'retriggerTime', type: 'text', validator: function(val, cb) { return handsontableDataCheck_Num_Nullable(val, cb, this.row, this.col, protocolAlarmUnitConfigSwitchItemsHandsontableHelper); } },
                { data: 'alarmLevel', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.normal, _loginUserLanguageResource.alarmLevel1, _loginUserLanguageResource.alarmLevel2, _loginUserLanguageResource.alarmLevel3] },
                { data: 'isSendMessage', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'isSendMail', type: 'dropdown', strict: true, allowInvalid: false, source: [_loginUserLanguageResource.yes, _loginUserLanguageResource.no] },
                { data: 'status0' },
                { data: 'status1' }
            ];
            protocolAlarmUnitConfigSwitchItemsHandsontableHelper.colHeaders = colHeaders;
            protocolAlarmUnitConfigSwitchItemsHandsontableHelper.columns = columns;
            protocolAlarmUnitConfigSwitchItemsHandsontableHelper.createTable(data);
        },
        error: function() { mini.unmask('alarmSwitchTableDiv_id'); mini.alert(_loginUserLanguageResource.requestFailed); }
    });
}


//================================================================
//1. 数值量报警 Helper + 加载
//================================================================
var ProtocolAlarmUnitConfigNumItemsHandsontableHelper = {
 createNew: function(divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];

     helper.addCellStyle = _alarmMakeCellStyle();
     helper.addReadOnlyBg = _alarmMakeReadOnlyBg(helper);

     helper.createTable = function(data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: (typeof _emailEnable !== 'undefined' && _emailEnable ? [13, 14, 15] : [11, 12, 13, 14, 15]),
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [40, 50, 120, 80, 80, 80, 80, 80, 100, 100, 120, 120, 120, 80, 80, 80],
             columns: helper.columns,
             width: '100%',
             height: '100%',
             fixedColumnsStart: 3,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
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
             cells: function(row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }
                 // 单元节点：仅允许编辑第 5 列之后的字段（1~4 列只读）
                 if (visualColIndex >= 1 && visualColIndex <= 4) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                 } else {
                     if (helper.columns[visualColIndex].type !== 'dropdown' && helper.columns[visualColIndex].type !== 'checkbox') {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             beforeChange: function(changes, source) {
                 if (!changes) return true;
                 if (!editFlag) return false;
                 return true;
             },
             afterOnCellMouseOver: _alarmMakeMouseOver(helper)
         });
     };
     return helper;
 }
};

//================================================================
//2. 工况诊断报警（FES）Helper + 加载
//================================================================
var ProtocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper = {
 createNew: function(divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];
     helper.addCellStyle = _alarmMakeCellStyle();
     helper.addReadOnlyBg = _alarmMakeReadOnlyBg(helper);

     helper.createTable = function(data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: (typeof emailConfig !== 'undefined' && emailConfig.enable ? [8] : [6, 7, 8]),
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [40, 50, 80, 100, 100, 120, 120, 120],
             columns: helper.columns,
             width: '100%',
             height: '100%',
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
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
             cells: function(row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }
                 if (visualColIndex >= 1 && visualColIndex <= 2) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                 } else {
                     if (helper.columns[visualColIndex].type !== 'dropdown' && helper.columns[visualColIndex].type !== 'checkbox') {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             beforeChange: function(changes, source) {
                 if (!changes) return true;
                 if (!editFlag) return false;
                 return true;
             },
             afterOnCellMouseOver: _alarmMakeMouseOver(helper)
         });
     };
     return helper;
 }
};

//================================================================
//3. 通信状态报警 Helper + 加载
//================================================================
var ProtocolAlarmUnitConfigCommStatusItemsHandsontableHelper = {
 createNew: function(divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];
     helper.addCellStyle = _alarmMakeCellStyle();
     helper.addReadOnlyBg = _alarmMakeReadOnlyBg(helper);

     helper.createTable = function(data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: (typeof emailConfig !== 'undefined' && emailConfig.enable ? [8, 9] : [6, 7, 8, 9]),
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [40, 50, 80, 100, 100, 120, 120, 120],
             columns: helper.columns,
             width: '100%',
             height: '100%',
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
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
             cells: function(row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }
                 if (visualColIndex >= 1 && visualColIndex <= 2) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                 } else {
                     if (helper.columns[visualColIndex].type !== 'dropdown' && helper.columns[visualColIndex].type !== 'checkbox') {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             beforeChange: function(changes, source) {
                 if (!changes) return true;
                 if (!editFlag) return false;
                 return true;
             },
             afterOnCellMouseOver: _alarmMakeMouseOver(helper)
         });
     };
     return helper;
 }
};

//================================================================
//4. 运行状态报警 Helper + 加载
//================================================================
var ProtocolAlarmUnitConfigRunStatusItemsHandsontableHelper = {
 createNew: function(divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];
     helper.addCellStyle = _alarmMakeCellStyle();
     helper.addReadOnlyBg = _alarmMakeReadOnlyBg(helper);

     helper.createTable = function(data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: (typeof emailConfig !== 'undefined' && emailConfig.enable ? [8, 9] : [6, 7, 8, 9]),
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [40, 50, 80, 100, 100, 120, 120, 120],
             columns: helper.columns,
             width: '100%',
             height: '100%',
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
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
             cells: function(row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }
                 if (visualColIndex >= 1 && visualColIndex <= 2) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                 } else {
                     if (helper.columns[visualColIndex].type !== 'dropdown' && helper.columns[visualColIndex].type !== 'checkbox') {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             beforeChange: function(changes, source) {
                 if (!changes) return true;
                 if (!editFlag) return false;
                 return true;
             },
             afterOnCellMouseOver: _alarmMakeMouseOver(helper)
         });
     };
     return helper;
 }
};

//================================================================
//5. 枚举量报警 Helper + 加载（上下布局：上 grid，下 Handsontable）
//================================================================
var ProtocolAlarmUnitConfigEnumItemsHandsontableHelper = {
 createNew: function(divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];
     helper.addCellStyle = _alarmMakeCellStyle();
     helper.addReadOnlyBg = _alarmMakeReadOnlyBg(helper);

     helper.createTable = function(data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: (typeof emailConfig !== 'undefined' && emailConfig.enable ? [] : [7, 8]),
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [40, 50, 50, 120, 100, 100, 120, 120, 120],
             columns: helper.columns,
             width: '100%',
             height: '100%',
             fixedColumnsStart: 4,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
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
             cells: function(row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }
                 if (visualColIndex >= 1 && visualColIndex <= 3) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                 } else {
                     if (helper.columns[visualColIndex].type !== 'dropdown' && helper.columns[visualColIndex].type !== 'checkbox') {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             beforeChange: function(changes, source) {
                 if (!changes) return true;
                 if (!editFlag) return false;
                 return true;
             },
             afterOnCellMouseOver: _alarmMakeMouseOver(helper)
         });
     };
     return helper;
 }
};

//================================================================
//6. 开关量报警 Helper + 加载（上下布局）
//================================================================
var ProtocolAlarmUnitConfigSwitchItemsHandsontableHelper = {
 createNew: function(divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];
     helper.addCellStyle = _alarmMakeCellStyle();
     helper.addReadOnlyBg = _alarmMakeReadOnlyBg(helper);

     helper.createTable = function(data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: (typeof emailConfig !== 'undefined' && emailConfig.enable ? [10, 11] : [8, 9, 10, 11]),
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [40, 50, 50, 120, 150, 100, 100, 120, 120, 120],
             columns: helper.columns,
             width: '100%',
             height: '100%',
             fixedColumnsStart: 4,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
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
             cells: function(row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }
                 if (prop === 'value') {
                     var status0 = this.instance.getDataAtRowProp(row, 'status0');
                     var status1 = this.instance.getDataAtRowProp(row, 'status1');
                     this.type = 'dropdown';
                     this.source = [status1, status0];
                     this.strict = true;
                     this.allowInvalid = false;
                 }
                 if (visualColIndex >= 1 && visualColIndex <= 3) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                 } else {
                     if (helper.columns[visualColIndex].type !== 'dropdown' && helper.columns[visualColIndex].type !== 'checkbox') {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             beforeChange: function(changes, source) {
                 if (!changes) return true;
                 if (!editFlag) return false;
                 return true;
             },
             afterOnCellMouseOver: _alarmMakeMouseOver(helper)
         });
     };
     return helper;
 }
};

// ================================================================
// 7. 刷新
// ================================================================
function refreshAlarmUnitProtocolTree() {
    var tree = mini.get('alarmUnitProtocolTree');
    if (tree) tree.load();
}

function refreshAlarmUnitList() {
    var tree = mini.get('alarmUnitList');
    if (tree) tree.load();
}
var ProtocolConfigAlarmUnitPropertiesHandsontableHelper = {
    createNew: function(divid) {
        var helper = {};
        helper.hot = null;
        helper.classes = null;
        helper.divid = divid;
        helper.validresult = true;
        helper.colHeaders = [];
        helper.columns = [];
        helper.AllData = [];

        helper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(245, 245, 245)';
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        helper.createTable = function(data) {
            $('#' + helper.divid).empty();
            var hotElement = document.querySelector('#' + helper.divid);

            helper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                theme: 'ht-theme-classic',
                data: data,
                colWidths: [1,4,5],
                columns: helper.columns,
                stretchH: 'all',
                width: '100%',
                height: '100%',
                autoWrapRow: true,
                rowHeaders: false,
                colHeaders: helper.colHeaders,
                columnSorting: true,
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
                cells: function(row, col, prop) {
                    var cellProperties = {};
                    var visualRowIndex = this.instance.toVisualRow(row);
                    var visualColIndex = this.instance.toVisualColumn(col);

                    // 权限控制
                    if (!editFlag) {
                        cellProperties.editor = false;
                        cellProperties.renderer = helper.addBoldBg;
                        return cellProperties;
                    }

                    // 根据 classes 控制编辑规则
                    if (helper.classes === 0 || helper.classes === 1) {
                        // 根/协议节点：只读
                        cellProperties.editor = false;
                        cellProperties.renderer = helper.addBoldBg;
                    } else if (helper.classes === 3) {
                        // 报警单元
                        if (visualColIndex === 0 || visualColIndex === 1) {
                            // 序号列、变量名列：只读
                            cellProperties.editor = false;
                            cellProperties.renderer = helper.addBoldBg;
                        } else {
                            // 值列（visualColIndex === 2）
                            if (visualRowIndex === 0) {
                                // 单元名称：非空校验
                                this.validator = function(val, callback) {
                                    return handsontableDataCheck_NotNull(val, callback, row, col, helper);
                                };
                                cellProperties.renderer = helper.addCellStyle;
                            } else if (visualRowIndex === 1) {
                                // 计算类型：下拉框
                                this.type = 'dropdown';
                                this.strict = true;
                                this.allowInvalid = false;
                                this.source = [
                                    _loginUserLanguageResource.nothing,
                                    _loginUserLanguageResource.SRPCalculate,
                                    _loginUserLanguageResource.PCPCalculate
                                ];
                                cellProperties.renderer = helper.addCellStyle;
                            } else if (visualRowIndex === 2) {
                                // 序号：可空数字校验
                                this.validator = function(val, callback) {
                                    return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
                                };
                                cellProperties.renderer = helper.addCellStyle;
                            } else {
                                // 备注：普通文本
                                cellProperties.renderer = helper.addCellStyle;
                            }
                        }
                    }
                    return cellProperties;
                },
                afterOnCellMouseOver: _alarmMakeMouseOver(helper)
            });
        };

        helper.saveData = function() {};
        helper.clearContainer = function() {
            helper.AllData = [];
        };
        return helper;
    }
};

function _alarmMakeReadOnlyBg(helper) {
    return function(instance, td, row, col, prop, value, cellProperties) {
        if (cellProperties.type === 'checkbox') {
            Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(245, 245, 245)';
        } else if (cellProperties.type === 'dropdown') {
            Handsontable.renderers.DropdownRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(245, 245, 245)';
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        } else {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(245, 245, 245)';
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        }
    };
}

function _alarmMakeCellStyle() {
    return function(instance, td, row, col, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);
        td.style.whiteSpace = 'nowrap';
        td.style.overflow = 'hidden';
        td.style.textOverflow = 'ellipsis';
    };
}

function _alarmMakeMouseOver(helper) {
    return function(event, coords, TD) {
        if (coords.col >= 0 && coords.row >= 0 && helper.columns[coords.col] && helper.columns[coords.col].type !== 'checkbox' &&
            helper.hot && helper.hot.getDataAtCell) {
            var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
            if (rawValue && rawValue.length > 0) {
                TD.title = rawValue;
            }
        }
    };
}

function alarmItemsSelectAll() {
    var helper = null;
    var subTabs = mini.get('alarmConfigSubTabs');
    if (!subTabs) return;
    var activeSub = subTabs.getActiveTab();
    if (!activeSub) return;
    var subName = activeSub.name;
    if(subName=='fes'){
    	helper = protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper;
    }else if(subName=='comm'){
    	helper = protocolAlarmUnitConfigCommStatusItemsHandsontableHelper;
    }else if(subName=='run'){
    	helper = protocolAlarmUnitConfigRunStatusItemsHandsontableHelper;
    }else if(subName=='numeric'){
    	helper = protocolAlarmUnitConfigNumItemsHandsontableHelper;
    }else if(subName=='enum'){
    	helper = protocolAlarmUnitConfigEnumItemsHandsontableHelper;
    }else if(subName=='switching'){
    	helper = protocolAlarmUnitConfigSwitchItemsHandsontableHelper;
    }
    
    
    if(helper!=null){
    	if (helper && helper.hot) {
            var rowCount = helper.hot.countRows();
            var updateData = [];
            for (var i = 0; i < rowCount; i++) {
                updateData.push([i, 'checked', true]);
            }
            helper.hot.setDataAtRowProp(updateData);
        }
    }
}

function alarmItemsDeselectAll() {
	var helper = null;
	var subTabs = mini.get('alarmConfigSubTabs');
    if (!subTabs) return;
    var activeSub = subTabs.getActiveTab();
    if (!activeSub) return;
    var subName = activeSub.name;
    if(subName=='fes'){
    	helper = protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper;
    }else if(subName=='comm'){
    	helper = protocolAlarmUnitConfigCommStatusItemsHandsontableHelper;
    }else if(subName=='run'){
    	helper = protocolAlarmUnitConfigRunStatusItemsHandsontableHelper;
    }else if(subName=='numeric'){
    	helper = protocolAlarmUnitConfigNumItemsHandsontableHelper;
    }else if(subName=='enum'){
    	helper = protocolAlarmUnitConfigEnumItemsHandsontableHelper;
    }else if(subName=='switching'){
    	helper = protocolAlarmUnitConfigSwitchItemsHandsontableHelper;
    }
    
    
    if(helper!=null){
    	if (helper && helper.hot) {
            var rowCount = helper.hot.countRows();
            var updateData = [];
            for (var i = 0; i < rowCount; i++) {
                updateData.push([i, 'checked', false]);
            }
            helper.hot.setDataAtRowProp(updateData);
        }
    }
}

function addAlarmUnitInfo() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedDeviceNode = deviceTree.getSelectedNode();
    if (!selectedDeviceNode) {
        return;
    }
    var deviceTypeIds = selectedDeviceTypeId || '';

    // 获取当前选中的报警协议节点（可能为协议或目录）
    var protocolTree = mini.get('alarmUnitProtocolTree');
    var selectedProtocolNode = protocolTree ? protocolTree.getSelectedNode() : null;
    var protocolList = '';
    if (selectedProtocolNode) {
        if (selectedProtocolNode.classes === 1) {
            protocolList = selectedProtocolNode.code || '';
        } else if (selectedProtocolNode.classes === 0) {
            // 目录节点：收集所有子协议节点的 code
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
        title: _loginUserLanguageResource.addAlarmUnit,
        url: context + '/miniui-app/modules/driverConfig/alarmUnitAddWindow.jsp',
        width: 450,
        height: 420,
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: deviceTypeIds,
                protocolList: protocolList
            });
            // 暴露刷新父页面报警单元列表树的函数
            contentWindow.parent._parentRefreshUnitTree = function() {
            	refreshAlarmUnitList();
            };
            // 暴露设置新增对象高亮的函数
            contentWindow.parent._parentSetNewObject = function(name, classes) {
                window._newAlarmUnitObjectName = name;
                window._newAlarmUnitObjectClasses = classes;
            };
        },
        ondestroy: function(action) {
            // 新增后已在子窗口回调中刷新树
        }
    });
}

//================================================================
//保存报警单元数据
//================================================================
function SaveModbusProtocolAlarmUnitConfigTreeData() {
 var tree = mini.get('alarmUnitList');
 if (!tree) return;

 var selectedNode = tree.getSelectedNode();
 if (!selectedNode) return;

 // 只对报警单元（classes === 3）保存
 if (selectedNode.classes !== 3) return;

 var tabs = mini.get('alarmUnitRightTabs');
 if (!tabs) return;
 var activeTab = tabs.getActiveTab();
 if (!activeTab) return;
 var activeName = activeTab.name; // 'props' 或 'config'

 if (activeName === 'props') {
     // 保存属性
     saveAlarmUnitProperties(tree, selectedNode);
 } else if (activeName === 'config') {
     // 保存配置：根据当前激活的报警类型子标签
     grantAlarmItemsPermission(tree, selectedNode);
 }
}

//================================================================
//保存报警单元属性
//================================================================
function saveAlarmUnitProperties(tree, node) {
 var helper = protocolConfigAlarmUnitPropertiesHandsontableHelper;
 if (!helper || !helper.hot) {
     mini.alert(_loginUserLanguageResource.noDataToSave);
     return;
 }

 var propertiesData = helper.hot.getData();
 // propertiesData 每行: [id, title, value]
 // 索引0: 单元名称
 // 索引1: 计算类型（显示文本）
 // 索引2: 排序
 // 索引3: 备注

 var unitName = propertiesData[0] && propertiesData[0][2] ? propertiesData[0][2] : '';
 var calcTypeText = propertiesData[1] && propertiesData[1][2] ? propertiesData[1][2] : '';
 var sort = propertiesData[2] && propertiesData[2][2] ? propertiesData[2][2] : '';
 var remark = propertiesData[3] && propertiesData[3][2] ? propertiesData[3][2] : '';

 // 计算类型文本转数字
 var calculateType = 0;
 if (calcTypeText === _loginUserLanguageResource.SRPCalculate) {
     calculateType = 1;
 } else if (calcTypeText === _loginUserLanguageResource.PCPCalculate) {
     calculateType = 2;
 }

 var saveData = {
     id: node.id,
     unitCode: node.code || '',
     unitName: unitName,
     oldUnitName: node.text || '',
     protocol: node.protocol || '',
     calculateType: calculateType,
     sort: sort,
     remark: remark
 };

 SaveModbusProtocolAlarmUnitConfigData(saveData);
}

//================================================================
//保存报警单元属性到后端
//================================================================
function SaveModbusProtocolAlarmUnitConfigData(saveData) {
 var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.updateWait });
 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/saveModbusProtocolAlarmUnitData',
     data: { data: JSON.stringify(saveData) },
     dataType: 'json',
     success: function(response) {
         mini.unmask(document.body);
         if (response.success) {
             if (saveData.delidslist && saveData.delidslist.length > 0) {
                 _selectedAlarmUnitId = null;
                 _selectedAlarmUnitClasses = null;
                 mini.alert(_loginUserLanguageResource.deleteSuccessfully);
             } else {
                 mini.alert(_loginUserLanguageResource.savedSuccessfully);
             }
             refreshAlarmUnitList();
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
//================================================================
//7. 右键菜单事件
//================================================================

function onAlarmUnitTreeBeforeMenu(e) {
 var tree = mini.get('alarmUnitList');
 var menu = e.sender;
 var node = tree.getSelectedNode();
 if (!node || node.classes !== 3) {
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
function deleteAlarmUnitNode(e) {
var tree = mini.get('alarmUnitList');
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
           SaveModbusProtocolAlarmUnitConfigData(deleteData);
       }
   }
);
}

//================================================================
//保存报警项配置（配置 Tab 下各报警类型）
//================================================================
function grantAlarmItemsPermission(tree, node) {
 var subTabs = mini.get('alarmConfigSubTabs');
 if (!subTabs) return;
 var activeSub = subTabs.getActiveTab();
 if (!activeSub) return;
 var subName = activeSub.name; // fes / comm / run / numeric / enum / switching

 var saveData = {
     id: node.id,
     unitCode: node.code || '',
     unitName: node.text || '',
     oldUnitName: node.text || '',
     protocol: node.protocol || '',
     alarmItems: []
 };

 // 根据报警类型收集数据
 var helper = null;

 if (subName === 'numeric') {
     // 数值量
     helper = protocolAlarmUnitConfigNumItemsHandsontableHelper;
     saveData.resolutionMode = 2;
     collectNumericItems(helper, saveData);
 } else if (subName === 'switching') {
     // 开关量
     helper = protocolAlarmUnitConfigSwitchItemsHandsontableHelper;
     saveData.resolutionMode = 0;
     collectSwitchItems(tree, helper, saveData);
 } else if (subName === 'enum') {
     // 枚举量
     helper = protocolAlarmUnitConfigEnumItemsHandsontableHelper;
     saveData.resolutionMode = 1;
     collectEnumItems(tree, helper, saveData);
 } else if (subName === 'comm') {
     // 通信状态
     helper = protocolAlarmUnitConfigCommStatusItemsHandsontableHelper;
     saveData.resolutionMode = 3;
     collectCommStatusItems(helper, saveData);
 } else if (subName === 'run') {
     // 运行状态
     helper = protocolAlarmUnitConfigRunStatusItemsHandsontableHelper;
     saveData.resolutionMode = 6;
     collectRunStatusItems(helper, saveData);
 } else if (subName === 'fes') {
     // 功图工况
     helper = protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper;
     saveData.resolutionMode = 4;
     collectFESItems(helper, saveData);
 }

 if (!helper || !helper.hot) {
     mini.alert(_loginUserLanguageResource.noDataToSave);
     return;
 }

 // 提交
 grantAlarmItemsToPermission(saveData);
}

//================================================================
//各报警类型的数据收集
//================================================================

//数值量
function collectNumericItems(helper, saveData) {
 var data = helper.hot.getData();
 for (var i = 0; i < data.length; i++) {
     var item = {};
     item.alarmSign = helper.hot.getDataAtRowProp(i, 'checked') ? 1 : 0;
     item.itemName = helper.hot.getDataAtRowProp(i, 'title');
     item.upperLimit = helper.hot.getDataAtRowProp(i, 'upperLimit');
     item.lowerLimit = helper.hot.getDataAtRowProp(i, 'lowerLimit');
     item.hystersis = helper.hot.getDataAtRowProp(i, 'hystersis');
     item.delay = helper.hot.getDataAtRowProp(i, 'delay');
     item.retriggerTime = helper.hot.getDataAtRowProp(i, 'retriggerTime');
     item.alarmLevel = helper.hot.getDataAtRowProp(i, 'alarmLevel');
     item.isSendMessage = helper.hot.getDataAtRowProp(i, 'isSendMessage');
     item.isSendMail = helper.hot.getDataAtRowProp(i, 'isSendMail');
     item.itemCode = helper.hot.getDataAtRowProp(i, 'code');
     item.type = helper.hot.getDataAtRowProp(i, 'type');
     if (item.type == 2) {
         item.itemAddr = helper.hot.getDataAtRowProp(i, 'addr');
     }
     if (item.alarmSign == 1
             || isNotVal(item.upperLimit)
             || isNotVal(item.lowerLimit)
             || isNotVal(item.hystersis)
             || isNotVal(item.delay)
             || isNotVal(item.retriggerTime)
             || isNotVal(item.alarmLevel)
             || isNotVal(item.isSendMessage)
             || isNotVal(item.isSendMail)) {
         saveData.alarmItems.push(item);
     }
 }
}

//开关量
function collectSwitchItems(tree, helper, saveData) {
 // 从上方 grid 获取当前选中项
 var grid = mini.get('alarmSwitchItemsGrid');
 var record = null;
 if (grid) {
     var records = grid.getSelecteds();
     if (records && records.length > 0) record = records[0];
 }

 if (!record) {
     // 没有选中报警项，不能保存
     return;
 }

 saveData.alarmItemName = record.title || '';
 saveData.alarmItemAddr = record.addr || '';
 saveData.alarmItemCode = record.itemCode || '';
 saveData.alarmItemHighLowByte = record.highLowByte || '';

 var data = helper.hot.getData();
 for (var i = 0; i < data.length; i++) {
     var item = {};
     item.bitIndex = helper.hot.getDataAtRowProp(i, 'bitIndex');
     item.itemName = record.title || '';
     item.itemAddr = record.addr || '';
     item.itemCode = record.itemCode || '';
     // 开关量 0/1 状态
     var value0 = helper.hot.getDataAtRowProp(i, 'status0');
     var value1 = helper.hot.getDataAtRowProp(i, 'status1');
     var rowValue = helper.hot.getDataAtRowProp(i, 'value');
     if (value1 != null && rowValue == value1) {
         item.value = 1;
     } else {
         item.value = 0;
     }

     item.alarmSign = helper.hot.getDataAtRowProp(i, 'checked') ? 1 : 0;
     item.delay = helper.hot.getDataAtRowProp(i, 'delay');
     item.retriggerTime = helper.hot.getDataAtRowProp(i, 'retriggerTime');
     item.alarmLevel = helper.hot.getDataAtRowProp(i, 'alarmLevel');
     item.isSendMessage = helper.hot.getDataAtRowProp(i, 'isSendMessage');
     item.isSendMail = helper.hot.getDataAtRowProp(i, 'isSendMail');
     item.type = saveData.resolutionMode;

     if (item.alarmSign == 1
             || isNotVal(item.delay)
             || isNotVal(item.retriggerTime)
             || isNotVal(item.alarmLevel)
             || item.isSendMessage == _loginUserLanguageResource.yes
             || item.isSendMail == _loginUserLanguageResource.yes) {
         saveData.alarmItems.push(item);
     }
 }
}

//枚举量
function collectEnumItems(tree, helper, saveData) {
 var grid = mini.get('alarmEnumItemsGrid');
 var record = null;
 if (grid) {
     var records = grid.getSelecteds();
     if (records && records.length > 0) record = records[0];
 }

 if (!record) {
     return;
 }

 saveData.alarmItemName = record.title || '';
 saveData.alarmItemAddr = record.addr || '';
 saveData.alarmItemCode = record.itemCode || '';

 var data = helper.hot.getData();
 for (var i = 0; i < data.length; i++) {
     var item = {};
     item.itemName = record.title || '';
     item.itemAddr = record.addr || '';
     item.itemCode = record.itemCode || '';

     item.alarmSign = helper.hot.getDataAtRowProp(i, 'checked') ? 1 : 0;
     item.value = helper.hot.getDataAtRowProp(i, 'value');
     item.delay = helper.hot.getDataAtRowProp(i, 'delay');
     item.retriggerTime = helper.hot.getDataAtRowProp(i, 'retriggerTime');
     item.alarmLevel = helper.hot.getDataAtRowProp(i, 'alarmLevel');
     item.isSendMessage = helper.hot.getDataAtRowProp(i, 'isSendMessage');
     item.isSendMail = helper.hot.getDataAtRowProp(i, 'isSendMail');
     item.type = saveData.resolutionMode;

     if (item.alarmSign == 1
             || isNotVal(item.delay)
             || isNotVal(item.retriggerTime)
             || isNotVal(item.alarmLevel)
             || isNotVal(item.isSendMessage)
             || isNotVal(item.isSendMail)) {
         saveData.alarmItems.push(item);
     }
 }
}

//通信状态
function collectCommStatusItems(helper, saveData) {
 var data = helper.hot.getData();
 for (var i = 0; i < data.length; i++) {
     var item = {};
     item.alarmSign = helper.hot.getDataAtRowProp(i, 'checked') ? 1 : 0;
     item.itemName = helper.hot.getDataAtRowProp(i, 'title');
     item.delay = helper.hot.getDataAtRowProp(i, 'delay');
     item.retriggerTime = helper.hot.getDataAtRowProp(i, 'retriggerTime');
     item.alarmLevel = helper.hot.getDataAtRowProp(i, 'alarmLevel');
     item.isSendMessage = helper.hot.getDataAtRowProp(i, 'isSendMessage');
     item.isSendMail = helper.hot.getDataAtRowProp(i, 'isSendMail');
     item.itemCode = helper.hot.getDataAtRowProp(i, 'code');
     item.value = helper.hot.getDataAtRowProp(i, 'value');
     item.type = saveData.resolutionMode;

     if (item.alarmSign == 1
             || isNotVal(item.delay)
             || isNotVal(item.retriggerTime)
             || isNotVal(item.alarmLevel)
             || isNotVal(item.isSendMessage)
             || isNotVal(item.isSendMail)) {
         saveData.alarmItems.push(item);
     }
 }
}

//运行状态
function collectRunStatusItems(helper, saveData) {
 var data = helper.hot.getData();
 for (var i = 0; i < data.length; i++) {
     var item = {};
     item.alarmSign = helper.hot.getDataAtRowProp(i, 'checked') ? 1 : 0;
     item.itemName = helper.hot.getDataAtRowProp(i, 'title');
     item.delay = helper.hot.getDataAtRowProp(i, 'delay');
     item.retriggerTime = helper.hot.getDataAtRowProp(i, 'retriggerTime');
     item.alarmLevel = helper.hot.getDataAtRowProp(i, 'alarmLevel');
     item.isSendMessage = helper.hot.getDataAtRowProp(i, 'isSendMessage');
     item.isSendMail = helper.hot.getDataAtRowProp(i, 'isSendMail');
     item.itemCode = helper.hot.getDataAtRowProp(i, 'code');
     item.value = helper.hot.getDataAtRowProp(i, 'value');
     item.type = saveData.resolutionMode;

     if (item.alarmSign == 1
             || isNotVal(item.delay)
             || isNotVal(item.retriggerTime)
             || isNotVal(item.alarmLevel)
             || isNotVal(item.isSendMessage)
             || isNotVal(item.isSendMail)) {
         saveData.alarmItems.push(item);
     }
 }
}

//功图工况
function collectFESItems(helper, saveData) {
 var data = helper.hot.getData();
 for (var i = 0; i < data.length; i++) {
     var item = {};
     item.alarmSign = helper.hot.getDataAtRowProp(i, 'checked') ? 1 : 0;
     item.itemName = helper.hot.getDataAtRowProp(i, 'title');
     item.delay = helper.hot.getDataAtRowProp(i, 'delay');
     item.retriggerTime = helper.hot.getDataAtRowProp(i, 'retriggerTime');
     item.alarmLevel = helper.hot.getDataAtRowProp(i, 'alarmLevel');
     item.isSendMessage = helper.hot.getDataAtRowProp(i, 'isSendMessage');
     item.isSendMail = helper.hot.getDataAtRowProp(i, 'isSendMail');
     item.itemCode = helper.hot.getDataAtRowProp(i, 'code');
     item.value = helper.hot.getDataAtRowProp(i, 'code');
     item.type = saveData.resolutionMode;

     if (item.alarmSign == 1
             || isNotVal(item.delay)
             || isNotVal(item.retriggerTime)
             || isNotVal(item.alarmLevel)
             || isNotVal(item.isSendMessage)
             || isNotVal(item.isSendMail)) {
         saveData.alarmItems.push(item);
     }
 }
}

//================================================================
//提交报警项权限到后端
//================================================================
function grantAlarmItemsToPermission(saveData) {
 var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.updateWait });
 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/grantAlarmItemsToAlarmUnitPermission',
     data: { data: JSON.stringify(saveData) },
     dataType: 'json',
     success: function(response) {
         mini.unmask(document.body);
         if (response.success) {
             if (saveData.delidslist && saveData.delidslist.length > 0) {
                 _selectedAlarmUnitId = null;
                 _selectedAlarmUnitClasses = null;
                 mini.alert(_loginUserLanguageResource.deleteSuccessfully);
             } else {
                 mini.alert(_loginUserLanguageResource.savedSuccessfully);
             }
             refreshAlarmUnitList();
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

//================================================================
//打开导出报警单元窗口
//================================================================
function openExportAlarmUnitWindow() {
 var deviceTree = mini.get('deviceTypeTree');
 if (!deviceTree) {
     return;
 }
 var selectedNode = deviceTree.getSelectedNode();
 if (!selectedNode) {
     return;
 }
 var deviceTypeIds = selectedDeviceTypeId || '';

 mini.open({
     title: _loginUserLanguageResource.exportAlarmUnit,
     url: context + '/miniui-app/modules/driverConfig/exportAlarmUnitWindow.jsp',
     width: 420,
     height: 600,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function() {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;
         contentWindow.setData({
             deviceTypeIds: deviceTypeIds
         });
     }
 });
}

//================================================================
//打开导入报警单元窗口
//================================================================
function openImportAlarmUnitWindow() {
 var deviceTree = mini.get('deviceTypeTree');
 if (!deviceTree) return;
 var selectedNode = deviceTree.getSelectedNode();
 if (!selectedNode) return;
 var deviceTypeId = selectedNode.deviceTypeId;
 var deviceTypeName = getNodePath(deviceTree, selectedNode);

 mini.open({
     title: _loginUserLanguageResource.importAlarmUnit,
     url: context + '/miniui-app/modules/driverConfig/importAlarmUnitWindow.jsp',
     width: '90%',
     height: '80%',
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function() {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;
         contentWindow.setData({
             deviceTypeId: deviceTypeId,
             deviceTypeName: deviceTypeName
         });
         // 暴露刷新父页面报警单元列表树的函数
         contentWindow.parent.refreshAlarmUnitList = function() {
        	 refreshAlarmUnitList();
         };
     }
 });
}

//================================================================
//打开报警颜色配置窗口
//================================================================
function openAlarmColorSelectWindow() {
 mini.open({
     title: _loginUserLanguageResource.alarmColorConfig,
     url: context + '/miniui-app/modules/driverConfig/alarmColorSelectWindow.jsp',
     width: 700,
     height: 560,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function() {
         // 数据加载由子窗口内部完成
     }
 });
}