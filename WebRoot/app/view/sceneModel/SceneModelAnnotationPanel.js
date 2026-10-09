Ext.define('AP.view.sceneModel.SceneModelAnnotationPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.sceneModelAnnotationPanel',
    requires: [
        'AP.view.sceneModel.ImageSelectWindow'
    ],

    layout: 'border',
    border: false,

    // ============ 内部状态 ============
    canvasSelect: null,
    labelLayer: null,
    currentRecord: null,
    cachedOrigin: { x: 0, y: 0, scale: 1, ready: false },

    // 数据源绑定：uuid -> { deviceId, fieldName, unit, currentValue }
    bindingMap: null,
    // 每个形状的标签样式：uuid -> { font, bgColor, bgOpacity, textColor, hide, up }
    labelStyleMap: null,
    lastSelectedUuid: null,
    defaultLabelStyle: {
        font: '12px', bgColor: '#4A6CF7', bgOpacity: 100,
        textColor: '#FFFFFF', hide: false, up: true
    },
    globalStyle: {
        strokeStyle: '#00FF00', fillColor: '#0000FF', fillOpacity: 25,
        lineWidth: 1, ctrlRadius: 3
    },
    TRANSPARENT_STROKE: 'rgba(0,0,0,0)',

    initComponent: function() {
        var me = this;
        me.bindingMap = new Map();
        me.labelStyleMap = new Map();

        me.items = [
            // ============ 顶部工具条：图片操作 ============
            me.buildTopToolbar(),

            // ============ 中央：画布 ============
            me.buildCanvasArea(),

            // ============ 右侧：样式面板 ============
            me.buildStylePanel()
        ];

        me.callParent(arguments);

        // 等 DOM 就绪后初始化 canvas-select
        me.on('afterrender', function() {
            Ext.defer(me.initCanvasSelect, 80, me);
        });
    },

    // ========================================================
    // 顶部工具条
    // ========================================================
    buildTopToolbar: function() {
        var me = this;
        return {
            xtype: 'panel',
            region: 'north',
            height: 48,
            bodyStyle: 'background:#fff;border-bottom:1px solid #e9ecef',
            layout: { type: 'hbox', align: 'middle', pack: 'start' },
            padding: '8 12',
            items: [
                {
                    xtype: 'button',
                    text: '✋ 选择',
                    itemId: 'btnSelect',
                    enableToggle: true,
                    pressed: true,
                    toggleGroup: 'createType',
                    createType: 0,
                    handler: me.onCreateTypeChange,
                    scope: me
                }, {
                    xtype: 'button',
                    text: '▭ 矩形',
                    itemId: 'btnRect',
                    enableToggle: true,
                    toggleGroup: 'createType',
                    createType: 1,
                    handler: me.onCreateTypeChange,
                    scope: me
                }, '-', {
                    xtype: 'button', text: '🔍+', tooltip: '放大',
                    handler: function() { me.cs && me.cs.setScale(true); me.afterZoom(); }
                }, {
                    xtype: 'button', text: '🔍−', tooltip: '缩小',
                    handler: function() { me.cs && me.cs.setScale(false); me.afterZoom(); }
                }, {
                    xtype: 'button', text: '适配',
                    handler: function() { me.cs && me.cs.fitZoom(); me.afterZoom(); }
                }, '-', {
                    xtype: 'button',
                    text: '🖼️ 选择服务器图片',
                    handler: me.onSelectServerImage,
                    scope: me
                }, {
                    xtype: 'button',
                    text: '📤 上传本地图片',
                    handler: me.onUploadLocalImage,
                    scope: me
                }, '->', {
                    xtype: 'button',
                    text: '💾 保存标注',
                    cls: 'x-btn-primary',
                    handler: me.onSaveAnnotations,
                    scope: me
                }
            ]
        };
    },

    // ========================================================
    // 画布区域
    // ========================================================
    buildCanvasArea: function() {
        return {
            xtype: 'container',
            region: 'center',
            itemId: 'canvasWrap',
            layout: 'absolute',
            style: {
                position: 'relative',
                background: '#f8f9fa',
                border: '2px dashed #dee2e6',
                borderRadius: '8px',
                overflow: 'hidden',
                margin: '12px'
            },
            items: [{
                xtype: 'component',
                itemId: 'canvas',
                autoEl: { tag: 'canvas', cls: 'scene-annotation-canvas' },
                style: { display: 'block', width: '100%', height: '100%' }
            }, {
                xtype: 'component',
                itemId: 'labelLayer',
                autoEl: { tag: 'div', cls: 'scene-label-layer' },
                style: {
                    position: 'absolute', top: 0, left: 0, right: 0, bottom: 0,
                    pointerEvents: 'none', overflow: 'hidden', zIndex: 10
                }
            }]
        };
    },

    // ========================================================
    // 样式面板（右侧）
    // ========================================================
    buildStylePanel: function() {
        var me = this;
        return {
            xtype: 'panel',
            region: 'east',
            width: 280,
            title: '样式设置',
            collapsible: true,
            split: true,
            bodyPadding: 12,
            autoScroll: true,
            items: [
                // ---- 图形样式 ----
                {
                    xtype: 'fieldset',
                    title: '<span itemId="styleTargetTag">默认样式</span>',
                    defaults: { anchor: '100%', margin: '6 0' },
                    items: [
                        { xtype: 'colorfield', fieldLabel: '边框颜色', itemId: 'strokeStyle', value: '#00FF00', listeners: { change: me.onGraphicStyleChange, scope: me } },
                        { xtype: 'sliderfield', fieldLabel: '边框宽度', itemId: 'lineWidth', value: 1, minValue: 0, maxValue: 10, listeners: { change: me.onGraphicStyleChange, scope: me } },
                        { xtype: 'colorfield', fieldLabel: '填充颜色', itemId: 'fillStyle', value: '#0000FF', listeners: { change: me.onGraphicStyleChange, scope: me } },
                        { xtype: 'sliderfield', fieldLabel: '填充透明度%', itemId: 'fillOpacity', value: 25, minValue: 0, maxValue: 100, listeners: { change: me.onGraphicStyleChange, scope: me } },
                        { xtype: 'sliderfield', fieldLabel: '控制点大小', itemId: 'ctrlRadius', value: 3, minValue: 2, maxValue: 10, listeners: { change: me.onGraphicStyleChange, scope: me } }
                    ]
                },
                // ---- 标签样式 ----
                {
                    xtype: 'fieldset',
                    title: '<span itemId="labelStyleTargetTag">标签样式（默认）</span>',
                    defaults: { anchor: '100%', margin: '6 0' },
                    items: [
                        { xtype: 'combo', fieldLabel: '字体', itemId: 'labelFont',
                          store: ['11px', '12px', '13px', '14px'], value: '12px', editable: false,
                          listeners: { change: me.onLabelStyleChange, scope: me } },
                        { xtype: 'colorfield', fieldLabel: '标签背景', itemId: 'labelBg', value: '#4A6CF7',
                          listeners: { change: me.onLabelStyleChange, scope: me } },
                        { xtype: 'sliderfield', fieldLabel: '背景透明度%', itemId: 'labelBgOpacity', value: 100, minValue: 0, maxValue: 100,
                          listeners: { change: me.onLabelStyleChange, scope: me } },
                        { xtype: 'colorfield', fieldLabel: '文字颜色', itemId: 'labelText', value: '#FFFFFF',
                          listeners: { change: me.onLabelStyleChange, scope: me } },
                        { xtype: 'checkbox', fieldLabel: '隐藏标签', itemId: 'hideLabel',
                          listeners: { change: me.onLabelStyleChange, scope: me } }
                    ]
                }
            ]
        };
    },

    // ========================================================
    // 初始化 canvas-select
    // ========================================================
    initCanvasSelect: function() {
        var me = this;
        var canvasEl = me.down('#canvas').el.dom;
        var layerEl = me.down('#labelLayer').el.dom;
        me.labelLayer = layerEl;

        if (typeof CanvasSelect === 'undefined') {
            Ext.Msg.alert('错误', 'CanvasSelect 库未加载，请检查 resources/js/canvas-select.min.js');
            return;
        }

        me.cs = new CanvasSelect(canvasEl, '');
        me.cs.ctrlRadius = me.globalStyle.ctrlRadius;
        me.cs.createType = 0;
        me.cs.scrollZoom = true;
        me.cs.hideLabel = true;   // 用 HTML 覆盖层显示标签

        // 绑定事件
        me.cs.on('load', function() {
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        });
        me.cs.on('add', function(info) { me.onShapeAdded(info); });
        me.cs.on('select', function(info) { me.onShapeSelected(info); });
        me.cs.on('updated', function(result) { me.scheduleRenderLabels(); });
        me.cs.on('coor', function() { me.scheduleRenderLabels(); });

        // canvas 双击 → 打开数据源配置
        canvasEl.addEventListener('dblclick', function(e) {
            if (me.cs.createType !== 0) return;
            var info = me.cs.activeShape;
            if (info) {
                var uuid = me.extractUuid(info);
                if (uuid) me.openDataSourceWindow(uuid);
            }
        });

        // 每 60ms 检测原点变化，刷新 HTML 标签
        me.originTimer = setInterval(function() {
            if (me.updateOriginIfNeeded()) me.scheduleRenderLabels();
        }, 60);
    },

    // ========================================================
    // 事件：新建形状
    // ========================================================
    onShapeAdded: function(info) {
        var me = this;
        var uuid = me.extractUuid(info);
        if (!uuid) return;

        // 立即打开数据源配置
        Ext.defer(function() {
            me.openDataSourceWindow(uuid, false);
        }, 30);
    },

    // ========================================================
    // 事件：选中形状
    // ========================================================
    onShapeSelected: function(info) {
        var me = this;
        var uuid = me.extractUuid(info);
        if (!uuid) return;

        me.lastSelectedUuid = uuid;
        var shape = me.cs.dataset.find(s => s.uuid === uuid);
        if (shape) {
            // 回填样式控件
            var panel = me.down('fieldset').up();
            var fillParts = me.parseFillStyle(shape.fillStyle || '#0000FF40');
            me.down('#strokeStyle').setValue(shape._userStrokeStyle || shape.strokeStyle || '#00FF00');
            me.down('#lineWidth').setValue(shape.lineWidth != null ? shape.lineWidth : 1);
            me.down('#fillStyle').setValue(fillParts.hex);
            me.down('#fillOpacity').setValue(fillParts.alpha);
            me.down('#ctrlRadius').setValue(shape.ctrlRadius || 3);
        }
    },

    // ========================================================
    // 图片操作
    // ========================================================
    onSelectServerImage: function() {
        var me = this;
        Ext.create('AP.view.sceneModel.ImageSelectWindow', {
            listeners: {
                imageSelected: function(url) {
                    me.cs.setImage(url);
                    if (me.currentRecord) {
                        var cfg = me.currentRecord.getConfig();
                        cfg.imageUrl = url;
                        cfg.imageSource = 'server';
                        me.currentRecord.setConfig(cfg);
                    }
                }
            }
        }).show();
    },

    onUploadLocalImage: function() {
        var me = this;
        var input = document.createElement('input');
        input.type = 'file';
        input.accept = 'image/*,.svg';
        input.onchange = function(e) {
            var file = e.target.files[0];
            if (!file) return;

            var formData = new FormData();
            formData.append('file', file);

            Ext.MessageBox.show({ msg: '正在上传...', progressText: '上传中', width: 300 });

            Ext.Ajax.request({
                url: context + '/sceneModelController/uploadSceneImage',
                method: 'POST',
                rawData: formData,
                headers: { 'Content-Type': null },  // 让浏览器自己设置 boundary
                success: function(resp) {
                    Ext.MessageBox.hide();
                    try {
                        var result = Ext.decode(resp.responseText);
                        if (result.success) {
                            var url = result.imageUrl;
                            me.cs.setImage(url);
                            if (me.currentRecord) {
                                var cfg = me.currentRecord.getConfig();
                                cfg.imageUrl = url;
                                cfg.imageSource = 'uploaded';
                                me.currentRecord.setConfig(cfg);
                            }
                            Ext.toast('上传成功');
                        } else {
                            Ext.Msg.alert('失败', result.message || '上传失败');
                        }
                    } catch (err) {
                        Ext.Msg.alert('错误', '上传返回数据异常');
                    }
                },
                failure: function() {
                    Ext.MessageBox.hide();
                    Ext.Msg.alert('错误', '上传失败');
                }
            });
        };
        input.click();
    },

    // ========================================================
    // 保存标注
    // ========================================================
    onSaveAnnotations: function() {
        var me = this;
        if (!me.currentRecord) {
            Ext.Msg.alert('提示', '请先在左侧选择一个场景');
            return;
        }

        var cfg = me.getConfig();
        me.currentRecord.setConfig(cfg);

        // 通知外层
        var configPanel = me.up('sceneModelConfigPanel');
        if (configPanel && configPanel.saveScene) {
            configPanel.saveScene();
        }

        // 保存到数据库（单条更新）
        Ext.Ajax.request({
            url: context + '/sceneModelController/saveSceneModel',
            method: 'POST',
            jsonData: {
                id: me.currentRecord.get('id'),
                config: JSON.stringify(cfg)
            },
            success: function() {
                Ext.toast('标注已保存');
            }
        });
    },

    // ========================================================
    // 数据源配置弹窗
    // ========================================================
    openDataSourceWindow: function(uuid, isEdit) {
        var me = this;
        // 若已存在则复用
        var win = Ext.getCmp('sceneDataSourceWindow');
        if (!win) {
            win = Ext.create('Ext.window.Window', {
                id: 'sceneDataSourceWindow',
                title: '绑定数据源',
                width: 400,
                modal: true,
                layout: 'fit',
                closeAction: 'hide',
                items: [{
                    xtype: 'form',
                    padding: 20,
                    defaults: { anchor: '100%', labelWidth: 60 },
                    items: [
                        { xtype: 'combo', fieldLabel: '设备', name: 'deviceId', itemId: 'deviceCombo',
                          store: { type: 'ajax', autoLoad: true,
                                   url: context + '/sceneModelController/getDeviceList',
                                   reader: { type: 'json', rootProperty: 'data' } },
                          displayField: 'name', valueField: 'id',
                          queryMode: 'local', editable: false, forceSelection: true,
                          listeners: { change: function(combo, v) { win.fillFields(v); } }
                        },
                        { xtype: 'combo', fieldLabel: '字段', name: 'fieldName', itemId: 'fieldCombo',
                          queryMode: 'local', editable: false, forceSelection: true,
                          displayField: 'label', valueField: 'name' }
                    ]
                }],
                buttons: [
                    { text: '取消', handler: function() { win.hide(); } },
                    { text: '确定', cls: 'x-btn-primary', handler: function() {
                        var deviceId = win.down('#deviceCombo').getValue();
                        var fieldName = win.down('#fieldCombo').getValue();
                        if (!deviceId || !fieldName) {
                            Ext.Msg.alert('提示', '请选择设备和字段');
                            return;
                        }
                        var device = win.down('#deviceCombo').getStore().findRecord('id', deviceId);
                        var field = device.get('fields').find(f => f.name === fieldName);

                        var old = me.bindingMap.get(uuid) || {};
                        me.bindingMap.set(uuid, {
                            deviceId: deviceId,
                            fieldName: fieldName,
                            unit: field ? field.unit : '',
                            currentValue: old.currentValue != null ? old.currentValue : '--'
                        });

                        if (!me.labelStyleMap.has(uuid)) {
                            me.labelStyleMap.set(uuid, Ext.apply({}, me.defaultLabelStyle));
                        }

                        me.scheduleRenderLabels();
                        win.hide();
                    }}
                ],

                // 回填字段联动
                fillFields: function(deviceId, selectedField) {
                    var fieldCombo = this.down('#fieldCombo');
                    var device = this.down('#deviceCombo').getStore().findRecord('id', deviceId);
                    var fields = device ? device.get('fields') : [];
                    fieldCombo.getStore().removeAll();
                    fieldCombo.getStore().add(fields);
                    if (selectedField) {
                        fieldCombo.setValue(selectedField);
                    } else if (fields.length > 0) {
                        fieldCombo.setValue(fields[0].name);
                    }
                },

                showFor: function(uuid, isEdit) {
                    this.currentUuid = uuid;
                    var binding = me.bindingMap.get(uuid) || {};
                    this.setTitle(isEdit ? '编辑数据源绑定' : '绑定数据源');
                    var combo = this.down('#deviceCombo');
                    // 等 store 加载完再回填
                    var store = combo.getStore();
                    if (store.isLoaded()) {
                        if (binding.deviceId) combo.setValue(binding.deviceId);
                        this.fillFields(combo.getValue(), binding.fieldName);
                    } else {
                        store.on('load', function() {
                            if (binding.deviceId) combo.setValue(binding.deviceId);
                            win.fillFields(combo.getValue(), binding.fieldName);
                        }, this, { single: true });
                    }
                    this.show();
                }
            });
        } else {
            win.show();
        }
        win.showFor(uuid, isEdit);
    },

    // ========================================================
    // 样式变更
    // ========================================================
    onGraphicStyleChange: function() {
        var me = this;
        var strokeStyle = me.down('#strokeStyle').getValue();
        var lineWidth = me.down('#lineWidth').getValue();
        var fillColor = me.down('#fillStyle').getValue();
        var fillOpacity = me.down('#fillOpacity').getValue();
        var ctrlRadius = me.down('#ctrlRadius').getValue();

        var fillStyle = fillColor + me.toHexAlpha(fillOpacity);
        var effectiveStroke = lineWidth === 0 ? me.TRANSPARENT_STROKE : strokeStyle;

        if (me.lastSelectedUuid) {
            // 有选中：只改这一个
            var idx = me.cs.dataset.findIndex(s => s.uuid === me.lastSelectedUuid);
            if (idx >= 0) {
                var shape = me.cs.dataset[idx];
                shape._userStrokeStyle = strokeStyle;
                shape.strokeStyle = effectiveStroke;
                shape.lineWidth = lineWidth;
                shape.fillStyle = fillStyle;
                shape.ctrlRadius = ctrlRadius;

                // setData 重建
                var snapshot = me.cs.dataset.map(s => Ext.apply({}, s));
                me.cs.setData(snapshot);
            }
        } else {
            // 无选中：改全局默认
            me.globalStyle = {
                strokeStyle: strokeStyle, fillColor: fillColor, fillOpacity: fillOpacity,
                lineWidth: lineWidth, ctrlRadius: ctrlRadius
            };
            me.cs.strokeStyle = effectiveStroke;
            me.cs.fillStyle = fillStyle;
            me.cs.lineWidth = lineWidth;
            me.cs.ctrlRadius = ctrlRadius;
            me.cs.update();
        }
    },

    onLabelStyleChange: function() {
        var me = this;
        var style = {
            font: me.down('#labelFont').getValue(),
            bgColor: me.down('#labelBg').getValue(),
            bgOpacity: me.down('#labelBgOpacity').getValue(),
            textColor: me.down('#labelText').getValue(),
            hide: me.down('#hideLabel').getValue(),
            up: true
        };

        if (me.lastSelectedUuid) {
            me.labelStyleMap.set(me.lastSelectedUuid, style);
        } else {
            Ext.apply(me.defaultLabelStyle, style);
        }
        me.scheduleRenderLabels();
    },

    // ========================================================
    // 标签覆盖层渲染
    // ========================================================
    updateOriginIfNeeded: function(force) {
        if (!this.cs) return false;
        var cs = this.cs;
        var scale = cs.scale || 1;
        var mx = cs.mouse[0], my = cs.mouse[1];
        var px = cs.position[0], py = cs.position[1];
        var newX = mx - px * scale;
        var newY = my - py * scale;

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
        if (!layer || !me.cachedOrigin.ready) return;

        var origin = me.cachedOrigin;
        var existing = {};
        Array.from(layer.children).forEach(el => existing[el.dataset.uuid] = el);
        var visible = {};

        me.cs.dataset.forEach(function(shape) {
            var binding = me.bindingMap.get(shape.uuid);
            if (!binding) return;

            var anchor = me.getShapeTopAnchor(shape);
            if (!anchor) return;

            var cx = Math.round(origin.x + anchor.x * origin.scale);
            var cy = Math.round(origin.y + anchor.y * origin.scale);
            var style = me.labelStyleMap.get(shape.uuid) || me.defaultLabelStyle;

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
                'font-weight:600;transform:translate(-50%,-100%);box-shadow:0 2px 6px rgba(0,0,0,0.18);' +
                'pointer-events:auto;cursor:pointer;' +
                'left:' + cx + 'px;top:' + cy + 'px;' +
                'background:' + style.bgColor + me.toHexAlpha(style.bgOpacity) + ';' +
                'color:' + style.textColor + ';font-size:' + style.font + ';' +
                'display:' + (style.hide ? 'none' : 'block') + ';';
            el.textContent = me.buildLabelText(binding);

            visible[shape.uuid] = true;
        });

        Object.keys(existing).forEach(function(uuid) {
            if (!visible[uuid] && existing[uuid].parentNode) {
                existing[uuid].parentNode.removeChild(existing[uuid]);
            }
        });
    },

    buildLabelText: function(b) {
        if (!b || !b.deviceId || !b.fieldName) return '未绑定';
        var val = (b.currentValue != null && b.currentValue !== '--')
            ? b.currentValue + (b.unit || '') : '--';
        return b.deviceId + '.' + b.fieldName + ': ' + val;
    },

    getShapeTopAnchor: function(shape) {
        var coor = shape.coor;
        if (!coor) return null;
        if (shape.type === 1) {
            var a = coor[0], b = coor[1];
            return { x: (a[0] + b[0]) / 2, y: Math.min(a[1], b[1]) };
        }
        if (shape.type === 3) return { x: coor[0], y: coor[1] };
        if (shape.type === 2 || shape.type === 4) {
            var xs = coor.map(p => p[0]), ys = coor.map(p => p[1]);
            return { x: (Math.min.apply(null, xs) + Math.max.apply(null, xs)) / 2, y: Math.min.apply(null, ys) };
        }
        return null;
    },

    // ========================================================
    // 对外接口：加载 / 导出 config
    // ========================================================
    loadConfig: function(cfg, record) {
        var me = this;
        me.currentRecord = record;
        me.bindingMap.clear();
        me.labelStyleMap.clear();
        me.lastSelectedUuid = null;

        if (cfg.defaultLabelStyle) Ext.apply(me.defaultLabelStyle, cfg.defaultLabelStyle);
        if (cfg.globalStyle) Ext.apply(me.globalStyle, cfg.globalStyle);

        // 加载图片
        if (cfg.imageUrl) {
            me.cs.setImage(cfg.imageUrl);
        }

        // 加载 shapes
        var shapes = cfg.shapes || [];
        // 恢复绑定信息
        shapes.forEach(function(s) {
            if (s.deviceId && s.fieldName) {
                me.bindingMap.set(s.uuid, {
                    deviceId: s.deviceId,
                    fieldName: s.fieldName,
                    unit: s.unit,
                    currentValue: s.currentValue
                });
            }
            if (s.labelStyle) me.labelStyleMap.set(s.uuid, s.labelStyle);
        });

        // 剔除业务字段后传给 canvas-select
        var pureShapes = shapes.map(function(s) {
            return {
                uuid: s.uuid, type: s.type, coor: s.coor,
                strokeStyle: s.strokeStyle, fillStyle: s.fillStyle,
                lineWidth: s.lineWidth, ctrlRadius: s.ctrlRadius
            };
        });
        me.cs.setData(pureShapes);

        Ext.defer(function() {
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        }, 60);
    },

    getConfig: function() {
        var me = this;
        return {
            imageUrl: me.cs && me.cs.image ? (me.cs.image.src || '') : '',
            defaultLabelStyle: Ext.apply({}, me.defaultLabelStyle),
            globalStyle: Ext.apply({}, me.globalStyle),
            shapes: me.cs.dataset.map(function(s) {
                var binding = me.bindingMap.get(s.uuid) || {};
                var labelStyle = me.labelStyleMap.get(s.uuid) || me.defaultLabelStyle;
                return {
                    uuid: s.uuid,
                    type: s.type,
                    coor: s.coor,
                    strokeStyle: s._userStrokeStyle || s.strokeStyle,
                    fillStyle: s.fillStyle,
                    lineWidth: s.lineWidth,
                    ctrlRadius: s.ctrlRadius,
                    labelStyle: Ext.apply({}, labelStyle),
                    deviceId: binding.deviceId,
                    fieldName: binding.fieldName,
                    unit: binding.unit,
                    currentValue: binding.currentValue
                };
            })
        };
    },

    clearAll: function() {
        var me = this;
        me.currentRecord = null;
        me.bindingMap.clear();
        me.labelStyleMap.clear();
        me.lastSelectedUuid = null;
        if (me.cs) {
            me.cs.setData([]);
        }
        if (me.labelLayer) {
            me.labelLayer.innerHTML = '';
        }
    },

    // ========================================================
    // 工具栏回调
    // ========================================================
    onCreateTypeChange: function(btn) {
        if (!btn.pressed || !this.cs) return;
        this.cs.createType = btn.createType;
    },

    afterZoom: function() {
        var me = this;
        Ext.defer(function() {
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        }, 30);
    },

    // ========================================================
    // 工具方法
    // ========================================================
    extractUuid: function(info) {
        if (!info) return null;
        if (info.uuid) return info.uuid;
        if (info.index !== undefined && this.cs.dataset[info.index]) {
            return this.cs.dataset[info.index].uuid;
        }
        return null;
    },

    toHexAlpha: function(percent) {
        var a = Math.max(0, Math.min(100, Math.round(percent)));
        return Math.round(a / 100 * 255).toString(16).padStart(2, '0').toUpperCase();
    },

    parseFillStyle: function(fillStyle) {
        if (!fillStyle) return { hex: '#0000FF', alpha: 25 };
        var m = fillStyle.match(/^#([0-9a-f]{6})([0-9a-f]{2})?$/i);
        if (m) {
            var alpha = m[2] ? Math.round(parseInt(m[2], 16) / 255 * 100) : 100;
            return { hex: '#' + m[1], alpha: alpha };
        }
        return { hex: '#0000FF', alpha: 25 };
    },

    // 清理
    beforeDestroy: function() {
        if (this.originTimer) clearInterval(this.originTimer);
        this.callParent(arguments);
    }
});