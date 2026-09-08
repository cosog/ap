//协议配置
var protocolItemsConfigHandsontableHelper = null;
var protocolPropertiesHandsontableHelper = null;
var protocolItemsMeaningConfigHandsontableHelper = null;
var protocolSwitchingValueBitStatusConfigHandsontableHelper = null;

var protocolExtendedFieldConfigHandsontableHelper = null;
var protocolExtendedFieldHighLowByteConfigHandsontableHelper = null;
var protocolExtendedFieldMeaningConfigHandsontableHelper = null;
var protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = null;

function loadProtocolTree() {
    var protocolTree = mini.get('protocolTree');
    if (protocolTree) {
        protocolTree.load();
    }
}

function onProtocolTreeBeforeLoad(e) {
    var params = e.params || {};
    var deviceTypeIds = selectedDeviceTypeId || '';
    params.deviceTypeIds = deviceTypeIds;
    e.params = params;
}

function onProtocolTreeLoad(e) {
    var tree = e.sender;
    var root = tree.getRootNode();
    if (!root) return;

    var protocolNodes = [];

    function collect(node) {
        if (node.children && node.children.length > 0) {
            for (var i = 0; i < node.children.length; i++) {
                collect(node.children[i]);
            }
        } else {
            if (node.classes == 1) {
                protocolNodes.push(node);
            }
        }
    }
    collect(root);

    if (protocolNodes.length > 0) {
        var targetNode = null;
        // 如果有新添加的协议名称，优先选中它
        if (window._newProtocolName) {
            for (var i = 0; i < protocolNodes.length; i++) {
                if (protocolNodes[i].text === window._newProtocolName) {
                    targetNode = protocolNodes[i];
                    break;
                }
            }
            // 清除标记，避免重复选中
            window._newProtocolName = null;
        }
        // 如果没有找到或没有新协议，默认选中第一个
        if (!targetNode) {
            targetNode = protocolNodes[0];
        }
        setTimeout(function() {
            tree.selectNode(targetNode);
        }, 50);
    }
}
function onProtocolNodeSelect(e) {
    var node = e.node;
    if (node && node.classes === 1) { // 协议节点
        // 显示信息标签
        //var infoLabel = document.getElementById('protocolInfoLabel');
        //if (infoLabel) {
        //    infoLabel.innerHTML = '【<font color="red">' + node.text + '</font>】';
        //}
        // 根据当前激活的子标签加载对应数据
        loadProtocolDetailData(node);
    }
}
function onProtocolSubTabChanged2(e) {
    if (isInitializing) return;
    if (selectedDeviceTypeId) loadDataForCurrentTab(selectedDeviceTypeId);
}

function onProtocolSubTabChanged(e) {
    if (isInitializing) return;
    // 获取当前选中的协议节点
    var protocolTree = mini.get('protocolTree');
    if (!protocolTree) return;
    var selectedNode = protocolTree.getSelectedNode();
    if (selectedNode && selectedNode.classes === 1) {
        // 直接根据选中的协议节点和当前子标签加载数据
        loadProtocolDetailData(selectedNode);
    } else {
        // 如果没有选中协议节点，可能需要清空或显示空状态
        // 可选：清空表格
    }
}
function onExtendedSubTabChanged(e) {
    if (isInitializing) return;
    var protocolTree = mini.get('protocolTree');
    if (!protocolTree) return;
    var selectedNode = protocolTree.getSelectedNode();
    if (selectedNode && selectedNode.classes === 1) {
        // 根据当前协议和扩展子标签重新加载
        var extSub = mini.get('extendedSubTabs');
        if (!extSub) return;
        var extActive = extSub.getActiveTab();
        if (!extActive) return;

        var protocolCode = selectedNode.code || '';
        var protocolName = selectedNode.text || '';

        if (extActive.name === 'numeric') {
            CreateProtocolExtendedFieldConfigInfoTable(protocolName, 1, protocolCode);
        } else if (extActive.name === 'highlow') {
            CreateProtocolExtendedFieldHighLowByteConfigInfoTable(protocolName, 1, protocolCode);
        }
    }
}
//================================================================
// 根据激活子标签加载数据
// ================================================================
function loadProtocolDetailData(node) {
    if (!node) return;
    var protoSub = mini.get('protocolSubTabs');
    if (!protoSub) return;
    var activeTab = protoSub.getActiveTab();
    if (!activeTab) return;
    var tabName = activeTab.name; // props, config, extended

    // 获取协议 code
    var protocolCode = node.code || '';
    var protocolName = node.text || '';

    if (tabName === 'props') {
        // 加载属性
        CreateProtocolConfigAddrMappingPropertiesInfoTable(node);
    } else if (tabName === 'config') {
        // 加载配置项
        CreateModbusProtocolAddrMappingItemsConfigInfoTable(protocolName, 1, protocolCode);
    } else if (tabName === 'extended') {
        // 加载扩展字段，根据扩展子标签决定
        var extSub = mini.get('extendedSubTabs');
        if (extSub) {
            var extActive = extSub.getActiveTab();
            if (extActive) {
                if (extActive.name === 'numeric') {
                    CreateProtocolExtendedFieldConfigInfoTable(protocolName, 1, protocolCode);
                } else if (extActive.name === 'highlow') {
                    CreateProtocolExtendedFieldHighLowByteConfigInfoTable(protocolName, 1, protocolCode);
                }
            }
        }
    }
}

function getCurrentProtocolCode() {
    var tree = mini.get('protocolTree');
    if (!tree) return '';
    var selected = tree.getSelectedNode();
    if (selected && selected.classes === 1) {
        return selected.code || '';
    }
    return '';
}

function CreateProtocolConfigAddrMappingPropertiesInfoTable(data) {
    var root = [];
    if (data.classes === 0) {
        root.push({
            id: 1,
            title: _loginUserLanguageResource.rootNode,
            value: _loginUserLanguageResource.protocolList
        });
    } else if (data.classes === 1) {
        root.push({
            id: 1,
            title: _loginUserLanguageResource.protocolName,
            value: data.text
        });
        root.push({
            id: 2,
            title: _loginUserLanguageResource.sequenceNumber,
            value: data.sort
        });
        root.push({
            id: 3,
            title: _loginUserLanguageResource.language,
            value: data.languageName
        });
        root.push({
            id: 4,
            title: _loginUserLanguageResource.protocolBelongTo,
            value: data.deviceTypeAllPath
        });
    }
    if (protocolPropertiesHandsontableHelper === null || protocolPropertiesHandsontableHelper.hot === undefined) {
        protocolPropertiesHandsontableHelper = ProtocolPropertiesHandsontableHelper.createNew("ModbusProtocolAddrMappingPropertiesTableInfoDiv_id");


        var colHeaders = [_loginUserLanguageResource.idx, _loginUserLanguageResource.variable, _loginUserLanguageResource.value];
        var columns = [{
            data: 'id'
        }, {
            data: 'title'
        }, {
            data: 'value'
        }];
        protocolPropertiesHandsontableHelper.colHeaders = colHeaders;
        protocolPropertiesHandsontableHelper.columns = columns;
        protocolPropertiesHandsontableHelper.classes = data.classes;
        protocolPropertiesHandsontableHelper.createTable(root);
    } else {
        protocolPropertiesHandsontableHelper.classes = data.classes;
        protocolPropertiesHandsontableHelper.hot.loadData(root);
        protocolPropertiesHandsontableHelper.hot.render();
    }
}

function CreateModbusProtocolAddrMappingItemsConfigInfoTable(protocolName, classes, code) {
	var divId='protocolSubTabs';
	var mask = mini.mask({
        el: divId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getProtocolItemsConfigData',
        data: {
            protocolName: protocolName,
            classes: classes,
            code: code
        },
        dataType: 'json',
        success: function(result) {
        	mini.unmask(divId);
            var dataLength = result.totalRoot.length;
            var defultNullData = [];
            var defultDataLength = 100;
            for (var i = 0; i < defultDataLength; i++) {
                defultNullData.push({});
            }
            var tableData = result.totalRoot;
            if (dataLength < defultDataLength) {
                if (defultDataLength - dataLength < 10) {
                    for (var i = 0; i < 10; i++) tableData.push({});
                } else {
                    for (var i = dataLength; i < defultDataLength; i++) tableData.push({});
                }
            } else {
                for (var i = 0; i < 10; i++) tableData.push({});
            }

            if (!protocolItemsConfigHandsontableHelper || !protocolItemsConfigHandsontableHelper.hot) {
                protocolItemsConfigHandsontableHelper = ProtocolItemsConfigHandsontableHelper.createNew('ModbusProtocolAddrMappingItemsConfigTableInfoDiv_id');

                var colHeaders = [
                    ['', '', {
                        label: (_loginUserLanguageResource.lowerComputer || 'Lower Computer'),
                        colspan: 6
                    }, {
                        label: (_loginUserLanguageResource.upperComputer || 'Upper Computer'),
                        colspan: 5
                    }],
                    [
                        _loginUserLanguageResource.idx,
                        _loginUserLanguageResource.name,
                        _loginUserLanguageResource.startAddress,
                        _loginUserLanguageResource.highLowByte,
                        _loginUserLanguageResource.treeDataType,
                        _loginUserLanguageResource.quantity,
                        _loginUserLanguageResource.RWType,
                        _loginUserLanguageResource.acqMode,
                        _loginUserLanguageResource.IFDataType,
                        _loginUserLanguageResource.prec,
                        _loginUserLanguageResource.ratio,
                        _loginUserLanguageResource.unit,
                        _loginUserLanguageResource.resolutionMode
                    ]
                ];
                var columns = [{
                        data: 'id'
                    },
                    {
                        data: 'title'
                    },
                    {
                        data: 'addr',
                        type: 'text',
                        allowInvalid: true,
                        validator: function(val, callback) {
                            return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolItemsConfigHandsontableHelper);
                        }
                    },
                    {
                        data: 'highLowByte',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: ['', (_loginUserLanguageResource.highByte || 'High'), (_loginUserLanguageResource.lowByte || 'Low')]
                    },
                    {
                        data: 'storeDataType',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: ['bit', 'byte', 'int16', 'uint16', 'float32', 'float64', 'bcd']
                    },
                    {
                        data: 'quantity',
                        type: 'text',
                        allowInvalid: true,
                        validator: function(val, callback) {
                            return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolItemsConfigHandsontableHelper);
                        }
                    },
                    {
                        data: 'RWType',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: [(_loginUserLanguageResource.readOnly || 'Read Only'), (_loginUserLanguageResource.writeOnly || 'Write Only'), (_loginUserLanguageResource.readWrite || 'Read Write')]
                    },
                    {
                        data: 'acqMode',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: [(_loginUserLanguageResource.activeAcqModel || 'Active'), (_loginUserLanguageResource.passiveAcqModel || 'Passive')]
                    },
                    {
                        data: 'IFDataType',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: ['bool', 'int', 'float32', 'float64', 'string']
                    },
                    {
                        data: 'prec',
                        type: 'text',
                        allowInvalid: true,
                        validator: function(val, callback) {
                            return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolItemsConfigHandsontableHelper);
                        }
                    },
                    {
                        data: 'ratio',
                        type: 'text',
                        allowInvalid: true,
                        validator: function(val, callback) {
                            return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolItemsConfigHandsontableHelper);
                        }
                    },
                    {
                        data: 'unit'
                    },
                    {
                        data: 'resolutionMode',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: [(_loginUserLanguageResource.switchingValue || 'Switching'), (_loginUserLanguageResource.enumValue || 'Enum'), (_loginUserLanguageResource.numericValue || 'Numeric')]
                    }
                ];
                protocolItemsConfigHandsontableHelper.colHeaders = colHeaders;
                protocolItemsConfigHandsontableHelper.columns = columns;
                protocolItemsConfigHandsontableHelper.createTable(tableData);
            } else {
                protocolItemsConfigHandsontableHelper.hot.deselectCell();
                protocolItemsConfigHandsontableHelper.hot.loadData(tableData);
            }

            // 初始化数据映射（用于重复检查）
            protocolItemsConfigHandsontableHelper.initTitleDataMap(tableData);
            protocolItemsConfigHandsontableHelper.initAddressDataMap(tableData);

            // 选中第一行或保持之前的选中
            var selectedRow = 0;
            var savedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
            if (dataLength > savedRow) {
                selectedRow = savedRow;
            }
            protocolItemsConfigHandsontableHelper.hot.selectCell(selectedRow, 'title');
            // 更新含义表
            var protocolCode = getCurrentProtocolCode();
            var itemTitle = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'title');
            var itemAddr = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'addr');
            var highLowByte = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'highLowByte');
            var resolutionMode = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'resolutionMode');
            var quantity = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(selectedRow, 'quantity');
            setTimeout(function() {
                CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
            }, 50);
        },
        error: function() {
        	mini.unmask(divId);
            mini.alert((_loginUserLanguageResource.ajaxError));
        }
    });
}

function CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, isNew) {
    // 销毁旧的 helper
    if (protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
        protocolItemsMeaningConfigHandsontableHelper.hot.destroy();
        protocolItemsMeaningConfigHandsontableHelper = null;
    }

    var highLow = '';
    if (highLowByte === (_loginUserLanguageResource.highByte || 'High')) highLow = 'high';
    else if (highLowByte === (_loginUserLanguageResource.lowByte || 'Low')) highLow = 'low';

    var resolutionModeValue = 2;
    if (resolutionMode === (_loginUserLanguageResource.switchingValue || 'Switching')) resolutionModeValue = 0;
    else if (resolutionMode === (_loginUserLanguageResource.enumValue || 'Enum')) resolutionModeValue = 1;

    quantity = parseInt(quantity) || 0;

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getProtocolItemMeaningConfigData',
        data: {
            protocolCode: protocolCode,
            itemAddr: itemAddr,
            highLowByte: highLow,
            resolutionMode: resolutionModeValue,
            quantity: quantity
        },
        dataType: 'json',
        success: function(result) {
            var data = result.totalRoot || [];
            // 更新含义面板标题（使用 jQuery 操作 DOM，或通过 miniui 标签）
            var showInfo = (itemTitle) ? '【<font color="red">' + itemTitle + '</font>】' + (_loginUserLanguageResource.meaning || 'Meaning') : (_loginUserLanguageResource.meaning || 'Meaning');
            // 假设含义面板标题对应的元素是 id 为 ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id 的 mini-panel 或 div
            // 这里直接设置其标题（如果是 miniui 面板可用 mini.get 设置，但为了兼容，我们使用 jQuery 查找）
            // 若该面板是 miniui 控件，可以用 mini.get('ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id').setTitle(showInfo);
            // 由于此处可能是 div，我们直接修改其标题文本（根据实际 DOM 结构）
            // 假设面板是 miniui 的 Panel，使用 mini.get
            var meaningPanel = mini.get('ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id');
            if (meaningPanel) {
                meaningPanel.setTitle(showInfo);
            } else {
                // 否则直接修改 HTML
                $('#ModbusProtocolAddrMappingItemsMeaningConfigPanel_Id .mini-panel-title').html(showInfo);
            }

            // 开关量特殊处理：显示/隐藏位状态表
            if (resolutionModeValue === 0) {
                // 显示位状态表
                var splitter = mini.get('meaningAndBitStatusSplitter_Id');
                if (splitter) {
                    splitter.showPane(2);
                }
                CreateProtocolSwitchingValueBitStatusConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, isNew);
            } else {
                var splitter = mini.get('meaningAndBitStatusSplitter_Id');
                if (splitter) {
                    splitter.hidePane(2);
                }
                if (protocolSwitchingValueBitStatusConfigHandsontableHelper && protocolSwitchingValueBitStatusConfigHandsontableHelper.hot) {
                    protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.destroy();
                    protocolSwitchingValueBitStatusConfigHandsontableHelper = null;
                }
            }

            // 补充空行（非开关量模式）
            if (resolutionModeValue !== 0) {
                var dataLength = data.length;
                var defultDataLength = 50;
                if (dataLength < defultDataLength) {
                    if (defultDataLength - dataLength < 10) {
                        for (var i = 0; i < 10; i++) data.push({});
                    } else {
                        for (var i = dataLength; i < defultDataLength; i++) data.push({});
                    }
                } else {
                    for (var i = 0; i < 10; i++) data.push({});
                }
            }

            if (!protocolItemsMeaningConfigHandsontableHelper || !protocolItemsMeaningConfigHandsontableHelper.hot) {
                protocolItemsMeaningConfigHandsontableHelper = ProtocolItemsMeaningConfigHandsontableHelper.createNew('ModbusProtocolAddrMappingItemsMeaningTableInfoDiv_id');
                var colHeaders, columns;
                if (resolutionModeValue === 0) {
                    colHeaders = [(_loginUserLanguageResource.bit || 'Bit'), (_loginUserLanguageResource.meaning || 'Meaning'), ''];
                    columns = [{
                            data: 'title'
                        },
                        {
                            data: 'meaning'
                        },
                        {
                            data: 'value'
                        }
                    ];
                    protocolItemsMeaningConfigHandsontableHelper.hiddenColumns = [2];
                    protocolItemsMeaningConfigHandsontableHelper.contextMenu = false;
                } else {
                    colHeaders = [(_loginUserLanguageResource.value || 'Value'), (_loginUserLanguageResource.meaning || 'Meaning')];
                    columns = [{
                            data: 'value',
                            type: 'text',
                            allowInvalid: true,
                            validator: function(val, callback) {
                                return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolItemsMeaningConfigHandsontableHelper);
                            }
                        },
                        {
                            data: 'meaning'
                        }
                    ];
                    protocolItemsMeaningConfigHandsontableHelper.hiddenColumns = [];
                    protocolItemsMeaningConfigHandsontableHelper.contextMenu = {
                        items: {
                            "row_above": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above')
                            },
                            "row_below": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below')
                            },
                            "col_left": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left')
                            },
                            "col_right": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right')
                            },
                            "remove_row": {
                                name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row')
                            },
                            "remove_col": {
                                name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column')
                            },
                            "merge_cell": {
                                name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells')
                            },
                            "copy": {
                                name: (_loginUserLanguageResource.contextMenu_copy || 'Copy')
                            },
                            "cut": {
                                name: (_loginUserLanguageResource.contextMenu_cut || 'Cut')
                            }
                        }
                    };
                }
                protocolItemsMeaningConfigHandsontableHelper.colHeaders = colHeaders;
                protocolItemsMeaningConfigHandsontableHelper.columns = columns;
                protocolItemsMeaningConfigHandsontableHelper.itemResolutionMode = resolutionModeValue;
                protocolItemsMeaningConfigHandsontableHelper.createTable(data);
            } else {
                protocolItemsMeaningConfigHandsontableHelper.hot.loadData(data);
                protocolItemsMeaningConfigHandsontableHelper.itemResolutionMode = resolutionModeValue;
            }
        },
        error: function() {
            mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
        }
    });
}

function CreateProtocolSwitchingValueBitStatusConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, isNew) {
    if (protocolSwitchingValueBitStatusConfigHandsontableHelper && protocolSwitchingValueBitStatusConfigHandsontableHelper.hot) {
        protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.destroy();
        protocolSwitchingValueBitStatusConfigHandsontableHelper = null;
    }
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getProtocolSwitchingValueBitStatusConfigData',
        data: {
            protocolCode: protocolCode,
            itemAddr: itemAddr,
            highLowByte: highLowByte,
            quantity: quantity,
            resolutionMode: resolutionMode
        },
        dataType: 'json',
        success: function(result) {
            var data = result.totalRoot || [];
            // 更新位状态面板标题
            var showInfo = (itemTitle) ? '【<font color="red">' + itemTitle + '</font>】' + (_loginUserLanguageResource.switchingValueBitStatusConfig || 'Bit Status Config') : (_loginUserLanguageResource.switchingValueBitStatusConfig || 'Bit Status Config');
            var bitPanel = mini.get('ProtocolSwitchingValueBitStatusConfigPanel_Id');
            if (bitPanel) {
                bitPanel.setTitle(showInfo);
            } else {
                $('#ProtocolSwitchingValueBitStatusConfigPanel_Id .mini-panel-title').html(showInfo);
            }

            if (!protocolSwitchingValueBitStatusConfigHandsontableHelper || !protocolSwitchingValueBitStatusConfigHandsontableHelper.hot) {
                protocolSwitchingValueBitStatusConfigHandsontableHelper = ProtocolSwitchingValueBitStatusConfigHandsontableHelper.createNew('ProtocolSwitchingValueBitStatusTableInfoDiv_id');
                var colHeaders = [(_loginUserLanguageResource.bit || 'Bit') + '/' + (_loginUserLanguageResource.value || 'Value'), (_loginUserLanguageResource.switchingValuStatus || 'Status'), '', ''];
                var columns = [{
                        data: 'title',
                        type: 'text'
                    },
                    {
                        data: 'status'
                    },
                    {
                        data: 'bitIndex'
                    },
                    {
                        data: 'value'
                    }
                ];
                protocolSwitchingValueBitStatusConfigHandsontableHelper.colHeaders = colHeaders;
                protocolSwitchingValueBitStatusConfigHandsontableHelper.columns = columns;
                protocolSwitchingValueBitStatusConfigHandsontableHelper.createTable(data);
            } else {
                protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.loadData(data);
            }
        },
        error: function() {
            mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
        }
    });
}

//================================================================
// 加载数值运算表格
// ================================================================
function CreateProtocolExtendedFieldConfigInfoTable(protocolName, classes, code) {
    // 销毁旧的 helper
    if (protocolExtendedFieldConfigHandsontableHelper && protocolExtendedFieldConfigHandsontableHelper.hot) {
        protocolExtendedFieldConfigHandsontableHelper.hot.destroy();
        protocolExtendedFieldConfigHandsontableHelper = null;
    }

    // 更新信息标签
    var showInfo = (_loginUserLanguageResource.extendedField || 'Extended Field');
    if (protocolName) {
        showInfo = '【<font color="red">' + protocolName + '</font>】' + showInfo;
    }
    //$('#protocolInfoLabel').html(showInfo);

    var divId='protocolSubTabs';
	var mask = mini.mask({
        el: divId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldsConfigData',
        data: {
            protocolName: protocolName,
            classes: classes,
            code: code,
            type: 0 // 数值运算
        },
        dataType: 'json',
        success: function(result) {
        	mini.unmask(divId);
            var dataLength = result.totalRoot.length;
            var defultDataLength = 100;
            var tableData = result.totalRoot;
            if (dataLength < defultDataLength) {
                for (var i = dataLength; i < defultDataLength; i++) {
                    tableData.push({});
                }
            }

            if (!protocolExtendedFieldConfigHandsontableHelper) {
                protocolExtendedFieldConfigHandsontableHelper = ProtocolExtendedFieldConfigHandsontableHelper.createNew('ProtocolExtendedFieldTableInfoDiv_id');

                var operationList = result.operationList || [];
                var additionalConditionsList = result.additionalConditionsList || [];

                var colHeaders = [
                    _loginUserLanguageResource.idx || 'Idx',
                    _loginUserLanguageResource.name || 'Name',
                    (_loginUserLanguageResource.dataColumn || 'Column') + '1',
                    _loginUserLanguageResource.fourOperation || 'Operation',
                    (_loginUserLanguageResource.dataColumn || 'Column') + '2',
                    _loginUserLanguageResource.prec || 'Prec',
                    _loginUserLanguageResource.ratio || 'Ratio',
                    _loginUserLanguageResource.unit || 'Unit',
                    _loginUserLanguageResource.additionalConditions || 'Additional Conditions'
                ];

                var columns = [{
                        data: 'id'
                    },
                    {
                        data: 'title',
                        renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle
                    },
                    {
                        data: 'title1',
                        renderer: protocolExtendedFieldConfigHandsontableHelper.placeholderRenderer
                    },
                    {
                        data: 'operation',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: operationList,
                        renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle
                    },
                    {
                        data: 'title2',
                        renderer: protocolExtendedFieldConfigHandsontableHelper.placeholderRenderer
                    },
                    {
                        data: 'prec',
                        type: 'text',
                        allowInvalid: true,
                        renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle,
                        validator: function(val, callback) {
                            return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldConfigHandsontableHelper);
                        }
                    },
                    {
                        data: 'ratio',
                        type: 'text',
                        allowInvalid: true,
                        renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle,
                        validator: function(val, callback) {
                            return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldConfigHandsontableHelper);
                        }
                    },
                    {
                        data: 'unit',
                        renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle
                    },
                    {
                        data: 'additionalConditions',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: additionalConditionsList,
                        renderer: protocolExtendedFieldConfigHandsontableHelper.addCellStyle
                    }
                ];

                protocolExtendedFieldConfigHandsontableHelper.colHeaders = colHeaders;
                protocolExtendedFieldConfigHandsontableHelper.columns = columns;
                protocolExtendedFieldConfigHandsontableHelper.createTable(tableData);
            } else {
                protocolExtendedFieldConfigHandsontableHelper.hot.loadData(tableData);
                protocolExtendedFieldConfigHandsontableHelper.hot.render();
            }
        },
        error: function() {
        	mini.unmask(divId);
            mini.alert((_loginUserLanguageResource.ajaxError));
        }
    });
}




//================================================================
//加载高低字节主表
//================================================================
function CreateProtocolExtendedFieldHighLowByteConfigInfoTable(protocolName, classes, code) {
    if (protocolExtendedFieldHighLowByteConfigHandsontableHelper && protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot) {
        protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.destroy();
        protocolExtendedFieldHighLowByteConfigHandsontableHelper = null;
    }

    var showInfo = (_loginUserLanguageResource.extendedField || 'Extended Field');
    if (protocolName) {
        showInfo = '【<font color="red">' + protocolName + '</font>】' + showInfo;
    }
    //$('#protocolInfoLabel').html(showInfo);
	
    var divId='protocolSubTabs';
	var mask = mini.mask({
        el: divId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldsConfigData',
        data: {
            protocolName: protocolName,
            classes: classes,
            code: code,
            type: 1 // 高低字节
        },
        dataType: 'json',
        success: function(result) {
        	mini.unmask(divId);
            var dataLength = result.totalRoot.length;
            var defultDataLength = 100;
            var tableData = result.totalRoot;
            if (dataLength < defultDataLength) {
                for (var i = dataLength; i < defultDataLength; i++) {
                    tableData.push({});
                }
            }

            if (!protocolExtendedFieldHighLowByteConfigHandsontableHelper) {
                protocolExtendedFieldHighLowByteConfigHandsontableHelper = ProtocolExtendedFieldHighLowByteConfigHandsontableHelper.createNew('ProtocolExtendedFieldHighLowByteTableInfoDiv_id');

                var colHeaders = [
                    _loginUserLanguageResource.idx || 'Idx',
                    _loginUserLanguageResource.name || 'Name',
                    (_loginUserLanguageResource.dataColumn || 'Column'),
                    _loginUserLanguageResource.highLowByte || 'High/Low Byte',
                    _loginUserLanguageResource.resolutionMode || 'Resolution Mode',
                    _loginUserLanguageResource.prec || 'Prec',
                    _loginUserLanguageResource.ratio || 'Ratio',
                    _loginUserLanguageResource.unit || 'Unit'
                ];

                var columns = [{
                        data: 'id'
                    },
                    {
                        data: 'title',
                        renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle
                    },
                    {
                        data: 'title1',
                        renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.placeholderRenderer
                    },
                    {
                        data: 'highLowByte',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: [(_loginUserLanguageResource.highByte || 'High'), (_loginUserLanguageResource.lowByte || 'Low')],
                        renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle
                    },
                    {
                        data: 'resolutionMode',
                        type: 'dropdown',
                        strict: true,
                        allowInvalid: false,
                        source: [(_loginUserLanguageResource.switchingValue || 'Switching'), (_loginUserLanguageResource.enumValue || 'Enum'), (_loginUserLanguageResource.numericValue || 'Numeric')],
                        renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle
                    },
                    {
                        data: 'prec',
                        type: 'text',
                        allowInvalid: true,
                        renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle,
                        validator: function(val, callback) {
                            return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldHighLowByteConfigHandsontableHelper);
                        }
                    },
                    {
                        data: 'ratio',
                        type: 'text',
                        allowInvalid: true,
                        renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle,
                        validator: function(val, callback) {
                            return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, protocolExtendedFieldHighLowByteConfigHandsontableHelper);
                        }
                    },
                    {
                        data: 'unit',
                        renderer: protocolExtendedFieldHighLowByteConfigHandsontableHelper.addCellStyle
                    }
                ];

                protocolExtendedFieldHighLowByteConfigHandsontableHelper.colHeaders = colHeaders;
                protocolExtendedFieldHighLowByteConfigHandsontableHelper.columns = columns;
                protocolExtendedFieldHighLowByteConfigHandsontableHelper.createTable(tableData);
            } else {
                protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.loadData(tableData);
                protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.render();
            }

            // 默认选中第一行并加载含义表
            if (dataLength > 0) {
                protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.selectCell(0, 'title');
                $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val(0);
                var protocolCode = getCurrentProtocolCode();
                var itemTitle = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(0, 'title');
                var resolutionMode = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(0, 'resolutionMode');
                CreateProtocolExtendedFieldMeaningConfigInfoTable(protocolCode, itemTitle, resolutionMode, true);
            } else {
                // 无数据，清空含义和位状态
                $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val('');
                CreateProtocolExtendedFieldMeaningConfigInfoTable('', '', '', true);
            }
        },
        error: function() {
        	mini.unmask(divId);
            mini.alert((_loginUserLanguageResource.ajaxError));
        }
    });
}



//================================================================
//加载高低字节含义表格
//================================================================
function CreateProtocolExtendedFieldMeaningConfigInfoTable(protocolCode, itemTitle, resolutionMode, isNew) {
    if (protocolExtendedFieldMeaningConfigHandsontableHelper && protocolExtendedFieldMeaningConfigHandsontableHelper.hot) {
        protocolExtendedFieldMeaningConfigHandsontableHelper.hot.destroy();
        protocolExtendedFieldMeaningConfigHandsontableHelper = null;
    }

    var resolutionModeValue = 2;
    if (resolutionMode === (_loginUserLanguageResource.switchingValue || 'Switching')) resolutionModeValue = 0;
    else if (resolutionMode === (_loginUserLanguageResource.enumValue || 'Enum')) resolutionModeValue = 1;

    var quantity = 8; // 默认8位

    var showInfo = (_loginUserLanguageResource.meaning || 'Meaning');
    if (itemTitle) {
        showInfo = '【<font color="red">' + itemTitle + '</font>】' + showInfo;
    }
    // 更新含义面板标题（假设面板id是 ProtocolExtendedFieldConfigHighLowByteItemsMeaningConfigPanel_Id）
    var meaningPanel = mini.get('ProtocolExtendedFieldConfigHighLowByteItemsMeaningConfigPanel_Id');
    if (meaningPanel) {
        meaningPanel.setTitle(showInfo);
    } else {
        $('#ProtocolExtendedFieldConfigHighLowByteItemsMeaningConfigPanel_Id .mini-panel-title').html(showInfo);
    }

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldMeaningConfigData',
        data: {
            protocolCode: protocolCode,
            itemTitle: itemTitle,
            resolutionMode: resolutionModeValue,
            quantity: quantity
        },
        dataType: 'json',
        success: function(result) {
            var data = result.totalRoot || [];

            // 补充空行
            if (resolutionModeValue !== 0) {
                var dataLength = data.length;
                var defultDataLength = 50;
                if (dataLength < defultDataLength) {
                    if (defultDataLength - dataLength < 10) {
                        for (var i = 0; i < 10; i++) data.push({});
                    } else {
                        for (var i = dataLength; i < defultDataLength; i++) data.push({});
                    }
                } else {
                    for (var i = 0; i < 10; i++) data.push({});
                }
            }

            if (!protocolExtendedFieldMeaningConfigHandsontableHelper) {
                protocolExtendedFieldMeaningConfigHandsontableHelper = ProtocolExtendedFieldMeaningConfigHandsontableHelper.createNew('ProtocolExtendedFieldConfigHighLowByteItemsMeaningTableInfoDiv_id');

                var colHeaders, columns;
                if (resolutionModeValue === 0) {
                    colHeaders = [(_loginUserLanguageResource.bit || 'Bit'), (_loginUserLanguageResource.meaning || 'Meaning'), ''];
                    columns = [{
                            data: 'title'
                        },
                        {
                            data: 'meaning'
                        },
                        {
                            data: 'value'
                        }
                    ];
                    protocolExtendedFieldMeaningConfigHandsontableHelper.hiddenColumns = [2];
                    protocolExtendedFieldMeaningConfigHandsontableHelper.contextMenu = false;
                } else {
                    colHeaders = [(_loginUserLanguageResource.value || 'Value'), (_loginUserLanguageResource.meaning || 'Meaning')];
                    columns = [{
                            data: 'value',
                            type: 'text',
                            allowInvalid: true,
                            validator: function(val, callback) {
                                return handsontableDataCheck_Num(val, callback, this.row, this.col, protocolExtendedFieldMeaningConfigHandsontableHelper);
                            }
                        },
                        {
                            data: 'meaning'
                        }
                    ];
                    protocolExtendedFieldMeaningConfigHandsontableHelper.hiddenColumns = [];
                    protocolExtendedFieldMeaningConfigHandsontableHelper.contextMenu = {
                        items: {
                            "row_above": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above')
                            },
                            "row_below": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below')
                            },
                            "col_left": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left')
                            },
                            "col_right": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right')
                            },
                            "remove_row": {
                                name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row')
                            },
                            "remove_col": {
                                name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column')
                            },
                            "merge_cell": {
                                name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells')
                            },
                            "copy": {
                                name: (_loginUserLanguageResource.contextMenu_copy || 'Copy')
                            },
                            "cut": {
                                name: (_loginUserLanguageResource.contextMenu_cut || 'Cut')
                            }
                        }
                    };
                }
                protocolExtendedFieldMeaningConfigHandsontableHelper.colHeaders = colHeaders;
                protocolExtendedFieldMeaningConfigHandsontableHelper.columns = columns;
                protocolExtendedFieldMeaningConfigHandsontableHelper.itemResolutionMode = resolutionModeValue;
                protocolExtendedFieldMeaningConfigHandsontableHelper.createTable(data);
            } else {
                protocolExtendedFieldMeaningConfigHandsontableHelper.hot.loadData(data);
                protocolExtendedFieldMeaningConfigHandsontableHelper.itemResolutionMode = resolutionModeValue;
                protocolExtendedFieldMeaningConfigHandsontableHelper.hot.render();
            }

            // 控制位状态面板显示
            if (resolutionModeValue === 0) {
                // 显示位状态面板
                var splitter = mini.get('highLowBitStatusSplitter_Id');
                if (splitter) {
                    splitter.showPane(2);
                }
                CreateProtocolExtendedFieldSwitchingValueBitStatusConfigInfoTable(protocolCode, itemTitle, resolutionMode, isNew);
            } else {
                var splitter = mini.get('highLowBitStatusSplitter_Id');
                if (splitter) {
                    splitter.hidePane(2);
                }
                if (protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper && protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot) {
                    protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.destroy();
                    protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = null;
                }
            }
        },
        error: function() {
            mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
        }
    });
}



//================================================================
//加载高低字节开关量位状态表格
//================================================================
function CreateProtocolExtendedFieldSwitchingValueBitStatusConfigInfoTable(protocolCode, itemTitle, resolutionMode, isNew) {
    if (protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper && protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot) {
        protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.destroy();
        protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = null;
    }

    var quantity = 8;
    var showInfo = (_loginUserLanguageResource.switchingValueBitStatusConfig || 'Bit Status Config');
    if (itemTitle) {
        showInfo = '【<font color="red">' + itemTitle + '</font>】' + showInfo;
    }
    var bitPanel = mini.get('ProtocolExtendedFieldSwitchingValueBitStatusConfigPanel_Id');
    if (bitPanel) {
        bitPanel.setTitle(showInfo);
    } else {
        $('#ProtocolExtendedFieldSwitchingValueBitStatusConfigPanel_Id .mini-panel-title').html(showInfo);
    }

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getProtocolExtendedFieldSwitchingValueBitStatusConfigData',
        data: {
            protocolCode: protocolCode,
            itemTitle: itemTitle,
            quantity: quantity,
            resolutionMode: resolutionMode
        },
        dataType: 'json',
        success: function(result) {
            var data = result.totalRoot || [];

            if (!protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper) {
                protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = ProtocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.createNew('ProtocolExtendedFieldSwitchingValueBitStatusConfigTableInfoDiv_id');

                var colHeaders = [(_loginUserLanguageResource.bit || 'Bit') + '/' + (_loginUserLanguageResource.value || 'Value'), (_loginUserLanguageResource.switchingValuStatus || 'Status'), '', ''];
                var columns = [{
                        data: 'title',
                        type: 'text'
                    },
                    {
                        data: 'status'
                    },
                    {
                        data: 'bitIndex'
                    },
                    {
                        data: 'value'
                    }
                ];

                protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.colHeaders = colHeaders;
                protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.columns = columns;
                protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.createTable(data);
            } else {
                protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.loadData(data);
                protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.render();
            }
        },
        error: function() {
            mini.alert((_loginUserLanguageResource.ajaxError || 'Ajax error'));
        }
    });
}
function saveProtocolConfigData() {
    // 1. 获取当前选中的协议节点
    var protocolTree = mini.get('protocolTree');
    if (!protocolTree) {
        mini.alert(_loginUserLanguageResource.tip || '提示', '请先选择协议');
        return;
    }
    var selectedNode = protocolTree.getSelectedNode();
    if (!selectedNode || selectedNode.classes !== 1) {
        mini.alert(_loginUserLanguageResource.tip || '提示', '请选择一个协议节点');
        return;
    }

    // 2. 确定保存类型
    var saveType = 0; // 默认属性
    var protoSub = mini.get('protocolSubTabs');
    if (protoSub) {
        var activeTab = protoSub.getActiveTab();
        if (activeTab) {
            var tabName = activeTab.name; // props, config, extended
            if (tabName === 'props') {
                saveType = 0;
            } else if (tabName === 'config') {
                saveType = 1;
            } else if (tabName === 'extended') {
                var extSub = mini.get('extendedSubTabs');
                if (extSub) {
                    var extActive = extSub.getActiveTab();
                    if (extActive) {
                        if (extActive.name === 'numeric') {
                            saveType = 2; // 数值运算
                        } else if (extActive.name === 'highlow') {
                            saveType = 3; // 高低字节
                        }
                    }
                }
            }
        }
    }

    // 3. 准备协议基础数据
    var protocolConfigData = {
        text: selectedNode.text,
        code: selectedNode.code,
        deviceType: selectedNode.deviceType || '',
        sort: selectedNode.sort || ''
    };

    // 4. 如果是属性标签，从属性表格获取最新名称和排序
    if (saveType === 0 && protocolPropertiesHandsontableHelper && protocolPropertiesHandsontableHelper.hot) {
        var propertiesData = protocolPropertiesHandsontableHelper.hot.getData();
        if (propertiesData && propertiesData.length > 0) {
            var nameVal = (propertiesData[0] && propertiesData[0][2] !== undefined) ? propertiesData[0][2] : '';
            var sortVal = (propertiesData[1] && propertiesData[1][2] !== undefined) ? propertiesData[1][2] : '';
            protocolConfigData.text = isNotVal(nameVal) ? nameVal : '';
            protocolConfigData.sort = isNotVal(sortVal) ? sortVal : '';
        }
    }

    // 5. 校验协议名称不能为空
    if (!isNotVal(protocolConfigData.text)) {
        mini.alert(_loginUserLanguageResource.tip || '提示',
            (_loginUserLanguageResource.protocolName || '协议名称') + ',' +
            (_loginUserLanguageResource.canNotBeEmpty || '不能为空') + '!');
        return;
    }

    // 6. 构造 configInfo 对象
    var configInfo = {
        ProtocolName: protocolConfigData.text,
        ProtocolCode: protocolConfigData.code,
        DeviceType: protocolConfigData.deviceType,
        Sort: protocolConfigData.sort,
        DataConfig: [],
        ExtendedFieldConfig: []
    };

    // 7. 根据 saveType 收集对应的表格数据
    if (saveType === 1) {
        // 配置项表格
        if (protocolItemsConfigHandsontableHelper && protocolItemsConfigHandsontableHelper.hot) {
            var itemsData = protocolItemsConfigHandsontableHelper.hot.getData();
            var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);

            for (var i = 0; i < itemsData.length; i++) {
                var itemTitle = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title');
                if (!isNotVal(itemTitle)) continue;

                var item = {};
                item.Title = itemTitle;
                item.Addr = parseInt(protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'addr')) || 0;

                var highLow = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'highLowByte');
                item.HighLowByte = '';
                if (highLow === (_loginUserLanguageResource.highByte || '高字节')) {
                    item.HighLowByte = 'high';
                } else if (highLow === (_loginUserLanguageResource.lowByte || '低字节')) {
                    item.HighLowByte = 'low';
                }

                item.StoreDataType = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'storeDataType') || '';
                item.Quantity = parseInt(protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'quantity')) || 0;
                item.RWType = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'RWType') || '';
                item.AcqMode = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'acqMode') || '';
                item.IFDataType = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'IFDataType') || '';

                var precStr = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'prec') + '';
                precStr = precStr.replace(/\s/g, '');
                item.Prec = (item.IFDataType && item.IFDataType.toLowerCase().indexOf('float') >= 0) ?
                    (isNumber(parseFloat(precStr)) ? parseFloat(precStr) : 0) : 0;

                var ratioVal = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'ratio');
                item.Ratio = isNumber(parseFloat(ratioVal)) ? parseFloat(ratioVal) : 1;

                var unitVal = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'unit') + '';
                item.Unit = unitVal.replace(/\s/g, '');

                item.ResolutionMode = protocolItemsConfigHandsontableHelper.hot.getDataAtRowProp(i, 'resolutionMode') || '';

                // 只有选中行才收集含义/位状态
                if (i === selectedRow) {
                    item.Meaning = [];
                    if (item.ResolutionMode === (_loginUserLanguageResource.enumValue || '枚举量')) {
                        // 枚举量：从含义表收集 value/meaning
                        if (protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
                            var meaningData = protocolItemsMeaningConfigHandsontableHelper.hot.getData();
                            for (var j = 0; j < meaningData.length; j++) {
                                var val = protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'value');
                                var meaning = protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'meaning');
                                if (isNotVal(val) && isNotVal(meaning)) {
                                    item.Meaning.push({
                                        Value: val,
                                        Meaning: meaning
                                    });
                                }
                            }
                        }
                    } else if (item.ResolutionMode === (_loginUserLanguageResource.switchingValue || '开关量')) {
                        // 开关量：从位状态表收集，并结合含义表补充 Meaning
                        if (protocolSwitchingValueBitStatusConfigHandsontableHelper && protocolSwitchingValueBitStatusConfigHandsontableHelper.hot) {
                            var bitData = protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.getData();
                            for (var k = 0; k < bitData.length; k++) {
                                var status = protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'status');
                                var bitIndex = protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'bitIndex');
                                var bitValue = protocolSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'value');
                                if (isNotVal(status)) {
                                    var exist = false;
                                    for (var m = 0; m < item.Meaning.length; m++) {
                                        if (bitIndex == item.Meaning[m].Value) {
                                            if (bitValue == 0) item.Meaning[m].Status0 = status;
                                            else if (bitValue == 1) item.Meaning[m].Status1 = status;
                                            exist = true;
                                            break;
                                        }
                                    }
                                    if (!exist) {
                                        var newMeaning = {
                                            Value: bitIndex
                                        };
                                        if (bitValue == 0) newMeaning.Status0 = status;
                                        else if (bitValue == 1) newMeaning.Status1 = status;
                                        item.Meaning.push(newMeaning);
                                    }
                                }
                            }
                        }
                        // 补充含义表的 Meaning 字段
                        if (protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
                            var meaningData = protocolItemsMeaningConfigHandsontableHelper.hot.getData();
                            for (var j = 0; j < meaningData.length; j++) {
                                var bitIdx = protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'value');
                                var meaningText = protocolItemsMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'meaning');
                                for (var k = 0; k < item.Meaning.length; k++) {
                                    if (bitIdx == item.Meaning[k].Value) {
                                        item.Meaning[k].Meaning = meaningText;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                configInfo.DataConfig.push(item);
            }
        }
    } else if (saveType === 2) {
        // 扩展字段 - 数值运算
        if (protocolExtendedFieldConfigHandsontableHelper && protocolExtendedFieldConfigHandsontableHelper.hot) {
            var extData = protocolExtendedFieldConfigHandsontableHelper.hot.getData();
            for (var i = 0; i < extData.length; i++) {
                var title = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title');
                if (!isNotVal(title)) continue;
                var extItem = {};
                extItem.Title = title;
                var title1 = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title1');
                extItem.Title1 = (title1 === (_loginUserLanguageResource.doubleClickCellTip + '...')) ? '' : title1;
                extItem.Operation = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'operation') || '';
                var title2 = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title2');
                extItem.Title2 = (title2 === (_loginUserLanguageResource.doubleClickCellTip + '...')) ? '' : title2;

                var precStr = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'prec') + '';
                precStr = precStr.replace(/\s/g, '');
                extItem.Prec = isNumber(parseFloat(precStr)) ? parseFloat(precStr) : 0;

                var ratioVal = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'ratio');
                extItem.Ratio = isNumber(parseFloat(ratioVal)) ? parseFloat(ratioVal) : 1;

                var unitVal = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'unit') + '';
                extItem.Unit = unitVal.replace(/\s/g, '');
                extItem.AdditionalConditions = protocolExtendedFieldConfigHandsontableHelper.hot.getDataAtRowProp(i, 'additionalConditions') || '';

                configInfo.ExtendedFieldConfig.push(extItem);
            }
        }
    } else if (saveType === 3) {
        // 扩展字段 - 高低字节
        if (protocolExtendedFieldHighLowByteConfigHandsontableHelper && protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot) {
            var extData = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getData();
            var selectedRow = parseInt($('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val() || 0);
            for (var i = 0; i < extData.length; i++) {
                var title = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title');
                if (!isNotVal(title)) continue;
                var extItem = {};
                extItem.Title = title;
                var title1 = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'title1');
                extItem.Title1 = (title1 === (_loginUserLanguageResource.doubleClickCellTip + '...')) ? '' : title1;

                var highLow = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'highLowByte');
                extItem.HighLowByte = '';
                if (highLow === (_loginUserLanguageResource.highByte || '高字节')) {
                    extItem.HighLowByte = 'high';
                } else if (highLow === (_loginUserLanguageResource.lowByte || '低字节')) {
                    extItem.HighLowByte = 'low';
                }

                extItem.ResolutionMode = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'resolutionMode') || '';

                var precStr = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'prec') + '';
                precStr = precStr.replace(/\s/g, '');
                extItem.Prec = isNumber(parseFloat(precStr)) ? parseFloat(precStr) : 0;

                var ratioVal = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'ratio');
                extItem.Ratio = isNumber(parseFloat(ratioVal)) ? parseFloat(ratioVal) : 1;

                var unitVal = protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.getDataAtRowProp(i, 'unit') + '';
                extItem.Unit = unitVal.replace(/\s/g, '');

                // 只有选中行才收集含义/位状态
                if (i === selectedRow) {
                    extItem.Meaning = [];
                    if (extItem.ResolutionMode === (_loginUserLanguageResource.enumValue || '枚举量')) {
                        if (protocolExtendedFieldMeaningConfigHandsontableHelper && protocolExtendedFieldMeaningConfigHandsontableHelper.hot) {
                            var meaningData = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getData();
                            for (var j = 0; j < meaningData.length; j++) {
                                var val = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'value');
                                var meaning = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'meaning');
                                if (isNotVal(val) && isNotVal(meaning)) {
                                    extItem.Meaning.push({
                                        Value: val,
                                        Meaning: meaning
                                    });
                                }
                            }
                        }
                    } else if (extItem.ResolutionMode === (_loginUserLanguageResource.switchingValue || '开关量')) {
                        if (protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper && protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot) {
                            var bitData = protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.getData();
                            for (var k = 0; k < bitData.length; k++) {
                                var status = protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'status');
                                var bitIndex = protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'bitIndex');
                                var bitValue = protocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper.hot.getDataAtRowProp(k, 'value');
                                if (isNotVal(status)) {
                                    var exist = false;
                                    for (var m = 0; m < extItem.Meaning.length; m++) {
                                        if (bitIndex == extItem.Meaning[m].Value) {
                                            if (bitValue == 0) extItem.Meaning[m].Status0 = status;
                                            else if (bitValue == 1) extItem.Meaning[m].Status1 = status;
                                            exist = true;
                                            break;
                                        }
                                    }
                                    if (!exist) {
                                        var newMeaning = {
                                            Value: bitIndex
                                        };
                                        if (bitValue == 0) newMeaning.Status0 = status;
                                        else if (bitValue == 1) newMeaning.Status1 = status;
                                        extItem.Meaning.push(newMeaning);
                                    }
                                }
                            }
                        }
                        if (protocolExtendedFieldMeaningConfigHandsontableHelper && protocolExtendedFieldMeaningConfigHandsontableHelper.hot) {
                            var meaningData = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getData();
                            for (var j = 0; j < meaningData.length; j++) {
                                var bitIdx = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'value');
                                var meaningText = protocolExtendedFieldMeaningConfigHandsontableHelper.hot.getDataAtRowProp(j, 'meaning');
                                for (var k = 0; k < extItem.Meaning.length; k++) {
                                    if (bitIdx == extItem.Meaning[k].Value) {
                                        extItem.Meaning[k].Meaning = meaningText;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                configInfo.ExtendedFieldConfig.push(extItem);
            }
        }
    }

    // 8. 重复检查（仅配置项表格）
    var hasDuplicate = false;
    if (saveType === 1 && protocolItemsConfigHandsontableHelper) {
        var dupList = protocolItemsConfigHandsontableHelper.getDuplicateRowList();
        var addrDupList = protocolItemsConfigHandsontableHelper.getAddrDuplicateRowList();
        if (dupList.length > 0 || addrDupList.length > 0) {
            hasDuplicate = true;
        }
    }

    // 9. 如果有重复，弹出确认对话框（用 mini.confirm）
    if (hasDuplicate) {
        mini.confirm(_loginUserLanguageResource.protocolSaveConfirm,
            _loginUserLanguageResource.confirm,
            function(action) {
                if (action == 'ok') {
                    saveModbusProtocolAddrMappingConfigData(configInfo, saveType);
                }
            }
        );
    } else {
        saveModbusProtocolAddrMappingConfigData(configInfo, saveType);
    }
}

function saveModbusProtocolAddrMappingConfigData(configInfo, saveType) {
    var loading = mini.loading(_loginUserLanguageResource.updateWait, _loginUserLanguageResource.tip);

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/saveModbusProtocolAddrMappingConfigData',
        data: {
            data: JSON.stringify(configInfo),
            saveType: saveType
        },
        dataType: 'json',
        success: function(response) {
            mini.hideMessageBox(loading);
            if (response.success) {
                // 清空临时数据（原逻辑调用了 clearContainer，这里保留空实现）
                if (protocolItemsConfigHandsontableHelper && typeof protocolItemsConfigHandsontableHelper.clearContainer === 'function') {
                    protocolItemsConfigHandsontableHelper.clearContainer();
                }

                if (configInfo.delidslist && configInfo.delidslist.length > 0) {
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                } else {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                }

                // 刷新协议树（根据原逻辑：删除时刷新，或 saveType==0 时刷新）
                if (configInfo.delidslist && configInfo.delidslist.length > 0) {
                    // 删除后刷新并重置选中状态
                    var tree = mini.get('protocolTree');
                    if (tree) {
                        tree.load();
                    }
                    // 清空隐藏域
                    $('#ModbusProtocolAddrMappingItemsSelectRow_Id').val(0);
                    $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val(0);
                } else {
                    if (saveType === 0) {
                        // 仅属性保存时刷新树（协议名称可能变化）
                        var tree = mini.get('protocolTree');
                        if (tree) {
                            tree.load();
                        }
                    }
                }
            } else {
                mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed) + '</font>');
            }
        },
        error: function() {
            mini.hideMessageBox(loading);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

function addProtocolData() {
    // 获取当前选中的设备类型节点
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }

    var deviceTypeId = selectedNode.deviceTypeId;
    var deviceTypeName = selectedNode.text;

    // 打开添加窗口
    mini.open({
        title: _loginUserLanguageResource.addProtoco,
        url: context + '/miniui-app/modules/driverConfig/protocolAddWindow.jsp',
        width: 330,
        height: 280,
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            // 传递参数
            contentWindow.setData({
                deviceTypeId: deviceTypeId,
                deviceTypeName: deviceTypeName,
                languageValue: _loginUserLanguageValue,
                language: _loginUserLanguage
            });
            // 暴露刷新树的函数给子窗口
            contentWindow._parentRefreshProtocolTree = function() {
                var tree = mini.get('protocolTree');
                if (tree) {
                    tree.load();
                }
            };
            // 暴露设置新协议名称的函数，用于树加载后高亮
            contentWindow._parentSetNewProtocolName = function(name) {
                window._newProtocolName = name;
                // 在树加载完成后会自动选中（见 onProtocolTreeLoad 中的处理）
            };
        },
        ondestroy: function(action) {
            // 如果子窗口成功添加，会刷新树，这里无需额外操作
        }
    });
}
function openFieldMappingWindow() {
    // 获取当前设备类型树选中节点的所有子节点 ID
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        return;
    }

    mini.open({
        title: _loginUserLanguageResource.fieldMappingTable,
        url: context + '/miniui-app/modules/driverConfig/databaseColumnMappingWindow.jsp',
        width: '65%',
        height: '80%',
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            // 传递 deviceTypeIds
            contentWindow.setData({
                deviceTypeIds: selectedDeviceTypeId,
                editFlag: editFlag
            });
        },
        ondestroy: function() {
            // 关闭后可选刷新
        }
    });
}
function openExportProtocolWindow() {
    // 获取设备类型树选中的节点（与之前字段映射表类似）
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        return;
    }

    mini.open({
        title: _loginUserLanguageResource.exportProtocol,
        url: context + '/miniui-app/modules/driverConfig/exportProtocolWindow.jsp',
        width: 450,
        height: 600,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: selectedDeviceTypeId
            });
        }
    });
}
function openImportProtocolWindow() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var deviceTypeId = selectedNode.deviceTypeId;
    var deviceTypeName = getNodePath(deviceTree, selectedNode);

    mini.open({
        title: _loginUserLanguageResource.importProtocol,
        url: context + '/miniui-app/modules/driverConfig/importProtocolWindow.jsp',
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
            contentWindow.parent.refreshProtocolTree = function(r, c, value) {
                var tree = mini.get('protocolTree');
                if (tree) {
                    tree.load();
                }
            };
        },
        ondestroy: function() {
            // 关闭后
        }
    });
}

function openProtocolDeviceTypeChangeWindow() {
    // 获取当前设备类型树选中的节点（用于过滤协议列表）
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        return;
    }

    mini.open({
        title: _loginUserLanguageResource.protocoDeviceTypeChange,
        url: context + '/miniui-app/modules/driverConfig/protocolDeviceTypeChangeWindow.jsp',
        width: 600,
        height: 600,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: allDeviceTypeIds
            });
            contentWindow.parent.refreshProtocolTree = function(r, c, value) {
                var tree = mini.get('protocolTree');
                if (tree) {
                    tree.load();
                }
            };
        }
    });
}
//删除协议节点
function deleteProtocolNode(e) {
    var tree = mini.get('protocolTree');
    var menu = e.sender;
    var node = tree.getSelectedNode();
    if (!node) {
        return;
    }
    var protocolCode = node.code;
    var protocolName = node.text;

    mini.confirm(_loginUserLanguageResource.confirmDelete, _loginUserLanguageResource.confirm, function(action) {
        if (action == 'ok') {
            var configInfo = {
                delidslist: [protocolCode]
            };
            saveModbusProtocolAddrMappingConfigData(configInfo);
        }
    });
}
//右键菜单打开前事件（控制显示/隐藏）
function onProtocolTreeBeforeMenu(e) {
    var tree = mini.get('protocolTree');
    var menu = e.sender;
    var node = tree.getSelectedNode(); // 注意：右键点击时，树会自动选中该节点

    // 只对协议节点（classes===1）显示菜单，目录节点不显示
    if (!node || node.classes !== 1) {
        e.cancel = true; // 阻止菜单弹出
        e.htmlEvent.preventDefault();
        return;
    }

    // 更新菜单项文字（国际化）
    var deleteText = _loginUserLanguageResource.deleteData;
    document.getElementById('protocolTreeMenuDeleteText').textContent = deleteText;
    document.getElementById('acqUnitTreeMenuDeleteText').textContent = deleteText;

    // 根据权限禁用菜单项（如果需要）
    var deleteItem = mini.getbyName('delete', menu);
    if (!editFlag) {
        deleteItem.disable();
    } else {
        deleteItem.enable();
    }
}