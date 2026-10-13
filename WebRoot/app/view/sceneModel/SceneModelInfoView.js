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
    // 布局
    // ========================================================
    initComponent: function() {
        var me = this;
        me.bindingMap = new Map();
        me.labelStyleMap = new Map();
        me.userColorMap = new Map();
        me.userLineWidthMap = new Map();

        Ext.apply(me, {
            tbar: [],
            items: [
                {
                    region: 'west',
                    width: '32%',
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
                          handler: function(b) { if (b.pressed && me.cs) me.cs.createType = 0; } },
                        { xtype: 'button', text: '▭ 矩形', itemId: 'btnRect',
                          enableToggle: true, toggleGroup: 'createType',
                          handler: function(b) { if (b.pressed && me.cs) me.cs.createType = 1; } },
                        { xtype: 'button', text: '• 点', itemId: 'btnPoint',
                          enableToggle: true, toggleGroup: 'createType', tooltip: '点击画布添加点标注',
                          handler: function(b) { if (b.pressed && me.cs) me.cs.createType = 3; } },
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
                            xtype: 'panel', region: 'east', width: 280,
                            title: '样式设置', collapsible: true, split: true,
                            bodyPadding: 12, autoScroll: true,
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
            }, 100, me);
        });
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
        var newKey = data.map(function(s) { return s.uuid || ''; }).join('|');
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
        if (url === me._canvasCurrentUrl) return;
        if (url === me.BLANK_IMAGE) { me.imageUrl = ''; me.rebuildCanvasSelect(); return; }
        if (me._settingImage) return;
        me._settingImage = true;

        var probe = new Image();
        probe.onload = function() {
            me._settingImage = false;
            if (!me.cs) return;
            try {
                me.cs.setImage(url);
                me.imageUrl = url;
                me._canvasCurrentUrl = url;
            } catch (e) {
                me.imageUrl = '';
                me._canvasCurrentUrl = null;
                me.rebuildCanvasSelect();
            }
        };
        probe.onerror = function() {
            me._settingImage = false;
            me.imageUrl = '';
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
            if (me.originTimer) { clearInterval(me.originTimer); me.originTimer = null; }
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

    initCanvasSelect: function() {
        var me = this;
        var canvasCmp = me.down('#canvas');
        var layerCmp = me.down('#labelLayer');
        if (!canvasCmp || !layerCmp) return;
        if (me.originTimer) { clearInterval(me.originTimer); me.originTimer = null; }
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

        me.cs.on('load', function() { me.updateOriginIfNeeded(true); me.scheduleRenderLabels(); });
        me.cs.on('add', function(info) { me.onShapeAdded(info); });
        me.cs.on('select', function(info) { me.onShapeSelected(info); });
        me.cs.on('updated', function() { me.scheduleRenderLabels(); });
        me.cs.on('coor', function() { me.scheduleRenderLabels(); });

        canvasEl.addEventListener('dblclick', function() {
            if (me.cs.createType !== 0) return;
            var info = me.cs.activeShape;
            if (info) {
                var uuid = me.extractUuid(info);
                if (uuid) me.openDataSourceWindow(uuid);
            }
        });

        me.originTimer = setInterval(function() {
            if (me.updateOriginIfNeeded()) me.scheduleRenderLabels();
        }, 60);
    },

    onShapeAdded: function(info) {
        var me = this;
        var uuid = me.extractUuid(info);
        if (!uuid) return;
        me.userColorMap.set(uuid, me.globalStyle.strokeStyle || '#00FF00');
        me.userLineWidthMap.set(uuid, me.globalStyle.lineWidth != null ? me.globalStyle.lineWidth : 1);
        try {
            var shapes = me.cs.dataset;
            for (var i = 0; i < shapes.length; i++) {
                if (shapes[i].uuid === uuid) {
                    shapes[i]._userStrokeStyle = me.globalStyle.strokeStyle || '#00FF00';
                    shapes[i]._userLineWidth = me.globalStyle.lineWidth != null ? me.globalStyle.lineWidth : 1;
                    if (shapes[i].type === 3) {
                        var r = (me.cs && me.cs.ctrlRadius != null) ? me.cs.ctrlRadius
                              : (me.globalStyle.ctrlRadius != null ? me.globalStyle.ctrlRadius : 3);
                        shapes[i].ctrlRadius = r;
                        shapes[i]._userCtrlRadius = r;
                    }
                    break;
                }
            }
        } catch (e) {}
        Ext.defer(function() { me.openDataSourceWindow(uuid, false); }, 30);
    },

    onShapeSelected: function(info) {
        var me = this;
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

        me.setColorFieldValue('strokeStyle', userColor, 100);
        me.setColorFieldValue('fillStyle', fillParts.hex, fillParts.alpha);

        var lwField = panel.down('#lineWidth');
        if (lwField) lwField.setValue(userLw);

        // ★ 点类型：面板显示的值 = canvas-select 实际绘制用的全局 ctrlRadius
        var crVal;
        if (shape.type === 3) {
            crVal = me.cs.ctrlRadius != null ? me.cs.ctrlRadius : 3;
        } else {
            crVal = shape.ctrlRadius != null ? shape.ctrlRadius : 3;
        }
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
            filterImages: function(keyword) {
                var view = this.down('[itemId=imageView]');
                var allData = this.allImages || [];
                view.getStore().removeAll();
                if (!keyword) { view.getStore().add(allData); return; }
                var filtered = allData.filter(function(item) {
                    return item.name.toLowerCase().indexOf(keyword.toLowerCase()) >= 0;
                });
                view.getStore().add(filtered);
            },
            doSelect: function(url) {
                me.setCanvasImage(url);
                if (me.currentRecord) {
                    var cfg = me.readRecordConfig(me.currentRecord);
                    cfg.imageUrl = me.imageUrl;
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
                                }});
                            } else { Ext.Msg.alert('失败', result.message || '上传失败'); }
                        },
                        failure: function() { Ext.MessageBox.hide(); Ext.Msg.alert('错误', '上传失败'); }
                    });
                };
                input.click();
            }
        });

        imageStore.on('load', function(store) {
            win.allImages = store.getRange().map(function(r) { return r.getData(); });
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
                        cfg.imageUrl = me.imageUrl;
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
                me.scheduleRenderLabels();
            }
        }).show();
    },

    // ========================================================
    // 图形样式变更
    // ========================================================
    onGraphicStyleChange: function() {
        var me = this;
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

        if (me.lastSelectedUuid && me.cs) {
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
                shape.ctrlRadius = ctrlRadius;
                shape._userStrokeStyle = strokeStyle;
                shape._userLineWidth = lineWidth;

                // ★★★ 点类型：canvas-select 用全局 ctrlRadius 渲染，必须同步
                if (shape.type === 3) {
                    shape._userCtrlRadius = ctrlRadius;
                    me.cs.ctrlRadius = ctrlRadius;
                }

                me._lastDataKey = null;
                var snapshot = me.cs.dataset.map(function(s) { return Ext.apply({}, s); });
                me.safeSetData(snapshot);

                requestAnimationFrame(function() {
                    try {
                        if (me.cs) {
                            try { me.cs.activeShape = { index: idx }; } catch (e) {}
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
        if (!layer || !me.cachedOrigin.ready || !me.cs) return;

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
            // ★ 点锚点距离 = canvas-select 实际绘制用的全局 ctrlRadius
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

    loadScene: function(record) {
        var me = this;
        if (!me.cs) { Ext.defer(function() { me.loadScene(record); }, 100, me); return; }
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
    },

    resetStylePanel: function() {
        var me = this;
        try {
            var panel = me.down('panel[region=east]');
            if (!panel) return;

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
        } catch (e) {}
    },

    // ========================================================
    // 用 config 渲染（★ 本次修复重点：从 shapes 提取点半径同步到全局 ctrlRadius）
    // ========================================================
    renderSceneConfig: function(cfg) {
        var me = this;
        if (!cfg) cfg = { imageUrl: '', shapes: [] };

        if (cfg.defaultLabelStyle) Ext.apply(me.defaultLabelStyle, cfg.defaultLabelStyle);
        if (cfg.globalStyle) Ext.apply(me.globalStyle, cfg.globalStyle);

        try { me.syncStylePanelFromState(); } catch (e) {}

        // ★★★ 关键修复：从 shapes 中提取点半径，同步到 canvas-select 的全局 ctrlRadius
        //   canvas-select 绘制 type=3 的点时，半径用的是 this.ctrlRadius（全局），
        //   而不是 shape.ctrlRadius。所以必须从持久化的数据里恢复这个全局值。
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
            // 同步 UI 面板（不触发 loop：setValue 会走 onGraphicStyleChange 的"无选中"分支，
            // 那里写入的也是同一个值，幂等）
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

        Ext.defer(function() {
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        }, 100);
    },

    syncStylePanelFromState: function() {
        var me = this;
        try {
            var gs = me.globalStyle || {};
            var ls = me.defaultLabelStyle || {};

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
        } catch (e) {}
    },

    getConfig: function() {
        var me = this;
        return {
            imageUrl: me.imageUrl || '',
            defaultLabelStyle: Ext.apply({}, me.defaultLabelStyle),
            globalStyle: Ext.apply({}, me.globalStyle),
            shapes: me.cs ? me.cs.dataset.map(function(s) {
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

                // 点半径：全局（canvas-select 限制）
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
            }) : []
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
        if (info.index !== undefined && this.cs.dataset[info.index]) {
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

    beforeDestroy: function() {
        if (this.originTimer) { clearInterval(this.originTimer); this.originTimer = null; }
        try { if (this.cs && typeof this.cs.destroy === 'function') this.cs.destroy(); } catch (e) {}
        this.callParent(arguments);
    }
});