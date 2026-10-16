var loginUserSceneModelRight = getRoleModuleRight('SceneModel');

Ext.define("AP.view.sceneModel.SceneModelInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.sceneModelInfoView',
    layout: 'border',
    border: false,

    cs: null,
    labelLayer: null,
    currentRecord: null,
    imageUrl: '',
    cachedOrigin: { x: 0, y: 0, scale: 1, ready: false },
    bindingMap: null,
    labelStyleMap: null,
    userColorMap: null,
    userLineWidthMap: null,
    lastSelectedUuid: null,
    _suppressStyleChange: false,
    _clearingSelection: false,

    // 记录用户点击 tbar 时选择的创建类型（0=选择 1=矩形 3=点）
    _userCreateType: 0,

    _resizing: false,
    _resizeTimer: null,
    _originTimer: null,
    _visibilityHandler: null,

    defaultLabelStyle: {
        font: '12px', bgColor: '#4A6CF7', bgOpacity: 100,
        textColor: '#FFFFFF', hide: false, hideDeviceName: true, up: true
    },
    globalStyle: {
        strokeStyle: '#00FF00', fillColor: '#0000FF', fillOpacity: 25,
        lineWidth: 1, ctrlRadius: 3
    },
    TRANSPARENT_STROKE: 'rgba(0,0,0,0.01)',

    _canvasCurrentUrl: null,
    _lastDataKey: null,
    _settingImage: false,
    _rebuilding: false,

    BLANK_IMAGE: context + '/sceneModelController/getSceneImage?name=__blank__.svg&_v=2',

    // ========================================================
    // ★ 工具：统一的安全调用包装
    // ========================================================
    safe: function(fn, label) {
        try { return fn(); }
        catch (e) {
            if (label) console.warn('[SceneModelPanel] ' + label + ':', e);
        }
    },

    // ========================================================
    // ★ 工具：统一获取真实的 canvas DOM
    // ========================================================
    getCanvasEl: function() {
        var me = this;
        if (me.cs && me.cs.canvas && me.cs.canvas.tagName === 'CANVAS') {
            return me.cs.canvas;
        }
        var canvasCmp = me.down('#canvas');
        if (canvasCmp && canvasCmp.el && canvasCmp.el.dom) {
            return canvasCmp.el.dom;
        }
        return null;
    },

    // ========================================================
    // ★ 工具：origin 轮询定时器的启动/停止
    // ========================================================
    startOriginTimer: function() {
        var me = this;
        if (me._originTimer) return;
        if (document.hidden) return;
        me._originTimer = setInterval(function() {
            if (me.updateOriginIfNeeded()) me.scheduleRenderLabels();
        }, 100);
    },

    stopOriginTimer: function() {
        var me = this;
        if (me._originTimer) {
            clearInterval(me._originTimer);
            me._originTimer = null;
        }
    },

    // ========================================================
    // ★ 关键：主动派发一次"鼠标移到画布中心"的合成 mousemove
    // --------------------------------------------------------
    // 首次打开模块时，用户还没有移动鼠标，canvas-select 内部的
    // cs.mouse / cs.position 尚未初始化，导致 updateOriginIfNeeded
    // 无法计算出正确的 origin，标签位置错乱。
    //
    // 这里主动派发一次合成事件，等价于"用户把鼠标移到画布上"，
    // 让库内部完成 mouse/position 初始化。之后 origin 就能算对。
    // ========================================================
    _nudgeCanvasMouseCenter: function() {
        var me = this;
        var canvasEl = me.getCanvasEl();
        if (!canvasEl) return;

        var rect = canvasEl.getBoundingClientRect();
        if (rect.width < 10 || rect.height < 10) return;

        // offsetX/offsetY 是相对 canvas 元素的 CSS 像素坐标
        var offX = rect.width  / 2;
        var offY = rect.height / 2;
        var clientX = rect.left + offX;
        var clientY = rect.top  + offY;

        var ev;
        try {
            ev = new MouseEvent('mousemove', {
                bubbles: true, cancelable: true, view: window,
                clientX: clientX, clientY: clientY,
                screenX: clientX, screenY: clientY,
                button: 0
            });
        } catch (e) { return; }

        // 合成事件的 offsetX/offsetY 是只读的，必须显式定义
        try { Object.defineProperty(ev, 'offsetX', { get: function() { return offX; } }); } catch (e) {}
        try { Object.defineProperty(ev, 'offsetY', { get: function() { return offY; } }); } catch (e) {}

        try { canvasEl.dispatchEvent(ev); } catch (e) {}
    },

    // ========================================================
    // 布局
    // ========================================================
    initComponent: function() {
        var me = this;
        me.bindingMap = new Map();
        me.labelStyleMap = new Map();
        me.userColorMap = new Map();
        me.userLineWidthMap = new Map();

        var bindingTreeStore = Ext.create('Ext.data.TreeStore', {
            root: { expanded: true, children: [] }
        });

        Ext.apply(me, {
            tbar: [],
            items: [
                {
                    region: 'west',
                    width: '25%',
                    layout: 'fit',
                    title: '场景列表',
                    header: false,
                    split: true,
                    collapsible: true,
                    id: 'SceneModelInfoPanel_Id',
                    tbar: [{
                        xtype: 'button',
                        text: loginUserLanguageResource.refresh,
                        iconCls: 'note-refresh',
                        handler: function() {
                            var gridPanel = Ext.getCmp("SceneModelGridView_Id");
                            if (isNotVal(gridPanel)) gridPanel.getStore().load();
                        }
                    }, '->', {
                        xtype: 'button', text: loginUserLanguageResource.add, iconCls: 'add',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onCreateScene(); }
                    }, "-", {
                        xtype: 'button', text: loginUserLanguageResource.deleteData, iconCls: 'delete',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onDeleteScene(); }
                    }, "-", {
                        xtype: 'button', text: loginUserLanguageResource.save, iconCls: 'save',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onSaveScene(); }
                    }, "-", {
                        xtype: 'button', text: loginUserLanguageResource.exportData, iconCls: 'export',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onExportScenes(); }
                    }, "-", {
                        xtype: 'button', text: loginUserLanguageResource.importData, iconCls: 'import',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        handler: function() { me.onImportScenes(); }
                    },{
                        id: 'SceneModelSelectId_Id', xtype: 'textfield', value: 0, hidden: true
                    },{
                        id: 'addSceneModelSelectName_Id', xtype: 'textfield', value: '', hidden: true
                    }]
                },
                {
                    region: 'center',
                    id: 'SceneModelConfigPanel_Id',
                    layout: 'border',
                    border: false,
                    tbar: [
                        { xtype: 'button', text: '✋ 选择', itemId: 'btnSelect',
                          enableToggle: true, pressed: true, toggleGroup: 'createType',
                          handler: function(b) {
                              if (b.pressed && me.cs) {
                                  me.cs.createType = 0;
                                  me._userCreateType = 0;
                              }
                          } },
                        { xtype: 'button', text: '▭ 矩形', itemId: 'btnRect',
                          enableToggle: true, toggleGroup: 'createType',
                          handler: function(b) {
                              if (b.pressed && me.cs) {
                                  me.cs.createType = 1;
                                  me._userCreateType = 1;
                              }
                          } },
                        { xtype: 'button', text: '• 点', itemId: 'btnPoint',
                          enableToggle: true, toggleGroup: 'createType', tooltip: '点击画布添加点标注',
                          handler: function(b) {
                              if (b.pressed && me.cs) {
                                  me.cs.createType = 3;
                                  me._userCreateType = 3;
                              }
                          } },
                        '-',
                        { xtype: 'button', text: '🔍+', tooltip: '放大',
                          handler: function() { if (me.cs) { me.cs.setScale(true); me.afterZoom(); } } },
                        { xtype: 'button', text: '🔍−', tooltip: '缩小',
                          handler: function() { if (me.cs) { me.cs.setScale(false); me.afterZoom(); } } },
                        { xtype: 'button', text: '适配',
                          handler: function() { if (me.cs) { me.cs.fitZoom(); me.afterZoom(); } } },
                        '-',
                        { xtype: 'button', text: '🖼️ 选择服务器图片',
                          handler: function() { me.onSelectServerImage(); } },
                        { xtype: 'button', text: '📤 上传本地图片',
                          handler: function() { me.onUploadLocalImage(); } },
                        '->',
                        { xtype: 'button', text: '💾 保存标注',
                          handler: function() { me.onSaveAnnotations(); } }
                    ],
                    items: [
                        {
                            xtype: 'container',
                            region: 'center',
                            itemId: 'canvasWrap',
                            layout: 'absolute',
                            style: {
                                position: 'relative', background: '#f8f9fa',
                                border: '2px dashed #dee2e6', borderRadius: '8px',
                                overflow: 'hidden', margin: '12px'
                            },
                            items: [{
                                xtype: 'component', itemId: 'canvas',
                                autoEl: { tag: 'canvas', cls: 'scene-annotation-canvas' },
                                style: { display: 'block', width: '100%', height: '100%' }
                            }, {
                                xtype: 'component', itemId: 'labelLayer',
                                autoEl: { tag: 'div', cls: 'scene-label-layer' },
                                style: {
                                    position: 'absolute', top: 0, left: 0, right: 0, bottom: 0,
                                    pointerEvents: 'none', overflow: 'hidden', zIndex: 10
                                }
                            }]
                        },
                        {
                            xtype: 'panel',
                            region: 'east',
                            width: 320,
                            split: true,
                            collapsible: true,
                            title: '样式与标注',
                            layout: 'border',
                            border: false,
                            items: [
                                {
                                    region: 'north',
                                    height: '50%',
                                    split: true,
                                    collapsible: true,
                                    title: '样式设置',
                                    itemId: 'styleFormPanel',
                                    autoScroll: true,
                                    bodyPadding: 12,
                                    border: false,
                                    items: [{
                                        xtype: 'fieldset',
                                        title: '图形样式',
                                        defaults: { anchor: '100%', labelWidth: 90, margin: '6 0' },
                                        items: [
                                            { xtype: 'colorfield', fieldLabel: '边框颜色', itemId: 'strokeStyle',
                                              listeners: { collapse: function (field) { me.applyColorFieldVisual(field, 1); me.onGraphicStyleChange(); } } },
                                            { xtype: 'numberfield', fieldLabel: '边框宽度', itemId: 'lineWidth',
                                              value: 1, minValue: 0, maxValue: 10, allowDecimals: false,
                                              listeners: { change: function() { me.onGraphicStyleChange(); } } },
                                            { xtype: 'colorfield', fieldLabel: '填充颜色', itemId: 'fillStyle',
                                              listeners: { collapse: function (field) { me.applyColorFieldVisual(field, null); me.onGraphicStyleChange(); } } },
                                            { xtype: 'numberfield', fieldLabel: '控制点大小', itemId: 'ctrlRadius',
                                              value: 3, minValue: 2, maxValue: 10, allowDecimals: false,
                                              tooltip: '同时控制点标注的半径大小',
                                              listeners: { change: function() { me.onGraphicStyleChange(); } } }
                                        ]
                                    }, {
                                        xtype: 'fieldset',
                                        title: '标签样式',
                                        defaults: { anchor: '100%', labelWidth: 90, margin: '6 0' },
                                        items: [
                                            { xtype: 'combo', fieldLabel: '字体', itemId: 'labelFont',
                                              store: ['11px', '12px', '13px', '14px'], value: '12px', editable: false, matchFieldWidth: true,
                                              listeners: { change: function() { me.onLabelStyleChange(); } } },
                                            { xtype: 'colorfield', fieldLabel: '标签背景', itemId: 'labelBg',
                                              listeners: { collapse: function (field) { me.applyColorFieldVisual(field, null); me.onLabelStyleChange(); } } },
                                            { xtype: 'colorfield', fieldLabel: '文字颜色', itemId: 'labelText',
                                              listeners: { collapse: function (field) { me.applyColorFieldVisual(field, 1); me.onLabelStyleChange(); } } },
                                            { xtype: 'checkbox', fieldLabel: '标签在上方', itemId: 'labelUp', value: true,
                                              listeners: { change: function() { me.onLabelStyleChange(); } } },
                                            { xtype: 'checkbox', fieldLabel: '隐藏设备名称', itemId: 'hideDeviceName', value: true,
                                              listeners: { change: function() { me.onLabelStyleChange(); } } },
                                            { xtype: 'checkbox', fieldLabel: '隐藏标签', itemId: 'hideLabel',
                                              listeners: { change: function() { me.onLabelStyleChange(); } } }
                                        ]
                                    }]
                                },
                                {
                                    region: 'center',
                                    title: '标注列表',
                                    layout: 'fit',
                                    border: false,
                                    items: [{
                                        xtype: 'treepanel',
                                        itemId: 'bindingTree',
                                        id: 'SceneBindingTree_Id',
                                        rootVisible: false,
                                        border: false,
                                        useArrows: true,
                                        lines: false,
                                        store: bindingTreeStore,
                                        emptyText: '<div style="padding:30px;color:#aaa;text-align:center;">暂无标注</div>',
                                        listeners: {
                                            itemclick: function(view, record, item, index, e, eOpts) {
                                                me.onBindingTreeItemClick(view, record);
                                            }
                                        }
                                    }]
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);

        var centerPanel = Ext.getCmp('SceneModelConfigPanel_Id');
        centerPanel.loadScene = function(r) { me.loadScene(r); };
        centerPanel.resetPanel = function() { me.resetPanel(); };
        centerPanel.getSceneConfig = function() { return me.getConfig(); };

        me.on('afterrender', function() {
            Ext.defer(function() {
                me.initCanvasSelect();
                Ext.defer(function() { me.resetStylePanel(); }, 50, me);

                Ext.defer(function() { me.fixStylePanelLayout(); }, 80, me);
                Ext.defer(function() { me.fixStylePanelLayout(); }, 350, me);

                me._bindResizeSources();
            }, 100, me);
        });

        me.on('boxready', function() {
            Ext.defer(function() { me.fixStylePanelLayout(); }, 60, me);
        });

        me.mon(me, 'resize', me._scheduleResize, me);
    },

    // ========================================================
    // ★ 把画布切回"选择"模式
    // ========================================================
    resetToSelectMode: function() {
        var me = this;
        me.safe(function() {
            me._userCreateType = 0;
            if (me.cs) me.cs.createType = 0;

            var btnSelect = me.down('#btnSelect');
            var btnRect   = me.down('#btnRect');
            var btnPoint  = me.down('#btnPoint');

            if (btnSelect) {
                try { btnSelect.toggle(true, true); } catch (e) { try { btnSelect.setPressed(true); } catch (e2) {} }
            }
            if (btnRect) {
                try { btnRect.toggle(false, true); } catch (e) { try { btnRect.setPressed(false); } catch (e2) {} }
            }
            if (btnPoint) {
                try { btnPoint.toggle(false, true); } catch (e) { try { btnPoint.setPressed(false); } catch (e2) {} }
            }
        }, 'resetToSelectMode');
    },

    // ========================================================
    // ★ 绑定所有可能影响画布可视区域的 resize 触发源
    // ========================================================
    _bindResizeSources: function() {
        var me = this;

        Ext.EventManager.onWindowResize(me._scheduleResize, me);

        var cfgPanel = me.down('#SceneModelConfigPanel_Id');
        if (cfgPanel) {
            me.mon(cfgPanel, 'resize', me._scheduleResize, me);
        }

        var east = me.down('panel[region=east]');
        if (east) {
            me.mon(east, 'resize', me._scheduleResize, me);
            me.mon(east, 'collapse', function() {
                Ext.defer(function() { me.handleResize(); }, 120, me);
                Ext.defer(function() { me.handleResize(); }, 380, me);
            });
            me.mon(east, 'expand', function() {
                Ext.defer(function() { me.handleResize(); }, 120, me);
                Ext.defer(function() { me.handleResize(); }, 380, me);
            });
        }

        var west = me.down('panel[region=west]');
        if (west) {
            me.mon(west, 'resize', me._scheduleResize, me);
        }
    },

    _scheduleResize: function() {
        var me = this;
        if (me._resizeTimer) clearTimeout(me._resizeTimer);
        me._resizeTimer = setTimeout(function() {
            me.safe(function() { me.handleResize(); }, 'handleResize');
        }, 120);
    },

    // ========================================================
    // 修正右侧样式面板的首次布局
    // ========================================================
    fixStylePanelLayout: function() {
        var me = this;
        me.safe(function() {
            var east = me.down('panel[region=east]');
            if (!east) return;

            try { east.updateLayout(); } catch (e) {}

            var north = east.down('panel[region=north]');
            if (north) {
                try { north.updateLayout(); } catch (e) {}

                var w = north.getWidth();
                if (w && w > 10) {
                    try { north.setWidth(w - 1); north.setWidth(w); } catch (e) {}
                }

                try {
                    north.query('fieldset').forEach(function(fs) {
                        try {
                            var fw = fs.getWidth();
                            if (fw && fw > 10) {
                                fs.setWidth(fw - 1);
                                fs.setWidth(fw);
                            }
                        } catch (e) {}
                    });
                } catch (e) {}
            }

            var center = east.down('panel[region=center]');
            if (center) {
                try { center.updateLayout(); } catch (e) {}
            }
        }, 'fixStylePanelLayout');
    },

    // ========================================================
    // 尺寸变化
    // ========================================================
    handleResize: function() {
        var me = this;
        if (!me.cs) return;
        if (me._resizing) return;
        me._resizing = true;

        try {
            var dom = me.getCanvasEl();
            if (!dom) return;

            var rect = dom.getBoundingClientRect();
            var cssW = Math.round(rect.width);
            var cssH = Math.round(rect.height);
            if (cssW < 20 || cssH < 20) return;

            if (dom.tagName === 'CANVAS') {
                if (dom.width  !== cssW) dom.width  = cssW;
                if (dom.height !== cssH) dom.height = cssH;
            }

            if (typeof me.cs.resize === 'function') me.cs.resize();
            if (typeof me.cs.fitZoom === 'function') me.cs.fitZoom();

            // resize 后同样给一次 mouse 初始化，防止 origin 依赖未初始化字段
            me._nudgeCanvasMouseCenter();

            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        } catch (e) {
            console.warn('[SceneModelPanel] handleResize 失败:', e);
        } finally {
            me._resizing = false;
        }
    },

    // ========================================================
    // 统一取消选中
    // ========================================================
    clearSelectionUI: function() {
        var me = this;
        if (me._clearingSelection) return;
        me._clearingSelection = true;
        try {
            me.lastSelectedUuid = null;

            var tree = me.down('#bindingTree');
            if (tree) {
                try { tree.getSelectionModel().deselectAll(); } catch (e) {}
            }

            try { me.resetStylePanel(); } catch (e) {}

            if (me.cs) {
                try { me.cs.activeShape = null; } catch (e) {}
                try { if ('selected' in me.cs) me.cs.selected = null; } catch (e) {}
                try { if ('selectedShape' in me.cs) me.cs.selectedShape = null; } catch (e) {}
                try { if ('selectedIndex' in me.cs) me.cs.selectedIndex = -1; } catch (e) {}
                try { if ('activeIndex' in me.cs) me.cs.activeIndex = -1; } catch (e) {}
                try { if ('hoverShape' in me.cs) me.cs.hoverShape = null; } catch (e) {}

                try { me.dispatchClickOnBlank(); } catch (e) {}

                try {
                    if (typeof me.cs.draw === 'function')        me.cs.draw();
                    else if (typeof me.cs.update === 'function') me.cs.update();
                } catch (e) {}

                var csRef = me.cs;
                setTimeout(function() {
                    if (!me.cs || me.cs !== csRef) return;
                    try {
                        try { me.cs.activeShape = null; } catch (e) {}
                        try { if ('selected' in me.cs) me.cs.selected = null; } catch (e) {}
                        try { if ('selectedShape' in me.cs) me.cs.selectedShape = null; } catch (e) {}
                        if (typeof me.cs.draw === 'function')        me.cs.draw();
                        else if (typeof me.cs.update === 'function') me.cs.update();
                    } catch (e) {}
                }, 30);
            }

            me.scheduleRenderLabels();
        } finally {
            me._clearingSelection = false;
        }
    },

    dispatchClickOnBlank: function() {
        var me = this;
        var canvasEl = me.getCanvasEl();
        if (!canvasEl) return;

        var rect = canvasEl.getBoundingClientRect();
        var clientX = rect.left + 1;
        var clientY = rect.top + 1;

        var baseProps = {
            bubbles: true, cancelable: true, composed: true, view: window,
            clientX: clientX, clientY: clientY,
            screenX: clientX, screenY: clientY, button: 0
        };
        var pointerProps = Object.assign({}, baseProps, {
            pointerId: 1, pointerType: 'mouse', isPrimary: true,
            width: 1, height: 1, pressure: 0.5
        });

        try {
            if (typeof PointerEvent !== 'undefined') {
                canvasEl.dispatchEvent(new PointerEvent('pointerover',  Object.assign({}, pointerProps, { buttons: 0 })));
                canvasEl.dispatchEvent(new PointerEvent('pointerenter', Object.assign({}, pointerProps, { buttons: 0 })));
                canvasEl.dispatchEvent(new PointerEvent('pointermove',  Object.assign({}, pointerProps, { buttons: 0 })));
                canvasEl.dispatchEvent(new PointerEvent('pointerdown',  Object.assign({}, pointerProps, { buttons: 1 })));
                canvasEl.dispatchEvent(new PointerEvent('pointerup',    Object.assign({}, pointerProps, { buttons: 0 })));
            }
            canvasEl.dispatchEvent(new MouseEvent('mouseover', Object.assign({}, baseProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new MouseEvent('mousemove', Object.assign({}, baseProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new MouseEvent('mousedown', Object.assign({}, baseProps, { buttons: 1 })));
            canvasEl.dispatchEvent(new MouseEvent('mouseup',   Object.assign({}, baseProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new MouseEvent('click',     Object.assign({}, baseProps, { buttons: 0 })));
        } catch (e) {
            console.warn('[SceneModelPanel] 派发空白点击失败', e);
        }
    },

    // ========================================================
    // 树形列表：刷新
    // ========================================================
    refreshBindingTree: function() {
        var me = this;
        var tree = me.down('#bindingTree');
        if (!tree) return;

        var store = tree.getStore();
        if (!store) return;
        var root = store.getRootNode ? store.getRootNode() : store.getRoot();
        if (!root) return;

        root.removeAll();

        var deviceMap = {};
        var deviceOrder = [];

        me.bindingMap.forEach(function(binding, uuid) {
            if (!binding || !binding.deviceId) return;

            var devKey = String(binding.deviceId);
            if (!deviceMap[devKey]) {
                deviceMap[devKey] = {
                    text: binding.deviceName || devKey,
                    deviceId: devKey,
                    expanded: true,
                    leaf: false,
                    children: []
                };
                deviceOrder.push(devKey);
            }

            deviceMap[devKey].children.push({
                text: (binding.fieldLabel || binding.fieldName || uuid),
                leaf: true,
                uuid: uuid,
                fieldName: binding.fieldName
            });
        });

        deviceOrder.forEach(function(devKey) {
            root.appendChild(deviceMap[devKey]);
        });

        if (me.lastSelectedUuid) {
            me.selectTreeNodeByUuid(me.lastSelectedUuid);
        }
    },

    selectTreeNodeByUuid: function(uuid) {
        var me = this;
        var tree = me.down('#bindingTree');
        if (!tree || !uuid) return;
        var store = tree.getStore();
        var root = store.getRootNode ? store.getRootNode() : store.getRoot();
        if (!root) return;

        var found = null;
        root.cascadeBy(function(node) {
            if (!found && node.isLeaf() && node.get('uuid') === uuid) {
                found = node;
            }
        });
        if (found) {
            try {
                tree.getSelectionModel().select(found);
                tree.ensureVisible(found);
            } catch (e) {}
        }
    },

    onBindingTreeItemClick: function(view, record) {
        var me = this;
        if (!record) return;

        if (record.isLeaf()) {
            var uuid = record.get('uuid');
            if (!uuid) return;
            me.selectShapeByUuid(uuid);
        } else {
            //me.clearSelectionUI();
        }
    },

    // ========================================================
    // 根据 uuid 选中形状
    // ========================================================
    selectShapeByUuid: function(uuid) {
        var me = this;
        if (!me.cs || !me.cs.dataset || !uuid) return;

        try {
            if (me.cs.createType !== 0) {
                me.cs.createType = 0;
                me._userCreateType = 0;
                var btn = me.down('#btnSelect');
                if (btn) {
                    try { btn.toggle(true, true); }
                    catch (e) { try { btn.setPressed(true); } catch (e2) {} }
                }
            }
        } catch (e) {}

        var idx = -1;
        var shape = null;
        for (var i = 0; i < me.cs.dataset.length; i++) {
            if (me.cs.dataset[i].uuid === uuid) {
                idx = i;
                shape = me.cs.dataset[i];
                break;
            }
        }
        if (idx < 0 || !shape) return;

        try { me.updateOriginIfNeeded(true); } catch (e) {}

        me.onShapeSelected({ uuid: uuid, index: idx });

        try { me.cs.activeShape = shape; } catch (e) {}

        try { me.dispatchClickOnShape(shape); } catch (e) {}

        try {
            if (typeof me.cs.draw === 'function')        me.cs.draw();
            else if (typeof me.cs.update === 'function') me.cs.update();
        } catch (e) {}

        me.scheduleRenderLabels();
    },

    getShapeCenterInWorld: function(shape) {
        if (!shape || !shape.coor) return null;
        var coor = shape.coor;
        var t = shape.type;

        if (t === 1) {
            var a = coor[0], b = coor[1];
            if (!Ext.isArray(a) || !Ext.isArray(b)) return null;
            return { x: (a[0] + b[0]) / 2, y: (a[1] + b[1]) / 2 };
        }
        if (t === 3 || t === 5) {
            return { x: coor[0], y: coor[1] };
        }
        if (t === 2 || t === 4) {
            var sx = 0, sy = 0;
            for (var i = 0; i < coor.length; i++) { sx += coor[i][0]; sy += coor[i][1]; }
            return { x: sx / coor.length, y: sy / coor.length };
        }
        return null;
    },

    dispatchClickOnShape: function(shape) {
        var me = this;
        if (!me.cs || !shape) return;
        if (!me.cachedOrigin.ready) return;

        var canvasEl = me.getCanvasEl();
        if (!canvasEl) return;

        var center = me.getShapeCenterInWorld(shape);
        if (!center) return;

        var screenX = me.cachedOrigin.x + center.x * me.cachedOrigin.scale;
        var screenY = me.cachedOrigin.y + center.y * me.cachedOrigin.scale;

        var rect = canvasEl.getBoundingClientRect();
        var clientX = rect.left + screenX;
        var clientY = rect.top  + screenY;

        var baseProps = {
            bubbles: true, cancelable: true, composed: true, view: window,
            clientX: clientX, clientY: clientY,
            screenX: clientX, screenY: clientY, button: 0
        };
        var pointerProps = Object.assign({}, baseProps, {
            pointerId: 1, pointerType: 'mouse', isPrimary: true,
            width: 1, height: 1, pressure: 0.5
        });

        try {
            if (typeof PointerEvent !== 'undefined') {
                canvasEl.dispatchEvent(new PointerEvent('pointerover',  Object.assign({}, pointerProps, { buttons: 0 })));
                canvasEl.dispatchEvent(new PointerEvent('pointerenter', Object.assign({}, pointerProps, { buttons: 0 })));
                canvasEl.dispatchEvent(new PointerEvent('pointermove',  Object.assign({}, pointerProps, { buttons: 0 })));
                canvasEl.dispatchEvent(new PointerEvent('pointerdown',  Object.assign({}, pointerProps, { buttons: 1 })));
                canvasEl.dispatchEvent(new PointerEvent('pointerup',    Object.assign({}, pointerProps, { buttons: 0 })));
            }
            canvasEl.dispatchEvent(new MouseEvent('mouseover', Object.assign({}, baseProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new MouseEvent('mousemove', Object.assign({}, baseProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new MouseEvent('mousedown', Object.assign({}, baseProps, { buttons: 1 })));
            canvasEl.dispatchEvent(new MouseEvent('mouseup',   Object.assign({}, baseProps, { buttons: 0 })));
            canvasEl.dispatchEvent(new MouseEvent('click',     Object.assign({}, baseProps, { buttons: 0 })));
        } catch (e) {
            console.warn('[SceneModelPanel] 事件派发失败', e);
        }
    },

    // ========================================================
    // 颜色工具方法
    // ========================================================
    ensureHashColor: function(color) {
        if (!color || typeof color !== 'string') return '';
        color = color.trim();
        if (color.indexOf('#') !== 0) color = '#' + color;
        return color;
    },

    applyColorFieldVisual: function(field, alphaOverride) {
        if (!field || !field.inputEl) return;
        var hexRaw = field.getValue() || '';
        var hex = String(hexRaw).replace('#', '').toUpperCase();
        if (hex.length !== 6) return;

        var alpha = alphaOverride;
        if (alpha == null) {
            alpha = 1;
            try { if (field.color && field.color.a != null) alpha = field.color.a; } catch (e) {}
        }
        alpha = Math.max(0, Math.min(1, alpha));

        try { field.inputEl.applyStyles({ background: '#' + hex, opacity: alpha }); } catch (e) {}
        try {
            var colorObj = field.color;
            if (!colorObj || typeof colorObj !== 'object') colorObj = {};
            colorObj.a = alpha;
            field.setColor(colorObj);
        } catch (e) {}
    },

    setColorFieldValue: function(itemId, hex, alphaPercent) {
        var me = this;
        var panel = me.down('panel[region=east]');
        if (!panel) return;
        var field = panel.down('#' + itemId);
        if (!field) return;

        var cleanHex = (hex || '#000000').replace('#', '').toUpperCase();
        var alpha = Math.max(0, Math.min(1, (alphaPercent != null ? alphaPercent : 100) / 100));

        try { field.setValue(cleanHex); } catch (e) {}
        try {
            var colorObj = field.color;
            if (!colorObj || typeof colorObj !== 'object') { colorObj = {}; field.color = colorObj; }
            colorObj.a = alpha;
        } catch (e) {}
        if (field.inputEl) {
            try { field.inputEl.applyStyles({ background: '#' + cleanHex, opacity: alpha }); } catch (e) {}
        }
        try { field.setColor(field.color); } catch (e) {}
    },

    getColorFieldValue: function(itemId) {
        var me = this;
        var panel = me.down('panel[region=east]');
        if (!panel) return { hex: '#000000', alphaPercent: 100 };
        var field = panel.down('#' + itemId);
        if (!field) return { hex: '#000000', alphaPercent: 100 };

        var raw = field.getValue() || '';
        var cleanHex = ('#' + String(raw).replace('#', '')).toUpperCase();
        var alphaPercent = 100;
        try { if (field.color && field.color.a != null) alphaPercent = Math.round(field.color.a * 100); } catch (e) {}
        return { hex: cleanHex, alphaPercent: alphaPercent };
    },

    normalizeColor: function(color, fallback) {
        if (!color || typeof color !== 'string') return fallback;
        color = color.trim();
        if (/^rgba?\(/i.test(color)) return color;
        if (/^#[0-9a-f]{3}$/i.test(color)) return '#' + color[1] + color[1] + color[2] + color[2] + color[3] + color[3];
        if (/^#[0-9a-f]{6}$/i.test(color)) return color;
        if (/^#[0-9a-f]{8}$/i.test(color)) {
            var r = parseInt(color.slice(1, 3), 16);
            var g = parseInt(color.slice(3, 5), 16);
            var b = parseInt(color.slice(5, 7), 16);
            var a = parseInt(color.slice(7, 9), 16) / 255;
            if (a === 0) a = 0.01;
            return 'rgba(' + r + ',' + g + ',' + b + ',' + a.toFixed(3) + ')';
        }
        return fallback;
    },

    readRecordConfig: function(record) {
        if (!record) return { imageUrl: '', shapes: [] };
        var raw = record.get('config');
        if (!raw) return { imageUrl: '', shapes: [] };
        if (typeof raw === 'object') return raw;
        try {
            var parsed = JSON.parse(raw);
            if (parsed && typeof parsed === 'object') return parsed;
        } catch (e) {}
        return { imageUrl: '', shapes: [] };
    },

    writeRecordConfig: function(record, cfg) {
        if (!record) return;
        try {
            if (!cfg || typeof cfg !== 'object') cfg = { imageUrl: '', shapes: [] };
            record.set('config', JSON.stringify(cfg));
        } catch (e) {}
    },

    safeSetData: function(shapes) {
        var me = this;
        if (!me.cs) return;
        var data = Ext.isArray(shapes) ? shapes : [];

        var newKey;
        try { newKey = JSON.stringify(data); }
        catch (e) { newKey = data.map(function(s) { return s.uuid || ''; }).join('|'); }

        if (newKey === me._lastDataKey) return;
        me._lastDataKey = newKey;

        try { me.cs.setData(data); }
        catch (e) {
            me._lastDataKey = null;
            try { me.cs.setData([]); } catch (e2) {}
        }
    },

    setCanvasImage: function(url) {
        var me = this;
        if (!me.cs || me._rebuilding) return;
        if (!url) url = me.BLANK_IMAGE;

        // ★ 关键修复：用户一选中，立即同步记录 imageUrl
        //   不再等 probe.onload 异步回调；即便 SVG 加载 onerror 也不会丢
        me.imageUrl = url;

        if (url === me.BLANK_IMAGE) {
            me.imageUrl = '';
            me.rebuildCanvasSelect();
            return;
        }

        // 已加载过相同图片 → 直接返回（但上面已经同步 imageUrl，是安全的）
        if (url === me._canvasCurrentUrl) return;

        if (me._settingImage) return;
        me._settingImage = true;

        var probe = new Image();
        probe.onload = function() {
            me._settingImage = false;
            if (!me.cs) return;
            try {
                me.cs.setImage(url);
                me._canvasCurrentUrl = url;
                // me.imageUrl 已在前面同步设置，这里不再重复赋值
            } catch (e) {
                // 内部渲染异常时保留 imageUrl，交给用户重新选图
                me._canvasCurrentUrl = null;
                me.rebuildCanvasSelect();
            }
        };
        probe.onerror = function() {
            me._settingImage = false;
            // ★ 只有当前 imageUrl 仍然是这个失败的 url 时才清空
            //   避免并发情况下把新选的图片 url 误清
            if (me.imageUrl === url) {
                me.imageUrl = '';
            }
            me._canvasCurrentUrl = null;
            me.rebuildCanvasSelect();
        };
        probe.src = url;
    },

    rebuildCanvasSelect: function() {
        var me = this;
        if (me._rebuilding) return;
        me._rebuilding = true;
        try {
            me.stopOriginTimer();
            try {
                if (me.cs) {
                    if (typeof me.cs.destroy === 'function')      me.cs.destroy();
                    else if (typeof me.cs.dispose === 'function') me.cs.dispose();
                }
            } catch (e) {}
            me.cs = null;

            var canvasCmp = me.down('#canvas');
            if (canvasCmp && canvasCmp.el && canvasCmp.el.dom) {
                var oldDom  = canvasCmp.el.dom;
                var parentEl = oldDom.parentNode;
                if (parentEl) {
                    var newDom = oldDom.cloneNode(false);
                    newDom.width  = oldDom.width  || 800;
                    newDom.height = oldDom.height || 600;
                    parentEl.replaceChild(newDom, oldDom);
                    canvasCmp.el = Ext.get(newDom);
                }
            }
            me._canvasCurrentUrl = null;
            me._lastDataKey      = null;
            me._settingImage     = false;
            me.cachedOrigin      = { x: 0, y: 0, scale: 1, ready: false };
            if (me.labelLayer) me.labelLayer.innerHTML = '';
            me.initCanvasSelect();
            me._canvasCurrentUrl = me.BLANK_IMAGE;
        } finally {
            me._rebuilding = false;
        }
    },

    // ========================================================
    // ★ 初始化 canvas-select
    // ========================================================
    initCanvasSelect: function() {
        var me = this;
        var canvasCmp = me.down('#canvas');
        var layerCmp = me.down('#labelLayer');
        if (!canvasCmp || !layerCmp) return;

        me.stopOriginTimer();
        try { if (me.cs && typeof me.cs.destroy === 'function') me.cs.destroy(); } catch (e) {}
        me.cs = null;

        var canvasEl = canvasCmp.el.dom;
        me.labelLayer = layerCmp.el.dom;
        if (typeof CanvasSelect === 'undefined') { Ext.Msg.alert('错误', 'CanvasSelect 库未加载'); return; }

        me.cs = new CanvasSelect(canvasEl, me.BLANK_IMAGE);
        me._canvasCurrentUrl = me.BLANK_IMAGE;
        me._lastDataKey = '';
        me.cs.ctrlRadius = me.globalStyle.ctrlRadius;
        me.cs.createType = 0;
        me.cs.scrollZoom = true;
        me.cs.hideLabel = true;

        // ★ 关键改动：load 事件里主动 nudge 一次鼠标，触发 canvas-select 内部初始化
        me.cs.on('load', function() {
            try { if (typeof me.cs.fitZoom === 'function') me.cs.fitZoom(); }
            catch (e) { console.warn('[SceneModelPanel] load fitZoom 失败', e); }

            //me.enforceReadOnly();

            // 关键：让库内部完成 mouse/position 初始化
            me._nudgeCanvasMouseCenter();

            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();

            // 兜底：延时再适应一次，应对异步渲染
            Ext.defer(function() {
                if (!me.cs) return;
                me._nudgeCanvasMouseCenter();
                me.updateOriginIfNeeded(true);
                me.scheduleRenderLabels();
            }, 150, me);
        });

        me.cs.on('add',     function(info) { me.onShapeAdded(info); });

        me.cs.on('select',  function(info) {
            if (me._clearingSelection) return;
            var uuid = me.extractUuid(info);
            if (!uuid) {
                if (me.lastSelectedUuid) me.clearSelectionUI();
            } else {
                me.onShapeSelected(info);
            }
        });

        me.cs.on('delete',  function(info) { me.onShapeDeleted(info); });
        me.cs.on('updated', function()     { me.scheduleRenderLabels(); me.refreshBindingTree(); });
        me.cs.on('coor',    function()     { me.scheduleRenderLabels(); });

        canvasEl.addEventListener('click', function() {
            if (!me.cs || me.cs.createType !== 0) return;
            setTimeout(function() {
                if (!me.cs || me.cs.createType !== 0) return;
                if (me._clearingSelection) return;
                var active = me.cs.activeShape;
                if (!active && me.lastSelectedUuid) {
                    me.clearSelectionUI();
                }
            }, 50);
        });

        canvasEl.addEventListener('dblclick', function() {
            if (me.cs.createType !== 0) return;
            var info = me.cs.activeShape;
            if (info) {
                var uuid = me.extractUuid(info);
                if (uuid) me.openDataSourceWindow(uuid, true);
            }
        });

        me.startOriginTimer();

        if (me._visibilityHandler) {
            try { document.removeEventListener('visibilitychange', me._visibilityHandler); } catch (e) {}
            me._visibilityHandler = null;
        }
        me._visibilityHandler = function() {
            if (document.hidden) {
                me.stopOriginTimer();
            } else {
                me.startOriginTimer();
                if (me.cs) {
                    me.updateOriginIfNeeded(true);
                    me.scheduleRenderLabels();
                }
            }
        };
        document.addEventListener('visibilitychange', me._visibilityHandler);
    },

    // ========================================================
    // 强制只读
    // ========================================================
    enforceReadOnly: function() {
        var me = this;
        if (!me.cs) return;
        try { me.cs.drag = false; }     catch (e) {}
        try { me.cs.readonly = true; }  catch (e) {}
        try { me.cs.locked = true; }    catch (e) {}
        try {
            if (Ext.isArray(me.cs.dataset)) {
                me.cs.dataset.forEach(function(s) {
                    if (!s) return;
                    s.drag = false;
                    s.locked = true;
                    s.readonly = true;
                });
            }
        } catch (e) {}
    },

    onShapeAdded: function(info) {
        var me = this;
        var uuid = me.extractUuid(info);
        if (!uuid) return;

        me.userColorMap.set(uuid, me.globalStyle.strokeStyle || '#00FF00');
        me.userLineWidthMap.set(uuid, me.globalStyle.lineWidth != null ? me.globalStyle.lineWidth : 1);

        Ext.defer(function() { me.openDataSourceWindow(uuid, false); }, 30);
    },

    onShapeDeleted: function(info) {
        var me = this;

        var uuid = null;
        if (info) {
            if (info.uuid)         uuid = info.uuid;
            else if (info.oldUuid) uuid = info.oldUuid;
        }

        if (uuid) {
            me.bindingMap.delete(uuid);
            me.labelStyleMap.delete(uuid);
            me.userColorMap.delete(uuid);
            me.userLineWidthMap.delete(uuid);
            if (me.lastSelectedUuid === uuid) {
                me.lastSelectedUuid = null;
                try { me.resetStylePanel(); } catch (e) {}
            }
        }

        me.refreshBindingTree();
        me.scheduleRenderLabels();
    },

    onShapeSelected: function(info) {
        var me = this;
        if (!me.cs || !me.cs.dataset) return;

        var uuid = me.extractUuid(info);
        if (!uuid) return;
        me.lastSelectedUuid = uuid;
        var shape = null;
        for (var i = 0; i < me.cs.dataset.length; i++) {
            if (me.cs.dataset[i].uuid === uuid) { shape = me.cs.dataset[i]; break; }
        }
        if (!shape) return;

        var panel = me.down('panel[region=east]');
        if (!panel) return;

        var fillParts = me.parseFillStyle(shape.fillStyle || 'rgba(0,0,255,0.25)');
        var userColor = me.userColorMap.get(uuid) || shape._userStrokeStyle || '#00FF00';
        if (userColor === me.TRANSPARENT_STROKE) userColor = me.globalStyle.strokeStyle || '#00FF00';

        var userLw = me.userLineWidthMap.get(uuid);
        if (userLw == null) userLw = shape._userLineWidth != null ? shape._userLineWidth
                              : (shape.lineWidth != null ? shape.lineWidth : 1);

        var crVal;
        if (shape.type === 3) {
            crVal = me.cs.ctrlRadius != null ? me.cs.ctrlRadius : 3;
        } else {
            crVal = shape.ctrlRadius != null ? shape.ctrlRadius : 3;
        }

        me._suppressStyleChange = true;
        try {
            me.setColorFieldValue('strokeStyle', userColor, 100);
            me.setColorFieldValue('fillStyle', fillParts.hex, fillParts.alpha);

            var lwField = panel.down('#lineWidth');
            if (lwField) lwField.setValue(userLw);

            var crField = panel.down('#ctrlRadius');
            if (crField) crField.setValue(crVal);

            var labelStyle = me.labelStyleMap.get(uuid) || me.defaultLabelStyle || {};
            me.setColorFieldValue('labelBg', labelStyle.bgColor || '#4A6CF7',
                labelStyle.bgOpacity != null ? labelStyle.bgOpacity : 100);
            me.setColorFieldValue('labelText', labelStyle.textColor || '#FFFFFF', 100);

            var fontField = panel.down('#labelFont');
            if (fontField) fontField.setValue(labelStyle.font || '12px');

            var hideDevField = panel.down('#hideDeviceName');
            if (hideDevField) hideDevField.setValue(labelStyle.hideDeviceName !== false);

            var hideField = panel.down('#hideLabel');
            if (hideField) hideField.setValue(!!labelStyle.hide);

            var upField = panel.down('#labelUp');
            if (upField) upField.setValue(labelStyle.up !== false);
        } finally {
            me._suppressStyleChange = false;
        }

        me.selectTreeNodeByUuid(uuid);
    },

    afterZoom: function() {
        var me = this;
        Ext.defer(function() { me.updateOriginIfNeeded(true); me.scheduleRenderLabels(); }, 30);
    },

    onSelectServerImage: function() {
        var me = this;
        var imageStore = Ext.create('Ext.data.Store', {
            fields: ['name', 'url', 'size', 'lastModified'],
            proxy: { type: 'ajax', url: context + '/sceneModelController/getSceneImageList',
                     reader: { type: 'json', rootProperty: 'data' } },
            autoLoad: true
        });

        var win = Ext.create('Ext.window.Window', {
            title: '选择场景图片', width: 820, height: 580,
            modal: true, layout: 'fit', closeAction: 'destroy',
            items: [{
                xtype: 'panel', layout: 'fit',
                tbar: [
                    { xtype: 'textfield', itemId: 'searchField', emptyText: '搜索文件名...', width: 220,
                      listeners: { change: function(f, v) { win.filterImages(v); } } },
                    '->',
                    { xtype: 'button', text: '📤 上传本地图片', handler: function() { win.doUpload(); } },
                    { xtype: 'button', text: '🔄 刷新',
                      handler: function() {
                          imageStore.reload({ callback: function() {
                              win.allImages = imageStore.getRange().map(function(r) { return r.getData(); });
                              // ★ 刷新后也恢复选中当前图片
                              Ext.defer(function() { win.selectCurrentImage(); }, 50);
                          }});
                      } }
                ],
                items: [{
                    xtype: 'dataview', itemId: 'imageView',
                    itemSelector: 'div.scene-image-item',
                    overItemCls: 'scene-image-item-over',
                    selectedItemCls: 'scene-image-item-selected',
                    emptyText: '<div style="padding:40px;color:#aaa;text-align:center;">暂无图片</div>',
                    store: imageStore,
                    tpl: [
                        '<tpl for=".">',
                            '<div class="scene-image-item" style="display:inline-block;width:140px;height:170px;',
                            'margin:10px;text-align:center;cursor:pointer;border:2px solid #e9ecef;',
                            'border-radius:6px;padding:8px;background:#fff;vertical-align:top;">',
                                '<div style="height:110px;display:flex;align-items:center;justify-content:center;overflow:hidden;">',
                                    '<img src="{url}" style="max-width:120px;max-height:110px;object-fit:contain;" onerror="this.style.display=\'none\'"/>',
                                '</div>',
                                '<div style="font-size:11px;color:#666;margin-top:6px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" title="{name}">{name}</div>',
                            '</div>',
                        '</tpl>'
                    ],
                    listeners: { itemdblclick: function(view, record) { win.doSelect(record.get('url')); } }
                }]
            }],
            buttons: [
                { text: '取消', handler: function() { win.close(); } },
                { text: '确定', cls: 'x-btn-primary', handler: function() {
                    var view = win.down('[itemId=imageView]');
                    var sel = view.getSelection();
                    if (sel.length === 0) { Ext.Msg.alert('提示', '请选择一张图片'); return; }
                    win.doSelect(sel[0].get('url'));
                }}
            ],

            // ========================================================
            // ★ 新增：默认选中当前配置的图片
            // --------------------------------------------------------
            // 打开窗口后，根据 me.imageUrl 在 store 里找到对应 record，
            // 选中它并滚动到可见区域，让用户一眼看到当前配置的是哪张图。
            // ========================================================
            selectCurrentImage: function() {
                var view = this.down('[itemId=imageView]');
                if (!view) return;
                var store = view.getStore();
                if (!store || store.getCount() === 0) return;

                var currentUrl = me.imageUrl || '';
                if (!currentUrl) return;   // 无当前图片 → 不选中任何

                // 在 store 中查找 url 匹配的 record
                var matched = null;
                store.each(function(rec) {
                    if (rec.get('url') === currentUrl) {
                        matched = rec;
                        return false;   // 停止遍历
                    }
                });
                if (!matched) return;

                // 选中 + 滚动到可见
                try {
                    view.getSelectionModel().select(matched);
                    view.ensureVisible(matched);
                } catch (e) {}
            },

            filterImages: function(keyword) {
                var view = this.down('[itemId=imageView]');
                var allData = this.allImages || [];
                view.getStore().removeAll();
                if (!keyword) {
                    view.getStore().add(allData);
                    // ★ 无关键字过滤后，恢复选中当前图片
                    Ext.defer(function() { win.selectCurrentImage(); }, 30);
                    return;
                }
                var filtered = allData.filter(function(item) {
                    return item.name.toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
                });
                view.getStore().add(filtered);
                // ★ 过滤后也尝试恢复选中（当前图片若被过滤掉则不会选中，符合预期）
                Ext.defer(function() { win.selectCurrentImage(); }, 30);
            },

            doSelect: function(url) {
                me.setCanvasImage(url);
                if (me.currentRecord) {
                    var cfg = me.readRecordConfig(me.currentRecord);
                    cfg.imageUrl = url;
                    cfg.imageSource = 'server';
                    me.writeRecordConfig(me.currentRecord, cfg);
                }
                win.close();
            },

            doUpload: function() {
                var input = document.createElement('input');
                input.type = 'file'; input.accept = 'image/*,.svg';
                input.onchange = function(e) {
                    var file = e.target.files[0];
                    if (!file) return;
                    var formData = new FormData();
                    formData.append('file', file);
                    Ext.MessageBox.show({ msg: '正在上传...', progressText: '上传中', width: 300 });
                    Ext.Ajax.request({
                        url: context + '/sceneModelController/uploadSceneImage',
                        method: 'POST', rawData: formData,
                        headers: { 'Content-Type': null },
                        success: function(resp) {
                            Ext.MessageBox.hide();
                            var result = Ext.decode(resp.responseText);
                            if (result.success) {
                                Ext.Msg.alert(loginUserLanguageResource.tip, '上传成功');
                                imageStore.reload({ callback: function() {
                                    win.allImages = imageStore.getRange().map(function(r) { return r.getData(); });
                                    // ★ 上传成功后也恢复选中
                                    Ext.defer(function() { win.selectCurrentImage(); }, 50);
                                }});
                            } else { Ext.Msg.alert('失败', result.message || '上传失败'); }
                        },
                        failure: function() { Ext.MessageBox.hide(); Ext.Msg.alert('错误', '上传失败'); }
                    });
                };
                input.click();
            }
        });

        // ========================================================
        // ★ store 加载完成后，自动选中当前配置的图片
        // --------------------------------------------------------
        // store 与 window 谁先就绪不确定，所以两边各挂一次：
        //   1) store.on('load') → 窗口已渲染就立即选中
        //   2) win.on('show')   → 窗口已显示后也尝试一次
        // ========================================================
        imageStore.on('load', function(store) {
            win.allImages = store.getRange().map(function(r) { return r.getData(); });
            if (win.rendered) {
                Ext.defer(function() { win.selectCurrentImage(); }, 50);
            }
        });

        win.on('show', function() {
            Ext.defer(function() { win.selectCurrentImage(); }, 100);
        });

        win.show();
    },

    onUploadLocalImage: function() {
        var me = this;
        var targetRecord = me.currentRecord;
        var input = document.createElement('input');
        input.type = 'file'; input.accept = 'image/*,.svg';
        input.onchange = function(e) {
            var file = e.target.files[0];
            if (!file) return;
            var formData = new FormData();
            formData.append('file', file);
            Ext.MessageBox.show({ msg: '正在上传...', progressText: '上传中', width: 300 });
            Ext.Ajax.request({
                url: context + '/sceneModelController/uploadSceneImage',
                method: 'POST', rawData: formData,
                headers: { 'Content-Type': null },
                success: function(resp) {
                    Ext.MessageBox.hide();
                    var result;
                    try { result = Ext.decode(resp.responseText); }
                    catch (err) { Ext.Msg.alert('错误', '服务器返回数据异常'); return; }
                    if (!result.success) { Ext.Msg.alert('失败', result.message || '上传失败'); return; }
                    
                    me.setCanvasImage(result.imageUrl);
                    if (targetRecord) {
                        var cfg = me.readRecordConfig(targetRecord);
                        if (!Ext.isArray(cfg.shapes)) cfg.shapes = [];
                        // ★ 直接用服务器返回的 imageUrl
                        cfg.imageUrl = result.imageUrl;
                        cfg.imageSource = 'uploaded';
                        me.writeRecordConfig(targetRecord, cfg);
                    }
                    
                    Ext.Msg.alert(loginUserLanguageResource.tip, '上传成功');
                },
                failure: function() { Ext.MessageBox.hide(); Ext.Msg.alert('错误', '上传失败'); }
            });
        };
        input.click();
    },

    onSaveAnnotations: function() {
        var me = this;
        if (!me.currentRecord) { Ext.Msg.alert('提示', '请先选择场景'); return; }
        if (!me.cs) { Ext.Msg.alert('提示', '画布未初始化'); return; }
        var imageUrl = me.imageUrl || '';
        if (!imageUrl) { Ext.Msg.alert('提示', '请先选择场景图片'); return; }
        if (imageUrl.indexOf('blob:') === 0 || imageUrl.indexOf('data:') === 0) {
            Ext.Msg.confirm('提示', '当前图片还未上传到服务器，是否先上传？', function(btn) {
                if (btn === 'yes') me.onUploadLocalImage();
            });
            return;
        }
        var cfg = me.getConfig();
        if (!cfg.shapes || cfg.shapes.length === 0) {
            Ext.Msg.confirm('提示', '当前场景还没有任何标注，仍要保存吗？', function(btn) {
                if (btn === 'yes') me.submitSaveScene(cfg);
            });
            return;
        }
        me.submitSaveScene(cfg);
    },

    submitSaveScene: function(cfg) {
        var me = this;
        var record = me.currentRecord;
        me.writeRecordConfig(record, cfg);
        Ext.MessageBox.show({ msg: '正在保存场景标注...', progressText: '保存中',
                              width: 300, wait: true, waitConfig: { interval: 200 } });
        Ext.Ajax.request({
            url: context + '/sceneModelController/saveSceneModel',
            method: 'POST',
            params: { id: record.get('modelId') || record.get('id'), config: JSON.stringify(cfg) },
            success: function(resp) {
                Ext.MessageBox.hide();
                try {
                    var result = Ext.decode(resp.responseText);
                    if (result.success) {
                        record.commit();
                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
                    } else {
                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveFailed);
                    }
                } catch (e) { Ext.Msg.alert('错误', '服务器返回数据异常'); }
            },
            failure: function(response) {
                Ext.MessageBox.hide();
                Ext.Msg.alert('错误', '网络请求失败：' + (response.status || ''));
            }
        });
    },

    // ========================================================
    // 数据源绑定窗口
    // ========================================================
    openDataSourceWindow: function(uuid, isEdit) {
        var me = this;
        if (!uuid) return;
        var existing = me.bindingMap.get(uuid) || {};
        var orgId = '';
        if (me.currentRecord) {
            orgId = me.currentRecord.get('orgid') || me.currentRecord.get('orgId') || '';
        }
        var oldWin = Ext.getCmp('sceneDataSourceWindow');
        if (oldWin) oldWin.close();
        var oldDeviceGrid = Ext.getCmp('SceneDeviceGrid_Id');
        if (oldDeviceGrid) oldDeviceGrid.destroy();
        var oldFieldGrid = Ext.getCmp('SceneDeviceFieldGrid_Id');
        if (oldFieldGrid) oldFieldGrid.destroy();

        Ext.create('AP.view.sceneModel.DataSourceWindow', {
            targetUuid: uuid, targetOrgId: orgId,
            currentBinding: {
                deviceId: existing.deviceId || '',
                fieldName: existing.fieldName || '',
                unit: existing.unit || ''
            },
            onConfirm: function(binding, uuid) {
                var old = me.bindingMap.get(uuid) || {};
                me.bindingMap.set(uuid, {
                    deviceId: binding.deviceId, deviceName: binding.deviceName,
                    fieldName: binding.fieldName, fieldLabel: binding.fieldLabel,
                    unit: binding.unit || '', dataSource: binding.dataSource || '',
                    currentValue: old.currentValue != null ? old.currentValue : '--'
                });
                if (!me.labelStyleMap.has(uuid)) {
                    me.labelStyleMap.set(uuid, Ext.apply({}, me.defaultLabelStyle));
                }
                me.refreshBindingTree();
                me.scheduleRenderLabels();

                Ext.defer(function() {
                    me.safe(function() {
                        if (isEdit) {
                            me.selectShapeByUuid(uuid);
                        } else {
                            if (me.cs) {
                                me.cs.createType = me._userCreateType;
                                var btnId = (me._userCreateType === 1) ? 'btnRect'
                                          : (me._userCreateType === 3) ? 'btnPoint'
                                          : 'btnSelect';
                                var btn = me.down('#' + btnId);
                                if (btn) {
                                    try { btn.toggle(true, true); } catch (e) {}
                                }
                            }
                            me.scheduleRenderLabels();
                        }
                    }, 'onConfirm 后处理');
                }, 100);
            }
        }).show();
    },

    onGraphicStyleChange: function() {
        var me = this;
        if (me._suppressStyleChange) return;
        if (!me.cs) return;
        var panel = me.down('panel[region=east]');
        if (!panel) return;

        var strokeVal = me.getColorFieldValue('strokeStyle');
        var fillVal   = me.getColorFieldValue('fillStyle');

        var strokeStyle = me.normalizeColor(strokeVal.hex, '#00FF00');
        var fillColor   = me.normalizeColor(fillVal.hex, '#0000FF');
        var fillOpacity = fillVal.alphaPercent;

        var lineWidth = panel.down('#lineWidth') ? panel.down('#lineWidth').getValue() : 1;
        var ctrlRadius = panel.down('#ctrlRadius') ? panel.down('#ctrlRadius').getValue() : 3;

        if (!strokeStyle || !fillColor) return;

        var fillStyle = me.buildFillColor(fillColor, fillOpacity);
        var canvasStroke = (lineWidth <= 0) ? me.TRANSPARENT_STROKE : strokeStyle;
        var canvasLw = (lineWidth <= 0) ? 1 : lineWidth;

        if (me.lastSelectedUuid && me.cs && me.cs.dataset) {
            var idx = -1;
            for (var i = 0; i < me.cs.dataset.length; i++) {
                if (me.cs.dataset[i].uuid === me.lastSelectedUuid) { idx = i; break; }
            }
            if (idx >= 0) {
                me.userColorMap.set(me.lastSelectedUuid, strokeStyle);
                me.userLineWidthMap.set(me.lastSelectedUuid, lineWidth);

                var shape = me.cs.dataset[idx];
                shape.strokeStyle = canvasStroke;
                shape.lineWidth = canvasLw;
                shape.fillStyle = fillStyle;
                shape._userStrokeStyle = strokeStyle;
                shape._userLineWidth = lineWidth;

                if (shape.type === 3) {
                    me.cs.ctrlRadius = ctrlRadius;
                } else {
                    shape.ctrlRadius = ctrlRadius;
                }

                me._lastDataKey = null;
                var snapshot = me.cs.dataset.map(function(s) { return Ext.apply({}, s); });
                me.safeSetData(snapshot);

                requestAnimationFrame(function() {
                    try {
                        if (me.cs) {
                            try { me.cs.activeShape = me.cs.dataset[idx]; } catch (e) {}
                            try { if (typeof me.cs.update === 'function') me.cs.update(); } catch (e) {}
                        }
                    } catch (e) {}
                    me.scheduleRenderLabels();
                });
                return;
            }
        }

        if (me.cs) {
            me.globalStyle = {
                strokeStyle: strokeStyle, fillColor: fillColor, fillOpacity: fillOpacity,
                lineWidth: lineWidth, ctrlRadius: ctrlRadius
            };
            me.cs.strokeStyle = canvasStroke;
            me.cs.fillStyle = fillStyle;
            me.cs.lineWidth = canvasLw;
            me.cs.ctrlRadius = ctrlRadius;
            try { if (typeof me.cs.update === 'function') me.cs.update(); } catch (e) {}
        }
    },

    onLabelStyleChange: function() {
        var me = this;
        if (me._suppressStyleChange) return;
        if (!me.cs) return;
        var panel = me.down('panel[region=east]');
        if (!panel) return;

        var bgVal   = me.getColorFieldValue('labelBg');
        var textVal = me.getColorFieldValue('labelText');

        var style = {
            font: panel.down('#labelFont') ? panel.down('#labelFont').getValue() : '12px',
            bgColor: me.normalizeColor(bgVal.hex, '#4A6CF7'),
            bgOpacity: bgVal.alphaPercent,
            textColor: me.normalizeColor(textVal.hex, '#FFFFFF'),
            hide: panel.down('#hideLabel') ? panel.down('#hideLabel').getValue() : false,
            hideDeviceName: panel.down('#hideDeviceName') ? panel.down('#hideDeviceName').getValue() : true,
            up: panel.down('#labelUp') ? panel.down('#labelUp').getValue() : true
        };
        if (!style.bgColor || !style.textColor) return;

        if (me.lastSelectedUuid) {
            me.labelStyleMap.set(me.lastSelectedUuid, style);
        } else {
            Ext.apply(me.defaultLabelStyle, style);
        }
        me.scheduleRenderLabels();
    },

    buildFillColor: function(hexColor, alphaPercent) {
        var hex = hexColor.replace('#', '');
        if (hex.length !== 6) return 'rgba(0,0,255,0.25)';
        var r = parseInt(hex.slice(0, 2), 16);
        var g = parseInt(hex.slice(2, 4), 16);
        var b = parseInt(hex.slice(4, 6), 16);
        var a = Math.max(0, Math.min(1, (alphaPercent || 0) / 100));
        return 'rgba(' + r + ',' + g + ',' + b + ',' + a.toFixed(3) + ')';
    },

    updateOriginIfNeeded: function(force) {
        if (!this.cs) return false;
        var cs = this.cs;
        var scale = cs.scale || 1;
        if (!cs.mouse || !cs.position) return false;
        var newX = cs.mouse[0] - cs.position[0] * scale;
        var newY = cs.mouse[1] - cs.position[1] * scale;
        var co = this.cachedOrigin;
        if (!co.ready || force ||
            Math.abs(newX - co.x) > 1 || Math.abs(newY - co.y) > 1 ||
            Math.abs(scale - co.scale) > 0.001) {
            this.cachedOrigin = { x: newX, y: newY, scale: scale, ready: true };
            return true;
        }
        return false;
    },

    scheduleRenderLabels: function() {
        var me = this;
        if (me._rafPending) return;
        me._rafPending = true;
        requestAnimationFrame(function() {
            me._rafPending = false;
            me.renderLabels();
        });
    },

    renderLabels: function() {
        var me = this;
        var layer = me.labelLayer;
        if (!layer || !me.cachedOrigin.ready || !me.cs || !me.cs.dataset) return;

        var origin = me.cachedOrigin;
        var existing = {};
        Array.from(layer.children).forEach(function(el) { existing[el.dataset.uuid] = el; });
        var visible = {};

        me.cs.dataset.forEach(function(shape) {
            var binding = me.bindingMap.get(shape.uuid);
            if (!binding) return;
            var anchor = me.getShapeTopAnchor(shape);
            if (!anchor) return;

            var cx = Math.round(origin.x + anchor.x * origin.scale);
            var cy = Math.round(origin.y + anchor.y * origin.scale);
            var style = me.labelStyleMap.get(shape.uuid) || me.defaultLabelStyle;

            var up = style.up !== false;
            var translateY = up ? '-100%' : '0';

            var el = existing[shape.uuid];
            if (!el) {
                el = document.createElement('div');
                el.className = 'scene-canvas-label';
                el.dataset.uuid = shape.uuid;
                el.addEventListener('dblclick', function(e) {
                    e.stopPropagation();
                    me.openDataSourceWindow(shape.uuid, true);
                });
                layer.appendChild(el);
            }
            el.style.cssText = 'position:absolute;padding:4px 10px;border-radius:4px;white-space:nowrap;' +
                'font-weight:600;transform:translate(-50%,' + translateY + ');box-shadow:0 2px 6px rgba(0,0,0,0.18);' +
                'pointer-events:auto;cursor:pointer;' +
                'left:' + cx + 'px;top:' + cy + 'px;' +
                'background:' + me.buildFillColor(style.bgColor, style.bgOpacity) + ';' +
                'color:' + style.textColor + ';font-size:' + style.font + ';' +
                'display:' + (style.hide ? 'none' : 'block') + ';';
            el.textContent = me.buildLabelText(binding, style);
            visible[shape.uuid] = true;
        });

        Object.keys(existing).forEach(function(uuid) {
            if (!visible[uuid] && existing[uuid].parentNode) {
                existing[uuid].parentNode.removeChild(existing[uuid]);
            }
        });
    },

    buildLabelText: function(b, labelStyle) {
        if (!b || !b.deviceId || !b.fieldName) return '未绑定';
        var fldName = b.fieldLabel || b.fieldName;
        var hideDev = !labelStyle || labelStyle.hideDeviceName !== false;
        if (hideDev) return fldName;
        var devName = b.deviceName || b.deviceId;
        return devName + '.' + fldName;
    },

    getShapeTopAnchor: function(shape) {
        var coor = shape.coor;
        if (!coor) return null;
        if (shape.type === 1) {
            var a = coor[0], b = coor[1];
            return { x: (a[0] + b[0]) / 2, y: Math.min(a[1], b[1]) };
        }
        if (shape.type === 3) {
            var r = (this.cs && this.cs.ctrlRadius != null) ? this.cs.ctrlRadius
                  : (shape.ctrlRadius != null ? shape.ctrlRadius
                  : ((this.globalStyle && this.globalStyle.ctrlRadius) || 3));
            return { x: coor[0], y: coor[1] - r };
        }
        if (shape.type === 2 || shape.type === 4) {
            var xs = coor.map(function(p) { return p[0]; });
            var ys = coor.map(function(p) { return p[1]; });
            return { x: (Math.min.apply(null, xs) + Math.max.apply(null, xs)) / 2, y: Math.min.apply(null, ys) };
        }
        return null;
    },

    // ========================================================
    // 加载场景
    // ========================================================
    loadScene: function(record) {
        var me = this;
        if (!me.cs) { Ext.defer(function() { me.loadScene(record); }, 100, me); return; }

        me.resetToSelectMode();

        if (!record) { me.currentRecord = null; me.clearScene(); return; }
        me.currentRecord = record;
        me.clearScene();

        Ext.MessageBox.show({ msg: '正在加载场景配置...', progressText: '加载中',
                              width: 300, wait: true, waitConfig: { interval: 200 } });
        Ext.Ajax.request({
            url: context + '/sceneModelController/getSceneModelConfig',
            method: 'POST',
            params: { id: record.get('modelId') || record.get('id') },
            success: function(resp) {
                Ext.MessageBox.hide();
                try {
                    var result = Ext.decode(resp.responseText);
                    if (result.success) {
                        var cfg = result.config;
                        if (typeof cfg === 'string') {
                            try { cfg = JSON.parse(cfg); } catch (e) { cfg = null; }
                        }
                        if (!cfg) cfg = { imageUrl: '', shapes: [] };
                        me.renderSceneConfig(cfg);
                    } else {
                        Ext.Msg.alert('加载失败', result.message || '未知错误');
                    }
                } catch (e) { Ext.Msg.alert('错误', '服务器返回数据异常'); }
            },
            failure: function() { Ext.MessageBox.hide(); Ext.Msg.alert('错误', '网络请求失败'); }
        });
    },

    clearScene: function() {
        var me = this;
        me.bindingMap.clear();
        me.labelStyleMap.clear();
        me.userColorMap.clear();
        me.userLineWidthMap.clear();
        me.lastSelectedUuid = null;
        me.imageUrl = '';
        me.resetStylePanel();
        if (me.cs) me.safeSetData([]);
        if (me.labelLayer) me.labelLayer.innerHTML = '';
        me.refreshBindingTree();
    },

    resetStylePanel: function() {
        var me = this;
        try {
            var panel = me.down('panel[region=east]');
            if (!panel) return;

            me._suppressStyleChange = true;
            try {
                me.setColorFieldValue('strokeStyle', '#00FF00', 100);
                me.setColorFieldValue('fillStyle',   '#0000FF', 25);

                var lwField = panel.down('#lineWidth');
                if (lwField) lwField.setValue(1);
                var crField = panel.down('#ctrlRadius');
                if (crField) crField.setValue(3);

                if (me.cs) me.cs.ctrlRadius = 3;

                var fontField = panel.down('#labelFont');
                if (fontField) fontField.setValue('12px');

                me.setColorFieldValue('labelBg',   '#4A6CF7', 100);
                me.setColorFieldValue('labelText', '#FFFFFF', 100);

                var hideDevField = panel.down('#hideDeviceName');
                if (hideDevField) hideDevField.setValue(true);
                var hideField = panel.down('#hideLabel');
                if (hideField) hideField.setValue(false);
                var upField = panel.down('#labelUp');
                if (upField) upField.setValue(true);
            } finally {
                me._suppressStyleChange = false;
            }
        } catch (e) {
            me._suppressStyleChange = false;
        }
    },

    renderSceneConfig: function(cfg) {
        var me = this;
        if (!cfg) cfg = { imageUrl: '', shapes: [] };

        if (cfg.defaultLabelStyle) Ext.apply(me.defaultLabelStyle, cfg.defaultLabelStyle);
        if (cfg.globalStyle) Ext.apply(me.globalStyle, cfg.globalStyle);

        try { me.syncStylePanelFromState(); } catch (e) {}

        var pointRadius = null;
        (cfg.shapes || []).forEach(function(s) {
            var tt = parseInt(s.type, 10);
            if (tt === 3) {
                var r = (s.ctrlRadius != null && !isNaN(s.ctrlRadius)) ? parseInt(s.ctrlRadius, 10)
                      : ((s.radius != null && !isNaN(s.radius)) ? parseInt(s.radius, 10) : null);
                if (r != null) pointRadius = r;
            }
        });
        if (pointRadius != null && me.cs) {
            me.cs.ctrlRadius = pointRadius;
            me.globalStyle.ctrlRadius = pointRadius;
            var panel = me.down('panel[region=east]');
            if (panel) {
                var crField = panel.down('#ctrlRadius');
                if (crField) crField.setValue(pointRadius);
            }
        }

        if (cfg.imageUrl) me.setCanvasImage(cfg.imageUrl);
        else me.setCanvasImage(me.BLANK_IMAGE);

        var shapes = cfg.shapes || [];
        shapes.forEach(function(s) {
            me.userColorMap.set(s.uuid, s.strokeStyle || '#00FF00');
            me.userLineWidthMap.set(s.uuid, s.lineWidth != null ? s.lineWidth : 1);
            if (s.deviceId && s.fieldName) {
                me.bindingMap.set(s.uuid, {
                    deviceId: s.deviceId, deviceName: s.deviceName,
                    fieldName: s.fieldName, fieldLabel: s.fieldLabel,
                    unit: s.unit, dataSource: s.dataSource, currentValue: s.currentValue
                });
            }
            if (s.labelStyle) me.labelStyleMap.set(s.uuid, s.labelStyle);
        });

        var pureShapes = [];
        shapes.forEach(function(s) {
            if (!s || !s.uuid) return;
            if (s.type == null) return;
            if (!s.coor) return;

            var t = parseInt(s.type, 10);
            if (isNaN(t)) return;

            var coor = s.coor;
            if (t === 1) {
                if (!Ext.isArray(coor) || coor.length < 2) return;
                var a = coor[0], b = coor[1];
                if (!Ext.isArray(a) || !Ext.isArray(b)) return;
                if (a.length < 2 || b.length < 2) return;
                if (isNaN(a[0]) || isNaN(a[1]) || isNaN(b[0]) || isNaN(b[1])) return;
            } else if (t === 2 || t === 4) {
                if (!Ext.isArray(coor) || coor.length < 2) return;
                for (var i = 0; i < coor.length; i++) {
                    var p = coor[i];
                    if (!Ext.isArray(p) || p.length < 2) return;
                    if (isNaN(p[0]) || isNaN(p[1])) return;
                }
            } else if (t === 3 || t === 5) {
                if (!Ext.isArray(coor) || coor.length < 2) return;
                if (isNaN(coor[0]) || isNaN(coor[1])) return;
            } else return;

            var userLw = (s.lineWidth != null && !isNaN(s.lineWidth)) ? parseInt(s.lineWidth, 10) : 1;
            var userStroke = me.normalizeColor(s.strokeStyle, '#00FF00');
            var canvasLw = (userLw <= 0) ? 1 : userLw;
            var canvasStroke = (userLw <= 0) ? me.TRANSPARENT_STROKE : userStroke;
            var fillStyle = me.normalizeColor(s.fillStyle, 'rgba(0,0,255,0.25)');

            var cr = (s.ctrlRadius != null && !isNaN(s.ctrlRadius)) ? parseInt(s.ctrlRadius, 10)
                   : ((t === 3 && s.radius != null && !isNaN(s.radius)) ? parseInt(s.radius, 10) : 3);

            var shapeData = {
                uuid: s.uuid, type: t, coor: coor,
                strokeStyle: canvasStroke, fillStyle: fillStyle,
                lineWidth: canvasLw, ctrlRadius: cr,
                _userStrokeStyle: userStroke, _userLineWidth: userLw
            };
            if (t === 3) shapeData._userCtrlRadius = cr;
            pureShapes.push(shapeData);
        });

        me._lastDataKey = null;
        me.safeSetData(pureShapes);

        me.refreshBindingTree();

        // ★ 加载完成后：派发一次鼠标移动到画布中心，让 canvas-select 内部初始化
        //   然后计算 origin、重绘标签；并延时兜底再适应一次
        Ext.defer(function() {
            me._nudgeCanvasMouseCenter();
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();

            Ext.defer(function() {
                if (!me.cs) return;
                me._nudgeCanvasMouseCenter();
                me.updateOriginIfNeeded(true);
                me.scheduleRenderLabels();
            }, 200, me);
        }, 100, me);
    },

    syncStylePanelFromState: function() {
        var me = this;
        try {
            var gs = me.globalStyle || {};
            var ls = me.defaultLabelStyle || {};

            me._suppressStyleChange = true;
            try {
                me.setColorFieldValue('strokeStyle', gs.strokeStyle || '#00FF00', 100);
                me.setColorFieldValue('fillStyle',   gs.fillColor   || '#0000FF',
                    gs.fillOpacity != null ? gs.fillOpacity : 25);
                me.setColorFieldValue('labelBg',     ls.bgColor     || '#4A6CF7',
                    ls.bgOpacity != null ? ls.bgOpacity : 100);
                me.setColorFieldValue('labelText',   ls.textColor   || '#FFFFFF', 100);

                var panel = me.down('panel[region=east]');
                if (panel) {
                    var lwField = panel.down('#lineWidth');
                    if (lwField) lwField.setValue(gs.lineWidth != null ? gs.lineWidth : 1);
                    var crField = panel.down('#ctrlRadius');
                    if (crField) crField.setValue(gs.ctrlRadius != null ? gs.ctrlRadius : 3);
                    var fontField = panel.down('#labelFont');
                    if (fontField) fontField.setValue(ls.font || '12px');
                    var hideDevField = panel.down('#hideDeviceName');
                    if (hideDevField) hideDevField.setValue(ls.hideDeviceName !== false);
                    var hideField = panel.down('#hideLabel');
                    if (hideField) hideField.setValue(!!ls.hide);
                    var upField = panel.down('#labelUp');
                    if (upField) upField.setValue(ls.up !== false);
                }
            } finally {
                me._suppressStyleChange = false;
            }
        } catch (e) {
            me._suppressStyleChange = false;
        }
    },

    getConfig: function() {
        var me = this;
        var dataset = (me.cs && me.cs.dataset) ? me.cs.dataset : [];
        return {
            imageUrl: me.imageUrl || '',
            defaultLabelStyle: Ext.apply({}, me.defaultLabelStyle),
            globalStyle: Ext.apply({}, me.globalStyle),
            shapes: dataset.map(function(s) {
                var binding = me.bindingMap.get(s.uuid) || {};
                var labelStyle = me.labelStyleMap.get(s.uuid) || me.defaultLabelStyle;

                var userColor = me.userColorMap.get(s.uuid)
                              || s._userStrokeStyle || s.strokeStyle || '#00FF00';
                if (userColor === me.TRANSPARENT_STROKE) {
                    userColor = me.globalStyle.strokeStyle || '#00FF00';
                }
                var userLw = me.userLineWidthMap.get(s.uuid);
                if (userLw == null) userLw = (s._userLineWidth != null ? s._userLineWidth
                                            : (s.lineWidth != null ? s.lineWidth : 1));

                var cr;
                if (s.type === 3) {
                    cr = (me.cs && me.cs.ctrlRadius != null) ? me.cs.ctrlRadius : 3;
                } else {
                    cr = s.ctrlRadius != null ? s.ctrlRadius : 3;
                }

                return {
                    uuid: s.uuid, type: s.type, coor: s.coor,
                    strokeStyle: userColor, fillStyle: s.fillStyle,
                    lineWidth: userLw, ctrlRadius: cr,
                    labelStyle: Ext.apply({}, labelStyle),
                    deviceId: binding.deviceId, deviceName: binding.deviceName,
                    fieldName: binding.fieldName, fieldLabel: binding.fieldLabel,
                    unit: binding.unit, dataSource: binding.dataSource,
                    currentValue: binding.currentValue
                };
            })
        };
    },

    resetPanel: function() {
        var me = this;
        me.currentRecord = null;
        me.bindingMap.clear();
        me.labelStyleMap.clear();
        me.userColorMap.clear();
        me.userLineWidthMap.clear();
        me.lastSelectedUuid = null;
        me.imageUrl = '';
        me.resetStylePanel();
        if (me.cs) me.safeSetData([]);
        if (me.labelLayer) me.labelLayer.innerHTML = '';
        me.refreshBindingTree();
    },

    onCreateScene: function() {
        var selectedOrgId = "";
        var IframeViewStore = Ext.getCmp("IframeView_Id").getStore();
        var count = IframeViewStore.getCount();
        var IframeViewSelection = Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
        if (IframeViewSelection.length > 0) selectedOrgId = IframeViewSelection[0].data.orgId;
        else if (count > 0) selectedOrgId = IframeViewStore.getAt(0).data.orgId;

        var win = Ext.create("AP.view.sceneModel.SceneModelInfoWindow");
        win.show();
        Ext.getCmp("addSceneModelOrg_Id").setValue(selectedOrgId);
    },

    onDeleteScene: function() {
        var selectModelId = [];
        var selectModelNameList = [];
        var sceneModelGridView = Ext.getCmp("SceneModelGridView_Id");
        var selectionModel = sceneModelGridView.getSelectionModel();
        var selectionRecord = selectionModel.getSelection();
        if (selectionRecord.length > 0) {
            selectionRecord.forEach(function(record) {
                selectModelId.push(record.data.modelId);
                var selectModelName = '';
                if (loginUserLanguage.toUpperCase() == 'ZH_CN') selectModelName = record.data.name_zh_CN;
                else if (loginUserLanguage.toUpperCase() == 'EN') selectModelName = record.data.name_en;
                else if (loginUserLanguage.toUpperCase() == 'RU') selectModelName = record.data.name_ru;
                selectModelNameList.push(selectModelName);
            });

            var deleteInfo = loginUserLanguageResource.confirmDelete;
            if (selectModelId.length == 1) {
                deleteInfo = loginUserLanguageResource.name + ":<font color=red>" + selectModelNameList[0] + "</font>"
                    + "</br>" + loginUserLanguageResource.confirmDelete;
            } else {
                deleteInfo = loginUserLanguageResource.sparseRecordCount + ":<font color=red>" + selectModelId.length + "</font>"
                    + "</br>" + loginUserLanguageResource.confirmDelete;
            }

            Ext.Msg.confirm(loginUserLanguageResource.tip, deleteInfo, function (btn) {
                if (btn == "yes") {
                    Ext.Ajax.request({
                        method: 'POST',
                        url: context + '/sceneModelController/deleteSceneModel',
                        success: function(response) {
                            var data = Ext.JSON.decode(response.responseText);
                            if (data.success) {
                                Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
                                Ext.getCmp("SceneModelGridView_Id").getStore().load();
                            } else {
                                Ext.MessageBox.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.deleteFailed + "</font>");
                            }
                        },
                        failure: function() {
                            Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.requestFailed);
                        },
                        params: { ids: selectModelId.join(",") }
                    });
                }
            });
        } else {
            Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
        }
    },

    onSaveScene: function() {
        var grid = Ext.getCmp("SceneModelGridView_Id");
        var store = grid.getStore();
        var modifiedRecords = store.getModifiedRecords();
        var modifiedModels = [];
        modifiedRecords.forEach(function(record) {
            var modified = {};
            modified.id = record.data.modelId;
            modified.name_zh_CN = record.data.name_zh_CN;
            modified.name_en = record.data.name_en;
            modified.name_ru = record.data.name_ru;
            if (isNotVal(record.data.sort) && isNumber(record.data.sort)) modified.sort = record.data.sort;
            modifiedModels.push(modified);
        });

        if (modifiedModels.length > 0) {
            Ext.Ajax.request({
                method: 'POST',
                url: context + '/sceneModelController/saveSceneModelInfo',
                success: function(response) {
                    var data = Ext.JSON.decode(response.responseText);
                    if (data.success) {
                        Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
                        store.commitChanges();
                        Ext.getCmp("SceneModelGridView_Id").getStore().load();
                    } else {
                        Ext.MessageBox.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.saveFailed + "</font>");
                    }
                },
                failure: function() {
                    Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.requestFailed);
                },
                params: { data: JSON.stringify(modifiedModels) }
            });
        } else {
            Ext.MessageBox.alert(loginUserLanguageResource.tip, loginUserLanguageResource.noDataChange);
        }
    },

    onExportScenes: function() {
        var grid = Ext.getCmp("SceneModelGridView_Id");
        if (!isNotVal(grid)) return;
        var data = grid.getStore().getRange().map(function(r) { return r.getData(); });
        var blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
        var url = URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = 'scenes-' + Date.now() + '.json';
        a.click();
        URL.revokeObjectURL(url);
    },

    onImportScenes: function() {
        var input = document.createElement('input');
        input.type = 'file'; input.accept = '.json';
        input.onchange = function(e) {
            var file = e.target.files[0];
            if (!file) return;
            var reader = new FileReader();
            reader.onload = function(ev) {
                try {
                    var list = JSON.parse(ev.target.result);
                    Ext.Ajax.request({
                        url: context + '/sceneModelController/saveSceneModelList',
                        method: 'POST',
                        jsonData: { list: list },
                        success: function() {
                            var grid = Ext.getCmp("SceneModelGridView_Id");
                            if (isNotVal(grid)) grid.getStore().load();
                        }
                    });
                } catch (err) { Ext.Msg.alert('错误', '文件格式不正确'); }
            };
            reader.readAsText(file);
        };
        input.click();
    },

    extractUuid: function(info) {
        if (!info) return null;
        if (info.uuid) return info.uuid;
        if (info.index !== undefined && this.cs && this.cs.dataset && this.cs.dataset[info.index]) {
            return this.cs.dataset[info.index].uuid;
        }
        return null;
    },

    parseFillStyle: function(fillStyle) {
        if (!fillStyle) return { hex: '#0000FF', alpha: 25 };
        var m = fillStyle.match(/rgba?\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*(?:,\s*([\d.]+))?\s*\)/i);
        if (m) {
            var hex = '#' + [m[1], m[2], m[3]].map(function(n) { return (+n).toString(16).padStart(2, '0'); }).join('');
            var alpha = m[4] != null ? Math.round(parseFloat(m[4]) * 100) : 100;
            return { hex: hex, alpha: alpha };
        }
        if (/^#[0-9a-f]{8}$/i.test(fillStyle)) {
            return { hex: fillStyle.slice(0, 7),
                     alpha: Math.round(parseInt(fillStyle.slice(7, 9), 16) / 255 * 100) };
        }
        if (/^#[0-9a-f]{6}$/i.test(fillStyle)) return { hex: fillStyle, alpha: 100 };
        if (/^#[0-9a-f]{3}$/i.test(fillStyle)) {
            return { hex: '#' + fillStyle[1] + fillStyle[1] + fillStyle[2] + fillStyle[2] + fillStyle[3] + fillStyle[3],
                     alpha: 100 };
        }
        return { hex: '#0000FF', alpha: 25 };
    },

    // ========================================================
    // ★ beforeDestroy
    // ========================================================
    beforeDestroy: function() {
        if (this._originTimer) { clearInterval(this._originTimer); this._originTimer = null; }
        if (this._resizeTimer) { clearTimeout(this._resizeTimer); this._resizeTimer = null; }

        try { Ext.EventManager.removeResizeListener(this._scheduleResize, this); } catch (e) {}

        if (this._visibilityHandler) {
            try { document.removeEventListener('visibilitychange', this._visibilityHandler); } catch (e) {}
            this._visibilityHandler = null;
        }

        try { if (this.cs && typeof this.cs.destroy === 'function') this.cs.destroy(); } catch (e) {}
        this.cs = null;

        this.callParent(arguments);
    }
});