// ================================================================
// 报表单元配置模块（MiniUI）
// ================================================================

// ---------- 状态变量 ----------
var _currentReportUnitNode = null;
var _currentReportStandardSubTab = 'singleWellReport'; // singleWellReport / areaReport
var _currentSingleWellReportTab = 'hourlyReport';      // hourlyReport / dailyReport
var _currentAreaReportTab = 'dailyReport';             // 区域报表内部子 Tab

// 新增报表单元后高亮标记（由添加窗口设置）
var _newReportUnitObjectName = null;

// ---------- Handsontable Helper 变量 ----------
var reportUnitPropertiesHandsontableHelper = null;
var singleWellRangeReportTemplateHandsontableHelper = null;
var singleWellRangeReportTemplateContentHandsontableHelper = null;
var productionReportTemplateHandsontableHelper = null;
var productionReportTemplateContentHandsontableHelper = null;
var singleWellDailyReportTemplateHandsontableHelper = null;
var singleWellDailyReportTemplateContentHandsontableHelper = null;
var hydrologicalWellDailyReportTemplateHandsontableHelper = null;
var hydrologicalWellDailyReportContentHandsontableHelper = null;

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
    } else {
        _currentReportUnitNode = null;
        updateReportUnitConfigTabs(-1);
        clearAllReportUnitData();
    }
}

// ================================================================
// 5. 更新 Tab 可见性
// ================================================================
function updateReportUnitConfigTabs(unitClasses) {
    var tabs = mini.get('reportUnitRightTabs');
    if (!tabs) return;

    var standardTab = tabs.getTab('configStandard');
    var hydroTab = tabs.getTab('configHydrological');
    var propsTab = tabs.getTab('props');
    if (!standardTab || !hydroTab || !propsTab) return;

    var uc = parseInt(unitClasses, 10);
    var showStandard = (uc === 0);
    var showHydro = (uc === 1);

    tabs.updateTab(standardTab, { visible: showStandard });
    tabs.updateTab(hydroTab, { visible: showHydro });

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
// ================================================================
function loadTemplateList(gridId, reportType, unitFieldName, node) {
    var grid = mini.get(gridId);
    if (!grid) return;

    // 缓存上下文，供 beforeload / onload / selectionchanged 使用
    grid._unitNode = node;
    grid._unitFieldName = unitFieldName;
    grid._reportType = reportType;

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
//     ★ 只加载模板列表；模板表 + 内容表由模板列表 select 触发
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
// ================================================================
function onTemplateListLoad(e) {
    var grid = e.sender;
    var data = e.data || [];
    var reportType = grid._reportType;

    // 无数据 → 清空模板表 + 内容表
    if (data.length === 0) {
        clearTemplateDetailByReportType(reportType);
        grid._dataReady = true;
        return;
    }

    // 找到要选中的行
    var targetCode = grid._unitNode ? (grid._unitNode[grid._unitFieldName] || '') : '';
    var targetIndex = -1;
    if (targetCode) {
        for (var i = 0; i < data.length; i++) {
            if (data[i].templateCode === targetCode) {
                targetIndex = i;
                break;
            }
        }
    }

    grid._dataReady = true;
    grid.deselectAll();

    if (targetIndex >= 0) {
        setTimeout(function () {
            grid.select(targetIndex);
        }, 30);
    } else {
        // ★ 无匹配模板 → 清空模板表 + 内容表
        clearTemplateDetailByReportType(reportType);
    }
}

function onHourlyTemplateListLoad(e) { onTemplateListLoad(e); }
function onDailyTemplateListLoad(e)  { onTemplateListLoad(e); }
function onAreaTemplateListLoad(e)   { onTemplateListLoad(e); }

// ================================================================
// 15. 模板列表 - 选中变化
//     - 有选中 → 加载模板表 + 内容表
//     - 无选中 → 清空模板表 + 内容表
// ================================================================
function onTemplateListSelectionChanged(e, reportType) {
    if (isInitializing) return;
    var grid = e.sender;
    if (!grid._dataReady) return;

    var selected = grid.getSelected();

    // ★ 无选中 → 清空两个表
    if (!selected) {
        clearTemplateDetailByReportType(reportType);
        return;
    }

    // ★ 有选中 → 加载模板表 + 内容表
    if (reportType === 2) {
        loadHourlyTemplateDetail(grid._unitNode, selected);
    } else if (reportType === 0) {
        loadDailyTemplateDetail(grid._unitNode, selected);
    } else if (reportType === 1) {
        loadAreaTemplateDetail(grid._unitNode, selected);
    }
}

function onHourlyTemplateSelectionChanged(e) { onTemplateListSelectionChanged(e, 2); }
function onDailyTemplateSelectionChanged(e)  { onTemplateListSelectionChanged(e, 0); }
function onAreaTemplateSelectionChanged(e)   { onTemplateListSelectionChanged(e, 1); }

// ================================================================
// 16. 模板详情加载（模板表 + 内容表）
// ================================================================
function loadHourlyTemplateDetail(unitNode, templateNode) {
    loadTemplateTable(unitNode, templateNode,
        'hourlyReportTemplateContainer', 'hourlyReportTemplateTitle',
        'deviceHourlyReportTemplate', 2);
    loadTemplateContentTable(unitNode, templateNode, 2,
        'hourlyReportContentContainer', 'hourlyReportContentTitle',
        'deviceHourlyReportContentConfig');
}

function loadDailyTemplateDetail(unitNode, templateNode) {
    loadTemplateTable(unitNode, templateNode,
        'dailyReportTemplateContainer', 'dailyReportTemplateTitle',
        'deviceDailyReportTemplate', 0);
    loadTemplateContentTable(unitNode, templateNode, 0,
        'dailyReportContentContainer', 'dailyReportContentTitle',
        'deviceDailyReportContentConfig');
}

function loadAreaTemplateDetail(unitNode, templateNode) {
    loadTemplateTable(unitNode, templateNode,
        'areaReportTemplateContainer', 'areaReportTemplateTitle',
        'areaDailyReportTemplate', 1);
    loadTemplateContentTable(unitNode, templateNode, 1,
        'areaReportContentContainer', 'areaReportContentTitle',
        'areaDailyReportContentConfig');
}

// ================================================================
// 17. 通用：加载模板表（Handsontable）
// ================================================================
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
                if (singleWellDailyReportTemplateContentHandsontableHelper &&
                    singleWellDailyReportTemplateContentHandsontableHelper.hot) {
                    singleWellDailyReportTemplateContentHandsontableHelper.hot.destroy();
                }
                singleWellDailyReportTemplateContentHandsontableHelper =
                    SingleWellDailyReportTemplateContentHandsontableHelper.createNew(containerId);
                _initContentHelperColumns(singleWellDailyReportTemplateContentHandsontableHelper, result, 2);
                singleWellDailyReportTemplateContentHandsontableHelper.createTable(result.totalRoot);

            } else if (reportType === 0) {
                if (singleWellRangeReportTemplateContentHandsontableHelper &&
                    singleWellRangeReportTemplateContentHandsontableHelper.hot) {
                    singleWellRangeReportTemplateContentHandsontableHelper.hot.destroy();
                }
                singleWellRangeReportTemplateContentHandsontableHelper =
                    SingleWellRangeReportTemplateContentHandsontableHelper.createNew(containerId);
                _initContentHelperColumns(singleWellRangeReportTemplateContentHandsontableHelper, result, 0);
                singleWellRangeReportTemplateContentHandsontableHelper.createTable(result.totalRoot);

            } else if (reportType === 1) {
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

// 内容表通用列初始化（三个内容 Helper 共用）
function _initContentHelperColumns(helper, result, reportType) {
    helper.result = result;
    if (reportType === 1) {
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
            { data: 'id' }, { data: 'headerName' }, { data: 'itemName' },
            { data: 'unit' }, { data: 'dataSource' }, { data: 'totalType' },
            { data: 'showLevel' }, { data: 'prec' },
            { data: 'sumSign', type: 'checkbox' },
            { data: 'averageSign', type: 'checkbox' },
            { data: 'reportCurveConfShowValue' }, { data: 'curveStatType' },
            { data: 'config', renderer: renderReportUnitContentConfig }
        ];
    } else {
        helper.colHeaders = [
            _loginUserLanguageResource.idx, _loginUserLanguageResource.fiedName,
            _loginUserLanguageResource.dataColumn, _loginUserLanguageResource.unit,
            _loginUserLanguageResource.dataSource, _loginUserLanguageResource.totalType,
            _loginUserLanguageResource.showLevel, _loginUserLanguageResource.prec,
            _loginUserLanguageResource.reportCurve, _loginUserLanguageResource.config
        ];
        helper.columns = [
            { data: 'id' }, { data: 'headerName' }, { data: 'itemName' },
            { data: 'unit' }, { data: 'dataSource' }, { data: 'totalType' },
            { data: 'showLevel' }, { data: 'prec' },
            { data: 'reportCurveConfShowValue' },
            { data: 'config', renderer: renderReportUnitContentConfig }
        ];
    }
}

// ================================================================
// 19. 清空模板表 + 内容表
// ================================================================
function clearTemplateDetailByReportType(reportType) {
    if (reportType === 2) {
        if (singleWellDailyReportTemplateHandsontableHelper &&
            singleWellDailyReportTemplateHandsontableHelper.hot) {
            try { singleWellDailyReportTemplateHandsontableHelper.hot.destroy(); } catch (e) {}
            singleWellDailyReportTemplateHandsontableHelper = null;
        }
        if (singleWellDailyReportTemplateContentHandsontableHelper &&
            singleWellDailyReportTemplateContentHandsontableHelper.hot) {
            try { singleWellDailyReportTemplateContentHandsontableHelper.hot.destroy(); } catch (e) {}
            singleWellDailyReportTemplateContentHandsontableHelper = null;
        }
        setInnerHTML('hourlyReportTemplateContainer', '');
        setInnerHTML('hourlyReportContentContainer', '');
        var t1 = document.getElementById('hourlyReportTemplateTitle');
        if (t1) t1.innerText = _loginUserLanguageResource.deviceHourlyReportTemplate || '';
        var c1 = document.getElementById('hourlyReportContentTitle');
        if (c1) c1.innerText = _loginUserLanguageResource.deviceHourlyReportContentConfig || '';

    } else if (reportType === 0) {
        if (singleWellRangeReportTemplateHandsontableHelper &&
            singleWellRangeReportTemplateHandsontableHelper.hot) {
            try { singleWellRangeReportTemplateHandsontableHelper.hot.destroy(); } catch (e) {}
            singleWellRangeReportTemplateHandsontableHelper = null;
        }
        if (singleWellRangeReportTemplateContentHandsontableHelper &&
            singleWellRangeReportTemplateContentHandsontableHelper.hot) {
            try { singleWellRangeReportTemplateContentHandsontableHelper.hot.destroy(); } catch (e) {}
            singleWellRangeReportTemplateContentHandsontableHelper = null;
        }
        setInnerHTML('dailyReportTemplateContainer', '');
        setInnerHTML('dailyReportContentContainer', '');
        var t2 = document.getElementById('dailyReportTemplateTitle');
        if (t2) t2.innerText = _loginUserLanguageResource.deviceDailyReportTemplate || '';
        var c2 = document.getElementById('dailyReportContentTitle');
        if (c2) c2.innerText = _loginUserLanguageResource.deviceDailyReportContentConfig || '';

    } else if (reportType === 1) {
        if (productionReportTemplateHandsontableHelper &&
            productionReportTemplateHandsontableHelper.hot) {
            try { productionReportTemplateHandsontableHelper.hot.destroy(); } catch (e) {}
            productionReportTemplateHandsontableHelper = null;
        }
        if (productionReportTemplateContentHandsontableHelper &&
            productionReportTemplateContentHandsontableHelper.hot) {
            try { productionReportTemplateContentHandsontableHelper.hot.destroy(); } catch (e) {}
            productionReportTemplateContentHandsontableHelper = null;
        }
        setInnerHTML('areaReportTemplateContainer', '');
        setInnerHTML('areaReportContentContainer', '');
        var t3 = document.getElementById('areaReportTemplateTitle');
        if (t3) t3.innerText = _loginUserLanguageResource.areaDailyReportTemplate || '';
        var c3 = document.getElementById('areaReportContentTitle');
        if (c3) c3.innerText = _loginUserLanguageResource.areaDailyReportContentConfig || '';
    }
}

function setInnerHTML(id, html) {
    var el = document.getElementById(id);
    if (el) el.innerHTML = html;
}

// ================================================================
// 加载报表单元属性（属性 Tab）
// ================================================================
function loadReportUnitProperties(node) {
    if (!node) return;

    var container = document.getElementById('reportUnitPropertiesContainer');
    if (!container) return;
    var root = [];
    if (node.classes === 0) {
        root.push({
            id: 1,
            title: _loginUserLanguageResource.rootNode,
            value: _loginUserLanguageResource.unitList
        });
    } else if (node.classes === 1) {
        root.push({ id: 1, title: _loginUserLanguageResource.unitName,        value: node.text });
        root.push({ id: 2, title: _loginUserLanguageResource.calculationType, value: node.calculateTypeName });
        root.push({ id: 3, title: _loginUserLanguageResource.sequenceNumber,  value: node.sort });
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
            { data: 'id' }, { data: 'title' }, { data: 'value' }
        ];
        reportUnitPropertiesHandsontableHelper.classes = node.classes;
        reportUnitPropertiesHandsontableHelper.createTable(root);
    } else {
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

// ================================================================
// 报表模板表格 - 通用工厂
// ================================================================
function _createReportTemplateHandsontableHelper(divid, templateData) {
    var helper = {};
    helper.templateData = templateData;
    helper.data = [];
    helper.hot = null;
    helper.container = document.getElementById(divid);

    helper.colWidths = [];
    if (_loginUserLanguage === 'zh_CN') {
        helper.colWidths = templateData.columnWidths_zh_CN;
    } else if (_loginUserLanguage === 'en') {
        helper.colWidths = templateData.columnWidths_en;
    } else if (_loginUserLanguage === 'ru') {
        helper.colWidths = templateData.columnWidths_ru;
    }

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

    helper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);

        var idx = instance.toVisualRow(row);
        if (templateData.header && templateData.header[idx]) {
            var st = templateData.header[idx].tdStyle;
            if (st) {
                if (st.fontWeight)      td.style.fontWeight = st.fontWeight;
                if (st.fontSize)        td.style.fontSize = st.fontSize;
                if (st.height)          td.style.height = st.height;
                if (st.color)           td.style.color = st.color;
                if (st.backgroundColor) td.style.backgroundColor = st.backgroundColor;
                if (st.textAlign)       td.style.textAlign = st.textAlign;
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
            afterOnCellMouseOver: _handsontableMakeMouseOver(helper)
        });
    };

    helper.getData = function () { return helper.data; };
    helper.clearContainer = function () { helper.data = []; };
    helper.saveData = function () {};

    helper.initData();
    return helper;
}

// 4 个模板 Helper
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

// ================================================================
// 报表模板内容表格 - 通用工厂
// ================================================================
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

    helper.addCellStyle = _handsontableMakeCellStyle(helper);

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
            colWidths: (helper.colHeaders && helper.colHeaders.length === 13) ?
                [50, 130, 130, 60, 90, 100, 70, 50, 50, 50, 100, 100, 60] :
                [50, 130, 130, 70, 80, 100, 70, 50, 90, 60],
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

                if (helper.columns[visualColIndex] && helper.columns[visualColIndex].renderer) {
                    return cp;
                }

                if (prop === 'reportCurveConfShowValue') {
                    cp.renderer = helper.addCurveBg;
                } else if (helper.columns[visualColIndex] &&
                    helper.columns[visualColIndex].type !== 'checkbox' &&
                    prop !== 'config') {
                    cp.renderer = helper.addCellStyle;
                }
                return cp;
            },
            afterOnCellMouseOver: _handsontableMakeMouseOver(helper)
        });
    };

    helper.saveData = function () {};
    helper.clearContainer = function () {};
    return helper;
}

// 内容表 config 列渲染器
function renderReportUnitContentConfig(instance, td, row, col, prop, value, cellProperties) {
    Handsontable.renderers.TextRenderer.apply(this, arguments);
    td.innerHTML = "<a href='javascript:void(0)' " +
        "onclick='onReportUnitContentConfigClick(" + row + "," + col + ")' " +
        "style='text-decoration:none;color:#1890ff;'>" +
        (_loginUserLanguageResource.config || 'Config') + "...</a>";
}

// ================================================================
// 点击内容表"配置"链接 —— 打开报表内容配置窗口
// ================================================================
function onReportUnitContentConfigClick(row, col) {
    var reportType = getActiveReportType();
    if (reportType === -1) return;

    var node = _currentReportUnitNode;
    if (!node) {
        mini.alert(_loginUserLanguageResource.checkOne);
        return;
    }

    var classes       = node.classes;
    var unitId        = node.id;
    var unitName      = node.text;
    var calculateType = node.calculateType || 0;
    var unitClasses   = node.unitClasses || 0;

    var templateCode = '';
    if (parseInt(unitClasses) === 0) {
        var grid = getActiveReportTemplateGrid();
        if (grid) {
            var sel = grid.getSelected();
            if (sel) templateCode = sel.templateCode;
        }
    }

    mini.open({
        title: _loginUserLanguageResource.reportContentConfig,
        url: context + '/miniui-app/modules/driverConfig/reportUnitContentConfigWindow.jsp',
        width: '80%',
        height: '90%',
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                unitId: unitId,
                unitName: unitName,
                calculateType: calculateType,
                classes: classes,
                unitClasses: unitClasses,
                reportType: reportType,
                selectedRow: row,
                selectedCol: col,
                templateCode: templateCode,
                editFlag: editFlag
            });

            contentWindow.parent.refreshReportUnitContentTable = function (reportType, unitClasses) {
                refreshReportUnitContentTable(reportType, unitClasses);
            };
        }
    });
}

// ================================================================
// 供 reportUnitContentConfigWindow.jsp 保存成功后回调
// ================================================================
window.refreshReportUnitContentTable = function (reportType, unitClasses) {
    var node = _currentReportUnitNode;
    if (!node) return;

    var rt = parseInt(reportType);
    var uc = parseInt(unitClasses);

    if (uc === 1) {
        loadHydrologicalReportContent(node);
        return;
    }

    var grid = null;
    if (rt === 0) grid = mini.get('dailyTemplateListGrid');
    else if (rt === 1) grid = mini.get('areaTemplateListGrid');
    else if (rt === 2) grid = mini.get('hourlyTemplateListGrid');

    if (!grid) return;
    var sel = grid.getSelected();
    if (!sel) return;

    if (rt === 0) {
        loadTemplateContentTable(node, sel, 0,
            'dailyReportContentContainer', 'dailyReportContentTitle',
            'deviceDailyReportContentConfig');
    } else if (rt === 1) {
        loadTemplateContentTable(node, sel, 1,
            'areaReportContentContainer', 'areaReportContentTitle',
            'areaDailyReportContentConfig');
    } else if (rt === 2) {
        loadTemplateContentTable(node, sel, 2,
            'hourlyReportContentContainer', 'hourlyReportContentTitle',
            'deviceHourlyReportContentConfig');
    }
};

// ================================================================
// 根据当前激活的三级 Tab 返回 reportType
// ================================================================
function getActiveReportType() {
    var stdSub = mini.get('reportStandardConfigSubTabs');
    if (!stdSub) return -1;
    var stdActive = stdSub.getActiveTab();
    if (!stdActive) return -1;

    if (stdActive.name === 'singleWellReport') {
        var swSub = mini.get('singleWellReportSubTabs');
        if (!swSub) return -1;
        var swActive = swSub.getActiveTab();
        if (!swActive) return -1;
        if (swActive.name === 'hourlyReport') return 2;
        if (swActive.name === 'dailyReport')  return 0;
    } else if (stdActive.name === 'areaReport') {
        var areaSub = mini.get('areaReportSubTabs');
        if (!areaSub) return -1;
        var areaActive = areaSub.getActiveTab();
        if (!areaActive) return -1;
        if (areaActive.name === 'dailyReport') return 1;
    }
    return -1;
}

// 4 个内容 Helper
var SingleWellRangeReportTemplateContentHandsontableHelper = {
    createNew: function (divid) { return _createReportTemplateContentHandsontableHelper(divid); }
};
var SingleWellDailyReportTemplateContentHandsontableHelper = {
    createNew: function (divid) { return _createReportTemplateContentHandsontableHelper(divid); }
};
var ProductionReportTemplateContentHandsontableHelper = {
    createNew: function (divid) { return _createReportTemplateContentHandsontableHelper(divid); }
};
var HydrologicalWellDailyReportContentHandsontableHelper = {
    createNew: function (divid) { return _createReportTemplateContentHandsontableHelper(divid); }
};

// ================================================================
// 22. 清空全部报表单元数据
// ================================================================
function clearAllReportUnitData() {
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

    setInnerHTML('reportUnitPropertiesContainer', '');
    setInnerHTML('hourlyReportTemplateContainer', '');
    setInnerHTML('hourlyReportContentContainer', '');
    setInnerHTML('dailyReportTemplateContainer', '');
    setInnerHTML('dailyReportContentContainer', '');
    setInnerHTML('areaReportTemplateContainer', '');
    setInnerHTML('areaReportContentContainer', '');
    setInnerHTML('hydroReportTemplateContainer', '');
    setInnerHTML('hydroReportContentContainer', '');

    var gridIds = ['hourlyTemplateListGrid', 'dailyTemplateListGrid', 'areaTemplateListGrid'];
    for (var j = 0; j < gridIds.length; j++) {
        var g = mini.get(gridIds[j]);
        if (g) {
            g.setData([]);
            g._columnsSet = false;
        }
    }
}

// ================================================================
// 报表单元属性 - Handsontable Helper
// ================================================================
var ReportUnitPropertiesHandsontableHelper = {
    createNew: function (divid) {
        var helper = {};
        helper.hot = null;
        helper.classes = null;
        helper.divid = divid;
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

                    if (!editFlag) {
                        cellProperties.editor = false;
                        cellProperties.renderer = helper.addReadOnlyBg;
                        return cellProperties;
                    }

                    if (visualColIndex === 0 || visualColIndex === 1) {
                        cellProperties.editor = false;
                        cellProperties.renderer = helper.addReadOnlyBg;
                        return cellProperties;
                    }

                    if (helper.classes === 0) {
                        cellProperties.editor = false;
                        cellProperties.renderer = helper.addReadOnlyBg;
                        return cellProperties;
                    }

                    if (helper.classes === 1) {
                        if (visualColIndex === 2) {
                            if (visualRowIndex === 0) {
                                this.validator = function (val, callback) {
                                    return handsontableDataCheck_NotNull(val, callback, row, col, helper);
                                };
                                cellProperties.renderer = helper.addCellStyle;
                            } else if (visualRowIndex === 1) {
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
                                this.validator = function (val, callback) {
                                    return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
                                };
                                cellProperties.renderer = helper.addCellStyle;
                            } else {
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

// ================================================================
// 打开添加报表单元窗口
// ================================================================
function addReportUnitInfo() {
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

    mini.open({
        title: _loginUserLanguageResource.addReportUnit,
        url: context + '/miniui-app/modules/driverConfig/reportUnitAddWindow.jsp',
        width: 420,
        height: 380,
        modal: true,
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: deviceTypeIds
            });

            contentWindow.parent._parentRefreshUnitTree = function () {
                refreshReportUnitList();
            };
            contentWindow.parent._parentSetNewObject = function (name, classes) {
                window._newReportUnitObjectName = name;
            };
        }
    });
}

// ================================================================
// 保存报表单元数据
// ================================================================
function SaveReportUnitData() {
    var tree = mini.get('reportUnitList');
    if (!tree) return;

    var selectedNode = tree.getSelectedNode();
    if (!selectedNode) return;
    if (selectedNode.classes !== 1) return;

    var tabs = mini.get('reportUnitRightTabs');
    if (!tabs) return;
    var activeTab = tabs.getActiveTab();
    if (!activeTab) return;

    var saveType = 0;
    if (activeTab.name === 'props') {
        saveType = 0;
    } else if (activeTab.name === 'configStandard' || activeTab.name === 'configHydrological') {
        saveType = 1;
    }

    var saveData = {
        classes: selectedNode.classes,
        id: selectedNode.id,
        unitCode: selectedNode.code || '',
        unitClasses: selectedNode.unitClasses || 0,
        singleWellRangeReportTemplate: selectedNode.singleWellRangeReportTemplate,
        singleWellDailyReportTemplate: selectedNode.singleWellDailyReportTemplate,
        productionReportTemplate: selectedNode.productionReportTemplate
    };

    if (saveType === 0) {
        var helper = reportUnitPropertiesHandsontableHelper;
        if (!helper || !helper.hot) {
            mini.alert(_loginUserLanguageResource.noDataToSave);
            return;
        }

        var propertiesData = helper.hot.getData();
        saveData.unitName = (propertiesData[0] && propertiesData[0][2]) ? propertiesData[0][2] : '';
        var calcTypeText = (propertiesData[1] && propertiesData[1][2]) ? propertiesData[1][2] : '';
        saveData.calculateType = 0;
        if (calcTypeText === _loginUserLanguageResource.SRPCalculate) {
            saveData.calculateType = 1;
        } else if (calcTypeText === _loginUserLanguageResource.PCPCalculate) {
            saveData.calculateType = 2;
        }
        saveData.sort = (propertiesData[2] && propertiesData[2][2]) ? propertiesData[2][2] : '';
    } else {
        saveData.unitName      = selectedNode.text;
        saveData.calculateType = selectedNode.calculateType;
        saveData.sort          = selectedNode.sort;

        var grid = getActiveReportTemplateGrid();
        if (grid && grid._unitFieldName) {
            var sel = grid.getSelected();
            saveData[grid._unitFieldName] = sel ? sel.templateCode : '';
        }
    }

    SaveModbusProtocolReportUnitData(saveData);
}

// ================================================================
// 根据当前激活的一级/二级/三级 Tab，返回对应的模板列表 grid
// ================================================================
function getActiveReportTemplateGrid() {
    var stdSub = mini.get('reportStandardConfigSubTabs');
    if (!stdSub) return null;
    var stdActive = stdSub.getActiveTab();
    if (!stdActive) return null;

    if (stdActive.name === 'singleWellReport') {
        var swSub = mini.get('singleWellReportSubTabs');
        if (!swSub) return null;
        var swActive = swSub.getActiveTab();
        if (!swActive) return null;
        if (swActive.name === 'hourlyReport') {
            return mini.get('hourlyTemplateListGrid');
        } else if (swActive.name === 'dailyReport') {
            return mini.get('dailyTemplateListGrid');
        }
    } else if (stdActive.name === 'areaReport') {
        var areaSub = mini.get('areaReportSubTabs');
        if (!areaSub) return null;
        var areaActive = areaSub.getActiveTab();
        if (!areaActive) return null;
        if (areaActive.name === 'dailyReport') {
            return mini.get('areaTemplateListGrid');
        }
    }
    return null;
}

// ================================================================
// 提交保存/删除到后端
// ================================================================
function SaveModbusProtocolReportUnitData(saveData) {
    var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.updateWait });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/saveProtocolReportUnitData',
        data: { data: JSON.stringify(saveData) },
        dataType: 'json',
        success: function (response) {
            mini.unmask(document.body);
            if (response && response.success) {
                if (saveData.delidslist && saveData.delidslist.length > 0) {
                    _currentReportUnitNode = null;
                    window._newReportUnitObjectName = null;
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                } else {
                    mini.alert(_loginUserLanguageResource.savedSuccessfully);
                }
                refreshReportUnitList();
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
// 报表单元树 - 右键菜单弹出前
// ================================================================
function onReportUnitTreeBeforeMenu(e) {
    var tree = mini.get('reportUnitList');
    var menu = e.sender;
    var node = tree ? tree.getSelectedNode() : null;

    if (!node || node.classes !== 1) {
        e.cancel = true;
        if (e.htmlEvent) e.htmlEvent.preventDefault();
        return;
    }

    var deleteText = _loginUserLanguageResource.deleteData;
    var el = document.getElementById('reportUnitTreeMenuDeleteText');
    if (el) el.textContent = deleteText;

    var deleteItem = mini.getbyName('delete', menu);
    if (deleteItem) {
        if (!editFlag) {
            deleteItem.disable();
        } else {
            deleteItem.enable();
        }
    }
}

// ================================================================
// 删除报表单元节点
// ================================================================
function deleteReportUnitNode(e) {
    var tree = mini.get('reportUnitList');
    var node = tree ? tree.getSelectedNode() : null;
    if (!node) return;

    var nodeId = node.id;

    mini.confirm(
        _loginUserLanguageResource.confirmDelete,
        _loginUserLanguageResource.confirm,
        function (action) {
            if (action === 'ok') {
                var deleteData = { delidslist: [nodeId] };
                SaveModbusProtocolReportUnitData(deleteData);
            }
        }
    );
}

// ================================================================
// 打开导出报表单元窗口
// ================================================================
function openExportReportUnitWindow() {
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
        title: _loginUserLanguageResource.exportReportUnit,
        url: context + '/miniui-app/modules/driverConfig/exportReportUnitWindow.jsp',
        width: 420,
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
// 打开导入报表单元窗口
// ================================================================
function openImportReportUnitWindow() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) return;
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) return;
    var deviceTypeId = selectedNode.deviceTypeId;
    var deviceTypeName = getNodePath(deviceTree, selectedNode);

    mini.open({
        title: _loginUserLanguageResource.importReportUnit,
        url: context + '/miniui-app/modules/driverConfig/importReportUnitWindow.jsp',
        width: '90%',
        height: '80%',
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
            contentWindow.parent.refreshReportUnitList = function () {
                refreshReportUnitList();
            };
        }
    });
}