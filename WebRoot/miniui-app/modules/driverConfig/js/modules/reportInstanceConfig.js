// ================================================================
// 报表实例配置模块（只有一棵实例树，无协议树）
// 对应原 ExtJS: AP.view.acquisitionUnit.ModbusProtocolReportInstanceConfigInfoView
// ================================================================

// ---------- 状态变量 ----------
var _currentReportInstanceNode = null;
var _selectedReportInstanceId = null;

var protocolReportInstancePropertiesHandsontableHelper = null;

// ================================================================
// 1. 实例树事件
// ================================================================

// 报表实例树 beforeload：无额外参数（与 ExtJS 一致）
function onReportInstanceTreeBeforeLoad(e) {
    // 无参
}

function onReportInstanceTreeLoad(e) {
    var tree = e.sender;
 // ★ 首次加载时设置列
    if (!tree._columnsSet) {
        tree.setColumns([
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.reportInstanceList,
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
    if (window._newReportInstanceName) {
        (function find(node) {
            if (node.text === window._newReportInstanceName && node.classes === 1) {
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
        if (targetNode) window._newReportInstanceName = null;
    }

    // ★ 2. 恢复上次选中
    if (!targetNode && _selectedReportInstanceId) {
        (function find(node) {
            if (node.id === _selectedReportInstanceId && node.classes === 1) {
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

function onReportInstanceTreeSelect(e) {
    var node = e.node;
    if (!node) return;

    _currentReportInstanceNode = node;
    _selectedReportInstanceId = node.id;

    loadReportInstanceProperties(node);
}

// 加载实例树
function loadReportInstanceTree() {
    var tree = mini.get('reportInstanceList');
    if (!tree) return;
    if (!tree.getUrl()) {
        tree.setUrl(context + '/acquisitionUnitManagerController/modbusReportInstanceConfigTreeData');
    }
    tree.load();
}

// ================================================================
// 2. 加载报表实例属性表
// ================================================================
function loadReportInstanceProperties(node) {
    if (!node) return;

    var container = document.getElementById('reportInstancePropertiesContainer');
    if (!container) return;

    // 销毁旧表
    if (protocolReportInstancePropertiesHandsontableHelper) {
        if (protocolReportInstancePropertiesHandsontableHelper.hot) {
            protocolReportInstancePropertiesHandsontableHelper.hot.destroy();
        }
        protocolReportInstancePropertiesHandsontableHelper = null;
    }

    var divId = 'reportInstancePropertiesContainer';
    var mask = mini.mask({
        el: divId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/getReportUnitList',
        dataType: 'json',
        success: function (response) {
            mini.unmask(divId);

            var unitList = response.unitList || [];

            var root = [];
            var hiddenRows = [];
            var classes = node.classes;

            if (classes === 0) {
                root.push({
                    id: 1,
                    title: _loginUserLanguageResource.rootNode,
                    value: _loginUserLanguageResource.instanceList
                });
            } else if (classes === 1) {
                // ★ 5 行：中文 / 英文 / 俄文 / 报表单元 / 序号
                // 根据 _loginUserLanguageList 决定每行是否隐藏
                var langList = (typeof _loginUserLanguageList !== 'undefined' && _loginUserLanguageList) ? _loginUserLanguageList : [];

                // 中文
                root.push({ id: 1, title: _loginUserLanguageResource.language_zh_CN, value: node.name_zh_CN });
                if (isExist(langList, 1) === 0) hiddenRows.push(0);

                // 英文
                root.push({ id: 2, title: _loginUserLanguageResource.language_en, value: node.name_en });
                if (isExist(langList, 2) === 0) hiddenRows.push(1);

                // 俄文
                root.push({ id: 3, title: _loginUserLanguageResource.language_ru, value: node.name_ru });
                if (isExist(langList, 3) === 0) hiddenRows.push(2);

                // 报表单元
                root.push({ id: 4, title: _loginUserLanguageResource.reportUnit, value: node.unitName });

                // 序号
                root.push({ id: 5, title: _loginUserLanguageResource.sequenceNumber, value: node.sort });
            } else if (classes === 2) {
                root.push({ id: 1, title: _loginUserLanguageResource.reportUnit, value: node.text });
            }

            if (protocolReportInstancePropertiesHandsontableHelper == null ||
                protocolReportInstancePropertiesHandsontableHelper.hot == undefined) {

                protocolReportInstancePropertiesHandsontableHelper =
                    ProtocolReportInstancePropertiesHandsontableHelper.createNew('reportInstancePropertiesContainer');

                protocolReportInstancePropertiesHandsontableHelper.colHeaders = [
                    _loginUserLanguageResource.idx,
                    _loginUserLanguageResource.variable,
                    _loginUserLanguageResource.value
                ];
                protocolReportInstancePropertiesHandsontableHelper.columns = [
                    { data: 'id' },
                    { data: 'title' },
                    { data: 'value' }
                ];
                protocolReportInstancePropertiesHandsontableHelper.classes = classes;
                protocolReportInstancePropertiesHandsontableHelper.unitList = unitList;
                protocolReportInstancePropertiesHandsontableHelper.createTable(root);
            } else {
                protocolReportInstancePropertiesHandsontableHelper.classes = classes;
                protocolReportInstancePropertiesHandsontableHelper.unitList = unitList;
                protocolReportInstancePropertiesHandsontableHelper.hot.loadData(root);
            }

            // 处理隐藏行
            if (protocolReportInstancePropertiesHandsontableHelper
                && protocolReportInstancePropertiesHandsontableHelper.hot) {
                var plugin = protocolReportInstancePropertiesHandsontableHelper.hot.getPlugin('hiddenRows');
                if (hiddenRows.length > 0) {
                    plugin.hideRows(hiddenRows);
                } else {
                    plugin.showRows([0, 1, 2]);
                }
                protocolReportInstancePropertiesHandsontableHelper.hot.render();
            }
        },
        error: function () {
            mini.unmask(divId);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

//================================================================
//报表实例属性表格 Helper
//================================================================
var ProtocolReportInstancePropertiesHandsontableHelper = {
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
             hiddenRows: {
                 rows: [],
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: [1, 4, 7],
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
                 // row 是物理行号（数据行号），不受 hiddenRows 影响
                 var visualColIndex = this.instance.toVisualColumn(col);

                 var editFlag = (typeof loginUserProtocolConfigModuleRight !== 'undefined'
                     && loginUserProtocolConfigModuleRight != null
                     && loginUserProtocolConfigModuleRight.editFlag == 1);

                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addBoldBg;
                     return cellProperties;
                 }

                 // 根/单元节点只读
                 if (helper.classes === 0 || helper.classes === 2) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addBoldBg;
                     return cellProperties;
                 }

                 // 实例节点（classes === 1）
                 if (visualColIndex === 0 || visualColIndex === 1) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addBoldBg;
                     return cellProperties;
                 }

                 if (visualColIndex === 2) {
                     // 物理行 0/1/2：中文/英文/俄文名称
                     if (row >= 0 && row <= 2) {
                         // 判断当前语言对应的物理行
                         var lang = (typeof loginUserLanguage !== 'undefined' ? loginUserLanguage : '').toUpperCase();
                         var currentLangRow = -1;
                         if (lang === 'ZH_CN') currentLangRow = 0;
                         else if (lang === 'EN') currentLangRow = 1;
                         else if (lang === 'RU') currentLangRow = 2;

                         // 只有当前语言行做非空校验
                         if (row === currentLangRow) {
                             this.validator = function (val, callback) {
                                 return handsontableDataCheck_NotNull(val, callback, row, col, helper);
                             };
                         }
                         cellProperties.renderer = helper.addCellStyle;
                     } else if (row === 3) {
                         // 报表单元下拉
                         this.type = 'dropdown';
                         this.source = helper.unitList;
                         this.strict = true;
                         this.allowInvalid = false;
                         cellProperties.renderer = helper.addCellStyle;
                     } else if (row === 4) {
                         // 序号
                         this.validator = function (val, callback) {
                             return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
                         };
                         cellProperties.renderer = helper.addCellStyle;
                     }
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
// 3. 刷新
// ================================================================
function refreshReportInstanceList() {
    var tree = mini.get('reportInstanceList');
    if (tree) tree.load();
}

// ================================================================
// 4. 保存报表实例配置
// ================================================================
function saveReportInstanceData() {
    if (!_currentReportInstanceNode) {
        mini.alert(_loginUserLanguageResource.checkOne);
        return;
    }
    if (_currentReportInstanceNode.classes !== 1) return;

    var helper = protocolReportInstancePropertiesHandsontableHelper;
    if (!helper || !helper.hot) {
        mini.alert(_loginUserLanguageResource.noDataToSave);
        return;
    }

    var propertiesData = helper.hot.getData();
    if (!propertiesData || propertiesData.length < 5) {
        mini.alert(_loginUserLanguageResource.noDataToSave);
        return;
    }

    var node = _currentReportInstanceNode;
    var saveData = {};

    saveData.id = node.id;
    saveData.code = node.code;
    saveData.oldName = node.text;
    // ★ 物理行索引固定：0/1/2=名称, 3=报表单元, 4=序号
    saveData.name_zh_CN = propertiesData[0][2];
    saveData.name_en = propertiesData[1][2];
    saveData.name_ru = propertiesData[2][2];
    saveData.unitName = propertiesData[3][2];
    saveData.sort = propertiesData[4][2];

    saveModbusProtocolReportInstanceData(saveData);
}

// ================================================================
// 5. 统一提交函数
// ================================================================
function saveModbusProtocolReportInstanceData(saveData) {
    var mask = mini.mask({
        el: document.body,
        html: _loginUserLanguageResource.updateWait
    });

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/saveProtocolReportInstanceData',
        data: { data: JSON.stringify(saveData) },
        dataType: 'json',
        success: function (response) {
            mini.unmask(document.body);
            if (response && response.success) {
                if (saveData.delidslist && saveData.delidslist.length > 0) {
                    _selectedReportInstanceId = null;
                    _currentReportInstanceNode = null;
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                } else {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                }
                refreshReportInstanceList();
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
// 6. 右键菜单
// ================================================================
function onReportInstanceTreeBeforeMenu(e) {
    var tree = mini.get('reportInstanceList');
    var menu = e.sender;
    var node = tree ? tree.getSelectedNode() : null;

    if (!node || node.classes !== 1) {
        e.cancel = true;
        if (e.htmlEvent) e.htmlEvent.preventDefault();
        return;
    }

    var deleteText = _loginUserLanguageResource.deleteData;
    var el = document.getElementById('reportInstanceTreeMenuDeleteText');
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

function deleteReportInstanceNode(e) {
    var tree = mini.get('reportInstanceList');
    var node = tree ? tree.getSelectedNode() : null;
    if (!node) return;
    if (node.classes !== 1) return;

    mini.confirm(
        _loginUserLanguageResource.confirmDelete,
        _loginUserLanguageResource.confirm,
        function (action) {
            if (action === 'ok') {
                var configInfo = { delidslist: [node.id] };
                saveModbusProtocolReportInstanceData(configInfo);
            }
        }
    );
}

// ================================================================
// 7. 打开添加报表实例窗口
// ================================================================
function addReportInstanceInfo() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) return;
    var selectedDeviceNode = deviceTree.getSelectedNode();
    if (!selectedDeviceNode) {
        mini.alert(_loginUserLanguageResource.selectDeviceType);
        return;
    }
    var deviceTypeIds = selectedDeviceTypeId || '';
    var deviceTypeName = getNodePath(deviceTree, selectedDeviceNode);

    mini.open({
        title: _loginUserLanguageResource.addReportInstance,
        url: context + '/miniui-app/modules/driverConfig/reportInstanceAddWindow.jsp',
        width: 500,
        height: 380,
        modal: true,
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;

            contentWindow.setData({
                deviceTypeIds: deviceTypeIds,
                deviceTypeName: deviceTypeName
            });

            contentWindow._parentRefreshInstanceTree = function () {
                refreshReportInstanceList();
            };
            contentWindow._parentSetNewInstanceName = function (name) {
                window._newReportInstanceName = name;
            };
        }
    });
}

// ================================================================
// 8. 打开导出报表实例窗口
// ================================================================
function openExportReportInstanceWindow() {
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
        title: _loginUserLanguageResource.exportReportInstance,
        url: context + '/miniui-app/modules/driverConfig/exportReportInstanceWindow.jsp',
        width: 450,
        height: 600,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({ deviceTypeIds: deviceTypeIds });
        }
    });
}

// ================================================================
// 9. 打开导入报表实例窗口
// ================================================================
function openImportReportInstanceWindow() {
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
        title: _loginUserLanguageResource.importReportInstance,
        url: context + '/miniui-app/modules/driverConfig/importReportInstanceWindow.jsp',
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
            contentWindow.parent.refreshReportInstanceList = function () {
                refreshReportInstanceList();
            };
        }
    });
}