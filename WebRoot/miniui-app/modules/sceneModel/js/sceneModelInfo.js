// ================================================================
// 场景模型模块 - sceneModelInfo.js
// ================================================================

var cs = null;          // canvas-select 实例
var _destroyed = false;

// 模块权限
var _sceneModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };

// ---------------------------------------------------------------
// 页面入口
// ---------------------------------------------------------------
function initSceneModelPage() {
    // 获取权限
    _sceneModuleRight = getRoleModuleRight(context + '/roleManagerController/getRoleModuleRight', 'SceneModel') || { viewFlag: 0, editFlag: 0, controlFlag: 0 };
    _sceneModuleRight.viewFlag = parseInt(_sceneModuleRight.viewFlag) || 0;
    _sceneModuleRight.editFlag = parseInt(_sceneModuleRight.editFlag) || 0;
    _sceneModuleRight.controlFlag = parseInt(_sceneModuleRight.controlFlag) || 0;

    // 强制解析所有内部组件
    forceParseInnerPanels();
    
    // 初始化配置
    initI18n();
    initSpectrum();
    initCanvasSelect();
    updateBtnStatus();
    
    // 监听主界面消息
    initSceneMessageListener();
    
    // 触发首次数据加载
    loadSceneGrid();
}

function forceParseInnerPanels() {
    var ids = ['sceneModelPanel', 'rightTopPanel', 'canvasPanel', 'stylePanel', 'annotationPanel', 'sceneModelGrid'];
    for (var i = 0; i < ids.length; i++) {
        var id = ids[i];
        if (!mini.get(id)) {
            var el = document.getElementById(id);
            if (el) {
                try { mini.parse(el); } catch (e) { console.warn('[SceneModel] parse ' + id + ' 失败:', e); }
            }
        }
    }
}

function getComp(id) { return mini.get(id); }

// ================================================================
// 1. 国际化
// ================================================================
function initI18n() {
    var p = getComp('sceneModelPanel');
    if (p) p.setTitle(_loginUserLanguageResource.sceneModel);

    p = getComp('stylePanel');
    if (p) p.setTitle(_loginUserLanguageResource.styleSettings);

    p = getComp('annotationPanel');
    if (p) p.setTitle(_loginUserLanguageResource.annotationList);
    
    var sceneModelGrid = mini.get('sceneModelGrid');
    if (sceneModelGrid) sceneModelGrid.setEmptyText(_loginUserLanguageResource.emptyMsg);
    
    var bindingTree = mini.get('bindingTree');
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
}

function setBtnText(id, text) {
    var b = getComp(id);
    if (b && text !== undefined) b.setText(text);
}

function setElText(id, text) {
    var el = document.getElementById(id);
    if (el && text !== undefined) el.innerText = text;
}

// ================================================================
// 2. 表格配置与数据加载
// ================================================================
function loadSceneGrid() {
    var grid = mini.get('sceneModelGrid');
    if (!grid) return;
    
    if (!grid.getUrl()) {
        grid.setUrl(context + '/sceneModelController/getSceneModelList');
    }
    
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
    var result = e.result || {};
    
    if (!grid._columnsCreated) {
        createSceneModelGridColumns(grid, result);
        grid._columnsCreated = true;
    }
    
    // 恢复选中状态
    var keepId = grid._keepSelectId;
    var keepName = grid._keepSelectName;
    grid._keepSelectId = null;
    grid._keepSelectName = null;
    
    var data = grid.getData() || [];
    
    if (data.length > 0) {
        var target = null;
        
        // 优先按 ID 匹配（如果后端返回了新ID）
        if (keepId) {
            for (var i = 0; i < data.length; i++) {
                if (String(data[i].modelId) === String(keepId)) {
                    target = data[i];
                    break;
                }
            }
        }
        
        // 如果 ID 没匹配到，再按添加时输入的名称匹配（兼容后端未返回 ID 的情况）
        if (!target && keepName) {
            for (var j = 0; j < data.length; j++) {
                if (data[j].name_zh_CN === keepName || 
                    data[j].name_en === keepName || 
                    data[j].name_ru === keepName) {
                    target = data[j];
                    break;
                }
            }
        }
        
        // 如果都没匹配到，或者本来就没有 keep 记录，默认选中第一行
        if (!target) {
            var selected = grid.getSelected();
            if (!selected) {
                target = data[0];
            } else {
                target = selected; // 保留原有选中
            }
        }
        
        if (target) {
            grid.select(target);
        }
        
    } else {
        // 无数据时清空画布
        if (cs) cs.setData([]);
        if (document.getElementById('sceneLabelLayer')) {
            document.getElementById('sceneLabelLayer').innerHTML = '';
        }
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

    var columns=[];
 // 复选框列
    if (editFlag) {
        columns.push({
            type: 'checkcolumn',
            width: 40,
            header: '',
            headerAlign: 'center',
            align: 'center'
        });
    }
    columns.push({ type: 'indexcolumn', width: 50, header: R.idx, headerAlign: 'center', align: 'center' });
    columns.push({ field: 'name_zh_CN', header: R.language_zh_CN, headerAlign: 'center', align: 'left', width: 150, visible: showChineseName, editor: textEditor });
    columns.push({ field: 'name_en', header: R.language_en, headerAlign: 'center', align: 'left', width: 150, visible: showEnglishName, editor: textEditor });
    columns.push({ field: 'name_ru', header: R.language_ru, headerAlign: 'center', align: 'left', width: 150, visible: showRussianName, editor: textEditor });
    columns.push({ field: 'sort', header: R.sequenceNumber, headerAlign: 'center', align: 'center', width: 80, editor: sortEditor });
    
    grid.setColumns(columns);
    grid.setAllowCellEdit(editFlag);
    grid.setAllowCellSelect(editFlag);
}

function onSceneGridCellBeginEdit(e) {
    if (_sceneModuleRight.editFlag != 1) {
        e.cancel = true;
    }
}

function onSceneGridSelectionChanged(e) {
    var grid = e.sender;
    var row = grid.getSelected();
    // 留给后续业务（画布加载等）
}

// ================================================================
// 3. 监听主界面消息
// ================================================================
function initSceneMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        
        if (message.action === 'refresh') {
            loadSceneGrid();
        }
    });
}

// ================================================================
// 4. 权限控制
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

// ================================================================
// 5. 画布工具栏按钮功能 (HTML onclick)
// ================================================================
function onCanvasSelect() {
    if (cs) cs.createType = 0;
}

function onCanvasRect() {
    if (_sceneModuleRight.editFlag != 1) return; 
    if (cs) cs.createType = 1;
}

function onCanvasPoint() {
    if (_sceneModuleRight.editFlag != 1) return; 
    if (cs) cs.createType = 3;
}

function onCanvasZoomIn() {
    if (cs) cs.setScale(true);
}

function onCanvasZoomOut() {
    if (cs) cs.setScale(false);
}

function onCanvasFitZoom() {
    if (cs) cs.fitZoom();
}

function onSaveAnnotations() {
    // 下一步实现
    mini.alert("标注保存功能待实现");
}

// ================================================================
// 6. 初始化 Spectrum 颜色选择器
// ================================================================
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
// 7. 初始化 CanvasSelect
// ================================================================
function initCanvasSelect() {
    var canvasEl = document.getElementById('sceneCanvas');
    if (!canvasEl) return;
    
    var BLANK_IMAGE = context + '/sceneModelController/getSceneImage?name=__blank__.svg&_v=2';

    if (typeof CanvasSelect === 'undefined') return;

    try {
        cs = new CanvasSelect(canvasEl, BLANK_IMAGE);
        cs.ctrlRadius = 3;
        cs.createType = 0;
        cs.scrollZoom = true;
        cs.hideLabel = true;

        if (_sceneModuleRight.editFlag != 1) {
            try { cs.readonly = true; } catch (e) {}
            try { cs.locked = true; } catch (e) {}
        }

        cs.on('load', function () {
            if (typeof cs.fitZoom === 'function') cs.fitZoom();
        });
    } catch (e) {
        console.error('[SceneModel] CanvasSelect 初始化失败:', e);
    }
}

// ================================================================
// 8. 左侧工具栏按钮功能 (HTML onclick)
// ================================================================

function onSceneRefresh() {
    loadSceneGrid();
}

function onSceneAdd() {
    if (_sceneModuleRight.editFlag != 1) return;
    
 // 组织信息
    var orgId = window.parent && window.parent.getSelectOrgNodeId ? window.parent.getSelectOrgNodeId() : '';
    var orgName = window.parent && window.parent.getSelectOrgNodePath ? window.parent.getSelectOrgNodePath() : '';

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
            
            // ★ 监听子窗口的成功回调
            iframe.contentWindow.parent._parentRefreshSceneList = function(success, newId, addedName) {
                if (success) {
                    var grid = mini.get('sceneModelGrid');
                    if (grid) {
                        // 把新ID或新名称存起来，供 onSceneGridLoad 识别
                        if (newId) grid._keepSelectId = newId;
                        if (addedName) grid._keepSelectName = addedName;
                        
                        loadSceneGrid(); // 重新加载表格
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
    if (rows.length === 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    var ids = [];
    var names = [];
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].modelId);
        var nameStr = rows[i].name_zh_CN || rows[i].name_en || rows[i].name_ru || '';
        names.push(nameStr);
    }

    var deleteInfo;
    if (ids.length === 1) {
        deleteInfo = _loginUserLanguageResource.name + '：<font color="red">' + names[0] + '</font><br/>' + _loginUserLanguageResource.confirmDelete;
    } else {
        deleteInfo = _loginUserLanguageResource.sparseRecordCount + '：<font color="red">' + ids.length + '</font><br/>' + _loginUserLanguageResource.confirmDelete;
    }

    mini.confirm(deleteInfo, _loginUserLanguageResource.tip, function (action) {
        if (action !== 'ok') return;

        var maskEl = 'sceneModelPanel';
        mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/sceneModelController/deleteSceneModel',
            type: 'POST',
            data: { ids: ids.join(',') },
            dataType: 'json',
            success: function (result) {
                mini.unmask(maskEl);
                if (result && result.success === true) {
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully, _loginUserLanguageResource.tip);
                    onSceneRefresh();
                } else {
                    mini.alert('<font color="red">' + _loginUserLanguageResource.deleteFailed + '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(maskEl);
                mini.alert('【<font color="red">' + _loginUserLanguageResource.exceptionThrow + '</font>】：' + _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
            }
        });
    });
}

function onSceneSave() {
    if (_sceneModuleRight.editFlag != 1) return;

    var grid = mini.get('sceneModelGrid');
    if (!grid) return;

    grid.commitEdit(); // 提交正在编辑的单元格

    var modifiedRecords = grid.getChanges('modified', false);
    if (!modifiedRecords || modifiedRecords.length === 0) {
        mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
        return;
    }

    var modifiedModels = [];
    for (var i = 0; i < modifiedRecords.length; i++) {
        var rec = modifiedRecords[i];
        modifiedModels.push({
            id: rec.modelId,
            name_zh_CN: rec.name_zh_CN,
            name_en: rec.name_en,
            name_ru: rec.name_ru,
            sort: (rec.sort != undefined && rec.sort !== '' && !isNaN(rec.sort)) ? rec.sort : null
        });
    }

    var maskEl = 'sceneModelPanel';
    mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

    $.ajax({
        url: context + '/sceneModelController/saveSceneModelInfo',
        type: 'POST',
        data: { data: JSON.stringify(modifiedModels) },
        dataType: 'json',
        success: function (result) {
            mini.unmask(maskEl);
            if (result && result.success === true) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                grid.accept();
                onSceneRefresh();
            } else {
                mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
            }
        },
        error: function () {
            mini.unmask(maskEl);
            mini.alert('【<font color="red">' + _loginUserLanguageResource.exceptionThrow + '</font>】：' + _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
        }
    });
}