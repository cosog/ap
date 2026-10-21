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

    // ★ 销毁标志，阻止异步回调再操作已销毁的组件
    _destroyed: false,
    // ★ 场景加载请求序号，过滤过期响应（快速切换场景时）
    _loadSceneSeq: 0,

    defaultLabelStyle: {
        font: '12px', bgColor: '#4A6CF7', bgOpacity: 100,
        textColor: '#FFFFFF', hide: false, hideDeviceName: true, hideFieldName: false, up: true
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
        if (me._destroyed) return;
        if (document.hidden) return;
        me._originTimer = setInterval(function() {
            if (me._destroyed) return;
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
    // ★ 工具：清空所有绑定相关 Map
    // ========================================================
    _clearAllBindingMaps: function() {
        var me = this;
        if (me.bindingMap)       me.bindingMap.clear();
        if (me.labelStyleMap)    me.labelStyleMap.clear();
        if (me.userColorMap)     me.userColorMap.clear();
        if (me.userLineWidthMap) me.userLineWidthMap.clear();
    },

    // ========================================================
    // ★ 关键：主动派发一次"鼠标移到画布中心"的合成 mousemove
    // ========================================================
    _nudgeCanvasMouseCenter: function() {
        var me = this;
        if (me._destroyed) return;
        var canvasEl = me.getCanvasEl();
        if (!canvasEl) return;

        var rect = canvasEl.getBoundingClientRect();
        if (rect.width < 10 || rect.height < 10) return;

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
        } catch (e) {
            try {
                ev = document.createEvent('MouseEvents');
                ev.initMouseEvent('mousemove', true, true, window,
                    0, 0, 0, clientX, clientY, false, false, false, false, 0, null);
            } catch (e2) { return; }
        }

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
        
        var styleLabelWidth = 90;
        var styleLabels = [
            loginUserLanguageResource.borderColor,
            loginUserLanguageResource.borderWidth,
            loginUserLanguageResource.fillColor,
            loginUserLanguageResource.ctrlPointSize,
            loginUserLanguageResource.font,
            loginUserLanguageResource.labelBg,
            loginUserLanguageResource.textColor,
            loginUserLanguageResource.labelOnTop,
            loginUserLanguageResource.hideDeviceName,
            loginUserLanguageResource.hideFieldName,
            loginUserLanguageResource.hideLabel
        ];
        for (var i = 0; i < styleLabels.length; i++) {
            if (styleLabels[i]) {
                var w = getLabelWidth(styleLabels[i], loginUserLanguage);
                if (w > styleLabelWidth) {
                    styleLabelWidth = w;
                }
            }
        }
        // 稍微加一点余量，避免文字紧贴输入框
        //styleLabelWidth += 10;

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
                        hidden:true,
                        handler: function() { me.onExportScenes(); }
                    }, "-", {
                        xtype: 'button', text: loginUserLanguageResource.importData, iconCls: 'import',
                        disabled: loginUserSceneModelRight.editFlag != 1,
                        hidden:true,
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
                        { xtype: 'button', text: '✋ '+loginUserLanguageResource.select, itemId: 'btnSelect',
                          enableToggle: true, pressed: true, toggleGroup: 'createType',
                          handler: function(b) {
                              if (b.pressed && me.cs) {
                                  me.cs.createType = 0;
                                  me._userCreateType = 0;
                              }
                          } },
                        { xtype: 'button', text: '▭ '+loginUserLanguageResource.rectangle, itemId: 'btnRect',
                          enableToggle: true, toggleGroup: 'createType',
                          handler: function(b) {
                              if (b.pressed && me.cs) {
                                  me.cs.createType = 1;
                                  me._userCreateType = 1;
                                  // ★ 切到创建模式时清空选中，避免误改之前选中的形状，并恢复默认面板
                                  me.lastSelectedUuid = null;
                                  me.resetStylePanel();
                              }
                          } },
                        { xtype: 'button', text: '• '+loginUserLanguageResource.point, itemId: 'btnPoint',
                          enableToggle: true, toggleGroup: 'createType', tooltip: loginUserLanguageResource.addPointTooltip,
                          handler: function(b) {
                              if (b.pressed && me.cs) {
                                  me.cs.createType = 3;
                                  me._userCreateType = 3;
                                  // ★ 切到创建模式时清空选中，避免误改之前选中的形状，并恢复默认面板
                                  me.lastSelectedUuid = null;
                                  me.resetStylePanel();
                              }
                          } },
                        '-',
                        { xtype: 'button', text: '🔍+', tooltip: loginUserLanguageResource.zoomIn,
                          handler: function() { if (me.cs) { me.cs.setScale(true); me.afterZoom(); } } },
                        { xtype: 'button', text: '🔍−', tooltip: loginUserLanguageResource.zoomOut,
                          handler: function() { if (me.cs) { me.cs.setScale(false); me.afterZoom(); } } },
                        { xtype: 'button', text: loginUserLanguageResource.fitZoom,
                          handler: function() { if (me.cs) { me.cs.fitZoom(); me.afterZoom(); } } },
                        '-',
                        { xtype: 'button', text: '🖼️ '+loginUserLanguageResource.selectServerImage,
                          handler: function() { me.onSelectServerImage(); } },
                        { xtype: 'button', text: '📤 '+loginUserLanguageResource.uploadLocalImage,
                          handler: function() { me.onUploadLocalImage(); } },
                        '->',
                        { xtype: 'button', text: loginUserLanguageResource.saveAnnotations,iconCls: 'save',
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
                                border: '2px dashed #dee6e6', borderRadius: '8px',
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
                            title: loginUserLanguageResource.styleAndAnnotations	,
                            layout: 'border',
                            border: false,
                            items: [
                                {
                                    region: 'north',
                                    height: '50%',
                                    split: true,
                                    collapsible: true,
                                    title: loginUserLanguageResource.styleSettings,
                                    itemId: 'styleFormPanel',
                                    autoScroll: true,
                                    bodyPadding: 12,
                                    border: false,
                                    items: [{
                                        xtype: 'fieldset',
                                        title: loginUserLanguageResource.graphicStyle,
                                        defaults: { anchor: '100%', labelWidth: styleLabelWidth, margin: '6 0' },
                                        items: [
                                            { xtype: 'colorfield', fieldLabel: loginUserLanguageResource.borderColor, itemId: 'strokeStyle',
                                              listeners: { collapse: function (field) { me.applyColorFieldVisual(field, 1); me.onGraphicStyleChange(); } } },
                                            { xtype: 'numberfield', fieldLabel: loginUserLanguageResource.borderWidth, itemId: 'lineWidth',
                                              value: 1, minValue: 0, maxValue: 10, allowDecimals: false,
                                              listeners: { change: function() { me.onGraphicStyleChange(); } } },
                                            { xtype: 'colorfield', fieldLabel: loginUserLanguageResource.fillColor, itemId: 'fillStyle',
                                              listeners: { collapse: function (field) { me.applyColorFieldVisual(field, null); me.onGraphicStyleChange(); } } },
                                            { xtype: 'numberfield', fieldLabel: loginUserLanguageResource.ctrlPointSize, itemId: 'ctrlRadius',
                                              value: 3, minValue: 2, maxValue: 10, allowDecimals: false,
                                              tooltip: loginUserLanguageResource.ctrlRadiusTooltip,
                                              listeners: { change: function() { me.onGraphicStyleChange(); } } }
                                        ]
                                    }, {
                                        xtype: 'fieldset',
                                        title: loginUserLanguageResource.labelStyle,
                                        defaults: { anchor: '100%', labelWidth: styleLabelWidth, margin: '6 0' },
                                        items: [
                                            { xtype: 'combo', fieldLabel: loginUserLanguageResource.font, itemId: 'labelFont',
                                              store: ['11px', '12px', '13px', '14px'], value: '12px', editable: false, matchFieldWidth: true,
                                              listeners: { change: function() { me.onLabelStyleChange(); } } },
                                            { xtype: 'colorfield', fieldLabel: loginUserLanguageResource.labelBg, itemId: 'labelBg',
                                              listeners: { collapse: function (field) { me.applyColorFieldVisual(field, null); me.onLabelStyleChange(); } } },
                                            { xtype: 'colorfield', fieldLabel: loginUserLanguageResource.textColor, itemId: 'labelText',
                                              listeners: { collapse: function (field) { me.applyColorFieldVisual(field, 1); me.onLabelStyleChange(); } } },
                                            { xtype: 'checkbox', fieldLabel: loginUserLanguageResource.labelOnTop, itemId: 'labelUp', value: true,
                                              listeners: { change: function() { me.onLabelStyleChange(); } } },
                                            { xtype: 'checkbox', fieldLabel: loginUserLanguageResource.hideDeviceName, itemId: 'hideDeviceName', value: true,
                                              listeners: { change: function() { me.onLabelStyleChange(); } } },
                                            { xtype: 'checkbox', fieldLabel: loginUserLanguageResource.hideFieldName, itemId: 'hideFieldName',
                                              listeners: { change: function() { me.onLabelStyleChange(); } } },
                                            { xtype: 'checkbox', fieldLabel: loginUserLanguageResource.hideLabel, itemId: 'hideLabel', hidden:true,
                                              listeners: { change: function() { me.onLabelStyleChange(); } } }
                                        ]
                                    }]
                                },
                                {
                                    region: 'center',
                                    title: loginUserLanguageResource.annotationList,
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
                                        emptyText: '<div style="padding:30px;color:#aaa;text-align:center;">'+loginUserLanguageResource.emptyMsg+'</div>',
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
                if (me._destroyed) return;
                me.initCanvasSelect();
                Ext.defer(function() {
                    if (me._destroyed) return;
                    me.resetStylePanel();
                }, 50, me);

                Ext.defer(function() {
                    if (me._destroyed) return;
                    me.fixStylePanelLayout();
                }, 80, me);
                Ext.defer(function() {
                    if (me._destroyed) return;
                    me.fixStylePanelLayout();
                }, 350, me);

                me._bindResizeSources();
            }, 100, me);
        });

        me.on('boxready', function() {
            Ext.defer(function() {
                if (me._destroyed) return;
                me.fixStylePanelLayout();
            }, 60, me);
        });

        me.mon(me, 'resize', me._scheduleResize, me);
    },

    // ========================================================
    // ★ 把画布切回"选择"模式
    // ========================================================
    resetToSelectMode: function() {
        var me = this;
        if (me._destroyed) return;
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
        if (me._destroyed) return;

        Ext.EventManager.onWindowResize(me._scheduleResize, me);

        var cfgPanel = me.down('#SceneModelConfigPanel_Id');
        if (cfgPanel) {
            me.mon(cfgPanel, 'resize', me._scheduleResize, me);
        }

        var east = me.down('panel[region=east]');
        if (east) {
            me.mon(east, 'resize', me._scheduleResize, me);
            me.mon(east, 'collapse', function() {
                Ext.defer(function() {
                    if (me._destroyed) return;
                    me.handleResize();
                }, 120, me);
                Ext.defer(function() {
                    if (me._destroyed) return;
                    me.handleResize();
                }, 380, me);
            });
            me.mon(east, 'expand', function() {
                Ext.defer(function() {
                    if (me._destroyed) return;
                    me.handleResize();
                }, 120, me);
                Ext.defer(function() {
                    if (me._destroyed) return;
                    me.handleResize();
                }, 380, me);
            });
        }

        var west = me.down('panel[region=west]');
        if (west) {
            me.mon(west, 'resize', me._scheduleResize, me);
        }
    },

    _scheduleResize: function() {
        var me = this;
        if (me._destroyed) return;
        if (me._resizeTimer) clearTimeout(me._resizeTimer);
        me._resizeTimer = setTimeout(function() {
            if (me._destroyed) return;
            me.safe(function() { me.handleResize(); }, 'handleResize');
        }, 120);
    },

    // ========================================================
    // 修正右侧样式面板的首次布局
    // ========================================================
    fixStylePanelLayout: function() {
        var me = this;
        if (me._destroyed) return;
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
        if (me._destroyed) return;
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
    clearSelectionUI: function(skipTree) {
        var me = this;
        if (me._destroyed) return;
        if (me._clearingSelection) return;
        me._clearingSelection = true;
        try {
            me.lastSelectedUuid = null;

            // ★ 如果 skipTree 为 false 或未传，才清空树的选中
            if (!skipTree) {
                var tree = me.down('#bindingTree');
                if (tree) {
                    try { tree.getSelectionModel().deselectAll(); } catch (e) {}
                }
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
                    if (me._destroyed) return;
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
        if (me._destroyed) return;
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
    refreshBindingTree: function(forceSort) {
        var me = this;
        if (me._destroyed) return;
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

        // ★ 新增：如果需要强制排序（加载场景时），按设备名称排序
        if (forceSort) {
            deviceOrder.sort(function(devKeyA, devKeyB) {
                var nameA = String(deviceMap[devKeyA].text || '').toLowerCase();
                var nameB = String(deviceMap[devKeyB].text || '').toLowerCase();
                if (nameA < nameB) return -1;
                if (nameA > nameB) return 1;
                return 0;
            });
        }

        deviceOrder.forEach(function(devKey) {
            root.appendChild(deviceMap[devKey]);
        });

        if (me.lastSelectedUuid) {
            me.selectTreeNodeByUuid(me.lastSelectedUuid);
        }
    },

    selectTreeNodeByUuid: function(uuid) {
        var me = this;
        if (me._destroyed) return;
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
        if (me._destroyed) return;
        if (!record) return;

        if (record.isLeaf()) {
            // 叶子节点：对应具体的标注，选中画布图形
            var uuid = record.get('uuid');
            if (!uuid) return;
            me.selectShapeByUuid(uuid);
        } else {
            // ★ 新增：非叶子节点（设备节点），切回“选择”模式，取消矩形/点高亮
            if (me.cs && me.cs.createType !== 0) {
                me.resetToSelectMode();
            }
            // 如果当前画布有选中的标注，清空画布的选中高亮和右侧样式面板，但保留当前点击的设备节点的选中态
            if (me.lastSelectedUuid) {
                me.clearSelectionUI(true); // 传入 true 表示 skipTree
            }
        }
    },

    // ========================================================
    // 根据 uuid 选中形状
    // ========================================================
    selectShapeByUuid: function(uuid) {
        var me = this;
        if (me._destroyed) return;
        if (!me.cs || !me.cs.dataset || !uuid) return;

        // ★ 关键：如果在矩形或点模式下点击了右侧树，自动切回“选择”模式，并取消矩形/点按钮的选中状态
        if (me.cs.createType !== 0) {
            me.resetToSelectMode();
        }

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
        if (me._destroyed) return;
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
        if (me._destroyed) return;
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
        if (me._destroyed) return { hex: '#000000', alphaPercent: 100 };
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
        if (me._destroyed) return;
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
        if (me._destroyed) return;
        if (!me.cs || me._rebuilding) return;
        if (!url) url = me.BLANK_IMAGE;

        // 如果是空白图，就把记录的 imageUrl 置空
        me.imageUrl = (url === me.BLANK_IMAGE) ? '' : url;

        if (url === me._canvasCurrentUrl) return;

        if (me._settingImage) return;
        me._settingImage = true;

        // ★ 核心修复：加上时间戳，强制浏览器向服务器请求最新图片
        var loadUrl = url;
        if (loadUrl.indexOf('blob:') !== 0 && loadUrl.indexOf('data:') !== 0) {
            loadUrl += (loadUrl.indexOf('?') > -1 ? '&' : '?') + '_t=' + new Date().getTime();
        }

        var probe = new Image();
        probe.onload = function() {
            if (me._destroyed) return;
            me._settingImage = false;
            if (!me.cs) return;
            try {
                me.cs.setImage(loadUrl);
                me._canvasCurrentUrl = url;
                
                // 适配一下视图和标注
                me.updateOriginIfNeeded(true);
                me.scheduleRenderLabels();
            } catch (e) {
                me._canvasCurrentUrl = null;
                me.rebuildCanvasSelect();
            }
        };
        probe.onerror = function() {
            if (me._destroyed) return;
            me._settingImage = false;
            
            // ★ 如果加载的是非空白图（说明图被删了或路径不对），平滑降级为空白背景，不重建画布以保留标注
            if (url !== me.BLANK_IMAGE) {
                me.imageUrl = '';
                me._canvasCurrentUrl = null;
                me.setCanvasImage(me.BLANK_IMAGE);
            } else {
                // 如果连内置空白图都加载失败，只能重建画布了
                me._canvasCurrentUrl = null;
                me.rebuildCanvasSelect();
            }
        };
        probe.src = loadUrl;
    },

    rebuildCanvasSelect: function() {
        var me = this;
        if (me._destroyed) return;
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
        if (me._destroyed) return;

        var canvasCmp = me.down('#canvas');
        var layerCmp = me.down('#labelLayer');
        if (!canvasCmp || !layerCmp) return;

        me.stopOriginTimer();
        try { if (me.cs && typeof me.cs.destroy === 'function') me.cs.destroy(); } catch (e) {}
        me.cs = null;

        var canvasEl = canvasCmp.el.dom;
        me.labelLayer = layerCmp.el.dom;
        if (typeof CanvasSelect === 'undefined') { Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.canvasSelectNotLoaded); return; }

        me.cs = new CanvasSelect(canvasEl, me.BLANK_IMAGE);
        me._canvasCurrentUrl = me.BLANK_IMAGE;
        me._lastDataKey = '';
        me.cs.ctrlRadius = me.globalStyle.ctrlRadius;
        me.cs.createType = 0;
        me.cs.scrollZoom = true;
        me.cs.hideLabel = true;

        me.cs.on('load', function() {
            if (me._destroyed) return;
            try { if (typeof me.cs.fitZoom === 'function') me.cs.fitZoom(); }
            catch (e) { console.warn('[SceneModelPanel] load fitZoom 失败', e); }

            me._nudgeCanvasMouseCenter();

            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();

            Ext.defer(function() {
                if (me._destroyed) return;
                if (!me.cs) return;
                me._nudgeCanvasMouseCenter();
                me.updateOriginIfNeeded(true);
                me.scheduleRenderLabels();
            }, 150, me);
        });

        me.cs.on('add',     function(info) { if (me._destroyed) return; me.onShapeAdded(info); });

        me.cs.on('select',  function(info) {
            if (me._destroyed) return;
            if (me._clearingSelection) return;
            var uuid = me.extractUuid(info);
            if (!uuid) {
                if (me.lastSelectedUuid) me.clearSelectionUI();
            } else {
                me.onShapeSelected(info);
            }
        });

        me.cs.on('delete',  function(info) { if (me._destroyed) return; me.onShapeDeleted(info); });
        me.cs.on('updated', function()     { if (me._destroyed) return; me.scheduleRenderLabels(); });
        me.cs.on('coor',    function()     { if (me._destroyed) return; me.scheduleRenderLabels(); });

        canvasEl.addEventListener('click', function() {
            if (me._destroyed) return;
            if (!me.cs || me.cs.createType !== 0) return;
            setTimeout(function() {
                if (me._destroyed) return;
                if (!me.cs || me.cs.createType !== 0) return;
                if (me._clearingSelection) return;
                var active = me.cs.activeShape;
                if (!active && me.lastSelectedUuid) {
                    me.clearSelectionUI();
                }
            }, 50);
        });

        canvasEl.addEventListener('dblclick', function() {
            if (me._destroyed) return;
            if (!me.cs || me.cs.createType !== 0) return;
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
            if (me._destroyed) return;
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
        if (me._destroyed) return;
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

    // ========================================================
    // ★ 新标注添加回调
    //     - 延迟应用样式，防止覆盖坐标
    //     - 强制重置默认标签样式，防止透明度污染
    // ========================================================
    onShapeAdded: function(info) {
        var me = this;
        if (me._destroyed) return;
        var uuid = me.extractUuid(info);
        if (!uuid) return;

        var DEFAULT_STROKE      = '#00FF00';
        var DEFAULT_LINE_WIDTH  = 1;
        var DEFAULT_FILL_STYLE  = 'rgba(0,0,255,0.25)';
        var DEFAULT_CTRL_RADIUS = 3;

        // ★ 强制重置默认标签样式
        me.defaultLabelStyle = {
        	font: '12px', bgColor: '#4A6CF7', bgOpacity: 100,
            textColor: '#FFFFFF', hide: false, hideDeviceName: true, hideFieldName: false, up: true
        };

        me.userColorMap.set(uuid, DEFAULT_STROKE);
        me.userLineWidthMap.set(uuid, DEFAULT_LINE_WIDTH);

        Ext.defer(function() {
            if (me._destroyed) return;
            if (!me.cs) return;

            var newShape = null;
            if (Ext.isArray(me.cs.dataset)) {
                for (var i = 0; i < me.cs.dataset.length; i++) {
                    if (me.cs.dataset[i].uuid === uuid) { newShape = me.cs.dataset[i]; break; }
                }
            }
            
            if (newShape) {
                newShape.strokeStyle      = DEFAULT_STROKE;
                newShape.lineWidth        = DEFAULT_LINE_WIDTH;
                newShape.fillStyle        = DEFAULT_FILL_STYLE;
                newShape._userStrokeStyle = DEFAULT_STROKE;
                newShape._userLineWidth   = DEFAULT_LINE_WIDTH;
                newShape.ctrlRadius       = DEFAULT_CTRL_RADIUS;
                if (newShape.type === 3) {
                    newShape._userCtrlRadius = DEFAULT_CTRL_RADIUS;
                }
            }

            me.cs.strokeStyle = DEFAULT_STROKE;
            me.cs.fillStyle   = DEFAULT_FILL_STYLE;
            me.cs.lineWidth   = DEFAULT_LINE_WIDTH;
            me.cs.ctrlRadius  = DEFAULT_CTRL_RADIUS;

            me.resetStylePanel();

            me.lastSelectedUuid = uuid;

            me._lastDataKey = null;
            try {
                if (typeof me.cs.update === 'function') me.cs.update();
            } catch (e) {}
            me.scheduleRenderLabels();

            me.openDataSourceWindow(uuid, false);
        }, 150); 
    },

    onShapeDeleted: function(info) {
        var me = this;
        if (me._destroyed) return;

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
        if (me._destroyed) return;
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
            crVal = shape.ctrlRadius != null ? shape.ctrlRadius
                  : (me.cs.ctrlRadius != null ? me.cs.ctrlRadius : 3);
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
            
            var hideFldField = panel.down('#hideFieldName');
            if (hideFldField) hideFldField.setValue(labelStyle.hideFieldName === true);

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
        if (me._destroyed) return;
        Ext.defer(function() {
            if (me._destroyed) return;
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        }, 30, me);
    },

    // ========================================================
    // 选择场景图片（包含删除功能及保护机制）
    // ========================================================
    onSelectServerImage: function() {
        var me = this;
        if (me._destroyed) return;
        var imageStore = Ext.create('Ext.data.Store', {
            fields: ['name', 'url', 'size', 'lastModified'],
            proxy: { type: 'ajax', url: context + '/sceneModelController/getSceneImageList',
                     reader: { type: 'json', rootProperty: 'data' } },
            autoLoad: true
        });

        var win = Ext.create('Ext.window.Window', {
            title: loginUserLanguageResource.selectSceneImage, width: 820, height: 580,
            modal: true, layout: 'fit', closeAction: 'destroy',
            items: [{
                xtype: 'panel', layout: 'fit',
                tbar: [
                    { xtype: 'textfield', itemId: 'searchField', emptyText: loginUserLanguageResource.searchFileName+'...', width: 220,
                      listeners: { change: function(f, v) { win.filterImages(v); } } },
                    '->',
                    { xtype: 'button', text: '📤 '+loginUserLanguageResource.uploadLocalImage, handler: function() { win.doUpload(); } },
                    // ★ 新增：删除选中的图片按钮（默认禁用）
                    { xtype: 'button', itemId: 'btnDeleteImg', text: loginUserLanguageResource.deleteImage, iconCls: 'delete', disabled: true, tooltip: loginUserLanguageResource.deleteSelectedImgTip, handler: function() { win.doDelete(); } },
                    { xtype: 'button', text: loginUserLanguageResource.refresh, iconCls: 'note-refresh',
                      handler: function() {
                          imageStore.reload({ callback: function() {
                              win.allImages = imageStore.getRange().map(function(r) { return r.getData(); });
                              Ext.defer(function() { win.selectCurrentImage(); }, 50);
                          }});
                      } }
                ],
                items: [{
                    xtype: 'dataview', itemId: 'imageView',
                    itemSelector: 'div.scene-image-item',
                    overItemCls: 'scene-image-item-over',
                    selectedItemCls: 'scene-image-item-selected',
                    emptyText: '<div style="padding:40px;color:#aaa;text-align:center;">'+loginUserLanguageResource.noImages+'</div>',
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
                    listeners: { 
                        itemdblclick: function(view, record) { win.doSelect(record.get('url')); },
                        // ★ 核心新增：监听选中状态变化，控制删除按钮的可用性
                        selectionchange: function(view, selections) {
                            var btnDelete = win.down('#btnDeleteImg');
                            if (!btnDelete) return;
                            
                            if (selections.length === 0) {
                                btnDelete.setDisabled(true);
                                btnDelete.setTooltip(loginUserLanguageResource.selectImgFirstTip);
                                return;
                            }
                            var selectedUrl = selections[0].get('url');
                            
                            // 如果选中的图正是当前场景正在使用的图
                            if (me.imageUrl && selectedUrl === me.imageUrl) {
                                btnDelete.setDisabled(true);
                                btnDelete.setTooltip(loginUserLanguageResource.cannotDeleteUsedImgTip);
                            } else {
                                btnDelete.setDisabled(false);
                                btnDelete.setTooltip(loginUserLanguageResource.deleteSelectedImgTip);
                            }
                        }
                    }
                }]
            }],
            buttons: [
                { text: loginUserLanguageResource.cancel, iconCls: 'cancel',handler: function() { win.close(); } },
                { text: loginUserLanguageResource.confirm,iconCls: 'save', handler: function() {
                    var view = win.down('[itemId=imageView]');
                    var sel = view.getSelection();
                    if (sel.length === 0) { Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.selectImgFirst); return; }
                    win.doSelect(sel[0].get('url'));
                }}
            ],

            selectCurrentImage: function() {
                var view = this.down('[itemId=imageView]');
                if (!view) return;
                var store = view.getStore();
                if (!store || store.getCount() === 0) return;

                var currentUrl = me.imageUrl || '';
                if (!currentUrl) return;

                var matched = null;
                store.each(function(rec) {
                    if (rec.get('url') === currentUrl) {
                        matched = rec;
                        return false;
                    }
                });
                if (!matched) return;

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
                    Ext.defer(function() { win.selectCurrentImage(); }, 30);
                    return;
                }
                var filtered = allData.filter(function(item) {
                    return item.name.toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
                });
                view.getStore().add(filtered);
                Ext.defer(function() { win.selectCurrentImage(); }, 30);
            },

            doSelect: function(url) {
                if (me._destroyed) { win.close(); return; }
                me.setCanvasImage(url);
                if (me.currentRecord) {
                    var cfg = me.readRecordConfig(me.currentRecord);
                    cfg.imageUrl = url;
                    cfg.imageSource = 'server';
                    me.writeRecordConfig(me.currentRecord, cfg);
                }
                win.close();
            },

            // 删除图片
            doDelete: function() {
                var view = win.down('[itemId=imageView]');
                if (!view) return;
                var sel = view.getSelection();
                if (sel.length === 0) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.selectImgToDel);
                    return;
                }
                var record = sel[0];
                var name = record.get('name');
                var url = record.get('url');

                // ★ 二次保险：如果强行调用删除，也拦住
                if (me.imageUrl && url === me.imageUrl) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.cannotDeleteUsedImgAlert);
                    return;
                }

                Ext.Msg.confirm(loginUserLanguageResource.tip, loginUserLanguageResource.confirmDeleteImage+'[<font color=red>' + name + '</font>]?', function(btn) {
                    if (btn === 'yes') {
                        Ext.MessageBox.show({ msg: loginUserLanguageResource.deleting+'...', progressText: loginUserLanguageResource.deletingProgress, width: 300, wait: true });
                        Ext.Ajax.request({
                            url: context + '/sceneModelController/deleteSceneImage',
                            method: 'POST',
                            params: { name: name },
                            success: function(resp) {
                                Ext.MessageBox.hide();
                                try {
                                    var result = Ext.decode(resp.responseText);
                                    if (result.success) {
                                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
                                        
                                        // 如果当前画布用的就是这张图，替换为空白图，保留标注
                                        if (me.imageUrl === url) {
                                            me.imageUrl = '';
                                            me._canvasCurrentUrl = null; // 强制让 setCanvasImage 认为需要重新加载
                                            me._settingImage = false;    // 重置加载状态
                                            me.setCanvasImage(me.BLANK_IMAGE);
                                            if (me.currentRecord) {
                                                var cfg = me.readRecordConfig(me.currentRecord);
                                                cfg.imageUrl = '';
                                                me.writeRecordConfig(me.currentRecord, cfg);
                                            }
                                        }
                                        
                                        // 刷新图片列表
                                        imageStore.reload({ callback: function() {
                                            win.allImages = imageStore.getRange().map(function(r) { return r.getData(); });
                                        }});
                                    } else {
                                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteFailed);
                                    }
                                } catch (e) {
                                    Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.serverDataError);
                                }
                            },
                            failure: function() {
                                Ext.MessageBox.hide();
                                Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.networkErrorCannotDelete);
                            }
                        });
                    }
                });
            },

            doUpload: function() {
                var input = document.createElement('input');
                input.type = 'file'; input.accept = 'image/*,.svg';
                input.onchange = function(e) {
                    var file = e.target.files[0];
                    if (!file) return;
                    var formData = new FormData();
                    formData.append('file', file);
                    Ext.MessageBox.show({ msg: loginUserLanguageResource.uploading+'...', progressText: loginUserLanguageResource.uploadingProgress, width: 300 });
                    Ext.Ajax.request({
                        url: context + '/sceneModelController/uploadSceneImage',
                        method: 'POST', rawData: formData,
                        headers: { 'Content-Type': null },
                        success: function(resp) {
                            Ext.MessageBox.hide();
                            var result = Ext.decode(resp.responseText);
                            if (result.success) {
                                Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadSuccess);
                                imageStore.reload({ callback: function() {
                                    win.allImages = imageStore.getRange().map(function(r) { return r.getData(); });
                                    Ext.defer(function() { win.selectCurrentImage(); }, 50);
                                }});
                            } else { Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadFail); }
                        },
                        failure: function() { Ext.MessageBox.hide(); Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.uploadFail); }
                    });
                };
                input.click();
            }
        });

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
        if (me._destroyed) return;
        var targetRecord = me.currentRecord;
        var input = document.createElement('input');
        input.type = 'file'; input.accept = 'image/*,.svg';
        input.onchange = function(e) {
            var file = e.target.files[0];
            if (!file) return;
            var formData = new FormData();
            formData.append('file', file);
            Ext.MessageBox.show({ msg: loginUserLanguageResource.uploading+'...', progressText: loginUserLanguageResource.uploadingProgress, width: 300 });
            Ext.Ajax.request({
                url: context + '/sceneModelController/uploadSceneImage',
                method: 'POST', rawData: formData,
                headers: { 'Content-Type': null },
                success: function(resp) {
                    Ext.MessageBox.hide();
                    if (me._destroyed) return;
                    var result;
                    try { result = Ext.decode(resp.responseText); }
                    catch (err) { Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.serverDataError); return; }
                    if (!result.success) { Ext.Msg.alert(loginUserLanguageResource.tip, result.message || loginUserLanguageResource.uploadFail); return; }

                    me.setCanvasImage(result.imageUrl);
                    if (targetRecord) {
                        var cfg = me.readRecordConfig(targetRecord);
                        if (!Ext.isArray(cfg.shapes)) cfg.shapes = [];
                        cfg.imageUrl = result.imageUrl;
                        cfg.imageSource = 'uploaded';
                        me.writeRecordConfig(targetRecord, cfg);
                    }

                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadSuccess);
                },
                failure: function() {
                    Ext.MessageBox.hide();
                    if (me._destroyed) return;
                    Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.uploadFail);
                }
            });
        };
        input.click();
    },

    onSaveAnnotations: function() {
        var me = this;
        if (me._destroyed) return;
        if (!me.currentRecord) { Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.selectSceneFirst); return; }
        if (!me.cs) { Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.canvasNotInitialized); return; }
        var imageUrl = me.imageUrl || '';
        if (!imageUrl) { Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.selectSceneImgFirst); return; }
        if (imageUrl.indexOf('blob:') === 0 || imageUrl.indexOf('data:') === 0) {
            Ext.Msg.confirm(loginUserLanguageResource.tip, loginUserLanguageResource.imgNotUploadedConfirm+'?', function(btn) {
                if (btn === 'yes') me.onUploadLocalImage();
            });
            return;
        }
        var cfg = me.getConfig();
        if (!cfg.shapes || cfg.shapes.length === 0) {
            Ext.Msg.confirm(loginUserLanguageResource.tip, loginUserLanguageResource.noAnnotationsConfirm+'?', function(btn) {
                if (btn === 'yes') me.submitSaveScene(cfg);
            });
            return;
        }
        me.submitSaveScene(cfg);
    },

    submitSaveScene: function(cfg) {
        var me = this;
        if (me._destroyed) return;
        var record = me.currentRecord;
        me.writeRecordConfig(record, cfg);
        Ext.MessageBox.show({ msg: loginUserLanguageResource.savingAnnotations+'...', progressText: loginUserLanguageResource.savingProgress,
                              width: 300, wait: true, waitConfig: { interval: 200 } });
        Ext.Ajax.request({
            url: context + '/sceneModelController/saveSceneModel',
            method: 'POST',
            params: { id: record.get('modelId') || record.get('id'), config: JSON.stringify(cfg) },
            success: function(resp) {
                Ext.MessageBox.hide();
                if (me._destroyed) return;
                try {
                    var result = Ext.decode(resp.responseText);
                    if (result.success) {
                        record.commit();
                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.savedSuccessfully);
                    } else {
                        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveFailed);
                    }
                } catch (e) { Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.serverDataError); }
            },
            failure: function(response) {
                Ext.MessageBox.hide();
                if (me._destroyed) return;
                Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.requestFailed+':' + (response.status || ''));
            }
        });
    },

    // ========================================================
    // 数据源绑定窗口
    // ========================================================
    openDataSourceWindow: function(uuid, isEdit) {
        var me = this;
        if (me._destroyed) return;
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
                if (me._destroyed) return;
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
                    if (me._destroyed) return;
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
                }, 100, me);
            }
        }).show();
    },

    // ========================================================
    // 图形样式变化
    // ========================================================
    onGraphicStyleChange: function() {
        var me = this;
        if (me._destroyed) return;
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
                }
                shape.ctrlRadius = ctrlRadius;

                me._lastDataKey = null;
                
                requestAnimationFrame(function() {
                    if (me._destroyed) return;
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
        if (me._destroyed) return;
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
            hideFieldName: panel.down('#hideFieldName') ? panel.down('#hideFieldName').getValue() : false,
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

    // ========================================================
    // origin 计算
    // ========================================================
    updateOriginIfNeeded: function(force) {
        if (this._destroyed) return false;
        if (!this.cs) return false;
        var cs = this.cs;
        var scale = cs.scale || 1;

        if (!cs.mouse || !cs.position
            || !cs.mouse.length || cs.mouse.length < 2
            || !cs.position.length || cs.position.length < 2) {
            return false;
        }

        var mx = cs.mouse[0], my = cs.mouse[1];
        var px = cs.position[0], py = cs.position[1];

        if (!isFinite(mx) || !isFinite(my)
            || !isFinite(px) || !isFinite(py)
            || !isFinite(scale) || scale <= 0) {
            return false;
        }

        var newX = mx - px * scale;
        var newY = my - py * scale;

        if (!isFinite(newX) || !isFinite(newY)) return false;

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
        if (me._destroyed) return;
        if (me._rafPending) return;
        me._rafPending = true;
        requestAnimationFrame(function() {
            if (me._destroyed) {
                me._rafPending = false;
                return;
            }
            me._rafPending = false;
            me.renderLabels();
        });
    },

    renderLabels: function() {
        var me = this;
        if (me._destroyed) return;
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
                
                // 原有：双击修改数据源
                el.addEventListener('dblclick', function(e) {
                    if (me._destroyed) return;
                    e.stopPropagation();
                    me.openDataSourceWindow(shape.uuid, true);
                });
                
                // 新增：单击选中图形
                el.addEventListener('click', function(e) {
                    if (me._destroyed) return;
                    e.stopPropagation(); 
                    if (me.cs && me.cs.createType === 0) {
                        me.selectShapeByUuid(shape.uuid);
                    }
                });
                
                layer.appendChild(el);
            }
            
            // 计算背景色和阴影
            var bgColor = me.buildFillColor(style.bgColor, style.bgOpacity);
            var boxShadow = (style.bgOpacity == 0) ? 'none' : '0 2px 6px rgba(0,0,0,0.18)';

            // ★ 核心修改：获取文本内容，并判断是否真正需要显示
            var labelText = me.buildLabelText(binding, style);
            el.textContent = labelText;
            
            // 如果勾选了“隐藏标签” 或者 文本为空（设备名和字段名都隐藏了），则设为 none
            var isHidden = style.hide || (labelText === '');

            el.style.cssText = 'position:absolute;padding:4px 10px;border-radius:4px;white-space:nowrap;' +
                'font-weight:600;transform:translate(-50%,' + translateY + ');box-shadow:' + boxShadow + ';' +
                'pointer-events:auto;cursor:pointer;' +
                'left:' + cx + 'px;top:' + cy + 'px;' +
                'background:' + bgColor + ';' +
                'color:' + style.textColor + ';font-size:' + style.font + ';' +
                'display:' + (isHidden ? 'none' : 'block') + ';'; // ★ 使用 isHidden 判断

            visible[shape.uuid] = true;
        });

        Object.keys(existing).forEach(function(uuid) {
            if (!visible[uuid] && existing[uuid].parentNode) {
                existing[uuid].parentNode.removeChild(existing[uuid]);
            }
        });
    },

    buildLabelText: function(b, labelStyle) {
        if (!b || !b.deviceId || !b.fieldName) return loginUserLanguageResource.unbound;
        
        var hideDev = !labelStyle || labelStyle.hideDeviceName !== false;
        var hideFld = labelStyle && labelStyle.hideFieldName === true;
        
        var devName = b.deviceName || b.deviceId;
        var fldName = b.fieldLabel || b.fieldName;
        
        // 根据勾选状态返回不同的组合
        if (hideDev && hideFld) {
            return ''; // 都隐藏时显示空白
        } else if (hideDev) {
            return fldName; // 只显示字段名
        } else if (hideFld) {
            return devName; // 只显示设备名
        } else {
            return devName + '.' + fldName; // 设备名.字段名
        }
    },

    getShapeTopAnchor: function(shape) {
        var coor = shape.coor;
        if (!coor) return null;
        if (shape.type === 1) {
            var a = coor[0], b = coor[1];
            return { x: (a[0] + b[0]) / 2, y: Math.min(a[1], b[1]) };
        }
        if (shape.type === 3) {
            var r = (shape.ctrlRadius != null) ? shape.ctrlRadius
                  : ((this.cs && this.cs.ctrlRadius != null) ? this.cs.ctrlRadius
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
        if (me._destroyed) return;

        if (!me.cs) {
            Ext.defer(function() {
                if (me._destroyed) return;
                me.loadScene(record);
            }, 100, me);
            return;
        }

        me.resetToSelectMode();

        if (!record) { me.currentRecord = null; me.clearScene(); return; }
        me.currentRecord = record;
        me.clearScene();

        var seq = ++me._loadSceneSeq;
        var recordId = record.get('modelId') || record.get('id');

        Ext.MessageBox.show({ msg: loginUserLanguageResource.loadingSceneConfig+'...', progressText: loginUserLanguageResource.loadingProgress,
                              width: 300, wait: true, waitConfig: { interval: 200 } });
        Ext.Ajax.request({
            url: context + '/sceneModelController/getSceneModelConfig',
            method: 'POST',
            params: { id: recordId },
            success: function(resp) {
                if (seq !== me._loadSceneSeq) return;
                if (me._destroyed) return;

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
                        Ext.Msg.alert(loginUserLanguageResource.loadFailed, result.message || loginUserLanguageResource.unknownError);
                    }
                } catch (e) { Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.serverDataError); }
            },
            failure: function() {
                if (seq !== me._loadSceneSeq) return;
                if (me._destroyed) return;

                Ext.MessageBox.hide();
                Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.requestFailed);
            }
        });
    },

    clearScene: function() {
        var me = this;
        if (me._destroyed) return;
        me._clearAllBindingMaps();
        me.lastSelectedUuid = null;
        me.imageUrl = '';
        me.resetStylePanel();
        if (me.cs) me.safeSetData([]);
        if (me.labelLayer) me.labelLayer.innerHTML = '';
        me.refreshBindingTree();
    },

    resetStylePanel: function() {
        var me = this;
        if (me._destroyed) return;
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
                
                var hideFldField = panel.down('#hideFieldName');
                if (hideFldField) hideFldField.setValue(false);
                
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

    // ========================================================
    // 用 config 渲染
    // ========================================================
    renderSceneConfig: function(cfg) {
        var me = this;
        if (me._destroyed) return;
        if (!cfg) cfg = { imageUrl: '', shapes: [] };

        try {
            if (cfg.defaultLabelStyle) Ext.apply(me.defaultLabelStyle, cfg.defaultLabelStyle);
            if (cfg.globalStyle) Ext.apply(me.globalStyle, cfg.globalStyle);

            try { me.syncStylePanelFromState(); } catch (e) {}

            me._clearAllBindingMaps();

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
                        deviceId: String(s.deviceId),
                        deviceName: s.deviceName,
                        fieldName: s.fieldName,
                        fieldLabel: s.fieldLabel,
                        unit: s.unit, dataSource: s.dataSource,
                        currentValue: s.currentValue
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

            me.refreshBindingTree(true);

            Ext.defer(function() {
                if (me._destroyed) return;
                me._nudgeCanvasMouseCenter();
                me.updateOriginIfNeeded(true);
                me.scheduleRenderLabels();

                Ext.defer(function() {
                    if (me._destroyed) return;
                    if (!me.cs) return;
                    me._nudgeCanvasMouseCenter();
                    me.updateOriginIfNeeded(true);
                    me.scheduleRenderLabels();
                }, 200, me);
            }, 100, me);

        } catch (e) {
            console.error('[SceneModelPanel] renderSceneConfig 异常:', e);

            if (!me.bindingMap)       me.bindingMap = new Map();
            if (!me.labelStyleMap)    me.labelStyleMap = new Map();
            if (!me.userColorMap)     me.userColorMap = new Map();
            if (!me.userLineWidthMap) me.userLineWidthMap = new Map();

            try { me._clearAllBindingMaps(); } catch (e2) {}
        }
    },

    syncStylePanelFromState: function() {
        var me = this;
        if (me._destroyed) return;
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
                    var hideFldField = panel.down('#hideFieldName');
                    if (hideFldField) hideFldField.setValue(ls.hideFieldName === true);
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
                    cr = (s.ctrlRadius != null) ? s.ctrlRadius
                       : ((me.cs && me.cs.ctrlRadius != null) ? me.cs.ctrlRadius : 3);
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
        if (me._destroyed) return;
        me.currentRecord = null;
        me._clearAllBindingMaps();
        me.lastSelectedUuid = null;
        me.imageUrl = '';
        me.resetStylePanel();
        if (me.cs) me.safeSetData([]);
        if (me.labelLayer) me.labelLayer.innerHTML = '';
        me.refreshBindingTree();
    },

    onCreateScene: function() {
        var me = this;
        if (me._destroyed) return;
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
        var me = this;
        if (me._destroyed) return;
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
        var me = this;
        if (me._destroyed) return;
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
        var me = this;
        if (me._destroyed) return;
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
        var me = this;
        if (me._destroyed) return;
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
                } catch (err) { Ext.Msg.alert(loginUserLanguageResource.error, loginUserLanguageResource.invalidFileFormat); }
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
        this._destroyed = true;

        if (this._originTimer) { clearInterval(this._originTimer); this._originTimer = null; }
        if (this._resizeTimer) { clearTimeout(this._resizeTimer); this._resizeTimer = null; }

        try { Ext.EventManager.removeResizeListener(this._scheduleResize, this); } catch (e) {}

        if (this._visibilityHandler) {
            try { document.removeEventListener('visibilitychange', this._visibilityHandler); } catch (e) {}
            this._visibilityHandler = null;
        }

        this._rafPending = false;

        if (this.labelLayer) {
            try { this.labelLayer.innerHTML = ''; } catch (e) {}
            this.labelLayer = null;
        }

        this._clearAllBindingMaps();
        this.bindingMap       = null;
        this.labelStyleMap    = null;
        this.userColorMap     = null;
        this.userLineWidthMap = null;

        try { if (this.cs && typeof this.cs.destroy === 'function') this.cs.destroy(); } catch (e) {}
        this.cs = null;

        this._resizing = false;
        this._settingImage = false;
        this._rebuilding = false;
        this._clearingSelection = false;
        this._suppressStyleChange = false;

        this.callParent(arguments);
    }
});