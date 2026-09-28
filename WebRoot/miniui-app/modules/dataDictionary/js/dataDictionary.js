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
            //var firstRow = selected[0];
            //_selectedSysDataId = firstRow.sysdataid || '';
            //_selectedSysDataCode = firstRow.code || '';
            //loadDictItemGrid();
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
            var record = e.record;
            if (!record || record.columnDataSource == 0) return '';
            var val = e.value;
            if (val === undefined || val === null || val === '') return '';
            var s = String(val).replace(/"/g, '&quot;');
            var color = record.status ? '' : 'color:gray;';
            return '<a href="javascript:void(0)" style="text-decoration:none;' + color
                 + '" onclick="onConfigFieldClick(\'' + (record.dataitemid || '') + '\')">'
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
//================================================================
//配置字段 - 点击超链接，打开编辑窗口（对照 ExtJS callBackDictItemConfig）
//================================================================
function onConfigFieldClick(dataitemid) {
 var R = _loginUserLanguageResource;

 if (_dataDictModuleRight.editFlag != 1) return;

 // 从当前表格拿整行数据
 var grid = mini.get('dictItemGrid');
 if (!grid) return;
 var data = grid.getData() || [];
 var record = null;
 for (var i = 0; i < data.length; i++) {
     if (String(data[i].dataitemid) === String(dataitemid)) {
         record = data[i];
         break;
     }
 }
 if (!record) return;

 mini.open({
     title: R.editDataItem,
     url: context + '/miniui-app/modules/dataDictionary/dataDictionaryItemAddWindow.jsp',
     width: '60%',
     height: '80%',
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var cw = iframe.contentWindow;
         // ★ 把整行 record 交给子窗口回填（对照 ExtJS callBackDictItemConfig 的一堆 setValue）
         cw.setData({
             mode: 'edit',
             lang: (loginUserLanguage || '').toUpperCase(),
             sysDataId: _selectedSysDataId,
             record: record
         });
         cw._parentRefreshDictItem = function () {
             loadDictItemGrid();
             loadSysDataGrid();
         };
     }
 });
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
//================================================================
//数据字典删除
//================================================================
function onSysDataDel() {
	 var R = _loginUserLanguageResource;
	 var grid = mini.get('sysDataGrid');
	 if (!grid) return;
	
	 var rows = grid.getSelecteds() || [];
	 if (rows.length === 0) {
	     mini.alert(R.checkOne, R.tip);
	     return;
	 }
	
	 // ★ 与 ExtJS 完全一致：按语言取名称，用 dataModuleName 前缀
	 var idList = [];
	 var nameList = [];
	 var lang = (loginUserLanguage || '').toUpperCase();
	 for (var i = 0; i < rows.length; i++) {
	     idList.push(rows[i].sysdataid);
	     var nm = '';
	     if (lang === 'ZH_CN')      nm = rows[i].name_zh_CN;
	     else if (lang === 'EN')    nm = rows[i].name_en;
	     else if (lang === 'RU')    nm = rows[i].name_ru;
	     nameList.push(nm);
	 }
	
	 var deleteInfo;
	 if (idList.length === 1) {
	     deleteInfo = R.dataModuleName
	         + ":<font color=red>" + nameList[0] + "</font>"
	         + "<br/>" + R.confirmDelete;
	 } else {
	     deleteInfo = R.sparseRecordCount
	         + ":<font color=red>" + idList.length + "</font>"
	         + "<br/>" + R.confirmDelete;
	 }
	
	 mini.confirm(deleteInfo, R.tip, function (action) {
	     if (action !== 'ok') return;
	     $.ajax({
	         url: context + '/systemdataInfoController/deleteSystemdataInfoById',
	         type: 'POST',
	         data: { paramsId: idList.join(',') },
	         dataType: 'json',
	         success: function (result) {
	             if (result.flag === true) {
	                 mini.alert(R.deleteSuccessfully, R.tip);
	             } else {
	                 mini.alert('<font color="red">' + R.deleteFailed + '</font>', R.tip);
	             }
	             _selectedSysDataId = '';
	             _selectedSysDataCode = '';
	             loadSysDataGrid();
	         },
	         error: function () {
	             mini.alert(R.requestFailed, R.tip);
	         }
	     });
	 });
}

//================================================================
//数据字典主表 - 批量保存（对照 ExtJS batchUpdateDataDictionaryInfo）
//================================================================
function onSysDataSave() {
	 var R = _loginUserLanguageResource;
	 var grid = mini.get('sysDataGrid');
	 if (!grid) return;
	
	 // ★ 提交当前单元格编辑，确保 getChanges 拿到最新值
	 grid.commitEdit();
	
	 // ★ 只取修改过的行（等价 ExtJS store.getModifiedRecords()）
	 var modified = grid.getChanges('modified', false) || [];
	 if (modified.length === 0) {
	     mini.alert(R.noDataChange, R.tip);
	     return;
	 }
	
	 // ★ 字段与 ExtJS 完全一致：sysdataid / name_zh_CN / name_en / name_ru / sorts
	 var arr = [];
	 for (var i = 0; i < modified.length; i++) {
	     var rec = modified[i];
	     arr.push({
	         sysdataid: rec.sysdataid,
	         name_zh_CN: rec.name_zh_CN,
	         name_en:    rec.name_en,
	         name_ru:    rec.name_ru,
	         sorts:      rec.sorts
	     });
	 }
	
	 $.ajax({
	     url: context + '/systemdataInfoController/batchUpdateDataDictionaryInfo',
	     type: 'POST',
	     data: { data: JSON.stringify(arr) },
	     dataType: 'json',
	     success: function (result) {
	         if (result.success === true && result.flag === true) {
	             mini.alert(R.savedSuccessfully, R.tip);
	         } else if (result.success === true && result.flag === false) {
	             mini.alert('<font color="red">' + R.saveFailed + '</font>', R.tip);
	         } else {
	             mini.alert('<font color="red">' + R.saveFailed + '</font>', R.tip);
	         }
	         grid.accept();
	         loadSysDataGrid();
	     },
	     error: function () {
	         mini.alert(R.requestFailed, R.tip);
	     }
	 });
}

//================================================================
//数据字典主表 - 导出完整数据（对照 ExtJS exportDataDictionaryCompleteData）
//================================================================
function onSysDataExport() {
	 var R = _loginUserLanguageResource;
	 var url = context + '/systemdataInfoController/exportDataDictionaryCompleteData';
	
	 var timestamp = new Date().getTime();
	 var key = 'exportDataDictionaryCompleteData' + '_' + timestamp;
	 var maskPanelId = 'sysDataModulePanel';
	
	 var param = "&recordCount=10000"
	           + "&fileName=" + URLencode(URLencode(R.dataDictionaryExportFileName))
	           + '&key=' + key;
	
	 exportDataMask(key, maskPanelId, R.loadingData);
	 downloadFile(url + '?flag=true' + param);
}
function onSysDataImport()  { console.log('[数据字典] 导入数据模块'); }

//================================================================
//右侧字典项 - 新增（对照 ExtJS addfindtattxtInfo + savetoSysDataItems）
//================================================================
function onDictItemAdd() {
	 var R = _loginUserLanguageResource;
	
	 if (!_selectedSysDataId) {
	     mini.alert(R.checkOne, R.tip);
	     return;
	 }
	 // 与 ExtJS 一致：只有指定字典 code 才允许添加
	 if (_selectedSysDataCode !== 'realTimeMonitoring_Overview'
	     && _selectedSysDataCode !== 'historyQuery_Overview') {
	     return;
	 }
	
	 // 当前设备类型（取当前二级标签的 deviceTypeId）
	 var deviceType = currentLevel2 ? (currentLevel2.deviceTypeId || '') : '';
	 if (deviceType && deviceType.indexOf(',') > -1) {
	     deviceType = currentLevel1 ? (currentLevel1.deviceTypeId || '') : '';
	 }
	
	 mini.open({
	     title: R.addDataItem,
	     url: context + '/miniui-app/modules/dataDictionary/dataDictionaryItemAddWindow.jsp',
	     width: '60%',
	     height: '80%',
	     modal: true,
	     allowResize: true,
	     maxable: true,
	     onload: function () {
	         var iframe = this.getIFrameEl();
	         var cw = iframe.contentWindow;
	         cw.setData({
	             lang: (loginUserLanguage || '').toUpperCase(),
	             mode: 'save',
	             sysDataId: _selectedSysDataId,
	             deviceType: deviceType
	         });
	         // 子窗口保存成功后刷新右侧字典项列表 + 左侧字典主表
	         cw._parentRefreshDictItem = function () {
	             loadDictItemGrid();
	             loadSysDataGrid();
	         };
	     }
	 });
}

//================================================================
//右侧字典项 - 批量删除（对照 ExtJS delfindtattxtInfo）
//================================================================
function onDictItemDel() {
	 var R = _loginUserLanguageResource;
	 var grid = mini.get('dictItemGrid');
	 if (!grid) return;
	
	 var rows = grid.getSelecteds() || [];
	 if (rows.length === 0) {
	     mini.alert(R.checkOne, R.tip);
	     return;
	 }
	
	 var lang = (loginUserLanguage || '').toUpperCase();
	 var idList = [];
	 var nameList = [];
	 for (var i = 0; i < rows.length; i++) {
	     idList.push(rows[i].dataitemid);
	     var nm = '';
	     if (lang === 'ZH_CN')      nm = rows[i].name_zh_CN;
	     else if (lang === 'EN')    nm = rows[i].name_en;
	     else if (lang === 'RU')    nm = rows[i].name_ru;
	     nameList.push(nm);
	 }
	
	 var deleteInfo;
	 if (idList.length === 1) {
	     deleteInfo = R.fiedName
	         + ":<font color=red>" + nameList[0] + "</font>"
	         + "<br/>" + R.confirmDelete;
	 } else {
	     deleteInfo = R.sparseRecordCount
	         + ":<font color=red>" + idList.length + "</font>"
	         + "<br/>" + R.confirmDelete;
	 }
	
	 mini.confirm(deleteInfo, R.tip, function (action) {
	     if (action !== 'ok') return;
	     $.ajax({
	         url: context + '/dataitemsInfoController/deleteDataitemsInfoById',
	         type: 'POST',
	         data: { paramsId: idList.join(',') },
	         dataType: 'json',
	         success: function (result) {
	             if (result.flag === true) {
	                 mini.alert(R.deleteSuccessfully, R.tip, function () {
	                     loadDictItemGrid();
	                 });
	             } else {
	                 mini.alert('<font color="red">' + R.deleteFailed + '</font>', R.tip);
	             }
	         },
	         error: function () {
	             mini.alert(R.requestFailed, R.tip);
	         }
	     });
	 });
}

//================================================================
//右侧字典项 - 批量保存（对照 ExtJS batchUpdateDictionaryItemInfo）
//================================================================
function onDictItemSave() {
	 var R = _loginUserLanguageResource;
	 var grid = mini.get('dictItemGrid');
	 if (!grid) return;
	
	 // ★ 提交当前单元格编辑，确保 getChanges 拿到最新值
	 grid.commitEdit();
	
	 // ★ 只取修改过的行（等价 ExtJS store.getModifiedRecords()）
	 var modified = grid.getChanges('modified', false) || [];
	 if (modified.length === 0) {
	     mini.alert(R.noDataChange, R.tip);
	     return;
	 }
	
	 var arr = [];
	 for (var i = 0; i < modified.length; i++) {
	     var rec = modified[i];
	     arr.push({
	         dataitemid: rec.dataitemid,
	         name_zh_CN: rec.name_zh_CN,
	         name_en:    rec.name_en,
	         name_ru:    rec.name_ru,
	         datavalue:  rec.datavalue,
	         status_cn:  rec.status_cn ? 1 : 0,
	         status_en:  rec.status_en ? 1 : 0,
	         status_ru:  rec.status_ru ? 1 : 0,
	         sorts:      rec.sorts,
	         status:     rec.status    ? 1 : 0
	     });
	 }
	
	 $.ajax({
	     url: context + '/dataitemsInfoController/batchUpdateDictionaryItemInfo',
	     type: 'POST',
	     data: { data: JSON.stringify(arr) },
	     dataType: 'json',
	     success: function (result) {
	         if (result.success === true && result.flag === true) {
	             mini.alert(R.savedSuccessfully, R.tip);
	             grid.accept();
	             loadDictItemGrid();
	             loadSysDataGrid();     // ExtJS 保存后额外刷新主表
	         } else if (result.success === true && result.flag === false) {
	             mini.alert('<font color="red">' + R.saveFailed + '</font>', R.tip);
	         } else {
	             mini.alert('<font color="red">' + R.saveFailed + '</font>', R.tip);
	         }
	     },
	     error: function () {
	         mini.alert(R.requestFailed, R.tip);
	     }
	 });
}