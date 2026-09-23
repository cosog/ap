// ================================================================
// 数据字典配置模块 - dataDictionary.js
// 功能：数据字典主表 + 字典项表 + 动态列 + 一二级标签 + 国际化
// ================================================================

var _dataDictModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };
var isInitializing = true;

// 一二级标签数据源
var level1Data = [];
var level2Data = [];
var currentLevel1 = null;
var currentLevel2 = null;

// 当前选中的数据字典
var _selectedSysDataId = '';
var _selectedSysDataCode = '';

// ================================================================
// 页面初始化
// ================================================================
function initDataDictionaryPage() {
    // 1. 权限
    _dataDictModuleRight = getRoleModuleRight(
        context + '/roleManagerController/getRoleModuleRight',
        'DataDictionaryManagement'
    );
    if (!_dataDictModuleRight) { _dataDictModuleRight = {}; }
    _dataDictModuleRight.viewFlag    = parseInt(_dataDictModuleRight.viewFlag)    || 0;
    _dataDictModuleRight.editFlag    = parseInt(_dataDictModuleRight.editFlag)    || 0;
    _dataDictModuleRight.controlFlag = parseInt(_dataDictModuleRight.controlFlag) || 0;

    // 2. 动态构建一二级标签
    buildLevel1Tabs();

    // 3. 国际化
    initDictI18n();

    // 4. 权限按钮
    updateDictBtnStatus();

    setTimeout(function () {
        isInitializing = false;
        // 5. 加载数据字典主表
        loadSysDataGrid();
    }, 50);
}

// ================================================================
// 国际化处理
// ================================================================
function initDictI18n() {
    var R = _loginUserLanguageResource;

    // ---------- 按钮 ----------
    var btnMap = {
        'sysDataRefreshBtn': 'refresh',
        'sysDataSearchBtn': 'search',
        'sysDataAddBtn': 'add',
        'sysDataDelBtn': 'deleteData',
        'sysDataSaveBtn': 'save',
        'sysDataExportBtn': 'exportData',
        'sysDataImportBtn': 'importData',
        'dictItemSearchBtn': 'search',
        'dictItemAddBtn': 'add',
        'dictItemDelBtn': 'deleteData',
        'dictItemSaveBtn': 'save'
    };
    for (var id in btnMap) {
        var btn = mini.get(id);
        if (btn) btn.setText(R[btnMap[id]]);
    }

    // ---------- 标签前缀 ----------
    var labelMap = {
        'sysDataLblType': 'type',
        'sysDataLblName': 'name',
        'dictItemLblType': 'type',
        'dictItemLblName': 'name'
    };
    for (var id in labelMap) {
        var el = document.getElementById(id);
        if (el) el.textContent = R[labelMap[id]] + '：';
    }

    // ---------- 搜索下拉框 ----------
    var sysDataSearchType = mini.get('sysDataSearchType');
    if (sysDataSearchType) {
        sysDataSearchType.setData([
            { id: 0, text: R.dataModuleName },
            { id: 1, text: R.dataModuleCode }
        ]);
        sysDataSearchType.setValue(0);
    }
    var dictItemSearchType = mini.get('dictItemSearchType');
    if (dictItemSearchType) {
        dictItemSearchType.setData([
            { id: 0, text: R.fieldCode },
            { id: 1, text: R.fiedName }
        ]);
        dictItemSearchType.setValue(0);
    }

    // ---------- 二级标签占位提示 ----------
    var noChildTip = document.getElementById('noChildTip');
    if (noChildTip) noChildTip.textContent = R.selectLevel1;
    
    var dictItemGrid = mini.get('dictItemGrid');
    if (dictItemGrid) dictItemGrid.setEmptyText(R.emptyMsg);
}

// ================================================================
// 数据字典主表 - 加载
// ================================================================
function loadSysDataGrid() {
    var grid = mini.get('sysDataGrid');
    if (!grid) return;
    if (!grid.getUrl()) {
        grid.setUrl(context + '/systemdataInfoController/findSystemdataInfo');
    }
    grid.load();
}

function onSysDataGridBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize  || 100;
    params.start  = pageIndex * pageSize;
    params.limit  = pageSize;

    var typeCtrl = mini.get('sysDataSearchType');
    var nameCtrl = mini.get('sysDataSearchName');
    params.typeName = typeCtrl ? typeCtrl.getValue() : 0;
    params.sysName  = nameCtrl ? (nameCtrl.getValue() || '') : '';

    e.params = params;
}

function onSysDataGridLoad(e) {
    var grid   = e.sender;
    var result = e.result || {};

    grid._showChineseName = (result.showChineseName === undefined) ? true : !!result.showChineseName;
    grid._showEnglishName = (result.showEnglishName === undefined) ? true : !!result.showEnglishName;
    grid._showRussianName = (result.showRussianName === undefined) ? true : !!result.showRussianName;

    if (!grid._columnsCreated) {
        createSysDataGridColumns(grid);
        grid._columnsCreated = true;
    }

    // 默认选中第一行
    var data = grid.getData() || [];
    if (data.length > 0) {
        var selected = grid.getSelecteds() || [];
        if (selected.length === 0) {
            grid.select(0);
        } else {
            // 已有选中，手动触发一次字典项加载
            var firstRow = selected[0];
            _selectedSysDataId = firstRow.sysdataid || '';
            _selectedSysDataCode = firstRow.code || '';
            loadDictItemGrid();
        }
    } else {
        clearDictItemGrid();
    }
}

// ================================================================
// 数据字典主表 - 动态创建列
// ================================================================
function createSysDataGridColumns(grid) {
    var R = _loginUserLanguageResource;
    var editFlag = (_dataDictModuleRight.editFlag == 1);

    var showChineseName = (grid._showChineseName !== false);
    var showEnglishName = (grid._showEnglishName !== false);
    var showRussianName = (grid._showRussianName !== false);

    var currentLang = (loginUserLanguage || '').toUpperCase();

    function langEditor(lang) {
        if (!editFlag) return null;
        return {
            type: 'textbox',
            allowBlank: (currentLang === lang) ? false : true
        };
    }

    var sortEditor = editFlag
        ? { type: 'spinner', minValue: 1, maxValue: 9999999999, allowBlank: false }
        : null;

    var columns = [];

    if (editFlag) {
        columns.push({ type: 'checkcolumn', width: 40, header: '', headerAlign: 'center', align: 'center' });
    }

    columns.push({ type: 'indexcolumn', width: 50, header: R.idx, headerAlign: 'center', align: 'center' });

    columns.push({
        field: 'name_zh_CN', header: R.language_zh_CN,
        headerAlign: 'center', align: 'center', width: 160,
        visible: showChineseName, editor: langEditor('ZH_CN')
    });
    columns.push({
        field: 'name_en', header: R.language_en,
        headerAlign: 'center', align: 'center', width: 160,
        visible: showEnglishName, editor: langEditor('EN')
    });
    columns.push({
        field: 'name_ru', header: R.language_ru,
        headerAlign: 'center', align: 'center', width: 160,
        visible: showRussianName, editor: langEditor('RU')
    });
    columns.push({
        field: 'sorts', header: R.displayOrder,
        headerAlign: 'center', align: 'center', width: 80,
        editor: sortEditor
    });
    columns.push({
        field: 'moduleName', header: R.dictionaryBelongTo,
        headerAlign: 'center', align: 'center', width: 120
    });

    // 隐藏字段
    columns.push({ field: 'sysdataid', visible: false });
    columns.push({ field: 'code',      visible: false });
    columns.push({ field: 'moduleId',  visible: false });

    grid.setColumns(columns);
    grid.setAllowCellEdit(editFlag);
    grid.setAllowCellSelect(editFlag);
}

// ================================================================
// 数据字典主表 - 选中行 → 加载右侧字典项
// ================================================================
function onSysDataGridSelectionChanged(e) {
    var grid = e.sender;
    var rows = grid.getSelecteds() || [];
    if (rows.length === 0) {
        _selectedSysDataId = '';
        _selectedSysDataCode = '';
        clearDictItemGrid();
        return;
    }

    var row = rows[0];
    _selectedSysDataId = row.sysdataid || '';
    _selectedSysDataCode = row.code || '';

    // 与 ExtJS 一致：只有 realTimeMonitoring_Overview / historyQuery_Overview
    // 才显示"新增"按钮
    var addBtn = mini.get('dictItemAddBtn');
    if (addBtn) {
        if (_selectedSysDataCode === 'realTimeMonitoring_Overview'
            || _selectedSysDataCode === 'historyQuery_Overview') {
            addBtn.setVisible(true);
            addBtn.setEnabled(_dataDictModuleRight.editFlag == 1);
        } else {
            addBtn.setVisible(false);
        }
    }

    // 加载字典项
    loadDictItemGrid();
}

// ================================================================
// 字典项表 - 加载
// ================================================================
function loadDictItemGrid() {
    var grid = mini.get('dictItemGrid');
    if (!grid) return;
    if (!grid.getUrl()) {
        grid.setUrl(context + '/dataitemsInfoController/getDataDictionaryItemList');
    }
    grid.load();
}

function onDictItemGridBeforeLoad(e) {
    var params = e.params || {};

    var pageIndex = params.pageIndex || 0;
    var pageSize  = params.pageSize  || 100;
    params.start  = pageIndex * pageSize;
    params.limit  = pageSize;

    // 选中的数据字典 ID
    params.dictionaryId = _selectedSysDataId;

    // 搜索条件
    var typeCtrl = mini.get('dictItemSearchType');
    var nameCtrl = mini.get('dictItemSearchName');
    params.type  = typeCtrl ? typeCtrl.getValue() : 0;
    params.value = nameCtrl ? (nameCtrl.getValue() || '') : '';

    // 设备类型（来自当前二级标签）
    var deviceType = currentLevel2 ? (currentLevel2.deviceTypeId || '') : '';
    if (deviceType && deviceType.indexOf(',') > -1) {
    	deviceType = currentLevel1 ? (currentLevel1.deviceTypeId || '') : '';
    }
    params.deviceType = deviceType;

    e.params = params;
}

function onDictItemGridLoad(e) {
    var grid   = e.sender;
    var result = e.result || {};

    grid._showChineseName = (result.showChineseName === undefined) ? true : !!result.showChineseName;
    grid._showEnglishName = (result.showEnglishName === undefined) ? true : !!result.showEnglishName;
    grid._showRussianName = (result.showRussianName === undefined) ? true : !!result.showRussianName;

    if (!grid._columnsCreated) {
        createDictItemGridColumns(grid);
        grid._columnsCreated = true;
    }
}

// ================================================================
// 字典项表 - 动态创建列
// ================================================================
function createDictItemGridColumns(grid) {
    var R = _loginUserLanguageResource;
    var editFlag = (_dataDictModuleRight.editFlag == 1);

    var showChineseName = (grid._showChineseName !== false);
    var showEnglishName = (grid._showEnglishName !== false);
    var showRussianName = (grid._showRussianName !== false);

    var columns = [];

    // 复选框列
    if (editFlag) {
        columns.push({ type: 'checkcolumn', width: 40, header: '', headerAlign: 'center', align: 'center' });
    }

    // 序号
    columns.push({ type: 'indexcolumn', width: 50, header: R.idx, headerAlign: 'center', align: 'center' });

    // 中文名
    columns.push({
        field: 'name_zh_CN',
        header: R.language_zh_CN,
        headerAlign: 'center', align: 'center',
        width: 140,
        visible: showChineseName,
        editor: editFlag ? { type: 'textbox', allowBlank: false } : null,
        renderer: renderDictItemCell
    });

    // 英文名
    columns.push({
        field: 'name_en',
        header: R.language_en,
        headerAlign: 'center', align: 'center',
        width: 140,
        visible: showEnglishName,
        editor: editFlag ? { type: 'textbox', allowBlank: false } : null,
        renderer: renderDictItemCell
    });

    // 俄文名
    columns.push({
        field: 'name_ru',
        header: R.language_ru,
        headerAlign: 'center', align: 'center',
        width: 140,
        visible: showRussianName,
        editor: editFlag ? { type: 'textbox', allowBlank: false } : null,
        renderer: renderDictItemCell
    });

    // 列数据来源
    columns.push({
        field: 'columnDataSourceName',
        header: R.columnDataSource,
        headerAlign: 'center', align: 'center',
        width: 130,
        renderer: renderDictItemCell
    });

    // 字段编码（隐藏，与 ExtJS 一致）
    columns.push({
        field: 'code',
        header: R.fieldCode,
        headerAlign: 'center', align: 'center',
        width: 120,
        visible: false,
        renderer: renderDictItemCell
    });

    // 配置字段
    columns.push({
        field: 'configItemName',
        header: R.configureField,
        headerAlign: 'center', align: 'center',
        width: 120,
        renderer: function (e) {
            var val = e.value;
            if (val === undefined || val === null || val === '') return '';
            var s = String(val).replace(/"/g, '&quot;');
            var status = e.record ? e.record.status : false;
            var color = status ? '' : 'color:gray;';
            // 与 ExtJS 的 iconDictItemConfigureField 行为一致，用链接形式渲染
            return '<a href="javascript:void(0)" style="text-decoration:none;' + color
                 + '" onclick="onConfigFieldClick(\'' + (e.record ? e.record.dataitemid : '') + '\')">'
                 + s + '...</a>';
        }
    });

    // 字段参数
    columns.push({
        field: 'datavalue',
        header: R.fieldParameter,
        headerAlign: 'center', align: 'center',
        width: 120,
        editor: editFlag ? { type: 'textbox', allowBlank: false } : null,
        renderer: renderDictItemCell
    });

    // status_cn
    if (showChineseName) {
        columns.push({
            field: 'status_cn',
            header: R.language_zh_CN,
            type: 'checkboxcolumn',
            trueValue: true, falseValue: false,
            headerAlign: 'center', align: 'center',
            width: 70,
            editable: editFlag
        });
    }

    // status_en
    if (showEnglishName) {
        columns.push({
            field: 'status_en',
            header: R.language_en,
            type: 'checkboxcolumn',
            trueValue: true, falseValue: false,
            headerAlign: 'center', align: 'center',
            width: 70,
            editable: editFlag
        });
    }

    // status_ru
    if (showRussianName) {
        columns.push({
            field: 'status_ru',
            header: R.language_ru,
            type: 'checkboxcolumn',
            trueValue: true, falseValue: false,
            headerAlign: 'center', align: 'center',
            width: 70,
            editable: editFlag
        });
    }

    // 排序
    columns.push({
        field: 'sorts',
        header: R.sequenceNumber,
        headerAlign: 'center', align: 'center',
        width: 60,
        editor: editFlag ? { type: 'spinner', minValue: 1, maxValue: 999999 } : null,
        renderer: renderDictItemCell
    });

    // 启用
    columns.push({
        field: 'status',
        header: R.enable,
        type: 'checkboxcolumn',
        trueValue: true, falseValue: false,
        headerAlign: 'center', align: 'center',
        width: 60,
        editable: editFlag
    });

    // 隐藏字段
    columns.push({ field: 'dataitemid',       visible: false });
    columns.push({ field: 'columnDataSource', visible: false });
    columns.push({ field: 'deviceType',       visible: false });

    grid.setColumns(columns);
    grid.setAllowCellEdit(editFlag);
    grid.setAllowCellSelect(editFlag);
}

// ================================================================
// 通用单元格渲染：status 为 false 时显示灰色
// ================================================================
function renderDictItemCell(e) {
    var val = e.value;
    if (val === undefined || val === null || val === '') return '';
    var s = String(val).replace(/"/g, '&quot;');
    var status = e.record ? e.record.status : false;
    var color = status ? '' : 'color:gray;';
    return '<span style="' + color + '" title="' + s + '">' + s + '</span>';
}

// ================================================================
// 字典项表 - 单元格编辑前校验
// ================================================================
function onDictItemGridCellBeginEdit(e) {
    if (_dataDictModuleRight.editFlag != 1) {
        e.cancel = true;
        return;
    }
    var record = e.record;
    var field  = e.field;
    // 与 ExtJS 一致：columnDataSource != 0（非"固定字段"）的行不允许编辑 sorts
    if (field === 'sorts' && record && record.columnDataSource != 0) {
        e.cancel = true;
    }
}

// ================================================================
// 配置字段 - 点击弹窗（占位，后续实现弹窗）
// ================================================================
function onConfigFieldClick(dataitemid) {
    console.log('[数据字典] 点击配置字段, dataitemid=', dataitemid);
    // TODO：弹出配置窗口
}

// ================================================================
// 清空右侧字典项表格
// ================================================================
function clearDictItemGrid() {
    var grid = mini.get('dictItemGrid');
    if (grid) grid.setData([]);
}

// ================================================================
// 构建一级标签
// ================================================================
function buildLevel1Tabs() {
    var container = document.getElementById('level1Footer');
    if (!container) return;
    container.innerHTML = '';

    var tabInfo = null;
    try {
        if (window.parent && window.parent.tabInfo) tabInfo = window.parent.tabInfo;
    } catch(e) { console.warn('无法获取 tabInfo', e); }

    var children = (tabInfo && tabInfo.children) ? tabInfo.children : [];

    if (children.length === 0) {
        container.innerHTML = '<span style="padding:8px 16px;color:#999;font-size:13px;">'
            + _loginUserLanguageResource.emptyMsg + '</span>';
        var level2 = document.getElementById('level2Sidebar');
        if (level2) {
            level2.innerHTML = '<div class="no-child-tip">'
                + _loginUserLanguageResource.selectLevel1 + '</div>';
        }
        return;
    }

    level1Data = children;
    for (var i = 0; i < level1Data.length; i++) {
        var item = level1Data[i];
        var span = document.createElement('span');
        span.className = 'tab-item' + (i === 0 ? ' active' : '');
        span.dataset.index = i;
        span.dataset.deviceTypeId = item.deviceTypeId || '';
        span.textContent = item.text;
        span.title = item.text;
        span.onclick = function () { selectLevel1(parseInt(this.dataset.index)); };
        container.appendChild(span);
    }
    if (level1Data.length > 0) selectLevel1(0);
}

// ================================================================
// 一级标签切换
// ================================================================
function selectLevel1(index) {
    if (index < 0 || index >= level1Data.length) return;
    var item = level1Data[index];
    currentLevel1 = item;

    var container = document.getElementById('level1Footer');
    var tabs = container.querySelectorAll('.tab-item');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    buildLevel2Tabs(item);
}

// ================================================================
// 构建二级标签
// ================================================================
function buildLevel2Tabs(parentItem) {
    var container = document.getElementById('level2Sidebar');
    if (!container) return;
    container.innerHTML = '';

    var children = (parentItem && parentItem.children) ? parentItem.children : [];

    if (children.length === 0) {
        container.innerHTML = '<div class="no-child-tip">'
            + _loginUserLanguageResource.emptyMsg + '</div>';
        currentLevel2 = null;
        return;
    }

    level2Data = children;

    var allIds = [];
    for (var i = 0; i < children.length; i++) {
        if (children[i].deviceTypeId) allIds.push(children[i].deviceTypeId);
    }

    var allTabs = [];
    if (children.length > 1) {
        allTabs.push({
            text: _loginUserLanguageResource.all,
            deviceTypeId: allIds.join(','),
            isAll: true
        });
    }
    for (var i = 0; i < children.length; i++) {
        allTabs.push(children[i]);
    }

    for (var i = 0; i < allTabs.length; i++) {
        var item = allTabs[i];
        var div = document.createElement('div');
        div.className = 'tab-item' + (i === 0 ? ' active' : '');
        div.dataset.index = i;
        div.dataset.deviceTypeId = item.deviceTypeId || '';
        div.dataset.isAll = item.isAll || false;
        div.textContent = item.text;
        div.title = item.text;
        div.onclick = function () { selectLevel2(parseInt(this.dataset.index)); };
        container.appendChild(div);
    }

    if (allTabs.length > 0) {
        currentLevel2 = allTabs[0];
        // 二级标签首次激活后，重新加载字典项（如果已有选中的字典模块）
        if (_selectedSysDataId) {
            loadDictItemGrid();
        }
    }
}

// ================================================================
// 二级标签切换
// ================================================================
function selectLevel2(index) {
    var container = document.getElementById('level2Sidebar');
    if (!container) return;
    var tabs = container.querySelectorAll('.tab-item');

    var allTabs = [];
    var children = currentLevel1 ? (currentLevel1.children || []) : [];
    if (children.length > 1) {
        var allIds = [];
        for (var i = 0; i < children.length; i++) {
            if (children[i].deviceTypeId) allIds.push(children[i].deviceTypeId);
        }
        allTabs.push({
            text: _loginUserLanguageResource.all,
            deviceTypeId: allIds.join(','),
            isAll: true
        });
    }
    for (var i = 0; i < children.length; i++) allTabs.push(children[i]);

    if (index < 0 || index >= allTabs.length) return;

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    currentLevel2 = allTabs[index];

    // 二级标签切换后重新加载字典项
    if (_selectedSysDataId) {
        loadDictItemGrid();
    }
}

// ================================================================
// 权限控制
// ================================================================
function updateDictBtnStatus() {
    var editFlag = (_dataDictModuleRight.editFlag == 1);
    var btnIds = [
        'sysDataAddBtn', 'sysDataDelBtn', 'sysDataSaveBtn',
        'sysDataExportBtn', 'sysDataImportBtn',
        'dictItemAddBtn', 'dictItemDelBtn', 'dictItemSaveBtn'
    ];
    for (var i = 0; i < btnIds.length; i++) {
        var btn = mini.get(btnIds[i]);
        if (btn) btn.setEnabled(editFlag);
    }
}

// ================================================================
// 事件处理
// ================================================================
function onSysDataRefresh() {
    var typeCtrl = mini.get('sysDataSearchType');
    var nameCtrl = mini.get('sysDataSearchName');
    if (typeCtrl) typeCtrl.setValue(0);
    if (nameCtrl) nameCtrl.setValue('');
    loadSysDataGrid();
}

function onSysDataSearch() {
    loadSysDataGrid();
}

function onDictItemSearch() {
    if (!_selectedSysDataId) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }
    loadDictItemGrid();
}

function onSysDataAdd() {
    mini.open({
        title: _loginUserLanguageResource.addDictionary,
        url: context + '/miniui-app/modules/dataDictionary/dataDictionaryAddWindow.jsp',
        width: 1000,
        height: 700,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var cw = iframe.contentWindow;
            // ★ 子窗口保存成功后回调刷新左侧字典主表
            cw._parentRefreshDictList = function () {
                loadSysDataGrid();
            };
        }
    });
}
function onSysDataDel()     { console.log('[数据字典] 删除数据模块'); }
function onSysDataSave()    { console.log('[数据字典] 保存数据模块'); }
function onSysDataExport()  { console.log('[数据字典] 导出数据模块'); }
function onSysDataImport()  { console.log('[数据字典] 导入数据模块'); }

function onDictItemAdd()    { console.log('[数据字典] 新增字典项'); }
function onDictItemDel()    { console.log('[数据字典] 删除字典项'); }
function onDictItemSave()   { console.log('[数据字典] 保存字典项'); }