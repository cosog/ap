// ================================================================
// 场景模型模块 - sceneModelInfo.js
// ================================================================

var cs = null;           // CanvasSelect 实例
var _destroyed = false;  // 销毁标志
var _clearingSelection = false; // 防止取消选中陷入死循环
var _keydownHandler = null;     // 全局键盘监听句柄

// 画布数据
var bindingMap = new Map();       // uuid -> {deviceId, fieldName...}
var labelStyleMap = new Map();    // uuid -> {font, bgColor...}
var userColorMap = new Map();     // uuid -> 边框颜色
var userLineWidthMap = new Map(); // uuid -> 边框宽度

var currentRecord = null;         // 当前选中的场景记录
var imageUrl = '';                // 当前画布背景图
var lastSelectedUuid = null;      // 最近选中的形状 uuid
var _loadSceneSeq = 0;            // 请求序号，防并发
var _userCreateType = 0;          // 记录用户选中的绘制类型
var _suppressStyleChange = false; // 防止样式回显引发死循环

// 画布尺寸变化相关
var _isResizing = false;
var _resizeTimer = null;
var _isSettingImage = false;

// 默认样式
var defaultLabelStyle = {
    font: '12px', bgColor: '#4A6CF7', bgOpacity: 100,
    textColor: '#FFFFFF', hide: false, hideDeviceName: true, hideFieldName: false, up: true
};
var globalStyle = {
    strokeStyle: '#00FF00', fillColor: '#0000FF', fillOpacity: 25,
    lineWidth: 1, ctrlRadius: 3
};
var TRANSPARENT_STROKE = 'rgba(0,0,0,0.01)';

function getBlankImage() {
    return context + '/sceneModelController/getSceneImage?name=__blank__.svg&_v=2';
}

var _originTimer = null;
var _rafPending = false;
var _cachedOrigin = { x: 0, y: 0, scale: 1, ready: false };
var _sceneModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };

// ---------------------------------------------------------------
// 页面入口
// ---------------------------------------------------------------
function initSceneModelPage() {
    _sceneModuleRight = getRoleModuleRight(context + '/roleManagerController/getRoleModuleRight', 'SceneModel') || { viewFlag: 0, editFlag: 0, controlFlag: 0 };
    _sceneModuleRight.editFlag = parseInt(_sceneModuleRight.editFlag) || 0;

    forceParseInnerPanels();
    initI18n();
    initSpectrum();
    bindKeyboardEvents();
    
    setTimeout(function() {
        initCanvasSelect();
        updateBtnStatus();
        initSceneGridConfig();
        initSceneMessageListener();
        bindControlEvents();
        loadSceneGrid();
        startOriginTimer();
        
        // ★ 初始化默认选中“选择”按钮
        updateToolbarButtons(0);
    }, 100);
}

// ================================================================
// ★ 核心：统一控制工具栏按钮的互斥高亮状态
// ================================================================
function updateToolbarButtons(createType) {
    var btnSelect = mini.get('btnSelect');
    var btnRect = mini.get('btnRect');
    var btnPoint = mini.get('btnPoint');
    
    if (btnSelect) btnSelect.setChecked(createType === 0);
    if (btnRect) btnRect.setChecked(createType === 1);
    if (btnPoint) btnPoint.setChecked(createType === 3);
}

function forceParseInnerPanels() {
    var ids = ['sceneModelPanel', 'rightTopPanel', 'canvasPanel', 'stylePanel', 'annotationPanel', 'sceneModelGrid', 'bindingTree'];
    for (var i = 0; i < ids.length; i++) {
        var el = document.getElementById(ids[i]);
        if (el && !mini.get(ids[i])) {
            try { mini.parse(el); } catch (e) { console.warn('parse ' + ids[i] + ' 失败'); }
        }
    }
}

function getComp(id) { return mini.get(id); }

// ================================================================
// 1. 国际化 & 颜色选择器
// ================================================================
function initI18n() {
    var p = getComp('sceneModelPanel');
    if (p) p.setTitle(_loginUserLanguageResource.sceneModel);
    p = getComp('stylePanel');
    if (p) p.setTitle(_loginUserLanguageResource.styleSettings);
    p = getComp('annotationPanel');
    if (p) p.setTitle(_loginUserLanguageResource.annotationList);

    var sceneModelGrid = getComp('sceneModelGrid');
    if (sceneModelGrid) sceneModelGrid.setEmptyText(_loginUserLanguageResource.emptyMsg);
    var bindingTree = getComp('bindingTree');
    if (bindingTree) bindingTree.setEmptyText(_loginUserLanguageResource.emptyMsg);

    setBtnText('btnRefreshScene', _loginUserLanguageResource.refresh);
    setBtnText('btnAddScene',     _loginUserLanguageResource.add);
    setBtnText('btnDelScene',     _loginUserLanguageResource.deleteData);
    setBtnText('btnSaveScene',    _loginUserLanguageResource.save);

    setBtnText('btnSelect',       '✋ '+_loginUserLanguageResource.select);
    setBtnText('btnRect',         '▭ '+_loginUserLanguageResource.rectangle);
    setBtnText('btnPoint',        '• '+_loginUserLanguageResource.point);
    setBtnText('btnZoomIn',       '🔍+');
    setBtnText('btnZoomOut',      '🔍−');
    setBtnText('btnFitZoom',      _loginUserLanguageResource.fitZoom);
    setBtnText('btnSelectImg',    '🖼️ '+_loginUserLanguageResource.selectServerImage);
    setBtnText('btnUploadImg',    '📤 '+_loginUserLanguageResource.uploadLocalImage);
    setBtnText('btnSaveAnnotation', _loginUserLanguageResource.saveAnnotations);

    setElText('legendGraphicStyle', _loginUserLanguageResource.graphicStyle);
    setElText('legendLabelStyle',   _loginUserLanguageResource.labelStyle);

    setElText('lblStrokeStyle', _loginUserLanguageResource.borderColor + '：');
    setElText('lblLineWidth',   _loginUserLanguageResource.borderWidth + '：');
    setElText('lblFillStyle',   _loginUserLanguageResource.fillColor + '：');
    setElText('lblCtrlRadius',  _loginUserLanguageResource.ctrlPointSize + '：');

    setElText('lblLabelFont',   _loginUserLanguageResource.font + '：');
    setElText('lblLabelBg',     _loginUserLanguageResource.labelBg + '：');
    setElText('lblLabelText',   _loginUserLanguageResource.textColor + '：');

    setElText('lblLabelUp',        _loginUserLanguageResource.labelOnTop + '：');
    setElText('lblHideDeviceName', _loginUserLanguageResource.hideDeviceName + '：');
    setElText('lblHideFieldName',  _loginUserLanguageResource.hideFieldName + '：');
    setElText('lblHideLabel',      _loginUserLanguageResource.hideLabel + '：');

    var fontCombo = getComp('labelFont');
    if (fontCombo) {
        fontCombo.setData([
            { id: '11px', text: '11px' },
            { id: '12px', text: '12px' },
            { id: '13px', text: '13px' },
            { id: '14px', text: '14px' }
        ]);
        fontCombo.setValue('12px');
    }
}

function setBtnText(id, text) {
    var b = getComp(id);
    if (b && text !== undefined) b.setText(text);
}

function setElText(id, text) {
    var el = document.getElementById(id);
    if (el && text !== undefined) el.innerText = text;
}

function initSpectrum() {
    var palette = [
        ['#000','#444','#666','#999','#ccc','#eee','#f3f3f3','#fff'],
        ['#f00','#f90','#ff0','#0f0','#0ff','#00f','#90f','#f0f'],
        ['#f4cccc','#fce5cd','#fff2cc','#d9ead3','#d0e0e3','#cfe2f3','#d9d2e9','#ead1dc'],
        ['#ea9999','#f9cb9c','#ffe599','#b6d7a8','#a2c4c9','#9fc5e8','#b4a7d6','#d5a6bd'],
        ['#e06666','#f6b26b','#ffd966','#93c47d','#76a5af','#6fa8dc','#8e7cc3','#c27ba0'],
        ['#c00','#e69138','#f1c232','#6aa84f','#45818e','#3d85c6','#674ea7','#a64d79'],
        ['#900','#b45f06','#bf9000','#38761d','#134f5c','#0b5394','#351c75','#741b47'],
        ['#600','#783f04','#7f6000','#274e13','#0c343d','#073763','#20124d','#4c1130']
    ];

    var cancelTxt = _loginUserLanguageResource.cancel;
    var confirmTxt = _loginUserLanguageResource.confirm;

    if ($('#strokeColor').length) {
        $('#strokeColor').spectrum({
            color: '#00FF00', showAlpha: false, showInput: true, showInitial: true,
            showPalette: true, showButtons: false, clickoutFiresChange: true, preferredFormat: 'hex',
            cancelText: cancelTxt, chooseText: confirmTxt, palette: palette,
            disabled: (_sceneModuleRight.editFlag != 1)
        });
    }

    if ($('#fillColor').length) {
        $('#fillColor').spectrum({
            color: 'rgba(0,0,255,0.25)', showAlpha: true, showInput: true, showInitial: true,
            showPalette: true, showButtons: false, clickoutFiresChange: true, preferredFormat: 'hex',
            cancelText: cancelTxt, chooseText: confirmTxt, palette: palette,
            disabled: (_sceneModuleRight.editFlag != 1)
        });
    }

    if ($('#labelBgColor').length) {
        $('#labelBgColor').spectrum({
            color: '#4A6CF7', showAlpha: false, showInput: true, showInitial: true,
            showPalette: true, showButtons: false, clickoutFiresChange: true, preferredFormat: 'hex',
            cancelText: cancelTxt, chooseText: confirmTxt, palette: palette,
            disabled: (_sceneModuleRight.editFlag != 1)
        });
    }

    if ($('#labelTextColor').length) {
        $('#labelTextColor').spectrum({
            color: '#FFFFFF', showAlpha: false, showInput: true, showInitial: true,
            showPalette: true, showButtons: false, clickoutFiresChange: true, preferredFormat: 'hex',
            cancelText: cancelTxt, chooseText: confirmTxt, palette: palette,
            disabled: (_sceneModuleRight.editFlag != 1)
        });
    }

    var styleInputs = ['lineWidth', 'ctrlRadius', 'labelFont', 'labelUp', 'hideDeviceName', 'hideFieldName', 'hideLabel'];
    for (var i = 0; i < styleInputs.length; i++) {
        var comp = mini.get(styleInputs[i]);
        if (comp) comp.setEnabled(_sceneModuleRight.editFlag == 1);
    }
}

// ================================================================
// 2. 权限控制与页面事件绑定
// ================================================================
function updateBtnStatus() {
    var editFlag = (_sceneModuleRight.editFlag == 1);
    var btnIds = [
        'btnAddScene', 'btnDelScene', 'btnSaveScene',
        'btnRect', 'btnPoint', 'btnSelectImg', 'btnUploadImg', 'btnSaveAnnotation'
    ];
    for (var i = 0; i < btnIds.length; i++) {
        var btn = mini.get(btnIds[i]);
        if (btn) btn.setEnabled(editFlag);
    }
}

function bindControlEvents() {
    $('#strokeColor').on('move.spectrum', function() { onGraphicStyleChange(); });
    $('#fillColor').on('move.spectrum', function() { onGraphicStyleChange(); });
    $('#labelBgColor').on('move.spectrum', function() { onLabelStyleChange(); });
    $('#labelTextColor').on('move.spectrum', function() { onLabelStyleChange(); });
    
    ['lineWidth', 'ctrlRadius'].forEach(function(id) {
        var comp = mini.get(id);
        if (comp) comp.on('valuechanged', function() { onGraphicStyleChange(); });
    });
    
    ['labelFont', 'labelUp', 'hideDeviceName', 'hideFieldName', 'hideLabel'].forEach(function(id) {
        var comp = mini.get(id);
        if (comp) comp.on('valuechanged', function() { onLabelStyleChange(); });
    });
    
    var tree = mini.get('bindingTree');
    if (tree) tree.on('nodeclick', function(e) { onBindingTreeSelect(e); });
}

// ================================================================
// 3. 左侧列表增删改查
// ================================================================
function initSceneGridConfig() {
    var grid = getComp('sceneModelGrid');
    if (!grid) return;
    
    grid.set({
        url: context + '/sceneModelController/getSceneModelList',
        idField: 'modelId',
        allowAlternate: true,
        showPager: false,
        showEmptyText: true,
        cellEditAction: 'celldblclick',
        onbeforeload: onSceneGridBeforeLoad,
        onload: onSceneGridLoad,
        onselectionchanged: onSceneGridSelectionChanged,
        oncellbeginedit: onSceneGridCellBeginEdit
    });
}

function loadSceneGrid() {
    var grid = mini.get('sceneModelGrid');
    if (!grid) return;
    grid.load();
}

function onSceneGridBeforeLoad(e) {
    var params = e.params || {};
    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    e.params = params;
}

function onSceneGridLoad(e) {
    var grid = e.sender;
    if (!grid._columnsCreated) {
        createSceneModelGridColumns(grid, e.result || {});
        grid._columnsCreated = true;
    }
    var keepId = grid._keepSelectId;
    var keepName = grid._keepSelectName;
    grid._keepSelectId = null;
    grid._keepSelectName = null;
    var data = grid.getData() || [];
    if (data.length > 0) {
        var target = null;
        if (keepId) {
            for (var i = 0; i < data.length; i++) {
                if (String(data[i].modelId) === String(keepId)) { target = data[i]; break; }
            }
        }
        if (!target && keepName) {
            for (var j = 0; j < data.length; j++) {
                if (data[j].name_zh_CN === keepName || data[j].name_en === keepName || data[j].name_ru === keepName) {
                    target = data[j]; break;
                }
            }
        }
        
        if (grid.getSelected()){
        	target=grid.getSelected();
        }else{
        	if (!target) target = data[0];
        }
        
        
        grid.select(target);
        
        // ★ 延时获取行号并自动滚动定位
        var selectRow = grid.indexOf(target);
        setTimeout(function () {
            if (selectRow >= 0) {
                grid.scrollIntoView(selectRow);
            }
        }, 150);
        
    } else {
        currentRecord = null;
        clearScene();
    }
}

function onSceneGridCellBeginEdit(e) {
    if (_sceneModuleRight.editFlag != 1) e.cancel = true;
}

function onSceneGridSelectionChanged(e) {
    var grid = e.sender;
    var row = grid.getSelected();
    if (row) {
        loadScene(row);
    } else {
        currentRecord = null;
        clearScene();
    }
}

function createSceneModelGridColumns(grid, result) {
    var R = _loginUserLanguageResource;
    var editFlag = (_sceneModuleRight.editFlag == 1);
    
    var showChineseName = result.showChineseName === undefined ? true : !!result.showChineseName;
    var showEnglishName = result.showEnglishName === undefined ? true : !!result.showEnglishName;
    var showRussianName = result.showRussianName === undefined ? true : !!result.showRussianName;
    
    var textEditor = editFlag ? { type: 'textbox' } : null;
    var sortEditor = editFlag ? { type: 'spinner', minValue: 1, maxValue: 9999999999 } : null;

    var columns = [];
    if (editFlag) {
        columns.push({ type: 'checkcolumn', width: 40, header: '', headerAlign: 'center', align: 'center' });
    }
    columns.push({ type: 'indexcolumn', width: 50, header: R.idx, headerAlign: 'center', align: 'center' });
    columns.push({ field: 'name_zh_CN', header: R.language_zh_CN, headerAlign: 'center', align: 'left', width: 120, visible: showChineseName, editor: textEditor });
    columns.push({ field: 'name_en', header: R.language_en, headerAlign: 'center', align: 'left', width: 120, visible: showEnglishName, editor: textEditor });
    columns.push({ field: 'name_ru', header: R.language_ru, headerAlign: 'center', align: 'left', width: 120, visible: showRussianName, editor: textEditor });
    columns.push({ field: 'sort', header: R.sequenceNumber, headerAlign: 'center', align: 'center', width: 60, editor: sortEditor });
    
    grid.setColumns(columns);
    grid.setAllowCellEdit(editFlag);
    grid.setAllowCellSelect(editFlag);
}

function onSceneRefresh() { loadSceneGrid(); }

function onSceneAdd() {
    if (_sceneModuleRight.editFlag != 1) return;
    var orgId = window.parent && window.parent.getSelectOrgNodeId ? window.parent.getSelectOrgNodeId() : '';
    mini.open({
        title: _loginUserLanguageResource.add,
        url: context + '/miniui-app/modules/sceneModel/SceneModelInfoWindow.jsp',
        width: 400,
        height: 280,
        modal: true,
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            iframe.contentWindow.setData({ orgId: orgId });
            iframe.contentWindow.parent._parentRefreshSceneList = function(success, newId, addedName) {
                if (success) {
                    var grid = mini.get('sceneModelGrid');
                    if (grid) {
                        if (newId) grid._keepSelectId = newId;
                        if (addedName) grid._keepSelectName = addedName;
                        loadSceneGrid();
                    }
                }
            };
        }
    });
}

function onSceneDel() {
    if (_sceneModuleRight.editFlag != 1) return;
    var grid = mini.get('sceneModelGrid');
    if (!grid) return;
    var rows = grid.getSelecteds() || [];
    if (rows.length === 0) { mini.alert(_loginUserLanguageResource.checkOne); return; }

    var ids = [], names = [];
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].modelId);
        names.push(rows[i].name_zh_CN || rows[i].name_en || rows[i].name_ru || '');
    }
    var deleteInfo = ids.length === 1 
        ? _loginUserLanguageResource.name + '：<font color="red">' + names[0] + '</font><br/>' + _loginUserLanguageResource.confirmDelete
        : _loginUserLanguageResource.sparseRecordCount + '：<font color="red">' + ids.length + '</font><br/>' + _loginUserLanguageResource.confirmDelete;

    mini.confirm(deleteInfo, _loginUserLanguageResource.tip, function (action) {
        if (action !== 'ok') return;
        mini.mask({ el: 'sceneModelPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });
        $.ajax({
            url: context + '/sceneModelController/deleteSceneModel',
            type: 'POST',
            data: { ids: ids.join(',') },
            dataType: 'json',
            success: function (result) {
                mini.unmask('sceneModelPanel');
                if (result && result.success === true) {
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                    onSceneRefresh();
                } else {
                    mini.alert('<font color="red">' + _loginUserLanguageResource.deleteFailed + '</font>');
                }
            },
            error: function () {
                mini.unmask('sceneModelPanel');
                mini.alert(_loginUserLanguageResource.requestFailed);
            }
        });
    });
}

function onSceneSave() {
    if (_sceneModuleRight.editFlag != 1) return;
    var grid = mini.get('sceneModelGrid');
    if (!grid) return;
    grid.commitEdit();
    var modifiedRecords = grid.getChanges('modified', false);
    if (!modifiedRecords || modifiedRecords.length === 0) {
        mini.alert(_loginUserLanguageResource.noDataChange);
        return;
    }
    var modifiedModels = [];
    for (var i = 0; i < modifiedRecords.length; i++) {
        var rec = modifiedRecords[i];
        modifiedModels.push({
            id: rec.modelId, name_zh_CN: rec.name_zh_CN, name_en: rec.name_en, name_ru: rec.name_ru,
            sort: (rec.sort != undefined && rec.sort !== '' && !isNaN(rec.sort)) ? rec.sort : null
        });
    }
    mini.mask({ el: 'sceneModelPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });
    $.ajax({
        url: context + '/sceneModelController/saveSceneModelInfo',
        type: 'POST',
        data: { data: JSON.stringify(modifiedModels) },
        dataType: 'json',
        success: function (result) {
            mini.unmask('sceneModelPanel');
            if (result && result.success === true) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully);
                grid.accept();
                onSceneRefresh();
            } else {
                mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>');
            }
        },
        error: function () {
            mini.unmask('sceneModelPanel');
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

// ================================================================
// 4. 初始化画布与事件
// ================================================================
function initCanvasSelect() {
    var canvasEl = document.getElementById('sceneCanvas');
    if (!canvasEl) return;

    // ★ 核心修复：加上 tabindex，否则无法获取键盘焦点
    if (!canvasEl.hasAttribute('tabindex')) {
        canvasEl.setAttribute('tabindex', '0');
    }

    if (typeof CanvasSelect === 'undefined') { console.error('CanvasSelect未引入'); return; }

    cs = new CanvasSelect(canvasEl, getBlankImage());
    cs._canvasCurrentUrl = getBlankImage();
    cs.ctrlRadius = 3;
    cs.createType = 0;
    cs.scrollZoom = true;
    cs.hideLabel = true;

    if (_sceneModuleRight.editFlag != 1) {
        try { cs.readonly = true; cs.locked = true; } catch(e) {}
    }

    cs.on('load', function() {
        if (typeof cs.fitZoom === 'function') { try { cs.fitZoom(); } catch(e){} }
        _nudgeCanvasMouseCenter();
        updateOriginIfNeeded(true);
        scheduleRenderLabels();

        setTimeout(function() {
            if (!cs || _destroyed) return;
            _nudgeCanvasMouseCenter();
            updateOriginIfNeeded(true);
            scheduleRenderLabels();
        }, 150);
    });

    cs.on('add', function(info) { onShapeAdded(info); });
    cs.on('select', function(info) {
        if (_clearingSelection) return;
        var uuid = extractUuid(info);
        if (!uuid) {
            if (lastSelectedUuid) clearSelectionUI();
        } else {
            onShapeSelected(uuid);
        }
    });
    
    cs.on('delete', function(info) {
        var uuid = info ? (info.uuid || info.oldUuid) : null;
        if (uuid) {
            bindingMap.delete(uuid);
            labelStyleMap.delete(uuid);
            userColorMap.delete(uuid);
            userLineWidthMap.delete(uuid);
            if (lastSelectedUuid === uuid) {
                lastSelectedUuid = null;
                resetStylePanel();
            }
            refreshBindingTree();
            scheduleRenderLabels();
        }
    });
    
    cs.on('updated', function() { scheduleRenderLabels(); });
    cs.on('coor', function() { scheduleRenderLabels(); });

    // 点击画布时，主动让画布获取焦点，确保键盘事件可用
    canvasEl.addEventListener('click', function() {
        try { canvasEl.focus(); } catch(e) {}
        
        if (!cs || cs.createType !== 0) return;
        setTimeout(function() {
            if (!cs || cs.createType !== 0) return;
            var active = cs.activeShape;
            if (!active && lastSelectedUuid) {
                clearSelectionUI();
            }
        }, 50);
    });

    canvasEl.addEventListener('dblclick', function() {
        if (!cs || cs.createType !== 0) return;
        var info = cs.activeShape;
        if (info) {
            var uuid = extractUuid(info);
            if (uuid) openDataSourceWindow(uuid, true);
        }
    });

    window.addEventListener('resize', function() {
        if (_resizeTimer) clearTimeout(_resizeTimer);
        _resizeTimer = setTimeout(handleCanvasResize, 120);
    });
}

function handleCanvasResize() {
    if (_isResizing || _destroyed || !cs) return;
    _isResizing = true;
    try {
        var dom = getCanvasEl();
        if (!dom) return;
        var rect = dom.getBoundingClientRect();
        var cssW = Math.round(rect.width);
        var cssH = Math.round(rect.height);
        if (cssW < 20 || cssH < 20) return;

        if (dom.tagName === 'CANVAS') {
            if (dom.width !== cssW) dom.width = cssW;
            if (dom.height !== cssH) dom.height = cssH;
        }

        if (typeof cs.resize === 'function') cs.resize();
        if (typeof cs.fitZoom === 'function') cs.fitZoom();

        _nudgeCanvasMouseCenter();
        updateOriginIfNeeded(true);
        scheduleRenderLabels();
    } catch (e) {
        console.warn('resize 失败', e);
    } finally {
        _isResizing = false;
    }
}

function getCanvasEl() {
    if (cs && cs.canvas && cs.canvas.tagName === 'CANVAS') return cs.canvas;
    return document.getElementById('sceneCanvas');
}

function _nudgeCanvasMouseCenter() {
    if (_destroyed) return;
    var canvasEl = getCanvasEl();
    if (!canvasEl) return;
    var rect = canvasEl.getBoundingClientRect();
    if (rect.width < 10 || rect.height < 10) return;
    var offX = rect.width / 2;
    var offY = rect.height / 2;
    var clientX = rect.left + offX;
    var clientY = rect.top + offY;
    var ev;
    try {
        ev = new MouseEvent('mousemove', { bubbles: true, cancelable: true, view: window, clientX: clientX, clientY: clientY, screenX: clientX, screenY: clientY, button: 0 });
    } catch (e) {
        try { ev = document.createEvent('MouseEvents'); ev.initMouseEvent('mousemove', true, true, window, 0, 0, 0, clientX, clientY, false, false, false, false, 0, null); } catch (e2) { return; }
    }
    try { Object.defineProperty(ev, 'offsetX', { get: function() { return offX; } }); } catch (e) {}
    try { Object.defineProperty(ev, 'offsetY', { get: function() { return offY; } }); } catch (e) {}
    try { canvasEl.dispatchEvent(ev); } catch (e) {}
}

function dispatchClickOnBlank() {
    if (_destroyed) return;
    var canvasEl = getCanvasEl();
    if (!canvasEl) return;
    var rect = canvasEl.getBoundingClientRect();
    var clientX = rect.left + 1;
    var clientY = rect.top + 1;
    var baseProps = { bubbles: true, cancelable: true, composed: true, view: window, clientX: clientX, clientY: clientY, screenX: clientX, screenY: clientY, button: 0 };
    var pointerProps = Object.assign({}, baseProps, { pointerId: 1, pointerType: 'mouse', isPrimary: true, width: 1, height: 1, pressure: 0.5 });
    try {
        if (typeof PointerEvent !== 'undefined') {
            canvasEl.dispatchEvent(new PointerEvent('pointerover', Object.assign({}, pointerProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new PointerEvent('pointerenter', Object.assign({}, pointerProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new PointerEvent('pointermove', Object.assign({}, pointerProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new PointerEvent('pointerdown', Object.assign({}, pointerProps, { buttons: 1 })));
            canvasEl.dispatchEvent(new PointerEvent('pointerup', Object.assign({}, pointerProps, { buttons: 0 })));
        }
        canvasEl.dispatchEvent(new MouseEvent('mouseover', Object.assign({}, baseProps, { buttons: 0 })));
        canvasEl.dispatchEvent(new MouseEvent('mousemove', Object.assign({}, baseProps, { buttons: 0 })));
        canvasEl.dispatchEvent(new MouseEvent('mousedown', Object.assign({}, baseProps, { buttons: 1 })));
        canvasEl.dispatchEvent(new MouseEvent('mouseup', Object.assign({}, baseProps, { buttons: 0 })));
        canvasEl.dispatchEvent(new MouseEvent('click', Object.assign({}, baseProps, { buttons: 0 })));
    } catch (e) { console.warn('派发空白点击失败', e); }
}

function dispatchClickOnShape(shape) {
    if (_destroyed || !cs || !shape || !_cachedOrigin.ready) return;
    var canvasEl = getCanvasEl();
    if (!canvasEl) return;
    var center = getShapeCenterInWorld(shape);
    if (!center) return;
    var screenX = _cachedOrigin.x + center.x * _cachedOrigin.scale;
    var screenY = _cachedOrigin.y + center.y * _cachedOrigin.scale;
    var rect = canvasEl.getBoundingClientRect();
    var clientX = rect.left + screenX;
    var clientY = rect.top + screenY;
    var baseProps = { bubbles: true, cancelable: true, composed: true, view: window, clientX: clientX, clientY: clientY, screenX: clientX, screenY: clientY, button: 0 };
    var pointerProps = Object.assign({}, baseProps, { pointerId: 1, pointerType: 'mouse', isPrimary: true, width: 1, height: 1, pressure: 0.5 });
    try {
        if (typeof PointerEvent !== 'undefined') {
            canvasEl.dispatchEvent(new PointerEvent('pointerover', Object.assign({}, pointerProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new PointerEvent('pointerenter', Object.assign({}, pointerProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new PointerEvent('pointermove', Object.assign({}, pointerProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new PointerEvent('pointerdown', Object.assign({}, pointerProps, { buttons: 1 })));
            canvasEl.dispatchEvent(new PointerEvent('pointerup', Object.assign({}, pointerProps, { buttons: 0 })));
        }
        canvasEl.dispatchEvent(new MouseEvent('mouseover', Object.assign({}, baseProps, { buttons: 0 })));
        canvasEl.dispatchEvent(new MouseEvent('mousemove', Object.assign({}, baseProps, { buttons: 0 })));
        canvasEl.dispatchEvent(new MouseEvent('mousedown', Object.assign({}, baseProps, { buttons: 1 })));
        canvasEl.dispatchEvent(new MouseEvent('mouseup', Object.assign({}, baseProps, { buttons: 0 })));
        canvasEl.dispatchEvent(new MouseEvent('click', Object.assign({}, baseProps, { buttons: 0 })));
    } catch (e) { console.warn('事件派发失败', e); }
}

function getShapeCenterInWorld(shape) {
    if (!shape || !shape.coor) return null;
    var coor = shape.coor;
    var t = shape.type;
    if (t === 1) {
        var a = coor[0], b = coor[1];
        if (!Array.isArray(a) || !Array.isArray(b)) return null;
        return { x: (a[0] + b[0]) / 2, y: (a[1] + b[1]) / 2 };
    }
    if (t === 3 || t === 5) return { x: coor[0], y: coor[1] };
    if (t === 2 || t === 4) {
        var sx = 0, sy = 0;
        for (var i = 0; i < coor.length; i++) { sx += coor[i][0]; sy += coor[i][1]; }
        return { x: sx / coor.length, y: sy / coor.length };
    }
    return null;
}

// ★ 修复工具栏模式切换，更新按钮状态
function onCanvasSelect() { 
    if (cs) cs.createType = 0; 
    _userCreateType = 0;
    updateToolbarButtons(0);
    clearSelectionUI();
}
function onCanvasRect() { 
    if (_sceneModuleRight.editFlag == 1 && cs) {
        cs.createType = 1; 
        _userCreateType = 1;
        updateToolbarButtons(1);
        lastSelectedUuid = null;
        resetStylePanel();
        var tree = mini.get('bindingTree');
        if (tree) tree.deselectAll();
    }
}
function onCanvasPoint() { 
    if (_sceneModuleRight.editFlag == 1 && cs) {
        cs.createType = 3; 
        _userCreateType = 3;
        updateToolbarButtons(3);
        lastSelectedUuid = null;
        resetStylePanel();
        var tree = mini.get('bindingTree');
        if (tree) tree.deselectAll();
    }
}

function afterZoom() {
    if (_destroyed) return;
    setTimeout(function() {
        if (_destroyed) return;
        _nudgeCanvasMouseCenter();
        updateOriginIfNeeded(true);
        scheduleRenderLabels();
    }, 30);
}

function onCanvasZoomIn() { 
    if (cs) { cs.setScale(true); afterZoom(); } 
}
function onCanvasZoomOut() { 
    if (cs) { cs.setScale(false); afterZoom(); } 
}
function onCanvasFitZoom() { 
    if (cs) { cs.fitZoom(); afterZoom(); } 
}

// ================================================================
// 5. 标签渲染逻辑
// ================================================================
function startOriginTimer() {
    if (_originTimer || _destroyed) return;
    _originTimer = setInterval(function() {
        if (_destroyed) return;
        if (updateOriginIfNeeded()) scheduleRenderLabels();
    }, 100);
}

function updateOriginIfNeeded(force) {
    if (!cs || !cs.mouse || !cs.position || cs.mouse.length < 2 || cs.position.length < 2) return false;
    var scale = cs.scale || 0;
    var mx = cs.mouse[0], my = cs.mouse[1];
    var px = cs.position[0], py = cs.position[1];
    
    if (!isFinite(mx) || !isFinite(my) || !isFinite(px) || !isFinite(py) || !isFinite(scale) || scale <= 0) {
        return false;
    }

    var newX = mx - px * scale;
    var newY = my - py * scale;
    if (!isFinite(newX) || !isFinite(newY)) return false;

    var co = _cachedOrigin;
    if (!co.ready || force || Math.abs(newX - co.x) > 1 || Math.abs(newY - co.y) > 1 || Math.abs(scale - co.scale) > 0.001) {
        _cachedOrigin = { x: newX, y: newY, scale: scale, ready: true };
        return true;
    }
    return false;
}

function scheduleRenderLabels() {
    if (_rafPending || _destroyed) return;
    _rafPending = true;
    requestAnimationFrame(function() {
        _rafPending = false;
        renderLabels();
    });
}

function renderLabels() {
    var layer = document.getElementById('sceneLabelLayer');
    if (!layer || !_cachedOrigin.ready || !cs || !cs.dataset) return;

    var origin = _cachedOrigin;
    var existing = {};
    Array.from(layer.children).forEach(function(el) { existing[el.dataset.uuid] = el; });
    var visible = {};

    cs.dataset.forEach(function(shape) {
        var binding = bindingMap.get(shape.uuid);
        if (!binding) return;
        var anchor = getShapeTopAnchor(shape);
        if (!anchor) return;

        var cx = Math.round(origin.x + anchor.x * origin.scale);
        var cy = Math.round(origin.y + anchor.y * origin.scale);
        var style = labelStyleMap.get(shape.uuid) || defaultLabelStyle;
        var up = style.up !== false;
        var translateY = up ? '-100%' : '0';

        var el = existing[shape.uuid];
        if (!el) {
            el = document.createElement('div');
            el.className = 'scene-canvas-label';
            el.dataset.uuid = shape.uuid;
            el.addEventListener('dblclick', function(e) {
                e.stopPropagation();
                openDataSourceWindow(shape.uuid, true);
            });
            el.addEventListener('click', function(e) {
                e.stopPropagation();
                if (cs && cs.createType === 0) {
                    selectShapeByUuid(shape.uuid);
                    selectTreeNodeByUuid(shape.uuid, false); // 点击画布不滚动树
                }
            });
            layer.appendChild(el);
        }

        var labelText = buildLabelText(binding, style);
        el.textContent = labelText;
        var isHidden = style.hide || (labelText === '');
        var bgColor = buildFillColor(style.bgColor, style.bgOpacity);
        var boxShadow = (style.bgOpacity == 0) ? 'none' : '0 2px 6px rgba(0,0,0,0.18)';

        el.style.cssText = 'position:absolute;padding:4px 10px;border-radius:4px;white-space:nowrap;' +
            'font-weight:600;transform:translate(-50%,' + translateY + ');box-shadow:' + boxShadow + ';' +
            'pointer-events:auto;cursor:pointer;left:' + cx + 'px;top:' + cy + 'px;' +
            'background:' + bgColor + ';color:' + style.textColor + ';font-size:' + style.font + ';' +
            'display:' + (isHidden ? 'none' : 'block') + ';';
        visible[shape.uuid] = true;
    });

    Object.keys(existing).forEach(function(uuid) {
        if (!visible[uuid] && existing[uuid].parentNode) {
            existing[uuid].parentNode.removeChild(existing[uuid]);
        }
    });
}

function buildLabelText(b, style) {
    if (!b || !b.deviceId || !b.fieldName) return _loginUserLanguageResource.unbound;
    var hideDev = !style || style.hideDeviceName !== false;
    var hideFld = style && style.hideFieldName === true;
    var devName = b.deviceName || b.deviceId;
    var fldName = b.fieldLabel || b.fieldName;
    if (hideDev && hideFld) return '';
    if (hideDev) return fldName;
    if (hideFld) return devName;
    return devName + '.' + fldName;
}

function getShapeTopAnchor(shape) {
    var coor = shape.coor;
    if (!coor) return null;
    if (shape.type === 1) return { x: (coor[0][0] + coor[1][0]) / 2, y: Math.min(coor[0][1], coor[1][1]) };
    if (shape.type === 3) {
        var r = shape.ctrlRadius != null ? shape.ctrlRadius : (cs.ctrlRadius || 3);
        return { x: coor[0], y: coor[1] - r };
    }
    if (shape.type === 2 || shape.type === 4) {
        var xs = coor.map(function(p) { return p[0]; });
        var ys = coor.map(function(p) { return p[1]; });
        return { x: (Math.min.apply(null, xs) + Math.max.apply(null, xs)) / 2, y: Math.min.apply(null, ys) };
    }
    return null;
}

// ================================================================
// 6. 画布交互与样式联动
// ================================================================
function onShapeAdded(info) {
    var uuid = extractUuid(info);
    
    // ★只有在新增图形时，如果没拿到 uuid，才去取 dataset 的最后一项兜底
    if (!uuid) {
        if (cs && cs.dataset && cs.dataset.length > 0) {
            uuid = cs.dataset[cs.dataset.length - 1].uuid;
        }
    }
    
    if (!uuid) return;

    // 画完立刻锁定 lastSelectedUuid，防止弹窗关闭后丢失
    lastSelectedUuid = uuid;

    userColorMap.set(uuid, '#00FF00');
    userLineWidthMap.set(uuid, 1);

    setTimeout(function() {
        if (_destroyed) return;
        openDataSourceWindow(uuid, false);
    }, 50);

    setTimeout(function() {
        if (_destroyed) return;
        if (!cs) return;

        var newShape = null;
        if (cs.dataset) {
            for (var i = 0; i < cs.dataset.length; i++) {
                if (cs.dataset[i].uuid === uuid) { newShape = cs.dataset[i]; break; }
            }
        }
        if (newShape) {
            newShape.strokeStyle = '#00FF00';
            newShape.lineWidth = 1;
            newShape.fillStyle = 'rgba(0,0,255,0.25)';
            newShape.ctrlRadius = 3;
            newShape._userStrokeStyle = '#00FF00';
            newShape._userLineWidth = 1; 
        }
        
        resetStylePanel();
        lastSelectedUuid = uuid;
        
        try {
            if (typeof cs.update === 'function') {
                cs.update();
            }
        } catch (e) {
            console.warn('画布 update 失败', e);
        }
        
        scheduleRenderLabels();
    }, 150);
}

function onShapeSelected(uuid) {
    lastSelectedUuid = uuid;
    var shape = null;
    for (var i = 0; i < cs.dataset.length; i++) {
        if (cs.dataset[i].uuid === uuid) { shape = cs.dataset[i]; break; }
    }
    if (!shape) return;

    var userColor = userColorMap.get(uuid)
                  || shape._userStrokeStyle
                  || shape.strokeStyle
                  || '#00FF00';
    if (userColor === TRANSPARENT_STROKE) userColor = '#00FF00';

    var userLw = userLineWidthMap.get(uuid);
    if (userLw == null) {
        userLw = (shape._userLineWidth != null) ? shape._userLineWidth
               : (shape.lineWidth != null ? shape.lineWidth : 1);
    }

    var cr = shape.ctrlRadius != null ? shape.ctrlRadius : 3;
    var fillParts = parseFillStyle(shape.fillStyle || 'rgba(0,0,255,0.25)');

    _suppressStyleChange = true;
    try {
        $('#strokeColor').spectrum('set', userColor);
        $('#fillColor').spectrum('set', 'rgba(' + hexToRgb(fillParts.hex) + ',' + (fillParts.alpha/100) + ')');
        
        var labelStyle = labelStyleMap.get(uuid) || defaultLabelStyle;
        $('#labelBgColor').spectrum('set', labelStyle.bgColor);
        $('#labelTextColor').spectrum('set', labelStyle.textColor);

        mini.get('lineWidth').setValue(userLw);
        mini.get('ctrlRadius').setValue(cr);
        mini.get('labelFont').setValue(labelStyle.font);
        mini.get('labelUp').setValue(labelStyle.up !== false);
        mini.get('hideDeviceName').setValue(labelStyle.hideDeviceName !== false);
        mini.get('hideFieldName').setValue(labelStyle.hideFieldName === true);
        mini.get('hideLabel').setValue(!!labelStyle.hide);
    } finally {
        _suppressStyleChange = false;
    }

    selectTreeNodeByUuid(uuid, true); // 代码触发选中时滚动树
}

function selectShapeByUuid(uuid) {
    if (!cs || !cs.dataset) return;
    
    if (cs.createType !== 0) { 
        cs.createType = 0; 
        _userCreateType = 0;
        updateToolbarButtons(0);
    }
    
    var shape = null;
    for (var i = 0; i < cs.dataset.length; i++) {
        if (cs.dataset[i].uuid === uuid) { shape = cs.dataset[i]; break; }
    }
    if (!shape) return;
    
    _suppressStyleChange = true;
    try { cs.activeShape = shape; } catch(e) {}
    onShapeSelected(uuid);
    dispatchClickOnShape(shape);
    
    if (typeof cs.draw === 'function') {
        cs.draw();
    } else if (typeof cs.update === 'function') {
        cs.update();
    }
    
    scheduleRenderLabels();
}

function clearSelectionUI(skipTree) {
    if (_destroyed || _clearingSelection) return;
    _clearingSelection = true;
    try {
        lastSelectedUuid = null;

        if (!skipTree) {
            var tree = mini.get('bindingTree');
            if (tree) {
                try { tree.deselectAll(); } catch (e) {}
            }
        }

        resetStylePanel();

        if (cs) {
            try { cs.activeShape = null; } catch (e) {}
            try { dispatchClickOnBlank(); } catch (e) {}
            
            try {
                if (typeof cs.draw === 'function') cs.draw();
                else if (typeof cs.update === 'function') cs.update();
            } catch (e) {}

            var csRef = cs;
            setTimeout(function() {
                if (_destroyed) return;
                if (!cs || cs !== csRef) return;
                try {
                    if (typeof cs.draw === 'function') cs.draw();
                    else if (typeof cs.update === 'function') cs.update();
                } catch (e) {}
            }, 30);
        }

        scheduleRenderLabels();
    } finally {
        _clearingSelection = false;
    }
}

function resetStylePanel() {
    _suppressStyleChange = true;
    try {
        $('#strokeColor').spectrum('set', '#00FF00');
        $('#fillColor').spectrum('set', 'rgba(0,0,255,0.25)');
        $('#labelBgColor').spectrum('set', '#4A6CF7');
        $('#labelTextColor').spectrum('set', '#FFFFFF');
        mini.get('lineWidth').setValue(1);
        mini.get('ctrlRadius').setValue(3);
        mini.get('labelFont').setValue('12px');
        mini.get('labelUp').setValue(true);
        mini.get('hideDeviceName').setValue(true);
        mini.get('hideFieldName').setValue(false);
        mini.get('hideLabel').setValue(false);
    } finally {
        _suppressStyleChange = false;
    }
}

function onGraphicStyleChange() {
    if (!cs) return;
    if (_suppressStyleChange) return;

    var stroke = $('#strokeColor').spectrum('get') ? $('#strokeColor').spectrum('get').toHexString() : '#00FF00';
    var fillObj = $('#fillColor').spectrum('get');
    var fill = fillObj ? fillObj.toRgbString() : 'rgba(0,0,255,0.25)';
    var rawLw = mini.get('lineWidth').getValue();
    var lw = (rawLw === '' || rawLw === null || rawLw === undefined) ? 1 : parseInt(rawLw, 10);
    
    var cr = mini.get('ctrlRadius').getValue() || 3;

    var canvasLw = (lw <= 0) ? 1 : lw;
    var canvasStroke = (lw <= 0) ? TRANSPARENT_STROKE : stroke;

    if (lastSelectedUuid) {
        userColorMap.set(lastSelectedUuid, stroke);
        userLineWidthMap.set(lastSelectedUuid, lw);
        var shape = cs.dataset.find(function(s) { return s.uuid === lastSelectedUuid; });
        if (shape) {
            shape.strokeStyle = canvasStroke;
            shape.lineWidth = canvasLw;
            shape._userStrokeStyle = stroke;
            shape._userLineWidth = lw;
            shape.fillStyle = fill;
            shape.ctrlRadius = cr;
            if (shape.type === 3) {
                cs.ctrlRadius = cr;
            }
        }
    } else {
        globalStyle = { strokeStyle: stroke, fillColor: fill, lineWidth: lw, ctrlRadius: cr };
        cs.strokeStyle = canvasStroke;
        cs.lineWidth = canvasLw;
        cs.fillStyle = fill;
        cs.ctrlRadius = cr;
    }
    
    if (typeof cs.update === 'function') {
        cs.update();
    }
    scheduleRenderLabels();
}

function onLabelStyleChange() {
    if (!cs) return;
    if (_suppressStyleChange) return;

    var style = {
        font: mini.get('labelFont').getValue() || '12px',
        bgColor: $('#labelBgColor').spectrum('get') ? $('#labelBgColor').spectrum('get').toHexString() : '#4A6CF7',
        bgOpacity: 100,
        textColor: $('#labelTextColor').spectrum('get') ? $('#labelTextColor').spectrum('get').toHexString() : '#FFFFFF',
        up: mini.get('labelUp').getValue() === true,
        hideDeviceName: mini.get('hideDeviceName').getValue() === true,
        hideFieldName: mini.get('hideFieldName').getValue() === true,
        hide: mini.get('hideLabel').getValue() === true
    };
    if (lastSelectedUuid) {
        labelStyleMap.set(lastSelectedUuid, style);
    } else {
        defaultLabelStyle = style;
    }
    scheduleRenderLabels();
}

// ================================================================
// 7. 场景数据加载与保存
// ================================================================
function loadScene(record) {
    if (!cs) return;
    
    if (cs.createType !== 0) {
        cs.createType = 0;
        _userCreateType = 0;
        updateToolbarButtons(0);
    }
    
    if (!record) { currentRecord = null; clearScene(); return; }
    currentRecord = record;
    clearScene();

    var seq = ++_loadSceneSeq;
    var recordId = record.modelId;
    mini.mask({ el: 'canvasPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.loadingSceneConfig });

    $.ajax({
        url: context + '/sceneModelController/getSceneModelConfig',
        type: 'POST',
        data: { id: recordId },
        dataType: 'json',
        success: function(result) {
            if (seq !== _loadSceneSeq || _destroyed) return;
            mini.unmask('canvasPanel');
            if (result.success) {
                var cfg = result.config;
                if (typeof cfg === 'string') { try { cfg = JSON.parse(cfg); } catch(e) { cfg = null; } }
                if (!cfg) cfg = { imageUrl: '', shapes: [] };
                renderSceneConfig(cfg);
            } else {
                mini.alert(result.message || '加载失败');
            }
        },
        error: function() {
            if (seq !== _loadSceneSeq || _destroyed) return;
            mini.unmask('canvasPanel');
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

function clearScene() {
    bindingMap.clear();
    labelStyleMap.clear();
    userColorMap.clear();
    userLineWidthMap.clear();
    lastSelectedUuid = null;
    imageUrl = '';
    
    if (cs) cs.setData([]);
    
    //清空背景图，还原为默认的空白 SVG
    setCanvasImage(getBlankImage());

    var layer = document.getElementById('sceneLabelLayer');
    if (layer) layer.innerHTML = '';
    refreshBindingTree();
    resetStylePanel();
}

function renderSceneConfig(cfg) {
    if (!cfg) cfg = { imageUrl: '', shapes: [] };
    
    if (cfg.defaultLabelStyle) Object.assign(defaultLabelStyle, cfg.defaultLabelStyle);
    if (cfg.globalStyle) Object.assign(globalStyle, cfg.globalStyle);

    setCanvasImage(cfg.imageUrl || getBlankImage());

    var shapes = cfg.shapes || [];
    shapes.forEach(function(s) {
        userColorMap.set(s.uuid, s.strokeStyle || '#00FF00');
        userLineWidthMap.set(s.uuid, (s.lineWidth != null ? s.lineWidth : 1));
        if (s.deviceId && s.fieldName) { bindingMap.set(s.uuid, s); }
        if (s.labelStyle) labelStyleMap.set(s.uuid, s.labelStyle);
    });

    var pureShapes = [];
    shapes.forEach(function(s) {
        // ★ 核心修复：增加极其严格的坐标校验，防止脏数据导致画布崩溃
        if (!s || !s.uuid) return;
        if (s.type == null) return;
        if (!s.coor) return;

        var t = parseInt(s.type, 10);
        if (isNaN(t)) return;

        var coor = s.coor;
        if (t === 1) {
            if (!Array.isArray(coor) || coor.length < 2) return;
            var a = coor[0], b = coor[1];
            if (!Array.isArray(a) || !Array.isArray(b)) return;
            if (a.length < 2 || b.length < 2) return;
            if (isNaN(a[0]) || isNaN(a[1]) || isNaN(b[0]) || isNaN(b[1])) return;
        } else if (t === 2 || t === 4) {
            if (!Array.isArray(coor) || coor.length < 2) return;
            for (var i = 0; i < coor.length; i++) {
                var p = coor[i];
                if (!Array.isArray(p) || p.length < 2) return;
                if (isNaN(p[0]) || isNaN(p[1])) return;
            }
        } else if (t === 3 || t === 5) {
            if (!Array.isArray(coor) || coor.length < 2) return;
            if (isNaN(coor[0]) || isNaN(coor[1])) return;
        } else return;

        var cr = (s.ctrlRadius != null) ? parseInt(s.ctrlRadius, 10) : 3;
        var sLw = (s.lineWidth != null) ? parseInt(s.lineWidth, 10) : 1; 
        var sStroke = s.strokeStyle || '#00FF00';
        var canvasStroke = (sLw <= 0) ? TRANSPARENT_STROKE : sStroke;
        var canvasLw = (sLw <= 0) ? 1 : sLw;
        
        pureShapes.push({
            uuid: s.uuid, type: t, coor: coor,
            strokeStyle: canvasStroke,
            fillStyle: s.fillStyle || 'rgba(0,0,255,0.25)',
            lineWidth: canvasLw,
            ctrlRadius: cr,
            _userStrokeStyle: sStroke,
            _userLineWidth: sLw
        });
    });

    setTimeout(function() {
        if (cs) cs.setData(pureShapes);
        refreshBindingTree(true);
        scheduleRenderLabels();
        
        handleCanvasResize();
        
        setTimeout(function() {
            handleCanvasResize();
        }, 150);
    }, 100);
}

function setCanvasImage(url) {
    if (!cs || _isSettingImage) return;
    if (!url) url = getBlankImage();
    
    imageUrl = (url === getBlankImage()) ? '' : url;
    if (url === cs._canvasCurrentUrl) return;

    _isSettingImage = true;
    var loadUrl = url;
    if (loadUrl.indexOf('blob:') !== 0 && loadUrl.indexOf('data:') !== 0) {
        loadUrl += (loadUrl.indexOf('?') > -1 ? '&' : '?') + '_t=' + new Date().getTime();
    }

    var probe = new Image();
    probe.onload = function() {
        try {
            cs.setImage(loadUrl);
            cs._canvasCurrentUrl = url;
            _nudgeCanvasMouseCenter();
            updateOriginIfNeeded(true);
            scheduleRenderLabels();
        } catch (e) {
            console.error('setImage error', e);
        } finally {
            _isSettingImage = false;
        }
    };
    probe.onerror = function() {
        console.warn('背景图加载失败，降级处理', url);
        _isSettingImage = false;
        if (url !== getBlankImage()) {
            cs._canvasCurrentUrl = null;
            setCanvasImage(getBlankImage());
        }
    };
    probe.src = loadUrl;
}

// ================================================================
// 8. 数据源绑定弹窗
// ================================================================
function openDataSourceWindow(uuid, isEdit) {
    if (!uuid) return;
    var existing = bindingMap.get(uuid) || {};
    var orgId = '';
    if (currentRecord) orgId = currentRecord.orgid || currentRecord.orgId || '';

    mini.open({
        title: _loginUserLanguageResource.configureDataSource,
        url: context + '/miniui-app/modules/sceneModel/DataSourceWindow.jsp',
        width: 900,
        height: 560,
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            iframe.contentWindow.setData({
                targetOrgId: orgId,
                currentBinding: {
                    deviceId: existing.deviceId || '',
                    fieldName: existing.fieldName || '',
                    unit: existing.unit || ''
                },
                onConfirm: function(binding) {
                    var old = bindingMap.get(uuid) || {};
                    bindingMap.set(uuid, {
                        deviceId: binding.deviceId, deviceName: binding.deviceName,
                        fieldName: binding.fieldName, fieldLabel: binding.fieldLabel,
                        unit: binding.unit || '', dataSource: binding.dataSource || '',
                        currentValue: old.currentValue != null ? old.currentValue : '--'
                    });
                    if (!labelStyleMap.has(uuid)) {
                        labelStyleMap.set(uuid, Object.assign({}, defaultLabelStyle));
                    }
                    refreshBindingTree();
                    scheduleRenderLabels();
                    
                    lastSelectedUuid = uuid;
                    
                    if (!isEdit && cs) { 
                        cs.createType = _userCreateType; 
                        updateToolbarButtons(_userCreateType);
                    }
                }
            });
        },
        onDestroy: function(action) {
            setTimeout(function() {
                var canvasEl = getCanvasEl();
                if (canvasEl) {
                    try { canvasEl.focus(); } catch(e) {}
                }
            }, 50);
        }
    });
}

// ================================================================
// 9. 右侧标注树渲染与交互
// ================================================================
function refreshBindingTree(forceSort) {
    var tree = mini.get('bindingTree');
    if (!tree) return;

    var data = [];
    var devMap = {};
    var devOrder = [];

    bindingMap.forEach(function(b, uuid) {
        if (!b.deviceId) return;
        var devKey = String(b.deviceId);
        
        if (!devMap[devKey]) {
            devMap[devKey] = { 
                id: 'dev_' + devKey, 
                text: b.deviceName || devKey, 
                expanded: true,
                children: [] 
            };
            devOrder.push(devKey);
        }
        
        devMap[devKey].children.push({
            id: uuid, 
            uuid: uuid, 
            text: b.fieldLabel || b.fieldName || uuid, 
            leaf: true
        });
    });

    if (forceSort) {
        devOrder.sort(function(a, b) {
            var nameA = String(devMap[a].text).toLowerCase();
            var nameB = String(devMap[b].text).toLowerCase();
            return nameA < nameB ? -1 : (nameA > nameB ? 1 : 0);
        });
    }

    devOrder.forEach(function(devKey) { 
        data.push(devMap[devKey]); 
    });

    tree.loadData(data);
    
    if (lastSelectedUuid) selectTreeNodeByUuid(lastSelectedUuid, true);
}

// ★ 核心修复：增加 shouldScroll 参数，控制是否要滚动
function selectTreeNodeByUuid(uuid, shouldScroll) {
    var tree = mini.get('bindingTree');
    if (!tree) return;
    var node = tree.getNode(uuid);
    if (node) {
        tree.selectNode(node);
        
        if (shouldScroll) {
            setTimeout(function() {
                if (tree && node) {
                    try {
                        tree.scrollIntoView(node);
                    } catch (e) {
                        console.warn('树节点滚动失败', e);
                    }
                }
            }, 150);
        }
    }
}

function onBindingTreeSelect(e) {
    var node = e.node;
    if (!node) return;
    if (node.leaf) {
        selectShapeByUuid(node.uuid);
    } else {
        if (cs && cs.createType !== 0) {
            cs.createType = 0;
            _userCreateType = 0;
            updateToolbarButtons(0);
        }
        if (lastSelectedUuid) clearSelectionUI(true);
    }
}

// ================================================================
// 10. 保存画布
// ================================================================
function onSaveAnnotations() {
    if (!currentRecord) { mini.alert(_loginUserLanguageResource.selectSceneFirst); return; }
    if (!cs) return;
    if (!imageUrl) { mini.alert(_loginUserLanguageResource.selectSceneImgFirst); return; }

    var shapes = cs.dataset || [];
    var cfgShapes = [];

    shapes.forEach(function(s) {
        var binding = bindingMap.get(s.uuid) || {};
        var labelStyle = labelStyleMap.get(s.uuid) || defaultLabelStyle;

        var userColor = userColorMap.get(s.uuid)
                      || s._userStrokeStyle
                      || s.strokeStyle
                      || '#00FF00';
        if (userColor === TRANSPARENT_STROKE) userColor = '#00FF00';

        var userLw = userLineWidthMap.get(s.uuid);
        if (userLw == null) {
            userLw = (s._userLineWidth != null) ? s._userLineWidth
                   : (s.lineWidth != null ? s.lineWidth : 1);
        }

        var cr = s.ctrlRadius != null ? s.ctrlRadius : 3;

        cfgShapes.push({
            uuid: s.uuid, type: s.type, coor: s.coor,
            strokeStyle: userColor, fillStyle: s.fillStyle,
            lineWidth: userLw, ctrlRadius: cr,
            labelStyle: Object.assign({}, labelStyle),
            deviceId: binding.deviceId, deviceName: binding.deviceName,
            fieldName: binding.fieldName, fieldLabel: binding.fieldLabel,
            unit: binding.unit, dataSource: binding.dataSource,
            currentValue: binding.currentValue
        });
    });

    var cfg = {
        imageUrl: imageUrl,
        defaultLabelStyle: Object.assign({}, defaultLabelStyle),
        globalStyle: Object.assign({}, globalStyle),
        shapes: cfgShapes
    };

    mini.mask({ el: 'canvasPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.savingAnnotations });

    $.ajax({
        url: context + '/sceneModelController/saveSceneModel',
        type: 'POST',
        data: { id: currentRecord.modelId, config: JSON.stringify(cfg) },
        dataType: 'json',
        success: function(result) {
            mini.unmask('canvasPanel');
            if (result.success) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully);
            } else {
                mini.alert(_loginUserLanguageResource.saveFailed);
            }
        },
        error: function() {
            mini.unmask('canvasPanel');
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

// ================================================================
// 11. 其他工具方法与监听
// ================================================================
function extractUuid(info) {
    if (!info) return null;
    if (info.uuid) return info.uuid;
    if (info.index !== undefined && cs && cs.dataset && cs.dataset[info.index]) {
        return cs.dataset[info.index].uuid;
    }
    return null;
}

function parseFillStyle(fillStyle) {
    if (!fillStyle) return { hex: '#0000FF', alpha: 25 };
    var m = fillStyle.match(/rgba?\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*(?:,\s*([\d.]+))?\s*\)/i);
    if (m) {
        var hex = '#' + [m[1], m[2], m[3]].map(function(n) { return (+n).toString(16).padStart(2, '0'); }).join('');
        var alpha = m[4] != null ? Math.round(parseFloat(m[4]) * 100) : 100;
        return { hex: hex, alpha: alpha };
    }
    return { hex: '#0000FF', alpha: 25 };
}

function buildFillColor(hexColor, alphaPercent) {
    var hex = hexColor.replace('#', '');
    if (hex.length !== 6) return 'rgba(0,0,255,0.25)';
    var r = parseInt(hex.slice(0, 2), 16);
    var g = parseInt(hex.slice(2, 4), 16);
    var b = parseInt(hex.slice(4, 6), 16);
    var a = Math.max(0, Math.min(1, (alphaPercent || 0) / 100));
    return 'rgba(' + r + ',' + g + ',' + b + ',' + a.toFixed(3) + ')';
}

function hexToRgb(hex) {
    hex = hex.replace('#', '');
    if (hex.length === 3) hex = hex.split('').map(function(c) { return c + c; }).join('');
    var r = parseInt(hex.substring(0, 2), 16);
    var g = parseInt(hex.substring(2, 4), 16);
    var b = parseInt(hex.substring(4, 6), 16);
    return r + ',' + g + ',' + b;
}

function initSceneMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        if (message.action === 'refresh') {
            loadSceneGrid();
        }
    });
}

//================================================================
//★ 辅助：读写 config 数据
//================================================================
function readRecordConfig(record) {
 if (!record) return { imageUrl: '', shapes: [] };
 var raw = record.config;
 if (!raw) return { imageUrl: '', shapes: [] };
 if (typeof raw === 'object') return raw;
 try {
     var parsed = JSON.parse(raw);
     if (parsed && typeof parsed === 'object') return parsed;
 } catch (e) {}
 return { imageUrl: '', shapes: [] };
}

function writeRecordConfig(record, cfg) {
 if (!record) return;
 try {
     if (!cfg || typeof cfg !== 'object') cfg = { imageUrl: '', shapes: [] };
     record.config = JSON.stringify(cfg);
 } catch (e) {}
}

//================================================================
//8.1 选择服务器图片（打开独立 JSP）
//================================================================
function onSelectServerImage() {
 if (_destroyed) return;
 var targetRecord = currentRecord;

 mini.open({
     title: _loginUserLanguageResource.selectSceneImage || '选择场景图片',
     url: context + '/miniui-app/modules/sceneModel/SelectSceneImageWindow.jsp',
     width: 820,
     height: 580,
     modal: true,
     allowResize: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         // 传入当前使用的图片 URL，用于 JSP 里的选中回显和删除保护
         iframe.contentWindow.setData({
             currentImageUrl: imageUrl || ''
         });
         
         // 接收 JSP 里点击确定的回调
         iframe.contentWindow.onConfirmCallback = function (url) {
             if (_destroyed) return;
             
             // 1. 设置画布图片
             setCanvasImage(url);
             
             // 2. 更新记录的数据
             if (targetRecord) {
                 var cfg = readRecordConfig(targetRecord);
                 cfg.imageUrl = url;
                 cfg.imageSource = 'server';
                 writeRecordConfig(targetRecord, cfg);
             }
         };
     }
 });
}

//================================================================
//8.2 上传本地图片（主界面直接触发隐藏 input）
//================================================================
function onUploadLocalImage() {
 if (_destroyed) return;
 var targetRecord = currentRecord;

 var input = document.createElement('input');
 input.type = 'file';
 input.accept = 'image/*,.svg';
 input.onchange = function (e) {
     var file = e.target.files[0];
     if (!file) return;

     var formData = new FormData();
     formData.append('file', file);

     mini.mask({ el: 'canvasPanel', html: _loginUserLanguageResource.uploading + '...' });

     $.ajax({
         url: context + '/sceneModelController/uploadSceneImage',
         type: 'POST',
         data: formData,
         processData: false,
         contentType: false,
         success: function (resp) {
             mini.unmask('canvasPanel');
             if (_destroyed) return;
             if (!resp || !resp.success) {
                 mini.alert(resp.message || _loginUserLanguageResource.uploadFail);
                 return;
             }

             // 设置新图
             setCanvasImage(resp.imageUrl);

             // 同步更新 config
             if (targetRecord) {
                 var cfg = readRecordConfig(targetRecord);
                 cfg.imageUrl = resp.imageUrl;
                 cfg.imageSource = 'uploaded';
                 writeRecordConfig(targetRecord, cfg);
             }

             mini.alert(_loginUserLanguageResource.uploadSuccess);
         },
         error: function () {
             mini.unmask('canvasPanel');
             if (_destroyed) return;
             mini.alert(_loginUserLanguageResource.uploadFail);
         }
     });
 };
 input.click();
}

// ================================================================
// ★ 12. 键盘删除事件 (保护视图状态版)
// ================================================================
function bindKeyboardEvents() {
    if (_keydownHandler) return;
    _keydownHandler = function(e) {
        if (_destroyed) return;
        
        if (e.key === 'Delete' || e.key === 'Backspace') {
            var target = e.target;
            var tagName = target.tagName.toUpperCase();
            if (tagName === 'INPUT' || tagName === 'TEXTAREA' || target.isContentEditable) {
                return;
            }
            
            var uuidToDelete = null;
            if (lastSelectedUuid) {
                uuidToDelete = lastSelectedUuid;
            } else if (cs && cs.activeShape && cs.activeShape.uuid) {
                uuidToDelete = cs.activeShape.uuid;
            }
            
            if (uuidToDelete) {
                e.preventDefault();
                
                var oldCreateType = cs ? cs.createType : 0;
                var oldUserCreateType = _userCreateType;
                
                if (cs && cs.dataset) {
                    bindingMap.delete(uuidToDelete);
                    labelStyleMap.delete(uuidToDelete);
                    userColorMap.delete(uuidToDelete);
                    userLineWidthMap.delete(uuidToDelete);
                    
                    lastSelectedUuid = null;
                    try { cs.activeShape = null; } catch(err){}
                    resetStylePanel();
                    
                    // ★ 核心修复：记录当前画布的缩放和平移状态，防止 setData 重置视图
                    var savedScale = cs.scale;
                    var savedMouse = cs.mouse ? cs.mouse.slice() : [0, 0];
                    var savedPos = cs.position ? cs.position.slice() : [0, 0];

                    var newDataset = [];
                    for (var i = 0; i < cs.dataset.length; i++) {
                        if (cs.dataset[i].uuid !== uuidToDelete) {
                            newDataset.push(cs.dataset[i]);
                        }
                    }
                    
                    try {
                        cs.setData(newDataset); 
                        
                        // 恢复视图状态
                        cs.scale = savedScale;
                        cs.mouse = savedMouse;
                        cs.position = savedPos;
                        if (typeof cs.update === 'function') cs.update();
                    } catch(err) {
                        console.warn('setData 删除失败', err);
                    }
                    
                    // 恢复模式
                    if (oldCreateType !== 0) {
                        cs.createType = oldCreateType;
                        _userCreateType = oldUserCreateType;
                        updateToolbarButtons(oldUserCreateType);
                    } else {
                        cs.createType = 0;
                        _userCreateType = 0;
                        updateToolbarButtons(0);
                    }
                    
                    refreshBindingTree();
                    scheduleRenderLabels();
                    
                    if (typeof cs.draw === 'function') {
                        try { cs.draw(); } catch(err){}
                    }
                }
            }
        }
    };
    document.addEventListener('keydown', _keydownHandler);
}