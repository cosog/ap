// ================================================================
// 采控实例配置模块
// ================================================================

// ---------- 状态变量 ----------
var _currentAcqInstanceProtocolNode = null;   // 当前选中的协议节点
var _currentAcqInstanceNode = null;           // 当前选中的实例节点
var _selectedAcqInstanceId = null;            // 上次选中的实例 ID（用于恢复）

var protocolConfigInstancePropertiesHandsontableHelper = null;

// ================================================================
// 1. 协议树事件
// ================================================================

/**
 * 协议树加载前：附加设备类型 ID 参数
 * 对应原 store: ModbusProtocolAcqInstanceProtocolTreeInfoStore.beforeload
 */
function onAcqInstanceProtocolTreeBeforeLoad(e) {
    var params = e.params || {};
    if (typeof selectedDeviceTypeId !== 'undefined' && selectedDeviceTypeId) {
        params.deviceTypeIds = selectedDeviceTypeId;
    }
    e.params = params;
}

/**
 * 协议树加载完成：自动选中第一个协议或上次选中的协议
 */
function onAcqInstanceProtocolTreeLoad(e) {
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

    // 否则选第一个协议节点（classes === 1）
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

/**
 * 协议树节点选中：加载对应的实例列表
 */
function onAcqInstanceProtocolTreeSelect(e) {
    var node = e.node;
    _currentAcqInstanceProtocolNode = node;

    if (node && node.classes === 1) {
        // 记录选中的协议 code
        _selectedProtocolTreeNodeCode = node.code;
        // 清空实例选中状态
        _selectedAcqInstanceId = null;
    }

    loadAcqInstanceList(node);
}

/**
 * 加载实例列表树
 */
function loadAcqInstanceList(protocolNode) {
    var tree = mini.get('acqInstanceList');
    if (!tree) return;

    // 设置 URL（首次）
    if (!tree.getUrl()) {
        tree.setUrl(context + '/acquisitionUnitManagerController/modbusInstanceConfigTreeData');
    }

    tree.load();
}

// ================================================================
// 2. 实例列表树事件
// ================================================================

/**
 * 实例列表加载前：附加协议参数
 * 对应原 store: ModbusProtocolInstanceTreeInfoStore.beforeload
 */
function onAcqInstanceListBeforeLoad(e) {
    var params = e.params || {};
    if (_currentAcqInstanceProtocolNode) {
        if (_currentAcqInstanceProtocolNode.classes === 1) {
            params.protocol = _currentAcqInstanceProtocolNode.code;
        } else {
            // 目录节点：收集所有子协议 code
            var protocolList = [];
            if (isNotVal(_currentAcqInstanceProtocolNode.children)) {
                for (var i = 0; i < _currentAcqInstanceProtocolNode.children.length; i++) {
                    protocolList.push(_currentAcqInstanceProtocolNode.children[i].code);
                }
            }
            params.protocol = protocolList.join(",");
        }
    }
    e.params = params;
}

/**
 * 实例列表加载完成：自动选中第一个实例或上次选中的实例
 */
function onAcqInstanceListLoad(e) {
    var tree = e.sender;
    
 // ★ 首次加载时设置列
    if (!tree._columnsSet) {
        tree.setColumns([
            {
                field: 'text',
                name: 'taskname',
                header: _loginUserLanguageResource.acqInstanceList,
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

    // ★ 1. 优先高亮新增的实例（名称匹配 + classes === 1）
    if (window._newAcqInstanceName) {
        (function find(node) {
            if (node.text === window._newAcqInstanceName && node.classes === 1) {
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

        if (targetNode) {
            window._newAcqInstanceName = null; // 清除标记
        }
    }

    // ★ 2. 若没有新增高亮，按之前选中的实例 ID 恢复
    if (!targetNode && _selectedAcqInstanceId) {
        (function find(node) {
            if (node.id === _selectedAcqInstanceId && node.classes === 1) {
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

    // ★ 3. 若无目标，则选第一个实例节点
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
/**
 * 实例列表节点选中：显示实例属性
 */
function onAcqInstanceListSelect(e) {
    var node = e.node;
    if (!node) return;

    _currentAcqInstanceNode = node;
    _selectedAcqInstanceId = node.id;

    loadAcqInstanceProperties(node);
}

//================================================================
//加载采控实例属性表
//================================================================
function loadAcqInstanceProperties(node) {
 if (!node) return;

 var container = document.getElementById('acqInstancePropertiesContainer');
 if (!container) return;

 // 销毁旧表格
 if (protocolConfigInstancePropertiesHandsontableHelper) {
     if (protocolConfigInstancePropertiesHandsontableHelper.hot) {
         protocolConfigInstancePropertiesHandsontableHelper.hot.destroy();
     }
     protocolConfigInstancePropertiesHandsontableHelper = null;
 }

 // ★ 收集协议列表（从选中的协议树节点获取）
 var protocolList = [];
 if (_currentAcqInstanceProtocolNode) {
     if (_currentAcqInstanceProtocolNode.classes === 1) {
         protocolList.push(_currentAcqInstanceProtocolNode.code);
     } else {
         if (isNotVal(_currentAcqInstanceProtocolNode.children)) {
             for (var i = 0; i < _currentAcqInstanceProtocolNode.children.length; i++) {
                 protocolList.push(_currentAcqInstanceProtocolNode.children[i].code);
             }
         }
     }
 }
 
 var divId='acqInstancePropertiesContainer';
 var mask = mini.mask({
     el: divId,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 // ★ 请求采集单元列表（用于下拉框）
 $.ajax({
     method: 'POST',
     url: context + '/acquisitionUnitManagerController/getAcqUnitList',
     dataType: 'json',
     data: { protocol: protocolList.join(',') },
     success: function (response) {
    	 mini.unmask(divId);
    	 
    	 var unitList = response.unitList || [];
         var unitIdNameList = response.unitIdNameList || [];

         var root = [];
         var hiddenRows = [];
         var classes = node.classes;

         // ---- 构建表格数据 ----
         if (classes === 0) {
             root.push({
                 id: 1,
                 title: _loginUserLanguageResource.rootNode,
                 value: _loginUserLanguageResource.instanceList
             });
         } else if (classes === 1) {
             // 实例节点
             root.push({ id: 1, title: _loginUserLanguageResource.instanceName, value: node.text });
             root.push({
                 id: 2,
                 title: _loginUserLanguageResource.acqUnit,
                 value: (node.protocol || '') + '/' + (node.unitName || '')
             });
             root.push({ id: 3, title: _loginUserLanguageResource.acqProtocolType, value: node.acqProtocolType });

             var acqProtocolType = node.acqProtocolType || '';
             if (acqProtocolType.indexOf('private-') === 0) {
                 hiddenRows = [4, 5, 6, 7, 8, 9, 10, 11];
             }

             root.push({ id: 4, title: _loginUserLanguageResource.ctrlProtocolType, value: node.ctrlProtocolType });
             root.push({
                 id: 5,
                 title: _loginUserLanguageResource.signInPrefixSuffixHex,
                 value: parseInt(node.signInPrefixSuffixHex) === 1
             });
             root.push({ id: 6, title: _loginUserLanguageResource.signInPrefix + '(HEX/ASC)', value: node.signInPrefix });
             root.push({ id: 7, title: _loginUserLanguageResource.signInSuffix + '(HEX/ASC)', value: node.signInSuffix });
             root.push({
                 id: 8,
                 title: _loginUserLanguageResource.signInIDHex,
                 value: parseInt(node.signInIDHex) === 1
             });
             root.push({
                 id: 9,
                 title: _loginUserLanguageResource.heartbeatPrefixSuffixHex,
                 value: parseInt(node.heartbeatPrefixSuffixHex) === 1
             });
             root.push({ id: 10, title: _loginUserLanguageResource.heartbeatPrefix + '(HEX/ASC)', value: node.heartbeatPrefix });
             root.push({ id: 11, title: _loginUserLanguageResource.heartbeatSuffix + '(HEX/ASC)', value: node.heartbeatSuffix });
             root.push({
                 id: 12,
                 title: _loginUserLanguageResource.packetSendInterval + '(ms)',
                 value: node.packetSendInterval
             });

             // ★ 序号 id 根据隐藏行数动态调整（与 ExtJS 一致）
             var sortId = 13;
             if (hiddenRows.length > 0) {
                 sortId = 13 - hiddenRows.length;
             }
             root.push({ id: sortId, title: _loginUserLanguageResource.sequenceNumber, value: node.sort });

         } else if (classes === 2) {
             // 采集单元节点
             root.push({ id: 1, title: _loginUserLanguageResource.acqUnit, value: node.text });
         }

         // ---- 创建 / 复用 Helper ----
         if (protocolConfigInstancePropertiesHandsontableHelper == null
             || protocolConfigInstancePropertiesHandsontableHelper.hot == undefined) {

             protocolConfigInstancePropertiesHandsontableHelper =
                 ProtocolConfigInstancePropertiesHandsontableHelper.createNew('acqInstancePropertiesContainer');

             protocolConfigInstancePropertiesHandsontableHelper.colHeaders = [
                 _loginUserLanguageResource.idx,
                 _loginUserLanguageResource.variable,
                 _loginUserLanguageResource.value
             ];
             protocolConfigInstancePropertiesHandsontableHelper.columns = [
                 { data: 'id' },
                 { data: 'title' },
                 { data: 'value' }
             ];
             protocolConfigInstancePropertiesHandsontableHelper.classes = classes;
             protocolConfigInstancePropertiesHandsontableHelper.unitList = unitList;
             protocolConfigInstancePropertiesHandsontableHelper.unitIdNameList = unitIdNameList;
             protocolConfigInstancePropertiesHandsontableHelper.createTable(root);

         } else {
             protocolConfigInstancePropertiesHandsontableHelper.classes = classes;
             protocolConfigInstancePropertiesHandsontableHelper.unitList = unitList;
             protocolConfigInstancePropertiesHandsontableHelper.unitIdNameList = unitIdNameList;
             protocolConfigInstancePropertiesHandsontableHelper.hot.loadData(root);
         }

         // ---- 处理隐藏行 ----
         if (protocolConfigInstancePropertiesHandsontableHelper
             && protocolConfigInstancePropertiesHandsontableHelper.hot) {
             var plugin = protocolConfigInstancePropertiesHandsontableHelper.hot.getPlugin('hiddenRows');
             if (hiddenRows.length > 0) {
                 plugin.hideRows(hiddenRows);
             } else {
                 plugin.showRows(hiddenRows);
             }
             protocolConfigInstancePropertiesHandsontableHelper.hot.render();
         }
     },
     error: function () {
    	 mini.unmask(divId);
         mini.alert(_loginUserLanguageResource.requestFailed);
     }
 });
}


// ================================================================
// 刷新方法
// ================================================================
function refreshAcqInstanceProtocolTree() {
    var tree = mini.get('acqInstanceProtocolTree');
    if (tree) tree.load();
}

function refreshAcqInstanceList() {
    var tree = mini.get('acqInstanceList');
    if (tree) tree.load();
}

//================================================================
//采控实例属性表格 Helper
//对应原 ExtJS: ProtocolConfigInstancePropertiesHandsontableHelper
//================================================================
var ProtocolConfigInstancePropertiesHandsontableHelper = {
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
     helper.unitIdNameList = [];

     helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
         if (cellProperties.type === 'checkbox') {
             Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
         } else if (cellProperties.type === 'dropdown') {
             Handsontable.renderers.DropdownRenderer.apply(this, arguments);
         } else {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
         }
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
             hiddenRows: {
                 rows: [],
                 indicators: false,
                 copyPasteEnabled: false
             },
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

                 // 权限判断
                 var editFlag = (typeof loginUserProtocolConfigModuleRight !== 'undefined'
                     && loginUserProtocolConfigModuleRight != null
                     && loginUserProtocolConfigModuleRight.editFlag == 1);

                 if (editFlag) {
                     if (visualColIndex == 0 || visualColIndex == 1) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addBoldBg;
                     }
                     if (helper.classes === 0 || helper.classes === 2) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addBoldBg;
                     } else if (helper.classes === 1) {
                         // 实例节点
                         if (visualColIndex === 2 && visualRowIndex === 0) {
                             // 实例名称：非空校验
                             this.validator = function (val, callback) {
                                 return handsontableDataCheck_NotNull(val, callback, row, col, helper);
                             };
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualColIndex === 2 && visualRowIndex === 1) {
                             // 采集单元：下拉框
                             this.type = 'dropdown';
                             this.strict = true;
                             this.allowInvalid = false;
                             this.source = helper.unitList;
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualColIndex === 2 && visualRowIndex === 2) {
                             // 采集协议类型
                             this.type = 'dropdown';
                             this.source = ['modbus-tcp', 'modbus-rtu', 'private-kd93', 'private-lq1000', 'private-g771'];
                             this.strict = true;
                             this.allowInvalid = false;
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualColIndex === 2 && visualRowIndex === 3) {
                             // 控制协议类型
                             this.type = 'dropdown';
                             this.source = ['modbus-tcp', 'modbus-rtu'];
                             this.strict = true;
                             this.allowInvalid = false;
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualColIndex === 2 && (visualRowIndex === 4 || visualRowIndex === 7 || visualRowIndex === 8)) {
                             // 登录前后缀HEX、登录ID HEX、心跳前后缀HEX：复选框
                             this.type = 'checkbox';
                         } else if (visualColIndex === 2 && (visualRowIndex === 11 || visualRowIndex === 12)) {
                             // 包发送间隔、序号：可空数字
                             this.validator = function (val, callback) {
                                 return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
                             };
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualColIndex === 2 && (visualRowIndex === 5 || visualRowIndex === 6)) {
                             // 登录前缀、登录后缀
                             if (helper.hot && helper.hot.getDataAtCell) {
                                 var signInPrefixSuffixHex = helper.hot.getDataAtCell(4, 2);
                                 if (signInPrefixSuffixHex) {
                                     this.validator = function (val, callback) {
                                         return handsontableDataCheck_HexStr_Nullable(val, callback, row, col, helper);
                                     };
                                 }
                             }
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualColIndex === 2 && (visualRowIndex === 9 || visualRowIndex === 10)) {
                             // 心跳前缀、心跳后缀
                             if (helper.hot && helper.hot.getDataAtCell) {
                                 var heartbeatPrefixSuffixHex = helper.hot.getDataAtCell(8, 2);
                                 if (heartbeatPrefixSuffixHex) {
                                     this.validator = function (val, callback) {
                                         return handsontableDataCheck_HexStr_Nullable(val, callback, row, col, helper);
                                     };
                                 }
                             }
                             cellProperties.renderer = helper.addCellStyle;
                         }
                     }
                 } else {
                     // 无编辑权限
                     if (visualColIndex === 2 && (visualRowIndex === 4 || visualRowIndex === 7 || visualRowIndex === 8)) {
                         this.type = 'checkbox';
                     }
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addBoldBg;
                 }

                 return cellProperties;
             },
             beforeChange: function (changes, source) {
                 if (!changes) return true;
                 var editFlag = (typeof loginUserProtocolConfigModuleRight !== 'undefined'
                     && loginUserProtocolConfigModuleRight != null
                     && loginUserProtocolConfigModuleRight.editFlag == 1);
                 if (!editFlag) return false;
                 return true;
             },
             afterChange: function (changes, source) {
                 if (!changes) return;
                 changes.forEach(function (change) {
                     var row = change[0], prop = change[1], oldVal = change[2], newVal = change[3];
                     if (row === 2 && prop === 'value') {
                         // 采集协议类型变化：控制隐藏行
                         var plugin = helper.hot.getPlugin('hiddenRows');
                         var hiddenRows = [4, 5, 6, 7, 8, 9, 10, 11];
                         if (newVal && newVal.indexOf('private-') === 0) {
                             plugin.hideRows(hiddenRows);
                             helper.hot.setDataAtRowProp(12, 'id', 5);
                         } else {
                             plugin.showRows(hiddenRows);
                             helper.hot.setDataAtRowProp(12, 'id', 13);
                         }
                         helper.hot.render();
                     }
                 });
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if ((coords.col < 2 || (coords.col == 2 && coords.row != 4 && coords.row != 7 && coords.row != 8))
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

//================================================================
//打开"添加采控实例"窗口
//================================================================
function addAcqInstanceInfo() {
 var deviceTree = mini.get('deviceTypeTree');
 if (!deviceTree) return;
 var selectedDeviceNode = deviceTree.getSelectedNode();
 if (!selectedDeviceNode) {
     mini.alert(_loginUserLanguageResource.selectDeviceType);
     return;
 }
 var deviceTypeIds = selectedDeviceTypeId || '';

 // 收集当前选中的协议 code（用于过滤采集单元树）
 var protocolList = '';
 if (_currentAcqInstanceProtocolNode) {
     if (_currentAcqInstanceProtocolNode.classes === 1) {
         protocolList = _currentAcqInstanceProtocolNode.code || '';
     } else if (_currentAcqInstanceProtocolNode.classes === 0) {
         var codes = [];
         if (_currentAcqInstanceProtocolNode.children) {
             for (var i = 0; i < _currentAcqInstanceProtocolNode.children.length; i++) {
                 codes.push(_currentAcqInstanceProtocolNode.children[i].code);
             }
         }
         protocolList = codes.join(',');
     }
 }

 mini.open({
     title: _loginUserLanguageResource.addAcqInstance,
     url: context + '/miniui-app/modules/driverConfig/acqInstanceAddWindow.jsp',
     width: 520,
     height: 650,
     modal: true,
     allowResize: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;

         contentWindow.setData({
             deviceTypeIds: deviceTypeIds,
             protocolList: protocolList
         });

         // 刷新实例列表树（新增成功后回调）
         contentWindow._parentRefreshInstanceTree = function () {
             refreshAcqInstanceList();
         };

         // 新增成功后，将实例名称告知父页面，用于树加载后高亮
         contentWindow._parentSetNewInstanceName = function (name) {
             window._newAcqInstanceName = name;
         };
     },
     ondestroy: function (action) {
         // 新增成功时已在子窗口中回调刷新
     }
 });
}

//================================================================
//保存采控实例配置
// 对应原 ExtJS: SaveModbusProtocolInstanceConfigTreeData()
//================================================================
function saveAcqInstanceConfigData() {
 if (!_currentAcqInstanceNode) {
     mini.alert(_loginUserLanguageResource.checkOne);
     return;
 }

 // 只处理实例节点（classes === 1）
 if (_currentAcqInstanceNode.classes !== 1) {
     return;
 }

 var helper = protocolConfigInstancePropertiesHandsontableHelper;
 if (!helper || !helper.hot) {
     mini.alert(_loginUserLanguageResource.noDataToSave);
     return;
 }

 // ★ 关键：getData() 返回的始终是完整数据（包含被隐藏的行），
 //    因此索引与原 ExtJS 完全一致
 var propertiesData = helper.hot.getData();
 if (!propertiesData || propertiesData.length < 13) {
     mini.alert(_loginUserLanguageResource.noDataToSave);
     return;
 }

 var node = _currentAcqInstanceNode;
 var saveData = {};

 // ---- 节点基础信息 ----
 saveData.id = node.id;
 saveData.code = node.code;
 saveData.oldName = node.text;
 saveData.protocol = node.protocol;
 saveData.protocolDeviceTypeAllPath = node.protocolDeviceTypeAllPath;

 // ---- 属性表逐行读取（索引与原 ExtJS 完全一致）----
 saveData.name = propertiesData[0][2];               // 实例名称
 saveData.unitId = node.unitId;                      // 采集单元 ID（取自节点）
 saveData.unitName = propertiesData[1][2];           // 采集单元名称
 saveData.acqProtocolType = propertiesData[2][2];    // 采集协议类型
 saveData.ctrlProtocolType = propertiesData[3][2];   // 控制协议类型

 // 复选框：true → 1，其余 → 0
 saveData.signInPrefixSuffixHex = (propertiesData[4][2] === true) ? 1 : 0;
 saveData.signInPrefix = propertiesData[5][2];
 saveData.signInSuffix = propertiesData[6][2];
 saveData.signInIDHex = (propertiesData[7][2] === true) ? 1 : 0;
 saveData.heartbeatPrefixSuffixHex = (propertiesData[8][2] === true) ? 1 : 0;
 saveData.heartbeatPrefix = propertiesData[9][2];
 saveData.heartbeatSuffix = propertiesData[10][2];
 saveData.packetSendInterval = propertiesData[11][2];
 saveData.sort = propertiesData[12][2];

 // 提交
 saveModbusProtocolAcqInstanceData(saveData);
}

//================================================================
//统一提交函数
// 对应原 ExtJS: SaveModbusProtocolAcqInstanceData(saveData)
//================================================================
function saveModbusProtocolAcqInstanceData(saveData) {
 // 收集当前协议列表（用于后端定位数据）
 var protocolList = [];
 if (_currentAcqInstanceProtocolNode) {
     if (_currentAcqInstanceProtocolNode.classes === 1) {
         protocolList.push(_currentAcqInstanceProtocolNode.code);
     } else {
         if (isNotVal(_currentAcqInstanceProtocolNode.children)) {
             for (var i = 0; i < _currentAcqInstanceProtocolNode.children.length; i++) {
                 protocolList.push(_currentAcqInstanceProtocolNode.children[i].code);
             }
         }
     }
 }
 var protocols = protocolList.join(',');

 var mask = mini.mask({
     el: document.body,
     html: _loginUserLanguageResource.updateWait
 });

 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/saveProtocolInstanceData',
     data: {
         data: JSON.stringify(saveData),
         protocols: protocols
     },
     dataType: 'json',
     success: function (response) {
         mini.unmask(document.body);

         if (response && response.success) {
             // 删除 vs 保存：区分提示
             if (saveData.delidslist && saveData.delidslist.length > 0) {
                 _selectedAcqInstanceId = null;
                 _currentAcqInstanceNode = null;
                 mini.alert(_loginUserLanguageResource.deleteSuccessfully);
             } else {
                 mini.alert(_loginUserLanguageResource.savedSuccessfully);
             }
             // 刷新实例列表
             refreshAcqInstanceList();
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

//================================================================
//右键菜单：打开前事件
//================================================================
function onAcqInstanceTreeBeforeMenu(e) {
 var tree = mini.get('acqInstanceList');
 var menu = e.sender;
 var node = tree ? tree.getSelectedNode() : null;

 // 只对实例节点（classes === 1）显示菜单
 if (!node || node.classes !== 1) {
     e.cancel = true;
     if (e.htmlEvent) e.htmlEvent.preventDefault();
     return;
 }

 // 菜单文字国际化
 var deleteText = _loginUserLanguageResource.deleteData;
 var el = document.getElementById('acqInstanceTreeMenuDeleteText');
 if (el) el.textContent = deleteText;

 // 权限控制
 var deleteItem = mini.getbyName('delete', menu);
 if (deleteItem) {
     if (typeof editFlag === 'undefined' || !editFlag) {
         deleteItem.disable();
     } else {
         deleteItem.enable();
     }
 }
}

//================================================================
//删除采控实例节点
//  对应原 ExtJS beforecellcontextmenu → deleteData handler
//================================================================
function deleteAcqInstanceNode(e) {
 var tree = mini.get('acqInstanceList');
 var node = tree ? tree.getSelectedNode() : null;
 if (!node) return;
 if (node.classes !== 1) return;

 mini.confirm(
     _loginUserLanguageResource.confirmDelete,
     _loginUserLanguageResource.confirm,
     function (action) {
         if (action === 'ok') {
             var configInfo = {
                 name: node.text,
                 deviceType: node.deviceType,
                 protocol: node.protocol,
                 protocolDeviceTypeAllPath: node.protocolDeviceTypeAllPath,
                 delidslist: [node.id]
             };
             saveModbusProtocolAcqInstanceData(configInfo);
         }
     }
 );
}

//================================================================
//打开"导出采控实例"窗口
//================================================================
function openExportAcqInstanceWindow() {
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

 // 与 ExtJS 一致：遍历当前选中设备类型节点的所有子节点 id
 var deviceTypeIds = selectedDeviceTypeId || '';

 mini.open({
     title: _loginUserLanguageResource.exportAcqInstance,
     url: context + '/miniui-app/modules/driverConfig/exportAcqInstanceWindow.jsp',
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

//================================================================
//打开"导入采控实例"窗口
//================================================================
function openImportAcqInstanceWindow() {
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
     title: _loginUserLanguageResource.importAcqInstance,
     url: context + '/miniui-app/modules/driverConfig/importAcqInstanceWindow.jsp',
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

         // 暴露刷新父页面实例列表树的函数
         contentWindow.parent.refreshAcqInstanceList = function () {
        	 refreshAcqInstanceList();
         };
     }
 });
}