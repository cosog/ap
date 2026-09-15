// ================================================================
// 报警实例配置模块
// 对应原 ExtJS: AP.view.acquisitionUnit.ModbusProtocolAlarmInstanceConfigInfoView
// ================================================================

// ---------- 状态变量 ----------
var _currentAlarmInstanceProtocolNode = null;
var _currentAlarmInstanceNode = null;
var _selectedAlarmInstanceId = null;

var protocolAlarmInstancePropertiesHandsontableHelper = null;

// ================================================================
// 1. 协议树事件
// ================================================================
function onAlarmInstanceProtocolTreeBeforeLoad(e) {
    var params = e.params || {};
    if (typeof selectedDeviceTypeId !== 'undefined' && selectedDeviceTypeId) {
        params.deviceTypeIds = selectedDeviceTypeId;
    }
    e.params = params;
}

function onAlarmInstanceProtocolTreeLoad(e) {
    var tree = e.sender;
    var root = tree.getRootNode();
    if (!root) return;

    var targetNode = null;

    if (_selectedProtocolTreeNodeCode) {
        (function find(node) {
            if (node.code === _selectedProtocolTreeNodeCode && node.classes === 1) {
                targetNode = node;
                return true;
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) {
                    if (find(node.children[i])) return true;
                }
            }
            return false;
        })(root);
    }

    if (!targetNode) {
        (function collect(node) {
            if (targetNode) return;
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                    if (targetNode) return;
                }
            } else {
                if (node.classes === 1) targetNode = node;
            }
        })(root);
    }

    if (targetNode) {
        setTimeout(function () {
            tree.selectNode(targetNode);
        }, 50);
    }
}

function onAlarmInstanceProtocolTreeSelect(e) {
    var node = e.node;
    _currentAlarmInstanceProtocolNode = node;

    if (node && node.classes === 1) {
        _selectedProtocolTreeNodeCode = node.code;
        _selectedAlarmInstanceId = null;
    }

    loadAlarmInstanceList(node);
}

function loadAlarmInstanceList(protocolNode) {
    var tree = mini.get('alarmInstanceList');
    if (!tree) return;

    if (!tree.getUrl()) {
        tree.setUrl(context + '/acquisitionUnitManagerController/modbusAlarmInstanceConfigTreeData');
    }

    tree.load();
}

// ================================================================
// 2. 实例列表树事件
// ================================================================
function onAlarmInstanceListBeforeLoad(e) {
    var params = e.params || {};
    if (_currentAlarmInstanceProtocolNode) {
        if (_currentAlarmInstanceProtocolNode.classes === 1) {
            params.protocol = _currentAlarmInstanceProtocolNode.code;
        } else {
            var protocolList = [];
            if (isNotVal(_currentAlarmInstanceProtocolNode.children)) {
                for (var i = 0; i < _currentAlarmInstanceProtocolNode.children.length; i++) {
                    protocolList.push(_currentAlarmInstanceProtocolNode.children[i].code);
                }
            }
            params.protocol = protocolList.join(",");
        }
    }
    e.params = params;
}

function onAlarmInstanceListLoad(e) {
    var tree = e.sender;
 // ★ 首次加载时设置列
    if (!tree._columnsSet) {
        tree.setColumns([
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.alarmInstanceList,
                headerAlign: 'left',
                align: 'left',
                width: '70%'
            },
            {
                field: 'deviceCount',
                header: _loginUserLanguageResource.primaryDeviceCount,
                headerAlign: 'center',
                align: 'center',
                width: '30%'
            },
            { field: 'id', visible: false }
        ]);
        tree._columnsSet = true;
    }
    var root = tree.getRootNode();
    if (!root) return;

    var targetNode = null;

    // ★ 1. 高亮新增实例
    if (window._newAlarmInstanceName) {
        (function find(node) {
            if (node.text === window._newAlarmInstanceName && node.classes === 1) {
                targetNode = node;
                return true;
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) {
                    if (find(node.children[i])) return true;
                }
            }
            return false;
        })(root);

        if (targetNode) window._newAlarmInstanceName = null;
    }

    // ★ 2. 恢复上次选中
    if (!targetNode && _selectedAlarmInstanceId) {
        (function find(node) {
            if (node.id === _selectedAlarmInstanceId && node.classes === 1) {
                targetNode = node;
                return true;
            }
            if (node.children) {
                for (var i = 0; i < node.children.length; i++) {
                    if (find(node.children[i])) return true;
                }
            }
            return false;
        })(root);
    }

    // ★ 3. 选第一个实例节点
    if (!targetNode) {
        (function collect(node) {
            if (targetNode) return;
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                    if (targetNode) return;
                }
            } else {
                if (node.classes === 1) targetNode = node;
            }
        })(root);
    }

    setTimeout(function () {
        if (targetNode) {
            tree.selectNode(targetNode);
        } else if (root.children && root.children.length > 0) {
            tree.selectNode(root.children[0]);
        }
    }, 50);
}

function onAlarmInstanceListSelect(e) {
    var node = e.node;
    if (!node) return;

    _currentAlarmInstanceNode = node;
    _selectedAlarmInstanceId = node.id;

    loadAlarmInstanceProperties(node);
}

// ================================================================
// 3. 加载报警实例属性表
// ================================================================
function loadAlarmInstanceProperties(node) {
    if (!node) return;

    var container = document.getElementById('alarmInstancePropertiesContainer');
    if (!container) return;

    if (protocolAlarmInstancePropertiesHandsontableHelper) {
        if (protocolAlarmInstancePropertiesHandsontableHelper.hot) {
            protocolAlarmInstancePropertiesHandsontableHelper.hot.destroy();
        }
        protocolAlarmInstancePropertiesHandsontableHelper = null;
    }

    // 收集协议列表
    var protocolList = [];
    if (_currentAlarmInstanceProtocolNode) {
        if (_currentAlarmInstanceProtocolNode.classes === 1) {
            protocolList.push(_currentAlarmInstanceProtocolNode.code);
        } else {
            if (isNotVal(_currentAlarmInstanceProtocolNode.children)) {
                for (var i = 0; i < _currentAlarmInstanceProtocolNode.children.length; i++) {
                    protocolList.push(_currentAlarmInstanceProtocolNode.children[i].code);
                }
            }
        }
    }

    var divId = 'alarmInstancePropertiesContainer';
    var mask = mini.mask({
        el: divId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getAlarmUnitList',
        dataType: 'json',
        data: { protocol: protocolList.join(',') },
        success: function (response) {
            mini.unmask(divId);

            var unitList = response.unitList || [];

            var root = [];
            var classes = node.classes;

            if (classes === 0) {
                root.push({
                    id: 1,
                    title: _loginUserLanguageResource.rootNode,
                    value: _loginUserLanguageResource.instanceList
                });
            } else if (classes === 1) {
                root.push({ id: 1, title: _loginUserLanguageResource.instanceName, value: node.text });
                root.push({
                    id: 2,
                    title: _loginUserLanguageResource.alarmUnit,
                    value: (node.protocol || '') + '/' + (node.alarmUnitName || '')
                });
                root.push({ id: 3, title: _loginUserLanguageResource.sequenceNumber, value: node.sort });
            } else if (classes === 2) {
                root.push({ id: 1, title: _loginUserLanguageResource.alarmUnit, value: node.text });
            }

            if (protocolAlarmInstancePropertiesHandsontableHelper == null ||
                protocolAlarmInstancePropertiesHandsontableHelper.hot == undefined) {

                protocolAlarmInstancePropertiesHandsontableHelper =
                    ProtocolAlarmInstancePropertiesHandsontableHelper.createNew('alarmInstancePropertiesContainer');

                protocolAlarmInstancePropertiesHandsontableHelper.colHeaders = [
                    _loginUserLanguageResource.idx,
                    _loginUserLanguageResource.variable,
                    _loginUserLanguageResource.value
                ];
                protocolAlarmInstancePropertiesHandsontableHelper.columns = [
                    { data: 'id' },
                    { data: 'title' },
                    { data: 'value' }
                ];
                protocolAlarmInstancePropertiesHandsontableHelper.classes = classes;
                protocolAlarmInstancePropertiesHandsontableHelper.unitList = unitList;
                protocolAlarmInstancePropertiesHandsontableHelper.createTable(root);
            } else {
                protocolAlarmInstancePropertiesHandsontableHelper.classes = classes;
                protocolAlarmInstancePropertiesHandsontableHelper.unitList = unitList;
                protocolAlarmInstancePropertiesHandsontableHelper.hot.loadData(root);
            }
        },
        error: function () {
            mini.unmask(divId);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

//================================================================
//报警实例属性表格 Helper
//================================================================
var ProtocolAlarmInstancePropertiesHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.classes = null;
     helper.divid = divid;
     helper.validresult = true;
     helper.colHeaders = [];
     helper.columns = [];
     helper.AllData = [];
     helper.unitList = [];

     helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             colWidths: [1, 4, 5],
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
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualRowIndex = this.instance.toVisualRow(row);
                 var visualColIndex = this.instance.toVisualColumn(col);

                 var editFlag = (typeof loginUserProtocolConfigModuleRight !== 'undefined'
                     && loginUserProtocolConfigModuleRight != null
                     && loginUserProtocolConfigModuleRight.editFlag == 1);

                 if (editFlag) {
                     if (helper.classes === 0 || helper.classes === 2) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addBoldBg;
                     } else if (helper.classes === 1) {
                         if (visualColIndex === 2 && visualRowIndex === 0) {
                             // 实例名称
                             this.validator = function (val, callback) {
                                 return handsontableDataCheck_NotNull(val, callback, row, col, helper);
                             };
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualColIndex === 2 && visualRowIndex === 2) {
                             // 序号
                             this.validator = function (val, callback) {
                                 return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
                             };
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualColIndex === 2 && visualRowIndex === 1) {
                             // 报警单元
                             this.type = 'dropdown';
                             this.source = helper.unitList;
                             this.strict = true;
                             this.allowInvalid = false;
                         } else if (visualColIndex == 0 || visualColIndex == 1) {
                             cellProperties.editor = false;
                             cellProperties.renderer = helper.addBoldBg;
                         } else {
                             cellProperties.renderer = helper.addCellStyle;
                         }
                     }
                 } else {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addBoldBg;
                 }

                 return cellProperties;
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if (coords.col >= 0 && coords.row >= 0
                     && helper.columns[coords.col] && helper.columns[coords.col].type != 'checkbox'
                     && helper.hot && helper.hot.getDataAtCell) {
                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                     if (isNotVal(rawValue)) {
                         TD.title = rawValue;
                     }
                 }
             }
         });
     };

     helper.saveData = function () {};
     helper.clearContainer = function () {
         helper.AllData = [];
     };
     return helper;
 }
};

// ================================================================
// 4. 刷新方法
// ================================================================
function refreshAlarmInstanceProtocolTree() {
    var tree = mini.get('alarmInstanceProtocolTree');
    if (tree) tree.load();
}

function refreshAlarmInstanceList() {
    var tree = mini.get('alarmInstanceList');
    if (tree) tree.load();
}

// ================================================================
// 5. 保存报警实例配置
// ================================================================
function saveAlarmInstanceConfigData() {
    if (!_currentAlarmInstanceNode) {
        mini.alert(_loginUserLanguageResource.checkOne);
        return;
    }

    if (_currentAlarmInstanceNode.classes !== 1) {
        return;
    }

    var helper = protocolAlarmInstancePropertiesHandsontableHelper;
    if (!helper || !helper.hot) {
        mini.alert(_loginUserLanguageResource.noDataToSave);
        return;
    }

    var propertiesData = helper.hot.getData();
    if (!propertiesData || propertiesData.length < 3) {
        mini.alert(_loginUserLanguageResource.noDataToSave);
        return;
    }

    var node = _currentAlarmInstanceNode;
    var saveData = {};

    saveData.id = node.id;
    saveData.code = node.code;
    saveData.oldName = node.text;
    saveData.name = propertiesData[0][2];
    saveData.alarmUnitId = node.alarmUnitId;
    saveData.alarmUnitName = propertiesData[1][2];
    saveData.sort = propertiesData[2][2];

    saveModbusProtocolAlarmInstanceData(saveData);
}

// ================================================================
// 6. 统一提交函数
// ================================================================
function saveModbusProtocolAlarmInstanceData(saveData) {
    var protocolList = [];
    if (_currentAlarmInstanceProtocolNode) {
        if (_currentAlarmInstanceProtocolNode.classes === 1) {
            protocolList.push(_currentAlarmInstanceProtocolNode.code);
        } else {
            if (isNotVal(_currentAlarmInstanceProtocolNode.children)) {
                for (var i = 0; i < _currentAlarmInstanceProtocolNode.children.length; i++) {
                    protocolList.push(_currentAlarmInstanceProtocolNode.children[i].code);
                }
            }
        }
    }
    var protocolCodes = protocolList.join(',');

    var mask = mini.mask({
        el: document.body,
        html: _loginUserLanguageResource.updateWait
    });

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/saveProtocolAlarmInstanceData',
        data: {
            data: JSON.stringify(saveData),
            protocolCodes: protocolCodes
        },
        dataType: 'json',
        success: function (response) {
            mini.unmask(document.body);
            if (response && response.success) {
                if (saveData.delidslist && saveData.delidslist.length > 0) {
                    _selectedAlarmInstanceId = null;
                    _currentAlarmInstanceNode = null;
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                } else {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                }
                refreshAlarmInstanceList();
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
// 7. 右键菜单
// ================================================================
function onAlarmInstanceTreeBeforeMenu(e) {
    var tree = mini.get('alarmInstanceList');
    var menu = e.sender;
    var node = tree ? tree.getSelectedNode() : null;

    if (!node || node.classes !== 1) {
        e.cancel = true;
        if (e.htmlEvent) e.htmlEvent.preventDefault();
        return;
    }

    var deleteText = _loginUserLanguageResource.deleteData;
    var el = document.getElementById('alarmInstanceTreeMenuDeleteText');
    if (el) el.textContent = deleteText;

    var deleteItem = mini.getbyName('delete', menu);
    if (deleteItem) {
        if (typeof editFlag === 'undefined' || !editFlag) {
            deleteItem.disable();
        } else {
            deleteItem.enable();
        }
    }
}

function deleteAlarmInstanceNode(e) {
    var tree = mini.get('alarmInstanceList');
    var node = tree ? tree.getSelectedNode() : null;
    if (!node) return;
    if (node.classes !== 1) return;

    mini.confirm(
        _loginUserLanguageResource.confirmDelete,
        _loginUserLanguageResource.confirm,
        function (action) {
            if (action === 'ok') {
                var configInfo = {
                    delidslist: [node.id]
                };
                saveModbusProtocolAlarmInstanceData(configInfo);
            }
        }
    );
}

// ================================================================
// 8. 打开添加报警实例窗口
// ================================================================
function addAlarmInstanceInfo() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) return;
    var selectedDeviceNode = deviceTree.getSelectedNode();
    if (!selectedDeviceNode) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var deviceTypeIds = selectedDeviceTypeId || '';

    var protocolList = '';
    if (_currentAlarmInstanceProtocolNode) {
        if (_currentAlarmInstanceProtocolNode.classes === 1) {
            protocolList = _currentAlarmInstanceProtocolNode.code || '';
        } else if (_currentAlarmInstanceProtocolNode.classes === 0) {
            var codes = [];
            if (_currentAlarmInstanceProtocolNode.children) {
                for (var i = 0; i < _currentAlarmInstanceProtocolNode.children.length; i++) {
                    codes.push(_currentAlarmInstanceProtocolNode.children[i].code);
                }
            }
            protocolList = codes.join(',');
        }
    }

    mini.open({
        title: _loginUserLanguageResource.addAlarmInstance,
        url: context + '/miniui-app/modules/driverConfig/alarmInstanceAddWindow.jsp',
        width: 500,
        height: 350,
        modal: true,
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;

            contentWindow.setData({
                deviceTypeIds: deviceTypeIds,
                protocolList: protocolList
            });

            contentWindow._parentRefreshInstanceTree = function () {
                refreshAlarmInstanceList();
            };

            contentWindow._parentSetNewInstanceName = function (name) {
                window._newAlarmInstanceName = name;
            };
        }
    });
}

// ================================================================
// 9. 打开导出报警实例窗口
// ================================================================
function openExportAlarmInstanceWindow() {
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
    var deviceTypeIds = selectedDeviceTypeId || '';

    mini.open({
        title: _loginUserLanguageResource.exportAlarmInstance,
        url: context + '/miniui-app/modules/driverConfig/exportAlarmInstanceWindow.jsp',
        width: 450,
        height: 600,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: deviceTypeIds
            });
        }
    });
}

// ================================================================
// 10. 打开导入报警实例窗口
// ================================================================
function openImportAlarmInstanceWindow() {
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
        title: _loginUserLanguageResource.importAlarmInstance,
        url: context + '/miniui-app/modules/driverConfig/importAlarmInstanceWindow.jsp',
        width: 500,
        height: 700,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;

            contentWindow.setData({
                deviceTypeId: deviceTypeId,
                deviceTypeName: deviceTypeName
            });

            contentWindow.parent.refreshAlarmInstanceList = function () {
                refreshAlarmInstanceList();
            };
        }
    });
}