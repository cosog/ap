// ================================================================
// 显示实例配置模块
// 对应原 ExtJS: AP.view.acquisitionUnit.ModbusProtocolDisplayInstanceConfigInfoView
// ================================================================

// ---------- 状态变量 ----------
var _currentDisplayInstanceProtocolNode = null;
var _currentDisplayInstanceNode = null;
var _selectedDisplayInstanceId = null;

var protocolDisplayInstancePropertiesHandsontableHelper = null;

// ================================================================
// 1. 协议树事件
// ================================================================
function onDisplayInstanceProtocolTreeBeforeLoad(e) {
    var params = e.params || {};
    if (typeof selectedDeviceTypeId !== 'undefined' && selectedDeviceTypeId) {
        params.deviceTypeIds = selectedDeviceTypeId;
    }
    e.params = params;
}

function onDisplayInstanceProtocolTreeLoad(e) {
    var tree = e.sender;
    var root = tree.getRootNode();
    if (!root) return;

    var targetNode = null;

    // 优先恢复上次选中的协议
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

    // 否则选第一个协议节点
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

function onDisplayInstanceProtocolTreeSelect(e) {
    var node = e.node;
    _currentDisplayInstanceProtocolNode = node;

    if (node && node.classes === 1) {
        _selectedProtocolTreeNodeCode = node.code;
        _selectedDisplayInstanceId = null;
    }

    loadDisplayInstanceList(node);
}

function loadDisplayInstanceList(protocolNode) {
    var tree = mini.get('displayInstanceList');
    if (!tree) return;

    if (!tree.getUrl()) {
        tree.setUrl(context + '/acquisitionUnitManagerController/modbusDisplayInstanceConfigTreeData');
    }

    tree.load();
}

// ================================================================
// 2. 实例列表树事件
// ================================================================
function onDisplayInstanceListBeforeLoad(e) {
    var params = e.params || {};
    if (_currentDisplayInstanceProtocolNode) {
        if (_currentDisplayInstanceProtocolNode.classes === 1) {
            params.protocol = _currentDisplayInstanceProtocolNode.code;
        } else {
            var protocolList = [];
            if (isNotVal(_currentDisplayInstanceProtocolNode.children)) {
                for (var i = 0; i < _currentDisplayInstanceProtocolNode.children.length; i++) {
                    protocolList.push(_currentDisplayInstanceProtocolNode.children[i].code);
                }
            }
            params.protocol = protocolList.join(",");
        }
    }
    e.params = params;
}

function onDisplayInstanceListLoad(e) {
    var tree = e.sender;
 // ★ 首次加载时设置列
    if (!tree._columnsSet) {
        tree.setColumns([
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.displayInstanceList,
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
    if (window._newDisplayInstanceName) {
        (function find(node) {
            if (node.text === window._newDisplayInstanceName && node.classes === 1) {
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

        if (targetNode) window._newDisplayInstanceName = null;
    }

    // ★ 2. 恢复上次选中
    if (!targetNode && _selectedDisplayInstanceId) {
        (function find(node) {
            if (node.id === _selectedDisplayInstanceId && node.classes === 1) {
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

function onDisplayInstanceListSelect(e) {
    var node = e.node;
    if (!node) return;

    _currentDisplayInstanceNode = node;
    _selectedDisplayInstanceId = node.id;

    loadDisplayInstanceProperties(node);
}

// ================================================================
// 3. 加载显示实例属性表
// ================================================================
function loadDisplayInstanceProperties(node) {
    if (!node) return;

    var container = document.getElementById('displayInstancePropertiesContainer');
    if (!container) return;

    if (protocolDisplayInstancePropertiesHandsontableHelper) {
        if (protocolDisplayInstancePropertiesHandsontableHelper.hot) {
            protocolDisplayInstancePropertiesHandsontableHelper.hot.destroy();
        }
        protocolDisplayInstancePropertiesHandsontableHelper = null;
    }

    // 收集协议列表
    var protocolList = [];
    if (_currentDisplayInstanceProtocolNode) {
        if (_currentDisplayInstanceProtocolNode.classes === 1) {
            protocolList.push(_currentDisplayInstanceProtocolNode.code);
        } else {
            if (isNotVal(_currentDisplayInstanceProtocolNode.children)) {
                for (var i = 0; i < _currentDisplayInstanceProtocolNode.children.length; i++) {
                    protocolList.push(_currentDisplayInstanceProtocolNode.children[i].code);
                }
            }
        }
    }

    var divId = 'displayInstancePropertiesContainer';
    var mask = mini.mask({
        el: divId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getDisplayUnitList',
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
                    title: _loginUserLanguageResource.displayUnit,
                    value: (node.protocol || '') + '/' + (node.displayUnitName || '')
                });
                root.push({ id: 3, title: _loginUserLanguageResource.sequenceNumber, value: node.sort });
            } else if (classes === 2) {
                root.push({ id: 1, title: _loginUserLanguageResource.displayUnit, value: node.text });
            }

            if (protocolDisplayInstancePropertiesHandsontableHelper == null ||
                protocolDisplayInstancePropertiesHandsontableHelper.hot == undefined) {

                protocolDisplayInstancePropertiesHandsontableHelper =
                    ProtocolDisplayInstancePropertiesHandsontableHelper.createNew('displayInstancePropertiesContainer');

                protocolDisplayInstancePropertiesHandsontableHelper.colHeaders = [
                    _loginUserLanguageResource.idx,
                    _loginUserLanguageResource.variable,
                    _loginUserLanguageResource.value
                ];
                protocolDisplayInstancePropertiesHandsontableHelper.columns = [
                    { data: 'id' },
                    { data: 'title' },
                    { data: 'value' }
                ];
                protocolDisplayInstancePropertiesHandsontableHelper.classes = classes;
                protocolDisplayInstancePropertiesHandsontableHelper.unitList = unitList;
                protocolDisplayInstancePropertiesHandsontableHelper.createTable(root);
            } else {
                protocolDisplayInstancePropertiesHandsontableHelper.classes = classes;
                protocolDisplayInstancePropertiesHandsontableHelper.unitList = unitList;
                protocolDisplayInstancePropertiesHandsontableHelper.hot.loadData(root);
            }
        },
        error: function () {
            mini.unmask(divId);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

//================================================================
//显示实例属性表格 Helper
//================================================================
var ProtocolDisplayInstancePropertiesHandsontableHelper = {
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
                         } else if (visualColIndex === 2 && visualRowIndex === 2) {
                             // 序号
                             this.validator = function (val, callback) {
                                 return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
                             };
                         } else if (visualColIndex === 2 && visualRowIndex === 1) {
                             // 显示单元
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
function refreshDisplayInstanceProtocolTree() {
    var tree = mini.get('displayInstanceProtocolTree');
    if (tree) tree.load();
}

function refreshDisplayInstanceList() {
    var tree = mini.get('displayInstanceList');
    if (tree) tree.load();
}

// ================================================================
// 5. 保存显示实例配置
// ================================================================
function saveDisplayInstanceConfigData() {
    if (!_currentDisplayInstanceNode) {
        mini.alert(_loginUserLanguageResource.checkOne);
        return;
    }

    if (_currentDisplayInstanceNode.classes !== 1) {
        return;
    }

    var helper = protocolDisplayInstancePropertiesHandsontableHelper;
    if (!helper || !helper.hot) {
        mini.alert(_loginUserLanguageResource.noDataToSave);
        return;
    }

    var propertiesData = helper.hot.getData();
    if (!propertiesData || propertiesData.length < 3) {
        mini.alert(_loginUserLanguageResource.noDataToSave);
        return;
    }

    var node = _currentDisplayInstanceNode;
    var saveData = {};

    saveData.id = node.id;
    saveData.code = node.code;
    saveData.oldName = node.text;
    saveData.name = propertiesData[0][2];
    saveData.displayUnitId = node.displayUnitId;
    saveData.displayUnitName = propertiesData[1][2];
    saveData.sort = propertiesData[2][2];

    saveModbusProtocolDisplayInstanceData(saveData);
}

// ================================================================
// 6. 统一提交函数
// ================================================================
function saveModbusProtocolDisplayInstanceData(saveData) {
    var protocolList = [];
    if (_currentDisplayInstanceProtocolNode) {
        if (_currentDisplayInstanceProtocolNode.classes === 1) {
            protocolList.push(_currentDisplayInstanceProtocolNode.code);
        } else {
            if (isNotVal(_currentDisplayInstanceProtocolNode.children)) {
                for (var i = 0; i < _currentDisplayInstanceProtocolNode.children.length; i++) {
                    protocolList.push(_currentDisplayInstanceProtocolNode.children[i].code);
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
        url: context + '/acquisitionUnitManagerController/saveProtocolDisplayInstanceData',
        data: {
            data: JSON.stringify(saveData),
            protocolCodes: protocolCodes
        },
        dataType: 'json',
        success: function (response) {
            mini.unmask(document.body);
            if (response && response.success) {
                if (saveData.delidslist && saveData.delidslist.length > 0) {
                    _selectedDisplayInstanceId = null;
                    _currentDisplayInstanceNode = null;
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                } else {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                }
                refreshDisplayInstanceList();
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
function onDisplayInstanceTreeBeforeMenu(e) {
    var tree = mini.get('displayInstanceList');
    var menu = e.sender;
    var node = tree ? tree.getSelectedNode() : null;

    if (!node || node.classes !== 1) {
        e.cancel = true;
        if (e.htmlEvent) e.htmlEvent.preventDefault();
        return;
    }

    var deleteText = _loginUserLanguageResource.deleteData;
    var el = document.getElementById('displayInstanceTreeMenuDeleteText');
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

function deleteDisplayInstanceNode(e) {
    var tree = mini.get('displayInstanceList');
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
                saveModbusProtocolDisplayInstanceData(configInfo);
            }
        }
    );
}

// ================================================================
// 8. 打开添加显示实例窗口
// ================================================================
function addDisplayInstanceInfo() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) return;
    var selectedDeviceNode = deviceTree.getSelectedNode();
    if (!selectedDeviceNode) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var deviceTypeIds = selectedDeviceTypeId || '';

    var protocolList = '';
    if (_currentDisplayInstanceProtocolNode) {
        if (_currentDisplayInstanceProtocolNode.classes === 1) {
            protocolList = _currentDisplayInstanceProtocolNode.code || '';
        } else if (_currentDisplayInstanceProtocolNode.classes === 0) {
            var codes = [];
            if (_currentDisplayInstanceProtocolNode.children) {
                for (var i = 0; i < _currentDisplayInstanceProtocolNode.children.length; i++) {
                    codes.push(_currentDisplayInstanceProtocolNode.children[i].code);
                }
            }
            protocolList = codes.join(',');
        }
    }

    mini.open({
        title: _loginUserLanguageResource.addDisplayInstance,
        url: context + '/miniui-app/modules/driverConfig/displayInstanceAddWindow.jsp',
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
                refreshDisplayInstanceList();
            };

            contentWindow._parentSetNewInstanceName = function (name) {
                window._newDisplayInstanceName = name;
            };
        }
    });
}

// ================================================================
// 9. 打开导出显示实例窗口
// ================================================================
function openExportDisplayInstanceWindow() {
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
        title: _loginUserLanguageResource.exportDisplayInstance,
        url: context + '/miniui-app/modules/driverConfig/exportDisplayInstanceWindow.jsp',
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
// 10. 打开导入显示实例窗口
// ================================================================
function openImportDisplayInstanceWindow() {
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
        title: _loginUserLanguageResource.importDisplayInstance,
        url: context + '/miniui-app/modules/driverConfig/importDisplayInstanceWindow.jsp',
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

            contentWindow.parent.refreshDisplayInstanceList = function () {
                refreshDisplayInstanceList();
            };
        }
    });
}