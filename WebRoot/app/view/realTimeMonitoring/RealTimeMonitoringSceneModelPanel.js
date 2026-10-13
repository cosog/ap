/**
 * 实时监控模块 - 场景模型只读显示面板
 */
Ext.define("AP.view.realTimeMonitoring.RealTimeMonitoringSceneModelPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.realTimeMonitoringSceneModelPanel',
    layout: 'fit',
    border: false,

    deviceId: null,
    deviceName: null,

    cs: null,
    labelLayer: null,
    imageUrl: '',
    cachedOrigin: { x: 0, y: 0, scale: 1, ready: false },
    bindingMap: null,
    labelStyleMap: null,
    userColorMap: null,
    userLineWidthMap: null,
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
    _resizing: false,
    _resizeTimer: null,

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
            id: 'RealTimeMonitoringSceneModelPanel_Id',
            tbar: [
                { xtype: 'button', text: '🔍+', tooltip: '放大',
                  handler: function() { if (me.cs) { me.cs.setScale(true); me.afterZoom(); } } },
                { xtype: 'button', text: '🔍−', tooltip: '缩小',
                  handler: function() { if (me.cs) { me.cs.setScale(false); me.afterZoom(); } } },
                { xtype: 'button', text: '适配',
                  handler: function() { me.handleResize(); } }
            ],
            items: [{
                xtype: 'container',
                itemId: 'canvasWrap',
                layout: 'absolute',
                style: {
                    position: 'relative',
                    background: '#f8f9fa',
                    border: '1px solid #dee2e6',
                    borderRadius: '4px',
                    overflow: 'hidden',
                    margin: '4px'
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
            }]
        });

        me.callParent(arguments);

        me.on('afterrender', function() {
            Ext.defer(function() {
                me.initCanvasSelect();
                me.loadSceneConfigByDevice(me.deviceId);
            }, 100, me);
        });

        me.on('resize', function() {
            if (me._resizeTimer) clearTimeout(me._resizeTimer);
            me._resizeTimer = setTimeout(function() {
                try { me.handleResize(); }
                catch (e) { console.warn('[SceneModelPanel] resize 异常', e); }
            }, 150);
        });
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
            var canvasCmp = me.down('#canvas');
            if (!canvasCmp || !canvasCmp.el || !canvasCmp.el.dom) return;
            var dom = canvasCmp.el.dom;
            var w = dom.offsetWidth || dom.clientWidth || 0;
            var h = dom.offsetHeight || dom.clientHeight || 0;
            if (w < 20 || h < 20) return;

            try { if (typeof me.cs.resize === 'function') me.cs.resize(); }
            catch (e) { console.warn('[SceneModelPanel] resize 失败', e); }

            try { if (typeof me.cs.fitZoom === 'function') me.cs.fitZoom(); }
            catch (e) { console.warn('[SceneModelPanel] fitZoom 失败', e); }

            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        } finally {
            me._resizing = false;
        }
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

    // ========================================================
    // ★★★ 初始化 canvas-select（照抄场景模型模块的做法）
    //   关键：加 originTimer 60ms 轮询
    // ========================================================
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

        if (typeof CanvasSelect === 'undefined') {
            Ext.Msg.alert('错误', 'CanvasSelect 库未加载');
            return;
        }

        me.cs = new CanvasSelect(canvasEl, me.BLANK_IMAGE);
        me._canvasCurrentUrl = me.BLANK_IMAGE;
        me._lastDataKey = '';
        me.cs.ctrlRadius = me.globalStyle.ctrlRadius;
        me.cs.createType = 0;
        me.cs.scrollZoom = true;
        me.cs.hideLabel = true;

        // 只读
        me.enforceReadOnly();

        me.cs.on('load', function() {
            try { if (typeof me.cs.fitZoom === 'function') me.cs.fitZoom(); }
            catch (e) { console.warn('[SceneModelPanel] load fitZoom 失败', e); }

            me.enforceReadOnly();
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        });
        // ★ 事件里只刷标签，不刷 origin，origin 交给 originTimer
        me.cs.on('updated', function() { me.scheduleRenderLabels(); });
        me.cs.on('coor',    function() { me.scheduleRenderLabels(); });

        // ★★★ 关键：60ms 轮询，避免鼠标移动时读到 mouse/position 的中间状态
        me.originTimer = setInterval(function() {
            if (me.updateOriginIfNeeded()) me.scheduleRenderLabels();
        }, 60);
    },

    // ========================================================
    // 加载场景配置
    // ========================================================
    loadSceneConfigByDevice: function(deviceId) {
        var me = this;
        if (!deviceId || deviceId <= 0) {
            me.renderSceneConfig({ imageUrl: '', shapes: [] });
            return;
        }

        Ext.Ajax.request({
            url: context + '/realTimeMonitoringController/getSceneModelConfigWithRealtimeValues',
            method: 'POST',
            params: { deviceId: deviceId },
            success: function(resp) {
                try {
                    var result = Ext.decode(resp.responseText);
                    var cfg = null;
                    if (result.success) {
                        cfg = result.config;
                        if (typeof cfg === 'string') {
                            try { cfg = JSON.parse(cfg); } catch (e) { cfg = null; }
                        }
                    }
                    if (!cfg) cfg = { imageUrl: '', shapes: [] };
                    me.renderSceneConfig(cfg);
                } catch (e) {
                    me.renderSceneConfig({ imageUrl: '', shapes: [] });
                }
            },
            failure: function() {
                me.renderSceneConfig({ imageUrl: '', shapes: [] });
            }
        });
    },

    // ========================================================
    // 图片加载
    // ========================================================
    setCanvasImage: function(url) {
        var me = this;
        if (!me.cs || me._rebuilding) return;
        if (!url) url = me.BLANK_IMAGE;
        if (url === me._canvasCurrentUrl) return;

        if (url === me.BLANK_IMAGE) {
            me.imageUrl = '';
            me.rebuildCanvasSelect();
            return;
        }

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

    // ========================================================
    // 用 config 渲染
    // ========================================================
    renderSceneConfig: function(cfg) {
        var me = this;
        if (!cfg) cfg = { imageUrl: '', shapes: [] };

        if (cfg.defaultLabelStyle) Ext.apply(me.defaultLabelStyle, cfg.defaultLabelStyle);
        if (cfg.globalStyle) Ext.apply(me.globalStyle, cfg.globalStyle);

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
        }

        if (cfg.imageUrl) me.setCanvasImage(cfg.imageUrl);
        else me.setCanvasImage(me.BLANK_IMAGE);

        var shapes = cfg.shapes || [];
        shapes.forEach(function(s) {
            me.userColorMap.set(s.uuid, s.strokeStyle || '#00FF00');
            me.userLineWidthMap.set(s.uuid, s.lineWidth != null ? s.lineWidth : 1);

            if (s.deviceId && s.fieldName) {
                me.bindingMap.set(s.uuid, {
                    deviceId:     s.deviceId,
                    deviceName:   s.deviceName,
                    fieldName:    s.fieldName,
                    fieldLabel:   s.fieldLabel,
                    unit:         s.unit,
                    dataSource:   s.dataSource,
                    currentValue: s.currentValue
                });
            }
            if (s.labelStyle) me.labelStyleMap.set(s.uuid, s.labelStyle);
        });

        var pureShapes = [];
        shapes.forEach(function(s) {
            if (!s || !s.uuid) return;
            if (s.type == null || !s.coor) return;

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
            } else {
                return;
            }

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
                _userStrokeStyle: userStroke, _userLineWidth: userLw,
                drag: false, readonly: true, locked: true
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

    safeSetData: function(shapes) {
        var me = this;
        if (!me.cs) return;
        var data = Ext.isArray(shapes) ? shapes : [];
        var newKey = data.map(function(s) { return s.uuid || ''; }).join('|');
        if (newKey === me._lastDataKey) return;
        me._lastDataKey = newKey;
        try {
            me.cs.setData(data);
            me.enforceReadOnly();
        } catch (e) {
            me._lastDataKey = null;
            try { me.cs.setData([]); } catch (e2) {}
        }
    },

    // ========================================================
    // 颜色规范化
    // ========================================================
    normalizeColor: function(color, fallback) {
        if (!color || typeof color !== 'string') return fallback;
        color = color.trim();
        if (/^rgba?\(/i.test(color)) return color;
        if (/^#[0-9a-f]{3}$/i.test(color)) {
            return '#' + color[1] + color[1] + color[2] + color[2] + color[3] + color[3];
        }
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
    // origin 计算（与场景模型模块完全一致）
    // ========================================================
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
        Array.from(layer.children).forEach(function(el) {
            existing[el.dataset.uuid] = el;
        });
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
                layer.appendChild(el);
            }
            el.style.cssText = 'position:absolute;padding:4px 10px;border-radius:4px;white-space:nowrap;' +
                'font-weight:600;transform:translate(-50%,' + translateY + ');box-shadow:0 2px 6px rgba(0,0,0,0.18);' +
                'pointer-events:auto;' +
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
        var prefix = hideDev ? fldName : ((b.deviceName || b.deviceId) + '.' + fldName);

        var val = b.currentValue;
        if (val != null && val !== '' && val !== '--') {
            var unit = b.unit;
            var unitStr = (unit != null && unit !== '') ? ('(' + unit + ')') : '';
            return prefix + '：' + val + unitStr;
        }
        return prefix;
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
            return {
                x: (Math.min.apply(null, xs) + Math.max.apply(null, xs)) / 2,
                y: Math.min.apply(null, ys)
            };
        }
        return null;
    },

    afterZoom: function() {
        var me = this;
        Ext.defer(function() {
            me.updateOriginIfNeeded(true);
            me.scheduleRenderLabels();
        }, 30);
    },

    updateRealtimeValuesFromPush: function(pushDeviceId, itemList) {
        var me = this;
        if (pushDeviceId == null) return;
        if (!Ext.isArray(itemList) || itemList.length === 0) return;

        var valueMap = {};
        itemList.forEach(function(item) {
            if (!item || !item.column) return;
            valueMap[String(item.column).toUpperCase()] = item;
        });

        var changed = false;
        me.bindingMap.forEach(function(binding, uuid) {
            if (!binding || !binding.fieldName) return;
            if (String(binding.deviceId) !== String(pushDeviceId)) return;

            var item = valueMap[String(binding.fieldName).toUpperCase()];
            if (!item) return;

            var newValue = (item.value != null && item.value !== '')
                         ? item.value
                         : item.rawValue;

            if (binding.currentValue !== newValue) {
                binding.currentValue = newValue;
                changed = true;
            }
        });

        if (changed) me.scheduleRenderLabels();
    },

    updateRealtimeValue: function(uuid, value, unit) {
        var me = this;
        if (!uuid) return;
        var binding = me.bindingMap.get(uuid);
        if (!binding) return;
        binding.currentValue = value;
        if (unit != null) binding.unit = unit;
        me.scheduleRenderLabels();
    },

    updateRealtimeValues: function(list) {
        var me = this;
        if (!Ext.isArray(list)) return;
        list.forEach(function(item) {
            var binding = me.bindingMap.get(item.uuid);
            if (binding) {
                binding.currentValue = item.value;
                if (item.unit != null) binding.unit = item.unit;
            }
        });
        me.scheduleRenderLabels();
    },

    beforeDestroy: function() {
        if (this._resizeTimer) {
            clearTimeout(this._resizeTimer);
            this._resizeTimer = null;
        }
        if (this.originTimer) {
            clearInterval(this.originTimer);
            this.originTimer = null;
        }
        this._resizing = false;
        try {
            if (this.cs && typeof this.cs.destroy === 'function') this.cs.destroy();
        } catch (e) {}
        this.cs = null;
        this.callParent(arguments);
    }
});