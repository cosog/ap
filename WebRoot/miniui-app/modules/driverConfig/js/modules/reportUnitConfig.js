// ================================================================
// 报表单元配置模块（MiniUI）
// ================================================================

// ---------- 状态变量 ----------
var _currentReportUnitNode          = null;
var _currentReportStandardSubTab    = 'singleWellReport';   // singleWellReport / areaReport
var _currentSingleWellReportTab     = 'hourlyReport';        // hourlyReport / dailyReport
var _currentAreaReportTab           = 'dailyReport';         // 区域报表内部子 Tab

// 新增报表单元后高亮标记（由添加窗口设置）
var _newReportUnitObjectName        = null;

// ---------- Handsontable Helper 变量 ----------
var reportUnitPropertiesHandsontableHelper                 = null;
var singleWellRangeReportTemplateHandsontableHelper        = null;
var singleWellRangeReportTemplateContentHandsontableHelper = null;
var productionReportTemplateHandsontableHelper             = null;
var productionReportTemplateContentHandsontableHelper      = null;
var singleWellDailyReportTemplateHandsontableHelper        = null;
var singleWellDailyReportTemplateContentHandsontableHelper = null;
var hydrologicalWellDailyReportTemplateHandsontableHelper  = null;
var hydrologicalWellDailyReportContentHandsontableHelper   = null;

// ================================================================
// 1. 报表单元列表树 - 加载前事件
// ================================================================
function onReportUnitListBeforeLoad(e) {
    var params = e.params || {};
    if (typeof selectedDeviceTypeId !== 'undefined' && selectedDeviceTypeId) {
        params.deviceTypeIds = selectedDeviceTypeId;
    }
    e.params = params;
}

// ================================================================
// 2. 报表单元列表树 - 加载完成事件
// ================================================================
function onReportUnitListLoad(e) {
    var tree = e.sender;
    var root = tree.getRootNode();
    if (!root) return;

    var targetNode = null;

    // ① 新增对象高亮
    if (_newReportUnitObjectName) {
        function findNewNode(node) {
            if (node.text === _newReportUnitObjectName && node.classes === 1) {
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
        if (targetNode) _newReportUnitObjectName = null;
    }

    // ② 按上次选中的 id 恢复
    if (!targetNode && _currentReportUnitNode && _currentReportUnitNode.id) {
        var lastId = _currentReportUnitNode.id;
        function findById(node) {
            if (node.id === lastId && node.classes === 1) {
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

    // ③ 默认选第一个 classes === 1 的节点
    if (!targetNode) {
        function collect(node) {
            if (targetNode) return;
            if (node.children && node.children.length > 0) {
                for (var i = 0; i < node.children.length; i++) {
                    collect(node.children[i]);
                    if (targetNode) return;
                }
            } else {
                if (node.classes === 1) targetNode = node;
            }
        }
        collect(root);
    }

    setTimeout(function () {
        if (targetNode) {
            tree.selectNode(targetNode);
        } else if (root.children && root.children.length > 0) {
            tree.selectNode(root.children[0]);
        }
    }, 50);
}

// ================================================================
// 3. 报表单元列表树 - 刷新
// ================================================================
function refreshReportUnitList() {
    var tree = mini.get('reportUnitList');
    if (!tree) return;
    if (!tree.getUrl()) {
        tree.setUrl(context + '/acquisitionUnitManagerController/repoerUnitTreeData');
    }
    tree.load();
}

// ================================================================
// 4. 报表单元列表树 - 节点选中事件
//
// 策略：
//   - 记录节点
//   - 调用 updateReportUnitConfigTabs 更新可见性（可能改变激活）
//   - 若激活 Tab 被改变 → onReportUnitDetailTabChanged 触发加载
//   - 若激活 Tab 未变 → 手动调用 loadReportUnitDataByCurrentTab 触发加载
// ================================================================
function onReportUnitListSelect(e) {
    var node = e.node;
    if (!node) return;

    if (node.classes === 1) {
        _currentReportUnitNode = node;

        var tabs = mini.get('reportUnitRightTabs');
        var oldActiveName = '';
        if (tabs) {
            var oldActive = tabs.getActiveTab();
            oldActiveName = oldActive ? oldActive.name : '';
        }

        updateReportUnitConfigTabs(node.unitClasses);

        var newActiveName = '';
        if (tabs) {
            var newActive = tabs.getActiveTab();
            newActiveName = newActive ? newActive.name : '';
        }

        // 激活 Tab 未变 → 手动触发加载
        if (oldActiveName === newActiveName) {
            setTimeout(function () {
                loadReportUnitDataByCurrentTab();
            }, 10);
        }
        // 否则由 onReportUnitDetailTabChanged 处理
    } else {
        _currentReportUnitNode = null;
        updateReportUnitConfigTabs(-1);
        clearAllReportUnitData();
    }
}

// ================================================================
// 5. 更新 Tab 可见性（保留当前激活，若不可保留则按类别重新激活）
// ================================================================
function updateReportUnitConfigTabs(unitClasses) {
    var tabs = mini.get('reportUnitRightTabs');
    if (!tabs) return;

    var standardTab = tabs.getTab('configStandard');
    var hydroTab    = tabs.getTab('configHydrological');
    var propsTab    = tabs.getTab('props');
    if (!standardTab || !hydroTab || !propsTab) return;

    var uc = parseInt(unitClasses, 10);
    var showStandard = (uc === 0);
    var showHydro    = (uc === 1);

    tabs.updateTab(standardTab, { visible: showStandard });
    tabs.updateTab(hydroTab,    { visible: showHydro });

    var activeTab = tabs.getActiveTab();
    var activeName = activeTab ? activeTab.name : '';

    var keepActive = false;
    if (activeName === 'props') {
        keepActive = true;
    } else if (activeName === 'configStandard' && showStandard) {
        keepActive = true;
    } else if (activeName === 'configHydrological' && showHydro) {
        keepActive = true;
    }

    if (!keepActive) {
        if (showStandard) {
            tabs.activeTab(standardTab);
        } else if (showHydro) {
            tabs.activeTab(hydroTab);
        } else {
            tabs.activeTab(propsTab);
        }
    }
}

// ================================================================
// 6. 主 Tab 切换 → 触发加载
// ================================================================
function onReportUnitDetailTabChanged(e) {
    if (isInitializing) return;
    if (!_currentReportUnitNode) return;
    var tab = e.tab;
    if (!tab) return;

    if (tab.name === 'props') {
        loadReportUnitProperties(_currentReportUnitNode);
    } else if (tab.name === 'configStandard') {
        loadStandardConfigByCurrentSubTab(_currentReportUnitNode);
    } else if (tab.name === 'configHydrological') {
        loadHydrologicalConfig(_currentReportUnitNode);
    }
}

// ================================================================
// 7. 标准报表子 Tab 切换
// ================================================================
function onReportStandardConfigSubTabChanged(e) {
    if (isInitializing) return;
    if (!_currentReportUnitNode) return;
    var tab = e.tab;
    if (!tab) return;
    _currentReportStandardSubTab = tab.name;
    loadStandardConfigByCurrentSubTab(_currentReportUnitNode);
}

function onSingleWellReportSubTabChanged(e) {
    if (isInitializing) return;
    if (!_currentReportUnitNode) return;
    var tab = e.tab;
    if (!tab) return;
    _currentSingleWellReportTab = tab.name;
    loadStandardConfigByCurrentSubTab(_currentReportUnitNode);
}

function onAreaReportSubTabChanged(e) {
    if (isInitializing) return;
    if (!_currentReportUnitNode) return;
    var tab = e.tab;
    if (!tab) return;
    _currentAreaReportTab = tab.name;
    loadStandardConfigByCurrentSubTab(_currentReportUnitNode);
}

// ================================================================
// 8. 根据"当前激活的所有 Tab"决定加载对应数据
// ================================================================
function loadReportUnitDataByCurrentTab() {
    var node = _currentReportUnitNode;
    if (!node || node.classes !== 1) return;

    var mainTabs = mini.get('reportUnitRightTabs');
    if (!mainTabs) return;
    var activeMain = mainTabs.getActiveTab();
    if (!activeMain) return;

    var mainName = activeMain.name;
    if (mainName === 'props') {
        loadReportUnitProperties(node);
    } else if (mainName === 'configStandard') {
        loadStandardConfigByCurrentSubTab(node);
    } else if (mainName === 'configHydrological') {
        loadHydrologicalConfig(node);
    }
}

// ================================================================
// 9. 标准报表配置：根据内部子 Tab 决定加载
// ================================================================
function loadStandardConfigByCurrentSubTab(node) {
    var stdSub = mini.get('reportStandardConfigSubTabs');
    if (!stdSub) return;
    var activeStd = stdSub.getActiveTab();
    if (!activeStd) return;

    if (activeStd.name === 'singleWellReport') {
        var swSub = mini.get('singleWellReportSubTabs');
        if (!swSub) return;
        var activeSw = swSub.getActiveTab();
        if (!activeSw) return;
        if (activeSw.name === 'hourlyReport') {
            loadHourlyReportTemplates(node);
        } else if (activeSw.name === 'dailyReport') {
            loadDailyReportTemplates(node);
        }
    } else if (activeStd.name === 'areaReport') {
        var areaSub = mini.get('areaReportSubTabs');
        if (!areaSub) return;
        var activeArea = areaSub.getActiveTab();
        if (!activeArea) return;
        if (activeArea.name === 'dailyReport') {
            loadAreaReportTemplates(node);
        }
    }
}

// ================================================================
// 10. 加载各类模板列表（通用：mini-datagrid）
//
// @param {string} gridId        datagrid 的 id
// @param {number} reportType    0: 单井区间日报表, 1: 区域报表, 2: 单井时报表
// @param {string} unitFieldName 单元节点上保存的模板 code 字段
// @param {Object} node          当前报表单元节点
// ================================================================
function loadTemplateList(gridId, reportType, unitFieldName, node) {
    var grid = mini.get(gridId);
    if (!grid) return;

    // 缓存上下文，供 beforeload / onload / selectionchanged 使用
    grid._unitNode      = node;
    grid._unitFieldName = unitFieldName;
    grid._reportType    = reportType;

    // 首次调用时设置列
    if (!grid._columnsSet) {
        grid.setColumns([
            { type: 'checkcolumn', width: 40, headerAlign: 'center', align: 'center' },
            {
                field: 'templateName',
                header: getTemplateListTitleByReportType(reportType),
                width: 'auto',
                headerAlign: 'left',
                align: 'left'
            }
        ]);
        grid._columnsSet = true;
    }

    // 设置 URL（首次）
    if (!grid.getUrl()) {
        grid.setUrl(context + '/acquisitionUnitManagerController/getReportDataTemplateList');
    }

    // 触发加载
    grid.load();
}

/**
 * 按 reportType 返回模板列表标题
 */
function getTemplateListTitleByReportType(reportType) {
    switch (reportType) {
        case 2: return _loginUserLanguageResource.deviceHourlyReportTemplateList || 'Hourly Report Template';
        case 0: return _loginUserLanguageResource.deviceDailyReportTemplateList   || 'Daily Report Template';
        case 1: return _loginUserLanguageResource.areaDailyReportTemplateList     || 'Area Report Template';
        default: return _loginUserLanguageResource.reportTemplate || 'Report Template';
    }
}

// ================================================================
// 11. 模板列表的三个入口
// ================================================================
function loadHourlyReportTemplates(node) {
    loadTemplateList('hourlyTemplateListGrid', 2, 'singleWellDailyReportTemplate', node);
}

function loadDailyReportTemplates(node) {
    loadTemplateList('dailyTemplateListGrid', 0, 'singleWellRangeReportTemplate', node);
}

function loadAreaReportTemplates(node) {
    loadTemplateList('areaTemplateListGrid', 1, 'productionReportTemplate', node);
}

// ================================================================
// 12. 模板列表 - 请求前附加参数
// ================================================================
function onTemplateListBeforeLoad(e) {
    var grid = e.sender;
    var params = e.params || {};
    params.reportType = grid._reportType;
    params.calculateType = grid._unitNode ? (grid._unitNode.calculateType || 0) : 0;
    e.params = params;
    
    grid._dataReady = false;
}

function onHourlyTemplateListBeforeLoad(e) { onTemplateListBeforeLoad(e); }
function onDailyTemplateListBeforeLoad(e)  { onTemplateListBeforeLoad(e); }
function onAreaTemplateListBeforeLoad(e)   { onTemplateListBeforeLoad(e); }

// ================================================================
// 13. 模板列表 - 加载完成后自动选中
//     - 优先匹配单元节点上的模板 code
//     - 否则选第一行
//     - 通过 grid.select 触发 selectionchanged → 加载详情
// ================================================================
function onTemplateListLoad(e) {
    var grid = e.sender;
    var data = e.data || [];
    var reportType = grid._reportType;

    // 无数据 → 清空模板详情
    if (data.length === 0) {
        clearTemplateDetailByReportType(reportType);
        grid._dataReady = true;
        return;
    }

    // 找到要选中的行
    var targetCode = grid._unitNode ? (grid._unitNode[grid._unitFieldName] || '') : '';
    var targetIndex = 0;
    if (targetCode) {
        for (var i = 0; i < data.length; i++) {
            if (data[i].templateCode === targetCode) {
                targetIndex = i;
                break;
            }
        }
    }
    
    grid._dataReady = true;

    setTimeout(function () {
        grid.select(targetIndex);
    }, 30);
}

function onHourlyTemplateListLoad(e) { 
	onTemplateListLoad(e); 
}
function onDailyTemplateListLoad(e)  { onTemplateListLoad(e); }
function onAreaTemplateListLoad(e)   { onTemplateListLoad(e); }

// ================================================================
// 15. 模板列表 - 选中变化
//     1) 同步 checkbox：只有选中行 checked=true
//     2) 触发对应详情加载
// ================================================================
function onTemplateListSelectionChanged(e, reportType) {
    if (isInitializing) return;
    var grid = e.sender;
    
    if (!grid._dataReady) return;   // 数据未就绪 → 跳过第一次
    
    var selected = grid.getSelected();
    if (!selected) return;

    // 触发详情加载
    if (reportType === 2) {
        loadHourlyTemplateDetail(grid._unitNode, selected);
    } else if (reportType === 0) {
        loadDailyTemplateDetail(grid._unitNode, selected);
    } else if (reportType === 1) {
        loadAreaTemplateDetail(grid._unitNode, selected);
    }
}

function onHourlyTemplateSelectionChanged(e) {
	onTemplateListSelectionChanged(e, 2); 
}
function onDailyTemplateSelectionChanged(e)  { onTemplateListSelectionChanged(e, 0); }
function onAreaTemplateSelectionChanged(e)   { onTemplateListSelectionChanged(e, 1); }

// ================================================================
// 16. 模板详情加载（模板表 + 内容表）
//     目前给出骨架，具体 Handsontable 渲染留待下一步
// ================================================================
function loadHourlyTemplateDetail(unitNode, templateNode) {
    loadTemplateTable(unitNode, templateNode,
        'hourlyReportTemplateContainer', 'hourlyReportTemplateTitle',
        'deviceHourlyReportTemplate',2);
    loadTemplateContentTable(unitNode, templateNode, 2,
        'hourlyReportContentContainer', 'hourlyReportContentTitle',
        'deviceHourlyReportContentConfig');
}

function loadDailyTemplateDetail(unitNode, templateNode) {
    loadTemplateTable(unitNode, templateNode,
        'dailyReportTemplateContainer', 'dailyReportTemplateTitle',
        'deviceDailyReportTemplate',0);
    loadTemplateContentTable(unitNode, templateNode, 0,
        'dailyReportContentContainer', 'dailyReportContentTitle',
        'deviceDailyReportContentConfig');
}

function loadAreaTemplateDetail(unitNode, templateNode) {
    loadTemplateTable(unitNode, templateNode,
        'areaReportTemplateContainer', 'areaReportTemplateTitle',
        'areaDailyReportTemplate',1);
    loadTemplateContentTable(unitNode, templateNode, 1,
        'areaReportContentContainer', 'areaReportContentTitle',
        'areaDailyReportContentConfig');
}

//================================================================
//17. 通用：加载模板表（Handsontable）
//  reportType: 0=日报表(单井区间), 1=区域报表, 2=时报表
//================================================================
function loadTemplateTable(unitNode, templateNode, containerId, titleId, titleKey, reportType) {
 var container = document.getElementById(containerId);
 if (!container) return;
 container.innerHTML = '<div class="empty-msg">' + _loginUserLanguageResource.loadingData + '</div>';

 var titleEl = document.getElementById(titleId);
 if (titleEl) {
     var unitName = unitNode ? (unitNode.text || '') : '';
     var titleText = _loginUserLanguageResource[titleKey] || titleKey;
     titleEl.innerText = (unitName ? (unitName + '/') : '') + titleText;
 }

 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/getReportTemplateData',
     data: {
         reportType: reportType,
         code: templateNode.templateCode,
         calculateType: templateNode.calculateType || 0
     },
     dataType: 'json',
     success: function (result) {
         container.innerHTML = '';

         // 根据 reportType 分派到对应 helper
         if (reportType === 2) {
             if (singleWellDailyReportTemplateHandsontableHelper &&
                 singleWellDailyReportTemplateHandsontableHelper.hot) {
                 singleWellDailyReportTemplateHandsontableHelper.hot.destroy();
             }
             singleWellDailyReportTemplateHandsontableHelper =
                 SingleWellDailyReportTemplateHandsontableHelper.createNew(containerId, result);
             singleWellDailyReportTemplateHandsontableHelper.createTable();

         } else if (reportType === 0) {
             if (singleWellRangeReportTemplateHandsontableHelper &&
                 singleWellRangeReportTemplateHandsontableHelper.hot) {
                 singleWellRangeReportTemplateHandsontableHelper.hot.destroy();
             }
             singleWellRangeReportTemplateHandsontableHelper =
                 SingleWellRangeReportTemplateHandsontableHelper.createNew(containerId, result);
             singleWellRangeReportTemplateHandsontableHelper.createTable();

         } else if (reportType === 1) {
             if (productionReportTemplateHandsontableHelper &&
                 productionReportTemplateHandsontableHelper.hot) {
                 productionReportTemplateHandsontableHelper.hot.destroy();
             }
             productionReportTemplateHandsontableHelper =
                 ProductionReportTemplateHandsontableHelper.createNew(containerId, result);
             productionReportTemplateHandsontableHelper.createTable();
         }
     },
     error: function () {
         container.innerHTML = '<div class="empty-msg">' + _loginUserLanguageResource.requestFailed + '</div>';
     }
 });
}

// ================================================================
// 18. 通用：加载模板内容表（Handsontable）
// ================================================================
//================================================================
//18. 通用：加载模板内容表（Handsontable）
//================================================================
function loadTemplateContentTable(unitNode, templateNode, reportType, containerId, titleId, titleKey) {
 var container = document.getElementById(containerId);
 if (!container) return;
 container.innerHTML = '<div class="empty-msg">' + _loginUserLanguageResource.loadingData + '</div>';

 var titleEl = document.getElementById(titleId);
 if (titleEl) {
     var unitName = unitNode ? (unitNode.text || '') : '';
     var titleText = _loginUserLanguageResource[titleKey] || titleKey;
     titleEl.innerText = (unitName ? (unitName + '/') : '') + titleText;
 }

 $.ajax({
     type: 'POST',
     url: context + '/acquisitionUnitManagerController/getReportUnitTotalCalItemsConfigData',
     data: {
         calculateType: unitNode ? (unitNode.calculateType || 0) : 0,
         reportType: reportType,
         unitId: unitNode ? unitNode.id : 0,
         templateCode: templateNode.templateCode,
         classes: unitNode ? unitNode.classes : 1
     },
     dataType: 'json',
     success: function (result) {
         container.innerHTML = '';

         if (reportType === 2) {
             // 时报表内容配置
             if (singleWellDailyReportTemplateContentHandsontableHelper &&
                 singleWellDailyReportTemplateContentHandsontableHelper.hot) {
                 singleWellDailyReportTemplateContentHandsontableHelper.hot.destroy();
             }
             singleWellDailyReportTemplateContentHandsontableHelper =
                 SingleWellDailyReportTemplateContentHandsontableHelper.createNew(containerId);
             _initContentHelperColumns(singleWellDailyReportTemplateContentHandsontableHelper, result, 2);
             singleWellDailyReportTemplateContentHandsontableHelper.createTable(result.totalRoot);

         } else if (reportType === 0) {
             // 日报表内容配置
             if (singleWellRangeReportTemplateContentHandsontableHelper &&
                 singleWellRangeReportTemplateContentHandsontableHelper.hot) {
                 singleWellRangeReportTemplateContentHandsontableHelper.hot.destroy();
             }
             singleWellRangeReportTemplateContentHandsontableHelper =
                 SingleWellRangeReportTemplateContentHandsontableHelper.createNew(containerId);
             _initContentHelperColumns(singleWellRangeReportTemplateContentHandsontableHelper, result, 0);
             singleWellRangeReportTemplateContentHandsontableHelper.createTable(result.totalRoot);

         } else if (reportType === 1) {
             // 区域报表内容配置
             if (productionReportTemplateContentHandsontableHelper &&
                 productionReportTemplateContentHandsontableHelper.hot) {
                 productionReportTemplateContentHandsontableHelper.hot.destroy();
             }
             productionReportTemplateContentHandsontableHelper =
                 ProductionReportTemplateContentHandsontableHelper.createNew(containerId);
             _initContentHelperColumns(productionReportTemplateContentHandsontableHelper, result, 1);
             productionReportTemplateContentHandsontableHelper.createTable(result.totalRoot);
         }
     },
     error: function () {
         container.innerHTML = '<div class="empty-msg">' + _loginUserLanguageResource.requestFailed + '</div>';
     }
 });
}

//内容表通用列初始化（三个内容 Helper 共用）
function _initContentHelperColumns(helper, result, reportType) {
 helper.result = result;
 if (reportType === 1) {
     // 区域报表：多 sumSign、averageSign、curveStatType
     helper.colHeaders = [
         _loginUserLanguageResource.idx, _loginUserLanguageResource.fiedName,
         _loginUserLanguageResource.dataColumn, _loginUserLanguageResource.unit,
         _loginUserLanguageResource.dataSource, _loginUserLanguageResource.totalType,
         _loginUserLanguageResource.showLevel, _loginUserLanguageResource.prec,
         _loginUserLanguageResource.sumSign, _loginUserLanguageResource.averageSign,
         _loginUserLanguageResource.reportCurve, _loginUserLanguageResource.curveStatType,
         _loginUserLanguageResource.config
     ];
     helper.columns = [
         { data: 'id' },
         { data: 'headerName' },
         { data: 'itemName' },
         { data: 'unit' },
         { data: 'dataSource' },
         { data: 'totalType' },
         { data: 'showLevel' },
         { data: 'prec' },
         { data: 'sumSign', type: 'checkbox' },
         { data: 'averageSign', type: 'checkbox' },
         { data: 'reportCurveConfShowValue' },
         { data: 'curveStatType' },
         { data: 'config', renderer: renderReportUnitContentConfig }
     ];
 } else {
     // 单井时报表 / 日报表
     helper.colHeaders = [
         _loginUserLanguageResource.idx, _loginUserLanguageResource.fiedName,
         _loginUserLanguageResource.dataColumn, _loginUserLanguageResource.unit,
         _loginUserLanguageResource.dataSource, _loginUserLanguageResource.totalType,
         _loginUserLanguageResource.showLevel, _loginUserLanguageResource.prec,
         _loginUserLanguageResource.reportCurve, _loginUserLanguageResource.config
     ];
     helper.columns = [
         { data: 'id' },
         { data: 'headerName' },
         { data: 'itemName' },
         { data: 'unit' },
         { data: 'dataSource' },
         { data: 'totalType' },
         { data: 'showLevel' },
         { data: 'prec' },
         { data: 'reportCurveConfShowValue' },
         { data: 'config', renderer: renderReportUnitContentConfig }
     ];
 }
}

// ================================================================
// 19. 清空模板详情（无数据时调用）
// ================================================================
function clearTemplateDetailByReportType(reportType) {
    if (reportType === 2) {
        setInnerHTML('hourlyReportTemplateContainer', '');
        setInnerHTML('hourlyReportContentContainer', '');
    } else if (reportType === 0) {
        setInnerHTML('dailyReportTemplateContainer', '');
        setInnerHTML('dailyReportContentContainer', '');
    } else if (reportType === 1) {
        setInnerHTML('areaReportTemplateContainer', '');
        setInnerHTML('areaReportContentContainer', '');
    }
}

function setInnerHTML(id, html) {
    var el = document.getElementById(id);
    if (el) el.innerHTML = html;
}

//================================================================
//加载报表单元属性（属性 Tab）
//对应 ExtJS 的 CreateProtocolReportUnitPropertiesInfoTable
//================================================================
function loadReportUnitProperties(node) {
 if (!node) return;

 var container = document.getElementById('reportUnitPropertiesContainer');
 if (!container) return;
 var root = [];
 if (node.classes === 0) {
     // 根节点
     root.push({
         id: 1,
         title: _loginUserLanguageResource.rootNode,
         value: _loginUserLanguageResource.unitList
     });
 } else if (node.classes === 1) {
     // 报表单元节点
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
 }

 if (reportUnitPropertiesHandsontableHelper == null ||
     reportUnitPropertiesHandsontableHelper.hot == undefined) {
     reportUnitPropertiesHandsontableHelper = ReportUnitPropertiesHandsontableHelper.createNew('reportUnitPropertiesContainer');
     reportUnitPropertiesHandsontableHelper.colHeaders = [
         _loginUserLanguageResource.idx,
         _loginUserLanguageResource.variable,
         _loginUserLanguageResource.value
     ];
     reportUnitPropertiesHandsontableHelper.columns = [
         { data: 'id' },
         { data: 'title' },
         { data: 'value' }
     ];
     reportUnitPropertiesHandsontableHelper.classes = node.classes;
     reportUnitPropertiesHandsontableHelper.createTable(root);
 } else {
     // 复用已有实例：更新 classes 后重新加载数据
     reportUnitPropertiesHandsontableHelper.classes = node.classes;
     reportUnitPropertiesHandsontableHelper.hot.loadData(root);
     reportUnitPropertiesHandsontableHelper.hot.render();
 }
}

// ================================================================
// 21. 加载水文井报表配置
// ================================================================
function loadHydrologicalConfig(node) {
    loadHydrologicalReportTemplate(node);
    loadHydrologicalReportContent(node);
}

function loadHydrologicalReportTemplate(node) {
    var container = document.getElementById('hydroReportTemplateContainer');
    if (!container) return;
    container.innerHTML = '<div class="empty-msg">' + _loginUserLanguageResource.loadingData + '</div>';

    var titleEl = document.getElementById('hydroReportTemplateTitle');
    if (titleEl) {
        titleEl.innerText = (node.text ? (node.text + '/') : '') +
            (_loginUserLanguageResource.reportTemplate || 'Report Template');
    }

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getHydrologicalWellReportTemplateData',
        data: {},
        dataType: 'json',
        success: function (result) {
            container.innerHTML = '';
            if (hydrologicalWellDailyReportTemplateHandsontableHelper &&
                hydrologicalWellDailyReportTemplateHandsontableHelper.hot) {
                hydrologicalWellDailyReportTemplateHandsontableHelper.hot.destroy();
            }
            hydrologicalWellDailyReportTemplateHandsontableHelper =
                HydrologicalWellDailyReportTemplateHandsontableHelper.createNew(
                    'hydroReportTemplateContainer', result);
            hydrologicalWellDailyReportTemplateHandsontableHelper.createTable();
        },
        error: function () {
            container.innerHTML = '<div class="empty-msg">' + _loginUserLanguageResource.requestFailed + '</div>';
        }
    });
}

function loadHydrologicalReportContent(node) {
    var container = document.getElementById('hydroReportContentContainer');
    if (!container) return;
    container.innerHTML = '<div class="empty-msg">' + _loginUserLanguageResource.loadingData + '</div>';

    var titleEl = document.getElementById('hydroReportContentTitle');
    if (titleEl) {
        titleEl.innerText = (node.text ? (node.text + '/') : '') +
            (_loginUserLanguageResource.reportContentConfig || 'Content Config');
    }

    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/getHydrologicalWellReportUnitItemsConfigColInfoData',
        data: {
            calculateType: node.calculateType || 0,
            reportType: 2,
            unitId: node.id,
            classes: node.classes
        },
        dataType: 'json',
        success: function (result) {
            container.innerHTML = '';
            if (hydrologicalWellDailyReportContentHandsontableHelper &&
                hydrologicalWellDailyReportContentHandsontableHelper.hot) {
                hydrologicalWellDailyReportContentHandsontableHelper.hot.destroy();
            }
            hydrologicalWellDailyReportContentHandsontableHelper =
                HydrologicalWellDailyReportContentHandsontableHelper.createNew(
                    'hydroReportContentContainer');
            _initContentHelperColumns(hydrologicalWellDailyReportContentHandsontableHelper, result, 2);
            hydrologicalWellDailyReportContentHandsontableHelper.createTable(result.totalRoot);
        },
        error: function () {
            container.innerHTML = '<div class="empty-msg">' + _loginUserLanguageResource.requestFailed + '</div>';
        }
    });
}

//================================================================
//报表模板表格 - 通用工厂
//模板表格结构：由后端返回的 templateData 决定
//- header: 每一行的单元格标题 + 样式
//- rowHeights / colWidths / mergeCells / fixedRowsTop / fixedRowsBottom
//整个表格只读（editor=false）
//================================================================
function _createReportTemplateHandsontableHelper(divid, templateData) {
 var helper = {};
 helper.templateData = templateData;
 helper.data = [];
 helper.hot = null;
 helper.container = document.getElementById(divid);

 // 列宽：按语言
 helper.colWidths = [];
 if (_loginUserLanguage === 'zh_CN') {
     helper.colWidths = templateData.columnWidths_zh_CN;
 } else if (_loginUserLanguage === 'en') {
     helper.colWidths = templateData.columnWidths_en;
 } else if (_loginUserLanguage === 'ru') {
     helper.colWidths = templateData.columnWidths_ru;
 }

 // 组装数据：每行一个单元格（多行表头）
 helper.initData = function () {
     helper.data = [];
     if (!templateData.header) return;
     for (var i = 0; i < templateData.header.length; i++) {
         var h = templateData.header[i];
         var title = '';
         if (_loginUserLanguage === 'zh_CN') title = h.title_zh_CN;
         else if (_loginUserLanguage === 'en') title = h.title_en;
         else if (_loginUserLanguage === 'ru') title = h.title_ru;
         helper.data.push(title);
     }
 };

 // 单元格样式渲染器
 helper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
     Handsontable.renderers.TextRenderer.apply(this, arguments);
     if (helper.hot && templateData.header) {
         for (var i = 0; i < templateData.header.length; i++) {
             if (row === i) {
                 var st = templateData.header[i].tdStyle;
                 if (st) {
                     if (st.fontWeight) td.style.fontWeight = st.fontWeight;
                     if (st.fontSize) td.style.fontSize = st.fontSize;
                     if (st.height) td.style.height = st.height;
                     if (st.color) td.style.color = st.color;
                     if (st.backgroundColor) td.style.backgroundColor = st.backgroundColor;
                     if (st.textAlign) td.style.textAlign = st.textAlign;
                 }
                 break;
             }
         }
     }
     td.style.whiteSpace = 'nowrap';
     td.style.overflow = 'hidden';
     td.style.textOverflow = 'ellipsis';
 };

 helper.createTable = function () {
     if (!helper.container) return;
     helper.container.innerHTML = '';
     helper.hot = new Handsontable(helper.container, {
         licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
         theme: 'ht-theme-classic',
         data: helper.data,
         fixedRowsTop: templateData.fixedRowsTop || 0,
         fixedRowsBottom: templateData.fixedRowsBottom || 0,
         rowHeaders: false,
         colHeaders: false,
         rowHeights: templateData.rowHeights || undefined,
         colWidths: helper.colWidths,
         stretchH: 'all',
         columnSorting: false,
         allowInsertRow: false,
         sortIndicator: false,
         manualColumnResize: true,
         manualRowResize: true,
         filters: false,
         renderAllRows: true,
         search: false,
         mergeCells: templateData.mergeCells || [],
         contextMenu: {
             items: {
                 "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                 "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
             }
         },
         cells: function (row, col, prop) {
             var cp = {};
             cp.editor = false;
             cp.renderer = helper.addStyle;
             return cp;
         },
         afterOnCellMouseOver: function (event, coords, TD) {
             if (coords.col >= 0 && coords.row >= 0 &&
                 helper.hot && helper.hot.getDataAtCell) {
                 var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                 if (rawValue && rawValue.length > 0) {
                     TD.title = rawValue;
                 }
             }
         }
     });
 };

 helper.getData = function () { return helper.data; };
 helper.clearContainer = function () { helper.data = []; };
 helper.saveData = function () {};

 helper.initData();
 return helper;
}

//4 个模板 Helper：内部都调用同一个工厂
var SingleWellRangeReportTemplateHandsontableHelper = {
 createNew: function (divid, templateData) {
     return _createReportTemplateHandsontableHelper(divid, templateData);
 }
};
var SingleWellDailyReportTemplateHandsontableHelper = {
 createNew: function (divid, templateData) {
     return _createReportTemplateHandsontableHelper(divid, templateData);
 }
};
var ProductionReportTemplateHandsontableHelper = {
 createNew: function (divid, templateData) {
     return _createReportTemplateHandsontableHelper(divid, templateData);
 }
};
var HydrologicalWellDailyReportTemplateHandsontableHelper = {
 createNew: function (divid, templateData) {
     return _createReportTemplateHandsontableHelper(divid, templateData);
 }
};

//================================================================
//报表模板内容表格 - 通用工厂
//列定义由 _initContentHelperColumns 提前设置
//所有列 editor=false，config 列渲染为链接
//================================================================
function _createReportTemplateContentHandsontableHelper(divid) {
 var helper = {};
 helper.divid = divid;
 helper.hot = null;
 helper.colHeaders = [];
 helper.columns = [];
 helper.hiddenColumns = [];

 helper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
     Handsontable.renderers.TextRenderer.apply(this, arguments);
     if (value != null) {
         var arr = String(value).split(';');
         if (arr.length === 3) td.style.backgroundColor = '#' + arr[2];
     }
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
     var container = document.getElementById(helper.divid);
     if (!container) return;
     container.innerHTML = '';
     helper.hot = new Handsontable(container, {
         licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
         theme: 'ht-theme-classic',
         data: data || [],
         hiddenColumns: {
             columns: helper.hiddenColumns || [],
             indicators: false,
             copyPasteEnabled: false
         },
         colWidths: (helper.colHeaders && helper.colHeaders.length === 13)
             ? [50, 130, 130, 60, 90, 100, 70, 50, 50, 50, 100, 100, 60]
             : [50, 130, 130, 70, 80, 100, 70, 50, 90, 60],
         columns: helper.columns,
         fixedColumnsStart: 2,
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
                 "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
             }
         },
         cells: function (row, col, prop) {
             var cp = {};
             var visualColIndex = this.instance.toVisualColumn(col);
             cp.editor = false;

             // config 列：由 columns 里的 renderer 处理
             if (helper.columns[visualColIndex] && helper.columns[visualColIndex].renderer) {
                 return cp;
             }

             // reportCurve 列（背景色）
             if (prop === 'reportCurveConfShowValue') {
                 cp.renderer = helper.addCurveBg;
             } else if (helper.columns[visualColIndex] &&
                        helper.columns[visualColIndex].type !== 'checkbox' &&
                        prop !== 'config') {
                 cp.renderer = helper.addCellStyle;
             }
             return cp;
         },
         afterOnCellMouseOver: function (event, coords, TD) {
             if (coords.col >= 0 && coords.row >= 0 &&
                 helper.columns[coords.col] &&
                 helper.columns[coords.col].type !== 'checkbox' &&
                 helper.hot && helper.hot.getDataAtCell) {
                 var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                 if (rawValue && rawValue.length > 0) {
                     TD.title = rawValue;
                 }
             }
         }
     });
 };

 helper.saveData = function () {};
 helper.clearContainer = function () {};
 return helper;
}

//内容表 config 列渲染器（链接，点击弹出配置窗口）
function renderReportUnitContentConfig(instance, td, row, col, prop, value, cellProperties) {
 Handsontable.renderers.TextRenderer.apply(this, arguments);
 td.innerHTML = "<a href='javascript:void(0)' " +
     "onclick='onReportUnitContentConfigClick(" + row + "," + col + ")' " +
     "style='text-decoration:none;color:#1890ff;'>" +
     (_loginUserLanguageResource.config || 'Config') + "...</a>";
}

//config 列点击：占位实现
function onReportUnitContentConfigClick(row, col) {
 // TODO: 打开内容配置窗口
 console.log('config click, row=' + row + ', col=' + col);
}

//3 个内容 Helper
var SingleWellRangeReportTemplateContentHandsontableHelper = {
 createNew: function (divid) {
     return _createReportTemplateContentHandsontableHelper(divid);
 }
};
var SingleWellDailyReportTemplateContentHandsontableHelper = {
 createNew: function (divid) {
     return _createReportTemplateContentHandsontableHelper(divid);
 }
};
var ProductionReportTemplateContentHandsontableHelper = {
 createNew: function (divid) {
     return _createReportTemplateContentHandsontableHelper(divid);
 }
};
var HydrologicalWellDailyReportContentHandsontableHelper = {
 createNew: function (divid) {
     return _createReportTemplateContentHandsontableHelper(divid);
 }
};

// ================================================================
// 22. 清空全部报表单元数据
// ================================================================
function clearAllReportUnitData() {
    // 销毁所有 helper
    var helperNames = [
        'reportUnitPropertiesHandsontableHelper',
        'singleWellRangeReportTemplateHandsontableHelper',
        'singleWellRangeReportTemplateContentHandsontableHelper',
        'productionReportTemplateHandsontableHelper',
        'productionReportTemplateContentHandsontableHelper',
        'singleWellDailyReportTemplateHandsontableHelper',
        'singleWellDailyReportTemplateContentHandsontableHelper',
        'hydrologicalWellDailyReportTemplateHandsontableHelper',
        'hydrologicalWellDailyReportContentHandsontableHelper'
    ];
    for (var i = 0; i < helperNames.length; i++) {
        var h = window[helperNames[i]];
        if (h && h.hot) {
            try { h.hot.destroy(); } catch (e) {}
        }
        window[helperNames[i]] = null;
    }

    // 清空各容器
    setInnerHTML('reportUnitPropertiesContainer', '');
    setInnerHTML('hourlyReportTemplateContainer', '');
    setInnerHTML('hourlyReportContentContainer', '');
    setInnerHTML('dailyReportTemplateContainer', '');
    setInnerHTML('dailyReportContentContainer', '');
    setInnerHTML('areaReportTemplateContainer', '');
    setInnerHTML('areaReportContentContainer', '');
    setInnerHTML('hydroReportTemplateContainer', '');
    setInnerHTML('hydroReportContentContainer', '');

    // 清空模板列表 datagrid
    var gridIds = ['hourlyTemplateListGrid', 'dailyTemplateListGrid', 'areaTemplateListGrid'];
    for (var j = 0; j < gridIds.length; j++) {
        var g = mini.get(gridIds[j]);
        if (g) {
            g.setData([]);
            g._columnsSet = false;
        }
    }
}


//================================================================
//报表单元属性 - Handsontable Helper
//对应 ExtJS 的 ReportUnitPropertiesHandsontableHelper
//列：[序号][变量][值]
//- 根节点(classes==0)：全部只读
//- 报表单元(classes==1)：
//   · 第 0/1 列只读
//   · 第 2 列（值）：
//       第 0 行 = 单元名称（必填）
//       第 1 行 = 计算类型（下拉：无/功图计算/转速计产）
//       第 2 行 = 排序（可空数字）
//       第 3 行 = 备注（普通文本）
//================================================================
var ReportUnitPropertiesHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot      = null;
     helper.classes  = null;
     helper.divid    = divid;
     helper.validresult = true;
     helper.colHeaders = [];
     helper.columns = [];
     helper.AllData = [];
     
     helper.addReadOnlyBg = _handsontableMakeReadOnlyBg(helper);
     helper.addCellStyle = _handsontableMakeCellStyle(helper);

     helper.createTable = function (data) {
         var container = document.getElementById(helper.divid);
         if (!container) return;
         container.innerHTML = '';

         helper.hot = new Handsontable(container, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             colWidths: [50, 180, 200],
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
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualRowIndex = this.instance.toVisualRow(row);
                 var visualColIndex = this.instance.toVisualColumn(col);

                 // ---------- 无编辑权限：全部只读 ----------
                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }

                 // ---------- 第 0/1 列始终只读 ----------
                 if (visualColIndex === 0 || visualColIndex === 1) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }

                 // ---------- classes === 0：根节点，全部只读 ----------
                 if (helper.classes === 0) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }

                 // ---------- classes === 1：报表单元节点 ----------
                 if (helper.classes === 1) {
                     if (visualColIndex === 2) {
                         if (visualRowIndex === 0) {
                             // 第 0 行：单元名称（必填）
                             this.validator = function (val, callback) {
                                 return handsontableDataCheck_NotNull(
                                     val, callback, row, col, helper
                                 );
                             };
                             cellProperties.renderer = helper.addCellStyle;
                         } else if (visualRowIndex === 1) {
                             // 第 1 行：计算类型（下拉）
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
                             // 第 2 行：排序（可空数字）
                             this.validator = function (val, callback) {
                                 return handsontableDataCheck_Num_Nullable(
                                     val, callback, row, col, helper
                                 );
                             };
                             cellProperties.renderer = helper.addCellStyle;
                         } else {
                             // 第 3 行：备注（普通文本）
                        	 cellProperties.renderer = helper.addCellStyle;
                         }
                     }
                 }
                 return cellProperties;
             },
             afterOnCellMouseOver: _handsontableMakeMouseOver(helper)
         });
     };

     helper.saveData = function () {};
     helper.clearContainer = function () {
         helper.AllData = [];
     };
     return helper;
 }
};
